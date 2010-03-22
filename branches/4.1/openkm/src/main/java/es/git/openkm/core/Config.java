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
import java.io.File;
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
	
	// Default directories
	public static final String HOME_DIR = getHomeDir();
	public static final String TMP_DIR = getTempDir();
	public static final boolean IN_SERVER = inServer(); 
	
	// Preview cache
	public static final String PDF_CACHE = HOME_DIR+File.separator+"cache"+File.separator+"pdf";
	public static final String SWF_CACHE = HOME_DIR+File.separator+"cache"+File.separator+"swf";
	
	// Multihost
	public static String INSTALL = "";

	// Maximum allowed users
	public static int MAX_USERS = 10;

	// Configuration files
	public static String CONFIG_FILE = "OpenKM"+INSTALL+".cfg";
	public static String MIME_FILE = "mime.types";
	public static String NODE_DEFINITIONS = "CustomNodes.cnd";
	
	// Default script
	public static String DEFAULT_SCRIPT = "print(\"UserId: \"+session.getUserID());\n" +
		"print(\"EventType: \"+eventType);\n" +
		"print(\"EventNode: \"+eventNode.getPath());\n" +
		"print(\"ScriptNode: \"+scriptNode.getPath());\n";
	
	// Configuration properties
	public static String PROPERTY_REPOSITORY_CONFIG = "repository.config";
	public static String PROPERTY_REPOSITORY_HOME = "repository.home";
	
	public static String PROPERTY_DEFAULT_USER_ROLE = "default.user.role";
	public static String PROPERTY_DEFAULT_ADMIN_ROLE = "default.admin.role";
	
	public static String PROPERTY_PRINCIPAL_ADAPTER = "principal.adapter";
	public static String PROPERTY_PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS = "principal.database.filter.inactive.users";
	
	// LDAP
	public static String PROPERTY_PRINCIPAL_LDAP_SERVER = "principal.ldap.server";
	public static String PROPERTY_PRINCIPAL_LDAP_SECURITY_PRINCIPAL = "principal.ldap.security.principal";
	public static String PROPERTY_PRINCIPAL_LDAP_SECURITY_CREDENTIALS = "principal.ldap.security.credentials";
	
	public static String PROPERTY_PRINCIPAL_LDAP_USER_SEARCH_BASE = "principal.ldap.user.search.base";
	public static String PROPERTY_PRINCIPAL_LDAP_USER_SEARCH_FILTER = "principal.ldap.user.search.filter";
	public static String PROPERTY_PRINCIPAL_LDAP_USER_ATTRIBUTE = "principal.ldap.user.atribute";
	
	public static String PROPERTY_PRINCIPAL_LDAP_ROLE_SEARCH_BASE = "principal.ldap.role.search.base";
	public static String PROPERTY_PRINCIPAL_LDAP_ROLE_SEARCH_FILTER = "principal.ldap.role.search.filter";
	public static String PROPERTY_PRINCIPAL_LDAP_ROLE_ATTRIBUTE = "principal.ldap.role.atribute";
	
	public static String PROPERTY_PRINCIPAL_LDAP_MAIL_SEARCH_BASE = "principal.ldap.mail.search.base";
	public static String PROPERTY_PRINCIPAL_LDAP_MAIL_SEARCH_FILTER = "principal.ldap.mail.search.filter";
	public static String PROPERTY_PRINCIPAL_LDAP_MAIL_ATTRIBUTE = "principal.ldap.mail.atribute";
	
	public static String PROPERTY_MAX_FILE_SIZE = "max.file.size";
	public static String PROPERTY_MAX_SEARCH_RESULTS = "max.search.results";
	
	public static String PROPERTY_RESTRICT_FILE_MIME = "restrict.file.mime";
	public static String PROPERTY_RESTRICT_FILE_EXTENSION = "restrict.file.extension";
	
	public static String PROPERTY_NOTIFICATION_MESSAGE_SUBJECT = "notification.message.subject";
	public static String PROPERTY_NOTIFICATION_MESSAGE_BODY = "notification.message.body";
	
	public static String PROPERTY_SUBSCRIPTION_MESSAGE_SUBJECT = "subscription.message.subject";
	public static String PROPERTY_SUBSCRIPTION_MESSAGE_BODY = "subscription.message.body";
	
	public static String PROPERTY_SUBSCRIPTION_TWITTER_USER = "notify.twitter.user";
	public static String PROPERTY_SUBSCRIPTION_TWITTER_PASSWORD = "notify.twitter.password";
	public static String PROPERTY_SUBSCRIPTION_TWITTER_STATUS = "notify.twitter.status";
	
	public static String PROPERTY_SYSTEM_DEMO = "system.demo";
	public static String PROPERTY_SYSTEM_READONLY = "system.readonly";
	public static String PROPERTY_SYSTEM_OCR = "system.ocr";
	public static String PROPERTY_SYSTEM_OPENOFFICE = "system.openoffice";
	public static String PROPERTY_SYSTEM_CONVERT = "system.convert";
	public static String PROPERTY_SYSTEM_PDF2SWF = "system.pdf2swf";
	public static String PROPERTY_SYSTEM_ANTIVIR = "system.antivir";
	public static String PROPERTY_SYSTEM_LOGIN_LOWERCASE = "system.login.lowercase";

	public static String PROPERTY_UPDATE_INFO = "update.info";
	public static String PROPERTY_APPLICATION_URL = "application.url";
	public static String PROPERTY_DEFAULT_LANG = "default.lang";
	public static String PROPERTY_USER_KEYWORDS_CACHE = "user.keywords.cache";
	public static String PROPERTY_USER_SIZE_CACHE = "user.size.cache";
		
	/**
	 *  Default values
	 */
	public static String REPOSITORY_CONFIG = "repository"+INSTALL+".xml";
	public static String REPOSITORY_HOME = "repository"+INSTALL;
	
	public static String SYSTEM_USER = "system";
	public static String ADMIN_USER = "okmAdmin";
	
	public static String DEFAULT_USER_ROLE = "UserRole";
	public static String DEFAULT_ADMIN_ROLE = "AdminRole";
	
	public static String WORKFLOW_RUN_CONFIG_FORM = "run_config";
	
	public static String PRINCIPAL_ADAPTER = "es.git.openkm.principal.DatabasePrincipalAdapter";
	public static String PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS = "on";
	
	// LDAP
	public static String PRINCIPAL_LDAP_SERVER = ""; // ldap://phoenix.server:389
	public static String PRINCIPAL_LDAP_SECURITY_PRINCIPAL = ""; //"cn=Administrator,cn=Users,dc=openkm,dc=com"
	public static String PRINCIPAL_LDAP_SECURITY_CREDENTIALS = ""; // "xxxxxx"
	
	public static String PRINCIPAL_LDAP_USER_SEARCH_BASE = ""; // ou=people,dc=openkm,dc=com
	public static String PRINCIPAL_LDAP_USER_SEARCH_FILTER = ""; // (&(objectClass=posixAccount)(!(objectClass=gosaUserTemplate)))
	public static String PRINCIPAL_LDAP_USER_ATTRIBUTE = ""; // uid
	
	public static String PRINCIPAL_LDAP_ROLE_SEARCH_BASE = ""; // ou=groups,dc=openkm,dc=com
	public static String PRINCIPAL_LDAP_ROLE_SEARCH_FILTER = ""; // (&(objectClass=posixGroup)(cn=*)(|(description=*OpenKM*)(cn=users)))
	public static String PRINCIPAL_LDAP_ROLE_ATTRIBUTE = ""; // cn
	
	public static String PRINCIPAL_LDAP_MAIL_SEARCH_BASE = ""; // uid={0},ou=people,dc=openkm,dc=com
	public static String PRINCIPAL_LDAP_MAIL_SEARCH_FILTER = ""; // (&(objectClass=inetOrgPerson)(mail=*))
	public static String PRINCIPAL_LDAP_MAIL_ATTRIBUTE = ""; // mail
	
	private static String MAX_FILE_SIZE_STR = "25"; // 25 Megas
	public static int MAX_FILE_SIZE = Integer.parseInt(MAX_FILE_SIZE_STR) * 1024 * 1024;
	private static String MAX_SEARCH_RESULTS_STR = "261006"; // Almost infinite
	public static int MAX_SEARCH_RESULTS = Integer.parseInt(MAX_SEARCH_RESULTS_STR);
	
	public static String RESTRICT_FILE_MIME = "off";
	public static String RESTRICT_FILE_EXTENSION = "*~,*.bak";

	public static String NOTIFICATION_MESSAGE_SUBJECT = "OpenKM - NOTIFICATION - $documentName";
	public static String NOTIFICATION_MESSAGE_BODY = "<b>Document: </b><a href=\"$documentUrl\">$documentPath</a><br/><b>User: </b>$userId<br/><b>Message: </b>$notificationMessage<br/>";

	public static String SUBSCRIPTION_MESSAGE_SUBJECT = "OpenKM - $eventType - $documentPath";
	public static String SUBSCRIPTION_MESSAGE_BODY = "<b>Document: </b><a href=\"$documentUrl\">$documentPath</a><br/><b>User: </b>$userId<br/><b>Event: </b>$eventType<br/><b>Comment: </b>$subscriptionComment<br/>";
		
	public static String SUBSCRIPTION_TWITTER_USER = "";
	public static String SUBSCRIPTION_TWITTER_PASSWORD = "";
	public static String SUBSCRIPTION_TWITTER_STATUS = "OpenKM - $documentUrl - $documentPath - $userId - $eventType";
	
	public static String SYSTEM_DEMO = "off";
	public static String SYSTEM_READONLY = "off";
	public static String SYSTEM_OCR = "";
	public static String SYSTEM_OPENOFFICE = "off";
	public static String SYSTEM_CONVERT = "";
	public static String SYSTEM_PDF2SWF = "";
	public static String SYSTEM_ANTIVIR = "";
	public static String SYSTEM_LOGIN_LOWERCASE = "off";
	
	public static String UPDATE_INFO = "on";
	public static String APPLICATION_URL = "http://localhost:8080/OpenKM/com.openkm.frontend.Main/index.jsp";
	public static String DEFAULT_LANG = "";
	public static String USER_KEYWORDS_CACHE = "off";
	public static String USER_SIZE_CACHE = "off";

	// KEA
	public static String KEA_THESAURUS_SKOS_FILE = "";
	public static String KEA_THESAURUS_OWL_FILE = "";
	public static String KEA_THESAURUS_VOCABULARY_SERQL = "";
	public static String KEA_THESAURUS_BASE_URL = "";
	public static String KEA_THESAURUS_TREE_ROOT = "";
	public static String KEA_THESAURUS_TREE_CHILDS = "";
	public static String KEA_MODEL_FILE = "";
	public static String KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER = "0";
	public static String KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION = "off";
	public static String KEA_STOPWORDS_FILE = "";

	
	public static int SESSION_EXPIRATION = 1800; // 30 mins (session.getMaxInactiveInterval())
	public static Set<String> mimeAccept = new TreeSet<String>();
	
	// Registered MIME types
	public static MimetypesFileTypeMap mimeTypes = null;
	
	/**
	 * Guess the application server home directory
	 */
	private static String getHomeDir() {
		// Try JBoss
		String dir = System.getProperty("jboss.home.dir");
		if (dir != null) {
			log.info("Using JBoss: " + dir);
			return dir;
		}
		
		// Try Tomcat
		dir = System.getProperty("catalina.home");
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
	 * Guess the system wide temporary directory
	 */
	private static String getTempDir() {
		String dir = System.getProperty("java.io.tmpdir");
		if (dir != null) {
			return dir;
		} else {
			return "";
		}
	}
	
	/**
	 * 
	 */
	private static boolean inServer() {
		if (System.getProperty("jboss.home.dir") != null || System.getProperty("catalina.home") != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void load() {
		Properties config = new Properties();
		String configFile = HOME_DIR+"/"+CONFIG_FILE;
			
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
			PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS = config.getProperty(PROPERTY_PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS, PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS);

			// LDAP
			PRINCIPAL_LDAP_SERVER = config.getProperty(PROPERTY_PRINCIPAL_LDAP_SERVER, PRINCIPAL_LDAP_SERVER);
			PRINCIPAL_LDAP_SECURITY_PRINCIPAL = config.getProperty(PROPERTY_PRINCIPAL_LDAP_SECURITY_PRINCIPAL, PRINCIPAL_LDAP_SECURITY_PRINCIPAL);
			PRINCIPAL_LDAP_SECURITY_CREDENTIALS = config.getProperty(PROPERTY_PRINCIPAL_LDAP_SECURITY_CREDENTIALS, PRINCIPAL_LDAP_SECURITY_CREDENTIALS);
			
			PRINCIPAL_LDAP_USER_SEARCH_BASE = config.getProperty(PROPERTY_PRINCIPAL_LDAP_USER_SEARCH_BASE, PRINCIPAL_LDAP_USER_SEARCH_BASE);
			PRINCIPAL_LDAP_USER_SEARCH_FILTER = config.getProperty(PROPERTY_PRINCIPAL_LDAP_USER_SEARCH_FILTER, PRINCIPAL_LDAP_USER_SEARCH_FILTER);
			PRINCIPAL_LDAP_USER_ATTRIBUTE = config.getProperty(PROPERTY_PRINCIPAL_LDAP_USER_ATTRIBUTE, PRINCIPAL_LDAP_USER_ATTRIBUTE);

			PRINCIPAL_LDAP_ROLE_SEARCH_BASE = config.getProperty(PROPERTY_PRINCIPAL_LDAP_ROLE_SEARCH_BASE, PRINCIPAL_LDAP_ROLE_SEARCH_BASE);
			PRINCIPAL_LDAP_ROLE_SEARCH_FILTER = config.getProperty(PROPERTY_PRINCIPAL_LDAP_ROLE_SEARCH_FILTER, PRINCIPAL_LDAP_ROLE_SEARCH_FILTER);
			PRINCIPAL_LDAP_ROLE_ATTRIBUTE = config.getProperty(PROPERTY_PRINCIPAL_LDAP_ROLE_ATTRIBUTE, PRINCIPAL_LDAP_ROLE_ATTRIBUTE);
			
			PRINCIPAL_LDAP_MAIL_SEARCH_BASE = config.getProperty(PROPERTY_PRINCIPAL_LDAP_MAIL_SEARCH_BASE, PRINCIPAL_LDAP_MAIL_SEARCH_BASE);
			PRINCIPAL_LDAP_MAIL_SEARCH_FILTER = config.getProperty(PROPERTY_PRINCIPAL_LDAP_MAIL_SEARCH_FILTER, PRINCIPAL_LDAP_MAIL_SEARCH_FILTER);
			PRINCIPAL_LDAP_MAIL_ATTRIBUTE= config.getProperty(PROPERTY_PRINCIPAL_LDAP_MAIL_ATTRIBUTE, PRINCIPAL_LDAP_MAIL_ATTRIBUTE);
			
			MAX_FILE_SIZE_STR = config.getProperty(PROPERTY_MAX_FILE_SIZE, MAX_FILE_SIZE_STR);
			MAX_FILE_SIZE = Integer.parseInt(MAX_FILE_SIZE_STR) * 1024 * 1024;
			MAX_SEARCH_RESULTS_STR = config.getProperty(PROPERTY_MAX_SEARCH_RESULTS, MAX_SEARCH_RESULTS_STR);
			MAX_SEARCH_RESULTS = Integer.parseInt(MAX_SEARCH_RESULTS_STR);

			RESTRICT_FILE_MIME = config.getProperty(PROPERTY_RESTRICT_FILE_MIME, RESTRICT_FILE_MIME);
			RESTRICT_FILE_EXTENSION = config.getProperty(PROPERTY_RESTRICT_FILE_EXTENSION, RESTRICT_FILE_EXTENSION);
			
			NOTIFICATION_MESSAGE_SUBJECT = config.getProperty(PROPERTY_NOTIFICATION_MESSAGE_SUBJECT, NOTIFICATION_MESSAGE_SUBJECT);
			NOTIFICATION_MESSAGE_BODY = config.getProperty(PROPERTY_NOTIFICATION_MESSAGE_BODY, NOTIFICATION_MESSAGE_BODY);
			
			SUBSCRIPTION_MESSAGE_SUBJECT = config.getProperty(PROPERTY_SUBSCRIPTION_MESSAGE_SUBJECT, SUBSCRIPTION_MESSAGE_SUBJECT);
			SUBSCRIPTION_MESSAGE_BODY = config.getProperty(PROPERTY_SUBSCRIPTION_MESSAGE_BODY, SUBSCRIPTION_MESSAGE_BODY);
			
			SUBSCRIPTION_TWITTER_USER = config.getProperty(PROPERTY_SUBSCRIPTION_TWITTER_USER, SUBSCRIPTION_TWITTER_USER);
			SUBSCRIPTION_TWITTER_PASSWORD = config.getProperty(PROPERTY_SUBSCRIPTION_TWITTER_PASSWORD, SUBSCRIPTION_TWITTER_PASSWORD);
			SUBSCRIPTION_TWITTER_STATUS = config.getProperty(PROPERTY_SUBSCRIPTION_TWITTER_STATUS, SUBSCRIPTION_TWITTER_STATUS);
			
			SYSTEM_DEMO = config.getProperty(PROPERTY_SYSTEM_DEMO, SYSTEM_DEMO);
			SYSTEM_READONLY = config.getProperty(PROPERTY_SYSTEM_READONLY, SYSTEM_READONLY);
			SYSTEM_OCR = config.getProperty(PROPERTY_SYSTEM_OCR, SYSTEM_OCR);
			SYSTEM_OPENOFFICE = config.getProperty(PROPERTY_SYSTEM_OPENOFFICE, SYSTEM_OPENOFFICE);
			SYSTEM_CONVERT = config.getProperty(PROPERTY_SYSTEM_CONVERT, SYSTEM_CONVERT);
			SYSTEM_PDF2SWF = config.getProperty(PROPERTY_SYSTEM_PDF2SWF, SYSTEM_PDF2SWF);
			SYSTEM_ANTIVIR = config.getProperty(PROPERTY_SYSTEM_ANTIVIR, SYSTEM_ANTIVIR);
			SYSTEM_LOGIN_LOWERCASE = config.getProperty(PROPERTY_SYSTEM_LOGIN_LOWERCASE, SYSTEM_LOGIN_LOWERCASE);
			
			UPDATE_INFO = config.getProperty(PROPERTY_UPDATE_INFO, UPDATE_INFO);
			APPLICATION_URL = config.getProperty(PROPERTY_APPLICATION_URL, APPLICATION_URL);
			DEFAULT_LANG = config.getProperty(PROPERTY_DEFAULT_LANG, DEFAULT_LANG);
			USER_KEYWORDS_CACHE = config.getProperty(PROPERTY_USER_KEYWORDS_CACHE, USER_KEYWORDS_CACHE);
			USER_SIZE_CACHE = config.getProperty(PROPERTY_USER_SIZE_CACHE, USER_SIZE_CACHE);
						
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
					PROPERTY_PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS+"="+PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS+", "+
					
					PROPERTY_PRINCIPAL_LDAP_SERVER+"="+PRINCIPAL_LDAP_SERVER+", "+
					PROPERTY_PRINCIPAL_LDAP_SECURITY_PRINCIPAL+"="+PRINCIPAL_LDAP_SECURITY_PRINCIPAL+","+
					PROPERTY_PRINCIPAL_LDAP_SECURITY_CREDENTIALS+"="+PRINCIPAL_LDAP_SECURITY_CREDENTIALS+","+

					PROPERTY_PRINCIPAL_LDAP_USER_SEARCH_BASE+"="+PRINCIPAL_LDAP_USER_SEARCH_BASE+", "+
					PROPERTY_PRINCIPAL_LDAP_USER_SEARCH_FILTER+"="+PRINCIPAL_LDAP_USER_SEARCH_FILTER+", "+
					PROPERTY_PRINCIPAL_LDAP_USER_ATTRIBUTE+"="+PRINCIPAL_LDAP_USER_ATTRIBUTE+", "+

					PROPERTY_PRINCIPAL_LDAP_ROLE_SEARCH_BASE+"="+PRINCIPAL_LDAP_ROLE_SEARCH_BASE+", "+
					PROPERTY_PRINCIPAL_LDAP_ROLE_SEARCH_FILTER+"="+PRINCIPAL_LDAP_ROLE_SEARCH_FILTER+", "+
					PROPERTY_PRINCIPAL_LDAP_ROLE_ATTRIBUTE+"="+PRINCIPAL_LDAP_ROLE_ATTRIBUTE+", "+

					PROPERTY_PRINCIPAL_LDAP_MAIL_SEARCH_BASE+"="+PRINCIPAL_LDAP_MAIL_SEARCH_BASE+", "+
					PROPERTY_PRINCIPAL_LDAP_MAIL_SEARCH_FILTER+"="+PRINCIPAL_LDAP_MAIL_SEARCH_FILTER+", "+
					PROPERTY_PRINCIPAL_LDAP_MAIL_ATTRIBUTE+"="+PRINCIPAL_LDAP_MAIL_ATTRIBUTE+", "+

					PROPERTY_MAX_FILE_SIZE+"="+(MAX_FILE_SIZE/ 1024 / 1024)+", "+
					PROPERTY_MAX_SEARCH_RESULTS+"="+MAX_SEARCH_RESULTS+", "+
					
					PROPERTY_RESTRICT_FILE_MIME+"="+RESTRICT_FILE_MIME+", "+
					PROPERTY_RESTRICT_FILE_EXTENSION+"="+RESTRICT_FILE_EXTENSION+", "+
					
					PROPERTY_NOTIFICATION_MESSAGE_SUBJECT+"="+NOTIFICATION_MESSAGE_SUBJECT+", "+
					PROPERTY_NOTIFICATION_MESSAGE_BODY+"="+NOTIFICATION_MESSAGE_BODY+", "+
					
					PROPERTY_SUBSCRIPTION_MESSAGE_SUBJECT+"="+SUBSCRIPTION_MESSAGE_SUBJECT+", "+
					PROPERTY_SUBSCRIPTION_MESSAGE_BODY+"="+SUBSCRIPTION_MESSAGE_BODY+", "+
					
					PROPERTY_SUBSCRIPTION_TWITTER_USER+"="+SUBSCRIPTION_TWITTER_USER+", "+
					PROPERTY_SUBSCRIPTION_TWITTER_PASSWORD+"="+SUBSCRIPTION_TWITTER_PASSWORD+", "+
					PROPERTY_SUBSCRIPTION_TWITTER_STATUS+"="+SUBSCRIPTION_TWITTER_STATUS+", "+
					
					PROPERTY_SYSTEM_DEMO+"="+SYSTEM_DEMO+", "+
					PROPERTY_SYSTEM_READONLY+"="+SYSTEM_READONLY+", "+
					PROPERTY_SYSTEM_OCR+"="+SYSTEM_OCR+", "+
					PROPERTY_SYSTEM_OPENOFFICE+"="+SYSTEM_OPENOFFICE+", "+
					PROPERTY_SYSTEM_CONVERT+"="+SYSTEM_CONVERT+", "+
					PROPERTY_SYSTEM_PDF2SWF+"="+SYSTEM_PDF2SWF+", "+
					PROPERTY_SYSTEM_ANTIVIR+"="+SYSTEM_ANTIVIR+", "+

					PROPERTY_APPLICATION_URL+"="+APPLICATION_URL+", "+
					PROPERTY_UPDATE_INFO+"="+UPDATE_INFO+", "+
					PROPERTY_DEFAULT_LANG+"="+DEFAULT_LANG+", "+
					PROPERTY_USER_KEYWORDS_CACHE+"="+USER_KEYWORDS_CACHE+
					PROPERTY_USER_SIZE_CACHE+"="+USER_SIZE_CACHE);
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
