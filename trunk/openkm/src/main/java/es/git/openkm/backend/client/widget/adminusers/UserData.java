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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.backend.client.Main;
import es.git.openkm.backend.client.OKMException;
import es.git.openkm.backend.client.bean.GWTUser;
import es.git.openkm.backend.client.config.Config;
import es.git.openkm.backend.client.service.OKMUserService;
import es.git.openkm.backend.client.service.OKMUserServiceAsync;

/**
 * UserData
 * 
 * @author jllort
 *
 */
public class UserData extends Composite {
	
	private final OKMUserServiceAsync userService = (OKMUserServiceAsync) GWT.create(OKMUserService.class);
	
	private final int ACTION_NONE 	= -1;
	private final int ACTION_CREATE = 0;
	private final int ACTION_UPDATE = 1;
	private final int ACTION_VIEW = 2;
	
	private HorizontalPanel hButtonPanel;
	private HorizontalPanel hRoleButtonPanel;
	private HorizontalPanel hNewRoleButtonPanel;
	private HorizontalPanel hPanel;
	private FlexTable table;
	private FlexTable rolesTable;
	private FlexTable staticRolesTable;
	private TextBox uid;
	private TextBox mail;
	private PasswordTextBox pwd;
	private PasswordTextBox pwd2;
	private Button create;
	private Button update;
	private Button cancel;
	private Button addRole;
	private Button addNewRole;
	private GWTUser user;
	private ListBox rolesList;
	private List<String> completeRoleList;
	private TextBox newRole;
	private CheckBox active;
	private int action = ACTION_NONE;
	
	/**
	 * User roles 
	 * 
	 * @author jllort
	 * 
	 */
	class RolePanel extends HorizontalPanel {
		private String role;
		
