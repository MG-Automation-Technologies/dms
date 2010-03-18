/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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

import java.io.IOException;
import java.util.Collection;

import es.git.openkm.bean.ContentInfo;
import es.git.openkm.bean.Folder;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.AccessDeniedException;

public interface FolderModule {

	/**
	 * Create a new folder in the repository.
	 * 
	 * @param token The session authorization token.
	 * @param fld A folder object with the new folder properties.
	 * @return A folder object with the new created folder properties.
	 * @throws PathNotFoundException If the parent folder doesn't exist.
	 * @throws ItemExistsException If there is already a folder in the
	 * repository with the same name in the same path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the parent folder because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Folder create(String token, Folder fld) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException;

	/**
	 * Obtains properties from a previously created folder.
	 * 
	 * @param token The session authorization token.
	 * @param fldPath The path that identifies an unique folder. 
	 * @return A folder object with the selected folder properties.
	 * @throws PathNotFoundException If the indicated folder doesn't exist.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Folder getProperties(String token, String fldPath) throws PathNotFoundException,
			RepositoryException;
	
	/**
	 * Delete a folder the repository. It is a logical delete,
	 * so really is moved to the user trash and can be restored.
	 * 
	 * @param token The session authorization token.
	 * @param fldPath The path that identifies an unique folder.  
	 * @throws LockException Can't delete a folder with locked documents.
	 * @throws PathNotFoundException If there is no folder in the repository in this path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the folder because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void delete(String token, String fldPath) throws LockException, 
			PathNotFoundException, AccessDeniedException, RepositoryException;

	/**
	 * Deletes definitively a folder from the repository. It is a phisical delete, so
	 * the folder can't be restored.
	 * 
	 * @param token The session authorization token.
	 * @param fldPath The path that identifies an unique folder.  
	 * @throws LockException Can't delete a folder with locked documents.
	 * @throws PathNotFoundException If there is no folder in the repository in this path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the folder because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void purge(String token, String fldPath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException;

	/**
	 * Rename a folder in the repository.
	 * 
	 * @param token The session authorization token.
	 * @param fldPath The path that identifies an unique folder.  
	 * @param newName The new folder name.
	 * @return A folder object with the new folder properties.
	 * @throws PathNotFoundException If there is no folder in the repository in this path.
	 * @throws ItemExistsException If there is already a folder in the
	 * repository with the same name in the same path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the folder because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Folder rename(String token, String fldPath, String newName) throws 
			PathNotFoundException, ItemExistsException, AccessDeniedException, 
			RepositoryException;

	/**
	 * Move a folder to another location in the repository.
	 * 
	 * @param token The session authorization token.
	 * @param fldPath The path that identifies an unique folder.
	 * @param dstPath The path of the destination folder.
	 * @throws PathNotFoundException If the dstPath does not exists.
	 * @throws ItemExistsException If there is already a folder in the
	 * destination folder with the same name.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the parent folder or the destination folder
	 * because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void move(String token, String fldPath, String dstPath) throws 
			PathNotFoundException, ItemExistsException, AccessDeniedException, 
			RepositoryException;

	/**
	 * Copy a folder to another location in the repository.
	 * 
	 * @param token The session authorization token.
	 * @param fldPath The path that identifies an unique folder.
	 * @param dstPath The path of the destination folder.
	 * @throws PathNotFoundException If the dstPath does not exists.
	 * @throws ItemExistsException If there is already a folder in the
	 * destination folder with the same name.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the parent folder or the destination folder
	 * because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void copy(String token, String fldPath, String dstPath) throws 
			PathNotFoundException, ItemExistsException, AccessDeniedException, 
			RepositoryException, IOException;

	/**
	 * Retrieve a list of child folders from an existing one.
	 * 
	 * @param token The session authorization token.
	 * @param fldPath The path that identifies an unique folder.
	 * @return A Collection with the child folders.
	 * @throws PathNotFoundException If there is no folder in the repository in this path
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Collection<Folder> getChilds(String token, String fldPath) throws PathNotFoundException, 
			RepositoryException;

	/**
	 * Retrive the content info of the folder: number of folders, number of documents, and total size.
	 * 
	 * @param token The session authorization token
	 * @param fldPath The path that identifies an unique folder.
	 * @return A ContentInfo with the number of folder, number of documents and total size.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this folder because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 */
	public ContentInfo getContentInfo(String token, String fldPath) throws 
		AccessDeniedException, RepositoryException, PathNotFoundException;
	
	/**
	 * Test if a folder path is valid.
	 * 
	 * @param token String with user autorization info.
	 * @param fldPath The path that identifies an unique folder.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this folder because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 */
	public boolean isValid(String token, String fldPath) throws 
		PathNotFoundException, AccessDeniedException, RepositoryException;
}
