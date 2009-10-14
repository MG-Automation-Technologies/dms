package es.git.openkm.bean.cache;

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
	
	public void incFolder() {
		this.folders += 1;
	}
	
	public void decFolder() {
		this.folders -= 1;
	}
	
	public long getDocuments() {
		return documents;
	}
	
	public void setDocument(long document) {
		this.documents = document;
	}

	public void incDocument() {
		this.documents += 1;
	}
	
	public void decDocument() {
		this.documents -= 1;
	}

	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	
	public void incSize(long num) {
		this.size += num;
	}
	
	public void decSize(long num) {
		this.size -= num;
	}
}
