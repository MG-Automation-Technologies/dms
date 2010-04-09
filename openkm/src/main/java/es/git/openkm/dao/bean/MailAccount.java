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

package es.git.openkm.dao.bean;


public class MailAccount {
	private int id = -1;
	private String user = "";
	private String mailHost = "";
	private String mailFolder = "";
	private String mailUser = "";
	private String mailPassword = "";
	private boolean active = false;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public String getMailFolder() {
		return mailFolder;
	}

	public void setMailFolder(String mailFolder) {
		this.mailFolder = mailFolder;
	}
	
	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}
	
	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("id="); sb.append(id);
		sb.append(", user="); sb.append(user);
		sb.append(", mailHost="); sb.append(mailHost);
		sb.append(", mailFolder="); sb.append(mailFolder);
		sb.append(", mailUser="); sb.append(mailUser);
		sb.append(", mailPassword="); sb.append(mailPassword);
		sb.append(", active="); sb.append(active);
		sb.append("]");
		return sb.toString();
	}
}
