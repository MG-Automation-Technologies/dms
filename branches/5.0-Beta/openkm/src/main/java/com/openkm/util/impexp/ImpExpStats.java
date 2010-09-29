package com.openkm.util.impexp;

public class ImpExpStats {
	private boolean ok = true;
	private long documents;
	private long folders;
	private long size;
	
	public boolean isOk() {
		return ok;
	}
	
	public void setOk(boolean ok) {
		this.ok = ok;
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
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("documents="); sb.append(documents);
		sb.append(", folders="); sb.append(folders);
		sb.append(", size="); sb.append(size);
		sb.append(", ok="); sb.append(ok);
		sb.append("}");
		return sb.toString();
	}
}
