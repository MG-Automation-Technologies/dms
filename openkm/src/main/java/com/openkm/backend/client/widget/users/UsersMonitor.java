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

package com.openkm.backend.client.widget.users;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.openkm.backend.client.Main;
import com.openkm.backend.client.bean.GWTSessionInfo;
import com.openkm.backend.client.config.Config;
import com.openkm.backend.client.service.OKMUserService;
import com.openkm.backend.client.service.OKMUserServiceAsync;
import com.openkm.backend.client.util.SimpleDate;

/**
 * UsersMonitor
 * 
 * @author jllort
 *
 */
public class UsersMonitor extends Composite {
	
	private final OKMUserServiceAsync userService = (OKMUserServiceAsync) GWT.create(OKMUserService.class);
	
	private VerticalPanel sp;
	private FlexTable table;
	private Map<String, String> hUser;
	private Collection<String> activeUsers;

	/**
	 * UsersMonitor
	 */
	public UsersMonitor() {
		activeUsers = new Vector<String>();
		sp = new VerticalPanel();
		table = new FlexTable();
		hUser = new HashMap<String, String>();
		
		table.setBorderWidth(0);
		table.setCellPadding(4);
		table.setCellSpacing(0);
		
		table.setHTML(0, 0, Main.i18n("users.uid"));
		table.setHTML(0, 1, Main.i18n("users.active"));
		table.setHTML(0, 2, Main.i18n("users.token"));
		table.setHTML(0, 3, Main.i18n("users.creation"));
		table.setHTML(0, 4, Main.i18n("users.last.access"));
		table.setHTML(0, 5, "");
		
		table.setStyleName("okm-NoWrap");
		table.getCellFormatter().setStyleName(0, 0, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 1, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 2, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 3, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 4, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 5, "okm-Table-Title");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 3, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 4, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 5, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 0, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 3, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 4, "okm-Table-Title-RightBorder");
		
		table.getCellFormatter().setWidth(0, 5, "100%");
		
		sp.add(table);
		
		sp.setSize("100%","100%");
		
		initWidget(sp);
	}
	
	public void addRow(GWTSessionInfo si) {
		final int rows = table.getRowCount();
		final String token = si.getToken();
		
		if (!hUser.containsKey(si.getUserID())) {
			table.setHTML(rows, 0, si.getUserID());
			table.setWidget(rows,1, new Image("img/icon/user/user_green.gif"));
			table.setHTML(rows, 2, si.getToken());
			table.setHTML(rows, 3, new SimpleDate(si.getCreation()).toString("DD/MM/YYYY HH:MM:SS"));
			table.setHTML(rows, 4, new SimpleDate(si.getAccess()).toString("DD/MM/YYYY HH:MM:SS"));
			Button logoutUser = new Button(Main.i18n("button.logout"));
			logoutUser.addClickHandler(new ClickHandler() { 
				@Override
				public void onClick(ClickEvent event) {
					activeUsers.remove(""+rows);
					Main.get().centerPanel.statsPanel.usersPanel.usersMonitor.Logout(token);
					diableTableUser(rows);
				}
			});
			logoutUser.setStyleName("okm-Input");
			table.setWidget(rows, 5, logoutUser);
			table.getCellFormatter().setVerticalAlignment(rows, 1, HasAlignment.ALIGN_MIDDLE);
			table.getCellFormatter().setHorizontalAlignment(rows, 1, HasAlignment.ALIGN_CENTER);
			hUser.put(si.getUserID(), ""+rows );
			activeUsers.add(""+rows); // Sets to active user list
			
		} else {
			final int updateRow = Integer.parseInt((String) hUser.get(si.getUserID())) ;
			activeUsers.add(""+updateRow); // Sets to active user list
			table.setWidget(updateRow,1, new Image("img/icon/user/user_green.gif"));
			table.setHTML(updateRow, 2, si.getToken());
			table.setHTML(updateRow, 3, new SimpleDate(si.getCreation()).toString("DD/MM/YYYY HH:MM:SS"));
			table.setHTML(updateRow, 4, new SimpleDate(si.getAccess()).toString("DD/MM/YYYY HH:MM:SS"));
			
			if (table.getWidget(updateRow,5)==null || !(table.getWidget(updateRow,5) instanceof Button)) {
				Button logoutUser = new Button(Main.i18n("button.logout"));
				logoutUser.addClickHandler(new ClickHandler() { 
					@Override
					public void onClick(ClickEvent event) {
						activeUsers.remove(""+updateRow);
						Main.get().centerPanel.statsPanel.usersPanel.usersMonitor.Logout(token);
						diableTableUser(updateRow);
					}
				});
				logoutUser.setStyleName("okm-Input");
				table.setWidget(updateRow, 5, logoutUser);
				table.getCellFormatter().setVerticalAlignment(updateRow, 1, HasAlignment.ALIGN_MIDDLE);
				table.getCellFormatter().setHorizontalAlignment(updateRow, 1, HasAlignment.ALIGN_CENTER);
			}
		}
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		table.setHTML(0, 0, Main.i18n("users.uid"));
		table.setHTML(0, 1, Main.i18n("users.active"));
		table.setHTML(0, 2, Main.i18n("users.token"));
		table.setHTML(0, 3, Main.i18n("users.creation"));
		table.setHTML(0, 4, Main.i18n("users.last.access"));
	}
	
	/**
	 * Call back get ungranted users
	 */
	final AsyncCallback<Object> callbackLogout = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("Logout", caught);
		}
	};
	
	/**
	 * Gets the loged users
	 */
	public void Logout(String token) {
		ServiceDefTarget endPoint = (ServiceDefTarget) userService;
		endPoint.setServiceEntryPoint(Config.OKMUserService);	
		userService.logout(token, callbackLogout);
	}
	
	/**
	 * Reset values ( preparing refreshing panel )
	 */
	public void reset() {
		activeUsers = new Vector<String>();
	}
	
	/**
	 * Refreshing active users icons
	 */
	public void refreshingActiveUsersIcons() {
		for (int i=1; i<table.getRowCount(); i++) {
			if(!activeUsers.contains(""+i)) {
				diableTableUser(i);
			} 
		}
	}
	
	/**
	 * Disables the user
	 * 
	 * @param row User row
	 */
	private void diableTableUser(int row) {
		table.setHTML(row, 2, "&nbsp;");
		table.setWidget(row, 1, new Image("img/icon/user/user_red.gif"));
		table.setHTML(row, 5, "&nbsp;");
	}
}