/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

public class Report implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String SQL = "sql";
	public static final String HIBERNATE = "hibernate";
	public static final String XPATH = "xpath";
	public static final String COLLECTION = "collection";
	public static final String SCRIPT = "script";
	private int id;
	private String name;
	private String type;
	private String fileContent;
	private String fileMime;
	private String fileName;
	private Set<ReportParameter> params = new HashSet<ReportParameter>();
	private boolean active;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	public String getFileMime() {
		return fileMime;
	}

	public void setFileMime(String fileMime) {
		this.fileMime = fileMime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Set<ReportParameter> getParams() {
		return params;
	}

	public void setParams(Set<ReportParameter> params) {
		this.params = params;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id="); sb.append(id);
		sb.append(", name="); sb.append(name);
		sb.append(", type="); sb.append(type);
		sb.append(", fileName="); sb.append(fileName);
		sb.append(", fileMime="); sb.append(fileMime);
		sb.append(", fileContent="); sb.append("[BIG]");
		sb.append(", active="); sb.append(active);
		sb.append(", params="); sb.append(params);
		sb.append("}");
		return sb.toString();
	}
}
