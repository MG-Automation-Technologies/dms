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
import com.openkm.bean.Note;
import com.openkm.bean.Version;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.FileSizeExceededException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UnsupportedMimeTypeException;
import com.openkm.core.VersionException;
import com.openkm.core.VirusDetectedException;


public class EJBDocumentModule implements com.openkm.module.DocumentModule {

	@Override
	public void addNote(String docPath, String text) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void cancelCheckout(String docPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public Version checkin(String docPath, String comment) throws LockException, VersionException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkout(String docPath) throws LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void copy(String docPath, String fldPath) throws ItemExistsException, PathNotFoundException,
			AccessDeniedException, RepositoryException, IOException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public Document create(Document doc, InputStream is) throws UnsupportedMimeTypeException,
			FileSizeExceededException, VirusDetectedException, ItemExistsException, PathNotFoundException,
			AccessDeniedException, RepositoryException, IOException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String docPath) throws LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Document> getChilds(String fldPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getContent(String docPath, boolean checkout) throws PathNotFoundException,
			RepositoryException, IOException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getContentByVersion(String docPath, String versionId) throws RepositoryException,
			PathNotFoundException, IOException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lock getLock(String docPath) throws RepositoryException, PathNotFoundException, LockException,
			DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPath(String uuid) throws AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document getProperties(String docPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Version> getVersionHistory(String docPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getVersionHistorySize(String docPath) throws RepositoryException, PathNotFoundException,
			DatabaseException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isCheckedOut(String docPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLocked(String docPath) throws RepositoryException, PathNotFoundException,
			DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid(String docPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void lock(String docPath) throws LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void move(String docPath, String fldPath) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void purge(String docPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void purgeVersionHistory(String docPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public Document rename(String docPath, String newName) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restoreVersion(String docPath, String versionId) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void setContent(String docPath, InputStream is) throws FileSizeExceededException,
			VirusDetectedException, VersionException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, IOException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void setProperties(Document doc) throws VersionException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void unlock(String docPath) throws LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public Note getNote(String notePath) throws LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeNote(String notePath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNote(String notePath, String text) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		
	}
}
