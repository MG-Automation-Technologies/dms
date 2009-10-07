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

package es.git.openkm.backend.client.lang;

import java.util.HashMap;

import es.git.openkm.backend.client.config.ErrorCode;

/**
 * German (Germany)
 * 
 * @author Matthias Scholz
 */
public class Lang_de_DE {
	
	public final static HashMap<String,String> lang;
	  static {
	    lang = new HashMap<String,String>();
	    
        // Button
		lang.put("button.close", "Schließen");
		lang.put("button.logout", "Abmelden");
		lang.put("button.search", "Suchen");
		lang.put("button.clean", "Zurücksetzen");
		lang.put("button.cancel", "Abbrechen");
		lang.put("button.select", "Auswahl");
		lang.put("button.edit", "Bearbeiten");
		lang.put("button.view", "Ansicht");
		lang.put("button.add", "Hinzufügen");
		lang.put("button.create.user", "Benutzer anlegen");
		lang.put("button.update.user", "Änderungen speichern");
		lang.put("button.delete", "Löschen");
		lang.put("button.add.new.role", "Neue Rolle hinzufügen");
		lang.put("button.filter", "Filter");
		lang.put("button.refresh", "Aktualisieren");
		lang.put("button.accept", "O.K.");
		lang.put("button.execute", "Ausführen");
		
		// Toolbar
		lang.put("toolbar.general", "Allgemein");
		lang.put("toolbar.search", "Suche");
		lang.put("toolbar.users", "Benutzer");
		lang.put("toolbar.utilities", "Werkzeuge");
		lang.put("toolbar.properties", "Eigenschaften");
		lang.put("toolbar.workflow", "Workflow");
		
		// Advanced Search
		lang.put("advanced.search.type", "Typ");
		lang.put("advanced.search.query", "Abfrage");
		lang.put("advanced.search.predefined", "Vordefinierte Abfrage");
		lang.put("advanced.search.predefined.locked.documents", "Gesperrte Dokumente");
		lang.put("advanced.search.predefined.documents.created.by.admin", "Dokumente welche durch den Benutzer 'okmAdmin' angelegt wurden");
		lang.put("advanced.search.predefined.documents.with.property.group.test", "Dokumente mit der Gruppeneigenschaft 'okg:test'");
		lang.put("advanced.search.predefined.documents.observed", "Beobachtete Dokumente");
		lang.put("advanced.search.status.search", "Suche");
		
		// Users
		lang.put("users.refreshing.every", "Aktualisierungsintervall");
		lang.put("users.refreshing.every.5.seconds", "5 Sekunden");
		lang.put("users.refreshing.every.15.seconds", "15 Sekunden");
		lang.put("users.refreshing.every.30.seconds", "30 Sekunden");
		lang.put("users.refreshing.every.1.minute", "1 Minuten");
		lang.put("users.refreshing.every.5.minutes", "5 Minuten");
		lang.put("users.refreshing.every.15.minutes", "15 Minuten");
		lang.put("users.uid", "UID");
		lang.put("users.active", "Aktiv");
		lang.put("users.token", "Token");
		lang.put("users.creation", "Erzeugt");
		lang.put("users.last.access", "Letzter Zugriff");
		lang.put("users.refreshing", "Aktualisiere ...");
		lang.put("users.mail", "Mail");
		lang.put("users.password", "Passwort");
		lang.put("users.active", "Aktiv");
		lang.put("users.active.yes", "Ja");
		lang.put("users.active.no", "Nein");
		lang.put("users.roles", "Rollen");
		lang.put("users.activity.log.uid", "UID");
		lang.put("users.activity.log.date.from", "Von Datum");
		lang.put("users.activity.log.date.to", "Bis Datum");
		lang.put("users.activity.log.action", "Aktion");
		lang.put("users.activity.date", "Datum");
		lang.put("users.activity.user", "Benutzer");
		lang.put("users.activity.date", "Datum");
		lang.put("users.activity.token", "Token");
		lang.put("users.activity.action", "Aktion");
		lang.put("users.activity.params", "Parameter");
		lang.put("users.error.password.equals", "Die beiden Passwörter müssen gleich sein");
		lang.put("users.error.password.blank", "Das Passwort darf nicht leer sein");
		lang.put("users.error.rol.name.empty", "Der Name der Rolle ist leer");
		lang.put("users.error.rol.exist", "Die Rolle existiert bereits");
		lang.put("users.menu.delete", "Löschen");
		lang.put("users.menu.edit", "Bearbeiten");
		lang.put("users.menu.add", "Benutzer hinzufügen");
		lang.put("users.status.search", "Suchen");
		
		// General utils
		lang.put("general.util.import", "Import");
		lang.put("general.util.export", "Export");
		lang.put("general.util.register", "Registrieren");
		lang.put("general.util.filesystem.path", "Pfad im Dateisystem");
		lang.put("general.util.repository.path", "Pfad im Repositorie");
		lang.put("general.util.register.property.groups", "Gruppeneigenschaften registrieren");
		lang.put("general.util.property.group.definition.path", "Pfad der Definition für die Gruppeneigenschaften");
		lang.put("general.util.folder.destination", "Ziel");
		lang.put("general.util.folder.destination.taxonomy", "Taxonomie");
		lang.put("general.util.folder.destination.templates", "Vorlagen");
		lang.put("general.util.select.folder.destination", "Bitte das Zielverzeichnis auswählen");
		lang.put("general.util.register.workflow", "Workflow registrieren");
		lang.put("general.util.workflow.file", "Workflow Datei");
		lang.put("general.util.report", "Berichte");
		lang.put("general.util.report.type", "Berichttyp");
		lang.put("general.util.report.type.users", "Benutzer");
		lang.put("general.util.report.type.locked.documents", "Gesperrte Dokumente");
		lang.put("general.util.report.type.subscribed.documents", "Abonierte Dokumente");
		lang.put("general.util.refreshing", "Aktualisierung");
		lang.put("general.util.registering.properties", "Registriere Eigenschaften");
		lang.put("general.util.registering.workflow", "Registriere Workflow");
		lang.put("general.util.exporting", "Exportiere");
		lang.put("general.util.importing", "Importiere");
		
		// Property groups
		lang.put("group.property.group", "Gruppeneigenschaft");
		lang.put("group.property.group.type.textarea", "Textfeld");
		lang.put("group.property.group.type.input", "Eingabe");
		lang.put("group.property.group.type.select", "Asuwahl");
		lang.put("group.property.group.type.select.multiple", "Mehrfachauswahl");
		lang.put("group.property.unique.id", "Eindeutige ID");
		lang.put("group.property.type", "Typ");
		lang.put("group.property.values", "Werte");
		lang.put("group.property.status.refresh", "Aktualisiere Eigenschaften");
		
		// Stats
		lang.put("stats.context.size", "Repositorie Größe");
		lang.put("stats.context.taxonomy", "Taxonomie");
		lang.put("stats.context.personal", "Meine Dokumente");
		lang.put("stats.context.templates", "Vorlagen");
		lang.put("stats.context.trash", "Papierkorb");
		lang.put("stats.context.folder.number", "Anzahl der Ordner");
		lang.put("stats.context.document.number", "Anzahl der Dokumente");
		lang.put("stats.context.documents", "Dokumente");
		lang.put("stats.context.total", "Insgesamt");
		lang.put("stats.context.other", "Andere");
		lang.put("stats.context.document.subscribed", "Abonierte Dokumente");
		lang.put("stats.context.folder.subscribed", "Abonierte Ordner");
		lang.put("stats.context.document.with.properties", "Dokumente mit Eigenschaften");
		lang.put("stats.context.folders", "Ordner");
		lang.put("stats.support", "Unterstützung");
		lang.put("stats.installation.id", "Installation ID");
		lang.put("stats.information", "Information");
		lang.put("general.status.graph", "Aktualisiere Grafiken");
		
		// Workflow
		lang.put("workflow.id", "ID");
		lang.put("workflow.name", "Name");
		lang.put("workflow.last.version", "Letzte Version");
		lang.put("workflow.version", "Version");
		lang.put("workflow.ended", "Beendet");
		lang.put("workflow.current.nodes", "Aktuelle Knoten");
		lang.put("workflow.variables", "Variablen");
		lang.put("workflow.suspended", "Aufgeschoben");
		lang.put("workflow.menu.instances", "Instanzen");
		lang.put("workflow.menu.delete", "Löschen");
		lang.put("workflow.status.delete", "Workflow Löschen");
		lang.put("workflow.status.version", "Aktualisiere Workflow Versionen");
		lang.put("workflow.status.instances", "Aktualisiere Workflow Instanzen");
		
		// Calendar
	    lang.put("calendar.day.sunday", "Sonntag");
	    lang.put("calendar.day.monday", "Montag");
	    lang.put("calendar.day.tuesday", "Dienstag");
	    lang.put("calendar.day.wednesday", "Mittwoch");
	    lang.put("calendar.day.thursday", "Donnerstag");
	    lang.put("calendar.day.friday", "Freitag");
	    lang.put("calendar.day.saturday", "Samstag");
	    lang.put("calendar.month.january", "Januar");
	    lang.put("calendar.month.february", "Fabruar");
	    lang.put("calendar.month.march", "März");
	    lang.put("calendar.month.april", "April");
	    lang.put("calendar.month.may", "Mai");
	    lang.put("calendar.month.june", "Juni");
	    lang.put("calendar.month.july", "Juli");
	    lang.put("calendar.month.august", "August");
	    lang.put("calendar.month.september", "September");
	    lang.put("calendar.month.october", "Oktober");
	    lang.put("calendar.month.november", "November");
	    lang.put("calendar.month.december", "Dezember");
	    
	    // Confirm
	    lang.put("confirm.label", "Bestätigung");
	    lang.put("confirm.delete.user", "Wollen Sie den Benutzer wirklich löschen?");
	    lang.put("confirm.delete.workflow", "Wollen Sie den Workflow wirklich löschen?");
	    lang.put("confirm.delete.workflow.version", "Wollen Sie die Workflow Version wirklich löschen?");
		
		// Error
	    lang.put("error.label", "Das System hat einen Fehler erzeugt");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Zugriff zum Dokument wurde verweigert");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Dokument wurde nicht gefunden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Das Dokument existiert bereits");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Dokumentensperre verweigert");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Dokumentensperre erwünscht");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Interner Repositoriefehler");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Interner Anwendungsfehler");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Dokument Pfad wurde nicht gefunden");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Zugriff zum Ordner wurde verweigert");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Der Ordner wurde nicht gefunden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Der Ordner existiert bereits");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Interner Repositoriefehler");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Allgemeiner Repositoriefehler");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Ordner Pfad wurde nicht gefunden");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Zugriff zum Eintrag wurde verweigert");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Eintrag nicht gefunden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Interner Repositoriefehler");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Allgemeiner Repositoriefehler");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Dokument nicht gefunden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Allgemeiner Repositoriefehler");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Nicht unterstütztes Dateiformat");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Das Dokument existiert bereits");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Maximale Dokumentgröße wurde überschritten");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Die Sitzung wurde geschlossen");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Der Name der gespeicherten Suche muß eindeutig sein");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Der Name des Lesezeichens muß eindeutig sein");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Die Sitzung wurde geschlossen");
	  
	  }
}
