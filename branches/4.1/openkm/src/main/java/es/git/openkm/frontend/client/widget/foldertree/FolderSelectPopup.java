/**
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

package es.git.openkm.frontend.client.widget.foldertree;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.bean.GWTFolder;
import es.git.openkm.frontend.client.bean.GWTMail;
import es.git.openkm.frontend.client.bean.GWTPermission;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.panel.PanelDefinition;
import es.git.openkm.frontend.client.service.OKMDocumentService;
import es.git.openkm.frontend.client.service.OKMDocumentServiceAsync;
import es.git.openkm.frontend.client.service.OKMFolderService;
import es.git.openkm.frontend.client.service.OKMFolderServiceAsync;
import es.git.openkm.frontend.client.service.OKMMailService;
import es.git.openkm.frontend.client.service.OKMMailServiceAsync;
import es.git.openkm.frontend.client.util.Util;

public class FolderSelectPopup extends DialogBox  {
	public static final int ENTRYPOINT_NONE   		= -1;
	public static final int ENTRYPOINT_TAXONOMY		= 0;
	public static final int ENTRYPOINT_BROWSER 		= 1;
	public static final int ENTRYPOINT_TEMPLATES	= 2;
	public static final int ENTRYPOINT_MY_DOCUMENTS	= 3;
	public static final int ENTRYPOINT_KEYBOARD		= 4;
	public static final int ENTRYPOINT_TRASH		= 5;
	public static final int ENTRYPOINT_MAIL			= 6;
	public static final int ENTRYPOINT_MAIL_ATTACH	= 7;
	
	public static final int ACTION_NONE					= -1;
	public static final int ACTION_MOVE 				= 0;
	public static final int ACTION_COPY 				= 1;
	public static final int ACTION_RESTORE 				= 2;
	public static final int ACTION_CREATE_FROM_TEMPLATE = 3;
	
	public static final int DOCUMENT = 0;
	public static final int FOLDER = 1;
	public static final int MAIL = 2;
	
	private final OKMFolderServiceAsync folderService = (OKMFolderServiceAsync) GWT.create(OKMFolderService.class);
	private final OKMDocumentServiceAsync documentService = (OKMDocumentServiceAsync) GWT.create(OKMDocumentService.class);
	private final OKMMailServiceAsync mailService = (OKMMailServiceAsync) GWT.create(OKMMailService.class);
	
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private HorizontalPanel hListPanel;
	private HorizontalPanel hContextPanel;
	private ScrollPanel scrollDirectoryPanel;
	private VerticalPanel verticalDirectoryPanel;
	private FolderSelectTree folderSelectTree;
	private Button cancelButton;
	private Button actionButton;
	private ListBox contextListBox;
	private HTML contextTxt;
	private int type;  // Determines the type DOCUMENT OR FOLDER to restore
	private Object node; // Document or folder to be restored, copyed etc...
	private int entryPoint; // Entry point fired on tree or browser trash
	private int action = ACTION_NONE; // Sets the action ( move / copy )
	private int originPanel = ENTRYPOINT_NONE;
	private boolean refresh = false;
	private TreeItem itemToBeRemoved;
	private HTML status = new HTML();
	private String msgProperty = "";
	private String errorMsgProperty = "";
	
	public FolderSelectPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		
		vPanel = new VerticalPanel();		
		vPanel.setWidth("300");
		vPanel.setHeight("200");
		hPanel = new HorizontalPanel();
		hListPanel  = new HorizontalPanel();
		hContextPanel = new HorizontalPanel();
		
		contextTxt = new HTML(Main.i18n("search.context"));
		contextListBox = new ListBox();
		contextListBox.setStyleName("okm-Select");
		contextListBox.addItem(Main.i18n("leftpanel.label.taxonomy"),""+PanelDefinition.NAVIGATOR_TAXONOMY);
		contextListBox.addItem(Main.i18n("leftpanel.label.templates"),""+PanelDefinition.NAVIGATOR_TEMPLATES);
		contextListBox.addItem(Main.i18n("leftpanel.label.my.documents"),""+PanelDefinition.NAVIGATOR_PERSONAL);
		contextListBox.addItem(Main.i18n("leftpanel.label.mail"),""+PanelDefinition.NAVIGATOR_MAIL);
		contextListBox.addChangeListener(new ChangeListener() {
				public void onChange(Widget sender) {
					folderSelectTree.changeView(Integer.parseInt(contextListBox.getValue(contextListBox.getSelectedIndex())));
				}
			}
		);
		hContextPanel.add(contextTxt);
		hContextPanel.add(new HTML("&nbsp;&nbsp;"));
		hContextPanel.add(contextListBox);
		hContextPanel.setCellVerticalAlignment(contextTxt, HasVerticalAlignment.ALIGN_MIDDLE);
		
		hListPanel.add(hContextPanel);
		hListPanel.setWidth("290");
		
		scrollDirectoryPanel = new ScrollPanel();
		scrollDirectoryPanel.setSize("290", "150");
		scrollDirectoryPanel.setStyleName("okm-Popup-text");
		verticalDirectoryPanel = new VerticalPanel();
		verticalDirectoryPanel.setSize("100%", "100%");
		folderSelectTree = new FolderSelectTree();
		folderSelectTree.setSize("100%", "100%");
				
		verticalDirectoryPanel.add(folderSelectTree);
		scrollDirectoryPanel.add(verticalDirectoryPanel);
		
		cancelButton = new Button(Main.i18n("button.cancel"), new ClickListener() {
			public void onClick(Widget sender) {
				action = ACTION_NONE;
				hide();
			}
		});
		
		actionButton = new Button(Main.i18n("button.move"), new ClickListener() {
			public void onClick(Widget sender) {
				executeAction(folderSelectTree.getActualPath(), false);
			}
		});
		
		status.setWidth("280");
		status.setWordWrap(true);
		status.setStyleName("fancyfileupload-pending");
		status.setVisible(false);
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(hListPanel);
		vPanel.add(new HTML("<br>"));
		vPanel.add(scrollDirectoryPanel);
		vPanel.add(status);
		vPanel.add(new HTML("<br>"));
		hPanel.add(cancelButton);
		HTML space = new HTML();
		space.setWidth("50");
		hPanel.add(space);
		hPanel.add(actionButton);
		vPanel.add(hPanel);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(hListPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(scrollDirectoryPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(status, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHeight(scrollDirectoryPanel, "150");

		cancelButton.setStyleName("okm-Button");
		actionButton.setStyleName("okm-Button");

		super.hide();
		setWidget(vPanel);
	}
	
	/**
	 * Executes the action
	 */
	public void executeAction(String actualPath, boolean refresh) {
		ServiceDefTarget endPoint;
		String fatherPath = "";
		this.refresh = refresh;
		
		switch (type) {
			case DOCUMENT:
				switch (action) {
					case ACTION_MOVE:
						fatherPath = ((GWTDocument) node).getParent();
						// Only move when origin and destination path are not equals
						if (!fatherPath.equals(actualPath)) {
							setActionView();
							endPoint = (ServiceDefTarget) documentService;
							endPoint.setServiceEntryPoint(Config.OKMDocumentService);
							documentService.move( ((GWTDocument) node).getPath(),actualPath, callbackMove);
						} else {
							changeStatusOnError("fileupload.label.error.not.allowed.copy.same.folder");
						}
						break;
						
					case ACTION_COPY:
						fatherPath = ((GWTDocument) node).getParent();
						// Only copy when origin and destination path are not equals
						if (!fatherPath.equals(actualPath)) {
							setActionView();
							endPoint = (ServiceDefTarget) documentService;
							endPoint.setServiceEntryPoint(Config.OKMDocumentService);
							documentService.copy( ((GWTDocument) node).getPath(),actualPath, callbackCopy);
						} else {
							changeStatusOnError("fileupload.label.error.not.allowed.copy.same.folder");
						}
						break;
					
					case ACTION_RESTORE:
						setActionView();
						endPoint = (ServiceDefTarget) documentService;
						endPoint.setServiceEntryPoint(Config.OKMDocumentService);
						documentService.move( ((GWTDocument) node).getPath(),actualPath, callbackRestore);
						break;
					
					case ACTION_CREATE_FROM_TEMPLATE:
						fatherPath = ((GWTDocument) node).getParent();
						// Only create from template when origin and destination path are not equals
						if (!fatherPath.equals(actualPath)) {
							setActionView();
							endPoint = (ServiceDefTarget) documentService;
							endPoint.setServiceEntryPoint(Config.OKMDocumentService);								
							documentService.copy(((GWTDocument) node).getPath(),actualPath,callbackCopyFromTemplate);
						} else {
							changeStatusOnError("fileupload.label.error.not.allowed.create.from.template.same.folder");
						}
						break;
				}
				break;
		
			case MAIL:
				switch (action) {
					case ACTION_MOVE:
						fatherPath = ((GWTMail) node).getParent();
						// Only move when origin and destination path are not equals
						if (!fatherPath.equals(actualPath)) {
							setActionView();
							endPoint = (ServiceDefTarget) mailService;
							endPoint.setServiceEntryPoint(Config.OKMMailService);
							mailService.move( ((GWTMail) node).getPath(),actualPath, callbackMove);
						} else {
							changeStatusOnError("fileupload.label.error.not.allowed.copy.same.folder");
						}
						break;
						
					case ACTION_COPY:
						fatherPath = ((GWTMail) node).getParent();
						// Only copy when origin and destination path are not equals
						if (!fatherPath.equals(actualPath)) {
							setActionView();
							endPoint = (ServiceDefTarget) mailService;
							endPoint.setServiceEntryPoint(Config.OKMMailService);
							mailService.copy( ((GWTMail) node).getPath(),actualPath, callbackCopy);
						} else {
							changeStatusOnError("fileupload.label.error.not.allowed.copy.same.folder");
						}
						break;
					
					case ACTION_RESTORE:
						setActionView();
						endPoint = (ServiceDefTarget) mailService;
						endPoint.setServiceEntryPoint(Config.OKMMailService);
						mailService.move( ((GWTMail) node).getPath(),actualPath, callbackRestore);
						break;
				}
				break;
				
		case FOLDER:
			switch (action) {
				case ACTION_MOVE:
					// Only move when origin not contained on destination path and destination not equals actual parent
					if ( actualPath.indexOf(((GWTFolder) node).getPath())==-1 && !((GWTFolder) node).getParentPath().equals(actualPath)) {
						setActionView();
						endPoint = (ServiceDefTarget) folderService;
						endPoint.setServiceEntryPoint(Config.OKMFolderService);
						folderService.move( ((GWTFolder) node).getPath(),actualPath, callbackMove);
					} else {
						changeStatusOnError("fileupload.label.error.not.allowed.move.folder.child");
					}
					break;
				
				case ACTION_COPY:
					// Only copy when origin and destination path are not equals
					if ( !((GWTFolder) node).getPath().equals(actualPath) ) {
						setActionView();
						endPoint = (ServiceDefTarget) folderService;
						endPoint.setServiceEntryPoint(Config.OKMFolderService);
						folderService.copy( ((GWTFolder) node).getPath(),actualPath, callbackCopy);
					} else {
						changeStatusOnError("fileupload.label.error.not.allowed.copy.same.folder");
					}
					break;
				
				case ACTION_RESTORE:
					setActionView();
					endPoint = (ServiceDefTarget) folderService;
					endPoint.setServiceEntryPoint(Config.OKMFolderService);
					folderService.move( ((GWTFolder) node).getPath(), actualPath, callbackRestore);
					break;
			}
			break;
		}
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		contextTxt.setHTML(Main.i18n("search.context"));
		setText(Main.i18n("trash.directory.select.label"));
		cancelButton.setText(Main.i18n("button.cancel"));
		
		if (action == ACTION_COPY ) {
			actionButton.setText(Main.i18n("button.copy"));
		} else {
			actionButton.setText(Main.i18n("button.move"));
		}
		
		contextListBox.setItemText(0,Main.i18n("leftpanel.label.taxonomy"));
		contextListBox.setItemText(1,Main.i18n("leftpanel.label.templates"));
		contextListBox.setItemText(2,Main.i18n("leftpanel.label.my.documents"));
		contextListBox.setItemText(3,Main.i18n("leftpanel.label.mail"));
	}
	
	/**
	 * Shows the popup 
	 */
	public void show(){
		initButtons();
		status.setVisible(false);
		int left = (Window.getClientWidth()-300) / 2;
		int top = (Window.getClientHeight()-200) / 2;
		setPopupPosition(left, top);
		setText(Main.i18n("trash.directory.select.label"));
		
		// Resets to initial tree value
		folderSelectTree.reset();
		super.show();
	}
	
	/**
	 * Move document or folder
	 */
	final AsyncCallback callbackMove = new AsyncCallback() {
		public void onSuccess(Object result) {	
			hide();
			action = ACTION_NONE; // Always resets initial value to ACTION_NONE
			switch (entryPoint) {
				case ENTRYPOINT_TAXONOMY:
				case ENTRYPOINT_TEMPLATES:
				case ENTRYPOINT_TRASH :
				case ENTRYPOINT_MAIL :
				case ENTRYPOINT_MY_DOCUMENTS :
					Main.get().activeFolderTree.deleteMovedOrRestored();
					break;
					
				case ENTRYPOINT_BROWSER:
					Main.get().mainPanel.browser.fileBrowser.deleteMovedOrMoved();
					break;
				
				case ENTRYPOINT_KEYBOARD:
					// Removes the item
					if (itemToBeRemoved!=null) {
						TreeItem parentItem = itemToBeRemoved.getParentItem();
						parentItem.removeItem(itemToBeRemoved);
						if (parentItem.getChildCount()==0) {
							((GWTFolder) parentItem.getUserObject()).setHasChilds(false);
						}
						
						switch(originPanel) {
							case PanelDefinition.NAVIGATOR_TAXONOMY:
								Main.get().mainPanel.navigator.taxonomyTree.evaluesFolderIcon(parentItem);
								Main.get().mainPanel.navigator.taxonomyTree.actualItem = parentItem; // Changes the actualItem because
																									 // has been moved and on restore
																									 // view ( refreshing ) needs new path
								break;
							case PanelDefinition.NAVIGATOR_PERSONAL:
								Main.get().mainPanel.navigator.personalTree.evaluesFolderIcon(parentItem);
								Main.get().mainPanel.navigator.taxonomyTree.actualItem = parentItem; // Changes the actualItem because
								 																	 // has been moved and on restore
								 																	 // view ( refreshing ) needs new path
								break;
							case PanelDefinition.NAVIGATOR_TEMPLATES:
								Main.get().mainPanel.navigator.templateTree.evaluesFolderIcon(parentItem);
								Main.get().mainPanel.navigator.taxonomyTree.actualItem = parentItem; // Changes the actualItem because
								 																	 // has been moved and on restore
								 																	 // view ( refreshing ) needs new path
							case PanelDefinition.NAVIGATOR_MAIL:
								Main.get().mainPanel.navigator.mailTree.evaluesFolderIcon(parentItem);
								Main.get().mainPanel.navigator.taxonomyTree.actualItem = parentItem; // Changes the actualItem because
								 																	 // has been moved and on restore
								 																	 // view ( refreshing ) needs new path
								break;
						}
						itemToBeRemoved = null;
					}
					originPanel = ENTRYPOINT_NONE; // Resets the originPanel value
					break;
			}
			
			// Refreshing
			if (refresh) {
				Main.get().mainPanel.topPanel.toolBar.executeRefresh();
				refresh = false;
			}
			// Refreshing users repository size
			Main.get().workspaceUserProperties.getUserDocumentsSize();
		}

		public void onFailure(Throwable caught) {
			changeStatusOnError(errorMsgProperty);
			Main.get().showError("Move", caught);
		}
	};
	
	/**
	 * Copy document or folder
	 */
	final AsyncCallback callbackCopy = new AsyncCallback() {
		public void onSuccess(Object result) {
			action = ACTION_NONE; // Always resets initial value to ACTION_NONE
			hide();
			switch(entryPoint) {
				case ENTRYPOINT_MAIL_ATTACH:
					refresh = true;
					break;
			}
			
			if (refresh) {
				Main.get().mainPanel.topPanel.toolBar.executeRefresh();
				refresh = false;
			}
			// Refreshing users repository size
			Main.get().workspaceUserProperties.getUserDocumentsSize();
		}

		public void onFailure(Throwable caught) {
			changeStatusOnError(errorMsgProperty);
			Main.get().showError("Copy", caught);
		}
	};
	
	/**
	 * Create document from template
	 */
	final AsyncCallback callbackCopyFromTemplate = new AsyncCallback() {
		public void onSuccess(Object result) {	
			action = ACTION_NONE; // Always resets initial value to ACTION_NONE
			hide();
			String docPath = folderSelectTree.getActualPath() + "/" + ((GWTDocument) node).getName();
			String path = folderSelectTree.getActualPath();
			switch (Integer.parseInt(contextListBox.getValue(contextListBox.getSelectedIndex()))) {
				case PanelDefinition.NAVIGATOR_TAXONOMY:
					Main.get().mainPanel.navigator.stackPanel.showStack(PanelDefinition.NAVIGATOR_TAXONOMY, false);
					Main.get().mainPanel.browser.fileBrowser.setFlagCreatedFromTemplate();
					Main.get().activeFolderTree.openAllPathFolder(path,docPath);
					break;
					
				case PanelDefinition.NAVIGATOR_PERSONAL:
					Main.get().mainPanel.navigator.stackPanel.showStack(PanelDefinition.NAVIGATOR_PERSONAL, false);
					Main.get().mainPanel.browser.fileBrowser.setFlagCreatedFromTemplate();
					Main.get().activeFolderTree.openAllPathFolder(path,docPath);
					break;
					
				case PanelDefinition.NAVIGATOR_MAIL:
					Main.get().mainPanel.navigator.stackPanel.showStack(PanelDefinition.NAVIGATOR_MAIL, false);
					Main.get().mainPanel.browser.fileBrowser.setFlagCreatedFromTemplate();
					Main.get().activeFolderTree.openAllPathFolder(path,docPath);
					break;
					
			}
			// Refreshing users repository size
			Main.get().workspaceUserProperties.getUserDocumentsSize();
		}

		public void onFailure(Throwable caught) {
			changeStatusOnError(errorMsgProperty);
			Main.get().showError("CreateFromTemplate", caught);
		}
	};
	
	/**
	 * Restore documento or folder
	 */
	final AsyncCallback callbackRestore = new AsyncCallback() {
		public void onSuccess(Object result) {	
			action = ACTION_NONE; // Always resets initial value to ACTION_NONE
			hide();
			switch (entryPoint) {
				case ENTRYPOINT_TAXONOMY:
					Main.get().activeFolderTree.deleteMovedOrRestored();
					break;
				case ENTRYPOINT_BROWSER:
					Main.get().mainPanel.browser.fileBrowser.deleteMovedOrMoved();
					break;
			}
			// Refreshing users repository size
			Main.get().workspaceUserProperties.getUserDocumentsSize();
		}

		public void onFailure(Throwable caught) {
			changeStatusOnError(errorMsgProperty);
			Main.get().showError("Restore", caught);
		}
	};
	
	/**
	 * Sets the document to restore
	 * 
	 * @param document The document object
	 */
	public void setToMove(GWTDocument document){
		type = DOCUMENT;
		this.node = document;
		action = ACTION_MOVE;  // Sets the action
		actionButton.setText(Main.i18n("button.move"));
		msgProperty = "fileupload.status.move.file";
		errorMsgProperty = "fileupload.label.error.move.file";
	}
	
	/**
	 * Sets the mail to restore
	 * 
	 * @param mail The mail object
	 */
	public void setToMove(GWTMail mail){
		type = MAIL;
		this.node = mail;
		action = ACTION_MOVE;  // Sets the action
		actionButton.setText(Main.i18n("button.move"));
		msgProperty = "fileupload.status.move.mail";
		errorMsgProperty = "fileupload.label.error.move.mail";
	}
	
	/**
	 * Sets the document to copy
	 * 
	 * @param document The document object
	 */
	public void setToCopy(GWTDocument document){
		type = DOCUMENT;
		this.node = document;
		action = ACTION_COPY;
		actionButton.setText(Main.i18n("button.copy"));
		msgProperty = "fileupload.status.copy.file";
		errorMsgProperty = "fileupload.label.error.copy.file";
	}
	
	/**
	 * Sets the mail to copy
	 * 
	 * @param mail The mail object
	 */
	public void setToCopy(GWTMail mail){
		type = MAIL;
		this.node = mail;
		action = ACTION_COPY;
		actionButton.setText(Main.i18n("button.copy"));
		msgProperty = "fileupload.status.copy.mail";
		errorMsgProperty = "fileupload.label.error.copy.mail";
	}
	
	/**
	 * Sets the document to create from template
	 * 
	 * @param document The document object
	 */
	public void setToCreateFromTemplate(GWTDocument document){
		type = DOCUMENT;
		this.node = document;
		action = ACTION_CREATE_FROM_TEMPLATE;
		actionButton.setText(Main.i18n("button.create"));
		msgProperty = "fileupload.status.create.from.template";
		errorMsgProperty = "fileupload.label.error.create.from.template";
	}
	
	/**
	 * Sets the folder to restore
	 * 
	 * @param folder The folder object
	 */
	public void setToMove(GWTFolder folder) {
		type = FOLDER;
		this.node = folder;
		action = ACTION_MOVE;
		actionButton.setText(Main.i18n("button.move"));
		msgProperty = "fileupload.status.move.folder";
		errorMsgProperty = "fileupload.label.error.move.folder";
	}
	
	/**
	 * Sets the folder to copy
	 * 
	 * @param folder The folder object
	 */
	public void setToCopy(GWTFolder folder) {
		type = FOLDER;
		this.node = folder;
		action = ACTION_COPY;
		actionButton.setText(Main.i18n("button.copy"));
		msgProperty = "fileupload.status.copy.folder";
		errorMsgProperty = "fileupload.label.error.copy.folder";
	}
	
	/**
	 * Sets the document to restore
	 * 
	 * @param document The document object
	 */
	public void setToRestore(GWTDocument document){
		type = DOCUMENT;
		this.node = document;
		action = ACTION_RESTORE;
		actionButton.setText(Main.i18n("button.restore"));
		msgProperty = "fileupload.status.restore.file";
		errorMsgProperty = "fileupload.label.error.restore.file";
	}
	
	/**
	 * Sets the mail to restore
	 * 
	 * @param mail The document object
	 */
	public void setToRestore(GWTMail mail){
		type = MAIL;
		this.node = mail;
		action = ACTION_RESTORE;
		actionButton.setText(Main.i18n("button.restore"));
		msgProperty = "fileupload.status.restore.mail";
		errorMsgProperty = "fileupload.label.error.restore.mail";
	}
	
	/**
	 * Sets the folder to restore
	 * 
	 * @param folder The folder object
	 */
	public void setToRestore(GWTFolder folder) {
		type = FOLDER;
		this.node = folder;
		action = ACTION_RESTORE;
		actionButton.setText(Main.i18n("button.restore"));
		msgProperty = "fileupload.status.restore.folder";
		errorMsgProperty = "fileupload.label.error.restore.folder";
	}
	
	/**
	 * Sets the entryPoint fired this popup
	 * 
	 * @param entryPoint The entryPoint value
	 */
	public void setEntryPoint(int entryPoint){
		this.entryPoint = entryPoint;
	}
	
	/**
	 * Enables or disables move buttom
	 * 
	 * @param enable
	 */
	public void enable(boolean enable) {
		actionButton.setEnabled(enable);
		status.setVisible(false); // Always hides status
	}
	
	/**
	 * Enable taxonomy
	 */
	public void enableTaxonomy () {
		contextListBox.setItemSelected(0,true);
	}
	
	/**
	 * Enable my documents
	 */
	public void enableMyDocuments() {
		contextListBox.setItemSelected(2,true);
	}
	
	/**
	 * Enable templates
	 */
	public void enableTemplates() {
		contextListBox.setItemSelected(1,true);
	}
	
	/**
	 * Enable templates
	 */
	public void enableMails() {
		contextListBox.setItemSelected(3,true);
	}
	
	/**
	 * Gets the action
	 * 
	 * @return int The action
	 */
	public int getAction(){
		return action;
	}
	
	/**
	 * Evaluates the security for action 
	 * 
	 * @param folder The folder
	 * @return The security grant ( true if granted or false other case)
	 */
	public boolean evaluateActionSecurity(GWTFolder folder) {
		return ((folder.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE);
	}
	
	/**
	 * Sets the tree item to be removed
	 * 
	 * @param item The tree item
	 */
	public void setTreeItemToBeDeleted(TreeItem item){
		itemToBeRemoved = item;
	}
	
	/**
	 * Sets the origin panel
	 * 
	 * @param originPanel The origin panel value
	 */
	public void setOriginPanel(int originPanel) {
		this.originPanel = originPanel;
	}
	
	/**
	 * Sets the action view
	 */
	private void setActionView() {
		cancelButton.setEnabled(false);
		actionButton.setEnabled(false);
		status.setHTML(Util.imageItemHTML("img/indicator.gif")+Main.i18n(msgProperty));
		status.setStyleName("fancyfileupload-pending");
		status.setVisible(true);
	}
	
	/**
	 * Enables all button
	 */
	private void initButtons() {
		cancelButton.setEnabled(true);
		actionButton.setEnabled(false);
	}
	
	/**
	 * Changes the status on error
	 */
	private void changeStatusOnError(String msg) {
		status.setHTML(Main.i18n(msg));
		status.setStyleName("fancyfileupload-failed");
		status.setVisible(true);
		initButtons();
	}
}