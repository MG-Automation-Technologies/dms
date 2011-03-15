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

package com.openkm.module.base;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.PropertyType;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.core.NodeImpl;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.security.AccessManager;
import org.apache.jackrabbit.spi.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Folder;
import com.openkm.bean.Lock;
import com.openkm.bean.Notification;
import com.openkm.bean.Permission;
import com.openkm.bean.Property;
import com.openkm.bean.Version;
import com.openkm.bean.Wiki;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.UserQuotaExceededException;
import com.openkm.dao.bean.cache.UserItems;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;

public class BaseWikiModule {
	private static Logger log = LoggerFactory.getLogger(BaseWikiModule.class);
	
	/**
	 * Create a new wiki
	 */
	public static Node create(Session session, Node parentNode, String name, String[] keywords,
			String content) throws javax.jcr.ItemExistsException, javax.jcr.PathNotFoundException,
			javax.jcr.AccessDeniedException, javax.jcr.RepositoryException,	IOException,
			DatabaseException {
		log.debug("create({}, {}, {}, {}, {})", new Object[] { session, parentNode, name, keywords, content });

		// Create and add a new file node
		Node wikiNode = parentNode.addNode(name, Wiki.TYPE);
		wikiNode.setProperty(Property.KEYWORDS, keywords);
		wikiNode.setProperty(Property.CATEGORIES, new String[]{}, PropertyType.REFERENCE);
		wikiNode.setProperty(Wiki.AUTHOR, session.getUserID());
		wikiNode.setProperty(Wiki.NAME, name);
		
		// Get parent node auth info
		Value[] usersReadParent = parentNode.getProperty(Permission.USERS_READ).getValues();
		String[] usersRead = JCRUtils.usrValue2String(usersReadParent, session.getUserID());
		Value[] usersWriteParent = parentNode.getProperty(Permission.USERS_WRITE).getValues();
		String[] usersWrite = JCRUtils.usrValue2String(usersWriteParent, session.getUserID());
		Value[] usersDeleteParent = parentNode.getProperty(Permission.USERS_DELETE).getValues();
		String[] usersDelete = JCRUtils.usrValue2String(usersDeleteParent, session.getUserID());
		Value[] usersSecurityParent = parentNode.getProperty(Permission.USERS_SECURITY).getValues();
		String[] usersSecurity = JCRUtils.usrValue2String(usersSecurityParent, session.getUserID());

		Value[] rolesReadParent = parentNode.getProperty(Permission.ROLES_READ).getValues();
		String[] rolesRead = JCRUtils.rolValue2String(rolesReadParent);
		Value[] rolesWriteParent = parentNode.getProperty(Permission.ROLES_WRITE).getValues();
		String[] rolesWrite = JCRUtils.rolValue2String(rolesWriteParent);
		Value[] rolesDeleteParent = parentNode.getProperty(Permission.ROLES_DELETE).getValues();
		String[] rolesDelete = JCRUtils.rolValue2String(rolesDeleteParent);
		Value[] rolesSecurityParent = parentNode.getProperty(Permission.ROLES_SECURITY).getValues();
		String[] rolesSecurity = JCRUtils.rolValue2String(rolesSecurityParent);

		// Set auth info
		wikiNode.setProperty(Permission.USERS_READ, usersRead);
		wikiNode.setProperty(Permission.USERS_WRITE, usersWrite);
		wikiNode.setProperty(Permission.USERS_DELETE, usersDelete);
		wikiNode.setProperty(Permission.USERS_SECURITY, usersSecurity);
		wikiNode.setProperty(Permission.ROLES_READ, rolesRead);
		wikiNode.setProperty(Permission.ROLES_WRITE, rolesWrite);
		wikiNode.setProperty(Permission.ROLES_DELETE, rolesDelete);
		wikiNode.setProperty(Permission.ROLES_SECURITY, rolesSecurity);

		Node contentNode = wikiNode.addNode(Wiki.CONTENT, Wiki.CONTENT_TYPE);
		contentNode.setProperty(Wiki.SIZE, content.length());
		contentNode.setProperty(Wiki.AUTHOR, session.getUserID());
		contentNode.setProperty(Wiki.VERSION_COMMENT, "");
		contentNode.setProperty(JcrConstants.JCR_MIMETYPE, "text/plain");
		contentNode.setProperty(JcrConstants.JCR_ENCODING, "UTF-8");
		
		contentNode.setProperty(JcrConstants.JCR_DATA, content);
		contentNode.setProperty(JcrConstants.JCR_LASTMODIFIED, Calendar.getInstance());
		parentNode.save();

		// Esta línea vale millones!! Resuelve la incidencia del isCkechedOut.
		// Por lo visto un nuevo nodo se añade con el isCheckedOut a true :/
		contentNode.checkin();
		
		// Update user items size
		//if (Config.USER_ITEM_CACHE) {
		//	UserItemsManager.incWikis(session.getUserID(), 1);
		//}
		
		return wikiNode;
	}
	
