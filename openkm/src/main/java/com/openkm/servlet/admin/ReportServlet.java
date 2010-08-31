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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.EvalError;

import com.openkm.core.DatabaseException;
import com.openkm.dao.HibernateUtil;
import com.openkm.dao.ReportDAO;
import com.openkm.dao.bean.Report;
import com.openkm.util.DocConverter;
import com.openkm.util.JCRUtils;
import com.openkm.util.ReportUtil;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtil;

/**
 * Execute report servlet
 */
public class ReportServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ReportServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();

			Map<String, String> types = new LinkedHashMap<String, String>();
			types.put(Report.SQL, "SQL");
			types.put(Report.SCRIPT, "Script");
			//types.put(Report.HIBERNATE, "Hibernate");
			//types.put(Report.COLLECTION, "Collection");
			//types.put(Report.XPATH, "XPath");
			
			if (action.equals("create")) {
				ServletContext sc = getServletContext();
				Report rp = new Report();
				sc.setAttribute("action", action);
				sc.setAttribute("types", types);
				sc.setAttribute("rp", rp);
				sc.getRequestDispatcher("/admin/report_edit.jsp").forward(request, response);
			} else if (action.equals("edit")) {
				ServletContext sc = getServletContext();
				int rpId = WebUtil.getInt(request, "rp_id");
				Report rp = ReportDAO.findByPk(rpId);
				sc.setAttribute("action", action);
				sc.setAttribute("types", types);
				sc.setAttribute("rp", rp);
				sc.getRequestDispatcher("/admin/report_edit.jsp").forward(request, response);
			} else if (action.equals("delete")) {
				ServletContext sc = getServletContext();
				int rpId = WebUtil.getInt(request, "rp_id");
				Report rp = ReportDAO.findByPk(rpId);
				sc.setAttribute("action", action);
				sc.setAttribute("types", types);
				sc.setAttribute("rp", rp);
				sc.getRequestDispatcher("/admin/report_edit.jsp").forward(request, response);
			} else if (action.equals("execute")) {
				execute(session, request, response);
			} else {
				list(session, request, response);
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} catch (JRException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} catch (EvalError e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} finally {
			JCRUtils.logout(session);
		}
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doPost({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = "";
		Session session = null;
		updateSessionManager(request);
		
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				session = JCRUtils.getSession();
				InputStream is = null;
				FileItemFactory factory = new DiskFileItemFactory(); 
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(request);
				Report rp = new Report();
				
				for (Iterator<FileItem> it = items.iterator(); it.hasNext();) {
					FileItem item = it.next();
					
					if (item.isFormField()) {
						if (item.getFieldName().equals("action")) {
							action = item.getString("UTF-8");
						} else if (item.getFieldName().equals("rp_id")) {
							rp.setId(Integer.parseInt(item.getString("UTF-8")));
						} else if (item.getFieldName().equals("rp_name")) {
							rp.setName(item.getString("UTF-8"));
						} else if (item.getFieldName().equals("rp_type")) {
							rp.setType(item.getString("UTF-8"));
						} else if (item.getFieldName().equals("rp_active")) {
							rp.setActive(true);
						
						}
					} else {
						is = item.getInputStream();
						rp.setFileName(FilenameUtils.getName(item.getName()));
						rp.setFileContent(IOUtils.toByteArray(is));
						is.close();
					}
				}
			
				if (action.equals("create")) {
					ReportDAO.create(rp);
					
					// Activity log
					UserActivity.log(session.getUserID(), "ADMIN_REPORT_CREATE", null, rp.toString());
					list(session, request, response);
				} else if (action.equals("edit")) {
					ReportDAO.update(rp);
					
					// Activity log
					UserActivity.log(session.getUserID(), "ADMIN_REPORT_EDIT", Integer.toString(rp.getId()), rp.toString());
					list(session, request, response);
				} else if (action.equals("delete")) {
					ReportDAO.delete(rp.getId());
					
					// Activity log
					UserActivity.log(session.getUserID(), "ADMIN_REPORT_DELETE", Integer.toString(rp.getId()), null);
					list(session, request, response);
				}
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} catch (FileUploadException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * List registered reports
	 */
	private void list(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("list({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		List<Report> list = ReportDAO.findAll();
		
		for (Report rp : list) {
			if (Report.SQL.equals(rp.getType())) {
				rp.setType("SQL");
			} else if (Report.SCRIPT.equals(rp.getType())) {
				rp.setType("Script");
			} else if (Report.HIBERNATE.equals(rp.getType())) {
				rp.setType("Hibernate");
			} else if (Report.XPATH.equals(rp.getType())) {
				rp.setType("XPath");
			} else if (Report.COLLECTION.equals(rp.getType())) {
				rp.setType("Collection");
			}
		}
		
		sc.setAttribute("reports", list);
		sc.getRequestDispatcher("/admin/report_list.jsp").forward(request, response);
		log.debug("list: void");
	}
	
	/**
	 * Execute report
	 */
	private void execute(Session session, HttpServletRequest request, HttpServletResponse response) throws 
			IOException, DatabaseException, JRException, EvalError {
		log.debug("execute({}, {}, {})", new Object[] { session, request, response });
		int rpId = WebUtil.getInt(request, "rp_id");
		Report rp = ReportDAO.findByPk(rpId);
		String agent = request.getHeader("USER-AGENT");
		
		// Disable browser cache
		response.setHeader("Expires", "Sat, 6 May 1971 12:00:00 GMT");
		response.setHeader("Cache-Control", "max-age=0, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		
		// Set MIME type
		response.setContentType(DocConverter.PDF);
		String fileName = rp.getFileName().substring(0, rp.getFileName().indexOf('.')) + ".pdf"; 
		
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			log.debug("Agent: Explorer");
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");
		} else if (null != agent && -1 != agent.indexOf("Mozilla"))	{
			log.debug("Agent: Mozilla");
			fileName = MimeUtility.encodeText(fileName, "UTF-8", "B");
		} else {
			log.debug("Agent: Unknown");
		}
		
		// Set filename
		response.setHeader("Content-disposition", "attachment; filename=\""+ fileName +"\"");
		
		// Set default report parameters
		Map<String, String> parameters = new HashMap<String, String>();
		String host = com.openkm.core.Config.APPLICATION_URL;
		parameters.put("host", host.substring(0, host.lastIndexOf("/")+1));
		
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		OutputStream os = null;
		org.hibernate.Session dbSession = null;
		
		try {
			baos = new ByteArrayOutputStream();
			bais = new ByteArrayInputStream(rp.getFileContent());
			
			if (Report.SQL.equals(rp.getType())) {
				dbSession = HibernateUtil.getSession();
				ReportUtil.generateReport(baos, bais, parameters, ReportUtil.PDF_OUTPUT, dbSession.connection());
			} else if (Report.SCRIPT.equals(rp.getType())) {
				ReportUtil.generateReport(baos, bais, parameters, ReportUtil.PDF_OUTPUT);
			}
			
			// Send back to browser
			os = response.getOutputStream();
			IOUtils.write(baos.toByteArray(), os);
			os.flush();
		} finally {
			if (Report.SQL.equals(rp.getType())) {
				//HibernateUtil.close(dbSession);
			}
			IOUtils.closeQuietly(bais);
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(os);
		}
		
		// Activity log
		UserActivity.log(request.getRemoteUser(), "ADMIN_REPORT_EXECUTE", Integer.toString(rpId), rp.toString());
		log.debug("execute: void");
	}
}
