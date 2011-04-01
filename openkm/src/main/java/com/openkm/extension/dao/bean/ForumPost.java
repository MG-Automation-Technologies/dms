package com.openkm.extension.dao.bean;

import java.io.Serializable;
import java.util.Calendar;

public class ForumPost implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String user;
	private String subject;
	private String message;
	private Calendar date;
	
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
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
}
