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

package com.openkm.dao.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AccessManager implements Serializable {
	private static final long serialVersionUID = 1L;
	private String uuid;
	private Set<String> usersRead = new HashSet<String>();
	private Set<String> usersWrite = new HashSet<String>();
	private Set<String> usersDelete = new HashSet<String>();
	private Set<String> usersSecurity = new HashSet<String>();
	private Set<String> rolesRead = new HashSet<String>();
	private Set<String> rolesWrite = new HashSet<String>();
	private Set<String> rolesDelete = new HashSet<String>();
	private Set<String> rolesSecurity = new HashSet<String>();
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Set<String> getUsersRead() {
		return usersRead;
	}

	public void setUsersRead(Set<String> usersRead) {
		this.usersRead = usersRead;
	}

	public Set<String> getUsersWrite() {
		return usersWrite;
	}

	public void setUsersWrite(Set<String> usersWrite) {
		this.usersWrite = usersWrite;
	}

	public Set<String> getUsersDelete() {
		return usersDelete;
	}

	public void setUsersDelete(Set<String> usersDelete) {
		this.usersDelete = usersDelete;
	}

	public Set<String> getUsersSecurity() {
		return usersSecurity;
	}

	public void setUsersSecurity(Set<String> usersSecurity) {
		this.usersSecurity = usersSecurity;
	}

	public Set<String> getRolesRead() {
		return rolesRead;
	}

	public void setRolesRead(Set<String> rolesRead) {
		this.rolesRead = rolesRead;
	}

	public Set<String> getRolesWrite() {
		return rolesWrite;
	}

	public void setRolesWrite(Set<String> rolesWrite) {
		this.rolesWrite = rolesWrite;
	}

	public Set<String> getRolesDelete() {
		return rolesDelete;
	}

	public void setRolesDelete(Set<String> rolesDelete) {
		this.rolesDelete = rolesDelete;
	}

	public Set<String> getRolesSecurity() {
		return rolesSecurity;
	}

	public void setRolesSecurity(Set<String> rolesSecurity) {
		this.rolesSecurity = rolesSecurity;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("uuid="); sb.append(uuid);
		sb.append(", usersRead="); sb.append(usersRead);
		sb.append(", usersWrite="); sb.append(usersWrite);
		sb.append(", usersDelete="); sb.append(usersDelete);
		sb.append(", usersSecurity="); sb.append(usersSecurity);
		sb.append(", rolesRead="); sb.append(rolesRead);
		sb.append(", rolesWrite="); sb.append(rolesWrite);
		sb.append(", rolesDelete="); sb.append(rolesDelete);
		sb.append(", rolesSecurity="); sb.append(rolesSecurity);
		sb.append("}");
		return sb.toString();
	}

}
