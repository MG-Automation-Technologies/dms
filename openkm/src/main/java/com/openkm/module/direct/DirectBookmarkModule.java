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
import javax.jcr.NodeIterator;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Bookmark;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Repository;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.ItemExistsException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.SessionManager;
import com.openkm.module.BookmarkModule;
import com.openkm.util.FileUtils;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;

public class DirectBookmarkModule implements BookmarkModule {
	private static Logger log = LoggerFactory.getLogger(DirectBookmarkModule.class);
	
	@Override
	public Bookmark add(String token, String nodePath, String name) throws AccessDeniedException, 
			PathNotFoundException, ItemExistsException, RepositoryException {
		log.debug("add({}, {}, {})", new Object[] { token, nodePath, name });
		Bookmark newBookmark = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			Session session = SessionManager.getInstance().get(token);
			Node rootNode = session.getRootNode();
			Node userBookmarkList = rootNode.getNode(Repository.HOME+"/"+session.getUserID()+"/"+Bookmark.LIST);
			Node node = rootNode.getNode(nodePath.substring(1));

			// Escape dangerous chars in name
			name = FileUtils.escape(name);

			Node bookmark = userBookmarkList.addNode(name, Bookmark.TYPE);
			bookmark.setProperty(Bookmark.NODE_PATH, nodePath);
			bookmark.setProperty(Bookmark.NODE_TYPE, getNodeType(node));
			userBookmarkList.save();

			// Set returned bookmark properties
			newBookmark = getProperties(session, bookmark.getPath());
			
			// Activity log
			UserActivity.log(session, "BOOKMARK_ADD", name, nodePath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.ItemExistsException e) {
			log.warn(e.getMessage(), e);
			throw new ItemExistsException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("add: {}", newBookmark);
		return newBookmark;
	}

	@Override
	public void remove(String token, String name) throws AccessDeniedException, PathNotFoundException,
			RepositoryException {
		log.debug("remove({}, {})", token, name);
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			Session session = SessionManager.getInstance().get(token);
			Node userBookmarkList = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+Bookmark.LIST);

			// Escape dangerous chars in name
			name = FileUtils.escape(name);

			Node savedBookmark = userBookmarkList.getNode(name);
			savedBookmark.remove();
			userBookmarkList.save();
			
			// Activity log
			UserActivity.log(session, "BOOKMARK_REMOVE", name, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("remove: void");
	}

	@Override
	public Bookmark rename(String token, String name, String newName) throws AccessDeniedException,
			PathNotFoundException, ItemExistsException, RepositoryException {
		log.debug("rename:({}, {}, {})", new Object[] { token, name, newName });
		Bookmark renamedBookmark = null;
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			session = SessionManager.getInstance().get(token);
			Node userBookmarkList = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+Bookmark.LIST);

			// Escape dangerous chars in name
			name = FileUtils.escape(name);
			newName = FileUtils.escape(newName);

			String oldPath = userBookmarkList.getPath()+"/"+name;

			if (newName != null && !newName.equals("") && !newName.equals(name)) {
				String newPath = userBookmarkList.getPath()+"/"+newName;
				session.move(oldPath, newPath);

				// Publish changes
				session.save();

				// Set returned bookmark properties
				renamedBookmark = getProperties(session, newPath);
			} else {
				// Don't change anything
				renamedBookmark = getProperties(session, oldPath);
			}
			
			// Activity log
			UserActivity.log(session, "BOOKMARK_RENAME", name, newName);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.ItemExistsException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new ItemExistsException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("rename: {}", renamedBookmark);
		return renamedBookmark;
	}

	@Override
	public Collection<Bookmark> getAll(String token) throws RepositoryException {
		log.debug("getAll({})", token);
		Collection<Bookmark> ret = new ArrayList<Bookmark>();

		try {
			Session session = SessionManager.getInstance().get(token);
			Node userBookmarkList = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+Bookmark.LIST);

			for (NodeIterator it = userBookmarkList.getNodes(); it.hasNext(); ) {
				Node bm = it.nextNode();
				Bookmark bookmark = getProperties(session, bm.getPath());
				ret.add(bookmark);
			}
			
			// Activity log
			UserActivity.log(session, "BOOKMARK_GET_ALL", null, null);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getAll: {}", ret);
		return ret;
	}

	@Override
	public void setUserHome(String token, String nodePath) throws AccessDeniedException, 
			RepositoryException {
		log.debug("setUserHome({}, {})", token, nodePath);
		Node userConfig = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			Session session = SessionManager.getInstance().get(token);
			Node rootNode = session.getRootNode();
			userConfig = rootNode.getNode(Repository.HOME+"/"+session.getUserID()+"/"+Repository.USER_CONFIG);
			Node node = rootNode.getNode(nodePath.substring(1));
			userConfig.setProperty(Bookmark.HOME_PATH, nodePath);
			userConfig.setProperty(Bookmark.HOME_TYPE, getNodeType(node));
			
			// Activity log
			UserActivity.log(session, "BOOKMARK_SET_USER_HOME", null, nodePath);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(userConfig);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("setUserHome: void");
	}

	@Override
	public Bookmark getUserHome(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getUserHome({})", token);
		Bookmark ret = new Bookmark();

		try {
			Session session = SessionManager.getInstance().get(token);
			Node userConfig = session.getRootNode().getNode(Repository.HOME+"/"+
					session.getUserID()+"/"+Repository.USER_CONFIG);
			ret.setName(Bookmark.HOME_PATH);
			ret.setPath(userConfig.getProperty(Bookmark.HOME_PATH).getString());
			ret.setType(userConfig.getProperty(Bookmark.HOME_TYPE).getString());
			
			// Activity log
			UserActivity.log(session, "BOOKMARK_GET_USER_HOME", null, ret.getPath());
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getUserHome: {}", ret);
		return ret;
	}

	/**
	 * Get bookmark info
	 */
	private Bookmark getProperties(Session session, String nodePath) throws
			javax.jcr.PathNotFoundException, javax.jcr.RepositoryException {
		log.debug("getBookmark({}, {})", session, nodePath);
		Bookmark bm = new Bookmark();
		Node bookmark = session.getRootNode().getNode(nodePath.substring(1));

		// Properties
		bm.setName(bookmark.getName());
		bm.setPath(bookmark.getProperty(Bookmark.NODE_PATH).getString());
		bm.setType(bookmark.getProperty(Bookmark.NODE_TYPE).getString());

		// Unescape dangerous chars in name
		bm.setName(bm.getName());

		log.debug("getBookmark: {}", bm);
		return bm;
	}

	/**
	 * @param node
	 * @return
	 * @throws javax.jcr.RepositoryException
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