		/**
		 * Role panel
		 * 
		 * @param role The role id
		 */
		public RolePanel(final String role) {
			this.role = role;
			HTML roleHTML = new HTML(role);
			Button delete = new Button(Main.i18n("button.delete"));
			delete.setStyleName("okm-Button");
			add(delete);
			add(new HTML("&nbsp;"));
			add(roleHTML);
			this.setCellVerticalAlignment(roleHTML, HasAlignment.ALIGN_MIDDLE);
			delete.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					// If role was on exiting role list must restore on list
					if(completeRoleList.contains(role)) {
						rolesList.addItem(role, role);
					}
					
					// Removes row
					int i= 3;
					boolean found = false;
					while (i<rolesTable.getRowCount() && !found) {
						if (((RolePanel) rolesTable.getWidget(i,0)).compare(role) ) {
							found = true;
						} else {
							i++;
						}
					}
					if (found) rolesTable.removeRow(i);
					
					// Removes role from list
					user.getRoles().remove(role);
				}
			});
		}
		
		public boolean compare(String role) {
			return role.equals(this.role);
		}
	}
	
	/**
	 * UserData
	 */
	public UserData() {
		hPanel = new HorizontalPanel();
		hRoleButtonPanel = new HorizontalPanel();
		hNewRoleButtonPanel = new HorizontalPanel();
		hButtonPanel = new HorizontalPanel();
		user = new GWTUser();
		uid = new TextBox();
		mail = new TextBox();
		pwd = new PasswordTextBox();
		pwd2 = new PasswordTextBox();
		table = new FlexTable();
		rolesTable = new FlexTable();
		staticRolesTable = new FlexTable();
		rolesList = new ListBox();
		newRole = new TextBox();
		active = new CheckBox();
		completeRoleList = new ArrayList<String>();
		
		table.setBorderWidth(0);
		table.setCellPadding(4);
		table.setCellSpacing(0);
		
		rolesTable.setBorderWidth(0);
		rolesTable.setCellPadding(4);
		rolesTable.setCellSpacing(0);
		
		staticRolesTable.setBorderWidth(0);
		staticRolesTable.setCellPadding(4);
		staticRolesTable.setCellSpacing(0);
		
		create = new Button (Main.i18n("button.create.user"));
		create.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				collectUserData();
				if (validateUserBeforeAction()) {
					createUser(user);
				}
			}
		});
		
		update = new Button (Main.i18n("button.update.user"));
		update.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				collectUserData();
				if (validateUserBeforeAction()) {
					updateUser(user);
				}
			}
		});
		
		cancel = new Button (Main.i18n("button.cancel"));
		cancel.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				Main.get().centerPanel.adminUsersPanel.users.updateUser(user); // To restore delete button must restore all row
				resetUserData();
				setAction(ACTION_CREATE);
			}
		});
		
		addRole = new Button (Main.i18n("button.add"));
		addRole.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				if (rolesList.getSelectedIndex()>0) {
					addRole.setEnabled(false);
					String role = rolesList.getValue(rolesList.getSelectedIndex());
					user.getRoles().add(role);
					rolesTable.setWidget(rolesTable.getRowCount(), 0, new RolePanel(role));
					rolesList.removeItem(rolesList.getSelectedIndex());
				}
				
			}
		});
		
		newRole.addKeyboardListener(new KeyboardListener(){
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
			}

			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
			}

			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				addNewRole.setEnabled(!newRole.getText().equals(""));
			}
		});
		
		addNewRole = new Button (Main.i18n("button.add.new.role"));
		addNewRole.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				if (validateNewRole(newRole.getText())) {
					String role = newRole.getText();
					user.getRoles().add(role);
					rolesTable.setWidget(rolesTable.getRowCount(), 0, new RolePanel(role));
					newRole.setText("");
				}
			}
		});
		addNewRole.setEnabled(false);
		
		rolesList.addChangeHandler(new ChangeHandler(){
			@Override
			public void onChange(ChangeEvent event) {
				if (rolesList.getSelectedIndex()>0) {
					addRole.setEnabled(true);
				}
			}
		});
		
		hRoleButtonPanel.add(rolesList);
		hRoleButtonPanel.add(new HTML("&nbsp;"));
		hRoleButtonPanel.add(addRole);
		
		hNewRoleButtonPanel.add(newRole);
		hNewRoleButtonPanel.add(new HTML("&nbsp;"));
		hNewRoleButtonPanel.add(addNewRole);
		
		create.setVisible(false);
		setAction(ACTION_CREATE); // By default first action is create
		
		table.setHTML(0, 0, "<b>" + Main.i18n("users.uid") + "</b>");
		table.setHTML(1, 0, "<b>" + Main.i18n("users.mail") + "</b>");
		table.setHTML(2, 0, "<b>" + Main.i18n("users.password") + "</b>");
		table.setHTML(3, 0, "<b>" + Main.i18n("users.password") + "</b>");
		table.setHTML(4, 0, "<b>" + Main.i18n("users.active") + "</b>");
		
		table.setWidget(0, 1, uid);
		table.setWidget(1, 1, mail);
		table.setWidget(2, 1, pwd);
		table.setWidget(3, 1, pwd2);
		table.setWidget(4, 1, active);
		
		table.setWidget(5, 0, rolesTable);
		table.setWidget(6, 0, staticRolesTable);
		
		table.setWidget(7, 0, create);
		hButtonPanel.add(update);
		hButtonPanel.add(new HTML("&nbsp;"));
		hButtonPanel.add(cancel);
		table.setWidget(8, 0, hButtonPanel);
		
		table.getFlexCellFormatter().setColSpan(5, 0, 2);
		table.getFlexCellFormatter().setColSpan(7, 0, 2);
		table.getFlexCellFormatter().setColSpan(8, 0, 2);
		table.getFlexCellFormatter().setHorizontalAlignment(7, 0 , HasAlignment.ALIGN_CENTER);
		table.getFlexCellFormatter().setHorizontalAlignment(8, 0 , HasAlignment.ALIGN_CENTER);
		
		rolesTable.setHTML(0, 0, "<b>" + Main.i18n("users.roles") + "</b>");
		rolesTable.setWidget(1, 0, hNewRoleButtonPanel);
		rolesTable.setWidget(2, 0, hRoleButtonPanel);
		
		staticRolesTable.setHTML(0, 0, "<b>" + Main.i18n("users.roles") + "</b>");
		
		uid.setStyleName("okm-Input");
		mail.setStyleName("okm-Input");
		pwd.setStyleName("okm-Input");
		pwd2.setStyleName("okm-Input");
		rolesList.setStyleName("okm-Input");
		newRole.setStyleName("okm-Input");
		create.setStyleName("okm-Button");
		update.setStyleName("okm-Button");
		cancel.setStyleName("okm-Button");
		addRole.setStyleName("okm-Button");
		addNewRole.setStyleName("okm-Button");
		
		hPanel.add(table);
		hPanel.add(new HTML("&nbsp;"));
		
		hPanel.setCellVerticalAlignment(table, HasAlignment.ALIGN_TOP);
		
		initWidget(hPanel);
	}
	
	/**
	 * Edit user
	 * 
	 * @param user The user data
	 */
	public void editUser(GWTUser user) {
		resetUserData();
		this.user = user;
		uid.setText(user.getId());
		mail.setText(user.getEmail());
		pwd.setText("");
		pwd2.setText("");
		active.setChecked(user.isActive());
		setUserRoles(user.getRoles());
		setAction(ACTION_UPDATE);
	}
	
	/**
	 * View the user
	 * 
	 * @param user The user data
	 */
	public void viewUser(GWTUser user) {
		resetUserData();
		this.user = user;
		uid.setText(user.getId());
		mail.setText(user.getEmail());
		pwd.setText("");
		pwd2.setText("");
		active.setChecked(user.isActive());
		setUserRoles(user.getRoles());
		setAction(ACTION_VIEW);
	}
	
	/**
	 * Creates a user
	 * 
	 */
	public void createNewUser() {
		resetUserData();
		this.user = new GWTUser();
		uid.setText("");
		mail.setText("");
		pwd.setText("");
		pwd2.setText("");
		active.setChecked(user.isActive());
		setUserRoles(user.getRoles());
		setAction(ACTION_CREATE);
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		table.setHTML(0, 0, "<b>" + Main.i18n("users.uid") + "</b>");
		table.setHTML(1, 0, "<b>" + Main.i18n("users.mail") + "</b>");
		table.setHTML(2, 0, "<b>" + Main.i18n("users.password") + "</b>");
		table.setHTML(3, 0, "<b>" + Main.i18n("users.password") + "</b>");
		rolesTable.setHTML(0, 0, "<b>" + Main.i18n("users.roles") + "</b>");
		staticRolesTable.setHTML(0, 0, "<b>" + Main.i18n("users.roles") + "</b>");
		create.setText(Main.i18n("button.create.user"));
		update.setText(Main.i18n("button.update.user"));
		cancel.setText(Main.i18n("button.cancel"));
		addRole.setText(Main.i18n("button.add"));
		addNewRole.setText(Main.i18n("button.add.new.role"));
	}
	
	/**
	 * Sets action 
	 * 
	 * @param action The action
	 */
	private void setAction(int action) {
		this.action = action;
		
		switch (action) {
			case ACTION_CREATE:
				uid.setEnabled(true);
				mail.setEnabled(true);
				pwd.setEnabled(true);
				pwd2.setEnabled(true);
				active.setEnabled(true);
				create.setVisible(true);
				hButtonPanel.setVisible(false);
				rolesTable.setVisible(true);
				staticRolesTable.setVisible(false);
				findAllRoles();
				break;
				
			case ACTION_UPDATE:
				uid.setEnabled(false);
				mail.setEnabled(true);
				pwd.setEnabled(true);
				pwd2.setEnabled(true);
				active.setEnabled(true);
				create.setVisible(false);
				hButtonPanel.setVisible(true);
				rolesTable.setVisible(true);
				staticRolesTable.setVisible(false);
				findAllRoles();
				break;
			
			case ACTION_VIEW:
				uid.setEnabled(false);
				mail.setEnabled(false);
				pwd.setEnabled(false);
				pwd2.setEnabled(false);
				active.setEnabled(false);
				create.setVisible(false);
				hButtonPanel.setVisible(false);
				rolesTable.setVisible(false);
				staticRolesTable.setVisible(true);
				findAllRoles();
				break;
		}
	}
	
	
	/**
	 * Call back create a user
	 */
	final AsyncCallback callbackCreateUser = new AsyncCallback() {
		public void onSuccess(Object result) {
			Main.get().centerPanel.adminUsersPanel.users.addUser(user);
			Main.get().centerPanel.adminUsersPanel.users.selectLastRow();
			viewUser(user);
		}
		
		public void onFailure(Throwable caught) {
			create.setEnabled(true);
			Main.get().showError("createUser", caught);
		}
	};
	
	/**
	 * Call back update user
	 */
	final AsyncCallback callbackUpdateUser = new AsyncCallback() {
		public void onSuccess(Object result) {
			Main.get().centerPanel.adminUsersPanel.users.updateUser(user);
			viewUser(user);
		}
		
		public void onFailure(Throwable caught) {
			update.setEnabled(true);
			Main.get().showError("updateUser", caught);
		}
	};
	
	/**
	 * Call back find all roles
	 */
	final AsyncCallback callbackFindAllRoles = new AsyncCallback() {
		public void onSuccess(Object result) {
			List roles = (List) result;
			completeRoleList = new ArrayList<String>();
			
			rolesList.addItem(" - ");
			addRole.setEnabled(false); // Always start with addRole button disabled
			for (Iterator it = roles.iterator(); it.hasNext();) {
				String role = (String) it.next();
				completeRoleList.add(role);
				
				if (user.getRoles()!=null && !user.getRoles().contains(role)) {
					rolesList.addItem(role, role);
				}
			}
		}
		
		public void onFailure(Throwable caught) {
			create.setEnabled(false);
			Main.get().showError("findAllRoles", caught);
		}
	};
	
	/**
	 * Create a user
	 */
	public void createUser(GWTUser user) {
		ServiceDefTarget endPoint = (ServiceDefTarget) userService;
		endPoint.setServiceEntryPoint(Config.OKMUserService);	
		userService.createUser(user, callbackCreateUser);
	}
	
	/**
	 * Update user
	 */
	public void updateUser(GWTUser user) {
		ServiceDefTarget endPoint = (ServiceDefTarget) userService;
		endPoint.setServiceEntryPoint(Config.OKMUserService);	
		userService.updateUser(user, callbackUpdateUser);
	}
	
	/**
	 * Find all roles
	 */
	public void findAllRoles() {
		ServiceDefTarget endPoint = (ServiceDefTarget) userService;
		endPoint.setServiceEntryPoint(Config.OKMUserService);	
		userService.findAllRoles(callbackFindAllRoles);
	}
	
	/**
	 * Collect user data
	 */
	private void collectUserData() {
		user.setId(uid.getText());
		user.setEmail(mail.getText());
		user.setActive(active.isChecked());
		
		// We mantain by default the same pwd
		if (!pwd.getText().equals("")) {
			user.setPass(pwd.getText());
		}
	}
	
	/**
	 * Resets the user data
	 */
	private void resetUserData() {
		user = new GWTUser();
		uid.setText("");
		mail.setText("");
		pwd.setText("");
		pwd2.setText("");
		active.setChecked(false);
		
		// Removing roles
		while(rolesTable.getRowCount()>3) {
			rolesTable.removeRow(3);
		}
		while(staticRolesTable.getRowCount()>1) {
			staticRolesTable.removeRow(1);
		}
		
		// Removing all roles
		while (rolesList.getItemCount()>0) {
			rolesList.removeItem(0);
		}
	}
	
	/**
	 * Validates user data for creation or update purposes
	 * 
	 * @return boolean validations
	 */
	private boolean validateUserBeforeAction() {
		boolean validate = true;
		boolean validateCreate = true;
		
		// Test user must be length > 0 and Test password must be the same
		validate = !user.getId().equals("") && pwd.getText().equals(pwd2.getText());
		
		// Case creation password must not be ""
		if (action==ACTION_CREATE) {
			validateCreate = !pwd.getText().equals("");
		}
		
		if (!validate) {
			Main.get().showError("validateUserBeforeAction()",new OKMException("", Main.i18n("users.error.password.equals")));
			
		} else if (!validateCreate) {
			Main.get().showError("validateUserBeforeAction()",new OKMException("", Main.i18n("users.error.password.blank")));
		}
		
		return validate && validateCreate;
	}
	
	/**
	 * Set the user roles
	 * 
	 * @param roles The roles list
	 */
	private void setUserRoles(List roles) {
		for (Iterator it = roles.iterator(); it.hasNext(); ){
			String role = (String) it.next();
			rolesTable.setWidget(rolesTable.getRowCount(), 0, new RolePanel(role));
			staticRolesTable.setHTML(staticRolesTable.getRowCount(), 0, role);
		}
	}
	
	/**
	 * Validates the new Rol
	 * 
	 * @return boolean indicates if new role is valid
	 */
	private boolean validateNewRole(String role) {
		boolean validateNoBlank = true;
		boolean validateNotExist = true;
		boolean validateNotAddedBefore = true;
		
		// Role must be length>0 and not contained on role list
		validateNoBlank = !role.equals("");
		validateNotExist = !completeRoleList.contains(role);
		
		// Test looking for some role setting before
		if (validateNoBlank && validateNotExist) {
			for (int i=3; i<rolesTable.getRowCount(); i++) {
				if (((RolePanel)rolesTable.getWidget(i,0)).compare(role)) {
					validateNotAddedBefore = false;
				}
			}
		}
		
		if (!validateNoBlank) {
			Main.get().showError("validateUserBeforeAction()",new OKMException("", Main.i18n("users.error.rol.name.empty")));
			
		} else if(!validateNotExist) {
			Main.get().showError("validateUserBeforeAction()",new OKMException("", Main.i18n("users.error.rol.exist")));
			
		} else if(!validateNotAddedBefore) {
			Main.get().showError("validateUserBeforeAction()",new OKMException("", Main.i18n("users.error.rol.exist")));
		}
		
		return validateNoBlank && validateNotExist && validateNotAddedBefore;
	}
}