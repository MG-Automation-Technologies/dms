/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
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

package com.openkm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerDetector {
	private static Logger log = LoggerFactory.getLogger(ServerDetector.class);
	private static final String JBOSS_PROPERTY = "jboss.home.dir";
	private static final String TOMCAT_PROPERTY = "catalina.home";
	
	/**
	 * Guess the application server home directory
	 */
	public static String getHomeDir() {
		// Try JBoss
		String dir = System.getProperty(JBOSS_PROPERTY);
		if (isJBoss()) {
			log.info("Using JBoss: " + dir);
			return dir;
		}
		
		// Try Tomcat
		dir = System.getProperty(TOMCAT_PROPERTY);
		if (dir != null) {
			log.info("Using Tomcat: " + dir);
			return dir;
		}
		
		// Otherwise GWT hosted mode
		dir = System.getProperty("user.dir") + "/src/test/resources";
		log.info("Using default dir: " + dir);
		return dir;
	}
	
	/**
	 * Detect if running in JBoss 
	 */
	public static boolean isJBoss() {
		return System.getProperty(JBOSS_PROPERTY) != null;
	}
	
	/**
	 * Detect if running in Tomcat
	 */
	public static boolean isTomcat() {
		return !isJBoss() && System.getProperty(TOMCAT_PROPERTY) != null;
	}
	
	/**
	 * Guess JNDI base
	 */
	public static String getJndiBase() {
		if (isJBoss()) return "java:/";
		else if (isTomcat()) return "java:/comp/env/";
		else return "";
	}
	
	/**
	 * Guess the system wide temporary directory
	 */
	public static String getTempDir() {
		String dir = System.getProperty("java.io.tmpdir");
		if (dir != null) {
			return dir;
		} else {
			return "";
		}
	}
	
	/**
	 * Guess the system null device
	 */
	public static String getNullDevice() {
		String os = System.getProperty("os.name").toLowerCase();
		
		if (os.contains("linux") || os.contains("mac os")) {
			return "/dev/null";
		} else if (os.contains("windows")) {
			return "NUL:";
		} else {
			return null;
		}
	}
	
	/**
	 * Test if is running in application server
	 */
	public static boolean inServer() {
		return isJBoss() || isTomcat();
	}
}
