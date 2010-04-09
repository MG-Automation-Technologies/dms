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

package es.git.openkm.frontend.client.widget.startup;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;

/**
 * @author jllort
 *
 */
public class StartUpPopup extends DialogBox implements ClickListener {
	private StartUpImageBundle suImageBundle = (StartUpImageBundle) GWT.create(StartUpImageBundle.class);
	private VerticalPanel vPanel;
	private VerticalPanel status;
	private ScrollPanel scrollPanel;
	private FlexTable table;
	public Button button;
	public int actual = -1;
	private List msgList = new ArrayList();
	
	public StartUpPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		
		vPanel = new VerticalPanel();
		status = new VerticalPanel();
		table = new FlexTable();
		button = new Button(Main.i18n("button.close"), this);
		scrollPanel = new ScrollPanel(status);
		scrollPanel.setAlwaysShowScrollBars(false);
		scrollPanel.setSize("390","160");
		scrollPanel.setStyleName("okm-Input");
		
		status.setWidth("390");
		
		vPanel.add(new HTML("&nbsp;"));
		vPanel.add(scrollPanel);
		vPanel.add(new HTML("&nbsp;"));
		vPanel.add(table);
		vPanel.add(button);
		vPanel.add(new HTML("&nbsp;"));
		
		button.setVisible(false);
		button.setStyleName("okm-Button");
		
		vPanel.setCellHorizontalAlignment(scrollPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(table, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(button, HasAlignment.ALIGN_CENTER);
		vPanel.setCellVerticalAlignment(scrollPanel, HasAlignment.ALIGN_MIDDLE);
		
		int left = (Window.getClientWidth()-380)/2;
		int top = (Window.getClientHeight()-220)/2;
		vPanel.setWidth("420px");
		vPanel.setHeight("220px");
		
		for (int i=0; i<StartUp.STARTUP_KEEP_ALIVE; i++) {
			table.setWidget(0, i, suImageBundle.loadedDisabledIcon().createImage());
		}
		
		setText(Main.i18n("startup.openkm"));
		setPopupPosition(left,top);
		setWidget(vPanel);
	}

	/**
	 * Add new status message
	 * 
	 * @param text The text
	 * @param actual The actual status
	 */
	public void addStatus(String text, int actual){
		HTML tmpHTML;
		
		// We've jumped normally due to errors, must mark as incorrect
		if (this.actual+1 < actual) {
			for (int i = this.actual+1; i<actual; i++) {
				tmpHTML= new HTML("&nbsp;" + Main.get().startUp.getStatusMsg(i));
				tmpHTML.setStyleName("okm-Input-Error");
				tmpHTML.setWordWrap(false);
				status.add(tmpHTML);
				scrollPanel.ensureVisible(tmpHTML);
				table.setWidget(0, i, suImageBundle.loadedErrorIcon().createImage());
			}
		}
		
		tmpHTML= new HTML("&nbsp;" + text);
		tmpHTML.setWordWrap(false);
		msgList.add(tmpHTML);
		if (msgList.size()>11) {
			status.remove((HTML) msgList.remove(0)); // Only shows 10 messages on panel, when arrives 10 remove the first
		}
		status.add(tmpHTML);
		scrollPanel.ensureVisible(tmpHTML);
		table.setWidget(0, actual, suImageBundle.loadedIcon().createImage());
		this.actual = actual;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.ClickListener#onClick(com.google.gwt.user.client.ui.Widget)
	 */
	public void onClick(Widget sender) {
		Main.get().startUp.disable();
		hide();
	}
}
