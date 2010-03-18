/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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

package es.git.openkm.frontend.client.bean;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Query params
 * 
 * @author jllort
 *
 */
public class GWTQueryParams implements IsSerializable {
	
	public static final int DOCUMENT = 1;
	public static final int FOLDER = 2;
	public static final int MAIL = 4;
	public static final String OPERATOR_AND = "and";
	public static final String OPERATOR_OR = "or";
 
	private String name;
	private String keywords;
	private String content;
	private String path;
	private String mimeType;
	private String author;
	private Date lastModifiedFrom;
	private Date lastModifiedTo;
	private boolean isDashboard = false;
	private long domain = 0;
	private String from = "";
	private String to = "";
	private String subject = "";
	private String operator = OPERATOR_AND;
	
	private HashMap<String, GWTPropertyParams> properties;
	private HashMap<String, String> searchProperties;
	
	private String grpName;

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
	
	public HashMap<String, GWTPropertyParams> getProperties() {
		return properties;
	}
	
	public void setProperties(HashMap<String, GWTPropertyParams> finalProperties) {
		this.properties = finalProperties;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getLastModifiedFrom() {
		return lastModifiedFrom;
	}

	public void setLastModifiedFrom(Date lastModifiedFrom) {
		this.lastModifiedFrom = lastModifiedFrom;
	}

	public Date getLastModifiedTo() {
		return lastModifiedTo;
	}

	public void setLastModifiedTo(Date lastModifiedTo) {
		this.lastModifiedTo = lastModifiedTo;
	}

	public HashMap<String, String> getSearchProperties() {
		return searchProperties;
	}

	public void setSearchProperties(HashMap<String, String> searchProperties) {
		this.searchProperties = searchProperties;
	}

	public boolean isDashboard() {
		return isDashboard;
	}

	public void setDashboard(boolean isDashboard) {
		this.isDashboard = isDashboard;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("name="); sb.append(name);
		sb.append(", keywords="); sb.append(keywords);
		sb.append(", content="); sb.append(content);
		sb.append(", path="); sb.append(path);
		sb.append(", mimeType="); sb.append(mimeType);
		sb.append(", author="); sb.append(author);
		sb.append(", isDashboard="+isDashboard);
		sb.append(", lastModifiedFrom="); sb.append(lastModifiedFrom==null?null:lastModifiedFrom.getTime());
		sb.append(", lastModifiedTo="); sb.append(lastModifiedTo==null?null:lastModifiedTo.getTime());
		sb.append(", properties="); sb.append(properties);
		sb.append("]");
		return sb.toString();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getDomain() {
		return domain;
	}

	public void setDomain(long domain) {
		this.domain = domain;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
