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

package com.openkm.frontend.client.widget.propertygroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTCheckBox;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTFormElement;
import com.openkm.frontend.client.bean.GWTInput;
import com.openkm.frontend.client.bean.GWTOption;
import com.openkm.frontend.client.bean.GWTSelect;
import com.openkm.frontend.client.bean.GWTTextArea;
import com.openkm.frontend.client.config.Config;
import com.openkm.frontend.client.extension.event.HasPropertyGroupEvent;
import com.openkm.frontend.client.extension.event.handler.PropertyGroupHandlerExtension;
import com.openkm.frontend.client.extension.event.hashandler.HasPropertyGroupHandlerExtension;
import com.openkm.frontend.client.service.OKMPropertyGroupService;
import com.openkm.frontend.client.service.OKMPropertyGroupServiceAsync;
import com.openkm.frontend.client.util.CommonUI;
import com.openkm.frontend.client.util.OKMBundleResources;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.searchin.CalendarWidget;

/**
 * PropertyGroupWidget
 * 
 * @author jllort
 *
 */
public class PropertyGroupWidget extends Composite implements HasPropertyGroupEvent, HasPropertyGroupHandlerExtension  {
	
	private final OKMPropertyGroupServiceAsync propertyGroupService = (OKMPropertyGroupServiceAsync) GWT.create(OKMPropertyGroupService.class);
	
	private FlexTable table;
	private String grpName;
	private String docPath;
	private List<GWTFormElement> formElementList = new ArrayList<GWTFormElement>();
	private Map<String, Widget> hWidgetProperties = new HashMap<String, Widget>();
	private CellFormatter cellFormatter;
	private PropertyGroupWidgetToFire propertyGroupWidgetToFire;
	private List<PropertyGroupHandlerExtension> propertyGroupHandlerExtensionList;
	private FolderSelectPopup folderSelectPopup;
	
	/**
	 * PropertyGroup
	 * 
	 * @param docPath The document path
	 * @param grpName The group name
	 * @param widget Widget at first row
	 * @param PropertyGroupWidgetToFire widget with methods to be fired
	 */
	public PropertyGroupWidget(String docPath, String grpName, Widget widget, PropertyGroupWidgetToFire propertyGroupWidgetToFire) {	
		propertyGroupHandlerExtensionList = new ArrayList<PropertyGroupHandlerExtension>();
		table = new FlexTable();
		folderSelectPopup = new FolderSelectPopup();
		this.docPath = docPath;
		this.grpName = grpName;
		this.propertyGroupWidgetToFire = propertyGroupWidgetToFire;

		table.setWidth("100%");

		table.setWidget(0, 0, widget);
		table.getFlexCellFormatter().setColSpan(0,0,2);
			
		// Widget format
		table.getCellFormatter().setHorizontalAlignment(0,0,HasAlignment.ALIGN_CENTER);
		table.getCellFormatter().setVerticalAlignment(0,0,HasAlignment.ALIGN_MIDDLE);
		
		RowFormatter rowFormatter = table.getRowFormatter();
		rowFormatter.setStyleName(0, "okm-Security-Title");
		
		cellFormatter = table.getCellFormatter(); // Gets the cell formatter
			
		// Format borders and margins
		cellFormatter.addStyleName(0,0,"okm-Security-Title-RightBorder");
		
		folderSelectPopup.setStyleName("okm-Popup");
		folderSelectPopup.addStyleName("okm-DisableSelect");
		
		initWidget(table);
	}
	
	/**
	 * Set the WordWarp for all the row cells
	 * 
	 * @param row The row cell
	 * @param columns Number of row columns
	 * @param warp
	 */
	private void setRowWordWarp(int row, int columns, boolean warp) {
		for (int i=0; i<columns; i++){
			cellFormatter.setWordWrap(row, i, false);
		}
	}
	
	/**
	 * Set the WordWarp for all the row cells
	 * 
	 * @param table FlexTable The table to format
	 * @param row The row cell
	 * @param columns Number of row columns
	 * @param warp
	 */
	private void setRowWordWarp(FlexTable table, int row, int columns, boolean warp) {
		for (int i=0; i<columns; i++){
			table.getCellFormatter().setWordWrap(row, i, false);
		}
	}

