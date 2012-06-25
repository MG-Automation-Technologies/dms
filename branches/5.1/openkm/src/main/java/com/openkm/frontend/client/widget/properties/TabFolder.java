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

package com.openkm.frontend.client.widget.properties;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTPermission;
import com.openkm.frontend.client.bean.GWTPropertyGroup;
import com.openkm.frontend.client.extension.event.HasFolderEvent;
import com.openkm.frontend.client.extension.event.handler.FolderHandlerExtension;
import com.openkm.frontend.client.extension.event.handler.PropertyGroupHandlerExtension;
import com.openkm.frontend.client.extension.event.hashandler.HasFolderHandlerExtension;
import com.openkm.frontend.client.extension.event.hashandler.HasPropertyGroupHandlerExtension;
import com.openkm.frontend.client.extension.widget.tabfolder.TabFolderExtension;
import com.openkm.frontend.client.service.OKMPropertyGroupService;
import com.openkm.frontend.client.service.OKMPropertyGroupServiceAsync;
import com.openkm.frontend.client.service.OKMRepositoryService;
import com.openkm.frontend.client.service.OKMRepositoryServiceAsync;
import com.openkm.frontend.client.util.Util;

/**
 * The tab folder
 * 
 * @author jllort
 *
 */
public class TabFolder extends Composite implements HasFolderEvent, HasFolderHandlerExtension, HasPropertyGroupHandlerExtension {
	
	private final OKMRepositoryServiceAsync repositoryService = (OKMRepositoryServiceAsync) GWT.create(OKMRepositoryService.class);
	private final OKMPropertyGroupServiceAsync propertyGroupService = (OKMPropertyGroupServiceAsync) GWT.create(OKMPropertyGroupService.class);
	
	private static final int TAB_HEIGHT = 20;
	private int SECURITY_TAB = -1;
	private int NOTES_TAB = -1;
	
	public TabLayoutPanel tabPanel;
	public Folder folder;
	private SecurityScrollTable security;
	public Notes notes;
	private VerticalPanel panel;
	private List<PropertyGroup> propertyGroup;
	private List<TabFolderExtension> widgetExtensionList;
	private List<FolderHandlerExtension> folderHandlerExtensionList;
	private List<PropertyGroupHandlerExtension> propertyGroupHandlerExtensionList;
	private boolean visibleButton = true; // Sets visibleButtons enabled to default view 
	private int selectedTab = 0; 
	private int latestSelectedTab = 0;
	private int height = 0;
	private int width = 0;
	private boolean propertiesVisible = false;
	private boolean securityVisible = false;
	private boolean notesVisible = false;
	private boolean propertyGroupsVisible = false;
	
