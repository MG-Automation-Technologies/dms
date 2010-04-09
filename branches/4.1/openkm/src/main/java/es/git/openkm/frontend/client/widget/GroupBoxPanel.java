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

import com.google.gwt.user.client.DOM;  
import com.google.gwt.user.client.Element;  
import com.google.gwt.user.client.ui.FlowPanel;  
   
/**
 * GroupBoxPanel
 * 
 * @author jllort
 *
 */
public class GroupBoxPanel extends FlowPanel {  
   
	private Element legend;  
   
	/**
	 * GroupBoxPanel
	 */
	public GroupBoxPanel() {  
		Element fieldset = DOM.createFieldSet();  
		this.legend = DOM.createLegend();  
		DOM.appendChild(fieldset, legend);  
		setElement(fieldset);  
	}  
	   
	/**
	 * getCaption
	 * 
	 * @return
	 */
	public String getCaption() {  
		return DOM.getInnerText(this.legend);  
	}  
   
	/**
	 * setCaption
	 * 
	 * @param caption
	 */
	public void setCaption(String caption) {  
		DOM.setInnerText(this.legend, caption);  
	}  
}  