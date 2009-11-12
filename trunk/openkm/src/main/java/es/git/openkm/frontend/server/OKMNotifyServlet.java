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

package es.git.openkm.frontend.server;

import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMNotification;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.config.ErrorCode;
import es.git.openkm.frontend.client.service.OKMNotifyService;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMNotifyServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMNotifyServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMNotifyServlet extends OKMRemoteServiceServlet implements OKMNotifyService {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(OKMFolderServlet.class);
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMNotifyService#subscribe(java.lang.String)
	 */
	public void subscribe( String nodePath) throws OKMException  {
		log.debug("subscribe("+nodePath+")");
		String token = getToken();
		
		try {
			OKMNotification.getInstance().subscribe(token, nodePath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) { 
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) { 
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("subscribe: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMNotifyService#unsubscribe(java.lang.String)
	 */
	public void unsubscribe( String nodePath) throws OKMException {
		log.debug("subscribe("+nodePath+")");
		String token = getToken();
		
		try {
			OKMNotification.getInstance().unsubscribe(token, nodePath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) { 
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) { 
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("subscribe: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMNotifyService#notify(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void notify(String docPath, String users, String message) throws OKMException {
		log.debug("notify("+docPath+")");
		String token = getToken();
		
		try {
			Collection col = Arrays.asList(users.split(","));
			OKMNotification.getInstance().notify(token, docPath, col, message);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) { 
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) { 
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_Repository), e.getMessage());
		} 
	}
}
