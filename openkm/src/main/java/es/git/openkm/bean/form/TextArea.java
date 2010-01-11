package es.git.openkm.bean.form;

public class TextArea extends FormField {
	private String cols = "20";
	private String rows = "2";

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("cols="); sb.append(cols);
		sb.append(", rows="); sb.append(rows);
		sb.append(", label="); sb.append(label);
		sb.append(", name="); sb.append(name);
		sb.append(", value="); sb.append(value);
		sb.append("}");
		return sb.toString();
	}
}
