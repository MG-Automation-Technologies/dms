using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using Microsoft.Win32;

namespace OutlookOpenKMAddIn.config
{
    class ConfigXML
    {
        // Configuration file params
        private const String OPENKM_CONFIGURATION_FILE_NAME = "openkm.xml";
        private const String OPENKM_FOLDER_NAME = "OpenKM";

        // Configuration xml tags
        private const String XML_TAG_OPENKM = "openkm";
        private const String XML_TAG_HOST = "host";
        private const String XML_TAG_USER = "user";
        private const String XML_TAG_PASSWORD = "password";

        // Initialize default values
        private String host = "http://127.0.0.1:8080/OpenKM/";
        private String user = "";
        private String password = "";
        private String configPath = "";
        private String configFilename = "";

        public ConfigXML()
        {
            // Take a look here http://support.microsoft.com/kb/221837/es ( user could change default my documents path )
            // now this case is not contempled
            RegistryKey myDocumentskey = Registry.CurrentUser.OpenSubKey(@"Software\Microsoft\Windows\CurrentVersion\Explorer\Shell Folders\");
            configPath = (String)myDocumentskey.GetValue("Personal") + "\\" + OPENKM_FOLDER_NAME;
            configFilename = configPath + "\\" + OPENKM_CONFIGURATION_FILE_NAME;

            // Create default %CURRENT USER%/My documents/OpenKM/ folder
            if (!Directory.Exists(configPath))
            {
                Directory.CreateDirectory(configPath);
            }

            // Create configuration file
            if (!File.Exists(configFilename))
            {
                CreateConfigurationFile();
            }

            // Always we reading configuration file
            ReadConfig();
        }

        private void ReadConfig()
        {
            XmlTextReader reader = new XmlTextReader(configFilename);

            while (reader.Read())
            {
                switch (reader.NodeType)
                {
                    case XmlNodeType.Element:
                        if (reader.Name.ToLower().Equals(XML_TAG_HOST))
                        {
                            if (reader.Read())
                            {
                                host = reader.Value;
                            }
                        }
                        else if (reader.Name.ToLower().Equals(XML_TAG_USER))
                        {
                            if (reader.Read())
                            {
                                user = reader.Value;
                            }
                        }
                        else if (reader.Name.ToLower().Equals(XML_TAG_PASSWORD))
                        {
                            if (reader.Read())
                            {
                                password = reader.Value;
                            }
                        }
                        break;

                    case XmlNodeType.Text:
                        break;

                    case XmlNodeType.EndElement:
                        break;
                }
            }

            reader.Close();
        }

        public void CreateConfigurationFile()
        {
            // Deleting file if exists
            if (File.Exists(configFilename))
            {
                File.Delete(configFilename);
            }

            XmlTextWriter writer = new XmlTextWriter(configFilename, null);

            writer.Formatting = Formatting.Indented;
            writer.WriteStartDocument();
            writer.WriteStartElement(XML_TAG_OPENKM, "");         // <openkm>
            writer.WriteStartElement(XML_TAG_HOST, "");           // <host>
            writer.WriteString(host);                             // host value
            writer.WriteEndElement();                             // </host>
            writer.WriteStartElement(XML_TAG_USER, "");           // <user>
            writer.WriteString(user);                             // user value
            writer.WriteEndElement();                             // </user>
            writer.WriteStartElement(XML_TAG_PASSWORD, "");       // <password>
            writer.WriteString(password);                             // password value
            writer.WriteEndElement();                             // </password>
            writer.WriteFullEndElement();                         // </openkm>
            writer.WriteEndDocument();                            // EOF
            writer.Close();
        }

        public String getHost()
        {
            return host;
        }

        public String getUser()
        {
            return user;
        }

        public String getPassword()
        {
            return password;
        }

        public void setHost(String host)
        {
            this.host = host;
        }

        public void setUser(String user)
        {
            this.user = user;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }
    }
}
