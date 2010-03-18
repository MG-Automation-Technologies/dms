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

package es.git.openkm.module.ejb;

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


public class EJBDocumentModule implements es.git.openkm.module.DocumentModule {

	public Document create(String token, Document doc, InputStream is) throws IOException, UnsupportedMimeTypeException, FileSizeExceededException, ItemExistsException, PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void delete(String token, String docPath) throws LockException, PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public Document rename(String token, String docPath, String newName) throws PathNotFoundException, ItemExistsException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public Document getProperties(String token, String docPath) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setProperties(String token, Document doc) throws VersionException, LockException, PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public InputStream getContent(String token, String docPath, boolean checkout) throws IOException, PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getContentByVersion(String token, String docPath, String versionId) throws RepositoryException, IOException, PathNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setContent(String token, String docPath, InputStream is) throws IOException, VersionException, LockException, PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public Collection<Document> getChilds(String token, String fldPath) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public void checkout(String token, String docPath) throws LockException, PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public void cancelCheckout(String token, String docPath) throws LockException, PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public boolean isCheckedOut(String token, String docPath) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}

	public Version checkin(String token, String docPath, String comment) throws LockException, VersionException, PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Version> getVersionHistory(String token, String docPath) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public void lock(String token, String docPath) throws LockException, PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public void unlock(String token, String docPath) throws LockException, PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public boolean isLocked(String token, String docPath) throws RepositoryException, PathNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	public Lock getLock(String token, String docPath) throws RepositoryException, PathNotFoundException, LockException {
		// TODO Auto-generated method stub
		return null;
	}

	public void purge(String token, String docPath) throws AccessDeniedException, RepositoryException, PathNotFoundException {
		// TODO Auto-generated method stub
		
	}

	public void move(String token, String docPath, String fldPath) throws PathNotFoundException, ItemExistsException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public void copy(String token, String docPath, String fldPath) throws PathNotFoundException, ItemExistsException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public void restoreVersion(String token, String docPath, String versionId) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public void purgeVersionHistory(String token, String docPath) throws AccessDeniedException, RepositoryException, PathNotFoundException {
		// TODO Auto-generated method stub
		
	}

	public long getVersionHistorySize(String token, String docPath) throws RepositoryException, PathNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isValid(String token, String docPath) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}

	public String getPath(String token, String uuid)
			throws AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addNote(String token, String docPath, String text)
			throws LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException {
		// TODO Auto-generated method stub
		
	}
}
