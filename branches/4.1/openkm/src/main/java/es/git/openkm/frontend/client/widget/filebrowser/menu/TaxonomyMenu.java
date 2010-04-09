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

package es.git.openkm.frontend.client.widget.filebrowser.menu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.bean.GWTMail;
import es.git.openkm.frontend.client.bean.GWTPermission;
import es.git.openkm.frontend.client.util.Util;
import es.git.openkm.frontend.client.widget.MenuBase;
import es.git.openkm.frontend.client.widget.upload.FancyFileUpload;

/**
 * Browser menu
 * 
 * @author jllort
 *
 */
public class TaxonomyMenu extends MenuBase {
	
	private boolean checkoutOption 			= false;
	private boolean checkinOption 			= false;
	private boolean deleteOption 			= false;
	private boolean renameOption 			= false;
	private boolean cancelCheckoutOption 	= false;
	private boolean downloadOption 			= false;
	private boolean lockOption 				= false;
	private boolean unlockOption 			= false;
	private boolean moveOption 				= false;
	private boolean addPropertyGroupOption  = false;
	private boolean copyOption				= false;
	private boolean addBookmarkOption		= false;
	private boolean setHomeOption			= false;
	private boolean exportOption			= false;
	private boolean mediaPlayerOption		= false;
	private boolean imageViewerOption		= false;
	
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
	private MenuItem bookmark;
	private MenuItem home;
	private MenuItem export;
	private MenuItem mediaPlayer;
	private MenuItem imageViewer;
	
