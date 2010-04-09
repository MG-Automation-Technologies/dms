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

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * OKMFolderServiceAsync
 * 
 * @author jllort
 *
 */
public interface OKMFolderServiceAsync {
	public void getChilds(String fldId, AsyncCallback callback);
	public void delete(String fldPath, AsyncCallback callback);
	public void create(String fldId, String fldIdParent,AsyncCallback callback);
	public void rename(String fldId, String newName,AsyncCallback callback);
	public void move(String fldPath, String dstPath, AsyncCallback callback);
	public void purge(String fldPath, AsyncCallback callback);
	public void getProperties(String fldPath, AsyncCallback callback);
	public void copy(String fldPath, String dstPath, AsyncCallback callback);
	public void isValid(String fldPath, AsyncCallback callback);
}