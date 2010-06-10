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

package com.openkm.frontend.client.widget.searchin;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.panel.ExtendedDockPanel;
import com.openkm.frontend.client.panel.center.Search;
import com.openkm.frontend.client.panel.center.SearchBrowser;
import com.openkm.frontend.client.panel.top.TopPanel;

/**
 * Status
 * 
 * @author jllort
 *
 */
public class Status extends PopupPanel {
	
	private HorizontalPanel hPanel;
	private HTML msg;
	private HTML space;
	private Image image;
	
	private boolean flag_saveSearch 	= false;
	
	/**
	 * Status
	 */
	public Status() {
		super(false,true);
		hPanel = new HorizontalPanel();
		image = new Image("img/indicator.gif");
		msg = new HTML("");
		space = new HTML("");
		
		hPanel.add(image);
		hPanel.add(msg);
		hPanel.add(space);
		
		hPanel.setCellVerticalAlignment(image, HasAlignment.ALIGN_MIDDLE);
		hPanel.setCellVerticalAlignment(msg, HasAlignment.ALIGN_MIDDLE);
		hPanel.setCellHorizontalAlignment(image, HasAlignment.ALIGN_CENTER);
		hPanel.setCellWidth(image, "30px");
		hPanel.setCellWidth(space, "7px");
		
		hPanel.setHeight("25px");
		
		msg.setStyleName("okm-NoWrap");
		
		super.hide();
		setWidget(hPanel);
	}
	
	/**
	 * Refresh
	 */
	public void refresh() {
		if (flag_saveSearch) {
			int left = ((Main.get().mainPanel.search.getRight()-200)/2) + Main.get().mainPanel.search.getLeft() + Search.SPLITTER_WIDTH +
			   		   ExtendedDockPanel.VERTICAL_BORDER_PANEL_WIDTH;;
			int top =  ((Main.get().mainPanel.search.searchBrowser.topHeight-40)/2) + TopPanel.PANEL_HEIGHT + SearchBrowser.SPLITTER_HEIGHT;
			setPopupPosition(left,top);
			show();
		} else {
			hide();
		}
	}
	
	/**
	 * Sets save search flag
	 */
	public void setFlag_saveSearch() {
		msg.setHTML(Main.i18n("search.saved.status.savesearch"));
		flag_saveSearch = true;
		refresh();
	}
	
	/**
	 * Unset save search flag
	 */
	public void unsetFlag_saveSearch() {
		flag_saveSearch = false;
		refresh();
	}
}