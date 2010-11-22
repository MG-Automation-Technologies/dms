package com.openkm.bean.form;

import java.util.ArrayList;
import java.util.List;

public class CheckBox extends FormElement {
	private static final long serialVersionUID = 1L;
	private List<Validator> validators = new ArrayList<Validator>();
	private boolean value = false;
	private boolean readonly = false;
	
	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
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
