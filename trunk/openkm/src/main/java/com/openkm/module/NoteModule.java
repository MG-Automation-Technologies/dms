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

import com.openkm.bean.Note;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;

public interface NoteModule {
	/**
	 * Add a note to a document
	 * 
	 * @param docPath The path that identifies an unique document.
	 * @param text The message text
	 * @throws LockException A locked document can't be modified.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this folder because of lack of permissions.
	 * @throws RepositoryException If there is any problem.
	 */
	public void add(String token, String nodePath, String comment) throws LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Remove a note from a document
	 * 
	 * @param notePath The path that identifies an unique document.
	 * @throws LockException A locked document can't be modified.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this folder because of lack of permissions.
	 * @throws RepositoryException If there is any problem.
	 */
	public void remove(String token, String notePath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Get a note from a document
	 * 
	 * @param notePath The path that identifies an unique document.
	 * @throws LockException A locked document can't be modified.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this folder because of lack of permissions.
	 * @throws RepositoryException If there is any problem.
	 */
	public Note get(String token, String notePath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException;

	/**
	 * Set a note from a document
	 * 
	 * @param notePath The path that identifies an unique document.
	 * @throws LockException A locked document can't be modified.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this folder because of lack of permissions.
	 * @throws RepositoryException If there is any problem.
	 */
	public void set(String token, String notePath, String comment) throws LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException;

	/**
	 * Retrieve a list of child documents from an existing folder.
	 * 
	 * @param fldPath The path that identifies an unique folder.
	 * @return A Collection with the child documents.
	 * @throws PathNotFoundException If there is no folder in this repository path.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public List<Note> list(String token, String nodePath) throws PathNotFoundException,
			RepositoryException, DatabaseException;
}
