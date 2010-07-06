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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.LoginException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.bean.Permission;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMAuthService;
import com.openkm.frontend.client.util.RoleComparator;
import com.openkm.frontend.client.util.UserComparator;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMAuthServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMAuthServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMAuthServlet extends OKMRemoteServiceServlet implements OKMAuthService {
	private static Logger log = LoggerFactory.getLogger(OKMAuthServlet.class);
	private static final long serialVersionUID = 2638205115826644606L;
	
	@Override
	public void logout() throws OKMException {
		log.debug("logout()");
		updateSessionManager();
		try {
			OKMAuth.getInstance().logout();
			getThreadLocalRequest().getSession().invalidate();
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}
		log.debug("logout: void");
	}
	
	@Override
	public Map<String, Byte> getGrantedRoles(String nodePath) throws OKMException {
		log.debug("getGrantedRoles({})", nodePath);
		Map<String, Byte> hm = new HashMap<String, Byte>();
		updateSessionManager();
		
		try {
			hm = OKMAuth.getInstance().getGrantedRoles(nodePath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		}  catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getGrantedRoles: {}", hm);
		return hm;
	}
	
	@Override
	public Map<String, Byte> getGrantedUsers(String nodePath) throws OKMException {
		log.debug("getGrantedUsers({})", nodePath);
		Map<String, Byte> hm = new HashMap<String, Byte>();
		updateSessionManager();
		
		try {
			hm = OKMAuth.getInstance().getGrantedUsers(nodePath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		}  catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getGrantedUsers: {}", hm);
		return hm;
	}
	
	@Override
	public String getRemoteUser() {
		log.debug("getRemoteUser()");
		String user = getThreadLocalRequest().getRemoteUser();
		log.debug("getRemoteUser: {}", user);
		return user;
	}
	
	@Override
	public List<String> getUngrantedUsers(String nodePath) throws OKMException {
		log.debug("getUngrantedUsers({})", nodePath);
		List<String> userList = new ArrayList<String>();
		updateSessionManager();
		
		try {
			Collection<String> col = OKMAuth.getInstance().getUsers();
			Collection<String> grantedUsers = OKMAuth.getInstance().getGrantedUsers(nodePath).keySet();
			
			for (Iterator<String> it = col.iterator(); it.hasNext();){
				String user = it.next();
				
				if (!grantedUsers.contains(user)) {
					userList.add(user);
				}
			}
			
			Collections.sort(userList, UserComparator.getInstance());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getUngrantedUsers: {}", userList);
		return userList;
	}
	
	@Override
	public List<String> getUngrantedRoles(String nodePath) throws OKMException {
		log.debug("getUngrantedRoles({})", nodePath);
		List<String> roleList = new ArrayList<String>();
		updateSessionManager();
		
		try {
			Collection<String> col = OKMAuth.getInstance().getRoles();
			Collection<String> grantedRoles = OKMAuth.getInstance().getGrantedRoles(nodePath).keySet();
			
			//Not add rols that are granted
			for (Iterator<String> it = col.iterator(); it.hasNext();){
				String rol = it.next();
				
				// Always removing UserRole and AdminRole ( must be only used as connection grant not assigned to repository )
				if (!grantedRoles.contains(rol) && !rol.equals(Config.DEFAULT_USER_ROLE) && !rol.equals(Config.DEFAULT_ADMIN_ROLE)) {
					roleList.add(rol);
				}
			}
			
			Collections.sort(roleList, RoleComparator.getInstance());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		}  catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("getUngrantedRoles: {}", roleList);
		return roleList;
	}
	
	@Override
	public List<String> getFilteredUngrantedUsers(String nodePath, String filter) throws OKMException {
		log.debug("getFilteredUngrantedUsers({})", nodePath);
		List<String> userList = new ArrayList<String>();
		updateSessionManager();
		
		try {
			Collection<String> col = OKMAuth.getInstance().getUsers();
			Collection<String> grantedUsers = OKMAuth.getInstance().getGrantedUsers(nodePath).keySet();
			
			for (Iterator<String> it = col.iterator(); it.hasNext();){
				String user = it.next();
				
				if (!grantedUsers.contains(user) && user.toLowerCase().startsWith(filter.toLowerCase())) {
					userList.add(user);
				}
			}
			
			Collections.sort(userList, UserComparator.getInstance());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		}  catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		}  catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getFilteredUngrantedUsers: {}", userList);
		return userList;
	}
	
	@Override
	public List<String> getFilteredUngrantedRoles(String nodePath, String filter) throws OKMException {
		log.debug("getFilteredUngrantedRoles({})", nodePath);
		List<String> roleList = new ArrayList<String>();
		updateSessionManager();
		
		try {
			Collection<String> col = OKMAuth.getInstance().getRoles();
			Collection<String> grantedRoles = OKMAuth.getInstance().getGrantedRoles(nodePath).keySet();
			
			//Not add rols that are granted
			for (Iterator<String> it = col.iterator(); it.hasNext();){
				String rol = it.next();
				
				// Always removing UserRole and AdminRole ( must be only used as connection grant not assigned to repository )
				if (!grantedRoles.contains(rol) && rol.toLowerCase().startsWith(filter.toLowerCase()) &&
					!rol.equals(Config.DEFAULT_USER_ROLE) && !rol.equals(Config.DEFAULT_ADMIN_ROLE)) {
					roleList.add(rol);
				}
			}
			
			Collections.sort(roleList, RoleComparator.getInstance());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		}  catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("getFilteredUngrantedRoles: {}", roleList);
		return roleList;
	}
	
	@Override
	public void grantUser(String path, String user, int permissions, boolean recursive) throws OKMException {
		log.debug("grantUser({}, {}, {}, {})", new Object[] { path, user, permissions, recursive });
		updateSessionManager();
		
		try {
			OKMAuth.getInstance().grantUser(path, user, permissions, recursive);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("grantUser: void");
	}
	
	@Override
	public void revokeUser(String path, String user, boolean recursive) throws OKMException {
		log.debug("revokeUser({}, {}, {})", new Object[] { path, user, recursive });
		updateSessionManager();
		
		try {
			OKMAuth oKMAuth = OKMAuth.getInstance();
			oKMAuth.revokeUser(path, user, Permission.READ, recursive);
			oKMAuth.revokeUser(path, user, Permission.WRITE, recursive);
			//oKMAuth.revokeUser(path, user, Permission.REMOVE);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("revokeUser: void");
	}
	
	@Override
	public void revokeUser(String path, String user, int permissions, boolean recursive) throws OKMException {
		log.debug("revokeUser({}, {}, {}, {})", new Object[] { path, user, permissions, recursive });
		updateSessionManager();
		
		try {
			OKMAuth.getInstance().revokeUser(path, user, permissions, recursive);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("revokeUser: void");
	}
		
	@Override
	public void grantRole(String path, String role, int permissions, boolean recursive) throws OKMException  {
		log.debug("grantRole({}, {}, {}, {})", new Object[] { path, role, permissions, recursive });
		updateSessionManager();
		
		try {
			OKMAuth.getInstance().grantRole(path, role, permissions, recursive);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("grantRole: void");
	}
	
	@Override
	public void revokeRole(String path, String role, boolean recursive) throws OKMException {
		log.debug("revokeRole({}, {}, {})", new Object[] { path, role, recursive });
		updateSessionManager();
		
		try {
			if (!(Config.SYSTEM_DEMO && path.equals("/okm:root"))) {
				OKMAuth oKMAuth = OKMAuth.getInstance();
				oKMAuth.revokeRole(path, role, Permission.READ, recursive);
				oKMAuth.revokeRole(path, role, Permission.WRITE, recursive);
				//oKMAuth.revokeRole(path, user, Permission.REMOVE);
			}
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("revokeRole: void");
	}

	@Override
	public void revokeRole(String path, String role, int permissions, boolean recursive) throws OKMException {
		log.debug("revokeRole({}, {}, {}, {})", new Object[] { path, role, permissions, recursive });
		updateSessionManager();
		
		try {
			if (!(Config.SYSTEM_DEMO && path.equals("/okm:root"))) {
				OKMAuth.getInstance().revokeRole(path, role, permissions, recursive);
			}
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("revokeRole: void");
	}

	@Override
	public void keepAlive() throws OKMException {
		log.debug("keepAlive()");
		updateSessionManager();
		Session session = null;
						
		try {
			session = JCRUtils.getSession();
			
			// Activity log
			UserActivity.log(session.getUserID(), "KEEP_ALIVE", null, null);
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
				
		log.debug("keepAlive: void");
	}
	
	@Override
	public List<String> getAllUsers() throws OKMException {
		log.debug("getAllUsers()");
		List<String> userList = new ArrayList<String>();
		updateSessionManager();
		
		try {
			Collection<String> col = OKMAuth.getInstance().getUsers();
			
			for (Iterator<String> it = col.iterator(); it.hasNext();){
				String user = it.next();
				userList.add(user);
			}
			
			Collections.sort(userList, UserComparator.getInstance());
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PrincipalAdapterException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getAllUsers: {}", userList);
		return userList;
	}
	
	@Override
	public List<String> getAllRoles() throws OKMException {
		log.debug("getAllRoles()");
		List<String> roleList = new ArrayList<String>();
		updateSessionManager();
		
		try {
			Collection<String> col = OKMAuth.getInstance().getRoles();
			
			for (Iterator<String> it = col.iterator(); it.hasNext();){
				String rol = it.next();
				if (!rol.equals(Config.DEFAULT_USER_ROLE) && !rol.equals(Config.DEFAULT_ADMIN_ROLE)) {
					roleList.add(rol);
				}
			}
			
			Collections.sort(roleList, RoleComparator.getInstance());
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PrincipalAdapterException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getAllRoles: {}", roleList);
		return roleList;
	}
	
	@Override
	public List<String> getFilteredAllUsers(String filter) throws OKMException {
		log.debug("getFilteredAllUsers()");
		List<String> userList = new ArrayList<String>();
		updateSessionManager();
		
		try {
			Collection<String> col = OKMAuth.getInstance().getUsers();
			
			for (Iterator<String> it = col.iterator(); it.hasNext();){
				String user = it.next();
				if (user.toLowerCase().startsWith(filter.toLowerCase())) {
					userList.add(user);
				}
			}
			
			Collections.sort(userList, UserComparator.getInstance());
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PrincipalAdapterException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getFilteredAllUsers: {}", userList);
		return userList;
	}
	
	@Override
	public List<String> getFilteredAllRoles(String filter) throws OKMException {
		log.debug("getFilteredAllRoles()");
		List<String> roleList = new ArrayList<String>();
		updateSessionManager();
		
		try {
			Collection<String> col = OKMAuth.getInstance().getRoles();
			
			for (Iterator<String> it = col.iterator(); it.hasNext();){
				String rol = it.next();
				if (!rol.equals(Config.DEFAULT_USER_ROLE) && !rol.equals(Config.DEFAULT_ADMIN_ROLE) &&
					rol.toLowerCase().startsWith(filter.toLowerCase())) {
					roleList.add(rol);
				}
			}
			
			Collections.sort(roleList, RoleComparator.getInstance());
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_PrincipalAdapterException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getFilteredAllRoles: {}", roleList);
		return roleList;
	}
}
