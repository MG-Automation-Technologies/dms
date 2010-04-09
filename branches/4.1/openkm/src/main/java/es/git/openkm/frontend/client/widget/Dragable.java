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

package es.git.openkm.frontend.client.widget;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.bean.GWTMail;
import es.git.openkm.frontend.client.bean.GWTPermission;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMDocumentService;
import es.git.openkm.frontend.client.service.OKMDocumentServiceAsync;
import es.git.openkm.frontend.client.service.OKMFolderService;
import es.git.openkm.frontend.client.service.OKMFolderServiceAsync;
import es.git.openkm.frontend.client.service.OKMMailService;
import es.git.openkm.frontend.client.service.OKMMailServiceAsync;

/**
 * @author jllort
 *
 */
public class Dragable extends Composite implements OriginPanel {
	
	private final OKMFolderServiceAsync folderService = (OKMFolderServiceAsync) GWT.create(OKMFolderService.class);
	private final OKMDocumentServiceAsync documentService = (OKMDocumentServiceAsync) GWT.create(OKMDocumentService.class);
	private final OKMMailServiceAsync mailService = (OKMMailServiceAsync) GWT.create(OKMMailService.class);
	
	private boolean dragged = false;
	private boolean dropEnabled = false; // Used to eliminate user drag & drop too much faster ( timer to enable drop )
	private HTML floater = new HTML();
	private Event event = null;
	EventPreview eventPreview = null;
	private int originPanel = NONE; 
	private Timer timer;
	private TreeItem selectedTreeItem;
	private TreeItem lastSelectedTreeItem;
	private Element selectedElement;
	private Element lastSelectElement;
	
