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

import java.io.IOException;
import java.util.List;

import com.openkm.bean.Lock;
import com.openkm.bean.Version;
import com.openkm.bean.Wiki;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UserQuotaExceededException;
import com.openkm.core.VersionException;

public interface WikiModule {
	/**
	 * Creates a new wiki in the repository.
	 * 
	 * @param wiki A wiki object with the new wiki properties.
	 * @param content The document content in bytes.
	 * @return A wiki object with the properties of the new created wiki.
	 * @throws PathNotFoundException If the parent folder doesn't exist.
	 * @throws ItemExistsException If there is already a wiki in 
	 * the repository with the same name.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the parent folder because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws IOException An error when inserting document data into the repository.
	 */
	public Wiki create(String token, Wiki wiki, String content) throws ItemExistsException,
			PathNotFoundException, AccessDeniedException, RepositoryException, IOException,
			DatabaseException;
	
	/**
	 * Deletes a wiki from the repository. It is a logical delete,
	 * so really is moved to the user trash and can be restored.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @throws LockException Can't delete a locked node.
	 * @throws PathNotFoundException If there is no wiki in this 
	 * repository path.
	 * @throws AccessDeniedException If there is any security problem:
	 * you can't modify the wiki because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void delete(String token, String wikiPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Rename a wiki in the repository.
	 * 
	 * @param wikiPath The path that identifies an unique document.
	 * @param newName The new folder name.
	 * @return An wiki object with the new wiki properties.
	 * @throws PathNotFoundException If there is no node in this repository path.
	 * @throws ItemExistsException If there is already a node in the
	 * repository with the same name in the same path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the node because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Wiki rename(String token, String wikiPath, String newName) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Obtain wiki properties from the repository.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @return The wiki properties.
	 * @throws PathNotFoundException If there is no wiki in this repository path.
	 * you can't modify the node because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Wiki getProperties(String token, String wikiPath) throws PathNotFoundException,
			RepositoryException, DatabaseException;
	
	/**
	 * Set wiki properties in the repository.
	 * 
	 * @param wiki An wiki object with the properties
	 * @throws VersionException A wiki checked in can't be modified.
	 * @throws LockException A locked node can't be modified.
	 * @throws PathNotFoundException If there is no node in this
	 * repository path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the node because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void setProperties(String token, Wiki wiki) throws VersionException, LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Obtain wiki content from the repository.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @return The content of the wiki file.
	 * @throws PathNotFoundException If there is no node in this
	 * repository path.
	 * @throws RepositoryException If there is any general repository problem.
     * @throws IOException An error when retrieving node data 
     * from the repository.
	 */
	public String getContent(String token, String wikiPath, boolean checkout) throws 
			PathNotFoundException, RepositoryException, IOException, DatabaseException;
	
	/**
	 * Obtain wiki content from the repository.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @param versionId The id of the version to get the content from.
	 * @return The content of the wiki file.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify this node because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 * @throws IOException An error when retrieving node data from the repository.
	 */
	public String getContentByVersion(String token, String wikiPath, String versionId) throws 
			RepositoryException, PathNotFoundException, IOException, DatabaseException;
	
	/**
	 * Set wiki content in the repository.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @param content The new wiki content.
	 * @throws VersionException A node checked in can't be modified.
	 * @throws LockException A locked node can't be modified.
	 * @throws PathNotFoundException If there is no document in this
	 * repository path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the node because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws IOException If there is any error setting the new content.
	 */
	public void setContent(String token, String wikiPath, String content) throws VersionException,
			LockException, PathNotFoundException, AccessDeniedException, RepositoryException,
			IOException, DatabaseException;
	
