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

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UserAlreadyLoggerException;
import com.openkm.module.AuthModule;
import com.openkm.module.ModuleManager;
import com.openkm.principal.PrincipalAdapterException;

public class OKMAuth implements AuthModule {
	private static Logger log = LoggerFactory.getLogger(OKMAuth.class);
	private static OKMAuth instance = new OKMAuth();

	private OKMAuth() {}
	
	public static OKMAuth getInstance() {
		return instance;
	}
	
	@Override
	public String login(String user, String pass) throws UserAlreadyLoggerException, 
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("login({}, {})", user, pass);
		AuthModule am = ModuleManager.getAuthModule();
		String token = am.login(user, pass);
		log.debug("login: {}", token);
		return token;
	}

	@Override
	public String login() throws UserAlreadyLoggerException, AccessDeniedException, 
			RepositoryException, DatabaseException {
		log.debug("login()");
		AuthModule am = ModuleManager.getAuthModule();
		String token = am.login();
		log.debug("login: {}", token);
		return token;
	}

	@Override
	public void logout(String token) throws AccessDeniedException, RepositoryException, 
			DatabaseException {
		log.debug("logout()");
		AuthModule am = ModuleManager.getAuthModule();
		am.logout(token);
		log.debug("logout: void");
	}

	@Override
	public void grantUser(String token, String nodePath, String user, int permissions, boolean recursive) 
			throws PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("grantUser({}, {}, {})", new Object[] { nodePath, user, permissions });
		AuthModule am = ModuleManager.getAuthModule();
		am.grantUser(token, nodePath, user, permissions, recursive);
		log.debug("grantUser: void");
	}

	@Override
	public void revokeUser(String token, String nodePath, String user, int permissions, boolean recursive) 
			throws PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("revokeUser({}, {}, {})", new Object[] { nodePath, user, permissions });
		AuthModule am = ModuleManager.getAuthModule();
		am.revokeUser(token, nodePath, user, permissions, recursive);
		log.debug("revokeUser: void");
	}

	@Override
	public Map<String, Byte> getGrantedUsers(String token, String nodePath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("getGrantedUsers({})", nodePath);
		AuthModule am = ModuleManager.getAuthModule();
		Map<String, Byte> grantedUsers = am.getGrantedUsers(token, nodePath);
		log.debug("getGrantedUsers: {}", grantedUsers);
		return grantedUsers;
	}

	@Override
	public void grantRole(String token, String nodePath, String role, int permissions, boolean recursive)
			throws PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("grantRole({}, {}, {})", new Object[] { nodePath, role, permissions });
		AuthModule am = ModuleManager.getAuthModule();
		am.grantRole(token, nodePath, role, permissions, recursive);
		log.debug("grantRole: void");
	}

	@Override
	public void revokeRole(String token, String nodePath, String user, int permissions, boolean recursive)
			throws PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("revokeRole({}, {}, {})", new Object[] { nodePath, user, permissions });
		AuthModule am = ModuleManager.getAuthModule();
		am.revokeRole(token, nodePath, user, permissions, recursive);
		log.debug("revokeRole: void");
	}

	@Override
	public Map<String, Byte> getGrantedRoles(String token, String nodePath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("getGrantedRoles({})", nodePath);
		AuthModule am = ModuleManager.getAuthModule();
		Map<String, Byte> grantedRoles = am.getGrantedRoles(token, nodePath);
		log.debug("getGrantedRoles: {}", grantedRoles);
		return grantedRoles;
	}

	@Override
	public List<String> getUsers(String token) throws PrincipalAdapterException {
		log.debug("getUsers()");
		AuthModule am = ModuleManager.getAuthModule();
		List<String> users = am.getUsers(token);
		log.debug("getUsers: {}", users);
		return users;
	}

	@Override
	public List<String> getRoles(String token) throws PrincipalAdapterException {
		log.debug("getRoles()");
		AuthModule am = ModuleManager.getAuthModule();
		List<String> roles = am.getRoles(token);
		log.debug("getRoles: {}", roles);
		return roles;
	}

	@Override
	public List<String> getMails(String token, List<String> users) throws PrincipalAdapterException {
		log.debug("getMails({})", users);
		AuthModule am = ModuleManager.getAuthModule();
		List<String> mails = am.getMails(token, users);
		log.debug("getMails: {}", mails);
		return mails;
	}
}
