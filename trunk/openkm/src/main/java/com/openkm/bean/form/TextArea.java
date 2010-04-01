package com.openkm.bean.form;

public class TextArea extends FormElement {
	private String value = "";
	
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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("label="); sb.append(label);
		sb.append(", name="); sb.append(name);
		sb.append(", value="); sb.append(value);
		sb.append(", width="); sb.append(width);
		sb.append(", height="); sb.append(height);
		sb.append("}");
		return sb.toString();
	}
}
