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

import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.bean.Bookmark;

public interface BookmarkModule {

	/**
	 * Create a new folder in the repository.
	 * 
	 * @param nodePath A node path to be bookmarked.
	 * @param name The name of the bookmark.
	 * @thows PathNotFoundException If there is no node with this nodePath.
	 * @throws ItemExistsException If there is already a bookmark in the
	 * repository with the same name.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Bookmark add(String token, String nodePath, String name) throws AccessDeniedException,
			PathNotFoundException, RepositoryException, DatabaseException;

	/**
	 * Get info from a previously created bookmark
	 * 
	 * @param token 
	 * @param bmId The unique bookmark id.
	 * @return The bookmark object.
	 */
	public Bookmark get(String token, int bmId) throws AccessDeniedException, RepositoryException,
			DatabaseException;
	
	/**
	 * Remove a bookmark
	 * 
	 * @param name The bookmark name to be deleted. 
	 * @throws PathNotFoundException If the indicated bookmark doesn't exist.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void remove(String token, int bmId) throws AccessDeniedException, RepositoryException,
			DatabaseException;
	
	/**
	 * Rename a previous stored bookmark.
	 * 
	 * @param name The actual bookmark name.
	 * @param newName The new bookmark name.
	 * @thows PathNotFoundException If there is no node with this name.
	 * @throws ItemExistsException If there is already a bookmark in the
	 * repository with the same name.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Bookmark rename(String token, int bmId, String newName) throws AccessDeniedException, 
			RepositoryException, DatabaseException;
	
	/**
	 * Retrive an user bookmark collection.
	 * 
	 * @return All the user bookmarks
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public List<Bookmark> getAll(String token) throws RepositoryException, DatabaseException;
}
