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
import java.util.Calendar;
import java.util.Collection;

/**
 * @author pavila
 * 
 */
public class Document implements Serializable {
	private static final long serialVersionUID = 4453338766237619444L;
	
	public static final String TYPE = "okm:document";
	public static final String CONTENT = "okm:content"; 
	public static final String CONTENT_TYPE = "okm:resource";
	public static final String SIZE = "okm:size";
	public static final String LANGUAGE = "okm:language";
	public static final String AUTHOR = "okm:author";
	public static final String KEYWORDS = "okm:keywords";
	public static final String VERSION_COMMENT = "okm:versionComment";
	public static final String NAME = "okm:name";
	
	private String path;
	private String language = "";
	private String author;
	private Calendar created;
	private String keywords = "";
	private Calendar lastModified;
	private String mimeType;
	private boolean locked;
	private boolean checkedOut;
	private Version actualVersion;
	private byte permissions;
	private Lock lockInfo;
	private String uuid;
	private boolean subscribed;
	private boolean convertibleToPdf;
	private boolean convertibleToSwf;
	private Collection<String> subscriptors;
	private Collection<Note> notes;

	public Lock getLockInfo() {
		return lockInfo;
	}

	public void setLockInfo(Lock lockInfo) {
		this.lockInfo = lockInfo;
	}

	public Calendar getLastModified() {
		return lastModified;
	}

	public void setLastModified(Calendar lastModified) {
		this.lastModified = lastModified;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public boolean isCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
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

	public boolean isConvertibleToPdf() {
		return convertibleToPdf;
	}

	public void setConvertibleToPdf(boolean convertibleToPdf) {
		this.convertibleToPdf = convertibleToPdf;
	}

	public boolean isConvertibleToSwf() {
		return convertibleToSwf;
	}

	public void setConvertibleToSwf(boolean convertibleToSwf) {
		this.convertibleToSwf = convertibleToSwf;
	}
	
	public Collection<Note> getNotes() {
		return notes;
	}

	public void setNotes(Collection<Note> notes) {
		this.notes = notes;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("path="); sb.append(path);
		sb.append(", mimeType="); sb.append(mimeType);
		sb.append(", author="); sb.append(author);
		sb.append(", permissions="); sb.append(permissions);
		sb.append(", created="); sb.append(created==null?null:created.getTime());
		sb.append(", lastModified="); sb.append(lastModified==null?null:lastModified.getTime());
		sb.append(", keywords="); sb.append(keywords);
		sb.append(", locked="); sb.append(locked);
		sb.append(", lockInfo="); sb.append(lockInfo);
		sb.append(", actualVersion="); sb.append(actualVersion);
		sb.append(", subscribed="); sb.append(subscribed);
		sb.append(", uuid="); sb.append(uuid);
		sb.append(", convertibleToPdf="); sb.append(convertibleToPdf);
		sb.append(", convertibleToSwf="); sb.append(convertibleToSwf);
		sb.append(", notes="); sb.append(notes);
		sb.append("]");
		return sb.toString();
	}
}
