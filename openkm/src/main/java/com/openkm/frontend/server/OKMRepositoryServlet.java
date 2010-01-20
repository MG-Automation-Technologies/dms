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

package com.openkm.frontend.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMRepository;
import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
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

	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMRepositoryService#getUpdateMessage()
	 */
	public String getUpdateMessage() throws OKMException {
		log.debug("getUpdateMessage()");
		
		String token = getToken();

		try {
			return OKMRepository.getInstance().getUpdateMessage(token);
			
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_General), e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMRepositoryService#getPersonal()
	 */
	public GWTFolder getPersonal() throws OKMException {
		log.debug("getPersonal()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			folder =  OKMRepository.getInstance().getPersonalFolder(token);
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getPersonal: "+gWTFolder);
		return gWTFolder;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMRepositoryService#getTemplate()
	 */
	public GWTFolder getTemplate() throws OKMException {
		log.debug("getTemplate()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			folder =  OKMRepository.getInstance().getTemplatesFolder(token);
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getTemplate: "+gWTFolder);
		return gWTFolder;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMRepositoryService#purgeTrash()
	 */
	public void purgeTrash() throws OKMException {
		log.debug("purgeTrash()");
		String token = getToken();
		
		try {
			OKMRepository.getInstance().purgeTrash(token);
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e ) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("purgeTrash: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMRepositoryService#getTrash()
	 */
	public GWTFolder getTrash() throws OKMException {
		log.debug("getTrash()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			folder = OKMRepository.getInstance().getTrashFolder(token);
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getTrash: "+gWTFolder);
		return gWTFolder;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMRepositoryService#getRoot()
	 */
	public GWTFolder getRoot() throws OKMException {
		log.debug("getRoot()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			folder =  OKMRepository.getInstance().getRootFolder(token);
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getRoot: "+gWTFolder);
		return gWTFolder;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMRepositoryService#getRootPath()
	 */
	public String getRootPath() throws OKMException {
		log.debug("getRootPath()");
		String token = getToken();
		String fldPath = "";
		
		try {
			fldPath = OKMRepository.getInstance().getRootFolder(token).getPath();
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet,ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getRootPath: "+fldPath);
		return fldPath;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMRepositoryService#getMail()
	 */
	public GWTFolder getMail() throws OKMException {
		log.debug("getMail()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			folder =  OKMRepository.getInstance().getMailFolder(token);
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getMail: "+gWTFolder);
		return gWTFolder;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMRepositoryService#getThesaurus()
	 */
	public GWTFolder getThesaurus() throws OKMException {
		log.debug("getThesaurus()");
		String token = getToken();
		Folder folder;
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			folder =  OKMRepository.getInstance().getThesaurusFolder(token);
			gWTFolder = Util.copy(folder);
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServlet, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getThesaurus: "+gWTFolder);
		return gWTFolder;
	}
}
