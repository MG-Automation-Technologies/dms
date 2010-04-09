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

package es.git.openkm.backend.client.bean;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author jllort
 *
 */
public class GWTSessionInfo  implements IsSerializable  {
	
	public static final String CREATION = "creation";
	public static final String ACCESS = "access";
	
	private Date creation;
	private Date access;
	private int loginInstances;
	private String userID;
	private String token;
	
	/**
	 * GWTSessionInfo
	 */
	public GWTSessionInfo() {
	}
	
	/**
	 * @return
	 */
	public Date getAccess() {
		return access;
	}
	
	/**
	 * @param access
	 */
	public void setAccess(Date access) {
		this.access = access;
	}
	
	/**
	 * @return
	 */
	public Date getCreation() {
		return creation;
	}
	
	/**
	 * @param creation
	 */
	public void setCreation(Date creation) {
		this.creation = creation;
	}
	
	/**
	 * @return
	 */
	public int getLoginInstances() {
		return loginInstances;
	}

	/**
	 * @param loginInstances
	 */
	public void setLoginInstances(int loginInstances) {
		this.loginInstances = loginInstances;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
