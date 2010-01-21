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

package com.openkm.frontend.client.config;

/**
 * General configuration
 * 
 * @author jllort
 */
public class Config {
	public static String INSTALL = "";
	
	// Service entrypoint 
	public static final String OKMFolderService = "/OpenKM"+INSTALL+"/OKMFolderServlet";
	public static final String OKMDocumentService = "/OpenKM"+INSTALL+"/OKMDocumentServlet";
	public static final String OKMDownloadServlet = "/OpenKM"+INSTALL+"/OKMDownloadServlet";
	public static final String OKMFileUploadService = "/OpenKM"+INSTALL+"/OKMFileUploadServlet";
	public static final String OKMAuthService = "/OpenKM"+INSTALL+"/OKMAuthServlet";
	public static final String OKMSearchService = "/OpenKM"+INSTALL+"/OKMSearchServlet";
	public static final String OKMPropertyGroupService = "/OpenKM"+INSTALL+"/OKMPropertyGroupServlet";
	public static final String OKMNotifyService = "/OpenKM"+INSTALL+"/OKMNotifyServlet";
	public static final String OKMBookmarkService = "/OpenKM"+INSTALL+"/OKMBookmarkServlet";
	public static final String OKMRepositoryService = "/OpenKM"+INSTALL+"/OKMRepositoryServlet";
	public static final String OKMDashboardService = "/OpenKM"+INSTALL+"/OKMDashboardServlet";
	public static final String OKMWorkspaceService = "/OpenKM"+INSTALL+"/OKMWorkspaceServlet";
	public static final String OKMWorkflowService = "/OpenKM"+INSTALL+"/OKMWorkflowServlet";
	public static final String OKMMailService = "/OpenKM"+INSTALL+"/OKMMailServlet";
	public static final String OKMGeneralService = "/OpenKM"+INSTALL+"/OKMGeneralServlet";
}
