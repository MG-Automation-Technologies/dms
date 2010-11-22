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

package com.openkm.frontend.client.panel;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTMail;
import com.openkm.frontend.client.panel.bottom.BottomPanel;
import com.openkm.frontend.client.panel.center.Administration;
import com.openkm.frontend.client.panel.center.Dashboard;
import com.openkm.frontend.client.panel.center.Desktop;
import com.openkm.frontend.client.panel.center.Search;
import com.openkm.frontend.client.panel.top.TopPanel;
import com.openkm.frontend.client.util.Keyboard;
import com.openkm.frontend.client.widget.foldertree.FolderSelectPopup;
import com.openkm.frontend.client.widget.mainmenu.MainMenu;

/**
 * Extends dock panel functions
 * 
 * @author jllort
 *
 */
public class ExtendedDockPanel extends Composite {
	
	// Panels size
	public static final int VERTICAL_BORDER_PANEL_WIDTH = 10;
	
	// Workspace constants
	public static final int DESKTOP 		= 0;
	public static final int SEARCH 			= 1;
	public static final int DASHBOARD		= 2;
	public static final int ADMINISTRATION	= 3;
	public static final int EXTENSIONS 		= 4;
	
	public DockPanel dockPanel;
	
	public TopPanel topPanel;
	public VerticalBorderPanel leftBorderPanel;
	public VerticalBorderPanel rightBorderPanel;
	public BottomPanel bottomPanel;
	
	// PANELS
	public Desktop desktop;
	public Search search;
	public Dashboard dashboard;
	public Administration administration;
	
	private int actualView = -1;
	
	private HandlerRegistration handlerRegistration = null;
	private FolderSelectPopup folderSelectPopup;
	
	int centerWidth = 0;
	int centerHeight = 0;
	
	/**
	 * Extended dock panel
	 */
	public ExtendedDockPanel() {
		dockPanel = new DockPanel();
		folderSelectPopup = new FolderSelectPopup();
		enableKeyShorcuts();
		
		// Object initialization
		topPanel = new TopPanel();
		leftBorderPanel  = new VerticalBorderPanel();
		rightBorderPanel = new VerticalBorderPanel();
		bottomPanel = new BottomPanel();
		
		// Desktop panels initialization
		desktop = new Desktop();
		
		// Search panels initialization
		search = new Search();
		
		// Dashboard panel initialization
		dashboard = new Dashboard();
		
		// Administration panel initialization
		administration = new Administration();
		
		// Initialize dockPanel size
		dockPanel.setSize(""+Window.getClientWidth(), ""+Window.getClientHeight());

		// The active panel must be the last on initalization because establishes coordenates
		leftBorderPanel.setSize(VERTICAL_BORDER_PANEL_WIDTH, Window.getClientHeight()-(TopPanel.PANEL_HEIGHT + BottomPanel.PANEL_HEIGHT));
		rightBorderPanel.setSize(VERTICAL_BORDER_PANEL_WIDTH, Window.getClientHeight()-(TopPanel.PANEL_HEIGHT + BottomPanel.PANEL_HEIGHT));
		
		centerWidth = Window.getClientWidth()-(2*VERTICAL_BORDER_PANEL_WIDTH);
		centerHeight = Window.getClientHeight()-(TopPanel.PANEL_HEIGHT + BottomPanel.PANEL_HEIGHT);
		
		topPanel.setWidth(""+Window.getClientWidth());
		desktop.setSize(centerWidth, centerHeight);
		search.setSize(centerWidth, centerHeight);
		dashboard.setSize(centerWidth, centerHeight);
		administration.setSize(centerWidth, centerHeight);
		
		actualView = DESKTOP;	
		
		// Creates the dockPanel
		dockPanel.add(topPanel, DockPanel.NORTH);
		dockPanel.add(bottomPanel, DockPanel.SOUTH);
		dockPanel.add(leftBorderPanel, DockPanel.WEST);		
		dockPanel.add(rightBorderPanel, DockPanel.EAST);
		dockPanel.add(desktop, DockPanel.CENTER);
		
		dockPanel.setHorizontalAlignment(DockPanel.ALIGN_LEFT);
		dockPanel.setVerticalAlignment(DockPanel.ALIGN_TOP);
		
		//dockPanel.setVisible(false);
		initWidget(dockPanel);
	}

	
	/**
	 * setView
	 * 
	 * @param workspace
	 */
	public void setView(int workspace) {
		disableView();
		switch (workspace) {
			case DESKTOP :
				int navigatorView = Main.get().mainPanel.desktop.navigator.getStackIndex();
				Main.get().mainPanel.topPanel.toolBar.changeView(navigatorView, DESKTOP);
				actualView = workspace;
				break;
				
			case SEARCH :
				Main.get().mainPanel.topPanel.toolBar.changeView(0, SEARCH);
				actualView = workspace;
				break;
			
			case DASHBOARD :
				Main.get().mainPanel.topPanel.toolBar.changeView(0, DASHBOARD);
				actualView = workspace;
				break;
				
			case ADMINISTRATION :
				Main.get().mainPanel.topPanel.toolBar.changeView(0, ADMINISTRATION);
				actualView = workspace;
				break;
			
			default:
				Main.get().mainPanel.topPanel.toolBar.changeView(0, EXTENSIONS);
				actualView = workspace;
		}
		enableView();
	}
	
