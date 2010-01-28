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
 * Dutch (Nederlands/Belgium)
 *
 * @author Ph Vervoort
 * v4.0 M. van den Berg
 */
public class Lang_nl_NL {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd/MM/yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd/MM/yyyy");
	    lang.put("general.hour.pattern", "hh:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "OpenKM laden");
	    lang.put("startup.starting.loading", "Starten met laden van OpenKM");
	    lang.put("startup.taxonomy", "Taxonomie root node ophalen");
	    lang.put("startup.categories", "Getting categories root node");
	    lang.put("startup.thesaurus", "Getting thesaurus root node");
	    lang.put("startup.template", "Template root node ophalen");
	    lang.put("startup.personal", "Persoonlijke root node ophalen");
	    lang.put("startup.mail", "e-mail root node ophalen");
	    lang.put("startup.trash", "Prullenbak root node ophalen");
	    lang.put("startup.user.home", "Gebruiker thuis node ophalen");
	    lang.put("startup.bookmarks", "Favorieten ophalen");
	    lang.put("startup.loading.taxonomy", "Taxonomie laden");
	    lang.put("startup.loading.taxonomy.getting.folders", "Taxonomie - Mappen ophalen");
	    lang.put("startup.loading.taxonomy.eval.params", "Taxonomie - Browser params evalueren");
	    lang.put("startup.loading.taxonomy.open.path", "Taxonomie - Paden openen");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Taxonomie - Bestandsbrowser mappen ophalen");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Taxonomie - Bestandsbrowser documenten ophalen");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Taxonomie - e-mails ophalen");
	    lang.put("startup.loading.personal", "Persoonlijk laden");
	    lang.put("startup.loading.mail", "e-mails laden");
	    lang.put("startup.loading.categories", "Loading categories");
	    lang.put("startup.loading.thesaurus", "Loading thesaurus");
	    lang.put("startup.loading.templates", "Templates laden");
	    lang.put("startup.loading.trash", "Prullenbak laden");
	    lang.put("startup.loading.history.search", "Historische zoekopdrachten laden");
	    lang.put("startup.loading.user.values", "Gebruikers waarden laden");
	    lang.put("startup.loading.property.group.translations", "Eigenschap groep vertalingen laden");
	    lang.put("startup.keep.alive", "keep alive laden");
	    
	    // Update notification
	    lang.put("openkm.update.available", "OpenKM update beschikbaar");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Taxonomie");
	    lang.put("leftpanel.label.trash", "Prullenbak");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Opgeslagen zoekopdrachten");
	    lang.put("leftpanel.label.categories", "Categories");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Templates");
	    lang.put("leftpanel.label.my.documents", "Mijn documenten");
	    lang.put("leftpanel.label.user.search", "Gebruiker zoeken");
		lang.put("leftpanel.label.all.repository", "Alle repositories");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Maak map");
	    lang.put("tree.menu.directory.remove", "Verwijder");
	    lang.put("tree.menu.directory.rename", "Hernoem");
	    lang.put("tree.menu.directory.refresh", "Ververs");
	    lang.put("tree.menu.directory.move", "Verplaats");
	    lang.put("tree.menu.directory.copy", "Kopier");
	    lang.put("tree.menu.directory.add.document", "Bestand toevoegen");
	    lang.put("tree.menu.add.bookmark", "Add bookmark");
	    lang.put("tree.menu.set.home", "Set default home");
	    lang.put("tree.menu.export", "Export to file");
	    lang.put("tree.status.refresh.folder", "Boomstructuur verversen");
	    lang.put("tree.status.refresh.create", "Map aanmaken");
	    lang.put("tree.status.refresh.delete", "Map wissen");
	    lang.put("tree.status.refresh.rename", "Map hernoemen");
	    lang.put("tree.status.refresh.restore", "Map terugplaatsen");
	    lang.put("tree.status.refresh.purge", "Map verwijderen");
	    lang.put("tree.status.refresh.get", "Map verversen");
	    lang.put("tree.folder.new", "Nieuwe map");
	    lang.put("tree.status.refresh.add.subscription", "Adding subscription");
	    lang.put("tree.status.refresh.remove.subscription", "Deleting subscription");
	    lang.put("tree.status.refresh.get.root", "Refresing root folder");
	    lang.put("tree.status.refresh.get.keywords", "Refreshing keywords");
	    lang.put("tree.status.refresh.get.user.home", "Getting user home");
	    lang.put("tree.status.refresh.purge.trash", "Cleaning trash");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Terugplaatsen");
	    lang.put("trash.menu.directory.purge", "Verwijderen");
	    lang.put("trash.menu.directory.purge.trash", "Prullenbak leegmaken");
	    lang.put("trash.directory.select.label", "Kies te verplaatsen map");
	    
