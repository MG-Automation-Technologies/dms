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

package es.git.openkm.frontend.client.widget.dashboard;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTTaskInstance;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMWorkflowService;
import es.git.openkm.frontend.client.service.OKMWorkflowServiceAsync;

/**
 * WorkflowDashboard
 * 
 * @author jllort
 *
 */
public class WorkflowDashboard extends Composite {
	
	private final OKMWorkflowServiceAsync workflowService = (OKMWorkflowServiceAsync) GWT.create(OKMWorkflowService.class);
	
	private final int NUMBER_OF_COLUMNS = 2;
	
	private HorizontalPanel hPanel;
	private VerticalPanel vPanelLeft;
	private VerticalPanel vPanelRight;
	
	private WorkflowWidget pendingTasks;
	public WorkflowFormPanel workflowFormPanel;
	
	private boolean firstTime = true; 
	
	/**
	 * UserDashboard
	 */
	public WorkflowDashboard() {
		vPanelLeft = new VerticalPanel();
		vPanelRight = new VerticalPanel();
		hPanel = new HorizontalPanel();
		
		hPanel.add(vPanelLeft);
		hPanel.add(vPanelRight);
		
		pendingTasks = new WorkflowWidget("dashboard.workflow.pending.tasks", "img/icon/workflow.gif", true);
		workflowFormPanel = new WorkflowFormPanel();
		
		vPanelLeft.add(pendingTasks);
		vPanelRight.add(workflowFormPanel);
		
		hPanel.setHeight("100%");
		vPanelRight.setHeight("100%");
		
		initWidget(hPanel);
		
		// Getting user tasks
		findUserTaskInstances();
		
		firstTime=false; // Must not show status first time loading OpenKM ( on startup )
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		pendingTasks.langRefresh();
		workflowFormPanel.langRefresh();
	}
	
	/**
	 * setWidth
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		int columnWidth = width/NUMBER_OF_COLUMNS;
		
		// Trying to distribute widgets on columns with max size
		pendingTasks.setWidth(columnWidth);
		workflowFormPanel.setWidth(""+columnWidth);
		workflowFormPanel.setHeight("100%");
	}
	
	/**
	 * Get subscribed documents callback
	 */
	final AsyncCallback<List<GWTTaskInstance>> callbackFindUserTaskInstancess = new AsyncCallback<List<GWTTaskInstance>>() {
		public void onSuccess(List<GWTTaskInstance> result){
			pendingTasks.setTasks(result);
			Main.get().mainPanel.bottomPanel.userInfo.setNewsWorkflows(result.size());
			pendingTasks.unsetRefreshing();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("findUserTaskInstances", caught);
			pendingTasks.unsetRefreshing();
		}
	};
	
	public void findUserTaskInstances() {
		if (!firstTime) {
			pendingTasks.setRefreshing();
		}
		ServiceDefTarget endPoint = (ServiceDefTarget) workflowService;
		endPoint.setServiceEntryPoint(Config.OKMWorkflowService);		
		workflowService.findUserTaskInstances(callbackFindUserTaskInstancess);
	}
}