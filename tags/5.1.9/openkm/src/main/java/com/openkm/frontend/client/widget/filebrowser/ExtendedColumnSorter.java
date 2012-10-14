/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
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

package com.openkm.frontend.client.widget.filebrowser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.gen2.table.client.SortableGrid;
import com.google.gwt.gen2.table.client.SortableGrid.ColumnSorter;
import com.google.gwt.gen2.table.client.SortableGrid.ColumnSorterCallback;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortList;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTMail;
import com.openkm.frontend.client.bean.GWTObjectToOrder;
import com.openkm.frontend.client.util.ColumnComparatorDate;
import com.openkm.frontend.client.util.ColumnComparatorDouble;
import com.openkm.frontend.client.util.ColumnComparatorText;

/**
 * ExtendedColumnSorter
 * 
 * @author jllort
 *
 */
public class ExtendedColumnSorter extends ColumnSorter {
	
	private String selectedRowDataID = "";

	/* (non-Javadoc)
	 * @see com.google.gwt.widgetideas.table.client.SortableGrid$ColumnSorter#onSortColumn(com.google.gwt.widgetideas.table.client.SortableGrid, com.google.gwt.widgetideas.table.client.TableModel.ColumnSortList, com.google.gwt.widgetideas.table.client.SortableGrid.ColumnSorterCallback)
	 */
	public void onSortColumn(SortableGrid grid,
			ColumnSortList sortList, ColumnSorterCallback callback) {
		
		// Get the primary column, sort order, number of rows, number of columns
		int column = sortList.getPrimaryColumn();
	    boolean ascending = sortList.isPrimaryAscending();
	    int rows = Main.get().mainPanel.desktop.browser.fileBrowser.table.getDataTable().getRowCount();
	    int columns = Main.get().mainPanel.desktop.browser.fileBrowser.table.getDataTable().getColumnCount();
	    int selectedRow = Main.get().mainPanel.desktop.browser.fileBrowser.table.getSelectedRow();
	    Map<Integer,Object> data = new HashMap<Integer,Object>(Main.get().mainPanel.desktop.browser.fileBrowser.table.data);
	    
	    List<String[]> elementList = new ArrayList<String[]>(); 					// List with all data
	    List<GWTObjectToOrder> elementToOrder = new ArrayList<GWTObjectToOrder>(); 	// List with column data, and actual position
	    
	    // Gets the data values and set on a list of String arrays ( element by column )
	    for (int i=0; i<rows;i++) {
	    	String[] rowI= new String[columns];
	    	GWTObjectToOrder rowToOrder = new GWTObjectToOrder();
	    	for (int x=0; x<columns; x++) {
	    		rowI[x] = Main.get().mainPanel.desktop.browser.fileBrowser.table.getDataTable().getHTML(i, x);
	    	}
	    	elementList.add(i,rowI);
	    	
	    	switch(column) {
		    	case 0 :
		    	case 1 :
		    	case 2 :
		    	case 5 :
			    		// Text
				    	rowToOrder.setObject(rowI[column].toLowerCase());		// Lower case solves problem with sort ordering
				    	rowToOrder.setDataId(""+ i);							// Actual position value
				    	elementToOrder.add(rowToOrder);
		    		break;
		    	
		    	case 3 :
		    		// Bytes
		    		if (data.get(Integer.parseInt(rowI[7])) instanceof GWTFolder) {
		    			rowToOrder.setObject(new Double(0));										// Byte value
				    	rowToOrder.setDataId(""+ i);												// Actual position value
				    	elementToOrder.add(rowToOrder);
		    		} else if (data.get(Integer.parseInt(rowI[7])) instanceof GWTMail) {  
		    			rowToOrder.setObject(new Double(((GWTMail) data.get(Integer.parseInt(rowI[7]))).getSize()));
				    	rowToOrder.setDataId(""+ i);												// Actual position value
				    	elementToOrder.add(rowToOrder);
		    		} else if (data.get(Integer.parseInt(rowI[7])) instanceof GWTDocument) {  
		    			rowToOrder.setObject(new Double(((GWTDocument) data.get(Integer.parseInt(rowI[7]))).getActualVersion().getSize()));
				    	rowToOrder.setDataId(""+ i);												// Actual position value
				    	elementToOrder.add(rowToOrder);
		    		}
		    		break;
		    		
		    	case 4 :
		    		// Date
		    		if (data.get(Integer.parseInt(rowI[7])) instanceof GWTFolder) {
		    			rowToOrder.setObject(((GWTFolder) data.get(Integer.parseInt(rowI[7]))).getCreated()); 	// Date value
				    	rowToOrder.setDataId(""+ i);														 	// Actual position value
				    	elementToOrder.add(rowToOrder);
		    		} else if (data.get(Integer.parseInt(rowI[7])) instanceof GWTMail) {  
		    			rowToOrder.setObject(((GWTMail) data.get(Integer.parseInt(rowI[7]))).getReceivedDate()); // Date value
				    	rowToOrder.setDataId(""+ i);																 // Actual position value
				    	elementToOrder.add(rowToOrder);
		    		} else if (data.get(Integer.parseInt(rowI[7])) instanceof GWTDocument) {  
		    			rowToOrder.setObject(((GWTDocument) data.get(Integer.parseInt(rowI[7]))).getLastModified()); // Date value
				    	rowToOrder.setDataId(""+ i);																 // Actual position value
				    	elementToOrder.add(rowToOrder);
		    		}
		    		break;
		    		
		    	case 6:
		    		// Version
		    		if (data.get(Integer.parseInt(rowI[7])) instanceof GWTFolder || 
		    			data.get(Integer.parseInt(rowI[7])) instanceof GWTMail) {
		    			rowToOrder.setObject(new Double(0));		
			    		rowToOrder.setDataId(""+ i);							
			    		elementToOrder.add(rowToOrder);
		    		} else if (data.get(Integer.parseInt(rowI[7])) instanceof GWTDocument) { 
		    			String version = ((GWTDocument) data.get(Integer.parseInt(rowI[7]))).getActualVersion().getName();
		    			String numberParts[] = version.split("\\.");
		    			version = "";
		    			for (int x=0; x<numberParts.length; x++) {
		    				switch(numberParts[x].length()) {
		    					case 1:
		    						version = version + "00" + numberParts[x];
		    						break;
		    					case 2:
		    						version = version + "0" + numberParts[x];
		    						break;
		    				}
		    			}
		    			if (numberParts.length==2) {
		    				version = version + "000000";
		    			}
		    			if (numberParts.length==3) {
		    				version = version + "000";
		    			}
		    			rowToOrder.setObject(new Double(version));
		    			rowToOrder.setDataId(""+ i);							
			    		elementToOrder.add(rowToOrder);
		    		}		    		
		    		break;
	    	}
	    	
	    	// Saves the selected row
	    	if (selectedRow==i) {
	    		selectedRowDataID = rowToOrder.getDataId();
	    	}
	    }
	    
	    switch(column) {
	    	case 0 :
	    	case 1 :
	    	case 2 :
	    	case 5 :
	    		// Text
	    		Collections.sort(elementToOrder, ColumnComparatorText.getInstance());
	    		break;
	    	
	    	case 3 :
	    	case 6 :
	    		// Bytes
	    		Collections.sort(elementToOrder, ColumnComparatorDouble.getInstance());
	    		break;
	    		
	    	case 4 :
	    		// Date
	    		Collections.sort(elementToOrder, ColumnComparatorDate.getInstance());
	    		break;
	    }
	    
	    // Reversing if needed
	    if (!ascending) {
			Collections.reverse(elementToOrder);
		}
	    
	    applySort(elementList, elementToOrder);
	    
	    callback.onSortingComplete();
	}
    
