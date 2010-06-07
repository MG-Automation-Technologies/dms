/**
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

package com.openkm.frontend.client.widget.mainmenu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTAvailableOption;
import com.openkm.frontend.client.bean.ToolBarOption;
import com.openkm.frontend.client.config.Config;
import com.openkm.frontend.client.extension.widget.MenuItemExtension;
import com.openkm.frontend.client.lang.Lang;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.ConfirmPopup;

/**
 * Main menu
 * 
 * @author jllort
 *
 */
public class MainMenu extends Composite {
	
	// URI CONSTANTS
	public static final String URI_HELP = "http://www.openkm.com";
	public static final String URI_BUG_REPORT = "http://issues.openkm.com";
	public static final String URI_SUPPORT_REQUEST = "http://www.openkm.com/Contact/";
	public static final String URI_PUBLIC_FORUM = "http://forum.openkm.com";
	public static final String URI_PROJECT_WEB = "http://www.openkm.com";
	public static final String URI_DOCUMENTATION = "http://wiki.openkm.com";
	public static final String URI_VERSION_CHANGES = "http://wiki.openkm.com/index.php/Changelog";

	private ToolBarOption mainMenuOption;
	public Bookmark bookmark;
	public BookmarkPopup bookmarkPopup;
	public ManageBookmarkPopup manageBookmarkPopup;
	
	private MenuBar MainMenu;
	private MenuItem menuFile;
			private MenuBar subMenuFile;
			private MenuItem createDirectory;
			private MenuItem addDocument;
			private MenuItem download;
			private MenuItem downloadPdf;
			private MenuItem sendDocumentLink;
			private MenuItem export;
			private MenuItem horizontalLineFile1;
			private MenuItem purgeTrash;
			private MenuItem horizontalLineFile2;
			private MenuItem exit;
	private MenuItem menuEdit;
		private MenuBar subMenuEdit;
			private MenuItem lock;
			private MenuItem unlock;
			private MenuItem checkout;
			private MenuItem checkin;
			private MenuItem cancelCheckout;
			private MenuItem delete;
			private MenuItem copy;
			private MenuItem move;
			private MenuItem rename;
	private MenuItem menuTools;
		private MenuBar subMenuTools;
			private MenuItem language;
				private MenuBar subMenuLanguage;
					private MenuItem catalan;
					private MenuItem english;
					private MenuItem spanish;
					private MenuItem farsi;
					private MenuItem french;
					private MenuItem brazilian;
					private MenuItem dutch;
					private MenuItem german;
					private MenuItem gallego;
					private MenuItem italian;
					private MenuItem chineseSimple;
					private MenuItem swedish;
					private MenuItem serbian;
					private MenuItem turkish;
					private MenuItem japanese;
					private MenuItem romanian;
					private MenuItem polish;
					private MenuItem hungarian;
					private MenuItem greece;
					private MenuItem chineseTraditional;
					private MenuItem latvian;
					private MenuItem macedonian;
					private MenuItem colombian;
					private MenuItem russian;
					private MenuItem bosnian;
					private MenuItem czech;
					private MenuItem usa;
					private MenuItem indonesian;
					private MenuItem portuguese;
					private MenuItem arabic;
				private MenuBar subMenuSkin;
					private MenuItem skinDefault;
					private MenuItem skinTest;
					private MenuItem skinMediumFont;
					private MenuItem skinBigFont;
			private MenuItem skin;
			private MenuItem debugConsole;
			public MenuItem administration;
			private MenuBar subMenuPreferences;
				private MenuItem userPreferences;
			private MenuItem preferences;
	private MenuItem menuBookmark;
		public MenuBar subMenuBookmark;
		private MenuItem home;
		private MenuItem defaultHome;
		private MenuItem manageBookmark;
		private MenuItem horizontalLineBookmark1;
	private MenuItem menuHelp;
		private MenuBar subMenuHelp;
			private MenuItem help;
			private MenuItem documentation;
			private MenuItem bugReport;
			private MenuItem supportRequest;
			private MenuItem publicForum;
			private MenuItem versionChanges;
			private MenuItem projectWeb;
			private MenuItem about;
	
