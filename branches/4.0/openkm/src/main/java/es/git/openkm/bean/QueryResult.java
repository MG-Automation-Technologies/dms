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

package es.git.openkm.bean;

import java.io.Serializable;

public class QueryResult implements Serializable {

	private static final long serialVersionUID = -6397099389620834328L;
	private Document document;
	private Folder folder;
	private Mail mail;
	private Document attachment;
	private long score;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public Document getAttachment() {
		return attachment;
	}

	public void setAttachment(Document attachment) {
		this.attachment = attachment;
	}
	
	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("document="); sb.append(document);
		sb.append(", folder="); sb.append(folder);
		sb.append(", mail="); sb.append(mail);
		sb.append(", attachment="); sb.append(attachment);
		sb.append(", score="); sb.append(score);
		sb.append("]");
		return sb.toString();
	}
}