/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTMail;
import com.openkm.frontend.client.bean.GWTPermission;
import com.openkm.frontend.client.extension.event.HasMailEvent;
import com.openkm.frontend.client.extension.event.handler.MailHandlerExtension;
import com.openkm.frontend.client.extension.event.hashandler.HasMailHandlerExtension;
import com.openkm.frontend.client.extension.widget.tabmail.TabMailExtension;
import com.openkm.frontend.client.util.Util;

/**
 * The tab mail
 * 
 * @author jllort
 *
 */
public class TabMail extends Composite implements HasMailEvent, HasMailHandlerExtension {
	private static final int TAB_HEIGHT = 20;
	private int SECURITY_TAB = -1;
	
	public TabLayoutPanel tabPanel;
	public Mail mail;
	public SecurityScrollTable security;
	private VerticalPanel panel;
	private List<TabMailExtension> widgetExtensionList;
	private List<MailHandlerExtension> mailHandlerExtensionList;
	private int selectedTab = 0; // Used to determine selected tab to mantain on change document, because not all documents
								 // have the same numeber of tabs ( document group properties are variable ) 
	private boolean propertiesVisible = false;
	private boolean securityVisible = false;
	private int height = 0;
	private int width = 0;
	
	/**
	 * The Document tab
	 */
	public TabMail() {
		widgetExtensionList = new ArrayList<TabMailExtension>();
		mailHandlerExtensionList = new ArrayList<MailHandlerExtension>();
		tabPanel = new TabLayoutPanel(TAB_HEIGHT, Unit.PX);
		mail = new Mail();
		security = new SecurityScrollTable();
		panel = new VerticalPanel();

		tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int tabIndex = event.getSelectedItem().intValue();
				selectedTab = tabIndex;
				if (tabIndex==SECURITY_TAB) {
					Timer timer = new Timer() {
						@Override
						public void run() {
							security.fillWidth(); // Always when shows fires fill width
						}
					};
					timer.schedule(50); // Fill width must be done after really it'll be visible
				}
				Main.get().mainPanel.topPanel.toolBar.evaluateRemoveGroupProperty(isSelectedTabGroupPropety(event.getSelectedItem().intValue()));
				fireEvent(HasMailEvent.TAB_CHANGED);
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
	public void setPixelSize(int width, int height) {
		this.height = height;
		this.width = width;
		tabPanel.setPixelSize(width, height);
		mail.setPixelSize(width,height-TAB_HEIGHT); // Substract tab height
		security.setPixelSize(width,height-TAB_HEIGHT); // Substract tab height
		security.fillWidth();
		
		// Setting size to extension
		for (Iterator<TabMailExtension> it = widgetExtensionList.iterator(); it.hasNext();) {
			it.next().setPixelSize(width,height-TAB_HEIGHT);
		}
		
		fireEvent(HasMailEvent.PANEL_RESIZED);
	}
	
	/**
	 * Sets document values
	 * 
	 * @param doc The document object
	 */
	public void setProperties(GWTMail gWTMail) {	
		selectedTab = tabPanel.getSelectedIndex(); // Sets the actual selected Tab
		
		if (securityVisible) {
			security.setPath(gWTMail.getPath());
			security.GetGrants();
			
			GWTFolder parentFolder = Main.get().activeFolderTree.getFolder();
			if ((parentFolder.getPermissions() & GWTPermission.SECURITY) == GWTPermission.SECURITY &&
				(gWTMail.getPermissions() & GWTPermission.SECURITY) == GWTPermission.SECURITY) {
				security.setChangePermision(true);
			} else {
				security.setChangePermision(false);
			}
		}
		
		mail.set(gWTMail);
		
		// Setting folder object to extensions
		for (Iterator<TabMailExtension> it = widgetExtensionList.iterator(); it.hasNext();) {
			it.next().set(gWTMail);
		}
		
		fireEvent(HasMailEvent.MAIL_CHANGED);
	}
	
	/**
	 * Refresh security values
	 */
	public void securityRefresh() {
		fireEvent(HasMailEvent.SECURITY_CHANGED);
		Main.get().mainPanel.desktop.browser.fileBrowser.securityRefresh();
	}
	
	/**
	 * getSelectedTab
	 * 
	 * @return
	 */
	public int getSelectedTab() {
		return selectedTab;
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		selectedTab = tabPanel.getSelectedIndex();
		
		while (tabPanel.getWidgetCount() > 0) {
			tabPanel.remove(0);
		}
		
		if (propertiesVisible) {
			tabPanel.add(mail, Main.i18n("tab.document.properties"));
			mail.langRefresh();
		}
		if (securityVisible) {
			tabPanel.add(security, Main.i18n("tab.document.security"));
			security.langRefresh();
		}
		
		// Adding extensions
		for (Iterator<TabMailExtension> it = widgetExtensionList.iterator(); it.hasNext();) {
			TabMailExtension extension = it.next();
			tabPanel.add(extension, extension.getTabText());
		}
		
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
		fireEvent(HasMailEvent.SET_VISIBLE_BUTTONS);
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
	 * showProperties
	 */
	public void showProperties() {
		tabPanel.add(mail, Main.i18n("tab.document.properties"));
		propertiesVisible = true;	
	}
	
	/**
	 * showSecurity
	 */
	public void showSecurity() {
		tabPanel.add(security, Main.i18n("tab.document.security"));
		securityVisible = true;
		SECURITY_TAB = tabPanel.getWidgetCount()-1; // Starts at 0
	}
	
	/**
	 * showExtensions
	 */
	public void showExtensions() {
		for (TabMailExtension extension : widgetExtensionList) {
			tabPanel.add(extension, extension.getTabText());
			extension.setPixelSize(width,height-TAB_HEIGHT);
		}
	}
	
	/**
	 * init
	 */
	public void init() {
		if (tabPanel.getWidgetCount()>0) {
			tabPanel.selectTab(0);
			
			if (securityVisible && mail.get()!=null) {
				security.setPath(mail.get().getPath());
				security.GetGrants();
				
				GWTFolder parentFolder = Main.get().activeFolderTree.getFolder();
				if ((parentFolder.getPermissions() & GWTPermission.SECURITY) == GWTPermission.SECURITY &&
					(mail.get().getPermissions() & GWTPermission.SECURITY) == GWTPermission.SECURITY) {
					security.setChangePermision(true);
				} else {
					security.setChangePermision(false);
				}
			}
		}
	}
	
	/**
	 * getMail
	 * 
	 * @return
	 */
	public GWTMail getMail() {
		return mail.get();
	}
	
	/**
	 * addMailExtension
	 * 
	 * @param extension
	 */
	public void addMailExtension(TabMailExtension extension) {
		widgetExtensionList.add(extension);
	}
	
	/**
	 * resizingIncubatorWidgets 
	 * 
	 * Needs resizing if not widgets disappears
	 */
	public void resizingIncubatorWidgets() {
		security.setPixelSize(getOffsetWidth(), getOffsetHeight()-TAB_HEIGHT); // Substract tab height
		security.fillWidth();
		// TODO:Solves minor bug with IE
		if (Util.getUserAgent().startsWith("ie")) {
			Timer timer = new Timer() {
				@Override
				public void run() {
					tabPanel.setWidth(""+width);
					tabPanel.setWidth(""+(width+1));
					Timer timer = new Timer() {
						@Override
						public void run() {
							tabPanel.setWidth(""+width);
						}
					};
					timer.schedule(50);
				}
			};
			timer.schedule(100);
		}
	}
	
	@Override
	public void addMailHandlerExtension(MailHandlerExtension handlerExtension) {
		mailHandlerExtensionList.add(handlerExtension);
	}

	@Override
	public void fireEvent(MailEventConstant event) {
		for (MailHandlerExtension handlerExtension : mailHandlerExtensionList) {
			handlerExtension.onChange(event);
		}
	}
}