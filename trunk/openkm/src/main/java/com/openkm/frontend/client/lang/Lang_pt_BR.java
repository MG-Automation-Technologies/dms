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
 * Portuguese (Brazil)
 * 
 * @author Heindrickson
 */
public class Lang_pt_BR {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd/MM/yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd/MM/yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Loading OpenKM");
	    lang.put("startup.starting.loading", "Starting loading OpenKM");
	    lang.put("startup.taxonomy", "Getting taxonomy root node");
	    lang.put("startup.categories", "Getting categories root node");
	    lang.put("startup.thesaurus", "Getting thesaurus root node");
	    lang.put("startup.template", "Getting template root node");
	    lang.put("startup.personal", "Getting personal root node");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Getting trash root node");
	    lang.put("startup.user.home", "Getting user home node");
	    lang.put("startup.bookmarks", "Getting bookmarks");
	    lang.put("startup.loading.taxonomy", "Loading taxonomy");
	    lang.put("startup.loading.taxonomy.getting.folders", "Loading taxonomy - getting folders");
	    lang.put("startup.loading.taxonomy.eval.params", "Loading taxonomy - eval browser params");
	    lang.put("startup.loading.taxonomy.open.path", "Loading taxonomy - open path");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Loading taxonomy - getting folders");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Loading taxonomy - getting documents");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Loading personal");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.categories", "Loading categories");
	    lang.put("startup.loading.thesaurus", "Loading thesaurus");
	    lang.put("startup.loading.templates", "Loading templates");
	    lang.put("startup.loading.trash", "Loading trash");
	    lang.put("startup.loading.history.search", "Loading history search");
	    lang.put("startup.loading.user.values", "Loading user values");
	    lang.put("startup.keep.alive", "Loading keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "OpenKM update available");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Taxonomia");
	    lang.put("leftpanel.label.trash", "Lixeira");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Pesquisas salvas");
	    lang.put("leftpanel.label.categories", "Categories");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Templates");
	    lang.put("leftpanel.label.my.documents", "My documents");
	    lang.put("leftpanel.label.user.search", "User news");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Criar pasta");
	    lang.put("tree.menu.directory.remove", "Eliminar");
	    lang.put("tree.menu.directory.rename", "Renomear");
	    lang.put("tree.menu.directory.refresh", "Atualizar");
	    lang.put("tree.menu.directory.move", "Mover");
	    lang.put("tree.menu.directory.copy", "Copiar");
	    lang.put("tree.menu.directory.add.document", "Adicionar documento");
	    lang.put("tree.menu.add.bookmark", "Add bookmark");
	    lang.put("tree.menu.set.home", "Set default home");
	    lang.put("tree.menu.export", "Export to file");
	    lang.put("tree.status.refresh.folder", "Atualizando lista de pastas");
	    lang.put("tree.status.refresh.create", "Criando pasta");
	    lang.put("tree.status.refresh.delete", "Eliminando pasta");
	    lang.put("tree.status.refresh.rename", "Renomeando pasta");
	    lang.put("tree.status.refresh.restore", "Restaurando pasta");
	    lang.put("tree.status.refresh.purge", "Eliminando pasta");
	    lang.put("tree.status.refresh.get", "Atualizando pasta");
	    lang.put("tree.folder.new", "Nova pasta");
	    lang.put("tree.status.refresh.add.subscription", "Adding subscription");
	    lang.put("tree.status.refresh.remove.subscription", "Deleting subscription");
	    lang.put("tree.status.refresh.get.root", "Refresing root folder");
	    lang.put("tree.status.refresh.get.keywords", "Refreshing keywords");
	    lang.put("tree.status.refresh.get.user.home", "Getting user home");
	    lang.put("tree.status.refresh.purge.trash", "Cleaning trash");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Restaurar");
	    lang.put("trash.menu.directory.purge", "Eliminar");
	    lang.put("trash.menu.directory.purge.trash", "Esvaziar lixeira");
	    lang.put("trash.directory.select.label", "Selecionar pasta de destino");
	    
	    // General menu
	    lang.put("general.menu.file", "Arquivo");
	    	lang.put("general.menu.file.exit", "Sair");
	    	lang.put("general.menu.file.purge.trash", "Esvaziar lixeira");
	    lang.put("general.menu.edit", "Editar");
			lang.put("general.menu.file.create.directory", "Criar pasta");
			lang.put("general.menu.file.download.document", "Baixar documento");
			lang.put("general.menu.file.download.document.pdf", "Baixar documento com PDF");
			lang.put("general.menu.file.send.link", "Send document link");
			lang.put("general.menu.file.lock", "Bloquear");
			lang.put("general.menu.file.unlock", "Desbloquear");
			lang.put("general.menu.file.add.document", "Adicionar documento");
			lang.put("general.menu.file.checkout", "Checkout");
			lang.put("general.menu.file.checkin", "Checkin");
			lang.put("general.menu.file.cancel.checkout", "Cancelar checkout");
			lang.put("general.menu.file.delete", "Eliminar");
			lang.put("general.menu.file.refresh", "Atualizar");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "File uploader");
	    lang.put("general.menu.tools", "Ferramentas");
	    	lang.put("general.menu.tools.languages", "Idiomas");
	    	lang.put("general.menu.tools.skin", "Pele (Skin)");
    			lang.put("general.menu.tools.skin.default", "Padrão");
    			lang.put("general.menu.tools.skin.default2", "Padrão 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Medium font");
    			lang.put("general.menu.tools.skin.bigfont", "Big font");
    		lang.put("general.menu.debug.console", "Debug console");
    		lang.put("general.menu.administration", "Show administration");
    		lang.put("general.menu.tools.preferences", "Prefererences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Bookmarks");
	    	lang.put("general.menu.bookmark.home", "Home");
	    	lang.put("general.menu.bookmark.default.home", "Set default home");
	    	lang.put("general.menu.bookmark.edit", "Edit bookmarks");
	    lang.put("general.menu.help", "Ajuda");
		    lang.put("general.menu.bug.report", "Reportar bug");
		    lang.put("general.menu.support.request", "Solicitar suporte");
		    lang.put("general.menu.public.forum", "Fórum público");
		    lang.put("general.menu.project.web", "Projeto na web");
		    lang.put("general.menu.version.changes", "Version notes");
		    lang.put("general.menu.documentation", "Documentação");
		    lang.put("general.menu.about", "Sobre OpenKM");
	    lang.put("general.connected", "Conectado como");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Nome");
	    lang.put("filebrowser.date.update", "Data de modificação");
	    lang.put("filebrowser.size", "Tamanho");
	    lang.put("filebrowser.path", "Caminho");
	    lang.put("filebrowser.author", "Autor");
	    lang.put("filebrowser.version", "Versão");
	    lang.put("filebrowser.menu.checkout", "Check-out");
	    lang.put("filebrowser.menu.checkin", "Check-in");
	    lang.put("filebrowser.menu.delete", "Eliminar");
	    lang.put("filebrowser.menu.rename", "Renomear");
	    lang.put("filebrowser.menu.checkout.cancel", "Cancelar check-out");
	    lang.put("filebrowser.menu.lock", "Bloquear");
	    lang.put("filebrowser.menu.unlock", "Desbloquear");
	    lang.put("filebrowser.menu.download", "Baixar");
	    lang.put("filebrowser.menu.move", "Mover");
	    lang.put("filebrowser.menu.copy", "Copy");
	    lang.put("filebrowser.menu.create.from.template", "Create from template");
	    lang.put("filebrowser.menu.add.property.group", "Add property group");
	    lang.put("filebrowser.menu.remove.property.group", "Remove property group");
	    lang.put("filebrowser.menu.start.workflow", "Start workflow");
	    lang.put("filebrowser.menu.add.subscription", "Add subscription");
	    lang.put("filebrowser.menu.remove.subscription", "Remove subscrition");
	    lang.put("filebrowser.menu.add.bookmark", "Add bookmark");
	    lang.put("filebrowser.menu.set.home", "Set default home");
	    lang.put("filebrowser.menu.refresh", "Atualizar");
	    lang.put("filebrowser.menu.export", "Export to ZIP");
	    lang.put("filebrowser.menu.play", "Play");
	    lang.put("filebrowser.menu.image.viewer", "Image viewer");
	    lang.put("filebrowser.status.refresh.folder", "Atualizando lista de pastas");
	    lang.put("filebrowser.status.refresh.document", "Atualizando lista de documentos");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Eliminando pasta");
	    lang.put("filebrowser.status.refresh.delete.document", "Eliminando documento");
	    lang.put("filebrowser.status.refresh.checkout",  "Realizando checkout");
	    lang.put("filebrowser.status.refresh.lock",  "Bloqueando documento");
	    lang.put("filebrowser.status.refresh.unlock", "Desbloqueando documento");
	    lang.put("filebrowser.status.refresh.document.rename", "Renomeando documento");
	    lang.put("filebrowser.status.refresh.folder.rename", "Renomeando pasta");
	    lang.put("filebrowser.status.refresh.document.purge", "Eliminando documento");
	    lang.put("filebrowser.status.refresh.folder.purge", "Eliminando pasta");
	    lang.put("filebrowser.status.refresh.folder.get", "Atualizando pasta");
	    lang.put("filebrowser.status.refresh.document.get", "Atualizando documento");
	    lang.put("filebrowser.status.refresh.add.subscription", "Adding subscription");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Deleting subscription");
	    lang.put("filebrowser.status.refresh.get.user.home", "Getting user home");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Enviar");
	    lang.put("fileupload.status.sending", "Enviando arquivo...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "Arquivo enviado corretamente");
	    lang.put("fileupload.upload.status", "Estado do envio");
	    lang.put("fileupload.upload.uploaded", "Enviado");
	    lang.put("fileupload.upload.completed", "Envio completado");
	    lang.put("fileupload.upload.runtime", "Tempo de execução");
	    lang.put("fileupload.upload.remaining", "Falta");
	    lang.put("fileupload.button.close", "Fechar");
	    lang.put("fileupload.button.add.other.file", "Add other document");
	    lang.put("fileupload.status.move.file", "Moving file...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Coping file...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "Restoring file...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Moving folder...");
	    lang.put("fileupload.status.copy.folder", "Coping folder...");
	    lang.put("fileupload.status.restore.folder", "Restoring folder...");
	    lang.put("fileupload.status.create.from.template", "Creating file from template...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Inserção de documentos");
	    lang.put("fileupload.label.update", "Atualização de documentos");
	    lang.put("fileupload.label.users.notify", "Notify to users");
	    lang.put("fileupload.label.comment", "Comment");
	    lang.put("fileupload.label.users.to.notify",  "Users be notified");
	    lang.put("fileupload.label.users",  "Users");
	    lang.put("fileupload.label.must.select.users",  "You must select some user to notify");
	    lang.put("fileupload.label.importZip", "Import Documents from ZIP");
	    lang.put("fileupload.label.notify.comment", "Notification message");
	    lang.put("fileupload.label.error.importing.zip", "Error importing file");
	    lang.put("fileupload.label.error.move.file", "Error moving file");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Error coping file");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "Error restoring file");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Error moving folder");
	    lang.put("fileupload.label.error.copy.folder", "Error coping folder");
	    lang.put("fileupload.label.error.restore.folder", "Error restoring folder");
	    lang.put("fileupload.label.error.create.from.template", "Error creating file from template");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Not allowed to move on origin or child folder");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Not allowed to move on origin folder");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Not allowed to create a file on origin folder");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Propriedades");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "Histórico");
	    lang.put("tab.document.status.history", "Atualizando histórico");
	    lang.put("tab.status.security.role", "Atualizando grupos");
	    lang.put("tab.status.security.user", "Atualizando usuários");
	    lang.put("tab.document.status.group.properties", "Updating property group");
	    lang.put("tab.document.status.set.keywords", "Setting keywords");
	    lang.put("tab.document.status.set.categories", "Updating categories");
	    lang.put("tab.document.status.get.version.history.size", "Refreshing document history size");
	    lang.put("tab.document.status.purge.version.history", "Compacting document history");
	    lang.put("tab.document.status.restore.version", "Restoring document versión");
	    lang.put("tab.document.security", "Segurança");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Propriedades");
	    lang.put("tab.folder.security", "Segurança");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Área de trabalho");
	    lang.put("tab.workspace.search", "Pesquisa");
	    lang.put("tab.workspace.dashboard", "DashBoard");
	    lang.put("tab.workspace.administration", "Administration");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Nome");
	    lang.put("document.folder", "Pasta");
	    lang.put("document.size", "Tamanho");
	    lang.put("document.created", "Criado");
	    lang.put("document.lastmodified", "Modificado");
	    lang.put("document.mimetype", "Tipo MIME");
	    lang.put("document.keywords", "Palavras chaves");
	    lang.put("document.by", "por");
	    lang.put("document.status", "Estado");
	    lang.put("document.status.checkout", "Editado por");
	    lang.put("document.status.locked", "Bloqueado por");
	    lang.put("document.status.normal", "Disponível");
	    lang.put("document.subscribed", "Subscribed");
	    lang.put("document.subscribed.yes", "Yes");
	    lang.put("document.subscribed.no", "No");
	    lang.put("document.history.size", "History size");
	    lang.put("document.subscribed.users", "Subscribed users");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Add note");
	    lang.put("document.keywords.cloud", "Keywords cloud");
	    lang.put("document.categories", "Categories");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nome");
	    lang.put("folder.parent", "Pai");
	    lang.put("folder.created", "Criado");
	    lang.put("folder.by", "por");
	    lang.put("folder.subscribed", "Subscribed");
	    lang.put("folder.subscribed.yes", "Yes");
	    lang.put("folder.subscribed.no", "No");
	    lang.put("folder.subscribed.users", "Subscribed users");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Versão");
	    lang.put("version.created", "Data");
	    lang.put("version.author", "Autor");
	    lang.put("version.size", "Tamanho");
	    lang.put("version.purge.document", "Compact history");
	    lang.put("version.comment", "Comment");
	    
	    // Security
	    lang.put("security.label", "Segurança");
	    lang.put("security.group.name", "Grupo");
	    lang.put("security.group.permission.read", "Leitura");
	    lang.put("security.group.permission.write", "Escrita");
	    lang.put("security.user.name", "Usuário");
	    lang.put("security.user.permission.read", "Leitura");
	    lang.put("security.user.permission.write", "Escrita");
	    lang.put("security.users", "Usuários");
	    lang.put("security.groups", "Grupos");
	    lang.put("security.recursive", "Aplicar alterações de forma recursiva");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "Erro do sistema");
	    lang.put("error.invocation", "Erro de comunicação com o servidor");
	    
	    // About
	    lang.put("about.label", "Sobre OpenKM");
	    lang.put("about.loading", "Carregando ...");
	    
	    // Logout
	    lang.put("logout.label", "Sair");
	    lang.put("logout.closed", "OpenKM foi encerrado corretamente.");
	    lang.put("logout.logout", "OpenKM is logging out, please wait");
	    
	    // Confirm
	    lang.put("confirm.label", "Confirmação");
	    lang.put("confirm.delete.folder", "Tem certeza que deseja eliminar a pasta ?");
	    lang.put("confirm.delete.document", "Tem certeza que deseja eliminar o documento ?");
	    lang.put("confirm.delete.trash", "Tem certeza que deseja esvaziar a lixeira ?");
	    lang.put("confirm.purge.folder", "Tem certeza que deseja eliminar a pasta ?");
	    lang.put("confirm.purge.document", "Tem certeza que deseja eliminar o documento ?");
	    lang.put("confirm.delete.propety.group", "¿ Do you really want to delete propety group ?");
	    lang.put("confirm.purge.version.history.document", "¿ Do you really want to delete document history ?");
	    lang.put("confirm.purge.restore.document", "¿ Do you really want to restore to this document version ?");
	    lang.put("confirm.set.default.home", "¿ Do you really want to set default home ?");
	    lang.put("confirm.delete.saved.search", "¿ Do you really want to delete saved search ?");
	    lang.put("confirm.delete.user.news", "¿ Do you really want to delete user news ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    
	    // Search inputs
	    lang.put("search.context", "Context");
	    lang.put("search.content", "Conteúdo");
	    lang.put("search.name", "Nome");
	    lang.put("search.keywords", "Palavras chaves");
	    lang.put("search.folder", "Folder");
	    lang.put("search.category", "Category");
	    lang.put("search.results", "Results");
	    lang.put("search.to", "to");
	    lang.put("search.page.results", "Page results");
	    lang.put("search.add.property.group", "Add property group");
	    lang.put("search.mimetype", "Mime type");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Pesquisa avançada");
	    lang.put("search.user", "Usuário");
	    lang.put("search.date.and", "and");
	    lang.put("search.date.range", "Date range between");
	    lang.put("search.save.as.news", "Save as user news");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    lang.put("search.category.filter", "Filter by category");
	    
	    // Search results
	    lang.put("search.result.name", "Nome");
	    lang.put("search.result.score", "Relevance");
	    lang.put("search.result.size", "Tamanho");
	    lang.put("search.result.date.update", "Data de modificação");
	    lang.put("search.result.author", "Autor");
	    lang.put("search.result.version", "Versão");
	    lang.put("search.result.path", "Caminho");
	    lang.put("search.result.menu.download", "Download");
	    lang.put("search.result.menu.go.folder", "Ir para a pasta");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Executando pesquisa");
	    lang.put("search.result.status.runsearch", "Executando pesquisa salva");
	    
	    // Search saved
	    lang.put("search.saved.run", "Executar");
	    lang.put("search.saved.delete", "Eliminar");
	    lang.put("search.saved.status.getsearchs", "Atualizando pesquisas salvas");
	    lang.put("search.saved.status.savesearch", "Salvando pesquisa");
	    lang.put("search.saved.status.deletesearch", "Eliminando pesquisa salva");
	    lang.put("search.saved.status.getusernews", "Refreshing user news");
	    
	    // Button
	    lang.put("button.close", "Fechar");
	    lang.put("button.logout", "Sair");
	    lang.put("button.update", "Alterar");
	    lang.put("button.cancel", "Cancelar");
	    lang.put("button.accept", "Aceitar");
	    lang.put("button.restore", "Restaurar");
	    lang.put("button.move", "Mover");
	    lang.put("button.change", "Modificar");
	    lang.put("button.search", "Pesquisar");
	    lang.put("button.save.search", "Salvar pesquisa");
	    lang.put("button.view", "Ver");
	    lang.put("button.clean", "Clean");
	    lang.put("button.add", "Add");
	    lang.put("button.delete", "Delete");
	    lang.put("button.copy", "Copy");
	    lang.put("button.create", "Create");
	    lang.put("button.show", "Show");
	    lang.put("button.memory", "Alterar");
	    lang.put("button.copy.clipboard", "Copy to clipboard");	
	    lang.put("button.start", "Start");
	    lang.put("button.select", "Select");
	    
	    // Group
	    lang.put("group.label", "Add property group");
	    lang.put("group.group", "Group");
	    lang.put("group.property.group", "Property");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Add bookmark");
	    lang.put("bookmark.name", "Name");
	    lang.put("bookmark.edit.label", "Edit bookmarks");
	    lang.put("bookmark.path", "Path");
	    lang.put("bookmark.type", "Type");
	    lang.put("bookmark.type.document", "Document");
	    lang.put("bookmark.type.folder", "Folder");
	    
	    // Notify
	    lang.put("notify.label", "Sending document link");
	    
	    // Status
	    lang.put("status.document.copied", "Document marked to copy");
	    lang.put("status.document.cut", "Document market to cut");
	    lang.put("status.folder.copied", "Folder marked to copy");
	    lang.put("status.folder.cut", "Folder market to cut");
	    lang.put("status.keep.alive.error", "Detected lost connection to server (keep alive)");
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

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Add thesaurus keyword");
	    lang.put("thesaurus.tab.tree", "Tree");
	    lang.put("thesaurus.tab.keywords", "Keywords");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Add category");
	    lang.put("categories.folder.error.delete", "Can not delete category with documents");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Sem permissão para acessar o documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Impossível bloquear o documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Version error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Sem permissão para acessar o documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Não existe um documento com esse nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Já existe um documento com esse nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Impossível bloquear o documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Impossível desbloquear o documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Erro interno da aplicação");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Sem permissão para acessar a pasta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Não existe uma pasta com esse nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Já existe uma pasta com esse nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Erro interno da aplicação");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Folder path not found");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Sem permissão para acessar o elemento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Não existe um elemento com esse nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Erro interno da aplicação");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Não existe um documento com esse nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Erro interno da aplicação");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Formato de arquivo não suportado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Já existe um documento com esse nome");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "O documento é muito grande");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "A sessão foi encerrada");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Generic error executing query");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "The stored search name must be unique");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "The bookmark name must be unique");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Session lost");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error in browser configuration");
	  }
}
