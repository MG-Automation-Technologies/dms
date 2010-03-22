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

package es.git.openkm.frontend.client.widget.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.bean.GWTFormElement;
import es.git.openkm.frontend.client.bean.GWTInput;
import es.git.openkm.frontend.client.bean.GWTOption;
import es.git.openkm.frontend.client.bean.GWTPermission;
import es.git.openkm.frontend.client.bean.GWTSelect;
import es.git.openkm.frontend.client.bean.GWTTextArea;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMPropertyGroupService;
import es.git.openkm.frontend.client.service.OKMPropertyGroupServiceAsync;
import es.git.openkm.frontend.client.widget.ConfirmPopup;

/**
 * PropertyGroup
 * 
 * @author jllort
 *
 */
public class PropertyGroup extends Composite {
	
	private final OKMPropertyGroupServiceAsync propertyGroupService = (OKMPropertyGroupServiceAsync) GWT.create(OKMPropertyGroupService.class);
	private final static String MAP_LIST_VALUES = "_LIST_VALUES";
	
	private ScrollPanel scrollPanel;
	private FlexTable table;
	private String grpName;
	private GWTDocument doc;
	private Button changeButton;
	private Button removeButton;
	private Button cancelButton;
	private Map<String, String[]> hProperties = new HashMap<String, String[]>();
	private Collection<GWTFormElement> hMetaData = new ArrayList<GWTFormElement>();
	private HashMap<String, Widget> hWidgetProperties = new HashMap<String, Widget>();
	private boolean editValues = false;
	private CellFormatter cellFormatter;
	private HorizontalPanel hPanel;
	
