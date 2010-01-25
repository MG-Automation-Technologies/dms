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
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;

public class EJBRepositoryModule implements com.openkm.module.RepositoryModule {

	public Folder getRootFolder(String token) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public Folder getTrashFolder(String token) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public Folder getTemplatesFolder(String token) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public Folder getPersonalFolder(String token) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public Folder getMailFolder(String token) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public Folder getThesaurusFolder(String token) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public Folder getCategoriesFolder(String token) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void purgeTrash(String token) throws AccessDeniedException, RepositoryException {
		// TODO Auto-generated method stub
	}

	public String getUpdateMessage(String token) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUuid(String token) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasNode(String token, String path) throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}
}
