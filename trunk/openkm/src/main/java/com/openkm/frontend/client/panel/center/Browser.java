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

package com.openkm.frontend.client.panel.center;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.openkm.frontend.client.panel.ExtendedComposite;
import com.openkm.frontend.client.widget.filebrowser.FileBrowser;
import com.openkm.frontend.client.widget.properties.TabMultiple;

/**
 * Browser panel
 * 
 * @author jllort
 *
 */
public class Browser extends ExtendedComposite {
	
	private final static int SPLITTER_HEIGHT = 10;
	
	private VerticalSplitPanelExtended verticalSplitPanel;
	private VerticalPanel tabPropertiesPanel;
	
	public FileBrowser fileBrowser;
	public TabMultiple tabMultiple;
	
	private boolean isResizeInProgress = false;
	public int width = 0;
	public int topHeight = 0;
	public int bottomHeight = 0;
	
	public Browser() {
		verticalSplitPanel = new VerticalSplitPanelExtended();
		fileBrowser = new FileBrowser();
		tabPropertiesPanel = new VerticalPanel();
		tabMultiple = new TabMultiple();
		
		tabPropertiesPanel.add(tabMultiple);
		tabPropertiesPanel.setStyleName("okm-Properties-Tab");

		verticalSplitPanel.getSplitPanel().setTopWidget(fileBrowser);
		verticalSplitPanel.getSplitPanel().setBottomWidget(tabPropertiesPanel);
		
		verticalSplitPanel.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				if (verticalSplitPanel.getSplitPanel().isResizing()) {
					if (!isResizeInProgress) {
						isResizeInProgress = true;
						onSplitResize();
					}
				} 
			}
		});
		
		verticalSplitPanel.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				if (isResizeInProgress) {
					isResizeInProgress = false;
				}
			}
		});
		
		initWidget(verticalSplitPanel);
	}
	
	/**
	 * onSplitResize
	 */
	public void onSplitResize() {
		final int resizeUpdatePeriod = 20; // ms ( Internally splitter is refreshing each 20 ms )
		if (isResizeInProgress) {
			new Timer() {
				@Override
				public void run() {
					resizePanels(); // Always making resize
					if (isResizeInProgress) {
						onSplitResize();
					} 
				}
			}.schedule(resizeUpdatePeriod);
		}
	}
	
	/**
	 * Refresh language values
	 */
	public void langRefresh() {
		fileBrowser.langRefresh();	
		tabMultiple.langRefresh();
	}
	
	/**
	 * Sets the size on initialization
	 * 
	 * @param width The max width of the widget
	 * @param height The max height of the widget
	 */
	public void setSize(int width, int height) {
		topHeight = (height-SPLITTER_HEIGHT)/2;
		bottomHeight = height - (topHeight + SPLITTER_HEIGHT);
		verticalSplitPanel.setSize(""+width, ""+height);
		verticalSplitPanel.getSplitPanel().setSplitPosition(""+topHeight);
		fileBrowser.setSize(""+width, ""+topHeight);
		// Resize the scroll panel on filebrowser
		// We substrat 2 pixels for fileBrowser pixels on width and the status fixed sixe on height 
		fileBrowser.table.setPixelSize(width-2, topHeight-2-FileBrowser.STATUS_SIZE);
		// Resize the scroll panel on tab properties 
		// We substract 4 pixels for width and heigh generated by border line
		tabMultiple.setSize(width-2,bottomHeight-4);
		tabPropertiesPanel.setSize(""+width, ""+bottomHeight);
	}
	
	/**
	 * setWidth
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
		verticalSplitPanel.setWidth(""+width);
		fileBrowser.setWidth(""+width);
		// Resize the scroll panel on filebrowser
		// We substrat 2 pixels for fileBrowser pixels on width and the status fixed sixe on height 
		fileBrowser.table.setPixelSize(width-2, topHeight-2-FileBrowser.STATUS_SIZE);
		// Resize the scroll panel on tab properties 
		// We substract 4 pixels for width and heigh generated by border line
		tabMultiple.setSize(width-2,bottomHeight-4);
		tabPropertiesPanel.setSize(""+width, ""+bottomHeight);
	}
	
	/**
	 * Sets the panel width on resizing
	 * 
	 * @param left
	 * @param right
	 */
	private void resizePanels() {
		int total = 0;
		String value = DOM.getStyleAttribute (verticalSplitPanel.getSplitPanel().getElement(), "height");
		if (value.contains("px")) { value = value.substring(0,value.indexOf("px")); }
		total = Integer.parseInt(value);
		value = DOM.getStyleAttribute (DOM.getChild(DOM.getChild(verticalSplitPanel.getSplitPanel().getElement(),0), 0), "height");
		if (value.contains("px")) { value = value.substring(0,value.indexOf("px")); }
		topHeight = Integer.parseInt(value);
		value = DOM.getStyleAttribute (DOM.getChild(DOM.getChild(verticalSplitPanel.getSplitPanel().getElement(),0), 2), "top");
		if (value.contains("px")) { value = value.substring(0,value.indexOf("px")); }
		bottomHeight = total - Integer.parseInt(value);		
		fileBrowser.setHeight(""+topHeight);
		// Resize the scroll panel on filebrowser
		// We substrat 2 pixels for fileBrowser pixels on width and the status fixed sixe on height 
		fileBrowser.table.setPixelSize(width-2, topHeight-2-FileBrowser.STATUS_SIZE);
		// Resize the scroll panel on tab properties 
		// We substract 4 pixels for width and heigh generated by border line
		tabMultiple.setSize(width-2,bottomHeight-4);
		tabPropertiesPanel.setSize(""+width, ""+bottomHeight);
	}
	
	/**
	 * Value of the center top panel
	 *  
	 * @return The value of the center top panel
	 */
	public int getTopHeight() {
		return topHeight;
	}
}