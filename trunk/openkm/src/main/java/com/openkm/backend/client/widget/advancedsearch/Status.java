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

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

import com.openkm.backend.client.Main;

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
	private boolean flag_search	= false;
	int left = 0;
	int top = 0;
	
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
	public void refresh(Widget widget) {
		if (flag_search ) {
			show();
			left = widget.getAbsoluteLeft() + ((widget.getOffsetWidth()-getOffsetWidth())/2);
			top = widget.getAbsoluteTop() + ((widget.getOffsetHeight()-getOffsetHeight())/2);
			setPopupPosition(left, top);
		} else {
			hide();
		}
	}
	
	/**
	 * refresh
	 */
	public void refresh() {
		if ( flag_search) {
			setPopupPosition(left, top);
			show();
		} else {
			hide();
		}
	}
	
	
	
	/**
	 * Sets get search flag
	 */
	public void setFlag_search() {
		msg.setHTML(Main.i18n("advanced.search.status.search"));
		flag_search= true;
	}
	
	/**
	 * Unset get search flag
	 */
	public void unsetFlag_search() {
		flag_search= false;
		refresh();
	}
}