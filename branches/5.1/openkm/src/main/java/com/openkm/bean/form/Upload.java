package com.openkm.bean.form;

import java.util.ArrayList;
import java.util.List;

public class Upload extends FormElement {
	private static final long serialVersionUID = 1L;
	private List<Validator> validators = new ArrayList<Validator>();
	private String folderPath = "";
	private String documentName = "";
	private String uploadUuid = "";
	
	public List<Validator> getValidators() {
		return validators;
	}

	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	public String getUploadUuid() {
		return uploadUuid;
	}

	public void setUploadUuid(String uploadUuid) {
		this.uploadUuid = uploadUuid;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("label="); sb.append(label);
		sb.append(", name="); sb.append(name);
		sb.append(", width="); sb.append(width);
		sb.append(", height="); sb.append(height);
		sb.append(", folderPath="); sb.append(folderPath);
		sb.append(", documentName="); sb.append(documentName);
		sb.append(", uploadUuid="); sb.append(uploadUuid);
		sb.append(", validators="); sb.append(validators);
		sb.append("}");
		return sb.toString();
	}
}
