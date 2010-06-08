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

package com.openkm.module;

import java.util.List;
import java.util.Map;

import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UserAlreadyLoggerException;
import com.openkm.principal.PrincipalAdapterException;

public interface AuthModule {

	/**
	 * Logins into the repository and gets a token with user info for future API
	 * invocations.
	 * 
	 * @param user User name for login.
	 * @param pass Password for login.
	 * @return A token with authorization info for next API invocations.
	 * @throws AccessDeniedException If authorization fails.
	 * @throws UserAlreadyLoggerException If the user is already logged into the system.  
	 * @throws RepositoryException If there is an error accessing to repository.
	 */
	public void login(String user, String pass) throws UserAlreadyLoggerException,
			AccessDeniedException, RepositoryException, DatabaseException, DatabaseException;

	/**
	 * Logins into the repository and gets a token with user info for future API
	 * invocations.
	 * 
	 * @return A token with authorization info for next API invocations.
	 * @throws AccessDeniedException If authorization fails.
	 * @throws RepositoryException If there is an error accessing to repository.
	 */
	public void login() throws UserAlreadyLoggerException, AccessDeniedException, 
			RepositoryException, DatabaseException;

	/**
	 * Log out from the repository. Invalidates the authorization token.
	 * 
	 * @throws AccessDeniedException If authorization fails.
	 * @throws RepositoryException If there is an error accessing to repository.
	 */
	public void logout() throws AccessDeniedException, RepositoryException, DatabaseException;

	/**
	 * Add user permissions to a node.
	 * 
	 * @param nodePath The complete path to the node.
	 * @param user User name which permissions are changed.
	 * @param permissions A mask with the permissions to be added. 
	 * @param recursive recursive – If the nodePath indicated a folder,
	 * the permissions can be applied recursively.
	 * @throws PathNotFoundException If the node defined by nodePath do not exists.
	 * @throws AccessDeniedException If the authorization information is not valid.
	 * @throws RepositoryException If there is any error accessing to the repository.
	 */
	public void grantUser(String nodePath, String user, int permissions, boolean recursive) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Revoke user permissions from a node.
	 * 
	 * @param nodePath The complete path to the node.
	 * @param user User name which permissions are changed.
	 * @param permissions A mask with the permissions to be removed.
	 * @param recursive If the nodePath indicates a folder, the 
	 * permissions can be revoked recursively.
	 * @throws PathNotFoundException If the node defined by nodePath do not exists.
	 * @throws AccessDeniedException If the authorization information is not valid.
	 * @throws RepositoryException If there is any error accessing to the repository.
	 */
	public void revokeUser(String nodePath, String user, int permissions, boolean recursive) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException;
	
	/** 
	 * Get user permissions from am item (document or folder).
	 * 
	 * @param nodePath The complete path to the node.
	 * @return A hashmap with pairs of user / permissions.
	 * @throws PathNotFoundException If the node defined by nodePath do not exists.
	 * @throws AccessDeniedException If the authorization information is not valid.
	 * @throws RepositoryException If there is any error accessing to the repository.
	 */
	public Map<String, Byte> getGrantedUsers(String nodePath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException;

	/**
	 * Grant role permissions for a node.
	 * 
	 * @param nodePath The complete path to the node.
	 * @param role Role name which permissions are changed.
	 * @param permissions A mask with the permissions to be added.
	 * @param recursive If the nodePath indicates a folder, the permissions can 
	 * be applied recursively.
	 * @throws PathNotFoundException If the node defined by nodePath do not exists.
	 * @throws AccessDeniedException If the authorization information is not valid.
	 * @throws RepositoryException If there is any error accessing to the repository.
	 */
	public void grantRole(String nodePath, String role, int permissions, boolean recursive) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Revoke role permissions from a node.
	 * 
	 * @param nodePath The complete path to the node.
	 * @param role Role name which permissions are changed.
	 * @param permissions A mask with the permissions to be removed.
	 * @param recursive If the nodePath indicates a folder, the 
	 * permissions can be applied recursively.
	 * @throws PathNotFoundException If the node defined by nodePath do not exists.
	 * @throws AccessDeniedException If the authorization information is not valid.
	 * @throws RepositoryException If there is any error accessing to the repository.
	 */
	public void revokeRole(String nodePath, String role, int permissions, boolean recursive) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException;
	
	/** 
	 * Get roles permissions from am item (document or folder).
	 * 
	 * @param nodePath The complete path to the node.
	 * @return A hashmap with pairs of role / permissions.
	 * @throws PathNotFoundException If the node defined by nodePath do not exists.
	 * @throws AccessDeniedException If the authorization information is not valid.
	 * @throws RepositoryException If there is any error accessing to the repository.
	 */
	public Map<String, Byte> getGrantedRoles(String nodePath) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Retrieves a list of repository users
	 * 
	 * @return A collection of repository users.
	 * @throws RepositoryException If there is any error retrieving the users list.
	 */
	public List<String> getUsers() throws PrincipalAdapterException;
	
	/**
	 * Retrieves a list of repository roles.
	 * 
	 * @return A collection of repository roles.
	 * @throws RepositoryException If there is any error retrieving the roles list.
	 */
	public List<String> getRoles() throws PrincipalAdapterException;
	
	/**
	 * Retrieves the user mails from an user list.
	 * 
	 * @param users A collection of user names.
	 * @return A collection of user mails.
	 * @throws RepositoryException If there is any error retrieving the mail list.
	 */
	public List<String> getMails(List<String> users) throws PrincipalAdapterException;
}
