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

package com.openkm.frontend.client.widget;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabBar;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.panel.ExtendedDockPanel;

/**
 * Tab Workspace
 * 
 * @author jllort
 *
 */
public class TabWorkspace extends Composite {
	
	public TabBar tabBar;
	private boolean desktopVisible		= false;
	private boolean searchVisible 		= false;
	private boolean dashboardVisible 	= false;
	private boolean adminitrationVisible = false;

	/**
	 * Tab Workspace
	 */
	public TabWorkspace() {
		tabBar = new TabBar();
		tabBar.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				switch (indexCorrectedChangeViewIndex(event.getSelectedItem().intValue())) {
					case ExtendedDockPanel.DESKTOP :
						Main.get().mainPanel.setView(ExtendedDockPanel.DESKTOP);
						Main.get().activeFolderTree.centerActulItemOnScroll(); // Center the actual item every time
						break;
						
					case ExtendedDockPanel.SEARCH :
						Main.get().mainPanel.setView(ExtendedDockPanel.SEARCH);
						break;
						
					case ExtendedDockPanel.DASHBOARD :
						Main.get().mainPanel.setView(ExtendedDockPanel.DASHBOARD);
						break;
					
					case ExtendedDockPanel.ADMINISTRATION :
						Main.get().mainPanel.setView(ExtendedDockPanel.ADMINISTRATION);
						break;
				}
			}
		});
		
		initWidget(tabBar);
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		int selected = tabBar.getSelectedTab();

		while (tabBar.getTabCount()>0) {
			tabBar.selectTab(0);
			tabBar.removeTab(0);
		}
		if (desktopVisible) {
			tabBar.addTab(Main.i18n("tab.workspace.desktop"));
		}
		if (searchVisible) {
			tabBar.addTab(Main.i18n("tab.workspace.search"));
		}
		if (dashboardVisible) {
			tabBar.addTab(Main.i18n("tab.workspace.dashboard"));
		}
		if (adminitrationVisible) {
			tabBar.addTab(Main.i18n("tab.workspace.administration"));
		}
		tabBar.selectTab(selected);
	}
	
	/**
	 * Gets the selected workspace 
	 * 
	 * @return The selected workspace
	 */
	public int getSelectedWorkspace() {
		return indexCorrectedChangeViewIndex(tabBar.getSelectedTab());
	}
	
	/**
	 * Changes the selected tab index selected
	 * 
	 * @param tabIndex The tab index value
	 */
	public void changeSelectedTab(int tabIndex){
		switch (tabIndex ) {
			case ExtendedDockPanel.DESKTOP :
				tabBar.selectTab(ExtendedDockPanel.DESKTOP);
				Main.get().mainPanel.setView(ExtendedDockPanel.DESKTOP);
				break;
				
			case ExtendedDockPanel.SEARCH :
				tabBar.selectTab(ExtendedDockPanel.SEARCH);
				Main.get().mainPanel.setView(ExtendedDockPanel.SEARCH);
				break;
				
			case ExtendedDockPanel.DASHBOARD :
				tabBar.selectTab(ExtendedDockPanel.DASHBOARD);
				Main.get().mainPanel.setView(ExtendedDockPanel.DASHBOARD);
				break;
				
			case ExtendedDockPanel.ADMINISTRATION :
				tabBar.selectTab(ExtendedDockPanel.ADMINISTRATION);
				Main.get().mainPanel.setView(ExtendedDockPanel.ADMINISTRATION);
				break;
		}
	}
	
	/**
	 * indexCorrectedChangeViewIndex
	 * 
	 * Return index correction made depending visible panels
	 * 
	 * @param index
	 * @return
	 */
	public int indexCorrectedChangeViewIndex(int index) {
		int corrected = index;
		if (!desktopVisible && corrected>=ExtendedDockPanel.DESKTOP) {
			corrected++;
		}
		if (!searchVisible && corrected>=ExtendedDockPanel.SEARCH) {
			corrected++;
		}
		if (!dashboardVisible && corrected>=ExtendedDockPanel.DASHBOARD) {
			corrected++;
		}
		return corrected;
	}
	
	/**
	 * showDesktop
	 */
	public void showDesktop() {
		tabBar.addTab(Main.i18n("tab.workspace.desktop"));
		desktopVisible = true;
		
	}
	
	/**
	 * showSearh
	 */
	public void showSearh() {
		tabBar.addTab(Main.i18n("tab.workspace.search"));
		searchVisible = true;
	}
	
	/**
	 * showDashboard
	 */
	public void showDashboard() {
		tabBar.addTab(Main.i18n("tab.workspace.dashboard"));
		dashboardVisible = true;
	}
	
	/**
	 * showAdministration
	 */
	public void showAdministration() {
		tabBar.addTab(Main.i18n("tab.workspace.administration"));
		adminitrationVisible = true;
	}
	
	/**
	 * isDesktopVisible
	 * 
	 * @return
	 */
	public boolean isDesktopVisible() {
		return desktopVisible;
	}
	
	/**
	 * init
	 */
	public void init() {
		if (tabBar.getTabCount()>0) {
			tabBar.selectTab(0);
		}
	}
}