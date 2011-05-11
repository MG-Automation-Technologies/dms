/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package es.git.openkm.module;

import java.util.Collection;
import java.util.HashMap;

import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.UserAlreadyLoggerException;

public interface AuthModule {

	/**
	 * Logins into the repository and gets a token with user info for future API
	 * invocations.
	 * 
	 * @param user User name for login.
	 * @param pass Password for login.
	 * @return A token with autorization info for next API invocations.
	 * @throws AccessDeniedException If autorization fails.
	 * @throws UserAlreadyLoggerException If the user is already logged into the system.  
	 * @throws RepositoryException If there is an error accessing to repository.
	 */
	public String login(String user, String pass) throws UserAlreadyLoggerException,
			AccessDeniedException, RepositoryException;

	/**
	 * Logins into the repository and gets a token with user info for future API
	 * invocations.
	 * 
	 * @return A token with autorization info for next API invocations.
	 * @throws AccessDeniedException If autorization fails.
	 * @throws RepositoryException If there is an error accessing to repository.
	 */
	public String login() throws UserAlreadyLoggerException, AccessDeniedException, 
			RepositoryException;

	/**
	 * Log out from the repostory. Invalidates the autorization token.
	 * 
	 * @param token The session authorization token.
	 * @throws AccessDeniedException If autorization fails.
	 * @throws RepositoryException If there is an error accessing to repository.
	 */
	public void logout(String token) throws AccessDeniedException,
			RepositoryException;

	/**
	 * Add user permissions to a node.
	 * 
	 * @param token The session authorization token.
	 * @param nodePath The complete path to the node.
	 * @param user User name which permissions are changed.
	 * @param permissions A mask with the permissions to be added. 
	 * @param recursive recursive – If the nodePath indicated a folder,
	 * the permissions can be applied recursively.
	 * @throws PathNotFoundException If the node defined by nodePath do not exists.
	 * @throws AccessDeniedException If the token authorization information is not valid.
	 * @throws RepositoryException If there is any error accessing to the repository.
	 */
	public void grantUser(String token, String nodePath, String user, int permissions, boolean recursive) 
			throws PathNotFoundException, AccessDeniedException, RepositoryException;
	
	/**
	 * Revoke user permissions from a node.
	 * 
	 * @param token The session authorization token.
	 * @param nodePath The complete path to the node.
	 * @param user User name which permissions are changed.
	 * @param permissions A mask with the permissions to be removed.
	 * @param recursive If the nodePath indicates a folder, the 
	 * permissions can be revoked recursively.
	 * @throws PathNotFoundException If the node defined by nodePath do not exists.
	 * @throws AccessDeniedException If the token authorization information is not valid.
	 * @throws RepositoryException If there is any error accessing to the repository.
	 */
	public void revokeUser(String token, String nodePath, String user, int permissions, boolean recursive)
			throws PathNotFoundException, AccessDeniedException, RepositoryException;
	
	/** 
	 * Get user permissions from am item (document or folder).
	 * 
	 * @param token The session authorization token.
	 * @param nodePath The complete path to the node.
	 * @return A hashmap with pairs of user / permissions.
	 * @throws PathNotFoundException If the node defined by nodePath do not exists.
	 * @throws AccessDeniedException If the token authorization information is not valid.
	 * @throws RepositoryException If there is any error accessing to the repository.
	 */
	public HashMap<String, Byte> getGrantedUsers(String token, String nodePath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException;

	/**
	 * Grant role permissions for a node.
	 * 
	 * @param token The session authorization token.
	 * @param nodePath The complete path to the node.
	 * @param role Role name which permissions are changed.
	 * @param permissions A mask with the permissions to be added.
	 * @param recursive If the nodePath indicates a folder, the permissions can 
	 * be applied recursively.
	 * @throws PathNotFoundException If the node defined by nodePath do not exists.
	 * @throws AccessDeniedException If the token authorization information is not valid.
	 * @throws RepositoryException If there is any error accessing to the repository.
	 */
	public void grantRole(String token, String nodePath, String role, int permissions, boolean recursive)
			throws PathNotFoundException, AccessDeniedException, RepositoryException;
	
	/**
	 * Revoke role permissions from a node.
	 * 
	 * @param token The session authorization token.
	 * @param nodePath The complete path to the node.
	 * @param role Role name which permissions are changed.
	 * @param permissions A mask with the permissions to be removed.
	 * @param recursive If the nodePath indicates a folder, the 
	 * permissions can be applied recursively.
	 * @throws PathNotFoundException If the node defined by nodePath do not exists.
	 * @throws AccessDeniedException If the token authorization information is not valid.
	 * @throws RepositoryException If there is any error accessing to the repository.
	 */
	public void revokeRole(String token, String nodePath, String role, int permissions, boolean recursive) throws PathNotFoundException, AccessDeniedException, RepositoryException;
	
	/** 
	 * Get roles permissions from am item (document or folder).
	 * 
	 * @param token The session authorization token.
	 * @param nodePath The complete path to the node.
	 * @return A hashmap with pairs of role / permissions.
	 * @throws PathNotFoundException If the node defined by nodePath do not exists.
	 * @throws AccessDeniedException If the token authorization information is not valid.
	 * @throws RepositoryException If there is any error accessing to the repository.
	 */
	public HashMap<String, Byte> getGrantedRoles(String token, String nodePath) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException;
	
	/**
	 * Retrieves a list of repository users
	 * 
	 * @param token The session authorization token.
	 * @return A collection of repository users.
	 * @throws RepositoryException If there is any error retrieving the users list.
	 */
	public Collection<String> getUsers(String token) throws RepositoryException;
	
	/**
	 * Retrieves a list of repository roles.
	 * 
	 * @param token The session authorization token.
	 * @return A collection of repository roles.
	 * @throws RepositoryException If there is any error retrieving the roles list.
	 */
	public Collection<String> getRoles(String token) throws RepositoryException;
	
	/**
	 * Retrieves the user mails from an user list.
	 * 
	 * @param token The session authorization token.
	 * @param users A collection of user names.
	 * @return A collection of user mails.
	 * @throws RepositoryException If there is any error retrieving the mail list.
	 */
	public Collection<String> getMails(String token, Collection<String> users) throws RepositoryException;
}