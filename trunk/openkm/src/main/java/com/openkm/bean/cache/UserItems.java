package com.openkm.bean.cache;

import java.io.Serializable;

public class UserItems implements Serializable {
	private static final long serialVersionUID = 7194648651169891142L;
	
	private long folders;
	private long documents;
	private long size;
	
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
		sb.append(", documents="); sb.append(documents);
		sb.append(", folders="); sb.append(folders);
		sb.append("]");
		return sb.toString();
	}
}
