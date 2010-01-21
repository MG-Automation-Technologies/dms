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

package com.openkm.backend.client.widget.propertygroups;

import java.util.Iterator;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import com.openkm.backend.client.Main;
import com.openkm.backend.client.bean.GWTMetaData;
import com.openkm.backend.client.config.Config;
import com.openkm.backend.client.service.OKMPropertyGroupService;
import com.openkm.backend.client.service.OKMPropertyGroupServiceAsync;

/**
 * PropertyGroupDetail
 * 
 * @author jllort
 *
 */
public class PropertyGroupDetail extends Composite {
	
	private final OKMPropertyGroupServiceAsync propertyGroupService = (OKMPropertyGroupServiceAsync) GWT.create(OKMPropertyGroupService.class);
	
	private VerticalPanel vPanel;
	private FlexTable table;
	private TextBox uniqueId;
	private ListBox typeList;
	private Button addProperty;
	public Status status;
	
	/**
	 * PropertyGroupDetail
	 */
	public PropertyGroupDetail() {
		vPanel = new VerticalPanel();
		table = new FlexTable();
		uniqueId = new TextBox();
		typeList = new ListBox();
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		addProperty = new Button(Main.i18n("button.add"));
		
		addProperty.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {

			}
		});
		
		addProperty.setEnabled(false);
		
		typeList.addItem("","");
		typeList.addItem(Main.i18n("group.property.group.type.textarea"), ""+GWTMetaData.TEXT_AREA);
		typeList.addItem(Main.i18n("group.property.group.type.input"), ""+GWTMetaData.INPUT);
		typeList.addItem(Main.i18n("group.property.group.type.select"), ""+GWTMetaData.SELECT);
		typeList.addItem(Main.i18n("group.property.group.type.select.multiple"), ""+GWTMetaData.SELECT_MULTI);
		
		table.setBorderWidth(0);
		table.setCellPadding(4);
		table.setCellSpacing(0);
		
		table.setStyleName("okm-NoWrap");
		table.setHTML(0, 0, "<b>" + Main.i18n("group.property.unique.id") + "</b>");
		table.setHTML(0, 1, "<b>" + Main.i18n("group.property.type") + "</b>");
		table.setHTML(0, 2, "<b>" + Main.i18n("group.property.values") + "</b>");
		table.setHTML(0, 3, "");
		
		table.getCellFormatter().setStyleName(0, 0, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 1, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 2, "okm-Table-Title");
		table.getCellFormatter().setStyleName(0, 3, "okm-Table-Title");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 3, "okm-Table-Title-LeftBorder");
		table.getCellFormatter().addStyleName(0, 0, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 1, "okm-Table-Title-RightBorder");
		table.getCellFormatter().addStyleName(0, 2, "okm-Table-Title-RightBorder");
		
		table.getCellFormatter().setWidth(0, 3, "100%");
		
		table.setWidget(1, 0, uniqueId);
		table.setWidget(1, 1, typeList);
		table.setWidget(1, 2, addProperty);
		showAddProperty(false); // Hide add property row
		
		vPanel.add(table);
		
		vPanel.setSize("100%","100%");
		
		uniqueId.setStyleName("okm-Input");
		typeList.setStyleName("okm-Input");
		addProperty.setStyleName("okm-Input");
		
		initWidget(vPanel);
	}
	
	/**
	 * Gets asyncronous to get metada group properties
	 */
	final AsyncCallback<Map<String,GWTMetaData>> callbackGetMetaData = new AsyncCallback<Map<String,GWTMetaData>>() {
		public void onSuccess(Map<String,GWTMetaData> result){
			int rows = table.getRowCount();
			FlexCellFormatter cellFormatter = table.getFlexCellFormatter();

			for (Iterator<String> it = result.keySet().iterator(); it.hasNext();) {
				final String propertyName = it.next();
				GWTMetaData gwtMetadata = result.get(propertyName);
				
				table.setHTML(rows, 0, "<b>" + propertyName + "</b>");
				
				switch (gwtMetadata.getType()) {
					case GWTMetaData.TEXT_AREA:
						table.setHTML(rows, 1, Main.i18n("group.property.group.type.textarea"));
						table.setHTML(rows, 2, "");
						rows++;
						break;
				
					case GWTMetaData.INPUT:
						table.setHTML(rows, 1, Main.i18n("group.property.group.type.input"));
						table.setHTML(rows, 2, "");
						rows++;
						break;
						
					case GWTMetaData.SELECT:
						table.setHTML(rows, 1, Main.i18n("group.property.group.type.select"));
						FlexTable tableSelect = new FlexTable();
						tableSelect.setBorderWidth(0);
						tableSelect.setCellPadding(4);
						tableSelect.setCellSpacing(0);
						int tsRows = tableSelect.getRowCount();
						
						for (Iterator<String> itData = gwtMetadata.getValues().iterator(); itData.hasNext(); ){
							String value = itData.next();
							tableSelect.setHTML(tsRows, 0, value);
							tsRows++;
						}
						table.setWidget(rows, 2, tableSelect);
						
						cellFormatter.setVerticalAlignment(rows, 0, HasAlignment.ALIGN_TOP);
						cellFormatter.setVerticalAlignment(rows, 1, HasAlignment.ALIGN_TOP);
						cellFormatter.setVerticalAlignment(rows, 2, HasAlignment.ALIGN_TOP);

						rows++;
						break;
						
					case GWTMetaData.SELECT_MULTI:
						table.setHTML(rows, 1, Main.i18n("group.property.group.type.select.multiple"));
						FlexTable tableSelectMultiple = new FlexTable();
						tableSelectMultiple.setBorderWidth(0);
						tableSelectMultiple.setCellPadding(4);
						tableSelectMultiple.setCellSpacing(0);
						int tsmRows = tableSelectMultiple.getRowCount();

						for (Iterator<String> itData = gwtMetadata.getValues().iterator(); itData.hasNext(); ){
							String value = itData.next();
							tableSelectMultiple.setHTML(tsmRows, 0, value);
							tsmRows++;
						}
						table.setWidget(rows, 2, tableSelectMultiple);
						
						cellFormatter.setVerticalAlignment(rows, 0, HasAlignment.ALIGN_TOP);
						cellFormatter.setVerticalAlignment(rows, 1, HasAlignment.ALIGN_TOP);
						cellFormatter.setVerticalAlignment(rows, 2, HasAlignment.ALIGN_TOP);
						
						rows++;
						break;
				}
			}
			status.unsetFlag_properties();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getMetaData", caught);
			status.unsetFlag_properties();
		}
	};
	
	/**
	 * Gets all metadata group properties 
	 * 
	 * @param grpName The property group name
	 */
	public void getMetaData(String grpName) {
		status.setFlag_properties();
		status.refresh(vPanel);
		removeAllRows();
		//showAddProperty(true); // Show add property row
		ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
		endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
		propertyGroupService.getMetaData(grpName, callbackGetMetaData);
	}
	
	/**
	 * removeAllRows
	 */
	private void removeAllRows() {
		while (table.getRowCount()>3) {
			table.removeRow(2);
		}
	}
	
	/**
	 * Sets visible the row to add properties
	 * 
	 * @param visible
	 */
	private void showAddProperty(boolean visible) {
		table.getRowFormatter().setVisible(1, visible);
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		table.setHTML(0, 0, "<b>" + Main.i18n("group.property.unique.id") + "</b>");
		table.setHTML(0, 1, "<b>" + Main.i18n("group.property.type") + "</b>");
		table.setHTML(0, 2, "<b>" + Main.i18n("group.property.values") + "</b>");
		addProperty.setHTML(Main.i18n("button.add"));
		
		typeList.setItemText(1, Main.i18n("group.property.group.type.textarea"));
		typeList.setItemText(2, Main.i18n("group.property.group.type.input"));
		typeList.setItemText(3, Main.i18n("group.property.group.type.select"));
		typeList.setItemText(4, Main.i18n("group.property.group.type.select.multiple"));
	}
}