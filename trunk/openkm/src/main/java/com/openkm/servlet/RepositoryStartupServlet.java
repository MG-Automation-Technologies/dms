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

package com.openkm.servlet;

import java.io.File;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.EvalError;
import bsh.Interpreter;

import com.openkm.cache.UserDocumentKeywordsManager;
import com.openkm.cache.UserItemsManager;
import com.openkm.core.Config;
import com.openkm.core.Cron;
import com.openkm.core.DataStoreGarbageCollector;
import com.openkm.core.DatabaseException;
import com.openkm.core.RepositoryInfo;
import com.openkm.core.UpdateInfo;
import com.openkm.core.UserMailImporter;
import com.openkm.core.Watchdog;
import com.openkm.dao.HibernateUtil;
import com.openkm.kea.RDFREpository;
import com.openkm.module.direct.DirectRepositoryModule;
import com.openkm.util.DocConverter;
import com.openkm.util.UserActivity;
import com.openkm.util.WarUtils;
import com.openkm.util.cl.ClassLoaderUtils;
import com.openkm.util.cl.JarClassLoader;

/**
 * Servlet Class
 * 
 * @web.servlet name="RepositoryStartup" display-name="Name for RepositoryStartup"
 *              description="Description for RepositoryStartup" load-on-startup = "1"
 * @web.servlet-mapping url-pattern="/RepositoryStartupServlet"
 * @web.servlet-init-param name="repository-config" value="A value"
 */
