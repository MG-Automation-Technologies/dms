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

package es.git.openkm.frontend.client.widget.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTButton;
import es.git.openkm.frontend.client.bean.GWTFormElement;
import es.git.openkm.frontend.client.bean.GWTInput;
import es.git.openkm.frontend.client.bean.GWTOption;
import es.git.openkm.frontend.client.bean.GWTProcessDefinition;
import es.git.openkm.frontend.client.bean.GWTProcessInstance;
import es.git.openkm.frontend.client.bean.GWTSelect;
import es.git.openkm.frontend.client.bean.GWTTaskInstance;
import es.git.openkm.frontend.client.bean.GWTTextArea;
import es.git.openkm.frontend.client.bean.GWTWorkflowComment;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMRepositoryService;
import es.git.openkm.frontend.client.service.OKMRepositoryServiceAsync;
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
	private final OKMRepositoryServiceAsync repositoryService = (OKMRepositoryServiceAsync) GWT.create(OKMRepositoryService.class);
	private final String VARIABLE_KEY_ID = "$";
	
	private VerticalPanel vPanel;
	private GWTTaskInstance taskInstance;
	private FlexTable table;
	private FlexTable parameterTable;
	private FlexTable formTable;
	private Collection<GWTFormElement> formFieldList = new ArrayList<GWTFormElement>();
	private Button submitForm;
	private List<FormWidget> formWidgetList = new ArrayList<FormWidget>();
	private TitleWidget taskTittle;
	private TitleWidget processInstanceTittle;
	private TitleWidget processDefinitionTittle;
	private TitleWidget parametersTittle;
	private TitleWidget commentsTitle;
	private TitleWidget formsTitle;
	private Hyperlink documentLink;
	private VerticalPanel newNotePanel;
	private TextArea textArea;
	private HTML addComment;
	private Button add;
	private FlexTable tableNotes;
	
	/**
	 * WorkflowFormPanel
	 */
	public WorkflowFormPanel() {
		vPanel = new VerticalPanel();
		table = new FlexTable();
		formTable = new FlexTable();
		parameterTable = new FlexTable();
		newNotePanel = new VerticalPanel(); 
		tableNotes = new FlexTable();
		textArea = new TextArea();
		addComment = new HTML("<b>" + Main.i18n("dashboard.workflow.add.comment") + "</b>");
		textArea.setSize("500px", "100px");
		
		textArea.addKeyboardListener(new KeyboardListener() {
			public void onKeyUp(Widget arg0, char arg1, int arg2) {
				if (!textArea.getText().equals("")) {
					add.setEnabled(true);
				} else {
					add.setEnabled(false);
				}
			}
			public void onKeyPress(Widget arg0, char arg1, int arg2) {
			}
			public void onKeyDown(Widget arg0, char arg1, int arg2) {
			}
		});
	    
		add = new Button(Main.i18n("button.add"), new ClickListener() {
			public void onClick(Widget arg0) {
				addComment();
			}
		});
		add.setEnabled(false);
	    
		submitForm = new Button(Main.i18n("button.accept"), new ClickListener() {
			public void onClick(Widget arg0) {
				setTaskInstanceValues(taskInstance.getId(), null); 
			}
		});
		
		HTML space = new HTML("");
		newNotePanel.add(space);
		newNotePanel.add(addComment);
		newNotePanel.add(textArea);
		HTML space2 = new HTML("");
		newNotePanel.add(space2);
		newNotePanel.add(add);
		
		newNotePanel.setCellHeight(space, "40px");
		newNotePanel.setCellHeight(space2, "10px");
		newNotePanel.setCellHorizontalAlignment(addComment, HasAlignment.ALIGN_CENTER);
		newNotePanel.setCellHorizontalAlignment(add, HasAlignment.ALIGN_CENTER);
	    
		
		int[] taskRow = {1, 2, 3, 4, 5, 6 };
		int[] processInstanceRow = {8, 9, 10};
		int[] processDefinitionRow = {12, 13, 14, 15 };
		int[] dataTitle = {17};
		int[] commentTitle = {19};
		int[] formTitle = {};
		taskTittle = new TitleWidget(Main.i18n("dashboard.workflow.task"), taskRow);
		processInstanceTittle = new TitleWidget(Main.i18n("dashboard.workflow.task.process.instance"), processInstanceRow);
		processDefinitionTittle = new TitleWidget(Main.i18n("dashboard.workflow.task.process.definition"), processDefinitionRow);
		parametersTittle = new TitleWidget(Main.i18n("dashboard.workflow.task.process.data"), dataTitle);
		commentsTitle = new TitleWidget(Main.i18n("dashboard.workflow.comments"), commentTitle);
		formsTitle  = new TitleWidget(Main.i18n("dashboard.workflow.task.process.forms"), formTitle);
		taskTittle.setWidth("100%");
		processInstanceTittle.setWidth("100%");
		processDefinitionTittle.setWidth("100%");
		parametersTittle.setWidth("100%");
		commentsTitle.setWidth("100%");
		formsTitle.setWidth("100%");
		
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
		table.setWidget(16, 0, parametersTittle);
		table.setWidget(17, 0, parameterTable);
		table.setWidget(18, 0, 	commentsTitle);
		table.setWidget(19, 0, tableNotes);
		table.setWidget(20, 0, 	formsTitle);
		table.setWidget(21, 0, formTable);
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
		parametersTittle.setVisibleRows(true);
		commentsTitle.setVisibleRows(false);
		formsTitle.setVisibleRows(true);
		
		table.getCellFormatter().setWidth(1, 2, "100%");
		table.getCellFormatter().setWidth(8, 2, "100%");
		table.getCellFormatter().setWidth(12, 2, "100%");
		table.getFlexCellFormatter().setColSpan(0, 0, 3);
		table.getFlexCellFormatter().setColSpan(7, 0, 3);
		table.getFlexCellFormatter().setColSpan(11, 0, 3);
		table.getFlexCellFormatter().setColSpan(16, 0, 3);
		table.getFlexCellFormatter().setColSpan(17, 0, 3);
		table.getFlexCellFormatter().setColSpan(18, 0, 3);
		table.getFlexCellFormatter().setColSpan(19, 0, 3);
		table.getFlexCellFormatter().setColSpan(20, 0, 3);
		table.getFlexCellFormatter().setColSpan(21, 0, 3);
		table.getCellFormatter().setStyleName(0, 0, "okm-WorkflowFormPanel-Title");
		table.getCellFormatter().setStyleName(7, 0, "okm-WorkflowFormPanel-Title");
		table.getCellFormatter().setStyleName(11, 0, "okm-WorkflowFormPanel-Title");
		table.getCellFormatter().setStyleName(16, 0, "okm-WorkflowFormPanel-Title");
		table.getCellFormatter().setStyleName(18, 0, "okm-WorkflowFormPanel-Title");
		table.getCellFormatter().setStyleName(20, 0, "okm-WorkflowFormPanel-Title");
		
		vPanel.add(table);
		
		table.setStyleName("okm-NoWrap");
		vPanel.setStyleName("okm-WorkflowFormPanel");
		submitForm.setStyleName("okm-Button");
		add.setStyleName("okm-Button");
		textArea.setStyleName("okm-Input");
		tableNotes.setStyleName("okm-DisableSelect");
		
		tableNotes.setWidth("100%");
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
		parametersTittle.setTitle(Main.i18n("dashboard.workflow.task.process.data"));
		commentsTitle.setTitle(Main.i18n("dashboard.workflow.comments"));
		formsTitle.setTitle(Main.i18n("dashboard.workflow.task.process.forms"));
		submitForm.setHTML(Main.i18n("button.accept"));
		addComment.setHTML("<b>" + Main.i18n("dashboard.workflow.add.comment") + "</b>");
		add.setText(Main.i18n("button.add"));
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
		
		clearPanel();
		
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
		
		documentLink = null;
		// Print variables
		for (Iterator<String> it = processInstance.getVariables().keySet().iterator(); it.hasNext();) {
			String key = it.next();
			final String value = (String) processInstance.getVariables().get(key);
			int row = parameterTable.getRowCount();
			
			// Special case path
			if (key.equals(Main.get().workspaceUserProperties.getWorkspace().getWorkflowProcessIntanceVariableUUID())) {
				final int documentRow = row;
				parameterTable.setHTML(documentRow, 0, "<b>"+ 
						               Main.get().workspaceUserProperties.getWorkspace().getWorkflowProcessIntanceVariablePath() + 
						               "</b>");
				
				ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
				endPoint.setServiceEntryPoint(Config.OKMRepositoryService);		
				repositoryService.getPathByUUID(value, new AsyncCallback<String>() {
					public void onSuccess(final String docPath) {
						Hyperlink link = new Hyperlink();
						link.setText(docPath);
						table.setWidget(10, 1, link);
						link.addClickListener(new ClickListener() {
							public void onClick(Widget arg0) {
								String path = docPath.substring(0,docPath.lastIndexOf("/"));
								CommonUI.openAllFolderPath(path, docPath);	
							}
						});
						link.setStyleName("okm-Hyperlink");
						
						// Clones link
						documentLink = new Hyperlink();
						documentLink.setText(docPath);
						documentLink.addClickListener(new ClickListener() {
							public void onClick(Widget arg0) {
								String path = docPath.substring(0,docPath.lastIndexOf("/"));
								CommonUI.openAllFolderPath(path, docPath);	
							}
						});
						documentLink.setStyleName("okm-Hyperlink");
						parameterTable.setWidget(documentRow, 1, documentLink);
					}
					
					public void onFailure(Throwable caught) {
						Main.get().showError("getPathByUUID", caught);
					}
				});
				
				
			}  else {
				
				parameterTable.setHTML(row, 0, "<b>" + key + "</b>");
				parameterTable.setHTML(row, 1, value);
			}
		}
		
		// Print comments
		for (Iterator<GWTWorkflowComment> it = processInstance.getRootToken().getComments().iterator(); it.hasNext();) {
			writeComment(it.next());
		}
		writeAddComment();
		
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
		table.setHTML(6, 1, "");
		table.setHTML(8, 1, "");
		table.setHTML(9, 1, "");
		table.setHTML(10, 1, "");
		table.setHTML(12, 1, "");
		table.setHTML(13, 1, "");
		table.setHTML(14, 1, "");
		table.setHTML(15, 1, "");
		formWidgetList = new ArrayList<FormWidget>(); // Init new form widget list
		formFieldList = new ArrayList<GWTFormElement>();
		documentLink = null;
		textArea.setText("");
		removeAllParametersTableRows();
		removeAllFormTableRows();
		removeAllCommentsTableRows();
	}
	
	/**
	 * Get process definitions callback
	 */
	final AsyncCallback<Map<String, Collection<GWTFormElement>>> callbackGetProcessDefinitionForms = new AsyncCallback<Map<String, Collection<GWTFormElement>>>() {
		public void onSuccess(Map<String, Collection<GWTFormElement>> result) {
			formFieldList = new ArrayList<GWTFormElement>();
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
		Map<String, Object> variables = taskInstance.getProcessInstance().getVariables();
		submitForm.setVisible(true); // always set form visible
		HorizontalPanel hPanel = new HorizontalPanel();
		formWidgetList = new ArrayList<FormWidget>(); // Init new form widget list
		
		// Deletes all table rows
		removeAllFormTableRows();
		
		// Adds submit button
		if (formFieldList.size()>0) {
			HTML space = new HTML("&nbsp;");
			hPanel.add(submitForm);
			hPanel.add(space);
			hPanel.setCellWidth(space, "5px");
		}
		
		// Sets form fields
		for (Iterator<GWTFormElement> it = formFieldList.iterator(); it.hasNext(); ) {
			final GWTFormElement formField = it.next();
			int row = formTable.getRowCount();
			
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
				transButton.addClickListener(new ClickListener() {
					public void onClick(Widget arg0) {
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
				
				// Initializing input value from variable
				if (gWTInput.getValue().startsWith(VARIABLE_KEY_ID) && variables.containsKey(gWTInput.getValue().substring(1))) {
					textBox.setText((String) variables.get(gWTInput.getValue().substring(1)));
				} else {
					textBox.setText(gWTInput.getValue());
				}
				
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
				
				String selectedValue = "";
				// Initialize select value
				if (gWTSelect.getValue().startsWith(VARIABLE_KEY_ID) && variables.containsKey(gWTSelect.getValue().substring(1))) {
					selectedValue = (String) variables.get(gWTSelect.getValue().substring(1));
				} else {
					selectedValue = gWTSelect.getValue();
				}
				
				for (Iterator<GWTOption> itx = gWTSelect.getOptions().iterator(); itx.hasNext(); ) {
					GWTOption option = itx.next();
					listBox.addItem(option.getLabel(), option.getValue());
					if (option.getValue().equals(selectedValue)) {
						listBox.setSelectedIndex(listBox.getItemCount()-1);
					}
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
				
				// Initialize textarea value
				if (gWTTextArea.getValue().startsWith(VARIABLE_KEY_ID) && variables.containsKey(gWTTextArea.getValue().substring(1))) {
					textArea.setText((String) variables.get(gWTTextArea.getValue().substring(1)));
				} else {
					textArea.setText(gWTTextArea.getValue());
				}
				
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
	}
	
	/**
	 * Get subscribed documents callback
	 */
	final AsyncCallback<Object> callbackSetTaskInstanceValues = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {
			Main.get().mainPanel.dashboard.workflowDashboard.refreshAll();
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
		Map<String, Object> values = new HashMap<String, Object>();
		for (Iterator<FormWidget> it = formWidgetList.iterator(); it.hasNext();) {
			FormWidget fw = it.next();
			if (fw.getWidget() instanceof TextBox) {
				TextBox textBox = (TextBox) fw.getWidget();
				values.put(textBox.getName(), textBox.getText());
			} else if (fw.getWidget() instanceof TextArea) {
				TextArea textArea = (TextArea) fw.getWidget();
				values.put(textArea.getName(), textArea.getText());
			} else if (fw.getWidget() instanceof ListBox) {
				ListBox listBox = (ListBox) fw.getWidget();
				values.put(listBox.getName(), listBox.getValue(listBox.getSelectedIndex()));
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
	 * removeAllFormTableRows
	 */
	private void removeAllFormTableRows() {
		// Deletes all table rows
		while (formTable.getRowCount()>0) {
			formTable.removeRow(0);
		}
	}
	
	/**
	 * removeAllFormTableRows
	 */
	private void removeAllParametersTableRows() {
		// Deletes all table rows
		while (parameterTable.getRowCount()>0) {
			parameterTable.removeRow(0);
		}
	}
	
	/**
	 * removeAllCommentsTableRows
	 */
	private void removeAllCommentsTableRows() {
		while (tableNotes.getRowCount()>0) {
			tableNotes.removeRow(0);
		}
	}
	
	/**
	 * Writes the note 
	 * 
	 * @param comment
	 */
	private void writeComment(GWTWorkflowComment comment) {
		int row = tableNotes.getRowCount();
		tableNotes.setHTML(row, 0, "<b>" + comment.getActorId() + "</b>");
		DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
		tableNotes.setHTML(row, 1, dtf.format(comment.getTime()));
		tableNotes.getCellFormatter().setHorizontalAlignment(row, 1, HasAlignment.ALIGN_RIGHT);
		tableNotes.getRowFormatter().setStyleName(row, "okm-Notes-Title");
		tableNotes.getCellFormatter().setHeight(row, 1, "30");
		tableNotes.getCellFormatter().setVerticalAlignment(row, 0, HasAlignment.ALIGN_BOTTOM);
		tableNotes.getCellFormatter().setVerticalAlignment(row, 1, HasAlignment.ALIGN_BOTTOM);
		row++;
		tableNotes.setHTML(row, 0, "");
		tableNotes.getCellFormatter().setHeight(row, 0, "6");
		tableNotes.getRowFormatter().setStyleName(row, "okm-Notes-Line");
		tableNotes.getFlexCellFormatter().setColSpan(row, 0, 2);
		row++;
		tableNotes.setHTML(row, 0, comment.getMessage());
		tableNotes.getFlexCellFormatter().setColSpan(row, 0, 2);
	}
	
	/**
	 * writeAddNote
	 */
	private void writeAddComment() {
		int row = tableNotes.getRowCount();
		tableNotes.setWidget(row, 0, newNotePanel);
		tableNotes.getFlexCellFormatter().setColSpan(row, 0, 2);
		tableNotes.getCellFormatter().setHorizontalAlignment(row, 0, HasAlignment.ALIGN_CENTER);
	}
	
	/**
	 * Callback addComment workflow
	 */
	final AsyncCallback<Object> callbackAddComment = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {	
			GWTWorkflowComment comment = new GWTWorkflowComment();
			comment.setMessage(textArea.getText());
			comment.setTime(new Date());
			comment.setActorId(Main.get().workspaceUserProperties.getUser());
			taskInstance.getProcessInstance().getRootToken().getComments().add(comment);
			tableNotes.removeRow(tableNotes.getRowCount()-1); // Deletes last row = addComment
			writeComment(comment);
			writeAddComment();
			textArea.setText("");
			add.setEnabled(false);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("addComment", caught);
		}
	};
	
	/**
	 * addNote document
	 */
	private void addComment() {
		if (!textArea.getText().equals("")) {
			ServiceDefTarget endPoint = (ServiceDefTarget) workflowService;
			endPoint.setServiceEntryPoint(Config.OKMWorkflowService);
			workflowService.addComment(taskInstance.getProcessInstance().getRootToken().getId(), textArea.getText(), callbackAddComment);
		}
	}
	
	/**
	 * TitleWidget
	 * 
	 * @author jllort
	 *
	 */
	class TitleWidget extends HorizontalPanel implements SourcesClickEvents   {
		
		private ClickListenerCollection clickListeners;
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
			
			addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
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
		 * @see com.google.gwt.user.client.ui.SourcesClickEvents#addClickListener(com.google.gwt.user.client.ui.ClickListener)
		 */
		public void addClickListener(ClickListener listener) {
			if (clickListeners == null) {
		      clickListeners = new ClickListenerCollection();
		    }
		    clickListeners.add(listener);
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.user.client.ui.SourcesClickEvents#removeClickListener(com.google.gwt.user.client.ui.ClickListener)
		 */
		public void removeClickListener(ClickListener listener) {
			if (clickListeners != null) {
				clickListeners.remove(listener);
			}
		}
		
		/* (non-Javadoc)
		 * @see com.google.gwt.user.client.ui.Composite#onBrowserEvent(com.google.gwt.user.client.Event)
		 */
		public void onBrowserEvent(Event event) {
		    switch (DOM.eventGetType(event)) {
		    	case Event.ONCLICK: {
		    		if (clickListeners != null) {
		    			clickListeners.fireClick(this);
		    		}
		    		break;
		    	}
		    }
		    super.onBrowserEvent(event);
		}
	}
}