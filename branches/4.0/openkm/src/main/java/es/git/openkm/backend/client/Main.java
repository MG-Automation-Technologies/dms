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

package es.git.openkm.backend.client;

import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;

import es.git.openkm.backend.client.config.Config;
import es.git.openkm.backend.client.lang.Lang;
import es.git.openkm.backend.client.panel.BottomPanel;
import es.git.openkm.backend.client.panel.CenterPanel;
import es.git.openkm.backend.client.panel.TopPanel;
import es.git.openkm.backend.client.panel.VerticalBorderPanel;
import es.git.openkm.backend.client.service.OKMUserService;
import es.git.openkm.backend.client.service.OKMUserServiceAsync;
import es.git.openkm.backend.client.util.Util;
import es.git.openkm.backend.client.widget.ErrorPopup;
import es.git.openkm.backend.client.widget.VerticalToolBar;
import es.git.openkm.backend.client.widget.ConfirmPopup;

/**
 * Main entry point application
 * 
 * @author jllort
 *
 */
public final class Main implements EntryPoint, WindowCloseListener {
	
	private final OKMUserServiceAsync userService = (OKMUserServiceAsync) GWT.create(OKMUserService.class);
	
	private static Main singleton;
	
	/**
	 * @return singleton Main instance 
	 */
	public static Main get() {
		return singleton;
	}
	
	// To avoid the logout in the onClose event fired in case of documentDownload
	public boolean redirect = false;
	
	// Main panel declaration
	public DockPanel dockPanel;
	public TopPanel topPanel;
	public VerticalBorderPanel leftPanel;
	public VerticalBorderPanel centerSeparatorPanel;
	public VerticalBorderPanel rightPanel;
	public BottomPanel bottomPanel;
	public VerticalToolBar verticalToolBar;
	public CenterPanel centerPanel;
	public ErrorPopup errorPopup;
	public ConfirmPopup confirmPopup;
	
	// Language declarations
	private String lang;
	private HashMap<String,String> hI18n;
	
	public void onModuleLoad() {
		isAdmin();
	}
	
	public void onModuleLoad2() {
		singleton = this;
		
		// First we initialize language values
		lang = Util.getBrowserLanguage();
		hI18n = Lang.getLang(lang);
		
		dockPanel = new DockPanel();
		topPanel = new TopPanel();
		leftPanel = new VerticalBorderPanel();
		centerSeparatorPanel = new VerticalBorderPanel();
		rightPanel = new VerticalBorderPanel();
		bottomPanel = new BottomPanel();
		verticalToolBar = new VerticalToolBar();
		centerPanel = new CenterPanel();
		errorPopup = new ErrorPopup(false);
		errorPopup.setStyleName("okm-Popup");
		errorPopup.setWidth("300px");
		errorPopup.setHeight("205px");
		confirmPopup = new ConfirmPopup();
		confirmPopup.setWidth("300px");
		confirmPopup.setHeight("125px");
		confirmPopup.setStyleName("okm-Popup");
		confirmPopup.addStyleName("okm-DisableSelect");
		
		// Creates the dockPanel
		dockPanel.add(topPanel, DockPanel.NORTH);
		dockPanel.add(bottomPanel, DockPanel.SOUTH);
		dockPanel.add(leftPanel, DockPanel.WEST);
		dockPanel.add(verticalToolBar, DockPanel.WEST);
		dockPanel.add(centerSeparatorPanel, DockPanel.WEST);
		dockPanel.add(centerPanel, DockPanel.WEST);
		dockPanel.add(rightPanel, DockPanel.EAST);
		
		// Styles
		verticalToolBar.addStyleName("okm-Input");
		
		// Get grid of scrollbars, and clear out the window's built-in margin,
	    // because we want to take advantage of the entire client area.
	    Window.enableScrolling(false);
	    Window.setMargin("0px");
	    
	    RootPanel.get().add(dockPanel);
	    
	    // Setting panels size
	    refreshSize();
	    
	    // Gets graphics at first time
	    centerPanel.statsPanel.graphicalStats.getSizeContext(); // It's a secuential to initalize all graphics
	}
	
	private void refreshSize() {
		int width = Window.getClientWidth();
		int height = Window.getClientHeight();
		int verticalToolBarWidth = 75;
		
		topPanel.setSize(width,10); 
		bottomPanel.setSize(width,10);
		leftPanel.setSize(10,height-20);
		centerSeparatorPanel.setSize(10,height-20);
		rightPanel.setSize(10,height-20);
		
		verticalToolBar.setPixelSize(verticalToolBarWidth,height-20);
		centerPanel.setPixelSize(width-30-verticalToolBarWidth,height-20);
	}
	
	/**
	 * Refresh language
	 * 
	 * @param lang The language code
	 */
	public void refreshLang(String lang) {
		this.lang = lang;
		hI18n = Lang.getLang(lang);
		errorPopup.langRefresh();
		centerPanel.langRefresh();
	}
	
	/**
	 * Gets the actual lang
	 * 
	 * @return lang The language code
	 */
	public String getLang(){
		return lang;
	}
	
	/**
	 * Gets the i18n param translation
	 * 
	 * @param properties The propetie code locator
	 * @return The translated value
	 */
	public static String i18n(String property) {
		String ret = (String) Main.get().hI18n.get(property);
		
		if (ret == null) {
			ret = property;
		}
		
		return ret;
	}
	
	/**
	 * Shows popup error message ( unique entry point for error on all application )
	 * 
	 * @param okme The exception error
	 */
	public void showError(String callback, Throwable caught) {
		if (caught instanceof OKMException) {
			OKMException okme = (OKMException) caught;
			errorPopup.show(okme.getCode()+"("+callback+"): "+i18n(okme.getCode()) + "<br><br>" + okme.getMessage());
		}
	}
	
	public String onWindowClosing() {
		return null;
	}

	public void onWindowClosed() {		
	}
	
	/**
	 * Call back get the remote user
	 */
	final AsyncCallback<Boolean> callbackIsAdmin = new AsyncCallback<Boolean>() {
		public void onSuccess(Boolean result) {
			if (result) {
				onModuleLoad2();
			} else {
				Window.alert("Error: for security purposes only admin users can be loged on administration");
			}
		}
		
		public void onFailure(Throwable caught) {
			Main.get().showError("isAdmin", caught);
		}
	};
	
	/**
	 * Gets the remote user
	 */
	public void isAdmin() {
		ServiceDefTarget endPoint = (ServiceDefTarget) userService;
		endPoint.setServiceEntryPoint(Config.OKMUserService);	
		userService.isAdmin(callbackIsAdmin);
	}
}
