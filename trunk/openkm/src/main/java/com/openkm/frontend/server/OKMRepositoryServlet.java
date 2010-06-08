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

package com.openkm.frontend.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMRepository;
import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMRepositoryService;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMRepositoryServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMRepositoryServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMRepositoryServlet extends OKMRemoteServiceServlet implements OKMRepositoryService {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(OKMRepositoryServlet.class);

	@Override
	public String getUpdateMessage() throws OKMException {
		log.debug("getUpdateMessage()");
		String token = getToken();
		String msg = "";

		try {
			msg = OKMRepository.getInstance().getUpdateMessage(token);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getUpdateMessage: {}", msg);
		return msg;
	}
	
	@Override
	public GWTFolder getPersonalFolder() throws OKMException {
		log.debug("getPersonalFolder()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			// Administrators user can see all user homes
			if (getThreadLocalRequest().isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
				folder = OKMRepository.getInstance().getPersonalFolderBase();
			} else {
				folder = OKMRepository.getInstance().getPersonalFolder(token);
			}
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getPersonalFolder: {}", gWTFolder);
		return gWTFolder;
	}
	
	@Override
	public GWTFolder getTemplatesFolder() throws OKMException {
		log.debug("getTemplateFolder()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			folder =  OKMRepository.getInstance().getTemplatesFolder(token);
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getTemplatesFolder: {}", gWTFolder);
		return gWTFolder;
	}
	
	@Override
	public void purgeTrash() throws OKMException {
		log.debug("purgeTrash()");
		String token = getToken();
		
		try {
			OKMRepository.getInstance().purgeTrash(token);
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e ) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("purgeTrash: void");
	}
	
	@Override
	public GWTFolder getTrashFolder() throws OKMException {
		log.debug("getTrashFolder()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			// Administrators user can see all user homes
			if (getThreadLocalRequest().isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
				folder = OKMRepository.getInstance().getTrashFolderBase();
			} else {
				folder = OKMRepository.getInstance().getTrashFolder(token);
			}
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getTrashFolder: {}", gWTFolder);
		return gWTFolder;
	}
	
	@Override
	public GWTFolder getRootFolder() throws OKMException {
		log.debug("getRootFolder()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			folder = OKMRepository.getInstance().getRootFolder(token);
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getRootFolder: {}", gWTFolder);
		return gWTFolder;
	}
		
	@Override
	public GWTFolder getMailFolder() throws OKMException {
		log.debug("getMailFolder()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			// Administrators user can see all user homes
			if (getThreadLocalRequest().isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
				folder = OKMRepository.getInstance().getMailFolderBase();
			} else {
				folder = OKMRepository.getInstance().getMailFolder(token);
			}
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getMailFolder: {}", gWTFolder);
		return gWTFolder;
	}
	
	@Override
	public GWTFolder getThesaurusFolder() throws OKMException {
		log.debug("getThesaurusFolder()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			folder =  OKMRepository.getInstance().getThesaurusFolder(token);
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getThesaurusFolder: {}", gWTFolder);
		return gWTFolder;
	}
	
	@Override
	public GWTFolder getCategoriesFolder() throws OKMException {
		log.debug("getCategoriesFolder()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			folder =  OKMRepository.getInstance().getCategoriesFolder(token);
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getCategoriesFolder: {}", gWTFolder);
		return gWTFolder;
	}
	
	@Override
	public String getPathByUUID(String uuid) throws OKMException {
		log.debug("getPathByUUID()");
		String token = getToken();
		String path = "";
		try {
			path = OKMRepository.getInstance().getPath(token, uuid);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRepositoryService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} 
		return path;
	}
}
