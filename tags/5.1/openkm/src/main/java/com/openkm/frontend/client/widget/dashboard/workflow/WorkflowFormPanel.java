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

package com.openkm.frontend.client.widget.dashboard.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Event;
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
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTButton;
import com.openkm.frontend.client.bean.GWTCheckBox;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTFormElement;
import com.openkm.frontend.client.bean.GWTInput;
import com.openkm.frontend.client.bean.GWTOption;
import com.openkm.frontend.client.bean.GWTProcessDefinition;
import com.openkm.frontend.client.bean.GWTProcessInstance;
import com.openkm.frontend.client.bean.GWTSelect;
import com.openkm.frontend.client.bean.GWTTaskInstance;
import com.openkm.frontend.client.bean.GWTTextArea;
import com.openkm.frontend.client.bean.GWTValidator;
import com.openkm.frontend.client.bean.GWTWorkflowComment;
import com.openkm.frontend.client.contants.service.RPCService;
import com.openkm.frontend.client.service.OKMDocumentService;
import com.openkm.frontend.client.service.OKMDocumentServiceAsync;
import com.openkm.frontend.client.service.OKMFolderService;
import com.openkm.frontend.client.service.OKMFolderServiceAsync;
import com.openkm.frontend.client.service.OKMRepositoryService;
import com.openkm.frontend.client.service.OKMRepositoryServiceAsync;
import com.openkm.frontend.client.service.OKMWorkflowService;
import com.openkm.frontend.client.service.OKMWorkflowServiceAsync;
import com.openkm.frontend.client.util.CommonUI;
import com.openkm.frontend.client.util.OKMBundleResources;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.util.validator.ValidatorBuilder;
import com.openkm.frontend.client.widget.propertygroup.FolderSelectPopup;
import com.openkm.frontend.client.widget.searchin.CalendarWidget;

import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.actions.FocusAction;

/**
 * WorkflowFormPanel
 * 
 * @author jllort
 *
 */
public class WorkflowFormPanel extends Composite {
	
	private final OKMWorkflowServiceAsync workflowService = (OKMWorkflowServiceAsync) GWT.create(OKMWorkflowService.class);
	private final OKMRepositoryServiceAsync repositoryService = (OKMRepositoryServiceAsync) GWT.create(OKMRepositoryService.class);
	private final OKMDocumentServiceAsync documentService = (OKMDocumentServiceAsync) GWT.create(OKMDocumentService.class);
	private final OKMFolderServiceAsync folderService = (OKMFolderServiceAsync) GWT.create(OKMFolderService.class);
	
	private VerticalPanel vPanel;
	private GWTTaskInstance taskInstance;
	private FlexTable table;
	private FlexTable parameterTable;
	private FlexTable formTable;
	private List<GWTFormElement> formElementList = new ArrayList<GWTFormElement>();
	private Button submitForm;
	private Map<String, Widget> formWidgetList = new HashMap<String, Widget>();
	private TitleWidget taskTittle;
	private TitleWidget processInstanceTittle;
	private TitleWidget processDefinitionTittle;
	private TitleWidget parametersTittle;
	private TitleWidget commentsTitle;
	private TitleWidget formsTitle;
	private Anchor documentLink;
	private VerticalPanel newNotePanel;
	private TextArea textArea;
	private HTML addComment;
	private Button add;
	private FlexTable tableNotes;
	private FolderSelectPopup folderSelectPopup;
	ValidationProcessor validationProcessor;
	
