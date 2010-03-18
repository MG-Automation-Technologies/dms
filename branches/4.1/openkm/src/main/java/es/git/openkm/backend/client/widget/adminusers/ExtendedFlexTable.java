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


package es.git.openkm.backend.client.widget.adminusers;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

import es.git.openkm.backend.client.Main;

public class ExtendedFlexTable extends FlexTable implements TableListener {
	
	private int mouseX = 0;
	private int mouseY = 0;
	private int selectedRow = -1;
	
	/**
	 * ExtendedFlexTable
	 */
	public ExtendedFlexTable() {
		super();
		
		// Adds double click event control to table ( on default only has CLICK )
		sinkEvents(Event.ONDBLCLICK | Event.MOUSEEVENTS);
		addTableListener(this);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.EventListener#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Event event) {
        int selectedColumn = 0;
		int selectedRow = 0;
		
		if (DOM.eventGetType(event) == Event.ONDBLCLICK || DOM.eventGetType(event) == Event.ONMOUSEDOWN 
			|| DOM.eventGetType(event) == Event.ONCLICK) {
			Element td = getMouseEventTargetCell(event);
            if (td == null) return;
            Element tr = DOM.getParent(td);
            Element body = DOM.getParent(tr);
            selectedRow = DOM.getChildIndex(body, tr); 
            selectedColumn = DOM.getChildIndex(tr, td);
		}
		
		// Only if selectedRow > 0, indicates a document row value and must apear menu or double click action
		if (selectedRow>0) {
			
			// When de button mouse is released
			mouseX = DOM.eventGetClientX(event);
			mouseY = DOM.eventGetClientY(event);
			
			// On double click not sends event to onCellClicked across super.onBrowserEvent();
			if (DOM.eventGetType(event) == Event.ONDBLCLICK) {
				// Disables the event propagation the sequence is:
				// Two time entry onCellClicked before entry on onBrowserEvent and disbles the
				// Tree onCellClicked that produces inconsistence error refreshing
				DOM.eventCancelBubble(event, true);
				onCellClicked(this, selectedRow, selectedColumn); // Fires oncellclicked on right buttom
				Main.get().centerPanel.adminUsersPanel.users.editUser();
				
			} else if (DOM.eventGetType(event) == Event.ONMOUSEDOWN) {
				switch (DOM.eventGetButton(event)) {
				case Event.BUTTON_RIGHT:
		            
		        	Main.get().centerPanel.adminUsersPanel.users.showMenu();
					DOM.eventPreventDefault(event); // Prevent to fire event to browser
					break;
				default:
					break;
				}
			} else if (DOM.eventGetType(event) == Event.ONCLICK) {
				Main.get().centerPanel.adminUsersPanel.users.showUser(selectedRow);
			}
		}
		super.onBrowserEvent(event);
	}
	
	/**
     * Method originally copied from HTMLTable superclass where it was defined private
     * Now implemented differently to only return target cell if it'spart of 'this' table
     */
    private Element getMouseEventTargetCell(Event event) {
        Element td = DOM.eventGetTarget(event);
        //locate enclosing td element
        while (!DOM.getElementProperty(td, "tagName").equalsIgnoreCase("td")) {
            // If we run out of elements, or run into the table itself, then give up.
            if ((td == null) || td==getElement())
                return null;
            td = DOM.getParent(td);
        }
        //test if the td is actually from this table
        Element tr = DOM.getParent(td);
        Element body = DOM.getParent(tr);
        if (body==this.getBodyElement()) {
            return td;
        }
        //Didn't find appropriate cell
        return null;
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
	      if (selected) {
	        getRowFormatter().addStyleName(row, "okm-Table-SelectedRow");
	      } else {
	        getRowFormatter().removeStyleName(row, "okm-Table-SelectedRow");
	      }
	    }
	 }
	
	/**
	 * Removes all rows except the first
	 */
	public void removeAllRows() {
		// Resets selected Rows and Col values
		selectedRow = -1;
		
		// Purge all rows except first
		while (getRowCount() > 1) {
			removeRow(1);
		}
	}
	
	/**
	 * Gets the selected row
	 * 
	 * @return The selected row value
	 */
	public int getSelectedRow() {
		return selectedRow;
	}

	/**
	 * Gets the X position on mouse click
	 * 
	 * @return The x position on mouse click
	 */
	public int getMouseX() {
		return mouseX;
	}
	
	/**
	 * Gets the Y position on mouse click
	 * 
	 * @return The y position on mouse click
	 */
	public int getMouseY() {
		return mouseY;
	}
	
	/**
	 * Sets the selected row
	 * 
	 * @param row The row
	 */
	public void setSelectedRow(int row) {
		if (row != 0 && row != selectedRow) {
			styleRow(selectedRow, false);
			styleRow(row, true);
			selectedRow = row;
		}
	}
}