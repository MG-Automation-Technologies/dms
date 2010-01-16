package es.git.openkm.bean.form;

public class TextArea extends FormElement {
	private String name = "";
	
	public TextArea() {
		width = "400px";
		height = "300px";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("label="); sb.append(label);
		sb.append(", value="); sb.append(value);
		sb.append(", width="); sb.append(width);
		sb.append(", height="); sb.append(height);
		sb.append(", name="); sb.append(name);
		sb.append("}");
		return sb.toString();
	}
}
