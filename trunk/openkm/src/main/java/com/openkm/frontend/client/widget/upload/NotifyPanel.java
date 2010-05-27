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

package com.openkm.frontend.client.widget.upload;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.openkm.frontend.client.Main;

/**
 * NotifyPanel
 * 
 * @author jllort
 *
 */
public class NotifyPanel extends Composite {
	
	private static final int TAB_USERS 	= 0;
	private static final int TAB_GROUPS = 1;
	
	private TabPanel tabPanel;
	private VerticalPanel vPanel;
	private NotifyUser notifyUser;
	private NotifyRole notifyRole;
	
	/**
	 * NotifyPanel
	 */
	public NotifyPanel() {
		vPanel = new VerticalPanel();
		notifyUser = new NotifyUser();
		notifyRole = new NotifyRole();
		tabPanel = new TabPanel();
		
		tabPanel.add(notifyUser, Main.i18n("fileupload.label.users"));
		tabPanel.add(notifyRole, Main.i18n("fileupload.label.groups"));
		tabPanel.selectTab(TAB_USERS);
		tabPanel.setWidth("100%");
		
		vPanel.add(tabPanel);
		
		tabPanel.addStyleName("okm-DisableSelect");
		
		initWidget(vPanel);
	}
	
	/**
	 * reset
	 */
	public void reset() {
		notifyUser.reset();
		notifyRole.reset();
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		TabBar tabBar = tabPanel.getTabBar();
		int selected = tabBar.getSelectedTab();
		
		while (tabPanel.getWidgetCount() > 0) {
			tabPanel.remove(0);
		}
		
		tabPanel.add(notifyUser, Main.i18n("fileupload.label.users"));
		tabPanel.add(notifyRole, Main.i18n("fileupload.label.groups"));
		tabPanel.selectTab(selected);
		
		notifyUser.langRefresh();
		notifyRole.langRefresh();
	}
	
	/**
	 * Gets all users and roles
	 */
	public void getAll() {
		notifyUser.getAllUsers();
		notifyRole.getAllRoles();
	}
}