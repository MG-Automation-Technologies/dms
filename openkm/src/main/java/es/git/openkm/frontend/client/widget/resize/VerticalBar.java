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

package es.git.openkm.frontend.client.widget.resize;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.panel.ExtendedSizeComposite;

/**
 * Vertical Bar
 * 
 * @author jllort
 *
 */
public class VerticalBar extends ExtendedSizeComposite {
	
	private VerticalPanel panel;
	private Label label;
	
	/**
	 * Vertical Bar
	 */
	public VerticalBar(){
		label = new Label();
		panel = new VerticalPanel();
		
		panel.setStyleName("okm-VerticalBar");
		label.setSize("100%","100%");
		label.setStyleName("okm-VerticalBar-Point");
		label.addMouseDownHandler(new MouseDownHandler(){
			@Override
			public void onMouseDown(MouseDownEvent event) {
				int height = Main.get().mainPanel.getLeftPanelHeight();
				Main.get().verticalBarSplitter.setSize(label.getAbsoluteLeft(), label.getAbsoluteTop(), height);
				Main.get().verticalBarSplitter.setPosition();
				Main.get().verticalBarSplitter.show();
			}});
		
		panel.add(label);
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