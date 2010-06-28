package com.openkm.bean.form;

import java.util.ArrayList;
import java.util.List;

public class Input extends FormElement {
	private static final long serialVersionUID = 1L;
	public static final String TYPE_TEXT = "text";
	public static final String TYPE_DATE = "date";
	public static final String TYPE_LINK = "link";
	private List<Validator> validators = new ArrayList<Validator>();
	private String type = TYPE_TEXT;
	private String value = "";
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public List<Validator> getValidators() {
		return validators;
	}

	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("label="); sb.append(label);
		sb.append(", name="); sb.append(name);
		sb.append(", value="); sb.append(value);
		sb.append(", width="); sb.append(width);
		sb.append(", height="); sb.append(height);		
		sb.append(", type="); sb.append(type);
		sb.append(", validators="); sb.append(validators);
		sb.append("}");
		return sb.toString();
	}
}
