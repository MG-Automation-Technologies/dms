package es.git.openkm.util.impexp;

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
}
