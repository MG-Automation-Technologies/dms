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

package com.openkm.ws.endpoint;

import java.util.Collection;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.jboss.annotation.security.SecurityDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.module.FolderModule;
import com.openkm.module.ModuleManager;
import com.openkm.ws.util.FolderArray;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMFolder"
 * @web.servlet-mapping url-pattern="/OKMFolder"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@SecurityDomain("OpenKM")
public class OKMFolder {
	private static Logger log = LoggerFactory.getLogger(OKMFolder.class);

	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#create(java.lang.String, com.openkm.bean.Folder)
	 */
	public Folder create(String token, Folder fld) throws AccessDeniedException, 
			RepositoryException, PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("create({}, {})", token, fld);
		FolderModule fm = ModuleManager.getFolderModule();
		Folder newFolder = fm.create(token, fld);
		log.debug("create: {}", newFolder);
		return newFolder;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#getProperties(java.lang.String, java.lang.String)
	 */
	public Folder getProperties(String token, String fldPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("getProperties({}, {})", token, fldPath);
		FolderModule fm = ModuleManager.getFolderModule();
		Folder fld = fm.getProperties(token, fldPath);
		log.debug("getProperties: {}", fld);
		return fld;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#delete(java.lang.String, java.lang.String)
	 */
	public void delete(String token, String fldPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("delete({}, {})", token, fldPath);
		FolderModule fm = ModuleManager.getFolderModule();
		fm.delete(token, fldPath);
		log.debug("delete: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Folder rename(String token, String fldPath, String newName) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("rename({}, {})", token, fldPath);
		FolderModule fm = ModuleManager.getFolderModule();
		Folder renamedFolder = fm.rename(token, fldPath, newName);
		log.debug("rename: {}", renamedFolder);
		return renamedFolder;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#move(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void move(String token, String fldPath, String dstPath) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("move({}, {}, {})", new Object[] { token, fldPath, dstPath });
		FolderModule fm = ModuleManager.getFolderModule();
		fm.move(token, fldPath, dstPath);
		log.debug("move: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#getChilds(java.lang.String, java.lang.String)
	 */
	public FolderArray getChilds(String token, String fldPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getChilds({}, {})", token, fldPath);
		FolderModule fm = ModuleManager.getFolderModule();
		FolderArray fa = new FolderArray();
		Collection<Folder> col = fm.getChilds(token, fldPath);
		fa.setValue((Folder []) col.toArray(new Folder[col.size()]));
		log.debug("getChilds: {}", fa);
		return fa;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#isValid(java.lang.String, java.lang.String)
	 */
	public boolean isValid(String token, String fldPath) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("isValid({} {})", token, fldPath);
		FolderModule fm = ModuleManager.getFolderModule();
		boolean valid = fm.isValid(token, fldPath);
		log.debug("isValid: {}", valid);
		return valid;
	}
}
