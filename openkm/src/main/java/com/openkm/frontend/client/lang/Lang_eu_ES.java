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
 * Euskera (Basque Country)
 * 
 * @author Uribarri Goikuria
 */
public class Lang_eu_ES {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd/MM/yyyy HH:mm:ss");
	    lang.put("general.day.pattern", "dd/MM/yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "OpenKM kargatzen");
	    lang.put("startup.starting.loading", " OpenKMren kargatzea hasten");
	    lang.put("startup.taxonomy", "Taxonomiaren nodo nagusia lortzen");
	    lang.put("startup.categories", "Kategorien nodo nagusia lortzen");
	    lang.put("startup.thesaurus", "Thesausus-en nodo nagusia lortzen");
	    lang.put("startup.template", "Txantiloien nodo nagusia lortzen");
	    lang.put("startup.personal", "Pertsonalen nodo nagusia lortzen");
	    lang.put("startup.mail", "Posten nodo nagusia lortzen");
	    lang.put("startup.trash", "Paperontziaren nodo nagusia lortzen");
	    lang.put("startup.user.home", "Erabiltzaileen hasiera lortzen");
	    lang.put("startup.bookmarks", "Markatzaileak lortzen");
	    lang.put("startup.loading.taxonomy", "Taxonomía kargatzen");
	    lang.put("startup.loading.taxonomy.getting.folders", "Taxonomia kargatzen - karpetak lortzen");
	    lang.put("startup.loading.taxonomy.eval.params", "Taxonomia kargatzen - parametroak ebaluatzen");
	    lang.put("startup.loading.taxonomy.open.path", "Taxonomia kargatzen - bidea zabaltzen");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Taxonomia kargatzen - karpetak lortzen");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Taxonomia kargatzen - dokumentuak lortzen");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Taxonomía kargatzen - posta elektronikoak lortzen");
	    lang.put("startup.loading.personal", "Pertsonalak kargatzen");
	    lang.put("startup.loading.mail", "mezuak kargatzen");
	    lang.put("startup.loading.categories", "Kategoriak kargatzen");
	    lang.put("startup.loading.thesaurus", "thesaurus kargatzen");
	    lang.put("startup.loading.templates", "txantiloiak kargatzen");
	    lang.put("startup.loading.trash", "Paperontzia kargatzen");
	    lang.put("startup.loading.history.search", "bilaketan historikoa kargatzen");
	    lang.put("startup.loading.user.values", "Erabiltzailearen datuak kargatzen");
	    lang.put("startup.keep.alive", "keep alive kargatzen");
	    
	    // Update notification
	    lang.put("openkm.update.available", "OpenKM-en aktualizazioa erabilgarri");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Taxonomia");
	    lang.put("leftpanel.label.trash", "Paperontzia");
	    lang.put("leftpanel.label.mail", "posta elektronikoa");
	    lang.put("leftpanel.label.stored.search", "Gordetako Bilaketak");
	    lang.put("leftpanel.label.categories", "Kategoriak");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Txantiloiak");
	    lang.put("leftpanel.label.my.documents", "Dokumentu Pertsonalak");
	    lang.put("leftpanel.label.user.search", "Erabilearen berriak");
	    lang.put("leftpanel.label.all.repository", "repositorio osoan");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Karpeta Sortu");
	    lang.put("tree.menu.directory.remove", "Ezabatu");
	    lang.put("tree.menu.directory.rename", "Berrizendatu");
	    lang.put("tree.menu.directory.refresh", "Freskatu");
	    lang.put("tree.menu.directory.move", "Mugitu");
	    lang.put("tree.menu.directory.copy", "Kopiatu");
	    lang.put("tree.menu.directory.add.document", "Dokumentua Gehitu");
	    lang.put("tree.menu.add.bookmark", "Markatzailea Gehitu");
	    lang.put("tree.menu.set.home", "Hasiera ezarri");
	    lang.put("tree.menu.export", "Fitxategia esportatu");
	    lang.put("tree.status.refresh.folder", "Karpeten ardatza eguneratzen");
	    lang.put("tree.status.refresh.create", "Karpeta Sortu");
	    lang.put("tree.status.refresh.delete", "Karpeta Ezabatu");
	    lang.put("tree.status.refresh.rename", "Karpeta Berrizendatu");
	    lang.put("tree.status.refresh.restore", "Karpeta Berriztatu");
	    lang.put("tree.status.refresh.purge", "Karpeta Ezabatu");
	    lang.put("tree.status.refresh.get", "Karpeta Eguneratu");
	    lang.put("tree.folder.new", "Karpeta Berria");
	    lang.put("tree.status.refresh.add.subscription", "Izenematea esartzen");
	    lang.put("tree.status.refresh.remove.subscription", "Izenematea ezabatzen");
	    lang.put("tree.status.refresh.get.root", "Sarrerako ontzia freskatzen ");
	    lang.put("tree.status.refresh.get.keywords", "Kode hitzak freskatzen");
	    lang.put("tree.status.refresh.get.user.home", "Erabiltzailearen hasiera lortzen");
	    lang.put("tree.status.refresh.purge.trash", "Paperontzia husten");
	    lang.put("tree.menu.directory.find.folder","Buscar carpeta");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Berriztatu");
	    lang.put("trash.menu.directory.purge", "Ezabatu");
	    lang.put("trash.menu.directory.purge.trash", "Paperontzia Hustu");
	    lang.put("trash.directory.select.label", "helburu karpeta aukeratu");
	    
