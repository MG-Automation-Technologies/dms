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
    private static final String TEMP_DIR = getTemporaryBaseDir();

    /**
     *  Static public helper methods
     */

    public static String getTempDir() {
        return TEMP_DIR;
    }
    
    public static String getRealRootDir() {
    	return BASE_DIR;
    }
    
    /**
     * getTemporaryBaseDir
     * 
     * @return The temp i/o sytem dir
     */
    private static String getTemporaryBaseDir() {
        
    	String dir = System.getProperty("java.io.tmpdir");
    	if (dir!=null) {
    		return dir;
    	} else {
    		return "";
    	}
    	
    }

    /**
     * getSystemBaseDir
     * 
     * @return The system base dir
     */
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
