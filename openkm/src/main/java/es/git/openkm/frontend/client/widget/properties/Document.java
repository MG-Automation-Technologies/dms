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

package es.git.openkm.frontend.client.widget.properties;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.bean.GWTKeyword;
import es.git.openkm.frontend.client.bean.GWTPermission;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMDocumentService;
import es.git.openkm.frontend.client.service.OKMDocumentServiceAsync;
import es.git.openkm.frontend.client.util.Util;
import es.git.openkm.frontend.client.widget.dashboard.ImageHover;

/**
 * Document
 * 
 * @author jllort
 *
 */
public class Document extends Composite {
	private final OKMDocumentServiceAsync documentService = (OKMDocumentServiceAsync) GWT.create(OKMDocumentService.class);
	private FlexTable tableProperties;
	private FlexTable tableSubscribedUsers;
	private FlexTable table;
	private GWTDocument document;
	private Button copyUrlToClipBoard;
	private Button copyWebdavToClipBoard;
	private HorizontalPanel keywordPanel;
	private SimplePanel sp;
	private ScrollPanel scrollPanel;
	private SuggestBox suggestKey;
	private MultiWordSuggestOracle multiWordkSuggestKey; 
	private FlowPanel hKeyPanel;
	private Map<String,Widget> keywordMap;
	//private TagCloud keywordsCloud;
	private boolean visible = true;
	private HTML subcribedUsersText;
	//private HTML keywordsCloudText;
	
