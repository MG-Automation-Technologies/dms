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

package es.git.openkm.frontend.client;

import java.util.HashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.gwt.user.client.ui.RootPanel;

import es.git.openkm.frontend.client.bean.GWTBookmark;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.bean.RepositoryContext;
import es.git.openkm.frontend.client.lang.Lang;
import es.git.openkm.frontend.client.panel.ExtendedDockPanel;
import es.git.openkm.frontend.client.util.Location;
import es.git.openkm.frontend.client.util.Util;
import es.git.openkm.frontend.client.util.WindowUtils;
import es.git.openkm.frontend.client.util.WorkspaceUserProperties;
import es.git.openkm.frontend.client.widget.AboutPopup;
import es.git.openkm.frontend.client.widget.ConfirmPopup;
import es.git.openkm.frontend.client.widget.DebugConsolePopup;
import es.git.openkm.frontend.client.widget.Dragable;
import es.git.openkm.frontend.client.widget.ErrorPopup;
import es.git.openkm.frontend.client.widget.ExternalURLPopup;
import es.git.openkm.frontend.client.widget.GroupPopup;
import es.git.openkm.frontend.client.widget.ImageViewerPopup;
import es.git.openkm.frontend.client.widget.LogoutPopup;
import es.git.openkm.frontend.client.widget.MediaPlayerPopup;
import es.git.openkm.frontend.client.widget.MsgPopup;
import es.git.openkm.frontend.client.widget.NotifyPopup;
import es.git.openkm.frontend.client.widget.UserPopup;
import es.git.openkm.frontend.client.widget.WorkflowPopup;
import es.git.openkm.frontend.client.widget.foldertree.FolderTree;
import es.git.openkm.frontend.client.widget.resize.HorizontalBarSplitter;
import es.git.openkm.frontend.client.widget.resize.VerticalBarSplitter;
import es.git.openkm.frontend.client.widget.security.SecurityPopup;
import es.git.openkm.frontend.client.widget.startup.StartUp;
import es.git.openkm.frontend.client.widget.startup.StartUpPopup;
import es.git.openkm.frontend.client.widget.upload.FileUpload;

/**
 * Main entry point application
 * 
 * @author jllort
 *
 */
public final class Main implements EntryPoint, WindowCloseListener {
	
	private static Main singleton;
	
	/**
	 * @return singleton Main instance 
	 */
	public static Main get() {
		return singleton;
	}
	
	// Main panel declaration
	public ExtendedDockPanel mainPanel;
	
	// Other panel declaration
	public StartUpPopup startUpPopup;
	public FileUpload fileUpload;
	public ErrorPopup errorPopup;
	public ErrorPopup errorPopupLogout;
	public MsgPopup msgPopup;
	public ExternalURLPopup externalURLPopup;
	public LogoutPopup logoutPopup;
	public SecurityPopup securityPopup;
	public AboutPopup aboutPopup;
	public UserPopup userPopup;
	public ConfirmPopup confirmPopup;
	public Dragable dragable;
	public GroupPopup groupPopup;
	public WorkflowPopup workflowPopup;
	public NotifyPopup notifyPopup;
	public MediaPlayerPopup mediaPlayerPopup;
	public ImageViewerPopup imageViewerPopup;
	public DebugConsolePopup debugConsolePopup;
	public VerticalBarSplitter verticalBarSplitter;
	public HorizontalBarSplitter horizontalBarSplitter;
	
	// User workspace properties
	public WorkspaceUserProperties workspaceUserProperties;
	
	// To avoid the logout in the onClose event fired in case of documentDownload
	public boolean redirect = false;
	
	// Language declarations
	private String lang;
	private HashMap<String, String> hI18n;
	public HashMap hPropertyGroupI18n;
	
	// The nodePath parameter
	public String fldPath = "";  // Used for folderTree because docPath is set to null by filebroeser on this case the refreshing
									// panels are not sincronized ( loading )
	public String docPath = "";  // Used for folderTree because docPath is set to null by filebroeser on this case the refreshing
									// panels are not sincronized ( loading )
	
	// Main root folders and user home general values for all app
	public GWTFolder taxonomyRootFolder;
	public GWTFolder thesaurusRootFolder;
	public GWTFolder personalRootFolder;
	public GWTFolder templatesRootFolder;
	public GWTFolder mailRootFolder;
	public GWTFolder trashRootFolder;
	public GWTBookmark userHome;
	public FolderTree activeFolderTree; // The active folder
		
	// The satartUp sequence
	public StartUp startUp;
	
	// The location ( url params )
	public Location loc;
	
	// Repository context
	public RepositoryContext repositoryContext;
	
