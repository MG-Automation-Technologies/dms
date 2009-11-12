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


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.bean.GWTPermission;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMRepositoryService;
import es.git.openkm.frontend.client.service.OKMRepositoryServiceAsync;

/**
 * The tab folder
 * 
 * @author jllort
 *
 */
public class TabFolder extends Composite implements TabListener {
	
	private final OKMRepositoryServiceAsync repositoryService = (OKMRepositoryServiceAsync) GWT.create(OKMRepositoryService.class);
	
	public TabPanel tabPanel;
	private Folder folder;
	private SecurityScrollTable security;
	private VerticalPanel panel;
	
	public TabFolder() {
		tabPanel = new TabPanel();
		folder = new Folder();
		security = new SecurityScrollTable();
		panel = new VerticalPanel();
		
		tabPanel.add(folder, Main.i18n("tab.folder.properties"));
		tabPanel.add(security, Main.i18n("tab.folder.security"));

		tabPanel.selectTab(0);
		tabPanel.addTabListener(this);
		panel.add(tabPanel);
		
		tabPanel.setWidth("100%");
		folder.setSize("100%", "100%");
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
	public void setSize(int width, int height) {
		tabPanel.setSize(""+width, ""+height);
		folder.setPixelSize(width,height-20); // Substract tab height
		security.setPixelSize(width-2,height-22); // Substract tab height
		security.fillWidth();
	}
	
	/**
	 * Sets the folder values
	 * 
	 * @param folder The folder object
	 */
	public void setProperties(GWTFolder folder) {
		this.folder.set(folder);
		security.setPath(folder.getPath());
		security.GetGrands();
		if ((folder.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE) {
			security.setChangePermision(true);
		} else {
			security.setChangePermision(false);
		}
	}
	
	/**
	 * Refresh security values
	 */
	public void securityRefresh() {
		// Determines if folder security changed is tree or filebrowser, if filebrowser has no selectedRow then
		// must refresh tree node selected to reflect security changes ( icon color )
		// this remote methods also refresh properties (remote call to setProperties ).
		if (Main.get().mainPanel.browser.fileBrowser.isSelectedRow()) {
			Main.get().mainPanel.browser.fileBrowser.securityRefresh();
		} else {
			Main.get().activeFolderTree.securityRefresh();
		}
	}
	
	/**
	 * Refresh language
	 */
	public void langRefresh() {
		TabBar tabBar = tabPanel.getTabBar();
		int selected = tabBar.getSelectedTab();
		
		while (tabPanel.getWidgetCount() > 0) {
			tabPanel.remove(0);
		}
		
		tabPanel.add(folder, Main.i18n("tab.folder.properties"));
		tabPanel.add(security, Main.i18n("tab.folder.security"));
		tabPanel.selectTab(selected);
		
		folder.langRefresh();
		security.langRefresh();
		
		resizingIncubatorWidgets();
	}
	
	/**
	 * Gets asyncronous root node
	 */
	final AsyncCallback callbackGetRoot = new AsyncCallback() {
		public void onSuccess(Object result) {
			//Only executes on initalization and the actualItem is root element on initialization
			//We put the id on root
			setProperties((GWTFolder) result);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetRoot", caught);
		}
	};
	
	/**
	 * Gets the root
	 */
	public void getRoot() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);	
		repositoryService.getRoot(callbackGetRoot);
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
		if (tabIndex==1) {
			security.fillWidth(); // Always when shows fires fill width
		}
	}
}