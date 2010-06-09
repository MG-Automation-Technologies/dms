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

package com.openkm.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Folder;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.AccessDeniedException;
import com.openkm.module.ModuleManager;
import com.openkm.module.RepositoryModule;

public class OKMRepository implements RepositoryModule {
	private static Logger log = LoggerFactory.getLogger(OKMRepository.class);
	private static OKMRepository instance = new OKMRepository();

	private OKMRepository() {}
	
	public static OKMRepository getInstance() {
		return instance;
	}
	
	@Override
	public Folder getRootFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getRootFolder()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder rootFolder = rm.getRootFolder();
		log.debug("getRootFolder: {}", rootFolder);
		return rootFolder;
	}

	@Override
	public Folder getTrashFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getTrashFolder()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder trashFolder = rm.getTrashFolder();
		log.debug("getTrashFolder: {}", trashFolder);
		return trashFolder;
	}

	@Override
	public Folder getTrashFolderBase() throws PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getTrashFolderBase()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder trashFolder = rm.getTrashFolderBase();
		log.debug("getTrashFolderBase: {}", trashFolder);
		return trashFolder;
	}
	
	@Override
	public Folder getTemplatesFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getTemplatesFolder()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder templatesFolder = rm.getTemplatesFolder();
		log.debug("getTemplatesFolder: {}", templatesFolder);
		return templatesFolder;
	}

	@Override
	public Folder getPersonalFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getPersonalFolder()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder personalFolder = rm.getPersonalFolder();
		log.debug("getPersonalFolder: {}", personalFolder);
		return personalFolder;
	}
	
	@Override
	public Folder getPersonalFolderBase() throws PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getPersonalFolderBase()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder personalFolder = rm.getPersonalFolderBase();
		log.debug("getPersonalFolderBase: {}", personalFolder);
		return personalFolder;
	}

	@Override
	public Folder getMailFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getMailFolder()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder mailFolder = rm.getMailFolder();
		log.debug("getMailFolder: {}", mailFolder);
		return mailFolder;
	}
	
	@Override
	public Folder getMailFolderBase() throws PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getMailFolderBase()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder mailFolder = rm.getMailFolderBase();
		log.debug("getMailFolderBase: {}", mailFolder);
		return mailFolder;
	}

	@Override
	public Folder getThesaurusFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getThesaurusFolder()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder thesaurusFolder = rm.getThesaurusFolder();
		log.debug("getThesaurusFolder: {}", thesaurusFolder);
		return thesaurusFolder;
	}

	@Override
	public Folder getCategoriesFolder() throws PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getCategoriesFolder()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder categoriesFolder = rm.getCategoriesFolder();
		log.debug("getCategoriesFolder: {}", categoriesFolder);
		return categoriesFolder;
	}
	
	@Override
	public void purgeTrash() throws AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("purgeTrash()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		rm.purgeTrash();
		log.debug("purgeTrash: void");
	}

	@Override
	public String getUpdateMessage() throws RepositoryException { 
		log.debug("getUpdateMessage()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		String updateMessage = rm.getUpdateMessage();
		log.debug("getUpdateMessage: {}", updateMessage);
		return updateMessage;
	}

	@Override
	public String getUuid() throws RepositoryException {
		log.debug("getUuid()");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		String uuid = rm.getUuid();
		log.debug("getUuid: {}", uuid);
		return uuid;
	}

	@Override
	public boolean hasNode(String path) throws RepositoryException, DatabaseException {
		log.debug("hasNode({})", path);
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		boolean ret = rm.hasNode(path);
		log.debug("hasNode: {}", ret);
		return ret;
	}

	@Override
	public String getPath(String uuid) throws PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getPath({})", uuid);
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		String ret = rm.getPath(uuid);
		log.debug("getPath: {}", ret);
		return ret;
	}
}
