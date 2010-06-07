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
import java.util.Collection;

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
			UnsupportedMimeTypeException, FileSizeExceededException, VirusDetectedException,
			ItemExistsException, PathNotFoundException, AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("create({}, {})", token, doc);
		DocumentModule dm = ModuleManager.getDocumentModule();
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		Document newDocument = dm.create(token, doc, bais);
		bais.close();
		log.debug("create: {}", newDocument);
		return newDocument;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#delete(java.lang.String, java.lang.String)
	 */
	public void delete(String token, String docPath) throws AccessDeniedException, 
			RepositoryException, PathNotFoundException, LockException, DatabaseException {
		log.debug("delete({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.delete(token, docPath);
		log.debug("delete: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getProperties(java.lang.String, java.lang.String)
	 */
	public Document getProperties(String token, String docPath) throws RepositoryException, 
			PathNotFoundException, DatabaseException {
		log.debug("getProperties({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Document doc = dm.getProperties(token, docPath);
		log.debug("getProperties: {}", doc);
		return doc;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getContent(java.lang.String, java.lang.String, boolean)
	 */
	public byte[] getContent(String token, String docPath, boolean checkout) throws RepositoryException, 
			IOException, PathNotFoundException, DatabaseException {
		log.debug("getContent({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		InputStream is = dm.getContent(token, docPath, checkout);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(is, baos);
		byte[] data = baos.toByteArray();
		log.debug("getContent: {}", data);
		return data;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getContentByVersion(java.lang.String, java.lang.String, java.lang.String)
	 */
	public byte[] getContentByVersion(String token, String docPath, String versionId) 
			throws RepositoryException, IOException, PathNotFoundException, DatabaseException {
		log.debug("getContentByVersion({}, {}, {})", new Object[] { token, docPath, versionId });
		DocumentModule dm = ModuleManager.getDocumentModule();
		InputStream is = dm.getContentByVersion(token, docPath, versionId); 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(is, baos);
		byte[] data = baos.toByteArray();
		log.debug("getContentByVersion: {}", data);
		return data;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getChilds(java.lang.String, java.lang.String)
	 */
	public DocumentArray getChilds(String token, String fldId) throws RepositoryException,
			PathNotFoundException, DatabaseException {
		log.debug("getChilds({}, {})", token, fldId);
		DocumentModule dm = ModuleManager.getDocumentModule();
		DocumentArray da = new DocumentArray();
		Collection<Document> col = dm.getChilds(token, fldId);
		da.setValue((Document[]) col.toArray(new Document[col.size()]));
		log.debug("getChilds: {}", da);
		return da;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Document rename(String token, String docPath, String newName) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("rename({}, {}, {})", new Object[] { token, docPath, newName });
		DocumentModule dm = ModuleManager.getDocumentModule();
		Document renamedDocument = dm.rename(token, docPath, newName);
		log.debug("rename: {}", renamedDocument);
		return renamedDocument;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#setProperties(java.lang.String, com.openkm.bean.Document)
	 */
	public void setProperties(String token, Document doc) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, VersionException, LockException, DatabaseException {
		log.debug("setProperties({}, {})", token, doc);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.setProperties(token, doc);
		log.debug("setProperties: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#checkout(java.lang.String, java.lang.String)
	 */
	public void checkout(String token, String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, LockException, DatabaseException {
		log.debug("checkout({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.checkout(token, docPath);
		log.debug("checkout: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#checkout(java.lang.String, java.lang.String)
	 */
	public void cancelCheckout(String token, String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, LockException, DatabaseException {
		log.debug("cancelCheckout({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.cancelCheckout(token, docPath);
		log.debug("cancelCheckout: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#checkin(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Version checkin(String token, String docPath, String comment) throws LockException, 
			VersionException, PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("checkin({}, {}, {})", new Object[] { token, docPath, comment });
		DocumentModule dm = ModuleManager.getDocumentModule();
		Version version = dm.checkin(token, docPath, comment);
		log.debug("checkin: {}", version);
		return version;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getVersionHistory(java.lang.String, java.lang.String)
	 */
	public VersionArray getVersionHistory(String token, String docPath) throws 
			PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getVersionHistory({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		VersionArray va = new VersionArray();
		Collection<Version> col = dm.getVersionHistory(token, docPath);
		va.setValue((Version[]) col.toArray(new Version[col.size()]));
		log.debug("getVersionHistory: {}", va);
		return va;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#setContent(java.lang.String, byte[])
	 */
	public void setContent(String token, String docPath, byte[] content) throws
			FileSizeExceededException, VirusDetectedException, VersionException,
			LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, IOException, DatabaseException {
		log.debug("setContent({}, {}, {})", new Object[] { token, docPath, content });
		DocumentModule dm = ModuleManager.getDocumentModule();
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		dm.setContent(token, docPath, bais);
		bais.close();
		log.debug("setContent: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#lock(java.lang.String, java.lang.String)
	 */
	public void lock(String token, String docPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("lock({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.lock(token, docPath);
		log.debug("lock: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#unlock(java.lang.String, java.lang.String)
	 */
	public void unlock(String token, String docPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("unlock({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.unlock(token, docPath);
		log.debug("unlock: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#purge(java.lang.String, java.lang.String)
	 */
	public void purge(String token, String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("purge({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.purge(token, docPath);
		log.debug("purge: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#move(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void move(String token, String docPath, String fldPath) throws LockException, 
			PathNotFoundException, ItemExistsException, AccessDeniedException, 
			RepositoryException, DatabaseException {
		log.debug("move({}, {}, {})", new Object[] { token, docPath, fldPath });
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.move(token, docPath, fldPath);
		log.debug("move: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#restoreVersion(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void restoreVersion(String token, String docPath, String versionId)
			throws AccessDeniedException, RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("restoreVersion({}, {}, {})", new Object[] { token, docPath, versionId });
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.restoreVersion(token, docPath, versionId);
		log.debug("restoreVersion: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#purgeVersionHistory(java.lang.String, java.lang.String)
	 */
	public void purgeVersionHistory(String token, String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("purgeVersionHistory({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.purgeVersionHistory(token, docPath);
		log.debug("purgeVersionHistory: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getVersionSize(java.lang.String, java.lang.String)
	 */
	public long getVersionHistorySize(String token, String docPath) throws RepositoryException,
			PathNotFoundException, DatabaseException {
		log.debug("getVersionHistorySize({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		long size = dm.getVersionHistorySize(token, docPath);
		log.debug("getVersionHistorySize: {}", size);
		return size;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#isValid(java.lang.String, java.lang.String)
	 */
	public boolean isValid(String token, String docPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("isValid({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		boolean valid = dm.isValid(token, docPath);
		log.debug("isValid: {}", valid);
		return valid;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DocumentModule#getPath(java.lang.String, java.lang.String)
	 */
	public String getPath(String token, String uuid) throws AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("getPath({}, {})", token, uuid);
		DocumentModule dm = ModuleManager.getDocumentModule();
		String path = dm.getPath(token, uuid);
		log.debug("getPath: {}", path);
		return path;
	}
}