	private void disableView() {
		switch (actualView) {
			case DESKTOP :
				dockPanel.remove(desktop);
				break;
				
			case SEARCH :
				dockPanel.remove(search);
				break;
				
			case DASHBOARD:
				dockPanel.remove(dashboard);
				break;
			
			case ADMINISTRATION:
				dockPanel.remove(administration);
				break;
			
			default:
				dockPanel.remove(topPanel.tabWorkspace.getWidgetExtensionByIndex(actualView));
				break;
		}
	}
	
	private void enableView() {
		switch (actualView) {
			case DESKTOP :
				dockPanel.add(desktop,DockPanel.CENTER);
				desktop.refreshSpliterAfterAdded();
				break;
				
			case SEARCH :
				dockPanel.add(search,DockPanel.CENTER);
				search.refreshSpliterAfterAdded();
				break;
		
			case DASHBOARD :
				dockPanel.add(dashboard,DockPanel.CENTER);
				break;
			
			case ADMINISTRATION :
				dockPanel.add(administration,DockPanel.CENTER);
				break;
			
			default:
				dockPanel.add(topPanel.tabWorkspace.getWidgetExtensionByIndex(actualView),DockPanel.CENTER);
				break;
		}
	}	
	
	/**
	 * getActualView
	 * 
	 * @return
	 */
	public int getActualView(){
		return actualView;
	}
	
