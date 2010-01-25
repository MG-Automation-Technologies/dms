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

import com.openkm.bean.Folder;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.AccessDeniedException;

public interface RepositoryModule {

	/**
	 * Obtain the root folder of the repository.
	 * 
	 * @param token The session authorization token.
	 * @return A folder object with the repository root node properties.
	 * @throws PathNotFoundException If there is no root folder node in the repository.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Folder getRootFolder(String token) throws PathNotFoundException, RepositoryException;

	/**
	 * Obtains the user trash folder.
	 * 
	 * @param token The session authorization token.
	 * @return A folder object with the user trash node properties.
	 * @throws PathNotFoundException If there is no user trash folder node in the repository.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Folder getTrashFolder(String token) throws PathNotFoundException, RepositoryException;

	/**
	 * Obtain the template folder of the repository.
	 * 
	 * @param token The session authorization token.
	 * @return A folder object with the templates node properties.
	 * @throws PathNotFoundException If there is no templates folder node in the repository.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Folder getTemplatesFolder(String token) throws PathNotFoundException, RepositoryException;

	/**
	 * Obtain the personal documents folder of the repository.
	 * 
	 * @param token The session authorization token.
	 * @return A folder object with the user documents folder node properties.
	 * @throws PathNotFoundException If there is no user documents folder node in the repository.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Folder getPersonalFolder(String token) throws PathNotFoundException, RepositoryException;

	/**
	 * Obtain the personal mails folder of the repository.
	 * 
	 * @param token The session authorization token.
	 * @return A folder object with the user mails folder node properties.
	 * @throws PathNotFoundException If there is no user documents folder node in the repository.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Folder getMailFolder(String token) throws PathNotFoundException, RepositoryException;

	/**
	 * Obtain the thesaurus folder of the repository.
	 * 
	 * @param token The session authorization token.
	 * @return A folder object with the thesaurus folder node properties.
	 * @throws PathNotFoundException If there is no user documents folder node in the repository.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Folder getThesaurusFolder(String token) throws PathNotFoundException, RepositoryException;

	/**
	 * Obtain the categories folder of the repository.
	 * 
	 * @param token The session authorization token.
	 * @return A folder object with the categories folder node properties.
	 * @throws PathNotFoundException If there is no user documents folder node in the repository.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Folder getCategoriesFolder(String token) throws PathNotFoundException, RepositoryException;
	
	/**
	 * Remove all the items in the user trash folder for ever. You can't 
	 * recover this items anymore.
	 * 
	 * @param token The session authorization token.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the user deleted folders and documents because 
	 * of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void purgeTrash(String token) throws AccessDeniedException, RepositoryException;
	
	/**
	 * Get the update message, if any.
	 * 
	 * @param token The session authorization token.
	 * @return A possible update message or simple info for the application.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public String getUpdateMessage(String token) throws RepositoryException;
	
	/**
	 * Get the unique repository identifier
	 * 
	 * @param token The session authorization token
	 * @return The repository UUID
	 * @throws RepositoryException If there is any gegeran repository problem.
	 */
	public String getUuid(String token) throws RepositoryException;
	
	/**
	 * Test if a node path exists
	 * 
	 * @param token The session authorization token
	 * @param path The node path to test 
	 * @return
	 * @throws RepositoryException
	 */
	public boolean hasNode(String token, String path) throws RepositoryException;
}
