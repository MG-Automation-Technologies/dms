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

package com.openkm.frontend.client.widget.categories;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTMail;
import com.openkm.frontend.client.bean.GWTPermission;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.MenuBase;

/**
 * CategoriesMenu menu
 * 
 * @author jllort
 *
 */
public class CategoriesMenu extends MenuBase {
	
	private boolean renameOption 		= false;
	
	private boolean rootNode 			= true;  // Indicates root node selected ( option menu are specific on this case ).
	
	private MenuBar dirMenu;
	
	private MenuItem rename;
	
	public CategoriesMenu() {
		// The item selected must be called on style.css : .okm-MenuBar .gwt-MenuItem-selected
		
		// First initialize language values
		dirMenu = new MenuBar(true);
		rename = new MenuItem(Util.menuHTML("img/icon/actions/rename.gif", Main.i18n("tree.menu.directory.rename")), true, renFolder);
		rename.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(rename);
		dirMenu.setStyleName("okm-MenuBar");
		initWidget(dirMenu);
	}
	
	// Command menu to rename a new Directory
	Command renFolder = new Command() {
		public void execute() {
			if (renameOption) {
				Main.get().activeFolderTree.rename();
				Main.get().activeFolderTree.hideMenuPopup();
			}
		}
	};
	
	@Override
	public void langRefresh() {
		rename.setHTML(Util.menuHTML("img/icon/actions/delete.gif", Main.i18n("tree.menu.directory.remove")));
	}
	
	@Override
	public void evaluateMenuOptions() {
		if (renameOption) {enable(rename);} else {disable(rename);}
	}
	
	@Override
	public void enableRootMenuOptions() {
		rootNode	 = true;
		renameOption = false;
	}
	
	@Override
	public void enableAllMenuOptions() {
		rootNode		= false;
		renameOption 	= true;
	}
	
	@Override
	public void disableAllMenuOption() {
		renameOption 	= false;
	}
	
	@Override
	public void checkMenuOptionPermissions(GWTFolder folder, GWTFolder folderParent) {
		if ( (folder.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE)  {			
			// Evaluates root node case
			if (rootNode) {
				renameOption = false;
			} else if ((folderParent.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE){
				renameOption = true;
			} else {
				renameOption = false;
			}
		} else {
			renameOption = false;
		}
	}
	
	@Override
	public void checkMenuOptionPermissions(GWTMail mail, GWTFolder folder) {}
	
	@Override
	public void checkMenuOptionPermissions(GWTDocument doc, GWTFolder folder) {}
}