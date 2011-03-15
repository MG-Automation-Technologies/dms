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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.jackrabbit.api.XASession;
import org.apache.jackrabbit.core.NodeImpl;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.security.AccessManager;
import org.apache.jackrabbit.spi.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.ContentInfo;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Mail;
import com.openkm.bean.Repository;
import com.openkm.cache.UserItemsManager;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.JcrSessionManager;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UserQuotaExceededException;
import com.openkm.dao.bean.cache.UserItems;
import com.openkm.module.FolderModule;
import com.openkm.module.base.BaseFolderModule;
import com.openkm.module.base.BaseScriptingModule;
import com.openkm.util.FileUtils;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;

public class DirectFolderModule implements FolderModule {
	private static Logger log = LoggerFactory.getLogger(DirectFolderModule.class);
	
	@Override
	public Folder create(String token, Folder fld) throws AccessDeniedException, RepositoryException, 
			PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("create({}, {})", token, fld);
		Folder newFolder = null;
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
			
			String parent = FileUtils.getParent(fld.getPath());
			String name = FileUtils.getName(fld.getPath());
			parentNode = session.getRootNode().getNode(parent.substring(1));

			// Escape dangerous chars in name
			name = FileUtils.escape(name);
			fld.setPath(parent+"/"+name);
			
			// Create node
			Node folderNode = BaseFolderModule.create(session, parentNode, name);
						
			// Set returned folder properties
			newFolder = BaseFolderModule.getProperties(session, folderNode);
			
			// Check scripting
			BaseScriptingModule.checkScripts(session, parentNode, folderNode, "CREATE_FOLDER");

			// Activity log
			UserActivity.log(session.getUserID(), "CREATE_FOLDER", folderNode.getUUID(), fld.getPath());
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.ItemExistsException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new ItemExistsException(e.getMessage(), e);
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