	    // General menu
	    lang.put("general.menu.file", "Artxiboa");
	    	lang.put("general.menu.file.exit", "Irten");
	    	lang.put("general.menu.file.purge.trash", "Paperontzia hustu");
	    lang.put("general.menu.edit", "Edizioa");
			lang.put("general.menu.file.create.directory", "Karpeta sortu");
			lang.put("general.menu.file.download.document", "Dokumentua deskargatu");
			lang.put("general.menu.file.download.document.pdf", "Dokumentua PDF bezala deskargatu");
			lang.put("general.menu.file.send.link", "dokumentuaren lotura bidali");
			lang.put("general.menu.file.send.attachment", "Enviar documento adjunto");
			lang.put("general.menu.file.lock", "Blokeatu");
			lang.put("general.menu.file.unlock", "Desblokeatu");
			lang.put("general.menu.file.add.document", "Dokumentua erantsi");
			lang.put("general.menu.file.checkout", "Checkout");
			lang.put("general.menu.file.checkin", "Checkin");
			lang.put("general.menu.file.cancel.checkout", " checkout-a ezeztatu");
			lang.put("general.menu.file.delete", "Ezabatu");
			lang.put("general.menu.file.refresh", "Freskatu");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "Fitxategiak Igo");
	    lang.put("general.menu.tools", "Tresnak");
	    	lang.put("general.menu.tools.languages", "Hizkuntzak");
	    	lang.put("general.menu.tools.skin", "Itxura");
    			lang.put("general.menu.tools.skin.default", "Lehenetsia");
    			lang.put("general.menu.tools.skin.default2", "Lehenetsia 2");
    			lang.put("general.menu.tools.skin.mediumfont", "iturri ertaina");
    			lang.put("general.menu.tools.skin.bigfont", "iturri handia");
    		lang.put("general.menu.debug.console", "depurazio konsola");
    		lang.put("general.menu.administration", "Administrazioa erakutsi");
    		lang.put("general.menu.tools.preferences", "Lehentasunak");
    			lang.put("general.menu.tools.user.preferences", "Erabiltzailearen konfigurazioa");
    	lang.put("general.menu.bookmark", "Markatzaileak");
	    	lang.put("general.menu.bookmark.home", "Hasiera");
	    	lang.put("general.menu.bookmark.default.home", "Hasiera lehenetsia");
	    	lang.put("general.menu.bookmark.edit", "Markatzaileak editatu");
	    lang.put("general.menu.help", "Laguntza");
		    lang.put("general.menu.bug.report", "Akatzak erdietsi");
		    lang.put("general.menu.support.request", "Laguntza eskaera");
		    lang.put("general.menu.public.forum", "Foro publikoa");
		    lang.put("general.menu.project.web", "Proiektuaren web-orria");
		    lang.put("general.menu.documentation", "Dokumentazioa");
		    lang.put("general.menu.version.changes", "Bertsioaren oharrak");
		    lang.put("general.menu.about", "OpenKM-ri buruz");
	    lang.put("general.connected", "Bezala Konektatua");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Izena");
	    lang.put("filebrowser.date.update", "Modifikikazio data");
	    lang.put("filebrowser.size", "Tamaina");
	    lang.put("filebrowser.path", "Bidea");
	    lang.put("filebrowser.author", "Sortzailea");
	    lang.put("filebrowser.version", "Bersioa");
	    lang.put("filebrowser.menu.checkout", "Check-out");
	    lang.put("filebrowser.menu.checkin", "Check-in");
	    lang.put("filebrowser.menu.delete", "Ezabatu");
	    lang.put("filebrowser.menu.rename", "Berrizendatu");
	    lang.put("filebrowser.menu.checkout.cancel", "check-out-a ezeztatu");
	    lang.put("filebrowser.menu.lock", "Blokeatu");
	    lang.put("filebrowser.menu.unlock", "Desblokeatu");
	    lang.put("filebrowser.menu.download", "Descargatu");
	    lang.put("filebrowser.menu.move", "Mugitu");
	    lang.put("filebrowser.menu.copy", "Kopiatu");
	    lang.put("filebrowser.menu.create.from.template", "Txantiloitik sortu");
	    lang.put("filebrowser.menu.add.property.group", "Propietate taldea gehitu");
	    lang.put("filebrowser.menu.remove.property.group", "Propietate taldea ezabatu");
	    lang.put("filebrowser.menu.start.workflow", " workflow hasi");
	    lang.put("filebrowser.menu.add.subscription", "Harpidetza gehitu");
	    lang.put("filebrowser.menu.remove.subscription", "Harpidetza ezabatu");
	    lang.put("filebrowser.menu.add.bookmark", "Markatzailea gehitu");
	    lang.put("filebrowser.menu.set.home", "Hasiera ezarri");
	    lang.put("filebrowser.menu.refresh", "Freskatu");
	    lang.put("filebrowser.menu.export", "ZIP-era exportatu");
	    lang.put("filebrowser.menu.play", "Erreproduzitu");
	    lang.put("filebrowser.menu.image.viewer", "Irudi bisorea");
	    lang.put("filebrowser.status.refresh.folder", "Karpeten zerrenda eguneratu");
	    lang.put("filebrowser.status.refresh.document", "Dokumentu zerrenda eguneratu");
	    lang.put("filebrowser.status.refresh.mail", "helbide elektronikoen zerrenda eguneratu");
	    lang.put("filebrowser.status.refresh.delete.folder", "Karpeta ezabatzen");
	    lang.put("filebrowser.status.refresh.delete.document", "Dokumentua ezabatzen");
	    lang.put("filebrowser.status.refresh.checkout", "checkout-a egiten");
	    lang.put("filebrowser.status.refresh.lock", "lock-a esartzen");
	    lang.put("filebrowser.status.refresh.unlock", "lock-a ezabatzen");
	    lang.put("filebrowser.status.refresh.document.rename", "Dokumentua berrizendatzen");
	    lang.put("filebrowser.status.refresh.folder.rename", "Karpeta berrizendatzen");
	    lang.put("filebrowser.status.refresh.document.purge", "Dokumentua ezabatzen");
	    lang.put("filebrowser.status.refresh.folder.purge", "Karpeta Ezabatzen");
	    lang.put("filebrowser.status.refresh.folder.get", "Karpeta Eguneratzen");
	    lang.put("filebrowser.status.refresh.document.get", "Dokumentua eguneratzen");
	    lang.put("filebrowser.status.refresh.add.subscription", "Harpidetze esartzen");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Harpidetza ezabatzen");
	    lang.put("filebrowser.status.refresh.get.user.home", "Sarrera lortzen");
	    lang.put("filebrowser.status.refresh.delete.mail", "Mezu elektronikoa ezabatzen");
	    lang.put("filebrowser.status.refresh.mail.purge", "Mezu elektronikoa ezabatzen");
	    
	    // File Upload
	    lang.put("fileupload.send", "Bidali");
	    lang.put("fileupload.status.sending", "Fitxategia igotzen...");
	    lang.put("fileupload.status.indexing", "Fitxategia indexatzen ...");
	    lang.put("fileupload.status.ok", "Bidalketa zuzen eginda");
	    lang.put("fileupload.upload.status", "Bidalketaren egoera");
	    lang.put("fileupload.upload.uploaded", "Burutua");
	    lang.put("fileupload.upload.completed", "Bidalketa Burutua");
	    lang.put("fileupload.upload.runtime", "Ejekuzio denbora");
	    lang.put("fileupload.upload.remaining", "falta");
	    lang.put("fileupload.button.close", "Itxi");
	    lang.put("fileupload.button.add.other.file", "Beste fitxategi bat gehitu");
	    lang.put("fileupload.status.move.file", "Fitxategia mugitzen..");
	    lang.put("fileupload.status.move.mail", "Mezu elektronikoa mugitzen...");
	    lang.put("fileupload.status.copy.file", "Fitxategia kopiatzen..");
	    lang.put("fileupload.status.copy.mail", "Mezu elektronikoa kopiatzen...");
	    lang.put("fileupload.status.restore.file", "Fitxategia berriztatzen...");
	    lang.put("fileupload.status.restore.mail", "Mezu elektronikoa berriztatzen...");
	    lang.put("fileupload.status.move.folder", "Karpeta mugitzen...");
	    lang.put("fileupload.status.copy.folder", "Karpeta kopiatzen...");
	    lang.put("fileupload.status.restore.folder", "Karpeta berrizten...");
	    lang.put("fileupload.status.create.from.template", "Fitxategia txantiloitik sortzen...");
	    lang.put("fileupload.status.of", "-ena");
	    lang.put("fileupload.label.insert", "Dokumentuak txertatu");
	    lang.put("fileupload.label.update", "Dokumentuak eguneratu");
	    lang.put("fileupload.label.users.notify", "Erabiltzaileei jakinarazi");
	    lang.put("fileupload.label.comment", "Iruzkina");
	    lang.put("fileupload.label.users.to.notify",  "Jakinarazteko erabiltzaileak");
	    lang.put("fileupload.label.users",  "Erabiltzaileak");
	    lang.put("fileupload.label.groups.to.notify","Grupos a notificar");
	    lang.put("fileupload.label.groups","Grupos");
	    lang.put("fileupload.label.must.select.users",  "Aukeratu erabiltzaieleren bat jakinarazteko");
	    lang.put("fileupload.label.importZip", "ZIP-etik dokumentuak inportatu");
	    lang.put("fileupload.label.notify.comment", "Jakinarazpen mezua");
	    lang.put("fileupload.label.error.importing.zip", "Errorea fitxategia inportatzerakoan");
	    lang.put("fileupload.label.error.move.file", "Errorea fitxaegia mugitzerakoan");
	    lang.put("fileupload.label.error.move.mail", "Errorea mezu elektronikoa mugitzerakoan");
	    lang.put("fileupload.label.error.copy.file", "Errorea fitxategia kopiatzerakoan");
	    lang.put("fileupload.label.error.copy.mail", "Errorea mezu elektronikoa kopiatzerakoan");
	    lang.put("fileupload.label.error.restore.file", "Errorea fitxategia berriztatzerakoan");
	    lang.put("fileupload.label.error.restore.mail", "Errorea mezu elektronikoa berriztatzerakoan");
	    lang.put("fileupload.label.error.move.folder", "Errorea karpeta mugitzerakoan");
	    lang.put("fileupload.label.error.copy.folder", "Errorea karpeta kopiatzerakoan");
	    lang.put("fileupload.label.error.restore.folder", "Errorea karpeta berriztatzerakoan");
	    lang.put("fileupload.label.error.create.from.template", "Errorea txantiloitik fitxategia sortzerakoan");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Ezin da mugitu jatorrizko karpetan edo ondorengoetan");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Ezin da jatorrizko karpeta kopiatu");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Ezin da karpeta bat sortu jatorrizko karpetan");

	    // Tab properties
	    lang.put("tab.document.properties", "Ezaugarriak");
	    lang.put("tab.document.notes", "Oharrak");
	    lang.put("tab.document.history", "Historial-a");
	    lang.put("tab.document.status.history", "Historial-a eguneratzen");
	    lang.put("tab.status.security.role", "Actualizando seguridad roles");
	    lang.put("tab.status.security.user", "Erabiltzaileen segurtasuna eguneratzen");
	    lang.put("tab.document.status.group.properties", "Ezaugarri taldea eguneratzen");
	    lang.put("tab.document.status.set.keywords", "Kode Hitzak esartzen");
	    lang.put("tab.document.status.set.categories", "Mailak eguneratzen");
	    lang.put("tab.document.status.get.version.history.size", "Dokumentuaren historikoaren neurria freskatzen");
	    lang.put("tab.document.status.purge.version.history", "Compactando el histórico de documentos");
	    lang.put("tab.document.status.restore.version", "Dokumentuaren bertsioa berriztatzen");
	    lang.put("tab.document.security", "Segurtasuna");
	    lang.put("tab.document.preview", "Ikusi");
	    lang.put("tab.folder.properties", "Ezaugarriak");
	    lang.put("tab.folder.security", "Segurtasuna");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Idazmahaia");
	    lang.put("tab.workspace.search", "Bilatzailea");
	    lang.put("tab.workspace.dashboard", "ohar-taula");
	    lang.put("tab.workspace.administration", "Administrazioa");
	    
	    // Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Izena");
	    lang.put("document.folder", "Karpeta");
	    lang.put("document.size", "Neurria");
	    lang.put("document.created", "Sortua");
	    lang.put("document.lastmodified", "Aldatua");
	    lang.put("document.mimetype", "MIME mota");
	    lang.put("document.keywords", "Kode hitzak");
	    lang.put("document.by", "-ek");
	    lang.put("document.status", "Egoera");
	    lang.put("document.status.checkout", "-gatik editatua");
	    lang.put("document.status.locked", "-ek blokeatua");
	    lang.put("document.status.normal", "Erabilgarri");
	    lang.put("document.subscribed", "Izenpetua");
	    lang.put("document.subscribed.yes", "Bai");
	    lang.put("document.subscribed.no", "Ez");
	    lang.put("document.history.size", "Historiakoaren neurria");
	    lang.put("document.subscribed.users", "Izenemandako erabiltzaileak");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Oharra gehitu");
	    lang.put("document.keywords.cloud", "Kode hitzen hodeia");
	    lang.put("document.categories", "Mailak");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Izena");
	    lang.put("folder.parent", "Aita");
	    lang.put("folder.created", "Sortua");
	    lang.put("folder.by", "-ek");
	    lang.put("folder.subscribed", "Izenpetua");
	    lang.put("folder.subscribed.yes", "Bai");
	    lang.put("folder.subscribed.no", "Ez");
	    lang.put("folder.subscribed.users", "Izenpetutako erabileak");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Carpetas");
	    lang.put("folder.number.documents", "Documentos");
	    lang.put("folder.number.mails", "Correos electrónicos");
	    
	    // Version
	    lang.put("version.name", "Bertsioa");
	    lang.put("version.created", "Data");
	    lang.put("version.author", "Egile");
	    lang.put("version.size", "Tamaina");
	    lang.put("version.purge.document", "Historikoa trinkatu");
	    lang.put("version.comment", "Iruzkina");
	    
	    // Security
	    lang.put("security.label", "Segurtasuna");
	    lang.put("security.group.name", "Taldea");
	    lang.put("security.group.permission.read", "Irakurketa");
	    lang.put("security.group.permission.write", "Idazketa");
	    lang.put("security.group.permission.delete", "Borrado");
	    lang.put("security.group.permission.security", "Seguridad");
	    lang.put("security.user.name", "Erabiltzaiea");
	    lang.put("security.user.permission.read", "Irakurketa");
	    lang.put("security.user.permission.write", "Idazketa");
	    lang.put("security.user.permission.delete", "Borrado");
	    lang.put("security.user.permission.security", "Seguridad");
	    lang.put("security.users", "Erabiltzaielak");
	    lang.put("security.groups", "Taldeak");
	    lang.put("security.recursive", "Errekurtsibo eran aldaketak ezarri");
	    lang.put("secutiry.filter.by.users","Filtro de usuarios");
	    lang.put("secutiry.filter.by.groups","Filtro de grupos");
	    lang.put("security.status.updating","Actualizando la seguridad");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preikusketa ez erabilgarri");

	    // Mail
	    lang.put("mail.from", "-tik");
	    lang.put("mail.reply", "-ri erantzun");
	    lang.put("mail.to", "-tzako");
	    lang.put("mail.subject", "Gaia");
	    lang.put("mail.attachment", "Eranskinak");
	    
	    // Error
	    lang.put("error.label", "Sistemak errorea sortu du");
	    lang.put("error.invocation", "Errorea zerbitzariarekin komunikatzerakoan");
	    
	    // About
	    lang.put("about.label", "OpenKM-ri buruz");
	    lang.put("about.loading", "Kargatzen ...");
	    
	    // Logout
	    lang.put("logout.label", "Irten");
	    lang.put("logout.closed", "OpenKM egoki itxi da.");
	    lang.put("logout.logout", "OpenKM izten ari da; mesedez itxaron");
	    
	    // Confirm
	    lang.put("confirm.label", "Baieztapena");
	    lang.put("confirm.delete.folder", "Ziur karpeta ezabatu nahi duzula?");
	    lang.put("confirm.delete.document", "Ziur dokumentua ezabatu nahi duzula?");
	    lang.put("confirm.delete.trash", "Ziur paperontzia utzitu nahi duzula?");
	    lang.put("confirm.purge.folder", "Ziur karpeta ezabatu nahi duzula?");
	    lang.put("confirm.purge.document", "Ziur dokumentua ezabatu nahi duzula?");
	    lang.put("confirm.delete.propety.group", "Ziur ezaugarri taldea ezabatu nahi duzula?");
	    lang.put("confirm.purge.version.history.document", "Ziur dokumentuaren historikoa ezabatu nahi duzula?");
	    lang.put("confirm.purge.restore.document", "Ziur bertsio honetara berriztatu nahi duzula dokumentuaren historikoa?");
	    lang.put("confirm.set.default.home", "Ziur zaude hasiera lehenetsia ezarri nahi duzula");
	    lang.put("confirm.delete.saved.search", "Ziur gordetako kontsulta ezabatu nahi duzula?");
	    lang.put("confirm.delete.user.news", "Ziur erabiltzailearen berria ezabatu nahi duzula?");
	    lang.put("confirm.delete.mail", "Ziur mezu elektronikoa ezabatu nahi duzula?");
	    lang.put("confirm.get.pooled.workflow.task", "¿Seguro que desea asignarse esta tarea?");
	    lang.put("confirm.force.unlock", "¿Seguro que desea forzar el desbloqueo del documento?");
	    lang.put("confirm.force.cancel.checkout", "¿Seguro que desea forzar la cancelación del documento editado?");
	    
	    // Search
	    lang.put("search.context", "Testuinguru");
	    lang.put("search.content", "Edukina");
	    lang.put("search.name", "Izena");
	    lang.put("search.keywords", "Kode Hitzak");
	    lang.put("search.folder", "Karpeta");
	    lang.put("search.category", "Maila");
	    lang.put("search.results", "Emaizak");
	    lang.put("search.to", "-era");
	    lang.put("search.page.results", "Orri bakoitzeko emaitzak");
	    lang.put("search.add.property.group", "Talde ezaugarria gehitu");
	    lang.put("search.mimetype", "Dokumentu mota");
	    lang.put("search.type", "Mota");
	    lang.put("search.type.document", "Dokumentua");
	    lang.put("search.type.folder", "Karpeta");
	    lang.put("search.type.mail", "Posta Elektronikoa");
	    lang.put("search.advanced", "Bilaketa aurreratua");
	    lang.put("search.user", "Erabiltzailea");
	    lang.put("search.date.and", "eta");
	    lang.put("search.date.range", "Data barrutiak -tik");
	    lang.put("search.save.as.news", "Erabiltzaileraren berri bezala gorde");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Karpeta iragazpena");
	    lang.put("search.category.filter", "Maila iragazpena");
	    
	    // Search results
	    lang.put("search.result.name", "Izena");
	    lang.put("search.result.score", "Garrantsia");
	    lang.put("search.result.size", "Neurria");
	    lang.put("search.result.date.update", "Modifikazioa data");
	    lang.put("search.result.author", "Egilea");
	    lang.put("search.result.version", "Bestsioa");
	    lang.put("search.result.path", "Kokapena");
	    lang.put("search.result.menu.download", "Deskargatu");
	    lang.put("search.result.menu.go.folder", "karpetara joan");
	    lang.put("search.result.menu.go.document", "dokumentora joan");
	    lang.put("search.result.status.findPaginated", "Bilaketa burutu");
	    lang.put("search.result.status.runsearch", "Gordetako bilaketa burutu");
	    
	    // Search saved
	    lang.put("search.saved.run", "Burutu");
	    lang.put("search.saved.delete", "Ezabatu");
	    lang.put("search.saved.status.getsearchs", "Gordetako bilaketak eguneratu");
	    lang.put("search.saved.status.savesearch", "Bilaketa gordetzen");
	    lang.put("search.saved.status.deletesearch", "Gordetako bilaketa ezabatzen");
	    lang.put("search.saved.status.getusernews", "Erabiltzaielearen berriak eguneratzen");
	    
	    // Button
	    lang.put("button.close", "Itxi");
	    lang.put("button.logout", "Irten");
	    lang.put("button.update", "Eguneratu");
	    lang.put("button.cancel", "Ezeztatu");
	    lang.put("button.accept", "Baieztatu");
	    lang.put("button.restore", "Berriztatu");
	    lang.put("button.move", "Mugitu");
	    lang.put("button.change", "Aldatu");
	    lang.put("button.search", "Bilatu");
	    lang.put("button.save.search", "Kontsulta Gorde");
	    lang.put("button.view", "Ikusi");
	    lang.put("button.clean", "Garbitu");
	    lang.put("button.add", "Gehitu");
	    lang.put("button.delete", "Ezabatu");
	    lang.put("button.copy", "Kopiatu");
	    lang.put("button.create", "Crear");
	    lang.put("button.show", "Erakutsi");
	    lang.put("button.memory", "Eguneratu");
	    lang.put("button.copy.clipboard", "Paper-zorrora kopiatu");	
	    lang.put("button.start", "Hasi");
	    lang.put("button.select", "Aukeratu");
	    lang.put("button.test", "Comprobar");
	    lang.put("button.next", "Siguiente");
	    
	    // Group
	    lang.put("group.label", "Ezaugarri taldea gehitu");
	    lang.put("group.group", "Taldea");
	    lang.put("group.property.group", "Ezaugarri");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Markatzailea gehitu");
	    lang.put("bookmark.name", "Izena");
	    lang.put("bookmark.edit.label", "Markatzaileen edizioa");
	    lang.put("bookmark.path", "Bidea");
	    lang.put("bookmark.type", "Mota");
	    lang.put("bookmark.type.document", "Dokumentua");
	    lang.put("bookmark.type.folder", "Karpeta");
	    
	    // Notify
	    lang.put("notify.label", "Dokumentuaren lotura bidali");
	    lang.put("notify.label.attachment", "Enviar documento adjunto");
	    
	    // Status
	    lang.put("status.document.copied", "Kopiatzeko markatutako dokumentua");
	    lang.put("status.document.cut", "Mozteko markatutako dokumentua");
	    lang.put("status.folder.copied", "Kopiatzeko markatutako karpeta");
	    lang.put("status.folder.cut", "Mozteko markatutako karpeta");
	    lang.put("status.keep.alive.error", "konexio galdua Zerbitzariarekin (keep alive)");
	    lang.put("status.debug.enabled", "Garbiketa aktibatua");
	    lang.put("status.debug.disabled", "Garbiketa desaktibatua");
	    lang.put("status.network.error.detected", "Sarean errorea antzemana");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Igandea");
	    lang.put("calendar.day.monday", "Astelehena");
	    lang.put("calendar.day.tuesday", "Asteartea");
	    lang.put("calendar.day.wednesday", "Asteazkena");
	    lang.put("calendar.day.thursday", "Osteguna");
	    lang.put("calendar.day.friday", "Ostirala");
	    lang.put("calendar.day.saturday", "Larunbat");
	    lang.put("calendar.month.january", "Urtarrilla");
	    lang.put("calendar.month.february", "Otsaila");
	    lang.put("calendar.month.march", "Martxoa");
	    lang.put("calendar.month.april", "Apirila");
	    lang.put("calendar.month.may", "Maiatza");
	    lang.put("calendar.month.june", "Ekaina");
	    lang.put("calendar.month.july", "Uztaila");
	    lang.put("calendar.month.august", "Abuztua");
	    lang.put("calendar.month.september", "Iraila");
	    lang.put("calendar.month.october", "Urria");
	    lang.put("calendar.month.november", "Azaroa");
	    lang.put("calendar.month.december", "Abendua");
	    
	    // Media player
	    lang.put("media.player.label", "Multimedia erreproduzitzailea");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Irudien bisorea");
	    lang.put("image.viewer.zoom.in", "Handitu");
	    lang.put("image.viewer.zoom.out", "Txikitu");
	    
	    // Debug console
	    lang.put("debug.console.label", "Garbiketa Konsola");
	    lang.put("debug.enable.disable", "CTRL+Z garbiketa modua aktibatu edo desaktibatzeko");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Orokorra");
	    lang.put("dashboard.tab.news", "Berriak");
	    lang.put("dashboard.tab.user", "Erabilea");
	    lang.put("dashboard.tab.workflow", "Workflow");
	    lang.put("dashboard.tab.mail", "Posta");
	    lang.put("dashboard.tab.keymap", "Kode Hitzak");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Berriak");
	    lang.put("dashboard.user.locked.documents", "Blokeatutako dokumentuak");
	    lang.put("dashboard.user.checkout.documents", "checkout-en dokumentuak");
	    lang.put("dashboard.user.last.modified.documents", "Aldatutako azken dokumentuak");
	    lang.put("dashboard.user.last.downloaded.documents", "Deskargatutako azken dokumentuak");
	    lang.put("dashboard.user.subscribed.documents", "Izenpetutako dokumentuak");
	    lang.put("dashboard.user.subscribed.folders", "Izenpetutako karpetak");
	    lang.put("dashboard.user.last.uploaded.documents", "Igondako azken dokumentuak");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Azken asteko dokumentu ikusienak");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Azken hiledo dokumentu ikusienak");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Azken asten dokumentu aldatuenak");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Azken hilean dokumentu aldatuenak");
	    lang.put("dashboard.general.last.uploaded.documents", "Igondako azken dokumentuak");
	    lang.put("dashboard.workflow.pending.tasks", "Egiteko lanak");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Tareas pendientes no asignadas");
	    lang.put("dashboard.workflow.task", "Lana");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Izena");
	    lang.put("dashboard.workflow.task.created", "Sortze data ");
	    lang.put("dashboard.workflow.task.start", "Hasiera Data");
	    lang.put("dashboard.workflow.task.duedate", "Muga data");
	    lang.put("dashboard.workflow.task.end", "Bukaera Data");
	    lang.put("dashboard.workflow.task.description", "Deskribapena");
	    lang.put("dashboard.workflow.task.process.instance", "Instancia de proceso");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Bertsioa");
	    lang.put("dashboard.workflow.task.process.name", "Izena");
	    lang.put("dashboard.workflow.task.process.description", "Deskribapena");
	    lang.put("dashboard.workflow.task.process.data", "Datos");
	    lang.put("dashboard.workflow.comments", "Commentario");
	    lang.put("dashboard.workflow.task.process.forms", "Formulario");
	    lang.put("dashboard.workflow.add.comment","Añadir comentario");
	    lang.put("dashboard.workflow.task.process.definition", "Prozesuaren definizioa");
	    lang.put("dashboard.workflow.task.process.path", "Bidea");
	    lang.put("dashboard.refreshing", "Freskatzen");
	    lang.put("dashboard.keyword", "Kode Hitzak");
	    lang.put("dashboard.keyword.suggest", "Hitza idatzi");
	    lang.put("dashboard.keyword.all", "Kode Hitzak");
	    lang.put("dashboard.keyword.top", "Kode Hitz erabilienak");
	    lang.put("dashboard.keyword.related", "Erlazionatutako koder hitzak");
	    lang.put("dashboard.keyword.goto.document", "Dokumentura joan");
	    lang.put("dashboard.keyword.clean.keywords", "Kode Hitzak Garbitu");
	    lang.put("dashboard.mail.last.imported.mails", "Posta Elektronikoak");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Erantzitako Dokumentuak");
	    
	    // Workflow
	    lang.put("workflow.label", "Workflow Hasi");
	    
	    // User configuration
	    lang.put("user.preferences.label", "Erabiltzailearen konfigurazioa");
	    lang.put("user.preferences.user", "Erabiltzailea");
	    lang.put("user.preferences.password", "Kodea");
	    lang.put("user.preferences.mail", "Posta elektronikoa");
	    lang.put("user.preferences.roles","Roles");
	    lang.put("user.preferences.imap.host", "Servidor de IMAP");
	    lang.put("user.preferences.imap.user", " IMAP Erabiltzailea");
	    lang.put("user.preferences.imap.user.password", "IMAP Kodea");
	    lang.put("user.preferences.imap.folder", "IMAP karpeta");
	    lang.put("user.preferences.password.error", "Akatza: kodeak desberdinak dira");
	    lang.put("user.preferences.user.data", "Erabile Kontua");
	    lang.put("user.preferences.mail.data", "Nire Kontua");
	    lang.put("user.preferences.imap.error", "Posta eratzeko eremu guztiak beharrezkoak dira");
	    lang.put("user.preferences.imap.password.error.void", "IMAP kodea ezin daiteke hutsik izan sorreran");
	    lang.put("user.preferences.imap.test.error","Configuración de IMAP incorrecta");
	    lang.put("user.preferences.imap.test.ok","Configuración de IMAP correcta");

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Thesaurus-en kode hitza gehitu");
	    lang.put("thesaurus.tab.tree", "Ardatza");
	    lang.put("thesaurus.tab.keywords", "Kode Hitzak");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Maila gehitu");
	    lang.put("categories.folder.error.delete", "Ezin daiteke dokumentuen kategoria ezabatu");
	    
	    // Wizard
	    lang.put("wizard.document.uploading", "Asistente de documento");
	    
	    // User info
	    lang.put("user.info.chat.connect", "Conectar al chat");
	    lang.put("user.info.chat.disconnect", "Desconectar al chat");
	    lang.put("user.info.chat.new.room", "Nueva ventana");
	    lang.put("user.info.locked.actual", "Documentos bloqueados");
	    lang.put("user.info.checkout.actual", "Documentos editados");
	    lang.put("user.info.subscription.actual", "Suscripciones actuales");
	    lang.put("user.info.news.new", "Novedades");
	    lang.put("user.info.workflow.pending", "Workflows pendientes");
	    lang.put("user.info.user.quota", "Cuota utilizada");
	    
	    // Users online popup
	    lang.put("user.online", "Usuarios conectados");
	    
	    // Chat room
	    lang.put("chat.room", "Chat");
	    lang.put("chat.users.in.room", "Usuarios");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Ez daukazu baimenik dokumentura sartzeko");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Ezin da dokumentua blokeatu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Biltegiko barne errorea");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Dokumentuaren bidea ez da existitzen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Errorea bertsioan");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Ez daukazu baimenik dokumentu horretara sartzeko");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Ez da existitzen dokumenturik izen horrekon");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Badago dokumentu bat izen horrekon");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Ezin izan da dokumentua desblokeatu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Ezin izan da dokumentua desblokeatu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Biltegiko barne errorea");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Aplikazioaren barne errorea");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Dokumentuaren bidea ez da existitzen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_DatabaseException, "Error en la base de datos");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Ez daukazu baimenik karpetara sartzeko");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Ez dago karpetarik izen horrekin");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Badago karpeta bat izen horrekin");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Biltegiko barne akatza");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Aplikazioaren barne akatza");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Karpetaren bidea ez da existitzen");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_DatabaseException, "Error en la base de datos");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_AccessDenied, "Ez daukazu baimenik elementura sartzeko");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_ItemNotFound, "Ez dago elementurik izen horrekin");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_Repository, "Repositorioan barne akatza");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_General, "Aplikazioaren barne akatza");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_DatabaseException, "Error en la base de datos");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Ez dago dokumenturik izen horrekin");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Aplikazioaren barne akatza");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Jasanezindako fitxategi mota");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Ez dago dokumenturik izen horrekin");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Dokumentua handiegia da");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_DocumentNameMismatch, "El nombre del documento no coincide");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Saioa itxi egin da");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_DatabaseException, "Error en la base de datos");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Akatz orokorra kontsulta burutzan");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Gordetako kontsultaren izena bakarra izan behar da");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_DatabaseException, "Error en la base de datos");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Markatzailearen izena bakarra izan behar da");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Sesioa galdu da");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Biltegiko barne akatza");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_DatabaseException, "Error en la base de datos");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Biltegiko barne akatza");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "sartu/irten gailuan akatza");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Biltegiko barne akatza");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_DatabaseException, "Error en la base de datos");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Errorea nabigatzailearen konfigurazioan");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_QuotaExceed, "Error ha excedido la cuota de usuario, contacte con el administrador");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_AccessDenied, "No tiene permisos para acceder al correo");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemNotFound, "No existe un correo con ese nombre");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemExists, "Ya existe un correo con ese nombre");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_UnLock, "No se pudo desbloquear el correo");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Repository, "Error interno del repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_General, "Error interno de la aplicación");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_PathNotFound, "La ruta del correo no existe");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_DatabaseException, "Error en la base de datos");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_PathNotFound, "La ruta del no existe");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_AccessDenied, "No tiene permisos para acceder");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_Repository, "Error interno del repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_DatabaseException, "Error en la base de datos");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_General, "Error interno de la aplicación");   
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Lock, "Bloqueado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_PathNotFound, "La ruta no existe");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_AccessDenied, "No tiene permisos para acceder");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Repository, "Error interno del repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_DatabaseException, "Error en la base de datos");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Error interno de la aplicación");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchGroup, "No existe el grupo");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchProperty, "No existe la propiedad");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_IOException, "Error en el dispositivo de entrada/salida");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_Repository, "Error interno del repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_General, "Error interno de la aplicación");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_PathNotFound, "La ruta del no existe");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_DatabaseException, "Error en la base de datos");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_AccessDenied, "No tiene permisos para acceder");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_Repository, "Error interno del repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_General, "Error interno de la aplicación");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_DatabaseException, "Error en la base de datos");
	  }
}
