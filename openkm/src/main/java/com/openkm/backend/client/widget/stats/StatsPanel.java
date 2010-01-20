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

package com.openkm.backend.client.widget.stats;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;

import com.openkm.backend.client.Main;
import com.openkm.backend.client.config.Config;
import com.openkm.backend.client.panel.VerticalBorderPanel;
import com.openkm.backend.client.service.OKMRepositoryService;
import com.openkm.backend.client.service.OKMRepositoryServiceAsync;
import com.openkm.backend.client.widget.users.UsersPanel;

/**
 * StatsPanel
 * 
 * @author jllort
 *
 */
public class StatsPanel extends Composite {
	
	private final OKMRepositoryServiceAsync repositoryService = (OKMRepositoryServiceAsync) GWT.create(OKMRepositoryService.class);
	
	private VerticalSplitPanel verticalSplitPanel;
	public GraphicalStats graphicalStats;
	private HorizontalPanel hPanel;
	public UsersPanel usersPanel;
	private FlexTable table;
	private VerticalBorderPanel centerSeparatorPanel;
	private HorizontalPanel leftPanel;
	private HTML uuid;
	private HTML support;
	private HTML version;
	
	/**
	 * UsersPanel
	 */
	public StatsPanel() {
		verticalSplitPanel = new VerticalSplitPanel();
		graphicalStats = new GraphicalStats();
		hPanel = new HorizontalPanel();
		usersPanel = new UsersPanel();
		table = new FlexTable();
		centerSeparatorPanel = new VerticalBorderPanel();
		leftPanel = new HorizontalPanel();
		uuid = new HTML();
		support = new HTML("<a href=\"mailto:support@openkm.com\">support@openkm.com</a>");
		version = new HTML("0.0");
		
		table.setHTML(0, 0, "<b>OpenKM</b>");
		table.setWidget(1, 0, version);
		table.setHTML(2, 0, "<br>");
		table.setHTML(3, 0, "&copy; GIT Consultors S.L.");
		table.setHTML(4, 0, "C/ Los Doscientos, 35 4º Izq");
		table.setHTML(5, 0, "03007 Alicante");
		table.setHTML(6, 0, "Spain");
		table.setHTML(7, 0, "<br>");
		table.setHTML(8, 0, "<br>");
		table.setHTML(9, 0, "<br>");
		table.setHTML(10, 0, "<br>");
		
		table.setHTML(11, 0, "<b>" + Main.i18n("stats.support") + "</b>");
		table.setWidget(12, 0, support);
		table.setHTML(13, 0, "<br>");
		table.setHTML(14, 0, "<b>" + Main.i18n("stats.installation.id") + "</b>");
		table.setWidget(15, 0, uuid);
		
		HTML space = new HTML("");
		leftPanel.add(space);
		leftPanel.add(table);
		
		hPanel.add(leftPanel);
		hPanel.add(centerSeparatorPanel);
		hPanel.add(usersPanel);
		
		verticalSplitPanel.setTopWidget(graphicalStats);
		verticalSplitPanel.setBottomWidget(hPanel);
		verticalSplitPanel.setSplitPosition("265");
		
		graphicalStats.setStyleName("okm-Input");
		leftPanel.setStyleName("okm-Input");
		leftPanel.addStyleName("okm-VerticalBorderPanel");
		
		centerSeparatorPanel.setSize("10","100%");
		usersPanel.setSize("100%","100%");
		leftPanel.setSize("200","100%");
		hPanel.setSize("100%","100%");
		
		hPanel.setCellWidth(leftPanel, "200");
		hPanel.setCellWidth(centerSeparatorPanel,"10");
		hPanel.setCellVerticalAlignment(usersPanel, HasAlignment.ALIGN_TOP);
		leftPanel.setCellVerticalAlignment(table, HasAlignment.ALIGN_TOP);
		leftPanel.setCellWidth(space, "15");
		
		getUuid();
		getAppVersion();
		
		initWidget(verticalSplitPanel);
		
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		graphicalStats.langRefresh();
		usersPanel.langRefresh();
		table.setHTML(11, 0, "<b>" + Main.i18n("stats.support") + "</b>");
		table.setHTML(14, 0, "<b>" + Main.i18n("stats.installation.id") + "</b>");
	}
	
	/**
	 * Get asyncronous uuid
	 */
	final AsyncCallback<String> callbackGetUuid = new AsyncCallback<String>() {
		public void onSuccess(String result) {
			uuid.setHTML(result + "&nbsp;&nbsp;");
			support.setHTML("<a href=\"mailto:support@openkm.com?subject=OpenKM Support - UUID(" + 
							   result + ")\">support@openkm.com</a>");
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getUuid", caught);
		}
	};
	
	/**
	 * Get uuid
	 */
	public void getUuid() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);	
		repositoryService.getUuid(callbackGetUuid);
	}
	
	/**
	 * Get asyncronous version
	 */
	final AsyncCallback<String> callbackGetAppVersion = new AsyncCallback<String>() {
		public void onSuccess(String result) {
			version.setHTML("Version: " + result);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getAppVersion", caught);
		}
	};
	
	/**
	 * Get version
	 */
	public void getAppVersion() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);	
		repositoryService.getAppVersion(callbackGetAppVersion);
	}
}