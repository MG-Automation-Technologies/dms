/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.config;

import com.openkm.openoffice.bean.OKMDocumentBean;
import com.openkm.openoffice.logic.OKMException;
import com.openkm.openoffice.util.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class DocumentFile {
    private String OPENKM_FOLDER = "OpenKM";
    private String OPENKM_CONFIG_FILENAME = "document.xml";
    private String directoryToStoreFiles;
    private String documentFilename;
    private List<OKMDocumentBean> docList;


    public DocumentFile() throws OKMException {
        docList = new ArrayList<OKMDocumentBean>();
        directoryToStoreFiles = getDirectoryToStoreFiles();
        if (directoryToStoreFiles==null)  {
            directoryToStoreFiles = FileUtil.getWorkingPath() + OPENKM_FOLDER;
            File file = new File(directoryToStoreFiles);
            if (!file.exists()) {
                file.mkdir();
            }
        }

        if (documentFilename==null) {
            documentFilename = directoryToStoreFiles + FileUtil.getFolderPathSeparator() + OPENKM_CONFIG_FILENAME;
            File f = new File(documentFilename);
            try {
                if (!f.exists()) {
                    f.createNewFile();
                    save();
                }
            } catch (IOException ex) {
                throw new OKMException(ex.getMessage());
            }
        }
        docList = new ArrayList<OKMDocumentBean>();
    }

    public String getDirectoryToStoreFiles() {
        return directoryToStoreFiles;
    }


    public void save() throws OKMException {
        try {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            FileOutputStream stream = new FileOutputStream(documentFilename);
            XMLStreamWriter writer;
            writer = outputFactory.createXMLStreamWriter(stream,"UTF-8");
            writer.writeStartDocument("utf-8", "1.0");      // Header
            writer.writeStartElement("openkm");             // openkm node

            for (Iterator<OKMDocumentBean> it = docList.iterator(); it.hasNext();) {
                OKMDocumentBean doc = it.next();
                writer.writeStartElement("document");       // document node

                writer.writeStartElement("uuid");           // uuid
                writer.writeCharacters(doc.getUUID());
                writer.writeEndElement();

                writer.writeStartElement("path");           // path
                writer.writeCharacters(doc.getPath());
                writer.writeEndElement();

                writer.writeStartElement("localfilename");  // localfilename
                writer.writeCharacters(doc.getLocalFilename());
                writer.writeEndElement();

                writer.writeStartElement("name");           // name
                writer.writeCharacters(doc.getName());
                writer.writeEndElement();

                writer.writeEndElement();                   // close document
            }

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

    public List<OKMDocumentBean> read() throws OKMException {
        try {
            docList = new ArrayList<OKMDocumentBean>();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(documentFilename));

            NodeList nodes = doc.getElementsByTagName("document");
            if (nodes.getLength()>0) {
                for (int i=0; i<nodes.getLength(); i++) {
                    OKMDocumentBean oKMDocumentBean = new OKMDocumentBean();
                    Node document = nodes.item(i);
                    Element docElement = (Element) document;
                    oKMDocumentBean.setUUID(docElement.getElementsByTagName("uuid").item(0).getChildNodes().item(0).getNodeValue());
                    oKMDocumentBean.setPath(docElement.getElementsByTagName("path").item(0).getChildNodes().item(0).getNodeValue());
                    oKMDocumentBean.setLocalFilename(docElement.getElementsByTagName("localfilename").item(0).getChildNodes().item(0).getNodeValue());
                    oKMDocumentBean.setName(docElement.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
                    docList.add(oKMDocumentBean);
                }
            }

        } catch (ParserConfigurationException ex) {
            throw new OKMException(ex.getMessage());
        } catch (SAXException ex) {
            throw new OKMException(ex.getMessage());
        } catch (IOException ex) {
            throw new OKMException(ex.getMessage());
        }

        return docList;
    }

    public void add(OKMDocumentBean doc) throws OKMException {
        read();
        docList.add(doc);
        save();
    }

    public void remove(OKMDocumentBean doc) throws OKMException {
        read();
        for (Iterator<OKMDocumentBean> it = docList.iterator(); it.hasNext();) {
            OKMDocumentBean tmpDoc = it.next();
            if (tmpDoc.getUUID().equals(doc.getUUID())) {
                docList.remove(tmpDoc);
                break;
            }
        }
        save();
    }

    public boolean isOpenKMDocument(String localFilename) throws OKMException {
        boolean found = false;
        read();
        for (Iterator<OKMDocumentBean> it = docList.iterator(); it.hasNext();) {
            OKMDocumentBean doc = it.next();
            if (localFilename.equals(doc.getLocalFilename())) {
                found = true;
                break;
            } 
        }
        return found;
    }

    public OKMDocumentBean findByLocalFileName(String localFilename) throws OKMException {
        read();
        for (Iterator<OKMDocumentBean> it = docList.iterator(); it.hasNext();) {
            OKMDocumentBean doc = it.next();
            if (localFilename.equals(doc.getLocalFilename())) {
                return doc;
            }
        }
        return null;
    }
}
