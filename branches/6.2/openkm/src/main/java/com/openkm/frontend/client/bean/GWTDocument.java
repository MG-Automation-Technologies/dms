/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2012  Paco Avila & Josep Llort
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

package com.openkm.frontend.client.bean;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * GWTDocument
 * 
 * @author jllort
 */
public class GWTDocument implements IsSerializable {
	public static final String TYPE = "okm:document";
	
	private String parentPath;
	private String name;
	private String path;
	private String parent;
	private String author;
	private byte[] content;
	private Date created;
	private Date lastModified;
	private String mimeType;
	private boolean locked;
	private boolean checkedOut;
	private GWTVersion actualVersion;
	private int permissions;
	private GWTLockInfo lockInfo;
	private boolean subscribed;
	private boolean convertibleToPdf;
	private boolean convertibleToSwf;
	private String uuid;
	private boolean isAttachment = false;
	private boolean hasNotes = false;
	private Set<GWTUser> subscriptors;
	private List<GWTNote> notes;
	private Set<GWTFolder> categories;
	private Set<String> keywords;
	private GWTUser user;
	
	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	
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
	
	public String getParent() {
		return parent;
	}
	
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public byte[] getContent() {
		return content;
	}
	
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
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
	
	public GWTVersion getActualVersion() {
		return actualVersion;
	}
	
	public void setActualVersion(GWTVersion actualVersion) {
		this.actualVersion = actualVersion;
	}
	
	public int getPermissions() {
		return permissions;
	}
	
	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
	
	public GWTLockInfo getLockInfo() {
		return lockInfo;
	}
	
	public void setLockInfo(GWTLockInfo lockInfo) {
		this.lockInfo = lockInfo;
	}
	
	public boolean isSubscribed() {
		return subscribed;
	}
	
	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
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
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public boolean isAttachment() {
		return isAttachment;
	}
	
	public void setAttachment(boolean isAttachment) {
		this.isAttachment = isAttachment;
	}
	
	public boolean isHasNotes() {
		return hasNotes;
	}
	
	public void setHasNotes(boolean hasNotes) {
		this.hasNotes = hasNotes;
	}
	
	public Set<GWTUser> getSubscriptors() {
		return subscriptors;
	}
	
	public void setSubscriptors(Set<GWTUser> subscriptors) {
		this.subscriptors = subscriptors;
	}
	
	public List<GWTNote> getNotes() {
		return notes;
	}
	
	public void setNotes(List<GWTNote> notes) {
		this.notes = notes;
	}
	
	public Set<GWTFolder> getCategories() {
		return categories;
	}
	
	public void setCategories(Set<GWTFolder> categories) {
		this.categories = categories;
	}
	
	public Set<String> getKeywords() {
		return keywords;
	}
	
	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}
	
	public GWTUser getUser() {
		return user;
	}

	public void setUser(GWTUser user) {
		this.user = user;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("path="); sb.append(path);
		//sb.append(", title="); sb.append(title);
		// sb.append(", description="); sb.append(description);
		sb.append(", mimeType="); sb.append(mimeType);
		sb.append(", author="); sb.append(author);
		sb.append(", permissions="); sb.append(permissions);
		sb.append(", created="); sb.append(created==null?null:created.getTime());
		sb.append(", lastModified="); sb.append(lastModified==null?null:lastModified.getTime());
		sb.append(", keywords="); sb.append(keywords);
		sb.append(", categories="); sb.append(categories);
		sb.append(", locked="); sb.append(locked);
		sb.append(", lockInfo="); sb.append(lockInfo);
		sb.append(", actualVersion="); sb.append(actualVersion);
		sb.append(", subscribed="); sb.append(subscribed);
		sb.append(", uuid="); sb.append(uuid);
		sb.append(", convertibleToPdf="); sb.append(convertibleToPdf);
		sb.append(", convertibleToSwf="); sb.append(convertibleToSwf);
		sb.append(", notes="); sb.append(notes);
		sb.append(", user="); sb.append(user.getId());
		sb.append(", username="); sb.append(user.getUsername());
		sb.append("}");
		return sb.toString();
	}
}