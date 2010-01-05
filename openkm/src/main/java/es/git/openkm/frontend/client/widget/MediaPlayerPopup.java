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

package es.git.openkm.frontend.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.util.Util;

/**
 * Media player popup
 * 
 * @author jllort
 *
 */
public class MediaPlayerPopup extends DialogBox {

	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private HTML text;
	private Button button;
	private HTML zoomIn;
	private HTML zoomOut;
	private HTML actualZoom;
	private String mediaUrl="";
	private int actualRatio = 1;
	
	/**
	 * Media player
	 */
	public MediaPlayerPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		
		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		text= new HTML("<div id=\"mediaplayercontainer\"></div>\n");
		setText(Main.i18n("media.player.label"));
		button = new Button(Main.i18n("button.close"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		button.setStyleName("okm-Input");
		
		zoomIn = new HTML(Util.imageHTML("img/icon/actions/zoom_in.gif",Main.i18n("image.viewer.zoom.in")));
		zoomOut = new HTML(Util.imageHTML("img/icon/actions/zoom_out.gif",Main.i18n("image.viewer.zoom.out")));
		actualZoom = new HTML("x"+actualRatio);
		
		zoomIn.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (actualRatio<3) {
					actualRatio++;
					changeMediaFileSize();
				}
			}
		});
		
		zoomOut.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (actualRatio>1) {
					actualRatio--;
					changeMediaFileSize();
				}
			}
		});
		
		hPanel.setHeight("30");
		hPanel.setWidth("100%");
		hPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		hPanel.addStyleName("okm-ToolBar");
		HTML space1 = space();
		HTML space2 = space();
		HTML space3 = space();
		HTML separator1 = separator();
		hPanel.add(space1);
		hPanel.add(zoomIn);
		hPanel.add(space2);
		hPanel.add(zoomOut);
		hPanel.add(separator1);
		hPanel.add(space3);
		hPanel.add(actualZoom);
		
		hPanel.setCellWidth(zoomIn, "20px");
		hPanel.setCellWidth(zoomOut, "20px");
		hPanel.setCellWidth(space1, "5px");
		hPanel.setCellWidth(space2, "5px");
		hPanel.setCellWidth(space3, "5px");
		hPanel.setCellWidth(separator1, "5px");
		hPanel.setCellHorizontalAlignment(actualZoom, HasAlignment.ALIGN_RIGHT);
		hPanel.setCellHorizontalAlignment(zoomIn, HasAlignment.ALIGN_CENTER);
		hPanel.setCellHorizontalAlignment(zoomIn, HasAlignment.ALIGN_CENTER);
		
		vPanel.add(hPanel);
		vPanel.add(text);
		vPanel.add(new HTML("<BR>"));
		vPanel.add(button);
		vPanel.add(new HTML("<BR>"));
		
		vPanel.setWidth("400px");
		vPanel.setHeight("280px");
		
		
		vPanel.setCellHorizontalAlignment(button, HasAlignment.ALIGN_CENTER);

		hide();
		setWidget(vPanel);
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("media.player.label"));
		button.setHTML(Main.i18n("button.close"));
		zoomIn.setHTML(Util.imageHTML("img/icon/actions/zoom_in.gif",Main.i18n("image.viewer.zoom.in")));
		zoomOut.setHTML(Util.imageHTML("img/icon/actions/zoom_out.gif",Main.i18n("image.viewer.zoom.out")));
	}
	
	/**
	 * Set the media file to reproduce
	 * 
	 * @param mediaUrl The media file url
	 */
	public void setMediaFile(String mediaUrl) {
		this.mediaUrl = mediaUrl;
		Util.removeMediaPlayer();
		text.setHTML("<div id=\"mediaplayercontainer\"></div>\n");
		Util.createMediaPlayer(mediaUrl, "400", "280");
		actualZoom.setHTML("x"+actualRatio + "&nbsp;");
	}
	
	/**
	 * Gets the HTML space code
	 * 
	 * @return Space tool bar code
	 */
	private HTML space() {
		HTML space = new HTML(" ");
		space.setStyleName("okm-ToolBar-space");
		return space;
	}
	
	/**
	 * Gets the HTML separator code
	 * 
	 * @return Separator tool bar
	 */
	private HTML separator() {
		HTML space = new HTML(Util.imageHTML("img/icon/toolbar/separator.gif"));
		return space;
	}
	
	/**
	 * Changes the media file size
	 */
	private void changeMediaFileSize() {
		int width = (400*actualRatio);
		int height = (280*actualRatio);
		
		// Setting size -> evaluates width/height ratio
		if ((new Double(Window.getClientWidth()).doubleValue()/new Double(Window.getClientHeight()).doubleValue())>1.43) {
			if (height>Window.getClientHeight()-110) {
				height = Window.getClientHeight()-110;
				width = new Double(height*1.43).intValue();
			} 
		} else {
			if (width>(Window.getClientWidth()-50)){
				width = Window.getClientWidth()-50;
				height = new Double(width*0.57).intValue();
			}
		}
		
		Util.createMediaPlayer(mediaUrl, ""+width, ""+height);
		actualZoom.setHTML("x"+actualRatio + "&nbsp;");
		center();
	}
}