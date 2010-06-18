package com.openkm.bean.form;

public class Validator {
	private String type = "";
	private String parameter = "";
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getParameter() {
		return parameter;
	}
	
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("type="); sb.append(type);
		sb.append(", parameter="); sb.append(parameter);
		sb.append("}");
		return sb.toString();
	}
}

