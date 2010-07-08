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

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
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

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMFolder"
 * @web.servlet-mapping url-pattern="/OKMFolder"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@SecurityDomain("OpenKM")
public class OKMFolder {
	private static Logger log = LoggerFactory.getLogger(OKMFolder.class);

	@WebMethod
	public Folder create(@WebParam(name = "fld") Folder fld) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("create({})", fld);
		FolderModule fm = ModuleManager.getFolderModule();
		Folder newFolder = fm.create(fld);
		log.debug("create: {}", newFolder);
		return newFolder;
	}
	
	@WebMethod
	public Folder createSimple(@WebParam(name = "fldPath") String fldPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("createSimple({})", fldPath);
		FolderModule fm = ModuleManager.getFolderModule();
		Folder fld = new Folder();
		fld.setPath(fldPath);
		Folder newFolder = fm.create(fld);
		log.debug("createSimple: {}", newFolder);
		return newFolder;
	}

	@WebMethod
	public Folder getProperties(@WebParam(name = "fldPath") String fldPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("getProperties({})", fldPath);
		FolderModule fm = ModuleManager.getFolderModule();
		Folder fld = fm.getProperties(fldPath);
		log.debug("getProperties: {}", fld);
		return fld;
	}

	@WebMethod
	public void delete(@WebParam(name = "fldPath") String fldPath) throws LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("delete({})", fldPath);
		FolderModule fm = ModuleManager.getFolderModule();
		fm.delete(fldPath);
		log.debug("delete: void");
	}
	
	@WebMethod
	public Folder rename(@WebParam(name = "fldPath") String fldPath,
			@WebParam(name = "newName") String newName) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("rename({})", fldPath);
		FolderModule fm = ModuleManager.getFolderModule();
		Folder renamedFolder = fm.rename(fldPath, newName);
		log.debug("rename: {}", renamedFolder);
		return renamedFolder;
	}
	
	@WebMethod
	public void move(@WebParam(name = "fldPath") String fldPath, 
			@WebParam(name = "dstPath") String dstPath) throws PathNotFoundException, ItemExistsException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("move({}, {})", fldPath, dstPath);
		FolderModule fm = ModuleManager.getFolderModule();
		fm.move(fldPath, dstPath);
		log.debug("move: void");
	}
	
	@WebMethod
	public Folder[] getChilds(@WebParam(name = "fldPath") String fldPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getChilds({})", fldPath);
		FolderModule fm = ModuleManager.getFolderModule();
		List<Folder> col = fm.getChilds(fldPath);
		Folder[] result = (Folder []) col.toArray(new Folder[col.size()]);
		log.debug("getChilds: {}", result);
		return result;
	}
	
	@WebMethod
	public boolean isValid(@WebParam(name = "fldPath") String fldPath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("isValid({})", fldPath);
		FolderModule fm = ModuleManager.getFolderModule();
		boolean valid = fm.isValid(fldPath);
		log.debug("isValid: {}", valid);
		return valid;
	}
}
