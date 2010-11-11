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
	public static final String OKMFolderService = "/OpenKM"+INSTALL+"/frontend/Folder";
	public static final String OKMDocumentService = "/OpenKM"+INSTALL+"/frontend/Document";
	public static final String OKMDownloadServlet = "/OpenKM"+INSTALL+"/frontend/Download";
	public static final String OKMFileUploadService = "/OpenKM"+INSTALL+"/frontend/FileUpload";
	public static final String OKMAuthService = "/OpenKM"+INSTALL+"/frontend/Auth";
	public static final String OKMSearchService = "/OpenKM"+INSTALL+"/frontend/Search";
	public static final String OKMPropertyGroupService = "/OpenKM"+INSTALL+"/frontend/PropertyGroup";
	public static final String OKMNotifyService = "/OpenKM"+INSTALL+"/frontend/Notify";
	public static final String OKMBookmarkService = "/OpenKM"+INSTALL+"/frontend/Bookmark";
	public static final String OKMRepositoryService = "/OpenKM"+INSTALL+"/frontend/Repository";
	public static final String OKMDashboardService = "/OpenKM"+INSTALL+"/frontend/Dashboard";
	public static final String OKMWorkspaceService = "/OpenKM"+INSTALL+"/frontend/Workspace";
	public static final String OKMWorkflowService = "/OpenKM"+INSTALL+"/frontend/Workflow";
	public static final String OKMMailService = "/OpenKM"+INSTALL+"/frontend/Mail";
	public static final String OKMGeneralService = "/OpenKM"+INSTALL+"/frontend/General";
	public static final String OKMThesaurusService = "/OpenKM"+INSTALL+"/frontend/Thesaurus";
	public static final String OKMPropertyService = "/OpenKM"+INSTALL+"/frontend/Property";
	public static final String OKMChatService = "/OpenKM"+INSTALL+"/frontend/Chat";
	public static final String OKMUserConfigService = "/OpenKM"+INSTALL+"/frontend/UserConfig";
	public static final String OKMNoteService = "/OpenKM"+INSTALL+"/frontend/Note";
	public static final String OKMLanguageService = "/OpenKM"+INSTALL+"/frontend/Language";
	public static final String OKMProposeSubscriptionService = "/OpenKM"+INSTALL+"/frontend/ProposedSubscription";
	public static final String OKMFeedService = "/OpenKM"+INSTALL+"/feed/";
	public static final String OKMStaplingService = "/OpenKM"+INSTALL+"/extension/Stapling";
	public static final String OKMStaplingDownloadService = "/OpenKM"+INSTALL+"/extension/StaplingDownload";
}