	/**
	 * Gets asyncronous to group properties
	 */
	final AsyncCallback<List<GWTFormElement>> callbackGetProperties = new AsyncCallback<List<GWTFormElement>>() {
		public void onSuccess(List<GWTFormElement> result){
			formElementList = result;
			hWidgetProperties.clear();
			int rows = 1;

			for (Iterator<GWTFormElement> it = result.iterator(); it.hasNext(); ) {
				drawFormElement(rows,it.next());
				rows++;
			}
			
			if (propertyGroupWidgetToFire!=null) {
				propertyGroupWidgetToFire.finishedGetProperties();
			}
			fireEvent(HasPropertyGroupEvent.PROPERTYGROUP_GET_PROPERTIES);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getMetaData", caught);
			if (propertyGroupWidgetToFire!=null) {
				propertyGroupWidgetToFire.finishedGetProperties();
			}
		}
	};
	
	/**
	 * Gets asyncronous to set roperties
	 */
	final AsyncCallback<Object> callbackSetProperties = new AsyncCallback<Object>() {
		public void onSuccess(Object result){
			if (propertyGroupWidgetToFire!=null) {
				propertyGroupWidgetToFire.finishedSetProperties();
			}
			fireEvent(HasPropertyGroupEvent.PROPERTYGROUP_CHANGED);
		}

		public void onFailure(Throwable caught) {
			if (propertyGroupWidgetToFire!=null) {
				propertyGroupWidgetToFire.finishedSetProperties();
			}
			Main.get().showError("setProperties", caught);
		}
	};
	
	/**
	 * Gets asyncronous to remove document group properties
	 */
	final AsyncCallback<Object> callbackRemoveGroup = new AsyncCallback<Object>() {
		public void onSuccess(Object result){
			fireEvent(HasPropertyGroupEvent.PROPERTYGROUP_REMOVED);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("callbackRemoveGroup", caught);
		}
	};
	