	public void onModuleLoad() {
		Log.setUncaughtExceptionHandler();
		Log.setCurrentLogLevel(Log.LOG_LEVEL_OFF);

		singleton = this;
		
		// All objects defined before singleton to use global reference.
		
		// Saves repository context paths
		repositoryContext = new RepositoryContext();
		
		// Request parameter
		loc = WindowUtils.getLocation();
		if (loc.getParameter("docPath")!=null && !loc.getParameter("docPath").equals("")) {
			fldPath = loc.getParameter("docPath").substring(0,loc.getParameter("docPath").lastIndexOf("/")); 
			docPath = loc.getParameter("docPath"); 
		}
		
		// Tries to capture lang parameter
		if (loc.getParameter("lang")!=null && !loc.getParameter("lang").equals("")) {
			lang = loc.getParameter("lang");
		} else {
			// First we initialize language values
			lang = Util.getBrowserLanguage();
		}
		hI18n = Lang.getLang(lang);
		
		// Initialize workspace properties
		workspaceUserProperties = new WorkspaceUserProperties();
		
		// Initialize user home
		userHome = new GWTBookmark();

		// Initialize panels
		mainPanel = new ExtendedDockPanel();
		
		// Loading popup
		startUpPopup = new StartUpPopup();
		startUpPopup.setWidth("300px");
		startUpPopup.setHeight("220px");
		startUpPopup.setStyleName("okm-Popup");
		startUpPopup.addStyleName("okm-DisableSelect");
		
		// Initialize general panels
		fileUpload = new FileUpload();
		fileUpload.setStyleName("okm-Popup");
		errorPopup = new ErrorPopup(false);
		errorPopup.setStyleName("okm-Popup-Error");
		errorPopup.setWidth("380px");
		errorPopup.setHeight("205px");
		errorPopupLogout = new ErrorPopup(true);
		errorPopupLogout.setStyleName("okm-Popup-Error");
		errorPopupLogout.setWidth("300px");
		errorPopupLogout.setHeight("205px");
		msgPopup = new MsgPopup();
		msgPopup.setStyleName("okm-Popup");
		msgPopup.setWidth("300px");
		msgPopup.setHeight("205px");
		externalURLPopup = new ExternalURLPopup();
		externalURLPopup.setStyleName("okm-Popup");
		logoutPopup = new LogoutPopup();
		logoutPopup.setWidth("250");
		logoutPopup.setHeight("110");
		logoutPopup.setStyleName("okm-Popup");
		logoutPopup.addStyleName("okm-DisableSelect");
		securityPopup = new SecurityPopup();
		securityPopup.setWidth("325");
		securityPopup.setHeight("330");
		securityPopup.setStyleName("okm-Popup");
		securityPopup.addStyleName("okm-DisableSelect");
		aboutPopup = new AboutPopup();
		aboutPopup.setWidth("300px");
		aboutPopup.setHeight("220px");
		aboutPopup.setStyleName("okm-Popup");
		aboutPopup.addStyleName("okm-DisableSelect");
		userPopup = new UserPopup();
		userPopup.setWidth("400px");
		userPopup.setHeight("220px");
		userPopup.setStyleName("okm-Popup");
		//userPopup.addStyleName("okm-DisableSelect");
		confirmPopup = new ConfirmPopup();
		confirmPopup.setWidth("300px");
		confirmPopup.setHeight("125px");
		confirmPopup.setStyleName("okm-Popup");
		confirmPopup.addStyleName("okm-DisableSelect");
		dragable = new Dragable();
		groupPopup = new GroupPopup();
		groupPopup.setWidth("300px");
		groupPopup.setHeight("100px");
		groupPopup.setStyleName("okm-Popup");
		groupPopup.addStyleName("okm-DisableSelect");
		workflowPopup = new WorkflowPopup();
		workflowPopup.setWidth("300px");
		workflowPopup.setHeight("100px");
		workflowPopup.setStyleName("okm-Popup");
		workflowPopup.addStyleName("okm-DisableSelect");
		notifyPopup = new NotifyPopup();
		notifyPopup.setWidth("300px");
		notifyPopup.setHeight("100px");
		notifyPopup.setStyleName("okm-Popup");
		mediaPlayerPopup = new MediaPlayerPopup();
		mediaPlayerPopup.setWidth("360px");
		mediaPlayerPopup.setHeight("290px");
		mediaPlayerPopup.setStyleName("okm-Popup");
		mediaPlayerPopup.addStyleName("okm-DisableSelect");
		imageViewerPopup = new ImageViewerPopup();
		imageViewerPopup.setWidth("400px");
		imageViewerPopup.setHeight("200px");
		imageViewerPopup.setStyleName("okm-Popup");
		imageViewerPopup.addStyleName("okm-DisableSelect");
		debugConsolePopup = new DebugConsolePopup();
		debugConsolePopup.setWidth("300px");
		debugConsolePopup.setHeight("100px");
		debugConsolePopup.setStyleName("okm-Popup");
		debugConsolePopup.addStyleName("okm-DisableSelect");
		verticalBarSplitter = new VerticalBarSplitter();
		horizontalBarSplitter = new HorizontalBarSplitter();

		// Get grid of scrollbars, and clear out the window's built-in margin,
	    // because we want to take advantage of the entire client area.
	    Window.enableScrolling(false);
	    Window.setMargin("0px");
	    
	    RootPanel.get().add(mainPanel);
	    RootPanel.get().add(dragable);
	    RootPanel.get().add(verticalBarSplitter);
	    RootPanel.get().add(horizontalBarSplitter);

	    Window.addWindowCloseListener(this);
	    initJavaScriptApi();
		
		// Sets the active folder tree, it'll be used to store the active folder 
		// every time switching stack panel
		activeFolderTree = mainPanel.navigator.taxonomyTree;
		
		// Initialize on startup when all objects are created sequentially
		startUpPopup.show();
		startUp = new StartUp();
		startUp.init();
	}
	
