package com.openkm.bean.form;

public class Button extends FormElement {
	private static final long serialVersionUID = 1L;
	private String transition = "";

	public String getTransition() {
		return transition;
	}

	public void setTransition(String transition) {
		this.transition = transition;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("label="); sb.append(label);
		sb.append(", name="); sb.append(name);
		sb.append(", transition="); sb.append(transition);
		sb.append(", width="); sb.append(width);
		sb.append(", height="); sb.append(height);
		sb.append("}");
		return sb.toString();
	}
}
