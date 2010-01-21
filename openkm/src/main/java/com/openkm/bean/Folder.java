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
import java.util.Collection;

/**
 * @author pavila
 *
 */
public class Folder implements Serializable {
	private static final long serialVersionUID = -2927429339076624036L;
	
	public static final String TYPE = "okm:folder";
	public static final String AUTHOR = "okm:author";
	public static final String NAME = "okm:name";
	
	private Calendar created;
	private boolean hasChilds;
	private String path;
	private String author;
	private byte permissions;
	private String uuid;
	private boolean subscribed;
	private Collection<String> subscriptors;
	
	public boolean getHasChilds() {
		return hasChilds;
	}

	public void setHasChilds(boolean hasChilds) {
		this.hasChilds = hasChilds;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public byte getPermissions() {
		return permissions;
	}

	public void setPermissions(byte permissions) {
		this.permissions = permissions;
	}
	
	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	public Collection<String> getSubscriptors() {
		return subscriptors;
	}

	public void setSubscriptors(Collection<String> subscriptors) {
		this.subscriptors = subscriptors;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("path="); sb.append(path);
		sb.append(", permissions="); sb.append(permissions);
		sb.append(", created="); sb.append(created==null?null:created.getTime());
		sb.append(", hasChilds="); sb.append(hasChilds);
		sb.append(", subscribed="); sb.append(subscribed);
		sb.append(", uuid="); sb.append(uuid);
		sb.append("]");
		return sb.toString();
	}
}
