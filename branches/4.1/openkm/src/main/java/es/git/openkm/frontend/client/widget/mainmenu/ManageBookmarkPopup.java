/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package es.git.openkm.frontend.client.widget.mainmenu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTBookmark;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMBookmarkService;
import es.git.openkm.frontend.client.service.OKMBookmarkServiceAsync;
import es.git.openkm.frontend.client.util.Util;

/**
 * ManageBookmarkPopup
 * 
 * @author jllort
 *
 */
public class ManageBookmarkPopup extends DialogBox {
	
	private final OKMBookmarkServiceAsync bookmarkService = (OKMBookmarkServiceAsync) GWT.create(OKMBookmarkService.class);
	
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private Button cancelButton;
	private Button deleteButton;
	private Button updateButton;
	private FlexTable table;
	private FlexTable tableBookmark;
	private ScrollPanel scrollPanel;
	private ScrollPanel scrollPanelBookmark;
	private TextBox textBox;
	private Map bookmarkMap = new HashMap();
	private int selectedRow = -1;
	private int columns = 3;
	
	/**
	 * ManageBookmarkPopup
	 */
	public ManageBookmarkPopup(){
		// Establishes auto-close when click outside
		super(false,true);
		
		vPanel = new VerticalPanel();
		textBox = new TextBox();
		textBox.setStyleName("okm-Input");
		textBox.setVisibleLength(40);
		textBox.setMaxLength(90);
		textBox.addKeyboardListener(new KeyboardListener() {
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				if ((char)KeyboardListener.KEY_ENTER == keyCode ) {
					if (textBox.getText().length()>0) {
						rename(table.getText(selectedRow,1),textBox.getText());
					}
					
				} else if ((char)KeyboardListener.KEY_ESCAPE == keyCode ) {
					tableBookmark.setHTML(0,1,table.getText(selectedRow,1));
					deleteButton.setEnabled(true);
					updateButton.setEnabled(true);
				}		
			}

			public void onKeyDown(Widget arg0, char arg1, int arg2) {
			}

			public void onKeyUp(Widget arg0, char arg1, int arg2) {
			}
		});
		
		cancelButton = new Button(Main.i18n("button.close"), new ClickListener() {
				public void onClick(Widget sender)  {
					Main.get().mainPanel.topPanel.mainMenu.bookmark.getAll(); // Refreshing menu after edit bookmarks
					hide();
				}
			}
		);
		cancelButton.setStyleName("okm-Button");
		
		deleteButton = new Button(Main.i18n("button.delete"), new ClickListener() {
				public void onClick(Widget sender)  {
					if (selectedRow>=0) {
						remove(table.getText(selectedRow,1));
					}
				}
			}
		);
		deleteButton.setStyleName("okm-Button");
		deleteButton.setEnabled(false);
		
		updateButton = new Button(Main.i18n("button.update"), new ClickListener() {
				public void onClick(Widget sender)  {
					if (selectedRow>=0) {
						textBox.setText(table.getHTML(selectedRow,1));
						tableBookmark.setWidget(0,1,textBox);
						updateButton.setEnabled(false);
						deleteButton.setEnabled(false);
						textBox.setFocus(true);
					}
				}
			}
		);
		updateButton.setStyleName("okm-Button");
		updateButton.setEnabled(false);
		
		table = new FlexTable();
		table.setBorderWidth(0);
		table.setCellSpacing(0);
		table.setCellSpacing(0);
		table.setWidth("100%");
		table.addStyleName("okm-DisableSelect");
		
		table.addTableListener(new TableListener() {
				public void onCellClicked(SourcesTableEvents sender, int row, int col) {
					if (row != selectedRow) {
						styleRow(selectedRow, false);
						styleRow(row, true);
						selectedRow = row;
						if (bookmarkMap.containsKey(table.getText(row,1))) {
							GWTBookmark bookmark = (GWTBookmark) bookmarkMap.get(table.getText(row,1));
							tableBookmark.setHTML(0,1,bookmark.getName());
							tableBookmark.setHTML(1,1,bookmark.getPath());
							if (bookmark.getType().equals(Bookmark.BOOKMARK_DOCUMENT)) {
								tableBookmark.setHTML(2,1,Main.i18n("bookmark.type.document"));
							} else if (bookmark.getType().equals(Bookmark.BOOKMARK_FOLDER)) {
								tableBookmark.setHTML(2,1,Main.i18n("bookmark.type.folder"));
							} 
						}
						deleteButton.setEnabled(true);
						updateButton.setEnabled(true);
					}
				}
			}
		);
		
