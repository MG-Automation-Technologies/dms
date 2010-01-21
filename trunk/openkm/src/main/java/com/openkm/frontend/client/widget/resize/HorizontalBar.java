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

package com.openkm.frontend.client.widget.resize;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.Coordenates;

/**
 * Horizontal Bar
 * 
 * @author jllort
 *
 */
public class HorizontalBar extends Composite {
	
	private HorizontalPanel panel;
	private Label label;
	
	/**
	 * Horizontal Bar
	 */
	public HorizontalBar() {
		panel = new HorizontalPanel();
		panel.setStyleName("okm-HorizontalBar");
		label = new Label("");
		label.setSize("100%", "10px");
		label.setStyleName("okm-HorizontalBar-Point");
		
		label.addMouseDownHandler(new MouseDownHandler(){
			@Override
			public void onMouseDown(MouseDownEvent event) {
				int width = Main.get().mainPanel.getCenterCoordenates().getWidth();
				Coordenates verticalBar = Main.get().mainPanel.getVerticalBarCoordenates();
		    	int realPosX =  verticalBar.getX()+verticalBar.getWidth();
		    	Main.get().horizontalBarSplitter.setSize(label.getAbsoluteLeft(), label.getAbsoluteTop(),width, realPosX);
		    	Main.get().horizontalBarSplitter.setPosition();
		    	Main.get().horizontalBarSplitter.show();
			}
		});
		
		panel.add(label);
		initWidget(panel);
	}
}