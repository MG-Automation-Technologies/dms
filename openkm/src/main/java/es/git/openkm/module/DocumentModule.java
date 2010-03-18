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
import java.io.InputStream;
import java.util.Collection;

import es.git.openkm.bean.Document;
import es.git.openkm.bean.Lock;
import es.git.openkm.bean.Version;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.FileSizeExceededException;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.UnsupportedMimeTypeException;
import es.git.openkm.core.VersionException;
import es.git.openkm.core.VirusDetectedException;

public interface DocumentModule {

	/**
	 * Creates a new document in the repository.
	 * 
	 * @param token The session authorization token.
	 * @param doc A document object with the new document properties.
	 * @param content The document content in bytes.
	 * @return A document object with the properties of the new created document.
	 * @throws UnsupportedMimeTypeException If the uploaded file has an unsupported
	 * MIME type.
	 * @throws FileSizeExceededException  If the document content is biggest than 
	 * the maximum accepted.
	 * @throws PathNotFoundException If the parent folder doesn't exist.
	 * @throws ItemExistsException If there is already a document in 
	 * the repository with the same name.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the parent document folder because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws IOException An error when inserting document data into the repository.
	 */
	public Document create(String token, Document doc, InputStream is) throws
			UnsupportedMimeTypeException, FileSizeExceededException, VirusDetectedException,
			ItemExistsException, PathNotFoundException, AccessDeniedException,
			RepositoryException, IOException;
	
	/**
	 * Deletes a document from the repository. It is a logical delete,
	 * so really is moved to the user trash and can be restored.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @throws LockException Can't delete a locked document.
	 * @throws PathNotFoundException If there is no document in this 
	 * repository path.
	 * @throws AccessDeniedException If there is any security problem:
	 * you can't modify the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void delete(String token, String docPath) throws LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException;

	/**
	 * Rename a document in the repository.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @param newName The new folder name.
	 * @return An document object with the new document properties.
	 * @throws PathNotFoundException If there is no document in this repository path.
	 * @throws ItemExistsException If there is already a document in the
	 * repository with the same name in the same path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Document rename(String token, String docPath, String newName) throws
			PathNotFoundException, ItemExistsException, AccessDeniedException, RepositoryException;

	/**
	 * Obtain document properties from the repository.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @return The document properties.
	 * @throws PathNotFoundException If there is no document in this repository path.
	 * you can't modify the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Document getProperties(String token, String docPath) throws PathNotFoundException,
			RepositoryException;

	/**
	 * Set document properties in the repository.
	 * 
	 * @param token The session authorization token.
	 * @param doc An document object with the properties
	 * @throws VersionException A document checked in can't be modified.
	 * @throws LockException A locked document can't be modified.
	 * @throws PathNotFoundException If there is no document in this
	 * repository path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void setProperties(String token, Document doc) throws VersionException, 
			LockException, PathNotFoundException, AccessDeniedException, RepositoryException;

	/**
	 * Obtain document content from the repository.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @return The content of the document file.
	 * @throws PathNotFoundException If there is no document in this
	 * repository path.
	 * @throws RepositoryException If there is any general repository problem.
     * @throws IOException An error when retrieving document data 
     * from the repository.
	 */
	public InputStream getContent(String token, String docPath, boolean checkout) throws PathNotFoundException, 
			RepositoryException, IOException;

	/**
	 * Obtain document content from the repository.
	 * 
	 * @param token String with user autorization info.
	 * @param docPath The path that identifies an unique document.
	 * @param versionId The id of the version to get the content from.
	 * @return The content of the document file.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify this document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 * @throws IOException An error when retrieving document data from the repository.
	 */
	public InputStream getContentByVersion(String token, String docPath, String versionId) throws
			RepositoryException, PathNotFoundException, IOException;

