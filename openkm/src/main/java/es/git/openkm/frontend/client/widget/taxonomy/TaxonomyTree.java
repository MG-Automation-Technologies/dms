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

package es.git.openkm.frontend.client.widget.taxonomy;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.panel.PanelDefinition;
import es.git.openkm.frontend.client.widget.MenuPopup;
import es.git.openkm.frontend.client.widget.foldertree.FolderSelectPopup;
import es.git.openkm.frontend.client.widget.foldertree.FolderTree;
import es.git.openkm.frontend.client.widget.mainmenu.Bookmark;
import es.git.openkm.frontend.client.widget.startup.StartUp;

/**
 * TaxonomyTree
 * 
 * @author jllort
 *
 */
public class TaxonomyTree extends FolderTree {
	
	/**
	 * Inits on first load
	 */
	public void init() {
		menuPopup = new MenuPopup(new TaxonomyMenu());
		menuPopup.setStyleName("okm-Tree-MenuPopup");
		
		folderRoot = Main.get().taxonomyRootFolder;
		
		// Sets the context on list context search values
		Main.get().repositoryContext.setContextTaxonomy(folderRoot.getPath());
		Main.get().mainPanel.search.searchIn.setContextValue(folderRoot.getPath(),PanelDefinition.NAVIGATOR_TAXONOMY);
		
		actualItem.setUserObject(folderRoot);
		evaluesFolderIcon(actualItem);			
		actualItem.setState(true);
		rootItem = actualItem;  // Preserves actualItem value
		
		// Simulate we pass params by broser ( take a look really are not passed )
		// to show user home on loading
		if (Main.get().fldPath==null || Main.get().fldPath.equals("")) {
			if (Main.get().userHome.getType().equals(Bookmark.BOOKMARK_DOCUMENT)) {
				Main.get().docPath = Main.get().userHome.getPath();
				Main.get().fldPath = Main.get().userHome.getPath().substring(0,Main.get().userHome.getPath().lastIndexOf("/"));
			} else if (Main.get().userHome.getType().equals(Bookmark.BOOKMARK_FOLDER)) {
				Main.get().fldPath = Main.get().userHome.getPath();
			}
		}
		
		Main.get().startUp.nextStatus(StartUp.STARTUP_LOADING_TAXONOMY_FOLDERS);
		getChilds(folderRoot.getPath()); // Normal refreshing
	}
	
	/**
	 * Move folder on file browser ( only trash mode )
	 */
	public void move() {
		GWTFolder folderToRestore = (GWTFolder) actualItem.getUserObject();
		folderSelectPopup.setEntryPoint(FolderSelectPopup.ENTRYPOINT_TAXONOMY);
		folderSelectPopup.setToMove(folderToRestore);
		showDirectorySelectPopup();
		hideMenuPopup();
	}
	
	/**
	 * Copy folder on file browser ( only trash mode )
	 */
	public void copy() {
		GWTFolder folderToCopy = (GWTFolder) actualItem.getUserObject();
		folderSelectPopup.setEntryPoint(FolderSelectPopup.ENTRYPOINT_TAXONOMY);
		folderSelectPopup.setToCopy(folderToCopy);
		showDirectorySelectPopup();
		hideMenuPopup();
	}
	
}