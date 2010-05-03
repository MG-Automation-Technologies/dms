using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml;
using Microsoft.Win32;
using MSOpenKMCore.ws;
using WordOpenKMAddIn.bean;
using WordOpenKMAddIn.util;

namespace WordOpenKMAddIn.config
{
    partial class DocumentXML
    {
        // Configuration xml tags
        private const String XML_TAG_OPENKM         = "openkm";
        private const String XML_TAG_DOCUMENT       = "document";
        private const String XML_TAG_UUID           = "uuid";
        private const String XML_TAG_PATH           = "path";
        private const String XML_TAG_LOCAL_FILENAME = "localfilename";
        private const String XML_TAG_NAME           = "name";
        private const String XML_TAG_TYPE           = "type";

        private FileUtil fileUtil = null;
        private List<OKMDocument> documentList = null;

        // Document XML
        public DocumentXML()
        {
            try
            {
                fileUtil = new FileUtil();
                documentList = new List<OKMDocument>();

                // Create default %CURRENT USER%/My documents/OpenKM/ folder
                if (!Directory.Exists(fileUtil.getConfigPath()))
                {
                    Directory.CreateDirectory(fileUtil.getConfigPath());
                }

                // Create configuration file
                if (!File.Exists(fileUtil.getDocumentFilenamePath()))
                {
                    CreateDocumentFile();
                }

                // Always we reading configuration file
                ReadDocumentFile();
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Refresh document list
        public void refresh()
        {
            try
            {
                ReadDocumentFile();
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Read document file
        private void ReadDocumentFile()
        {
            XmlTextReader reader = null;
            OKMDocument doc = null;
            documentList = null;

            try
            {
                reader = new XmlTextReader(fileUtil.getDocumentFilenamePath());
                doc = new OKMDocument();
                documentList = new List<OKMDocument>();

                while (reader.Read())
                {
                    switch (reader.NodeType)
                    {
                        case XmlNodeType.Element:
                            if (reader.Name.ToLower().Equals(XML_TAG_DOCUMENT))
                            {
                                doc = new OKMDocument();
                            }
                            else if (reader.Name.ToLower().Equals(XML_TAG_UUID))
                            {
                                if (reader.Read())
                                {
                                    doc.setUUID(reader.Value);
                                }
                            }
                            else if (reader.Name.ToLower().Equals(XML_TAG_PATH))
                            {
                                if (reader.Read())
                                {
                                    doc.setPath(reader.Value);
                                }
                            }
                            else if (reader.Name.ToLower().Equals(XML_TAG_LOCAL_FILENAME))
                            {
                                if (reader.Read())
                                {
                                    doc.setLocalFilename(reader.Value);
                                }
                            }
                            else if (reader.Name.ToLower().Equals(XML_TAG_NAME))
                            {
                                if (reader.Read())
                                {
                                    doc.setName(reader.Value);
                                }
                            }
                            else if (reader.Name.ToLower().Equals(XML_TAG_TYPE))
                            {
                                if (reader.Read())
                                {
                                    doc.setType(reader.Value);
                                    documentList.Add(doc);
                                }
                            }
                            break;

                        case XmlNodeType.Text:
                            break;

                        case XmlNodeType.EndElement:
                            break;
                    }
                }
            }
            catch (Exception e)
            {
                throw e;
            }
            finally
            {
                if (reader != null)
                {
                    reader.Close();
                }
            }
        }

        // Add new document to document file
        public void add(OKMDocument oKMDocument)
        {
            try 
            {
                documentList.Add(oKMDocument);
                CreateDocumentFile();
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Create document file
        public void CreateDocumentFile()
        {
            XmlTextWriter writer = null;

            try
            {
                // Deleting file if exists
                if (File.Exists(fileUtil.getDocumentFilenamePath()))
                {
                    File.Delete(fileUtil.getDocumentFilenamePath());
                }

                writer = new XmlTextWriter(fileUtil.getDocumentFilenamePath(), null);

                writer.Formatting = Formatting.Indented;
                writer.WriteStartDocument();
                writer.WriteStartElement(XML_TAG_OPENKM, "");                               // <openkm>

                // Writing documents
                foreach (OKMDocument doc in documentList)
                {
                    writer.WriteStartElement(XML_TAG_DOCUMENT, "");                         // <document>
                    writer.WriteStartElement(XML_TAG_UUID, "");                             // <uuid>
                    writer.WriteString(doc.getUUID());                                      // uuid value
                    writer.WriteEndElement();                                               // </uuid>
                    writer.WriteStartElement(XML_TAG_PATH, "");                             // <path>
                    writer.WriteString(doc.getPath());                                      // path value
                    writer.WriteEndElement();                                               // </localfilename>
                    writer.WriteStartElement(XML_TAG_LOCAL_FILENAME, "");                   // <path>
                    writer.WriteString(doc.getLocalFilename());                             // localfilename value
                    writer.WriteEndElement();                                               // </path>
                    writer.WriteStartElement(XML_TAG_NAME, "");                             // <name>
                    writer.WriteString(doc.getName());                                      // name value
                    writer.WriteEndElement();                                               // </name>
                    writer.WriteStartElement(XML_TAG_TYPE, "");                             // <type>
                    writer.WriteString(doc.getType());                                      // type value
                    writer.WriteEndElement();                                               // </type>
                    writer.WriteEndElement();                                               // </document>
                }

                writer.WriteFullEndElement();                                               // </openkm>
                writer.WriteEndDocument();                                                  // EOF
            }
            catch (Exception e)
            {
                throw e;
            }
            finally
            {
                if (writer != null)
                {
                    writer.Close();
                }
            }
        }

        // Returns if some local file is an openkm
        public bool isOpenKMDocument(String localFilename) {
            try
            {
                bool found = false;

                foreach (OKMDocument oKMDocument in documentList)
                {
                    if (oKMDocument.getLocalFilename().Equals(localFilename))
                    {
                        found = true;
                        break;
                    }
                }

                return found;
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Return the openkm document xml data object
        public OKMDocument getOpenKMDocument(String localFilename)
        {
            try
            {
                foreach (OKMDocument oKMDocument in documentList)
                {
                    if (oKMDocument.getLocalFilename().Equals(localFilename))
                    {
                        return oKMDocument;
                    }
                }

                return null;
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // remove document from document file
        public void remove(OKMDocument oKMDocument) 
        {
            try
            {
                OKMDocument documentToRemove = null;

                foreach (OKMDocument oKMDocumentTemp in documentList)
                {
                    if (oKMDocumentTemp.getLocalFilename().Equals(oKMDocument.getLocalFilename()))
                    {
                        documentToRemove = oKMDocumentTemp;
                        break;
                    }
                }

                if (documentToRemove != null)
                {
                    documentList.Remove(documentToRemove);
                }

                CreateDocumentFile();
            }
            catch (Exception e)
            {
                throw e;
            }
        }
    }
}
