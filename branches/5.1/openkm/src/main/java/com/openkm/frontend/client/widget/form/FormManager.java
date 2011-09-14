/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

package com.openkm.frontend.client.widget.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FileUpload;
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
import com.openkm.frontend.client.bean.FileToUpload;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTKeyValue;
import com.openkm.frontend.client.bean.GWTPropertyParams;
import com.openkm.frontend.client.bean.GWTTaskInstance;
import com.openkm.frontend.client.bean.form.GWTButton;
import com.openkm.frontend.client.bean.form.GWTCheckBox;
import com.openkm.frontend.client.bean.form.GWTDownload;
import com.openkm.frontend.client.bean.form.GWTFormElement;
import com.openkm.frontend.client.bean.form.GWTInput;
import com.openkm.frontend.client.bean.form.GWTNode;
import com.openkm.frontend.client.bean.form.GWTOption;
import com.openkm.frontend.client.bean.form.GWTSelect;
import com.openkm.frontend.client.bean.form.GWTSeparator;
import com.openkm.frontend.client.bean.form.GWTSuggestBox;
import com.openkm.frontend.client.bean.form.GWTText;
import com.openkm.frontend.client.bean.form.GWTTextArea;
import com.openkm.frontend.client.bean.form.GWTUpload;
import com.openkm.frontend.client.bean.form.GWTValidator;
import com.openkm.frontend.client.contants.ui.UIFileUploadConstants;
import com.openkm.frontend.client.service.OKMDocumentService;
import com.openkm.frontend.client.service.OKMDocumentServiceAsync;
import com.openkm.frontend.client.service.OKMKeyValueService;
import com.openkm.frontend.client.service.OKMKeyValueServiceAsync;
import com.openkm.frontend.client.service.OKMRepositoryService;
import com.openkm.frontend.client.service.OKMRepositoryServiceAsync;
import com.openkm.frontend.client.util.CommonUI;
import com.openkm.frontend.client.util.ISO8601;
import com.openkm.frontend.client.util.MessageFormat;
import com.openkm.frontend.client.util.OKMBundleResources;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.util.validator.ValidatorBuilder;
import com.openkm.frontend.client.widget.searchin.CalendarWidget;
import com.openkm.frontend.client.widget.searchin.HasSearch;

import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.actions.FocusAction;

/**
 * FormManager
 * 
 * @author jllort
 *
 */
public class FormManager {
	private final OKMKeyValueServiceAsync keyValueService = (OKMKeyValueServiceAsync) GWT.create(OKMKeyValueService.class);
	private final OKMRepositoryServiceAsync repositoryService = (OKMRepositoryServiceAsync) GWT.create(OKMRepositoryService.class);
	private final OKMDocumentServiceAsync documentService = (OKMDocumentServiceAsync) GWT.create(OKMDocumentService.class);
	
	// Boolean contants
	private String BOOLEAN_TRUE = String.valueOf(Boolean.TRUE);
	
	private List<GWTFormElement> formElementList = new ArrayList<GWTFormElement>();
	public Map<String, GWTPropertyParams> hPropertyParams = new HashMap<String, GWTPropertyParams>();
	private Map<String, Widget> hWidgetProperties = new HashMap<String, Widget>();
	private FlexTable table;
	private FolderSelectPopup folderSelectPopup;
	private ValidationProcessor validationProcessor;
	private boolean drawed = false;
	private boolean readOnly = false;
	private GWTTaskInstance taskInstance;
	private Button submitForm;
	private HasWorkflow workflow;
	private HorizontalPanel submitButtonPanel;
	private boolean isSearchView = false;
	private HasSearch search;
	
	/**
	 * FormManager used in workflow mode
	 */
	public FormManager(HasWorkflow workflow) {
		this.workflow = workflow;
		init();
	}
	
	/**
	 * FormManager used in search mode
	 */
	public FormManager(HasSearch search) {
		this.search = search;
		isSearchView = true;
		init();
	}
	
	/**
	 * FormManager used in property group mode
	 */
	public FormManager() {
		init();
	}
	
	/**
	 * init
	 */
	private void init() {
		table = new FlexTable();
		table.setWidth("100%");
		table.setStyleName("okm-NoWrap");
		folderSelectPopup = new FolderSelectPopup();
		folderSelectPopup.setStyleName("okm-Popup");
		folderSelectPopup.addStyleName("okm-DisableSelect");
		submitButtonPanel = new HorizontalPanel();
	}

