/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2011 Paco Avila & Josep Llort
 * 
 * No bytes were intentionally harmed during the development of this application.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.frontend.client.widget.startup;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.openkm.extension.frontend.client.Customization;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTUserConfig;
import com.openkm.frontend.client.contants.service.RPCService;
import com.openkm.frontend.client.extension.ExtensionManager;
import com.openkm.frontend.client.service.OKMAuthService;
import com.openkm.frontend.client.service.OKMAuthServiceAsync;
import com.openkm.frontend.client.service.OKMDocumentService;
import com.openkm.frontend.client.service.OKMDocumentServiceAsync;
import com.openkm.frontend.client.service.OKMFolderService;
import com.openkm.frontend.client.service.OKMFolderServiceAsync;
import com.openkm.frontend.client.service.OKMGeneralService;
import com.openkm.frontend.client.service.OKMGeneralServiceAsync;
import com.openkm.frontend.client.service.OKMRepositoryService;
import com.openkm.frontend.client.service.OKMRepositoryServiceAsync;
import com.openkm.frontend.client.service.OKMUserConfigService;
import com.openkm.frontend.client.service.OKMUserConfigServiceAsync;
import com.openkm.frontend.client.util.CommonUI;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.mainmenu.Bookmark;

/**
 * @author jllort
 * 
 */
public class StartUp {
	
	public static final int STARTUP_STARTING = 0;
	public static final int STARTUP_GET_USER_VALUES = 1;
	public static final int STARTUP_GET_TAXONOMY_ROOT = 2;
	public static final int STARTUP_GET_CATEGORIES_ROOT = 3;
	public static final int STARTUP_GET_THESAURUS_ROOT = 4;
	public static final int STARTUP_GET_TEMPLATE_ROOT = 5;
	public static final int STARTUP_GET_PERSONAL = 6;
	public static final int STARTUP_GET_MAIL = 7;
	public static final int STARTUP_GET_TRASH = 8;
	public static final int STARTUP_GET_USER_HOME = 9;
	public static final int STARTUP_GET_BOOKMARKS = 10;
	public static final int STARTUP_INIT_TREE_NODES = 11;
	public static final int STARTUP_LOADING_HISTORY_SEARCH = 12;
	public static final int STARTUP_LOADING_TAXONOMY_EVAL_PARAMS = 13;
	public static final int STARTUP_LOADING_OPEN_PATH = 14;
	
	private final OKMRepositoryServiceAsync repositoryService = (OKMRepositoryServiceAsync) GWT.create(OKMRepositoryService.class);
	private final OKMAuthServiceAsync authService = (OKMAuthServiceAsync) GWT.create(OKMAuthService.class);
	private final OKMUserConfigServiceAsync userConfigService = (OKMUserConfigServiceAsync) GWT.create(OKMUserConfigService.class);
	private final OKMGeneralServiceAsync generalService = (OKMGeneralServiceAsync) GWT.create(OKMGeneralService.class);
	private final OKMFolderServiceAsync folderService = (OKMFolderServiceAsync) GWT.create(OKMFolderService.class);
	private final OKMDocumentServiceAsync documentService = (OKMDocumentServiceAsync) GWT.create(OKMDocumentService.class);
	
	private boolean enabled = true;
	private boolean error = false;
	private int status = -1;
	private String docPath = null;
	private String fldPath = null;
	private String taskInstanceId = null;
	public Timer keepAlive;
	
	/**
	 * Inits on first load
	 */
	public void init() {
		ServiceDefTarget endPoint = (ServiceDefTarget) generalService;
		endPoint.setServiceEntryPoint(RPCService.GeneralService);
		generalService.getEnabledExtensions(new AsyncCallback<List<String>>() {
			@Override
			public void onSuccess(List<String> result) {
				Main.get().setExtensionUuidList(result);
				// Only show registered extensions
				ExtensionManager.start(Customization.getExtensionWidgets(result));
				nextStatus(STARTUP_STARTING);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Main.get().showError("getEnabledExtensions", caught);
				nextStatus(STARTUP_STARTING);
			}
		});
	}
	
