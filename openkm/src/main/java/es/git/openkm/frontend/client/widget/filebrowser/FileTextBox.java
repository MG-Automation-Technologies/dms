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

package es.git.openkm.frontend.client.widget.filebrowser;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;

/**
 * File textBox
 * 
 * @author jllort
 *
 */
public class FileTextBox extends Composite implements KeyboardListener {
	
	public static final int ACTION_NONE = -1;
	public static final int ACTION_RENAME = 0;
	private TextBox textBox;
	private int action = ACTION_RENAME; 
	
	/**
	 * File textBox
	 */
	public FileTextBox() {
		textBox = new TextBox();
		textBox.addKeyboardListener(this);
		textBox.setVisibleLength(20);
		textBox.setStyleName("okm-FileBrowser-TextBox");
		initWidget(textBox);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.KeyboardListener#onKeyDown(com.google.gwt.user.client.ui.Widget, char, int)
	 */
	public void onKeyDown(Widget sender, char keyCode, int modifiers) {
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
		switch (keyCode) {
			case (char)KeyboardListener.KEY_ENTER: 
				switch(action) {
					case ACTION_RENAME:
						if (textBox.getText().length() > 0) {
							Main.get().mainPanel.browser.fileBrowser.rename(textBox.getText());
						} else {
							Main.get().mainPanel.browser.fileBrowser.hideRename();
						}
						break;
				}
			    Main.get().mainPanel.enableKeyShorcuts(); // Enables general keys applications
			    break;
			    
			case (char)KeyboardListener.KEY_ESCAPE: 
				switch(action) {
					case ACTION_RENAME:
						Main.get().mainPanel.browser.fileBrowser.hideRename();
						break;
				}
				Main.get().mainPanel.enableKeyShorcuts(); // Enables general keys applications
			    break;
		}
	}
	
	/**
	 * Resets text Box values
	 */
	public void reset() {
		textBox.setText("");
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
		textBox.setFocus(true);
		textBox.selectAll();
	}
	
	/**
	 * Sets the action text Box
	 * 
	 * @param action The action value
	 */
	public void setAction(int action) {
		this.action = action;
	}
}