/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.servlet.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMFolder;
import com.openkm.bean.ContentInfo;
import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.FileSizeExceededException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UnsupportedMimeTypeException;
import com.openkm.core.UserQuotaExceededException;
import com.openkm.core.VirusDetectedException;
import com.openkm.module.direct.DirectRepositoryModule;
import com.openkm.util.Benchmark;
import com.openkm.util.FormatUtil;
import com.openkm.util.WebUtil;
import com.openkm.util.impexp.HTMLInfoDecorator;
import com.openkm.util.impexp.ImpExpStats;
import com.openkm.util.impexp.RepositoryImporter;

/**
 * Benchmark servlet
 */
public class BenchmarkServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(BenchmarkServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws 
			ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action")!=null?request.getParameter("action"):"";
		updateSessionManager(request);
				
		if (action.equals("import")) {
			okmImport(request, response);
		} else if (action.equals("copy")) {
			okmCopy(request, response);
		} else if (action.equals("generate")) {
			okmGenerate(request, response);
		} else {
			ServletContext sc = getServletContext();
			sc.getRequestDispatcher("/admin/benchmark.jsp").forward(request, response);
		}
	}

	/**
	 * Load documents into repository several times
	 */
	private void okmImport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.debug("okmImport({}, {})", request, response);
		String path = WebUtil.getString(request, "param1");
		int times = WebUtil.getInt(request, "param2");
		PrintWriter out = response.getWriter();
		ImpExpStats tStats = new ImpExpStats();
		long tBegin = 0, tEnd = 0;
		response.setContentType("text/html");
		header(out);
		out.println("<h1>Benchmark</h1>");
		out.flush();
		
		try {
			File dir = new File(path);
			int docs = FileUtils.listFiles(dir, null, true).size();
			out.println("<b>- Path:</b> "+path+"<br/>");
			out.println("<b>- Times:</b> "+times+"<br/>");
			out.println("<b>- Documents:</b> "+docs+"<br/>");
			out.flush();
			
			Folder fld = new Folder();
			fld.setPath("/okm:root/Benchmark");
			OKMFolder.getInstance().create(null, fld);
			tBegin = System.currentTimeMillis();
			
			for (int i=0; i<times; i++) {
				out.println("<h2>Iteration "+i+"</h2>");
				out.flush();
				//out.println("<table class=\"results\" width=\"100%\">");
				//out.println("<tr><th>#</th><th>Document</th><th>Size</th></tr>");
				
				long begin = System.currentTimeMillis();
				fld.setPath("/okm:root/Benchmark/"+i);
				OKMFolder.getInstance().create(null, fld);
				ImpExpStats stats = RepositoryImporter.importDocuments(null, dir, fld.getPath(), out, 
						new HTMLInfoDecorator(docs));
				long end = System.currentTimeMillis();
				tStats.setSize(tStats.getSize() + stats.getSize());
				tStats.setFolders(tStats.getFolders() + stats.getFolders());
				tStats.setDocuments(tStats.getDocuments() + stats.getDocuments());
				
				//out.println("<table>");
				out.println("<br/>");
				out.println("<b>Size:</b> "+FormatUtil.formatSize(stats.getSize())+"<br/>");
				out.println("<b>Folders:</b> "+stats.getFolders()+"<br/>");
				out.println("<b>Documents:</b> "+stats.getDocuments()+"<br/>");
				out.println("<b>Time:</b> "+FormatUtil.formatSeconds(end - begin)+"<br/>");
				out.flush();
			}
			
			tEnd = System.currentTimeMillis();
		} catch (PathNotFoundException e) {
			out.println("<div class=\"warn\">PathNotFoundException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (ItemExistsException e) {
			out.println("<div class=\"warn\">ItemExistsException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (AccessDeniedException e) {
			out.println("<div class=\"warn\">AccessDeniedException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (RepositoryException e) {
			out.println("<div class=\"warn\">RepositoryException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (DatabaseException e) {
			out.println("<div class=\"warn\">DatabaseException: "+e.getMessage()+"</div>");
			out.flush();
		}
		
		out.println("<hr/>");
		out.println("<b>Total size:</b> "+FormatUtil.formatSize(tStats.getSize())+"<br/>");
		out.println("<b>Total folders:</b> "+tStats.getFolders()+"<br/>");
		out.println("<b>Total documents:</b> "+tStats.getDocuments()+"<br/>");
		out.println("<b>Total time:</b> "+FormatUtil.formatSeconds(tEnd - tBegin)+"<br/>");
		out.print("</body>");
		out.print("</html>");
		out.flush();
		out.close();
		log.debug("okmImport: void");
	}
	
	/**
	 * Copy documents into repository several times
	 */
	private void okmCopy(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.debug("okmCopy({}, {})", request, response);
		String src = WebUtil.getString(request, "param1");
		String dst = WebUtil.getString(request, "param2");
		int times = WebUtil.getInt(request, "param3");
		PrintWriter out = response.getWriter();
		ContentInfo cInfo = new ContentInfo();
		long tBegin = 0, tEnd = 0;
		response.setContentType("text/html");
		header(out);
		out.println("<h1>Benchmark</h1>");
		out.flush();
		
		try {
			cInfo = OKMFolder.getInstance().getContentInfo(null, src);
			out.println("<b>- Source:</b> "+src+"<br/>");
			out.println("<b>- Destination:</b> "+dst+"<br/>");
			out.println("<b>- Size:</b> "+FormatUtil.formatSize(cInfo.getSize())+"<br/>");
			out.println("<b>- Mails:</b> "+cInfo.getMails()+"<br/>");
			out.println("<b>- Folders:</b> "+cInfo.getFolders()+"<br/>");
			out.println("<b>- Documents:</b> "+cInfo.getDocuments()+"<br/>");
			out.flush();
			tBegin = System.currentTimeMillis();
			
			for (int i=0; i<times; i++) {
				out.println("<h2>Iteration "+i+"</h2>");
				out.flush();
				long begin = System.currentTimeMillis();
				Folder fld = new Folder();
				fld.setPath(dst+"/"+i);
				OKMFolder.getInstance().create(null, fld);
				OKMFolder.getInstance().copy(null, src, fld.getPath());
				long end = System.currentTimeMillis();
				out.println("<b>Time:</b> "+FormatUtil.formatSeconds(end - begin)+"<br/>");
				out.flush();
			}
			
			tEnd = System.currentTimeMillis();
		} catch (PathNotFoundException e) {
			out.println("<div class=\"warn\">PathNotFoundException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (ItemExistsException e) {
			out.println("<div class=\"warn\">ItemExistsException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (AccessDeniedException e) {
			out.println("<div class=\"warn\">AccessDeniedException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (RepositoryException e) {
			out.println("<div class=\"warn\">RepositoryException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (DatabaseException e) {
			out.println("<div class=\"warn\">DatabaseException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (UserQuotaExceededException e) {
			out.println("<div class=\"warn\">UserQuotaExceededException: "+e.getMessage()+"</div>");
			out.flush();
		}
				
		out.println("<hr/>");
		out.println("<b>Total size:</b> "+FormatUtil.formatSize(cInfo.getSize() * times)+"<br/>");
		out.println("<b>Total mails:</b> "+cInfo.getMails() * times+"<br/>");
		out.println("<b>Total folders:</b> "+cInfo.getFolders() * times+"<br/>");
		out.println("<b>Total documents:</b> "+cInfo.getDocuments() * times+"<br/>");
		out.println("<b>Total time:</b> "+FormatUtil.formatSeconds(tEnd - tBegin)+"<br/>");
		out.print("</body>");
		out.print("</html>");
		out.flush();
		out.close();
		log.debug("okmCopy: void");
	}
	
	/**
	 * Generate documents into repository
	 */
	private void okmGenerate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.debug("okmGenerate({}, {})", request, response);
		int maxDocuments = WebUtil.getInt(request, "param1");
		int maxFolder = WebUtil.getInt(request, "param2");
		int maxDepth = WebUtil.getInt(request, "param3");
		PrintWriter out = response.getWriter();
		long tBegin = 0, tEnd = 0;
		Benchmark bm = null;
		response.setContentType("text/html");
		header(out);
		out.println("<h1>Benchmark</h1>");
		out.flush();
		
		try {
			bm = new Benchmark(maxDocuments, maxFolder, maxDepth);
			out.println("<b>- Documents:</b> "+bm.getMaxDocuments()+"<br/>");
			out.println("<b>- Folders:</b> "+bm.getMaxFolders()+"<br/>");
			out.println("<b>- Depth:</b> "+bm.getMaxDepth()+"<br/>");
			out.println("<b>- Calibration:</b> "+bm.runCalibration()+" ms<br/>");
			out.println("<table class=\"results\" width=\"80%\">");
			out.println("<tr><th>Date</th><th>Partial seconds</th><th>Partial miliseconds</th><th>Total folders</th><th>Total documents</th></tr>");
			out.flush();
			tBegin = System.currentTimeMillis();
			Folder root = new DirectRepositoryModule().getRootFolder(null);
			bm.populateText(null, root, out);
			tEnd = System.currentTimeMillis();
			out.println("</table>");
		} catch (FileNotFoundException e) {
			out.println("<div class=\"warn\">FileNotFoundException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (PathNotFoundException e) {
			out.println("<div class=\"warn\">PathNotFoundException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (ItemExistsException e) {
			out.println("<div class=\"warn\">ItemExistsException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (AccessDeniedException e) {
			out.println("<div class=\"warn\">AccessDeniedException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (RepositoryException e) {
			out.println("<div class=\"warn\">RepositoryException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (DatabaseException e) {
			out.println("<div class=\"warn\">DatabaseException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (UserQuotaExceededException e) {
			out.println("<div class=\"warn\">UserQuotaExceededException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (InputMismatchException e) {
			out.println("<div class=\"warn\">InputMismatchException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (UnsupportedMimeTypeException e) {
			out.println("<div class=\"warn\">UnsupportedMimeTypeException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (FileSizeExceededException e) {
			out.println("<div class=\"warn\">FileSizeExceededException: "+e.getMessage()+"</div>");
			out.flush();
		} catch (VirusDetectedException e) {
			out.println("<div class=\"warn\">VirusDetectedException: "+e.getMessage()+"</div>");
			out.flush();
		}
				
		out.println("<hr/>");
		out.println("<b>Total folders:</b> "+bm.getTotalFolders()+"<br/>");
		out.println("<b>Total documents:</b> "+bm.getTotalDocuments()+"<br/>");
		out.println("<b>Total time:</b> "+FormatUtil.formatSeconds(tEnd - tBegin)+"<br/>");
		out.print("</body>");
		out.print("</html>");
		out.flush();
		out.close();
		log.debug("okmGenerate: void");
	}

	private void header(PrintWriter out) {
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		out.println("<link rel=\"Shortcut icon\" href=\"favicon.ico\" />");
		out.println("<link rel=\"stylesheet\" href=\"css/style.css\" type=\"text/css\" />");
		out.println("<script src=\"js/biblioteca.js\" type=\"text/javascript\"></script>");
		out.println("<script type=\"text/javascript\">scrollToBottom();</script>");
		out.println("<title>Benchmark</title>");
		out.println("</head>");
		out.println("<body>");
	}
}