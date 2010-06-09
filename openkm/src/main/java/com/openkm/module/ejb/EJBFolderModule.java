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
import java.util.List;

import com.openkm.bean.ContentInfo;
import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;

public class EJBFolderModule implements com.openkm.module.FolderModule {

	@Override
	public void copy(String fldPath, String dstPath) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, IOException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public Folder create(Folder fld) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String fldPath) throws LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Folder> getChilds(String fldPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentInfo getContentInfo(String fldPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Folder getProperties(String fldPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(String fldPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void move(String fldPath, String dstPath) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void purge(String fldPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public Folder rename(String fldPath, String newName) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
}