		scrollPanel = new ScrollPanel(table);
		scrollPanel.setSize("380","150");
		scrollPanel.setStyleName("okm-Bookmark-Panel");
		
		// Selected Bookmark data
		tableBookmark = new FlexTable();
		tableBookmark.setBorderWidth(0);
		tableBookmark.setCellSpacing(0);
		tableBookmark.setCellSpacing(0);
		tableBookmark.setWidth("100%");
		
		tableBookmark.setHTML(0,0,"<b>"+Main.i18n("bookmark.name")+"</b>");
		tableBookmark.setHTML(0,1,"");
		tableBookmark.setHTML(1,0,"<b>"+Main.i18n("bookmark.path")+"</b>");
		tableBookmark.setHTML(1,1,"");
		tableBookmark.setHTML(2,0,"<b>"+Main.i18n("bookmark.type")+"</b>");
		tableBookmark.setHTML(2,1,"");
		
		tableBookmark.getCellFormatter().setWidth(0,0,"15%");
		tableBookmark.getCellFormatter().setWidth(0,1,"15%");
		tableBookmark.getCellFormatter().setWidth(0,2,"70%");
		
		// Set no wrap
		CellFormatter cellFormatter = tableBookmark.getCellFormatter();
		for (int i=0; i<columns; i++) {
			cellFormatter.setWordWrap(0, i, false);
			cellFormatter.setWordWrap(1, i, false);
			cellFormatter.setWordWrap(2, i, false);
		}
		
		scrollPanelBookmark = new ScrollPanel(tableBookmark);
		scrollPanelBookmark.setWidth("380");
		scrollPanelBookmark.setStyleName("okm-Bookmark-Panel");
		scrollPanelBookmark.setAlwaysShowScrollBars(false);
		
