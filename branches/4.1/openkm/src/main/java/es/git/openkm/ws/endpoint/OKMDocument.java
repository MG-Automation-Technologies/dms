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

package es.git.openkm.ws.endpoint;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Document;
import es.git.openkm.bean.Version;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.FileSizeExceededException;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.UnsupportedMimeTypeException;
import es.git.openkm.core.VersionException;
import es.git.openkm.core.VirusDetectedException;
import es.git.openkm.module.DocumentModule;
import es.git.openkm.module.ModuleManager;
import es.git.openkm.ws.util.DocumentArray;
import es.git.openkm.ws.util.VersionArray;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMDocument" 
 * @web.servlet-mapping url-pattern="/OKMDocument"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class OKMDocument {
	private static Logger log = LoggerFactory.getLogger(OKMDocument.class);

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#create(java.lang.String, es.git.openkm.bean.Document, byte[])
	 */
	public Document create(String token, Document doc, byte[] content) throws IOException,
			UnsupportedMimeTypeException, FileSizeExceededException, VirusDetectedException,
			ItemExistsException, PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("create(" + token + ", " + doc + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		Document newDocument = dm.create(token, doc, bais);
		bais.close();
		log.debug("create: "+newDocument);
		return newDocument;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#delete(java.lang.String, java.lang.String)
	 */
	public void delete(String token, String docId) throws AccessDeniedException, 
			RepositoryException, PathNotFoundException, LockException {
		log.debug("delete(" + token + ", " + docId + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.delete(token, docId);
		log.debug("delete: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getProperties(java.lang.String, java.lang.String)
	 */
	public Document getProperties(String token, String docPath) throws RepositoryException, 
			PathNotFoundException {
		log.debug("getProperties(" + token + ", " + docPath + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		Document doc = dm.getProperties(token, docPath);
		log.debug("getProperties: " + doc);
		return doc;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getContent(java.lang.String, java.lang.String, boolean)
	 */
	public byte[] getContent(String token, String docPath, boolean checkout) throws RepositoryException, 
			IOException, PathNotFoundException {
		log.debug("getContent(" + token + ", " + docPath + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		InputStream is = dm.getContent(token, docPath, checkout);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(is, baos);
		byte[] data = baos.toByteArray();
		log.debug("getContent: " + data);
		return data;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getContentByVersion(java.lang.String, java.lang.String, java.lang.String)
	 */
	public byte[] getContentByVersion(String token, String docPath, String versionId) 
			throws RepositoryException, IOException, PathNotFoundException {
		log.debug("getContentByVersion(" + token + ", " + docPath + ", " + versionId + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		InputStream is = dm.getContentByVersion(token, docPath, versionId); 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(is, baos);
		byte[] data = baos.toByteArray();
		log.debug("getContentByVersion: " + data);
		return data;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getChilds(java.lang.String, java.lang.String)
	 */
	public DocumentArray getChilds(String token, String fldId) throws RepositoryException,
			PathNotFoundException {
		log.debug("getChilds(" + token + ", " + fldId + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		DocumentArray da = new DocumentArray();
		da.setValue((Document[]) dm.getChilds(token, fldId).toArray(new Document[0]));
		log.debug("getChilds: " + da);
		return da;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Document rename(String token, String docPath, String newName) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, ItemExistsException {
		log.debug("rename(" + token + ", " + docPath + ", " + newName + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		Document renamedDocument = dm.rename(token, docPath, newName);
		log.debug("rename: "+renamedDocument);
		return renamedDocument;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#setProperties(java.lang.String, es.git.openkm.bean.Document)
	 */
	public void setProperties(String token, Document doc) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, VersionException, LockException {
		log.debug("setProperties(" + token + ", " + doc + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.setProperties(token, doc);
		log.debug("setProperties: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#checkout(java.lang.String, java.lang.String)
	 */
	public void checkout(String token, String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, LockException {
		log.debug("checkout(" + token + ", " + docPath + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.checkout(token, docPath);
		log.debug("checkout: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#checkout(java.lang.String, java.lang.String)
	 */
	public void cancelCheckout(String token, String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, LockException {
		log.debug("cancelCheckout(" + token + ", " + docPath + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.cancelCheckout(token, docPath);
		log.debug("cancelCheckout: void");		
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#checkin(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Version checkin(String token, String docPath, String comment) throws LockException, 
			VersionException, PathNotFoundException, AccessDeniedException,
			RepositoryException {
		log.debug("checkin(" + token + ", " + docPath + ", " + comment + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		Version version = dm.checkin(token, docPath, comment);
		log.debug("checkin: "+version);
		return version;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getVersionHistory(java.lang.String, java.lang.String)
	 */
	public VersionArray getVersionHistory(String token, String docPath) throws 
			PathNotFoundException, RepositoryException {
		log.debug("getVersionHistory(" + token + ", " + docPath + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		VersionArray va = new VersionArray();
		va.setValue((Version[]) dm.getVersionHistory(token, docPath).toArray(new Version[0]));
		log.debug("getVersionHistory: " + va);
		return va;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#setContent(java.lang.String, byte[])
	 */
	public void setContent(String token, String docPath, byte[] content) throws
			FileSizeExceededException, VirusDetectedException, VersionException,
			LockException, PathNotFoundException, AccessDeniedException,
			RepositoryException, IOException {
		log.debug("setContent(" + token + ", " + docPath + ", " + content + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		dm.setContent(token, docPath, bais);
		bais.close();
		log.debug("setContent: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#lock(java.lang.String, java.lang.String)
	 */
	public void lock(String token, String docPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("lock(" + token + ", " + docPath + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.lock(token, docPath);
		log.debug("lock: void");		
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#unlock(java.lang.String, java.lang.String)
	 */
	public void unlock(String token, String docPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("unlock(" + token + ", " + docPath + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.unlock(token, docPath);
		log.debug("unlock: void");		
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#purge(java.lang.String, java.lang.String)
	 */
	public void purge(String token, String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException {
		log.debug("purge(" + token + ", " + docPath + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.purge(token, docPath);
		log.debug("purge: void");	
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#move(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void move(String token, String docPath, String destPath) throws LockException, 
			PathNotFoundException, ItemExistsException, AccessDeniedException, 
			RepositoryException {
		log.debug("move(" + token + ", " + docPath + ", " + destPath + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.move(token, docPath, destPath);
		log.debug("move: void");	
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#restoreVersion(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void restoreVersion(String token, String docPath, String versionId)
			throws AccessDeniedException, RepositoryException, PathNotFoundException {
		log.debug("restoreVersion(" + token + ", " + docPath + ", " + versionId + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.restoreVersion(token, docPath, versionId);
		log.debug("restoreVersion: void");	
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#purgeVersionHistory(java.lang.String, java.lang.String)
	 */
	public void purgeVersionHistory(String token, String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException {
		log.debug("purgeVersionHistory(" + token + ", " + docPath + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.purgeVersionHistory(token, docPath);
		log.debug("purgeVersionHistory: void");	
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getVersionSize(java.lang.String, java.lang.String)
	 */
	public long getVersionHistorySize(String token, String docPath) throws RepositoryException,
			PathNotFoundException {
		log.debug("getVersionHistorySize(" + token + ", " + docPath + ")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		long size = dm.getVersionHistorySize(token, docPath);
		log.debug("getVersionHistorySize: "+size);
		return size;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#isValid(java.lang.String, java.lang.String)
	 */
	public boolean isValid(String token, String docPath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException {
		log.debug("isValid({}, {})", token, docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		boolean valid = dm.isValid(token, docPath);
		log.debug("isValid: {}", valid);
		return valid;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.DocumentModule#getPath(java.lang.String, java.lang.String)
	 */
	public String getPath(String token, String uuid) throws AccessDeniedException, RepositoryException {
		log.debug("getPath("+token+", "+uuid+")");
		DocumentModule dm = ModuleManager.getDocumentModule();
		String path = dm.getPath(token, uuid);
		log.debug("getPath: "+path);
		return path;
	}
}
