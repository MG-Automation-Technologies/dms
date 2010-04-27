using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using WordOpenKMAddIn.config;
using MSOpenKMCore.ws;
using WordOpenKMAddIn.util;

namespace WordOpenKMAddIn.bean
{
    class Util
    {
        // copy document object to OKMDocument object
        public static OKMDocument copy(document doc, String docType)
        {
            OKMDocument oKMDocument = new OKMDocument();
            FileUtil fileUtil = new FileUtil();

            oKMDocument.setUUID(doc.uuid);
            oKMDocument.setName(fileUtil.getDocumentName(doc));
            oKMDocument.setLocalFilename(fileUtil.getFilenameWithoutCollision(doc));
            oKMDocument.setPath(doc.path);
            oKMDocument.setType(docType);

            return oKMDocument;
        }

        // Return the folder name, text before last "/" character
        public static String getFolderName(folder folderNode)
        {
            return folderNode.path.Substring(folderNode.path.LastIndexOf("/") + 1);
        }

        // Return if a user has folder has write permissions
        public static bool hasWritePermission(folder fld)
        {
            return ((fld.permissions & OKMPermissions.WRITE) == OKMPermissions.WRITE);
        }

        // Return if a user document has write permissions
        public static bool hasWritePermission(folder fatherFld, document doc)
        {
            // Must have permission to write in folders and in document
            return ((fatherFld.permissions & OKMPermissions.WRITE) == OKMPermissions.WRITE) &
                   ((doc.permissions & OKMPermissions.WRITE) == OKMPermissions.WRITE);
        }

        // Return the openkm document path
        public static String getOpenKMPath(String localFilename, folder fld)
        {
            return fld.path + "/" + localFilename.Substring(localFilename.LastIndexOf(@"\") + 1);
        }
    }
}
