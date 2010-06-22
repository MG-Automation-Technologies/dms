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

package com.openkm.frontend.client.widget;

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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
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
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTCheckBox;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTFormElement;
import com.openkm.frontend.client.bean.GWTInput;
import com.openkm.frontend.client.bean.GWTOption;
import com.openkm.frontend.client.bean.GWTProcessDefinition;
import com.openkm.frontend.client.bean.GWTSelect;
import com.openkm.frontend.client.bean.GWTTextArea;
import com.openkm.frontend.client.config.Config;
import com.openkm.frontend.client.service.OKMWorkflowService;
import com.openkm.frontend.client.service.OKMWorkflowServiceAsync;
import com.openkm.frontend.client.util.OKMBundleResources;
import com.openkm.frontend.client.widget.searchin.CalendarWidget;

/**
 * WorkflowPopup popup
 * 
 * @author jllort
 *
 */
public class WorkflowPopup extends DialogBox {
	
	private final OKMWorkflowServiceAsync workflowService = (OKMWorkflowServiceAsync) GWT.create(OKMWorkflowService.class);
	
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private Button button;
	private Button addButton;
	private ListBox listBox;
	private List<GWTFormElement> formFieldList;
	private Map<String, Widget> formWidgetList;
	private FlexTable formTable;
	private boolean drawed = false;
	
	/**
	 * WorkflowPopup popup
	 */
	public WorkflowPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		formFieldList = new ArrayList<GWTFormElement>();
		formWidgetList = new HashMap<String, Widget>();

		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		formTable = new FlexTable();
		
		button = new Button(Main.i18n("button.close"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		addButton = new Button(Main.i18n("button.start"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				runProcessDefinition();
			}
		});

		listBox = new ListBox();
		listBox.addChangeHandler(new ChangeHandler(){
			@Override
			public void onChange(ChangeEvent event) {
				if (listBox.getSelectedIndex()>0) {
					addButton.setEnabled(true);
				} else {
					addButton.setEnabled(false);
				}
			}
		});
		
		listBox.setStyleName("okm-Select");
		
		vPanel.setWidth("300px");
		vPanel.setHeight("50px");
		button.setStyleName("okm-Button");
		addButton.setStyleName("okm-Button");
		addButton.setEnabled(false);
		
		hPanel.add(button);
		hPanel.add(new HTML("&nbsp;&nbsp;"));
		hPanel.add(addButton);
		
		hPanel.setCellHorizontalAlignment(button,VerticalPanel.ALIGN_CENTER);
		hPanel.setCellHorizontalAlignment(addButton,VerticalPanel.ALIGN_CENTER);
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(listBox);
		vPanel.add(new HTML("<br>"));
		vPanel.add(formTable);
		vPanel.add(new HTML("<br>"));
		vPanel.add(hPanel);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(listBox, VerticalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hPanel, VerticalPanel.ALIGN_CENTER);
		
		formTable.setVisible(false);
		
		super.hide();
		setWidget(vPanel);
	}
	
	/**
	 * Gets asyncronous to get all process definitions
	 */
	final AsyncCallback<List<GWTProcessDefinition>> callbackFindLatestProcessDefinitions = new AsyncCallback<List<GWTProcessDefinition>>() {
		public void onSuccess(List<GWTProcessDefinition> result){
			listBox.clear();
			listBox.addItem("",""); // Adds empty value
			for (Iterator<GWTProcessDefinition> it = result.iterator(); it.hasNext();) {
				GWTProcessDefinition processDefinition = it.next();
				listBox.addItem(processDefinition.getName(),""+processDefinition.getId());
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("callbackFindLatestProcessDefinitions", caught);
		}
	};
	
	/**
	 * Gets asyncronous to run process definition
	 */
	final AsyncCallback<Object> callbackRunProcessDefinition = new AsyncCallback<Object>() {
		public void onSuccess(Object result){
			Main.get().mainPanel.dashboard.workflowDashboard.findUserTaskInstances();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("callbackRunProcessDefinition", caught);
		}
	};
	
	/**
	 * Enables close button
	 */
	public void enableClose() {
		button.setEnabled(true);
		Main.get().mainPanel.setVisible(true); // Shows main panel when all widgets are loaded
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("workflow.label"));
		button.setText(Main.i18n("button.close"));
		addButton.setText(Main.i18n("button.start"));
	}
	
