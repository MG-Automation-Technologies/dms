/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.config;

import com.openkm.openoffice.bean.ConfigBean;
import com.openkm.openoffice.logic.OKMException;
import com.openkm.openoffice.util.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javolution.xml.stream.XMLOutputFactory;
import javolution.xml.stream.XMLStreamException;
import javolution.xml.stream.XMLStreamWriter;
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

    private String OPENKM_FOLDER = "OpenKM";
    private String OPENKM_CONFIG_FILENAME = "openkm.xml";
    private String directoryToStoreFiles;
    private String configFilename;

    public ConfigFile() throws OKMException {
        directoryToStoreFiles = getDirectoryToStoreFiles();
        if (directoryToStoreFiles==null)  {
            directoryToStoreFiles = FileUtil.getWorkingPath() + OPENKM_FOLDER;
            File file = new File(directoryToStoreFiles);
            if (!file.exists()) {
                file.mkdir();
            }
        }

        if (configFilename==null) {
            configFilename = directoryToStoreFiles + FileUtil.getFolderPathSeparator() + OPENKM_CONFIG_FILENAME;
            File f = new File(configFilename);
            try {
                if (!f.exists()) {
                    f.createNewFile();
                    save(new ConfigBean());
                }
            } catch (IOException ex) {
                throw new OKMException(ex.getMessage());
            }
        }
    }

    public String getDirectoryToStoreFiles() {
        return directoryToStoreFiles;
    }

    

    public void save(ConfigBean configBean) throws OKMException {
        try {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            FileOutputStream stream = new FileOutputStream(configFilename);
            XMLStreamWriter writer;
            writer = outputFactory.createXMLStreamWriter(stream,"UTF-8");

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

    public ConfigBean read() throws OKMException {
        ConfigBean configBean = new ConfigBean();

        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(configFilename));

            NodeList nodes = doc.getElementsByTagName("openkm");
            if (nodes.getLength()>0) {
                Node config = nodes.item(0);
                Element configElement = (Element) config;
                if (configElement.getElementsByTagName("host").item(0).hasChildNodes()) {
                    configBean.setHost(configElement.getElementsByTagName("host").item(0).getChildNodes().item(0).getNodeValue());
                }
                if (configElement.getElementsByTagName("user").item(0).hasChildNodes()) {
                    configBean.setUser(configElement.getElementsByTagName("user").item(0).getChildNodes().item(0).getNodeValue());
                }
                if (configElement.getElementsByTagName("password").item(0).hasChildNodes()) {
                    configBean.setPassword(configElement.getElementsByTagName("password").item(0).getChildNodes().item(0).getNodeValue());
                }
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
