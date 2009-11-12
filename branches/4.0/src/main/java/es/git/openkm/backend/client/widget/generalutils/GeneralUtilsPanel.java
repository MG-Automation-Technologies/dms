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

package es.git.openkm.backend.client.widget.generalutils;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;

/**
 * GeneralUtilsPanel
 * 
 * @author jllort
 *
 */
public class GeneralUtilsPanel extends Composite {
	
	private VerticalSplitPanel verticalSplitPanel;
	public GeneralUtils generalUtils;
	private VerticalPanel sp;
	public HTML msg;
	private Frame iframe;
	
	/**
	 * GeneralUtilsPanel
	 */
	public GeneralUtilsPanel() {
		verticalSplitPanel = new VerticalSplitPanel();
		generalUtils = new GeneralUtils();
		sp = new VerticalPanel();
		msg = new HTML("&nbsp;");
		sp.add(msg);
		
		iframe = new Frame("about:blank");
		DOM.setElementProperty(iframe.getElement(), "frameborder", "0");
		DOM.setElementProperty(iframe.getElement(), "marginwidth", "0");
		DOM.setElementProperty(iframe.getElement(), "marginheight", "0");
		DOM.setElementProperty(iframe.getElement(), "scrolling", "yes");
		
		verticalSplitPanel.setTopWidget(generalUtils);
		verticalSplitPanel.setBottomWidget(sp);
		verticalSplitPanel.setSplitPosition("250");
		
		generalUtils.setStyleName("okm-Input");
		sp.setStyleName("okm-Input");
		
		iframe.setSize("100%", "100%");
		sp.setSize("100%","100%");
		
		initWidget(verticalSplitPanel);
	}
	
	/**
	 * Setting the msg
	 * 
	 * @param text
	 */
	public void setMsg(String text){
		removeAllWidgets();
		sp.add(msg);
		msg.setHTML(text);
	}
	
	/**
	 * Sets the url result
	 * 
	 * @param url
	 */
	public void setUrlResult(String url) {
		removeAllWidgets();
		iframe.setUrl(url);
		sp.add(iframe);
	}
	
	/**
	 * removeAllWidgets
	 */
	private void removeAllWidgets() {
		while (sp.getWidgetCount()>0) {
			sp.remove(0);
		}
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		generalUtils.langRefresh();
	}
}