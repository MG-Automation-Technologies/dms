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
 * Spanish (Spain)
 * 
 * @author Paco Avila
 */
public class Lang_es_ES {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd/MM/yyyy HH:mm:ss");
	    lang.put("general.day.pattern", "dd/MM/yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Cargando OpenKM");
	    lang.put("startup.starting.loading", "Comenzando la carga de OpenKM");
	    lang.put("startup.taxonomy", "Obteniendo el nodo principal de la taxonomía");
	    lang.put("startup.categories", "Obteniendo el nodo principal de las categorías");
	    lang.put("startup.thesaurus", "Obteniendo el nodo principal del thesausus");
	    lang.put("startup.template", "Obteniendo el nodo principal de las plantillas");
	    lang.put("startup.personal", "Obteniendo el nodo principal de personal");
	    lang.put("startup.mail", "Obteniendo el nodo principal de mail");
	    lang.put("startup.trash", "Oteniendo el nodo principal de la papelera");
	    lang.put("startup.user.home", "Obteniendo el inicio de usuarios");
	    lang.put("startup.bookmarks", "Obteniendo marcadores");
	    lang.put("startup.loading.taxonomy", "Cargando taxonomía");
	    lang.put("startup.loading.taxonomy.getting.folders", "Cargando taxonomía - obteniendo carpetas");
	    lang.put("startup.loading.taxonomy.eval.params", "Cargando taxonomía - evaluando parámetros");
	    lang.put("startup.loading.taxonomy.open.path", "Cargando taxonomía - abriendo la ruta");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Cargando taxonomía - obteniendo carpetas");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Cargando taxonomía - obteniendo documentos");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Cargando taxonomía - obteniendo correos electrónicos");
	    lang.put("startup.loading.personal", "Cargando personales");
	    lang.put("startup.loading.mail", "Cargando correos");
	    lang.put("startup.loading.categories", "Cargando categorías");
	    lang.put("startup.loading.thesaurus", "Cargando thesaurus");
	    lang.put("startup.loading.templates", "Cargando plantillas");
	    lang.put("startup.loading.trash", "Cargando papelera");
	    lang.put("startup.loading.history.search", "Cargando histórico de búsquedas");
	    lang.put("startup.loading.user.values", "Cargando datos del usuario");
	    lang.put("startup.loading.property.group.translations", "Cargando traducciones de grupo de propiedades");
	    lang.put("startup.keep.alive", "Cargando keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Actualización disponible de OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Taxonomía");
	    lang.put("leftpanel.label.trash", "Papelera");
	    lang.put("leftpanel.label.mail", "Correo electrónico");
	    lang.put("leftpanel.label.stored.search", "Búsquedas almacenadas");
	    lang.put("leftpanel.label.categories", "Categorías");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Plantillas");
	    lang.put("leftpanel.label.my.documents", "Documentos personales");
	    lang.put("leftpanel.label.user.search", "Novedades de usuario");
	    lang.put("leftpanel.label.all.repository", "En todo el repositorio");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Crear carpeta");
	    lang.put("tree.menu.directory.remove", "Eliminar");
	    lang.put("tree.menu.directory.rename", "Renombrar");
	    lang.put("tree.menu.directory.refresh", "Refrescar");
	    lang.put("tree.menu.directory.move", "Mover");
	    lang.put("tree.menu.directory.copy", "Copiar");
	    lang.put("tree.menu.directory.add.document", "Añadir documento");
	    lang.put("tree.menu.add.bookmark", "Añadir marcador");
	    lang.put("tree.menu.set.home", "Establecer inicio");
	    lang.put("tree.menu.export", "Exportar a fichero");
	    lang.put("tree.status.refresh.folder", "Actualizando árbol de carpetas");
	    lang.put("tree.status.refresh.create", "Creando carpeta");
	    lang.put("tree.status.refresh.delete", "Eliminando carpeta");
	    lang.put("tree.status.refresh.rename", "Renombrando carpeta");
	    lang.put("tree.status.refresh.restore", "Restaurando carpeta");
	    lang.put("tree.status.refresh.purge", "Eliminando carpeta");
	    lang.put("tree.status.refresh.get", "Actualizando carpeta");
	    lang.put("tree.folder.new", "Nueva carpeta");
	    lang.put("tree.status.refresh.add.subscription", "Estableciendo suscripción");
	    lang.put("tree.status.refresh.remove.subscription", "Eliminando suscripción");
	    lang.put("tree.status.refresh.get.root", "Refrescando carpeta inicial");
	    lang.put("tree.status.refresh.get.keywords", "Refrescando palabras clave");
	    lang.put("tree.status.refresh.get.user.home", "Obteniendo inicio del usuario");
	    lang.put("tree.status.refresh.purge.trash", "Vaciando papelera");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Restaurar");
	    lang.put("trash.menu.directory.purge", "Eliminar");
	    lang.put("trash.menu.directory.purge.trash", "Vaciar papelera");
	    lang.put("trash.directory.select.label", "Seleccionar capeta de destino");
	    
	    // General menu
	    lang.put("general.menu.file", "Archivo");
	    	lang.put("general.menu.file.exit", "Salir");
	    	lang.put("general.menu.file.purge.trash", "Vaciar papelera");
	    lang.put("general.menu.edit", "Edición");
			lang.put("general.menu.file.create.directory", "Crear carpeta");
			lang.put("general.menu.file.download.document", "Descargar documento");
			lang.put("general.menu.file.download.document.pdf", "Descargar documento como PDF");
			lang.put("general.menu.file.send.link", "Enviar enlace del documento");
			lang.put("general.menu.file.lock", "Bloquear");
			lang.put("general.menu.file.unlock", "Desbloquear");
			lang.put("general.menu.file.add.document", "Añadir documento");
			lang.put("general.menu.file.checkout", "Checkout");
			lang.put("general.menu.file.checkin", "Checkin");
			lang.put("general.menu.file.cancel.checkout", "Cancelar checkout");
			lang.put("general.menu.file.delete", "Eliminar");
			lang.put("general.menu.file.refresh", "Refrescar");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "Subida de ficheros");
	    lang.put("general.menu.tools", "Herramientas");
	    	lang.put("general.menu.tools.languages", "Idiomas");
	    	lang.put("general.menu.tools.skin", "Apariencia");
    			lang.put("general.menu.tools.skin.default", "Por defecto");
    			lang.put("general.menu.tools.skin.default2", "Por defecto 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Fuente mediana");
    			lang.put("general.menu.tools.skin.bigfont", "Fuente grande");
    		lang.put("general.menu.debug.console", "Consola de depuración");
    		lang.put("general.menu.administration", "Mostrar administración");
    		lang.put("general.menu.tools.preferences", "Prefererencias");
    			lang.put("general.menu.tools.user.preferences", "Configuración del usuario");
    	lang.put("general.menu.bookmark", "Marcadores");
	    	lang.put("general.menu.bookmark.home", "Inicio");
	    	lang.put("general.menu.bookmark.default.home", "Establecer inicio por defecto");
	    	lang.put("general.menu.bookmark.edit", "Editar marcadores");
	    lang.put("general.menu.help", "Ayuda");
		    lang.put("general.menu.bug.report", "Reportar fallos");
		    lang.put("general.menu.support.request", "Solicitud soporte");
		    lang.put("general.menu.public.forum", "Foro público");
		    lang.put("general.menu.project.web", "Web del proyecto");
		    lang.put("general.menu.documentation", "Documentación");
		    lang.put("general.menu.version.changes", "Notas de la versión");
		    lang.put("general.menu.about", "Sobre OpenKM");
	    lang.put("general.connected", "Conectado como");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Nombre");
	    lang.put("filebrowser.date.update", "Fecha de modificación");
	    lang.put("filebrowser.size", "Tamaño");
	    lang.put("filebrowser.path", "Ruta");
	    lang.put("filebrowser.author", "Autor");
	    lang.put("filebrowser.version", "Versión");
	    lang.put("filebrowser.menu.checkout", "Check-out");
	    lang.put("filebrowser.menu.checkin", "Check-in");
	    lang.put("filebrowser.menu.delete", "Eliminar");
	    lang.put("filebrowser.menu.rename", "Renombrar");
	    lang.put("filebrowser.menu.checkout.cancel", "Cancelar check-out");
	    lang.put("filebrowser.menu.lock", "Bloquear");
	    lang.put("filebrowser.menu.unlock", "Desbloquear");
	    lang.put("filebrowser.menu.download", "Descargar");
	    lang.put("filebrowser.menu.move", "Mover");
	    lang.put("filebrowser.menu.copy", "Copiar");
	    lang.put("filebrowser.menu.create.from.template", "Crear desde plantilla");
	    lang.put("filebrowser.menu.add.property.group", "Añadir grupo de propiedades");
	    lang.put("filebrowser.menu.remove.property.group", "Eliminar grupo de propiedades");
	    lang.put("filebrowser.menu.start.workflow", "Iniciar workflow");
	    lang.put("filebrowser.menu.add.subscription", "Añadir suscripción");
	    lang.put("filebrowser.menu.remove.subscription", "Eliminar suscripción");
	    lang.put("filebrowser.menu.add.bookmark", "Añadir marcador");
	    lang.put("filebrowser.menu.set.home", "Establecer inicio");
	    lang.put("filebrowser.menu.refresh", "Refrescar");
	    lang.put("filebrowser.menu.export", "Exportar a ZIP");
	    lang.put("filebrowser.menu.play", "Reproducir");
	    lang.put("filebrowser.menu.image.viewer", "Visor de imágenes");
	    lang.put("filebrowser.status.refresh.folder", "Actualizando lista de carpetas");
	    lang.put("filebrowser.status.refresh.document", "Actualizando lista de documentos");
	    lang.put("filebrowser.status.refresh.mail", "Actualizando lista de correos electrónicos");
	    lang.put("filebrowser.status.refresh.delete.folder", "Eliminando carpeta");
	    lang.put("filebrowser.status.refresh.delete.document", "Eliminando documento");
	    lang.put("filebrowser.status.refresh.checkout", "Realizando checkout");
	    lang.put("filebrowser.status.refresh.lock", "Estableciendo lock");
	    lang.put("filebrowser.status.refresh.unlock", "Eliminando lock");
	    lang.put("filebrowser.status.refresh.document.rename", "Renombrando documento");
	    lang.put("filebrowser.status.refresh.folder.rename", "Renombrando carpeta");
	    lang.put("filebrowser.status.refresh.document.purge", "Eliminando documento");
	    lang.put("filebrowser.status.refresh.folder.purge", "Eliminando carpeta");
	    lang.put("filebrowser.status.refresh.folder.get", "Actualizando carpeta");
	    lang.put("filebrowser.status.refresh.document.get", "Actualizando documento");
	    lang.put("filebrowser.status.refresh.add.subscription", "Estableciendo suscripción");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Eliminando suscripción");
	    lang.put("filebrowser.status.refresh.get.user.home", "Obteniendo inicio");
	    lang.put("filebrowser.status.refresh.delete.mail", "Eliminando correo electrónico");
	    lang.put("filebrowser.status.refresh.mail.purge", "Eliminando correo electrónico");
	    
	    // File Upload
	    lang.put("fileupload.send", "Enviar");
	    lang.put("fileupload.status.sending", "Subiendo el fichero...");
	    lang.put("fileupload.status.indexing", "Indexando el fichero...");
	    lang.put("fileupload.status.ok", "Envío completado correctamente");
	    lang.put("fileupload.upload.status", "Estado envio");
	    lang.put("fileupload.upload.uploaded", "Completado");
	    lang.put("fileupload.upload.completed", "Envio completado");
	    lang.put("fileupload.upload.runtime", "Tiempo de ejecución");
	    lang.put("fileupload.upload.remaining", "falta");
	    lang.put("fileupload.button.close", "Cerrar");
	    lang.put("fileupload.button.add.other.file", "Añadir otro fichero");
	    lang.put("fileupload.status.move.file", "Moviendo el fichero...");
	    lang.put("fileupload.status.move.mail", "Moviendo el correo electrónico...");
	    lang.put("fileupload.status.copy.file", "Copiando el fichero...");
	    lang.put("fileupload.status.copy.mail", "Copiando el correo electrónico...");
	    lang.put("fileupload.status.restore.file", "Restaurando el fichero...");
	    lang.put("fileupload.status.restore.mail", "Restaurando el correo electrónico...");
	    lang.put("fileupload.status.move.folder", "Moviendo la carpeta...");
	    lang.put("fileupload.status.copy.folder", "Copiando la carpeta...");
	    lang.put("fileupload.status.restore.folder", "Restaurando la carpeta...");
	    lang.put("fileupload.status.create.from.template", "Creando el fichero desde la plantilla...");
	    lang.put("fileupload.status.of", "de");
	    lang.put("fileupload.label.insert", "Inserción de documentos");
	    lang.put("fileupload.label.update", "Actualización de documentos");
	    lang.put("fileupload.label.users.notify", "Notificar a los usuarios");
	    lang.put("fileupload.label.comment", "Comentario");
	    lang.put("fileupload.label.users.to.notify",  "Usuarios a notificar");
	    lang.put("fileupload.label.users",  "Usuarios");
	    lang.put("fileupload.label.must.select.users",  "Seleccione algún usuario al que notificar");
	    lang.put("fileupload.label.importZip", "Importar documentos desde ZIP");
	    lang.put("fileupload.label.notify.comment", "Mensaje de notificación");
	    lang.put("fileupload.label.error.importing.zip", "Error al importar el fichero");
	    lang.put("fileupload.label.error.move.file", "Error moviendo el fichero");
	    lang.put("fileupload.label.error.move.mail", "Error moviendo el correo electrónico");
	    lang.put("fileupload.label.error.copy.file", "Error copiando el fichero");
	    lang.put("fileupload.label.error.copy.mail", "Error copiando el correo electrónico");
	    lang.put("fileupload.label.error.restore.file", "Error restaurando el fichero");
	    lang.put("fileupload.label.error.restore.mail", "Error restaurando el correo electrónico");
	    lang.put("fileupload.label.error.move.folder", "Error moviendo la carpeta");
	    lang.put("fileupload.label.error.copy.folder", "Error copiando la carpeta");
	    lang.put("fileupload.label.error.restore.folder", "Error restaurando la carpeta");
	    lang.put("fileupload.label.error.create.from.template", "Error creando fichero desde la plantilla");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "No se permite mover en la carpeta origen o hijas");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "No se permite copiar en la carpeta origen");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "No se permite crear un fichero en la carpeta origen");

	    // Tab properties
	    lang.put("tab.document.properties", "Propiedades");
	    lang.put("tab.document.notes", "Notas");
	    lang.put("tab.document.history", "Historial");
	    lang.put("tab.document.status.history", "Actualizando historial");
	    lang.put("tab.status.security.role", "Actualizando seguridad roles");
	    lang.put("tab.status.security.user", "Actualizando seguridad usuarios");
	    lang.put("tab.document.status.group.properties", "Actualizando grupo de propiedades");
	    lang.put("tab.document.status.set.keywords", "Estableciendo palabras clave");
	    lang.put("tab.document.status.set.categories", "Actualizando categorías");
	    lang.put("tab.document.status.get.version.history.size", "Refrescando el tamaño del histórico del documento");
	    lang.put("tab.document.status.purge.version.history", "Compactando el histórico de documentos");
	    lang.put("tab.document.status.restore.version", "Restaurando la versión del documento");
	    lang.put("tab.document.security", "Seguridad");
	    lang.put("tab.document.preview", "Previsualizar");
	    lang.put("tab.folder.properties", "Propiedades");
	    lang.put("tab.folder.security", "Seguridad");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Escritorio");
	    lang.put("tab.workspace.search", "Buscador");
	    lang.put("tab.workspace.dashboard", "Tablón");
	    lang.put("tab.workspace.administration", "Administración");
	    
	    // Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Nombre");
	    lang.put("document.folder", "Carpeta");
	    lang.put("document.size", "Tamaño");
	    lang.put("document.created", "Creado");
	    lang.put("document.lastmodified", "Modificado");
	    lang.put("document.mimetype", "Tipo MIME");
	    lang.put("document.keywords", "Palabras clave");
	    lang.put("document.by", "por");
	    lang.put("document.status", "Estado");
	    lang.put("document.status.checkout", "Editado por");
	    lang.put("document.status.locked", "Bloqueado por");
	    lang.put("document.status.normal", "Disponible");
	    lang.put("document.subscribed", "Suscrito");
	    lang.put("document.subscribed.yes", "Sí");
	    lang.put("document.subscribed.no", "No");
	    lang.put("document.history.size", "Tamaño del histórico");
	    lang.put("document.subscribed.users", "Usuarios suscritos");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Añadir nota");
	    lang.put("document.keywords.cloud", "Nube de palabras clave");
	    lang.put("document.categories", "Categorías");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nombre");
	    lang.put("folder.parent", "Padre");
	    lang.put("folder.created", "Creado");
	    lang.put("folder.by", "por");
	    lang.put("folder.subscribed", "Suscrito");
	    lang.put("folder.subscribed.yes", "Sí");
	    lang.put("folder.subscribed.no", "No");
	    lang.put("folder.subscribed.users", "Usuarios suscritos");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Versión");
	    lang.put("version.created", "Fecha");
	    lang.put("version.author", "Autor");
	    lang.put("version.size", "Tamaño");
	    lang.put("version.purge.document", "Compactar histórico");
	    lang.put("version.comment", "Comentario");
	    
	    // Security
	    lang.put("security.label", "Seguridad");
	    lang.put("security.group.name", "Grupo");
	    lang.put("security.group.permission.read", "Lectura");
	    lang.put("security.group.permission.write", "Escritura");
	    lang.put("security.user.name", "Usuario");
	    lang.put("security.user.permission.read", "Lectura");
	    lang.put("security.user.permission.write", "Escritura");
	    lang.put("security.users", "Usuarios");
	    lang.put("security.groups", "Grupos");
	    lang.put("security.recursive", "Aplicar cambios de forma recursiva");
	    
	    // Preview
	    lang.put("preview.unavailable", "Previsualización no disponible");

	    // Mail
	    lang.put("mail.from", "Desde");
	    lang.put("mail.reply", "Contestar a");
	    lang.put("mail.to", "Para");
	    lang.put("mail.subject", "Asunto");
	    lang.put("mail.attachment", "Ficheros adjuntos");
	    
	    // Error
	    lang.put("error.label", "El sistema ha generado un error");
	    lang.put("error.invocation", "Error de comunicación con el servidor");
	    
	    // About
	    lang.put("about.label", "Sobre OpenKM");
	    lang.put("about.loading", "Cargando ...");
	    
	    // Logout
	    lang.put("logout.label", "Salir");
	    lang.put("logout.closed", "OpenKM se ha cerrado correctamente.");
	    lang.put("logout.logout", "OpenKM se está cerrando; por favor espere un momento");
	    
	    // Confirm
	    lang.put("confirm.label", "Confirmación");
	    lang.put("confirm.delete.folder", "¿Seguro que desea eliminar la carpeta?");
	    lang.put("confirm.delete.document", "¿Seguro que desea eliminar el documento?");
	    lang.put("confirm.delete.trash", "¿Seguro que desea vaciar la papelera?");
	    lang.put("confirm.purge.folder", "¿Seguro que desea eliminar la carpeta?");
	    lang.put("confirm.purge.document", "¿Seguro que desea eliminar el documento?");
	    lang.put("confirm.delete.propety.group", "¿Seguro que desea eliminar el grupo de propiedades?");
	    lang.put("confirm.purge.version.history.document", "¿Seguro que desea eliminar el histórico del documento?");
	    lang.put("confirm.purge.restore.document", "¿Seguro que desea restaurar a esta versión del histórico del documento?");
	    lang.put("confirm.set.default.home", "¿Seguro que desea establecer el inicio por defecto?");
	    lang.put("confirm.delete.saved.search", "¿Seguro que desea eliminar la consulta almacenada?");
	    lang.put("confirm.delete.user.news", "¿Seguro que desea eliminar la novedad de usuario?");
	    lang.put("confirm.delete.mail", "¿Seguro que desea eliminar el correo electrónico?");
	    
	    // Search
	    lang.put("search.context", "Contexto");
	    lang.put("search.content", "Contenido");
	    lang.put("search.name", "Nombre");
	    lang.put("search.keywords", "Palabras clave");
	    lang.put("search.folder", "Carpeta");
	    lang.put("search.results", "Resultados");
	    lang.put("search.to", "al");
	    lang.put("search.page.results", "Resultados por página");
	    lang.put("search.add.property.group", "Añadir propiedad de grupo");
	    lang.put("search.mimetype", "Tipo documento");
	    lang.put("search.type", "Tipo");
	    lang.put("search.type.document", "Documento");
	    lang.put("search.type.folder", "Carpeta");
	    lang.put("search.type.mail", "Correo electrónico");
	    lang.put("search.advanced", "Búsqueda avanzada");
	    lang.put("search.user", "Usuario");
	    lang.put("search.date.and", "y");
	    lang.put("search.date.range", "Rango de fechas entre");
	    lang.put("search.save.as.news", "Guardar como novedades de usuario");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filtrado por carpeta");
	    
	    // Search results
	    lang.put("search.result.name", "Nombre");
	    lang.put("search.result.score", "Relevancia");
	    lang.put("search.result.size", "Tamaño");
	    lang.put("search.result.date.update", "Fecha de modificación");
	    lang.put("search.result.author", "Autor");
	    lang.put("search.result.version", "Versión");
	    lang.put("search.result.path", "Ubicación");
	    lang.put("search.result.menu.download", "Descargar");
	    lang.put("search.result.menu.go.folder", "Ir a la carpeta");
	    lang.put("search.result.menu.go.document", "Ir al documento");
	    lang.put("search.result.status.findPaginated", "Ejecutando búsqueda");
	    lang.put("search.result.status.runsearch", "Ejecutando búsqueda almacenada");
	    
	    // Search saved
	    lang.put("search.saved.run", "Ejecutar");
	    lang.put("search.saved.delete", "Eliminar");
	    lang.put("search.saved.status.getsearchs", "Actualizando búsquedas almacenadas");
	    lang.put("search.saved.status.savesearch", "Almacenando búsqueda");
	    lang.put("search.saved.status.deletesearch", "Eliminando búsqueda almacenada");
	    lang.put("search.saved.status.getusernews", "Actualizando las novedades de usuario");
	    
	    // Button
	    lang.put("button.close", "Cerrar");
	    lang.put("button.logout", "Salir");
	    lang.put("button.update", "Actualizar");
	    lang.put("button.cancel", "Cancelar");
	    lang.put("button.accept", "Aceptar");
	    lang.put("button.restore", "Restaurar");
	    lang.put("button.move", "Mover");
	    lang.put("button.change", "Modificar");
	    lang.put("button.search", "Buscar");
	    lang.put("button.save.search", "Guardar la consulta");
	    lang.put("button.view", "Ver");
	    lang.put("button.clean", "Limpiar");
	    lang.put("button.add", "Añadir");
	    lang.put("button.delete", "Eliminar");
	    lang.put("button.copy", "Copiar");
	    lang.put("button.create", "Crear");
	    lang.put("button.show", "Mostrar");
	    lang.put("button.memory", "Actualizar");
	    lang.put("button.copy.clipboard", "Copiar al portapapeles");	
	    lang.put("button.start", "Iniciar");
	    lang.put("button.select", "Seleccionar");
	    
	    // Group
	    lang.put("group.label", "Añadir grupo de propiedad");
	    lang.put("group.group", "Grupo");
	    lang.put("group.property.group", "Propiedad");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Añadir marcador");
	    lang.put("bookmark.name", "Nombre");
	    lang.put("bookmark.edit.label", "Edición de marcadores");
	    lang.put("bookmark.path", "Ruta");
	    lang.put("bookmark.type", "Tipo");
	    lang.put("bookmark.type.document", "Documento");
	    lang.put("bookmark.type.folder", "Carpeta");
	    
	    // Notify
	    lang.put("notify.label", "Enviar enlace del documento");
	    
	    // Status
	    lang.put("status.document.copied", "Documento marcado para copiar");
	    lang.put("status.document.cut", "Documento marcado para recortar");
	    lang.put("status.folder.copied", "Carpeta marcada para copiar");
	    lang.put("status.folder.cut", "Carpeta marcada para recortar");
	    lang.put("status.keep.alive.error", "Detectada pérdida de conexión con el servidor (keep alive)");
	    lang.put("status.debug.enabled", "Depuración activada");
	    lang.put("status.debug.disabled", "Depuración desactivada");
	    lang.put("status.network.error.detected", "Detectado error de red");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Domingo");
	    lang.put("calendar.day.monday", "Lunes");
	    lang.put("calendar.day.tuesday", "Martes");
	    lang.put("calendar.day.wednesday", "Miércoles");
	    lang.put("calendar.day.thursday", "Jueves");
	    lang.put("calendar.day.friday", "Viernes");
	    lang.put("calendar.day.saturday", "Sábado");
	    lang.put("calendar.month.january", "Enero");
	    lang.put("calendar.month.february", "Febrero");
	    lang.put("calendar.month.march", "Marzo");
	    lang.put("calendar.month.april", "Abril");
	    lang.put("calendar.month.may", "Mayo");
	    lang.put("calendar.month.june", "Junio");
	    lang.put("calendar.month.july", "Julio");
	    lang.put("calendar.month.august", "Agosto");
	    lang.put("calendar.month.september", "Septiembre");
	    lang.put("calendar.month.october", "Octubre");
	    lang.put("calendar.month.november", "Noviembre");
	    lang.put("calendar.month.december", "Diciembre");
	    
	    // Media player
	    lang.put("media.player.label", "Reproductor multimedia");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Visor de imágenes");
	    lang.put("image.viewer.zoom.in", "Ampliar");
	    lang.put("image.viewer.zoom.out", "Reducir");
	    
	    // Debug console
	    lang.put("debug.console.label", "Consola de depuración");
	    lang.put("debug.enable.disable", "CTRL+Z para activar o desactivar el modo de depuración");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "General");
	    lang.put("dashboard.tab.news", "Novedades");
	    lang.put("dashboard.tab.user", "Usuario");
	    lang.put("dashboard.tab.workflow", "Workflow");
	    lang.put("dashboard.tab.mail", "Correo");
	    lang.put("dashboard.tab.keymap", "Palabras clave");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Nuevos");
	    lang.put("dashboard.user.locked.documents", "Documentos bloqueados");
	    lang.put("dashboard.user.checkout.documents", "Documentos en checkout");
	    lang.put("dashboard.user.last.modified.documents", "Últimos documentos modificados");
	    lang.put("dashboard.user.last.downloaded.documents", "Últimos documentos descargados");
	    lang.put("dashboard.user.subscribed.documents", "Documentos suscritos");
	    lang.put("dashboard.user.subscribed.folders", "Carpetas suscritas");
	    lang.put("dashboard.user.last.uploaded.documents", "Últimos documentos subidos");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Documentos más vistos la última semana");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Documentos más vistos el último mes");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Documentos más modificados la última semana");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Documentos más modificados el último mes");
	    lang.put("dashboard.general.last.uploaded.documents", "Últimos documentos subidos");
	    lang.put("dashboard.workflow.pending.tasks", "Tareas pendientes");
	    lang.put("dashboard.workflow.task", "Tarea");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Nombre");
	    lang.put("dashboard.workflow.task.created", "Fecha de creación");
	    lang.put("dashboard.workflow.task.start", "Fecha de inicio");
	    lang.put("dashboard.workflow.task.duedate", "Fecha límite");
	    lang.put("dashboard.workflow.task.end", "Fecha de fin");
	    lang.put("dashboard.workflow.task.description", "Descripción");
	    lang.put("dashboard.workflow.task.process.instance", "Instancia de proceso");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Versión");
	    lang.put("dashboard.workflow.task.process.name", "Nombre");
	    lang.put("dashboard.workflow.task.process.description", "Descripción");
	    lang.put("dashboard.workflow.task.process.data", "Datos");
	    lang.put("dashboard.workflow.task.process.definition", "Definición de proceso");
	    lang.put("dashboard.workflow.task.process.path", "Ruta");
	    lang.put("dashboard.refreshing", "Refrescando");
	    lang.put("dashboard.keyword", "Palabras clave");
	    lang.put("dashboard.keyword.suggest", "Escribe la palabra");
	    lang.put("dashboard.keyword.all", "Palabras clave");
	    lang.put("dashboard.keyword.top", "Palabras clave más usadas");
	    lang.put("dashboard.keyword.related", "Palabras clave relacionadas");
	    lang.put("dashboard.keyword.goto.document", "Ir al documento");
	    lang.put("dashboard.keyword.clean.keywords", "Limpiar palabras clave");
	    lang.put("dashboard.mail.last.imported.mails", "Correos electrónicos");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Documentos adjuntos");
	    
	    // Workflow
	    lang.put("workflow.label", "Iniciar workflow");
	    
	    // User configuration
	    lang.put("user.preferences.label", "Configuración de usuario");
	    lang.put("user.preferences.user", "Usuario");
	    lang.put("user.preferences.password", "Clave");
	    lang.put("user.preferences.mail", "Correo electrónico");
	    lang.put("user.preferences.imap.host", "Servidor de IMAP");
	    lang.put("user.preferences.imap.user", "Usuario de IMAP");
	    lang.put("user.preferences.imap.user.password", "Clave de IMAP");
	    lang.put("user.preferences.imap.folder", "Carpeta de IMAP");
	    lang.put("user.preferences.password.error", "Error: las claves son distintas");
	    lang.put("user.preferences.user.data", "Cuenta de usuario");
	    lang.put("user.preferences.mail.data", "Cuenta de correo");
	    lang.put("user.preferences.imap.error", "Todos los campos son obligatorios para configurar el correo");
	    lang.put("user.preferences.imap.password.error.void", "La clave de IMAP no puede estar vacía en la creación");

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Añadir una palabra clave del thesaurus");
	    lang.put("thesaurus.tab.tree", "Arbol");
	    lang.put("thesaurus.tab.keywords", "Palabras clave");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Añadir categoría");
	    lang.put("categories.folder.error.delete", "No se puede eliminar una categoría con documentos");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "No tiene permisos para acceder al documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "No se pudo bloquear el documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Error interno del repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "La ruta del documento no existe");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Error en la versión");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "No tiene permisos para acceder al documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "No existe un documento con ese nombre");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Ya existe un documento con ese nombre");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "No se pudo bloquear el documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "No se pudo desbloquear el documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Error interno del repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Error interno de la aplicación");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "La ruta del documento no existe");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "No tiene permisos para acceder a la carpeta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "No existe una carpeta con ese nombre");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Ya existe una carpeta con ese nombre");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Error interno del repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Error interno de la aplicación");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "La ruta de la carpeta no existe");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "No tiene permisos para acceder al elemento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "No existe un elemento con ese nombre");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Error interno del repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Error interno de la aplicación");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "No existe un documento con ese nombre");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Error interno de la aplicación");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Tipo de fichero no soportado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Ya existe un documento con ese nombre");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "El documento es demasiado grande");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "La sesión se ha cerrado");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Error genérico al ejecutar la consulta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "El nombre de la consulta almacenada debe ser único");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "El nombre del marcador deber ser único");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Se ha perdido la sesión");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Error interno del repositorio");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Error interno del repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error en el dispositivo de entrada/salida");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Error interno del repositorio");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error en la configuración del navegador");
	  }
}
