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

package com.openkm.frontend.client.widget.searchuser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTQueryParams;
import com.openkm.frontend.client.config.Config;
import com.openkm.frontend.client.service.OKMDashboardService;
import com.openkm.frontend.client.service.OKMDashboardServiceAsync;
import com.openkm.frontend.client.service.OKMSearchService;
import com.openkm.frontend.client.service.OKMSearchServiceAsync;

/**
 * User news searches 
 * 
 * @author jllort
 *
 */
public class UserNews extends Composite {
	
	private final OKMDashboardServiceAsync dashboardService = (OKMDashboardServiceAsync) GWT.create(OKMDashboardService.class);
	private final OKMSearchServiceAsync searchService = (OKMSearchServiceAsync) GWT.create(OKMSearchService.class);
	
	private ExtendedFlexTable table;
	public MenuPopup menuPopup;
	private Status status;
	private boolean firstTime = true;
	private List<GWTQueryParams> savedSearches;
	private int searchIdToDelete = 0;
	
	/**
	 * UserNews
	 */
	public UserNews() {
		savedSearches = new ArrayList<GWTQueryParams>();
		table = new ExtendedFlexTable();
		menuPopup = new MenuPopup();
		menuPopup.setStyleName("okm-SearchSaved-MenuPopup");
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		
		table.setBorderWidth(0);
		table.setCellSpacing(0);
		table.setCellSpacing(0);
		
		table.sinkEvents(Event.ONDBLCLICK | Event.ONMOUSEDOWN);
		
		initWidget(table);
		
	}
	
	/**
	 * Inits on first load
	 */
	public void init(){
		getUserSearchs();
	}
	
	/**
	 * Show the browser menu
	 */
	public void showMenu() {
		// The browser menu depends on actual view
		// Must substract top position from Y Screen Position
		menuPopup.setPopupPosition(table.getMouseX(), table.getMouseY());
		menuPopup.show();		
	}
	
	/**
	 * Gets the selected row value
	 * 
	 * @return The selected row value
	 */
	public int getSelectedRow(){
		return table.getSelectedRow();
	}
	
	/**
	 * Call Back get search 
	 */
	final AsyncCallback<List<GWTQueryParams>> callbackGetUserSearchs = new AsyncCallback<List<GWTQueryParams>>() {
		public void onSuccess(List<GWTQueryParams> result){
			savedSearches = result;
			table.removeAllRows();
			
			for (Iterator<GWTQueryParams> it = result.iterator(); it.hasNext();){
				addRow(it.next());
			}
			if (!firstTime) {
				status.unsetFlag_getUserNews();
			} else {
				firstTime=false;
			}
		}
		
		public void onFailure(Throwable caught) {
			if (!firstTime){
				status.unsetFlag_getUserNews();
			} else {
				firstTime=false;
			}

			Main.get().showError("GetSearchs", caught);
		}
	};
	
	/**
	 * Call Back delete search 
	 */
	final AsyncCallback<Object> callbackDeleteSearch = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {
			table.removeRow(getSelectedRow());
			table.selectPrevRow();
			
			for (Iterator<GWTQueryParams> it = savedSearches.iterator(); it.hasNext();) {
				GWTQueryParams params = it.next();
				if (params.getId() == searchIdToDelete) {
					savedSearches.remove(params);
					searchIdToDelete = 0;
					break;
				}
			}
			
			Main.get().mainPanel.dashboard.newsDashboard.getUserSearchs(true);
			status.unsetFlag_deleteSearch();
		}
		
		public void onFailure(Throwable caught) {
			status.unsetFlag_deleteSearch();
			Main.get().showError("DeleteSearch", caught);
		}
	};
	
	/**
	 * addNewSavedSearch
	 * 
	 * @param search
	 */
	public void addNewSavedSearch(GWTQueryParams search) {
		savedSearches.add(search);
		addRow(search);
	}
		
	/**
	 * Adds a new row
	 * 
	 * @param search The search value
	 */
	public void addRow(GWTQueryParams search) {
		int rows = table.getRowCount();
		
		table.setHTML(rows, 0, search.getQueryName());
		table.setHTML(rows, 1, ""+search.getId());
		table.setHTML(rows, 2, "");
		table.getFlexCellFormatter().setVisible(rows, 1, false);
		
		// The hidden column extends table to 100% width
		CellFormatter cellFormatter = table.getCellFormatter();
		cellFormatter.setWidth(rows, 2, "100%");
		
		table.getRowFormatter().setStyleName(rows, "okm-userNews");
		setRowWordWarp(rows, 3, false);
	}
	
	/**
	 * Set the WordWarp for all the row cells
	 * 
	 * @param row The row cell
	 * @param columns Number of row columns
	 * @param warp
	 */
	private void setRowWordWarp(int row, int columns, boolean warp) {
		CellFormatter cellFormatter = table.getCellFormatter();
		for (int i=0; i<columns; i++) {
			cellFormatter.setWordWrap(row, i, false);
		}
	}
	
	/**
	 * Get searchs
	 */
	public void getUserSearchs() {
		if (!firstTime) {
			status.setFlag_getUserNews();
		}
		ServiceDefTarget endPoint = (ServiceDefTarget) dashboardService;
		endPoint.setServiceEntryPoint(Config.DashboardService);
		dashboardService.getUserSearchs(callbackGetUserSearchs);
	}
	
	/**
	 * Gets a search
	 */
	public void getSearch() {
		if (getSelectedRow() >= 0) {
			int id = Integer.parseInt(table.getText(getSelectedRow(), 1));
			for (Iterator<GWTQueryParams> it = savedSearches.iterator(); it.hasNext();) {
				GWTQueryParams params = it.next();
				if (params.getId()==id) {
					Main.get().mainPanel.search.searchBrowser.searchResult.getSearch(params);
					break;
				}
			}
		}
	}
	
	/**
	 * Deletes a Search
	 */
	public void deleteSearch() {
		if (getSelectedRow() >= 0) {
			status.setFlag_deleteSearch();
			searchIdToDelete = Integer.parseInt(table.getText(getSelectedRow(),1));
			ServiceDefTarget endPoint = (ServiceDefTarget) searchService;
			endPoint.setServiceEntryPoint(Config.SearchService);
			searchService.deleteSearch(searchIdToDelete, callbackDeleteSearch);
		}
	}
	
	/**
	 * Sets the selected panel value
	 * 
	 * @param selected The selected panel value
	 */
	public void setSelectedPanel(boolean selected) {
		table.setSelectedPanel(selected);
	}
	
	/**
	 * Indicates if panel is selected
	 * 
	 * @return The value of panel ( selected )
	 */
	public boolean isPanelSelected() {
		return table.isPanelSelected();
	}

	/**
	 * 
	 */
	public void langRefresh() {
		menuPopup.langRefresh();
	}
}
	