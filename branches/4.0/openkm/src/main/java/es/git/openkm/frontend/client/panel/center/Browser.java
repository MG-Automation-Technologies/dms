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

package es.git.openkm.frontend.client.panel.center;

import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.panel.ExtendedComposite;
import es.git.openkm.frontend.client.widget.filebrowser.FileBrowser;
import es.git.openkm.frontend.client.widget.properties.TabMultiple;
import es.git.openkm.frontend.client.widget.resize.HorizontalBar;

/**
 * Browser panel
 * 
 * @author jllort
 *
 */
public class Browser extends ExtendedComposite {
	
	private VerticalPanel panel;
	private VerticalPanel tabPropertiesPanel;
	
	public FileBrowser fileBrowser;
	public TabMultiple tabMultiple;

	private HorizontalBar horizontalBar;
	
	public int topHeight;
	public int labelHeight= 10;
	public int bottomHeight;
	
	public Browser() {
		panel = new VerticalPanel();
		horizontalBar = new HorizontalBar();
		fileBrowser = new FileBrowser();
		tabPropertiesPanel = new VerticalPanel();
		tabMultiple = new TabMultiple();
		
		tabPropertiesPanel.add(tabMultiple);
		tabPropertiesPanel.setStyleName("okm-Properties-Tab");

		panel.add(fileBrowser);
		panel.add(horizontalBar);
		panel.add(tabPropertiesPanel);
		panel.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
		initWidget(panel);
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
		topHeight = (height-labelHeight)/2;
		bottomHeight = height - (topHeight + labelHeight);
		panel.setSize(""+width, ""+height);
		fileBrowser.setSize(""+width, ""+topHeight);
		// Resize the scroll panel on filebrowser
		// We substrat 2 pixels for fileBrowser pixels on width and the status fixed sixe on height 
		fileBrowser.table.setPixelSize(width-2, this.topHeight-2-FileBrowser.STATUS_SIZE);
		horizontalBar.setSize(""+width, ""+labelHeight);
		// Resize the scroll panel on tab properties 
		// We substract 2 pixels for width and heigh generated by border line
		tabMultiple.setSize(width-2,bottomHeight-2);
		tabPropertiesPanel.setSize(""+width, ""+bottomHeight);
	}
	
	/**
	 * Set the size on resize
	 * 
	 * @param width The max width of the widget
	 * @param topHeight The height of the center top panel
	 * @param height The total heigh of top / bottom panels
	 */
	public void setSize(int width, int topHeight, int height) {
		this.topHeight = topHeight;
		bottomHeight = height - (this.topHeight + labelHeight);
		panel.setSize(""+width, ""+height);
		fileBrowser.setSize(""+width, ""+this.topHeight);
		// Resize the scroll panel on filebrowser
		// We substrat 2 pixels for fileBrowser pixels on width and the status fixed sixe on height 
		fileBrowser.table.setPixelSize(width-2, this.topHeight-2-FileBrowser.STATUS_SIZE);
		horizontalBar.setSize(""+width, ""+labelHeight);
		// Resize the scroll panel on tab properties 
		// We substract 2 pixels for width and heigh generated by border line
		tabMultiple.setSize(width-2,bottomHeight-2);
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