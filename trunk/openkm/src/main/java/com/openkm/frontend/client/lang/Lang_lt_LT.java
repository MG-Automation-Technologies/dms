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

import com.openkm.frontend.client.contants.service.ErrorCode;

/**
 * Lithuanian (Lithuania)
 * 
 * @author Andrius Ašmenskas
 */
public class Lang_lt_LT {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "yyy-MM-dd hh:mm:ss");
	    lang.put("general.day.pattern", "yyyy-MM-dd");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Užkraunama OpenKM");
	    lang.put("startup.starting.loading", "Pradedama užkrauti OpenKM");
	    lang.put("startup.taxonomy", "Gaunamas klasifikatoriaus šakninis elementas");
	    lang.put("startup.categories", "Gaunamas kategorijų šakninis elementas");
	    lang.put("startup.thesaurus", "Gaunamas žodyno šakninis elementas");
	    lang.put("startup.template", "Gaunamas šablonų šakninis elementas");
	    lang.put("startup.personal", "Gaunamas asmeninis šakninis elementas");
	    lang.put("startup.mail", "Gaunamas el. pašto šakninis elementas");
	    lang.put("startup.trash", "Gaunamas šiukšlynės šakninis elementas");
	    lang.put("startup.user.home", "Gaunamas vartotojo namų elementas");
	    lang.put("startup.bookmarks", "Gaunamos nuorodos");
	    lang.put("startup.loading.taxonomy", "Užkraunamas klasifikatorius");
	    lang.put("startup.loading.taxonomy.getting.folders", "Užkraunamas klasifikatorius - gaunami aplankai");
	    lang.put("startup.loading.taxonomy.eval.params", "Užkraunamas klasifikatorius - tikrinama naršyklė");
	    lang.put("startup.loading.taxonomy.open.path", "Užkraunamas klasifikatorius - atidaromas kelias");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Užkraunamas klasifikatorius - gaunami aplankai");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Užkraunamas klasifikatorius - gaunami dokumentai");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Užkraunamas klasifikatorius - gaunamas el. paštas");
	    lang.put("startup.loading.personal", "Užkraunami asmeniniai duomenys");
	    lang.put("startup.loading.mail", "Užkraunamas el. paštas");
	    lang.put("startup.loading.categories", "Užkraunamos kategorijos");
	    lang.put("startup.loading.thesaurus", "Užkraunamas žodynas");
	    lang.put("startup.loading.templates", "Užkraunami šablonai");
	    lang.put("startup.loading.trash", "Užkraunama šiukšlynė");
	    lang.put("startup.loading.history.search", "Užkraunama paieškos istorija");
	    lang.put("startup.loading.user.values", "Užkraunami vartotojo duomenys");
	    lang.put("startup.keep.alive", "Užkraunamas keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Galimas OpenKM atnaujinimas");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Klasifikatorius");
	    lang.put("leftpanel.label.trash", "Šiukšlynė");
	    lang.put("leftpanel.label.mail", "El. paštas");
	    lang.put("leftpanel.label.stored.search", "Išsaugotos paieškos");
	    lang.put("leftpanel.label.categories", "Kategorijos");
	    lang.put("leftpanel.label.thesaurus", "Žodynas");
	    lang.put("leftpanel.label.templates", "Šablonai");
	    lang.put("leftpanel.label.my.documents", "Mano dokumentai");
	    lang.put("leftpanel.label.user.search", "Vartotojo naujienos");
	    lang.put("leftpanel.label.all.repository", "Visa talpykla");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Sukurti aplanką");
	    lang.put("tree.menu.directory.remove", "Ištrinti");
	    lang.put("tree.menu.directory.rename", "Pervardinti");
	    lang.put("tree.menu.directory.refresh", "Atnaujinti");
	    lang.put("tree.menu.directory.move", "Perkelti");
	    lang.put("tree.menu.directory.copy", "Kopijuoti");
	    lang.put("tree.menu.directory.add.document", "Įkelti dokumentą");
	    lang.put("tree.menu.add.bookmark", "Pridėti nuorodą");
	    lang.put("tree.menu.set.home", "Nustatyti numatytą namų vietą");
	    lang.put("tree.menu.export", "Eksportuoti į failą");
	    lang.put("tree.status.refresh.folder", "Atnaujinamas aplanko medis");
	    lang.put("tree.status.refresh.create", "Sukuriamas aplankas");
	    lang.put("tree.status.refresh.delete", "Trinamas aplankas");
	    lang.put("tree.status.refresh.rename", "Pervardinamas aplankas");
	    lang.put("tree.status.refresh.restore", "Atstatomas aplankas");
	    lang.put("tree.status.refresh.purge", "Išvalomas aplankas");
	    lang.put("tree.status.refresh.get", "Atnaujinamas aplankas");
	    lang.put("tree.folder.new", "Naujas aplankas");
	    lang.put("tree.status.refresh.add.subscription", "Pridedama prenumerata");
	    lang.put("tree.status.refresh.remove.subscription", "Trinama prenumerata");
	    lang.put("tree.status.refresh.get.root", "Atnaujinamas pagrindinis aplankas");
	    lang.put("tree.status.refresh.get.keywords", "Atnaujinami raktiniai žodžiai");
	    lang.put("tree.status.refresh.get.user.home", "Gaunama vartotojo namų vieta");
	    lang.put("tree.status.refresh.purge.trash", "Išvaloma šiukšlynė");
	    lang.put("tree.menu.directory.find.folder","Surasti aplanką");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Atstatyti");
	    lang.put("trash.menu.directory.purge", "Išvalyti");
	    lang.put("trash.menu.directory.purge.trash", "Išvalyti šiukšlynę");
	    lang.put("trash.directory.select.label", "Pasirinkti paskirties aplanką");
	    
	    // General menu
	    lang.put("general.menu.file", "Failas");
	    	lang.put("general.menu.file.exit", "Išeiti iš programos");
	    	lang.put("general.menu.file.purge.trash", "Išvalyti šiukšlynę");
	    lang.put("general.menu.edit", "Redaguoti");
			lang.put("general.menu.file.create.directory", "Sukurti aplanką");
			lang.put("general.menu.file.download.document", "Atsisiųsti dokumentą");
			lang.put("general.menu.file.download.document.pdf", "Atsisiųsti dokumentą kaip PDF");
			lang.put("general.menu.file.send.link", "Siųsti dokumento nuorodą");
			lang.put("general.menu.file.send.attachment", "Siųsti dokumento priedą");
			lang.put("general.menu.file.lock", "Užrakinti");
			lang.put("general.menu.file.unlock", "Atrakinti");
			lang.put("general.menu.file.add.document", "Įkelti dokumentą");
			lang.put("general.menu.file.checkout", "Paimti patikrinimui");
			lang.put("general.menu.file.checkin", "Gražinti patikrintą");
			lang.put("general.menu.file.cancel.checkout", "Atšaukti patikrinimą");
			lang.put("general.menu.file.delete", "Ištrinti");
			lang.put("general.menu.file.refresh", "Atnaujinti");
			lang.put("general.menu.file.scanner", "Skeneris");
			lang.put("general.menu.file.uploader", "Failų įkėlimas");
	    lang.put("general.menu.tools", "Įrankiai");
	    	lang.put("general.menu.tools.languages", "Kalbos");
	    	lang.put("general.menu.tools.skin", "Išvaizda");
    			lang.put("general.menu.tools.skin.default", "Numatyta");
    			lang.put("general.menu.tools.skin.default2", "Numatyta 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Mažas šriftas");
    			lang.put("general.menu.tools.skin.bigfont", "Didelis šriftas");
    		lang.put("general.menu.debug.console", "Klaidų paieškos konsolė");
    		lang.put("general.menu.administration", "Rodyti administravimo įrankius");
    		lang.put("general.menu.tools.preferences", "Nustatymai");
    			lang.put("general.menu.tools.user.preferences", "Vartotojų nustatymai");
    	lang.put("general.menu.bookmark", "Nuorodos");
	    	lang.put("general.menu.bookmark.home", "Namai");
	    	lang.put("general.menu.bookmark.default.home", "Nustatyti numatytą namų vietą");
	    	lang.put("general.menu.bookmark.edit", "Redaguoti nuorodas");
	    lang.put("general.menu.help", "Pagalba");
		    lang.put("general.menu.bug.report", "Pranešimas apie klaidas");
		    lang.put("general.menu.support.request", "Pagalbos užsakymas");
		    lang.put("general.menu.public.forum", "Viešas forumas");
		    lang.put("general.menu.project.web", "Projekto žinatinklis");
		    lang.put("general.menu.version.changes", "Versijos pastabos");
		    lang.put("general.menu.documentation", "Dokumentacija");
		    lang.put("general.menu.about", "Apie OpenKM");
	    lang.put("general.connected", "Prisijungęs kaip");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Pavadinimas");
	    lang.put("filebrowser.date.update", "Atnaujinimo data");
	    lang.put("filebrowser.size", "Dydis");
	    lang.put("filebrowser.path", "Kelias");
	    lang.put("filebrowser.author", "Autorius");
	    lang.put("filebrowser.version", "Versija");
	    lang.put("filebrowser.menu.checkout", "Paimti patikrinimui");
	    lang.put("filebrowser.menu.checkin", "Gražinti patikrintą");
	    lang.put("filebrowser.menu.delete", "Ištrinti");
	    lang.put("filebrowser.menu.rename", "Pervardinti");
	    lang.put("filebrowser.menu.checkout.cancel", "Atšaukti patikrinimą");
	    lang.put("filebrowser.menu.lock", "Užrakinti");
	    lang.put("filebrowser.menu.unlock", "Atrakinti");
	    lang.put("filebrowser.menu.download", "Atsisiųsti");
	    lang.put("filebrowser.menu.move", "Perkelti");
	    lang.put("filebrowser.menu.copy", "Kopijuoti");
	    lang.put("filebrowser.menu.create.from.template", "Sukurti pagal šabloną");
	    lang.put("filebrowser.menu.add.property.group", "Pridėti savybių grupę");
	    lang.put("filebrowser.menu.remove.property.group", "Pašalinti savybių grupę");
	    lang.put("filebrowser.menu.start.workflow", "Vykdyti darbų eigą");
	    lang.put("filebrowser.menu.add.subscription", "Pridėti prenumeratą");
	    lang.put("filebrowser.menu.remove.subscription", "Šalinti prenumeratą");
	    lang.put("filebrowser.menu.add.bookmark", "Pridėti nuorodą");
	    lang.put("filebrowser.menu.set.home", "Nustatyti numatytą namų vietą");
	    lang.put("filebrowser.menu.refresh", "Atnaujinti");
	    lang.put("filebrowser.menu.export", "Eksportuoti į ZIP archyvą");
	    lang.put("filebrowser.menu.play", "Paleisti");
	    lang.put("filebrowser.menu.image.viewer", "Vaizdų peržiūra");
	    lang.put("filebrowser.status.refresh.folder", "Atnaujinamas aplankų sąrašas");
	    lang.put("filebrowser.status.refresh.document", "Atnaujinamas dokumentų sąrašas");
	    lang.put("filebrowser.status.refresh.mail", "Atnaujinamas pašto sąrašas");
	    lang.put("filebrowser.status.refresh.delete.folder", "Trinamas aplankas");
	    lang.put("filebrowser.status.refresh.delete.document", "Trinamas dokumentas");
	    lang.put("filebrowser.status.refresh.checkout", "Dokumentas imamas patikrinimui");
	    lang.put("filebrowser.status.refresh.lock", "Dokumentas užrakinamas");
	    lang.put("filebrowser.status.refresh.unlock", "Dokumentas atrakinamas");
	    lang.put("filebrowser.status.refresh.document.rename", "Dokumentas pervardinamas");
	    lang.put("filebrowser.status.refresh.folder.rename", "Aplankas pervardinamas");
	    lang.put("filebrowser.status.refresh.document.purge", "Dokumentas trinamas");
	    lang.put("filebrowser.status.refresh.folder.purge", "Aplankas trinamas");
	    lang.put("filebrowser.status.refresh.folder.get", "Aplankas atnaujinamas");
	    lang.put("filebrowser.status.refresh.document.get", "Dokumentas atnaujinamas");
	    lang.put("filebrowser.status.refresh.add.subscription", "Pridedama prenumerata");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Trinama prenumerata");
	    lang.put("filebrowser.status.refresh.get.user.home", "Gaunama vartotojo namų vieta");
	    lang.put("filebrowser.status.refresh.delete.mail", "Paštas trinamas");
	    lang.put("filebrowser.status.refresh.mail.purge", "Paštas šalinamas");
	    
	    // File Upload
	    lang.put("fileupload.send", "Išsaugoti");
	    lang.put("fileupload.status.sending", "Įkeliamas failas...");
	    lang.put("fileupload.status.indexing", "Indeksuojamas failą...");
	    lang.put("fileupload.status.ok", "Failas sėkmingai įkeltas");
	    lang.put("fileupload.upload.status", "Įkėlimo būsena");
	    lang.put("fileupload.upload.uploaded", "Įkeltas");
	    lang.put("fileupload.upload.completed", "Įkėlimas baigtas");
	    lang.put("fileupload.upload.runtime", "Darbo laikas");
	    lang.put("fileupload.upload.remaining", "Liko");
	    lang.put("fileupload.button.close", "Uždaryti");
	    lang.put("fileupload.button.add.other.file", "Pridėti dar vieną failą");
	    lang.put("fileupload.status.move.file", "Perkeliamas failas...");
	    lang.put("fileupload.status.move.mail", "Perkeliamas paštas...");
	    lang.put("fileupload.status.copy.file", "Kopijuojamas failas...");
	    lang.put("fileupload.status.copy.mail", "Kopijuojamas paštas...");
	    lang.put("fileupload.status.restore.file", "Atstatomas failas...");
	    lang.put("fileupload.status.restore.mail", "Atstatomas paštas...");
	    lang.put("fileupload.status.move.folder", "Perkeliamas aplankas...");
	    lang.put("fileupload.status.copy.folder", "Kopijuojamas aplankas...");
	    lang.put("fileupload.status.restore.folder", "Atstatomas aplankas...");
	    lang.put("fileupload.status.create.from.template", "Sukuriamas failas pagal šabloną...");
	    lang.put("fileupload.status.of", "iš");
	    lang.put("fileupload.label.insert", "Pridėti naujus dokumentus");
	    lang.put("fileupload.label.update", "Atnaujinti dokumentus");
	    lang.put("fileupload.label.users.notify", "Informuoti vartotojams");
	    lang.put("fileupload.label.comment", "Komentaras");
	    lang.put("fileupload.label.users.to.notify",  "Informuoti vartotojus");
	    lang.put("fileupload.label.users",  "Vartotojai");
	    lang.put("fileupload.label.groups.to.notify","Informuoti grupes");
	    lang.put("fileupload.label.groups","Grupės");
	    lang.put("fileupload.label.must.select.users",  "Turite pasirinkti vartotojus kuriuos norite informuoti");
	    lang.put("fileupload.label.importZip", "Importuoti dokumentus iš ZIP archyvo");
	    lang.put("fileupload.label.notify.comment", "Informavimo žinutė");
	    lang.put("fileupload.label.error.importing.zip", "Klaida importuojant failą");
	    lang.put("fileupload.label.error.move.file", "Klaida perkeliant failą");
	    lang.put("fileupload.label.error.move.mail", "Klaida perkeliant paštą");
	    lang.put("fileupload.label.error.copy.file", "Klaida kopijuojant failą");
	    lang.put("fileupload.label.error.copy.mail", "Klaida kopijuojant paštą");
	    lang.put("fileupload.label.error.restore.file", "Klaida atstatant failą");
	    lang.put("fileupload.label.error.restore.mail", "Klaida atstatant paštą");
	    lang.put("fileupload.label.error.move.folder", "Klaida perkeliant aplanką");
	    lang.put("fileupload.label.error.copy.folder", "Klaida kopijuojant aplanką");
	    lang.put("fileupload.label.error.restore.folder", "Klaida atstatant aplanką");
	    lang.put("fileupload.label.error.create.from.template", "Klaida kuriant failą pagal šabloną");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Negalima perkelti į šio aplanko pradinį aplanką arba aplanką esantį aplanko viduje");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Negalima perkelti į šio aplanko pradinį aplanką");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Negalima sukurti failo pradiniame aplanke");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Sąvybės");
	    lang.put("tab.document.notes", "Pastabos");
	    lang.put("tab.document.history", "Istorija");
	    lang.put("tab.document.status.history", "Atnaujinimo istorija");
	    lang.put("tab.status.security.role", "Atnaujinamos saugos rolės");
	    lang.put("tab.status.security.user", "Atnaujinami saugos vartotojai");
	    lang.put("tab.document.status.group.properties", "Atnaujinamos sąvybių grupės");
	    lang.put("tab.document.status.set.keywords", "Nustatomi raktiniai žodiai");
	    lang.put("tab.document.status.set.categories", "Atnaujinamos kategorijos");
	    lang.put("tab.document.status.get.version.history.size", "Atnaujinamas dokumento istorijos dydis");
	    lang.put("tab.document.status.purge.version.history", "Tankinama dokumento istorija");
	    lang.put("tab.document.status.restore.version", "Atstatoma dokumento versija");
	    lang.put("tab.document.security", "Sauga");
	    lang.put("tab.document.preview", "Peržiūra");
	    lang.put("tab.folder.properties", "Sąvybės");
	    lang.put("tab.folder.security", "Sauga");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Darbastalis");
	    lang.put("tab.workspace.search", "Paieška");
	    lang.put("tab.workspace.dashboard", "Įrankių skydelis");
	    lang.put("tab.workspace.administration", "Administravimas");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Pavadinimas");
	    lang.put("document.folder", "Aplankas");
	    lang.put("document.size", "Dydis");
	    lang.put("document.created", "Sukurtas");
	    lang.put("document.lastmodified", "Modifikuotas");
	    lang.put("document.mimetype", "Failo tipas");
	    lang.put("document.keywords", "Raktiniai žodžiai");
	    lang.put("document.by", "vartotojo");
	    lang.put("document.status", "Būklė");
	    lang.put("document.status.checkout", "Redagavo");
	    lang.put("document.status.locked", "Užrakino");
	    lang.put("document.status.normal", "Galimas");
	    lang.put("document.subscribed", "Prenumeruojamas");
	    lang.put("document.subscribed.yes", "Taip");
	    lang.put("document.subscribed.no", "Ne");
	    lang.put("document.history.size", "Istorijos dydis");
	    lang.put("document.subscribed.users", "Užsiprenumeravę vartotojai");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Įrašyti pastabą");
	    lang.put("document.keywords.cloud", "Raktiniai žodžiai");
	    lang.put("document.categories", "Kategorijos");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Pavadinimas");
	    lang.put("folder.parent", "Esantis");
	    lang.put("folder.created", "Sukurtas");
	    lang.put("folder.by", "vartotojo");
	    lang.put("folder.subscribed", "Prenumeruojamas");
	    lang.put("folder.subscribed.yes", "Taip");
	    lang.put("folder.subscribed.no", "Ne");
	    lang.put("folder.subscribed.users", "Užsiprenumeravę vartotojai");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Aplankai");
	    lang.put("folder.number.documents", "Dokumentai");
	    lang.put("folder.number.mails", "Paštai");
	    
	    // Version
	    lang.put("version.name", "Versija");
	    lang.put("version.created", "Data");
	    lang.put("version.author", "Autorius");
	    lang.put("version.size", "Dydis");
	    lang.put("version.purge.document", "Ištrinti istoriją");
	    lang.put("version.comment", "Komentaras");
	    
	    // Security
	    lang.put("security.label", "Sauga");
	    lang.put("security.group.name", "Grupė");
	    lang.put("security.group.permission.read", "Skaityti");
	    lang.put("security.group.permission.write", "Saugoti");
	    lang.put("security.group.permission.delete", "Trinti");
	    lang.put("security.group.permission.security", "Sauga");
	    lang.put("security.user.name", "Vartotojas");
	    lang.put("security.user.permission.read", "Skaityti");
	    lang.put("security.user.permission.write", "Saugoti");
	    lang.put("security.user.permission.delete", "Trinti");
	    lang.put("security.user.permission.security", "Sauga");
	    lang.put("security.users", "Vartotojai");
	    lang.put("security.groups", "Grupės");
	    lang.put("security.recursive", "Grįžtamų leidimų pakeitimai");
	    lang.put("secutiry.filter.by.users","Vartotojų filtras");
	    lang.put("secutiry.filter.by.groups","Grupių filtras");
	    lang.put("security.status.updating","Atnaujinami saugos nustatymai");
	    
	    // Preview
	    lang.put("preview.unavailable", "Peržiūra negalima");

	    // Mail
	    lang.put("mail.from", "Nuo");
	    lang.put("mail.reply", "Atsakyti");
	    lang.put("mail.to", "Kam");
	    lang.put("mail.subject", "Tema");
	    lang.put("mail.attachment", "Priedai");
	    
	    // Error
	    lang.put("error.label", "Sistema sugeneravo klaidą");
	    lang.put("error.invocation", "Klaida susijungiant su serveriu");
	    
	    // About
	    lang.put("about.label", "Apie OpenKM");
	    lang.put("about.loading", "Kraunama ...");
	    
	    // Logout
	    lang.put("logout.label", "Išeiti");
	    lang.put("logout.closed", "OpenKM uždaryta be klaidų.");
	    lang.put("logout.logout", "Išsiregistruojama iš OpenKM, prašome palaukti");
	    
	    // Confirm
	    lang.put("confirm.label", "Patvirtinimas");
	    lang.put("confirm.delete.folder", "Ar tikrai norite ištrinti aplanką ?");
	    lang.put("confirm.delete.document", "Ar tikrai norite ištrinti dokumentą ?");
	    lang.put("confirm.delete.trash", "Ar tikrai norite išvalyti šiukšlynę ?");
	    lang.put("confirm.purge.folder", "Ar tikrai norite ištrinti aplanką ?");
	    lang.put("confirm.purge.document", "Ar tikrai norite ištrinti dokumentą ?");
	    lang.put("confirm.delete.propety.group", "Ar tikrai norite ištrinti sąvybių grupę ?");
	    lang.put("confirm.purge.version.history.document", "Ar tikrai norite ištrinti dokumento istoriją ?");
	    lang.put("confirm.purge.restore.document", "Ar tikrai norite atstatyti šią dokumento versiją ?");
	    lang.put("confirm.set.default.home", "Ar tikrai norite nustatyti šią numatytą namų vietą ?");
	    lang.put("confirm.delete.saved.search", "Ar tikrai norite ištrinti išsaugotą paiešką ?");
	    lang.put("confirm.delete.user.news", "Ar tikrai norite ištrinti vartotojo naujienas ?");
	    lang.put("confirm.delete.mail", "Ar tikrai norite ištrinti paštą ?");
	    lang.put("confirm.get.pooled.workflow.task", "Ar tikrai norite priskirti šią užduotį sau ?");
	    lang.put("confirm.force.unlock", "Ar tikrai norite priverstinai atrakinti dokumentą ?");
	    lang.put("confirm.force.cancel.checkout", "Ar tikrai norite priverstinai atšaukti dokumento redagavimą ?");
	    
	    // Search inputs
	    lang.put("search.context", "Vieta");
	    lang.put("search.content", "Tekstas dokumente");
	    lang.put("search.name", "Pavadinimas");
	    lang.put("search.keywords", "Raktiniai žodžiai");
	    lang.put("search.folder", "Aplankas");
	    lang.put("search.category", "Kategorija");
	    lang.put("search.results", "Rezultatai");
	    lang.put("search.to", "kam");
	    lang.put("search.page.results", "Puslapių rezultatai");
	    lang.put("search.add.property.group", "Pridėti savybių grupę");
	    lang.put("search.mimetype", "Failo tipas");
	    lang.put("search.type", "Tipas");
	    lang.put("search.type.document", "Dokumentas");
	    lang.put("search.type.folder", "Aplankas");
	    lang.put("search.type.mail", "Paštas");
	    lang.put("search.advanced", "Išplėstinė paieška");
	    lang.put("search.user", "Vartotojas");
	    lang.put("search.date.and", "iki");
	    lang.put("search.date.range", "Datos intervalas nuo");
	    lang.put("search.save.as.news", "Išsaugoti kaip vartotojo naujienas");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filtruoti pagal aplankus");
	    lang.put("search.category.filter", "Filtruoti pagal kategorijas");
	    
	    // Search results
	    lang.put("search.result.name", "Pavadinimas");
	    lang.put("search.result.score", "Aktualumas");
	    lang.put("search.result.size", "Dydis");
	    lang.put("search.result.date.update", "Atnaujinti datą");
	    lang.put("search.result.author", "Autorius");
	    lang.put("search.result.version", "Versija");
	    lang.put("search.result.path", "Kelias");
	    lang.put("search.result.menu.download", "Atsisiųsti");
	    lang.put("search.result.menu.go.folder", "Pereiti į šį aplanką");
	    lang.put("search.result.menu.go.document", "Pereiti prie šio dokumento");
	    lang.put("search.result.status.findPaginated", "Atnaujinama paieška");
	    lang.put("search.result.status.runsearch", "Atnaujinama išsaugota paieška");
	    
	    // Search saved
	    lang.put("search.saved.run", "Vykdyti");
	    lang.put("search.saved.delete", "Trinti");
	    lang.put("search.saved.status.getsearchs", "Atnaujinamos išsaugotos paieškos");
	    lang.put("search.saved.status.savesearch", "Atnaujinamos išsaugotos paieškos");
	    lang.put("search.saved.status.deletesearch", "Trinamos išsaugota paieška");
	    lang.put("search.saved.status.getusernews", "Atnaujinamos vartotojo naujienos");
	    
	    // Button
	    lang.put("button.close", "Uždaryti");
	    lang.put("button.logout", "Išsiregistruoti");
	    lang.put("button.update", "Atnaujinti");
	    lang.put("button.cancel", "Atšaukti");
	    lang.put("button.accept", "Priimti");
	    lang.put("button.restore", "Atstatyti");
	    lang.put("button.move", "Perkelti");
	    lang.put("button.change", "Pakeisti");
	    lang.put("button.search", "Ieškoti");
	    lang.put("button.save.search", "Išsaugoti paiešką");
	    lang.put("button.view", "Peržiūra");
	    lang.put("button.clean", "Išvalyti");
	    lang.put("button.add", "Pridėti");
	    lang.put("button.delete", "Trinti");
	    lang.put("button.copy", "Kopijuoti");
	    lang.put("button.create", "Sukurti");
	    lang.put("button.show", "Rodyti");
	    lang.put("button.memory", "Atnaujinti");
	    lang.put("button.copy.clipboard", "Kopijuoti į atmintį");	
	    lang.put("button.start", "Pradėti");
	    lang.put("button.select", "Pasirinkti");
	    lang.put("button.test", "Testas");
	    lang.put("button.next", "Kitas");
	    
	    // Group
	    lang.put("group.label", "Pridėti sąvybių grupę");
	    lang.put("group.group", "Grupė");
	    lang.put("group.property.group", "Sąvybė");lang.put("general.menu.file.scanner", "Skeneris");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Pridėti nuorodą");
	    lang.put("bookmark.name", "Pavadinimas");
	    lang.put("bookmark.edit.label", "Redaguoti nuorodas");
	    lang.put("bookmark.path", "Kelias");
	    lang.put("bookmark.type", "Tipas");
	    lang.put("bookmark.type.document", "Dokumentas");
	    lang.put("bookmark.type.folder", "Aplankas");
	    
	    // Notify
	    lang.put("notify.label", "Siunčiama dokumento nuoroda");
	    lang.put("notify.label.attachment", "Siunčiamas dokumento priedas");
	    
	    // Status
	    lang.put("status.document.copied", "Dokumentas pažymėtas kopijavimui");
	    lang.put("status.document.cut", "Dokumentas pažymėtas iškirpimui");
	    lang.put("status.folder.copied", "Aplankas pažymėtas kopijavimui");
	    lang.put("status.folder.cut", "Aplankas pažymėtas iškirpimui");
	    lang.put("status.keep.alive.error", "Nustatytas ryšio su serveriu praradimas");
	    lang.put("status.debug.enabled", "Klaidų paieškos režimas įjungtas");
	    lang.put("status.debug.disabled", "Klaidų paieškos režimas išjungtas");
	    lang.put("status.network.error.detected", "Nustatyta tinklo ryšio klaida");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Sekmadienis");
	    lang.put("calendar.day.monday", "Pirmadienis");
	    lang.put("calendar.day.tuesday", "Antradienis");
	    lang.put("calendar.day.wednesday", "Trečiadienis");
	    lang.put("calendar.day.thursday", "Ketvirtadienis");
	    lang.put("calendar.day.friday", "Penktadienis");
	    lang.put("calendar.day.saturday", "Šeštadienis");
	    lang.put("calendar.month.january", "Sausis");
	    lang.put("calendar.month.february", "Vasaris");
	    lang.put("calendar.month.march", "Kovas");
	    lang.put("calendar.month.april", "Balandis");
	    lang.put("calendar.month.may", "Gegužė");
	    lang.put("calendar.month.june", "Birželis");
	    lang.put("calendar.month.july", "Liepa");
	    lang.put("calendar.month.august", "Rugpjūtis");
	    lang.put("calendar.month.september", "Rugsėjis");
	    lang.put("calendar.month.october", "Spalis");
	    lang.put("calendar.month.november", "Lapkritis");
	    lang.put("calendar.month.december", "Gruodis");
	    
	    // Media player
	    lang.put("media.player.label", "Garso ir vaizdo rodymas");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Paveikslų peržiūra");
	    lang.put("image.viewer.zoom.in", "Didinti");
	    lang.put("image.viewer.zoom.out", "Mažinti");
	    
	    // Debug console
	    lang.put("debug.console.label", "Klaidų paieškos konsolė");
	    lang.put("debug.enable.disable", "CTRL+Z kad ijungti/išjungti klaidų paieškos konsolę");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Bendra");
	    lang.put("dashboard.tab.news", "Naujienos");
	    lang.put("dashboard.tab.user", "Vartotojas");
	    lang.put("dashboard.tab.workflow", "Darbų eiga");
	    lang.put("dashboard.tab.mail", "Paštas");
	    lang.put("dashboard.tab.keymap", "Raktiniai žodžiai");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Naujas");
	    lang.put("dashboard.user.locked.documents", "Užrakinti dokumentai");
	    lang.put("dashboard.user.checkout.documents", "Paimti patikrinimui dokumentai");
	    lang.put("dashboard.user.last.modified.documents", "Vėliausiai redaguoti dokumentai");
	    lang.put("dashboard.user.last.downloaded.documents", "Vėliausiai atsisiųsti dokumentai");
	    lang.put("dashboard.user.subscribed.documents", "Prenumeruojami dokumentai");
	    lang.put("dashboard.user.subscribed.folders", "Prenumeruojami aplankai");
	    lang.put("dashboard.user.last.uploaded.documents", "Vėliausiai įkelti dokumentai");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Praeitą sąvaitę dažniausiai žiūrėti dokumentai");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Praeitą mėnesį dažniausiai žiūrėti dokumentai");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Praeitą sąvaitę dažniausiai redaguoti dokumentai");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Praeitą mėnesį dažniausiai redaguoti dokumentai");
	    lang.put("dashboard.general.last.uploaded.documents", "Vėliausiai įkelti dokumentai");
	    lang.put("dashboard.workflow.pending.tasks", "Laukiančios užduotys");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Nepriskirtos užduotys");
	    lang.put("dashboard.workflow.task", "Užduotis");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Pavadinimas");
	    lang.put("dashboard.workflow.task.created", "Sukūrimo data");
	    lang.put("dashboard.workflow.task.start", "Pradžios data");
	    lang.put("dashboard.workflow.task.duedate", "Data iki");
	    lang.put("dashboard.workflow.task.end", "Pabaigos data");
	    lang.put("dashboard.workflow.task.description", "Aprašymas");
	    lang.put("dashboard.workflow.task.process.instance", "Proceso rekalavimas");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Versija");
	    lang.put("dashboard.workflow.task.process.name", "Pavadinimas");
	    lang.put("dashboard.workflow.task.process.description", "Aprašymas");
	    lang.put("dashboard.workflow.task.process.data", "Duomenys");
	    lang.put("dashboard.workflow.comments", "Komentarai");
	    lang.put("dashboard.workflow.task.process.forms", "Nuo");
	    lang.put("dashboard.workflow.add.comment","Pridėti komentarą");
	    lang.put("dashboard.workflow.task.process.definition", "Proceso aprašymas");
	    lang.put("dashboard.workflow.task.process.path", "Kelias");
	    lang.put("dashboard.refreshing", "Atnaujinama");
	    lang.put("dashboard.keyword", "Raktiniai žodžiai");
	    lang.put("dashboard.keyword.suggest", "Įveskite raktinį žodį");
	    lang.put("dashboard.keyword.all", "Visi raktiniai žodžiai");
	    lang.put("dashboard.keyword.top", "Dažniausi raktiniai žodžiai");
	    lang.put("dashboard.keyword.related", "Susiję raktiniai žodžiai");
	    lang.put("dashboard.keyword.goto.document", "Eiti prie dokumento");
	    lang.put("dashboard.keyword.clean.keywords", "Išvalyti raktinius žodžius");
	    lang.put("dashboard.mail.last.imported.mails", "Elektroniniai laiškai");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Priedai");
	    
	    // Workflow
	    lang.put("workflow.label", "Pradėti darbų eigą");
	    
	    // User configuration
	    lang.put("user.preferences.label", "Vartotojų nustatymai");
	    lang.put("user.preferences.user", "Vartotojas");
	    lang.put("user.preferences.password", "Slaptažodis");
	    lang.put("user.preferences.mail", "El. paštas");
	    lang.put("user.preferences.roles","Rolės");
	    lang.put("user.preferences.imap.host", "IMAP serveris");
	    lang.put("user.preferences.imap.user", "IMAP vartotojo vardas");
	    lang.put("user.preferences.imap.user.password", "IMAP slaptažodis");
	    lang.put("user.preferences.imap.folder", "IMAP aplankas");
	    lang.put("user.preferences.password.error", "Klaida: skiriasi slaptažodžiai");
	    lang.put("user.preferences.user.data", "Vartotojo sąskaita");
	    lang.put("user.preferences.mail.data", "Pašto sąskaita");
	    lang.put("user.preferences.imap.error", "Norint nustatyti paštą, visi laukai privalomi");
	    lang.put("user.preferences.imap.password.error.void", "Kuriant IMAP paštą, slaptažodis negali būti tuščias");
	    lang.put("user.preferences.imap.test.error","IMAP konfigūracijos klaida");
	    lang.put("user.preferences.imap.test.ok","IMAP sukonfigūruotas teisingai");

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Pridėti raktinius žodžius į žodyną");
	    lang.put("thesaurus.tab.tree", "Medis");
	    lang.put("thesaurus.tab.keywords", "Raktiniai žodžiai");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Pridėti kategoriją");
	    lang.put("categories.folder.error.delete", "Kategorijos trynimas negalimas kol joje yra priskirta dokumentų");
	    
	    // Wizard
	    lang.put("wizard.document.uploading", "Dokumentų vedlys");
	    
	    // User info
	    lang.put("user.info.chat.connect", "Prisijungti prie pokalbių");
	    lang.put("user.info.chat.disconnect", "Atsijungti nuo pokalbių");
	    lang.put("user.info.chat.new.room", "Tinklo pokalbių kambarys");
	    lang.put("user.info.locked.actual", "Užrakinti dokumentai");
	    lang.put("user.info.checkout.actual", "Paimti patikrinimui dokumentai");
	    lang.put("user.info.subscription.actual", "Galiojančios prenumeratos");
	    lang.put("user.info.news.new", "Naujienos");
	    lang.put("user.info.workflow.pending", "Laukiančios darbų eigos");
	    lang.put("user.info.user.quota", "Panaudota talpa");
	    
	    // Users online popup
	    lang.put("user.online", "Prisijungę vartotojai");
	    
	    // Chat room
	    lang.put("chat.room", "Pokalbiai");
	    lang.put("chat.users.in.room", "Vartotojai");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Document access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Document lock denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Version error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Document access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Document not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Document already exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Document lock denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Document unlock desired");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Database, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Folder access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Folder not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Folder already exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Folder path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Database, "Database error");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_AccessDenied, "Item access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_ItemNotFound, "Item not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_General, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_Database, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Document not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Repository internal error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Unsupported file format");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Document already exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Document size exceeded");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_DocumentNameMismatch, "Document name is diferent");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Session closed");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Database, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Generic error executing query");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "The stored search name must be unique");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Database, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "The bookmark name must be unique");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Session lost");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Database, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IO, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Database, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error in browser configuration");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_QuotaExceed, "Error user quota exceed, contact with adminitrator");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_AccessDenied, "Document access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemNotFound, "Document not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemExists, "Document already exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Lock, "Document lock denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Database, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_PathNotFound, "Path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_AccessDenied, "Access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_Database, "Database error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_General, "Application internal error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Lock, "Locked");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_PathNotFound, "Path not exist");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_AccessDenied, "Access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Database, "Database error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchGroup, "Group not exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchProperty, "Property not exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_IO, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_PathNotFound, "Path not exist");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_Database, "Database error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_AccessDenied, "Access denied");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_Database, "Database error");
	  }
}
