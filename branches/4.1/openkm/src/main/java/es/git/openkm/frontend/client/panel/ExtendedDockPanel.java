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

package es.git.openkm.frontend.client.panel;

import java.util.HashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.TreeItem;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.Coordenates;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.bean.GWTMail;
import es.git.openkm.frontend.client.panel.bottom.BottomPanel;
import es.git.openkm.frontend.client.panel.center.Administration;
import es.git.openkm.frontend.client.panel.center.Browser;
import es.git.openkm.frontend.client.panel.center.Dashboard;
import es.git.openkm.frontend.client.panel.center.Search;
import es.git.openkm.frontend.client.panel.left.HistorySearch;
import es.git.openkm.frontend.client.panel.left.Navigator;
import es.git.openkm.frontend.client.panel.top.TopPanel;
import es.git.openkm.frontend.client.util.Keyboard;
import es.git.openkm.frontend.client.util.Util;
import es.git.openkm.frontend.client.widget.foldertree.FolderSelectPopup;
import es.git.openkm.frontend.client.widget.mainmenu.MainMenu;
import es.git.openkm.frontend.client.widget.resize.VerticalBar;

/**
 * Extends dock panel functions
 * 
 * @author jllort
 *
 */
public class ExtendedDockPanel extends Composite {
	
	// Workspace constants
	public static final int DESKTOP 		= 0;
	public static final int SEARCH 			= 1;
	public static final int DASHBOARD		= 2;
	public static final int ADMINISTRATION	= 3;
	
	public DockPanel dockPanel;
	
	public TopPanel topPanel;
	public VerticalBorderPanel leftBorderPanel;
	public VerticalBar verticalBarPanel; 
	public VerticalBorderPanel rightBorderPanel;
	public BottomPanel bottomPanel;
	
	// DESKTOP
	public Navigator navigator;
	public Browser browser;
	
	// SEARCH panel
	public HistorySearch historySearch;
	public Search search;
	
	// Dashboard panel
	public Dashboard dashboard;
	public Administration administration;
	
	public Coordenates main;
	public Coordenates top;
	public Coordenates leftBar;
	public Coordenates left;
	public Coordenates verticalBar;
	public Coordenates center;
	public Coordenates rightBar;
	public Coordenates bottom;
	
	private int actualView = -1;
	private HashMap<String, Coordenates> desktopMap = new HashMap<String, Coordenates>();
	private HashMap<String, Coordenates> searchMap  = new HashMap<String, Coordenates>();
	private HashMap<String, Coordenates> dashboardMap  = new HashMap<String, Coordenates>();
	private HashMap<String, Coordenates> administrationMap  = new HashMap<String, Coordenates>();
	
	private EventPreview keyBoardShorcuts;
	private FolderSelectPopup folderSelectPopup;
	
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
		verticalBarPanel = new VerticalBar();
		rightBorderPanel = new VerticalBorderPanel();
		bottomPanel = new BottomPanel();
		
		// Desktop panels initialization
		navigator = new Navigator();
		browser = new Browser();
		
		// Search panels initialization
		historySearch = new HistorySearch();
		search = new Search();
		
		// Dashboard panel initialization
		dashboard = new Dashboard();
		
		// Administration panel initialization
		administration = new Administration();
		
		// Initialize panels size and position (x,y,width,height) of each panel
		main = new Coordenates(0, 0, Window.getClientWidth(), Window.getClientHeight());
		// Initialize dockPanel size
		dockPanel.setSize(""+main.getWidth(), ""+main.getHeight());
		// Initializes coordenates panels values and sets workspace panels size using coordenates panels values
		init();		
		
		// Creates the dockPanel
		dockPanel.add(topPanel, DockPanel.NORTH);
		dockPanel.add(bottomPanel, DockPanel.SOUTH);
		dockPanel.add(leftBorderPanel, DockPanel.WEST);
		dockPanel.add(navigator, DockPanel.WEST);
		
		dockPanel.add(historySearch, DockPanel.WEST);
		historySearch.setVisible(false);
		
		dockPanel.add(verticalBarPanel, DockPanel.WEST);
		dockPanel.add(rightBorderPanel, DockPanel.EAST);
		dockPanel.add(browser, DockPanel.CENTER);
		
		dockPanel.setHorizontalAlignment(DockPanel.ALIGN_LEFT);
		dockPanel.setVerticalAlignment(DockPanel.ALIGN_TOP);
		
