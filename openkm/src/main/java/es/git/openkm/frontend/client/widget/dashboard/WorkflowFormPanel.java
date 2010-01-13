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

package es.git.openkm.frontend.client.widget.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTButton;
import es.git.openkm.frontend.client.bean.GWTFormField;
import es.git.openkm.frontend.client.bean.GWTInput;
import es.git.openkm.frontend.client.bean.GWTProcessDefinition;
import es.git.openkm.frontend.client.bean.GWTProcessInstance;
import es.git.openkm.frontend.client.bean.GWTSelect;
import es.git.openkm.frontend.client.bean.GWTTaskInstance;
import es.git.openkm.frontend.client.bean.GWTTextArea;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMWorkflowService;
import es.git.openkm.frontend.client.service.OKMWorkflowServiceAsync;
import es.git.openkm.frontend.client.util.CommonUI;

/**
 * WorkflowFormPanel
 * 
 * @author jllort
 *
 */
public class WorkflowFormPanel extends Composite {
	
	private final OKMWorkflowServiceAsync workflowService = (OKMWorkflowServiceAsync) GWT.create(OKMWorkflowService.class);
	
	private VerticalPanel vPanel;
	private GWTTaskInstance taskInstance;
	private FlexTable table;
	private FlexTable formTable;
	private Collection<GWTFormField> formFieldList = new ArrayList<GWTFormField>();
	private Button submitForm;
	private List<FormWidget> formWidgetList = new ArrayList<FormWidget>();
	private TitleWidget taskTittle;
	private TitleWidget processInstanceTittle;
	private TitleWidget processDefinitionTittle;
	private TitleWidget dataTittle;
	private Hyperlink documentLink;
	
