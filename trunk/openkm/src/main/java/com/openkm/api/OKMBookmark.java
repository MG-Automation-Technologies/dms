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

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Bookmark;
import com.openkm.core.ItemExistsException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.module.BookmarkModule;
import com.openkm.module.ModuleManager;

/**
 * @author pavila
 *
 */
public class OKMBookmark implements BookmarkModule {
	private static Logger log = LoggerFactory.getLogger(OKMBookmark.class);
	private static OKMBookmark instance = new OKMBookmark();

	private OKMBookmark() {}
	
	public static OKMBookmark getInstance() {
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.BookmarkModule#add(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Bookmark add(String token, String nodePath, String name) throws 
			PathNotFoundException, ItemExistsException, RepositoryException {
		log.debug("add(" + token + ", " + nodePath + ", " + name + ")");
		BookmarkModule bm = ModuleManager.getBookmarkModule();
		Bookmark bookmark = bm.add(token, nodePath, name);
		log.debug("add: "+bookmark);
		return bookmark;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.BookmarkModule#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String token, String name) throws PathNotFoundException,
			RepositoryException {
		log.debug("remove(" + token + ", " + name + ")");
		BookmarkModule bm = ModuleManager.getBookmarkModule();
		bm.remove(token, name);
		log.debug("remove: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.BookmarkModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Bookmark rename(String token, String name, String newName) throws 
			PathNotFoundException, ItemExistsException, RepositoryException {
		log.debug("rename(" + token + ", " + name + ", " + newName + ")");
		BookmarkModule bm = ModuleManager.getBookmarkModule();
		Bookmark bookmark= bm.rename(token, name, newName);
		log.debug("rename: "+bookmark);
		return bookmark;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.BookmarkModule#getAll(java.lang.String)
	 */
	@Override
	public Collection<Bookmark> getAll(String token) throws RepositoryException {
		log.debug("getAll(" + token + ")");
		BookmarkModule bm = ModuleManager.getBookmarkModule();
		Collection<Bookmark> col = bm.getAll(token);
		log.debug("getAll: " + col);
		return col;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.BookmarkModule#setUserHome(java.lang.String, java.lang.String)
	 */
	@Override
	public void setUserHome(String token, String path) throws RepositoryException {
		log.debug("setUserHome(" + token + ")");
		BookmarkModule bm = ModuleManager.getBookmarkModule();
		bm.setUserHome(token, path);
		log.debug("setUserHome: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.BookmarkModule#getUserHome(java.lang.String)
	 */
	@Override
	public Bookmark getUserHome(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getUserHome(" + token + ")");
		BookmarkModule bm = ModuleManager.getBookmarkModule();
		Bookmark bookmark = bm.getUserHome(token);
		log.debug("getUserHome: " + bookmark);
		return bookmark;
	}
}