		//dockPanel.setVisible(false);
		initWidget(dockPanel);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setSize(java.lang.String, java.lang.String)
	 */
	public void setSize(String width, String height) {
		dockPanel.setSize(width, height);
	}
	
	/**
	 * Initializes worskpaces coordenades, and sizes
	 */
	public void init() {
		
		// The active panel must be the last on initalization because establishes coordenates
		initWorkspaceCoordenates(ADMINISTRATION, main);
		initWorkspaceCoordenates(DASHBOARD, main);
		initWorkspaceCoordenates(SEARCH, main);
		initWorkspaceCoordenates(DESKTOP, main);
		
		actualView = DESKTOP;
	}
	
	/**
	 * Initializes the workspace coordenates
	 * 
	 * @param workspace The workspace to initialize
	 * @param main The main window
	 */
	public void initWorkspaceCoordenates(int workspace, Coordenates main) {
		top = new Coordenates(0, 0, main.getWidth(), 57);
		bottom = new Coordenates(0, main.getHeight()-25, main.getWidth(), 25);
		int centerHeight = main.getHeight() - (top.getHeight()+bottom.getHeight());
		
		leftBar = new Coordenates(0, top.getHeight(), 10, centerHeight);
		rightBar = new Coordenates(main.getWidth()-10, top.getHeight(), 10, centerHeight);
		left = new Coordenates(leftBar.getWidth(), top.getHeight(), 225, centerHeight);
		verticalBar = new Coordenates(left.getX()+left.getWidth(), top.getHeight(), 10, centerHeight);
		
		center = new Coordenates();
		center.setX(verticalBar.getX()+verticalBar.getWidth());
		center.setY(top.getHeight());
		center.setWidth(main.getWidth()-(leftBar.getWidth() + left.getWidth() + verticalBar.getWidth() +rightBar.getWidth()));
		center.setHeight(centerHeight);
		
		switch (workspace) {
			case DESKTOP :
				desktopMap.put("top", top);
				desktopMap.put("bottom", bottom);
				desktopMap.put("leftBar", leftBar);
				desktopMap.put("rightBar", rightBar);
				desktopMap.put("left", left);
				desktopMap.put("verticalBar", verticalBar);
				desktopMap.put("center", center);
				setWorkspaceSize(topPanel, leftBorderPanel, navigator, verticalBarPanel, browser, rightBorderPanel, bottomPanel);
				actualView = workspace;
				saveCoordenates();
				break;
				
			case SEARCH :
				searchMap.put("top", top);
				searchMap.put("bottom", bottom);
				searchMap.put("leftBar", leftBar);
				searchMap.put("rightBar", rightBar);
				searchMap.put("left", left);
				searchMap.put("verticalBar", verticalBar);
				searchMap.put("center", center);
				setWorkspaceSize(topPanel, leftBorderPanel, historySearch, verticalBarPanel, search, rightBorderPanel, bottomPanel);
				actualView = workspace;
				saveCoordenates();
				break;
			
			case DASHBOARD:
				dashboardMap.put("top", top);
				dashboardMap.put("bottom", bottom);
				dashboardMap.put("leftBar", leftBar);
				dashboardMap.put("rightBar", rightBar);
				dashboardMap.put("left", left);
				dashboardMap.put("verticalBar", new Coordenates());
				center.setWidth(main.getWidth()-20); // The 2x10, two left / right bars
				dashboardMap.put("center", center);
				dashboard.setSize(center.getWidth(), center.getHeight());
				actualView = workspace;
				saveCoordenates();
				break;
				
			case ADMINISTRATION:
				administrationMap.put("top", top);
				administrationMap.put("bottom", bottom);
				administrationMap.put("leftBar", leftBar);
				administrationMap.put("rightBar", rightBar);
				administrationMap.put("left", left);
				administrationMap.put("verticalBar", new Coordenates());
				center.setWidth(main.getWidth()-20); // The 2x10, two left / right bars
				administrationMap.put("center", center);
				administration.setSize(center.getWidth(), center.getHeight());
				actualView = workspace;
				saveCoordenates();
				break;
		}
	}
	
