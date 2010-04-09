/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package es.git.openkm.backend.client.widget.workflow;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

import es.git.openkm.backend.client.Main;
import es.git.openkm.backend.client.util.Util;
import es.git.openkm.backend.client.widget.ConfirmPopup;

/**
 * Menu menu
 * 
 * @author jllort
 *
 */
public class Menu extends Composite {
	
	private MenuBar workflowMenu;
	private MenuItem delete;
	
	/**
	 * WorkflowVersionMenu menu
	 */
	public Menu() {
		// The item selected must be called on style.css : .okm-MenuBar .gwt-MenuItem-selected
		
		// First initialize language values
		workflowMenu = new MenuBar(true);
		delete = new MenuItem(Util.menuHTML("img/icon/user/user_add.gif", Main.i18n("workflow.menu.delete")), true, deleteWorkflowVersion);
		delete.addStyleName("okm-MenuItem");
		workflowMenu.addItem(delete);
		workflowMenu.setStyleName("okm-MenuBar");
		initWidget(workflowMenu);
	}
	
	// Command menu to delete
	Command deleteWorkflowVersion = new Command() {
		public void execute() {
			Main.get().confirmPopup.setConfirm(ConfirmPopup.CONFIRM_DELETE_WORKFLOW_VERSION);
			Main.get().confirmPopup.show();
			hide();
		}
	};

	/**
	 *  Refresh language values
	 */
	public void langRefresh() {
		delete.setHTML(Util.menuHTML("img/icon/user/user_add.gif", Main.i18n("workflow.menu.delete")));
	}
	
	/**
	 * Hide popup menu
	 */
	public void hide() {
		Main.get().centerPanel.workflowPanel.workflowVersionData.menuPopup.hide();
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