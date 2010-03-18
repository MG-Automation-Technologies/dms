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

package es.git.openkm.backend.client.widget.advancedsearch;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.backend.client.Main;
import es.git.openkm.backend.client.config.Config;
import es.git.openkm.backend.client.service.OKMSearchService;
import es.git.openkm.backend.client.service.OKMSearchServiceAsync;

/**
 * AdvancedSearchQuery
 * 
 * @author jllort
 *
 */
public class AdvancedSearchQuery extends Composite {
	private final OKMSearchServiceAsync searchService = (OKMSearchServiceAsync) GWT.create(OKMSearchService.class);
	
	private static final String PREDEFINED_QUERY_LOCKED_DOCUMENTS = "/jcr:root/okm:root//element(*,okm:document)[@jcr:lockOwner]/@jcr:lockOwner";
	private static final String PREDEFINED_DOC_CREATED_ADMIN = "/jcr:root/okm:root//element(*, okm:document)[@okm:author='okmAdmin']/(@jcr:lockOwner|@jcr:created)";
	private static final String PREDEFINED_DOC_WITH_PG_TEST = "/jcr:root/okm:root//element(*, okg:test)";
	private static final String PREDEFINED_DOC_OBSERVED = "/jcr:root/okm:root//element(*, mix:notification)";
	
	private FlexTable table;
	private ListBox typeList;
	private TextArea sqlTextArea;
	private VerticalPanel sp;
	private Button searchButton;
	private Button cleanButton;
	private ListBox predefinedQueryList;
	private HorizontalPanel vPanel;
	private HorizontalPanel buttonPanel;
	private HTML predefinedHTMLText;
	
