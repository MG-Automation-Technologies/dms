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

package es.git.openkm.frontend.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author jllort
 *
 */
public interface OKMAuthServiceAsync {
	public void logout(AsyncCallback callback);
	public void getGrantedUsers(String nodePath, AsyncCallback callback);
	public void getGrantedRoles(String nodePath, AsyncCallback callback);
	public void getRemoteUser(AsyncCallback callback);
	public void getUngrantedUsers(String nodePath, AsyncCallback callback);
	public void getUngrantedRoles(String nodePath, AsyncCallback callback);
	public void grantUser(String path, String user, int permissions, boolean recursive, AsyncCallback callback);
	public void revokeUser(String path, String user, boolean recursive, AsyncCallback callback);
	public void revokeUser(String path, String user, int permissions, boolean recursive, AsyncCallback callback);
	public void grantRole(String path, String role, int permissions, boolean recursive, AsyncCallback callback);
	public void revokeRole(String path, String role, boolean recursive, AsyncCallback callback);
	public void revokeRole(String path, String role, int permissions, boolean recursive, AsyncCallback callback);
	public void keepAlive(AsyncCallback callback);
	public void getAllUsers(AsyncCallback callback);
}