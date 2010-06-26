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

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.dao.MimeTypeDAO;
import com.openkm.dao.bean.MimeType;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtil;

/**
 * Mime type management servlet
 */
public class MimeTypeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(MimeTypeServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			
			if (action.equals("create")) {
				ServletContext sc = getServletContext();
				MimeType mt = new MimeType();
				sc.setAttribute("action", action);
				sc.setAttribute("extensions", null);
				sc.setAttribute("mt", mt);
				sc.getRequestDispatcher("/admin/mime_edit.jsp").forward(request, response);
			} else if (action.equals("edit")) {
				ServletContext sc = getServletContext();
				int mtId = WebUtil.getInt(request, "mt_id");
				MimeType mt = MimeTypeDAO.findByPk(mtId);
				String extensions = "";
				
				for (String ext : mt.getExtensions()) {
					extensions += ext + " ";
				}
				
				sc.setAttribute("action", action);
				sc.setAttribute("extensions", extensions.trim());
				sc.setAttribute("mt", mt);
				sc.getRequestDispatcher("/admin/mime_edit.jsp").forward(request, response);
			} else if (action.equals("delete")) {
				ServletContext sc = getServletContext();
				int mtId = WebUtil.getInt(request, "mt_id");
				MimeType mt = MimeTypeDAO.findByPk(mtId);
				String extensions = "";
				
				for (String ext : mt.getExtensions()) {
					extensions += ext + " ";
				}
				
				sc.setAttribute("action", action);
				sc.setAttribute("extensions", extensions.trim());
				sc.setAttribute("mt", mt);
				sc.getRequestDispatcher("/admin/mime_edit.jsp").forward(request, response);
			} else if (action.equals("reset")) {
				MimeTypeDAO.deleteAll();
				Config.resetMimeTypes();
				list(session, request, response);
			} else {
				list(session, request, response);
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
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
				MimeType mt = new MimeType();
				
				for (Iterator<FileItem> it = items.iterator(); it.hasNext();) {
					FileItem item = it.next();
					
					if (item.isFormField()) {
						if (item.getFieldName().equals("action")) {
							action = item.getString("UTF-8");
						} else if (item.getFieldName().equals("mt_id")) {
							mt.setId(Integer.parseInt(item.getString("UTF-8")));
						} else if (item.getFieldName().equals("mt_name")) {
							mt.setName(item.getString("UTF-8"));
						} else if (item.getFieldName().equals("mt_active")) {
							mt.setActive(true);
						} else if (item.getFieldName().equals("mt_extensions")) {
							String[] extensions = item.getString("UTF-8").split(" ");
							for (int i=0; i<extensions.length; i++) {
								mt.getExtensions().add(extensions[i]);
							}
						}
					} else {
						is = item.getInputStream();
						mt.setImageMime(Config.mimeTypes.getContentType(item.getName()));
						mt.setImageData(IOUtils.toByteArray(is));
						is.close();
					}
				}
			
				if (action.equals("create")) {
					MimeTypeDAO.create(mt);
					Config.loadMimeTypes();
					
					// Activity log
					UserActivity.log(session.getUserID(), "ADMIN_MIME_TYPE_CREATE", null, mt.toString());
					list(session, request, response);
				} else if (action.equals("edit")) {
					MimeTypeDAO.update(mt);
					Config.loadMimeTypes();
					
					// Activity log
					UserActivity.log(session.getUserID(), "ADMIN_MIME_TYPE_EDIT", Integer.toString(mt.getId()), mt.toString());
					list(session, request, response);
				} else if (action.equals("delete")) {
					MimeTypeDAO.delete(mt.getId());
					Config.loadMimeTypes();
					
					// Activity log
					UserActivity.log(session.getUserID(), "ADMIN_MIME_TYPE_DELETE", Integer.toString(mt.getId()), null);
					list(session, request, response);
				}
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (FileUploadException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} finally {
			JCRUtils.logout(session);
		}
	}

	/**
	 * List registered mime types
	 */
	private void list(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("list({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		sc.setAttribute("mimeTypes", MimeTypeDAO.findAll());
		sc.getRequestDispatcher("/admin/mime_list.jsp").forward(request, response);
		log.debug("list: void");
	}
}
