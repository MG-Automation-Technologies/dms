using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using MSOpenKMCore.ws;

namespace WordOpenKMAddIn.util
{
    class FileUtil
    {
        // Configuration file params
        private const String OPENKM_CONFIGURATION_FILE_NAME = "document.xml";
        private const String OPENKM_FOLDER_NAME = "OpenKM";

        private RegistryUtil registryUtil = null;
        private String configPath = "";
        private String configDocumentFilenamePath = "";

        // File util
        public FileUtil()
        {
            registryUtil = new RegistryUtil();
            configPath = registryUtil.getPersonal() + "\\" + OPENKM_FOLDER_NAME;
            configDocumentFilenamePath = configPath + "\\" + OPENKM_CONFIGURATION_FILE_NAME;
        }

        // document filename path
        public String getDocumentFilenamePath()
        {
            return configDocumentFilenamePath;
        }

        // folder filename path
        public String getConfigPath()
        {
            return configPath;
        }

        // returns file name that not exist in disk
        public String getFilenameWithoutCollision (document doc) {
            String fileName = getConfigPath() + @"\" + getDocumentName(doc); // Default path name
            String docNamePart1 = fileName.Substring(0, fileName.LastIndexOf("."));
            String docNamePart2 = fileName.Substring(fileName.LastIndexOf("."));

            int count = 1;
            while (File.Exists(fileName))
            {
                fileName = docNamePart1 + "_" + count + docNamePart2;
                count++;
            }

            return fileName;
        }

        // get the document Extension
        public String getDocumentExtension(document doc)
        {
            return doc.path.Substring(doc.path.LastIndexOf(".") + 1);
        }

        // get the document name
        public String getDocumentName(document doc)
        {
            return doc.path.Substring(doc.path.LastIndexOf("/") + 1);
        }

        // Read the local file
        public static byte[] readFile(string filePath)
        {
            byte[] buffer;
            FileStream fileStream = new FileStream(filePath, FileMode.Open, FileAccess.Read);
            try
            {
                int length = (int)fileStream.Length;  // get file length
                buffer = new byte[length];            // create buffer
                int count;                            // actual number of bytes read
                int sum = 0;                          // total number of bytes read

                // read until Read method returns 0 (end of the stream has been reached)
                while ((count = fileStream.Read(buffer, sum, length - sum)) > 0)
                    sum += count;  // sum is a buffer offset for next reading
            }
            finally
            {
                fileStream.Close();
            }
            return buffer;
        }

    }
}
