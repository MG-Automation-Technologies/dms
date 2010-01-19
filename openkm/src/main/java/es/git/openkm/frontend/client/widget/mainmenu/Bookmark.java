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

package es.git.openkm.frontend.client.widget.mainmenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTBookmark;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMBookmarkService;
import es.git.openkm.frontend.client.service.OKMBookmarkServiceAsync;
import es.git.openkm.frontend.client.service.OKMDocumentService;
import es.git.openkm.frontend.client.service.OKMDocumentServiceAsync;
import es.git.openkm.frontend.client.service.OKMFolderService;
import es.git.openkm.frontend.client.service.OKMFolderServiceAsync;
import es.git.openkm.frontend.client.util.Util;
import es.git.openkm.frontend.client.widget.ConfirmPopup;
import es.git.openkm.frontend.client.widget.startup.StartUp;

/**
 * Bookmark on menu
 * 
 * @author jllort
 *
 */
public class Bookmark {
	
	private final OKMBookmarkServiceAsync bookmarkService = (OKMBookmarkServiceAsync) GWT.create(OKMBookmarkService.class);
	private final OKMFolderServiceAsync folderService = (OKMFolderServiceAsync) GWT.create(OKMFolderService.class);
	private final OKMDocumentServiceAsync documentService = (OKMDocumentServiceAsync) GWT.create(OKMDocumentService.class);
	
	public static final String BOOKMARK_DOCUMENT	= "okm:document";
	public static final String BOOKMARK_FOLDER 		= "okm:folder";
	
	private List<MenuItem> bookmarks = new ArrayList<MenuItem>();
	private String nodePath = "";
	private boolean document = false;
	private boolean bookmarkEnabled = true;
	private String validPath = "";
	
	/**
	 * Bookmark
	 */
	public Bookmark(){
	}
	
	/**
	 * Inits on first load
	 */
	public void init(){
		getAll();
	}
	
	/**
	 * Callback get all
	 */
	final AsyncCallback<List<GWTBookmark>> callbackGetAll = new AsyncCallback<List<GWTBookmark>>() {
		public void onSuccess(List<GWTBookmark> result) {
			List<GWTBookmark> bookmarkList = result;
			MenuBar subMenuBookmark = Main.get().mainPanel.topPanel.mainMenu.subMenuBookmark;
			
			// Resets all bookmark menu
			resetMenu();
			bookmarks = new ArrayList<MenuItem>();
			
			for (Iterator<GWTBookmark> it = bookmarkList.iterator(); it.hasNext();) {
				final GWTBookmark bookmark = it.next();
				
				String icon = "";
				if (bookmark.getType().equals(BOOKMARK_DOCUMENT)) {
					icon = "img/icon/menu/document_bookmark.gif";
				} else if (bookmark.getType().equals(BOOKMARK_FOLDER)) {
					icon = "img/icon/menu/folder_bookmark.gif";
				}
				
				MenuItem tmpBookmark = new MenuItem(Util.menuHTML(icon, bookmark.getName()), true, new Command() {
						public void execute() {
							if (bookmarkEnabled) {

								if (bookmark.getType().equals(BOOKMARK_DOCUMENT)) {
									String path = bookmark.getPath().substring(0,bookmark.getPath().lastIndexOf("/"));
									Main.get().activeFolderTree.openAllPathFolder(path,bookmark.getPath());
								} else if (bookmark.getType().equals(BOOKMARK_FOLDER)) {
									Main.get().activeFolderTree.openAllPathFolder(bookmark.getPath(),"");
								}
							}
						}
					}
				);
				tmpBookmark.addStyleName("okm-MainMenuItem");
				subMenuBookmark.addItem(tmpBookmark);
				bookmarks.add(tmpBookmark); // Save menuItem to list to refreshing management
			}
			
			Main.get().startUp.nextStatus(StartUp.STARTUP_GET_PROPERTY_GROUP_TRANSLATIONS); // Sets the next status to loading
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getAll", caught);
		}
	};
	
	/**
	 * Callback add
	 */
	final AsyncCallback<GWTBookmark> callbackAdd = new AsyncCallback<GWTBookmark>() {
		public void onSuccess(GWTBookmark result) {
			
			MenuBar subMenuBookmark = Main.get().mainPanel.topPanel.mainMenu.subMenuBookmark;
			final GWTBookmark bookmark = result;
			
			String icon = "";	
					
			if (bookmark.getType().equals(BOOKMARK_DOCUMENT)) {
				icon = "img/icon/menu/document_bookmark.gif";
			} else if(bookmark.getType().equals(BOOKMARK_FOLDER)) {
				icon = "img/icon/menu/folder_bookmark.gif";
			}
			
			MenuItem tmpBookmark = new MenuItem(Util.menuHTML(icon, bookmark.getName()), true, new Command() {
				public void execute() {
					if (bookmarkEnabled) {
						validPath = bookmark.getPath();
						if (bookmark.getType().equals(BOOKMARK_DOCUMENT)) {
							ServiceDefTarget endPoint = (ServiceDefTarget) documentService;
							endPoint.setServiceEntryPoint(Config.OKMDocumentService);
							documentService.isValid( validPath ,callbackIsValidDocument);
						} else if (bookmark.getType().equals(BOOKMARK_FOLDER)) {
							ServiceDefTarget endPoint = (ServiceDefTarget) folderService;
							endPoint.setServiceEntryPoint(Config.OKMFolderService);	
							folderService.isValid(validPath, callbackIsValidFolder);
						}
					}
				}
			}
			);
			
			tmpBookmark.addStyleName("okm-MainMenuItem");
			subMenuBookmark.addItem(tmpBookmark);
			bookmarks.add(tmpBookmark); // Save menuItem to list to refreshing management
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("add", caught);
		}
	};
	
