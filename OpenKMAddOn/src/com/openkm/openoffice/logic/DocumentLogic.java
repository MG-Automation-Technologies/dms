/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.logic;

import com.openkm.openoffice.bean.OKMDocumentBean;
import com.openkm.openoffice.util.FileUtil;
import com.openkm.openoffice.util.Util;
import com.openkm.ws.client.AccessDeniedException_Exception;
import com.openkm.ws.client.Document;
import com.openkm.ws.client.OKMAuth;
import com.openkm.ws.client.OKMAuthService;
import com.openkm.ws.client.OKMDocument;
import com.openkm.ws.client.OKMDocumentService;
import com.openkm.ws.client.RepositoryException_Exception;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author jllort
 */
public class DocumentLogic {

    private static QName AuthServiceName = new QName("http://endpoint.ws.openkm.git.es/", "OKMAuthService");
    private static QName DocumentServiceName = new QName("http://endpoint.ws.openkm.git.es/", "OKMDocumentService");

    public static void create(String host, String username, String password, OKMDocumentBean document) throws OKMException {
        String token = "";
        OKMAuthService authService = null;
        OKMDocumentService docService = null;
        OKMAuth okmAuth = null;
        OKMDocument okmDocument = null;
        Document doc = new Document();

        try {
            authService = new OKMAuthService(new URL(host + "/OKMAuth?wsdl"), AuthServiceName);
            docService = new OKMDocumentService(new URL(host + "/OKMDocument?wsdl"), DocumentServiceName);
            okmAuth = authService.getOKMAuthPort();
            okmDocument = docService.getOKMDocumentPort();
            BindingProvider bpAuth = (BindingProvider) okmAuth;
            BindingProvider bpDocument= (BindingProvider) okmDocument;
            bpAuth.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, host+"/OKMAuth");
            bpDocument.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, host+"/OKMDocument");

            token = okmAuth.login(username, password);

            doc.setPath(document.getPath());
            okmDocument.create(token, doc, FileUtil.readFile(document.getLocalFilename()));

            // Logout OpenKM
            okmAuth.logout(token);
            token = "";
        } catch (Exception ex) {
            if (!token.equals("")) {
                try {
                  // Logout OpenKM
                        okmAuth.logout(token);
                } catch (AccessDeniedException_Exception ex1) {
                } catch (RepositoryException_Exception ex1) {
                }
            }
            throw new OKMException(ex.getMessage() + "\nStackTrace\n" + ex.getStackTrace());
        } 
    }

    public static OKMDocumentBean chekckout(String host, String username, String password, Document doc, String directoryPath) throws OKMException {
        String token = "";
        OKMAuthService authService = null;
        OKMDocumentService docService = null;
        OKMAuth okmAuth = null;
        OKMDocument okmDocument = null;
        OKMDocumentBean okmDocumentBean = new OKMDocumentBean();

        try {
            authService = new OKMAuthService(new URL(host + "/OKMAuth?wsdl"), AuthServiceName);
            docService = new OKMDocumentService(new URL(host + "/OKMDocument?wsdl"), DocumentServiceName);
            okmAuth = authService.getOKMAuthPort();
            okmDocument = docService.getOKMDocumentPort();
            BindingProvider bpAuth = (BindingProvider) okmAuth;
            BindingProvider bpDocument= (BindingProvider) okmDocument;
            bpAuth.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, host+"/OKMAuth");
            bpDocument.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, host+"/OKMDocument");

            token = okmAuth.login(username, password);

            okmDocument.checkout(token, doc.getPath());
            byte[] bytedoc = okmDocument.getContent(token, doc.getPath(), false);

            String fileName = Util.getLocalFilenameWithoutCollisions(doc, directoryPath);
            File file = new File(fileName);
            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytedoc);
            fos.flush();
            fos.close();

            okmDocumentBean = Util.copy(doc);
            okmDocumentBean.setLocalFilename(fileName);

            // Logout OpenKM
            okmAuth.logout(token);
            token = "";
        } catch (Exception ex) {
            if (!token.equals("")) {
                try {
                    // Logout OpenKM
                    okmAuth.logout(token);
                } catch (AccessDeniedException_Exception ex1) {
                } catch (RepositoryException_Exception ex1) {
                }
            }
            throw new OKMException(ex.getMessage() + "\nStackTrace\n" + ex.getStackTrace());
        }
        return okmDocumentBean;
    }

    public static void cancelCheckout(String host, String username, String password, OKMDocumentBean document) throws OKMException {
        String token = "";
        OKMAuthService authService = null;
        OKMDocumentService docService = null;
        OKMAuth okmAuth = null;
        OKMDocument okmDocument = null;

        try {
            authService = new OKMAuthService(new URL(host + "/OKMAuth?wsdl"), AuthServiceName);
            docService = new OKMDocumentService(new URL(host + "/OKMDocument?wsdl"), DocumentServiceName);
            okmAuth = authService.getOKMAuthPort();
            okmDocument = docService.getOKMDocumentPort();
            BindingProvider bpAuth = (BindingProvider) okmAuth;
            BindingProvider bpDocument= (BindingProvider) okmDocument;
            bpAuth.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, host+"/OKMAuth");
            bpDocument.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, host+"/OKMDocument");

            token = okmAuth.login(username, password);

            okmDocument.cancelCheckout(token, document.getPath());

            // Logout OpenKM
            okmAuth.logout(token);
            token = "";
        } catch (Exception ex) {
            if (!token.equals("")) {
                try {
                    // Logout OpenKM
                    okmAuth.logout(token);
                } catch (AccessDeniedException_Exception ex1) {
                } catch (RepositoryException_Exception ex1) {
                }
            }
            throw new OKMException(ex.getMessage() + "\nStackTrace\n" + ex.getStackTrace());
        }
    }

    public static void checkin(String host, String username, String password, OKMDocumentBean document) throws OKMException {
        String token = "";
        OKMAuthService authService = null;
        OKMDocumentService docService = null;
        OKMAuth okmAuth = null;
        OKMDocument okmDocument = null;

        try {
            authService = new OKMAuthService(new URL(host + "/OKMAuth?wsdl"), AuthServiceName);
            docService = new OKMDocumentService(new URL(host + "/OKMDocument?wsdl"), DocumentServiceName);
            okmAuth = authService.getOKMAuthPort();
            okmDocument = docService.getOKMDocumentPort();
            BindingProvider bpAuth = (BindingProvider) okmAuth;
            BindingProvider bpDocument= (BindingProvider) okmDocument;
            bpAuth.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, host+"/OKMAuth");
            bpDocument.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, host+"/OKMDocument");

            token = okmAuth.login(username, password);

            okmDocument.setContent(token, document.getPath(), FileUtil.readFile(document.getLocalFilename()));
            okmDocument.checkin(token, document.getPath(),"");

            // Logout OpenKM
            okmAuth.logout(token);
            token = "";
        } catch (Exception ex) {
            if (!token.equals("")) {
                try {
                    // Logout OpenKM
                    okmAuth.logout(token);
                } catch (AccessDeniedException_Exception ex1) {
                } catch (RepositoryException_Exception ex1) {
                }
            }
            throw new OKMException(ex.getMessage() + "\nStackTrace\n" + ex.getStackTrace());
        }
    }
}
