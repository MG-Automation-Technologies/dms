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

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.widget.eastereggs.Futurama;

/**
 * About popup
 * 
 * @author jllort
 *
 */
public class AboutPopup extends DialogBox implements ClickListener {
	
	private VerticalPanel vPanel;
	private Image logo;
	private HTML text;
	private Button button;
	private String msg1 = "<b>Be Open, my friend!</b><br>";
	private String copy = "&nbsp;&copy 2006 - 2009 GIT Consultors S.L. <br><br>";
	private String team = "<b>OpenKM Team</b><br>" + 
							"Francisco José Ávila Bermejo (<i>Monkiki</i>)<br>"+
							"Josep Llort Tella (<i>Darkman97i</i>)<br><br>";
	
	private String web = "<a href=\"http://www.openkm.com\" target=\"_blank\">http://www.openkm.com</a><br><br>";
	private String version = "Version 4.0&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	
	private Futurama futurama;
	
	/**
	 * About popup
	 */
	public AboutPopup() {
		
		// Establishes auto-close when click outside
		super(false,true);
		futurama = new Futurama();
		int left = (Window.getClientWidth()-300)/2;
		int top = (Window.getClientHeight()-280)/2;
		
		vPanel = new VerticalPanel();
		text = new HTML();
		button = new Button(Main.i18n("button.close"), this);
		logo = new Image("img/logo_openkm.gif");
		
		vPanel.setWidth("300px");
		vPanel.setHeight("195px");
		button.setStyleName("okm-Button");
				
		text.setHTML(msg1);
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(logo);
		HTML htmlVersion = new HTML(version);
		vPanel.add(htmlVersion);
		vPanel.add(new HTML("<br>"));
		vPanel.add(text);
		HTML htmlWeb = new HTML(web);
		vPanel.add(htmlWeb);
		HTML htmlTeam = new HTML(team);
		vPanel.add(htmlTeam);
		HTML htmlCopy = new HTML(copy);
		vPanel.add(htmlCopy);
		vPanel.add(button);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(logo, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(htmlVersion, HasAlignment.ALIGN_RIGHT);
		vPanel.setCellHorizontalAlignment(text, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(htmlWeb, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(htmlTeam, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(htmlCopy, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(button, HasAlignment.ALIGN_CENTER);
		
		setPopupPosition(left,top);

		super.hide();
		setWidget(vPanel);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.ClickListener#onClick(com.google.gwt.user.client.ui.Widget)
	 */
	public void onClick(Widget sender) {
		super.hide();
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("about.label"));
		button.setText(Main.i18n("button.close"));
	}
	
	/**
	 * Show the popup error
	 * 
	 * @param msg Error message
	 */
	public void show() {
		setText(Main.i18n("about.label"));
		text.setHTML(msg1);
		logo.setUrl("img/logo_openkm.gif");
		reset();
		super.show();
	}
	
	/**
	 * Change the image
	 * 
	 * @param img The image
	 */
	public void changeImg(String img){
		logo.setUrl(img);
	}
	
	/**
	 * Sets the text ( used by easter egg )
	 * 
	 * @param msg1 The text
	 */
	public void setText(String msg1){
		text.setHTML(msg1);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.PopupPanel#onKeyPressPreview(char, int)
	 */
	public boolean onKeyPressPreview(char key, int modifiers) {
		futurama.evaluateKey(key);
	    return true;
	}

	/**
	 * Reset values
	 */
	public void reset() {
		futurama.reset();
	}
}