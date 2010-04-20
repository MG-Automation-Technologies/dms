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
 * Turkish translations
 * 
 * @author jllort
 *
 */
public class Lang_tr_TR {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd/MM/yyyy HH:mm:ss");
	    lang.put("general.day.pattern", "dd/MM/yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "OpenKM Yükleniyor..");
	    lang.put("startup.starting.loading", "OpenKM Yüklemesi Baþlýyor");
	    lang.put("startup.taxonomy", "Sýnýflandýrma Merkezi Oluþturuluyor ");
	    lang.put("startup.template", "Þablon Merkezi Oluþturuluyor");
	    lang.put("startup.personal", "Kiþiler Merkezi Oluþturuluyor");
	    lang.put("startup.mail", "Getting e-mail root node");
	    lang.put("startup.trash", "Geri Dönüþüm Kutusu Merkezi Oluþturuluyor");
	    lang.put("startup.user.home", "Kullanýcý Merkezi Oluþturuluyor");
	    lang.put("startup.bookmarks", "Favorilerim Merkezi Oluþturuluyor");
	    lang.put("startup.loading.taxonomy", "Sýnýflandýrma Yükleniyor");
	    lang.put("startup.loading.taxonomy.getting.folders", "Sýnýflandýrma Yapýlýyor - Klasörler alýnýyor");
	    lang.put("startup.loading.taxonomy.eval.params", "Sýnýflandýrma yükleniyor - Tarayýcý Parametreleri Düzenleniyor");
	    lang.put("startup.loading.taxonomy.open.path", "Sýnýflandýrma Yapýlýyor - Yol Açýk");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Sýnýflandýrma Yapýlýyor - Klasörler Alýnýyor");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Sýnýflandýrma Yapýlýyor - Dokümanlar Alýnýyor");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
	    lang.put("startup.loading.personal", "Kiþiler Yükleniyor");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.templates", "Þablonlar Yükleniyor");
	    lang.put("startup.loading.trash", "Geri Dönüþüm Kutusu Yükleniyor..");
	    lang.put("startup.loading.history.search", "Arama Geçmiþi yükleniyor");
	    lang.put("startup.loading.user.values", "Kullanýcý Deðerleri Yükleniyor..");
	    lang.put("startup.loading.property.group.translations", "Özel Gruplar Yükleniyor");
	    lang.put("startup.keep.alive", "Yükleme Sürüyor..");
	    
	    // Update notification
	    lang.put("openkm.update.available", "OpenKM Güncellemesi Uygun..");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Sýnýflandýrma");
	    lang.put("leftpanel.label.trash", "Geri Dönüþüm Kutusu");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Araþtýrma Dosyasý");
	    lang.put("leftpanel.label.templates", "Þablonlar");
	    lang.put("leftpanel.label.my.documents", "Dokümanlarým");
	    lang.put("leftpanel.label.user.search", "User news");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Klasör Oluþtur");
	    lang.put("tree.menu.directory.remove", "Sil");
	    lang.put("tree.menu.directory.rename", "Yeniden Adlandýr");
	    lang.put("tree.menu.directory.refresh", "Yenile");
	    lang.put("tree.menu.directory.move", "Taþý");
	    lang.put("tree.menu.directory.copy", "Kopyala");
	    lang.put("tree.menu.directory.add.document", "Doküman Ekle");
	    lang.put("tree.menu.add.bookmark", "Favorilere Ekle");
	    lang.put("tree.menu.set.home", "Varolana Ayarla");
	    lang.put("tree.menu.export", "Dosyaya Aktar");
	    lang.put("tree.status.refresh.folder", "Klasör Aðacý Güncelleniyor");
	    lang.put("tree.status.refresh.create", "Klasör Olþturuluyor");
	    lang.put("tree.status.refresh.delete", "Klasör Siliniyor");
	    lang.put("tree.status.refresh.rename", "Klasör Yeniden Adladýrýlýyor");
	    lang.put("tree.status.refresh.restore", "Klasör Dolduruluyor");
	    lang.put("tree.status.refresh.purge", "Klasör Temizleniyor");
	    lang.put("tree.status.refresh.get", "Klasör Güncelleniyor");
	    lang.put("tree.folder.new", "Yeni Klasör");
	    lang.put("tree.status.refresh.add.subscription", "Adding subscription");
	    lang.put("tree.status.refresh.remove.subscription", "Üye Siliniyor");
	    lang.put("tree.status.refresh.get.root", "Kaynak Klasör Yenileniyor");
	    lang.put("tree.status.refresh.get.user.home", "Kullanýcý Açýlýyor");
	    lang.put("tree.status.refresh.purge.trash", "Geri Dönüþüm Kutusu Temizleniyor");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Yükle");
	    lang.put("trash.menu.directory.purge", "Temizle");
	    lang.put("trash.menu.directory.purge.trash", "Geri Dönüþüm Kutusunu Temizle");
	    lang.put("trash.directory.select.label", "Hedef Klasör Seç");
	    
