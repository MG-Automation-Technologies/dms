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

package es.git.openkm.frontend.client.bean;

import java.util.Collection;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * GWTDocument
 * 
 * @author jllort
 *
 */
public class GWTDocument implements IsSerializable {
	
	private String parentId;
	private String name;
	private String path;
	private String parent;
	private String author;
	private byte[] content;
	private Date created;
	private String keywords = "";
	private Date lastModified;
	private String mimeType;
	private boolean locked;
	private boolean checkedOut;
	private GWTVersion actualVersion;
	private byte permissions;
	private GWTLock lockInfo;
	private boolean subscribed;
	private boolean convertibleToPdf;
	private boolean convertibleToSwf;
	private String uuid;
	private boolean isAttachment = false;
	private boolean hasNotes = false;

	private Collection<String> subscriptors;
	private Collection<GWTNote> notes;
	
	/**
	 * @return Returns the subscribed.
	 */
	public boolean isSubscribed() {
		return subscribed;
	}

	/**
	 * @param subscribed The subscribed to set.
	 */
	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
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
	
	public void setActualVersion (GWTVersion version) {
		this.actualVersion = version;
	}
	
	public GWTVersion getActualVersion(){
		return this.actualVersion;
	}
	
	public byte getPermissions() {
		return permissions;
	}

	public void setPermissions(byte permissions) {
		this.permissions = permissions;
	}
	
	public GWTLock getLockInfo() {
		return lockInfo;
	}
	
	public void setLockInfo(GWTLock lockInfo) {
		this.lockInfo = lockInfo;
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
	
	/**
	 * @return Returns the subscriptors.
	 */
	public Collection<String> getSubscriptors() {
		return subscriptors;
	}

	/**
	 * @param subscriptors The subscriptors to set.
	 */
	public void setSubscriptors(Collection<String> subscriptors) {
		this.subscriptors = subscriptors;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Collection<GWTNote> getNotes() {
		return notes;
	}

	public void setNotes(Collection<GWTNote> notes) {
		this.notes = notes;
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
}
