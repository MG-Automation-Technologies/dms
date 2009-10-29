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
 * Bosanski 
 * 
 * @author Senad Uka
 */
public class Lang_bs_BA {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd.MM.yyyy. hh:mm:ss");
	    lang.put("general.hour.pattern", "hh:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Učitavanje OpenKM");
	    lang.put("startup.starting.loading", "Početak punjenja OpenKM");
	    lang.put("startup.taxonomy", "Dobavljanje glavnog direktorija javnih dokumenata");
	    lang.put("startup.template", "Dobavljanje glavnog direktorija predložaka");
	    lang.put("startup.personal", "Dobavljanje glavnog direktorija ličnih dokumenata");
	    lang.put("startup.mail", "Dobavljanje glavnog e-mail direktorija");
	    lang.put("startup.trash", "Dobavljanje glavnog smeće direktorija");
	    lang.put("startup.user.home", "Dobavljanje korisničkog početnog direktorija");
	    lang.put("startup.bookmarks", "Dobavljanje oznaka");
	    lang.put("startup.loading.taxonomy", "Učitavanje javnih dokumenata");
	    lang.put("startup.loading.taxonomy.getting.folders", "Učitavanje javnih dokumenata - Dobavljanje direktorija");
	    lang.put("startup.loading.taxonomy.eval.params", "Učitavanje javnih dokumenata - Procjena pregleda ");
	    lang.put("startup.loading.taxonomy.open.path", "Učitavanje javnih dokumenata - Putanja");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Učitavanje javnih dokumenata - Dobavljanje direktorija");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Učitavanje javnih dokumenata - Dobavljanje dokumenata");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Učitavanje javnih dokumenata - Dobavljanje pošte");
	    lang.put("startup.loading.personal", "Učitavanje ličnih podataka");
	    lang.put("startup.loading.mail", "Učitavanje pošte");
	    lang.put("startup.loading.templates", "Učitavanje predloška");
	    lang.put("startup.loading.trash", "Učitavanje kante za smeće");
	    lang.put("startup.loading.history.search", "Učitavanje historijskog pretraživanja");
	    lang.put("startup.loading.user.values", "Učitavanje korisničkih vrijednosti");
	    lang.put("startup.loading.property.group.translations", "Učitavanje prijevoda osobina grupe");
	    lang.put("startup.keep.alive", "Učitavanje ostati u radnom");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Nova verzija OpenKM dostupna");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Javni dokumenti");
	    lang.put("leftpanel.label.trash", "Smeće");
	    lang.put("leftpanel.label.mail", "Pošta");
	    lang.put("leftpanel.label.stored.search", "Pohranjena pretraživanja");
	    lang.put("leftpanel.label.templates", "Predlošci");
	    lang.put("leftpanel.label.my.documents", "Moji dokumenti");
	    lang.put("leftpanel.label.user.search", "Korisničke vijesti");
	    lang.put("leftpanel.label.all.repository", "Svi repozitoriji");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Kreiraj datoteku");
	    lang.put("tree.menu.directory.remove", "Izbriši");
	    lang.put("tree.menu.directory.rename", "Preimenuj");
	    lang.put("tree.menu.directory.refresh", "Osvježi");
	    lang.put("tree.menu.directory.move", "Prebaci");
	    lang.put("tree.menu.directory.copy", "Kopiraj");
	    lang.put("tree.menu.directory.add.document", "Dodaj dokument");
	    lang.put("tree.menu.add.bookmark", "Dodaj oznaku");
	    lang.put("tree.menu.set.home", "Postavi početnu stranicu");
	    lang.put("tree.menu.export", "Ispiši u dokument");
	    lang.put("tree.status.refresh.folder", "Ažuriranje stabla dokumenata ");
	    lang.put("tree.status.refresh.create", "Kreiranje direktorija");
	    lang.put("tree.status.refresh.delete", "Brisanje direktorija");
	    lang.put("tree.status.refresh.rename", "Preimenovanje direktorija");
	    lang.put("tree.status.refresh.restore", "Vraćanje direktorija");
	    lang.put("tree.status.refresh.purge", "Čiščenje direktorija");
	    lang.put("tree.status.refresh.get", "Ažuriranje direktorija");
	    lang.put("tree.folder.new", "Novi direktorij");
	    lang.put("tree.status.refresh.add.subscription", "Dodavanje potpisa");
	    lang.put("tree.status.refresh.remove.subscription", "Brisanje potpisa");
	    lang.put("tree.status.refresh.get.root", "Osvježavanje glavnog direktorija");
	    lang.put("tree.status.refresh.get.user.home", "Dobavljanje glavne početne stranice");
	    lang.put("tree.status.refresh.purge.trash", "Čišćenje smeća");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Vrati");
	    lang.put("trash.menu.directory.purge", "Očisti");
	    lang.put("trash.menu.directory.purge.trash", "Očisti smeće");
	    lang.put("trash.directory.select.label", "Izaberi ciljani direktorij");
	    
	    // General menu
	    lang.put("general.menu.file", "Datoteka");
	    	lang.put("general.menu.file.exit", "Izaći");
	    	lang.put("general.menu.file.purge.trash", "Očisti smeće");
	    lang.put("general.menu.edit", "Uredi");
			lang.put("general.menu.file.create.directory", "Kreiraj direktorij");
			lang.put("general.menu.file.download.document", "Preuzmi dokument");
			lang.put("general.menu.file.download.document.pdf", "Preuzmi dokument kao PDF");
			lang.put("general.menu.file.send.link", "Pošalji link dokumenta");
			lang.put("general.menu.file.lock", "Zaključaj");
			lang.put("general.menu.file.unlock", "Otključaj");
			lang.put("general.menu.file.add.document", "Dodaj dokument");
			lang.put("general.menu.file.checkout", "Rezerviši dokument");
			lang.put("general.menu.file.checkin", "Pošalji novu verziju");
			lang.put("general.menu.file.cancel.checkout", "Poništi rezervaciju");
			lang.put("general.menu.file.delete", "Izbriši");
			lang.put("general.menu.file.refresh", "Osvježi");
	    lang.put("general.menu.tools", "Alati");
	    	lang.put("general.menu.tools.languages", "Jezici");
	    	lang.put("general.menu.tools.skin", "Skin");
    			lang.put("general.menu.tools.skin.default", "Po zadanom");
    			lang.put("general.menu.tools.skin.default2", "Po zadanom 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Srednji font");
    			lang.put("general.menu.tools.skin.bigfont", "Veliki font");
    		lang.put("general.menu.debug.console", "Debug console");
    		lang.put("general.menu.administration", "Pokaži administraciju");
    		lang.put("general.menu.tools.preferences", "Prioriteti");
    			lang.put("general.menu.tools.user.preferences", "Korisnička konfiguracija");
    	lang.put("general.menu.bookmark", "Oznake");
	    	lang.put("general.menu.bookmark.home", "Početna");
	    	lang.put("general.menu.bookmark.default.home", "Postavi zadanu početnu");
	    	lang.put("general.menu.bookmark.edit", "Uredi oznake");
	    lang.put("general.menu.help", "Pomoć");
		    lang.put("general.menu.bug.report", "Izvještaj grešaka");
		    lang.put("general.menu.support.request", "Zahtjev za podršku");
		    lang.put("general.menu.public.forum", "Javni forum");
		    lang.put("general.menu.project.web", "Projekat web");
		    lang.put("general.menu.version.changes", "Bilješke verzija");
		    lang.put("general.menu.documentation", "Dokumentacija");
		    lang.put("general.menu.about", "O OpenKM-u");
	    lang.put("general.connected", "Spojen kao");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Ime");
	    lang.put("filebrowser.date.update", "Datum ažuriranja");
	    lang.put("filebrowser.size", "Veličina");
	    lang.put("filebrowser.path", "Putanja");
	    lang.put("filebrowser.author", "Autor");
	    lang.put("filebrowser.version", "Verzija");
	    lang.put("filebrowser.menu.checkout", "Rezerviši dokument");
	    lang.put("filebrowser.menu.checkin", "Pošalji novu verziju");
	    lang.put("filebrowser.menu.delete", "Izbriši");
	    lang.put("filebrowser.menu.rename", "Preimenuj");
	    lang.put("filebrowser.menu.checkout.cancel", "Otkaži rezervaciju");
	    lang.put("filebrowser.menu.lock", "Zaključaj");
	    lang.put("filebrowser.menu.unlock", "Otključaj");
	    lang.put("filebrowser.menu.download", "Preuzmi");
	    lang.put("filebrowser.menu.move", "Premjesti");
	    lang.put("filebrowser.menu.copy", "Kopiraj");
	    lang.put("filebrowser.menu.create.from.template", "Kreiraj iz predloška");
	    lang.put("filebrowser.menu.add.property.group", "Dodaj grupu osobina");
	    lang.put("filebrowser.menu.remove.property.group", "Izbriši grupu osobina");
	    lang.put("filebrowser.menu.start.workflow", "Početak poslovnog procesa");
	    lang.put("filebrowser.menu.add.subscription", "Dodaj pretplata");
	    lang.put("filebrowser.menu.remove.subscription", "Obriši pretplatu");
	    lang.put("filebrowser.menu.add.bookmark", "Dodaj oznaku");
	    lang.put("filebrowser.menu.set.home", "Postavi početnu stranicu");
	    lang.put("filebrowser.menu.refresh", "Osvježi");
	    lang.put("filebrowser.menu.export", "Snimi kao ZIP arhivu");
	    lang.put("filebrowser.menu.play", "Pokreni");
	    lang.put("filebrowser.menu.image.viewer", "Preglednik slika");
	    lang.put("filebrowser.status.refresh.folder", "Ažuriram listu direktorija");
	    lang.put("filebrowser.status.refresh.document", "Ažuriram listu dokumenata");
	    lang.put("filebrowser.status.refresh.mail", "Ažuriram listu pošte");
	    lang.put("filebrowser.status.refresh.delete.folder", "Brišem direktorije");
	    lang.put("filebrowser.status.refresh.delete.document", "Brišem dokumente");
	    lang.put("filebrowser.status.refresh.checkout", "Rezervišem dokumente");
	    lang.put("filebrowser.status.refresh.lock", "Zaključavam dokumente");
	    lang.put("filebrowser.status.refresh.unlock", "Otključavam dokumente");
	    lang.put("filebrowser.status.refresh.document.rename", "Mijenjam ime dokumenata");
	    lang.put("filebrowser.status.refresh.folder.rename", "Mijenjam ime dokumenata");
	    lang.put("filebrowser.status.refresh.document.purge", "Permanentno brišem dokumente");
	    lang.put("filebrowser.status.refresh.folder.purge", "Permanentno brišem direktorije");
	    lang.put("filebrowser.status.refresh.folder.get", "Ažuriram informacije o direktorijima");
	    lang.put("filebrowser.status.refresh.document.get", "Ažuriram dokumente");
	    lang.put("filebrowser.status.refresh.add.subscription", "Dodajem pretplatu");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Brišem pretplatu");
	    lang.put("filebrowser.status.refresh.get.user.home", "Dobavljanje korisničke početne stranice");
	    lang.put("filebrowser.status.refresh.delete.mail", "Brišem poštu");
	    lang.put("filebrowser.status.refresh.mail.purge", "Permanentno brišem poštu");
	    
	    // File Upload
	    lang.put("fileupload.send", "Pošalji");
	    lang.put("fileupload.status.sending", "Učitavam datoteku ...");
	    lang.put("fileupload.status.indexing", "Indeksiram datoteku ...");
	    lang.put("fileupload.status.ok", "Datoteka je uspješno učitana");
	    lang.put("fileupload.upload.status", "Status učitavanja");
	    lang.put("fileupload.upload.uploaded", "Učitano");
	    lang.put("fileupload.upload.completed", "Učitavanje završeno");
	    lang.put("fileupload.upload.runtime", "Dužina trajanja");
	    lang.put("fileupload.upload.remaining", "Preostalo");
	    lang.put("fileupload.button.close", "Zatvori");
	    lang.put("fileupload.button.add.other.file", "Dodaj drugu datoteku");
	    lang.put("fileupload.status.move.file", "Premještanje datoteke ...");
	    lang.put("fileupload.status.move.mail", "Premještanje pošte ...");
	    lang.put("fileupload.status.copy.file", "Kopiranje datoteke ...");
	    lang.put("fileupload.status.copy.mail", "Kopiranje pošte ...");
	    lang.put("fileupload.status.restore.file", "Vraćanje datoteke ...");
	    lang.put("fileupload.status.restore.mail", "Vraćanje pošte ...");
	    lang.put("fileupload.status.move.folder", "Premještanje direktorija ...");
	    lang.put("fileupload.status.copy.folder", "Kopiranje direktorija ...");
	    lang.put("fileupload.status.restore.folder", "Vraćanje direktorija ...");
	    lang.put("fileupload.status.create.from.template", "Kreiranje datoteke iz predloška");
	    lang.put("fileupload.status.of", "od");
	    lang.put("fileupload.label.insert", "Dodaj nove dokumente");
	    lang.put("fileupload.label.update", "Ažuriranje dokumenata");
	    lang.put("fileupload.label.users.notify", "Obavjsti korisnika");
	    lang.put("fileupload.label.comment", "Komentar");
	    lang.put("fileupload.label.users.to.notify",  "Korisnici koje treba obavijestiti");
	    lang.put("fileupload.label.users",  "Korisnici");
	    lang.put("fileupload.label.must.select.users",  "Morate izabrati korisnika kojeg treba obavijestiti");
	    lang.put("fileupload.label.importZip", "Ubaci dokumente iz ZIP arhive");
	    lang.put("fileupload.label.notify.comment", "Obavijest");
	    lang.put("fileupload.label.error.importing.zip", "Greška u ubacivanju dokumenata iz arhive");
	    lang.put("fileupload.label.error.move.file", "Greška u premještanju datoteke");
	    lang.put("fileupload.label.error.move.mail", "Greška u premještanju pošte");
	    lang.put("fileupload.label.error.copy.file", "Greška u kopiranju datoteke");
	    lang.put("fileupload.label.error.copy.mail", "Greška u kopiranju pošte");
	    lang.put("fileupload.label.error.restore.file", "Greška u vraćanju datoteke ");
	    lang.put("fileupload.label.error.restore.mail", "Greška u vraćanju pošte");
	    lang.put("fileupload.label.error.move.folder", "Greška u prebacivanju direktorija");
	    lang.put("fileupload.label.error.copy.folder", "Greška u kopiranju direktorija");
	    lang.put("fileupload.label.error.restore.folder", "Greška u vraćanju direktorija");
	    lang.put("fileupload.label.error.create.from.template", "Greška u kreiranju datoteke iz predloška");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Nije dopušteno pomjeranje na osnovnom direktoriju ili poddirektoriju");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Nije dopušteno pomjeriti u isti destinacioni direktorij kao i izvorni");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Nije dozvoljeno kreiranje datoteke na izvornom direktoriju");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Osobine");
	    lang.put("tab.document.notes", "Zabilješke");
	    lang.put("tab.document.history", "Historija");
	    lang.put("tab.document.status.history", "Ažuriranje historije");
	    lang.put("tab.status.security.role", "Ažuriranje sigurnosnih uloga");
	    lang.put("tab.status.security.user", "Ažuriranje korisnika sigurnosti");
	    lang.put("tab.document.status.group.properties", "Ažuriranje grupe osobina");
	    lang.put("tab.document.status.set.keywords", "Postavljanje ključnih riječi");
	    lang.put("tab.document.status.get.version.history.size", "Osvježavanje veličine historije dokumenta");
	    lang.put("tab.document.status.purge.version.history", "Zbijanje historije dokumenta");
	    lang.put("tab.document.status.restore.version", "Vraćanje verzije dokumenta");
	    lang.put("tab.document.security", "Sigurnost");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Osobine");
	    lang.put("tab.folder.security", "Sigurnost");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Radna površina");
	    lang.put("tab.workspace.search", "Traži");
	    lang.put("tab.workspace.dashboard", "Kotrolna ploča");
	    lang.put("tab.workspace.administration", "Administracija");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Ime");
	    lang.put("document.folder", "Datoteka");
	    lang.put("document.size", "Veličina");
	    lang.put("document.created", "Kreiran");
	    lang.put("document.lastmodified", "Izmjenjen");
	    lang.put("document.mimetype", "MIME tip");
	    lang.put("document.keywords", "Ključne riječi");
	    lang.put("document.by", "od");
	    lang.put("document.status", "Status");
	    lang.put("document.status.checkout", "Uređen od");
	    lang.put("document.status.locked", "Zaljučan od");
	    lang.put("document.status.normal", "Dostupan");
	    lang.put("document.subscribed", "Potpisan");
	    lang.put("document.subscribed.yes", "Da");
	    lang.put("document.subscribed.no", "Ne");
	    lang.put("document.history.size", "Veličina historija");
	    lang.put("document.subscribed.users", "Potpisani korisnici");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Dodaj bilješku");
	    lang.put("document.keywords.cloud", "Oblak ključnih riječi");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "IME");
	    lang.put("folder.parent", "Roditelj");
	    lang.put("folder.created", "Kreiran");
	    lang.put("folder.by", "od");
	    lang.put("folder.subscribed", "Potpis");
	    lang.put("folder.subscribed.yes", "Da");
	    lang.put("folder.subscribed.no", "Ne");
	    lang.put("folder.subscribed.users", "Potpisani korisnici");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Verzija");
	    lang.put("version.created", "Datum");
	    lang.put("version.author", "Autor");
	    lang.put("version.size", "Veličina");
	    lang.put("version.purge.document", "Kompresuj historiju");
	    lang.put("version.comment", "Komentar");
	    
	    // Security
	    lang.put("security.label", "Sigurnost");
	    lang.put("security.group.name", "Grupa");
	    lang.put("security.group.permission.read", "Čitaj");
	    lang.put("security.group.permission.write", "Piši");
	    lang.put("security.user.name", "Korisnik");
	    lang.put("security.user.permission.read", "Čitaj");
	    lang.put("security.user.permission.write", "Piši");
	    lang.put("security.users", "Korisnici");
	    lang.put("security.groups", "Grupe");
	    lang.put("security.recursive", "Rekurzivne promjene dozvola");
	    

	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "Od");
	    lang.put("mail.reply", "Odgovori ka");
	    lang.put("mail.to", "Prema");
	    lang.put("mail.subject", "Naslov");
	    lang.put("mail.attachment", "Atačmenti");
	    
	    // Error
	    lang.put("error.label", "Sistem je generirao grešku");
	    lang.put("error.invocation", "Greša u komunikaciji sa serverom");
	    
	    // About
	    lang.put("about.label", "O OpenKM-u");
	    lang.put("about.loading", "Učitavanje ...");
	    
	    // Logout
	    lang.put("logout.label", "Izlaz");
	    lang.put("logout.closed", "OpenKM je ispravno zatvoren.");
	    lang.put("logout.logout", "OpenKM se odjavljuje, molimo sačekajte");
	    
	    // Confirm
	    lang.put("confirm.label", "Potvrda");
	    lang.put("confirm.delete.folder", "¿ Dali stvarno želite da izbrišete datoteku ?");
	    lang.put("confirm.delete.document", "¿ Dali stvarno želite da izbrišete dokument ?");
	    lang.put("confirm.delete.trash", "¿ Dali stvarno želite da ispraznite kantu za smeće ?");
	    lang.put("confirm.purge.folder", "¿ Dali stvarno želite da izbrišete datoteku ?");
	    lang.put("confirm.purge.document", "¿ Dali stvarno želite da izbrišete dokument ?");
	    lang.put("confirm.delete.propety.group", "¿ Dali stvarno želite da izbrišete grupu vlasnika ?");
	    lang.put("confirm.purge.version.history.document", "¿ Dali stvarno želite da izbrišete historiju dokumenata ?");
	    lang.put("confirm.purge.restore.document", "¿ Dali stvarno želite da vratite ovu verziju dokumenta ?");
	    lang.put("confirm.set.default.home", "¿ Dali stvarno želite da postavite zadanu početnu ?");
	    lang.put("confirm.delete.saved.search", "¿ Dali stvarno želite da izbrišete spašenu pretragu ?");
	    lang.put("confirm.delete.user.news", "¿ Dali stvarno želite da izbrišete korisničke obavijesti ?");
	    lang.put("confirm.delete.mail", "¿ Dali stvarno želite da izbrišete poštu ?");
	    
	    // Search inputs
	    lang.put("search.context", "Dodatak");
	    lang.put("search.content", "Sadržaj");
	    lang.put("search.name", "Ime");
	    lang.put("search.keywords", "Ključne riječi");
	    lang.put("search.folder", "Datoteka");
	    lang.put("search.results", "Rezultati");
	    lang.put("search.to", "prema");
	    lang.put("search.page.results", "Rezultati stranice");
	    lang.put("search.add.property.group", "Dodaj grupu vlasništva");
	    lang.put("search.mimetype", "Mime tip");
	    lang.put("search.type", "Tip");
	    lang.put("search.type.document", "Dokument");
	    lang.put("search.type.folder", "Datoteka");
	    lang.put("search.type.mail", "Pošta");
	    lang.put("search.advanced", "Napredna pretraga");
	    lang.put("search.user", "Korisnik");
	    lang.put("search.date.and", "i");
	    lang.put("search.date.range", "Datum između");
	    lang.put("search.save.as.news", "Spasi kao korisničke vijesti");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter po datotekama");
	    
	    // Search results
	    lang.put("search.result.name", "Ime");
	    lang.put("search.result.score", "Značajno");
	    lang.put("search.result.size", "Veličina");
	    lang.put("search.result.date.update", "Datum ažuriranja");
	    lang.put("search.result.author", "Autor");
	    lang.put("search.result.version", "Verzija");
	    lang.put("search.result.path", "Putanja");
	    lang.put("search.result.menu.download", "Preuzimanje");
	    lang.put("search.result.menu.go.folder", "Idi na datoteku");
	    lang.put("search.result.menu.go.document", "Idi na dokument");
	    lang.put("search.result.status.findPaginated", "Ažuriranje pretrage");
	    lang.put("search.result.status.runsearch", "Ažuriranje spašene pretrage");
	    
	    // Search saved
	    lang.put("search.saved.run", "Pokreni");
	    lang.put("search.saved.delete", "Izbriši");
	    lang.put("search.saved.status.getsearchs", "Osvježi spašene pretrage");
	    lang.put("search.saved.status.savesearch", "Ažuriraj spašene pretrage");
	    lang.put("search.saved.status.deletesearch", "Izbriši spašene pretrage");
	    lang.put("search.saved.status.getusernews", "Osvježi korisničke obavjesti");
	    
	    // Button
	    lang.put("button.close", "Zatvori");
	    lang.put("button.logout", "Odjavi se");
	    lang.put("button.update", "Ažuriraj");
	    lang.put("button.cancel", "Odgodi");
	    lang.put("button.accept", "Prihvati");
	    lang.put("button.restore", "Vrati");
	    lang.put("button.move", "Premjesti");
	    lang.put("button.change", "Promjeni");
	    lang.put("button.search", "Traži");
	    lang.put("button.save.search", "Spasi pretragu");
	    lang.put("button.view", "Pogledaj");
	    lang.put("button.clean", "Očisti");
	    lang.put("button.add", "Dodaj");
	    lang.put("button.delete", "Obriši");
	    lang.put("button.copy", "Kopiraj");
	    lang.put("button.create", "Kreiraj");
	    lang.put("button.show", "Prikaži");
	    lang.put("button.memory", "Ažuriraj");
	    lang.put("button.copy.clipboard", "Kopiraj u clipboard");	
	    lang.put("button.start", "Počni");
	    lang.put("button.select", "Izaberi");
	    
	    // Group
	    lang.put("group.label", "Dodaj grupu vlasništva");
	    lang.put("group.group", "Grupa");
	    lang.put("group.property.group", "Vlasništvo");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Dodaj oznake");
	    lang.put("bookmark.name", "Ime");
	    lang.put("bookmark.edit.label", "Uredi oznake");
	    lang.put("bookmark.path", "Putanja");
	    lang.put("bookmark.type", "Tip");
	    lang.put("bookmark.type.document", "Dokument");
	    lang.put("bookmark.type.folder", "Datoteka");
	    
	    // Notify
	    lang.put("notify.label", "Slanje linka dokumenta");
	    
	    // Status
	    lang.put("status.document.copied", "Dokument označen za kopiranje");
	    lang.put("status.document.cut", "Dokument označen za rezanje");
	    lang.put("status.folder.copied", "Datoteka označena za kopiranje");
	    lang.put("status.folder.cut", "Datoteka označena za rezanje");
	    lang.put("status.keep.alive.error", "Detektovana izgubljena konekcija prema serveru");
	    lang.put("status.debug.enabled", "Debagiranje omogućeno");
	    lang.put("status.debug.disabled", "Debagiranje onemogućeno");
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
	    lang.put("calendar.month.june", "Juni");
	    lang.put("calendar.month.july", "Juli");
	    lang.put("calendar.month.august", "August");
	    lang.put("calendar.month.september", "Septembar");
	    lang.put("calendar.month.october", "Oktobar");
	    lang.put("calendar.month.november", "Novembar");
	    lang.put("calendar.month.december", "Decembar");
	    
	    // Media player
	    lang.put("media.player.label", "Audio/video pregledik");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Preglednik slika");
	    lang.put("image.viewer.zoom.in", "Zumiraj");
	    lang.put("image.viewer.zoom.out", "Odzumiraj");
	    
	    // Debug console
	    lang.put("debug.console.label", "Debagiraj konzolu");
	    lang.put("debug.enable.disable", "CTRL+Z za omogućavanje ili neomogućavenje debagerskog moda");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Opšte");
	    lang.put("dashboard.tab.news", "Vijesti");
	    lang.put("dashboard.tab.user", "Korisnik");
	    lang.put("dashboard.tab.workflow", "Tok rada");
	    lang.put("dashboard.tab.mail", "Pošta");
	    lang.put("dashboard.tab.keymap", "Mapa ključnih riječi");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Novi");
	    lang.put("dashboard.user.locked.documents", "Zaključani dokumenti");
	    lang.put("dashboard.user.checkout.documents", "Provjeri dokumente");
	    lang.put("dashboard.user.last.modified.documents", "Zadnje mijenjani dokumenti");
	    lang.put("dashboard.user.last.downloaded.documents", "Zadnje preuzeti dokumenti");
	    lang.put("dashboard.user.subscribed.documents", "Potpisani dokumeti");
	    lang.put("dashboard.user.subscribed.folders", "Potpisane datoteke");
	    lang.put("dashboard.user.last.uploaded.documents", "Zadnje učitani dokumenti");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Najgledaniji dokumenti prošle sedmice");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Najgledaiji dokumenti prošlog mjeseca");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Najviše mijenjani dokumenti prošle sedmice");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Najviše mjenjani dokumenti prošlog mjeseca");
	    lang.put("dashboard.general.last.uploaded.documents", "Zadnje učitani dokumenti");
	    lang.put("dashboard.workflow.pending.tasks", "Nerješeni zadaci");
	    lang.put("dashboard.workflow.task", "Zadatak");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Ime");
	    lang.put("dashboard.workflow.task.created", "Datum kreiranja");
	    lang.put("dashboard.workflow.task.start", "Početni datum");
	    lang.put("dashboard.workflow.task.duedate", "Rok");
	    lang.put("dashboard.workflow.task.end", "Krajnji datum");
	    lang.put("dashboard.workflow.task.description", "Opis");
	    lang.put("dashboard.workflow.task.process.instance", "Instanca procesa");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Verzija");
	    lang.put("dashboard.workflow.task.process.name", "Ime");
	    lang.put("dashboard.workflow.task.process.description", "Opis");
	    lang.put("dashboard.workflow.task.process.data", "Podatak");
	    lang.put("dashboard.workflow.task.process.definition", "Definicija procesa");
	    lang.put("dashboard.workflow.task.process.path", "Putanja");
	    lang.put("dashboard.refreshing", "Osvježavanje");
	    lang.put("dashboard.keyword", "Ključne riječi");
	    lang.put("dashboard.keyword.suggest", "Tip ključne riječi");
	    lang.put("dashboard.keyword.all", "Sve ključne riječi");
	    lang.put("dashboard.keyword.top", "Najčešće ključne riječi");
	    lang.put("dashboard.keyword.related", "Ključne riječi koje se odnose na");
	    lang.put("dashboard.keyword.goto.document", "Idi na dokument");
	    lang.put("dashboard.keyword.clean.keywords", "Očisti ključne riječi");
	    lang.put("dashboard.mail.last.imported.mails", "Electronic mails");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Attachments");
	    
	    // Workflow
	    lang.put("workflow.label", "Počni tok rada");
	    
	    // User configuration
	    lang.put("user.preferences.label", "Korisnička konfiguracija");
	    lang.put("user.preferences.user", "Ime");
	    lang.put("user.preferences.password", "Šifra");
	    lang.put("user.preferences.imap.host", "IMAP server");
	    lang.put("user.preferences.imap.user", "IMAP korisničko ime");
	    lang.put("user.preferences.imap.user.password", "IMAP korisnička šifra");
	    lang.put("user.preferences.imap.folder", "IMAP datoteka");
	    lang.put("user.preferences.password.error", "Greška: šifre su različite");
	    lang.put("user.preferences.user.data", "User account");
	    lang.put("user.preferences.mail.data", "Mail account");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Prisup dokumentima odbijen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Dokument nije pronađen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Dokument već postoji");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Dokument je zaključan");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Otključajte dokument");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Interna greška repozitorija");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Interna greška aplikacije");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Putanja dokumenta nije pronađena");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Datoteci pristup odbijen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Datoteka nije pronađena");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Datoteka već postoji");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Interna greška repozitorija");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Interna greška repozitorija");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Putanja datoteke nije pronađena");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Zabrana pristupa predmetu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Predmet nije pronađen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Interna greška repozitorija");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Interna greška repozitorija");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Dokument nije pronađen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Interna greška repozitorija");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Neprihvatljiv format datoteke");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Dokument već postoji");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Veličina dokumenta je premašena");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Sesija zatvorena");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Greška prilikom pokretanja upita");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Ime pretrage mora biti jedinstveno");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Ime oznake mora biti jedinstveno");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Sesija je izgubljena");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Interna greška repozitorija");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Interna greška repozitorija");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Greška na Izlaz/Ulaz");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Interna greška repozitorija");
	  }
}
