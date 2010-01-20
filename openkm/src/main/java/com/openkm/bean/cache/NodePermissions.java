package com.openkm.bean.cache;

import java.io.Serializable;
import java.util.HashSet;

public class NodePermissions implements Serializable {
	private static final long serialVersionUID = -895133213627179445L;
	
	private HashSet<String> usersRead;
	private HashSet<String> usersWrite;
	private HashSet<String> rolesRead;
	private HashSet<String> rolesWrite;
	
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
}
