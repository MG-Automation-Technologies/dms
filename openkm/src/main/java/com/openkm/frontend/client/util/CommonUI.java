/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

package com.openkm.frontend.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTTaskInstance;
import com.openkm.frontend.client.contants.service.ErrorCode;
import com.openkm.frontend.client.contants.ui.UIDesktopConstants;
import com.openkm.frontend.client.contants.ui.UIDockPanelConstants;
import com.openkm.frontend.client.service.OKMWorkflowService;
import com.openkm.frontend.client.service.OKMWorkflowServiceAsync;

/**
 * @author jllort
 *
 */
public class CommonUI {
	private static final OKMWorkflowServiceAsync workflowService = (OKMWorkflowServiceAsync) GWT.create(OKMWorkflowService.class);
	
	/**
	 * Opens all folder path
	 * 
	 * @param path The parent path
	 * @param docPath The document full path
	 */
	public static void openAllFolderPath(String path, String docPath) {
		boolean found = false;
		
		// Open folder path is only possible if desktop is visible
		if (Main.get().mainPanel.topPanel.tabWorkspace.isDesktopVisible()) {
			if (path.startsWith(Main.get().mainPanel.desktop.navigator.taxonomyTree.folderRoot.getPath()) ||
				path.startsWith(Main.get().mainPanel.desktop.navigator.categoriesTree.folderRoot.getPath()) ||
				path.startsWith(Main.get().mainPanel.desktop.navigator.thesaurusTree.folderRoot.getPath()) ||
				path.startsWith(Main.get().mainPanel.desktop.navigator.personalTree.folderRoot.getPath()) ||
				path.startsWith(Main.get().mainPanel.desktop.navigator.templateTree.folderRoot.getPath()) || 
				path.startsWith(Main.get().mainPanel.desktop.navigator.trashTree.folderRoot.getPath()) || 
				path.startsWith(Main.get().mainPanel.desktop.navigator.mailTree.folderRoot.getPath())) {
				found = true;
			}
			
			if (found) {
				Main.get().mainPanel.topPanel.tabWorkspace.changeSelectedTab(UIDockPanelConstants.DESKTOP);
				
				if (path.startsWith(Main.get().mainPanel.desktop.navigator.taxonomyTree.folderRoot.getPath())) {
					Main.get().mainPanel.desktop.navigator.stackPanel.showStack(UIDesktopConstants.NAVIGATOR_TAXONOMY, false);
				} else if (path.startsWith(Main.get().mainPanel.desktop.navigator.categoriesTree.folderRoot.getPath())) {
					Main.get().mainPanel.desktop.navigator.stackPanel.showStack(UIDesktopConstants.NAVIGATOR_CATEGORIES, false);
				} else if (path.startsWith(Main.get().mainPanel.desktop.navigator.thesaurusTree.folderRoot.getPath())) {
					Main.get().mainPanel.desktop.navigator.stackPanel.showStack(UIDesktopConstants.NAVIGATOR_THESAURUS, false);
				} else if (path.startsWith(Main.get().mainPanel.desktop.navigator.personalTree.folderRoot.getPath())) {
					Main.get().mainPanel.desktop.navigator.stackPanel.showStack(UIDesktopConstants.NAVIGATOR_PERSONAL, false);
				} else if (path.startsWith(Main.get().mainPanel.desktop.navigator.templateTree.folderRoot.getPath())) {
					Main.get().mainPanel.desktop.navigator.stackPanel.showStack(UIDesktopConstants.NAVIGATOR_TEMPLATES, false);
				} else if (path.startsWith(Main.get().mainPanel.desktop.navigator.trashTree.folderRoot.getPath())) {
					Main.get().mainPanel.desktop.navigator.stackPanel.showStack(UIDesktopConstants.NAVIGATOR_TRASH, false);
				} else if (path.startsWith(Main.get().mainPanel.desktop.navigator.mailTree.folderRoot.getPath())) {
					Main.get().mainPanel.desktop.navigator.stackPanel.showStack(UIDesktopConstants.NAVIGATOR_MAIL, false);
				}
				
				Main.get().activeFolderTree.openAllPathFolder(path, docPath);
			}
		}
	}
	
	/**
	 * Opens workflow dashboard workspace with required pending user task instance.
	 * 
	 * @param taskInstanceId ID of required task instance
	 */
	public static void openTaskInstance(String taskInstanceId) {
		if (Main.get().workspaceUserProperties.getWorkspace().isTabDashboardVisible() && 
			Main.get().workspaceUserProperties.getWorkspace().isDashboardWorkflowVisible() && 
			taskInstanceId != null && !taskInstanceId.equals("")) {
			Main.get().mainPanel.topPanel.tabWorkspace.changeSelectedTab(UIDockPanelConstants.DASHBOARD);
			Main.get().mainPanel.dashboard.horizontalToolBar.showWorkflowView();
			workflowService.getTaskInstance(new Long(taskInstanceId).longValue(), new AsyncCallback<GWTTaskInstance>() {
				@Override
				public void onSuccess(GWTTaskInstance taskInstance) {
					// Taskintance = null indicates is not valid user task instance
					if (taskInstance != null) {
						// Opens pending user task
						Main.get().mainPanel.dashboard.workflowDashboard.workflowFormPanel.setTaskInstance(taskInstance);
					} else {
						Main.get().showError("getTaskInstance (taskInstance==null)", new Throwable(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkflowService, ErrorCode.CAUSE_General)));
					}
				}
				@Override
				public void onFailure(Throwable caught) {
					Main.get().showError("isValidUserPendingTask", caught);
				}
			});
		} 
	}
}