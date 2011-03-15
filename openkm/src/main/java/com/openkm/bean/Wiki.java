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
import java.util.HashSet;
import java.util.Set;

public class Wiki implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String TYPE = "okm:wiki";
	public static final String CONTENT = "okm:content";
	public static final String CONTENT_TYPE = "okm:resource";
	public static final String SIZE = "okm:size";
	public static final String MIX_TYPE = "mix:wiki";
	public static final String AUTHOR = "okm:author";
	public static final String VERSION_COMMENT = "okm:versionComment";
	public static final String NAME = "okm:name";
	
	private String path;
	private String author;
	private Calendar created;
	private Calendar lastModified;
	private boolean locked;
	private boolean checkedOut;
	private Version actualVersion;
	private byte permissions;
	private Lock lockInfo;
	private String uuid;
	private boolean subscribed;
	private Set<String> subscriptors = new HashSet<String>();
	private Set<String> keywords = new HashSet<String>();
	private Set<Folder> categories = new HashSet<Folder>();

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
	
	public Set<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}
	
	public Set<String> getSubscriptors() {
		return subscriptors;
	}

	public void setSubscriptors(Set<String> subscriptors) {
		this.subscriptors = subscriptors;
	}
	
	public Set<Folder> getCategories() {
		return categories;
	}

	public void setCategories(Set<Folder> categories) {
		this.categories = categories;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Calendar getLastModified() {
		return lastModified;
	}

	public void setLastModified(Calendar lastModified) {
		this.lastModified = lastModified;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	public Version getActualVersion() {
		return actualVersion;
	}

	public void setActualVersion(Version actualVersion) {
		this.actualVersion = actualVersion;
	}

	public byte getPermissions() {
		return permissions;
	}

	public void setPermissions(byte permissions) {
		this.permissions = permissions;
	}

	public Lock getLockInfo() {
		return lockInfo;
	}

	public void setLockInfo(Lock lockInfo) {
		this.lockInfo = lockInfo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		//sb.append("date="); sb.append(date==null?null:date.getTime());
		sb.append(", author="); sb.append(author);
		sb.append(", path="); sb.append(path);
		sb.append("}");
		return sb.toString();
	}
}
