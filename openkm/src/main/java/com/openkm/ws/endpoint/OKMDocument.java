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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.io.IOUtils;
import org.jboss.annotation.security.SecurityDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Document;
import com.openkm.bean.Note;
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

	@WebMethod
	public Document create(@WebParam(name = "token") String token,
			@WebParam(name = "doc") Document doc,
			@WebParam(name = "content") byte[] content) throws IOException, UnsupportedMimeTypeException,
			FileSizeExceededException, UserQuotaExceededException, VirusDetectedException,
			ItemExistsException, PathNotFoundException, AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("create({})", doc);
		DocumentModule dm = ModuleManager.getDocumentModule();
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		Document newDocument = dm.create(token, doc, bais);
		bais.close();
		log.debug("create: {}", newDocument);
		return newDocument;
	}
	
	@WebMethod
	public Document createSimple(@WebParam(name = "token") String token,
			@WebParam(name = "docPath") String docPath,
			@WebParam(name = "content") byte[] content) throws IOException, UnsupportedMimeTypeException,
			FileSizeExceededException, UserQuotaExceededException, VirusDetectedException, 
			ItemExistsException, PathNotFoundException, AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("createSimple({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		Document doc = new Document();
		doc.setPath(docPath);
		Document newDocument = dm.create(token, doc, bais);
		bais.close();
		log.debug("createSimple: {}", newDocument);
		return newDocument;
	}

	@WebMethod
	public void delete(@WebParam(name = "docPath") String docPath) throws AccessDeniedException, 
			RepositoryException, PathNotFoundException, LockException, DatabaseException {
		log.debug("delete({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.delete(docPath);
		log.debug("delete: void");
	}
	
	@WebMethod
	public Document getProperties(@WebParam(name = "docPath") String docPath) throws RepositoryException,
			PathNotFoundException, DatabaseException {
		log.debug("getProperties({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Document doc = dm.getProperties(docPath);
		log.debug("getProperties: {}", doc);
		return doc;
	}

	@WebMethod
	public byte[] getContent(@WebParam(name = "docPath") String docPath,
			@WebParam(name = "checkout") boolean checkout) throws RepositoryException, IOException,
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

	@WebMethod
	public byte[] getContentByVersion(@WebParam(name = "docPath") String docPath,
			@WebParam(name = "versionId") String versionId) throws RepositoryException, IOException,
			PathNotFoundException, DatabaseException {
		log.debug("getContentByVersion({}, {})", docPath, versionId);
		DocumentModule dm = ModuleManager.getDocumentModule();
		InputStream is = dm.getContentByVersion(docPath, versionId); 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(is, baos);
		byte[] data = baos.toByteArray();
		log.debug("getContentByVersion: {}", data);
		return data;
	}

	@WebMethod
	public Document[] getChilds(@WebParam(name = "fldPath") String fldPath) throws RepositoryException, 
			PathNotFoundException, DatabaseException {
		log.debug("getChilds({})", fldPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		List<Document> col = dm.getChilds(fldPath);
		Document[] result = (Document[]) col.toArray(new Document[col.size()]);
		log.debug("getChilds: {}", result);
		return result;
	}
	
	@WebMethod
	public Document rename(@WebParam(name = "docPath") String docPath,
			@WebParam(name = "newName") String newName) throws AccessDeniedException, RepositoryException,
			PathNotFoundException, ItemExistsException, DatabaseException {
		log.debug("rename({}, {})", docPath, newName);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Document renamedDocument = dm.rename(docPath, newName);
		log.debug("rename: {}", renamedDocument);
		return renamedDocument;
	}
	
	@WebMethod
	public void setProperties(@WebParam(name = "doc") Document doc) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, VersionException, LockException, DatabaseException {
		log.debug("setProperties({})", doc);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.setProperties(doc);
		log.debug("setProperties: void");
	}

	@WebMethod
	public void checkout(@WebParam(name = "docPath") String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, LockException, DatabaseException {
		log.debug("checkout({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.checkout(docPath);
		log.debug("checkout: void");
	}

	@WebMethod
	public void cancelCheckout(@WebParam(name = "docPath") String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, LockException, DatabaseException {
		log.debug("cancelCheckout({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.cancelCheckout(docPath);
		log.debug("cancelCheckout: void");
	}
	
	@WebMethod
	public Version checkin(@WebParam(name = "docPath") String docPath,
			@WebParam(name = "comment") String comment) throws LockException, VersionException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("checkin({}, {})", docPath, comment);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Version version = dm.checkin(docPath, comment);
		log.debug("checkin: {}", version);
		return version;
	}

	@WebMethod
	public Version[] getVersionHistory(@WebParam(name = "docPath") String docPath) throws 
			PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getVersionHistory({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		List<Version> col = dm.getVersionHistory(docPath);
		Version[] result = (Version[]) col.toArray(new Version[col.size()]);
		log.debug("getVersionHistory: {}", result);
		return result;
	}

	@WebMethod
	public void setContent(@WebParam(name = "docPath") String docPath,
			@WebParam(name = "content") byte[] content) throws FileSizeExceededException, 
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

	@WebMethod
	public void lock(@WebParam(name = "token") String token,
			@WebParam(name = "docPath") String docPath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("lock({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.lock(token, docPath);
		log.debug("lock: void");
	}

	@WebMethod
	public void unlock(@WebParam(name = "docPath") String docPath) throws LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("unlock({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.unlock(docPath);
		log.debug("unlock: void");
	}

	@WebMethod
	public void purge(@WebParam(name = "docPath") String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("purge({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.purge(docPath);
		log.debug("purge: void");
	}

	@WebMethod
	public void move(@WebParam(name = "docPath") String docPath, 
			@WebParam(name = "fldPath") String fldPath) throws LockException, PathNotFoundException,
			ItemExistsException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("move({}, {})", docPath, fldPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.move(docPath, fldPath);
		log.debug("move: void");
	}
	
	@WebMethod
	public void restoreVersion(@WebParam(name = "docPath") String docPath,
			@WebParam(name = "versionId") String versionId) throws AccessDeniedException, 
			RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("restoreVersion({}, {})", docPath, versionId);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.restoreVersion(docPath, versionId);
		log.debug("restoreVersion: void");
	}
	
	@WebMethod
	public void purgeVersionHistory(@WebParam(name = "docPath") String docPath) throws AccessDeniedException,
			RepositoryException, PathNotFoundException, DatabaseException {
		log.debug("purgeVersionHistory({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.purgeVersionHistory(docPath);
		log.debug("purgeVersionHistory: void");
	}
	
	@WebMethod
	public long getVersionHistorySize(@WebParam(name = "docPath") String docPath) throws RepositoryException,
			PathNotFoundException, DatabaseException {
		log.debug("getVersionHistorySize({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		long size = dm.getVersionHistorySize(docPath);
		log.debug("getVersionHistorySize: {}", size);
		return size;
	}
	
	@WebMethod
	public boolean isValid(@WebParam(name = "docPath") String docPath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("isValid({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		boolean valid = dm.isValid(docPath);
		log.debug("isValid: {}", valid);
		return valid;
	}
	
	@WebMethod
	public String getPath(@WebParam(name = "uuid") String uuid) throws AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("getPath({})", uuid);
		DocumentModule dm = ModuleManager.getDocumentModule();
		String path = dm.getPath(uuid);
		log.debug("getPath: {}", path);
		return path;
	}
	
	@WebMethod
	public void addNote(@WebParam(name = "docPath") String docPath,
			@WebParam(name = "text") String text) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("addNote({}, {})", docPath, text);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.addNote(docPath, text);
		log.debug("addNote: void");
	}
	
	@WebMethod
	public Note getNote(@WebParam(name = "notePath") String notePath) throws LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("addNote({})", notePath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		Note ret = dm.getNote(notePath);
		log.debug("addNote: {}", ret);
		return ret;
	}

	@WebMethod
	public void removeNote(@WebParam(name = "notePath") String notePath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("removeNote({})", notePath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.removeNote(notePath);
		log.debug("removeNote: void");
	}

	@WebMethod
	public void setNote(@WebParam(name = "notePath") String notePath,
			@WebParam(name = "text") String text) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("setNote({}, {})", notePath, text);
		DocumentModule dm = ModuleManager.getDocumentModule();
		dm.setNote(notePath, text);
		log.debug("setNote: void");
	}
}
