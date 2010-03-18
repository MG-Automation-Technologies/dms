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

import es.git.openkm.bean.Folder;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;

public class EJBRepositoryModule implements es.git.openkm.module.RepositoryModule {

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

	@Override
	public String getPath(String token, String uuid) throws PathNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}
}
