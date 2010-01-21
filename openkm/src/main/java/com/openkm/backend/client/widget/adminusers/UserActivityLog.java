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

package com.openkm.backend.client.widget.adminusers;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.openkm.backend.client.Main;
import com.openkm.backend.client.bean.GWTActivity;

/**
 * 
 * UserActivityLog
 * 
 * @author jllort
 *
 */
public class UserActivityLog extends Composite {
	
	private FlexTable table;
	private VerticalPanel vPanel; 
	public Status status;
	
	/**
	 * UserActivityLog
	 */
	public UserActivityLog() {
		vPanel = new VerticalPanel();
		table = new FlexTable();
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		
		table.setWidth("100%");
		table.setBorderWidth(0);
		table.setCellPadding(4);
		table.setCellSpacing(0);
		
		table.setHTML(0, 0, "<b>" + Main.i18n("users.activity.date") + "</b>");
		table.setHTML(0, 1, "<b>" + Main.i18n("users.activity.user") + "</b>");
		table.setHTML(0, 2, "<b>" + Main.i18n("users.activity.token") + "</b>");
		table.setHTML(0, 3, "<b>" + Main.i18n("users.activity.action") + "</b>");
		table.setHTML(0, 4, "<b>" + Main.i18n("users.activity.params") + "</b>");
		
		table.setStyleName("okm-NoWrap");
		table.getCellFormatter().setStyleName(0, 0, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 1, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 2, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 3, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 4, "okm-Table-Title");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 3, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 4, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 0, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 3, "okm-Table-Title-RightBorder");
		
		vPanel.add(table);
		
		initWidget(vPanel);
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		table.setHTML(0, 0, Main.i18n("users.activity.log"));
	}
	
	/**
	 * Remove all rows
	 */
	public void removeAllRows() {
		while(table.getRowCount()>1) {
			table.removeRow(1);
		}
	}
	
	/**
	 * Adds a row
	 * 
	 * @param activity The activity data
	 */
	public void addRow(GWTActivity activity) {
		int rows = table.getRowCount();
		
		table.setHTML(rows, 0, activity.getActDate().toString());
		table.setHTML(rows, 1, activity.getActUser());
		table.setHTML(rows, 2, activity.getActToken());
		table.setHTML(rows, 3, activity.getActAction());
		table.setHTML(rows, 4, activity.getActParams());
	}
	
}