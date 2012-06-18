/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

package com.openkm.frontend.client.widget.filebrowser.menu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTAvailableOption;
import com.openkm.frontend.client.bean.ToolBarOption;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.MenuBase;

/**
 * Browser menu
 * 
 * @author jllort
 *
 */
public class MailMenu extends MenuBase {
	
	private ToolBarOption toolBarOption;
	private MenuBar dirMenu;
	private MenuItem delete;
	private MenuItem rename;
	private MenuItem move;
	private MenuItem copy;
	
	/**
	 * MailMenu
	 */
	public MailMenu() {
		toolBarOption = new ToolBarOption();
		// The item selected must be called on style.css : .okm-MenuBar .gwt-MenuItem-selected
		
		// First initialize language values
		dirMenu = new MenuBar(true);
		delete = new MenuItem(Util.menuHTML("img/icon/actions/delete.gif", Main.i18n("filebrowser.menu.delete")), true, deleteFile);
		delete.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(delete);
		rename = new MenuItem(Util.menuHTML("img/icon/actions/rename.gif", Main.i18n("filebrowser.menu.rename")), true, renameFile);
		rename.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(rename);
		move = new MenuItem(Util.menuHTML("img/icon/actions/move_document.gif", Main.i18n("filebrowser.menu.move")), true, moveFile);
		move.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(move);
		copy = new MenuItem(Util.menuHTML("img/icon/actions/copy.gif", Main.i18n("filebrowser.menu.copy")), true, copyFile);
		copy.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(copy);
		
		dirMenu.setStyleName("okm-MenuBar");
		initWidget(dirMenu);
	}
	
	// Command menu to lock file
	Command deleteFile = new Command() {
		public void execute() {
			if (toolBarOption.deleteOption) {
				Main.get().mainPanel.desktop.browser.fileBrowser.confirmDelete();
				hide();
			}
		}
	};
	
	// Command menu to rename file
	Command renameFile = new Command() {
		public void execute() {
			if (toolBarOption.renameOption) {
				Main.get().mainPanel.desktop.browser.fileBrowser.rename();
				hide();
			}
		}
	};
	
	// Command menu to rename file
	Command moveFile = new Command() {
		public void execute() {
			if (toolBarOption.moveOption) {
				Main.get().mainPanel.desktop.browser.fileBrowser.move();
				hide();
			}
		}
	};
	
	// Command menu to rename file
	Command copyFile = new Command() {
		public void execute() {
			if (toolBarOption.copyOption) {
				Main.get().mainPanel.desktop.browser.fileBrowser.copy();
				hide();
			}
		}
	};
	
	@Override
	public void langRefresh() {
		delete.setHTML(Util.menuHTML("img/icon/actions/delete.gif", Main.i18n("filebrowser.menu.delete")));
		rename.setHTML(Util.menuHTML("img/icon/actions/rename.gif", Main.i18n("filebrowser.menu.rename")));
		move.setHTML(Util.menuHTML("img/icon/actions/move_document.gif", Main.i18n("filebrowser.menu.move")));
		copy.setHTML(Util.menuHTML("img/icon/actions/copy.gif", Main.i18n("filebrowser.menu.copy")));
	}
	@Override
	public void setOptions(ToolBarOption toolBarOption) {
		this.toolBarOption = toolBarOption;
		toolBarOption.bookmarkOption = true;
		evaluateMenuOptions();
	}
	
	@Override
	public void disableAllOptions() {
		toolBarOption = new ToolBarOption();
		evaluateMenuOptions();
	}
	
	/**
	 * Evaluates menu options
	 */
	public void evaluateMenuOptions(){
		if (toolBarOption.deleteOption){enable(delete);} else {disable(delete);}
		if (toolBarOption.renameOption){enable(rename);} else {disable(rename);}
		if (toolBarOption.moveOption){enable(move);} else {disable(move);}
		if (toolBarOption.copyOption){enable(copy);} else {disable(copy);}
	}
	
	/**
	 * Hide popup menu
	 */
	public void hide() {
		Main.get().mainPanel.desktop.browser.fileBrowser.mailMenuPopup.hide();
	}
	
	@Override
	public void setAvailableOption(GWTAvailableOption option) {
		if (!option.isDeleteOption()) {
			dirMenu.removeItem(delete);
		}
		if (!option.isRenameOption()) {
			dirMenu.removeItem(rename);
		}
		if (!option.isMoveOption()) {
			dirMenu.removeItem(move);
		}
		if (!option.isCopyOption()) {
			dirMenu.removeItem(copy);
		}	
	}
}