/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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
import java.util.Set;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

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

import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.dao.HibernateUtil;
import com.openkm.dao.ReportDAO;
import com.openkm.dao.bean.Report;
import com.openkm.dao.bean.ReportParameter;
import com.openkm.util.JCRUtils;
import com.openkm.util.ReportUtils;
import com.openkm.util.SecureStore;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtils;

/**
 * Execute report servlet
 */
public class ReportServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ReportServlet.class);
	private static Map<String, String> paramTypes = new LinkedHashMap<String, String>();
	private static Map<String, String> types = new LinkedHashMap<String, String>();
	
	static {
		types.put(Report.SQL, "SQL");
		types.put(Report.SCRIPT, "Script");
		//types.put(Report.HIBERNATE, "Hibernate");
		//types.put(Report.COLLECTION, "Collection");
		//types.put(Report.XPATH, "XPath");
		
		paramTypes.put(ReportParameter.INPUT, "INPUT");
		paramTypes.put(ReportParameter.TEXTAREA, "TEXTAREA");
		paramTypes.put(ReportParameter.DATE, "DATE");
		paramTypes.put(ReportParameter.PATH, "PATH");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtils.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			
			if (action.equals("create")) {
				ServletContext sc = getServletContext();
				Report rp = new Report();
				sc.setAttribute("action", action);
				sc.setAttribute("types", types);
				sc.setAttribute("rp", rp);
				sc.getRequestDispatcher("/admin/report_edit.jsp").forward(request, response);
			} else if (action.equals("edit")) {
				ServletContext sc = getServletContext();
				int rpId = WebUtils.getInt(request, "rp_id");
				Report rp = ReportDAO.findByPk(rpId);
				sc.setAttribute("action", action);
				sc.setAttribute("types", types);
				sc.setAttribute("rp", rp);
				sc.getRequestDispatcher("/admin/report_edit.jsp").forward(request, response);
			} else if (action.equals("delete")) {
				ServletContext sc = getServletContext();
				int rpId = WebUtils.getInt(request, "rp_id");
				Report rp = ReportDAO.findByPk(rpId);
				sc.setAttribute("action", action);
				sc.setAttribute("types", types);
				sc.setAttribute("rp", rp);
				sc.getRequestDispatcher("/admin/report_edit.jsp").forward(request, response);
			} else if (action.equals("paramList")) {
				paramList(session, request, response);
			} else if (action.equals("paramCreate")) {
				paramCreate(session, request, response);
			} else if (action.equals("paramEdit")) {
				paramEdit(session, request, response);
			} else if (action.equals("paramDelete")) {
				paramDelete(session, request, response);
			} else if (action.equals("execParams")) {
				execParams(session, request, response);
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
						rp.setFileMime(Config.mimeTypes.getContentType(item.getName()));
						rp.setFileContent(SecureStore.b64Encode(IOUtils.toByteArray(is)));
						is.close();
					}
				}
			
				if (action.equals("create")) {
					int id = ReportDAO.create(rp);
					
					// Activity log
					UserActivity.log(session.getUserID(), "ADMIN_REPORT_CREATE", Integer.toString(id), rp.toString());
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
	 * Show report parameters, previous step to execution
	 */
	private void execParams(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {	
		log.debug("execParams({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		int rpId = WebUtils.getInt(request, "rp_id");
		Set<ReportParameter> params = ReportDAO.findByPk(rpId).getParams();
		
		for (ReportParameter rpp : params) {
			if (ReportParameter.INPUT.equals(rpp.getType())) {
				rpp.setType("Input");
			} else if (ReportParameter.TEXTAREA.equals(rpp.getType())) {
				rpp.setType("TextArea");
			} else if (ReportParameter.DATE.equals(rpp.getType())) {
				rpp.setType("Date");
			} else if (ReportParameter.PATH.equals(rpp.getType())) {
				rpp.setType("Path");
			}
		}
		
		sc.setAttribute("rp_id", rpId);
		sc.setAttribute("params", params);
		sc.setAttribute("ReportUtil", new ReportUtils());
		sc.getRequestDispatcher("/admin/report_exec.jsp").forward(request, response);
		log.debug("execParams: void");
	}
	
	/**
	 * Execute report
	 */
	private void execute(Session session, HttpServletRequest request, HttpServletResponse response) throws 
			IOException, DatabaseException, JRException, EvalError {
		log.debug("execute({}, {}, {})", new Object[] { session, request, response });
		int rpId = WebUtils.getInt(request, "rp_id");
		int out = WebUtils.getInt(request, "out",  ReportUtils.OUTPUT_PDF);
		Report rp = ReportDAO.findByPk(rpId);
		String agent = request.getHeader("USER-AGENT");
		
		// Disable browser cache
		response.setHeader("Expires", "Sat, 6 May 1971 12:00:00 GMT");
		response.setHeader("Cache-Control", "max-age=0, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		
		// Set MIME type
		response.setContentType(ReportUtils.FILE_MIME[out]);
		String fileName = rp.getFileName().substring(0, rp.getFileName().indexOf('.')) + ReportUtils.FILE_EXTENSION[out]; 
		
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
		
		for (ReportParameter rpp : rp.getParams()) {
			parameters.put(rpp.getName(), WebUtils.getString(request, rpp.getName()));
		}
		
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		OutputStream os = null;
		org.hibernate.Session dbSession = null;
		
		try {
			baos = new ByteArrayOutputStream();
			bais = new ByteArrayInputStream(SecureStore.b64Decode(rp.getFileContent()));
			
			if (Report.SQL.equals(rp.getType())) {
				dbSession = HibernateUtil.getSessionFactory().openSession();
				
				if (ReportUtils.MIME_JRXML.equals(rp.getFileMime())) {
					JasperReport jr = JasperCompileManager.compileReport(bais);
					ReportUtils.generateReport(baos, jr, parameters, out, dbSession.connection());
				} else if (ReportUtils.MIME_JASPER.equals(rp.getFileMime())) {
					JasperReport jr = (JasperReport) JRLoader.loadObject(bais);
					ReportUtils.generateReport(baos, jr, parameters, out, dbSession.connection());
				}
			} else if (Report.SCRIPT.equals(rp.getType())) {
				if (ReportUtils.MIME_JRXML.equals(rp.getFileMime())) {
					JasperReport jr = JasperCompileManager.compileReport(bais);
					ReportUtils.generateReport(baos, jr, parameters, out);
				} else if (ReportUtils.MIME_JASPER.equals(rp.getFileMime())) {
					JasperReport jr = (JasperReport) JRLoader.loadObject(bais);
					ReportUtils.generateReport(baos, jr, parameters, out);
				}
			}
			
			// Send back to browser
			os = response.getOutputStream();
			IOUtils.write(baos.toByteArray(), os);
			os.flush();
		} finally {
			if (Report.SQL.equals(rp.getType())) {
				HibernateUtil.close(dbSession);
			}
			IOUtils.closeQuietly(bais);
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(os);
		}
		
		// Activity log
		UserActivity.log(request.getRemoteUser(), "ADMIN_REPORT_EXECUTE", Integer.toString(rpId), rp.toString());
		log.debug("execute: void");
	}
	
	/**
	 * List reports parameters
	 */
	private void paramList(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("paramList({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		int rpId = WebUtils.getInt(request, "rp_id");
		Report rep = ReportDAO.findByPk(rpId);
		Set<ReportParameter> params = rep.getParams();
		
		for (ReportParameter rpp : params) {
			if (ReportParameter.INPUT.equals(rpp.getType())) {
				rpp.setType("Input");
			} else if (ReportParameter.TEXTAREA.equals(rpp.getType())) {
				rpp.setType("TextArea");
			} else if (ReportParameter.DATE.equals(rpp.getType())) {
				rpp.setType("Date");
			} else if (ReportParameter.PATH.equals(rpp.getType())) {
				rpp.setType("Path");
			}
		}
		
		sc.setAttribute("rp_id", rpId);
		sc.setAttribute("params", params);
		sc.getRequestDispatcher("/admin/report_param_list.jsp").forward(request, response);
		log.debug("paramList: void");
	}
	
	/**
	 * Create report parameter 
	 */
	private void paramCreate(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, RepositoryException {
		log.debug("paramCreate({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			int rpId = WebUtils.getInt(request, "rp_id");
			ReportParameter rpp = new ReportParameter();
			rpp.setLabel(WebUtils.getString(request, "rpp_label"));
			rpp.setName(WebUtils.getString(request, "rpp_name"));
			rpp.setType(WebUtils.getString(request, "rpp_type"));
			Report rp = ReportDAO.findByPk(rpId);
			rp.getParams().add(rpp);
			ReportDAO.update(rp);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_REPORT_PARAMETER_CREATE", Integer.toString(rpId), rpp.toString());
			paramList(session, request, response);
		} else {
			ServletContext sc = getServletContext();
			ReportParameter rpp = new ReportParameter();
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("paramTypes", paramTypes);
			sc.setAttribute("rpp", rpp);
			sc.getRequestDispatcher("/admin/report_param_edit.jsp").forward(request, response);
		}
		
		log.debug("paramCreate: void");
	}
	
	/**
	 * Edit report parameter
	 */
	private void paramEdit(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("paramEdit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			int rppId = WebUtils.getInt(request, "rpp_id");
			ReportParameter rpp = ReportDAO.findParamByPk(rppId);
			rpp.setLabel(WebUtils.getString(request, "rpp_label"));
			rpp.setName(WebUtils.getString(request, "rpp_name"));
			rpp.setType(WebUtils.getString(request, "rpp_type"));
			ReportDAO.updateParam(rpp);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_REPORT_PARAMETER_EDIT", Integer.toString(rpp.getId()), rpp.toString());
			paramList(session, request, response);
		} else {
			ServletContext sc = getServletContext();
			int rppId = WebUtils.getInt(request, "rpp_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("paramTypes", paramTypes);
			sc.setAttribute("rpp", ReportDAO.findParamByPk(rppId));
			sc.getRequestDispatcher("/admin/report_param_edit.jsp").forward(request, response);
		}
		
		log.debug("paramEdit: void");
	}
	
	/**
	 * Delete report parameter
	 */
	private void paramDelete(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("paramDelete({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			int rppId = WebUtils.getInt(request, "rpp_id");
			ReportDAO.deleteParam(rppId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_REPORT_PARAMETER_DELETE", Integer.toString(rppId), null);
			paramList(session, request, response);
		} else {
			ServletContext sc = getServletContext();
			int rppId = WebUtils.getInt(request, "rpp_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("paramTypes", paramTypes);
			sc.setAttribute("rpp", ReportDAO.findParamByPk(rppId));
			sc.getRequestDispatcher("/admin/report_param_edit.jsp").forward(request, response);
		}
		
		log.debug("paramDelete: void");
	}
}
