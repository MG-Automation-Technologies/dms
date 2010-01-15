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

package es.git.openkm.frontend.client.widget.thesaurus;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.bean.GWTMail;
import es.git.openkm.frontend.client.bean.GWTPermission;
import es.git.openkm.frontend.client.util.Util;
import es.git.openkm.frontend.client.widget.MenuBase;
import es.git.openkm.frontend.client.widget.upload.FancyFileUpload;

/**
 * ThesaurusMenu menu
 * 
 * @author jllort
 *
 */
public class ThesaurusMenu extends MenuBase {
	
	private MenuBar dirMenu;
	
	public ThesaurusMenu() {
		// The item selected must be called on style.css : .okm-MenuBar .gwt-MenuItem-selected
		
		// First initialize language values
		dirMenu = new MenuBar(true);
		initWidget(dirMenu);
	}
	
	@Override
	public void langRefresh() {}
	@Override
	public void evaluateMenuOptions() {}
	@Override
	public void enableRootMenuOptions() {}
	@Override
	public void enableAllMenuOptions() {}
	@Override
	public void disableAllMenuOption() {}
	@Override
	public void checkMenuOptionPermissions(GWTMail mail, GWTFolder folder) {}
	@Override
	public void checkMenuOptionPermissions(GWTDocument doc, GWTFolder folder) {}
	@Override
	public void checkMenuOptionPermissions(GWTFolder folder, GWTFolder folderParent) {}
	public void hide() {}
	public void show() {}
}