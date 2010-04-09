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

package es.git.openkm.frontend.client.service;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;

import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.bean.GWTFolder;

/**
 * OKMFolderService
 * 
 * @author jllort
 *
 */
public interface OKMFolderService extends RemoteService {

	public List<GWTFolder> getChilds (String fldId) throws OKMException;
	
	public void delete(String fldPath) throws OKMException;
	public GWTFolder create(String fldId, String fldIdParent) throws OKMException;
	public GWTFolder rename(String fldId, String newName) throws OKMException;
	public void move(String fldPath, String dstPath) throws OKMException;
	public void purge(String fldPath) throws OKMException;
	public GWTFolder getProperties(String fldPath) throws OKMException;
	public void copy(String fldPath, String dstPath) throws OKMException;
	public Boolean isValid(String fldPath) throws OKMException;
}