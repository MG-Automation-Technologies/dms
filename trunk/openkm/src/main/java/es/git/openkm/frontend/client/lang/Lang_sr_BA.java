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
 * Servian translations
 *
 * @author Božidar Roljić
 */
public class Lang_sr_BA {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "yyyy-MM-dd hh:mm:ss");
	    lang.put("general.day.pattern", "yyyy-MM-dd");
	    lang.put("general.hour.pattern", "hh:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Učitavanje OpenKM-a");
	    lang.put("startup.starting.loading", "Početak učitavanja OpenKM-a");
	    lang.put("startup.taxonomy", "Pokretanje javnix dokumenta");
	    lang.put("startup.template", "Pokretanje šablona");
	    lang.put("startup.personal", "Pokretanje mojih dokumenata");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Pokretanje kante za smeće");
	    lang.put("startup.user.home", "Pokretanje početne");
	    lang.put("startup.bookmarks", "Pokretanje oznaka");
	    lang.put("startup.loading.taxonomy", "Učitavanje javnix dokumenta");
	    lang.put("startup.loading.taxonomy.getting.folders", "Učitavanje javnix dokumenta - pokretanje foldera");
	    lang.put("startup.loading.taxonomy.eval.params", "Učitavanje javnix dokumenta - procena browser parametara");
	    lang.put("startup.loading.taxonomy.open.path", "Učitavanje javnix dokumenta - otvaranje putanje");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Učitavanje javnix dokumenta - pokretanje foldera");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Učitavanje javnix dokumenta - pokretanje dokumenata");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Učitavanje mojih dokumenata");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.templates", "Učitavanje šema");
	    lang.put("startup.loading.trash", "Učitavanje kante za smeće");
	    lang.put("startup.loading.history.search", "Učitavanje pretrage");
	    lang.put("startup.loading.user.values", "Učitavanje parametara korisnika");
	    lang.put("startup.loading.property.group.translations", "Učitavanje parametara grupe");
	    lang.put("startup.keep.alive", "Učitavanje keep alive");
	    
