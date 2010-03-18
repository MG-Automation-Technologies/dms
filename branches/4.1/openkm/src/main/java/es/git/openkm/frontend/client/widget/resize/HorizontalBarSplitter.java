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

import java.util.Iterator;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;

/**
 * HorizontalBarSplitter
 * 
 * @author jllort
 *
 */
public class HorizontalBarSplitter extends Composite implements HasWidgets {
	private static int CORNER_TOP_MARGIN = 50; // Can't resize top to major pos ( window height - CORNER_RIGHT_MARGIN )
	private static int CORNER_DOWN_MARGIN = 100; // Can't resize down to minor pos ( window height - CORNER_LEFT_MARGIN )
	
	private int labelHeight = 10;
	private int dragStartY = 0;
	boolean dragging = false;
	private int posX = 0;
	private int posY = 0;
	private int minTopY = 0;
	private int maxDownY = 0;
	private PopupPanel panel;
	private Label label;
	private Event event = null;
	EventPreview eventPreview = null;
	
	/**
	 * Horizontal splitter
	 */
	public HorizontalBarSplitter(){
		// Establishes false to non auto-close when click outside
		panel = new PopupPanel(false); 
		label = new Label("");
		
		eventPreview = new EventPreview() {
            public boolean onEventPreview(Event e) {
                event = e;
                return true;
            }
		};
		
		label.sinkEvents(Event.MOUSEEVENTS);
		label.setStyleName("okm-Popup-HorizontalBar-Point");
		label.setSize("10","100%");
		label.addMouseListener(new MouseListenerAdapter(){
			/* (non-Javadoc)
			 * @see com.google.gwt.user.client.ui.MouseListener#onMouseDown(com.google.gwt.user.client.ui.Widget, int, int)
			 */
			public void onMouseDown(Widget sender, int x, int y) {
				dragging = true;
			    dragStartY = y;
			}
						
			/* (non-Javadoc)
			 * @see com.google.gwt.user.client.ui.MouseListener#onMouseMove(com.google.gwt.user.client.ui.Widget, int, int)
			 */
			public void onMouseMove(Widget sender, int x, int y) {
				if (dragging) {
					int posY = DOM.eventGetClientY(event) -5;
					
					if (minTopY > posY) {
						RootPanel.get().setWidgetPosition(Main.get().horizontalBarSplitter , posX, minTopY );
					} else if (maxDownY < posY) {
						RootPanel.get().setWidgetPosition(Main.get().horizontalBarSplitter , posX, maxDownY );
					} else {
						RootPanel.get().setWidgetPosition(Main.get().horizontalBarSplitter , posX, posY );
					}
				}
				super.onMouseMove(sender, x, y);
			}
			
			/* (non-Javadoc)
			 * @see com.google.gwt.user.client.ui.MouseListener#onMouseUp(com.google.gwt.user.client.ui.Widget, int, int)
			 */
			public void onMouseUp(Widget sender, int x, int y) {
				DOM.releaseCapture(label.getElement());
				DOM.removeEventPreview(eventPreview);
				
				dragging = false;
				int posY = DOM.eventGetClientY(event);
				
				if (minTopY > posY) {
					Main.get().mainPanel.horizontalResize(minTopY - dragStartY);
				} else if (maxDownY < posY) {
					Main.get().mainPanel.horizontalResize(maxDownY - dragStartY);
				} else {
					Main.get().mainPanel.horizontalResize(posY - dragStartY);
				}
				
				panel.setVisible(false);
				
				super.onMouseUp(sender, x, y);
			}
		});
		
		panel.setStyleName("okm-Popup-HorizontalBar");
		panel.add(label);
		panel.setVisible(false);
		
		initWidget(panel);
	}
	
	/**
	 * Sets the popup size
	 * 
	 * @param x The x position
	 * @param y The y position
	 * @param width The width
	 * @param posX The real x position
	 */
	public void setSize(int x, int y, int width, int posX) {
		this.posY = y;
		this.posX = posX;
		
		label.setHeight(""+labelHeight);
		label.setWidth(""+width);

		panel.setWidth(""+width);
		panel.setHeight(""+labelHeight);
		panel.setStyleName("okm-Popup-HorizontalBar");		
	}
	
	/**
	 * Sets the position
	 */
	public void setPosition(){
		RootPanel.get().setWidgetPosition(Main.get().horizontalBarSplitter , posX, posY);
	}
	
	/**
	 * Show the popup
	 */
	public void show() {
		// Sets the max and minival Y pos values to control resize
		minTopY = Main.get().mainPanel.center.getY() + CORNER_TOP_MARGIN;
		maxDownY = Main.get().mainPanel.center.getY() + Main.get().mainPanel.center.getHeight() - CORNER_DOWN_MARGIN;
		
		panel.setVisible(true);
		dragging = true;
		DOM.addEventPreview(eventPreview);
		DOM.setCapture(label.getElement());
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#add(com.google.gwt.user.client.ui.Widget)
	 */
	public void add(Widget w) {
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#clear()
	 */
	public void clear(){
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#iterator()
	 */
	public Iterator iterator(){
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#remove(com.google.gwt.user.client.ui.Widget)
	 */
	public boolean remove(Widget w){
		return true;
	}
}