	/**
	 * Retrieve a list of child wiki from an existing folder.
	 * 
	 * @param fldPath The path that identifies an unique folder.
	 * @return A Collection with the child wikis.
	 * @throws PathNotFoundException If there is no folder in this repository path.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public List<Wiki> getChilds(String token, String fldPath) throws PathNotFoundException,
			RepositoryException, DatabaseException;
	
	/**
	 * Checkout the wiki to edit it. The wiki can't be edited by another
	 * user until it is checked in o the checkout is canceled.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @throws LockException A locked node can't be modified.
	 * @throws PathNotFoundException If there is no node in this repository path.
	 * @throws AccessDeniedException If there is any security problem: you can't modify
	 * the node because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void checkout(String token, String wikiPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Cancel a previous checked out state in a wiki.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @throws LockException A locked node can't be modified.
	 * @throws PathNotFoundException If there is no node in this repository path.
	 * @throws AccessDeniedException If there is any security problem: you can't modify
	 * the node because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void cancelCheckout(String token, String wikiPath) throws LockException, 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Test if a node has been already checked out.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @return True if the wiki is in checked out state, or false if not.
	 * @throws PathNotFoundException If there is no node in this repository path.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public boolean isCheckedOut(String token, String wikiPath) throws PathNotFoundException,
			RepositoryException, DatabaseException;
	
	/**
	 * Check in the wiki to create a new version.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @param comment A comment for this checkin.
	 * @return A version object with the properties of the new generated version.
	 * @throws LockException A locked node can't be modified.
	 * @throws VersionException If the nodes was not previously checked out.
	 * @throws PathNotFoundException If there is no node in this repository path.
	 * @throws AccessDeniedException If there is any security problem: you can't modify
	 * the node because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Version checkin(String token, String wikiPath, String comment) throws LockException,
			VersionException, PathNotFoundException, AccessDeniedException, RepositoryException,
			DatabaseException;
	
	/**
	 * Get the wiki version history.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @return A Collection of Versions with every wiki version.
	 * @throws PathNotFoundException If there is no node in this repository path.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public List<Version> getVersionHistory(String token, String wikiPath) throws PathNotFoundException,
			RepositoryException, DatabaseException;
	
	/**
	 * Lock a wiki, so only is editable by the locker.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @throws LockException If the node is already locked.
	 * @throws PathNotFoundException If there is no node in this repository path.
	 * @throws AccessDeniedException If there is any security problem: you can't modify
	 * the node because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void lock(String token, String wikiPath) throws LockException, PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Unlock a wiki, so will be editable for other users.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @throws LockException If the node is not locked.
	 * @throws PathNotFoundException If there is no node in this repository path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the node because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void unlock(String token, String wikiPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Tell if a wiki is locked.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @throws AccessDeniedException If there is any security problem: you can't access this node
	 * because of lack of permissions.
	 * @throws RepositoryException If there is any repository problem.
	 * @throws PathNotFoundException If there is no node in the repository with this path.
	 * @return True if the document is locked, and False otherwise.
	 */
	public boolean isLocked(String token, String wikiPath) throws RepositoryException, PathNotFoundException,
			DatabaseException;
	
	/**
	 * Returns a lock information.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @throws AccessDeniedException If there is any security problem: you can't access this node
	 * because of lack of permissions.
	 * @throws RepositoryException If there is any repository problem.
	 * @throws PathNotFoundException If there is no node in the repository with this path.
	 * @throws LockException If the node is not locked.
	 * @return The lock info.
	 */
	public Lock getLock(String token, String wikiPath) throws RepositoryException, PathNotFoundException,
			LockException, DatabaseException;
	
	/**
	 * Deletes definitively a wiki from the repository. It is a physical delete, so
	 * the node can't be restored.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @throws AccessDeniedException If there is any security problem: you can't access this node 
	 * because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws PathNotFoundException If there is no node in the repository with this path.
	 */
	public void purge(String token, String wikPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, DatabaseException;

	/**
	 * Move a wiki to another location in the repository.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @param fldPath The destination folder path.
	 * @throws PathNotFoundException If the dstPath does not exists
	 * @throws ItemExistsException If there is already a node in the
	 * destination folder with the same name.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the node parent folder or the destination folder
	 * because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void move(String token, String wikiPath, String fldPath) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Copy a wiki to another location in the repository.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @param fldPath The destination folder path.
	 * @throws PathNotFoundException If the dstPath does not exists
	 * @throws ItemExistsException If there is already a node in the
	 * destination folder with the same name.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the node parent folder or the destination folder
	 * because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void copy(String token, String wikiPath, String fldPath) throws ItemExistsException,
			PathNotFoundException, AccessDeniedException, RepositoryException, IOException,
			DatabaseException, UserQuotaExceededException;
	
	/**
	 * Revert the wiki to an specific previous version.
	 * 
	 * @param wikiPath The path that identifies an unique wiki.
	 * @param versionId The version id to revert to.
	 * @throws PathNotFoundException If there is no node in this repository path.
	 * @throws AccessDeniedException If there is any security problem: you 
	 * can't modify the node because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void restoreVersion(String token, String wikiPath, String versionId) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException;
	
	/**
	 * Test if a wiki path is valid.
	 * 
	 * @param wikiPath The path that identifies an unique document.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this node because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 */
	public boolean isValid(String token, String wikiPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException;
	
	/**
	 * Get the wiki path from a UUID
	 * 
	 * @param uuid The unique document id.
	 * @return The wiki path
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this node because of lack of permissions.
	 * @throws RepositoryException If there is any problem.
	 */
	public String getPath(String token, String uuid) throws AccessDeniedException, RepositoryException,
			DatabaseException;
}
