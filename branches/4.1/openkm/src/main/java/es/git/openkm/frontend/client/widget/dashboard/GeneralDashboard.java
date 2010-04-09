/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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
 * GeneralDashboard
 * 
 * @author jllort
 *
 */
public class GeneralDashboard extends Composite {
	private final OKMDashboardServiceAsync dashboardService = (OKMDashboardServiceAsync) GWT.create(OKMDashboardService.class);
	
	private final int NUMBER_OF_COLUMNS = 2;
	
	private HorizontalPanel hPanel;
	private VerticalPanel vPanelLeft;
	private VerticalPanel vPanelRight;
	
	private DashboardWidget lastWeekTopDownloadedDocuments;
	private DashboardWidget lastMonthTopDownloadedDocuments;
	private DashboardWidget lastMonthTopModifiedDocuments;
	private DashboardWidget lastWeekTopModifiedDocuments;
	private DashboardWidget lastModifiedDocuments;
	private DashboardWidget lastUploadedDocuments;
	
	private boolean firstTime = true;
	
	/**
	 * GeneralDashboard
	 */
	public GeneralDashboard() {
		vPanelLeft = new VerticalPanel();
		vPanelRight = new VerticalPanel();
		hPanel = new HorizontalPanel();
		
		lastWeekTopDownloadedDocuments = new DashboardWidget("LastWeekTopDownloadedDocuments","dashboard.general.last.week.top.downloaded.documents", "img/icon/actions/download.gif", true);
		lastMonthTopDownloadedDocuments = new DashboardWidget("LastMonthTopDownloadedDocuments", "dashboard.general.last.month.top.downloaded.documents", "img/icon/actions/download.gif", false);
		lastWeekTopModifiedDocuments = new DashboardWidget("LastWeekTopModifiedDocuments", "dashboard.general.last.week.top.modified.documents", "img/icon/actions/checkin.gif", false);
		lastMonthTopModifiedDocuments = new DashboardWidget("LastMonthTopModifiedDocuments", "dashboard.general.last.month.top.modified.documents", "img/icon/actions/checkin.gif", false);
		lastModifiedDocuments = new DashboardWidget("LastModifiedDocuments", "dashboard.user.last.modified.documents", "img/icon/actions/checkin.gif", true);
		lastUploadedDocuments = new DashboardWidget("LastUploadedDocuments", "dashboard.general.last.uploaded.documents", "img/icon/actions/add_document.gif", false);
		
		vPanelLeft.add(lastWeekTopDownloadedDocuments);
		vPanelLeft.add(lastMonthTopDownloadedDocuments);
		vPanelLeft.add(lastWeekTopModifiedDocuments);
		vPanelLeft.add(lastMonthTopModifiedDocuments);
		vPanelLeft.add(lastUploadedDocuments);
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
		lastMonthTopDownloadedDocuments.langRefresh();
		lastWeekTopModifiedDocuments.langRefresh();
		lastMonthTopModifiedDocuments.langRefresh();
		lastModifiedDocuments.langRefresh();
		lastUploadedDocuments.langRefresh();
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
		lastMonthTopDownloadedDocuments.setWidth(columnWidth);
		lastWeekTopModifiedDocuments.setWidth(columnWidth);
		lastMonthTopModifiedDocuments.setWidth(columnWidth);
		lastModifiedDocuments.setWidth(columnWidth);
		lastUploadedDocuments.setWidth(columnWidth);
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
	 * Get last month top downloaded documents callback
	 */
	final AsyncCallback<List<GWTDashboardStatsDocumentResult>> callbackGetLastMonthTopDownloadedDocuments = new AsyncCallback<List<GWTDashboardStatsDocumentResult>>() {
		public void onSuccess(List<GWTDashboardStatsDocumentResult> result){
			lastMonthTopDownloadedDocuments.setDocuments(result);
			lastMonthTopDownloadedDocuments.setHeaderResults(result.size());
			lastMonthTopDownloadedDocuments.unsetRefreshing();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getLastMonthTopDownloadedDocuments", caught);
			lastMonthTopDownloadedDocuments.unsetRefreshing();
		}
	};
	
	/**
	 * Gets last months top modified documents callback
	 */
	final AsyncCallback<List<GWTDashboardStatsDocumentResult>> callbackGetLastMonthTopModifiedDocuments = new AsyncCallback<List<GWTDashboardStatsDocumentResult>>() {
		public void onSuccess(List<GWTDashboardStatsDocumentResult> result){
			lastMonthTopModifiedDocuments.setDocuments(result);
			lastMonthTopModifiedDocuments.setHeaderResults(result.size());
			lastMonthTopModifiedDocuments.unsetRefreshing();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getLastMonthTopModifiedDocuments", caught);
			lastMonthTopModifiedDocuments.unsetRefreshing();
		}
	};
	
	/**
	 * Gets last week top modified documents callback
	 */
	final AsyncCallback<List<GWTDashboardStatsDocumentResult>> callbackGetLastWeekTopModifiedDocuments = new AsyncCallback<List<GWTDashboardStatsDocumentResult>>() {
		public void onSuccess(List<GWTDashboardStatsDocumentResult> result){
			lastWeekTopModifiedDocuments.setDocuments(result);
			lastWeekTopModifiedDocuments.setHeaderResults(result.size());
			lastWeekTopModifiedDocuments.unsetRefreshing();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getLastWeekTopModifiedDocuments", caught);
			lastWeekTopModifiedDocuments.unsetRefreshing();
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
	 * get last week top uploaded documents
	 */
	final AsyncCallback<List<GWTDashboardStatsDocumentResult>> callbackGetLastUploadedDocuments = new AsyncCallback<List<GWTDashboardStatsDocumentResult>>() {
		public void onSuccess(List<GWTDashboardStatsDocumentResult> result){
			lastUploadedDocuments.setDocuments(result);
			lastUploadedDocuments.setHeaderResults(result.size());
			lastUploadedDocuments.unsetRefreshing();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("callbackGetLastUploadedDocuments", caught);
			lastUploadedDocuments.unsetRefreshing();
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
	 * getLastMonthTopDownloadedDocuments
	 */
	public void getLastMonthTopDownloadedDocuments() {
		if (!firstTime) {
			lastMonthTopDownloadedDocuments.setRefreshing();
		}
		ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
		endPoint.setServiceEntryPoint(Config.OKMDashboardService);		
		dashboardService.getLastMonthTopDownloadedDocuments(callbackGetLastMonthTopDownloadedDocuments);
	}
	
	/**
	 * getLastMonthTopModifiedDocuments
	 */
	public void getLastMonthTopModifiedDocuments() {
		if (!firstTime) {
			lastMonthTopModifiedDocuments.setRefreshing();
		}
		ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
		endPoint.setServiceEntryPoint(Config.OKMDashboardService);		
		dashboardService.getLastMonthTopModifiedDocuments(callbackGetLastMonthTopModifiedDocuments);
	}
	
	/**
	 * getLastWeekTopModifiedDocuments
	 */
	public void getLastWeekTopModifiedDocuments() {
		if (!firstTime) {
			lastWeekTopModifiedDocuments.setRefreshing();
		}
		ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
		endPoint.setServiceEntryPoint(Config.OKMDashboardService);		
		dashboardService.getLastWeekTopModifiedDocuments(callbackGetLastWeekTopModifiedDocuments);
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
	 * getLastUploadedDocuments
	 */
	public void getLastUploadedDocuments() {
		if (!firstTime) {
			lastUploadedDocuments.setRefreshing();
		}
		ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
		endPoint.setServiceEntryPoint(Config.OKMDashboardService);		
		dashboardService.getLastUploadedDocuments(callbackGetLastUploadedDocuments);
	}
	
	/**
	 * Refresh all panels
	 */
	public void refreshAll() {
		getLastWeekTopDownloadedDocuments();
		getLastMonthTopDownloadedDocuments();
		getLastMonthTopModifiedDocuments();
		getLastWeekTopModifiedDocuments();
		getLastModifiedDocuments();
		getLastUploadedDocuments();
	}
}
