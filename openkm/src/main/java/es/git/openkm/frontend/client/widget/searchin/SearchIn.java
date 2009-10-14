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

package es.git.openkm.frontend.client.widget.searchin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTMetaData;
import es.git.openkm.frontend.client.bean.GWTPropertyParams;
import es.git.openkm.frontend.client.bean.GWTQueryParams;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.panel.PanelDefinition;
import es.git.openkm.frontend.client.service.OKMAuthService;
import es.git.openkm.frontend.client.service.OKMAuthServiceAsync;
import es.git.openkm.frontend.client.service.OKMSearchService;
import es.git.openkm.frontend.client.service.OKMSearchServiceAsync;
import es.git.openkm.frontend.client.widget.eastereggs.FuturamaWalking;
import es.git.openkm.frontend.client.widget.searchsaved.Status;

/**
 * SearchIn
 * 
 * @author jllort
 *
 */
public class SearchIn extends Composite {
	
	private final OKMAuthServiceAsync authService = (OKMAuthServiceAsync) GWT.create(OKMAuthService.class);
	private final OKMSearchServiceAsync searchService = (OKMSearchServiceAsync) GWT.create(OKMSearchService.class);
	
	private static final int MIN_WORD_LENGTH = 3;
	private static final int CALENDAR_FIRED_NONE = -1;
	private static final int CALENDAR_FIRED_START = 0;
	private static final int CALENDAR_FIRED_END = 1;
	
	private VerticalPanel searchPanel;
	private FlexTable table;
	private TextBox content;
	private TextBox name;
	private TextBox keywords; 
	private Button searchButton;
	private Button saveSearchButton;
	private Button cleanButton;
	public Button addGroup;
	private TextBox searchSavedName;
	private GWTQueryParams params;
	public ControlSearchIn controlSearch;
	private ListBox resultPage;
	private ListBox mimeTypes;
	public ListBox context;
	private GroupPopup groupPopup;
	private HashMap<String, Widget> hWidgetProperties = new HashMap<String, Widget>();
	private FlexTable tableProperties;
	private HorizontalPanel hPanel;
	private KeyboardListener keyboardListener;
	private FuturamaWalking futuramaWalking;
	private List<Integer> advancedSearchIndexList = new ArrayList<Integer>();
	private CheckBox advancedSearch;
	private HorizontalPanel hPanelSearchConfig;
	private HTML advancedSearchText;
	private ListBox userListBox;
	private TextBox startDate;
	private TextBox endDate;
	private HorizontalPanel dateRange;
	private PopupPanel calendarPopup;
	private CalendarWidget calendar;
	private Image startCalendarIcon;
	private Image endCalendarIcon;
	private HTML dateBetween;
	private int calendarFired = CALENDAR_FIRED_NONE;
	private Date modifyDateFrom;
	private Date modifyDateTo;
	private CheckBox dashboard;
	private Status status;
	private boolean userNews = false;
	private Image pathExplorer;
	private HorizontalPanel pathExplorerPanel;
	public TextBox path;
	private FolderSelectPopup folderSelectPopup;
	private HorizontalPanel typePanel;
	private CheckBox typeDocument;
	private CheckBox typeFolder;
	private CheckBox typeMail;
	private HTML document;
	private HTML folder;
	private HTML mail;
	private FlexTable tableMail;
	private TextBox from;
	private TextBox to;
	private TextBox subject;
	HorizontalPanel searchTypePanel;
	final CheckBox searchTypeAnd;
	final CheckBox searchTypeOr;
	
