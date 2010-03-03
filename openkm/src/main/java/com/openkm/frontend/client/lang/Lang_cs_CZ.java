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

package com.openkm.frontend.client.lang;

import java.util.HashMap;

import com.openkm.frontend.client.config.ErrorCode;

/**
 * Czech (Czech Republic)
 * 
 * @author Petr Suchy
 */
public class Lang_cs_CZ {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd-MM-yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd-MM-yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Nahrává se OpenKM");
	    lang.put("startup.starting.loading", "Začíná se nahrávat OpenKM");
	    lang.put("startup.taxonomy", "Získává se kořenový uzel stromu");
	    lang.put("startup.categories", "Getting categories root node");
	    lang.put("startup.thesaurus", "Getting thesaurus root node");
	    lang.put("startup.template", "Získává se kořenový uzel šablon");
	    lang.put("startup.personal", "Získává se kořenový uzel personal");
	    lang.put("startup.mail", "Získává se kořenový uzel e-mailu");
	    lang.put("startup.trash", "Získává se kořenový uzel koše");
	    lang.put("startup.user.home", "Získává se kořenový uzel domovského adresáře");
	    lang.put("startup.bookmarks", "Získává se kořenový uzel záložek");
	    lang.put("startup.loading.taxonomy", "Nahrává se strom");
	    lang.put("startup.loading.taxonomy.getting.folders", "Nahrává se strom - získávají se složky");
	    lang.put("startup.loading.taxonomy.eval.params", "Nahrává se strom - přepočítavají se parametry prohlížeče");
	    lang.put("startup.loading.taxonomy.open.path", "Nahrává se strom - otevřená cesta");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Nahrává se strom - získávají se složky");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Nahrává se strom - získávají se dokumenty");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Nahrává se strom - získávají se maily");
	    lang.put("startup.loading.personal", "Nahrává se personal");
	    lang.put("startup.loading.mail", "Nahrávají se e-maily");
	    lang.put("startup.loading.categories", "Loading categories");
	    lang.put("startup.loading.thesaurus", "Loading thesaurus");
	    lang.put("startup.loading.templates", "Nahrávají se šablony");
	    lang.put("startup.loading.trash", "Nahrává se koš");
	    lang.put("startup.loading.history.search", "Nahrává se hledání v historii");
	    lang.put("startup.loading.user.values", "Nahrává se uživatelské hodnoty");
	    lang.put("startup.loading.property.group.translations", "Nahrává se skupina vlastností překlady");
	    lang.put("startup.keep.alive", "Nahrává se obnova připojení");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Je dostupná aktualizace OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Strom");
	    lang.put("leftpanel.label.trash", "Koš");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Uložená hledání");
	    lang.put("leftpanel.label.categories", "Categories");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Šablony");
	    lang.put("leftpanel.label.my.documents", "Moje dokumenty");
	    lang.put("leftpanel.label.user.search", "Uživatelské zprávy");
	    lang.put("leftpanel.label.all.repository", "Celé úložiště");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Vytvoř složku");
	    lang.put("tree.menu.directory.remove", "Smazat");
	    lang.put("tree.menu.directory.rename", "Přejmenovat");
	    lang.put("tree.menu.directory.refresh", "Obnovit");
	    lang.put("tree.menu.directory.move", "Přesunout");
	    lang.put("tree.menu.directory.copy", "Kopírovat");
	    lang.put("tree.menu.directory.add.document", "Přidat dokument");
	    lang.put("tree.menu.add.bookmark", "Přidat záložku");
	    lang.put("tree.menu.set.home", "Nastavit domovskou složku");
	    lang.put("tree.menu.export", "Exportovat do souboru");
	    lang.put("tree.status.refresh.folder", "Aktualizuje se strom složek");
	    lang.put("tree.status.refresh.create", "Vytváří se složka");
	    lang.put("tree.status.refresh.delete", "Maže se složka");
	    lang.put("tree.status.refresh.rename", "Přejmenovává se složka");
	    lang.put("tree.status.refresh.restore", "Obnovuje se složka");
	    lang.put("tree.status.refresh.purge", "Čistí se složka");
	    lang.put("tree.status.refresh.get", "Aktualizuje se složka");
	    lang.put("tree.folder.new", "Nová složka");
	    lang.put("tree.status.refresh.add.subscription", "Přidává se upozornění o změnách mailem");
	    lang.put("tree.status.refresh.remove.subscription", "Odebírá se upozornění o změnách mailem");
	    lang.put("tree.status.refresh.get.root", "Obnovuje se kořenová složka");
	    lang.put("tree.status.refresh.get.keywords", "Refreshing keywords");
	    lang.put("tree.status.refresh.get.user.home", "Získává se domovský adresář");
	    lang.put("tree.status.refresh.purge.trash", "Vyprazdňuje se koš");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Obnovit");
	    lang.put("trash.menu.directory.purge", "Úplně smazat");
	    lang.put("trash.menu.directory.purge.trash", "Vyprázdnit koš");
	    lang.put("trash.directory.select.label", "Zvolte cílovou složku");
	    
	    // General menu
	    lang.put("general.menu.file", "Soubor");
	    	lang.put("general.menu.file.exit", "Konec");
	    	lang.put("general.menu.file.purge.trash", "Vyprázdnit koš");
	    lang.put("general.menu.edit", "Úpravy");
			lang.put("general.menu.file.create.directory", "Vytvořit složku");
			lang.put("general.menu.file.download.document", "Stáhnout dokument");
			lang.put("general.menu.file.download.document.pdf", "Stáhnout dokument jako PDF");
			lang.put("general.menu.file.send.link", "Poslat odkaz na soubor");
			lang.put("general.menu.file.lock", "Uzamknout");
			lang.put("general.menu.file.unlock", "Odemknout");
			lang.put("general.menu.file.add.document", "Přidat dokument");
			lang.put("general.menu.file.checkout", "Check out");
			lang.put("general.menu.file.checkin", "Check in");
			lang.put("general.menu.file.cancel.checkout", "Zrušit check out");
			lang.put("general.menu.file.delete", "Odstranit");
			lang.put("general.menu.file.refresh", "Obnovit");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "File uploader");
	    lang.put("general.menu.tools", "Nástroje");
	    	lang.put("general.menu.tools.languages", "Jazyky");
	    	lang.put("general.menu.tools.skin", "Šablona vzhledu");
    			lang.put("general.menu.tools.skin.default", "Implicitní");
    			lang.put("general.menu.tools.skin.default2", "Implicitní 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Střední velikost písma");
    			lang.put("general.menu.tools.skin.bigfont", "Velká velikost písma");
    		lang.put("general.menu.debug.console", "Konzole pro ladění");
    		lang.put("general.menu.administration", "Zobrazení administrace");
    		lang.put("general.menu.tools.preferences", "Předvolby");
    			lang.put("general.menu.tools.user.preferences", "Uživatelská nastavení");
    	lang.put("general.menu.bookmark", "Záložky");
	    	lang.put("general.menu.bookmark.home", "Domovská složka");
	    	lang.put("general.menu.bookmark.default.home", "Nastavit domovskou složku");
	    	lang.put("general.menu.bookmark.edit", "Upravit záložky");
	    lang.put("general.menu.help", "Nápověda");
		    lang.put("general.menu.bug.report", "Nahlásit chybu");
		    lang.put("general.menu.support.request", "Žádost o pomoc");
		    lang.put("general.menu.public.forum", "Veřejné fórum");
		    lang.put("general.menu.project.web", "Stránka projektu");
		    lang.put("general.menu.version.changes", "Poznámky k verzi");
		    lang.put("general.menu.documentation", "Dokumentace");
		    lang.put("general.menu.about", "O OpenKM");
	    lang.put("general.connected", "Připojen jako ");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Jméno");
	    lang.put("filebrowser.date.update", "Datum úpravy");
	    lang.put("filebrowser.size", "Velikost");
	    lang.put("filebrowser.path", "Cesta");
	    lang.put("filebrowser.author", "Autor");
	    lang.put("filebrowser.version", "Verze");
	    lang.put("filebrowser.menu.checkout", "Check out");
	    lang.put("filebrowser.menu.checkin", "Check in");
	    lang.put("filebrowser.menu.delete", "Smazat");
	    lang.put("filebrowser.menu.rename", "Přejmenovat");
	    lang.put("filebrowser.menu.checkout.cancel", "Check out cancel");
	    lang.put("filebrowser.menu.lock", "Uzamknout");
	    lang.put("filebrowser.menu.unlock", "Odemknout");
	    lang.put("filebrowser.menu.download", "Stáhnout");
	    lang.put("filebrowser.menu.move", "Přesunout");
	    lang.put("filebrowser.menu.copy", "Kopírovat");
	    lang.put("filebrowser.menu.create.from.template", "Vytvořit ze šablony");
	    lang.put("filebrowser.menu.add.property.group", "Přidat skupinu vlastností");
	    lang.put("filebrowser.menu.remove.property.group", "Odebrat skupinu vlastností");
	    lang.put("filebrowser.menu.start.workflow", "Začít průběh zpracování dokumentu");
	    lang.put("filebrowser.menu.add.subscription", "Přidat upozornění o změnách mailem");
	    lang.put("filebrowser.menu.remove.subscription", "Odebrat upozornění o změnách mailem");
	    lang.put("filebrowser.menu.add.bookmark", "Přidat záložku");
	    lang.put("filebrowser.menu.set.home", "Nastavit domovskou složku");
	    lang.put("filebrowser.menu.refresh", "Aktualizovat");
	    lang.put("filebrowser.menu.export", "Exportovat do ZIP");
	    lang.put("filebrowser.menu.play", "Přehrát");
	    lang.put("filebrowser.menu.image.viewer", "Prohlížeč obrázků");
	    lang.put("filebrowser.status.refresh.folder", "Aktualizuje se seznam složek");
	    lang.put("filebrowser.status.refresh.document", "Aktualizuje se seznam dokumentů");
	    lang.put("filebrowser.status.refresh.mail", "Aktualizuje se seznam mailů");
	    lang.put("filebrowser.status.refresh.delete.folder", "Maže se složka");
	    lang.put("filebrowser.status.refresh.delete.document", "Maže se dokument");
	    lang.put("filebrowser.status.refresh.checkout", "Checking out dokument");
	    lang.put("filebrowser.status.refresh.lock", "Zamyká se dokument");
	    lang.put("filebrowser.status.refresh.unlock", "Odemyká se dokument");
	    lang.put("filebrowser.status.refresh.document.rename", "Přejmenovává se document");
	    lang.put("filebrowser.status.refresh.folder.rename", "Přejmenovává se složka");
	    lang.put("filebrowser.status.refresh.document.purge", "Maže se dokument");
	    lang.put("filebrowser.status.refresh.folder.purge", "Maže se složka");
	    lang.put("filebrowser.status.refresh.folder.get", "Aktualizuje se složka");
	    lang.put("filebrowser.status.refresh.document.get", "Aktualizuje se dokument");
	    lang.put("filebrowser.status.refresh.add.subscription", "Přidává se upozornění o změnách mailem");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Odebírá se se upozornění o změnách mailem");
	    lang.put("filebrowser.status.refresh.get.user.home", "Získává se domovská složka");
	    lang.put("filebrowser.status.refresh.delete.mail", "Maže se mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Maže se mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Odeslat");
	    lang.put("fileupload.status.sending", "Nahrává se soubor...");
	    lang.put("fileupload.status.indexing", "Indexuje se soubor...");
	    lang.put("fileupload.status.ok", "Soubro byl úspěšně nahrán");
	    lang.put("fileupload.upload.status", "Výsledek nahrání");
	    lang.put("fileupload.upload.uploaded", "Nahrán");
	    lang.put("fileupload.upload.completed", "Nahrávání dokončeno");
	    lang.put("fileupload.upload.runtime", "Runtime");
	    lang.put("fileupload.upload.remaining", "Zbývá");
	    lang.put("fileupload.button.close", "Zavřít");
	    lang.put("fileupload.button.add.other.file", "Přidat další soubor");
	    lang.put("fileupload.status.move.file", "Přesouvá se soubor...");
	    lang.put("fileupload.status.move.mail", "Přesouvá se mail...");
	    lang.put("fileupload.status.copy.file", "Kopíruje se soubor...");
	    lang.put("fileupload.status.copy.mail", "Kopíruje se mail...");
	    lang.put("fileupload.status.restore.file", "Obnovuje se soubor...");
	    lang.put("fileupload.status.restore.mail", "Obnovuje se soubormail...");
	    lang.put("fileupload.status.move.folder", "Přesouvá se složka...");
	    lang.put("fileupload.status.copy.folder", "Kopíruje se složka...");
	    lang.put("fileupload.status.restore.folder", "Obnovuje se složka...");
	    lang.put("fileupload.status.create.from.template", "Vytváří se soubor ze šablony...");
	    lang.put("fileupload.status.of", "z");
	    lang.put("fileupload.label.insert", "Přidat nové dokumenty");
	    lang.put("fileupload.label.update", "Aktualizovat dokumenty");
	    lang.put("fileupload.label.users.notify", "Upozornit uživatele");
	    lang.put("fileupload.label.comment", "Poznámka");
	    lang.put("fileupload.label.users.to.notify",  "Uživatelé kteří budou upozorněni");
	    lang.put("fileupload.label.users",  "Uživatelé");
	    lang.put("fileupload.label.must.select.users",  "Musíte zvolit uživatele, který má být upozorněn");
	    lang.put("fileupload.label.importZip", "Nahrát dokumenty ze ZIP souboru");
	    lang.put("fileupload.label.notify.comment", "Zpráva o změně");
	    lang.put("fileupload.label.error.importing.zip", "Chyba při nahrávání souboru");
	    lang.put("fileupload.label.error.move.file", "Chyba při přesouvání souboru");
	    lang.put("fileupload.label.error.move.mail", "Chyba při přesouvání mailu");
	    lang.put("fileupload.label.error.copy.file", "Chyba při kopírování souboru");
	    lang.put("fileupload.label.error.copy.mail", "Chyba při kopírování mailu");
	    lang.put("fileupload.label.error.restore.file", "Chyba při obnově souboru");
	    lang.put("fileupload.label.error.restore.mail", "Chyba při obnově mailu");
	    lang.put("fileupload.label.error.move.folder", "Chyba při přesouvání složky");
	    lang.put("fileupload.label.error.copy.folder", "Chyba při kopírování složky");
	    lang.put("fileupload.label.error.restore.folder", "Chyba při obnovování složky");
	    lang.put("fileupload.label.error.create.from.template", "Chyba při vytváření souboru ze šablony");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Není možné přesunout pod původní složku nebo podsložku");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Není možné přesunout pod původní složku");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Není možné vytvořit soubor do složky, kde je uložena šablona");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Vlastnosti");
	    lang.put("tab.document.notes", "Poznámky");
	    lang.put("tab.document.history", "Historie");
	    lang.put("tab.document.status.history", "Upravuje se historie");
	    lang.put("tab.status.security.role", "Aktualizují se přístupové role");
	    lang.put("tab.status.security.user", "Aktualizují se oprávnení uživatelů");
	    lang.put("tab.document.status.group.properties", "Aktualizují se skupina vlastností");
	    lang.put("tab.document.status.set.keywords", "Nastavují se klíčová slova");
	    lang.put("tab.document.status.set.categories", "Updating categories");
	    lang.put("tab.document.status.get.version.history.size", "Aktualizuje se velikost historie dokumentu");
	    lang.put("tab.document.status.purge.version.history", "Zkompaktnit historii dokumentu");
	    lang.put("tab.document.status.restore.version", "Obnovuje se verze dokumentu");
	    lang.put("tab.document.security", "Zabezpečení");
	    lang.put("tab.document.preview", "Náhled");
	    lang.put("tab.folder.properties", "Vlastnosti");
	    lang.put("tab.folder.security", "Zabezpečení");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Plocha");
	    lang.put("tab.workspace.search", "Hledat");
	    lang.put("tab.workspace.dashboard", "Přehled");
	    lang.put("tab.workspace.administration", "Administrace");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Název");
	    lang.put("document.folder", "Složka");
	    lang.put("document.size", "Velikost");
	    lang.put("document.created", "Vytvořeno");
	    lang.put("document.lastmodified", "Změněno");
	    lang.put("document.mimetype", "MIME typ");
	    lang.put("document.keywords", "Klíčová slova");
	    lang.put("document.by", "");
	    lang.put("document.status", "Stav");
	    lang.put("document.status.checkout", "Upraveno");
	    lang.put("document.status.locked", "Zamčeno");
	    lang.put("document.status.normal", "Dostupné");
	    lang.put("document.subscribed", "Nastaveno upozornění o změnách");
	    lang.put("document.subscribed.yes", "Ano");
	    lang.put("document.subscribed.no", "Ne");
	    lang.put("document.history.size", "Velikost historie");
	    lang.put("document.subscribed.users", "Uživatelé kterým je zasíláno upozornění");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Přidat poznámku");
	    lang.put("document.keywords.cloud", "Mrak klíčových slov");
	    lang.put("document.categories", "Categories");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nazev");
	    lang.put("folder.parent", "Rodič");
	    lang.put("folder.created", "Vytvořeno");
	    lang.put("folder.by", "");
	    lang.put("folder.subscribed", "Nastaveno upozornění o změnách");
	    lang.put("folder.subscribed.yes", "Ano");
	    lang.put("folder.subscribed.no", "Ne");
	    lang.put("folder.subscribed.users", "Uživatelé kterým je zasíláno upozornění");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Verze");
	    lang.put("version.created", "Datum");
	    lang.put("version.author", "Autor");
	    lang.put("version.size", "Velikost");
	    lang.put("version.purge.document", "Zkompaktnit historii dokumentu");
	    lang.put("version.comment", "Komentář");
	    
	    // Security
	    lang.put("security.label", "Zabezpečení");
	    lang.put("security.group.name", "Skupina");
	    lang.put("security.group.permission.read", "Číst");
	    lang.put("security.group.permission.write", "Zapisovat");
	    lang.put("security.user.name", "Uživatel");
	    lang.put("security.user.permission.read", "Číst");
	    lang.put("security.user.permission.write", "Zapisovat");
	    lang.put("security.users", "Uživatelé");
	    lang.put("security.groups", "Skupiny");
	    lang.put("security.recursive", "Nastavit práva rekurzivně");
	    
	    // Preview
	    lang.put("preview.unavailable", "Náhled není dostupný");

	    // Mail
	    lang.put("mail.from", "Od");
	    lang.put("mail.reply", "Odpovědět komu");
	    lang.put("mail.to", "Komu");
	    lang.put("mail.subject", "Předmět");
	    lang.put("mail.attachment", "Přílohy");
	    
	    // Error
	    lang.put("error.label", "Systém vytvořil chybu");
	    lang.put("error.invocation", "Chyba při komunikaci se serverem");
	    
	    // About
	    lang.put("about.label", "O OpenKM");
	    lang.put("about.loading", "Nahrává se ...");
	    
	    // Logout
	    lang.put("logout.label", "Konec");
	    lang.put("logout.closed", "OpenKM byl úspěšně ukončen.");
	    lang.put("logout.logout", "Probíhá odhlášení z OpenKM, prosím počkejte");
	    
	    // Confirm
	    lang.put("confirm.label", "Potvrzení");
	    lang.put("confirm.delete.folder", "¿ Opravdu chcete smazat složku ?");
	    lang.put("confirm.delete.document", "¿ Opravdu chcete smazat dokument ?");
	    lang.put("confirm.delete.trash", "¿ Opravdu chcete vyprázdnit koš ?");
	    lang.put("confirm.purge.folder", "¿ Opravdu chcete smazat složku ?");
	    lang.put("confirm.purge.document", "¿ Opravdu chcete smazat dokument ?");
	    lang.put("confirm.delete.propety.group", "¿ Opravdu chcete smazat skupinu vlastností ?");
	    lang.put("confirm.purge.version.history.document", "¿ Opravdu chcete smazat historii dokumentu ?");
	    lang.put("confirm.purge.restore.document", "¿ Opravdu chcete obnovit dokument do této verze ?");
	    lang.put("confirm.set.default.home", "¿ Opravdu chcete nastavit domovskou složku ?");
	    lang.put("confirm.delete.saved.search", "¿ Opravdu chcete smazat uložené hledání ?");
	    lang.put("confirm.delete.user.news", "¿ Opravdu chcete smazat uživatelské zprávy ?");
	    lang.put("confirm.delete.mail", "¿ Opravdu chcete smazat mail ?");
	    
	    // Search inputs
	    lang.put("search.context", "Oblast");
	    lang.put("search.content", "Obsah");
	    lang.put("search.name", "Jméno");
	    lang.put("search.keywords", "Klíčová slova");
	    lang.put("search.folder", "Složka");
	    lang.put("search.category", "Category");
	    lang.put("search.results", "Výsledky");
	    lang.put("search.to", "do");
	    lang.put("search.page.results", "Počet výsledků na stránce");
	    lang.put("search.add.property.group", "Přidat skupinu vlastností");
	    lang.put("search.mimetype", "Mime typ");
	    lang.put("search.type", "Typ");
	    lang.put("search.type.document", "Dokument");
	    lang.put("search.type.folder", "Složka");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Rozšířené hledání");
	    lang.put("search.user", "Uživatel");
	    lang.put("search.date.and", "a");
	    lang.put("search.date.range", "Datum od");
	    lang.put("search.save.as.news", "Uložit jako uživatelskou zprávu");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filtrovat podle složky");
	    lang.put("search.category.filter", "Filter by category");
	    
	    // Search results
	    lang.put("search.result.name", "Jméno");
	    lang.put("search.result.score", "Relevance");
	    lang.put("search.result.size", "Velikost");
	    lang.put("search.result.date.update", "Datum úpravy");
	    lang.put("search.result.author", "Autor");
	    lang.put("search.result.version", "Verze");
	    lang.put("search.result.path", "Cesta");
	    lang.put("search.result.menu.download", "Stáhnout");
	    lang.put("search.result.menu.go.folder", "Jdi do složky");
	    lang.put("search.result.menu.go.document", "Jdi do dokumentu");
	    lang.put("search.result.status.findPaginated", "Aktualizuje se hledání");
	    lang.put("search.result.status.runsearch", "Aktualizuje se uložené hledání");
	    
	    // Search saved
	    lang.put("search.saved.run", "Hledej");
	    lang.put("search.saved.delete", "Smaž");
	    lang.put("search.saved.status.getsearchs", "Aktualizace uložených hledání");
	    lang.put("search.saved.status.savesearch", "Aktualizace uložených hledání");
	    lang.put("search.saved.status.deletesearch", "Mažou se uložená hledání");
	    lang.put("search.saved.status.getusernews", "Aktualizují se uložená hledání");
	    
	    // Button
	    lang.put("button.close", "Zavřít");
	    lang.put("button.logout", "Odhlásit");
	    lang.put("button.update", "Aktualizovat");
	    lang.put("button.cancel", "Zrušit");
	    lang.put("button.accept", "Přimout");
	    lang.put("button.restore", "Obnovit");
	    lang.put("button.move", "Přesunout");
	    lang.put("button.change", "Změnit");
	    lang.put("button.search", "Hledat");
	    lang.put("button.save.search", "Uložit hledání");
	    lang.put("button.view", "Zobrazit");
	    lang.put("button.clean", "Vyčistit");
	    lang.put("button.add", "Přidat");
	    lang.put("button.delete", "Smazat");
	    lang.put("button.copy", "Kopírovat");
	    lang.put("button.create", "Vytvořit");
	    lang.put("button.show", "Ukázat");
	    lang.put("button.memory", "Aktualizovat");
	    lang.put("button.copy.clipboard", "Zkopírovat do clipboardu");	
	    lang.put("button.start", "Start");
	    lang.put("button.select", "Vybrat");
	    
	    // Group
	    lang.put("group.label", "Přidat skupinu vlastností");
	    lang.put("group.group", "Skupina");
	    lang.put("group.property.group", "Vlastnost");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Přidat záložku");
	    lang.put("bookmark.name", "Jméno");
	    lang.put("bookmark.edit.label", "Upravit záložky");
	    lang.put("bookmark.path", "Cesta");
	    lang.put("bookmark.type", "Typ");
	    lang.put("bookmark.type.document", "Dokument");
	    lang.put("bookmark.type.folder", "Složka");
	    
	    // Notify
	    lang.put("notify.label", "Odesílá se odkaz na soubor");
	    
	    // Status
	    lang.put("status.document.copied", "Dokument označen ke zkopírování");
	    lang.put("status.document.cut", "Dokument označen k vyjmutí");
	    lang.put("status.folder.copied", "Složka označena ke zkopírování");
	    lang.put("status.folder.cut", "Složka označena k vyjmutí");
	    lang.put("status.keep.alive.error", "Spojení se serverem bylo přerušeno");
	    lang.put("status.debug.enabled", "Podpora ladění zapnuta");
	    lang.put("status.debug.disabled", "Podpora ladění vypnuta");
	    lang.put("status.network.error.detected", "Chyba síťového připojení");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Neděle");
	    lang.put("calendar.day.monday", "Pondělí");
	    lang.put("calendar.day.tuesday", "Úterý");
	    lang.put("calendar.day.wednesday", "Středa");
	    lang.put("calendar.day.thursday", "Čtvrtek");
	    lang.put("calendar.day.friday", "Pátek");
	    lang.put("calendar.day.saturday", "Sobota");
	    lang.put("calendar.month.january", "Leden");
	    lang.put("calendar.month.february", "Únor");
	    lang.put("calendar.month.march", "Březen");
	    lang.put("calendar.month.april", "Duben");
	    lang.put("calendar.month.may", "Květen");
	    lang.put("calendar.month.june", "Červen");
	    lang.put("calendar.month.july", "Červenec");
	    lang.put("calendar.month.august", "Srpen");
	    lang.put("calendar.month.september", "Září");
	    lang.put("calendar.month.october", "Říjen");
	    lang.put("calendar.month.november", "Listopad");
	    lang.put("calendar.month.december", "Prosinec");
	    
	    // Media player
	    lang.put("media.player.label", "Přehrávač médií");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Prohlížeč obrázků");
	    lang.put("image.viewer.zoom.in", "Zvětšit");
	    lang.put("image.viewer.zoom.out", "Zmenšit");
	    
	    // Debug console
	    lang.put("debug.console.label", "Konzole pro ladění");
	    lang.put("debug.enable.disable", "CTRL+Z pro zapnutí nebo vypnutí podpory ladění");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Obecné");
	    lang.put("dashboard.tab.news", "Novinky");
	    lang.put("dashboard.tab.user", "Uživatel");
	    lang.put("dashboard.tab.workflow", "Průběh zpracování dokumentu");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Mapa klíčových slov");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Nový");
	    lang.put("dashboard.user.locked.documents", "Uzamčené dokumenty");
	    lang.put("dashboard.user.checkout.documents", "Checkout documents");
	    lang.put("dashboard.user.last.modified.documents", "Naposledy upravené dokumenty");
	    lang.put("dashboard.user.last.downloaded.documents", "Naposledy stažené dokumenty");
	    lang.put("dashboard.user.subscribed.documents", "Domumenty o kterých je zasíláno upozornění");
	    lang.put("dashboard.user.subscribed.folders", "Složky o kterých je zasíláno upozornění");
	    lang.put("dashboard.user.last.uploaded.documents", "Naposledy nahrané dokumenty");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Nejvíce prohlížené dokumenty posledního týdne");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Nejvíce prohlížené dokumenty posledního měsíce");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Nejvíce upravované dokumenty posledního týdne");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Nejvíce upravované dokumenty posledního měsíce");
	    lang.put("dashboard.general.last.uploaded.documents", "Naposledy nahrané dokumenty");
	    lang.put("dashboard.workflow.pending.tasks", "Čekající úlohy");
	    lang.put("dashboard.workflow.task", "Úloha");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Jméno");
	    lang.put("dashboard.workflow.task.created", "Datum vytvoření");
	    lang.put("dashboard.workflow.task.start", "Datum spuštění");
	    lang.put("dashboard.workflow.task.duedate", "Plánované datum dokončení");
	    lang.put("dashboard.workflow.task.end", "Datum ukončení");
	    lang.put("dashboard.workflow.task.description", "Popis");
	    lang.put("dashboard.workflow.task.process.instance", "Proces");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Verze");
	    lang.put("dashboard.workflow.task.process.name", "Jméno");
	    lang.put("dashboard.workflow.task.process.description", "Popis");
	    lang.put("dashboard.workflow.task.process.data", "Data");
	    lang.put("dashboard.workflow.task.process.definition", "Definice procesu");
	    lang.put("dashboard.workflow.task.process.path", "Cesta");
	    lang.put("dashboard.refreshing", "Obnovuji");
	    lang.put("dashboard.keyword", "Klíčová slova");
	    lang.put("dashboard.keyword.suggest", "Napište klíčové slovo");
	    lang.put("dashboard.keyword.all", "Všechna klíčová slova");
	    lang.put("dashboard.keyword.top", "Nejpoužívanější klíčová slova");
	    lang.put("dashboard.keyword.related", "Příbuzná klíčová slova");
	    lang.put("dashboard.keyword.goto.document", "Jdi k dokumentu");
	    lang.put("dashboard.keyword.clean.keywords", "Vyčistit klíčová slova");
	    lang.put("dashboard.mail.last.imported.mails", "Elektronické zprávy");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Přílohy");
	    
	    // Workflow
	    lang.put("workflow.label", "Začni průběh zpracování dokumentu");
	    
	    // User configuration
	    lang.put("user.preferences.label", "Uživatelská nastavení");
	    lang.put("user.preferences.user", "Uživatel");
	    lang.put("user.preferences.password", "Heslo");
	    lang.put("user.preferences.mail", "E-mail");
	    lang.put("user.preferences.imap.host", "IMAP server");
	    lang.put("user.preferences.imap.user", "IMAP uživatelské jméno");
	    lang.put("user.preferences.imap.user.password", "IMAP heslo");
	    lang.put("user.preferences.imap.folder", "IMAP složka");
	    lang.put("user.preferences.password.error", "Chyba: hesla si neodpovídají");
	    lang.put("user.preferences.user.data", "Uživatelský účet");
	    lang.put("user.preferences.mail.data", "Mailový účet");
	    lang.put("user.preferences.imap.error", "Všechna pole jsou povinná pokud chcete dostávat upozornění mailem.");
	    lang.put("user.preferences.imap.password.error.void", "Heslo IMAP musí být vyplněno");
	    
	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Add thesaurus keyword");
	    lang.put("thesaurus.tab.tree", "Tree");
	    lang.put("thesaurus.tab.keywords", "Keywords");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Add category");
	    lang.put("categories.folder.error.delete", "Can not delete category with documents");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Přístup k dokumentu není povolen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Zamčení dokumentu není povoleno");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Chyba úložiště");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Nebyla nalezena cesta k souboru");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Version error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Přístup k dokumentu není povolen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Dokument nenalezen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Dokument již existuje");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Zamčení dokumentu není povoleno");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Je požadováno odemčení dokumentu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Chyba úložiště");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Chyba aplikace");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Nebyla nalezena cesta k souboru");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Přístup do složky není povolen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Složka nebyla nalezena");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Složka již existuje");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Chyba úložiště");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Chyba úložiště");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Cesta ke složce nebyla nalezena");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Přístup k položce není povolen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Položka nebyla nalezena");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Chyba úložiště");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Chyba úložiště");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Dokument nenalezen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Chyba úložiště");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Nepodporovaný formát");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Dokument již existuje");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Soubor je příliš veliký");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Relace byla ukončena");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Obecná chyba při spouštění dotazu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Jméno uloženého hledání musí být unikátní");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Jméno záložky musí být unikátní");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Relace ztracena");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Chyba úložiště");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Chyba úložiště");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Chyba čtení/zápisu");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Chyba úložiště");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error in browser configuration");
	  }
}
