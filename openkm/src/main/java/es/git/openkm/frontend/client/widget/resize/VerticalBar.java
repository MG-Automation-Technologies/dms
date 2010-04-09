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

package es.git.openkm.frontend.client.widget.resize;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.panel.ExtendedSizeComposite;

/**
 * Vertical Bar
 * 
 * @author jllort
 *
 */
public class VerticalBar extends ExtendedSizeComposite implements MouseListener {
	
	private VerticalPanel panel;
	private Label label;
	//public VerticalBarPopup verticalBarPopup;
	
	/**
	 * Vertical Bar
	 */
	public VerticalBar(){
		label = new Label();
		panel = new VerticalPanel();
		//verticalBarPopup = new VerticalBarPopup();
		
		panel.setStyleName("okm-VerticalBar");
		label.setSize("100%","100%");
		label.setStyleName("okm-VerticalBar-Point");
		label.addMouseListener(this);
		
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
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.MouseListener#onMouseDown(com.google.gwt.user.client.ui.Widget, int, int)
	 */
	public void onMouseDown(Widget sender, int x, int y){
		int height = Main.get().mainPanel.getLeftPanelHeight();
		Main.get().verticalBarSplitter.setSize(label.getAbsoluteLeft(), label.getAbsoluteTop(), height);
		Main.get().verticalBarSplitter.setPosition();
		Main.get().verticalBarSplitter.show();
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.MouseListener#onMouseEnter(com.google.gwt.user.client.ui.Widget)
	 */
	public void onMouseEnter(Widget sender) {
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.MouseListener#onMouseLeave(com.google.gwt.user.client.ui.Widget)
	 */
	public void onMouseLeave(Widget sender) {
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.MouseListener#onMouseMove(com.google.gwt.user.client.ui.Widget, int, int)
	 */
	public void onMouseMove(Widget sender, int x, int y) {
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.MouseListener#onMouseUp(com.google.gwt.user.client.ui.Widget, int, int)
	 */
	public void onMouseUp(Widget sender, int x, int y) {
	}
}