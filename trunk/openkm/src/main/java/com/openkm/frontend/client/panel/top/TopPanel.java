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

package com.openkm.frontend.client.panel.top;

import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.openkm.frontend.client.panel.ExtendedSizeComposite;
import com.openkm.frontend.client.widget.TabWorkspace;
import com.openkm.frontend.client.widget.ToolBar;
import com.openkm.frontend.client.widget.mainmenu.MainMenu;

/**
 * Top panel
 * 
 * @author jllort
 *
 */
public class TopPanel extends ExtendedSizeComposite {
	
	private VerticalPanel panel;
	private HorizontalPanel toolsPanel;
	private HorizontalPanel horizontalPanel;
	private HorizontalPanel horizontalPanelMenu;
	public MainMenu mainMenu;
	public ToolBar toolBar;
	public TabWorkspace tabWorkspace;
	private Label leftLabel;
	private Label rightLabel;
	private Image horizontalLine;
	
	/**
	 * Top panel
	 */
	public TopPanel() {
		// First initialize language values
		panel = new VerticalPanel();
		horizontalPanel = new HorizontalPanel();
		horizontalPanelMenu = new HorizontalPanel();
		toolsPanel = new HorizontalPanel();
		mainMenu = new MainMenu();
		toolBar = new ToolBar();
		tabWorkspace = new TabWorkspace();
		leftLabel = new Label("");
		rightLabel = new Label("");
		toolsPanel.add(toolBar);
		toolsPanel.add(tabWorkspace);
		toolsPanel.setCellHorizontalAlignment(toolBar, HorizontalPanel.ALIGN_LEFT);
		toolsPanel.setCellVerticalAlignment(tabWorkspace, HorizontalPanel.ALIGN_BOTTOM);
		toolsPanel.setCellHorizontalAlignment(tabWorkspace, HorizontalPanel.ALIGN_RIGHT);
		toolsPanel.setWidth("100%");
		toolsPanel.setCellWidth(toolBar,"100%");
		
		horizontalLine = new Image("img/transparent_pixel.gif");
		horizontalLine.setStyleName("okm-TopPanel-Line-Border");
		horizontalLine.setSize("100%", "2px");
		
		horizontalPanelMenu.add(mainMenu);
		horizontalPanelMenu.setWidth("100%");
		SimplePanel separator = new SimplePanel();
		separator.setWidth("100%");
		horizontalPanelMenu.add(separator);
		Image logo = new Image("img/logon_openkm_tiny.gif");
		horizontalPanelMenu.add(logo);
		horizontalPanelMenu.setCellHorizontalAlignment(logo, HasAlignment.ALIGN_RIGHT);
		horizontalPanelMenu.setCellVerticalAlignment(logo, HasAlignment.ALIGN_MIDDLE);
		panel.setStyleName("okm-TopPanel");
		panel.addStyleName("okm-DisableSelect");
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		panel.setSize("100%", "100%");
		panel.add(horizontalPanelMenu);
		panel.add(horizontalLine);
		panel.add(toolsPanel);
		
		panel.setCellWidth(horizontalLine, "100%");
		
		leftLabel.setStyleName("okm-TopPanel-Border");
		rightLabel.setStyleName("okm-TopPanel-Border");
		leftLabel.setSize("10px", "100%");
		rightLabel.setSize("10px","100%");
		
		horizontalPanel.add(leftLabel);
		horizontalPanel.add(panel);
		horizontalPanel.add(rightLabel);
		
		horizontalPanel.setCellWidth(leftLabel, "10px");
		horizontalPanel.setCellWidth(panel, "100%");
		horizontalPanel.setCellWidth(rightLabel, "10px");
		
		initWidget(horizontalPanel);
	}
	
	/**
	 * Sets the size
	 * 
	 * @param width the width size
	 * @param height the height size
	 */
	public void setSize(int width, int height) {
		horizontalPanel.setSize(""+width, ""+height);
	}
	
	/**
	 * Lang refresh
	 */
	public void langRefresh() {
		mainMenu.langRefresh();
		toolBar.langRefresh();
		tabWorkspace.langRefresh();
	}
}