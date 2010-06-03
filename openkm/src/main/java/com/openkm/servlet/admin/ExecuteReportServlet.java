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

package com.openkm.servlet.admin;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.JcrConstants;

import com.openkm.bean.report.admin.ReportLockedDocument;
import com.openkm.bean.report.admin.ReportSubscribedDocuments;
import com.openkm.bean.report.admin.ReportUser;
import com.openkm.core.DatabaseException;
import com.openkm.dao.AbstractDAO;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.WorkflowDAO;
import com.openkm.dao.bean.User;
import com.openkm.module.direct.DirectRepositoryModule;
import com.openkm.util.ReportUtil;
import com.openkm.util.WebUtil;

/**
 * Execute report servlet
 */
public class ExecuteReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(ExecuteReportServlet.class);
	
	// Report files
	public static final String REPORT_REGISTERED_USERS = "ReportRegisteredUsers.jrxml";
	public static final String REPORT_LOCKED_DOCUMENTS = "ReportLockedDocuments.jrxml";
	public static final String REPORT_SUBSCRIBED_DOCUMENTS = "ReportSubscribedDocuments.jrxml";
	public static final String REPORT_WORKFLOW_WORKLOAD = "ReportWorkflowWorkload.jrxml";
	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet >>>");
        Collection col = null;
        AbstractDAO dao = null;
        Connection con = null;
        
        // Setting default report type
        int type = ReportUtil.REPORT_PDF_OUTPUT;
        String mimeType = "application/pdf";
        String fileName = "";
        boolean downloading = true;
        
        // Getting params
        String reportType = WebUtil.getString(request, "type", "pdf");
        String jasperFile = WebUtil.getString(request, "jasperFile");
        
        // Setting report type
        if (reportType.equals("html")) {
        	type = ReportUtil.REPORT_HTML_OUTPUT;
        	downloading = false;
        } 

        try {
	        // Charging the collection
	        if (jasperFile.equals(REPORT_REGISTERED_USERS)) {
	        	col = reportUsers();
	        	fileName = REPORT_REGISTERED_USERS.substring(0, REPORT_REGISTERED_USERS.indexOf(".")) + "." + reportType;
	        } else if (jasperFile.equals(REPORT_LOCKED_DOCUMENTS)) {
	        	col = reportLockedDocuments();
	        	fileName = REPORT_LOCKED_DOCUMENTS.substring(0, REPORT_LOCKED_DOCUMENTS.indexOf(".")) + "." + reportType;
	        } else if (jasperFile.equals(REPORT_SUBSCRIBED_DOCUMENTS)) {
	        	col = reportSubscribedDocuments();
	        	fileName = REPORT_SUBSCRIBED_DOCUMENTS.substring(0, REPORT_SUBSCRIBED_DOCUMENTS.indexOf(".")) + "." + reportType;
	        } else if (jasperFile.equals(REPORT_WORKFLOW_WORKLOAD)) {
	        	fileName = REPORT_WORKFLOW_WORKLOAD.substring(0, REPORT_WORKFLOW_WORKLOAD.indexOf(".")) + "." + reportType;
	        	dao = WorkflowDAO.getInstance();
	        	con = dao.getConnection();
	        }
	        
	        if (downloading) {
	            // Only when downloading
		        // Setting headers 
		        String agent = request.getHeader("USER-AGENT");
		        
		        // Disable browser cache
		        response.setHeader("Expires", "Sat, 6 May 1971 12:00:00 GMT");
		        response.setHeader("Cache-Control", "max-age=0, must-revalidate");
		        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		        response.setHeader("Pragma", "no-cache");
		        
		        // Set MIME type
		        response.setContentType(mimeType);
		        
		        if (null != agent && -1 != agent.indexOf("MSIE")) {
					log.debug("Agent: Explorer");
					fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");
				} else if (null != agent && -1 != agent.indexOf("Mozilla"))	{
					log.debug("Agent: Mozilla");
					fileName = MimeUtility.encodeText(fileName, "UTF-8", "B");
				} else {
					log.debug("Agent: Unknown");
				}
		        
		        // Setting filename
		        response.setHeader("Content-disposition", "attachment; filename=\""+ fileName +"\"");
	        }
	        
	        Map<String, String> parameters = new HashMap<String, String>();
	        String host = com.openkm.core.Config.APPLICATION_URL;
	        parameters.put("host", host.substring(0, host.lastIndexOf("/")+1));
	        
	        if (con != null) {
	        	ReportUtil.generateReport(response.getOutputStream(), jasperFile, parameters, type, con);
	        } else {
	        	ReportUtil.generateReport(response.getOutputStream(), jasperFile, parameters, type, col);
	        }
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dao != null) {
				dao.closeConnection(con);
			}
		}
    }
	
	/**
	 * Gets all the users data
	 */
	private Collection<ReportUser> reportUsers() throws DatabaseException {
		log.debug("reportUsers()");
		List<ReportUser> al = new ArrayList<ReportUser>();
		
		for (Iterator<User> it = AuthDAO.findAllUsers(false).iterator(); it.hasNext(); ) {
			al.add(reportCopy(it.next()));
		}
		
		log.debug("reportUsers():"+al);
		return al;
	}
	
	/**
	 * Copy the Users data to GWTUser data.
	 */
	private static ReportUser reportCopy(User user) {
		ReportUser reportUser = new ReportUser();
		
		reportUser.setEmail(user.getEmail());
		reportUser.setId(user.getId());
		//reportUser.setRoles(user.getRoles());
		reportUser.setActive(user.isActive());

		return reportUser;
	}
	
	/**
	 * Gets all the locked documents data
	 * 
	 * @return Collection of locked documents data
	 */
	private Collection<ReportLockedDocument> reportLockedDocuments() {
		log.debug("reportLockedDocuments()");
		List<ReportLockedDocument> al = new ArrayList<ReportLockedDocument>();
		String statement = "/jcr:root/okm:root//element(*,okm:document)[@jcr:lockOwner]/@jcr:lockOwner";
		String type = "xpath";
		
		Session jcrSession = DirectRepositoryModule.getSystemSession();
		Workspace workspace = jcrSession.getWorkspace();
		QueryManager queryManager;
		
		try {
			queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, type);
			QueryResult result = query.execute();
			
			for (RowIterator it = result.getRows(); it.hasNext();) {
				ReportLockedDocument ld = new ReportLockedDocument();
				Row row = it.nextRow();
				
				Value v = row.getValue(JcrConstants.JCR_LOCKOWNER);
				ld.setOwner(v==null?"NULL":v.getString());
				v = row.getValue(JcrConstants.JCR_PATH);
				ld.setPath(v==null?"NULL":v.getString());
				
				al.add(ld);
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

		log.debug("reportLockedDocuments():"+al);
		return al;
	}
	
	/**
	 * Gets all the documents with subscriptors data
	 * 
	 * @return Collection of documents observed data
	 */
	private Collection<ReportSubscribedDocuments> reportSubscribedDocuments() {
		log.debug("reportSubscribedDocuments()");
		List<ReportSubscribedDocuments> al = new ArrayList<ReportSubscribedDocuments>();
		String statement = "/jcr:root/okm:root//element(*, mix:notification)/@okm:subscriptors";
		String type = "xpath";
		
		Session jcrSession = DirectRepositoryModule.getSystemSession();
		Workspace workspace = jcrSession.getWorkspace();
		QueryManager queryManager;
		
		try {
			queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, type);
			QueryResult result = query.execute();
			
			for (RowIterator it = result.getRows(); it.hasNext();) {
				ReportSubscribedDocuments dob = new ReportSubscribedDocuments();
				Row row = it.nextRow();
				
				Value v = row.getValue(JcrConstants.JCR_PATH);
				dob.setPath(v==null?"NULL":v.getString());
				
				String path = row.getValue(JcrConstants.JCR_PATH).getString();
				Node node = jcrSession.getRootNode().getNode(path.substring(1));
				Property prop = node.getProperty("okm:subscriptors");
				Value[] values = prop.getValues();
				StringBuilder sb = new StringBuilder();
				
				for (int i=0; i<values.length-1; i++) {
					sb.append(values[i].getString());
					sb.append(", ");
				}
				sb.append(values[values.length-1].getString());
				dob.setSubscriptors(sb.toString());
				
				al.add(dob);
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

		log.debug("reportSubscribedDocuments: "+al);
		return al;
	}
}