	/**
	 * Show the popup error
	 * 
	 * @param msg Error message
	 */
	public void show() {
		setText(Main.i18n("workflow.label"));
		findLatestProcessDefinitions(); // Gets all groups
		listBox.setVisible(true);
		addButton.setEnabled(false);
		formTable.setVisible(false);
		drawed = false;
		int left = (Window.getClientWidth()-300)/2;
		int top = (Window.getClientHeight()-100)/2;
		setPopupPosition(left,top);
		super.show();
	}
	
	/**
	 * Gets all process definitions
	 */
	private void findLatestProcessDefinitions() {
		ServiceDefTarget endPoint = (ServiceDefTarget) workflowService;
		endPoint.setServiceEntryPoint(Config.OKMWorkflowService);	
		workflowService.findLatestProcessDefinitions(callbackFindLatestProcessDefinitions);
	}	
	
	/**
	 * Run process definition
	 */
	private void runProcessDefinition() {
		if (listBox.getSelectedIndex()>0) {
			if (drawed) {
				runProcessDefinitionWithValues();
			} else {
				formFieldList = new ArrayList<GWTFormElement>();
				formWidgetList = new HashMap<String, Widget>();
				getProcessDefinitionForms(new Double(listBox.getValue(listBox.getSelectedIndex())).doubleValue());
			}
		}
	}
	
	/**
	 * runProcessDefinition with values
	 */
	private void runProcessDefinitionWithValues() {
		for (Iterator<GWTFormElement> it = formFieldList.iterator(); it.hasNext();) {
			GWTFormElement formElement = it.next();
			if (formWidgetList.containsKey(formElement.getName())) {
				Widget widget = formWidgetList.get(formElement.getName());
				if (formElement instanceof GWTInput) {
					((GWTInput)formElement).setValue(((TextBox)((HorizontalPanel) widget).getWidget(0)).getValue());
					
				} else if (formElement instanceof GWTCheckBox) {
					((GWTCheckBox) formElement).setValue(((CheckBox) widget).getValue());
					
				} else if (formElement instanceof GWTTextArea) {
					((GWTTextArea) formElement).setValue(((TextArea) widget).getValue());
					
				} else if (formElement instanceof GWTSelect) {
					GWTSelect select = (GWTSelect) formElement; 
					// Disables all options
					for (Iterator<GWTOption> itx = select.getOptions().iterator(); itx.hasNext();)  {
						itx.next().setSelected(false);
					}
					if (select.getType().equals(GWTSelect.TYPE_SIMPLE)) {
						ListBox listBox = (ListBox) widget;
						if (listBox.getSelectedIndex()>=0) {
							for (Iterator<GWTOption> itx = select.getOptions().iterator(); itx.hasNext();)  {
								GWTOption option = itx.next();
								if (option.getValue().equals(listBox.getValue(listBox.getSelectedIndex()))) {
									option.setSelected(true);
								}
							}
						}
					} else {
						FlexTable tableMulti = (FlexTable) widget;
						for (int i=1; i<tableMulti.getRowCount(); i++) {
							for (Iterator<GWTOption> itx = select.getOptions().iterator(); itx.hasNext();)  {
								GWTOption option = itx.next();
								if (option.getValue().equals(((HTML) tableMulti.getWidget(i,0)).getText())) {
									option.setSelected(true);
								}
							}
						}
					}
				} 
			}
		}
		
		GWTDocument gwtDocument = Main.get().mainPanel.desktop.browser.fileBrowser.getDocument();
		ServiceDefTarget endPoint = (ServiceDefTarget) workflowService;
		endPoint.setServiceEntryPoint(Config.OKMWorkflowService);	
		workflowService.runProcessDefinition(gwtDocument.getUuid(), new Double(listBox.getValue(listBox.getSelectedIndex())).doubleValue(),
											 formFieldList, callbackRunProcessDefinition);
		hide();
		if (Main.get().mainPanel.desktop.browser.fileBrowser.isDocumentSelected() ){
			GWTDocument doc = Main.get().mainPanel.desktop.browser.fileBrowser.getDocument();
			Main.get().mainPanel.desktop.browser.tabMultiple.tabDocument.setProperties(doc);
		}
	}
	
