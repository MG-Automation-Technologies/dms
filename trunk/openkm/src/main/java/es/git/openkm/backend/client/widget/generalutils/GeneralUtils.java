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

package es.git.openkm.backend.client.widget.generalutils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.backend.client.Main;
import es.git.openkm.backend.client.config.Config;
import es.git.openkm.backend.client.service.OKMGeneralUtilsService;
import es.git.openkm.backend.client.service.OKMGeneralUtilsServiceAsync;

/**
 * GeneralUtils
 * 
 * @author jllort
 *
 */
public class GeneralUtils extends Composite {
	
	private final OKMGeneralUtilsServiceAsync generalUtilsService = (OKMGeneralUtilsServiceAsync) GWT.create(OKMGeneralUtilsService.class);
	
	private VerticalPanel vPanel;
	private FlexTable table;
	private TextBox importFileSystemPath;
	public TextBox importRepositoryPath; 
	private TextBox exportFileSystemPath;
	public TextBox exportRepositoryPath;
	private TextBox propertyGroupPath;
	private Button importButton;
	private Button exportButton;
	private Button registerButton;
	private FolderSelectPopup folderSelectPopup;
	private Image folderImportExplorer;
	private Image folderExportExplorer;
    private ListBox reportTypeList;
    private Button executeReportButton;
    private Button registerThesaurus;
    private ListBox showLevelList;
    private HTML levelText;
    public Status status;
	
