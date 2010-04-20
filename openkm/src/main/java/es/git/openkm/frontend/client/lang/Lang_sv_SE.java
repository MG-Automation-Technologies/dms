/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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
 * Swedish (Sweden) 
 * 
 * @author Torgrim Oldenburg
 */
public class Lang_sv_SE {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "yyyy-MM-dd HH:mm:ss");
	    lang.put("general.day.pattern", "yyyy-MM-dd");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Laddar OpenKM");
	    lang.put("startup.starting.loading", "Startar laddning av OpenKM");
	    lang.put("startup.taxonomy", "Organiserar root noden");
	    lang.put("startup.template", "Hämtar template från root noden");
	    lang.put("startup.personal", "Hämtar personlig root nod");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Hämtar papperskorgens root nod");
	    lang.put("startup.user.home", "Hämtar användarens root nod");
	    lang.put("startup.bookmarks", "Hämtar bokmärken");
	    lang.put("startup.loading.taxonomy", "Laddar organisation");
	    lang.put("startup.loading.taxonomy.getting.folders", "Laddar organisation - hämtar mappar");
	    lang.put("startup.loading.taxonomy.eval.params", "Laddar organisation - validerar webläsarparametrar");
	    lang.put("startup.loading.taxonomy.open.path", "Laddar organisation - öppnar sökväg");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Laddar organisering - hämtar mappar");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Laddar organisering - hämtar document");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Laddar användare");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.templates", "Laddar mallar");
	    lang.put("startup.loading.trash", "Laddar papperskorg");
	    lang.put("startup.loading.history.search", "Laddar sökhistorik");
	    lang.put("startup.loading.user.values", "Laddar användarvärden");
	    lang.put("startup.loading.property.group.translations", "Laddar egenskaper för gruppöversättningar");
	    lang.put("startup.keep.alive", "Laddar keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Det finns en uppdatering av OpenKM tillgänglig");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Organisation");
	    lang.put("leftpanel.label.trash", "Papperskorg");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Sparade sökningar");
	    lang.put("leftpanel.label.templates", "Mallar");
	    lang.put("leftpanel.label.my.documents", "Mina dokument");
	    lang.put("leftpanel.label.user.search", "User news");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Skapa mapp");
	    lang.put("tree.menu.directory.remove", "Ta bort");
	    lang.put("tree.menu.directory.rename", "Byt namn");
	    lang.put("tree.menu.directory.refresh", "Uppdatera");
	    lang.put("tree.menu.directory.move", "Flytta");
	    lang.put("tree.menu.directory.copy", "Kopiera");
	    lang.put("tree.menu.directory.add.document", "Lägg till dokument");
	    lang.put("tree.menu.add.bookmark", "Lägg till bokmärke");
	    lang.put("tree.menu.set.home", "Ställ in hemkatalog");
	    lang.put("tree.menu.export", "Exportera till fil");
	    lang.put("tree.status.refresh.folder", "Uppdaterar mappträd");
	    lang.put("tree.status.refresh.create", "Skapar mappar");
	    lang.put("tree.status.refresh.delete", "Tar bort mappar");
	    lang.put("tree.status.refresh.rename", "Byter namn på mappar");
	    lang.put("tree.status.refresh.restore", "Återskapar mappar");
	    lang.put("tree.status.refresh.purge", "Rensar mappar");
	    lang.put("tree.status.refresh.get", "Updaterar mappar");
	    lang.put("tree.folder.new", "Ny mapp");
	    lang.put("tree.status.refresh.add.subscription", "Lägger till prenumerationer");
	    lang.put("tree.status.refresh.remove.subscription", "Tar bort prenumerationer");
	    lang.put("tree.status.refresh.get.root", "Uppdaterar root mappen");
	    lang.put("tree.status.refresh.get.user.home", "Hämtar användarnas hemkataloger");
	    lang.put("tree.status.refresh.purge.trash", "Rensar papperskorgen");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Återskapa");
	    lang.put("trash.menu.directory.purge", "Töm");
	    lang.put("trash.menu.directory.purge.trash", "Töm papperskorgen");
	    lang.put("trash.directory.select.label", "Välj destinationsmapp");
	    
	    // General menu
	    lang.put("general.menu.file", "Fil");
	    	lang.put("general.menu.file.exit", "Avsluta");
	    	lang.put("general.menu.file.purge.trash", "Töm papperskorgen");
	    lang.put("general.menu.edit", "Editera");
			lang.put("general.menu.file.create.directory", "Skapar mappar");
			lang.put("general.menu.file.download.document", "Ladda ned dokument");
			lang.put("general.menu.file.download.document.pdf", "Download document as PDF");
			lang.put("general.menu.file.send.link", "Skicka dokumentlänk");
			lang.put("general.menu.file.lock", "Lås");
			lang.put("general.menu.file.unlock", "Lås upp");
			lang.put("general.menu.file.add.document", "Lägg till dokument");
			lang.put("general.menu.file.checkout", "Checka ut");
			lang.put("general.menu.file.checkin", "Checka in");
			lang.put("general.menu.file.cancel.checkout", "Avbryta utcheckning");
			lang.put("general.menu.file.delete", "Ta bort");
			lang.put("general.menu.file.refresh", "Uppdatera");
			lang.put("general.menu.file.scanner", "Scanner");
	    lang.put("general.menu.tools", "Verktyg");
	    	lang.put("general.menu.tools.languages", "Språk");
	    	lang.put("general.menu.tools.skin", "Teman");
    			lang.put("general.menu.tools.skin.default", "Som standard");
    			lang.put("general.menu.tools.skin.default2", "Som standard 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Medium typsnitt");
    			lang.put("general.menu.tools.skin.bigfont", "Stort typsnitt");
    		lang.put("general.menu.debug.console", "Avbuggningskonsol");
    		lang.put("general.menu.administration", "Visa administration");
    		lang.put("general.menu.tools.preferences", "Prefererences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Bokmärken");
	    	lang.put("general.menu.bookmark.home", "Hem");
	    	lang.put("general.menu.bookmark.default.home", "Sätt som hemkatalog");
	    	lang.put("general.menu.bookmark.edit", "Redigera bokmärken");
	    lang.put("general.menu.help", "Hjälp");
		    lang.put("general.menu.bug.report", "Buggrapport");
		    lang.put("general.menu.support.request", "Supportärende");
		    lang.put("general.menu.public.forum", "Publikt forum");
		    lang.put("general.menu.project.web", "Projekt web");
		    lang.put("general.menu.version.changes", "Versionsanteckningar");
		    lang.put("general.menu.documentation", "Dokumentation");
		    lang.put("general.menu.about", "Om OpenKM");
	    lang.put("general.connected", "Ansluten som");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Namn");
	    lang.put("filebrowser.date.update", "Uppdateringsdatum");
	    lang.put("filebrowser.size", "Storlek");
	    lang.put("filebrowser.path", "Sökväg");
	    lang.put("filebrowser.author", "Författare");
	    lang.put("filebrowser.version", "Version");
	    lang.put("filebrowser.menu.checkout", "Checka ut");
	    lang.put("filebrowser.menu.checkin", "Checka in");
	    lang.put("filebrowser.menu.delete", "Ta bort");
	    lang.put("filebrowser.menu.rename", "Byt namn");
	    lang.put("filebrowser.menu.checkout.cancel", "Avbryt checka ut");
	    lang.put("filebrowser.menu.lock", "Lås");
	    lang.put("filebrowser.menu.unlock", "Lås upp");
	    lang.put("filebrowser.menu.download", "Ladda ned");
	    lang.put("filebrowser.menu.move", "Flytta");
	    lang.put("filebrowser.menu.copy", "Kopiera");
	    lang.put("filebrowser.menu.create.from.template", "Skapa från mall");
	    lang.put("filebrowser.menu.add.property.group", "Lägg till ");
	    lang.put("filebrowser.menu.remove.property.group", "Tar bort egenskapsgrupp");
	    lang.put("filebrowser.menu.start.workflow", "Start workflow");
	    lang.put("filebrowser.menu.add.subscription", "Lägg till prenumeration");
	    lang.put("filebrowser.menu.remove.subscription", "Ta bort prenumeration");
	    lang.put("filebrowser.menu.add.bookmark", "Lägg till bokmärke");
	    lang.put("filebrowser.menu.set.home", "Ställ in hemkatalog");
	    lang.put("filebrowser.menu.refresh", "Uppdatera");
	    lang.put("filebrowser.menu.export", "Exportera till ZIP");
	    lang.put("filebrowser.menu.play", "Spela upp");
	    lang.put("filebrowser.menu.image.viewer", "Bildvisare");
	    lang.put("filebrowser.status.refresh.folder", "Uppdaterar mapplistan");
	    lang.put("filebrowser.status.refresh.document", "Uppdaterar dokumentlistan");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Tar bort mapp");
	    lang.put("filebrowser.status.refresh.delete.document", "Tar bort dokument");
	    lang.put("filebrowser.status.refresh.checkout", "Checkar in dokument");
	    lang.put("filebrowser.status.refresh.lock", "Lås dokument");
	    lang.put("filebrowser.status.refresh.unlock", "Lås upp dokument");
	    lang.put("filebrowser.status.refresh.document.rename", "Byt namnt på dokument");
	    lang.put("filebrowser.status.refresh.folder.rename", "Byt namn på mapp");
	    lang.put("filebrowser.status.refresh.document.purge", "Ta bort dokument");
	    lang.put("filebrowser.status.refresh.folder.purge", "Ta bort mapp");
	    lang.put("filebrowser.status.refresh.folder.get", "Uppdatera mapp");
	    lang.put("filebrowser.status.refresh.document.get", "Uppdatera dokument");
	    lang.put("filebrowser.status.refresh.add.subscription", "Lägger till prenumeration");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Tar bort prenumeration");
	    lang.put("filebrowser.status.refresh.get.user.home", "Hämtar användarens hemkatalog");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Skicka");
	    lang.put("fileupload.status.sending", "Laddar upp fil...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "Filen uppladdades korrekt");
	    lang.put("fileupload.upload.status", "Status på uppladdning");
	    lang.put("fileupload.upload.uploaded", "Uppladdad");
	    lang.put("fileupload.upload.completed", "Uppladdningen klar");
	    lang.put("fileupload.upload.runtime", "Körtid");
	    lang.put("fileupload.upload.remaining", "Kvarstår");
	    lang.put("fileupload.button.close", "Stäng");
	    lang.put("fileupload.button.add.other.file", "Lägg till en annan fil");
	    lang.put("fileupload.status.move.file", "Flytta fil...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Kopiera fil...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "Återskapa fil...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Flytta mapp...");
	    lang.put("fileupload.status.copy.folder", "Kopiera mapp...");
	    lang.put("fileupload.status.restore.folder", "Återskapa mapp...");
	    lang.put("fileupload.status.create.from.template", "Skapar fil från mall...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Lägg till dokument");
	    lang.put("fileupload.label.update", "Uppdatera dokument");
	    lang.put("fileupload.label.users.notify", "Meddela användarna");
	    lang.put("fileupload.label.comment", "Kommentera");
	    lang.put("fileupload.label.users.to.notify",  "Användare att meddela");
	    lang.put("fileupload.label.users",  "Användare");
	    lang.put("fileupload.label.must.select.users",  "Du måste välja användare att meddela");
	    lang.put("fileupload.label.importZip", "Import Documents from ZIP");
	    lang.put("fileupload.label.notify.comment", "Notifieringsmeddelande");
	    lang.put("fileupload.label.error.importing.zip", "Fel vid import av fil");
	    lang.put("fileupload.label.error.move.file", "Fel vid flyttande av fil");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Fel vid kopiering av fil");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "Fel vid återskapande av fil");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Fel vid flyttande av mapp");
	    lang.put("fileupload.label.error.copy.folder", "Fel vid kopiering av mapp");
	    lang.put("fileupload.label.error.restore.folder", "Fel vid återskapande av mapp");
	    lang.put("fileupload.label.error.create.from.template", "Fel vid skapande av fil från mall");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Du har inte rättighet att flytta från ursprungs till underliggande mapp");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Du har inte rättighet att flytta från ursprungsmappen");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Du har inte tillstånd att skapa fil i ursprungsmappen");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Inställningar");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "Historik");
	    lang.put("tab.document.status.history", "Uppdatera historik");
	    lang.put("tab.status.security.role", "Uppdatera säkerhetsroller");
	    lang.put("tab.status.security.user", "Uppdatera säkerhetsanvändare");
	    lang.put("tab.document.status.group.properties", "Uppdatera egenskapsgrupp");
	    lang.put("tab.document.status.set.keywords", "Sätt in nyckelord");
	    lang.put("tab.document.status.get.version.history.size", "Uppdatera dokumentethistorikens storlek");
	    lang.put("tab.document.status.purge.version.history", "Sammanpressa dokumenthistoriken");
	    lang.put("tab.document.status.restore.version", "Återskapa dokumentversion");
	    lang.put("tab.document.security", "Säkerhet");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Egenskaper");
	    lang.put("tab.folder.security", "Säkerhet");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Skrivbord");
	    lang.put("tab.workspace.search", "Sök");
	    lang.put("tab.workspace.dashboard", "DashBoard");
	    lang.put("tab.workspace.administration", "Administration");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Namn");
	    lang.put("document.folder", "Mapp");
	    lang.put("document.size", "Storlek");
	    lang.put("document.created", "Skapad");
	    lang.put("document.lastmodified", "Modifierad");
	    lang.put("document.mimetype", "MIME typ");
	    lang.put("document.keywords", "Nyckelord");
	    lang.put("document.by", "av");
	    lang.put("document.status", "Status");
	    lang.put("document.status.checkout", "Editerad av");
	    lang.put("document.status.locked", "Låst av");
	    lang.put("document.status.normal", "Tillgänglig");
	    lang.put("document.subscribed", "Prenumererad");
	    lang.put("document.subscribed.yes", "Ja");
	    lang.put("document.subscribed.no", "Nej");
	    lang.put("document.history.size", "Historikstorlek");
	    lang.put("document.subscribed.users", "Prenumererande användare");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Add note");
	    lang.put("document.keywords.cloud", "Keywords cloud");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Namn");
	    lang.put("folder.parent", "Överliggande");
	    lang.put("folder.created", "Skapad");
	    lang.put("folder.by", "av");
	    lang.put("folder.subscribed", "Prenumererad");
	    lang.put("folder.subscribed.yes", "Ja");
	    lang.put("folder.subscribed.no", "Nej");
	    lang.put("folder.subscribed.users", "Prenumererande användare");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Version");
	    lang.put("version.created", "Datum");
	    lang.put("version.author", "Författare");
	    lang.put("version.size", "Storlek");
	    lang.put("version.purge.document", "Compact history");
	    lang.put("version.comment", "Kommentar");
	    
	    // Security
	    lang.put("security.label", "Säkerhet");
	    lang.put("security.group.name", "Grupp");
	    lang.put("security.group.permission.read", "Läs");
	    lang.put("security.group.permission.write", "Skriv");
	    lang.put("security.user.name", "Användare");
	    lang.put("security.user.permission.read", "Läs");
	    lang.put("security.user.permission.write", "Skriv");
	    lang.put("security.users", "Användare");
	    lang.put("security.groups", "Grupper");
	    lang.put("security.recursive", "Rekursiva behörighetsförändringar");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "Systemet har genererat ett fel");
	    lang.put("error.invocation", "Fel vid kommunikation med servern");
	    
	    // About
	    lang.put("about.label", "Om OpenKM");
	    lang.put("about.loading", "Laddar ...");
	    
	    // Logout
	    lang.put("logout.label", "Avsluta");
	    lang.put("logout.closed", "OpenKM har avslutats korrekt.");
	    lang.put("logout.logout", "OpenKM avslutas, vänligen vänta");
	    
	    // Confirm
	    lang.put("confirm.label", "Bekräfta");
	    lang.put("confirm.delete.folder", "Är du säker på att du vill ta bort mappen?");
	    lang.put("confirm.delete.document", "Är du säker på att du vill ta bort filen?");
	    lang.put("confirm.delete.trash", "Är du säker på att du vill ta bort papperskorgen?");
	    lang.put("confirm.purge.folder", "Är du säker på att du vill ta bort mapparna?");
	    lang.put("confirm.purge.document", "Är du säker på att du vill ta bort filerna?");
	    lang.put("confirm.delete.propety.group", "Är du säker på att du vill ta bort egenskapsgrupp?");
	    lang.put("confirm.purge.version.history.document", "Är du säker på att du vill ta bort dokumenthistoriken?");
	    lang.put("confirm.purge.restore.document", "Är du säker på att du vill återskapa till denna version av dokumentet?");
	    lang.put("confirm.set.default.home", "Är du säker på att du vill ställa hemkatalogen?");
	    lang.put("confirm.delete.saved.search", "¿ Do you really want to delete saved search ?");
	    lang.put("confirm.delete.user.news", "¿ Do you really want to delete user news ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    lang.put("confirm.get.pooled.workflow.task","¿ Do you want to assign this task to you ?");
	    
	    // Search inputs
	    lang.put("search.context", "Kontext");
	    lang.put("search.content", "Innehåll");
	    lang.put("search.name", "Namn");
	    lang.put("search.keywords", "Nyckelord");
	    lang.put("search.folder", "Folder");
	    lang.put("search.results", "Resultat");
	    lang.put("search.to", "till");
	    lang.put("search.page.results", "Sökresultat");
	    lang.put("search.add.property.group", "Lägg till egenskapsgrupp");
	    lang.put("search.mimetype", "Mime-typ");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Avancerad sökning");
	    lang.put("search.user", "Användare");
	    lang.put("search.date.and", "och");
	    lang.put("search.date.range", "Mellan datumen");
	    lang.put("search.save.as.news", "Save as user news");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    
	    // Search results
	    lang.put("search.result.name", "Namn");
	    lang.put("search.result.score", "Relevans");
	    lang.put("search.result.size", "Storlek");
	    lang.put("search.result.date.update", "Uppdatera datum");
	    lang.put("search.result.author", "Författare");
	    lang.put("search.result.version", "Version");
	    lang.put("search.result.path", "Sökväg");
	    lang.put("search.result.menu.download", "Ladda ner");
	    lang.put("search.result.menu.go.folder", "Gå till mapp");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Uppdaterar sökning");
	    lang.put("search.result.status.runsearch", "Uppdaterar sparad sökning");
	    
	    // Search saved
	    lang.put("search.saved.run", "Kör");
	    lang.put("search.saved.delete", "Ta bort");
	    lang.put("search.saved.status.getsearchs", "Uppdaterar sparade sökningar");
	    lang.put("search.saved.status.savesearch", "Uppdaterar sparad sökning");
	    lang.put("search.saved.status.deletesearch", "Tar bort sparad sökning");
	    lang.put("search.saved.status.getusernews", "Refreshing user news");
	    
	    // Button
	    lang.put("button.close", "Stäng");
	    lang.put("button.logout", "Logga ut");
	    lang.put("button.update", "Uppdatera");
	    lang.put("button.cancel", "Avbryt");
	    lang.put("button.accept", "Acceptera");
	    lang.put("button.restore", "Återskapa");
	    lang.put("button.move", "Flytta");
	    lang.put("button.change", "Ändra");
	    lang.put("button.search", "Sök");
	    lang.put("button.save.search", "Spara sökning");
	    lang.put("button.view", "Visa");
	    lang.put("button.clean", "Rensa");
	    lang.put("button.add", "Lägg till");
	    lang.put("button.delete", "Ta bort");
	    lang.put("button.copy", "Kopiera");
	    lang.put("button.create", "Skapa");
	    lang.put("button.show", "Visa");
	    lang.put("button.memory", "Upptadera");
	    lang.put("button.copy.clipboard", "Copy to clipboard");	
	    lang.put("button.start", "Start");
	    lang.put("button.select", "Select");
	    
	    // Group
	    lang.put("group.label", "Lägg till egenskapsgrupp");
	    lang.put("group.group", "Grupp");
	    lang.put("group.property.group", "Egenskaper");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Lägg till bokmärke");
	    lang.put("bookmark.name", "Namm");
	    lang.put("bookmark.edit.label", "Editera bokmärken");
	    lang.put("bookmark.path", "Sökväg");
	    lang.put("bookmark.type", "Typ");
	    lang.put("bookmark.type.document", "Dokument");
	    lang.put("bookmark.type.folder", "Mapp");
	    
	    // Notify
	    lang.put("notify.label", "Skickar dokumentlänk");
	    
	    // Status
	    lang.put("status.document.copied", "Dokument markerad som 'kopiera'");
	    lang.put("status.document.cut", "Dokument markerad som 'klipp ut'");
	    lang.put("status.folder.copied", "Mapp markerad som 'kopiera'");
	    lang.put("status.folder.cut", "Mapp markerad som 'klipp ut'");
	    lang.put("status.keep.alive.error", "Har upptäckt en förlorad kontakt med servern (keep alive)");
	    lang.put("status.debug.enabled", "Avbuggning aktiverad");
	    lang.put("status.debug.disabled", "Avbuggning avaktiverad");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Söndag");
	    lang.put("calendar.day.monday", "Måndag");
	    lang.put("calendar.day.tuesday", "Tisdag");
	    lang.put("calendar.day.wednesday", "Onsdag");
	    lang.put("calendar.day.thursday", "Torsdag");
	    lang.put("calendar.day.friday", "Fredag");
	    lang.put("calendar.day.saturday", "Lördag");
	    lang.put("calendar.month.january", "Januari");
	    lang.put("calendar.month.february", "Februari");
	    lang.put("calendar.month.march", "Mars");
	    lang.put("calendar.month.april", "April");
	    lang.put("calendar.month.may", "Maj");
	    lang.put("calendar.month.june", "Juni");
	    lang.put("calendar.month.july", "Juli");
	    lang.put("calendar.month.august", "Augusti");
	    lang.put("calendar.month.september", "September");
	    lang.put("calendar.month.october", "Oktober");
	    lang.put("calendar.month.november", "November");
	    lang.put("calendar.month.december", "December");
	    
	    // Media player
	    lang.put("media.player.label", "Mediaspelare");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Bildvisare");
	    lang.put("image.viewer.zoom.in", "Zooma in");
	    lang.put("image.viewer.zoom.out", "Zooma out");
	    
	    // Debug console
	    lang.put("debug.console.label", "Debug konsol");
	    lang.put("debug.enable.disable", "CTRL+Z för att aktivera och avaktivera avbuggningsläge");

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
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Unassigned pending tasks");
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
	    lang.put("dashboard.workflow.comments", "Comments");
	    lang.put("dashboard.workflow.task.process.forms", "Form");
	    lang.put("dashboard.workflow.add.comment","Add comment");
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
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Åtkomst vägrad till dokument");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Dokumentet hittades inte");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Dokumentet finns redan");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Åtkomst vägrad att låsa dokument");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Upplåsning av dokument begärt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Internt fel i dokumentlager");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Internt applikationsfel");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Dokumentets sökväg hittades inte");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Åtkomst vägrad till mapp");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Mappen hittades inte");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Mappen finns redan");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Internt fel i dokumentlager");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Internt applikationsfel");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Dokumentets sökväg hittades inte");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Åtkomst vägrad till objekt");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Objektet hittades inte");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Internt fel i dokumentlager");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Internt applikationsfel");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Dokumentet hittas inte");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Internt applikationsfel");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Filformatet stöds ej");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Dokumentet finns redan");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Dokumentets storlek överskreds");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Sessionen avslutad");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Allmänt fel att köra sökningen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Sparat söknamn måste vara unikt");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Namnet på bokmärket måste vara unikt");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Sessionen tappad");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Internt fel i dokumentlager");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Internt fel i dokumentlager");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Internt fel i dokumentlager");
	  }
}