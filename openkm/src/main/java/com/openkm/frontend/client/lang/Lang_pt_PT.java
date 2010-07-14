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
 * Portuguese (European)
 * 
 * @author Ricardo Marques
 */
public class Lang_pt_PT {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd/MM/yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd/MM/yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "A carregar o OpenKM");
	    lang.put("startup.starting.loading", "A preparar o OpenKM");
	    lang.put("startup.taxonomy", "A carregar a taxonomia");
	    lang.put("startup.categories", "A carregar as categorias");
	    lang.put("startup.thesaurus", "A carregar o léxico");
	    lang.put("startup.template", "A carregar os templates");
	    lang.put("startup.personal", "A carregar ficheiros pessoais");
	    lang.put("startup.mail", "A carregar as mensagens");
	    lang.put("startup.trash", "A carregar a reciclagem");
	    lang.put("startup.user.home", "A carregar a área pessoal");
	    lang.put("startup.bookmarks", "A carregar os marcadores");
	    lang.put("startup.loading.taxonomy", "A carregar a taxonomia");
	    lang.put("startup.loading.taxonomy.getting.folders", "A carregar taxonomia - A obter pastas");
	    lang.put("startup.loading.taxonomy.eval.params", "A carregar taxonomia - A verificar navegador");
	    lang.put("startup.loading.taxonomy.open.path", "A carregar taxonomia - A abrir caminho");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "A carregar taxonomia - a obter pastas");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "A carregar taxonomia - a obter os documentos");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "A carregar taxonomia - A obter mensagens");
	    lang.put("startup.loading.personal", "A carregar área pessoal");
	    lang.put("startup.loading.mail", "A carregar mensagens");
	    lang.put("startup.loading.categories", "A carregar categorias");
	    lang.put("startup.loading.thesaurus", "A carregar o léxico");
	    lang.put("startup.loading.templates", "A carregar templates");
	    lang.put("startup.loading.trash", "A carregar a reciclagem");
	    lang.put("startup.loading.history.search", "A carregar o histórico da pesquisa");
	    lang.put("startup.loading.user.values", "A carregar os valores do utilizador");
	    lang.put("startup.keep.alive", "A carregar o keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Actualização do OpenKM disponível");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Taxonomia");
	    lang.put("leftpanel.label.trash", "Reciclagem");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Pesquisas guardadas");
	    lang.put("leftpanel.label.categories", "Categorias");
	    lang.put("leftpanel.label.thesaurus", "Léxico");
	    lang.put("leftpanel.label.templates", "Modelos");
	    lang.put("leftpanel.label.my.documents", "Documentos pessoais");
	    lang.put("leftpanel.label.user.search", "Notícias");
	    lang.put("leftpanel.label.all.repository", "Repositório");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Criar pasta");
	    lang.put("tree.menu.directory.remove", "Eliminar");
	    lang.put("tree.menu.directory.rename", "Mudar o nome");
	    lang.put("tree.menu.directory.refresh", "Actualizar");
	    lang.put("tree.menu.directory.move", "Mover");
	    lang.put("tree.menu.directory.copy", "Copiar");
	    lang.put("tree.menu.directory.add.document", "Adicionar documento");
	    lang.put("tree.menu.add.bookmark", "Adicionar marcador");
	    lang.put("tree.menu.set.home", "Definir como pasta inicial");
	    lang.put("tree.menu.export", "Exportar para ficheiro");
	    lang.put("tree.status.refresh.folder", "A actualizar lista de pastas");
	    lang.put("tree.status.refresh.create", "A criar pasta");
	    lang.put("tree.status.refresh.delete", "A eliminar pasta");
	    lang.put("tree.status.refresh.rename", "A renomear pasta");
	    lang.put("tree.status.refresh.restore", "A restaurar pasta");
	    lang.put("tree.status.refresh.purge", "A eliminar pasta");
	    lang.put("tree.status.refresh.get", "A actualizar pasta");
	    lang.put("tree.folder.new", "Nova pasta");
	    lang.put("tree.status.refresh.add.subscription", "A adicionar subscrição");
	    lang.put("tree.status.refresh.remove.subscription", "A eliminar subscrição");
	    lang.put("tree.status.refresh.get.root", "A actualizar pastas");
	    lang.put("tree.status.refresh.get.keywords", "A actualizar palavras-chave");
	    lang.put("tree.status.refresh.get.user.home", "A carregar a área pessoal");
	    lang.put("tree.status.refresh.purge.trash", "Esvaziar reciclagem");
	    lang.put("tree.menu.directory.find.folder","Find folder");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Restaurar");
	    lang.put("trash.menu.directory.purge", "Eliminar");
	    lang.put("trash.menu.directory.purge.trash", "Esvaziar reciclagem");
	    lang.put("trash.directory.select.label", "Seleccionar pasta de destino");
	    
	    // General menu
	    lang.put("general.menu.file", "Ficheiro");
	    	lang.put("general.menu.file.exit", "Sair");
	    	lang.put("general.menu.file.purge.trash", "Esvaziar reciclagem");
	    lang.put("general.menu.edit", "Editar");
			lang.put("general.menu.file.create.directory", "Criar pasta");
			lang.put("general.menu.file.download.document", "Descarregar documento");
			lang.put("general.menu.file.download.document.pdf", "Descarregar documento como PDF");
			lang.put("general.menu.file.send.link", "Enviar hiperligação do documento");
			lang.put("general.menu.file.send.attachment", "Send document attachment");
			lang.put("general.menu.file.lock", "Bloquear");
			lang.put("general.menu.file.unlock", "Desbloquear");
			lang.put("general.menu.file.add.document", "Adicionar documento");
			lang.put("general.menu.file.checkout", "Checkout");
			lang.put("general.menu.file.checkin", "Checkin");
			lang.put("general.menu.file.cancel.checkout", "Cancelar checkout");
			lang.put("general.menu.file.delete", "Eliminar");
			lang.put("general.menu.file.refresh", "Actualizar");
			lang.put("general.menu.file.scanner", "Digitalizador");
			lang.put("general.menu.file.uploader", "Enviar ficheiros");
	    lang.put("general.menu.tools", "Ferramentas");
	    	lang.put("general.menu.tools.languages", "Idiomas");
	    	lang.put("general.menu.tools.skin", "Temas de ambiente");
    			lang.put("general.menu.tools.skin.default", "Padrão");
    			lang.put("general.menu.tools.skin.default2", "Padrão 2");
    			lang.put("general.menu.tools.skin.mediumfont", "tamanho de fonte média");
    			lang.put("general.menu.tools.skin.bigfont", "tamanho de fonte grande");
    		lang.put("general.menu.debug.console", "Consola de depuração");
    		lang.put("general.menu.administration", "Mostrar administração");
    		lang.put("general.menu.tools.preferences", "Preferências");
    			lang.put("general.menu.tools.user.preferences", "Configuração do utilizador");
    	lang.put("general.menu.bookmark", "marcadores");
	    	lang.put("general.menu.bookmark.home", "Pasta inicial");
	    	lang.put("general.menu.bookmark.default.home", "Definir como pasta inicial");
	    	lang.put("general.menu.bookmark.edit", "Editar marcadores");
	    lang.put("general.menu.help", "Ajuda");
		    lang.put("general.menu.bug.report", "Comunicar erro");
		    lang.put("general.menu.support.request", "Solicitar suporte");
		    lang.put("general.menu.public.forum", "Fórum público");
		    lang.put("general.menu.project.web", "Página na internet");
		    lang.put("general.menu.version.changes", "Notas da versão");
		    lang.put("general.menu.documentation", "Documentação");
		    lang.put("general.menu.about", "Sobre o OpenKM");
	    lang.put("general.connected", "Ligado como");
	    
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
	    lang.put("filebrowser.menu.download", "Descarregar");
	    lang.put("filebrowser.menu.move", "Mover");
	    lang.put("filebrowser.menu.copy", "Copiar");
	    lang.put("filebrowser.menu.create.from.template", "Criar a partir do modelo");
	    lang.put("filebrowser.menu.add.property.group", "Adicionar grupo de propriedades");
	    lang.put("filebrowser.menu.remove.property.group", "Remover grupo de propriedades");
	    lang.put("filebrowser.menu.start.workflow", "Iniciar workflow");
	    lang.put("filebrowser.menu.add.subscription", "Adicionar subscrição");
	    lang.put("filebrowser.menu.remove.subscription", "Remover subscrição");
	    lang.put("filebrowser.menu.add.bookmark", "Adicionar marcador");
	    lang.put("filebrowser.menu.set.home", "Definir como pasta inicial");
	    lang.put("filebrowser.menu.refresh", "Actualizar");
	    lang.put("filebrowser.menu.export", "Exportar para ZIP");
	    lang.put("filebrowser.menu.play", "Reproduzir");
	    lang.put("filebrowser.menu.image.viewer", "Visualizador de imagens");
	    lang.put("filebrowser.status.refresh.folder", "A actualizar lista de pastas");
	    lang.put("filebrowser.status.refresh.document", "A actualizar lista de documentos");
	    lang.put("filebrowser.status.refresh.mail", "A actualizar lista de mensagens");
	    lang.put("filebrowser.status.refresh.delete.folder", "A eliminar pasta");
	    lang.put("filebrowser.status.refresh.delete.document", "A eliminar documento");
	    lang.put("filebrowser.status.refresh.checkout",  "A fazer checkout");
	    lang.put("filebrowser.status.refresh.lock",  "A bloquear documento");
	    lang.put("filebrowser.status.refresh.unlock", "A desbloquear documento");
	    lang.put("filebrowser.status.refresh.document.rename", "A renomear documento");
	    lang.put("filebrowser.status.refresh.folder.rename", "A renomear pasta");
	    lang.put("filebrowser.status.refresh.document.purge", "A eliminar documento");
	    lang.put("filebrowser.status.refresh.folder.purge", "A eliminar pasta");
	    lang.put("filebrowser.status.refresh.folder.get", "A actualizar pasta");
	    lang.put("filebrowser.status.refresh.document.get", "A actualizar documento");
	    lang.put("filebrowser.status.refresh.add.subscription", "A adicionar subscrição");
	    lang.put("filebrowser.status.refresh.remove.subscription", "A eliminar subscrição");
	    lang.put("filebrowser.status.refresh.get.user.home", "A obter a área do utilizador");
	    lang.put("filebrowser.status.refresh.delete.mail", "A eliminar mensagem");
	    lang.put("filebrowser.status.refresh.mail.purge", "A eliminar mensagem");
	    
	    // File Upload
	    lang.put("fileupload.send", "Enviar");
	    lang.put("fileupload.status.sending", "A enviar ficheiro...");
	    lang.put("fileupload.status.indexing", "A indexar ficheiro...");
	    lang.put("fileupload.status.ok", "Ficheiro enviado correctamente");
	    lang.put("fileupload.upload.status", "Estado de envio");
	    lang.put("fileupload.upload.uploaded", "Enviado");
	    lang.put("fileupload.upload.completed", "Envio concluído");
	    lang.put("fileupload.upload.runtime", "Tempo de envio");
	    lang.put("fileupload.upload.remaining", "Falta");
	    lang.put("fileupload.button.close", "Fechar");
	    lang.put("fileupload.button.add.other.file", "Adicionar outro documento");
	    lang.put("fileupload.status.move.file", "A mover ficheiro...");
	    lang.put("fileupload.status.move.mail", "A mover mensagem...");
	    lang.put("fileupload.status.copy.file", "A copiar ficheiro...");
	    lang.put("fileupload.status.copy.mail", "A copiar mensagem...");
	    lang.put("fileupload.status.restore.file", "A restaurar ficheiro...");
	    lang.put("fileupload.status.restore.mail", "A restaurar mensagem...");
	    lang.put("fileupload.status.move.folder", "A mover pasta...");
	    lang.put("fileupload.status.copy.folder", "A copiar pasta...");
	    lang.put("fileupload.status.restore.folder", "A restaurar pasta...");
	    lang.put("fileupload.status.create.from.template", "A criar documento a partir do modelo...");
	    lang.put("fileupload.status.of", "de");
	    lang.put("fileupload.label.insert", "Inserção de documentos");
	    lang.put("fileupload.label.update", "Actualização de documentos");
	    lang.put("fileupload.label.users.notify", "Notificar utilizadores");
	    lang.put("fileupload.label.comment", "Comentar");
	    lang.put("fileupload.label.users.to.notify",  "A notificar");
	    lang.put("fileupload.label.users",  "Utilizadores");
	    lang.put("fileupload.label.groups.to.notify","Groups to notify");
	    lang.put("fileupload.label.groups","Groups");
	    lang.put("fileupload.label.must.select.users",  "Deve escolher pelo menos um utilizador para notificar");
	    lang.put("fileupload.label.importZip", "Importar documentos a partir de ZIP");
	    lang.put("fileupload.label.notify.comment", "Mensagem de notificação");
	    lang.put("fileupload.label.error.importing.zip", "Erro ao importar ficheiro");
	    lang.put("fileupload.label.error.move.file", "Erro ao mover documento");
	    lang.put("fileupload.label.error.move.mail", "Erro ao mover mensagem");
	    lang.put("fileupload.label.error.copy.file", "Erro ao copiar documento");
	    lang.put("fileupload.label.error.copy.mail", "Erro ao copiar mensagem");
	    lang.put("fileupload.label.error.restore.file", "Erro ao restaurar documento");
	    lang.put("fileupload.label.error.restore.mail", "Erro ao restaurar mensagem");
	    lang.put("fileupload.label.error.move.folder", "Erro ao mover documento");
	    lang.put("fileupload.label.error.copy.folder", "Erro ao copiar pasta");
	    lang.put("fileupload.label.error.restore.folder", "Erro ao restaurar pasta");
	    lang.put("fileupload.label.error.create.from.template", "Erro ao criar documento a partir do modelo");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Não é permitido mover para este local");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Não é permitido mover para este local");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Não é permitido criar ficheiro na pasta de origem");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Propriedades");
	    lang.put("tab.document.notes", "Notas");
	    lang.put("tab.document.history", "Histórico");
	    lang.put("tab.document.status.history", "A actualizar histórico");
	    lang.put("tab.status.security.role", "A actualizar grupos");
	    lang.put("tab.status.security.user", "A actualizar utilizadores");
	    lang.put("tab.document.status.group.properties", "A actualizar grupo de propriedades");
	    lang.put("tab.document.status.set.keywords", "A definir palavras-chave");
	    lang.put("tab.document.status.set.categories", "A actualizar categorias");
	    lang.put("tab.document.status.get.version.history.size", "A actualizar dimensão do histórico do documento");
	    lang.put("tab.document.status.purge.version.history", "A compactar histórico do documento");
	    lang.put("tab.document.status.restore.version", "A restaurar a versão do documento");
	    lang.put("tab.document.security", "Segurança");
	    lang.put("tab.document.preview", "Pré-visualizar");
	    lang.put("tab.folder.properties", "Propriedades");
	    lang.put("tab.folder.security", "Segurança");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Área de trabalho");
	    lang.put("tab.workspace.search", "Pesquisa");
	    lang.put("tab.workspace.dashboard", "DashBoard");
	    lang.put("tab.workspace.administration", "Administração");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Nome");
	    lang.put("document.folder", "Pasta");
	    lang.put("document.size", "Tamanho");
	    lang.put("document.created", "Criado");
	    lang.put("document.lastmodified", "Modificado");
	    lang.put("document.mimetype", "Tipo MIME");
	    lang.put("document.keywords", "Palavras-chave");
	    lang.put("document.by", "por");
	    lang.put("document.status", "Estado");
	    lang.put("document.status.checkout", "Editado por");
	    lang.put("document.status.locked", "Bloqueado por");
	    lang.put("document.status.normal", "Disponí­vel");
	    lang.put("document.subscribed", "Subscrito");
	    lang.put("document.subscribed.yes", "Sim");
	    lang.put("document.subscribed.no", "Não");
	    lang.put("document.history.size", "Dimensão do histórico");
	    lang.put("document.subscribed.users", "Utilizadores subscritos");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Adicionar nota");
	    lang.put("document.keywords.cloud", "Palavras-chave");
	    lang.put("document.categories", "Categorias");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nome");
	    lang.put("folder.parent", "Na pasta");
	    lang.put("folder.created", "Criado");
	    lang.put("folder.by", "por");
	    lang.put("folder.subscribed", "Subscrito");
	    lang.put("folder.subscribed.yes", "Sim");
	    lang.put("folder.subscribed.no", "Não");
	    lang.put("folder.subscribed.users", "Utilizadores subscritos");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Folders");
	    lang.put("folder.number.documents", "Documents");
	    lang.put("folder.number.mails", "Mails");
	    
	    // Version
	    lang.put("version.name", "Versão");
	    lang.put("version.created", "Data");
	    lang.put("version.author", "Autor");
	    lang.put("version.size", "Tamanho");
	    lang.put("version.purge.document", "Compactar histórico");
	    lang.put("version.comment", "Comentário");
	    
	    // Security
	    lang.put("security.label", "Segurança");
	    lang.put("security.group.name", "Grupo");
	    lang.put("security.group.permission.read", "Leitura");
	    lang.put("security.group.permission.write", "Escrita");
	    lang.put("security.group.permission.delete", "Delete");
	    lang.put("security.group.permission.security", "Security");
	    lang.put("security.user.name", "Utilizador");
	    lang.put("security.user.permission.read", "Leitura");
	    lang.put("security.user.permission.write", "Escrita");
	    lang.put("security.user.permission.delete", "Delete");
	    lang.put("security.user.permission.security", "Security");
	    lang.put("security.users", "Utilizadores");
	    lang.put("security.groups", "Grupos");
	    lang.put("security.recursive", "Aplicar alterações de forma recursiva");
	    lang.put("secutiry.filter.by.users","Users filter");
	    lang.put("secutiry.filter.by.groups","Groups filter");
	    lang.put("security.status.updating","Updating security");
	    
	    // Preview
	    lang.put("preview.unavailable", "Pré-visualização indisponível");

	    // Mail
	    lang.put("mail.from", "de");
	    lang.put("mail.reply", "Responder");
	    lang.put("mail.to", "Para");
	    lang.put("mail.subject", "Assunto");
	    lang.put("mail.attachment", "Anexo");
	    
	    // Error
	    lang.put("error.label", "Erro do sistema");
	    lang.put("error.invocation", "Erro de comunicação com o servidor");
	    
	    // About
	    lang.put("about.label", "Sobre o OpenKM");
	    lang.put("about.loading", "A carregar ...");
	    
	    // Logout
	    lang.put("logout.label", "Sair");
	    lang.put("logout.closed", "OpenKM foi encerrado correctamente.");
	    lang.put("logout.logout", "OpenKM está a terminar a sessão, aguarde por favor");
	    
	    // Confirm
	    lang.put("confirm.label", "Confirmação");
	    lang.put("confirm.delete.folder", "Tem certeza que deseja eliminar a pasta?");
	    lang.put("confirm.delete.document", "Tem certeza que deseja eliminar o documento?");
	    lang.put("confirm.delete.trash", "Tem certeza que deseja esvaziar a reciclagem?");
	    lang.put("confirm.purge.folder", "Tem certeza que deseja eliminar a pasta?");
	    lang.put("confirm.purge.document", "Tem certeza que deseja eliminar o documento?");
	    lang.put("confirm.delete.propety.group", "Tem certeza que deseja eliminar o grupo de propriedades?");
	    lang.put("confirm.purge.version.history.document", "Tem certeza que deseja eliminar o histórico do documento?");
	    lang.put("confirm.purge.restore.document", "Tem certeza que deseja restaurar esta versão do documento?");
	    lang.put("confirm.set.default.home", "Tem certeza que deseja definir como pasta inicial?");
	    lang.put("confirm.delete.saved.search", "Tem certeza que deseja eliminar a pesquisa guardada?");
	    lang.put("confirm.delete.user.news", "Tem certeza que deseja eliminar a notícia?");
	    lang.put("confirm.delete.mail", "Tem certeza que deseja eliminar a mensagem?");
	    lang.put("confirm.get.pooled.workflow.task","Tem certeza que deseja atribuir a tarefa?");
	    
	    // Search inputs
	    lang.put("search.context", "Contexto");
	    lang.put("search.content", "Conteúdo");
	    lang.put("search.name", "Nome");
	    lang.put("search.keywords", "Palavras-chave");
	    lang.put("search.folder", "Pasta");
	    lang.put("search.category", "Categoria");
	    lang.put("search.results", "Resultados");
	    lang.put("search.to", "para");
	    lang.put("search.page.results", "Resultados da página");
	    lang.put("search.add.property.group", "Adicionar grupo de propriedades");
	    lang.put("search.mimetype", "Tipo de MIME");
	    lang.put("search.type", "Tipo");
	    lang.put("search.type.document", "Documento");
	    lang.put("search.type.folder", "Pasta");
	    lang.put("search.type.mail", "Mensagens");
	    lang.put("search.advanced", "Pesquisa avançada");
	    lang.put("search.user", "Utilizador");
	    lang.put("search.date.and", "e");
	    lang.put("search.date.range", "Data entre");
	    lang.put("search.save.as.news", "Guardar como notícia");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filtrar por pasta");
	    lang.put("search.category.filter", "Filtrar por categoria");
	    
	    // Search results
	    lang.put("search.result.name", "Nome");
	    lang.put("search.result.score", "Relevância");
	    lang.put("search.result.size", "Tamanho");
	    lang.put("search.result.date.update", "Data de modificação");
	    lang.put("search.result.author", "Autor");
	    lang.put("search.result.version", "Versão");
	    lang.put("search.result.path", "Caminho");
	    lang.put("search.result.menu.download", "Descarregar");
	    lang.put("search.result.menu.go.folder", "Ir para a pasta");
	    lang.put("search.result.menu.go.document", "Ir para o documento");
	    lang.put("search.result.status.findPaginated", "A executar pesquisa");
	    lang.put("search.result.status.runsearch", "A executar pesquisa guardada");
	    
	    // Search saved
	    lang.put("search.saved.run", "Executar");
	    lang.put("search.saved.delete", "Eliminar");
	    lang.put("search.saved.status.getsearchs", "A actualizar pesquisas guardadas");
	    lang.put("search.saved.status.savesearch", "A guardar pesquisa");
	    lang.put("search.saved.status.deletesearch", "A eliminar pesquisa guardada");
	    lang.put("search.saved.status.getusernews", "A actualizar notícias");
	    
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
	    lang.put("button.save.search", "Guardar pesquisa");
	    lang.put("button.view", "Ver");
	    lang.put("button.clean", "Limpar");
	    lang.put("button.add", "Adicionar");
	    lang.put("button.delete", "Eliminar");
	    lang.put("button.copy", "Copiar");
	    lang.put("button.create", "Criar");
	    lang.put("button.show", "Mostrar");
	    lang.put("button.memory", "Alterar");
	    lang.put("button.copy.clipboard", "Copiar para área de transferência");	
	    lang.put("button.start", "Iniciar");
	    lang.put("button.select", "Seleccionar");
	    lang.put("button.test", "Test");
	    lang.put("button.next", "Next");
	    
	    // Group
	    lang.put("group.label", "Adicionar grupo de propriedades");
	    lang.put("group.group", "Grupo");
	    lang.put("group.property.group", "Propriedades");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Adicionar marcador");
	    lang.put("bookmark.name", "Nome");
	    lang.put("bookmark.edit.label", "Editar marcadores");
	    lang.put("bookmark.path", "Caminho");
	    lang.put("bookmark.type", "Tipo");
	    lang.put("bookmark.type.document", "Documento");
	    lang.put("bookmark.type.folder", "Pasta");
	    
	    // Notify
	    lang.put("notify.label", "A enviar hiperligação do documento");
	    lang.put("notify.label.attachment", "Send document attachment");
	    
	    // Status
	    lang.put("status.document.copied", "Documento marcado para copiar");
	    lang.put("status.document.cut", "Documento marcado para cortar");
	    lang.put("status.folder.copied", "Pasta marcada para copiar");
	    lang.put("status.folder.cut", "Pasta marcada para cortar");
	    lang.put("status.keep.alive.error", "Sem ligação ao servidor (keep alive)");
	    lang.put("status.debug.enabled", "Depurador activado");
	    lang.put("status.debug.disabled", "Depurador desactivado");
	    lang.put("status.network.error.detected", "Erro de rede detectado");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Domingo");
	    lang.put("calendar.day.monday", "Segunda");
	    lang.put("calendar.day.tuesday", "Terça");
	    lang.put("calendar.day.wednesday", "Quarta");
	    lang.put("calendar.day.thursday", "Quinta");
	    lang.put("calendar.day.friday", "Sexta");
	    lang.put("calendar.day.saturday", "Sábado");
	    lang.put("calendar.month.january", "Janeiro");
	    lang.put("calendar.month.february", "Fevereiro");
	    lang.put("calendar.month.march", "Março");
	    lang.put("calendar.month.april", "Abril");
	    lang.put("calendar.month.may", "Maio");
	    lang.put("calendar.month.june", "Junho");
	    lang.put("calendar.month.july", "Julho");
	    lang.put("calendar.month.august", "Agosto");
	    lang.put("calendar.month.september", "Setembro");
	    lang.put("calendar.month.october", "Outubro");
	    lang.put("calendar.month.november", "Novembro");
	    lang.put("calendar.month.december", "Dezembro");
	    
	    // Media player
	    lang.put("media.player.label", "Media player");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Visualizador de imagens");
	    lang.put("image.viewer.zoom.in", "Ampliar");
	    lang.put("image.viewer.zoom.out", "Reduzir");
	    
	    // Debug console
	    lang.put("debug.console.label", "Consola de depuração");
	    lang.put("debug.enable.disable", "CTRL+Z para activar ou desactivar modo de depuração");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Geral");
	    lang.put("dashboard.tab.news", "Notícias");
	    lang.put("dashboard.tab.user", "Utilizador");
	    lang.put("dashboard.tab.workflow", "Workflow");
	    lang.put("dashboard.tab.mail", "Correio electrónico");
	    lang.put("dashboard.tab.keymap", "palavras-chave");

	    // Dahboard general
	    lang.put("dashboard.new.items", "Novo");
	    lang.put("dashboard.user.locked.documents", "Documentos bloqueados");
	    lang.put("dashboard.user.checkout.documents", "Documentos em Checkout");
	    lang.put("dashboard.user.last.modified.documents", "Últimos documentos modificados");
	    lang.put("dashboard.user.last.downloaded.documents", "Últimos documentos descarregados");
	    lang.put("dashboard.user.subscribed.documents", "Documentos subscritos");
	    lang.put("dashboard.user.subscribed.folders", "Pastas subscritas");
	    lang.put("dashboard.user.last.uploaded.documents", "Últimos documentos enviados");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Documentos mais vistos na última semana");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Documentos mais vistos no último mês");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Documentos mais alterados na última semana");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Documentos mais alterados no último mês");
	    lang.put("dashboard.general.last.uploaded.documents", "Documentos recentemente enviados");
	    lang.put("dashboard.workflow.pending.tasks", "Tarefas pendentes");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Tarefas pendentes não atribuídas");
	    lang.put("dashboard.workflow.task", "Tarefa");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Nome");
	    lang.put("dashboard.workflow.task.created", "Data de criação");
	    lang.put("dashboard.workflow.task.start", "Data de iniciação");
	    lang.put("dashboard.workflow.task.duedate", "Data de entrega");
	    lang.put("dashboard.workflow.task.end", "Data de término");
	    lang.put("dashboard.workflow.task.description", "Descrição");
	    lang.put("dashboard.workflow.task.process.instance", "Instância do processo");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Versão");
	    lang.put("dashboard.workflow.task.process.name", "Nome");
	    lang.put("dashboard.workflow.task.process.description", "Descrição");
	    lang.put("dashboard.workflow.task.process.data", "Dados");
	    lang.put("dashboard.workflow.comments", "Comentários");
	    lang.put("dashboard.workflow.task.process.forms", "Formulário");
	    lang.put("dashboard.workflow.add.comment","Adicionar comentário");
	    lang.put("dashboard.workflow.task.process.definition", "Definição do processo");
	    lang.put("dashboard.workflow.task.process.path", "Caminho");
	    lang.put("dashboard.refreshing", "A actualizar");
	    lang.put("dashboard.keyword", "palavras-chave");
	    lang.put("dashboard.keyword.suggest", "Inserir palavra-chave");
	    lang.put("dashboard.keyword.all", "Todas as palavras-chave");
	    lang.put("dashboard.keyword.top", "Principais palavras-chave");
	    lang.put("dashboard.keyword.related", "Palavras-chave relacionadas");
	    lang.put("dashboard.keyword.goto.document", "Ir para documento");
	    lang.put("dashboard.keyword.clean.keywords", "Limpar palavras-chave");
	    lang.put("dashboard.mail.last.imported.mails", "Mensagens de correio electrónico");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Anexos");
	    
	    // Workflow
	    lang.put("workflow.label", "Iniciar workflow");
	    
	    // User configuration
	    lang.put("user.preferences.label", "Configuração do utilizador");
	    lang.put("user.preferences.user", "Utilizador");
	    lang.put("user.preferences.password", "Senha");
	    lang.put("user.preferences.mail", "E-mail");
	    lang.put("user.preferences.roles","Roles");
	    lang.put("user.preferences.imap.host", "servidor IMAP");
	    lang.put("user.preferences.imap.user", "Nome de utilizador IMAP");
	    lang.put("user.preferences.imap.user.password", "Senha IMAP");
	    lang.put("user.preferences.imap.folder", "pasta IMAP");
	    lang.put("user.preferences.password.error", "Erro: As senhas são diferentes");
	    lang.put("user.preferences.user.data", "Conta do utilizador");
	    lang.put("user.preferences.mail.data", "Conta de E-Mail");
	    lang.put("user.preferences.imap.error", "Todos os campos são obrigatórios para configurar o E-Mail");
	    lang.put("user.preferences.imap.password.error.void", "Tem de ser inserida a senha do servidor IMAP");
	    lang.put("user.preferences.imap.test.error","IMAP configuration error");
	    lang.put("user.preferences.imap.test.ok","IMAP configuration ok");

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Adicionar palavra-chave");
	    lang.put("thesaurus.tab.tree", "Árvore");
	    lang.put("thesaurus.tab.keywords", "palavras-chave");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Adicionar categoria");
	    lang.put("categories.folder.error.delete", "Não é possível eliminar categoria com documentos atribuídos");
	    
	    // Wizard
	    lang.put("wizard.document.uploading", "Document wizard");
	    
	    // User info
	    lang.put("user.info.chat.connect", "Connect to chat");
	    lang.put("user.info.chat.disconnect", "Disconnet chat");
	    lang.put("user.info.chat.new.room", "Net chat room");
	    lang.put("user.info.locked.actual", "Locked documents");
	    lang.put("user.info.checkout.actual", "Checkout documents");
	    lang.put("user.info.subscription.actual", "Actual subscriptions");
	    lang.put("user.info.news.new", "News");
	    lang.put("user.info.workflow.pending", "Pending workflows");
	    lang.put("user.info.user.quota", "Used quota");
	    
	    // Users online popup
	    lang.put("user.online", "Users online");
	    
	    // Chat room
	    lang.put("chat.room", "Chat");
	    lang.put("chat.users.in.room", "Users");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Sem permissão para aceder a documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Impossível bloquear documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Caminho para documento não encontrado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Erro de versão");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Sem permissão para aceder ao documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Não existe um documento com o nome especificado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Já existe um documento com o nome especificado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Impossível bloquear o documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Impossí­vel desbloquear o documento");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Erro interno da aplicação");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Caminho para o documento não encontrado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Sem permissão para aceder à pasta");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Não existe uma pasta com o nome especificado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Já existe uma pasta com o nome especificado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Erro interno da aplicação");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Caminho para a pasta não encontrado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_AccessDenied, "Sem permissão para aceder");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_ItemNotFound, "Não existe um elemento com o nome especificado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_General, "Erro interno da aplicação");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Não existe um documento com o nome especificado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Erro interno da aplicação");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Formato de ficheiro não suportado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Já existe um documento com o nome especificado");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Tamanho do documento superior ao permitido");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_DocumentNameMismatch, "Document name is diferent");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "A sessão foi encerrada");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Erro genérico ao executar pedido");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Nome da pesquisa atribuído");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Nome de marcador atribuído");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Sessão perdida");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Erro de I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Erro interno do repositório");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Erro na configuração do navegador");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_QuotaExceed, "Error user quota exceed, contact with adminitrator");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_AccessDenied, "Document access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemNotFound, "Document not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemExists, "Document already exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Lock, "Document lock denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_PathNotFound, "Path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_AccessDenied, "Access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_General, "Application internal error"); 
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Lock, "Locked");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_PathNotFound, "Path not exist");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_AccessDenied, "Access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchGroup, "Group not exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchProperty, "Property not exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_PathNotFound, "Path not exist");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_AccessDenied, "Access denied");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_DatabaseException, "Database error");
	  }
}
