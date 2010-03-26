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
 * Macedonian
 * 
 * @author Vladimir Stefanov
 */
public class Lang_mk_MK {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd.MM.yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd.MM.yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Вчитувам OpenKM");
	    lang.put("startup.starting.loading", "Почнувам со вчитување на OpenKM");
	    lang.put("startup.taxonomy", "Примам коренов јазол за јавни документи");
	    lang.put("startup.categories", "Getting categories root node");
	    lang.put("startup.thesaurus", "Getting thesaurus root node");
	    lang.put("startup.template", "Примам коренов јазол за мостри");
	    lang.put("startup.personal", "Примам коренов јазол за лични поставки");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Примам коренов јазол за ѓубре");
	    lang.put("startup.user.home", "Примам коренов јазол за корисничка домашна папка");
	    lang.put("startup.bookmarks", "Примам обележувачи");
	    lang.put("startup.loading.taxonomy", "Вчитувам јавни документи");
	    lang.put("startup.loading.taxonomy.getting.folders", "Вчитувам јавни документи - примам папки");
	    lang.put("startup.loading.taxonomy.eval.params", "Вчитувам јавни документи - вреднувам параметри на прелист.");
	    lang.put("startup.loading.taxonomy.open.path", "Вчитувам јавни документи - отвори патека");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Вчитувам јавни документи - примам папки");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Вчитувам јавни документи - примам документи");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Вчитувам лични");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.categories", "Loading categories");
	    lang.put("startup.loading.thesaurus", "Loading thesaurus");
	    lang.put("startup.loading.templates", "Вчитувам мостри");
	    lang.put("startup.loading.trash", "Вчитувам ѓубре");
	    lang.put("startup.loading.history.search", "Вчитувам пребарување на историјата");
	    lang.put("startup.loading.user.values", "Вчитувам кориснички вредности");
	    lang.put("startup.keep.alive", "Вчитувам одржување во живот");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Достапна е нова верзија на OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Јавни документи");
	    lang.put("leftpanel.label.trash", "Ѓубре");
	    lang.put("leftpanel.label.mail", "E-пошта");
	    lang.put("leftpanel.label.stored.search", "Зачувани пребарувања");
	    lang.put("leftpanel.label.categories", "Categories");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Мостри");
	    lang.put("leftpanel.label.my.documents", "Мои документи");
	    lang.put("leftpanel.label.user.search", "Вести за корисници");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Создади папка");
	    lang.put("tree.menu.directory.remove", "Избриши");
	    lang.put("tree.menu.directory.rename", "Преименувај");
	    lang.put("tree.menu.directory.refresh", "Освежи");
	    lang.put("tree.menu.directory.move", "Премести");
	    lang.put("tree.menu.directory.copy", "Копирај");
	    lang.put("tree.menu.directory.add.document", "Додади документ");
	    lang.put("tree.menu.add.bookmark", "Додади обележувач");
	    lang.put("tree.menu.set.home", "Постави стандардна домашна папка");
	    lang.put("tree.menu.export", "Извези во датотека");
	    lang.put("tree.status.refresh.folder", "Ажурирам стебло на папката");
	    lang.put("tree.status.refresh.create", "Создавам папка");
	    lang.put("tree.status.refresh.delete", "Бришам папка");
	    lang.put("tree.status.refresh.rename", "Преименувам папка");
	    lang.put("tree.status.refresh.restore", "Враќам назад папка");
	    lang.put("tree.status.refresh.purge", "Бришам папка засекогаш");
	    lang.put("tree.status.refresh.get", "Ажурирам папка");
	    lang.put("tree.folder.new", "Нова папка");
	    lang.put("tree.status.refresh.add.subscription", "Додавање претплата");
	    lang.put("tree.status.refresh.remove.subscription", "Бришење претплата");
	    lang.put("tree.status.refresh.get.root", "Освежување на кореновата папка");
	    lang.put("tree.status.refresh.get.keywords", "Refreshing keywords");
	    lang.put("tree.status.refresh.get.user.home", "Примам корисничка домашна папка");
	    lang.put("tree.status.refresh.purge.trash", "Чистење на ѓубрето");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Поврати");
	    lang.put("trash.menu.directory.purge", "Избриши засекогаш");
	    lang.put("trash.menu.directory.purge.trash", "Исчисти ѓубре");
	    lang.put("trash.directory.select.label", "Избери целна папка");
	    
	    // General menu
	    lang.put("general.menu.file", "Датотека");
	    	lang.put("general.menu.file.exit", "Излези");
	    	lang.put("general.menu.file.purge.trash", "Исчисти ѓубре");
	    lang.put("general.menu.edit", "Уреди");
			lang.put("general.menu.file.create.directory", "Создади папка");
			lang.put("general.menu.file.download.document", "Симни документ");
			lang.put("general.menu.file.download.document.pdf", "Симни документ како PDF");
			lang.put("general.menu.file.send.link", "Испрати врска до документот");
			lang.put("general.menu.file.lock", "Заклучи");
			lang.put("general.menu.file.unlock", "Отклучи");
			lang.put("general.menu.file.add.document", "Додади документ");
			lang.put("general.menu.file.checkout", "Одјава");
			lang.put("general.menu.file.checkin", "Пријава");
			lang.put("general.menu.file.cancel.checkout", "Откажи одјава");
			lang.put("general.menu.file.delete", "Избриши");
			lang.put("general.menu.file.refresh", "Освежи");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "File uploader");
	    lang.put("general.menu.tools", "Алатки");
	    	lang.put("general.menu.tools.languages", "Јазици");
	    	lang.put("general.menu.tools.skin", "Тема");
    			lang.put("general.menu.tools.skin.default", "Преддефинирана");
    			lang.put("general.menu.tools.skin.default2", "Преддефинирана 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Среден фонт");
    			lang.put("general.menu.tools.skin.bigfont", "Голем фонт");
    		lang.put("general.menu.debug.console", "Козола за дебагирање");
    		lang.put("general.menu.administration", "Прикажи администрација");
    		lang.put("general.menu.tools.preferences", "Prefererences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Обележувачи");
	    	lang.put("general.menu.bookmark.home", "Дома");
	    	lang.put("general.menu.bookmark.default.home", "Постави стандардна домашна папка");
	    	lang.put("general.menu.bookmark.edit", "Уреди обележувачи");
	    lang.put("general.menu.help", "Помош");
		    lang.put("general.menu.bug.report", "Пријавување на грешки");
		    lang.put("general.menu.support.request", "Барање на поддржка");
		    lang.put("general.menu.public.forum", "Јавен форум");
		    lang.put("general.menu.project.web", "Проект web");
		    lang.put("general.menu.version.changes", "Белешки за верзијата");
		    lang.put("general.menu.documentation", "Документација");
		    lang.put("general.menu.about", "За OpenKM");
	    lang.put("general.connected", "Поврзан како");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Име");
	    lang.put("filebrowser.date.update", "Дата на ажурирање");
	    lang.put("filebrowser.size", "Големина");
	    lang.put("filebrowser.path", "Патека");
	    lang.put("filebrowser.author", "Автор");
	    lang.put("filebrowser.version", "Верзија");
	    lang.put("filebrowser.menu.checkout", "Одјава");
	    lang.put("filebrowser.menu.checkin", "Пријава");
	    lang.put("filebrowser.menu.delete", "Избриши");
	    lang.put("filebrowser.menu.rename", "Преименувај");
	    lang.put("filebrowser.menu.checkout.cancel", "Откажување на одјава");
	    lang.put("filebrowser.menu.lock", "Заклучи");
	    lang.put("filebrowser.menu.unlock", "Отклучи");
	    lang.put("filebrowser.menu.download", "Симни");
	    lang.put("filebrowser.menu.move", "Премести");
	    lang.put("filebrowser.menu.copy", "Копирај");
	    lang.put("filebrowser.menu.create.from.template", "Создади од мостра");
	    lang.put("filebrowser.menu.add.property.group", "Додади група на својства");
	    lang.put("filebrowser.menu.remove.property.group", "Отстрани група на својства");
	    lang.put("filebrowser.menu.start.workflow", "Почни работен процес");
	    lang.put("filebrowser.menu.add.subscription", "Додај претплата");
	    lang.put("filebrowser.menu.remove.subscription", "Отстрани претплата");
	    lang.put("filebrowser.menu.add.bookmark", "Додај обележувач");
	    lang.put("filebrowser.menu.set.home", "Постави стандардна почетна папка");
	    lang.put("filebrowser.menu.refresh", "Освежи");
	    lang.put("filebrowser.menu.export", "Извези во ZIP");
	    lang.put("filebrowser.menu.play", "Пушти");
	    lang.put("filebrowser.menu.image.viewer", "Прегледувач на слики");
	    lang.put("filebrowser.status.refresh.folder", "Ажурирам листа на папки");
	    lang.put("filebrowser.status.refresh.document", "Ажурирам листа на документи");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Бришам папка");
	    lang.put("filebrowser.status.refresh.delete.document", "Бришам документ");
	    lang.put("filebrowser.status.refresh.checkout", "Одјавувам документ");
	    lang.put("filebrowser.status.refresh.lock", "Заклучувам документ");
	    lang.put("filebrowser.status.refresh.unlock", "Отклучувам документ");
	    lang.put("filebrowser.status.refresh.document.rename", "Преименувам документ");
	    lang.put("filebrowser.status.refresh.folder.rename", "Преименувам папка");
	    lang.put("filebrowser.status.refresh.document.purge", "Бришам документ");
	    lang.put("filebrowser.status.refresh.folder.purge", "Бришам папка");
	    lang.put("filebrowser.status.refresh.folder.get", "Ажурирам папка");
	    lang.put("filebrowser.status.refresh.document.get", "Ажурирам документ");
	    lang.put("filebrowser.status.refresh.add.subscription", "Додавам претплата");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Бришам претплата");
	    lang.put("filebrowser.status.refresh.get.user.home", "Примам корисничка домашна папка");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Испрати");
	    lang.put("fileupload.status.sending", "Испраќам датотека...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "Датотеката е правилно испратена");
	    lang.put("fileupload.upload.status", "Статус на испраќањето");
	    lang.put("fileupload.upload.uploaded", "Испратено");
	    lang.put("fileupload.upload.completed", "Испраќањето заврши");
	    lang.put("fileupload.upload.runtime", "Време на работа");
	    lang.put("fileupload.upload.remaining", "Останува уште");
	    lang.put("fileupload.button.close", "Затвори");
	    lang.put("fileupload.button.add.other.file", "Додај друга датотека");
	    lang.put("fileupload.status.move.file", "Преместувам датотека...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Копирам датотека...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "Враќам назад датотека...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Преместувам папка...");
	    lang.put("fileupload.status.copy.folder", "Копирам папка...");
	    lang.put("fileupload.status.restore.folder", "Враќам назад папка...");
	    lang.put("fileupload.status.create.from.template", "Создавам датотека од мострата...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Додади нов документ");
	    lang.put("fileupload.label.update", "Ажурирај документи");
	    lang.put("fileupload.label.users.notify", "Извести ги корисниците");
	    lang.put("fileupload.label.comment", "Коментар");
	    lang.put("fileupload.label.users.to.notify",  "Корисници кои ќе бидат известени");
	    lang.put("fileupload.label.users",  "Корисници");
	    lang.put("fileupload.label.must.select.users",  "Морате да изберете некој корисник да го известите");
	    lang.put("fileupload.label.importZip", "Увези документи од ZIP");
	    lang.put("fileupload.label.notify.comment", "Белешка за известување");
	    lang.put("fileupload.label.error.importing.zip", "Грешка при увезувањето на датотеката");
	    lang.put("fileupload.label.error.move.file", "Грешка при преместувањето на датотеката");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Грешка при копирањето на датотеката");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "Грешка при повраќањето на датотеката");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Грешка при преместувањето на папката");
	    lang.put("fileupload.label.error.copy.folder", "Грешка при копирањето на папката");
	    lang.put("fileupload.label.error.restore.folder", "Грешка при повраќањето на папката");
	    lang.put("fileupload.label.error.create.from.template", "Грешка при креирањето на датотеката од мостра");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Не е дозволено преместување на оригиналната или папката дете");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Не е дозволено преместување на оригиналната папка");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Не е дозволено креирање датотека во оригиналната папка");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Својства");
	    lang.put("tab.document.notes", "Белешки");
	    lang.put("tab.document.history", "Историја");
	    lang.put("tab.document.status.history", "Ажурирам историја");
	    lang.put("tab.status.security.role", "Ажурирам сигурносни правила");
	    lang.put("tab.status.security.user", "Ажурирам сигурносни правила за корисници");
	    lang.put("tab.document.status.group.properties", "Ажурирам група на својства");
	    lang.put("tab.document.status.set.keywords", "Поставувам клучни зборови");
	    lang.put("tab.document.status.set.categories", "Updating categories");
	    lang.put("tab.document.status.get.version.history.size", "Освежувам големина на историјата на документот");
	    lang.put("tab.document.status.purge.version.history", "Компресирам историја на документот");
	    lang.put("tab.document.status.restore.version", "Враќам верзија на документот");
	    lang.put("tab.document.security", "Безбедност");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Својства");
	    lang.put("tab.folder.security", "Безбедност");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Десктоп");
	    lang.put("tab.workspace.search", "Пребарување");
	    lang.put("tab.workspace.dashboard", "Контролна табла");
	    lang.put("tab.workspace.administration", "Администрација");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Име");
	    lang.put("document.folder", "Папка");
	    lang.put("document.size", "Големина");
	    lang.put("document.created", "Создадена");
	    lang.put("document.lastmodified", "Променета");
	    lang.put("document.mimetype", "MIME тип");
	    lang.put("document.keywords", "Клучни зборови");
	    lang.put("document.by", "од");
	    lang.put("document.status", "Статус");
	    lang.put("document.status.checkout", "Уреден од");
	    lang.put("document.status.locked", "Заклучен од");
	    lang.put("document.status.normal", "Достапен");
	    lang.put("document.subscribed", "Преплатен");
	    lang.put("document.subscribed.yes", "Да");
	    lang.put("document.subscribed.no", "Не");
	    lang.put("document.history.size", "Големина на историја");
	    lang.put("document.subscribed.users", "Преплатени корисници");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Додај белешка");
	    lang.put("document.keywords.cloud", "Keywords cloud");
	    lang.put("document.categories", "Categories");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Име");
	    lang.put("folder.parent", "Родител");
	    lang.put("folder.created", "Создадена");
	    lang.put("folder.by", "од");
	    lang.put("folder.subscribed", "Преплатен");
	    lang.put("folder.subscribed.yes", "Да");
	    lang.put("folder.subscribed.no", "Не");
	    lang.put("folder.subscribed.users", "Преплатени корисници");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Верзија");
	    lang.put("version.created", "Датум");
	    lang.put("version.author", "Автор");
	    lang.put("version.size", "Големина");
	    lang.put("version.purge.document", "Збиј ја историјата");
	    lang.put("version.comment", "Коментар");
	    
	    // Security
	    lang.put("security.label", "Безбедност");
	    lang.put("security.group.name", "Група");
	    lang.put("security.group.permission.read", "Читај");
	    lang.put("security.group.permission.write", "Запишувај");
	    lang.put("security.user.name", "Корисник");
	    lang.put("security.user.permission.read", "Читај");
	    lang.put("security.user.permission.write", "Запишуај");
	    lang.put("security.users", "Корисници");
	    lang.put("security.groups", "Групи");
	    lang.put("security.recursive", "Рекурзивна промена на дозволите");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "Сисемот создаде грешка");
	    lang.put("error.invocation", "Грешка при комуницирањето со серверот");
	    
	    // About
	    lang.put("about.label", "За OpenKM");
	    lang.put("about.loading", "Вчитувам ...");
	    
	    // Logout
	    lang.put("logout.label", "Излези");
	    lang.put("logout.closed", "OpenKM беша правилно затворен.");
	    lang.put("logout.logout", "OpenKM се одјавува, ве молиме почекајте");
	    
	    // Confirm
	    lang.put("confirm.label", "Потврда");
	    lang.put("confirm.delete.folder", "¿ Дали навистина сакате да ја избришете папката ?");
	    lang.put("confirm.delete.document", "¿ Дали навистина сакате да го избришете документот ?");
	    lang.put("confirm.delete.trash", "¿ Дали навистина сакате да го испразните ѓубрето ?");
	    lang.put("confirm.purge.folder", "¿ Дали навистина сакате да ја избришете папката ?");
	    lang.put("confirm.purge.document", "¿ Дали навистина сакате да го избришете документот ?");
	    lang.put("confirm.delete.propety.group", "¿  Дали навистина сакате да ја избришете групата на својства ?");
	    lang.put("confirm.purge.version.history.document", "¿ Дали навистина сакате да ја избришете историјата на документот ?");
	    lang.put("confirm.purge.restore.document", "¿ Дали навистина сакате да се вратите на оваа верзија на документот ?");
	    lang.put("confirm.set.default.home", "¿ Дали навистина сакате да поставите стандардна почетна папка ?");
	    lang.put("confirm.delete.saved.search", "¿ Дали навистина сакате да ги избришете зачуваните пребарувања ?");
	    lang.put("confirm.delete.user.news", "¿  Дали навистина сакате да ги избришете вестите за корисниците ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    
	    // Search inputs
	    lang.put("search.context", "Контекст");
	    lang.put("search.content", "Содржина");
	    lang.put("search.name", "Име");
	    lang.put("search.keywords", "Клучни зборови");
	    lang.put("search.folder", "Папка");
	    lang.put("search.category", "Category");
	    lang.put("search.results", "Резултати");
	    lang.put("search.to", "во");
	    lang.put("search.page.results", "Резултати за страница");
	    lang.put("search.add.property.group", "Додај група на својства");
	    lang.put("search.mimetype", "Mime тип");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Напредно пребарување");
	    lang.put("search.user", "Корисник");
	    lang.put("search.date.and", "и");
	    lang.put("search.date.range", "Временски опсег помеѓу");
	    lang.put("search.save.as.news", "Зачувај како кориснички вести");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Филтрирај по папка");
	    lang.put("search.category.filter", "Filter by category");
	    
	    // Search results
	    lang.put("search.result.name", "Име");
	    lang.put("search.result.score", "Важност");
	    lang.put("search.result.size", "Големина");
	    lang.put("search.result.date.update", "Датум на ажурирање");
	    lang.put("search.result.author", "Автор");
	    lang.put("search.result.version", "Верзија");
	    lang.put("search.result.path", "Патека");
	    lang.put("search.result.menu.download", "Симни");
	    lang.put("search.result.menu.go.folder", "Оди во папка");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Ажурирам пребарувања");
	    lang.put("search.result.status.runsearch", "Ажурирам зачувани пребарувања");
	    
	    // Search saved
	    lang.put("search.saved.run", "Изврши");
	    lang.put("search.saved.delete", "Избриши");
	    lang.put("search.saved.status.getsearchs", "Освежувам зачувани пребарувања");
	    lang.put("search.saved.status.savesearch", "Ажурирам зачувани пребарувања");
	    lang.put("search.saved.status.deletesearch", "Бришам зачувани пребарувања");
	    lang.put("search.saved.status.getusernews", "Освежувам кориснички вести");
	    
	    // Button
	    lang.put("button.close", "Затвори");
	    lang.put("button.logout", "Одјави се");
	    lang.put("button.update", "Ажурирај");
	    lang.put("button.cancel", "Откажи");
	    lang.put("button.accept", "Прифати");
	    lang.put("button.restore", "Поврати");
	    lang.put("button.move", "Премести");
	    lang.put("button.change", "Промени");
	    lang.put("button.search", "Пребарај");
	    lang.put("button.save.search", "Зачувај пребарување");
	    lang.put("button.view", "Поглед");
	    lang.put("button.clean", "Исчисти");
	    lang.put("button.add", "Додади");
	    lang.put("button.delete", "Избриши");
	    lang.put("button.copy", "Копирај");
	    lang.put("button.create", "Создади");
	    lang.put("button.show", "Прикажи");
	    lang.put("button.memory", "Ажурирај");
	    lang.put("button.copy.clipboard", "Копирај во инсерти");	
	    lang.put("button.start", "Отпочни");
	    lang.put("button.select", "Избери");
	    
	    // Group
	    lang.put("group.label", "Додади група на својства");
	    lang.put("group.group", "Група");
	    lang.put("group.property.group", "Својства");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Додај обележувач");
	    lang.put("bookmark.name", "Име");
	    lang.put("bookmark.edit.label", "Уреди обележувачи");
	    lang.put("bookmark.path", "Патека");
	    lang.put("bookmark.type", "Тип");
	    lang.put("bookmark.type.document", "Документ");
	    lang.put("bookmark.type.folder", "Папка");
	    
	    // Notify
	    lang.put("notify.label", "Испраќам врска од документот");
	    
	    // Status
	    lang.put("status.document.copied", "Документот е означен за копирање");
	    lang.put("status.document.cut", "Документот е означен за сечење");
	    lang.put("status.folder.copied", "Папкате е означена за копирање");
	    lang.put("status.folder.cut", "Папката е означена за сечење");
	    lang.put("status.keep.alive.error", "Детектирана е изгубена врска до серверот (одржи во живот)");
	    lang.put("status.debug.enabled", "Дебагирај овозможено");
	    lang.put("status.debug.disabled", "Дебагирај оневозможено");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Недела");
	    lang.put("calendar.day.monday", "Понеделник");
	    lang.put("calendar.day.tuesday", "Вторник");
	    lang.put("calendar.day.wednesday", "Среда");
	    lang.put("calendar.day.thursday", "Четврток");
	    lang.put("calendar.day.friday", "Петок");
	    lang.put("calendar.day.saturday", "Сабота");
	    lang.put("calendar.month.january", "Јануари");
	    lang.put("calendar.month.february", "Февруари");
	    lang.put("calendar.month.march", "Март");
	    lang.put("calendar.month.april", "Април");
	    lang.put("calendar.month.may", "Мај");
	    lang.put("calendar.month.june", "Јуни");
	    lang.put("calendar.month.july", "Јули");
	    lang.put("calendar.month.august", "Август");
	    lang.put("calendar.month.september", "Септември");
	    lang.put("calendar.month.october", "Октомври");
	    lang.put("calendar.month.november", "Ноември");
	    lang.put("calendar.month.december", "Декември");
	    
	    // Media player
	    lang.put("media.player.label", "Пуштач на медиум");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Прегледувач на слики");
	    lang.put("image.viewer.zoom.in", "Зумирај");
	    lang.put("image.viewer.zoom.out", "Одзумирај");
	    
	    // Debug console
	    lang.put("debug.console.label", "Конзола за дебагирање");
	    lang.put("debug.enable.disable", "CTRL+Z за да овозможите или оневозможите режим за дебагирање");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Општо");
	    lang.put("dashboard.tab.news", "Вести");
	    lang.put("dashboard.tab.user", "Корисник");
	    lang.put("dashboard.tab.workflow", "Работен процес");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Keyword map");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Нов");
	    lang.put("dashboard.user.locked.documents", "Заклучени документи");
	    lang.put("dashboard.user.checkout.documents", "Одјавени документи");
	    lang.put("dashboard.user.last.modified.documents", "Последни променети документи");
	    lang.put("dashboard.user.last.downloaded.documents", "Последни симнати документи");
	    lang.put("dashboard.user.subscribed.documents", "Документи со претплата");
	    lang.put("dashboard.user.subscribed.folders", "Папки со претплата");
	    lang.put("dashboard.user.last.uploaded.documents", "Последни испратени документи");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Најгледани документи во минатата недела");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Најгледани документи во минатиот месец");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Најпроменувани документи во минатата недела");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Најпроменувани документи во минатаиот месец");
	    lang.put("dashboard.general.last.uploaded.documents", "Последно испратени документи");
	    lang.put("dashboard.workflow.pending.tasks", "Задачи на листа на чекање");
	    lang.put("dashboard.workflow.task", "Задача");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Име");
	    lang.put("dashboard.workflow.task.created", "Датум на создавање");
	    lang.put("dashboard.workflow.task.start", "Датум на отпочнување");
	    lang.put("dashboard.workflow.task.duedate", "Роковна дата");
	    lang.put("dashboard.workflow.task.end", "Краен датум");
	    lang.put("dashboard.workflow.task.description", "Опис");
	    lang.put("dashboard.workflow.task.process.instance", "Инстанца на процесот");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Верзија");
	    lang.put("dashboard.workflow.task.process.name", "Име");
	    lang.put("dashboard.workflow.task.process.description", "Опис");
	    lang.put("dashboard.workflow.task.process.data", "Дата");
	    lang.put("dashboard.workflow.comments", "Comments");
	    lang.put("dashboard.workflow.task.process.forms", "Form");
	    lang.put("dashboard.workflow.add.comment","Add comment");
	    lang.put("dashboard.workflow.task.process.definition", "Дефиниција на процесот");
	    lang.put("dashboard.workflow.task.process.path", "Патека");
	    lang.put("dashboard.refreshing", "Освежувам");
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
	    lang.put("workflow.label", "Отпочни работен процес");
	    
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
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Пристапот до документот е одбиен");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Заклучувањето на документот е одбиено");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Внатрешна грешка во складиштето");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Не е најдена патеката на документот");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Version error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Пристапот до документот е одбиен");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Документот не е најден");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Документот веќе постои");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Заклучувањето на документот е одбиено");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Посакано е отклучување на документот");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Внатрешна грешка во складиштето");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Внатрешна грешка на апликацијата");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Не е најдена патеката на документот");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Пристапот до папката е одбиен");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Папката не е најдена");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Папката веќе постои");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Внатрешна грешка во складиштето");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Внатрешна грешка во складиштето");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Патеката на папката не е најдена");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Пристапот до елементот е одбиен");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Елементот не е најден");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Внатрешна грешка во складиштето");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Внатрешна грешка во складиштето");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Документот не е најден");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Внатрешна грешка во складиштето");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Неподдржан формат на датотека");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Документот веќе постои");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Документот е преголем");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Сесијата е затворена");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Генеричка грешка при извршување на барањето");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Снименото име на пребарувањето мора да е уникатно");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Името на обележувачот мора да е уникатно");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Сесијата е изгубена");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Внатрешна грешка во складиштето");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Внатрешна грешка во складиштето");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Грешка при влез/излез");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Внатрешна грешка во складиштето");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error in browser configuration");
	  }
}
