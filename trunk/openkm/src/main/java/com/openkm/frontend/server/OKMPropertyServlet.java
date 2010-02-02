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

import com.openkm.api.OKMProperty;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.VersionException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMPropertyService;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMPropertyServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMPropertyServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMPropertyServlet extends OKMRemoteServiceServlet implements OKMPropertyService {
	private static Logger log = LoggerFactory.getLogger(OKMPropertyServlet.class);
	private static final long serialVersionUID = 1138063389446959876L;
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyService#addCategory(java.lang.String, java.lang.String)
	 */
	public void addCategory(String nodePath, String category) throws OKMException {
		log.debug("addCategory(nodePath:"+nodePath+" category:"+category+")");
		String token = getToken();
		try {
			OKMProperty.getInstance().addCategory(token, nodePath, category);
		} catch (VersionException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_Version), e.getMessage());
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		log.debug("addCategory: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyService#removeCategory(java.lang.String, java.lang.String)
	 */
	public void removeCategory(String nodePath, String category) throws OKMException {
		log.debug("removeCategory(nodePath:"+nodePath+" category:"+category+")");
		String token = getToken();
		try {
			OKMProperty.getInstance().removeCategory(token, nodePath, category);
		} catch (VersionException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_Version), e.getMessage());
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		log.debug("removeCategory: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyService#addKeyword(java.lang.String, java.lang.String)
	 */
	public void addKeyword(String nodePath, String keyword) throws OKMException {
		log.debug("addKeyword(nodePath:"+nodePath+" keyword:"+keyword+")");
		String token = getToken();
		try {
			OKMProperty.getInstance().addKeyword(token, nodePath, keyword);
		} catch (VersionException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_Version), e.getMessage());
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		log.debug("addKeyword: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyService#removeKeyword(java.lang.String, java.lang.String)
	 */
	public void removeKeyword(String nodePath, String keyword) throws OKMException {
		log.debug("removeKeyword(nodePath:"+nodePath+" keyword:"+keyword+")");
		String token = getToken();
		try {
			OKMProperty.getInstance().removeKeyword(token, nodePath, keyword);
		} catch (VersionException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_Version), e.getMessage());
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		log.debug("addKeyword: void");
	}

}