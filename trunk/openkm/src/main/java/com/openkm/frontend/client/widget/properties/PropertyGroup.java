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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTFormElement;
import com.openkm.frontend.client.bean.GWTPermission;
import com.openkm.frontend.client.service.OKMPropertyGroupService;
import com.openkm.frontend.client.service.OKMPropertyGroupServiceAsync;
import com.openkm.frontend.client.widget.ConfirmPopup;
import com.openkm.frontend.client.widget.propertygroup.PropertyGroupWidget;
import com.openkm.frontend.client.widget.propertygroup.PropertyGroupWidgetToFire;
import com.openkm.frontend.client.widget.propertygroup.WidgetToFire;

/**
 * PropertyGroup
 * 
 * @author jllort
 *
 */
public class PropertyGroup extends Composite {
	
	private final OKMPropertyGroupServiceAsync propertyGroupService = (OKMPropertyGroupServiceAsync) GWT.create(OKMPropertyGroupService.class);
	private final static String MAP_LIST_VALUES = "_LIST_VALUES";
	
	private ScrollPanel scrollPanel;
	PropertyGroupWidget propertyGroupWidget;
	private String grpName;
	private String grpLabel;
	private GWTDocument doc;
	private Button changeButton;
	private Button removeButton;
	private Button cancelButton;
	private Map<String, String[]> hProperties = new HashMap<String, String[]>();
	private Collection<GWTFormElement> hMetaData = new ArrayList<GWTFormElement>();
	private HashMap<String, Widget> hWidgetProperties = new HashMap<String, Widget>();
	private boolean editValues = false;
	private CellFormatter cellFormatter;
	private FiredHorizontalPanel hPanelFired;
	
	/**
	 * PropertyGroup
	 */
	public PropertyGroup(String grpName, String groupLabel, GWTDocument doc, GWTFolder folder, boolean visible) {	
		hPanelFired = new FiredHorizontalPanel();
		propertyGroupWidget = new PropertyGroupWidget(doc.getPath(), grpName, hPanelFired, hPanelFired);
		scrollPanel = new ScrollPanel(propertyGroupWidget);
		this.grpName = grpName;
		this.grpLabel = groupLabel;
		this.doc = doc;
		
		changeButton = new Button(Main.i18n("button.change"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (!editValues) {
					Main.get().mainPanel.disableKeyShorcuts(); // Disables key shortcuts while updating
					changeButton.setHTML(Main.i18n("button.memory"));
					cancelButton.setVisible(true);
					edit();
					editValues = true;
					removeButton.setVisible(false);
				} else {
					Main.get().mainPanel.enableKeyShorcuts(); // Enables general keys applications
					changeButton.setHTML(Main.i18n("button.change"));
					setProperties();
					editValues = false;
					removeButton.setVisible(true);
					cancelButton.setVisible(false);
				}
			}
		});
		
		removeButton = new Button(Main.i18n("button.delete"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (!editValues) {
					Main.get().confirmPopup.setConfirm(ConfirmPopup.CONFIRM_DELETE_DOCUMENT_PROPERTY_GROUP);
					Main.get().confirmPopup.show();
				} 
			}
		});
		
		cancelButton = new Button(Main.i18n("button.cancel"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (editValues) {
					Main.get().mainPanel.enableKeyShorcuts(); // Enables general keys applications
					changeButton.setHTML(Main.i18n("button.change"));
					editValues = false;
					cancelEdit();
					removeButton.setVisible(true);
					cancelButton.setVisible(false);
				} 
			}
		});
		
		// Checking button permisions
		if (!visible) {
			changeButton.setVisible(visible);
			removeButton.setVisible(visible);
			
		} else if ( ((doc.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE) &&
			     ((folder.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE))  {
	    	 		changeButton.setVisible(true);
	    	 		removeButton.setVisible(true);
	    	 		
		} else {
			changeButton.setVisible(false);
			removeButton.setVisible(false);
		}
		
		hPanelFired.add(changeButton);
		hPanelFired.add(new HTML("&nbsp;&nbsp;"));
		hPanelFired.add(cancelButton);
		hPanelFired.add(new HTML("&nbsp;&nbsp;"));
		hPanelFired.add(removeButton);
		
		cancelButton.setVisible(false);  // Not shows cancel button
		
		// Button format
		changeButton.setStyleName("okm-Button");
		removeButton.setStyleName("okm-Button");
		cancelButton.setStyleName("okm-Button");

		getProperties();
		
		initWidget(scrollPanel);
	}
	
	/**
	 * Lang refresh
	 */
	public void langRefresh() {
		changeButton.setHTML(Main.i18n("button.change"));
		removeButton.setHTML(Main.i18n("button.delete"));		
	}	
	
	/**
	 * Gets all group properties 
	 */
	private void getProperties() {
		GWTDocument gwtDocument = Main.get().mainPanel.browser.fileBrowser.getDocument();
		if (gwtDocument!= null) {
			propertyGroupWidget.getProperties();
		}
	}

	/**
	 * Remove the document property group
	 */
	public void removeGroup() {
		GWTDocument gwtDocument = Main.get().mainPanel.browser.fileBrowser.getDocument();
		if (gwtDocument!= null) {
			propertyGroupWidget.removeGroup();
		}
	}
	
	/**
	 * set document property group
	 */
	public void setProperties() {
		GWTDocument gwtDocument = Main.get().mainPanel.browser.fileBrowser.getDocument();
		if (gwtDocument!= null) {
			Main.get().mainPanel.browser.tabMultiple.status.setGroupProperties();
			propertyGroupWidget.setProperties();
		}
	}
	
	/**
	 * edit document property group
	 */
	public void edit() {
		GWTDocument gwtDocument = Main.get().mainPanel.browser.fileBrowser.getDocument();
		if (gwtDocument!= null) {
			propertyGroupWidget.edit();
		}
	}
	
	/**
	 * edit document property group
	 */
	public void cancelEdit() {
		GWTDocument gwtDocument = Main.get().mainPanel.browser.fileBrowser.getDocument();
		if (gwtDocument!= null) {
			propertyGroupWidget.cancelEdit();
		}
	}
	
	/**
	 * Gets the group name
	 * 
	 * @return The group name
	 */
	public String getGrpName(){
		return grpName;
	}
	
	/**
	 * Gets the group label
	 * 
	 * @return The group label
	 */
	public String getGrpLabel(){
		return grpLabel;
	}
	
	/**
	 * FiredVerticalPanel
	 * 
	 * @author jllort
	 *
	 */
	private class FiredHorizontalPanel extends PropertyGroupWidgetToFire implements WidgetToFire {
		private HorizontalPanel hPanel;
		
		public FiredHorizontalPanel() {
			hPanel = new HorizontalPanel();
			initWidget(hPanel);
			
		}
		
		@Override
		public void finishedGetProperties() {
		}
		
		@Override
		public void finishedSetProperties() {
			Main.get().mainPanel.browser.tabMultiple.status.unsetGroupProperties();
		}
		
		/**
		 * add
		 * 
		 * @param widget
		 */
		public void add(Widget widget) {
			hPanel.add(widget);
		}
	}
}
