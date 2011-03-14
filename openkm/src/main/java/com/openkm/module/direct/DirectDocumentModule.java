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
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.api.XASession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Document;
import com.openkm.bean.Lock;
import com.openkm.bean.Repository;
import com.openkm.bean.Version;
import com.openkm.bean.kea.MetadataDTO;
import com.openkm.bean.kea.Term;
import com.openkm.cache.UserItemsManager;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.FileSizeExceededException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.JcrSessionManager;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UnsupportedMimeTypeException;
import com.openkm.core.UserQuotaExceededException;
import com.openkm.core.VersionException;
import com.openkm.core.VirusDetectedException;
import com.openkm.core.VirusDetection;
import com.openkm.dao.LockTokenDAO;
import com.openkm.dao.MimeTypeDAO;
import com.openkm.dao.bean.cache.UserItems;
import com.openkm.kea.RDFREpository;
import com.openkm.kea.metadata.MetadataExtractionException;
import com.openkm.kea.metadata.MetadataExtractor;
import com.openkm.module.DocumentModule;
import com.openkm.module.base.BaseDocumentModule;
import com.openkm.module.base.BaseNotificationModule;
import com.openkm.module.base.BaseScriptingModule;
import com.openkm.util.FileUtils;
import com.openkm.util.JCRUtils;
import com.openkm.util.Transaction;
import com.openkm.util.UserActivity;

public class DirectDocumentModule implements DocumentModule {
	private static Logger log = LoggerFactory.getLogger(DirectDocumentModule.class);
	
	@Override
	public Document create(String token, Document doc, InputStream is) throws UnsupportedMimeTypeException, 
			FileSizeExceededException, UserQuotaExceededException, VirusDetectedException, 
			ItemExistsException, PathNotFoundException, AccessDeniedException, 
			RepositoryException, IOException, DatabaseException {
		log.debug("create({})", doc);
		Document newDocument = null;
		Node parentNode = null;
		Session session = null;
		int size = is.available();
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		if (size > Config.MAX_FILE_SIZE) {
			throw new FileSizeExceededException(Integer.toString(size));
		}
		
		File tmpJcr = File.createTempFile("okm", ".jcr");
		File tmpAvr = File.createTempFile("okm", ".avr");
		String parent = FileUtils.getParent(doc.getPath());
		String name = FileUtils.getName(doc.getPath());
		
		// Add to kea - must have the same extension
		int idx = name.lastIndexOf('.');
		String fileExtension = idx>0?name.substring(idx):".tmp";
		File tmpKea = File.createTempFile("kea", fileExtension);
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			// Escape dangerous chars in name
			name = FileUtils.escape(name);
			doc.setPath(parent+"/"+name);
			
			// Check file restrictions
			String mimeType = Config.mimeTypes.getContentType(name.toLowerCase());
			
			if (Config.RESTRICT_FILE_MIME && MimeTypeDAO.findByName(mimeType) == null) {
				throw new UnsupportedMimeTypeException(mimeType);
			}
			
			// Manage temporary files
			byte[] buff = new byte[4*1024];
			FileOutputStream fosJcr = new FileOutputStream(tmpJcr);
			FileOutputStream fosAvr = new FileOutputStream(tmpAvr);
			FileOutputStream fosKea = new FileOutputStream(tmpKea);
			int read;
			while ((read = is.read(buff)) != -1) {
				fosJcr.write(buff, 0, read);
				if (!Config.SYSTEM_ANTIVIR.equals("")) fosAvr.write(buff, 0, read);
				if (!Config.KEA_MODEL_FILE.equals("")) fosKea.write(buff, 0, read);
			}
			fosJcr.flush();
			fosJcr.close();
			fosAvr.flush();
			fosAvr.close();
			fosKea.flush();
			fosKea.close();
			is.close();
			is = new FileInputStream(tmpJcr);
			
			if (!Config.SYSTEM_ANTIVIR.equals("")) {
				VirusDetection.detect(tmpAvr);
			}
			
			// Start KEA
			Collection<String> keywords = doc.getKeywords() != null ? doc.getKeywords() : new ArrayList<String>(); // Adding submitted keywords
	        if (!Config.KEA_MODEL_FILE.equals("")) {
		        MetadataExtractor mdExtractor = new MetadataExtractor(Config.KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER);
		        MetadataDTO mdDTO = mdExtractor.extract(tmpKea);
		        
		        for (ListIterator<Term> it = mdDTO.getSubjectsAsTerms().listIterator(); it.hasNext();) {
		        	Term term =  it.next();
		        	log.info("Term:" + term.getText());
		        	if (Config.KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION) {
		        		if (RDFREpository.getInstance().getKeywords().contains(term.getText())) {
		        			keywords.add(term.getText().replace(" ", "_")); // Replacing spaces to "_" and adding at ends space for other word
		        		}
		        	} else {
		        		keywords.add(term.getText().replace(" ", "_")); // Replacing spaces to "_" and adding at ends space for other word
		        	}
		        }
	        }
	        // End KEA
	        
			parentNode = session.getRootNode().getNode(parent.substring(1));
			Node documentNode = BaseDocumentModule.create(session, parentNode, name, null /* doc.getTitle() */,
					mimeType, keywords.toArray(new String[keywords.size()]), is);
			
			// Check document filters
			//DocumentUtils.checkFilters(session, documentNode, mimeType);
			
			// Set returned document properties
			newDocument = BaseDocumentModule.getProperties(session, documentNode);
			
			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "CREATE", null);
			
