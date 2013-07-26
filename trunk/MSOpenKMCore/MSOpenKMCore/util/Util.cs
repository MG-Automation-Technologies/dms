using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MSOpenKMCore.config;
using MSOpenKMCore.ws;
using MSOpenKMCore.util;
using MSOpenKMCore.bean;

namespace MSOpenKMCore.util
{
    public class Util
    {
        // copy document object to OKMDocument object
        public static MSOpenKMCore.bean.OKMDocument copy(document doc, String docType)
        {
            try
            {
                MSOpenKMCore.bean.OKMDocument oKMDocument = new MSOpenKMCore.bean.OKMDocument();
                FileUtil fileUtil = new FileUtil();

                oKMDocument.setUUID(doc.uuid);
                oKMDocument.setName(getDocumentName(doc));
                oKMDocument.setLocalFilename(fileUtil.getFilenameWithoutCollision(doc));
                oKMDocument.setPath(doc.path);
                oKMDocument.setType(docType);

                return oKMDocument;
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Return the folder name, text before last "/" character
        public static String getFolderName(MSOpenKMCore.ws.folder folderNode)
        {
            try
            {
                return folderNode.path.Substring(folderNode.path.LastIndexOf("/") + 1);
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Return if a user has folder has write permissions
        public static bool hasWritePermission(MSOpenKMCore.ws.folder fld)
        {
            try
            {
                return ((fld.permissions & OKMPermissions.WRITE) == OKMPermissions.WRITE);
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Return if a user document has write permissions
        public static bool hasWritePermission(MSOpenKMCore.ws.folder fatherFld, MSOpenKMCore.ws.document doc)
        {
            try
            {
                // Must have permission to write in folders and in document
                return ((fatherFld.permissions & OKMPermissions.WRITE) == OKMPermissions.WRITE) &
                       ((doc.permissions & OKMPermissions.WRITE) == OKMPermissions.WRITE);
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Return the openkm document path
        public static String getOpenKMPath(String localFilename, MSOpenKMCore.ws.folder fld)
        {
            try
            {
                return fld.path + "/" + localFilename.Substring(localFilename.LastIndexOf(@"\") + 1);
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // get the document Extension
        public static String getDocumentExtension(MSOpenKMCore.ws.document doc)
        {
            try
            {
                return doc.path.Substring(doc.path.LastIndexOf(".") + 1);
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // get the document name
        public static String getDocumentName(MSOpenKMCore.ws.document doc)
        {
            try
            {
                return doc.path.Substring(doc.path.LastIndexOf("/") + 1);
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        // Return if document is valid to be opened with ms word
        public static bool isDocumentValidToOpenWithMSWord(MSOpenKMCore.ws.document doc)
        {
            bool valid = false;
            String[] WORD_EXTENSIONS = new String[15] {"doc","dot","xml","html",
                                                       "htm","mht","mhtml","rtf",
                                                       "txt","scd","olk","pab",
                                                       "wri","wpd","wpd"};

            String docExtension = getDocumentExtension(doc);
            foreach (String extension in WORD_EXTENSIONS)
            {
                if (docExtension.Equals(extension))
                {
                    valid = true;
                    break;
                }
            }

            // Special case, document could start with doc*
            if (!valid)
            {
                if (docExtension.StartsWith("doc"))
                {
                    valid = true;
                }
            }

            return valid;
        }

        // Return if document is valid to be opened with ms excel
        public static bool isDocumentValidToOpenWithMSExcel(MSOpenKMCore.ws.document doc)
        {
            bool valid = false;
            String[] EXCEL_EXTENSIONS = new String[16] {"xls","xlt","htm","html","mht","mhtml","xml",
                                                       "xla","xlm","xlc","xlw","odc","uxdc","prn",
                                                       "txt","csv"};

            String docExtension = getDocumentExtension(doc);
            foreach (String extension in EXCEL_EXTENSIONS)
            {
                if (docExtension.Equals(extension))
                {
                    valid = true;
                    break;
                }
            }

            // Special case, document could start with xl*
            if (!valid)
            {
                if (docExtension.StartsWith("xl"))
                {
                    valid = true;
                }
            }

            return valid;
        }

        // Return if document is valid to be opened with ms excel
        public static bool isDocumentValidToOpenWithMSPowerPoint(MSOpenKMCore.ws.document doc)
        {
            bool valid = false;
            String[] POWERPOINT_EXTENSIONS = new String[9] {"htm","html","mht","mhtml","txt","rtf","doc","wpd","wps"};

            String docExtension = getDocumentExtension(doc);
            foreach (String extension in POWERPOINT_EXTENSIONS)
            {
                if (docExtension.Equals(extension))
                {
                    valid = true;
                    break;
                }
            }

            // Special case, document could start with pps* ppt* pot*
            if (!valid)
            {
                if (docExtension.StartsWith("pps"))
                {
                    valid = true;
                }
            }
            if (!valid)
            {
                if (docExtension.StartsWith("ppt"))
                {
                    valid = true;
                }
            }
            if (!valid)
            {
                if (docExtension.StartsWith("pot"))
                {
                    valid = true;
                }
            }

            return valid;
        }

        // Return if document is valid to be opened with ms visio
        public static bool isDocumentValidToOpenWithMSVisio(MSOpenKMCore.ws.document doc)
        {
            bool valid = false;
            String[] VISIO_EXTENSIONS = new String[9] { "vsd", "vxd", "vss", "vsx", "vst", "vtx", "vsw", "svg", "svgz" };

            String docExtension = getDocumentExtension(doc);
            foreach (String extension in VISIO_EXTENSIONS)
            {
                if (docExtension.Equals(extension))
                {
                    valid = true;
                    break;
                }
            }

            // Special case, document could start with vs* v?x
            if (!valid)
            {
                if (docExtension.StartsWith("vs"))
                {
                    valid = true;
                }
            }
            if (!valid)
            {
                if (docExtension.StartsWith("v") && docExtension.Length == 3 && docExtension.EndsWith("x"))
                {
                    valid = true;
                }
            }

            return valid;
        }
    }
}
