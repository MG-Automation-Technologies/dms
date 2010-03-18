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

package es.git.openkm.frontend.client.widget.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTPermission;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMAuthService;
import es.git.openkm.frontend.client.service.OKMAuthServiceAsync;
import es.git.openkm.frontend.client.util.UserComparator;
import es.git.openkm.frontend.client.util.Util;

/**
 * Security User
 * 
 * @author jllort
 *
 */
public class SecurityUser extends Composite implements HasWidgets {
	
	private final OKMAuthServiceAsync authService = (OKMAuthServiceAsync) GWT.create(OKMAuthService.class);
	
	public UserScrollTable assignedUser;
	public UserScrollTable unassignedUser;
	private HorizontalPanel panel;
	private VerticalPanel buttonPanel;
	private SimplePanel spLeft;
	private SimplePanel spRight;
	private SimplePanel spHeight;
	private HTML addButtom;
	private HTML removeButtom;
	private String path = "";
	private String tmpUser = "";
		
	/**
	 * Security user
	 */
	public SecurityUser() {
		panel = new HorizontalPanel();
		buttonPanel = new VerticalPanel();
		assignedUser = new UserScrollTable(true);
		unassignedUser = new UserScrollTable(false);
		spLeft = new SimplePanel();
		spRight = new SimplePanel();
		spHeight = new SimplePanel();
		spLeft.setWidth("4");
		spRight.setWidth("1");
		spHeight.setHeight("30");
		
		addButtom = new HTML(Util.imageHTML("img/icon/security/add.gif"));
		removeButtom = new HTML(Util.imageHTML("img/icon/security/remove.gif"));
		
		buttonPanel.add(addButtom);
		buttonPanel.add(spHeight); // separator
		buttonPanel.add(removeButtom);
		
		addButtom.addClickListener(addButtomListener);
		removeButtom.addClickListener(removeButtomListener);
		
		panel.add(spLeft);
		panel.add(assignedUser);
		panel.add(buttonPanel);
		panel.add(unassignedUser);
		
		panel.setCellWidth(buttonPanel, "20");
		panel.setCellWidth(spLeft, "4");
		panel.setCellVerticalAlignment(buttonPanel,HasAlignment.ALIGN_MIDDLE);
		panel.setCellHorizontalAlignment(buttonPanel,HasAlignment.ALIGN_CENTER);
		
		panel.setSize("318", "235");
		
		initWidget(panel);
	}
	
	/**
	 * Add buttom listener
	 */
	ClickListener addButtomListener = new ClickListener() {
		public void onClick(Widget sender) {
			if (unassignedUser.getUser() != null) {
				tmpUser = unassignedUser.getUser();
				addUser(tmpUser, GWTPermission.READ, Main.get().securityPopup.recursive.isChecked());				
			}
		}
	};
	
