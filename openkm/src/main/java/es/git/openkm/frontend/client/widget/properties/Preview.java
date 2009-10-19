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

package es.git.openkm.frontend.client.widget.properties;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

import es.git.openkm.frontend.client.util.Util;

/**
 * Notes
 * 
 * @author jllort
 *
 */
public class Preview extends Composite {

	private HorizontalPanel hPanel;
	private HTML text;
	private int width = 0;
	private int height = 0;
	
	public Preview () {
		hPanel = new HorizontalPanel();
		text= new HTML("<div id=\"pdfviewercontainer\"></div>\n");
		hPanel.add(text);
		hPanel.setCellHorizontalAlignment(text, HasAlignment.ALIGN_CENTER);
		hPanel.setCellVerticalAlignment(text, HasAlignment.ALIGN_MIDDLE);
		
		initWidget(hPanel);
	}
	
	public void setPixelSize(int width, int height) {
		super.setPixelSize(width, height);
		this.width = width;
		this.height = height;
	}
	
	public void init() {
		text.setHTML("<div id=\"pdfviewercontainer\"></div>\n"); // needed for rewriting purpose
		Util.createPDFViewer("/OpenKM/js/zviewer/test.swf", ""+(width-20), ""+(height-20));
	}
}