//	  Update notification
	    lang.put("openkm.update.available", "Nova verzija OpenKM-a");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Javna dokumenta");
	    lang.put("leftpanel.label.trash", "Kanta za smeće");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Sačuvane pretrage");
	    lang.put("leftpanel.label.templates", "Šabloni");
	    lang.put("leftpanel.label.my.documents", "Moja dokumenta");
	    lang.put("leftpanel.label.user.search", "User news");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Napravi folder");
	    lang.put("tree.menu.directory.remove", "Izbriši");
	    lang.put("tree.menu.directory.rename", "Preimenuj");
	    lang.put("tree.menu.directory.refresh", "Osvježi");
	    lang.put("tree.menu.directory.move", "Premjesti");
	    lang.put("tree.menu.directory.copy", "Kopiraj");
	    lang.put("tree.menu.directory.add.document", "Dodaj dokument");
	    lang.put("tree.menu.add.bookmark", "Dodaj oznaku");
	    lang.put("tree.menu.set.home", "Podesi početni folder");
	    lang.put("tree.menu.export", "Eksportuj u fajl");
	    lang.put("tree.status.refresh.folder", "Ažuriraj mapu foldera");
	    lang.put("tree.status.refresh.create", "Napravi folder");
	    lang.put("tree.status.refresh.delete", "Briši folder");
	    lang.put("tree.status.refresh.rename", "Preimenuj folder");
	    lang.put("tree.status.refresh.restore", "Obnovi folder");
	    lang.put("tree.status.refresh.purge", "Očisti folder");
	    lang.put("tree.status.refresh.get", "Ažuriraj folder");
	    lang.put("tree.folder.new", "Novi folder");
	    lang.put("tree.status.refresh.add.subscription","Potpisivanje");
	    lang.put("tree.status.refresh.remove.subscription", "Brisanje potpisa");
	    lang.put("tree.status.refresh.get.root", "Osvježi glavni folder");
	    lang.put("tree.status.refresh.get.user.home", "Pokretanje početnog foldera");
	    lang.put("tree.status.refresh.purge.trash", "Očisti kantu");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Obnovi");
	    lang.put("trash.menu.directory.purge", "Očisti");
	    lang.put("trash.menu.directory.purge.trash", "Očisti kantu");
	    lang.put("trash.directory.select.label", "Odaberi odredišni folder");
	    
	    // General menu
	    lang.put("general.menu.file", "Fajl");
	    	lang.put("general.menu.file.exit", "Izlaz");
	    	lang.put("general.menu.file.purge.trash", "Očisti smeće");
	    lang.put("general.menu.edit", "Izmjeni");
			lang.put("general.menu.file.create.directory", "Napravi folder");
			lang.put("general.menu.file.download.document", "Preuzmi dokument");
			lang.put("general.menu.file.download.document.pdf", "Download document as PDF");
			lang.put("general.menu.file.send.link", "Pošalji link");
			lang.put("general.menu.file.lock", "Zaključaj");
			lang.put("general.menu.file.unlock", "Otključaj");
			lang.put("general.menu.file.add.document", "Dodaj dokument");
			lang.put("general.menu.file.checkout", "Odjava");
			lang.put("general.menu.file.checkin", "Prijava");
			lang.put("general.menu.file.cancel.checkout", "Otkaži odjavu");
			lang.put("general.menu.file.delete", "Briši");
			lang.put("general.menu.file.refresh", "Osvježi");
	    lang.put("general.menu.tools", "Alati");
	    	lang.put("general.menu.tools.languages", "Jezici");
	    	lang.put("general.menu.tools.skin", "Tema");
    			lang.put("general.menu.tools.skin.default", "Podrazumijevani");
    			lang.put("general.menu.tools.skin.default2", "Podrazumijevani 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Normalan font");
    			lang.put("general.menu.tools.skin.bigfont", "Veliki font");
    		lang.put("general.menu.debug.console", "Debaguj consolu");
    		lang.put("general.menu.administration", "Administracija");
    		lang.put("general.menu.tools.preferences", "Prefererences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Oznake");
	    	lang.put("general.menu.bookmark.home", "Početni");
	    	lang.put("general.menu.bookmark.default.home", "Podesi podrazumjevani početni");
	    	lang.put("general.menu.bookmark.edit", "Izmjeni oznake");
	    lang.put("general.menu.help", "Pomoć");
		    lang.put("general.menu.bug.report", "Izvjestaj o greškama");
		    lang.put("general.menu.support.request", "Zahtjev za podrsku");
		    lang.put("general.menu.public.forum", "Javni forum");
		    lang.put("general.menu.project.web", "Projekt web");
		    lang.put("general.menu.version.changes", "Opis verzije");
		    lang.put("general.menu.documentation", "Dokumentacija");
		    lang.put("general.menu.about", "O, OpenKM-u");
	    lang.put("general.connected", "Kontakt");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Naziv");
	    lang.put("filebrowser.date.update", "Ažuriraj datum");
	    lang.put("filebrowser.size", "Veličina");
	    lang.put("filebrowser.path", "Putanja");
	    lang.put("filebrowser.author", "Autor");
	    lang.put("filebrowser.version", "Verzija");
	    lang.put("filebrowser.menu.checkout", "Odjava");
	    lang.put("filebrowser.menu.checkin", "Prijava");
	    lang.put("filebrowser.menu.delete", "Obriši");
	    lang.put("filebrowser.menu.rename", "Preimenuj");
	    lang.put("filebrowser.menu.checkout.cancel", "Otkaži odjavu");
	    lang.put("filebrowser.menu.lock", "Zaključaj");
	    lang.put("filebrowser.menu.unlock", "Otključaj");
	    lang.put("filebrowser.menu.download", "Preuzmi");
	    lang.put("filebrowser.menu.move", "Premjesti");
	    lang.put("filebrowser.menu.copy", "Kopiraj");
	    lang.put("filebrowser.menu.create.from.template", "Kreiraj iz šablona");
	    lang.put("filebrowser.menu.add.property.group", "Dodaj grupu svojstava");
	    lang.put("filebrowser.menu.remove.property.group", "Ukloni grupu svojstava");
	    lang.put("filebrowser.menu.start.workflow", "Start workflow");
	    lang.put("filebrowser.menu.add.subscription", "Potpiši");
	    lang.put("filebrowser.menu.remove.subscription", "Ukloni potpis");
	    lang.put("filebrowser.menu.add.bookmark", "Dodaj oznaku");
	    lang.put("filebrowser.menu.set.home", "Postavi podrazumijevanu početnu");
	    lang.put("filebrowser.menu.refresh", "Osvježi prikaz");
	    lang.put("filebrowser.menu.export", "Izvezi u ZIP");
	    lang.put("filebrowser.menu.play", "Play");
	    lang.put("filebrowser.menu.image.viewer", "Image viewer");
	    lang.put("filebrowser.status.refresh.folder", "Ažuriranje liste foldera");
	    lang.put("filebrowser.status.refresh.document", "Ažuriranje liste dokumenata");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Brisanje foldera");
	    lang.put("filebrowser.status.refresh.delete.document", "Brisanje dokumenta");
	    lang.put("filebrowser.status.refresh.checkout", "Odjava dokumenta");
	    lang.put("filebrowser.status.refresh.lock", "Zaključavanje dokumenta");
	    lang.put("filebrowser.status.refresh.unlock", "Otključavanje dokumenta");
	    lang.put("filebrowser.status.refresh.document.rename", "Preimenovanje dokumenta");
	    lang.put("filebrowser.status.refresh.folder.rename", "Preimenovanje foldera");
	    lang.put("filebrowser.status.refresh.document.purge", "Brisanje dokumenta");
	    lang.put("filebrowser.status.refresh.folder.purge", "Brisanje foldera");
	    lang.put("filebrowser.status.refresh.folder.get", "Ažuriranje foldera");
	    lang.put("filebrowser.status.refresh.document.get", "Ažuriranje dokumenta");
	    lang.put("filebrowser.status.refresh.add.subscription","Potpisivanje");
	    lang.put("filebrowser.status.refresh.remove.subscription","Brisanje potpisa");
	    lang.put("filebrowser.status.refresh.get.user.home", "Pokretanje početnog foldera");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Pošalji");
	    lang.put("fileupload.status.sending", "Slanje fajla ...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "Fajl je korektno poslan");
	    lang.put("fileupload.upload.status", "Status slanja");
	    lang.put("fileupload.upload.uploaded", "Fajl poslan");
	    lang.put("fileupload.upload.completed", "Slanje fajla kompletirano");
	    lang.put("fileupload.upload.runtime", "Vrijeme izvršavanja");
	    lang.put("fileupload.upload.remaining", "Preostalo");
	    lang.put("fileupload.button.close", "Zatvori");
	    lang.put("fileupload.button.add.other.file", "Dodaj drugi fajl");
	    lang.put("fileupload.status.move.file", "Premjesti fajl...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Kopiraj fajl...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "Obnovi fajl...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Premještanje foldera...");
	    lang.put("fileupload.status.copy.folder", "Kopiranje foldera...");
	    lang.put("fileupload.status.restore.folder", "Obnavljanje foldera...");
	    lang.put("fileupload.status.create.from.template", "Kreiraj fajl iz šablona...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Dodaj nova dokumenta");
	    lang.put("fileupload.label.update", "Ažuriraj dokumenta");
	    lang.put("fileupload.label.users.notify", "Obavjesti korisnike");
	    lang.put("fileupload.label.comment", "Komentar");
	    lang.put("fileupload.label.users.to.notify",  "Korisnici su obavješteni");
	    lang.put("fileupload.label.users",  "Korisnici");
	    lang.put("fileupload.label.must.select.users",  "Morate selektovati nekog korisnika");
	    lang.put("fileupload.label.importZip", "Import Documents from ZIP");
	    lang.put("fileupload.label.notify.comment", "Obavještajna poruka");
	    lang.put("fileupload.label.error.importing.zip", "Greška u uvoženju fajla");
	    lang.put("fileupload.label.error.move.file", "Greška u premještanju fajla");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Greška u kopiranju fajla");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "Greška u obnavljanju fajla");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Greška u premještanju foldera");
	    lang.put("fileupload.label.error.copy.folder", "Greška u kopiranju foldera");
	    lang.put("fileupload.label.error.restore.folder", "Greška obnavljanju foldera");
	    lang.put("fileupload.label.error.create.from.template", "Greška kreiranja fajla iz šablona");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Nije dozvoljeno premještanje");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Nije dozvoljeno premještanje u izvorni folder");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Nije dozvoljeno kreirati fajl u izvornom folderu");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Svojstva");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "Istorija");
	    lang.put("tab.document.status.history", "Osvježi istoriju");
	    lang.put("tab.status.security.role", "Ažuriranje sigurnosnih pravila");
	    lang.put("tab.status.security.user", "Ažuriranje sigurnosti korisnika");
	    lang.put("tab.document.status.group.properties", "Ažuriranje svojstava grupe");
	    lang.put("tab.document.status.set.keywords", "Podešavanje ključnih riječi");
	    lang.put("tab.document.status.get.version.history.size", "Osvežavanje veličine istorije dokumenata");
	    lang.put("tab.document.status.purge.version.history", "Zbijanje istorije dokumenata");
	    lang.put("tab.document.status.restore.version", "Obnavljanje verzije dokumenata");
	    lang.put("tab.document.security", "Sigurnost");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Svojstva");
	    lang.put("tab.folder.security", "Sigurnost");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Desktop");
	    lang.put("tab.workspace.search", "Traži");
	    lang.put("tab.workspace.dashboard", "DashBoard");
	    lang.put("tab.workspace.administration", "Administration");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Naziv");
	    lang.put("document.folder", "Folder");
	    lang.put("document.size", "Veličina");
	    lang.put("document.created", "Kreiran");
	    lang.put("document.lastmodified", "Izmjenjen");
	    lang.put("document.mimetype", "MIME tip");
	    lang.put("document.keywords", "Ključne riječi");
	    lang.put("document.by", "by");
	    lang.put("document.status", "Status");
	    lang.put("document.status.checkout", "Izmjenio");
	    lang.put("document.status.locked", "Zaključao");
	    lang.put("document.status.normal", "Dostupan");
	    lang.put("document.subscribed", "Potpisan");
	    lang.put("document.subscribed.yes", "Da");
	    lang.put("document.subscribed.no", "Ne");
	    lang.put("document.history.size", "Veličina istorije");
	    lang.put("document.subscribed.users", "Potpisani korisnici");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Add note");
	    lang.put("document.keywords.cloud", "Keywords cloud");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Naziv");
	    lang.put("folder.parent", "Nadređeni");
	    lang.put("folder.created", "Kreiran");
	    lang.put("folder.by", "by");
	    lang.put("folder.subscribed", "Potpisan");
	    lang.put("folder.subscribed.yes", "Da");
	    lang.put("folder.subscribed.no", "Ne");
	    lang.put("folder.subscribed.users", "Potpisani korisnici");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Verzija");
	    lang.put("version.created", "Datum");
	    lang.put("version.author", "Autor");
	    lang.put("version.size", "Veličina");
	    lang.put("version.purge.document", "Kratka istorija");
	    lang.put("version.comment", "Komentar");
	    
	    // Security
	    lang.put("security.label", "Sigurnost");
	    lang.put("security.group.name", "Grupa");
	    lang.put("security.group.permission.read", "Čitanje");
	    lang.put("security.group.permission.write", "Pisanje");
	    lang.put("security.user.name", "Korisnik");
	    lang.put("security.user.permission.read", "Čitanje");
	    lang.put("security.user.permission.write", "Pisanje");
	    lang.put("security.users", "Korisnici");
	    lang.put("security.groups", "Grupe");
	    lang.put("security.recursive", "Rekurzivna izmjena privilegija");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "Sistemska greška");
	    lang.put("error.invocation", "Greška u komunikaciji sa serverom");
	    
	    // About
	    lang.put("about.label", "O, OpenKM");
	    lang.put("about.loading", "Učitavanje ...");
	    
	    // Logout
	    lang.put("logout.label", "Izlaz");
	    lang.put("logout.closed", "OpenKM je uredno zatvoren.");
	    lang.put("logout.logout", "OpenKM se odjavljuje, molim sačekajte..");
	    
	    // Confirm
	    lang.put("confirm.label", "Potvrda");
	    lang.put("confirm.delete.folder", "¿ Da li zaista želite da obrišete folder ?");
	    lang.put("confirm.delete.document", "¿ Da li zaista želite da obrišete dokument ?");
	    lang.put("confirm.delete.trash", "¿ Da li zaista želite da isprazite korpu ?");
	    lang.put("confirm.purge.folder", "¿ Da li zaista želite da obrišete folder ?");
	    lang.put("confirm.purge.document", "¿ Da li zaista želite da obrišete dokument ?");
	    lang.put("confirm.delete.propety.group", "¿ Da li zaista želite da obrišete svojstvo grupi ?");
	    lang.put("confirm.purge.version.history.document", "¿ Da li zaista želite da obrišete istoriju dokumenta ?");
	    lang.put("confirm.purge.restore.document", "¿ Da li zaista želite da obnovite verziju dokumenta ?");
	    lang.put("confirm.set.default.home", "¿ Da li zaista želite da postavite početnu stranicu?");
	    lang.put("confirm.delete.saved.search", "¿ Do you really want to delete saved search ?");
	    lang.put("confirm.delete.user.news", "¿ Do you really want to delete user news ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    
	    // Search inputs
	    lang.put("search.context", "Objašnjenje");
	    lang.put("search.content", "Sadržaj");
	    lang.put("search.name", "Naziv");
	    lang.put("search.keywords", "Ključne riječi");
	    lang.put("search.folder", "Folder");
	    lang.put("search.results", "Rezultati");
	    lang.put("search.to", "ka");
	    lang.put("search.page.results", "Lista rezultata");
	    lang.put("search.add.property.group", "Dodaj svojstvo grupi");
	    lang.put("search.mimetype", "Mime tip");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Napredna pretraga");
	    lang.put("search.user", "Korisnik");
	    lang.put("search.date.and", "i");
	    lang.put("search.date.range", "Datum od.. do..");
	    lang.put("search.save.as.news", "Save as user news");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    
	    // Search results
	    lang.put("search.result.name", "Naziv");
	    lang.put("search.result.score", "Važnost");
	    lang.put("search.result.size", "Vličina");
	    lang.put("search.result.date.update", "Datum ažuriranja");
	    lang.put("search.result.author", "Autor");
	    lang.put("search.result.version", "Verzija");
	    lang.put("search.result.path", "Putanja");
	    lang.put("search.result.menu.download", "Preuzmi");
	    lang.put("search.result.menu.go.folder", "Idi u folder");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Ažuriranje pretrage");
	    lang.put("search.result.status.runsearch", "Ažuriranje snimljene pretrage");
	    
	    // Search saved
	    lang.put("search.saved.run", "Pokreni");
	    lang.put("search.saved.delete", "Obriši");
	    lang.put("search.saved.status.getsearchs", "Osvježavanje snimljene pretrage");
	    lang.put("search.saved.status.savesearch", "Ažuriranje snimljene pretrage");
	    lang.put("search.saved.status.deletesearch", "Brisanje snimljene pretrage");
	    lang.put("search.saved.status.getusernews", "Refreshing user news");
	    
	    // Button
	    lang.put("button.close", "Zatvori");
	    lang.put("button.logout", "Odjavi se");
	    lang.put("button.update", "Ažuriraj");
	    lang.put("button.cancel", "Odustati");
	    lang.put("button.accept", "Prihvati");
	    lang.put("button.restore", "Obnovi");
	    lang.put("button.move", "Premjesti");
	    lang.put("button.change", "Izmjeni");
	    lang.put("button.search", "Traži");
	    lang.put("button.save.search", "Snimi pretragu");
	    lang.put("button.view", "Pogled");
	    lang.put("button.clean", "Očisti");
	    lang.put("button.add", "Dodaj");
	    lang.put("button.delete", "Obriši");
	    lang.put("button.copy", "Kopiraj");
	    lang.put("button.create", "Kreiraj");
	    lang.put("button.show", "Prikaži");
	    lang.put("button.memory", "Ažuriraj");
	    lang.put("button.copy.clipboard", "Copy to clipboard");	
	    lang.put("button.start", "Start");
	    lang.put("button.select", "Select");
	    
	    // Group
	    lang.put("group.label", "Dodaj svojstvo grupi");
	    lang.put("group.group", "Grupa");
	    lang.put("group.property.group", "Svojstvo");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Dodaj oznaku");
	    lang.put("bookmark.name", "Naziv");
	    lang.put("bookmark.edit.label", "Izmjeni oznake");
	    lang.put("bookmark.path", "Putanja");
	    lang.put("bookmark.type", "Tip");
	    lang.put("bookmark.type.document", "Dokument");
	    lang.put("bookmark.type.folder", "Folder");
	    
	    // Notify
	    lang.put("notify.label", "Slanje linka dokumenta"); 
	    
	    // Status
	    lang.put("status.document.copied", "Dokument označen za kopiranje");
	    lang.put("status.document.cut", "Dokument označen za isjecanje");
	    lang.put("status.folder.copied", "Folder označen za kopiranje");
	    lang.put("status.folder.cut", "Folder označen za isjecanje");
	    lang.put("status.keep.alive.error", "Izgubljena konekcija ka serveru");
	    lang.put("status.debug.enabled", "Debug omogućen");
	    lang.put("status.debug.disabled", "Debug onemogućen");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Nedjelja");
	    lang.put("calendar.day.monday", "Ponedjeljak");
	    lang.put("calendar.day.tuesday", "Utorak");
	    lang.put("calendar.day.wednesday", "Srijeda");
	    lang.put("calendar.day.thursday", "Četvrtak");
	    lang.put("calendar.day.friday", "Petak");
	    lang.put("calendar.day.saturday", "Subota");
	    lang.put("calendar.month.january", "Januar");
	    lang.put("calendar.month.february", "Februar");
	    lang.put("calendar.month.march", "Mart");
	    lang.put("calendar.month.april", "April");
	    lang.put("calendar.month.may", "Maj");
	    lang.put("calendar.month.june", "Jun");
	    lang.put("calendar.month.july", "Jul");
	    lang.put("calendar.month.august", "August");
	    lang.put("calendar.month.september", "Septembar");
	    lang.put("calendar.month.october", "Oktobar");
	    lang.put("calendar.month.november", "Novembar");
	    lang.put("calendar.month.december", "Decembar");
	    
	    // Media player
	    lang.put("media.player.label", "Media player");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Image viewer");
	    lang.put("image.viewer.zoom.in", "Približi");
	    lang.put("image.viewer.zoom.out", "Udalji");
	    
	    // Debug console
	    lang.put("debug.console.label", "Debaguj konzolu");
	    lang.put("debug.enable.disable", "CTRL+Z za omogućenje/onemogućenje debag moda");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "General");
	    lang.put("dashboard.tab.news", "News");
	    lang.put("dashboard.tab.user", "User");
	    lang.put("dashboard.tab.workflow", "Workflow");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Keyword map");

	    // Dahboard general
	    lang.put("dashboard.new.items", "New");
	    lang.put("dashboard.user.locked.documents", "Locked documents");
	    lang.put("dashboard.user.checkout.documents", "Checkout documents");
	    lang.put("dashboard.user.last.modified.documents", "Last modified documents");
	    lang.put("dashboard.user.last.downloaded.documents", "Last downloaded documents");
	    lang.put("dashboard.user.subscribed.documents", "Subscribed documents");
	    lang.put("dashboard.user.subscribed.folders", "Subscribed folders");
	    lang.put("dashboard.user.last.uploaded.documents", "Last uploaded documents");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Last week top viewed documents");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Last month top viewed documents");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Last week top modified documents");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Last month top viewed documents");
	    lang.put("dashboard.general.last.uploaded.documents", "Last uploaded documents");
	    lang.put("dashboard.workflow.pending.tasks", "Pending tasks");
	    lang.put("dashboard.workflow.task", "Task");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Name");
	    lang.put("dashboard.workflow.task.created", "Creation date");
	    lang.put("dashboard.workflow.task.start", "Start date");
	    lang.put("dashboard.workflow.task.duedate", "Due date");
	    lang.put("dashboard.workflow.task.end", "End date");
	    lang.put("dashboard.workflow.task.description", "Description");
	    lang.put("dashboard.workflow.task.process.instance", "Process instance");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Version");
	    lang.put("dashboard.workflow.task.process.name", "Name");
	    lang.put("dashboard.workflow.task.process.description", "Description");
	    lang.put("dashboard.workflow.task.process.data", "Data");
	    lang.put("dashboard.workflow.task.process.definition", "Process definition");
	    lang.put("dashboard.workflow.task.process.path", "Path");
	    lang.put("dashboard.refreshing", "Refreshing");
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
	    lang.put("workflow.label", "Start workflow");
	    
	    // User configuration
	    lang.put("user.preferences.label", "User configuration");
	    lang.put("user.preferences.user", "User");
	    lang.put("user.preferences.password", "Password");
	    lang.put("user.preferences.mail", "E-mail");
	    lang.put("user.preferences.imap.host", "IMAP server");
	    lang.put("user.preferences.imap.user", "IMAP user name");
	    lang.put("user.preferences.imap.user.password", "IMAP user password");
	    lang.put("user.preferences.imap.folder", "IMAP folder");
	    lang.put("user.preferences.password.error", "Error: passwords are diferents");
	    lang.put("user.preferences.user.data", "User account");
	    lang.put("user.preferences.mail.data", "Mail account");
	    lang.put("user.preferences.imap.error", "All fields are obligatory to set the mail configurations");
	    lang.put("user.preferences.imap.password.error.void", "Password must not be empty on IMAP mail creation");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Pristup dokumentu odbijen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Dokument nije pronađen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Dokument vec postoji");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Zaključavanje dokumenta odbijeno");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Document unlock desired");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Repository interna greška");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Application interna greška");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Putanja do dokumenta nije nađena");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Pristup folderu odbijen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Folder nije nađen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Folder već postoji");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Repository interna greška");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Repository interna greška");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Putanja do foldera nije nađena");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Pristup stavci odbijen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Stavka nije pronađena");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Repository interna greška");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Repository interna greška");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Document nije pronađen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Repository interna greška");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Nepoznat format datoteke");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Dokument već postoji");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Veličina dokumenta je premašena");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Sesija je zatvorena");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Generalna greška kod izvršavanja upita");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Sačuvano traženo ime mora biti jedinstveno");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Ime oznake mora biti jedinstveno");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Sesija je izgubljena");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Repository interna greška");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Repository interna greška");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Repository interna greška");
	  }
}
