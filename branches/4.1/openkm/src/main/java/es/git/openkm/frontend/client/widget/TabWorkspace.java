/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabListener;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.panel.ExtendedDockPanel;

/**
 * Tab Workspace
 * 
 * @author jllort
 *
 */
public class TabWorkspace extends Composite implements TabListener {
	
	public TabBar tabBar;
	private boolean enableAdminitration = false;

	/**
	 * Tab Workspace
	 */
	public TabWorkspace() {
		tabBar = new TabBar();
		tabBar.addTab(Main.i18n("tab.workspace.desktop"));
		tabBar.addTab(Main.i18n("tab.workspace.search"));
		tabBar.addTab(Main.i18n("tab.workspace.dashboard"));
		tabBar.selectTab(0);
		tabBar.addTabListener(this);
		
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
		
		tabBar.addTab(Main.i18n("tab.workspace.desktop"));
		tabBar.addTab(Main.i18n("tab.workspace.search"));
		tabBar.addTab(Main.i18n("tab.workspace.dashboard"));
		if (enableAdminitration) {
			tabBar.addTab(Main.i18n("tab.workspace.administration"));
		}
		tabBar.selectTab(selected);
	}
	
	public void enableAdministration() {
		enableAdminitration = true;
		langRefresh();
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
		switch (tabIndex ) {
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
	
	/**
	 * Gets the selected workspace 
	 * 
	 * @return The selected workspace
	 */
	public int getSelectedWorkspace() {
		return tabBar.getSelectedTab();
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
}