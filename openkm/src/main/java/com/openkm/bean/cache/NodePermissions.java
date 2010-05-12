package com.openkm.bean.cache;

import java.io.Serializable;
import java.util.HashSet;

public class NodePermissions implements Serializable {
	private static final long serialVersionUID = -895133213627179445L;
	
	private HashSet<String> usersRead;
	private HashSet<String> usersWrite;
	private HashSet<String> usersDelete;
	private HashSet<String> usersSecurity;
	private HashSet<String> rolesRead;
	private HashSet<String> rolesWrite;
	private HashSet<String> rolesDelete;
	private HashSet<String> rolesSecurity;
	
	public HashSet<String> getUsersRead() {
		return usersRead;
	}
	
	public void setUsersRead(HashSet<String> usersRead) {
		this.usersRead = usersRead;
	}
	
	public HashSet<String> getUsersWrite() {
		return usersWrite;
	}
	
	public void setUsersWrite(HashSet<String> usersWrite) {
		this.usersWrite = usersWrite;
	}
	
	public HashSet<String> getRolesRead() {
		return rolesRead;
	}
	
	public void setRolesRead(HashSet<String> rolesRead) {
		this.rolesRead = rolesRead;
	}
	
	public HashSet<String> getRolesWrite() {
		return rolesWrite;
	}
	
	public void setRolesWrite(HashSet<String> rolesWrite) {
		this.rolesWrite = rolesWrite;
	}

	public HashSet<String> getUsersDelete() {
		return usersDelete;
	}

	public void setUsersDelete(HashSet<String> usersDelete) {
		this.usersDelete = usersDelete;
	}

	public HashSet<String> getRolesDelete() {
		return rolesDelete;
	}

	public void setRolesDelete(HashSet<String> rolesDelete) {
		this.rolesDelete = rolesDelete;
	}

	public HashSet<String> getUsersSecurity() {
		return usersSecurity;
	}

	public void setUsersSecurity(HashSet<String> usersSecurity) {
		this.usersSecurity = usersSecurity;
	}

	public HashSet<String> getRolesSecurity() {
		return rolesSecurity;
	}

	public void setRolesSecurity(HashSet<String> rolesSecurity) {
		this.rolesSecurity = rolesSecurity;
	}
}
