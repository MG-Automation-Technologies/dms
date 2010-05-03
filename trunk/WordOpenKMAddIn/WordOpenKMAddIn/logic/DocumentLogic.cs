using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MSOpenKMCore.ws;
using WordOpenKMAddIn.bean;
using System.IO;
using WordOpenKMAddIn.util;

namespace WordOpenKMAddIn.logic
{
    class DocumentLogic
    {

        // check out document
        public static OKMDocument checkoutDocument(document doc, String host, String username, String password)
        {
            String token = "";
            OKMAuthService authService = null;
            OKMDocumentService documentService = null;

            try
            {
                // Init services
                authService = new OKMAuthService(host);
                documentService = new OKMDocumentService(host);

                // OpenKM authentication
                token = authService.login(username, password);

                // Create a new document
                OKMDocument oKMDocument = Util.copy(doc, OKMDocumentType.TYPE_WORD);

                // Creates new document
                FileStream fileStream = new FileStream(oKMDocument.getLocalFilename(), FileMode.CreateNew);

                // Checking out document
                documentService.checkout(token, doc.path);
                byte[] byteDoc = documentService.getContent(token, doc.path, false);

                // Write the data to the file, byte by byte.
                for (int i = 0; i < byteDoc.Length; i++)
                {
                    fileStream.WriteByte(byteDoc[i]);
                }

                fileStream.Close();

                // Logout OpenKM
                authService.logout(token);
                token = "";

                return oKMDocument;
            }
            catch (Exception e)
            {
                if (!token.Equals(""))
                {
                    // Logout OpenKM
                    authService.logout(token);
                    token = "";
                }
                String errorMSG = "DocumentLogic (checkoutDocument)\n" + e.Message + "\n\n" + e.StackTrace;
                throw new OKMException(errorMSG);
            }
        }

        // cancel checkout document
        public static void cancelCheckout(OKMDocument oKMDocument, String host, String username, String password)
        {
            String token = "";
            OKMAuthService authService = null;
            OKMDocumentService documentService = null;

            try
            {
                // Init services
                authService = new OKMAuthService(host);
                documentService = new OKMDocumentService(host);

                // OpenKM authentication
                token = authService.login(username, password);

                // Cancelling checkout
                documentService.cancelCheckout(token, oKMDocument.getPath());

                // Logout OpenKM
                authService.logout(token);
                token = "";
            }
            catch (Exception e)
            {
                if (!token.Equals(""))
                {
                    // Logout OpenKM
                    authService.logout(token);
                    token = "";
                }
                String errorMSG = "DocumentLogic (cancelCheckout)\n" + e.Message + "\n\n" + e.StackTrace;
                throw new OKMException(errorMSG);
            }
        }

        // Checkin document
        public static void checkin(OKMDocument oKMDocument, String host, String username, String password)
        {
            String token = "";
            OKMAuthService authService = null;
            OKMDocumentService documentService = null;

            try
            {
                // Init services
                authService = new OKMAuthService(host);
                documentService = new OKMDocumentService(host);

                // OpenKM authentication
                token = authService.login(username, password);

                // Checkin document
                documentService.setContent(token, oKMDocument.getPath(), FileUtil.readFile(oKMDocument.getLocalFilename()));
                documentService.checkin(token, oKMDocument.getPath(), "");

                // Logout OpenKM
                authService.logout(token);
                token = "";
            }
            catch (Exception e)
            {
                if (!token.Equals(""))
                {
                    // Logout OpenKM
                    authService.logout(token);
                    token = "";
                }
                String errorMSG = "DocumentLogic (checkin)\n" + e.Message + "\n\n" + e.StackTrace;
                throw new OKMException(errorMSG);
            }
        }

        // create document
        public static void create(String localFileName, String docPath, String host, String username, String password)
        {
            String token = "";
            OKMAuthService authService = null;
            OKMDocumentService documentService = null;

            try
            {
                // Init services
                authService = new OKMAuthService(host);
                documentService = new OKMDocumentService(host);

                // OpenKM authentication
                token = authService.login(username, password);

                // create document
                document doc = new document();
                doc.path = docPath;
                documentService.create(token, doc, FileUtil.readFile(localFileName));

                // Logout OpenKM
                authService.logout(token);
                token = "";
            }
            catch (Exception e)
            {
                if (!token.Equals(""))
                {
                    // Logout OpenKM
                    authService.logout(token);
                    token = "";
                }
                String errorMSG = "DocumentLogic (create)\n" + e.Message + "\n\n" + e.StackTrace;
                throw new OKMException(errorMSG);
            }
        }
    }
}
