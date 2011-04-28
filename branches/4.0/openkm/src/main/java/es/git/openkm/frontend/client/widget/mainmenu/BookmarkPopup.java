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

package es.git.openkm.frontend.client.widget.mainmenu;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;

/**
 * BookmarkPopup
 * 
 * @author jllort
 *
 */
public class BookmarkPopup extends DialogBox {
	private VerticalPanel vPanel;
	private Label name;
	private HorizontalPanel hPanel;
	private VerticalPanel valuesPanel;
	private TextBox textBox;
	private Button acceptButton;
	private Button cancelButton;
	private String nodePath = "";
	private ScrollPanel scrollPanel;
	
	/**
	 * BookmarkPopup
	 */
	public BookmarkPopup() {
		// Establishes auto-close when click outside
		super(false,true);

		vPanel = new VerticalPanel();
		valuesPanel = new VerticalPanel();
		name  = new Label(Main.i18n("bookmark.name"));
		
		hPanel = new HorizontalPanel();
		
		textBox = new TextBox();
		textBox.setStyleName("okm-Input");
		textBox.setVisibleLength(40);
		textBox.setMaxLength(90);
		
		textBox.addKeyboardListener(new KeyboardListener() {
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				if ((char)KeyboardListener.KEY_ENTER == keyCode ) {
					if (!nodePath.equals("") && !textBox.getText().equals("")) {
						Main.get().mainPanel.topPanel.mainMenu.bookmark.add(nodePath, textBox.getText());
					}
					reset();
					hide();
				}	
			}

			public void onKeyDown(Widget arg0, char arg1, int arg2) {
			}

			public void onKeyUp(Widget arg0, char arg1, int arg2) {
			}
		});

		cancelButton = new Button(Main.i18n("button.cancel"), new ClickListener() {
				public void onClick(Widget sender)  {
					reset();
					hide();
				}
			}
		);
		cancelButton.setStyleName("okm-Button");
		
		acceptButton = new Button(Main.i18n("button.accept"), new ClickListener() {
				public void onClick(Widget sender)  {
					if (!nodePath.equals("") && !textBox.getText().equals("")) {
						Main.get().mainPanel.topPanel.mainMenu.bookmark.add(nodePath, textBox.getText());
					}
					reset();
					hide();
				}
			}
		);
		acceptButton.setStyleName("okm-Button");
		
		hPanel.add(cancelButton);
		hPanel.add(new HTML("&nbsp;"));
		hPanel.add(acceptButton);
		
		// TODO This is a workaround for a Firefox 2 bug
		// http://code.google.com/p/google-web-toolkit/issues/detail?id=891
		// Table for solve some visualization problems
		scrollPanel = new ScrollPanel(textBox);
		scrollPanel.setAlwaysShowScrollBars(false);
		scrollPanel.setSize("100%","100%");
		
		valuesPanel.add(name);
		valuesPanel.add(scrollPanel);
		valuesPanel.setCellHorizontalAlignment(name,HorizontalPanel.ALIGN_LEFT);
		valuesPanel.setCellHorizontalAlignment(scrollPanel,HorizontalPanel.ALIGN_LEFT);
		valuesPanel.setWidth("300");

		vPanel.setWidth("310px");
		vPanel.setHeight("100px");

		vPanel.add(new HTML("<br>"));
		vPanel.add(valuesPanel);
		vPanel.add(new HTML("<br>"));
		vPanel.add(hPanel);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(name, VerticalPanel.ALIGN_LEFT);
		vPanel.setCellHorizontalAlignment(valuesPanel, VerticalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hPanel, VerticalPanel.ALIGN_CENTER);
		vPanel.removeStyleDependentName("okm-DisableSelect");
		
		center();
		hide();
		setWidget(vPanel);
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("bookmark.label"));
		name.setText(Main.i18n("bookmark.name"));
		cancelButton.setText(Main.i18n("button.cancel"));
		acceptButton.setText(Main.i18n("button.accept"));
	}
	
	/**
	 * Show the popup bookmark
	 * 
	 */
	public void show(String nodePath, String name) {
		setText(Main.i18n("bookmark.label"));
		this.nodePath = nodePath;
		textBox.setText(name);
		center();
		textBox.setFocus(true);
	}
	
	/**
	 * Reset values
	 */
	private void reset(){
		nodePath = "";
		textBox.setText("");
	}
}