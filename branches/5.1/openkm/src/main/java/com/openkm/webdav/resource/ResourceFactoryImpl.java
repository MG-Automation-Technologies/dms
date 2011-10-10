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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bradmcevoy.common.Path;
import com.bradmcevoy.http.Resource;
import com.bradmcevoy.http.ResourceFactory;
import com.openkm.api.OKMDocument;
import com.openkm.api.OKMFolder;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.webdav.JcrSessionTokenHolder;

/**
 * You should generally avoid using any request information other then that
 * provided in the method arguments. But if you find you need to you can access
 * the request and response objects from HttpManager.request() and
 * HttpManager.response()
 * 
 * @author pavila
 */
public class ResourceFactoryImpl implements ResourceFactory {
	private static final Logger log = LoggerFactory.getLogger(ResourceFactoryImpl.class);
	public static final String REALM = "OpenKM";
	private static RootResource rootResource = null; 
	
	@Override
	public Resource getResource(String host, String url) {
		log.info("getResource({}, {})", host, url);
		Path srcPath = Path.path(url);
		
		// STRIP PRECEEDING PATH
		Path path = srcPath.getStripFirst().getStripFirst();
		
		try {
			if (path.isRoot()) {
				log.info("ROOT");
				
				if (rootResource == null) {
					rootResource = new RootResource(this, srcPath);
				}
				
				return rootResource;
			} else {
				return getNode(srcPath, path.toPath());
			}
		} catch (PathNotFoundException e) {
			log.error("PathNotFoundException: " + e.getMessage());
		} catch (AccessDeniedException e) {
			log.error("AccessDeniedException: " + e.getMessage());
		} catch (RepositoryException e) {
			log.error("RepositoryException: " + e.getMessage());
		} catch (DatabaseException e) {
			log.error("DatabaseException: " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 
	 */
	public Resource getFolder(Path path, String fldPath) throws PathNotFoundException, RepositoryException,
			DatabaseException {
		String token = JcrSessionTokenHolder.get();
		Folder fld = OKMFolder.getInstance().getProperties(token, fldPath);
		List<Folder> fldChilds = OKMFolder.getInstance().getChilds(token, fldPath);
		List<Document> docChilds = OKMDocument.getInstance().getChilds(token, fldPath);
		Resource fldResource = new FolderResource(this, path, fld, fldChilds, docChilds);
		return fldResource;
	}
	
	/**
	 * 
	 */
	public Resource getDocument(String docPath) throws PathNotFoundException, RepositoryException, DatabaseException {
		String token = JcrSessionTokenHolder.get();
		Document doc = OKMDocument.getInstance().getProperties(token, docPath);
		Resource docResource = new DocumentResource(doc);
		return docResource;
	}
	
	/**
	 * 
	 */
	public Resource getNode(Path srcPath, String path) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.info("getNode({}, {})", srcPath, path);
		
		String repoPath = path;
		String token = JcrSessionTokenHolder.get();
		
		if (OKMFolder.getInstance().isValid(token, repoPath)) {
			Resource res = getFolder(srcPath, repoPath);
			log.info("getNode: {}", res);
			return res;
		} else if (OKMDocument.getInstance().isValid(token, repoPath)) {
			Resource res = getDocument(repoPath);
			log.info("getNode: {}", res);
			return res;
		}
		
		log.info("getNode: null");
		return null;
	}
}