	    // General menu
	    lang.put("general.menu.file", "Dosya");
	    	lang.put("general.menu.file.exit", "Çýkýþ");
	    	lang.put("general.menu.file.purge.trash", "Geri Dönüþüm Kutusunu Temizle");
	    lang.put("general.menu.edit", "Düzenle");
			lang.put("general.menu.file.create.directory", "Klasör Oluþtur");
			lang.put("general.menu.file.download.document", "Doküman Yükle");
			lang.put("general.menu.file.send.link", "Doküman Linki Gönder");
			lang.put("general.menu.file.lock", "Kilitle");
			lang.put("general.menu.file.unlock", "Kilidi Aç");
			lang.put("general.menu.file.add.document", "Doküman Ekle");
			lang.put("general.menu.file.checkout", "Çýkýþta Denetle");
			lang.put("general.menu.file.checkin", "Giriþte Denetle");
			lang.put("general.menu.file.cancel.checkout", "Denetlemeden Vazgeç");
			lang.put("general.menu.file.delete", "Sil");
			lang.put("general.menu.file.refresh", "Yenile");
			lang.put("general.menu.file.scanner", "Scanner");
	    lang.put("general.menu.tools", "Araçlar");
	    	lang.put("general.menu.tools.languages", "Diller");
	    	lang.put("general.menu.tools.skin", "Arayüz");
    			lang.put("general.menu.tools.skin.default", "Varsayýlan");
    			lang.put("general.menu.tools.skin.default2", "Varsayýlan 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Orta");
    			lang.put("general.menu.tools.skin.bigfont", "Büyük");
    		lang.put("general.menu.debug.console", "Hata Ayýklama Konsolu");
    		lang.put("general.menu.administration", "Yönetimi Göster");
    		lang.put("general.menu.tools.preferences", "Prefererences");
    			lang.put("general.menu.tools.user.preferences", "User configuration");
    	lang.put("general.menu.bookmark", "Favoriler");
	    	lang.put("general.menu.bookmark.home", "Anasayfa");
	    	lang.put("general.menu.bookmark.default.home", "Varsayýlaný Ayarla");
	    	lang.put("general.menu.bookmark.edit", "Favorileri Düzenle");
	    lang.put("general.menu.help", "Yardým");
		    lang.put("general.menu.bug.report", "Hata Raporu");
		    lang.put("general.menu.support.request", "Destek Talebi");
		    lang.put("general.menu.public.forum", "Genel Oturum");
		    lang.put("general.menu.project.web", "Webi Tasarla");
		    lang.put("general.menu.version.changes", "Uyarlama Notlarý");
		    lang.put("general.menu.documentation", "Dokümantasyon");
		    lang.put("general.menu.about", "OpenKM Hakkýnda");
	    lang.put("general.connected", "Baðlanýldý");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Ýsim");
	    lang.put("filebrowser.date.update", "Güncelleme Tarihi");
	    lang.put("filebrowser.size", "Boyut");
	    lang.put("filebrowser.path", "Yol");
	    lang.put("filebrowser.author", "Yazar");
	    lang.put("filebrowser.version", "Sürüm");
	    lang.put("filebrowser.menu.checkout", "Çýkýþta Denetle");
	    lang.put("filebrowser.menu.checkin", "Giriþte Denetle");
	    lang.put("filebrowser.menu.delete", "Sil");
	    lang.put("filebrowser.menu.rename", "Yeniden Adlandýr");
	    lang.put("filebrowser.menu.checkout.cancel", "Denetlemeden Vazgeç");
	    lang.put("filebrowser.menu.lock", "Kilitle");
	    lang.put("filebrowser.menu.unlock", "Kilidi Aç");
	    lang.put("filebrowser.menu.download", "Yükle");
	    lang.put("filebrowser.menu.move", "Taþý");
	    lang.put("filebrowser.menu.copy", "Kopyala");
	    lang.put("filebrowser.menu.create.from.template", "Görselden Oluþtur");
	    lang.put("filebrowser.menu.add.property.group", "Özel Grup Ekle");
	    lang.put("filebrowser.menu.remove.property.group", "Özel Gruptan Çýk");
	    lang.put("filebrowser.menu.start.workflow", "Start workflow");
	    lang.put("filebrowser.menu.add.subscription", "Üye Ekle");
	    lang.put("filebrowser.menu.remove.subscription", "Üye Sil");
	    lang.put("filebrowser.menu.add.bookmark", "Favorilere Ekle");
	    lang.put("filebrowser.menu.set.home", "Varsayýlaný Ayarla");
	    lang.put("filebrowser.menu.refresh", "Yenile");
	    lang.put("filebrowser.menu.export", "Dosyaya Aktar");
	    lang.put("filebrowser.menu.play", "Oynat");
	    lang.put("filebrowser.menu.image.viewer", "Resim Gösterici");
	    lang.put("filebrowser.status.refresh.folder", "Klasör Listesini Güncelle");
	    lang.put("filebrowser.status.refresh.document", "Doküman Listesini Güncelle");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Klasör Siliniyor");
	    lang.put("filebrowser.status.refresh.delete.document", "Doküman Siliniyor");
	    lang.put("filebrowser.status.refresh.checkout", "Doküman Kontrolünü Bitir");
	    lang.put("filebrowser.status.refresh.lock", "Kilitli Doküman");
	    lang.put("filebrowser.status.refresh.unlock", "Kilitsiz Doküman");
	    lang.put("filebrowser.status.refresh.document.rename", "Dokümaný Yeniden Adlandýr");
	    lang.put("filebrowser.status.refresh.folder.rename", "Klasörü Yenden Adlandýr");
	    lang.put("filebrowser.status.refresh.document.purge", "Doküman Siliniyor");
	    lang.put("filebrowser.status.refresh.folder.purge", "Klasör Siliniyor");
	    lang.put("filebrowser.status.refresh.folder.get", "Klasör Güncelleniyor");
	    lang.put("filebrowser.status.refresh.document.get", "Doküman Güncelleniyor");
	    lang.put("filebrowser.status.refresh.add.subscription", "Üye Ekleniyor");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Üye Siliniyor");
	    lang.put("filebrowser.status.refresh.get.user.home", "Kullanýcý Hesabý Açýlýyor");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Gönder");
	    lang.put("fileupload.status.sending", "Dosya Yükleniyor...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "Doðru Olarak Yüklenen Dosyalar");
	    lang.put("fileupload.upload.status", "Yükleme Durumu");
	    lang.put("fileupload.upload.uploaded", "Yüklenen");
	    lang.put("fileupload.upload.completed", "Yükleme Tamamlandý");
	    lang.put("fileupload.upload.runtime", "Ýþlem Sayýsý");
	    lang.put("fileupload.upload.remaining", "Kalan");
	    lang.put("fileupload.button.close", "Kapat");
	    lang.put("fileupload.button.add.other.file", "Baþka Dosya Ekle");
	    lang.put("fileupload.status.move.file", "Taþýnan Dosya...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Kopyalanan Dosya...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "Yenilenen Dosya...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Taþýnan Klasör...");
	    lang.put("fileupload.status.copy.folder", "Kopyalanan Klasör...");
	    lang.put("fileupload.status.restore.folder", "Yenilenen Klasör...");
	    lang.put("fileupload.status.create.from.template", "Þablon Dosyasý Oluþturma...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Yeni Doküman Ekle");
	    lang.put("fileupload.label.update", "Dokümanlarý Güncelle");
	    lang.put("fileupload.label.users.notify", "Kullanýcýlara Haber Ver");
	    lang.put("fileupload.label.comment", "Yorum");
	    lang.put("fileupload.label.users.to.notify",  "Haber Verilen Kullanýcýlar");
	    lang.put("fileupload.label.users",  "Kullanýcýlar");
	    lang.put("fileupload.label.must.select.users",  "Haber Vermek Ýçin Kullanýcý Seçiniz");
	    lang.put("fileupload.label.importZip", "Import Documents from ZIP");
	    lang.put("fileupload.label.notify.comment", "Uyarý Mesajý");
	    lang.put("fileupload.label.error.importing.zip", "Dosya Ýhraç Hatasý");
	    lang.put("fileupload.label.error.move.file", "Dosya Taþýma Hatasý");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Dosya Kopyalama Hatasý");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "Dosya Yenileme Hatasý");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Klasör Taþýma Hatasý");
	    lang.put("fileupload.label.error.copy.folder", "Klasör Kopyalama Hatasý");
	    lang.put("fileupload.label.error.restore.folder", "Klasör Yenileme Hatasý");
	    lang.put("fileupload.label.error.create.from.template", "Þablon Dosyasý Oluþturma Hatasý");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Klasör Taþýma Ýzni Yok ");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Ana Klasörü Kopyalama Ýzni Yok");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Ana Klasör Oluþturma Ýzni Yok");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Özellikler");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "Geçmiþ");
	    lang.put("tab.document.status.history", "Güncelleme Geçmiþi");
	    lang.put("tab.status.security.role", "Güvenlik Rollerinin Güncellenmesi");
	    lang.put("tab.status.security.user", "Güvenlik Kullanýcýlarýnýn Güncellenmesi");
	    lang.put("tab.document.status.group.properties", "Grup Özelliklerinin Güncellenmesi");
	    lang.put("tab.document.status.set.keywords", "Anahtar Kelimelerin Düzenlenmesi");
	    lang.put("tab.document.status.get.version.history.size", "Doküman Boyutunun Güncellenmesi");
	    lang.put("tab.document.status.purge.version.history", "Doküman Geçmiþi Düzenleniyor");
	    lang.put("tab.document.status.restore.version", "Doküman Sürümü Yenileniyor");
	    lang.put("tab.document.security", "Güvenlik");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Özellikler");
	    lang.put("tab.folder.security", "Güvenlik");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Masaüstü");
	    lang.put("tab.workspace.search", "Arama");
	    lang.put("tab.workspace.dashboard", "DashBoard");
	    lang.put("tab.workspace.administration", "Administration");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Ýsim");
	    lang.put("document.folder", "Klasör");
	    lang.put("document.size", "Boyut");
	    lang.put("document.created", "Oluþturuldu");
	    lang.put("document.lastmodified", "Düzenlendi");
	    lang.put("document.mimetype", "Taklit Tipi");
	    lang.put("document.keywords", "Anahtar Kelimeler");
	    lang.put("document.by", "Tarafýndan");
	    lang.put("document.status", "Durum");
	    lang.put("document.status.checkout", "Tarafýndan Oluþturulmuþtur");
	    lang.put("document.status.locked", "Tarafýndan Kilitlenmiþtir");
	    lang.put("document.status.normal", "Uygun Durumda");
	    lang.put("document.subscribed", "Onaylanmýþ");
	    lang.put("document.subscribed.yes", "Evet");
	    lang.put("document.subscribed.no", "Hayýr");
	    lang.put("document.history.size", "Geçmiþ Boyut");
	    lang.put("document.subscribed.users", "Onaylanmýþ Kullanýcýlar");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Add note");
	    lang.put("document.keywords.cloud", "Keywords cloud");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Ýsim");
	    lang.put("folder.parent", "Çalýþma Grubu");
	    lang.put("folder.created", "Oluþturuldu");
	    lang.put("folder.by", "Tarafýndan");
	    lang.put("folder.subscribed", "Onaylandý");
	    lang.put("folder.subscribed.yes", "Evet");
	    lang.put("folder.subscribed.no", "Hayýr");
	    lang.put("folder.subscribed.users", "Onaylanmýþ Kullanýcýlar");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "Sürüm");
	    lang.put("version.created", "Tarih");
	    lang.put("version.author", "Yazar");
	    lang.put("version.size", "Boyut");
	    lang.put("version.purge.document", "Yakýn Geçmiþ");
	    lang.put("version.comment", "Yorum");
	    
	    // Security
	    lang.put("security.label", "Güvenlik");
	    lang.put("security.group.name", "Grup");
	    lang.put("security.group.permission.read", "Oku");
	    lang.put("security.group.permission.write", "Yaz");
	    lang.put("security.user.name", "Kullanýcý");
	    lang.put("security.user.permission.read", "Oku");
	    lang.put("security.user.permission.write", "Yaz");
	    lang.put("security.users", "Kullanýcýlar");
	    lang.put("security.groups", "Gruplar");
	    lang.put("security.recursive", "Deðiþimlere Ýzin Ver");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "Sistem Hatasý");
	    lang.put("error.invocation", "Sunucu Baðlantý Hatasý");
	    
	    // About
	    lang.put("about.label", "OpenKM Hakkýnda");
	    lang.put("about.loading", "Yükleniyor ...");
	    
	    // Logout
	    lang.put("logout.label", "Çýkýþ");
	    lang.put("logout.closed", "OpenKM Düzgün Olarak Kapanmýþtýr.");
	    lang.put("logout.logout", "OpenKM Oturumu Kapatýlýyor, Lütfen Bekleyiniz!");
	    
	    // Confirm
	    lang.put("confirm.label", "Onaylama");
	    lang.put("confirm.delete.folder", "Klasörü Silmek Ýstediðinizden Eminmisiniz ?");
	    lang.put("confirm.delete.document", "Dokümaný Silmek istediðinizden Eminmisiniz ?");
	    lang.put("confirm.delete.trash", "Geri Dönüþüm Kutusunu Boþaltmak istediðinizden Eminmisiniz ?");
	    lang.put("confirm.purge.folder", "Klasörü Temizlemek Ýstediðinizden Eminmisiniz ?");
	    lang.put("confirm.purge.document", "Dokümaný Temizlemek Ýstediðinizden Eminmisiniz ?");
	    lang.put("confirm.delete.propety.group", "Özel Grubu Silmek istediðinizden Eminmisiniz ?");
	    lang.put("confirm.purge.version.history.document", "Doküman Geçmiþini Silmek Ýstediðinizden Eminmisiniz ?");
	    lang.put("confirm.purge.restore.document", "Doküman Sürümünü Yenilemek Ýstediðinizden Eminmisiniz ?");
	    lang.put("confirm.set.default.home", "Varsayýlana Ayarlamak Ýstediðinizden Eminmisiniz ?");
	    lang.put("confirm.delete.saved.search", "¿ Do you really want to delete saved search ?");
	    lang.put("confirm.delete.user.news", "¿ Do you really want to delete user news ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    lang.put("confirm.get.pooled.workflow.task","¿ Do you want to assign this task to you ?");
	    
	    // Search inputs
	    lang.put("search.context", "Kapsam");
	    lang.put("search.content", "Ýçerik");
	    lang.put("search.name", "Ýsim");
	    lang.put("search.keywords", "Anahtar Kelimeler");
	    lang.put("search.folder", "Folder");
	    lang.put("search.results", "Sonuçlar");
	    lang.put("search.to", "-e/-a");
	    lang.put("search.page.results", "Sayfadaki Arama Sonuçlarý");
	    lang.put("search.add.property.group", "Özel Grup Ekle");
	    lang.put("search.mimetype", "Taklit Tipi");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Geliþmiþ Arama");
	    lang.put("search.user", "Kullanýcý");
	    lang.put("search.date.and", "ve");
	    lang.put("search.date.range", "Arasýndaki Tarih Aralýðý");
	    lang.put("search.save.as.news", "Save as user news");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    
	    // Search results
	    lang.put("search.result.name", "Ýsim");
	    lang.put("search.result.score", "Ýlgili");
	    lang.put("search.result.size", "Boyut");
	    lang.put("search.result.date.update", "Güncelleme Tarihi");
	    lang.put("search.result.author", "Yazar");
	    lang.put("search.result.version", "Sürüm");
	    lang.put("search.result.path", "Yol");
	    lang.put("search.result.menu.download", "Yükle");
	    lang.put("search.result.menu.go.folder", "Klasöre Git");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Arama Güncelleniyor");
	    lang.put("search.result.status.runsearch", "Kayýtlý Aramalar Güncelleniyor");
	    
	    // Search saved
	    lang.put("search.saved.run", "Çalýþtýr");
	    lang.put("search.saved.delete", "Sil");
	    lang.put("search.saved.status.getsearchs", "Kayýtlý aramalar yenileniyor");
	    lang.put("search.saved.status.savesearch", "Kayýtlý aramalar günceleniyor");
	    lang.put("search.saved.status.deletesearch", "Kayýtlý aramalar Siliniyor");
	    lang.put("search.saved.status.getusernews", "Refreshing user news");
	    
	    // Button
	    lang.put("button.close", "Kapat");
	    lang.put("button.logout", "Oturumdan Çýk");
	    lang.put("button.update", "Güncelle");
	    lang.put("button.cancel", "Ýptal Et");
	    lang.put("button.accept", "Kabul Et");
	    lang.put("button.restore", "Yenile");
	    lang.put("button.move", "Taþý");
	    lang.put("button.change", "Deðiþtir");
	    lang.put("button.search", "Ara");
	    lang.put("button.save.search", "Aramayý Kaydet");
	    lang.put("button.view", "Görüntüle");
	    lang.put("button.clean", "Temizle");
	    lang.put("button.add", "Ekle");
	    lang.put("button.delete", "Sil");
	    lang.put("button.copy", "Kopyala");
	    lang.put("button.create", "Oluþtur");
	    lang.put("button.show", "Göster");
	    lang.put("button.memory", "Güncelle");
	    lang.put("button.copy.clipboard", "Copy to clipboard");	
	    lang.put("button.start", "Start");
	    lang.put("button.select", "Select");
	    
	    // Group
	    lang.put("group.label", "Özel Grup Ekle");
	    lang.put("group.group", "Grup");
	    lang.put("group.property.group", "Özellik");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Favorilere Ekle");
	    lang.put("bookmark.name", "Ýsim");
	    lang.put("bookmark.edit.label", "Favorileri Düzenle");
	    lang.put("bookmark.path", "Yol");
	    lang.put("bookmark.type", "Tip");
	    lang.put("bookmark.type.document", "Doküman");
	    lang.put("bookmark.type.folder", "Klasör");
	    
	    // Notify
	    lang.put("notify.label", "Dokuman Baðlantýsý Yollanýyor");
	    
	    // Status
	    lang.put("status.document.copied", "Ýþaretli Dokümaný Kopyala ");
	    lang.put("status.document.cut", "Ýþaretli Dokümaný Kes ");
	    lang.put("status.folder.copied", "Ýþaretli Klasörü Kopyala");
	    lang.put("status.folder.cut", "Ýþaretli Klasörü Kes");
	    lang.put("status.keep.alive.error", "Sunucu Baðlantý Kesintisi (Lütfen Bekleyin !)");
	    lang.put("status.debug.enabled", "Hata Belirlendi");
	    lang.put("status.debug.disabled", "Hata Belirlenemedi");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Pazar");
	    lang.put("calendar.day.monday", "Pazartesi");
	    lang.put("calendar.day.tuesday", "Salý");
	    lang.put("calendar.day.wednesday", "Çarþamba");
	    lang.put("calendar.day.thursday", "Perþembe");
	    lang.put("calendar.day.friday", "Cuma");
	    lang.put("calendar.day.saturday", "Cumartesi");
	    lang.put("calendar.month.january", "Ocak");
	    lang.put("calendar.month.february", "Þubat");
	    lang.put("calendar.month.march", "Mart");
	    lang.put("calendar.month.april", "Nisan");
	    lang.put("calendar.month.may", "Mayýs");
	    lang.put("calendar.month.june", "Haziran");
	    lang.put("calendar.month.july", "Temmuz");
	    lang.put("calendar.month.august", "Agustos");
	    lang.put("calendar.month.september", "Eylül");
	    lang.put("calendar.month.october", "Ekim");
	    lang.put("calendar.month.november", "Kasým");
	    lang.put("calendar.month.december", "Aralýk");
	    
	    // Media player
	    lang.put("media.player.label", "Medya Oynatýcý");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Resim Gösterici");
	    lang.put("image.viewer.zoom.in", "Küçült");
	    lang.put("image.viewer.zoom.out", "Büyült");
	    
	    // Debug console
	    lang.put("debug.console.label", "Hata Konsolu");
	    lang.put("debug.enable.disable", "Hata Ekraný Ýçin CTRL+Z ye Basýnýz ");

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
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Dokümana Ýþlemi engellendi");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Doküman bulunamadý");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Doküman Zaten Var");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Döküman Kilitleme Engeli");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Doküman Kilidi Açma Talebi");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Kaynak Baðlantý Hatasý");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Uygulama Hatasý");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Döküman Bulunamadý");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Klasöre Giriþ Engellendi");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Klasör Bulunamadý");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Klasör Zaten Var");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Kaynak Baðlantý Hatasý");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Kaynak Baðlantý Hatasý");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Klasör Bulunamadý");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Dokümana Giriþ Engellendi");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Aranan Öge Bulunamadý");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Kaynak Baðlantý hatasý");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Kaynak Baðlantý hatasý");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Döküman bulunamadý");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Kaynak Baðlantý hatasý");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Desteklenmeyen DOsya Türü");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Doküman Zaten Var ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Döküman Boyutu Aþýldý ");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Oturum Kapatýldý");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Hata Sorgulamasý Yapýlýyor");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Her Sorguda Tek Kelime Kullanýnýz!");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Favori Ýsimleri Tekil Olmalý");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Oturum Kapandý");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Kaynak Baðlantý Hatasý");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Kaynak Baðlantý Hatasý");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Kaynak Baðlantý Hatasý");
	  }
}