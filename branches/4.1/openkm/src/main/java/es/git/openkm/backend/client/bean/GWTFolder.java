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

package es.git.openkm.backend.client.bean;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Collection;
import java.util.Date;

/**
 * @author jllort
 *
 */
public class GWTFolder implements IsSerializable {

	private String parentPath;
	private String path;
	private String name;
	private boolean hasChilds;
	private Date created;
	private String author;
	private byte permissions;
	private boolean subscribed;
	
	private Collection<String> subscriptors;
	
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

	public boolean getHasChilds() {
		return hasChilds;
	}

	public void setHasChilds(boolean parent) {
		this.hasChilds = parent;
	}
	
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
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getCreated() {
		return created;
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
	
	public String toString() {
		return "[path="+path+", name="+name+", hasChilds="+hasChilds+"]";
	}

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
}