	/**
	 * Get process definitions callback
	 */
	final AsyncCallback<Map<String, List<GWTFormElement>>> callbackGetProcessDefinitionForms = new AsyncCallback<Map<String, List<GWTFormElement>>>() {
		public void onSuccess(Map<String, List<GWTFormElement>> result) {
			// Initial task is always called start
			formFieldList = result.get(Main.get().workspaceUserProperties.getWorkspace().getWorkflowRunConfigForm());
			if (formFieldList!=null) {
				drawForm();
			} else {
				formFieldList = new ArrayList<GWTFormElement>();
				runProcessDefinitionWithValues();
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getProcessDefinitionForms", caught);
		}
	};
	
	/**
	 * getProcessDefinitionForms
	 * 
	 * @param id
	 */
	public void getProcessDefinitionForms(double id) {
		ServiceDefTarget endPoint = (ServiceDefTarget) workflowService;
		endPoint.setServiceEntryPoint(Config.OKMWorkflowService);		
		workflowService.getProcessDefinitionForms(id, callbackGetProcessDefinitionForms);
	}
	
	/**
	 * drawForm
	 */
	private void drawForm() {
		HorizontalPanel hPanel = new HorizontalPanel();
		formWidgetList = new HashMap<String, Widget>(); // Init new form widget list
		
		// Deletes all table rows
		while (formTable.getRowCount()>0) {
			formTable.removeRow(0);
		}
		
		// Show form / hide list
		if (formFieldList.size()>0) {
			formTable.setVisible(true);
			listBox.setVisible(false);
		} 
		
		// Sets form fields
		for (Iterator<GWTFormElement> it = formFieldList.iterator(); it.hasNext(); ) {
			final GWTFormElement formField = it.next();
			int row = formTable.getRowCount();
			
			// Prepares to save widget list values
			Widget widget = null;
			
			if (formField instanceof GWTInput) {
				GWTInput gWTInput = (GWTInput) formField;
				HorizontalPanel hInputPanel = new HorizontalPanel();
				final TextBox textBox = new TextBox();
				hInputPanel.add(textBox);
				textBox.setName(gWTInput.getName());
				textBox.setValue(gWTInput.getValue());
				textBox.setWidth(gWTInput.getWidth());
				
				// Case input is date must disable input
				if (gWTInput.getType().equals(GWTInput.TYPE_DATE))  {
					final PopupPanel calendarPopup = new PopupPanel(true);
					final CalendarWidget calendar = new CalendarWidget();
					calendar.addChangeHandler(new ChangeHandler(){
						@Override
						public void onChange(ChangeEvent event) {
							calendarPopup.hide();
							DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.day.pattern"));
							textBox.setText(dtf.format(calendar.getDate()));
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
				}
				
				textBox.setWidth(gWTInput.getWidth());
				textBox.setStyleName("okm-Input");
				formTable.setHTML(row, 0, "<b>" + gWTInput.getLabel() + "</b>");
				formTable.setWidget(row, 1, hInputPanel);
				widget = textBox;
				
			} else if (formField instanceof GWTCheckBox) {
				GWTCheckBox gWTCheckBox = (GWTCheckBox) formField;
				CheckBox checkBox = new CheckBox();
				checkBox.setValue(gWTCheckBox.getValue());
				formTable.setHTML(row, 0, "<b>" + gWTCheckBox.getLabel() + "</b>");
				formTable.setWidget(row, 1, checkBox);
				widget = checkBox;
				
			} else if (formField instanceof GWTSelect) {
				final int rowButton = row;
				final GWTSelect gWTSelect = (GWTSelect) formField;
				final FlexTable tableMulti = new FlexTable();
				final ListBox listBox = new ListBox();
				final Button addButton = new Button(Main.i18n("button.add"),new ClickHandler() { 
					@Override
					public void onClick(ClickEvent event) {							
						if (listBox.getSelectedIndex()>0) {
							// Case select multiple
							int rowTableMulti = tableMulti.getRowCount();
							HTML htmlValue = new HTML(listBox.getValue(listBox.getSelectedIndex()));
							HTML htmlLabel = new HTML("");
							final Button addButton = (Button) formTable.getWidget(rowButton, 2);
							
							for (Iterator<GWTOption> itOptions = gWTSelect.getOptions().iterator(); itOptions.hasNext();) {
								GWTOption option = itOptions.next();
								if (option.getValue().equals(htmlValue.getText())) {
									htmlLabel.setHTML(option.getLabel());
									htmlValue.setHTML(option.getValue());
									break;
								} 
							} 
							
							final String label = htmlLabel.getText();
							final String value = htmlValue.getText();
							Image removeImage = new Image("img/icon/actions/delete.gif");
							removeImage.addClickHandler(new ClickHandler() { 
								@Override
								public void onClick(ClickEvent event) {
									Widget sender = (Widget) event.getSource();
									
									// Looking for row to delete 
									for (int i=0; i<tableMulti.getRowCount(); i++){
										if (tableMulti.getWidget(i,1).equals(sender)) {
											tableMulti.removeRow(i);
										}
									}
									
									listBox.addItem(label, value);
									listBox.setVisible(true);
									addButton.setVisible(true);
								}
							});
							
							tableMulti.setWidget(rowTableMulti,0,htmlValue);
							tableMulti.setWidget(rowTableMulti,1,htmlLabel);
							tableMulti.setWidget(rowTableMulti,2,removeImage);
							setRowWordWarp(tableMulti,rowTableMulti, 3, true);
							htmlValue.setVisible(false);
							
							listBox.removeItem(listBox.getSelectedIndex());
							if (listBox.getItemCount()<=1) {
								listBox.setVisible(false);
								addButton.setVisible(false);
							}
						}
					}
				});
				
				listBox.setName(gWTSelect.getName());
				listBox.setWidth(gWTSelect.getWidth());
				listBox.setStyleName("okm-Select");
				listBox.addItem("",""); // Always we set and empty value
				
				formTable.setHTML(row, 0, "<b>" + gWTSelect.getLabel() + "</b>");
				if (gWTSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
					formTable.setWidget(row, 1, listBox);
					widget = listBox;
				} else if (gWTSelect.getType().equals(GWTSelect.TYPE_MULTIPLE)) {
					formTable.setWidget(row, 1, tableMulti);
					formTable.setWidget(row, 2, addButton);
					row++; // Incrementing row
					formTable.setHTML(row, 0, "");
					
					HTML name = new HTML(gWTSelect.getName()); // First table name it'll be the value name
					tableMulti.setWidget(0,0,name);
					name.setVisible(false);
					widget = tableMulti;
				} 
				
				for (Iterator<GWTOption> itx = gWTSelect.getOptions().iterator(); itx.hasNext(); ) {
					final GWTOption option = itx.next();
					String value = option.getValue();
					
					if (option.isSelected()) {
						if (gWTSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
							listBox.addItem(option.getLabel(), value);
							listBox.setSelectedIndex(listBox.getItemCount()-1);
							
						} else if (gWTSelect.getType().equals(GWTSelect.TYPE_MULTIPLE)) { 
							// Case select multiple
							int rowTableMulti = tableMulti.getRowCount();
							HTML htmlLabel = new HTML(option.getLabel());
							HTML htmlValue = new HTML(option.getValue());
							
							Image removeImage = new Image("img/icon/actions/delete.gif");
							removeImage.addClickHandler(new ClickHandler() { 
								@Override
								public void onClick(ClickEvent event) {
									Widget sender = (Widget) event.getSource();
									
									// Looking for row to delete 
									for (int i=0; i<tableMulti.getRowCount(); i++){
										if (tableMulti.getWidget(i, 1).equals(sender)) {
											tableMulti.removeRow(i);
										}
									}
									
									listBox.addItem(option.getLabel(), option.getValue());
									listBox.setVisible(true);
									addButton.setVisible(true);
								}
							});
							
							tableMulti.setWidget(rowTableMulti, 0, htmlValue);
							tableMulti.setWidget(rowTableMulti, 1, htmlLabel);
							tableMulti.setWidget(rowTableMulti, 2, removeImage);
							setRowWordWarp(tableMulti, rowTableMulti, 3, true);
							htmlValue.setVisible(false);
						}
					}
				}
				
				// Shows or hides listbox and addbutton depending list count 
				if (gWTSelect.getType().equals(GWTSelect.TYPE_SIMPLE) && listBox.getItemCount()==0 ) {
					listBox.setVisible(false);
					addButton.setVisible(false);
				}
				
			} else if (formField instanceof GWTTextArea) {
				GWTTextArea gWTTextArea = (GWTTextArea) formField;
				TextArea textArea = new TextArea();
				textArea.setName(gWTTextArea.getName());
				textArea.setSize(gWTTextArea.getWidth(), gWTTextArea.getHeight());
				textArea.setValue(gWTTextArea.getValue());
				
				textArea.setStyleName("okm-Input");
				formTable.setHTML(row, 0, "<b>" + gWTTextArea.getLabel() + "</b>");
				formTable.setWidget(row, 1, textArea);
				formTable.getCellFormatter().setVerticalAlignment(row, 0, HasAlignment.ALIGN_TOP);
				widget = textArea;
			} 
			
			// Saves widget
			if (widget!=null) {
				formWidgetList.put(formField.getName(), widget);
			}
		}
		
		// Adds submit button
		if (formFieldList.size()>0) {
			int row = formTable.getRowCount();
			formTable.setWidget(row, 0, hPanel);
			formTable.getFlexCellFormatter().setColSpan(row, 0, 2);
			formTable.getCellFormatter().setHorizontalAlignment(row, 0, HasAlignment.ALIGN_CENTER);
		}
		drawed = true; 
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
}
