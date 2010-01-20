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

package com.openkm.backend.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMFolder;
import com.openkm.backend.client.OKMException;
import com.openkm.backend.client.bean.GWTFolder;
import com.openkm.backend.client.config.ErrorCode;
import com.openkm.backend.client.service.OKMFolderService;
import com.openkm.backend.client.util.FolderComparator;
import com.openkm.bean.Folder;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMFolderServletAdmin"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMFolderServletAdmin"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMFolderServletAdmin extends OKMRemoteServiceServletAdmin implements OKMFolderService {
	private static Logger log = LoggerFactory.getLogger(OKMFolderServletAdmin.class);
	private static final long serialVersionUID = -4436438730167948558L;

	/* (non-Javadoc)
	 * @see com.openkm.backend.client.service.OKMFolderService#getChilds(java.lang.String)
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
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderServiceAdmin, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderServiceAdmin, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		}  catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMFolderServiceAdmin, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getFolderChilds: "+folderList);
		return folderList;
	}

}