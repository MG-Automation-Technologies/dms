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

package com.openkm.frontend.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.panel.ExtendedDockPanel;
import com.openkm.frontend.client.panel.PanelDefinition;

/**
 * Confirm panel
 * 
 * @author jllort
 *
 */
public class ConfirmPopup extends DialogBox {
	
	public static final int NO_ACTION 								= 0;
	public static final int CONFIRM_DELETE_FOLDER 					= 1;
	public static final int CONFIRM_DELETE_DOCUMENT 				= 2;
	public static final int CONFIRM_EMPTY_TRASH 					= 3;
	public static final int CONFIRM_PURGE_FOLDER 					= 4;
	public static final int CONFIRM_PURGE_DOCUMENT  				= 5;
	public static final int CONFIRM_DELETE_DOCUMENT_PROPERTY_GROUP	= 6;
	public static final int CONFIRM_PURGE_VERSION_HISTORY_DOCUMENT	= 7;
	public static final int CONFIRM_RESTORE_HISTORY_DOCUMENT		= 8;
	public static final int CONFIRM_SET_DEFAULT_HOME				= 9;
	public static final int CONFIRM_DELETE_SAVED_SEARCH				= 10;
	public static final int CONFIRM_DELETE_USER_NEWS				= 11;
	public static final int CONFIRM_DELETE_MAIL		 				= 12;
	public static final int CONFIRM_PURGE_MAIL  					= 13;
	
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private HTML text;
	private Button cancelButton;
	private Button acceptButton;
	private int action = 0;
	private Object object;
	
	/**
	 * Confirm popup
	 */
	public ConfirmPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		
		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		text = new HTML();
		text.setStyleName("okm-NoWrap");
		
		cancelButton = new Button(Main.i18n("button.cancel"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		acceptButton = new Button(Main.i18n("button.accept"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				execute();
				hide();
			}
		});

		vPanel.setWidth("300px");
		vPanel.setHeight("100px");
		cancelButton.setStyleName("okm-Button");
		acceptButton.setStyleName("okm-Button");

		text.setHTML("");
		
		hPanel.add(cancelButton);
		hPanel.add(new HTML("&nbsp;&nbsp;"));
		hPanel.add(acceptButton);
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(text);
		vPanel.add(new HTML("<br>"));
		vPanel.add(hPanel);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(text, VerticalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hPanel, VerticalPanel.ALIGN_CENTER);

		super.hide();
		setWidget(vPanel);
	}
	
	/**
	 * Execute the confirmed action
	 */
	private void execute() {
		switch (action) {
		
			case CONFIRM_DELETE_FOLDER :
				if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
					Main.get().mainPanel.browser.fileBrowser.delete();
				} else if (Main.get().activeFolderTree.isPanelSelected()) {
					Main.get().activeFolderTree.delete();
				}
				break;
				
			case CONFIRM_DELETE_DOCUMENT :
				if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
					Main.get().mainPanel.browser.fileBrowser.delete();
				}
				break;
				
			case CONFIRM_EMPTY_TRASH :
				// Ensures DESKTOP view is enabled
				if (Main.get().mainPanel.topPanel.tabWorkspace.getSelectedWorkspace()!=ExtendedDockPanel.DESKTOP){
					Main.get().mainPanel.topPanel.tabWorkspace.changeSelectedTab(ExtendedDockPanel.DESKTOP);
				}
				
				//Ensures that trash view is enabled
				if (Main.get().mainPanel.navigator.getStackIndex() != PanelDefinition.NAVIGATOR_TRASH) {
					Main.get().mainPanel.navigator.stackPanel.showStack(PanelDefinition.NAVIGATOR_TRASH, false);
				}
				
				Main.get().activeFolderTree.purgeTrash();
				break;
			
