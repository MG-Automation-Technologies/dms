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

import java.util.Collection;

import es.git.openkm.bean.ContentInfo;
import es.git.openkm.bean.Folder;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.RepositoryException;

public class EJBFolderModule implements es.git.openkm.module.FolderModule {

	public Folder create(String token, Folder fld) throws PathNotFoundException, ItemExistsException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public Folder getProperties(String token, String fldPath) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(String token, String fldPath) throws LockException, PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public void purge(String token, String fldPath) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public Folder rename(String token, String fldPath, String newName) throws PathNotFoundException, ItemExistsException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public void move(String token, String fldPath, String dstPath) throws PathNotFoundException, ItemExistsException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public void copy(String token, String fldPath, String dstPath) throws PathNotFoundException, ItemExistsException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		
	}

	public Collection<Folder> getChilds(String token, String fldPath) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public ContentInfo getContentInfo(String token, String fldPath) throws AccessDeniedException, RepositoryException, PathNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValid(String token, String fldPath) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}

}