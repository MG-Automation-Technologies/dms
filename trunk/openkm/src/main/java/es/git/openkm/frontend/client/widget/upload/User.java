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

package es.git.openkm.frontend.client.widget.upload;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;

import es.git.openkm.frontend.client.Main;

/**
 * User
 * 
 * @author jllort
 *
 */
public class User extends Composite  implements TableListener {
	private FlexTable table = new FlexTable();
	private boolean isUserToNofity = false;
	private int numCols = 1;
	private int selectedRow = -1;

	public User(boolean isUserToNofity) {
		this.isUserToNofity = isUserToNofity;
		
		// User notify table
		table = new FlexTable();
		table.setBorderWidth(0);
		table.setCellSpacing(0);
		table.setCellSpacing(0);
		table.setWidth("100%");
		
		if (isUserToNofity) {
			table.setHTML(0, 0, Main.i18n("fileupload.label.users.to.notify"));
		} else {
			table.setHTML(0, 0, Main.i18n("Usuario"));
		}
		
		RowFormatter rowFormatter = table.getRowFormatter();
		rowFormatter.setStyleName(0, "okm-Security-Title");
		
		// Format borders and margins
		table.getCellFormatter().setStyleName(0,0,"okm-Security-Title-Margin");
		table.getCellFormatter().addStyleName(0,0,"okm-Security-Title-LeftBorder");
		
		table.addTableListener(this);
		initWidget(table);
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
	 * Adds new username row
	 * 
	 * @param userName The user name value
	 */
	public void addRow(String userName) {
		int rows = table.getRowCount();
		table.setHTML(rows, 0, userName);	
		table.getRowFormatter().setStyleName(rows, "okm-Table-Row");
		
		setRowWordWarp(rows, numCols, false);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.TableListener#onCellClicked(com.google.gwt.user.client.ui.SourcesTableEvents, int, int)
	 */
	public void onCellClicked(SourcesTableEvents sender, int row, int col) {
		// Mark selected row or orders rows if header row (0) is clicked 
		// And row must be other than the selected one
		if (row != 0 && row != selectedRow) {
			styleRow(selectedRow, false);
			styleRow(row, true);
			selectedRow = row;
		}
	}
	
	/**
	 * Change the style row selected or unselected
	 * 
	 * @param row The row afected
	 * @param selected Indicates selected unselected row
	 */
	private void styleRow(int row, boolean selected) {
		// Ensures that header is never changed
	    if (row > 0) {
	      if (selected){
	        table.getRowFormatter().addStyleName(row, "okm-Security-SelectedRow");
	      }
	      else {
	        table.getRowFormatter().removeStyleName(row, "okm-Security-SelectedRow");
	      }
	    }
	 }
	
	/**
	 * Gets the user
	 * 
	 * @return The user
	 */
	public String getUser() {
		String user = null;
		
		if (selectedRow > 0) {
			user =  table.getText(selectedRow, 0);
		}
		
		return user;
	}
	
	/**
	 * Selects the las row
	 */
	public void selectLastRow() {
		int rows = table.getRowCount();
		if (rows>1) {
			styleRow(selectedRow, false);
			styleRow(rows-1, true);
			selectedRow = rows-1;
		}
	}
	
	/**
	 * Removes the selected row
	 */
	public void removeSelectedRow() {
		if (selectedRow > 0) {
			table.removeRow(selectedRow);
			selectedRow--;
			if (selectedRow > 0) {
				styleRow(selectedRow, true);
			} else if (table.getRowCount()>1) {
				selectedRow = 1;
				styleRow(selectedRow, true);
			} 
		}
	}
	
	/**
	 * Gets the users string to notify
	 * 
	 * @return The users string
	 */
	public String getUsersToNotify() {
		String users = "";
		
		if (table.getRowCount()>1) {
			for (int i = 1; i<table.getRowCount(); i++){
				users += table.getText(i,0) + ",";
			}
		}
		
		// Removes last ',' character
		if (users.length()>0) {
			users = users.substring(0, users.length()-1);
		}
		
		return users;
	}
	
	/**
	 * Removes all rows except the first
	 */
	public void removeAllRows() {
		// Purge all rows except first
		while (table.getRowCount() > 1) {
			table.removeRow(1);
		}
	}
	
	/**
	 * Reset table values
	 */
	public void reset() {
		selectedRow = -1;
		removeAllRows();
	}
	
	/**
	 * Lang refreshing
	 */
	public void langRefresh() {
		if (isUserToNofity) {
			table.setHTML(0, 0, Main.i18n("fileupload.label.users.to.notify"));
		} else {
			table.setHTML(0, 0, Main.i18n("fileupload.label.users"));
		}
	}
}