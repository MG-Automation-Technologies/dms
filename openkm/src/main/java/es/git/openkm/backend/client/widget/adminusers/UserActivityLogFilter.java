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

package es.git.openkm.backend.client.widget.adminusers;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.backend.client.Main;
import es.git.openkm.backend.client.bean.GWTActivity;
import es.git.openkm.backend.client.config.Config;
import es.git.openkm.backend.client.service.OKMUserService;
import es.git.openkm.backend.client.service.OKMUserServiceAsync;
import es.git.openkm.backend.client.util.SimpleDate;

public class UserActivityLogFilter extends Composite {
	
	private final OKMUserServiceAsync userService = (OKMUserServiceAsync) GWT.create(OKMUserService.class);
	
	private static final int CALENDAR_FIRED_NONE = -1;
	private static final int CALENDAR_FIRED_FROM = 0;
	private static final int CALENDAR_FIRED_TO = 1;
	
	private VerticalPanel vPanel;
	private FlexTable table;
	private ListBox actionList;
	private TextBox fromDate;
	private TextBox toDate;
	private Image fromDateIcon;
	private Image toDateIcon;
	private Button filter;
	private PopupPanel calendarPopup;
	private CalendarWidget calendar;
	private int calendarFired = CALENDAR_FIRED_NONE;
	private Date dateFrom;
	private Date dateTo;
	public ListBox usersList;	// Mantains the actual current usersList
	
	private String actions[] = {"Auth", "LOGIN", "LOGOUT", "SESSION_EXPIRATION",
								"Document", "CANCEL_CHECKOUT_DOCUMENT", 
								"CHECKIN_DOCUMENT", "CHECKOUT_DOCUMENT", "CREATE_DOCUMENT", "DELETE_DOCUMENT", 
								"GET_CHILD_DOCUMENTS", "GET_DOCUMENT_CONTENT", "GET_DOCUMENT_CONTENT_BY_VERSION",
								"GET_DOCUMENT_PROPERTIES", "GET_DOCUMENT_VERSION_HISTORY", "GET_PROPERTY_GROUP_PROPERTIES",
								"LOCK_DOCUMENT", "MOVE_DOCUMENT", "PURGE_DOCUMENT", "RENAME_DOCUMENT", 
								"SET_DOCUMENT_CONTENT", "SET_DOCUMENT_PROPERTIES", "UNLOCK_DOCUMENT"};
	
