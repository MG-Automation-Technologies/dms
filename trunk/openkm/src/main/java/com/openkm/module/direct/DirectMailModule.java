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
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
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

import com.openkm.bean.Document;
import com.openkm.bean.Mail;
import com.openkm.bean.Permission;
import com.openkm.bean.Repository;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.VirusDetectedException;
import com.openkm.module.MailModule;
import com.openkm.util.FileUtils;
import com.openkm.util.JCRUtils;
import com.openkm.util.Transaction;
import com.openkm.util.UserActivity;

public class DirectMailModule implements MailModule {
	private static Logger log = LoggerFactory.getLogger(DirectMailModule.class);

	/**
	 * Get mail properties
	 */
	public Mail getProperties(Session session, String mailPath) throws 
			javax.jcr.PathNotFoundException, javax.jcr.RepositoryException  {
		log.debug("getProperties[session]({}, {})", session, mailPath);
		Mail mail = new Mail();
		Node mailNode = session.getRootNode().getNode(mailPath.substring(1));
			
		// Properties
		Value[] replyValues = mailNode.getProperty(Mail.REPLY).getValues();
		String[] reply = JCRUtils.value2String(replyValues);
		Value[] toValues = mailNode.getProperty(Mail.TO).getValues();
		String[] to = JCRUtils.value2String(toValues);
		Value[] ccValues = mailNode.getProperty(Mail.CC).getValues();
		String[] cc = JCRUtils.value2String(ccValues);
		Value[] bccValues = mailNode.getProperty(Mail.BCC).getValues();
		String[] bcc = JCRUtils.value2String(bccValues);
		
		mail.setPath(mailNode.getPath());
		mail.setUuid(mailNode.getUUID());
		mail.setReply(reply);
		mail.setTo(to);
		mail.setCc(cc);
		mail.setBcc(bcc);
		mail.setFrom(mailNode.getProperty(Mail.FROM).getString());
		mail.setSize(mailNode.getProperty(Mail.SIZE).getLong());
		mail.setSentDate(mailNode.getProperty(Mail.SENT_DATE).getDate());
		mail.setReceivedDate(mailNode.getProperty(Mail.RECEIVED_DATE).getDate());
		mail.setSubject(mailNode.getProperty(Mail.SUBJECT).getString());
		mail.setContent(mailNode.getProperty(Mail.CONTENT).getString());
		mail.setMimeType(mailNode.getProperty(Mail.MIME_TYPE).getString());
		
		// Get attachments
		ArrayList<Document> attachments = new ArrayList<Document>();
		
		for (NodeIterator nit = mailNode.getNodes(); nit.hasNext(); ) {
			Node node = nit.nextNode();

			if (node.isNodeType(Document.TYPE)) {
				Document attachment = new DirectDocumentModule().getProperties(session, node.getPath());
				attachments.add(attachment);
			}
		}
		
		mail.setAttachments(attachments);
		
		// Get permissions
		if (Config.SYSTEM_READONLY) {
			mail.setPermissions(Permission.NONE);
		} else {
			AccessManager am = ((SessionImpl) session).getAccessManager();
			Path path = ((NodeImpl)mailNode).getPrimaryPath();
			//Path path = ((SessionImpl)session).getHierarchyManager().getPath(((NodeImpl)folderNode).getId());
			if (am.isGranted(path, org.apache.jackrabbit.core.security.authorization.Permission.READ)) {
				mail.setPermissions(Permission.READ);
			}
			
			if (am.isGranted(path, org.apache.jackrabbit.core.security.authorization.Permission.ADD_NODE)) {
				mail.setPermissions((byte) (mail.getPermissions() | Permission.WRITE));
			}
			
			if (am.isGranted(path, org.apache.jackrabbit.core.security.authorization.Permission.REMOVE_NODE)) {
				mail.setPermissions((byte) (mail.getPermissions() | Permission.DELETE));
			}
			
			if (am.isGranted(path, org.apache.jackrabbit.core.security.authorization.Permission.MODIFY_AC)) {
				mail.setPermissions((byte) (mail.getPermissions() | Permission.SECURITY));
			}
		}
		
		log.debug("Permisos: {} => {}", mailPath, mail.getPermissions());
		log.debug("getProperties[session]: {}", mail);
		return mail;
	}

