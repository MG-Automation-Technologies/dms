/**
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

package com.openkm.frontend.client.widget.searchin;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTQueryParams;
import com.openkm.frontend.client.contants.service.RPCService;
import com.openkm.frontend.client.contants.ui.UISearchConstants;
import com.openkm.frontend.client.service.OKMSearchService;
import com.openkm.frontend.client.service.OKMSearchServiceAsync;

/**
 * SearchControl
 * 
 * @author jllort
 *
 */
public class SearchControl extends Composite {
	
	private final OKMSearchServiceAsync searchService = (OKMSearchServiceAsync) GWT.create(OKMSearchService.class);
	
	private static final int MIN_WORD_LENGTH = 3;
	
	private ScrollPanel scrollPanel;
	private FlexTable table;
	public Button searchButton;
	private Button saveSearchButton;
	private Button cleanButton;
	private TextBox searchSavedName;
	private GWTQueryParams params;
	public KeyPressHandler keyPressHandler;
	public KeyUpHandler keyUpHandler;
	private boolean userNews = false;
	public ControlSearchIn controlSearch;
	private ListBox resultPage;
	HorizontalPanel searchTypePanel;
	public final CheckBox searchTypeAnd;
	public final CheckBox searchTypeOr;
	public CheckBox dashboard;
	private HTML saveUserNewsText;
	private HTML resultsPageText;
	private HTML searchTypeText;
	
