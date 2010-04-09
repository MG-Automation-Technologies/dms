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

package es.git.openkm.frontend.client.widget.properties;

import java.util.Iterator;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.panel.PanelDefinition;
import es.git.openkm.frontend.client.util.Util;

public class Folder extends Composite {
	
	private ScrollPanel scrollPanel;
	private FlexTable tableProperties;
	private FlexTable tableSubscribedUsers;
	private FlexTable table;
	private GWTFolder folder;
	private Button copyWebdavToClipBoard;
	
	/**
	 * The folder
	 */
	public Folder() {
		table = new FlexTable();
		tableProperties = new FlexTable();
		tableSubscribedUsers = new FlexTable();
		scrollPanel = new ScrollPanel(table);
		
		copyWebdavToClipBoard = new Button(Main.i18n("button.copy.clipboard"), new ClickListener() {
			public void onClick(Widget sender) {
				String url = Main.get().workspaceUserProperties.getApplicationURL();
				int idx = url.lastIndexOf('/');
				url = url.substring(0, url.lastIndexOf('/', idx-1)) + "/repository/default" + folder.getPath();
				Util.copyToClipboard(url);
			}
		});
		
		copyWebdavToClipBoard.setStyleName("okm-Button");
		
		tableProperties.setWidth("100%");
		tableProperties.setHTML(0, 0, "<b>"+Main.i18n("folder.uuid")+"</b>");
		tableProperties.setHTML(0, 1, "");
		tableProperties.setHTML(1, 0, "<b>"+Main.i18n("folder.name")+"</b>");
		tableProperties.setHTML(1, 1, "");
		tableProperties.setHTML(2, 0, "<b>"+Main.i18n("folder.parent")+"</b>");
		tableProperties.setHTML(2, 1, "");
		tableProperties.setHTML(3, 0, "<b>"+Main.i18n("folder.created")+"</b>");
		tableProperties.setHTML(3, 1, "");
		tableProperties.setHTML(4, 0, "<b>"+Main.i18n("folder.subscribed")+"</b>");
		tableProperties.setHTML(4, 1, "");
		tableProperties.setHTML(5, 0, "<b>"+Main.i18n("folder.webdav")+"</b>");
		tableProperties.setWidget(5, 1, copyWebdavToClipBoard);
		
		tableSubscribedUsers.setHTML(0,0,"<b>"+Main.i18n("folder.subscribed.users")+"<b>");
				
		table.setWidget(0, 0, tableProperties);
		table.setHTML(0,1, "");
		table.setWidget(0,2,tableSubscribedUsers);

		// The hidden column extends table to 100% width
		CellFormatter cellFormatter = table.getCellFormatter();
		cellFormatter.setWidth(0, 1, "25");
		cellFormatter.setVerticalAlignment(0,0, HasAlignment.ALIGN_TOP);
		cellFormatter.setVerticalAlignment(0,2, HasAlignment.ALIGN_TOP);
		
		// Sets wordWrap for al rows
		setRowWordWarp(0, 0, true, tableProperties);
		setRowWordWarp(1, 0, true, tableProperties);
		setRowWordWarp(2, 0, true, tableProperties);
		setRowWordWarp(3, 0, true, tableProperties);
		setRowWordWarp(4, 0, true, tableProperties);
		setRowWordWarp(0, 0, true, tableSubscribedUsers);
		
		tableProperties.setStyleName("okm-DisableSelect");
		tableSubscribedUsers.setStyleName("okm-DisableSelect");
		
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
			cellFormatter.setWordWrap(row, i, false);
		}
	}
	
	/**
	 * Sets the folder values
	 * 
	 * @param folder The folder object
	 */
	public void set(GWTFolder folder) {
		this.folder = folder;
		tableProperties.setHTML(0, 1, folder.getUuid());
		tableProperties.setHTML(1, 1, folder.getName());
		tableProperties.setHTML(2, 1, folder.getParentPath());
		DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
		tableProperties.setHTML(3, 1, dtf.format(folder.getCreated())+" "+Main.i18n("folder.by")+" "+folder.getAuthor());
		if (folder.isSubscribed()) {
			tableProperties.setHTML(4, 1, Main.i18n("folder.subscribed.yes"));
		} else {
			tableProperties.setHTML(4, 1, Main.i18n("folder.subscribed.no"));
		}
		
		// Some preoperties only must be visible on taxonomy or trash view
		int actualView = Main.get().mainPanel.navigator.getStackIndex();
		if (actualView==PanelDefinition.NAVIGATOR_TAXONOMY || actualView==PanelDefinition.NAVIGATOR_TRASH){
			tableProperties.getCellFormatter().setVisible(4,0,true);
			tableProperties.getCellFormatter().setVisible(4,1,true);
		} else {
			tableProperties.getCellFormatter().setVisible(4,0,false);
			tableProperties.getCellFormatter().setVisible(4,1,false);
		}
		
		setRowWordWarp(0, 1, true, tableProperties);
		setRowWordWarp(1, 1, true, tableProperties);
		setRowWordWarp(2, 1, true, tableProperties);
		setRowWordWarp(3, 1, true, tableProperties);
		setRowWordWarp(4, 1, true, tableProperties);
		
		// Remove all table rows >= 1
		while (tableSubscribedUsers.getRowCount() > 1) {
			tableSubscribedUsers.removeRow(1);
		}
		
		// Sets the folder subscribers
		for (Iterator<String> it = folder.getSubscriptors().iterator(); it.hasNext(); ) {
			tableSubscribedUsers.setHTML(tableSubscribedUsers.getRowCount(), 0, it.next());
			setRowWordWarp(tableSubscribedUsers.getRowCount()-1, 0, true, tableSubscribedUsers);
		}
		
		// Some data must not be visible on personal view
		if (actualView==PanelDefinition.NAVIGATOR_PERSONAL) {
			tableSubscribedUsers.setVisible(false);
		} else {
			tableSubscribedUsers.setVisible(true);
		}
	}
		
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		tableProperties.setHTML(0, 0, "<b>"+Main.i18n("folder.uuid")+"</b>");
		tableProperties.setHTML(1, 0, "<b>"+Main.i18n("folder.name")+"</b>");
		tableProperties.setHTML(2, 0, "<b>"+Main.i18n("folder.parent")+"</b>");
		tableProperties.setHTML(3, 0, "<b>"+Main.i18n("folder.created")+"</b>");
		tableProperties.setHTML(4, 0, "<b>"+Main.i18n("folder.subscribed")+"</b>");
		tableProperties.setHTML(5, 0, "<b>"+Main.i18n("folder.webdav")+"</b>");
		
		if (folder!=null) {
			if (folder.isSubscribed()) {
				tableProperties.setHTML(4, 1, Main.i18n("folder.subscribed.yes"));
			} else {
				tableProperties.setHTML(4, 1, Main.i18n("folder.subscribed.no"));
			}
		}
		
		tableSubscribedUsers.setHTML(0, 0, "<b>"+Main.i18n("folder.subscribed.users")+"<b>");
		copyWebdavToClipBoard.setHTML(Main.i18n("button.copy.clipboard"));
	}
}