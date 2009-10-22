/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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

package es.git.openkm.frontend.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMDocument;
import es.git.openkm.bean.Document;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.Config;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.config.ErrorCode;
import es.git.openkm.util.DocConverter;
import es.git.openkm.util.FileUtils;
import es.git.openkm.util.impexp.HTMLInfoDecorator;
import es.git.openkm.util.impexp.RepositoryExporter;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMDownloadServlet"
 *                           display-name="Name for DownloadDocument"
 *                           description="Description for Download Servlet"
 * @web.servlet-mapping      url-pattern="/OKMDownloadServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMDownloadServlet extends OKMHttpServlet {
	private static Logger log = LoggerFactory.getLogger(OKMDownloadServlet.class);
	private static final long serialVersionUID = -1575303169358903617L;

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("service("+req+", "+resp+")");
		req.setCharacterEncoding("UTF-8");
		String token;
		String path = new String(req.getParameter("id").getBytes("ISO-8859-1"), "UTF-8");
		String checkout = req.getParameter("checkout");
		String ver = req.getParameter("ver");
		String export = req.getParameter("export");
		String toPdf = req.getParameter("toPdf");
		String toSwf = req.getParameter("toSwf");
		String inline = req.getParameter("inline");
		File tmp = File.createTempFile("okm", ".mkk");
		Document doc = null;
		InputStream is = null;
		
		try {
			token = getToken(req);
			File pdfCache = null;
			File swfCache = null;
						
			// Get document
			if (export != null) {
				FileOutputStream os = new FileOutputStream(tmp);
				exportZip(token, path, os);
				os.flush();
				os.close();
				is = new FileInputStream(tmp);
			} else {
				OKMDocument okmDoc = OKMDocument.getInstance();
				doc = okmDoc.getProperties(token, path);
				pdfCache = new File(Config.PDF_CACHE+File.separator+doc.getUuid());
				swfCache = new File(Config.SWF_CACHE+File.separator+doc.getUuid());
				
				if (toSwf == null || toSwf != null && !Config.SYSTEM_PDF2SWF.equals("") && !swfCache.exists() ||
						toPdf == null || toPdf != null && !pdfCache.exists()) {
					if (ver != null && !ver.equals("")) {
						is = okmDoc.getContentByVersion(token, path, ver);
					} else {
						is = okmDoc.getContent(token, path, checkout != null);
					}
				}
			}
			
			// Send document
			if (export != null) {
				String fileName = FileUtils.getName(path)+".zip";
				sendFile(req, resp, fileName, "application/zip", inline != null, is);
				is.close();
				tmp.delete();
			} else if (doc != null) {
				String fileName = FileUtils.getName(doc.getPath());
				
				if (Config.SYSTEM_OPENOFFICE.equals("on")) {
					if (toPdf != null) {
						if (pdfCache.exists()) {
							is = new FileInputStream(pdfCache);
						} else {
							doc2pdf(is, doc.getMimeType(), pdfCache);
							is = new FileInputStream(pdfCache);
						}
						
						doc.setMimeType("application/pdf");
						fileName = FileUtils.getFileName(fileName)+".pdf";
					} else if (toSwf != null && !Config.SYSTEM_PDF2SWF.equals("")) {
						if (swfCache.exists()) {
							is = new FileInputStream(swfCache);
						} else {
							File prvTmp = File.createTempFile("okm", ".prv");
							
							try {
								if (doc.getMimeType().equals("application/pdf")) {
									FileOutputStream fos = new FileOutputStream(prvTmp);
									IOUtils.copy(is, fos);
									fos.flush();
									fos.close();
									pdf2swf(prvTmp, swfCache);
									is = new FileInputStream(swfCache);
								} else {
									doc2pdf(is, doc.getMimeType(), prvTmp);
									pdf2swf(prvTmp, swfCache);
									is = new FileInputStream(swfCache);
								}
							} finally {
								prvTmp.delete();
							}
						}
						
						doc.setMimeType("application/x-shockwave-flash");
						fileName = FileUtils.getFileName(fileName)+".swf";
					}
				}
				
				sendFile(req, resp, fileName, doc.getMimeType(), inline != null, is);
				is.close();
				tmp.delete();
			}
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_PathNotFound), e.getMessage()));
		} catch (RepositoryException e) {
			log.warn(e.getMessage(), e);
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_Repository), e.getMessage()));
		} catch (OKMException e) {
			log.warn(e.getMessage(), e);
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_OKMGeneral), e.getMessage()));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_IOException), e.getMessage()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServletException(new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDownloadService, ErrorCode.CAUSE_General), e.getMessage()));
		}
		
		log.debug("service: void");
	}
	
	/**
	 * Send file to client browser
	 * @throws IOException If there is a communication error.
	 */
	private void sendFile(HttpServletRequest req, HttpServletResponse resp, 
			String fileName, String mimeType, boolean inline, InputStream is) throws IOException {
		log.info("sendFile("+req+", "+resp+", "+fileName+", "+mimeType+", "+inline+", "+is+")");
		String agent = req.getHeader("USER-AGENT");
		
		// Disable browser cache
		resp.setHeader("Expires", "Sat, 6 May 1971 12:00:00 GMT");
		resp.setHeader("Cache-Control", "max-age=0, must-revalidate");
		resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
		resp.setHeader("Pragma", "no-cache");

		// Set MIME type
		resp.setContentType(mimeType);
		
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			log.debug("Agent: Explorer");
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");
		} else if (null != agent && -1 != agent.indexOf("Mozilla"))	{
			log.debug("Agent: Mozilla");
			fileName = MimeUtility.encodeText(fileName, "UTF-8", "B");
		} else {
			log.debug("Agent: Unknown");
		}
		
		if (inline) {
			resp.setHeader("Content-disposition", "inline; filename=\""+fileName+"\"");
		} else {
			resp.setHeader("Content-disposition", "attachment; filename=\""+fileName+"\"");
		}

		// Set length
		resp.setContentLength(is.available());
		log.debug("File: "+fileName+", Length: "+is.available());
		
		ServletOutputStream sos = resp.getOutputStream();
		IOUtils.copy(is, sos);
		sos.flush();
		sos.close();
	}
	
	/**
	 * Generate a zip file from a repository folder path
	 * 
	 * @param token
	 * @param path
	 * @return
	 * @throws IOException 
	 * @throws PathNotFoundException 
	 * @throws AccessDeniedException 
	 * @throws RepositoryException 
	 */
	private void exportZip(String token, String path, OutputStream os) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, IOException {
		log.debug("exportZip("+token+", "+path+")");
		ZipOutputStream zos = null;
		File tmp = null;
		
		try {
			tmp = FileUtils.createTempDir();
			
			// Export files
			StringWriter out = new StringWriter();
			RepositoryExporter.exportDocuments(token, path, tmp, out, new HTMLInfoDecorator());
			out.close();
			
			// Zip files
			zos = new ZipOutputStream(os);
			
			// Prevents java.util.zip.ZipException: ZIP file must have at least one entry
			ZipEntry ze = new ZipEntry(FileUtils.getName(path)+"/");
			zos.putNextEntry(ze);
			zos.closeEntry();
			
			exportZipHelper(tmp, zos, FileUtils.getName(path));
		} catch (IOException e) {
			log.error("Error exporting zip", e);
			throw e;
		} finally {
			if (tmp != null) {
				try {
					org.apache.commons.io.FileUtils.deleteDirectory(tmp);
				} catch (IOException e) {
					log.error("Error deleting temporal directory", e);
					throw e;
				}
			}
			
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					log.error("Error closing zip output stream", e);
					throw e;
				}
			}
		}
		
		log.debug("exportZip: void");
	}
	
	/**
	 * @param fs
	 * @param zos
	 * @param zePath
	 * @throws IOException
	 */
	private void exportZipHelper(File fs, ZipOutputStream zos, String zePath) throws IOException {
		log.debug("exportZipHelper("+fs+", "+zos+", "+zePath+")");
		File[] files = fs.listFiles();
		
		for (int i=0; i<files.length; i++) {
			if (files[i].isDirectory()) {
				log.info("DIRECTORY "+files[i]);
				ZipEntry ze = new ZipEntry(zePath+"/"+files[i].getName()+"/");
				zos.putNextEntry(ze);
				zos.closeEntry();
				
				exportZipHelper(files[i], zos, zePath+"/"+files[i].getName());
			} else {
				log.info("FILE "+files[i]);
				FileInputStream fis = new FileInputStream(files[i]);
				ZipEntry ze = new ZipEntry(zePath+"/"+files[i].getName());
				zos.putNextEntry(ze);
				IOUtils.copy(fis, zos);
				zos.closeEntry();
				fis.close();
			}
		}
		
		log.debug("exportZipHelper: void");
	}
	
	/**
	 * 
	 */
	private void doc2pdf(InputStream is, String mimeType, File output) throws IOException {
		log.info("** Convert from "+mimeType+" to PDF **");
		DocConverter dc = new DocConverter();
		FileOutputStream os = new FileOutputStream(output);
		dc.convert(is, mimeType, os, "application/pdf");
		os.flush();
		os.close();
		is.close();
	}
	
	/**
	 * 
	 */
	private void pdf2swf(File input, File output) throws IOException {
		log.info("** Convert from PDF to SWF **");
		try {
			ProcessBuilder pb = new ProcessBuilder(Config.SYSTEM_PDF2SWF, input.getPath(), "-o", output.getPath());
			Process process = pb.start();
			process.waitFor();
			String info = IOUtils.toString(process.getInputStream());
			process.destroy();
		
			// Check return code
			if (process.exitValue() == 1) {
				log.warn(info);
			}
		} catch (InterruptedException e) {
			log.error("Error in PDF to SWF conversion", e);
		}
	}
}
