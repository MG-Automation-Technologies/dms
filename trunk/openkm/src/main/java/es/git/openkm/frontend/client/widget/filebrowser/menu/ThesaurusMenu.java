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

package es.git.openkm.frontend.client.widget.filebrowser.menu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.bean.GWTMail;
import es.git.openkm.frontend.client.bean.GWTPermission;
import es.git.openkm.frontend.client.util.CommonUI;
import es.git.openkm.frontend.client.util.Util;
import es.git.openkm.frontend.client.widget.MenuBase;
import es.git.openkm.frontend.client.widget.upload.FancyFileUpload;

/**
 * ThesaurusMenu menu
 * 
 * @author jllort
 *
 */
public class ThesaurusMenu extends MenuBase {
	
	private boolean checkoutOption 			= false;
	private boolean checkinOption 			= false;
	private boolean deleteOption 			= false;
	private boolean renameOption 			= false;
	private boolean cancelCheckoutOption 	= false;
	private boolean downloadOption 			= false;
	private boolean lockOption 				= false;
	private boolean unlockOption 			= false;
	private boolean addPropertyGroupOption  = false;
	private boolean mediaPlayerOption		= false;
	private boolean imageViewerOption		= false;
	private boolean goOption				= false;
	
	private MenuBar dirMenu;
	private MenuItem checkout;
	private MenuItem checkin;
	private MenuItem cancelCheckout;
	private MenuItem delete;
	private MenuItem rename;
	private MenuItem download;
	private MenuItem lock;
	private MenuItem unlock;
	private MenuItem mediaPlayer;
	private MenuItem imageViewer;
	private MenuItem go;
	
	/**
	 * Thesaurus menu
	 */
	public ThesaurusMenu() {
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
		mediaPlayer = new MenuItem(Util.menuHTML("img/icon/actions/media_preview.gif", Main.i18n("filebrowser.menu.play")), true, mediaPlayerFile);
		mediaPlayer.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(mediaPlayer);
		imageViewer = new MenuItem(Util.menuHTML("img/icon/actions/image_viewer.gif", Main.i18n("filebrowser.menu.image.viewer")), true, imageViewerFile);
		imageViewer.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(imageViewer);
		go = new MenuItem(Util.menuHTML("img/icon/actions/goto_folder.gif", Main.i18n("search.result.menu.go.folder")), true, goDirectory);
		go.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(go);
		
		dirMenu.setStyleName("okm-MenuBar");
		initWidget(dirMenu);
	}
	
	// Command menu to download file
	Command downloadFile = new Command() {
		public void execute() {		
			if (downloadOption) {
				Main.get().mainPanel.browser.fileBrowser.table.downloadDocument(false);
				hide();
			}
		}
	};
	
	// Command menu to checkout file
	Command checkoutFile = new Command() {
		public void execute() {
			if (checkoutOption) {
				Main.get().mainPanel.browser.fileBrowser.checkout();
				hide();
			}
		}
	};
	
	// Command menu to checkin file
	Command checkinFile = new Command() {
		public void execute() {
			if (checkinOption) {
				Main.get().fileUpload.setPath(Main.get().mainPanel.browser.fileBrowser.getPath());
				Main.get().fileUpload.setAction(FancyFileUpload.ACTION_UPDATE);
				Main.get().fileUpload.showPopup(false,false);
				hide();
			}
		}
	};
	
	// Command menu to cancel checkin file
	Command cancelCheckinFile = new Command() {
		public void execute() {
			if (cancelCheckoutOption) {
				Main.get().mainPanel.browser.fileBrowser.cancelCheckout();
				hide();
			}
		}
	};
	
	// Command menu to lock file
	Command lockFile = new Command() {
		public void execute() {
			if (lockOption) {
				Main.get().mainPanel.browser.fileBrowser.lock();
				hide();
			}
		}
	};
	
	// Command menu to unlock file
	Command unlockFile = new Command() {
		public void execute() {
			if (unlockOption) {
				Main.get().mainPanel.browser.fileBrowser.unlock();
				hide();
			}
		}
	};
	
