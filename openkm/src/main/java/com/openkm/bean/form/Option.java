package com.openkm.bean.form;

import java.io.Serializable;

public class Option implements Serializable {
	private static final long serialVersionUID = 1L;
	private String label = "";
	private String value = "";
	private boolean selected = false;
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("label="); sb.append(label);
		sb.append(", value="); sb.append(value);
		sb.append(", selected="); sb.append(selected);
		sb.append("}");
		return sb.toString();
	}
}

