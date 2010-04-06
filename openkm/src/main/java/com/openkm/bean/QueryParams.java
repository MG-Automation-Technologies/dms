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

package com.openkm.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;

public class QueryParams implements Serializable {
	private static final long serialVersionUID = 2072424432578100142L;
	
	public static final String TYPE = "okm:query";
	public static final String LIST = "okm:queries";
	public static final String LIST_TYPE = "okm:queries";
	public static final String CONTENT = "okm:content";
	public static final String NAME = "okm:name";
	public static final String KEYWORDS = "okm:keywords";
	public static final String CATEGORIES = "okm:categories";
	public static final String MIME_TYPE = "okm:mimeType";
	public static final String AUTHOR = "okm:author";
	public static final String PATH = "okm:path";
	public static final String LAST_MODIFIED_FROM = "okm:lastModifiedFrom";
	public static final String LAST_MODIFIED_TO = "okm:lastModifiedTo";
	public static final String DASHBOARD = "okm:dashboard";
	public static final String DOMAIN = "okm:domain";
	public static final String OPERATOR = "okm:operator";
	public static final String SUBJECT = "okm:subject";
	public static final String FROM = "okm:from";
	public static final String TO = "okm:to";
	
	public static final int DOCUMENT = 1;
	public static final int FOLDER = 2;
	public static final int MAIL = 4;
	
	private String name;
	private String keywords;
	private String categories;
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
	private String operator = "and";
	private HashMap<String, String> properties;
		
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}
	
	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public HashMap<String, String> getProperties() {
		return properties;
	}
	
	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("name="); sb.append(name);
		sb.append(", keywords="); sb.append(keywords);
		sb.append(", categories="); sb.append(categories);
		sb.append(", content="); sb.append(content);
		sb.append(", mimeType="); sb.append(mimeType);
		sb.append(", author="); sb.append(author);
		sb.append(", path="); sb.append(path);
		sb.append(", dashboard="); sb.append(dashboard);
		sb.append(", subject="); sb.append(subject);
		sb.append(", from="); sb.append(from);
		sb.append(", to="); sb.append(to);
		sb.append(", domain="); sb.append(domain);
		sb.append(", operator="); sb.append(operator);
		sb.append(", lastModifiedFrom="); sb.append(lastModifiedFrom==null?null:lastModifiedFrom.getTime());
		sb.append(", lastModifiedTo="); sb.append(lastModifiedTo==null?null:lastModifiedTo.getTime());
		sb.append(", properties="); sb.append(properties);
		sb.append("}");
		return sb.toString();
	}
}
