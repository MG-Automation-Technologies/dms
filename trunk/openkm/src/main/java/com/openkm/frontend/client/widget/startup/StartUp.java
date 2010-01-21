 /**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
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

package com.openkm.frontend.client.widget.startup;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTBookmark;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.config.Config;
import com.openkm.frontend.client.service.OKMAuthService;
import com.openkm.frontend.client.service.OKMAuthServiceAsync;
import com.openkm.frontend.client.service.OKMBookmarkService;
import com.openkm.frontend.client.service.OKMBookmarkServiceAsync;
import com.openkm.frontend.client.service.OKMPropertyGroupService;
import com.openkm.frontend.client.service.OKMPropertyGroupServiceAsync;
import com.openkm.frontend.client.service.OKMRepositoryService;
import com.openkm.frontend.client.service.OKMRepositoryServiceAsync;

/**
 * @author jllort
 *
 */
public class StartUp {
	
	public static final int STARTUP_STARTING								= 0;
	public static final int STARTUP_GET_TAXONOMY_ROOT						= 1;
	public static final int STARTUP_GET_THESAURUS_ROOT 						= 2;
	public static final int STARTUP_GET_TEMPLATE_ROOT 						= 3;
	public static final int STARTUP_GET_PERSONAL 	  						= 4;
	public static final int STARTUP_GET_MAIL 	  							= 5;
	public static final int STARTUP_GET_TRASH 	 	  						= 6;
	public static final int STARTUP_GET_USER_HOME 	  						= 7;
	public static final int STARTUP_GET_BOOKMARKS							= 8;
	public static final int STARTUP_GET_PROPERTY_GROUP_TRANSLATIONS			= 9;
	public static final int STARTUP_LOADING_TAXONOMY						= 10;
	public static final int STARTUP_LOADING_TAXONOMY_FOLDERS				= 11;
	public static final int STARTUP_LOADING_TAXONOMY_EVAL_PARAMS			= 12;
	public static final int STARTUP_LOADING_OPEN_PATH						= 13;
	public static final int STARTUP_LOADING_TAXONOMY_FILEBROWSER_FOLDERS	= 14;
	public static final int STARTUP_LOADING_TAXONOMY_FILEBROWSER_DOCUMENTS	= 15;
	public static final int STARTUP_LOADING_TAXONOMY_FILEBROWSER_MAILS		= 16;
	public static final int STARTUP_LOADING_THESAURUS						= 17;
	public static final int STARTUP_LOADING_TEMPLATES						= 18;
	public static final int STARTUP_LOADING_PERSONAL						= 19;
	public static final int STARTUP_LOADING_MAIL							= 20;	
	public static final int STARTUP_LOADING_TRASH							= 21;
	public static final int STARTUP_LOADING_HISTORY_SEARCH					= 22;
	public static final int STARTUP_GET_USER_VALUES							= 23;
	public static final int STARTUP_KEEP_ALIVE								= 24;
	
	private final OKMBookmarkServiceAsync bookmarkService = (OKMBookmarkServiceAsync) GWT.create(OKMBookmarkService.class);
	private final OKMRepositoryServiceAsync repositoryService = (OKMRepositoryServiceAsync) GWT.create(OKMRepositoryService.class);
	private final OKMAuthServiceAsync authService = (OKMAuthServiceAsync) GWT.create(OKMAuthService.class);
	private final OKMPropertyGroupServiceAsync propertyGroupService = (OKMPropertyGroupServiceAsync) GWT.create(OKMPropertyGroupService.class);
	
	private boolean enabled = true;
	private boolean error = false;
	private int status = -1;
	public Timer keepAlive;
	
	/**
	 * Inits on first load
	 */
	public void init(){
		nextStatus(STARTUP_STARTING);
	}
	