	// Command menu to lock file
	Command deleteFile = new Command() {
		public void execute() {
			if (deleteOption) {
				Main.get().mainPanel.browser.fileBrowser.confirmDelete();
				hide();
			}
		}
	};
	
	// Command menu to rename file
	Command renameFile = new Command() {
		public void execute() {
			if (renameOption) {
				Main.get().mainPanel.browser.fileBrowser.rename();
				hide();
			}
		}
	};
	
	// Command menu to rename file
	Command addPropertyGroup = new Command() {
		public void execute() {
			if (addPropertyGroupOption) {
				Main.get().groupPopup.show();
				hide();
			}
		}
	};

	// Command menu to download file
	Command mediaPlayerFile = new Command() {
		public void execute() {		
			if (mediaPlayerOption) {
				Main.get().mainPanel.browser.fileBrowser.table.mediaPlayerDocument();
				hide();
			}
		}
	};
	
	// Command menu to download file
	Command imageViewerFile = new Command() {
		public void execute() {		
			if (imageViewerOption) {
				Main.get().mainPanel.browser.fileBrowser.table.imageViewerDocument();
				hide();
			}
		}
	};
	
	// Command menu to go directory file
	Command goDirectory = new Command() {
		public void execute() {
			if (goOption) {
				String docPath = "";
				String path = "";
				if (Main.get().mainPanel.browser.fileBrowser.isDocumentSelected()) {
					docPath = Main.get().mainPanel.browser.fileBrowser.getDocument().getPath();
					path = docPath.substring(0,docPath.lastIndexOf("/"));
				}
				CommonUI.openAllFolderPath(path, docPath);
				hide();
			}
		}
	};
	
	/**
	 *  Refresh language values
	 */
	public void langRefresh() {
		checkout.setHTML(Util.menuHTML("img/icon/actions/checkout.gif", Main.i18n("filebrowser.menu.checkout")));
		checkin.setHTML(Util.menuHTML("img/icon/actions/checkin.gif", Main.i18n("filebrowser.menu.checkin")));
		delete.setHTML(Util.menuHTML("img/icon/actions/delete.gif", Main.i18n("filebrowser.menu.delete")));
		rename.setHTML(Util.menuHTML("img/icon/actions/rename.gif", Main.i18n("filebrowser.menu.rename")));
		cancelCheckout.setHTML(Util.menuHTML("img/icon/actions/cancel_checkout.gif", Main.i18n("filebrowser.menu.checkout.cancel")));
		lock.setHTML(Util.menuHTML("img/icon/actions/lock.gif", Main.i18n("filebrowser.menu.lock")));
		unlock.setHTML(Util.menuHTML("img/icon/actions/unlock.gif", Main.i18n("filebrowser.menu.unlock")));
		download.setHTML(Util.menuHTML("img/icon/actions/download.gif", Main.i18n("filebrowser.menu.download")));
		mediaPlayer.setHTML(Util.menuHTML("img/icon/actions/media_preview.gif", Main.i18n("filebrowser.menu.play")));
		imageViewer.setHTML(Util.menuHTML("img/icon/actions/image_viewer.gif", Main.i18n("filebrowser.menu.image.viewer")));
		go.setHTML(Util.menuHTML("img/icon/actions/goto_folder.gif", Main.i18n("search.result.menu.go.folder")));
	}
	
	/**
	 * Checks permissions associated to folder and menu options enabled actions
	 * 
	 * @param folder The folder
	 */
	public void checkMenuOptionPermissions(GWTFolder folder, GWTFolder folderParent) {
	}
	
