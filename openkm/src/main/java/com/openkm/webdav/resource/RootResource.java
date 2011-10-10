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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bradmcevoy.common.Path;
import com.bradmcevoy.http.Auth;
import com.bradmcevoy.http.CollectionResource;
import com.bradmcevoy.http.GetableResource;
import com.bradmcevoy.http.PropFindableResource;
import com.bradmcevoy.http.Range;
import com.bradmcevoy.http.Request;
import com.bradmcevoy.http.Request.Method;
import com.bradmcevoy.http.Resource;
import com.bradmcevoy.http.exceptions.BadRequestException;
import com.bradmcevoy.http.exceptions.NotAuthorizedException;
import com.openkm.api.OKMRepository;
import com.openkm.bean.Folder;
import com.openkm.bean.Repository;
import com.openkm.core.PathNotFoundException;
import com.openkm.jcr.JCRUtils;
import com.openkm.webdav.JcrSessionTokenHolder;

public class RootResource implements PropFindableResource, GetableResource, CollectionResource {
	private final Logger log = LoggerFactory.getLogger(RootResource.class);
	private final List<Folder> fldChilds = new ArrayList<Folder>();
	private Folder fld;
	private final Path path;
	
	public RootResource(Path path) {
		this.path = path;
		this.fld = new Folder();
		this.fld.setPath("/");
		this.fld.setUuid(Repository.getUuid());
		
		try {
			String token = JcrSessionTokenHolder.get();
			
			Folder okmRoot= OKMRepository.getInstance().getRootFolder(token);
			fldChilds.add(ResourceUtils.fixResourcePath(okmRoot));
			this.fld.setCreated(okmRoot.getCreated());
			
			Folder okmPersonal = OKMRepository.getInstance().getPersonalFolderBase(token);
			fldChilds.add(ResourceUtils.fixResourcePath(okmPersonal));
			
			Folder okmTemplates = OKMRepository.getInstance().getTemplatesFolder(token);
			fldChilds.add(ResourceUtils.fixResourcePath(okmTemplates));
		} catch (PathNotFoundException e) {
			log.error("PathNotFoundException: " + e.getMessage());
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
		}
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
			return ResourceUtils.getNode(path, Path.path(fld.getPath()).getStripFirst() + "/" + childName);
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
		
		return resources;
	}
	
	@Override
	public void sendContent(OutputStream out, Range range, Map<String, String> params, String contentType)
			throws IOException, NotAuthorizedException, BadRequestException {
		log.info("sendContent({}, {})", params, contentType);
		ResourceUtils.createContent(out, path, fldChilds, null);
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
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("fld="); sb.append(fld);
		sb.append("}");
		return sb.toString();
	}
}