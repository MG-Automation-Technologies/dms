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

package com.openkm.module.ejb;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.openkm.bean.Document;
import com.openkm.bean.Lock;
import com.openkm.bean.Version;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.FileSizeExceededException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UnsupportedMimeTypeException;
import com.openkm.core.VersionException;


public class EJBDocumentModule implements com.openkm.module.DocumentModule {

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

	public List<Document> getChilds(String token, String fldPath) throws PathNotFoundException, RepositoryException {
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

	public List<Version> getVersionHistory(String token, String docPath) throws PathNotFoundException, RepositoryException {
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