	/**
	 * Gets asyncronous taxonomy root node
	 */
	final AsyncCallback<GWTFolder> callbackGetRootFolder = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			// Only executes on initalization and evalues root Node permissions
			Main.get().taxonomyRootFolder = result;
			Main.get().mainPanel.desktop.browser.fileBrowser.table.fillWidth(); // Sets de columns size
			nextStatus(STARTUP_GET_CATEGORIES_ROOT);
		}
		
		public void onFailure(Throwable caught) {
			Main.get().showError("GetRootFolder", caught);
		}
	};
	
	/**
	 * Gets asyncronous template root node
	 */
	final AsyncCallback<GWTFolder> callbackGetTemplatesFolder = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			// Only executes on initalization
			Main.get().templatesRootFolder = result;
			nextStatus(STARTUP_GET_PERSONAL);
		}
		
		public void onFailure(Throwable caught) {
			Main.get().showError("GetTemplatesFolder", caught);
		}
	};
	
	/**
	 * Gets asyncronous mail root node
	 */
	final AsyncCallback<GWTFolder> callbackGetMailFolder = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			// Only executes on initalization
			Main.get().mailRootFolder = result;
			nextStatus(STARTUP_GET_TRASH);
		}
		
		public void onFailure(Throwable caught) {
			Main.get().showError("GetMailFolder", caught);
		}
	};
	
	/**
	 * Gets asyncronous thesaurus root node
	 */
	final AsyncCallback<GWTFolder> callbackGetThesaurusFolder = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			// Only executes on initalization
			Main.get().thesaurusRootFolder = result;
			nextStatus(STARTUP_GET_TEMPLATE_ROOT);
		}
		
		public void onFailure(Throwable caught) {
			Main.get().showError("GetThesaurusFolder", caught);
		}
	};
	
	/**
	 * Gets asyncronous categories root node
	 */
	final AsyncCallback<GWTFolder> callbackGetCategoriesFolder = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			// Only executes on initalization
			Main.get().categoriesRootFolder = result;
			nextStatus(STARTUP_GET_THESAURUS_ROOT);
		}
		
		public void onFailure(Throwable caught) {
			Main.get().showError("GetCategoriesFolder", caught);
		}
	};
	
	/**
	 * Callback get user home
	 */
	final AsyncCallback<GWTUserConfig> callbackGetUserHome = new AsyncCallback<GWTUserConfig>() {
		public void onSuccess(GWTUserConfig result) {
			Main.get().userHome = result;
			nextStatus(STARTUP_GET_BOOKMARKS);
		}
		
		public void onFailure(Throwable caught) {
			Main.get().showError("GetUserHome", caught);
		}
	};
	
	/**
	 * Gets asyncronous personal documents node
	 */
	final AsyncCallback<GWTFolder> callbackGetPersonalFolder = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			Main.get().personalRootFolder = result;
			nextStatus(STARTUP_GET_MAIL);
		}
		
		public void onFailure(Throwable caught) {
			Main.get().showError("GetPersonalFolder", caught);
		}
	};
	
	/**
	 * Gets asyncronous trash node
	 */
	final AsyncCallback<GWTFolder> callbackGetTrashFolder = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			Main.get().trashRootFolder = result;
			nextStatus(STARTUP_GET_USER_HOME);
		}
		
		public void onFailure(Throwable caught) {
			Main.get().showError("GetTrashFolder", caught);
		}
	};
	
	/**
	 * Call back add new granted user
	 */
	final AsyncCallback<Object> callbackKeepAlive = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {
		}
		
		public void onFailure(Throwable caught) {
			Main.get().mainPanel.bottomPanel.setStatus("status.keep.alive.error", true);
		}
	};
	
	/**
	 * Gets asyncronous to add a group
	 */
	final AsyncCallback<Map<String, String>> callbackGetPropertyGroupTranslations = new AsyncCallback<Map<String, String>>() {
		@Override
		public void onSuccess(Map<String, String> result) {
			Main.get().hPropertyGroupI18n = result;
			
		}
		@Override
		public void onFailure(Throwable caught) {
			Main.get().showError("GetPropertyGroupTranslations", caught);
		}
	};
	
	/**
	 * Gets the trash
	 */
	public void getTrash() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(RPCService.RepositoryService);
		repositoryService.getTrashFolder(callbackGetTrashFolder);
	}
	
	/**
	 * Gets the personal documents
	 */
	public void getPersonal() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(RPCService.RepositoryService);
		repositoryService.getPersonalFolder(callbackGetPersonalFolder);
	}
	
	/**
	 * Gets the user home
	 * 
	 */
	public void getUserHome() {
		ServiceDefTarget endPoint = (ServiceDefTarget) userConfigService;
		endPoint.setServiceEntryPoint(RPCService.UserConfigService);
		userConfigService.getUserHome(callbackGetUserHome);
	}
	
	/**
	 * Gets the template
	 */
	public void getTemplate() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(RPCService.RepositoryService);
		repositoryService.getTemplatesFolder(callbackGetTemplatesFolder);
	}
	
	/**
	 * Gets the mail
	 */
	public void getMail() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(RPCService.RepositoryService);
		repositoryService.getMailFolder(callbackGetMailFolder);
	}
	
	/**
	 * Gets the thesaurus
	 */
	public void getThesaurus() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(RPCService.RepositoryService);
		repositoryService.getThesaurusFolder(callbackGetThesaurusFolder);
	}
	
	public void getCategories() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(RPCService.RepositoryService);
		repositoryService.getCategoriesFolder(callbackGetCategoriesFolder);
	}
	
	/**
	 * Gets the taxonomy
	 */
	public void getRoot() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(RPCService.RepositoryService);
		repositoryService.getRootFolder(callbackGetRootFolder);
	}
	
	public void startKeepAlive(double scheduleTime) {
		// KeepAlieve thread
		ServiceDefTarget endPoint = (ServiceDefTarget) authService;
		endPoint.setServiceEntryPoint(RPCService.AuthService);
		keepAlive = new Timer() {
			public void run() {
				authService.keepAlive(callbackKeepAlive);
			}
		};
		
		keepAlive.scheduleRepeating(new Double(scheduleTime).intValue()); // 15 min
	}
	
	/**
	 * Opens a document destination passed by url parameter
	 */
	private void openDocumentByBrowserURLParam() {
		Main.get().startUp.nextStatus(StartUp.STARTUP_LOADING_TAXONOMY_EVAL_PARAMS);
		fldPath = Main.get().fldPath;
		docPath = Main.get().docPath;
		taskInstanceId = Main.get().taskInstanceId;
		
		
		// Always reset variables
		Main.get().docPath = null;
		Main.get().fldPath = null;
		Main.get().taskInstanceId = null;
		
		// Simulate we pass params by browser ( take a look really are not passed )
		// to show user home on loading
		if (fldPath == null || fldPath.equals("")) {
			if (Main.get().userHome.getHomeType().equals(Bookmark.BOOKMARK_DOCUMENT)) {
				docPath = Main.get().userHome.getHomePath();
				fldPath = Util.getParent(Main.get().userHome.getHomePath());
			} else if (Main.get().userHome.getHomeType().equals(Bookmark.BOOKMARK_FOLDER)) {
				fldPath = Main.get().userHome.getHomePath();
			}
		}
		
		// Opens folder passed by parameter
		if (docPath != null && !docPath.equals("")) {
			documentService.isValid(docPath, new AsyncCallback<Boolean>() {
				@Override
				public void onSuccess(Boolean result) {
					if (result.booleanValue()) {
						// Opens folder passed by parameter
						CommonUI.openAllFolderPath(fldPath, docPath);
					}
					CommonUI.openUserTaskInstance(taskInstanceId); // Always trying opening taskInstance
					Main.get().startUp.nextStatus(StartUp.STARTUP_LOADING_OPEN_PATH);
				}
				@Override
				public void onFailure(Throwable caught) {
					Main.get().showError("isValid", caught);
					Main.get().startUp.nextStatus(StartUp.STARTUP_LOADING_OPEN_PATH);
				}
			});
		} else if (fldPath != null && !fldPath.equals("")) {
			folderService.isValid(fldPath, new AsyncCallback<Boolean>() {
				@Override
				public void onSuccess(Boolean result) {
					if (result.booleanValue()) {
						// Opens folder passed by parameter
						CommonUI.openAllFolderPath(fldPath, "");
					}
					CommonUI.openUserTaskInstance(taskInstanceId); // Always trying opening taskInstance
					Main.get().startUp.nextStatus(StartUp.STARTUP_LOADING_OPEN_PATH);
				}
				@Override
				public void onFailure(Throwable caught) {
					Main.get().showError("isValid", caught);
					Main.get().startUp.nextStatus(StartUp.STARTUP_LOADING_OPEN_PATH);
				}
			});
		} else {
			Main.get().startUp.nextStatus(StartUp.STARTUP_LOADING_OPEN_PATH);
		}
		
		
		
	}
	
	/**
	 * Sets the next status
	 * 
	 * @param status The new status
	 */
	public void nextStatus(int status) {
		if (enabled) {
			// Status is always incremental
			if (this.status < status) {
				this.status = status;
				
				switch (status) {
					case STARTUP_STARTING:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.starting.loading"), STARTUP_STARTING);
						nextStatus(STARTUP_GET_USER_VALUES);
						break;
					
					case STARTUP_GET_USER_VALUES:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.user.values"), STARTUP_GET_USER_VALUES);
						Main.get().workspaceUserProperties.init();
						break;
					
					case STARTUP_GET_TAXONOMY_ROOT:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.taxonomy"), STARTUP_GET_TAXONOMY_ROOT);
						getRoot();
						break;
					
					case STARTUP_GET_CATEGORIES_ROOT:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.categories"), STARTUP_GET_CATEGORIES_ROOT);
						getCategories();
						break;
					
					case STARTUP_GET_THESAURUS_ROOT:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.thesaurus"), STARTUP_GET_THESAURUS_ROOT);
						getThesaurus();
						break;
					
					case STARTUP_GET_TEMPLATE_ROOT:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.template"), STARTUP_GET_TEMPLATE_ROOT);
						getTemplate();
						break;
					
					case STARTUP_GET_PERSONAL:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.personal"), STARTUP_GET_PERSONAL);
						getPersonal();
						break;
					
					case STARTUP_GET_MAIL:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.mail"), STARTUP_GET_MAIL);
						getMail();
						break;
					
					case STARTUP_GET_TRASH:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.trash"), STARTUP_GET_TRASH);
						getTrash();
						break;
					
					case STARTUP_GET_USER_HOME:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.user.home"), STARTUP_GET_USER_HOME);
						getUserHome();
						break;
					
					case STARTUP_GET_BOOKMARKS:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.bookmarks"), STARTUP_GET_BOOKMARKS);
						Main.get().mainPanel.topPanel.mainMenu.bookmark.getAll(); // Initialize bookmarks
						Main.get().mainPanel.desktop.browser.tabMultiple.init(); // Initialize tab multiple
						break;
					
					case STARTUP_INIT_TREE_NODES:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.init.tree.nodes"), STARTUP_INIT_TREE_NODES);
						Main.get().mainPanel.desktop.navigator.taxonomyTree.init();
						Main.get().mainPanel.desktop.navigator.categoriesTree.init();
						Main.get().mainPanel.desktop.navigator.thesaurusTree.init();
						Main.get().mainPanel.desktop.navigator.templateTree.init();
						Main.get().mainPanel.desktop.navigator.personalTree.init();
						Main.get().mainPanel.desktop.navigator.mailTree.init();
						Main.get().mainPanel.desktop.navigator.trashTree.init();
						Main.get().startUp.nextStatus(StartUp.STARTUP_LOADING_HISTORY_SEARCH);
						break;
					
					case STARTUP_LOADING_HISTORY_SEARCH:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.history.search"), STARTUP_LOADING_HISTORY_SEARCH);
						Main.get().mainPanel.search.historySearch.searchSaved.init(); // Initialize history saved
						Main.get().mainPanel.search.historySearch.userNews.init();
						Main.get().mainPanel.setVisible(true);
						Main.get().workspaceUserProperties.setAvailableAction(); // Some actions ( menus / etc ... )
																					// must be set at ends startup
																					// After init widget methods ares
																					// all yet finished
						
						Main.get().startUp.nextStatus(StartUp.STARTUP_LOADING_TAXONOMY_EVAL_PARAMS);
						break;
					
					case STARTUP_LOADING_TAXONOMY_EVAL_PARAMS:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.taxonomy.eval.params"), STARTUP_LOADING_TAXONOMY_EVAL_PARAMS);
						openDocumentByBrowserURLParam();
						break;
					
					case STARTUP_LOADING_OPEN_PATH:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.taxonomy.open.path"), STARTUP_LOADING_OPEN_PATH);
						enabled = false;
						
						if (!error) {
							Main.get().startUpPopup.hide();
						}
						break;
				}
			}
		}
	}
	
	/**
	 * Disable
	 */
	public void disable() {
		enabled = false;
	}
	
	/**
	 * Tries to recover after an error
	 */
	public void recoverFromError() {
		error = true;
		Main.get().startUpPopup.button.setVisible(true);
		
		if (status < STARTUP_LOADING_OPEN_PATH) {
			// This range are sequential calls
			if (status < STARTUP_INIT_TREE_NODES) {
				nextStatus(status + 1); // Tries to execute next initializing
			} else {
				nextStatus(status + 1); // Tries to execute next initializing
			}
		} else {
			enabled = false;
		}
	}
	
	public String getStatusMsg(int status) {
		String msg = "";
		
		switch (status) {
			case STARTUP_STARTING:
				msg = Main.i18n("startup.starting.loading");
				break;
			
			case STARTUP_GET_USER_VALUES:
				msg = Main.i18n("startup.loading.user.values");
				break;
			
			case STARTUP_GET_TAXONOMY_ROOT:
				msg = Main.i18n("startup.taxonomy");
				break;
			
			case STARTUP_GET_CATEGORIES_ROOT:
				msg = Main.i18n("startup.categories");
				break;
			
			case STARTUP_GET_THESAURUS_ROOT:
				msg = Main.i18n("startup.thesaurus");
				break;
			
			case STARTUP_GET_TEMPLATE_ROOT:
				msg = Main.i18n("startup.template");
				break;
			
			case STARTUP_GET_PERSONAL:
				msg = Main.i18n("startup.personal");
				break;
			
			case STARTUP_GET_MAIL:
				msg = Main.i18n("startup.mail");
				break;
			
			case STARTUP_GET_TRASH:
				msg = Main.i18n("startup.trash");
				break;
			
			case STARTUP_GET_USER_HOME:
				msg = Main.i18n("startup.user.home");
				getUserHome();
				break;
			
			case STARTUP_GET_BOOKMARKS:
				msg = Main.i18n("startup.bookmarks");
				break;
			
			case STARTUP_INIT_TREE_NODES:
				msg = Main.i18n("startup.loading.taxonomy");
				break;
			
			case STARTUP_LOADING_HISTORY_SEARCH:
				msg = Main.i18n("startup.loading.history.search");
				break;
			
			case STARTUP_LOADING_TAXONOMY_EVAL_PARAMS:
				msg = Main.i18n("startup.loading.taxonomy.eval.params");
				break;
			
			case STARTUP_LOADING_OPEN_PATH:
				msg = Main.i18n("startup.loading.taxonomy.open.path");
				break;
		}
		
		return msg;
	}
	
	/**
	 * getStatus
	 * 
	 * @return
	 */
	public int getStatus() {
		return status;
	}
}