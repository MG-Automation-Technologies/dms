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
 * Latvian (Latvia)
 * 
 * @author Martins Rajeckis
 */
public class Lang_lv_LV {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd.MM.yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd.MM.yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup  taxonomy - sistemātika
	    lang.put("startup.openkm", "Ielādē OpenKM");
	    lang.put("startup.starting.loading", "Sākam OpenKM ielādi");
	    lang.put("startup.taxonomy", "Saņem sistemātikas sākuma posmu");
	    lang.put("startup.categories", "Getting categories root node");
	    lang.put("startup.thesaurus", "Getting thesaurus root node");
	    lang.put("startup.template", "Saņem šablona sākuma posmu");
	    lang.put("startup.personal", "Saņem personīgo sākuma posmu");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Saņem miskastes sākuma posmu");
	    lang.put("startup.user.home", "Saņem lietotāja mājas sākumu");
	    lang.put("startup.bookmarks", "Saņem grāmatzīmes");
	    lang.put("startup.loading.taxonomy", "Ielādē sistemātiku");
	    lang.put("startup.loading.taxonomy.getting.folders", "Ielādē sistemātiku - saņem mapes");
	    lang.put("startup.loading.taxonomy.eval.params", "Ielādē sistemātiku - novērtē pārlūkprogrammas parametrus");
	    lang.put("startup.loading.taxonomy.open.path", "Ielādē sistemātiku - atver ceļu");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Ielādē sistemātiku - saņem mapes");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Ielādē sistemātiku - saņem dokumentus");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Ielādē personālu");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.categories", "Loading categories");
	    lang.put("startup.loading.thesaurus", "Loading thesaurus");
	    lang.put("startup.loading.templates", "Ielādē  šablonus");
	    lang.put("startup.loading.trash", "Ielādē  miskasti");
	    lang.put("startup.loading.history.search", "Ielādē meklēšanas vēsturi");
	    lang.put("startup.loading.user.values", "Ielādē lietotāja parametrus");
	    lang.put("startup.keep.alive", "Ielādē keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Pieejams OpenKM atjauninājums");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Sistemātika");
	    lang.put("leftpanel.label.trash", "Miskaste");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Saglabātie meklēšanas pieprasījumi");
	    lang.put("leftpanel.label.categories", "Categories");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Šabloni");
	    lang.put("leftpanel.label.my.documents", "Mani dokumenti");
	    lang.put("leftpanel.label.user.search", "Lietotāja jaunumi");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Izveidot mapi");
	    lang.put("tree.menu.directory.remove", "Dzēst");
	    lang.put("tree.menu.directory.rename", "Pārsaukt");
	    lang.put("tree.menu.directory.refresh", "Atjaunot");
	    lang.put("tree.menu.directory.move", "Pārvietot");
	    lang.put("tree.menu.directory.copy", "Kopēt");
	    lang.put("tree.menu.directory.add.document", "Pievienot dokumentu");
	    lang.put("tree.menu.add.bookmark", "Pievienot grāmatzīmi");
	    lang.put("tree.menu.set.home", "Uzstādīt noklusētās mājas");
	    lang.put("tree.menu.export", "Eksportēt uz failu");
	    lang.put("tree.status.refresh.folder", "Atjaunina mapju koku");
	    lang.put("tree.status.refresh.create", "Izveidot mapi");
	    lang.put("tree.status.refresh.delete", "Izdzēst mapi");
	    lang.put("tree.status.refresh.rename", "Pārsaukt mapi");
	    lang.put("tree.status.refresh.restore", "Atjaunot mapi");
	    lang.put("tree.status.refresh.purge", "Iztīrīt mapi");
	    lang.put("tree.status.refresh.get", "Atjaunināt mapes saturu");
	    lang.put("tree.folder.new", "Jauna mape");
	    lang.put("tree.status.refresh.add.subscription", "Pievienot abonementu");
	    lang.put("tree.status.refresh.remove.subscription", "Dzēst abonementu");
	    lang.put("tree.status.refresh.get.root", "Atjaunot root mapi");
	    lang.put("tree.status.refresh.get.keywords", "Refreshing keywords");
	    lang.put("tree.status.refresh.get.user.home", "Saņem leitotāja mājas");
	    lang.put("tree.status.refresh.purge.trash", "iztīra miskasti");
	    lang.put("tree.menu.directory.find.folder","Find folder");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Restaurēt");
	    lang.put("trash.menu.directory.purge", "iztīrīt");
	    lang.put("trash.menu.directory.purge.trash", "iztīrīt miskasti");
	    lang.put("trash.directory.select.label", "izvēlieties saņemēja mapi");
	    
	    // General menu
	    lang.put("general.menu.file", "Fails");
	    	lang.put("general.menu.file.exit", "Iziet");
	    	lang.put("general.menu.file.purge.trash", "iztīrīt miskasti");
	    lang.put("general.menu.edit", "Rediģēt");
			lang.put("general.menu.file.create.directory", "Izveidot mapi");
			lang.put("general.menu.file.download.document", "Lejupielādēt dokumentu");
			lang.put("general.menu.file.download.document.pdf", "Lejupielādēt dokumentu kā PDF");
			lang.put("general.menu.file.send.link", "Sūtīt dokumenta saiti");
			lang.put("general.menu.file.send.attachment", "Send document attachment");
			lang.put("general.menu.file.lock", "Slēgt");
			lang.put("general.menu.file.unlock", "Atslēgt");
			lang.put("general.menu.file.add.document", "Pievienot dokumentu");
			lang.put("general.menu.file.checkout", "Izreģistrēt");
			lang.put("general.menu.file.checkin", "Piereģistrēt");
			lang.put("general.menu.file.cancel.checkout", "Atcelt izreģistrēšanu");
			lang.put("general.menu.file.delete", "Dzēst");
			lang.put("general.menu.file.refresh", "Atzsvaidzināt");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "File uploader");
	    lang.put("general.menu.tools", "Rīki");
	    	lang.put("general.menu.tools.languages", "Valodas");
	    	lang.put("general.menu.tools.skin", "Ādas");
    			lang.put("general.menu.tools.skin.default", "Pēc noklusējuma");
    			lang.put("general.menu.tools.skin.default2", "Pēc noklusējuma 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Vidējs fonta lielums");
    			lang.put("general.menu.tools.skin.bigfont", "Liels fonts");
    		lang.put("general.menu.debug.console", "Atkļūdošanas konsole");
    		lang.put("general.menu.administration", "Rādīt administrēšanu");
    		lang.put("general.menu.tools.preferences", "Preferences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Grāmatzīmes");
	    	lang.put("general.menu.bookmark.home", "Mājas");
	    	lang.put("general.menu.bookmark.default.home", "Uzstādīt noklusētās mājas");
	    	lang.put("general.menu.bookmark.edit", "Labot grāmatzīmes");
	    lang.put("general.menu.help", "Palīdzība");
		    lang.put("general.menu.bug.report", "Kļūdu ziņojums");
		    lang.put("general.menu.support.request", "Atbalsta pieprasījums");
		    lang.put("general.menu.public.forum", "Publiskais forums");
		    lang.put("general.menu.project.web", "Projekts internetā");
		    lang.put("general.menu.version.changes", "Versijas piezīmes");
		    lang.put("general.menu.documentation", "Dokumentācija");
		    lang.put("general.menu.about", "Par OpenKM");
	    lang.put("general.connected", "Pieslēdzies kā");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Nosaukums");
	    lang.put("filebrowser.date.update", "Koriģēšanas datums");
	    lang.put("filebrowser.size", "Izmērs");
	    lang.put("filebrowser.path", "Ceļš");
	    lang.put("filebrowser.author", "Autors");
	    lang.put("filebrowser.version", "Versija");
	    lang.put("filebrowser.menu.checkout", "Izreģistrēts");
	    lang.put("filebrowser.menu.checkin", "Piereģistrēts");
	    lang.put("filebrowser.menu.delete", "Dzēst");
	    lang.put("filebrowser.menu.rename", "Pārsaukt");
	    lang.put("filebrowser.menu.checkout.cancel", "Atcelt izreģistrēšanu");
	    lang.put("filebrowser.menu.lock", "Slēgt");
	    lang.put("filebrowser.menu.unlock", "Atslēgt");
	    lang.put("filebrowser.menu.download", "Lejupielādēt");
	    lang.put("filebrowser.menu.move", "Pārvietot");
	    lang.put("filebrowser.menu.copy", "Kopēt");
	    lang.put("filebrowser.menu.create.from.template", "Izveidot no šablona");
	    lang.put("filebrowser.menu.add.property.group", "Pievienot īpašību grupu");
	    lang.put("filebrowser.menu.remove.property.group", "Noņemt īpašību grupu");
	    lang.put("filebrowser.menu.start.workflow", "Sākt darbplūsmu");
	    lang.put("filebrowser.menu.add.subscription", "Pievienot abonementu");
	    lang.put("filebrowser.menu.remove.subscription", "Dzēst abonementu");
	    lang.put("filebrowser.menu.add.bookmark", "Pievienot grāmatzīmi");
	    lang.put("filebrowser.menu.set.home", "Uzstādīt noklusētās mājas");
	    lang.put("filebrowser.menu.refresh", "Atsvaidzināt");
	    lang.put("filebrowser.menu.export", "Eksportēt uz ZIP");
	    lang.put("filebrowser.menu.play", "Atskaņot");
	    lang.put("filebrowser.menu.image.viewer", "Attēlu skatītājs");
	    lang.put("filebrowser.status.refresh.folder", "Atjauno mapju sarakstu");
	    lang.put("filebrowser.status.refresh.document", "Atjauno dokumentu sarakstu");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Dzēš mapi");
	    lang.put("filebrowser.status.refresh.delete.document", "Dzēš dokumentu");
	    lang.put("filebrowser.status.refresh.checkout", "Izreģistrē dokumentu");
	    lang.put("filebrowser.status.refresh.lock", "Slēdz dokumentu");
	    lang.put("filebrowser.status.refresh.unlock", "Atslēdz dokumentu");
	    lang.put("filebrowser.status.refresh.document.rename", "Pārsauc dokumentu");
	    lang.put("filebrowser.status.refresh.folder.rename", "Pārsauc mapi");
	    lang.put("filebrowser.status.refresh.document.purge", "Dzēš dokumentu");
	    lang.put("filebrowser.status.refresh.folder.purge", "Dzēš mapi");
	    lang.put("filebrowser.status.refresh.folder.get", "Atjauno mapi");
	    lang.put("filebrowser.status.refresh.document.get", "Atjauno dokumentu");
	    lang.put("filebrowser.status.refresh.add.subscription", "Pievieno abonēšanu");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Dzēš abonēšanu");
	    lang.put("filebrowser.status.refresh.get.user.home", "Iet uz lietotāja mājām ");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Nosūtīt");
	    lang.put("fileupload.status.sending", "Augšupielādē failu...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "Fails korekti augšupielādēts");
	    lang.put("fileupload.upload.status", "Augšupielādēšanas statuss");
	    lang.put("fileupload.upload.uploaded", "Augšupielādēts");
	    lang.put("fileupload.upload.completed", "Augšupielādēšana pabeigta");
	    lang.put("fileupload.upload.runtime", "Izpildlaiks");
	    lang.put("fileupload.upload.remaining", "Atlicis");
	    lang.put("fileupload.button.close", "Aizvērt");
	    lang.put("fileupload.button.add.other.file", "Pievienot vēl kādu failu");
	    lang.put("fileupload.status.move.file", "Pārvieto failu...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Kopē failu...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "Atjauno failu...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Pārvieto mapi...");
	    lang.put("fileupload.status.copy.folder", "Kopē mapi...");
	    lang.put("fileupload.status.restore.folder", "Atjauno mapi...");
	    lang.put("fileupload.status.create.from.template", "Veido failu no šablona...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Pievienot jaunus dokumentus");
	    lang.put("fileupload.label.update", "Koriģēt dokumentus");
	    lang.put("fileupload.label.users.notify", "Informēt lietotājus");
	    lang.put("fileupload.label.comment", "Komentārs");
	    lang.put("fileupload.label.users.to.notify",  "Informējamie leitotāji");
	    lang.put("fileupload.label.users",  "Lietotāji");
	    lang.put("fileupload.label.groups.to.notify","Groups to notify");
	    lang.put("fileupload.label.groups","Groups");
	    lang.put("fileupload.label.must.select.users",  "Jāizvēlas kāds lietotājs, kuru informēt");
	    lang.put("fileupload.label.importZip", "Importēt Dokumentus no ZIP");
	    lang.put("fileupload.label.notify.comment", "Brīdinājuma ziņa");
	    lang.put("fileupload.label.error.importing.zip", "Kļūda importējot failu");
	    lang.put("fileupload.label.error.move.file", "Kļūda pārvietojot failu");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Kļūda kopējot failu");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "Kļūda atjaunojot failu");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Kļūda pārvietojot mapi");
	    lang.put("fileupload.label.error.copy.folder", "Kļūda kopējot mapi");
	    lang.put("fileupload.label.error.restore.folder", "Kļūda atjaunojot mapi");
	    lang.put("fileupload.label.error.create.from.template", "Kļūda veodojot failu no šablona");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Pārvietot sākuma vai pakļauto mapi nav atļauts");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Pārvietot uz sākuma mapi nav atļauts");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Izveidot failu sākuma mapē nav atļauts");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Īpašības");
	    lang.put("tab.document.notes", "Piezīmes");
	    lang.put("tab.document.history", "Vēsture");
	    lang.put("tab.document.status.history", "Atjauno vēsturi");
	    lang.put("tab.status.security.role", "Atjauno drošības lomas");
	    lang.put("tab.status.security.user", "Atjauno drošības lietotājus");
	    lang.put("tab.document.status.group.properties", "Atjauno īpašibu grupu");
	    lang.put("tab.document.status.set.keywords", "Uzstāda atslēgvārdus");
	    lang.put("tab.document.status.set.categories", "Updating categories");
	    lang.put("tab.document.status.get.version.history.size", "Atsvaidzina dokumenta vēstures izmēru");
	    lang.put("tab.document.status.purge.version.history", "Saspiež dokumenta vēsturi");
	    lang.put("tab.document.status.restore.version", "Atjauno dokumenta versiju");
	    lang.put("tab.document.security", "Drošība");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Īpašības");
	    lang.put("tab.folder.security", "Drošība");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Darbvirsma");
	    lang.put("tab.workspace.search", "Meklēšana");
	    lang.put("tab.workspace.dashboard", "Piezīmju dēlis");
	    lang.put("tab.workspace.administration", "Administrēšana");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Nosaukums");
	    lang.put("document.folder", "Mape");
	    lang.put("document.size", "Izmērs");
	    lang.put("document.created", "izveidots");
	    lang.put("document.lastmodified", "Labots");
	    lang.put("document.mimetype", "MIME/satura tips");
	    lang.put("document.keywords", "Atslēgvārdi");
	    lang.put("document.by", "pēc");
	    lang.put("document.status", "Statuss");
	    lang.put("document.status.checkout", "Rediģējis");
	    lang.put("document.status.locked", "Saslēdzis");
	    lang.put("document.status.normal", "Pieejams");
	    lang.put("document.subscribed", "Abonēts");
	    lang.put("document.subscribed.yes", "Jā");
	    lang.put("document.subscribed.no", "Nē");
	    lang.put("document.history.size", "Vēstures izmērs");
	    lang.put("document.subscribed.users", "Lietotāji, kuri abonējuši");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Pievienot piezīme");
	    lang.put("document.keywords.cloud", "Keywords cloud");
	    lang.put("document.categories", "Categories");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nosaukums");
	    lang.put("folder.parent", "Vecāks");
	    lang.put("folder.created", "Izveidots");
	    lang.put("folder.by", "pēc");
	    lang.put("folder.subscribed", "Abonēts");
	    lang.put("folder.subscribed.yes", "Jā");
	    lang.put("folder.subscribed.no", "Nē");
	    lang.put("folder.subscribed.users", "Lietotāji, kuri abonējuši");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Folders");
	    lang.put("folder.number.documents", "Documents");
	    lang.put("folder.number.mails", "Mails");
	    
	    // Version
	    lang.put("version.name", "Versija");
	    lang.put("version.created", "Datums");
	    lang.put("version.author", "Autors");
	    lang.put("version.size", "Izmērs");
	    lang.put("version.purge.document", "Saspiest vēsturi");
	    lang.put("version.comment", "Komentārs");
	    
	    // Security
	    lang.put("security.label", "Drošība");
	    lang.put("security.group.name", "Grupa");
	    lang.put("security.group.permission.read", "Lasīt");
	    lang.put("security.group.permission.write", "Rakstīt");
	    lang.put("security.group.permission.delete", "Delete");
	    lang.put("security.group.permission.security", "Security");
	    lang.put("security.user.name", "Lietotājs");
	    lang.put("security.user.permission.read", "Lasīt");
	    lang.put("security.user.permission.write", "Rakstīt");
	    lang.put("security.user.permission.delete", "Delete");
	    lang.put("security.user.permission.security", "Security");
	    lang.put("security.users", "Lietotāji");
	    lang.put("security.groups", "Grupas");
	    lang.put("security.recursive", "Rekursīvas tiesību maiņas");
	    lang.put("secutiry.filter.by.users","Users filter");
	    lang.put("secutiry.filter.by.groups","Groups filter");
	    lang.put("security.status.updating","Updating security");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "Sistēma ģenerējusi kļūdu");
	    lang.put("error.invocation", "Kļūda savienojoties ar serveri");
	    
	    // About
	    lang.put("about.label", "Par OpenKM");
	    lang.put("about.loading", "Ielādējam ...");
	    
	    // Logout
	    lang.put("logout.label", "Iziet");
	    lang.put("logout.closed", "OpenKM aizvērts korekti.");
	    lang.put("logout.logout", "OpenKM atslēdzas, uzgaidiet, lūdzu");
	    
	    // Confirm
	    lang.put("confirm.label", "Confirmation");
	    lang.put("confirm.delete.folder", "¿Vai tiešām vēlaties izdzēst šo mapi ?");
	    lang.put("confirm.delete.document", "¿ Vai tiešām vēlaties izdzēst šo dokumentu ?");
	    lang.put("confirm.delete.trash", "¿ Vai tiešām vēlaties iztīrīt miskasti ?");
	    lang.put("confirm.purge.folder", "¿ Vai tiešām vēlaties izdzēst šo mapi ?");
	    lang.put("confirm.purge.document", "¿ Vai tiešām vēlaties izdzēst šo dokumentu ?");
	    lang.put("confirm.delete.propety.group", "¿ Vai tiešām vēlaties izdzēst īpašību grupu ?");
	    lang.put("confirm.purge.version.history.document", "¿ Vai tiešām vēlaties izdzēst dokumenta vēsturi ?");
	    lang.put("confirm.purge.restore.document", "¿ Vai tiešām vēlaties atjaonot šo dokumenta versiju ?");
	    lang.put("confirm.set.default.home", "¿ vai jūs tiešām gribat uzstādīt noklusētās mājas ?");
	    lang.put("confirm.delete.saved.search", "¿ Vai tiešām vēlaties izdzēst saglabātās meklēšanas ?");
	    lang.put("confirm.delete.user.news", "¿ Vai tiešām vēlaties izdzēst lietotāja jaunumus ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    lang.put("confirm.get.pooled.workflow.task","¿ Do you want to assign this task to you ?");
	    
	    // Search inputs
	    lang.put("search.context", "Konteksts");
	    lang.put("search.content", "Saturs");
	    lang.put("search.name", "Nosaukums");
	    lang.put("search.keywords", "Atslēgvārdi");
	    lang.put("search.folder", "Folder");
	    lang.put("search.category", "Category");
	    lang.put("search.results", "Rezultāti");
	    lang.put("search.to", "līdz");
	    lang.put("search.page.results", "Lapas rezultāti");
	    lang.put("search.add.property.group", "Pievienot īpašību grupu");
	    lang.put("search.mimetype", "Mime type");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Paplašinātā meklēšana");
	    lang.put("search.user", "Lietotājs");
	    lang.put("search.date.and", "un");
	    lang.put("search.date.range", "Datumu diapazons starp");
	    lang.put("search.save.as.news", "Saglabāts kā lietotāja jaunumi");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    lang.put("search.category.filter", "Filter by category");
	    
	    // Search results
	    lang.put("search.result.name", "Nosaukumes");
	    lang.put("search.result.score", "Svarīgums");
	    lang.put("search.result.size", "Izmērs");
	    lang.put("search.result.date.update", "Koriģēšanas datums");
	    lang.put("search.result.author", "Autors");
	    lang.put("search.result.version", "Versija");
	    lang.put("search.result.path", "Ceļš");
	    lang.put("search.result.menu.download", "Lejupielādēt");
	    lang.put("search.result.menu.go.folder", "Iet uz mapi");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Koriģējam meklēšanu");
	    lang.put("search.result.status.runsearch", "Koriģējam saglabāto meklēšanu");
	    
	    // Search saved
	    lang.put("search.saved.run", "Izpildīt");
	    lang.put("search.saved.delete", "Dzēst");
	    lang.put("search.saved.status.getsearchs", "Atsvaidzina saglabātās meklēšanas");
	    lang.put("search.saved.status.savesearch", "Koriģē saglabātās meklēšanas");
	    lang.put("search.saved.status.deletesearch", "Dzēš saglabātās meklēšanas");
	    lang.put("search.saved.status.getusernews", "Atsvaidzina lietotāja ziņas");
	    
	    // Button
	    lang.put("button.close", "Aizvērt");
	    lang.put("button.logout", "Atslēgties");
	    lang.put("button.update", "Koriģēt");
	    lang.put("button.cancel", "Atcelt");
	    lang.put("button.accept", "Apstiprināt");
	    lang.put("button.restore", "Restaurēt");
	    lang.put("button.move", "Pārvietot");
	    lang.put("button.change", "Mainīt");
	    lang.put("button.search", "Meklēt");
	    lang.put("button.save.search", "Saglabāt  meklēšanu");
	    lang.put("button.view", "Skatīt");
	    lang.put("button.clean", "Tīrīt");
	    lang.put("button.add", "Pievienot");
	    lang.put("button.delete", "Dzēst");
	    lang.put("button.copy", "Kopēt");
	    lang.put("button.create", "Izveidot");
	    lang.put("button.show", "Parādīt");
	    lang.put("button.memory", "Koriģēt");
	    lang.put("button.copy.clipboard", "Kopēt uz starpliktuvi");	
	    lang.put("button.start", "Sākt");
	    lang.put("button.select", "Select");
	    lang.put("button.test", "Test");
	    lang.put("button.next", "Next");
	    
	    // Group
	    lang.put("group.label", "Pievienot īpašību grupu");
	    lang.put("group.group", "Grupa");
	    lang.put("group.property.group", "Īpašība");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Pievienot grāmatzīmi");
	    lang.put("bookmark.name", "Nosaukums");
	    lang.put("bookmark.edit.label", "Labot grāmatzīmes");
	    lang.put("bookmark.path", "Ceļš");
	    lang.put("bookmark.type", "Tips");
	    lang.put("bookmark.type.document", "Dokuments");
	    lang.put("bookmark.type.folder", "Mape");
	    
	    // Notify
	    lang.put("notify.label", "Sūta dokumenta saiti");
	    lang.put("notify.label.attachment", "Send document attachment");
	    
	    // Status
	    lang.put("status.document.copied", "Dokuments atzīmēts kopēšanai");
	    lang.put("status.document.cut", "Dokuments atzīmēts izgriezšanai");
	    lang.put("status.folder.copied", "Mape atzīmēta kopēšanai");
	    lang.put("status.folder.cut", "Mape atzīmēta izgriezšanai");
	    lang.put("status.keep.alive.error", "Savienojums ar serveri pazudis (keep alive)");
	    lang.put("status.debug.enabled", "Atkļūdošana atļauta");
	    lang.put("status.debug.disabled", "Atkļūdošana aizliegta");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Svētdiena");
	    lang.put("calendar.day.monday", "Pirmdiena");
	    lang.put("calendar.day.tuesday", "Otrdiena");
	    lang.put("calendar.day.wednesday", "Trešdiena");
	    lang.put("calendar.day.thursday", "Ceturtdiena");
	    lang.put("calendar.day.friday", "Piektdiena");
	    lang.put("calendar.day.saturday", "Sestdiena");
	    lang.put("calendar.month.january", "Janvāris");
	    lang.put("calendar.month.february", "Februāris");
	    lang.put("calendar.month.march", "Marts");
	    lang.put("calendar.month.april", "Aprīlis");
	    lang.put("calendar.month.may", "Maijs");
	    lang.put("calendar.month.june", "Jūnijs");
	    lang.put("calendar.month.july", "Jūlijs");
	    lang.put("calendar.month.august", "Augusts");
	    lang.put("calendar.month.september", "Septembris");
	    lang.put("calendar.month.october", "Oktobris");
	    lang.put("calendar.month.november", "Novembris");
	    lang.put("calendar.month.december", "Decembris");
	    
	    // Media player
	    lang.put("media.player.label", "Media player");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Attēlu skatītājs");
	    lang.put("image.viewer.zoom.in", "Tuvināt");
	    lang.put("image.viewer.zoom.out", "Tālināt");
	    
	    // Debug console
	    lang.put("debug.console.label", "Debug console");
	    lang.put("debug.enable.disable", "CTRL+Z to enable or disable debug mode");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "General");
	    lang.put("dashboard.tab.news", "News");
	    lang.put("dashboard.tab.user", "User");
	    lang.put("dashboard.tab.workflow", "Workflow");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Keyword map");
	    
	    // Dashboard general
	    lang.put("dashboard.new.items", "Jauns");
	    lang.put("dashboard.user.locked.documents", "Slēgtie dokumenti");
	    lang.put("dashboard.user.checkout.documents", "Izregistrētie dokumenti");
	    lang.put("dashboard.user.last.modified.documents", "Pēdējie labotie dokumenti");
	    lang.put("dashboard.user.last.downloaded.documents", "Pēdējie lejupielādētie dokumenti");
	    lang.put("dashboard.user.subscribed.documents", "Abonēti dokumenti");
	    lang.put("dashboard.user.subscribed.folders", "Abonētās mapes");
	    lang.put("dashboard.user.last.uploaded.documents", "Pēdējie augšupielādētie dokumenti");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Iepriekšējās nedēļas visvairāk skatītie dokumenti");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Iepriekšējā mēneša visvairāk skatītie dokumenti");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Iepriekšējās nedēļas visvairāk labotie dokumenti");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Iepriekšējā mēneša visvairāk labotie dokumenti");
	    lang.put("dashboard.general.last.uploaded.documents", "Pēdējie augšupielādētie dokumenti");
	    lang.put("dashboard.workflow.pending.tasks", "Nepabeigtie uzdevumi");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Unassigned pending tasks");
	    lang.put("dashboard.workflow.task", "Uzdevums");
	    lang.put("dashboard.workflow.task.id", "Identifikators");
	    lang.put("dashboard.workflow.task.name", "Nosaukums");
	    lang.put("dashboard.workflow.task.created", "Radīšanas datums");
	    lang.put("dashboard.workflow.task.start", "Sākuma datums");
	    lang.put("dashboard.workflow.task.duedate", "Līdz datumam");
	    lang.put("dashboard.workflow.task.end", "Beigu datums");
	    lang.put("dashboard.workflow.task.description", "Apraksts");
	    lang.put("dashboard.workflow.task.process.instance", "Procesa instance");
	    lang.put("dashboard.workflow.task.process.id", "Identifikators");
	    lang.put("dashboard.workflow.task.process.version", "Versija");
	    lang.put("dashboard.workflow.task.process.name", "Nosaukums");
	    lang.put("dashboard.workflow.task.process.description", "Apraksts");
	    lang.put("dashboard.workflow.task.process.data", "Dati");
	    lang.put("dashboard.workflow.comments", "Comments");
	    lang.put("dashboard.workflow.task.process.forms", "Form");
	    lang.put("dashboard.workflow.add.comment","Add comment");
	    lang.put("dashboard.workflow.task.process.definition", "Procesa definīcija");
	    lang.put("dashboard.workflow.task.process.path", "Ceļš");
	    lang.put("dashboard.refreshing", "Atsvaidzināt");
	    lang.put("dashboard.keyword", "Keywords");
	    lang.put("dashboard.keyword.suggest", "Type the keyword");
	    lang.put("dashboard.keyword.all", "All keywords");
	    lang.put("dashboard.keyword.top", "Top keywords");
	    lang.put("dashboard.keyword.related", "Related keywords");
	    lang.put("dashboard.keyword.goto.document", "Go to document");
	    lang.put("dashboard.keyword.clean.keywords", "Clean keywords");
	    lang.put("dashboard.mail.last.imported.mails", "Electronic mails");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Attachments");
	    
	    // Workflow
	    lang.put("workflow.label", "Sākam darbplūsmu");
	    
	    // User configuration
	    lang.put("user.preferences.label", "User configuration");
	    lang.put("user.preferences.user", "User");
	    lang.put("user.preferences.password", "Password");
	    lang.put("user.preferences.mail", "E-mail");
	    lang.put("user.preferences.imap.host", "IMAP server");
	    lang.put("user.preferences.imap.user", "IMAP user name");
	    lang.put("user.preferences.imap.user.password", "IMAP user password");
	    lang.put("user.preferences.imap.folder", "IMAP folder");
	    lang.put("user.preferences.password.error", "Error: passwords are diferent");
	    lang.put("user.preferences.user.data", "User account");
	    lang.put("user.preferences.mail.data", "Mail account");
	    lang.put("user.preferences.imap.error", "All fields are obligatory to set the mail configurations");
	    lang.put("user.preferences.imap.password.error.void", "Password must not be empty on IMAP mail creation");
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
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Pieeja dokumentam liegta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Dokumenta slēgšana liegta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Glabātuves iekšējā kļūda");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Dokumenta ceļš nav atrasts");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Version error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Pieeja dokumentam liegta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Dokuments nav atrasts");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Dokuments jua eksistē");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Dokumenta slēgšana liegta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Dokumenta atslēgšana atļauta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Glabātuves iekšējā kļūda");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Aplikācijas iekšējā kļūda");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Dokumenta ceļš nav atrasts");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Pieeja mapei liegta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Mape nav atrasta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Šāda mape jau eksistē");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Glabātuves iekšējā kļūda");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Glabātuves iekšējā kļūda");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Mapes ceļš nav atrasts");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_DatabaseException, "Database error");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_AccessDenied, "Pieeja vienībai liegta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_ItemNotFound, "Vienība nav atrasta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_Repository, "Glabātuves iekšējā kļūda");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_General, "Glabātuves iekšējā kļūda");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Dokuments nav atrasts");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Glabātuves iekšējā kļūda");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Neatbalstīts faila formāts");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Šāds dokuments jau eksistē");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Pārsniegts dokumenta izmērs");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Sesija aizvērta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Kļūda izpildot pieprasījumu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Saglabātās meklēšanas nosaukumam jābūt unikālam");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Grāmatzīmes nosaukumam jābūt unikālam");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Sesija zudusi");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Glabātuves iekšējā kļūda");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Glabātuves iekšējā kļūda");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Ievades/izvades kļūda");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Glabātuves iekšējā kļūda");
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