	/**
	 * TabFolder
	 */
	public TabFolder() {
		widgetExtensionList = new ArrayList<TabFolderExtension>();
		folderHandlerExtensionList = new ArrayList<FolderHandlerExtension>();
		propertyGroupHandlerExtensionList = new ArrayList<PropertyGroupHandlerExtension>();
		tabPanel = new TabLayoutPanel(TAB_HEIGHT, Unit.PX);
		folder = new Folder();
		security = new SecurityScrollTable();
		notes = new Notes(Notes.FOLDER_NOTE);
		panel = new VerticalPanel();
		propertyGroup = new ArrayList<PropertyGroup>();
		
		tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int tabIndex = event.getSelectedItem().intValue();
				Main.get().mainPanel.topPanel.toolBar.evaluateRemovePropertyGroup(isRemovePropertyGroupEnabled(tabIndex));
				selectedTab = tabIndex;
				if (tabIndex==SECURITY_TAB) {
					Timer timer = new Timer() {
						@Override
						public void run() {
							security.fillWidth(); // Always when shows fires fill width
						}
					};
					timer.schedule(50); // Fill width must be done after really it'll be visible
				}
				// Solves chrome bugt
				if (tabIndex==NOTES_TAB && Util.getUserAgent().startsWith("safari")) {
					Timer timer = new Timer() {
						@Override
						public void run() {
							notes.richTextArea.setFocus(true);
						}
					};
					timer.schedule(50); // Fill width must be done after really it'll be visible
				}
				fireEvent(HasFolderEvent.TAB_CHANGED);
			}
		});
		panel.add(tabPanel);
		
		tabPanel.setWidth("100%");
		folder.setSize("100%", "100%");
		notes.setSize("100%", "100%");
		panel.setSize("100%", "100%");
		
		tabPanel.setStyleName("okm-DisableSelect");
		
		// On init get root node folder information
		getRoot();
		
		initWidget(panel);
	}

	/**
	 * Sets the size
	 * 
	 * @param width With of the widget
	 * @param height Height of the widget
	 */
	public void setPixelSize(int width, int height) {
		this.height = height;
		this.width = width;
		tabPanel.setPixelSize(width, height);
		folder.setPixelSize(width,height-TAB_HEIGHT); // Substract tab height
		security.setPixelSize(width,height-TAB_HEIGHT); // Substract tab height
		notes.setPixelSize(width,height-TAB_HEIGHT); // Substract tab height
		security.fillWidth();
		
		// Setting size to extension
		for (Iterator<TabFolderExtension> it = widgetExtensionList.iterator(); it.hasNext();) {
			it.next().setPixelSize(width,height-TAB_HEIGHT);
		}
		
		if (!propertyGroup.isEmpty()) {			 // Sets size to propety groups	
			for (Iterator<PropertyGroup> it = propertyGroup.iterator(); it.hasNext();){
				PropertyGroup group =  it.next();
				group.setPixelSize(width,height-TAB_HEIGHT);
			}
		}
		
		fireEvent(HasFolderEvent.PANEL_RESIZED);
	}
	
	/**
	 * Sets the folder values
	 * 
	 * @param folder The folder object
	 */
	public void setProperties(GWTFolder folder) {
		this.folder.set(folder); // Used by tabFolderCommunicator
		notes.set(folder);	   	 // Used by TabFolderCommunicator
		
		selectedTab = tabPanel.getSelectedIndex(); 	// Sets the actual selected Tab
		latestSelectedTab = selectedTab; 			// stores latest selected tab
		
		if (securityVisible) {
			security.setPath(folder.getPath());
			security.GetGrants();
			
			if ((folder.getPermissions() & GWTPermission.SECURITY) == GWTPermission.SECURITY) {
				security.setChangePermision(true);
			} else {
				security.setChangePermision(false);
			}
		}
		if (propertyGroupsVisible) {
			Main.get().mainPanel.desktop.browser.tabMultiple.status.setGroupProperties();
		}
		
		if (!propertyGroup.isEmpty()) {
			for (Iterator<PropertyGroup> it = propertyGroup.iterator(); it.hasNext();){
				tabPanel.remove(it.next());
			}
			propertyGroup.clear();
		}
		
		// Only gets groups if really are visible
		if (propertyGroupsVisible) {
			getGroups(folder.getPath()); // Gets all the property group assigned to a document
									  	 // Here evalutates selectedTab
		}
		
		// Setting folder object to extensions
		for (Iterator<TabFolderExtension> it = widgetExtensionList.iterator(); it.hasNext();) {
			it.next().set(folder);
		}
		
		fireEvent(HasFolderEvent.FOLDER_CHANGED);
	}
	
	/**
	 * Refresh security values
	 */
	public void securityRefresh() {
		// Determines if folder security changed is tree or filebrowser, if filebrowser has no selectedRow then
		// must refresh tree node selected to reflect security changes ( icon color )
		// this remote methods also refresh properties (remote call to setProperties ).
		if (Main.get().mainPanel.desktop.browser.fileBrowser.isSelectedRow()) {
			Main.get().mainPanel.desktop.browser.fileBrowser.securityRefresh();
		} else {
			Main.get().activeFolderTree.securityRefresh();
		}
		fireEvent(HasFolderEvent.SECURITY_CHANGED);
	}
	
	/**
	 * Refresh language
	 */
	public void langRefresh() {
		selectedTab = tabPanel.getSelectedIndex();
		
		while (tabPanel.getWidgetCount() > 0) {
			tabPanel.remove(0);
		}
		
		if (propertiesVisible) {
			tabPanel.add(folder, Main.i18n("tab.folder.properties"));
			folder.langRefresh();
		}
		if (notesVisible) {
			tabPanel.add(notes, Main.i18n("tab.folder.notes"));
			notes.langRefresh();
		} 
		if (securityVisible) {
			tabPanel.add(security, Main.i18n("tab.folder.security"));
			security.langRefresh();
		}
		
		// Adding extensions
		for (Iterator<TabFolderExtension> it = widgetExtensionList.iterator(); it.hasNext();) {
			TabFolderExtension extension = it.next();
			tabPanel.add(extension, extension.getTabText());
		}
		
		// Refresh lang property group
		if (!propertyGroup.isEmpty()) {
			for (Iterator<PropertyGroup> it = propertyGroup.iterator(); it.hasNext();){
				PropertyGroup group = it.next();
				tabPanel.add(group, group.getGrpLabel());
				group.langRefresh();
			}
		}	
		
		tabPanel.selectTab(selectedTab);
		
		resizingIncubatorWidgets();
	}
	
	/**
	 * Gets asyncronous root node
	 */
	final AsyncCallback<GWTFolder> callbackGetRootFolder = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			//Only executes on initalization and the actualItem is root element on initialization
			//We put the id on root
			setProperties(result);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetRootFolder", caught);
		}
	};
	
	/**
	 * Gets the root
	 */
	public void getRoot() {	
		repositoryService.getRootFolder(callbackGetRootFolder);
	}
	
	/**
	 * Sets visibility to buttons ( true / false )
	 * 
	 * @param visible The visible value
	 */
	public void setVisibleButtons(boolean visible) {
		this.visibleButton = visible;
		security.setVisibleButtons(visible);
		notes.setVisibleButtons(visible);
		
		fireEvent(HasFolderEvent.SET_VISIBLE_BUTTON);
	}
	
	/**
	 * isVisibleButton
	 * 
	 * @return
	 */
	public boolean isVisibleButton() {
		return visibleButton;
	}
	
	/**
	 * Gets asynchronous to get all groups assigned to a document
	 */
	final AsyncCallback<List<GWTPropertyGroup>> callbackGetGroups = new AsyncCallback<List<GWTPropertyGroup>>() {
		public void onSuccess(List<GWTPropertyGroup> result){
			GWTFolder gwtFolder = Main.get().activeFolderTree.getFolder();
			
			for (Iterator<GWTPropertyGroup> it = result.iterator(); it.hasNext();) {
				GWTPropertyGroup gwtGroup = it.next();
				String groupTranslation = gwtGroup.getLabel();
				PropertyGroup group = new PropertyGroup(gwtGroup, folder.get(), gwtFolder, visibleButton, gwtGroup.isReadonly());
				tabPanel.add(group, groupTranslation);
				propertyGroup.add(group);
				
				// Adds property group handlers
				for (Iterator<PropertyGroupHandlerExtension> itx = propertyGroupHandlerExtensionList.iterator(); itx.hasNext();) {
					group.addPropertyGroupHandlerExtension(itx.next());
				}
			}
			
			// To prevent change on document that has minor tabs than previous the new selected tab it'll be the max - 1 on that cases
			if (tabPanel.getWidgetCount()-1<latestSelectedTab) {
				tabPanel.selectTab(tabPanel.getWidgetCount()-1);
			} else {
				tabPanel.selectTab(latestSelectedTab); // Always enable selected tab because on document change tab group are removed
												 // and on remove loses selectedTab
			}
			
			Main.get().mainPanel.desktop.browser.tabMultiple.status.unsetGroupProperties();
		}

		public void onFailure(Throwable caught) {
			Main.get().mainPanel.desktop.browser.tabMultiple.status.unsetGroupProperties();
			Main.get().showError("GetGroups", caught);
		}
	};
	
	/**
	 * Gets all property groups assigned to document
	 */
	private void getGroups(String docPath) {
		Main.get().mainPanel.desktop.browser.tabMultiple.status.setGroupProperties();
		propertyGroupService.getGroups(docPath, callbackGetGroups);
	}
	
	/**
	 * Removes the actual property group
	 */
	public void removePropertyGroup(){
		selectedTab = tabPanel.getSelectedIndex(); // Sets the actual selectedted Tab
		
		// Removes group 
		PropertyGroup group = (PropertyGroup) tabPanel.getWidget(selectedTab);
		group.removeGroup();
		propertyGroup.remove(group);
		
		// Remove tab
		tabPanel.remove(selectedTab);
		
		// If removed tab is last the new selected tab is selectedTab -1
		if (tabPanel.getWidgetCount()-1<selectedTab) {
			selectedTab--;
		}
		
		// Sets the new selected tab
		tabPanel.selectTab(selectedTab);
		
	}
	
	/**
	 * Return if actual tab selected is group property and can be removed
	 * 
	 * @return
	 */
	private boolean isRemovePropertyGroupEnabled(int tabIndex) {
		if ((tabPanel.getWidget(tabIndex) instanceof PropertyGroup)) {
			return ((PropertyGroup) (tabPanel.getWidget(tabIndex))).isRemovePropertyGroupEnabled();
		} else {
			return false;
		}
	}
	
	/**
	 * resizingIncubatorWidgets 
	 * 
	 * Needs resizing if not widgets disapears
	 */
	public void resizingIncubatorWidgets() {
		if (!propertyGroup.isEmpty()) {
			for (Iterator<PropertyGroup> it = propertyGroup.iterator(); it.hasNext();){
				PropertyGroup group = it.next();
				group.setPixelSize(getOffsetWidth(), getOffsetHeight()-TAB_HEIGHT); // Substract tab height
			}
		}	
		security.setPixelSize(getOffsetWidth(), getOffsetHeight()-TAB_HEIGHT); // Substract tab height
		security.fillWidth();
		// TODO:Solves minor bug with IE
		if (Util.getUserAgent().startsWith("ie")) {
			Timer timer = new Timer() {
				@Override
				public void run() {
					tabPanel.setWidth(""+width);
					tabPanel.setWidth(""+(width+1));
					Timer timer = new Timer() {
						@Override
						public void run() {
							tabPanel.setWidth(""+width);
						}
					};
					timer.schedule(50);
				}
			};
			timer.schedule(100);
		}
	}
	
	/**
	 * getSelectedTab
	 * 
	 * @return
	 */
	public int getSelectedTab() {
		return selectedTab;
	}
	
	/**
	 * getFolder
	 * 
	 * @return
	 */
	public GWTFolder getFolder() {
		return folder.get();
	}
	
	/**
	 * resetNumericFolderValues
	 */
	public void resetNumericFolderValues() {
		folder.resetNumericFolderValues();
	}
	
	/**
	 * setNumberOfFolders
	 */
	public void setNumberOfFolders(int num) {
		folder.setNumberOfFolders(num);
	}
	
	/**
	 * setNumberOfDocuments
	 */
	public void setNumberOfDocuments(int num) {
		folder.setNumberOfDocuments(num);
	}
	
	/**
	 * setNumberOfMails
	 */
	public void setNumberOfMails(int num) {
		folder.setNumberOfMails(num);
	}
	
	/**
	 * showProperties
	 */
	public void showProperties() {
		tabPanel.add(folder, Main.i18n("tab.folder.properties"));
		propertiesVisible = true;
	}
	
	/**
	 * showNotes
	 */
	public void showNotes() {
		tabPanel.add(notes, Main.i18n("tab.document.notes"));
		notesVisible = true;
		NOTES_TAB = tabPanel.getWidgetCount()-1; // Starts at 0
	}
	
	/**
	 * showSecurity
	 */
	public void showSecurity() {
		tabPanel.add(security, Main.i18n("tab.folder.security"));
		securityVisible = true;
		SECURITY_TAB = tabPanel.getWidgetCount()-1; // Starts at 0
	}
	
	/**
	 * showExtensions
	 */
	public void showExtensions() {
		for (TabFolderExtension extension : widgetExtensionList){
			tabPanel.add(extension, extension.getTabText());
			extension.setPixelSize(width,height-TAB_HEIGHT);
		}
	}
	
	/**
	 * showPropertyGroups
	 */
	public void showPropertyGroups() {
		propertyGroupsVisible = true;
	}
	
	/**
	 * init
	 */
	public void init() {
		if (tabPanel.getWidgetCount()>0) {
			tabPanel.selectTab(0);
			
			if (securityVisible && folder.get()!=null) {
				security.setPath(folder.get().getPath());
				security.GetGrants();
				
				if ((folder.get().getPermissions() & GWTPermission.SECURITY) == GWTPermission.SECURITY) {
					security.setChangePermision(true);
				} else {
					security.setChangePermision(false);
				}
			}
		}
	}
	
	/**
	 * addFolderExtension
	 * 
	 * @param extension
	 */
	public void addFolderExtension(TabFolderExtension extension) {
		widgetExtensionList.add(extension);
	}
	
	@Override
	public void addFolderHandlerExtension(FolderHandlerExtension handlerExtension) {
		folderHandlerExtensionList.add(handlerExtension);
	}

	@Override
	public void fireEvent(FolderEventConstant event) {
		for (Iterator<FolderHandlerExtension> it = folderHandlerExtensionList.iterator(); it.hasNext(); ) {
			it.next().onChange(event);
		}
	}
	
	@Override
	public void addPropertyGroupHandlerExtension(PropertyGroupHandlerExtension handlerExtension) {
		propertyGroupHandlerExtensionList.add(handlerExtension);
	}
}