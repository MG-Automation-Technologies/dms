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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.api.XASession;
import org.apache.jackrabbit.core.NodeImpl;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.security.AccessManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Document;
import es.git.openkm.bean.Lock;
import es.git.openkm.bean.Note;
import es.git.openkm.bean.Notification;
import es.git.openkm.bean.Permission;
import es.git.openkm.bean.Repository;
import es.git.openkm.bean.Version;
import es.git.openkm.bean.cache.UserItems;
import es.git.openkm.cache.UserItemsManager;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.Config;
import es.git.openkm.core.FileSizeExceededException;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.SessionManager;
import es.git.openkm.core.UnsupportedMimeTypeException;
import es.git.openkm.core.VersionException;
import es.git.openkm.core.VirusDetectedException;
import es.git.openkm.core.VirusDetection;
import es.git.openkm.module.DocumentModule;
import es.git.openkm.util.DocConverter;
import es.git.openkm.util.FileUtils;
import es.git.openkm.util.JCRUtils;
import es.git.openkm.util.Transaction;
import es.git.openkm.util.UserActivity;

public class DirectDocumentModule implements DocumentModule {
	private static Logger log = LoggerFactory.getLogger(DirectDocumentModule.class);

	/**
	 * @param session
	 * @param docPath
	 * @return
	 * @throws PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 */
	public Document getProperties(Session session, String docPath) throws
			javax.jcr.PathNotFoundException, javax.jcr.RepositoryException {
		log.debug("getProperties[session](" + session + ", " + docPath + ")");
		Document doc = new Document();

		Node documentNode = session.getRootNode().getNode(docPath.substring(1));
		Node contentNode = documentNode.getNode(Document.CONTENT);

		// Properties
		doc.setAuthor(documentNode.getProperty(Document.AUTHOR).getString());
		doc.setKeywords(documentNode.getProperty(Document.KEYWORDS).getString());
		doc.setPath(documentNode.getPath());
		doc.setLocked(documentNode.isLocked());
		doc.setUuid(documentNode.getUUID());

		if (doc.isLocked()) {
			doc.setLockInfo(getLock(session, docPath));
		} else {
			doc.setLockInfo(null);
		}

		doc.setCheckedOut(contentNode.isCheckedOut());
		doc.setMimeType(contentNode.getProperty(JcrConstants.JCR_MIMETYPE).getString());
		doc.setLastModified(contentNode.getProperty(JcrConstants.JCR_LASTMODIFIED).getDate());

		// Get actual version
		if (documentNode.isNodeType(Document.TYPE)) {
			javax.jcr.version.Version ver = contentNode.getBaseVersion();
			Version version = new Version();
			version.setAuthor(contentNode.getProperty(Document.AUTHOR).getString());
			version.setSize(contentNode.getProperty(Document.SIZE).getLong());
			version.setComment(contentNode.getProperty(Document.VERSION_COMMENT).getString());
			version.setName(ver.getName());
			version.setCreated(ver.getCreated());
			version.setActual(true);
			doc.setActualVersion(version);
		}

		// If this is a frozen node, we must get create property from
		// the original referenced node.
		if (documentNode.isNodeType(JcrConstants.NT_FROZENNODE)) {
			Node node = documentNode.getProperty(JcrConstants.JCR_FROZENUUID).getNode();
			doc.setCreated(node.getProperty(JcrConstants.JCR_CREATED).getDate());
		} else {
			doc.setCreated(documentNode.getProperty(JcrConstants.JCR_CREATED).getDate());
		}

		// Get permissions
		if (Config.SYSTEM_READONLY.equals("on")) {
			doc.setPermissions(Permission.NONE);
		} else {
			AccessManager am = ((SessionImpl) session).getAccessManager();
			
			if (am.isGranted(((NodeImpl)documentNode).getId(), Permission.READ)) {
				doc.setPermissions(Permission.READ);
			}
			
			if (am.isGranted(((NodeImpl)documentNode).getId(), Permission.WRITE)) {
				doc.setPermissions((byte) (doc.getPermissions() | Permission.WRITE));
			}
		}
		
		// Get user subscription
		ArrayList<String> subscriptorList = new ArrayList<String>();

		if (documentNode.isNodeType(Notification.TYPE)) {
			Value[] subscriptors = documentNode.getProperty(Notification.SUBSCRIPTORS).getValues();

			for (int i=0; i<subscriptors.length; i++) {
				subscriptorList.add(subscriptors[i].getString());

				if (session.getUserID().equals(subscriptors[i].getString())) {
					doc.setSubscribed(true);
				}
			}
		}

		doc.setSubscriptors(subscriptorList);
		doc.setConvertibleToPdf(new DocConverter().isValid(doc.getMimeType()));
		doc.setConvertibleToSwf(doc.isConvertibleToPdf() || doc.getMimeType().equals("application/pdf"));
		
		// Get comments
		ArrayList<Note> notes = new ArrayList<Note>();
		Node notesNode = documentNode.getNode(Note.LIST);
		
		for (NodeIterator nit = notesNode.getNodes(); nit.hasNext(); ) {
			Node noteNode = nit.nextNode();
			Note note = new Note();
			note.setDate(noteNode.getProperty(Note.DATE).getDate());
			note.setUser(noteNode.getProperty(Note.USER).getString());
			note.setText(noteNode.getProperty(Note.TEXT).getString());
			notes.add(note);
		}

		doc.setNotes(notes);
		
		log.debug("getProperties[session]: "+doc);
		return doc;
	}