	/**
	 * Create a new mail
	 */
	private Node create(Session session, Node parentNode, String name, long size, String from, 
			String[] reply, String[] to, String[] cc, String[] bcc, Calendar sentDate, 
			Calendar receivedDate, String subject, String content, String mimeType) throws
			javax.jcr.ItemExistsException, javax.jcr.PathNotFoundException, NoSuchNodeTypeException, 
			javax.jcr.lock.LockException, VersionException, ConstraintViolationException,
			javax.jcr.RepositoryException, IOException {
		// Create and add a new mail node
		Node mailNode = parentNode.addNode(name, Mail.TYPE);
		mailNode.setProperty(Mail.SIZE, size);
		mailNode.setProperty(Mail.FROM, from);
		mailNode.setProperty(Mail.REPLY, reply);
		mailNode.setProperty(Mail.TO, to);
		mailNode.setProperty(Mail.CC, cc);
		mailNode.setProperty(Mail.BCC, bcc);
		mailNode.setProperty(Mail.SENT_DATE, sentDate);
		mailNode.setProperty(Mail.RECEIVED_DATE, receivedDate);
		mailNode.setProperty(Mail.SUBJECT, subject);
		mailNode.setProperty(Mail.CONTENT, content);
		mailNode.setProperty(Mail.MIME_TYPE, mimeType);
		
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
		mailNode.setProperty(Permission.USERS_READ, usersRead);
		mailNode.setProperty(Permission.USERS_WRITE, usersWrite);
		mailNode.setProperty(Permission.USERS_DELETE, usersDelete);
		mailNode.setProperty(Permission.USERS_SECURITY, usersSecurity);
		mailNode.setProperty(Permission.ROLES_READ, rolesRead);
		mailNode.setProperty(Permission.ROLES_WRITE, rolesWrite);
		mailNode.setProperty(Permission.ROLES_DELETE, rolesDelete);
		mailNode.setProperty(Permission.ROLES_SECURITY, rolesSecurity);
		
		parentNode.save();
		
		return mailNode;
	}
	
	@Override
	public Mail create(Mail mail) throws AccessDeniedException, RepositoryException, PathNotFoundException,
			ItemExistsException, VirusDetectedException,	DatabaseException {
		log.debug("create({})", mail);
		Mail newMail = null;
		Transaction t = null;
		XASession session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			session = (XASession) JCRUtils.getSession();
			String parent = FileUtils.getParent(mail.getPath());
			String name = FileUtils.getName(mail.getPath());
			Node parentNode = session.getRootNode().getNode(parent.substring(1));
			
			// Escape dangerous chars in name
			name = FileUtils.escape(name);
			mail.setPath(parent+"/"+name);
			
			t = new Transaction(session);
			t.start();
			
			// Create node
			Node mailNode = create(session, parentNode, name, mail.getSize(), mail.getFrom(), mail.getReply(), 
					mail.getTo(), mail.getCc(), mail.getBcc(), mail.getSentDate(), mail.getReceivedDate(),
					mail.getSubject(), mail.getContent(), mail.getMimeType());
						
			// Set returned mail properties
			newMail = getProperties(session, mail.getPath());
			
			t.end();
			t.commit();
			
			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(mailNode, session.getUserID(), "CREATE", null);
			
			// Check scripting
			DirectScriptingModule.checkScripts(session, parentNode, mailNode, "CREATE_MAIL");

			// Activity log
			UserActivity.log(session.getUserID(), "CREATE_MAIL", mail.getPath(), null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
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
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			t.rollback();
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}

		log.debug("create: {}", newMail);
		return newMail;
	}

	@Override
	public Mail getProperties(String mailPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		log.debug("getProperties({})", mailPath);
		Mail mail = null;
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			mail = getProperties(session, mailPath);
			
			// Activity log
			UserActivity.log(session.getUserID(), "GET_MAIL_PROPERTIES", mailPath, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}

		log.debug("get: {}", mail);
		return mail;
	}

	@Override
	public void delete(String mailPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, LockException, DatabaseException {
		log.debug("delete({})", mailPath);
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			session = JCRUtils.getSession();
			String name = FileUtils.getName(mailPath);
			Node folderNode = session.getRootNode().getNode(mailPath.substring(1));
			Node parentNode = folderNode.getParent();
			Node userTrash = session.getRootNode().getNode(Repository.TRASH+"/"+session.getUserID());
			
			// Test if already exists a mail with the same name in the trash
			String destPath = userTrash.getPath()+"/";
			String testName = name;
			
			for (int i=1; session.itemExists(destPath+testName); i++) {
				testName = name+" ("+i+")";
			}
			
			session.move(folderNode.getPath(), destPath+testName);
			session.getRootNode().save();
			
			// Check scripting
			DirectScriptingModule.checkScripts(session, parentNode, folderNode, "DELETE_MAIL");
			
			// Activity log
			UserActivity.log(session.getUserID(), "DELETE_MAIL", mailPath, null);
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
			JCRUtils.logout(session);
		}
				
		log.debug("delete: void");
	}	
	
	@Override
	public void purge(String mailPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, DatabaseException {
		log.debug("purge({})", mailPath);
		Node parentNode = null;
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			session = JCRUtils.getSession();
			Node folderNode = session.getRootNode().getNode(mailPath.substring(1));
			parentNode = folderNode.getParent();
			folderNode.remove();
			parentNode.save();
						
			// Check scripting
			DirectScriptingModule.checkScripts(session, parentNode, folderNode, "PURGE_MAIL");

			// Activity log
			UserActivity.log(session.getUserID(), "PURGE_MAIL", mailPath, null);
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
			JCRUtils.logout(session);
		}
		
		log.debug("purge: void");
	}

	@Override
	public Mail rename(String mailPath, String newName) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("rename({}, {})", mailPath, newName);
		Mail renamedMail = null;
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			session = JCRUtils.getSession();
			String parent = FileUtils.getParent(mailPath);
			String name = FileUtils.getName(mailPath);
							
