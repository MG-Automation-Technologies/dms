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
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.jbpm.JbpmConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.cache.UserItemsManager;
import com.openkm.cache.UserKeywordsManager;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DataStoreGarbageCollector;
import com.openkm.core.RepositoryException;
import com.openkm.core.RepositoryInfo;
import com.openkm.core.SessionManager;
import com.openkm.core.UpdateInfo;
import com.openkm.core.UserMailImporter;
import com.openkm.core.Watchdog;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.WorkflowDAO;
import com.openkm.kea.RDFREpository;
import com.openkm.module.direct.DirectAuthModule;
import com.openkm.module.direct.DirectRepositoryModule;
import com.openkm.util.DocConverter;
import com.openkm.util.WarUtils;

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
	private UpdateInfo ui;
	private RepositoryInfo ri;
	private UserMailImporter umi;
	private DataStoreGarbageCollector dsgc;
	private boolean hasConfiguredDataStore;

    /* (non-Javadoc)
     * @see javax.servlet.GenericServlet#init()
     */
    public void init() throws ServletException {
        super.init();
        
        // Read config file
        Config.load();
        
        // Try a default OpenKM server location
        if (Config.APPLICATION_URL.equals("")) {
        	Config.APPLICATION_URL = "http://localhost:8080"+getServletContext().getContextPath()+"/com.openkm.frontend.Main/index.jsp";
        }
        
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
        	throw new ServletException(e.getMessage());
        }
        
        // Deserialize
        log.info("*** Cache deserialization ***");
        UserItemsManager.deserialize();
        UserKeywordsManager.deserialize();
        
        log.info("*** User database initialized ***");
        AuthDAO auth = AuthDAO.getInstance();
        auth.closeConnection(auth.getConnection());
        
        // Test for datastore
        SessionManager sm = SessionManager.getInstance();
		String sysToken = sm.getSystemToken();
		SessionImpl si = (SessionImpl)sm.get(sysToken);
        
		if (((RepositoryImpl)si.getRepository()).getDataStore() == null) {
        	hasConfiguredDataStore = false;
        } else {
        	hasConfiguredDataStore = true;
        }
        
        // Scheduler
        timer = new Timer();
        
        // Workflow
        log.info("*** Initializing workflow engine... ***");
        WorkflowDAO.getInstance().closeConnection(WorkflowDAO.getInstance().getConnection());
        JbpmConfiguration.getInstance().createJbpmContext().getGraphSession();
        JbpmConfiguration.getInstance().getJobExecutor().start();//startJobExecutor();
        
        log.info("*** Activating watchdog ***");
        wd = new Watchdog();
        timer.schedule(wd, 60*1000, 5*60*1000); // First in 1 min, next each 5 mins
        
        if (Config.UPDATE_INFO) {
        	 log.info("*** Activating update info ***");
        	 ui = new UpdateInfo();
        	 timer.schedule(ui, 1000, 24*60*60*1000); // First in 1 seg, next each 24 hours
        }
		
        log.info("*** Activating repository info ***");
        ri = new RepositoryInfo();
        timer.schedule(ri, 60*1000, Config.SCHEDULE_REPOSITORY_INFO); // First in 1 min, next each X minutes
        
        log.info("*** Activating user mail importer ***");
        umi = new UserMailImporter();
        timer.schedule(umi, 5*60*1000, Config.SCHEDULE_MAIL_IMPORTER); // First in 5 mins, next each X minutes
        
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
        
        log.info("*** Activating thesaurus repository ***");
        RDFREpository.getInstance();
        
        log.info("*** Start OpenOffice manager ***");
        DocConverter.getInstance().start();
    }

    /* (non-Javadoc)
     * @see javax.servlet.Servlet#destroy()
     */
    public void destroy() {
        super.destroy();

        if (log == null) log("*** Start OpenOffice manager ***");
        else log.info("*** Start OpenOffice manager ***");
        DocConverter.getInstance().stop();
        
        if (hasConfiguredDataStore) {
        	if (log == null) log("*** Shutting down datastore garbage collection... ***");
        	else log.info("*** Shutting down datastore garbage collection... ***");
        	dsgc.cancel();
        }
        
        if (log == null) log("*** Shutting down user mail importer ***");
        else log.info("*** Shutting down user mail importer ***");
        umi.cancel();
        
        if (log == null) log("*** Shutting down repository info... ***");
        else log.info("*** Shutting down repository info... ***");
        ri.cancel();
        
        if (log == null) log("*** Shutting down update info... ***");
        else log.info("*** Shutting down update info... ***");
        ui.cancel();
        
        if (log == null) log("*** Shutting down watchdog... ***");
        else log.info("*** Shutting down watchdog... ***");
        wd.cancel();
        
        timer.cancel();
        
        if (log == null) log("*** Shutting down workflow engine... ***");
        else log.info("*** Shutting down workflow engine... ***");
        JbpmConfiguration.getInstance().getJobExecutor().stop();
        
        if (log == null) log("*** Shutting down repository... ***");
        else log.info("*** Shutting down repository...");
        
        // Preserve user config
        SessionManager sessionManager = SessionManager.getInstance();
        for (Iterator<String> it = sessionManager.getTokens().iterator(); it.hasNext(); ) {
        	String token = it.next();
        	log.info("*** Logout: "+token+" ***");
        	
        	try {
        		new DirectAuthModule().logout(token);
			} catch (AccessDeniedException e) {
				e.printStackTrace();
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
        }
        
        // Serialize
        log.info("*** Cache serialization ***");
        UserItemsManager.serialize();
        UserKeywordsManager.serialize();
        
        // Preserve system user config
        DirectRepositoryModule.shutdown();
        
        if (log == null) log("*** Repository shutted down ***");
        else log.info("*** Repository shutted down ***");
    }
}
