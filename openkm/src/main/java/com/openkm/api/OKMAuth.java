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

package com.openkm.api;

import java.util.Collection;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.UserAlreadyLoggerException;
import com.openkm.module.AuthModule;
import com.openkm.module.ModuleManager;

public class OKMAuth implements AuthModule {
	private static Logger log = LoggerFactory.getLogger(OKMAuth.class);
	private static OKMAuth instance = new OKMAuth();

	private OKMAuth() {}
	
	public static OKMAuth getInstance() {
		return instance;
	}
	
	@Override
	public String login(String user, String pass) throws UserAlreadyLoggerException, 
			AccessDeniedException, RepositoryException {
		log.debug("login("+user+", "+pass+")");
		AuthModule am = ModuleManager.getAuthModule();
		String token = am.login(user, pass);
		log.debug("login: "+token);
		return token;
	}

	@Override
	public String login() throws UserAlreadyLoggerException, AccessDeniedException, 
			RepositoryException {
		log.debug("login()");
		AuthModule am = ModuleManager.getAuthModule();
		String token = am.login();
		log.debug("login: "+token);
		return token;
	}

	@Override
	public void logout(String token) throws AccessDeniedException, RepositoryException {
		log.debug("logout("+token+")");
		AuthModule am = ModuleManager.getAuthModule();
		am.logout(token);
		log.debug("logout: void");
	}

	@Override
	public void grantUser(String token, String nodePath, String user, int permissions, boolean recursive) 
			throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("grantUser("+token+", "+nodePath+", "+user+", "+permissions+")");
		AuthModule am = ModuleManager.getAuthModule();
		am.grantUser(token, nodePath, user, permissions, recursive);
		log.debug("grantUser: void");
	}

	@Override
	public void revokeUser(String token, String nodePath, String user, int permissions, boolean recursive) 
			throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("revokeUser("+token+", "+nodePath+", "+user+", "+permissions+")");
		AuthModule am = ModuleManager.getAuthModule();
		am.revokeUser(token, nodePath, user, permissions, recursive);
		log.debug("revokeUser: void");
	}

	@Override
	public HashMap<String, Byte> getGrantedUsers(String token, String nodePath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("getGrantedUsers("+token+", "+nodePath+")");
		AuthModule am = ModuleManager.getAuthModule();
		HashMap<String, Byte> grantedUsers = am.getGrantedUsers(token, nodePath);
		log.debug("getGrantedUsers: "+grantedUsers);
		return grantedUsers;
	}

	@Override
	public void grantRole(String token, String nodePath, String role, int permissions, boolean recursive)
			throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("grantRole("+token+", "+nodePath+", "+role+", "+permissions+")");
		AuthModule am = ModuleManager.getAuthModule();
		am.grantRole(token, nodePath, role, permissions, recursive);
		log.debug("grantRole: void");
	}

	@Override
	public void revokeRole(String token, String nodePath, String user, int permissions, boolean recursive)
			throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("revokeRole("+token+", "+nodePath+", "+user+", "+permissions+")");
		AuthModule am = ModuleManager.getAuthModule();
		am.revokeRole(token, nodePath, user, permissions, recursive);
		log.debug("revokeRole: void");
	}

	@Override
	public HashMap<String, Byte> getGrantedRoles(String token, String nodePath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("getGrantedRoles("+token+", "+nodePath+")");
		AuthModule am = ModuleManager.getAuthModule();
		HashMap<String, Byte> grantedRoles = am.getGrantedRoles(token, nodePath);
		log.debug("getGrantedRoles: "+grantedRoles);
		return grantedRoles;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.AuthModule#getUsers(java.lang.String)
	 */
	@Override
	public Collection<String> getUsers(String token) throws RepositoryException {
		log.debug("getUsers("+token+")");
		AuthModule am = ModuleManager.getAuthModule();
		Collection<String> users = am.getUsers(token);
		log.debug("getUsers: "+users);
		return users;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.AuthModule#getRoles(java.lang.String)
	 */
	@Override
	public Collection<String> getRoles(String token) throws RepositoryException {
		log.debug("getRoles("+token+")");
		AuthModule am = ModuleManager.getAuthModule();
		Collection<String> roles = am.getRoles(token);
		log.debug("getRoles: "+roles);
		return roles;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.AuthModule#getMails(java.lang.String, java.util.Collection)
	 */
	@Override
	public Collection<String> getMails(String token, Collection<String> users) throws RepositoryException {
		log.debug("getMails("+token+", "+users+")");
		AuthModule am = ModuleManager.getAuthModule();
		Collection<String> mails = am.getMails(token, users);
		log.debug("getMails: "+mails);
		return mails;
	}
}
