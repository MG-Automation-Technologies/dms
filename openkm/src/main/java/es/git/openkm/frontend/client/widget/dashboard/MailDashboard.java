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
import es.git.openkm.frontend.client.bean.GWTDashboardStatsDocumentResult;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMDashboardService;
import es.git.openkm.frontend.client.service.OKMDashboardServiceAsync;

/**
 * MailDashboard
 * 
 * @author jllort
 *
 */
public class MailDashboard extends Composite {
	private final OKMDashboardServiceAsync dashboardService = (OKMDashboardServiceAsync) GWT.create(OKMDashboardService.class);
	
	private final int NUMBER_OF_COLUMNS = 2;
	
	private HorizontalPanel hPanel;
	private VerticalPanel vPanelLeft;
	private VerticalPanel vPanelRight;
	
	private DashboardWidget lastWeekTopDownloadedDocuments;
	private DashboardWidget lastModifiedDocuments;
	
	private boolean firstTime = true;
	
	/**
	 * GeneralDashboard
	 */
	public MailDashboard() {
		vPanelLeft = new VerticalPanel();
		vPanelRight = new VerticalPanel();
		hPanel = new HorizontalPanel();
		
		lastWeekTopDownloadedDocuments = new DashboardWidget("LastWeekTopDownloadedDocuments","dashboard.general.last.week.top.downloaded.documents", "img/icon/actions/download.gif", true);
		lastModifiedDocuments = new DashboardWidget("LastModifiedDocuments", "dashboard.user.last.modified.documents", "img/icon/actions/checkin.gif", true);
		
		vPanelLeft.add(lastWeekTopDownloadedDocuments);
		vPanelRight.add(lastModifiedDocuments);
		
		hPanel.add(vPanelLeft);
		hPanel.add(vPanelRight);
		
		initWidget(hPanel);
		
		// Refreshing all panels
		refreshAll();
		
		firstTime = false;
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		lastWeekTopDownloadedDocuments.langRefresh();
		lastModifiedDocuments.langRefresh();
	}
	
	/**
	 * setWidth
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		int columnWidth = width/NUMBER_OF_COLUMNS;
		
		// Trying to distribute widgets on columns with max size
		lastWeekTopDownloadedDocuments.setWidth(columnWidth);
		lastModifiedDocuments.setWidth(columnWidth);
	}
	
	/**
	 * Get last week top downloaded documents callback
	 */
	final AsyncCallback<List<GWTDashboardStatsDocumentResult>> callbackGetLastWeekTopDownloadedDocuments = new AsyncCallback<List<GWTDashboardStatsDocumentResult>>() {
		public void onSuccess(List<GWTDashboardStatsDocumentResult> result){
			lastWeekTopDownloadedDocuments.setDocuments(result);
			lastWeekTopDownloadedDocuments.setHeaderResults(result.size());
			lastWeekTopDownloadedDocuments.unsetRefreshing();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getLastWeekTopDownloadedDocuments", caught);
			lastWeekTopDownloadedDocuments.unsetRefreshing();
		}
	};
	
	/**
	 * Gets last modified documents callback
	 */
	final AsyncCallback<List<GWTDashboardStatsDocumentResult>> callbackGetLastModifiedDocuments = new AsyncCallback<List<GWTDashboardStatsDocumentResult>>() {
		public void onSuccess(List<GWTDashboardStatsDocumentResult> result){
			lastModifiedDocuments.setDocuments(result);
			lastModifiedDocuments.setHeaderResults(result.size());
			lastModifiedDocuments.unsetRefreshing();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getLastModifiedDocuments", caught);
			lastModifiedDocuments.unsetRefreshing();
		}
	};

	/**
	 * getLastWeekTopDownloadedDocuments
	 */
	public void getLastWeekTopDownloadedDocuments() {
		if (!firstTime) {
			lastWeekTopDownloadedDocuments.setRefreshing();
		}
		ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
		endPoint.setServiceEntryPoint(Config.OKMDashboardService);		
		dashboardService.getLastWeekTopDownloadedDocuments(callbackGetLastWeekTopDownloadedDocuments);
	}
	
	/**
	 * getLastModifiedDocuments
	 */
	public void getLastModifiedDocuments() {
		if (!firstTime) {
			lastModifiedDocuments.setRefreshing();
		}
		ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
		endPoint.setServiceEntryPoint(Config.OKMDashboardService);		
		dashboardService.getLastModifiedDocuments(callbackGetLastModifiedDocuments);
	}
	
	/**
	 * Refresh all panels
	 */
	public void refreshAll() {
		getLastWeekTopDownloadedDocuments();
		getLastModifiedDocuments();
	}
}