	/**
	 * Set document content in the repository.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @param content The new document content.
	 * @throws FileSizeExceededException  If the document content is biggest than 
	 * the maximum accepted.
	 * @throws VersionException A document checked in can't be modified.
	 * @throws LockException A locked document can't be modified.
	 * @throws PathNotFoundException If there is no document in this
	 * repository path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws IOException If there is any error setting the new content.
	 */
	public void setContent(String token, String docPath, InputStream is) throws
			FileSizeExceededException, VirusDetectedException, VersionException,
			LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, IOException;

	/**
	 * Add a note to a document
	 * 
	 * @param token String with user authorization info.
	 * @param docPath The path that identifies an unique document.
	 * @param text The message text
	 * @throws LockException A locked document can't be modified.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this folder because of lack of permissions.
	 * @throws RepositoryException If there is any problem.
	 */
	public void addNote(String token, String docPath, String text) throws LockException,
		PathNotFoundException, AccessDeniedException, RepositoryException;

	/**
	 * Retrieve a list of child documents from an existing folder.
	 * 
	 * @param token The session authorization token.
	 * @param fldPath The path that identifies an unique folder.
	 * @return A Collection with the child documents.
	 * @throws PathNotFoundException If there is no folder in this repository path.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Collection<Document> getChilds(String token, String fldPath) throws PathNotFoundException, 
			RepositoryException;
	
	/**
	 * Checkout the document to edit it. The document can't be edited by another
	 * user until it is checked in o the checkout is canceled.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @throws LockException A locked document can't be modified.
	 * @throws PathNotFoundException If there is no document in this repository path.
	 * @throws AccessDeniedException If there is any security problem: you can't modify
	 * the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void checkout(String token, String docPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException;

	/**
	 * Cancel a previous checked out state in a document.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @throws LockException A locked document can't be modified.
	 * @throws PathNotFoundException If there is no document in this repository path.
	 * @throws AccessDeniedException If there is any security problem: you can't modify
	 * the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void cancelCheckout(String token, String docPath) throws LockException, 
			PathNotFoundException, AccessDeniedException, RepositoryException;
	
	/**
	 * Test if a node has been already checked out.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @return True if the document is in checked out state, or false if not.
	 * @throws PathNotFoundException If there is no document in this repository path.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public boolean isCheckedOut(String token, String docPath) throws PathNotFoundException,
			RepositoryException;

	/**
	 * Check in the document to create a new version.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @param comment A comment for this checkin.
	 * @return A version object with the properties of th new generated version.
	 * @throws LockException A locked document can't be modified.
	 * @throws VersionException If the nodes was not previously checked out.
	 * @throws PathNotFoundException If there is no document in this repository path.
	 * @throws AccessDeniedException If there is any security problem: you can't modify
	 * the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Version checkin(String token, String docPath, String comment) throws LockException, 
			VersionException, PathNotFoundException, AccessDeniedException, RepositoryException;

	/**
	 * Get the document version history.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @return A Collection of Versions with every document version.
	 * @throws PathNotFoundException If there is no document in this repository path.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Collection<Version> getVersionHistory(String token, String docPath) throws 
			PathNotFoundException, RepositoryException;

	/**
	 * Lock a document, so only is editable by the locker.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @throws LockException If the node is already locked.
	 * @throws PathNotFoundException If there is no document in this repository path.
	 * @throws AccessDeniedException If there is any security problem: you can't modify
	 * the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void lock(String token, String docPath) throws LockException, 
			PathNotFoundException, AccessDeniedException, RepositoryException;
	
	/**
	 * Unlock a document, so will be editable for other users.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @throws LockException If the node is not locked.
	 * @throws PathNotFoundException If there is no document in this repository path.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void unlock(String token, String docPath) throws LockException, 
			PathNotFoundException, AccessDeniedException, RepositoryException;
	
	/**
	 * Tell if a document is locked.
	 * 
	 * @param token String with user autorization info.
	 * @param docPath The path that identifies an unique document.
	 * @throws AccessDeniedException If there is any security problem: you can't access this document because of lack of permissions.
	 * @throws RepositoryException If there is any repository problem.
	 * @throws PathNotFoundException If there is no document in the repository with this path.
	 * @return True if the document is locked, and False otherwise.
	 */
	public boolean isLocked(String token, String docPath) throws RepositoryException, PathNotFoundException;
	
