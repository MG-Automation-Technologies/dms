package com.openkm.dao.bean;

import java.io.Serializable;

public class LockToken implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String user;
	private String token;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}
