/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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
import java.util.Calendar;
import java.util.Collection;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Document;
import es.git.openkm.bean.Mail;
import es.git.openkm.bean.Permission;
import es.git.openkm.bean.Repository;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.Config;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.SessionManager;
import es.git.openkm.core.VirusDetectedException;
import es.git.openkm.module.MailModule;
import es.git.openkm.util.FileUtils;
import es.git.openkm.util.JCRUtils;
import es.git.openkm.util.Transaction;
import es.git.openkm.util.UserActivity;

public class DirectMailModule implements MailModule {
	private static Logger log = LoggerFactory.getLogger(DirectMailModule.class);

	/**
	 * @param session
	 * @param fldPath
	 * @return
	 * @throws javax.jcr.RepositoryException 
	 * @throws javax.jcr.PathNotFoundException 
	 */
	public Mail getProperties(Session session, String mailPath) throws 
			javax.jcr.PathNotFoundException, javax.jcr.RepositoryException  {
		log.debug("getProperties[session]:(" + session + ", " + mailPath + ")");
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
		if (Config.SYSTEM_READONLY.equals("on")) {
			mail.setPermissions(Permission.NONE);
		} else {
			AccessManager am = ((SessionImpl) session).getAccessManager();
			
			if (am.isGranted(((NodeImpl)mailNode).getId(), Permission.READ)) {
				mail.setPermissions(Permission.READ);
			}
			
			if (am.isGranted(((NodeImpl)mailNode).getId(), Permission.WRITE)) {
				mail.setPermissions((byte) (mail.getPermissions() | Permission.WRITE));
			}
		}
		
		log.debug("Permisos: "+mailPath+": "+mail.getPermissions());
		log.debug("getProperties[session]: " + mail);
		return mail;
	}

