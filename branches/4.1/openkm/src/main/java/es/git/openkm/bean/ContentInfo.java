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

/**
 * @author pavila
 *
 */
public class ContentInfo implements Serializable {
	private static final long serialVersionUID = -6946496467746910033L;

	private long folders;
	private long documents;
	private long mails;
	private long size;

	public long getMails() {
		return mails;
	}

	public void setMails(long mails) {
		this.mails = mails;
	}
	
	public long getDocuments() {
		return documents;
	}

	public void setDocuments(long documents) {
		this.documents = documents;
	}

	public long getFolders() {
		return folders;
	}

	public void setFolders(long folders) {
		this.folders = folders;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("size="); sb.append(size);
		sb.append(", folders="); sb.append(folders);
		sb.append(", documents="); sb.append(documents);
		sb.append(", mails="); sb.append(mails);
		sb.append("]");
		return sb.toString();
	}
}
