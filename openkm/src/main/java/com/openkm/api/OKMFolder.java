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

package com.openkm.api;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.ContentInfo;
import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.module.FolderModule;
import com.openkm.module.ModuleManager;

/**
 * @author pavila
 *
 */
public class OKMFolder implements FolderModule {
	private static Logger log = LoggerFactory.getLogger(OKMFolder.class);
	private static OKMFolder instance = new OKMFolder();
	
	private OKMFolder() {}
	
	public static OKMFolder getInstance() {
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#create(java.lang.String, com.openkm.bean.Folder)
	 */
	@Override
	public Folder create(String token, Folder fldPath) throws PathNotFoundException, 
			ItemExistsException, AccessDeniedException, RepositoryException {
		log.debug("create("+token+", " + fldPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		Folder newFld = fm.create(token, fldPath);
		log.debug("create: "+ newFld);
		return newFld;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#getProperties(java.lang.String, java.lang.String)
	 */
	@Override
	public Folder getProperties(String token, String fldPath) throws PathNotFoundException, 
			RepositoryException {
		log.debug("getProperties("+token+", " + fldPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		Folder fld = fm.getProperties(token, fldPath);
		log.debug("getProperties: "+fld);
		return fld;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String token, String fldPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("delete("+token+", " + fldPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		fm.delete(token, fldPath);
		log.debug("delete: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#purge(java.lang.String, java.lang.String)
	 */
	@Override
	public void purge(String token, String fldPath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("purge("+token+", " + fldPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		fm.purge(token, fldPath);
		log.debug("purge: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Folder rename(String token, String fldPath, String newName) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException {
		log.debug("rename("+token+", " + fldPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		Folder renamedFolder = fm.rename(token, fldPath, newName);
		log.debug("rename: "+renamedFolder);
		return renamedFolder;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void move(String token, String fldPath, String dstPath) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException {
		log.debug("move("+token+", " + fldPath + ", " + dstPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		fm.move(token, fldPath, dstPath);
		log.debug("move: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#copy(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void copy(String token, String fldPath, String dstPath) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, IOException {
		log.debug("copy("+token+", " + fldPath + ", " + dstPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		fm.copy(token, fldPath, dstPath);
		log.debug("copy: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#getChilds(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<Folder> getChilds(String token, String fldPath) throws PathNotFoundException,
			RepositoryException {
		log.debug("getChilds(" + token + ", " + fldPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		Collection<Folder> childs = fm.getChilds(token, fldPath);
		log.debug("getChilds: "+childs);
		return childs;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#getContentInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public ContentInfo getContentInfo(String token, String fldPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException {
		log.debug("getContentInfo(" + token + ", " + fldPath + ")");
		FolderModule fm = ModuleManager.getFolderModule();
		ContentInfo contentInfo = fm.getContentInfo(token, fldPath);
		log.debug("getContentInfo: "+contentInfo);
		return contentInfo;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.FolderModule#isValid(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isValid(String token, String fldPath) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException {
		log.debug("isValid("+token+", "+fldPath+")");
		FolderModule fm = ModuleManager.getFolderModule();
		boolean valid = fm.isValid(token, fldPath);
		log.debug("isValid: "+valid);
		return valid;
	}
}
