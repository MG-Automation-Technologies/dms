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

package es.git.openkm.frontend.client.widget.foldertree;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.util.Util;

/**
 * File textBox
 * 
 * @author jllort
 *
 */
public class FolderTextBox extends Composite implements KeyboardListener, HasWidgets {
	private TextBox textBox;
	
	/**
	 * File textBox
	 */
	public FolderTextBox() {
		HorizontalPanel panel = new HorizontalPanel();
		//panel.setSize("100%", "100%");
		textBox = new TextBox();
		textBox.addKeyboardListener(this);
		textBox.setVisibleLength(20);
		textBox.setStyleName("okm-FileBrowser-TextBox");
		panel.add(new HTML(Util.imageItemHTML("img/menuitem_empty.gif", "", "top")));
		panel.add(textBox);
		initWidget(panel);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.KeyboardListener#onKeyDown(com.google.gwt.user.client.ui.Widget, char, int)
	 */
	public void onKeyDown(Widget sender, char keyCode, int modifiers) {
		int action = Main.get().activeFolderTree.getFolderAction();
		
		switch (keyCode) {
			case (char)KeyboardListener.KEY_ENTER: 
				switch(action) {
					case FolderTree.ACTION_CREATE:
						Main.get().activeFolderTree.rootItem.addStyleName("okm-DisableSelect"); // Disables drag and drop browser text selection
						if (textBox.getText().length() > 0) {
							Main.get().activeFolderTree.create(textBox.getText());
						} else {
							Main.get().activeFolderTree.removeTmpFolderCreate();		
						}
						break;
						
					case FolderTree.ACTION_RENAME:
						Main.get().activeFolderTree.rootItem.addStyleName("okm-DisableSelect"); // Disables drag and drop browser text selection
						if (textBox.getText().length() > 0) {
							Main.get().activeFolderTree.rename(textBox.getText());
						} else {
							Main.get().activeFolderTree.cancelRename();
						}
						break;
				}
				Main.get().mainPanel.enableKeyShorcuts(); // Enables general keys applications
			    break;
			    
			case (char)KeyboardListener.KEY_ESCAPE: 
				switch(action) {
					case FolderTree.ACTION_CREATE:
						Main.get().activeFolderTree.rootItem.addStyleName("okm-DisableSelect"); // Disables drag and drop browser text selection
						Main.get().activeFolderTree.removeTmpFolderCreate();
						break;
						
					case FolderTree.ACTION_RENAME:
						Main.get().activeFolderTree.rootItem.addStyleName("okm-DisableSelect"); // Disables drag and drop browser text selection
						Main.get().activeFolderTree.cancelRename();
						break;
				}
				Main.get().mainPanel.enableKeyShorcuts(); // Enables general keys applications
			    break;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.KeyboardListener#onKeyPress(com.google.gwt.user.client.ui.Widget, char, int)
	 */
	public void onKeyPress(Widget sender, char keyCode, int modifiers) {
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.KeyboardListener#onKeyUp(com.google.gwt.user.client.ui.Widget, char, int)
	 */
	public void onKeyUp(Widget sender, char keyCode, int modifiers) {
	}
	
	/**
	 * Resets text Box values
	 */
	public void reset() {
		textBox.setText("");
	}
	
	/**
	 * Gets the text value
	 * 
	 * @return The text
	 */
	public String getText() {
		return textBox.getText();
	}
	
	/**
	 * Sets text input box
	 * 
	 * @param text The text
	 */
	public void setText(String text) {
		textBox.setVisibleLength(text.length()+5);
		textBox.setText(text);
	}
	
	/**
	 * Sets windows focus to input
	 */
	public void setFocus() {
		textBox.selectAll();
		textBox.setFocus(true);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#add(com.google.gwt.user.client.ui.Widget)
	 */
	public void add(Widget w) {
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#clear()
	 */
	public void clear() {
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#iterator()
	 */
	public Iterator<Widget> iterator() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#remove(com.google.gwt.user.client.ui.Widget)
	 */
	public boolean remove(Widget w) {
		return true;
	}
}