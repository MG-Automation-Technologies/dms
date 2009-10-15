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
import es.git.openkm.frontend.client.bean.GWTDashboardStatsMailResult;
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
	
	private DashboardWidget userLastImportedMails;
	private DashboardWidget lastImportedAttachments;
	private DashboardWidget userLastMails;
	
	private boolean firstTime = true;
	
	/**
	 * GeneralDashboard
	 */
	public MailDashboard() {
		vPanelLeft = new VerticalPanel();
		vPanelRight = new VerticalPanel();
		hPanel = new HorizontalPanel();
		
		userLastImportedMails = new DashboardWidget("LastWeekTopDownloadedDocuments","dashboard.mail.last.imported.mails", "img/icon/actions/download.gif", true);
		lastImportedAttachments = new DashboardWidget("LastImportedAttachments", "dashboard.mail.last.imported.attached.documents", "img/icon/actions/checkin.gif", true);
		userLastMails = new DashboardWidget("UserLastMails", "dashboard.mail.user.last.mails", "img/icon/actions/checkin.gif", true);
		
		vPanelLeft.add(userLastImportedMails);
		vPanelLeft.add(userLastMails);
		vPanelRight.add(lastImportedAttachments);
		
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
		userLastImportedMails.langRefresh();
		userLastMails.langRefresh();
		lastImportedAttachments.langRefresh();
	}
	
	/**
	 * setWidth
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		int columnWidth = width/NUMBER_OF_COLUMNS;
		
		// Trying to distribute widgets on columns with max size
		userLastImportedMails.setWidth(columnWidth);
		userLastMails.setWidth(columnWidth);
		lastImportedAttachments.setWidth(columnWidth);
	}
	
	/**
	 * Get last user imported mails callback
	 */
	final AsyncCallback<List<GWTDashboardStatsMailResult>> callbackGetUserLastImportedMails = new AsyncCallback<List<GWTDashboardStatsMailResult>>() {
		public void onSuccess(List<GWTDashboardStatsMailResult> result){
			userLastImportedMails.setMails(result);
			userLastImportedMails.setHeaderResults(result.size());
			userLastImportedMails.unsetRefreshing();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getUserLastImportedMails", caught);
			userLastImportedMails.unsetRefreshing();
		}
	};
	
	/**
	 * Gets last imported mail attachments documents callback
	 */
	final AsyncCallback<List<GWTDashboardStatsDocumentResult>> callbackGetUserLastImportedMailAttachments = new AsyncCallback<List<GWTDashboardStatsDocumentResult>>() {
		public void onSuccess(List<GWTDashboardStatsDocumentResult> result){
			lastImportedAttachments.setDocuments(result);
			lastImportedAttachments.setHeaderResults(result.size());
			lastImportedAttachments.unsetRefreshing();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getUserLastImportedMailAttachments", caught);
			lastImportedAttachments.unsetRefreshing();
		}
	};
	
	/**
	 * Get last user mails callback
	 */
	final AsyncCallback<List<GWTDashboardStatsDocumentResult>> callbackGetUserLastMails = new AsyncCallback<List<GWTDashboardStatsDocumentResult>>() {
		public void onSuccess(List<GWTDashboardStatsDocumentResult> result){
			userLastMails.setDocuments(result);
			userLastMails.setHeaderResults(result.size());
			userLastMails.unsetRefreshing();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getUserLastMails", caught);
			userLastMails.unsetRefreshing();
		}
	};

	/**
	 * getLastWeekTopDownloadedDocuments
	 */
	public void getUserLastImportedMails() {
		if (!firstTime) {
			userLastImportedMails.setRefreshing();
		}
		ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
		endPoint.setServiceEntryPoint(Config.OKMDashboardService);		
		dashboardService.getUserLastImportedMails(callbackGetUserLastImportedMails);
	}
	
	/**
	 * getLastModifiedDocuments
	 */
	public void getUserLastImportedMailAttachments() {
		if (!firstTime) {
			lastImportedAttachments.setRefreshing();
		}
		ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
		endPoint.setServiceEntryPoint(Config.OKMDashboardService);		
		dashboardService.getUserLastImportedMailAttachments(callbackGetUserLastImportedMailAttachments);
	}
	
	/**
	 * getLastModifiedDocuments
	 */
	public void getUserLastMails() {
		if (!firstTime) {
			userLastMails.setRefreshing();
		}
		ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
		endPoint.setServiceEntryPoint(Config.OKMDashboardService);		
		dashboardService.getUserLastMails(callbackGetUserLastMails);
	}
	
	/**
	 * Refresh all panels
	 */
	public void refreshAll() {
		getUserLastImportedMails();
		getUserLastMails();
		getUserLastImportedMailAttachments();
	}
}
