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

import com.openkm.frontend.client.Main;

/**
 * General configuration
 * 
 * @author jllort
 */
public class Config {
	
	// Service entry point 
	public static String OKMFolderService = Main.CONTEXT+"/frontend/Folder";
	public static String OKMDocumentService = Main.CONTEXT+"/frontend/Document";
	public static String OKMDownloadServlet = Main.CONTEXT+"/frontend/Download";
	public static String OKMFileUploadService = Main.CONTEXT+"/frontend/FileUpload";
	public static String OKMAuthService = Main.CONTEXT+"/frontend/Auth";
	public static String OKMSearchService = Main.CONTEXT+"/frontend/Search";
	public static String OKMPropertyGroupService = Main.CONTEXT+"/frontend/PropertyGroup";
	public static String OKMNotifyService = Main.CONTEXT+"/frontend/Notify";
	public static String OKMBookmarkService = Main.CONTEXT+"/frontend/Bookmark";
	public static String OKMRepositoryService = Main.CONTEXT+"/frontend/Repository";
	public static String OKMDashboardService = Main.CONTEXT+"/frontend/Dashboard";
	public static String OKMWorkspaceService = Main.CONTEXT+"/frontend/Workspace";
	public static String OKMWorkflowService = Main.CONTEXT+"/frontend/Workflow";
	public static String OKMMailService = Main.CONTEXT+"/frontend/Mail";
	public static String OKMGeneralService = Main.CONTEXT+"/frontend/General";
	public static String OKMThesaurusService = Main.CONTEXT+"/frontend/Thesaurus";
	public static String OKMPropertyService = Main.CONTEXT+"/frontend/Property";
	public static String OKMChatService = Main.CONTEXT+"/frontend/Chat";
	public static String OKMUserConfigService = Main.CONTEXT+"/frontend/UserConfig";
	public static String OKMNoteService = Main.CONTEXT+"/frontend/Note";
	public static String OKMLanguageService = Main.CONTEXT+"/frontend/Language";
	public static String OKMProposeSubscriptionService = Main.CONTEXT+"/frontend/ProposedSubscription";
	public static String OKMFeedService = Main.CONTEXT+"/feed/";
	public static String OKMStaplingService = Main.CONTEXT+"/extension/Stapling";
	public static String OKMStaplingDownloadService = Main.CONTEXT+"/extension/StaplingDownload";
}
