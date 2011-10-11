/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

package com.openkm.webdav.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bradmcevoy.common.Path;
import com.bradmcevoy.http.Auth;
import com.bradmcevoy.http.CollectionResource;
import com.bradmcevoy.http.CopyableResource;
import com.bradmcevoy.http.DeletableResource;
import com.bradmcevoy.http.GetableResource;
import com.bradmcevoy.http.LockInfo;
import com.bradmcevoy.http.LockResult;
import com.bradmcevoy.http.LockTimeout;
import com.bradmcevoy.http.LockToken;
import com.bradmcevoy.http.LockableResource;
import com.bradmcevoy.http.MakeCollectionableResource;
import com.bradmcevoy.http.MoveableResource;
import com.bradmcevoy.http.PropFindableResource;
import com.bradmcevoy.http.PutableResource;
import com.bradmcevoy.http.Range;
import com.bradmcevoy.http.Request;
import com.bradmcevoy.http.Request.Method;
import com.bradmcevoy.http.Resource;
import com.bradmcevoy.http.exceptions.BadRequestException;
import com.bradmcevoy.http.exceptions.ConflictException;
import com.bradmcevoy.http.exceptions.LockedException;
import com.bradmcevoy.http.exceptions.NotAuthorizedException;
import com.bradmcevoy.http.exceptions.PreConditionFailedException;
import com.openkm.api.OKMDocument;
import com.openkm.api.OKMFolder;
import com.openkm.api.OKMRepository;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.core.Config;
import com.openkm.core.PathNotFoundException;
import com.openkm.jcr.JCRUtils;
import com.openkm.webdav.JcrSessionTokenHolder;

