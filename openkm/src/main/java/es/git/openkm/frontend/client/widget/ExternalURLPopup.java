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

package es.git.openkm.frontend.client.widget;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;

/**
 * ExternalURLPopup
 * 
 * @author jllort
 *
 */
public class ExternalURLPopup extends DialogBox {
	
	private VerticalPanel vPanel;
	private Button button;
	private Frame iframe;
	private String property = "";
	
	/**
	 * ExternalURLPopup
	 */
	public ExternalURLPopup() {
		
		// Establishes auto-close when click outside
		super(false,true);
		
		vPanel = new VerticalPanel();
		
		iframe = new Frame("about:blank");
		DOM.setElementProperty(iframe.getElement(), "frameborder", "0");
		DOM.setElementProperty(iframe.getElement(), "marginwidth", "0");
		DOM.setElementProperty(iframe.getElement(), "marginheight", "0");
		//DOM.setElementProperty(iframe.getElement(), "z-index", "10000");
		iframe.setStyleName("okm-Popup-text");
		
		button = new Button(Main.i18n("button.close"), new ClickListener(){
			public void onClick(Widget sender) {
				Log.debug("onClick("+sender+")");
				hide();
				
				Log.debug("onClick: void");
			}
		});
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(iframe);
		vPanel.add(new HTML("<br>"));
		vPanel.add(button);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(iframe, HorizontalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(button, HorizontalPanel.ALIGN_CENTER);
		
		button.setStyleName("okm-Button");

		setWidget(vPanel);
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n(property));
		button.setText(Main.i18n("button.close"));
	}
	
	/**
	 * Show the popup error
	 * 
	 * @param msg Error message
	 */
	public void show(String property, String url, int width, int height) {
		this.property = property;
		setText(Main.i18n(property));
		setSize(""+width,""+height);
		iframe.setSize(""+(width-20)+"px",""+(height-40)+"px");
		iframe.setUrl(url);
		center();
	}
}