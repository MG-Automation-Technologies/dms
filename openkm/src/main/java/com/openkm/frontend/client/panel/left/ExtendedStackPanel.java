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

package com.openkm.frontend.client.panel.left;

import com.google.gwt.user.client.ui.StackPanel;

import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.panel.ExtendedDockPanel;
import com.openkm.frontend.client.panel.PanelDefinition;

public class ExtendedStackPanel extends StackPanel {
	
	private boolean firstTime = true; // Controls firstime starting application must not execute main singleton because causes errors
	  								  // some objects are still not accessible due initialization process
	
	private int stackIndex = 0;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.StackPanel#showStack(int)
	 */
	public void showStack( int index ) {
		stackIndex = index;
		
		if (!firstTime) {		
			changeView(index,true);
		}
		
        super.showStack(index);
	} 
	
	/**
	 * Sets the first Time value to control visibility bettween widgets objects
	 * on initialization application
	 * 
	 * @param firstTime The firsTime value
	 */
	public void setFirsTime(boolean firstTime) {
		this.firstTime = firstTime;
	}
	
	/**
	 * Gets the stack index value
	 * 
	 * @return The stack index value
	 */
	public int getStackIndex() {
		return stackIndex;
	}
	
	/**
	 * Change the stack panel view
	 * 
	 * @param index The new stack index selected
	 * @param refresh Enables or disables refreshing
	 */
	private void changeView(int index, boolean refresh){
		switch (index) {
			case PanelDefinition.NAVIGATOR_TAXONOMY:
				Main.get().activeFolderTree = Main.get().mainPanel.navigator.taxonomyTree;
				Main.get().mainPanel.browser.fileBrowser.changeView(PanelDefinition.NAVIGATOR_TAXONOMY);
				Main.get().mainPanel.topPanel.toolBar.changeView(PanelDefinition.NAVIGATOR_TAXONOMY,ExtendedDockPanel.DESKTOP);
				if (refresh) {
					Main.get().activeFolderTree.refresh(true); // When opening a path document must not refreshing
				}
				Main.get().mainPanel.browser.tabMultiple.setVisibleButtons(true);
				break;
				
			case PanelDefinition.NAVIGATOR_TRASH:
				Main.get().activeFolderTree = Main.get().mainPanel.navigator.trashTree;
				Main.get().mainPanel.browser.fileBrowser.changeView(PanelDefinition.NAVIGATOR_TRASH);
				Main.get().mainPanel.topPanel.toolBar.changeView(PanelDefinition.NAVIGATOR_TRASH,ExtendedDockPanel.DESKTOP);
				if (refresh) {
					Main.get().activeFolderTree.refresh(true); // When opening a path document must not refreshing
				}
				Main.get().mainPanel.browser.tabMultiple.setVisibleButtons(false);
				break;
				
			case PanelDefinition.NAVIGATOR_THESAURUS:
				Main.get().activeFolderTree = Main.get().mainPanel.navigator.thesaurusTree;
				Main.get().mainPanel.browser.fileBrowser.changeView(PanelDefinition.NAVIGATOR_THESAURUS);
				Main.get().mainPanel.topPanel.toolBar.changeView(PanelDefinition.NAVIGATOR_THESAURUS,ExtendedDockPanel.DESKTOP);
				if (refresh) {
					Main.get().activeFolderTree.refresh(true); // When opening a path document must not refreshing
				}
				Main.get().mainPanel.browser.tabMultiple.setVisibleButtonsOnlyDocuments();
				break;
			
			case PanelDefinition.NAVIGATOR_TEMPLATES:
				Main.get().activeFolderTree = Main.get().mainPanel.navigator.templateTree;
				Main.get().mainPanel.browser.fileBrowser.changeView(PanelDefinition.NAVIGATOR_TEMPLATES);
				Main.get().mainPanel.topPanel.toolBar.changeView(PanelDefinition.NAVIGATOR_TEMPLATES,ExtendedDockPanel.DESKTOP);
				if (refresh) {
					Main.get().activeFolderTree.refresh(true); // When opening a path document must not refreshing
				}
				Main.get().mainPanel.browser.tabMultiple.setVisibleButtons(true);
				break;
			
			case PanelDefinition.NAVIGATOR_PERSONAL:
				Main.get().activeFolderTree = Main.get().mainPanel.navigator.personalTree;
				Main.get().mainPanel.browser.fileBrowser.changeView(PanelDefinition.NAVIGATOR_PERSONAL);
				Main.get().mainPanel.topPanel.toolBar.changeView(PanelDefinition.NAVIGATOR_PERSONAL,ExtendedDockPanel.DESKTOP);
				if (refresh) {
					Main.get().activeFolderTree.refresh(true); // When opening a path document must not refreshing
				}
				Main.get().mainPanel.browser.tabMultiple.setVisibleButtons(true);
				break;
			
			case PanelDefinition.NAVIGATOR_MAIL:
				Main.get().activeFolderTree = Main.get().mainPanel.navigator.mailTree;
				Main.get().mainPanel.browser.fileBrowser.changeView(PanelDefinition.NAVIGATOR_MAIL);
				Main.get().mainPanel.topPanel.toolBar.changeView(PanelDefinition.NAVIGATOR_MAIL,ExtendedDockPanel.DESKTOP);
				if (refresh) {
					Main.get().activeFolderTree.refresh(true); // When opening a path document must not refreshing
				}
				Main.get().mainPanel.browser.tabMultiple.setVisibleButtons(true);
				break;
		}
	}
	
	/**
	 * Show a stack
	 * 
	 * @param index The new stack index selected
	 * @param refresh Enables or disables refreshing
	 */
	public void showStack( int index, boolean refresh ) {
		stackIndex = index;
		changeView(index,refresh);
        super.showStack(index);
	} 
}