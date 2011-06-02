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

package com.openkm.frontend.client.panel.top;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.contants.ui.UIDockPanelConstants;
import com.openkm.frontend.client.util.OKMBundleResources;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.TabWorkspace;
import com.openkm.frontend.client.widget.mainmenu.MainMenu;
import com.openkm.frontend.client.widget.toolbar.ToolBar;

/**
 * Top panel
 * 
 * @author jllort
 *
 */
public class TopPanel extends Composite {
	
	public static final int PANEL_HEIGHT = 55 + (Util.getUserAgent().equals("gecko")?2:0);
	
	private VerticalPanel panel;
	private HorizontalPanel toolsPanel;
	private HorizontalPanel horizontalPanel;
	private HorizontalPanel horizontalPanelMenu;
	private HorizontalPanel quickSearchPanel;
	public MainMenu mainMenu;
	public ToolBar toolBar;
	public TabWorkspace tabWorkspace;
	private Label leftLabel;
	private Label rightLabel;
	private Image horizontalLine;
	private TextBox quickSearch;
	private Image searchImage;
	
	
	/**
	 * Top panel
	 */
	public TopPanel() {
		// First initialize language values
		panel = new VerticalPanel();
		horizontalPanel = new HorizontalPanel();
		horizontalPanelMenu = new HorizontalPanel();
		quickSearchPanel = new HorizontalPanel();
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
		
		quickSearch = new TextBox();
		quickSearch.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (quickSearch.getText().length()>=3) {
					searchImage.setResource(OKMBundleResources.INSTANCE.search());
				} else {
					searchImage.setResource(OKMBundleResources.INSTANCE.searchDisabled());
				}
				if ((char)KeyCodes.KEY_ENTER == event.getNativeKeyCode()) {
					executeQuickSearch();
				}
			}
		});
		quickSearch.setWidth("179");
		quickSearch.setStyleName("okm-Input");
		searchImage = new Image(OKMBundleResources.INSTANCE.searchDisabled());
		searchImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				executeQuickSearch();
			}
		});
		searchImage.setStyleName("okm-Hyperlink");
		quickSearchPanel.add(quickSearch);
		quickSearchPanel.add(Util.hSpace("5"));
		quickSearchPanel.add(searchImage);
		quickSearchPanel.add(Util.hSpace("25"));
		quickSearchPanel.setCellVerticalAlignment(searchImage, HasAlignment.ALIGN_MIDDLE);
		
		horizontalPanelMenu.add(mainMenu);
		horizontalPanelMenu.setWidth("100%");
		SimplePanel separator = new SimplePanel();
		separator.setWidth("100%");
		horizontalPanelMenu.add(separator);
		horizontalPanelMenu.add(quickSearchPanel);
		Image logo = new Image(OKMBundleResources.INSTANCE.logoOpenKM());
		horizontalPanelMenu.add(logo);
		horizontalPanelMenu.setCellHorizontalAlignment(quickSearchPanel, HasAlignment.ALIGN_RIGHT);
		horizontalPanelMenu.setCellVerticalAlignment(quickSearchPanel, HasAlignment.ALIGN_MIDDLE);
		horizontalPanelMenu.setCellHorizontalAlignment(logo, HasAlignment.ALIGN_RIGHT);
		horizontalPanelMenu.setCellVerticalAlignment(logo, HasAlignment.ALIGN_MIDDLE);
		horizontalPanelMenu.setCellWidth(logo, "40");
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
		leftLabel.setPixelSize(10, PANEL_HEIGHT);
		rightLabel.setPixelSize(10, PANEL_HEIGHT);
		
		horizontalPanel.add(leftLabel);
		horizontalPanel.add(panel);
		horizontalPanel.add(rightLabel);
		
		horizontalPanel.setCellWidth(leftLabel, "10px");
		horizontalPanel.setCellWidth(panel, "100%");
		horizontalPanel.setCellWidth(rightLabel, "10px");
		
		horizontalPanel.setHeight(""+PANEL_HEIGHT);
		
		initWidget(horizontalPanel);
	}
	
	/**
	 * executeQuickSearch
	 */
	private void executeQuickSearch() {
		if (quickSearch.getText().length()>=3) {
			Main.get().mainPanel.topPanel.tabWorkspace.changeSelectedTab(UIDockPanelConstants.SEARCH);
			Main.get().mainPanel.search.searchBrowser.searchIn.setQuickSearch(quickSearch.getText());
			quickSearch.setText("");
			searchImage.setResource(OKMBundleResources.INSTANCE.searchDisabled());
		}
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