	/**
	 * Gets asyncronous taxonomy root node
	 */
	final AsyncCallback<GWTFolder> callbackGetRoot = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			//Only executes on initalization and evalues root Node permissions
			Main.get().taxonomyRootFolder = result;
			Main.get().mainPanel.browser.fileBrowser.table.fillWidth(); // Sets de columns size
			nextStatus(STARTUP_GET_THESAURUS_ROOT);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetRoot", caught);
		}
	};
	
	/**
	 * Gets asyncronous template root node
	 */
	final AsyncCallback<GWTFolder> callbackGetTemplate = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			// Only executes on initalization
			Main.get().templatesRootFolder = result;
			nextStatus(STARTUP_GET_PERSONAL);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetTemplate", caught);
		}
	};
	
	/**
	 * Gets asyncronous mail root node
	 */
	final AsyncCallback<GWTFolder> callbackGetMail = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			// Only executes on initalization
			Main.get().mailRootFolder = result;
			nextStatus(STARTUP_GET_TRASH);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getMail", caught);
		}
	};
	
	/**
	 * Gets asyncronous thesaurus root node
	 */
	final AsyncCallback<GWTFolder> callbackGetThesaurus = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			// Only executes on initalization
			Main.get().thesaurusRootFolder = result;
			nextStatus(STARTUP_GET_TEMPLATE_ROOT);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getThesaurus", caught);
		}
	};

	/**
	 * Callback get user home
	 */
	final AsyncCallback<GWTBookmark> callbackGetUserHome = new AsyncCallback<GWTBookmark>() {
		public void onSuccess(GWTBookmark result) {
			Main.get().userHome = result;
			nextStatus(STARTUP_GET_BOOKMARKS);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getUserHome", caught);
		}
	};
	
	/**
	 * Gets asyncronous personal documents node
	 */
	final AsyncCallback<GWTFolder> callbackGetPersonal = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			Main.get().personalRootFolder = result;
			nextStatus(STARTUP_GET_MAIL);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("GetPersonal", caught);
		}
	};
	
	/**
	 * Gets asyncronous trash node
	 */
	final AsyncCallback<GWTFolder> callbackGetTrash = new AsyncCallback<GWTFolder>() {
		public void onSuccess(GWTFolder result) {
			Main.get().trashRootFolder = result;
			nextStatus(STARTUP_GET_USER_HOME);
		}
		
		public void onFailure(Throwable caught) {
			Main.get().showError("GetTrash", caught);
		}
	};
	
	/**
	 * Call back add new granted user
	 */
	final AsyncCallback<Object> callbackKeepAlive = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {
		}
			
		public void onFailure(Throwable caught) {
			Main.get().mainPanel.bottomPanel.setStatus("status.keep.alive.error",true);
		}
	};
	
	/**
	 * Gets asyncronous to add a group
	 */
	final AsyncCallback<Map<String,String>> callbackGetPropertyGroupTranslations = new AsyncCallback<Map<String,String>>() {
		public void onSuccess(Map<String,String> result){
			Main.get().hPropertyGroupI18n = result;
			nextStatus(StartUp.STARTUP_LOADING_TAXONOMY); // Sets the next status to loading
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getTranslations", caught);
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
	 * Gets the personal documents
	 */
	public void getPersonal() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);
		repositoryService.getPersonal(callbackGetPersonal);
	}
	
	/**
	 * Gets the user home
	 * 
	 */
	public void getUserHome() {
		ServiceDefTarget endPoint = (ServiceDefTarget) bookmarkService;
		endPoint.setServiceEntryPoint(Config.OKMBookmarkService);			
		bookmarkService.getUserHome(callbackGetUserHome);
	}
	
	/**
	 * Gets the template
	 */
	public void getTemplate() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);
		repositoryService.getTemplate(callbackGetTemplate);
	}
	
	/**
	 * Gets the mail
	 */
	public void getMail() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);
		repositoryService.getMail(callbackGetMail);
	}
	
	/**
	 * Gets the thesaurus
	 */
	public void getThesaurus() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);
		repositoryService.getThesaurus(callbackGetThesaurus);
	}
	
	/**
	 * Gets the taxonomy
	 */
	public void getRoot() {
		ServiceDefTarget endPoint = (ServiceDefTarget) repositoryService;
		endPoint.setServiceEntryPoint(Config.OKMRepositoryService);	
		repositoryService.getRoot(callbackGetRoot);
	}
	
	/**
	 * Get property translations
	 */
	public void getPropetyGroupTranslations() {
		ServiceDefTarget endPoint = (ServiceDefTarget) propertyGroupService;
		endPoint.setServiceEntryPoint(Config.OKMPropertyGroupService);	
		propertyGroupService.getTranslations(Main.get().getLang(), callbackGetPropertyGroupTranslations);
	}
	
	/**
	 * Sets the keep alive
	 */
	public void keepAlive() {
		// KeepAlieve thread
	    ServiceDefTarget endPoint = (ServiceDefTarget) authService;
		endPoint.setServiceEntryPoint(Config.OKMAuthService);
		keepAlive = new Timer() {
			public void run() {
				authService.keepAlive(callbackKeepAlive);
			}
		};
		
		keepAlive.scheduleRepeating(900*1000); // 15 min
	}
	
	/**
	 * Sets the next status
	 * 
	 * @param status The new status 
	 */
	public void nextStatus( int status) {
		if (enabled) {
			// Status is always incremental
			if (this.status<status) {
				this.status = status;
				
				switch (status) {
					case STARTUP_STARTING:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.starting.loading"), STARTUP_STARTING);
						nextStatus(STARTUP_GET_TAXONOMY_ROOT);
						break;
						
					case STARTUP_GET_TAXONOMY_ROOT:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.taxonomy"), STARTUP_GET_TAXONOMY_ROOT);
						getRoot();
						break;
						
					case STARTUP_GET_THESAURUS_ROOT:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.thesaurus"), STARTUP_GET_THESAURUS_ROOT);
						getThesaurus();
						break;
					
					case STARTUP_GET_TEMPLATE_ROOT:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.template"), STARTUP_GET_TEMPLATE_ROOT);
						getTemplate();
						break;
						
					case STARTUP_GET_PERSONAL :
						Main.get().startUpPopup.addStatus(Main.i18n("startup.personal"), STARTUP_GET_PERSONAL);
						getPersonal();
						break;
						
					case STARTUP_GET_MAIL :
						Main.get().startUpPopup.addStatus(Main.i18n("startup.mail"), STARTUP_GET_MAIL);
						getMail();
						break;
					
					case STARTUP_GET_TRASH :
						Main.get().startUpPopup.addStatus(Main.i18n("startup.trash"), STARTUP_GET_TRASH);
						getTrash();
						break;
				
					case STARTUP_GET_USER_HOME :
						Main.get().startUpPopup.addStatus(Main.i18n("startup.user.home"), STARTUP_GET_USER_HOME);
						getUserHome();
						break;
					
					case STARTUP_GET_BOOKMARKS:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.bookmarks"), STARTUP_GET_BOOKMARKS);
						Main.get().mainPanel.topPanel.mainMenu.bookmark.getAll(); 	// Initialize bookmarks
						Main.get().mainPanel.browser.tabMultiple.init();			// Initialize tab multiple
						break;
						
					case STARTUP_GET_PROPERTY_GROUP_TRANSLATIONS:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.property.group.translations"), STARTUP_GET_PROPERTY_GROUP_TRANSLATIONS);
						getPropetyGroupTranslations();
						break;
					
					case STARTUP_LOADING_TAXONOMY:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.taxonomy"), STARTUP_LOADING_TAXONOMY);
						Main.get().mainPanel.navigator.taxonomyTree.init();			// Initialize folder tree
						break;
					
					case STARTUP_LOADING_TAXONOMY_FOLDERS:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.taxonomy.getting.folders"), STARTUP_LOADING_TAXONOMY_FOLDERS);
						break;
					
					case STARTUP_LOADING_TAXONOMY_EVAL_PARAMS:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.taxonomy.eval.params"), STARTUP_LOADING_TAXONOMY_EVAL_PARAMS);
						break;
					
					case STARTUP_LOADING_OPEN_PATH:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.taxonomy.open.path"), STARTUP_LOADING_OPEN_PATH);
						break;
					
					case STARTUP_LOADING_TAXONOMY_FILEBROWSER_FOLDERS:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.taxonomy.getting.filebrowser.folders"),
														  STARTUP_LOADING_TAXONOMY_FILEBROWSER_FOLDERS);
						break;
					
					case STARTUP_LOADING_TAXONOMY_FILEBROWSER_DOCUMENTS:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.taxonomy.getting.filebrowser.documents"),
														  STARTUP_LOADING_TAXONOMY_FILEBROWSER_DOCUMENTS);
						break;
					
					case STARTUP_LOADING_TAXONOMY_FILEBROWSER_MAILS:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.taxonomy.getting.filebrowser.mails"),
														  STARTUP_LOADING_TAXONOMY_FILEBROWSER_MAILS);
						break;
						
					case STARTUP_LOADING_THESAURUS:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.thesaurus"), STARTUP_LOADING_THESAURUS);
						Main.get().mainPanel.navigator.thesaurusTree.init();	  	// Initialize thesaurus
						break;
						
					case STARTUP_LOADING_TEMPLATES:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.templates"), STARTUP_LOADING_TEMPLATES);
						Main.get().mainPanel.navigator.templateTree.init();	   		// Initialize templates
						break;
					
					case STARTUP_LOADING_PERSONAL:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.personal"), STARTUP_LOADING_PERSONAL);
						Main.get().mainPanel.navigator.personalTree.init();			// Initialize my documents
						break;

					case STARTUP_LOADING_MAIL:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.mail"), STARTUP_LOADING_MAIL);
						Main.get().mainPanel.navigator.mailTree.init();				// Initialize mail
						break;

					case STARTUP_LOADING_TRASH:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.trash"), STARTUP_LOADING_TRASH);
						Main.get().mainPanel.navigator.trashTree.init();			// Initialize trash folder
						break;
					
					case STARTUP_LOADING_HISTORY_SEARCH:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.history.search"), STARTUP_LOADING_HISTORY_SEARCH);
						Main.get().mainPanel.historySearch.searchSaved.init();		// Initialize history saved
						Main.get().mainPanel.historySearch.userNews.init();
						Main.get().mainPanel.setVisible(true);
						break;
					
					case STARTUP_GET_USER_VALUES:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.loading.user.values"), STARTUP_GET_USER_VALUES);
						Main.get().workspaceUserProperties.init();
						break;
						
					case STARTUP_KEEP_ALIVE:
						Main.get().startUpPopup.addStatus(Main.i18n("startup.keep.alive"), STARTUP_KEEP_ALIVE);
						keepAlive();
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
		
		if (status<STARTUP_LOADING_HISTORY_SEARCH) {	
			// This range are sequential calls
			if (status<STARTUP_LOADING_TAXONOMY) {
				nextStatus(status+1); // Tries to execute next initializing
			
			// This range must start with loading personal ( sequential in this range is break )
			} else if (status<STARTUP_LOADING_THESAURUS) {
				nextStatus(STARTUP_LOADING_THESAURUS); // Tries to execute next initializing
			
            // This range are sequential calls
			} else {
				nextStatus(status+1); // Tries to execute next initializing
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
				
			case STARTUP_GET_TAXONOMY_ROOT:
				msg = Main.i18n("startup.taxonomy");
				break;
				
			case STARTUP_GET_THESAURUS_ROOT:
				msg = Main.i18n("startup.thesaurus");
				break;
			
			case STARTUP_GET_TEMPLATE_ROOT:
				msg = Main.i18n("startup.template");
				break;
				
			case STARTUP_GET_PERSONAL :
				msg = Main.i18n("startup.personal");
				break;
			
			case STARTUP_GET_MAIL :
				msg = Main.i18n("startup.mail");
				break;
			
			case STARTUP_GET_TRASH :
				msg = Main.i18n("startup.trash");
				break;
		
			case STARTUP_GET_USER_HOME :
				msg = Main.i18n("startup.user.home");
				getUserHome();
				break;
			
			case STARTUP_GET_BOOKMARKS:
				msg = Main.i18n("startup.bookmarks");
				break;
				
			case STARTUP_GET_PROPERTY_GROUP_TRANSLATIONS:
				msg = Main.i18n("startup.loading.property.group.translations");
				break;
			
			case STARTUP_LOADING_TAXONOMY:
				msg = Main.i18n("startup.loading.taxonomy");
				break;
			
			case STARTUP_LOADING_TAXONOMY_FOLDERS:
				msg = Main.i18n("startup.loading.taxonomy.getting.folders");
				break;
			
			case STARTUP_LOADING_TAXONOMY_EVAL_PARAMS:
				msg = Main.i18n("startup.loading.taxonomy.eval.params");
				break;
			
			case STARTUP_LOADING_OPEN_PATH:
				msg = Main.i18n("startup.loading.taxonomy.open.path");
				break;
			
			case STARTUP_LOADING_TAXONOMY_FILEBROWSER_FOLDERS:
				msg = Main.i18n("startup.loading.taxonomy.getting.filebrowser.folders");
				break;
			
			case STARTUP_LOADING_TAXONOMY_FILEBROWSER_DOCUMENTS:
				msg = Main.i18n("startup.loading.taxonomy.getting.filebrowser.documents");
				break;
			
			case STARTUP_LOADING_TAXONOMY_FILEBROWSER_MAILS:
				msg = Main.i18n("startup.loading.taxonomy.getting.filebrowser.mails");
				break;
				
			case STARTUP_LOADING_THESAURUS:
				msg = Main.i18n("startup.loading.thesaurus");
				break;
				
			case STARTUP_LOADING_TEMPLATES:
				msg = Main.i18n("startup.loading.templates");
				break;
			
			case STARTUP_LOADING_PERSONAL:
				msg = Main.i18n("startup.loading.personal");
				break;
				
			case STARTUP_LOADING_MAIL:
				msg = Main.i18n("startup.loading.mail");
				break;
			
			case STARTUP_LOADING_TRASH:
				msg = Main.i18n("startup.loading.trash");
				break;
			
			case STARTUP_LOADING_HISTORY_SEARCH:
				msg = Main.i18n("startup.loading.history.search");
				break;
			
			case STARTUP_GET_USER_VALUES:
				msg = Main.i18n("startup.loading.user.values");
				break;
			
			case STARTUP_KEEP_ALIVE:
				msg = Main.i18n("startup.keep.alive");
				break;
				
		}			
		
		return msg;
	}
	
}