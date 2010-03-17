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

package com.openkm.frontend.client.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.openkm.frontend.client.bean.GWTFormElement;
import com.openkm.frontend.client.bean.GWTPropertyGroup;

/**
 * @author jllort
 *
 */
public interface OKMPropertyGroupServiceAsync {
	public void getAllGroups(AsyncCallback<List<GWTPropertyGroup>> callback);
	public void getAllGroups(String docPath, AsyncCallback<List<GWTPropertyGroup>> callback);
	public void addGroup(String docPath, String grpName, AsyncCallback<?> callback);
	public void getGroups(String docPath, AsyncCallback<List<GWTPropertyGroup>> callback);
	public void getProperties(String docPath, String grpName, AsyncCallback<Map<String, String[]>> callback);
	public void getMetaData(String grpName, AsyncCallback<Collection<GWTFormElement>> callback);
	public void setProperties(String docPath, String grpName, Map<String, String[]> properties, AsyncCallback<?> callback);
	public void removeGroup( String docPath, String grpName, AsyncCallback<?> callback);
}