package es.git.openkm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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

import es.git.openkm.bean.PropertyGroup;
import es.git.openkm.bean.form.Button;
import es.git.openkm.bean.form.FormElement;
import es.git.openkm.bean.form.Input;
import es.git.openkm.bean.form.Option;
import es.git.openkm.bean.form.Select;
import es.git.openkm.bean.form.TextArea;
import es.git.openkm.core.Config;
import es.git.openkm.core.ParseException;

public class FormUtils {
	private static Logger log = LoggerFactory.getLogger(FormUtils.class);
	private static Map<PropertyGroup, Collection<FormElement>> pGroups = null;

	/**
	 * Parse form.xml definitions
	 * 
	 * @return A Map with all the forms and its form elements.
	 */
	public static Map<String, Collection<FormElement>> parseWorkflowForms(InputStream is) throws ParseException {
		log.debug("parseWorkflowForms({})", is);
		// long begin = Calendar.getInstance().getTimeInMillis();
		Map<String, Collection<FormElement>> forms = new HashMap<String, Collection<FormElement>>();

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
						ArrayList<FormElement> fe = parseField(nlField);
						forms.put(taskName, fe);
					}
				}

				is.close();
			}
		} catch (ParserConfigurationException e) {
			throw new ParseException(e.getMessage());
		} catch (SAXException e) {
			throw new ParseException(e.getMessage());
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
	public static synchronized Map<PropertyGroup, Collection<FormElement>> parsePropertyGroupsForms() 
			throws IOException,	ParseException {
		log.info("parseMetadataForms()");
		// long begin = Calendar.getInstance().getTimeInMillis();
		if (pGroups == null) {
			String pgFile = Config.HOME_DIR + File.separator +"PropertyGroups" + Config.INSTALL + ".xml";
			log.debug("PropertyGroupForms: {}", pgFile);
			pGroups = new HashMap<PropertyGroup, Collection<FormElement>>();
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
							NodeList nlField = nForm.getChildNodes();
							ArrayList<FormElement> fe = parseField(nlField);
							PropertyGroup pg = new PropertyGroup();
							pg.setLabel(pgLabel);
							pg.setName(pgName);
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

		log.info("parseMetadataForms: {}", pGroups);
		// log.info("Time: "+(Calendar.getInstance().getTimeInMillis()-begin)+" ms");
		return pGroups;
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
	public static Collection<FormElement> getPropertyGroupForms(Map<PropertyGroup, Collection<FormElement>> formsElements, String groupName) {
		for (Iterator<Entry<PropertyGroup, Collection<FormElement>>> it1 = formsElements.entrySet().iterator(); it1.hasNext(); ) {
			Entry<PropertyGroup, Collection<FormElement>> entry = it1.next();
			if (entry.getKey().getName().equals(groupName)) {
				return entry.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * Retrieve the form element from a PropertyGroups definition. 
	 */
	public static FormElement getFormElement(Map<PropertyGroup, Collection<FormElement>> formsElements, String propertyName) {
		for (Iterator<Entry<PropertyGroup, Collection<FormElement>>> it1 = formsElements.entrySet().iterator(); it1.hasNext(); ) {
			Entry<PropertyGroup, Collection<FormElement>> entry = it1.next();
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
	private static ArrayList<FormElement> parseField(NodeList nlField) {
		ArrayList<FormElement> fe = new ArrayList<FormElement>();
		
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
					fe.add(input);
				} else if (fieldComponent.equals("textarea")) {
					TextArea textArea = new TextArea();
					Node item = nField.getAttributes().getNamedItem("label");
					if (item != null) textArea.setLabel(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("name");
					if (item != null) textArea.setName(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("cols");
					if (item != null) textArea.setWidth(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("rows");
					if (item != null) textArea.setHeight(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("value");
					if (item != null) textArea.setValue(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("width");
					if (item != null) textArea.setWidth(item.getNodeValue());
					item = nField.getAttributes().getNamedItem("height");
					if (item != null) textArea.setHeight(item.getNodeValue());
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
					fe.add(select);
				}
			}
		}
		
		return fe;
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