	/**
	 * Callback set user home
	 */
	final AsyncCallback<Object> callbackSetUserHome = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {
			if (document) {
				Main.get().mainPanel.topPanel.toolBar.setUserHome("",nodePath,BOOKMARK_DOCUMENT);
			} else {
				Main.get().mainPanel.topPanel.toolBar.setUserHome("",nodePath,BOOKMARK_FOLDER);
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("setUserHome", caught);
		}
	};
	
	/**
	 * Call back opens document passed by url param
	 */
	final AsyncCallback<Boolean> callbackIsValidDocument = new AsyncCallback<Boolean>() {
		public void onSuccess(Boolean result){
			Boolean isValid = result;
			
			if (isValid.booleanValue() && validPath!=null && !validPath.equals("")) {
				// Opens folder passed by parameter
				String path = validPath.substring(0,validPath.lastIndexOf("/"));
				Main.get().activeFolderTree.openAllPathFolder(path,validPath);
			}
			validPath = "";
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("isValid", caught);
		}
	};
	
	/**
	 * Call back opens folder passed by url param
	 */
	final AsyncCallback<Boolean> callbackIsValidFolder = new AsyncCallback<Boolean>() {
		public void onSuccess(Boolean result){
			Boolean isValid = result;
			
			if (isValid.booleanValue() && validPath!=null && !validPath.equals("")) {
				// Opens document passed by parameter
				Main.get().activeFolderTree.openAllPathFolder(validPath,"");
			}
			validPath = "";
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("isValid", caught);
		}
	};
	
	/**
	 * Resets all widgets on menu
	 */
	private void resetMenu(){
		if (!bookmarks.isEmpty()) {
			MenuBar subMenuBookmark = Main.get().mainPanel.topPanel.mainMenu.subMenuBookmark;
			
			for (Iterator<MenuItem> it = bookmarks.iterator(); it.hasNext();) {
				subMenuBookmark.removeItem(it.next());
			}
		}
	}
	
	/**
	 * Gets the bookmark list from the server
	 * 
	 */
	public void getAll() {
		ServiceDefTarget endPoint = (ServiceDefTarget) bookmarkService;
		endPoint.setServiceEntryPoint(Config.OKMBookmarkService);			
		bookmarkService.getAll(callbackGetAll);
	}
	
	/**
	 * Adds a bookmark
	 * 
	 * @param nodePath String The node path
	 * @param name String The bookmark name
	 * @param document boolean is document
	 */
	public void add(String nodePath, String name) {
		ServiceDefTarget endPoint = (ServiceDefTarget) bookmarkService;
		endPoint.setServiceEntryPoint(Config.OKMBookmarkService);			
		bookmarkService.add(nodePath, name, callbackAdd);
	}
	
	/**
	 * Sets the user home
	 * 
	 */
	public void setUserHome() {
		if (nodePath!=null && !nodePath.equals("")) { 
			ServiceDefTarget endPoint = (ServiceDefTarget) bookmarkService;
			endPoint.setServiceEntryPoint(Config.OKMBookmarkService);			
			bookmarkService.setUserHome(nodePath, callbackSetUserHome);
		}
	}
	
	/**
	 * Show confirmation to set default home
	 * 
	 * @param nodePath String The node path
	 * @param document boolean is document
	 */
	public void confirmSetHome(String nodePath, boolean document) {
		this.nodePath = nodePath;
		this.document = document;
		Main.get().confirmPopup.setConfirm(ConfirmPopup.CONFIRM_SET_DEFAULT_HOME);
		Main.get().confirmPopup.show();
	}
	
	/**
	 * Disables bookmarks
	 */
	public void disableBookmarks() {
		bookmarkEnabled = false;
		for (Iterator<MenuItem> it = bookmarks.iterator(); it.hasNext();) {
			(it.next()).addStyleName("okm-MenuItem-strike");
		}
	}
	
	/**
	 * Enable bookmarks
	 */
	public void enableBookmarks() {
		bookmarkEnabled = true;
		for (Iterator<MenuItem> it = bookmarks.iterator(); it.hasNext();) {
			(it.next()).removeStyleName("okm-MenuItem-strike");
		}
	}
}