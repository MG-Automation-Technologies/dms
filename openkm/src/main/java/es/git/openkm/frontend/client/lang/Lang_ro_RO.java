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
 * Romana (Romania)
 *
 * @author Ionut Arsene
 * @contact: develop-openkm@point.ro
 */
public class Lang_ro_RO {

	public final static HashMap<String,String> lang;
	  static {
	    lang = new HashMap<String,String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd.MM.yyyy hh:mm:ss");

	    // Startup
	    lang.put("startup.openkm", "OpenKM se incarca");
	    lang.put("startup.starting.loading", "Incepe incarcarea OpenKM");
	    lang.put("startup.taxonomy", "Se preia nodul principal pentru taxonomii");
	    lang.put("startup.template", "Se preia nodul principal pentru sabloane");
	    lang.put("startup.personal", "Se preia nodul principal pentru documente personale");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Se preia nodul principal pentru cosul de gunoi");
	    lang.put("startup.user.home", "Se preia nodul pentru paginile utilizatorului");
	    lang.put("startup.bookmarks", "Se preiau favoritii");
	    lang.put("startup.loading.taxonomy", "Se incarca taxonomia");
	    lang.put("startup.loading.taxonomy.getting.folders", "Se incarca taxonomia - se preiau dosarele");
	    lang.put("startup.loading.taxonomy.eval.params", "Se incarca taxonomia - se evalueaza parametrii navigatorului Web");
	    lang.put("startup.loading.taxonomy.open.path", "Se incarca taxonomia - deschidere locatie");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Se incarca taxonomia - se preiau dosarele");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Se incarca taxonomia - se preiau documentele");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Se incarca zona documentelor personale");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.templates", "Se incarca zona sabloanelor");
	    lang.put("startup.loading.trash", "Se incarca zona cosului de gunoi");
	    lang.put("startup.loading.history.search", "Se incarca zona istoricului cautarilor");
	    lang.put("startup.loading.user.values", "Se incarca parametrii utilizatorului");
	    lang.put("startup.loading.property.group.translations", "Se incarca grupul traducerilor referitoare la proprietate");
	    lang.put("startup.keep.alive", "Incarcare conexiune permanenta");

	    // Update notification
	    lang.put("openkm.update.available", "O versiune mai noua a OpenKM este disponibila");

	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Taxonomii");
	    lang.put("leftpanel.label.trash", "Cos de gunoi");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Cautari salvate");
	    lang.put("leftpanel.label.templates", "Sabloane");
	    lang.put("leftpanel.label.my.documents", "Documentele mele");
	    lang.put("leftpanel.label.user.search", "User news");
	    lang.put("leftpanel.label.all.repository", "All repository");

	    // Tree
	    lang.put("tree.menu.directory.create", "Creare dosar");
	    lang.put("tree.menu.directory.remove", "Stergere");
	    lang.put("tree.menu.directory.rename", "Redenumire");
	    lang.put("tree.menu.directory.refresh", "Reincarcare");
	    lang.put("tree.menu.directory.move", "Mutare");
	    lang.put("tree.menu.directory.copy", "Copiere");
	    lang.put("tree.menu.directory.add.document", "Adaugare document");
	    lang.put("tree.menu.add.bookmark", "Adaugare favorit");
	    lang.put("tree.menu.set.home", "Stabilire ca pagina de pornire");
	    lang.put("tree.menu.export", "Export intr-un fisier");
	    lang.put("tree.status.refresh.folder", "Se actualizeaza arborele de dosare");
	    lang.put("tree.status.refresh.create", "Se creeaza dosarul");
	    lang.put("tree.status.refresh.delete", "Se sterge dosarul");
	    lang.put("tree.status.refresh.rename", "Se redenumeste dosarul");
	    lang.put("tree.status.refresh.restore", "Se restaureaza dosarul");
	    lang.put("tree.status.refresh.purge", "Se goleste dosarul");
	    lang.put("tree.status.refresh.get", "Se actualizeaza dosarul");
	    lang.put("tree.folder.new", "Dosar nou");
	    lang.put("tree.status.refresh.add.subscription", "Se adauga o subscriere");
	    lang.put("tree.status.refresh.remove.subscription", "Se sterge o subscriere");
	    lang.put("tree.status.refresh.get.root", "Se reincarca dosarul parinte");
	    lang.put("tree.status.refresh.get.user.home", "Se incarca pagina de pornire");
	    lang.put("tree.status.refresh.purge.trash", "Se curata cosul de gunoi");

	    // Trash
	    lang.put("trash.menu.directory.restore", "Restaurare");
	    lang.put("trash.menu.directory.purge", "Golire");
	    lang.put("trash.menu.directory.purge.trash", "Golire cos de gunoi");
	    lang.put("trash.directory.select.label", "Selectati dosarul de destinatie");

	    // General menu
			lang.put("general.menu.file", "Fisier");
	    	lang.put("general.menu.file.exit", "Iesire");
	    	lang.put("general.menu.file.purge.trash", "Golire cos de gunoi");
	    lang.put("general.menu.edit", "Editare");
			lang.put("general.menu.file.create.directory", "Creare dosar");
			lang.put("general.menu.file.download.document", "Descarcare document");
			lang.put("general.menu.file.download.document.pdf", "Descarcare document ca fisier PDF");
			lang.put("general.menu.file.send.link", "Transmitere legatura catre document");
			lang.put("general.menu.file.lock", "Blocare");
			lang.put("general.menu.file.unlock", "Deblocare");
			lang.put("general.menu.file.add.document", "Adaugare document");
			lang.put("general.menu.file.checkout", "Marcare in editare");
			lang.put("general.menu.file.checkin", "Adaugare versiune noua");
			lang.put("general.menu.file.cancel.checkout", "Anulare marcare in editare");
			lang.put("general.menu.file.delete", "Stergere");
			lang.put("general.menu.file.refresh", "Reincarcare");
	    lang.put("general.menu.tools", "Unelte");
	    	lang.put("general.menu.tools.languages", "Alte limbi");
	    	lang.put("general.menu.tools.skin", "Teme aspect");
    			lang.put("general.menu.tools.skin.default", "Normal");
    			lang.put("general.menu.tools.skin.default2", "Normal 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Dimensiune medie text");
    			lang.put("general.menu.tools.skin.bigfont", "Dimensiune mare text");
    		lang.put("general.menu.debug.console", "Consola depanare");
    		lang.put("general.menu.administration", "Aratare zona administrare");
    		lang.put("general.menu.tools.preferences", "Prefererences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Favoriti");
	    	lang.put("general.menu.bookmark.home", "Pagina pornire");
	    	lang.put("general.menu.bookmark.default.home", "Stabilire ca pagina de pornire");
	    	lang.put("general.menu.bookmark.edit", "Editare favoriti");
	    lang.put("general.menu.help", "Ajutor");
		    lang.put("general.menu.bug.report", "Raportare incident");
		    lang.put("general.menu.support.request", "Cerere de suport");
		    lang.put("general.menu.public.forum", "Forum public");
		    lang.put("general.menu.project.web", "Site-ul proiectului");
		    lang.put("general.menu.version.changes", "Notele versiunii");
		    lang.put("general.menu.documentation", "Documentatie");
		    lang.put("general.menu.about", "Despre OpenKM");
	    lang.put("general.connected", "Conectat ca");

	    // File Browser
	    lang.put("filebrowser.name", "Nume");
	    lang.put("filebrowser.date.update", "Data actualizarii");
	    lang.put("filebrowser.size", "Dimensiune");
	    lang.put("filebrowser.path", "Cale");
	    lang.put("filebrowser.author", "Autor");
	    lang.put("filebrowser.version", "Versiune");
	    lang.put("filebrowser.menu.checkout", "Marcare in editare");
	    lang.put("filebrowser.menu.checkin", "Adaugare versiune noua");
	    lang.put("filebrowser.menu.delete", "Stergere");
	    lang.put("filebrowser.menu.rename", "Redenumire");
	    lang.put("filebrowser.menu.checkout.cancel", "Anulare marcare in editare");
	    lang.put("filebrowser.menu.lock", "Blocare");
	    lang.put("filebrowser.menu.unlock", "Deblocare");
	    lang.put("filebrowser.menu.download", "Descarcare");
	    lang.put("filebrowser.menu.move", "Mutare");
	    lang.put("filebrowser.menu.copy", "Copiere");
	    lang.put("filebrowser.menu.create.from.template", "Creare pe baza unui sablon");
	    lang.put("filebrowser.menu.add.property.group", "Adaugare grup proprietati");
	    lang.put("filebrowser.menu.remove.property.group", "Stergere grup proprietati");
	    lang.put("filebrowser.menu.start.workflow", "Start workflow");
	    lang.put("filebrowser.menu.add.subscription", "Adaugare subscriere");
	    lang.put("filebrowser.menu.remove.subscription", "Stergere subscriere");
	    lang.put("filebrowser.menu.add.bookmark", "Adaugare favorit");
	    lang.put("filebrowser.menu.set.home", "Stabilire ca pagina de pornire");
	    lang.put("filebrowser.menu.refresh", "Reincarcare");
	    lang.put("filebrowser.menu.export", "Export intr-un ZIP");
	    lang.put("filebrowser.menu.play", "Porneste vizualizare");
	    lang.put("filebrowser.menu.image.viewer", "Vizualizare imagine");
	    lang.put("filebrowser.status.refresh.folder", "Se actualizeaza lista dosarelor");
	    lang.put("filebrowser.status.refresh.document", "Se actualizeaza lista documentelor");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Se sterge dosarul");
	    lang.put("filebrowser.status.refresh.delete.document", "Se sterge documentul");
	    lang.put("filebrowser.status.refresh.checkout", "Se marcheaza in editare documentul");
	    lang.put("filebrowser.status.refresh.lock", "Se blocheaza documentul");
	    lang.put("filebrowser.status.refresh.unlock", "Se deblocheaza documentul");
	    lang.put("filebrowser.status.refresh.document.rename", "Se redenumeste documentul");
	    lang.put("filebrowser.status.refresh.folder.rename", "Se redenumeste dosarul");
	    lang.put("filebrowser.status.refresh.document.purge", "Se sterge documentul");
	    lang.put("filebrowser.status.refresh.folder.purge", "Se sterge dosarul");
	    lang.put("filebrowser.status.refresh.folder.get", "Se actualizeaza dosarul");
	    lang.put("filebrowser.status.refresh.document.get", "Se actualizeaza documentul");
	    lang.put("filebrowser.status.refresh.add.subscription", "Se adauga subscrierea");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Se sterge subscrierea");
	    lang.put("filebrowser.status.refresh.get.user.home", "Se incarca pagina de pornire");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");

	    // File Upload
	    lang.put("fileupload.send", "Trimite");
	    lang.put("fileupload.status.sending", "Se incarca fisierul...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "Fisierul a fost incarcat corect");
	    lang.put("fileupload.upload.status", "Stare incarcare");
	    lang.put("fileupload.upload.uploaded", "Incarcat");
	    lang.put("fileupload.upload.completed", "Incarcare finalizata");
	    lang.put("fileupload.upload.runtime", "Timp scurs");
	    lang.put("fileupload.upload.remaining", "Timp ramas");
	    lang.put("fileupload.button.close", "Inchidere");
	    lang.put("fileupload.button.add.other.file", "Adaugare alt document");
	    lang.put("fileupload.status.move.file", "Documentul este mutat...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Documentul este copiat...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "Documentul este restaurat...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Dosarul este mutat...");
	    lang.put("fileupload.status.copy.folder", "Dosarul este copiat...");
	    lang.put("fileupload.status.restore.folder", "Dosarul este restaurat...");
	    lang.put("fileupload.status.create.from.template", "Documentul este creat pe baza unui sablon...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Adaugare documente noi");
	    lang.put("fileupload.label.update", "Actualizare documente");
	    lang.put("fileupload.label.users.notify", "Notificare utilizatori");
	    lang.put("fileupload.label.comment", "Comentariu");
	    lang.put("fileupload.label.users.to.notify",  "Utilizatorii vor fi notificati");
	    lang.put("fileupload.label.users",  "Utilizatori");
	    lang.put("fileupload.label.must.select.users",  "Trebuie sa selectati un utilizator pentru a fi notificat");
	    lang.put("fileupload.label.importZip", "Import Documents from ZIP");
	    lang.put("fileupload.label.notify.comment", "Mesaj de notificare");
	    lang.put("fileupload.label.error.importing.zip", "Eroare la importul documentului");
	    lang.put("fileupload.label.error.move.file", "Eroare la mutarea documentului");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Eroare la copierea documentului");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "Eroare la restaurarea documentului");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Eroare la mutarea dosarului");
	    lang.put("fileupload.label.error.copy.folder", "Eroare la copierea dosarului");
	    lang.put("fileupload.label.error.restore.folder", "Eroare la restaurarea dosarului");
	    lang.put("fileupload.label.error.create.from.template", "Eroare la crearea documentului pe baza unui sablon");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Nu este posibila mutarea in propria locatie sau intr-o sublocatie");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Nu este posibila copierea in propria locatie");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Nu este posibila crearea in aceeasi locatie pe baza unui sablon");

	    // Tab properties
	    lang.put("tab.document.properties", "Proprietati");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "Istoric");
	    lang.put("tab.document.status.history", "Se actualizeaza istoricul");
	    lang.put("tab.status.security.role", "Se actualizeaza rolurile de securitate");
	    lang.put("tab.status.security.user", "Se actualizeaza utilizatorii");
	    lang.put("tab.document.status.group.properties", "Se actualizeaza grupul de proprietati");
	    lang.put("tab.document.status.set.keywords", "Se stabilesc cuvintele cheie");
	    lang.put("tab.document.status.get.version.history.size", "Se reincarca dimensiunea istoricului documentului");
	    lang.put("tab.document.status.purge.version.history", "Se restrange istoricul documentului");
	    lang.put("tab.document.status.restore.version", "Se restaureaza versiunea documentului");
	    lang.put("tab.document.security", "Securitate");
	    lang.put("tab.folder.properties", "Proprietati");
	    lang.put("tab.folder.security", "Securitate");

	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Zona de lucru");
	    lang.put("tab.workspace.search", "Cautare");
	    lang.put("tab.workspace.dashboard", "DashBoard");
	    lang.put("tab.workspace.administration", "Administration");

	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Nume");
	    lang.put("document.folder", "Dosar");
	    lang.put("document.size", "Dimensiune");
	    lang.put("document.created", "Creat la");
	    lang.put("document.lastmodified", "Modificat la");
	    lang.put("document.mimetype", "Tip MIME");
	    lang.put("document.keywords", "Cuvinte cheie");
	    lang.put("document.by", "de catre");
	    lang.put("document.status", "Stare");
	    lang.put("document.status.checkout", "Editat de catre");
	    lang.put("document.status.locked", "Blocat de catre");
	    lang.put("document.status.normal", "Disponibil");
	    lang.put("document.subscribed", "Subscris");
	    lang.put("document.subscribed.yes", "Da");
	    lang.put("document.subscribed.no", "Nu");
	    lang.put("document.history.size", "Dimensiune istoric");
	    lang.put("document.subscribed.users", "Utilizatori subscrisi");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Add note");
	    lang.put("document.keywords.cloud", "Keywords cloud");

	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nume");
	    lang.put("folder.parent", "Parinte");
	    lang.put("folder.created", "Creat la");
	    lang.put("folder.by", "de catre");
	    lang.put("folder.subscribed", "Subscris");
	    lang.put("folder.subscribed.yes", "Da");
	    lang.put("folder.subscribed.no", "Nu");
	    lang.put("folder.subscribed.users", "Utilizatori subscrisi");
	    lang.put("folder.webdav", "WebDAV");

	    // Version
	    lang.put("version.name", "Versiune");
	    lang.put("version.created", "Data");
	    lang.put("version.author", "Autor");
	    lang.put("version.size", "Dimensiune");
	    lang.put("version.purge.document", "Restrangere istoric");
	    lang.put("version.comment", "Comentariu");

	    // Security
	    lang.put("security.label", "Securitate");
	    lang.put("security.group.name", "Grup");
	    lang.put("security.group.permission.read", "Citire");
	    lang.put("security.group.permission.write", "Scriere");
	    lang.put("security.user.name", "Utilizator");
	    lang.put("security.user.permission.read", "Citire");
	    lang.put("security.user.permission.write", "Scriere");
	    lang.put("security.users", "Utilizatori");
	    lang.put("security.groups", "Grupuri");
	    lang.put("security.recursive", "Modificare recursiva a drepturilor");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");

	    // Error
	    lang.put("error.label", "Sistemul a generat o eroare");
	    lang.put("error.invocation", "Eroare la comunicatia cu serverul");

	    // About
	    lang.put("about.label", "Despre OpenKM");
	    lang.put("about.loading", "Se incarca ...");

	    // Logout
	    lang.put("logout.label", "Iesire");
	    lang.put("logout.closed", "OpenKM a fost inchis corect.");
	    lang.put("logout.logout", "OpenKM se deconecteaza, va rugam asteptati");

	    // Confirm
	    lang.put("confirm.label", "Confirmare");
	    lang.put("confirm.delete.folder", "¿ Sunteti sigur ca doriti stergerea dosarului ?");
	    lang.put("confirm.delete.document", "¿ Sunteti sigur ca doriti stergerea documentului ?");
	    lang.put("confirm.delete.trash", "¿ Sunteti sigur ca doriti golirea cosului de gunoi ?");
	    lang.put("confirm.purge.folder", "¿ Sunteti sigur ca doriti stergerea dosarului ?");
	    lang.put("confirm.purge.document", "¿ Sunteti sigur ca doriti stergerea documentului ?");
	    lang.put("confirm.delete.propety.group", "¿ Sunteti sigur ca doriti stergerea grupului de proprietati ?");
	    lang.put("confirm.purge.version.history.document", "¿ Sunteti sigur ca doriti stergerea istoricului documentului ?");
	    lang.put("confirm.purge.restore.document", "¿ Sunteti sigur ca doriti restaurarea acestei versiuni ?");
	    lang.put("confirm.set.default.home", "¿ Sunteti sigur ca doriti stabilirea ca pagina de pornire ?");
	    lang.put("confirm.delete.saved.search", "¿ Do you really want to delete saved search ?");
	    lang.put("confirm.delete.user.news", "¿ Do you really want to delete user news ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");

	    // Search inputs
	    lang.put("search.context", "Context");
	    lang.put("search.content", "Content");
	    lang.put("search.name", "Nume");
	    lang.put("search.keywords", "Cuvinte cheie");
	    lang.put("search.folder", "Folder");
	    lang.put("search.results", "Rezultate");
	    lang.put("search.to", "la");
	    lang.put("search.page.results", "Rezultate pe pagina");
	    lang.put("search.add.property.group", "Adauga grup de proprietati");
	    lang.put("search.mimetype", "Tip MIME");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Cautare avansata");
	    lang.put("search.user", "Utilizator");
	    lang.put("search.date.and", "si");
	    lang.put("search.date.range", "Intervalul calendaristic intre");
	    lang.put("search.save.as.news", "Save as user news");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");

	    // Search results
	    lang.put("search.result.name", "Nume");
	    lang.put("search.result.score", "Relevanta");
	    lang.put("search.result.size", "Dimensiune");
	    lang.put("search.result.date.update", "Data actualizarii");
	    lang.put("search.result.author", "Autor");
	    lang.put("search.result.version", "Versiune");
	    lang.put("search.result.path", "Cale");
	    lang.put("search.result.menu.download", "Descarcare");
	    lang.put("search.result.menu.go.folder", "Mergeti in dosar");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Se actualizeaza cautarea");
	    lang.put("search.result.status.runsearch", "Se actualizeaza cautarea salvata");

	    // Search saved
	    lang.put("search.saved.run", "Pornire");
	    lang.put("search.saved.delete", "Stergere");
	    lang.put("search.saved.status.getsearchs", "Reincarcare cautari salvate");
	    lang.put("search.saved.status.savesearch", "Se actualizeaza cautarea salvata");
	    lang.put("search.saved.status.deletesearch", "Se sterge cautarea salvata");
	    lang.put("search.saved.status.getusernews", "Refreshing user news");

	    // Button
	    lang.put("button.close", "Inchidere");
	    lang.put("button.logout", "Deconectare");
	    lang.put("button.update", "Actualizare");
	    lang.put("button.cancel", "Renuntare");
	    lang.put("button.accept", "Acceptare");
	    lang.put("button.restore", "Restaurare");
	    lang.put("button.move", "Mutare");
	    lang.put("button.change", "Schimbare");
	    lang.put("button.search", "Cautare");
	    lang.put("button.save.search", "Salvare cautare");
	    lang.put("button.view", "Vizualizare");
	    lang.put("button.clean", "Curatare");
	    lang.put("button.add", "Adaugare");
	    lang.put("button.delete", "Stergere");
	    lang.put("button.copy", "Copiere");
	    lang.put("button.create", "Creare");
	    lang.put("button.show", "Arata");
	    lang.put("button.memory", "Actualizare");
	    lang.put("button.copy.clipboard", "Copy to clipboard");	
	    lang.put("button.start", "Start");
	    lang.put("button.select", "Select");

	    // Group
	    lang.put("group.label", "Adauga grup de proprietati");
	    lang.put("group.group", "Grup");
	    lang.put("group.property.group", "Proprietate");

	    // Bookmark
	    lang.put("bookmark.label", "Adauga favoriti");
	    lang.put("bookmark.name", "Nume");
	    lang.put("bookmark.edit.label", "Editeaza favoriti");
	    lang.put("bookmark.path", "Cale");
	    lang.put("bookmark.type", "Tip");
	    lang.put("bookmark.type.document", "Document");
	    lang.put("bookmark.type.folder", "Dosar");

	    // Notify
	    lang.put("notify.label", "Se transmite legatura catre document");

	    // Status
	    lang.put("status.document.copied", "Document marcat pentru copiere");
	    lang.put("status.document.cut", "Document marcat pentru mutare");
	    lang.put("status.folder.copied", "Dosar marcat pentru copiere");
	    lang.put("status.folder.cut", "Dosar marcat pentru mutare");
	    lang.put("status.keep.alive.error", "A fost detectata o pierdere a conexiunii cu serverul (conexiunea permanenta)");
	    lang.put("status.debug.enabled", "Depanare pornita");
	    lang.put("status.debug.disabled", "Depanare oprita");

	    // Calendar
	    lang.put("calendar.day.sunday", "Duminica");
	    lang.put("calendar.day.monday", "Luni");
	    lang.put("calendar.day.tuesday", "Marti");
	    lang.put("calendar.day.wednesday", "Miercuri");
	    lang.put("calendar.day.thursday", "Joi");
	    lang.put("calendar.day.friday", "Vineri");
	    lang.put("calendar.day.saturday", "Sambata");
	    lang.put("calendar.month.january", "Ianuarie");
	    lang.put("calendar.month.february", "Februarie");
	    lang.put("calendar.month.march", "Martie");
	    lang.put("calendar.month.april", "Aprilie");
	    lang.put("calendar.month.may", "Mai");
	    lang.put("calendar.month.june", "Iunie");
	    lang.put("calendar.month.july", "Iulie");
	    lang.put("calendar.month.august", "August");
	    lang.put("calendar.month.september", "Septembrie");
	    lang.put("calendar.month.october", "Octombrie");
	    lang.put("calendar.month.november", "Noiembrie");
	    lang.put("calendar.month.december", "Decembrie");

	    // Media player
	    lang.put("media.player.label", "Player media");

	    // Image viewer
	    lang.put("image.viewer.label", "Vizualizator imagine");
	    lang.put("image.viewer.zoom.in", "Marire");
	    lang.put("image.viewer.zoom.out", "Micsorare");

	    // Debug console
	    lang.put("debug.console.label", "Consola depanare");
	    lang.put("debug.enable.disable", "CTRL+Z pentru a activa sau dezactiva modul depanare");

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
	    
	    // Workflow
	    lang.put("workflow.label", "Start workflow");
	    
	    // User configuration
	    lang.put("user.preferences.label", "User configuration");
	    lang.put("user.preferences.name", "Name");
	    lang.put("user.preferences.password", "Password");
	    lang.put("user.preferences.imap.host", "Imap server");
	    lang.put("user.preferences.imap.user", "Imap user name");
	    lang.put("user.preferences.imap.user.password", "Imap user password");
	    lang.put("user.preferences.imap.folder", "Imap folder");
	    lang.put("user.preferences.password.error", "Error passwords are diferents");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Accesul la document nu este permis");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Documentul nu a fost gasit");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Documentul exista deja");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Blocarea documentului nu este permisa");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Este dorita deblocarea documentului");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Eroare interna a depozitului");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Eroare interna a aplicatiei");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Calea catre document nu a fost gasita");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Accesul la dosar nu este permis");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Dosarul nu a fost gasit");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Dosarul exista deja");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Eroare interna a depozitului");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Eroare interna a aplicatiei");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Calea catre dosar nu a fost gasita");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Accesul la fisier nu este permis");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Fisierul nu a fost gasit");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Eroare interna a depozitului");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Eroare interna a depozitului");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Documentul nu a fost gasit");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Eroare interna a depozitului");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Tipul de fisier nu este suportat");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Documentul exista deja");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "A fost depasita dimensiunea maxima a documentului");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Sesiune inchisa");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Eroare generica la executarea interogarii");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Numele cautarii salvate trebuie sa fie unic");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Numele favoritului trebuie sa fie unic");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Conexiune pierduta");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Eroare interna a depozitului");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Eroare interna a depozitului");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Eroare interna a depozitului");
	  }
}