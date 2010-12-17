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

package com.openkm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.jcr.ItemNotFoundException;
import javax.jcr.LoginException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.util.JBPMUtils;

/**
 * Workflow Register Servlet
 */
public class WorkflowRegisterServlet extends BasicSecuredServlet {
	private static Logger log = LoggerFactory.getLogger(WorkflowRegisterServlet.class);
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getPathInfo();
		Session session = null;
		
		try {
			if (action != null && action.length() > 1 && action.indexOf(':') > 0) {
				String[] usrpass = action.substring(1).split(":");
				session = getSession(usrpass[0], usrpass[1]);
			}
						
			if (session != null) {
				handleRequest(request);
			} else {
				response.setContentType("text/plain; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("Missing user credentials");
				out.close();
			}
		} catch (LoginException e) {
			log.warn(e.getMessage(), e);
			response.setHeader("WWW-Authenticate", "Basic realm=\"OpenKM Worflow Register Server\"");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		} catch (ItemNotFoundException e) {
			log.warn(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ItemNotFoundException: "+e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "PathNotFoundException: "+e.getMessage());
		} catch (RepositoryException e) {
			log.warn(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "RepositoryException: "+e.getMessage());
		} catch (FileUploadException e) {
			log.warn(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "FileUploadException: "+e.getMessage());
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "IOException: "+e.getMessage());
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			if (session != null) {
				session.logout();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void handleRequest(HttpServletRequest request) throws FileUploadException, IOException, Exception {
		if (ServletFileUpload.isMultipartContent(request)) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			
			for (Iterator<FileItem> it = items.iterator(); it.hasNext();) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.getContentType().indexOf("application/x-zip-compressed") == -1) {
					throw new Exception("Not a process archive");
				} else {
					log.debug("Deploying process archive " + fileItem.getName());
					ZipInputStream zipInputStream = new ZipInputStream(fileItem.getInputStream());
					JbpmContext jbpmContext = JBPMUtils.getConfig().createJbpmContext();
					ProcessDefinition processDefinition = ProcessDefinition.parseParZipInputStream(zipInputStream);
					log.debug("Created a processdefinition : " + processDefinition.getName());
					jbpmContext.deployProcessDefinition(processDefinition);
					zipInputStream.close();
				}
			}
		}
	}
}
