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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.bean.Permission;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.SessionManager;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMAuthService;
import com.openkm.frontend.client.util.RoleComparator;
import com.openkm.frontend.client.util.UserComparator;
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
	
	public void logout() throws OKMException {
		log.debug("logout()");
		
		try {
			OKMAuth.getInstance().logout(getToken());
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		}  catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		}  catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_General), e.getMessage());
		}

		getThreadLocalRequest().getSession().invalidate();			
		
		log.info("***** LOGOUT ****");
		log.debug("logout: void");
	}
	

	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#getGrantedRoles(java.lang.String)
	 */
	public HashMap<String, Byte> getGrantedRoles(String nodePath) throws OKMException {
		log.debug("getGrantedRoles("+nodePath+")");
		
		String token = getToken();
		HashMap<String, Byte> hm = new HashMap<String, Byte>();
		try {
			hm = OKMAuth.getInstance().getGrantedRoles(token, nodePath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		}  catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getGrantedRoles: "+hm);
		
		return hm;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#getGrantedUsers(java.lang.String)
	 */
	public HashMap<String, Byte> getGrantedUsers(String nodePath) throws OKMException {
		log.debug("getGrantedUsers("+nodePath+")");
		String token = getToken();
		HashMap<String, Byte> hm = new HashMap<String, Byte>();
		
		try {
			hm = OKMAuth.getInstance().getGrantedUsers(token, nodePath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		}  catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_General), e.getMessage());
		}

		
		log.debug("getGrantedUsers: "+hm);
		return hm;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#getRemoteUser()
	 */
	public String getRemoteUser() {
		log.debug("getRemoteUser()");
		String user = getThreadLocalRequest().getRemoteUser();
		
		log.debug("getRemoteUser: "+user);
		return user;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#getUngrantedUsers(java.lang.String)
	 */
	public List<String> getUngrantedUsers(String nodePath) throws OKMException {
		log.debug("getUngrantedUsers("+ nodePath +")");
		List<String> userList = new ArrayList<String>(); 
		String token = getToken();
		Map<String,Byte> hm = new HashMap<String,Byte> ();
		
		try {
			Collection<String> col = OKMAuth.getInstance().getUsers(token);
			hm = OKMAuth.getInstance().getGrantedUsers(token, nodePath);
			
			for (Iterator<String> it = col.iterator(); it.hasNext();){
				String user = it.next();
				boolean found = false;
				
				// Not add users that are granted
				for (Iterator<String> ith = hm.keySet().iterator(); ith.hasNext(); ) {
					if ((ith.next()).equals(user)){
						found = true;
					}
				}
				
				if (!found) {
					userList.add(user);
				}
			}
			
			Collections.sort(userList, UserComparator.getInstance());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		}  catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		}  catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getUngrantedUsers: "+userList);
		return userList;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#getUngrantedRoles(java.lang.String)
	 */
	public List<String> getUngrantedRoles(String nodePath) throws OKMException {
		log.debug("getUngrantedRoles("+ nodePath +")");
		List<String> roleList = new ArrayList<String>(); 
		String token = getToken();
		HashMap<String,Byte>  hm = new HashMap<String,Byte> ();
		
		try {
			Collection<String> col = OKMAuth.getInstance().getRoles(token);
			hm = OKMAuth.getInstance().getGrantedRoles(token, nodePath);
			
			//Not add rols that are granted
			for (Iterator<String> it = col.iterator(); it.hasNext();){
				boolean found = false;
				String rol = it.next();
				
				for (Iterator<String> ith = hm.keySet().iterator(); ith.hasNext();){
					if ((ith.next()).equals(rol)){
						found = true;
					}
				}
				
				if (!found) {
					roleList.add(rol);
				}
			}
			
			Collections.sort(roleList, RoleComparator.getInstance());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		}  catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("getUngrantedRoles: "+roleList);
		return roleList;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#grantUser(java.lang.String, java.lang.String, int)
	 */
	public void grantUser(String path, String user, int permissions, boolean recursive) throws OKMException {
		log.debug("grantUser("+ path +","+ user +","+ permissions +")");
		String token = getToken();
		
		try {
			OKMAuth.getInstance().grantUser(token, path, user, permissions, recursive);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("grantUser: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#removeUser(java.lang.String, java.lang.String)
	 */
	public void revokeUser(String path, String user, boolean recursive) throws OKMException {
		log.debug("revokeUser("+ path +","+ user +")");
		String token = getToken();
		OKMAuth oKMAuth = OKMAuth.getInstance();
		
		try {
			oKMAuth.revokeUser(token, path, user, Permission.READ, recursive);
			oKMAuth.revokeUser(token, path, user, Permission.WRITE, recursive);
			//oKMAuth.revokeUser(token, path, user, Permission.REMOVE);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("revokeUser: void");
	}
	

	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#revokeUser(java.lang.String, java.lang.String, int)
	 */
	public void revokeUser(String path, String user, int permissions, boolean recursive) throws OKMException {
		log.debug("revokeUser("+ path +","+ user +","+ permissions +")");
		String token = getToken();
		
		try {
			OKMAuth.getInstance().revokeUser(token, path, user, permissions, recursive);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("revokeUser: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#grantRole(java.lang.String, java.lang.String, int)
	 */
	public void grantRole(String path, String role, int permissions, boolean recursive) throws OKMException  {
		log.debug("grantRole("+ path +","+ role +","+ permissions +")");
		String token = getToken();
		
		try {
			OKMAuth.getInstance().grantRole(token, path, role, permissions, recursive);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("grantRole: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#removeRole(java.lang.String, java.lang.String)
	 */
	public void revokeRole(String path, String role, boolean recursive) throws OKMException {
		log.debug("revokeRole("+ path +","+ role +")");
		String token = getToken();
		OKMAuth oKMAuth = OKMAuth.getInstance();
		
		try {
			oKMAuth.revokeRole(token, path, role, Permission.READ, recursive);
			oKMAuth.revokeRole(token, path, role, Permission.WRITE, recursive);
			//oKMAuth.revokeRole(token, path, user, Permission.REMOVE);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("revokeRole: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#revokeRole(java.lang.String, java.lang.String, int)
	 */
	public void revokeRole(String path, String role, int permissions, boolean recursive) throws OKMException {
		log.debug("revokeRole("+ path +","+ role +","+ permissions +")");
		String token = getToken();
		
		try {
			OKMAuth.getInstance().revokeRole(token, path, role, permissions, recursive);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_PathNotFound), e.getMessage());		 
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("revokeRole: void");
	}


	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#keepAlive()
	 */
	public void keepAlive() throws OKMException {
		log.debug("keepAlive()");
		String token = getToken();
		Session session = SessionManager.getInstance().get(token);
		
		// Activity log
		UserActivity.log(session, "KEEP_ALIVE", null, null);
		log.debug("keepAlive: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMAuthService#getAllUsers()
	 */
	public List<String> getAllUsers() throws OKMException {
		log.debug("getAllUsers()");
		String token = getToken();
		List<String> userList = new ArrayList<String>();
		
		try {
			Collection<String> col = OKMAuth.getInstance().getUsers(token);
			for (Iterator<String> it = col.iterator(); it.hasNext();){
				String user = it.next();
				userList.add(user);
			}
			
			Collections.sort(userList, UserComparator.getInstance());
		}  catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServlet, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getAllUsers: "+userList);
		return userList;
	}
}