	/**
	 * Edit values
	 */
	public void edit(){
		int rows = 1;

		for (Iterator<GWTFormElement> it = formElementList.iterator(); it.hasNext();) {
			GWTFormElement formField = it.next();
			
			if (formField instanceof GWTTextArea) {
				TextArea textArea = (TextArea) hWidgetProperties.get(formField.getName());
				table.setWidget(rows, 1, textArea);
				
			} else if (formField instanceof GWTInput) {
				HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formField.getName());
				table.setWidget(rows, 1, hPanel);
				
			} else if (formField instanceof GWTCheckBox) {
				CheckBox checkBox = (CheckBox) hWidgetProperties.get(formField.getName());
				table.setWidget(rows, 1, checkBox);
				
			} else if (formField instanceof GWTSelect) {
				GWTSelect gwtSelect = (GWTSelect) formField;
				if (gwtSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
					ListBox listBox = (ListBox) hWidgetProperties.get(formField.getName());
					table.setWidget(rows, 1, listBox);
					
				} else if (gwtSelect.getType().equals(GWTSelect.TYPE_MULTIPLE)) {
					HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formField.getName());
					FlexTable tableMulti = (FlexTable) hPanel.getWidget(0);
					ListBox listMulti = (ListBox) hPanel.getWidget(2);
					Button addButton = (Button) hPanel.getWidget(4);
					
					// Only it there's some element to assign must set it visible.
					if (listMulti.getItemCount()>1) {
						listMulti.setVisible(true);
						addButton.setVisible(true);
					}
					
					// Enables deleting  option
					for (int i=0; i<tableMulti.getRowCount(); i++) {
						((Image) tableMulti.getWidget(i,1)).setVisible(true);
					}
					table.setWidget(rows, 1, hPanel);
				}
			}
			rows++;
		}
		
		fireEvent(HasPropertyGroupEvent.PROPERTYGROUP_EDIT);
	}
	
	/**
	 * Sets the properties values
	 */
	public void setProperties() {
		int rows = 1;
		
		for (Iterator<GWTFormElement> it = formElementList.iterator(); it.hasNext();) {
			GWTFormElement formElement = it.next();
			
			if (formElement instanceof GWTTextArea) {
				TextArea textArea = (TextArea) hWidgetProperties.get(formElement.getName());
				((GWTTextArea) formElement).setValue(textArea.getText());

			} else if (formElement instanceof GWTInput) {
				HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formElement.getName());
				TextBox textBox = (TextBox) hPanel.getWidget(0);
				if (((GWTInput) formElement).getType().equals(GWTInput.TYPE_LINK)) {
					// Always must start with http://
					if (!textBox.getText().equals("") && !textBox.getText().startsWith("http://")) {
						textBox.setText("http://" + textBox.getText());
					}
				} 
				((GWTInput) formElement).setValue(textBox.getText()); // note that date is added by click handler in drawform method

			} else if (formElement instanceof GWTCheckBox) {	
				CheckBox checkbox = (CheckBox) hWidgetProperties.get(formElement.getName());
				((GWTCheckBox) formElement).setValue(checkbox.getValue());
					
			} else if (formElement instanceof GWTSelect) {
				GWTSelect gwtSelect = (GWTSelect) formElement;
				if (gwtSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
					ListBox listBox = (ListBox) hWidgetProperties.get(formElement.getName());
					String selectedValue = "";
					if (listBox.getSelectedIndex()>0) {
						selectedValue = listBox.getValue(listBox.getSelectedIndex());
					}
					for (Iterator<GWTOption> itOptions = gwtSelect.getOptions().iterator(); itOptions.hasNext();) {
						GWTOption option = itOptions.next();
						if (option.getValue().equals(selectedValue)) {
							option.setSelected(true);
						} else {
							option.setSelected(false);
						}
					}
					
				} else if (gwtSelect.getType().equals(GWTSelect.TYPE_MULTIPLE)) {
					HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formElement.getName());
					FlexTable tableMulti = (FlexTable) hPanel.getWidget(0);		
					
					// Disables all options
					for (Iterator<GWTOption> itOptions = gwtSelect.getOptions().iterator(); itOptions.hasNext();) {
						itOptions.next().setSelected(false);
					}
					
					// Enables options
					if (tableMulti.getRowCount()>0) {
						for (int i=0; i<tableMulti.getRowCount(); i++) {
							String selectedValue = tableMulti.getText(i,0);
							for (Iterator<GWTOption> itOptions = gwtSelect.getOptions().iterator(); itOptions.hasNext();) {
								GWTOption option = itOptions.next();
								if (option.getValue().equals(selectedValue)) {
									option.setSelected(true);
								} 
							}
						}
					} 
				}
			}
			
			drawFormElement(rows, formElement);
			rows ++;
		}

		ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
		endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
		propertyGroupService.setProperties(docPath, grpName, formElementList, callbackSetProperties);
	}
	
	
	/**
	 * Cancel edition and restores values
	 */
	public void cancelEdit() {
		int rows = 1;
		for (Iterator<GWTFormElement> it = formElementList.iterator(); it.hasNext();) {
			drawFormElement(rows, it.next());
			rows++;
		}
		fireEvent(HasPropertyGroupEvent.PROPERTYGROUP_CANCEL_EDIT);
	}
	
	/**
	 * drawFormElement
	 * 
	 * @param row
	 * @param gwtMetadata
	 */
	private void drawFormElement(int row, final GWTFormElement gwtMetadata) {
		final String propertyName = gwtMetadata.getName();
		
		if (gwtMetadata instanceof GWTTextArea) {
			TextArea textArea = new TextArea();
			textArea.setStyleName("okm-Input");
			textArea.setText(((GWTTextArea) gwtMetadata).getValue());
			textArea.setSize(gwtMetadata.getWidth(), gwtMetadata.getHeight());
			HTML text = new HTML(); // Create a widget for this property
			text.setHTML(((GWTTextArea) gwtMetadata).getValue().replaceAll("\n", "<br>"));
			hWidgetProperties.put(propertyName,textArea);
			table.setHTML(row, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
			table.setWidget(row, 1, text);
			table.getCellFormatter().setVerticalAlignment(row,0,VerticalPanel.ALIGN_TOP);
			table.getCellFormatter().setWidth(row, 1, "100%");
			setRowWordWarp(row, 2, true);
			
		} else if (gwtMetadata instanceof GWTInput) {
			HorizontalPanel hPanel = new HorizontalPanel();
			final TextBox textBox = new TextBox(); // Create a widget for this property
			hPanel.add(textBox);
			String value = "";
			if (((GWTInput) gwtMetadata).getType().equals(GWTInput.TYPE_TEXT) || 
				((GWTInput) gwtMetadata).getType().equals(GWTInput.TYPE_LINK) ||
				((GWTInput) gwtMetadata).getType().equals(GWTInput.TYPE_FOLDER)) {
				textBox.setText(((GWTInput) gwtMetadata).getValue());
				value = ((GWTInput) gwtMetadata).getValue();
			} else if (((GWTInput) gwtMetadata).getType().equals(GWTInput.TYPE_DATE)) {
				if (((GWTInput) gwtMetadata).getDate()!=null) {
					DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.day.pattern"));
					textBox.setText(dtf.format(((GWTInput) gwtMetadata).getDate()));
					value = dtf.format(((GWTInput) gwtMetadata).getDate());
				}
			} 
			textBox.setWidth(gwtMetadata.getWidth());
			textBox.setStyleName("okm-Input");
			hWidgetProperties.put(propertyName,hPanel);
			table.setHTML(row, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
			table.setHTML(row, 1, value);
			if (((GWTInput) gwtMetadata).getType().equals(GWTInput.TYPE_DATE)) {
				final PopupPanel calendarPopup = new PopupPanel(true);
				final CalendarWidget calendar = new CalendarWidget();
				calendar.addChangeHandler(new ChangeHandler(){
					@Override
					public void onChange(ChangeEvent event) {
						calendarPopup.hide();
						DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.day.pattern"));
						textBox.setText(dtf.format(calendar.getDate()));
						((GWTInput) gwtMetadata).setDate(calendar.getDate());
					}
				});
				calendarPopup.add(calendar);
				final Image calendarIcon = new Image(OKMBundleResources.INSTANCE.calendar());
				calendarIcon.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						calendarPopup.setPopupPosition(calendarIcon.getAbsoluteLeft(), calendarIcon.getAbsoluteTop()-2);
						calendarPopup.show();
					}
				});
				hPanel.add(new HTML("&nbsp;"));
				hPanel.add(calendarIcon);
				textBox.setEnabled(false);
			} else if (((GWTInput) gwtMetadata).getType().equals(GWTInput.TYPE_LINK)) {
				if (!value.equals("")) {
					HorizontalPanel hLinkPanel = new HorizontalPanel();
					Anchor anchor = new Anchor(value, true);
					final String url = value;
					anchor.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							Window.open(url, url, "");
						}
					});
					String containerName = ((GWTInput) gwtMetadata).getName() + "ContainerName";
					hLinkPanel.add(new HTML("<div id=\""+containerName+"\"></div>\n"));
					HTML space = new HTML("");
					hLinkPanel.add(space);
					hLinkPanel.add(anchor);
					hLinkPanel.setCellWidth(space, "5px");
					table.setWidget(row, 1, hLinkPanel);
					Util.createLinkClipboardButton(url, containerName);
				} else {
					table.setHTML(row, 1, "");
				}
			} else if (((GWTInput) gwtMetadata).getType().equals(GWTInput.TYPE_FOLDER)) {
				if (!value.equals("")) {
					Anchor anchor = new Anchor();
					final GWTFolder folder = ((GWTInput) gwtMetadata).getFolder();
					String path = value.substring(value.indexOf("/",1)+1); // removes first ocurrence
					// Looks if must change icon on parent if now has no childs and properties with user security atention
					if (folder.getHasChilds()) {
						anchor.setHTML(Util.imageItemHTML("img/menuitem_childs.gif", path, "top"));
					} else {
						anchor.setHTML(Util.imageItemHTML("img/menuitem_empty.gif", path, "top"));
					}
					
					anchor.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent arg0) {
							CommonUI.openAllFolderPath(folder.getPath(), null);
						}
					});
					anchor.setStyleName("okm-KeyMap-ImageHover");
					table.setWidget(row, 1, anchor);
				} else {
					table.setHTML(row, 1, "");
				}
				Image pathExplorer = new Image(OKMBundleResources.INSTANCE.folderExplorer());
				pathExplorer.addClickHandler(new ClickHandler() { 
					@Override
					public void onClick(ClickEvent event) {
						folderSelectPopup.show(textBox, ((GWTInput) gwtMetadata).getFolder());
					}
				});
				Image cleanPathExplorer = new Image(OKMBundleResources.INSTANCE.deleteIcon());
				cleanPathExplorer.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						textBox.setValue("");
						((GWTInput) gwtMetadata).setFolder(new GWTFolder());
					}
				});
				pathExplorer.setStyleName("okm-KeyMap-ImageHover");
				cleanPathExplorer.setStyleName("okm-KeyMap-ImageHover");
				hPanel.add(new HTML("&nbsp;"));
				hPanel.add(pathExplorer);
				hPanel.add(new HTML("&nbsp;"));
				hPanel.add(cleanPathExplorer);
				hPanel.setCellVerticalAlignment(pathExplorer, HasAlignment.ALIGN_MIDDLE);
				hPanel.setCellVerticalAlignment(cleanPathExplorer, HasAlignment.ALIGN_MIDDLE);
				textBox.setEnabled(false);
			}
			
			table.getCellFormatter().setVerticalAlignment(row,0,VerticalPanel.ALIGN_TOP);
			table.getCellFormatter().setWidth(row, 1, "100%");
				
		} else if (gwtMetadata instanceof GWTCheckBox) {
			CheckBox checkBox = new CheckBox();
			checkBox.setValue(((GWTCheckBox)gwtMetadata).getValue());
			hWidgetProperties.put(propertyName,checkBox);
			table.setHTML(row, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
			if (checkBox.getValue()) {
				table.setWidget(row, 1, new Image(OKMBundleResources.INSTANCE.yes()));
			} else {
				table.setWidget(row, 1, new Image(OKMBundleResources.INSTANCE.no()));
			}
			table.getCellFormatter().setVerticalAlignment(row,0,VerticalPanel.ALIGN_TOP);
			table.getCellFormatter().setWidth(row, 1, "100%");
			setRowWordWarp(row, 2, true);
			
		} else if (gwtMetadata instanceof GWTSelect) {
			final GWTSelect gwtSelect = (GWTSelect) gwtMetadata;
			if (gwtSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
				String selectedLabel = "";
				ListBox listBox = new ListBox();
				listBox.setStyleName("okm-Select");
				listBox.addItem("", ""); // Always we set and empty value
				
				for (Iterator<GWTOption> itData = gwtSelect.getOptions().iterator(); itData.hasNext(); ){
					GWTOption option = itData.next();
					listBox.addItem(option.getLabel(), option.getValue()); 
					if (option.isSelected()) {
						listBox.setItemSelected(listBox.getItemCount()-1, true);
						selectedLabel = option.getLabel();
					}
				}
				
				hWidgetProperties.put(propertyName,listBox);
				
				table.setHTML(row, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
				table.setHTML(row, 1, selectedLabel);
				table.getCellFormatter().setWidth(row, 1, "100%");
				setRowWordWarp(row, 2, true);
				
			} else if (gwtSelect.getType().equals(GWTSelect.TYPE_MULTIPLE)) {
				final HorizontalPanel hPanel = new HorizontalPanel();
				ListBox listMulti = new ListBox();
				listMulti.setStyleName("okm-Select");
				listMulti.addItem("",""); // Always we set and empty value
				
				// Table for values
				FlexTable tableMulti = new FlexTable();
				
				Button addButton = new Button(Main.i18n("button.add"),new ClickHandler() { 
					@Override
					public void onClick(ClickEvent event) {
						HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(propertyName);
						FlexTable tableMulti = (FlexTable) hPanel.getWidget(0);
						ListBox listMulti = (ListBox) hPanel.getWidget(2);
						Button addButton = (Button) hPanel.getWidget(4);
						
						if (listMulti.getSelectedIndex()>0) {
							final HTML htmlValue = new HTML(listMulti.getValue(listMulti.getSelectedIndex()));
							int rowTableMulti  = tableMulti.getRowCount();
							
							Image removeImage = new Image(OKMBundleResources.INSTANCE.deleteIcon());
							removeImage.addClickHandler(new ClickHandler() { 
								@Override
								public void onClick(ClickEvent event) {
									Widget sender = (Widget) event.getSource();
									HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(propertyName);
									FlexTable tableMulti = (FlexTable) hPanel.getWidget(0);
									ListBox listMulti = (ListBox) hPanel.getWidget(2);
									Button addButton = (Button) hPanel.getWidget(4);
									String value = htmlValue.getText();
									
									String optionLabel = "";
									for (Iterator<GWTOption> itOptions = gwtSelect.getOptions().iterator(); itOptions.hasNext();) {
										GWTOption option = itOptions.next();
										if (option.getValue().equals(htmlValue.getText())) {
											optionLabel = option.getLabel();
											break;
										} 
									} 
									
									listMulti.addItem(optionLabel, value);
									listMulti.setVisible(true);
									addButton.setVisible(true);
									
									// Looking for row to delete 
									for (int i=0; i<tableMulti.getRowCount(); i++){
										if (tableMulti.getWidget(i,1).equals(sender)) {
											tableMulti.removeRow(i);
										}
									}
								}
							});
							
							tableMulti.setWidget(rowTableMulti,0,htmlValue);
							tableMulti.setWidget(rowTableMulti,1,removeImage);
							tableMulti.setHTML(rowTableMulti,2, listMulti.getItemText(listMulti.getSelectedIndex()));
							
							setRowWordWarp(tableMulti,rowTableMulti, 2, true);
							listMulti.removeItem(listMulti.getSelectedIndex());
							htmlValue.setVisible(false);
							if (listMulti.getItemCount()<=1) {
								listMulti.setVisible(false);
								addButton.setVisible(false);
							}
						}
					}
				});
				addButton.setStyleName("okm-Button");
				
				hPanel.add(tableMulti);
				hPanel.add(new HTML("&nbsp;"));
				hPanel.add(listMulti);
				hPanel.add(new HTML("&nbsp;"));
				hPanel.add(addButton);
				hPanel.setVisible(true);
				listMulti.setVisible(false);
				addButton.setVisible(false);
				hPanel.setCellVerticalAlignment(tableMulti,VerticalPanel.ALIGN_TOP);
				hPanel.setCellVerticalAlignment(listMulti,VerticalPanel.ALIGN_TOP);
				hPanel.setCellVerticalAlignment(addButton,VerticalPanel.ALIGN_TOP);
				hPanel.setHeight("100%");

				table.setHTML(row, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
				table.setWidget(row, 1, hPanel);
				table.getCellFormatter().setVerticalAlignment(row,0,VerticalPanel.ALIGN_TOP);
				table.getCellFormatter().setVerticalAlignment(row,1,VerticalPanel.ALIGN_TOP);
				table.getCellFormatter().setWidth(row, 1, "100%");
				
				for (Iterator<GWTOption> itData = gwtSelect.getOptions().iterator(); itData.hasNext(); ){
					final GWTOption option = itData.next();
					
					// Looks if there's some selected value
					if (option.isSelected()) {
						int rowTableMulti = tableMulti.getRowCount();
						HTML htmlValue = new HTML(option.getValue());
						
						Image removeImage = new Image(OKMBundleResources.INSTANCE.deleteIcon());
						removeImage.addClickHandler(new ClickHandler() { 
							@Override
							public void onClick(ClickEvent event) {
								Widget sender = (Widget) event.getSource();
								HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(propertyName);
								FlexTable tableMulti = (FlexTable) hPanel.getWidget(0);
								ListBox listMulti = (ListBox) hPanel.getWidget(2);
								Button addButton = (Button) hPanel.getWidget(4);
								
								listMulti.addItem(option.getLabel(), option.getValue());
								listMulti.setVisible(true);
								addButton.setVisible(true);
								
								// Looking for row to delete 
								for (int i=0; i<tableMulti.getRowCount(); i++){
									if (tableMulti.getWidget(i,1).equals(sender)) {
										tableMulti.removeRow(i);
									}
								}
							}
						});
						
						tableMulti.setWidget(rowTableMulti, 0, htmlValue);
						tableMulti.setWidget(rowTableMulti, 1, removeImage);
						tableMulti.setHTML(rowTableMulti, 2, option.getLabel());
						setRowWordWarp(tableMulti, rowTableMulti, 2, true);
						htmlValue.setVisible(false);
						removeImage.setVisible(false);
					} else {
						listMulti.addItem(option.getLabel(), option.getValue()); 
					}
				}
				
				hWidgetProperties.put(propertyName,hPanel); 							// Saves panel
			}
		}
	}
	
	/**
	 * Gets all group properties 
	 */
	public void getProperties() {
		ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
		endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
		propertyGroupService.getProperties(docPath, grpName, callbackGetProperties);
	}

	/**
	 * Remove the document property group
	 */
	public void removeGroup() {
		ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
		endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
		propertyGroupService.removeGroup(docPath, grpName, callbackRemoveGroup);
	}
	
	/**
	 * Gets the group name
	 * 
	 * @return The group name
	 */
	public String getGrpName(){
		return grpName;
	}
	

	@Override
	public void fireEvent(PropertyGroupEventConstant event) {
		for (Iterator<PropertyGroupHandlerExtension> it = propertyGroupHandlerExtensionList.iterator(); it.hasNext();) {
			it.next().onChange(event);
		}
	}

	@Override
	public void addPropertyGroupHandlerExtension(PropertyGroupHandlerExtension handlerExtension) {
		propertyGroupHandlerExtensionList.add(handlerExtension);
	}
}
