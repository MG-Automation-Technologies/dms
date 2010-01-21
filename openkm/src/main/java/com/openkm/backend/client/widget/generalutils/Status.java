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

package com.openkm.backend.client.widget.generalutils;

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
	private boolean flag_properties	= false;
	private boolean flag_workflow	= false;
	private boolean flag_export		= false;
	private boolean flag_import		= false;
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
		if (flag_workflow || flag_properties || flag_export || flag_import) {
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
		if (flag_workflow || flag_properties || flag_export || flag_import) {
			setPopupPosition(left, top);
			show();
		} else {
			hide();
		}
	}
	
	/**
	 * Sets get wokflow flag
	 */
	public void setFlag_workflow() {
		msg.setHTML(Main.i18n("general.util.registering.workflow"));
		flag_workflow= true;
	}
	
	/**
	 * Unset get workflow flag
	 */
	public void unsetFlag_workflow() {
		flag_workflow= false;
		refresh();
	}
	
	/**
	 * Sets get properties flag
	 */
	public void setFlag_properties() {
		msg.setHTML(Main.i18n("general.util.registering.properties"));
		flag_properties= true;
	}
	
	/**
	 * Unset get properties flag
	 */
	public void unsetFlag_properties() {
		flag_properties= false;
		refresh();
	}
	
	/**
	 * Sets get export flag
	 */
	public void setFlag_export() {
		msg.setHTML(Main.i18n("general.util.exporting"));
		flag_export= true;
	}
	
	/**
	 * Unset get export flag
	 */
	public void unsetFlag_export() {
		flag_export= false;
		refresh();
	}
	
	/**
	 * Sets get import flag
	 */
	public void setFlag_import() {
		msg.setHTML(Main.i18n("general.util.importing"));
		flag_import= true;
	}
	
	/**
	 * Unset get import flag
	 */
	public void unsetFlag_import() {
		flag_import= false;
		refresh();
	}
}