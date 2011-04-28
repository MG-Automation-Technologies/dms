/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.openkm.bean.PropertyGroup;
import com.openkm.bean.form.Button;
import com.openkm.bean.form.CheckBox;
import com.openkm.bean.form.FormElement;
import com.openkm.bean.form.Input;
import com.openkm.bean.form.Option;
import com.openkm.bean.form.Select;
import com.openkm.bean.form.TextArea;
import com.openkm.bean.form.Validator;
import com.openkm.core.ParseException;

public class FormUtils {
	private static Logger log = LoggerFactory.getLogger(FormUtils.class);
	private static Map<PropertyGroup, List<FormElement>> pGroups = null;
	
	/**
	 * Parse form.xml definitions
	 * 
	 * @return A Map with all the forms and its form elements.
	 */
	public static Map<String, List<FormElement>> parseWorkflowForms(InputStream is) throws ParseException {
		log.debug("parseWorkflowForms({})", is);
		// long begin = Calendar.getInstance().getTimeInMillis();
		Map<String, List<FormElement>> forms = new HashMap<String, List<FormElement>>();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			dbf.setValidating(true);
			ErrorHandler handler = new ErrorHandler();
			DocumentBuilder db = dbf.newDocumentBuilder();
			db.setErrorHandler(handler);

			if (is != null) {
				Document doc = db.parse(is);
				doc.getDocumentElement().normalize();
				NodeList nlForm = doc.getElementsByTagName("workflow-form");

				for (int i = 0; i < nlForm.getLength(); i++) {
					Node nForm = nlForm.item(i);

					if (nForm.getNodeType() == Node.ELEMENT_NODE) {
						String taskName = nForm.getAttributes().getNamedItem("task").getNodeValue();
						NodeList nlField = nForm.getChildNodes();
						List<FormElement> fe = parseField(nlField);
						forms.put(taskName, fe);
					}
				}

				is.close();
			}
		} catch (ParserConfigurationException e) {
			throw new ParseException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new ParseException(e.getMessage(), e);
		} catch (IOException e) {
			throw new ParseException(e.getCause());
		}

