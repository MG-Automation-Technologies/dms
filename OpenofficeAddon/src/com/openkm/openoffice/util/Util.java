/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.util;

import com.openkm.openoffice.bean.OKMDocumentBean;
import com.openkm.openoffice.bean.OKMPermissionBean;
import com.openkm.openoffice.config.DocumentFile;
import com.openkm.openoffice.logic.OKMException;
import com.openkm.ws.client.Document;
import com.openkm.ws.client.Folder;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;

/**
 *
 * @author jllort
 */
public class Util {

    public static void startNewThread(ClassLoader classLoader, Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setContextClassLoader(classLoader);
        thread.start();
    }

    public static String getFolderName(Folder folder) {
        return folder.getPath().substring(folder.getPath().lastIndexOf("/")+1);
    }

    public static boolean hasWritePermission(Folder folder) {
        return ((folder.getPermissions() & OKMPermissionBean.WRITE) == OKMPermissionBean.WRITE);
    }

    public static boolean hasWritePermission(Folder folder, Document doc) {
        return ((folder.getPermissions() & OKMPermissionBean.WRITE) == OKMPermissionBean.WRITE &
                ((doc.getPermissions() & OKMPermissionBean.WRITE)== OKMPermissionBean.WRITE));
    }

    public static String getFileName(String path) throws UnsupportedEncodingException {
        return path.substring(path.lastIndexOf("/")+1);
    }

    public static String getDocumentName(Document doc) {
        return doc.getPath().substring(doc.getPath().lastIndexOf("/")+1);
    }

    public static String getDocumentNameWithoutCollisions(Document doc, String directoryPath) throws OKMException {
        String fileName = "";
        try {
            fileName = directoryPath + "/" + doc.getPath().substring(doc.getPath().lastIndexOf("/")+1);
            File file = new File(fileName);
            int count = 0;
            while (file.exists()) {
                fileName = doc.getPath().substring(doc.getPath().lastIndexOf("/")+1);
                String docExtension = fileName.substring(fileName.lastIndexOf(".")+1);
                String docName = fileName.substring(0,fileName.lastIndexOf(".")-1);
                fileName = directoryPath + "/" + docName + "_" + count + "." + docExtension;
                count++;
                file = new File(fileName);
            }
        } catch (Exception ex) {
            throw new OKMException(ex.getMessage());
        }
        return fileName;
    }

    public static String getDocumentExtension(Document doc) {
        return doc.getPath().substring(doc.getPath().lastIndexOf(".")+1);
    }

    public static OKMDocumentBean copy(Document doc) {
        OKMDocumentBean oKMDocumentBean = new OKMDocumentBean();

        oKMDocumentBean.setUUID(doc.getUuid());
        oKMDocumentBean.setName(getDocumentName(doc));
        oKMDocumentBean.setPath(doc.getPath());

        return oKMDocumentBean;
    }

    public static URI convertFileNamePathToURI(String fileName) {
        File file = new File(fileName);
        return file.toURI();
    }
}