	/**
	 * @param elementList
	 * @param elementToOrder
	 */
	private void applySort(List<String[]>  elementList, List<GWTObjectToOrder> elementToOrder) {
		// Removing all values
		while (Main.get().mainPanel.desktop.browser.fileBrowser.table.getDataTable().getRowCount()>0 ){
			Main.get().mainPanel.desktop.browser.fileBrowser.table.getDataTable().removeRow(0);
		}
		
		// Data map
		Map<Integer,Object> data = new HashMap<Integer,Object>(Main.get().mainPanel.desktop.browser.fileBrowser.table.data);
		Main.get().mainPanel.desktop.browser.fileBrowser.table.reset();
		
		int column = 0;
		for (Iterator<GWTObjectToOrder> it =  elementToOrder.iterator(); it.hasNext();) {
			GWTObjectToOrder orderedColumn = it.next();
    		String[] row = elementList.get(Integer.parseInt(orderedColumn.getDataId()));
    		
    		if (data.get(Integer.parseInt(row[7])) instanceof GWTFolder) {
    			Main.get().mainPanel.desktop.browser.fileBrowser.table.addRow((GWTFolder) data.get(Integer.parseInt(row[7])));
    		} else if (data.get(Integer.parseInt(row[7])) instanceof GWTMail) {
    			Main.get().mainPanel.desktop.browser.fileBrowser.table.addRow((GWTMail) data.get(Integer.parseInt(row[7])));
    		} else if (data.get(Integer.parseInt(row[7])) instanceof GWTDocument) {
    			Main.get().mainPanel.desktop.browser.fileBrowser.table.addRow((GWTDocument) data.get(Integer.parseInt(row[7])));
    		}
    		
    		// Sets selectedRow
    		if (!selectedRowDataID.equals("") && selectedRowDataID.equals(row[7])) {
    			Main.get().mainPanel.desktop.browser.fileBrowser.table.setSelectedRow(column);
    			selectedRowDataID = "";
    		}
    		
    		column++;
    	}
	}
}