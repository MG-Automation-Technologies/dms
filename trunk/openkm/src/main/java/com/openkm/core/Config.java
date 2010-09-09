/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
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

package com.openkm.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.dao.MimeTypeDAO;
import com.openkm.dao.bean.MimeType;

public class Config {
	private static Logger log = LoggerFactory.getLogger(Config.class);
	public static TreeMap<String, String> values = new TreeMap<String, String>();
	
	// Default directories
	public static final String HOME_DIR = getHomeDir();
	public static final String TMP_DIR = getTempDir();
	public static final boolean IN_SERVER = inServer();
	
	// Preview cache
	public static final String PDF_CACHE = HOME_DIR+File.separator+"cache"+File.separator+"pdf";
	public static final String SWF_CACHE = HOME_DIR+File.separator+"cache"+File.separator+"swf";
	
	// Multihost
	public static final String INSTALL = "";
	
	// Scripting
	public static final String START_SCRIPT = "start.bsh";
	public static final String STOP_SCRIPT = "stop.bsh";
	public static final String START_JAR = "start.jar";
	public static final String STOP_JAR = "stop.jar";
	
	// Configuration files
	public static final String CONFIG_FILE = "OpenKM"+INSTALL+".cfg";
	public static final String MIME_FILE = "mime.types";
	public static final String NODE_DEFINITIONS = "CustomNodes.cnd";
	public static final String DATASOURCE = "java:/OpenKM"+INSTALL+"DS";
	
	// Default script
	public static final String DEFAULT_SCRIPT = "print(\"UserId: \"+session.getUserID());\n" +
		"print(\"EventType: \"+eventType);\n" +
		"print(\"EventNode: \"+eventNode.getPath());\n" +
		"print(\"ScriptNode: \"+scriptNode.getPath());\n";
	
	// Configuration properties
	public static final String PROPERTY_REPOSITORY_CONFIG = "repository.config";
	public static final String PROPERTY_REPOSITORY_HOME = "repository.home";
	
	public static final String PROPERTY_DEFAULT_USER_ROLE = "default.user.role";
	public static final String PROPERTY_DEFAULT_ADMIN_ROLE = "default.admin.role";
	
	// Workflow
	public static final String PROPERTY_WORKFLOW_START_TASK_AUTO_RUN = "workflow.start.task.auto.run";
	
	// Principal
	public static final String PROPERTY_PRINCIPAL_ADAPTER = "principal.adapter";
	public static final String PROPERTY_PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS = "principal.database.filter.inactive.users";
	
	// LDAP
	public static final String PROPERTY_PRINCIPAL_LDAP_SERVER = "principal.ldap.server";
	public static final String PROPERTY_PRINCIPAL_LDAP_SECURITY_PRINCIPAL = "principal.ldap.security.principal";
	public static final String PROPERTY_PRINCIPAL_LDAP_SECURITY_CREDENTIALS = "principal.ldap.security.credentials";
	
	public static final String PROPERTY_PRINCIPAL_LDAP_USER_SEARCH_BASE = "principal.ldap.user.search.base";
	public static final String PROPERTY_PRINCIPAL_LDAP_USER_SEARCH_FILTER = "principal.ldap.user.search.filter";
	public static final String PROPERTY_PRINCIPAL_LDAP_USER_ATTRIBUTE = "principal.ldap.user.attribute";
	
	public static final String PROPERTY_PRINCIPAL_LDAP_ROLE_SEARCH_BASE = "principal.ldap.role.search.base";
	public static final String PROPERTY_PRINCIPAL_LDAP_ROLE_SEARCH_FILTER = "principal.ldap.role.search.filter";
	public static final String PROPERTY_PRINCIPAL_LDAP_ROLE_ATTRIBUTE = "principal.ldap.role.attribute";
	
	public static final String PROPERTY_PRINCIPAL_LDAP_USERS_BY_ROLE_SEARCH_FILTER = "principal.ldap.users.by.role.search.filter";
	public static final String PROPERTY_PRINCIPAL_LDAP_ROLES_BY_USER_SEARCH_FILTER = "principal.ldap.roles.by.user.search.filter";
	
	public static final String PROPERTY_PRINCIPAL_LDAP_MAIL_SEARCH_BASE = "principal.ldap.mail.search.base";
	public static final String PROPERTY_PRINCIPAL_LDAP_MAIL_SEARCH_FILTER = "principal.ldap.mail.search.filter";
	public static final String PROPERTY_PRINCIPAL_LDAP_MAIL_ATTRIBUTE = "principal.ldap.mail.attribute";
	
	public static final String PROPERTY_MAX_FILE_SIZE = "max.file.size";
	public static final String PROPERTY_MAX_SEARCH_RESULTS = "max.search.results";
	
	public static final String PROPERTY_RESTRICT_FILE_MIME = "restrict.file.mime";
	public static final String PROPERTY_RESTRICT_FILE_EXTENSION = "restrict.file.extension";
	
	public static final String PROPERTY_NOTIFICATION_MESSAGE_SUBJECT = "notification.message.subject";
	public static final String PROPERTY_NOTIFICATION_MESSAGE_BODY = "notification.message.body";
	
	public static final String PROPERTY_SUBSCRIPTION_MESSAGE_SUBJECT = "subscription.message.subject";
	public static final String PROPERTY_SUBSCRIPTION_MESSAGE_BODY = "subscription.message.body";
	
	public static final String PROPERTY_SUBSCRIPTION_TWITTER_USER = "notify.twitter.user";
	public static final String PROPERTY_SUBSCRIPTION_TWITTER_PASSWORD = "notify.twitter.password";
	public static final String PROPERTY_SUBSCRIPTION_TWITTER_STATUS = "notify.twitter.status";
	
	public static final String PROPERTY_SYSTEM_DEMO = "system.demo";
	public static final String PROPERTY_SYSTEM_READONLY = "system.readonly";
	public static final String PROPERTY_SYSTEM_MAINTENANCE = "system.maintenance";
	public static final String PROPERTY_SYSTEM_OCR = "system.ocr";
	public static final String PROPERTY_SYSTEM_OPENOFFICE = "system.openoffice";
	public static final String PROPERTY_SYSTEM_IMG2PDF = "system.img2pdf";
	public static final String PROPERTY_SYSTEM_PDF2SWF = "system.pdf2swf";
	public static final String PROPERTY_SYSTEM_ANTIVIR = "system.antivir";
	public static final String PROPERTY_SYSTEM_LOGIN_LOWERCASE = "system.login.lowercase";
	
	public static final String PROPERTY_UPDATE_INFO = "update.info";
	public static final String PROPERTY_APPLICATION_URL = "application.url";
	public static final String PROPERTY_USER_KEYWORDS_CACHE = "user.keywords.cache";
	public static final String PROPERTY_USER_SIZE_CACHE = "user.size.cache";
	
