package es.git.openkm.bean.form;

public class Input extends FormElement {
	public static final String TYPE_TEXT = "text";
	public static final String TYPE_DATE = "date";
	private String name = "";
	private String type = TYPE_TEXT;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
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
		sb.append(", value="); sb.append(value);
		sb.append(", width="); sb.append(width);
		sb.append(", height="); sb.append(height);
		sb.append(", name="); sb.append(name);
		sb.append(", type="); sb.append(type);
		sb.append("}");
		return sb.toString();
	}
}
