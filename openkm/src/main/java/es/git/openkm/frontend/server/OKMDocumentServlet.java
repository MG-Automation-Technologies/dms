/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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

package es.git.openkm.frontend.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMDocument;
import es.git.openkm.api.OKMRepository;
import es.git.openkm.api.OKMSearch;
import es.git.openkm.bean.Document;
import es.git.openkm.bean.QueryParams;
import es.git.openkm.bean.QueryResult;
import es.git.openkm.bean.Version;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.VersionException;
import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.bean.GWTQueryParams;
import es.git.openkm.frontend.client.bean.GWTQueryResult;
import es.git.openkm.frontend.client.bean.GWTVersion;
import es.git.openkm.frontend.client.config.ErrorCode;
import es.git.openkm.frontend.client.service.OKMDocumentService;
import es.git.openkm.frontend.client.util.DocumentComparator;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMDocumentServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMDocumentServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMDocumentServlet extends OKMRemoteServiceServlet implements OKMDocumentService {
	private static Logger log = LoggerFactory.getLogger(OKMDocumentServlet.class);
	private static final long serialVersionUID = 5746570509074299745L;
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#getChilds(java.lang.String)
	 */
	public List<GWTDocument> getChilds(String fldPath) throws OKMException {
		log.debug("getDocumentChilds("+fldPath+")");
		List<GWTDocument> docList = new ArrayList<GWTDocument>(); 
		String token = getToken();
		
		try {
			if (fldPath == null) {
				fldPath = OKMRepository.getInstance().getRootFolder(token).getPath();
			}
			
			// Case thesaurus view must search documents in keywords 
			if (fldPath.startsWith("/okm:thesaurus")){
				QueryParams queryParams = new QueryParams();
				queryParams.setKeywords(fldPath.substring(fldPath.lastIndexOf("/")+1).replace(" ", "_"));
				Collection<QueryResult> results = OKMSearch.getInstance().find(token, queryParams);
				for (Iterator<QueryResult> it = results.iterator(); it.hasNext();) {		
					QueryResult queryResult = it.next();
					if (queryResult.getDocument()!=null) {
						GWTDocument docClient = Util.copy(queryResult.getDocument());
						docList.add(docClient);
					}
				}
			} else {
			
				log.debug("ParentFolder: "+fldPath);
				Collection<Document> col = OKMDocument.getInstance().getChilds(token, fldPath);
				
				for (Iterator<Document> it = col.iterator(); it.hasNext();) {		
					Document doc = it.next();
					log.debug("Document: "+doc);
					GWTDocument docClient = Util.copy(doc);
	
					log.debug("Document: "+doc+" -> DocumentClient: "+docClient);
					docList.add(docClient);
				}
			}
			
			Collections.sort(docList, DocumentComparator.getInstance());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getDocumentChilds: "+docList);
		return docList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#getVersionHistory(java.lang.String)
	 */
	public List<GWTVersion> getVersionHistory (String docPath) throws OKMException {
		log.debug("getVersionHistory("+docPath+")");
		List<GWTVersion> versionList = new ArrayList<GWTVersion>(); 
		String token = getToken();
		
		try {
			log.debug("docPath: "+docPath);
			Collection<Version> col = OKMDocument.getInstance().getVersionHistory(token, docPath);
			
			for (Iterator<Version> it = col.iterator(); it.hasNext();){		
				Version version = it.next();
				log.debug("version: " + version);
				GWTVersion versionClient = Util.copy(version);
				log.debug("Version: "+version+" -> VersionClient: "+versionClient);
				versionList.add(versionClient); 
			}
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getVersionHistory: "+versionList);
		return versionList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#delete(java.lang.String)
	 */
	public void delete(String docPath) throws OKMException {
		log.debug("delete("+docPath+")");
		String token = getToken();
		
		try {
			OKMDocument.getInstance().delete(token, docPath);
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("delete: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#checkout(java.lang.String)
	 */
	public void checkout(String docPath) throws OKMException {
		log.debug("checkout("+docPath+")");
		String token = getToken();
		
		try {
			OKMDocument.getInstance().checkout(token, docPath);
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("checkout: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#cancelCheckout(java.lang.String)
	 */
	public void cancelCheckout(String docPath) throws OKMException {
		log.debug("cancelCheckout("+docPath+")");
		String token = getToken();
		
		try {
			OKMDocument.getInstance().cancelCheckout(token, docPath);
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("cancelCheckout: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#lock(java.lang.String)
	 */
	public void lock(String docPath) throws OKMException {
		log.debug("lock("+docPath+")");
		String token = getToken();
		
		try {
			OKMDocument.getInstance().lock(token, docPath);
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("lock: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#unlock(java.lang.String)
	 */
	public void unlock(String docPath) throws OKMException {
		log.debug("lock("+docPath+")");
		String token = getToken();
		
		try {
			OKMDocument.getInstance().unlock(token, docPath);
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_UnLock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("lock: void");
	}
	

	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#rename(java.lang.String, java.lang.String)
	 */
	public GWTDocument rename(String docPath, String newName) throws OKMException {
		log.debug("rename("+docPath+", "+ newName +")");
		String token = getToken();		
		GWTDocument gWTDocument = new GWTDocument();
		
		try {
			gWTDocument = Util.copy(OKMDocument.getInstance().rename(token, docPath, newName));
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (ItemExistsException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("rename: "+gWTDocument);
		return gWTDocument;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#move(java.lang.String, java.lang.String)
	 */
	public void move(String docPath, String destPath) throws OKMException {
		log.debug("move("+docPath+","+destPath+")");
		String token = getToken();
		
		try {
			OKMDocument.getInstance().move(token,docPath,destPath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (ItemExistsException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("move: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#purge(java.lang.String)
	 */
	public void purge(String docPath) throws OKMException {
		log.debug("purge("+docPath+")");
		String token = getToken();
		
		try {
			OKMDocument.getInstance().purge(token,docPath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("purge: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#setProperties(es.git.openkm.frontend.client.bean.GWTDocument)
	 */
	public void setProperties(GWTDocument doc) throws OKMException {
		log.debug("setProperties("+doc+")");
		String token = getToken();
		
		try {
			OKMDocument.getInstance().setProperties(token,Util.copy(doc));
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (VersionException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Version), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("setProperties: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#restoreVersion(java.lang.String, java.lang.String)
	 */
	public void restoreVersion(String docPath, String versionId) throws OKMException {
		log.debug("restoreVersion("+docPath+", "+versionId+")");
		String token = getToken();
		
		try {
			OKMDocument.getInstance().restoreVersion(token, docPath, versionId);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("restoreVersion: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#get(java.lang.String)
	 */
	public GWTDocument get(String docPath) throws OKMException {
		log.debug("get("+docPath+")");
		String token = getToken();
		GWTDocument gWTDocument = new GWTDocument();
		
		try {
			gWTDocument = Util.copy(OKMDocument.getInstance().getProperties(token, docPath));
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("get: "+gWTDocument);
		return gWTDocument;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#copy(java.lang.String, java.lang.String)
	 */
	public void copy(String docPath, String fldPath) throws OKMException {
		log.debug("copy("+docPath+", " + fldPath + ")");
		String token = getToken();
		
		try {
			OKMDocument.getInstance().copy(token, docPath, fldPath);
		} catch (ItemExistsException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_IOException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("copy: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#isValid(java.lang.String)
	 */
	public Boolean isValid(String docPath) throws OKMException {
		log.debug("isValid("+docPath+")");
		String token = getToken();
		
		try {
			return new Boolean(OKMDocument.getInstance().isValid(token, docPath));
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#getVersionHistorySize(java.lang.String)
	 */
	public Long getVersionHistorySize(String docPath) throws OKMException {
		log.debug("getVersionHistorySize("+docPath+")");
		String token = getToken();
	
		try {
			return new Long(OKMDocument.getInstance().getVersionHistorySize(token, docPath));			
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMDocumentService#purgeVersionHistory(java.lang.String)
	 */
	public void purgeVersionHistory(String docPath) throws OKMException {
		log.debug("purgeVersionHistory("+docPath+")");
		String token = getToken();
	
		try {
			OKMDocument.getInstance().purgeVersionHistory(token, docPath);			
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("purgeVersionHistory: void");
	}
	
	public void addNote(String docPath, String text) throws OKMException {
		log.debug("addNote("+docPath+", "+text+")");
		String token = getToken();
		
		try {
			OKMDocument.getInstance().addNote(token, docPath, text);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMDocumentService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("addNote: void");
	}
}