	/**
	 * SearchControl
	 */
	public SearchControl() {
		table = new FlexTable();
		table.setCellPadding(2);
		table.setCellSpacing(2);
		scrollPanel = new ScrollPanel(table);
		dashboard = new CheckBox();
		searchSavedName = new TextBox();
		searchSavedName.setWidth("200");
		controlSearch = new ControlSearchIn();
		resultPage = new ListBox();
		resultPage.addItem("10", "10");
		resultPage.addItem("20", "20");
		resultPage.addItem("30", "30");
		resultPage.addItem("50", "50");
		resultPage.addItem("100", "100");
		
		keyPressHandler = new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if ((char)KeyCodes.KEY_ENTER == event.getCharCode() && searchButton.isEnabled()) {
					executeSearch();
				}
			}
		};
		
		keyUpHandler = new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				evaluateSearchButtonVisible();
			}
		};
		
		searchButton = new Button(Main.i18n("button.search"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				executeSearch();
			}
		});
		
		cleanButton = new Button(Main.i18n("button.clean"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				SearchNormal searchNormal = Main.get().mainPanel.search.searchBrowser.searchIn.searchNormal;
				SearchAdvanced searchAdvanced = Main.get().mainPanel.search.searchBrowser.searchIn.searchAdvanced;
				searchNormal.context.setSelectedIndex(Main.get().mainPanel.search.searchBrowser.searchIn.posTaxonomy);
				searchNormal.content.setText("");
				searchAdvanced.path.setText("");
				searchAdvanced.categoryPath.setText("");
				searchAdvanced.categoryUuid = "";
				searchNormal.name.setText("");
				searchNormal.keywords.setText("");
				searchSavedName.setText("");
				searchButton.setEnabled(false);
				saveSearchButton.setEnabled(false);
				controlSearch.setVisible(false);
				Main.get().mainPanel.search.searchBrowser.searchIn.removeTablePropertiesRows();
				searchAdvanced.typeDocument.setValue(true);
				searchAdvanced.typeFolder.setValue(false);
				searchAdvanced.typeMail.setValue(false);
				searchAdvanced.mimeTypes.setSelectedIndex(0);
				searchNormal.userListBox.setSelectedIndex(0);
				searchNormal.startDate.setText("");
				searchNormal.endDate.setText("");
				searchNormal.modifyDateFrom = null;
				searchNormal.modifyDateTo = null;
				searchAdvanced.from.setText("");
				searchAdvanced.to.setText("");
				searchAdvanced.subject.setText("");
				Main.get().mainPanel.search.searchBrowser.searchResult.removeAllRows();
			}
		});
		
		saveSearchButton= new Button(Main.i18n("button.save.search"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				long domain = 0;
				SearchNormal searchNormal = Main.get().mainPanel.search.searchBrowser.searchIn.searchNormal;
				SearchAdvanced searchAdvanced = Main.get().mainPanel.search.searchBrowser.searchIn.searchAdvanced;
				String operator = GWTQueryParams.OPERATOR_AND;
				params = new GWTQueryParams();
				if (!searchAdvanced.path.getText().equals("")) {
					params.setPath(searchAdvanced.path.getText());
				} else {
					params.setPath(searchNormal.context.getValue(searchNormal.context.getSelectedIndex()));
				}
				
				if (!searchAdvanced.categoryUuid.equals("")) {
					params.setCategoryUuid(searchAdvanced.categoryUuid);
					params.setCategoryPath(searchAdvanced.categoryPath.getText().substring(16)); // removes /okm:category 
				}
				
				params.setContent(searchNormal.content.getText());
				params.setName(searchNormal.name.getText());
				params.setKeywords(searchNormal.keywords.getText());
				params.setProperties(Main.get().mainPanel.search.searchBrowser.searchIn.getProperties());
				params.setAuthor(searchNormal.userListBox.getValue(searchNormal.userListBox.getSelectedIndex()));
				params.setLastModifiedFrom(searchNormal.modifyDateFrom);
				params.setLastModifiedTo(searchNormal.modifyDateTo);
				params.setDashboard(dashboard.getValue());
				params.setMailFrom(searchAdvanced.from.getText());
				params.setMailTo(searchAdvanced.to.getText());
				params.setMailSubject(searchAdvanced.subject.getText());
				
				if (searchAdvanced.typeDocument.getValue()) {
					domain += GWTQueryParams.DOCUMENT;
				}
				if (searchAdvanced.typeFolder.getValue()) {
					domain += GWTQueryParams.FOLDER;
				}
				if (searchAdvanced.typeMail.getValue()) {
					domain += GWTQueryParams.MAIL;
				}
				params.setDomain(domain);
				
				if (searchTypeAnd.getValue()) {
					operator = GWTQueryParams.OPERATOR_AND;
				} else {
					operator = GWTQueryParams.OPERATOR_OR;
				}
				params.setOperator(operator);
				
				// Removes dates if dashboard is checked
				if (dashboard.getValue()) {
					params.setLastModifiedFrom(null);
					params.setLastModifiedTo(null);
				}
				
				params.setMimeType(searchAdvanced.mimeTypes.getValue(searchAdvanced.mimeTypes.getSelectedIndex()));
				
				if (!searchSavedName.getText().equals("")) {
					saveSearchButton.setEnabled(false);
					params.setQueryName(searchSavedName.getText());
					userNews = params.isDashboard();
					saveSearch(params,"sql");
				}
			}
		});
		
		searchSavedName.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				evalueSaveSearchButtonVisible();
			}
		});
		
		searchButton.setEnabled(false);
		saveSearchButton.setEnabled(false);
		
		// Type of search
		searchTypePanel = new HorizontalPanel();
		searchTypePanel.setVisible(true);  // On OpenKM 4.0 has hidden AND / OR option list
		searchTypeAnd = new CheckBox("AND");
		searchTypeOr = new CheckBox("OR");
		searchTypeAnd.setValue(true);
		searchTypeOr.setValue(false);
		
		searchTypeAnd.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				searchTypeOr.setValue(!searchTypeAnd.getValue()); // Always set changed between and and or type
			}
		});
		
		searchTypeOr.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				searchTypeAnd.setValue(!searchTypeOr.getValue()); // Always set changed between and and or type
			}
		});
		
		HTML space1 = new HTML("");
		searchTypePanel.add(searchTypeAnd);
		searchTypePanel.add(space1);
		searchTypePanel.add(searchTypeOr);
		searchTypePanel.setCellWidth(space1, "10");
		
		saveUserNewsText = new HTML(Main.i18n("search.save.as.news"));
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(dashboard);
		hPanel.add(new HTML("&nbsp;"));
		hPanel.add(saveUserNewsText);
		hPanel.setCellVerticalAlignment(dashboard, HasAlignment.ALIGN_MIDDLE);
		hPanel.setCellVerticalAlignment(saveUserNewsText, HasAlignment.ALIGN_MIDDLE);
		table.setWidget(0, 0, hPanel);
		
		table.setWidget(1, 0, saveSearchButton);
		table.setWidget(1, 1, searchSavedName);
		
		resultsPageText = new HTML(Main.i18n("search.page.results"));
		table.setWidget(2, 0, resultsPageText);
		table.setWidget(2, 1, resultPage);
		
		searchTypeText = new HTML(Main.i18n("search.type"));
		table.setHTML(3, 0, Main.i18n("search.type"));
		table.setWidget(3, 1, searchTypePanel);
		
		table.setWidget(4, 0, cleanButton);
		table.setWidget(4, 1, searchButton);
		
		table.setWidget(5, 0, controlSearch);
		
		table.getCellFormatter().setHorizontalAlignment(2, 0, HasAlignment.ALIGN_RIGHT);
		table.getCellFormatter().setHorizontalAlignment(3, 0, HasAlignment.ALIGN_RIGHT);
		table.getCellFormatter().setHorizontalAlignment(4, 0, HasAlignment.ALIGN_RIGHT);
		table.getFlexCellFormatter().setColSpan(0, 0, 2);
		table.getFlexCellFormatter().setColSpan(5, 0, 2);
		
		searchButton.setStyleName("okm-Button");
		saveSearchButton.setStyleName("okm-Button");
		saveSearchButton.addStyleName("okm-NoWrap");
		cleanButton.setStyleName("okm-Button");
		searchSavedName.setStyleName("okm-Input");
		resultPage.setStyleName("okm-Input");
		
		initWidget(scrollPanel);
	}
	
	/**
	 * Executes the search
	 */
	public void executeSearch() {
		long domain = 0;
		SearchNormal searchNormal = Main.get().mainPanel.search.searchBrowser.searchIn.searchNormal;
		SearchAdvanced searchAdvanced = Main.get().mainPanel.search.searchBrowser.searchIn.searchAdvanced;
		GWTQueryParams gwtParams = new GWTQueryParams();
		gwtParams.setContent(searchNormal.content.getText());
		if (!searchAdvanced.path.getText().equals("")) {
			gwtParams.setPath(searchAdvanced.path.getText());
		} else {
			gwtParams.setPath(searchNormal.context.getValue(searchNormal.context.getSelectedIndex()));
		}
		if (!searchAdvanced.categoryUuid.equals("")) {
			gwtParams.setCategoryUuid(searchAdvanced.categoryUuid);
		}
		gwtParams.setKeywords(searchNormal.keywords.getText());
		gwtParams.setMimeType("");
		gwtParams.setName(searchNormal.name.getText());
		gwtParams.setAuthor(searchNormal.userListBox.getValue(searchNormal.userListBox.getSelectedIndex()));
		
		gwtParams.setMailFrom(searchAdvanced.from.getText());
		gwtParams.setMailTo(searchAdvanced.to.getText());
		gwtParams.setMailSubject(searchAdvanced.subject.getText());
		
		if (searchTypeAnd.getValue()) {
			gwtParams.setOperator(GWTQueryParams.OPERATOR_AND);
		} else {
			gwtParams.setOperator(GWTQueryParams.OPERATOR_OR);
		}
		
		if (searchNormal.modifyDateFrom!=null && searchNormal.modifyDateTo!=null) {
			gwtParams.setLastModifiedFrom(searchNormal.modifyDateFrom);
			gwtParams.setLastModifiedTo(searchNormal.modifyDateTo);
		} else {
			gwtParams.setLastModifiedFrom(null);
			gwtParams.setLastModifiedTo(null);
		}
		
		if (searchAdvanced.typeDocument.getValue()) {
			domain += GWTQueryParams.DOCUMENT;
		}
		if (searchAdvanced.typeFolder.getValue()) {
			domain += GWTQueryParams.FOLDER;
		}
		if (searchAdvanced.typeMail.getValue()) {
			domain += GWTQueryParams.MAIL;
		}
		gwtParams.setDomain(domain);
		
		gwtParams.setProperties(Main.get().mainPanel.search.searchBrowser.searchIn.getProperties());
		gwtParams.setMimeType(searchAdvanced.mimeTypes.getValue(searchAdvanced.mimeTypes.getSelectedIndex()));
		Main.get().mainPanel.search.searchBrowser.searchIn.futuramaWalking.evaluate(searchNormal.content.getText());
		controlSearch.executeSearch(gwtParams, Integer.parseInt(resultPage.getItemText(resultPage.getSelectedIndex())));
	}
	
	/**
	 * Evalues seach button visibility
	 */
	public void evaluateSearchButtonVisible() {
		SearchNormal searchNormal = Main.get().mainPanel.search.searchBrowser.searchIn.searchNormal;
		SearchAdvanced searchAdvanced = Main.get().mainPanel.search.searchBrowser.searchIn.searchAdvanced;
		SearchMetadata searchMetadata = Main.get().mainPanel.search.searchBrowser.searchIn.searchMetadata;
		if (searchNormal.content.getText().length() >= MIN_WORD_LENGTH || searchNormal.name.getText().length() >= MIN_WORD_LENGTH ||
			searchNormal.keywords.getText().length() >= MIN_WORD_LENGTH || searchAdvanced.from.getText().length() >= MIN_WORD_LENGTH ||
			searchAdvanced.to.getText().length() >= MIN_WORD_LENGTH || searchAdvanced.subject.getText().length() >= MIN_WORD_LENGTH) {
			searchButton.setEnabled(true);
		} else {
			searchButton.setEnabled(false);
		}
		
		// Evaluates Mime Types
		if (searchAdvanced.mimeTypes.getSelectedIndex()>0) {
			searchButton.setEnabled(true);
		}
		
		// Evaluates user list
		if (searchNormal.userListBox.getSelectedIndex()>0) {
			searchButton.setEnabled(true);
		}
		
		// Evaluates date range
		if (searchNormal.modifyDateFrom!=null && searchNormal.modifyDateTo!=null) {
			searchButton.setEnabled(true);
		}
		
		// Evaluates properties to enable button		
		for (Iterator<String> it = searchMetadata.hWidgetProperties.keySet().iterator(); it.hasNext();){
			String key = it.next();
			Object widget = searchMetadata.hWidgetProperties.get(key);
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
	 * langRefresh
	 */
	public void langRefresh() {
		searchButton.setHTML(Main.i18n("button.search"));
		cleanButton.setHTML(Main.i18n("button.clean"));
		saveSearchButton.setHTML(Main.i18n("button.save.search"));
		saveUserNewsText.setHTML(Main.i18n("search.save.as.news"));
		resultsPageText.setHTML(Main.i18n("search.page.results"));
		searchTypeText.setHTML(Main.i18n("search.type"));
		controlSearch.langRefresh();
		
	}
	
	/**
	 * Save a search
	 */
	public void saveSearch(GWTQueryParams params, String type) {
		Main.get().mainPanel.search.searchBrowser.searchIn.status.setFlag_saveSearch();
		ServiceDefTarget endPoint = (ServiceDefTarget) searchService;
		endPoint.setServiceEntryPoint(RPCService.SearchService);
		searchService.saveSearch(params, type, callbackSaveSearch);
		
	}	
	
	/**
	 * Call Back save search 
	 */
	final AsyncCallback<Integer> callbackSaveSearch = new AsyncCallback<Integer>() {
		public void onSuccess(Integer result) {
			params.setId(result.intValue());
			if (userNews) {
				Main.get().mainPanel.search.historySearch.userNews.addNewSavedSearch(params);
				Main.get().mainPanel.search.historySearch.stackPanel.showStack(UISearchConstants.SEARCH_USER_NEWS);
				Main.get().mainPanel.dashboard.newsDashboard.getUserSearchs(true);
			} else {
				Main.get().mainPanel.search.historySearch.searchSaved.addNewSavedSearch(params);
				Main.get().mainPanel.search.historySearch.stackPanel.showStack(UISearchConstants.SEARCH_SAVED);
			}
			
			searchSavedName.setText(""); // Clean name atfer saved
			Main.get().mainPanel.search.searchBrowser.searchIn.status.unsetFlag_saveSearch();
		}
		
		public void onFailure(Throwable caught) {
			Main.get().mainPanel.search.searchBrowser.searchIn.status.unsetFlag_saveSearch();
			Main.get().showError("SaveSearch", caught);
		}
	};
}