	/**
	 * WorkflowFormPanel
	 */
	public WorkflowFormPanel() {
		vPanel = new VerticalPanel();
		table = new FlexTable();
		formTable = new FlexTable();
		submitForm = new Button(Main.i18n("button.accept"));
		
		submitForm.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				setTaskInstanceValues(taskInstance.getId(), null); 
			}
		});
		
		int[] taskRow = {1, 2, 3, 4, 5, 6 };
		int[] processInstanceRow = {8, 9, 10};
		int[] processDefinitionRow = {12, 13, 14, 15 };
		int[] dataTitle = {17};
		taskTittle = new TitleWidget(Main.i18n("dashboard.workflow.task"), taskRow);
		processInstanceTittle = new TitleWidget(Main.i18n("dashboard.workflow.task.process.instance"), processInstanceRow);
		processDefinitionTittle = new TitleWidget(Main.i18n("dashboard.workflow.task.process.definition"), processDefinitionRow);
		dataTittle = new TitleWidget(Main.i18n("dashboard.workflow.task.process.data"), dataTitle);
		taskTittle.setWidth("100%");
		processInstanceTittle.setWidth("100%");
		processDefinitionTittle.setWidth("100%");
		dataTittle.setWidth("100%");
		
		table.setWidget(0, 0, taskTittle);
		table.setHTML(1, 0, "<b>"+ Main.i18n("dashboard.workflow.task.id") + "</b>");
		table.setHTML(2, 0, "<b>"+ Main.i18n("dashboard.workflow.task.name") + "</b>");
		table.setHTML(3, 0, "<b>"+ Main.i18n("dashboard.workflow.task.created") + "</b>");
		table.setHTML(4, 0, "<b>"+ Main.i18n("dashboard.workflow.task.start") + "</b>");
		table.setHTML(5, 0, "<b>"+ Main.i18n("dashboard.workflow.task.duedate") + "</b>");
		//table.setHTML(6, 0, "<b>"+ Main.i18n("dashboard.workflow.task.end") + "</b>");
		table.setHTML(6, 0, "<b>"+ Main.i18n("dashboard.workflow.task.description") + "</b>");
		table.setWidget(7, 0, processInstanceTittle);
		table.setHTML(8, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.id") + "</b>");
		table.setHTML(9, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.version") + "</b>");
		table.setHTML(10, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.path") + "</b>");
		table.setWidget(11, 0, processDefinitionTittle);
		table.setHTML(12, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.id") + "</b>");
		table.setHTML(13, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.name") + "</b>");
		table.setHTML(14, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.version") + "</b>");
		table.setHTML(15, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.description") + "</b>");
		table.setWidget(16, 0, dataTittle);
		table.setWidget(17, 0, formTable);
		table.setHTML(1, 2, "");
		table.setHTML(2, 2, "");
		table.setHTML(3, 2, "");
		table.setHTML(4, 2, "");
		table.setHTML(5, 2, "");
		table.setHTML(8, 2, "");
		table.setHTML(9, 2, "");
		table.setHTML(10, 2, "");
		table.setHTML(12, 2, "");
		table.setHTML(13, 2, "");
		table.setHTML(14, 2, "");
		
		// Setting visibleRows
		taskTittle.setVisibleRows(false);
		processInstanceTittle.setVisibleRows(false);
		processDefinitionTittle.setVisibleRows(false);
		dataTittle.setVisibleRows(true);
		
		table.getCellFormatter().setWidth(1, 2, "100%");
		table.getCellFormatter().setWidth(8, 2, "100%");
		table.getCellFormatter().setWidth(12, 2, "100%");
		table.getFlexCellFormatter().setColSpan(0, 0, 3);
		table.getFlexCellFormatter().setColSpan(7, 0, 3);
		table.getFlexCellFormatter().setColSpan(11, 0, 3);
		table.getFlexCellFormatter().setColSpan(16, 0, 3);
		table.getFlexCellFormatter().setColSpan(17, 0, 3);
		table.getCellFormatter().setStyleName(0, 0, "okm-WorkflowFormPanel-Title");
		table.getCellFormatter().setStyleName(7, 0, "okm-WorkflowFormPanel-Title");
		table.getCellFormatter().setStyleName(11, 0, "okm-WorkflowFormPanel-Title");
		table.getCellFormatter().setStyleName(16, 0, "okm-WorkflowFormPanel-Title");
		
		vPanel.add(table);
		
		table.setStyleName("okm-NoWrap");
		vPanel.setStyleName("okm-WorkflowFormPanel");
		submitForm.setStyleName("okm-Button");
		
		table.setWidth("100%");
		vPanel.setHeight("100%");
		
		initWidget(vPanel);
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		taskTittle.setTitle(Main.i18n("dashboard.workflow.task"));
		table.setHTML(1, 0, "<b>"+ Main.i18n("dashboard.workflow.task.id") + "</b>");
		table.setHTML(2, 0, "<b>"+ Main.i18n("dashboard.workflow.task.name") + "</b>");
		table.setHTML(3, 0, "<b>"+ Main.i18n("dashboard.workflow.task.created") + "</b>");
		table.setHTML(4, 0, "<b>"+ Main.i18n("dashboard.workflow.task.start") + "</b>");
		table.setHTML(5, 0, "<b>"+ Main.i18n("dashboard.workflow.task.duedate") + "</b>");
		table.setHTML(6, 0, "<b>"+ Main.i18n("dashboard.workflow.task.description") + "</b>");
		processInstanceTittle.setTitle(Main.i18n("dashboard.workflow.task.process.instance"));
		table.setHTML(8, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.id") + "</b>");
		table.setHTML(9, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.version") + "</b>");
		table.setHTML(10, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.path") + "</b>");
		processDefinitionTittle.setTitle(Main.i18n("dashboard.workflow.task.process.definition"));
		table.setHTML(12, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.id") + "</b>");
		table.setHTML(13, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.name") + "</b>");
		table.setHTML(14, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.version") + "</b>");
		table.setHTML(15, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.description") + "</b>");
		dataTittle.setTitle(Main.i18n("dashboard.workflow.task.process.data"));
		submitForm.setHTML(Main.i18n("button.accept"));
	}
	
	/**
	 * Sets a TaskInstance
	 * 
	 * @param taskInstance
	 */
	public void setTaskInstance(GWTTaskInstance taskInstance) {
		this.taskInstance = taskInstance;
		GWTProcessInstance processInstance = taskInstance.getProcessInstance();
		GWTProcessDefinition processDefinition = processInstance.getProcessDefinition(); 
		
		table.setHTML(1, 1, ""+taskInstance.getId());
		table.setHTML(2, 1, ""+taskInstance.getName());
		DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
		table.setHTML(3, 1, dtf.format(taskInstance.getCreate()));
		
		if (taskInstance.getStart() != null) {
			table.setHTML(4, 1, dtf.format(taskInstance.getStart()));
		} else {
			table.setHTML(4, 1, "");
		}
		
		if (taskInstance.getDueDate() != null) {
			table.setHTML(5, 1, dtf.format(taskInstance.getDueDate()));
		}
		
		if (taskInstance.getDescription() != null) {
			table.setHTML(6, 1, ""+taskInstance.getDescription());
		}
		
		table.setHTML(8, 1, ""+processInstance.getId());
		table.setHTML(9, 1, ""+processInstance.getVersion());
		
//		if (processInstance.getVariables()!=null && processInstance.getVariables().keySet().contains("path")) {
//			Hyperlink link = new Hyperlink();
//			final String docPath = (String) processInstance.getVariables().get("path");
//			link.setText(docPath);
//			table.setWidget(10, 1, link);
//			link.addClickHandler(new ClickHandler() { 
//				@Override
//				public void onClick(ClickEvent event) {
//					String path = docPath.substring(0,docPath.lastIndexOf("/"));
//					CommonUI.openAllFolderPath(path, docPath);	
//				}
//			});
//			link.setStyleName("okm-Hyperlink");
//			
//			// Clones link
//			documentLink = new Hyperlink();
//			documentLink.setText(docPath);
//			documentLink.addClickHandler(new ClickHandler() { 
//				@Override
//				public void onClick(ClickEvent event) {
//					String path = docPath.substring(0,docPath.lastIndexOf("/"));
//					CommonUI.openAllFolderPath(path, docPath);	
//				}
//			});
//			documentLink.setStyleName("okm-Hyperlink");
//		} else {
//			documentLink = null;
//		}
		
		table.setHTML(12, 1, ""+processDefinition.getId());
		table.setHTML(13, 1, ""+processDefinition.getName());
		table.setHTML(14, 1, ""+processDefinition.getVersion());
		
		if (processDefinition.getDescription()!=null) {
			table.setHTML(16, 1, ""+processDefinition.getDescription());
		}

		getProcessDefinitionForms(processDefinition.getId());
	}
	
	/**
	 * clearPanel
	 */
	private void clearPanel() {
		table.setHTML(1, 1, "");
		table.setHTML(2, 1, "");
		table.setHTML(3, 1, "");
		table.setHTML(4, 1, "");
		table.setHTML(5, 1, "");
		table.setHTML(8, 1, "");
		table.setHTML(9, 1, "");
		table.setHTML(10, 1, "");
		table.setHTML(12, 1, "");
		table.setHTML(13, 1, "");
		table.setHTML(14, 1, "");
		table.setHTML(15, 1, "");
		formWidgetList = new ArrayList<FormWidget>(); // Init new form widget list
		formFieldList = new ArrayList<GWTFormField>();
		drawForm();
	}
	
	/**
	 * Get subscribed documents callback
	 */
	final AsyncCallback<Map<String, Collection<GWTFormField>>> callbackGetProcessDefinitionForms = new AsyncCallback<Map<String, Collection<GWTFormField>>>() {
		public void onSuccess(Map<String, Collection<GWTFormField>> result) {
			formFieldList = new ArrayList<GWTFormField>();
			if (result.containsKey(taskInstance.getName())) {
				formFieldList = result.get(taskInstance.getName());
				drawForm();
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
		submitForm.setVisible(true); // always set form visible
		HorizontalPanel hPanel = new HorizontalPanel();
		formWidgetList = new ArrayList<FormWidget>(); // Init new form widget list
		
		// Deletes all table rows
		while (formTable.getRowCount()>0) {
			formTable.removeRow(0);
		}
		
		// Adds submit button
		if (formFieldList.size()>0) {
			HTML space = new HTML("&nbsp;");
			hPanel.add(submitForm);
			hPanel.add(space);
			hPanel.setCellWidth(space, "5px");
		}
		
		// Adds document link
		if (documentLink!=null) {
			int row = formTable.getRowCount();
			formTable.setHTML(row, 0, "<b>"+ Main.i18n("dashboard.workflow.task.process.path") + "</b>");
			formTable.setWidget(row, 1, documentLink);
		}
		
		// Sets form fields
		for (Iterator<GWTFormField> it = formFieldList.iterator(); it.hasNext(); ) {
			final GWTFormField formField = it.next();
			int row = formTable.getRowCount();
			
			// On button case must not create new row
//			if (formField.getType()!=GWTFormField.TRANSITION) {
//				formTable.setHTML(row, 0, "<b>" + formField.getLabel() + "</b>");
//			}
			
			// Prepares to save widget list values
			FormWidget fw = new FormWidget();
			Widget widget = null;
			
			if (formField instanceof GWTButton) {
				final GWTButton gWTButton = (GWTButton) formField;
				submitForm.setVisible(false); // Always set form unvisible because there's new buttons
				
				Button transButton = new Button(gWTButton.getLabel());
				transButton.setStyleName("okm-Button");
				HTML space = new HTML("&nbsp;");
				hPanel.add(transButton);
				hPanel.add(space);
				hPanel.setCellWidth(space, "5px");
				
				
				// Setting submit button
				transButton.addClickHandler(new ClickHandler() { 
					@Override
					public void onClick(ClickEvent event) {
						if (gWTButton.getType().equals(GWTButton.TYPE_TRANSITION)) {
							setTaskInstanceValues(taskInstance.getId(), gWTButton.getValue());
						} else {
							setTaskInstanceValues(taskInstance.getId(), null);
						}
					}
				});
			} else if (formField instanceof GWTInput) {
				GWTInput gWTInput = (GWTInput) formField;
				TextBox textBox = new TextBox();
				textBox.setName(gWTInput.getName());
				textBox.setValue(gWTInput.getValue());
				if (gWTInput.getType().equals(GWTInput.TYPE_DATE))  {
					textBox.setEnabled(false);
				}
				textBox.setWidth(gWTInput.getSize());
				textBox.setStyleName("okm-Input");
				formTable.setHTML(row, 0, "<b>" + gWTInput.getLabel() + "</b>");
				formTable.setWidget(row, 1, textBox);
				widget = textBox;
			} else if (formField instanceof GWTSelect) {
				Window.alert("select");
			} else if (formField instanceof GWTTextArea) {
				Window.alert("textarea");
			} else {
				Window.alert("malament anem si veiem aixo !!!");
			}
			
//			switch (formField.getType()) {
//				case GWTFormField.CHECKBOX:
//					CheckBox chkBox = new CheckBox();
//					formTable.setWidget(row, 1, chkBox);
//					break;
				
//				case GWTFormField.TEXT_AREA:
//					TextArea textArea = new TextArea();
//					textArea.setStyleName("okm-Input");
//					formTable.setWidget(row, 1, textArea);
//					break;

//				case GWTFormField.SELECT:
//				case GWTFormField.SELECT_MULTI:
//					ListBox listBox = new ListBox();
//					listBox.setStyleName("okm-Input");
//					formTable.setWidget(row, 1, listBox);
//					break;
//			}
			
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
	}
	
	/**
	 * Get subscribed documents callback
	 */
	final AsyncCallback<Object> callbackSetTaskInstanceValues = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {
			Main.get().mainPanel.dashboard.workflowDashboard.findUserTaskInstances();
			clearPanel();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("setTaskInstanceValues", caught);
		}
	};
	
	/**
	 * setTaskInstanceValues
	 * 
	 * @param id
	 * @param transitionName
	 */
	private void setTaskInstanceValues(double id, String transitionName) {
		// Init values hashmap
		Map<String, String[]> values = new HashMap<String, String[]>();
		for (Iterator<FormWidget> it = formWidgetList.iterator(); it.hasNext();) {
			FormWidget fw = it.next();
			if (fw.getWidget() instanceof TextBox) {
				TextBox textBox = (TextBox) fw.getWidget();
				values.put(textBox.getName(), new String[]{textBox.getValue()});
			}
		}
		
		ServiceDefTarget endPoint = (ServiceDefTarget) workflowService;
		endPoint.setServiceEntryPoint(Config.OKMWorkflowService);		
		workflowService.setTaskInstanceValues(id, transitionName, values, callbackSetTaskInstanceValues);
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
	
	/**
	 * TitleWidget
	 * 
	 * @author jllort
	 *
	 */
	class TitleWidget extends HorizontalPanel implements HasClickHandlers   {
		
		HTML title;
		Image zoomImage;
		boolean zoom = false;
		int[] relatedRows;
		
		/**
		 * TitleWidget
		 */
		public TitleWidget(String text, int[] relatedRows) {
			super();
			sinkEvents(Event.ONCLICK);
			
			title = new HTML("");
			setTitle(text);
			this.relatedRows = relatedRows;
			
			if (zoom) {
				zoomImage = new Image("img/zoom_out.gif");
			} else {
				zoomImage = new Image("img/zoom_in.gif");
			}
			zoomImage.setStyleName("okm-Hyperlink");
			
			addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					zoom = !zoom;
					setVisibleRows(zoom);
				}
			});
			
			add(title);
			add(zoomImage);	
			setCellHorizontalAlignment(title, HasAlignment.ALIGN_CENTER);
			setCellHorizontalAlignment(zoomImage, HasAlignment.ALIGN_LEFT);
			setCellWidth(zoomImage, "22");
		}
		
		/* (non-Javadoc)
		 * @see com.google.gwt.user.client.ui.UIObject#setTitle(java.lang.String)
		 */
		public void setTitle(String text) {
			title.setHTML("<b>"+ text.toUpperCase() + "</b>");
		}
		
		/**
		 * setVisibleRows
		 * 
		 * @param visible
		 */
		public void setVisibleRows(boolean zoom) {
			this.zoom = zoom;
			showRelatedRows(zoom);
			if (zoom) {
				zoomImage.setUrl("img/zoom_out.gif");
			} else {
				zoomImage.setUrl("img/zoom_in.gif");
			}
		}
		
		/**
		 * showRelatedRows
		 * 
		 * @param visible
		 */
		private void showRelatedRows(boolean zoom) {
			for (int i=0; i<relatedRows.length; i++) {
				table.getRowFormatter().setVisible(relatedRows[i], zoom);
			}
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google.gwt.event.dom.client.ClickHandler)
		 */
		@Override
		public HandlerRegistration addClickHandler(ClickHandler handler) {
			return addHandler(handler, ClickEvent.getType());
		}
	}
}