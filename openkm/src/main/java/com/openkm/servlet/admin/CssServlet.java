/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2012 Paco Avila & Josep Llort
 * 
 * No bytes were intentionally harmed during the development of this application.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.servlet.admin;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.core.MimeTypeConfig;
import com.openkm.dao.CssDAO;
import com.openkm.dao.HibernateUtil;
import com.openkm.dao.LegacyDAO;
import com.openkm.dao.bean.Css;
import com.openkm.util.UserActivity;
import com.openkm.util.WarUtils;
import com.openkm.util.WebUtils;

/**
 * Css styles servlet
 */
public class CssServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(CssServlet.class);
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String method = request.getMethod();
		
		if (method.equals(METHOD_GET)) {
			doGet(request, response);
		} else if (method.equals(METHOD_POST)) {
			doPost(request, response);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtils.getString(request, "action");
		String userId = request.getRemoteUser();
		updateSessionManager(request);
		
		try {
			if (action.equals("create")) {
				create(userId, request, response);
			} else if (action.equals("edit")) {
				edit(userId, request, response);
			} else if (action.equals("delete")) {
				delete(userId, request, response);
			} else if (action.equals("export")) {
				export(userId, request, response);
			}
			
			if (action.equals("") || WebUtils.getBoolean(request, "persist")) {
				list(userId, request, response);
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.debug("doPost({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtils.getString(request, "action");
		String userId = request.getRemoteUser();
		Session dbSession = null;
		updateSessionManager(request);
		
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				InputStream is = null;
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(request);
				Css css = new Css();
				css.setActive(false);
				byte data[] = null;
				
				for (Iterator<FileItem> it = items.iterator(); it.hasNext();) {
					FileItem item = it.next();
					
					if (item.isFormField()) {
						if (item.getFieldName().equals("action")) {
							action = item.getString("UTF-8");
						} else if (item.getFieldName().equals("css_id")) {
							if (!item.getString("UTF-8").isEmpty()) {
								css.setId(new Long(item.getString("UTF-8")).longValue());
							}
						} else if (item.getFieldName().equals("css_name")) {
							css.setName(item.getString("UTF-8"));
						} else if (item.getFieldName().equals("css_context")) {
							css.setContext(item.getString("UTF-8"));
						} else if (item.getFieldName().equals("css_content")) {
							css.setContent(item.getString("UTF-8"));
						} else if (item.getFieldName().equals("css_active")) {
							css.setActive(true);
						}
					} else {
						is = item.getInputStream();
						data = IOUtils.toByteArray(is);
						is.close();
					}
				}
				
				if (action.equals("import")) {
					dbSession = HibernateUtil.getSessionFactory().openSession();
					importCss(userId, request, response, data, dbSession);
					// Activity log
					UserActivity.log(request.getRemoteUser(), "ADMIN_CSS_IMPORT", null, null, null);
				} else if (action.equals("edit")) {
					CssDAO.getInstance().update(css);
					
					// Activity log
					UserActivity.log(userId, "ADMIN_CSS_UPDATE", String.valueOf(css.getId()), null, css.getName());
				} else if (action.equals("delete")) {
					String name = WebUtils.getString(request, "css_name");
					CssDAO.getInstance().delete(css.getId());
					
					// Activity log
					UserActivity.log(userId, "ADMIN_CSS_DELETE", String.valueOf(css.getId()), null, name);
				} else if (action.equals("create")) {
					long id = CssDAO.getInstance().create(css);
					
					// Activity log
					UserActivity.log(userId, "ADMIN_CSS_CREATE", String.valueOf(id), null, css.getName());
				}
			}
			
			list(userId, request, response);
		} catch (FileUploadException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		}
	}
	
	/**
	 * Import a new CSS into database
	 */
	private void importCss(String userId, HttpServletRequest request, HttpServletResponse response,
			final byte[] data, Session dbSession) throws DatabaseException,
			IOException, SQLException {
		log.debug("importCss({}, {}, {}, {}, {})", new Object[] { userId, request, response, data, dbSession });
		dbSession.doWork(new Work() {
			@Override
			public void execute(Connection con) throws SQLException {
				Statement stmt = con.createStatement();
				InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(data));
				BufferedReader br = new BufferedReader(is);
				String query;
				
				try {
					while ((query = br.readLine()) != null) {
						stmt.executeUpdate(query);
					}
				} catch (IOException e) {
					throw new SQLException(e.getMessage(), e);
				}
				
				LegacyDAO.close(stmt);
			}
		});
		
		log.debug("importLanguage: void");
	}
	
	/**
	 * export
	 */
	private void export(String userId, HttpServletRequest request, HttpServletResponse response) throws DatabaseException, IOException {
		log.debug("export({}, {}, {})", new Object[] { userId, request, response });
		long id = WebUtils.getLong(request, "css_id");
		Css css = CssDAO.getInstance().findByPk(id);
		String fileName = "OpenKM_" + WarUtils.getAppVersion().getVersion() + "_" +css.getName() + "_" + css.getContext() + ".sql";
		
		// Prepare file headers
		WebUtils.prepareSendFile(request, response, fileName, MimeTypeConfig.MIME_SQL, false);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
		StringBuffer insertCss = new StringBuffer("INSERT INTO OKM_CSS (CSS_NAME, CSS_CONTEXT, CSS_CONTENT, CSS_ACTIVE) VALUES ('");
		insertCss.append(css.getName()).append("', '");
		insertCss.append(css.getContext()).append("', '");
		insertCss.append(css.getContent().replaceAll("'", "''")).append("', '");
		insertCss.append("T").append("');");
		out.println(insertCss);
		
		out.flush();
		log.debug("export: void");
	}
	
	/**
	 * Delete css
	 */
	private void delete(String userId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("delete({}, {}, {})", new Object[] { userId, request, response });
		ServletContext sc = getServletContext();
		long id = WebUtils.getLong(request, "css_id");
		
		sc.setAttribute("action", WebUtils.getString(request, "action"));
		sc.setAttribute("persist", true);
		sc.setAttribute("css", CssDAO.getInstance().findByPk(id));
		sc.getRequestDispatcher("/admin/css_edit.jsp").forward(request, response);
		
		log.debug("edit: void");
	}
	
	/**
	 * Edit css
	 */
	private void edit(String userId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("edit({}, {}, {})", new Object[] { userId, request, response });
		ServletContext sc = getServletContext();
		long id = WebUtils.getLong(request, "css_id");
		
		sc.setAttribute("action", WebUtils.getString(request, "action"));
		sc.setAttribute("persist", true);
		sc.setAttribute("css", CssDAO.getInstance().findByPk(id));
		sc.getRequestDispatcher("/admin/css_edit.jsp").forward(request, response);		
		
		log.debug("edit: void");
	}
	
	/**
	 * Create css
	 */
	private void create(String userId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("create({}, {}, {})", new Object[] { userId, request, response });
		ServletContext sc = getServletContext();
		
		sc.setAttribute("action", WebUtils.getString(request, "action"));
		sc.setAttribute("persist", true);
		sc.setAttribute("css", null);
		sc.getRequestDispatcher("/admin/css_edit.jsp").forward(request, response);
		
		log.debug("create: void");
	}
	
	/**
	 * Css list
	 */
	private void list(String userId, HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException, DatabaseException {
		log.debug("list({}, {}, {})", new Object[] { userId, request, response });
		ServletContext sc = getServletContext();
		sc.setAttribute("cssList", CssDAO.getInstance().findAll());
		sc.getRequestDispatcher("/admin/css_list.jsp").forward(request, response);
		log.debug("list: void");
	}
}