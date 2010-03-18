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

package es.git.openkm.frontend.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTWorkspace;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMRepositoryService;
import es.git.openkm.frontend.client.service.OKMRepositoryServiceAsync;
import es.git.openkm.frontend.client.service.OKMWorkspaceService;
import es.git.openkm.frontend.client.service.OKMWorkspaceServiceAsync;
import es.git.openkm.frontend.client.widget.startup.StartUp;

/**
 * Workspace user properties
 * 
 * @author jllort
 *
 */
public class WorkspaceUserProperties {
	
	private final OKMRepositoryServiceAsync repositoryService = (OKMRepositoryServiceAsync) GWT.create(OKMRepositoryService.class);
	private final OKMWorkspaceServiceAsync workspaceService = (OKMWorkspaceServiceAsync) GWT.create(OKMWorkspaceService.class);
	
	private GWTWorkspace workspace;
	private String user = "";
	private String applicationURL = "";
	private String msg = "";
	
	/**
	 * Workspace user properties
	 */
	public WorkspaceUserProperties () {
	}
	
	/**
	 * First time inits workspace
	 */
	public void init() {
		getUserWorkspace();
	}
	
	/**
	 * Call back to get remote update message
	 */
	final AsyncCallback<String> callbackGetUpdateMessage = new AsyncCallback<String>() {
		public void onSuccess(String result) {
			msg = result;
			Main.get().mainPanel.bottomPanel.userInfo.setUpdateMessage(msg);
			Main.get().startUp.nextStatus(StartUp.STARTUP_KEEP_ALIVE);
		}

		public void onFailure(Throwable caught){
			Main.get().showError("RemoteUser", caught);
		}
	};
	
	/**
	 * Call back to get workspace user data
	 */
	final AsyncCallback<GWTWorkspace> callbackGetUserWorkspace = new AsyncCallback<GWTWorkspace>() {
		public void onSuccess(GWTWorkspace result) {
			workspace = result;
			user = result.getUser();
			applicationURL = result.getApplicationURL();
			Main.get().mainPanel.bottomPanel.userInfo.setUser(user, result.isAdmin());
			Main.get().mainPanel.topPanel.toolBar.setApplet(result.getToken(), "/okm:home/"+user+"/okm:personal");
			Main.get().aboutPopup.setAppVersion(result.getAppVersion());
			getUserDocumentsSize();
			
			// Show administration menu on admin user
			if (result.isAdmin()) {
				Main.get().mainPanel.topPanel.mainMenu.administration.setVisible(true);
				Main.get().mainPanel.topPanel.tabWorkspace.enableAdministration();
			}
			
			getUpdateMessage();
		}

		public void onFailure(Throwable caught){
			Main.get().showError("getUserWorkspace", caught);
		}
	};
	
	/**
	 * Gets the users documents size 
	 */
	final AsyncCallback<Double> callbackGetUserDocumentsSize = new AsyncCallback<Double>() {
		public void onSuccess(Double result) {
			Main.get().mainPanel.bottomPanel.userInfo.setUserRepositorySize(result.longValue());
		}

		public void onFailure(Throwable caught){
			Main.get().showError("getUserDocumentsSize", caught);
		}
	};
	
	/**
	 * Gets the remote user
	 */
	private void getUpdateMessage() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);	
		repositoryService.getUpdateMessage(callbackGetUpdateMessage);
	}
	
	/**
	 * Gets the workspace user data
	 */
	public void getUserWorkspace() {
		ServiceDefTarget endPoint = (ServiceDefTarget) workspaceService;
		endPoint.setServiceEntryPoint(Config.OKMWorkspaceService);	
		workspaceService.getUserWorkspace(callbackGetUserWorkspace);
	}
	
	/**
	 * Gets the user documents size
	 */
	public void getUserDocumentsSize() {
		ServiceDefTarget endPoint = (ServiceDefTarget) workspaceService;
		endPoint.setServiceEntryPoint(Config.OKMWorkspaceService);	
		workspaceService.getUserDocumentsSize(callbackGetUserDocumentsSize);
	}
	
	/**
	 * Gets the user
	 * 
	 * @return The user
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Gets the application URL
	 *
	 * @return
	 */
	public String getApplicationURL() {
		return applicationURL;
	}
	
	/**
	 * Gets the workspace data
	 * 
	 * @return The workspace data
	 */
	public GWTWorkspace getWorkspace() {
		return workspace;
	}
	
}
