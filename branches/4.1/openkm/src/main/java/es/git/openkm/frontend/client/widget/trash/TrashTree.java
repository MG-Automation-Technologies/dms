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

package es.git.openkm.frontend.client.widget.trash;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.panel.PanelDefinition;
import es.git.openkm.frontend.client.widget.ConfirmPopup;
import es.git.openkm.frontend.client.widget.MenuPopup;
import es.git.openkm.frontend.client.widget.foldertree.FolderSelectPopup;
import es.git.openkm.frontend.client.widget.foldertree.FolderTree;
import es.git.openkm.frontend.client.widget.startup.StartUp;

public class TrashTree extends FolderTree {
	
	/**
	 * Inits on first load
	 */
	public void init() {
		menuPopup = new MenuPopup(new TrashMenu());
		menuPopup.setStyleName("okm-Tree-MenuPopup");
		
		folderRoot = Main.get().trashRootFolder;
		
		// Sets the context on list context search values
		Main.get().repositoryContext.setContextTrash(folderRoot.getPath());
		Main.get().mainPanel.search.searchIn.setContextValue(folderRoot.getPath(),PanelDefinition.NAVIGATOR_TRASH);
		
		rootItem = actualItem; // Save rootItem
		actualItem.setUserObject(folderRoot);
		evaluesFolderIcon(actualItem);			
		actualItem.setState(true);
		getOnlyChilds(folderRoot.getPath()); 
		Main.get().startUp.nextStatus(StartUp.STARTUP_LOADING_HISTORY_SEARCH);
	}
	
	/**
	 * Move folder on file browser ( only trash mode )
	 */
	public void move() {
		GWTFolder folderToRestore = (GWTFolder) actualItem.getUserObject();
		folderSelectPopup.setEntryPoint(FolderSelectPopup.ENTRYPOINT_TRASH);
		folderSelectPopup.setToMove(folderToRestore);
		showDirectorySelectPopup();
		hideMenuPopup();
	}
	
	/**
	 * Copy folder on file browser ( only trash mode )
	 */
	public void copy() {
		GWTFolder folderToCopy = (GWTFolder) actualItem.getUserObject();
		folderSelectPopup.setEntryPoint(FolderSelectPopup.ENTRYPOINT_TRASH);
		folderSelectPopup.setToCopy(folderToCopy);
		showDirectorySelectPopup();
		hideMenuPopup();
	}
	
	/**
	 * Confirm if really wants to purge trash
	 */
	public void confirmPurgeTrash() {
		Main.get().confirmPopup.setConfirm(ConfirmPopup.CONFIRM_EMPTY_TRASH);
		Main.get().confirmPopup.show();
	}
	
	/**
	 * Confirm the purge
	 */
	public void confirmPurge() {
		Main.get().confirmPopup.setConfirm(ConfirmPopup.CONFIRM_PURGE_FOLDER);
		Main.get().confirmPopup.show();
	}
}