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

package com.openkm.frontend.client.widget.notify;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.service.OKMAuthService;
import com.openkm.frontend.client.service.OKMAuthServiceAsync;
import com.openkm.frontend.client.util.OKMBundleResources;

/**
 * NotifyUser
 * 
 * @author jllort
 *
 */
public class NotifyUser extends Composite {
	
	private final OKMAuthServiceAsync authService = (OKMAuthServiceAsync) GWT.create(OKMAuthService.class);
	
	private HorizontalPanel hPanel;
	private UserScrollTable notifyUsersTable;
	private UserScrollTable userTable;
	private VerticalPanel buttonPanel;
	private Image addButtom;
	private Image removeButtom;
	
	/**
	 * NotifyUser
	 */
	public NotifyUser() {
		hPanel = new HorizontalPanel();
		notifyUsersTable = new UserScrollTable(true);
		userTable = new UserScrollTable(false);
		
		buttonPanel = new VerticalPanel();
		addButtom = new Image(OKMBundleResources.INSTANCE.add());
		removeButtom = new Image(OKMBundleResources.INSTANCE.remove());
		
		HTML space = new HTML("");
		buttonPanel.add(addButtom);
		buttonPanel.add(space); // separator
		buttonPanel.add(removeButtom);
		
		buttonPanel.setCellHeight(space, "40");
		
		addButtom.addClickHandler(addButtomHandler);
		removeButtom.addClickHandler(removeButtomHandler);
		
		hPanel.setSize("375","140");
		hPanel.add(notifyUsersTable);
		hPanel.add(buttonPanel);
		hPanel.add(userTable);
		hPanel.setCellVerticalAlignment(buttonPanel,VerticalPanel.ALIGN_MIDDLE);
		hPanel.setCellHorizontalAlignment(buttonPanel,HorizontalPanel.ALIGN_CENTER);
		hPanel.setCellWidth(buttonPanel,"20");
		
		notifyUsersTable.addStyleName("okm-Input");
		userTable.addStyleName("okm-Input");
		
		reset();
		
		initWidget(hPanel);
	}
	
	/**
	 * reset
	 */
	public void reset() {
		notifyUsersTable.reset();
		userTable.reset();
	}
	
	/**
	 * resetAvailableUsersTable
	 */
	public void resetAvailableUsersTable() {
		userTable.reset();
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		notifyUsersTable.langRefresh();
		userTable.langRefresh();
	}
	
	/**
	 * Add buttom handler
	 */
	ClickHandler addButtomHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (userTable.getUser() != null) {
				notifyUsersTable.addRow(userTable.getUser());	
				notifyUsersTable.selectLastRow();
				userTable.removeSelectedRow();
				Main.get().fileUpload.disableErrorNotify();  // Used in both widgets
				Main.get().notifyPopup.disableErrorNotify(); // has no bad effect disabling both
			}
		}
	};
	
	/**
	 * Remove buttom handler
	 */
	ClickHandler removeButtomHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (notifyUsersTable.getUser() != null) {
				userTable.addRow(notifyUsersTable.getUser());				
				userTable.selectLastRow();
				notifyUsersTable.removeSelectedRow();
			}
		}
	};
	
	/**
	 * Call back get all users
	 */
	final AsyncCallback<List<String>> callbackAllUsers = new AsyncCallback<List<String>>() {
		public void onSuccess(List<String> result) {			
			for (Iterator<String> it = result.iterator(); it.hasNext(); ) {
				userTable.addRow(it.next());
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetAllUsers", caught);
		}
	};
	
	/**
	 * Gets all users
	 */
	public void getAllUsers() {	
		authService.getAllUsers(callbackAllUsers);
	}
	
	/**
	 * Gets the all users by filter
	 */
	public void getFilteredAllUsers(String filter) {	
		authService.getFilteredAllUsers(filter, notifyUsersTable.getUsersToNotifyList(), callbackAllUsers);
	}
	
	/**
	 * getUsersToNotify
	 * 
	 * @return
	 */
	public String getUsersToNotify() {
		return notifyUsersTable.getUsersToNotify();
	}
}