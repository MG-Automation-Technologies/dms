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

package es.git.openkm.frontend.client.widget.searchin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTMetaData;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMPropertyGroupService;
import es.git.openkm.frontend.client.service.OKMPropertyGroupServiceAsync;

/**
 * Group popup
 * 
 * @author jllort
 *
 */
public class GroupPopup extends DialogBox {
	
	private final OKMPropertyGroupServiceAsync propertyGroupService = (OKMPropertyGroupServiceAsync) GWT.create(OKMPropertyGroupService.class);
	
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private Button button;
	private Button addButton;
	private ListBox groupListBox;
	private ListBox propertyListBox;
	private Map<String, GWTMetaData> hMetaData = new HashMap<String,GWTMetaData>();
	private FlexTable table;
	private Label groupLabel;
	private Label propertyLabel;
	private int validate = -1;
	
	/**
	 * About popup
	 */
	public GroupPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		
		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		groupLabel = new Label(Main.i18n("group.group"));
		propertyLabel = new Label(Main.i18n("group.property.group"));
		table = new FlexTable();
		
		button = new Button(Main.i18n("button.close"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		addButton = new Button(Main.i18n("button.add"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (propertyListBox.getSelectedIndex()>0) {
					String grpName = groupListBox.getValue(groupListBox.getSelectedIndex());
					String propertyName = propertyListBox.getValue(propertyListBox.getSelectedIndex());
					Main.get().mainPanel.search.searchIn.addProperty(grpName, propertyName,(GWTMetaData) hMetaData.get(propertyName), "");
				}
				enableAddGroupButton(); // Enables or disables add group button ( if exist some property still to be added )
				hide();
			}
		});

		groupListBox = new ListBox();
		groupListBox.addChangeHandler(new ChangeHandler(){
			@Override
			public void onChange(ChangeEvent event) {
				if (groupListBox.getSelectedIndex()>0) {
					propertyListBox.clear();
					getMetaData();
				} else {
					propertyListBox.clear();
					propertyListBox.setVisible(false);
					propertyLabel.setVisible(false);
					addButton.setEnabled(false);
				}
			}
		});
		groupListBox.setStyleName("okm-Select");
		
		propertyListBox = new ListBox();
		propertyListBox.addChangeHandler(new ChangeHandler(){
			@Override
			public void onChange(ChangeEvent event) {
				if (propertyListBox.getSelectedIndex()>0) {
					addButton.setEnabled(true);
				} else {
					addButton.setEnabled(false);
				}
			}
		});
		propertyListBox.setStyleName("okm-Select");
		propertyListBox.setVisible(false);
		propertyLabel.setVisible(false);
		
		vPanel.setWidth("300px");
		vPanel.setHeight("100px");
		button.setStyleName("okm-Button");
		addButton.setStyleName("okm-Button");
		addButton.setEnabled(false);
		
		hPanel.add(button);
		hPanel.add(new HTML("&nbsp;&nbsp;"));
		hPanel.add(addButton);
		
		hPanel.setCellHorizontalAlignment(button,VerticalPanel.ALIGN_CENTER);
		hPanel.setCellHorizontalAlignment(addButton,VerticalPanel.ALIGN_CENTER);
		
		table.setWidget(0,0,groupLabel);
		table.setWidget(0,1,groupListBox);
		table.setWidget(1,0,propertyLabel);
		table.setWidget(1,1,propertyListBox);

		vPanel.add(new HTML("<br>"));
		vPanel.add(table);
		vPanel.add(new HTML("<br>"));
		vPanel.add(hPanel);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(table, VerticalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hPanel, VerticalPanel.ALIGN_CENTER);

		super.hide();
		setWidget(vPanel);
	}
	
	/**
	 * Gets asyncronous to get all groups
	 */
	final AsyncCallback<List<String>> callbackGetAllGroups = new AsyncCallback<List<String>>() {
		public void onSuccess(List<String> result){
			groupListBox.clear();
			groupListBox.addItem("",""); // Adds empty value
			for (Iterator<String> it = result.iterator(); it.hasNext();) {
				String groupKey = it.next();
				String groupTranslation = Main.propertyGroupI18n(groupKey);
				groupListBox.addItem(groupTranslation,groupKey);
			}
			validate = 1;
			validateGroupsNoEmpty(); // Enables or disables add group button ( case exist some value to be added on list )
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetAllGroups", caught);
		}
	};
	
