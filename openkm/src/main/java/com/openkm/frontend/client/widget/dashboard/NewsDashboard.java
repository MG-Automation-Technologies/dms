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

package com.openkm.frontend.client.widget.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTDashboardStatsDocumentResult;
import com.openkm.frontend.client.config.Config;
import com.openkm.frontend.client.service.OKMDashboardService;
import com.openkm.frontend.client.service.OKMDashboardServiceAsync;

/**
 * NewsDashboard
 * 
 * @author jllort
 *
 */
public class NewsDashboard extends WidgetToFire {
	
	private final OKMDashboardServiceAsync dashboardService = (OKMDashboardServiceAsync) GWT.create(OKMDashboardService.class);
	
	private final int NUMBER_OF_COLUMNS = 2;
	
	private HorizontalPanel hPanel;
	private Map<String,DashboardWidget> hWidgetSearch= new HashMap<String,DashboardWidget>();
	private List<String> keyMap = new ArrayList<String>();
	private VerticalPanel vPanelLeft;
	private VerticalPanel vPanelRight;
	private int columnWidth = 0;
	private int actualSearchRefreshing = 0;
	private String actualRefreshingKey = "";
	private boolean refreshFind = true;
	private int newsDocuments = 0;
	Timer newsRefreshing;
	private boolean firstTime = true;
	
	/**
	 * NewsDashboard
	 */
	public NewsDashboard() {
		vPanelLeft = new VerticalPanel();
		vPanelRight = new VerticalPanel();
		hPanel = new HorizontalPanel();
		
		hPanel.add(vPanelLeft);
		hPanel.add(vPanelRight);
		
		initWidget(hPanel);
	}
	
	/**
	 * Gets all search callback
	 */
	final AsyncCallback<List<String>> callbackGetUserSearchs = new AsyncCallback<List<String>>() {
		public void onSuccess(List<String> result){			
			// Drops widget panel , prevent user deletes query
			List<String> keysToRemoveList = new ArrayList<String>();
			for (ListIterator<String> it = keyMap.listIterator(); it.hasNext();) {
				String key = it.next();
				
				if(!result.contains(key)) {
					DashboardWidget dashboardWidget = hWidgetSearch.get(key);
					if (dashboardWidget.getParent().equals(vPanelLeft)) {
						vPanelLeft.remove(dashboardWidget);
					} else if (dashboardWidget.getParent().equals(vPanelRight)) {
						vPanelRight.remove(dashboardWidget);
					}
					keysToRemoveList.add(key);
				}
			}
			keyMap.removeAll(keysToRemoveList);
			
			// Adds new widget
			for (ListIterator<String> it= result.listIterator(); it.hasNext();) {
				String key = it.next();
				if (!keyMap.contains(key)) {
					keyMap.add(key);
					DashboardWidget dashboardWidget = new DashboardWidget( key, key, "img/icon/news.gif", true);
					dashboardWidget.setWidgetToFire(Main.get().mainPanel.dashboard.newsDashboard);
					hWidgetSearch.put(key, dashboardWidget);
					dashboardWidget.setWidth(columnWidth);
					dashboardWidget.setHeaderResults(0);
					
					// Distribute widgets left / rigth
					if (vPanelLeft.getWidgetCount()<=vPanelRight.getWidgetCount()) {
						vPanelLeft.add(dashboardWidget);
					} else {
						vPanelRight.add(dashboardWidget);
					}
				}
			}	
			
			if (refreshFind) {
				refreshFind = false;
				refreshAllSearchs();
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getUserSearchs", caught);
		}
	};
	
	/**
	 * Gets the find search callback
	 */
	final AsyncCallback<List<GWTDashboardStatsDocumentResult>> callbackFind = new AsyncCallback<List<GWTDashboardStatsDocumentResult>>() {
		public void onSuccess(List<GWTDashboardStatsDocumentResult> result){
			DashboardWidget dashboardWidget = hWidgetSearch.get(actualRefreshingKey);
			dashboardWidget.setDocuments(result);
			dashboardWidget.setHeaderResults(result.size());
			newsDocuments += dashboardWidget.getNotViewed();
			find(actualSearchRefreshing++);
			dashboardWidget.unsetRefreshing();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getAllSearchs", caught);
			hWidgetSearch.get(actualRefreshingKey).unsetRefreshing();
		}
	};
	
	/**
	 * setWidth
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		this.columnWidth = width/NUMBER_OF_COLUMNS;
	}
	
	/**
	 * getAllSearchs
	 */
	public void getUserSearchs(boolean refreshFind) {
		this.refreshFind = refreshFind;
		ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
		endPoint.setServiceEntryPoint(Config.OKMDashboardService);		
		dashboardService.getUserSearchs(callbackGetUserSearchs);
	}

	/**
	 * refreshAllSearchs
	 */
	private void find(int value) {
		if (keyMap.size()>value) {
			actualRefreshingKey = keyMap.get(value);
			if (!firstTime) {
				hWidgetSearch.get(actualRefreshingKey).setRefreshing();
			}
			ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
			endPoint.setServiceEntryPoint(Config.OKMDashboardService);	
			dashboardService.find(actualRefreshingKey, callbackFind);
		} else {
			Main.get().mainPanel.bottomPanel.userInfo.setNewsDocuments(newsDocuments);
			newsRefreshing = new Timer() {
				public void run() {
					refreshAllSearchs();
				}
			};
			
			newsRefreshing.scheduleRepeating(1800*1000); // 30 min
			firstTime = false;
		}
	}
	
	/**
	 * Refreshing all searchs
	 */
	public void refreshAllSearchs(){
		newsDocuments = 0;
		actualSearchRefreshing = 0;
		find(actualSearchRefreshing++);
	}

	@Override
	public void decrementNewDocuments(int value) {
		newsDocuments -= value;
		Main.get().mainPanel.bottomPanel.userInfo.setNewsDocuments(newsDocuments);
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		for (ListIterator<String> it = keyMap.listIterator(); it.hasNext();) {
			DashboardWidget dashboardWidget = hWidgetSearch.get(it.next());
			dashboardWidget.langRefresh();
		}
	}
}