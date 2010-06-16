package com.openkm.dao.bean;

import java.io.Serializable;

public class UserItems implements Serializable {
	private static final long serialVersionUID = 1L;
	private String user;
	private long folders;
	private long documents;
	private long mails;
	private long size;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public long getFolders() {
		return folders;
	}
	
	public void setFolders(long folders) {
		this.folders = folders;
	}
	
	public long getDocuments() {
		return documents;
	}
	
	public void setDocuments(long document) {
		this.documents = document;
	}
	
	public long getMails() {
		return mails;
	}

	public void setMails(int mails) {
		this.mails = mails;
	}

	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("user="); sb.append(user);
		sb.append(", documents="); sb.append(documents);
		sb.append(", mails="); sb.append(mails);
		sb.append(", folders="); sb.append(folders);
		sb.append(", size="); sb.append(size);
		sb.append("}");
		return sb.toString();
	}
}
