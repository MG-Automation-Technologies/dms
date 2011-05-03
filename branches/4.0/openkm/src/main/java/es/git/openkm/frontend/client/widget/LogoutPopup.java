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

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMAuthService;
import es.git.openkm.frontend.client.service.OKMAuthServiceAsync;

/**
 * Logout
 * 
 * @author jllort
 *
 */
public class LogoutPopup extends DialogBox implements ClickListener {
	
	private VerticalPanel vPanel;
	private HTML text;
	private Button button;
	
	private final OKMAuthServiceAsync authService = (OKMAuthServiceAsync) GWT.create(OKMAuthService.class);
	
	/**
	 * Logout popup
	 */
	public LogoutPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		
		vPanel = new VerticalPanel();
		text = new HTML(Main.i18n("logout.logout"));
		button = new Button(Main.i18n("button.close"), this);
		
		vPanel.setWidth("250");
		vPanel.setHeight("100");
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(text);
		vPanel.add(new HTML("<br>"));
		vPanel.add(button);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(text, VerticalPanel.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(button, VerticalPanel.ALIGN_CENTER);

		button.setStyleName("okm-Button");

		super.hide();
		setWidget(vPanel);
	}
	
	/**
	 * OKM logout
	 */
	final AsyncCallback callbackLogout = new AsyncCallback() {
		public void onSuccess(Object result) {
			text.setText(Main.i18n("logout.closed"));
			button.setEnabled(true);
		}

		public void onFailure(Throwable caught) {
			Log.debug("callbackLogout.onFailure("+caught+")");
			Main.get().showError("Logout", caught);
		}
	};
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.ClickListener#onClick(com.google.gwt.user.client.ui.Widget)
	 */
	public void onClick(Widget sender) {
		super.hide();
		Main.get().redirect = true;
		Window.open("/OpenKM"+Config.INSTALL+"/", "_self", null);
		Main.get().redirect = false;
	}
	
	/**
	 * Lang refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("logout.label"));
		text.setText(Main.i18n("Logout.Logout"));
		button.setText(Main.i18n("button.close"));
	}
	
	/**
	 * OKM Logout
	 */
	public void logout() {
		button.setEnabled(false);
		int left = (Window.getClientWidth()-300)/2;
		int top = (Window.getClientHeight()-200)/2;
		setPopupPosition(left, top);
		setText(Main.i18n("logout.label"));
		show();
		Log.debug("Logout()");
		ServiceDefTarget endPoint = (ServiceDefTarget) authService;
		endPoint.setServiceEntryPoint(Config.OKMAuthService);	
		authService.logout(callbackLogout);
		Log.debug("Logout: void");
	}
}