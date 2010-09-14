/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.config;

import com.openkm.openoffice.bean.ConfigBean;
import com.openkm.openoffice.logic.OKMException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author jllort
 */
public class ConfigFile {

    private static String OPENKM_FOLDER = "OpenKM";
    private static String OPENKM_CONFIG_FILENAME = "openkm.xml";
    private static String directoryToStoreFiles;
    private static String configFilename;

    public static void init() throws OKMException {
        directoryToStoreFiles = getDirectoryToStoreFiles();
        if (directoryToStoreFiles==null)  {
            directoryToStoreFiles = getWorkingPath() + OPENKM_FOLDER;
            File file = new File(directoryToStoreFiles);
            if (!file.exists()) {
                file.mkdir();
            }
        }

        if (configFilename==null) {
            configFilename = directoryToStoreFiles + "/" + OPENKM_CONFIG_FILENAME;
            File f = new File(configFilename);
            try {
                if (!f.exists()) {
                    f.createNewFile();
                }
            } catch (IOException ex) {
                throw new OKMException(ex.getMessage());
            }
        }
    }

    public static String getDirectoryToStoreFiles() {
        return directoryToStoreFiles;
    }

    public static String getWorkingPath() {
        return System.getProperty("user.home")+"/";
    }

    public static void save(ConfigBean configBean) throws OKMException {
        try {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            FileWriter fileWriter = new FileWriter(configFilename);
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(fileWriter);

            writer.writeStartDocument("utf-8", "1.0");      // Header
            writer.writeStartElement("openkm");             // openkm node

            writer.writeStartElement("host");               // host
            writer.writeCharacters(configBean.getHost());
            writer.writeEndElement();

            writer.writeStartElement("user");               // user
            writer.writeCharacters(configBean.getUser());
            writer.writeEndElement();

            writer.writeStartElement("password");           // password
            writer.writeCharacters(configBean.getPassword());
            writer.writeEndElement();

            writer.writeEndElement();                       // close openkm
            writer.flush();
            writer.close();

        } catch (XMLStreamException ex) {
            throw new OKMException(ex.getMessage());
        } catch (IOException ex) {
            throw new OKMException(ex.getMessage());
        } finally {
        }
    }

    public static ConfigBean read() throws OKMException {
        ConfigBean configBean = new ConfigBean();

        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(configFilename));

            NodeList nodes = doc.getElementsByTagName("openkm");
            if (nodes.getLength()>0) {
                Node config = nodes.item(0);
                Element configElement = (Element) config;
                configBean.setHost(configElement.getElementsByTagName("host").item(0).getChildNodes().item(0).getNodeValue());
                configBean.setUser(configElement.getElementsByTagName("user").item(0).getChildNodes().item(0).getNodeValue());
                configBean.setPassword(configElement.getElementsByTagName("password").item(0).getChildNodes().item(0).getNodeValue());
            }

        } catch (ParserConfigurationException ex) {
            throw new OKMException(ex.getMessage());
        } catch (SAXException ex) {
            throw new OKMException(ex.getMessage());
        } catch (IOException ex) {
            throw new OKMException(ex.getMessage());
        }

        return configBean;
    }
}