	    // General menu
	    lang.put("general.menu.file", "Bestand");
	    lang.put("general.menu.file.exit", "Afsluiten");
	    lang.put("general.menu.file.purge.trash", "Prullenbak leegmaken");
	    lang.put("general.menu.edit", "Bewerken");
		lang.put("general.menu.file.create.directory", "Maak map");
		lang.put("general.menu.file.download.document", "Download document");
		lang.put("general.menu.file.download.document.pdf", "Download document als PDF");
		lang.put("general.menu.file.send.link", "Verstuur document link");
		lang.put("general.menu.file.lock", "Bescherm");
		lang.put("general.menu.file.unlock", "Vrijgeven");
		lang.put("general.menu.file.add.document", "Bestand toevoegen");
		lang.put("general.menu.file.checkout", "Uithalen");
		lang.put("general.menu.file.checkin", "Inleveren");
		lang.put("general.menu.file.cancel.checkout", "Uithalen annuleren");
		lang.put("general.menu.file.delete", "Wis");
		lang.put("general.menu.file.refresh", "Ververs");
	    lang.put("general.menu.tools", "Gereedschappen");
	    lang.put("general.menu.tools.languages", "Talen");
	    lang.put("general.menu.tools.skin", "Huid");
    	lang.put("general.menu.tools.skin.default", "Standaard");
    	lang.put("general.menu.tools.skin.default2", "Standaard 2");
    	lang.put("general.menu.tools.skin.mediumfont", "Medium font");
    	lang.put("general.menu.tools.skin.bigfont", "Big font");
    	lang.put("general.menu.debug.console", "Debug console");
    	lang.put("general.menu.administration", "Administratie tonen");
		lang.put("general.menu.tools.preferences", "Voorkeuren");
			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Bookmarks");
	    lang.put("general.menu.bookmark.home", "Home");
	    lang.put("general.menu.bookmark.default.home", "Standaard thuismap zetten");
	    lang.put("general.menu.bookmark.edit", "Bookmarks bewerken");
	    lang.put("general.menu.help", "Help");
		lang.put("general.menu.bug.report", "Fout melden");
		lang.put("general.menu.support.request", "Ondersteuningsaanvraag");
		lang.put("general.menu.public.forum", "Publiek forum");
		lang.put("general.menu.project.web", "Project op het web");
		lang.put("general.menu.version.changes", "Versie notities");
		lang.put("general.menu.documentation", "Documentatie");
		lang.put("general.menu.about", "Over OpenKM");
	    lang.put("general.connected", "Aangemeld als");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Naam");
	    lang.put("filebrowser.date.update", "Laatst bijgewerkt");
	    lang.put("filebrowser.size", "Grootte");
	    lang.put("filebrowser.path", "Pad");
	    lang.put("filebrowser.author", "Auteur");
	    lang.put("filebrowser.version", "Versie");
	    lang.put("filebrowser.menu.checkout", "Uithalen");
	    lang.put("filebrowser.menu.checkin", "Inleveren");
	    lang.put("filebrowser.menu.delete", "Wis");
	    lang.put("filebrowser.menu.rename", "Hernoem");
	    lang.put("filebrowser.menu.checkout.cancel", "Uithalen annuleren");
	    lang.put("filebrowser.menu.lock", "Bescherm");
	    lang.put("filebrowser.menu.unlock", "Vrijgeven");
	    lang.put("filebrowser.menu.download", "Download");
	    lang.put("filebrowser.menu.move", "Verplaats");
	    lang.put("filebrowser.menu.copy", "Kopie");
	    lang.put("filebrowser.menu.create.from.template", "Aanmaken vanuit template");
	    lang.put("filebrowser.menu.add.property.group", "Property group toevoegen");
	    lang.put("filebrowser.menu.remove.property.group", "Property group verwijderen");
	    lang.put("filebrowser.menu.start.workflow", "Start workflow");
	    lang.put("filebrowser.menu.add.subscription", "Inschrijving toevoegen");
	    lang.put("filebrowser.menu.remove.subscription", "Inschrijving verwijderen");
	    lang.put("filebrowser.menu.add.bookmark", "Bookmark toevoegen");
	    lang.put("filebrowser.menu.set.home", "Thuismap zetten");
	    lang.put("filebrowser.menu.refresh", "Ververs");
	    lang.put("filebrowser.menu.export", "Export naar ZIP");
	    lang.put("filebrowser.menu.play", "Afspelen");
	    lang.put("filebrowser.menu.image.viewer", "Afbeelding viewer");
	    lang.put("filebrowser.status.refresh.folder", "Maplijst verversen");
	    lang.put("filebrowser.status.refresh.document", "Bestandslijst verversen");
	    lang.put("filebrowser.status.refresh.mail", "Mail lijst updaten");
	    lang.put("filebrowser.status.refresh.delete.folder", "Map wissen");
	    lang.put("filebrowser.status.refresh.delete.document", "Bestand wissen");
	    lang.put("filebrowser.status.refresh.checkout", "Bestand uithalen");
	    lang.put("filebrowser.status.refresh.lock", "Bestand beschermen");
	    lang.put("filebrowser.status.refresh.unlock", "Bestand vrijgeven");
	    lang.put("filebrowser.status.refresh.document.rename", "Bestand hernoemen");
	    lang.put("filebrowser.status.refresh.folder.rename", "Map hernoemen");
	    lang.put("filebrowser.status.refresh.document.purge", "Bestand verwijderen");
	    lang.put("filebrowser.status.refresh.folder.purge", "Map verwijderen");
	    lang.put("filebrowser.status.refresh.folder.get", "Map verversen");
	    lang.put("filebrowser.status.refresh.document.get", "Bestand verversen");
	    lang.put("filebrowser.status.refresh.add.subscription", "Inschrijving toevoegen");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Inschrijving verwijderen");
	    lang.put("filebrowser.status.refresh.get.user.home", "Gebruiker thuismap ophalen");
	    lang.put("filebrowser.status.refresh.delete.mail", "Mail verwijderen");
	    lang.put("filebrowser.status.refresh.mail.purge", "Mail verwijderen");
	    