	/**
	 * Dragable
	 */
	public Dragable() {
		dragged = false;
		floater = new HTML("");
		floater.setVisible(false);
		floater.sinkEvents(Event.MOUSEEVENTS);
        floater.setVisible(false);
        floater.setWordWrap(false);
        floater.setStyleName("okm-Draggable");
        
        eventPreview = new EventPreview() {
            public boolean onEventPreview(Event e) {
                event = e;
                return true;
            }
		};

		floater.addMouseListener(new MouseListenerAdapter() {
			
            /* (non-Javadoc)
             * @see com.google.gwt.user.client.ui.MouseListener#onMouseUp(com.google.gwt.user.client.ui.Widget, int, int)
             */
            public void onMouseUp(Widget sender, int x, int y) {
            	if (timer!=null) {
            		timer.cancel(); // Cancel timer
            		timer = null;
            	}
            	
            	DOM.releaseCapture(floater.getElement());
                floater.setHTML("");
        		floater.setVisible(false);
        		DOM.removeEventPreview(eventPreview);
        		
        		// Cancelling the timer
        		if (timer!=null) {
                	timer.cancel(); // Cancelling timer
                	timer = null;
            	}
            	
            	// Only move if dragged has been enabled by timer
	            if (dragged && dropEnabled) {

	        		// Action depends on origin dragable
	        		switch(originPanel) {
	        			case TREE_ROOT :
	        				TreeItem clickedTreeItem = Main.get().activeFolderTree.elementClicked(DOM.eventGetTarget(event));
	        				if (clickedTreeItem!=null && 
	        					(((GWTFolder)clickedTreeItem.getUserObject()).getPermissions()& GWTPermission.WRITE) == GWTPermission.WRITE ){
	        					TreeItem draggedTreeItem = Main.get().activeFolderTree.getActualItem();
	        					boolean isChild = DOM.isOrHasChild(draggedTreeItem.getElement(), clickedTreeItem.getElement());
	        					
	                            if (draggedTreeItem != clickedTreeItem && !isChild) {
	                            	String fldPath = ((GWTFolder) draggedTreeItem.getUserObject()).getPath(); 	// Folder actual path
	                            	String dstPath = ((GWTFolder) clickedTreeItem.getUserObject()).getPath(); 	// Destination path
	                            	TreeItem parentItem = draggedTreeItem.getParentItem(); 						// The parent of actual item selected
	                            	
	                            	// Remove the folders and evaluates parent child status
	                                draggedTreeItem.remove();
	                                if (parentItem.getChildCount()==0) {
	                                	((GWTFolder) parentItem.getUserObject()).setHasChilds(false);   // Sets not has folder childs
	                                }
	                                
	                                clickedTreeItem.addItem(draggedTreeItem);							// Adds the draggedItem to selected
	                                ((GWTFolder) clickedTreeItem.getUserObject()).setHasChilds(true); 	// Always sets that the actual parent folder now has childs
	                                clickedTreeItem.setState(true); 								   	// Always opens treeItem parent
	                                draggedTreeItem.setSelected(true); 						   			// Selects the treeItem
	                                
	                                // Evaluate icon of changed folders last and actual parent tree Item
	                                Main.get().activeFolderTree.evaluesFolderIcon(parentItem);
	                                Main.get().activeFolderTree.evaluesFolderIcon(clickedTreeItem);
	                                
	                                // Move the folder
	                                ServiceDefTarget endPoint = (ServiceDefTarget) folderService;
									endPoint.setServiceEntryPoint(Config.OKMFolderService);
	                                folderService.move( fldPath, dstPath, callbackMove);
	                                
	                                // Sets the folder new path itself and childs
	                                GWTFolder draggedFolder = (GWTFolder) draggedTreeItem.getUserObject();
	                                String oldPath = draggedFolder.getPath();
	                                String newPath = dstPath + "/" + draggedFolder.getName();
	                                preventFolderInconsitences(draggedTreeItem, oldPath, newPath, dstPath);
	                                draggedTreeItem.setState(false);
	                                
	                                Main.get().activeFolderTree.openAllPathFolder(newPath,null);
	                            }
	        				}
	        				break;
	        				
	    				case FILE_BROWSER :
	    					clickedTreeItem = Main.get().activeFolderTree.elementClicked(DOM.eventGetTarget(event));
	    					TreeItem actualTreeItem = Main.get().activeFolderTree.getActualItem();
	    					
	        				if (clickedTreeItem!=null && Main.get().mainPanel.browser.fileBrowser.isSelectedRow() &&
	        					(((GWTFolder)clickedTreeItem.getUserObject()).getPermissions()& GWTPermission.WRITE) == GWTPermission.WRITE ){
	        					
	        					String dstPath = ((GWTFolder) clickedTreeItem.getUserObject()).getPath(); // Destination path
	        					
	        					// if selected path = actual path must not move
	        					if (!dstPath.equals(((GWTFolder) actualTreeItem.getUserObject()).getPath())) {
	        						
	        						// Unselects folder destination on tree
	        						if (lastSelectElement!= null) {
	        							DOM.setElementProperty(lastSelectElement,"className","gwt-TreeItem");
	        						}
	        						
		        					if (Main.get().mainPanel.browser.fileBrowser.isFolderSelected()) {
		        						
		        						GWTFolder gwtFolder= Main.get().mainPanel.browser.fileBrowser.getFolder();  // The dragged folder
		            					String fldPath = gwtFolder.getPath(); // Folder actual path
		                            	
		                            	// Destination path must not containt actual folder path, because folder can't be moved to his subfolders
		                            	if (!dstPath.startsWith(fldPath)){
		                            		
		                            		// Gets the moved tree Item
		                            		TreeItem movedTreeItem = Main.get().activeFolderTree.getChildFolder(fldPath);
		                            		
		                            		// Remove the folders and evaluates parent child status
		                            		movedTreeItem.remove();
		                                    if (actualTreeItem.getChildCount()==0) {
		                                    	((GWTFolder) actualTreeItem.getUserObject()).setHasChilds(false); // Sets not has folder childs
		                                    }
		                            		
		                            		clickedTreeItem.addItem(movedTreeItem);								// Adds the draggedItem to selected
		                                    ((GWTFolder) clickedTreeItem.getUserObject()).setHasChilds(true); 	// Always sets that the actual parent folder now has childs
		                                    clickedTreeItem.setState(true); 								   	// Always opens treeItem parent
		                                    Main.get().activeFolderTree.removeDeleted(fldPath);
		                            		
		                                    // Evaluate icon of changed folders last and actual parent tree Item
		                                    Main.get().activeFolderTree.evaluesFolderIcon(clickedTreeItem);
		                                    
		                            		// Move the folder
		                                    ServiceDefTarget endPoint = (ServiceDefTarget) folderService;
		    								endPoint.setServiceEntryPoint(Config.OKMFolderService);
		                                    folderService.move( fldPath, dstPath, callbackMove);
		                                    
		                                    // Sets the folder new path ( parent and itself ) recursive for itself and childs
		                                    movedTreeItem.setUserObject(gwtFolder);
		                                    String oldPath = gwtFolder.getPath();
			                                String newPath = dstPath + "/" + gwtFolder.getName();
			                                preventFolderInconsitences(movedTreeItem, oldPath, newPath, dstPath);
			                                movedTreeItem.setState(false);
		                                    
		                                    // Refresh file browser
		        							Main.get().mainPanel.browser.fileBrowser.deleteMovedOrMoved();
		        							
		                            	} 
		        					} else if (Main.get().mainPanel.browser.fileBrowser.isDocumentSelected()){
		                        		
		                        		GWTDocument gwtDocument = Main.get().mainPanel.browser.fileBrowser.getDocument(); // The dragged document
		                        		
		                        		// Move the document
		                        		ServiceDefTarget endPoint = (ServiceDefTarget) documentService;
		    							endPoint.setServiceEntryPoint(Config.OKMDocumentService);
		    							documentService.move( gwtDocument.getPath(),dstPath, callbackMove);
		    							
		    							// refresh file browser
		    							Main.get().mainPanel.browser.fileBrowser.deleteMovedOrMoved();
		    							
		                        	} else if (Main.get().mainPanel.browser.fileBrowser.isMailSelected()){
		                        		
		                        		GWTMail gwtMail = Main.get().mainPanel.browser.fileBrowser.getMail(); // The dragged document
		                        		
		                        		// Move the document
		                        		ServiceDefTarget endPoint = (ServiceDefTarget) mailService;
		    							endPoint.setServiceEntryPoint(Config.OKMMailService);
		    							mailService.move( gwtMail.getPath(),dstPath, callbackMove);
		    							
		    							// refresh file browser
		    							Main.get().mainPanel.browser.fileBrowser.deleteMovedOrMoved();
		                        	} 
	        					}
	        				}
	    					break;
	        		}
            	}
	            
	            dragged=false; 			// Sets always dragged to false
	            dropEnabled = false;	// Sets always dragged to false
	            
        		// Always we destroy possible timers to automatic up / down scroll
        		Main.get().mainPanel.navigator.scrollTaxonomyPanel.destroyTimer();
                super.onMouseUp(sender, x, y);
            }

            /* (non-Javadoc)
             * @see com.google.gwt.user.client.ui.MouseListener#onMouseMove(com.google.gwt.user.client.ui.Widget, int, int)
             */
            public void onMouseMove(Widget sender, int x, int y) {
                if (dragged && event!=null) {
                	
                	floater.setVisible(true); // Sets the floater visible
                	
                	// Cancels the timer if dropEnabled is true
                	if (dropEnabled && timer!=null) {
	                	timer.cancel(); // Cancelling timer
	                	timer = null;
                	}

                    int posX = DOM.eventGetClientX(event);
                    int posY = DOM.eventGetClientY(event);
                    RootPanel.get().setWidgetPosition(Main.get().dragable, posX + 1, posY);
                    
                    // Sets selected tree style to indicate posible selected destination
                    selectedTreeItem = Main.get().activeFolderTree.elementClicked(DOM.eventGetTarget(event));
                    TreeItem actualItem = Main.get().activeFolderTree.getActualItem();
                    
                    // Removes always style of las selected treeItem
                    if (lastSelectedTreeItem!=null && !actualItem.equals(lastSelectedTreeItem)){
                    	DOM.setElementProperty(lastSelectElement,"className","gwt-TreeItem");
                		lastSelectedTreeItem = null;
                	}
                    
                    // Sets the style of actual tree item
                    if (selectedTreeItem!=null) {
                    	
                    	selectedElement = DOM.getChild(DOM.getChild(DOM.getChild(DOM.getChild(DOM.getChild(selectedTreeItem.getElement(),0),0),0),1),0);
                    	DOM.setElementProperty(selectedElement,"className","gwt-TreeItem gwt-TreeItem-selected");
                    	
                    	if (lastSelectedTreeItem!=null && !selectedTreeItem.equals(lastSelectedTreeItem) && 
                    		!actualItem.equals(lastSelectedTreeItem)){
                    		
                    		DOM.setElementProperty(lastSelectElement,"className","gwt-TreeItem");
                    	}
                    	
                    	lastSelectedTreeItem = selectedTreeItem;
                    	lastSelectElement = selectedElement;
                    } 
                    
                    // Action depends dragables destinations widgets
         			Main.get().mainPanel.navigator.scrollTaxonomyPanel.ScrollOnDragDrop(posX + 1, posY);
                }

                super.onMouseMove(sender, x, y);
            }
		});
		
		initWidget(floater);
	}
	
