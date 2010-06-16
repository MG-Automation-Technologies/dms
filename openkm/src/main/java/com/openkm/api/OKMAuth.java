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
	public void login() throws RepositoryException, DatabaseException {
		log.debug("login()");
		AuthModule am = ModuleManager.getAuthModule();
		am.login();
		log.debug("login: void");
	}

	@Override
	public void logout() throws RepositoryException, DatabaseException {
		log.debug("logout()");
		AuthModule am = ModuleManager.getAuthModule();
		am.logout();
		log.debug("logout: void");
	}

	@Override
	public void grantUser(String nodePath, String user, int permissions, boolean recursive) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("grantUser({}, {}, {})", new Object[] { nodePath, user, permissions });
		AuthModule am = ModuleManager.getAuthModule();
		am.grantUser(nodePath, user, permissions, recursive);
		log.debug("grantUser: void");
	}

	@Override
	public void revokeUser(String nodePath, String user, int permissions, boolean recursive) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("revokeUser({}, {}, {})", new Object[] { nodePath, user, permissions });
		AuthModule am = ModuleManager.getAuthModule();
		am.revokeUser(nodePath, user, permissions, recursive);
		log.debug("revokeUser: void");
	}

	@Override
	public Map<String, Byte> getGrantedUsers(String nodePath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("getGrantedUsers({})", nodePath);
		AuthModule am = ModuleManager.getAuthModule();
		Map<String, Byte> grantedUsers = am.getGrantedUsers(nodePath);
		log.debug("getGrantedUsers: {}", grantedUsers);
		return grantedUsers;
	}

	@Override
	public void grantRole(String nodePath, String role, int permissions, boolean recursive) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("grantRole({}, {}, {})", new Object[] { nodePath, role, permissions });
		AuthModule am = ModuleManager.getAuthModule();
		am.grantRole(nodePath, role, permissions, recursive);
		log.debug("grantRole: void");
	}

	@Override
	public void revokeRole(String nodePath, String user, int permissions, boolean recursive) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("revokeRole({}, {}, {})", new Object[] { nodePath, user, permissions });
		AuthModule am = ModuleManager.getAuthModule();
		am.revokeRole(nodePath, user, permissions, recursive);
		log.debug("revokeRole: void");
	}

	@Override
	public Map<String, Byte> getGrantedRoles(String nodePath) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("getGrantedRoles({})", nodePath);
		AuthModule am = ModuleManager.getAuthModule();
		Map<String, Byte> grantedRoles = am.getGrantedRoles(nodePath);
		log.debug("getGrantedRoles: {}", grantedRoles);
		return grantedRoles;
	}

	@Override
	public List<String> getUsers() throws PrincipalAdapterException {
		log.debug("getUsers()");
		AuthModule am = ModuleManager.getAuthModule();
		List<String> users = am.getUsers();
		log.debug("getUsers: {}", users);
		return users;
	}

	@Override
	public List<String> getRoles() throws PrincipalAdapterException {
		log.debug("getRoles()");
		AuthModule am = ModuleManager.getAuthModule();
		List<String> roles = am.getRoles();
		log.debug("getRoles: {}", roles);
		return roles;
	}

	@Override
	public List<String> getUsersByRole(String role) throws PrincipalAdapterException {
		log.debug("getUsersByRole({})", role);
		AuthModule am = ModuleManager.getAuthModule();
		List<String> users = am.getUsersByRole(role);
		log.debug("getUsersByRole: {}", users);
		return users;
	}
	
	@Override
	public List<String> getMails(List<String> users) throws PrincipalAdapterException {
		log.debug("getMails({})", users);
		AuthModule am = ModuleManager.getAuthModule();
		List<String> mails = am.getMails(users);
		log.debug("getMails: {}", mails);
		return mails;
	}
}
