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

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.core.Config;

/**
 * WorkspaceHelper
 * 
 * @author jllort
 *
 */
public class WorkspaceHelper {

	private static Logger log = LoggerFactory.getLogger(WorkspaceHelper.class);

    // the directory names
    private static final String WORK_DIR = getWorkingBaseDir();
    private static final String TEMP_DIR = getTemporaryBaseDir();
    
    // The files path
    public static final String RDF_SKOS_VOVABULARY_PATH	= WORK_DIR + 
	 										 	   	  	  (Config.KEA_THESAURUS_SKOS_FILE.startsWith(File.separator)?"":File.separator) +
	 										 	   	  	  Config.KEA_THESAURUS_SKOS_FILE;
    
    public static final String RDF_OWL_VOVABULARY_PATH	= WORK_DIR + 
	  	  												  (Config.KEA_THESAURUS_OWL_FILE.startsWith(File.separator)?"":File.separator) +
	  	  												  Config.KEA_THESAURUS_OWL_FILE;
    
    public static final String KEA_MODEL_PATH 			= WORK_DIR + 
	   												  	  (Config.KEA_MODEL_FILE.startsWith(File.separator)?"":File.separator) +
	   												  	  Config.KEA_MODEL_FILE;
    
    // It's not final because model builder must change this path ( to solve problem with stopwords file on stopwordX class )
    public static String KEA_STOPWORDS_PATH 			= WORK_DIR + 
		  											  	  (Config.KEA_STOPWORDS_FILE.startsWith(File.separator)?"":File.separator) +
		  											  	  Config.KEA_STOPWORDS_FILE;
    
    // Language
    public static final String KEA_LANGUAGE = getLanguage();
    
    // Stop words class name
    public static final String KEA_STOPWORDS_CLASSNAME = getStopWordsClassName();

    /**
     * getTempDir
     * 
     * @return OS temp dir
     */
    public static String getTempDir() {
        return TEMP_DIR;
    }
    
    /**
     * getWorkingDir
     * 
     * @return jboss / tomcat / or user working dir
     */
    public static String getWorkingDir() {
    	return WORK_DIR;
    }
    
    /**
     * getLanguage
     * 
     * @return The language
     */
    public static String getLanguage() {
    	String lang =  "";
    	
    	if (!Config.KEA_STOPWORDS_FILE.equals("")) {
    		lang = Config.KEA_STOPWORDS_FILE.substring(Config.KEA_STOPWORDS_FILE.indexOf("_")+1,
    												   Config.KEA_STOPWORDS_FILE.indexOf("."));
    	}
				   										   
    	return lang;
    }
    
    /**
     * getStopWordsClassName
     * 
     * @return The class name
     */
    public static String getStopWordsClassName() {
    	String className = null;
    	
    	if (KEA_LANGUAGE.equals("en")) {
    		className = "es.git.openkm.kea.stopwords.StopwordsEnglish";
    	} else if (KEA_LANGUAGE.equals("es")) {
    		className = "es.git.openkm.kea.stopwords.StopwordsSpanish";
    	} else if (KEA_LANGUAGE.equals("de")) {
    		className = "es.git.openkm.kea.stopwords.StopwordsGerman";
    	} else if (KEA_LANGUAGE.equals("fr")) {
    		className = "es.git.openkm.kea.stopwords.StopwordsFrench";
    	} else {
    		className = "es.git.openkm.kea.stopwords.Stopwords";
    	}
    	
    	return className;
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
    private static String getWorkingBaseDir() {
        // try JBoss
        String dir = System.getProperty("jboss.home.dir");
        if (dir!=null) {
            log.info("Using JBoss: " + dir);
            return dir;
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
