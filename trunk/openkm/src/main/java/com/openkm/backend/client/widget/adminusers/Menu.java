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

package com.openkm.backend.client.widget.adminusers;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

import com.openkm.backend.client.Main;
import com.openkm.backend.client.util.Util;
import com.openkm.backend.client.widget.ConfirmPopup;

/**
 * Users menu
 * 
 * @author jllort
 *
 */
public class Menu extends Composite {
	
	private MenuBar usersMenu;
	private MenuItem edit;
	private MenuItem delete;
	private MenuItem add;
	
	private boolean optionDelete = false;
	
	/**
	 * Browser menu
	 */
	public Menu() {
		// The item selected must be called on style.css : .okm-MenuBar .gwt-MenuItem-selected
		
		// First initialize language values
		usersMenu = new MenuBar(true);
		edit = new MenuItem(Util.menuHTML("img/icon/user/user_edit.gif", Main.i18n("users.menu.edit")), true, editUser);
		edit.addStyleName("okm-MenuItem");
		usersMenu.addItem(edit);
		delete = new MenuItem(Util.menuHTML("img/icon/user/user_delete.gif", Main.i18n("users.menu.delete")), true, deleteUser);
		delete.addStyleName("okm-MenuItem");
		usersMenu.addItem(delete);
		add = new MenuItem(Util.menuHTML("img/icon/user/user_add.gif", Main.i18n("users.menu.add")), true, addUser);
		add.addStyleName("okm-MenuItem");
		usersMenu.addItem(add);
		usersMenu.setStyleName("okm-MenuBar");
		initWidget(usersMenu);
	}
	
	// Command menu to download file
	Command editUser = new Command() {
		public void execute() {
			Main.get().centerPanel.adminUsersPanel.users.editUser();
			hide();
		}
	};
	
	// Command menu to go directory file
	Command deleteUser = new Command() {
		public void execute() {
			if(optionDelete) {
				Main.get().confirmPopup.setConfirm(ConfirmPopup.CONFIRM_DELETE_USER);
				Main.get().confirmPopup.show();
			}
			hide();
		}
	};
	
	// Command menu to go directory file
	Command addUser = new Command() {
		public void execute() {
			Main.get().centerPanel.adminUsersPanel.users.addUser();
			hide();
		}
	};

	/**
	 *  Refresh language values
	 */
	public void langRefresh() {
		edit.setHTML(Util.menuHTML("img/icon/user/user_edit.gif", Main.i18n("users.menu.edit")));
		delete.setHTML(Util.menuHTML("img/icon/user/user_delete.gif", Main.i18n("users.menu.delete")));
		add.setHTML(Util.menuHTML("img/icon/user/user_add.gif", Main.i18n("users.menu.add")));
	}
	
	/**
	 * Hide popup menu
	 */
	public void hide() {
		Main.get().centerPanel.adminUsersPanel.users.menuPopup.hide();
	}
	
	/**
	 * Check menu option permissions
	 */
	public void checkMenuOptionPermissions() {
		//if (Main.get().centerPanel.adminUsersPanel.users.getUserName().equals(Config.ADMIN_USER)) {
		//	optionDelete = false;
		//} else {
			optionDelete = true;
		//}
	}
	
	/**
	 * Evaluates menu options
	 */
	public void evaluateMenuOptions() {
		if (optionDelete){enable(delete);} else {disable(delete);}
	}
	
	/**
	 * Enables menu item
	 * 
	 * @param menuItem The menu item
	 */
	public void enable(MenuItem menuItem) {
		menuItem.addStyleName("okm-MenuItem");
		menuItem.removeStyleName("okm-MenuItem-strike");
	}
	
	/**
	 * Disable the menu item
	 * 
	 * @param menuItem The menu item
	 */
	public void disable(MenuItem menuItem) {
		menuItem.removeStyleName("okm-MenuItem");
		menuItem.addStyleName("okm-MenuItem-strike");
	}
}