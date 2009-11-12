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

package es.git.openkm.backend.client.widget.adminusers;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;

import es.git.openkm.backend.client.panel.VerticalBorderPanel;

/**
 * AdminUsersPanel
 * 
 * @author jllort
 *
 */
public class AdminUsersPanel extends Composite {
	
	private VerticalSplitPanel verticalSplitPanel;
	public Users users;
	private HorizontalPanel hPanel;
	public UserData userData;
	public UserActivityLogFilter userActivityLogFilter;
	private VerticalBorderPanel centerSeparatorPanel;
	public UserActivityLog userActivityLog;
	private VerticalPanel leftPanel;
	private VerticalPanel bottomPanel;
	
	/**
	 * AdminUsersPanel
	 */
	public AdminUsersPanel() {
		verticalSplitPanel = new VerticalSplitPanel();
		users = new Users();
		hPanel = new HorizontalPanel();
		userData = new UserData();
		userActivityLogFilter = new UserActivityLogFilter();
		centerSeparatorPanel = new VerticalBorderPanel();
		userActivityLog = new UserActivityLog();
		leftPanel = new VerticalPanel();
		bottomPanel = new VerticalPanel();
		
		leftPanel.add(userData);
		
		bottomPanel.add(userActivityLogFilter);
		bottomPanel.add(userActivityLog);
		
		hPanel.add(users);
		hPanel.add(centerSeparatorPanel);
		hPanel.add(leftPanel);
		
		verticalSplitPanel.setTopWidget(hPanel);
		verticalSplitPanel.setBottomWidget(bottomPanel);		
		verticalSplitPanel.setSplitPosition("330");

		users.setStyleName("okm-Input");
		leftPanel.setStyleName("okm-Input");
		bottomPanel.setStyleName("okm-Input");
		userData.setStyleName("okm-VerticalBorderPanel");
		userActivityLogFilter.setStyleName("okm-VerticalBorderPanel");
		userActivityLogFilter.addStyleName("okm-Border-Bottom");
		
		hPanel.setCellWidth(leftPanel, "250");
		hPanel.setCellWidth(centerSeparatorPanel,"10");
		
		users.setSize("100%","100%");
		userData.setSize("100%","100%");
		userActivityLogFilter.setSize("100%","50");
		leftPanel.setSize("100%","100%");
		bottomPanel.setSize("100%","100%");
		centerSeparatorPanel.setSize("10","100%");
		userActivityLog.setSize("100%","100%");
		hPanel.setSize("100%","100%");
		
		bottomPanel.setCellHeight(userActivityLogFilter, "50");
		bottomPanel.setCellVerticalAlignment(userActivityLog, HasAlignment.ALIGN_TOP);
		
		initWidget(verticalSplitPanel);
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		users.langRefresh();
		userData.langRefresh();
		userActivityLog.langRefresh();
	}
}