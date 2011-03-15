package com.openkm.bean.form;

import java.util.ArrayList;
import java.util.List;

public class Select extends FormElement {
	private static final long serialVersionUID = 1L;
	public static final String TYPE_SIMPLE = "simple";
	public static final String TYPE_MULTIPLE = "multiple";
	private List<Validator> validators = new ArrayList<Validator>();
	private List<Option> options = new ArrayList<Option>();
	private String type = TYPE_SIMPLE;
	private boolean readonly = false;
	
	public Select() {
		super.width = "150px";
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		sb.append(", width="); sb.append(width);
		sb.append(", height="); sb.append(height);
		sb.append(", readonly="); sb.append(readonly);
		sb.append(", type="); sb.append(type);
		sb.append(", options="); sb.append(options);
		sb.append(", validators="); sb.append(validators);
		sb.append("}");
		return sb.toString();
	}
}
