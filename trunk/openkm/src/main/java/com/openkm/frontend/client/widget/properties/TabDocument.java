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

package com.openkm.frontend.client.widget.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTPermission;
import com.openkm.frontend.client.bean.GWTPropertyGroup;
import com.openkm.frontend.client.config.Config;
import com.openkm.frontend.client.service.OKMPropertyGroupService;
import com.openkm.frontend.client.service.OKMPropertyGroupServiceAsync;

/**
 * The tab document
 * 
 * @author jllort
 *
 */
public class TabDocument extends Composite {
	
	public static final int TAB_PREVIEW = 4;
	
	private final OKMPropertyGroupServiceAsync propertyGroupService = (OKMPropertyGroupServiceAsync) GWT.create(OKMPropertyGroupService.class);

	public TabPanel tabPanel;
	public Document document;
	public VersionScrollTable version;
	public SecurityScrollTable security;
	private VerticalPanel panel;
	private List<PropertyGroup> propertyGroup;
	private GWTDocument doc;
	public Notes notes;
	private Preview preview;
	private int selectedTab = 0; // Used to determine selected tab to mantain on change document, because not all documents
								 // have the same numeber of tabs ( document group properties are variable ) 
	private boolean visibleButton = true; // Sets visibleButtons enabled to default view 
	