	/**
	 * PropertyGroup
	 */
	public PropertyGroup(String grpName, GWTDocument doc, GWTFolder folder, boolean visible) {	
		table = new FlexTable();
		scrollPanel = new ScrollPanel(table);
		hPanel = new HorizontalPanel();
		this.grpName = grpName;
		this.doc = doc;
		
		changeButton = new Button(Main.i18n("button.change"), new ClickListener() {
			public void onClick(Widget sender) {
				if (!editValues) {
					Main.get().mainPanel.disableKeyShorcuts(); // Disables key shortcuts while updating
					changeButton.setHTML(Main.i18n("button.memory"));
					cancelButton.setVisible(true);
					edit();
					editValues = true;
					removeButton.setVisible(false);
				} else {
					Main.get().mainPanel.enableKeyShorcuts(); // Enables general keys applications
					changeButton.setHTML(Main.i18n("button.change"));
					setProperties();
					editValues = false;
					removeButton.setVisible(true);
					cancelButton.setVisible(false);
				}
			}
		});
		
		removeButton = new Button(Main.i18n("button.delete"), new ClickListener() {
			public void onClick(Widget sender) {
				if (!editValues) {
					Main.get().confirmPopup.setConfirm(ConfirmPopup.CONFIRM_DELETE_DOCUMENT_PROPERTY_GROUP);
					Main.get().confirmPopup.show();
				} 
			}
		});
		
		cancelButton = new Button(Main.i18n("button.cancel"), new ClickListener() {
			public void onClick(Widget sender) {
				if (editValues) {
					Main.get().mainPanel.enableKeyShorcuts(); // Enables general keys applications
					changeButton.setHTML(Main.i18n("button.change"));
					editValues = false;
					cancelEdit();
					removeButton.setVisible(true);
					cancelButton.setVisible(false);
				} 
			}
		});
		
		// Checking button permisions
		if (!visible) {
			changeButton.setVisible(visible);
			removeButton.setVisible(visible);
			
		} else if ( ((doc.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE) &&
			     ((folder.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE))  {
	    	 		changeButton.setVisible(true);
	    	 		removeButton.setVisible(true);
	    	 		
		} else {
			changeButton.setVisible(false);
			removeButton.setVisible(false);
		}

		table.setWidth("100%");
		
		hPanel.add(changeButton);
		hPanel.add(new HTML("&nbsp;&nbsp;"));
		hPanel.add(cancelButton);
		hPanel.add(new HTML("&nbsp;&nbsp;"));
		hPanel.add(removeButton);
		
		cancelButton.setVisible(false);  // Not shows cancel button
		
		table.setWidget(0, 0, hPanel);
		table.getFlexCellFormatter().setColSpan(0,0,2);
		
		// Button format
		changeButton.setStyleName("okm-Button");
		removeButton.setStyleName("okm-Button");
		cancelButton.setStyleName("okm-Button");
		table.getCellFormatter().setHorizontalAlignment(0,0,HasAlignment.ALIGN_CENTER);
		table.getCellFormatter().setVerticalAlignment(0,0,HasAlignment.ALIGN_MIDDLE);
		
		RowFormatter rowFormatter = table.getRowFormatter();
		rowFormatter.setStyleName(0, "okm-Security-Title");
		
		cellFormatter = table.getCellFormatter(); // Gets the cell formatter
		
		// Format borders and margins
		cellFormatter.addStyleName(0,0,"okm-Security-Title-RightBorder");
		
		getProperties();
		
		initWidget(scrollPanel);
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
	 * Lang refresh
	 */
	public void langRefresh() {
		changeButton.setHTML(Main.i18n("button.change"));
		removeButton.setHTML(Main.i18n("button.delete"));
	}	
	
	/**
	 * Gets asyncronous to group properties
	 */
	final AsyncCallback<Map<String, String[]>> callbackGetProperties = new AsyncCallback<Map<String, String[]>>() {
		public void onSuccess(Map<String, String[]> result){
			hProperties = result;
			getMetaData();  // Must be executed on secuential order before getproperties has finished
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getProperties", caught);
		}
	};

	/**
	 * Gets asyncronous to get metada group properties
	 */
	final AsyncCallback<Collection<GWTFormElement>> callbackGetMetaData = new AsyncCallback<Collection<GWTFormElement>>() {
		public void onSuccess(Collection<GWTFormElement> result){
			hMetaData = result;
			hWidgetProperties.clear();
			int rows = 1;

			for (Iterator<GWTFormElement> it = hMetaData.iterator(); it.hasNext(); ) {
				GWTFormElement gwtMetadata = it.next();
				final String propertyName = gwtMetadata.getName();
				
				if (gwtMetadata instanceof GWTTextArea) {
					TextArea textArea = new TextArea(); // Create a widget for this property
					textArea.setText(hProperties.get(propertyName)[0]);
					textArea.setStyleName("okm-Input");
					textArea.setSize("400","60");
					textArea.setReadOnly(true);
					hWidgetProperties.put(propertyName,textArea);
					table.setHTML(rows, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
					table.setWidget(rows, 1, textArea);
					table.getCellFormatter().setVerticalAlignment(rows,0,VerticalPanel.ALIGN_TOP);
					table.getCellFormatter().setWidth(rows, 1, "100%");
					setRowWordWarp(rows, 2, true);
					rows++;
					
				} else if (gwtMetadata instanceof GWTInput) {
					TextBox textBox = new TextBox(); // Create a widget for this property
					textBox.setText(hProperties.get(propertyName)[0]);
					textBox.setStyleName("okm-Input");
					hWidgetProperties.put(propertyName,textBox);
					table.setHTML(rows, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
					table.setHTML(rows, 1, hProperties.get(propertyName)[0]);
					table.getCellFormatter().setVerticalAlignment(rows,0,VerticalPanel.ALIGN_TOP);
					table.getCellFormatter().setWidth(rows, 1, "100%");
					rows++;
						
				} else if (gwtMetadata instanceof GWTSelect) {
					final GWTSelect gwtSelect = (GWTSelect) gwtMetadata;
					if (gwtSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
						String selectedValue = hProperties.get(propertyName)[0];
						ListBox listBox = new ListBox();
						listBox.setStyleName("okm-Select");
						listBox.addItem("",""); // Always we set and empty value
						
						for (Iterator<GWTOption> itData = gwtSelect.getOptions().iterator(); itData.hasNext(); ){
							GWTOption option = itData.next();
							listBox.addItem(option.getName(),option.getValue()); // The translation is composed by propertyName + "." + value key
							if (selectedValue!= null && selectedValue.equals(option.getValue())) {
								listBox.setItemSelected(listBox.getItemCount()-1,true);
							}
						}
						
						hWidgetProperties.put(propertyName,listBox);
						
						table.setHTML(rows, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
						if (selectedValue!=null && !selectedValue.equals("")) {
							table.setHTML(rows, 1, selectedValue );
						} else {
							table.setHTML(rows, 1, "");
						}
						table.getCellFormatter().setWidth(rows, 1, "100%");
						setRowWordWarp(rows, 2, true);
						rows++;
						
					} else {
						final HorizontalPanel hPanel = new HorizontalPanel();
						String[] selectedValues = (String[]) hProperties.get(propertyName);
						ListBox listMulti = new ListBox();
						listMulti.setStyleName("okm-Select");
						listMulti.addItem("",""); // Always we set and empty value
						List<String> selectValues = new ArrayList<String>(); // Values of list to be preservated
						
						// Table for values
						FlexTable tableMulti = new FlexTable();
						
						Button addButton = new Button(Main.i18n("button.add"),new ClickListener() {
							public void onClick(Widget arg0) {
								HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(propertyName);
								FlexTable tableMulti = (FlexTable) hPanel.getWidget(0);
								ListBox listMulti = (ListBox) hPanel.getWidget(2);
								Button addButton = (Button) hPanel.getWidget(4);
								
								if (listMulti.getSelectedIndex()>0) {
									final HTML htmlValue = new HTML(listMulti.getValue(listMulti.getSelectedIndex()));
									int rowTableMulti  = tableMulti.getRowCount();
									
									Image removeImage = new Image("img/icon/actions/delete.gif");
									removeImage.addClickListener(new ClickListener() {
										public void onClick(Widget sender) {
											HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(propertyName);
											FlexTable tableMulti = (FlexTable) hPanel.getWidget(0);
											ListBox listMulti = (ListBox) hPanel.getWidget(2);
											Button addButton = (Button) hPanel.getWidget(4);
											String value = htmlValue.getText();
											
											String optionName = "";
											for (Iterator<GWTOption> itOptions = gwtSelect.getOptions().iterator(); itOptions.hasNext();) {
												GWTOption option = itOptions.next();
												if (option.getValue().equals(htmlValue.getText())) {
													optionName = option.getName();
													break;
												} 
											} 
											
											listMulti.addItem(optionName,value);
											if (listMulti.getItemCount()>1){
												listMulti.setVisible(true);
												addButton.setVisible(true);
											}
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

						table.setHTML(rows, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
						table.setWidget(rows, 1, hPanel);
						table.getCellFormatter().setVerticalAlignment(rows,0,VerticalPanel.ALIGN_TOP);
						table.getCellFormatter().setVerticalAlignment(rows,1,VerticalPanel.ALIGN_TOP);
						table.getCellFormatter().setWidth(rows, 1, "100%");
						
						for (Iterator<GWTOption> itData = gwtSelect.getOptions().iterator(); itData.hasNext(); ){
							final GWTOption option = itData.next();
							boolean found = false;
							
							// Looks if there's some selected value
							if(selectedValues!=null) {
								for (int i=0; i<selectedValues.length; i++ ) {
									if (selectedValues[i].equals(option.getValue())) {
										int rowTableMulti = tableMulti.getRowCount();
										HTML htmlValue = new HTML(option.getValue());
										
										Image removeImage = new Image("img/icon/actions/delete.gif");
										removeImage.addClickListener(new ClickListener() {
											public void onClick(Widget sender) {
												HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(propertyName);
												FlexTable tableMulti = (FlexTable) hPanel.getWidget(0);
												ListBox listMulti = (ListBox) hPanel.getWidget(2);
												Button addButton = (Button) hPanel.getWidget(4);
												
												listMulti.addItem(option.getName(),option.getValue());
												if (listMulti.getItemCount()>1){
													listMulti.setVisible(true);
													addButton.setVisible(true);
												}
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
										tableMulti.setHTML(rowTableMulti,2,option.getName());
										setRowWordWarp(tableMulti,rowTableMulti, 2, true);
										htmlValue.setVisible(false);
										removeImage.setVisible(false);
										found = true;
									}
								}
							}
							
							// Only values not selecteds must appear on list
							if (!found) {
								selectValues.add(option.getValue());
								listMulti.addItem(option.getName(),option.getValue()); // The translation is composed by propertyName + "." + value key
							}
						}
						
						hWidgetProperties.put(propertyName,hPanel); 							// Saves panel
						
						if (selectValues.size()>0){
							String[] values = new String[selectValues.size()];
							int count = 0;
							for (Iterator<String> selVal = selectValues.iterator(); selVal.hasNext();) {
								values[count++] = selVal.next();
								hProperties.put(propertyName + MAP_LIST_VALUES, values); 		// Saves list values
							}
							
						} else {
							hProperties.put(propertyName + MAP_LIST_VALUES, new String[]{""}); 	// Saves list values
						}

						rows++;
					}
				}
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getMetaData", caught);
		}
	};
	
	/**
	 * Gets asyncronous to set roperties
	 */
	final AsyncCallback callbackSetProperties = new AsyncCallback() {
		public void onSuccess(Object result){
			Main.get().mainPanel.browser.tabMultiple.status.unsetGroupProperties();
		}

		public void onFailure(Throwable caught) {
			Main.get().mainPanel.browser.tabMultiple.status.unsetGroupProperties();
			Main.get().showError("setProperties", caught);
		}
	};
	
	/**
	 * Gets asyncronous to remove document group properties
	 */
	final AsyncCallback callbackRemoveGroup = new AsyncCallback() {
		public void onSuccess(Object result){
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("callbackRemoveGroup", caught);
		}
	};
	
	/**
	 * Edit values
	 */
	private void edit(){
		int rows = 1;

		for (Iterator<GWTFormElement> it = hMetaData.iterator(); it.hasNext();) {
			GWTFormElement gwtMetadata = it.next();
			String propertyName = gwtMetadata.getName();
			
			if (gwtMetadata instanceof GWTTextArea) {
				TextArea textArea = (TextArea) hWidgetProperties.get(propertyName);
				textArea.setText(hProperties.get(propertyName)[0]);
				textArea.setReadOnly(false);
				table.setWidget(rows, 1, (textArea));
				rows++;
				
			} else if (gwtMetadata instanceof GWTInput) {
				TextBox textBox = (TextBox) hWidgetProperties.get(propertyName);
				textBox.setText(hProperties.get(propertyName)[0]);
				table.setWidget(rows, 1,textBox);
				rows++;
				
			} else if (gwtMetadata instanceof GWTSelect) {
				GWTSelect gwtSelect = (GWTSelect) gwtMetadata;
				if (gwtSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
					ListBox listBox = (ListBox) hWidgetProperties.get(propertyName);
					String selectedValue = hProperties.get(propertyName)[0];
					// Select the actual value on list
					if (selectedValue!=null && !selectedValue.equals("")) {
						for (int i=0; i<listBox.getItemCount(); i++ ) {
							if (listBox.getValue(i).equals(selectedValue)) {
								listBox.setItemSelected(i,true);
							}
						}
					}
					table.setWidget(rows, 1, listBox);
					rows++;
					
				} else {
					HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(propertyName);
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
					
					rows++;
					break;
				}
			}
		}
	}
	
	/**
	 * Sets the properties values
	 */
	private void setProperties() {
		Map<String, String[]> hSaveProperties = new HashMap<String, String[]>();
		int rows = 1;
		
		for (Iterator<GWTFormElement> it = hMetaData.iterator(); it.hasNext();) {
			GWTFormElement gwtMetadata = it.next();
			String propertyName = gwtMetadata.getName();
			
			if (gwtMetadata instanceof GWTTextArea) {
				TextArea textArea = (TextArea) hWidgetProperties.get(propertyName);
				hSaveProperties.put(propertyName, new String[] {textArea.getText()});
				hProperties.put(propertyName, new String[] {textArea.getText()});
				// Resets values
				textArea.setReadOnly(true);

			} else if (gwtMetadata instanceof GWTInput) {
				TextBox textBox = (TextBox) hWidgetProperties.get(propertyName);
				hSaveProperties.put(propertyName, new String[] {textBox.getText()});
				hProperties.put(propertyName, new String[] {textBox.getText()});
				if (textBox.getText()!=null && !textBox.getText().equals("")) {
					table.setHTML(rows, 1, textBox.getText());
				} else {
					table.setHTML(rows, 1, "");
				}
				// Resets values
				textBox.setText("");
					
			} else if (gwtMetadata instanceof GWTSelect) {
				GWTSelect gwtSelect = (GWTSelect) gwtMetadata;
				if (gwtSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
					ListBox listBox = (ListBox) hWidgetProperties.get(propertyName);
					if (listBox.getSelectedIndex()>0) {
						String selectedValue = listBox.getValue(listBox.getSelectedIndex());
						hSaveProperties.put(propertyName, new String[] {selectedValue});
						hProperties.put(propertyName, new String[] {selectedValue});
						
						String optionName = "";
						for (Iterator<GWTOption> itOptions = gwtSelect.getOptions().iterator(); itOptions.hasNext();) {
							GWTOption option = itOptions.next();
							if (option.getValue().equals(selectedValue)) {
								optionName = option.getName();
								break;
							}
						}
						
						table.setHTML(rows, 1, optionName);
					} else {
						hSaveProperties.put(propertyName, new String[] {""});
						hProperties.put(propertyName, new String[] {""});
						table.setHTML(rows, 1, "");
					}
					
				} else {
					List<String> selectValues = new ArrayList<String>();
					HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(propertyName);
					FlexTable tableMulti = (FlexTable) hPanel.getWidget(0);
					ListBox listMulti = (ListBox) hPanel.getWidget(2);
					Button addButton = (Button) hPanel.getWidget(4);		
					
					if (tableMulti.getRowCount()>0) {
						String[] selectedValues = new String[tableMulti.getRowCount()];
						for (int i=0; i<tableMulti.getRowCount(); i++) {
							selectedValues[i]=tableMulti.getText(i,0);
						}
						hSaveProperties.put(propertyName, selectedValues);
						hProperties.put(propertyName, selectedValues);			  // These are the new properties used on canceling to restore it
						
						for (int i=0; i<tableMulti.getRowCount(); i++) {
							((Image) tableMulti.getWidget(i,1)).setVisible(false);
						}
					} else {
						hSaveProperties.put(propertyName, new String[]{""});
						hProperties.put(propertyName, new String[]{""});
					}
					
					// Saves actual list values to prevent new cancel updating
					if (listMulti.getItemCount()>1) {
						for (int i=1; i<listMulti.getItemCount(); i++) {
							selectValues.add(listMulti.getValue(i));
						}
					}
					
					if (selectValues.size()>0){
						String[] values = new String[selectValues.size()];
						int count = 0;
						for (Iterator<String> selVal = selectValues.iterator(); selVal.hasNext();) {
							values[count++] = selVal.next();
							hProperties.put(propertyName + MAP_LIST_VALUES, values); 		// Saves list values
						}
						
					} else {
						hProperties.put(propertyName + MAP_LIST_VALUES, new String[]{""}); 	// Saves list values
					}

					// Disables select and button
					listMulti.setVisible(false);
					addButton.setVisible(false);
				}
				rows ++;
			}
		}
		Main.get().mainPanel.browser.tabMultiple.status.setGroupProperties();
		ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
		endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
		propertyGroupService.setProperties(doc.getPath(), grpName, hSaveProperties, callbackSetProperties);
	}
	
	/**
	 * Cancel edition and restores values
	 */
	private void cancelEdit() {

		int rows = 1;
		
		for (Iterator<GWTFormElement> it = hMetaData.iterator(); it.hasNext();) {
			GWTFormElement gwtMetadata = it.next();
			final String propertyName = gwtMetadata.getName();
			
			if (gwtMetadata instanceof GWTTextArea) {
				TextArea textArea = (TextArea) hWidgetProperties.get(propertyName);
				textArea.setText(hProperties.get(propertyName)[0]);
				textArea.setReadOnly(true);
				table.setWidget(rows, 1, textArea);
				rows++;
					break;
				
			} else if (gwtMetadata instanceof GWTInput) {
				TextBox textBox = (TextBox) hWidgetProperties.get(propertyName);
				textBox.setText("");
				table.setHTML(rows, 1, hProperties.get(propertyName)[0]);
				rows++;

			} else if (gwtMetadata instanceof GWTSelect) {
				final GWTSelect gwtSelect = (GWTSelect) gwtMetadata;
				if (gwtSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
					String selectedValue = hProperties.get(propertyName)[0];

					if (selectedValue!=null && !selectedValue.equals("")) {
						
						String optionName = "";
						for (Iterator<GWTOption> itOptions = gwtSelect.getOptions().iterator(); itOptions.hasNext();) {
							GWTOption option = itOptions.next();
							if (option.getValue().equals(selectedValue)) {
								optionName = option.getName();
								break;
							}
						}
						
						table.setHTML(rows, 1, optionName);
					} else {
						table.setHTML(rows, 1, "");
					}
					
				} else {
					HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(propertyName);
					FlexTable tableMulti = (FlexTable) hPanel.getWidget(0);
					ListBox listMulti = (ListBox) hPanel.getWidget(2);
					Button addButton = (Button) hPanel.getWidget(4);
					String[] selectedValues = (String[]) hProperties.get(propertyName);
					String[] values =  hProperties.get(propertyName + MAP_LIST_VALUES);
					List<String> selectValues = new ArrayList<String>();
					
					for (int i=0; i<values.length; i++) {
						selectValues.add(values[i]);
					}
					
					// Removes all rows
					while (tableMulti.getRowCount()>0) {
						tableMulti.removeRow(0);
					}

					// Looks if there's some selected value and redraws all table
					if(selectedValues!=null) {
						for (int i=0; i<selectedValues.length; i++ ) {
							final String value = selectedValues[i];
							int rowTableMulti = tableMulti.getRowCount();
							HTML htmlValue = new HTML(value);

							Image removeImage = new Image("img/icon/actions/delete.gif");
							removeImage.addClickListener(new ClickListener() {
								public void onClick(Widget sender) {
									HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(propertyName);
									FlexTable tableMulti = (FlexTable) hPanel.getWidget(0);
									ListBox listMulti = (ListBox) hPanel.getWidget(2);
									Button addButton = (Button) hPanel.getWidget(4);
									
									String optionName = "";
									for (Iterator<GWTOption> itOptions = gwtSelect.getOptions().iterator(); itOptions.hasNext();) {
										GWTOption option = itOptions.next();
										if (option.getValue().equals(value)) {
											optionName = option.getName();
											break;
										} 
									} 
									
									listMulti.addItem(optionName,value);
									if (listMulti.getItemCount()>1){
										listMulti.setVisible(true);
										addButton.setVisible(true);
									}
									// Looking for row to delete 
									for (int i=0; i<tableMulti.getRowCount(); i++){
										if (tableMulti.getWidget(i,1).equals(sender)) {
											tableMulti.removeRow(i);
										}
									}
								}
							});
							
							String optionName = "";
							for (Iterator<GWTOption> itOptions = gwtSelect.getOptions().iterator(); itOptions.hasNext();) {
								GWTOption option = itOptions.next();
								if (option.getValue().equals(value)) {
									optionName = option.getName();
									break;
								} 
							} 
							
							tableMulti.setWidget(rowTableMulti,0,htmlValue);
							tableMulti.setWidget(rowTableMulti,1,removeImage);
							tableMulti.setHTML(rowTableMulti,2,optionName);
							setRowWordWarp(tableMulti,rowTableMulti, 2, true);
							htmlValue.setVisible(false);
							removeImage.setVisible(false);
						}
					}
					
					// Removes all list items except first ("the empty")
					while (listMulti.getItemCount()>1) {
						listMulti.removeItem(1);
					}
					
					// Recreates the initial list before staring updating
					for (Iterator<String> its = selectValues.iterator(); its.hasNext();) {
						String value = its.next();
						String optionName = "";
						for (Iterator<GWTOption> itOptions = gwtSelect.getOptions().iterator(); itOptions.hasNext();) {
							GWTOption option = itOptions.next();
							if (option.getValue().equals(value)) {
								optionName = option.getName();
								break;
							}
						}
						listMulti.addItem(optionName,value);
					}
					
					listMulti.setVisible(false);
					addButton.setVisible(false);
				}
				rows++;
			}
		}
	}
	
	/**
	 * Gets all group properties 
	 */
	private void getProperties() {
		GWTDocument gwtDocument = Main.get().mainPanel.browser.fileBrowser.getDocument();
		if (gwtDocument!= null) {
			ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
			endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
			propertyGroupService.getProperties(doc.getPath(), grpName, callbackGetProperties);
		}
	}
	
	/**
	 * Gets all metadata group properties 
	 */
	private void getMetaData() {
		GWTDocument gwtDocument = Main.get().mainPanel.browser.fileBrowser.getDocument();
		if (gwtDocument!= null) {
			ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
			endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
			propertyGroupService.getMetaData(grpName, callbackGetMetaData);
		}
	}

	/**
	 * Remove the document property group
	 */
	public void removeGroup() {
		GWTDocument gwtDocument = Main.get().mainPanel.browser.fileBrowser.getDocument();
		if (gwtDocument!= null) {
			ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
			endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
			propertyGroupService.removeGroup(doc.getPath(), grpName, callbackRemoveGroup);
		}
	}
	/**
	 * Gets the group name
	 * 
	 * @return The group name
	 */
	public String getGrpName(){
		return grpName;
	}
}
