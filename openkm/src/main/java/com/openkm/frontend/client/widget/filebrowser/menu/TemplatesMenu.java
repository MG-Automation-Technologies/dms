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

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.FileToUpload;
import com.openkm.frontend.client.bean.GWTAvailableOption;
import com.openkm.frontend.client.bean.ToolBarOption;
import com.openkm.frontend.client.contants.ui.UIFileUploadConstants;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.MenuBase;

/**
 * Browser menu templates
 * 
 * @author jllort
 *
 */
public class TemplatesMenu extends MenuBase {
	
	private ToolBarOption toolBarOption;
	private MenuBar dirMenu;
	private MenuItem checkout;
	private MenuItem checkin;
	private MenuItem cancelCheckout;
	private MenuItem delete;
	private MenuItem rename;
	private MenuItem download;
	private MenuItem lock;
	private MenuItem unlock;
	private MenuItem move;
	private MenuItem copy;
	private MenuItem createFromTemplate;
	private MenuItem export;
	
	/**
	 * MenuTemplates
	 */
	public TemplatesMenu() {
		toolBarOption = new ToolBarOption();
		// The item selected must be called on style.css : .okm-MenuBar .gwt-MenuItem-selected
		
		// First initialize language values
		dirMenu = new MenuBar(true);
		download = new MenuItem(Util.menuHTML("img/icon/actions/download.gif", Main.i18n("filebrowser.menu.download")), true, downloadFile);
		download.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(download);
		checkout = new MenuItem(Util.menuHTML("img/icon/actions/checkout.gif", Main.i18n("filebrowser.menu.checkout")), true, checkoutFile);
		checkout.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(checkout);
		checkin = new MenuItem(Util.menuHTML("img/icon/actions/checkin.gif", Main.i18n("filebrowser.menu.checkin")), true, checkinFile);
		checkin.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(checkin);
		cancelCheckout = new MenuItem(Util.menuHTML("img/icon/actions/cancel_checkout.gif", Main.i18n("filebrowser.menu.checkout.cancel")), true, cancelCheckinFile);
		cancelCheckout.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(cancelCheckout);
		lock = new MenuItem(Util.menuHTML("img/icon/actions/lock.gif", Main.i18n("filebrowser.menu.lock")), true, lockFile);
		lock.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(lock);
		unlock = new MenuItem(Util.menuHTML("img/icon/actions/unlock.gif", Main.i18n("filebrowser.menu.unlock")), true, unlockFile);
		unlock.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(unlock);
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
		export = new MenuItem(Util.menuHTML("img/icon/actions/export.gif", Main.i18n("filebrowser.menu.export")), true, exportToFile);
		export.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(export);
		createFromTemplate = new MenuItem(Util.menuHTML("img/icon/actions/create_from_template.gif", Main.i18n("filebrowser.menu.create.from.template")), true, createFileFromTemplate);
		createFromTemplate.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(createFromTemplate);
		dirMenu.setStyleName("okm-MenuBar");
		initWidget(dirMenu);
	}
	
	// Command menu to download file
	Command downloadFile = new Command() {
		public void execute() {		
			if (toolBarOption.downloadOption) {
				Main.get().mainPanel.desktop.browser.fileBrowser.table.downloadDocument(false);
				hide();
			}
		}
	};
	
	// Command menu to checkout file
	Command checkoutFile = new Command() {
		public void execute() {
			if (toolBarOption.checkoutOption) {
				Main.get().mainPanel.desktop.browser.fileBrowser.checkout();
				hide();
			}
		}
	};
	
	// Command menu to checkin file
	Command checkinFile = new Command() {
		public void execute() {
			if (toolBarOption.checkinOption) {
				FileToUpload fileToUpload = new FileToUpload();
				fileToUpload.setFileUpload(new FileUpload());
				fileToUpload.setPath(Main.get().mainPanel.desktop.browser.fileBrowser.getPath());
				fileToUpload.setAction(UIFileUploadConstants.ACTION_UPDATE);
				fileToUpload.setEnableAddButton(false);
				fileToUpload.setEnableImport(false);
				Main.get().fileUpload.enqueueFileToUpload(new ArrayList<FileToUpload>(Arrays.asList(fileToUpload)));
				hide();
			}
		}
	};
	
	// Command menu to cancel checkin file
	Command cancelCheckinFile = new Command() {
		public void execute() {
			if (toolBarOption.cancelCheckoutOption) {
				Main.get().mainPanel.desktop.browser.fileBrowser.cancelCheckout();
				hide();
			}
		}
	};
	
	// Command menu to lock file
	Command lockFile = new Command() {
		public void execute() {
			if (toolBarOption.lockOption) {
				Main.get().mainPanel.desktop.browser.fileBrowser.lock();
				hide();
			}
		}
	};
	
	// Command menu to unlock file
	Command unlockFile = new Command() {
		public void execute() {
			if (toolBarOption.unLockOption) {
				Main.get().mainPanel.desktop.browser.fileBrowser.unlock();
				hide();
			}
		}
	};
	
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
	
