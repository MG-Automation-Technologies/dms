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
import java.util.HashMap;

import es.git.openkm.bean.MetaData;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.NoSuchGroupException;
import es.git.openkm.core.NoSuchPropertyException;
import es.git.openkm.core.RepositoryException;

public interface PropertyGroupModule {

	/**
	 * Add a property group to a document.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @param grpName The group name previously registered in the system.
	 * @throws NoSuchGroupException If there is no such registered group name.
	 * @throws LockException Can't modify a locked document. 
	 * @throws PathNotFoundException If there is no document in this 
	 * repository path.
	 * @throws AccessDeniedException If there is any security problem:
	 * you can't modify the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void addGroup(String token, String docPath, String grpName) throws 
		NoSuchGroupException, LockException, PathNotFoundException, 
		AccessDeniedException, RepositoryException;

	/**
	 * Removes a property group from a document.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @param grpName The group name previously registered in the system.
	 * @throws NoSuchGroupException If there is no such registered group name.
	 * @throws LockException Can't modify a locked document. 
	 * @throws PathNotFoundException If there is no document in this 
	 * repository path.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void removeGroup(String token, String docPath, String grpName) throws 
		NoSuchGroupException, LockException, PathNotFoundException, RepositoryException;

	/**
	 * Get groups assigned to a document.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @throws PathNotFoundException If there is no document in this 
	 * repository path.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Collection<String> getGroups(String token, String docPath) throws 
		PathNotFoundException, RepositoryException;

	/**
	 * Get all groups defined in the system.
	 * 
	 * @param token The session authorization token.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public Collection<String> getAllGroups(String token) throws RepositoryException;

	/**
	 * Get all properties defined in a document by group.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @param grpName The group name previously registered in the system.
	 * @throws NoSuchGroupException If there is no such registered group name.
	 * @throws PathNotFoundException If there is no document in this 
	 * repository path.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public HashMap<String, String[]> getProperties(String token, String docPath, String grpName) throws 
		NoSuchGroupException, PathNotFoundException, RepositoryException;
	
	/**
	 * Set group properties to a document.
	 * 
	 * @param token The session authorization token.
	 * @param docPath The path that identifies an unique document.
	 * @param grpName The group name previously registered in the system.
	 * @param propName The category property name.
	 * @throws NoSuchPropertyException If there is no such registered category property.
	 * @throws NoSuchGroupException If there is no such registered group name.
	 * @throws LockException Can't modify a locked document. 
	 * @throws PathNotFoundException If there is no document in this 
	 * repository path.
	 * @throws AccessDeniedException If there is any security problem:
	 * you can't modify the document because of lack of permissions.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public void setProperties(String token, String docPath, String grpName, HashMap<String, String[]> properties) throws 
		NoSuchPropertyException, NoSuchGroupException, LockException, PathNotFoundException, 
		AccessDeniedException, RepositoryException;

	/**
	 * Get all possible values which can have a property.
	 *  
	 * @param token The session authorization token.
	 * @param grpName The group name previously registered in the system.
	 * @throws IOException If there is any problem reading the property values.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public HashMap<String, MetaData> getMetaData(String token, String grpName) throws IOException, 
		RepositoryException;
	
	/**
	 * Get all group property translations.
	 * 
	 * @param token The session authorization token.
	 * @param lang The language translations.
	 * @throws IOException If there is any problem reading the translations.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public HashMap<String, String> getTranslations(String token, String lang) throws IOException, 
		RepositoryException;
}