	/**
	 * Get wiki properties using a given Session.
	 */
	public static Wiki getProperties(Session session, Node wikiNode) throws javax.jcr.PathNotFoundException,
			javax.jcr.RepositoryException {
		log.debug("getProperties({}, {})", session, wikiNode);
		Wiki wiki = new Wiki();
		Node contentNode = wikiNode.getNode(Wiki.CONTENT);

		// Properties
		wiki.setAuthor(wikiNode.getProperty(Wiki.AUTHOR).getString());
		wiki.setPath(wikiNode.getPath());
		wiki.setLocked(wikiNode.isLocked());
		wiki.setUuid(wikiNode.getUUID());
		
		if (wiki.isLocked()) {
			wiki.setLockInfo(getLock(session, wikiNode.getPath()));
		} else {
			wiki.setLockInfo(null);
		}

		wiki.setCheckedOut(contentNode.isCheckedOut());
		wiki.setLastModified(contentNode.getProperty(JcrConstants.JCR_LASTMODIFIED).getDate());

		// Get actual version
		if (wikiNode.isNodeType(Wiki.TYPE)) {
			javax.jcr.version.Version ver = contentNode.getBaseVersion();
			Version version = new Version();
			version.setAuthor(contentNode.getProperty(Wiki.AUTHOR).getString());
			version.setSize(contentNode.getProperty(Wiki.SIZE).getLong());
			version.setComment(contentNode.getProperty(Wiki.VERSION_COMMENT).getString());
			version.setName(ver.getName());
			version.setCreated(ver.getCreated());
			version.setActual(true);
			wiki.setActualVersion(version);
		}

		// If this is a frozen node, we must get create property from
		// the original referenced node.
		if (wikiNode.isNodeType(JcrConstants.NT_FROZENNODE)) {
			Node node = wikiNode.getProperty(JcrConstants.JCR_FROZENUUID).getNode();
			wiki.setCreated(node.getProperty(JcrConstants.JCR_CREATED).getDate());
		} else {
			wiki.setCreated(wikiNode.getProperty(JcrConstants.JCR_CREATED).getDate());
		}

		// Get permissions
		if (Config.SYSTEM_READONLY) {
			wiki.setPermissions(Permission.NONE);
		} else {
			AccessManager am = ((SessionImpl) session).getAccessManager();
			Path path = ((NodeImpl)wikiNode).getPrimaryPath();
			//Path path = ((SessionImpl)session).getHierarchyManager().getPath(((NodeImpl)folderNode).getId());
			if (am.isGranted(path, org.apache.jackrabbit.core.security.authorization.Permission.READ)) {
				wiki.setPermissions(Permission.READ);
			}
			
			if (am.isGranted(path, org.apache.jackrabbit.core.security.authorization.Permission.ADD_NODE)) {
				wiki.setPermissions((byte) (wiki.getPermissions() | Permission.WRITE));
			}
			
			if (am.isGranted(path, org.apache.jackrabbit.core.security.authorization.Permission.REMOVE_NODE)) {
				wiki.setPermissions((byte) (wiki.getPermissions() | Permission.DELETE));
			}
			
			if (am.isGranted(path, org.apache.jackrabbit.core.security.authorization.Permission.MODIFY_AC)) {
				wiki.setPermissions((byte) (wiki.getPermissions() | Permission.SECURITY));
			}
		}
		
		// Get user subscription
		Set<String> subscriptorSet = new HashSet<String>();

		if (wikiNode.isNodeType(Notification.TYPE)) {
			Value[] subscriptors = wikiNode.getProperty(Notification.SUBSCRIPTORS).getValues();

			for (int i=0; i<subscriptors.length; i++) {
				subscriptorSet.add(subscriptors[i].getString());

				if (session.getUserID().equals(subscriptors[i].getString())) {
					wiki.setSubscribed(true);
				}
			}
		}

		wiki.setSubscriptors(subscriptorSet);
		
		// Get wiki keywords
		Set<String> keywordsSet = new HashSet<String>();
		Value[] keywords = wikiNode.getProperty(Property.KEYWORDS).getValues();

		for (int i=0; i<keywords.length; i++) {
			keywordsSet.add(keywords[i].getString());
		}

		wiki.setKeywords(keywordsSet);
		
		// Get wiki categories
		Set<Folder> categoriesSet = new HashSet<Folder>();
		Value[] categories = wikiNode.getProperty(Property.CATEGORIES).getValues();

		for (int i=0; i<categories.length; i++) {
			Node node = session.getNodeByUUID(categories[i].getString());
			categoriesSet.add(BaseFolderModule.getProperties(session, node));
		}

		wiki.setCategories(categoriesSet);
		
		// Get notes
		/*
		if (wikiNode.isNodeType(Note.MIX_TYPE)) {
			List<Note> notes = new ArrayList<Note>();
			Node notesNode = wikiNode.getNode(Note.LIST);
			
			for (NodeIterator nit = notesNode.getNodes(); nit.hasNext(); ) {
				Node noteNode = nit.nextNode();
				Note note = new Note();
				note.setDate(noteNode.getProperty(Note.DATE).getDate());
				note.setUser(noteNode.getProperty(Note.USER).getString());
				note.setText(noteNode.getProperty(Note.TEXT).getString());
				note.setPath(noteNode.getPath());
				notes.add(note);
			}
			
			wiki.setNotes(notes);
		}
		*/
		
		log.debug("Permisos: {} => {}", wikiNode.getPath(), wiki.getPermissions());
		log.debug("getProperties[session]: {}", wiki);
		return wiki;
	}
	