	public Document() {
		keywordMap = new HashMap<String,Widget>();
		document = new GWTDocument();
		table = new FlexTable();
		tableProperties = new FlexTable();
		tableSubscribedUsers = new FlexTable();
		//keywordsCloud = new TagCloud();
		scrollPanel = new ScrollPanel(table);
		keywordPanel = new HorizontalPanel();
		sp = new SimplePanel();
		sp.setWidth("16px");

		copyUrlToClipBoard = new Button(Main.i18n("button.copy.clipboard"), new ClickListener() {
			public void onClick(Widget sender) {
				String url = Main.get().workspaceUserProperties.getApplicationURL();
				url += "?docPath=" + URL.encodeComponent(document.getPath());
				Util.copyToClipboard(url);
			}
		});

		copyWebdavToClipBoard = new Button(Main.i18n("button.copy.clipboard"), new ClickListener() {
			public void onClick(Widget sender) {
				String url = Main.get().workspaceUserProperties.getApplicationURL();
				int idx = url.lastIndexOf('/');
				url = url.substring(0, url.lastIndexOf('/', idx-1)) + "/repository/default" + document.getPath();
				Util.copyToClipboard(url);
			}
		});

		multiWordkSuggestKey = new MultiWordSuggestOracle();
		suggestKey = new SuggestBox(multiWordkSuggestKey);
		suggestKey.setHeight("20");
		suggestKey.setText(Main.i18n("dashboard.keyword.suggest"));
		suggestKey.addKeyboardListener(new KeyboardListener() {
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
			}

			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
			}

			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				if (keyCode == (char)KeyboardListener.KEY_ENTER ) {
					boolean remove = ((document.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE 
							          && !document.isCheckedOut() && !document.isLocked());
					Main.get().mainPanel.enableKeyShorcuts(); // Enables general keys applications
					addKey(suggestKey.getText().replaceAll(" ", ""), remove); // Removes all spaces, keywords must have no space
					suggestKey.setText("");
				}
			}
			
		});
		suggestKey.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				if (suggestKey.getText().equals(Main.i18n("dashboard.keyword.suggest"))) {
					suggestKey.setText("");
				}
				Main.get().mainPanel.disableKeyShorcuts(); // Disables key shortcuts while updating
			}			
		});
		
		VerticalPanel vPanel = new VerticalPanel();
		hKeyPanel = new FlowPanel();
		HTML space = new HTML("");
		vPanel.add(suggestKey);
		vPanel.add(space);
		vPanel.add(hKeyPanel);
		
		hKeyPanel.setWidth("250");
		vPanel.setCellHeight(space, "5");
		
		keywordPanel.add(vPanel);
		keywordPanel.add(sp);
		copyUrlToClipBoard.setStyleName("okm-Button");
		copyWebdavToClipBoard.setStyleName("okm-Button");
		
		tableProperties.setHTML(0, 0, "<b>"+Main.i18n("document.uuid")+"</b>");
		tableProperties.setHTML(0, 1, "");
		tableProperties.setHTML(1, 0, "<b>"+Main.i18n("document.name")+"</b>");
		tableProperties.setHTML(1, 1, "");
		tableProperties.setHTML(2, 0, "<b>"+Main.i18n("document.folder")+"</b>");
		tableProperties.setHTML(3, 1, "");
		tableProperties.setHTML(3, 0, "<b>"+Main.i18n("document.size")+"</b>");
		tableProperties.setHTML(4, 1, "");
		tableProperties.setHTML(4, 0, "<b>"+Main.i18n("document.created")+"</b>");
		tableProperties.setHTML(5, 1, "");
		tableProperties.setHTML(5, 0, "<b>"+Main.i18n("document.lastmodified")+"</b>");
		tableProperties.setHTML(5, 1, "");
		tableProperties.setHTML(6, 0, "<b>"+Main.i18n("document.mimetype")+"</b>");
		tableProperties.setHTML(6, 1, "");
		tableProperties.setHTML(7, 0, "<b>"+Main.i18n("document.keywords")+"</b>");
		tableProperties.setHTML(7, 1, "");
		tableProperties.setHTML(8, 0, "<b>"+Main.i18n("document.status")+"</b>");
		tableProperties.setHTML(8, 1, "");
		tableProperties.setHTML(9, 0, "<b>"+Main.i18n("document.subscribed")+"</b>");
		tableProperties.setHTML(9, 1, "");
		tableProperties.setHTML(10, 0, "<b>"+Main.i18n("document.history.size")+"</b>");
		tableProperties.setHTML(10, 1, "");
		tableProperties.setHTML(11, 0, "<b>"+Main.i18n("document.url")+"</b>");
		tableProperties.setWidget(11, 1, copyUrlToClipBoard);
		tableProperties.setHTML(12, 0, "<b>"+Main.i18n("document.webdav")+"</b>");
		tableProperties.setWidget(12, 1, copyWebdavToClipBoard);
		
		tableProperties.getCellFormatter().setVerticalAlignment(7, 0, HasAlignment.ALIGN_TOP);
		
		// Sets the tagcloud
		//keywordsCloud.setWidth("350");
		
		VerticalPanel vPanel2 = new VerticalPanel();
		subcribedUsersText = new HTML("<b>"+Main.i18n("document.subscribed.users")+"<b>");
		//keywordsCloudText = new HTML("<b>"+Main.i18n("document.keywords.cloud")+"</b>");
		vPanel2.add(subcribedUsersText);
		vPanel2.add(tableSubscribedUsers);
		//vPanel2.add(keywordsCloudText);
		//vPanel2.add(keywordsCloud);
		
		table.setWidget(0, 0, tableProperties);
		table.setHTML(0, 1, "");
		table.setWidget(0, 2, vPanel2);
		
		// The hidden column extends table to 100% width
		CellFormatter cellFormatter = table.getCellFormatter();
		cellFormatter.setWidth(0, 1, "25");
		cellFormatter.setVerticalAlignment(0, 0, HasAlignment.ALIGN_TOP);
		cellFormatter.setVerticalAlignment(0, 2, HasAlignment.ALIGN_TOP);
		
		// Sets wordWrap for al rows
		for (int i=0; i<11; i++) {
			setRowWordWarp(i, 0, true, tableProperties);
		}

		setRowWordWarp(0, 0, true, tableSubscribedUsers);
		
		tableProperties.setStyleName("okm-DisableSelect");
		tableSubscribedUsers.setStyleName("okm-DisableSelect");
		suggestKey.setStyleName("okm-KeyMap-Suggest");
		suggestKey.addStyleName("okm-Input");
		hKeyPanel.setStylePrimaryName("okm-cloudWrap");
		//keywordsCloud.setStylePrimaryName("okm-cloudWrap");
		
		initWidget(scrollPanel);
	}
	
	/**
	 * Set the WordWarp for all the row cells
	 * 
	 * @param row The row cell
	 * @param columns Number of row columns
	 * @param warp
	 * @param table The table to change word wrap
	 */
	private void setRowWordWarp(int row, int columns, boolean warp, FlexTable table) {
		CellFormatter cellFormatter = table.getCellFormatter();
		for (int i=0; i<columns; i++) {
			cellFormatter.setWordWrap(row, i, warp);
		}
	}
	
	/**
	 * Sets the document values
	 * 
	 * @param doc The document object
	 */
	public void set(GWTDocument doc) {
		keywordMap = new HashMap<String,Widget>();
		this.document = doc;
		tableProperties.setHTML(0, 1, doc.getUuid());
		tableProperties.setHTML(1, 1, doc.getName());
		tableProperties.setHTML(2, 1, doc.getParentId());
		tableProperties.setHTML(3, 1, Util.formatSize(doc.getActualVersion().getSize()));
		DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
		tableProperties.setHTML(4, 1, dtf.format(doc.getCreated())+" "+Main.i18n("document.by")+" "+doc.getAuthor());
		tableProperties.setHTML(5, 1, dtf.format(doc.getLastModified())+" "+Main.i18n("document.by")+" "+doc.getActualVersion().getAuthor());
		tableProperties.setHTML(6, 1, doc.getMimeType());
		tableProperties.setWidget(7, 1, keywordPanel);
		hKeyPanel.clear();
		
		String keywords[] = doc.getKeywords().split(" ");
		Arrays.sort(keywords);
		boolean remove = ((doc.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE && !doc.isCheckedOut() && !doc.isLocked())
		                 && visible;
		
		for (int i=0; i<keywords.length; i++) {
			// First adds only new keywords
			final String keyword = keywords[i];
			Widget keywordButton = getKeyWidget(keyword, remove);
			keywordMap.put(keyword, keywordButton);
			hKeyPanel.add(keywordButton);
		}
		
		// Reloading keyword list
		multiWordkSuggestKey.clear();
		for (Iterator<GWTKeyword> it = Main.get().mainPanel.dashboard.keyMapDashboard.getAllKeywordList().iterator(); it.hasNext();) {
			multiWordkSuggestKey.add(it.next().getKeyword());
		}
		
		if (doc.isCheckedOut()) {
			tableProperties.setHTML(8, 1, Main.i18n("document.status.checkout")+" "+doc.getLockInfo().getOwner());
		} else if (doc.isLocked()) {
			tableProperties.setHTML(8, 1, Main.i18n("document.status.locked")+" "+doc.getLockInfo().getOwner());
		} else {
			tableProperties.setHTML(8, 1, Main.i18n("document.status.normal"));
		}
		
		if (doc.isSubscribed()) {
			tableProperties.setHTML(9, 1, Main.i18n("document.subscribed.yes"));
		} else {
			tableProperties.setHTML(9, 1, Main.i18n("document.subscribed.no"));
		}
		
		// Enables or disables change keywords with user permissions and document is not check-out or locked
		if (remove)  {
			suggestKey.setVisible(true);
		} else {
			suggestKey.setVisible(false);
		}
		
		getVersionHistorySize();
		
		// Sets wordWrap for al rows
		for (int i=0; i<11; i++) {
			setRowWordWarp(i, 1, true, tableProperties);
		}
		
		// Remove all table rows >= 1
		while (tableSubscribedUsers.getRowCount()>0) {
			tableSubscribedUsers.removeRow(0);
		}
		
		// Sets the folder subscribers
		for (Iterator<String> it= doc.getSubscriptors().iterator(); it.hasNext(); ) {
			tableSubscribedUsers.setHTML(tableSubscribedUsers.getRowCount(), 0, it.next());
			setRowWordWarp(tableSubscribedUsers.getRowCount()-1, 0, true, tableSubscribedUsers);
		}
		
		//drawTagCloud(keywords);
		
		// Some preoperties only must be visible on taxonomy or trash view
		//int actualView = Main.get().mainPanel.navigator.getStackIndex();
		//if (actualView==PanelDefinition.NAVIGATOR_TAXONOMY || actualView==PanelDefinition.NAVIGATOR_TRASH ){
		//	tableProperties.getCellFormatter().setVisible(7,0,true);
		//	tableProperties.getCellFormatter().setVisible(7,1,true);
		//	tableProperties.getCellFormatter().setVisible(9,0,true);
		//	tableProperties.getCellFormatter().setVisible(9,1,true);
		//} else {
		//	tableProperties.getCellFormatter().setVisible(7,0,false);
		//	tableProperties.getCellFormatter().setVisible(7,1,false);
		//	tableProperties.getCellFormatter().setVisible(9,0,false);
		//	tableProperties.getCellFormatter().setVisible(9,1,false);
		//}

	}
	
	/**
	 * Lang refresh
	 */
	public void langRefresh() {
		tableProperties.setHTML(0, 0, "<b>"+Main.i18n("document.uuid")+"</b>");
		tableProperties.setHTML(1, 0, "<b>"+Main.i18n("document.name")+"</b>");
		tableProperties.setHTML(2, 0, "<b>"+Main.i18n("document.folder")+"</b>");
		tableProperties.setHTML(3, 0, "<b>"+Main.i18n("document.size")+"</b>");
		tableProperties.setHTML(4, 0, "<b>"+Main.i18n("document.created")+"</b>");
		tableProperties.setHTML(5, 0, "<b>"+Main.i18n("document.lastmodified")+"</b>");
		tableProperties.setHTML(6, 0, "<b>"+Main.i18n("document.mimetype")+"</b>");
		tableProperties.setHTML(7, 0, "<b>"+Main.i18n("document.keywords")+"</b>");
		tableProperties.setHTML(8, 0, "<b>"+Main.i18n("document.status")+"</b>");
		tableProperties.setHTML(9, 0, "<b>"+Main.i18n("document.subscribed")+"</b>");
		tableProperties.setHTML(10, 0, "<b>"+Main.i18n("document.history.size")+"</b>");
		tableProperties.setHTML(11, 0, "<b>"+Main.i18n("document.url")+"</b>");
		tableProperties.setHTML(12, 0, "<b>"+Main.i18n("document.webdav")+"</b>");
		//keywordsCloudText.setHTML("<b>"+Main.i18n("document.keywords.cloud")+"</b>");
		subcribedUsersText.setHTML("<b>"+Main.i18n("document.subscribed.users")+"<b>");
		
		if (document != null) {
			DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
			if (document.getCreated() != null) {
				tableProperties.setHTML(4, 1, dtf.format(document.getCreated())+" "+Main.i18n("document.by")+" "+document.getAuthor());
			}
			
			if (document.getLastModified() != null) {
				tableProperties.setHTML(5, 1, dtf.format(document.getLastModified())+" "+Main.i18n("document.by")+" "+document.getActualVersion().getAuthor());
			}

			if (document.isCheckedOut()) {
				tableProperties.setHTML(8, 1, Main.i18n("document.status.checkout")+" "+document.getLockInfo().getOwner());
			} else if (document.isLocked()) {
				tableProperties.setHTML(8, 1, Main.i18n("document.status.locked")+" "+document.getLockInfo().getOwner());
			} else {
				tableProperties.setHTML(8, 1, Main.i18n("document.status.normal"));
			}
			
			if (document.isSubscribed()) {
				tableProperties.setHTML(9, 1, Main.i18n("document.subscribed.yes"));
			} else {
				tableProperties.setHTML(9, 1, Main.i18n("document.subscribed.no"));
			}
		}
		
		copyUrlToClipBoard.setHTML(Main.i18n("button.copy.clipboard"));
		copyWebdavToClipBoard.setHTML(Main.i18n("button.copy.clipboard"));
	}	
	
	/**
	 * Callback SetProperties document
	 */
	final AsyncCallback<Object> callbackSetProperties = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {	
			Main.get().mainPanel.browser.tabMultiple.status.unsetSetProperties();
		}

		public void onFailure(Throwable caught) {
			Main.get().mainPanel.browser.tabMultiple.status.unsetSetProperties();
			Main.get().showError("SetProperties", caught);
		}
	};
	
	/**
	 * Callback GetVersionHistorySize document
	 */
	final AsyncCallback<Object> callbackGetVersionHistorySize = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {	
			Long size = (Long) result;
			tableProperties.setHTML(10, 1, Util.formatSize(size.longValue()));
			Main.get().mainPanel.browser.tabMultiple.status.unsetGetVersionHistorySize();
		}

		public void onFailure(Throwable caught) {
			Main.get().mainPanel.browser.tabMultiple.status.unsetGetVersionHistorySize();
			Main.get().showError("GetVersionHistorySize", caught);
		}
	};
	
	/**
	 * SetProperties document
	 */
	public void setProperties() {
		Main.get().mainPanel.browser.tabMultiple.status.setSetProperties();
		ServiceDefTarget endPoint = (ServiceDefTarget) documentService;
		endPoint.setServiceEntryPoint(Config.OKMDocumentService);
		documentService.setProperties(document, callbackSetProperties);
	}
	
	/**
	 * getVersionHistorySize document
	 */
	public void getVersionHistorySize() {
		Main.get().mainPanel.browser.tabMultiple.status.setGetVersionHistorySize();
		ServiceDefTarget endPoint = (ServiceDefTarget) documentService;
		endPoint.setServiceEntryPoint(Config.OKMDocumentService);
		documentService.getVersionHistorySize(document.getPath(), callbackGetVersionHistorySize);
	}
	
	/**
	 * Sets visibility to buttons ( true / false )
	 * 
	 * @param visible The visible value
	 */
	public void setVisibleButtons(boolean visible) {
		this.visible = visible;
		suggestKey.setVisible(visible);
	}
	
	/**
	 * Removes a key
	 * 
	 * @param keyword The key to be removed
	 */
	public void removeKey(String keyword) {
		if (keywordMap.containsKey(keyword)) {
			keywordMap.remove(keyword);
			String keywords = "";
			String[] keywordsArray = new String[keywordMap.keySet().size()];
			int count = 0;
			for (Iterator<String> it = keywordMap.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				keywords += key +" ";
				keywordsArray[count++] = key;
			}
			document.setKeywords(keywords);
			setProperties();
			//drawTagCloud(keywordsArray);
		}
	}
	
	/**
	 * Adds a key
	 * 
	 * @param keyword The keyword to be added
	 */
	public void addKey(String keyword, boolean remove) {
		if (!keywordMap.containsKey(keyword)) {
			String keywords = "";
			String[] keywordsArray = new String[keywordMap.keySet().size()+1];
			int count = 0;
			for (Iterator<String> it = keywordMap.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				keywords += key +" ";
				keywordsArray[count++] = key; 
			}
			keywords += keyword +" ";
			keywordsArray[count++] = keyword; 
			Widget keywordButton = getKeyWidget(keyword, remove);
			keywordMap.put(keyword, keywordButton);
			hKeyPanel.add(keywordButton);
			document.setKeywords(keywords);
			setProperties();
			//drawTagCloud(keywordsArray);
		}
	}
	
	/**
	 * Get a new widget keyword
	 * 
	 * @param keyword The keyword
	 * 
	 * @return The widget
	 */
	private HorizontalPanel getKeyWidget(final String keyword, boolean remove) {
		final HorizontalPanel externalPanel = new HorizontalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();
		HTML space = new HTML();
		ImageHover add = new ImageHover("img/icon/actions/delete_disabled.gif","img/icon/actions/delete.gif");
		add.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Main.get().mainPanel.browser.tabMultiple.tabDocument.document.removeKey(keyword);
				hKeyPanel.remove(externalPanel);
			}
		});
		add.setStyleName("okm-KeyMap-ImageHover");
		hPanel.add(new HTML(keyword));
		hPanel.add(space);
		if (remove) {
			hPanel.add(add);
		}
		hPanel.setCellWidth(space, "6");
		hPanel.setStyleName("okm-KeyMap-Gray");
		HTML space1 = new HTML();
		externalPanel.add(hPanel);
		externalPanel.add(space1);
		externalPanel.setCellWidth(space1, "6");
		externalPanel.setStylePrimaryName("okm-cloudTags");  
		return externalPanel;
	}
	
	/**
	 * Draws a tag cloud
	 */
	/*
	private void drawTagCloud(String keywords[]) {
		// Deletes all tag clouds keys
		keywordsCloud.clear();
		keywordsCloud.setMinFrequency(Main.get().mainPanel.dashboard.keyMapDashboard.getTotalMinFrequency());
		keywordsCloud.setMaxFrequency(Main.get().mainPanel.dashboard.keyMapDashboard.getTotalMaxFrequency());
		
		for (int i=0; i<keywords.length; i++) {
			Hyperlink tagLink = new Hyperlink(keywords[i], null); 
			tagLink.setStylePrimaryName("okm-cloudTags");  
			Style linkStyle = tagLink.getElement().getFirstChildElement().getStyle();
			tagLink.getElement().getFirstChildElement().setClassName("okm-cloudTags");
			int fontSize = keywordsCloud.getLabelSize(Main.get().mainPanel.dashboard.keyMapDashboard.getKeywordRate(keywords[i]));
			linkStyle.setProperty("fontSize", fontSize+"pt");
			linkStyle.setProperty("color", keywordsCloud.getColor(fontSize));
			if (fontSize>0) {
				linkStyle.setProperty("top", (keywordsCloud.getMaxFontSize()-fontSize)/2+"px" );
			} 
			keywordsCloud.add(tagLink);
		}
	}
	*/
}
