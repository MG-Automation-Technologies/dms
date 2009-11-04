package es.git.openkm.bean;

public class AppVersion {
	private String major = "0";
	private String minor = "0";
	private String build = "0";
	
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}
	
	public String toString() {
		return major+"."+minor+" build-"+build;
	}
}