			// Escape dangerous chars in name
			newName = FileUtils.escape(newName);
			
			if (newName != null && !newName.equals("") && !newName.equals(name)) {
				String newPath = parent+"/"+newName;
				session.move(mailPath, newPath);
				
				// Set new name
				Node mailNode = session.getRootNode().getNode(newPath.substring(1));
				mailNode.setProperty(Mail.SUBJECT, newName);
			
				// Publish changes
				session.save();	
			
				// Set returned document properties
				renamedMail = getProperties(session, newPath);
			} else {
				// Don't change anything
				renamedMail = getProperties(session, mailPath);
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "RENAME_MAIL", mailPath, newName);
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
			JCRUtils.logout(session);
		}
		
		log.debug("rename: {}", renamedMail);
		return renamedMail;
	}
	
	@Override
	public void move(String mailPath, String dstPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("move({}, {})", mailPath, dstPath);
		Session session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			session = JCRUtils.getSession();
			String name = FileUtils.getName(mailPath);
			session.move(mailPath, dstPath+"/"+name);
			session.save();
			
			// Activity log
			UserActivity.log(session.getUserID(), "MOVE_MAIL", mailPath, dstPath);
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
			JCRUtils.logout(session);
		}
		
		log.debug("move: void");
	}
	
	/**
	 * Copy recursively
	 */
	public void copy(Session session, Node srcMailNode, Node dstFolderNode) throws ValueFormatException, 
		javax.jcr.PathNotFoundException, javax.jcr.RepositoryException, IOException {
		log.debug("copy({}, {}, {})", new Object[] { session, srcMailNode, dstFolderNode });
		
		String name = srcMailNode.getName();
		long size = srcMailNode.getProperty(Mail.SIZE).getLong();
		String from = srcMailNode.getProperty(Mail.FROM).getString();
		String[] reply = JCRUtils.value2String(srcMailNode.getProperty(Mail.REPLY).getValues());
		String[] to = JCRUtils.value2String(srcMailNode.getProperty(Mail.TO).getValues());
		String[] cc = JCRUtils.value2String(srcMailNode.getProperty(Mail.CC).getValues());
		String[] bcc = JCRUtils.value2String(srcMailNode.getProperty(Mail.BCC).getValues());
		Calendar sentDate = srcMailNode.getProperty(Mail.SENT_DATE).getDate(); 
		Calendar receivedDate = srcMailNode.getProperty(Mail.RECEIVED_DATE).getDate();
		String subject = srcMailNode.getProperty(Mail.SUBJECT).getString();
		String content = srcMailNode.getProperty(Mail.CONTENT).getString();
		String mimeType = srcMailNode.getProperty(Mail.MIME_TYPE).getString();
		DirectDocumentModule ddm = new DirectDocumentModule();
		
		Node mNode = create(session, dstFolderNode, name, size, from, reply, to, cc, bcc, sentDate, receivedDate, 
				subject, content, mimeType);
		
		// Get attachments
		for (NodeIterator nit = srcMailNode.getNodes(); nit.hasNext(); ) {
			Node node = nit.nextNode();

			if (node.isNodeType(Document.TYPE)) {
				ddm.copy(session, node, mNode);
			}
		}
		
		log.debug("copy: void");
	}

	@Override
	public void copy(String mailPath, String dstPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, ItemExistsException, IOException, DatabaseException {
		log.debug("copy({}, {})", mailPath, dstPath);
		Transaction t = null;
		XASession session = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			session = (XASession) JCRUtils.getSession();
			t = new Transaction(session);
			t.start();
			
			// Make some work
			Node srcMailNode = session.getRootNode().getNode(mailPath.substring(1)); 
			Node dstFolderNode = session.getRootNode().getNode(dstPath.substring(1));
			copy(session, srcMailNode, dstFolderNode);

			t.end();
			t.commit();
			
			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(dstFolderNode, session.getUserID(), "COPY", null);
			
			// Activity log
			UserActivity.log(session.getUserID(), "COPY_MAIL", mailPath, dstPath);
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
		} finally {
			JCRUtils.logout(session);
		}
		
		log.debug("copy: void");
	}
	
	@Override
	public List<Mail> getChilds(String fldPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		log.debug("findChilds({})", fldPath);
		List<Mail> childs = new ArrayList<Mail>();
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));

			for (NodeIterator ni = folderNode.getNodes(); ni.hasNext(); ) {
				Node child = ni.nextNode();
				
				if (child.isNodeType(Mail.TYPE)) {
					childs.add(getProperties(session, child.getPath()));
				}
			}

			// Activity log
			UserActivity.log(session.getUserID(), "GET_CHILD_MAILS", fldPath, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
				
		log.debug("findChilds: {}", childs);
		return childs;
	}

	@Override
	public boolean isValid(String mailPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("isValid({})", mailPath);
		boolean valid = false;
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			Node node = session.getRootNode().getNode(mailPath.substring(1));
			
			if (node.isNodeType(Mail.TYPE)) {
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
			JCRUtils.logout(session);
		}

		log.debug("isValid: {}", valid);
		return valid;
	}
}
