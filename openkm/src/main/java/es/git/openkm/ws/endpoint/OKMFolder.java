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

package es.git.openkm.ws.endpoint;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Folder;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.module.FolderModule;
import es.git.openkm.module.ModuleManager;
import es.git.openkm.ws.util.FolderArray;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMFolder"
 * @web.servlet-mapping url-pattern="/OKMFolder"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class OKMFolder {
	private static Logger log = LoggerFactory.getLogger(OKMFolder.class);

	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#create(java.lang.String, es.git.openkm.bean.Folder)
	 */
	public Folder create(String token, Folder fld) throws AccessDeniedException, 
			RepositoryException, PathNotFoundException, ItemExistsException {
		log.debug("create(" + token + ", " + fld + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		Folder newFolder = fm.create(token, fld);
		log.debug("create: " +  newFolder);
		return newFolder;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#getProperties(java.lang.String, java.lang.String)
	 */
	public Folder getProperties(String token, String fldPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException {
		log.debug("getProperties(" + token + ", " + fldPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		Folder fld = fm.getProperties(token, fldPath);
		log.debug("getProperties: " +  fld);
		return fld;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#delete(java.lang.String, java.lang.String)
	 */
	public void delete(String token, String fldPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("delete(" + token + ", " + fldPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		fm.delete(token, fldPath);
		log.debug("delete: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Folder rename(String token, String fldPath, String newName) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException {
		log.debug("rename("+token+", " + fldPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		Folder renamedFolder = fm.rename(token, fldPath, newName);
		log.debug("rename: "+renamedFolder);
		return renamedFolder;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#move(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void move(String token, String fldPath, String dstPath) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException {
		log.debug("move(" + token + ", " + fldPath + ", " + dstPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		fm.move(token, fldPath, dstPath);
		log.debug("move: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#getChilds(java.lang.String, java.lang.String)
	 */
	public FolderArray getChilds(String token, String fldPath) throws PathNotFoundException,
			RepositoryException {
		log.debug("getChilds(" + token + ", " + fldPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		FolderArray fa = new FolderArray();
		fa.setValue((Folder []) fm.getChilds(token, fldPath).toArray(new Folder[0]));
		log.debug("getChilds: " + fa);
		return fa;
	}
}
