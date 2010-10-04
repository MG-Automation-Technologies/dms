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
 * Vietnamese (Tiếng Việt)
 * email: Ngan.lkvn@gmail.com	 
 * @author: Ngan.LK 
 */
public class Lang_zh_VN {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd-MM-yyyy HH:mm:ss");
	    lang.put("general.day.pattern", "dd-MM-yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "Khởi động OpenKM");
	    lang.put("startup.starting.loading", "Đang mở OpenKM");
	    lang.put("startup.taxonomy", "Mở Phân loại văn bản");
	    lang.put("startup.categories", "Mở Thư mục gốc");//Getting categories root node
	    lang.put("startup.template", "Mở Bản mẫu");
	    lang.put("startup.personal", "Mở Người dùng");
	    lang.put("startup.mail", "Mở Địa chỉ email");
        lang.put("startup.trash", "Mở Thùng rác");
	    lang.put("startup.user.home", "Mở trang thông tin người dùng");
	    lang.put("startup.bookmarks", "Mở bookmarks");
	    lang.put("startup.loading.taxonomy", "Đang mở Phân loại văn bản");
	    lang.put("startup.loading.taxonomy.getting.folders", "Đang tải  các thư mục của Phân loại  văn bản");
        lang.put("startup.loading.taxonomy.eval.params", "Đang tải các tham số của Phân loại văn bản");
        lang.put("startup.loading.taxonomy.open.path", "Đang tải đường dẫn của Phân loại văn bản");
        lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "Đang tải file trong thư mục");
        lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "Đang tải đường dẫn của văn bản");
        lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Đang tải đường dẫn của Email");
	    lang.put("startup.loading.personal", "Đang tải thông tin người dùng");
	    lang.put("startup.loading.mail", "Đang tải mail");
	    lang.put("startup.loading.templates", "Đang tải các biểu mẫu");
	    lang.put("startup.loading.mail", "Đang tải mail");//Loading e-mails
	    lang.put("startup.loading.categories", "Đang tải thư mục gốc");//Loading categories
	    lang.put("startup.loading.thesaurus", "Đang mở phần lưu trữ");//Loading thesaurus
	    lang.put("startup.loading.trash", "Đang mở thùng rác");
	    lang.put("startup.loading.history.search", "Đang tìm kiếm");
	    lang.put("startup.loading.user.values", "Đang mở thông tin người dùng");
	    lang.put("startup.keep.alive", "Đang tồn tại");
	    
	    // Update notification
	    lang.put("openkm.update.available", "Cập nhật OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "Phân loại văn bản");
	    lang.put("leftpanel.label.trash", "Thùng rác");
	    lang.put("leftpanel.label.mail", "E-mail");//E-mail
	    lang.put("leftpanel.label.stored.search", "Tìm kiếm");
	    lang.put("leftpanel.label.categories", "Phân loại");//Categories
	    lang.put("leftpanel.label.thesaurus", "Kho lưu trữ");//Thesaurus
	    lang.put("leftpanel.label.templates", "Biểu mẫu");
	    lang.put("leftpanel.label.my.documents", "Văn bản");
	    lang.put("leftpanel.label.user.search", "Thông tin người dùng");//User news
	    lang.put("leftpanel.label.all.repository", "Kho tổng hợp"); //All repository
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Tạo thư mục");
	    lang.put("tree.menu.directory.remove", "Xóa thư mục");
	    lang.put("tree.menu.directory.rename", "Đổi tên thư mục");
	    lang.put("tree.menu.directory.refresh", "Làm tươi");
	    lang.put("tree.menu.directory.move", "Chuyển thư mục");
	    lang.put("tree.menu.directory.copy", "Copy thư mục");
	    lang.put("tree.menu.directory.add.document", "Thêm văn bản");
	    lang.put("tree.menu.add.bookmark", "Tạo bookmark");
	    lang.put("tree.menu.set.home", "Trở về trang chủ");
	    lang.put("tree.menu.export", "Xuất ra");
	    lang.put("tree.status.refresh.folder", "Làm tươi thư mục");
	    lang.put("tree.status.refresh.create", "Làm tươi sau khi tạo mới");
	    lang.put("tree.status.refresh.delete", "Làm tươi sau khi xóa");
	    lang.put("tree.status.refresh.rename", "Làm tươi sau khi đổi tên");
	    lang.put("tree.status.refresh.restore", "Làm tươi sau khi phục hồi");
	    lang.put("tree.status.refresh.purge", "Làm tươi sau khi tẩy");
	    lang.put("tree.status.refresh.get", "Làm tươi sau khi nhận");
	    lang.put("tree.folder.new", "Thư mục mới");
	    lang.put("tree.status.refresh.add.subscription", "Làm tươi khi thêm mô tả");
	    lang.put("tree.status.refresh.remove.subscription", "Làm tươi khi xóa mô tả");
	    lang.put("tree.status.refresh.get.root", "Làm tươi khi nhận thư mục gốc");
	    lang.put("tree.status.refresh.get.keywords", "Làm tươi khi nhận từ khóa");//Refreshing keywords
	    lang.put("tree.status.refresh.get.user.home", "Trang chủ người dùng");
	    lang.put("tree.status.refresh.purge.trash", "Thùng rác");
	    lang.put("tree.menu.directory.find.folder","Tìm thư mục...");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Phục hồi");
	    lang.put("trash.menu.directory.purge", "Tẩy");
	    lang.put("trash.menu.directory.purge.trash", "Thùng rác");
	    lang.put("trash.directory.select.label", "Chọn nhãn");
	    
	    // General menu
	    lang.put("general.menu.file", "File");
	    	lang.put("general.menu.file.exit", "Thoát");
            lang.put("general.menu.file.purge.trash", "Làm rỗng thùng rác");
	    lang.put("general.menu.edit", "Sửa");
			lang.put("general.menu.file.create.directory", "Tạo thư mục");
			lang.put("general.menu.file.download.document", "Download tài liệu");
			lang.put("general.menu.file.download.document.pdf", "Xuất văn bản ra pdf");
			lang.put("general.menu.file.send.link", "Gửi liên kết văn bản");
			lang.put("general.menu.file.send.attachment", "Gửi file đính kèm");
			lang.put("general.menu.file.lock", "Đã bị khóa");
			lang.put("general.menu.file.unlock", "Mở khóa");
			lang.put("general.menu.file.add.document", "Thêm văn bản");
			lang.put("general.menu.file.checkout", "Checkout");
			lang.put("general.menu.file.checkin", "Checkin");
			lang.put("general.menu.file.cancel.checkout", "Hủy việc Checkout");
			lang.put("general.menu.file.delete", "Xóa file");
			lang.put("general.menu.file.refresh", "Làm tươi file");
			lang.put("general.menu.file.scanner", "Scanner");//Scanner
			lang.put("general.menu.file.uploader", "Công cụ upload file");//File uploader
	    lang.put("general.menu.tools", "Công cụ");
	    	lang.put("general.menu.tools.languages", "Ngôn ngữ");
	    	lang.put("general.menu.tools.skin", "Giao diện");
    			lang.put("general.menu.tools.skin.default", "Giao diện mặc định");
    			lang.put("general.menu.tools.skin.default2", "Giao diện mặc định 2");
    			lang.put("general.menu.tools.skin.mediumfont", "font trung bình");
    			lang.put("general.menu.tools.skin.bigfont", "Font lớn");
    		lang.put("general.menu.debug.console", "Dòng lệnh debug");
    		lang.put("general.menu.administration", "Admin");
    		lang.put("general.menu.tools.preferences", "Tùy chọn");
    			lang.put("general.menu.tools.user.preferences", "Tùy chọn người dùng");
    	lang.put("general.menu.bookmark", "Bookmark");
	    	lang.put("general.menu.bookmark.home", "Trang chủ");
	    	lang.put("general.menu.bookmark.default.home", "Thiết lập mặc định");
	    	lang.put("general.menu.bookmark.edit", "Chỉnh sửa bookmark");
	    lang.put("general.menu.help", "Trợ giúp");
		    lang.put("general.menu.bug.report", "Báo cáo lỗi");
		    lang.put("general.menu.support.request", "Yêu cầu hỗ trợ");
		    lang.put("general.menu.public.forum", "Diễn đàn trao đổi");
		    lang.put("general.menu.project.web", "Trang web của dự án");
		    lang.put("general.menu.version.changes", "Thay đổi phiên bản");
		    lang.put("general.menu.documentation", "Tài liệu hướng dẫn");
		    lang.put("general.menu.about", "Về OpenKM");
	    lang.put("general.connected", "Đã được kết nối");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Tên");
	    lang.put("filebrowser.date.update", "Cập nhật");
	    lang.put("filebrowser.size", "Kích thước");
	    lang.put("filebrowser.path", "Đường dẫn");
	    lang.put("filebrowser.author", "Tác giả");
	    lang.put("filebrowser.version", "Phiên bản");
        lang.put("filebrowser.menu.checkout", "Checkout");
	    lang.put("filebrowser.menu.checkin", "Checkin");
	    lang.put("filebrowser.menu.delete", "Xóa");
	    lang.put("filebrowser.menu.rename", "Đổi tên");
	    lang.put("filebrowser.menu.checkout.cancel", "Hủy checkout");
	    lang.put("filebrowser.menu.lock", "Khóa");
	    lang.put("filebrowser.menu.unlock", "Mở khóa");
	    lang.put("filebrowser.menu.download", "Download");
	    lang.put("filebrowser.menu.move", "Di chuyển");
	    lang.put("filebrowser.menu.copy", "Copy");
	    lang.put("filebrowser.menu.create.from.template", "Tạo mới từ biểu mẫu");
	    lang.put("filebrowser.menu.add.property.group", "Thêm thuộc tính cho nhóm");
	    lang.put("filebrowser.menu.remove.property.group", "Xóa thuộc tính của nhóm");
	    lang.put("filebrowser.menu.start.workflow", "Kế hoạch công việc");
	    lang.put("filebrowser.menu.add.subscription", "Thêm mô tả");
	    lang.put("filebrowser.menu.remove.subscription", "Xóa mô tả");
	    lang.put("filebrowser.menu.add.bookmark", "Thêm bookmark");
	    lang.put("filebrowser.menu.set.home", "Thiết lập trang chủ");
	    lang.put("filebrowser.menu.refresh", "Làm tươi");
	    lang.put("filebrowser.menu.export", "Xuất file");
	    lang.put("filebrowser.menu.play", "Play");
	    lang.put("filebrowser.menu.image.viewer", "Xem ảnh");
	    lang.put("filebrowser.status.refresh.folder", "Danh sách thư mục đang được cập nhật");
	    lang.put("filebrowser.status.refresh.document", "Danh sách tài liệu đang được cập nhật");
	    lang.put("filebrowser.status.refresh.mail", "DS Email đang được cập nhật");
	    lang.put("filebrowser.status.refresh.delete.folder", "Xóa thư mục");
	    lang.put("filebrowser.status.refresh.delete.document", "Xóa tài liệu");
	    lang.put("filebrowser.status.refresh.checkout", "Kiểm tra các tài liệu");
	    lang.put("filebrowser.status.refresh.lock", "Tài liệu này đã bị khóa");
	    lang.put("filebrowser.status.refresh.unlock", "Tài liệu này đã được mở khóa");
	    lang.put("filebrowser.status.refresh.document.rename", "Đổi tên tài liệu");
	    lang.put("filebrowser.status.refresh.folder.rename", "Đổi tên thư mục");
	    lang.put("filebrowser.status.refresh.document.purge", "Xóa tài liệu");
	    lang.put("filebrowser.status.refresh.folder.purge", "Xóa thư mục");
	    lang.put("filebrowser.status.refresh.folder.get", "Cập nhật thư mục");
	    lang.put("filebrowser.status.refresh.document.get", "Cập nhật tài liệu");
	    lang.put("filebrowser.status.refresh.add.subscription", "Thêm mô tả");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Xóa mô tả");
	    lang.put("filebrowser.status.refresh.get.user.home", "Truy cập vào thư mục gốc của người dùng");
	    lang.put("filebrowser.status.refresh.delete.mail", "Xóa mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Làm sạch mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Gửi file");
	    lang.put("fileupload.status.sending", "Đang giử file...");
	    lang.put("fileupload.status.indexing", "Tập tin chỉ mục...");
	    lang.put("fileupload.status.ok", "Tải file lên 1 cách chính xác");
	    lang.put("fileupload.upload.status", "Trạng thái tải lên");
	    lang.put("fileupload.upload.uploaded", "File đã được tải lên");
	    lang.put("fileupload.upload.completed", "Việc tải file đã hoàn thành");
	    lang.put("fileupload.upload.runtime", "Thời gian chạy");
	    lang.put("fileupload.upload.remaining", "Đổi tên file");
	    lang.put("fileupload.button.close", "Đóng");
	    lang.put("fileupload.button.add.other.file", "Thêm file khác");
	    lang.put("fileupload.status.move.file", "Di chuyển file...");
	    lang.put("fileupload.status.move.mail", "Di chuyển email...");
	    lang.put("fileupload.status.copy.file", "Đang copy file...");
	    lang.put("fileupload.status.copy.mail", "Đang copy mail...");
	    lang.put("fileupload.status.restore.file", "Phục hồi lại file...");
	    lang.put("fileupload.status.restore.mail", "Phục hồi mail...");
	    lang.put("fileupload.status.move.folder", "Di chuyển thư mục...");
	    lang.put("fileupload.status.copy.folder", "Copy thư mục...");
	    lang.put("fileupload.status.restore.folder", "Phục hồi thư mục...");
	    lang.put("fileupload.status.create.from.template", "Tạo tài liệu mới từ biểu mẫu...");
	    lang.put("fileupload.status.of", "của");//of
	    lang.put("fileupload.label.insert", "Thêm mới");
	    lang.put("fileupload.label.update", "Cập nhật");
	    lang.put("fileupload.label.users.notify", "Thông báo cho người sử dụng");
	    lang.put("fileupload.label.comment", "Chú thích");
	    lang.put("fileupload.label.users.to.notify",  "Có thông báo mới");
	    lang.put("fileupload.label.users",  "Người dùng");
	    lang.put("fileupload.label.groups.to.notify","Thông báo theo nhóm"); //Groups to notify
	    lang.put("fileupload.label.groups","Nhóm");//Groups
	    lang.put("fileupload.label.must.select.users",  "Bạn phải chọn 1 thông báo người dùng");
	    lang.put("fileupload.label.importZip", "Import dưới dạng file zip");
	    lang.put("fileupload.label.notify.comment", "Thông báo tin nhắn");
	    lang.put("fileupload.label.error.importing.zip", "Có lỗi trong quá trình import file zip");
	    lang.put("fileupload.label.error.move.file", "Có lỗi khi di chuyển file");
	    lang.put("fileupload.label.error.move.mail", "Có lỗi khi di chuyển mail");
	    lang.put("fileupload.label.error.copy.file", "Có lỗi khi copy file");
	    lang.put("fileupload.label.error.copy.mail", "Có lỗi khi copy mail");
	    lang.put("fileupload.label.error.restore.file", "Có lỗi khi phục hồi file");
	    lang.put("fileupload.label.error.restore.mail", "Có lỗi khi phục hồi mail");
	    lang.put("fileupload.label.error.move.folder", "Có lỗi khi di chuyển thư mục");
	    lang.put("fileupload.label.error.copy.folder", "Có lỗi khi copy thư mục");
	    lang.put("fileupload.label.error.restore.folder", "Có lỗi khu phục hồi thư mục");
	    lang.put("fileupload.label.error.create.from.template", "Có lỗi khi tạo mới từ biểu mẫu");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Có lỗi: không cho phép di chuyển thư mục con");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Có lỗi: không cho phép copy cùng 1 thư mục");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Có lỗi: không cho phép tạo mới từ biểu mẫu giống nhau trong cùng 1 thư mục");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Đặc điểm của tài liệu");
	    lang.put("tab.document.notes", "Ghi chú về tài liệu");//Notes
	    lang.put("tab.document.history", "Phiên bản tài liệu");
	    lang.put("tab.document.status.history", "Cập nhật lịch sử");
	    lang.put("tab.status.security.role", "Thiết lập quyền");
	    lang.put("tab.status.security.user", "Bảo mật người dùng");
	    lang.put("tab.document.status.group.properties", "Đặc điểm nhóm");
	    lang.put("tab.document.status.set.keywords", "Thiết lập từ khóa");
	    lang.put("tab.document.status.set.categories", "Thiết lập phân loại tài liệu");//Updating categories
	    lang.put("tab.document.status.get.version.history.size", "Xem các kích thước khác nhau của tài liệu");
	    lang.put("tab.document.status.purge.version.history", "Xóa các phiên bản lịch sử");
	    lang.put("tab.document.status.restore.version", "Phục hồi các phiên bản");
	    lang.put("tab.document.security", "Bảo mật");
	    lang.put("tab.document.preview", "Xem trước");//Preview
	    lang.put("tab.folder.properties", "Đặc điểm");
	    lang.put("tab.folder.security", "Bảo mật");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Desktop");
	    lang.put("tab.workspace.search", "Tìm kiếm");
	    lang.put("tab.workspace.dashboard", "Bảng điều khiển");
	    lang.put("tab.workspace.administration", "Admin");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Tên");
	    lang.put("document.folder", "Thư mục");
	    lang.put("document.size", "Kích thước");
	    lang.put("document.created", "Ngày tạo");
	    lang.put("document.lastmodified", "Lần sửa cuối");
	    lang.put("document.mimetype", "Định dạng MIME");
	    lang.put("document.keywords", "Từ khóa");
	    lang.put("document.by", "Tác giả");
	    lang.put("document.status", "Trạng thái");
	    lang.put("document.status.checkout", "Checkout");
	    lang.put("document.status.locked", "Đã bị khóa");
	    lang.put("document.status.normal", "Chuẩn");
	    lang.put("document.subscribed", "Đăng ký");
	    lang.put("document.subscribed.yes", "Đã được đăngk ý");
	    lang.put("document.subscribed.no", "Chưa được đăng ký");
	    lang.put("document.history.size", "Dung lượng");
	    lang.put("document.subscribed.users", "Người dùng hiện hành");
	    lang.put("document.url", "Đường dẫ URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "Thêm lưu ý");
	    lang.put("document.keywords.cloud", "Từ khóa");
	    lang.put("document.categories", "Loại");//Categories
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "Tên");
	    lang.put("folder.parent", "Thư mục gốc");
	    lang.put("folder.created", "Ngày tạo");
	    lang.put("folder.by", "Tác giả");
	    lang.put("folder.subscribed", "Quyền đăng ký");
	    lang.put("folder.subscribed.yes", "Đã đăng ký");
	    lang.put("folder.subscribed.no", "Chưa đăng ký");
	    lang.put("folder.subscribed.users", "Người dùng hiện hành");
	    lang.put("folder.url", "Đường dẫn của thư mục");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Số thư mục");//Folders
	    lang.put("folder.number.documents", "Số tài liệu");//Documents
	    lang.put("folder.number.mails", "Số mail");//Mails
	    
	    // Version
	    lang.put("version.name", "Phiên bản");
	    lang.put("version.created", "Ngày tạo");
	    lang.put("version.author", "Tác giả");
	    lang.put("version.size", "Dung lượng");
	    lang.put("version.purge.document", "Nén tài liệu");
	    lang.put("version.comment", "Chú thích");
	    
	    // Security
	    lang.put("security.label", "Bảo mật");
	    lang.put("security.group.name", "Tên nhóm");
	    lang.put("security.group.permission.read", "Đọc");
	    lang.put("security.group.permission.write", "Viết");
	    lang.put("security.group.permission.delete", "Xóa");
	    lang.put("security.group.permission.security", "Bảo mật");
	    lang.put("security.user.name", "Người dùng");
	    lang.put("security.user.permission.read", "Đọc");
	    lang.put("security.user.permission.write", "Viết");
	    lang.put("security.user.permission.delete", "Xóa");
	    lang.put("security.user.permission.security", "Bảo mật");
	    lang.put("security.users", "Người dùng");
	    lang.put("security.groups", "Nhóm");
	    lang.put("security.recursive", "Sửa đổi các điều khoản");
	    lang.put("secutiry.filter.by.users","Truy vấn người dùng");//Users filter
	    lang.put("secutiry.filter.by.groups","Truy vấn nhóm");//Groups filter
	    lang.put("security.status.updating","Đang cập nhật quyền...");
	    
	    // Preview
	    lang.put("preview.unavailable", "Không được xem trước");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");//Reply to
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Tiêu đề");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "Lỗi hệ thống");
	    lang.put("error.invocation", "Lỗi khi kết nối với máy chủ");
	    
	    // About
	    lang.put("about.label", "Về OpenKM");
	    lang.put("about.loading", "Đang mở...");
	    
	    // Logout
	    lang.put("logout.label", "Thoát");
	    lang.put("logout.closed", "OpenKM đã được đóng.");
	    lang.put("logout.logout", "Bị hủy bỏ, xin vui lòng chờ");
	    
	    // Confirm
	    lang.put("confirm.label", "Xác nhận");
	    lang.put("confirm.delete.folder", "Bạn có muốn xóa thư mục không?");
	    lang.put("confirm.delete.document", "Bạn có muốn xóa tài liệu không?");
	    lang.put("confirm.delete.trash", "Bạn có muốn làm rỗng thùng rác không?");
        lang.put("confirm.purge.folder", "Bạn có muốn nén thư mục không?");
        lang.put("confirm.purge.document", "Bạn có muốn nén tài liệu không?");
        lang.put("confirm.delete.propety.group", "Bạn có muốn xóa thuộc tính nhóm không?");
        lang.put("confirm.purge.version.history.document", "Bạn có muốn xóa lịch sử tài liệu không?");
	    lang.put("confirm.purge.restore.document", "Bạn có thực sự muốn khôi phục lại các phiên bản của tài liệu không?");
	    lang.put("confirm.set.default.home", "Bạn có thực sự muốn thiết lập thư mục gốc mặc định không?");
	    lang.put("confirm.delete.saved.search", "Bạn có thực sự muốn xóa phần tìm kiếm đã lưu trước đó không？");//¿ Do you really want to delete saved search ?
	    lang.put("confirm.delete.user.news", "Bạn có thực sự muốn xóa thông tin người dùng không？");//¿ Do you really want to delete user news ?
	    lang.put("confirm.delete.mail", "Bạn có thực sự muốn xóa mail không？");//¿ Do you really want to delete mail ?
	    lang.put("confirm.get.pooled.workflow.task","Bạn có muốn xóa các phần công việc đã được giao cho bạn không?");//¿ Do you want to assign this task to you ?
	    lang.put("confirm.force.unlock", "Do you really want to force the document unlock ?");
	    lang.put("confirm.force.cancel.checkout", "Do you really want to force the document cancel checkout ?");
	    
	    // Search inputs
	    lang.put("search.context", "Phạm vi tìm kiếm");
	    lang.put("search.content", "Nội dung");
	    lang.put("search.name", "Tên");
	    lang.put("search.keywords", "Từ khóa");
	    lang.put("search.folder", "Thư mục");//Folder
	    lang.put("search.category", "Phân loại");//Category
	    lang.put("search.results", "Kết quả tìm kiếm");
	    lang.put("search.to", "Để");
	    lang.put("search.page.results", "Kết quả tìm kiếm mỗi trang");
	    lang.put("search.add.property.group", "Tìm theo thuộc tính nhóm");
	    lang.put("search.mimetype", "Định dạng Mime");
	    lang.put("search.type", "Kiểu");
	    lang.put("search.type.document", "Kiểu tài liệu");
	    lang.put("search.type.folder", "Thư mục");
	    lang.put("search.type.mail", "mail");
	    lang.put("search.advanced", "Tìm nâng cao");
	    lang.put("search.user", "Người dùng");
	    lang.put("search.date.and", "Ngày");
	    lang.put("search.date.range", "Phạm vi");
	    lang.put("search.save.as.news", "Thông tin người dùng được lưu trữ");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Lọc theo thư mục");
	    lang.put("search.category.filter", "Lọc theo phần loại tài liệu");//Filter by category
	    
	    // Search results
	    lang.put("search.result.name", "Kết quả theo Tên");
	    lang.put("search.result.score", "Phạm vi");
	    lang.put("search.result.size", "Dung lượng");
	    lang.put("search.result.date.update", "Ngày cập nhật");
	    lang.put("search.result.author", "Tác giả");
	    lang.put("search.result.version", "Kết quả theo phiên bản");
	    lang.put("search.result.path", "Kết quả theo đường dẫn");
	    lang.put("search.result.menu.download", "Tải về");
	    lang.put("search.result.menu.go.folder", "Chuyển đến thư mục");
	    lang.put("search.result.menu.go.document", "Chuyển đến tài liệu");
	    lang.put("search.result.status.findPaginated", "Cập nhật tìm kiếm");
	    lang.put("search.result.status.runsearch", "Lưu kết quả tìm kiếm này");
	    
	    // Search saved
	    lang.put("search.saved.run", "Đang lưu");
	    lang.put("search.saved.delete", "Xóa");
	    lang.put("search.saved.status.getsearchs", "Cập nhật phần tìm kiếm đã lưu");
        lang.put("search.saved.status.savesearch", "Lưu tìm kiếm đang được cập nhật");
        lang.put("search.saved.status.deletesearch", "Xóa phần tìm kiếm đã lưu ");
        lang.put("search.saved.status.getusernews", "Cập nhật thông tin người dùng");
	    
	    // Button
	    lang.put("button.close", "Đóng");
	    lang.put("button.logout", "Thoát");
	    lang.put("button.update", "Cập nhật");
	    lang.put("button.cancel", "Hủy");
	    lang.put("button.accept", "Đồng ý");
	    lang.put("button.restore", "Phục hồi");
	    lang.put("button.move", "Di chuyển");
	    lang.put("button.change", "Thay đổi");
	    lang.put("button.search", "Tìm kiếm");
	    lang.put("button.save.search", "Lưu tìm kiếm");
	    lang.put("button.view", "Xem");
	    lang.put("button.clean", "Làm sạch");
	    lang.put("button.add", "Thêm");
	    lang.put("button.delete", "Xóa");
	    lang.put("button.copy", "Copy");
	    lang.put("button.create", "Tạo mới");
	    lang.put("button.show", "Hiển thị");
	    lang.put("button.memory", "Bộ nhớ");
	    lang.put("button.copy.clipboard", "Sao chép vào clipboard");	//Copy to clipboard
	    lang.put("button.start", "Bắt đầu");
	    lang.put("button.select", "Chọn");
	    lang.put("button.test", "Kiểm tra");//Test
	    lang.put("button.next", "Tiếp theo");//Next
	    
	    // Group
	    lang.put("group.label", "Nhóm");
	    lang.put("group.group", "Nhóm");
	    lang.put("group.property.group", "Thuộc tính nhóm");
	    
	    // Bookmark
	    lang.put("bookmark.label", "Bookmark");
	    lang.put("bookmark.name", "Tên");
	    lang.put("bookmark.edit.label", "Sửa");
	    lang.put("bookmark.path", "Đường dẫn");
	    lang.put("bookmark.type", "Định dạng");
	    lang.put("bookmark.type.document", "Định dạng văn bản");
	    lang.put("bookmark.type.folder", "Kiểu thư mục");
	    
	    // Notify
	    lang.put("notify.label", "File đính kèm");
	    lang.put("notify.label.attachment", "Gửi file đính kèm");
	    
	    // Status
	    lang.put("status.document.copied", "Tài liệu đã được sao chép");
        lang.put("status.document.cut", "Tài liệu đã được cut");
	    lang.put("status.folder.copied", "Thư mục đã được sao chép");
        lang.put("status.folder.cut", "Đã cut thư mục");
	    lang.put("status.keep.alive.error", "Đã được phát hiện và bị ngắt kết nối từ máy chủ (giữ hoạt động)");
	    lang.put("status.debug.enabled", "Cho phép gỡ lỗi");
	    lang.put("status.debug.disabled", "Không được phép gỡ lỗi");
	    lang.put("status.network.error.detected", "Lỗi mạng");//Network error detected
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "Chủ nhật");
	    lang.put("calendar.day.monday", "Thứ hai");
	    lang.put("calendar.day.tuesday", "Thứ ba");
	    lang.put("calendar.day.wednesday", "Thứ tư");
	    lang.put("calendar.day.thursday", "Thứ năm");
	    lang.put("calendar.day.friday", "Thứ sáu");
	    lang.put("calendar.day.saturday", "Thứ bảy");
	    lang.put("calendar.month.january", "Tháng 1");
	    lang.put("calendar.month.february", "Tháng 2");
	    lang.put("calendar.month.march", "Tháng 3");
	    lang.put("calendar.month.april", "Tháng 4");
	    lang.put("calendar.month.may", "Tháng 5");
	    lang.put("calendar.month.june", "Tháng 6");
	    lang.put("calendar.month.july", "Tháng 7");
	    lang.put("calendar.month.august", "Tháng 8");
	    lang.put("calendar.month.september", "Tháng 9");
	    lang.put("calendar.month.october", "Tháng 10");
	    lang.put("calendar.month.november", "Tháng 11");
	    lang.put("calendar.month.december", "Tháng 12");
	    
	    // Media player
	    lang.put("media.player.label", "Trình nghe nhạc");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "Xem ảnh");
	    lang.put("image.viewer.zoom.in", "Phóng to");
	    lang.put("image.viewer.zoom.out", "Thu nhỏ");
	    
	    // Debug console
	    lang.put("debug.console.label", "Bảng điều khiển debug");
	    lang.put("debug.enable.disable", "CTRL+Z chế độ chuyển đổi cho phép hoặc không cho phép");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "Tổng hợp");//General
	    lang.put("dashboard.tab.news", "Thông tin");//News
	    lang.put("dashboard.tab.user", "Người dùng");//User
	    lang.put("dashboard.tab.workflow", "Quy trình làm việc");//Workflow
	    lang.put("dashboard.tab.mail", "Email");//Mail
	    lang.put("dashboard.tab.keymap", "Bản đồ từ khóa");//Keyword map

	    // Dahboard general
	    lang.put("dashboard.new.items", "Mới"); //New
	    lang.put("dashboard.user.locked.documents", "Tài liệu đã bị khóa");//Locked documents
	    lang.put("dashboard.user.checkout.documents", "Cập nhật tài liệu");//Checkout documents
	    lang.put("dashboard.user.last.modified.documents", "Lần sửa cuối");//Last modified documents
	    lang.put("dashboard.user.last.downloaded.documents", "Lần tải về cuối cùng");//Last downloaded documents
	    lang.put("dashboard.user.subscribed.documents", "Tài liệu đã được duyệt");//Subscribed documents
	    lang.put("dashboard.user.subscribed.folders", "Thư mục đã được duyệt");//Subscribed folders
	    lang.put("dashboard.user.last.uploaded.documents", "Đưa lên lần cuối");//Last uploaded documents
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "Số lượng xem bài viết cao nhất tính từ tuần trước");//Last week top viewed documents
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "Số lượng xem bài viết cao nhất tình từ tháng trước");//Last month top viewed documents
	    lang.put("dashboard.general.last.week.top.modified.documents", "Số lượng tài liệu được sửa đổi thường xuyên nhất tính từ tuần trước");//Last week top modified documents
	    lang.put("dashboard.general.last.month.top.modified.documents", "Số lượng tài liệu được sửa đổi thường xuyên nhất tình từ tháng trước");//Last month top modified documents
	    lang.put("dashboard.general.last.uploaded.documents", "Tài liệu được gửi lên gần đây nhất");//Last uploaded documents
	    lang.put("dashboard.workflow.pending.tasks", "Số công việc đang chờ");//Pending tasks
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Số công việc đang chờ giao nhiệm vụ");//Unassigned pending tasks
	    lang.put("dashboard.workflow.task", "Công việc");//Task
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "Tên");//Name
	    lang.put("dashboard.workflow.task.created", "Ngày tạo");//Creation date
	    lang.put("dashboard.workflow.task.start", "Ngày bắt đầu");//Start date
	    lang.put("dashboard.workflow.task.duedate", "Ngày hết hạn");//Due date
	    lang.put("dashboard.workflow.task.end", "Ngày kết thúc");//End date
	    lang.put("dashboard.workflow.task.description", "Mô tả công việc");//Description
	    lang.put("dashboard.workflow.task.process.instance", "Người duyệt");//Process instance
	    lang.put("dashboard.workflow.task.process.id", "ID của người duyệt");//
	    lang.put("dashboard.workflow.task.process.version", "Phiên bản");//Version
	    lang.put("dashboard.workflow.task.process.name", "Tên tiến trình");
	    lang.put("dashboard.workflow.task.process.description", "Mô tả");
	    lang.put("dashboard.workflow.task.process.data", "Dữ liệu");//Data
	    lang.put("dashboard.workflow.comments", "Chú thích");//Comments
	    lang.put("dashboard.workflow.task.process.forms", "Mẫu");//Form
	    lang.put("dashboard.workflow.add.comment","Thêm chú thích");//Add comment
	    lang.put("dashboard.workflow.task.process.definition", "Xác định tiến trình");//Process definition
	    lang.put("dashboard.workflow.task.process.path", "Đường dẫn");//Path
	    lang.put("dashboard.refreshing", "Đang làm tươi");//Refreshing
	    lang.put("dashboard.keyword", "Từ khóa");//Keywords
	    lang.put("dashboard.keyword.suggest", "Loại từ khóa");//Type the keyword
	    lang.put("dashboard.keyword.all", "Tất cả từ khóa");//All keywords
	    lang.put("dashboard.keyword.top", "Từ khóa tiêu biểu");//Top keywords
	    lang.put("dashboard.keyword.related", "Từ khóa liên quan");//Related keywords
	    lang.put("dashboard.keyword.goto.document", "Về tài liệu");//Go to document
	    lang.put("dashboard.keyword.clean.keywords", "Làm sạch từ khóa");//Clean keywords
	    lang.put("dashboard.mail.last.imported.mails", "Email");//Electronic mails
	    lang.put("dashboard.mail.last.imported.attached.documents", "File đính kèm");//Attachments
	    
	    // Workflow
	    lang.put("workflow.label", "Công việc");
	    
	    // User configuration
	    lang.put("user.preferences.label", "Cấu hình người dùng");//User configuration
	    lang.put("user.preferences.user", "Tên người dùng"); //User
	    lang.put("user.preferences.password", "Mật khẩu");//Password
	    lang.put("user.preferences.mail", "E-mail");//E-mail
	    lang.put("user.preferences.roles","Quyền");
	    lang.put("user.preferences.imap.host", "IMAP server");//IMAP server
	    lang.put("user.preferences.imap.user", "IMAP username");//IMAP user name
	    lang.put("user.preferences.imap.user.password", "IMAP user password");//IMAP user password
	    lang.put("user.preferences.imap.folder", "IMAP folder");//IMAP folder
	    lang.put("user.preferences.password.error", "Lỗi: hai mật khẩu không giống nhau");//Error: passwords are diferent
	    lang.put("user.preferences.user.data", "Tài khoản người dùng");//User account
	    lang.put("user.preferences.mail.data", "Tài khoản Mail");//Mail account
	    lang.put("user.preferences.imap.error", "Tất cả các trường là bắt buộc để cấu hình mail");//All fields are obligatory to set the mail configurations
	    lang.put("user.preferences.imap.password.error.void", "Trường password không được rỗng trong khi tạo mail IMAP");//Password must not be empty on IMAP mail creation
	    lang.put("user.preferences.imap.test.error","Lỗi cấu hình IMAP");
	    lang.put("user.preferences.imap.test.ok"," Cấu hình IMAP thành công");

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "Thêm từ khóa vào từ điển");//Add thesaurus keyword
	    lang.put("thesaurus.tab.tree", "Cây thư mục");//Tree
	    lang.put("thesaurus.tab.keywords", "Từ khóa");//Keywords
	    
	    // Categories
	    lang.put("categories.folder.select.label", "Thêm phân loại");//Add category
	    lang.put("categories.folder.error.delete", "Không thể xóa phân loại khi đang chứa tài liệu");//Cannot delete category with documents
	    
	    // Wizard
	    lang.put("wizard.document.uploading", "Nén tìa liệu");//Document wizard
	    
	    // User info
	    lang.put("user.info.chat.connect", "Kết nối để chat");//Connect to chat
	    lang.put("user.info.chat.disconnect", "Ngắt kết nối chat");
	    lang.put("user.info.chat.new.room", "Vào phòng Chat");//Net chat room
	    lang.put("user.info.locked.actual", "Tài liệu đã bị khóa");//Locked documents
	    lang.put("user.info.checkout.actual", "Cập nhật tài liệu");//Checkout documents
	    lang.put("user.info.subscription.actual", "Đăng ký thực tế");//Actual subscriptions
	    lang.put("user.info.news.new", "Thông tin người dùng");//News
	    lang.put("user.info.workflow.pending", "Danh sách công việc đang chờ");//Pending workflows
	    lang.put("user.info.user.quota", "Phạm vi sử dụng");//Used quota
	    
	    // Users online popup
	    lang.put("user.online", "Người dùng đang trực tuyến");//Users online
	    
	    // Chat room
	    lang.put("chat.room", "Phòng chat");//Chat
	    lang.put("chat.users.in.room", "Người dùng trực tuyến");//Users
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Tài liệu truy cập bị từ chối");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Việc khóa tài liệu bị từ chối");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Lỗi bộ nhớ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Không tìm thấy đường dẫn");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Lỗi phiên bản");//Version error
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Tài liệu truy cập bị từ chối");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Không tìm thấy tài liệu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Tài liệu không tồn tại");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Việc khóa tài liệu bị từ chối");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Cần mở khóa tài liệu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Bộ nhớ bị lỗi");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Ứng dụng bị lỗi");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Không tìm thấy đường dẫn");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_DatabaseException, "Lỗi cơ sở dữ liệu");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Truy cập thư mục bị từ chối");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Không tìm thấy thư mục");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Thư mục đã tồn tại");
        lang.put("OKM-" + ErrorCode.ORIGIN_OKMFolderService + ErrorCode.CAUSE_Repository, "Lỗi bộ nhớ");
        lang.put("OKM-" + ErrorCode.ORIGIN_OKMFolderService + ErrorCode.CAUSE_General, "Lỗi bộ nhớ");
        lang.put("OKM-" + ErrorCode.ORIGIN_OKMFolderService + ErrorCode.CAUSE_PathNotFound, "Không tìm thấy đường dẫn");
        lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_DatabaseException, "Lỗi cơ sở dữ liệu");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_AccessDenied, "Truy cập bị từ chối");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_ItemNotFound, "Không tìm thấy tài liệu");
        lang.put("OKM-" + ErrorCode.ORIGIN_OKMAuthService + ErrorCode.CAUSE_Repository, "Lỗi bộ nhớ");
        lang.put("OKM-" + ErrorCode.ORIGIN_OKMAuthService + ErrorCode.CAUSE_General, "Lỗi bộ nhớ");
        lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_DatabaseException, "Lỗi cơ sở dữ liệu");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Không tìm thấy tài liệu");
        lang.put("OKM-" + ErrorCode.ORIGIN_OKMPropertyGroupService + ErrorCode.CAUSE_General, "Lỗi bộ nhớ");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Định dạng tập tin không được hỗ trợ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Tài liệu đã tồn tại");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Dung lượng quá lớn, vượt mức cho phép");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_DocumentNameMismatch, "Tên tài liệu không phù hợp");//Document name is diferent
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Đóng phiên");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_DatabaseException, "Lỗi cơ sở dữ liệu");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Lỗi truy vấn thông thường");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "Tên của phần tìm kiếm cần lưu phải là duy nhất");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_DatabaseException, "Lỗi cơ sở dữ liệu");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "Tên tài liệu phải là duy nhất");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Bị ngắt kết nối");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Lỗi nội bộ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_DatabaseException, "Lỗi cơ sở dữ liệu");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Lỗi cục bộ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Đọc và ghi lỗi input/ output");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Lỗi cục bộ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_DatabaseException, "Lỗi cơ sở dữ liệu");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Lỗi khi cấu hình trình duyệt!");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_QuotaExceed, "Vượt quá giới hạn cho phép, liên hệ với ban quản trị để biết thêm chi tiết！");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_AccessDenied, "Truy xuất bị từ chối");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemNotFound, "Không tìm thấy dữ liệu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemExists, "Tài liệu đã tồn tại");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Lock, "Từ chối khóa tài liệu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Repository, "Lỗi cục bộ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_General, "Ứng dụng lỗi cục bộ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_PathNotFound, "Không tìm thấy đường dẫn của tài liệu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_DatabaseException, "Lỗi cơ sở dữ liệu");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_PathNotFound, "Không tìm thấy đường dẫn");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_AccessDenied, "Truy cập bị từ chối");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_Repository, "Lỗi cục bộ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_DatabaseException, "Lỗi cơ sở dữ liệu");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_General, "Ứng dụng lỗi cục bộ"); 
	    
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