	public MainMenu() {
		
		// The bookmark
		bookmark = new Bookmark();
		bookmarkPopup = new BookmarkPopup();
		bookmarkPopup.setWidth("310px");
		bookmarkPopup.setHeight("100px");
		bookmarkPopup.setStyleName("okm-Popup");
		
		// The bookmark management
		manageBookmarkPopup = new ManageBookmarkPopup();
		manageBookmarkPopup.setWidth("400px");
		manageBookmarkPopup.setHeight("230px");
		manageBookmarkPopup.setStyleName("okm-Popup");
		
		// General menu
		MainMenu = new MenuBar(false);
		MainMenu.setStyleName("okm-TopMenuBar");
		
		// File menu
		// First we must create menus and submenus on inverse order 
				createDirectory = new MenuItem(Util.menuHTML("img/icon/actions/add_folder.gif", Main.i18n("general.menu.file.create.directory")), true, createDirectoryOKM);
				createDirectory.addStyleName("okm-MainMenuItem");
				addDocument = new MenuItem(Util.menuHTML("img/icon/actions/add_document.gif", Main.i18n("general.menu.file.add.document")), true, addDocumentOKM);
				addDocument.addStyleName("okm-MainMenuItem");
				download = new MenuItem(Util.menuHTML("img/icon/actions/download.gif", Main.i18n("general.menu.file.download.document")), true, downloadOKM);
				download.addStyleName("okm-MainMenuItem");
				downloadPdf = new MenuItem(Util.menuHTML("img/icon/actions/download_pdf.gif", Main.i18n("general.menu.file.download.document.pdf")), true, downloadPdfOKM);
				downloadPdf.addStyleName("okm-MainMenuItem");
				sendDocumentLink = new MenuItem(Util.menuHTML("img/icon/actions/send_document_link.gif", Main.i18n("general.menu.file.send.link")), true, sendDocumentLinkOKM);
				sendDocumentLink.addStyleName("okm-MainMenuItem");
				export = new MenuItem(Util.menuHTML("img/icon/actions/export.gif", Main.i18n("filebrowser.menu.export")), true, exportToFile);
				export.addStyleName("okm-MainMenuItem");
				horizontalLineFile1 = new MenuItem("", true, nullExecute);
				horizontalLineFile1.setStyleName("okm-MainMenuItem");
				horizontalLineFile1.addStyleName("okm-MainMenuItem-Base-HorizontalSeparator");
				horizontalLineFile1.setHeight("2");
				purgeTrash = new MenuItem(Util.menuHTML("img/icon/actions/purge_trash.gif", Main.i18n("general.menu.file.purge.trash")), true, purgeTrashOKM);
				purgeTrash.addStyleName("okm-MainMenuItem");
				horizontalLineFile2 = new MenuItem("", true, nullExecute);
				horizontalLineFile2.setStyleName("okm-MainMenuItem");
				horizontalLineFile2.addStyleName("okm-MainMenuItem-Base-HorizontalSeparator");
				horizontalLineFile2.setHeight("2");
				exit = new MenuItem(Util.menuHTML("img/icon/menu/exit.gif", Main.i18n("general.menu.file.exit")), true, exitOKM);
				exit.addStyleName("okm-MainMenuItem");
			subMenuFile = new MenuBar(true);
			subMenuFile.setStyleName("okm-SubMenuBar");
			subMenuFile.setAutoOpen(true);
			subMenuFile.addItem(createDirectory);
			subMenuFile.addItem(addDocument);
			subMenuFile.addItem(download);
			subMenuFile.addItem(downloadPdf);
			subMenuFile.addItem(sendDocumentLink);
			subMenuFile.addItem(export);
			subMenuFile.addItem(horizontalLineFile1);
			subMenuFile.addItem(purgeTrash);
			subMenuFile.addItem(horizontalLineFile2);
			subMenuFile.addItem(exit);
		menuFile = new MenuItem(Main.i18n("general.menu.file"), subMenuFile);
		menuFile.addStyleName("okm-MainMenuBar");
		
		// Edit menu
		// First we must create menus and submenus on inverse order
				lock = new MenuItem(Util.menuHTML("img/icon/actions/lock.gif", Main.i18n("general.menu.file.lock")), true, lockOKM);
				lock.addStyleName("okm-MainMenuItem");
				unlock = new MenuItem(Util.menuHTML("img/icon/actions/unlock.gif", Main.i18n("general.menu.file.unlock")), true, unlockOKM);
				unlock.addStyleName("okm-MainMenuItem");
				checkout = new MenuItem(Util.menuHTML("img/icon/actions/checkout.gif", Main.i18n("general.menu.file.checkout")), true, checkoutOKM);
				checkout.addStyleName("okm-MainMenuItem");
				checkin = new MenuItem(Util.menuHTML("img/icon/actions/checkin.gif", Main.i18n("general.menu.file.checkin")), true, checkinOKM);
				checkin.addStyleName("okm-MainMenuItem");
				cancelCheckout = new MenuItem(Util.menuHTML("img/icon/actions/cancel_checkout.gif", Main.i18n("general.menu.file.cancel.checkout")), true, cancelCheckoutOKM);
				cancelCheckout.addStyleName("okm-MainMenuItem");
				delete = new MenuItem(Util.menuHTML("img/icon/actions/delete.gif", Main.i18n("general.menu.file.delete")), true, deleteOKM);
				delete.addStyleName("okm-MainMenuItem");
				copy = new MenuItem(Util.menuHTML("img/icon/actions/copy.gif", Main.i18n("filebrowser.menu.copy")), true, copyOKM);
				copy.addStyleName("okm-MenuItem-strike");
				move = new MenuItem(Util.menuHTML("img/icon/actions/move_document.gif", Main.i18n("filebrowser.menu.move")), true, moveOKM);
				move.addStyleName("okm-MenuItem-strike");
				rename = new MenuItem(Util.menuHTML("img/icon/actions/rename.gif", Main.i18n("filebrowser.menu.rename")), true, renameOKM);
				rename.addStyleName("okm-MenuItem-strike");
			// Submenu edit
			subMenuEdit = new MenuBar(true);
			subMenuEdit.setStyleName("okm-SubMenuBar");
			subMenuEdit.setAutoOpen(true);
			subMenuEdit.addItem(lock);
			subMenuEdit.addItem(unlock);
			subMenuEdit.addItem(checkout);
			subMenuEdit.addItem(checkin);
			subMenuEdit.addItem(cancelCheckout);
			subMenuEdit.addItem(delete);
			subMenuEdit.addItem(copy);
			subMenuEdit.addItem(move);
			subMenuEdit.addItem(rename);
		
		// Menu edit
		menuEdit = new MenuItem(Main.i18n("general.menu.edit"), subMenuEdit);
		menuEdit.addStyleName("okm-MainMenuBar");
		
		// Tools menu
		// First we must create menus and submenus on inverse order 
					// Submenu Language options
					subMenuLanguage = new MenuBar(true);
					subMenuLanguage.setStyleName("okm-SubMenuBar");
						arabic = new MenuItem(Util.menuHTML("img/icon/menu/ps.gif", "Arabic"), true, setLangArabic);
						bosnian = new MenuItem(Util.menuHTML("img/icon/menu/bs.gif", "Bosnian"), true, setLangBosnian);
						catalan = new MenuItem(Util.menuHTML("img/icon/menu/ca.gif", "Català"), true, setLangCatalan);
						chineseSimple = new MenuItem(Util.menuHTML("img/icon/menu/cn.gif", "Chinese simple"), true, setLangChinese);
						chineseTraditional = new MenuItem(Util.menuHTML("img/icon/menu/cn.gif", "Chinese traditional"), true, setLangChineseTraditional);
						czech = new MenuItem(Util.menuHTML("img/icon/menu/cs.gif", "Czech"), true, setLangCzech);
						german = new MenuItem(Util.menuHTML("img/icon/menu/de.gif", "Deutsch"), true, setLangDeutsch);
						english = new MenuItem(Util.menuHTML("img/icon/menu/en.gif", "English"), true, setLangEnglish);
						usa = new MenuItem(Util.menuHTML("img/icon/menu/us.gif", "English - Usa"), true, setLangEnglishUSA);
						spanish = new MenuItem(Util.menuHTML("img/icon/menu/es.gif", "Español"), true, setLangSpanish);
						colombian = new MenuItem(Util.menuHTML("img/icon/menu/co.gif", "Español Colombia"), true, setLangColombian);
						farsi = new MenuItem(Util.menuHTML("img/icon/menu/ir.gif", "Farsi"), true, setLangFarsi);
						french = new MenuItem(Util.menuHTML("img/icon/menu/fr.gif", "Français"), true, setLangFrench);
						gallego = new MenuItem(Util.menuHTML("img/icon/menu/gl.gif", "Gallego"), true, setLangGallego);
						greece = new MenuItem(Util.menuHTML("img/icon/menu/gr.gif", "Greece"), true, setLangGreece);
						hungarian  = new MenuItem(Util.menuHTML("img/icon/menu/hu.gif", "Hungarian"), true, setHungarian);
						indonesian = new MenuItem(Util.menuHTML("img/icon/menu/id.gif", "Indonesian"), true, setLangIndoneisan);
						italian = new MenuItem(Util.menuHTML("img/icon/menu/it.gif", "Italian"), true, setLangItalian);
						japanese = new MenuItem(Util.menuHTML("img/icon/menu/jp.gif", "Japanese"), true, setLangJapanese);
						latvian = new MenuItem(Util.menuHTML("img/icon/menu/lv.gif", "Latvian"), true, setLangLativian);
						macedonian  = new MenuItem(Util.menuHTML("img/icon/menu/mk.gif", "Macedonian"), true, setLangMacedonian);
						dutch = new MenuItem(Util.menuHTML("img/icon/menu/nl.gif", "Nederlands"), true, setLangNlBe);
						polish = new MenuItem(Util.menuHTML("img/icon/menu/pl.gif", "Polish"), true, setLangPolish);
						portuguese = new MenuItem(Util.menuHTML("img/icon/menu/pt.gif", "Português"), true, setLangPortuguese);
						brazilian = new MenuItem(Util.menuHTML("img/icon/menu/br.gif", "Português do Brasil"), true, setLangPtBr);
						romanian = new MenuItem(Util.menuHTML("img/icon/menu/ro.gif", "Romanian"), true, setRomanian);
						russian = new MenuItem(Util.menuHTML("img/icon/menu/ru.gif", "Russian"), true, setRussian);
						serbian = new MenuItem(Util.menuHTML("img/icon/menu/rs.gif", "Serbian"), true, setLangSerbian);
						swedish = new MenuItem(Util.menuHTML("img/icon/menu/se.gif", "Swedish"), true, setLangSwedish);
						turkish = new MenuItem(Util.menuHTML("img/icon/menu/tr.gif", "Turkish"), true, setLangTurkish);
						arabic.addStyleName("okm-MainMenuItem");
						bosnian.addStyleName("okm-MainMenuItem");
						catalan.addStyleName("okm-MainMenuItem");
						chineseSimple.addStyleName("okm-MainMenuItem");
						chineseTraditional.addStyleName("okm-MainMenuItem");
						czech.addStyleName("okm-MainMenuItem");
						german.addStyleName("okm-MainMenuItem");
						english.addStyleName("okm-MainMenuItem");
						usa.addStyleName("okm-MainMenuItem");
						spanish.addStyleName("okm-MainMenuItem");
						colombian.addStyleName("okm-MainMenuItem");
						farsi.addStyleName("okm-MainMenuItem");
						french.addStyleName("okm-MainMenuItem");
						gallego.addStyleName("okm-MainMenuItem");
						greece.addStyleName("okm-MainMenuItem");
						hungarian.addStyleName("okm-MainMenuItem");
						indonesian.addStyleName("okm-MainMenuItem");
						italian.addStyleName("okm-MainMenuItem");
						japanese.addStyleName("okm-MainMenuItem");
						latvian.addStyleName("okm-MainMenuItem");
						macedonian.addStyleName("okm-MainMenuItem");
						dutch.addStyleName("okm-MainMenuItem");
						polish.addStyleName("okm-MainMenuItem");
						portuguese.addStyleName("okm-MainMenuItem");
						brazilian.addStyleName("okm-MainMenuItem");
						romanian.addStyleName("okm-MainMenuItem");
						russian.addStyleName("okm-MainMenuItem");
						serbian.addStyleName("okm-MainMenuItem");
						swedish.addStyleName("okm-MainMenuItem");
						turkish.addStyleName("okm-MainMenuItem");
					subMenuLanguage.addItem(arabic);
					subMenuLanguage.addItem(bosnian);
					subMenuLanguage.addItem(catalan);
					subMenuLanguage.addItem(chineseSimple);
					subMenuLanguage.addItem(chineseTraditional);
					subMenuLanguage.addItem(czech);
					subMenuLanguage.addItem(german);
					subMenuLanguage.addItem(english);
					subMenuLanguage.addItem(usa);
					subMenuLanguage.addItem(spanish);
					subMenuLanguage.addItem(colombian);
					subMenuLanguage.addItem(farsi);
					subMenuLanguage.addItem(french);
					subMenuLanguage.addItem(gallego);
					subMenuLanguage.addItem(greece);
					subMenuLanguage.addItem(hungarian);
					subMenuLanguage.addItem(indonesian);
					subMenuLanguage.addItem(italian);
					subMenuLanguage.addItem(japanese);
					subMenuLanguage.addItem(latvian);
					subMenuLanguage.addItem(macedonian);
					subMenuLanguage.addItem(dutch);
					subMenuLanguage.addItem(polish);
					subMenuLanguage.addItem(portuguese);
					subMenuLanguage.addItem(brazilian);
					subMenuLanguage.addItem(romanian);
					subMenuLanguage.addItem(russian);
					subMenuLanguage.addItem(serbian);
					subMenuLanguage.addItem(swedish);
					subMenuLanguage.addItem(turkish);
				// Submenu language
				language = new MenuItem(Util.menuHTML("img/icon/menu/language.gif", Main.i18n("general.menu.tools.languages")), true, subMenuLanguage);
				language.addStyleName("okm-MainMenuItem");
				//language.addStyleName("okm-MainMenuItem-Base-Childs");
								
					// Submenu skin options
					subMenuSkin = new MenuBar(true);
					subMenuSkin.setStyleName("okm-SubMenuBar");
						skinDefault = new MenuItem(Util.menuHTML("img/icon/menu/skin_default.gif", Main.i18n("general.menu.tools.skin.default")), true, setSkinDefault);
						skinTest = new MenuItem(Util.menuHTML("img/icon/menu/skin_test.gif", Main.i18n("general.menu.tools.skin.default2")), true, setSkinDefault2);
						skinMediumFont = new MenuItem(Util.menuHTML("img/icon/menu/skin_test.gif", Main.i18n("general.menu.tools.skin.mediumfont")), true, setSkinMediumFont);
						skinBigFont = new MenuItem(Util.menuHTML("img/icon/menu/skin_test.gif", Main.i18n("general.menu.tools.skin.bigfont")), true, setSkinBigFont);
						skinDefault.addStyleName("okm-MainMenuItem");
						skinTest.addStyleName("okm-MainMenuItem");
						skinMediumFont.addStyleName("okm-MainMenuItem");
						skinBigFont.addStyleName("okm-MainMenuItem");
					subMenuSkin.addItem(skinDefault);
					subMenuSkin.addItem(skinTest);
					subMenuSkin.addItem(skinMediumFont);
					subMenuSkin.addItem(skinBigFont);
				
				// Submenu skin
				skin = new MenuItem(Util.menuHTML("img/icon/menu/skin.gif", Main.i18n("general.menu.tools.skin")), true, subMenuSkin);
				skin.addStyleName("okm-MainMenuItem");
				//skin.addStyleName("okm-MainMenuItem-Base-Childs");
				
				// Other tools options
				debugConsole = new MenuItem(Util.menuHTML("img/icon/menu/console.gif", Main.i18n("general.menu.debug.console")), true, setViewDebugConsole);
				debugConsole.addStyleName("okm-MainMenuItem");
				administration = new MenuItem(Util.menuHTML("img/icon/menu/administration.gif", Main.i18n("general.menu.administration")), true, showAdministration);
				administration.addStyleName("okm-MainMenuItem");
				administration.setVisible(false);
					
					// Submenu preferences opions
					subMenuPreferences = new MenuBar(true);
					subMenuPreferences.setStyleName("okm-SubMenuBar");
						userPreferences = new MenuItem(Util.menuHTML("img/icon/menu/skin_default.gif", Main.i18n("general.menu.tools.user.preferences")), true, setUserPreferences);
						userPreferences.addStyleName("okm-MainMenuItem");
					subMenuPreferences.addItem(userPreferences);
				
				// Submenu preferences
				preferences = new MenuItem(Util.menuHTML("img/icon/menu/skin.gif", Main.i18n("general.menu.tools.preferences")), true, subMenuPreferences);
				preferences.addStyleName("okm-MainMenuItem");
				
			// Submenu tools
			subMenuTools = new MenuBar(true);
			subMenuTools.setStyleName("okm-SubMenuBar");
			subMenuTools.setAutoOpen(true);
			subMenuTools.addItem(language);
			subMenuTools.addItem(skin);
			subMenuTools.addItem(debugConsole);
			subMenuTools.addItem(administration);
			subMenuTools.addItem(preferences);
			
		
		// Menu tools
		menuTools = new MenuItem(Main.i18n("general.menu.tools"), subMenuTools);
		menuTools.addStyleName("okm-MainMenuBar");
		
				home = new MenuItem(Util.menuHTML("img/icon/actions/bookmark_go.gif", Main.i18n("general.menu.bookmark.home")), true, goToUserHome);
				home.addStyleName("okm-MainMenuItem");
				defaultHome = new MenuItem(Util.menuHTML("img/icon/actions/bookmark.gif", Main.i18n("general.menu.bookmark.default.home")), true, setDefaultHome);
				defaultHome.addStyleName("okm-MainMenuItem");
				manageBookmark = new MenuItem(Util.menuHTML("img/icon/actions/bookmark_edit.gif", Main.i18n("general.menu.bookmark.edit")), true, editBookmark);
				manageBookmark.addStyleName("okm-MainMenuItem");
				horizontalLineBookmark1 = new MenuItem("", true, nullExecute);
				horizontalLineBookmark1.setStyleName("okm-MainMenuItem");
				horizontalLineBookmark1.addStyleName("okm-MainMenuItem-Base-HorizontalSeparator");
				horizontalLineBookmark1.setHeight("2");
				
			// Submenu tools
			subMenuBookmark = new MenuBar(true);
			subMenuBookmark.setStyleName("okm-SubMenuBar");
			subMenuBookmark.setAutoOpen(true);
			subMenuBookmark.addItem(home);
			subMenuBookmark.addItem(defaultHome);
			subMenuBookmark.addItem(manageBookmark);
			subMenuBookmark.addItem(horizontalLineBookmark1);
		
		// Menu bookmark
		menuBookmark = new MenuItem(Main.i18n("general.menu.bookmark"), subMenuBookmark);
		menuBookmark.addStyleName("okm-MainMenuBar");	
				
				// Submenu help option 
				help = new MenuItem(Util.menuHTML("img/icon/menu/help.gif", Util.windowOpen(Main.i18n("general.menu.help"), URI_HELP) ), true, nullExecute);
				help.addStyleName("okm-MainMenuItem");
				documentation = new MenuItem(Util.menuHTML("img/icon/menu/documentation.gif", Util.windowOpen(Main.i18n("general.menu.documentation"), URI_DOCUMENTATION)), true, nullExecute);
				documentation.addStyleName("okm-MainMenuItem");
				bugReport = new MenuItem(Util.menuHTML("img/icon/menu/bugs.gif", Util.windowOpen(Main.i18n("general.menu.bug.report") ,URI_BUG_REPORT) ), true, nullExecute);
				bugReport.addStyleName("okm-MainMenuItem");
				supportRequest = new MenuItem(Util.menuHTML("img/icon/menu/support.gif", Util.windowOpen(Main.i18n("general.menu.support.request"), URI_SUPPORT_REQUEST) ), true, nullExecute);
				supportRequest.addStyleName("okm-MainMenuItem");
				publicForum = new MenuItem(Util.menuHTML("img/icon/menu/forum.gif", Util.windowOpen(Main.i18n("general.menu.public.forum"), URI_PUBLIC_FORUM)), true, nullExecute);
				publicForum.addStyleName("okm-MainMenuItem");
				versionChanges = new MenuItem(Util.menuHTML("img/icon/menu/brick.gif", Util.windowOpen(Main.i18n("general.menu.version.changes"), URI_VERSION_CHANGES)), true, nullExecute);
				versionChanges.addStyleName("okm-MainMenuItem");
				projectWeb = new MenuItem(Util.menuHTML("img/icon/menu/home.gif", Util.windowOpen(Main.i18n("general.menu.project.web"), URI_PROJECT_WEB)), true, nullExecute);
				projectWeb.addStyleName("okm-MainMenuItem");
				about = new MenuItem(Util.menuHTML("img/icon/menu/about.gif", Main.i18n("general.menu.about")), true, aboutOKM);
				about.addStyleName("okm-MainMenuItem");
				
			// Submenu help
			subMenuHelp = new MenuBar(true);
			subMenuHelp.setStyleName("okm-SubMenuBar");
			subMenuHelp.setAutoOpen(true);
			//subMenuHelp.addItem(help);
			subMenuHelp.addItem(documentation);
			subMenuHelp.addItem(bugReport);
			subMenuHelp.addItem(supportRequest);
			subMenuHelp.addItem(publicForum);
			subMenuHelp.addItem(versionChanges);
			subMenuHelp.addItem(projectWeb);
			subMenuHelp.addItem(about);
		
		// Help menu
		menuHelp  = new MenuItem(Main.i18n("general.menu.help"), subMenuHelp);
		menuHelp.addStyleName("okm-MainMenuBar");
		
		// Create final general menu adding cascade menus to it
		MainMenu.addItem(menuFile);
		MainMenu.addItem(menuEdit);
		MainMenu.addItem(menuTools);
		MainMenu.addItem(menuBookmark);
		MainMenu.addItem(menuHelp);
		MainMenu.setAutoOpen(false);
		
		// By default hide menus
		menuEdit.setVisible(false);
		menuTools.setVisible(false);
		menuBookmark.setVisible(false);
		menuHelp.setVisible(false);
		
		initWidget(MainMenu);
	}
	