	/**
	 * Create a new document
	 * TODO El parámetro session no hace falta porque la sesión viene implícita
	 * en el segundo parámetro parentNode.
	 */
	public Node create(Session session, Node parentNode, String name, String keywords,
			String mimeType, InputStream is) throws
			javax.jcr.ItemExistsException, javax.jcr.PathNotFoundException,
			javax.jcr.AccessDeniedException, javax.jcr.RepositoryException, IOException {
		// Create and add a new file node
		Node documentNode = parentNode.addNode(name, Document.TYPE);
		documentNode.setProperty(Document.KEYWORDS, keywords);
		documentNode.setProperty(Document.AUTHOR, session.getUserID());
		documentNode.setProperty(Document.NAME, name);
		long size = is.available();
			
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
		documentNode.setProperty(Permission.USERS_READ, usersRead);
		documentNode.setProperty(Permission.USERS_WRITE, usersWrite);
		documentNode.setProperty(Permission.ROLES_READ, rolesRead);
		documentNode.setProperty(Permission.ROLES_WRITE, rolesWrite);

		Node contentNode = documentNode.addNode(Document.CONTENT, Document.CONTENT_TYPE);
		contentNode.setProperty(Document.SIZE, size);
		contentNode.setProperty(Document.AUTHOR, session.getUserID());
		contentNode.setProperty(Document.VERSION_COMMENT, "");
		contentNode.setProperty(JcrConstants.JCR_MIMETYPE, mimeType);
		
		// jcr:encoding only have sense for text/* MIME
		if (mimeType.startsWith("text/")) {
			contentNode.setProperty(JcrConstants.JCR_ENCODING, "UTF-8");
		}

		contentNode.setProperty(JcrConstants.JCR_DATA, is);
		contentNode.setProperty(JcrConstants.JCR_LASTMODIFIED, Calendar.getInstance());
		parentNode.save();

		// Esta línea vale millones!! Resuelve la incidencia del isCkechedOut.
		// Por lo visto un nuevo nodo se añade con el isCheckedOut a true :/
		contentNode.checkin();
		
		// Update user items
		UserItemsManager.incSize(session.getUserID(), size);
		UserItemsManager.incDocuments(session.getUserID(), 1);
		
		return documentNode; 
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#create(java.lang.String, es.git.openkm.bean.Document, byte[])
	 */
	@Override
	public Document create(String token, Document doc, InputStream is) throws
			UnsupportedMimeTypeException, FileSizeExceededException, VirusDetectedException, 
			ItemExistsException, PathNotFoundException, AccessDeniedException, RepositoryException,
			IOException {
		log.debug("create(" + token + ", " + doc + ")");
		Document newDocument = null;
		Node parentNode = null;
		File tmpJcr = File.createTempFile("okm", ".jcr");
		File tmpAvr = File.createTempFile("okm", ".avr");

		try {
			String parent = FileUtils.getParent(doc.getPath());
			String name = FileUtils.getName(doc.getPath());
			int size = is.available();

			// Escape dangerous chars in name
			name = FileUtils.escape(name);
			doc.setPath(parent+"/"+name);

			// Check file restrictions
			String mimeType = Config.mimeTypes.getContentType(name.toLowerCase());

			if (Config.RESTRICT_FILE_MIME.equals("on") && !Config.mimeAccept.contains(mimeType)) {
				throw new UnsupportedMimeTypeException(mimeType);
			}
			
			// Manage temporary files
			byte[] buff = new byte[4*1024];
			FileOutputStream fosJcr = new FileOutputStream(tmpJcr);
			FileOutputStream fosAvr = new FileOutputStream(tmpAvr);
			int read;
			while ((read = is.read(buff)) != -1) {
				fosJcr.write(buff, 0, read);
				fosAvr.write(buff, 0, read);
			}
			fosJcr.flush();
			fosJcr.close();
			fosAvr.flush();
			fosAvr.close();
			is.close();
			is = new FileInputStream(tmpJcr);

			if (size > Config.MAX_FILE_SIZE) {
				throw new FileSizeExceededException(""+size);
			}
			
			if (!Config.SYSTEM_ANTIVIR.equals("")) {
				VirusDetection.detect(tmpAvr);
			}

			Session session = SessionManager.getInstance().get(token);
			parentNode = session.getRootNode().getNode(parent.substring(1));
			Node documentNode = create(session, parentNode, name, doc.getKeywords(), mimeType, is);

			// Set returned document properties
			newDocument = getProperties(session, doc.getPath());

			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "CREATE", null);
			
			// Check scripting
			DirectScriptingModule.checkScripts(parentNode, documentNode.getPath(), session.getUserID(), "CREATE_DOCUMENT");

			// Activity log
			UserActivity.log(session, "CREATE_DOCUMENT", doc.getPath(), mimeType+", "+size);
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
			tmpJcr.delete();
			tmpAvr.delete();
		}

		log.debug("create: " + newDocument);
		return newDocument;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String token, String docPath) throws AccessDeniedException, RepositoryException, PathNotFoundException, LockException {
		log.debug("delete(" + token + ", " + docPath + ")");
		Session session = null;

		try {
			String name = FileUtils.getName(docPath);
			session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node parentNode = documentNode.getParent();
			Node userTrash = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+Repository.TRASH);

			if (documentNode.isLocked()) {
				throw new LockException("Can't delete a locked node");
			}

			// Test if already exists a document whith the same name in the trash
			String destPath = userTrash.getPath()+"/";
			String testName = name;
			String fileName = FileUtils.getFileName(name);
			String fileExtension = FileUtils.getFileExtension(name);

			for (int i=1; session.itemExists(destPath+testName); i++) {
				testName = fileName+" ("+i+")."+fileExtension;
			}

			session.move(documentNode.getPath(), destPath+testName);
			session.getRootNode().save();

			// Check scripting
			DirectScriptingModule.checkScripts(parentNode, docPath, session.getUserID(), "DELETE_DOCUMENT");

			// Activity log
			UserActivity.log(session, "DELETE_DOCUMENT", docPath, null);
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
	 * @see es.git.openkm.module.DocumentModule#getProperties(java.lang.String, java.lang.String)
	 */
	@Override
	public Document getProperties(String token, String docPath) throws RepositoryException, PathNotFoundException {
		log.debug("getProperties(" + token + ", " + docPath + ")");
		Document doc = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			doc = getProperties(session, docPath);

			// Activity log
			UserActivity.log(session, "GET_DOCUMENT_PROPERTIES", docPath, doc.getKeywords());
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getProperties: "+doc);
		return doc;
	}
	
	/**
	 * @param session
	 * @param docNode
	 * @return
	 * @throws javax.jcr.PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 * @throws IOException
	 */
	public InputStream getContent(Session session, Node docNode) throws javax.jcr.PathNotFoundException,
			javax.jcr.RepositoryException, IOException {
		log.debug("getContent[session](" + session + ", " + docNode+ ")");
		
		Node contentNode = docNode.getNode(Document.CONTENT);
		InputStream is = contentNode.getProperty(JcrConstants.JCR_DATA).getStream();
		
		log.debug("getContent[]session: "+is);
		return is;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getContent(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public InputStream getContent(String token, String docPath, boolean checkout) throws
			PathNotFoundException, RepositoryException, IOException {
		log.debug("getContent(" + token + ", " + docPath + ")");
		InputStream is;

		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			is = getContent(session, documentNode);

			// Activity log
			UserActivity.log(session, (checkout?"GET_DOCUMENT_CONTENT_CHECKOUT":"GET_DOCUMENT_CONTENT"), docPath, ""+is.available());
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
		}

		log.debug("getContent: "+is);
		return is;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getContentByVersion(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public InputStream getContentByVersion(String token, String docPath, String versionId)
			throws RepositoryException, PathNotFoundException, IOException {
		log.debug("getContentByVersion(" + token + ", " + docPath + ")");
		InputStream is;

		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = documentNode.getNode(Document.CONTENT);
			VersionHistory vh = contentNode.getVersionHistory();
			javax.jcr.version.Version ver = vh.getVersion(versionId);
			Node frozenNode = ver.getNode(JcrConstants.JCR_FROZENNODE);
			is = frozenNode.getProperty(JcrConstants.JCR_DATA).getStream();

			// Activity log
			UserActivity.log(session, "GET_DOCUMENT_CONTENT_BY_VERSION", docPath, versionId+", "+is.available());
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getContentByVersion: "+is);
		return is;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#setContent(java.lang.String, java.lang.String, byte[])
	 */
	@Override
	public void setContent(String token, String docPath, InputStream is) throws
			FileSizeExceededException, VirusDetectedException, VersionException, 
			LockException, PathNotFoundException, AccessDeniedException, 
			RepositoryException, IOException {
		log.debug("setContent(" + token + ", " + docPath + ", " + is + ")");
		Node contentNode = null;
		File tmpJcr = File.createTempFile("okm", ".jcr");
		File tmpAvr = File.createTempFile("okm", ".avr");

		try {
			int size = is.available();
		
			if (size > Config.MAX_FILE_SIZE) {
				throw new FileSizeExceededException(""+size);
			}
			
			// Manage temporary files
			byte[] buff = new byte[4*1024];
			FileOutputStream fosJcr = new FileOutputStream(tmpJcr);
			FileOutputStream fosAvr = new FileOutputStream(tmpAvr);
			int read;
			while ((read = is.read(buff)) != -1) {
				fosJcr.write(buff, 0, read);
				fosAvr.write(buff, 0, read);
			}
			fosJcr.flush();
			fosJcr.close();
			fosAvr.flush();
			fosAvr.close();
			is.close();
			is = new FileInputStream(tmpJcr);
			
			if (!Config.SYSTEM_ANTIVIR.equals("")) {
				VirusDetection.detect(tmpAvr);
			}

			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			contentNode = documentNode.getNode(Document.CONTENT);
			contentNode.setProperty(Document.SIZE, size);
			contentNode.setProperty(JcrConstants.JCR_DATA, is);
			contentNode.setProperty(JcrConstants.JCR_LASTMODIFIED, Calendar.getInstance());
			contentNode.save();

			// Check scripting
			DirectScriptingModule.checkScripts(documentNode, documentNode.getPath(), session.getUserID(), "SET_DOCUMENT_CONTENT");

			// Activity log
			UserActivity.log(session, "SET_DOCUMENT_CONTENT", docPath, ""+size);
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
		} catch (java.io.IOException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(contentNode);
			throw e;
		} finally {
			tmpJcr.delete();
			tmpAvr.delete();
		}

		log.debug("setContent: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#addComment(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addNote(String token, String docPath, String text) throws LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException {
		log.info("addNote(" + token + ", " + docPath + ", " + text + ")");
		Node notesNode = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			notesNode = documentNode.getNode(Note.LIST);
			Calendar cal = Calendar.getInstance();
			Node note = notesNode.addNode(cal.getTimeInMillis()+"", Note.TYPE);
			note.setProperty(Note.DATE, cal);
			note.setProperty(Note.USER, session.getUserID());
			note.setProperty(Note.TEXT, text);
			notesNode.save();

			// Activity log
			UserActivity.log(session, "ADD_DOCUMENT_COMMENT", docPath, text);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(notesNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(notesNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(notesNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(notesNode);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("addNote: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getChilds(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<Document> getChilds(String token, String fldPath) throws PathNotFoundException, RepositoryException {
		log.debug("getChilds(" + token + ", " + fldPath + ")");
		ArrayList<Document> childs = new ArrayList<Document>();

		try {
			Session session = SessionManager.getInstance().get(token);
			Node folderNode = session.getRootNode().getNode(fldPath.substring(1));

			for (NodeIterator ni = folderNode.getNodes(); ni.hasNext(); ) {
				Node child = ni.nextNode();
				log.debug("Child: "+child.getPath()+", "+child.getPrimaryNodeType().getName());

				if (child.isNodeType(Document.TYPE)) {
					childs.add(getProperties(session, child.getPath()));
				}
			}

			// Activity log
			UserActivity.log(session, "GET_CHILD_DOCUMENTS", fldPath, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getChilds: "+childs);
		return childs;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Document rename(String token, String docPath, String newName) throws AccessDeniedException, RepositoryException, PathNotFoundException, ItemExistsException {
		log.debug("rename:(" + token + ", " + docPath + ", " + newName + ")");
		Document renamedDocument = null;
		Session session = null;

		try {
			String parent = FileUtils.getParent(docPath);
			String name = FileUtils.getName(docPath);
			session = SessionManager.getInstance().get(token);

			// Escape dangerous chars in name
			newName = FileUtils.escape(newName);

			if (newName != null && !newName.equals("") && !newName.equals(name)) {
				String newPath = parent+"/"+newName;
				session.move(docPath, newPath);

				// Set new name
				Node documentNode = session.getRootNode().getNode(newPath.substring(1));
				documentNode.setProperty(Document.NAME, newName);

				// Publish changes
				session.save();

				// Set returned document properties
				renamedDocument = getProperties(session, newPath);
			} else {
				// Don't change anything
				renamedDocument = getProperties(session, docPath);
			}

			// Activity log
			UserActivity.log(session, "RENAME_DOCUMENT", docPath, newName);
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

		log.debug("rename: "+renamedDocument);
		return renamedDocument;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#setProperties(java.lang.String, es.git.openkm.bean.Document)
	 */
	@Override
	public void setProperties(String token, Document doc) throws VersionException, LockException, PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("setProperties(" + token + ", " + doc + ")");
		Node documentNode = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			documentNode = session.getRootNode().getNode(doc.getPath().substring(1));

			// Set document node properties
			documentNode.setProperty(Document.KEYWORDS, doc.getKeywords());
			documentNode.save();

			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "SET_PROPERTIES", null);

			// Check scripting
			DirectScriptingModule.checkScripts(documentNode, documentNode.getPath(), session.getUserID(), "SET_DOCUMENT_PROPERTIES");

			// Activity log
			UserActivity.log(session, "SET_DOCUMENT_PROPERTIES", doc.getPath(), doc.getKeywords());
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.version.VersionException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new VersionException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("setProperties: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#checkout(java.lang.String, java.lang.String)
	 */
	@Override
	public void checkout(String token, String docPath) throws AccessDeniedException, RepositoryException, PathNotFoundException, LockException {
		log.debug("checkout(" + token + ", " + docPath + ")");
		Transaction t = null;

		try {
			XASession session = (XASession)SessionManager.getInstance().get(token);
			javax.jcr.lock.Lock lck = null;

			t = new Transaction(session);
			t.start();

			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = documentNode.getNode(Document.CONTENT);
			lck = documentNode.lock(true, false);
			contentNode.checkout();

			t.end();
			t.commit();

			// Check scripting
			DirectScriptingModule.checkScripts(documentNode, documentNode.getPath(), session.getUserID(), "CHECKOUT_DOCUMENT");

			// Activity log
			UserActivity.log(session, "CHECKOUT_DOCUMENT", docPath, lck.getLockToken());
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
		}

		log.debug("checkout: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#cancelCheckout(java.lang.String, java.lang.String)
	 */
	@Override
	public void cancelCheckout(String token, String docPath) throws AccessDeniedException, RepositoryException, PathNotFoundException, LockException {
		log.debug("cancelCheckout(" + token + ", " + docPath + ")");
		Transaction t = null;

		try {
			XASession session = (XASession)SessionManager.getInstance().get(token);
			Node documentNode = null;

			t = new Transaction(session);
			t.start();

			documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = documentNode.getNode(Document.CONTENT);
			contentNode.restore(contentNode.getBaseVersion(), true);
			documentNode.unlock();

			t.end();
			t.commit();

			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "CANCEL_CHECKOUT", null);

			// Check scripting
			DirectScriptingModule.checkScripts(documentNode, documentNode.getPath(), session.getUserID(), "CANCEL_CHECKOUT_DOCUMENT");

			// Activity log
			UserActivity.log(session, "CANCEL_CHECKOUT_DOCUMENT", docPath, null);
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
		}

		log.debug("cancelCheckout: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#isCheckedOut(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isCheckedOut(String token, String docPath) throws RepositoryException, PathNotFoundException {
		log.debug("isCheckedOut(" + token + ", " + docPath + ")");
		boolean checkedOut = false;

		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = documentNode.getNode(Document.CONTENT);
			checkedOut = contentNode.isCheckedOut();
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("isCheckedOut: "+checkedOut);
		return checkedOut;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#checkin(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Version checkin(String token, String docPath, String comment) throws AccessDeniedException, RepositoryException, PathNotFoundException, LockException, VersionException {
		log.debug("checkin(" + token + ", " + docPath + ", " + comment + ")");
		Version version = new Version();
		Node contentNode = null;
		Transaction t = null;

		try {
			XASession session = (XASession)SessionManager.getInstance().get(token);
			Node documentNode = null;

			t = new Transaction(session);
			t.start();

			documentNode = session.getRootNode().getNode(docPath.substring(1));
			contentNode = documentNode.getNode(Document.CONTENT);

			// Set version author
			contentNode.setProperty(Document.AUTHOR, session.getUserID());
			contentNode.setProperty(Document.VERSION_COMMENT, comment);
			contentNode.save();

			// Performs checkin & unlock
			javax.jcr.version.Version ver = contentNode.checkin();
			version.setAuthor(contentNode.getProperty(Document.AUTHOR).getString());
			version.setSize(contentNode.getProperty(Document.SIZE).getLong());
			version.setComment(contentNode.getProperty(Document.VERSION_COMMENT).getString());
			version.setName(ver.getName());
			version.setCreated(ver.getCreated());
			version.setActual(true);
			documentNode.unlock();
			
			t.end();
			t.commit();
			
			// Update user items
			long size = contentNode.getProperty(Document.SIZE).getLong();
			UserItemsManager.incSize(session.getUserID(), size);
			
			// Add comment (as system user)
			String systemToken = SessionManager.getInstance().getSystemToken();
			addNote(systemToken, docPath, "New version "+version.getName()+" by "+session.getUserID()+": "+version.getComment());

			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "CHECKIN", comment);

			// Check scripting
			DirectScriptingModule.checkScripts(documentNode, documentNode.getPath(), session.getUserID(), "CHECKIN_DOCUMENT");

			// Activity log
			UserActivity.log(session, "CHECKIN_DOCUMENT", docPath, comment);
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
		}

		log.debug("checkin: "+version);
		return version;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getVersionHistory(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<Version> getVersionHistory(String token, String docPath) throws
			PathNotFoundException, RepositoryException {
		log.debug("getVersionHistory(" + token + ", " + docPath + ")");
		ArrayList<Version> history = new ArrayList<Version>();

		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = documentNode.getNode(Document.CONTENT);
			VersionHistory vh = contentNode.getVersionHistory();
			String baseVersion = contentNode.getBaseVersion().getName();

			for (VersionIterator vi = vh.getAllVersions(); vi.hasNext(); ) {
				javax.jcr.version.Version ver = vi.nextVersion();
				String versionName = ver.getName();

				// The rootVersion is not a "real" version node.
				if (!versionName.equals(JcrConstants.JCR_ROOTVERSION)) {
					Version version = new Version();
					Node frozenNode = ver.getNode(JcrConstants.JCR_FROZENNODE);
					version.setAuthor(frozenNode.getProperty(Document.AUTHOR).getString());
					version.setSize(frozenNode.getProperty(Document.SIZE).getLong());
					version.setComment(frozenNode.getProperty(Document.VERSION_COMMENT).getString());
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
			UserActivity.log(session, "GET_DOCUMENT_VERSION_HISTORY", docPath, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getVersionHistory: "+history);
		return history;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#lock(java.lang.String, java.lang.String)
	 */
	@Override
	public void lock(String token, String docPath) throws AccessDeniedException, RepositoryException, PathNotFoundException, LockException {
		log.debug("lock(" + token + ", " + docPath + ")");

		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			javax.jcr.lock.Lock lck = documentNode.lock(true, false);

			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "LOCK", null);

			// Check scripting
			DirectScriptingModule.checkScripts(documentNode, documentNode.getPath(), session.getUserID(), "LOCK_DOCUMENT");

			// Activity log
			UserActivity.log(session, "LOCK_DOCUMENT", docPath, lck.getLockToken());
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
		}
		
		log.debug("lock: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#unlock(java.lang.String, java.lang.String)
	 */
	@Override
	public void unlock(String token, String docPath) throws AccessDeniedException, RepositoryException, PathNotFoundException, LockException {
		log.debug("unlock(" + token + ", " + docPath + ")");

		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			documentNode.unlock();

			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "UNLOCK", null);
			
			// Check scripting
			DirectScriptingModule.checkScripts(documentNode, documentNode.getPath(), session.getUserID(), "UNLOCK_DOCUMENT");

			// Activity log
			UserActivity.log(session, "UNLOCK_DOCUMENT", docPath, null);
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
		}
		
		log.debug("unlock: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#isLocked(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isLocked(String token, String docPath) throws RepositoryException, PathNotFoundException {
		log.debug("isLocked(" + token + ", " + docPath + ")");
		boolean locked;

		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			locked = documentNode.isLocked();
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("isLocked: "+locked);
		return locked;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getLock(java.lang.String, java.lang.String)
	 */
	@Override
	public Lock getLock(String token, String docPath) throws RepositoryException, PathNotFoundException, LockException {
		log.debug("getLock(" + token + ", " + docPath + ")");
		Lock lock = new Lock();

		try {
			Session session = SessionManager.getInstance().get(token);
			lock = getLock(session, docPath);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getLock: "+lock);
		return lock;
	}

	/**
	 *
	 * @param session
	 * @param docPath
	 * @return
	 * @throws javax.jcr.RepositoryException
	 * @throws javax.jcr.AccessDeniedException
	 * @throws javax.jcr.lock.LockException
	 * @throws UnsupportedRepositoryOperationException
	 * @throws RepositoryException
	 * @throws PathNotFoundException
	 * @throws LockException
	 * @throws javax.jcr.RepositoryException
	 * @throws PathNotFoundException
	 */
	private Lock getLock(Session session, String docPath) throws UnsupportedRepositoryOperationException, javax.jcr.lock.LockException, javax.jcr.AccessDeniedException, javax.jcr.RepositoryException {
		log.debug("getLock(" + session + ", " + docPath + ")");
		Lock lock = new Lock();
		Node documentNode = session.getRootNode().getNode(docPath.substring(1));
		javax.jcr.lock.Lock lck = documentNode.getLock();
		lock.setOwner(lck.getLockOwner());
		lock.setNodePath(lck.getNode().getPath());
		lock.setToken(lck.getLockToken());
		log.debug("getLock: "+lock);
		return lock;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#purge(java.lang.String, java.lang.String)
	 */
	@Override
	public void purge(String token, String docPath) throws AccessDeniedException, RepositoryException, PathNotFoundException {
		log.debug("purge(" + token + ", " + docPath + ")");
		Node parentNode = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			parentNode = documentNode.getParent();
			HashMap<String, UserItems> userItemsHash = purgeHelper(session, documentNode);
			parentNode.save();
			
			// Update user items
			for (Iterator<Entry<String, UserItems>> it = userItemsHash.entrySet().iterator(); it.hasNext(); ) {
				Entry<String, UserItems> entry = it.next();
				String uid = entry.getKey();
				UserItems userItems = entry.getValue();
				UserItemsManager.decSize(uid, userItems.getSize());
				UserItemsManager.decDocuments(uid, userItems.getDocuments());
				UserItemsManager.decFolders(uid, userItems.getDocuments());
			}
			
			// Activity log
			UserActivity.log(session, "PURGE_DOCUMENT", docPath, null);
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
	
	/**
	 * 
	 */
	public HashMap<String, UserItems> purgeHelper(Session session, Node docNode) 
			throws javax.jcr.PathNotFoundException, javax.jcr.RepositoryException {
		Node contentNode = docNode.getNode(Document.CONTENT);
		long size = contentNode.getProperty(Document.SIZE).getLong();
		String author = contentNode.getProperty(Document.AUTHOR).getString();
		VersionHistory vh = contentNode.getVersionHistory();
		String baseVersion = contentNode.getBaseVersion().getName();
		HashMap<String, UserItems> userItemsHash = new HashMap<String, UserItems>();
		
		// Unreferenced VersionHistory should be deleted automatically
		// https://issues.apache.org/jira/browse/JCR-134
		// http://markmail.org/message/7aildokt74yeoar5
		// http://markmail.org/message/nhbwe7o3c7pd4sga
		// TODO Re-evaluate this issue when switch to Jackrabbit 1.6
		for (VersionIterator vi = vh.getAllVersions(); vi.hasNext(); ) {
			javax.jcr.version.Version ver = vi.nextVersion();
			String versionName = ver.getName();
			
			// The rootVersion is not a "real" version node.
			if (!versionName.equals(JcrConstants.JCR_ROOTVERSION) && !versionName.equals(baseVersion)) {
				Node frozenNode = ver.getNode(JcrConstants.JCR_FROZENNODE);
				size = frozenNode.getProperty(Document.SIZE).getLong();
				author = frozenNode.getProperty(Document.AUTHOR).getString();
				vh.removeVersion(versionName);
				
				// Update local user items for versions
				UserItems userItems = userItemsHash.get(author);
				if (userItems == null) userItems = new UserItems();
				userItems.setSize(userItems.getSize() + size);
				userItems.setDocuments(userItems.getDocuments() + 1);
				userItemsHash.put(author, userItems);
			}
		}

		// Update local user items for working version
		UserItems userItems = userItemsHash.get(author);
		if (userItems == null) userItems = new UserItems();
		userItems.setSize(userItems.getSize() + size);
		userItems.setDocuments(userItems.getDocuments() + 1);
		userItemsHash.put(author, userItems);
		
		docNode.remove();
		return userItemsHash;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#move(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void move(String token, String docPath, String dstPath) throws PathNotFoundException, ItemExistsException, AccessDeniedException, RepositoryException {
		log.debug("move(" + token + ", " + docPath + ", " + dstPath + ")");
		Session session = null;

		try {
			String name = FileUtils.getName(docPath);
			session = SessionManager.getInstance().get(token);
			session.move(docPath, dstPath+"/"+name);
			session.save();

			// Activity log
			UserActivity.log(session, "MOVE_DOCUMENT", docPath, dstPath);
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
	 * @see es.git.openkm.module.DocumentModule#copy(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void copy(String token, String docPath, String dstPath) throws
			ItemExistsException, PathNotFoundException, AccessDeniedException,
			RepositoryException, IOException {
		log.debug("copy(" + token + ", " + docPath + ", " + dstPath + ")");
		Node dstFolderNode = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			Node srcDocumentNode = session.getRootNode().getNode(docPath.substring(1));
			dstFolderNode = session.getRootNode().getNode(dstPath.substring(1));
			copy(session, srcDocumentNode, dstFolderNode);

			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(dstFolderNode, session.getUserID(), "COPY", null);

			// Activity log
			UserActivity.log(session, "COPY_DOCUMENT", docPath, dstPath);
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
		}

		log.debug("copy: void");
	}
	
	/**
	 * Is invoked from DirectDocumentNode and DirectFolderNode. 
	 * 
	 * @param session
	 * @param srcDocumentNode
	 * @param dstFolderNode
	 * @throws ValueFormatException
	 * @throws javax.jcr.PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 * @throws IOException
	 */
	public void copy(Session session, Node srcDocumentNode, Node dstFolderNode) throws ValueFormatException, 
			javax.jcr.PathNotFoundException, javax.jcr.RepositoryException, IOException {
		log.debug("copy(" + srcDocumentNode + ", " + dstFolderNode + ")");
		
		String keywords = srcDocumentNode.getProperty(Document.KEYWORDS).getString();
		Node srcDocumentContentNode = srcDocumentNode.getNode(Document.CONTENT);
		String mimeType = srcDocumentContentNode.getProperty("jcr:mimeType").getString();
		InputStream is = srcDocumentContentNode.getProperty("jcr:data").getStream();
		create(session, dstFolderNode, srcDocumentNode.getName(), keywords, mimeType, is);
		is.close();
		
		log.debug("copy: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#restoreVersion(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void restoreVersion(String token, String docPath, String versionId) throws AccessDeniedException, RepositoryException, PathNotFoundException {
		log.debug("restoreVersion(" + token + ", " + docPath + ", " + versionId + ")");
		Node contentNode = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			contentNode = documentNode.getNode(Document.CONTENT);
			contentNode.restore(versionId, true);
			contentNode.save();

			// Activity log
			UserActivity.log(session, "RESTORE_VERSION", docPath, versionId);
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
		}
		
		log.debug("restoreVersion: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#purgeVersionHistory(java.lang.String, java.lang.String)
	 */
	@Override
	public void purgeVersionHistory(String token, String docPath) throws AccessDeniedException, RepositoryException, PathNotFoundException {
		log.debug("purgeVersionHistory("+token+", "+docPath+")");

		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = documentNode.getNode(Document.CONTENT);
			VersionHistory vh = contentNode.getVersionHistory();
			String baseVersion = contentNode.getBaseVersion().getName();

			for (VersionIterator vi = vh.getAllVersions(); vi.hasNext(); ) {
				javax.jcr.version.Version ver = vi.nextVersion();
				String versionName = ver.getName();

				// The rootVersion is not a "real" version node.
				if (!versionName.equals(JcrConstants.JCR_ROOTVERSION) && !versionName.equals(baseVersion)) {
					vh.removeVersion(versionName);
				}
			}

			// Activity log
			UserActivity.log(session, "PURGE_VERSION_HISTORY", docPath, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("purgeVersionHistory: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getVersionHistorySize(java.lang.String, java.lang.String)
	 */
	@Override
	public long getVersionHistorySize(String token, String docPath) throws RepositoryException, PathNotFoundException {
		log.debug("getVersionHistorySize(" + token + ", " + docPath + ")");
		long ret = 0;

		try {
			Session session = SessionManager.getInstance().get(token);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = documentNode.getNode(Document.CONTENT);
			VersionHistory vh = contentNode.getVersionHistory();

			for (VersionIterator vi = vh.getAllVersions(); vi.hasNext(); ) {
				javax.jcr.version.Version ver = vi.nextVersion();
				String versionName = ver.getName();

				// The rootVersion is not a "real" version node.
				if (!versionName.equals(JcrConstants.JCR_ROOTVERSION)) {
					Node frozenNode = ver.getNode(JcrConstants.JCR_FROZENNODE);
					ret += frozenNode.getProperty(Document.SIZE).getLong();
				}
			}
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getVersionHistorySize: "+ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#isValid(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isValid(String token, String docPath) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("isValid(" + token + ", " + docPath + ")");
		boolean valid = false;

		try {
			Session session = SessionManager.getInstance().get(token);
			Node node = session.getRootNode().getNode(docPath.substring(1));

			if (node.isNodeType(Document.TYPE)) {
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

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getPath(java.lang.String, java.lang.String)
	 */
	@Override
	public String getPath(String token, String uuid) throws AccessDeniedException, RepositoryException {
		log.debug("getPath(" + token + ", " + uuid + ")");
		String path = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			Node node = session.getNodeByUUID(uuid);

			if (node.isNodeType(Document.TYPE)) {
				path = node.getPath();
			}
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getPath: "+path);
		return path;
	}
}
