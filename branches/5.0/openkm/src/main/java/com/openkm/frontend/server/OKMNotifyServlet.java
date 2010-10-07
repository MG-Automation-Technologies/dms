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

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.api.OKMNotification;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMNotifyService;
import com.openkm.principal.PrincipalAdapterException;

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
	
	@Override
	public void subscribe(String nodePath) throws OKMException  {
		log.debug("subscribe({})", nodePath);
		updateSessionManager();
		
		try {
			OKMNotification.getInstance().subscribe(null, nodePath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) { 
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) { 
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) { 
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("subscribe: void");
	}
	
	@Override
	public void unsubscribe(String nodePath) throws OKMException {
		log.debug("subscribe({})", nodePath);
		updateSessionManager();
		
		try {
			OKMNotification.getInstance().unsubscribe(null, nodePath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) { 
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) { 
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) { 
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("subscribe: void");
	}
	
	@Override
	public void notify(String docPath, String users, String roles, String message, boolean attachment) throws 
			OKMException {
		log.debug("notify({}, {}, {}, {})", new Object[] { docPath, users, roles, message, attachment });
		updateSessionManager();
		
		try {
			List<String> userNames = Arrays.asList(users.split(","));
			List<String> roleNames = Arrays.asList(roles.split(","));
			
			for (String role : roleNames) {
				List<String> usersInRole = OKMAuth.getInstance().getUsersByRole(null, role);
				
				for (String user : usersInRole) {
					if (!userNames.contains(user)) {
						userNames.add(user);
					}
				}
			}
			
			OKMNotification.getInstance().notify(null, docPath, userNames, message, attachment);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) { 
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) { 
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMNotifyService, ErrorCode.CAUSE_PrincipalAdapterException), e.getMessage());
		}
		
		log.debug("notify: void");
	}
}
