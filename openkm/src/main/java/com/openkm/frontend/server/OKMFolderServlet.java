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

package com.openkm.frontend.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMFolder;
import com.openkm.bean.Folder;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMFolderService;
import com.openkm.frontend.client.util.FolderComparator;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMFolderServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMFolderServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMFolderServlet extends OKMRemoteServiceServlet implements OKMFolderService {
	private static Logger log = LoggerFactory.getLogger(OKMFolderServlet.class);
	private static final long serialVersionUID = -4436438730167948558L;
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMFolderService#create(java.lang.String, java.lang.String)
	 */
	public GWTFolder create(String fldPath, String fldPathParent) throws OKMException {
		log.debug("create("+fldPath+", "+fldPathParent+")");
		String token = getToken();
		GWTFolder gWTFolder = new GWTFolder();
		Folder folder = new Folder();
		folder.setPath(fldPathParent+"/"+fldPath);
		
		try {
			gWTFolder = Util.copy(OKMFolder.getInstance().create(token, folder));
		} catch (ItemExistsException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e){
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e ){
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("create: "+gWTFolder);
		return gWTFolder;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMFolderService#delete(java.lang.String)
	 */
	public void delete(String fldPath) throws OKMException {
		log.debug("delete("+fldPath+")");
		String token = getToken();
		
		try {
			OKMFolder.getInstance().delete(token, fldPath);
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e){
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e ){
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("delete: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMFolderService#getChilds(java.lang.String)
	 */
	public List<GWTFolder> getChilds(String fldPath) throws OKMException {
		log.debug("getFolderChilds("+fldPath+")");
		List<GWTFolder> folderList = new ArrayList<GWTFolder>(); 
		String token = getToken();
		
		try {
			log.debug("ParentFolder: "+fldPath);
			Collection<Folder> col = OKMFolder.getInstance().getChilds(token, fldPath);
			for (Iterator<Folder> it = col.iterator(); it.hasNext();){				
				Folder folder = it.next();
				GWTFolder gWTFolder = Util.copy(folder);

				log.debug("Folder: "+folder+"  ->  gWTFolder: "+gWTFolder);
				folderList.add(gWTFolder);
			}
			
			Collections.sort(folderList, FolderComparator.getInstance());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		}  catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getFolderChilds: "+folderList);
		return folderList;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMFolderService#rename(java.lang.String, java.lang.String)
	 */
	public GWTFolder rename(String fldId, String newName)  throws OKMException  {
		log.debug("rename(" + fldId + "," + newName + ")");
		String token = getToken();
		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			gWTFolder = Util.copy(OKMFolder.getInstance().rename(token, fldId, newName));
		} catch (ItemExistsException e){
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_Repository), e.getMessage());
		}  catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("rename: "+gWTFolder);
		
		return gWTFolder;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMFolderService#move(java.lang.String, java.lang.String)
	 */
	public void move(String fldPath, String dstPath) throws OKMException {
		log.debug("move("+fldPath+","+dstPath+")");
		String token = getToken();
		
		try {
			OKMFolder.getInstance().move(token, fldPath, dstPath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e){
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (ItemExistsException e ){
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (RepositoryException e ){
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("move: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMFolderService#purge(java.lang.String)
	 */
	public void purge(String fldPath) throws OKMException {
		log.debug("purge("+fldPath+")");
		String token = getToken();
		
		try {
			OKMFolder.getInstance().purge(token, fldPath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e){
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e ){
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("purge: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMFolderService#get(java.lang.String)
	 */
	public GWTFolder getProperties(String fldPath) throws OKMException {
		log.debug("get("+fldPath+")");
		String token = getToken();

		GWTFolder gWTFolder = new GWTFolder();
		
		try {
			gWTFolder = Util.copy(OKMFolder.getInstance().getProperties(token, fldPath));
		} catch (PathNotFoundException e ){
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e ){
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e ){
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("get: " + gWTFolder);
		
		return gWTFolder;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMFolderService#copy(java.lang.String, java.lang.String)
	 */
	public void copy(String fldPath, String dstPath) throws OKMException {
		log.debug("copy("+fldPath+", " + dstPath + ")");
		String token = getToken();
		
		try {
			OKMFolder.getInstance().copy(token, fldPath, dstPath);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e){
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (ItemExistsException e ){
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (RepositoryException e ){
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("copy: void");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMFolderService#isValid(java.lang.String)
	 */
	public Boolean isValid(String fldPath) throws OKMException {
		log.debug("isValid("+fldPath+")");
		String token = getToken();
		
		try {
			return Boolean.valueOf(OKMFolder.getInstance().isValid(token, fldPath));
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e){
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e ){
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderService, ErrorCode.CAUSE_General), e.getMessage());
		}
	}
}
