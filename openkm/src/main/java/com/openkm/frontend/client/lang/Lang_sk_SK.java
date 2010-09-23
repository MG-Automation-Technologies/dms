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
 * Slovak (Slovak republic)
 * 
 * @author Miro & Jano
 */
public class Lang_sk_SK {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd.MM.yyyy HH:mm:ss");
	    lang.put("general.day.pattern", "dd.MM.yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Nahrávanie OpenKM");
	    lang.put("startup.starting.loading", "Štartujem nahrávanie OpenKM");
	    lang.put("startup.taxonomy", "Getting taxonomy root node");
	    lang.put("startup.categories", "Getting categories root node");
	    lang.put("startup.thesaurus", "Getting thesaurus root node");
	    lang.put("startup.template", "Getting template root node");
	    lang.put("startup.personal", "Getting personal root node");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Getting trash root node");
	    lang.put("startup.user.home", "Getting user home node");
	    lang.put("startup.bookmarks", "Getting bookmarks");
	    lang.put("startup.loading.taxonomy", "Loading taxonomy");
	    lang.put("startup.loading.taxonomy.getting.folders", "Loading taxonomy - getting folders");
	    lang.put("startup.loading.taxonomy.eval.params", "Loading taxonomy - eval browser params");
	    lang.put("startup.loading.taxonomy.open.path", "Loading taxonomy - open path");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Loading taxonomy - getting folders");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Loading taxonomy - getting documents");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Loading personal");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.categories", "Loading categories");
	    lang.put("startup.loading.thesaurus", "Loading thesaurus");
	    lang.put("startup.loading.templates", "Loading templates");
	    lang.put("startup.loading.trash", "Loading trash");
	    lang.put("startup.loading.history.search", "Loading history search");
	    lang.put("startup.loading.user.values", "Loading user values");
	    lang.put("startup.keep.alive", "Loading keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Je dostupný update OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Adresárový strom");
	    lang.put("leftpanel.label.trash", "Kôš");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Uložené vyhľadávania");
	    lang.put("leftpanel.label.categories", "Categories");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Šablóny");
	    lang.put("leftpanel.label.my.documents", "Moje dokumenty");
	    lang.put("leftpanel.label.user.search", "Novinky");
	    lang.put("leftpanel.label.all.repository", "Všetky repozitáre");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Vytvoriť adresár");
	    lang.put("tree.menu.directory.remove", "Zmazať");
	    lang.put("tree.menu.directory.rename", "Premenovať");
	    lang.put("tree.menu.directory.refresh", "Obnoviť");
	    lang.put("tree.menu.directory.move", "Presunúť");
	    lang.put("tree.menu.directory.copy", "Kopírovať");
	    lang.put("tree.menu.directory.add.document", "Pridať dokument");
	    lang.put("tree.menu.add.bookmark", "Pridať záložku");
	    lang.put("tree.menu.set.home", "Nastaviť domovský adresár");
	    lang.put("tree.menu.export", "Export do súboru");
	    lang.put("tree.status.refresh.folder", "Aktualizujem strom adresárov");
	    lang.put("tree.status.refresh.create", "Vytváram adresár");
	    lang.put("tree.status.refresh.delete", "Mažem adresár");
	    lang.put("tree.status.refresh.rename", "Premenuvávam adresár");
	    lang.put("tree.status.refresh.restore", "Obnovujem adresár");
	    lang.put("tree.status.refresh.purge", "Čistím adresár");
	    lang.put("tree.status.refresh.get", "Aktualizujem adresár");
	    lang.put("tree.folder.new", "Nový adresár");
	    lang.put("tree.status.refresh.add.subscription", "Pridávam predplatné");
	    lang.put("tree.status.refresh.remove.subscription", "Mažem predplatné");
	    lang.put("tree.status.refresh.get.root", "Obnovujem koreňový adresár");
	    lang.put("tree.status.refresh.get.keywords", "Refreshing keywords");
	    lang.put("tree.status.refresh.get.user.home", "Obnovujem domovský adresár");
	    lang.put("tree.status.refresh.purge.trash", "Čistím kôš");
	    lang.put("tree.menu.directory.find.folder","Find folder");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Obnova");
	    lang.put("trash.menu.directory.purge", "Čistenie");
	    lang.put("trash.menu.directory.purge.trash", "Vyčistiť kôš");
	    lang.put("trash.directory.select.label", "Vyberte cieľový adresár");
	    
	    // General menu
	    lang.put("general.menu.file", "Súbor");
	    	lang.put("general.menu.file.exit", "Koniec");
	    	lang.put("general.menu.file.purge.trash", "Vyčistiť kôš");
	    lang.put("general.menu.edit", "Upraviť");
			lang.put("general.menu.file.create.directory", "Vytvoriť adresár");
			lang.put("general.menu.file.download.document", "Stiahnúť dokument");
			lang.put("general.menu.file.download.document.pdf", "Stiahnuť dokument ako PDF");
			lang.put("general.menu.file.send.link", "Poslať odkaz na dokument");
			lang.put("general.menu.file.send.attachment", "Send document attachment");
			lang.put("general.menu.file.lock", "Uzamknúť");
			lang.put("general.menu.file.unlock", "Odomknúť");
			lang.put("general.menu.file.add.document", "Pridať dokument");
			lang.put("general.menu.file.checkout", "Check out");
			lang.put("general.menu.file.checkin", "Check in");
			lang.put("general.menu.file.cancel.checkout", "Prerušiť check out");
			lang.put("general.menu.file.delete", "Zmazať");
			lang.put("general.menu.file.refresh", "Obnoviť");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "File uploader");
	    lang.put("general.menu.tools", "Nástroje");
	    	lang.put("general.menu.tools.languages", "Jazyky");
	    	lang.put("general.menu.tools.skin", "Skin");
    			lang.put("general.menu.tools.skin.default", "Default");
    			lang.put("general.menu.tools.skin.default2", "Default 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Stredný font");
    			lang.put("general.menu.tools.skin.bigfont", "Veľký font");
    		lang.put("general.menu.debug.console", "Ladiaca konzola");
    		lang.put("general.menu.administration", "Spravovanie");
    		lang.put("general.menu.tools.preferences", "Nastavenia");
    			lang.put("general.menu.tools.user.preferences", "Užívateľské nastavenia");
    	lang.put("general.menu.bookmark", "Záložky");
	    	lang.put("general.menu.bookmark.home", "Domov");
	    	lang.put("general.menu.bookmark.default.home", "Nastav domov");
	    	lang.put("general.menu.bookmark.edit", "Úprava záložiek");
	    lang.put("general.menu.help", "Pomoc");
		    lang.put("general.menu.bug.report", "Správa o chybách");
		    lang.put("general.menu.support.request", "Žiadosť o podporu");
		    lang.put("general.menu.public.forum", "Verjné fórum");
		    lang.put("general.menu.project.web", "WEB projektu");
		    lang.put("general.menu.version.changes", "Poznámky k verzii");
		    lang.put("general.menu.documentation", "Dokumentácia");
		    lang.put("general.menu.about", "O OpenKM");
	    lang.put("general.connected", "Pripojený ako");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Meno");
	    lang.put("filebrowser.date.update", "Dátum aktualizácie");
	    lang.put("filebrowser.size", "Veľkosť");
	    lang.put("filebrowser.path", "Cesta");
	    lang.put("filebrowser.author", "Autor");
	    lang.put("filebrowser.version", "Verzia");
	    lang.put("filebrowser.menu.checkout", "Check out");
	    lang.put("filebrowser.menu.checkin", "Check in");
	    lang.put("filebrowser.menu.delete", "Zmazať");
	    lang.put("filebrowser.menu.rename", "Premenovať");
	    lang.put("filebrowser.menu.checkout.cancel", "Zrušiť check out");
	    lang.put("filebrowser.menu.lock", "Uzamknúť");
	    lang.put("filebrowser.menu.unlock", "Odomknúť");
	    lang.put("filebrowser.menu.download", "Stiahnúť");
	    lang.put("filebrowser.menu.move", "Presunúť");
	    lang.put("filebrowser.menu.copy", "Kopírovať");
	    lang.put("filebrowser.menu.create.from.template", "Vytvoriť zo šablóny");
	    lang.put("filebrowser.menu.add.property.group", "Pridať skupinu vlastností");
	    lang.put("filebrowser.menu.remove.property.group", "Odstrániť skupinu vlastností");
	    lang.put("filebrowser.menu.start.workflow", "Štart workflow");
	    lang.put("filebrowser.menu.add.subscription", "Pridaj predplatné");
	    lang.put("filebrowser.menu.remove.subscription", "Zmaž predplatné");
	    lang.put("filebrowser.menu.add.bookmark", "Pridaj záložku");
	    lang.put("filebrowser.menu.set.home", "Nastav default domov");
	    lang.put("filebrowser.menu.refresh", "Obnov");
	    lang.put("filebrowser.menu.export", "Export to ZIP");
	    lang.put("filebrowser.menu.play", "Prehraj");
	    lang.put("filebrowser.menu.image.viewer", "Prehliadač obrázkov");
	    lang.put("filebrowser.status.refresh.folder", "Update zoznamu adresárov");
	    lang.put("filebrowser.status.refresh.document", "Update zoznamu dokumentov");
	    lang.put("filebrowser.status.refresh.mail", "Update zoznamu mailov");
	    lang.put("filebrowser.status.refresh.delete.folder", "Zmaž adresár");
	    lang.put("filebrowser.status.refresh.delete.document", "Zmaž dokument");
	    lang.put("filebrowser.status.refresh.checkout", "Check-out dokument");
	    lang.put("filebrowser.status.refresh.lock", "Uzamkni dokument");
	    lang.put("filebrowser.status.refresh.unlock", "Odomkni dokument");
	    lang.put("filebrowser.status.refresh.document.rename", "Premenuj dokument");
	    lang.put("filebrowser.status.refresh.folder.rename", "Premenuj adresár");
	    lang.put("filebrowser.status.refresh.document.purge", "Zrušenie dokumentu");
	    lang.put("filebrowser.status.refresh.folder.purge", "Zrušenie adresára");
	    lang.put("filebrowser.status.refresh.folder.get", "Update adresára");
	    lang.put("filebrowser.status.refresh.document.get", "Update dokumentu");
	    lang.put("filebrowser.status.refresh.add.subscription", "Pridávam predplatné");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Mažem predplatné");
	    lang.put("filebrowser.status.refresh.get.user.home", "Getting user home");
	    lang.put("filebrowser.status.refresh.delete.mail", "Mazanie mailu");
	    lang.put("filebrowser.status.refresh.mail.purge", "Definitívne mazanie mailu");
	    
	    // File Upload
	    lang.put("fileupload.send", "Pošli");
	    lang.put("fileupload.status.sending", "Posielam súbor...");
	    lang.put("fileupload.status.indexing", "Indexujem súbor...");
	    lang.put("fileupload.status.ok", "Súbor nahratý");
	    lang.put("fileupload.upload.status", "Status");
	    lang.put("fileupload.upload.uploaded", "Odoslaný");
	    lang.put("fileupload.upload.completed", "Odosielanie ukončené");
	    lang.put("fileupload.upload.runtime", "Runtime");
	    lang.put("fileupload.upload.remaining", "Zostáva");
	    lang.put("fileupload.button.close", "Uzavri");
	    lang.put("fileupload.button.add.other.file", "Pridaj ďalší súbor");
	    lang.put("fileupload.status.move.file", "Presúvam súbor ...");
	    lang.put("fileupload.status.move.mail", "Presúvam mail...");
	    lang.put("fileupload.status.copy.file", "Kopírujem súbor...");
	    lang.put("fileupload.status.copy.mail", "Kopírujem mail...");
	    lang.put("fileupload.status.restore.file", "Obnovujem súbor...");
	    lang.put("fileupload.status.restore.mail", "Obnovujem mail...");
	    lang.put("fileupload.status.move.folder", "Presúvam adresár...");
	    lang.put("fileupload.status.copy.folder", "Kopírujem adresár...");
	    lang.put("fileupload.status.restore.folder", "Obnovujem adresár...");
	    lang.put("fileupload.status.create.from.template", "Vytvorenie súboru zo šablóny...");
	    lang.put("fileupload.status.of", "z");
	    lang.put("fileupload.label.insert", "Pridaj nové dokumenty");
	    lang.put("fileupload.label.update", "Update dokumentov");
	    lang.put("fileupload.label.users.notify", "Notifikácia užívateľom");
	    lang.put("fileupload.label.comment", "Komentár");
	    lang.put("fileupload.label.users.to.notify",  "Notifikovať užívateľov");
	    lang.put("fileupload.label.users",  "Užívatelia");
	    lang.put("fileupload.label.groups.to.notify","Groups to notify");
	    lang.put("fileupload.label.groups","Groups");
	    lang.put("fileupload.label.must.select.users",  "Musíš vybrať niekoho na notifikáciu");
	    lang.put("fileupload.label.importZip", "Import Dokumentu zo ZIP");
	    lang.put("fileupload.label.notify.comment", "Notifikačná správa");
	    lang.put("fileupload.label.error.importing.zip", "Chyba pri importe súboru");
	    lang.put("fileupload.label.error.move.file", "Chyba pri presúvaní súboru");
	    lang.put("fileupload.label.error.move.mail", "Chyba pri presúvaní mailu");
	    lang.put("fileupload.label.error.copy.file", "Chyba pri kopírovaní súboru");
	    lang.put("fileupload.label.error.copy.mail", "Chyba pri kopírovaní mailu");
	    lang.put("fileupload.label.error.restore.file", "Chyba pri obnovovaní súboru");
	    lang.put("fileupload.label.error.restore.mail", "Chyba pri obnovovaní mailu");
	    lang.put("fileupload.label.error.move.folder", "Chyba pri presúvaní adresára");
	    lang.put("fileupload.label.error.copy.folder", "Chyba pri kopírovaní adresára");
	    lang.put("fileupload.label.error.restore.folder", "Chyba pri obnovovaní adresára");
	    lang.put("fileupload.label.error.create.from.template", "Chyba pri vytváraní súboru zo šablóny");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Nedostatok práv pre vykonanie operácie");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Nedostatok práv pre vykonanie operácie");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Nedostatok práv pre vytváranie súboru v tomto adresári");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Vlastnosti");
	    lang.put("tab.document.notes", "Poznámky");
	    lang.put("tab.document.history", "História");
	    lang.put("tab.document.status.history", "Obnovenie histórie history");
	    lang.put("tab.status.security.role", "Obnovenie bezpečnostných rolí");
	    lang.put("tab.status.security.user", "Updating security users");
	    lang.put("tab.document.status.group.properties", "Obnovenie vlastníckej skupiny");
	    lang.put("tab.document.status.set.keywords", "Nastavenie kľúčových slov");
	    lang.put("tab.document.status.set.categories", "Updating categories");
	    lang.put("tab.document.status.get.version.history.size", "Občerstvenie histórie veľkosti dokumentu");
	    lang.put("tab.document.status.purge.version.history", "Komprimovanie histórie dokumentu");
	    lang.put("tab.document.status.restore.version", "Obnovenie verzie dokumentu");
	    lang.put("tab.document.security", "Bezpečnosť");
	    lang.put("tab.document.preview", "Prehľad");
	    lang.put("tab.folder.properties", "Vlastnosti");
	    lang.put("tab.folder.security", "Bezpečnosť");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Pracovná plocha");
	    lang.put("tab.workspace.search", "Hľadaj");
	    lang.put("tab.workspace.dashboard", "Palubná doska");
	    lang.put("tab.workspace.administration", "Administrácia");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Meno");
	    lang.put("document.folder", "Adresár");
	    lang.put("document.size", "Veľkosť");
	    lang.put("document.created", "Vytvorený");
	    lang.put("document.lastmodified", "Modifikovaný");
	    lang.put("document.mimetype", "MIME typ");
	    lang.put("document.keywords", "Kľúčové slová");
	    lang.put("document.by", "by");
	    lang.put("document.status", "Status");
	    lang.put("document.status.checkout", "Editovaný kým");
	    lang.put("document.status.locked", "Uzamknutý kým");
	    lang.put("document.status.normal", "Dostupný");
	    lang.put("document.subscribed", "Predplatený");
	    lang.put("document.subscribed.yes", "Áno");
	    lang.put("document.subscribed.no", "Nie");
	    lang.put("document.history.size", "História veľkosti");
	    lang.put("document.subscribed.users", "Predplatený pre užívateľov");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Pridaj poznámku");
	    lang.put("document.keywords.cloud", "Skupina kľúčových slov");
	    lang.put("document.categories", "Categories");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Meno");
	    lang.put("folder.parent", "Rodič");
	    lang.put("folder.created", "Vytvorený");
	    lang.put("folder.by", "kým");
	    lang.put("folder.subscribed", "Predplatený");
	    lang.put("folder.subscribed.yes", "Áno");
	    lang.put("folder.subscribed.no", "Nie");
	    lang.put("folder.subscribed.users", "Predplatený pre užívateľov");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Folders");
	    lang.put("folder.number.documents", "Documents");
	    lang.put("folder.number.mails", "Mails");
	    
	    // Version
	    lang.put("version.name", "Verzia");
	    lang.put("version.created", "Dátum");
	    lang.put("version.author", "Autor");
	    lang.put("version.size", "Veľkosť");
	    lang.put("version.purge.document", "História v skratke");
	    lang.put("version.comment", "Komentár");
	    
	    // Security
	    lang.put("security.label", "Bezpečnosť");
	    lang.put("security.group.name", "Skupina");
	    lang.put("security.group.permission.read", "Čítať");
	    lang.put("security.group.permission.write", "Zapisovať");
	    lang.put("security.group.permission.delete", "Delete");
	    lang.put("security.group.permission.security", "Security");
	    lang.put("security.user.name", "Užívateľ");
	    lang.put("security.user.permission.read", "Čítať");
	    lang.put("security.user.permission.write", "Zapisovať");
	    lang.put("security.user.permission.delete", "Delete");
	    lang.put("security.user.permission.security", "Security");
	    lang.put("security.users", "Užívatelia");
	    lang.put("security.groups", "Skupiny");
	    lang.put("security.recursive", "Rekurzívna zmena práv");
	    lang.put("secutiry.filter.by.users","Users filter");
	    lang.put("secutiry.filter.by.groups","Groups filter");
	    lang.put("security.status.updating","Updating security");
	    
	    // Preview
	    lang.put("preview.unavailable", "Prehliadač nedostupný");

	    // Mail
	    lang.put("mail.from", "Od");
	    lang.put("mail.reply", "Odpovedať komu");
	    lang.put("mail.to", "Komu");
	    lang.put("mail.subject", "Predmet");
	    lang.put("mail.attachment", "Prílohy");
	    
	    // Error
	    lang.put("error.label", "Systém vygeneroval chybu");
	    lang.put("error.invocation", "Chyba pri komunikácii so serverom");
	    
	    // About
	    lang.put("about.label", "O OpenKM");
	    lang.put("about.loading", "Nahrávam ...");
	    
	    // Logout
	    lang.put("logout.label", "Exit");
	    lang.put("logout.closed", "OpenKM bol ukončený korektne.");
	    lang.put("logout.logout", "Odhlasovanie z OpenKM, prosím o trpezlivosť");
	    
	    // Confirm
	    lang.put("confirm.label", "Potvrdenie");
	    lang.put("confirm.delete.folder", "¿ Naozaj chceš zmazať adresár?");
	    lang.put("confirm.delete.document", "¿ Naozaj chceš zmazať dokument?");
	    lang.put("confirm.delete.trash", "¿ Naozaj chceš vysypať kôš?");
	    lang.put("confirm.purge.folder", "¿ Naozaj chceš definitívne vymazať adresár?");
	    lang.put("confirm.purge.document", "¿ Naozaj chceš vyčistiť dokument?");
	    lang.put("confirm.delete.propety.group", "¿ Naozaj chceš zmazať vlstnícku skupinu?");
	    lang.put("confirm.purge.version.history.document", "¿ Naozaj chceš definitívne zmazať históriu dokumentu?");
	    lang.put("confirm.purge.restore.document", "¿ Naozaj chceš obnoviť verziu dokumentu?");
	    lang.put("confirm.set.default.home", "¿ Naozaj chceš nastaviť domov?");
	    lang.put("confirm.delete.saved.search", "¿ Naozaj chceš zmazať uložený výber?");
	    lang.put("confirm.delete.user.news", "¿ Naozaj chceš zmazať užívateľské novinky?");
	    lang.put("confirm.delete.mail", "¿ Naozaj chceš zmazať mail?");
	    lang.put("confirm.get.pooled.workflow.task","¿ Chceš si prideliť túto úlohu?");
	    lang.put("confirm.force.unlock","¿ Are you sure you want to force canceling locked document ?)");
	    lang.put("confirm.force.cancel.checkout","¿ Are you sure you want to force cancelling chekcout document ?");
	    
	    // Search inputs
	    lang.put("search.context", "Kontext");
	    lang.put("search.content", "Obsah");
	    lang.put("search.name", "Meno");
	    lang.put("search.keywords", "Kľúčové slová");
	    lang.put("search.folder", "Adresár");
	    lang.put("search.category", "Category");
	    lang.put("search.results", "Výsledky");
	    lang.put("search.to", "to");
	    lang.put("search.page.results", "Počet výsledkov na stranu");
	    lang.put("search.add.property.group", "Pridaj skupinu vlastníkov");
	    lang.put("search.mimetype", "Mime typ");
	    lang.put("search.type", "Typ");
	    lang.put("search.type.document", "Dokument");
	    lang.put("search.type.folder", "Adresár");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Rozšírené hľadanie");
	    lang.put("search.user", "Užívateľ");
	    lang.put("search.date.and", "a");
	    lang.put("search.date.range", "Rozsah dátumu medzi");
	    lang.put("search.save.as.news", "Ulož ako novinku");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter na adresár");
	    lang.put("search.category.filter", "Filter by category");
	    
	    // Search results
	    lang.put("search.result.name", "Meno");
	    lang.put("search.result.score", "Relevantnosť");
	    lang.put("search.result.size", "Veľkosť");
	    lang.put("search.result.date.update", "Dátum obnovy");
	    lang.put("search.result.author", "Autor");
	    lang.put("search.result.version", "Verzia");
	    lang.put("search.result.path", "Cesta");
	    lang.put("search.result.menu.download", "Download");
	    lang.put("search.result.menu.go.folder", "Choď do adresára");
	    lang.put("search.result.menu.go.document", "Prejdi na dokument");
	    lang.put("search.result.status.findPaginated", "Update hľadania");
	    lang.put("search.result.status.runsearch", "Update uloženého hľadania");
	    
	    // Search saved
	    lang.put("search.saved.run", "Spusti");
	    lang.put("search.saved.delete", "Zmaž");
	    lang.put("search.saved.status.getsearchs", "Refreshing saved searches");
	    lang.put("search.saved.status.savesearch", "Updating save search");
	    lang.put("search.saved.status.deletesearch", "Deleting saved search");
	    lang.put("search.saved.status.getusernews", "Refreshing user news");
	    
	    // Button
	    lang.put("button.close", "Zavri");
	    lang.put("button.logout", "Odhlás");
	    lang.put("button.update", "Update");
	    lang.put("button.cancel", "Zruš");
	    lang.put("button.accept", "Akceptuj");
	    lang.put("button.restore", "Obnov");
	    lang.put("button.move", "Presuň");
	    lang.put("button.change", "Zmeň");
	    lang.put("button.search", "Hľadaj");
	    lang.put("button.save.search", "Ulož výber");
	    lang.put("button.view", "Pohľad");
	    lang.put("button.clean", "Vyčisti");
	    lang.put("button.add", "Pridaj");
	    lang.put("button.delete", "Zmaž");
	    lang.put("button.copy", "Kopíruj");
	    lang.put("button.create", "Vytvor");
	    lang.put("button.show", "Ukáž");
	    lang.put("button.memory", "Update");
	    lang.put("button.copy.clipboard", "Kopíruj do schránky");	
	    lang.put("button.start", "Štart");
	    lang.put("button.select", "Vyber");
	    lang.put("button.test", "Test");
	    lang.put("button.next", "Next");
	    
	    // Group
	    lang.put("group.label", "Pridaj skupinu vlastníkov");
	    lang.put("group.group", "Skupina");
	    lang.put("group.property.group", "Vlastníctvo");lang.put("general.menu.file.scanner", "Scanner");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Pridaj záložku");
	    lang.put("bookmark.name", "Meno");
	    lang.put("bookmark.edit.label", "Edituj záložky");
	    lang.put("bookmark.path", "Cesta");
	    lang.put("bookmark.type", "Typ");
	    lang.put("bookmark.type.document", "Dokument");
	    lang.put("bookmark.type.folder", "Adresár");
	    
	    // Notify
	    lang.put("notify.label", "Pošli link na dokument");
	    lang.put("notify.label.attachment", "Send document attachment");
	    
	    // Status
	    lang.put("status.document.copied", "Dokument označený na kopírovanie");
	    lang.put("status.document.cut", "Dokument označený na vystrihnutie");
	    lang.put("status.folder.copied", "Adresár označený na kopírovanie");
	    lang.put("status.folder.cut", "Adresár označený na vystrihnutie");
	    lang.put("status.keep.alive.error", "Detekovaná strata spojenia so serverom");
	    lang.put("status.debug.enabled", "Debug mód zapnutý");
	    lang.put("status.debug.disabled", "Debug mód vypnutý");
	    lang.put("status.network.error.detected", "Detekovaná sieťová chyba");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Nedeľa");
	    lang.put("calendar.day.monday", "Pondelok");
	    lang.put("calendar.day.tuesday", "Utorok");
	    lang.put("calendar.day.wednesday", "Streda");
	    lang.put("calendar.day.thursday", "Štvrtok");
	    lang.put("calendar.day.friday", "Piatok");
	    lang.put("calendar.day.saturday", "Sobota");
	    lang.put("calendar.month.january", "Január");
	    lang.put("calendar.month.february", "Februáry");
	    lang.put("calendar.month.march", "Marec");
	    lang.put("calendar.month.april", "Apríl");
	    lang.put("calendar.month.may", "Máj");
	    lang.put("calendar.month.june", "Júne");
	    lang.put("calendar.month.july", "Júly");
	    lang.put("calendar.month.august", "August");
	    lang.put("calendar.month.september", "September");
	    lang.put("calendar.month.october", "Octóber");
	    lang.put("calendar.month.november", "November");
	    lang.put("calendar.month.december", "December");
	    
	    // Media player
	    lang.put("media.player.label", "Prehrávač médií");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Prehliadač obrázkov");
	    lang.put("image.viewer.zoom.in", "Zväčšiť");
	    lang.put("image.viewer.zoom.out", "Zmenšiť");
	    
	    // Debug console
	    lang.put("debug.console.label", "Debug konzola");
	    lang.put("debug.enable.disable", "Stlač CTRL+Z na zapnutie/vypnutie debug módu");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Všeobecné");
	    lang.put("dashboard.tab.news", "Novinky");
	    lang.put("dashboard.tab.user", "Užívateľ");
	    lang.put("dashboard.tab.workflow", "Workflow");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Mapa kľúčových slov");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Nový");
	    lang.put("dashboard.user.locked.documents", "Uzamknuté dokumenty");
	    lang.put("dashboard.user.checkout.documents", "Checkout dokumenty");
	    lang.put("dashboard.user.last.modified.documents", "Naposledy modifikované dokumenty");
	    lang.put("dashboard.user.last.downloaded.documents", "Naposledy stiahnuté dokumenty");
	    lang.put("dashboard.user.subscribed.documents", "Predplatené dokumenty");
	    lang.put("dashboard.user.subscribed.folders", "Predplatené adresáre");
	    lang.put("dashboard.user.last.uploaded.documents", "Naposledy odoslané dokumenty");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Najčastejšie prezerané za posledný týždeň");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Najčastejšie prezerané za posledný mesiac");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Najčastejšie modofikované za posledný týždeň");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Najčastejšie modifikované za posledný mesiac");
	    lang.put("dashboard.general.last.uploaded.documents", "Naposledy odoslané dokumenty");
	    lang.put("dashboard.workflow.pending.tasks", "Úlohy vo fronte");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Nepridelené úlohy vo fronte");
	    lang.put("dashboard.workflow.task", "Úloha");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Meno");
	    lang.put("dashboard.workflow.task.created", "Dátum vytvorenia");
	    lang.put("dashboard.workflow.task.start", "Dátum začiatku");
	    lang.put("dashboard.workflow.task.duedate", "Ukončiť do");
	    lang.put("dashboard.workflow.task.end", "Dátum ukončenia");
	    lang.put("dashboard.workflow.task.description", "Popis");
	    lang.put("dashboard.workflow.task.process.instance", "Process instance");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Verzia");
	    lang.put("dashboard.workflow.task.process.name", "Meno");
	    lang.put("dashboard.workflow.task.process.description", "Popis");
	    lang.put("dashboard.workflow.task.process.data", "Data");
	    lang.put("dashboard.workflow.comments", "Komentáre");
	    lang.put("dashboard.workflow.task.process.forms", "Forma");
	    lang.put("dashboard.workflow.add.comment","Pridaj komentár");
	    lang.put("dashboard.workflow.task.process.definition", "Definícia procesu");
	    lang.put("dashboard.workflow.task.process.path", "Cesta");
	    lang.put("dashboard.refreshing", "Obnovenie");
	    lang.put("dashboard.keyword", "Kľúčové slová");
	    lang.put("dashboard.keyword.suggest", "Napíš kľúčové slovo");
	    lang.put("dashboard.keyword.all", "Všetky kľúčové slová");
	    lang.put("dashboard.keyword.top", "Najčastejšie kľúčové slová");
	    lang.put("dashboard.keyword.related", "Súvisiace kľúčové slová");
	    lang.put("dashboard.keyword.goto.document", "Prejdi na dokument");
	    lang.put("dashboard.keyword.clean.keywords", "Vyčisti kľúčové slová");
	    lang.put("dashboard.mail.last.imported.mails", "Electronická pošta");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Prílohy");
	    
	    // Workflow
	    lang.put("workflow.label", "Spusti workflow");
	    
	    // User configuration
	    lang.put("user.preferences.label", "Konfigurácia užívateľa");
	    lang.put("user.preferences.user", "Užívateľ");
	    lang.put("user.preferences.password", "Heslo");
	    lang.put("user.preferences.mail", "E-mail");
	    lang.put("user.preferences.roles","Roles");
	    lang.put("user.preferences.imap.host", "IMAP server");
	    lang.put("user.preferences.imap.user", "IMAP užívateľské meno");
	    lang.put("user.preferences.imap.user.password", "IMAP užívateľské heslo");
	    lang.put("user.preferences.imap.folder", "IMAP adresár");
	    lang.put("user.preferences.password.error", "CHYBA: heslo je nesprávne");
	    lang.put("user.preferences.user.data", "Užívateľský účet");
	    lang.put("user.preferences.mail.data", "Mailový účet");
	    lang.put("user.preferences.imap.error", "Všetky polia sú povinné pre nastavenie mailových služieb");
	    lang.put("user.preferences.imap.password.error.void", "Heslo nesmie byť prázdne");
	    lang.put("user.preferences.imap.test.error","IMAP configuration error");
	    lang.put("user.preferences.imap.test.ok","IMAP configuration ok");
	    
	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Add thesaurus keyword");
	    lang.put("thesaurus.tab.tree", "Tree");
	    lang.put("thesaurus.tab.keywords", "Keywords");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Add category");
	    lang.put("categories.folder.error.delete", "Cannot delete category with documents");
	    
	    // Wizard
	    lang.put("wizard.document.uploading", "Document wizard");
	    
	    // User info
	    lang.put("user.info.chat.connect", "Connect to chat");
	    lang.put("user.info.chat.disconnect", "Disconnet chat");
	    lang.put("user.info.chat.new.room", "Net chat room");
	    lang.put("user.info.locked.actual", "Locked documents");
	    lang.put("user.info.checkout.actual", "Checkout documents");
	    lang.put("user.info.subscription.actual", "Actual subscriptions");
	    lang.put("user.info.news.new", "News");
	    lang.put("user.info.workflow.pending", "Pending workflows");
	    lang.put("user.info.user.quota", "Used quota");
	    
	    // Users online popup
	    lang.put("user.online", "Users online");
	    
	    // Chat room
	    lang.put("chat.room", "Chat");
	    lang.put("chat.users.in.room", "Users");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Document access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Document lock denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Version error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Prístup k dokumentu zakázaný");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Dokument nenájdený");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Dokument už existuje");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Uzamknutie dokumentu zakázané");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Požadované odomknutie dokumentu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Vnútorná chyba repozitára");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Vnútorná chyba aplikácie");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Cesta k dokumentu nenájdená");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Prístup k adresáru zakázaný");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Adresár nenájdený");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Adresár už existuje");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Vnútorná chyba repozitára");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Vnútorná chyba repozitára");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Cesta k adresáru nenájdená");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_DatabaseException, "Database error");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_AccessDenied, "Zakázaný prístup k položke");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_ItemNotFound, "Položka nenájdená");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_Repository, "Vnútorná chyba repozitára");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_General, "Vnútorná chyba repozitára");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Dokument nenájdený");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Vnútorná chyba repozitára");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Nepodporovaný formát súboru");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Dokument už existuje");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Prekročená maximálna veľkosť dokumentu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_DocumentNameMismatch, "Document name is diferent");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Sedenie ukončené");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Všeobecná chyba pri vykonaní dotazu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Meno uloženého dotazu musí byť unikátne");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Meno záložky musí byť unikátné");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Sedenie stratené");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Vnútorná chyba repozitára");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Vnútorná chyba repozitára");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Vstupno-výstupná chyba");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Vnútorná chyba repozitára");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error in browser configuration");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_QuotaExceed, "Error user quota exceed, contact with adminitrator");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_AccessDenied, "Document access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemNotFound, "Document not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemExists, "Document already exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Lock, "Document lock denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_PathNotFound, "Path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_AccessDenied, "Access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_General, "Application internal error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Lock, "Locked");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_PathNotFound, "Path not exist");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_AccessDenied, "Access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchGroup, "Group not exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchProperty, "Property not exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_PathNotFound, "Path not exist");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_AccessDenied, "Access denied");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_DatabaseException, "Database error");
	  }
}
