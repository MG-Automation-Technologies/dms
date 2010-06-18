package com.openkm.bean.form;

import java.io.Serializable;

public class FormElement implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String label = "";
	protected String name = "";
	protected String width = "100px";
	protected String height = "25px";
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}