			case CONFIRM_PURGE_FOLDER:
				if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
					Main.get().mainPanel.browser.fileBrowser.purge();
				} else if (Main.get().activeFolderTree.isPanelSelected()) {
					Main.get().activeFolderTree.purge();
				}
				break;
				
			case CONFIRM_PURGE_DOCUMENT:
				if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
					Main.get().mainPanel.browser.fileBrowser.purge();
				}
				break;
			
			case CONFIRM_DELETE_DOCUMENT_PROPERTY_GROUP:
				Main.get().mainPanel.browser.tabMultiple.tabDocument.removePropertyGroup();
				// Always if a property group is deleted add property button on tool bar must be enabled, we execute to ensure this
				Main.get().mainPanel.topPanel.toolBar.enableAddPropertyGroup();
				break;
				
			case CONFIRM_PURGE_VERSION_HISTORY_DOCUMENT:
				Main.get().mainPanel.browser.tabMultiple.tabDocument.version.purgeVersionHistory();
				break;
			
			case CONFIRM_RESTORE_HISTORY_DOCUMENT:
				if (object!=null && object instanceof String ) {
					Main.get().mainPanel.browser.tabMultiple.tabDocument.version.restoreVersion((String) object);
				}
				break;
			
			case CONFIRM_SET_DEFAULT_HOME:
				Main.get().mainPanel.topPanel.mainMenu.bookmark.setUserHome();
				break;
				
			case CONFIRM_DELETE_SAVED_SEARCH:
				Main.get().mainPanel.historySearch.searchSaved.deleteSearch();
				break;
				
			case CONFIRM_DELETE_USER_NEWS:
				Main.get().mainPanel.historySearch.userNews.deleteSearch();
				break;
			
			case CONFIRM_DELETE_MAIL :
				if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
					Main.get().mainPanel.browser.fileBrowser.delete();
				}
				break;
			
			case CONFIRM_PURGE_MAIL:
				if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
					Main.get().mainPanel.browser.fileBrowser.purge();
				}
				break;
		}
		
		action = NO_ACTION; // Resets action value
	}
	
	/**
	 * Sets the action to be confirmed
	 * 
	 * @param action The action to be confirmed
	 */
	public void setConfirm(int action) {
		this.action = action;
		switch (action) {
		
			case CONFIRM_DELETE_FOLDER :
				text.setHTML(Main.i18n("confirm.delete.folder"));
				break;
				
			case CONFIRM_DELETE_DOCUMENT :
				text.setHTML(Main.i18n("confirm.delete.document"));
				break;
			
			case CONFIRM_EMPTY_TRASH :
				text.setHTML(Main.i18n("confirm.delete.trash"));
				break;
			
			case CONFIRM_PURGE_FOLDER:
				text.setHTML(Main.i18n("confirm.purge.folder"));
				break;
				
			case CONFIRM_PURGE_DOCUMENT:
				text.setHTML(Main.i18n("confirm.purge.document"));
				break;
			
			case CONFIRM_DELETE_DOCUMENT_PROPERTY_GROUP:
				text.setHTML(Main.i18n("confirm.delete.propety.group"));
				break;
			
			case CONFIRM_PURGE_VERSION_HISTORY_DOCUMENT:
				text.setHTML(Main.i18n("confirm.purge.version.history.document"));
				break;
			
			case CONFIRM_RESTORE_HISTORY_DOCUMENT:
				text.setHTML(Main.i18n("confirm.purge.restore.document"));
				break;
			
			case CONFIRM_SET_DEFAULT_HOME:
				text.setHTML(Main.i18n("confirm.set.default.home"));
				break;
				
			case CONFIRM_DELETE_SAVED_SEARCH:
				text.setHTML(Main.i18n("confirm.delete.saved.search"));
				break;
				
			case CONFIRM_DELETE_USER_NEWS:
				text.setHTML(Main.i18n("confirm.delete.user.news"));
				break;
			
			case CONFIRM_DELETE_MAIL:
				text.setHTML(Main.i18n("confirm.delete.mail"));
				break;
		}
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("confirm.label"));
		cancelButton.setText(Main.i18n("button.cancel"));
		acceptButton.setText(Main.i18n("button.accept"));
	}
	
	/**
	 * Sets the value to object
	 * 
	 * @param object The object to set
	 */
	public void setValue(Object object) {
		this.object = object;
	}
	
	/**
	 * Get the object value
	 * 
	 * @return The object
	 */
	public Object getValue() {
		return this.object;
	}
	
	/**
	 * Shows de popup
	 */
	public void show(){
		setText(Main.i18n("confirm.label"));
		int left = (Window.getClientWidth()-300)/2;
		int top = (Window.getClientHeight()-125)/2;
		setPopupPosition(left,top);
		super.show();
	}
}