	/**
	 * Create a new mail
	 * @throws IOException 
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
		
		Value[] rolesReadParent = parentNode.getProperty(Permission.ROLES_READ).getValues();
		String[] rolesRead = JCRUtils.rolValue2String(rolesReadParent); 
		Value[] rolesWriteParent = parentNode.getProperty(Permission.ROLES_WRITE).getValues();
		String[] rolesWrite = JCRUtils.rolValue2String(rolesWriteParent); 
		
		// Set auth info
		mailNode.setProperty(Permission.USERS_READ, usersRead);
		mailNode.setProperty(Permission.USERS_WRITE, usersWrite);
		mailNode.setProperty(Permission.ROLES_READ, rolesRead);
		mailNode.setProperty(Permission.ROLES_WRITE, rolesWrite);
		
		parentNode.save();
		
		return mailNode;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.MailModule#create(java.lang.String, com.openkm.bean.Mail)
	 */
	@Override
	public Mail create(String token, Mail mail) throws AccessDeniedException, 
			RepositoryException, PathNotFoundException, ItemExistsException, VirusDetectedException {
		log.debug("create(" + token + ", " + mail + ")");
		Mail newMail = null;
		Transaction t = null;
		
		try {
			String parent = FileUtils.getParent(mail.getPath());
			String name = FileUtils.getName(mail.getPath());
			XASession session = (XASession)SessionManager.getInstance().get(token);
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
			UserActivity.log(session, "CREATE_MAIL", mail.getPath(), null);
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
		}

		log.debug("create: " + newMail);
		return newMail;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.MailModule#getProperties(java.lang.String, java.lang.String)
	 */
	@Override
	public Mail getProperties(String token, String mailPath) throws PathNotFoundException, RepositoryException {
		log.debug("get:(" + token + ", " + mailPath + ")");
		Mail mail = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			mail = getProperties(session, mailPath);
			
			// Activity log
			UserActivity.log(session, "GET_MAIL_PROPERTIES", mailPath, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("get: " + mail);
		return mail;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.MailModule#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String token, String mailPath) throws AccessDeniedException, RepositoryException, PathNotFoundException, LockException {
		log.debug("delete(" + token + ", " + mailPath + ")");
		Session session = null;
		
		try {
			String name = FileUtils.getName(mailPath);
			session = SessionManager.getInstance().get(token);
			Node folderNode = session.getRootNode().getNode(mailPath.substring(1));
			Node parentNode = folderNode.getParent();
			Node userTrash = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+Repository.TRASH);
			
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
			UserActivity.log(session, "DELETE_MAIL", mailPath, null);
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
	
	/* (non-Javadoc)
	 * @see com.openkm.module.MailModule#purge(java.lang.String, java.lang.String)
	 */
	@Override
	public void purge(String token, String mailPath) throws AccessDeniedException, RepositoryException, PathNotFoundException {
		log.debug("purge(" + token + ", " + mailPath + ")");
		Node parentNode = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			Node folderNode = session.getRootNode().getNode(mailPath.substring(1));
			parentNode = folderNode.getParent();
			folderNode.remove();
			parentNode.save();
						
			// Check scripting
			DirectScriptingModule.checkScripts(session, parentNode, folderNode, "PURGE_MAIL");

			// Activity log
			UserActivity.log(session, "PURGE_MAIL", mailPath, null);
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
	 * @see com.openkm.module.MailModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Mail rename(String token, String mailPath, String newName) throws AccessDeniedException, RepositoryException, PathNotFoundException, ItemExistsException {
		log.debug("rename:(" + token + ", " + mailPath + ", " + newName + ")");
		Mail renamedMail = null;
		Session session = null;
		
		try {
			String parent = FileUtils.getParent(mailPath);
			String name = FileUtils.getName(mailPath);
			session = SessionManager.getInstance().get(token);
				
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
			UserActivity.log(session, "RENAME_MAIL", mailPath, newName);
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
		
		log.debug("rename: "+renamedMail);
		return renamedMail;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.MailModule#move(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void move(String token, String mailPath, String dstPath) throws AccessDeniedException, RepositoryException, PathNotFoundException, ItemExistsException {
		log.debug("move(" + token + ", " + mailPath + ", " + dstPath + ")");
		Session session = null;

		try {
			String name = FileUtils.getName(mailPath);
			session = SessionManager.getInstance().get(token);
			session.move(mailPath, dstPath+"/"+name);
			session.save();
			
			// Activity log
			UserActivity.log(session, "MOVE_MAIL", mailPath, dstPath);
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
	
	/**
	 * @param session
	 * @param srcMailNode
	 * @param dstFolderNode
	 * @throws ValueFormatException
	 * @throws javax.jcr.PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 * @throws IOException
	 */
	public void copy(Session session, Node srcMailNode, Node dstFolderNode) throws ValueFormatException, 
		javax.jcr.PathNotFoundException, javax.jcr.RepositoryException, IOException {
		log.debug("copy(" + session + ", " + srcMailNode + ", " + dstFolderNode + ")");
		
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

	/* (non-Javadoc)
	 * @see com.openkm.module.MailModule#copy(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void copy(String token, String mailPath, String dstPath) throws AccessDeniedException, 
			RepositoryException, PathNotFoundException, ItemExistsException, IOException {
		log.debug("copy(" + token + ", " + mailPath + ", " + dstPath + ")");
		Transaction t = null;
		
		try {
			XASession session = (XASession)SessionManager.getInstance().get(token);
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
			UserActivity.log(session, "COPY_MAIL", mailPath, dstPath);
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
	
	/* (non-Javadoc)
	 * @see com.openkm.module.MailModule#getChilds(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<Mail> getChilds(String token, String fldPath) throws PathNotFoundException, RepositoryException {
		log.debug("findChilds(" + token + ", " + fldPath + ")");
		ArrayList<Mail> childs = new ArrayList<Mail>();

		try {
			Session session = SessionManager.getInstance().get(token);
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));

			for (NodeIterator ni = folderNode.getNodes(); ni.hasNext(); ) {
				Node child = ni.nextNode();
				
				if (child.isNodeType(Mail.TYPE)) {
					childs.add(getProperties(session, child.getPath()));
				}
			}

			// Activity log
			UserActivity.log(session, "GET_CHILD_MAILS", fldPath, null);
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
	 * @see com.openkm.module.MailModule#isValid(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isValid(String token, String mailPath) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("isValid(" + token + ", " + mailPath + ")");
		boolean valid = false;
		
		try {
			Session session = SessionManager.getInstance().get(token);
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
		}

		log.debug("isValid: "+valid);
		return valid;
	}
}