	/**
	 * Browser menu
	 */
	public TaxonomyMenu() {
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
		bookmark = new MenuItem(Util.menuHTML("img/icon/actions/add_bookmark.gif", Main.i18n("filebrowser.menu.add.bookmark")), true, addBookmark);
		bookmark.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(bookmark);
		home = new MenuItem(Util.menuHTML("img/icon/actions/bookmark.gif", Main.i18n("filebrowser.menu.set.home")), true, setHome);
		home.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(home);
		export = new MenuItem(Util.menuHTML("img/icon/actions/export.gif", Main.i18n("filebrowser.menu.export")), true, exportToFile);
		export.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(export);
		mediaPlayer = new MenuItem(Util.menuHTML("img/icon/actions/media_preview.gif", Main.i18n("filebrowser.menu.play")), true, mediaPlayerFile);
		mediaPlayer.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(mediaPlayer);
		imageViewer = new MenuItem(Util.menuHTML("img/icon/actions/image_viewer.gif", Main.i18n("filebrowser.menu.image.viewer")), true, imageViewerFile);
		imageViewer.addStyleName("okm-MenuItem-strike");
		dirMenu.addItem(imageViewer);
		
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
	Command moveFile = new Command() {
		public void execute() {
			if (moveOption) {
				Main.get().mainPanel.browser.fileBrowser.move();
				hide();
			}
		}
	};
	
	// Command menu to rename file
	Command copyFile = new Command() {
		public void execute() {
			if (copyOption) {
				Main.get().mainPanel.browser.fileBrowser.copy();
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
	
	// Command menu to add bookmark
	Command addBookmark = new Command() {
		public void execute() {
			if (addBookmarkOption) {
				if (Main.get().mainPanel.browser.fileBrowser.isDocumentSelected()) {
					String path = Main.get().mainPanel.browser.fileBrowser.getDocument().getPath();
					Main.get().mainPanel.topPanel.mainMenu.bookmarkPopup.show(path, path.substring(path.lastIndexOf("/")+1));
				} else if (Main.get().mainPanel.browser.fileBrowser.isFolderSelected()) {
					String path = Main.get().mainPanel.browser.fileBrowser.getFolder().getPath();
					Main.get().mainPanel.topPanel.mainMenu.bookmarkPopup.show(path, path.substring(path.lastIndexOf("/")+1));
				}
				
				hide();
			}
		}
	};
	
	// Command menu to set default home
	Command setHome = new Command() {
		public void execute() {
			if (setHomeOption) {
				Main.get().mainPanel.browser.fileBrowser.setHome();
				hide();
			}
		}
	};
	
	// Command menu to set default home
	Command exportToFile = new Command() {
		public void execute() {
			if (exportOption) {				
				Main.get().mainPanel.browser.fileBrowser.exportFolderToFile();
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
		move.setHTML(Util.menuHTML("img/icon/actions/move_document.gif", Main.i18n("filebrowser.menu.move")));
		copy.setHTML(Util.menuHTML("img/icon/actions/copy.gif", Main.i18n("filebrowser.menu.copy")));
		bookmark.setHTML(Util.menuHTML("img/icon/actions/add_bookmark.gif", Main.i18n("filebrowser.menu.add.bookmark")));
		home.setHTML(Util.menuHTML("img/icon/actions/bookmark.gif", Main.i18n("filebrowser.menu.set.home")));
		export.setHTML(Util.menuHTML("img/icon/actions/export.gif", Main.i18n("filebrowser.menu.export")));
		mediaPlayer.setHTML(Util.menuHTML("img/icon/actions/media_preview.gif", Main.i18n("filebrowser.menu.play")));
		imageViewer.setHTML(Util.menuHTML("img/icon/actions/image_viewer.gif", Main.i18n("filebrowser.menu.image.viewer")));
	}
	
	/**
	 * Checks permissions associated to folder and menu options enabled actions
	 * 
	 * @param folder The folder
	 */
	public void checkMenuOptionPermissions(GWTFolder folder, GWTFolder folderParent) {
		downloadOption 			= false;
		checkoutOption 		 	= false;
		checkinOption 		 	= false;
		cancelCheckoutOption 	= false;
		lockOption 			 	= false;
		unlockOption 		 	= false;
		addPropertyGroupOption 	= false;
		addBookmarkOption		= true;
		setHomeOption			= true;
		copyOption 				= true;
		exportOption			= true;
		mediaPlayerOption		= false;
		imageViewerOption		= false;
		
		if ( (folder.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE && 
			 (folderParent.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE )  {
			deleteOption 		= true;
			renameOption 		= true;
			moveOption 			= true;
		} else {
			deleteOption 		= false;
			renameOption 		= false;
			moveOption 			= false;
		}
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
		addBookmarkOption		= true;
		setHomeOption			= true;
		copyOption 				= true;
		exportOption			= false;
		
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
				moveOption 				= true;
				addPropertyGroupOption 	= true;
			} else {
				renameOption 			= false;
				deleteOption 			= false;
				moveOption 				= false;
				addPropertyGroupOption 	= false;
			}
		} else {
			lockOption				= false;
			deleteOption 			= false;
			renameOption 			= false;
			checkoutOption 			= false;
			moveOption 				= false;
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
			moveOption			= false;
			
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
			moveOption			 	= false;
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
		downloadOption		    = false;
		checkinOption 	     	= false;
		cancelCheckoutOption 	= false;
		unlockOption 		 	= false;	
		addBookmarkOption		= false;
		setHomeOption			= false;
		copyOption 				= true;
		exportOption			= false;
		mediaPlayerOption 		= false;
		imageViewerOption 		= false;
		lockOption				= false;
		checkoutOption 			= false;
		lockOption 				= false;
		renameOption 			= false;
		addPropertyGroupOption 	= false;
		renameOption 			= false;
		
		if ( (mail.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE)  {
			if ( (folder.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE)  {
				deleteOption 			= true;
				moveOption 				= true;
			} else {
				deleteOption 			= false;
				moveOption 				= false;
			}
		} else {
			deleteOption 			= false;
			moveOption 				= false;
		}	
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
		moveOption 			 	= false;
		addPropertyGroupOption 	= false;
		addBookmarkOption		= false;
		setHomeOption			= false;
		exportOption			= false;
		mediaPlayerOption		= false;
		imageViewerOption		= false;
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
		if (moveOption){enable(move);} else {disable(move);}
		if (copyOption){enable(copy);} else {disable(copy);}
		if (addBookmarkOption){enable(bookmark);} else {disable(bookmark);}
		if (setHomeOption){enable(home);} else {disable(home);}
		if (exportOption){enable(export);} else {disable(export);}
		if (mediaPlayerOption){enable(mediaPlayer);} else {disable(mediaPlayer);}
		if (imageViewerOption){enable(imageViewer);} else {disable(imageViewer);}
	}
	
	/**
	 * Hide popup menu
	 */
	public void hide() {
		Main.get().mainPanel.browser.fileBrowser.taxonomyMenuPopup.hide();
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