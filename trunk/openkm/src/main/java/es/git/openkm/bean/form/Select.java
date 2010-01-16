package es.git.openkm.bean.form;

import java.util.ArrayList;
import java.util.Collection;

public class Select extends FormElement {
	public static final String TYPE_SIMPLE = "simple";
	public static final String TYPE_MULTIPLE = "multiple";
	private Collection<Option> options = new ArrayList<Option>();
	private String name = "";
	private String type = TYPE_SIMPLE;
	
	public Select() {
		super.width = "150px";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("label="); sb.append(label);
		sb.append(", value="); sb.append(value);
		sb.append(", width="); sb.append(width);
		sb.append(", height="); sb.append(height);
		sb.append(", name="); sb.append(name);
		sb.append(", type="); sb.append(type);
		sb.append(", options="); sb.append(options);
		sb.append("}");
		return sb.toString();
	}
}
