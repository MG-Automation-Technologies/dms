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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.api.XASession;
import org.apache.jackrabbit.core.NodeImpl;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.security.AccessManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.ContentInfo;
import es.git.openkm.bean.Document;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.Mail;
import es.git.openkm.bean.Notification;
import es.git.openkm.bean.Permission;
import es.git.openkm.bean.Repository;
import es.git.openkm.cache.UserItemsManager;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.Config;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.SessionManager;
import es.git.openkm.module.FolderModule;
import es.git.openkm.util.FileUtils;
import es.git.openkm.util.JCRUtils;
import es.git.openkm.util.Transaction;
import es.git.openkm.util.UserActivity;

public class DirectFolderModule implements FolderModule {
	private static Logger log = LoggerFactory.getLogger(DirectFolderModule.class);

	/**
	 * @param session
	 * @param fldPath
	 * @return
	 * @throws javax.jcr.RepositoryException 
	 * @throws javax.jcr.PathNotFoundException 
	 */
	public Folder getProperties(Session session, String fldPath) throws 
			javax.jcr.PathNotFoundException, javax.jcr.RepositoryException  {
		log.debug("getProperties[session]:(" + session + ", " + fldPath + ")");
		Folder fld = new Folder();
		Node folderNode = session.getRootNode().getNode(fldPath.substring(1));
			
		// Properties
		fld.setPath(folderNode.getPath());
		fld.setHasChilds(false);
		fld.setCreated(folderNode.getProperty(JcrConstants.JCR_CREATED).getDate());
		fld.setAuthor(folderNode.getProperty(Folder.AUTHOR).getString());
		fld.setUuid(folderNode.getUUID());
					
		for (NodeIterator nit = folderNode.getNodes(); nit.hasNext(); ) {
			Node node = nit.nextNode();

			if (node.isNodeType(Folder.TYPE)) {
				fld.setHasChilds(true);
			}
		}
		
		// Get permissions
		if (Config.SYSTEM_READONLY.equals("on")) {
			fld.setPermissions(Permission.NONE);
		} else {
			AccessManager am = ((SessionImpl) session).getAccessManager();
			
			if (am.isGranted(((NodeImpl)folderNode).getId(), Permission.READ)) {
				fld.setPermissions(Permission.READ);
			}
			
			if (am.isGranted(((NodeImpl)folderNode).getId(), Permission.WRITE)) {
				fld.setPermissions((byte) (fld.getPermissions() | Permission.WRITE));
			}
		}
		
		// Get user subscription
		ArrayList<String> subscriptorList = new ArrayList<String>();
		
		if (folderNode.isNodeType(Notification.TYPE)) {
			Value[] subscriptors = folderNode.getProperty(Notification.SUBSCRIPTORS).getValues();

			for (int i=0; i<subscriptors.length; i++) {
				subscriptorList.add(subscriptors[i].getString());
				
				if (session.getUserID().equals(subscriptors[i].getString())) {
					fld.setSubscribed(true);
				}
			}
		}
		
		fld.setSubscriptors(subscriptorList);
		
		log.debug("Permisos: "+fldPath+": "+fld.getPermissions());
		log.debug("getProperties[session]: " + fld);
		return fld;
	}

