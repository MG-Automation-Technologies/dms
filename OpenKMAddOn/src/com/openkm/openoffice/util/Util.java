/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.util;

import com.openkm.openoffice.bean.OKMDocumentBean;
import com.openkm.openoffice.bean.OKMPermissionBean;
import com.openkm.openoffice.logic.OKMException;
import com.openkm.openoffice.bean.OOoFormats;
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

    public static String getOKMFolderName(Folder folder) {
        return folder.getPath().substring(folder.getPath().lastIndexOf("/")+1);
    }

    public static boolean hasWritePermission(Folder folder) {
        return ((folder.getPermissions() & OKMPermissionBean.WRITE) == OKMPermissionBean.WRITE);
    }

    public static boolean hasWritePermission(Folder folder, Document doc) {
        return ((folder.getPermissions() & OKMPermissionBean.WRITE) == OKMPermissionBean.WRITE &
                ((doc.getPermissions() & OKMPermissionBean.WRITE)== OKMPermissionBean.WRITE));
    }

    public static String getOKMFileName(String path) throws UnsupportedEncodingException {
        return path.substring(path.lastIndexOf("/")+1);
    }

    public static String getOKMDocumentName(Document doc) {
        return doc.getPath().substring(doc.getPath().lastIndexOf("/")+1);
    }

    public static String getLocalFilenameWithoutCollisions(Document doc, String directoryPath) throws OKMException {
        String fileName = "";
        try {
            fileName = directoryPath + FileUtil.getFolderPathSeparator() + doc.getPath().substring(doc.getPath().lastIndexOf("/")+1);
            File file = new File(fileName);
            int count = 0;
            while (file.exists()) {
                fileName = doc.getPath().substring(doc.getPath().lastIndexOf("/")+1);
                String docExtension = fileName.substring(fileName.lastIndexOf(".")+1);
                String docName = fileName.substring(0,fileName.lastIndexOf("."));
                fileName = directoryPath + FileUtil.getFolderPathSeparator() + docName + "_" + count + "." + docExtension;
                count++;
                file = new File(fileName);
            }
        } catch (Exception ex) {
            throw new OKMException(ex);
        }
        return fileName;
    }

    public static String getDocumentExtension(Document doc) {
        return doc.getPath().substring(doc.getPath().lastIndexOf(".")+1);
    }

    public static String getLocalFileName(String path) throws UnsupportedEncodingException {
        return path.substring(path.lastIndexOf(FileUtil.getFolderPathSeparator())+1);
    }


    public static OKMDocumentBean copy(Document doc) {
        OKMDocumentBean oKMDocumentBean = new OKMDocumentBean();

        oKMDocumentBean.setUUID(doc.getUuid());
        oKMDocumentBean.setName(getOKMDocumentName(doc));
        oKMDocumentBean.setPath(doc.getPath());

        return oKMDocumentBean;
    }

    public static URI convertFileNamePathToURI(String fileName) {
        File file = new File(fileName);
        return file.toURI();
    }

    public static String getOS() {
        return System.getProperty("os.name");
    }

    public static OOoFormats findFormatForFilterName(String filterName) {
        OOoFormats[] formats =  OOoFormats.values();
        for (OOoFormats format:formats) {
            if (format.getFilterName().equals(filterName)) {
                return format;
            }
        }
        return null;
    }
    
    public static String fileNameToOOoURL(final String fName) {
        StringBuilder sLoadUrl = new StringBuilder("file:///");
        sLoadUrl.append(fName.replace('\\', '/').replace("#", "%23"));
        return sLoadUrl.toString();
    }

}
