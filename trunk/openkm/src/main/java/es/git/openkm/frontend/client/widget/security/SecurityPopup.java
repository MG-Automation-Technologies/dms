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

package es.git.openkm.frontend.client.widget.security;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;

/**
 * Security popup
 * 
 * @author jllort
 *
 */
public class SecurityPopup extends DialogBox implements ClickHandler, TabListener {
	
	private VerticalPanel vPanel;
	private TabPanel tabPanel;
	public SecurityUser securityUser;
	public SecurityRole securityRole;
	public CheckBox recursive;
	private Button button;
	private SimplePanel sp;
	
	/**
	 * Security popup
	 */
	public SecurityPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		
		vPanel = new VerticalPanel();
		securityUser = new SecurityUser();
		securityRole = new SecurityRole();
		tabPanel = new TabPanel();
		sp = new SimplePanel();
		recursive = new CheckBox(Main.i18n("security.recursive"));
		button = new Button(Main.i18n("button.close"), this);
		
		vPanel.setWidth("345");
		vPanel.setHeight("330");
		sp.setHeight("4");
				
		tabPanel.add(securityUser, Main.i18n("security.users"));
		tabPanel.add(securityRole, Main.i18n("security.groups"));
		tabPanel.selectTab(0);
		tabPanel.setWidth("100%");
		tabPanel.addTabListener(this);
		
		vPanel.add(sp);
		vPanel.add(tabPanel);
		vPanel.add(recursive);
		vPanel.add(button);
		
		vPanel.setCellHeight(sp, "4");
		vPanel.setCellHeight(tabPanel, "255");
		vPanel.setCellHeight(recursive, "25");
		vPanel.setCellHeight(button, "25");
		vPanel.setCellHorizontalAlignment(tabPanel, VerticalPanel.ALIGN_CENTER);
		vPanel.setCellVerticalAlignment(tabPanel, VerticalPanel.ALIGN_TOP);
		vPanel.setCellHorizontalAlignment(button, VerticalPanel.ALIGN_CENTER);
		vPanel.setCellVerticalAlignment(button, VerticalPanel.ALIGN_MIDDLE);
		
		button.setStyleName("okm-Button");

		super.hide();
		setWidget(vPanel);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	public void onClick(ClickEvent event) {
		Main.get().mainPanel.browser.tabMultiple.securityRefresh();
		super.hide();
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("security.label"));
		recursive.setText(Main.i18n("security.recursive"));
		button.setText(Main.i18n("button.close"));

		TabBar tabBar = tabPanel.getTabBar();
		int selected = tabBar.getSelectedTab();
		
		while (tabPanel.getWidgetCount() > 0) {
			tabPanel.remove(0);
		}
		
		tabPanel.add(securityUser, Main.i18n("security.users"));
		tabPanel.add(securityRole, Main.i18n("security.groups"));
		tabPanel.selectTab(selected);
		
		securityUser.langRefresh();
		securityRole.langRefresh();
	}
	
	/**
	 * Show the security popup
	 */
	public void show(String path) {
		int left = (Window.getClientWidth()-325) / 2;
		int top = (Window.getClientHeight()-330) / 2;
		setPopupPosition(left, top);
		setText(Main.i18n("security.label"));
		Main.get().securityPopup.securityUser.setPath(path);
		Main.get().securityPopup.securityRole.setPath(path);
		securityUser.reset();
		securityRole.reset();
		securityUser.getGrantedUsers();
		securityUser.getUngrantedUsers();
		securityRole.getGrantedRoles();
		securityRole.getUngrantedRoles();
		super.show();
		
		// Fill width must be done on visible widgets
		securityUser.fillWidth();
		securityRole.fillWidth();
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
	}
}