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

package es.git.openkm.frontend.client.widget;

import java.util.Iterator;
import java.util.List;

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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTDocument;
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
	private ListBox listBox;
	
	/**
	 * About popup
	 */
	public GroupPopup() {
		// Establishes auto-close when click outside
		super(false,true);

		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		
		button = new Button(Main.i18n("button.close"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		addButton = new Button(Main.i18n("button.add"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				addGroup();
				hide();
				if (Main.get().mainPanel.browser.fileBrowser.isDocumentSelected() ){
					GWTDocument doc = Main.get().mainPanel.browser.fileBrowser.getDocument();
					Main.get().mainPanel.browser.tabMultiple.tabDocument.setProperties(doc);
				}
				// Case there's only two items (white and other) and this is added, then
				// there's no item to be added and must disable addPropertyGroup
				if (listBox.getItemCount()==2) {
					Main.get().mainPanel.topPanel.toolBar.disableAddPropertyGroup();
				}
			}
		});

		listBox = new ListBox();
		listBox.addChangeHandler(new ChangeHandler(){
			@Override
			public void onChange(ChangeEvent arg0) {
				if (listBox.getSelectedIndex()>0) {
					addButton.setEnabled(true);
				} else {
					addButton.setEnabled(false);
				}
			}
		});
		
		listBox.setStyleName("okm-Select");
		
		vPanel.setWidth("300px");
		vPanel.setHeight("50px");
		button.setStyleName("okm-Button");
		addButton.setStyleName("okm-Button");
		addButton.setEnabled(false);
		
		hPanel.add(button);
		hPanel.add(new HTML("&nbsp;&nbsp;"));
		hPanel.add(addButton);
		
		hPanel.setCellHorizontalAlignment(button,VerticalPanel.ALIGN_CENTER);
		hPanel.setCellHorizontalAlignment(addButton,VerticalPanel.ALIGN_CENTER);

		vPanel.add(new HTML("<br>"));
		vPanel.add(listBox);
		vPanel.add(new HTML("<br>"));
		vPanel.add(hPanel);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(listBox, VerticalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hPanel, VerticalPanel.ALIGN_CENTER);

		super.hide();
		setWidget(vPanel);
	}
	
	/**
	 * Gets asyncronous to get all groups
	 */
	final AsyncCallback<List<String>> callbackGetAllGroups = new AsyncCallback<List<String>>() {
		public void onSuccess(List<String> result){
			listBox.clear();
			listBox.addItem("",""); // Adds empty value
			for (Iterator<String> it = result.iterator(); it.hasNext();) {
				String groupKey = (String) it.next();
				String groupTranslation = Main.propertyGroupI18n(groupKey);
				listBox.addItem(groupTranslation,groupKey);
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetAllGroups", caught);
		}
	};
	
	/**
	 * Gets asyncronous to add a group
	 */
	final AsyncCallback<Object> callbackAddGroup = new AsyncCallback<Object>() {
		public void onSuccess(Object result){
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("AddGroup", caught);
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
	}
	
	/**
	 * Show the popup error
	 * 
	 * @param msg Error message
	 */
	public void show() {
		setText(Main.i18n("group.label"));
		getAllGroups(); // Gets all groups
		addButton.setEnabled(false);
		int left = (Window.getClientWidth()-300)/2;
		int top = (Window.getClientHeight()-100)/2;
		setPopupPosition(left,top);
		super.show();
	}
	
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
	 * Add a group to a document
	 */
	private void addGroup() {
		if (listBox.getSelectedIndex()>0) {
			String grpName = listBox.getValue(listBox.getSelectedIndex());
			if (Main.get().mainPanel.browser.fileBrowser.isDocumentSelected()) {
				GWTDocument gwtDocument = Main.get().mainPanel.browser.fileBrowser.getDocument();
				ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
				endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
				propertyGroupService.addGroup(gwtDocument.getPath(), grpName, callbackAddGroup);
			}
		}
	}
}
