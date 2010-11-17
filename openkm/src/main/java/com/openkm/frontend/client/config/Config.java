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
	public static final String INSTALL = "/OpenKM";
	
	// Service entry point 
	public static final String OKMFolderService = INSTALL+"/frontend/Folder";
	public static final String OKMDocumentService = INSTALL+"/frontend/Document";
	public static final String OKMDownloadServlet = INSTALL+"/frontend/Download";
	public static final String OKMFileUploadService = INSTALL+"/frontend/FileUpload";
	public static final String OKMAuthService = INSTALL+"/frontend/Auth";
	public static final String OKMSearchService = INSTALL+"/frontend/Search";
	public static final String OKMPropertyGroupService = INSTALL+"/frontend/PropertyGroup";
	public static final String OKMNotifyService = INSTALL+"/frontend/Notify";
	public static final String OKMBookmarkService = INSTALL+"/frontend/Bookmark";
	public static final String OKMRepositoryService = INSTALL+"/frontend/Repository";
	public static final String OKMDashboardService = INSTALL+"/frontend/Dashboard";
	public static final String OKMWorkspaceService = INSTALL+"/frontend/Workspace";
	public static final String OKMWorkflowService = INSTALL+"/frontend/Workflow";
	public static final String OKMMailService = INSTALL+"/frontend/Mail";
	public static final String OKMGeneralService = INSTALL+"/frontend/General";
	public static final String OKMThesaurusService = INSTALL+"/frontend/Thesaurus";
	public static final String OKMPropertyService = INSTALL+"/frontend/Property";
	public static final String OKMChatService = INSTALL+"/frontend/Chat";
	public static final String OKMUserConfigService = INSTALL+"/frontend/UserConfig";
	public static final String OKMNoteService = INSTALL+"/frontend/Note";
	public static final String OKMLanguageService = INSTALL+"/frontend/Language";
	public static final String OKMProposeSubscriptionService = INSTALL+"/frontend/ProposedSubscription";
	public static final String OKMFeedService = INSTALL+"/feed/";
	public static final String OKMStaplingService = INSTALL+"/extension/Stapling";
	public static final String OKMStaplingDownloadService = INSTALL+"/extension/StaplingDownload";
}