		log.debug("parseWorkflowForms: {}", forms);
		// log.info("Time: "+(Calendar.getInstance().getTimeInMillis()-begin)+" ms");
		return forms;
	}

	/**
	 * Parse PropertyGroups.xml definitions
	 * 
	 * @return A Map with all the forms and its form elements.
	 */
	public static synchronized Map<PropertyGroup, List<FormElement>> parsePropertyGroupsForms(String pgFile) 
			throws IOException,	ParseException {
		log.debug("parseMetadataForms()");
		// long begin = Calendar.getInstance().getTimeInMillis();
		if (pGroups == null) {
			log.debug("PropertyGroupForms: {}", pgFile);
			pGroups = new HashMap<PropertyGroup, List<FormElement>>();
			FileInputStream fis = null;
			
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setNamespaceAware(true);
				dbf.setValidating(true);
				ErrorHandler handler = new ErrorHandler();
				DocumentBuilder db = dbf.newDocumentBuilder();
				db.setErrorHandler(handler);
				fis = new FileInputStream(pgFile);
				
				if (fis != null) {
					Document doc = db.parse(fis);
					doc.getDocumentElement().normalize();
					NodeList nlForm = doc.getElementsByTagName("property-group");
					
					for (int i = 0; i < nlForm.getLength(); i++) {
						Node nForm = nlForm.item(i);
						
						if (nForm.getNodeType() == Node.ELEMENT_NODE) {
							String pgLabel = nForm.getAttributes().getNamedItem("label").getNodeValue();
							String pgName = nForm.getAttributes().getNamedItem("name").getNodeValue();
							String pgVisible = nForm.getAttributes().getNamedItem("visible").getNodeValue();
							NodeList nlField = nForm.getChildNodes();
							List<FormElement> fe = parseField(nlField);
							PropertyGroup pg = new PropertyGroup();
							pg.setLabel(pgLabel);
							pg.setName(pgName);
							pg.setVisible(Boolean.valueOf(pgVisible));
							pGroups.put(pg, fe);
						}
					}
				}
			} catch (ParserConfigurationException e) {
				throw new ParseException(e.getMessage());
			} catch (SAXException e) {
				throw new ParseException(e.getMessage());
			} catch (IOException e) {
				throw e;
			} finally {
				IOUtils.closeQuietly(fis);
			}
		}
		
		log.debug("parseMetadataForms: {}", pGroups);
		// log.info("Time: "+(Calendar.getInstance().getTimeInMillis()-begin)+" ms");
		return clonedPropertyGroups();
	}
	
	/**
	 * Clone to be modified
	 */
	@SuppressWarnings("unchecked")
	private static Map<PropertyGroup, List<FormElement>> clonedPropertyGroups() throws IOException {
		try {
			return (Map<PropertyGroup, List<FormElement>>) Serializer.read(Serializer.write(pGroups));
		} catch (ClassNotFoundException e) {
			throw new IOException("ClassNotFoundException", e);
		}
	}

	/**
	 * Force PropertyGroups.xml re-read in the next petition.
	 */
	public static synchronized void resetPropertyGroupsForms() {
		pGroups = null;
	}

	/**
	 * Retrieve the form elements from a PropertyGroup definition. 
	 */
	public static List<FormElement> getPropertyGroupForms(Map<PropertyGroup, List<FormElement>> formsElements, String groupName) {
		for (Iterator<Entry<PropertyGroup, List<FormElement>>> it1 = formsElements.entrySet().iterator(); it1.hasNext(); ) {
			Entry<PropertyGroup, List<FormElement>> entry = it1.next();
			if (entry.getKey().getName().equals(groupName)) {
				return entry.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * Retrieve the form element from a PropertyGroups definition. 
	 */
	public static FormElement getFormElement(Map<PropertyGroup, List<FormElement>> formsElements, String propertyName) {
		for (Iterator<Entry<PropertyGroup, List<FormElement>>> it1 = formsElements.entrySet().iterator(); it1.hasNext(); ) {
			Entry<PropertyGroup, List<FormElement>> entry = it1.next();
			for (Iterator<FormElement> it2 = entry.getValue().iterator(); it2.hasNext(); ) {
				FormElement fe = it2.next();
				if (fe.getName().equals(propertyName)) {
					return fe;
				}
			}
		}
		
		return null;
	}

	/**
	 * Parse individual form fields 
	 */
	private static List<FormElement> parseField(NodeList nlField) {
		List<FormElement> fe = new ArrayList<FormElement>();
		
		for (int j = 0; j < nlField.getLength(); j++) {
			Node nField = nlField.item(j);
		
			if (nField.getNodeType() == Node.ELEMENT_NODE) {
				String fieldComponent = nField.getNodeName();

				if (fieldComponent.equals("input")) {
					Input input = new Input();
					Node item = nField.getAttributes().getNamedItem("label");
					if (item != null) input.setLabel(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("name");
					if (item != null) input.setName(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("type");
					if (item != null) input.setType(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("value");
					if (item != null) input.setValue(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("width");
					if (item != null) input.setWidth(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("height");
					if (item != null) input.setHeight(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("readonly");
					if (item != null) input.setReadonly(Boolean.parseBoolean(item.getNodeValue()));
					input.setValidators(parseValidators(nField));
					fe.add(input);
				} else if (fieldComponent.equals("checkbox")) {
					CheckBox checkBox = new CheckBox();
					Node item = nField.getAttributes().getNamedItem("label");
					if (item != null) checkBox.setLabel(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("name");
					if (item != null) checkBox.setName(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("value");
					if (item != null) checkBox.setValue(Boolean.parseBoolean(item.getNodeValue()));
					item = nField.getAttributes().getNamedItem("width");
					if (item != null) checkBox.setWidth(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("height");
					if (item != null) checkBox.setHeight(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("readonly");
					if (item != null) checkBox.setReadonly(Boolean.parseBoolean(item.getNodeValue()));
					checkBox.setValidators(parseValidators(nField));
					fe.add(checkBox);
				} else if (fieldComponent.equals("textarea")) {
					TextArea textArea = new TextArea();
					Node item = nField.getAttributes().getNamedItem("label");
					if (item != null) textArea.setLabel(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("name");
					if (item != null) textArea.setName(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("value");
					if (item != null) textArea.setValue(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("width");
					if (item != null) textArea.setWidth(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("height");
					if (item != null) textArea.setHeight(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("readonly");
					if (item != null) textArea.setReadonly(Boolean.parseBoolean(item.getNodeValue()));
					textArea.setValidators(parseValidators(nField));
					fe.add(textArea);
				} else if (fieldComponent.equals("button")) {
					Button button = new Button();
					Node item = nField.getAttributes().getNamedItem("label");
					if (item != null) button.setLabel(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("name");
					if (item != null) button.setName(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("value");
					if (item != null) button.setValue(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("type");
					if (item != null) button.setType(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("width");
					if (item != null) button.setWidth(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("height");
					if (item != null) button.setHeight(item.getNodeValue());
					fe.add(button);
				} else if (fieldComponent.equals("select")) {
					Select select = new Select();
					ArrayList<Option> options = new ArrayList<Option>();
					Node item = nField.getAttributes().getNamedItem("label");
					if (item != null) select.setLabel(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("name");
					if (item != null) select.setName(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("type");
					if (item != null) select.setType(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("width");
					if (item != null) select.setWidth(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("height");
					if (item != null) select.setHeight(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("readonly");
					if (item != null) select.setReadonly(Boolean.parseBoolean(item.getNodeValue()));
					
					NodeList nlOptions = nField.getChildNodes();
					for (int k = 0; k < nlOptions.getLength(); k++) {
						Node nOption = nlOptions.item(k);
						
						if (nOption.getNodeType() == Node.ELEMENT_NODE) {
							if (nOption.getNodeName().equals("option")) {
								Option option = new Option();
								item = nOption.getAttributes().getNamedItem("label");
								if (item != null) option.setLabel(item.getNodeValue());
								item = nOption.getAttributes().getNamedItem("value");
								if (item != null) option.setValue(item.getNodeValue());
								item = nOption.getAttributes().getNamedItem("selected");
								if (item != null) option.setSelected(Boolean.parseBoolean(item.getNodeValue()));
								options.add(option);
							}
						}
					}
					
					select.setOptions(options);
					select.setValidators(parseValidators(nField));
					fe.add(select);
				}
			}
		}
		
		return fe;
	}
	
	private static List<Validator> parseValidators(Node nField) {
		List<Validator> validators = new ArrayList<Validator>();
		NodeList nlValidators = nField.getChildNodes();
		
		for (int k = 0; k < nlValidators.getLength(); k++) {
			Node nValidator = nlValidators.item(k);
			
			if (nValidator.getNodeType() == Node.ELEMENT_NODE) {
				if (nValidator.getNodeName().equals("validator")) {
					Validator validator = new Validator();
					Node item = nValidator.getAttributes().getNamedItem("type");
					if (item != null) validator.setType(item.getNodeValue());
					item = nValidator.getAttributes().getNamedItem("parameter");
					if (item != null) validator.setParameter(item.getNodeValue());
					validators.add(validator);
				}
			}
		}
		
		return validators;
	}
	
	/**
	 * 
	 */
	private static final class ErrorHandler extends DefaultHandler {
		public void error(SAXParseException exception) throws SAXException {
			log.error(exception.getMessage());
			throw exception;
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			log.error(exception.getMessage());
			throw exception;
		}

		public void warning(SAXParseException exception) throws SAXException {
			log.warn(exception.getMessage());
			throw exception;
		}
	}
}