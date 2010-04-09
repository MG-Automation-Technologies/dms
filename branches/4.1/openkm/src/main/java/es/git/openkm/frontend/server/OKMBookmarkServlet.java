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

package es.git.openkm.frontend.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.api.OKMBookmark;
import es.git.openkm.bean.Bookmark;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.bean.GWTBookmark;
import es.git.openkm.frontend.client.config.ErrorCode;
import es.git.openkm.frontend.client.service.OKMBookmarkService;
import es.git.openkm.frontend.client.util.BookmarkComparator;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMBookmarkServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMBookmarkServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMBookmarkServlet extends OKMRemoteServiceServlet implements OKMBookmarkService {
	private static Logger log = LoggerFactory.getLogger(OKMBookmarkServlet.class);
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMBookmarkService#getAll()
	 */
	public List<GWTBookmark> getAll() throws OKMException {
		log.debug("getAll()");
		List<GWTBookmark> bookmarkList = new ArrayList<GWTBookmark>(); 
		String token = getToken();
		
		try {
			Collection col = OKMBookmark.getInstance().getAll(token);
			
			for (Iterator it = col.iterator(); it.hasNext();) {		
				Bookmark bookmark = (Bookmark) it.next();
				log.debug("Bookmark: "+bookmark);
				GWTBookmark bookmarkClient = Util.copy(bookmark);

				log.debug("Bookmark: "+bookmark+" -> DocumentClient: "+bookmarkClient);
				bookmarkList.add(bookmarkClient);
			}
			
			Collections.sort(bookmarkList, BookmarkComparator.getInstance());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getAll: "+bookmarkList);
		return bookmarkList;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMBookmarkService#add(java.lang.String, java.lang.String)
	 */
	public GWTBookmark add(String nodePath, String name) throws OKMException {
		log.debug("add("+nodePath +","+name +")");
		String token = getToken();
		
		try {
			return Util.copy(OKMBookmark.getInstance().add(token, nodePath, name));
		} catch (ItemExistsException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_General), e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMBookmarkService#remove(java.lang.String)
	 */
	public void remove(String name) throws OKMException {
		log.debug("remove("+name +")");
		String token = getToken();
		
		try {
			OKMBookmark.getInstance().remove(token, name);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("remove: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMBookmarkService#setUserHome(java.lang.String)
	 */
	public void setUserHome(String name) throws OKMException {
		log.debug("setUserHome("+name +")");
		String token = getToken();
		
		try {
			OKMBookmark.getInstance().setUserHome(token, name);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("setUserHome: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMBookmarkService#getUserHome()
	 */
	public GWTBookmark getUserHome() throws OKMException {
		log.debug("getUserHome()");
		String token = getToken();
		
		try {
			return Util.copy(OKMBookmark.getInstance().getUserHome(token));
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_General), e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.service.OKMBookmarkService#rename(java.lang.String, java.lang.String)
	 */
	public GWTBookmark rename(String name, String newName) throws OKMException {
		log.debug("rename("+name +"," + newName +")");
		String token = getToken();
		
		try {
			return Util.copy(OKMBookmark.getInstance().rename(token, name, newName));
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (ItemExistsException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMBookmarkService, ErrorCode.CAUSE_General), e.getMessage());
		}
	}
}
