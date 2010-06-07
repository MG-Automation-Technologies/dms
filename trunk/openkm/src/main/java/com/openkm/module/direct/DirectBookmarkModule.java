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

package com.openkm.module.direct;

import java.util.ArrayList;
import java.util.Collection;

import javax.jcr.Node;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.SessionManager;
import com.openkm.dao.BookmarkDAO;
import com.openkm.dao.UserConfigDAO;
import com.openkm.dao.bean.Bookmark;
import com.openkm.dao.bean.UserConfig;
import com.openkm.module.BookmarkModule;
import com.openkm.util.FileUtils;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;

public class DirectBookmarkModule implements BookmarkModule {
	private static Logger log = LoggerFactory.getLogger(DirectBookmarkModule.class);
	
	@Override
	public Bookmark add(String token, String nodePath, String name) throws AccessDeniedException, 
			PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("add({}, {}, {})", new Object[] { token, nodePath, name });
		Bookmark newBookmark = null;
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			Node rootNode = session.getRootNode();
			Node node = rootNode.getNode(nodePath.substring(1));
			
			// Escape dangerous chars in name
			name = FileUtils.escape(name);

			newBookmark = new Bookmark();
			newBookmark.setUser(session.getUserID());
			newBookmark.setName(name);
			newBookmark.setPath(nodePath);
			newBookmark.setUuid(node.getUUID());
			newBookmark.setType(getNodeType(node));
			BookmarkDAO.create(newBookmark);
			
			// Activity log
			UserActivity.log(session, "BOOKMARK_ADD", name, nodePath);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			throw e;
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("add: {}", newBookmark);
		return newBookmark;
	}

	@Override
	public void remove(String token, int bmId) throws AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("remove({}, {})", token, bmId);
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}

			BookmarkDAO.delete(bmId);
			
			// Activity log
			UserActivity.log(session, "BOOKMARK_REMOVE", Integer.toString(bmId), null);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			throw e;
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("remove: void");
	}

	@Override
	public Bookmark rename(String token, int bmId, String newName) throws AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("rename({}, {}, {})", new Object[] { token, bmId, newName });
		Bookmark renamedBookmark = null;
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			Bookmark bm = BookmarkDAO.findByPk(bmId);
			bm.setName(newName);
			BookmarkDAO.update(bm);
						
			// Activity log
			UserActivity.log(session, "BOOKMARK_RENAME", Integer.toString(bmId), newName);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			throw e;
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("rename: {}", renamedBookmark);
		return renamedBookmark;
	}

	@Override
	public Collection<Bookmark> getAll(String token) throws RepositoryException, DatabaseException {
		log.debug("getAll({})", token);
		Collection<Bookmark> ret = new ArrayList<Bookmark>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			BookmarkDAO.findByUser(session.getUserID());
						
			// Activity log
			UserActivity.log(session, "BOOKMARK_GET_ALL", null, null);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			throw e;
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getAll: {}", ret);
		return ret;
	}

	@Override
	public void setUserHome(String token, String nodePath) throws AccessDeniedException, 
			RepositoryException, DatabaseException {
		log.debug("setUserHome({}, {})", token, nodePath);
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			Node rootNode = session.getRootNode();
			Node node = rootNode.getNode(nodePath.substring(1));
			UserConfig uc = new UserConfig();
			uc.setHomePath(nodePath);
			uc.setHomeUuid(node.getUUID());
			uc.setHomeType(getNodeType(node));
			UserConfigDAO.setHome(uc);
			
			// Activity log
			UserActivity.log(session, "BOOKMARK_SET_USER_HOME", null, nodePath);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			throw e;
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("setUserHome: void");
	}

	@Override
	public Bookmark getUserHome(String token) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		log.debug("getUserHome({})", token);
		Bookmark ret = new Bookmark();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			UserConfigDAO.findByPk(session.getUserID());
			
			// Activity log
			UserActivity.log(session, "BOOKMARK_GET_USER_HOME", null, ret.getPath());
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			throw e;
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getUserHome: {}", ret);
		return ret;
	}

	/**
	 * Get node type
	 */
	private String getNodeType(Node node) throws javax.jcr.RepositoryException  {
		String ret = "unknown";

		if (node.isNodeType(Document.TYPE)) {
			ret = Document.TYPE;
		} else if (node.isNodeType(Folder.TYPE)) {
			ret = Folder.TYPE;
		}

		return ret;
	}
}