	/**
	 * AdvancedSearchQuery
	 */
	public AdvancedSearchQuery() {
		sp = new VerticalPanel();
		table = new FlexTable();
		typeList = new ListBox();
		predefinedQueryList = new ListBox();
		sqlTextArea = new TextArea();
		vPanel = new HorizontalPanel();
		buttonPanel = new HorizontalPanel();
		searchButton = new Button(Main.i18n("button.search"), new ClickListener() {
			public void onClick(Widget sender) {
				if (!sqlTextArea.getText().equals("")) {
					getSearch();
				}
			}	
		});
		
		cleanButton = new Button(Main.i18n("button.clean"), new ClickListener() {
			public void onClick(Widget sender) {
				typeList.setSelectedIndex(0); // Selects XPath query
				predefinedQueryList.setSelectedIndex(0);
				sqlTextArea.setText("");
				Main.get().centerPanel.advancedSearchPanel.resultsSearch.removeAllRows();
			}	
		});
		
		typeList.addItem("XPath","xpath");
		typeList.addItem("SQL","sql");
		
		predefinedQueryList.addItem("-");
		predefinedQueryList.addItem(Main.i18n("advanced.search.predefined.locked.documents"));
		predefinedQueryList.addItem(Main.i18n("advanced.search.predefined.documents.created.by.admin"));
		predefinedQueryList.addItem(Main.i18n("advanced.search.predefined.documents.with.property.group.test"));
		predefinedQueryList.addItem(Main.i18n("advanced.search.predefined.documents.observed"));
		
		predefinedQueryList.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				if (predefinedQueryList.getSelectedIndex()>0) {
					switch (predefinedQueryList.getSelectedIndex()) {
						case 1:
							sqlTextArea.setText(PREDEFINED_QUERY_LOCKED_DOCUMENTS);
							typeList.setSelectedIndex(0); // Selects Xpath query
							break;
						
						case 2:
							sqlTextArea.setText(PREDEFINED_DOC_CREATED_ADMIN);
							typeList.setSelectedIndex(0); // Selects Xpath query
							break;
						
						case 3:
							sqlTextArea.setText(PREDEFINED_DOC_WITH_PG_TEST);
							typeList.setSelectedIndex(0); // Selects Xpath query
							break;
							
						case 4:
							sqlTextArea.setText(PREDEFINED_DOC_OBSERVED);
							typeList.setSelectedIndex(0); // Selects Xpath query
							break;
					}
				}
			}
		});
		
		predefinedHTMLText = new HTML(Main.i18n("advanced.search.predefined"));
		vPanel.add(typeList);
		vPanel.add(new HTML("&nbsp;&nbsp;"));
		vPanel.add(predefinedHTMLText);
		vPanel.add(new HTML("&nbsp;&nbsp;"));
		vPanel.add(predefinedQueryList);
		
		vPanel.setCellVerticalAlignment(predefinedHTMLText, HasAlignment.ALIGN_MIDDLE);
		
		buttonPanel.add(searchButton);
		buttonPanel.add(new HTML("&nbsp;&nbsp;"));
		buttonPanel.add(cleanButton);
		
		table.setHTML(0,0,Main.i18n("advanced.search.type"));
		table.setHTML(1,0,Main.i18n("advanced.search.query"));
		table.setWidget(0,1,vPanel);
		table.setWidget(1,1,sqlTextArea);
		table.setHTML(2,0,"");
		table.setWidget(2,1,buttonPanel);
		
		table.setWidth("100%");
		table.getCellFormatter().setWidth(0,0,"50");
		table.getCellFormatter().setVerticalAlignment(1, 0, HasAlignment.ALIGN_TOP);
		sqlTextArea.setWidth("100%");
		sqlTextArea.setHeight("120");
		sp.setSize("100%","100%");
		
		typeList.setStyleName("okm-Input");
		predefinedQueryList.setStyleName("okm-Input");
		sqlTextArea.setStyleName("okm-Input");
		searchButton.setStyleName("okm-Input");
		cleanButton.setStyleName("okm-Input");
		
		sp.add(table);
		
		initWidget(sp);
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		table.setHTML(0,0,Main.i18n("advanced.search.type"));
		table.setHTML(1,0,Main.i18n("advanced.search.query"));
		searchButton.setText(Main.i18n("button.search"));
		
		// Removes all items before inserting translated
		while(predefinedQueryList.getItemCount()>0) {
			predefinedQueryList.removeItem(0);
		}
		
		predefinedQueryList.addItem("-");
		predefinedQueryList.addItem(Main.i18n("advanced.search.predefined.locked.documents"));
		predefinedQueryList.addItem(Main.i18n("advanced.search.predefined.documents.created.by.system"));
		predefinedQueryList.addItem(Main.i18n("advanced.search.predefined.documents.with.property.group.test"));
		predefinedQueryList.addItem(Main.i18n("advanced.search.predefined.documents.observed"));
	}
	
	/**
	 * Call back get search results
	 */
	final AsyncCallback callBackGetSearch = new AsyncCallback() {
		public void onSuccess(Object result) {
			List results = (List) result;
			boolean title = true;
			Main.get().centerPanel.advancedSearchPanel.resultsSearch.removeAllRows();
			for (Iterator it = results.iterator(); it.hasNext();) {
				Main.get().centerPanel.advancedSearchPanel.resultsSearch.addRow((Vector) it.next(), title);
				title = false;
			}
			Main.get().centerPanel.advancedSearchPanel.resultsSearch.status.unsetFlag_search();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("callBackGetSearch", caught);
			Main.get().centerPanel.advancedSearchPanel.resultsSearch.status.unsetFlag_search();
		}
	};
	
	/**
	 * getSearch
	 */
	public void getSearch() {
		Main.get().centerPanel.advancedSearchPanel.resultsSearch.status.setFlag_search();
		Main.get().centerPanel.advancedSearchPanel.resultsSearch.status.refresh(Main.get().centerPanel.advancedSearchPanel.resultsSearch);
		ServiceDefTarget endPoint = (ServiceDefTarget) searchService;
		endPoint.setServiceEntryPoint(Config.OKMSearchService);	
		searchService.getSearch(sqlTextArea.getText(),typeList.getValue(typeList.getSelectedIndex()),callBackGetSearch);
	}
}