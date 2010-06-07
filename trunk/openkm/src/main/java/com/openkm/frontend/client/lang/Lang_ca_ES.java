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
 * Catalan (Spain)
 * 
 * @author Josep Llort
 */
public class Lang_ca_ES {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd/MM/yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd/MM/yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Carregant OpenKM");
	    lang.put("startup.starting.loading", "Començant a carregar OpenKM");
	    lang.put("startup.taxonomy", "Obtenint el node principal de la taxonomia");
	    lang.put("startup.categories", "Obtenint el node principal de les categories");
	    lang.put("startup.thesaurus", "Obtenint el node principal del thesaurus");
	    lang.put("startup.template", "Obtenint el node principal de les plantilles");
	    lang.put("startup.personal", "Obtenint el node principal del personal");
	    lang.put("startup.mail", "Obtenint el node principal del correu electrònic");
	    lang.put("startup.trash", "Obtenint el node principal de la paperera");
	    lang.put("startup.user.home", "Obtenint l'inici de l'usuari");
	    lang.put("startup.bookmarks", "Obtenint enllaços");
	    lang.put("startup.loading.taxonomy", "Carregant la taxonomia");
	    lang.put("startup.loading.taxonomy.getting.folders", "Carregant la taxonomia - obtenint carpetes");
	    lang.put("startup.loading.taxonomy.eval.params", "Carregant la taxonomia - avaluant pas de parametres");
	    lang.put("startup.loading.taxonomy.open.path", "Carregant la taxonomia - obrint la ruta");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Carregant la taxonomia - obtenint carpetes");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Carregant la taxonomia - obtenint documents");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Carregant la taxonomia - obtenint correus electrònics");
	    lang.put("startup.loading.personal", "Carregant personals");
	    lang.put("startup.loading.mail", "Carregant e-mails");	
	    lang.put("startup.loading.categories", "Carregant categories");
	    lang.put("startup.loading.thesaurus", "Carregant thesaurus");
	    lang.put("startup.loading.templates", "Carregant plantilles");
	    lang.put("startup.loading.trash", "Carregant paperera");
	    lang.put("startup.loading.history.search", "Carregant històric de cerques");
	    lang.put("startup.loading.user.values", "Carregant dades de l'usuari");
	    lang.put("startup.keep.alive", "Carregant keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Actualització disponible de OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Taxonomia");
	    lang.put("leftpanel.label.trash", "Paperera");
	    lang.put("leftpanel.label.mail", "Correu electrònic");
	    lang.put("leftpanel.label.stored.search", "Cerques emmagatzemades");
	    lang.put("leftpanel.label.categories", "Categories");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Plantilles");
	    lang.put("leftpanel.label.my.documents", "Documents personals");
	    lang.put("leftpanel.label.user.search", "Novetats d'usuari");
	    lang.put("leftpanel.label.all.repository", "En tot el repositori");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Crea una carpeta");
	    lang.put("tree.menu.directory.remove", "Elimina");
	    lang.put("tree.menu.directory.rename", "Renombra");
	    lang.put("tree.menu.directory.refresh", "Refresca");
	    lang.put("tree.menu.directory.move", "Mou");
	    lang.put("tree.menu.directory.copy", "Copia");
	    lang.put("tree.menu.directory.add.document", "Afegeix un fitxer");
	    lang.put("tree.menu.add.bookmark", "Afegeix un enllaç");
	    lang.put("tree.menu.set.home", "Estableix inici");
	    lang.put("tree.menu.export", "Exporta a un fitxer");
	    lang.put("tree.status.refresh.folder", "S'està refrescant l'arbre de carpetes");
	    lang.put("tree.status.refresh.create", "S'està creant la carpeta");
	    lang.put("tree.status.refresh.delete", "S'està eliminant la carpeta");
	    lang.put("tree.status.refresh.rename", "S'està renombrant la carpeta");
	    lang.put("tree.status.refresh.restore", "S'està restaurant la carpeta");
	    lang.put("tree.status.refresh.purge", "S'està eliminant la carpeta");
	    lang.put("tree.status.refresh.get", "S'està refrescant la carpeta");
	    lang.put("tree.folder.new", "Nova carpeta");
	    lang.put("tree.status.refresh.add.subscription", "S'està afegint la subscripció");
	    lang.put("tree.status.refresh.remove.subscription", "S'està eliminant la subscripció");
	    lang.put("tree.status.refresh.get.root", "S'està refrescant la carpeta inicial");
	    lang.put("tree.status.refresh.get.keywords", "S'estan refrescant les paraules clau");
	    lang.put("tree.status.refresh.get.user.home", "S'està obtenint l'inici de l'usuari");
	    lang.put("tree.status.refresh.purge.trash", "S'està buidant la paperera");
	    lang.put("tree.menu.directory.find.folder","Cercar carpeta");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Restaura");
	    lang.put("trash.menu.directory.purge", "Elimina");
	    lang.put("trash.menu.directory.purge.trash", "Buida la paperera");
	    lang.put("trash.directory.select.label", "Seleccioneu la carpeta destí");
	    
	    // General menu
	    lang.put("general.menu.file", "Fitxer");
	    	lang.put("general.menu.file.exit", "Surt");
	    	lang.put("general.menu.file.purge.trash", "Buida paperera");
	    lang.put("general.menu.edit", "Edició");
	    	lang.put("general.menu.file.create.directory", "Crea una carpeta");
	    	lang.put("general.menu.file.download.document", "Descarrega document");
	    	lang.put("general.menu.file.download.document.pdf", "Descarrega document com PDF");
	    	lang.put("general.menu.file.send.link", "Enviar enllaç del document");
	    	lang.put("general.menu.file.lock", "Bloqueja");
	    	lang.put("general.menu.file.unlock", "Desbloqueja");
	    	lang.put("general.menu.file.add.document", "Afegeix un fitxer");
	    	lang.put("general.menu.file.checkout", "Checkout");
	    	lang.put("general.menu.file.checkin", "Checkin");
	    	lang.put("general.menu.file.cancel.checkout", "Cancel·la checkout");
	    	lang.put("general.menu.file.delete", "Elimina");
	    	lang.put("general.menu.file.refresh", "Refresca");
	    	lang.put("general.menu.file.scanner", "Scanner");
	    	lang.put("general.menu.file.uploader", "Inserció de documents");
	    lang.put("general.menu.tools", "Eines");
	    	lang.put("general.menu.tools.languages", "Idiomes");
	    	lang.put("general.menu.tools.skin", "Aparença");
	    		lang.put("general.menu.tools.skin.default", "Per defecte");
	    		lang.put("general.menu.tools.skin.default2", "Per defecte 2");
	    		lang.put("general.menu.tools.skin.mediumfont", "Font mitjana");
	    		lang.put("general.menu.tools.skin.bigfont", "Font gran");
	    	lang.put("general.menu.debug.console", "Consola de depuració");
	    	lang.put("general.menu.administration", "Mostra administració");
	    	lang.put("general.menu.tools.preferences", "Preferències");
	    		lang.put("general.menu.tools.user.preferences", "Configuració de l'usuari");
	    lang.put("general.menu.bookmark", "Enllaços d'interès");
	    	lang.put("general.menu.bookmark.home", "Inici");
	    	lang.put("general.menu.bookmark.default.home", "Estableix inici per defecte");
	    	lang.put("general.menu.bookmark.edit", "Editar adreces d'interès");
	    lang.put("general.menu.help", "Ajuda");
		    lang.put("general.menu.bug.report", "Report de bugs");
		    lang.put("general.menu.support.request", "Sol·licitud d'ajuda");
		    lang.put("general.menu.public.forum", "Fòrum públic");
		    lang.put("general.menu.project.web", "Web del projecte");
		    lang.put("general.menu.version.changes", "Notes de la versió");
		    lang.put("general.menu.documentation", "Documentació");
		    lang.put("general.menu.about", "Sobre OpenKM");
	    lang.put("general.connected", "Connectat com a");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Nom");
	    lang.put("filebrowser.date.update", "Data de modificació");
	    lang.put("filebrowser.size", "Grandària");
	    lang.put("filebrowser.path", "Ruta");
	    lang.put("filebrowser.author", "Autor");
	    lang.put("filebrowser.version", "Versió");
	    lang.put("filebrowser.menu.checkout", "Check-out");
	    lang.put("filebrowser.menu.checkin", "Check-in");
	    lang.put("filebrowser.menu.delete", "Elimina");
	    lang.put("filebrowser.menu.rename", "Renombra");
	    lang.put("filebrowser.menu.checkout.cancel", "Cancela check-out");
	    lang.put("filebrowser.menu.lock", "Bloqueja");
	    lang.put("filebrowser.menu.unlock", "Desbloqueja");
	    lang.put("filebrowser.menu.download", "Descarrega");
	    lang.put("filebrowser.menu.move", "Mou");
	    lang.put("filebrowser.menu.copy", "Copia");
	    lang.put("filebrowser.menu.create.from.template", "Crea des de la plantilla");
	    lang.put("filebrowser.menu.add.property.group", "Afegeix grup de propietats");
	    lang.put("filebrowser.menu.remove.property.group", "Elimina grup de propietats");
	    lang.put("filebrowser.menu.start.workflow", "Iniciar workflow");
	    lang.put("filebrowser.menu.add.subscription", "Afegeix subscripció");
	    lang.put("filebrowser.menu.remove.subscription", "Elimina subscripció");
	    lang.put("filebrowser.menu.add.bookmark", "Afegeix un enllaç");
	    lang.put("filebrowser.menu.set.home", "Estableix com a inici");
	    lang.put("filebrowser.menu.refresh", "Refresca");
	    lang.put("filebrowser.menu.export", "Exporta a un ZIP");
	    lang.put("filebrowser.menu.play", "Reproduir");
	    lang.put("filebrowser.menu.image.viewer", "Visor d'imatges");
	    lang.put("filebrowser.status.refresh.folder", "S'està refrescant la llista carpetas");
	    lang.put("filebrowser.status.refresh.document", "S'està refrescant la llista de documents");
	    lang.put("filebrowser.status.refresh.mail", "S'està refrescant la llista de correus electrònics");
	    lang.put("filebrowser.status.refresh.delete.folder", "S'està eliminant la carpeta");
	    lang.put("filebrowser.status.refresh.delete.document", "Eliminant");
	    lang.put("filebrowser.status.refresh.checkout", "Efectuant checkout");
	    lang.put("filebrowser.status.refresh.lock", "S'està efectuant el bloqueig");
	    lang.put("filebrowser.status.refresh.unlock", "S'està eliminant el bloqueig");
	    lang.put("filebrowser.status.refresh.document.rename", "S'està renombrant el document");
	    lang.put("filebrowser.status.refresh.folder.rename", "S'està renombrant la carpeta");
	    lang.put("filebrowser.status.refresh.document.purge", "S'està eliminant el document");
	    lang.put("filebrowser.status.refresh.folder.purge", "S'està eliminant la carpeta");
	    lang.put("filebrowser.status.refresh.folder.get", "S'està refrescant la carpeta");
	    lang.put("filebrowser.status.refresh.document.get", "S'està refrescant el document");
	    lang.put("filebrowser.status.refresh.add.subscription", "S'està afegint la subscripció");
	    lang.put("filebrowser.status.refresh.remove.subscription", "S'està eliminant la subscripció");
	    lang.put("filebrowser.status.refresh.get.user.home", "S'està obtenint l'inici");
	    lang.put("filebrowser.status.refresh.delete.mail", "Eliminant");
	    lang.put("filebrowser.status.refresh.mail.purge", "S'està eliminant el correu electrònic");
	    
	    // File Upload
	    lang.put("fileupload.send", "Envia");
	    lang.put("fileupload.status.sending", "S'està enviant el fitxer...");
	    lang.put("fileupload.status.indexing", "S'està indexant el fitxer...");
	    lang.put("fileupload.status.ok", "El fitxer s'ha enviat correctament");
	    lang.put("fileupload.upload.status", "Estat enviament");
	    lang.put("fileupload.upload.uploaded", "Completat");
	    lang.put("fileupload.upload.completed", "L'enviament s'ha completat");
	    lang.put("fileupload.upload.runtime", "Temps acumulat");
	    lang.put("fileupload.upload.remaining", "Falta");
	    lang.put("fileupload.button.close", "Tanca");
	    lang.put("fileupload.button.add.other.file", "Afegeix un altre fitxer");
	    lang.put("fileupload.status.move.file", "S'està movent el fitxer...");
	    lang.put("fileupload.status.move.mail", "S'està movent el correu electrònic...");
	    lang.put("fileupload.status.copy.file", "S'està copiant el fitxer...");
	    lang.put("fileupload.status.copy.mail", "S'està copiant el correu electrònic...");
	    lang.put("fileupload.status.restore.file", "S'està restaurant el fitxer...");
	    lang.put("fileupload.status.restore.mail", "S'està restaurant el correu electrònic...");
	    lang.put("fileupload.status.move.folder", "S'està movent la carpeta...");
	    lang.put("fileupload.status.copy.folder", "S'està copiant la carpeta...");
	    lang.put("fileupload.status.restore.folder", "S'està restaurant la carpeta...");
	    lang.put("fileupload.status.create.from.template", "S'està creant el fitxer des de la plantilla...");
	    lang.put("fileupload.status.of", "de");
	    lang.put("fileupload.label.insert", "Inserció de documents");
	    lang.put("fileupload.label.update", "Actualizació de documents");
	    lang.put("fileupload.label.users.notify", "Notifica als usuaris");
	    lang.put("fileupload.label.comment", "Comentari");
	    lang.put("fileupload.label.users.to.notify",  "Usuaris a notificar");
	    lang.put("fileupload.label.users",  "Usuaris");
	    lang.put("fileupload.label.groups.to.notify","Grups a notificar");
	    lang.put("fileupload.label.groups","Grups");
	    lang.put("fileupload.label.must.select.users",  "Heu de sel·leccionar al manco un usuari a notificar");
	    lang.put("fileupload.label.importZip", "Importa documents desde ZIP");
	    lang.put("fileupload.label.notify.comment", "Missatge de notificació");
	    lang.put("fileupload.label.error.importing.zip", "S'ha produït un error a l'importar el fitxer");
	    lang.put("fileupload.label.error.move.file", "S'ha produït un error movent el fitxer");
	    lang.put("fileupload.label.error.move.mail", "S'ha produït un error movent el correu electrónic");
	    lang.put("fileupload.label.error.copy.file", "S'ha produït un error copiant el fitxer");
	    lang.put("fileupload.label.error.copy.mail", "S'ha produït un error copiant el correu electrònic");
	    lang.put("fileupload.label.error.restore.file", "S'ha produït un error restaurant el fitxer");
	    lang.put("fileupload.label.error.restore.mail", "S'ha produït un error restaurant el correu electrònic");
	    lang.put("fileupload.label.error.move.folder", "S'ha produït un error movent la carpeta");
	    lang.put("fileupload.label.error.copy.folder", "S'ha produït un error copiant la carpeta");
	    lang.put("fileupload.label.error.restore.folder", "S'ha produït un error restaurant la carpeta");
	    lang.put("fileupload.label.error.create.from.template", "S'ha produït un error creant el fitxer des de la plantilla");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "No es pot moure dins la carpeta origen o dins una filla");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "No es pot copiar dins la carpeta origen");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "No es pot crear un fitxer dins la carpeta origen");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Propietats");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "Historial");
	    lang.put("tab.document.status.history", "S'està refrescant historial");
	    lang.put("tab.status.security.role", "S'està refrescant la seguretat dels rols");
	    lang.put("tab.status.security.user", "S'està refrescant la seguretat dels usuaris");
	    lang.put("tab.document.status.group.properties", "S'està refrescant el grup de propietats");
	    lang.put("tab.document.status.set.keywords", "S'estan establint les paraules clau");
	    lang.put("tab.document.status.set.categories", "S'estan actualizant les categories");
	    lang.put("tab.document.status.get.version.history.size", "S'està refrescant el tamany de l'històric del document");
	    lang.put("tab.document.status.purge.version.history", "S'està compactant l'històric del document");
	    lang.put("tab.document.status.restore.version", "S'està restaurant la versió del document");
	    lang.put("tab.document.security", "Seguretat");
	    lang.put("tab.document.preview", "Previsualitzar");
	    lang.put("tab.folder.properties", "Propietats");
	    lang.put("tab.folder.security", "Seguretat");
	    	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Escriptori");
	    lang.put("tab.workspace.search", "Cercador");
	    lang.put("tab.workspace.dashboard", "Taulell");
	    lang.put("tab.workspace.administration", "Administració");
	    
	    // Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Nom");
	    lang.put("document.folder", "Carpeta");
	    lang.put("document.size", "Grandària");
	    lang.put("document.created", "Creat");
	    lang.put("document.lastmodified", "Modificat");
	    lang.put("document.mimetype", "Tipus MIME");
	    lang.put("document.keywords", "Paraules clau");
	    lang.put("document.by", "per");
	    lang.put("document.status", "Estat");
	    lang.put("document.status.checkout", "Editat per");
	    lang.put("document.status.locked", "Bloquejat per");
	    lang.put("document.status.normal", "Disponible");
	    lang.put("document.subscribed", "Subscrit");
	    lang.put("document.subscribed.yes", "Si");
	    lang.put("document.subscribed.no", "No");
	    lang.put("document.history.size", "Grandària de l'històric");
	    lang.put("document.subscribed.users", "Usuaris subscrits");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Afegir nota");
	    lang.put("document.keywords.cloud", "Núvol de paraules clau");
	    lang.put("document.categories", "Categories");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nom");
	    lang.put("folder.parent", "Pare");
	    lang.put("folder.created", "Creat");
	    lang.put("folder.by", "per");
	    lang.put("folder.subscribed", "Subscrit");
	    lang.put("folder.subscribed.yes", "Si");
	    lang.put("folder.subscribed.no", "No");
	    lang.put("folder.subscribed.users", "Usuaris subscrits");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Carpetes");
	    lang.put("folder.number.documents", "Documents");
	    lang.put("folder.number.mails", "Correu electrònics");
	    
	    // Version
	    lang.put("version.name", "Versió");
	    lang.put("version.created", "Data");
	    lang.put("version.author", "Autor");
	    lang.put("version.size", "Grandària");
	    lang.put("version.purge.document", "Compacta històric");
	    lang.put("version.comment", "Comentari");
	    
	    // Security
	    lang.put("security.label", "Seguretat");
	    lang.put("security.group.name", "Grup");
	    lang.put("security.group.permission.read", "Lectura");
	    lang.put("security.group.permission.write", "Escriptura");	   
	    lang.put("security.group.permission.delete", "Eliminar");
	    lang.put("security.group.permission.security", "Seguretat");
	    lang.put("security.user.name", "Usuari");
	    lang.put("security.user.permission.read", "Lectura");
	    lang.put("security.user.permission.write", "Escriptura");
	    lang.put("security.user.permission.delete", "Eliminar");
	    lang.put("security.user.permission.security", "Seguretat");
	    lang.put("security.users", "Usuaris");
	    lang.put("security.groups", "Grups");
	    lang.put("security.recursive", "Aplica canvis de forma recursiva");
	    lang.put("secutiry.filter.by.users","Filtre d'usuaris");
	    lang.put("secutiry.filter.by.groups","Filtre de grups");
	    

	    // Preview
	    lang.put("preview.unavailable", "Previsualització no disponible");
	    
	    // Mail
	    lang.put("mail.from", "Desde");
	    lang.put("mail.reply", "Contestar a");
	    lang.put("mail.to", "Per a");
	    lang.put("mail.subject", "Asunto");
	    lang.put("mail.attachment", "Fitxers adjunts");
	    
	    // Error
	    lang.put("error.label", "El sistema ha generat un error");
	    lang.put("error.invocation", "Error de comunicació amb el servidor");
	    
	    // About
	    lang.put("about.label", "Sobre OpenKM");
	    lang.put("about.loading", "Carregant ...");
	    
	    // Logout
	    lang.put("logout.label", "Surt");
	    lang.put("logout.closed", "OpenKM s'ha tancat correctament.");
	    lang.put("logout.logout", "OpenKM s'està tancant siusplau espereu un moment.");
	    
	    // Confirm
	    lang.put("confirm.label", "Confirmació");
	    lang.put("confirm.delete.folder", "Estau segurs que voleu eliminar la carpeta ?");
	    lang.put("confirm.delete.document", "Estau segurs que voleu eliminar el document ?");
	    lang.put("confirm.delete.trash", "Estau segurs que voleu buidar la paperera ?");
	    lang.put("confirm.purge.folder", "Estau segurs que voleu eliminar la carpeta ?");
	    lang.put("confirm.purge.document", "Estau segurs que voleu eliminar el document ?");
	    lang.put("confirm.delete.propety.group", "Estau segurs que voleu eliminar el grup de propietats ?");
	    lang.put("confirm.purge.version.history.document", "Estau segurs que voleu eliminar l'històric del document ?");
	    lang.put("confirm.purge.restore.document", "Estau segurs que voleu resturar aquesta versió del document ?");
	    lang.put("confirm.set.default.home", "Estau segurs que voleu establir l'inici per defecte ?");
	    lang.put("confirm.delete.saved.search", "Estau segurs que voleu eliminar la consulta emmagatzemanda ?");
	    lang.put("confirm.delete.user.news", "Estau segurs que voleu eliminar la novetat d'usuari ?");
	    lang.put("confirm.delete.mail", "Estau segurs que voleu eliminar el correu electrònic ?");
	    lang.put("confirm.get.pooled.workflow.task","¿ Estau segurs que us voleu assignar aquesta tasca ?");
	    
	    // Search
	    lang.put("search.context", "Contexte");
	    lang.put("search.content", "Contingut");
	    lang.put("search.name", "Nom");
	    lang.put("search.keywords", "Paraules clau");
	    lang.put("search.folder", "Carpeta");
	    lang.put("search.category", "Categoria");
	    lang.put("search.results", "Resultats");
	    lang.put("search.to", "al");
	    lang.put("search.page.results", "Resultats per pàgina");
	    lang.put("search.add.property.group", "Afegeix propietat de grup");
	    lang.put("search.mimetype", "Tipus de document");
	    lang.put("search.type", "Tipus");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Carpeta");
	    lang.put("search.type.mail", "Correu electrònic");
	    lang.put("search.advanced", "Cerca avançada");
	    lang.put("search.user", "Usuari");
	    lang.put("search.date.and", "i");
	    lang.put("search.date.range", "Rang de dates entre");
	    lang.put("search.save.as.news", "Desa com a novetat d'usuari");
	    
	    // search folder filter popup
	    lang.put("search.folder.filter", "Filtrat per carpeta");
	    lang.put("search.category.filter", "Filtrat per categories");
	    
	    // Search results
	    lang.put("search.result.name", "Nom");
	    lang.put("search.result.score", "Rellevància");
	    lang.put("search.result.size", "Tamany");
	    lang.put("search.result.date.update", "Data de modificació");
	    lang.put("search.result.author", "Autor");
	    lang.put("search.result.version", "Versió");
	    lang.put("search.result.path", "Ubicació");
	    lang.put("search.result.menu.download", "Descarrega");
	    lang.put("search.result.menu.go.folder", "Ves a la carpeta");
	    lang.put("search.result.menu.go.document", "Ves al document");
	    lang.put("search.result.status.findPaginated", "S'està refrescant la cerca");
	    lang.put("search.result.status.runsearch", "S'està refrescant la cerca emmagatzemada");
	    
	    // Search saved
	    lang.put("search.saved.run", "Executa");
	    lang.put("search.saved.delete", "Elimina");
	    lang.put("search.saved.status.getsearchs", "S'estan refrescant les cerques enmagazemades");
	    lang.put("search.saved.status.savesearch", "S'està guardant la cerca");
	    lang.put("search.saved.status.deletesearch", "S'està eliminant la cerca emmagatzemada");
	    lang.put("search.saved.status.getusernews", "S'estan refrescant les novetats d'usuari");
	    
	    // Button
	    lang.put("button.close", "Tanca");
	    lang.put("button.logout", "Surt");
	    lang.put("button.update", "Actualitza");
	    lang.put("button.cancel", "Cancel·la");
	    lang.put("button.accept", "Accepta");
	    lang.put("button.restore", "Restaura");
	    lang.put("button.move", "Mou");
	    lang.put("button.change", "Modifica");
	    lang.put("button.search", "Cerca");
	    lang.put("button.save.search", "Desa la consulta");
	    lang.put("button.view", "Visualitza");
	    lang.put("button.clean", "Neteja");
	    lang.put("button.add", "Afegeix");
	    lang.put("button.delete", "Elimina");
	    lang.put("button.copy", "Copia");
	    lang.put("button.create", "Crea");
	    lang.put("button.show", "Mostra");
	    lang.put("button.memory", "Actualitza");
	    lang.put("button.copy.clipboard", "Copia al portapapers");	    
	    lang.put("button.start", "Iniciar");
	    lang.put("button.select", "Sel.leciona");
	    lang.put("button.test", "Comprovar");
	    lang.put("button.next", "Següent");
	    
	    // Group
	    lang.put("group.label", "Afegeix grup de propietats");
	    lang.put("group.group", "Grup");
	    lang.put("group.property.group", "Propietat");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Afegeix enllaç d'interés");
	    lang.put("bookmark.name", "Nom");
	    lang.put("bookmark.edit.label", "Edita els enllaços d'interés");
	    lang.put("bookmark.path", "Ruta");
	    lang.put("bookmark.type", "Tipus");
	    lang.put("bookmark.type.document", "Document");
	    lang.put("bookmark.type.folder", "Carpeta");
	    
	    // Notify
	    lang.put("notify.label", "Enviar enllaç del document");
	    
	    // Status
	    lang.put("status.document.copied", "Document marcat per a copiar");
	    lang.put("status.document.cut", "Document marcat per a retallar");
	    lang.put("status.folder.copied", "Carpeta marcada per a copiar");
	    lang.put("status.folder.cut", "Carpeta marcada per a retallar");
	    lang.put("status.keep.alive.error", "Detectada pèrdua de connexió amb el servidor (keep alive)");
	    lang.put("status.debug.enabled", "Debug activat");
	    lang.put("status.debug.disabled", "Debug desactivat");
	    lang.put("status.network.error.detected", "Detectat error de red");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Diumenge");
	    lang.put("calendar.day.monday", "Dilluns");
	    lang.put("calendar.day.tuesday", "Dimarts");
	    lang.put("calendar.day.wednesday", "Dimecres");
	    lang.put("calendar.day.thursday", "Dijous");
	    lang.put("calendar.day.friday", "Divendres");
	    lang.put("calendar.day.saturday", "Dissabte");
	    lang.put("calendar.month.january", "Gener");
	    lang.put("calendar.month.february", "Febrer");
	    lang.put("calendar.month.march", "Març");
	    lang.put("calendar.month.april", "Abril");
	    lang.put("calendar.month.may", "Maig");
	    lang.put("calendar.month.june", "Juny");
	    lang.put("calendar.month.july", "Juliol");
	    lang.put("calendar.month.august", "Agost");
	    lang.put("calendar.month.september", "Setembre");
	    lang.put("calendar.month.october", "Octubre");
	    lang.put("calendar.month.november", "Novembre");
	    lang.put("calendar.month.december", "Desembre");
	    
	    // Media player
	    lang.put("media.player.label", "Reproductor multimedia");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Visor d'imatges");
	    lang.put("image.viewer.zoom.in", "Ampliar");
	    lang.put("image.viewer.zoom.out", "Reduir");
	    
	    // Debug console
	    lang.put("debug.console.label", "Consola de depuració");
	    lang.put("debug.enable.disable", "CTRL+Z per activar o desactivar el mode debug");
	    
	    // Dashboard tab
	    lang.put("dashboard.tab.general", "General");
	    lang.put("dashboard.tab.news", "Novetats");
	    lang.put("dashboard.tab.user", "Usuari");
	    lang.put("dashboard.tab.workflow", "Workflow");
	    lang.put("dashboard.tab.mail", "Correu");
	    lang.put("dashboard.tab.keymap", "Keyword map");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Nous");
	    lang.put("dashboard.user.locked.documents", "Documents bloquejats");
	    lang.put("dashboard.user.checkout.documents", "Documents checkout");
	    lang.put("dashboard.user.last.modified.documents", "Darrers documents modificats");
	    lang.put("dashboard.user.last.downloaded.documents", "Darrers documents descarregats");
	    lang.put("dashboard.user.subscribed.documents", "Documents suscrits");
	    lang.put("dashboard.user.subscribed.folders", "Carpetes suscrites");
	    lang.put("dashboard.user.last.uploaded.documents", "Darrers documets pujats");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Documents més vistos la darrera setmana");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Documents més vistos el darrer mes");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Documents més modificats la darrera setmana");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Documents més modificats el darrer mes");
	    lang.put("dashboard.general.last.uploaded.documents", "Darrers documents pujats");
	    lang.put("dashboard.workflow.pending.tasks", "Tasques pendents");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Tasques pendent no assignades");
	    lang.put("dashboard.workflow.task", "Tasca");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Nom");
	    lang.put("dashboard.workflow.task.created", "Data creació");
	    lang.put("dashboard.workflow.task.start", "Data inici");
	    lang.put("dashboard.workflow.task.duedate", "Data límit");
	    lang.put("dashboard.workflow.task.end", "Data finalització");
	    lang.put("dashboard.workflow.task.description", "Descripció");
	    lang.put("dashboard.workflow.task.process.instance", "Instancia del procés");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Versió");
	    lang.put("dashboard.workflow.task.process.name", "Nom");
	    lang.put("dashboard.workflow.task.process.description", "Descripció");
	    lang.put("dashboard.workflow.task.process.data", "Dades");
	    lang.put("dashboard.workflow.comments", "Comentaris");
	    lang.put("dashboard.workflow.task.process.forms", "Formulari");
	    lang.put("dashboard.workflow.add.comment","Añadir comentario");
	    lang.put("dashboard.workflow.task.process.definition", "Definició del process");
	    lang.put("dashboard.workflow.task.process.path", "Ruta");
	    lang.put("dashboard.refreshing", "Refrescant");
	    lang.put("dashboard.keyword", "Paraules clau");
	    lang.put("dashboard.keyword.suggest", "Escriu la paraula plau");
	    lang.put("dashboard.keyword.all", "Paraules clau");
	    lang.put("dashboard.keyword.top", "Paraules clau mes emprades");
	    lang.put("dashboard.keyword.related", "Paraules clau relacionades");
	    lang.put("dashboard.keyword.goto.document", "Anar al document");
	    lang.put("dashboard.keyword.clean.keywords", "Neteja paraules clau");
	    lang.put("dashboard.mail.last.imported.mails", "Correu electrònic");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Documents adjunts");
	    
	    // Workflow
	    lang.put("workflow.label", "Iniciar workflow");
	    
	    // User configuration
	    lang.put("user.preferences.label", "Configuración del usuario");
	    lang.put("user.preferences.user", "Usuari");
	    lang.put("user.preferences.password", "Clau");
	    lang.put("user.preferences.mail", "Correu electrònic");
	    lang.put("user.preferences.imap.host", "Servidor de IMAP");
	    lang.put("user.preferences.imap.user", "Nom d'usuari de IMAP");
	    lang.put("user.preferences.imap.user.password", "Clau de l'usuari de IMAP");
	    lang.put("user.preferences.imap.folder", "Carpeta de IMAP");
	    lang.put("user.preferences.password.error", "Error la clau no coincideix");
	    lang.put("user.preferences.user.data", "Compte d'usuari");
	    lang.put("user.preferences.mail.data", "Compte de mail");
	    lang.put("user.preferences.imap.error", "Tots els camps son obligatoris per configurar el correu");
	    lang.put("user.preferences.imap.password.error.void", "El clau de IMAP no pot ser buit en la creació");
	    lang.put("user.preferences.imap.test.error","Configuració de IMAP incorrecta");
	    lang.put("user.preferences.imap.test.ok","Configuració de IMAP correcta");

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Afegir una paraula clau del thesaurus");
	    lang.put("thesaurus.tab.tree", "Arbre");
	    lang.put("thesaurus.tab.keywords", "Paraules clau");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Afegir una categoria");
	    lang.put("categories.folder.error.delete", "No es pot eliminar una categoria amb documents");
	    
	    // Wizard
	    lang.put("wizard.document.uploading", "Assistent de document");
	    
	    // User info
	    lang.put("user.info.chat.connect", "Conectar el chat");
	    lang.put("user.info.chat.disconnect", "Desconectar el chat");
	    lang.put("user.info.chat.new.room", "Nou chat");
	    lang.put("user.info.locked.actual", "Documents bloquejats");
	    lang.put("user.info.checkout.actual", "Documents editats");
	    lang.put("user.info.subscription.actual", "Suscripcions actuals");
	    lang.put("user.info.news.new", "Novetats");
	    lang.put("user.info.workflow.pending", "Workflows pendients");
	    lang.put("user.info.user.quota", "Quota utilizada");
	    
	    // Users online popup
	    lang.put("user.online", "Usuaris conectats");
	    
	    // Chat room
	    lang.put("chat.room", "Chat");
	    lang.put("chat.users.in.room", "Usuaris");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "No teniu permisos per a accedir al document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "No s'ha pogut bloquejar el document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Error intern en el repositori");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "La ruta del document no existeix");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Error en la versió");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "No teniu permisos per a accedir al document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "No existeix un document amb aquest nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Ja existeix un document amb aquest nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "No s'ha pogut bloquejar el document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "No s'ha pogut desbloquejar el document");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Error intern en el repositori");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Error intern de l'aplicació");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "La ruta del document no existeix");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "No teniu permisos per a accedir a la carpeta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "No existeix una carpeta amb aquest nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Ja existeix una carpeta amb aquest nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Error intern del repositori");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Error intern de l'aplicació");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "La ruta de la carpeta no existeix");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "No teniu permisos per a accedir a l'element");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "No existeix un element amb aquest nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Error intern del repositori");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Error intern de l'aplicació");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "No existeix un document amb aquest nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Error intern de l'aplicació");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Tipus de fitxer no suportat");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Ja existeix un document amb aquest nom");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "El document és massa gran");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "La sessió s'ha tancat");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Generic error executing query");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "El nom de la consulta enmagatzemada ha de ser únic");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "El nom del l'enllaç d'interés ha de ser únic");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "S'ha perdut la sessió");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Error intern del repositori");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Error intern del repositori");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error en el dispositiu I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Error intern del repositori");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.ORIGIN_OKMBookmarkService, "Error en la base de dades");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error en la configuració del navegador");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_QuotaExceed, "Error ha superat la quota de usuario, contacti amb l'administrador");
	  }
}