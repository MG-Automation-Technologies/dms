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

package com.openkm.backend.client.widget.users;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.openkm.backend.client.panel.HorizontalBorderPanel;

/**
 * Users
 * 
 * @author jllort
 *
 */
public class UsersPanel extends Composite {
	
	private VerticalPanel vPanel;
	private Users users;
	private HorizontalBorderPanel centerSeparatorPanel;
	public UsersMonitor usersMonitor;
	private VerticalPanel vPanelMonitor;
	
	/**
	 * UsersPanel
	 */
	public UsersPanel() {
		vPanel = new VerticalPanel();
		centerSeparatorPanel = new HorizontalBorderPanel();
		users = new Users();
		usersMonitor = new UsersMonitor();
		vPanelMonitor = new VerticalPanel();
		
		vPanelMonitor.add(usersMonitor);
		
		vPanel.add(users);
		vPanel.add(centerSeparatorPanel);
		vPanel.add(vPanelMonitor);
		
		vPanel.setCellHeight(users, "30");
		
		users.setStyleName("okm-Input");
		vPanelMonitor.setStyleName("okm-Input");
		
		centerSeparatorPanel.setSize("100%", "10");
		vPanel.setCellHeight(centerSeparatorPanel, "10");
		vPanelMonitor.setSize("100%","100%");
		vPanelMonitor.setCellVerticalAlignment(usersMonitor, HasAlignment.ALIGN_TOP);
		
		initWidget(vPanel);
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		users.langRefresh();
		usersMonitor.langRefresh();
	}
}