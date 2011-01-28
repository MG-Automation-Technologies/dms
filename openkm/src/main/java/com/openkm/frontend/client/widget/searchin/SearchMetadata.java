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

package com.openkm.frontend.client.widget.searchin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTInput;
import com.openkm.frontend.client.bean.GWTOption;
import com.openkm.frontend.client.bean.GWTPropertyParams;
import com.openkm.frontend.client.bean.GWTSelect;
import com.openkm.frontend.client.bean.GWTTextArea;
import com.openkm.frontend.client.util.OKMBundleResources;

/**
 * SearchMetadata
 * 
 * @author jllort
 *
 */
public class SearchMetadata extends Composite {
	
	private ScrollPanel scrollPanel;
	private FlexTable table;
	private GroupPopup groupPopup;
	public Button addGroup;
	public Map<String, GWTPropertyParams> hProperties = new HashMap<String, GWTPropertyParams>();
	public Map<String, Widget> hWidgetProperties = new HashMap<String, Widget>();
	public FlexTable tableProperties;
	
	/**
	 * SearchMetadata
	 */
	public SearchMetadata() {
		table = new FlexTable();
		scrollPanel = new ScrollPanel(table);
		tableProperties = new FlexTable();
		
		// Table padding and spacing properties
		tableProperties.setCellPadding(0);
		tableProperties.setCellSpacing(0);
		
		groupPopup = new GroupPopup();
		groupPopup.setWidth("300px");
		groupPopup.setHeight("125px");
		groupPopup.setStyleName("okm-Popup");
		groupPopup.addStyleName("okm-DisableSelect");
		
		addGroup = new Button(Main.i18n("search.add.property.group"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				groupPopup.show();
			}
		});
		
		table.setWidget(0, 0, addGroup);
		table.setWidget(1, 0, tableProperties);
		
		addGroup.setStyleName("okm-Button");
		addGroup.addStyleName("okm-NoWrap");
		
		initWidget(scrollPanel);
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		addGroup.setHTML(Main.i18n("search.add.property.group"));
		groupPopup.langRefresh();
	}
	
	/**
	 * Add property group
	 * 
	 * @param grpName Group key
	 * @param propertyName Property group key
	 * @param gwtMetadata Property metada
	 * @param propertyValue The selected value
	 */
	public void addProperty(final GWTPropertyParams propertyParam){
		int rows = tableProperties.getRowCount();
		Image removeImage;
		
		if (!hWidgetProperties.containsKey(propertyParam.getFormElement().getName())) {
		
			if (propertyParam.getFormElement() instanceof GWTInput || propertyParam.getFormElement() instanceof GWTTextArea) {
				TextBox textBox = new TextBox(); // Create a widget for this property
				textBox.setStyleName("okm-Input");
				textBox.addKeyPressHandler(Main.get().mainPanel.search.searchBrowser.searchIn.searchControl.keyPressHandler);
				textBox.addKeyUpHandler(Main.get().mainPanel.search.searchBrowser.searchIn.searchControl.keyUpHandler);
				hProperties.put(propertyParam.getFormElement().getName(),propertyParam);
				hWidgetProperties.put(propertyParam.getFormElement().getName(),textBox);
				tableProperties.setHTML(rows, 0, "<b>" + propertyParam.getGrpLabel() + " :</b>");
				tableProperties.setHTML(rows, 1, "&nbsp;" + propertyParam.getFormElement().getLabel() + "&nbsp;");
				tableProperties.setWidget(rows, 2, textBox);
				
				removeImage = new Image(OKMBundleResources.INSTANCE.deleteIcon());
				removeImage.addClickHandler(new ClickHandler() { 
					@Override
					public void onClick(ClickEvent event) {
						Widget sender = (Widget) event.getSource();
						Main.get().mainPanel.search.searchBrowser.searchIn.removeProperty(sender,propertyParam.getFormElement().getName());
						groupPopup.enableAddGroupButton(); // Enables or disables button ( depends exist some item on list to be added )
					}
				});
				
				tableProperties.setWidget(rows, 3, removeImage);
				setRowWordWarp(tableProperties, rows, 2, false);
				removeImage.addStyleName("okm-Hyperlink");
				
				if (propertyParam.getValue()!=null && !propertyParam.getValue().equals("")) {
					textBox.setText(propertyParam.getValue());
				}
				
			} else if (propertyParam.getFormElement() instanceof GWTSelect) {
				GWTSelect gwtSelect = (GWTSelect) propertyParam.getFormElement();
				ListBox listBox = new ListBox();
				listBox.setStyleName("okm-Select");
				listBox.addItem("",""); // Always we set and empty value
				listBox.addChangeHandler(new ChangeHandler() {
					@Override
					public void onChange(ChangeEvent event) {
						Main.get().mainPanel.search.searchBrowser.searchIn.searchControl.evaluateSearchButtonVisible();							
					}
				});
				
				for (Iterator<GWTOption> itData = gwtSelect.getOptions().iterator(); itData.hasNext(); ) {
					GWTOption option = itData.next();
					String value = option.getValue();
					listBox.addItem(option.getLabel(), value); // The translation is composed by propertyName + "." + value key
					
					// Selects the selected value
					if (propertyParam.getValue()!=null && !propertyParam.getValue().equals("") && propertyParam.getValue().equals(value)) {
						listBox.setSelectedIndex(listBox.getItemCount()-1);
					}
				}
				
				hProperties.put(propertyParam.getFormElement().getName(),propertyParam);
				hWidgetProperties.put(propertyParam.getFormElement().getName(),listBox);
				
				tableProperties.setHTML(rows, 0, "<b>" + propertyParam.getGrpLabel() + " : </b>");
				tableProperties.setHTML(rows, 1, "&nbsp;" + propertyParam.getFormElement().getLabel() + "&nbsp;");
				tableProperties.setWidget(rows, 2, listBox);
				
				removeImage = new Image(OKMBundleResources.INSTANCE.deleteIcon());
				removeImage.addStyleName("okm-Hyperlink");
				removeImage.addClickHandler(new ClickHandler() { 
					@Override
					public void onClick(ClickEvent event) {
						Widget sender = (Widget) event.getSource();
						Main.get().mainPanel.search.searchBrowser.searchIn.removeProperty(sender, propertyParam.getFormElement().getName());
						groupPopup.enableAddGroupButton(); // Enables or disables button ( depends exist some item on list to be added )
					}
				});
				
				tableProperties.setWidget(rows, 3, removeImage);
				setRowWordWarp(tableProperties, rows, 2, false);					
			}
		}
	}
	
	/**
	 * Set the WordWarp for all the row cells
	 * 
	 * @param row The row cell
	 * @param columns Number of row columns
	 * @param warp
	 */
	private void setRowWordWarp(FlexTable table, int row, int columns, boolean wrap) {
		CellFormatter cellFormatter = table.getCellFormatter();
		for (int i=0; i<columns; i++) {
			cellFormatter.setWordWrap(row, i, wrap);
		}
	}
}