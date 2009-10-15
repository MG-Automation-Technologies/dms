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

package es.git.openkm.module.direct;

import java.util.ArrayList;
import java.util.Collection;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Bookmark;
import es.git.openkm.bean.Document;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.Repository;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.SessionManager;
import es.git.openkm.module.BookmarkModule;
import es.git.openkm.util.FileUtils;
import es.git.openkm.util.JCRUtils;
import es.git.openkm.util.UserActivity;

public class DirectBookmarkModule implements BookmarkModule {
	private static Logger log = LoggerFactory.getLogger(DirectBookmarkModule.class);

	/* (non-Javadoc)
	 * @see es.git.openkm.module.BookmarkModule#add(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Bookmark add(String token, String nodePath, String name) throws
			PathNotFoundException, ItemExistsException, RepositoryException {
		log.debug("add("+token+", "+nodePath+", "+name+")");
		Bookmark newBookmark = null;

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

		log.debug("add: "+newBookmark);
		return newBookmark;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.BookmarkModule#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String token, String name) throws PathNotFoundException,
			RepositoryException {
		log.debug("remove("+token+", "+name+")");

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

	/* (non-Javadoc)
	 * @see es.git.openkm.module.BookmarkModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Bookmark rename(String token, String name, String newName) throws
			PathNotFoundException, ItemExistsException, RepositoryException {
		log.debug("rename:(" + token + ", " + name + ", " + newName + ")");
		Bookmark renamedBookmark = null;
		Session session = null;

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

		log.debug("rename: "+renamedBookmark);
		return renamedBookmark;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.BookmarkModule#getAll(java.lang.String)
	 */
	@Override
	public Collection<Bookmark> getAll(String token) throws RepositoryException {
		log.debug("getAll("+token+")");
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

		log.debug("getAll: "+ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.BookmarkModule#setUserHome(java.lang.String, java.lang.String)
	 */
	@Override
	public void setUserHome(String token, String nodePath) throws RepositoryException {
		log.debug("setUserHome("+token+", "+nodePath+")");
		Node userConfig = null;

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

	/* (non-Javadoc)
	 * @see es.git.openkm.module.BookmarkModule#getUserHome(java.lang.String)
	 */
	@Override
	public Bookmark getUserHome(String token) throws PathNotFoundException, RepositoryException {
		log.debug("getUserHome("+token+")");
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

		log.debug("getUserHome: "+ret);
		return ret;
	}

	/**
	 * Get bookmark info
	 *
	 * @param session User session
	 * @param name Bookmark name
	 * @return The bookmakr info
	 * @throws javax.jcr.PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 */
	private Bookmark getProperties(Session session, String nodePath) throws
			javax.jcr.PathNotFoundException, javax.jcr.RepositoryException {
		log.debug("getBookmark(" + session + ", " + nodePath + ")");
		Bookmark bm = new Bookmark();
		Node bookmark = session.getRootNode().getNode(nodePath.substring(1));

		// Properties
		bm.setName(bookmark.getName());
		bm.setPath(bookmark.getProperty(Bookmark.NODE_PATH).getString());
		bm.setType(bookmark.getProperty(Bookmark.NODE_TYPE).getString());

		// Unescape dangerous chars in name
		bm.setName(bm.getName());

		log.debug("getBookmark: "+bm);
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
