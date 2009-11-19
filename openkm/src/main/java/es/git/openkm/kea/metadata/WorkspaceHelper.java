/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package es.git.openkm.kea.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * WorkspaceHelper
 * 
 * @author jllort
 *
 */
public class WorkspaceHelper {

	private static Logger log = LoggerFactory.getLogger(WorkspaceHelper.class);

    // the directory names
    private static final String BASE_DIR = getSystemBaseDir();
    private static final String MAIN_DIR = "atlantis";
    private static final String EXTRACT_DIR = "KPE";
    private static final String KP_DATA = "data_files";
    private static final String TEMP_DIR = getTemporaryBaseDir();

    // the paths
    private static final String tempDirPath = TEMP_DIR;
//               new StringBuilder().append(BASE_DIR).append(File.separator)
//                                  .append(MAIN_DIR).append(File.separator)
//                                  .append(EXTRACT_DIR).append(File.separator)
//                                  .append(TEMP_DIR).toString();
    private static final String dataDirPath =
              new StringBuilder().append(BASE_DIR).append(File.separator)
                                  .append(MAIN_DIR).append(File.separator)
                                  .append(EXTRACT_DIR).append(File.separator)
                                  .append(KP_DATA).toString();
    private static final String baseDirPath =
              new StringBuilder().append(BASE_DIR).append(File.separator)
                                 .append(MAIN_DIR).toString();
    private static final String extractDirPath =
              new StringBuilder().append(BASE_DIR).append(File.separator)
                                  .append(MAIN_DIR).append(File.separator)
                                  .append(EXTRACT_DIR).toString();


    // the data and model files
    //TODO: generalise - how????
    private static final File ipsvKeaModel
            = new File(getDataDir() + File.separator + "ipsv_model");
    private static final File ipsvRDF
            = new File(getDataDir() + File.separator + "ipsv-skos.rdf");
    private static final File ipsvStopWords
            = new File(getDataDir() + File.separator + "stopwords_en.txt");
    private static final File generalModel
            = new File(getDataDir() + File.separator + "general_model");
    private static final File ismtKeaModel
            = new File(getDataDir() + File.separator + "ismt_model");
    private static final File ismtRDF
            = new File(getDataDir() + File.separator + "ismt.rdf");


    /**
     *  Static public helper methods
     */

    public static String getTempDir() {
        return tempDirPath;
    }

    public static String getDataDir() {
        return dataDirPath;
    }

    public static String getBaseDir() {
        return baseDirPath;
    }

    public static String getExtractDir() {
        return extractDirPath;
    }
    
    public static String getRealRootDir() {
    	return BASE_DIR;
    }

    public static void setUpDiskArea() {

        try {
            File f = new File(getTempDir());
            if (!f.exists()) {
                f.mkdirs();
            }

            f = new File(getDataDir());
            if (!f.exists()) {
                f.mkdirs();
            }
        } catch (Exception e) {
            log.error("error detected setting up workspace directories",e);
        }
    }

    
    public static void setUpDataFiles() {

        InputStream in;
        FileOutputStream out;
        // need to instantiate temporarily to access classloader
        WorkspaceHelper wsh = new WorkspaceHelper();

        try {
            if(!ipsvKeaModel.exists()) {
                in = wsh.getClass().getClassLoader().getResourceAsStream("vocabulary/ipsv_model");
                out = new FileOutputStream(ipsvKeaModel);
                copyFile(in,out);
            }
            if(!generalModel.exists()) {
                in = wsh.getClass().getClassLoader().getResourceAsStream("vocabulary/general_model");
                out = new FileOutputStream(generalModel);
                copyFile(in,out);
            }
            if(!ismtKeaModel.exists()) {
                in = wsh.getClass().getClassLoader().getResourceAsStream("vocabulary/ismt_model");
                out = new FileOutputStream(ismtKeaModel);
                copyFile(in,out);
            }
            if(!ipsvRDF.exists()) {
                in = wsh.getClass().getClassLoader().getResourceAsStream("vocabulary/ipsv-skos.rdf");
                out = new FileOutputStream(ipsvRDF);
                copyFile(in,out);
            }
            if(!ismtRDF.exists()) {
                in = wsh.getClass().getClassLoader().getResourceAsStream("vocabulary/ismt.rdf");
                out = new FileOutputStream(ismtRDF);
                copyFile(in,out);
            }
            if(!ipsvStopWords.exists()) {
                in = wsh.getClass().getClassLoader().getResourceAsStream("vocabulary/stopwords_en.txt");
                out = new FileOutputStream(ipsvStopWords);
                copyFile(in,out);
            }
        } catch (FileNotFoundException e) {
            log.error("Data files not found: ",e);
        } catch (IOException e) {
            log.error("File copy failed: ",e);
        } catch (Throwable e) {
            log.error("Unexpected exception caught....",e);
        }
    }

    /**
     * private static methods
     */

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            throw e;
        }

    }
    
    private static String getTemporaryBaseDir() {
        // try JBoss
        String dir = System.getProperty("java.io.tmpdir");
        return dir;
    }

    private static String getSystemBaseDir() {
        // try JBoss
        String dir = System.getProperty("jboss.server.home.dir");
        if (dir!=null) {
            log.info("Using JBoss: " + dir);
            return dir + File.separator + "data";
        }
        // try Tomcat
        dir = System.getProperty("catalina.home");
        if (dir!=null) {
            log.info("Using Tomcat: " + dir);
            return dir;
        }
        // otherwise GWT hosted mode default
        dir = System.getProperty("user.dir");
        log.info("Using default dir: " + dir);
        return dir;
    }    
}
