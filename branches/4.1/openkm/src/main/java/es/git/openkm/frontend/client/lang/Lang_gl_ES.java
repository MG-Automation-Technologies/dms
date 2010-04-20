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
 * Galician (Spain)
 * 
 * @author Rubio
 */
public class Lang_gl_ES {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd/MM/yyyy HH:mm:ss");
	    lang.put("general.day.pattern", "dd/MM/yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Cargando OpenKM");
	    lang.put("startup.starting.loading", "Comezando a carga de OpenKM");
	    lang.put("startup.taxonomy", "Obtendo o nodo principal da taxonomia");
	    lang.put("startup.template", "Obtendo o nodo principal dos modelos");
	    lang.put("startup.personal", "Obtenndo o nodo principal de persoal");
	    lang.put("startup.mail", "Otenenndo o nodo principal de correo");
	    lang.put("startup.trash", "Obtendo o nodo principal do lixo");
	    lang.put("startup.user.home", "Obtendo o inicio de usuarios");
	    lang.put("startup.bookmarks", "Obtendo marcadores");
	    lang.put("startup.loading.taxonomy", "Cargando taxonomia");
	    lang.put("startup.loading.taxonomy.getting.folders", "Cargando taxonomia - Obtendo cartafoles");
	    lang.put("startup.loading.taxonomy.eval.params", "Cargando taxonomia - evaluando parametros");
	    lang.put("startup.loading.taxonomy.open.path", "Cargando taxonomia - abrindo o camiño");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Cargando taxonomia - Obtendo cartafoles");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Cargando taxonomia - Obtendo documentos");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Cargando taxonomia - Obtendo correos electronicos");
	    lang.put("startup.loading.personal", "Cargando personais");
	    lang.put("startup.loading.mail", "Cargando correos");
	    lang.put("startup.loading.templates", "Cargando modelos");
	    lang.put("startup.loading.trash", "Carregando lixo");
	    lang.put("startup.loading.history.search", "Carregantdo histórico de procuras");
	    lang.put("startup.loading.user.values", "Carregando datos do usuario");
	    lang.put("startup.loading.property.group.translations", "Cargando traducions de grupo de propiedades");
	    lang.put("startup.keep.alive", "Cargando keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Actualización dispoñible de OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Taxonomía");
	    lang.put("leftpanel.label.trash", "Lixo");
	    lang.put("leftpanel.label.mail", "Correo electrónico");
	    lang.put("leftpanel.label.stored.search", "Procuras almacenadas");
	    lang.put("leftpanel.label.templates", "Modelos");
	    lang.put("leftpanel.label.my.documents", "Documentos personais");
	    lang.put("leftpanel.label.user.search", "Novedades de usuario");
	    lang.put("leftpanel.label.all.repository", "En todo el repositorio");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Crear cartafol");
	    lang.put("tree.menu.directory.remove", "Eliminar");
	    lang.put("tree.menu.directory.rename", "Renomear");
	    lang.put("tree.menu.directory.refresh", "Refrescar");
	    lang.put("tree.menu.directory.move", "Mover");
	    lang.put("tree.menu.directory.copy", "Copiar");
	    lang.put("tree.menu.directory.add.document", "Engadir documento");
	    lang.put("tree.menu.add.bookmark", "Engadir marcador");
	    lang.put("tree.menu.set.home", "Establecer inicio");
	    lang.put("tree.menu.export", "Exportar a ficheiro");
	    lang.put("tree.status.refresh.folder", "Actualizando arbore de cartafoles");
	    lang.put("tree.status.refresh.create", "Creando cartafol");
	    lang.put("tree.status.refresh.delete", "Eliminando cartafol");
	    lang.put("tree.status.refresh.rename", "Renomeando cartafol");
	    lang.put("tree.status.refresh.restore", "Restaurando cartafol");
	    lang.put("tree.status.refresh.purge", "Eliminando cartafol");
	    lang.put("tree.status.refresh.get", "Actualizando cartafol");
	    lang.put("tree.folder.new", "Novo cartafol");
	    lang.put("tree.status.refresh.add.subscription", "Establecendo suscrición");
	    lang.put("tree.status.refresh.remove.subscription", "Eliminando suscrición");
	    lang.put("tree.status.refresh.get.root", "Refrescando cartafol inicial");
	    lang.put("tree.status.refresh.get.user.home", "Obtendo inicio do usuario");
	    lang.put("tree.status.refresh.purge.trash", "Vaciando lixo");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Restaurar");
	    lang.put("trash.menu.directory.purge", "Eliminar");
	    lang.put("trash.menu.directory.purge.trash", "Vaciar lixo");
	    lang.put("trash.directory.select.label", "Seleccionar cartafol de destino");
	    
	    // General menu
	    lang.put("general.menu.file", "Archivo");
	    	lang.put("general.menu.file.exit", "Sair");
	    	lang.put("general.menu.file.purge.trash", "Vaciar lixo");
	    lang.put("general.menu.edit", "Edición");
			lang.put("general.menu.file.create.directory", "Crear cartafol");
			lang.put("general.menu.file.download.document", "Descargar documento");
			lang.put("general.menu.file.download.document.pdf", "Descargar documento como PDF");
			lang.put("general.menu.file.send.link", "Enviar ligazon do documento");
			lang.put("general.menu.file.lock", "Bloquear");
			lang.put("general.menu.file.unlock", "Desbloquear");
			lang.put("general.menu.file.add.document", "Engadir documento");
			lang.put("general.menu.file.checkout", "Checkout");
			lang.put("general.menu.file.checkin", "Checkin");
			lang.put("general.menu.file.cancel.checkout", "Cancelar checkout");
			lang.put("general.menu.file.delete", "Eliminar");
			lang.put("general.menu.file.refresh", "Refrescar");
			lang.put("general.menu.file.scanner", "Scanner");
	    lang.put("general.menu.tools", "Ferramentas");
	    	lang.put("general.menu.tools.languages", "Idiomas");
	    	lang.put("general.menu.tools.skin", "Apariencia");
    			lang.put("general.menu.tools.skin.default", "Por defecto");
    			lang.put("general.menu.tools.skin.default2", "Por defecto 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Fonte mediana");
    			lang.put("general.menu.tools.skin.bigfont", "Fonte grande");
    		lang.put("general.menu.debug.console", "Debug console");
    		lang.put("general.menu.administration", "Mostrar administración");
    		lang.put("general.menu.tools.preferences", "Prefererencias");
    			lang.put("general.menu.tools.user.preferences", "Configuración del usuario");
    	lang.put("general.menu.bookmark", "Marcadores");
	    	lang.put("general.menu.bookmark.home", "Inicio");
	    	lang.put("general.menu.bookmark.default.home", "Establecer inicio predeterminado");
	    	lang.put("general.menu.bookmark.edit", "Editar marcadores");
	    lang.put("general.menu.help", "Axuda");
		    lang.put("general.menu.bug.report", "Informe de bugs");
		    lang.put("general.menu.support.request", "Solicitude soporte");
		    lang.put("general.menu.public.forum", "Foro público");
		    lang.put("general.menu.project.web", "Web do proxecto");
		    lang.put("general.menu.documentation", "Documentación");
		    lang.put("general.menu.version.changes", "Notas da versión");
		    lang.put("general.menu.about", "Sobre OpenKM");
	    lang.put("general.connected", "Conectado como");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Nome");
	    lang.put("filebrowser.date.update", "Data de modificación");
	    lang.put("filebrowser.size", "Tamaño");
	    lang.put("filebrowser.path", "Camiño");
	    lang.put("filebrowser.author", "Autor");
	    lang.put("filebrowser.version", "Versión");
	    lang.put("filebrowser.menu.checkout", "Check-out");
	    lang.put("filebrowser.menu.checkin", "Check-in");
	    lang.put("filebrowser.menu.delete", "Eliminar");
	    lang.put("filebrowser.menu.rename", "Renomear");
	    lang.put("filebrowser.menu.checkout.cancel", "Cancelar check-out");
	    lang.put("filebrowser.menu.lock", "Bloquear");
	    lang.put("filebrowser.menu.unlock", "Desbloquear");
	    lang.put("filebrowser.menu.download", "Baixar");
	    lang.put("filebrowser.menu.move", "Mover");
	    lang.put("filebrowser.menu.copy", "Copiar");
	    lang.put("filebrowser.menu.create.from.template", "Crear desde modelo");
	    lang.put("filebrowser.menu.add.property.group", "Engadir grupo de propiedades");
	    lang.put("filebrowser.menu.remove.property.group", "Eliminar grupo de propiedades");
	    lang.put("filebrowser.menu.start.workflow", "Iniciar workflow");
	    lang.put("filebrowser.menu.add.subscription", "Engadir suscrición");
	    lang.put("filebrowser.menu.remove.subscription", "Eliminar suscrición");
	    lang.put("filebrowser.menu.add.bookmark", "Engadir marcador");
	    lang.put("filebrowser.menu.set.home", "Establecer inicio");
	    lang.put("filebrowser.menu.refresh", "Refrescar");
	    lang.put("filebrowser.menu.export", "Exportar a ZIP");
	    lang.put("filebrowser.menu.play", "Reproducir");
	    lang.put("filebrowser.menu.image.viewer", "Visor de imágenes");
	    lang.put("filebrowser.status.refresh.folder", "Actualizando lista de cartafoles");
	    lang.put("filebrowser.status.refresh.document", "Actualizando lista de documentos");
	    lang.put("filebrowser.status.refresh.mail", "Actualizando lista de correos electrónicos");
	    lang.put("filebrowser.status.refresh.delete.folder", "Eliminando cartafol");
	    lang.put("filebrowser.status.refresh.delete.document", "Eliminando documento");
	    lang.put("filebrowser.status.refresh.checkout", "Facendo checkout");
	    lang.put("filebrowser.status.refresh.lock", "Establecendo lock");
	    lang.put("filebrowser.status.refresh.unlock", "Eliminando lock");
	    lang.put("filebrowser.status.refresh.document.rename", "Renomeando documento");
	    lang.put("filebrowser.status.refresh.folder.rename", "Renomeando cartafol");
	    lang.put("filebrowser.status.refresh.document.purge", "Eliminando documento");
	    lang.put("filebrowser.status.refresh.folder.purge", "Eliminando cartafol");
	    lang.put("filebrowser.status.refresh.folder.get", "Actualizando cartafol");
	    lang.put("filebrowser.status.refresh.document.get", "Actualizando documento");
	    lang.put("filebrowser.status.refresh.add.subscription", "Establecendo suscrición");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Eliminando suscrición");
	    lang.put("filebrowser.status.refresh.get.user.home", "Obtendo inicio");
	    lang.put("filebrowser.status.refresh.delete.mail", "Eliminando correo electrónico");
	    lang.put("filebrowser.status.refresh.mail.purge", "Eliminando correo electrónico");
	    
	    // File Upload
	    lang.put("fileupload.send", "Enviar");
	    lang.put("fileupload.status.sending", "Subindo  ficheiro...");
	    lang.put("fileupload.status.indexing", "Indexando ficheiro...");
	    lang.put("fileupload.status.ok", "Envío completado correctamente");
	    lang.put("fileupload.upload.status", "Estado envio");
	    lang.put("fileupload.upload.uploaded", "Completado");
	    lang.put("fileupload.upload.completed", "Envio completado");
	    lang.put("fileupload.upload.runtime", "Tempo de execución");
	    lang.put("fileupload.upload.remaining", "falta");
	    lang.put("fileupload.button.close", "Pechar");
	    lang.put("fileupload.button.add.other.file", "Engadir outro ficheiro");
	    lang.put("fileupload.status.move.file", "Movendo o ficheiro...");
	    lang.put("fileupload.status.move.mail", "Movendo o correo electronico...");
	    lang.put("fileupload.status.copy.file", "Copiando o ficheiro...");
	    lang.put("fileupload.status.copy.mail", "Copiando o correo electronico...");
	    lang.put("fileupload.status.restore.file", "Restaurando o ficheiro...");
	    lang.put("fileupload.status.restore.mail", "Restaurando o correo electronico...");
	    lang.put("fileupload.status.move.folder", "Moviendo o cartafol...");
	    lang.put("fileupload.status.copy.folder", "Copiando o cartafol...");
	    lang.put("fileupload.status.restore.folder", "Restaurando o cartafol...");
	    lang.put("fileupload.status.create.from.template", "Creando o ficheiro desda modelo...");
	    lang.put("fileupload.status.of", "de");
	    lang.put("fileupload.label.insert", "Inserción de documentos");
	    lang.put("fileupload.label.update", "Actualización de documentos");
	    lang.put("fileupload.label.users.notify", "Notificar aos usuarios");
	    lang.put("fileupload.label.comment", "Comentario");
	    lang.put("fileupload.label.users.to.notify",  "Usuarios a notificar");
	    lang.put("fileupload.label.users",  "Usuarios");
	    lang.put("fileupload.label.must.select.users",  "Seleccione algún usuario ao que notificar");
	    lang.put("fileupload.label.importZip", "Importar documentos desde ZIP");
	    lang.put("fileupload.label.notify.comment", "Mensaxe de notificación");
	    lang.put("fileupload.label.error.importing.zip", "Erro ao importar o ficheiro");
	    lang.put("fileupload.label.error.move.file", "Erro moviendo o ficheiro");
	    lang.put("fileupload.label.error.move.mail", "Erro moviendo o correo electronico");
	    lang.put("fileupload.label.error.copy.file", "Erro copiando o ficheiro");
	    lang.put("fileupload.label.error.copy.mail", "Erro copiando o correo electronico");
	    lang.put("fileupload.label.error.restore.file", "Erro restaurando  ficheiro");
	    lang.put("fileupload.label.error.restore.mail", "Erro restaurando correo electronico");
	    lang.put("fileupload.label.error.move.folder", "Erro moviendo o cartafol");
	    lang.put("fileupload.label.error.copy.folder", "Erro copiando o cartafol");
	    lang.put("fileupload.label.error.restore.folder", "Erro restaurando o cartafol");
	    lang.put("fileupload.label.error.create.from.template", "Erro creando ficheiro desda modelo");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Non se pode mover no cartafol orixe ou fillos");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Non se pode copiar no cartafol orixe");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Non se pode crear un ficheiro no cartafol orixe");

	    // Tab properties
	    lang.put("tab.document.properties", "Propiedades");
	    lang.put("tab.document.notes", "Notas");
	    lang.put("tab.document.history", "Historial");
	    lang.put("tab.document.status.history", "Actualizando historial");
	    lang.put("tab.status.security.role", "Actualizando seguridade roles");
	    lang.put("tab.status.security.user", "Actualizando seguridade usuarios");
	    lang.put("tab.document.status.group.properties", "Actualizando grupo de propiedades");
	    lang.put("tab.document.status.set.keywords", "Establecendo palabras chave");
	    lang.put("tab.document.status.get.version.history.size", "Refrescando o tamaño do histórico do documento");
	    lang.put("tab.document.status.purge.version.history", "Compactando o histórico de documentos");
	    lang.put("tab.document.status.restore.version", "Restaurando a versión do documento");
	    lang.put("tab.document.security", "Seguridade");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Propiedades");
	    lang.put("tab.folder.security", "Seguridade");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Escritorio");
	    lang.put("tab.workspace.search", "Procurador");
	    lang.put("tab.workspace.dashboard", "Tablón");
	    lang.put("tab.workspace.administration", "Administración");
	    
	    // Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Nome");
	    lang.put("document.folder", "Cartafol");
	    lang.put("document.size", "Tamaño");
	    lang.put("document.created", "Creado");
	    lang.put("document.lastmodified", "Modificado");
	    lang.put("document.mimetype", "Tipo MIME");
	    lang.put("document.keywords", "Palablas chave");
	    lang.put("document.by", "por");
	    lang.put("document.status", "Estado");
	    lang.put("document.status.checkout", "Editado por");
	    lang.put("document.status.locked", "Bloqueado por");
	    lang.put("document.status.normal", "Dispoñible");
	    lang.put("document.subscribed", "Suscrito");
	    lang.put("document.subscribed.yes", "Si");
	    lang.put("document.subscribed.no", "Non");
	    lang.put("document.history.size", "Tamaño do histórico");
	    lang.put("document.subscribed.users", "Usuarios suscritos");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Añadir note");
	    lang.put("document.keywords.cloud", "Nube de palabras clave");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nome");
	    lang.put("folder.parent", "Pai");
	    lang.put("folder.created", "Creado");
	    lang.put("folder.by", "por");
	    lang.put("folder.subscribed", "Suscrito");
	    lang.put("folder.subscribed.yes", "Si");
	    lang.put("folder.subscribed.no", "Non");
	    lang.put("folder.subscribed.users", "Usuarios suscritos");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Versión");
	    lang.put("version.created", "Data");
	    lang.put("version.author", "Autor");
	    lang.put("version.size", "Tamaño");
	    lang.put("version.purge.document", "Compactar histórico");
	    lang.put("version.comment", "Comentario");
	    
	    // Security
	    lang.put("security.label", "Seguridade");
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
	    lang.put("error.label", "O sistema xerou un erro");
	    lang.put("error.invocation", "Erro de comunicación co servidor");
	    
	    // About
	    lang.put("about.label", "Sobre OpenKM");
	    lang.put("about.loading", "Cargando ...");
	    
	    // Logout
	    lang.put("logout.label", "Saír");
	    lang.put("logout.closed", "OpenKM pechouse correctamente.");
	    lang.put("logout.logout", "OpenKM estase pechando por favor agarde un momento");
	    
	    // Confirm
	    lang.put("confirm.label", "Confirmación");
	    lang.put("confirm.delete.folder", "¿ Seguro que desexa eliminar o cartafol ?");
	    lang.put("confirm.delete.document", "¿ Seguro que desexa eliminar o documento ?");
	    lang.put("confirm.delete.trash", "¿ Seguro que desexa vaciar o lixo ?");
	    lang.put("confirm.purge.folder", "¿ Seguro que desexa eliminar o cartafol ?");
	    lang.put("confirm.purge.document", "¿ Seguro que desexa eliminar o documento ?");
	    lang.put("confirm.delete.propety.group", "¿ Seguro que desexa eliminar o grupo de propiedades ?");
	    lang.put("confirm.purge.version.history.document", "¿ Seguro que desexa eliminar o histórico do documento ?");
	    lang.put("confirm.purge.restore.document", "¿ Seguro que desexa restaurar esta versión do histórico do documento ?");
	    lang.put("confirm.set.default.home", "¿ Seguro que desexa establecer o inicio por defecto ?");
	    lang.put("confirm.delete.saved.search", "¿ Seguro que desea eliminar la consulta almacenada ?");
	    lang.put("confirm.delete.user.news", "¿ Seguro que desea eliminar la novedad de usuario ?");
	    lang.put("confirm.delete.mail", "¿ Seguro que desexa eliminar o correo electronico ?");
	    lang.put("confirm.get.pooled.workflow.task","¿ Seguro que desexa asignarse esta tarea ?");
	    
	    // Search
	    lang.put("search.context", "Contexto");
	    lang.put("search.content", "Contido");
	    lang.put("search.name", "Nome");
	    lang.put("search.keywords", "Palabras chave");
	    lang.put("search.folder", "Carpeta");
	    lang.put("search.results", "Resultados");
	    lang.put("search.to", "ao");
	    lang.put("search.page.results", "Resultados por páxina");
	    lang.put("search.add.property.group", "Engadir propiedad de grupo");
	    lang.put("search.mimetype", "Tipo documento");
	    lang.put("search.type", "Tipo");
	    lang.put("search.type.document", "Documento");
	    lang.put("search.type.folder", "Carpeta");
	    lang.put("search.type.mail", "Correo electrónico");
	    lang.put("search.advanced", "Procura avanzada");
	    lang.put("search.user", "Usuario");
	    lang.put("search.date.and", "e");
	    lang.put("search.date.range", "Rango de datas entre");
	    lang.put("search.save.as.news", "Guardar como novedades de usuario");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filtrado por carpeta");
	    
	    // Search results
	    lang.put("search.result.name", "Nome");
	    lang.put("search.result.score", "Relevancia");
	    lang.put("search.result.size", "Tamaño");
	    lang.put("search.result.date.update", "Data de modificación");
	    lang.put("search.result.author", "Autor");
	    lang.put("search.result.version", "Versión");
	    lang.put("search.result.path", "Ubicación");
	    lang.put("search.result.menu.download", "Baixar");
	    lang.put("search.result.menu.go.folder", "Ir ao cartafol");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Executando procura");
	    lang.put("search.result.status.runsearch", "Executando procura almacenada");
	    
	    // Search saved
	    lang.put("search.saved.run", "Executar");
	    lang.put("search.saved.delete", "Eliminar");
	    lang.put("search.saved.status.getsearchs", "Actualizando procuras almacenadas");
	    lang.put("search.saved.status.savesearch", "Almacenando procura");
	    lang.put("search.saved.status.deletesearch", "Eliminando procura almacenada");
	    lang.put("search.saved.status.getusernews", "Actualizando las novedades de usuario");
	    
	    // Button
	    lang.put("button.close", "Pechar");
	    lang.put("button.logout", "Saír");
	    lang.put("button.update", "Actualizar");
	    lang.put("button.cancel", "Cancelar");
	    lang.put("button.accept", "Aceptar");
	    lang.put("button.restore", "Restaurar");
	    lang.put("button.move", "Mover");
	    lang.put("button.change", "Modificar");
	    lang.put("button.search", "Procurar");
	    lang.put("button.save.search", "Gardar a consulta");
	    lang.put("button.view", "Ver");
	    lang.put("button.clean", "Limpar");
	    lang.put("button.add", "Engadir");
	    lang.put("button.delete", "Eliminar");
	    lang.put("button.copy", "Copiar");
	    lang.put("button.create", "Crear");
	    lang.put("button.show", "Mostrar");
	    lang.put("button.memory", "Actualizar");
	    lang.put("button.copy.clipboard", "Copia al portapapeles");	
	    lang.put("button.start", "Iniciar");
	    lang.put("button.select", "Seleccionar");
	    
	    // Group
	    lang.put("group.label", "Engadir grupo de propiedad");
	    lang.put("group.group", "Grupo");
	    lang.put("group.property.group", "Propiedade");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Engadir marcador");
	    lang.put("bookmark.name", "Nome");
	    lang.put("bookmark.edit.label", "Edición de marcadores");
	    lang.put("bookmark.path", "Camiño");
	    lang.put("bookmark.type", "Tipo");
	    lang.put("bookmark.type.document", "Documento");
	    lang.put("bookmark.type.folder", "Cartafol");
	    
	    // Notify
	    lang.put("notify.label", "Enviar ligazon do documento");
	    
	    // Status
	    lang.put("status.document.copied", "Documento marcado para copiar");
	    lang.put("status.document.cut", "Documento marcado para recortar");
	    lang.put("status.folder.copied", "Cartafol marcado para copiar");
	    lang.put("status.folder.cut", "Cartafol marcado para recortar");
	    lang.put("status.keep.alive.error", "Detectada perdida de conexión co servidor (keep alive)");
	    lang.put("status.debug.enabled", "Debug activado");
	    lang.put("status.debug.disabled", "Debug desactivado");
	    lang.put("status.network.error.detected", "Detectado error de red");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Domingo");
	    lang.put("calendar.day.monday", "Luns");
	    lang.put("calendar.day.tuesday", "Martes");
	    lang.put("calendar.day.wednesday", "Mércores");
	    lang.put("calendar.day.thursday", "Xoves");
	    lang.put("calendar.day.friday", "Venres");
	    lang.put("calendar.day.saturday", "Sábado");
	    lang.put("calendar.month.january", "Xaneiro");
	    lang.put("calendar.month.february", "Febreiro");
	    lang.put("calendar.month.march", "Marzo");
	    lang.put("calendar.month.april", "Abril");
	    lang.put("calendar.month.may", "Maio");
	    lang.put("calendar.month.june", "Xuño");
	    lang.put("calendar.month.july", "Xullo");
	    lang.put("calendar.month.august", "Agosto");
	    lang.put("calendar.month.september", "Setembro");
	    lang.put("calendar.month.october", "Outubro");
	    lang.put("calendar.month.november", "Novembro");
	    lang.put("calendar.month.december", "Decembro");
	    
	    // Media player
	    lang.put("media.player.label", "Reproductor multimedia");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Visor de imágenes");
	    lang.put("image.viewer.zoom.in", "Ampliar");
	    lang.put("image.viewer.zoom.out", "Reducir");
	    
	    // Debug console
	    lang.put("debug.console.label", "Consola de depuración");
	    lang.put("debug.enable.disable", "CTRL+Z para activar o desactivar el modo debug");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "General");
	    lang.put("dashboard.tab.news", "Novedades");
	    lang.put("dashboard.tab.user", "Usuario");
	    lang.put("dashboard.tab.workflow", "Workflow");
	    lang.put("dashboard.tab.mail", "Correo");
	    lang.put("dashboard.tab.keymap", "Keyword map");

	    // Dahboard general
	    lang.put("dashboard.new.items", "Nuevos");
	    lang.put("dashboard.user.locked.documents", "Documentos bloqueados");
	    lang.put("dashboard.user.checkout.documents", "Documentos checkout");
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
	    lang.put("dashboard.workflow.pending.tasks.without.owner","Tareas pendientes no asignadas a un usuario");
	    lang.put("dashboard.workflow.task", "Tarea");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Nombre");
	    lang.put("dashboard.workflow.task.created", "Fecha de creación");
	    lang.put("dashboard.workflow.task.start", "Fecha de inicio");
	    lang.put("dashboard.workflow.task.duedate", "Fecha límite");
	    lang.put("dashboard.workflow.task.end", "Fecha de finalización");
	    lang.put("dashboard.workflow.task.description", "Descripción");
	    lang.put("dashboard.workflow.task.process.instance", "Instancia de proceso");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Versión");
	    lang.put("dashboard.workflow.task.process.name", "Nom");
	    lang.put("dashboard.workflow.task.process.description", "Descripción");
	    lang.put("dashboard.workflow.task.process.data", "Datos");
	    lang.put("dashboard.workflow.comments", "Commentarios");
	    lang.put("dashboard.workflow.task.process.forms", "Formulario");
	    lang.put("dashboard.workflow.add.comment","Añadir comentario");
	    lang.put("dashboard.workflow.task.process.definition", "Definición de proceso");
	    lang.put("dashboard.workflow.task.process.path", "Ruta");
	    lang.put("dashboard.refreshing", "Refrescando");
	    lang.put("dashboard.keyword", "Palabras clave");
	    lang.put("dashboard.keyword.suggest", "Escribe la palabra clave");
	    lang.put("dashboard.keyword.all", "Palabras clave");
	    lang.put("dashboard.keyword.top", "Palabras clave mas usadas");
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
	    lang.put("user.preferences.password.error", "Error las claves son distintas");
	    lang.put("user.preferences.user.data", "Cuenta de usuario");
	    lang.put("user.preferences.mail.data", "Cuenta de correo");
	    lang.put("user.preferences.imap.error", "Todos los campos son obligatorios para configurar el correo");
	    lang.put("user.preferences.imap.password.error.void", "La clave de IMAP no puede estar vacía en la creación");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Non ten permisos para acceder ao documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Non existe un documento con ese nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Xa existe un documento con ese nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Non se puido bloquear o documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Non se puido desbloquear o documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Erro interno do repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Erro interno da aplicación");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "O camiño do documento non existe");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Non ten permisos para acceder ao cartafol");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Non existe un cartafol con ese nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Xa existe un cartafol con ese nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Erro interno do repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Erro interno da aplicación");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "O camiño do cartafol non existe");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Non ten permisos para acceder ao elemento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Non existe un elemento con ese nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Erro interno do repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Erro interno da aplicación");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Non existe un documento con ese nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Erro interno da aplicación");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Tipo de ficheiro non soportado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Xa existe un documento con ese nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "O documento é demasiado grande");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Pechouse a sesión");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Generic error executing query");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "O nome da consulta almacenada debe ser único");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "O nome do marcador deber ser único");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Se ha perdido la sesión");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Erro interno do repositorio");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Erro interno do repositorio");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error en el dispositivo I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Erro interno do repositorio");
	  }
}