	/**
	 * SearchIn
	 */
	public SearchIn() {
		futuramaWalking = new FuturamaWalking();
		searchPanel = new VerticalPanel();
		table = new FlexTable();
		content = new TextBox();
		name = new TextBox();
		keywords = new TextBox();
		searchSavedName = new TextBox();
		controlSearch = new ControlSearchIn();
		resultPage = new ListBox();
		advancedSearch = new CheckBox();
		mimeTypes = new ListBox();
		groupPopup = new GroupPopup();
		groupPopup.setWidth("300px");
		groupPopup.setHeight("125px");
		groupPopup.setStyleName("okm-Popup");
		groupPopup.addStyleName("okm-DisableSelect");
		tableProperties = new FlexTable();
		hPanel = new HorizontalPanel();
		hPanelSearchConfig = new HorizontalPanel();
		advancedSearchText = new HTML(Main.i18n("search.advanced"));
		userListBox = new ListBox();
		startDate = new TextBox();
		endDate = new TextBox();
		dateRange = new HorizontalPanel();
		calendar = new CalendarWidget();
		calendarPopup = new PopupPanel(true);
		startCalendarIcon = new Image("img/icon/search/calendar.gif");
		endCalendarIcon =  new Image("img/icon/search/calendar.gif");
		dateBetween = new HTML("&nbsp;&nbsp;"+Main.i18n("search.date.and")+"&nbsp;&nbsp;");
		dashboard = new CheckBox();
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		tableMail = new FlexTable();
		
		searchPanel.setSize("100%", "100%");
		
		// Calendar widget
		calendarPopup.setWidget(calendar);

		calendar.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				calendarPopup.hide();
				DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
				
				switch (calendarFired) {
					case CALENDAR_FIRED_START:
						startDate.setText(dtf.format(calendar.getDate()));
						modifyDateFrom = calendar.getDate();
						break;
					
					case CALENDAR_FIRED_END:
						endDate.setText(dtf.format(calendar.getDate()));
						modifyDateTo = calendar.getDate();
						break;
				}
				calendarFired = CALENDAR_FIRED_NONE;
				evaluateSearchButtonVisible();	
			}
		});
		
		startCalendarIcon.addClickListener(new ClickListener(){
			public void onClick(Widget arg0) {
				calendarFired = CALENDAR_FIRED_START;
				calendarPopup.setPopupPosition(startCalendarIcon.getAbsoluteLeft(), startCalendarIcon.getAbsoluteTop()-2);
				calendarPopup.show();
			}
			
		});
		
		endCalendarIcon.addClickListener(new ClickListener(){
			public void onClick(Widget arg0) {
				calendarFired = CALENDAR_FIRED_END;
				calendarPopup.setPopupPosition(endCalendarIcon.getAbsoluteLeft(), endCalendarIcon.getAbsoluteTop()-2);
				calendarPopup.show();
			}
			
		});
		
		// Advanced search
		advancedSearch.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				enableAdvancedSearch(advancedSearch.isChecked());
				resizeScreenToAdvancedMode(advancedSearch.isChecked());
			}
		});
		
		// Table padding and spacing properties
		tableProperties.setCellPadding(0);
		tableProperties.setCellSpacing(0);
		
		searchButton = new Button(Main.i18n("button.search"), new ClickListener() {
			public void onClick(Widget sender) {
				executeSearch();
			}
		});
		
		cleanButton = new Button(Main.i18n("button.clean"), new ClickListener() {
			public void onClick(Widget sender) {
				//context.setSelectedIndex(PanelDefinition.NAVIGATOR_ALL_CONTEXT);
				context.setSelectedIndex(PanelDefinition.NAVIGATOR_TAXONOMY);
				content.setText("");
				path.setText("");
				name.setText("");
				keywords.setText("");
				searchSavedName.setText("");
				searchButton.setEnabled(false);
				saveSearchButton.setEnabled(false);
				controlSearch.setVisible(false);
				removeTablePropertiesRows();
				typeDocument.setChecked(true);
				typeFolder.setChecked(false);
				typeMail.setChecked(false);
				mimeTypes.setSelectedIndex(0);
				userListBox.setSelectedIndex(0);
				startDate.setText("");
				endDate.setText("");
				modifyDateFrom = null;
				modifyDateTo = null;
				from.setText("");
				to.setText("");
				subject.setText("");
				Main.get().mainPanel.search.searchResult.removeAllRows();
			}
		});
		
		saveSearchButton= new Button(Main.i18n("button.save.search"), new ClickListener() {
			public void onClick(Widget sender) {
				long domain = 0;
				String operator = GWTQueryParams.OPERATOR_AND;
				params = new GWTQueryParams();
				if (!path.getText().equals("")) {
					params.setPath(path.getText());
				} else {
					params.setPath(context.getValue(context.getSelectedIndex()));
				}
				params.setContent(content.getText());
				params.setName(name.getText());
				params.setKeywords(keywords.getText());
				params.setSearchProperties(getProperties());
				params.setAuthor(userListBox.getValue(userListBox.getSelectedIndex()));
				params.setLastModifiedFrom(modifyDateFrom);
				params.setLastModifiedTo(modifyDateTo);
				params.setDashboard(dashboard.isChecked());
				params.setFrom(from.getText());
				params.setTo(to.getText());
				params.setSubject(subject.getText());
				
				if (typeDocument.isChecked()) {
					domain += GWTQueryParams.DOCUMENT;
				}
				if (typeFolder.isChecked()) {
					domain += GWTQueryParams.FOLDER;
				}
				if (typeMail.isChecked()) {
					domain += GWTQueryParams.MAIL;
				}
				params.setDomain(domain);
				
				if (searchTypeAnd.isChecked()) {
					operator = GWTQueryParams.OPERATOR_AND;
				} else {
					operator = GWTQueryParams.OPERATOR_OR;
				}
				params.setOperator(operator);
				
				// Removes dates if dashboard is checked
				if (dashboard.isChecked()) {
					params.setLastModifiedFrom(null);
					params.setLastModifiedTo(null);
				}
				
				params.setMimeType(mimeTypes.getValue(mimeTypes.getSelectedIndex()));
				
				if (!searchSavedName.getText().equals("")) {
					userNews = params.isDashboard();
					saveSearch(params,"sql", searchSavedName.getText());
				}
			}
		});
		
		addGroup = new Button(Main.i18n("search.add.property.group"), new ClickListener() {
			public void onClick(Widget sender) {
				groupPopup.show();
			}
		});
		
		context = new ListBox();
		context.setStyleName("okm-Select");
		context.addItem(Main.i18n("leftpanel.label.taxonomy"),"");
		context.addItem(Main.i18n("leftpanel.label.templates"),"");
		context.addItem(Main.i18n("leftpanel.label.my.documents"),"");
		context.addItem(Main.i18n("leftpanel.label.mail"),"");
		context.addItem(Main.i18n("leftpanel.label.trash"),"");
		//context.addItem(Main.i18n("leftpanel.label.all.repository"),"");
		//context.setSelectedIndex(PanelDefinition.NAVIGATOR_ALL_CONTEXT);
		context.setSelectedIndex(PanelDefinition.NAVIGATOR_TAXONOMY);
		
		context.addChangeListener(new ChangeListener() {
			public void onChange(Widget arg0) {
				path.setText(""); // each time list is changed must clean folder
			}
		});
		
		// To enable enter on textBox
		keyboardListener = new KeyboardListener() {
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {}
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				evaluateSearchButtonVisible();
			}
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				if ((char)KeyboardListener.KEY_ENTER == keyCode && searchButton.isEnabled()) {
					executeSearch();
				}
			}
		};
		
		content.addKeyboardListener(keyboardListener);
		name.addKeyboardListener(keyboardListener);
		keywords.addKeyboardListener(keyboardListener);
		
		searchSavedName.addKeyboardListener(new KeyboardListener() {
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {}
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				evalueSaveSearchButtonVisible();
			}
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {}
		});
		
		// Sets the folder explorer
		folderSelectPopup = new FolderSelectPopup();
		pathExplorerPanel = new HorizontalPanel();
		path = new TextBox();
		path.setReadOnly(true);
		pathExplorer =  new Image("img/icon/search/folder_explore.gif");
		
		pathExplorerPanel.add(path);
		pathExplorerPanel.add(new HTML("&nbsp;"));
		pathExplorerPanel.add(pathExplorer);
		
		pathExplorer.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				folderSelectPopup.show();
			}
		});
		
		pathExplorerPanel.setCellVerticalAlignment(pathExplorer, HasAlignment.ALIGN_MIDDLE);
		
		// Sets type document
		typePanel = new HorizontalPanel();
		typeDocument = new CheckBox();
		typeDocument.setChecked(true);
		document = new HTML(Main.i18n("search.type.document"));
		typeFolder = new CheckBox();
		typeFolder.setChecked(false);
		folder = new HTML(Main.i18n("search.type.folder"));
		typeMail = new CheckBox();
		typeMail.setChecked(false);
		typeMail.addClickListener(new ClickListener(){
			public void onClick(Widget arg0) {
				if (typeMail.isChecked()){
					tableMail.setVisible(true);
				} else {
					tableMail.setVisible(false);
				}
			}
			
		});
		mail = new HTML(Main.i18n("search.type.mail"));
		typePanel.add(typeDocument);
		typePanel.add(document);
		typePanel.add(new HTML("&nbsp;"));
		typePanel.add(typeFolder);
		typePanel.add(folder);
		typePanel.add(new HTML("&nbsp;"));
		typePanel.add(typeMail);
		typePanel.add(mail);
		typePanel.add(new HTML("&nbsp;"));
		typePanel.setCellVerticalAlignment(document, HasAlignment.ALIGN_MIDDLE);
		typePanel.setCellVerticalAlignment(folder, HasAlignment.ALIGN_MIDDLE);
		typePanel.setCellVerticalAlignment(mail, HasAlignment.ALIGN_MIDDLE);
		
		// Type of search
		searchTypePanel = new HorizontalPanel();
		searchTypePanel.setVisible(false);  // On OpenKM 4.0 has hidden AND / OR option list
		searchTypeAnd = new CheckBox("AND");
		searchTypeOr = new CheckBox("OR");
		searchTypeAnd.setChecked(true);
		searchTypeOr.setChecked(false);
		
		searchTypeAnd.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				searchTypeOr.setChecked(!searchTypeAnd.isChecked()); // Always set changed between and and or type
			}
		});
		
		searchTypeOr.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				searchTypeAnd.setChecked(!searchTypeOr.isChecked()); // Always set changed between and and or type
			}
		});
		
		HTML space1 = new HTML("");
		searchTypePanel.add(searchTypeAnd);
		searchTypePanel.add(space1);
		searchTypePanel.add(searchTypeOr);
		searchTypePanel.setCellWidth(space1, "10");
		
		// Sets mime types values
		mimeTypes.addItem(" ", "");
		mimeTypes.addItem("HTML", "text/html");
		mimeTypes.addItem("MS Excel", "application/vnd.ms-excel");
		mimeTypes.addItem("MS PowerPoint", "application/vnd.ms-powerpoint");
		mimeTypes.addItem("MS Word", "application/msword");
		mimeTypes.addItem("OpenOffice.org Database", "application/vnd.oasis.opendocument.database");
		mimeTypes.addItem("OpenOffice.org Draw", "application/vnd.oasis.opendocument.graphics");
		mimeTypes.addItem("OpenOffice.org Presentation", "application/vnd.oasis.opendocument.presentation");
		mimeTypes.addItem("OpenOffice.org Spreadsheet", "application/vnd.oasis.opendocument.spreadsheet");
		mimeTypes.addItem("OpenOffice.org Word Processor", "application/vnd.oasis.opendocument.text");
		mimeTypes.addItem("PDF", "application/pdf");
		mimeTypes.addItem("RTF", "application/rtf");
		mimeTypes.addItem("TXT", "text/plain");
		mimeTypes.addItem("XML", "text/xml");
		
		mimeTypes.addChangeListener(new ChangeListener() {
			public void onChange(Widget arg0) {
				evaluateSearchButtonVisible();							
			}
		});
		
		userListBox.addChangeListener(new ChangeListener() {
			public void onChange(Widget arg0) {
				evaluateSearchButtonVisible();							
			}
		});
		
		resultPage.addItem("10", "10");
		resultPage.addItem("20", "20");
		resultPage.addItem("30", "30");
		
		advancedSearch.setChecked(false);
		
		hPanelSearchConfig.add(resultPage);
		hPanelSearchConfig.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
		hPanelSearchConfig.add(advancedSearch);
		hPanelSearchConfig.add(new HTML("&nbsp;"));
		hPanelSearchConfig.add(advancedSearchText);
		hPanelSearchConfig.setCellVerticalAlignment(advancedSearchText, HasAlignment.ALIGN_MIDDLE);
		advancedSearchText.addStyleName("okm-NoWrap");
		
		// Horizontal panel for control search and search & clean buttons
		hPanel.add(cleanButton);
		hPanel.add(new HTML("&nbsp;&nbsp;"));
		hPanel.add(searchButton);
		hPanel.add(new HTML("&nbsp;&nbsp;"));
		hPanel.add(controlSearch);
		
		// Date range panel
		dateRange.add(startDate);
		dateRange.add(startCalendarIcon);
		dateRange.add(dateBetween);
		dateRange.add(endDate);
		dateRange.add(endCalendarIcon);
		startDate.setWidth("70");
		endDate.setWidth("70");
		startDate.setMaxLength(10);
		endDate.setMaxLength(10);
		startDate.setReadOnly(true);
		endDate.setReadOnly(true);
		dateRange.setCellVerticalAlignment(startCalendarIcon,HasAlignment.ALIGN_MIDDLE);
		dateRange.setCellVerticalAlignment(endCalendarIcon,HasAlignment.ALIGN_MIDDLE);
		dateBetween.addStyleName("okm-NoWrap");
		
		table.setHTML(0, 0, Main.i18n("search.context"));
		table.setWidget(0, 1, context);
		table.setHTML(0, 2, "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		table.setWidget(0, 3, tableMail);
		table.setHTML(0, 4, "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		table.setWidget(0, 5, addGroup);
		table.setHTML(1, 0, Main.i18n("search.content"));
		table.setWidget(1, 1, content);
		table.setWidget(1, 2, tableProperties);
		table.setHTML(2, 0, Main.i18n("search.name"));
		table.setWidget(2, 1, name);
		table.setHTML(3, 0, Main.i18n("search.keywords"));
		table.setWidget(3, 1, keywords);
		table.setHTML(4, 0, Main.i18n("search.folder"));
		table.setWidget(4, 1, pathExplorerPanel);
		table.setHTML(5, 0, Main.i18n("search.type"));
		table.setWidget(5, 1, typePanel);
		// table.setHTML(6, 0, Main.i18n("search.type"));	// On OpenKM 4.0 has hidden AND / OR option list
		//table.setWidget(6, 1, searchTypePanel);			// On OpenKM 4.0 has hidden AND / OR option list
		table.setHTML(7, 0, Main.i18n("search.mimetype"));
		table.setWidget(7, 1, mimeTypes);
		table.setHTML(8, 0, Main.i18n("search.user"));
		table.setWidget(8, 1, userListBox);
		table.setHTML(9, 0, Main.i18n("search.date.range"));
		table.setWidget(9, 1, dateRange);
		table.setHTML(10, 0, Main.i18n("search.page.results"));
		table.setWidget(10, 1, hPanelSearchConfig);
		table.setHTML(11, 0, Main.i18n("search.save.as.news"));
		table.setWidget(11, 1, dashboard);
		table.setWidget(12, 0, saveSearchButton);
		table.setWidget(12, 1, searchSavedName);
		table.setWidget(13, 0, hPanel);
		
		// Index list of advanced search widgets
		advancedSearchIndexList.add(new Integer(4));	// Folder
		advancedSearchIndexList.add(new Integer(5));	// Mime-types
		advancedSearchIndexList.add(new Integer(6)); 	// User list
		advancedSearchIndexList.add(new Integer(7)); 	// Date rang
		
		searchButton.setEnabled(false);
		saveSearchButton.setEnabled(false);
		
		// Adding mail search params
		from = new TextBox();
		to = new TextBox();
		subject = new TextBox();
		tableMail.setHTML(0, 0, Main.i18n("mail.from"));
		tableMail.setWidget(0, 1, from);
		tableMail.setHTML(1, 0, Main.i18n("mail.to"));
		tableMail.setWidget(1, 1, to);
		tableMail.setHTML(2, 0, Main.i18n("mail.subject"));
		tableMail.setWidget(2, 1, subject);
		setRowWordWarp(tableMail, 0, 2, false);
		setRowWordWarp(tableMail, 1, 2, false);
		setRowWordWarp(tableMail, 2, 2, false);
		setRowWordWarp(tableMail, 3, 2, false);
		tableMail.setVisible(false);
		
		from.addKeyboardListener(keyboardListener);
		to.addKeyboardListener(keyboardListener);
		subject.addKeyboardListener(keyboardListener);
		
		// The hidden column extends table to 100% width
		CellFormatter cellFormatter = table.getCellFormatter();
		cellFormatter.setWidth(0, 5, "100%");
		cellFormatter.setVerticalAlignment(0,3,VerticalPanel.ALIGN_TOP);
		cellFormatter.setHorizontalAlignment(0,3,VerticalPanel.ALIGN_LEFT);
		cellFormatter.setVerticalAlignment(0,5,VerticalPanel.ALIGN_TOP);
		cellFormatter.setHorizontalAlignment(0,5,VerticalPanel.ALIGN_LEFT);
		cellFormatter.setVerticalAlignment(0,6,VerticalPanel.ALIGN_TOP);
		cellFormatter.setHorizontalAlignment(0,6,VerticalPanel.ALIGN_LEFT);
		cellFormatter.setVerticalAlignment(1,2,VerticalPanel.ALIGN_TOP);
		cellFormatter.setHorizontalAlignment(1,2,VerticalPanel.ALIGN_LEFT);;
		cellFormatter.setHorizontalAlignment(7,0,VerticalPanel.ALIGN_LEFT);
		cellFormatter.setStyleName(0, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(1, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(2, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(3, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(4, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(5, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(6, 0, "okm-DisableSelect");
		
		// Sets a rowspan for left cell
		table.getFlexCellFormatter().setRowSpan(0,2,13);
		table.getFlexCellFormatter().setRowSpan(0,3,13);
		table.getFlexCellFormatter().setRowSpan(0,4,13);
		table.getFlexCellFormatter().setRowSpan(1,2,12);
		
		// Sets a colspan for row
		table.getFlexCellFormatter().setColSpan(13,0,5);
		
		// Sets wordWrap for al rows
		setRowWordWarp(table, 0, 1, false);
		setRowWordWarp(table, 1, 1, false);
		setRowWordWarp(table, 2, 1, false);
		setRowWordWarp(table, 3, 1, false);
		setRowWordWarp(table, 4, 1, false);
		setRowWordWarp(table, 5, 1, false);
		setRowWordWarp(table, 6, 1, false);
		setRowWordWarp(table, 7, 1, false);
		setRowWordWarp(table, 8, 2, false);
		setRowWordWarp(table, 9, 1, false);
		setRowWordWarp(table, 10, 1, false);
		setRowWordWarp(table, 11, 1, false);
		
		searchPanel.add(table);
		searchButton.setStyleName("okm-Button");
		saveSearchButton.setStyleName("okm-Button");
		saveSearchButton.addStyleName("okm-NoWrap");
		cleanButton.setStyleName("okm-Button");
		addGroup.setStyleName("okm-Button");
		addGroup.addStyleName("okm-NoWrap");
		content.setStyleName("okm-Input");
		name.setStyleName("okm-Input");
		keywords.setStyleName("okm-Input");
		resultPage.setStyleName("okm-Input");
		searchSavedName.setStyleName("okm-Input");
		document.addStyleName("okm-NoWrap");
		folder.addStyleName("okm-NoWrap");
		mail.addStyleName("okm-NoWrap");
		mimeTypes.setStyleName("okm-Select");
		userListBox.setStyleName("okm-Input");
		startDate.setStyleName("okm-Input");
		endDate.setStyleName("okm-Input");
		path.setStyleName("okm-Input");
		folderSelectPopup.setStyleName("okm-Popup");
		folderSelectPopup.addStyleName("okm-DisableSelect");
		from.setStyleName("okm-Input");
		to.setStyleName("okm-Input");
		subject.setStyleName("okm-Input");
		
		// Gets all users
		getAllUsers();
		
		// Disables advanced search by default
		enableAdvancedSearch(false);
		
		initWidget(searchPanel);
	}
	
	/**
	 * Evalues Save Search button visibility
	 */
	public void evalueSaveSearchButtonVisible() {
		if (searchSavedName.getText().length()>0 && searchButton.isEnabled()) {
			saveSearchButton.setEnabled(true);
		} else {
			saveSearchButton.setEnabled(false);
		}
	}
	
	/**
	 * Evalues seach button visibility
	 */
	public void evaluateSearchButtonVisible() {
		if (content.getText().length() >= MIN_WORD_LENGTH || name.getText().length() >= MIN_WORD_LENGTH ||
			keywords.getText().length() >= MIN_WORD_LENGTH || from.getText().length() >= MIN_WORD_LENGTH ||
			to.getText().length() >= MIN_WORD_LENGTH || subject.getText().length() >= MIN_WORD_LENGTH) {
			searchButton.setEnabled(true);
		} else {
			searchButton.setEnabled(false);
		}
		
		// Evaluates Mime Types
		if (mimeTypes.getSelectedIndex()>0) {
			searchButton.setEnabled(true);
		}
		
		// Evaluates user list
		if (userListBox.getSelectedIndex()>0) {
			searchButton.setEnabled(true);
		}
		
		// Evaluates date range
		if (modifyDateFrom!=null && modifyDateTo!=null) {
			searchButton.setEnabled(true);
		}
		
		// Evaluates properties to enable button
		Collection<String> properties = hWidgetProperties.keySet();
		
		for (Iterator<String> it = properties.iterator(); it.hasNext();){
			String key = it.next();
			Object widget = hWidgetProperties.get(key);
			if (widget instanceof TextBox) {
				if (((TextBox) widget).getText().length() >= MIN_WORD_LENGTH) {
					searchButton.setEnabled(true);
				}
			} else if (widget instanceof ListBox) {
				if (((ListBox) widget).getSelectedIndex()>0) {
					searchButton.setEnabled(true);
				}
			}
		}
		
		// After evaluating search button, must evaluate save search too
		evalueSaveSearchButtonVisible();
	}
	
	/**
	 * Refreshing lang
	 */
	public void langRefresh() {
		table.setHTML(0, 0, Main.i18n("search.context"));
		table.setHTML(1, 0, Main.i18n("search.content"));
		table.setHTML(2, 0, Main.i18n("search.name"));
		table.setHTML(3, 0, Main.i18n("search.keywords"));
		table.setHTML(4, 0, Main.i18n("search.folder"));
		table.setHTML(5, 0, Main.i18n("search.type"));
		// table.setHTML(6, 0, Main.i18n("search.type"));	// On OpenKM 4.0 has hidden AND / OR option list
		table.setHTML(7, 0, Main.i18n("search.mimetype"));
		table.setHTML(8, 0, Main.i18n("search.user"));
		table.setHTML(9, 0, Main.i18n("search.date.range"));
		table.setHTML(10, 0, Main.i18n("search.page.results"));
		table.setHTML(11, 0, Main.i18n("search.save.as.news"));
		
		tableMail.setHTML(0, 0, Main.i18n("mail.from"));
		tableMail.setHTML(1, 0, Main.i18n("mail.to"));
		tableMail.setHTML(2, 0, Main.i18n("mail.subject"));
		
		context.setItemText(PanelDefinition.NAVIGATOR_TAXONOMY,Main.i18n("leftpanel.label.taxonomy"));
		context.setItemText(PanelDefinition.NAVIGATOR_TEMPLATES,Main.i18n("leftpanel.label.templates"));
		context.setItemText(PanelDefinition.NAVIGATOR_PERSONAL,Main.i18n("leftpanel.label.my.documents"));
		context.setItemText(PanelDefinition.NAVIGATOR_MAIL,Main.i18n("leftpanel.label.mail"));
		context.setItemText(PanelDefinition.NAVIGATOR_TRASH,Main.i18n("leftpanel.label.trash"));
		//context.setItemText(PanelDefinition.NAVIGATOR_ALL_CONTEXT,Main.i18n("leftpanel.label.all.repository"));
		
		document.setHTML(Main.i18n("search.type.document"));
		folder.setHTML(Main.i18n("search.type.folder"));
		mail.setHTML(Main.i18n("search.type.mail"));
		
		controlSearch.langRefresh();
				
		searchButton.setHTML(Main.i18n("button.search"));
		cleanButton.setHTML(Main.i18n("button.clean"));
		saveSearchButton.setHTML(Main.i18n("button.save.search"));
		addGroup.setHTML(Main.i18n("search.add.property.group"));
		advancedSearchText.setHTML(Main.i18n("search.advanced"));
		dateBetween.setHTML("&nbsp;&nbsp;"+Main.i18n("search.date.and")+"&nbsp;&nbsp;");
		groupPopup.langRefresh();
		calendar.langRefresh();
		folderSelectPopup.langRefresh();
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
	
	/**
	 * Executes the search
	 */
	private void executeSearch() {
		long domain = 0;
		GWTQueryParams gwtParams = new GWTQueryParams();
		gwtParams.setContent(content.getText());
		if (!path.getText().equals("")) {
			gwtParams.setPath(path.getText());
		} else {
			gwtParams.setPath(context.getValue(context.getSelectedIndex()));
		}
		gwtParams.setKeywords(keywords.getText());
		gwtParams.setMimeType("");
		gwtParams.setName(name.getText());
		gwtParams.setAuthor(userListBox.getValue(userListBox.getSelectedIndex()));
		
		gwtParams.setFrom(from.getText());
		gwtParams.setTo(to.getText());
		gwtParams.setSubject(subject.getText());
		
		if (searchTypeAnd.isChecked()) {
			gwtParams.setOperator(GWTQueryParams.OPERATOR_AND);
		} else {
			gwtParams.setOperator(GWTQueryParams.OPERATOR_OR);
		}
		
		if (modifyDateFrom!=null && modifyDateTo!=null) {
			gwtParams.setLastModifiedFrom(modifyDateFrom);
			gwtParams.setLastModifiedTo(modifyDateTo);
		} else {
			gwtParams.setLastModifiedFrom(null);
			gwtParams.setLastModifiedTo(null);
		}
		
		if (typeDocument.isChecked()) {
			domain += GWTQueryParams.DOCUMENT;
		}
		if (typeFolder.isChecked()) {
			domain += GWTQueryParams.FOLDER;
		}
		if (typeMail.isChecked()) {
			domain += GWTQueryParams.MAIL;
		}
		gwtParams.setDomain(domain);
		
		gwtParams.setSearchProperties(getProperties());
		gwtParams.setMimeType(mimeTypes.getValue(mimeTypes.getSelectedIndex()));
		futuramaWalking.evaluate(content.getText());
		controlSearch.executeSearch(gwtParams, Integer.parseInt(resultPage.getItemText(resultPage.getSelectedIndex())));
	}
	
	/**
	 * Gets the properties 
	 * 
	 * @return The properties
	 */
	private HashMap<String, String> getProperties() {
		HashMap<String, String> hProperties = new HashMap<String, String>();
		
		Collection<String> properties = hWidgetProperties.keySet();
		
		for (Iterator<String> it = properties.iterator(); it.hasNext();){
			String key = it.next();
			Object widget = hWidgetProperties.get(key);
			
			if (widget instanceof TextBox){
				TextBox textBox = (TextBox) widget;
				if (!textBox.getText().equals("")) {
					hProperties.put(key, textBox.getText());
				}
			} else if (widget instanceof ListBox){
				ListBox listBox = (ListBox) widget;
				if (listBox.getSelectedIndex()>0) {
					hProperties.put(key, listBox.getValue(listBox.getSelectedIndex()) );
				}
			} 
		}
		
		return hProperties;
	}
	
	/**
	 * Add property group
	 * 
	 * @param grpName Group key
	 * @param propertyName Property group key
	 * @param gwtMetadata Property metada
	 * @param propertyValue The selected value
	 */
	public void addProperty(String grpName, final String propertyName, GWTMetaData gwtMetadata, String propertyValue){
		int rows = tableProperties.getRowCount();
		Image removeImage;
		
		if (!hWidgetProperties.containsKey(propertyName)) {
		
			switch (gwtMetadata.getType()) {
			
				case GWTMetaData.INPUT:
				case GWTMetaData.TEXT_AREA:
					TextBox textBox = new TextBox(); // Create a widget for this property
					textBox.setStyleName("okm-Input");
					textBox.addKeyboardListener(keyboardListener);
					hWidgetProperties.put(propertyName,textBox);
					tableProperties.setHTML(rows, 0, "<b>" + Main.propertyGroupI18n(grpName) + " :</b>");
					tableProperties.setHTML(rows, 1, "&nbsp;" + Main.propertyGroupI18n(propertyName) + "&nbsp;");
					tableProperties.setWidget(rows, 2, textBox);
					
					removeImage = new Image("img/icon/actions/delete.gif");
					removeImage.addClickListener(new ClickListener(){
						public void onClick(Widget arg0) {
							Main.get().mainPanel.search.searchIn.removeProperty(arg0,propertyName);
							groupPopup.enableAddGroupButton(); // Enables or disables button ( depends exist some item on list to be added )
						}
					});
					
					tableProperties.setWidget(rows, 3, removeImage);
					setRowWordWarp(tableProperties, rows, 2, false);
					
					if (propertyValue!=null && !propertyValue.equals("")) {
						textBox.setText(propertyValue);
					}
					break;
				
				case GWTMetaData.SELECT:
				case GWTMetaData.SELECT_MULTI:
					ListBox listBox = new ListBox();
					listBox.setStyleName("okm-Select");
					listBox.addItem("",""); // Always we set and empty value
					listBox.addChangeListener(new ChangeListener(){
						public void onChange(Widget arg0) {
							evaluateSearchButtonVisible();							
						}
					});
					
					for (Iterator<String> itData = gwtMetadata.getValues().iterator(); itData.hasNext(); ){
						String value = itData.next();
						listBox.addItem(Main.propertyGroupI18n(propertyName+"."+value),value); // The translation is composed by propertyName + "." + value key
						
						// Selects the selected value
						if (propertyValue!=null && !propertyValue.equals("") && propertyValue.equals(value)) {
							listBox.setSelectedIndex(listBox.getItemCount()-1);
						}
					}
					
					hWidgetProperties.put(propertyName,listBox);
					
					tableProperties.setHTML(rows, 0, "<b>" + Main.propertyGroupI18n(grpName) + " : </b>");
					tableProperties.setHTML(rows, 1, "&nbsp;" + Main.propertyGroupI18n(propertyName) + "&nbsp;");
					tableProperties.setWidget(rows, 2, listBox);
					
					removeImage = new Image("img/icon/actions/delete.gif");
					removeImage.addClickListener(new ClickListener(){
						public void onClick(Widget arg0) {
							Main.get().mainPanel.search.searchIn.removeProperty(arg0,propertyName);
							groupPopup.enableAddGroupButton(); // Enables or disables button ( depends exist some item on list to be added )
						}
					});
					
					tableProperties.setWidget(rows, 3, removeImage);
					setRowWordWarp(tableProperties, rows, 2, false);					
					break;
			}
		}
	}
	
	/**
	 * Gets the actual key properties
	 * 
	 * @return The actual properties values
	 */
	public Collection getActualProperties(){
		return hWidgetProperties.keySet();
	}
	
	/**
	 * removeProperty
	 * 
	 * @param rows The row
	 * @param propertyName The property name
	 */
	public void removeProperty(Widget arg0, String propertyName){
		for (int i=0; i<tableProperties.getRowCount(); i++) {
			if (tableProperties.getWidget(i,3).equals(arg0)) {
				tableProperties.removeRow(i);
			}
		}
		
		hWidgetProperties.remove(propertyName);
		evaluateSearchButtonVisible();
	}
	
	/**
	 * Sets the saved search
	 * 
	 * @param gWTParams The params
	 */
	public void setSavedSearch(GWTQueryParams gWTParams) {
		
		boolean advancedSearchFlag = false;
		if (gWTParams.getPath().startsWith(Main.get().repositoryContext.getContextTaxonomy())) {
			context.setSelectedIndex(PanelDefinition.NAVIGATOR_TAXONOMY);
		} else if (gWTParams.getPath().startsWith(Main.get().repositoryContext.getContextPersonal())) {
			context.setSelectedIndex(PanelDefinition.NAVIGATOR_PERSONAL);
		} else if (gWTParams.getPath().startsWith(Main.get().repositoryContext.getContextTemplates())) {
			context.setSelectedIndex(PanelDefinition.NAVIGATOR_PERSONAL);
		} else if (gWTParams.getPath().startsWith(Main.get().repositoryContext.getContextMail())) {
			context.setSelectedIndex(PanelDefinition.NAVIGATOR_MAIL);
		} else if (gWTParams.getPath().startsWith(Main.get().repositoryContext.getContextTrash())) {
			context.setSelectedIndex(PanelDefinition.NAVIGATOR_TRASH);
		} else {
			//context.setSelectedIndex(PanelDefinition.NAVIGATOR_ALL_CONTEXT);
			context.setSelectedIndex(PanelDefinition.NAVIGATOR_TAXONOMY);
		}
		
		// Detecting if user has setting some folder path filter or there's only a context one
		if (!gWTParams.getPath().equals(Main.get().repositoryContext.getContextTaxonomy()) && 
			!gWTParams.getPath().equals(Main.get().repositoryContext.getContextPersonal()) &&
			!gWTParams.getPath().equals(Main.get().repositoryContext.getContextTemplates()) && 
			!gWTParams.getPath().equals(Main.get().repositoryContext.getContextMail()) &&
			!gWTParams.getPath().equals(Main.get().repositoryContext.getContextTrash())) {
			path.setText(gWTParams.getPath());
		} else {
			path.setText("");
		}
		
		content.setText(gWTParams.getContent());
		name.setText(gWTParams.getName());
		keywords.setText(gWTParams.getKeywords());
		dashboard.setChecked(gWTParams.isDashboard());
		
		from.setText(gWTParams.getFrom());
		to.setText(gWTParams.getTo());
		subject.setText(gWTParams.getSubject());
		
		if (gWTParams.getOperator().equals(GWTQueryParams.OPERATOR_AND)) {
			searchTypeAnd.setChecked(true);
			searchTypeOr.setChecked(false);
		} else {
			searchTypeAnd.setChecked(false);
			searchTypeOr.setChecked(true);
		}
		
		// Document type
		if ((gWTParams.getDomain() & GWTQueryParams.DOCUMENT) != 0) {
			typeDocument.setChecked(true);
		} else {
			typeDocument.setChecked(false);
		}
		if ((gWTParams.getDomain() & GWTQueryParams.FOLDER) != 0) {
			typeFolder.setChecked(true);
		} else {
			typeFolder.setChecked(false);
		}
		if ((gWTParams.getDomain() & GWTQueryParams.MAIL) != 0) {
			typeMail.setChecked(true);
			tableMail.setVisible(true);
		} else {
			typeMail.setChecked(false);
			tableMail.setVisible(false);
		}
		
		mimeTypes.setSelectedIndex(0);
		// TODO: on api mime must not return null, this must be revised
		if (gWTParams.getMimeType()!= null && !gWTParams.getMimeType().equals("")) {
			for (int i=0; i<mimeTypes.getItemCount(); i++) {
				if (mimeTypes.getValue(i).equals(gWTParams.getMimeType())) {
					mimeTypes.setSelectedIndex(i);
					advancedSearchFlag = true;
				}
			}
			
		}
		
		userListBox.setSelectedIndex(0);
		if (gWTParams.getAuthor()!=null && !gWTParams.getAuthor().equals("")) {
			for (int i=0; i<userListBox.getItemCount(); i++) {
				if (userListBox.getValue(i).equals(gWTParams.getAuthor())) {
					userListBox.setSelectedIndex(i);
					advancedSearchFlag = true;
				}
			}
		}
		
		if (gWTParams.getLastModifiedFrom()!=null) {
			modifyDateFrom = gWTParams.getLastModifiedFrom();
			DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
			startDate.setText(dtf.format(modifyDateFrom));
			advancedSearchFlag = true;
		} else {
			modifyDateFrom = null;
			startDate.setText("");
		}
		
		if (gWTParams.getLastModifiedTo()!=null) {
			modifyDateTo = gWTParams.getLastModifiedTo();
			DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
			endDate.setText(dtf.format(modifyDateTo));
			advancedSearchFlag = true;
		} else {
			modifyDateTo = null;
			endDate.setText("");
		}
		
		removeTablePropertiesRows();
		setTableProperties(gWTParams.getProperties());
		evaluateSearchButtonVisible();
		enableAdvancedSearch(advancedSearchFlag);
		resizeScreenToAdvancedMode(advancedSearchFlag);
		advancedSearch.setChecked(advancedSearchFlag);
		executeSearch();
	}
	
	/**
	 * Remove al table properties rows
	 */
	private void removeTablePropertiesRows(){
		hWidgetProperties.clear();
		while (tableProperties.getRowCount()>0) {
			tableProperties.removeRow(0);
		}
	}
	
	/**
	 * Sets the table properties
	 * 
	 * @param hproperties The table properties map
	 */
	private void setTableProperties(HashMap hproperties) {
		GWTPropertyParams gWTPropertyParams;
		Collection<String> properties = hproperties.keySet();
		
		for (Iterator<String> it = properties.iterator(); it.hasNext(); ) {
			String key = it.next();
			gWTPropertyParams = (GWTPropertyParams) hproperties.get(key);
			addProperty(gWTPropertyParams.getGrpName(), key, gWTPropertyParams.getMetaData(), gWTPropertyParams.getValue());
		}
	}
	
	/**
	 * Sets the context values
	 * 
	 * @param contextValue The context value
	 * @param stackView The stack view
	 */
	public void setContextValue(String contextValue, int stackView){
		switch (stackView) {
		 	case PanelDefinition.NAVIGATOR_TAXONOMY:
		 		context.setValue(PanelDefinition.NAVIGATOR_TAXONOMY,contextValue);
		 		break;
		 	
		 	case PanelDefinition.NAVIGATOR_TEMPLATES:
		 		context.setValue(PanelDefinition.NAVIGATOR_TEMPLATES,contextValue);
		 		break;
		 		
		 	case PanelDefinition.NAVIGATOR_PERSONAL:
		 		context.setValue(PanelDefinition.NAVIGATOR_PERSONAL,contextValue);
		 		break;
		 		
		 	case PanelDefinition.NAVIGATOR_MAIL:
		 		context.setValue(PanelDefinition.NAVIGATOR_MAIL,contextValue);
		 		break;
		 		
		 	case PanelDefinition.NAVIGATOR_TRASH:
		 		context.setValue(PanelDefinition.NAVIGATOR_TRASH,contextValue);
		 		break;
		}
	}
	
	/**
	 * Enables advanced search
	 * 
	 * @param enable
	 */
	private void enableAdvancedSearch(boolean enable) {
		for (ListIterator<Integer> it = advancedSearchIndexList.listIterator(); it.hasNext();) {
			int index = it.next().intValue();
			for (int i=0; i<2; i++) {
				table.getCellFormatter().setVisible(index,i,enable);
			}
		}
	}
	
	/**
	 * Call back get all users
	 */
	final AsyncCallback<List<String>> callbackGetAllUsers = new AsyncCallback<List<String>>() {
		public void onSuccess(List<String> result) {
			List<String> users = (List<String>) result;
			
			userListBox.addItem("",""); // Add first value empty
			
			for (Iterator<String> it = users.iterator(); it.hasNext(); ) {
				String userName = it.next();
				userListBox.addItem(userName, userName);
			}
			
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetGrantedUsers", caught);
		}
	};
	
	/**
	 * Gets all users
	 */
	public void getAllUsers() {
		ServiceDefTarget endPoint = (ServiceDefTarget) authService;
		endPoint.setServiceEntryPoint(Config.OKMAuthService);	
		authService.getAllUsers(callbackGetAllUsers);
	}
	
	/**
	 * Resize the screen to advanced mode or not
	 * 
	 * @param resize Boolean value indicates resize to advanced mode or not
	 */
	private void resizeScreenToAdvancedMode(boolean resize) {
		if (resize) {
			Main.get().mainPanel.horizontalResize(378);
		} else {
			Main.get().mainPanel.horizontalResize(294);
		}
	}
	
	/**
	 * Call Back save search 
	 */
	final AsyncCallback<Object> callbackSaveSearch = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {
			if (userNews) {
				Main.get().mainPanel.historySearch.userNews.addRow(searchSavedName.getText());
				Main.get().mainPanel.historySearch.stackPanel.showStack(PanelDefinition.SEARCH_USER_NEWS);
				Main.get().mainPanel.dashboard.newsDashboard.getUserSearchs(true);
			} else {
				Main.get().mainPanel.historySearch.searchSaved.addRow(searchSavedName.getText());
				Main.get().mainPanel.historySearch.stackPanel.showStack(PanelDefinition.SEARCH_SAVED);
			}

			status.unsetFlag_saveSearch();
		}
		
		public void onFailure(Throwable caught) {
			status.unsetFlag_saveSearch();
			Main.get().showError("SaveSearch", caught);
		}
	};
	
	/**
	 * Save a search
	 */
	public void saveSearch(GWTQueryParams params, String type, String name) {
		status.setFlag_saveSearch();
		ServiceDefTarget endPoint = (ServiceDefTarget) searchService;
		endPoint.setServiceEntryPoint(Config.OKMSearchService);
		searchService.saveSearch(params, type, name, callbackSaveSearch);
	}
}