	// Schedule
	public static final String PROPERTY_SCHEDULE_REPOSITORY_INFO = "schedule.repository.info";
	public static final String PROPERTY_SCHEDULE_MAIL_IMPORTER = "schedule.mail.importer";
	public static final String PROPERTY_SCHEDULE_SESSION_KEEPALIVE = "schedule.session.keepalive";
	public static final String PROPERTY_SCHEDULE_DASHBOARD_REFRESH = "schedule.dashboard.refresh";
	
	// KEA
	public static final String PROPERTY_KEA_THESAURUS_SKOS_FILE = "kea.thesaurus.skos.file";
	public static final String PROPERTY_KEA_THESAURUS_OWL_FILE = "kea.thesaurus.owl.file";
	public static final String PROPERTY_KEA_THESAURUS_VOCABULARY_SERQL = "kea.thesaurus.vocabulary.serql";
	public static final String PROPERTY_KEA_THESAURUS_BASE_URL = "kea.thesaurus.base.url";
	public static final String PROPERTY_KEA_THESAURUS_TREE_ROOT = "kea.thesaurus.tree.root";
	public static final String PROPERTY_KEA_THESAURUS_TREE_CHILDS = "kea.thesaurus.tree.childs";
	public static final String PROPERTY_KEA_MODEL_FILE = "kea.model.file";
	public static final String PROPERTY_KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER = "kea.automatic.keyword.extraction.number";
	public static final String PROPERTY_KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION = "kea.automatic.keyword.extraction.restriction";
	public static final String PROPERTY_KEA_STOPWORDS_FILE = "kea.stopwords.file";
	public static final String PROPERTY_KEA_STOPWORDS_LANGUAGE = "kea.stopwords.language";
	
	// Validator
	public static final String PROPERTY_VALIDATOR_PASSWORD = "validator.password";
	
	public static final String PROPERTY_VALIDATOR_PASSWORD_MIN_LENGTH = "validator.password.min.length";
	public static final String PROPERTY_VALIDATOR_PASSWORD_MAX_LENGTH = "validator.password.max.length";
	public static final String PROPERTY_VALIDATOR_PASSWORD_MIN_LOWERCASE = "validator.password.min.lowercase";
	public static final String PROPERTY_VALIDATOR_PASSWORD_MIN_UPPERCASE = "validator.password.min.uppercase";
	public static final String PROPERTY_VALIDATOR_PASSWORD_MIN_DIGITS = "validator.password.min.digits";
	public static final String PROPERTY_VALIDATOR_PASSWORD_MIN_SPECIAL = "validator.password.mini.special";
	
	public static final String PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_LENGTH = "validator.password.error.min.length";
	public static final String PROPERTY_VALIDATOR_PASSWORD_ERROR_MAX_LENGTH = "validator.password.error.max.length";	
	public static final String PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_LOWERCASE = "validator.password.error.min.lowercase";
	public static final String PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_UPPERCASE = "validator.password.error.max.uppercase";
	public static final String PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_DIGITS = "validator.password.error.min.digits";
	public static final String PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_SPECIAL = "validator.password.error.min.special";

	// Hibernate
	public static final String PROPERTY_HIBERNATE_DIALECT = "hibernate.dialect";
	public static final String PROPERTY_HIBERNATE_DATASOURCE = "hibernate.datasource";
	public static final String PROPERTY_HIBERNATE_HBM2DDL = "hibernate.hbm2ddl";
	
	/**
	 *  Default values
	 */
	public static String REPOSITORY_CONFIG = "repository"+INSTALL+".xml";
	public static String REPOSITORY_HOME = "repository"+INSTALL;
	
	public static String SYSTEM_USER = "system";
	public static String ADMIN_USER = "okmAdmin";
	
	public static String DEFAULT_USER_ROLE = "UserRole";
	public static String DEFAULT_ADMIN_ROLE = "AdminRole";
	
	// Workflow
	public static String WORKFLOW_RUN_CONFIG_FORM = "run_config";
	public static String WORKFLOW_START_TASK_AUTO_RUN_STR = "on";
	public static boolean WORKFLOW_START_TASK_AUTO_RUN = "on".equalsIgnoreCase(WORKFLOW_START_TASK_AUTO_RUN_STR);
	public static String WORKFLOW_PROCESS_INSTANCE_VARIABLE_UUID = "uuid";
	public static String WORKFLOW_PROCESS_INSTANCE_VARIABLE_PATH = "path";
	
	// Principal
	public static String PRINCIPAL_ADAPTER = "com.openkm.principal.DatabasePrincipalAdapter";
	public static String PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS_STR = "on";
	public static boolean PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS = "on".equalsIgnoreCase(PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS_STR);
	
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
	
	public static String PRINCIPAL_LDAP_USERS_BY_ROLE_SEARCH_FILTER = ""; // (&(objectClass=group)(cn={0}))
	public static String PRINCIPAL_LDAP_ROLES_BY_USER_SEARCH_FILTER = ""; // (&(objectClass=group)(cn={0}))
	
	public static String PRINCIPAL_LDAP_MAIL_SEARCH_BASE = ""; // uid={0},ou=people,dc=openkm,dc=com
	public static String PRINCIPAL_LDAP_MAIL_SEARCH_FILTER = ""; // (&(objectClass=inetOrgPerson)(mail=*))
	public static String PRINCIPAL_LDAP_MAIL_ATTRIBUTE = ""; // mail
	
	private static String MAX_FILE_SIZE_STR = "64"; // 64 Megas
	public static int MAX_FILE_SIZE = Integer.parseInt(MAX_FILE_SIZE_STR) * 1024 * 1024;
	private static String MAX_SEARCH_RESULTS_STR = "261006"; // Almost infinite
	public static int MAX_SEARCH_RESULTS = Integer.parseInt(MAX_SEARCH_RESULTS_STR);
	
	private static String RESTRICT_FILE_MIME_STR = "off";
	public static boolean RESTRICT_FILE_MIME = "on".equalsIgnoreCase(RESTRICT_FILE_MIME_STR);
	public static String RESTRICT_FILE_EXTENSION = "*~,*.bak";

	public static String NOTIFICATION_MESSAGE_SUBJECT = "OpenKM - NOTIFICATION - $documentName";
	public static String NOTIFICATION_MESSAGE_BODY = "<b>Document: </b><a href=\"$documentUrl\">$documentPath</a><br/><b>User: </b>$userId<br/><b>Message: </b>$notificationMessage<br/>";

	public static String SUBSCRIPTION_MESSAGE_SUBJECT = "OpenKM - $eventType - $documentPath";
	public static String SUBSCRIPTION_MESSAGE_BODY = "<b>Document: </b><a href=\"$documentUrl\">$documentPath</a><br/><b>User: </b>$userId<br/><b>Event: </b>$eventType<br/><b>Comment: </b>$subscriptionComment<br/>";
		
