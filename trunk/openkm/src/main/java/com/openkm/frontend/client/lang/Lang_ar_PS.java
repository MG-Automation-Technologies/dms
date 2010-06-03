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
 * Arabic (Palestine,Middle East)
 * by Mohammed Abu Lamdy From English(United Kingdom),by @author Paco Avila
 *
 */
public class Lang_ar_PS {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd-MM-yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd-MM-yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "OpenKM تحميل");
	    lang.put("startup.starting.loading", "OpenKM تشغيل تحميل");
	    lang.put("startup.taxonomy", "جلب التصنيف من الجذر");
	    lang.put("startup.categories", "جلب الفئات من الجذر");
	    lang.put("startup.thesaurus", "جلب موسوعة المفردات من الجذر");
	    lang.put("startup.template", "جلب القوالب من الجذر");
	    lang.put("startup.personal", "جلب خاص المستخدم من الجذر");
	    lang.put("startup.mail", "جلب البريد الالكتروني من الجذر ");
	    lang.put("startup.trash", "جلب المحذوفات من الجذر");
	    lang.put("startup.user.home", "جلب بيئة المستخدم");
	    lang.put("startup.bookmarks", "جلب المفضلة");
	    lang.put("startup.loading.taxonomy", "تحميل التصنيف");
	    lang.put("startup.loading.taxonomy.getting.folders", "تحميل التصنيف - جلب المجلدات");
	    lang.put("startup.loading.taxonomy.eval.params", "تحميل التصنيف - تمرير قيم للمتصفح");
	    lang.put("startup.loading.taxonomy.open.path", "تحميل التصنيف - فتح المسار");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "تحميل التصنيف - جلب مجلدات");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "تحميل التصنيف - جلب المستندات");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "تحميل التصنيف - جلب البريد");
	    lang.put("startup.loading.personal", "تحميل خاص فى المستخدم");
	    lang.put("startup.loading.mail", "تحميل الريد الالكتروني");
	    lang.put("startup.loading.categories", "تحميل الفئات");
	    lang.put("startup.loading.thesaurus", "تحميل موسوعة المفردات");
	    lang.put("startup.loading.templates", "تحميل القوالب");
	    lang.put("startup.loading.trash", "تحميل سلة المحذوفات");
	    lang.put("startup.loading.history.search", "تحميل سلسلة احداث عملية البحث");
	    lang.put("startup.loading.user.values", "تحميل قيم المستخدم");
	    lang.put("startup.keep.alive", "تحميل للابقاء على النظام");
	    
	    // Update notification
	    lang.put("openkm.update.available", "OpenKM يتوفر تحديث جديد");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "التصنيف");
	    lang.put("leftpanel.label.trash", "سلة المحذوفات");
	    lang.put("leftpanel.label.mail", "البريد الالكتروني");
	    lang.put("leftpanel.label.stored.search", "تخزين البحث");
	    lang.put("leftpanel.label.categories", "الفئات");
	    lang.put("leftpanel.label.thesaurus", "موسوعة مفردات");
	    lang.put("leftpanel.label.templates", "قوالب");
	    lang.put("leftpanel.label.my.documents", "المستندات");
	    lang.put("leftpanel.label.user.search", "أخبار المستخدم");
	    lang.put("leftpanel.label.all.repository", "كل المستودع");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "انشاء مجلد");
	    lang.put("tree.menu.directory.remove", "حذف");
	    lang.put("tree.menu.directory.rename", "اعادة تسمية");
	    lang.put("tree.menu.directory.refresh", "تحديث");
	    lang.put("tree.menu.directory.move", "نقل");
	    lang.put("tree.menu.directory.copy", "نسخ");
	    lang.put("tree.menu.directory.add.document", "اضافة مستند");
	    lang.put("tree.menu.add.bookmark", "اضافة إلى المفضلة");
	    lang.put("tree.menu.set.home", "تعيين الافتراضي");
	    lang.put("tree.menu.export", "تصدير إلى ملف");
	    lang.put("tree.status.refresh.folder", "تحديث شجرة المجلد");
	    lang.put("tree.status.refresh.create", "إنشاء مجلد");
	    lang.put("tree.status.refresh.delete", "حذف المجلد");
	    lang.put("tree.status.refresh.rename", "اعادة تسمية المجلد");
	    lang.put("tree.status.refresh.restore", "استعادة المجلد");
	    lang.put("tree.status.refresh.purge", "تطهير المجلد");
	    lang.put("tree.status.refresh.get", "تحديث المجلد");
	    lang.put("tree.folder.new", "مجلد جديد");
	    lang.put("tree.status.refresh.add.subscription", "إضافة اشتراك");
	    lang.put("tree.status.refresh.remove.subscription", "حذف اشتراك");
	    lang.put("tree.status.refresh.get.root", "تحديث ملجد الجذر");
	    lang.put("tree.status.refresh.get.keywords", "تحديث الكلمات المفتاحية");
	    lang.put("tree.status.refresh.get.user.home", "الحصول على مسار المستخدم");
	    lang.put("tree.status.refresh.purge.trash", "تنظيف سلة المحذوفات");
	    lang.put("tree.menu.directory.find.folder","Find folder");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "استعادة");
	    lang.put("trash.menu.directory.purge", "نظف");
	    lang.put("trash.menu.directory.purge.trash", "تنظيف سلة المحذوفات");
	    lang.put("trash.directory.select.label", "حدد وجهة المجلد");
	    
	    // General menu
	    lang.put("general.menu.file", "ملف");
	    	lang.put("general.menu.file.exit", "خروج");
	    	lang.put("general.menu.file.purge.trash", "تنظيف سلة المحذوفات");
	    lang.put("general.menu.edit", "تعديل");
			lang.put("general.menu.file.create.directory", "انشاء مجلد");
			lang.put("general.menu.file.download.document", "تحميل المستند");
			lang.put("general.menu.file.download.document.pdf", "PDFتحميل المستند");
			lang.put("general.menu.file.send.link", "أرسل وصلة المستند");
			lang.put("general.menu.file.lock", "قفل");
			lang.put("general.menu.file.unlock", "فتح القفل");
			lang.put("general.menu.file.add.document", "اضافة مستند");
			lang.put("general.menu.file.checkout", "سحب");
			lang.put("general.menu.file.checkin", "تم المراجعة");
			lang.put("general.menu.file.cancel.checkout", "إلغاء السحب");
			lang.put("general.menu.file.delete", "حذف");
			lang.put("general.menu.file.refresh", "نحديث");
			lang.put("general.menu.file.scanner", "ماسح ضوئي");
			lang.put("general.menu.file.uploader", "رفع الملف");
	    lang.put("general.menu.tools", "أدوات");
	    	lang.put("general.menu.tools.languages", "لغات");
	    	lang.put("general.menu.tools.skin", "تغير شكل الواجهة");
    			lang.put("general.menu.tools.skin.default", "افتراضيا");
    			lang.put("general.menu.tools.skin.default2", "افتراضيا 2");
    			lang.put("general.menu.tools.skin.mediumfont", "الخط المتوسط");
    			lang.put("general.menu.tools.skin.bigfont", "خط كبير");
    		lang.put("general.menu.debug.console", "التصحيح");
    		lang.put("general.menu.administration", "إظهار الادارة");
    		lang.put("general.menu.tools.preferences", "تفضيلات");
    			lang.put("general.menu.tools.user.preferences", "تكوين اعدادات المستخدم");
    	lang.put("general.menu.bookmark", "المفضلة");
	    	lang.put("general.menu.bookmark.home", "المنزل");
	    	lang.put("general.menu.bookmark.default.home", "وضع المسار الافتراضي");
	    	lang.put("general.menu.bookmark.edit", "تعديل المفضلة");
	    lang.put("general.menu.help", "مساعدة");
		    lang.put("general.menu.bug.report", "تقرير الخطأ");
		    lang.put("general.menu.support.request", "طلب دعم");
		    lang.put("general.menu.public.forum", "المنتدي العام");
		    lang.put("general.menu.project.web", "مشروع ويب");
		    lang.put("general.menu.version.changes", "ملاحظات الإصدار");
		    lang.put("general.menu.documentation", "الوثائق");
		    lang.put("general.menu.about", "OpenKM عن");
	    lang.put("general.connected", "متصل كـ");
	    
	    // File Browser
	    lang.put("filebrowser.name", "اسم");
	    lang.put("filebrowser.date.update", "تحديث التاريخ");
	    lang.put("filebrowser.size", "الحجم");
	    lang.put("filebrowser.path", "المسار");
	    lang.put("filebrowser.author", "الكاتب");
	    lang.put("filebrowser.version", "اصدار");
	    lang.put("filebrowser.menu.checkout", "سحب");
	    lang.put("filebrowser.menu.checkin", "تم المراجعة");
	    lang.put("filebrowser.menu.delete", "حذف");
	    lang.put("filebrowser.menu.rename", "اعادة تسمية");
	    lang.put("filebrowser.menu.checkout.cancel", "إلغاءالسحب");
	    lang.put("filebrowser.menu.lock", "قفل");
	    lang.put("filebrowser.menu.unlock", "فتح القفل");
	    lang.put("filebrowser.menu.download", "تحميل");
	    lang.put("filebrowser.menu.move", "نقل");
	    lang.put("filebrowser.menu.copy", "نسخ");
	    lang.put("filebrowser.menu.create.from.template", "انشاء من قالب");
	    lang.put("filebrowser.menu.add.property.group", "إضافة خاصية المجموعة");
	    lang.put("filebrowser.menu.remove.property.group", "ازالة خاصية المجموعة");
	    lang.put("filebrowser.menu.start.workflow", "بدء سير العمل");
	    lang.put("filebrowser.menu.add.subscription", "إضافة اشتراك");
	    lang.put("filebrowser.menu.remove.subscription", "إلغاء اشتراك");
	    lang.put("filebrowser.menu.add.bookmark", "اضافة مفضلة");
	    lang.put("filebrowser.menu.set.home", "وضع البيئة الافتراضية");
	    lang.put("filebrowser.menu.refresh", "تحديث");
	    lang.put("filebrowser.menu.export", "تصدير إلى ملف مضغوط");
	    lang.put("filebrowser.menu.play", "مشغل وسائط متعددة");
	    lang.put("filebrowser.menu.image.viewer", "عارض الصور");
	    lang.put("filebrowser.status.refresh.folder", "تحديث قائمة المجلد");
	    lang.put("filebrowser.status.refresh.document", "تحديث قائمة المستند");
	    lang.put("filebrowser.status.refresh.mail", "تحديث قائمة البريد");
	    lang.put("filebrowser.status.refresh.delete.folder", "حذف المجلد");
	    lang.put("filebrowser.status.refresh.delete.document", "حذف المستند");
	    lang.put("filebrowser.status.refresh.checkout", "سحب المستند");
	    lang.put("filebrowser.status.refresh.lock", "اقفال المستند");
	    lang.put("filebrowser.status.refresh.unlock", "فتح اقفال المستند");
	    lang.put("filebrowser.status.refresh.document.rename", "اعادة تسمية المستند");
	    lang.put("filebrowser.status.refresh.folder.rename", "اعادة تسمية المجلد");
	    lang.put("filebrowser.status.refresh.document.purge", "حذف المستند");
	    lang.put("filebrowser.status.refresh.folder.purge", "حذف المجلد");
	    lang.put("filebrowser.status.refresh.folder.get", "تحديث المجلد");
	    lang.put("filebrowser.status.refresh.document.get", "رفع المستند");
	    lang.put("filebrowser.status.refresh.add.subscription", "إضافة اشتراك");
	    lang.put("filebrowser.status.refresh.remove.subscription", "حذف الاشتراك");
	    lang.put("filebrowser.status.refresh.get.user.home", "الحصول على مسار المستخدم");
	    lang.put("filebrowser.status.refresh.delete.mail", "حذف البريد");
	    lang.put("filebrowser.status.refresh.mail.purge", "تنظيف البريد");
	    
	    // File Upload
	    lang.put("fileupload.send", "ارسل");
	    lang.put("fileupload.status.sending", "...رفع الملف");
	    lang.put("fileupload.status.indexing", "...فهرسة الملف");
	    lang.put("fileupload.status.ok", "تم رفع الملف  بنجاح");
	    lang.put("fileupload.upload.status", "حالة الرفع");
	    lang.put("fileupload.upload.uploaded", "تم الرفع");
	    lang.put("fileupload.upload.completed", "تم الانتهاء من الرفع");
	    lang.put("fileupload.upload.runtime", "وقت التشغيل");
	    lang.put("fileupload.upload.remaining", "المتبقية");
	    lang.put("fileupload.button.close", "اغلاق");
	    lang.put("fileupload.button.add.other.file", "اضافة ملف اخر");
	    lang.put("fileupload.status.move.file", "...نقل الملف");
	    lang.put("fileupload.status.move.mail", "...نقل البريد");
	    lang.put("fileupload.status.copy.file", "...نسخ الملف");
	    lang.put("fileupload.status.copy.mail", "...نسخ البريد");
	    lang.put("fileupload.status.restore.file", "...استعادة الملف");
	    lang.put("fileupload.status.restore.mail", "...استعادة البريد");
	    lang.put("fileupload.status.move.folder", "...نقل المجلد");
	    lang.put("fileupload.status.copy.folder", "...نسخ المجلد");
	    lang.put("fileupload.status.restore.folder", "...استعادة المجلد");
	    lang.put("fileupload.status.create.from.template", "...انشاء الملف من القالب");
	    lang.put("fileupload.status.of", "من");
	    lang.put("fileupload.label.insert", "اضافة مستندات جديدة");
	    lang.put("fileupload.label.update", "تحديث المستندات");
	    lang.put("fileupload.label.users.notify", "إبلاغ المستخدمين");
	    lang.put("fileupload.label.comment", "تعليق");
	    lang.put("fileupload.label.users.to.notify",  "إعلام المستخدمين");
	    lang.put("fileupload.label.users",  "المستخدمين");
	    lang.put("fileupload.label.groups.to.notify","Groups to notify");
	    lang.put("fileupload.label.groups","Groups");
	    lang.put("fileupload.label.must.select.users",  "يجب تحديد بعض المستخدمين للإبلاغ");
	    lang.put("fileupload.label.importZip", "استيراد المستندات من ملف مضغوط");
	    lang.put("fileupload.label.notify.comment", "رسالة التبليغ");
	    lang.put("fileupload.label.error.importing.zip", "خطأ فى استيراد الملف");
	    lang.put("fileupload.label.error.move.file", "خطأ فى نقل الملف");
	    lang.put("fileupload.label.error.move.mail", "خطأ فى نقل البريد");
	    lang.put("fileupload.label.error.copy.file", "خطأ فى نسخ الملف");
	    lang.put("fileupload.label.error.copy.mail", "خطأ فى نسخ البريد");
	    lang.put("fileupload.label.error.restore.file", "خطأ فى استعادة الملف");
	    lang.put("fileupload.label.error.restore.mail", "خطأ فى استعادة البريد");
	    lang.put("fileupload.label.error.move.folder", "خطأ فى نقل المجلد");
	    lang.put("fileupload.label.error.copy.folder", "خطأ فى نسخ المجلد");
	    lang.put("fileupload.label.error.restore.folder", "خطأ فى استعادة المجلد");
	    lang.put("fileupload.label.error.create.from.template", "خطأ انشاء الملف من القالب");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "لا يسمح للنقل على مجلد الأصل أو  المجلد الفرعي");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "لا يسمح النقل على المجلد الأصل");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "لا يسمح لإنشاء ملف في المجلد الأصل");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "خصائص");
	    lang.put("tab.document.notes", "ملاحظات");
	    lang.put("tab.document.history", "سلسلة الاحداث");
	    lang.put("tab.document.status.history", "تحديث سلسلة الاحداث");
	    lang.put("tab.status.security.role", "تحديث قوانين الأمن");
	    lang.put("tab.status.security.user", "حديث أمن المستخدمين");
	    lang.put("tab.document.status.group.properties", "تحديث لخصائص المميزة للمجوعة");
	    lang.put("tab.document.status.set.keywords", "اعدادات الكلمات المفتاحية");
	    lang.put("tab.document.status.set.categories", "تحديث الفئات");
	    lang.put("tab.document.status.get.version.history.size", "اعادة تحديث حجم مستند فى سلسلة الاحداث");
	    lang.put("tab.document.status.purge.version.history", "إزالة تتبع سلسلة الاحداث");
	    lang.put("tab.document.status.restore.version", "استعادة اصدار المستند");
	    lang.put("tab.document.security", "الأمن");
	    lang.put("tab.document.preview", "عرض");
	    lang.put("tab.folder.properties", "خصائص");
	    lang.put("tab.folder.security", "الأمن");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "سطح المكتب");
	    lang.put("tab.workspace.search", "البحث");
	    lang.put("tab.workspace.dashboard", "لوحة الاعدادات");
	    lang.put("tab.workspace.administration", "إدارة");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "اسم");
	    lang.put("document.folder", "المجلد");
	    lang.put("document.size", "الحجم");
	    lang.put("document.created", "انشاء");
	    lang.put("document.lastmodified", "تعديل");
	    lang.put("document.mimetype", "تعريف نوع الملفات");
	    lang.put("document.keywords", "كلمات مفتاحية");
	    lang.put("document.by", "من قبل");
	    lang.put("document.status", "حالة");
	    lang.put("document.status.checkout", "تعديل بواسطة");
	    lang.put("document.status.locked", "اقفل بواسطة");
	    lang.put("document.status.normal", "متاح");
	    lang.put("document.subscribed", "اشتراك");
	    lang.put("document.subscribed.yes", "نعم");
	    lang.put("document.subscribed.no", "لا");
	    lang.put("document.history.size", "تاريخ الحجم");
	    lang.put("document.subscribed.users", "المستخدمون المشتركين");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "اضافة مذكرة");
	    lang.put("document.keywords.cloud", "الكلمات المفتاحية السحابية");
	    lang.put("document.categories", "الفئات");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "اسم");
	    lang.put("folder.parent", "الاصل");
	    lang.put("folder.created", "انشاء");
	    lang.put("folder.by", "من قبل");
	    lang.put("folder.subscribed", "اشتراك");
	    lang.put("folder.subscribed.yes", "نعم");
	    lang.put("folder.subscribed.no", "لا");
	    lang.put("folder.subscribed.users", "المستخدمون المشتركين");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Folders");
	    lang.put("folder.number.documents", "Documents");
	    lang.put("folder.number.mails", "Mails");
	    
	    // Version
	    lang.put("version.name", "الاصدار");
	    lang.put("version.created", "التاريخ");
	    lang.put("version.author", "الكاتب");
	    lang.put("version.size", "الحجم");
	    lang.put("version.purge.document", "تاريخ سجل محكم");
	    lang.put("version.comment", "تعليق");
	    
	    // Security
	    lang.put("security.label", "حماية");
	    lang.put("security.group.name", "مجموعة");
	    lang.put("security.group.permission.read", "قراءة");
	    lang.put("security.group.permission.write", "كتابة");
	    lang.put("security.group.permission.delete", "حذف");
	    lang.put("security.group.permission.security", "Security");
	    lang.put("security.user.name", "مستخدم");
	    lang.put("security.user.permission.read", "قراءة");
	    lang.put("security.user.permission.write", "كتابة");
	    lang.put("security.user.permission.delete", "حذف");
	    lang.put("security.user.permission.security", "Security");
	    lang.put("security.users", "مستخدمون");
	    lang.put("security.groups", "مجموعات");
	    lang.put("security.recursive", "تغير الإذن بشكل تكراري");
	    lang.put("secutiry.filter.by.users","Users filter");
	    lang.put("secutiry.filter.by.groups","Groups filter");
	    
	    // Preview
	    lang.put("preview.unavailable", "المعاينة غير متوفرة");

	    // Mail
	    lang.put("mail.from", "من");
	    lang.put("mail.reply", "الرد علي");
	    lang.put("mail.to", "إلي");
	    lang.put("mail.subject", "الموضوع");
	    lang.put("mail.attachment", "المرفقات");
	    
	    // Error
	    lang.put("error.label", "انتج النظام خطأ");
	    lang.put("error.invocation", "خطأ الاتصال فى المقلم");
	    
	    // About
	    lang.put("about.label", "OpenKM عن");
	    lang.put("about.loading", "...تحميل");
	    
	    // Logout
	    lang.put("logout.label", "خروج");
	    lang.put("logout.closed", "الاغلاق بشكل صحيح OpenKM تم");
	    lang.put("logout.logout", "OpenKM انتظر , تم تسجيل الخروج");
	    
	    // Confirm
	    lang.put("confirm.label", "تأكيد");
	    lang.put("confirm.delete.folder", "¿ هل تريد حقا ان تحذف المجلد  ؟");
	    lang.put("confirm.delete.document", "¿ هل تريد حقا ان تحذف المستند  ؟");
	    lang.put("confirm.delete.trash", "¿ هل تريد حقا ان تفرغ سلة المحذوفات ؟");
	    lang.put("confirm.purge.folder", "¿ هل تريد حقا ان تحذف المجلد ؟");
	    lang.put("confirm.purge.document", "¿ هل تريد حقا ان تحذف المستند ؟");
	    lang.put("confirm.delete.propety.group", "¿ هل تريد حقا ان تحذف الخصائص المميزة للمجموعة ؟");
	    lang.put("confirm.purge.version.history.document", "¿ هل تريد حقا ان تحذف سلسلة تاريخ الاحداث للمستند ؟");
	    lang.put("confirm.purge.restore.document", "¿ هل تريد حقا ان استعادة اصدار هذا المستند ؟");
	    lang.put("confirm.set.default.home", "¿ هل تريد حقا وضع المسار الافتراضي؟");
	    lang.put("confirm.delete.saved.search", "¿ هل تريد حقا حذف عملية البحث المحفوظة؟");
	    lang.put("confirm.delete.user.news", "¿ هل تريد حقا ان تحذف اخبار المستخدم ؟");
	    lang.put("confirm.delete.mail", "¿ هل تريد حقا ان تحذف البريد؟");
	    lang.put("confirm.get.pooled.workflow.task","¿ هل ترغب في إسناد هذه المهمة لك؟");
	    
	    // Search inputs
	    lang.put("search.context", "السياق");
	    lang.put("search.content", "المحتوي");
	    lang.put("search.name", "الاسم");
	    lang.put("search.keywords", "كلمات مفتاحية");
	    lang.put("search.folder", "مجلد");
	    lang.put("search.category", "فئة");
	    lang.put("search.results", "النتائج");
	    lang.put("search.to", "إلى");
	    lang.put("search.page.results", "صفحة النتائج");
	    lang.put("search.add.property.group", "اضافة خصائص مميزة للمجموعة");
	    lang.put("search.mimetype", "تعريف نوع الملفات");
	    lang.put("search.type", "النوع");
	    lang.put("search.type.document", "المستند");
	    lang.put("search.type.folder", "المجلد");
	    lang.put("search.type.mail", "البريد");
	    lang.put("search.advanced", "بحث متقدم");
	    lang.put("search.user", "مستخدم");
	    lang.put("search.date.and", "و");
	    lang.put("search.date.range", "تتراوح ما بين تاريخ");
	    lang.put("search.save.as.news", "حفظ كأخبار المستخدم");

	    // search folder filter popup
	    lang.put("search.folder.filter", "تصفية حسب المجلد");
	    lang.put("search.category.filter", "تصفية حسب الفئة");
	    
	    // Search results
	    lang.put("search.result.name", "الاسم");
	    lang.put("search.result.score", "الصلة بالموضوع");
	    lang.put("search.result.size", "الحجم");
	    lang.put("search.result.date.update", "تحديث التاريخ");
	    lang.put("search.result.author", "الكاتب");
	    lang.put("search.result.version", "الاصدار");
	    lang.put("search.result.path", "المسار");
	    lang.put("search.result.menu.download", "تحميل");
	    lang.put("search.result.menu.go.folder", "اذهب إلى المجلد");
	    lang.put("search.result.menu.go.document", "اذهب إلى المستند");
	    lang.put("search.result.status.findPaginated", "تحديث عملية البحث");
	    lang.put("search.result.status.runsearch", "حفظ تحديث عملية البحث");
	    
	    // Search saved
	    lang.put("search.saved.run", "تشغيل");
	    lang.put("search.saved.delete", "حذف");
	    lang.put("search.saved.status.getsearchs", "اعادة تحديث حفظ عملية البحث");
	    lang.put("search.saved.status.savesearch", "تحديث حفظ البحث");
	    lang.put("search.saved.status.deletesearch", "حذف نتائج البحث المخزنة");
	    lang.put("search.saved.status.getusernews", "اعادة تحديث اخبار المستخدم");
	    
	    // Button
	    lang.put("button.close", "اغلاق");
	    lang.put("button.logout", "خروج");
	    lang.put("button.update", "تحديث");
	    lang.put("button.cancel", "اغلاق");
	    lang.put("button.accept", "قبول");
	    lang.put("button.restore", "استعادة");
	    lang.put("button.move", "نقل");
	    lang.put("button.change", "تغير");
	    lang.put("button.search", "بحث");
	    lang.put("button.save.search", "حفظ البحث");
	    lang.put("button.view", "عرض");
	    lang.put("button.clean", "تنظيف");
	    lang.put("button.add", "اضافة");
	    lang.put("button.delete", "حذف");
	    lang.put("button.copy", "نسخ");
	    lang.put("button.create", "انشاء");
	    lang.put("button.show", "عرض");
	    lang.put("button.memory", "تحديث");
	    lang.put("button.copy.clipboard", "نسخ إلى لوح الحافظة");	
	    lang.put("button.start", "بدء");
	    lang.put("button.select", "اختار");
	    lang.put("button.test", "اختبار");
	    
	    // Group
	    lang.put("group.label", "اضافة صفة مميزة للمجموعة");
	    lang.put("group.group", "مجموعة");
	    lang.put("group.property.group", "صفة مميزة");
		lang.put("general.menu.file.scanner", "ماسح ضوئي");
	    
	    // Bookmark
	    lang.put("bookmark.label", "اضافة مفضلة");
	    lang.put("bookmark.name", "اسم");
	    lang.put("bookmark.edit.label", "تعديل المفضلة");
	    lang.put("bookmark.path", "مسار");
	    lang.put("bookmark.type", "نوع");
	    lang.put("bookmark.type.document", "مستند");
	    lang.put("bookmark.type.folder", "مجلد");
	    
	    // Notify
	    lang.put("notify.label", "تم ارسال رابط المستند");
	    
	    // Status
	    lang.put("status.document.copied", "المستند جهز للنسخ");
	    lang.put("status.document.cut", "المستند جهز للقص");
	    lang.put("status.folder.copied", "المجلد جهز للنسخ");
	    lang.put("status.folder.cut", "المجلد جهز للقص");
	    lang.put("status.keep.alive.error", "كشف فقد الاتصال إلى الملقم ... الإبقاء على الجلسة");
	    lang.put("status.debug.enabled", "تفعيل التصحيح");
	    lang.put("status.debug.disabled", "إلغاء التصحيح");
	    lang.put("status.network.error.detected", "كشف خطأ فى الشبكة");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "الاحد");
	    lang.put("calendar.day.monday", "الاثنين");
	    lang.put("calendar.day.tuesday", "الثلاثاء");
	    lang.put("calendar.day.wednesday", "الأربعاء");
	    lang.put("calendar.day.thursday", "الخميس");
	    lang.put("calendar.day.friday", "الجمعة");
	    lang.put("calendar.day.saturday", "السبت");
	    lang.put("calendar.month.january", "يناير");
	    lang.put("calendar.month.february", "فبراير");
	    lang.put("calendar.month.march", "مارس");
	    lang.put("calendar.month.april", "أبريل");
	    lang.put("calendar.month.may", "مايو");
	    lang.put("calendar.month.june", "يونيو");
	    lang.put("calendar.month.july", "يوليو");
	    lang.put("calendar.month.august", "أغسطس");
	    lang.put("calendar.month.september", "سبتمبر");
	    lang.put("calendar.month.october", "أكتوبر");
	    lang.put("calendar.month.november", "نوفمبر");
	    lang.put("calendar.month.december", "ديسمبر");
	    
	    // Media player
	    lang.put("media.player.label", "مشغل وسائط المتعددة");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "عارض الصور");
	    lang.put("image.viewer.zoom.in", "تكبير");
	    lang.put("image.viewer.zoom.out", "تصغير");
	    
	    // Debug console
	    lang.put("debug.console.label", "التصحيح");
	    lang.put("debug.enable.disable", "CTRL+Z لتفعيل او الغاء التصحيح اضغط");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "عام");
	    lang.put("dashboard.tab.news", "الاخبار");
	    lang.put("dashboard.tab.user", "المستخدم");
	    lang.put("dashboard.tab.workflow", "سير العمل");
	    lang.put("dashboard.tab.mail", "البريد");
	    lang.put("dashboard.tab.keymap", "خريطة الكلمات المفتاحية");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "جديد");
	    lang.put("dashboard.user.locked.documents", "وثائق مقفلة");
	    lang.put("dashboard.user.checkout.documents", "سحب");
	    lang.put("dashboard.user.last.modified.documents", "آخر تعديل على المستندات");
	    lang.put("dashboard.user.last.downloaded.documents", "آخر لمستندات المحملة");
	    lang.put("dashboard.user.subscribed.documents", "وثائق باشتراك");
	    lang.put("dashboard.user.subscribed.folders", "المجلدات باشتراك");
	    lang.put("dashboard.user.last.uploaded.documents", "آخر المستندات المرفوعة");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "في الاسبوع الماضي أعلى عرض المستندات");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "في الشهر الماضي أعلى عرض المستندات");
	    lang.put("dashboard.general.last.week.top.modified.documents", "في الاسبوع الماضي أعلى تعديل المستندات");
	    lang.put("dashboard.general.last.month.top.modified.documents", "في الشهر الماضي أعلى عرض المستندات");
	    lang.put("dashboard.general.last.uploaded.documents", "آخر المستندات المرفوعة");
	    lang.put("dashboard.workflow.pending.tasks", "المهام فى انتظار");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "المهام فى حالة عدم انتظار الغير معينة");
	    lang.put("dashboard.workflow.task", "مهمة");
	    lang.put("dashboard.workflow.task.id", "بطاقة اتعريف");
	    lang.put("dashboard.workflow.task.name", "اسم");
	    lang.put("dashboard.workflow.task.created", "تاريخ الإنشاء");
	    lang.put("dashboard.workflow.task.start", "تاريخ البدء");
	    lang.put("dashboard.workflow.task.duedate", "تاريخ الاستحقاق");
	    lang.put("dashboard.workflow.task.end", "نهاية التاريخ");
	    lang.put("dashboard.workflow.task.description", "وصف");
	    lang.put("dashboard.workflow.task.process.instance", "حالة العملية");
	    lang.put("dashboard.workflow.task.process.id", "بطاقة التعريف");
	    lang.put("dashboard.workflow.task.process.version", "الاصدار");
	    lang.put("dashboard.workflow.task.process.name", "اسم");
	    lang.put("dashboard.workflow.task.process.description", "وصف");
	    lang.put("dashboard.workflow.task.process.data", "تاريخ");
	    lang.put("dashboard.workflow.comments", "تعليقات");
	    lang.put("dashboard.workflow.task.process.forms", "نماذج");
	    lang.put("dashboard.workflow.add.comment","اضافة تعليق");
	    lang.put("dashboard.workflow.task.process.definition", "تعريف العملية");
	    lang.put("dashboard.workflow.task.process.path", "مسار");
	    lang.put("dashboard.refreshing", "اعادة تحديث");
	    lang.put("dashboard.keyword", "الكلمات المفتاحية");
	    lang.put("dashboard.keyword.suggest", "نوع الكلمة المفتاحية");
	    lang.put("dashboard.keyword.all", "كل الكلمات المفتاحية");
	    lang.put("dashboard.keyword.top", "أفضل الكلمات المفتاحية");
	    lang.put("dashboard.keyword.related", "كلمات مفتاحية ذات علاقة");
	    lang.put("dashboard.keyword.goto.document", "اذهب إلى المستند");
	    lang.put("dashboard.keyword.clean.keywords", "مسح الكلمات المفتاحية");
	    lang.put("dashboard.mail.last.imported.mails", "البريد الالكتروني");
	    lang.put("dashboard.mail.last.imported.attached.documents", "المرفقات");
	    
	    // Workflow
	    lang.put("workflow.label", "بدء سير العمل");
	    
	    // User configuration
	    lang.put("user.preferences.label", "اعدادات المستخدم");
	    lang.put("user.preferences.user", "المستخدم");
	    lang.put("user.preferences.password", "كلمة المرور");
	    lang.put("user.preferences.mail", "البريد الالكتروني");
	    lang.put("user.preferences.imap.host", "IMAP مقلم");
	    lang.put("user.preferences.imap.user", "IMAP اسم مستخدم");
	    lang.put("user.preferences.imap.user.password", "IMAP كلمة المرور");
	    lang.put("user.preferences.imap.folder", "IMAP مجلد");
	    lang.put("user.preferences.password.error", "خطأ: كلمة المرور مختلفة");
	    lang.put("user.preferences.user.data", "حساب المستخدم");
	    lang.put("user.preferences.mail.data", "بريد المستخدم");
	    lang.put("user.preferences.imap.error", "جميع المجالات واجبة لتعيين تكوينات البريد");
	    lang.put("user.preferences.imap.password.error.void", "IMAPكلمة المرور لا يجب ان تترك بلا قيمة فى انشاء بريد");
	    lang.put("user.preferences.imap.test.error","IMAP الاعدادات خطأ");
	    lang.put("user.preferences.imap.test.ok","IMAP الاعدادات صحيحة");

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "اضافة كلمات مفتاحية");
	    lang.put("thesaurus.tab.tree", "شجرة");
	    lang.put("thesaurus.tab.keywords", "كلمات مفتاحية");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "اضافة فئة");
	    lang.put("categories.folder.error.delete", "غير قادر على حذف الفئة مع المستندات");
	    
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
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "غير مسموح الوصول إلى المستند");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "رفض قفل المستند");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "خطأ داخلى فى المخزن");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "مسار المستند غير موجود");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "خطأ فى الاصدار");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "غير مسموح الوصول إلى المستند");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "المستند غير موجود");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "المستند فعلا موجود من قبل");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "رفض قفل المستند");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "رفض فتح قفل المستند");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "خطأ داخلى فى المخزن");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "خطأ فى التطبيق الداخلى");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "مسار المستند غر موجود");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "غير مسموح الوصول إلى المجلد");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "المجلد غير موجود");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "المجلد موجود مسبقا");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "خطأ داخلى فى المخزن");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "خطأ داخلى فى المخزن");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "مساء المجلد غير موجود");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "غير مسموح الوصول للعنصر");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "العنصر غير موجود");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "خطأ داخلى فى المخزن");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "خطأ داخلى فى المخزن");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "المستند غير موجود");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "خطأ داخلى فى المخزن");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "نوع الملف غير مدعوم");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "المستند فعلا موجود من قبل");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "حجم المستند تجاوز");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "اغلقت الجسلة");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "خطأ عام فى تنفيذ الاستعلام");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "اسم البحث المخزن يجب ان يكون فريد من نوعه");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "اسم المفضلة يجب ان يكون فريد من نوعه");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "فقدت الجلسة");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "خطأ فى المستودع الداخلى");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "خطأ فى المستودع الداخلى");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "خطأ على الادخال والاخراج");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "خطأ فى المستودع الداخلى");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "خطأ فى اعدادات المستعرض");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_QuotaExceed, "Error user quota exceed, contact with adminitrator");
	  }
}
