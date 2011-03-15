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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.api.XASession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Lock;
import com.openkm.bean.Repository;
import com.openkm.bean.Version;
import com.openkm.bean.Wiki;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.JcrSessionManager;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UserQuotaExceededException;
import com.openkm.core.VersionException;
import com.openkm.dao.bean.cache.UserItems;
import com.openkm.module.WikiModule;
import com.openkm.module.base.BaseNotificationModule;
import com.openkm.module.base.BaseScriptingModule;
import com.openkm.module.base.BaseWikiModule;
import com.openkm.util.FileUtils;
import com.openkm.util.JCRUtils;
import com.openkm.util.Transaction;
import com.openkm.util.UserActivity;

public class DirectWikiModule implements WikiModule {
	private static Logger log = LoggerFactory.getLogger(DirectWikiModule.class);
	
	@Override
	public Wiki create(String token, Wiki wiki, String content) throws ItemExistsException,
			PathNotFoundException, AccessDeniedException, RepositoryException, IOException,
			DatabaseException {
		log.debug("create({})", wiki);
		Wiki newWiki = null;
		Node parentNode = null;
		Session session = null;
				
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		String parent = FileUtils.getParent(wiki.getPath());
		String name = FileUtils.getName(wiki.getPath());
				
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			// Escape dangerous chars in name
			name = FileUtils.escape(name);
			wiki.setPath(parent+"/"+name);
			
			parentNode = session.getRootNode().getNode(parent.substring(1));
			Node wikiNode = BaseWikiModule.create(session, parentNode, name, 
					wiki.getKeywords().toArray(new String[wiki.getKeywords().size()]), content);
			
			// Set returned wiki properties
			newWiki = BaseWikiModule.getProperties(session, wikiNode);
			
			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(wikiNode, session.getUserID(), "CREATE_WIKI", null);
			
			// Check scripting
			BaseScriptingModule.checkScripts(session, parentNode, wikiNode, "CREATE_WIKI");
			
			// Activity log
			UserActivity.log(session.getUserID(), "CREATE_WIKI", wikiNode.getUUID(), wiki.getPath());
		} catch (javax.jcr.ItemExistsException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new ItemExistsException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new RepositoryException(e.getMessage(), e);
		} catch (java.io.IOException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw e;
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("create: {}", newWiki);
		return newWiki;
	}
	
