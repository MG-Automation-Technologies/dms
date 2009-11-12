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

package es.git.openkm.frontend.client.widget.properties;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTMail;
import es.git.openkm.frontend.client.bean.GWTPermission;
import es.git.openkm.frontend.client.service.OKMPropertyGroupService;
import es.git.openkm.frontend.client.service.OKMPropertyGroupServiceAsync;

/**
 * The tab mail
 * 
 * @author jllort
 *
 */
public class TabMail extends Composite implements TabListener {
	
	private final OKMPropertyGroupServiceAsync propertyGroupService = (OKMPropertyGroupServiceAsync) GWT.create(OKMPropertyGroupService.class);

	public TabPanel tabPanel;
	public Mail mail;
	public SecurityScrollTable security;
	private VerticalPanel panel;
	private GWTMail gWTMail;
	private int selectedTab = 0; // Used to determine selected tab to mantain on change document, because not all documents
								 // have the same numeber of tabs ( document group properties are variable ) 
	private boolean visibleButton = true; // Sets visibleButtons enabled to default view 
	
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
		tabPanel.addTabListener(this);
		
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
		this.gWTMail = gWTMail;
		selectedTab = tabPanel.getTabBar().getSelectedTab(); // Sets the actual selected Tab
		
		security.setPath(gWTMail.getPath());
		security.GetGrands();
		
		if ((gWTMail.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE) {
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
		this.visibleButton = visible;  // Save to be used by property group
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
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.TabListener#onBeforeTabSelected(com.google.gwt.user.client.ui.SourcesTabEvents, int)
	 */
	public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.TabListener#onTabSelected(com.google.gwt.user.client.ui.SourcesTabEvents, int)
	 */
	public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
		Main.get().mainPanel.topPanel.toolBar.evaluateRemoveGroupProperty(isSelectedTabGroupPropety(tabIndex));
	}
}