	/**
	 * GeneralUtils
	 */
	public GeneralUtils() {
		vPanel = new VerticalPanel();
		table = new FlexTable();
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		importFileSystemPath = new TextBox();
		importRepositoryPath = new TextBox();
		exportFileSystemPath = new TextBox();
		exportRepositoryPath = new TextBox();
		propertyGroupPath = new TextBox();
		importButton = new Button(Main.i18n("general.util.import"));
		exportButton = new Button(Main.i18n("general.util.export"));
		registerButton = new Button(Main.i18n("general.util.register"));
		registerThesaurus = new Button(Main.i18n("general.util.register"));
		folderSelectPopup = new FolderSelectPopup();
		folderImportExplorer = new Image("img/icon/general/folder_explore.gif");
		folderExportExplorer = new Image("img/icon/general/folder_explore.gif");

		folderImportExplorer.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				folderSelectPopup.show(FolderSelectPopup.ACTION_IMPORT);
			}
		});
		
		folderExportExplorer.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				folderSelectPopup.show(FolderSelectPopup.ACTION_EXPORT);
			}
		});
		
		importButton.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (!importFileSystemPath.getText().equals("") && !importRepositoryPath.getText().equals("")) {
					repositoryImport(importRepositoryPath.getText(), importFileSystemPath.getText());
					setEnabledButtons(false);
				}
			}
		});
		
		exportButton.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (!exportFileSystemPath.getText().equals("") && !exportRepositoryPath.getText().equals("")) {
					repositoryExport(exportRepositoryPath.getText(), exportFileSystemPath.getText());
					setEnabledButtons(false);
				}
			}
		});
		
		registerButton.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (!propertyGroupPath.getText().equals("")) {
					registerCustomNodeTypes(propertyGroupPath.getText());
					setEnabledButtons(false);
				}
			}
		});
		
		vPanel.add(table);
		
		// Import
		HorizontalPanel hPanelImport = new HorizontalPanel();
		hPanelImport.add(importRepositoryPath);
		hPanelImport.add(new HTML("&nbsp;"));
		hPanelImport.add(folderImportExplorer);
		hPanelImport.setCellVerticalAlignment(folderImportExplorer, HasAlignment.ALIGN_MIDDLE);
		table.setHTML(0, 0, "<b>"+Main.i18n("general.util.import")+"</b>");
		table.setHTML(1, 0, Main.i18n("general.util.filesystem.path"));
		table.setWidget(1, 1, importFileSystemPath);
		table.setHTML(1, 2, Main.i18n("general.util.repository.path"));		
		table.setWidget(1, 3, hPanelImport);
		table.setWidget(1, 4, importButton);
		
		// Space
		table.setHTML(2, 0, "&nbsp;");
		
		// Export
		HorizontalPanel hPanelExport = new HorizontalPanel();
		hPanelExport.add(exportRepositoryPath);
		hPanelExport.add(new HTML("&nbsp;"));
		hPanelExport.add(folderExportExplorer);
		hPanelExport.setCellVerticalAlignment(folderImportExplorer, HasAlignment.ALIGN_MIDDLE);
		table.setHTML(3, 0, "<b>"+Main.i18n("general.util.export")+"</b>");
		table.setHTML(4, 0, Main.i18n("general.util.repository.path"));
		table.setWidget(4, 1, hPanelExport);
		table.setHTML(4, 2, Main.i18n("general.util.filesystem.path"));
		table.setWidget(4, 3, exportFileSystemPath);
		table.setWidget(4, 4, exportButton);
		
		// Space
		table.setHTML(5, 0, "&nbsp;");
		
		// Property Group
		table.setHTML(6, 0, "<b>"+Main.i18n("general.util.register.property.groups")+"</b>");
		table.setHTML(7, 0, Main.i18n("general.util.property.group.definition.path"));
		table.setWidget(7, 1, propertyGroupPath);
		table.setWidget(7, 2, registerButton);
		//table.getFlexCellFormatter().setColSpan(7, 2, 2);
		
		// Thesaurus
		levelText = new HTML(Main.i18n("general.util.thesaurus.show.level"));
		showLevelList = new ListBox();
		showLevelList.addItem("1", "0");
		showLevelList.addItem("2", "1");
		showLevelList.addItem("3", "2");
		showLevelList.addItem("4", "3");
		showLevelList.addItem("5", "4");
		
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(levelText);
		hPanel.add(new HTML("&nbsp;"));
		hPanel.add(showLevelList);
		hPanel.setCellVerticalAlignment(levelText, HasAlignment.ALIGN_MIDDLE);
		hPanel.setCellVerticalAlignment(showLevelList, HasAlignment.ALIGN_MIDDLE);
		
		table.setHTML(6, 3, "<b>"+Main.i18n("general.util.register.thesaurus")+"</b>");
		table.setWidget(7, 3, hPanel);
		table.setWidget(7, 4, registerThesaurus);
		
		registerThesaurus.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
					String level = showLevelList.getValue(showLevelList.getSelectedIndex());
					Main.get().centerPanel.generalUtilsPanel.setUrlResult(Config.OKMThesaurusServletAdmin + "?level="+level);
			}
		});
		
		// Space
		table.setHTML(8, 0, "&nbsp;");
		
		// Reports
		
		// Make some radio buttons, all in one group.
	    RadioButton pdf = new RadioButton("reportType", "pdf");
	    final RadioButton html = new RadioButton("reportType", "html");
	    pdf.setValue(true);

		executeReportButton = new Button(Main.i18n("button.execute"));
		reportTypeList = new ListBox();
		reportTypeList.addItem("", "");
		reportTypeList.addItem(Main.i18n("general.util.report.type.locked.documents"), Config.REPORT_LOCKED_DOCUMENTS);
		reportTypeList.addItem(Main.i18n("general.util.report.type.subscribed.documents"), Config.REPORT_SUBSCRIBED_DOCUMENTS);
		reportTypeList.addItem(Main.i18n("general.util.report.type.users"), Config.REPORT_REGISTERED_USERS);
		
		HorizontalPanel hReportPanel = new HorizontalPanel();
		hReportPanel.add(reportTypeList);
		hReportPanel.add(pdf);
		hReportPanel.add(html);
		
		hReportPanel.setWidth("100%");
		hReportPanel.setCellWidth(pdf, "60px");
		hReportPanel.setCellWidth(html, "60px");
		pdf.setStylePrimaryName("okm-NoWrap");
		html.setStylePrimaryName("okm-NoWrap");
	    
		table.setHTML(9, 0, "<b>"+Main.i18n("general.util.report")+"</b>");
		table.setHTML(10, 0, Main.i18n("general.util.report.type"));
		table.setWidget(10, 1, hReportPanel);
		table.setWidget(10, 2, executeReportButton);
		
		executeReportButton.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (reportTypeList.getSelectedIndex()>0) {
					String jasperFile = reportTypeList.getValue(reportTypeList.getSelectedIndex());
					String type = "pdf";
					if (html.getValue()) {
						type="html";
						Main.get().centerPanel.generalUtilsPanel.setUrlResult(Config.OKMReportServletAdmin + "?jasperFile=" + jasperFile + "&type="+type);
					} else {
						clearMessagePanel();
						Window.open(Config.OKMReportServletAdmin + "?jasperFile=" + jasperFile + "&type="+type, "_self", "");
					}
				}
			}
		});
		
		importRepositoryPath.setText("/okm:root");
		exportRepositoryPath.setText("/okm:root");
		
		importFileSystemPath.setStyleName("okm-Input");
		importRepositoryPath.setStyleName("okm-Input");
		exportFileSystemPath.setStyleName("okm-Input");
		exportRepositoryPath.setStyleName("okm-Input");
		propertyGroupPath.setStyleName("okm-Input");
		
		importFileSystemPath.setWidth("250");
		importRepositoryPath.setWidth("250");
		exportFileSystemPath.setWidth("250");
		exportRepositoryPath.setWidth("250");
		propertyGroupPath.setWidth("250");
		
		vPanel.setStyleName("okm-Input");
		importButton.setStyleName("okm-Input");
		exportButton.setStyleName("okm-Input");
		registerButton.setStyleName("okm-Input");
		reportTypeList.setStyleName("okm-Input");
		executeReportButton.setStyleName("okm-Input");
		registerThesaurus.setStyleName("okm-Input");
		showLevelList.setStyleName("okm-Input");
		folderSelectPopup.setStyleName("okm-Popup");
		folderSelectPopup.addStyleName("okm-DisableSelect");
		
		vPanel.setSize("100%","100%");
		
		initWidget(vPanel);
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		table.setHTML(1, 0, Main.i18n("general.util.filesystem.path"));
		table.setHTML(1, 0, Main.i18n("general.util.repository.path"));
		table.setHTML(4, 0, Main.i18n("general.util.repository.path"));
		table.setHTML(4, 2, Main.i18n("general.util.filesystem.path"));
		table.setHTML(6, 0, "<b>"+Main.i18n("general.util.register.property.groups")+"</b>");
		table.setHTML(6, 3, "<b>"+Main.i18n("general.util.register.thesaurus")+"</b>");
		table.setHTML(7, 0, Main.i18n("general.util.property.group.definition.path"));
		table.setHTML(7, 3, Main.i18n("general.util.thesaurus.show.level"));
		table.setHTML(9, 0, "<b>"+Main.i18n("general.util.report")+"</b>");
		table.setHTML(10, 0, Main.i18n("general.util.report.type"));
		importButton.setText(Main.i18n("general.util.import"));
		exportButton.setText(Main.i18n("general.util.export"));
		registerButton.setText(Main.i18n("general.util.register"));
		registerThesaurus.setText(Main.i18n("general.util.register"));
		levelText.setHTML(Main.i18n("general.util.thesaurus.show.level"));
		folderSelectPopup.langRefresh();
	}
	
	/**
	 * Call back repository import
	 */
	final AsyncCallback<String> callBackRepositoryImport = new AsyncCallback<String>() {
		public void onSuccess(String result) {
			Main.get().centerPanel.generalUtilsPanel.setMsg(result);
			setEnabledButtons(true);
			status.unsetFlag_import();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("callBackRepositoryImport", caught);
			setEnabledButtons(true);
			status.unsetFlag_import();
		}
	};
	
	/**
	 * Call back repository export
	 */
	final AsyncCallback<String> callBackRepositoryExport = new AsyncCallback<String>() {
		public void onSuccess(String result) {
			Main.get().centerPanel.generalUtilsPanel.setMsg(result);
			setEnabledButtons(true);
			status.unsetFlag_export();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("callBackRepositoryExport", caught);
			setEnabledButtons(true);
			status.unsetFlag_export();
		}
	};
	
	/**
	 * Call back repository export
	 */
	final AsyncCallback<Object> callBackRegisterCustomNodeTypes = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {
			setEnabledButtons(true);
			status.unsetFlag_properties();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("callBackRegisterCustomNodeTypes", caught);
			setEnabledButtons(true);
			status.unsetFlag_properties();
		}
	};
	
	/**
	 * Repository import
	 */
	public void repositoryImport(String repoPath, String fsPath) {
		clearMessagePanel();
		ServiceDefTarget endPoint = (ServiceDefTarget) generalUtilsService;
		endPoint.setServiceEntryPoint(Config.OKMGeneralUtilsService);	
		status.setFlag_import();
		status.refresh(vPanel);
		generalUtilsService.repositoryImport(repoPath, fsPath, callBackRepositoryImport);
	}
	
	/**
	 * Repository export
	 */
	public void repositoryExport(String repoPath, String fsPath) {
		clearMessagePanel();
		ServiceDefTarget endPoint = (ServiceDefTarget) generalUtilsService;
		endPoint.setServiceEntryPoint(Config.OKMGeneralUtilsService);
		status.setFlag_export();
		status.refresh(vPanel);
		generalUtilsService.repositoryExport(repoPath, fsPath, callBackRepositoryExport);
	}
	
	/**
	 * Repository export
	 */
	public void registerCustomNodeTypes(String pgPath) {
		clearMessagePanel();
		ServiceDefTarget endPoint = (ServiceDefTarget) generalUtilsService;
		endPoint.setServiceEntryPoint(Config.OKMGeneralUtilsService);	
		status.setFlag_properties();
		status.refresh(vPanel);
		generalUtilsService.registerCustomNodeTypes(pgPath, callBackRegisterCustomNodeTypes);
	}
	
	/**
	 * Enables or disables buttons
	 * 
	 * @param enable
	 */
	private void setEnabledButtons(boolean enable) {
		importButton.setEnabled(enable);
		exportButton.setEnabled(enable);
		registerButton.setEnabled(enable);
	}
	
	/**
	 * Clears the message Panel
	 */
	private void clearMessagePanel() {
		Main.get().centerPanel.generalUtilsPanel.setMsg("");
	}
}