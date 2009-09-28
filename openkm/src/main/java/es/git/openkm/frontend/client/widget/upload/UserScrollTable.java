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

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.widgetideas.table.client.FixedWidthFlexTable;
import com.google.gwt.widgetideas.table.client.FixedWidthGrid;
import com.google.gwt.widgetideas.table.client.ScrollTable;
import com.google.gwt.widgetideas.table.client.SelectionGrid;
import com.google.gwt.widgetideas.table.client.ScrollTable.ScrollPolicy;
import com.google.gwt.widgetideas.table.client.ScrollTable.ScrollTableImages;

import es.git.openkm.frontend.client.Main;

/**
 * UserScrollTable
 * 
 * @author jllort
 *
 */
public class UserScrollTable extends Composite {
	private ScrollTable table;
	private boolean isUserToNofity = false;
	private FixedWidthFlexTable headerTable;
	private FixedWidthGrid dataTable;

	/**
	 * UserScrollTable
	 * 
	 * @param isUserToNofity
	 */
	public UserScrollTable(boolean isUserToNofity) {
		this.isUserToNofity = isUserToNofity;
		
		ScrollTableImages scrollTableImages = new ScrollTableImages(){
			public AbstractImagePrototype fillWidth() {
				return new AbstractImagePrototype() {
					public void applyTo(Image image) {
						image.setUrl("img/fill_width.gif");
					}
					public Image createImage() {
						return  new Image("img/fill_width.gif");
					}
					public String getHTML(){
						return "<img/>";
					}
				};
			}
			
			public AbstractImagePrototype scrollTableAscending() {
				return new AbstractImagePrototype() {
					public void applyTo(Image image) {
						image.setUrl("img/sort_asc.gif");
					}
					public Image createImage() {
						return  new Image("img/sort_asc.gif");
					}
					public String getHTML(){
						return "<img/>";
					}
				};
			}
			
			public AbstractImagePrototype scrollTableDescending() {
				return new AbstractImagePrototype() {
					public void applyTo(Image image) {
						image.setUrl("img/sort_desc.gif");
					}
					public Image createImage() {
						return  new Image("img/sort_desc.gif");
					}
					public String getHTML(){
						return "<img/>";
					}
				};
			}

			public AbstractImagePrototype scrollTableFillWidth() {
				return new AbstractImagePrototype() {
					public void applyTo(Image image) {
						image.setUrl("img/fill_width.gif");
					}
					public Image createImage() {
						return  new Image("img/fill_width.gif");
					}
					public String getHTML(){
						return "<img/>";
					}
				};
			}
		};
		
		headerTable = new FixedWidthFlexTable();
		dataTable = new FixedWidthGrid();
		
		table = new ScrollTable(dataTable,headerTable,scrollTableImages);
		table.setCellSpacing(0);
		table.setCellPadding(0);
		table.setSize("120","140");
		
		// Level 1 headers
	    if (isUserToNofity) {
	    	headerTable.setHTML(0, 0, Main.i18n("fileupload.label.users.to.notify"));
		} else {
			headerTable.setHTML(0, 0, Main.i18n("fileupload.label.users"));
		}
	    
	    table.setColumnWidth(0,120);
	    
	    // Table data
	    //dataTable.setHoveringPolicy(SortableFixedWidthGrid.HOVERING_POLICY_CELL);
	    dataTable.setSelectionPolicy(SelectionGrid.SelectionPolicy.ONE_ROW);
	    //dataTable.setMinHoverRow(0);
	    
	    table.setScrollPolicy(ScrollPolicy.BOTH);
	    
	    initWidget(table);
	}
	
	/**
	 * Adds new username row
	 * 
	 * @param userName The user name value
	 */
	public void addRow(String userName) {
		int rows = dataTable.getRowCount();
		dataTable.setHTML(rows, 0, userName);
	}
	
	/**
	 * Gets the user
	 * 
	 * @return The user
	 */
	public String getUser() {
		String user = null;
		
		if (!dataTable.getSelectedRows().isEmpty()) {
			int selectedRow = ((Integer) dataTable.getSelectedRows().iterator().next()).intValue();
			if (dataTable.isRowSelected(selectedRow)) {
				user = dataTable.getHTML(((Integer) dataTable.getSelectedRows().iterator().next()).intValue(),0);
			}
		}
		
		return user;
	}
	
	/**
	 * Selects the las row
	 */
	public void selectLastRow() {
		if (dataTable.getRowCount()>0) {
			dataTable.selectRow(dataTable.getRowCount()-1,true);
		}
	}
	
	/**
	 * Removes the selected row
	 */
	public void removeSelectedRow() {
		if(!dataTable.getSelectedRows().isEmpty()) {
			int selectedRow = ((Integer) dataTable.getSelectedRows().iterator().next()).intValue();
			dataTable.removeRow(selectedRow);
			if (dataTable.getRowCount()>0) {
				if (dataTable.getRowCount()>selectedRow) {
					dataTable.selectRow(selectedRow,true);
				} else {
					dataTable.selectRow(selectedRow-1,true);
				}
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
		
		if (dataTable.getRowCount()>0) {
			for (int i = 0; i<dataTable.getRowCount(); i++){
				users += dataTable.getText(i,0) + ",";
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
		// Purge all rows 
		while (dataTable.getRowCount() > 0) {
			dataTable.removeRow(0);
		}
	}
	
	/**
	 * Reset table values
	 */
	public void reset() {
		removeAllRows();
	}
	
	/**
	 * Lang refreshing
	 */
	public void langRefresh() {
		if (isUserToNofity) {
			headerTable.setHTML(0, 0, Main.i18n("fileupload.label.users.to.notify"));
		} else {
			headerTable.setHTML(0, 0, Main.i18n("fileupload.label.users"));
		}
	}
}