	/**
	 * The Document tab
	 */
	public TabDocument() {
		tabPanel = new TabPanel();
		document = new Document();
		notes = new Notes();
		version = new VersionScrollTable();
		security = new SecurityScrollTable();
		preview = new Preview();
		panel = new VerticalPanel();
		propertyGroup = new ArrayList<PropertyGroup>();

		tabPanel.add(document, Main.i18n("tab.document.properties"));
		tabPanel.add(notes, Main.i18n("tab.document.notes"));
		tabPanel.add(version, Main.i18n("tab.document.history"));
		tabPanel.add(security, Main.i18n("tab.document.security"));
		tabPanel.add(preview, Main.i18n("tab.document.preview"));
		
		tabPanel.selectTab(0);
		tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int tabIndex = event.getSelectedItem().intValue();
				Main.get().mainPanel.topPanel.toolBar.evaluateRemoveGroupProperty(isSelectedTabGroupPropety(tabIndex));
				selectedTab = tabIndex;
				if (tabIndex == TAB_PREVIEW) {
					preview.showEmbedSWF(doc.getPath());
				}
			}
		});
		
		panel.add(tabPanel);
		tabPanel.setWidth("100%");
		document.setSize("100%", "100%");
		notes.setSize("100%", "100%");
		panel.setSize("100%", "100%");
		
		tabPanel.setStyleName("okm-DisableSelect");
		
		initWidget(panel);
	}
	
	/**
	 * Sets the size
	 * 
	 * @param width With of the widget
	 * @param height Height of the widget
	 */
	public void setSize(int width, int height) {
		tabPanel.setPixelSize(width, height);
		document.setPixelSize(width,height-20); // Substract tab height
		preview.setPixelSize(width,height-20); // Substract tab height
		notes.setPixelSize(width,height-20); // Substract tab height
		version.setPixelSize(width-2,height-22); // Substract tab height
		version.fillWidth();
		security.setPixelSize(width-2,height-22); // Substract tab height
		security.fillWidth();
		if (!propertyGroup.isEmpty()) {			 // Sets size to propety groups	
			for (Iterator<PropertyGroup> it = propertyGroup.iterator(); it.hasNext();){
				PropertyGroup group =  it.next();
				group.setPixelSize(width,height-20);
			}
		}
		if (selectedTab == TAB_PREVIEW) {
			preview.showEmbedSWF(doc.getPath());
		}
	}
	
	/**
	 * Sets document values
	 * 
	 * @param doc The document object
	 */
	public void setProperties(GWTDocument doc) {	
		this.doc = doc;
		selectedTab = tabPanel.getTabBar().getSelectedTab(); // Sets the actual selected Tab
		
		document.set(doc);
		notes.set(doc);
		version.set(doc);
		security.setPath(doc.getPath());
		version.getVersionHistory();
		security.GetGrants();
		preview.setPreviewAvailable(doc.isConvertibleToSwf());
		
		if ((doc.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE && !doc.isCheckedOut() && !doc.isLocked()) {
			security.setChangePermision(true);
		} else {
			security.setChangePermision(false);
		}
		
		if (!propertyGroup.isEmpty()) {
			for (Iterator<PropertyGroup> it = propertyGroup.iterator(); it.hasNext();){
				tabPanel.remove(it.next());
			}
			propertyGroup.clear();
		}

		getGroups(doc.getPath()); // Gets all the property group assigned to a document
							      // Here evalutates selectedTab
	}
	
	/**
	 * Refresh security values
	 */
	public void securityRefresh() {
		Main.get().mainPanel.browser.fileBrowser.securityRefresh();
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		TabBar tabBar = tabPanel.getTabBar();
		selectedTab = tabBar.getSelectedTab();
		
		while (tabPanel.getWidgetCount() > 0) {
			tabPanel.remove(0);
		}
		
		tabPanel.add(document, Main.i18n("tab.document.properties"));
		tabPanel.add(notes, Main.i18n("tab.document.notes"));
		tabPanel.add(version, Main.i18n("tab.document.history"));
		tabPanel.add(security, Main.i18n("tab.document.security"));
		tabPanel.add(preview, Main.i18n("tab.document.preview"));
		
		// Refresh lang property group
		if (!propertyGroup.isEmpty()) {
			for (Iterator<PropertyGroup> it = propertyGroup.iterator(); it.hasNext();){
				PropertyGroup group = it.next();
				tabPanel.add(group, group.getGrpName());
				group.langRefresh();
			}
		}		
		
		document.langRefresh();
		version.langRefresh();
		security.langRefresh();
		notes.langRefresh();
		preview.langRefresh();
		
		tabPanel.selectTab(selectedTab);
		
		resizingIncubatorWidgets();
	}
	
	/**
	 * Sets visibility to buttons ( true / false )
	 * 
	 * @param visible The visible value
	 */
	public void setVisibleButtons(boolean visible){
		this.visibleButton = visible;  // Save to be used by property group
		document.setVisibleButtons(visible);
		notes.setVisibleButtons(visible);
		version.setVisibleButtons(visible);
		security.setVisibleButtons(visible);
	}
	
	/**
	 * Gets asyncronous to get all groups assigned to a document
	 */
	final AsyncCallback<List<GWTPropertyGroup>> callbackGetGroups = new AsyncCallback<List<GWTPropertyGroup>>() {
		public void onSuccess(List<GWTPropertyGroup> result){
			GWTFolder gwtFolder = Main.get().activeFolderTree.getFolder();
			
			for (Iterator<GWTPropertyGroup> it = result.iterator(); it.hasNext();) {
				GWTPropertyGroup gwtGroup = it.next();
				String groupTranslation = gwtGroup.getLabel();
				PropertyGroup group = new PropertyGroup(gwtGroup.getName(), doc, gwtFolder, visibleButton);
				tabPanel.add(group, groupTranslation);
				propertyGroup.add(group);
			}
			// To prevent change on document that has minor tabs than previous the new selected tab it'll be the max - 1 on that cases
			if (tabPanel.getTabBar().getTabCount()-1<selectedTab) {
				tabPanel.selectTab(tabPanel.getTabBar().getTabCount()-1);
			} else {
				tabPanel.selectTab(selectedTab); // Always enable selected tab because on document change tab group are removed
												 // and on remove loses selectedTab
			}
			Main.get().mainPanel.browser.tabMultiple.status.unsetGroupProperties();
		}

		public void onFailure(Throwable caught) {
			Main.get().mainPanel.browser.tabMultiple.status.unsetGroupProperties();
			Main.get().showError("GetAllGroups", caught);
		}
	};
	
	/**
	 * Gets all property groups assigned to document
	 */
	private void getGroups(String docPath) {
		Main.get().mainPanel.browser.tabMultiple.status.setGroupProperties();
		ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
		endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
		propertyGroupService.getGroups(docPath, callbackGetGroups);
	}
	
	/**
	 * Removes the actual property group
	 */
	public void removePropertyGroup(){
		selectedTab = tabPanel.getTabBar().getSelectedTab(); // Sets the actual selectedted Tab
		
		// Removes group 
		PropertyGroup group = (PropertyGroup) tabPanel.getWidget(selectedTab);
		group.removeGroup();
		propertyGroup.remove(group);
		
		// Remove tab
		tabPanel.remove(selectedTab);
		
		// If removed tab is last the new selected tab is selectedTab -1
		if (tabPanel.getTabBar().getTabCount()-1<selectedTab) {
			selectedTab--;
		}
		
		// Sets the new selected tab
		tabPanel.selectTab(selectedTab);
		
	}
	
	/**
	 * Return if actual tab selected is group property
	 * 
	 * @return
	 */
	private boolean isSelectedTabGroupPropety(int tabIndex){
		return (tabPanel.getWidget(tabIndex) instanceof PropertyGroup);
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
				group.setPixelSize(getOffsetWidth()-2, getOffsetHeight()-22); // Substract tab height
			}
		}	
		version.setPixelSize(getOffsetWidth()-2, getOffsetHeight()-22); // Substract tab height
		security.setPixelSize(getOffsetWidth()-2, getOffsetHeight()-22); // Substract tab height
	}
}