	// Command menu to set default home
	Command exportToFile = new Command() {
		public void execute() {
			if (toolBarOption.exportOption) {				
				Main.get().mainPanel.desktop.browser.fileBrowser.exportFolderToFile();
				hide();
			}
		}
	};
	
	// Command menu to create file from template
	Command createFileFromTemplate = new Command() {
		public void execute() {
			if (toolBarOption.createFromTemplateOption) {
				Main.get().mainPanel.desktop.browser.fileBrowser.createFromTemplate();
				hide();
			}
		}
	};

	@Override
	public void langRefresh() {
		checkout.setHTML(Util.menuHTML("img/icon/actions/checkout.gif", Main.i18n("filebrowser.menu.checkout")));
		checkin.setHTML(Util.menuHTML("img/icon/actions/checkin.gif", Main.i18n("filebrowser.menu.checkin")));
		delete.setHTML(Util.menuHTML("img/icon/actions/delete.gif", Main.i18n("filebrowser.menu.delete")));
		rename.setHTML(Util.menuHTML("img/icon/actions/rename.gif", Main.i18n("filebrowser.menu.rename")));
		cancelCheckout.setHTML(Util.menuHTML("img/icon/actions/cancel_checkout.gif", Main.i18n("filebrowser.menu.checkout.cancel")));
		lock.setHTML(Util.menuHTML("img/icon/actions/lock.gif", Main.i18n("filebrowser.menu.lock")));
		unlock.setHTML(Util.menuHTML("img/icon/actions/unlock.gif", Main.i18n("filebrowser.menu.unlock")));
		download.setHTML(Util.menuHTML("img/icon/actions/download.gif", Main.i18n("filebrowser.menu.download")));
		move.setHTML(Util.menuHTML("img/icon/actions/move_document.gif", Main.i18n("filebrowser.menu.move")));
		copy.setHTML(Util.menuHTML("img/icon/actions/copy.gif", Main.i18n("filebrowser.menu.copy")));
		export.setHTML(Util.menuHTML("img/icon/actions/export.gif", Main.i18n("filebrowser.menu.export")));
		createFromTemplate.setHTML(Util.menuHTML("img/icon/actions/copy.gif", Main.i18n("filebrowser.menu.create.from.template")));
	}
	
	@Override
	public void setOptions(ToolBarOption toolBarOption) {
		this.toolBarOption = toolBarOption;
		toolBarOption.createFromTemplateOption = true;
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
		if (toolBarOption.downloadOption){enable(download);} else {disable(download);}
		if (toolBarOption.deleteOption){enable(delete);} else {disable(delete);}
		if (toolBarOption.renameOption){enable(rename);} else {disable(rename);}
		if (toolBarOption.checkoutOption){enable(checkout);} else {disable(checkout);}
		if (toolBarOption.checkinOption){enable(checkin);} else {disable(checkin);}
		if (toolBarOption.cancelCheckoutOption){enable(cancelCheckout);} else {disable(cancelCheckout);}
		if (toolBarOption.lockOption){enable(lock);} else {disable(lock);}
		if (toolBarOption.unLockOption){enable(unlock);} else {disable(unlock);}
		if (toolBarOption.moveOption){enable(move);} else {disable(move);}
		if (toolBarOption.copyOption){enable(copy);} else {disable(copy);}
		if (toolBarOption.exportOption){enable(export);} else {disable(export);}
		if (toolBarOption.createFromTemplateOption){enable(createFromTemplate);} else {disable(createFromTemplate);}
	}
	
	@Override
	public void setAvailableOption(GWTAvailableOption option) {
		if (!option.isDownloadOption()) {
			dirMenu.removeItem(download);
		}
		if (!option.isDeleteOption()) {
			dirMenu.removeItem(delete);
		}
		if (!option.isRenameOption()) {
			dirMenu.removeItem(rename);
		}
		if (!option.isCheckoutOption()) {
			dirMenu.removeItem(checkout);
		}
		if (!option.isCheckinOption()) {
			dirMenu.removeItem(checkin);
		}
		if (!option.isCancelCheckoutOption()) {
			dirMenu.removeItem(cancelCheckout);
		}
		if (!option.isLockOption()) {
			dirMenu.removeItem(lock);
		}
		if (!option.isUnLockOption()) {
			dirMenu.removeItem(unlock);
		}
		if (!option.isMoveOption()) {
			dirMenu.removeItem(move);
		}
		if (!option.isCopyOption()) {
			dirMenu.removeItem(copy);
		}
		if (!option.isExportOption()) {
			dirMenu.removeItem(export);
		}
		if (!option.isCreateFromTemplateOption()) {
			dirMenu.removeItem(createFromTemplate);
		}
	}
	
	/**
	 * Hide popup menu
	 */
	public void hide() {
		Main.get().mainPanel.desktop.browser.fileBrowser.templatesMenuPopup.hide();
	}
}