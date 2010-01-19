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

package es.git.openkm.frontend.client.widget.searchin;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.panel.PanelDefinition;
import es.git.openkm.frontend.client.service.OKMFolderService;
import es.git.openkm.frontend.client.service.OKMFolderServiceAsync;
import es.git.openkm.frontend.client.service.OKMRepositoryService;
import es.git.openkm.frontend.client.service.OKMRepositoryServiceAsync;
import es.git.openkm.frontend.client.util.Util;

/**
 * Folder tree
 * 
 * @author jllort
 *
 */
public class FolderSelectTree extends Composite {
	
	private Tree tree;
	private TreeItem actualItem;
	private final OKMFolderServiceAsync folderService = (OKMFolderServiceAsync) GWT.create(OKMFolderService.class);
	private final OKMRepositoryServiceAsync repositoryService = (OKMRepositoryServiceAsync) GWT.create(OKMRepositoryService.class);
	TreeItem rootItem = new TreeItem(Util.imageItemHTML("img/menuitem_childs.gif", "root_schema", "top"));
	
	/**
	 * Folder Tree
	 */
	public FolderSelectTree() {
		tree = new Tree();
		rootItem.setStyleName("okm-TreeItem");
		rootItem.setUserObject(new GWTFolder());
		rootItem.setSelected(true);
		rootItem.setState(true);
		tree.setStyleName("okm-Tree");
		tree.addItem(rootItem);
		tree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				boolean refresh = true;
				TreeItem item = event.getSelectedItem();
				
				// Case that not refreshing tree and file browser ( right click )
				if (actualItem.equals(item)) {
					refresh = false;
				} else {
					// Disables actual item because on changing active node by
					// application this it's not changed automatically
					if (!actualItem.equals(item)) {
						actualItem.setSelected(false);
						actualItem = item;
					} else {
						refresh = false;
					}
				}
				
				if (refresh) {
					refresh(true);
				}
			}
		});
		actualItem = tree.getItem(0);
		initWidget(tree);
	}
	
	/**
	 * Resets all tree values
	 */
	public void reset() {
		actualItem = rootItem;
		actualItem.setSelected(true);
		while (actualItem.getChildCount()>0) {
			actualItem.getChild(0).remove();
		}
		
		changeView(Main.get().mainPanel.search.searchIn.context.getSelectedIndex());
	}
	
	/**
	 * Change de tree view 
	 * 
	 * @param view New view to see
	 */
	public void changeView(int view){
		actualItem = rootItem;
		while (actualItem.getChildCount()>0) {
			actualItem.getChild(0).remove();
		}
		
		switch (view){
			case PanelDefinition.NAVIGATOR_TAXONOMY :
				getRoot();
				break;
				
			case PanelDefinition.NAVIGATOR_TEMPLATES :
				getTemplate();
				break;
				
			case PanelDefinition.NAVIGATOR_PERSONAL :
				getPersonal();
				break;
			
			case PanelDefinition.NAVIGATOR_MAIL :
				getMail();
				break;	
			
			case PanelDefinition.NAVIGATOR_TRASH :
				getTrash();
				break;
		}
	}
	
	/**
	 * Refresh asyncronous subtree branch
	 */
	final AsyncCallback callbackGetChilds = new AsyncCallback() {
		public void onSuccess(Object result) {
			boolean directAdd = true;
			List folderList = (List) result;
			
			// If has no childs directly add values is permited
			if (actualItem.getChildCount() > 0) {
				directAdd = false;
				// to prevent remote folder remove it disables all tree branch 
				// items and after sequentially activate
				hideAllBranch(actualItem);
			}
			
			// On refreshing not refreshed the actual item values but must 
			// ensure that has childs value is consistent
			if (folderList.isEmpty()) {
				((GWTFolder) actualItem.getUserObject()).setHasChilds(false);
			} else {
				((GWTFolder) actualItem.getUserObject()).setHasChilds(true);
			}
			
			// Ads folders childs if exists
			for (Iterator it = folderList.iterator(); it.hasNext();) {
				GWTFolder folder = (GWTFolder) it.next();
				TreeItem folderItem = new TreeItem(folder.getName());
				folderItem.setUserObject(folder);
				folderItem.setStyleName("okm-TreeItem");
				
				// If has no childs directly add values is permited, else 
				// evalues each node to refresh, remove or add
				if (directAdd) {
					evaluesFolderIcon(folderItem);
					actualItem.addItem(folderItem);
				} else {
					// sequentially activate items and refreshes values
					addFolder(actualItem,folderItem);
				}
			}
			
			actualItem.setState(true);
			evaluesFolderIcon(actualItem);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetChilds", caught);
		}
	};
	
	/**
	 * Gets asyncronous root node
	 */
	final AsyncCallback<GWTFolder> callbackGetRoot = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			GWTFolder folderItem = result;
			
			actualItem.setUserObject(folderItem);
			evaluesFolderIcon(actualItem);			
			actualItem.setState(true);
			actualItem.setSelected(true);
			
			getChilds(result.getPath());
			
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetRoot", caught);
		}
	};
	
	/**
	 * Refresh the folders on a item node
	 * 
	 * @param path The folder path selected to list items
	 */
	public void getChilds(String path) {
		ServiceDefTarget endPoint = (ServiceDefTarget) folderService;
		endPoint.setServiceEntryPoint(Config.OKMFolderService);	
		folderService.getChilds(path, callbackGetChilds);
	}	
	
	/**
	 * Gets the root
	 */
	public void getRoot() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);	
		repositoryService.getRoot(callbackGetRoot);
	}
	
	/**
	 * Gets asyncronous template node
	 */
	final AsyncCallback<GWTFolder> callbackGetTemplate = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			GWTFolder folderItem = result;
			
			actualItem.setUserObject(folderItem);
			evaluesFolderIcon(actualItem);			
			actualItem.setState(true);
			actualItem.setSelected(true);
			
			getChilds(result.getPath());
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetTemplate", caught);
		}
	};
	
	/**
	 * Gets the template
	 */
	public void getTemplate() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);
		repositoryService.getTemplate(callbackGetTemplate);
	}
	
	/**
	 * Gets asyncronous mail node
	 */
	final AsyncCallback<GWTFolder> callbackGetMail = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			GWTFolder folderItem = result;
			
			actualItem.setUserObject(folderItem);
			evaluesFolderIcon(actualItem);			
			actualItem.setState(true);
			actualItem.setSelected(true);
			
			getChilds(result.getPath());
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetMail", caught);
		}
	};
	
	/**
	 * Gets the mail
	 */
	public void getMail() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);
		repositoryService.getMail(callbackGetMail);
	}
	
	/**
	 * Gets asyncronous personal documents node
	 */
	final AsyncCallback<GWTFolder> callbackGetPersonal = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			GWTFolder folderItem = result;
			
			actualItem.setUserObject(folderItem);
			evaluesFolderIcon(actualItem);			
			actualItem.setState(true);
			actualItem.setSelected(true);
			
			getChilds(result.getPath());
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetPersonal", caught);
		}
	};
	
	/**
	 * Gets the personal documents
	 */
	public void getPersonal() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);
		repositoryService.getPersonal(callbackGetPersonal);
	}
	
	/**
	 * Gets asyncronous trash node
	 */
	final AsyncCallback<GWTFolder> callbackGetTrash = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			GWTFolder folderItem = result;
			
			actualItem.setUserObject(folderItem);
			evaluesFolderIcon(actualItem);			
			actualItem.setState(true);
			actualItem.setSelected(true);
			
			getChilds(((GWTFolder) result).getPath());
		}
		
		public void onFailure(Throwable caught) {
			Main.get().showError("GetTrash", caught);
		}
	};
	
	/**
	 * Gets the trash
	 */
	public void getTrash() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);	
		repositoryService.getTrash(callbackGetTrash);
	}
	
	/**
	 * Refresh the tree node
	 */
	public void refresh(boolean reset) {
		String path = ((GWTFolder) actualItem.getUserObject()).getPath();
		getChilds(path);
	}
	
	/**
	 * Hides all items on a brach
	 * 
	 * @param actualItem The actual item active
	 */
	public void hideAllBranch(TreeItem actualItem) {
		int i = 0;
		int count = actualItem.getChildCount();
		
		for (i=0; i<count; i++) {
			actualItem.getChild(i).setVisible(false);
		}
	}
	
	/**
	 * Adds folders to actual item if not exists or refreshes it values
	 * 
	 * @param actualItem The actual item active
	 * @param newItem New item to be added, or refreshed
	 */
	public void addFolder(TreeItem actualItem , TreeItem newItem) {
		int i = 0;
		boolean found = false;
		int count = actualItem.getChildCount();
		GWTFolder folder;
		GWTFolder newFolder = (GWTFolder) newItem.getUserObject();
		String folderPath = newFolder.getPath(); 
		
		for (i=0; i<count; i++) {
			folder = (GWTFolder) actualItem.getChild(i).getUserObject();
			// If item is found actualizate values
			if ((folder).getPath().equals(folderPath)) {
				found = true;
				actualItem.getChild(i).setVisible(true);
				actualItem.getChild(i).setUserObject(newFolder);
				evaluesFolderIcon(actualItem.getChild(i));
			}
		}
		
		if (!found) {
			evaluesFolderIcon(newItem);
			actualItem.addItem(newItem);
		}
	}
	
	/**
	 * Gets the actual path of the selected directory tree
	 * 
	 * @return The actual path of selected directory
	 */
	public String getActualPath() {
		return ((GWTFolder) actualItem.getUserObject()).getPath();
	}
	
	/**
	 * Evalues actual folder icon to prevent other user interaction with the same folder
	 * this ensures icon and object hasChildsValue are consistent
	 */
	public void evaluesFolderIcon(TreeItem item) {
		GWTFolder folderItem = (GWTFolder) item.getUserObject();
		preventFolderInconsitences(item);
		
		if (folderItem.getHasChilds()) {
			item.setHTML(Util.imageItemHTML("img/menuitem_childs.gif", folderItem.getName(), "top"));
		} else {
			item.setHTML(Util.imageItemHTML("img/menuitem_empty.gif", folderItem.getName(), "top"));
		}
	}
	
	/**
	 * Prevents folder incosistences between server ( multi user deletes folder ) and tree
	 * nodes drawed
	 * 
	 * @param item The tree node
	 */
	public void preventFolderInconsitences(TreeItem item) {
		GWTFolder folderItem = (GWTFolder) item.getUserObject();
		
		// Case that must remove all items node
		if (item.getChildCount() > 0 && !folderItem.getHasChilds()) {
			while (item.getChildCount() > 0) {
				item.getChild(0).remove();
			}
		}
		if (item.getChildCount()< 1 && !folderItem.getHasChilds()) {
			folderItem.setHasChilds(false);
		}
	}	
}