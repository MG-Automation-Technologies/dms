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

package com.openkm.frontend.client.widget;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTMail;
import com.openkm.frontend.client.bean.GWTPermission;
import com.openkm.frontend.client.bean.ToolBarOption;
import com.openkm.frontend.client.config.Config;
import com.openkm.frontend.client.panel.ExtendedDockPanel;
import com.openkm.frontend.client.panel.PanelDefinition;
import com.openkm.frontend.client.service.OKMDocumentService;
import com.openkm.frontend.client.service.OKMDocumentServiceAsync;
import com.openkm.frontend.client.service.OKMFolderService;
import com.openkm.frontend.client.service.OKMFolderServiceAsync;
import com.openkm.frontend.client.service.OKMPropertyGroupService;
import com.openkm.frontend.client.service.OKMPropertyGroupServiceAsync;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.mainmenu.Bookmark;
import com.openkm.frontend.client.widget.upload.FancyFileUpload;

/**
 * ToolBar
 * 
 * @author jllort
 *
 */
public class ToolBar extends Composite implements HasAllMouseHandlers, OriginPanel {
	
	private final OKMDocumentServiceAsync documentService = (OKMDocumentServiceAsync) GWT.create(OKMDocumentService.class);
	private final OKMFolderServiceAsync folderService = (OKMFolderServiceAsync) GWT.create(OKMFolderService.class);
	private final OKMPropertyGroupServiceAsync propertyGroupService = (OKMPropertyGroupServiceAsync) GWT.create(OKMPropertyGroupService.class);
		
	private HorizontalPanel panel;
	private HTML createFolder;
	private HTML findFolder;
	private HTML lock;
	private HTML unLock;
	private HTML addDocument;
	private HTML delete;
	private HTML edit;
	private HTML checkin;
	private HTML cancelCheckout;
	private HTML download;
	private HTML downloadPdf;
	private HTML addPropertyGroup;
	private HTML removePropertyGroup;
	private HTML startWorkflow;
	private HTML addSubscription;
	private HTML removeSubscription;
	private HTML home;
	private HTML refresh;
	private HTML scanner;
		
	private boolean enabled = true;  // Indicates if toolbar is enabled or disabled
	private boolean propertyGroupEnabled = false; // Indicates if property group is enabled, used only on changing language
	private ToolBarOption toolBarOption;
	private int actualView;
	private HashMap<String, ToolBarOption> viewValues;
	
