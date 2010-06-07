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
 *
 * Translated by Denis "Di" Yagofarov, denyago@gmail.com
 *
 */

package com.openkm.frontend.client.lang;

import java.util.HashMap;

import com.openkm.frontend.client.config.ErrorCode;

/**
 * Russian
 * 
 * @author Denis Timurovich Yagofarov
 */
public class Lang_ru_RU {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd-MM-yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd-MM-yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Загружаю OpenKM");
	    lang.put("startup.starting.loading", "Начинаю загрузку OpenKM");
		// taxonomy = классификатор?
	    lang.put("startup.taxonomy", "Получаю корневой узел классификатора");
	    lang.put("startup.categories", "Getting categories root node");
	    lang.put("startup.thesaurus", "Getting thesaurus root node");
	    lang.put("startup.template", "Получаю корневой узел шаблонов");
	    lang.put("startup.personal", "Получаю корневой узел персонального раздела");
	    lang.put("startup.mail", "Получаю корневой узел e-mail");
	    lang.put("startup.trash", "Получаю корневой узел корзины");
	    lang.put("startup.user.home", "Получаю узел домашней папки");
	    lang.put("startup.bookmarks", "Получаю закладки");
	    lang.put("startup.loading.taxonomy", "Загружаю классификатор");
	    lang.put("startup.loading.taxonomy.getting.folders", "Загружаю классификатор - получаю папки");
	    lang.put("startup.loading.taxonomy.eval.params", "Загружаю классификатор - вычисляю параметры обозревателя");
	    lang.put("startup.loading.taxonomy.open.path", "Загружаю классификатор - открываю путь");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Загружаю классификатор - получаю папки");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Загружаю классификатор - получаю документы");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Загружаю классификатор - получаю почтовые сообщения");
	    lang.put("startup.loading.personal", "Загружаю персональный раздел");
	    lang.put("startup.loading.mail", "Загружаю сообщения e-mail");
	    lang.put("startup.loading.categories", "Loading categories");
	    lang.put("startup.loading.thesaurus", "Loading thesaurus");
	    lang.put("startup.loading.templates", "Загружаю шаблоны");
	    lang.put("startup.loading.trash", "Загружаю корзину");
	    lang.put("startup.loading.history.search", "Загружаю поиск по истории");
	    lang.put("startup.loading.user.values", "Загружаю пользовательские значения");
		// keep alive = поддержка .... чего?!
	    lang.put("startup.keep.alive", "Загружаю поддержку соединения");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Доступны обновления для OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Классификатор");
	    lang.put("leftpanel.label.trash", "Корзина");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Сохраненный поиск");
	    lang.put("leftpanel.label.categories", "Categories");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Шаблоны");
	    lang.put("leftpanel.label.my.documents", "Мои документы");
	    lang.put("leftpanel.label.user.search", "Новости пользователей"); // ??
	    lang.put("leftpanel.label.all.repository", "Весь репозиторий"); // ??
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Создать папку");
	    lang.put("tree.menu.directory.remove", "Удалить");
	    lang.put("tree.menu.directory.rename", "Переименовать");
	    lang.put("tree.menu.directory.refresh", "Обновить");
	    lang.put("tree.menu.directory.move", "Перенести");
	    lang.put("tree.menu.directory.copy", "Копировать");
	    lang.put("tree.menu.directory.add.document", "Добавить документ");
	    lang.put("tree.menu.add.bookmark", "Добавить закладку");
	    lang.put("tree.menu.set.home", "Сделать домашним по умолчанию");
	    lang.put("tree.menu.export", "Экспортировать в файл");
	    lang.put("tree.status.refresh.folder", "Обновляю древо папок");
	    lang.put("tree.status.refresh.create", "Создаю папку");
	    lang.put("tree.status.refresh.delete", "Удаляю папку");
	    lang.put("tree.status.refresh.rename", "Переименовываю папку");
	    lang.put("tree.status.refresh.restore", "Восстанавливаю папку");
	    lang.put("tree.status.refresh.purge", "Очищаю папку");
	    lang.put("tree.status.refresh.get", "Обновляю папку");
	    lang.put("tree.folder.new", "Новая папка");
	    lang.put("tree.status.refresh.add.subscription", "Добавляю подписку");
	    lang.put("tree.status.refresh.remove.subscription", "Удаляю подписку");
	    lang.put("tree.status.refresh.get.root", "Обновляю корневую папку");
	    lang.put("tree.status.refresh.get.keywords", "Refreshing keywords");
	    lang.put("tree.status.refresh.get.user.home", "Получаю начальный элемент");
	    lang.put("tree.status.refresh.purge.trash", "Очищаю корзину");
	    lang.put("tree.menu.directory.find.folder","Find folder");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Восстановить");
	    lang.put("trash.menu.directory.purge", "Очистить");
	    lang.put("trash.menu.directory.purge.trash", "Очистить корзину");
	    lang.put("trash.directory.select.label", "Выбрать целевую папку");
	    
	    // General menu
	    lang.put("general.menu.file", "Файл");
	    	lang.put("general.menu.file.exit", "Выход");
	    	lang.put("general.menu.file.purge.trash", "Очистить корзину");
	    lang.put("general.menu.edit", "Редактировать");
			lang.put("general.menu.file.create.directory", "Создать папку");
			lang.put("general.menu.file.download.document", "Загрузить документ");
			lang.put("general.menu.file.download.document.pdf", "Загрузить документ как PDF");
			lang.put("general.menu.file.send.link", "Отправить ссылку на документ");
			lang.put("general.menu.file.lock", "Блокировать");
			lang.put("general.menu.file.unlock", "Разблокировать");
			lang.put("general.menu.file.add.document", "Добавить документ");
			lang.put("general.menu.file.checkout", "Выгрузить (для редактирования)");
			lang.put("general.menu.file.checkin", "Загрузить (отредактированный)");
			lang.put("general.menu.file.cancel.checkout", "Отменить выгрузку");
			lang.put("general.menu.file.delete", "Удалить");
			lang.put("general.menu.file.refresh", "Обновить");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "File uploader");
	    lang.put("general.menu.tools", "Инструменты");
	    	lang.put("general.menu.tools.languages", "Языки");
	    	lang.put("general.menu.tools.skin", "Оболочки");
    			lang.put("general.menu.tools.skin.default", "По умолчанию");
    			lang.put("general.menu.tools.skin.default2", "По умолчанию 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Средний шрифт");
    			lang.put("general.menu.tools.skin.bigfont", "Большой шрифт");
    		lang.put("general.menu.debug.console", "Консоль отладки");
    		lang.put("general.menu.administration", "Показать администрирование");
    		lang.put("general.menu.tools.preferences", "Предпочтения");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Закладки");
	    	lang.put("general.menu.bookmark.home", "Домой");
	    	lang.put("general.menu.bookmark.default.home", "Выбрать домашний элемент по умолчанию");
	    	lang.put("general.menu.bookmark.edit", "Редактировать закладки");
	    lang.put("general.menu.help", "Помощь");
		    lang.put("general.menu.bug.report", "Сообщить о ошибке");
		    lang.put("general.menu.support.request", "Запросить поддержку");
		    lang.put("general.menu.public.forum", "На форум");
		    lang.put("general.menu.project.web", "Web-сайт проекта");
		    lang.put("general.menu.version.changes", "Замечания к релизу");
		    lang.put("general.menu.documentation", "Документация");
		    lang.put("general.menu.about", "Про OpenKM");
	    lang.put("general.connected", "Подсоединены как");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Название");
	    lang.put("filebrowser.date.update", "Время обновления");
	    lang.put("filebrowser.size", "Размер");
	    lang.put("filebrowser.path", "Путь");
	    lang.put("filebrowser.author", "Автор");
	    lang.put("filebrowser.version", "Версия");
	    lang.put("filebrowser.menu.checkout", "Выгрузить (для редактирования)");
	    lang.put("filebrowser.menu.checkin", "Загрузить (отредактированный)");
	    lang.put("filebrowser.menu.delete", "Удалить");
	    lang.put("filebrowser.menu.rename", "Переименовать");
	    lang.put("filebrowser.menu.checkout.cancel", "Отменить выгрузку");
	    lang.put("filebrowser.menu.lock", "Блокировать");
	    lang.put("filebrowser.menu.unlock", "Разблокировать");
	    lang.put("filebrowser.menu.download", "Загрузить");
	    lang.put("filebrowser.menu.move", "Переместить");
	    lang.put("filebrowser.menu.copy", "Копировать");
	    lang.put("filebrowser.menu.create.from.template", "Создать из шаблона");
	    lang.put("filebrowser.menu.add.property.group", "Добавить группу свойств");
	    lang.put("filebrowser.menu.remove.property.group", "Удалить группу свойств");
	    lang.put("filebrowser.menu.start.workflow", "Начать рабочий процесс");
	    lang.put("filebrowser.menu.add.subscription", "Добавить подписку");
	    lang.put("filebrowser.menu.remove.subscription", "Удалить подписку");
	    lang.put("filebrowser.menu.add.bookmark", "Добавить закладку");
	    lang.put("filebrowser.menu.set.home", "Установить начальный элемент по умолчанию");
	    lang.put("filebrowser.menu.refresh", "Обновить");
	    lang.put("filebrowser.menu.export", "Экспортировать в ZIP");
	    lang.put("filebrowser.menu.play", "Проиграть");
	    lang.put("filebrowser.menu.image.viewer", "Просмотр изображений");
	    lang.put("filebrowser.status.refresh.folder", "Обновляю список папок");
	    lang.put("filebrowser.status.refresh.document", "Обновляю список документов");
	    lang.put("filebrowser.status.refresh.mail", "Обновляю список писем");
	    lang.put("filebrowser.status.refresh.delete.folder", "Удаляю папку");
	    lang.put("filebrowser.status.refresh.delete.document", "Удаляю документ");
	    lang.put("filebrowser.status.refresh.checkout", "Выгружаю документ");
	    lang.put("filebrowser.status.refresh.lock", "Блокирую документ");
	    lang.put("filebrowser.status.refresh.unlock", "Снимаю блокировку с документа");
	    lang.put("filebrowser.status.refresh.document.rename", "Переименовываю документ");
	    lang.put("filebrowser.status.refresh.folder.rename", "Переименовываю папку");
	    lang.put("filebrowser.status.refresh.document.purge", "Удаляю документ");
	    lang.put("filebrowser.status.refresh.folder.purge", "Удаляю папку");
	    lang.put("filebrowser.status.refresh.folder.get", "Обновляю папку");
	    lang.put("filebrowser.status.refresh.document.get", "Обновляю документ");
	    lang.put("filebrowser.status.refresh.add.subscription", "Добавляю подписку");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Удаляю подписку");
	    lang.put("filebrowser.status.refresh.get.user.home", "Получаю начальный элемент");
	    lang.put("filebrowser.status.refresh.delete.mail", "Удаляю почту");
	    lang.put("filebrowser.status.refresh.mail.purge", "Удаляю почту");
	    
	    // File Upload
	    lang.put("fileupload.send", "Отправить");
	    lang.put("fileupload.status.sending", "Загружаю файл...");
	    lang.put("fileupload.status.indexing", "Индексирую файл...");
	    lang.put("fileupload.status.ok", "Файл загружен успешно");
	    lang.put("fileupload.upload.status", "Статус загрузки");
	    lang.put("fileupload.upload.uploaded", "Загружено");
	    lang.put("fileupload.upload.completed", "Загрузка закончена");
	    lang.put("fileupload.upload.runtime", "Прошло времени");
	    lang.put("fileupload.upload.remaining", "Осталось времени");
	    lang.put("fileupload.button.close", "Закрыть");
	    lang.put("fileupload.button.add.other.file", "Добавить ещё файл");
	    lang.put("fileupload.status.move.file", "Перемещаю файл...");
	    lang.put("fileupload.status.move.mail", "Перемещаю почту...");
	    lang.put("fileupload.status.copy.file", "Копирую файл...");
	    lang.put("fileupload.status.copy.mail", "Копирую почту...");
	    lang.put("fileupload.status.restore.file", "Восстанавливаю файл...");
	    lang.put("fileupload.status.restore.mail", "Восстанавливаю почту...");
	    lang.put("fileupload.status.move.folder", "Перемещаю папку...");
	    lang.put("fileupload.status.copy.folder", "Копирую папку...");
	    lang.put("fileupload.status.restore.folder", "Восстанавливаю папку...");
	    lang.put("fileupload.status.create.from.template", "Создаю файл из шаблона...");
	    lang.put("fileupload.status.of", "из");
	    lang.put("fileupload.label.insert", "Добавить новые документы");
	    lang.put("fileupload.label.update", "Обновить документы");
	    lang.put("fileupload.label.users.notify", "Уведомить пользователей");
	    lang.put("fileupload.label.comment", "Комментарий");
	    lang.put("fileupload.label.users.to.notify",  "Уведомлённые пользователи");
	    lang.put("fileupload.label.users",  "Пользователи");
	    lang.put("fileupload.label.groups.to.notify","Groups to notify");
	    lang.put("fileupload.label.groups","Groups");
	    lang.put("fileupload.label.must.select.users",  "Вы должны выбрать пользователя для уведомления");
	    lang.put("fileupload.label.importZip", "Импортировать документы из ZIP-файла");
	    lang.put("fileupload.label.notify.comment", "Сообщение уведомления");
	    lang.put("fileupload.label.error.importing.zip", "Ошибка импорта файла");
	    lang.put("fileupload.label.error.move.file", "Ошибка перемещения файла");
	    lang.put("fileupload.label.error.move.mail", "Ошибка перемещения почты");
	    lang.put("fileupload.label.error.copy.file", "Ошибка копирования файла");
	    lang.put("fileupload.label.error.copy.mail", "Ошибка копирования почты");
	    lang.put("fileupload.label.error.restore.file", "Ошибка восстановления файла");
	    lang.put("fileupload.label.error.restore.mail", "Ошибка восстановления почты");
	    lang.put("fileupload.label.error.move.folder", "Ошибка перемещения папки");
	    lang.put("fileupload.label.error.copy.folder", "Ошибка копирования папки");
	    lang.put("fileupload.label.error.restore.folder", "Ошибка восстановления папки");
	    lang.put("fileupload.label.error.create.from.template", "Ошибка создания файл из шаблона");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Не разрешено перемещать в исходную или дочернюю папку");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Не разрешено перемещать в исходную папку");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Не разрешено создавать файл в исходной папке");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Свойства");
	    lang.put("tab.document.notes", "Заметки");
	    lang.put("tab.document.history", "История");
	    lang.put("tab.document.status.history", "Обновляю историю");
	    lang.put("tab.status.security.role", "Обновляю роли (безопасность)");
	    lang.put("tab.status.security.user", "Обновляю пользователей (безопасность)");
	    lang.put("tab.document.status.group.properties", "Обновляю группу свойств");
	    lang.put("tab.document.status.set.keywords", "Устанавливаю ключевые слова");
	    lang.put("tab.document.status.set.categories", "Updating categories");
	    lang.put("tab.document.status.get.version.history.size", "Обновляю размер истории документа");
	    lang.put("tab.document.status.purge.version.history", "Удаляю историю документа");
	    lang.put("tab.document.status.restore.version", "Восстанавливаю версию документа");
	    lang.put("tab.document.security", "Безопасность");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Свойства");
	    lang.put("tab.folder.security", "Безопасность");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Рабочий стол");
	    lang.put("tab.workspace.search", "Поиск");
	    lang.put("tab.workspace.dashboard", "Приборная доска");
	    lang.put("tab.workspace.administration", "Администрирование");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Название");
	    lang.put("document.folder", "Папка");
	    lang.put("document.size", "Размер");
	    lang.put("document.created", "Создано");
	    lang.put("document.lastmodified", "Модифицировано");
	    lang.put("document.mimetype", "MIME-тип");
	    lang.put("document.keywords", "Ключевые слова");
	    lang.put("document.by", "автор"); //??!!
	    lang.put("document.status", "Статус");
	    lang.put("document.status.checkout", "Отредактировано");
	    lang.put("document.status.locked", "Заблокировано");
	    lang.put("document.status.normal", "Доступно");
	    lang.put("document.subscribed", "Подписано");
	    lang.put("document.subscribed.yes", "Да");
	    lang.put("document.subscribed.no", "Нет");
	    lang.put("document.history.size", "Размер истории");
	    lang.put("document.subscribed.users", "Подписанные пользователи");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Добавить заметку");
	    lang.put("document.keywords.cloud", "Облако ключевых слов");
	    lang.put("document.categories", "Categories");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Название");
	    lang.put("folder.parent", "Родитель");
	    lang.put("folder.created", "Создано");
	    lang.put("folder.by", "автор");
	    lang.put("folder.subscribed", "Подписано");
	    lang.put("folder.subscribed.yes", "Да");
	    lang.put("folder.subscribed.no", "Нет");
	    lang.put("folder.subscribed.users", "Подписанные пользователи");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Folders");
	    lang.put("folder.number.documents", "Documents");
	    lang.put("folder.number.mails", "Mails");
	    
	    // Version
	    lang.put("version.name", "Версия");
	    lang.put("version.created", "Дата");
	    lang.put("version.author", "Автор");
	    lang.put("version.size", "Размер");
	    lang.put("version.purge.document", "Удалить историю");
	    lang.put("version.comment", "Комментарий");
	    
	    // Security
	    lang.put("security.label", "Безопасность");
	    lang.put("security.group.name", "Группа");
	    lang.put("security.group.permission.read", "Читать");
	    lang.put("security.group.permission.write", "Изменять");
	    lang.put("security.group.permission.delete", "Delete");
	    lang.put("security.group.permission.security", "Security");
	    lang.put("security.user.name", "Пользователь");
	    lang.put("security.user.permission.read", "Читать");
	    lang.put("security.user.permission.write", "Изменять");
	    lang.put("security.user.permission.delete", "Delete");
	    lang.put("security.user.permission.security", "Security");
	    lang.put("security.users", "Пользователи");
	    lang.put("security.groups", "Группы");
	    lang.put("security.recursive", "Рекурсивное изменение прав");
	    lang.put("secutiry.filter.by.users","Users filter");
	    lang.put("secutiry.filter.by.groups","Groups filter");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "От");
	    lang.put("mail.reply", "Ответ на");
	    lang.put("mail.to", "Адресат");
	    lang.put("mail.subject", "Тема");
	    lang.put("mail.attachment", "Вложения");
	    
	    // Error
	    lang.put("error.label", "В системе произошла ошибка");
	    lang.put("error.invocation", "Ошибка в передаче данных серверу");
	    
	    // About
	    lang.put("about.label", "Про OpenKM");
	    lang.put("about.loading", "Загрузка ...");
	    
	    // Logout
	    lang.put("logout.label", "Выход");
	    lang.put("logout.closed", "Сеанс работы с OpenKM завершен успешно.");
	    lang.put("logout.logout", "Сеанс работы с OpenKM завершается, подождите");
	    
	    // Confirm
	    lang.put("confirm.label", "Подтверждение");
	    lang.put("confirm.delete.folder", "Вы действительно хотите удалить папку?");
	    lang.put("confirm.delete.document", "Вы действительно хотите удалить документ?");
	    lang.put("confirm.delete.trash", "Вы действительно хотите очистить корзину?");
	    lang.put("confirm.purge.folder", "Вы действительно хотите удалить папку?");
	    lang.put("confirm.purge.document", "Вы действительно хотите удалить документ?");
	    lang.put("confirm.delete.propety.group", "Вы действительно хотите удалить группу свойств?");
	    lang.put("confirm.purge.version.history.document", "Вы действительно хотите удалить историю документа?");
	    lang.put("confirm.purge.restore.document", "Вы действительно хотите восстановить версию этого документа?");
	    lang.put("confirm.set.default.home", "Вы действительно хотите установить этот начальный элемент по умолчанию?");
	    lang.put("confirm.delete.saved.search", "Вы действительно хотите удалить этот сохранённый поиск?");
	    lang.put("confirm.delete.user.news", "Вы действительно хотите удалить новости пользователя?");
	    lang.put("confirm.delete.mail", "Вы действительно хотите удалить почтовое сообщение?");
	    lang.put("confirm.get.pooled.workflow.task","¿ Do you want to assign this task to you ?");
	    
	    // Search inputs
	    lang.put("search.context", "Контекст");
	    lang.put("search.content", "Содержимое");
	    lang.put("search.name", "Название");
	    lang.put("search.keywords", "Ключевые слова");
	    lang.put("search.folder", "Папка");
	    lang.put("search.category", "Category");
	    lang.put("search.results", "Результаты");
	    lang.put("search.to", "из");
	    lang.put("search.page.results", "Результатов на страницу");
	    lang.put("search.add.property.group", "Добавить группу свойств");
	    lang.put("search.mimetype", "MIME-тип");
	    lang.put("search.type", "Тип");
	    lang.put("search.type.document", "Документ");
	    lang.put("search.type.folder", "Папка");
	    lang.put("search.type.mail", "Почта");
	    lang.put("search.advanced", "Расширенный поиск");
	    lang.put("search.user", "Пользователь");
	    lang.put("search.date.and", "и");
	    lang.put("search.date.range", "Дата, между");
	    lang.put("search.save.as.news", "Сохранить как новости пользователя");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Фильтровать по папкам");
	    lang.put("search.category.filter", "Filter by category");
	    
	    // Search results
	    lang.put("search.result.name", "Название");
	    lang.put("search.result.score", "Релевантность");
	    lang.put("search.result.size", "Размер");
	    lang.put("search.result.date.update", "Дата обновления");
	    lang.put("search.result.author", "Автор");
	    lang.put("search.result.version", "Версия");
	    lang.put("search.result.path", "Путь");
	    lang.put("search.result.menu.download", "Загрузить");
	    lang.put("search.result.menu.go.folder", "Перейти к папке");
	    lang.put("search.result.menu.go.document", "Перейти к документу");
	    lang.put("search.result.status.findPaginated", "Обновляю поиск");
	    lang.put("search.result.status.runsearch", "Обновляю сохраненный поиск");
	    
	    // Search saved
	    lang.put("search.saved.run", "Выполнить");
	    lang.put("search.saved.delete", "Удалить");
	    lang.put("search.saved.status.getsearchs", "Обновляю сохраненные поиски");
	    lang.put("search.saved.status.savesearch", "Обновляю сохранить поиск"); // ??!!
	    lang.put("search.saved.status.deletesearch", "Удаляю сохраненный поиск");
	    lang.put("search.saved.status.getusernews", "Обновляю новости пользователя");
	    
	    // Button
	    lang.put("button.close", "Закрыть");
	    lang.put("button.logout", "Выйти");
	    lang.put("button.update", "Обновить");
	    lang.put("button.cancel", "Отменить");
	    lang.put("button.accept", "Принять");
	    lang.put("button.restore", "Восстановить");
	    lang.put("button.move", "Перенести");
	    lang.put("button.change", "Изменить");
	    lang.put("button.search", "Искать");
	    lang.put("button.save.search", "Сохранить поиск");
	    lang.put("button.view", "Просмотр");
	    lang.put("button.clean", "Очистить");
	    lang.put("button.add", "Добавить");
	    lang.put("button.delete", "Удалить");
	    lang.put("button.copy", "Копировать");
	    lang.put("button.create", "Создать");
	    lang.put("button.show", "Показать");
	    lang.put("button.memory", "Обновить");
	    lang.put("button.copy.clipboard", "Скопировать в буфер обмена");	
	    lang.put("button.start", "Начать");
	    lang.put("button.select", "Выбрать");
	    lang.put("button.test", "Test");
	    lang.put("button.next", "Next");
	    
	    // Group
	    lang.put("group.label", "Добавить группу свойств");
	    lang.put("group.group", "Группа");
	    lang.put("group.property.group", "Свойство");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Добавить закладку");
	    lang.put("bookmark.name", "Название");
	    lang.put("bookmark.edit.label", "Редактировать закладки");
	    lang.put("bookmark.path", "Путь");
	    lang.put("bookmark.type", "Тип");
	    lang.put("bookmark.type.document", "Документ");
	    lang.put("bookmark.type.folder", "Папка");
	    
	    // Notify
	    lang.put("notify.label", "Отсылаю ссылку на документ");
	    
	    // Status
	    lang.put("status.document.copied", "Документ помечен для копирования");
	    lang.put("status.document.cut", "Документ помечен для перемещения");
	    lang.put("status.folder.copied", "Папка помечена для копирования");
	    lang.put("status.folder.cut", "Папка помечена для перемещения");
	    lang.put("status.keep.alive.error", "Потеря соединения с сервером (поддержка соединения)");
	    lang.put("status.debug.enabled", "Отладка включена");
	    lang.put("status.debug.disabled", "Отладка выключена");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Воскресение");
	    lang.put("calendar.day.monday", "Понедельник");
	    lang.put("calendar.day.tuesday", "Вторник");
	    lang.put("calendar.day.wednesday", "Среда");
	    lang.put("calendar.day.thursday", "Четверг");
	    lang.put("calendar.day.friday", "Пятница");
	    lang.put("calendar.day.saturday", "Суббота");
	    lang.put("calendar.month.january", "Январь");
	    lang.put("calendar.month.february", "Февраль");
	    lang.put("calendar.month.march", "Март");
	    lang.put("calendar.month.april", "Апрель");
	    lang.put("calendar.month.may", "Май");
	    lang.put("calendar.month.june", "Июнь");
	    lang.put("calendar.month.july", "Июль");
	    lang.put("calendar.month.august", "Август");
	    lang.put("calendar.month.september", "Сентябрь");
	    lang.put("calendar.month.october", "Октябрь");
	    lang.put("calendar.month.november", "Ноябрь");
	    lang.put("calendar.month.december", "Декабрь");
	    
	    // Media player
	    lang.put("media.player.label", "Медиа-проигрыватель");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Просмотр картинок");
	    lang.put("image.viewer.zoom.in", "Увеличить");
	    lang.put("image.viewer.zoom.out", "Уменьшить");
	    
	    // Debug console
	    lang.put("debug.console.label", "Консоль отладки");
	    lang.put("debug.enable.disable", "CTRL+Z для включения/отключения режима отладки");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Общее");
	    lang.put("dashboard.tab.news", "Новости");
	    lang.put("dashboard.tab.user", "Пользователь");
	    lang.put("dashboard.tab.workflow", "Рабочий процесс");
	    lang.put("dashboard.tab.mail", "Почта");
	    lang.put("dashboard.tab.keymap", "Карта ключевых слов");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Новое");
	    lang.put("dashboard.user.locked.documents", "Заблокированные документы");
	    lang.put("dashboard.user.checkout.documents", "Выгруженные документы");
	    lang.put("dashboard.user.last.modified.documents", "Недавно модифицированные");
	    lang.put("dashboard.user.last.downloaded.documents", "Недавно скачанные документы");
	    lang.put("dashboard.user.subscribed.documents", "Подписанные документы");
	    lang.put("dashboard.user.subscribed.folders", "Подписанные папки");
	    lang.put("dashboard.user.last.uploaded.documents", "Последний раз загруженные документы");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Наиболее просматриваемые за прошлую неделю документы");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Наиболее просматриваемые за прошлый месяц документы");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Наиболее модифицируемые за прошлую неделю  документы");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Наиболее модифицируемые за прошлый месяц документы"); // error in english source!!!!
	    lang.put("dashboard.general.last.uploaded.documents", "Последние загруженные документы");
	    lang.put("dashboard.workflow.pending.tasks", "Задачи в очереди");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Unassigned pending tasks");
	    lang.put("dashboard.workflow.task", "Задача");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Название");
	    lang.put("dashboard.workflow.task.created", "Дата создания");
	    lang.put("dashboard.workflow.task.start", "Дата начала");
	    lang.put("dashboard.workflow.task.duedate", "Due date");
	    lang.put("dashboard.workflow.task.end", "Дата конца");
	    lang.put("dashboard.workflow.task.description", "Описание");
	    lang.put("dashboard.workflow.task.process.instance", "Экземпляр процесса");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Версия");
	    lang.put("dashboard.workflow.task.process.name", "Название");
	    lang.put("dashboard.workflow.task.process.description", "Описание");
	    lang.put("dashboard.workflow.task.process.data", "Данные");
	    lang.put("dashboard.workflow.comments", "Comments");
	    lang.put("dashboard.workflow.task.process.forms", "Form");
	    lang.put("dashboard.workflow.add.comment","Add comment");
	    lang.put("dashboard.workflow.task.process.definition", "Определение процесса");
	    lang.put("dashboard.workflow.task.process.path", "Путь");
	    lang.put("dashboard.refreshing", "Обновляюсь");
	    lang.put("dashboard.keyword", "Ключевые слова");
	    lang.put("dashboard.keyword.suggest", "Введите ключевое слово");
	    lang.put("dashboard.keyword.all", "Все ключевые слова");
	    lang.put("dashboard.keyword.top", "Наиболее частые ключевые слова");
	    lang.put("dashboard.keyword.related", "Схожие ключевые слова");
	    lang.put("dashboard.keyword.goto.document", "Перейти к документу");
	    lang.put("dashboard.keyword.clean.keywords", "Очистить ключевые слова");
	    lang.put("dashboard.mail.last.imported.mails", "Electronic mails");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Attachments");
	    
	    // Workflow
	    lang.put("workflow.label", "Начать рабочий процесс");
	    
	    // User configuration
	    lang.put("user.preferences.label", "User configuration");
	    lang.put("user.preferences.user", "User");
	    lang.put("user.preferences.password", "Password");
	    lang.put("user.preferences.mail", "E-mail");
	    lang.put("user.preferences.imap.host", "IMAP server");
	    lang.put("user.preferences.imap.user", "IMAP user name");
	    lang.put("user.preferences.imap.user.password", "IMAP user password");
	    lang.put("user.preferences.imap.folder", "IMAP folder");
	    lang.put("user.preferences.password.error", "Error: passwords are diferent");
	    lang.put("user.preferences.user.data", "User account");
	    lang.put("user.preferences.mail.data", "Mail account");
	    lang.put("user.preferences.imap.error", "All fields are obligatory to set the mail configurations");
	    lang.put("user.preferences.imap.password.error.void", "Password must not be empty on IMAP mail creation");
	    lang.put("user.preferences.imap.test.error","IMAP configuration error");
	    lang.put("user.preferences.imap.test.ok","IMAP configuration ok");

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Add thesaurus keyword");
	    lang.put("thesaurus.tab.tree", "Tree");
	    lang.put("thesaurus.tab.keywords", "Keywords");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Add category");
	    lang.put("categories.folder.error.delete", "Cannot delete category with documents");
	    
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
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Доступ к документ запрещен");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Блокировать документ запрещено");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Внутренняя ошибка репозитория");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Путь к документу не найден");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Version error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Доступ к документ запрещен");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Документ не найден");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Документ уже существует");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Блокировать документ запрещено");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Требуется разблокировать документ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Внутренняя ошибка репозитория");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Внутренняя ошибка приложения");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Путь к документу не найден");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Доступ к папке запрещен");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Папка не найдена");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Папка уже существует");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Внутренняя ошибка репозитория");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Внутренняя ошибка репозитория");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Путь к папке не найден");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Доступ к объекту запрещен");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Объект не найден");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Внутренняя ошибка репозитория");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Внутренняя ошибка репозитория");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Документ не найден");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Внутренняя ошибка репозитория");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Неподдерживаемый формат файла");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Документ уже существует");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Размер документа превышен");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Сессия закрыта");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Ошибка выполнения запроса");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Название сохраненного поиска должно быть уникальным");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Название закладки должно быть уникальным");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Сессия потеряна");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Внутренняя ошибка репозитория");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Внутренняя ошибка репозитория");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Ошибка I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Внутренняя ошибка репозитория");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.ORIGIN_OKMBookmarkService, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_QuotaExceed, "Error user quota exceed, contact with adminitrator");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error in browser configuration");
	  }
}
