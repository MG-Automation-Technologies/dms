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

package es.git.openkm.backend.client.service;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import es.git.openkm.backend.client.bean.GWTActivity;
import es.git.openkm.backend.client.bean.GWTSessionInfo;
import es.git.openkm.backend.client.bean.GWTUser;

/**
 * @author jllort
 *
 */
public interface OKMUserServiceAsync {
	public void getLogedUsers(AsyncCallback<List<GWTSessionInfo>> callback);
	public void logout(String token, AsyncCallback<?> callback);
	public void findAllUsers(AsyncCallback<List<GWTUser>> callback);
	public void createUser(GWTUser gWTUser, AsyncCallback<?> callback);
	public void updateUser(GWTUser gWTUser, AsyncCallback<?> callback);
	public void deleteUser(GWTUser gWTUser, AsyncCallback<?> callback);
	public void findAllRoles(AsyncCallback<List<String>> callback);
	public void findActivityByFilter(Date date_begin, Date date_end, String user, String action, AsyncCallback<List<GWTActivity>> callback);
	public void isAdmin(AsyncCallback<Boolean> callback);
}