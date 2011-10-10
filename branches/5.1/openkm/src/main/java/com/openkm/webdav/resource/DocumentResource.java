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
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bradmcevoy.http.Auth;
import com.bradmcevoy.http.CollectionResource;
import com.bradmcevoy.http.CopyableResource;
import com.bradmcevoy.http.DeletableResource;
import com.bradmcevoy.http.GetableResource;
import com.bradmcevoy.http.MoveableResource;
import com.bradmcevoy.http.PropFindableResource;
import com.bradmcevoy.http.PropPatchableResource;
import com.bradmcevoy.http.Range;
import com.bradmcevoy.http.Request;
import com.bradmcevoy.http.Request.Method;
import com.bradmcevoy.http.exceptions.BadRequestException;
import com.bradmcevoy.http.exceptions.ConflictException;
import com.bradmcevoy.http.exceptions.NotAuthorizedException;
import com.bradmcevoy.http.webdav.PropPatchHandler.Fields;
import com.openkm.api.OKMDocument;
import com.openkm.bean.Document;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.jcr.JCRUtils;
import com.openkm.webdav.JcrSessionTokenHolder;

public class DocumentResource implements CopyableResource, DeletableResource, GetableResource,
		MoveableResource, PropFindableResource, PropPatchableResource {
	private static final Logger log = LoggerFactory.getLogger(DocumentResource.class);
	private Document doc;
	
	public DocumentResource(Document doc) {
		this.doc = ResourceUtils.fixResourcePath(doc);
	}
	
	@Override
	public String getUniqueId() {
		return doc.getUuid();
	}
	
	@Override
	public String getName() {
		return JCRUtils.getName(doc.getPath());
	}
	
	@Override
	public Object authenticate(String user, String password) {
		//log.info("authenticate({}, {})", new Object[] { user, password });
		return "OpenKM";
	}
	
	@Override
	public boolean authorise(Request request, Method method, Auth auth) {
		//log.info("authorise({}, {}, {})", new Object[] { request.getAbsolutePath(), method.toString(), auth.getUser() });
		return true;
	}
	
	@Override
	public String getRealm() {
		return ResourceFactoryImpl.REALM;
	}
	
	@Override
	public Date getCreateDate() {
		return doc.getCreated().getTime();
	}
	
	@Override
	public Date getModifiedDate() {
		return doc.getLastModified().getTime();
	}
	
	@Override
	public String checkRedirect(Request request) {
		return null;
	}
	
	@Override
	public Long getMaxAgeSeconds(Auth auth) {
		return null;
	}
	
	@Override
	public String getContentType(String accepts) {
		return doc.getMimeType();
	}
	
	@Override
	public Long getContentLength() {
		return doc.getActualVersion().getSize();
	}
	
	@Override
	public void sendContent(OutputStream out, Range range, Map<String, String> params, String contentType)
			throws IOException, NotAuthorizedException, BadRequestException {
		log.info("sendContent({}, {})", params, contentType);
		InputStream is = null;
		
		try {
			String token = JcrSessionTokenHolder.get();
			String fixedDocPath = ResourceUtils.fixRepositoryPath(doc.getPath());
			is = OKMDocument.getInstance().getContent(token, fixedDocPath, false);
			IOUtils.copy(is, out);
			out.flush();
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	@Override
	public void delete() throws NotAuthorizedException, ConflictException, BadRequestException {
		log.info("delete()");
		
		try {
			String token = JcrSessionTokenHolder.get();
			String fixedDocPath = ResourceUtils.fixRepositoryPath(doc.getPath());
			OKMDocument.getInstance().delete(token, fixedDocPath);
		} catch (Exception e) {
			throw new ConflictException(this);
		}
	}

	@Override
	public void setProperties(Fields fields) {
		// MIL-50: not implemented. Just to keep MS Office sweet
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
				String fixedDocPath = ResourceUtils.fixRepositoryPath(doc.getPath());
				doc = OKMDocument.getInstance().rename(token, fixedDocPath, newName);
			} catch (Exception e) {
				throw new RuntimeException("Failed to move to: " + dstPath);
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
				String fixedDocPath = ResourceUtils.fixRepositoryPath(doc.getPath());
				OKMDocument.getInstance().copy(token, fixedDocPath, dstPath);
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
		sb.append("doc="); sb.append(doc);
		sb.append("}");
		return sb.toString();
	}
}
