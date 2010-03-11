package com.openkm.bean.form;

public class Button extends FormElement {
	public static final String TYPE_SUBMIT = "submit";
	public static final String TYPE_TRANSITION = "transition";
	private String type = TYPE_SUBMIT;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		sb.append("}");
		return sb.toString();
	}
}
