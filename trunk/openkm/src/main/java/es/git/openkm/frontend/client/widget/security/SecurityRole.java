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
import es.git.openkm.frontend.client.util.RoleComparator;
import es.git.openkm.frontend.client.util.Util;

/**
 * Security Group
 * 
 * @author jllort
 *
 */
public class SecurityRole extends Composite implements HasWidgets {
	
	private final OKMAuthServiceAsync authService = (OKMAuthServiceAsync) GWT.create(OKMAuthService.class);
	
	public RoleScrollTable assignedRole;
	public RoleScrollTable unassignedRole;
	private HorizontalPanel panel;
	private VerticalPanel buttonPanel;
	private SimplePanel spLeft;
	private SimplePanel spRight;
	private SimplePanel spHeight;
	private HTML addButtom;
	private HTML removeButtom;
	private String path = "";
	private String tmpRole = "";
	
	/**
	 * Security group
	 */
	public SecurityRole() {
		panel = new HorizontalPanel();
		buttonPanel = new VerticalPanel();
		assignedRole = new RoleScrollTable(true);
		unassignedRole = new RoleScrollTable(false);
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
		panel.add(assignedRole);
		panel.add(buttonPanel);
		panel.add(unassignedRole);

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
			if (unassignedRole.getRole() != null) {
				tmpRole = unassignedRole.getRole();
				addRole(tmpRole, GWTPermission.READ, Main.get().securityPopup.recursive.isChecked());
			}
		}
	};
	
	/**
	 * Remove buttom listener
	 */
	ClickListener removeButtomListener = new ClickListener() {
		public void onClick(Widget sender) {
			if (assignedRole.getRole() != null) {
				tmpRole = assignedRole.getRole();
				revokeRole(tmpRole, Main.get().securityPopup.recursive.isChecked());
			}
		}
	};
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		assignedRole.langRefresh();
		unassignedRole.langRefresh();
	}
	
	/**
	 * Resets the values
	 */
	public void reset() {
		assignedRole.reset();
		unassignedRole.reset();
	}
	
	/**
	 * Call back get granted roles
	 */
	final AsyncCallback callbackGetGrantedRoles = new AsyncCallback() {
		public void onSuccess(Object result) {
			HashMap roles = (HashMap) result;
			List rolesList = new ArrayList();
			
			// Ordering grant roles to list
			for (Iterator it = roles.keySet().iterator(); it.hasNext(); ) {
				rolesList.add((String) it.next());
			}
			Collections.sort(rolesList, RoleComparator.getInstance());
			
			for (Iterator it = rolesList.iterator(); it.hasNext(); ) {
				String groupName = (String) it.next();
				Byte permission = (Byte) roles.get(groupName);
				assignedRole.addRow(groupName, permission);
			}
			
			// Only auto fit column when there's some values
			if (roles.keySet().size()>0) {
				assignedRole.autoFitColumnWidth(0);
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetGrantedRoles", caught);
		}
	};
	
	/**
	 * Call back get ungranted roles
	 */
	final AsyncCallback callbackGetUngrantedRoles = new AsyncCallback() {
		public void onSuccess(Object result) {
			List roles = (List) result;
			
			for (Iterator it = roles.iterator(); it.hasNext(); ) {
				String groupName = (String) it.next();
				unassignedRole.addRow(groupName);
			}
			
			// Only auto fit column when there's some values
			if (roles.size()>0) {
				assignedRole.autoFitColumnWidth(0);
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetUngrantedRoles", caught);
		}
	};
	
	/**
	 * Call back add new granted role
	 */
	final AsyncCallback callbackAddRole = new AsyncCallback() {
		public void onSuccess(Object result){
			assignedRole.addRow(tmpRole, new Byte(GWTPermission.READ));
			unassignedRole.removeSelectedRow();
			tmpRole = "";
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("AddRole", caught);
		}
	};
	
	/**
	 * Call back revoke granted role
	 */
	final AsyncCallback callbackRevokeRole = new AsyncCallback() {
		public void onSuccess(Object result){
			unassignedRole.addRow(tmpRole);
			unassignedRole.selectLastRow();
			assignedRole.removeSelectedRow();
			tmpRole = "";
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("RevokeRole", caught);
		}
	};
	
	/**
	 * Gets the granted roles
	 */
	public void getGrantedRoles() {
		if (path != null) {
			ServiceDefTarget endPoint = (ServiceDefTarget) authService;
			endPoint.setServiceEntryPoint(Config.OKMAuthService);	
			authService.getGrantedRoles(path, callbackGetGrantedRoles);
		}
	}
	
	/**
	 * Gets the granted roles
	 */
	public void getUngrantedRoles() {
		if (path != null) {
			ServiceDefTarget endPoint = (ServiceDefTarget) authService;
			endPoint.setServiceEntryPoint(Config.OKMAuthService);	
			authService.getUngrantedRoles(path, callbackGetUngrantedRoles);
		}
	}
	
	/**
	 * Grant the role
	 * 
	 * @param role The granted role
	 * @param permissions The permissions value
	 */
	public void addRole(String role, int permissions, boolean recursive) {
		if (path != null) {
			ServiceDefTarget endPoint = (ServiceDefTarget) authService;
			endPoint.setServiceEntryPoint(Config.OKMAuthService);	
			authService.grantRole(path, role, permissions, recursive, callbackAddRole);
		}
	}
	
	/**
	 * Revokes all role permissions
	 * 
	 * @param user The role
	 */
	public void revokeRole(String role, boolean recursive) {
		if (path != null) {
			ServiceDefTarget endPoint = (ServiceDefTarget) authService;
			endPoint.setServiceEntryPoint(Config.OKMAuthService);	
			authService.revokeRole(path, role, recursive, callbackRevokeRole);
		}
	}
	
	/**
	 * Sets the path
	 * 
	 * @param path The path
	 */
	public void setPath(String path) {
		assignedRole.setPath(path);
		this.path = path;
	}
	
	/**
	 * fillWidth
	 */
	public void fillWidth() {
		assignedRole.fillWidth();
		unassignedRole.fillWidth();
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