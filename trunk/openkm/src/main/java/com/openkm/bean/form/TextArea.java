package com.openkm.bean.form;

import java.util.ArrayList;
import java.util.List;

public class TextArea extends FormElement {
	private static final long serialVersionUID = 1L;
	private List<Validator> validators = new ArrayList<Validator>();
	private String value = "";
	private boolean readonly = false;
	
	public TextArea() {
		width = "300px";
		height = "100px";
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
	
	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("label="); sb.append(label);
		sb.append(", name="); sb.append(name);
		sb.append(", value="); sb.append(value);
		sb.append(", width="); sb.append(width);
		sb.append(", height="); sb.append(height);
		sb.append(", readonly="); sb.append(readonly);
		sb.append(", validators="); sb.append(validators);
		sb.append("}");
		return sb.toString();
	}
}