	@Override
	public void delete(String token, String wikiPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, LockException, DatabaseException {
		log.debug("delete({}, {})", token, wikiPath);
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			String name = FileUtils.getName(wikiPath);
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			Node parentNode = wikiNode.getParent();
			Node userTrash = session.getRootNode().getNode(Repository.TRASH+"/"+session.getUserID());

			if (wikiNode.isLocked()) {
				throw new LockException("Can't delete a locked node");
			}

			// Test if already exists a wiki whith the same name in the trash
			String destPath = userTrash.getPath()+"/";
			String testName = name;
			String fileName = FileUtils.getFileName(name);
			String fileExtension = FileUtils.getFileExtension(name);

			for (int i=1; session.itemExists(destPath+testName); i++) {
				testName = fileName+" ("+i+")."+fileExtension;
			}

			session.move(wikiNode.getPath(), destPath+testName);
			session.getRootNode().save();

			// Check scripting
			BaseScriptingModule.checkScripts(session, parentNode, wikiNode, "DELETE_WIKI");

			// Activity log
			UserActivity.log(session.getUserID(), "DELETE_WIKI", wikiNode.getUUID(), wikiPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("delete: void");
	}
	
	@Override
	public Wiki getProperties(String token, String wikiPath) throws RepositoryException, 
			PathNotFoundException, DatabaseException {
		log.debug("getProperties({}, {})", token, wikiPath);
		Wiki wiki = null;
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			wiki = BaseWikiModule.getProperties(session, wikiNode);

			// Activity log
			UserActivity.log(session.getUserID(), "GET_WIKI_PROPERTIES", wikiNode.getUUID(), wiki.getKeywords().toString()+", "+wikiPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("getProperties: {}", wiki);
		return wiki;
	}
	
	@Override
	public String getContent(String token, String wikiPath, boolean checkout) throws 
			PathNotFoundException, RepositoryException, IOException, DatabaseException {
		log.debug("getContent({}, {}, {})", new Object[] { token, wikiPath, checkout });
		Session session = null;
		String ret;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			ret = BaseWikiModule.getContent(session, wikiPath, checkout);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("getContent: {}", ret);
		return ret;
	}
	
	@Override
	public String getContentByVersion(String token, String wikiPath, String versionId) throws 
			RepositoryException, PathNotFoundException, IOException, DatabaseException {
		log.debug("getContentByVersion({}, {}, {})", new Object[] { token, wikiPath, versionId });
		Session session = null;
		String ret;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			Node contentNode = wikiNode.getNode(Wiki.CONTENT);
			VersionHistory vh = contentNode.getVersionHistory();
			javax.jcr.version.Version ver = vh.getVersion(versionId);
			Node frozenNode = ver.getNode(JcrConstants.JCR_FROZENNODE);
			ret = frozenNode.getProperty(JcrConstants.JCR_DATA).getString();

			// Activity log
			UserActivity.log(session.getUserID(), "GET_WIKI_CONTENT_BY_VERSION", wikiNode.getUUID(), versionId+", "+ret.length()+", "+wikiPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("getContentByVersion: {}", ret);
		return ret;
	}
	
	@Override
	public void setContent(String token, String wikiPath, String content) throws VersionException,
			LockException, PathNotFoundException, AccessDeniedException, RepositoryException,
			IOException, DatabaseException {
		log.debug("setContent({}, {}, {})", new Object[] { token, wikiPath, content });
		Node contentNode = null;
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			JCRUtils.loadLockTokens(session);
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			contentNode = wikiNode.getNode(Wiki.CONTENT);
			contentNode.setProperty(Wiki.SIZE, content.length());
			contentNode.setProperty(JcrConstants.JCR_DATA, content);
			contentNode.setProperty(JcrConstants.JCR_LASTMODIFIED, Calendar.getInstance());
			contentNode.save();

			// Check scripting
			BaseScriptingModule.checkScripts(session, wikiNode, wikiNode, "SET_WIKI_CONTENT");

			// Activity log
			UserActivity.log(session.getUserID(), "SET_WIKI_CONTENT", wikiNode.getUUID(), content.length()+", "+wikiPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(contentNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(contentNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.version.VersionException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(contentNode);
			throw new VersionException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(contentNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(contentNode);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("setContent: void");
	}
	
	@Override
	public List<Wiki> getChilds(String token, String fldPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getChilds({}, {})", token, fldPath);
		List<Wiki> childs = new ArrayList<Wiki>();
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));

			for (NodeIterator ni = folderNode.getNodes(); ni.hasNext(); ) {
				Node child = ni.nextNode();
				log.debug("Child: "+child.getPath()+", "+child.getPrimaryNodeType().getName());

				if (child.isNodeType(Wiki.TYPE)) {
					childs.add(BaseWikiModule.getProperties(session, child));
				}
			}

			// Activity log
			UserActivity.log(session.getUserID(), "GET_CHILD_WIKIS", folderNode.getUUID(), fldPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("getChilds: {}", childs);
		return childs;
	}
	
	@Override
	public Wiki rename(String token, String wikiPath, String newName) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("rename:({}, {}, {})", new Object[] { token, wikiPath, newName });
		Wiki renamedWiki = null;
		Session session = null;
		Node wikiNode = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			String parent = FileUtils.getParent(wikiPath);
			String name = FileUtils.getName(wikiPath);
			
			// Escape dangerous chars in name
			newName = FileUtils.escape(newName);

			if (newName != null && !newName.equals("") && !newName.equals(name)) {
				String newPath = parent + "/" + newName;
				session.move(wikiPath, newPath);

				// Set new name
				wikiNode = session.getRootNode().getNode(newPath.substring(1));
				wikiNode.setProperty(Wiki.NAME, newName);

				// Publish changes
				session.save();

				// Set returned wiki properties
				renamedWiki = BaseWikiModule.getProperties(session, wikiNode);
			} else {
				// Don't change anything
				wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
				renamedWiki = BaseWikiModule.getProperties(session, wikiNode);				
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "RENAME_WIKI", wikiNode.getUUID(), newName+", "+wikiPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.ItemExistsException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new ItemExistsException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("rename: {}", renamedWiki);
		return renamedWiki;
	}
	
	@Override
	public void setProperties(String token, Wiki wiki) throws VersionException, LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("setProperties({}, {})", token, wiki);
		Node wikiNode = null;
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			wikiNode = session.getRootNode().getNode(wiki.getPath().substring(1));
			
			// Update wiki keyword cache
			//UserKeywordsManager.put(session.getUserID(), wikiNode.getUUID(), wiki.getKeywords());
			
			// Update wiki title
			// wikiNode.setProperty(Wiki.TITLE, wiki.getTitle() == null ? "" : wiki.getTitle());

			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(wikiNode, session.getUserID(), "SET_WIKI_PROPERTIES", null);

			// Check scripting
			BaseScriptingModule.checkScripts(session, wikiNode, wikiNode, "SET_WIKI_PROPERTIES");

			// Activity log
			UserActivity.log(session.getUserID(), "SET_WIKI_PROPERTIES", wikiNode.getUUID(), wiki.getPath());
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(wikiNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(wikiNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.version.VersionException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(wikiNode);
			throw new VersionException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(wikiNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(wikiNode);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("setProperties: void");
	}
	
	@Override
	public void checkout(String token, String wikiPath) throws AccessDeniedException, RepositoryException, 
			PathNotFoundException, LockException, DatabaseException {
		log.debug("checkout({}, {})", token, wikiPath);
		Transaction t = null;
		XASession session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (token == null) {
				session = (XASession) JCRUtils.getSession();
			} else {
				session = (XASession) JcrSessionManager.getInstance().get(token);
			}
			
			javax.jcr.lock.Lock lck = null;

			t = new Transaction(session);
			t.start();

			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			Node contentNode = wikiNode.getNode(Wiki.CONTENT);
			lck = wikiNode.lock(true, false);
			JCRUtils.addLockToken(session, wikiNode);
			contentNode.checkout();

			t.end();
			t.commit();

			// Check scripting
			BaseScriptingModule.checkScripts(session, wikiNode, wikiNode, "CHECKOUT_WIKI");

			// Activity log
			UserActivity.log(session.getUserID(), "CHECKOUT_WIKI", wikiNode.getUUID(), lck.getLockToken()+", "+wikiPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			t.rollback();
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			t.rollback();
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw e;
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("checkout: void");
	}
	
	@Override
	public void cancelCheckout(String token, String wikiPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, LockException, DatabaseException {
		log.debug("cancelCheckout({}, {})", token, wikiPath);
		Transaction t = null;
		XASession session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (token == null) {
				session = (XASession) JCRUtils.getSession();
			} else {
				session = (XASession) JcrSessionManager.getInstance().get(token);
			}
			
			t = new Transaction(session);
			t.start();
			
			JCRUtils.loadLockTokens(session);
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			Node contentNode = wikiNode.getNode(Wiki.CONTENT);
			contentNode.restore(contentNode.getBaseVersion(), true);
			wikiNode.unlock();
			JCRUtils.removeLockToken(session, wikiNode);
			
			t.end();
			t.commit();

			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(wikiNode, session.getUserID(), "CANCEL_CHECKOUT_WIKI", null);

			// Check scripting
			BaseScriptingModule.checkScripts(session, wikiNode, wikiNode, "CANCEL_CHECKOUT_WIKI");

			// Activity log
			UserActivity.log(session.getUserID(), "CANCEL_CHECKOUT_WIKI", wikiNode.getUUID(), wikiPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			t.rollback();
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			t.rollback();
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw e;
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("cancelCheckout: void");
	}
	
	@Override
	public boolean isCheckedOut(String token, String wikiPath) throws RepositoryException,
			PathNotFoundException, DatabaseException {
		log.debug("isCheckedOut({}, {})", token, wikiPath);
		boolean checkedOut = false;
		Session session = null;

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			Node contentNode = wikiNode.getNode(Wiki.CONTENT);
			checkedOut = contentNode.isCheckedOut();
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("isCheckedOut: {}", checkedOut);
		return checkedOut;
	}
	
	@Override
	public Version checkin(String token, String wikiPath, String comment) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, LockException, VersionException, DatabaseException {
		log.debug("checkin({}, {}, {})", new Object[] { token, wikiPath, comment });
		Version version = new Version();
		Transaction t = null;
		XASession session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (token == null) {
				session = (XASession) JCRUtils.getSession();
			} else {
				session = (XASession) JcrSessionManager.getInstance().get(token);
			}
			
			t = new Transaction(session);
			t.start();
			
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			Node contentNode = null;
			
			synchronized (wikiNode) {
				JCRUtils.loadLockTokens(session);
				contentNode = wikiNode.getNode(Wiki.CONTENT);
				
				// Set version author
				contentNode.setProperty(Wiki.AUTHOR, session.getUserID());
				contentNode.setProperty(Wiki.VERSION_COMMENT, comment);
				contentNode.save();
				
				// Performs checkin & unlock
				javax.jcr.version.Version ver = contentNode.checkin();
				version.setAuthor(contentNode.getProperty(Wiki.AUTHOR).getString());
				version.setSize(contentNode.getProperty(Wiki.SIZE).getLong());
				version.setComment(contentNode.getProperty(Wiki.VERSION_COMMENT).getString());
				version.setName(ver.getName());
				version.setCreated(ver.getCreated());
				version.setActual(true);
				wikiNode.unlock();
				JCRUtils.removeLockToken(session, wikiNode);
			}
			
			t.end();
			t.commit();

			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(wikiNode, session.getUserID(), "CHECKIN_WIKI", comment);

			// Check scripting
			BaseScriptingModule.checkScripts(session, wikiNode, wikiNode, "CHECKIN_WIKI");

			// Activity log
			UserActivity.log(session.getUserID(), "CHECKIN_WIKI", wikiNode.getUUID(), comment+", "+wikiPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			t.rollback();
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			t.rollback();
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.version.VersionException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw new VersionException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw e;
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("checkin: {}", version);
		return version;
	}
	
	@Override
	public List<Version> getVersionHistory(String token, String wikiPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getVersionHistory({}, {})", token, wikiPath);
		List<Version> history = new ArrayList<Version>();
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			Node contentNode = wikiNode.getNode(Wiki.CONTENT);
			VersionHistory vh = contentNode.getVersionHistory();
			String baseVersion = contentNode.getBaseVersion().getName();

			for (VersionIterator vi = vh.getAllVersions(); vi.hasNext(); ) {
				javax.jcr.version.Version ver = vi.nextVersion();
				String versionName = ver.getName();

				// The rootVersion is not a "real" version node.
				if (!versionName.equals(JcrConstants.JCR_ROOTVERSION)) {
					Version version = new Version();
					Node frozenNode = ver.getNode(JcrConstants.JCR_FROZENNODE);
					version.setAuthor(frozenNode.getProperty(Wiki.AUTHOR).getString());
					version.setSize(frozenNode.getProperty(Wiki.SIZE).getLong());
					version.setComment(frozenNode.getProperty(Wiki.VERSION_COMMENT).getString());
					version.setName(ver.getName());
					version.setCreated(ver.getCreated());

					if (versionName.equals(baseVersion)) {
						version.setActual(true);
					} else {
						version.setActual(false);
					}

					history.add(version);
				}
			}
			
			// Reverse history
			Collections.reverse(history);

			// Activity log
			UserActivity.log(session.getUserID(), "GET_WIKI_VERSION_HISTORY", wikiNode.getUUID(), wikiPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("getVersionHistory: {}", history);
		return history;
	}
	
	@Override
	public void lock(String token, String wikiPath) throws AccessDeniedException, RepositoryException, 
			PathNotFoundException, LockException, DatabaseException {
		log.debug("lock({})", wikiPath);
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			javax.jcr.lock.Lock lck = wikiNode.lock(true, false);
			JCRUtils.addLockToken(session, wikiNode);
			
			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(wikiNode, session.getUserID(), "LOCK_WIKI", null);

			// Check scripting
			BaseScriptingModule.checkScripts(session, wikiNode, wikiNode, "LOCK_WIKI");

			// Activity log
			UserActivity.log(session.getUserID(), "LOCK_WIKI", wikiNode.getUUID(), lck.getLockToken()+", "+wikiPath);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			if (token == null) JCRUtils.logout(session);
		}
		
		log.debug("lock: void");
	}
	
	@Override
	public void unlock(String token, String wikiPath) throws AccessDeniedException, RepositoryException, 
			PathNotFoundException, LockException, DatabaseException {
		log.debug("unlock({}, {})", token, wikiPath);
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			JCRUtils.loadLockTokens(session);
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			wikiNode.unlock();
			JCRUtils.removeLockToken(session, wikiNode);

			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(wikiNode, session.getUserID(), "UNLOCK_WIKI", null);
			
			// Check scripting
			BaseScriptingModule.checkScripts(session, wikiNode, wikiNode, "UNLOCK_WIKI");

			// Activity log
			UserActivity.log(session.getUserID(), "UNLOCK_WIKI", wikiNode.getUUID(), wikiPath);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			if (token == null) JCRUtils.logout(session);
		}
		
		log.debug("unlock: void");
	}
	
	@Override
	public boolean isLocked(String token, String wikiPath) throws RepositoryException, PathNotFoundException, 
			DatabaseException {
		log.debug("isLocked({}, {})", token, wikiPath);
		boolean locked;
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			locked = wikiNode.isLocked();
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}
		
		log.debug("isLocked: {}", locked);
		return locked;
	}

	@Override
	public Lock getLock(String token, String wikiPath) throws RepositoryException, PathNotFoundException,
			LockException, DatabaseException {
		log.debug("getLock({}, {})", token, wikiPath);
		Lock lock = new Lock();
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			lock = BaseWikiModule.getLock(session, wikiPath);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}
		
		log.debug("getLock: {}", lock);
		return lock;
	}
	
	@Override
	public void purge(String token, String wikiPath) throws AccessDeniedException, RepositoryException, 
			PathNotFoundException, DatabaseException {
		log.debug("purge({}, {})", token, wikiPath);
		Node parentNode = null;
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			@SuppressWarnings("unused")
			HashMap<String, UserItems> userItemsHash = null;
			
			synchronized (wikiNode) {
				parentNode = wikiNode.getParent();
				userItemsHash = BaseWikiModule.purge(session, parentNode, wikiNode);
			}
			
			// Update user items
			//if (Config.USER_ITEM_CACHE) {
				//for (Iterator<Entry<String, UserItems>> it = userItemsHash.entrySet().iterator(); it.hasNext(); ) {
					//Entry<String, UserItems> entry = it.next();
					//String uid = entry.getKey();
					//UserItems userItems = entry.getValue();
					//UserItemsManager.decSize(uid, userItems.getSize());
					//UserItemsManager.decDocuments(uid, userItems.getDocuments());
					//UserItemsManager.decFolders(uid, userItems.getFolders());
				//}
			//}
			
			// Check scripting
			BaseScriptingModule.checkScripts(session, parentNode, wikiNode, "PURGE_WIKI");
			
			// Activity log
			UserActivity.log(session.getUserID(), "PURGE_WIKI", wikiNode.getUUID(), wikiPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}
		
		log.debug("purge: void");
	}

	@Override
	public void move(String token, String wikiPath, String dstPath) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("move({}, {}, {})", new Object[] { token, wikiPath, dstPath });
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			String name = FileUtils.getName(wikiPath);
			session.move(wikiPath, dstPath+"/"+name);
			session.save();

			// Activity log
			UserActivity.log(session.getUserID(), "MOVE_WIKI", wikiNode.getUUID(), dstPath+", "+wikiPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.ItemExistsException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new ItemExistsException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(session);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("move: void");
	}

	@Override
	public void copy(String token, String wikiPath, String dstPath) throws ItemExistsException,
			PathNotFoundException, AccessDeniedException, RepositoryException, IOException, DatabaseException, 
			UserQuotaExceededException {
		log.debug("copy({}, {}, {})", new Object[]  { token, wikiPath, dstPath });
		Node dstFolderNode = null;
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node srcWikiNode = session.getRootNode().getNode(wikiPath.substring(1));
			dstFolderNode = session.getRootNode().getNode(dstPath.substring(1));
			BaseWikiModule.copy(session, srcWikiNode, dstFolderNode);

			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(dstFolderNode, session.getUserID(), "COPY_WIKI", null);

			// Activity log
			UserActivity.log(session.getUserID(), "COPY_WIKI", srcWikiNode.getUUID(), dstPath+", "+wikiPath);
		} catch (javax.jcr.ItemExistsException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(dstFolderNode);
			throw new ItemExistsException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(dstFolderNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(dstFolderNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(dstFolderNode);
			throw new RepositoryException(e.getMessage(), e);
		} catch (java.io.IOException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(dstFolderNode);
			throw e;
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("copy: void");
	}
	
	@Override
	public void restoreVersion(String token, String wikiPath, String versionId) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("restoreVersion({}, {}, {})", new Object[] { token, wikiPath, versionId });
		Node contentNode = null;
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node wikiNode = session.getRootNode().getNode(wikiPath.substring(1));

			synchronized (wikiNode) {
				contentNode = wikiNode.getNode(Wiki.CONTENT);
				contentNode.restore(versionId, true);
				contentNode.save();
			}

			// Activity log
			UserActivity.log(session.getUserID(), "RESTORE_WIKI_VERSION", wikiNode.getUUID(), versionId+", "+wikiPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(contentNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(contentNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(contentNode);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}
		
		log.debug("restoreVersion: void");
	}
	
	@Override
	public boolean isValid(String token, String wikiPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("isValid({}, {})", token, wikiPath);
		boolean valid = false;
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node node = session.getRootNode().getNode(wikiPath.substring(1));

			if (node.isNodeType(Wiki.TYPE)) {
				valid = true;
			}
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("isValid: {}", valid);
		return valid;
	}

	@Override
	public String getPath(String token, String uuid) throws AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("getPath({}, {})", token, uuid);
		String path = null;
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node node = session.getNodeByUUID(uuid);

			if (node.isNodeType(Wiki.TYPE)) {
				path = node.getPath();
			}
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("getPath: {}", path);
		return path;
	}
}
