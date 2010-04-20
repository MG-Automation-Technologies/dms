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

package es.git.openkm.frontend.client.panel.center;

import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.panel.ExtendedComposite;
import es.git.openkm.frontend.client.widget.dashboard.GeneralDashboard;
import es.git.openkm.frontend.client.widget.dashboard.HorizontalToolBar;
import es.git.openkm.frontend.client.widget.dashboard.KeyMapDashboard;
import es.git.openkm.frontend.client.widget.dashboard.MailDashboard;
import es.git.openkm.frontend.client.widget.dashboard.NewsDashboard;
import es.git.openkm.frontend.client.widget.dashboard.UserDashboard;
import es.git.openkm.frontend.client.widget.dashboard.WorkflowDashboard;

/**
 * Dashboard
 * 
 * @author jllort
 *
 */
public class Dashboard extends ExtendedComposite {
	
	public static final int DASHBOARD_USER 		= 1;
	public static final int DASHBOARD_MAIL 		= 2;
	public static final int DASHBOARD_NEWS 		= 3;
	public static final int DASHBOARD_GENERAL	= 4;
	public static final int DASHBOARD_WORKFLOW	= 5;
	public static final int DASHBOARD_KEYMAP	= 6;
	
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
	private int actualView = 0; 
	
	/**
	 * Dashboard
	 */
	public Dashboard() {
		panel = new VerticalPanel();
		sp = new SimplePanel();
		userDashboard = new UserDashboard();
		mailDashboard = new MailDashboard();
		scrollPanel = new ScrollPanel(userDashboard);
		horizontalToolBar = new HorizontalToolBar();
		newsDashboard = new NewsDashboard();
		generalDashboard = new GeneralDashboard();
		workflowDashboard = new WorkflowDashboard();
		keyMapDashboard = new KeyMapDashboard();
		
		actualView = DASHBOARD_USER;
		
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
		horizontalToolBar.setHeight("60");
		horizontalToolBar.setWidth("100%");
		
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
		workflowDashboard.refreshAll();
		keyMapDashboard.refreshAll();
	}
}