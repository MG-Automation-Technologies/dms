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
 * Farsy (Persian)
 * 
 * @author unknown
 */
public class Lang_fa_FA {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "yyyy/MM/dd hh:mm:ss");
	    lang.put("general.day.pattern", "yyyy/MM/dd");
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
	    lang.put("leftpanel.label.taxonomy", "طبقه بندی");
	    lang.put("leftpanel.label.trash", "زباله");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "جستجوهای ذخیره شده");
	    lang.put("leftpanel.label.categories", "Categories");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Templates");
	    lang.put("leftpanel.label.my.documents", "My documents");
	    lang.put("leftpanel.label.user.search", "User news");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "ایجاد پوشه");
	    lang.put("tree.menu.directory.remove", "حذف");
	    lang.put("tree.menu.directory.rename", "تغییر نام");
	    lang.put("tree.menu.directory.refresh", "به روز رسانی");
	    lang.put("tree.menu.directory.move", "انتقال");
	    lang.put("tree.menu.directory.copy", "Copiar");
	    lang.put("tree.menu.directory.add.document", "ایجاد سند");
	    lang.put("tree.menu.add.bookmark", "Add bookmark");
	    lang.put("tree.menu.set.home", "Set default home");
	    lang.put("tree.menu.export", "Export to file");
	    lang.put("tree.status.refresh.folder", "به روز رسانی درخت");
	    lang.put("tree.status.refresh.create", "در حال ساخت پوشه");
	    lang.put("tree.status.refresh.delete", "در حال حذف پوشه");
	    lang.put("tree.status.refresh.rename", "در حال تغییر نام پوشه");
	    lang.put("tree.status.refresh.restore", "در حال بازیابی پوشه");
	    lang.put("tree.status.refresh.purge", "در حال پاکسازی پوشه");
	    lang.put("tree.status.refresh.get", "بازسازی پوشه");
	    lang.put("tree.folder.new", "پوشه جدید");
	    lang.put("tree.status.refresh.add.subscription", "Adding subscription");
		lang.put("tree.status.refresh.remove.subscription", "Deleting subscription");
		lang.put("tree.status.refresh.get.root", "Refreshing root folder");
		lang.put("tree.status.refresh.get.keywords", "Refreshing keywords");
	    lang.put("tree.status.refresh.get.user.home", "Getting user home");
	    lang.put("tree.status.refresh.purge.trash", "Cleaning trash");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "بازیابی");
	    lang.put("trash.menu.directory.purge", "پاکسازی");
	    lang.put("trash.menu.directory.purge.trash", "پاکسازی سطل زباله");
	    lang.put("trash.directory.select.label", "پوشه مورد نظر را برای انتقال، مشخش نمایید");
	    
	    // General menu
	    lang.put("general.menu.file", "فایل");
	    lang.put("general.menu.file.exit", "خروج");
			lang.put("general.menu.file.purge.trash", "پاکسازی سطل زباله");
			lang.put("general.menu.edit", "خروج");
			lang.put("general.menu.file.create.directory", "ایجاد پوشه");
			lang.put("general.menu.file.download.document", "Download document");
			lang.put("general.menu.file.download.document.pdf", "Download document as PDF");
			lang.put("general.menu.file.send.link", "Send document link");
			lang.put("general.menu.file.lock", "قفل");
			lang.put("general.menu.file.unlock", "کلید");
			lang.put("general.menu.file.add.document", "ایجاد سند");
			lang.put("general.menu.file.checkout", "ارسال");
			lang.put("general.menu.file.checkin", "دریافت");
			lang.put("general.menu.file.cancel.checkout", "لغو ارسال");
			lang.put("general.menu.file.delete", "حذف");
			lang.put("general.menu.file.refresh", "به روز رسانی");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "File uploader");
	    lang.put("general.menu.tools", "ابزار");
	    	lang.put("general.menu.tools.languages", "زبانها");
	    	lang.put("general.menu.tools.skin", "پوسته");
    			lang.put("general.menu.tools.skin.default", "اولیه");
    			lang.put("general.menu.tools.skin.default2", "آزمایش 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Medium font");
    			lang.put("general.menu.tools.skin.bigfont", "Big font");
    		lang.put("general.menu.debug.console", "Debug console");
    		lang.put("general.menu.administration", "Show administration");
    		lang.put("general.menu.tools.preferences", "Preferences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Bookmarks");
    		lang.put("general.menu.bookmark.home", "Home");
    		lang.put("general.menu.bookmark.default.home", "Set default home");
    		lang.put("general.menu.bookmark.edit", "Edit bookmarks");
	    lang.put("general.menu.help", "راهنما");
		  lang.put("general.menu.bug.report", "گزارش اشکال");
		  lang.put("general.menu.support.request", "درخواست کمک");
		  lang.put("general.menu.public.forum", "محل بحث عمومی");
		  lang.put("general.menu.project.web", "وب پروژه");
		  lang.put("general.menu.version.changes", "Version notes");
		  lang.put("general.menu.documentation", "مست");
		  lang.put("general.menu.about", "درباره ما");
	    lang.put("general.connected", "تماس با ما");
	    
	    // File Browser
	    lang.put("filebrowser.name", "نام");
	    lang.put("filebrowser.date.update", "به روز رسانی تاریخ");
	    lang.put("filebrowser.size", "اندازه");
	    lang.put("filebrowser.path", "مسیر");
	    lang.put("filebrowser.author", "مولف");
	    lang.put("filebrowser.version", "ورژن");
	    lang.put("filebrowser.menu.checkout", "ارسال");
	    lang.put("filebrowser.menu.checkin", "دریافت");
	    lang.put("filebrowser.menu.delete", "حذف");
	    lang.put("filebrowser.menu.rename", "تغییر نام");
	    lang.put("filebrowser.menu.checkout.cancel", "لغو ارسال");
	    lang.put("filebrowser.menu.lock", "قفل");
	    lang.put("filebrowser.menu.unlock", "کلیذ");
	    lang.put("filebrowser.menu.download", "Download");
	    lang.put("filebrowser.menu.move", "انتقال");
	    lang.put("filebrowser.menu.copy", "Copy");
	    lang.put("filebrowser.menu.create.from.template", "Create from template");
	    lang.put("filebrowser.menu.add.property.group", "Add property group");
	    lang.put("filebrowser.menu.remove.property.group", "Remove property group");
	    lang.put("filebrowser.menu.start.workflow", "Start workflow");
	    lang.put("filebrowser.menu.add.subscription", "Add subscription");
	    lang.put("filebrowser.menu.remove.subscription", "Remove subscrition");
	    lang.put("filebrowser.menu.add.bookmark", "Add bookmark");
	    lang.put("filebrowser.menu.set.home", "Set default home");
	    lang.put("filebrowser.menu.refresh", "به روز رسانی");
	    lang.put("filebrowser.menu.export", "Export to ZIP");
	    lang.put("filebrowser.menu.play", "Play");
	    lang.put("filebrowser.menu.image.viewer", "Image viewer");
		lang.put("filebrowser.status.refresh.folder", "به روز رسانی پوشه");
		lang.put("filebrowser.status.refresh.document", "به روز رسانی لیست اسناد");
		lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "حذف پوشه");
	    lang.put("filebrowser.status.refresh.delete.document", "حذف سند");
	    lang.put("filebrowser.status.refresh.checkout", "ارسال سند");
	    lang.put("filebrowser.status.refresh.lock", "در حال قفل نمودن");
	    lang.put("filebrowser.status.refresh.unlock", "در حال باز نمودن قفل");
		lang.put("filebrowser.status.refresh.document.rename", "در حال تغییر نام سند");
		lang.put("filebrowser.status.refresh.folder.rename", "در حال تغییر نام پوشه");
		lang.put("filebrowser.status.refresh.document.purge", "در حال پاکسازی سند");
		lang.put("filebrowser.status.refresh.folder.purge", "در حال حذف پوشه");
		lang.put("filebrowser.status.refresh.folder.get", "در حال درسافت پوشه");
		lang.put("filebrowser.status.refresh.document.get", "در حال دریافت سند");
		lang.put("filebrowser.status.refresh.add.subscription", "Adding subscription");
		lang.put("filebrowser.status.refresh.remove.subscription", "Deleting subscription");
		lang.put("filebrowser.status.refresh.get.user.home", "Getting user home");
		lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
		lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "ارسال");
	    lang.put("fileupload.status.sending", "در حال ارسال فایل");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "عملیات ارسال فایل به درستی به پایان رسد");
	    lang.put("fileupload.upload.status", "وضعیت ارسال");
	    lang.put("fileupload.upload.uploaded", "ارسال گردیده");
	    lang.put("fileupload.upload.completed", "ارسال پایان یافته است");
	    lang.put("fileupload.upload.runtime", "زمان اجرا");
	    lang.put("fileupload.upload.remaining", "باقیمانده");
	    lang.put("fileupload.button.close", "بستن");
	    lang.put("fileupload.button.add.other.file", "Add another file");
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
	    lang.put("fileupload.label.insert", "درج سند جدید");
	    lang.put("fileupload.label.update", "به روز رسانی اسناد");
	    lang.put("fileupload.label.users.notify", "Notify to users");
	    lang.put("fileupload.label.comment", "Comment");
	    lang.put("fileupload.label.users.to.notify",  "Users to notify");
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
	    lang.put("tab.document.properties", "خواص");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "تاریخچه");
	    lang.put("tab.document.status.history", "به روز رسانی تاریخچه");
	    lang.put("tab.status.security.role", "به روز رسانی قوانین امنیتی");
	    lang.put("tab.status.security.user", "به روز رسانی وضعیت امنیتی کاربر");
	    lang.put("tab.document.status.group.properties", "به روز رسانی خواص گروه");
	    lang.put("tab.document.status.set.keywords", "Setting keywords");
	    lang.put("tab.document.status.set.categories", "Updating categories");
	    lang.put("tab.document.status.get.version.history.size", "Refreshing document history size");
	    lang.put("tab.document.status.purge.version.history", "Compacting document history");
	    lang.put("tab.document.status.restore.version", "Restoring document version");
	    lang.put("tab.document.security", "امنیت");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "خواص");
	    lang.put("tab.folder.security", "امنیت");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "میز گار");
	    lang.put("tab.workspace.search", "جستجو");
	    lang.put("tab.workspace.dashboard", "DashBoard");
	    lang.put("tab.workspace.administration", "Administration");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "نام");
	    lang.put("document.folder", "پوشه");
	    lang.put("document.size", "اندازه");
	    lang.put("document.created", "ایجاد");
	    lang.put("document.lastmodified", "تغییر");
	    lang.put("document.mimetype", "MIME نوع");
	    lang.put("document.keywords", "کلمات کلیدی");
	    lang.put("document.by", "توسط");
	    lang.put("document.status", "وضعیت");
	    lang.put("document.status.checkout", "تغییر توسط");
	    lang.put("document.status.locked", "قفل شده توسط");
	    lang.put("document.status.normal", "موجود");
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
	    lang.put("folder.name", "نام");
	    lang.put("folder.parent", "پدر");
	    lang.put("folder.created", "ایجاد");
	    lang.put("folder.by", "توسط");
	    lang.put("folder.subscribed", "Subscribed");
	    lang.put("folder.subscribed.yes", "Yes");
	    lang.put("folder.subscribed.no", "No");
	    lang.put("folder.subscribed.users", "Subscribed users");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "ورژن");
	    lang.put("version.created", "تاریخ");
	    lang.put("version.author", "مولف");
	    lang.put("version.size", "اندازه");
	    lang.put("version.purge.document", "Compact history");
	    lang.put("version.comment", "Comment");
	    
	    // Security
	    lang.put("security.label", "امنیت");
	    lang.put("security.group.name", "گروه");
	    lang.put("security.group.permission.read", "خواندن");
	    lang.put("security.group.permission.write", "نوشتن");
	    lang.put("security.group.permission.delete", "Delete");
	    lang.put("security.user.name", "کاربر");
	    lang.put("security.user.permission.read", "خواندن");
	    lang.put("security.user.permission.write", "نوشتن");
	    lang.put("security.user.permission.delete", "Delete");
	    lang.put("security.users", "کاربران");
	    lang.put("security.groups", "گروه ها");
	    lang.put("security.recursive", "تغییر دسترسی بازگشتی");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "خطا در سیستم");
	    lang.put("error.invocation", "خطا در اتصال به سرور");
	    
	    // About
	    lang.put("about.label", "درباره ما");
	    lang.put("about.loading", "بارگزاری");
	    
	    // Logout
	    lang.put("logout.label", "خروج");
	    lang.put("logout.closed", "برنامه به صحت بسته شد");
	    lang.put("logout.logout", "OpenKM is logging out, please wait");
	    
	    // Confirm
	    lang.put("confirm.label", "تاییدیه");
	    lang.put("confirm.delete.folder", "آیا برای حذف پوشه مطمئن هستید");
	    lang.put("confirm.delete.document", "آیا برای خذف سند مطمئن هستید");
	    lang.put("confirm.delete.trash", "آیا برای حذف سطل زباله مطمئن هستید");
	    lang.put("confirm.purge.folder", "آیا مطمئن به پاکسازی پوشه هستید");
	    lang.put("confirm.purge.document", "آیه مطمئن به پاکسازی سند هستید");
	    lang.put("confirm.delete.propety.group", "آیا برای حذف گروه مطمئن می باشید");
	    lang.put("confirm.purge.version.history.document", "¿ Do you really want to delete document history ?");
	    lang.put("confirm.purge.restore.document", "¿ Do you really want to restore to this document version ?");
	    lang.put("confirm.set.default.home", "¿ Do you really want to set default home ?");
	    lang.put("confirm.delete.saved.search", "¿ Do you really want to delete saved search ?");
	    lang.put("confirm.delete.user.news", "¿ Do you really want to delete user news ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    lang.put("confirm.get.pooled.workflow.task","¿ Do you want to assign this task to you ?");
	    
	    // Search inputs
	    lang.put("search.context", "Context");
	    lang.put("search.content", "محتوا");
	    lang.put("search.name", "نام");
	    lang.put("search.keywords", "کلمات کلیدی");
	    lang.put("search.folder", "Folder");
	    lang.put("search.category", "Category");
	    lang.put("search.results", "نتایج");
	    lang.put("search.to", "به");
	    lang.put("search.page.results", "صفحات نتیجه");
	    lang.put("search.add.property.group", "درج گروه");
	    lang.put("search.mimetype", "Mime نوع");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "جستجوی پیشرفته");
	    lang.put("search.user", "کاربر");
	    lang.put("search.date.and", "and");
	    lang.put("search.date.range", "Date range between");
	    lang.put("search.save.as.news", "Save as user news");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    lang.put("search.category.filter", "Filter by category");
	    
	    // Search results
	    lang.put("search.result.name", "نام");
	    lang.put("search.result.score", "ارتباط");
	    lang.put("search.result.size", "اندازه");
	    lang.put("search.result.date.update", "تاریخ به روز رسانی");
	    lang.put("search.result.author", "مولف");
	    lang.put("search.result.version", "ورژن");
	    lang.put("search.result.path", "مسیر");
	    lang.put("search.result.menu.download", "Download");
	    lang.put("search.result.menu.go.folder", "برو به پوشه");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "به روز رسانی جستجو");
	    lang.put("search.result.status.runsearch", "به روز رسانی جستجوهای ذخیره شده");
	    
	    // Search saved
	    lang.put("search.saved.run", "اجرا");
	    lang.put("search.saved.delete", "حذف");
			lang.put("search.saved.status.getsearchs", "به روز رسانی جستجوهای ذخیره شده");
			lang.put("search.saved.status.savesearch", "به روز رسانی جستجوهای ذخیره شده");
			lang.put("search.saved.status.deletesearch", "حذف جستجوهای ذخیره شده");
			lang.put("search.saved.status.getusernews", "Refreshing user news");
	    
	    // Button
	    lang.put("button.close", "بستن");
	    lang.put("button.logout", "خروج");
	    lang.put("button.update", "به روز رسانی");
	    lang.put("button.cancel", "لغو");
	    lang.put("button.accept", "قبول");
	    lang.put("button.restore", "بازیابی");
	    lang.put("button.move", "انتقال");
	    lang.put("button.change", "تغییر");
	    lang.put("button.search", "جستجو");
	    lang.put("button.save.search", "ثبت جستجو");
	    lang.put("button.view", "مشاهده");
	    lang.put("button.clean", "پاک");
	    lang.put("button.add", "درج");
	    lang.put("button.delete", "حذف");
	    lang.put("button.copy", "Copy");
	    lang.put("button.create", "Create");
	    lang.put("button.show", "Show");
	    lang.put("button.memory", "Update");
	    lang.put("button.copy.clipboard", "Copy to clipboard");	
	    lang.put("button.start", "Start");
	    lang.put("button.select", "Select");
	    lang.put("button.test", "Test");
	    
	    // Group
	    lang.put("group.label", "درج گروه");
	    lang.put("group.group", "گروه");
	    lang.put("group.property.group", "خواص");
	    
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
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Unassigned pending tasks");
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
	    lang.put("dashboard.workflow.add.comment","Add comment");
	    lang.put("dashboard.workflow.task.process.definition", "Process definition");
	    lang.put("dashboard.workflow.task.process.path", "Path");
	    lang.put("dashboard.refreshing", "Refreshing");
	    lang.put("dashboard.keyword", "Keywords");
	    lang.put("dashboard.keyword.suggest", "Type the keyword");
	    lang.put("dashboard.keyword.all", "All keywords");
	    lang.put("dashboard.keyword.top", "Top keywords");
	    lang.put("dashboard.keyword.related", "Related keywords");
	    lang.put("dashboard.keyword.goto.document", "Go to document");
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
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "دسترسی به سند امکان پذیر نمیباشد");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "سند قفل میباشد");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "ایراد داخلی مخزن");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Version error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "دسترسی به سند امکان پذیر نمیباشد");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "سند یافت نشد");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "این سند موجود میباشد");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "سند قفل میباشد");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "سند محتاج به کلید میباشد");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "ایراد داخلی مخزن");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "ایراد داخلی برنامه");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "دسترسی به پوشه امکان پذیر نمیباشد");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "پوشه یافت نشد");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "این پوشه موجود میباشد");
		lang.put("OKM-" + ErrorCode.ORIGIN_OKMFolderService + ErrorCode.CAUSE_Repository, "ایراد داخلی مخزن");
		lang.put("OKM-" + ErrorCode.ORIGIN_OKMFolderService + ErrorCode.CAUSE_General, "ایراد داخلی");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Folder path not found");
		
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "دسترسی محدود");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "یافت نگردید");
		lang.put("OKM-" + ErrorCode.ORIGIN_OKMAuthServlet + ErrorCode.CAUSE_Repository, "ایراد داخلی مخزن");
		lang.put("OKM-" + ErrorCode.ORIGIN_OKMAuthServlet + ErrorCode.CAUSE_General, "ایراد داخلی");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "سند یافت نشد");
			lang.put("OKM-" + ErrorCode.ORIGIN_OKMPropertyGroupService + ErrorCode.CAUSE_General, "ایراد داخلی مخزن");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "این فرمت پشتیبانی نمیشود");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "این سند موجود میباشد");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "حجم سند زیاد است");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "زمان فعال به پایان رسیده است");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Generic error executing query");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "The stored search name must be unique");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "The bookmark name must be unique");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Session lost");
	    
	    lang.put("OKM-" + ErrorCode.ORIGIN_OKMDashboardService + ErrorCode.CAUSE_Repository, "ایراد داخلی مخزن");
	  }
}