	/**
	 * Create a new folder
	 */
	private Node create(Session session, Node parentNode, String name) throws javax.jcr.ItemExistsException,
			javax.jcr.PathNotFoundException, NoSuchNodeTypeException, 
			javax.jcr.lock.LockException, VersionException, 
			ConstraintViolationException, javax.jcr.RepositoryException {
		// Create and add a new folder node
		Node folderNode = parentNode.addNode(name, Folder.TYPE);
		folderNode.setProperty(Folder.AUTHOR, session.getUserID());
		folderNode.setProperty(Folder.NAME, name);
		
		// Get parent node auth info
		Value[] usersReadParent = parentNode.getProperty(Permission.USERS_READ).getValues();
		String[] usersRead = JCRUtils.usrValue2String(usersReadParent, session.getUserID()); 
		Value[] usersWriteParent = parentNode.getProperty(Permission.USERS_WRITE).getValues();
		String[] usersWrite = JCRUtils.usrValue2String(usersWriteParent, session.getUserID()); 
		
		Value[] rolesReadParent = parentNode.getProperty(Permission.ROLES_READ).getValues();
		String[] rolesRead = JCRUtils.rolValue2String(rolesReadParent); 
		Value[] rolesWriteParent = parentNode.getProperty(Permission.ROLES_WRITE).getValues();
		String[] rolesWrite = JCRUtils.rolValue2String(rolesWriteParent); 
		
		// Set auth info
		folderNode.setProperty(Permission.USERS_READ, usersRead);
		folderNode.setProperty(Permission.USERS_WRITE, usersWrite);
		folderNode.setProperty(Permission.ROLES_READ, rolesRead);
		folderNode.setProperty(Permission.ROLES_WRITE, rolesWrite);
		
		parentNode.save();
		
		// Update user items
		UserItemsManager.incFolders(session.getUserID());
		
		return folderNode;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#create(java.lang.String, es.git.openkm.bean.Folder)
	 */
	public Folder create(String token, Folder fld) throws AccessDeniedException, 
			RepositoryException, PathNotFoundException, ItemExistsException {
		log.debug("create(" + token + ", " + fld + ")");
		Folder newFolder = null;
		Node parentNode = null;
		
		try {
			String parent = FileUtils.getParent(fld.getPath());
			String name = FileUtils.getName(fld.getPath());
			Session session = SessionManager.getInstance().get(token);
			parentNode = session.getRootNode().getNode(parent.substring(1));

			// Escape dangerous chars in name
			name = FileUtils.escape(name);
			fld.setPath(parent+"/"+name);
			
			// Create node
			Node folderNode = create(session, parentNode, name);
						
			// Set returned folder properties
			newFolder = getProperties(session, fld.getPath());
			
			// Check scripting
			DirectScriptingModule.checkScripts(parentNode, folderNode.getPath(), session.getUserID(), "CREATE_FOLDER");

			// Activity log
			UserActivity.log(session, "CREATE_FOLDER", fld.getPath(), null);
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
		}

		log.debug("create: " + newFolder);
		return newFolder;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#get(java.lang.String, java.lang.String)
	 */
	public Folder getProperties(String token, String fldPath) throws PathNotFoundException, RepositoryException {
		log.debug("get:(" + token + ", " + fldPath + ")");
		Folder fld = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			fld = getProperties(session, fldPath);
			
			// Activity log
			UserActivity.log(session, "GET_FOLDER_PROPERTIES", fldPath, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("get: " + fld);
		return fld;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#delete(java.lang.String, java.lang.String)
	 */
	public void delete(String token, String fldPath) throws AccessDeniedException, RepositoryException, PathNotFoundException, LockException {
		log.debug("delete(" + token + ", " + fldPath + ")");
		Session session = null;
		
		try {
			String name = FileUtils.getName(fldPath);
			session = SessionManager.getInstance().get(token);
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));
			Node parentNode = folderNode.getParent();
			Node userTrash = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+Repository.TRASH);
						
			if (hasLockedNodes(folderNode)) {
				throw new LockException("Can't delete a folder with child locked nodes");
			}
			
			if (!hasWriteAccess(folderNode)) {
				throw new AccessDeniedException("Can't delete a folder with readonly nodes");
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
			DirectScriptingModule.checkScripts(parentNode, fldPath, session.getUserID(), "DELETE_FOLDER");
			
			// Activity log
			UserActivity.log(session, "DELETE_FOLDER", fldPath, null);
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
		}
				
		log.debug("delete: void");
	}

	/**
	 * @param node
	 * @return
	 * @throws javax.jcr.RepositoryException
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
	 * @param node
	 * @return
	 * @throws javax.jcr.RepositoryException
	 */
	private boolean hasWriteAccess(Node node) throws javax.jcr.RepositoryException {
		log.debug("hasWriteAccess("+node.getPath()+")");
		boolean canWrite = true;
		AccessManager am = ((SessionImpl) node.getSession()).getAccessManager();
		
		for (NodeIterator ni = node.getNodes(); ni.hasNext(); ) {
			Node child = ni.nextNode();
			
			if (child.isNodeType(Document.TYPE)) {
				canWrite &= am.isGranted(((NodeImpl)node).getId(), Permission.WRITE);
			} else if (child.isNodeType(Folder.TYPE)) {
				canWrite &= am.isGranted(((NodeImpl)node).getId(), Permission.WRITE);
				canWrite &= hasWriteAccess(child);
			} else if (child.isNodeType(Mail.TYPE)) {
				canWrite &= am.isGranted(((NodeImpl)node).getId(), Permission.WRITE);
			} else {
				throw new javax.jcr.RepositoryException("Unknown node type");
			}
		}
		
		log.debug("hasWriteAccess: "+canWrite);
		return canWrite;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#purge(java.lang.String, java.lang.String)
	 */
	public void purge(String token, String fldPath) throws AccessDeniedException, RepositoryException, PathNotFoundException {
		log.debug("purge(" + token + ", " + fldPath + ")");
		Node parentNode = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));
			parentNode = folderNode.getParent();
			folderNode.remove();
			parentNode.save();
			
			// Update user items
			UserItemsManager.decFolders(session.getUserID());
			
			// Check scripting
			DirectScriptingModule.checkScripts(parentNode, fldPath, session.getUserID(), "PURGE_FOLDER");

			// Activity log
			UserActivity.log(session, "PURGE_FOLDER", fldPath, null);
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
		}
		log.debug("purge: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Folder rename(String token, String fldPath, String newName) throws AccessDeniedException, RepositoryException, PathNotFoundException, ItemExistsException {
		log.debug("rename:(" + token + ", " + fldPath + ", " + newName + ")");
		Folder renamedFolder = null;
		Session session = null;
		
		try {
			String parent = FileUtils.getParent(fldPath);
			String name = FileUtils.getName(fldPath);
			session = SessionManager.getInstance().get(token);
				
			// Escape dangerous chars in name
			newName = FileUtils.escape(newName);
			
			if (newName != null && !newName.equals("") && !newName.equals(name)) {
				String newPath = parent+"/"+newName;
				session.move(fldPath, newPath);
				
				// Set new name
				Node folderNode = session.getRootNode().getNode(newPath.substring(1));
				folderNode.setProperty(Folder.NAME, newName);
			
				// Publish changes
				session.save();	
			
				// Set returned document properties
				renamedFolder = getProperties(session, newPath);
			} else {
				// Don't change anything
				renamedFolder = getProperties(session, fldPath);
			}
			
			// Activity log
			UserActivity.log(session, "RENAME_FOLDER", fldPath, newName);
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
		}
		
		log.debug("rename: "+renamedFolder);
		return renamedFolder;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#move(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void move(String token, String fldPath, String dstPath) throws AccessDeniedException, RepositoryException, PathNotFoundException, ItemExistsException {
		log.debug("move(" + token + ", " + fldPath + ", " + dstPath + ")");
		Session session = null;

		try {
			String name = FileUtils.getName(fldPath);
			session = SessionManager.getInstance().get(token);
			session.move(fldPath, dstPath+"/"+name);
			session.save();
			
			// Activity log
			UserActivity.log(session, "MOVE_FOLDER", fldPath, dstPath);
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
		}
		
		log.debug("move: void");	
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#copy(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void copy(String token, String fldPath, String dstPath) throws AccessDeniedException, 
			RepositoryException, PathNotFoundException, ItemExistsException, IOException {
		log.debug("copy(" + token + ", " + fldPath + ", " + dstPath + ")");
		Transaction t = null;
		
		try {
			String name = FileUtils.getName(fldPath);
			XASession session = (XASession)SessionManager.getInstance().get(token);
			
			t = new Transaction(session);
			t.start();
			
			// Make some work
			Node srcFolderNode = session.getRootNode().getNode(fldPath.substring(1)); 
			Node dstFolderNode = session.getRootNode().getNode(dstPath.substring(1));
			Node newFolder = create(session, dstFolderNode, name);
			copyHelper(session, srcFolderNode, newFolder);
			
			t.end();
			t.commit();
			
			// Activity log
			UserActivity.log(session, "COPY_FOLDER", fldPath, dstPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			t.rollback();
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.ItemExistsException e) {
			log.warn(e.getMessage(), e);
			t.rollback();
			throw new ItemExistsException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			t.rollback();
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw new RepositoryException(e.getMessage(), e);
		} catch (java.io.IOException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw e;
		}

		log.debug("copy: void");
	}

	/**
	 * Performs recursive node copy
	 * 
	 * @param node
	 * @throws NoSuchNodeTypeException
	 * @throws VersionException
	 * @throws ConstraintViolationException
	 * @throws javax.jcr.lock.LockException
	 * @throws javax.jcr.RepositoryException
	 * @throws IOException 
	 */
	private void copyHelper(Session session, Node srcFolderNode, Node dstFolderNode) throws NoSuchNodeTypeException, 
			VersionException, ConstraintViolationException, javax.jcr.lock.LockException, 
			javax.jcr.RepositoryException, IOException {
		log.debug("copyHelper("+srcFolderNode.getPath()+", "+dstFolderNode.getPath()+")");
		
		for (NodeIterator it = srcFolderNode.getNodes(); it.hasNext(); ) {
			Node child = it.nextNode();
			
			if (child.isNodeType(Document.TYPE)) {
				new DirectDocumentModule().copy(session, child, dstFolderNode);
			} else if (child.isNodeType(Mail.TYPE)) {
				new DirectMailModule().copy(session, child, dstFolderNode);
			} else if (child.isNodeType(Folder.TYPE)) {
				Node newFolder = create(session, dstFolderNode, child.getName());
				copyHelper(session, child, newFolder);
			}
		}
		
		log.debug("copyHelper: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#getChilds(java.lang.String, java.lang.String)
	 */
	public Collection<Folder> getChilds(String token, String fldPath) throws PathNotFoundException, RepositoryException {
		log.debug("findChilds(" + token + ", " + fldPath + ")");
		ArrayList<Folder> childs = new ArrayList<Folder>();

		try {
			Session session = SessionManager.getInstance().get(token);
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));

			for (NodeIterator ni = folderNode.getNodes(); ni.hasNext(); ) {
				Node child = ni.nextNode();
				
				if (child.isNodeType(Folder.TYPE)) {
					childs.add(getProperties(session, child.getPath()));
				}
			}

			// Activity log
			UserActivity.log(session, "GET_CHILD_FOLDERS", fldPath, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
				
		log.debug("findChilds: "+childs);
		return childs;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#getContentInfo(java.lang.String, java.lang.String)
	 */
	public ContentInfo getContentInfo(String token, String fldPath) throws AccessDeniedException, RepositoryException, PathNotFoundException {
		log.debug("getContentInfo(" + token + ", " + fldPath + ")");
		ContentInfo contentInfo = new ContentInfo();

		try {
			Session session = SessionManager.getInstance().get(token);
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));
			contentInfo = getContentInfoHelper(folderNode);
			
			// Activity log
			UserActivity.log(session, "GET_FOLDER_CONTENT_INFO", fldPath, contentInfo.toString());
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
		
		log.debug("getContentInfo: "+contentInfo);
		return contentInfo;
	}

	/**
	 * 
	 * @param folderNode
	 * @return
	 * @throws AccessDeniedException
	 * @throws RepositoryException
	 * @throws PathNotFoundException
	 */
	public ContentInfo getContentInfoHelper(Node folderNode) throws AccessDeniedException, RepositoryException, PathNotFoundException {
		log.debug("getContentInfoHelper(" + folderNode + ")");
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
		
		log.debug("getContentInfoHelper: "+contentInfo);
		return contentInfo;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.FolderModule#isValid(java.lang.String, java.lang.String)
	 */
	public boolean isValid(String token, String fldPath) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("isValid(" + token + ", " + fldPath + ")");
		boolean valid = false;
		
		try {
			Session session = SessionManager.getInstance().get(token);
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
		}

		log.debug("isValid: "+valid);
		return valid;
	}
}