	/**
	 * getTable
	 * 
	 * @return
	 */
	public FlexTable getTable() {
		return table;
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
			table.getCellFormatter().setWordWrap(row, i, false);
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
	 * drawFormElement
	 * 
	 * @param row
	 * @param gwtMetadata
	 */
	private void drawFormElement(int row, final GWTFormElement gwtMetadata, boolean readOnly, boolean searchView) {
		final String propertyName = gwtMetadata.getName();
		
		if (gwtMetadata instanceof GWTButton) {
			final GWTButton gWTButton = (GWTButton) gwtMetadata;
			if (submitForm!=null) {
				submitForm.setVisible(false); // Always set form hidden because there's new buttons
			}
			
			Button transButton = new Button(gWTButton.getLabel());
			transButton.setStyleName("okm-Button");
			HTML space = new HTML("&nbsp;");
			submitButtonPanel.add(transButton);
			submitButtonPanel.add(space);
			submitButtonPanel.setCellWidth(space, "5px");
			
			// Setting submit button
			transButton.addClickHandler(new ClickHandler() { 
				@Override
				public void onClick(ClickEvent event) {
					if (validationProcessor.validate()) {
						if (gWTButton.getTransition().equals("")) {
							workflow.setTaskInstanceValues(taskInstance.getId(), null);
						} else {
							workflow.setTaskInstanceValues(taskInstance.getId(), gWTButton.getTransition());
						}
					}
				}
			});
			
		} else if (gwtMetadata instanceof GWTTextArea) {
			HorizontalPanel hPanel = new HorizontalPanel();
			TextArea textArea = new TextArea();
			textArea.setEnabled((!readOnly && !((GWTTextArea) gwtMetadata).isReadonly()) || isSearchView);
			hPanel.add(textArea);
			textArea.setStyleName("okm-TextArea");
			textArea.setText(((GWTTextArea) gwtMetadata).getValue());
			textArea.setSize(gwtMetadata.getWidth(), gwtMetadata.getHeight());
			HTML text = new HTML(); // Create a widget for this property
			text.setHTML(((GWTTextArea) gwtMetadata).getValue().replaceAll("\n", "<br>"));
			hWidgetProperties.put(propertyName,hPanel);
			table.setHTML(row, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
			table.setWidget(row, 1, text);
			table.getCellFormatter().setVerticalAlignment(row,0,VerticalPanel.ALIGN_TOP);
			table.getCellFormatter().setWidth(row, 1, "100%");		
			if (searchView) {
				final Image removeImage = new Image(OKMBundleResources.INSTANCE.deleteIcon());
				removeImage.addClickHandler(new ClickHandler() { 
					@Override
					public void onClick(ClickEvent event) {
						for (int row=0; row<table.getRowCount(); row++) {
							if (table.getWidget(row, 2).equals(removeImage)) {
								table.removeRow(row);
								break;
							}
						}
						hWidgetProperties.remove(propertyName);
						hPropertyParams.remove(propertyName);
						formElementList.remove(gwtMetadata);
						search.propertyRemoved();
					}
				});
				removeImage.addStyleName("okm-Hyperlink");
				table.setWidget(row, 2, removeImage);
				table.getCellFormatter().setVerticalAlignment(row, 2, HasAlignment.ALIGN_TOP);
				if (search!=null) {
					textArea.addKeyUpHandler(new KeyUpHandler() {
						@Override
						public void onKeyUp(KeyUpEvent event) {
							search.metadataValueChanged();
						}
					});
				}
				setRowWordWarp(row, 3, true);
			} else {
				setRowWordWarp(row, 2, true);
			}
			
		} else if (gwtMetadata instanceof GWTInput) {
			HorizontalPanel hPanel = new HorizontalPanel();
			final TextBox textBox = new TextBox(); // Create a widget for this property
			textBox.setEnabled((!readOnly && !((GWTInput) gwtMetadata).isReadonly()) || isSearchView);
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
						if (search!=null) {
							search.metadataValueChanged();
						}
					}
				});
				calendarPopup.add(calendar);
				final Image calendarIcon = new Image(OKMBundleResources.INSTANCE.calendar());
				if (readOnly || ((GWTInput) gwtMetadata).isReadonly()) {
					calendarIcon.setResource(OKMBundleResources.INSTANCE.calendarDisabled());
				} else {
					calendarIcon.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							calendarPopup.setPopupPosition(calendarIcon.getAbsoluteLeft(), calendarIcon.getAbsoluteTop()-2);
							calendarPopup.show();
						}
					});
				}
				calendarIcon.setStyleName("okm-Hyperlink");
				hPanel.add(Util.hSpace("5"));
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
					anchor.setStyleName("okm-Hyperlink");
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
						folderSelectPopup.show(textBox, search); // when any changes is done is fired search.metadataValueChanged();
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
			
			if (searchView) {
				// Second date input
				if (((GWTInput) gwtMetadata).getType().equals(GWTInput.TYPE_DATE)) {
					final TextBox textBoxTo = new TextBox();
					textBoxTo.setWidth(gwtMetadata.getWidth());
					textBoxTo.setStyleName("okm-Input");
					hPanel.add(new HTML("&nbsp;&harr;&nbsp;"));
					hPanel.add(textBoxTo);
					
					if (((GWTInput) gwtMetadata).getDateTo() != null) {
						DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.day.pattern"));
						textBoxTo.setText(dtf.format(((GWTInput) gwtMetadata).getDateTo()));
					}
					
					final PopupPanel calendarPopup = new PopupPanel(true);
					final CalendarWidget calendar = new CalendarWidget();
					calendar.addChangeHandler(new ChangeHandler() {
						@Override
						public void onChange(ChangeEvent event) {
							calendarPopup.hide();
							DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.day.pattern"));
							textBoxTo.setText(dtf.format(calendar.getDate()));
							((GWTInput) gwtMetadata).setDateTo(calendar.getDate());
							if (search!=null) {
								search.metadataValueChanged();
							}
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
					
					calendarIcon.setStyleName("okm-Hyperlink");
					hPanel.add(Util.hSpace("5"));
					hPanel.add(calendarIcon);
					textBoxTo.setEnabled(false);
				}
				
				// Delete
				final Image removeImage = new Image(OKMBundleResources.INSTANCE.deleteIcon());
				removeImage.addClickHandler(new ClickHandler() { 
					@Override
					public void onClick(ClickEvent event) {
						for (int row=0; row<table.getRowCount(); row++) {
							if (table.getWidget(row, 2).equals(removeImage)) {
								table.removeRow(row);
								break;
							}
						}
						
						hWidgetProperties.remove(propertyName);
						hPropertyParams.remove(propertyName);
						formElementList.remove(gwtMetadata);
						search.propertyRemoved();
					}
				});
				
				removeImage.addStyleName("okm-Hyperlink");
				table.setWidget(row, 2, removeImage);
				table.getCellFormatter().setVerticalAlignment(row, 2, HasAlignment.ALIGN_TOP);
				
				if (search != null) {
					textBox.addKeyUpHandler(new KeyUpHandler() {
						@Override
						public void onKeyUp(KeyUpEvent event) {
							search.metadataValueChanged();
						}
					});
				}
				setRowWordWarp(row, 3, true);
			} else {
				setRowWordWarp(row, 2, true);
			}
				
		} else if(gwtMetadata instanceof GWTSuggestBox) {
			HorizontalPanel hPanel = new HorizontalPanel();
			final GWTSuggestBox suggestBox = (GWTSuggestBox) gwtMetadata;
			final TextBox textBox = new TextBox(); // Create a widget for this property
			textBox.setWidth(gwtMetadata.getWidth());
			textBox.setStyleName("okm-Input");
			textBox.setReadOnly(true);
			textBox.setEnabled((!readOnly && !suggestBox.isReadonly()) || isSearchView);
			final HTML hiddenKey = new HTML("");
			hiddenKey.setVisible(false);
			
			if (suggestBox.getValue()!=null) {
				hiddenKey.setHTML(suggestBox.getValue());
			}
			
			hPanel.add(textBox);
			hPanel.add(hiddenKey);
			final HTML value = new HTML("");
			table.setHTML(row, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
			table.setWidget(row, 1, value);
			
			if (textBox.isEnabled()) {
				final Image databaseRecordImage = new Image(OKMBundleResources.INSTANCE.databaseRecord());
				databaseRecordImage.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						List<String> tables = new ArrayList<String>();
						if (suggestBox.getTable()!=null) {
							tables.add(suggestBox.getTable());
						}
						
						DatabaseRecord databaseRecord = new DatabaseRecord(hiddenKey, textBox);
						// when any changes is done is fired search.metadataValueChanged();
						DatabaseRecordSelectPopup drsPopup = new DatabaseRecordSelectPopup(suggestBox.getDialogTitle(),
																						   tables, suggestBox.getFilterQuery(), 
																						   databaseRecord, search,
																						   suggestBox.getFilterMinLen());
						drsPopup.setWidth("300");
						drsPopup.setHeight("220");
						drsPopup.setStyleName("okm-Popup");
						drsPopup.setPopupPosition(databaseRecordImage.getAbsoluteLeft(), databaseRecordImage.getAbsoluteTop()-2);
						drsPopup.show();
					}
				});
				
				databaseRecordImage.setStyleName("okm-Hyperlink");
				hPanel.add(new HTML("&nbsp;"));
				hPanel.add(databaseRecordImage);
			}		
			
			hWidgetProperties.put(propertyName, hPanel);
			if (!suggestBox.getValue().equals("")) {
				List<String> tables = new ArrayList<String>();
				
				if (suggestBox.getTable()!=null) {
					tables.add(suggestBox.getTable());
				}
				
				String formatedQuery = MessageFormat.format(suggestBox.getValueQuery(), suggestBox.getValue());
				keyValueService.getKeyValues(tables, formatedQuery, new AsyncCallback<List<GWTKeyValue>>() {
					@Override
					public void onSuccess(List<GWTKeyValue> result) {
						if (!result.isEmpty()) {
							GWTKeyValue keyValue = result.get(0);
							textBox.setValue(keyValue.getValue());
							value.setHTML(keyValue.getValue());
							hiddenKey.setHTML(keyValue.getKey());
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Main.get().showError("getKeyValues", caught);
					}
				});
			}
			
			if (searchView) {
				final Image removeImage = new Image(OKMBundleResources.INSTANCE.deleteIcon());
				removeImage.addClickHandler(new ClickHandler() { 
					@Override
					public void onClick(ClickEvent event) {
						for (int row=0; row<table.getRowCount(); row++) {
							if (table.getWidget(row, 2).equals(removeImage)) {
								table.removeRow(row);
								break;
							}
						}
						
						hWidgetProperties.remove(propertyName);
						hPropertyParams.remove(propertyName);
						formElementList.remove(gwtMetadata);
						search.propertyRemoved();
					}
				});
				
				removeImage.addStyleName("okm-Hyperlink");
				table.setWidget(row, 2, removeImage);
				table.getCellFormatter().setVerticalAlignment(row, 2, HasAlignment.ALIGN_TOP);
				textBox.addKeyUpHandler(Main.get().mainPanel.search.searchBrowser.searchIn.searchControl.keyUpHandler);
				setRowWordWarp(row, 3, true);
			} else {
				setRowWordWarp(row, 2, true);
			}
		} else if (gwtMetadata instanceof GWTCheckBox) {
			CheckBox checkBox = new CheckBox();
			checkBox.setEnabled((!readOnly && !((GWTCheckBox) gwtMetadata).isReadonly()) || isSearchView);
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
			
			if (searchView) {
				final Image removeImage = new Image(OKMBundleResources.INSTANCE.deleteIcon());
				removeImage.addClickHandler(new ClickHandler() { 
					@Override
					public void onClick(ClickEvent event) {
						for (int row=0; row<table.getRowCount(); row++) {
							if (table.getWidget(row, 2).equals(removeImage)) {
								table.removeRow(row);
								break;
							}
						}
						
						hWidgetProperties.remove(propertyName);
						hPropertyParams.remove(propertyName);
						formElementList.remove(gwtMetadata);
						search.propertyRemoved();
					}
				});
				
				removeImage.addStyleName("okm-Hyperlink");
				table.setWidget(row, 2, removeImage);
				table.getCellFormatter().setVerticalAlignment(row, 2, HasAlignment.ALIGN_TOP);
				
				if (search != null) {
					checkBox.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							search.metadataValueChanged();
						}
					});
				}
				
				setRowWordWarp(row, 3, true);
			} else {
				setRowWordWarp(row, 2, true);
			}
		} else if (gwtMetadata instanceof GWTSelect) {
			final GWTSelect gwtSelect = (GWTSelect) gwtMetadata;
			if (gwtSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
				String selectedLabel = "";
				HorizontalPanel hPanel = new HorizontalPanel();
				ListBox listBox = new ListBox();
				listBox.setEnabled((!readOnly && !gwtSelect.isReadonly()) || isSearchView);
				hPanel.add(listBox);
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
				
				hWidgetProperties.put(propertyName,hPanel);
				
				table.setHTML(row, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
				table.setHTML(row, 1, selectedLabel);
				table.getCellFormatter().setWidth(row, 1, "100%");
				
				if (searchView) {
					final Image removeImage = new Image(OKMBundleResources.INSTANCE.deleteIcon());
					removeImage.addClickHandler(new ClickHandler() { 
						@Override
						public void onClick(ClickEvent event) {
							for (int row=0; row<table.getRowCount(); row++) {
								if (table.getWidget(row, 2).equals(removeImage)) {
									table.removeRow(row);
									break;
								}
							}
							
							hWidgetProperties.remove(propertyName);
							hPropertyParams.remove(propertyName);
							formElementList.remove(gwtMetadata);
							search.propertyRemoved();
						}
					});
					
					removeImage.addStyleName("okm-Hyperlink");
					table.setWidget(row, 2, removeImage);
					table.getCellFormatter().setVerticalAlignment(row, 2, HasAlignment.ALIGN_TOP);
					
					if (search != null) {
						listBox.addChangeHandler(new ChangeHandler() {
							@Override
							public void onChange(ChangeEvent event) {
								search.metadataValueChanged();
							}
						});
					}
					
					setRowWordWarp(row, 3, true);
				} else {
					setRowWordWarp(row, 2, true);
				}
				
			} else if (gwtSelect.getType().equals(GWTSelect.TYPE_MULTIPLE)) {
				final HorizontalPanel hPanel = new HorizontalPanel();
				ListBox listMulti = new ListBox();
				listMulti.setEnabled((!readOnly && !gwtSelect.isReadonly()) || isSearchView);
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
									
									if (search!=null) {
										search.metadataValueChanged();
									}
								}
							});
							
							tableMulti.setWidget(rowTableMulti,0,htmlValue);
							tableMulti.setWidget(rowTableMulti,1,removeImage);
							tableMulti.setHTML(rowTableMulti,2, listMulti.getItemText(listMulti.getSelectedIndex()));
							
							setRowWordWarp(tableMulti,rowTableMulti, 2, true);
							listMulti.removeItem(listMulti.getSelectedIndex());
							htmlValue.setVisible(false);
							
							if (listMulti.getItemCount() <= 1) {
								listMulti.setVisible(false);
								addButton.setVisible(false);
							}
							
							if (search!=null) {
								search.metadataValueChanged();
							}
						}
					}
				});
				addButton.setEnabled((!readOnly && !gwtSelect.isReadonly()) || isSearchView);
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
				
				for (Iterator<GWTOption> itData = gwtSelect.getOptions().iterator(); itData.hasNext(); ) {
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
								for (int i=0; i<tableMulti.getRowCount(); i++) {
									if (tableMulti.getWidget(i,1).equals(sender)) {
										tableMulti.removeRow(i);
									}
								}
								
								if (search != null) {
									search.metadataValueChanged();
								}
							}
						});
						removeImage.setStyleName("okm-KeyMap-ImageHover");
						
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
				
				if (searchView) {
					final Image removeImage = new Image(OKMBundleResources.INSTANCE.deleteIcon());
					removeImage.addClickHandler(new ClickHandler() { 
						@Override
						public void onClick(ClickEvent event) {
							for (int row=0; row<table.getRowCount(); row++) {
								if (table.getWidget(row, 2).equals(removeImage)) {
									table.removeRow(row);
									break;
								}
							}
							
							hWidgetProperties.remove(propertyName);
							hPropertyParams.remove(propertyName);
							formElementList.remove(gwtMetadata);
							search.propertyRemoved();
						}
					});
					
					removeImage.addStyleName("okm-Hyperlink");
					table.setWidget(row, 2, removeImage);
					table.getCellFormatter().setVerticalAlignment(row, 2, HasAlignment.ALIGN_TOP);
					//not implemented
					//textBox.addKeyUpHandler(Main.get().mainPanel.search.searchBrowser.searchIn.searchControl.keyUpHandler);
					setRowWordWarp(row, 3, true);
				} else {
					setRowWordWarp(row, 2, true);
				}
			}
		} else if (gwtMetadata instanceof GWTUpload) {
			final GWTUpload upload = (GWTUpload) gwtMetadata;
			HorizontalPanel hPanel = new HorizontalPanel();
			FileUpload fileUpload = new FileUpload();
			fileUpload.setStyleName("okm-Input");
			fileUpload.getElement().setAttribute("size", ""+upload.getWidth());
			final Anchor documentLink = new Anchor();
			// Setting document link by uuid
			if (upload.getDocumentUuid()!=null && !upload.getDocumentUuid().equals("")) {
				repositoryService.getPathByUUID(upload.getDocumentUuid(), new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {
						documentService.get(result, new AsyncCallback<GWTDocument>() {
							@Override
							public void onSuccess(GWTDocument result) {
								final String docPath = result.getPath();
								documentLink.setText(result.getName());
								documentLink.addClickHandler(new ClickHandler() { 
									@Override
									public void onClick(ClickEvent event) {
										String path = docPath.substring(0,docPath.lastIndexOf("/"));
										CommonUI.openAllFolderPath(path, docPath);
									}
								});
							}
							@Override
							public void onFailure(Throwable caught) {
								Main.get().showError("get", caught);
							}
						});
					}
					@Override
					public void onFailure(Throwable caught) {
						Main.get().showError("getPathByUUID", caught);
					}
				});
			} 
			documentLink.setStyleName("okm-Hyperlink");
			hPanel.add(documentLink);
			hPanel.add(fileUpload);
			hWidgetProperties.put(propertyName,hPanel);
			table.setHTML(row, 0, "<b>" + gwtMetadata.getLabel() + "</b>");
			table.setWidget(row, 1, new HTML(""));
			table.getCellFormatter().setVerticalAlignment(row,0,VerticalPanel.ALIGN_TOP);
			table.getCellFormatter().setWidth(row, 1, "100%");		
			setRowWordWarp(row, 2, true);
			
			// If folderPath is null must initialize value
			if (upload.getFolderPath()==null || upload.getFolderPath().equals("") && 
				upload.getFolderUuid()!=null && !upload.getFolderUuid().equals("")) {
				repositoryService.getPathByUUID(upload.getFolderUuid(), new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {
						upload.setFolderPath(result);
					}
					@Override
					public void onFailure(Throwable caught) {
						Main.get().showError("getPathByUUID", caught);
					}
				});
			}
		} else if (gwtMetadata instanceof GWTText) {
			HorizontalPanel hPanel = new HorizontalPanel();
			HTML tittle = new HTML("&nbsp;"+"<b>"+((GWTText)gwtMetadata).getLabel()+"</b>"+"&nbsp;");
			tittle.setStyleName("okm-NoWrap");
			hPanel.add(Util.hSpace("10"));			
			hPanel.add(tittle);
			hPanel.setCellWidth(tittle, ((GWTText)gwtMetadata).getWidth());
			hWidgetProperties.put(propertyName,hPanel);
			table.setWidget(row, 0, hPanel);
			table.getFlexCellFormatter().setColSpan(row, 0, 2);
		} else if (gwtMetadata instanceof GWTSeparator) {
			HorizontalPanel hPanel = new HorizontalPanel();
			Image horizontalLine = new Image("img/transparent_pixel.gif");
			horizontalLine.setStyleName("okm-TopPanel-Line-Border");
			horizontalLine.setSize("10", "2px");
			Image horizontalLine2 = new Image("img/transparent_pixel.gif");
			horizontalLine2.setStyleName("okm-TopPanel-Line-Border");
			horizontalLine2.setSize("100%", "2px");
			HTML tittle = new HTML("&nbsp;"+"<b>"+((GWTSeparator)gwtMetadata).getLabel()+"</b>"+"&nbsp;");
			tittle.setStyleName("okm-NoWrap");
			hPanel.add(horizontalLine);			
			hPanel.add(tittle);
			hPanel.add(horizontalLine2);
			hPanel.setCellVerticalAlignment(horizontalLine, HasAlignment.ALIGN_MIDDLE);
			hPanel.setCellVerticalAlignment(horizontalLine2, HasAlignment.ALIGN_MIDDLE);
			hPanel.setCellWidth(horizontalLine2, ((GWTSeparator)gwtMetadata).getWidth());
			hWidgetProperties.put(propertyName,hPanel);
			table.setWidget(row, 0, hPanel);
			table.getFlexCellFormatter().setColSpan(row, 0, 2);
		} else if (gwtMetadata instanceof GWTDownload) {
			HorizontalPanel hPanel = new HorizontalPanel();
			hWidgetProperties.put(propertyName, hPanel);
			table.setWidget(row, 0, hPanel);
			table.getFlexCellFormatter().setColSpan(row, 0, 2);
			GWTDownload download = (GWTDownload) gwtMetadata;
			FlexTable downloadTable = new FlexTable();
			for (final GWTNode node : download.getNodes()) {
				int downloadTableRow = downloadTable.getRowCount();
				Anchor anchor = new Anchor("<b>" + node.getLabel() + "</b>", true);
				anchor.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						if (!node.getUuid().equals("")) {
							Util.downloadFileByUUID(node.getUuid(), "");
						} else if (!node.getPath().equals("")) {
							Util.downloadFile(node.getPath(), "");
						}
					}
				});
				anchor.setStyleName("okm-Hyperlink");
				downloadTable.setWidget(downloadTableRow, 0, anchor);
			}
			hPanel.add(new HTML("<b>" + gwtMetadata.getLabel() + "</b>"));
			hPanel.add(downloadTable);
		}
	}
	
	/**
	 * Edit values
	 */
	public void edit() {
		// Before edit must be always drawed
		if (!drawed) {
			draw(readOnly);
		}
		
		int rows = 0;
		validationProcessor = new DefaultValidationProcessor();
		FocusAction focusAction = new FocusAction();

		for (Iterator<GWTFormElement> it = formElementList.iterator(); it.hasNext();) {
			GWTFormElement formField = it.next();
			
			if (formField instanceof GWTTextArea) {
				HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formField.getName());
				table.setWidget(rows, 1, hPanel);
				
				for (GWTValidator validator : ((GWTTextArea) formField).getValidators()) {
					TextArea textArea = (TextArea) hPanel.getWidget(0);
					ValidatorBuilder.addValidator(validationProcessor, focusAction, hPanel, "textarea_"+rows, validator, textArea);
				}
			} else if (formField instanceof GWTInput) {
				HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formField.getName());
				table.setWidget(rows, 1, hPanel);
				
				for (GWTValidator validator : ((GWTInput) formField).getValidators()) {
					TextBox textBox = (TextBox) hPanel.getWidget(0);
					ValidatorBuilder.addValidator(validationProcessor, focusAction, hPanel, "input_"+rows, validator, textBox);
				}
			} else if (formField instanceof GWTSuggestBox) {
				HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formField.getName());
				table.setWidget(rows, 1, hPanel);
				
				for (GWTValidator validator : ((GWTSuggestBox) formField).getValidators()) {
					TextBox textBox = (TextBox) hPanel.getWidget(0);
					ValidatorBuilder.addValidator(validationProcessor, focusAction, hPanel, "suggestbox_"+rows, validator, textBox);
				}
			} else if (formField instanceof GWTCheckBox) {
				CheckBox checkBox = (CheckBox) hWidgetProperties.get(formField.getName());
				table.setWidget(rows, 1, checkBox);
			} else if (formField instanceof GWTSelect) {
				GWTSelect gwtSelect = (GWTSelect) formField;
				if (gwtSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
					HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formField.getName());
					ListBox listBox = (ListBox) hPanel.getWidget(0);
					table.setWidget(rows, 1, hPanel);
					
					for (GWTValidator validator : ((GWTSelect) formField).getValidators()) {
						ValidatorBuilder.addValidator(validationProcessor, focusAction, hPanel, "select_"+rows, validator, listBox);
					}
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
					
					for (GWTValidator validator : ((GWTSelect) formField).getValidators()) {
						ValidatorBuilder.addValidator(validationProcessor, focusAction, hPanel, "select_"+rows, validator, tableMulti);
					}
				}
			} else if (formField instanceof GWTUpload) {
				HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formField.getName());
				table.setWidget(rows, 1, hPanel);
				
				for (GWTValidator validator : ((GWTUpload) formField).getValidators()) {
					FileUpload fileUpload = (FileUpload) hPanel.getWidget(1);
					ValidatorBuilder.addValidator(validationProcessor, focusAction, hPanel, "fileupload_"+rows, validator, fileUpload);
				}
			} else if (formField instanceof GWTText) {
				// Nothing to be done here
			} else if (formField instanceof GWTSeparator) {
				// Nothing to be done here
			} else if (formField instanceof GWTDownload) {
				// Nothing to be done here
			}
			rows++;
		}
		
		// Always ad submit form at ends
		if (submitForm != null) {
			HTML space = new HTML("&nbsp;");
			submitButtonPanel.add(submitForm);
			submitButtonPanel.add(space);
			submitButtonPanel.setCellWidth(space, "5px");
			int row = table.getRowCount();
			table.setWidget(row, 0, submitButtonPanel);
			table.getFlexCellFormatter().setColSpan(row, 0, 2);
			table.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasAlignment.ALIGN_CENTER);
		}
	}
	
	/**
	 * setFormElements
	 * 
	 * @param formElementList
	 */
	public void setFormElements(List<GWTFormElement> formElementList) {
		drawed = false;
		hWidgetProperties.clear();
		hPropertyParams.clear();
		this.formElementList = formElementList;
	}
	
	/**
	 * addPropertyParam
	 * 
	 * @param propertyParam
	 */
	public void addPropertyParam(GWTPropertyParams propertyParam) {
		updateFormElementsValuesWithNewer(); // save values
		drawed = false;
		
		if (!hWidgetProperties.containsKey(propertyParam.getFormElement().getName())) {
			hPropertyParams.put(propertyParam.getFormElement().getName(), propertyParam);
			formElementList.add(propertyParam.getFormElement());
			GWTFormElement formElement = propertyParam.getFormElement();
			
			if (propertyParam.getValue() != null) {
				if (formElement instanceof GWTInput) {
					GWTInput input = (GWTInput) formElement;
					if (((GWTInput) formElement).getType().equals(GWTInput.TYPE_DATE)) {
						if (!propertyParam.getValue().equals("")) {
							String date[] = propertyParam.getValue().split(",");
							input.setDate(ISO8601.parse(date[0]));
							if (date.length==2) {
								input.setDateTo(ISO8601.parse(date[1]));
							}
						}
					} else {
						input.setValue(propertyParam.getValue());
					}
				} else if (formElement instanceof GWTTextArea) {
					((GWTTextArea) formElement).setValue(propertyParam.getValue());
				} else if (formElement instanceof GWTSuggestBox) {
					((GWTSuggestBox) formElement).setValue(propertyParam.getValue());
				} else if (formElement instanceof GWTCheckBox) {
					((GWTCheckBox) formElement).setValue(Boolean.parseBoolean(propertyParam.getValue()));
				} else if (formElement instanceof GWTSelect) {
					String value[] = propertyParam.getValue().split(",");
					GWTSelect select = (GWTSelect) formElement;
					
					for (GWTOption option : select.getOptions()) {
						for (int i=0; i<value.length; i++) {
							if (option.getValue().equals(value[i])) {
								option.setSelected(true);
							} else {
								option.setSelected(false);
							}
						}
					}
				} else if (formElement instanceof GWTUpload) {
					// Not aplicable to property groups
				} else if (formElement instanceof GWTText) {
					((GWTText) formElement).setLabel(propertyParam.getValue());
				} else if (formElement instanceof GWTSeparator) {
					// Nothing to be done here
				} else if (formElement instanceof GWTDownload) {
					// Nothing to be done here
				}
			}
		}
	}
	
	/**
	 * draw
	 * 
	 */
	public void draw() {
		draw(false);
	}
	
	/**
	 * draw
	 * 
	 * @param readOnly
	 */
	public void draw(boolean readOnly) {
		this.readOnly = readOnly;
		table.removeAllRows();
		submitButtonPanel.clear();
		int rows = 0;
		
		for (GWTFormElement formElement : formElementList) {
			drawFormElement(rows, formElement, readOnly, isSearchView);
			rows ++;
		}
		
		drawed = true;
	}
	
	/**
	 * updateFormElements
	 * 
	 * @return
	 */
	public List<GWTFormElement> getFormElements() {
		return formElementList;
	}
	
	/**
	 * getPropertyParams
	 * 
	 * @return
	 */
	public Map<String, GWTPropertyParams> getPropertyParams() {
		for (GWTFormElement formElement : updateFormElementsValuesWithNewer()) {
			String value = "";
			if (formElement instanceof GWTInput) {
				if (((GWTInput) formElement).getType().equals(GWTInput.TYPE_DATE)) {
					GWTInput input = (GWTInput) formElement;
					value = ISO8601.format(input.getDate());
					if (input.getDateTo()!=null) {
						value += ","+ ISO8601.format(input.getDateTo());
					} else {
						value += "," + value;
					}
				} else {
					value = ((GWTInput) formElement).getValue();
				}
			} else if (formElement instanceof GWTTextArea) {
				value = ((GWTTextArea) formElement).getValue();
			} else if (formElement instanceof GWTSuggestBox) {
				value = ((GWTSuggestBox) formElement).getValue();
			} else if (formElement instanceof GWTCheckBox) {
				value = String.valueOf(((GWTCheckBox) formElement).getValue());
			} else if (formElement instanceof GWTSelect) {
				GWTSelect select = (GWTSelect) formElement;
				for (GWTOption option : select.getOptions()) {
					if (option.isSelected()) {
						if (!value.equals("")) {
							value += ",";
						}
						value += option.getValue();
					}
				}
			} else if (formElement instanceof GWTUpload) {
				// Not aplicable to property groups
			} else if (formElement instanceof GWTText) {
				// Nothing to be done here
			} else if (formElement instanceof GWTSeparator) {
				// Nothing to be done here
			} else if (formElement instanceof GWTDownload) {
				// Nothing to be done here
			}
			
			hPropertyParams.get(formElement.getName()).setValue(value);
		}
		return hPropertyParams;
	}
	
	/**
	 * updateFormElementsWithNewer
	 * 
	 * @return
	 */
	public List<GWTFormElement> updateFormElementsValuesWithNewer() {
		int rows = 0;
		
		for (GWTFormElement formElement : formElementList) {
			if (formElement instanceof GWTTextArea) {
				HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formElement.getName());
				TextArea textArea = (TextArea) hPanel.getWidget(0);
				((GWTTextArea) formElement).setValue(textArea.getText());
			} else if (formElement instanceof GWTInput) {
				HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formElement.getName());
				TextBox textBox = (TextBox) hPanel.getWidget(0);
				((GWTInput) formElement).setValue(textBox.getText()); // note that date is added by click handler in drawform method
				if (((GWTInput) formElement).getType().equals(GWTInput.TYPE_FOLDER)) {
					// Must be updated folder in GWTInput because must be drawed
					GWTFolder folder = new GWTFolder();
					folder.setPath(textBox.getText());
					((GWTInput) formElement).setFolder(folder);
				}
			} else if (formElement instanceof GWTSuggestBox) {
				HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formElement.getName());
				HTML hiddenKey = (HTML) hPanel.getWidget(1);
				((GWTSuggestBox) formElement).setValue(hiddenKey.getHTML()); 
			} else if (formElement instanceof GWTCheckBox) {	
				CheckBox checkbox = (CheckBox) hWidgetProperties.get(formElement.getName());
				((GWTCheckBox) formElement).setValue(checkbox.getValue());
			} else if (formElement instanceof GWTSelect) {
				GWTSelect gwtSelect = (GWTSelect) formElement;
				if (gwtSelect.getType().equals(GWTSelect.TYPE_SIMPLE)) {
					HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formElement.getName());
					ListBox listBox = (ListBox) hPanel.getWidget(0);
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
			} else if (formElement instanceof GWTUpload) {
				// Nothing to be done here, upload files are updated in file upload widget
			} else if (formElement instanceof GWTText) {
				// Nothing to be done here
			} else if (formElement instanceof GWTSeparator) {
				// Nothing to be done here
			} else if (formElement instanceof GWTDownload) {
				// Nothing to be done here
			}
			
			rows ++;
		}
		
		return formElementList;
	}
	
	/**
	 * hasFileUploadFormElement
	 * 
	 * @return
	 */
	public boolean hasFileUploadFormElement() {
		boolean found = false;
		int rows = 0;
		for (GWTFormElement formElement : formElementList) {
			if (formElement instanceof GWTUpload) {
				HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formElement.getName());
				FileUpload fileUpload = (FileUpload) hPanel.getWidget(1);
				if (!fileUpload.getFilename().equals("")) {
					found  = true;
				}
				break;
			}
			rows++;
		}
		return found;
	}
	
	/**
	 * getFilesToUpload
	 * 
	 * @return
	 */
	public Collection<FileToUpload> getFilesToUpload(String transition) {
		List<FileToUpload> filesToUpload= new ArrayList<FileToUpload>();
		int rows = 0;
		for (GWTFormElement formElement : formElementList) {
			if (formElement instanceof GWTUpload) {
				HorizontalPanel hPanel = (HorizontalPanel) hWidgetProperties.get(formElement.getName());
				table.setWidget(rows, 1, hPanel);
				FileUpload fileUpload = (FileUpload) hPanel.getWidget(1);
				if (!fileUpload.getFilename().equals("")) {
					hPanel.remove(fileUpload);
					hPanel.add(new HTML(fileUpload.getFilename())); // replace uploadfile widget to text file
					FileToUpload fileToUpload = new FileToUpload();
					GWTUpload upload = (GWTUpload) formElement;
					if (upload.getType().equals(GWTUpload.TYPE_CREATE)) {
						fileToUpload.setAction(UIFileUploadConstants.ACTION_INSERT);
					} else if (upload.getType().equals(GWTUpload.TYPE_UPDATE)) {
						fileToUpload.setAction(UIFileUploadConstants.ACTION_UPDATE);
					} 
					fileToUpload.setName(formElement.getName());
					fileToUpload.setFileUpload(fileUpload);
					fileToUpload.setSize(upload.getWidth());
					fileToUpload.setFireEvent(false);
					fileToUpload.setPath(upload.getFolderPath());
					fileToUpload.setDesiredDocumentName(upload.getDocumentName());
					fileToUpload.setWorkflow(workflow);
					fileToUpload.setLastToBeUploaded(false);
					fileToUpload.setEnableAddButton(false);
					fileToUpload.setEnableImport(false);
					fileToUpload.setWorkflowTaskId(taskInstance.getId());
					fileToUpload.setWorkflowTransition(transition);
					filesToUpload.add(fileToUpload);
				}
			}
			rows++;
		}
		// Indicates is the last file to be upload in the cycle
		if (filesToUpload.size()>0) {
			filesToUpload.get(filesToUpload.size()-1).setLastToBeUploaded(true);
		}
		return filesToUpload;
	}

	
	/**
	 * updateFilesToUpload
	 * 
	 * @param filesToUpload
	 */
	public void updateFilesToUpload(Collection<FileToUpload> filesToUpload) {
		for (FileToUpload fileToUpload : filesToUpload) {
			for (GWTFormElement formElement : formElementList) {
				if (formElement.getName().equals(fileToUpload.getName())) {
					GWTUpload upload = (GWTUpload) formElement;
					upload.setDocumentUuid(fileToUpload.getDocumentUUID());
				}
			}
		}
	}
	
	/**
	 * loadDataFromPropertyGroupVariables
	 * 
	 * @param map
	 */
	public void loadDataFromPropertyGroupVariables(Map<String, GWTFormElement> map) {
		// Only iterate if really there's some variable to be mapped 
		if (!map.isEmpty()) {
			for (GWTFormElement formElement : formElementList) {
				if (map.containsKey(formElement.getName())) {
					if (formElement instanceof GWTTextArea) {
						GWTTextArea textArea = (GWTTextArea) formElement;
						textArea.setValue(getStringValueFromVariable(map.get(formElement.getName())));
					} else if (formElement instanceof GWTInput) {
						GWTInput input = (GWTInput) formElement;
						input.setValue(getStringValueFromVariable(map.get(formElement.getName())));
					} else if (formElement instanceof GWTSuggestBox) {
						GWTSuggestBox suggestBox = (GWTSuggestBox) formElement;
						suggestBox.setValue(getStringValueFromVariable(map.get(formElement.getName())));
					} else if (formElement instanceof GWTCheckBox) {
						GWTCheckBox checkBox = (GWTCheckBox) formElement;
						checkBox.setValue(getBooleanValueFromVariable(map.get(formElement.getName())));
					} else if (formElement instanceof GWTSelect) {
						GWTSelect select = (GWTSelect) formElement;
						select.setOptions(getOptionsValueFromVariable(formElement.getName(), select.getOptions())) ;
					} else if (formElement instanceof GWTUpload) {
						// No aplicable to property groups
					} else if (formElement instanceof GWTText) {
						GWTText text = (GWTText) formElement;
						text.setLabel(getStringValueFromVariable(map.get(formElement.getName())));
					} else if (formElement instanceof GWTSeparator) {
						// Nothing to be done here
					} else if (formElement instanceof GWTDownload) {
						// Nothing to be done here
					}
				}
			}
		}
	}
	
	/**
	 * @param map
	 */
	public void loadDataFromWorkflowVariables(Map<String, Object> map) {
		// Only iterate if really there's some variable to be mapped 
		if (!map.isEmpty()) {
			for (GWTFormElement formElement : formElementList) {
				if (formElement instanceof GWTTextArea) {
					GWTTextArea textArea = (GWTTextArea) formElement;
					
					if (!textArea.getData().equals("") && map.keySet().contains(textArea.getData())) {
						textArea.setValue(getStringValueFromVariable(map.get(textArea.getData())));
					}
				} else if (formElement instanceof GWTInput) {
					GWTInput input = (GWTInput) formElement;
					
					if (!input.getData().equals("") && map.keySet().contains(input.getData())) {
						Object var = map.get(input.getData());
						input.setValue(getStringValueFromVariable(var));
						
						if (input.getType().equals(GWTInput.TYPE_DATE)) {
							if (!"".equals(input.getValue())) {
								Date date = ISO8601.parse(input.getValue());
								
								if (date != null) {
									input.setDate(date);
								} else {
									Log.warn("Input '" + input.getName() + "' value should be in ISO8601 format: " + input.getValue());
								}
							}
						}
					}
				} else if (formElement instanceof GWTSuggestBox) {
					GWTSuggestBox suggestBox = (GWTSuggestBox) formElement;
					
					if (!suggestBox.getData().equals("") && map.keySet().contains(suggestBox.getData())) {
						suggestBox.setValue(getStringValueFromVariable(map.get(suggestBox.getData())));
					}
				} else if (formElement instanceof GWTCheckBox) {
					GWTCheckBox checkBox = (GWTCheckBox) formElement;
					
					if (!checkBox.getData().equals("") && map.keySet().contains(checkBox.getData())) {
						checkBox.setValue(getBooleanValueFromVariable(map.get(checkBox.getData())));
					}
				} else if (formElement instanceof GWTSelect) {
					GWTSelect select = (GWTSelect) formElement;
					
					if (!select.getData().equals("") && map.keySet().contains(select.getData())) {
						select.setOptions(getOptionsValueFromVariable(map.get(select.getData()), select.getOptions())) ;
					}
				} else if (formElement instanceof GWTUpload) {
					GWTUpload upload = (GWTUpload) formElement;
					
					if (!upload.getData().equals("") && map.keySet().contains(upload.getData())) {
						GWTUpload uploadData = (GWTUpload) map.get(upload.getData());
						
						if (!uploadData.getDocumentName().equals("")) {
							upload.setDocumentName(uploadData.getDocumentName());
						}
						
						if (!uploadData.getDocumentUuid().equals("")) {
							upload.setDocumentUuid(uploadData.getDocumentUuid());
						}
						
						if (!uploadData.getFolderPath().equals("")) {
							upload.setFolderPath(uploadData.getFolderPath());
						}
						
						if (!uploadData.getFolderUuid().equals("")) {
							upload.setFolderUuid(uploadData.getFolderUuid());
						}
						
						if (uploadData.getValidators().size()>0) {
							upload.setValidators(uploadData.getValidators());
						}
					}
				} else if (formElement instanceof GWTText) {
					GWTText text = (GWTText) formElement;
					if (!text.getData().equals("") && map.keySet().contains(text.getData())) {
						text.setLabel(getStringValueFromVariable(map.get(text.getData())));
					}
				} else if (formElement instanceof GWTSeparator) {
					// Nothing to be done here
				} else if (formElement instanceof GWTDownload) {
					GWTDownload download = (GWTDownload) formElement;
					if (!download.getData().equals("") && map.keySet().contains(download.getData())) {
						download.setNodes(getNodesValueFromVariable(map.get(download.getData())));
					}
				}
			}
		}
	}
	
	/**
	 * getNodesValueFromVariable
	 * 
	 * @param obj
	 * @return
	 */
	private List<GWTNode> getNodesValueFromVariable(Object obj) {
		if (obj instanceof GWTInput) {
			return new ArrayList<GWTNode>();
		} else if (obj instanceof GWTTextArea) {
			return new ArrayList<GWTNode>();
		} else if (obj instanceof GWTSuggestBox) {
			return new ArrayList<GWTNode>();
		} else if (obj instanceof GWTCheckBox) {
			return new ArrayList<GWTNode>();
		} else if (obj instanceof GWTSelect) {
			return new ArrayList<GWTNode>();
		} else if (obj instanceof GWTUpload) {
			return new ArrayList<GWTNode>();
		} else if (obj instanceof GWTText) {
			return new ArrayList<GWTNode>();
		} else if (obj instanceof GWTSeparator) {
			return new ArrayList<GWTNode>();
		} else if (obj instanceof GWTDownload) {
			GWTDownload download = (GWTDownload) obj;
			return download.getNodes();
		} else {
			return new ArrayList<GWTNode>();
		} 
	}
	
	/**
	 * getStringValueFromVariable
	 * 
	 * @param obj
	 * @return
	 */
	private String getStringValueFromVariable(Object obj) {
		if (obj instanceof GWTInput) {
			return ((GWTInput)obj).getValue();
		} else if (obj instanceof GWTTextArea) {
			return ((GWTTextArea)obj).getValue();
		} else if (obj instanceof GWTSuggestBox) {
			return ((GWTSuggestBox)obj).getValue();
		} else if (obj instanceof GWTCheckBox) {
			return String.valueOf(((GWTCheckBox)obj).getValue());
		} else if (obj instanceof GWTSelect) {
			String values = "";
			GWTSelect select = (GWTSelect) obj;
			
			for (GWTOption option : select.getOptions()) {
				if (option.isSelected()) {
					if (values.length() > 0) {
						values += "," + option.getValue();
					} else {
						values += option.getValue();
					}
				}
			}
			return values;
		} else if (obj instanceof GWTUpload) {
			return null;
		} else if (obj instanceof GWTText) {
			return ((GWTText)obj).getLabel();
		} else if (obj instanceof GWTSeparator) {
			return null;
		} else if (obj instanceof GWTDownload) {
			return null;
		} else {
			return null;
		} 
	}
	
	/**
	 * getBooleanValueFromVariable
	 * 
	 * @param obj
	 * @return
	 */
	private boolean getBooleanValueFromVariable(Object obj) {
		if (obj instanceof GWTInput) {
			return ((GWTInput)obj).getValue().toLowerCase().equals(BOOLEAN_TRUE);
		} else if (obj instanceof GWTTextArea) {
			return ((GWTTextArea)obj).getValue().toLowerCase().equals(BOOLEAN_TRUE);
		} else if (obj instanceof GWTSuggestBox) {
			return ((GWTSuggestBox)obj).getValue().toLowerCase().equals(BOOLEAN_TRUE);
		} else if (obj instanceof GWTCheckBox) {
			return ((GWTCheckBox)obj).getValue();
		} else if (obj instanceof GWTSelect) {
			String values = "";
			GWTSelect select = (GWTSelect) obj;
			
			for (GWTOption option : select.getOptions()) {
				if (option.isSelected()) {
					if (values.length() > 0) {
						values += "," + option.getValue();
					} else {
						values += option.getValue();
					}
				}
			}
			return values.toLowerCase().contains(BOOLEAN_TRUE); // test if on chain contains "true"
		} else if (obj instanceof GWTUpload) {
			return false;
		} else if (obj instanceof GWTText) {
			return false;
		} else if (obj instanceof GWTSeparator) {
			return false;
		} else if (obj instanceof GWTDownload) {
			return false;
		} else {
			return false;
		}
	}
	
	/**
	 * getOptionsValueFromVariable
	 * 
	 * @param obj
	 * @param options
	 * @return
	 */
	private Collection<GWTOption> getOptionsValueFromVariable(Object obj, Collection<GWTOption> options) {
		for (GWTOption option : options) {
			if (obj instanceof GWTInput) {
				if (option.getValue().equals(((GWTInput)obj).getValue())) {
					option.setSelected(true);
					return options;
				}
			} else if (obj instanceof GWTTextArea) {
				if (option.getValue().equals(((GWTTextArea)obj).getValue())) {
					option.setSelected(true);
					return options;
				}
			} else if (obj instanceof GWTSuggestBox) {
				if (option.getValue().equals(((GWTSuggestBox)obj).getValue())) {
					option.setSelected(true);
					return options;
				}
			} else if (obj instanceof GWTCheckBox) {
				if (option.getValue().equals(String.valueOf(((GWTCheckBox)obj).getValue()))) {
					option.setSelected(true);
					return options;
				}
			} else if (obj instanceof GWTSelect) {
				// Only doing mapping between values, if not found then is false
				boolean found = false;
				GWTSelect select = (GWTSelect) obj;
				for (GWTOption optionVar : select.getOptions()) {
					if (option.getValue().equals(optionVar.getValue())) {
						found = optionVar.isSelected();
						break;
					}
				}
				option.setSelected(found); // always setting values, if not found
			} else if (obj instanceof GWTUpload) {
				return options;
			} else if (obj instanceof GWTText) {
				return options;
			} else if (obj instanceof GWTSeparator) {
				return options;
			} else if (obj instanceof GWTDownload) {
				return null;
			} else {
				return options;
			}
		}
		return options;
	}
	
	/**
	 * Gets a string map values
	 * 
	 * @return
	 */
	public Map<String, String> getStringMapValues() {
		Map<String, String> values = new HashMap<String, String>();
		for (GWTFormElement formElement : formElementList) {
			if (formElement instanceof GWTTextArea) {
				values.put(formElement.getName(), getStringValueFromVariable(formElement));
			} else if (formElement instanceof GWTInput) {
				values.put(formElement.getName(), getStringValueFromVariable(formElement));
			} else if (formElement instanceof GWTSuggestBox) {
				values.put(formElement.getName(), getStringValueFromVariable(formElement));
			} else if (formElement instanceof GWTCheckBox) {
				values.put(formElement.getName(), getStringValueFromVariable(formElement));
			} else if (formElement instanceof GWTSelect) {
				values.put(formElement.getName(), getStringValueFromVariable(formElement));
			} else if (formElement instanceof GWTUpload) {
				// No aplicable to property groups
			} else if (formElement instanceof GWTText) {
				// Nothing to be done here
			} else if (formElement instanceof GWTSeparator) {
				// Nothing to be done here
			} else if (formElement instanceof GWTDownload) {
				// Nothing to be done here
			} 
		}
		return values;
	}
	
	/**
	 * setSubmitFormButton
	 * 
	 * @param submitForm
	 */
	public void setSubmitFormButton(Button submitForm) {
		this.submitForm = submitForm;
	}
	
	/**
	 * setTaskInstance
	 */
	public void setTaskInstance(GWTTaskInstance taskInstance) {
		this.taskInstance = taskInstance;
	}
	
	/**
	 * getValidationProcessor
	 * 
	 * @return
	 */
	public ValidationProcessor getValidationProcessor() {
		return validationProcessor;
	}
	
	/**
	 * DatabaseRecord
	 * 
	 * @author jllort
	 *
	 */
	class DatabaseRecord implements HasDatabaseRecord {
		private HTML keyWidget;
		private TextBox valueWidget;
		
		/**
		 * DatabaseRecord
		 * 
		 * @param keyWidget
		 * @param valueWidget
		 */
		public DatabaseRecord(HTML keyWidget, TextBox valueWidget) {
			this.keyWidget = keyWidget;
			this.valueWidget = valueWidget;
		}
		
		@Override
		public void setKeyValue(GWTKeyValue keyValue) {
			keyWidget.setHTML(keyValue.getKey());
			valueWidget.setText(keyValue.getValue());
		}		
	}
}