	public static String SUBSCRIPTION_TWITTER_USER = "";
	public static String SUBSCRIPTION_TWITTER_PASSWORD = "";
	public static String SUBSCRIPTION_TWITTER_STATUS = "OpenKM - $documentUrl - $documentPath - $userId - $eventType";
	
	private static String SYSTEM_DEMO_STR = "off";
	public static boolean SYSTEM_DEMO = "on".equalsIgnoreCase(SYSTEM_DEMO_STR);
	private static String SYSTEM_MAINTENANCE_STR = "off";
	public static boolean SYSTEM_MAINTENANCE = "on".equalsIgnoreCase(SYSTEM_MAINTENANCE_STR); 
	private static String SYSTEM_READONLY_STR = "off";
	public static boolean SYSTEM_READONLY = "on".equalsIgnoreCase(SYSTEM_READONLY_STR);
	public static String SYSTEM_OCR = "";
	public static String SYSTEM_OPENOFFICE = "";
	public static String SYSTEM_IMG2PDF = "";
	public static String SYSTEM_PDF2SWF = "";
	public static String SYSTEM_ANTIVIR = "";
	private static String SYSTEM_LOGIN_LOWERCASE_STR = "off";
	public static boolean SYSTEM_LOGIN_LOWERCASE = "on".equalsIgnoreCase(SYSTEM_LOGIN_LOWERCASE_STR);
	
	private static String UPDATE_INFO_STR = "on";
	public static boolean UPDATE_INFO = "on".equalsIgnoreCase(UPDATE_INFO_STR);
	public static String APPLICATION_URL = "http://localhost:8080/OpenKM"+INSTALL+"/com.openkm.frontend.Main/index.jsp";
	public static String APPLICATION_BASE = getBase(APPLICATION_URL);
	public static String DEFAULT_LANG = "";
	private static String USER_KEYWORDS_CACHE_STR = "off";
	public static boolean USER_KEYWORDS_CACHE = "on".equalsIgnoreCase(USER_KEYWORDS_CACHE_STR);
	private static String USER_SIZE_CACHE_STR = "off";
	public static boolean USER_SIZE_CACHE = "on".equalsIgnoreCase(USER_SIZE_CACHE_STR);
	
	// Schedule
	private static String SCHEDULE_REPOSITORY_INFO_STR = "1440"; // 24*60 min = 24 hours
	public static long SCHEDULE_REPOSITORY_INFO = Long.parseLong(SCHEDULE_REPOSITORY_INFO_STR) * 60 * 1000;
	private static String SCHEDULE_MAIL_IMPORTER_STR = "60"; // 60 min = 1 hour
	public static long SCHEDULE_MAIL_IMPORTER = Long.parseLong(SCHEDULE_MAIL_IMPORTER_STR) * 60 * 1000;
	private static String SCHEDULE_SESSION_KEEPALIVE_STR = "15"; // 15 min = 1/4 hour
	public static long SCHEDULE_SESSION_KEEPALIVE = Long.parseLong(SCHEDULE_SESSION_KEEPALIVE_STR) * 60 * 1000;
	private static String SCHEDULE_DASHBOARD_REFRESH_STR = "30"; // 30 min = 1/2 hour
	public static long SCHEDULE_DASHBOARD_REFRESH = Long.parseLong(SCHEDULE_DASHBOARD_REFRESH_STR) * 60 * 1000;

	// KEA
	public static String KEA_THESAURUS_SKOS_FILE = "";
	public static String KEA_THESAURUS_OWL_FILE = "";
	public static String KEA_THESAURUS_VOCABULARY_SERQL = "";
	public static String KEA_THESAURUS_BASE_URL = "";
	public static String KEA_THESAURUS_TREE_ROOT = "";
	public static String KEA_THESAURUS_TREE_CHILDS = "";
	public static String KEA_MODEL_FILE = "";
	private static String KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER_STR = "0";
	public static int KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER = Integer.parseInt(KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER_STR);
	private static String KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION_STR = "off";
	public static boolean KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION = "on".equalsIgnoreCase(KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION_STR);
	public static String KEA_STOPWORDS_FILE = "";

	// Validator
	public static String VALIDATOR_PASSWORD = "com.openkm.validator.password.NoPasswordValidator";
	
	private static String VALIDATOR_PASSWORD_MIN_LENGTH_STR = "0";
	public static int VALIDATOR_PASSWORD_MIN_LENGTH = Integer.parseInt(VALIDATOR_PASSWORD_MIN_LENGTH_STR);
	private static String VALIDATOR_PASSWORD_MAX_LENGTH_STR = "0";
	public static int VALIDATOR_PASSWORD_MAX_LENGTH = Integer.parseInt(VALIDATOR_PASSWORD_MAX_LENGTH_STR);
	private static String VALIDATOR_PASSWORD_MIN_LOWERCASE_STR = "0";
	public static int VALIDATOR_PASSWORD_MIN_LOWERCASE = Integer.parseInt(VALIDATOR_PASSWORD_MIN_LOWERCASE_STR);
	private static String VALIDATOR_PASSWORD_MIN_UPPERCASE_STR = "0";
	public static int VALIDATOR_PASSWORD_MIN_UPPERCASE = Integer.parseInt(VALIDATOR_PASSWORD_MIN_UPPERCASE_STR);
	private static String VALIDATOR_PASSWORD_MIN_DIGITS_STR = "0";
	public static int VALIDATOR_PASSWORD_MIN_DIGITS = Integer.parseInt(VALIDATOR_PASSWORD_MIN_DIGITS_STR);
	private static String VALIDATOR_PASSWORD_MIN_SPECIAL_STR = "0";
	public static int VALIDATOR_PASSWORD_MIN_SPECIAL = Integer.parseInt(VALIDATOR_PASSWORD_MIN_SPECIAL_STR);
	
	public static String VALIDATOR_PASSWORD_ERROR_MIN_LENGTH = "Password error: too short";
	public static String VALIDATOR_PASSWORD_ERROR_MAX_LENGTH = "Password error: too long";	
	public static String VALIDATOR_PASSWORD_ERROR_MIN_LOWERCASE = "Password error: too few lowercase characters";
	public static String VALIDATOR_PASSWORD_ERROR_MIN_UPPERCASE = "Password error: too few uppercase characters";
	public static String VALIDATOR_PASSWORD_ERROR_MIN_DIGITS = "Password error: too few digits";
	public static String VALIDATOR_PASSWORD_ERROR_MIN_SPECIAL = "Password error: too few special characters";
	
