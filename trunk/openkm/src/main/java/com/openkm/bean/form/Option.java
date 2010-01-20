package com.openkm.bean.form;

public class Option {
	private String name = "";
	private String value = "";
	private boolean selected = false;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
		sb.append("name="); sb.append(name);
		sb.append(", value="); sb.append(value);
		sb.append(", selected="); sb.append(selected);
		sb.append("}");
		return sb.toString();
	}
}
