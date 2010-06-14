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

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTMail;
import com.openkm.frontend.client.bean.GWTPermission;

/**
 * The tab mail
 * 
 * @author jllort
 *
 */
public class TabMail extends Composite {

	public TabPanel tabPanel;
	public Mail mail;
	public SecurityScrollTable security;
	private VerticalPanel panel;
	private int selectedTab = 0; // Used to determine selected tab to mantain on change document, because not all documents
								 // have the same numeber of tabs ( document group properties are variable ) 
	
	/**
	 * The Document tab
	 */
	public TabMail() {
		tabPanel = new TabPanel();
		mail = new Mail();
		security = new SecurityScrollTable();
		panel = new VerticalPanel();

		tabPanel.add(mail, Main.i18n("tab.document.properties"));
		tabPanel.add(security, Main.i18n("tab.document.security"));
		
		tabPanel.selectTab(0);
		tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Main.get().mainPanel.topPanel.toolBar.evaluateRemoveGroupProperty(isSelectedTabGroupPropety(event.getSelectedItem().intValue()));
			}
		});
		
		panel.add(tabPanel);
		tabPanel.setWidth("100%");
		mail.setSize("100%", "100%");
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
		mail.setPixelSize(width,height-20); // Substract tab height
		security.setPixelSize(width-2,height-22); // Substract tab height
		security.fillWidth();
	}
	
	/**
	 * Sets document values
	 * 
	 * @param doc The document object
	 */
	public void setProperties(GWTMail gWTMail) {	
		selectedTab = tabPanel.getTabBar().getSelectedTab(); // Sets the actual selected Tab
		
		security.setPath(gWTMail.getPath());
		security.GetGrants();
		
		GWTFolder parentFolder = Main.get().activeFolderTree.getFolder();
		if ((parentFolder.getPermissions() & GWTPermission.SECURITY) == GWTPermission.SECURITY &&
			(gWTMail.getPermissions() & GWTPermission.SECURITY) == GWTPermission.SECURITY) {
			security.setChangePermision(true);
		} else {
			security.setChangePermision(false);
		}
		
		mail.set(gWTMail);
	}
	
	/**
	 * Refresh security values
	 */
	public void securityRefresh() {
		Main.get().mainPanel.desktop.browser.fileBrowser.securityRefresh();
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
		
		tabPanel.add(mail, Main.i18n("tab.document.properties"));
		tabPanel.add(security, Main.i18n("tab.document.security"));
		
		mail.langRefresh();
		security.langRefresh();
		
		tabPanel.selectTab(selectedTab);
		
		resizingIncubatorWidgets();
	}
	
	/**
	 * Sets visibility to buttons ( true / false )
	 * 
	 * @param visible The visible value
	 */
	public void setVisibleButtons(boolean visible){
		security.setVisibleButtons(visible);
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
		security.setPixelSize(getOffsetWidth()-2, getOffsetHeight()-22); // Substract tab height
		security.fillWidth();
	}
}