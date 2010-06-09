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

import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;

public class EJBRepositoryModule implements com.openkm.module.RepositoryModule {

	@Override
	public Folder getCategoriesFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Folder getMailFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Folder getMailFolderBase() throws PathNotFoundException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPath(String uuid) throws PathNotFoundException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Folder getPersonalFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Folder getPersonalFolderBase() throws PathNotFoundException, RepositoryException,
			DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Folder getRootFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Folder getTemplatesFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Folder getThesaurusFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Folder getTrashFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Folder getTrashFolderBase() throws PathNotFoundException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUpdateMessage() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUuid() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNode(String path) throws RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void purgeTrash() throws AccessDeniedException, RepositoryException, DatabaseException {
		// TODO Auto-generated method stub
	}
}
