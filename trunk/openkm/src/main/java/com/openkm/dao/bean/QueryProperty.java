package com.openkm.dao.bean;

import java.io.Serializable;

public class QueryProperty implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String property;
	private String value;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
