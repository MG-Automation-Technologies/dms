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

package es.git.openkm.frontend.client.lang;

import java.util.HashMap;

import es.git.openkm.frontend.client.config.ErrorCode;

/**
 * Hungarian
 * 
 * @author Zoltan Kovacs (werdy@freemail.hu)
 */
public class Lang_hu_HU {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "yyyy-MM-dd hh:mm:ss");
	    lang.put("general.hour.pattern", "hh:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Indul az OpenKM");
	    lang.put("startup.starting.loading", "OpenKM töltődik");
	    lang.put("startup.taxonomy", "Dokumentumok gyökérelem töltődik");
	    lang.put("startup.template", "Vázlat gyökérelem töltődik");
	    lang.put("startup.personal", "Személyes dokumentumok gyökérelem töltődik");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Kuka gyökérelem töltődik");
	    lang.put("startup.user.home", "Saját mappa gyökérelem töltődik");
	    lang.put("startup.bookmarks", "Könyvjelzők töltődnek");
	    lang.put("startup.loading.taxonomy", "Dokumentumok töltődnek");
	    lang.put("startup.loading.taxonomy.getting.folders", "Dokumentumok töltődnek - mappák töltődnek");
	    lang.put("startup.loading.taxonomy.eval.params", "Dokumentumok töltődnek - böngésző tulajdonságainak kiértékelése");
	    lang.put("startup.loading.taxonomy.open.path", "Dokumentumok töltődnek - útvonal megnyitása");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Dokumentumok töltődnek - mappák töltődnek");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Dokumentumok töltődnek - dokumentumok töltődnek");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Személyes dokumentumok töltődnek");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.templates", "Vázlatok töltődnek");
	    lang.put("startup.loading.trash", "Kuka töltődik");
	    lang.put("startup.loading.history.search", "Elmentett keresések töltődnek");
	    lang.put("startup.loading.user.values", "Felhasználói adatok töltődnek");
	    lang.put("startup.loading.property.group.translations", "Kategóriák fordításai töltődnek");
	    lang.put("startup.keep.alive", "Kapcsolatfenntartó töltődik");
	    
	    // Update notification
	    lang.put("openkm.update.available", "OpenKM frissítés érhető el");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Dokumentumok");
	    lang.put("leftpanel.label.trash", "Kuka");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Elmentett keresések");
	    lang.put("leftpanel.label.templates", "Vázlatok");
	    lang.put("leftpanel.label.my.documents", "Személyes dokumentumok");
	    lang.put("leftpanel.label.user.search", "Hírek");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Mappa létrehozása");
	    lang.put("tree.menu.directory.remove", "Törlés");
	    lang.put("tree.menu.directory.rename", "Átnevezés");
	    lang.put("tree.menu.directory.refresh", "Frissítés");
	    lang.put("tree.menu.directory.move", "Áthelyezés");
	    lang.put("tree.menu.directory.copy", "Másolás");
	    lang.put("tree.menu.directory.add.document", "Dokumentum feltöltése");
	    lang.put("tree.menu.add.bookmark", "Hozzáadás a könyvjelzőkhöz");
	    lang.put("tree.menu.set.home", "Beállítás mint saját mappa");
	    lang.put("tree.menu.export", "Exportálás fájlba");
	    lang.put("tree.status.refresh.folder", "Mappák frissítése...");
	    lang.put("tree.status.refresh.create", "Mappa létrehozása...");
	    lang.put("tree.status.refresh.delete", "Mappa törlése...");
	    lang.put("tree.status.refresh.rename", "Mappa átnevezése...");
	    lang.put("tree.status.refresh.restore", "Mappa visszaállítása...");
	    lang.put("tree.status.refresh.purge", "Mappa tartalmának törlése...");
	    lang.put("tree.status.refresh.get", "Mappa frissítése...");
	    lang.put("tree.folder.new", "Új mappa");
	    lang.put("tree.status.refresh.add.subscription", "Feliratkozás értesítésre...");
	    lang.put("tree.status.refresh.remove.subscription", "Értesítés lemondása...");
	    lang.put("tree.status.refresh.get.root", "Gyökér mappa frissítése...");
	    lang.put("tree.status.refresh.get.user.home", "Saját mappa frissítése...");
	    lang.put("tree.status.refresh.purge.trash", "Kuka ürítése...");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Visszaállítás");
	    lang.put("trash.menu.directory.purge", "Eltávolítás");
	    lang.put("trash.menu.directory.purge.trash", "Kuka ürítése");
	    lang.put("trash.directory.select.label", "Célmappa kiválasztása");
	    
	    // General menu
	    lang.put("general.menu.file", "Fájl");
	    	lang.put("general.menu.file.exit", "Kilépés");
	    	lang.put("general.menu.file.purge.trash", "Kuka ürítése");
	    lang.put("general.menu.edit", "Szerkesztés");
			lang.put("general.menu.file.create.directory", "Mappa létrehozása");
			lang.put("general.menu.file.download.document", "Dokumentum letöltése");
			lang.put("general.menu.file.download.document.pdf", "Dokumentum letöltése PDF-ként");
			lang.put("general.menu.file.send.link", "Hivatkozás küldése");
			lang.put("general.menu.file.lock", "Zárolás");
			lang.put("general.menu.file.unlock", "Feloldás");
			lang.put("general.menu.file.add.document", "Dokumentum feltöltése");
			lang.put("general.menu.file.checkout", "Módosításra letöltés");
			lang.put("general.menu.file.checkin", "Módosított dokumentum feltöltése");
			lang.put("general.menu.file.cancel.checkout", "Módosítás elvetése");
			lang.put("general.menu.file.delete", "Törlés");
			lang.put("general.menu.file.refresh", "Frissítés");
	    lang.put("general.menu.tools", "Eszközök");
	    	lang.put("general.menu.tools.languages", "Nyelvek");
	    	lang.put("general.menu.tools.skin", "Kinézet");
    			lang.put("general.menu.tools.skin.default", "Alapértelmezett");
    			lang.put("general.menu.tools.skin.default2", "Alapértelmezett 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Közepes betűméret");
    			lang.put("general.menu.tools.skin.bigfont", "Nagy betűméret");
    		lang.put("general.menu.debug.console", "Hibakereső konzol");
    		lang.put("general.menu.administration", "Adminisztráció");
    		lang.put("general.menu.tools.preferences", "Prefererences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Könyvjelzők");
	    	lang.put("general.menu.bookmark.home", "Saját mappa");
	    	lang.put("general.menu.bookmark.default.home", "Beállítás mint saját mappa");
	    	lang.put("general.menu.bookmark.edit", "Könyvjelzők szerkesztése");
	    lang.put("general.menu.help", "Segítség");
		    lang.put("general.menu.bug.report", "Hibajelentés küldése");
		    lang.put("general.menu.support.request", "Támogatás kérése");
		    lang.put("general.menu.public.forum", "Nyilvános fórum");
		    lang.put("general.menu.project.web", "Honlap");
		    lang.put("general.menu.version.changes", "Változások a verziók között");
		    lang.put("general.menu.documentation", "Dokumentáció");
		    lang.put("general.menu.about", "Névjegy");
	    lang.put("general.connected", "Belépve mint");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Név");
	    lang.put("filebrowser.date.update", "Frissítve");
	    lang.put("filebrowser.size", "Méret");
	    lang.put("filebrowser.path", "Útvonal");
	    lang.put("filebrowser.author", "Tulajdonos");
	    lang.put("filebrowser.version", "Verzió");
	    lang.put("filebrowser.menu.checkout", "Módosításra letöltés");
	    lang.put("filebrowser.menu.checkin", "Módosított dokumentum feltöltése");
	    lang.put("filebrowser.menu.delete", "Törlés");
	    lang.put("filebrowser.menu.rename", "Átnevezés");
	    lang.put("filebrowser.menu.checkout.cancel", "Módosítás elvetése");
	    lang.put("filebrowser.menu.lock", "Zárolás");
	    lang.put("filebrowser.menu.unlock", "Feloldás");
	    lang.put("filebrowser.menu.download", "Letöltés");
	    lang.put("filebrowser.menu.move", "Áthelyezés");
	    lang.put("filebrowser.menu.copy", "Másolás");
	    lang.put("filebrowser.menu.create.from.template", "Létrehozás vázlat alapján");
	    lang.put("filebrowser.menu.add.property.group", "Kategória hozzáadása");
	    lang.put("filebrowser.menu.remove.property.group", "Kategória törlése");
	    lang.put("filebrowser.menu.start.workflow", "Munkafolyamat indítása");
	    lang.put("filebrowser.menu.add.subscription", "Értesítés kérése a változásokról");
	    lang.put("filebrowser.menu.remove.subscription", "Értesítés lemondása a változásokról");
	    lang.put("filebrowser.menu.add.bookmark", "Hozzáadás a könyvjelzőkhöz");
	    lang.put("filebrowser.menu.set.home", "Beállítás mint saját mappa");
	    lang.put("filebrowser.menu.refresh", "Frissítés");
	    lang.put("filebrowser.menu.export", "Exportálás tömörített (ZIP) fájlba");
	    lang.put("filebrowser.menu.play", "Lejátszás");
	    lang.put("filebrowser.menu.image.viewer", "Képnéző");
	    lang.put("filebrowser.status.refresh.folder", "Mappalista frissítése...");
	    lang.put("filebrowser.status.refresh.document", "Dokumentumlista frissítése...");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Mappa törlése...");
	    lang.put("filebrowser.status.refresh.delete.document", "Dokumentum törlése...");
	    lang.put("filebrowser.status.refresh.checkout", "Dokumentum letöltése módosításra...");
	    lang.put("filebrowser.status.refresh.lock", "Dokumentum zárolása...");
	    lang.put("filebrowser.status.refresh.unlock", "Dokumentum feloldása...");
	    lang.put("filebrowser.status.refresh.document.rename", "Dokumentum átnevezése...");
	    lang.put("filebrowser.status.refresh.folder.rename", "Mappa átnevezése...");
	    lang.put("filebrowser.status.refresh.document.purge", "Dokumentum eltávolítása...");
	    lang.put("filebrowser.status.refresh.folder.purge", "Mappa eltávolítása...");
	    lang.put("filebrowser.status.refresh.folder.get", "Mappa frissítése...");
	    lang.put("filebrowser.status.refresh.document.get", "Dokumentum frissítése...");
	    lang.put("filebrowser.status.refresh.add.subscription", "Feliratkozás...");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Leiratkozás...");
	    lang.put("filebrowser.status.refresh.get.user.home", "Saját mappa frissítése...");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Feltöltés");
	    lang.put("fileupload.status.sending", "Fájl feltöltése...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "A fájl feltöltése sikeresen befejeződött");
	    lang.put("fileupload.upload.status", "A feltöltés állapota");
	    lang.put("fileupload.upload.uploaded", "Feltöltve");
	    lang.put("fileupload.upload.completed", "A feltöltés befejeződött");
	    lang.put("fileupload.upload.runtime", "Futásidő");
	    lang.put("fileupload.upload.remaining", "Hátralévő");
	    lang.put("fileupload.button.close", "Bezárás");
	    lang.put("fileupload.button.add.other.file", "Újabb fájl feltöltése");
	    lang.put("fileupload.status.move.file", "Fájl áthelyezése...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Fájl másolása...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "Fájl visszaállítása...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Mappa áthelyezése...");
	    lang.put("fileupload.status.copy.folder", "Mappa másolása...");
	    lang.put("fileupload.status.restore.folder", "Mappa visszaállítása...");
	    lang.put("fileupload.status.create.from.template", "Fájl létrehozása vázlatból...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Dokumentum feltöltése");
	    lang.put("fileupload.label.update", "Dokumentumok frissítése");
	    lang.put("fileupload.label.users.notify", "Felhasználók értesítése");
	    lang.put("fileupload.label.comment", "Megjegyzés");
	    lang.put("fileupload.label.users.to.notify",  "Értesített felhasználók");
	    lang.put("fileupload.label.users",  "Felhasználók");
	    lang.put("fileupload.label.must.select.users",  "Legalább egy felhasználót ki kell választani az értesítéshez");
	    lang.put("fileupload.label.importZip", "Dokumentum importálása tömörített (ZIP) fájlból");
	    lang.put("fileupload.label.notify.comment", "Értesítő üzenet");
	    lang.put("fileupload.label.error.importing.zip", "Hiba a fájl importálása közben");
	    lang.put("fileupload.label.error.move.file", "Hiba a fájl áthelyezése közben");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Hiba a fájl másolása közben");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "Hiba a fájl visszaállítása közben");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Hiba a mappa áthelyezése közben");
	    lang.put("fileupload.label.error.copy.folder", "Hiba a mappa másolása közben");
	    lang.put("fileupload.label.error.restore.folder", "Hiba a mappa visszaállítása közben");
	    lang.put("fileupload.label.error.create.from.template", "Hiba a fájl létrehozása közben");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "A célmappa nem lehet önmaga vagy önmagán belül");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "A célmappa nem lehet azonos a forrásmappával");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "A célmappa nem lehet azonos a forrásmappával");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Tulajdonságok");
	    lang.put("tab.document.notes", "Jegyzetek");
	    lang.put("tab.document.history", "Verziók");
	    lang.put("tab.document.status.history", "Verziók frissítése...");
	    lang.put("tab.status.security.role", "Biztonság - szabályok frissítése...");
	    lang.put("tab.status.security.user", "Biztonság - felhasználók frissítése...");
	    lang.put("tab.document.status.group.properties", "Kategóriák frissítése...");
	    lang.put("tab.document.status.set.keywords", "Kulcsszavak beállítása...");
	    lang.put("tab.document.status.get.version.history.size", "Verziók méretének frissítése...");
	    lang.put("tab.document.status.purge.version.history", "Verziók eltávolítása...");
	    lang.put("tab.document.status.restore.version", "Verzió visszaállítása...");
	    lang.put("tab.document.security", "Biztonság");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Tulajdonságok");
	    lang.put("tab.folder.security", "Biztonság");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Fájlkezelő");
	    lang.put("tab.workspace.search", "Keresés");
	    lang.put("tab.workspace.dashboard", "Asztal");
	    lang.put("tab.workspace.administration", "Adminisztráció");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Név");
	    lang.put("document.folder", "Mappa");
	    lang.put("document.size", "Méret");
	    lang.put("document.created", "Létrehozva");
	    lang.put("document.lastmodified", "Módosítva");
	    lang.put("document.mimetype", "MIME típus");
	    lang.put("document.keywords", "Kulcsszavak");
	    lang.put("document.by", "");
	    lang.put("document.status", "Státusz");
	    lang.put("document.status.checkout", "Módosítja");
	    lang.put("document.status.locked", "Zárolta");
	    lang.put("document.status.normal", "Elérhető");
	    lang.put("document.subscribed", "Változásokról értesítés");
	    lang.put("document.subscribed.yes", "Igen");
	    lang.put("document.subscribed.no", "Nem");
	    lang.put("document.history.size", "Verziók mérete");
	    lang.put("document.subscribed.users", "Változásokról értesített felhasználok");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Jegyzet írása");
	    lang.put("document.keywords.cloud", "Keywords cloud");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Név");
	    lang.put("folder.parent", "Szülő");
	    lang.put("folder.created", "Létrehozva");
	    lang.put("folder.by", "által");
	    lang.put("folder.subscribed", "Változásokról értesítés");
	    lang.put("folder.subscribed.yes", "Igen");
	    lang.put("folder.subscribed.no", "Nem");
	    lang.put("folder.subscribed.users", "Változásokról értesített felhasználók");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Verzió");
	    lang.put("version.created", "Dátum");
	    lang.put("version.author", "Tulajdonos");
	    lang.put("version.size", "Méret");
	    lang.put("version.purge.document", "Verziók eltávolítása");
	    lang.put("version.comment", "Megjegyzés");
	    
	    // Security
	    lang.put("security.label", "Biztonság");
	    lang.put("security.group.name", "Csoport");
	    lang.put("security.group.permission.read", "Olvasható");
	    lang.put("security.group.permission.write", "Módosítható");
	    lang.put("security.user.name", "Felhasználó");
	    lang.put("security.user.permission.read", "Olvasható");
	    lang.put("security.user.permission.write", "Módosítható");
	    lang.put("security.users", "Felhasználók");
	    lang.put("security.groups", "Csoportok");
	    lang.put("security.recursive", "Jogosultságok rekurzív módosítása");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "Rendszerhiba");
	    lang.put("error.invocation", "Hiba a szerverrel való kommunikáció közben");
	    
	    // About
	    lang.put("about.label", "Névjegy");
	    lang.put("about.loading", "Töltés ...");
	    
	    // Logout
	    lang.put("logout.label", "Kilépés");
	    lang.put("logout.closed", "Az OpenKM futása sikeresen befejeződött.");
	    lang.put("logout.logout", "Kilépés az OpenKM-ből, kérem várjon");
	    
	    // Confirm
	    lang.put("confirm.label", "Megerősítés");
	    lang.put("confirm.delete.folder", "¿ Biztos, hogy törölni akarja a mappát ?");
	    lang.put("confirm.delete.document", "¿ Biztos, hogy törölni akarja a dokumentumot ?");
	    lang.put("confirm.delete.trash", "¿ Biztos, hogy öríteni akarja a kukát ?");
	    lang.put("confirm.purge.folder", "¿ Biztos, hogy véglegesen eltávolítja a mappát ?");
	    lang.put("confirm.purge.document", "¿ Biztos, hogy véglegesen eltávolítja a dokumentumot ?");
	    lang.put("confirm.delete.propety.group", "¿ Biztos, hogy törölni akarja a kategóriát ?");
	    lang.put("confirm.purge.version.history.document", "¿ Biztos, hogy törölni akarja az előző verziókat ?");
	    lang.put("confirm.purge.restore.document", "¿ Biztos, hogy visszaállítja a dokumentumot a kiválasztott verzióra ?");
	    lang.put("confirm.set.default.home", "¿ Biztos, hogy beállítja a saját mappát ?");
	    lang.put("confirm.delete.saved.search", "¿ Biztos, hogy törölni akarja a keresést ?");
	    lang.put("confirm.delete.user.news", "¿ Biztos, hogy törölni akarja a hírt ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    
	    // Search inputs
	    lang.put("search.context", "Hely");
	    lang.put("search.content", "Tartalom");
	    lang.put("search.name", "Név");
	    lang.put("search.keywords", "Kulcsszavak");
	    lang.put("search.folder", "Folder");
	    lang.put("search.results", "Találatok");
	    lang.put("search.to", "-");
	    lang.put("search.page.results", "Találat / oldal");
	    lang.put("search.add.property.group", "Tulajdonság hozzáadása");
	    lang.put("search.mimetype", "MIME típus");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Részletes keresés");
	    lang.put("search.user", "Felhasználó");
	    lang.put("search.date.and", "-");
	    lang.put("search.date.range", "Dátum (tól-ig)");
	    lang.put("search.save.as.news", "Mentés mint felhasználói hír");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    
	    // Search results
	    lang.put("search.result.name", "Név");
	    lang.put("search.result.score", "Előfordulás");
	    lang.put("search.result.size", "Méret");
	    lang.put("search.result.date.update", "Frissítve");
	    lang.put("search.result.author", "Tulajdonos");
	    lang.put("search.result.version", "Verzió");
	    lang.put("search.result.path", "Útvonal");
	    lang.put("search.result.menu.download", "Letöltés");
	    lang.put("search.result.menu.go.folder", "Mappa mutatása");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Keresés frissítése...");
	    lang.put("search.result.status.runsearch", "Elmentett keresés frissítése...");
	    
	    // Search saved
	    lang.put("search.saved.run", "Futtatás");
	    lang.put("search.saved.delete", "Törlés");
	    lang.put("search.saved.status.getsearchs", "Elmentett keresések frissítése...");
	    lang.put("search.saved.status.savesearch", "Keresés mentése...");
	    lang.put("search.saved.status.deletesearch", "Elmentett keresés törlése...");
	    lang.put("search.saved.status.getusernews", "Felhasználói hírek frissítése...");
	    
	    // Button
	    lang.put("button.close", "Bezárás");
	    lang.put("button.logout", "Kijelentkezés");
	    lang.put("button.update", "Módosítás");
	    lang.put("button.cancel", "Mégsem");
	    lang.put("button.accept", "Elfogadás");
	    lang.put("button.restore", "Visszaállítás");
	    lang.put("button.move", "Áthelyezés");
	    lang.put("button.change", "Változtatás");
	    lang.put("button.search", "Keresés");
	    lang.put("button.save.search", "Keresés mentése");
	    lang.put("button.view", "Megtekintés");
	    lang.put("button.clean", "Törlés");
	    lang.put("button.add", "Hozzáadás");
	    lang.put("button.delete", "Törlés");
	    lang.put("button.copy", "Másolás");
	    lang.put("button.create", "Létrehozás");
	    lang.put("button.show", "Mutatás");
	    lang.put("button.memory", "Frissítés");
	    lang.put("button.copy.clipboard", "Vágolapra másolás");	
	    lang.put("button.start", "Indítás");
	    lang.put("button.select", "Select");
	    
	    // Group
	    lang.put("group.label", "Tulajdonság hozzáadása");
	    lang.put("group.group", "Kategória");
	    lang.put("group.property.group", "Tulajdonság");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Hozzáadás a könyvjelzőkhöz");
	    lang.put("bookmark.name", "Név");
	    lang.put("bookmark.edit.label", "Könyvjelzők szerkesztése");
	    lang.put("bookmark.path", "Útvonal");
	    lang.put("bookmark.type", "Típus");
	    lang.put("bookmark.type.document", "Dokumentum");
	    lang.put("bookmark.type.folder", "Mappa");
	    
	    // Notify
	    lang.put("notify.label", "Hivakozás küldése");
	    
	    // Status
	    lang.put("status.document.copied", "Dokumentum másolásra kijelölve");
	    lang.put("status.document.cut", "Dokumentum kivágásra kijelölve");
	    lang.put("status.folder.copied", "Mappa másolásra kijelölve");
	    lang.put("status.folder.cut", "Mappa kivágásra kijelölve");
	    lang.put("status.keep.alive.error", "Kapcsolat megszakadt a szerverrel (kapcsolatfenntartás)");
	    lang.put("status.debug.enabled", "Hibakeresés bekapcsolva");
	    lang.put("status.debug.disabled", "Hibakeresés kikapcsolva");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Vasárnap");
	    lang.put("calendar.day.monday", "Hétfő");
	    lang.put("calendar.day.tuesday", "Kedd");
	    lang.put("calendar.day.wednesday", "Szerda");
	    lang.put("calendar.day.thursday", "Csütörtök");
	    lang.put("calendar.day.friday", "Péntek");
	    lang.put("calendar.day.saturday", "Szombat");
	    lang.put("calendar.month.january", "Január");
	    lang.put("calendar.month.february", "Február");
	    lang.put("calendar.month.march", "Március");
	    lang.put("calendar.month.april", "Április");
	    lang.put("calendar.month.may", "Május");
	    lang.put("calendar.month.june", "Június");
	    lang.put("calendar.month.july", "Július");
	    lang.put("calendar.month.august", "Augusztus");
	    lang.put("calendar.month.september", "Szeptember");
	    lang.put("calendar.month.october", "Október");
	    lang.put("calendar.month.november", "November");
	    lang.put("calendar.month.december", "December");
	    
	    // Media player
	    lang.put("media.player.label", "Médialejátszó");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Képnéző");
	    lang.put("image.viewer.zoom.in", "Nagyítás");
	    lang.put("image.viewer.zoom.out", "Kicsinyítés");
	    
	    // Debug console
	    lang.put("debug.console.label", "Hibakereső konzol");
	    lang.put("debug.enable.disable", "A CTRL+Z billenytűk lenyomásával lehet a hibakeresést be- és kikapcsolni");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Általános");
	    lang.put("dashboard.tab.news", "Hírek");
	    lang.put("dashboard.tab.user", "Felhasználó");
	    lang.put("dashboard.tab.workflow", "Munkafolyamat");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Keyword map");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Új");
	    lang.put("dashboard.user.locked.documents", "Zárolt dokumentumok");
	    lang.put("dashboard.user.checkout.documents", "Módosítás alatt álló dokumentumok");
	    lang.put("dashboard.user.last.modified.documents", "Legutóbb módosított dokumentumok");
	    lang.put("dashboard.user.last.downloaded.documents", "Legutóbb letöltött dokumentumok");
	    lang.put("dashboard.user.subscribed.documents", "Figyelemmel kísért dokumentumok");
	    lang.put("dashboard.user.subscribed.folders", "Figyelemmel kísért mappák");
	    lang.put("dashboard.user.last.uploaded.documents", "Legutóbb feltöltött dokumentumok");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Előző héten legtöbbet letöltött dokumentumok");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Előző hónapban legtöbbet letöltött dokumentumok");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Előző héten legtöbbet módosított dokumentumok");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Előző héten legtöbbet nézett dokumentumok");
	    lang.put("dashboard.general.last.uploaded.documents", "Legutóbb feltöltött dokumentumok");
	    lang.put("dashboard.workflow.pending.tasks", "Függőben lévő feladatok");
	    lang.put("dashboard.workflow.task", "Feladat");
	    lang.put("dashboard.workflow.task.id", "Azonosító");
	    lang.put("dashboard.workflow.task.name", "Név");
	    lang.put("dashboard.workflow.task.created", "Létrehozás dátuma");
	    lang.put("dashboard.workflow.task.start", "Kezdés dátuma");
	    lang.put("dashboard.workflow.task.duedate", "Lejárat dátuma");
	    lang.put("dashboard.workflow.task.end", "Befejezés dátuma");
	    lang.put("dashboard.workflow.task.description", "Leírás");
	    lang.put("dashboard.workflow.task.process.instance", "Eljárás");
	    lang.put("dashboard.workflow.task.process.id", "Azonosító");
	    lang.put("dashboard.workflow.task.process.version", "Verzió");
	    lang.put("dashboard.workflow.task.process.name", "Név");
	    lang.put("dashboard.workflow.task.process.description", "Leírás");
	    lang.put("dashboard.workflow.task.process.data", "Adat");
	    lang.put("dashboard.workflow.task.process.definition", "Művelet");
	    lang.put("dashboard.workflow.task.process.path", "Útvonal");
	    lang.put("dashboard.refreshing", "Frissítés");
	    lang.put("dashboard.keyword", "Keywords");
	    lang.put("dashboard.keyword.suggest", "Type the keyword");
	    lang.put("dashboard.keyword.all", "All keywords");
	    lang.put("dashboard.keyword.top", "Top keywords");
	    lang.put("dashboard.keyword.related", "Related keywords");
	    lang.put("dashboard.keyword.goto.document", "Goto document");
	    lang.put("dashboard.keyword.clean.keywords", "Clean keywords");
	    lang.put("dashboard.mail.last.imported.mails", "Electronic mails");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Attachments");
	    
	    // Workflow
	    lang.put("workflow.label", "Munkafolyamat indítása");
	    
	    // User configuration
	    lang.put("user.preferences.label", "User configuration");
	    lang.put("user.preferences.user", "User");
	    lang.put("user.preferences.password", "Password");
	    lang.put("user.preferences.imap.host", "IMAP server");
	    lang.put("user.preferences.imap.user", "IMAP user name");
	    lang.put("user.preferences.imap.user.password", "IMAP user password");
	    lang.put("user.preferences.imap.folder", "IMAP folder");
	    lang.put("user.preferences.password.error", "Error: passwords are diferents");
	    lang.put("user.preferences.user.data", "User account");
	    lang.put("user.preferences.mail.data", "Mail account");
	    lang.put("user.preferences.imap.error", "All fields are obligatory to set the mail configurations");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "A dokumentum nem hozzáférhető");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "A dokumentum nem található");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Már létezik dokumentum ilyen néven");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "A dokumentum nem zárolható");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "A dokumentum zárolását fel kell oldani");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Tárhely belső hiba");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Program belső hiba");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Az útvonal nem található");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "A mappa nem hozzáférhető");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "A mappa nem található");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Már létezik mappa ilyen néven");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Tárhely belső hiba");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Program belső hiba");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Az útvonal nem található");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "A kért elem nem hozzáférhető");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "A kért elem nem található");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Tárhely belső hiba");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Program belső hiba");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "A dokumentum nem található");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Program belső hiba");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Nem támogatott fájlformátum");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Már létezik dokumentum ilyen néven");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "A dokumentum mérete nagyobb mint a megengedett");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Lezárul a munkamenet");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Hiba a lekérdezés során");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Már van elmentve keresés ilyen néven");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Már létezik könyvjelző ilyen néven");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Elveszett a munkamenet");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Tárhely belső hiba");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Tárhely belső hiba");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "IO hiba");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Tárhely belső hiba");
	  }
}