	/**
	 * enableKeyShorcuts
	 */
	public void enableKeyShorcuts() {
		Log.debug("ExtendedDockPanel enableKeyShorcuts");
		dockPanel.sinkEvents(Event.KEYEVENTS);
		handlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
					boolean propagate = true;
					int type = event.getTypeInt(); 
					if (type == Event.ONKEYDOWN )  {
						int keyCode = event.getNativeEvent().getKeyCode(); 
						switch (keyCode) {
							case Keyboard.KEY_F2:
								if (actualView == DESKTOP && Main.get().activeFolderTree.isPanelSelected() && 
									Main.get().mainPanel.topPanel.toolBar.getToolBarOption().renameOption &&
									(Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TAXONOMY ||
									 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_PERSONAL || 		
									 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TEMPLATES ||
									 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_CATEGORIES ||
									 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_MAIL)) {
									
									Main.get().activeFolderTree.rename();
									propagate = false;
								} else if (Main.get().mainPanel.topPanel.toolBar.getToolBarOption().renameOption && (
										   Main.get().mainPanel.desktop.browser.fileBrowser.isDocumentSelected() || 
										   Main.get().mainPanel.desktop.browser.fileBrowser.isFolderSelected()) ) {
									
									Main.get().mainPanel.desktop.browser.fileBrowser.rename();
									propagate = false;
								}
								break;
							
							case Keyboard.KEY_SUPR:
								if (actualView == DESKTOP && Main.get().activeFolderTree.isPanelSelected() && 
									Main.get().mainPanel.topPanel.toolBar.getToolBarOption().deleteOption &&
									(Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TAXONOMY ||
									 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_CATEGORIES || 	
									 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_PERSONAL || 		
									 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TEMPLATES ||
									 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_MAIL)) {
										
									Main.get().mainPanel.topPanel.toolBar.executeDelete();
									propagate = false;
								} else if (Main.get().mainPanel.topPanel.toolBar.getToolBarOption().deleteOption && (
										   Main.get().mainPanel.desktop.browser.fileBrowser.isDocumentSelected() || 
										   Main.get().mainPanel.desktop.browser.fileBrowser.isFolderSelected() ||
										   Main.get().mainPanel.desktop.browser.fileBrowser.isMailSelected())) {
									
									Main.get().mainPanel.topPanel.toolBar.executeDelete();
									propagate = false;
								}
								break;
							
							case Keyboard.KEY_C:
							case Keyboard.KEY_X:
								// Case CTRL + C
								if (event.getNativeEvent().getCtrlKey()) {
									if (actualView == DESKTOP && Main.get().activeFolderTree.isPanelSelected() && 
											Main.get().mainPanel.topPanel.toolBar.getToolBarOption().copyOption &&
											(Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TAXONOMY ||
											 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_PERSONAL || 		
											 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TEMPLATES ||
											 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_MAIL)) {
										
										// Saves folder to be copied
										GWTFolder folder = Main.get().activeFolderTree.getFolder();
										folderSelectPopup.setEntryPoint(FolderSelectPopup.ENTRYPOINT_KEYBOARD);
										switch (keyCode) {
											case Keyboard.KEY_C:
												folderSelectPopup.setToCopy(folder);
												Main.get().mainPanel.bottomPanel.setStatus("status.folder.copied",false);
												break;
											case Keyboard.KEY_X:
												Main.get().mainPanel.bottomPanel.setStatus("status.folder.cut",false);
												// Sets the origin panel an treitem to be removed after move
												folderSelectPopup.setOriginPanel(Main.get().mainPanel.desktop.navigator.getStackIndex()); 
												TreeItem actualItem = Main.get().activeFolderTree.getActualItem();
												folderSelectPopup.setTreeItemToBeDeleted(actualItem);
												folderSelectPopup.setToMove(folder);
												
												break;
										}
										propagate = false;
										
									} else if (Main.get().mainPanel.topPanel.toolBar.getToolBarOption().copyOption && 
											   (Main.get().mainPanel.desktop.browser.fileBrowser.isDocumentSelected() || 
											    Main.get().mainPanel.desktop.browser.fileBrowser.isFolderSelected() ||
											    Main.get().mainPanel.desktop.browser.fileBrowser.isMailSelected()) ) {
																				
										// Saves the document or folder to be copied
										folderSelectPopup.setEntryPoint(FolderSelectPopup.ENTRYPOINT_KEYBOARD);
										if (Main.get().mainPanel.desktop.browser.fileBrowser.isDocumentSelected()) {
											GWTDocument document = Main.get().mainPanel.desktop.browser.fileBrowser.getDocument();
											switch (keyCode) {
												case Keyboard.KEY_C:
													Main.get().mainPanel.bottomPanel.setStatus("status.document.copied",false);
													folderSelectPopup.setToCopy(document);
													break;
												case Keyboard.KEY_X:
													Main.get().mainPanel.bottomPanel.setStatus("status.document.cut",false);
													folderSelectPopup.setToMove(document);
													break;
											}
										} else if (Main.get().mainPanel.desktop.browser.fileBrowser.isMailSelected()) {
											GWTMail mail = Main.get().mainPanel.desktop.browser.fileBrowser.getMail();
											switch (keyCode) {
												case Keyboard.KEY_C:
													Main.get().mainPanel.bottomPanel.setStatus("status.document.copied",false);
													folderSelectPopup.setToCopy(mail);
													break;
												case Keyboard.KEY_X:
													Main.get().mainPanel.bottomPanel.setStatus("status.document.cut",false);
													folderSelectPopup.setToMove(mail);
													break;
											}
										} else {
											GWTFolder folder = Main.get().mainPanel.desktop.browser.fileBrowser.getFolder();
											switch (keyCode) {
												case Keyboard.KEY_C:
													Main.get().mainPanel.bottomPanel.setStatus("status.folder.copied",false);
													folderSelectPopup.setToCopy(folder);
													break;
												case Keyboard.KEY_X:
													Main.get().mainPanel.bottomPanel.setStatus("status.folder.cut",false);
													// Sets the origin panel and treeitem to be removed after move
													folderSelectPopup.setOriginPanel(Main.get().mainPanel.desktop.navigator.getStackIndex()); 
													TreeItem actualItem = Main.get().activeFolderTree.getChildFolder(folder.getPath());
													folderSelectPopup.setTreeItemToBeDeleted(actualItem);
													folderSelectPopup.setToMove(folder);
													break;
											}
										}
										propagate = false;
									}
								}
								break;
							
							case Keyboard.KEY_V:
								// Case CTRL + V
								if (event.getNativeEvent().getCtrlKey() && 
									(folderSelectPopup.getAction()== FolderSelectPopup.ACTION_COPY ||
									 folderSelectPopup.getAction()== FolderSelectPopup.ACTION_MOVE )) {
									
									// Destination folder is always selected by tree
									if (actualView == DESKTOP && Main.get().activeFolderTree.isPanelSelected() && 
										(Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TAXONOMY ||
										 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_PERSONAL || 		
										 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TEMPLATES || 
										 Main.get().mainPanel.desktop.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_MAIL)) {
										
										// Evaluates destination folder grant to copy
										GWTFolder folder = Main.get().activeFolderTree.getFolder();
										if (folderSelectPopup.evaluateActionSecurity(folder)) {
											Main.get().mainPanel.bottomPanel.resetStatus();
											folderSelectPopup.executeAction(folder.getPath(), true);
										}
									}
								}
								break;
							
							case Keyboard.KEY_D:
								// Case CTRL + D
								if (event.getNativeEvent().getCtrlKey() && actualView == DESKTOP && 
									Main.get().mainPanel.topPanel.toolBar.getToolBarOption().downloadOption) {
									Main.get().mainPanel.topPanel.toolBar.executeDownload();
									propagate = false;
								}
								break;
								
							case Keyboard.KEY_Z:
								// Case CTRL+Z
								if (event.getNativeEvent().getCtrlKey()) {
									
									if (Log.getCurrentLogLevel()==Log.LOG_LEVEL_DEBUG) {
										Log.setCurrentLogLevel(Log.LOG_LEVEL_OFF);
										Main.get().mainPanel.bottomPanel.setStatus("status.debug.disabled",false);
									} else {
										Log.setCurrentLogLevel(Log.LOG_LEVEL_DEBUG);
										Main.get().mainPanel.bottomPanel.setStatus("status.debug.enabled",false);
									}
									propagate = false;
								}
								break;
							
							case Keyboard.KEY_N:
								// Case CTRL + N
								if (event.getNativeEvent().getCtrlKey() && actualView == DESKTOP && 
									Main.get().mainPanel.topPanel.toolBar.getToolBarOption().createFolderOption) {
									Main.get().mainPanel.topPanel.toolBar.executeFolderDirectory();
									propagate = false;
								}
								break;
							
							case Keyboard.KEY_F5:
								if (actualView == DESKTOP && 
									Main.get().mainPanel.topPanel.toolBar.getToolBarOption().refreshOption){
									Main.get().mainPanel.topPanel.toolBar.executeRefresh();
									propagate = false;
								}
								break;
							
							case Keyboard.KEY_INSERT:
								if (actualView == DESKTOP && 
									Main.get().mainPanel.topPanel.toolBar.getToolBarOption().addDocumentOption) {
									Main.get().mainPanel.topPanel.toolBar.executeAddDocument();
									propagate = false;
								}
								break;
								
							case Keyboard.KEY_B:
								// Case ALT + B
								if (event.getNativeEvent().getAltKey() && Main.get().mainPanel.topPanel.mainMenu.getToolBarOption().homeOption) {
									Main.get().mainPanel.topPanel.mainMenu.manageBookmarkPopup.showPopup();
									propagate = false;
								}
								break;
								
							case Keyboard.KEY_F1:
								Window.open(MainMenu.URI_DOCUMENTATION, "_blank", "");
								propagate = false;
								break;
								
							case Keyboard.KEY_Q: 
								// Case CTRL + Q
								if (event.getNativeEvent().getCtrlKey()) {
									Main.get().logoutPopup.logout();
								}
								break;
						}
					}
					
					// Not propagates event to the browser
					if (!propagate) {
						DOM.eventPreventDefault((Event) event.getNativeEvent());
					}
				}
		});
	}
	
	/**
	 * Disables key shortcuts
	 */
	public void disableKeyShorcuts() {
		Log.debug("ExtendedDockPanel disableKeyShorcuts");
		dockPanel.unsinkEvents(Event.KEYEVENTS);
		if (handlerRegistration!=null) {
			handlerRegistration.removeHandler();
		}
	}
	
	/**
	 * getCenterWidth
	 * 
	 * @return
	 */
	public int getCenterWidth() {
		return centerWidth;
	}

	/**
	 * getCenterHeight
	 * 
	 * @return
	 */
	public int getCenterHeight() {
		return centerHeight;
	}
}