	/**
	 * Gets asyncronous to get metada group properties
	 */
	final AsyncCallback<Map<String, GWTMetaData>> callbackGetMetaData = new AsyncCallback<Map<String, GWTMetaData>>() {
		public void onSuccess(Map<String, GWTMetaData> result){
			propertyListBox.clear();
			propertyListBox.setVisible(true);
			propertyLabel.setVisible(true);
			propertyListBox.addItem("",""); // First item is always blank
			
			Collection<String> actualProperties = Main.get().mainPanel.search.searchIn.getActualProperties();

			for (Iterator<String> it = result.keySet().iterator(); it.hasNext();) {
				String propertyName = (String) it.next();
				if (!actualProperties.contains(propertyName)) { // Only appears properties not stil added
					propertyListBox.addItem(Main.propertyGroupI18n(propertyName),propertyName);
				}
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getMetaData", caught);
		}
	};
	
	/**
	 * Gets asyncronous to get metada group properties and validate is there's one not assigned
	 */
	final AsyncCallback<Map<String, GWTMetaData>> callbackGetMetaDataToValidate = new AsyncCallback<Map<String, GWTMetaData>>() {
		public void onSuccess(Map<String, GWTMetaData> result){

			Collection<String> actualProperties = Main.get().mainPanel.search.searchIn.getActualProperties();
			boolean found = false;
			
			for (Iterator<String> it = result.keySet().iterator(); it.hasNext();) {
				String propertyName = it.next();
				if (!actualProperties.contains(propertyName)) { // Only appears properties not stil added
					found = true;
				}
			}
			
			// Removes the item on list
			if (!found) {
				groupListBox.removeItem(validate); // When removing object it's not necessary to increment validate value
			} else {
				validate++;
			}
			validateGroupsNoEmpty();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getMetaData", caught);
		}
	};
	
	/**
	 * Enables close button
	 */
	public void enableClose() {
		button.setEnabled(true);
		Main.get().mainPanel.setVisible(true); // Shows main panel when all widgets are loaded
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("group.label"));
		button.setText(Main.i18n("button.close"));
		addButton.setText(Main.i18n("button.add"));
		groupLabel.setText(Main.i18n("group.group"));
		propertyLabel.setText(Main.i18n("group.property.group"));
	}
	
	/**
	 * Show the popup error
	 * 
	 * @param msg Error message
	 */
	public void show() {
		int left = (Window.getClientWidth()-300)/2;
		int top = (Window.getClientHeight()-100)/2;
		setPopupPosition(left,top);
		setText(Main.i18n("group.label"));
		validate = -1;
		groupListBox.clear();
		propertyListBox.clear();
		propertyListBox.setVisible(false);
		propertyLabel.setVisible(false);
		getAllGroups(); // Gets all groups
		addButton.setEnabled(false);
		super.show();
	}
	
	/**
	 * Gets all property groups
	 */
	private void getAllGroups() {
		ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
		endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
		propertyGroupService.getAllGroups(callbackGetAllGroups);
	}
	
	/**
	 * Gets all metadata group properties 
	 */
	private void getMetaData() {
		ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
		endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
		propertyGroupService.getMetaData(groupListBox.getValue(groupListBox.getSelectedIndex()), callbackGetMetaData);
	}
	
	/**
	 * Enables or disables add group button ( case exist some item to be added )
	 */
	public void enableAddGroupButton() {
		groupListBox.clear();
		propertyListBox.clear();
		getAllGroups(); // Gets all groups
	}
	
	/**
	 * Validates that exist some item to add
	 */
	private void validateGroupsNoEmpty(){
		if (groupListBox.getItemCount()>validate) {
			String value = groupListBox.getValue(validate);
			ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
			endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
			propertyGroupService.getMetaData(value, callbackGetMetaDataToValidate);
		} else {
			// Validate button 
			if (groupListBox.getItemCount()>1) {
				Main.get().mainPanel.search.searchIn.addGroup.setEnabled(true);
			} else {
				Main.get().mainPanel.search.searchIn.addGroup.setEnabled(false);
			}
			validate = -1; // Resets values
		}
	}
}