	/**
	 * Sets the workspace size
	 * 
	 * @param topPanel The actual top panel
	 * @param leftBorderPanel The leftBorder panel
	 * @param leftPanel The left panel
	 * @param verticalBarPanel The verticalBar panel
	 * @param centerPanel The center panel
	 * @param rightBorderPanel The rightBorder panel
	 * @param bottomPanel The bottom panel
	 */
	public void setWorkspaceSize(ExtendedSizeComposite topPanel, ExtendedSizeComposite leftBorderPanel, 
								 ExtendedSizeComposite leftPanel, ExtendedSizeComposite verticalBarPanel,
								 ExtendedComposite centerPanel,
								 ExtendedSizeComposite rightBorderPanel, ExtendedSizeComposite bottomPanel) {
		Util.setSize(topPanel, top);
		Util.setSize(leftBorderPanel, leftBar);
		Util.setSize(leftPanel, left);
		Util.setSize(verticalBarPanel, verticalBar);
		centerPanel.setSize(center.getWidth(), center.getHeight());
		Util.setSize(rightBorderPanel, rightBar);
		Util.setSize(bottomPanel, bottom);
	}
	
	/**
	 * Establishes the verticalResize
	 * 
	 * @param width The width
	 */
	public void verticalResize(int width) {
		switch (actualView) {
			case DESKTOP :
				verticalResize(width, navigator, browser);
				break;
				
			case SEARCH :
				verticalResize(width, historySearch, search);
				break;
		}
	}
	
	/**
	 * Sets the vertical resize
	 * 
	 * @param width The width
	 * @param leftPanel The left panel
	 * @param centerPanel The center panel
	 */
	public void verticalResize(int width, ExtendedSizeComposite leftPanel, ExtendedComposite centerPanel) {
		// Make a correction on width respect initial leftPanel position
		leftPanel.setSize(width-left.getX(), left.getHeight());
		// Capture final width because we can't be sure that the resize be totally done (HTML resize problems)
		left.setWidth(leftPanel.getOffsetWidth());
		// Recalculate verticalBar X position
		verticalBar.setX(left.getX()+left.getWidth());
		// Make a correction on width respect intial centerPanel position
		center.setWidth( main.getWidth()-(left.getWidth()+verticalBar.getWidth()+leftBar.getWidth()+rightBar.getWidth()) );
		centerPanel.setSize(center.getWidth(),centerPanel.getTopHeight(),center.getHeight());
		// Capture final width because we can't be sure that the resize be totally done (HTML resize problems)
		center.setWidth(centerPanel.getOffsetWidth());
		//Recalculate center X position
		center.setX(verticalBar.getX()+verticalBar.getWidth());
	}
	
	public void horizontalResize(int height) {
		//Ajust height to top Y position height - center.getY()
		switch (actualView) {
		case DESKTOP :
			horizontalResize(height, browser);
			break;
			
		case SEARCH :
			horizontalResize(height, search);
			break;
		}
	}
	
	public void horizontalResize(int height, ExtendedComposite centerPanel) {
		//Ajust height to top Y position height - center.getY()
		centerPanel.setSize(center.getWidth(), height-center.getY(), center.getHeight());
	}
	
	private void saveCoordenates() {
		switch (actualView) {
			case DESKTOP :
				desktopMap.put("top", top);
				desktopMap.put("bottom", bottom);
				desktopMap.put("leftBar", leftBar);
				desktopMap.put("rightBar", rightBar);
				desktopMap.put("left", left);
				desktopMap.put("verticalBar", verticalBar);
				desktopMap.put("center", center);
				break;
				
			case SEARCH :
				searchMap.put("top", top);
				searchMap.put("bottom", bottom);
				searchMap.put("leftBar", leftBar);
				searchMap.put("rightBar", rightBar);
				searchMap.put("left", left);
				searchMap.put("verticalBar", verticalBar);
				searchMap.put("center", center);
				break;
		}
	}
	
	private void getCoordenates() {
		switch (actualView) {
			case DESKTOP :
				top 		= (Coordenates) desktopMap.get("top");
				bottom 		= (Coordenates) desktopMap.get("bottom");
				leftBar 	= (Coordenates) desktopMap.get("leftBar");
				rightBar 	= (Coordenates) desktopMap.get("rightBar");
				left 		= (Coordenates) desktopMap.get("left");
				verticalBar = (Coordenates) desktopMap.get("verticalBar");
				center		= (Coordenates) desktopMap.get("center");
				break;
				
			case SEARCH :
				top 		= (Coordenates) searchMap.get("top");
				bottom 		= (Coordenates) searchMap.get("bottom");
				leftBar 	= (Coordenates) searchMap.get("leftBar");
				rightBar 	= (Coordenates) searchMap.get("rightBar");
				left 		= (Coordenates) searchMap.get("left");
				verticalBar = (Coordenates) searchMap.get("verticalBar");
				center		= (Coordenates) searchMap.get("center");
				break;
		}
	}
	
