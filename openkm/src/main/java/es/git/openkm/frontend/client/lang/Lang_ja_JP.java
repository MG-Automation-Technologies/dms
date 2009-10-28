/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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
 * Japanese (Japan)
 * 
 * @author Sunatomo
 */
public class Lang_ja_JP {
	
	public final static HashMap<String, String> lang;
		static {
		lang = new HashMap<String, String>();
		
		// General configuration
	    lang.put("general.date.pattern", "dd/MM/yyyy hh:mm:ss");
	    lang.put("general.hour.pattern", "hh:mm:ss");
		
		// Startup
		lang.put("startup.openkm", "OpenKM起動中");
		lang.put("startup.starting.loading", "OpenKM読込中");
		lang.put("startup.taxonomy", "階層ルート取得中");
		lang.put("startup.template", "テンプレートルート取得中");
		lang.put("startup.personal", "個別ルートノードの生成中");
		lang.put("startup.mail", "Getting e-mail root node");
		lang.put("startup.trash", "ごみ箱のルート取得中");
		lang.put("startup.user.home", "ユーザホームの取得中");
		lang.put("startup.bookmarks", "ブックマークの取得中");
		lang.put("startup.loading.taxonomy", "階層読込中");
		lang.put("startup.loading.taxonomy.getting.folders", "階層読込中 - フォルダ取得");
		lang.put("startup.loading.taxonomy.eval.params", "階層読込中 - ブラウザパラメータ計算");
		lang.put("startup.loading.taxonomy.open.path", "階層読込中 - パスを開く");
		lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "階層読込中 - フォルダ取得");
		lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "階層読込中 - 文書取得");
		lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "Loading taxonomy - getting mails");
		lang.put("startup.loading.personal", "Loading personal");
		lang.put("startup.loading.mail", "Loading e-mails");
		lang.put("startup.loading.templates", "テンプレートの読込中");
		lang.put("startup.loading.trash", "ごみ箱の読込中");
		lang.put("startup.loading.history.search", "検索履歴の読込中");
		lang.put("startup.loading.user.values", "ユーザ設定の読込中");
		lang.put("startup.loading.property.group.translations", "Loading property group translations");
		lang.put("startup.keep.alive", "Loading keep alive");
		
		// Update notification
		lang.put("openkm.update.available", "OpenKM のアップデートがあります");
		
		// Left Panel
		lang.put("leftpanel.label.taxonomy", "階層");
		lang.put("leftpanel.label.trash", "ごみ箱");
		lang.put("leftpanel.label.mail", "E-mail");
		lang.put("leftpanel.label.stored.search", "保存検索条件");
		lang.put("leftpanel.label.templates", "テンプレート");
		lang.put("leftpanel.label.my.documents", "マイドキュメント");
		lang.put("leftpanel.label.user.search", "User news");
		lang.put("leftpanel.label.all.repository", "All repository");
		
		// Tree
		lang.put("tree.menu.directory.create", "新規フォルダ");
		lang.put("tree.menu.directory.remove", "削除");
		lang.put("tree.menu.directory.rename", "名前の変更");
		lang.put("tree.menu.directory.refresh", "最新の情報に更新");
		lang.put("tree.menu.directory.move", "移動");
		lang.put("tree.menu.directory.copy", "コピー");
		lang.put("tree.menu.directory.add.document", "文書の追加");
		lang.put("tree.menu.add.bookmark", "ブックマークの追加");
		lang.put("tree.menu.set.home", "ホームに設定");
		lang.put("tree.menu.export", "エクスポート");
		lang.put("tree.status.refresh.folder", "フォルダツリーの更新中...");
		lang.put("tree.status.refresh.create", "フォルダの作成中...");
		lang.put("tree.status.refresh.delete", "フォルダの削除中...");
		lang.put("tree.status.refresh.rename", "フォルダの名前変更中...");
		lang.put("tree.status.refresh.restore", "フォルダの復元中...");
		lang.put("tree.status.refresh.purge", "フォルダの消去中...");
		lang.put("tree.status.refresh.get", "フォルダの更新中...");
		lang.put("tree.folder.new", "新規フォルダ");
		lang.put("tree.status.refresh.add.subscription", "署名の追加中...");
		lang.put("tree.status.refresh.remove.subscription", "署名の削除中...");
		lang.put("tree.status.refresh.get.root", "ルートフォルダの更新中...");
		lang.put("tree.status.refresh.get.user.home", "ユーザホームの取得中...");
		lang.put("tree.status.refresh.purge.trash", "ごみ箱の消去中...");
		
		// Trash
		lang.put("trash.menu.directory.restore", "ディレクトリの復元");
		lang.put("trash.menu.directory.purge", "ディレクトリを空にする");
		lang.put("trash.menu.directory.purge.trash", "ごみ箱を空にする");
		lang.put("trash.directory.select.label", "相手先のフォルダを選択してください");
		
		// General menu
		lang.put("general.menu.file", "ファイル");
		lang.put("general.menu.file.exit", "閉じる");
		lang.put("general.menu.file.purge.trash", "ごみ箱を空にする");
		lang.put("general.menu.edit", "編集");
		lang.put("general.menu.file.create.directory", "新規フォルダ");
		lang.put("general.menu.file.download.document", "文書のダウンロード");
		lang.put("general.menu.file.download.document.pdf", "文書をPDFしダウンロード");
		lang.put("general.menu.file.send.link", "文書へのリンクを送る");
		lang.put("general.menu.file.lock", "ロックする");
		lang.put("general.menu.file.unlock", "ロック解除");
		lang.put("general.menu.file.add.document", "文書の追加");
		lang.put("general.menu.file.checkout", "チェックアウト");
		lang.put("general.menu.file.checkin", "チェックイン");
		lang.put("general.menu.file.cancel.checkout", "チェックアウトのキャンセル");
		lang.put("general.menu.file.delete", "削除");
		lang.put("general.menu.file.refresh", "更新");
		lang.put("general.menu.tools", "ツール");
		lang.put("general.menu.tools.languages", "言語設定");
		lang.put("general.menu.tools.skin", "スキン");
		lang.put("general.menu.tools.skin.default", "By default");
		lang.put("general.menu.tools.skin.default2", "By default 2");
		lang.put("general.menu.tools.skin.mediumfont", "Medium font");
		lang.put("general.menu.tools.skin.bigfont", "Big font");
		lang.put("general.menu.debug.console", "デバッグコンソール");
		lang.put("general.menu.administration", "管理者機能");
		lang.put("general.menu.tools.preferences", "Prefererences");
			lang.put("general.menu.tools.user.preferences", "User configuration");
		lang.put("general.menu.bookmark", "ブックマーク");
		lang.put("general.menu.bookmark.home", "ホーム");
		lang.put("general.menu.bookmark.default.home", "ホームに設定");
		lang.put("general.menu.bookmark.edit", "ブックマークの編集");
		lang.put("general.menu.help", "ヘルプ");
		lang.put("general.menu.bug.report", "バグの報告");
		lang.put("general.menu.support.request", "サポート要請");
		lang.put("general.menu.public.forum", "公式フォーラム");
		lang.put("general.menu.project.web", "プロジェクトWeb");
		lang.put("general.menu.version.changes", "バージョン");
		lang.put("general.menu.documentation", "文書");
		lang.put("general.menu.about", "OpenKMについて");
		lang.put("general.connected", "接続ユーザ");
		
		// File Browser
		lang.put("filebrowser.name", "名前");
		lang.put("filebrowser.date.update", "更新日時");
		lang.put("filebrowser.size", "サイズ");
		lang.put("filebrowser.path", "パス");
		lang.put("filebrowser.author", "作成者");
		lang.put("filebrowser.version", "バージョン");
		lang.put("filebrowser.menu.checkout", "チェックアウト");
		lang.put("filebrowser.menu.checkin", "チェックイン");
		lang.put("filebrowser.menu.delete", "削除");
		lang.put("filebrowser.menu.rename", "名前の変更");
		lang.put("filebrowser.menu.checkout.cancel", "チェックアウトのキャンセル");
		lang.put("filebrowser.menu.lock", "ロック");
		lang.put("filebrowser.menu.unlock", "ロック解除");
		lang.put("filebrowser.menu.download", "ダウンロード");
		lang.put("filebrowser.menu.move", "移動");
		lang.put("filebrowser.menu.copy", "複写");
		lang.put("filebrowser.menu.create.from.template", "テンプレートから作成");
		lang.put("filebrowser.menu.add.property.group", "グループ設定の追加");
		lang.put("filebrowser.menu.remove.property.group", "グループ設定の除去");
		lang.put("filebrowser.menu.start.workflow", "Start workflow");
		lang.put("filebrowser.menu.add.subscription", "署名を追加");
		lang.put("filebrowser.menu.remove.subscription", "署名を除去");
		lang.put("filebrowser.menu.add.bookmark", "ブックマークの追加");
		lang.put("filebrowser.menu.set.home", "ホームに設定");
		lang.put("filebrowser.menu.refresh", "更新");
		lang.put("filebrowser.menu.export", "ファイルへのエクスポート");
		lang.put("filebrowser.menu.play", "Play");
		lang.put("filebrowser.menu.image.viewer", "イメージビュアー");
		lang.put("filebrowser.status.refresh.folder", "フォルダリストの更新中...");
		lang.put("filebrowser.status.refresh.document", "文書リストの更新中...");
		lang.put("filebrowser.status.refresh.mail", "Updating mail list");
		lang.put("filebrowser.status.refresh.delete.folder", "フォルダの削除中...");
		lang.put("filebrowser.status.refresh.delete.document", "文書の削除中...");
		lang.put("filebrowser.status.refresh.checkout", "文書のチェックアウト中...");
		lang.put("filebrowser.status.refresh.lock", "文書のロック中...");
		lang.put("filebrowser.status.refresh.unlock", "文書のロック解除中...");
		lang.put("filebrowser.status.refresh.document.rename", "文書の名前変更中...");
		lang.put("filebrowser.status.refresh.folder.rename", "フォルダの名前変更中...");
		lang.put("filebrowser.status.refresh.document.purge", "文書を空にしています...");
		lang.put("filebrowser.status.refresh.folder.purge", "フォルダを空にしています...");
		lang.put("filebrowser.status.refresh.folder.get", "フォルダの更新中...");
		lang.put("filebrowser.status.refresh.document.get", "文書のの更新中...");
		lang.put("filebrowser.status.refresh.add.subscription", "書名の追加中...");
		lang.put("filebrowser.status.refresh.remove.subscription", "署名の削除中...");
		lang.put("filebrowser.status.refresh.get.user.home", "ユーザホームの取得中...");
		lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
		lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
		
		// File Upload
		lang.put("fileupload.send", "送信");
		lang.put("fileupload.status.sending", "ファイルのアップロード中...");
		lang.put("fileupload.status.indexing", "Indexing file...");
		lang.put("fileupload.status.ok", "ファイルのアップロードが成功しました");
		lang.put("fileupload.upload.status", "アップロードの状態");
		lang.put("fileupload.upload.uploaded", "アップロードした");
		lang.put("fileupload.upload.completed", "アップロード成功");
		lang.put("fileupload.upload.runtime", "処理時間");
		lang.put("fileupload.upload.remaining", "Remaining");
		lang.put("fileupload.button.close", "閉じる");
		lang.put("fileupload.button.add.other.file", "他のファイルを追加");
		lang.put("fileupload.status.move.file", "ファイル移動中...");
		lang.put("fileupload.status.move.mail", "Moving mail...");
		lang.put("fileupload.status.copy.file", "ファイル複写中...");
		lang.put("fileupload.status.copy.mail", "Coping mail...");
		lang.put("fileupload.status.restore.file", "ファイル復元中...");
		lang.put("fileupload.status.restore.mail", "Restoring mail...");
		lang.put("fileupload.status.move.folder", "フォルダ移動中...");
		lang.put("fileupload.status.copy.folder", "フォルダ複写中...");
		lang.put("fileupload.status.restore.folder", "フォルダ復元中...");
		lang.put("fileupload.status.create.from.template", "テンプレートからファイル作成中...");
		lang.put("fileupload.status.of", "of");
		lang.put("fileupload.label.insert", "文書の追加");
		lang.put("fileupload.label.update", "文書の更新");
		lang.put("fileupload.label.users.notify", "利用者への通知");
		lang.put("fileupload.label.comment", "コメント");
		lang.put("fileupload.label.users.to.notify",  "通知するユーザ");
		lang.put("fileupload.label.users",  "利用者");
		lang.put("fileupload.label.must.select.users",  "通知するユーザを選択して下さい");
		lang.put("fileupload.label.importZip", "Import Documents from ZIP");
		lang.put("fileupload.label.notify.comment", "通知メッセージ");
		lang.put("fileupload.label.error.importing.zip", "圧縮文書のインポートに失敗");
		lang.put("fileupload.label.error.move.file", "ファイルの移動に失敗");
		lang.put("fileupload.label.error.move.mail", "Error moving mail");
		lang.put("fileupload.label.error.copy.file", "ファイルの複写に失敗");
		lang.put("fileupload.label.error.copy.mail", "Error coping mail");
		lang.put("fileupload.label.error.restore.file", "ファイルの復元に失敗");
		lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
		lang.put("fileupload.label.error.move.folder", "フォルダの移動に失敗");
		lang.put("fileupload.label.error.copy.folder", "フォルダの複写に失敗");
		lang.put("fileupload.label.error.restore.folder", "フォルダの復元に失敗");
		lang.put("fileupload.label.error.create.from.template", "テンプレートからのファイル作成に失敗");
		lang.put("fileupload.label.error.not.allowed.move.folder.child", "このフォルダもしくは子フォルダを移動は許可されていません");
		lang.put("fileupload.label.error.not.allowed.copy.same.folder", "送りと受け側のフォルダが同じです");
		lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "テンプレートと同じフォルダ上にファイルは作れません");
		
		// Tab properties
		lang.put("tab.document.properties", "プロパティ");
		lang.put("tab.document.notes", "Notes");
		lang.put("tab.document.history", "履歴");
		lang.put("tab.document.status.history", "履歴の更新中...");
		lang.put("tab.status.security.role", "グループ権限の更新中...");
		lang.put("tab.status.security.user", "ユーザ権限の更新中...");
		lang.put("tab.document.status.group.properties", "グループ設定の更新中...");
		lang.put("tab.document.status.set.keywords", "キーワード設定の更新中...");
		lang.put("tab.document.status.get.version.history.size", "文書履歴の更新中...");
		lang.put("tab.document.status.purge.version.history", "文書の履歴を削除中...");
		lang.put("tab.document.status.restore.version", "文書のバージョンを復元中...");
		lang.put("tab.document.security", "セキュリティ");
		lang.put("tab.document.preview", "Preview");
		lang.put("tab.folder.properties", "プロパティ");
		lang.put("tab.folder.security", "セキュリティ");
		
		// Workspace tabs
		lang.put("tab.workspace.desktop", "デスクトップ");
		lang.put("tab.workspace.search", "検索");
		lang.put("tab.workspace.dashboard", "DashBoard");
		lang.put("tab.workspace.administration", "Administration");
		
		//  Document
		lang.put("document.uuid", "UUID");
		lang.put("document.name", "名前");
		lang.put("document.folder", "フォルダ");
		lang.put("document.size", "サイズ");
		lang.put("document.created", "作成日時");
		lang.put("document.lastmodified", "更新日時");
		lang.put("document.mimetype", "MIME タイプ");
		lang.put("document.keywords", "キーワード");
		lang.put("document.by", "by");
		lang.put("document.status", "ステータス");
		lang.put("document.status.checkout", "作成者");
		lang.put("document.status.locked", "ロック処理");
		lang.put("document.status.normal", "Available");
		lang.put("document.subscribed", "認証済み");
		lang.put("document.subscribed.yes", "はい");
		lang.put("document.subscribed.no", "いいえ");
		lang.put("document.history.size", "合計サイズ");
		lang.put("document.subscribed.users", "認証ユーザ");
		lang.put("document.url", "URL");
		lang.put("document.webdav", "WebDAV");
		lang.put("document.add.note", "Add note");
		lang.put("document.keywords.cloud", "Keywords cloud");
		
		// Folder
		lang.put("folder.uuid", "UUID");
		lang.put("folder.name", "名前");
		lang.put("folder.parent", "親フォルダ");
		lang.put("folder.created", "作成者");
		lang.put("folder.by", "");
		lang.put("folder.subscribed", "認証");
		lang.put("folder.subscribed.yes", "はい");
		lang.put("folder.subscribed.no", "いいえ");
		lang.put("folder.subscribed.users", "認証ユーザ");
		lang.put("folder.webdav", "WebDAV");
		
		// Version
		lang.put("version.name", "バージョン");
		lang.put("version.created", "作成日時");
		lang.put("version.author", "作成者");
		lang.put("version.size", "サイズ");
		lang.put("version.purge.document", "履歴の消去");
		lang.put("version.comment", "コメント");
		
		// Security
		lang.put("security.label", "セキュリティ");
		lang.put("security.group.name", "グループ");
		lang.put("security.group.permission.read", "読込");
		lang.put("security.group.permission.write", "書込");
		lang.put("security.user.name", "ユーザ");
		lang.put("security.user.permission.read", "読込");
		lang.put("security.user.permission.write", "書込");
		lang.put("security.users", "ユーザ");
		lang.put("security.groups", "グループ");
		lang.put("security.recursive", "アクセス許可を再帰的に子フォルダ適用する");
		
		// Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
		
		// Error
		lang.put("error.label", "システムエラーが発生しました");
		lang.put("error.invocation", "サーバとの通信でエラーが発生しました");
		
		// About
		lang.put("about.label", "OpenKM について");
		lang.put("about.loading", "読込中 ...");
		
		// Logout
		lang.put("logout.label", "閉じる");
		lang.put("logout.closed", "OpenKM は正常に終了しました");
		lang.put("logout.logout", "OpenKM はログアウト中です。お待ち下さい");
		
		// Confirm
		lang.put("confirm.label", "確認");
		lang.put("confirm.delete.folder", "本当にフォルダを削除しますか？");
		lang.put("confirm.delete.document", "本当に文書を削除しますか？");
		lang.put("confirm.delete.trash", "本当にごみ箱を空にしますか？");
		lang.put("confirm.purge.folder", "本当にフォルダを削除しますか？");
		lang.put("confirm.purge.document", "本当に文書を空にしますか？");
		lang.put("confirm.delete.propety.group", "本当にグループプロパティを削除しますか？");
		lang.put("confirm.purge.version.history.document", "本当に文書履歴を削除しますか？");
		lang.put("confirm.purge.restore.document", "本当にこの文書バージョンを元に戻しますか？");
		lang.put("confirm.set.default.home", "本当にデフォルトのホームにしますか？");
		lang.put("confirm.delete.saved.search", "¿ Do you really want to delete saved search ?");
	    lang.put("confirm.delete.user.news", "¿ Do you really want to delete user news ?");
	    lang.put("confirm.delete.mail", "¿ Do you really want to delete mail ?");
		
		// Search inputs
		lang.put("search.context", "種類");
		lang.put("search.content", "ファイル名");
		lang.put("search.name", "名前");
		lang.put("search.keywords", "キーワード");
		lang.put("search.folder", "Folder");
		lang.put("search.results", "結果");
		lang.put("search.to", "to");
		lang.put("search.page.results", "ページ当り表示数");
		lang.put("search.add.property.group", "グループのプロパティ追加");
		lang.put("search.mimetype", "Mime タイプ");
		lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
		lang.put("search.advanced", "拡張検索");
		lang.put("search.user", "ユーザ");
		lang.put("search.date.and", "＆");
		lang.put("search.date.range", "年月日");
		lang.put("search.save.as.news", "Save as user news");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
		
		// Search results
		lang.put("search.result.name", "名前");
		lang.put("search.result.score", "検索スコア");
		lang.put("search.result.size", "サイズ");
		lang.put("search.result.date.update", "更新年月日");
		lang.put("search.result.author", "作者");
		lang.put("search.result.version", "バージョン");
		lang.put("search.result.path", "パス");
		lang.put("search.result.menu.download", "ダウンロード");
		lang.put("search.result.menu.go.folder", "フォルダに移動");
		lang.put("search.result.menu.go.document", "Go to document");
		lang.put("search.result.status.findPaginated", "検索を更新中...");
		lang.put("search.result.status.runsearch", "保存検索条件の取得中...");
		
		// Search saved
		lang.put("search.saved.run", "実行");
		lang.put("search.saved.delete", "削除");
		lang.put("search.saved.status.getsearchs", "検索条件の更新中...");
		lang.put("search.saved.status.savesearch", "検索条件の保存中...");
		lang.put("search.saved.status.deletesearch", "検索条件の削除中...");
		lang.put("search.saved.status.getusernews", "Refreshing user news");
		
		// Button
		lang.put("button.close", "閉じる");
		lang.put("button.logout", "ログアウト");
		lang.put("button.update", "更新");
		lang.put("button.cancel", "キャンセル");
		lang.put("button.accept", "はい");
		lang.put("button.restore", "復元");
		lang.put("button.move", "移動");
		lang.put("button.change", "変更");
		lang.put("button.search", "検索");
		lang.put("button.save.search", "検索条件の保存");
		lang.put("button.view", "開く");
		lang.put("button.clean", "クリア");
		lang.put("button.add", "追加");
		lang.put("button.delete", "削除");
		lang.put("button.copy", "複写");
		lang.put("button.create", "作成");
		lang.put("button.show", "Show");
		lang.put("button.memory", "Update");
		lang.put("button.copy.clipboard", "Copy to clipboard");
		lang.put("button.start", "Start");
		lang.put("button.select", "Select");
		
		// Group
		lang.put("group.label", "Add property group");
		lang.put("group.group", "グループ");
		lang.put("group.property.group", "プロパティ");
		
		// Bookmark
		lang.put("bookmark.label", "ブックマークの追加");
		lang.put("bookmark.name", "名前");
		lang.put("bookmark.edit.label", "ブックマークの編集");
		lang.put("bookmark.path", "パス");
		lang.put("bookmark.type", "T");
		lang.put("bookmark.type.document", "文書");
		lang.put("bookmark.type.folder", "フォルダ");
		
		// Notify
		lang.put("notify.label", "文書リンクの送信中");
		
		// Status
		lang.put("status.document.copied", "Document marked to copy");
		lang.put("status.document.cut", "Document market to cut");
		lang.put("status.folder.copied", "Folder marked to copy");
		lang.put("status.folder.cut", "Folder marked to cut");
		lang.put("status.keep.alive.error", "Detected lost connection to server (keep alive)");
		lang.put("status.debug.enabled", "デバッグ有効化");
		lang.put("status.debug.disabled", "デバッグ無効化");
		lang.put("status.network.error.detected", "Network error detected");
		
		// Calendar
		lang.put("calendar.day.sunday", "日曜日");
		lang.put("calendar.day.monday", "月曜日");
		lang.put("calendar.day.tuesday", "火曜日");
		lang.put("calendar.day.wednesday", "水曜日");
		lang.put("calendar.day.thursday", "木曜日");
		lang.put("calendar.day.friday", "金曜日");
		lang.put("calendar.day.saturday", "土曜日");
		lang.put("calendar.month.january", "1月");
		lang.put("calendar.month.february", "2月");
		lang.put("calendar.month.march", "3月");
		lang.put("calendar.month.april", "4月");
		lang.put("calendar.month.may", "5月");
		lang.put("calendar.month.june", "6月");
		lang.put("calendar.month.july", "7月");
		lang.put("calendar.month.august", "8月");
		lang.put("calendar.month.september", "9月");
		lang.put("calendar.month.october", "10月");
		lang.put("calendar.month.november", "11月");
		lang.put("calendar.month.december", "12月");
		
		// Media player
		lang.put("media.player.label", "メディアプレーヤ");
		
		// Image viewer
		lang.put("image.viewer.label", "イメージビューワ");
		lang.put("image.viewer.zoom.in", "拡大");
		lang.put("image.viewer.zoom.out", "縮小");
		
		// Debug console
		lang.put("debug.console.label", "デバッグコンソール");
		lang.put("debug.enable.disable", "CTRL+Z でデバッグのOn/Offが切り替えできます");

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
	    lang.put("user.preferences.name", "Name");
	    lang.put("user.preferences.password", "Password");
	    lang.put("user.preferences.imap.host", "IMAP server");
	    lang.put("user.preferences.imap.user", "IMAP user name");
	    lang.put("user.preferences.imap.user.password", "IMAP user password");
	    lang.put("user.preferences.imap.folder", "IMAP folder");
	    lang.put("user.preferences.password.error", "Error: passwords are diferents");
	    
		// Errors
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "文書へのアクセスが拒否されました");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "文書がありません");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "文書が既に存在します");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "文書のロックが拒否されました");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "文書のロック解除が拒否されました");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "リポジトリの内部エラーです");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "アプリケーションの内部エラーです");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "文書のパスが見つかりません");
		
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "フォルダへのアクセスが拒否されました");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "フォルダがありません");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "フォルダが既に存在します");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "リポジトリの内部エラーです");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "リポジトリの内部エラーです");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "フォルダのパスが見つかりません");

		lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "アイテムへのアクセスが拒否されました");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "アイテムが見つかりません");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "リポジトリ内部エラーです");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "リポジトリ内部エラーです");
		
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "文書が見つかりません");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "リポジトリ内部エラーです");
		
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "未サポートのファイル形式です");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "文書が既に存在します");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "文書のサイズが既定を超えました");
		
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "セッションが閉じました");
		
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Generic error executing query");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "検索条件名は重複しない必要があります");
		
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "ブックマーク名は重複しない必要があります");
		
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "セッションが失われました");
		
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "リポジトリの内部エラーです");
		
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "リポジトリの内部エラーです");
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error on I/O");
		
		lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "リポジトリの内部エラーです");
	  }
}