	/**
	 * Returns a lock information.
	 * 
	 * @param token String with user autorization info.
	 * @param docPath The path that identifies an unique document.
	 * @throws AccessDeniedException If there is any security problem: you can't access this document because of lack of permissions.
	 * @throws RepositoryException If there is any repository problem.
	 * @throws PathNotFoundException If there is no document in the repository with this path.
	 * @throws LockException If the node is not locked.
	 * @return The lock info.
	 */
	public Lock getLock(String token, String docPath) throws RepositoryException, PathNotFoundException, LockException;
	
	/**
	 * Deletes definitively a document from the repository. It is a phisical delete, so
	 * the document can't be restored.
	 * 
	 * @param token
	 *            String with user autorization info.
	 * @param docPath The path that identifies an unique document.
	 * @throws AccessDeniedException
	 *             If there is any security problem: you can't access this document because of lack of permissions.
	 * @throws RepositoryException
	 *             If there is any general repository problem.
	 * @throws PathNotFoundException If there is no document in the repository with this path.
	 */
	public void purge(String token, String docPath) throws AccessDeniedException, RepositoryException, PathNotFoundException;

	/**
	 * Move a document to another location in the repository.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @param fldPath The destination folder path.
	 * @throws PathNotFoundException If the dstPath does not exists
	 * @throws ItemExistsException If there is already a document in the
	 * destination folder with the same name.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the document's parent folder or the destination folder
	 * because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void move(String token, String docPath, String fldPath) throws PathNotFoundException,
		ItemExistsException, AccessDeniedException, RepositoryException;
	
	/**
	 * Copy a document to another location in the repository.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @param fldPath The destination folder path.
	 * @throws PathNotFoundException If the dstPath does not exists
	 * @throws ItemExistsException If there is already a document in the
	 * destination folder with the same name.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't modify the document's parent folder or the destination folder
	 * because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void copy(String token, String docPath, String fldPath) throws 
			ItemExistsException, PathNotFoundException, AccessDeniedException,
			RepositoryException, IOException;
	
	/**
	 * Revert the document to an specific previous version.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @param versionId The version id to revert to.
	 * @throws PathNotFoundException If there is no document in this repository path.
	 * @throws AccessDeniedException If there is any security problem: you 
	 * can't modify the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void restoreVersion(String token, String docPath, String versionId) throws 
		PathNotFoundException, AccessDeniedException, RepositoryException;

	/**
	 * Purge a Document version history, so delete all previous verions but last one. 
	 * Used to free Document version size.
	 * 
	 * @param token String with user autorization info.
	 * @param docPath The path that identifies an unique document.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this folder because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 */
	public void purgeVersionHistory(String token, String docPath) throws 
		AccessDeniedException, RepositoryException, PathNotFoundException;

	/**
	 * Get the version size of a Document.
	 * 
	 * @param token String with user autorization info.
	 * @param docPath The path that identifies an unique document.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this folder because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 */
	public long getVersionHistorySize(String token, String docPath) throws 
		RepositoryException, PathNotFoundException;

	/**
	 * Test if a document path is valid.
	 * 
	 * @param token String with user autorization info.
	 * @param docPath The path that identifies an unique document.
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this folder because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws PathNotFoundException If there is no folder in the repository with this path.
	 */
	public boolean isValid(String token, String docPath) throws 
		PathNotFoundException, AccessDeniedException, RepositoryException;
	
	/**
	 * Get the document path from a UUID
	 * 
	 * @param token String with user authorization info.
	 * @param uuid The unique document id.
	 * @return The document path
	 * @throws AccessDeniedException If there is any security problem: 
	 * you can't access this folder because of lack of permissions.
	 * @throws RepositoryException If there is any problem.
	 */
	public String getPath(String token, String uuid) throws AccessDeniedException,
		RepositoryException;
}
