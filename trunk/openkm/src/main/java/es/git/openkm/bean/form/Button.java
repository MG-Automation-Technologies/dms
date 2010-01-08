package es.git.openkm.bean.form;

public class Button extends Component {
	public static final String TYPE_SAVE = "save";
	public static final String TYPE_CANCEL = "cancel";
	public static final String TYPE_TRANSITION = "transition";
	private String type = TYPE_SAVE;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("type="); sb.append(type);
		sb.append(", label="); sb.append(label);
		sb.append(", name="); sb.append(name);
		sb.append(", value="); sb.append(value);
		sb.append("}");
		return sb.toString();
	}
}
