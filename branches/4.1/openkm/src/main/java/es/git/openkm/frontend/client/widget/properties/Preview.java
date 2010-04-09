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

package es.git.openkm.frontend.client.widget.properties;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.config.Config;
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
	private boolean previewAvailable = false;
	
	/**
	 * Preview
	 */
	public Preview() {
		hPanel = new HorizontalPanel();
		text= new HTML("<div id=\"pdfviewercontainer\"></div>\n");
		hPanel.add(text);
		hPanel.setCellHorizontalAlignment(text, HasAlignment.ALIGN_CENTER);
		hPanel.setCellVerticalAlignment(text, HasAlignment.ALIGN_MIDDLE);
		
		initWidget(hPanel);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setPixelSize(int, int)
	 */
	public void setPixelSize(int width, int height) {
		super.setPixelSize(width, height);
		this.width = width;
		this.height = height;
	}
	
	public void showEmbedSWF(String uuid) {
		if (previewAvailable) {
			String url = Config.OKMDownloadServlet +"?toSwf&inline&uuid=" + URL.encodeComponent(uuid);
			text.setHTML("<div id=\"pdfviewercontainer\"></div>\n"); // needed for rewriting purpose
			Util.createPDFViewer(url, ""+width, ""+height);
		} else {
			text.setHTML("<div id=\"pdfviewercontainer\" align=\"center\"><br><br>" + Main.i18n("preview.unavailable") + "</div>\n"); // needed for rewriting purpose
		}
	}
	
	/**
	 * Sets the boolean value if previewing document is available
	 * 
	 * @param previewAvailable
	 */
	public void setPreviewAvailable(boolean previewAvailable) {
		this.previewAvailable = previewAvailable;
	}
	
	/**
	 * langRefresh
	 */
	public void langRefresh() {
		if (!previewAvailable) {
			text.setHTML("<div id=\"pdfviewercontainer\" align=\"center\"><br><br>" + Main.i18n("preview.unavailable") + "</div>\n"); // needed for rewriting purpose
		}
	}
}
