/**


 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2012  Paco Avila & Josep Llort
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.core.MimeTypeConfig;
import com.openkm.dao.OmrDAO;
import com.openkm.dao.bean.Omr;
import com.openkm.util.FileUtils;
import com.openkm.util.OMRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtils;

/**
 * omr servlet
 */
public class OmrServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(OmrServlet.class);
	private static final int FILE_TEMPLATE = 1;
	private static final int FILE_ASC= 2;
	private static final int FILE_CONFIG = 3;
	private static final int FILE_FIELDS = 4;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
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
			} else if (action.equals("downloadFile")) {
				downloadFile(userId, request, response);
			} else if (action.equals("editAsc")) {
			//	editAscFile(userId, request, response);
			} else if (action.equals("editFields")) {
			//	editFieldsFile(userId, request, response);
			} else {
				list(userId, request, response);
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		}
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doPost({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = "";
		String userId = request.getRemoteUser();
		updateSessionManager(request);
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				String fileName = null;
				InputStream is = null;
				FileItemFactory factory = new DiskFileItemFactory(); 
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(request);
				Omr om = new Omr();
				for (Iterator<FileItem> it = items.iterator(); it.hasNext();) {
					FileItem item = it.next();
					
					if (item.isFormField()) {
						if (item.getFieldName().equals("action")) {
							action = item.getString("UTF-8");
						} else if (item.getFieldName().equals("om_id")) {
							om.setId(Integer.parseInt(item.getString("UTF-8")));
						} else if (item.getFieldName().equals("om_name")) {
							om.setName(item.getString("UTF-8"));
						} else if (item.getFieldName().equals("om_active")) {
							om.setActive(true);
						}
					} else {
						is = item.getInputStream();
						fileName = item.getName();
					}
				}
			
				if (action.equals("create")) {
					// Store locally template file to be used later
					File tmp = FileUtils.createTempFile();
					FileUtils.copy(is, tmp);
					// Store template file
					om.setTemplateFileName(FilenameUtils.getName(fileName));
					om.setTemplateFileMime(MimeTypeConfig.mimeTypes.getContentType(fileName));
					om.setTemplateFilContent(IOUtils.toByteArray(is));
					is.close();
					// Create training files
					Map<String, File> trainingMap = OMRUtils.trainingTemplate(tmp);
					File ascFile = trainingMap.get(OMRUtils.ASC_FILE);
					File configFile = trainingMap.get(OMRUtils.CONFIG_FILE);
					String baseFileName = om.getTemplateFileName()+".";
					// Store asc file
					om.setAscFileName(baseFileName+"asc");
					om.setAscFileMime("text/plain");
					is = new FileInputStream(ascFile);
					om.setAscFileContent(IOUtils.toByteArray(is));
					is.close();
					// Store config file
					om.setConfigFileName(baseFileName+"config");
					om.setConfigFileMime("text/plain");
					is = new FileInputStream(configFile);
					om.setConfigFileContent(IOUtils.toByteArray(is));
					is.close();
					// Delete temporal files
					FileUtils.deleteQuietly(tmp);
					FileUtils.deleteQuietly(ascFile);
					FileUtils.deleteQuietly(configFile);
					
					long id = OmrDAO.create(om);
					// Activity log
					UserActivity.log(userId, "ADMIN_OMR_CREATE", Long.toString(id), null, om.toString());
					list(userId, request, response);
				} else if (action.equals("edit")) {
					Omr tmp = OmrDAO.findByPk(om.getId());
					tmp.setActive(om.isActive());
					tmp.setTemplateFilContent(om.getTemplateFileContent());
					tmp.setTemplateFileMime(om.getTemplateFileMime());
					tmp.setTemplateFileName(om.getTemplateFileName());
					tmp.setName(om.getName());
					OmrDAO.updateTemplate(tmp);
					
					// Activity log
					UserActivity.log(userId, "ADMIN_OMR_EDIT", Long.toString(om.getId()), null, om.toString());
					list(userId, request, response);
				} else if (action.equals("delete")) {
					OmrDAO.delete(om.getId());
					// Activity log
					UserActivity.log(userId, "ADMIN_OMR_DELETE", Long.toString(om.getId()), null, null);
					list(userId, request, response);
				}
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} catch (FileUploadException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		}
	}
	
	/**
	 * List omr templates
	 */
	private void list(String userId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("list({}, {}, {})", new Object[] { userId, request, response });
		ServletContext sc = getServletContext();
		List<Omr> list = OmrDAO.findAll();
		sc.setAttribute("omr", list);
		sc.getRequestDispatcher("/admin/omr_list.jsp").forward(request, response);
		log.debug("list: void");
	}
	
	/**
	 * New omr template
	 */
	private void create(String userId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
			DatabaseException {
		log.debug("create({}, {}, {})", new Object[] { userId, request, response });
		
		ServletContext sc = getServletContext();
		Omr om = new Omr();
		sc.setAttribute("action", WebUtils.getString(request, "action"));
		sc.setAttribute("om", om);
		sc.getRequestDispatcher("/admin/omr_edit.jsp").forward(request, response);
		
		log.debug("create: void");
	}
	
	/**
	 * edit type record
	 */
	private void edit(String userId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
			DatabaseException {
		log.debug("edit({}, {}, {})", new Object[] { userId, request, response });
		
		ServletContext sc = getServletContext();
		int omId = WebUtils.getInt(request, "om_id");
		sc.setAttribute("action", WebUtils.getString(request, "action"));
		sc.setAttribute("om", OmrDAO.findByPk(omId));
		sc.getRequestDispatcher("/admin/omr_edit.jsp").forward(request, response);
		
		log.debug("edit: void");
	}
	
	/**
	 * delete type record
	 */
	private void delete(String userId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
			DatabaseException {
		log.debug("delete({}, {}, {})", new Object[] { userId, request, response });
		
		ServletContext sc = getServletContext();
		int omId = WebUtils.getInt(request, "om_id");
		sc.setAttribute("action", WebUtils.getString(request, "action"));
		sc.setAttribute("om", OmrDAO.findByPk(omId));
		sc.getRequestDispatcher("/admin/omr_edit.jsp").forward(request, response);
		
		log.debug("delete: void");
	}
	
	/**
	 * download file
	 */
	private void downloadFile(String userId, HttpServletRequest request, HttpServletResponse response)
			throws DatabaseException, IOException {
		log.debug("downloadFile({}, {}, {})", new Object[] { userId, request, response });
		int omId = WebUtils.getInt(request, "om_id");
		int fileType = WebUtils.getInt(request, "type");
		Omr omr = OmrDAO.findByPk(omId);
	
		
		if (omr != null && fileType>=FILE_TEMPLATE && fileType<=FILE_FIELDS) {
			OutputStream os = response.getOutputStream();
			try {
				byte[] fileContent = null;
				switch (fileType) {
					case FILE_TEMPLATE:
						fileContent = omr.getTemplateFileContent();
						WebUtils.prepareSendFile(request, response, omr.getTemplateFileName(), omr.getTemplateFileMime(), true);
						break;
					case FILE_ASC:
						fileContent = omr.getAscFileContent();
						WebUtils.prepareSendFile(request, response, omr.getAscFileName(), omr.getAscFileMime(), true);
						break;
					case FILE_CONFIG:
						fileContent = omr.getConfigFileContent();
						WebUtils.prepareSendFile(request, response, omr.getConfigFileName(), omr.getConfigFileMime(), true);
						break;
					case FILE_FIELDS:
						fileContent = omr.getFieldsFileContent();
						WebUtils.prepareSendFile(request, response, omr.getFieldsFileName(), omr.getFieldsFileMime(), true);
						break;
				}
				if (fileContent!=null) {
					response.setContentLength(fileContent.length);
					os.write(fileContent);
					os.flush();
				}
			} finally {
				IOUtils.closeQuietly(os);
			}
		}
		
		log.debug("downloadFile: void");
	}
}