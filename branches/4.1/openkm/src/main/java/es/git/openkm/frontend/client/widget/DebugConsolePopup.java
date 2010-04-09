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

package es.git.openkm.frontend.client.widget;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;

/**
 * DebugConsolePopup
 * 
 * @author jllort
 *
 */
public class DebugConsolePopup extends DialogBox implements ClickListener {
	
	private VerticalPanel vPanel;
	private Button button;
	private HTML text;
	
	/**
	 * Logout popup
	 */
	public DebugConsolePopup() {
		// Establishes auto-close when click outside
		super(false,false);
		
		setText(Main.i18n("debug.console.label"));
		vPanel = new VerticalPanel();
		button = new Button(Main.i18n("button.close"), this);
		text = new HTML(Main.i18n("debug.enable.disable"));
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(text);
		vPanel.add(Log.getDivLogger().getWidget());
		vPanel.add(new HTML("<br>"));
		vPanel.add(button);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(button, VerticalPanel.ALIGN_CENTER);

		button.setStyleName("okm-Button");

		super.hide();
		setWidget(vPanel);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.ClickListener#onClick(com.google.gwt.user.client.ui.Widget)
	 */
	public void onClick(Widget sender) {
		super.hide();
	}
	
	/**
	 * Lang refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("debug.console.label"));
		text.setHTML(Main.i18n("debug.enable.disable"));
	}
	
}