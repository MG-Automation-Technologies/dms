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

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

import es.git.openkm.frontend.client.Main;

/**
 * Status
 * 
 * @author jllort
 *
 */
public class Status extends PopupPanel {
	
	private HorizontalPanel hPanel;
	private HTML msg;
	private HTML space;
	private Image image;
	
	private boolean flag_versionHistory 		= false;
	private boolean flag_groupProperties 		= false;
	private boolean flag_UserSecurity 			= false;
	private boolean flag_RoleSecurity 			= false;
	private boolean flag_setProperties		 	= false;
	private boolean flag_getVersionHistorySize  = false;
	private boolean flag_purgeVersionHistory  	= false;
	private boolean flag_restoreVersion  		= false;
	
	/**
	 * The status
	 */
	public Status() {
		super(false,true);
		hPanel = new HorizontalPanel();
		image = new Image("img/indicator.gif");
		msg = new HTML("");
		space = new HTML("");
		
		hPanel.add(image);
		hPanel.add(msg);
		hPanel.add(space);
		
		hPanel.setCellVerticalAlignment(image, HasAlignment.ALIGN_MIDDLE);
		hPanel.setCellVerticalAlignment(msg, HasAlignment.ALIGN_MIDDLE);
		hPanel.setCellHorizontalAlignment(image, HasAlignment.ALIGN_CENTER);
		hPanel.setCellWidth(image, "30px");
		hPanel.setCellWidth(space, "7px");
		
		hPanel.setHeight("25px");
		
		msg.setStyleName("okm-NoWrap");
		
		super.hide();
		setWidget(hPanel);
	}
	
	/**
	 * Refreshing the panel
	 */
	public void refresh() {
		if (flag_versionHistory || flag_UserSecurity || flag_RoleSecurity || flag_groupProperties 
			|| flag_getVersionHistorySize || flag_setProperties || flag_purgeVersionHistory || flag_restoreVersion) {
			int left = ((Main.get().mainPanel.center.getWidth()-220)/2) + Main.get().mainPanel.left.getWidth() + 10;
			int top =  ((Main.get().mainPanel.browser.bottomHeight-40)/2) + Main.get().mainPanel.center.getY() + 
						 Main.get().mainPanel.browser.topHeight + 10;
			setPopupPosition(left, top);
			Main.get().mainPanel.browser.tabMultiple.setStyleName("okm-PanelRefreshing");
			super.show();
		} else {
			super.hide();
			Main.get().mainPanel.browser.tabMultiple.removeStyleName("okm-PanelRefreshing");
		}
	}
	
	/**
	 * Sets the version history flag
	 */
	public void setVersionHistory() {
		msg.setHTML(Main.i18n("tab.document.status.history"));
		flag_versionHistory = true;
		refresh();
	}
	
	/**
	 * Unset the version history flag
	 */
	public void unsetVersionHistory() {
		flag_versionHistory = false;
		refresh();
	}
	
	/**
	 * Sets the user security flag
	 */
	public void setUserSecurity() {
		msg.setHTML(Main.i18n("tab.status.security.user"));
		flag_UserSecurity = true;
		refresh();
	}
	
	/**
	 * Unset the user security flag
	 */
	public void unsetUserSecurity() {
		flag_UserSecurity = false;
		refresh();
	}
	
	/**
	 * Sets the role security flag
	 */
	public void setRoleSecurity() {
		msg.setHTML(Main.i18n("tab.status.security.role"));
		flag_RoleSecurity = true;
		refresh();
	}
	
	/**
	 * Unset the role security flag
	 */
	public void unsetRoleSecurity() {
		flag_RoleSecurity = false;
		refresh();
	}
	
	/**
	 * Sets the group properties history flag
	 */
	public void setGroupProperties() {
		msg.setHTML(Main.i18n("tab.document.status.group.properties"));
		flag_groupProperties = true;
		refresh();
	}
	
	/**
	 * Unset the group properties flag
	 */
	public void unsetGroupProperties() {
		flag_groupProperties = false;
		refresh();
	}
	
	/**
	 * Sets the properties flag
	 */
	public void setSetProperties() {
		msg.setHTML(Main.i18n("tab.document.status.set.keywords"));
		flag_setProperties = true;
		refresh();
	}
	
	/**
	 * Unset the properties flag
	 */
	public void unsetSetProperties() {
		flag_setProperties = false;
		refresh();
	}
	
	/**
	 * Sets the get version history size flag
	 */
	public void setGetVersionHistorySize() {
		msg.setHTML(Main.i18n("tab.document.status.get.version.history.size"));
		flag_getVersionHistorySize = true;
		refresh();
	}
	
	/**
	 * Unset the get version history size flag
	 */
	public void unsetGetVersionHistorySize() {
		flag_getVersionHistorySize = false;
		refresh();
	}
	
	/**
	 * Sets the purge version history flag
	 */
	public void setPurgeVersionHistory() {
		msg.setHTML(Main.i18n("tab.document.status.purge.version.history"));
		flag_purgeVersionHistory = true;
		refresh();
	}
	
	/**
	 * Unset the purge version history flag
	 */
	public void unsetPurgeVersionHistory() {
		flag_purgeVersionHistory = false;
		refresh();
	}
	
	/**
	 * Sets the restore version flag
	 */
	public void setRestoreVersion() {
		msg.setHTML(Main.i18n("tab.document.status.restore.version"));
		flag_restoreVersion = true;
		refresh();
	}
	
	/**
	 * Unset the restore version flag
	 */
	public void unsetRestoreVersion() {
		flag_restoreVersion = false;
		refresh();
	}
}