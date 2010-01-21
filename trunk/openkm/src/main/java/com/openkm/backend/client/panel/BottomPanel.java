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

package com.openkm.backend.client.panel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Bottom panel
 * 
 * @author jllort
 *
 */
public class BottomPanel extends Composite {
	
	private HorizontalPanel panel;
	//private Label msg;
	private SimplePanel spLeft;
	private SimplePanel spRight;
	
	/**
	 * BottomPanel
	 */
	public BottomPanel() {
		panel = new HorizontalPanel();
		spLeft = new SimplePanel();
		spRight = new SimplePanel();
		//msg = new Label("OpenKM - Real Time Administrator Console");
		
		spLeft.setWidth("10");
		spRight.setWidth("10");
		
		panel.add(spLeft);
		//panel.add(msg);
		panel.add(spRight);
		panel.setCellWidth(spLeft,"10");
		panel.setCellWidth(spRight,"10");
		//panel.setCellHorizontalAlignment(msg, HasAlignment.ALIGN_CENTER);
		panel.setCellHorizontalAlignment(spRight, HasAlignment.ALIGN_RIGHT);
		//panel.setCellVerticalAlignment(msg, HasAlignment.ALIGN_MIDDLE);
		
		panel.setStyleName("okm-bottomPanel");
		panel.addStyleName("okm-DisableSelect");
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		panel.setSize("100%", "100%");
		initWidget(panel);
	}
	
	/**
	 * Sets the size
	 * 
	 * @param width the width size
	 * @param height the height size
	 */
	public void setSize(int width, int height) {
		panel.setSize(""+width, ""+height);
	}
}