	// Hibernate
	public static String HIBERNATE_DIALECT = "org.hibernate.dialect.HSQLDialect";
	public static String HIBERNATE_DATASOURCE = "java:/OpenKMDS";
	public static String HIBERNATE_HBM2DDL = "create";
	
	// Chat
	private static String CHAT_ENABLED_STR = "on";
	public static boolean CHAT_ENABLED = "on".equalsIgnoreCase(CHAT_ENABLED_STR);
	private static String CHAT_AUTOLOGIN_STR = "on";
	public static boolean CHAT_AUTOLOGIN = "on".equalsIgnoreCase(CHAT_AUTOLOGIN_STR);
	
	// Misc
	public static int SESSION_EXPIRATION = 1800; // 30 mins (session.getMaxInactiveInterval())
	
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
	 * Test if is running in application server
	 */
	private static boolean inServer() {
		if (System.getProperty("jboss.home.dir") != null || System.getProperty("catalina.home") != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Get url base
	 */
	private static String getBase(String url) {
		String ret = "";
		
		int idx1 = url.lastIndexOf('/');
		if (idx1 > 0) ret = url.substring(0, idx1);
		int idx2 = ret.lastIndexOf('/');
		if (idx2 > 0) ret = ret.substring(0, idx2);
		
		return ret;
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
			values.put(PROPERTY_REPOSITORY_CONFIG, REPOSITORY_CONFIG);
			REPOSITORY_HOME = config.getProperty(PROPERTY_REPOSITORY_HOME, REPOSITORY_HOME);
			values.put(PROPERTY_REPOSITORY_HOME, REPOSITORY_HOME);
			
			DEFAULT_USER_ROLE = config.getProperty(PROPERTY_DEFAULT_USER_ROLE, DEFAULT_USER_ROLE);
			values.put(PROPERTY_DEFAULT_USER_ROLE, DEFAULT_USER_ROLE);
			DEFAULT_ADMIN_ROLE = config.getProperty(PROPERTY_DEFAULT_ADMIN_ROLE, DEFAULT_ADMIN_ROLE);
			values.put(PROPERTY_DEFAULT_ADMIN_ROLE, DEFAULT_ADMIN_ROLE);
			
			// Workflow
			WORKFLOW_START_TASK_AUTO_RUN_STR = config.getProperty(PROPERTY_WORKFLOW_START_TASK_AUTO_RUN, WORKFLOW_START_TASK_AUTO_RUN_STR);
			WORKFLOW_START_TASK_AUTO_RUN = "on".equalsIgnoreCase(WORKFLOW_START_TASK_AUTO_RUN_STR);
			values.put(PROPERTY_WORKFLOW_START_TASK_AUTO_RUN, WORKFLOW_START_TASK_AUTO_RUN_STR+" ("+WORKFLOW_START_TASK_AUTO_RUN+")");
			
			// Principal
			PRINCIPAL_ADAPTER = config.getProperty(PROPERTY_PRINCIPAL_ADAPTER, PRINCIPAL_ADAPTER);
			values.put(PROPERTY_PRINCIPAL_ADAPTER, PRINCIPAL_ADAPTER);
			PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS_STR = config.getProperty(PROPERTY_PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS, PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS_STR);
			PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS = "on".equalsIgnoreCase(PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS_STR);
			values.put(PROPERTY_PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS, PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS_STR+" ("+PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS+")");

			// LDAP
			PRINCIPAL_LDAP_SERVER = config.getProperty(PROPERTY_PRINCIPAL_LDAP_SERVER, PRINCIPAL_LDAP_SERVER);
			values.put(PROPERTY_PRINCIPAL_LDAP_SERVER, PRINCIPAL_LDAP_SERVER);
			PRINCIPAL_LDAP_SECURITY_PRINCIPAL = config.getProperty(PROPERTY_PRINCIPAL_LDAP_SECURITY_PRINCIPAL, PRINCIPAL_LDAP_SECURITY_PRINCIPAL);
			values.put(PROPERTY_PRINCIPAL_LDAP_SECURITY_PRINCIPAL, PRINCIPAL_LDAP_SECURITY_PRINCIPAL);
			PRINCIPAL_LDAP_SECURITY_CREDENTIALS = config.getProperty(PROPERTY_PRINCIPAL_LDAP_SECURITY_CREDENTIALS, PRINCIPAL_LDAP_SECURITY_CREDENTIALS);
			values.put(PROPERTY_PRINCIPAL_LDAP_SECURITY_CREDENTIALS, PRINCIPAL_LDAP_SECURITY_CREDENTIALS);
			
			PRINCIPAL_LDAP_USER_SEARCH_BASE = config.getProperty(PROPERTY_PRINCIPAL_LDAP_USER_SEARCH_BASE, PRINCIPAL_LDAP_USER_SEARCH_BASE);
			values.put(PROPERTY_PRINCIPAL_LDAP_USER_SEARCH_BASE, PRINCIPAL_LDAP_USER_SEARCH_BASE);
			PRINCIPAL_LDAP_USER_SEARCH_FILTER = config.getProperty(PROPERTY_PRINCIPAL_LDAP_USER_SEARCH_FILTER, PRINCIPAL_LDAP_USER_SEARCH_FILTER);
			values.put(PROPERTY_PRINCIPAL_LDAP_USER_SEARCH_FILTER, PRINCIPAL_LDAP_USER_SEARCH_FILTER);
			PRINCIPAL_LDAP_USER_ATTRIBUTE = config.getProperty(PROPERTY_PRINCIPAL_LDAP_USER_ATTRIBUTE, PRINCIPAL_LDAP_USER_ATTRIBUTE);
			values.put(PROPERTY_PRINCIPAL_LDAP_USER_ATTRIBUTE, PRINCIPAL_LDAP_USER_ATTRIBUTE);

			PRINCIPAL_LDAP_ROLE_SEARCH_BASE = config.getProperty(PROPERTY_PRINCIPAL_LDAP_ROLE_SEARCH_BASE, PRINCIPAL_LDAP_ROLE_SEARCH_BASE);
			values.put(PROPERTY_PRINCIPAL_LDAP_ROLE_SEARCH_BASE, PRINCIPAL_LDAP_ROLE_SEARCH_BASE);
			PRINCIPAL_LDAP_ROLE_SEARCH_FILTER = config.getProperty(PROPERTY_PRINCIPAL_LDAP_ROLE_SEARCH_FILTER, PRINCIPAL_LDAP_ROLE_SEARCH_FILTER);
			values.put(PROPERTY_PRINCIPAL_LDAP_ROLE_SEARCH_FILTER, PRINCIPAL_LDAP_ROLE_SEARCH_FILTER);
			PRINCIPAL_LDAP_ROLE_ATTRIBUTE = config.getProperty(PROPERTY_PRINCIPAL_LDAP_ROLE_ATTRIBUTE, PRINCIPAL_LDAP_ROLE_ATTRIBUTE);
			values.put(PROPERTY_PRINCIPAL_LDAP_ROLE_ATTRIBUTE, PRINCIPAL_LDAP_ROLE_ATTRIBUTE);
			
			PRINCIPAL_LDAP_USERS_BY_ROLE_SEARCH_FILTER = config.getProperty(PROPERTY_PRINCIPAL_LDAP_USERS_BY_ROLE_SEARCH_FILTER, PRINCIPAL_LDAP_USERS_BY_ROLE_SEARCH_FILTER);
			values.put(PROPERTY_PRINCIPAL_LDAP_USERS_BY_ROLE_SEARCH_FILTER, PRINCIPAL_LDAP_USERS_BY_ROLE_SEARCH_FILTER);
			PRINCIPAL_LDAP_ROLES_BY_USER_SEARCH_FILTER = config.getProperty(PROPERTY_PRINCIPAL_LDAP_ROLES_BY_USER_SEARCH_FILTER, PRINCIPAL_LDAP_ROLES_BY_USER_SEARCH_FILTER);
			values.put(PROPERTY_PRINCIPAL_LDAP_ROLES_BY_USER_SEARCH_FILTER, PRINCIPAL_LDAP_ROLES_BY_USER_SEARCH_FILTER);
			
			PRINCIPAL_LDAP_MAIL_SEARCH_BASE = config.getProperty(PROPERTY_PRINCIPAL_LDAP_MAIL_SEARCH_BASE, PRINCIPAL_LDAP_MAIL_SEARCH_BASE);
			values.put(PROPERTY_PRINCIPAL_LDAP_MAIL_SEARCH_BASE, PRINCIPAL_LDAP_MAIL_SEARCH_BASE);
			PRINCIPAL_LDAP_MAIL_SEARCH_FILTER = config.getProperty(PROPERTY_PRINCIPAL_LDAP_MAIL_SEARCH_FILTER, PRINCIPAL_LDAP_MAIL_SEARCH_FILTER);
			values.put(PROPERTY_PRINCIPAL_LDAP_MAIL_SEARCH_FILTER, PRINCIPAL_LDAP_MAIL_SEARCH_FILTER);
			PRINCIPAL_LDAP_MAIL_ATTRIBUTE= config.getProperty(PROPERTY_PRINCIPAL_LDAP_MAIL_ATTRIBUTE, PRINCIPAL_LDAP_MAIL_ATTRIBUTE);
			values.put(PROPERTY_PRINCIPAL_LDAP_MAIL_ATTRIBUTE, PRINCIPAL_LDAP_MAIL_ATTRIBUTE);
			
			MAX_FILE_SIZE_STR = config.getProperty(PROPERTY_MAX_FILE_SIZE, MAX_FILE_SIZE_STR);
			MAX_FILE_SIZE = Integer.parseInt(MAX_FILE_SIZE_STR) * 1024 * 1024;
			values.put(PROPERTY_MAX_FILE_SIZE, MAX_FILE_SIZE_STR+" ("+MAX_FILE_SIZE+")");
			MAX_SEARCH_RESULTS_STR = config.getProperty(PROPERTY_MAX_SEARCH_RESULTS, MAX_SEARCH_RESULTS_STR);
			MAX_SEARCH_RESULTS = Integer.parseInt(MAX_SEARCH_RESULTS_STR);
			values.put(PROPERTY_MAX_SEARCH_RESULTS, MAX_SEARCH_RESULTS_STR+" ("+MAX_SEARCH_RESULTS+")");

			RESTRICT_FILE_MIME_STR = config.getProperty(PROPERTY_RESTRICT_FILE_MIME, RESTRICT_FILE_MIME_STR);
			RESTRICT_FILE_MIME = "on".equalsIgnoreCase(RESTRICT_FILE_MIME_STR);
			values.put(PROPERTY_RESTRICT_FILE_MIME, RESTRICT_FILE_MIME_STR+" ("+RESTRICT_FILE_MIME+")");
			RESTRICT_FILE_EXTENSION = config.getProperty(PROPERTY_RESTRICT_FILE_EXTENSION, RESTRICT_FILE_EXTENSION);
			values.put(PROPERTY_RESTRICT_FILE_EXTENSION, RESTRICT_FILE_EXTENSION);
			
			NOTIFICATION_MESSAGE_SUBJECT = config.getProperty(PROPERTY_NOTIFICATION_MESSAGE_SUBJECT, NOTIFICATION_MESSAGE_SUBJECT);
			values.put(PROPERTY_NOTIFICATION_MESSAGE_SUBJECT, NOTIFICATION_MESSAGE_SUBJECT);
			NOTIFICATION_MESSAGE_BODY = config.getProperty(PROPERTY_NOTIFICATION_MESSAGE_BODY, NOTIFICATION_MESSAGE_BODY);
			values.put(PROPERTY_NOTIFICATION_MESSAGE_BODY, NOTIFICATION_MESSAGE_BODY);
			
			SUBSCRIPTION_MESSAGE_SUBJECT = config.getProperty(PROPERTY_SUBSCRIPTION_MESSAGE_SUBJECT, SUBSCRIPTION_MESSAGE_SUBJECT);
			values.put(PROPERTY_SUBSCRIPTION_MESSAGE_SUBJECT, SUBSCRIPTION_MESSAGE_SUBJECT);
			SUBSCRIPTION_MESSAGE_BODY = config.getProperty(PROPERTY_SUBSCRIPTION_MESSAGE_BODY, SUBSCRIPTION_MESSAGE_BODY);
			values.put(PROPERTY_SUBSCRIPTION_MESSAGE_BODY, SUBSCRIPTION_MESSAGE_BODY);
			
			SUBSCRIPTION_TWITTER_USER = config.getProperty(PROPERTY_SUBSCRIPTION_TWITTER_USER, SUBSCRIPTION_TWITTER_USER);
			values.put(PROPERTY_SUBSCRIPTION_TWITTER_USER, SUBSCRIPTION_TWITTER_USER);
			SUBSCRIPTION_TWITTER_PASSWORD = config.getProperty(PROPERTY_SUBSCRIPTION_TWITTER_PASSWORD, SUBSCRIPTION_TWITTER_PASSWORD);
			values.put(PROPERTY_SUBSCRIPTION_TWITTER_PASSWORD, SUBSCRIPTION_TWITTER_PASSWORD);
			SUBSCRIPTION_TWITTER_STATUS = config.getProperty(PROPERTY_SUBSCRIPTION_TWITTER_STATUS, SUBSCRIPTION_TWITTER_STATUS);
			values.put(PROPERTY_SUBSCRIPTION_TWITTER_STATUS, SUBSCRIPTION_TWITTER_STATUS);
			
			SYSTEM_DEMO_STR = config.getProperty(PROPERTY_SYSTEM_DEMO, SYSTEM_DEMO_STR);
			SYSTEM_DEMO = "on".equalsIgnoreCase(SYSTEM_DEMO_STR);
			values.put(PROPERTY_SYSTEM_DEMO, SYSTEM_DEMO_STR+" ("+SYSTEM_DEMO+")");
			SYSTEM_MAINTENANCE_STR = config.getProperty(PROPERTY_SYSTEM_MAINTENANCE, SYSTEM_MAINTENANCE_STR);
			SYSTEM_MAINTENANCE= "on".equalsIgnoreCase(SYSTEM_MAINTENANCE_STR);
			values.put(PROPERTY_SYSTEM_MAINTENANCE, SYSTEM_MAINTENANCE_STR+" ("+SYSTEM_MAINTENANCE+")");
			SYSTEM_READONLY_STR = config.getProperty(PROPERTY_SYSTEM_READONLY, SYSTEM_READONLY_STR);
			SYSTEM_READONLY = "on".equalsIgnoreCase(SYSTEM_READONLY_STR);
			values.put(PROPERTY_SYSTEM_READONLY, SYSTEM_READONLY_STR+" ("+SYSTEM_READONLY+")");
			SYSTEM_OCR = config.getProperty(PROPERTY_SYSTEM_OCR, SYSTEM_OCR);
			values.put(PROPERTY_SYSTEM_OCR, SYSTEM_OCR);
			SYSTEM_OPENOFFICE = config.getProperty(PROPERTY_SYSTEM_OPENOFFICE, SYSTEM_OPENOFFICE);
			values.put(PROPERTY_SYSTEM_OPENOFFICE, SYSTEM_OPENOFFICE);
			SYSTEM_IMG2PDF = config.getProperty(PROPERTY_SYSTEM_IMG2PDF, SYSTEM_IMG2PDF);
			values.put(PROPERTY_SYSTEM_IMG2PDF, SYSTEM_IMG2PDF);
			SYSTEM_PDF2SWF = config.getProperty(PROPERTY_SYSTEM_PDF2SWF, SYSTEM_PDF2SWF);
			values.put(PROPERTY_SYSTEM_PDF2SWF, SYSTEM_PDF2SWF);
			SYSTEM_ANTIVIR = config.getProperty(PROPERTY_SYSTEM_ANTIVIR, SYSTEM_ANTIVIR);
			values.put(PROPERTY_SYSTEM_ANTIVIR, SYSTEM_ANTIVIR);
			SYSTEM_LOGIN_LOWERCASE_STR = config.getProperty(PROPERTY_SYSTEM_LOGIN_LOWERCASE, SYSTEM_LOGIN_LOWERCASE_STR);
			SYSTEM_LOGIN_LOWERCASE = "on".equalsIgnoreCase(SYSTEM_LOGIN_LOWERCASE_STR);
			values.put(PROPERTY_SYSTEM_LOGIN_LOWERCASE, SYSTEM_LOGIN_LOWERCASE_STR+" ("+SYSTEM_LOGIN_LOWERCASE+")");
			
			UPDATE_INFO_STR = config.getProperty(PROPERTY_UPDATE_INFO, UPDATE_INFO_STR);
			UPDATE_INFO = "on".equalsIgnoreCase(UPDATE_INFO_STR);
			values.put(PROPERTY_UPDATE_INFO, UPDATE_INFO_STR+" ("+UPDATE_INFO+")");
			APPLICATION_URL = config.getProperty(PROPERTY_APPLICATION_URL, APPLICATION_URL);
			APPLICATION_BASE = getBase(APPLICATION_URL); 
			values.put(PROPERTY_APPLICATION_URL, APPLICATION_URL);
			USER_KEYWORDS_CACHE_STR = config.getProperty(PROPERTY_USER_KEYWORDS_CACHE, USER_KEYWORDS_CACHE_STR);
			USER_KEYWORDS_CACHE = "on".equalsIgnoreCase(USER_KEYWORDS_CACHE_STR);
			values.put(PROPERTY_USER_KEYWORDS_CACHE, USER_KEYWORDS_CACHE_STR+" ("+USER_KEYWORDS_CACHE+")");
			USER_SIZE_CACHE_STR = config.getProperty(PROPERTY_USER_SIZE_CACHE, USER_SIZE_CACHE_STR);
			USER_SIZE_CACHE = "on".equalsIgnoreCase(USER_SIZE_CACHE_STR);
			values.put(PROPERTY_USER_SIZE_CACHE, USER_SIZE_CACHE_STR+" ("+USER_SIZE_CACHE+")");
			
			// Schedule
			SCHEDULE_REPOSITORY_INFO_STR = config.getProperty(PROPERTY_SCHEDULE_REPOSITORY_INFO, SCHEDULE_REPOSITORY_INFO_STR);
			SCHEDULE_REPOSITORY_INFO = Long.parseLong(SCHEDULE_REPOSITORY_INFO_STR) * 60 * 1000;
			values.put(PROPERTY_SCHEDULE_REPOSITORY_INFO, SCHEDULE_REPOSITORY_INFO_STR+" ("+SCHEDULE_REPOSITORY_INFO+")");
			SCHEDULE_MAIL_IMPORTER_STR = config.getProperty(PROPERTY_SCHEDULE_MAIL_IMPORTER, SCHEDULE_MAIL_IMPORTER_STR);
			SCHEDULE_MAIL_IMPORTER = Long.parseLong(SCHEDULE_MAIL_IMPORTER_STR) * 60 * 1000;
			values.put(PROPERTY_SCHEDULE_MAIL_IMPORTER, SCHEDULE_MAIL_IMPORTER_STR+" ("+SCHEDULE_MAIL_IMPORTER+")");
			SCHEDULE_SESSION_KEEPALIVE_STR = config.getProperty(PROPERTY_SCHEDULE_SESSION_KEEPALIVE, SCHEDULE_SESSION_KEEPALIVE_STR);
			SCHEDULE_SESSION_KEEPALIVE = Long.parseLong(SCHEDULE_SESSION_KEEPALIVE_STR) * 60 * 1000;
			values.put(PROPERTY_SCHEDULE_SESSION_KEEPALIVE, SCHEDULE_SESSION_KEEPALIVE_STR+" ("+SCHEDULE_SESSION_KEEPALIVE+")");
			SCHEDULE_DASHBOARD_REFRESH_STR = config.getProperty(PROPERTY_SCHEDULE_DASHBOARD_REFRESH, SCHEDULE_DASHBOARD_REFRESH_STR);
			SCHEDULE_DASHBOARD_REFRESH = Long.parseLong(SCHEDULE_DASHBOARD_REFRESH_STR) * 60 * 1000;
			values.put(PROPERTY_SCHEDULE_DASHBOARD_REFRESH, SCHEDULE_DASHBOARD_REFRESH_STR+" ("+SCHEDULE_DASHBOARD_REFRESH+")");
			
			// KEA
			KEA_THESAURUS_SKOS_FILE = config.getProperty(PROPERTY_KEA_THESAURUS_SKOS_FILE, KEA_THESAURUS_SKOS_FILE);
			values.put(PROPERTY_KEA_THESAURUS_SKOS_FILE, KEA_THESAURUS_SKOS_FILE);
			KEA_THESAURUS_OWL_FILE = config.getProperty(PROPERTY_KEA_THESAURUS_OWL_FILE, KEA_THESAURUS_OWL_FILE);
			values.put(PROPERTY_KEA_THESAURUS_OWL_FILE, KEA_THESAURUS_OWL_FILE);
			KEA_THESAURUS_VOCABULARY_SERQL = config.getProperty(PROPERTY_KEA_THESAURUS_VOCABULARY_SERQL, KEA_THESAURUS_VOCABULARY_SERQL);
			values.put(PROPERTY_KEA_THESAURUS_VOCABULARY_SERQL, KEA_THESAURUS_VOCABULARY_SERQL);
			KEA_THESAURUS_BASE_URL = config.getProperty(PROPERTY_KEA_THESAURUS_BASE_URL, KEA_THESAURUS_BASE_URL);
			values.put(PROPERTY_KEA_THESAURUS_BASE_URL, KEA_THESAURUS_BASE_URL);
			KEA_THESAURUS_TREE_ROOT = config.getProperty(PROPERTY_KEA_THESAURUS_TREE_ROOT, KEA_THESAURUS_TREE_ROOT);
			values.put(PROPERTY_KEA_THESAURUS_TREE_ROOT, KEA_THESAURUS_TREE_ROOT);
			KEA_THESAURUS_TREE_CHILDS = config.getProperty(PROPERTY_KEA_THESAURUS_TREE_CHILDS, KEA_THESAURUS_TREE_CHILDS);
			values.put(PROPERTY_KEA_THESAURUS_TREE_CHILDS, KEA_THESAURUS_TREE_CHILDS);
			KEA_MODEL_FILE = config.getProperty(PROPERTY_KEA_MODEL_FILE, KEA_MODEL_FILE);
			values.put(PROPERTY_KEA_MODEL_FILE, KEA_MODEL_FILE);
			KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER_STR = config.getProperty(PROPERTY_KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER, KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER_STR);
			KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER = Integer.parseInt(KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER_STR);
			values.put(PROPERTY_KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER, KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER_STR+" ("+KEA_AUTOMATIC_KEYWORD_EXTRACTION_NUMBER+")");
			KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION_STR = config.getProperty(PROPERTY_KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION, KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION_STR);
			KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION = "on".equalsIgnoreCase(KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION_STR);
			values.put(PROPERTY_KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION, KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION_STR+" ("+KEA_AUTOMATIC_KEYWORD_EXTRACTION_RESTRICTION+")");
			KEA_STOPWORDS_FILE = config.getProperty(PROPERTY_KEA_STOPWORDS_FILE, KEA_STOPWORDS_FILE);
			values.put(PROPERTY_KEA_STOPWORDS_FILE, KEA_STOPWORDS_FILE);
			
			// Validator
			VALIDATOR_PASSWORD = config.getProperty(PROPERTY_VALIDATOR_PASSWORD, VALIDATOR_PASSWORD);
			values.put(PROPERTY_VALIDATOR_PASSWORD, VALIDATOR_PASSWORD);
					
			VALIDATOR_PASSWORD_MIN_LENGTH_STR = config.getProperty(PROPERTY_VALIDATOR_PASSWORD_MIN_LENGTH, VALIDATOR_PASSWORD_MIN_LENGTH_STR);			
			VALIDATOR_PASSWORD_MIN_LENGTH = Integer.parseInt(VALIDATOR_PASSWORD_MIN_LENGTH_STR);
			values.put(PROPERTY_VALIDATOR_PASSWORD_MIN_LENGTH, VALIDATOR_PASSWORD_MIN_LENGTH_STR+" ("+VALIDATOR_PASSWORD_MIN_LENGTH+")");
			VALIDATOR_PASSWORD_MAX_LENGTH_STR = config.getProperty(PROPERTY_VALIDATOR_PASSWORD_MAX_LENGTH, VALIDATOR_PASSWORD_MAX_LENGTH_STR);
			VALIDATOR_PASSWORD_MAX_LENGTH = Integer.parseInt(VALIDATOR_PASSWORD_MAX_LENGTH_STR);
			values.put(PROPERTY_VALIDATOR_PASSWORD_MAX_LENGTH, VALIDATOR_PASSWORD_MAX_LENGTH_STR+" ("+VALIDATOR_PASSWORD_MAX_LENGTH+")");
			VALIDATOR_PASSWORD_MIN_LOWERCASE_STR = config.getProperty(PROPERTY_VALIDATOR_PASSWORD_MIN_LOWERCASE, VALIDATOR_PASSWORD_MIN_LOWERCASE_STR);
			VALIDATOR_PASSWORD_MIN_LOWERCASE = Integer.parseInt(VALIDATOR_PASSWORD_MIN_LOWERCASE_STR);
			values.put(PROPERTY_VALIDATOR_PASSWORD_MIN_LOWERCASE, VALIDATOR_PASSWORD_MIN_LOWERCASE_STR+" ("+VALIDATOR_PASSWORD_MIN_LOWERCASE+")");
			VALIDATOR_PASSWORD_MIN_UPPERCASE_STR = config.getProperty(PROPERTY_VALIDATOR_PASSWORD_MIN_UPPERCASE, VALIDATOR_PASSWORD_MIN_UPPERCASE_STR);
			VALIDATOR_PASSWORD_MIN_UPPERCASE = Integer.parseInt(VALIDATOR_PASSWORD_MIN_UPPERCASE_STR);
			values.put(PROPERTY_VALIDATOR_PASSWORD_MIN_UPPERCASE, VALIDATOR_PASSWORD_MIN_UPPERCASE_STR+" ("+VALIDATOR_PASSWORD_MIN_UPPERCASE+")");
			VALIDATOR_PASSWORD_MIN_DIGITS_STR = config.getProperty(PROPERTY_VALIDATOR_PASSWORD_MIN_DIGITS, VALIDATOR_PASSWORD_MIN_DIGITS_STR);
			VALIDATOR_PASSWORD_MIN_DIGITS = Integer.parseInt(VALIDATOR_PASSWORD_MIN_DIGITS_STR);
			values.put(PROPERTY_VALIDATOR_PASSWORD_MIN_DIGITS, VALIDATOR_PASSWORD_MIN_DIGITS_STR+" ("+VALIDATOR_PASSWORD_MIN_DIGITS+")");
			VALIDATOR_PASSWORD_MIN_SPECIAL_STR = config.getProperty(PROPERTY_VALIDATOR_PASSWORD_MIN_SPECIAL, VALIDATOR_PASSWORD_MIN_SPECIAL_STR);
			VALIDATOR_PASSWORD_MIN_SPECIAL = Integer.parseInt(VALIDATOR_PASSWORD_MIN_SPECIAL_STR);
			values.put(PROPERTY_VALIDATOR_PASSWORD_MIN_SPECIAL, VALIDATOR_PASSWORD_MIN_SPECIAL_STR+" ("+VALIDATOR_PASSWORD_MIN_SPECIAL+")");
			
			VALIDATOR_PASSWORD_ERROR_MIN_LENGTH = config.getProperty(PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_LENGTH, VALIDATOR_PASSWORD_ERROR_MIN_LENGTH);
			values.put(PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_LENGTH, VALIDATOR_PASSWORD_ERROR_MIN_LENGTH);
			VALIDATOR_PASSWORD_ERROR_MAX_LENGTH = config.getProperty(PROPERTY_VALIDATOR_PASSWORD_ERROR_MAX_LENGTH, VALIDATOR_PASSWORD_ERROR_MAX_LENGTH);
			values.put(PROPERTY_VALIDATOR_PASSWORD_ERROR_MAX_LENGTH, VALIDATOR_PASSWORD_ERROR_MAX_LENGTH);
			VALIDATOR_PASSWORD_ERROR_MIN_LOWERCASE = config.getProperty(PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_LOWERCASE, VALIDATOR_PASSWORD_ERROR_MIN_LOWERCASE);
			values.put(PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_LOWERCASE, VALIDATOR_PASSWORD_ERROR_MIN_LOWERCASE);
			VALIDATOR_PASSWORD_ERROR_MIN_UPPERCASE = config.getProperty(PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_UPPERCASE, VALIDATOR_PASSWORD_ERROR_MIN_UPPERCASE);
			values.put(PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_UPPERCASE, VALIDATOR_PASSWORD_ERROR_MIN_UPPERCASE);
			VALIDATOR_PASSWORD_ERROR_MIN_DIGITS = config.getProperty(PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_DIGITS, VALIDATOR_PASSWORD_ERROR_MIN_DIGITS);
			values.put(PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_DIGITS, VALIDATOR_PASSWORD_ERROR_MIN_DIGITS);
			VALIDATOR_PASSWORD_ERROR_MIN_SPECIAL = config.getProperty(PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_SPECIAL, VALIDATOR_PASSWORD_ERROR_MIN_SPECIAL);
			values.put(PROPERTY_VALIDATOR_PASSWORD_ERROR_MIN_SPECIAL, VALIDATOR_PASSWORD_ERROR_MIN_SPECIAL);
			
			// Hibernate
			HIBERNATE_DIALECT = config.getProperty(PROPERTY_HIBERNATE_DIALECT, HIBERNATE_DIALECT);
			values.put(PROPERTY_HIBERNATE_DIALECT, HIBERNATE_DIALECT);
			HIBERNATE_DATASOURCE = config.getProperty(PROPERTY_HIBERNATE_DATASOURCE, HIBERNATE_DATASOURCE);
			values.put(PROPERTY_HIBERNATE_DATASOURCE, HIBERNATE_DATASOURCE);
			HIBERNATE_HBM2DDL = config.getProperty(PROPERTY_HIBERNATE_HBM2DDL, HIBERNATE_HBM2DDL);
			values.put(PROPERTY_HIBERNATE_HBM2DDL, HIBERNATE_HBM2DDL);
			
			fis.close();
		} catch (FileNotFoundException e) {
			log.warn("** No "+CONFIG_FILE+" file found, set default config **");
		} catch (IOException e) {
			log.warn("** IOError reading "+CONFIG_FILE+", set default config **");
		} finally {
			log.info("** Configuration **");
			for (Entry<String, String> entry : values.entrySet()) {
				log.info("{} => {}", entry.getKey(), entry.getValue());
			}
		}
	}
	
	/**
	 * load mime types
	 */
	public static void loadMimeTypes(ServletContext sc) {
		try {
			List<MimeType> mimeTypeList = MimeTypeDAO.findAll();
			Config.mimeTypes = new MimetypesFileTypeMap();
			
			if (mimeTypeList.isEmpty()) {
				resetMimeTypes(sc);
				mimeTypeList = MimeTypeDAO.findAll();
			}
			
			for (MimeType mt : mimeTypeList) {
				if (mt.isActive()) {
					String entry = mt.getName();
					for (String ext : mt.getExtensions()) {
						entry += " " + ext;
					}
					log.debug("loadMimeTypes => Add Entry: {}", entry);
					Config.mimeTypes.addMimeTypes(entry);
				}
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Load default mime type definitions
	 */
	public static void resetMimeTypes(ServletContext sc) {
		final String MIME_FILE = "/META-INF/mime.types";
		
		try {
			log.info("** Reading MIME file **");
			InputStream is = sc.getResourceAsStream(MIME_FILE);
			
			if (is != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(is));		
				String line;
			
				while ((line = br.readLine()) != null) {
					line = line.trim();
					
					if (!line.startsWith("#") && line.length() > 0) {
						String[] data = line.split("\\s");
						MimeType mt = new MimeType();
						mt.setName(data[0]);
						InputStream iis = sc.getResourceAsStream("/META-INF/mime/" + mt.getName() + ".gif");
						mt.setImageContent(IOUtils.toByteArray(iis));
						iis.close();
						mt.setImageMime("image/gif");
						mt.setActive(true);
						
						for (int i=1; i<data.length; i++) {
							mt.getExtensions().add(data[i]);
						}
						
						log.debug("*** Creating MIME type {} for {}", mt.getName(), mt.getExtensions());
						MimeTypeDAO.create(mt);
					}
				}
			
				br.close();
				is.close();
			} else {
				throw new FileNotFoundException(MIME_FILE);
			}
		} catch (FileNotFoundException e) {
			log.warn("** No MIME file found **");
			if (RESTRICT_FILE_MIME) {
				log.warn("** File upload disabled **");
			}
		} catch (IOException e) {
			log.warn("** IO Error reading MIME file **");
			if (RESTRICT_FILE_MIME) {
				log.warn("** File upload disabled **");
			}
		} catch (DatabaseException e) {
			log.warn(e.getMessage(), e);
			if (RESTRICT_FILE_MIME) {
				log.warn("** File upload disabled **");
			}
		}
	}
}
