package es.git.openkm.bean.form;

public class Input extends FormField {
	public static final String TYPE_TEXT = "text";
	public static final String TYPE_DATE = "date";
	private String type = TYPE_TEXT;
	private String size = "20";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("type="); sb.append(type);
		sb.append(", size="); sb.append(size);
		sb.append(", label="); sb.append(label);
		sb.append(", name="); sb.append(name);
		sb.append(", value="); sb.append(value);
		sb.append("}");
		return sb.toString();
	}
}
