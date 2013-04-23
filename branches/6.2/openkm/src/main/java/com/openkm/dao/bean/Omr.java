package com.openkm.dao.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@Table(name = "OKM_OMR")
public class Omr implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="OM_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
		
	@Column(name="OM_NAME", length=128, unique=true)
	private String name;
		
	@Column(name="OM_TEMPLATE_FILE_CONTENT")
	@Lob @Type(type = "org.hibernate.type.TextType")
	private String templateFileContent;
	
	@Column(name="OM_ASC_FILE_CONTENT")
	@Lob @Type(type = "org.hibernate.type.TextType")
	private String ascFileContent;
	
	@Column(name="OM_CONFIG_FILE_CONTENT")
	@Lob @Type(type = "org.hibernate.type.TextType")
	private String configFileContent;
	
	@Column(name="OM_FIELDS_FILE_CONTENT")
	@Lob @Type(type = "org.hibernate.type.TextType")
	private String fieldsFileContent;
	
	@Column(name="OM_FILE_TEMPLATE_MIME", length=32)
	private String templateFileMime;
	
	@Column(name="OM_ASC_TEMPLATE_MIME", length=32)
	private String ascFileMime;
	
	@Column(name="OM_CONFIG_TEMPLATE_MIME", length=32)
	private String configFileMime;
	
	@Column(name="OM_FIELDS_TEMPLATE_MIME", length=32)
	private String fieldsFileMime;
		
	private String templateFileName;
	private String ascFileName;
	private String configFileName;
	private String fieldsFileName;
	private boolean active;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateFileContent() {
		return templateFileContent;
	}

	public void setTemplateFilContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}

	public String getTemplateFileMime() {
		return templateFileMime;
	}

	public void setTemplateFileMime(String fileTemplateMime) {
		this.templateFileMime = fileTemplateMime;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getAscFileContent() {
		return ascFileContent;
	}

	public void setAscFileContent(String ascFileContent) {
		this.ascFileContent = ascFileContent;
	}

	public String getConfigFileContent() {
		return configFileContent;
	}

	public void setConfigFileContent(String configFileContent) {
		this.configFileContent = configFileContent;
	}

	public String getFieldsFileContent() {
		return fieldsFileContent;
	}

	public void setFieldsFileContent(String fieldsFileContent) {
		this.fieldsFileContent = fieldsFileContent;
	}

	public String getAscFileMime() {
		return ascFileMime;
	}

	public void setAscFileMime(String ascFileMime) {
		this.ascFileMime = ascFileMime;
	}

	public String getConfigFileMime() {
		return configFileMime;
	}

	public void setConfigFileMime(String configFileMime) {
		this.configFileMime = configFileMime;
	}

	public String getFieldsFileMime() {
		return fieldsFileMime;
	}

	public void setFieldsFileMime(String fieldsFileMime) {
		this.fieldsFileMime = fieldsFileMime;
	}

	public String getAscFileName() {
		return ascFileName;
	}

	public void setAscFileName(String ascFileName) {
		this.ascFileName = ascFileName;
	}

	public String getConfigFileName() {
		return configFileName;
	}

	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}

	public String getFieldsFileName() {
		return fieldsFileName;
	}

	public void setFieldsFileName(String fieldsFileName) {
		this.fieldsFileName = fieldsFileName;
	}

	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id=").append(id);
		sb.append(", name=").append(name);
		sb.append(", templateFileName=").append(templateFileName);
		sb.append(", templateFileName=").append(templateFileName);
		sb.append(", templateFileContent=").append("[BIG]");
		sb.append(", ascFileName=").append(ascFileName);
		sb.append(", ascFileName=").append(ascFileName);
		sb.append(", ascFileContent=").append("[BIG]");
		sb.append(", configFileName=").append(configFileName);
		sb.append(", configFileName=").append(configFileName);
		sb.append(", configFileContent=").append("[BIG]");
		sb.append(", fieldsFileName=").append(fieldsFileName);
		sb.append(", fieldsFileName=").append(fieldsFileName);
		sb.append(", fieldsFileContent=").append("[BIG]");
		sb.append(", active=").append(active);
		sb.append("}");
		return sb.toString();
	}
}