	/**
	 * WorkflowFormPanel
	 */
	public WorkflowFormPanel() {
		folderSelectPopup = new FolderSelectPopup();
		vPanel = new VerticalPanel();
		table = new FlexTable();
		formTable = new FlexTable();
		parameterTable = new FlexTable();
		newNotePanel = new VerticalPanel(); 
		tableNotes = new FlexTable();
		textArea = new TextArea();
		addComment = new HTML("<b>" + Main.i18n("dashboard.workflow.add.comment") + "</b>");
		textArea.setSize("500px", "100px");
		
		textArea.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent arg0) {
				if (!textArea.getText().equals("")) {
					add.setEnabled(true);
				} else {
					add.setEnabled(false);
				}
			}
		});
	    
		add = new Button(Main.i18n("button.add"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				addComment();
			}
		});
		add.setEnabled(false);
	    
		submitForm = new Button(Main.i18n("button.accept"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (validationProcessor.validate()) {
					setTaskInstanceValues(taskInstance.getId(), null);
				}
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
			if (processInstance.getVariables().get(key) instanceof String) {
				final String value = (String) processInstance.getVariables().get(key);
				int row = parameterTable.getRowCount();
				
				// Special case path
				if (key.equals(Main.get().workspaceUserProperties.getWorkspace().getWorkflowProcessIntanceVariableUUID())) {
					final int documentRow = row;
					parameterTable.setHTML(documentRow, 0, "<b>"+ 
							               Main.get().workspaceUserProperties.getWorkspace().getWorkflowProcessIntanceVariablePath() + 
							               "</b>");
					
					ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
					endPoint.setServiceEntryPoint(RPCService.RepositoryService);		
					repositoryService.getPathByUUID(value, new AsyncCallback<String>() {
						@Override
						public void onSuccess(final String docPath) {
							// Validating if is document / folder / mail and displaying object path
							ServiceDefTarget endPoint = (ServiceDefTarget) documentService;
							endPoint.setServiceEntryPoint(RPCService.DocumentService);
							documentService.isValid(docPath, new AsyncCallback<Boolean>() {
								public void onSuccess(Boolean result) {
									if (result.booleanValue()) {
										writePath(documentRow, docPath, false);
									} else {
										ServiceDefTarget endPoint = (ServiceDefTarget) folderService;
										endPoint.setServiceEntryPoint(RPCService.FolderService);
										folderService.isValid(docPath, new AsyncCallback<Boolean>() {
											public void onSuccess(Boolean result) {
												if (result.booleanValue()) {
													writePath(documentRow, docPath, true);
												} else {
													// must be a mail object
													writePath(documentRow, docPath, false);
												}
											}
											
											@Override
											public void onFailure(Throwable caught) {
												Main.get().showError("isValid",caught);
											}
										});
									}
								}
								
								@Override
								public void onFailure(Throwable caught) {
									Main.get().showError("isValid",caught);
								}
							});
						}
						
						@Override
						public void onFailure(Throwable caught) {
							Main.get().showError("getPathByUUID", caught);
						}
					});
					
				}  else {
					
					parameterTable.setHTML(row, 0, "<b>" + key + "</b>");
					parameterTable.setHTML(row, 1, value);
				}
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
		formWidgetList = new HashMap<String, Widget>(); // Init new form widget list
		formElementList = new ArrayList<GWTFormElement>();
		documentLink = null;
		textArea.setText("");
		removeAllParametersTableRows();
		removeAllFormTableRows();
		removeAllCommentsTableRows();
	}
	
	/**
	 * Get process definitions callback
	 */
	final AsyncCallback<Map<String, List<GWTFormElement>>> callbackGetProcessDefinitionForms = new AsyncCallback<Map<String, List<GWTFormElement>>>() {
		public void onSuccess(Map<String, List<GWTFormElement>> result) {
			formElementList = new ArrayList<GWTFormElement>();
			if (result.containsKey(taskInstance.getName())) {
				formElementList = result.get(taskInstance.getName());
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
		endPoint.setServiceEntryPoint(RPCService.WorkflowService);		
		workflowService.getProcessDefinitionForms(id, callbackGetProcessDefinitionForms);
	}
	
	/**
	 * drawForm
	 */
	private void drawForm() {
		validationProcessor = new DefaultValidationProcessor();
		FocusAction focusAction = new FocusAction();
		
		submitForm.setVisible(true); // always set form visible
		HorizontalPanel hPanel = new HorizontalPanel();
		formWidgetList = new HashMap<String, Widget>(); // Init new form widget list
		
		// Deletes all table rows
		removeAllFormTableRows();
		
		// Adds submit button
		if (formElementList.size()>0) {
			HTML space = new HTML("&nbsp;");
			hPanel.add(submitForm);
			hPanel.add(space);
			hPanel.setCellWidth(space, "5px");
		}
		
		// Sets form fields
		for (Iterator<GWTFormElement> it = formElementList.iterator(); it.hasNext(); ) {
			final GWTFormElement formField = it.next();
			int row = formTable.getRowCount();
			
			// Prepares to save widget list values
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
						if (validationProcessor.validate()) {
							if (gWTButton.getType().equals(GWTButton.TYPE_TRANSITION)) {
								setTaskInstanceValues(taskInstance.getId(), gWTButton.getValue());
							} else {
								setTaskInstanceValues(taskInstance.getId(), null);
							}
						}
					}
				});
				
			} else if (formField instanceof GWTInput) {
				final GWTInput gWTInput = (GWTInput) formField;
				HorizontalPanel hFormPanel = new HorizontalPanel();
				final TextBox textBox = new TextBox();
				hFormPanel.add(textBox);
				textBox.setName(gWTInput.getName());
				if (gWTInput.getType().equals(GWTInput.TYPE_TEXT) || 
					gWTInput.getType().equals(GWTInput.TYPE_LINK) ||
					gWTInput.getType().equals(GWTInput.TYPE_FOLDER))  {
					textBox.setValue(gWTInput.getValue());
				} else if (gWTInput.getType().equals(GWTInput.TYPE_DATE))  {
					if (gWTInput.getDate()!=null) {
						DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.day.pattern"));
						textBox.setText(dtf.format(gWTInput.getDate()));
					}
				} 
				textBox.setWidth(gWTInput.getWidth());
				
				// Case is read only
				if (textBox.isReadOnly()) {
					textBox.setVisible(false);
					if (gWTInput.getType().equals(GWTInput.TYPE_TEXT) || gWTInput.getType().equals(GWTInput.TYPE_DATE)) {
						hFormPanel.add(new HTML(textBox.getValue()));
					}
				}
				
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
							gWTInput.setDate(calendar.getDate());
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
					if (!gWTInput.isReadonly()) {
						hFormPanel.add(new HTML("&nbsp;"));
						hFormPanel.add(calendarIcon);
						textBox.setEnabled(false);
						hFormPanel.setCellVerticalAlignment(calendarIcon, HasAlignment.ALIGN_MIDDLE);
					} 
					
				} else if (gWTInput.getType().equals(GWTInput.TYPE_LINK))  {
					String value = gWTInput.getValue(); 
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
						String containerName = gWTInput.getName() + "ContainerName";
						hLinkPanel.add(new HTML("<div id=\""+containerName+"\"></div>\n"));
						HTML space = new HTML("");
						hLinkPanel.add(space);
						hLinkPanel.add(anchor);
						hLinkPanel.setCellWidth(space, "5px");
						if (gWTInput.isReadonly()) {
							hFormPanel.add(hLinkPanel);
							Util.createLinkClipboardButton(url, containerName);
							hFormPanel.setCellVerticalAlignment(hLinkPanel, HasAlignment.ALIGN_MIDDLE);
						}
					} 					
					
				} else if (gWTInput.getType().equals(GWTInput.TYPE_FOLDER))  {
					String value = gWTInput.getValue(); 
					if (!value.equals("")) {
						Anchor anchor = new Anchor();
						final GWTFolder folder = gWTInput.getFolder();
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
						if (gWTInput.isReadonly()) {
							hFormPanel.add(anchor);
							hFormPanel.setCellVerticalAlignment(anchor, HasAlignment.ALIGN_MIDDLE);
						}
					} 
					Image pathExplorer = new Image(OKMBundleResources.INSTANCE.folderExplorer());
					pathExplorer.addClickHandler(new ClickHandler() { 
						@Override
						public void onClick(ClickEvent event) {
							folderSelectPopup.show(textBox, gWTInput.getFolder());
						}
					});
					Image cleanPathExplorer = new Image(OKMBundleResources.INSTANCE.deleteIcon());
					cleanPathExplorer.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							textBox.setValue("");
							gWTInput.setFolder(new GWTFolder());
						}
					});
					pathExplorer.setStyleName("okm-KeyMap-ImageHover");
					cleanPathExplorer.setStyleName("okm-KeyMap-ImageHover");
					HorizontalPanel hFolderPanel = new HorizontalPanel();
					hFolderPanel.add(new HTML("&nbsp;"));
					hFolderPanel.add(pathExplorer);
					hFolderPanel.add(new HTML("&nbsp;"));
					hFolderPanel.add(cleanPathExplorer);
					hFolderPanel.setCellVerticalAlignment(pathExplorer, HasAlignment.ALIGN_MIDDLE);
					hFolderPanel.setCellVerticalAlignment(cleanPathExplorer, HasAlignment.ALIGN_MIDDLE);
					if (!gWTInput.isReadonly()) {
						hFormPanel.add(hFolderPanel);
						textBox.setEnabled(false);
						hFormPanel.setCellVerticalAlignment(hFolderPanel, HasAlignment.ALIGN_MIDDLE);
					}
				}
				
				textBox.setWidth(gWTInput.getWidth());
				textBox.setStyleName("okm-Input");
				formTable.setHTML(row, 0, "<b>" + gWTInput.getLabel() + "</b>");
				formTable.setWidget(row, 1, hFormPanel);
				widget = hFormPanel;
				
				for (GWTValidator validator : ((GWTInput) formField).getValidators()) {
					ValidatorBuilder.addValidator(validationProcessor, focusAction, hFormPanel, "input_"+row, validator, textBox);
				}
				
			} else if (formField instanceof GWTCheckBox) {
				GWTCheckBox gWTCheckBox = (GWTCheckBox) formField;
				CheckBox checkBox = new CheckBox();
				checkBox.setValue(gWTCheckBox.getValue());
				checkBox.setEnabled(!gWTCheckBox.isReadonly());
				formTable.setHTML(row, 0, "<b>" + gWTCheckBox.getLabel() + "</b>");
				formTable.setWidget(row, 1, checkBox);
				widget = checkBox;
				
			} else if (formField instanceof GWTSelect) {
				final int rowButton = row;
				final GWTSelect gWTSelect = (GWTSelect) formField;
				HorizontalPanel hFormPanel = new HorizontalPanel();
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
							removeImage.setVisible(!gWTSelect.isReadonly());
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
				addButton.setVisible(!gWTSelect.isReadonly());
				
				listBox.setName(gWTSelect.getName());
				listBox.setWidth(gWTSelect.getWidth());
				listBox.setStyleName("okm-Select");
				listBox.addItem("",""); // Always we set and empty value
				
				formTable.setHTML(row, 0, "<b>" + gWTSelect.getLabel() + "</b>");
				if (gWTSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
					hFormPanel.add(listBox);
					listBox.setEnabled(!gWTSelect.isReadonly());
					formTable.setWidget(row, 1, hFormPanel);
					widget = hFormPanel;
					
					for (GWTValidator validator : ((GWTSelect) formField).getValidators()) {
						ValidatorBuilder.addValidator(validationProcessor, focusAction, hFormPanel, "select_"+row, validator, listBox);
					}
				} else if (gWTSelect.getType().equals(GWTSelect.TYPE_MULTIPLE)) {
					hFormPanel.add(tableMulti);
					hFormPanel.add(addButton);
					hFormPanel.setCellVerticalAlignment(addButton, HasAlignment.ALIGN_TOP);
					row++; // Incrementing row
					formTable.setHTML(row, 0, "");
					formTable.setWidget(row, 1, hFormPanel);
					HTML name = new HTML(gWTSelect.getName()); // First table name it'll be the value name
					tableMulti.setWidget(0,0,name);
					name.setVisible(false);
					widget = hFormPanel;
					
					for (GWTValidator validator : ((GWTSelect) formField).getValidators()) {
						ValidatorBuilder.addValidator(validationProcessor, focusAction, hFormPanel, "select_"+row, validator, tableMulti);
					}
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
							removeImage.setVisible(!gWTSelect.isReadonly());
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
				HorizontalPanel hFormPanel = new HorizontalPanel();
				TextArea textArea = new TextArea();
				hFormPanel.add(textArea);
				textArea.setName(gWTTextArea.getName());
				textArea.setSize(gWTTextArea.getWidth(), gWTTextArea.getHeight());
				textArea.setValue(gWTTextArea.getValue());
				textArea.setReadOnly(gWTTextArea.isReadonly());
				
				textArea.setStyleName("okm-Input");
				formTable.setHTML(row, 0, "<b>" + gWTTextArea.getLabel() + "</b>");
				formTable.setWidget(row, 1, hFormPanel);
				formTable.getCellFormatter().setVerticalAlignment(row, 0, HasAlignment.ALIGN_TOP);
				widget = hFormPanel;
				
				for (GWTValidator validator : ((GWTTextArea) formField).getValidators()) {
					ValidatorBuilder.addValidator(validationProcessor, focusAction, hFormPanel, "textarea_"+row, validator, textArea);
				}
			} 
			
			// Saves widget
			if (widget!=null) {
				formWidgetList.put(formField.getName(), widget);
			}
		}
		
		// Adds submit button
		if (formElementList.size()>0) {
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
		for (Iterator<GWTFormElement> it = formElementList.iterator(); it.hasNext();) {
			GWTFormElement formElement = it.next();
			if (formWidgetList.containsKey(formElement.getName())) {
				Widget widget = formWidgetList.get(formElement.getName());
				if (formElement instanceof GWTInput) {
					// Date is set in drawform click handler, here only capture text
					HorizontalPanel hPanel = (HorizontalPanel) widget;
					TextBox textBox = (TextBox) hPanel.getWidget(0);
					if (((GWTInput) formElement).getType().equals(GWTInput.TYPE_LINK)) {
						// Always must start with http://
						if (!textBox.getText().equals("") && !textBox.getText().startsWith("http://")) {
							textBox.setText("http://" + textBox.getText());
						}
					} 
					((GWTInput)formElement).setValue(textBox.getValue()); 
					
				} else if (formElement instanceof GWTCheckBox) {
					((GWTCheckBox) formElement).setValue(((CheckBox) widget).getValue());
					
				} else if (formElement instanceof GWTTextArea) {
					HorizontalPanel hPanel = (HorizontalPanel) widget;
					TextArea textArea = (TextArea) hPanel.getWidget(0);
					((GWTTextArea) formElement).setValue(textArea.getValue());
					
				} else if (formElement instanceof GWTSelect) {
					GWTSelect select = (GWTSelect) formElement; 
					// Disables all options
					for (Iterator<GWTOption> itx = select.getOptions().iterator(); itx.hasNext();)  {
						itx.next().setSelected(false);
					}
					if (select.getType().equals(GWTSelect.TYPE_SIMPLE)) {
						HorizontalPanel hPanel = (HorizontalPanel) widget;
						ListBox listBox = (ListBox) hPanel.getWidget(0);
						if (listBox.getSelectedIndex()>=0) {
							for (Iterator<GWTOption> itx = select.getOptions().iterator(); itx.hasNext();)  {
								GWTOption option = itx.next();
								if (option.getValue().equals(listBox.getValue(listBox.getSelectedIndex()))) {
									option.setSelected(true);
								}
							}
						}
					} else if (select.getType().equals(GWTSelect.TYPE_MULTIPLE)) {
						HorizontalPanel hPanel = (HorizontalPanel) widget;
						FlexTable tableMulti = (FlexTable) hPanel.getWidget(0);
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
		
		ServiceDefTarget endPoint = (ServiceDefTarget) workflowService;
		endPoint.setServiceEntryPoint(RPCService.WorkflowService);		
		workflowService.setTaskInstanceValues(id, transitionName, formElementList, callbackSetTaskInstanceValues);
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
			endPoint.setServiceEntryPoint(RPCService.WorkflowService);
			workflowService.addComment(taskInstance.getProcessInstance().getRootToken().getId(), textArea.getText(), callbackAddComment);
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
	 * writePath
	 * 
	 * @param row
	 * @param docPath
	 * @param isFolder
	 */
	private void writePath(int row, final String docPath, final boolean isFolder) {
		Anchor link = new Anchor();
		link.setText(docPath);
		table.setWidget(10, 1, link);
		link.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (!isFolder) {
					String path = docPath.substring(0,docPath.lastIndexOf("/"));
					CommonUI.openAllFolderPath(path, docPath);
				} else {
					CommonUI.openAllFolderPath(docPath, "");
				}
			}
		});
		link.setStyleName("okm-Hyperlink");
		
		// Clones link
		documentLink = new Anchor();
		documentLink.setText(docPath);
		documentLink.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (!isFolder) {
					String path = docPath.substring(0,docPath.lastIndexOf("/"));
					CommonUI.openAllFolderPath(path, docPath);
				} else {
					CommonUI.openAllFolderPath(docPath, "");
				}
			}
		});
		documentLink.setStyleName("okm-Hyperlink");
		parameterTable.setWidget(row, 1, documentLink);
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
				zoomImage = new Image(OKMBundleResources.INSTANCE.zoomOut());
			} else {
				zoomImage = new Image(OKMBundleResources.INSTANCE.zoomIn());
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
				zoomImage.setResource(OKMBundleResources.INSTANCE.zoomOut());
			} else {
				zoomImage.setResource(OKMBundleResources.INSTANCE.zoomIn());
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