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

package es.git.openkm.frontend.client.widget.properties.attachment;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Attchment menu popup
 * 
 * @author jllort
 *
 */
public class MenuPopup extends PopupPanel {
	
	private VerticalPanel panel;
	private Menu menu;
	
	public MenuPopup() {
		// Establishes auto-close when click outside
		super(true,true);
		panel = new VerticalPanel();
		menu = new Menu();
		panel.add(menu);	
		setWidget(panel);
	}
	
	/**
	 * Refresh language values
	 */
	public void langRefresh() {
		menu.langRefresh();
	}
}