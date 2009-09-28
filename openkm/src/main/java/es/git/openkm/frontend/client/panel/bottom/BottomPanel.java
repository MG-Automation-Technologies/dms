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

package es.git.openkm.frontend.client.panel.bottom;

import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.panel.ExtendedSizeComposite;
import es.git.openkm.frontend.client.widget.UserInfo;

/**
 * Bottom panel
 * 
 * @author jllort
 *
 */
public class BottomPanel extends ExtendedSizeComposite {
	
	private HorizontalPanel panel;
	private Label statusMsg;
	private SimplePanel spLeft;
	private SimplePanel spRight;
	public UserInfo userInfo;
	private String key = "";
	
	/**
	 * BottomPanel
	 */
	public BottomPanel() {
		userInfo = new UserInfo();
		panel = new HorizontalPanel();
		spLeft = new SimplePanel();
		spRight = new SimplePanel();
		statusMsg = new Label("");
		statusMsg.setStyleName("okm-Input");
		statusMsg.setSize("340","15");
		statusMsg.setHorizontalAlignment(HasAlignment.ALIGN_LEFT);
		
		spLeft.setWidth("10");
		spRight.setWidth("10");

		
		panel.add(spLeft);
		panel.add(userInfo);
		panel.add(statusMsg);
		panel.add(spRight);
		panel.setCellWidth(spLeft,"10");
		panel.setCellWidth(spRight,"10");
		panel.setCellHorizontalAlignment(userInfo, HasAlignment.ALIGN_LEFT);
		panel.setCellHorizontalAlignment(statusMsg, HasAlignment.ALIGN_RIGHT);
		panel.setCellHorizontalAlignment(spRight, HasAlignment.ALIGN_RIGHT);
		panel.setCellVerticalAlignment(userInfo, HasAlignment.ALIGN_MIDDLE);
		panel.setCellVerticalAlignment(statusMsg, HasAlignment.ALIGN_MIDDLE);
		
		panel.setStyleName("okm-bottomPanel");
		panel.addStyleName("okm-DisableSelect");
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		panel.setSize("100%", "100%");
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
	
	/**
	 * Sets the status
	 * 
	 * @param key The status value
	 */
	public void setStatus(String key, boolean error) {
		this.key = key;
		if (error) {
			statusMsg.addStyleName("okm-Input-Error");
		} else {
			statusMsg.removeStyleName("okm-Input-Error");
		}
		statusMsg.setText(" "+Main.i18n(key));
	}
	
	/**
	 * Resets the status value
	 */
	public void resetStatus(){
		statusMsg.setText("");
		statusMsg.removeStyleName("okm-Input-Error");
	}
	
	/**
	 * Lang refresh
	 */
	public void langRefresh() {
		statusMsg.setText(" "+Main.i18n(key));
		userInfo.langRefresh();
	}
}