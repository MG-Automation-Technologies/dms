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

package es.git.openkm.backend.client.config;

/**
 * General configuration
 * 
 * @author jllort
 */
public class Config {
	public static String INSTALL = "";
	
	// Service entrypoint 
	public static final String OKMUserService = "/OpenKM"+INSTALL+"/OKMUserServletAdmin";
	public static final String OKMSearchService = "/OpenKM"+INSTALL+"/OKMSearchServletAdmin";
	public static final String OKMPropertyGroupService = "/OpenKM"+INSTALL+"/OKMPropertyGroupServletAdmin";
	public static final String OKMGeneralUtilsService = "/OpenKM"+INSTALL+"/OKMGeneralUtilsServletAdmin";
	public static final String OKMFolderService = "/OpenKM"+INSTALL+"/OKMFolderServletAdmin";
	public static final String OKMRepositoryService = "/OpenKM"+INSTALL+"/OKMRepositoryServletAdmin";
	public static final String OKMStatsService = "/OpenKM"+INSTALL+"/OKMStatsServletAdmin";
	public static final String OKMWorkflowUploadService = "/OpenKM"+INSTALL+"/OKMWorkflowUploadServletAdmin";
	public static final String OKMWorkflowServletService= "/OpenKM"+INSTALL+"/OKMWorkflowServletAdmin";
	public static final String OKMReportServletAdmin= "/OpenKM"+INSTALL+"/OKMReportServletAdmin";
	public static final String OKMConfig = "/OpenKM"+INSTALL+"/admin/config.jsp";
	public static final String OKMViewRepository = "/OpenKM"+INSTALL+"/admin/repository_view.jsp";
	
	// Report files
	public static final String REPORT_REGISTERED_USERS 		= "ReportRegisteredUsers.jrxml";
	public static final String REPORT_LOCKED_DOCUMENTS 		= "ReportLockedDocuments.jrxml";
	public static final String REPORT_SUBSCRIBED_DOCUMENTS 	= "ReportSubscribedDocuments.jrxml";
}
