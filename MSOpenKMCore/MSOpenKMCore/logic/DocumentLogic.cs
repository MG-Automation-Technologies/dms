using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MSOpenKMCore.ws;
using MSOpenKMCore.bean;
using System.IO;
using MSOpenKMCore.util;

namespace MSOpenKMCore.logic
{
    public class DocumentLogic
    {

        // check out document
        public static MSOpenKMCore.bean.OKMDocument checkoutDocument(document doc, String type, String host, String username, String password)
        {
            String token = "";
            OKMAuth authService = null;
            MSOpenKMCore.ws.OKMDocument documentService = null;

            try
            {
                // Init services
                SSL.init(host);
                authService = new OKMAuth(host);
                documentService = new MSOpenKMCore.ws.OKMDocument(host);

                // OpenKM authentication
                token = authService.login(username, password);

                // Create a new document
                MSOpenKMCore.bean.OKMDocument oKMDocument = Util.copy(doc, type);

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
        public static void cancelCheckout(MSOpenKMCore.bean.OKMDocument oKMDocument, String host, String username, String password)
        {
            String token = "";
            OKMAuth authService = null;
            MSOpenKMCore.ws.OKMDocument documentService = null;

            try
            {
                // Init services
                SSL.init(host);
                authService = new MSOpenKMCore.ws.OKMAuth(host);
                documentService = new MSOpenKMCore.ws.OKMDocument(host);

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
        public static void checkin(MSOpenKMCore.bean.OKMDocument oKMDocument, String host, String username, String password)
        {
            String token = "";
            MSOpenKMCore.ws.OKMAuth authService = null;
            MSOpenKMCore.ws.OKMDocument documentService = null;

            try
            {
                // Init services
                SSL.init(host);
                authService = new MSOpenKMCore.ws.OKMAuth(host);
                documentService = new MSOpenKMCore.ws.OKMDocument(host);

                // OpenKM authentication
                token = authService.login(username, password);

                // Checkin document
                //documentService.setContent(token, oKMDocument.getPath(), FileUtil.readFile(oKMDocument.getLocalFilename()));
                //documentService.checkin(token, oKMDocument.getPath(), "");

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
            OKMAuth authService = null;
            MSOpenKMCore.ws.OKMDocument documentService = null;

            try
            {
                // Init services
                SSL.init(host);
                authService = new OKMAuth(host);
                documentService = new MSOpenKMCore.ws.OKMDocument(host);

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
