package com.openkm.dao.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class QueryParams implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int DOCUMENT = 1;
	public static final int FOLDER = 2;
	public static final int MAIL = 4;
	
	public static final String AND = "and";
	public static final String OR = "or";
	
	private int id;
	private String queryName;
	private String user;
	private String name;
	private Set<String> keywords = new HashSet<String>();
	private Set<String> categories = new HashSet<String>();
	private String content;
	private String mimeType;
	private String author;
	private String path;
	private Calendar lastModifiedFrom;
	private Calendar lastModifiedTo;
	private String subject;
	private String from;
	private String to;
	private boolean dashboard;
	private long domain = DOCUMENT;
	private String operator = AND;
	private Map<String, String> properties = new HashMap<String, String>();
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getQueryName() {
		return queryName;
	}
	
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<String> getKeywords() {
		return keywords;
	}
	
	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}
	
	public Set<String> getCategories() {
		return categories;
	}
	
	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public Calendar getLastModifiedFrom() {
		return lastModifiedFrom;
	}
	
	public void setLastModifiedFrom(Calendar lastModifiedFrom) {
		this.lastModifiedFrom = lastModifiedFrom;
	}
	
	public Calendar getLastModifiedTo() {
		return lastModifiedTo;
	}
	
	public void setLastModifiedTo(Calendar lastModifiedTo) {
		this.lastModifiedTo = lastModifiedTo;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public boolean isDashboard() {
		return dashboard;
	}
	
	public void setDashboard(boolean dashboard) {
		this.dashboard = dashboard;
	}
	
	public long getDomain() {
		return domain;
	}
	
	public void setDomain(long domain) {
		this.domain = domain;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id="); sb.append(id);
		sb.append(", queryName="); sb.append(queryName);
		sb.append(", user="); sb.append(user);
		sb.append(", name="); sb.append(name);
		sb.append(", keywords="); sb.append(keywords);
		sb.append(", categories="); sb.append(categories);
		sb.append(", content="); sb.append(content);
		sb.append(", mimeType="); sb.append(mimeType);
		sb.append(", author="); sb.append(author);
		sb.append(", path="); sb.append(path);
		sb.append(", dashboard="); sb.append(dashboard);
		sb.append(", domain="); sb.append(domain);
		sb.append(", operator="); sb.append(operator);
		sb.append("}");
		return sb.toString();
	}
}
