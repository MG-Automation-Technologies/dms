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
 * Chinese traditional 
 * 
 * @author Jerry ( plinius@ms2.url.com.tw )
 */
public class Lang_zh_TW {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "yyyy-MM-dd hh:mm:ss");
	    lang.put("general.day.pattern", "yyyy-MM-dd");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "載入OpenKM");
		lang.put("startup.starting.loading", "開始載入OpenKM");
		lang.put("startup.taxonomy", "正在取得分類根節點");
		lang.put("startup.categories", "Getting categories root node");
		lang.put("startup.thesaurus", "Getting thesaurus root node");
		lang.put("startup.template", "正在取得樣板根節點");
		lang.put("startup.personal", "正在取得個人根節點");
		lang.put("startup.mail", "Getting e-mail root node");
		lang.put("startup.trash", "正在取得回收筒根節點");
		lang.put("startup.user.home", "正在取得使用者根節點");
		lang.put("startup.bookmarks", "正在取得書籤");
	    lang.put("startup.loading.taxonomy", "正在載入分類");
		lang.put("startup.loading.taxonomy.getting.folders", "正在載入分類 - 正在取得資料夾");
		lang.put("startup.loading.taxonomy.eval.params", "正在載入分類 - 正在取得瀏覽器參數");
		lang.put("startup.loading.taxonomy.open.path", "正在載入分類 - 開啟路徑");
		lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "正在載入分類 - 正在取得資料夾");
		lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "正在載入分類 - 正在取得文件");
		lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
		lang.put("startup.loading.personal", "正在取得個人化資料");
		lang.put("startup.loading.mail", "Loading e-mails");
		lang.put("startup.loading.categories", "Loading categories");
		lang.put("startup.loading.thesaurus", "Loading thesaurus");
		lang.put("startup.loading.templates", "正在取得樣板");
		lang.put("startup.loading.trash", "正在取得回收筒");
		lang.put("startup.loading.history.search", "正在取得歷史搜尋");
		lang.put("startup.loading.user.values", "正在取得使用者資料");
		lang.put("startup.keep.alive", "Loading keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "OpenKM更新成功");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "分類");
	    lang.put("leftpanel.label.trash", "回收筒");
	    lang.put("leftpanel.label.mail", "E-mail");
		lang.put("leftpanel.label.stored.search", "已儲存的搜尋");
		lang.put("leftpanel.label.categories", "Categories");
		lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "樣板");
	    lang.put("leftpanel.label.my.documents", "我的文件");
	    lang.put("leftpanel.label.user.search", "使用者新進文件");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "建立資料夾");
		lang.put("tree.menu.directory.remove", "刪除");
		lang.put("tree.menu.directory.rename", "重新命名");
	    lang.put("tree.menu.directory.refresh", "重新整理");
	    lang.put("tree.menu.directory.move", "移動");
	    lang.put("tree.menu.directory.copy", "複製");
	    lang.put("tree.menu.directory.add.document", "新增文件");
	    lang.put("tree.menu.add.bookmark", "新增書籤");
		lang.put("tree.menu.set.home", "設為預設首頁書籤");
	    lang.put("tree.menu.export", "匯出到檔案");
	    lang.put("tree.status.refresh.folder", "正在更新資料夾根目錄結構");
		lang.put("tree.status.refresh.create", "正在建立資料夾");
		lang.put("tree.status.refresh.delete", "正在刪除資料夾");
		lang.put("tree.status.refresh.rename", "正在重新命名資料夾");
		lang.put("tree.status.refresh.restore", "正在重新載入資料夾");
	    lang.put("tree.status.refresh.purge", "正在清空資料夾");
	    lang.put("tree.status.refresh.get", "正在更新資料夾");
	    lang.put("tree.folder.new", "新資料夾");
	    lang.put("tree.status.refresh.add.subscription", "新增訂閱");
	    lang.put("tree.status.refresh.remove.subscription", "移除訂閱");
		lang.put("tree.status.refresh.get.root", "正在重新整理資料夾根目錄結構");
		lang.put("tree.status.refresh.get.keywords", "Refreshing keywords");
		lang.put("tree.status.refresh.get.user.home", "正在取得使用者首頁書籤");
	    lang.put("tree.status.refresh.purge.trash", "正在清除回收筒");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "重新載入");
	    lang.put("trash.menu.directory.purge", "清空");
	    lang.put("trash.menu.directory.purge.trash", "清空回收筒");
	    lang.put("trash.directory.select.label", "選擇目的資料夾");
	    
	    // General menu
	    lang.put("general.menu.file", "檔案");
	    	lang.put("general.menu.file.exit", "離開");
			lang.put("general.menu.file.purge.trash", "清空回收筒");
	    lang.put("general.menu.edit", "編輯");
			lang.put("general.menu.file.create.directory", "建立資料夾");
			lang.put("general.menu.file.download.document", "下載文件");
			lang.put("general.menu.file.download.document.pdf", "以PDF格式下載文件");
			lang.put("general.menu.file.send.link", "文件通知");
			lang.put("general.menu.file.lock", "鎖定");
			lang.put("general.menu.file.unlock", "解除鎖定");
			lang.put("general.menu.file.add.document", "新增文件");
			lang.put("general.menu.file.checkout", "簽出");
			lang.put("general.menu.file.checkin", "簽入");
			lang.put("general.menu.file.cancel.checkout", "取消簽出");
			lang.put("general.menu.file.delete", "刪除");
			lang.put("general.menu.file.refresh", "重新整理");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "File uploader");
	    lang.put("general.menu.tools", "工具");
	    	lang.put("general.menu.tools.languages", "語系");
	    	lang.put("general.menu.tools.skin", "外觀");
    			lang.put("general.menu.tools.skin.default", "預設");
    			lang.put("general.menu.tools.skin.default2", "預設2");
    			lang.put("general.menu.tools.skin.mediumfont", "中型字");
    			lang.put("general.menu.tools.skin.bigfont", "大型字");
    		lang.put("general.menu.debug.console", "除錯控制台");
    		lang.put("general.menu.administration", "顯示管理平台");
    		lang.put("general.menu.tools.preferences", "Prefererences");
    	lang.put("general.menu.bookmark", "書籤");
		lang.put("general.menu.bookmark.home", "首頁書籤");
		lang.put("general.menu.bookmark.default.home", "設為預設首頁書籤");
	    	lang.put("general.menu.bookmark.edit", "編輯書籤");
	    lang.put("general.menu.help", "說明");
		    lang.put("general.menu.bug.report", "漏洞報告");
		    lang.put("general.menu.support.request", "寫信給官網");
		    lang.put("general.menu.public.forum", "公開論壇");
			lang.put("general.menu.project.web", "OpenKM首頁");
		    lang.put("general.menu.version.changes", "版本日誌");
		    lang.put("general.menu.documentation", "官網文件頁面");
		    lang.put("general.menu.about", "關於OpenKM");
	    lang.put("general.connected", "已連接");
	    
	    // File Browser
	    lang.put("filebrowser.name", "名稱");
	    lang.put("filebrowser.date.update", "更新日期");
	    lang.put("filebrowser.size", "大小");
	    lang.put("filebrowser.path", "路徑");
	    lang.put("filebrowser.author", "作者");
	    lang.put("filebrowser.version", "版本");
	    lang.put("filebrowser.menu.checkout", "簽出");
	    lang.put("filebrowser.menu.checkin", "簽入");
	    lang.put("filebrowser.menu.delete", "刪除");
	    lang.put("filebrowser.menu.rename", "重新命名");
	    lang.put("filebrowser.menu.checkout.cancel", "取消簽出");
	    lang.put("filebrowser.menu.lock", "鎖定");
		lang.put("filebrowser.menu.unlock", "解除鎖定");
	    lang.put("filebrowser.menu.download", "下載");
	    lang.put("filebrowser.menu.move", "移動");
	    lang.put("filebrowser.menu.copy", "複製");
	    lang.put("filebrowser.menu.create.from.template", "從樣板建立");
	    lang.put("filebrowser.menu.add.property.group", "新增屬性群組");
	    lang.put("filebrowser.menu.remove.property.group", "刪除屬性群組");
		lang.put("filebrowser.menu.start.workflow", "開始流程");
	    lang.put("filebrowser.menu.add.subscription", "新增訂閱");
	    lang.put("filebrowser.menu.remove.subscription", "移除訂閱");
	    lang.put("filebrowser.menu.add.bookmark", "新增書籤");
		lang.put("filebrowser.menu.set.home", "設為預設首頁書籤");
	    lang.put("filebrowser.menu.refresh", "重新整理");
	    lang.put("filebrowser.menu.export", "匯出為ZIP檔");
	    lang.put("filebrowser.menu.play", "播放");
	    lang.put("filebrowser.menu.image.viewer", "圖示檢視");
	    lang.put("filebrowser.status.refresh.folder", "正在更新資料夾清單");
		lang.put("filebrowser.status.refresh.document", "正在更新文件清單");
		lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "正在刪除資料夾");
	    lang.put("filebrowser.status.refresh.delete.document", "正在刪除文件");
	    lang.put("filebrowser.status.refresh.checkout", "正在簽出文件");
	    lang.put("filebrowser.status.refresh.lock", "正在鎖定文件");
		lang.put("filebrowser.status.refresh.unlock", "正在解除鎖定文件");
	    lang.put("filebrowser.status.refresh.document.rename", "正在重新命名文件");
	    lang.put("filebrowser.status.refresh.folder.rename", "正在重新命名資料夾");
		lang.put("filebrowser.status.refresh.document.purge", "正在刪除文件");
	    lang.put("filebrowser.status.refresh.folder.purge", "正在刪除資料夾");
		lang.put("filebrowser.status.refresh.folder.get", "正在更新資料夾");
		lang.put("filebrowser.status.refresh.document.get", "正在更新文件");
	    lang.put("filebrowser.status.refresh.add.subscription", "正在新增訂閱");
	    lang.put("filebrowser.status.refresh.remove.subscription", "鄭在移除訂閱");
		lang.put("filebrowser.status.refresh.get.user.home", "正在取得使用者首頁書籤");
		lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
		lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "傳送");
	    lang.put("fileupload.status.sending", "正在更新檔案...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "檔案上傳無誤");
	    lang.put("fileupload.upload.status", "上傳狀態");
	    lang.put("fileupload.upload.uploaded", "上傳");
	    lang.put("fileupload.upload.completed", "上傳完成");
	    lang.put("fileupload.upload.runtime", "Runtime");
	    lang.put("fileupload.upload.remaining", "Remaining");
	    lang.put("fileupload.button.close", "關閉");
	    lang.put("fileupload.button.add.other.file", "新增其他檔案");
	    lang.put("fileupload.status.move.file", "正在移動檔案...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "正在複製檔案...");
	    lang.put("fileupload.status.copy.mail", "Coping mail...");
	    lang.put("fileupload.status.restore.file", "正在重新載入檔案...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
		lang.put("fileupload.status.move.folder", "正在移動資料夾...");
		lang.put("fileupload.status.copy.folder", "正在複製資料夾...");
		lang.put("fileupload.status.restore.folder", "正在重新載入資料夾...");
	    lang.put("fileupload.status.create.from.template", "正在從樣板建立檔案...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "新增文件");
	    lang.put("fileupload.label.update", "更新文件");
	    lang.put("fileupload.label.users.notify", "通知使用者");
		lang.put("fileupload.label.comment", "註解");
	    lang.put("fileupload.label.users.to.notify",  "被通知的使用者");
	    lang.put("fileupload.label.users",  "使用者");
	    lang.put("fileupload.label.must.select.users",  "需選擇要通知的使用者");
	    lang.put("fileupload.label.importZip", "匯入ZIP文件");
		lang.put("fileupload.label.notify.comment", "通知訊息");
	    lang.put("fileupload.label.error.importing.zip", "匯入檔案錯誤");
	    lang.put("fileupload.label.error.move.file", "移動檔案錯誤");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "複製檔案錯誤");
	    lang.put("fileupload.label.error.copy.mail", "Error coping mail");
	    lang.put("fileupload.label.error.restore.file", "重新載入檔案錯誤");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
		lang.put("fileupload.label.error.move.folder", "移動資料夾錯誤");
		lang.put("fileupload.label.error.copy.folder", "複製資料夾錯誤");
		lang.put("fileupload.label.error.restore.folder", "重新載入資料夾錯誤");
		lang.put("fileupload.label.error.create.from.template", "從樣板建立檔案錯誤");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "不允許移動到原資料夾或子資料夾");
		lang.put("fileupload.label.error.not.allowed.copy.same.folder", "不允許移動到原資料夾");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "不允許建立檔案到原資料夾");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "屬性");
	    lang.put("tab.document.notes", "日誌");
	    lang.put("tab.document.history", "歷史記錄");
	    lang.put("tab.document.status.history", "正在更新歷史記錄");
	    lang.put("tab.status.security.role", "正在更新權限角色");
		lang.put("tab.status.security.user", "正在更新權限使用者");
	    lang.put("tab.document.status.group.properties", "正在更新屬性群組");
	    lang.put("tab.document.status.set.keywords", "正在設定關鍵字");
	    lang.put("tab.document.status.set.categories", "Updating categories");
		lang.put("tab.document.status.get.version.history.size", "正在重新整理文件歷史大小");
		lang.put("tab.document.status.purge.version.history", "正在壓縮文件歷史");
	    lang.put("tab.document.status.restore.version", "正在重新載入文件版本");
	    lang.put("tab.document.security", "安全");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "屬性");
	    lang.put("tab.folder.security", "安全");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "桌面");
	    lang.put("tab.workspace.search", "搜尋");
	    lang.put("tab.workspace.dashboard", "儀表板");
	    lang.put("tab.workspace.administration", "管理");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "名稱");
	    lang.put("document.folder", "資料夾");
	    lang.put("document.size", "大小");
	    lang.put("document.created", "建立");
	    lang.put("document.lastmodified", "修改");
	    lang.put("document.mimetype", "MIME 類型");
	    lang.put("document.keywords", "關鍵字");
	    lang.put("document.by", "由");
	    lang.put("document.status", "狀態");
	    lang.put("document.status.checkout", "編輯者");
	    lang.put("document.status.locked", "鎖定者");
		lang.put("document.status.normal", "有效的");
	    lang.put("document.subscribed", "已訂閱");
	    lang.put("document.subscribed.yes", "是");
	    lang.put("document.subscribed.no", "否");
	    lang.put("document.history.size", "歷史大小");
	    lang.put("document.subscribed.users", "已訂閱使用者");
	    lang.put("document.url", "網址");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "新增日誌");
	    lang.put("document.keywords.cloud", "Keywords cloud");
	    lang.put("document.categories", "Categories");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "名稱");
	    lang.put("folder.parent", "上一層");
	    lang.put("folder.created", "建立");
	    lang.put("folder.by", "由");
	    lang.put("folder.subscribed", "已訂閱");
	    lang.put("folder.subscribed.yes", "是");
	    lang.put("folder.subscribed.no", "否");
		lang.put("folder.subscribed.users", "已訂閱使用者");
		lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "版本");
	    lang.put("version.created", "日期");
	    lang.put("version.author", "作者");
	    lang.put("version.size", "大小Size");
	    lang.put("version.purge.document", "壓縮歷史記錄");
	    lang.put("version.comment", "註解");
	    
	    // Security
	    lang.put("security.label", "安全");
	    lang.put("security.group.name", "群組");
	    lang.put("security.group.permission.read", "讀");
	    lang.put("security.group.permission.write", "寫");
	    lang.put("security.user.name", "使用者");
		lang.put("security.user.permission.read", "讀");
		lang.put("security.user.permission.write", "寫");
	    lang.put("security.users", "使用者");
	    lang.put("security.groups", "群組");
	    lang.put("security.recursive", "Recursive permission changes");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    
	    // Error
	    lang.put("error.label", "系統發生一項錯誤");
	    lang.put("error.invocation", "當連線到主機時發生錯誤");
	    
	    // About
	    lang.put("about.label", "關於OpenKM");
	    lang.put("about.loading", "載入中 ...");
	    
	    // Logout
	    lang.put("logout.label", "離開");
	    lang.put("logout.closed", "OpenKM已經關閉完成");
	    lang.put("logout.logout", "OpenKM正在關閉中，請稍候");
	    
	    // Confirm
	    lang.put("confirm.label", "確認");
	    lang.put("confirm.delete.folder", "確定要刪除資料夾?");
		lang.put("confirm.delete.document", "確定要刪除文件?");
	    lang.put("confirm.delete.trash", "確定要清除回收筒?");
		lang.put("confirm.purge.folder", "確定要刪除資料夾??");
		lang.put("confirm.purge.document", "確定要刪除文件?");
		lang.put("confirm.delete.propety.group", "確定要刪除屬性群組?");
		lang.put("confirm.purge.version.history.document", "確定要刪除歷史文件?");
	    lang.put("confirm.purge.restore.document", "確定要重新載入這個文件版本?");
		lang.put("confirm.set.default.home", "確定要設定預設首頁書籤?");
	    lang.put("confirm.delete.saved.search", "確定要刪除已儲存的搜尋條件?");
	    lang.put("confirm.delete.user.news", "確定要刪除使用者新進文件?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
	    lang.put("confirm.get.pooled.workflow.task","¿ Do you want to assign this task to you ?");
	    
	    // Search inputs
	    lang.put("search.context", "範圍");
	    lang.put("search.content", "內容");
	    lang.put("search.name", "名稱");
	    lang.put("search.keywords", "關鍵字");
	    lang.put("search.folder", "Folder");
	    lang.put("search.category", "Category");
	    lang.put("search.results", "結果");
	    lang.put("search.to", "至");
	    lang.put("search.page.results", "每頁顯示結果");
	    lang.put("search.add.property.group", "新增屬性群組");
	    lang.put("search.mimetype", "Mime 類型");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "進階搜尋");
	    lang.put("search.user", "使用者");
	    lang.put("search.date.and", "至");
	    lang.put("search.date.range", "日期區間");
	    lang.put("search.save.as.news", "儲存成使用者新進文件");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    lang.put("search.category.filter", "Filter by category");
	    
	    // Search results
	    lang.put("search.result.name", "名稱");
	    lang.put("search.result.score", "相關性");
	    lang.put("search.result.size", "大小");
	    lang.put("search.result.date.update", "更新日期");
	    lang.put("search.result.author", "作者");
	    lang.put("search.result.version", "版本");
	    lang.put("search.result.path", "路徑");
	    lang.put("search.result.menu.download", "下載");
	    lang.put("search.result.menu.go.folder", "直接到所屬資料夾");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "正在更新搜尋條件");
	    lang.put("search.result.status.runsearch", "正在更新已儲存的搜尋條件");
	    
	    // Search saved
	    lang.put("search.saved.run", "執行");
	    lang.put("search.saved.delete", "刪除");
	    lang.put("search.saved.status.getsearchs", "重新整理已儲存的搜尋條件");
		lang.put("search.saved.status.savesearch", "正在更新已儲存的搜尋條件");
		lang.put("search.saved.status.deletesearch", "正在刪除已儲存的搜尋條件");
	    lang.put("search.saved.status.getusernews", "正在重新整理使用者新進文件");
	    
	    // Button
	    lang.put("button.close", "關閉");
	    lang.put("button.logout", "登出");
	    lang.put("button.update", "更新");
	    lang.put("button.cancel", "取消");
	    lang.put("button.accept", "接受");
	    lang.put("button.restore", "重新載入");
	    lang.put("button.move", "移動");
	    lang.put("button.change", "變更");
	    lang.put("button.search", "搜尋");
	    lang.put("button.save.search", "儲存搜尋條件");
	    lang.put("button.view", "檢視");
	    lang.put("button.clean", "清除");
	    lang.put("button.add", "新增");
	    lang.put("button.delete", "刪除");
	    lang.put("button.copy", "複製");
	    lang.put("button.create", "建立");
	    lang.put("button.show", "顯示");
	    lang.put("button.memory", "更新");
	    lang.put("button.copy.clipboard", "複製到剪貼簿");	
	    lang.put("button.start", "開始");
	    lang.put("button.select", "Select");
	    
	    // Group
	    lang.put("group.label", "新增屬性群組");
	    lang.put("group.group", "群組");
	    lang.put("group.property.group", "屬性");
	    
	    // Bookmark
	    lang.put("bookmark.label", "新增書籤");
	    lang.put("bookmark.name", "名稱");
	    lang.put("bookmark.edit.label", "編輯書籤");
	    lang.put("bookmark.path", "路徑");
	    lang.put("bookmark.type", "類型");
	    lang.put("bookmark.type.document", "文件");
	    lang.put("bookmark.type.folder", "資料夾");
	    
	    // Notify
		lang.put("notify.label", "文件通知");
	    
	    // Status
	    lang.put("status.document.copied", "文件已被複製");
	    lang.put("status.document.cut", "文件已被剪下");
	    lang.put("status.folder.copied", "資料夾已被複製");
	    lang.put("status.folder.cut", "資料夾已被剪下");
	    lang.put("status.keep.alive.error", "偵測到與主機連線中斷");
	    lang.put("status.debug.enabled", "啟動除錯");
	    lang.put("status.debug.disabled", "取消除錯");
	    lang.put("status.network.error.detected", "Network error detected");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "星期日");
		lang.put("calendar.day.monday", "星期一");
		lang.put("calendar.day.tuesday", "星期二");
		lang.put("calendar.day.wednesday", "星期三");
		lang.put("calendar.day.thursday", "星期四");
		lang.put("calendar.day.friday", "星期五");
		lang.put("calendar.day.saturday", "星期六");
	    lang.put("calendar.month.january", "一月");
		lang.put("calendar.month.february", "二月");
		lang.put("calendar.month.march", "三月");
		lang.put("calendar.month.april", "四月");
		lang.put("calendar.month.may", "五月");
		lang.put("calendar.month.june", "六月");
		lang.put("calendar.month.july", "七月");
		lang.put("calendar.month.august", "八月");
		lang.put("calendar.month.september", "九月");
		lang.put("calendar.month.october", "十月");
		lang.put("calendar.month.november", "十一月");
		lang.put("calendar.month.december", "十二月");
	    
	    // Media player
	    lang.put("media.player.label", "媒體播放");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "影像檢視");
	    lang.put("image.viewer.zoom.in", "縮小");
	    lang.put("image.viewer.zoom.out", "放大");
	    
	    // Debug console
	    lang.put("debug.console.label", "除錯控制台");
	    lang.put("debug.enable.disable", "CTRL+Z以啟動或取消除錯模式");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "一般");
	    lang.put("dashboard.tab.news", "新進文件");
	    lang.put("dashboard.tab.user", "使用者");
	    lang.put("dashboard.tab.workflow", "簽核");
	    lang.put("dashboard.tab.mail", "Mail");
	    lang.put("dashboard.tab.keymap", "Keyword map");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "新進");
	    lang.put("dashboard.user.locked.documents", "鎖定文件");
	    lang.put("dashboard.user.checkout.documents", "簽出文件");
	    lang.put("dashboard.user.last.modified.documents", "最新修改文件");
	    lang.put("dashboard.user.last.downloaded.documents", "最新下載文件");
	    lang.put("dashboard.user.subscribed.documents", "已訂閱文件");
	    lang.put("dashboard.user.subscribed.folders", "已訂閱資料夾");
	    lang.put("dashboard.user.last.uploaded.documents", "最新上傳文件");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "最近一週被檢視多次的文件");
		lang.put("dashboard.general.last.month.top.downloaded.documents", "最近一個月被檢視多次的文件");
	    lang.put("dashboard.general.last.week.top.modified.documents", "最近一週被修改多次的文件");
		lang.put("dashboard.general.last.month.top.modified.documents", "最近一個月被修改多次的文件");
		lang.put("dashboard.general.last.uploaded.documents", "最新上傳文件");
	    lang.put("dashboard.workflow.pending.tasks", "Pending作業");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "Unassigned pending tasks");
	    lang.put("dashboard.workflow.task", "作業");
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "名稱");
	    lang.put("dashboard.workflow.task.created", "建立日期");
	    lang.put("dashboard.workflow.task.start", "開始日期");
	    lang.put("dashboard.workflow.task.duedate", "到期日");
	    lang.put("dashboard.workflow.task.end", "結束日期");
	    lang.put("dashboard.workflow.task.description", "說明");
	    lang.put("dashboard.workflow.task.process.instance", "Process instance");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "版本");
	    lang.put("dashboard.workflow.task.process.name", "名稱");
		lang.put("dashboard.workflow.task.process.description", "說明");
		lang.put("dashboard.workflow.task.process.data", "日期");
		lang.put("dashboard.workflow.comments", "Comments");
	    lang.put("dashboard.workflow.task.process.forms", "Form");
	    lang.put("dashboard.workflow.add.comment","Add comment");
	    lang.put("dashboard.workflow.task.process.definition", "流程定義");
	    lang.put("dashboard.workflow.task.process.path", "路徑");
	    lang.put("dashboard.refreshing", "重新整理");
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
		lang.put("workflow.label", "開始流程");
		
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
		lang.put("OKM-" + ErrorCode.ORIGIN_OKMDocumentService + ErrorCode.CAUSE_AccessDenied, "文件存取失敗");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "找不到文件");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "文件已經存在");
		lang.put("OKM-" + ErrorCode.ORIGIN_OKMDocumentService + ErrorCode.CAUSE_Lock, "文件鎖定失敗");
		lang.put("OKM-" + ErrorCode.ORIGIN_OKMDocumentService + ErrorCode.CAUSE_UnLock, "文件需要解除鎖定");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "找不到文件路徑");

		lang.put("OKM-" + ErrorCode.ORIGIN_OKMFolderService + ErrorCode.CAUSE_AccessDenied, "資料夾存取失敗");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "找不到資料夾");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "資料夾已經存在");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "找不到資料夾路徑");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "項目存取失敗");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "找不到項目");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Repository internal error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "找不到文件");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Repository internal error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "檔案類型不支援");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "文件已經存在");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "超過可接受文件大小");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Session closed");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "執行查詢時發生錯誤");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "欲儲存的查詢條件名稱必須唯一");

		lang.put("OKM-" + ErrorCode.ORIGIN_OKMBookmarkService + ErrorCode.CAUSE_ItemExists, "書籤名稱必須唯一");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Session lost");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error in browser configuration");
	  }
}