public class FolderResource implements MakeCollectionableResource, PutableResource, CopyableResource,
		DeletableResource, MoveableResource, PropFindableResource, GetableResource, LockableResource {
	private final Logger log = LoggerFactory.getLogger(FolderResource.class);
	private final List<Document> docChilds;
	private final List<Folder> fldChilds;
	private Folder fld;
	private final Path path;
	
	public FolderResource(Folder fld) {
		this.fldChilds = null;
		this.docChilds = null;
		this.path = null;
		this.fld = ResourceUtils.fixResourcePath(fld);
	}
	
	public FolderResource(Path path, Folder fld, List<Folder> fldChilds, List<Document> docChilds) {
		this.fldChilds = fldChilds;
		this.docChilds = docChilds;
		this.path = path;
		this.fld = ResourceUtils.fixResourcePath(fld);
	}
	
	public Folder getFolder() {
		return fld;
	}
	
	@Override
	public String getUniqueId() {
		return fld.getUuid();
	}
	
	@Override
	public String getName() {
		return JCRUtils.getName(fld.getPath());
	}
	
	@Override
	public Object authenticate(String user, String password) {
		// log.info("authenticate({}, {})", new Object[] { user, password });
		return ResourceFactoryImpl.REALM;
	}
	
	@Override
	public boolean authorise(Request request, Method method, Auth auth) {
		// log.info("authorise({}, {}, {})", new Object[] {
		// request.getAbsolutePath(), method, auth });
		return true;
	}
	
	@Override
	public String getRealm() {
		return ResourceFactoryImpl.REALM;
	}
	
	@Override
	public Date getCreateDate() {
		return fld.getCreated().getTime();
	}
	
	@Override
	public Date getModifiedDate() {
		return fld.getCreated().getTime();
	}
	
	@Override
	public String checkRedirect(Request request) {
		return null;
	}
	
	@Override
	public Resource child(String childName) {
		log.info("child({})", childName);
		
		try {
			return ResourceUtils.getNode(path, fld.getPath() + "/" + childName);
		} catch (PathNotFoundException e) {
			log.error("PathNotFoundException: " + e.getMessage());
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
		}
		
		return null;
	}
	
	@Override
	public List<? extends Resource> getChildren() {
		log.info("getChildren()");
		List<Resource> resources = new ArrayList<Resource>();
		
		if (fldChilds != null) {
			for (Folder fld : fldChilds) {
				resources.add(new FolderResource(fld));
			}
		}
		
		if (docChilds != null) {
			for (Document doc : docChilds) {
				resources.add(new DocumentResource(doc));
			}
		}
		
		return resources;
	}
	
	@Override
	public Resource createNew(String newName, InputStream inputStream, Long length, String contentType)
			throws IOException, ConflictException, NotAuthorizedException, BadRequestException {
		log.info("createNew({}, {}, {})", new Object[] { newName, length, contentType });
		OKMDocument okmDocument = OKMDocument.getInstance();
		Document newDoc = new Document();
		String fixedDocPath = ResourceUtils.fixRepositoryPath(fld.getPath());
		newDoc.setPath(fixedDocPath + "/" + newName);
		
		try {
			String token = JcrSessionTokenHolder.get();
			
			if (OKMRepository.getInstance().hasNode(token, newDoc.getPath())) {
				// Already exists, so create new version
				okmDocument.checkout(token, newDoc.getPath());
				okmDocument.setContent(token, newDoc.getPath(), inputStream);
				okmDocument.checkin(token, newDoc.getPath(), "Modified from WebDAV");
			} else {
				// Restrict for extension
	       		StringTokenizer st = new StringTokenizer(Config.RESTRICT_FILE_EXTENSION, ",");
				
	       		while (st.hasMoreTokens()) {
	       			String wc = st.nextToken().trim();
	       			String re = ResourceUtils.wildcard2regexp(wc);
	       			
	       			if (Pattern.matches(re, newName)) {
	        			log.warn("Filename BAD -> {} ({})", re, wc);
	        			return null;
	        		} else {
	        			// Create a new one
	    				newDoc = okmDocument.create(token, newDoc, inputStream);
	        		}
	        	}
			}
			
			return new DocumentResource(newDoc);
		} catch (PathNotFoundException e) {
			log.warn("PathNotFoundException: " + e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("Failed to create: " + e.getMessage(), e);
		}
		
		return null;
	}
	
	@Override
	public CollectionResource createCollection(String newName) throws NotAuthorizedException, ConflictException,
			BadRequestException {
		log.info("createCollection({})", newName);
		Folder newFld = new Folder();
		String fixedFldPath = ResourceUtils.fixRepositoryPath(fld.getPath());
		newFld.setPath(fixedFldPath + "/" + newName);
		
		try {
			String token = JcrSessionTokenHolder.get();
			newFld = OKMFolder.getInstance().create(token, newFld);
			return new FolderResource(newFld);
		} catch (Exception e) {
			throw new ConflictException(this);
		}
	}
	
	@Override
	public void sendContent(OutputStream out, Range range, Map<String, String> params, String contentType)
			throws IOException, NotAuthorizedException, BadRequestException {
		log.info("sendContent({}, {})", params, contentType);
		ResourceUtils.createContent(out, path, fldChilds, docChilds);
	}
	
	@Override
	public Long getMaxAgeSeconds(Auth auth) {
		return null;
	}
	
	@Override
	public String getContentType(String accepts) {
		return null;
	}
	
	@Override
	public Long getContentLength() {
		return null;
	}
	
	@Override
	public void delete() throws NotAuthorizedException, ConflictException, BadRequestException {
		log.info("delete()");
		
		try {
			String token = JcrSessionTokenHolder.get();
			String fixedFldPath = ResourceUtils.fixRepositoryPath(fld.getPath());
			OKMFolder.getInstance().delete(token, fixedFldPath);
		} catch (Exception e) {
			throw new ConflictException(this);
		}
	}
	
	@Override
	public void moveTo(CollectionResource newParent, String newName) throws ConflictException, NotAuthorizedException,
			BadRequestException {
		log.info("moveTo({}, {})", newParent, newName);
		
		if (newParent instanceof FolderResource) {
			FolderResource newFldParent = (FolderResource) newParent;
			String dstPath = newFldParent.getFolder().getPath() + "/" + newName;
			
			try {
				String token = JcrSessionTokenHolder.get();
				String fixedFldPath = ResourceUtils.fixRepositoryPath(fld.getPath());
				fld = OKMFolder.getInstance().rename(token, fixedFldPath, newName);
			} catch (Exception e) {
				throw new RuntimeException("Failed to move to: " + dstPath, e);
			}
		} else {
			throw new RuntimeException("Destination is an unknown type. Must be a FsDirectoryResource, is a: "
					+ newParent.getClass());
		}
	}
	
	@Override
	public void copyTo(CollectionResource newParent, String newName) throws NotAuthorizedException,
			BadRequestException, ConflictException {
		log.info("copyTo({}, {})", newParent, newName);
		
		if (newParent instanceof FolderResource) {
			FolderResource newFldParent = (FolderResource) newParent;
			String dstPath = newFldParent.getFolder().getPath() + "/" + newName;
			
			try {
				String token = JcrSessionTokenHolder.get();
				String fixedFldPath = ResourceUtils.fixRepositoryPath(fld.getPath());
				OKMFolder.getInstance().copy(token, fixedFldPath, dstPath);
			} catch (Exception e) {
				throw new RuntimeException("Failed to copy to:" + dstPath, e);
			}
		} else {
			throw new RuntimeException("Destination is an unknown type. Must be a FolderResource, is a: "
					+ newParent.getClass());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("fld="); sb.append(fld);
		sb.append("}");
		return sb.toString();
	}

	@Override
	public LockResult lock(LockTimeout timeout, LockInfo lockInfo) throws NotAuthorizedException,
			PreConditionFailedException, LockedException {
		return null;
	}

	@Override
	public LockResult refreshLock(String token) throws NotAuthorizedException, PreConditionFailedException {
		return null;
	}

	@Override
	public void unlock(String tokenId) throws NotAuthorizedException, PreConditionFailedException {
	}

	@Override
	public LockToken getCurrentLock() {
		return null;
	}
}