	// Lang refresh
	public void langRefresh() {
		bookmarkPopup.langRefresh(); // Refreshing popup
		manageBookmarkPopup.langRefresh(); // Refreshing management popup
		menuEdit.setText(Main.i18n("general.menu.edit"));
			createDirectory.setHTML(Util.menuHTML("img/icon/actions/add_folder.gif", Main.i18n("general.menu.file.create.directory")));
			download.setHTML(Util.menuHTML("img/icon/actions/download.gif", Main.i18n("general.menu.file.download.document")));
			downloadPdf.setHTML(Util.menuHTML("img/icon/actions/download_pdf.gif", Main.i18n("general.menu.file.download.document.pdf")));
			sendDocumentLink.setHTML(Util.menuHTML("img/icon/actions/send_document_link.gif", Main.i18n("general.menu.file.send.link")));
			export.setHTML(Util.menuHTML("img/icon/actions/export.gif", Main.i18n("filebrowser.menu.export")));
			lock.setHTML(Util.menuHTML("img/icon/actions/lock.gif", Main.i18n("general.menu.file.lock")));
			unlock.setHTML(Util.menuHTML("img/icon/actions/unlock.gif", Main.i18n("general.menu.file.unlock")));
			addDocument.setHTML(Util.menuHTML("img/icon/actions/add_document.gif", Main.i18n("general.menu.file.add.document")));
			checkout.setHTML(Util.menuHTML("img/icon/actions/checkout.gif", Main.i18n("general.menu.file.checkout")));
			checkin.setHTML(Util.menuHTML("img/icon/actions/checkin.gif", Main.i18n("general.menu.file.checkin")));
			cancelCheckout.setHTML(Util.menuHTML("img/icon/actions/cancel_checkout.gif", Main.i18n("general.menu.file.cancel.checkout")));
			delete.setHTML(Util.menuHTML("img/icon/actions/delete.gif", Main.i18n("general.menu.file.delete")));
			move.setHTML(Util.menuHTML("img/icon/actions/move_document.gif", Main.i18n("filebrowser.menu.move")));
			copy.setHTML(Util.menuHTML("img/icon/actions/copy.gif", Main.i18n("filebrowser.menu.copy")));
			rename.setHTML(Util.menuHTML("img/icon/actions/rename.gif", Main.i18n("filebrowser.menu.rename")));
		menuFile.setText(Main.i18n("general.menu.file"));
			purgeTrash.setHTML(Util.menuHTML("img/icon/actions/purge_trash.gif", Main.i18n("general.menu.file.purge.trash")));
			exit.setHTML(Util.menuHTML("img/icon/menu/exit.gif", Main.i18n("general.menu.file.exit")));
		menuTools.setText(Main.i18n("general.menu.tools"));
			language.setHTML(Util.menuHTML("img/icon/menu/language.gif", Main.i18n("general.menu.tools.languages")));
			skin.setHTML(Util.menuHTML("img/icon/menu/skin.gif", Main.i18n("general.menu.tools.skin")));
				skinDefault.setHTML(Util.menuHTML("img/icon/menu/skin_default.gif", Main.i18n("general.menu.tools.skin.default")));
				skinTest.setHTML(Util.menuHTML("img/icon/menu/skin_test.gif", Main.i18n("general.menu.tools.skin.default2")));
				skinMediumFont.setHTML(Util.menuHTML("img/icon/menu/skin_test.gif", Main.i18n("general.menu.tools.skin.mediumfont")));
				skinBigFont.setHTML(Util.menuHTML("img/icon/menu/skin_test.gif", Main.i18n("general.menu.tools.skin.bigfont")));	
				skinTest.setHTML(Util.menuHTML("img/icon/menu/skin_test.gif", Main.i18n("general.menu.tools.skin.default2")));	
			debugConsole.setHTML(Util.menuHTML("img/icon/menu/console.gif", Main.i18n("general.menu.debug.console")));
			administration.setHTML(Util.menuHTML("img/icon/menu/administration.gif", Main.i18n("general.menu.administration")));
			preferences.setHTML(Util.menuHTML("img/icon/menu/skin.gif", Main.i18n("general.menu.tools.preferences")));
				userPreferences.setHTML(Util.menuHTML("img/icon/menu/skin_default.gif", Main.i18n("general.menu.tools.user.preferences")));
		menuBookmark.setText(Main.i18n("general.menu.bookmark"));
			home.setHTML(Util.menuHTML("img/icon/actions/bookmark_go.gif", Main.i18n("general.menu.bookmark.home")));
			defaultHome.setHTML(Util.menuHTML("img/icon/actions/bookmark.gif", Main.i18n("general.menu.bookmark.default.home")));
			manageBookmark.setHTML(Util.menuHTML("img/icon/actions/bookmark_edit.gif", Main.i18n("general.menu.bookmark.edit")));
		menuHelp.setText(Main.i18n("general.menu.help"));
			help.setHTML(Util.menuHTML("img/icon/menu/help.gif", Util.windowOpen(Main.i18n("general.menu.help"), URI_HELP)));
			documentation.setHTML(Util.menuHTML("img/icon/menu/documentation.gif", Util.windowOpen(Main.i18n("general.menu.documentation"), URI_DOCUMENTATION)));
			bugReport.setHTML(Util.menuHTML("img/icon/menu/bugs.gif", Util.windowOpen(Main.i18n("general.menu.bug.report"), URI_BUG_REPORT)));
			supportRequest.setHTML(Util.menuHTML("img/icon/menu/support.gif", Util.windowOpen(Main.i18n("general.menu.support.request"), URI_SUPPORT_REQUEST)));
			publicForum.setHTML(Util.menuHTML("img/icon/menu/forum.gif", Util.windowOpen(Main.i18n("general.menu.public.forum"), URI_PUBLIC_FORUM)));
			versionChanges.setHTML(Util.menuHTML("img/icon/menu/brick.gif", Util.windowOpen(Main.i18n("general.menu.version.changes"), URI_VERSION_CHANGES)));
			projectWeb.setHTML(Util.menuHTML("img/icon/menu/home.gif", Util.windowOpen(Main.i18n("general.menu.project.web"), URI_PROJECT_WEB)));
			about.setHTML(Util.menuHTML("img/icon/menu/about.gif", Main.i18n("general.menu.about")));
	}
	
