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

package es.git.openkm.backend.client.widget.propertygroups;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.backend.client.Main;
import es.git.openkm.backend.client.config.Config;
import es.git.openkm.backend.client.service.OKMPropertyGroupService;
import es.git.openkm.backend.client.service.OKMPropertyGroupServiceAsync;

/**
 * PropertyGroup
 * 
 * @author jllort
 *
 */
public class PropertyGroup extends Composite {
	
	private final OKMPropertyGroupServiceAsync propertyGroupService = (OKMPropertyGroupServiceAsync) GWT.create(OKMPropertyGroupService.class);
	
	private VerticalPanel sp;
	private FlexTable table;
	
	/**
	 * PropertyGroup
	 */
	public PropertyGroup() {
		sp = new VerticalPanel();
		table = new FlexTable();
		
		table.setBorderWidth(0);
		table.setCellPadding(4);
		table.setCellSpacing(0);
		
		table.setStyleName("okm-NoWrap");
		table.getCellFormatter().setStyleName(0, 0, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 1, "okm-Table-Title");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 0, "okm-Table-Title-RightBorder");
		
		table.getCellFormatter().setWidth(0, 1, "100%");
		
		sp.setSize("100%","100%");
		sp.add(table);
		
		table.setHTML(0, 0, "<b>" + Main.i18n("group.property.group") + "</b>");
		table.setHTML(0, 1, "");
		
		getAllGroups();
		initWidget(sp);
	}
	
	/**
	 * Gets asyncronous to get all groups
	 */
	final AsyncCallback<List<String>> callbackGetAllGroups = new AsyncCallback<List<String>>() {
		public void onSuccess(List<String> result){
			for (Iterator<String> it = result.iterator(); it.hasNext();) {
				final String group = it.next();
				int rows = table.getRowCount();
				table.setHTML(rows, 0, group);
				Button edit = new Button(Main.i18n("button.view"));
				edit.addClickHandler(new ClickHandler() { 
					@Override
					public void onClick(ClickEvent event) {
						Main.get().centerPanel.propertyGroupPanel.propertyGroupDetail.getMetaData(group);
					}
				});
				edit.setStyleName("okm-Input");
				table.setWidget(rows, 1, edit);
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetAllGroups", caught);
		}
	};
	
	/**
	 * Gets all property groups
	 */
	private void getAllGroups() {
		ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
		endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
		propertyGroupService.getAllGroups(callbackGetAllGroups);
	}
	
	/**
	 * Lang refreshing
	 */
	public void langRefresh() {
		table.setHTML(0, 0, "<b>" + Main.i18n("group.property.group") + "</b>");
		
		if (table.getRowCount()>1) {
			for (int i=1; i<table.getRowCount(); i++) {
				Button edit = (Button) table.getWidget(i,1);
				edit.setHTML(Main.i18n("button.view"));
			}
		}
	}
}