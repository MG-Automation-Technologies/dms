package com.openkm.bean;

public class AppVersion {
	private String major = "0";
	private String minor = "0";
	private String maintenance = "0";
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
	
	public String getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(String maintenance) {
		this.maintenance = maintenance;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}
	
	public String getVersion() {
		return major + "." + minor + "." + maintenance;
	}
	
	public String toString() {
		return major + "." + minor + "." + maintenance + " (build: " + build + ")";
	}
}
