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

package es.git.openkm.backend.server;

import java.io.IOException;
import java.net.URLEncoder;
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

import es.git.openkm.backend.client.config.Config;
import es.git.openkm.bean.report.admin.ReportLockedDocument;
import es.git.openkm.bean.report.admin.ReportSubscribedDocuments;
import es.git.openkm.bean.report.admin.ReportUser;
import es.git.openkm.dao.AuthDAO;
import es.git.openkm.dao.bean.User;
import es.git.openkm.module.direct.DirectRepositoryModule;
import es.git.openkm.util.ReportUtil;
import es.git.openkm.util.WebUtil;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMReportServletAdmin"
 *                           display-name="Report service"
 *                           description="Report service"
 * @web.servlet-mapping      url-pattern="/OKMReportServletAdmin"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMReportServletAdmin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(OKMReportServletAdmin.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet >>>");
        Collection col = null;
        
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
	        if (jasperFile.equals(Config.REPORT_REGISTERED_USERS)) {
	        	col = reportUsers();
	        	fileName = Config.REPORT_REGISTERED_USERS.substring(0, Config.REPORT_REGISTERED_USERS.indexOf(".")) + "." + reportType;
	        	
	        } else if (jasperFile.equals(Config.REPORT_LOCKED_DOCUMENTS)) {
	        	col = reportLockedDocuments();
	        	fileName = Config.REPORT_LOCKED_DOCUMENTS.substring(0, Config.REPORT_LOCKED_DOCUMENTS.indexOf(".")) + "." + reportType;

	        } else if (jasperFile.equals(Config.REPORT_SUBSCRIBED_DOCUMENTS)) {
	        	col = reportSubscribedDocuments();
	        	fileName = Config.REPORT_SUBSCRIBED_DOCUMENTS.substring(0, Config.REPORT_SUBSCRIBED_DOCUMENTS.indexOf(".")) + "." + reportType;
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
	        String host = es.git.openkm.core.Config.APPLICATION_URL;
	        parameters.put("host", host.substring(0, host.lastIndexOf("/")+1));
			ReportUtil.generateReport(response.getOutputStream(), jasperFile, parameters, type, col);
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * Gets all the users data 
	 * 
	 * @return Collection of users data
	 * 
	 * @throws SQLException
	 */
	private Collection<ReportUser> reportUsers() throws SQLException {
		log.debug("reportUsers()");
		List<ReportUser> al = new ArrayList<ReportUser>();
		
		for (Iterator<User> it = AuthDAO.getInstance().findAllUsers(false).iterator(); it.hasNext(); ) {
			al.add(Util.reportCopy(it.next()));
		}
		
		log.debug("reportUsers():"+al);
		return al;
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