	    // File Upload
	    lang.put("fileupload.send", "Verzend");
	    lang.put("fileupload.status.sending", "Bestand laden...");
	    lang.put("fileupload.status.indexing", "Bestand indexeren...");
	    lang.put("fileupload.status.ok", "Bestand correct geladen");
	    lang.put("fileupload.upload.status", "Laad-status");
	    lang.put("fileupload.upload.uploaded", "Geladen");
	    lang.put("fileupload.upload.completed", "Laden gedaan");
	    lang.put("fileupload.upload.runtime", "Looptijd");
	    lang.put("fileupload.upload.remaining", "Resterend");
	    lang.put("fileupload.button.close", "Sluiten");
	    lang.put("fileupload.button.add.other.file", "Nog een document toevoegen");
	    lang.put("fileupload.status.move.file", "Bestand verplaatsen...");
	    lang.put("fileupload.status.move.mail", "Mail verplaatsen...");
	    lang.put("fileupload.status.copy.file", "Bestand kopieren...");
	    lang.put("fileupload.status.copy.mail", "Mail kopieren...");
	    lang.put("fileupload.status.restore.file", "Bestand terughalen...");
	    lang.put("fileupload.status.restore.mail", "Mail terughalen...");
	    lang.put("fileupload.status.move.folder", "Map verplaatsen...");
	    lang.put("fileupload.status.copy.folder", "Map kopieren...");
	    lang.put("fileupload.status.restore.folder", "Map terughalen...");
	    lang.put("fileupload.status.create.from.template", "Bestand maken vanuit template...");
		lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Nieuwe bestanden toevoegen");
	    lang.put("fileupload.label.update", "Bestanden bijwerken");
	    lang.put("fileupload.label.users.notify", "Melden bij gebruikers");
	    lang.put("fileupload.label.comment", "Commentaar");
	    lang.put("fileupload.label.users.to.notify",  "Gebruikers voor notificatie");
	    lang.put("fileupload.label.users",  "Gebruikers");
	    lang.put("fileupload.label.must.select.users",  "U moet een gebruiker selecteren voor notificatie");
	    lang.put("fileupload.label.importZip", "Import Documenten vanuit ZIP");
	    lang.put("fileupload.label.notify.comment", "Notificatie bericht");
	    lang.put("fileupload.label.error.importing.zip", "Fout bij importeren bestand");
	    lang.put("fileupload.label.error.move.file", "Fout bij verplaatsen bestand");
	    lang.put("fileupload.label.error.move.mail", "Fout bij verplaatsen mail");
	    lang.put("fileupload.label.error.copy.file", "Fout bij kopieren bestand");
	    lang.put("fileupload.label.error.copy.mail", "Fout bij kopieren mail");
	    lang.put("fileupload.label.error.copy.file", "Fout bij kopieren bestand");
	    lang.put("fileupload.label.error.restore.file", "Fout bij terughalen bestand");
	    lang.put("fileupload.label.error.move.folder", "Fout bij verplaatsen map");
	    lang.put("fileupload.label.error.copy.folder", "Fout bij kopieren map");
	    lang.put("fileupload.label.error.restore.folder", "Fout bij terugplaatsen map");
	    lang.put("fileupload.label.error.create.from.template", "Fout: kan geen bestand maken van template");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Verplaatsen naar zelfde of sub map niet toegestaan");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Verplaatsen naar selfde map niet toegestaan");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Bestand creeren op zelfde map niet toegestaan");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Eigenschappen");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "Geschiedenis");
	    lang.put("tab.document.status.history", "Geschiedenis bijwerken");
	    lang.put("tab.document.security", "Beveiliging bestanden");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.status.security.role", "Beveiliging: groepen bijwerken");
	    lang.put("tab.status.security.user", "Beveiliging: gebruikers bijwerken");
	    lang.put("tab.document.status.group.properties", "Property group bijwerken");
	    lang.put("tab.document.status.set.keywords", "Keywords opslaan");
	    lang.put("tab.document.status.get.version.history.size", "Verversen document history grootte");
	    lang.put("tab.document.status.purge.version.history", "Comprimeren document historie");
	    lang.put("tab.document.status.restore.version", "Terughalen document versie");
	    lang.put("tab.folder.properties", "Eigenschappen");
	    lang.put("tab.folder.security", "Beveiliging mappen");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Bureaublad");
	    lang.put("tab.workspace.search", "Zoek");
	    lang.put("tab.workspace.dashboard", "DashBoard");
	    lang.put("tab.workspace.administration", "Administration");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Naam");
	    lang.put("document.folder", "Map");
	    lang.put("document.size", "Grootte");
	    lang.put("document.created", "Gemaakt");
	    lang.put("document.lastmodified", "Gewijzigd");
	    lang.put("document.mimetype", "MIME type");
	    lang.put("document.keywords", "Sleutelwoorden");
	    lang.put("document.by", "door");
	    lang.put("document.status", "Status");
	    lang.put("document.status.checkout", "Bewerkt door");
	    lang.put("document.status.locked", "Beschermd door");
	    lang.put("document.status.normal", "Beschikbaar");
	    lang.put("document.subscribed", "Subscribed");
	    lang.put("document.subscribed.yes", "Yes");
	    lang.put("document.subscribed.no", "No");
	    lang.put("document.history.size", "History size");
	    lang.put("document.subscribed.users", "Ingeschreven gebruikers");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Add note");
	    lang.put("document.keywords.cloud", "Keywords wolk");
	    lang.put("document.categories", "Categories");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Naam");
	    lang.put("folder.parent", "Bovenliggend");
	    lang.put("folder.created", "Gemaakt");
	    lang.put("folder.by", "door");
	    lang.put("folder.subscribed", "Subscribed");
	    lang.put("folder.subscribed.yes", "Yes");
	    lang.put("folder.subscribed.no", "No");
	    lang.put("folder.subscribed.users", "Subscribed users");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Versie");
	    lang.put("version.created", "Datum");
	    lang.put("version.author", "Auteur");
	    lang.put("version.size", "Grootte");
	    lang.put("version.purge.document", "Compact history");
	    lang.put("version.comment", "Comment");
	    
	    // Security
	    lang.put("security.label", "Beveiliging");
	    lang.put("security.group.name", "Groep");
	    lang.put("security.group.permission.read", "Lees");
	    lang.put("security.group.permission.write", "Schrijf");
	    lang.put("security.user.name", "Gebruiker");
	    lang.put("security.user.permission.read", "Lees");
	    lang.put("security.user.permission.write", "Schrijf");
	    lang.put("security.users", "Gebruikers");
	    lang.put("security.groups", "Groepen");
	    lang.put("security.recursive", "Overerfbare rechten");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "Van");
	    lang.put("mail.reply", "Beantwoorden");
	    lang.put("mail.to", "Aan");
	    lang.put("mail.subject", "Onderwerp");
	    lang.put("mail.attachment", "Bijlagen");
	    
	    // Error
	    lang.put("error.label", "Het systeem heeft een fout gemaakt");
	    lang.put("error.invocation", "Communicatiefout met server");
	    
	    // About
	    lang.put("about.label", "Over OpenKM");
	    lang.put("about.loading", "Laden ...");
	    
	    // Logout
	    lang.put("logout.label", "Afsluiten");
	    lang.put("logout.closed", "OpenKM werd correct afgesloten.");
	    lang.put("logout.logout", "OpenKM is bezig met uitloggen, aub geduld");
	    
	    // Confirm
	    lang.put("confirm.label", "Bevestigen");
	    lang.put("confirm.delete.folder", "Wilt u echt deze map wissen ?");
	    lang.put("confirm.delete.document", "Wilt u echt dit bestand wissen ?");
	    lang.put("confirm.delete.trash", "Wilt u echt de prullenbak leegmaken ?");
	    lang.put("confirm.purge.folder", "Wilt u echt deze map verwijderen ?");
	    lang.put("confirm.purge.document", "Wilt u echt dit bestand verwijderen ?");
	    lang.put("confirm.delete.propety.group", "� Wilt u echt deze property groep verwijderen ?");
	    lang.put("confirm.purge.version.history.document", "� Wilt u echt deze document geschiedenis verwijderen ?");
	    lang.put("confirm.purge.restore.document", "� Wilt u echt deze document geschiedenis terughalen ?");
	    lang.put("confirm.set.default.home", "� Wilt u echt de standaard thuismap veranderen ?");
	    lang.put("confirm.delete.saved.search", "� Wilt u echt deze opgeslagen zoekopdracht verwijderen ?");
	    lang.put("confirm.delete.user.news", "� Wilt u echt dit gebruikers nieuws verwijderen ?");
	    lang.put("confirm.delete.mail", "� Wilt u echt deze mail verwijderen ?");
	    
	    // Search inputs
	    lang.put("search.context", "Context");
	    lang.put("search.content", "Inhoud");
	    lang.put("search.name", "Naam");
	    lang.put("search.keywords", "Sleutelwoorden");
		lang.put("search.folder", "Map");
	    lang.put("search.results", "Resultaten");
	    lang.put("search.to", "aan");
	    lang.put("search.page.results", "Page results");
	    lang.put("search.add.property.group", "Property groep toevoegen");
	    lang.put("search.mimetype", "Mime type");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Dokument");
	    lang.put("search.type.folder", "Map");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Uitgebreide zoekopdracht");
	    lang.put("search.user", "Gebruiker");
	    lang.put("search.date.and", "and");
	    lang.put("search.date.range", "Datum tussen");
	    lang.put("search.save.as.news", "Opslaan als gebruiker nieuws");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter op map");
	    
	    // Search results
	    lang.put("search.result.name", "Naam");
	    lang.put("search.result.score", "Relevance");
	    lang.put("search.result.size", "Grootte");
	    lang.put("search.result.date.update", "Datum bijwerken");
	    lang.put("search.result.author", "Auteur");
	    lang.put("search.result.version", "Versie");
	    lang.put("search.result.path", "Pad");
	    lang.put("search.result.menu.download", "Download");
	    lang.put("search.result.menu.go.folder", "Ga naar map");
	    lang.put("search.result.menu.go.document", "Ga naar document");
	    lang.put("search.result.status.findPaginated", "Zoek op");
	    lang.put("search.result.status.runsearch", "Werk opgeslagen zoekopdracht bij");
	    
	    // Search saved
	    lang.put("search.saved.run", "Uitvoeren");
	    lang.put("search.saved.delete", "Wissen");
	    lang.put("search.saved.status.getsearchs", "Opgeslagen zoekopdrachten verversen");
	    lang.put("search.saved.status.savesearch", "Opgeslagen zoekopdracht bijwerken");
	    lang.put("search.saved.status.deletesearch", "Opgeslagen zoekopdracht wissen");
	    lang.put("search.saved.status.getusernews", "Refreshing user news");
	    
	    // Button
	    lang.put("button.close", "Sluit");
	    lang.put("button.logout", "Afmelden");
	    lang.put("button.update", "Bewerk");
	    lang.put("button.cancel", "Annuleer");
	    lang.put("button.accept", "Accepteer");
	    lang.put("button.restore", "Plaats terug");
	    lang.put("button.move", "Verplaats");
	    lang.put("button.change", "Verander");
	    lang.put("button.search", "Zoek");
	    lang.put("button.save.search", "Zoekopdracht bewaren");
	    lang.put("button.view", "Bekijk");
	    lang.put("button.clean", "Opschonen");
	    lang.put("button.add", "Voeg toe");
	    lang.put("button.delete", "Verwijder");
	    lang.put("button.copy", "Kopieer");
	    lang.put("button.create", "Aanmaken");
	    lang.put("button.show", "Toon");
	    lang.put("button.memory", "Bewerk");
	    lang.put("button.copy.clipboard", "Kopieren naar klembord");	
	    lang.put("button.start", "Start");
	    lang.put("button.select", "Selecteer");
	    
	    // Group
	    lang.put("group.label", "Property groep toevoegen");
	    lang.put("group.group", "Groep");
	    lang.put("group.property.group", "Eigenschap");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Favoriet toevoegen");
	    lang.put("bookmark.name", "Naam");
	    lang.put("bookmark.edit.label", "Favoriet bewerken");
	    lang.put("bookmark.path", "Pad");
	    lang.put("bookmark.type", "Type");
	    lang.put("bookmark.type.document", "Document");
	    lang.put("bookmark.type.folder", "Map");
	    
	    // Notify
	    lang.put("notify.label", "Document link versturen");
	    
	    // Status
	    lang.put("status.document.copied", "Document gemarkeerd voor kopieren");
	    lang.put("status.document.cut", "Document gemarkeerd voor knippen");
	    lang.put("status.folder.copied", "Map gemarkeerd voor kopieren");
	    lang.put("status.folder.cut", "Map gemarkeerd voor knippen");
	    lang.put("status.keep.alive.error", "Verloren verbinding naar de server gedetecteerd (keep alive)");
	    lang.put("status.debug.enabled", "Debug Ingeschakeld");
	    lang.put("status.debug.disabled", "Debug Uitgeschakeld");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Zondag");
	    lang.put("calendar.day.monday", "Maandag");
	    lang.put("calendar.day.tuesday", "Dinsdag");
	    lang.put("calendar.day.wednesday", "Woensdag");
	    lang.put("calendar.day.thursday", "Donderdag");
	    lang.put("calendar.day.friday", "Vrijdag");
	    lang.put("calendar.day.saturday", "Zaterdag");
	    lang.put("calendar.month.january", "Januari");
	    lang.put("calendar.month.february", "Februari");
	    lang.put("calendar.month.march", "Maart");
	    lang.put("calendar.month.april", "April");
	    lang.put("calendar.month.may", "Mei");
	    lang.put("calendar.month.june", "Juni");
	    lang.put("calendar.month.july", "July");
	    lang.put("calendar.month.august", "Augustus");
	    lang.put("calendar.month.september", "September");
	    lang.put("calendar.month.october", "Oktober");
	    lang.put("calendar.month.november", "November");
	    lang.put("calendar.month.december", "December");
	    
	    // Media player
	    lang.put("media.player.label", "Media speler");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Afbeelding viewer");
	    lang.put("image.viewer.zoom.in", "Zoom in");
	    lang.put("image.viewer.zoom.out", "Zoom uit");
	    
	    // Debug console
	    lang.put("debug.console.label", "Debug console");
	    lang.put("debug.enable.disable", "CTRL+Z om debug modus aan/uit te zetten");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Algemeen");
	    lang.put("dashboard.tab.news", "Nieuws");
	    lang.put("dashboard.tab.user", "User");
	    lang.put("dashboard.tab.workflow", "Workflow");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Sleutelwoord mapping");

	    // Dahboard general
	    lang.put("dashboard.new.items", "New");
	    lang.put("dashboard.user.locked.documents", "Gelockte documenten");
	    lang.put("dashboard.user.checkout.documents", "Check-out documenten");
	    lang.put("dashboard.user.last.modified.documents", "Laatst bewerkte documenten");
	    lang.put("dashboard.user.last.downloaded.documents", "Laatst gedownloade documenten");
	    lang.put("dashboard.user.subscribed.documents", "Ingeschreven documenten");
	    lang.put("dashboard.user.subscribed.folders", "Ingeschreven mappen");
	    lang.put("dashboard.user.last.uploaded.documents", "Documenten laast ge-upload");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Vorige week top gedownloade documenten");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Vorige maand top gedownloade documenten");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Vorige week top bewerkte documenten");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Vorige maand top bekeken documenten");
	    lang.put("dashboard.general.last.uploaded.documents", "Documenten laatst ge-upload");
	    lang.put("dashboard.workflow.pending.tasks", "Wachtende opdrachten");
	    lang.put("dashboard.workflow.task", "Opdracht");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Naam");
	    lang.put("dashboard.workflow.task.created", "Creation date");
	    lang.put("dashboard.workflow.task.start", "Start datum");
	    lang.put("dashboard.workflow.task.duedate", "Due date");
	    lang.put("dashboard.workflow.task.end", "Eind datum");
	    lang.put("dashboard.workflow.task.description", "Description");
	    lang.put("dashboard.workflow.task.process.instance", "Process instance");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Versie");
	    lang.put("dashboard.workflow.task.process.name", "Naam");
	    lang.put("dashboard.workflow.task.process.description", "Omschrijving");
	    lang.put("dashboard.workflow.task.process.data", "Data");
	    lang.put("dashboard.workflow.task.process.definition", "Proces definitie");
	    lang.put("dashboard.workflow.task.process.path", "Pad");
	    lang.put("dashboard.refreshing", "Refreshing");
	    lang.put("dashboard.keyword", "Sleutelwoorden");
	    lang.put("dashboard.keyword.suggest", "Tik het sleutelwoord");
	    lang.put("dashboard.keyword.all", "Alle sleutelwoorden");
	    lang.put("dashboard.keyword.top", "Top sleutelwoorden");
	    lang.put("dashboard.keyword.related", "Gerelateerde sleutelwoorden");
	    lang.put("dashboard.keyword.goto.document", "Go naar document");
	    lang.put("dashboard.keyword.clean.keywords", "Sleutelwoorden opruimen");
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

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Add thesaurus keyword");
	    lang.put("thesaurus.tab.tree", "Tree");
	    lang.put("thesaurus.tab.keywords", "Keywords");
	    
	    // Categories
	    lang.put("categories.directory.select.label", "Add category");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Bestand toegang geweigerd");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Bestand niet gevonden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Bestand bestaat reeds");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Bestand beschermen geweigerd");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Bestand vrijgeven gewenst");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Interne fout met bestand");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Algemene fout met bestand");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Map toegang geweigerd");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Map niet gevonden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Map bestaat reeds");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Interne fout met map");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Algemene fout met map");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Folder path not found");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Toegang geweigerd");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Niet gevonden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Interne fout");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Algemene fout");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Bestand niet gevonden");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Algemene fout");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Bestandsformaat niet ondersteund");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Bestand bestaat reeds");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Bestandsgrootte overschreden");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Sessie afgesloten");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Algemene fout bij uitvoeren query");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "De opgeslagen zoekopdracht naam moet uniek zijn");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "De favoriet naam moet uniek zijn");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Session lost");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Interne fout met map");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Interne fout met map");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Interne fout met map");
	  }
}
