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

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;

/**
 * VerticalBarSplitter
 * 
 * @author jllort
 *
 */
public class VerticalBarSplitter extends Composite implements HasWidgets {
	
	private static int CORNER_RIGHT_MARGIN = 100; // Can't resize right to major pos ( window width - CORNER_RIGHT_MARGIN )
	private static int CORNER_LEFT_MARGIN = 180; // Can't resize left to minor pos ( window width - CORNER_LEFT_MARGIN )
	
	private int labelSize = 10;
	private int posX = 0;
	private int posY = 0;
	private int clientWidth = 0;
	boolean dragging = false;
	private Label label;
	private VerticalPanel panel;
	private Event event = null;
	private NativePreviewEvent nPEvent = null;
	private HandlerRegistration handlerRegistration = null;	
	
	/**
	 * VerticalBar splitter
	 */
	public VerticalBarSplitter() {
		// Establishes false to non auto-close when click outside
		label = new Label("");
		panel = new VerticalPanel();
		
		label.sinkEvents(Event.MOUSEEVENTS);
		label.addStyleName("okm-Popup-VerticalBar-Point");
		label.setSize("100%","10");
		
		label.addMouseDownHandler(new MouseDownHandler(){
			@Override
			public void onMouseDown(MouseDownEvent event) {
				dragging = true;
				RootPanel.get().setWidgetPosition(Main.get().verticalBarSplitter , event.getClientX(), event.getClientY());
			}
		});
		
		label.addMouseMoveHandler(new MouseMoveHandler(){
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				if (dragging) {
					int posX = event.getClientX() -5;
					
					if ((clientWidth-CORNER_RIGHT_MARGIN) < (posX)) { 
						RootPanel.get().setWidgetPosition(Main.get().verticalBarSplitter , clientWidth-CORNER_RIGHT_MARGIN, posY );
					} else if (CORNER_LEFT_MARGIN > posX) {
						RootPanel.get().setWidgetPosition(Main.get().verticalBarSplitter , CORNER_LEFT_MARGIN, posY );
					} else {
						RootPanel.get().setWidgetPosition(Main.get().verticalBarSplitter , posX, posY );
					}
				}
			}
		});
		
		label.addMouseUpHandler(new MouseUpHandler(){
			@Override
			public void onMouseUp(MouseUpEvent event) {
				DOM.releaseCapture(label.getElement());
				if (handlerRegistration!=null) {
					handlerRegistration.removeHandler();
				}
				dragging = false;
				int posX = event.getClientX() -5;
				
				RootPanel.get().setWidgetPosition(Main.get().verticalBarSplitter , posX , posY);

				if ((clientWidth-CORNER_RIGHT_MARGIN) < (posX)) {
					Main.get().mainPanel.verticalResize(clientWidth-CORNER_RIGHT_MARGIN);
				} else if (CORNER_LEFT_MARGIN > posX) {
					Main.get().mainPanel.verticalResize(CORNER_LEFT_MARGIN);
				} else {
					Main.get().mainPanel.verticalResize(posX);
				}
				panel.setVisible(false);
				
			}});
		
		panel.add(label);
		panel.setStyleName("okm-Popup-VerticalBar");
		panel.setVisible(false);
		
		initWidget(panel);	
	}
	
	/**
	 * Sets the popup size
	 * 
	 * @param x The x value
	 * @param y The y value
	 * @param height The height
	 */
	public void setSize(int x, int y, int height) {
		this.posX = x;
		
		label.setHeight(""+height);
		label.setHeight(""+height);
		panel.setWidth(""+labelSize);
		panel.setWidth(""+labelSize);
	}
	
	/**
	 * Sets the popup position
	 */
	public void setPosition() {
		posY = Main.get().mainPanel.getLeftCoordenates().getY();
		RootPanel.get().setWidgetPosition(Main.get().verticalBarSplitter , posX, posY);
	}
		
	/**
	 * Show the popup
	 */
	public void show() {
		clientWidth = Window.getClientWidth();
		panel.setVisible(true);
		dragging = true;
		handlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				nPEvent = event;
			}
		});
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
	public Iterator<Widget> iterator() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#remove(com.google.gwt.user.client.ui.Widget)
	 */
	public boolean remove(Widget w) {
		return true;
	}
}