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

package com.openkm.frontend.client.panel.center;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.openkm.frontend.client.extension.widget.ToolBarBoxExtension;
import com.openkm.frontend.client.widget.dashboard.GeneralDashboard;
import com.openkm.frontend.client.widget.dashboard.HorizontalToolBar;
import com.openkm.frontend.client.widget.dashboard.KeyMapDashboard;
import com.openkm.frontend.client.widget.dashboard.MailDashboard;
import com.openkm.frontend.client.widget.dashboard.MessagingDashboard;
import com.openkm.frontend.client.widget.dashboard.NewsDashboard;
import com.openkm.frontend.client.widget.dashboard.UserDashboard;
import com.openkm.frontend.client.widget.dashboard.WorkflowDashboard;

/**
 * Dashboard
 * 
 * @author jllort
 *
 */
public class Dashboard extends Composite {
	
	private boolean userVisible = false;
	private boolean mailVisible = false;
	private boolean newsVisible = false;
	private boolean generalVisible = false;
	private boolean workflowVisible = false;
	private boolean keywordsVisible = false;
	private boolean messagingVisible = false;
	
	public static final int DASHBOARD_NONE 		= -1;
	public static final int DASHBOARD_USER 		= 1;
	public static final int DASHBOARD_MAIL 		= 2;
	public static final int DASHBOARD_NEWS 		= 3;
	public static final int DASHBOARD_GENERAL	= 4;
	public static final int DASHBOARD_WORKFLOW	= 5;
	public static final int DASHBOARD_KEYMAP	= 6;
	public static final int DASHBOARD_MESSAGING	= 7;
	public static final int DASHBOARD_EXTENSION	= 8;
	
	private VerticalPanel panel;
	private SimplePanel sp;
	private ScrollPanel scrollPanel;
	public HorizontalToolBar horizontalToolBar;
	public UserDashboard userDashboard;
	public MailDashboard mailDashboard;
	public NewsDashboard newsDashboard;
	public GeneralDashboard generalDashboard;
	public WorkflowDashboard workflowDashboard;
	public KeyMapDashboard keyMapDashboard;
	public MessagingDashboard messagingDashboard;
	private Widget actualWidgetExtension;
	private int actualView = 0; 
	Timer dashboardRefreshing;
	List<Widget> widgetExtensionList;
	
	/**
	 * Dashboard
	 */
	public Dashboard() {
		widgetExtensionList = new ArrayList<Widget>();
		panel = new VerticalPanel();
		sp = new SimplePanel();
		userDashboard = new UserDashboard();
		mailDashboard = new MailDashboard();
		scrollPanel = new ScrollPanel();
		horizontalToolBar = new HorizontalToolBar();
		newsDashboard = new NewsDashboard();
		generalDashboard = new GeneralDashboard();
		workflowDashboard = new WorkflowDashboard();
		keyMapDashboard = new KeyMapDashboard();
		messagingDashboard = new MessagingDashboard();
		
		actualView = DASHBOARD_NONE;
		
		sp.add(scrollPanel);
		
		panel.add(horizontalToolBar);
		panel.add(sp);
		
		sp.setStyleName("okm-Input");
		
		initWidget(panel);
	}
	
	/**
	 * Sets the size on initialization
	 * 
	 * @param width The max width of the widget
	 * @param height The max height of the widget
	 */
	public void setSize(int width, int height) {
		panel.setPixelSize(width-2, height-2);
		panel.setCellHeight(sp, ""+(height-60-2));
		panel.setCellHeight(horizontalToolBar, ""+60);
		sp.setPixelSize(width-2, height-60-2);
		scrollPanel.setPixelSize(width-2, height-60-2);
		userDashboard.setWidth(width-2);
		mailDashboard.setWidth(width-2);
		newsDashboard.setWidth(width-2);
		generalDashboard.setWidth(width-2);
		workflowDashboard.setWidth(width-2);
		keyMapDashboard.setSize(""+(width-2), ""+(height-60-2));
		messagingDashboard.setWidth(width-2);
		horizontalToolBar.setHeight("60");
		horizontalToolBar.setWidth("100%");
		
		for (Iterator<Widget> it = widgetExtensionList.iterator(); it.hasNext();) {
			it.next().setWidth(""+(width-2));
		}
		
		newsDashboard.getUserSearchs(true); // Here must get all searchs to set correct width size
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		userDashboard.langRefresh();
		mailDashboard.langRefresh();
		horizontalToolBar.langRefresh();
		generalDashboard.langRefresh();
		newsDashboard.langRefresh();
		workflowDashboard.langRefresh();
		keyMapDashboard.langRefresh();
		messagingDashboard.langRefresh();
	}
	
