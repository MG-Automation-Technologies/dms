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

import java.util.Collection;

import com.openkm.bean.Bookmark;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;

public interface BookmarkModule {

	/**
	 * Create a new folder in the repository.
	 * 
	 * @param token The session authorization token.
	 * @param nodePath A node path to be bookmarked.
	 * @thows PathNotFoundException If there is no node with this nodePath.
	 * @throws ItemExistsException If there is already a bookmark in the
	 * repository with the same name.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Bookmark add(String token, String nodePath, String name) throws AccessDeniedException, 
			PathNotFoundException, ItemExistsException, RepositoryException;

	/**
	 * Obtains properties from a previously created folder.
	 * 
	 * @param token The session authorization token.
	 * @param name The bookamark name to be deleted. 
	 * @throws PathNotFoundException If the indicated bookmark doesn't exist.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void remove(String token, String name) throws AccessDeniedException, PathNotFoundException, 
			RepositoryException;
	
	/**
	 * Rename a previous stored bookmark.
	 * 
	 * @param token The session authorization token.
	 * @param name The actual bookmark name.
	 * @param newName The new bookmark name.
	 * @thows PathNotFoundException If there is no node with this name.
	 * @throws ItemExistsException If there is already a bookmark in the
	 * repository with the same name.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Bookmark rename(String token, String name, String newName) throws AccessDeniedException,
			PathNotFoundException, ItemExistsException, RepositoryException;
	
	/**
	 * Retrive an user bookmark collection.
	 * 
	 * @param token The session authorization token
	 * @return All the user bookmarks
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Collection<Bookmark> getAll(String token) throws RepositoryException;
	
	/**
	 * Set the user default home node
	 * 
	 * @param token The session authorization token
	 * @param nodePath A node path to be set as user home folder.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void setUserHome(String token, String nodePath) throws AccessDeniedException,
			RepositoryException;
	
	/**
	 * Get the user default home node
	 * 
	 * @param token The session authorization token
	 * @throws PathNotFoundException If the indicated bookmark doesn't exist.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Bookmark getUserHome(String token) throws PathNotFoundException,
			RepositoryException;
}
