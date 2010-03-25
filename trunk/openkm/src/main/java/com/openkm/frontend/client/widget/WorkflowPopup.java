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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.openkm.frontend.client.Main;
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
	private Collection<GWTFormElement> formFieldList;
	private List<FormWidget> formWidgetList;
	private FlexTable formTable;
	private boolean drawed = false;
	
	/**
	 * WorkflowPopup popup
	 */
	public WorkflowPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		formFieldList = new ArrayList<GWTFormElement>();
		formWidgetList = new ArrayList<FormWidget>();

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
			formFieldList = new ArrayList<GWTFormElement>();
			formWidgetList = new ArrayList<FormWidget>();
			if (drawed) {
				runProcessDefinitionWithValues();
			} else {
				getProcessDefinitionForms(new Double(listBox.getValue(listBox.getSelectedIndex())).doubleValue());
			}
		}
	}
	
	/**
	 * runProcessDefinition with values
	 */
	private void runProcessDefinitionWithValues() {
		// Always trying to capture values
		Map<String, Object> values = new HashMap<String, Object>();
		for (Iterator<FormWidget> it = formWidgetList.iterator(); it.hasNext();) {
			FormWidget fw = it.next();
			if (fw.getWidget() instanceof TextBox) {
				TextBox textBox = (TextBox) fw.getWidget();
				values.put(textBox.getName(), textBox.getValue());
			} else if (fw.getWidget() instanceof TextArea) {
				TextArea textArea = (TextArea) fw.getWidget();
				values.put(textArea.getName(), textArea.getValue());
			} else if (fw.getWidget() instanceof ListBox) {
				ListBox listBox = (ListBox) fw.getWidget();
				values.put(listBox.getName(), listBox.getValue(listBox.getSelectedIndex()));
			}
		}
		
		GWTDocument gwtDocument = Main.get().mainPanel.browser.fileBrowser.getDocument();
		ServiceDefTarget endPoint = (ServiceDefTarget) workflowService;
		endPoint.setServiceEntryPoint(Config.OKMWorkflowService);	
		workflowService.runProcessDefinition(gwtDocument.getUuid(), new Double(listBox.getValue(listBox.getSelectedIndex())).doubleValue(),
				                             values, callbackRunProcessDefinition);
		hide();
		if (Main.get().mainPanel.browser.fileBrowser.isDocumentSelected() ){
			GWTDocument doc = Main.get().mainPanel.browser.fileBrowser.getDocument();
			Main.get().mainPanel.browser.tabMultiple.tabDocument.setProperties(doc);
		}
	}
	
	/**
	 * Get process definitions callback
	 */
	final AsyncCallback<Map<String, Collection<GWTFormElement>>> callbackGetProcessDefinitionForms = new AsyncCallback<Map<String, Collection<GWTFormElement>>>() {
		public void onSuccess(Map<String, Collection<GWTFormElement>> result) {
			// Initial task is always called start
			formFieldList = result.get(Main.get().workspaceUserProperties.getWorkspace().getWorkflowRunConfigForm());
			if (formFieldList!=null) {
				drawForm();
			} else {
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
		formWidgetList = new ArrayList<FormWidget>(); // Init new form widget list
		
		// Deletes all table rows
		while (formTable.getRowCount()>0) {
			formTable.removeRow(0);
		}
		
		// Adds submit button
		if (formFieldList.size()>0) {
			formTable.setVisible(true);
			listBox.setVisible(false);
		} 
		
		// Sets form fields
		for (Iterator<GWTFormElement> it = formFieldList.iterator(); it.hasNext(); ) {
			final GWTFormElement formField = it.next();
			int row = formTable.getRowCount();
			
			// Prepares to save widget list values
			FormWidget fw = new FormWidget();
			Widget widget = null;
			
			if (formField instanceof GWTInput) {
				GWTInput gWTInput = (GWTInput) formField;
				TextBox textBox = new TextBox();
				textBox.setName(gWTInput.getName());
				textBox.setValue(gWTInput.getValue());
				if (gWTInput.getType().equals(GWTInput.TYPE_DATE))  {
					textBox.setEnabled(false);
				}
				textBox.setWidth(gWTInput.getWidth());
				textBox.setStyleName("okm-Input");
				formTable.setHTML(row, 0, "<b>" + gWTInput.getLabel() + "</b>");
				formTable.setWidget(row, 1, textBox);
				widget = textBox;
			} else if (formField instanceof GWTSelect) {
				GWTSelect gWTSelect = (GWTSelect) formField;
				ListBox listBox = new ListBox();
				listBox.setName(gWTSelect.getName());
				listBox.setWidth(gWTSelect.getWidth());
				for (Iterator<GWTOption> itx = gWTSelect.getOptions().iterator(); itx.hasNext(); ) {
					GWTOption option = itx.next();
					listBox.addItem(option.getName(), option.getValue());
				}
				listBox.setStyleName("okm-Input");
				formTable.setHTML(row, 0, "<b>" + gWTSelect.getLabel() + "</b>");
				formTable.setWidget(row, 1, listBox);
				widget = listBox;
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
				fw.setWidget(widget);
				formWidgetList.add(fw);
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
	 * FormWidget
	 * 
	 * @author jllort
	 *
	 */
	class FormWidget {
		
		private Widget widget;
		
		/**
		 * FormWidget
		 */
		public FormWidget() {
		}

		public Widget getWidget() {
			return widget;
		}

		public void setWidget(Widget widget) {
			this.widget = widget;
		}
	}
}
