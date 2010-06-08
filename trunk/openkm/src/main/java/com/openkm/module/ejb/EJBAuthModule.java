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

package com.openkm.module.ejb;

import java.util.HashMap;
import java.util.List;

import com.openkm.core.AccessDeniedException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UserAlreadyLoggerException;
import com.openkm.principal.PrincipalAdapterException;

public class EJBAuthModule implements com.openkm.module.AuthModule {

	public String login(String user, String pass) throws AccessDeniedException,
			UserAlreadyLoggerException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public String login() throws AccessDeniedException, UserAlreadyLoggerException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public void logout(String token) throws AccessDeniedException,
			RepositoryException {
		// TODO Auto-generated method stub
	}

	public HashMap<String, Byte> getGrantedUsers(String token, String nodePath) throws AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<String, Byte> getGrantedRoles(String token, String nodePath) throws AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public void grantUser(String token, String nodePath, String user, int permissions, boolean recursive) throws AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public void revokeUser(String token, String nodePath, String user, int permissions, boolean recursive) throws AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public void grantRole(String token, String nodePath, String role, int permissions, boolean recursive) throws AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public void revokeRole(String token, String nodePath, String user, int permissions, boolean recursive) throws AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public List<String> getUsers(String token) throws PrincipalAdapterException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getRoles(String token) throws PrincipalAdapterException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getMails(String token, List<String> users) throws PrincipalAdapterException {
		// TODO Auto-generated method stub
		return null;
	}
}
