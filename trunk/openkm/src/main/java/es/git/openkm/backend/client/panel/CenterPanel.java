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

package es.git.openkm.backend.client.panel;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.backend.client.widget.adminusers.AdminUsersPanel;
import es.git.openkm.backend.client.widget.advancedsearch.AdvancedSearchPanel;
import es.git.openkm.backend.client.widget.config.ConfigPanel;
import es.git.openkm.backend.client.widget.generalutils.GeneralUtilsPanel;
import es.git.openkm.backend.client.widget.propertygroups.PropertyGroupPanel;
import es.git.openkm.backend.client.widget.stats.StatsPanel;
import es.git.openkm.backend.client.widget.workflow.WorkflowPanel;

/**
 * Center panel
 * 
 * @author jllort
 *
 */
public class CenterPanel extends Composite {
	
	public static final int PANEL_STATS 				= 0;
	public static final int PANEL_INDEX_ADVANCED_SEARCH = 1;
	public static final int PANEL_INDEX_USERS 			= 2;
	public static final int PANEL_INDEX_GENERAL_UTILS 	= 3;
	public static final int PANEL_PROPERTY_GROUPS 		= 4;
	public static final int PANEL_WORKFLOW 				= 5;
	public static final int PANEL_CONFIG 				= 6;
	
    private VerticalPanel vPanel;
    private List<Widget> panelList;
    public AdvancedSearchPanel advancedSearchPanel;
    public GeneralUtilsPanel generalUtilsPanel;
    public PropertyGroupPanel propertyGroupPanel;
    public StatsPanel statsPanel;
    public AdminUsersPanel adminUsersPanel;
    public WorkflowPanel workflowPanel;
    public ConfigPanel configPanel;
    private int actualVisible = 0;
    private int width = 0;
    private int height = 0;
	
	/**
	 * CenterPanel
	 */
	public CenterPanel() {		
		vPanel = new VerticalPanel();
		statsPanel = new StatsPanel();
		
		panelList = new ArrayList<Widget>();
		panelList.add(statsPanel);
		
		actualVisible = PANEL_STATS;
		
		vPanel.add(statsPanel);
		
		initWidget(vPanel);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setPixelSize(int, int)
	 */
	public void setPixelSize(int width, int height) {
		this.width = width;
		this.height = height;
		vPanel.setPixelSize(width-2, height-2);
		statsPanel.setPixelSize(width, height);
	}
	
	/**
	 * setVisiblePanel
	 * 
	 * @param index
	 */
	public void setVisiblePanel(int index) {		
		if (actualVisible!=index && index>=0) {
			
			// Removing actual visible widget
			switch (actualVisible) {
				case PANEL_INDEX_ADVANCED_SEARCH :
					vPanel.remove(advancedSearchPanel);
					break;
					
				case PANEL_INDEX_USERS :
					vPanel.remove(adminUsersPanel);
					break;
				
				case PANEL_INDEX_GENERAL_UTILS :
					vPanel.remove(generalUtilsPanel);
					break;
						
				case PANEL_PROPERTY_GROUPS:
					vPanel.remove(propertyGroupPanel);
					break;
					
				case PANEL_STATS:
					vPanel.remove(statsPanel);
					break;
					
				case PANEL_WORKFLOW:
					vPanel.remove(workflowPanel);
					break;

				case PANEL_CONFIG:
					vPanel.remove(configPanel);
					break;
			}
			
			actualVisible = index; // Setting the visible widget

			switch(index) {
				case PANEL_INDEX_ADVANCED_SEARCH :
					if (!panelList.contains(advancedSearchPanel)) {
						advancedSearchPanel = new AdvancedSearchPanel();
						panelList.add(advancedSearchPanel);
						vPanel.add(advancedSearchPanel);
						advancedSearchPanel.setPixelSize(width, height);
					} else {
						vPanel.add(advancedSearchPanel);
					}
					break;
					
				case PANEL_INDEX_USERS :
					if (!panelList.contains(adminUsersPanel)) {
						adminUsersPanel = new AdminUsersPanel();
						vPanel.add(adminUsersPanel);
						adminUsersPanel.setSize(""+width,""+height);
						panelList.add(adminUsersPanel);
					} else {
						vPanel.add(adminUsersPanel);
					}
					break;
				
				case PANEL_INDEX_GENERAL_UTILS :
					if (!panelList.contains(generalUtilsPanel)) {
						generalUtilsPanel = new GeneralUtilsPanel();
						panelList.add(generalUtilsPanel);
						vPanel.add(generalUtilsPanel);
						generalUtilsPanel.setPixelSize(width, height);
					} else {
						vPanel.add(generalUtilsPanel);
					}
					break;
					
				case PANEL_PROPERTY_GROUPS:
					if (!panelList.contains(propertyGroupPanel)) {
						propertyGroupPanel = new PropertyGroupPanel();
						vPanel.add(propertyGroupPanel);
						propertyGroupPanel.setSize(""+width,""+height);
						panelList.add(propertyGroupPanel);
					} else {
						vPanel.add(propertyGroupPanel);
					}
					break;
					
				case PANEL_STATS:
					if (!panelList.contains(statsPanel)) {
						statsPanel = new StatsPanel();
						vPanel.add(statsPanel);
						statsPanel.setSize(""+width,""+height);
						panelList.add(statsPanel);
					} else {
						vPanel.add(statsPanel);
					}
					break;
					
				case PANEL_WORKFLOW:
					if (!panelList.contains(workflowPanel)) {
						workflowPanel = new WorkflowPanel();
						vPanel.add(workflowPanel);
						workflowPanel.setSize(width,height);
						panelList.add(workflowPanel);
					} else {
						vPanel.add(workflowPanel);
					}
					//workflowPanel.workflow.findLatestProcessDefinitions(); // Always refreshing this panel to show new uploaded process files
					break;

				case PANEL_CONFIG:
					if (!panelList.contains(configPanel)) {
						configPanel = new ConfigPanel();
						vPanel.add(configPanel);
						configPanel.setSize(width,height);
						panelList.add(configPanel);
					} else {
						vPanel.add(configPanel);
					}
					break;			
			}
		}
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		adminUsersPanel.langRefresh();
		advancedSearchPanel.langRefresh();
		generalUtilsPanel.langRefresh();
	}
}