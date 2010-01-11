package es.git.openkm.bean.form;

import java.util.ArrayList;
import java.util.Collection;

public class Select extends FormField {
	public static final String TYPE_SIMPLE = "simple";
	public static final String TYPE_MULTIPLE = "multiple";
	private Collection<Option> options = new ArrayList<Option>();
	private String type = TYPE_SIMPLE;
	private String size = "1";

	public Collection<Option> getOptions() {
		return options;
	}

	public void setOptions(Collection<Option> options) {
		this.options = options;
	}

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
		sb.append(", options="); sb.append(options);
		sb.append(", label="); sb.append(label);
		sb.append(", name="); sb.append(name);
		sb.append(", value="); sb.append(value);
		sb.append("}");
		return sb.toString();
	}
}
