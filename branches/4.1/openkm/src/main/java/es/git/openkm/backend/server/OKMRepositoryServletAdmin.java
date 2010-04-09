/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package es.git.openkm.backend.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMRepository;
import es.git.openkm.backend.client.OKMException;
import es.git.openkm.backend.client.bean.GWTFolder;
import es.git.openkm.backend.client.config.ErrorCode;
import es.git.openkm.backend.client.service.OKMRepositoryService;
import es.git.openkm.bean.Folder;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.util.WarUtils;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMRepositoryServletAdmin"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMRepositoryServletAdmin"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMRepositoryServletAdmin extends OKMRemoteServiceServletAdmin implements OKMRepositoryService {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(OKMRepositoryServletAdmin.class);
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMRepositoryService#getTemplate()
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
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServletAdmin, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServletAdmin, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServletAdmin, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getTemplate: "+gWTFolder);
		return gWTFolder;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMRepositoryService#getRoot()
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
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServletAdmin, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServletAdmin, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServletAdmin, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getRoot: "+gWTFolder);
		return gWTFolder;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMRepositoryService#getUuid()
	 */
	public String getUuid() throws OKMException {
		log.debug("getUuid()");
		String token = getToken();
		String uuid = "";
		
		try {
			uuid =  OKMRepository.getInstance().getUuid(token);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_RepositoryServletAdmin, ErrorCode.CAUSE_Repository), e.getMessage());
		} 
		
		log.debug("getUuid: ");
		return uuid;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMRepositoryService#getAppVersion()
	 */
	public String getAppVersion() throws OKMException {
		return WarUtils.getAppVersion().toString();
	}
}