	/**
	 * Checks permissions associated to document and menu options enabled actions
	 * 
	 * @param doc The document
	 */
	public void checkMenuOptionPermissions(GWTDocument doc, GWTFolder folder) {	
		String user = Main.get().workspaceUserProperties.getUser();
		
		downloadOption		    = true;
		checkinOption 	     	= false;
		cancelCheckoutOption 	= false;
		unlockOption 		 	= false;	
		goOption				= true;
		
		if (doc.getMimeType().equals("video/x-flv") || doc.getMimeType().equals("application/x-shockwave-flash") ||  
				doc.getMimeType().equals("audio/mpeg") || doc.getMimeType().equals("image/gif") ||
				doc.getMimeType().equals("image/jpeg") || doc.getMimeType().equals("image/png")) {
			mediaPlayerOption = true;
		} else {
			mediaPlayerOption = false;
		}
		
		if (doc.getMimeType().equals("image/gif") || doc.getMimeType().equals("image/jpeg") ||
				doc.getMimeType().equals("image/png")) {
			imageViewerOption = true;
		} else {
			imageViewerOption = false;
		}
		
		if ( (doc.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE)  {
			lockOption				= true;
			checkoutOption 			= true;
			
			if ( (folder.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE)  {
				renameOption 			= true;
				deleteOption 			= true;
				addPropertyGroupOption 	= true;
			} else {
				renameOption 			= false;
				deleteOption 			= false;
				addPropertyGroupOption 	= false;
			}
		} else {
			lockOption				= false;
			deleteOption 			= false;
			renameOption 			= false;
			checkoutOption 			= false;
			addPropertyGroupOption 	= false;
		}
		
		if (doc.isCheckedOut()){
			lockOption 			= false;
			unlockOption		= false;
			checkoutOption		= false;
			if (doc.getLockInfo().getOwner().equals(user)) {
				checkinOption		 	= true;
				cancelCheckoutOption 	= true;
				addPropertyGroupOption 	= true;
			} else {
				checkinOption		 	= false;
				cancelCheckoutOption 	= false;
				addPropertyGroupOption 	= false;
			}
			deleteOption		= false;
			renameOption		= false;
			
		} else if (doc.isLocked()){
			lockOption			= false;
			if (doc.getLockInfo().getOwner().equals(user)) {
				unlockOption	= true;
			} else {
				unlockOption	= false;
			}
			checkoutOption	 	 	= false;
			checkinOption		 	= false;
			cancelCheckoutOption 	= false;
			deleteOption		 	= false;
			renameOption		 	= false;
			addPropertyGroupOption 	= false;
		} else {
			unlockOption			= false;
			checkinOption			= false;
			cancelCheckoutOption	= false;
		}
		
	}
	
	/**
	 * Checks permissions associated to mail and menu options enabled actions
	 * 
	 * @param mail The mail
	 */
	public void checkMenuOptionPermissions(GWTMail mail, GWTFolder folder) {	
	}
	
	/**
	 * Disables all menu options
	 */
	public void disableAllMenuOption() {
		downloadOption 			= false;
		deleteOption 		 	= false; 
		renameOption 		 	= false; 
		checkoutOption 		 	= false;
		checkinOption 			= false;
		cancelCheckoutOption 	= false;
		lockOption 			 	= false;
		unlockOption 		 	= false;
		addPropertyGroupOption 	= false;
		mediaPlayerOption		= false;
		imageViewerOption		= false;
		goOption				= false;
	}
	
	/**
	 * Evaluates menu options
	 */
	public void evaluateMenuOptions(){
		if (downloadOption){enable(download);} else {disable(download);}
		if (deleteOption){enable(delete);} else {disable(delete);}
		if (renameOption){enable(rename);} else {disable(rename);}
		if (checkoutOption){enable(checkout);} else {disable(checkout);}
		if (checkinOption){enable(checkin);} else {disable(checkin);}
		if (cancelCheckoutOption){enable(cancelCheckout);} else {disable(cancelCheckout);}
		if (lockOption){enable(lock);} else {disable(lock);}
		if (unlockOption){enable(unlock);} else {disable(unlock);}
		if (mediaPlayerOption){enable(mediaPlayer);} else {disable(mediaPlayer);}
		if (imageViewerOption){enable(imageViewer);} else {disable(imageViewer);}
		if (goOption){enable(go);} else {disable(go);}
	}
	
	/**
	 * Hide popup menu
	 */
	public void hide() {
		Main.get().mainPanel.browser.fileBrowser.thesaurusMenuPopup.hide();
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.widget.MenuBase#enableAllMenuOptions()
	 */
	public void enableAllMenuOptions(){
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.frontend.client.widget.MenuBase#enableRootMenuOptions()
	 */
	public void enableRootMenuOptions(){
	}
}