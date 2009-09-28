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

package es.git.openkm.frontend.client.util;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.panel.ExtendedDockPanel;
import es.git.openkm.frontend.client.panel.PanelDefinition;

/**
 * @author jllort
 *
 */
public class CommonUI {
	
	/**
	 * Opens all folder path
	 * 
	 * @param path The parent path
	 * @param docPath The document full path
	 */
	public static void openAllFolderPath(String path, String docPath) {
		boolean found = false;
		
		if (path.startsWith(Main.get().mainPanel.navigator.taxonomyTree.folderRoot.getPath())) {
			Main.get().mainPanel.navigator.stackPanel.showStack(PanelDefinition.NAVIGATOR_TAXONOMY, false);
			found = true;
		} else if (path.startsWith(Main.get().mainPanel.navigator.personalTree.folderRoot.getPath())) {
			Main.get().mainPanel.navigator.stackPanel.showStack(PanelDefinition.NAVIGATOR_PERSONAL, false);
			found = true;
		} else if (path.startsWith(Main.get().mainPanel.navigator.templateTree.folderRoot.getPath())) {
			Main.get().mainPanel.navigator.stackPanel.showStack(PanelDefinition.NAVIGATOR_TEMPLATES, false);
			found = true;
		} else if (path.startsWith(Main.get().mainPanel.navigator.trashTree.folderRoot.getPath())) {
			Main.get().mainPanel.navigator.stackPanel.showStack(PanelDefinition.NAVIGATOR_TRASH, false);
			found = true;
		} else if (path.startsWith(Main.get().mainPanel.navigator.mailTree.folderRoot.getPath())) {
			Main.get().mainPanel.navigator.stackPanel.showStack(PanelDefinition.NAVIGATOR_MAIL, false);
			found = true;
		}
		
		if (found) {
			Main.get().mainPanel.topPanel.tabWorkspace.changeSelectedTab(ExtendedDockPanel.DESKTOP);
			Main.get().activeFolderTree.openAllPathFolder(path,docPath);
		}
	}
	
}