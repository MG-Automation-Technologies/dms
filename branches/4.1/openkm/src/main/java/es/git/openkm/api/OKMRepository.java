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

package es.git.openkm.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Folder;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.module.ModuleManager;
import es.git.openkm.module.RepositoryModule;

public class OKMRepository implements RepositoryModule {
	private static Logger log = LoggerFactory.getLogger(OKMRepository.class);
	private static OKMRepository instance = new OKMRepository();

	private OKMRepository() {}
	
	public static OKMRepository getInstance() {
		return instance;
	}
	
	@Override
	public Folder getRootFolder(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getRootFolder("+token+")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder rootFolder = rm.getRootFolder(token);
		log.debug("getRootFolder: "+rootFolder);
		return rootFolder;
	}

	@Override
	public Folder getTrashFolder(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getTrashFolder("+token+")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder trashFolder = rm.getTrashFolder(token);
		log.debug("getTrashFolder: "+trashFolder);
		return trashFolder;
	}

	@Override
	public Folder getTemplatesFolder(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getTemplatesFolder("+token+")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder templatesFolder = rm.getTemplatesFolder(token);
		log.debug("getTemplatesFolder: "+templatesFolder);
		return templatesFolder;
	}

	@Override
	public Folder getPersonalFolder(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getPersonalFolder("+token+")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder personalFolder = rm.getPersonalFolder(token);
		log.debug("getPersonalFolder: "+personalFolder);
		return personalFolder;
	}

	@Override
	public Folder getMailFolder(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getMailFolder("+token+")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder mailFolder = rm.getMailFolder(token);
		log.debug("getMailFolder: "+mailFolder);
		return mailFolder;
	}
	
	@Override
	public void purgeTrash(String token) throws AccessDeniedException, RepositoryException {
		log.debug("purgeTrash("+token+")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		rm.purgeTrash(token);
		log.debug("purgeTrash: void");
	}

	@Override
	public String getUpdateMessage(String token) throws RepositoryException {
		log.debug("getUpdateMessage("+token+")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		String updateMessage = rm.getUpdateMessage(token);
		log.debug("getUpdateMessage: "+updateMessage);
		return updateMessage;
	}

	@Override
	public String getUuid(String token) throws RepositoryException {
		log.debug("getUuid("+token+")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		String uuid = rm.getUuid(token);
		log.debug("getUuid: "+uuid);
		return uuid;
	}

	@Override
	public boolean hasNode(String token, String path) throws RepositoryException {
		log.debug("hasNode("+token+" , "+path+")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		boolean ret = rm.hasNode(token, path);
		log.debug("hasNode: "+ret);
		return ret;
	}

	@Override
	public String getPath(String token, String uuid) throws PathNotFoundException, RepositoryException {
		log.debug("getPath("+token+" , "+uuid+")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		String ret = rm.getPath(token, uuid);
		log.debug("getPath: "+ret);
		return ret;
	}
}