			// Check scripting
			BaseScriptingModule.checkScripts(session, parentNode, documentNode, "CREATE_DOCUMENT");
			
			// Activity log
			UserActivity.log(session.getUserID(), "CREATE_DOCUMENT", documentNode.getUUID(), mimeType+", "+size+", "+doc.getPath());
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
		} catch (MetadataExtractionException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			org.apache.commons.io.FileUtils.deleteQuietly(tmpJcr);
			org.apache.commons.io.FileUtils.deleteQuietly(tmpAvr);
			org.apache.commons.io.FileUtils.deleteQuietly(tmpKea);
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("create: {}", newDocument);
		return newDocument;
	}

	@Override
	public void delete(String token, String docPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, LockException, DatabaseException {
		log.debug("delete({}, {})", token, docPath);
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
			
			String name = FileUtils.getName(docPath);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node parentNode = documentNode.getParent();
			Node userTrash = session.getRootNode().getNode(Repository.TRASH+"/"+session.getUserID());

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
			BaseScriptingModule.checkScripts(session, parentNode, documentNode, "DELETE_DOCUMENT");

			// Activity log
			UserActivity.log(session.getUserID(), "DELETE_DOCUMENT", documentNode.getUUID(), docPath);
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
	public Document getProperties(String token, String docPath) throws RepositoryException, 
			PathNotFoundException, DatabaseException {
		log.debug("getProperties({}, {})", token, docPath);
		Document doc = null;
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			doc = BaseDocumentModule.getProperties(session, documentNode);

			// Activity log
			UserActivity.log(session.getUserID(), "GET_DOCUMENT_PROPERTIES", documentNode.getUUID(), doc.getKeywords().toString()+", "+docPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("getProperties: {}", doc);
		return doc;
	}

	@Override
	public InputStream getContent(String token, String docPath, boolean checkout) throws 
			PathNotFoundException, RepositoryException, IOException, DatabaseException {
		log.debug("getContent({}, {}, {})", new Object[] { token, docPath, checkout });
		InputStream is;
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			is = BaseDocumentModule.getContent(session, docPath, checkout);
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

		log.debug("getContent: {}", is);
		return is;
	}

	@Override
	public InputStream getContentByVersion(String token, String docPath, String versionId) throws 
			RepositoryException, PathNotFoundException, IOException, DatabaseException {
		log.debug("getContentByVersion({}, {}, {})", new Object[] { token, docPath, versionId });
		InputStream is;
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = documentNode.getNode(Document.CONTENT);
			VersionHistory vh = contentNode.getVersionHistory();
			javax.jcr.version.Version ver = vh.getVersion(versionId);
			Node frozenNode = ver.getNode(JcrConstants.JCR_FROZENNODE);
			is = frozenNode.getProperty(JcrConstants.JCR_DATA).getStream();

			// Activity log
			UserActivity.log(session.getUserID(), "GET_DOCUMENT_CONTENT_BY_VERSION", documentNode.getUUID(), versionId+", "+is.available()+", "+docPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("getContentByVersion: "+is);
		return is;
	}

	@Override
	public void setContent(String token, String docPath, InputStream is) throws FileSizeExceededException,
			UserQuotaExceededException, VirusDetectedException, VersionException, LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, IOException, 
			DatabaseException {
		log.debug("setContent({}, {})", docPath, is);
		Node contentNode = null;
		Session session = null;
		int size = is.available();
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		if (size > Config.MAX_FILE_SIZE) {
			throw new FileSizeExceededException(""+size);
		}
		
		File tmpJcr = File.createTempFile("okm", ".jcr");
		File tmpAvr = File.createTempFile("okm", ".avr");

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			// Manage temporary files
			byte[] buff = new byte[4*1024];
			FileOutputStream fosJcr = new FileOutputStream(tmpJcr);
			FileOutputStream fosAvr = new FileOutputStream(tmpAvr);
			int read;
			while ((read = is.read(buff)) != -1) {
				fosJcr.write(buff, 0, read);
				if (!Config.SYSTEM_ANTIVIR.equals("")) fosAvr.write(buff, 0, read);
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
			
			JCRUtils.loadLockTokens(session);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			contentNode = documentNode.getNode(Document.CONTENT);
			contentNode.setProperty(Document.SIZE, size);
			contentNode.setProperty(JcrConstants.JCR_DATA, is);
			contentNode.setProperty(JcrConstants.JCR_LASTMODIFIED, Calendar.getInstance());
			contentNode.save();

			// Check scripting
			BaseScriptingModule.checkScripts(session, documentNode, documentNode, "SET_DOCUMENT_CONTENT");

			// Activity log
			UserActivity.log(session.getUserID(), "SET_DOCUMENT_CONTENT", documentNode.getUUID(), size+", "+docPath);
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
			org.apache.commons.io.FileUtils.deleteQuietly(tmpJcr);
			org.apache.commons.io.FileUtils.deleteQuietly(tmpAvr);
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("setContent: void");
	}
	
	@Override
	public List<Document> getChilds(String token, String fldPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getChilds({}, {})", token, fldPath);
		List<Document> childs = new ArrayList<Document>();
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

				if (child.isNodeType(Document.TYPE)) {
					childs.add(BaseDocumentModule.getProperties(session, child));
				}
			}

			// Activity log
			UserActivity.log(session.getUserID(), "GET_CHILD_DOCUMENTS", folderNode.getUUID(), fldPath);
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
	public Document rename(String token, String docPath, String newName) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("rename:({}, {}, {})", new Object[] { token, docPath, newName });
		Document renamedDocument = null;
		Session session = null;
		Node documentNode = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			String parent = FileUtils.getParent(docPath);
			String name = FileUtils.getName(docPath);
			
			// Escape dangerous chars in name
			newName = FileUtils.escape(newName);

			if (newName != null && !newName.equals("") && !newName.equals(name)) {
				String newPath = parent + "/" + newName;
				session.move(docPath, newPath);

				// Set new name
				documentNode = session.getRootNode().getNode(newPath.substring(1));
				documentNode.setProperty(Document.NAME, newName);

				// Publish changes
				session.save();

				// Set returned document properties
				renamedDocument = BaseDocumentModule.getProperties(session, documentNode);
			} else {
				// Don't change anything
				documentNode = session.getRootNode().getNode(docPath.substring(1));
				renamedDocument = BaseDocumentModule.getProperties(session, documentNode);				
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "RENAME_DOCUMENT", documentNode.getUUID(), newName+", "+docPath);
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

		log.debug("rename: {}", renamedDocument);
		return renamedDocument;
	}

	@Override
	public void setProperties(String token, Document doc) throws VersionException, LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("setProperties({}, {})", token, doc);
		Node documentNode = null;
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
			
			documentNode = session.getRootNode().getNode(doc.getPath().substring(1));
			
			// Update document keyword cache
			//UserKeywordsManager.put(session.getUserID(), documentNode.getUUID(), doc.getKeywords());
			
			// Update document title
			// documentNode.setProperty(Document.TITLE, doc.getTitle() == null ? "" : doc.getTitle());

			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "SET_PROPERTIES", null);

			// Check scripting
			BaseScriptingModule.checkScripts(session, documentNode, documentNode, "SET_DOCUMENT_PROPERTIES");

			// Activity log
			UserActivity.log(session.getUserID(), "SET_DOCUMENT_PROPERTIES", documentNode.getUUID(), doc.getPath());
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
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("setProperties: void");
	}

	@Override
	public void checkout(String token, String docPath) throws AccessDeniedException, RepositoryException, 
			PathNotFoundException, LockException, DatabaseException {
		log.debug("checkout({}, {})", token, docPath);
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

			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = documentNode.getNode(Document.CONTENT);
			lck = documentNode.lock(true, false);
			JCRUtils.addLockToken(session, documentNode);
			contentNode.checkout();

			t.end();
			t.commit();

			// Check scripting
			BaseScriptingModule.checkScripts(session, documentNode, documentNode, "CHECKOUT_DOCUMENT");

			// Activity log
			UserActivity.log(session.getUserID(), "CHECKOUT_DOCUMENT", documentNode.getUUID(), lck.getLockToken()+", "+docPath);
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
	public void cancelCheckout(String token, String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, LockException, DatabaseException {
		log.debug("cancelCheckout({}, {})", token, docPath);
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
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = documentNode.getNode(Document.CONTENT);
			contentNode.restore(contentNode.getBaseVersion(), true);
			documentNode.unlock();
			JCRUtils.removeLockToken(session, documentNode);
			
			t.end();
			t.commit();

			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "CANCEL_CHECKOUT", null);

			// Check scripting
			BaseScriptingModule.checkScripts(session, documentNode, documentNode, "CANCEL_CHECKOUT_DOCUMENT");

			// Activity log
			UserActivity.log(session.getUserID(), "CANCEL_CHECKOUT_DOCUMENT", documentNode.getUUID(), docPath);
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
	public void forceCancelCheckout(String token, String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, LockException, DatabaseException {
		log.debug("forceCancelCheckout({}, {})", token, docPath);
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
			
			if (!session.getUserID().equals(Config.ADMIN_USER)) {
				throw new AccessDeniedException("Only administrator use allowed");
			}
			
			t = new Transaction(session);
			t.start();
			
			JCRUtils.loadLockTokens(session);
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = documentNode.getNode(Document.CONTENT);
			javax.jcr.lock.Lock lock = documentNode.getLock();
			
			if (lock.getLockOwner().equals(session.getUserID())) {
				contentNode.restore(contentNode.getBaseVersion(), true);
				documentNode.unlock();
				JCRUtils.removeLockToken(session, documentNode);
			} else {
				String lt = JCRUtils.getLockToken(documentNode.getUUID());
				session.addLockToken(lt);
				contentNode.restore(contentNode.getBaseVersion(), true);
				documentNode.unlock();
				LockTokenDAO.remove(lock.getLockOwner(), lt);
			}
			
			t.end();
			t.commit();

			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "FORCE_CANCEL_CHECKOUT", null);

			// Check scripting
			BaseScriptingModule.checkScripts(session, documentNode, documentNode, "FORCE_CANCEL_CHECKOUT_DOCUMENT");

			// Activity log
			UserActivity.log(session.getUserID(), "FORCE_CANCEL_CHECKOUT_DOCUMENT", documentNode.getUUID(), docPath);
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

		log.debug("forceCancelCheckout: void");
	}

	@Override
	public boolean isCheckedOut(String token, String docPath) throws RepositoryException,
			PathNotFoundException, DatabaseException {
		log.debug("isCheckedOut({}, {})", token, docPath);
		boolean checkedOut = false;
		Session session = null;

		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = documentNode.getNode(Document.CONTENT);
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
	public Version checkin(String token, String docPath, String comment) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, LockException, VersionException, DatabaseException {
		log.debug("checkin({}, {}, {})", new Object[] { token, docPath, comment });
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
			
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			Node contentNode = null;
			
			synchronized (documentNode) {
				JCRUtils.loadLockTokens(session);
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
				JCRUtils.removeLockToken(session, documentNode);
			}
			
			t.end();
			t.commit();
			
			
			if (Config.USER_ITEM_CACHE) {
				// Update user items
				long size = contentNode.getProperty(Document.SIZE).getLong();
				UserItemsManager.incSize(session.getUserID(), size);
			}
			
			// Remove pdf & preview from cache
			new File(Config.CACHE_DXF + File.separator + documentNode.getUUID() + ".dxf").delete();
			new File(Config.CACHE_PDF + File.separator + documentNode.getUUID() + ".pdf").delete();
			new File(Config.CACHE_SWF + File.separator + documentNode.getUUID() + ".swf").delete();

			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "CHECKIN", comment);

			// Check scripting
			BaseScriptingModule.checkScripts(session, documentNode, documentNode, "CHECKIN_DOCUMENT");

			// Activity log
			UserActivity.log(session.getUserID(), "CHECKIN_DOCUMENT", documentNode.getUUID(), comment+", "+docPath);
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

		log.debug("checkin: "+version);
		return version;
	}

	@Override
	public List<Version> getVersionHistory(String token, String docPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getVersionHistory({}, {})", token, docPath);
		List<Version> history = new ArrayList<Version>();
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
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
			UserActivity.log(session.getUserID(), "GET_DOCUMENT_VERSION_HISTORY", documentNode.getUUID(), docPath);
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
	public void lock(String token, String docPath) throws AccessDeniedException, RepositoryException, 
			PathNotFoundException, LockException, DatabaseException {
		log.debug("lock({})", docPath);
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
			
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			javax.jcr.lock.Lock lck = documentNode.lock(true, false);
			JCRUtils.addLockToken(session, documentNode);
			
			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "LOCK", null);

			// Check scripting
			BaseScriptingModule.checkScripts(session, documentNode, documentNode, "LOCK_DOCUMENT");

			// Activity log
			UserActivity.log(session.getUserID(), "LOCK_DOCUMENT", documentNode.getUUID(), lck.getLockToken()+", "+docPath);
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
	public void unlock(String token, String docPath) throws AccessDeniedException, RepositoryException, 
			PathNotFoundException, LockException, DatabaseException {
		log.debug("unlock({}, {})", token, docPath);
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
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			documentNode.unlock();
			JCRUtils.removeLockToken(session, documentNode);

			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "UNLOCK", null);
			
			// Check scripting
			BaseScriptingModule.checkScripts(session, documentNode, documentNode, "UNLOCK_DOCUMENT");

			// Activity log
			UserActivity.log(session.getUserID(), "UNLOCK_DOCUMENT", documentNode.getUUID(), docPath);
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
	public void forceUnlock(String token, String docPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("forceUnlock({}, {})", token, docPath);
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
			
			if (!session.getUserID().equals(Config.ADMIN_USER)) {
				throw new AccessDeniedException("Only administrator use allowed");
			}
			
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			javax.jcr.lock.Lock lock = documentNode.getLock();
			
			if (lock.getLockOwner().equals(session.getUserID())) {
				JCRUtils.loadLockTokens(session);
				documentNode.unlock();
				JCRUtils.removeLockToken(session, documentNode);
			} else {
				String lt = JCRUtils.getLockToken(documentNode.getUUID());
				session.addLockToken(lt);
				documentNode.unlock();
				LockTokenDAO.remove(lock.getLockOwner(), lt);
			}

			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "FORCE_UNLOCK", null);
			
			// Check scripting
			BaseScriptingModule.checkScripts(session, documentNode, documentNode, "FORCE_UNLOCK_DOCUMENT");

			// Activity log
			UserActivity.log(session.getUserID(), "FORCE_UNLOCK_DOCUMENT", documentNode.getUUID(), docPath);
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
		
		log.debug("forceUnlock: void");
	}

	@Override
	public boolean isLocked(String token, String docPath) throws RepositoryException, PathNotFoundException, 
			DatabaseException {
		log.debug("isLocked({}, {})", token, docPath);
		boolean locked;
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			locked = documentNode.isLocked();
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
	public Lock getLock(String token, String docPath) throws RepositoryException, PathNotFoundException,
			LockException, DatabaseException {
		log.debug("getLock({}, {})", token, docPath);
		Lock lock = new Lock();
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			lock = BaseDocumentModule.getLock(session, docPath);
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
	public void purge(String token, String docPath) throws AccessDeniedException, RepositoryException, 
			PathNotFoundException, DatabaseException {
		log.debug("purge({}, {})", token, docPath);
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
			
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			HashMap<String, UserItems> userItemsHash = null;
			
			synchronized (documentNode) {
				parentNode = documentNode.getParent();
				userItemsHash = purgeHelper(session, parentNode, documentNode);
			}
			
			// Update user items
			if (Config.USER_ITEM_CACHE) {
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
			BaseScriptingModule.checkScripts(session, parentNode, documentNode, "PURGE_DOCUMENT");
			
			// Activity log
			UserActivity.log(session.getUserID(), "PURGE_DOCUMENT", documentNode.getUUID(), docPath);
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
	 * Remove version history, compute free space and remove obsolete files from
	 * PDF and previsualization cache.
	 */
	public HashMap<String, UserItems> purgeHelper(Session session, Node parentNode, Node docNode) 
			throws javax.jcr.PathNotFoundException, javax.jcr.RepositoryException {
		Node contentNode = docNode.getNode(Document.CONTENT);
		long size = contentNode.getProperty(Document.SIZE).getLong();
		String author = contentNode.getProperty(Document.AUTHOR).getString();
		VersionHistory vh = contentNode.getVersionHistory();
		HashMap<String, UserItems> userItemsHash = new HashMap<String, UserItems>();
		log.debug("VersionHistory UUID: {}", vh.getUUID());

		// Remove pdf & preview from cache
		new File(Config.CACHE_DXF + File.separator + docNode.getUUID() + ".dxf").delete();
		new File(Config.CACHE_PDF + File.separator + docNode.getUUID() + ".pdf").delete();
		new File(Config.CACHE_SWF + File.separator + docNode.getUUID() + ".swf").delete();
		
		// Remove node itself
		docNode.remove();
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
				Node frozenNode = ver.getNode(JcrConstants.JCR_FROZENNODE);
				size = frozenNode.getProperty(Document.SIZE).getLong();
				author = frozenNode.getProperty(Document.AUTHOR).getString();
				log.debug("vh.removeVersion({})", versionName);
				vh.removeVersion(versionName);
				
				if (Config.USER_ITEM_CACHE) {
					// Update local user items for versions
					UserItems userItems = userItemsHash.get(author);
					if (userItems == null) userItems = new UserItems();
					userItems.setSize(userItems.getSize() + size);
					userItems.setDocuments(userItems.getDocuments() + 1);
					userItemsHash.put(author, userItems);
				}
			}
		}
		
		if (Config.USER_ITEM_CACHE) {
			// Update local user items for working version
			UserItems userItems = userItemsHash.get(author);
			if (userItems == null) userItems = new UserItems();
			userItems.setSize(userItems.getSize() + size);
			userItems.setDocuments(userItems.getDocuments() + 1);
			userItemsHash.put(author, userItems);
		}
		
		return userItemsHash;
	}

	@Override
	public void move(String token, String docPath, String dstPath) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("move({}, {}, {})", new Object[] { token, docPath, dstPath });
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
			
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			String name = FileUtils.getName(docPath);
			session.move(docPath, dstPath+"/"+name);
			session.save();

			// Activity log
			UserActivity.log(session.getUserID(), "MOVE_DOCUMENT", documentNode.getUUID(), dstPath+", "+docPath);
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
	public void copy(String token, String docPath, String dstPath) throws ItemExistsException,
			PathNotFoundException, AccessDeniedException, RepositoryException, IOException, DatabaseException, 
			UserQuotaExceededException {
		log.debug("copy({}, {}, {})", new Object[]  { token, docPath, dstPath });
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
			
			Node srcDocumentNode = session.getRootNode().getNode(docPath.substring(1));
			dstFolderNode = session.getRootNode().getNode(dstPath.substring(1));
			copy(session, srcDocumentNode, dstFolderNode);

			// Check subscriptions
			BaseNotificationModule.checkSubscriptions(dstFolderNode, session.getUserID(), "COPY", null);

			// Activity log
			UserActivity.log(session.getUserID(), "COPY_DOCUMENT", srcDocumentNode.getUUID(), dstPath+", "+docPath);
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
	
	/**
	 * Is invoked from DirectDocumentNode and DirectFolderNode.
	 */
	public void copy(Session session, Node srcDocumentNode, Node dstFolderNode) throws ValueFormatException, 
			javax.jcr.PathNotFoundException, javax.jcr.RepositoryException, IOException, DatabaseException,
			UserQuotaExceededException {
		log.debug("copy({}, {}, {})", new Object[] { session, srcDocumentNode, dstFolderNode });
		
		Node srcDocumentContentNode = srcDocumentNode.getNode(Document.CONTENT);
		String mimeType = srcDocumentContentNode.getProperty("jcr:mimeType").getString();
		// String title = srcDocumentContentNode.getProperty(Document.TITLE).getString();
		InputStream is = srcDocumentContentNode.getProperty("jcr:data").getStream();
		BaseDocumentModule.create(session, dstFolderNode, srcDocumentNode.getName(), null /* title */,
				mimeType, new String[]{}, is);
		is.close();
		
		log.debug("copy: void");
	}

	@Override
	public void restoreVersion(String token, String docPath, String versionId) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("restoreVersion({}, {}, {})", new Object[] { token, docPath, versionId });
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
			
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));

			synchronized (documentNode) {
				contentNode = documentNode.getNode(Document.CONTENT);
				contentNode.restore(versionId, true);
				contentNode.save();
			}

			// Activity log
			UserActivity.log(session.getUserID(), "RESTORE_VERSION", documentNode.getUUID(), versionId+", "+docPath);
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
	public void purgeVersionHistory(String token, String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("purgeVersionHistory({}, {})", token, docPath);
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
			
			Node documentNode = session.getRootNode().getNode(docPath.substring(1));
			
			synchronized (documentNode) {
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
			}

			// Activity log
			UserActivity.log(session.getUserID(), "PURGE_VERSION_HISTORY", documentNode.getUUID(), docPath);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}
		
		log.debug("purgeVersionHistory: void");
	}

	@Override
	public long getVersionHistorySize(String token, String docPath) throws RepositoryException,
			PathNotFoundException, DatabaseException {
		log.debug("getVersionHistorySize({}, {})", token, docPath);
		long ret = 0;
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
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
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("getVersionHistorySize: {}", ret);
		return ret;
	}

	@Override
	public boolean isValid(String token, String docPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("isValid({}, {})", token, docPath);
		boolean valid = false;
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
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

			if (node.isNodeType(Document.TYPE)) {
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
