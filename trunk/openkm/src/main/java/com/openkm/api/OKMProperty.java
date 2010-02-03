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

import com.openkm.core.AccessDeniedException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.VersionException;
import com.openkm.module.ModuleManager;
import com.openkm.module.PropertyModule;

/**
 * @author pavila
 *
 */
public class OKMProperty implements PropertyModule {
	private static Logger log = LoggerFactory.getLogger(OKMDocument.class);
	private static OKMProperty instance = new OKMProperty();

	private OKMProperty() {}
	
	public static OKMProperty getInstance() {
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyModule#addCategory(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addCategory(String token, String nodePath, String category)
			throws VersionException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("addCategory("+token+", "+nodePath+")");
		PropertyModule pm = ModuleManager.getPropertyModule();
		pm.addCategory(token, nodePath, category);
		log.debug("addCategory: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyModule#removeCategory(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void removeCategory(String token, String nodePath, String category)
			throws VersionException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("removeCategory("+token+", "+nodePath+")");
		PropertyModule pm = ModuleManager.getPropertyModule();
		pm.removeCategory(token, nodePath, category);
		log.debug("removeCategory: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyModule#addKeyword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addKeyword(String token, String nodePath, String keyword)
			throws VersionException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("addKeyword("+token+", "+nodePath+")");
		PropertyModule pm = ModuleManager.getPropertyModule();
		pm.addKeyword(token, nodePath, keyword);
		log.debug("addKeyword: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyModule#removeKeyword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void removeKeyword(String token, String nodePath, String keyword)
			throws VersionException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("removeKeyword("+token+", "+nodePath+")");
		PropertyModule pm = ModuleManager.getPropertyModule();
		pm.removeKeyword(token, nodePath, keyword);
		log.debug("removeKeyword: void");
	}
}