	native void initJavaScriptApi() /*-{
    	// define a static JS function with a friendly name
    	$wnd.i18n = function(s) {
    	 	return @es.git.openkm.frontend.client.Main::i18n(Ljava/lang/String;)(s);
    	};
    	
    	$wnd.propertyGroupI18n = function(s) {
    	 	return @es.git.openkm.frontend.client.Main::propertyGroupI18n(Ljava/lang/String;)(s);
    	};
    	
    	$wnd.refresh = function(s) {
    		return @es.git.openkm.frontend.client.Main::refresh(Ljava/lang/String;)(s);
    	}
    }-*/;
	
	/**
	 * Refresh language
	 * 
	 * @param lang The language code
	 */
	public void refreshLang(String lang) {
		this.lang = lang;
		hI18n = Lang.getLang(lang);
		mainPanel.navigator.langRefresh();
		mainPanel.topPanel.langRefresh();
		mainPanel.browser.langRefresh();
		mainPanel.historySearch.langRefresh();
		mainPanel.search.langRefresh();
		mainPanel.bottomPanel.langRefresh();
		mainPanel.dashboard.langRefresh();
		fileUpload.langRefresh();
		logoutPopup.langRefresh();
		securityPopup.langRefresh();
		aboutPopup.langRefresh();
		userPopup.langRefresh();
		confirmPopup.langRefresh();
		msgPopup.langRefresh();
		errorPopup.langRefresh();
		errorPopupLogout.langRefresh();
		externalURLPopup.langRefresh();
		groupPopup.langRefresh();
		workflowPopup.langRefresh();
		notifyPopup.langRefresh();
		mediaPlayerPopup.langRefresh();
		imageViewerPopup.langRefresh();
		debugConsolePopup.langRefresh();
		// Refreshing all menus on tabs not only the active
		mainPanel.navigator.taxonomyTree.langRefresh();
		mainPanel.navigator.thesaurusTree.langRefresh();
		mainPanel.navigator.personalTree.langRefresh();
		mainPanel.navigator.templateTree.langRefresh();
		mainPanel.navigator.trashTree.langRefresh();
		mainPanel.navigator.thesaurusTree.thesaurusSelectPopup.langRefresh();
		startUp.getPropetyGroupTranslations(); // Gets all hashmap translations;
	}
	
	/**
	 * Gets the actual lang
	 * 
	 * @return lang The language code
	 */
	public String getLang() {
		return lang;
	}
	
	/**
	 * Shows popup error message ( unique entry point for error on all application )
	 * 
	 * @param okme The exception error
	 */
	public void showError(String callback, Throwable caught) {
		startUp.recoverFromError();
		if (caught instanceof OKMException) {
			OKMException okme = (OKMException) caught;
			Log.error("OKMException("+callback+"): "+okme.getCode());
			errorPopup.show(okme.getCode()+"("+callback+"): "+i18n(okme.getCode()) + "<br><br>" + okme.getMessage());
		} else if (caught instanceof InvocationException) {
			InvocationException ie = (InvocationException) caught;
			Log.error("InvocationException("+callback+"): "+ie);
			//errorPopupLogout.show(Main.i18n("error.invocation")+" ("+callback+")");
			errorPopup.show(Main.i18n("error.invocation")+" ("+callback+")");
		} else if (caught instanceof StatusCodeException) {
			StatusCodeException ie = (StatusCodeException) caught;
			Log.error("StatusCodeException("+callback+"): "+ie + " <br>HTTP status code error:"+ie.getStatusCode());
			//errorPopupLogout.show(Main.i18n("error.invocation")+" ("+callback+")");
			mainPanel.bottomPanel.setStatus("status.network.error.detected", true, ie.getStatusCode());
		} else {
			Log.error("UnknownException("+callback+"): "+caught.getMessage());
			//errorPopupLogout.show(callback+": "+caught.getMessage());
			errorPopup.show(callback+": "+caught.getMessage());
		}
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
	 * Gets the i18n PropertyGroup param translation
	 * 
	 * @param properties The propetie code locator
	 * @return The translated value
	 */
	public static String propertyGroupI18n(String property) {
		String ret = (String) Main.get().hPropertyGroupI18n.get(property);
		
		if (ret == null) {
			ret = property;
		}
		
		return ret;
	}
	
	/**
	 * Called from scanner applet
	 */
	public static String refresh(String s) {
		Main.get().mainPanel.topPanel.toolBar.executeRefresh();
		return "";
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.WindowCloseListener#onWindowClosing()
	 */
	public String onWindowClosing() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.WindowCloseListener#onWindowClosed()
	 */
	public void onWindowClosed() {
		startUp.keepAlive.cancel();
	}
}