		log.debug("create: {}", newFolder);
		return newFolder;
	}
	
	@Override
	public Folder getProperties(String token, String fldPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getProperties({}, {})", token, fldPath);
		Folder fld = null;
		Session session = null;

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));
			fld = BaseFolderModule.getProperties(session, folderNode);
			
			// Activity log
			UserActivity.log(session.getUserID(), "GET_FOLDER_PROPERTIES", folderNode.getUUID(), fldPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("get: {}", fld);
		return fld;
	}
	
	@Override
	public void delete(String token, String fldPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, LockException, DatabaseException {
		log.debug("delete({}, {})", token, fldPath);
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			String name = FileUtils.getName(fldPath);
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));
			Node parentNode = folderNode.getParent();
			Node userTrash = session.getRootNode().getNode(Repository.TRASH+"/"+session.getUserID());
			
			if (hasLockedNodes(folderNode)) {
				throw new LockException("Can't delete a folder with child locked nodes");
			}
			
			if (!hasWriteAccess(folderNode)) {
				throw new AccessDeniedException("Can't delete a folder with readonly nodes");
			}
			
			if (Repository.ROOT.equals(name) || Repository.CATEGORIES.equals(name) || 
					Repository.THESAURUS.equals(name) || Repository.TEMPLATES.equals(name) ||
					Repository.PERSONAL.equals(name) || Repository.MAIL.equals(name) ||
					Repository.TRASH.equals(name)) {
				throw new AccessDeniedException("Can't delete a required node");
			}
			
			// Test if already exists a folder whith the same name in the trash
			String destPath = userTrash.getPath()+"/";
			String testName = name;
			
			for (int i=1; session.itemExists(destPath+testName); i++) {
				testName = name+" ("+i+")";
			}
			
			session.move(folderNode.getPath(), destPath+testName);
			session.getRootNode().save();
			
			// Check scripting
			BaseScriptingModule.checkScripts(session, parentNode, folderNode, "DELETE_FOLDER");
			
			// Activity log
			UserActivity.log(session.getUserID(), "DELETE_FOLDER", folderNode.getUUID(), fldPath);
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

	/**
	 * Check recursively if the folder contains locked nodes
	 */
	private boolean hasLockedNodes(Node node) throws javax.jcr.RepositoryException {
		boolean hasLock = false;
		
		for (NodeIterator ni = node.getNodes(); ni.hasNext(); ) {
			Node child = ni.nextNode();
			
			if (child.isNodeType(Document.TYPE)) {
				hasLock |= child.isLocked();
			} else if (child.isNodeType(Folder.TYPE)) {
				hasLock |= hasLockedNodes(child);
			} else if (child.isNodeType(Mail.TYPE)) {
				// Mail nodes can't be locked
			} else {
				throw new javax.jcr.RepositoryException("Unknown node type");
			}
		}
		
		return hasLock;
	}
	
	/**
	 * Check if a node has removable childs
	 * TODO: Is this neccessary? The access manager should prevent this an
	 * make the core thown an exception. 
	 */
	private boolean hasWriteAccess(Node node) throws javax.jcr.RepositoryException {
		log.debug("hasWriteAccess({})", node.getPath());
		final int REMOVE_NODE = org.apache.jackrabbit.core.security.authorization.Permission.REMOVE_NODE;
		boolean canWrite = true;
		AccessManager am = ((SessionImpl) node.getSession()).getAccessManager();
		
		for (NodeIterator ni = node.getNodes(); ni.hasNext(); ) {
			Node child = ni.nextNode();
			Path path = ((NodeImpl)node).getPrimaryPath();
			
			if (child.isNodeType(Document.TYPE)) {
				canWrite &= am.isGranted(path, REMOVE_NODE);
			} else if (child.isNodeType(Folder.TYPE)) {
				canWrite &= am.isGranted(path, REMOVE_NODE);
				canWrite &= hasWriteAccess(child);
			} else if (child.isNodeType(Mail.TYPE)) {
				canWrite &= am.isGranted(path, REMOVE_NODE);
			} else {
				throw new javax.jcr.RepositoryException("Unknown node type");
			}
		}
		
		log.debug("hasWriteAccess: {}", canWrite);
		return canWrite;
	}
	
	@Override
	public void purge(String token, String fldPath) throws AccessDeniedException, RepositoryException, 
			PathNotFoundException, DatabaseException {
		log.debug("purge({}, {})", token, fldPath);
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
			
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));
			HashMap<String, UserItems> userItemsHash = null;
			
			synchronized (folderNode) {
				parentNode = folderNode.getParent();
				userItemsHash = purgeHelper(session, folderNode);
				parentNode.save();
			}
			
			if (Config.USER_ITEM_CACHE) {
				// Update user items
				for (Iterator<Entry<String, UserItems>> it = userItemsHash.entrySet().iterator(); it.hasNext(); ) {
					Entry<String, UserItems> entry = it.next();
					String uid = entry.getKey();
					UserItems userItems = entry.getValue();
					UserItemsManager.decSize(uid, userItems.getSize());
					UserItemsManager.decDocuments(uid, userItems.getDocuments());
					UserItemsManager.decFolders(uid, userItems.getFolders());
				}
			}
			
			// Check scripting
			BaseScriptingModule.checkScripts(session, parentNode, folderNode, "PURGE_FOLDER");

			// Activity log
			UserActivity.log(session.getUserID(), "PURGE_FOLDER", folderNode.getUUID(), fldPath);
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
	
	/**
	 * Purge folders recursively
	 */
	public HashMap<String, UserItems> purgeHelper(Session session, Node fldNode) throws VersionException, 
			javax.jcr.lock.LockException, ConstraintViolationException, javax.jcr.RepositoryException {
		HashMap<String, UserItems> userItemsHash = new HashMap<String, UserItems>();
		
		for (NodeIterator nit = fldNode.getNodes(); nit.hasNext(); ) {
			HashMap<String, UserItems> userItemsHashRet = new HashMap<String, UserItems>();
			Node node = nit.nextNode();
			
			if (node.isNodeType(Document.TYPE)) {
				userItemsHashRet = new DirectDocumentModule().purgeHelper(session, node.getParent(), node);
			} else if (node.isNodeType(Folder.TYPE)) {
				userItemsHashRet = purgeHelper(session, node);
				//String author = node.getProperty(Folder.AUTHOR).getString();
				//userItemsHashRet.get(key)
			}
			
			if (Config.USER_ITEM_CACHE) {
				// Join hash maps
				for (Iterator<Entry<String, UserItems>> entIt = userItemsHashRet.entrySet().iterator(); entIt.hasNext(); ) {
					Entry<String, UserItems> entry = entIt.next();
					String uid = entry.getKey();
					UserItems userItem = entry.getValue();
					UserItems userItemTmp = userItemsHash.get(uid);
					if (userItemTmp == null) userItemTmp = new UserItems();
					userItemTmp.setSize(userItemTmp.getSize() + userItem.getSize());
					userItemTmp.setDocuments(userItemTmp.getDocuments() + userItem.getDocuments());
					userItemsHash.put(uid, userItemTmp);
				}
			}
		}
		
		fldNode.remove();
		return userItemsHash;
	}
	
	@Override
	public Folder rename(String token, String fldPath, String newName) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("rename({}, {}, {})", new Object[] { token, fldPath, newName });
		Folder renamedFolder = null;
		Session session = null;
		Node folderNode = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			String parent = FileUtils.getParent(fldPath);
			String name = FileUtils.getName(fldPath);
							
			// Escape dangerous chars in name
			newName = FileUtils.escape(newName);
			
			if (newName != null && !newName.equals("") && !newName.equals(name)) {
				String newPath = parent+"/"+newName;
				session.move(fldPath, newPath);
				
				// Set new name
				folderNode = session.getRootNode().getNode(newPath.substring(1));
				folderNode.setProperty(Folder.NAME, newName);
			
				// Publish changes
				session.save();	
			
				// Set returned document properties
				renamedFolder = BaseFolderModule.getProperties(session, folderNode);
			} else {
				// Don't change anything
				folderNode = session.getRootNode().getNode(fldPath.substring(1));
				renamedFolder = BaseFolderModule.getProperties(session, folderNode);
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "RENAME_FOLDER", folderNode.getUUID(), newName+", "+fldPath);
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
		
		log.debug("rename: {}", renamedFolder);
		return renamedFolder;
	}
	
	@Override
	public void move(String token, String fldPath, String dstPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("move({}, {}, {})", new Object[] { token, fldPath, dstPath });
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
			
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));
			String name = FileUtils.getName(fldPath);
			session.move(fldPath, dstPath+"/"+name);
			session.save();
			
			// Activity log
			UserActivity.log(session.getUserID(), "MOVE_FOLDER", folderNode.getUUID(), dstPath+", "+fldPath);
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
	public void copy(String token, String fldPath, String dstPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, ItemExistsException, IOException, DatabaseException, 
			UserQuotaExceededException {
		log.debug("copy({}, {}, {})", new Object[] { token, fldPath, dstPath });
		//Transaction t = null;
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
			
			String name = FileUtils.getName(fldPath);
			//t = new Transaction(session);
			//t.start();
			
			// Make some work
			Node srcFolderNode = session.getRootNode().getNode(fldPath.substring(1)); 
			Node dstFolderNode = session.getRootNode().getNode(dstPath.substring(1));
			Node newFolder = BaseFolderModule.create(session, dstFolderNode, name);
			dstFolderNode.save();
			copyHelper(session, srcFolderNode, newFolder);
			
			//t.end();
			//t.commit();
			
			// Activity log
			UserActivity.log(session.getUserID(), "COPY_FOLDER", srcFolderNode.getUUID(), dstPath+", "+fldPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			//t.rollback();
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.ItemExistsException e) {
			log.warn(e.getMessage(), e);
			//t.rollback();
			throw new ItemExistsException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			//t.rollback();
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			//t.rollback();
			throw new RepositoryException(e.getMessage(), e);
		} catch (java.io.IOException e) {
			log.error(e.getMessage(), e);
			//t.rollback();
			throw e;
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("copy: void");
	}

	/**
	 * Performs recursive node copy
	 */
	private void copyHelper(Session session, Node srcFolderNode, Node dstFolderNode) throws NoSuchNodeTypeException, 
			VersionException, ConstraintViolationException, javax.jcr.lock.LockException, 
			javax.jcr.RepositoryException, IOException, DatabaseException, UserQuotaExceededException {
		log.debug("copyHelper({}, {}, {})", new Object[] { session, srcFolderNode.getPath(), dstFolderNode.getPath() });
		
		for (NodeIterator it = srcFolderNode.getNodes(); it.hasNext(); ) {
			Node child = it.nextNode();
			
			if (child.isNodeType(Document.TYPE)) {
				new DirectDocumentModule().copy(session, child, dstFolderNode);
				dstFolderNode.save();
			} else if (child.isNodeType(Mail.TYPE)) {
				new DirectMailModule().copy(session, child, dstFolderNode);
				dstFolderNode.save();
			} else if (child.isNodeType(Folder.TYPE)) {
				Node newFolder = BaseFolderModule.create(session, dstFolderNode, child.getName());
				dstFolderNode.save();
				copyHelper(session, child, newFolder);
			}
		}
		
		log.debug("copyHelper: void");
	}
	
	@Override
	public List<Folder> getChilds(String token, String fldPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("findChilds({}, {})", token, fldPath);
		List<Folder> childs = new ArrayList<Folder>();
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
				
				if (child.isNodeType(Folder.TYPE)) {
					childs.add(BaseFolderModule.getProperties(session, child));
				}
			}

			// Activity log
			UserActivity.log(session.getUserID(), "GET_CHILD_FOLDERS", folderNode.getUUID(), fldPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}
				
		log.debug("findChilds: {}", childs);
		return childs;
	}
	
	@Override
	public ContentInfo getContentInfo(String token, String fldPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("getContentInfo({}, {})", token, fldPath);
		ContentInfo contentInfo = new ContentInfo();
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));
			contentInfo = getContentInfoHelper(folderNode);
			
			// Activity log
			UserActivity.log(session.getUserID(), "GET_FOLDER_CONTENT_INFO", folderNode.getUUID(), contentInfo.toString()+", "+fldPath);
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
		
		log.debug("getContentInfo: {}", contentInfo);
		return contentInfo;
	}

	/**
	 * Get content info recursively
	 */
	private ContentInfo getContentInfoHelper(Node folderNode) throws AccessDeniedException, 
			RepositoryException, PathNotFoundException {
		log.debug("getContentInfoHelper({})", folderNode);
		ContentInfo contentInfo = new ContentInfo();
		
		try {
			for (NodeIterator ni = folderNode.getNodes(); ni.hasNext(); ) {
				Node child = ni.nextNode();
				
				if (child.isNodeType(Folder.TYPE)) {
					ContentInfo ci = getContentInfoHelper(child);
					contentInfo.setFolders(contentInfo.getFolders() + ci.getFolders() + 1);
					contentInfo.setDocuments(contentInfo.getDocuments() + ci.getDocuments());
					contentInfo.setSize(contentInfo.getSize() + ci.getSize());
				} else if (child.isNodeType(Document.TYPE)) {
					Node documentContentNode = child.getNode(Document.CONTENT);
					long size = documentContentNode.getProperty(Document.SIZE).getLong();
					contentInfo.setDocuments(contentInfo.getDocuments() + 1);
					contentInfo.setSize(contentInfo.getSize() + size);
				} else if (child.isNodeType(Mail.TYPE)) {
					long size = child.getProperty(Mail.SIZE).getLong();
					contentInfo.setMails(contentInfo.getMails() + 1);
					contentInfo.setSize(contentInfo.getSize() + size);
				}
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
		}
		
		log.debug("getContentInfoHelper: {}", contentInfo);
		return contentInfo;
	}
	
	@Override
	public boolean isValid(String token, String fldPath) throws PathNotFoundException, AccessDeniedException, 
			RepositoryException, DatabaseException {
		log.debug("isValid({}, {})", token, fldPath);
		boolean valid = false;
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node node = session.getRootNode().getNode(fldPath.substring(1));
			
			if (node.isNodeType(Folder.TYPE)) {
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

			if (node.isNodeType(Folder.TYPE)) {
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