	public void setView(int workspace) {
		saveCoordenates();
		disableView();
		switch (workspace) {
			case DESKTOP :
				int navigatorView = Main.get().mainPanel.navigator.getStackIndex();
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
		}
		getCoordenates();
		enableView();
	}
	
	private void disableView() {
		switch (actualView) {
			case DESKTOP :
				navigator.setVisible(false);
				dockPanel.remove(browser);
				break;
				
			case SEARCH :
				historySearch.setVisible(false);
				dockPanel.remove(search);
				break;
				
			case DASHBOARD:
				dockPanel.remove(dashboard);
				break;
			
			case ADMINISTRATION:
				dockPanel.remove(administration);
				break;
		}
	}
	
	private void enableView() {
		switch (actualView) {
			case DESKTOP :
				// Ensures verticalBarPanel is visible
				verticalBarPanel.setVisible(true);
				navigator.setVisible(true);
				dockPanel.add(browser,DockPanel.CENTER);
				break;
				
			case SEARCH :
				// Ensures verticalBarPanel is visible
				verticalBarPanel.setVisible(true);
				historySearch.setVisible(true);
				dockPanel.add(search,DockPanel.CENTER);
				break;
		
			case DASHBOARD :
				// Special case must set univisible verticalBarPanel, because main area is not divided
				verticalBarPanel.setVisible(false);
				dockPanel.add(dashboard,DockPanel.CENTER);
				break;
			
			case ADMINISTRATION :
				// Special case must set univisible verticalBarPanel, because main area is not divided
				verticalBarPanel.setVisible(false);
				dockPanel.add(administration,DockPanel.CENTER);
				break;
		}
	}	
	
	public int getActualView(){
		return actualView;
	}
	
	public int getLeftPanelHeight() {
		return left.getHeight();
	}
	
	public Coordenates getLeftCoordenates() {
		return left;
	}
	
	public Coordenates getVerticalBarCoordenates() {
		return verticalBar;
	}
	
	public Coordenates getCenterCoordenates() {
		return center;
	}
	
