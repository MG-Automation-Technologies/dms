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

package com.openkm.ws.endpoint;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.io.IOUtils;
import org.jboss.annotation.security.SecurityDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Document;
import com.openkm.bean.Version;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.FileSizeExceededException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.UnsupportedMimeTypeException;
import com.openkm.core.UserQuotaExceededException;
import com.openkm.core.VersionException;
import com.openkm.core.VirusDetectedException;
import com.openkm.module.DocumentModule;
import com.openkm.module.ModuleManager;
import com.openkm.ws.util.DocumentArray;
import com.openkm.ws.util.VersionArray;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMDocument" 
 * @web.servlet-mapping url-pattern="/OKMDocument"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@SecurityDomain("OpenKM")
public class OKMDocument {
	private static Logger log = LoggerFactory.getLogger(OKMDocument.class);

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#create(java.lang.String, com.openkm.bean.Document, byte[])
	 */
	public Document create(String token, Document doc, byte[] content) throws IOException,
			UnsupportedMimeTypeException, FileSizeExceededException, UserQuotaExceededException,
			VirusDetectedException, ItemExistsException, PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("create({})", doc);
		DocumentModule dm = ModuleManager.getDocumentModule();
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		Document newDocument = dm.create(doc, bais);
		bais.close();
		log.debug("create: {}", newDocument);
		return newDocument;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#delete(java.lang.String, java.lang.String)
	 */
	public void delete(String docPath) throws AccessDeniedException, RepositoryException, 
			PathNotFoundException, LockException, DatabaseException {
		log.debug("delete({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.delete(docPath);
		log.debug("delete: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getProperties(java.lang.String, java.lang.String)
	 */
	public Document getProperties(String docPath) throws RepositoryException, PathNotFoundException, 
			DatabaseException {
		log.debug("getProperties({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Document doc = dm.getProperties(docPath);
		log.debug("getProperties: {}", doc);
		return doc;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getContent(java.lang.String, java.lang.String, boolean)
	 */
	public byte[] getContent(String docPath, boolean checkout) throws RepositoryException, IOException,
			PathNotFoundException, DatabaseException {
		log.debug("getContent({}, {})", docPath, checkout);
		DocumentModule dm = ModuleManager.getDocumentModule();
		InputStream is = dm.getContent(docPath, checkout);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(is, baos);
		byte[] data = baos.toByteArray();
		log.debug("getContent: {}", data);
		return data;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getContentByVersion(java.lang.String, java.lang.String, java.lang.String)
	 */
	public byte[] getContentByVersion(String docPath, String versionId) throws RepositoryException,
			IOException, PathNotFoundException, DatabaseException {
		log.debug("getContentByVersion({}, {})", docPath, versionId);
		DocumentModule dm = ModuleManager.getDocumentModule();
		InputStream is = dm.getContentByVersion(docPath, versionId); 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(is, baos);
		byte[] data = baos.toByteArray();
		log.debug("getContentByVersion: {}", data);
		return data;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getChilds(java.lang.String, java.lang.String)
	 */
	public DocumentArray getChilds(String fldId) throws RepositoryException, PathNotFoundException,
			DatabaseException {
		log.debug("getChilds({})", fldId);
		DocumentModule dm = ModuleManager.getDocumentModule();
		DocumentArray da = new DocumentArray();
		List<Document> col = dm.getChilds(fldId);
		da.setValue((Document[]) col.toArray(new Document[col.size()]));
		log.debug("getChilds: {}", da);
		return da;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Document rename(String docPath, String newName) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("rename({}, {})", docPath, newName);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Document renamedDocument = dm.rename(docPath, newName);
		log.debug("rename: {}", renamedDocument);
		return renamedDocument;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#setProperties(java.lang.String, com.openkm.bean.Document)
	 */
	public void setProperties(Document doc) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, VersionException, LockException, DatabaseException {
		log.debug("setProperties({})", doc);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.setProperties(doc);
		log.debug("setProperties: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#checkout(java.lang.String, java.lang.String)
	 */
	public void checkout(String docPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, LockException, DatabaseException {
		log.debug("checkout({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.checkout(docPath);
		log.debug("checkout: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#checkout(java.lang.String, java.lang.String)
	 */
	public void cancelCheckout(String docPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, LockException, DatabaseException {
		log.debug("cancelCheckout({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.cancelCheckout(docPath);
		log.debug("cancelCheckout: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#checkin(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Version checkin(String docPath, String comment) throws LockException, VersionException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("checkin({}, {})", docPath, comment);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Version version = dm.checkin(docPath, comment);
		log.debug("checkin: {}", version);
		return version;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getVersionHistory(java.lang.String, java.lang.String)
	 */
	public VersionArray getVersionHistory(String docPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		log.debug("getVersionHistory({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		VersionArray va = new VersionArray();
		List<Version> col = dm.getVersionHistory(docPath);
		va.setValue((Version[]) col.toArray(new Version[col.size()]));
		log.debug("getVersionHistory: {}", va);
		return va;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#setContent(java.lang.String, byte[])
	 */
	public void setContent(String docPath, byte[] content) throws FileSizeExceededException, 
			UserQuotaExceededException, VirusDetectedException, VersionException, LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, IOException, 
			DatabaseException {
		log.debug("setContent({}, {})", docPath, content);
		DocumentModule dm = ModuleManager.getDocumentModule();
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		dm.setContent(docPath, bais);
		bais.close();
		log.debug("setContent: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#lock(java.lang.String, java.lang.String)
	 */
	public void lock(String docPath) throws LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("lock({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.lock(docPath);
		log.debug("lock: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#unlock(java.lang.String, java.lang.String)
	 */
	public void unlock(String docPath) throws LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("unlock({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.unlock(docPath);
		log.debug("unlock: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#purge(java.lang.String, java.lang.String)
	 */
	public void purge(String docPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, DatabaseException {
		log.debug("purge({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.purge(docPath);
		log.debug("purge: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#move(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void move(String docPath, String fldPath) throws LockException, PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("move({}, {})", docPath, fldPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.move(docPath, fldPath);
		log.debug("move: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#restoreVersion(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void restoreVersion(String docPath, String versionId) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("restoreVersion({}, {})", docPath, versionId);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.restoreVersion(docPath, versionId);
		log.debug("restoreVersion: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#purgeVersionHistory(java.lang.String, java.lang.String)
	 */
	public void purgeVersionHistory(String docPath) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, DatabaseException {
		log.debug("purgeVersionHistory({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.purgeVersionHistory(docPath);
		log.debug("purgeVersionHistory: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getVersionSize(java.lang.String, java.lang.String)
	 */
	public long getVersionHistorySize(String docPath) throws RepositoryException, PathNotFoundException,
			DatabaseException {
		log.debug("getVersionHistorySize({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		long size = dm.getVersionHistorySize(docPath);
		log.debug("getVersionHistorySize: {}", size);
		return size;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#isValid(java.lang.String, java.lang.String)
	 */
	public boolean isValid(String docPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("isValid({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		boolean valid = dm.isValid(docPath);
		log.debug("isValid: {}", valid);
		return valid;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getPath(java.lang.String, java.lang.String)
	 */
	public String getPath(String uuid) throws AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("getPath({})", uuid);
		DocumentModule dm = ModuleManager.getDocumentModule();
		String path = dm.getPath(uuid);
		log.debug("getPath: {}", path);
		return path;
	}
}
