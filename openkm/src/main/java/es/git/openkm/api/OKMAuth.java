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

package es.git.openkm.api;

import java.util.Collection;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.UserAlreadyLoggerException;
import es.git.openkm.module.AuthModule;
import es.git.openkm.module.ModuleManager;

public class OKMAuth implements AuthModule {
	private static Logger log = LoggerFactory.getLogger(OKMAuth.class);
	private static OKMAuth instance = new OKMAuth();

	private OKMAuth() {}
	
	public static OKMAuth getInstance() {
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#login(java.lang.String, java.lang.String)
	 */
	public String login(String user, String pass) throws UserAlreadyLoggerException, 
			AccessDeniedException, RepositoryException {
		log.debug("login("+user+", "+pass+")");
		AuthModule am = ModuleManager.getAuthModule();
		String token = am.login(user, pass);
		log.debug("login: "+token);
		return token;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#login()
	 */
	public String login() throws UserAlreadyLoggerException, AccessDeniedException, 
			RepositoryException {
		log.debug("login()");
		AuthModule am = ModuleManager.getAuthModule();
		String token = am.login();
		log.debug("login: "+token);
		return token;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#logout(java.lang.String)
	 */
	public void logout(String token) throws AccessDeniedException, RepositoryException {
		log.debug("logout("+token+")");
		AuthModule am = ModuleManager.getAuthModule();
		am.logout(token);
		log.debug("logout: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#grantUser(java.lang.String, java.lang.String, java.lang.String, int, boolean)
	 */
	public void grantUser(String token, String nodePath, String user, int permissions, boolean recursive) 
			throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("grantUser("+token+", "+nodePath+", "+user+", "+permissions+")");
		AuthModule am = ModuleManager.getAuthModule();
		am.grantUser(token, nodePath, user, permissions, recursive);
		log.debug("grantUser: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#revokeUser(java.lang.String, java.lang.String, java.lang.String, int, boolean)
	 */
	public void revokeUser(String token, String nodePath, String user, int permissions, boolean recursive) 
			throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("revokeUser("+token+", "+nodePath+", "+user+", "+permissions+")");
		AuthModule am = ModuleManager.getAuthModule();
		am.revokeUser(token, nodePath, user, permissions, recursive);
		log.debug("revokeUser: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#getGrantedUsers(java.lang.String, java.lang.String)
	 */
	public HashMap<String, Byte> getGrantedUsers(String token, String nodePath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("getGrantedUsers("+token+", "+nodePath+")");
		AuthModule am = ModuleManager.getAuthModule();
		HashMap<String, Byte> grantedUsers = am.getGrantedUsers(token, nodePath);
		log.debug("getGrantedUsers: "+grantedUsers);
		return grantedUsers;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#grantRole(java.lang.String, java.lang.String, java.lang.String, int, boolean)
	 */
	public void grantRole(String token, String nodePath, String role, int permissions, boolean recursive)
			throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("grantRole("+token+", "+nodePath+", "+role+", "+permissions+")");
		AuthModule am = ModuleManager.getAuthModule();
		am.grantRole(token, nodePath, role, permissions, recursive);
		log.debug("grantRole: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#revokeRole(java.lang.String, java.lang.String, java.lang.String, int, boolean)
	 */
	public void revokeRole(String token, String nodePath, String user, int permissions, boolean recursive)
			throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("revokeRole("+token+", "+nodePath+", "+user+", "+permissions+")");
		AuthModule am = ModuleManager.getAuthModule();
		am.revokeRole(token, nodePath, user, permissions, recursive);
		log.debug("revokeRole: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#getGrantedRoles(java.lang.String, java.lang.String)
	 */
	public HashMap<String, Byte> getGrantedRoles(String token, String nodePath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("getGrantedRoles("+token+", "+nodePath+")");
		AuthModule am = ModuleManager.getAuthModule();
		HashMap<String, Byte> grantedRoles = am.getGrantedRoles(token, nodePath);
		log.debug("getGrantedRoles: "+grantedRoles);
		return grantedRoles;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#getUsers(java.lang.String)
	 */
	public Collection<String> getUsers(String token) throws RepositoryException {
		log.debug("getUsers("+token+")");
		AuthModule am = ModuleManager.getAuthModule();
		Collection<String> users = am.getUsers(token);
		log.debug("getUsers: "+users);
		return users;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#getRoles(java.lang.String)
	 */
	public Collection<String> getRoles(String token) throws RepositoryException {
		log.debug("getRoles("+token+")");
		AuthModule am = ModuleManager.getAuthModule();
		Collection<String> roles = am.getRoles(token);
		log.debug("getRoles: "+roles);
		return roles;
	}
}