		hPanel = new HorizontalPanel();
		hPanel.add(deleteButton);
		hPanel.add(new HTML("&nbsp;&nbsp;"));
		hPanel.add(updateButton);
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(scrollPanelBookmark);
		vPanel.add(new HTML("<br>"));
		vPanel.add(hPanel);
		vPanel.add(new HTML("<br>"));
		vPanel.add(scrollPanel);
		vPanel.add(new HTML("<br>"));
		vPanel.add(cancelButton);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(scrollPanelBookmark, HorizontalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hPanel, HorizontalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(scrollPanel, HorizontalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(cancelButton, HorizontalPanel.ALIGN_CENTER);
		
		vPanel.setWidth("100%");
		
		center();
		hide();
		setWidget(vPanel);
	}
	
	/**
	 * Callback get all
	 */
	final AsyncCallback callbackGetAll = new AsyncCallback() {
		public void onSuccess(Object result) {
			List bookmarkList = (List) result;
			int row = table.getRowCount();
			bookmarkMap = new HashMap();
			
			for (Iterator it = bookmarkList.iterator(); it.hasNext();) {
				GWTBookmark bookmark = (GWTBookmark) it.next();	
				bookmarkMap.put(bookmark.getName(),bookmark);
				
				String icon = "";
				if (bookmark.getType().equals(Bookmark.BOOKMARK_DOCUMENT)) {
					icon = "img/icon/menu/document_bookmark.gif";
				} else if (bookmark.getType().equals(Bookmark.BOOKMARK_FOLDER)) {
					icon = "img/icon/menu/folder_bookmark.gif";
				}
				
				table.setHTML(row,0,Util.imageHTML(icon));
				table.setHTML(row,1,bookmark.getName());
				table.getRowFormatter().setStyleName(row, "okm-Table-Row");
				table.getCellFormatter().setWidth(row,0,"25");
				setRowWordWarp(row,2,false);
				
				if(row==0) {
					tableBookmark.setHTML(0,1,bookmark.getName());
					tableBookmark.setHTML(1,1,bookmark.getPath());
					tableBookmark.setHTML(2,1,bookmark.getType());
					if (bookmark.getType().equals(Bookmark.BOOKMARK_DOCUMENT)) {
						tableBookmark.setHTML(2,1,Main.i18n("bookmark.type.document"));
					} else if (bookmark.getType().equals(Bookmark.BOOKMARK_FOLDER)) {
						tableBookmark.setHTML(2,1,Main.i18n("bookmark.type.folder"));
					} 
					deleteButton.setEnabled(true);
					updateButton.setEnabled(true);
					styleRow(row,true);
					selectedRow = row;
				}
				
				row++;
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getAll", caught);
		}
	};
	
	/**
	 * Callback remove
	 */
	final AsyncCallback callbackRemove = new AsyncCallback() {
		public void onSuccess(Object result) {
			if (selectedRow>=0) {
				bookmarkMap.remove(table.getText(selectedRow,1));
				table.removeRow(selectedRow);
				if (table.getRowCount()>0) {
					if (selectedRow!=0) {
						selectedRow--;
						styleRow(selectedRow,true);
					} else {
						styleRow(selectedRow,true);
					}
				} else {
					deleteButton.setEnabled(false);
					updateButton.setEnabled(false);
					selectedRow=-1;
				}
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("remove", caught);
		}
	};
	
	/**
	 * Callback rename
	 */
	final AsyncCallback callbackRename = new AsyncCallback() {
		public void onSuccess(Object result) {
			GWTBookmark bookmark = (GWTBookmark) result;
			if (selectedRow>=0) {
				//String newName = textBox.getText();
				//GWTBookmark bookmark = (GWTBookmark) bookmarkMap.get(table.getText(selectedRow,1));
				bookmarkMap.remove(table.getText(selectedRow,1));
				bookmark.setName(bookmark.getName());
				bookmarkMap.put(bookmark.getName(),bookmark);
				tableBookmark.setHTML(0,1,bookmark.getName());
				table.setHTML(selectedRow,1,bookmark.getName());
			}
			deleteButton.setEnabled(true);
			updateButton.setEnabled(true);
		}

		public void onFailure(Throwable caught) {
			deleteButton.setEnabled(true);
			updateButton.setEnabled(true);
			Main.get().showError("rename", caught);
		}
	};
	
	/**
	 * Gets the bookmark list from the server
	 * 
	 */
	public void getAll() {
		ServiceDefTarget endPoint = (ServiceDefTarget) bookmarkService;
		endPoint.setServiceEntryPoint(Config.OKMBookmarkService);			
		bookmarkService.getAll(callbackGetAll);
	}
	
	/**
	 * Remove bookmark 
	 * 
	 */
	private void remove(String name) {
		ServiceDefTarget endPoint = (ServiceDefTarget) bookmarkService;
		endPoint.setServiceEntryPoint(Config.OKMBookmarkService);			
		bookmarkService.remove(name, callbackRemove);
	}
	
	/**
	 * Rename bookmark 
	 * 
	 */
	private void rename(String name, String newName) {
		ServiceDefTarget endPoint = (ServiceDefTarget) bookmarkService;
		endPoint.setServiceEntryPoint(Config.OKMBookmarkService);			
		bookmarkService.rename(name, newName, callbackRename);
	}
	
	/**
	 * Removes all rows
	 */
	private void removeAll(){
		bookmarkMap = new HashMap();
		while (table.getRowCount()>0){
			table.removeRow(0);
		}
	}
	
	/**
	 * Show the popup
	 */
	public void showPopup(){
		setText(Main.i18n("bookmark.edit.label"));
		tableBookmark.setHTML(0,1,"");
		tableBookmark.setHTML(1,1,"");
		tableBookmark.setHTML(2,1,"");
		deleteButton.setEnabled(false);
		updateButton.setEnabled(false);
		
		selectedRow = -1;
		removeAll();
		getAll();
		center();
	}
	
	/**
	 * Change the style row selected or unselected
	 * 
	 * @param row The row afected
	 * @param selected Indicates selected unselected row
	 */
	private void styleRow(int row, boolean selected) {
		if (row>=0) {
			if (selected){
				table.getRowFormatter().addStyleName(row, "okm-Security-SelectedRow");
			}
			else {
				table.getRowFormatter().removeStyleName(row, "okm-Security-SelectedRow");
			}
		}
	}
	
	/**
	 * Set the WordWarp for all the row cells
	 * 
	 * @param row The row cell
	 * @param columns Number of row columns
	 * @param warp
	 */
	private void setRowWordWarp(int row, int columns, boolean warp) {
		CellFormatter cellFormatter = table.getCellFormatter();
		for (int i=0; i<columns; i++) {
			cellFormatter.setWordWrap(row, i, false);
		}
	}
	
	/**
	 * Refreshing language
	 */
	public void langRefresh(){
		setText(Main.i18n("bookmark.edit.label"));
		tableBookmark.setHTML(0,0,"<b>"+Main.i18n("bookmark.name")+"</b>");
		tableBookmark.setHTML(1,0,"<b>"+Main.i18n("bookmark.path")+"</b>");
		tableBookmark.setHTML(2,0,"<b>"+Main.i18n("bookmark.type")+"</b>");
		cancelButton.setHTML(Main.i18n("button.close"));
		deleteButton.setHTML(Main.i18n("button.delete"));
		updateButton.setHTML(Main.i18n("button.update"));
	}
}
