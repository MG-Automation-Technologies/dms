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

package es.git.openkm.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
	private static Logger log = LoggerFactory.getLogger(Config.class);
	
	// JBoss home
	public static String JBOSS_HOME = System.getProperty("jboss.home.dir");
	
	// Multihost
	public static String INSTALL = "";
	
	// Trial
	public static boolean TRIAL = false;

	// Maximum allowed users
	public static int MAX_USERS = 10;

	// Configuration files
	public static String CONFIG_FILE = "OpenKM"+INSTALL+".cfg";
	public static String MIME_FILE = "mime.types";
	public static String NODE_DEFINITIONS = "CustomNodes.cnd";
	
	// Configuration properties
	public static String PROPERTY_REPOSITORY_CONFIG = "repository.config";
	public static String PROPERTY_REPOSITORY_HOME = "repository.home";
	public static String PROPERTY_DEFAULT_USER_ROLE = "default.user.role";
	public static String PROPERTY_DEFAULT_ADMIN_ROLE = "default.admin.role";
	public static String PROPERTY_PRINCIPAL_ADAPTER = "principal.adapter";
	public static String PROPERTY_PRINCIPAL_LDAP_SERVER = "principal.ldap.server";
	public static String PROPERTY_PRINCIPAL_LDAP_SEARCH = "principal.ldap.search";
	public static String PROPERTY_MAX_FILE_SIZE = "max.file.size";
	public static String PROPERTY_RESTRICT_FILE_MIME = "restrict.file.mime";
	public static String PROPERTY_RESTRICT_FILE_EXTENSION = "restrict.file.extension";
	public static String PROPERTY_MAX_SEARCH_RESULTS = "max.search.results";
	public static String PROPERTY_APPLICATION_URL = "application.url";
	public static String PROPERTY_NOTIFY_MESSAGE_BODY = "notify.message.body";
	public static String PROPERTY_NOTIFY_MESSAGE_SUBJECT = "notify.message.subject";
	public static String PROPERTY_SUBSCRIPTION_MESSAGE_BODY = "subscription.message.body";
	public static String PROPERTY_SUBSCRIPTION_MESSAGE_SUBJECT = "subscription.message.subject";
	public static String PROPERTY_SUBSCRIPTION_TWITTER_USER = "notify.twitter.user";
	public static String PROPERTY_SUBSCRIPTION_TWITTER_PASSWORD = "notify.twitter.password";
	public static String PROPERTY_SUBSCRIPTION_TWITTER_STATUS = "notify.twitter.status";
	public static String PROPERTY_UPDATE_INFO = "update.info";
	public static String PROPERTY_SYSTEM_DEMO = "system.demo";
	public static String PROPERTY_SYSTEM_READONLY = "system.readonly";
	public static String PROPERTY_SYSTEM_OCR = "system.ocr";
	public static String PROPERTY_SYSTEM_OPENOFFICE = "system.openoffice";
	public static String PROPERTY_SYSTEM_ANTIVIR = "system.antivir";
	public static String PROPERTY_DEFAULT_LANG = "default.lang";
	
	// Default values
	public static String REPOSITORY_CONFIG = "repository"+INSTALL+".xml";
	public static String REPOSITORY_HOME = "repository"+INSTALL;
	public static String SYSTEM_USER = "system";
	public static String ADMIN_USER = "okmAdmin";
	public static String DEFAULT_USER_ROLE = "UserRole";
	public static String DEFAULT_ADMIN_ROLE = "AdminRole";
	public static String PRINCIPAL_ADAPTER = "es.git.openkm.principal.DatabasePrincipalAdapter";
	public static String PRINCIPAL_LDAP_SERVER = ""; // ldap://vp1135.vp.in:389
	public static String PRINCIPAL_LDAP_SEARCH = ""; // dc=vp1135,dc=vp,dc=in
	private static String MAX_FILE_SIZE_STR = "25"; // 25 Megas
	public static String RESTRICT_FILE_MIME = "off";
	public static String RESTRICT_FILE_EXTENSION = "*~,*.bak";
	public static int MAX_FILE_SIZE = Integer.parseInt(MAX_FILE_SIZE_STR) * 1024 * 1024;
	private static String MAX_SEARCH_RESULTS_STR = "25"; // 25 Documents
	public static int MAX_SEARCH_RESULTS = Integer.parseInt(MAX_SEARCH_RESULTS_STR);
	public static String APPLICATION_URL = "http://localhost:8080/OpenKM/es.git.openkm.frontend.Main/index.jsp";
	public static String NOTIFY_MESSAGE_BODY = "<b>Document: </b><a href=\"{0}\">{1}</a><br/><b>User: </b>{3}<br/><b>Message: </b>{4}<br/>";
	public static String NOTIFY_MESSAGE_SUBJECT = "OpenKM - NOTIFICATION - {0}";
	public static String SUBSCRIPTION_MESSAGE_BODY = "<b>Document: </b><a href=\"{0}\">{1}</a><br/><b>User: </b>{3}<br/><b>Event: </b>{4}<br/><b>Comment: </b>{5}<br/>";
	public static String SUBSCRIPTION_MESSAGE_SUBJECT = "OpenKM - {0} - {1}";
	public static String SUBSCRIPTION_TWITTER_USER = "";
	public static String SUBSCRIPTION_TWITTER_PASSWORD = "";
	public static String SUBSCRIPTION_TWITTER_STATUS = "OpenKM - {0} - {1} - {3} - {4}";
	public static String UPDATE_INFO = "on";
	public static String SYSTEM_DEMO = "off";
	public static String SYSTEM_READONLY = "off";
	public static String SYSTEM_OCR = "";
	public static String SYSTEM_OPENOFFICE = "";
	public static String SYSTEM_ANTIVIR = "";
	public static String DEFAULT_LANG = "";
	public static int SESSION_EXPIRATION = 1800; // 30 mins (session.getMaxInactiveInterval())
	public static Set<String> mimeAccept = new TreeSet<String>();
	
	
	// Registered MIME types
	public static MimetypesFileTypeMap mimeTypes = null;
	
	public static void load() {
		Properties config = new Properties();
		String configFile = JBOSS_HOME+"/"+CONFIG_FILE;
			
		// Read config
		try {
			log.info("** Reading config file " + configFile + " **");
			FileInputStream fis = new FileInputStream(configFile);
			config.load(fis);
			
			REPOSITORY_CONFIG = config.getProperty(PROPERTY_REPOSITORY_CONFIG, REPOSITORY_CONFIG);
			REPOSITORY_HOME = config.getProperty(PROPERTY_REPOSITORY_HOME, REPOSITORY_HOME);
			DEFAULT_USER_ROLE = config.getProperty(PROPERTY_DEFAULT_USER_ROLE, DEFAULT_USER_ROLE);
			DEFAULT_ADMIN_ROLE = config.getProperty(PROPERTY_DEFAULT_ADMIN_ROLE, DEFAULT_ADMIN_ROLE);
			PRINCIPAL_ADAPTER = config.getProperty(PROPERTY_PRINCIPAL_ADAPTER, PRINCIPAL_ADAPTER);
			PRINCIPAL_LDAP_SERVER = config.getProperty(PROPERTY_PRINCIPAL_LDAP_SERVER, PRINCIPAL_LDAP_SERVER);
			PRINCIPAL_LDAP_SEARCH = config.getProperty(PROPERTY_PRINCIPAL_LDAP_SEARCH, PRINCIPAL_LDAP_SEARCH);
			MAX_FILE_SIZE_STR = config.getProperty(PROPERTY_MAX_FILE_SIZE, MAX_FILE_SIZE_STR);
			MAX_FILE_SIZE = Integer.parseInt(MAX_FILE_SIZE_STR) * 1024 * 1024;
			RESTRICT_FILE_MIME = config.getProperty(PROPERTY_RESTRICT_FILE_MIME, RESTRICT_FILE_MIME);
			RESTRICT_FILE_EXTENSION = config.getProperty(PROPERTY_RESTRICT_FILE_EXTENSION, RESTRICT_FILE_EXTENSION);
			MAX_SEARCH_RESULTS_STR = config.getProperty(PROPERTY_MAX_SEARCH_RESULTS, MAX_SEARCH_RESULTS_STR);
			MAX_SEARCH_RESULTS = Integer.parseInt(MAX_SEARCH_RESULTS_STR);
			APPLICATION_URL = config.getProperty(PROPERTY_APPLICATION_URL, APPLICATION_URL);
			SUBSCRIPTION_TWITTER_USER = config.getProperty(PROPERTY_SUBSCRIPTION_TWITTER_USER, SUBSCRIPTION_TWITTER_USER);
			SUBSCRIPTION_TWITTER_PASSWORD = config.getProperty(PROPERTY_SUBSCRIPTION_TWITTER_PASSWORD, SUBSCRIPTION_TWITTER_PASSWORD);
			SUBSCRIPTION_TWITTER_STATUS = config.getProperty(PROPERTY_SUBSCRIPTION_TWITTER_STATUS, SUBSCRIPTION_TWITTER_STATUS);
			NOTIFY_MESSAGE_BODY = config.getProperty(PROPERTY_NOTIFY_MESSAGE_BODY, NOTIFY_MESSAGE_BODY);
			NOTIFY_MESSAGE_SUBJECT = config.getProperty(PROPERTY_NOTIFY_MESSAGE_SUBJECT, NOTIFY_MESSAGE_SUBJECT);
			SUBSCRIPTION_MESSAGE_BODY = config.getProperty(PROPERTY_SUBSCRIPTION_MESSAGE_BODY, SUBSCRIPTION_MESSAGE_BODY);
			SUBSCRIPTION_MESSAGE_SUBJECT = config.getProperty(PROPERTY_SUBSCRIPTION_MESSAGE_SUBJECT, SUBSCRIPTION_MESSAGE_SUBJECT);
			UPDATE_INFO = config.getProperty(PROPERTY_UPDATE_INFO, UPDATE_INFO);
			SYSTEM_DEMO = config.getProperty(PROPERTY_SYSTEM_DEMO, SYSTEM_DEMO);
			SYSTEM_READONLY = config.getProperty(PROPERTY_SYSTEM_READONLY, SYSTEM_READONLY);
			SYSTEM_OCR = config.getProperty(PROPERTY_SYSTEM_OCR, SYSTEM_OCR);
			SYSTEM_OPENOFFICE = config.getProperty(PROPERTY_SYSTEM_OPENOFFICE, SYSTEM_OPENOFFICE);
			SYSTEM_ANTIVIR = config.getProperty(PROPERTY_SYSTEM_ANTIVIR, SYSTEM_ANTIVIR);
			DEFAULT_LANG = config.getProperty(PROPERTY_DEFAULT_LANG, DEFAULT_LANG);
						
			fis.close();
		} catch (FileNotFoundException e) {
			log.warn("** No "+CONFIG_FILE+" file found, set default config **");
		} catch (IOException e) {
			log.warn("** IOError reading "+CONFIG_FILE+", set default config **");
		} finally {
			log.info("** Configuration **");
			log.info("{"+
					PROPERTY_REPOSITORY_CONFIG+"="+REPOSITORY_CONFIG+", "+
					PROPERTY_REPOSITORY_HOME+"="+REPOSITORY_HOME+", "+
					PROPERTY_DEFAULT_USER_ROLE+"="+DEFAULT_USER_ROLE+", "+
					PROPERTY_DEFAULT_ADMIN_ROLE+"="+DEFAULT_ADMIN_ROLE+", "+
					PROPERTY_PRINCIPAL_ADAPTER+"="+PRINCIPAL_ADAPTER+", "+
					PROPERTY_PRINCIPAL_LDAP_SERVER+"="+PRINCIPAL_LDAP_SERVER+", "+
					PROPERTY_PRINCIPAL_LDAP_SEARCH+"="+PRINCIPAL_LDAP_SEARCH+", "+
					PROPERTY_MAX_FILE_SIZE+"="+(MAX_FILE_SIZE/ 1024 / 1024)+", "+
					PROPERTY_RESTRICT_FILE_MIME+"="+RESTRICT_FILE_MIME+", "+
					PROPERTY_RESTRICT_FILE_EXTENSION+"="+RESTRICT_FILE_EXTENSION+", "+
					PROPERTY_MAX_SEARCH_RESULTS+"="+MAX_SEARCH_RESULTS+", "+
					PROPERTY_APPLICATION_URL+"="+APPLICATION_URL+", "+
					PROPERTY_NOTIFY_MESSAGE_BODY+"="+NOTIFY_MESSAGE_BODY+", "+
					PROPERTY_NOTIFY_MESSAGE_SUBJECT+"="+NOTIFY_MESSAGE_SUBJECT+", "+
					PROPERTY_SUBSCRIPTION_MESSAGE_BODY+"="+SUBSCRIPTION_MESSAGE_BODY+", "+
					PROPERTY_SUBSCRIPTION_MESSAGE_SUBJECT+"="+SUBSCRIPTION_MESSAGE_SUBJECT+", "+
					PROPERTY_SUBSCRIPTION_TWITTER_USER+"="+SUBSCRIPTION_TWITTER_USER+", "+
					PROPERTY_SUBSCRIPTION_TWITTER_PASSWORD+"="+SUBSCRIPTION_TWITTER_PASSWORD+", "+
					PROPERTY_SUBSCRIPTION_TWITTER_STATUS+"="+SUBSCRIPTION_TWITTER_STATUS+", "+
					PROPERTY_UPDATE_INFO+"="+UPDATE_INFO+", "+
					PROPERTY_SYSTEM_DEMO+"="+SYSTEM_DEMO+", "+
					PROPERTY_SYSTEM_READONLY+"="+SYSTEM_READONLY+", "+
					PROPERTY_SYSTEM_OCR+"="+SYSTEM_OCR+", "+
					PROPERTY_SYSTEM_OPENOFFICE+"="+SYSTEM_OPENOFFICE+", "+
					PROPERTY_DEFAULT_LANG+"="+DEFAULT_LANG+"}");
			
			if (TRIAL) {
				log.info("*** *** *** *** *** *** *** ***");
				log.info("*** OPENKM ENTERPRISE TRIAL ***");
				log.info("*** *** *** *** *** *** *** ***");
			}
		}
		
		// Read MIME info
		try {
			log.info("** Reading MIME file " + MIME_FILE + " **");
			InputStream is1 = Config.class.getResourceAsStream(MIME_FILE);
			InputStream is2 = Config.class.getResourceAsStream(MIME_FILE);
			
			if (is1 != null && is2 != null) {
				mimeTypes = new MimetypesFileTypeMap(is1);
				BufferedReader br = new BufferedReader(new InputStreamReader(is2));		
				String line;
			
				while ((line = br.readLine()) != null) {
					line = line.trim();
					if (!line.startsWith("#") && line.length() > 0) {
						mimeAccept.add(line.split("\\s")[0]);
					}
				}
			
				br.close();
				is2.close();
				is1.close();
			} else {
				throw new FileNotFoundException(MIME_FILE);
			}
		} catch (FileNotFoundException e) {
			log.warn("** No "+MIME_FILE+" file found **");
			if (RESTRICT_FILE_MIME.equals("on")) {
				log.warn("** File upload disabled **");
			}
		} catch (IOException e) {
			log.warn("** IO Error reading "+MIME_FILE+" **");
			if (RESTRICT_FILE_MIME.equals("on")) {
				log.warn("** File upload disabled **");
			}
		} finally {
			log.info("** MIME Accepted **");
			log.info(mimeAccept.toString());
		}
	}
}
