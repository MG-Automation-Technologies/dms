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

package com.openkm.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Document;
import com.openkm.bean.Lock;
import com.openkm.bean.Version;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.FileSizeExceededException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UnsupportedMimeTypeException;
import com.openkm.core.VersionException;
import com.openkm.core.VirusDetectedException;
import com.openkm.module.DocumentModule;
import com.openkm.module.ModuleManager;

/**
 * @author pavila
 *
 */
public class OKMDocument implements DocumentModule {
	private static Logger log = LoggerFactory.getLogger(OKMDocument.class);
	private static OKMDocument instance = new OKMDocument();

	private OKMDocument() {}
	
	public static OKMDocument getInstance() {
		return instance;
	}
	
	@Override
	public Document create(String token, Document doc, InputStream is) throws UnsupportedMimeTypeException, 
			FileSizeExceededException, VirusDetectedException, ItemExistsException,	PathNotFoundException,
			AccessDeniedException, RepositoryException, IOException, DatabaseException {
		log.debug("create({}, {})", doc, is);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Document newDocument = dm.create(token, doc, is);
		log.debug("create: {}", newDocument);
		return newDocument;
	}
	
	@Override
	public void delete(String token, String docPath) throws LockException, PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("delete({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.delete(token, docPath);
		log.debug("delete: void");
	}
	
	@Override
	public Document getProperties(String token, String docPath) throws RepositoryException, 
			PathNotFoundException, DatabaseException {
		log.debug("getProperties({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Document doc = dm.getProperties(token, docPath);
		log.debug("getProperties: {}", doc);
		return doc;
	}

	@Override
	public InputStream getContent(String token, String docPath, boolean checkout) throws PathNotFoundException, 
			RepositoryException, IOException, DatabaseException {
		log.debug("getContent({}, {})", docPath, checkout);
		DocumentModule dm = ModuleManager.getDocumentModule();
		InputStream is = dm.getContent(token, docPath, checkout);
		log.debug("getContent: {}", is);
		return is;
	}
	
	@Override
	public InputStream getContentByVersion(String token, String docPath, String versionId) throws 
			RepositoryException, PathNotFoundException, IOException, DatabaseException {
		log.debug("getContentByVersion({}, {})", docPath, versionId);
		DocumentModule dm = ModuleManager.getDocumentModule();
		InputStream is = dm.getContentByVersion(token, docPath, versionId);
		log.debug("getContentByVersion: {}", is);
		return is;
	}

	@Override
	public void addNote(String token, String docPath, String text) throws LockException, 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("addNote({}, {})", docPath, text);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.addNote(token, docPath, text);
		log.debug("addNote: void");
	}
	
	@Override
	public List<Document> getChilds(String token, String fldPath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("getChilds({})", fldPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		List<Document> col = dm.getChilds(token, fldPath);
		log.debug("getChilds: {}", col);
		return col;
	}

	@Override
	public Document rename(String token, String docPath, String newName) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("rename({}, {})", docPath, newName);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Document renamedDocument = dm.rename(token, docPath, newName);
		log.debug("rename: {}", renamedDocument);
		return renamedDocument;
	}

	@Override
	public void setProperties(String token, Document doc) throws LockException, VersionException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("setProperties({})", doc);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.setProperties(token, doc);
		log.debug("setProperties: void");
	}

	@Override
	public void checkout(String token, String docPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("checkout({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.checkout(token, docPath);
		log.debug("checkout: void");
	}

	@Override
	public void cancelCheckout(String token, String docPath) throws LockException, 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("cancelCheckout({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.cancelCheckout(token, docPath);
		log.debug("cancelCheckout: void");
	}
	
	@Override
	public boolean isCheckedOut(String token, String docPath) throws PathNotFoundException, 
			RepositoryException, DatabaseException {
		log.debug("isCheckedOut({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		boolean checkedOut = dm.isCheckedOut(token, docPath);
		log.debug("isCheckedOut: {}", checkedOut);
		return checkedOut;
	}
	
	@Override
	public Version checkin(String token, String docPath, String comment) throws LockException, 
			VersionException, PathNotFoundException, AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("checkin({}, {})", docPath, comment);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Version version = dm.checkin(token, docPath, comment);
		log.debug("checkin: {}", version);
		return version;
	}

	@Override
	public List<Version> getVersionHistory(String token, String docPath) throws PathNotFoundException, 
			RepositoryException, DatabaseException {
		log.debug("getVersionHistory({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		List<Version> history = dm.getVersionHistory(token, docPath);
		log.debug("getVersionHistory: {}", history);
		return history;
	}

	@Override
	public void setContent(String token, String docPath, InputStream is) throws 
			FileSizeExceededException, VirusDetectedException, VersionException,
			LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, IOException, DatabaseException {
		log.debug("setContent({}, {})", docPath, is);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.setContent(token, docPath, is);
		log.debug("setContent: void");
	}

	@Override
	public void lock(String token, String docPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("lock({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.lock(token, docPath);
		log.debug("lock: void");
	}

	@Override
	public void unlock(String token, String docPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("unlock({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.unlock(token, docPath);
		log.debug("unlock: void");
	}

	@Override
	public boolean isLocked(String token, String docPath) throws PathNotFoundException, 
			RepositoryException, DatabaseException {
		log.debug("isLocked({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		boolean locked = dm.isLocked(token, docPath);
		log.debug("isLocked: {}", locked);
		return locked;
	}

	@Override
	public Lock getLock(String token, String docPath) throws LockException, 
			PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getLock({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Lock lock = dm.getLock(token, docPath);
		log.debug("getLock: {}", lock);
		return lock;
	}

	@Override
	public void purge(String token, String docPath) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("purge({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.purge(token, docPath);
		log.debug("purge: void");
	}

	@Override
	public void move(String token, String docPath, String destPath) throws PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("move({}, {})", docPath, destPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.move(token, docPath, destPath);
		log.debug("move: void");
	}

	@Override
	public void copy(String token, String docPath, String destPath) throws 
			ItemExistsException, PathNotFoundException, AccessDeniedException,
			RepositoryException, IOException, DatabaseException {
		log.debug("copy({}, {})", docPath, destPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.copy(token, docPath, destPath);
		log.debug("copy: void");
	}
	
	@Override
	public void restoreVersion(String token, String docPath, String versionId) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("restoreVersion({}, {})", docPath, versionId);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.restoreVersion(token, docPath, versionId);
		log.debug("restoreVersion: void");
	}

	@Override
	public void purgeVersionHistory(String token, String docPath) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("purgeVersionHistory({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.purgeVersionHistory(token, docPath);
		log.debug("purgeVersionHistory: void");
	}

	@Override
	public long getVersionHistorySize(String token, String docPath) throws PathNotFoundException, 
			RepositoryException, DatabaseException {
		log.debug("getVersionHistorySize({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		long size = dm.getVersionHistorySize(token, docPath);
		log.debug("getVersionHistorySize: {}", size);
		return size;
	}

	@Override
	public boolean isValid(String token, String docPath) throws PathNotFoundException, AccessDeniedException, 
			RepositoryException, DatabaseException {
		log.debug("isValid({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		boolean valid = dm.isValid(token, docPath);
		log.debug("isValid: {}", valid);
		return valid;
	}

	@Override
	public String getPath(String token, String uuid) throws AccessDeniedException, RepositoryException, 
			DatabaseException {
		log.debug("getPath({})", uuid);
		DocumentModule dm = ModuleManager.getDocumentModule();
		String path = dm.getPath(token, uuid);
		log.debug("getPath: {}", path);
		return path;
	}
}
