/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author jllort
 */
public class FileUtil {

    public static byte[] readFile(String filePath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        byte[] bytes = new byte[(int)file.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        int read = 0;
        int numRead = 0;
        while (read < bytes.length && (numRead=dis.read(bytes, read, bytes.length-read)) >= 0) {
            read = read + numRead;
        }
        return bytes;
    }

    public static String getWorkingPath() {
        return System.getProperty("user.home")+getFolderPathSeparator();
    }

    public static String getFolderPathSeparator() {
        return System.getProperty("file.separator");
    }
    
}
