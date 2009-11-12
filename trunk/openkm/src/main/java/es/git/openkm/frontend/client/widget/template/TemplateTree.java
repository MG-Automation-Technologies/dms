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

package es.git.openkm.frontend.client.widget.template;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.panel.PanelDefinition;
import es.git.openkm.frontend.client.widget.MenuPopup;
import es.git.openkm.frontend.client.widget.foldertree.FolderSelectPopup;
import es.git.openkm.frontend.client.widget.foldertree.FolderTree;
import es.git.openkm.frontend.client.widget.startup.StartUp;

/**
 * TemplateTree
 * 
 * @author jllort
 *
 */
public class TemplateTree extends FolderTree {
	
	/**
	 * Inits on first load
	 */
	public void init() {
		menuPopup = new MenuPopup(new TemplateMenu());
		menuPopup.setStyleName("okm-Tree-MenuPopup");
		
		folderRoot = Main.get().templatesRootFolder;
		
		// Sets the context on list context search values
		Main.get().repositoryContext.setContextTemplates(folderRoot.getPath());
		Main.get().mainPanel.search.searchIn.setContextValue(folderRoot.getPath(),PanelDefinition.NAVIGATOR_TEMPLATES);
		
		actualItem.setUserObject(folderRoot);
		evaluesFolderIcon(actualItem);			
		actualItem.setState(true);
		rootItem = actualItem;  // Preserves actualItem value
		getOnlyChilds(folderRoot.getPath());  	// Special load for firsTime loading ( not refresh file browser )
												// needed to solve that if this stack panel is not showed by user
												// before making a search, can't jump to document folder.
		Main.get().startUp.nextStatus(StartUp.STARTUP_LOADING_PERSONAL);
	}
	
	/**
	 * Move folder on file browser ( only trash mode )
	 */
	public void move() {
		GWTFolder folderToRestore = (GWTFolder) actualItem.getUserObject();
		folderSelectPopup.setEntryPoint(FolderSelectPopup.ENTRYPOINT_TEMPLATES);
		folderSelectPopup.setToMove(folderToRestore);
		showDirectorySelectPopup();
		hideMenuPopup();
	}
	
	/**
	 * Copy folder on file browser ( only trash mode )
	 */
	public void copy() {
		GWTFolder folderToCopy = (GWTFolder) actualItem.getUserObject();
		folderSelectPopup.setEntryPoint(FolderSelectPopup.ENTRYPOINT_TEMPLATES);
		folderSelectPopup.setToCopy(folderToCopy);
		showDirectorySelectPopup();
		hideMenuPopup();
	}
	
}