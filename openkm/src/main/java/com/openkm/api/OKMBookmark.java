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

import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.bean.Bookmark;
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

	@Override
	public Bookmark add(String token, String nodePath, String name) throws AccessDeniedException,
			PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("add({}, {}, {})", new Object[] { token, nodePath, name });
		BookmarkModule bm = ModuleManager.getBookmarkModule();
		Bookmark bookmark = bm.add(token, nodePath, name);
		log.debug("add: {}", bookmark);
		return bookmark;
	}

	@Override
	public void remove(String token, int bmId) throws AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("remove({}, {})", token, bmId);
		BookmarkModule bm = ModuleManager.getBookmarkModule();
		bm.remove(token, bmId);
		log.debug("remove: void");
	}
	
	@Override
	public Bookmark rename(String token, int bmId, String newName) throws AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("rename({}, {}, {})", new Object[] { token, bmId, newName });
		BookmarkModule bm = ModuleManager.getBookmarkModule();
		Bookmark bookmark= bm.rename(token, bmId, newName);
		log.debug("rename: {}", bookmark);
		return bookmark;
	}

	@Override
	public Collection<Bookmark> getAll(String token) throws RepositoryException,
			DatabaseException {
		log.debug("getAll({})", token);
		BookmarkModule bm = ModuleManager.getBookmarkModule();
		Collection<Bookmark> col = bm.getAll(token);
		log.debug("getAll: {}", col);
		return col;
	}

	@Override
	public void setUserHome(String token, String path) throws AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("setUserHome({})", token);
		BookmarkModule bm = ModuleManager.getBookmarkModule();
		bm.setUserHome(token, path);
		log.debug("setUserHome: void");
	}

	@Override
	public Bookmark getUserHome(String token) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		log.debug("getUserHome({})", token);
		BookmarkModule bm = ModuleManager.getBookmarkModule();
		Bookmark bookmark = bm.getUserHome(token);
		log.debug("getUserHome: {}", bookmark);
		return bookmark;
	}
}