	public void enableKeyShorcuts() {
		Log.debug("ExtendedDockPanel enableKeyShorcuts");
		dockPanel.sinkEvents(Event.ONKEYDOWN);
		keyBoardShorcuts = new EventPreview() {
				public boolean onEventPreview(Event event) {
					boolean propagate = true;
					int type = DOM.eventGetType(event);
					if (type == Event.ONKEYDOWN )  {
						int keyCode = DOM.eventGetKeyCode(event);
						switch (keyCode) {
							case Keyboard.KEY_F2:
								if (actualView == DESKTOP && Main.get().activeFolderTree.isPanelSelected() && 
									Main.get().mainPanel.topPanel.toolBar.getToolBarOption().renameOption &&
									(Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TAXONOMY ||
									 Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_PERSONAL || 		
									 Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TEMPLATES ||
									 Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_MAIL)) {
									
									Main.get().activeFolderTree.rename();
									propagate = false;
								} else if (Main.get().mainPanel.topPanel.toolBar.getToolBarOption().renameOption && (
										   Main.get().mainPanel.browser.fileBrowser.isDocumentSelected() || 
										   Main.get().mainPanel.browser.fileBrowser.isFolderSelected()) ) {
									
									Main.get().mainPanel.browser.fileBrowser.rename();
									propagate = false;
								}
								break;
							
							case Keyboard.KEY_SUPR:
								if (actualView == DESKTOP && Main.get().activeFolderTree.isPanelSelected() && 
									Main.get().mainPanel.topPanel.toolBar.getToolBarOption().deleteOption &&
									(Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TAXONOMY ||
									 Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_PERSONAL || 		
									 Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TEMPLATES ||
									 Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_MAIL)) {
										
									Main.get().mainPanel.topPanel.toolBar.executeDelete();
									propagate = false;
								} else if (Main.get().mainPanel.topPanel.toolBar.getToolBarOption().deleteOption && (
										   Main.get().mainPanel.browser.fileBrowser.isDocumentSelected() || 
										   Main.get().mainPanel.browser.fileBrowser.isFolderSelected() ||
										   Main.get().mainPanel.browser.fileBrowser.isMailSelected())) {
									
									Main.get().mainPanel.topPanel.toolBar.executeDelete();
									propagate = false;
								}
								break;
							
							case Keyboard.KEY_C:
							case Keyboard.KEY_X:
								// Case CTRL + C
								if (DOM.eventGetCtrlKey(event)) {
									if (actualView == DESKTOP && Main.get().activeFolderTree.isPanelSelected() && 
											Main.get().mainPanel.topPanel.toolBar.getToolBarOption().copyOption &&
											(Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TAXONOMY ||
											 Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_PERSONAL || 		
											 Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TEMPLATES ||
											 Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_MAIL)) {
										
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
												folderSelectPopup.setOriginPanel(Main.get().mainPanel.navigator.getStackIndex()); 
												TreeItem actualItem = Main.get().activeFolderTree.getActualItem();
												folderSelectPopup.setTreeItemToBeDeleted(actualItem);
												folderSelectPopup.setToMove(folder);
												
												break;
										}
										propagate = false;
										
									} else if (Main.get().mainPanel.topPanel.toolBar.getToolBarOption().copyOption && 
											   (Main.get().mainPanel.browser.fileBrowser.isDocumentSelected() || 
											    Main.get().mainPanel.browser.fileBrowser.isFolderSelected() ||
											    Main.get().mainPanel.browser.fileBrowser.isMailSelected()) ) {
																				
										// Saves the document or folder to be copied
										folderSelectPopup.setEntryPoint(FolderSelectPopup.ENTRYPOINT_KEYBOARD);
										if (Main.get().mainPanel.browser.fileBrowser.isDocumentSelected()) {
											GWTDocument document = Main.get().mainPanel.browser.fileBrowser.getDocument();
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
										} else if (Main.get().mainPanel.browser.fileBrowser.isMailSelected()) {
											GWTMail mail = Main.get().mainPanel.browser.fileBrowser.getMail();
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
											GWTFolder folder = Main.get().mainPanel.browser.fileBrowser.getFolder();
											switch (keyCode) {
												case Keyboard.KEY_C:
													Main.get().mainPanel.bottomPanel.setStatus("status.folder.copied",false);
													folderSelectPopup.setToCopy(folder);
													break;
												case Keyboard.KEY_X:
													Main.get().mainPanel.bottomPanel.setStatus("status.folder.cut",false);
													// Sets the origin panel and treeitem to be removed after move
													folderSelectPopup.setOriginPanel(Main.get().mainPanel.navigator.getStackIndex()); 
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
								if (DOM.eventGetCtrlKey(event) && 
									(folderSelectPopup.getAction()== FolderSelectPopup.ACTION_COPY ||
									 folderSelectPopup.getAction()== FolderSelectPopup.ACTION_MOVE )) {
									
									// Destination folder is always selected by tree
									if (actualView == DESKTOP && Main.get().activeFolderTree.isPanelSelected() && 
										(Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TAXONOMY ||
										 Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_PERSONAL || 		
										 Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_TEMPLATES || 
										 Main.get().mainPanel.navigator.getStackIndex()==PanelDefinition.NAVIGATOR_MAIL)) {
										
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
								if (DOM.eventGetCtrlKey(event) && actualView == DESKTOP && 
									Main.get().mainPanel.topPanel.toolBar.getToolBarOption().downloadOption) {
									Main.get().mainPanel.topPanel.toolBar.executeDownload();
									propagate = false;
								}
								break;
								
							case Keyboard.KEY_Z:
								// Case CTRL+Z
								if (DOM.eventGetCtrlKey(event)) {
									
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
								if (DOM.eventGetCtrlKey(event) && actualView == DESKTOP && 
									Main.get().mainPanel.topPanel.toolBar.getToolBarOption().createDirectoryOption) {
									Main.get().mainPanel.topPanel.toolBar.executeCreateDirectory();
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
								if (DOM.eventGetAltKey(event) && Main.get().mainPanel.topPanel.mainMenu.getToolBarOption().homeOption) {
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
								if (DOM.eventGetCtrlKey(event)) {
									Main.get().logoutPopup.logout();
								}
								break;
						}
					}
					
					// Not propagates event to the browser
					if (!propagate) {
						DOM.eventPreventDefault(event);
					}
					return propagate;
				}
		};
		
		DOM.addEventPreview(keyBoardShorcuts);
	}
	
	/**
	 * Disables key shortcuts
	 */
	public void disableKeyShorcuts() {
		Log.debug("ExtendedDockPanel disableKeyShorcuts");
		dockPanel.unsinkEvents(Event.ONKEYDOWN);
		DOM.removeEventPreview(keyBoardShorcuts);
	}
}