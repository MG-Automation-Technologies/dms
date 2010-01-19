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

package es.git.openkm.backend.client.widget.users;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.backend.client.Main;
import es.git.openkm.backend.client.bean.GWTSessionInfo;
import es.git.openkm.backend.client.config.Config;
import es.git.openkm.backend.client.service.OKMUserService;
import es.git.openkm.backend.client.service.OKMUserServiceAsync;

/**
 * Users
 * 
 * @author jllort
 *
 */
public class Users extends Composite {
	
	private final OKMUserServiceAsync userService = (OKMUserServiceAsync) GWT.create(OKMUserService.class);
	
	private VerticalPanel sp;
	private HorizontalPanel refreshingPanel;
	private CheckBox refreshingCheckBox;
	private HTML refreshingEvery;
	private ListBox timerList;
	private Timer refreshingTimer;
	private HorizontalPanel refreshingIndicator;
	private HTML refreshingTxtIndicator;
	private Button refresh;
	
	private int timerValues[] = {5*1000, 15*1000, 30*1000, 1*60*1000, 5*60*1000, 15*60*1000};
	
	/**
	 * Users
	 */
	public Users () {
		sp = new VerticalPanel();
		refreshingPanel = new HorizontalPanel();
		refresh = new Button(Main.i18n("button.refresh"));
		refreshingIndicator = new HorizontalPanel();
		refreshingIndicator.setVisible(false);
		
		refresh.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				refreshingCheckBox.setEnabled(false);
				refresh.setEnabled(false);
				setVisibleRefreshing(true);
				getLogedUsers();
			}
		});
		
		sp.setSize("100%","100%");
		
		refreshingEvery = new HTML(Main.i18n("users.refreshing.every"));
		
		timerList = new ListBox();
		timerList.addItem(Main.i18n("users.refreshing.every.5.seconds"));
		timerList.addItem(Main.i18n("users.refreshing.every.15.seconds"));
		timerList.addItem(Main.i18n("users.refreshing.every.30.seconds"));
		timerList.addItem(Main.i18n("users.refreshing.every.1.minute"));
		timerList.addItem(Main.i18n("users.refreshing.every.5.minutes"));
		timerList.addItem(Main.i18n("users.refreshing.every.15.minutes"));
		timerList.addChangeHandler(new ChangeHandler(){
			@Override
			public void onChange(ChangeEvent event) {
				if (refreshingCheckBox.getValue() && refreshingTimer!=null) {
					refreshingTimer.scheduleRepeating(timerValues[timerList.getSelectedIndex()]);
				}
			}			
		});
		
		refreshingCheckBox = new CheckBox();
		refreshingCheckBox.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (refreshingCheckBox.getValue()) {
					refresh.setEnabled(false);
					settingTimer();
				} else {
					if (refreshingTimer!=null) {
						refreshingTimer.cancel();
						refreshingTimer=null;
					}
					refresh.setEnabled(true);
				}
			}
		});
		
		refreshingPanel.add(new HTML("&nbsp;"));
		refreshingPanel.add(refreshingCheckBox);
		refreshingPanel.add(new HTML("&nbsp;"));
		refreshingPanel.add(refreshingEvery);
		refreshingPanel.add(new HTML("&nbsp;"));
		refreshingPanel.add(timerList);
		refreshingPanel.add(new HTML("&nbsp;"));
		
		refreshingIndicator.add(new Image("img/indicator.gif"));
		refreshingIndicator.add(new HTML("&nbsp;"));
		refreshingTxtIndicator = new HTML(Main.i18n("users.refreshing"));
		refreshingIndicator.add(refreshingTxtIndicator);
		refreshingPanel.add(refresh);
		refreshingPanel.add(new HTML("&nbsp;"));
		refreshingPanel.add(refreshingIndicator);
		
		refreshingPanel.setCellVerticalAlignment(refreshingEvery, HasAlignment.ALIGN_MIDDLE);
		
		timerList.setStyleName("okm-Input");
		refresh.setStyleName("okm-Button");
		
		HTML separator = new HTML("&nbsp;");
		separator.setHeight("5px");
		sp.add(separator);
		sp.add(refreshingPanel);
		
		sp.setCellHeight(separator, "5px");
		
		getLogedUsers();
		
		initWidget(sp);
	}
	
	/**
	 * Call back get ungranted users
	 */
	final AsyncCallback<List<GWTSessionInfo>> callbackGetLogedUsers = new AsyncCallback<List<GWTSessionInfo>>() {
		public void onSuccess(List<GWTSessionInfo> result) {
			Main.get().centerPanel.statsPanel.usersPanel.usersMonitor.reset(); // Prepares for refreshing
			for (Iterator<GWTSessionInfo> it = result.iterator(); it.hasNext(); ) {
				Main.get().centerPanel.statsPanel.usersPanel.usersMonitor.addRow(it.next());
			}
			Main.get().centerPanel.statsPanel.usersPanel.usersMonitor.refreshingActiveUsersIcons();
			
			// Setting timer if it's needed
			if (refreshingCheckBox.getValue()) {
				settingTimer();
			} 
			setVisibleRefreshing(false);
			
			// Enables or disables refresh button ( depens if checkbox is checked ).
			refresh.setEnabled(!refreshingCheckBox.getValue());
			refreshingCheckBox.setEnabled(true);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetLogedUsers", caught);
			setVisibleRefreshing(false);
		}
	};
	
	/**
	 * Gets the loged users
	 */
	public void getLogedUsers() {
		ServiceDefTarget endPoint = (ServiceDefTarget) userService;
		endPoint.setServiceEntryPoint(Config.OKMUserService);	
		userService.getLogedUsers(callbackGetLogedUsers);
	}	
	
	/**
	 * Sets the timer
	 */
	private void settingTimer() {
		if (refreshingTimer==null) {
			refreshingTimer = new Timer() {
				public void run() {
					// After executing callbackGetLogedUsers we set next timer, we ensures
					// destroy timer at start running because we cab't know how much time
					// is needed to complete getting users ... these operations must be 
					// syncronized.
					setVisibleRefreshing(true);
					getLogedUsers();
					refreshingTimer.cancel(); 
					refreshingTimer=null;
				}
			};
			refreshingTimer.scheduleRepeating(timerValues[timerList.getSelectedIndex()]);
		}
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		refreshingTxtIndicator.setHTML(Main.i18n("users.refreshing"));
	}
	
	/**
	 * Sets visible refreshing panel 
	 * 
	 * @param visible boolean value visible widget
	 */
	public void setVisibleRefreshing(boolean visible) {
		refreshingIndicator.setVisible(visible);
	}
}