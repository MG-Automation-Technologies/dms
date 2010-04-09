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

package es.git.openkm.backend.client.service;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import es.git.openkm.backend.client.OKMException;
import es.git.openkm.backend.client.bean.GWTActivity;
import es.git.openkm.backend.client.bean.GWTSessionInfo;
import es.git.openkm.backend.client.bean.GWTUser;

/**
 * @author jllort
 *
 */
public interface OKMUserService extends RemoteService {

	public List<GWTSessionInfo> getLogedUsers() throws OKMException;
	public void logout(String token) throws OKMException;
	public List<GWTUser> findAllUsers() throws OKMException;
	public void createUser(GWTUser gWTUser) throws OKMException;
	public void updateUser(GWTUser gWTUser) throws OKMException;
	public void deleteUser(GWTUser gWTUser) throws OKMException;
	public List<String> findAllRoles() throws OKMException;
	public List<GWTActivity> findActivityByFilter(Date date_begin, Date date_end, String user, String action) throws OKMException;
	public Boolean isAdmin();
}