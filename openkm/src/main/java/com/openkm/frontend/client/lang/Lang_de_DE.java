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
 * German (Germany)
 * 
 * @author Matthias Scholz
 */
public class Lang_de_DE {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd.MM.yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd.MM.yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Lade OpenKM");
	    lang.put("startup.starting.loading", "Starte das Laden von OpenKM");
	    lang.put("startup.taxonomy", "Hole Taxonomie Wurzelknoten");
	    lang.put("startup.categories", "Getting categories root node");
	    lang.put("startup.thesaurus", "Getting thesaurus root node");
	    lang.put("startup.template", "Hole Vorlagen Wurzelknoten");
	    lang.put("startup.personal", "Hole persönlichen Wurzelknoten ");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Hole Papierkorb Wurzelknoten");
	    lang.put("startup.user.home", "Hole Benutzer eigenen Knoten");
	    lang.put("startup.bookmarks", "Hole Lesezeichen");
	    lang.put("startup.loading.taxonomy", "Lade Taxonomie");
	    lang.put("startup.loading.taxonomy.getting.folders", "Lade Taxonomie - Hole Ordner");
	    lang.put("startup.loading.taxonomy.eval.params", "Lade Taxonomie - werte Browser Parameter aus");
	    lang.put("startup.loading.taxonomy.open.path", "Lade Taxonomie - öffne Pfad");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Lade Taxonomie - hole Ordner");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Lade Taxonomie - hole Dokumente");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Lade persönliche");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.categories", "Loading categories");
	    lang.put("startup.loading.thesaurus", "Loading thesaurus");
	    lang.put("startup.loading.templates", "Lade Vorlagen");
	    lang.put("startup.loading.trash", "Lade Papierkorb");
	    lang.put("startup.loading.history.search", "Lade historische Suchen");
	    lang.put("startup.loading.user.values", "Lade Benutzerwerte");
	    lang.put("startup.keep.alive", "Lade Lebenszeichen");
	    
	    // Update notification
	    lang.put("openkm.update.avaliable", "OpenKM Update verfügbar");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Taxonomie");
	    lang.put("leftpanel.label.trash", "Papierkorb");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Gespeicherte Suchen");
	    lang.put("leftpanel.label.categories", "Categories");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Vorlagen");
	    lang.put("leftpanel.label.my.documents", "Eigene Dokumente");
	    lang.put("leftpanel.label.user.search", "User news");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Ordner anlegen");
	    lang.put("tree.menu.directory.remove", "Löschen");
	    lang.put("tree.menu.directory.rename", "Umbenennen");
	    lang.put("tree.menu.directory.refresh", "Auffrischen");
	    lang.put("tree.menu.directory.move", "Verschieben");
	    lang.put("tree.menu.directory.copy", "Kopieren");
	    lang.put("tree.menu.directory.add.document", "Dokument hinzufügen");
	    lang.put("tree.menu.add.bookmark", "Lesezeichen hinzufügen");
	    lang.put("tree.menu.set.home", "Startseite einstellen");
	    lang.put("tree.menu.export", "In Datei exportieren");
	    lang.put("tree.status.refresh.folder", "Aktualisiere Ordnerbaum");
	    lang.put("tree.status.refresh.create", "Lege Ordner an");
	    lang.put("tree.status.refresh.delete", "Lösche Ordner");
	    lang.put("tree.status.refresh.rename", "Benenne Ordner um");
	    lang.put("tree.status.refresh.restore", "Stelle Ordner wieder her");
	    lang.put("tree.status.refresh.purge", "Säubere Ordner");
	    lang.put("tree.status.refresh.get", "Aktualisiere Ordner");
	    lang.put("tree.folder.new", "Neuer Ordner");
	    lang.put("tree.status.refresh.add.subscription", "Füge Abonnement hinzu");
	    lang.put("tree.status.refresh.remove.subscription", "Lösche Abonnement");
	    lang.put("tree.status.refresh.get.root", "Frische Wurzelordner auf");
	    lang.put("tree.status.refresh.get.keywords", "Refreshing keywords");
	    lang.put("tree.status.refresh.get.user.home", "Hole Benutzer Home");
	    lang.put("tree.status.refresh.purge.trash", "Leere Papierkorb");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Wiederherstellen");
	    lang.put("trash.menu.directory.purge", "Säubern");
	    lang.put("trash.menu.directory.purge.trash", "Papierkorb leeren");
	    lang.put("trash.directory.select.label", "Zielordner auswählen");
	    
	    // General menu
	    lang.put("general.menu.file", "Datei");
	    	lang.put("general.menu.file.exit", "Beenden");
	    	lang.put("general.menu.file.purge.trash", "Papierkorb leeren");
	    lang.put("general.menu.edit", "Bearbeiten");
			lang.put("general.menu.file.create.directory", "Ordner anlegen");
			lang.put("general.menu.file.download.document", "Download document");
			lang.put("general.menu.file.download.document.pdf", "Download document as PDF");
			lang.put("general.menu.file.send.link", "Send document link");
			lang.put("general.menu.file.lock", "Sperren");
			lang.put("general.menu.file.unlock", "Sperre aufheben");
			lang.put("general.menu.file.add.document", "Dokument hinzufügen");
			lang.put("general.menu.file.checkout", "Auschecken");
			lang.put("general.menu.file.checkin", "Einchecken");
			lang.put("general.menu.file.cancel.checkout", "Auschecken abbrechen");
			lang.put("general.menu.file.delete", "Löschen");
			lang.put("general.menu.file.refresh", "Auffrischen");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "File uploader");
	    lang.put("general.menu.tools", "Werkzeuge");
	    	lang.put("general.menu.tools.languages", "Sprache");
	    	lang.put("general.menu.tools.skin", "Aussehen");
    			lang.put("general.menu.tools.skin.default", "Standard");
    			lang.put("general.menu.tools.skin.default2", "Standard 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Medium font");
    			lang.put("general.menu.tools.skin.bigfont", "Große Schrift");
    		lang.put("general.menu.debug.console", "Debug console");
    		lang.put("general.menu.administration", "Show administration");
    		lang.put("general.menu.tools.preferences", "Preferences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Lesezeichen");
	    	lang.put("general.menu.bookmark.home", "Startseite");
	    	lang.put("general.menu.bookmark.default.home", "Startseite einstellen");
	    	lang.put("general.menu.bookmark.edit", "Lesezeichen bearbeiten");
	    lang.put("general.menu.help", "Hilfe");
		    lang.put("general.menu.bug.report", "Fehler Bericht");
		    lang.put("general.menu.support.request", "Unterstützung anfordern");
		    lang.put("general.menu.public.forum", "Öffentliches Forum");
		    lang.put("general.menu.project.web", "Projekt Webseite");
		    lang.put("general.menu.version.changes", "Versionsinformationen");
		    lang.put("general.menu.documentation", "Dokumentation");
		    lang.put("general.menu.about", "Über OpenKM");
	    lang.put("general.connected", "Angemeldet als");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Name");
	    lang.put("filebrowser.date.update", "Aktualisierungsdatum");
	    lang.put("filebrowser.size", "Größe");
	    lang.put("filebrowser.path", "Pfad");
	    lang.put("filebrowser.author", "Autor");
	    lang.put("filebrowser.version", "Version");
	    lang.put("filebrowser.menu.checkout", "Auschecken");
	    lang.put("filebrowser.menu.checkin", "Einchecken");
	    lang.put("filebrowser.menu.delete", "Löschen");
	    lang.put("filebrowser.menu.rename", "Umbenennen");
	    lang.put("filebrowser.menu.checkout.cancel", "Auschecken abbrechen");
	    lang.put("filebrowser.menu.lock", "Sperren");
	    lang.put("filebrowser.menu.unlock", "Sperre aufheben");
	    lang.put("filebrowser.menu.download", "Download");
	    lang.put("filebrowser.menu.move", "Verschieben");
	    lang.put("filebrowser.menu.copy", "Kopieren");
	    lang.put("filebrowser.menu.create.from.template", "Von Vorlage erzeugen");
	    lang.put("filebrowser.menu.add.property.group", "Eigenschaftengruppe hinzufügen");
	    lang.put("filebrowser.menu.remove.property.group", "Eigenschaftengruppe löschen");
	    lang.put("filebrowser.menu.start.workflow", "Start workflow");
	    lang.put("filebrowser.menu.add.subscription", "Abonnement hinzufügen");
	    lang.put("filebrowser.menu.remove.subscription", "Abonnement löschen");
	    lang.put("filebrowser.menu.add.bookmark", "Lesezeichen hinzufügen");
	    lang.put("filebrowser.menu.set.home", "Startseite einstellen");
	    lang.put("filebrowser.menu.refresh", "Auffrischen");
	    lang.put("filebrowser.menu.export", "In ZIP exportieren");
	    lang.put("filebrowser.menu.play", "Play");
	    lang.put("filebrowser.menu.image.viewer", "Image viewer");
	    lang.put("filebrowser.status.refresh.folder", "Frische Ordnerliste auf");
	    lang.put("filebrowser.status.refresh.document", "Frische Dokumentliste auf");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Lösche Ordner");
	    lang.put("filebrowser.status.refresh.delete.document", "Lösche Dokument");
	    lang.put("filebrowser.status.refresh.checkout", "Checke Dokument aus");
	    lang.put("filebrowser.status.refresh.lock", "Sperre Dokument");
	    lang.put("filebrowser.status.refresh.unlock", "Entsperre Dokument");
	    lang.put("filebrowser.status.refresh.document.rename", "Benenne Dokument um");
	    lang.put("filebrowser.status.refresh.folder.rename", "Benenne Ordner um");
	    lang.put("filebrowser.status.refresh.document.purge", "Lösche Dokument");
	    lang.put("filebrowser.status.refresh.folder.purge", "Lösche Ordner");
	    lang.put("filebrowser.status.refresh.folder.get", "Aktualisiere Ordner");
	    lang.put("filebrowser.status.refresh.document.get", "Aktualisiere Dokument");
	    lang.put("filebrowser.status.refresh.add.subscription", "Füge Abonnement hinzu");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Lösche Abonnement");
	    lang.put("filebrowser.status.refresh.get.user.home", "Hole Benutzerstartseite");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Hochladen");
	    lang.put("fileupload.status.sending", "Lade Datei hoch...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "Hochladen der Datei war erfolgreich");
	    lang.put("fileupload.upload.status", "Hochladen Status");
	    lang.put("fileupload.upload.uploaded", "Hochgeladen");
	    lang.put("fileupload.upload.completed", "Hochladen beendet");
	    lang.put("fileupload.upload.runtime", "Laufzeit");
	    lang.put("fileupload.upload.remaining", "Verbleiben");
	    lang.put("fileupload.button.close", "Schließen");
	    lang.put("fileupload.button.add.other.file", "Weitere Dateien hochladen");
	    lang.put("fileupload.status.move.file", "Verschiebe Datei...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Kopiere Datei...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "Stelle Datei wieder her...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Verschiebe Ordner...");
	    lang.put("fileupload.status.copy.folder", "Kopiere Ordner...");
	    lang.put("fileupload.status.restore.folder", "Stelle Ordner wieder her...");
	    lang.put("fileupload.status.create.from.template", "erzeuge Datei von einer Vorlage...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Neue Dokumente Hinzufügen");
	    lang.put("fileupload.label.update", "Dokumente auffrischen");
	    lang.put("fileupload.label.users.notify", "Benutzer informieren");
	    lang.put("fileupload.label.comment", "Kommentar");
	    lang.put("fileupload.label.users.to.notify",  "Zu Informierende Benutzer");
	    lang.put("fileupload.label.users",  "Benutzer");
	    lang.put("fileupload.label.must.select.users",  "Sie Müssen einen Benutzer auswählen");
	    lang.put("fileupload.label.importZip", "Import Documents from ZIP");
	    lang.put("fileupload.label.notify.comment", "Notification message");
	    lang.put("fileupload.label.error.importing.zip", "Fehler beim Dateiimport");
	    lang.put("fileupload.label.error.move.file", "Fehler beim Verscheiben der Datei");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Fehler beim Kopieren der Datei");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "Fehler beim Wiederherstellen der Datei");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Fehler beim Verschieben des Ordners");
	    lang.put("fileupload.label.error.copy.folder", "Fehler beim Kopieren des Ordners");
	    lang.put("fileupload.label.error.restore.folder", "Fehler beim Wiederherstellen des Ordners");
	    lang.put("fileupload.label.error.create.from.template", "Fehler beim Erzeugen einer Datei von einer Vorlage");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Quell- oder Unterordner verscheiben nicht erlaubt");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Quellordner verscheiben nicht erlaubt");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Erzeugen einer Datei im Quellordner nicht erlaubt");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Eigenschaften");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "Historie");
	    lang.put("tab.document.status.history", "Aktualisiere Historie");
	    lang.put("tab.status.security.role", "Aktualisiere Sicherheit Rollen");
	    lang.put("tab.status.security.user", "Aktualisiere Sicherheit Benutzer");
	    lang.put("tab.document.status.group.properties", "Aktualisiere Eigenschaften Gruppe");
	    lang.put("tab.document.status.set.keywords", "Setze Schlagwörter");
	    lang.put("tab.document.status.set.categories", "Updating categories");
	    lang.put("tab.document.status.get.version.history.size", "Frische Größe der Dokumenthistorie auf");
	    lang.put("tab.document.status.purge.version.history", "Bereinige Dokumenthistorie");
	    lang.put("tab.document.status.restore.version", "Stelle Dokument Version wieder her");
	    lang.put("tab.document.security", "Sicherheit");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Eigenschaften");
	    lang.put("tab.folder.security", "Sicherheit");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Arbeitsplatz");
	    lang.put("tab.workspace.search", "Suche");
	    lang.put("tab.workspace.dashboard", "DashBoard");
	    lang.put("tab.workspace.administration", "Administration");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Name");
	    lang.put("document.folder", "Ordner");
	    lang.put("document.size", "Größe");
	    lang.put("document.created", "Erzeugt");
	    lang.put("document.lastmodified", "Geändert");
	    lang.put("document.mimetype", "MIME Type");
	    lang.put("document.keywords", "Schlagwörter");
	    lang.put("document.by", "von");
	    lang.put("document.status", "Status");
	    lang.put("document.status.checkout", "Ausgecheckt von");
	    lang.put("document.status.locked", "Gesperrt durch");
	    lang.put("document.status.normal", "Verfügbar");
	    lang.put("document.subscribed", "Abonniert");
	    lang.put("document.subscribed.yes", "Ja");
	    lang.put("document.subscribed.no", "Nein");
	    lang.put("document.history.size", "Größe Historie");
	    lang.put("document.subscribed.users", "Subscribed users");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Add note");
	    lang.put("document.keywords.cloud", "Keywords cloud");
	    lang.put("document.categories", "Categories");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Name");
	    lang.put("folder.parent", "Eltern");
	    lang.put("folder.created", "Erzeugt");
	    lang.put("folder.by", "von");
	    lang.put("folder.subscribed", "Abonniert");
	    lang.put("folder.subscribed.yes", "Ja");
	    lang.put("folder.subscribed.no", "Nein");
	    lang.put("folder.subscribed.users", "Subscribed users");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Folders");
	    lang.put("folder.number.documents","Documents");
	    lang.put("folder.number.mails", "Mails");
	    
	    // Version
	    lang.put("version.name", "Version");
	    lang.put("version.created", "Datum");
	    lang.put("version.author", "Autor");
	    lang.put("version.size", "Größe");
	    lang.put("version.purge.document", "Bereinige Historie");
	    lang.put("version.comment", "Comment");
	    
	    // Security
	    lang.put("security.label", "Sicherheit");
	    lang.put("security.group.name", "Gruppe");
	    lang.put("security.group.permission.read", "Lesen");
	    lang.put("security.group.permission.write", "Schreiben");
	    lang.put("security.group.permission.delete", "Delete");
	    lang.put("security.group.permission.security", "Security");
	    lang.put("security.user.name", "Benutzer");
	    lang.put("security.user.permission.read", "Lesen");
	    lang.put("security.user.permission.write", "Schreiben");
	    lang.put("security.user.permission.delete", "Delete");
	    lang.put("security.user.permission.security", "Security");
	    lang.put("security.users", "Benutzer");
	    lang.put("security.groups", "Gruppen");
	    lang.put("security.recursive", "Rekursive Änderung der Berechtigungen");
	    lang.put("secutiry.filter.by.users","Users filter");
	    lang.put("secutiry.filter.by.groups","Groups filter");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");
	    
	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "Es ist ein Fehler aufgetreten :-(");
	    lang.put("error.invocation", "Fehler beim Kommunizieren mit dem Server");
	    
	    // About
	    lang.put("about.label", "Über OpenKM");
	    lang.put("about.loading", "Lade ...");
	    
	    // Logout
	    lang.put("logout.label", "Beenden");
	    lang.put("logout.closed", "OpenKM wurde erfolgreich beendet");
	    lang.put("logout.logout", "Melde OpenKM ab, bitte warten");
	    
	    // Confirm
	    lang.put("confirm.label", "Bestätigung");
	    lang.put("confirm.delete.folder", "Sind Sie sicher, dass der Ordner gelöscht werden soll?");
	    lang.put("confirm.delete.document", "Sind Sie sicher, dass das Dokument gelöscht werden soll?");
	    lang.put("confirm.delete.trash", "Sind Sie sicher, dass der Papierkob geleert werden soll?");
	    lang.put("confirm.purge.folder", "Sind Sie sicher, dass der Ordner gelöscht werden soll?");
	    lang.put("confirm.purge.document", "Sind Sie sicher, dass das Dokument gelöscht werden soll?");
	    lang.put("confirm.delete.propety.group", "Sind Sie sicher, dass die Eigenschaftengruppe gelöscht werden soll?");
	    lang.put("confirm.purge.version.history.document", "Sind Sie sicher, dass das die Dokument Historie gelöscht werden soll?");
	    lang.put("confirm.purge.restore.document", "Sind Sie sicher, dass diese Dokument Version wiederhergestellt werden soll?");
	    lang.put("confirm.set.default.home", "Sind Sie sicher, dass die Startseite eingestellt werden soll?");
	    lang.put("confirm.delete.saved.search", "¿ Do you really want to delete saved search ?");
	    lang.put("confirm.delete.user.news", "¿ Do you really want to delete user news ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    lang.put("confirm.get.pooled.workflow.task","¿ Do you want to assign this task to you ?");
	    
	    // Search inputs
	    lang.put("search.context", "Kontext");
	    lang.put("search.content", "Inhalt");
	    lang.put("search.name", "Name");
	    lang.put("search.keywords", "Schlüsselwörter");
	    lang.put("search.folder", "Folder");
	    lang.put("search.category", "Category");
	    lang.put("search.results", "Ergebnisse");
	    lang.put("search.to", "bis");
	    lang.put("search.page.results", "Seitenergebnisse");
	    lang.put("search.add.property.group", "Eigenschaftengruppe hinzufügen");
	    lang.put("search.mimetype", "Mime type");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Erweiterte Suche");
	    lang.put("search.user", "Benutzer");
	    lang.put("search.date.and", "and");
	    lang.put("search.date.range", "Date range between");
	    lang.put("search.save.as.news", "Save as user news");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    lang.put("search.category.filter", "Filter by category");
	    
	    // Search results
	    lang.put("search.result.name", "Name");
	    lang.put("search.result.score", "Wertung");
	    lang.put("search.result.size", "Größe");
	    lang.put("search.result.date.update", "Änderungsdatum");
	    lang.put("search.result.author", "Autor");
	    lang.put("search.result.version", "Version");
	    lang.put("search.result.path", "Pfad");
	    lang.put("search.result.menu.download", "Download");
	    lang.put("search.result.menu.go.folder", "Zum Ordner springen");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Aktualisiere Suche");
	    lang.put("search.result.status.runsearch", "Aktualisiere gespeicherte Suche");
	    
	    // Search saved
	    lang.put("search.saved.run", "Start");
	    lang.put("search.saved.delete", "Löschen");
	    lang.put("search.saved.status.getsearchs", "Frische gespeicherte Suchen auf");
	    lang.put("search.saved.status.savesearch", "Aktualisiere gespeicherte Suchen");
	    lang.put("search.saved.status.deletesearch", "Lösche gespeicherte Suchen");
	    lang.put("search.saved.status.getusernews", "Refreshing user news");
	    
	    // Button
	    lang.put("button.close", "Schließen");
	    lang.put("button.logout", "Abmelden");
	    lang.put("button.update", "Bearbeiten");
	    lang.put("button.cancel", "Abbruch");
	    lang.put("button.accept", "O.K.");
	    lang.put("button.restore", "Wiederherstellen");
	    lang.put("button.move", "Verschieben");
	    lang.put("button.change", "Bearbeiten");
	    lang.put("button.search", "Suchen");
	    lang.put("button.save.search", "Suche speichern");
	    lang.put("button.view", "Ansicht");
	    lang.put("button.clean", "Zurücksetzen");
	    lang.put("button.add", "Hinzufügen");
	    lang.put("button.delete", "Löschen");
	    lang.put("button.copy", "Kopieren");
	    lang.put("button.create", "Erzeugen");
	    lang.put("button.show", "Anzeigen");
	    lang.put("button.memory", "Speichern");
	    lang.put("button.copy.clipboard", "Copy to clipboard");	 
	    lang.put("button.start", "Start");
	    lang.put("button.select", "Select");
	    lang.put("button.test", "Test");
	    lang.put("button.next", "Next");
	    
	    // Group
	    lang.put("group.label", "Gruppeneigenschaft hinzufügen");
	    lang.put("group.group", "Gruppe");
	    lang.put("group.property.group", "Eigenschaft");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Lesezeichen hinzufügen");
	    lang.put("bookmark.name", "Name");
	    lang.put("bookmark.edit.label", "Lesezeichen bearbeiten");
	    lang.put("bookmark.path", "Pfad");
	    lang.put("bookmark.type", "Typ");
	    lang.put("bookmark.type.document", "Dokument");
	    lang.put("bookmark.type.folder", "Ordner");
	    
	    // Notify
	    lang.put("notify.label", "Sending document link");
	    
	    // Status
	    lang.put("status.document.copied", "Dokument zum Kopieren markiert");
	    lang.put("status.document.cut", "Dokument zum Ausschneiden markiert");
	    lang.put("status.folder.copied", "Ordner zum Kopieren markiert");
	    lang.put("status.folder.cut", "Ordner zum Ausschneiden markiert");
	    lang.put("status.keep.alive.error", "Verlust der Verbindung zum Server(Lebenszeichen)");
	    lang.put("status.debug.enabled", "Debug enabled");
	    lang.put("status.debug.disabled", "Debug disabled");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Sunday");
	    lang.put("calendar.day.monday", "Monday");
	    lang.put("calendar.day.tuesday", "Tuesday");
	    lang.put("calendar.day.wednesday", "Wednesday");
	    lang.put("calendar.day.thursday", "Thursday");
	    lang.put("calendar.day.friday", "Friday");
	    lang.put("calendar.day.saturday", "Saturday");
	    lang.put("calendar.month.january", "January");
	    lang.put("calendar.month.february", "February");
	    lang.put("calendar.month.march", "March");
	    lang.put("calendar.month.april", "April");
	    lang.put("calendar.month.may", "May");
	    lang.put("calendar.month.june", "June");
	    lang.put("calendar.month.july", "July");
	    lang.put("calendar.month.august", "August");
	    lang.put("calendar.month.september", "September");
	    lang.put("calendar.month.october", "October");
	    lang.put("calendar.month.november", "November");
	    lang.put("calendar.month.december", "December");
	    
	    // Media player
	    lang.put("media.player.label", "Media player");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Image viewer");
	    lang.put("image.viewer.zoom.in", "Zoom in");
	    lang.put("image.viewer.zoom.out", "Zoom out");
	    
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
	    lang.put("dashboard.keyword.goto.document", "Go to document");
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
	    lang.put("wizard.document.uploading","Document wizard");
	    
	    // User info
	    lang.put("user.info.chat.online","Online");
	    lang.put("user.info.chat.offline","Offline");
	    lang.put("user.info.chat.connect","Connect to chat");
	    lang.put("user.info.chat.disconnect","Disconnet chat");
	    lang.put("user.info.chat.new.room","Net chat room");
	    lang.put("user.info.locked.actual","Locked documents");
	    lang.put("user.info.checkout.actual","Checkout documents");
	    lang.put("user.info.subscription.actual","Actual subscriptions");
	    lang.put("user.info.news.new","News");
	    lang.put("user.info.workflow.pending","Pending workflows");
	    
	    // Users online popup
	    lang.put("user.online","Users online");
	    
	    // Chat room
	    lang.put("chat.room","Chat");
	    lang.put("chat.users.in.room","Users");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Zugriff auf Dokument verweigert");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Dokument sperren verweigert");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Interner Repositoriefehler");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Dokumentpfad nicht gefunden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Version error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Zugriff auf Dokument verweigert");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Dokument nicht gefunden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Dokument existiert bereits");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Dokument sperren verweigert");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Dokument Entsperen gewünscht");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Interner Repositoriefehler");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Interner Anwendungsfehler");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Dokumentpfad nicht gefunden");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Zugriff auf Ordner verweigert");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Ordner nicht gefunden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Ordner existiert bereits");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Interner Repositoriefehler");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Interner Anwendungsfehler");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Ordnerpfad nicht gefunden");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Zugriff auf Element verweigert");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Element nicht gefunden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Interner Repositoriefehler");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Interner Anwendungsfehler ");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Dokument nicht gefunden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Interner Anwendungsfehler");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Nicht unterstütztes Dateiformat");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Dokument existiert bereits");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "zulässige maximale Dokumentgröße überschritten");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Sitzung geschlossen");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Generic error executing query");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Der Name der gespeicherten Suche muß eindeutig sein.");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Der Name des Lesezeichens muß eindeutig sein.");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Session lost");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Interner Repositoriefehler");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Interner Repositoriefehler");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Interner Repositoriefehler");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error in browser configuration");
	  }
}