	/**
	 * Remove buttom listener
	 */
	ClickListener removeButtomListener = new ClickListener() {
		public void onClick(Widget sender) {
			if (assignedUser.getUser() != null) {
				tmpUser = assignedUser.getUser();
				removeUser(tmpUser, Main.get().securityPopup.recursive.isChecked());
			}
		}
	};
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		assignedUser.langRefresh();
		unassignedUser.langRefresh();
	}
	
	/**
	 * Resets the values
	 */
	public void reset() {
		assignedUser.reset();
		unassignedUser.reset();
	}
	
	/**
	 * Call back get granted users
	 */
	final AsyncCallback callbackGetGrantedUsers = new AsyncCallback() {
		public void onSuccess(Object result) {
			HashMap users = (HashMap) result;
			List usersList = new ArrayList();
			
			// Ordering grant roles to list
			for (Iterator it = users.keySet().iterator(); it.hasNext(); ) {
				usersList.add((String) it.next());
			}
			Collections.sort(usersList, UserComparator.getInstance());
			
			for (Iterator it = usersList.iterator(); it.hasNext(); ) {
				String userName = (String) it.next();
				Byte permission = (Byte) users.get(userName);
				assignedUser.addRow(userName, permission);
			}
			
			// Only auto fit column when there's some values
			if (users.keySet().size()>0) {
				unassignedUser.autoFitColumnWidth(0);
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetGrantedUsers", caught);
		}
	};
	
	/**
	 * Call back get ungranted users
	 */
	final AsyncCallback callbackGetUngrantedUsers = new AsyncCallback() {
		public void onSuccess(Object result) {
			List roles = (List) result;
			
			for (Iterator it = roles.iterator(); it.hasNext(); ) {
				String userName = (String) it.next();
				unassignedUser.addRow(userName);
			}
			
			// Only auto fit column when there's some values
			if (roles.size()>0) {
				unassignedUser.autoFitColumnWidth(0);
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetUngrantedUsers", caught);
		}
	};
	
	/**
	 * Call back add new granted user
	 */
	final AsyncCallback callbackAddUser = new AsyncCallback() {
		public void onSuccess(Object result) {
			assignedUser.addRow(tmpUser, new Byte(GWTPermission.READ));
			unassignedUser.removeSelectedRow();
			tmpUser = "";
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("AddUser", caught);
		}
	};
	
	/**
	 * Call back revoke granted user
	 */
	final AsyncCallback callbackRevokeUser = new AsyncCallback() {
		public void onSuccess(Object result) {
			unassignedUser.addRow(tmpUser);
			unassignedUser.selectLastRow();
			assignedUser.removeSelectedRow();
			tmpUser = "";
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("RevokeUser", caught);
		}
	};
	
	/**
	 * Gets the granted users
	 */
	public void getGrantedUsers() {
		if (path != null) {
			ServiceDefTarget endPoint = (ServiceDefTarget) authService;
			endPoint.setServiceEntryPoint(Config.OKMAuthService);	
			authService.getGrantedUsers(path, callbackGetGrantedUsers);
		}
	}
	
	/**
	 * Gets the ungranted users
	 */
	public void getUngrantedUsers() {
		if (path != null) {
			ServiceDefTarget endPoint = (ServiceDefTarget) authService;
			endPoint.setServiceEntryPoint(Config.OKMAuthService);	
			authService.getUngrantedUsers(path, callbackGetUngrantedUsers);
		}
	}
	

	/**
	 * Grant the user
	 * 
	 * @param user The granted user
	 * @param permissions The permissions value
	 */
	public void addUser(String user, int permissions, boolean recursive) {
		if (path != null) {
			ServiceDefTarget endPoint = (ServiceDefTarget) authService;
			endPoint.setServiceEntryPoint(Config.OKMAuthService);	
			authService.grantUser(path, user, permissions, recursive, callbackAddUser);
		}
	}
	
	/**
	 * Revokes all user permissions
	 * 
	 * @param user The user
	 */
	public void removeUser(String user, boolean recursive) {
		if (path != null) {
			ServiceDefTarget endPoint = (ServiceDefTarget) authService;
			endPoint.setServiceEntryPoint(Config.OKMAuthService);	
			authService.revokeUser(path, user, recursive, callbackRevokeUser);
		}
	}
	
	/**
	 * Sets the path
	 * 
	 * @param path The path
	 */
	public void setPath(String path) {
		assignedUser.setPath(path);
		this.path = path;
	}
	
	/**
	 * fillWidth
	 */
	public void fillWidth() {
		assignedUser.fillWidth();
		unassignedUser.fillWidth();
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#add(com.google.gwt.user.client.ui.Widget)
	 */
	public void add(Widget w) {
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#clear()
	 */
	public void clear() {
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#iterator()
	 */
	public Iterator iterator() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#remove(com.google.gwt.user.client.ui.Widget)
	 */
	public boolean remove(Widget w) {
		return true;
	}
}