public class RepositoryStartupServlet extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(RepositoryStartupServlet.class);
	private static final long serialVersionUID = 207151527252937549L;
	private Timer timer;
	private Watchdog wd;
	private Cron cron;
	private UpdateInfo ui;
	private RepositoryInfo ri;
	private UserMailImporter umi;
	private DataStoreGarbageCollector dsgc;
	private boolean hasConfiguredDataStore;

    @Override
    public void init() throws ServletException {
        super.init();
        
        // Read config file
        Config.load();
        
        // Get OpenKM version
        WarUtils.readAppVersion(getServletContext());
        log.info("*** Application version: "+WarUtils.getAppVersion()+" ***");

        // Initialize folder pdf cache
        File pdfCacheFolder = new File(Config.PDF_CACHE);
        if (!pdfCacheFolder.exists()) pdfCacheFolder.mkdirs();

        // Initialize folder preview cache
        File previewCacheFolder = new File(Config.SWF_CACHE);
        if (!previewCacheFolder.exists()) previewCacheFolder.mkdirs();
        
        // Initialize Velocity engine
	    try {
	        Properties p = new Properties();
		    p.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, Config.HOME_DIR);
			Velocity.init(p);
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
        
        try {
        	log.info("*** Repository initializing... ***");
        	DirectRepositoryModule.initialize();
        	log.info("*** Repository initialized ***");
        } catch (Exception e) {
        	throw new ServletException(e.getMessage(), e);
        }
        
        // Deserialize
        try {
        	log.info("*** Cache deserialization ***");
        	UserItemsManager.deserialize();
        	UserDocumentKeywordsManager.deserialize();
        } catch (DatabaseException e) {
        	log.warn(e.getMessage(), e);
        }
        
        log.info("*** User database initialized ***");
        
        // Test for datastore
		SessionImpl si = (SessionImpl) DirectRepositoryModule.getSystemSession();
        
		if (((RepositoryImpl)si.getRepository()).getDataStore() == null) {
        	hasConfiguredDataStore = false;
        } else {
        	hasConfiguredDataStore = true;
        }
        
        // Scheduler
        timer = new Timer();
        
        // Workflow
        log.info("*** Initializing workflow engine... ***");
        JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
        jbpmContext.setSessionFactory(HibernateUtil.getSessionFactory());
        jbpmContext.getGraphSession();
        jbpmContext.getJbpmConfiguration().getJobExecutor().start();//startJobExecutor();
        jbpmContext.close();
        
        // Mime types
        log.info("*** Initializing MIME types... ***");
        Config.loadMimeTypes();
                
        if (Config.UPDATE_INFO) {
        	 log.info("*** Activating update info ***");
        	 ui = new UpdateInfo();
        	 timer.schedule(ui, 1000, 24*60*60*1000); // First in 1 seg, next each 24 hours
        }
		
        log.info("*** Activating watchdog ***");
        wd = new Watchdog();
        timer.schedule(wd, 60*1000, 5*60*1000); // First in 1 min, next each 5 mins
        
        log.info("*** Activating cron ***");
        cron = new Cron();
        timer.schedule(cron, 60*1000, 60*1000); // First in 1 min, next each 1 min
        
        log.info("*** Activating repository info ***");
        ri = new RepositoryInfo();
        timer.schedule(ri, 60*1000, Config.SCHEDULE_REPOSITORY_INFO); // First in 1 min, next each X minutes
        
        if (Config.SCHEDULE_MAIL_IMPORTER > 0) {
        	log.info("*** Activating user mail importer ***");
        	umi = new UserMailImporter();
        	timer.schedule(umi, 5*60*1000, Config.SCHEDULE_MAIL_IMPORTER); // First in 5 mins, next each X minutes
        } else {
        	log.info("*** User mail importer disabled ***");
        }
        
        if (hasConfiguredDataStore) {
        	log.info("*** Activating datastore garbage collection ***");
        	dsgc = new DataStoreGarbageCollector();
        	Calendar now = Calendar.getInstance();
        	now.add(Calendar.DAY_OF_YEAR, 1);
        	now.set(Calendar.HOUR_OF_DAY, 0);
        	now.set(Calendar.MINUTE, 0);
        	now.set(Calendar.SECOND, 0);
        	now.set(Calendar.MILLISECOND, 0);
        	timer.scheduleAtFixedRate(dsgc, now.getTime(), 24*60*60*1000); // First tomorrow at 00:00, next each 24 hours
        }
        
        try {
        	log.info("*** Activating thesaurus repository ***");
        	RDFREpository.getInstance();
        } catch (Exception e) {
        	log.warn(e.getMessage(), e);
        }
        
        try {
        	log.info("*** Start OpenOffice manager ***");
        	DocConverter.getInstance().start();
        } catch (Throwable e) {
        	log.warn(e.getMessage(), e);
        }
        
        try {
        	log.info("*** Ejecute start script ***");
        	runScript(Config.START_SCRIPT);
        	runJar(Config.START_JAR);
        } catch (Throwable e) {
        	log.warn(e.getMessage(), e);
        }
        
        // Activity log
		UserActivity.log(Config.SYSTEM_USER, "OPENKM_START", null, null);
    }

	@Override
    public void destroy() {
        super.destroy();

        try {
        	if (log == null) log("*** Shutting down OpenOffice manager ***");
        	else log.info("*** Shutting down OpenOffice manager ***");
        	DocConverter.getInstance().stop();
        } catch (Throwable e) {
        	log.warn(e.getMessage(), e);
        }
        
        if (hasConfiguredDataStore) {
        	if (log == null) log("*** Shutting down datastore garbage collection... ***");
        	else log.info("*** Shutting down datastore garbage collection... ***");
        	dsgc.cancel();
        }
        
        if (Config.SCHEDULE_MAIL_IMPORTER > 0) {
        	if (log == null) log("*** Shutting down user mail importer ***");
        	else log.info("*** Shutting down user mail importer ***");
        	umi.cancel();
        }
        
        if (log == null) log("*** Shutting down repository info... ***");
        else log.info("*** Shutting down repository info... ***");
        ri.cancel();
        
        if (log == null) log("*** Shutting down cron... ***");
        else log.info("*** Shutting down cron... ***");
        cron.cancel();
        
        if (log == null) log("*** Shutting down watchdog... ***");
        else log.info("*** Shutting down watchdog... ***");
        wd.cancel();
        
        if (log == null) log("*** Shutting down update info... ***");
        else log.info("*** Shutting down update info... ***");
        ui.cancel();
        
        timer.cancel();
        
        if (log == null) log("*** Shutting down workflow engine... ***");
        else log.info("*** Shutting down workflow engine... ***");
        JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
        jbpmContext.getJbpmConfiguration().getJobExecutor().stop();
        jbpmContext.close();
        
        if (log == null) log("*** Shutting down repository... ***");
        else log.info("*** Shutting down repository...");
        
        // Serialize
        try {
        	log.info("*** Cache serialization ***");
        	UserItemsManager.serialize();
        	UserDocumentKeywordsManager.serialize();
        } catch (DatabaseException e) {
        	log.warn(e.getMessage(), e);
        }
        
        try {
        	// Preserve system user config
        	DirectRepositoryModule.shutdown();
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
        
        if (log == null) log("*** Repository shutted down ***");
        else log.info("*** Repository shutted down ***");
        
        try {
        	if (log == null) log("*** Ejecute stop script ***");
        	else log.info("*** Ejecute stop script ***");
        	runScript(Config.STOP_SCRIPT);
        	runJar(Config.STOP_JAR);
        } catch (Throwable e) {
        	log.warn(e.getMessage(), e);
        }
        
        // Activity log
		UserActivity.log(Config.SYSTEM_USER, "OPENKM_STOP", null, null);
    }
    
    /**
     * Ejecute script
     */
    private void runScript(String name) {
    	try {
    		File script = new File(Config.HOME_DIR + File.separatorChar + name); 
        	FileReader fr = null;
        	
        	if (script.exists() && script.canRead()) {
        		Interpreter i = new Interpreter();
        		fr = new FileReader(script);
				
				try {
					Object result = i.eval(fr);
					log.info("Script output: "+(result!=null?result.toString():"null"));
				} catch (EvalError e) {
					log.warn(e.getMessage(), e);
				} finally {
					IOUtils.closeQuietly(fr);
				}
        	} else {
        		log.warn("Unable to read script: {}", script.getPath());
        	}
        } catch (Exception e) {
        	log.warn(e.getMessage(), e);
        }
    }
    
    /**
     * Ejecute jar
     */
    private void runJar(String name) {
    	try {
    		File jar = new File(Config.HOME_DIR + File.separatorChar + name); 
    		
    		if (jar.exists() && jar.canRead()) {
    			JarClassLoader jcl = new JarClassLoader(jar.toURI().toURL(), getClass().getClassLoader());
    			String mainClass = jcl.getMainClassName();
    			
    			if (mainClass != null) {
    				Class<?> c = jcl.loadClass(jcl.getMainClassName());
    				ClassLoaderUtils.invokeClass(c, new String[] {});
    			} else {
    				log.error("Main class not defined at: {}", jar.getPath());
    			}
    		} else {
    			log.warn("Unable to read jar: {}", jar.getPath());
    		}
    	} catch (Exception e) {
    		log.warn(e.getMessage(), e);
    	}
	}
}
