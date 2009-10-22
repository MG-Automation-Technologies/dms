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

package es.git.openkm.servlet;

import java.io.File;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.SessionImpl;
import org.jbpm.JbpmConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.cache.UserItemsManager;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.Config;
import es.git.openkm.core.DataStoreGarbageCollector;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.RepositoryInfo;
import es.git.openkm.core.SessionManager;
import es.git.openkm.core.UpdateInfo;
import es.git.openkm.core.Watchdog;
import es.git.openkm.dao.AuthDAO;
import es.git.openkm.dao.WorkflowDAO;
import es.git.openkm.module.direct.DirectAuthModule;
import es.git.openkm.module.direct.DirectRepositoryModule;

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
        	Config.APPLICATION_URL = "http://localhost:8080"+getServletContext().getContextPath()+"/es.git.openkm.frontend.Main/index.jsp";
        }

        // Initialize folder pdf cache
        File pdfCacheFolder = new File(Config.PDF_CACHE);
        if (!pdfCacheFolder.exists()) pdfCacheFolder.mkdirs();

        // Initialize folder preview cache
        File previewCacheFolder = new File(Config.SWF_CACHE);
        if (!previewCacheFolder.exists()) previewCacheFolder.mkdirs();
        
        try {
        	log.info("*** Repository initializing... ***");
        	DirectRepositoryModule.initialize();
        	log.info("*** Repository initialized ***");
        } catch (Exception e) {
        	throw new ServletException(e.getMessage());
        }
        
        // Deserialize
        UserItemsManager.deserialize();
        
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
        
        if (Config.TRIAL || Config.UPDATE_INFO.equalsIgnoreCase("on")) {
        	 log.info("*** Activating update info ***");
        	 ui = new UpdateInfo();
        	 timer.schedule(ui, 1000, 24*60*60*1000); // First in 1 seg, next each 24 hours
        }
		
        log.info("*** Activating repository info ***");
        ri = new RepositoryInfo();
        timer.schedule(ri, 60*1000, 24*60*60*1000); // First in 1 min, next each 24 hours
        
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
    }

    /* (non-Javadoc)
     * @see javax.servlet.Servlet#destroy()
     */
    public void destroy() {
        super.destroy();
        
        if (hasConfiguredDataStore) {
        	if (log == null) log("*** Shutting down datastore garbage collection... ***");
        	else log.info("*** Shutting down datastore garbage collection... ***");
        	dsgc.cancel();
        }
        
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
        UserItemsManager.serialize();
        
        // Preserve system user config
        DirectRepositoryModule.shutdown();
        
        if (log == null) log("*** Repository shutted down ***");
        else log.info("*** Repository shutted down ***");
    }
}