	/**
	 * Retrieve lock info from a wiki path
	 */
	public static Lock getLock(Session session, String wikiPath) throws UnsupportedRepositoryOperationException,
			javax.jcr.lock.LockException, javax.jcr.AccessDeniedException, javax.jcr.RepositoryException {
		log.debug("getLock({}, {})", session, wikiPath);
		Lock lock = new Lock();
		Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
		javax.jcr.lock.Lock lck = wikiNode.getLock();
		lock.setOwner(lck.getLockOwner());
		lock.setNodePath(lck.getNode().getPath());
		lock.setToken(lck.getLockToken());
		log.debug("getLock: {}", lock);
		return lock;
	}
	
	/**
	 * Retrieve the content input stream from a wiki path
	 */
	public static String getContent(Session session, String wikiPath, boolean checkout) throws 
			javax.jcr.PathNotFoundException, javax.jcr.RepositoryException, IOException {
		Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
		String ret = getContent(session, wikiNode);
		
		// Activity log
		UserActivity.log(session.getUserID(), (checkout?"GET_WIKI_CONTENT_CHECKOUT":"GET_WIKI_CONTENT"), wikiNode.getUUID(), ret.length()+", "+wikiPath);
		
		return ret;
	}
	
	/**
	 * Retrieve the content InputStream from a given Node. 
	 */
	public static String getContent(Session session, Node wikiNode) throws javax.jcr.PathNotFoundException,
			javax.jcr.RepositoryException, IOException {
		log.debug("getContent({}, {})", session, wikiNode);
		
		Node contentNode = wikiNode.getNode(Wiki.CONTENT);
		String ret = contentNode.getProperty(JcrConstants.JCR_DATA).getString();
		
		log.debug("getContent: {}", ret);
		return ret;
	}
	