	public UserActivityLogFilter() {
		vPanel = new VerticalPanel();
		table = new FlexTable();
		actionList = new ListBox();
		fromDate = new TextBox();
		toDate = new TextBox();
		filter = new Button(Main.i18n("button.filter"));
		calendarPopup = new PopupPanel(true);
		calendar = new CalendarWidget();
		usersList = new ListBox();
		
		fromDate.setReadOnly(true);
		toDate.setReadOnly(true);
		
		usersList.addItem("-", "");
		
		filter.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				filter.setEnabled(false);
				findActivityByFilter();
			}
		});
		filter.setEnabled(false);
		
		// Calendar widget
		calendarPopup.setWidget(calendar);
		
		calendar.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				calendarPopup.hide();
				
				switch (calendarFired) {
					case CALENDAR_FIRED_FROM:
						fromDate.setText((new SimpleDate(calendar.getDate()).toString("DD/MM/YYYY")));
						dateFrom = calendar.getDate();
						break;
					
					case CALENDAR_FIRED_TO:
						toDate.setText((new SimpleDate(calendar.getDate()).toString("DD/MM/YYYY")));
						dateTo = calendar.getDate();
						break;
				}
				calendarFired = CALENDAR_FIRED_NONE;
				evaluateFilterButton();
			}
		});
		
		filter.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {

			}
		});
		
		fromDateIcon = new Image("img/icon/user/calendar.gif");
		toDateIcon =  new Image("img/icon/user/calendar.gif");
		
		fromDateIcon.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				calendarFired = CALENDAR_FIRED_FROM;
				calendarPopup.setPopupPosition(fromDateIcon.getAbsoluteLeft(), fromDateIcon.getAbsoluteTop()-2);
				calendarPopup.show();
			}
		});
		
		toDateIcon.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				calendarFired = CALENDAR_FIRED_TO;
				calendarPopup.setPopupPosition(toDateIcon.getAbsoluteLeft(), toDateIcon.getAbsoluteTop()-2);
				calendarPopup.show();
			}
		});
		
		table.setBorderWidth(0);
		table.setCellPadding(4);
		table.setCellSpacing(0);
		
		table.setHTML(0, 0, "<b>" + Main.i18n("users.activity.log.uid") + "</b>");
		table.setHTML(1, 0, "<b>" + Main.i18n("users.activity.log.date.from") + "</b>");
		table.setHTML(1, 2, "<b>" + Main.i18n("users.activity.log.date.to") + "</b>");
		table.setHTML(2, 0, "<b>" + Main.i18n("users.activity.log.action") + "</b>");
		
		HorizontalPanel hPanelFrom = new HorizontalPanel();
		hPanelFrom.add(fromDate);
		hPanelFrom.add(fromDateIcon);
		HorizontalPanel hPanelTo = new HorizontalPanel();
		hPanelTo.add(toDate);
		hPanelTo.add(toDateIcon);
		
		fromDate.setWidth("70");
		toDate.setWidth("70");
		
		table.setWidget(0, 1, usersList);
		table.setWidget(1, 1, hPanelFrom);
		table.setWidget(1, 3, hPanelTo);
		table.setWidget(2, 1, actionList);
		table.setWidget(2, 4, filter);
		
		table.getFlexCellFormatter().setColSpan(2, 1, 3);
		
		actionList.addItem("", "");
		for (int i=0 ; i<actions.length; i++) {
			if (!actions[i].equals("Auth") && !actions[i].equals("Document") && !actions[i].equals("File")) {
				actionList.addItem(actions[i], actions[i]);
			}
		}
		
		fromDate.setStyleName("okm-Input");
		toDate.setStyleName("okm-Input");
		actionList.setStyleName("okm-Input");
		usersList.setStyleName("okm-Input");
		filter.setStyleName("okm-Button");
		
		vPanel.add(table);
		initWidget(vPanel);
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		filter.setText("button.filter");
		table.setHTML(0, 0, "<b>" + Main.i18n("users.activity.log.uid") + "</b>");
		table.setHTML(1, 0, "<b>" + Main.i18n("users.activity.log.date.from") + "</b>");
		table.setHTML(1, 2, "<b>" + Main.i18n("users.activity.log.date.to") + "</b>");
		table.setHTML(2, 0, "<b>" + Main.i18n("users.activity.log.action") + "</b>");
	}
	
	/**
	 * Call back find activity by filter
	 */
	final AsyncCallback callbackFindActivityByFilter = new AsyncCallback() {
		public void onSuccess(Object result) {
			Main.get().centerPanel.adminUsersPanel.userActivityLog.removeAllRows();
			List activityList = (List) result;
			
			for (Iterator it = activityList.iterator(); it.hasNext(); ) {
				Main.get().centerPanel.adminUsersPanel.userActivityLog.addRow((GWTActivity) it.next());
			}
			filter.setEnabled(true);
			Main.get().centerPanel.adminUsersPanel.userActivityLog.status.unsetFlag_search();
		}
		
		public void onFailure(Throwable caught) {
			filter.setEnabled(true);
			Main.get().showError("findActivityByFilter", caught);
			Main.get().centerPanel.adminUsersPanel.userActivityLog.status.unsetFlag_search();
		}
	};
	
	/**
	 * Finds activity by filter
	 */
	public void findActivityByFilter() {
		Main.get().centerPanel.adminUsersPanel.userActivityLog.status.setFlag_search();
		Main.get().centerPanel.adminUsersPanel.userActivityLog.status.refresh(Main.get().centerPanel.adminUsersPanel.userActivityLog);
		ServiceDefTarget endPoint = (ServiceDefTarget) userService;
		endPoint.setServiceEntryPoint(Config.OKMUserService);	
		userService.findActivityByFilter(dateFrom, dateTo, 
										 usersList.getValue(usersList.getSelectedIndex()), 
										 actionList.getValue(actionList.getSelectedIndex()), callbackFindActivityByFilter);
	}
	
	/**
	 * Adds a user
	 * 
	 * @param user The user id
	 */
	public void addUser(String user) {
		usersList.addItem(user, user);
	}
	
	/**
	 * Removes the user
	 * 
	 * @param user User id
	 */
	public void removeUser(String user) {
		int i=0;
		boolean found = false;
		
		while (i<usersList.getItemCount() && !found) {
			if (usersList.getValue(i).equals(user)) {
				found = true;
			} else {
				i++;
			}
		}
		
		if (found) {
			usersList.removeItem(i);
		}
	}
	
	/**
	 * evaluateFilterButton
	 */
	public void evaluateFilterButton() {
		filter.setEnabled(!fromDate.getText().equals("") && !toDate.getText().equals(""));
	}
}