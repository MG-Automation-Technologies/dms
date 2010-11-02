/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
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

package com.openkm.frontend.client.extension.comunicator;

import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.ToolBarOption;
import com.openkm.frontend.client.util.Util;


/**
 * GeneralComunicator
 * 
 * @author jllort
 *
 */
public class GeneralComunicator {
	
	/**
	 * refreshUI
	 */
	public static void refreshUI() {
		Main.get().mainPanel.topPanel.toolBar.executeRefresh();
	}
	
	/**
	 * getToolBarOption
	 * 
	 * @return
	 */
	public static ToolBarOption getToolBarOption() {
		return Main.get().mainPanel.topPanel.toolBar.getToolBarOption();
	}
	
	/**
	 * getLang
	 * 
	 * @return
	 */
	public static String getLang() {
		return Main.get().getLang();
	}
	
	/**
	 * Download Document
	 * 
	 * @param checkout
	 */
	public static void downloadDocument(boolean checkout) {
		if (Main.get().mainPanel.desktop.browser.fileBrowser.isDocumentSelected()) {
			Util.downloadFile(Main.get().mainPanel.desktop.browser.fileBrowser.getDocument().getPath(), (checkout?"checkout":""));
		}
	}
	
	/**
	 * Download document as PDF
	 */
	public static void downloadDocumentPdf() {
		if (Main.get().mainPanel.desktop.browser.fileBrowser.isDocumentSelected()) {
			Util.downloadFile(Main.get().mainPanel.desktop.browser.fileBrowser.getDocument().getPath(), "toPdf");
		}
	}
	
	/**
	 * extensionCallOwnDownload
	 * 
	 * @param params
	 */
	public static void extensionCallOwnDownload(String url) {
		final Element downloadIframe = RootPanel.get("__download").getElement(); 
		DOM.setElementAttribute(downloadIframe, "src", url); 
	}
	
	/**
	 * downloadFile
	 * 
	 * @param path
	 * @param params
	 */
	public static void downloadFile(String path, String params) {
		Util.downloadFile(path, params);
	}
	
	/**
	 * Sets the status
	 * 
	 * @param msg
	 */
	public static void setStatus(String msg) {
		Main.get().mainPanel.bottomPanel.setStatus(msg);
	}
	
	/**
	 * Sets the status
	 * 
	 */
	public static void resetStatus() {
		Main.get().mainPanel.bottomPanel.resetStatus();
	}
	
	/**
	 * showError
	 * 
	 * @param callback
	 * @param caught
	 */
	public static void showError(String callback, Throwable caught) {
		Main.get().showError(callback, caught);
	}
	
	/**
	 * logout
	 */
	public static void logout() {
		Main.get().logoutPopup.logout();
	}
	
	/**
	 * refreshUserDocumentsSize
	 */
	public static void refreshUserDocumentsSize() {
		Main.get().workspaceUserProperties.getUserDocumentsSize();
	}
	
	/**
	 * getUserRoleList
	 * 
	 * @return
	 */
	public static List<String> getUserRoleList() {
		return Main.get().workspaceUserProperties.getWorkspace().getRoleList();
	}
	
	/**
	 * getUser
	 * 
	 * @return
	 */
	public static String getUser() {
		return Main.get().workspaceUserProperties.getUser();
	}

}