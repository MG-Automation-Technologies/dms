/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.module.ModuleManager;
import es.git.openkm.module.RepositoryModule;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMRepository"
 * @web.servlet-mapping url-pattern="/OKMRepository"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class OKMRepository {
	private static Logger log = LoggerFactory.getLogger(OKMRepository.class);

	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#getRootFolder(java.lang.String)
	 */
	public Folder getRootFolder(String token) throws PathNotFoundException, 
			RepositoryException {
		log.debug("getRootFolder(" + token + ")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder root = rm.getRootFolder(token);
		log.debug("getRootFolder: " + root);
		return root;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#getTrashFolder(java.lang.String)
	 */
	public Folder getTrashFolder(String token) throws PathNotFoundException, 
			RepositoryException {
		log.debug("getTrashFolder(" + token + ")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder trash = rm.getTrashFolder(token);
		log.debug("getTrashFolder: " + trash);
		return trash;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#getTemplate(java.lang.String)
	 */
	public Folder getTemplatesFolder(String token) throws PathNotFoundException, 
			RepositoryException {
		log.debug("getTemplatesFolder(" + token + ")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder templatesFolder = rm.getTemplatesFolder(token);
		log.debug("getTemplatesFolder: " + templatesFolder);
		return templatesFolder;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#getPersonalFolder(java.lang.String)
	 */
	public Folder getPersonalFolder(String token) throws PathNotFoundException, 
			RepositoryException {
		log.debug("getPersonalFolder(" + token + ")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		Folder personalFolder = rm.getPersonalFolder(token);
		log.debug("getPersonalFolder: " + personalFolder);
		return personalFolder;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#purgeTrash(java.lang.String)
	 */
	public void purgeTrash(String token) throws AccessDeniedException, RepositoryException {
		log.debug("purgeTrash(" + token + ")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		rm.purgeTrash(token);
		log.debug("purgeTrash: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.RepositoryModule#hasNode(java.lang.String, java.lang.String)
	 */
	public boolean hasNode(String token, String path) throws RepositoryException {
		log.debug("hasNode("+token+" , "+path+")");
		RepositoryModule rm = ModuleManager.getRepositoryModule();
		boolean ret = rm.hasNode(token, path);
		log.debug("hasNode: "+ret);
		return ret;
	}
}
