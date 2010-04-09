/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package es.git.openkm.bean;

import java.io.Serializable;

public class Bookmark implements Serializable {
	private static final long serialVersionUID = 933205631560227904L;
	
	public static final String TYPE = "okm:bookmark";
	public static final String LIST = "okm:bookmarks";
	public static final String LIST_TYPE = "okm:bookmarks";
	public static final String NODE_PATH = "okm:nodePath";
	public static final String NODE_TYPE = "okm:nodeType";
	public static final String HOME_PATH = "okm:homePath";
	public static final String HOME_TYPE = "okm:homeType";
			
	private String name;
	private String path;
	private String type;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("name="); sb.append(name);
		sb.append(", path="); sb.append(path);
		sb.append(", type="); sb.append(type);
		sb.append("]");
		return sb.toString();
	}
}