	/**
	 * Folder listener
	 */
	ClickHandler createFolderHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.createFolderOption) {
				executeFolderDirectory();
			}
		}
	};
	
	/**
	 * Execute create folder
	 */
	public void executeFolderDirectory() {
		Main.get().activeFolderTree.addTmpFolderCreate();
	}
	
	/**
	 * Find folder Handler
	 */
	ClickHandler findFolderHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.findFolderOption) {
				Main.get().findFolderSelectPopup.show();
			}
		}
	};
	
	/**
	 * Lock Handler
	 */
	ClickHandler lockHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.lockOption) {
				executeLock();
			}
		}
	};
	
	/**
	 * Execute unlock
	 */
	public void executeLock() {
		Main.get().mainPanel.browser.fileBrowser.lock();
	}
	
	/**
	 * Unlock Handler
	 */
	ClickHandler unLockHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.unLockOption) {
				executeUnlock();
			}
		}
	};
	
	/**
	 * Execute lock
	 */
	public void executeUnlock() {
		Main.get().mainPanel.browser.fileBrowser.unlock();
	}
	
	/**
	 * Add document Handler
	 */
	ClickHandler addDocumentHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.addDocumentOption) {
				executeAddDocument();
			}
		}
	};
	
	/**
	 * Execute adds documents
	 */
	public void executeAddDocument() {
		Main.get().fileUpload.setPath((String) Main.get().activeFolderTree.getActualPath());
		Main.get().fileUpload.setAction(FancyFileUpload.ACTION_INSERT);
		Main.get().fileUpload.showPopup(true,true);
	}
	
	/**
	 * Delete Handler
	 */
	ClickHandler deleteHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.deleteOption) {
				executeDelete();
			}
		}
	};
	
	/**
	 * Executes delete option
	 */
	public void executeDelete() {
		if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
			Main.get().mainPanel.browser.fileBrowser.confirmDelete();
		} else if (Main.get().activeFolderTree.isPanelSelected()) {
			Main.get().activeFolderTree.confirmDelete();
		}
	}
	
	/**
	 * Executes delete option
	 */
	public void executeCopy() {
		if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
			Main.get().mainPanel.browser.fileBrowser.copy();
		} else if (Main.get().activeFolderTree.isPanelSelected()) {
			Main.get().activeFolderTree.copy();
		}
	}
	
	/**
	 * Executes move option
	 */
	public void executeMove() {
		if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
			Main.get().mainPanel.browser.fileBrowser.move();
		} else if (Main.get().activeFolderTree.isPanelSelected()) {
			Main.get().activeFolderTree.move();
		}
	}
	
	/**
	 * Executes rename option
	 */
	public void executeRename() {
		if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
			Main.get().mainPanel.browser.fileBrowser.rename();
		} else if (Main.get().activeFolderTree.isPanelSelected()) {
			Main.get().activeFolderTree.rename();
		}
	}
	
	/**
	 * Edit Handler
	 */
	ClickHandler checkoutHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.checkoutOption){
				executeCheckout();
			}
		}
	};
	
	/**
	 * Execute check out
	 */
	public void executeCheckout() {
		Main.get().mainPanel.browser.fileBrowser.checkout();
	}
	
	/**
	 * Checkin Handler
	 */
	ClickHandler checkinHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.checkinOption) {
				exectuteCheckin();
			}
		}
	};
	
	/**
	 * Execute checkin
	 */
	public void exectuteCheckin() {
		Main.get().fileUpload.setPath(Main.get().mainPanel.browser.fileBrowser.getPath());
		Main.get().fileUpload.setAction(FancyFileUpload.ACTION_UPDATE);
		Main.get().fileUpload.showPopup(false,false);
	}
	
	/**
	 * Checkout cancel Handler
	 */
	ClickHandler cancelCheckoutHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.cancelCheckoutOption) {
				executeCancelCheckout();
			}
		}
	};
	
	/**
	 * Cancel the check out
	 */
	public void executeCancelCheckout() {
		Main.get().mainPanel.browser.fileBrowser.cancelCheckout();
	}
	
	/**
	 * Download Handler
	 */
	ClickHandler downloadHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.downloadOption) {
				executeDownload();
			}
		}
	};

	/**
	 * Download as PDF Handler
	 */
	ClickHandler downloadPdfHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.downloadPdfOption) {
				executeDownloadPdf();
			}
		}
	};
	
	/**
	 * Download document
	 */
	public void executeDownload() {
		Main.get().mainPanel.browser.fileBrowser.table.downloadDocument(false);
	}

	/**
	 * Download document as PDF
	 */
	public void executeDownloadPdf() {
		Main.get().mainPanel.browser.fileBrowser.table.downloadDocumentPdf();
	}

	/**
	 * Add property group Handler
	 */
	ClickHandler addPropertyGroupHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.addPropertyGroupOption) {
				executeAddPropertyGroup();
			}
		}
	};
	
	/**
	 * Execute add property group
	 */
	public void executeAddPropertyGroup(){
		Main.get().groupPopup.show();
	}
	
	/**
	 * Add workflowgroup
	 */
	public void executeAddWorkflow(){
		Main.get().workflowPopup.show();
	}
	
	/**
	 * Remove property group Handler
	 */
	ClickHandler removePropertyGroupHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.removePropertyGroupOption && toolBarOption.firedRemovePropertyGroupOption) {
				executeRemovePropertyGroup();
			}
		}
	};
	
	/**
	 * Add workflow Handler
	 */
	ClickHandler addWorkflowHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.workflowOption) {
				executeAddWorkflow();
			}
		}
	};
	
	/**
	 * Execute remove property group
	 */
	public void executeRemovePropertyGroup() {
		Main.get().confirmPopup.setConfirm(ConfirmPopup.CONFIRM_DELETE_DOCUMENT_PROPERTY_GROUP);
		Main.get().confirmPopup.show();
	}
	
	/**
	 * Add subscription
	 */
	ClickHandler addSubscriptionHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.addSubscription) {
				executeAddSubscription();
			}
		}
	};
	
	/**
	 * Execute add subscription
	 */
	public void executeAddSubscription(){
		if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
			Main.get().mainPanel.browser.fileBrowser.addSubscription();
		} else if (Main.get().activeFolderTree.isPanelSelected()) {
			Main.get().activeFolderTree.addSubscription();
		}
	}
	
	/**
	 * Remove subscription
	 */
	ClickHandler removeSubscriptionHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.removeSubscription) {
				executeRemoveSubscription();
			}
		}
	};
	
	/**
	 * Execute remove property group
	 */
	public void executeRemoveSubscription(){
		if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
			Main.get().mainPanel.browser.fileBrowser.removeSubscription();
		} else if (Main.get().activeFolderTree.isPanelSelected()) {
			Main.get().activeFolderTree.removeSubscription();
		}
	}
	
	/**
	 * Arrow rotate clock wise Handler
	 */
	ClickHandler arrowRefreshHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.refreshOption) {
				executeRefresh();
			}
		}
	};
	
	/**
	 * Scanner Handler
	 */
	ClickHandler scannerHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.scannerOption ) {
				Window.alert("aqui");
				//runScannerApplet("1.6",Main.get().activeFolderTree.getActualPath(),Main.get().workspaceUserProperties.getWorkspace().getToken());
				Window.alert("disparat");
			}
		}
	};
	
	/**
	 * Refreshing workspace
	 */
	public void executeRefresh() {
		switch (Main.get().mainPanel.topPanel.tabWorkspace.getSelectedWorkspace()) {
			case ExtendedDockPanel.DESKTOP :
				int actualView = Main.get().mainPanel.navigator.stackPanel.getStackIndex();
				switch (actualView){
					case PanelDefinition.NAVIGATOR_TAXONOMY:
					case PanelDefinition.NAVIGATOR_CATEGORIES:
					case PanelDefinition.NAVIGATOR_THESAURUS:
					case PanelDefinition.NAVIGATOR_TEMPLATES:
					case PanelDefinition.NAVIGATOR_PERSONAL:
					case PanelDefinition.NAVIGATOR_MAIL:
						Main.get().activeFolderTree.refresh(false);
						break;
						
					case PanelDefinition.NAVIGATOR_TRASH:
						Main.get().activeFolderTree.refresh(false);
						break;
				}
				break;
				
			case ExtendedDockPanel.SEARCH :
				break;
				
			case ExtendedDockPanel.DASHBOARD :
				Main.get().mainPanel.dashboard.refreshAll();
				break;
		}
	}
	
	/**
	 * Arrow rotate clock wise Handler
	 */
	ClickHandler arrowHomeHandler = new ClickHandler() { 
		@Override
		public void onClick(ClickEvent event) {
			if (toolBarOption.homeOption) {
				executeGoToUserHome();
			}
		}
	};
	
	/**
	 * Goes home
	 */
	public void executeGoToUserHome() {		
		// First must validate path is correct
		if (Main.get().userHome!=null && !Main.get().userHome.getPath().equals("")) {
			if (Main.get().userHome.getType().equals(Bookmark.BOOKMARK_DOCUMENT)) {
				ServiceDefTarget endPoint = (ServiceDefTarget) documentService;
				endPoint.setServiceEntryPoint(Config.OKMDocumentService);
				documentService.isValid( Main.get().userHome.getPath() ,callbackIsValidDocument);
			} else if (Main.get().userHome.getType().equals(Bookmark.BOOKMARK_FOLDER)) {
				ServiceDefTarget endPoint = (ServiceDefTarget) folderService;
				endPoint.setServiceEntryPoint(Config.OKMFolderService);	
				folderService.isValid(Main.get().userHome.getPath(), callbackIsValidFolder);
			}
		}
	}
	
	/**
	 * Execute add subscription
	 */
	public void executeExport(){
		if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
			Main.get().mainPanel.browser.fileBrowser.exportFolderToFile();
		} else if (Main.get().activeFolderTree.isPanelSelected()) {
			Main.get().activeFolderTree.exportFolderToFile();
		}
	}

	/**
	 * Gets the HTML space code
	 * 
	 * @return Space tool bar code
	 */
	private HTML space() {
		HTML space = new HTML(" ");
		space.setStyleName("okm-ToolBar-space");
		return space;
	}
	
	/**
	 * Gets the HTML separator code
	 * 
	 * @return Separator tool bar
	 */
	private HTML separator() {
		HTML space = new HTML(Util.imageHTML("img/icon/toolbar/separator.gif"));
		return space;
	}

	/**
	 * Tool Bar
	 */
	public ToolBar() {
		actualView = PanelDefinition.NAVIGATOR_TAXONOMY;
		viewValues = new HashMap<String, ToolBarOption>();
		toolBarOption = getDefaultRootToolBar();
		
		createFolder = new HTML(Util.imageHTML("img/icon/actions/add_folder.gif",Main.i18n("tree.menu.directory.create")));
		findFolder = new HTML(Util.imageHTML("img/icon/actions/folder_find.gif",Main.i18n("tree.menu.directory.find.folder")));
		lock = new HTML(Util.imageHTML("img/icon/actions/lock_disabled.gif",Main.i18n("general.menu.file.lock")));
		unLock = new HTML(Util.imageHTML("img/icon/actions/unlock_disabled.gif",Main.i18n("general.menu.file.unlock")));
		addDocument = new HTML(Util.imageHTML("img/icon/actions/add_document.gif",Main.i18n("general.menu.file.add.document")));
		delete = new HTML(Util.imageHTML("img/icon/actions/delete_disabled.gif",Main.i18n("general.menu.file.delete")));
		edit = new HTML(Util.imageHTML("img/icon/actions/checkout_disabled.gif",Main.i18n("general.menu.file.checkout")));
		checkin = new HTML(Util.imageHTML("img/icon/actions/checkin_disabled.gif",Main.i18n("general.menu.file.checkin")));
		cancelCheckout  = new HTML(Util.imageHTML("img/icon/actions/cancel_checkout_disabled.gif",Main.i18n("general.menu.file.cancel.checkout")));
		download  = new HTML(Util.imageHTML("img/icon/actions/download_disabled.gif",Main.i18n("general.menu.file.download.document")));
		downloadPdf  = new HTML(Util.imageHTML("img/icon/actions/download_pdf_disabled.gif",Main.i18n("general.menu.file.download.document.pdf")));
		addPropertyGroup = new HTML(Util.imageHTML("img/icon/actions/add_property_group_disabled.gif",Main.i18n("filebrowser.menu.add.property.group")));
		removePropertyGroup = new HTML(Util.imageHTML("img/icon/actions/remove_property_group_disabled.gif",Main.i18n("filebrowser.menu.remove.property.group")));
		startWorkflow = new HTML(Util.imageHTML("img/icon/actions/start_workflow_disabled.gif",Main.i18n("filebrowser.menu.start.workflow")));
		addSubscription = new HTML(Util.imageHTML("img/icon/actions/add_subscription_disabled.gif",Main.i18n("filebrowser.menu.add.subscription")));
		removeSubscription = new HTML(Util.imageHTML("img/icon/actions/remove_subscription_disabled.gif",Main.i18n("filebrowser.menu.remove.subscription")));
		home  = new HTML(Util.imageHTML("img/icon/actions/bookmark_go.gif",Main.i18n("general.menu.bookmark.home"))); 
		refresh  = new HTML(Util.imageHTML("img/icon/actions/refresh.gif",Main.i18n("general.menu.file.refresh")));
		scanner  = new HTML(Util.imageHTML("img/icon/actions/scanner.gif",Main.i18n("general.menu.file.scanner")));
		
		createFolder.addClickHandler(createFolderHandler);
		findFolder.addClickHandler(findFolderHandler);
		lock.addClickHandler(lockHandler);
		unLock.addClickHandler(unLockHandler);
		addDocument.addClickHandler(addDocumentHandler);
		delete.addClickHandler(deleteHandler);
		edit.addClickHandler(checkoutHandler);
		checkin.addClickHandler(checkinHandler);
		cancelCheckout.addClickHandler(cancelCheckoutHandler);
		download.addClickHandler(downloadHandler);
		downloadPdf.addClickHandler(downloadPdfHandler);
		addPropertyGroup.addClickHandler(addPropertyGroupHandler);
		removePropertyGroup.addClickHandler(removePropertyGroupHandler);
		startWorkflow.addClickHandler(addWorkflowHandler);
		addSubscription.addClickHandler(addSubscriptionHandler);
		removeSubscription.addClickHandler(removeSubscriptionHandler);
		home.addClickHandler(arrowHomeHandler);
		refresh.addClickHandler(arrowRefreshHandler);
		scanner.addClickHandler(scannerHandler);
		
		MouseOverHandler mouseOverHandler = new MouseOverHandler(){
			@Override
			public void onMouseOver(MouseOverEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.addStyleName("okm-ToolBar-selected");
			}
		};
		
		MouseOutHandler mouseOutHandler = new MouseOutHandler(){
			@Override
			public void onMouseOut(MouseOutEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.removeStyleName("okm-ToolBar-selected");
			}
		};
		
		createFolder.addMouseOverHandler(mouseOverHandler);
		createFolder.addMouseOutHandler(mouseOutHandler);
		findFolder.addMouseOverHandler(mouseOverHandler);
		findFolder.addMouseOutHandler(mouseOutHandler);
		lock.addMouseOverHandler(mouseOverHandler);
		lock.addMouseOutHandler(mouseOutHandler);
		unLock.addMouseOverHandler(mouseOverHandler);
		unLock.addMouseOutHandler(mouseOutHandler);
		addDocument.addMouseOverHandler(mouseOverHandler);
		addDocument.addMouseOutHandler(mouseOutHandler);
		delete.addMouseOverHandler(mouseOverHandler);
		delete.addMouseOutHandler(mouseOutHandler);
		edit.addMouseOverHandler(mouseOverHandler);
		edit.addMouseOutHandler(mouseOutHandler);
		checkin.addMouseOverHandler(mouseOverHandler);
		checkin.addMouseOutHandler(mouseOutHandler);
		cancelCheckout.addMouseOverHandler(mouseOverHandler);
		cancelCheckout.addMouseOutHandler(mouseOutHandler);
		download.addMouseOverHandler(mouseOverHandler);
		download.addMouseOutHandler(mouseOutHandler);
		downloadPdf.addMouseOverHandler(mouseOverHandler);
		downloadPdf.addMouseOutHandler(mouseOutHandler);
		addPropertyGroup.addMouseOverHandler(mouseOverHandler);
		addPropertyGroup.addMouseOutHandler(mouseOutHandler);
		removePropertyGroup.addMouseOverHandler(mouseOverHandler);
		removePropertyGroup.addMouseOutHandler(mouseOutHandler);
		startWorkflow.addMouseOverHandler(mouseOverHandler);
		startWorkflow.addMouseOutHandler(mouseOutHandler);
		addSubscription.addMouseOverHandler(mouseOverHandler);
		addSubscription.addMouseOutHandler(mouseOutHandler);
		removeSubscription.addMouseOverHandler(mouseOverHandler);
		removeSubscription.addMouseOutHandler(mouseOutHandler);
		home.addMouseOverHandler(mouseOverHandler);
		home.addMouseOutHandler(mouseOutHandler);
		refresh.addMouseOverHandler(mouseOverHandler);
		refresh.addMouseOutHandler(mouseOutHandler);
		scanner.addMouseOverHandler(mouseOverHandler);
		scanner.addMouseOutHandler(mouseOutHandler);
		
		createFolder.setStyleName("okm-ToolBar-button");
		findFolder.setStyleName("okm-ToolBar-button");
		lock.setStyleName("okm-ToolBar-button");
		unLock.setStyleName("okm-ToolBar-button");
		addDocument.setStyleName("okm-ToolBar-button");
		delete.setStyleName("okm-ToolBar-button-disabled");
		edit.setStyleName("okm-ToolBar-button-disabled");
		checkin.setStyleName("okm-ToolBar-button-disabled");
		cancelCheckout.setStyleName("okm-ToolBar-button-disabled");
		download.setStyleName("okm-ToolBar-button-disabled");
		downloadPdf.setStyleName("okm-ToolBar-button-disabled");
		addPropertyGroup.setStyleName("okm-ToolBar-button-disabled");
		removePropertyGroup.setStyleName("okm-ToolBar-button-disabled");
		startWorkflow.setStyleName("okm-ToolBar-button-disabled");
		addSubscription.setStyleName("okm-ToolBar-button-disabled");
		removeSubscription.setStyleName("okm-ToolBar-button-disabled");
		home.setStyleName("okm-ToolBar-button-disabled");
		refresh.setStyleName("okm-ToolBar-button-disabled");
		scanner.setStyleName("okm-ToolBar-button-disabled");
		
		panel = new HorizontalPanel();
		panel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		panel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		panel.addStyleName("okm-ToolBar");
		panel.add(space());
		panel.add(createFolder);
		panel.add(space());
		panel.add(findFolder);
		panel.add(space());
		panel.add(download);
		panel.add(space());
		panel.add(downloadPdf);
		panel.add(space());
		panel.add(separator());
		panel.add(lock);
		panel.add(space());
		panel.add(unLock);
		panel.add(separator());
		panel.add(addDocument);
		panel.add(space());
		panel.add(edit);
		panel.add(space());
		panel.add(checkin);
		panel.add(space());
		panel.add(cancelCheckout);
		panel.add(space());
		panel.add(delete);
		panel.add(separator());
		panel.add(addPropertyGroup);
		panel.add(space());
		panel.add(removePropertyGroup);
		panel.add(separator());
		panel.add(startWorkflow);
		panel.add(separator());
		panel.add(addSubscription);
		panel.add(space());
		panel.add(removeSubscription);
		panel.add(separator());
		panel.add(refresh);
		panel.add(home);
		panel.add(separator());
		panel.add(scanner);
		
		initWidget(panel);
	}
	
	/**
	 * Checks permissions associated to folder and tool buttom enabled actions
	 * 
	 * @param folder The folder
	 * @param folderParent the folder parent
	 * @param origin The Origin panel 
	 */
	public void checkToolButtomPermissions(GWTFolder folder, GWTFolder folderParent, int originPanel) {
		// Only if toolbar is enabled must change tools icons values
		if (isEnabled()) {			
			disableDownload();
			disableDownloadPdf();
			disableSendDocumentLink();
			disableCheckout();
			disableLock();
			disableCheckin();
			disableCancelCheckout();
			disableUnlock();
			disableAddPropertyGroup();
			disableRemovePropertyGroup();
			disableWorkflow();
			disableFiredRemovePropertyGroup();
			disableRename();
			disableCopy();
			disableMove();
			disableAddNote();
			disableScanner();
			
			// On folder parent don't enables subscription
			if (Main.get().taxonomyRootFolder.getPath().equals(folder.getPath()) || 
				Main.get().thesaurusRootFolder.getPath().equals(folder.getPath()) ||
				Main.get().templatesRootFolder.getPath().equals(folder.getPath()) ||
				Main.get().personalRootFolder.getPath().equals(folder.getPath()) || 
				Main.get().trashRootFolder.getPath().equals(folder.getPath()) ||
				Main.get().mailRootFolder.getPath().equals(folder.getPath())) {
				disableAddSubscription();
				disableRemoveSubscription();
				disableExport();
				
			} else if (folder.isSubscribed()) {
				disableAddSubscription();
				enableRemoveSubscription();
				
			} else {
				enableAddSubscription();
				disableRemoveSubscription();
			}
			
			if ( (folder.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE) {
				if (originPanel != FILE_BROWSER) {
					enableAddDocument();
					enableCreateDirectory();
				}
				// Evaluates special case root node that must not be deleted;
				if (Main.get().taxonomyRootFolder.getPath().equals(folder.getPath()) || 
					Main.get().thesaurusRootFolder.getPath().equals(folder.getPath()) ||
					Main.get().templatesRootFolder.getPath().equals(folder.getPath()) ||
					Main.get().personalRootFolder.getPath().equals(folder.getPath()) || 
					Main.get().trashRootFolder.getPath().equals(folder.getPath()) ||
					Main.get().mailRootFolder.getPath().equals(folder.getPath())) {
					disableDelete();
				} else if((folderParent.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE){
					enableDelete();
					enableRename(); 
					enableCopy();
					enableMove();
					enableExport();
				} else {
					disableDelete();
				}
				
				// Enable scanner button
				if (Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TAXONOMY ||
					Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TEMPLATES ||
					Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_PERSONAL || 
					Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TRASH ) {
					enableScanner();
				}
				
			} else {
				if (originPanel != FILE_BROWSER) {
					disableCreateDirectory();
					disableAddDocument();
				}
				disableDelete();
			}
			
			// Except taxonomy and thesaurus stack panels always disabling 
			if (Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TEMPLATES ||
				Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_PERSONAL || 
				Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TRASH || 
				Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_MAIL) {
				disableAddPropertyGroup();
				disableRemovePropertyGroup();
				disableFiredRemovePropertyGroup();
				disableAddSubscription();
				disableRemoveSubscription();
			}
			
			// Disables add document, delete and create directory from thesaurus view
			if (Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_THESAURUS || 
				Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_CATEGORIES ) {
				disableAddDocument();
				disableAddSubscription();
				if (Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_THESAURUS) {
					disableCreateDirectory();
					disableDelete();
				}
			}
			
			// Enables find folder in Desktop view 
			if (Main.get().mainPanel.topPanel.tabWorkspace.getSelectedWorkspace()==ExtendedDockPanel.DESKTOP){
				enableFindFolder();
			} else {
				disableFindFolder();
			}
			
			// The remove property group is special case depends on tab property enabled, with this call we force to set false
			evaluateRemoveGroupProperty(false);
			
			// Sets the permission to main menu
			Main.get().mainPanel.topPanel.mainMenu.setOptions(toolBarOption);
		}
	}
	
	/**
	 * Checks permissions associated to document and tool buttom enabled actions
	 * 
	 * @param doc The document
	 */
	public void checkToolButtomPermissions(GWTDocument doc, GWTFolder folder) {
		// Only if toolbar is enabled must change tools icons values
		if (isEnabled()) {
			boolean disable = false;
			String user = Main.get().workspaceUserProperties.getUser();
			
			enableDownload();
			disableRename(); 
			disableCopy();
			disableMove();
			disableExport();
			disableAddNote();
			disableScanner();

			if (doc.isConvertibleToPdf()) {
				enableDownloadPdf();
			} else {
				disableDownloadPdf();
			}
			
			if (doc.isSubscribed()) {
				disableAddSubscription();
				enableRemoveSubscription();
			} else {
				enableAddSubscription();
				disableRemoveSubscription();
			} 
			
			if ((doc.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE) {
				if (!doc.isCheckedOut() && !doc.isLocked()) {
					enableCheckout();
					enableLock();
					disableCheckin();
					disableCancelCheckout();
					disableUnlock();
					enableAddNote();
					
					// In thesaurus and categories view must not evaluate write folder permissions
					if ((folder.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE ||
						 Main.get().mainPanel.navigator.getStackIndex()!= PanelDefinition.NAVIGATOR_THESAURUS ||
						 Main.get().mainPanel.navigator.getStackIndex()!= PanelDefinition.NAVIGATOR_CATEGORIES) {
						enableDelete();
						enableRename();
						enableCopy();
						enableMove();
						enableRemovePropertyGroup(); // Always enable it ( not controls button, only boolean value )
						enableWorkflow();
						if (Main.get().mainPanel.navigator.getStackIndex()!= PanelDefinition.NAVIGATOR_TEMPLATES &&
							Main.get().mainPanel.navigator.getStackIndex()!= PanelDefinition.NAVIGATOR_CATEGORIES &&
							Main.get().mainPanel.navigator.getStackIndex()!= PanelDefinition.NAVIGATOR_THESAURUS &&
							Main.get().mainPanel.navigator.getStackIndex()!= PanelDefinition.NAVIGATOR_PERSONAL &&
							Main.get().mainPanel.navigator.getStackIndex()!= PanelDefinition.NAVIGATOR_TRASH && 
							Main.get().mainPanel.navigator.getStackIndex()!= PanelDefinition.NAVIGATOR_MAIL) {
							getAllGroups(); // Evaluates enable or disable property group buttons
						}
						// Enable scanner button
						if (Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TAXONOMY ||
							Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TEMPLATES ||
							Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_PERSONAL || 
							Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TRASH ) {
							enableScanner();
						}
					} else {
						disableDelete();
						disableAddPropertyGroup();
						disableRemovePropertyGroup();
					}
				} else {
					if (doc.isCheckedOut()) {
						if (doc.getLockInfo().getOwner().equals(user)) {
							enableCheckin();
							enableCancelCheckout();
							disableCheckout();
							disableLock();
							disableUnlock();
							disableDelete();
							enableAddPropertyGroup();
							enableRemovePropertyGroup();
							enableAddNote();
						} else {
							disable = true;
						}
					} else {
						if (doc.getLockInfo().getOwner().equals(user)) {
							enableUnlock();
							disableCheckin();
							disableCancelCheckout();
							disableCheckout();
							disableLock();
							disableDelete();
							enableAddPropertyGroup();
							enableRemovePropertyGroup();
							enableAddNote();
						} else {
							disable = true;
						}
					}
				}
			} else {
				disable = true;
			}
			
			if (disable) {
				disableLock();
				disableUnlock();
				disableCheckout();
				disableCheckin();
				disableCancelCheckout();
				disableDelete();
				disableAddPropertyGroup();
				disableRemovePropertyGroup();
				disableWorkflow();
			} 
			
			// Only on taxonomy, categories, and thesaurus enables to send document link by mail 
			if (Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TAXONOMY ||
				Main.get().mainPanel.navigator.getStackIndex()!= PanelDefinition.NAVIGATOR_THESAURUS ||
				Main.get().mainPanel.navigator.getStackIndex()!= PanelDefinition.NAVIGATOR_CATEGORIES) {
				enableSendDocumentLink();
			} else {
				disableSendDocumentLink();
			}
			
			// Excepts on taxonomy, categories and thesaurus panel always disabling 
			if (Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TEMPLATES ||
				Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_PERSONAL || 
				Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TRASH || 
				Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_MAIL) {
				disableAddPropertyGroup();
				disableRemovePropertyGroup();
				disableFiredRemovePropertyGroup();
				disableAddSubscription();
				disableRemoveSubscription();
			}
			
			// Enables find folder in Desktop view 
			if (Main.get().mainPanel.topPanel.tabWorkspace.getSelectedWorkspace()==ExtendedDockPanel.DESKTOP){
				enableFindFolder();
			} else {
				disableFindFolder();
			}
			
			// Sets the permission to main menu
			Main.get().mainPanel.topPanel.mainMenu.setOptions(toolBarOption);
			
			// Sets the visible values to note tab
			Main.get().mainPanel.browser.tabMultiple.tabDocument.notes.setVisibleAddNote(toolBarOption.addNoteOption);
		}
	}
	
	/**
	 * Checks permissions associated to document and tool buttom enabled actions
	 * 
	 * @param mail The Mail
	 */
	public void checkToolButtomPermissions(GWTMail mail, GWTFolder folder) {
		// Only if toolbar is enabled must change tools icons values
		if (isEnabled()) {
			boolean disable = false;
			
			disableDownload();
			disableRename(); 
			disableCopy();
			disableMove();
			disableExport();
			disableDownloadPdf();
			disableAddSubscription();
			disableRemoveSubscription();
			disableCheckout();
			disableLock();
			disableUnlock();
			disableAddPropertyGroup();
			disableRemovePropertyGroup();
			disableCheckout();
			disableCheckin();
			disableCancelCheckout();
			disableWorkflow();
			disableAddDocument();
			disableAddNote();
			disableScanner();
			
			if ((mail.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE) {

				if ((folder.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE) {
					enableDelete();
					enableRename();
					enableCopy();
					enableMove();
					enableRemovePropertyGroup(); // Always enable it ( not controls button, only boolean value )
					// On mail panel is not able to uploading files
					if (Main.get().mainPanel.navigator.getStackIndex()!= PanelDefinition.NAVIGATOR_MAIL ) {
						enableAddDocument();
					} 
				} else {
					disableDelete();
				}				
			} else {
				disable = true;
			}
			
			if (disable) {
				disableDelete();
			} 
			
			// Onnly on taxonomy enables to send document link by mail 
			if (Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TAXONOMY) {
				enableSendDocumentLink();
			} else {
				disableSendDocumentLink();
			}
			
			// Excepts on taxonomy and thesaurus panel always disabling 
			if (Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TEMPLATES ||
				Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_PERSONAL || 
				Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_TRASH || 
				Main.get().mainPanel.navigator.getStackIndex()== PanelDefinition.NAVIGATOR_MAIL) {
				disableAddPropertyGroup();
				disableRemovePropertyGroup();
				disableFiredRemovePropertyGroup();
				disableAddSubscription();
				disableRemoveSubscription();
			}
			
			// Sets the permission to main menu
			Main.get().mainPanel.topPanel.mainMenu.setOptions(toolBarOption);
		}
	}
	
	/**
	 * Indicates if toolBar is enabled
	 * 
	 * @return The value of enabled / disabled toolbar
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Enables create directory
	 */
	public void enableCreateDirectory() {
		toolBarOption.createFolderOption = true;
		createFolder.setStyleName("okm-ToolBar-button");
		createFolder.setHTML(Util.imageHTML("img/icon/actions/add_folder.gif",Main.i18n("tree.menu.directory.create")));
	}
	
	/**
	 * Disables create directory
	 */
	public void disableCreateDirectory() {
		toolBarOption.createFolderOption = false;
		createFolder.setStyleName("okm-ToolBar-button-disabled");
		createFolder.setHTML(Util.imageHTML("img/icon/actions/add_folder_disabled.gif",Main.i18n("tree.menu.directory.create")));
	}
	
	/**
	 * Enables find folder
	 */
	public void enableFindFolder() {
		toolBarOption.findFolderOption = true;
		findFolder.setStyleName("okm-ToolBar-button");
		findFolder.setHTML(Util.imageHTML("img/icon/actions/folder_find.gif",Main.i18n("tree.menu.directory.find.folder")));
	}
	
	/**
	 * Disables create directory
	 */
	public void disableFindFolder() {
		toolBarOption.findFolderOption = false;
		findFolder.setStyleName("okm-ToolBar-button-disabled");
		findFolder.setHTML(Util.imageHTML("img/icon/actions/folder_find_disabled.gif",Main.i18n("tree.menu.directory.find.folder")));
	}
	
	/**
	 * Enables edit buttom
	 */
	public void enableCheckout() {
		toolBarOption.checkoutOption = true;
		edit.setStyleName("okm-ToolBar-button");
		edit.setHTML(Util.imageHTML("img/icon/actions/checkout.gif",Main.i18n("general.menu.file.checkout")));
	}
	
	/**
	 * Disables edit buttom
	 */
	public void disableCheckout() {
		toolBarOption.checkoutOption = false;
		edit.setStyleName("okm-ToolBar-button-disabled");
		edit.setHTML(Util.imageHTML("img/icon/actions/checkout_disabled.gif",Main.i18n("general.menu.file.checkout")));
	}
	
	/**
	 * Enables checkin buttom
	 */
	public void enableCheckin() {
		toolBarOption.checkinOption = true;
		checkin.setStyleName("okm-ToolBar-button");
		checkin.setHTML(Util.imageHTML("img/icon/actions/checkin.gif",Main.i18n("general.menu.file.checkin")));
	}
	
	/**
	 * Disables checkin buttom
	 */
	public void disableCheckin() {
		toolBarOption.checkinOption = false;
		checkin.setStyleName("okm-ToolBar-button-disabled");
		checkin.setHTML(Util.imageHTML("img/icon/actions/checkin_disabled.gif",Main.i18n("general.menu.file.checkin")));
	}
	
	/**
	 * Enables checkout cancel buttom
	 */
	public void enableCancelCheckout() {
		toolBarOption.cancelCheckoutOption = true;
		cancelCheckout.setStyleName("okm-ToolBar-button");
		cancelCheckout.setHTML(Util.imageHTML("img/icon/actions/cancel_checkout.gif",Main.i18n("general.menu.file.cancel.checkout")));
	}
	
	/**
	 * Disables checkout cancel buttom
	 */
	public void disableCancelCheckout() {
		toolBarOption.cancelCheckoutOption = false;
		cancelCheckout.setStyleName("okm-ToolBar-button-disabled");
		cancelCheckout.setHTML(Util.imageHTML("img/icon/actions/cancel_checkout_disabled.gif",Main.i18n("general.menu.file.cancel.checkout")));
	}
	
	/**
	 * Disables lock buttom
	 */
	public void disableLock() {
		toolBarOption.lockOption = false;
		lock.setStyleName("okm-ToolBar-button-disabled");
		lock.setHTML(Util.imageHTML("img/icon/actions/lock_disabled.gif",Main.i18n("general.menu.file.lock")));
	}
	
	/**
	 * Enables lock buttom
	 */
	public void enableLock() {
		toolBarOption.lockOption = true;
		lock.setStyleName("okm-ToolBar-button");
		lock.setHTML(Util.imageHTML("img/icon/actions/lock.gif",Main.i18n("general.menu.file.lock")));
	}
	
	/**
	 * Disables delete lock buttom
	 */
	public void disableUnlock() {
		toolBarOption.unLockOption = false;
		unLock.setStyleName("okm-ToolBar-button-disabled");
		unLock.setHTML(Util.imageHTML("img/icon/actions/unlock_disabled.gif",Main.i18n("general.menu.file.unlock")));
	}
	
	/**
	 * Enables delete lock buttom
	 */
	public void enableUnlock() {
		toolBarOption.unLockOption = true;
		unLock.setStyleName("okm-ToolBar-button");
		unLock.setHTML(Util.imageHTML("img/icon/actions/unlock.gif",Main.i18n("general.menu.file.unlock")));
	}
	
	/**
	 * Disables download buttom
	 */
	public void disableDownload() {
		toolBarOption.downloadOption = false;
		download.setStyleName("okm-ToolBar-button-disabled");
		download.setHTML(Util.imageHTML("img/icon/actions/download_disabled.gif",Main.i18n("general.menu.file.download.document")));
	}
	
	/**
	 * Enables download buttom
	 */
	public void enableDownload() {
		toolBarOption.downloadOption = true;
		download.setStyleName("okm-ToolBar-button");
		download.setHTML(Util.imageHTML("img/icon/actions/download.gif",Main.i18n("general.menu.file.download.document")));
	}

	/**
	 * Disables download as PDF buttom
	 */
	public void disableDownloadPdf() {
		toolBarOption.downloadPdfOption = false;
		downloadPdf.setStyleName("okm-ToolBar-button-disabled");
		downloadPdf.setHTML(Util.imageHTML("img/icon/actions/download_pdf_disabled.gif",Main.i18n("general.menu.file.download.document.pdf")));
	}
	
	/**
	 * Enables download as PDF buttom
	 */
	public void enableDownloadPdf() {
		toolBarOption.downloadPdfOption = true;
		downloadPdf.setStyleName("okm-ToolBar-button");
		downloadPdf.setHTML(Util.imageHTML("img/icon/actions/download_pdf.gif",Main.i18n("general.menu.file.download.document.pdf")));
	}

	/**
	 * Disables send document link button
	 */
	public void disableSendDocumentLink() {
		toolBarOption.sendDocumentLinkOption = false;
	}
	
	/**
	 * Enables send document link button
	 */
	public void enableSendDocumentLink(){
		toolBarOption.sendDocumentLinkOption = true;
	}
	
	/**
	 * Disables delete buttom
	 */
	public void disableDelete() {
		toolBarOption.deleteOption = false;
		delete.setStyleName("okm-ToolBar-button-disabled");
		delete.setHTML(Util.imageHTML("img/icon/actions/delete_disabled.gif",Main.i18n("general.menu.file.delete")));
	}
	
	/**
	 * Enables delete buttom
	 */
	public void enableDelete() {
		toolBarOption.deleteOption = true;
		delete.setStyleName("okm-ToolBar-button");
		delete.setHTML(Util.imageHTML("img/icon/actions/delete.gif",Main.i18n("general.menu.file.delete")));
	}
	
	/**
	 * Disable arrow rotate clockwise 
	 */
	public void disableRefresh() {
		toolBarOption.refreshOption = false;
		refresh.setStyleName("okm-ToolBar-button-disabled");
		refresh.setHTML(Util.imageHTML("img/icon/actions/refresh_disabled.gif",Main.i18n("general.menu.file.refresh")));
	}
	
	/**
	 * Enables Rotate ClockWise Arrow
	 */
	public void enableRefresh() {
		toolBarOption.refreshOption = true;
		refresh.setStyleName("okm-ToolBar-button");
		refresh.setHTML(Util.imageHTML("img/icon/actions/refresh.gif",Main.i18n("general.menu.file.refresh")));
	}
	
	/**
	 * Disables add document 
	 */
	public void disableAddDocument() {
		toolBarOption.addDocumentOption = false;
		addDocument.setStyleName("okm-ToolBar-button-disabled");
		addDocument.setHTML(Util.imageHTML("img/icon/actions/add_document_disabled.gif",Main.i18n("general.menu.file.add.document")));
	}
	
	/**
	 * Enables add document 
	 */
	public void enableAddDocument() {
		toolBarOption.addDocumentOption = true;
		addDocument.setStyleName("okm-ToolBar-button");
		addDocument.setHTML(Util.imageHTML("img/icon/actions/add_document.gif",Main.i18n("general.menu.file.add.document")));
	}
	
	/**
	 * Disables add property group 
	 */
	public void disableAddPropertyGroup() {
		toolBarOption.addPropertyGroupOption = false;
		addPropertyGroup.setStyleName("okm-ToolBar-button-disabled");
		addPropertyGroup.setHTML(Util.imageHTML("img/icon/actions/add_property_group_disabled.gif",Main.i18n("filebrowser.menu.add.property.group")));
	}
	
	/**
	 * Enables add property group 
	 */
	public void enableAddPropertyGroup() {
		toolBarOption.addPropertyGroupOption = true;
		addPropertyGroup.setStyleName("okm-ToolBar-button");
		addPropertyGroup.setHTML(Util.imageHTML("img/icon/actions/add_property_group.gif",Main.i18n("filebrowser.menu.add.property.group")));
	}
	
	/**
	 * Disables add subscription 
	 */
	public void disableAddSubscription() {
		toolBarOption.addSubscription = false;
		addSubscription.setStyleName("okm-ToolBar-button-disabled");
		addSubscription.setHTML(Util.imageHTML("img/icon/actions/add_subscription_disabled.gif",Main.i18n("filebrowser.menu.add.subscription")));
	}
	
	/**
	 * Enables add subscription 
	 */
	public void enableAddSubscription() {
		toolBarOption.addSubscription = true;
		addSubscription.setStyleName("okm-ToolBar-button");
		addSubscription.setHTML(Util.imageHTML("img/icon/actions/add_subscription.gif",Main.i18n("filebrowser.menu.add.subscription")));
	}
	
	/**
	 * Disables remove subscription 
	 */
	public void disableRemoveSubscription() {
		toolBarOption.removeSubscription = false;
		removeSubscription.setStyleName("okm-ToolBar-button-disabled");
		removeSubscription.setHTML(Util.imageHTML("img/icon/actions/remove_subscription_disabled.gif",Main.i18n("filebrowser.menu.remove.subscription")));
	}
	
	/**
	 * Enables remove subscription 
	 */
	public void enableRemoveSubscription() {
		toolBarOption.removeSubscription = true;
		removeSubscription.setStyleName("okm-ToolBar-button");
		removeSubscription.setHTML(Util.imageHTML("img/icon/actions/remove_subscription.gif",Main.i18n("filebrowser.menu.remove.subscription")));
	}
	
	/**
	 * Disables remove subscription 
	 */
	public void disableHome() {
		toolBarOption.homeOption = false;
		home.setStyleName("okm-ToolBar-button-disabled");
		home.setHTML(Util.imageHTML("img/icon/actions/bookmark_go_disabled.gif",Main.i18n("general.menu.bookmark.home")));
	}
	
	/**
	 * Enables remove subscription 
	 */
	public void enableHome() {
		toolBarOption.homeOption = true;
		home.setStyleName("okm-ToolBar-button");
		home.setHTML(Util.imageHTML("img/icon/actions/bookmark_go.gif",Main.i18n("general.menu.bookmark.home")));
	}
	
	/**
	 * Disables remove property group 
	 */
	public void disableRemovePropertyGroup() {
		toolBarOption.removePropertyGroupOption = false;
	}
	
	/**
	 * Enables remove property group 
	 */
	public void enableRemovePropertyGroup() {
		toolBarOption.removePropertyGroupOption = true;
	}
	
	/**
	 * Disables workflow
	 */
	public void disableWorkflow() {
		toolBarOption.workflowOption = false;
		startWorkflow.setStyleName("okm-ToolBar-button-disabled");
		startWorkflow.setHTML(Util.imageHTML("img/icon/actions/start_workflow_disabled.gif",Main.i18n("filebrowser.menu.start.workflow")));
	}
	
	/**
	 * Enables workflow 
	 */
	public void enableWorkflow() {
		toolBarOption.workflowOption = true;
		startWorkflow.setStyleName("okm-ToolBar-button");
		startWorkflow.setHTML(Util.imageHTML("img/icon/actions/start_workflow.gif",Main.i18n("filebrowser.menu.start.workflow")));
	}
	
	/**
	 * Disables scanner
	 */
	public void disableScanner() {
		toolBarOption.scannerOption = false;
		scanner.setStyleName("okm-ToolBar-button-disabled");
		scanner.setHTML(Util.imageHTML("img/icon/actions/scanner_disabled.gif",Main.i18n("general.menu.file.scanner")));
	}
	
	/**
	 * Enables scanner 
	 */
	public void enableScanner() {
		toolBarOption.scannerOption = true;
		scanner.setStyleName("okm-ToolBar-button");
		scanner.setHTML(Util.imageHTML("img/icon/actions/scanner.gif",Main.i18n("general.menu.file.scanner")));
	}
	
	/**
	 * Disables fired property group 
	 */
	public void disableFiredRemovePropertyGroup() {
		toolBarOption.firedRemovePropertyGroupOption = false;
	}
	
	/**
	 * Enables fired property group 
	 */
	public void enableFiredRemovePropertyGroup() {
		toolBarOption.firedRemovePropertyGroupOption = true;
	}
	
	/**
	 * Enables rename
	 */
	public void enableRename() {
		toolBarOption.renameOption = true;
	}
	
	/**
	 * Disable rename
	 */
	public void disableRename() {
		toolBarOption.renameOption = false;
	}
	
	/**
	 * Enables copy
	 */
	public void enableCopy() {
		toolBarOption.copyOption = true;
	}
	
	/**
	 * Disable copy
	 */
	public void disableCopy() {
		toolBarOption.copyOption = false;
	}
	
	/**
	 * Enables move
	 */
	public void enableMove() {
		toolBarOption.moveOption = true;
	}
	
	/**
	 * Disable move
	 */
	public void disableMove() {
		toolBarOption.moveOption = false;
	}
	
	/**
	 * Enables export
	 */
	public void enableExport() {
		toolBarOption.exportOption = true;
	}
	
	/**
	 * Disable export
	 */
	public void disableExport() {
		toolBarOption.exportOption = false;
	}
	
	/**
	 * Enables add note
	 */
	public void enableAddNote() {
		toolBarOption.addNoteOption = true;
	}
	
	/**
	 * Disable add note
	 */
	public void disableAddNote() {
		toolBarOption.addNoteOption = false;
	}

	/**
	 * Gets the defatul Tool Bar object values for root
	 * 
	 * @return The default toolBarOption for init root
	 */
	public ToolBarOption getDefaultRootToolBar() {
		ToolBarOption tmpToolBarOption = new ToolBarOption();
		tmpToolBarOption.createFolderOption				= true;
		tmpToolBarOption.findFolderOption				= true;
		tmpToolBarOption.addDocumentOption 				= true;
		tmpToolBarOption.checkoutOption 				= false;
		tmpToolBarOption.checkinOption 					= false;
		tmpToolBarOption.cancelCheckoutOption 			= false;
		tmpToolBarOption.lockOption						= false;
		tmpToolBarOption.unLockOption 					= false;
		tmpToolBarOption.downloadOption					= false;
		tmpToolBarOption.downloadPdfOption				= false;
		tmpToolBarOption.deleteOption					= false;
		tmpToolBarOption.addPropertyGroupOption 		= false;
		tmpToolBarOption.removePropertyGroupOption  	= false;
		tmpToolBarOption.addSubscription  				= false;
		tmpToolBarOption.removeSubscription 		 	= false;
		tmpToolBarOption.firedRemovePropertyGroupOption = false;
		tmpToolBarOption.homeOption						= true;
		tmpToolBarOption.refreshOption					= true;
		tmpToolBarOption.renameOption 					= false;
		tmpToolBarOption.copyOption 					= false;
		tmpToolBarOption.moveOption 					= false;
		tmpToolBarOption.exportOption					= false;
		tmpToolBarOption.workflowOption					= false;
		tmpToolBarOption.addNoteOption					= false;
		tmpToolBarOption.scannerOption					= true;
		return tmpToolBarOption;
	}
	
	/**
	 * Gets the defatul Tool Bar object values for categories
	 * 
	 * @return The default toolBarOption for templates
	 */
	public ToolBarOption getDefaultCategoriesToolBar() {
		ToolBarOption tmpToolBarOption = new ToolBarOption();
		tmpToolBarOption.createFolderOption				= true;
		tmpToolBarOption.findFolderOption				= true;
		tmpToolBarOption.addDocumentOption 				= false;
		tmpToolBarOption.checkoutOption 				= false;
		tmpToolBarOption.checkinOption 					= false;
		tmpToolBarOption.cancelCheckoutOption 			= false;
		tmpToolBarOption.lockOption						= false;
		tmpToolBarOption.unLockOption 					= false;
		tmpToolBarOption.downloadOption					= false;
		tmpToolBarOption.downloadPdfOption				= false;
		tmpToolBarOption.deleteOption					= false;
		tmpToolBarOption.addPropertyGroupOption 		= false;
		tmpToolBarOption.removePropertyGroupOption  	= false;
		tmpToolBarOption.addSubscription  				= false;
		tmpToolBarOption.removeSubscription 		 	= false;
		tmpToolBarOption.firedRemovePropertyGroupOption = false;
		tmpToolBarOption.homeOption						= false;
		tmpToolBarOption.refreshOption					= true;
		tmpToolBarOption.renameOption 					= false;
		tmpToolBarOption.copyOption 					= false;
		tmpToolBarOption.moveOption 					= false;
		tmpToolBarOption.exportOption					= false;
		tmpToolBarOption.workflowOption					= false;
		tmpToolBarOption.addNoteOption					= false;
		tmpToolBarOption.scannerOption					= false;
		return tmpToolBarOption;
	}
	
	/**
	 * Gets the defatul Tool Bar object values for thesaurus
	 * 
	 * @return The default toolBarOption for templates
	 */
	public ToolBarOption getDefaultThesaurusToolBar() {
		ToolBarOption tmpToolBarOption = new ToolBarOption();
		tmpToolBarOption.createFolderOption				= false;
		tmpToolBarOption.findFolderOption				= true;
		tmpToolBarOption.addDocumentOption 				= false;
		tmpToolBarOption.checkoutOption 				= false;
		tmpToolBarOption.checkinOption 					= false;
		tmpToolBarOption.cancelCheckoutOption 			= false;
		tmpToolBarOption.lockOption						= false;
		tmpToolBarOption.unLockOption 					= false;
		tmpToolBarOption.downloadOption					= false;
		tmpToolBarOption.downloadPdfOption				= false;
		tmpToolBarOption.deleteOption					= false;
		tmpToolBarOption.addPropertyGroupOption 		= false;
		tmpToolBarOption.removePropertyGroupOption  	= false;
		tmpToolBarOption.addSubscription  				= false;
		tmpToolBarOption.removeSubscription 		 	= false;
		tmpToolBarOption.firedRemovePropertyGroupOption = false;
		tmpToolBarOption.homeOption						= false;
		tmpToolBarOption.refreshOption					= true;
		tmpToolBarOption.renameOption 					= false;
		tmpToolBarOption.copyOption 					= false;
		tmpToolBarOption.moveOption 					= false;
		tmpToolBarOption.exportOption					= false;
		tmpToolBarOption.workflowOption					= false;
		tmpToolBarOption.addNoteOption					= false;
		tmpToolBarOption.scannerOption					= false;
		return tmpToolBarOption;
	}
	
	/**
	 * Gets the defatul Tool Bar object values for trash
	 * 
	 * @return The default toolBarOption for trash
	 */
	public ToolBarOption getDefaultTrashToolBar() {
		ToolBarOption tmpToolBarOption = new ToolBarOption();
		tmpToolBarOption.createFolderOption				= false;
		tmpToolBarOption.findFolderOption				= true;
		tmpToolBarOption.addDocumentOption 				= false;
		tmpToolBarOption.checkoutOption 				= false;
		tmpToolBarOption.checkinOption 					= false;
		tmpToolBarOption.cancelCheckoutOption 			= false;
		tmpToolBarOption.lockOption						= false;
		tmpToolBarOption.unLockOption 					= false;
		tmpToolBarOption.downloadOption					= false;
		tmpToolBarOption.downloadPdfOption				= false;
		tmpToolBarOption.deleteOption					= false;
		tmpToolBarOption.addPropertyGroupOption 		= false;
		tmpToolBarOption.removePropertyGroupOption  	= false;
		tmpToolBarOption.addSubscription  				= false;
		tmpToolBarOption.removeSubscription 		 	= false;
		tmpToolBarOption.firedRemovePropertyGroupOption = false;
		tmpToolBarOption.homeOption						= false;
		tmpToolBarOption.refreshOption					= true;
		tmpToolBarOption.renameOption 					= false;
		tmpToolBarOption.copyOption 					= false;
		tmpToolBarOption.moveOption 					= false;
		tmpToolBarOption.exportOption					= false;
		tmpToolBarOption.workflowOption					= false;
		tmpToolBarOption.addNoteOption					= false;
		tmpToolBarOption.scannerOption					= false;
		return tmpToolBarOption;
	}
	
	/**
	 * Gets the defatul Tool Bar object values for templates
	 * 
	 * @return The default toolBarOption for templates
	 */
	public ToolBarOption getDefaultTemplatesToolBar() {
		ToolBarOption tmpToolBarOption = new ToolBarOption();
		tmpToolBarOption.createFolderOption				= true;
		tmpToolBarOption.findFolderOption				= true;
		tmpToolBarOption.addDocumentOption 				= true;
		tmpToolBarOption.checkoutOption 				= false;
		tmpToolBarOption.checkinOption 					= false;
		tmpToolBarOption.cancelCheckoutOption 			= false;
		tmpToolBarOption.lockOption						= false;
		tmpToolBarOption.unLockOption 					= false;
		tmpToolBarOption.downloadOption					= false;
		tmpToolBarOption.downloadPdfOption				= false;
		tmpToolBarOption.deleteOption					= false;
		tmpToolBarOption.addPropertyGroupOption 		= false;
		tmpToolBarOption.removePropertyGroupOption  	= false;
		tmpToolBarOption.addSubscription  				= false;
		tmpToolBarOption.removeSubscription 		 	= false;
		tmpToolBarOption.firedRemovePropertyGroupOption = false;
		tmpToolBarOption.homeOption						= false;
		tmpToolBarOption.refreshOption					= true;
		tmpToolBarOption.renameOption 					= false;
		tmpToolBarOption.copyOption 					= false;
		tmpToolBarOption.moveOption 					= false;
		tmpToolBarOption.exportOption					= false;
		tmpToolBarOption.workflowOption					= false;
		tmpToolBarOption.addNoteOption					= false;
		tmpToolBarOption.scannerOption					= false;
		return tmpToolBarOption;
	}
	
	/**
	 * Gets the defatul Tool Bar object values for my documents
	 * 
	 * @return The default toolBarOption for templates
	 */
	public ToolBarOption getDefaultMyDocumentsToolBar() {
		ToolBarOption tmpToolBarOption = new ToolBarOption();
		tmpToolBarOption.createFolderOption				= true;
		tmpToolBarOption.findFolderOption				= true;
		tmpToolBarOption.addDocumentOption 				= true;
		tmpToolBarOption.checkoutOption 				= false;
		tmpToolBarOption.checkinOption 					= false;
		tmpToolBarOption.cancelCheckoutOption 			= false;
		tmpToolBarOption.lockOption						= false;
		tmpToolBarOption.unLockOption 					= false;
		tmpToolBarOption.downloadOption					= false;
		tmpToolBarOption.downloadPdfOption				= false;
		tmpToolBarOption.deleteOption					= false;
		tmpToolBarOption.addPropertyGroupOption 		= false;
		tmpToolBarOption.removePropertyGroupOption  	= false;
		tmpToolBarOption.addSubscription  				= false;
		tmpToolBarOption.removeSubscription 		 	= false;
		tmpToolBarOption.firedRemovePropertyGroupOption = false;
		tmpToolBarOption.homeOption						= false;
		tmpToolBarOption.refreshOption					= true;
		tmpToolBarOption.renameOption 					= false;
		tmpToolBarOption.copyOption 					= false;
		tmpToolBarOption.moveOption 					= false;
		tmpToolBarOption.exportOption					= false;
		tmpToolBarOption.workflowOption					= false;
		tmpToolBarOption.addNoteOption					= false;
		tmpToolBarOption.scannerOption					= true;
		return tmpToolBarOption;
	}
	
	/**
	 * Gets the defatul Tool Bar object values for search
	 * 
	 * @return The default toolBarOption for search
	 */
	public ToolBarOption getDefaultSearchToolBar() {
		ToolBarOption tmpToolBarOption = new ToolBarOption();
		tmpToolBarOption.createFolderOption			= false;
		tmpToolBarOption.findFolderOption				= false;
		tmpToolBarOption.addDocumentOption 				= false;
		tmpToolBarOption.checkoutOption 				= false;
		tmpToolBarOption.checkinOption 					= false;
		tmpToolBarOption.cancelCheckoutOption 			= false;
		tmpToolBarOption.lockOption						= false;
		tmpToolBarOption.unLockOption 					= false;
		tmpToolBarOption.downloadOption					= false;
		tmpToolBarOption.downloadPdfOption				= false;
		tmpToolBarOption.deleteOption					= false;
		tmpToolBarOption.addPropertyGroupOption 		= false;
		tmpToolBarOption.removePropertyGroupOption  	= false;
		tmpToolBarOption.addSubscription  				= false;
		tmpToolBarOption.removeSubscription 		 	= false;
		tmpToolBarOption.firedRemovePropertyGroupOption = false;
		tmpToolBarOption.homeOption						= false;
		tmpToolBarOption.refreshOption					= false;
		tmpToolBarOption.renameOption 					= false;
		tmpToolBarOption.copyOption 					= false;
		tmpToolBarOption.moveOption 					= false;
		tmpToolBarOption.exportOption					= false;
		tmpToolBarOption.workflowOption					= false;
		tmpToolBarOption.addNoteOption					= false;
		tmpToolBarOption.scannerOption					= false;
		return tmpToolBarOption;
	}
	
	/**
	 * Gets the defatul Tool Bar object values for dashboard
	 * 
	 * @return The default toolBarOption for search
	 */
	public ToolBarOption getDefaultDashboardToolBar() {
		ToolBarOption tmpToolBarOption = new ToolBarOption();
		tmpToolBarOption.createFolderOption				= false;
		tmpToolBarOption.findFolderOption				= false;
		tmpToolBarOption.addDocumentOption 				= false;
		tmpToolBarOption.checkoutOption 				= false;
		tmpToolBarOption.checkinOption 					= false;
		tmpToolBarOption.cancelCheckoutOption 			= false;
		tmpToolBarOption.lockOption						= false;
		tmpToolBarOption.unLockOption 					= false;
		tmpToolBarOption.downloadOption					= false;
		tmpToolBarOption.downloadPdfOption				= false;
		tmpToolBarOption.deleteOption					= false;
		tmpToolBarOption.addPropertyGroupOption 		= false;
		tmpToolBarOption.removePropertyGroupOption  	= false;
		tmpToolBarOption.addSubscription  				= false;
		tmpToolBarOption.removeSubscription 		 	= false;
		tmpToolBarOption.firedRemovePropertyGroupOption = false;
		tmpToolBarOption.homeOption						= false;
		tmpToolBarOption.refreshOption					= true;
		tmpToolBarOption.renameOption 					= false;
		tmpToolBarOption.copyOption 					= false;
		tmpToolBarOption.moveOption 					= false;
		tmpToolBarOption.exportOption					= false;
		tmpToolBarOption.workflowOption					= false;
		tmpToolBarOption.addNoteOption					= false;
		tmpToolBarOption.scannerOption					= false;
		return tmpToolBarOption;
	}
	
	/**
	 * Gets the defatul Tool Bar object values for administration
	 * 
	 * @return The default toolBarOption for search
	 */
	public ToolBarOption getDefaultAdministrationToolBar() {
		ToolBarOption tmpToolBarOption = new ToolBarOption();
		tmpToolBarOption.createFolderOption				= false;
		tmpToolBarOption.findFolderOption				= false;
		tmpToolBarOption.addDocumentOption 				= false;
		tmpToolBarOption.checkoutOption 				= false;
		tmpToolBarOption.checkinOption 					= false;
		tmpToolBarOption.cancelCheckoutOption 			= false;
		tmpToolBarOption.lockOption						= false;
		tmpToolBarOption.unLockOption 					= false;
		tmpToolBarOption.downloadOption					= false;
		tmpToolBarOption.downloadPdfOption				= false;
		tmpToolBarOption.deleteOption					= false;
		tmpToolBarOption.addPropertyGroupOption 		= false;
		tmpToolBarOption.removePropertyGroupOption  	= false;
		tmpToolBarOption.addSubscription  				= false;
		tmpToolBarOption.removeSubscription 		 	= false;
		tmpToolBarOption.firedRemovePropertyGroupOption = false;
		tmpToolBarOption.homeOption						= false;
		tmpToolBarOption.refreshOption					= false;
		tmpToolBarOption.renameOption 					= false;
		tmpToolBarOption.copyOption 					= false;
		tmpToolBarOption.moveOption 					= false;
		tmpToolBarOption.exportOption					= false;
		tmpToolBarOption.workflowOption					= false;
		tmpToolBarOption.addNoteOption					= false;
		tmpToolBarOption.scannerOption					= false;
		return tmpToolBarOption;
	}
	
	/**
	 * Evalues show Icons based on toolBarOption values
	 * 
	 */
	public void evaluateShowIcons() {
		if (toolBarOption.createFolderOption) {enableCreateDirectory(); } else {disableCreateDirectory(); }
		if (toolBarOption.findFolderOption) {enableFindFolder(); } else {disableFindFolder(); }
		if (toolBarOption.addDocumentOption) {enableAddDocument(); } else {disableAddDocument(); }
		if (toolBarOption.checkoutOption) { enableCheckout(); } else { disableCheckout(); }
		if (toolBarOption.checkinOption) { enableCheckin(); } else { disableCheckin(); }
		if (toolBarOption.cancelCheckoutOption) { enableCancelCheckout(); } else { disableCancelCheckout(); }
		if (toolBarOption.lockOption) { enableLock(); } else { disableLock();}
		if (toolBarOption.unLockOption) { enableUnlock(); } else { disableUnlock(); }
		if (toolBarOption.downloadOption) { enableDownload(); } else { disableDownload(); }
		if (toolBarOption.downloadPdfOption) { enableDownloadPdf(); } else { disableDownloadPdf(); }
		if (toolBarOption.deleteOption) { enableDelete(); } else { disableDelete(); }
		if (toolBarOption.addPropertyGroupOption) { enableAddPropertyGroup(); } else { disableAddPropertyGroup(); }
		
		// Special case removePropertyGroupOption is only evaluated on TabDocument tab changing by evaluateRemoveGroupProperty method
		if (!toolBarOption.removePropertyGroupOption) { // We evaluate for changing panel desktop / search ( only disable option )
			removePropertyGroup.setStyleName("okm-ToolBar-button-disabled");
			removePropertyGroup.setHTML(Util.imageHTML("img/icon/actions/remove_property_group_disabled.gif",Main.i18n("filebrowser.menu.remove.property.group")));
		}
		
		if (toolBarOption.workflowOption) { enableWorkflow(); } else { disableWorkflow();}
		if (toolBarOption.addSubscription) { enableAddSubscription(); } else { disableAddSubscription(); }
		if (toolBarOption.removeSubscription){ enableRemoveSubscription(); } else { disableRemoveSubscription(); }
		if (toolBarOption.homeOption){ enableHome(); } else { disableHome(); }
		if (toolBarOption.refreshOption){ enableRefresh(); } else { disableRefresh(); }
		if (toolBarOption.scannerOption){ enableScanner(); } else { disableScanner(); }
	}
	
	/**
	 * Evaluate the remove group property
	 * 
	 * @param propertyGroupEnabled
	 */
	public void evaluateRemoveGroupProperty(boolean propertyGroupEnabled) {
		// Show or hide removeGroupProperty depends on two cases, the property is enabled by security user and
		// must be one tab group selected
		
		// We save to used on changing language
		this.propertyGroupEnabled = propertyGroupEnabled;
		
		// Sets fired property
		if (propertyGroupEnabled) {
			enableFiredRemovePropertyGroup();
		} else {
			disableFiredRemovePropertyGroup();
		}
		
		// Show or hides button
		if (toolBarOption.removePropertyGroupOption && toolBarOption.firedRemovePropertyGroupOption) {
			removePropertyGroup.setStyleName("okm-ToolBar-button");
			removePropertyGroup.setHTML(Util.imageHTML("img/icon/actions/remove_property_group.gif",Main.i18n("filebrowser.menu.remove.property.group")));
		} else {
			removePropertyGroup.setStyleName("okm-ToolBar-button-disabled");
			removePropertyGroup.setHTML(Util.imageHTML("img/icon/actions/remove_property_group_disabled.gif",Main.i18n("filebrowser.menu.remove.property.group")));
		}
	}
	
	/**
	 * Save changes to the actual view
	 * Must be called after mainPanel actual view is changed
	 */
	public void changeView(int view, int newMainPanelView) {
		boolean toolBarEnabled = true;
		int mainPanelView = Main.get().mainPanel.getActualView();
		
		// Evaluates actual desktop view to put values
		switch(mainPanelView){
			case ExtendedDockPanel.DESKTOP:
				// Saves actual view values on hashMap
				switch (actualView) {
					case PanelDefinition.NAVIGATOR_TAXONOMY:
						viewValues.put("view_root:option", toolBarOption);
						break;
						
					case PanelDefinition.NAVIGATOR_CATEGORIES:
						viewValues.put("view_categories:option", toolBarOption);
						break;
						
					case PanelDefinition.NAVIGATOR_THESAURUS:
						viewValues.put("view_thesaurus:option", toolBarOption);
						break;
						
					case PanelDefinition.NAVIGATOR_TRASH:
						viewValues.put("view_trash:option", toolBarOption);
						break;
						
					case PanelDefinition.NAVIGATOR_TEMPLATES:
						viewValues.put("view_templates:option", toolBarOption);
						break;
					
					case PanelDefinition.NAVIGATOR_PERSONAL:
						viewValues.put("view_my_documents:option", toolBarOption);
						break;
					
					case PanelDefinition.NAVIGATOR_MAIL:
						viewValues.put("view_mail:option", toolBarOption);
						break;
				}
				break;
				
			case ExtendedDockPanel.SEARCH:
				viewValues.put("view_search:option", toolBarOption);
				break;
				
			case ExtendedDockPanel.DASHBOARD:
				viewValues.put("view_dashboard:option", toolBarOption);
				break;
				
			case ExtendedDockPanel.ADMINISTRATION:
				viewValues.put("view_administration:option", toolBarOption);
				break;
		}
		
		// Evaluates new desktop view to restore values 
		switch(newMainPanelView){
			case ExtendedDockPanel.DESKTOP:
				switch (view) {
					case PanelDefinition.NAVIGATOR_TAXONOMY:
						if (viewValues.containsKey("view_root:option")){
							toolBarOption = (ToolBarOption) viewValues.get("view_root:option");
						}
						toolBarEnabled = true;
						break;
						
					case PanelDefinition.NAVIGATOR_CATEGORIES:
						if (viewValues.containsKey("view_categories:option")){
							toolBarOption = (ToolBarOption) viewValues.get("view_categories:option");
						} else {
							toolBarOption = getDefaultCategoriesToolBar();
						}
						toolBarEnabled = true;
						break;
					
					case PanelDefinition.NAVIGATOR_THESAURUS:
						if (viewValues.containsKey("view_thesaurus:option")){
							toolBarOption = (ToolBarOption) viewValues.get("view_thesaurus:option");
						} else {
							toolBarOption = getDefaultThesaurusToolBar();
						}
						toolBarEnabled = true;
						break;
						
					case PanelDefinition.NAVIGATOR_TRASH:
						if (viewValues.containsKey("view_trash:option")){
							toolBarOption = (ToolBarOption) viewValues.get("view_trash:option");
						} else {
							toolBarOption = getDefaultTrashToolBar();
						}
						toolBarEnabled = false;
						break;
					
					case PanelDefinition.NAVIGATOR_TEMPLATES:
						if (viewValues.containsKey("view_templates:option")){
							toolBarOption = (ToolBarOption) viewValues.get("view_templates:option");
						} else {
							toolBarOption = getDefaultTemplatesToolBar();
						}
						toolBarEnabled = true;
						break;
					
					case PanelDefinition.NAVIGATOR_PERSONAL:
						if (viewValues.containsKey("view_my_documents:option")){
							toolBarOption = (ToolBarOption) viewValues.get("view_my_documents:option");
						} else {
							toolBarOption = getDefaultMyDocumentsToolBar();
						}
						toolBarEnabled = true;
						break;
				}
				break;
				
			case ExtendedDockPanel.SEARCH:
				if (viewValues.containsKey("view_search:option")){
					toolBarOption = (ToolBarOption) viewValues.get("view_search:option");
				} else {
					toolBarOption = getDefaultSearchToolBar();
				}
				toolBarEnabled = false;
				break;
			
			case ExtendedDockPanel.DASHBOARD:
				if (viewValues.containsKey("view_dashboard:option")){
					toolBarOption = (ToolBarOption) viewValues.get("view_dashboard:option");
				} else {
					toolBarOption = getDefaultDashboardToolBar();
				}
				toolBarEnabled = false;
				break;
				
			case ExtendedDockPanel.ADMINISTRATION:
				if (viewValues.containsKey("view_administration:option")){
					toolBarOption = (ToolBarOption) viewValues.get("view_administration:option");
				} else {
					toolBarOption = getDefaultAdministrationToolBar();
				}
				toolBarEnabled = false;
				break;

		}
		
		// Enables  before evaluate show icons, order is important because can evaluate
		// icons if enabled is false always before evaluate icons must be enabled
		enabled = true;
		evaluateShowIcons(); // Evalues icons to show
		enabled = toolBarEnabled; 
		actualView = view;   // Sets the new view active
		
		// Sets the permission to main menu
		Main.get().mainPanel.topPanel.mainMenu.setOptions(toolBarOption);
	}
	
	/**
	 * Call back opens document passed by url param
	 */
	final AsyncCallback<Boolean> callbackIsValidDocument = new AsyncCallback<Boolean>() {
		public void onSuccess(Boolean result){
			if (result.booleanValue()) {
				// Opens folder passed by parameter
				String path = Main.get().userHome.getPath().substring(0,Main.get().userHome.getPath().lastIndexOf("/"));
				Main.get().activeFolderTree.openAllPathFolder(path,Main.get().userHome.getPath());
			}
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
			if (result.booleanValue()) {
				// Opens document passed by parameter
				Main.get().activeFolderTree.openAllPathFolder(Main.get().userHome.getPath(),"");
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("isValid", caught);
		}
	};
	
	
	
	/**
	 * Gets asyncronous to get all groups
	 */
	final AsyncCallback<List<String>> callbackGetAllGroups = new AsyncCallback<List<String>>() {
		public void onSuccess(List<String> result){
			// List of groups to be added
			if (!result.isEmpty()) {
				enableAddPropertyGroup();
			} else {
				disableAddPropertyGroup();
			}
		}

		public void onFailure(Throwable caught) {
			disableAddPropertyGroup();
			Main.get().showError("GetAllGroups", caught);
		}
	};
	
	/**
	 * Gets all property groups
	 */
	private void getAllGroups() {
		GWTDocument gwtDocument = Main.get().mainPanel.browser.fileBrowser.getDocument();
		if (gwtDocument!= null) {
			ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
			endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
			propertyGroupService.getAllGroups(gwtDocument.getPath(), callbackGetAllGroups);
		}
	}
	
	/**
	 * Sets the user home
	 * 
	 * @param name String The name
	 * @param path String The path
	 * @param type String the type
	 */
	public void setUserHome(String name, String path, String type){
		Main.get().userHome.setName(name);
		Main.get().userHome.setPath(path);
		Main.get().userHome.setType(type);
	}

	/**
	 * Create html applet code 
	 */
	public void setApplet(String token, String path) {
//		Widget scannerApplet = RootPanel.get("appletScanner");
//		scannerApplet.setVisible(false);
//		scannerApplet.getElement().setInnerHTML("<applet code=\"com.openkm.applet.Scanner\" name=\"Scanner\" width=\"20\" height=\"20\" mayscript archive=\"../scanner.jar\">"+
//				"<param name=\"token\" value=\""+token+"\">"+
//				"<param name=\"path\" value=\""+path+"\">"+
//				//"<param name=\"separate_jvm\" value=\"true\">"+
//				"</applet>");
//		String javaScript = "<script>\n";
//		javaScript += "var attributes = { code:'com.openkm.applet.Scanner.class', archive:'../scanner.jar', width:15, height:15};\n";
//		javaScript += "var parameters = { codebase_lookup:'true' };\n";
//		javaScript += "var version = '1.6';\n";
//		javaScript += "</script>\n";
//		javaScript += "deployJava.runApplet(attributes, parameters, version);\n";
//		scannerApplet.getElement().setInnerHTML(javaScript);
	}

	/**
	 * Set current repository path
	 */
	public native void setPath(String path) /*-{
		$doc.OKMApplet.setPath(path);
	}-*/;
	
	/**
	 * Lang refresh
	 */
	public void langRefresh() {
		evaluateShowIcons();
		evaluateRemoveGroupProperty(propertyGroupEnabled);
	}
	
	/**
	 * Gets the tool bar option
	 * 
	 * @return The actual toolBar Option
	 */
	public ToolBarOption getToolBarOption() {
		return toolBarOption;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasMouseDownHandlers#addMouseDownHandler(com.google.gwt.event.dom.client.MouseDownHandler)
	 */
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
	    return addDomHandler(handler, MouseDownEvent.getType());
	}
	  
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasMouseMoveHandlers#addMouseMoveHandler(com.google.gwt.event.dom.client.MouseMoveHandler)
	 */
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		return addDomHandler(handler, MouseMoveEvent.getType());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasMouseOutHandlers#addMouseOutHandler(com.google.gwt.event.dom.client.MouseOutHandler)
	 */
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
	    return addDomHandler(handler, MouseOutEvent.getType());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasMouseOverHandlers#addMouseOverHandler(com.google.gwt.event.dom.client.MouseOverHandler)
	 */
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
	    return addDomHandler(handler, MouseOverEvent.getType());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasMouseUpHandlers#addMouseUpHandler(com.google.gwt.event.dom.client.MouseUpHandler)
	 */
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
	    return addDomHandler(handler, MouseUpEvent.getType());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasMouseWheelHandlers#addMouseWheelHandler(com.google.gwt.event.dom.client.MouseWheelHandler)
	 */
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
	    return addDomHandler(handler, MouseWheelEvent.getType());
	}
}