	/**
	 * Remove version history, compute free space and remove obsolete files.
	 */
	public static HashMap<String, UserItems> purge(Session session, Node parentNode, Node wikiNode) 
			throws javax.jcr.PathNotFoundException, javax.jcr.RepositoryException {
		Node contentNode = wikiNode.getNode(Wiki.CONTENT);
		//long size = contentNode.getProperty(Wiki.SIZE).getLong();
		//String author = contentNode.getProperty(Wiki.AUTHOR).getString();
		VersionHistory vh = contentNode.getVersionHistory();
		HashMap<String, UserItems> userItemsHash = new HashMap<String, UserItems>();
		log.debug("VersionHistory UUID: {}", vh.getUUID());
		
		// Remove node itself
		wikiNode.remove();
		parentNode.save();

		// Unreferenced VersionHistory should be deleted automatically
		// https://issues.apache.org/jira/browse/JCR-134
		// http://markmail.org/message/7aildokt74yeoar5
		// http://markmail.org/message/nhbwe7o3c7pd4sga
		for (VersionIterator vi = vh.getAllVersions(); vi.hasNext(); ) {
			javax.jcr.version.Version ver = vi.nextVersion();
			String versionName = ver.getName();
			log.debug("Version: {}", versionName);
			
			// The rootVersion is not a "real" version node.
			if (!versionName.equals(JcrConstants.JCR_ROOTVERSION)) {
				//Node frozenNode = ver.getNode(JcrConstants.JCR_FROZENNODE);
				//size = frozenNode.getProperty(Wiki.SIZE).getLong();
				//author = frozenNode.getProperty(Wiki.AUTHOR).getString();
				log.debug("vh.removeVersion({})", versionName);
				vh.removeVersion(versionName);
				
				//if (Config.USER_ITEM_CACHE) {
					// Update local user items for versions
					//UserItems userItems = userItemsHash.get(author);
					//if (userItems == null) userItems = new UserItems();
					//userItems.setWikis(userItems.getWikis() + 1);
					//userItemsHash.put(author, userItems);
				//}
			}
		}
		
		//if (Config.USER_ITEM_CACHE) {
			// Update local user items for working version
			//UserItems userItems = userItemsHash.get(author);
			//if (userItems == null) userItems = new UserItems();
			//userItems.setWikis(userItems.getWikis() + 1);
			//userItemsHash.put(author, userItems);
		//}
		
		return userItemsHash;
	}
	
	/**
	 * Is invoked from DirectWikiNode
	 */
	public static void copy(Session session, Node srcWikiNode, Node dstFolderNode) throws
			ValueFormatException, javax.jcr.PathNotFoundException, javax.jcr.RepositoryException,
			IOException, DatabaseException, UserQuotaExceededException {
		log.debug("copy({}, {}, {})", new Object[] { session, srcWikiNode, dstFolderNode });
		
		Node srcWikiContentNode = srcWikiNode.getNode(Wiki.CONTENT);
		// String title = srcWikiContentNode.getProperty(Wiki.TITLE).getString();
		String content = srcWikiContentNode.getProperty("jcr:data").getString();
		BaseWikiModule.create(session, dstFolderNode, srcWikiNode.getName(), new String[]{}, content);
		
		log.debug("copy: void");
	}
}
