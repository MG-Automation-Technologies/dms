/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
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

package com.openkm.backend.client.widget.advancedsearch;

import java.util.Vector;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * ResultsSearch
 * 
 * @author jllort
 *
 */
public class ResultsSearch extends Composite {
	
	private FlexTable table;
	private VerticalPanel sp;
	public Status status;
	
	/**
	 * ResultsSearch
	 */
	public ResultsSearch() {
		table = new FlexTable();
		sp = new VerticalPanel();
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		
		table.setBorderWidth(0);
		table.setCellPadding(4);
		table.setCellSpacing(0);
		
		sp.add(table);
		sp.setSize("100%","100%");
		
		initWidget(sp);
	}

	/**
	 * addRow
	 * 
	 * @param vector
	 */
	public void addRow(Vector<String> vector, boolean title){
		int cols = vector.size();
		int rows = table.getRowCount(); 
			
		for (int i=0; i<cols; i++) {
			table.setHTML(rows, i, vector.get(i));
			if (title) {
				table.getCellFormatter().setStyleName(rows, i,"okm-Table-Title");
				if (i>0)
					table.getCellFormatter().addStyleName(rows, i,"okm-Table-Title-LeftBorder");
				table.getCellFormatter().addStyleName(rows, i,"okm-Table-Title-RightBorder");
			}
			table.getCellFormatter().addStyleName(rows, i,"okm-NoWrap");
		}
		
		// Create extra column with 100%
		if (title & cols>0 ) {
			table.setHTML(rows, cols, "&nbsp;");
			table.getCellFormatter().setWidth(rows, cols, "100%");
			table.getCellFormatter().setStyleName(rows, cols,"okm-Table-Title");
			table.getCellFormatter().addStyleName(rows, cols,"okm-Table-Title-LeftBorder");
		}
	}
	
	/**
	 * removeAllRows
	 */
	public void removeAllRows() {
		while (table.getRowCount()>0) {
			table.removeRow(0);
		}
	}
	
}