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

package es.git.openkm.frontend.client.panel.center;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Frame;

import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.panel.ExtendedComposite;

/**
 * Administration
 * 
 * @author jllort
 *
 */
public class Administration extends ExtendedComposite {
	
	private Frame iframe;
	
	/**
	 * Administration
	 */
	public Administration() {
		iframe = new Frame("about:blank");
		
		DOM.setElementProperty(iframe.getElement(), "frameborder", "0");
		DOM.setElementProperty(iframe.getElement(), "marginwidth", "0");
		DOM.setElementProperty(iframe.getElement(), "marginheight", "0");
		//DOM.setElementProperty(iframe.getElement(), "allowtransparency", "true"); // Commented because on IE show clear if allowtransparency=true
		DOM.setElementProperty(iframe.getElement(), "scrolling", "no");
		
		iframe.setUrl("/OpenKM"+Config.INSTALL+"/admin/index.jsp");
		iframe.setStyleName("okm-Input");
		
		initWidget(iframe);
	}
	
	/**
	 * Sets the size on initialization
	 * 
	 * @param width The max width of the widget
	 * @param height The max height of the widget
	 */
	public void setSize(int width, int height) {
		iframe.setPixelSize(width-2, height-2);
	}
	
}