	/**
	 * Enables menu item
	 * 
	 * @param menuItem The menu item
	 */
	public void enable(MenuItem menuItem) {
		menuItem.removeStyleName("okm-MenuItem-strike");
	}
	
	/**
	 * Disables the menu item with and strike
	 * 
	 * @param menuItem The menu item
	 */
	public void disable(MenuItem menuItem) {
		menuItem.addStyleName("okm-MenuItem-strike");
	}
	
	/**
	 * Enables or disables menu option on privileges
	 */
	public void evaluateMenuOptions() {
		if (mainMenuOption.createFolderOption) {	enable(createDirectory); } else { disable(createDirectory);	}
		if (mainMenuOption.downloadOption) { enable(download); } else { disable(download); }
		if (mainMenuOption.downloadPdfOption) { enable(downloadPdf); } else { disable(downloadPdf); }
		if (mainMenuOption.sendDocumentLinkOption) { enable(sendDocumentLink); } else { disable(sendDocumentLink); }
		if (mainMenuOption.exportOption) { enable(export); } else { disable(export); }
		if (mainMenuOption.lockOption) { enable(lock); } else {	disable(lock); }
		if (mainMenuOption.unLockOption) { enable(unlock); } else { disable(unlock); }
		if (mainMenuOption.addDocumentOption) { enable(addDocument); } else { disable(addDocument); }
		if (mainMenuOption.checkoutOption) { enable(checkout); } else { disable(checkout); }
		if (mainMenuOption.checkinOption) { enable(checkin); } else { disable(checkin); }
		if (mainMenuOption.cancelCheckoutOption) { enable(cancelCheckout); } else { disable(cancelCheckout); }
		if (mainMenuOption.deleteOption) { enable(delete); } else { disable(delete); }
		if (mainMenuOption.copyOption) { enable(copy); } else { disable(copy); }
		if (mainMenuOption.renameOption) { enable(rename); } else { disable(rename); }
		if (mainMenuOption.moveOption) { enable(move); } else { disable(move); }
		if (mainMenuOption.homeOption) {
			enable(home);
			enable(defaultHome);
			bookmark.enableBookmarks();
		} else {
			disable(home);
			disable(defaultHome);
			bookmark.disableBookmarks();
		}
	}
	
