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

package com.openkm.backend.client.widget.config;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.ScrollPanel;

import com.openkm.backend.client.config.Config;

/**
 * GeneralUtilsPanel
 * 
 * @author jllort
 *
 */
public class ConfigPanel extends Composite {
	
	// Added to include external jsp
	private ScrollPanel scrollPanel;
	private Frame iframe;
	
	/**
	 * GeneralUtilsPanel
	 */
	public ConfigPanel() {
		// Added to include external jsp
		iframe = new Frame("about:blank");
		scrollPanel = new ScrollPanel(iframe);
		
		DOM.setElementProperty(iframe.getElement(), "frameborder", "0");
		DOM.setElementProperty(iframe.getElement(), "marginwidth", "0");
		DOM.setElementProperty(iframe.getElement(), "marginheight", "0");
		//DOM.setElementProperty(iframe.getElement(), "allowtransparency", "true"); // Commented because on IE show clear if allowtransparency=true
		DOM.setElementProperty(iframe.getElement(), "scrolling", "auto");

		iframe.setUrl("/OpenKM"+Config.INSTALL+"/admin/config.jsp");
		iframe.setSize("100%", "100%");
		iframe.setStyleName("okm-noBorder");
		scrollPanel.setStyleName("okm-Input");
		
		initWidget(scrollPanel);
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
	}
	
	/**
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		scrollPanel.setSize(""+(width-2), ""+(height-2));
	}
}