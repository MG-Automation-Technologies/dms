package com.openkm.bean.form;

import java.io.Serializable;

/**
 * http://www.javascript-coder.com/html-form/javascript-form-validation.phtml
 */
public class Validator implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String TYPE_REQUIRED = "req";
	public static final String TYPE_ALPHABETIC = "alpha";
	public static final String TYPE_DECIMAL = "dec";
	public static final String TYPE_NUMERIC = "num";
	public static final String TYPE_EMAIL = "email";
	public static final String TYPE_MAXLENGTH = "maxlen";
	public static final String TYPE_MINLENGTH = "minlen";
	public static final String TYPE_LESSTHAN = "lt";
	public static final String TYPE_GREATERTHAN= "gt";
	public static final String TYPE_REGEXP = "regexp";
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

