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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.openkm.backend.client.Main;
import com.openkm.backend.client.bean.GWTUser;
import com.openkm.backend.client.config.Config;
import com.openkm.backend.client.service.OKMUserService;
import com.openkm.backend.client.service.OKMUserServiceAsync;

/**
 * 
 * Users
 * 
 * @author jllort
 *
 */
public class Users extends Composite {
	
	private final OKMUserServiceAsync userService = (OKMUserServiceAsync) GWT.create(OKMUserService.class);
	
	private VerticalPanel vPanel;
	private ExtendedFlexTable table;
	private int markedRowToDelete = -1; // indicates the deleted row
	private Map<String, GWTUser> hUsers;
	public MenuPopup menuPopup;
	
	/**
	 * Users
	 */
	public Users() {
		vPanel = new VerticalPanel();
		table = new ExtendedFlexTable();
		hUsers = new HashMap<String, GWTUser>();
		menuPopup = new MenuPopup();
		menuPopup.setStylePrimaryName("okm-MenuPopup");
		
		table.setBorderWidth(0);
		table.setCellPadding(4);
		table.setCellSpacing(0);
		
		table.setHTML(0, 0, Main.i18n("users.uid"));
		table.setHTML(0, 1, Main.i18n("users.active"));
		table.setHTML(0, 2, Main.i18n("users.mail"));
		table.setHTML(0, 3, "");
		
		table.setStyleName("okm-NoWrap");
		table.getCellFormatter().setStyleName(0, 0, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 1, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 2, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 3, "okm-Table-Title");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 3, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 0, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-RightBorder");
		
		table.getCellFormatter().setWidth(0, 3, "100%");
		
		vPanel.add(table);
		
		findAllUsers();
		
		initWidget(vPanel);
	}
	
	/**
	 * Call back get all users
	 */
	final AsyncCallback<List<GWTUser>> callbackFindAllUsers = new AsyncCallback<List<GWTUser>>() {
		public void onSuccess(List<GWTUser> result) {
			for (Iterator<GWTUser> it = result.iterator(); it.hasNext(); ) {
				addUser(it.next());
			}

		}

		public void onFailure(Throwable caught) {
			Main.get().showError("findAllUsers", caught);
		}
	};
	
	/**
	 * Call back delte user
	 */
	final AsyncCallback<Object> callbackDeleteUser = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {
			if (markedRowToDelete>=0) {
				table.removeRow(markedRowToDelete);
			}
			markedRowToDelete = -1;
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("deleteUser", caught);
		}
	};
	
	/**
	 * Gets all users
	 */
	public void findAllUsers() {
		ServiceDefTarget endPoint = (ServiceDefTarget) userService;
		endPoint.setServiceEntryPoint(Config.OKMUserService);	
		userService.findAllUsers(callbackFindAllUsers);
	}
	
	/**
	 * Delete user
	 * 
	 * @param user User data
	 * @param markedRowToDelete Row to be deleted
	 */
	public void deleteUser(GWTUser user, int markedRowToDelete) {
		this.markedRowToDelete = markedRowToDelete;
		ServiceDefTarget endPoint = (ServiceDefTarget) userService;
		endPoint.setServiceEntryPoint(Config.OKMUserService);	
		userService.deleteUser(user, callbackDeleteUser);
		Main.get().centerPanel.adminUsersPanel.userActivityLogFilter.removeUser(user.getId());
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		table.setHTML(0, 0, Main.i18n("users.uid"));
		table.setHTML(0, 1, Main.i18n("users.active"));
		table.setHTML(0, 2, Main.i18n("users.mail"));
		menuPopup.langRefresh();
	}
	
	/**
	 * Adds a user
	 * 
	 * @param user The user data
	 */
	public void addUser(final GWTUser user) {
		hUsers.put(user.getId(), user);
		final int rows = table.getRowCount();
		addUserToTable(user, rows);
		Main.get().centerPanel.adminUsersPanel.userActivityLogFilter.addUser(user.getId());
	}
	
	/**
	 * Update user data
	 * 
	 * @param user The user data
	 */
	public void updateUser(final GWTUser user) {
		int rows= 0;
		boolean found = false;
		
		// Tries to find the user row
		while (!found && rows<table.getRowCount()) {
			if (table.getHTML(rows,0).equals(user.getId())) {
				found = true;
			} else {
				rows++;
			}
		}
		
		// Update user row if found
		if (found) {
			addUserToTable(user, rows);
		}
	}
	
	/**
	 * Adds user to table
	 * 
	 * @param user The user data
	 * @param rows The row where to be added
	 */
	private void addUserToTable(final GWTUser user, final int rows) {
		table.setHTML(rows, 0, user.getId());
		if (user.isActive()) {
			table.setHTML(rows, 1, Main.i18n("users.active.yes"));
		} else {
			table.setHTML(rows, 1, Main.i18n("users.active.no"));
		}
		table.setHTML(rows, 2, user.getEmail());
		table.setHTML(rows, 3, "&nbsp;");
	}
	
	/**
	 * Show the user
	 * 
	 * @param row The row user
	 */
	public void showUser(int row) {
		Main.get().centerPanel.adminUsersPanel.userData.viewUser((GWTUser) hUsers.get(table.getText(row, 0)));
	}
	
	/**
	 * Show the menu
	 */
	public void showMenu() {
		// The browser menu depends on actual view
		// Must substract top position from Y Screen Position
		menuPopup.setPopupPosition(table.getMouseX(), table.getMouseY());
		menuPopup.checkMenuOptionPermissions();
		menuPopup.evaluateMenuOptions();
		menuPopup.show();		
	}
	
	/**
	 * Deletes the user
	 */
	public void deleteUser() {
		if (table.getSelectedRow()>0) {
			deleteUser((GWTUser) hUsers.get(table.getText(table.getSelectedRow(), 0)), table.getSelectedRow());
		}
	}
	
	/**
	 * Edits the user
	 */
	public void editUser() {
		Main.get().centerPanel.adminUsersPanel.userData.editUser((GWTUser) hUsers.get(table.getText(table.getSelectedRow(), 0)));
	}
	
	/**
	 * Adds a new user
	 */
	public void addUser() {
		Main.get().centerPanel.adminUsersPanel.userData.createNewUser();
	}
	
	/**
	 * Selects the last row
	 */
	public void selectLastRow() {
		table.setSelectedRow(table.getRowCount()-1);
	}
	
	/**
	 * Gets the user name
	 * 
	 * @return user name
	 */
	public String getUserName() {
		return table.getText(table.getSelectedRow(), 0);
	}
}