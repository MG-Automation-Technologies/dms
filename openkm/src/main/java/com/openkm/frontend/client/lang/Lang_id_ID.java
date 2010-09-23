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
 * Indonesian 
 * 
 * @author si_sol
 */
public class Lang_id_ID {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd-MM-yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd-MM-yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Sedang me-load OpenKM");
	    lang.put("startup.starting.loading", "Mulai me-load OpenKM");
	    lang.put("startup.taxonomy", "Sedang ambil taksonomi node dasar");
	    lang.put("startup.categories", "Sedang ambil kategori node dasar");
	    lang.put("startup.thesaurus", "Sedang ambil node dasar thesaurus");
	    lang.put("startup.template", "Sedang ambil node dasar template");
	    lang.put("startup.personal", "Sedang ambil node dasar personal");
	    lang.put("startup.mail", "Sedang ambil node dasar e-mail");
	    lang.put("startup.trash", "Sedang ambil node dasar trash");
	    lang.put("startup.user.home", "Sedang ambil node home pengguna");
	    lang.put("startup.bookmarks", "Sedang ambil bookmark");
	    lang.put("startup.loading.taxonomy", "Sedang me-load taksonomi");
	    lang.put("startup.loading.taxonomy.getting.folders", "Sedang me-load taksonomi - ambil folder");
	    lang.put("startup.loading.taxonomy.eval.params", "Sedang me-load taksonomi - mengevaluasi parameter utk browser");
	    lang.put("startup.loading.taxonomy.open.path", "Sedang me-load taksnomi - membuka path");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Sedang me-load taksonomi - ambil folder");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Sedang me-load taksonomi - ambil dokumen");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Sedang me-load taksonomi - ambil email");
	    lang.put("startup.loading.personal", "Sedang me-load personal");
	    lang.put("startup.loading.mail", "Sedang me-load email");
	    lang.put("startup.loading.categories", "Sedang me-load kategori");
	    lang.put("startup.loading.thesaurus", "Sedang me-load thesaurus");
	    lang.put("startup.loading.templates", "Sedang me-load template");
	    lang.put("startup.loading.trash", "Sedang me-load trash");
	    lang.put("startup.loading.history.search", "Sedang me-load sejarah pencarian");
	    lang.put("startup.loading.user.values", "Sedang me-load user values");
	    lang.put("startup.keep.alive", "Sedang me-load keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "update OpenKM tersedia");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Taksonomi");
	    lang.put("leftpanel.label.trash", "Trash");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Pencarian tersimpan");
	    lang.put("leftpanel.label.categories", "Kategori");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Template");
	    lang.put("leftpanel.label.my.documents", "Dokument");
	    lang.put("leftpanel.label.user.search", "Berita");
	    lang.put("leftpanel.label.all.repository", "Keseluruhan repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Buat folder");
	    lang.put("tree.menu.directory.remove", "Hapus");
	    lang.put("tree.menu.directory.rename", "Rename");
	    lang.put("tree.menu.directory.refresh", "Refresh");
	    lang.put("tree.menu.directory.move", "Pindahkan");
	    lang.put("tree.menu.directory.copy", "Copy");
	    lang.put("tree.menu.directory.add.document", "Tambah dokumen");
	    lang.put("tree.menu.add.bookmark", "Tambah bookmark");
	    lang.put("tree.menu.set.home", "Men-set default home");
	    lang.put("tree.menu.export", "Ekspor ke file");
	    lang.put("tree.status.refresh.folder", "Mengupdate folder tree");
	    lang.put("tree.status.refresh.create", "Membuat folder");
	    lang.put("tree.status.refresh.delete", "Menghapus folder");
	    lang.put("tree.status.refresh.rename", "Merename folder");
	    lang.put("tree.status.refresh.restore", "Merestore folder");
	    lang.put("tree.status.refresh.purge", "Mempurge folder");
	    lang.put("tree.status.refresh.get", "Mengupdate folder");
	    lang.put("tree.folder.new", "Folder baru");
	    lang.put("tree.status.refresh.add.subscription", "Menambah subskripsi");
	    lang.put("tree.status.refresh.remove.subscription", "Menghapus subskripsi");
	    lang.put("tree.status.refresh.get.root", "Merefresh folder root");
	    lang.put("tree.status.refresh.get.keywords", "Merefresh keyword");
	    lang.put("tree.status.refresh.get.user.home", "Sedang mengambil home pengguna");
	    lang.put("tree.status.refresh.purge.trash", "Membersihkan trash");
	    lang.put("tree.menu.directory.find.folder","Find folder");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Restore");
	    lang.put("trash.menu.directory.purge", "Purge");
	    lang.put("trash.menu.directory.purge.trash", "Purge trash");
	    lang.put("trash.directory.select.label", "Pilih folder tujuan");
	    
	    // General menu
	    lang.put("general.menu.file", "File");
	    	lang.put("general.menu.file.exit", "Keluar");
	    	lang.put("general.menu.file.purge.trash", "Purge trash");
	    lang.put("general.menu.edit", "Edit");
			lang.put("general.menu.file.create.directory", "Buat folder");
			lang.put("general.menu.file.download.document", "Download dokumen");
			lang.put("general.menu.file.download.document.pdf", "Download dokumen sebagai PDF");
			lang.put("general.menu.file.send.link", "Kirim link dokumen");
			lang.put("general.menu.file.send.attachment", "Send document attachment");
			lang.put("general.menu.file.lock", "Kunci");
			lang.put("general.menu.file.unlock", "Buka kunci");
			lang.put("general.menu.file.add.document", "Tambah dokumen");
			lang.put("general.menu.file.checkout", "Check out");
			lang.put("general.menu.file.checkin", "Check in");
			lang.put("general.menu.file.cancel.checkout", "Batalkan check out");
			lang.put("general.menu.file.delete", "Hapus");
			lang.put("general.menu.file.refresh", "Refresh");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "Pengupload file");
	    lang.put("general.menu.tools", "Tools");
	    	lang.put("general.menu.tools.languages", "Bahasa");
	    	lang.put("general.menu.tools.skin", "Skin");
    			lang.put("general.menu.tools.skin.default", "By default");
    			lang.put("general.menu.tools.skin.default2", "By default 2");
    			lang.put("general.menu.tools.skin.mediumfont", "Font sedang");
    			lang.put("general.menu.tools.skin.bigfont", "Font besar");
    		lang.put("general.menu.debug.console", "Terminal Debug");
    		lang.put("general.menu.administration", "Tampilkan administrasi");
    		lang.put("general.menu.tools.preferences", "Prefererensi");
    			lang.put("general.menu.tools.user.preferences", "konfigurasi user");
    	lang.put("general.menu.bookmark", "Bookmark");
	    	lang.put("general.menu.bookmark.home", "Home");
	    	lang.put("general.menu.bookmark.default.home", "Menset default home");
	    	lang.put("general.menu.bookmark.edit", "Edit bookmark");
	    lang.put("general.menu.help", "Petunjuk");
		    lang.put("general.menu.bug.report", "Laporkan bug");
		    lang.put("general.menu.support.request", "Permintaan support");
		    lang.put("general.menu.public.forum", "Forum public");
		    lang.put("general.menu.project.web", "Web Proyek");
		    lang.put("general.menu.version.changes", "Version note");
		    lang.put("general.menu.documentation", "Dokumentasi");
		    lang.put("general.menu.about", "Tentang OpenKM");
	    lang.put("general.connected", "Terhubung sebagai");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Nama");
	    lang.put("filebrowser.date.update", "Tanggal update");
	    lang.put("filebrowser.size", "Ukuran");
	    lang.put("filebrowser.path", "Path");
	    lang.put("filebrowser.author", "Pembuat");
	    lang.put("filebrowser.version", "Versi");
	    lang.put("filebrowser.menu.checkout", "Check out");
	    lang.put("filebrowser.menu.checkin", "Check in");
	    lang.put("filebrowser.menu.delete", "Hapus");
	    lang.put("filebrowser.menu.rename", "Rename");
	    lang.put("filebrowser.menu.checkout.cancel", "Batalkan Check out");
	    lang.put("filebrowser.menu.lock", "Kunci");
	    lang.put("filebrowser.menu.unlock", "Buka kunci");
	    lang.put("filebrowser.menu.download", "Download");
	    lang.put("filebrowser.menu.move", "Pindahkan");
	    lang.put("filebrowser.menu.copy", "Copy");
	    lang.put("filebrowser.menu.create.from.template", "Buat dari template");
	    lang.put("filebrowser.menu.add.property.group", "Tambah grup properti");
	    lang.put("filebrowser.menu.remove.property.group", "Hapus grup properti");
	    lang.put("filebrowser.menu.start.workflow", "Menstart workflow");
	    lang.put("filebrowser.menu.add.subscription", "Tambah subskripsi");
	    lang.put("filebrowser.menu.remove.subscription", "Hapus subskripsi");
	    lang.put("filebrowser.menu.add.bookmark", "Tambah bookmark");
	    lang.put("filebrowser.menu.set.home", "Menset default home");
	    lang.put("filebrowser.menu.refresh", "Refresh");
	    lang.put("filebrowser.menu.export", "Ekspor ke ZIP");
	    lang.put("filebrowser.menu.play", "Play");
	    lang.put("filebrowser.menu.image.viewer", "Viewer gambar");
	    lang.put("filebrowser.status.refresh.folder", "Mengupdate daftar folder");
	    lang.put("filebrowser.status.refresh.document", "Mengupdate daftar dokumen");
	    lang.put("filebrowser.status.refresh.mail", "Mengupdate daftar mail");
	    lang.put("filebrowser.status.refresh.delete.folder", "Menghapus folder");
	    lang.put("filebrowser.status.refresh.delete.document", "Menghapus dokumen");
	    lang.put("filebrowser.status.refresh.checkout", "Men-check out dokumen");
	    lang.put("filebrowser.status.refresh.lock", "Mengunci dokumen");
	    lang.put("filebrowser.status.refresh.unlock", "Membuka kunci dokumen");
	    lang.put("filebrowser.status.refresh.document.rename", "Merename dokumen");
	    lang.put("filebrowser.status.refresh.folder.rename", "Merename folder");
	    lang.put("filebrowser.status.refresh.document.purge", "Menghapus dokumen");
	    lang.put("filebrowser.status.refresh.folder.purge", "Menghapus folder");
	    lang.put("filebrowser.status.refresh.folder.get", "Mengupdate folder");
	    lang.put("filebrowser.status.refresh.document.get", "Mengupdate dokumen");
	    lang.put("filebrowser.status.refresh.add.subscription", "Menambah subskripsi");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Menghapus subskripsi");
	    lang.put("filebrowser.status.refresh.get.user.home", "Sedang ambil home user");
	    lang.put("filebrowser.status.refresh.delete.mail", "Menghapus mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Menghapus mail (permanen)");
	    
	    // File Upload
	    lang.put("fileupload.send", "Kirim");
	    lang.put("fileupload.status.sending", "Mengupload file...");
	    lang.put("fileupload.status.indexing", "Mengindeks file...");
	    lang.put("fileupload.status.ok", "File sukses ter-upload");
	    lang.put("fileupload.upload.status", "Status upload");
	    lang.put("fileupload.upload.uploaded", "Ter-upload");
	    lang.put("fileupload.upload.completed", "Upload selesai");
	    lang.put("fileupload.upload.runtime", "Runtime");
	    lang.put("fileupload.upload.remaining", "Tersisa");
	    lang.put("fileupload.button.close", "Menutup");
	    lang.put("fileupload.button.add.other.file", "Tambah file lain");
	    lang.put("fileupload.status.move.file", "Memindahkan file...");
	    lang.put("fileupload.status.move.mail", "Memindahkan mail...");
	    lang.put("fileupload.status.copy.file", "Mengcopy file...");
	    lang.put("fileupload.status.copy.mail", "Mengcopy mail...");
	    lang.put("fileupload.status.restore.file", "Merestore file...");
	    lang.put("fileupload.status.restore.mail", "Merestore mail...");
	    lang.put("fileupload.status.move.folder", "Memindahkan folder...");
	    lang.put("fileupload.status.copy.folder", "Mengcopy folder...");
	    lang.put("fileupload.status.restore.folder", "Merestore folder...");
	    lang.put("fileupload.status.create.from.template", "Membuat file dari template...");
	    lang.put("fileupload.status.of", "dari");
	    lang.put("fileupload.label.insert", "Tambah dokumen baru");
	    lang.put("fileupload.label.update", "Update dokumen");
	    lang.put("fileupload.label.users.notify", "Notifikasi ke user");
	    lang.put("fileupload.label.comment", "Komentar");
	    lang.put("fileupload.label.users.to.notify",  "User sudah dinotifikasi");
	    lang.put("fileupload.label.users",  "Pengguna");
	    lang.put("fileupload.label.groups.to.notify","Groups to notify");
	    lang.put("fileupload.label.groups","Groups");
	    lang.put("fileupload.label.must.select.users",  "Pilih user yang akan dinotifikasi");
	    lang.put("fileupload.label.importZip", "Impor Dokumen dari ZIP");
	    lang.put("fileupload.label.notify.comment", "Berita Notifikasi");
	    lang.put("fileupload.label.error.importing.zip", "Gagal mengimpor file");
	    lang.put("fileupload.label.error.move.file", "Gagal memindah file");
	    lang.put("fileupload.label.error.move.mail", "Gagal memindah mail");
	    lang.put("fileupload.label.error.copy.file", "Gagal mengcopy file");
	    lang.put("fileupload.label.error.copy.mail", "Gagal mengcopy mail");
	    lang.put("fileupload.label.error.restore.file", "Gagal merestore file");
	    lang.put("fileupload.label.error.restore.mail", "Gagal merestore mail");
	    lang.put("fileupload.label.error.move.folder", "Gagal memindah folder");
	    lang.put("fileupload.label.error.copy.folder", "Gagal mengcopy folder");
	    lang.put("fileupload.label.error.restore.folder", "Gagal merestore folder");
	    lang.put("fileupload.label.error.create.from.template", "Gagal buat file dari  template");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Tak boleh memindahkan folder asal atau turunannya");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Tak boleh memindahkan folder asal");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Tak boleh membuat file pada folder asal");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Properti");
	    lang.put("tab.document.notes", "Note");
	    lang.put("tab.document.history", "Histori");
	    lang.put("tab.document.status.history", "Megupdate histori");
	    lang.put("tab.status.security.role", "Mengupdate role sekuriti");
	    lang.put("tab.status.security.user", "Mengupdate user sekuriti");
	    lang.put("tab.document.status.group.properties", "Mengupdate properti grup");
	    lang.put("tab.document.status.set.keywords", "Menset keyword");
	    lang.put("tab.document.status.set.categories", "Mengupdate kategori");
	    lang.put("tab.document.status.get.version.history.size", "Merefresh ukuran histori dokumen");
	    lang.put("tab.document.status.purge.version.history", "Mengkompres histori dokumen");
	    lang.put("tab.document.status.restore.version", "Merestore versi dokumen");
	    lang.put("tab.document.security", "Sekuriti");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Properti");
	    lang.put("tab.folder.security", "Sekuriti");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Desktop");
	    lang.put("tab.workspace.search", "Pencarian");
	    lang.put("tab.workspace.dashboard", "DashBoard");
	    lang.put("tab.workspace.administration", "Administrasi");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Nama");
	    lang.put("document.folder", "Folder");
	    lang.put("document.size", "Ukuran");
	    lang.put("document.created", "Dibuat");
	    lang.put("document.lastmodified", "Dimodifikasi");
	    lang.put("document.mimetype", "Tipe MIME");
	    lang.put("document.keywords", "Keyword");
	    lang.put("document.by", "oleh");
	    lang.put("document.status", "Status");
	    lang.put("document.status.checkout", "Diedit oleh");
	    lang.put("document.status.locked", "Dikunci oleh");
	    lang.put("document.status.normal", "Tersedia");
	    lang.put("document.subscribed", "Tersubskripsi");
	    lang.put("document.subscribed.yes", "Ya");
	    lang.put("document.subscribed.no", "Tidak");
	    lang.put("document.history.size", "Ukuran histori");
	    lang.put("document.subscribed.users", "User tersubskripsi");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Tambah note");
	    lang.put("document.keywords.cloud", "Cloud keyword");
	    lang.put("document.categories", "Kategori");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Nama");
	    lang.put("folder.parent", "Induk");
	    lang.put("folder.created", "Dibuat");
	    lang.put("folder.by", "oleh");
	    lang.put("folder.subscribed", "Tersubskipsi");
	    lang.put("folder.subscribed.yes", "Ya");
	    lang.put("folder.subscribed.no", "Tidak");
	    lang.put("folder.subscribed.users", "User tersubskripsi");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Folders");
	    lang.put("folder.number.documents", "Documents");
	    lang.put("folder.number.mails", "Mails");
	    
	    // Version
	    lang.put("version.name", "Versi");
	    lang.put("version.created", "Tanggal");
	    lang.put("version.author", "Pembuat");
	    lang.put("version.size", "Ukuran");
	    lang.put("version.purge.document", "Kompres histori");
	    lang.put("version.comment", "Komentar");
	    
	    // Security
	    lang.put("security.label", "Sekuriti");
	    lang.put("security.group.name", "Grup");
	    lang.put("security.group.permission.read", "Baca");
	    lang.put("security.group.permission.write", "Tulis");
	    lang.put("security.group.permission.delete", "Delete");
	    lang.put("security.group.permission.security", "Security");
	    lang.put("security.user.name", "User");
	    lang.put("security.user.permission.read", "Baca");
	    lang.put("security.user.permission.write", "Tulis");
	    lang.put("security.user.permission.delete", "Delete");
	    lang.put("security.user.permission.security", "Security");
	    lang.put("security.users", "User");
	    lang.put("security.groups", "Grup");
	    lang.put("security.recursive", "Perubahan permisi rekursif");
	    lang.put("secutiry.filter.by.users","Users filter");
	    lang.put("secutiry.filter.by.groups","Groups filter");
	    lang.put("security.status.updating","Updating security");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview tak tersedia");

	    // Mail
	    lang.put("mail.from", "Dari");
	    lang.put("mail.reply", "Reply ke");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subyek");
	    lang.put("mail.attachment", "Attachment");
	    
	    // Error
	    lang.put("error.label", "Sistem menggenerate error");
	    lang.put("error.invocation", "Error saat komunikasi dengan server");
	    
	    // About
	    lang.put("about.label", "Tentang OpenKM");
	    lang.put("about.loading", "Meload ...");
	    
	    // Logout
	    lang.put("logout.label", "Keluar");
	    lang.put("logout.closed", "OpenKM telah ditutup sempurna.");
	    lang.put("logout.logout", "OpenKM sedang log out, tunggu");
	    
	    // Confirm
	    lang.put("confirm.label", "Confirmasi");
	    lang.put("confirm.delete.folder", "¿ Anda yakin ingin hapus folder ?");
	    lang.put("confirm.delete.document", "¿ Anda yakin ingin dokumen ?");
	    lang.put("confirm.delete.trash", "¿ Anda yakin ingin mengosongkan trash ?");
	    lang.put("confirm.purge.folder", "¿ Anda yakin ingin hapus folder ?");
	    lang.put("confirm.purge.document", "¿ Anda yakin ingin hapus dokumen secara permanen ?");
	    lang.put("confirm.delete.propety.group", "¿ Anda yakin ingin hapus grup properti ?");
	    lang.put("confirm.purge.version.history.document", "¿ Anda yakin ingin hapus histori  dokumen ?");
	    lang.put("confirm.purge.restore.document", "¿ Anda yakin ingin merestore ke versi dokumen ini ?");
	    lang.put("confirm.set.default.home", "¿ Anda yakin ingin menset default home ?");
	    lang.put("confirm.delete.saved.search", "¿ Anda yakin ingin hapus simpanan pencarian ?");
	    lang.put("confirm.delete.user.news", "¿ Anda yakin ingin hapus berita untuk user ?");
	    lang.put("confirm.delete.mail", "¿ Anda yakin ingin hapus mail ?");
	    lang.put("confirm.get.pooled.workflow.task","¿ Anda yakin ingin meng-assign tugas ini ke anda sendiri ?");
	    lang.put("confirm.force.unlock","¿ Are you sure you want to force canceling locked document");
	    lang.put("confirm.force.cancel.checkout","¿ Are you sure you want to force cancelling chekcout document ?");
	    
	    // Search inputs
	    lang.put("search.context", "Konteks");
	    lang.put("search.content", "Isi");
	    lang.put("search.name", "Nama");
	    lang.put("search.keywords", "Keyword");
	    lang.put("search.folder", "Folder");
	    lang.put("search.category", "Kategori");
	    lang.put("search.results", "Hasil");
	    lang.put("search.to", "ke");
	    lang.put("search.page.results", "Halaman hasil");
	    lang.put("search.add.property.group", "Tambah grup properti");
	    lang.put("search.mimetype", "Tipe Mime");
	    lang.put("search.type", "Tipe");
	    lang.put("search.type.document", "Dokumen");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Pencarian lanjut");
	    lang.put("search.user", "Pengguna");
	    lang.put("search.date.and", "dan");
	    lang.put("search.date.range", "Rentang tanggal antara");
	    lang.put("search.save.as.news", "Simpan sebagai berita user");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter berdasar folder");
	    lang.put("search.category.filter", "Filter berdasar kategori");
	    
	    // Search results
	    lang.put("search.result.name", "Nama");
	    lang.put("search.result.score", "Relevansi");
	    lang.put("search.result.size", "Ukuran");
	    lang.put("search.result.date.update", "Tanggal update");
	    lang.put("search.result.author", "Pembuat");
	    lang.put("search.result.version", "Versi");
	    lang.put("search.result.path", "Path");
	    lang.put("search.result.menu.download", "Download");
	    lang.put("search.result.menu.go.folder", "Ke folder");
	    lang.put("search.result.menu.go.document", "Ke dokumen");
	    lang.put("search.result.status.findPaginated", "Mengupdate pencarian");
	    lang.put("search.result.status.runsearch", "Mengupdate simpanan pencarian");
	    
	    // Search saved
	    lang.put("search.saved.run", "Me-run");
	    lang.put("search.saved.delete", "Hapus");
	    lang.put("search.saved.status.getsearchs", "Merefresh simpanan pencarian");
	    lang.put("search.saved.status.savesearch", "Mengupdate simpanan pencarian");
	    lang.put("search.saved.status.deletesearch", "Menghapus simpanan pencarian");
	    lang.put("search.saved.status.getusernews", "Merefresh user news");
	    
	    // Button
	    lang.put("button.close", "Menutup");
	    lang.put("button.logout", "Logout");
	    lang.put("button.update", "Mengupdate");
	    lang.put("button.cancel", "Batal");
	    lang.put("button.accept", "Aksep");
	    lang.put("button.restore", "Merestore");
	    lang.put("button.move", "Memindah");
	    lang.put("button.change", "Ubah");
	    lang.put("button.search", "Cari");
	    lang.put("button.save.search", "Simpan pencarian");
	    lang.put("button.view", "View");
	    lang.put("button.clean", "Klining");
	    lang.put("button.add", "Tambah");
	    lang.put("button.delete", "Hapus");
	    lang.put("button.copy", "Copy");
	    lang.put("button.create", "Buat");
	    lang.put("button.show", "Tampilkan");
	    lang.put("button.memory", "Update");
	    lang.put("button.copy.clipboard", "Copy ke clipboard");	
	    lang.put("button.start", "Menstart");
	    lang.put("button.select", "Pilih");
	    lang.put("button.test", "Test");
	    lang.put("button.next", "Next");
	    
	    // Group
	    lang.put("group.label", "Tambah grup properti");
	    lang.put("group.group", "Grup");
	    lang.put("group.property.group", "Properti");lang.put("general.menu.file.scanner", "Scanner");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Tambah bookmark");
	    lang.put("bookmark.name", "Nama");
	    lang.put("bookmark.edit.label", "Edit bookmark");
	    lang.put("bookmark.path", "Path");
	    lang.put("bookmark.type", "Tipe");
	    lang.put("bookmark.type.document", "Dokumen");
	    lang.put("bookmark.type.folder", "Folder");
	    
	    // Notify
	    lang.put("notify.label", "Kirim link dokumen");
	    lang.put("notify.label.attachment", "Send document attachment");
	    
	    // Status
	    lang.put("status.document.copied", "Dokumen dimark untuk di-copy");
	    lang.put("status.document.cut", "Dokumen dimark untuk di-cut");
	    lang.put("status.folder.copied", "Folder dimark untuk di-copy");
	    lang.put("status.folder.cut", "Folder dimark untuk di-cut");
	    lang.put("status.keep.alive.error", "Kegagalan koneksi ke server terdeteksi (keep alive)");
	    lang.put("status.debug.enabled", "Debug di-enable");
	    lang.put("status.debug.disabled", "Debug di-disable");
	    lang.put("status.network.error.detected", "Terdeteksi error di network");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Ahad");
	    lang.put("calendar.day.monday", "Senin");
	    lang.put("calendar.day.tuesday", "Selasa");
	    lang.put("calendar.day.wednesday", "Rabu");
	    lang.put("calendar.day.thursday", "Kamis");
	    lang.put("calendar.day.friday", "Jum'at");
	    lang.put("calendar.day.saturday", "Sabtu");
	    lang.put("calendar.month.january", "Januari");
	    lang.put("calendar.month.february", "Februari");
	    lang.put("calendar.month.march", "Maret");
	    lang.put("calendar.month.april", "April");
	    lang.put("calendar.month.may", "Mei");
	    lang.put("calendar.month.june", "Juni");
	    lang.put("calendar.month.july", "Juli");
	    lang.put("calendar.month.august", "Agustus");
	    lang.put("calendar.month.september", "September");
	    lang.put("calendar.month.october", "Oktober");
	    lang.put("calendar.month.november", "November");
	    lang.put("calendar.month.december", "Desember");
	    
	    // Media player
	    lang.put("media.player.label", "Media player");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Viewer gambar");
	    lang.put("image.viewer.zoom.in", "Besarkan");
	    lang.put("image.viewer.zoom.out", "Kecilkan");
	    
	    // Debug console
	    lang.put("debug.console.label", "Konsol Debug");
	    lang.put("debug.enable.disable", "CTRL+Z untuk meng-enable atau disable mode debug");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Umum");
	    lang.put("dashboard.tab.news", "Berita");
	    lang.put("dashboard.tab.user", "User");
	    lang.put("dashboard.tab.workflow", "Workflow");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Peta Keyword");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "Berita");
	    lang.put("dashboard.user.locked.documents", "Dokumen terkunci");
	    lang.put("dashboard.user.checkout.documents", "Dokumen ter-checkout");
	    lang.put("dashboard.user.last.modified.documents", "Dokumen termodifikasi terakhir");
	    lang.put("dashboard.user.last.downloaded.documents", "Dokumen terdownload terakhir");
	    lang.put("dashboard.user.subscribed.documents", "Dokumen tersubskripsi");
	    lang.put("dashboard.user.subscribed.folders", "Folder tersubskripsi");
	    lang.put("dashboard.user.last.uploaded.documents", "Dokumen ter-upload terakhir");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Dokumen paling banyak di-view minggu lalu");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Dokumen paling banyak di-view bulan lalu");
	    lang.put("dashboard.general.last.week.top.modified.documents", "Dokumen paling banyak dimodifikasi minggu lalu");
	    lang.put("dashboard.general.last.month.top.modified.documents", "Dokumen paling banyak dimodifikasi bulan lalu");
	    lang.put("dashboard.general.last.uploaded.documents", "Dokumen upload terkini");
	    lang.put("dashboard.workflow.pending.tasks", "Task terpending");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Task terpending yang belum di-assign");
	    lang.put("dashboard.workflow.task", "Task");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Nama");
	    lang.put("dashboard.workflow.task.created", "Tanggal dibuat");
	    lang.put("dashboard.workflow.task.start", "Tanggal mulai");
	    lang.put("dashboard.workflow.task.duedate", "Due date");
	    lang.put("dashboard.workflow.task.end", "Tanggal akhir");
	    lang.put("dashboard.workflow.task.description", "Deskripsi");
	    lang.put("dashboard.workflow.task.process.instance", "Proses instance");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "Versi");
	    lang.put("dashboard.workflow.task.process.name", "Nama");
	    lang.put("dashboard.workflow.task.process.description", "Deskripsi");
	    lang.put("dashboard.workflow.task.process.data", "Data");
	    lang.put("dashboard.workflow.comments", "Catatan");
	    lang.put("dashboard.workflow.task.process.forms", "Form");
	    lang.put("dashboard.workflow.add.comment","Tambah komentar");
	    lang.put("dashboard.workflow.task.process.definition", "Definisi proses");
	    lang.put("dashboard.workflow.task.process.path", "Path");
	    lang.put("dashboard.refreshing", "Merefresh");
	    lang.put("dashboard.keyword", "Keyword");
	    lang.put("dashboard.keyword.suggest", "Ketik keyword");
	    lang.put("dashboard.keyword.all", "Semua keyword");
	    lang.put("dashboard.keyword.top", "Top keyword");
	    lang.put("dashboard.keyword.related", "Related keyword");
	    lang.put("dashboard.keyword.goto.document", "Ke dokumen");
	    lang.put("dashboard.keyword.clean.keywords", "Bersihkan keyword");
	    lang.put("dashboard.mail.last.imported.mails", "Mail elektronik");
	    lang.put("dashboard.mail.last.imported.attached.documents", "Attachment");
	    
	    // Workflow
	    lang.put("workflow.label", "Menstart workflow");
	    
	    // User configuration
	    lang.put("user.preferences.label", "Konfigurasi User");
	    lang.put("user.preferences.user", "User");
	    lang.put("user.preferences.password", "Password");
	    lang.put("user.preferences.mail", "E-mail");
	    lang.put("user.preferences.roles","Roles");
	    lang.put("user.preferences.imap.host", "IMAP server");
	    lang.put("user.preferences.imap.user", "IMAP userid");
	    lang.put("user.preferences.imap.user.password", "IMAP user password");
	    lang.put("user.preferences.imap.folder", "IMAP folder");
	    lang.put("user.preferences.password.error", "Error: password berbeda");
	    lang.put("user.preferences.user.data", "Account pengguna");
	    lang.put("user.preferences.mail.data", "Account mail");
	    lang.put("user.preferences.imap.error", "Semua field wajib menset konfigurasi mail");
	    lang.put("user.preferences.imap.password.error.void", "Password nggak boleh kosong  untuk mail IMAP");
	    lang.put("user.preferences.imap.test.error","IMAP configuration error");
	    lang.put("user.preferences.imap.test.ok","IMAP configuration ok");

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Tambah keyword pada thesaurus ");
	    lang.put("thesaurus.tab.tree", "Tree");
	    lang.put("thesaurus.tab.keywords", "Keyword");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Tambah kategori");
	    lang.put("categories.folder.error.delete", "Tak bisa hapus kategori yang ada dokumennya");

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
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Akses Dokumen ditolak");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Penguncian Dokumen ditolak");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Internal error pada repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Path ke dokumen tak ketemu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Version  error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Akses Dokumen ditolak");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Dokumen tak ketemu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Dokumen sudah ada");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Penguncian Dokumen ditolak");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Dokumen harus di unlock dulu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Internal error pada repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Internal error pada aplikasi");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Path ke dokumen tak ketemu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Akses ke folder ditolak");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Folder tak ketemu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Folder sudah ada");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Internal error pada repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Internal error pada repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Path ke Folder tak ketemu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_DatabaseException, "Database error");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_AccessDenied, "Akses ke Item ditolak");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_ItemNotFound, "Item tak ditemukan");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_Repository, "internal error pada repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_General, "Internal  error pada repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Dokumen tak ditemukan");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Internal error pada repository");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Format file tak didukung");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Dokumen sudah ada");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Ukuran dokumen melebihi batas");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_DocumentNameMismatch, "Document name is diferent");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Sesi  terputus");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Generic error pada saat query");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Simpanan pencarian harus unique / tunggal");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Nama bookmark harus unique / tunggal");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Sesi terputus koneksi");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Internal error pada repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Internal error pada repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error pada peralatan I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Internal error pada repository");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error saat mengkonfigurasi browser");
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