	/**
	 * Sets the HTML value to floater
	 * @param html
	 */
	public void show(String html, int originPanel){
		this.originPanel = originPanel;
		DOM.addEventPreview(eventPreview);
		DOM.setCapture(floater.getElement());
		floater.setHTML(html);
		
		// Initialize values
// dragged=false;
		dragged=true;
		selectedTreeItem = null;
		lastSelectedTreeItem = null;

		timer = new Timer() {
			public void run() {
//				dragged=true;
				dropEnabled=true;
			}
		};
		
		timer.scheduleRepeating(1500); // 1,5 seconds
	}
	
	/**
	 * Move document or folder
	 */
	final AsyncCallback callbackMove = new AsyncCallback() {
		public void onSuccess(Object result) {				
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("Move", caught);
		}
	};
	
	/**
	 * Prevents folder incosistences changing moved path on childs recursivelly
	 * nodes drawed
	 * 
	 * @param item The tree node
	 */
	public void preventFolderInconsitences(TreeItem item, String oldPath, String newPath, String parentPath) {
		GWTFolder folderItem = (GWTFolder) item.getUserObject();
		
		folderItem.setPath(folderItem.getPath().replaceFirst(oldPath,newPath));
		folderItem.setParentPath(parentPath);
		
		// Recursively changing paht value
		for (int i=0; i<item.getChildCount();i++) {
			preventFolderInconsitences(item.getChild(i), oldPath, newPath, folderItem.getPath());
		}
	}	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#add(com.google.gwt.user.client.ui.Widget)
	 */
	public void add(Widget w) {
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#clear()
	 */
	public void clear() {
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#iterator()
	 */
	public Iterator iterator() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#remove(com.google.gwt.user.client.ui.Widget)
	 */
	public boolean remove(Widget w) {
		return true;
	}
	
}