	/**
	 * Sets the main menu options
	 * 
	 * @param mainMenuOption The manin Menu options
	 */
	public void setOptions(ToolBarOption mainMenuOption){
		this.mainMenuOption = mainMenuOption;
		evaluateMenuOptions();
	}
	
	// Command menu to set Spanish lang
	Command setLangSpanish = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_es_ES);
		}
	};
	
	// Command menu to set Arabic lang
	Command setLangArabic = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_ar_PS);
		}
	};
	
	// Command menu to set Bosnian lang
	Command setLangBosnian = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_bs_BA);
		}
	};
	
	// Command menu to set Catalan lang
	Command setLangCatalan = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_ca_ES);
		}
	};
	
	// Command menu to set Chinese simple lang
	Command setLangChinese = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_zh_CN);
		}
	};
	
	// Command menu to set Chinese traditional lang
	Command setLangChineseTraditional = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_zh_TW);
		}
	};
	
	// Command menu to set Czech traditional lang
	Command setLangCzech = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_cs_CZ);
		}
	};
	
	// Command menu to set Colombian lang
	Command setLangColombian = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_co_ES);
		}
	};
	
	// Command menu to set German lang
	Command setLangDeutsch = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_de_DE);
		}
	};
	
	// Command menu to set English lang
	Command setLangEnglish = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_en_GB);
		}
	};
	
	// Command menu to set English USA lang
	Command setLangEnglishUSA = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_en_US);
		}
	};
	
	// Command menu to set Farsi lang
	Command setLangFarsi = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_fa_FA);
		}
	};

	// Command menu to set French lang
	Command setLangFrench = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_fr_FR);
		}
	};
	
	// Command menu to set French lang
	Command setLangGallego = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_gl_ES);
		}
	};
	
	// Command menu to set Greece lang
	Command setLangGreece = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_el_GR);
		}
	};
	
	// Command menu to set Hungarian lang
	Command setHungarian = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_hu_HU);
		}
	};
	
	// Command menu to set Indonesian lang
	Command setLangIndoneisan = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_id_ID);
		}
	};
	
	// Command menu to set Italian lang
	Command setLangItalian = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_it_IT);
		}
	};
	
	// Command menu to set French lang
	Command setLangJapanese = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_ja_JP);
		}
	};
	
	// Command menu to set Latvian lang
	Command setLangLativian = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_lv_LV);
		}
	};
	
	// Command menu to set Macedoniam lang
	Command setLangMacedonian = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_mk_MK);
		}
	};
	
	
	// Command menu to set Portuguese lang
	Command setLangPortuguese = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_pt_PT);
		}
	};

	// Command menu to set Portuguese/Brazilian lang
	Command setLangPtBr = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_pt_BR);
		}
	};
	
	// Command menu to set Romanian lang
	Command setRomanian = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_ro_RO);
		}
	};
	
	// Command menu to set Russian lang
	Command setRussian = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_ru_RU);
		}
	};
	
	// Command menu to set Servian
	Command setLangSerbian = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_sr_BA);
		}
	};
	
	// Command menu to set Swedish
	Command setLangSwedish = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_sv_SE);
		}
	};

	// Command menu to set Polish lang
	Command setLangPolish = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_pl_PL);
		}
	};
	
	// Command menu to set Dutch lang
	Command setLangNlBe = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_nl_NL);
		}
	};
	
	// Command menu to set Swedish
	Command setLangTurkish = new Command() {
		public void execute() {
			Main.get().refreshLang(Lang.LANG_tr_TR);
		}
	};
	
	// Command menu to purge trash 
	Command purgeTrashOKM = new Command() {
		public void execute() {
			Main.get().confirmPopup.setConfirm(ConfirmPopup.CONFIRM_EMPTY_TRASH);
			Main.get().confirmPopup.show();
		}
	};
	
	// Command menu to create directory 
	Command createDirectoryOKM = new Command() {
		public void execute() {
			if (mainMenuOption.createFolderOption) {
				Main.get().mainPanel.topPanel.toolBar.executeFolderDirectory();
			}
		}
	};
	
	// Command menu to download
	Command downloadOKM = new Command() {
		public void execute() {
			if (mainMenuOption.downloadOption) {
				Main.get().mainPanel.topPanel.toolBar.executeDownload();
			}
		}
	};

	// Command menu to download as PDF
	Command downloadPdfOKM = new Command() {
		public void execute() {
			if (mainMenuOption.downloadPdfOption) {
				Main.get().mainPanel.topPanel.toolBar.executeDownloadPdf();
			}
		}
	};

	// Command menu to send document link
	Command sendDocumentLinkOKM = new Command() {
		public void execute() {
			if (mainMenuOption.sendDocumentLinkOption) {
				Main.get().notifyPopup.executeSendDocumentLink();
			}
		}
	};
	
	
	// Command menu to send document link
	Command exportToFile = new Command() {
		public void execute() {
			if (mainMenuOption.exportOption) {
				Main.get().mainPanel.topPanel.toolBar.executeExport();
			}
		}
	};

	// Command menu to lock  
	Command lockOKM = new Command() {
		public void execute() {
			if (mainMenuOption.lockOption) {
				Main.get().mainPanel.topPanel.toolBar.executeLock();
			}
		}
	};
	
	// Command menu to unlock
	Command unlockOKM = new Command() {
		public void execute() {
			if (mainMenuOption.unLockOption) {
				Main.get().mainPanel.topPanel.toolBar.executeUnlock();
			}
		}
	};
	
	// Command menu to add documen
	Command addDocumentOKM = new Command() {
		public void execute() {
			if (mainMenuOption.addDocumentOption) {
				Main.get().mainPanel.topPanel.toolBar.executeAddDocument();
			}
		}
	};
	
	// Command menu to edit (checkout)
	Command checkoutOKM = new Command() {
		public void execute() {
			if (mainMenuOption.checkoutOption) {
				Main.get().mainPanel.topPanel.toolBar.executeCheckout();
			}
		}
	};
	
	// Command menu to checkin
	Command checkinOKM = new Command() {
		public void execute() {
			if (mainMenuOption.checkinOption) {
				Main.get().mainPanel.topPanel.toolBar.exectuteCheckin();
			}
		}
	};
	
	// Command menu to cancel checkout
	Command cancelCheckoutOKM = new Command() {
		public void execute() {
			if (mainMenuOption.cancelCheckoutOption) {
				Main.get().mainPanel.topPanel.toolBar.executeCancelCheckout();
			}
		}
	};
	
	// Command menu to copy
	Command copyOKM = new Command() {
		public void execute() {
			if (mainMenuOption.copyOption) {
				Main.get().mainPanel.topPanel.toolBar.executeCopy();
			}
		}
	};
	
	// Command menu to copy
	Command moveOKM = new Command() {
		public void execute() {
			Main.get().mainPanel.topPanel.toolBar.executeMove();
		}
	};
	
	// Command menu to copy
	Command renameOKM = new Command() {
		public void execute() {
			if (mainMenuOption.renameOption) {
				Main.get().mainPanel.topPanel.toolBar.executeRename();
			}
		}
	};
	
	// Command menu to delete 
	Command deleteOKM = new Command() {
		public void execute() {
			if (mainMenuOption.deleteOption) {
				Main.get().mainPanel.topPanel.toolBar.executeDelete();
			}
		}
	};
	
	// Command menu to delete 
	Command refreshOKM = new Command() {
		public void execute() {
			if (mainMenuOption.refreshOption) {
				Main.get().mainPanel.topPanel.toolBar.executeRefresh();
			}
		}
	};
	
	// Command menu to exit application
	Command exitOKM = new Command() {
		public void execute() {
			Main.get().logoutPopup.logout();
		}
	};
	
	// Command menu to show about
	Command aboutOKM = new Command() {
		public void execute() {
			Main.get().aboutPopup.show();
		}
	};
	
	// Command menu to set default skin
	Command setSkinDefault = new Command() {
		public void execute() {
			changeCss("default");
		}
	};
	
	// Command menu to set test skin
	Command setSkinDefault2 = new Command() {
		public void execute() {
			changeCss("test");
		}
	};
	
	// Command menu to set test skin
	Command setSkinMediumFont = new Command() {
		public void execute() {
			changeCss("mediumfont");
		}
	};
	
	// Command menu to set test skin
	Command setSkinBigFont = new Command() {
		public void execute() {
			changeCss("bigfont");
		}
	};
	
	// Command menu to show debug console
	Command setViewDebugConsole = new Command() {
		public void execute() {
			Main.get().debugConsolePopup.center();
		}
	};
	
	// Command menu to show administration
	Command showAdministration = new Command() {
		public void execute() {
			Window.open("/OpenKM"+Config.INSTALL+"/admin/index.jsp", "Administration", "");
		}
	};
	
	// Command menu to go to set user preferences
	Command setUserPreferences = new Command() {
		public void execute() {
			Main.get().userPopup.show();
		}
	};
	
	// Command menu to go to user home
	Command goToUserHome = new Command() {
		public void execute() {
			if (mainMenuOption.homeOption) {
				Main.get().mainPanel.topPanel.toolBar.executeGoToUserHome();
			}
		}
	};
	
	// Command menu to go to user home
	Command setDefaultHome = new Command() {
		public void execute() {
			if (mainMenuOption.homeOption) {
				if (Main.get().mainPanel.browser.fileBrowser.isPanelSelected()) {
					Main.get().mainPanel.browser.fileBrowser.setHome();
				} else if (Main.get().activeFolderTree.isPanelSelected()) {
					Main.get().activeFolderTree.setHome();
				}
			}
		}
	};
	
	// Command menu to go to user home
	Command editBookmark = new Command() {
		public void execute() {
			if (mainMenuOption.homeOption) {
				manageBookmarkPopup.showPopup();
			}
		}
	};
	
	
	
	// Command menu that executes void
	Command nullExecute = new Command() {
		public void execute() {
		}
	};
	
	/**
	 * Gets the tools bar options
	 * @return The tool bar options values
	 */
	public ToolBarOption getToolBarOption() {
		return mainMenuOption;
	}
	
	/**
	 * Change on fly the actual css
	 * 
	 * @param title The css name
	 */
	public static native void changeCss(String title) /*-{
		new $wnd.changeCss(title);
	}-*/;
	
	
	/**
	 * setAvailableOption
	 * 
	 * @param option
	 */
	public void setAvailableOption(GWTAvailableOption option) {
		// FILE MENU
		createDirectory.setVisible(option.isCreateFolderOption());
		addDocument.setVisible(option.isAddDocumentOption());
		download.setVisible(option.isDownloadOption());
		downloadPdf.setVisible(option.isDownloadPdfOption());
		sendDocumentLink.setVisible(option.isSendDocumentLinkOption());
		export.setVisible(option.isExportOption());
		horizontalLineFile1.setVisible(option.isCreateFolderOption() || option.isAddDocumentOption() || option.isDownloadOption() ||
									   option.isDownloadPdfOption() || option.isSendDocumentLinkOption() || option.isExportOption()); 
		purgeTrash.setVisible(option.isPurgeTrashOption());
		horizontalLineFile2.setVisible(option.isPurgeTrashOption());
		
		// EDIT MENU
		lock.setVisible(option.isLockOption());
		unlock.setVisible(option.isUnLockOption());
		checkout.setVisible(option.isCheckoutOption());
		checkin.setVisible(option.isCheckinOption());
		cancelCheckout.setVisible(option.isCancelCheckoutOption());
		delete.setVisible(option.isDeleteOption());
		copy.setVisible(option.isCopyOption());
		move.setVisible(option.isMoveOption());
		rename.setVisible(option.isRenameOption());
		
		// MENU TOOLS
		skin.setVisible(option.isSkinOption());
		debugConsole.setVisible(option.isDebugOption());
		administration.setVisible(option.isAdministrationOption());
		
		// MENU BOOKMARKS
		home.setVisible(option.isHomeOption());
		defaultHome.setVisible(option.isAddBookmarkOption());
		manageBookmark.setVisible(option.isManageBookmarkOption());
		horizontalLineBookmark1.setVisible(option.isHomeOption() || option.isAddBookmarkOption() || option.isAddBookmarkOption());
		
		// MENU HELP
		help.setVisible(option.isHelpOption());
		documentation.setVisible(option.isDocumentationOption());
		bugReport.setVisible(option.isBugReportOption());
		supportRequest.setVisible(option.isSupportRequestOption());
		publicForum.setVisible(option.isPublicForumOption());
		versionChanges.setVisible(option.isVersionChangesOption());
		projectWeb.setVisible(option.isProjectWebOption());
		about.setVisible(option.isAboutOption());
	}
	
	/**
	 * setEditMenuVisible
	 * 
	 * @param visible
	 */
	public void setEditMenuVisible(boolean visible) {
		menuEdit.setVisible(visible);
	}
	
	/**
	 * setToolsMenuVisible
	 * 
	 * @param visible
	 */
	public void setToolsMenuVisible(boolean visible) {
		menuTools.setVisible(visible);
	}
	
	/**
	 * setBookmarkMenuVisible
	 * 
	 * @param visible
	 */
	public void setBookmarkMenuVisible(boolean visible) {
		menuBookmark.setVisible(visible);
	}
	
	/**
	 * setHelpMenuVisible
	 * 
	 * @param visible
	 */
	public void setHelpMenuVisible(boolean visible) {
		menuHelp.setVisible(visible);
	}
	
	/**
	 * addMenu
	 * 
	 * @param extension
	 */
	public void addMenu(MenuItemExtension extension) {
		MainMenu.addItem(extension);
	}
}
