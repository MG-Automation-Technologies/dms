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

package es.git.openkm.frontend.client.widget.searchuser;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMDashboardService;
import es.git.openkm.frontend.client.service.OKMDashboardServiceAsync;
import es.git.openkm.frontend.client.service.OKMSearchService;
import es.git.openkm.frontend.client.service.OKMSearchServiceAsync;

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
	
	/**
	 * UserNews
	 */
	public UserNews() {
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
	final AsyncCallback<List<String>> callbackGetUserSearchs = new AsyncCallback<List<String>>() {
		public void onSuccess(List<String> result){
			List<String> documentList = result;		
			
			table.removeAllRows();
			
			for (Iterator<String> it = documentList.iterator(); it.hasNext();){
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
			Main.get().mainPanel.dashboard.newsDashboard.getUserSearchs(true);
			status.unsetFlag_deleteSearch();
		}
		
		public void onFailure(Throwable caught) {
			status.unsetFlag_deleteSearch();
			Main.get().showError("DeleteSearch", caught);
		}
	};
		
	/**
	 * Adds a new row
	 * 
	 * @param search The search value
	 */
	public void addRow(String search) {
		int rows = table.getRowCount();
		
		table.setHTML(rows, 0, search);
		table.setHTML(rows, 1, "");
		
		// The hidden column extends table to 100% width
		CellFormatter cellFormatter = table.getCellFormatter();
		cellFormatter.setWidth(rows, 1, "100%");
		
		table.getRowFormatter().setStyleName(rows, "okm-userNews");
		setRowWordWarp(rows, 2, false);
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
		endPoint.setServiceEntryPoint(Config.OKMDashboardService);
		dashboardService.getUserSearchs(callbackGetUserSearchs);
	}
	
	/**
	 * Gets a search
	 */
	public void getSearch() {
		if (getSelectedRow() >= 0) {
			String name = table.getText(getSelectedRow(), 0);
			Main.get().mainPanel.search.searchResult.getSearch(name);
		}
	}
	
	/**
	 * Deletes a Search
	 */
	public void deleteSearch() {
		if (getSelectedRow() >= 0) {
			status.setFlag_deleteSearch();
			String name = table.getText(getSelectedRow(),0);
			ServiceDefTarget endPoint = (ServiceDefTarget) searchService;
			endPoint.setServiceEntryPoint(Config.OKMSearchService);
			searchService.deleteSearch(name, callbackDeleteSearch);
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
	