	/**
	 * changeView
	 * 
	 * @param view
	 */
	public void changeView(int view) {
		
		switch(actualView) {
			case DASHBOARD_USER :
				scrollPanel.remove(userDashboard);
				break;
			
			case DASHBOARD_MAIL :
				scrollPanel.remove(mailDashboard);
				break;
			
			case DASHBOARD_NEWS :
				scrollPanel.remove(newsDashboard);
				break;
			
			case DASHBOARD_GENERAL:
				scrollPanel.remove(generalDashboard);
				break;
				
			case DASHBOARD_WORKFLOW:
				scrollPanel.remove(workflowDashboard);
				break;
			
			case DASHBOARD_KEYMAP:
				scrollPanel.remove(keyMapDashboard);
				break;
			
			case DASHBOARD_MESSAGING:
				scrollPanel.remove(messagingDashboard);
				break;
			
			case DASHBOARD_EXTENSION:
				scrollPanel.remove(actualWidgetExtension);
				break;
		}
		
		switch (view) {
			case DASHBOARD_USER :
				scrollPanel.add(userDashboard);
				break;
			
			case DASHBOARD_MAIL :
				scrollPanel.add(mailDashboard);
				break;
			
			case DASHBOARD_NEWS :
				scrollPanel.add(newsDashboard);
				break;
				
			case DASHBOARD_GENERAL:
				scrollPanel.add(generalDashboard);
				break;
			
			case DASHBOARD_WORKFLOW:
				scrollPanel.add(workflowDashboard);
				break;
				
			case DASHBOARD_KEYMAP:
				scrollPanel.add(keyMapDashboard);
				break;
				
			case DASHBOARD_MESSAGING:
				scrollPanel.add(messagingDashboard);
				break;
				
			case DASHBOARD_EXTENSION:
				actualWidgetExtension = widgetExtensionList.get(horizontalToolBar.getSelectedExtension());
				scrollPanel.add(actualWidgetExtension);
				break;
		}
		
		actualView = view;
	}
	
	/**
	 * Refresh all
	 */
	public void refreshAll() {
		userDashboard.refreshAll();
		mailDashboard.refreshAll();
		newsDashboard.refreshAllSearchs();
		generalDashboard.refreshAll();
		workflowDashboard.findUserTaskInstances();
		keyMapDashboard.refreshAll();
		messagingDashboard.refreshAll();
	}
	
	/**
	 * startRefreshingDashboard
	 * 
	 * @param scheduleTime
	 */
	public void startRefreshingDashboard(double scheduleTime) {
		dashboardRefreshing = new Timer() {
			public void run() {
				refreshAll();
			}
		};
		
		dashboardRefreshing.scheduleRepeating(new Double(scheduleTime).intValue()); 
	}
	
	/**
	 * showUser
	 */
	public void showUser() {
		userVisible = true;
		horizontalToolBar.showUser();
	}
	
	/**
	 * showMail
	 */
	public void showMail() {
		mailVisible = true;
		horizontalToolBar.showMail();
	}
	
	/**
	 * showNews
	 */
	public void showNews() {
		newsVisible = true;
		horizontalToolBar.showNews();
	}
	
	/**
	 * showGeneral
	 */
	public void showGeneral() {
		generalVisible = true;
		horizontalToolBar.showGeneral();
	}
	
	/**
	 * showWorkflow
	 */
	public void showWorkflow() {
		workflowVisible = true;
		horizontalToolBar.showWorkflow();
	}
	
	/**
	 * showKeywords
	 */
	public void showKeywords() {
		keywordsVisible = true;
		horizontalToolBar.showKeywords();
	}
	
	/**
	 * showKeywords
	 */
	public void showMessaging() {
		messagingVisible = true;
		horizontalToolBar.showMessaging();
	}
	
	/**
	 * init
	 */
	public void init() {
		if (userVisible) {
			changeView(DASHBOARD_USER);
		} else if (mailVisible) {
			changeView(DASHBOARD_MAIL);
		} else if (newsVisible) {
			changeView(DASHBOARD_NEWS);
		} else if (generalVisible) {
			changeView(DASHBOARD_GENERAL);
		} else if (workflowVisible) {
			changeView(DASHBOARD_WORKFLOW);
		} else if (keywordsVisible) {
			changeView(DASHBOARD_KEYMAP);
		} else if (messagingVisible) {
			changeView(DASHBOARD_MESSAGING);
		} else if (!widgetExtensionList.isEmpty()){
			changeView(DASHBOARD_EXTENSION);
		}
		horizontalToolBar.init();
	}
	
	/**
	 * addToolBarBoxExtension
	 * 
	 * @param extension
	 */
	public void addToolBarBoxExtension(ToolBarBoxExtension extension) {
		widgetExtensionList.add(extension.getWidget());
		horizontalToolBar.addToolBarBoxExtension(extension);
	}
}