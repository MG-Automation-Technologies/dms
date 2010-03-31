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
 * Chinese (China) 
 * email: mingjie.mj@gmail.com 
 * @author wmj2003 
 */
public class Lang_zh_CN {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "yyyy-MM-dd hh:mm:ss");
	    lang.put("general.day.pattern", "yyyy-MM-dd");
	    lang.put("general.hour.pattern", "hh:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "加载OpenKM");
	    lang.put("startup.starting.loading", "开始加载OpenKM");
	    lang.put("startup.taxonomy", "正在获得分类根节点");
	    lang.put("startup.template", "正在获得模板根节点");
	    lang.put("startup.personal", "正在获得个人根节点");
	    lang.put("startup.mail", "获取 e-mail根节点");
        lang.put("startup.trash", "正在获得垃回收站节点");
	    lang.put("startup.user.home", "正在获得用户根目录节点");
	    lang.put("startup.bookmarks", "正在获得书签");
	    lang.put("startup.loading.taxonomy", "正在加载分类");
	    lang.put("startup.loading.taxonomy.getting.folders", "正在加载分类- 正在获得文件夹");
        lang.put("startup.loading.taxonomy.eval.params", "正在加载分类- eval 浏览器参数");
        lang.put("startup.loading.taxonomy.open.path", "正在加载分类- 打开路径");
        lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "正在加载分类- 正在获得文件夹");
        lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "正在加载分类- 正在获得文档");
        lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "正在加载邮件 - 正在获取邮件");
	    lang.put("startup.loading.personal", "正在加载个人文档");
	    lang.put("startup.loading.mail", "正在加载邮件信息");
	    lang.put("startup.loading.templates", "正在加载模板");
	    lang.put("startup.loading.mail", "Loading e-mails");
	    lang.put("startup.loading.thesaurus", "Loading thesaurus");
	    lang.put("startup.loading.trash", "正在加载回收站");
	    lang.put("startup.loading.history.search", "加载历史检索");
	    lang.put("startup.loading.user.values", "加载用户值");
	    lang.put("startup.loading.property.group.translations", "正在加载属性组翻译");
	    lang.put("startup.keep.alive", "正在加载保持活动");
	    
	    // Update notification
	    lang.put("openkm.update.available", "OpenKM 可用更新");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "分类");
	    lang.put("leftpanel.label.trash", "回收站");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "已保存的检索");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "模板");
	    lang.put("leftpanel.label.my.documents", "我的文档");
	    lang.put("leftpanel.label.user.search", "用户消息");//User news
	    lang.put("leftpanel.label.all.repository", "所有存储文件"); //All repository
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "新建文件夹");
	    lang.put("tree.menu.directory.remove", "删除");
	    lang.put("tree.menu.directory.rename", "重命名");
	    lang.put("tree.menu.directory.refresh", "刷新");
	    lang.put("tree.menu.directory.move", "移动");
	    lang.put("tree.menu.directory.copy", "复制");
	    lang.put("tree.menu.directory.add.document", "新增文档");
	    lang.put("tree.menu.add.bookmark", "新增书签");
	    lang.put("tree.menu.set.home", "设为默认根目录");
	    lang.put("tree.menu.export", "导出文件");
	    lang.put("tree.status.refresh.folder", "更新目录树");
	    lang.put("tree.status.refresh.create", "正在新建文件夹");
	    lang.put("tree.status.refresh.delete", "正在删除文件夹");
	    lang.put("tree.status.refresh.rename", "正在重命名文件夹");
	    lang.put("tree.status.refresh.restore", "正在恢复文件夹");
	    lang.put("tree.status.refresh.purge", "正在清空文件夹");
	    lang.put("tree.status.refresh.get", "正在更新文件夹");
	    lang.put("tree.folder.new", "新文件夹");
	    lang.put("tree.status.refresh.add.subscription", "正在新增订阅");
	    lang.put("tree.status.refresh.remove.subscription", "正在删除订阅");
	    lang.put("tree.status.refresh.get.root", "正在刷新根文件夹");
	    lang.put("tree.status.refresh.get.user.home", "正在获得用户根目录");
	    lang.put("tree.status.refresh.purge.trash", "正在清空回收站");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "恢复");
	    lang.put("trash.menu.directory.purge", "清空");
	    lang.put("trash.menu.directory.purge.trash", "清空回收站");
	    lang.put("trash.directory.select.label", "选择目标文件夹");
	    
	    // General menu
	    lang.put("general.menu.file", "文件");
	    	lang.put("general.menu.file.exit", "退出");
            lang.put("general.menu.file.purge.trash", "清空回收站");
	    lang.put("general.menu.edit", "编辑");
			lang.put("general.menu.file.create.directory", "新建文件夹");
			lang.put("general.menu.file.download.document", "下载文档");
			lang.put("general.menu.file.download.document.pdf", "下载保存为pdf");
			lang.put("general.menu.file.send.link", "发送文档链接");
			lang.put("general.menu.file.lock", "上锁");
			lang.put("general.menu.file.unlock", "解锁");
			lang.put("general.menu.file.add.document", "新增文档");
			lang.put("general.menu.file.checkout", "签出");
			lang.put("general.menu.file.checkin", "签入");
			lang.put("general.menu.file.cancel.checkout", "取消签入");
			lang.put("general.menu.file.delete", "删除");
			lang.put("general.menu.file.refresh", "刷新");
			lang.put("general.menu.file.scanner", "Scanner");
	    lang.put("general.menu.tools", "工具");
	    	lang.put("general.menu.tools.languages", "语言");
	    	lang.put("general.menu.tools.skin", "皮肤");
    			lang.put("general.menu.tools.skin.default", "默认");
    			lang.put("general.menu.tools.skin.default2", "默认2");
    			lang.put("general.menu.tools.skin.mediumfont", "中号字体");
    			lang.put("general.menu.tools.skin.bigfont", "大号字体");
    		lang.put("general.menu.debug.console", "调试命令行");
    		lang.put("general.menu.administration", "显示管理");
    		lang.put("general.menu.tools.preferences", "Prefererences首选项");
    			lang.put("general.menu.tools.user.preferences", "用户配置");
    	lang.put("general.menu.bookmark", "书签");
	    	lang.put("general.menu.bookmark.home", "根目录");
	    	lang.put("general.menu.bookmark.default.home", "设为默认根目录");
	    	lang.put("general.menu.bookmark.edit", "编辑书签");
	    lang.put("general.menu.help", "帮助");
		    lang.put("general.menu.bug.report", "漏洞报告");
		    lang.put("general.menu.support.request", "支持需求");
		    lang.put("general.menu.public.forum", "公开论坛");
		    lang.put("general.menu.project.web", "项目主页");
		    lang.put("general.menu.version.changes", "版本日志");
		    lang.put("general.menu.documentation", "文档");
		    lang.put("general.menu.about", "关于OpenKM");
	    lang.put("general.connected", "已连接");
	    
	    // File Browser
	    lang.put("filebrowser.name", "名称");
	    lang.put("filebrowser.date.update", "更新日期");
	    lang.put("filebrowser.size", "大小");
	    lang.put("filebrowser.path", "路径");
	    lang.put("filebrowser.author", "作者");
	    lang.put("filebrowser.version", "版本");
        lang.put("filebrowser.menu.checkout", "签出");
	    lang.put("filebrowser.menu.checkin", "签入");
	    lang.put("filebrowser.menu.delete", "删除");
	    lang.put("filebrowser.menu.rename", "重命名");
	    lang.put("filebrowser.menu.checkout.cancel", "取消签出");
	    lang.put("filebrowser.menu.lock", "上锁");
	    lang.put("filebrowser.menu.unlock", "解锁");
	    lang.put("filebrowser.menu.download", "下载");
	    lang.put("filebrowser.menu.move", "移动");
	    lang.put("filebrowser.menu.copy", "复制");
	    lang.put("filebrowser.menu.create.from.template", "从模板新建");
	    lang.put("filebrowser.menu.add.property.group", "新增属性组");
	    lang.put("filebrowser.menu.remove.property.group", "移除属性组");
	    lang.put("filebrowser.menu.start.workflow", "启动工作流");
	    lang.put("filebrowser.menu.add.subscription", "新增订阅");
	    lang.put("filebrowser.menu.remove.subscription", "移除订阅");
	    lang.put("filebrowser.menu.add.bookmark", "新增书签");
	    lang.put("filebrowser.menu.set.home", "设为默认根目录");
	    lang.put("filebrowser.menu.refresh", "刷新");
	    lang.put("filebrowser.menu.export", "导出到文件");
	    lang.put("filebrowser.menu.play", "播放");
	    lang.put("filebrowser.menu.image.viewer", "图片查看器");
	    lang.put("filebrowser.status.refresh.folder", "正在更新文件夹列表");
	    lang.put("filebrowser.status.refresh.document", "正在更新文档列表");
	    lang.put("filebrowser.status.refresh.mail", "正在更新邮件列表");
	    lang.put("filebrowser.status.refresh.delete.folder", "正在删除文件夹");
	    lang.put("filebrowser.status.refresh.delete.document", "正在删除文档");
	    lang.put("filebrowser.status.refresh.checkout", "签出文档");
	    lang.put("filebrowser.status.refresh.lock", "正在对文档上锁");
	    lang.put("filebrowser.status.refresh.unlock", "正在对文档解锁");
	    lang.put("filebrowser.status.refresh.document.rename", "正在重命名文档");
	    lang.put("filebrowser.status.refresh.folder.rename", "正在重命名文件夹");
	    lang.put("filebrowser.status.refresh.document.purge", "正在删除文档");
	    lang.put("filebrowser.status.refresh.folder.purge", "正在删除文件夹");
	    lang.put("filebrowser.status.refresh.folder.get", "正在更新文件夹");
	    lang.put("filebrowser.status.refresh.document.get", "正在更新文档");
	    lang.put("filebrowser.status.refresh.add.subscription", "正在新增订阅");
	    lang.put("filebrowser.status.refresh.remove.subscription", "正在删除订阅");
	    lang.put("filebrowser.status.refresh.get.user.home", "正在获得用户根目录");
	    lang.put("filebrowser.status.refresh.delete.mail", "正在删除邮件");
	    lang.put("filebrowser.status.refresh.mail.purge", "正在清理右键");
	    
	    // File Upload
	    lang.put("fileupload.send", "发送");
	    lang.put("fileupload.status.sending", "文件上传中...");
	    lang.put("fileupload.status.indexing", "文件索引中...");
	    lang.put("fileupload.status.ok", "文件正确上传");
	    lang.put("fileupload.upload.status", "上传状态");
	    lang.put("fileupload.upload.uploaded", "已上传");
	    lang.put("fileupload.upload.completed", "完成上传");
	    lang.put("fileupload.upload.runtime", "运行时");
	    lang.put("fileupload.upload.remaining", "剩余");
	    lang.put("fileupload.button.close", "关闭");
	    lang.put("fileupload.button.add.other.file", "新增另一文件");
	    lang.put("fileupload.status.move.file", "文件移动中...");
	    lang.put("fileupload.status.move.mail", "邮件移动中...");
	    lang.put("fileupload.status.copy.file", "文件复制中...");
	    lang.put("fileupload.status.copy.mail", "邮件复制中...");
	    lang.put("fileupload.status.restore.file", "文件恢复中...");
	    lang.put("fileupload.status.restore.mail", "邮件恢复中...");
	    lang.put("fileupload.status.move.folder", "文件夹移动中...");
	    lang.put("fileupload.status.copy.folder", "文件夹复制中...");
	    lang.put("fileupload.status.restore.folder", "文件夹恢复中...");
	    lang.put("fileupload.status.create.from.template", "从模板新建文件中...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "增加新文档");
	    lang.put("fileupload.label.update", "更新文档");
	    lang.put("fileupload.label.users.notify", "通知用户");
	    lang.put("fileupload.label.comment", "评论");
	    lang.put("fileupload.label.users.to.notify",  "被通知的用户");
	    lang.put("fileupload.label.users",  "用户");
	    lang.put("fileupload.label.must.select.users",  "你必须选择通知的用户");
	    lang.put("fileupload.label.importZip", "从zip压缩包中导入文档");
	    lang.put("fileupload.label.notify.comment", "通知消息");
	    lang.put("fileupload.label.error.importing.zip", "导入文件错误");
	    lang.put("fileupload.label.error.move.file", "移动文件错误");
	    lang.put("fileupload.label.error.move.mail", "移动邮件错误");
	    lang.put("fileupload.label.error.copy.file", "复制文件错误");
	    lang.put("fileupload.label.error.copy.mail", "复制邮件错误");
	    lang.put("fileupload.label.error.restore.file", "恢复文件错误");
	    lang.put("fileupload.label.error.restore.mail", "恢复邮件错误");
	    lang.put("fileupload.label.error.move.folder", "移动文件夹错误");
	    lang.put("fileupload.label.error.copy.folder", "复制文件夹错误");
	    lang.put("fileupload.label.error.restore.folder", "恢复文件夹错误");
	    lang.put("fileupload.label.error.create.from.template", "从文档新建文件错误");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "原文件夹或子文件夹不允许移动");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "原文件夹不允许移动");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "原文件夹不允许新建文件");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "属性");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "历史");
	    lang.put("tab.document.status.history", "正在更新历史");
	    lang.put("tab.status.security.role", "正在更新安全角色");
	    lang.put("tab.status.security.user", "正在更新安全用户");
	    lang.put("tab.document.status.group.properties", "正在更新属性组");
	    lang.put("tab.document.status.set.keywords", "正在设置关键字");
	    lang.put("tab.document.status.get.version.history.size", "刷新文档历史大小");
	    lang.put("tab.document.status.purge.version.history", "文档历史压缩");
	    lang.put("tab.document.status.restore.version", "正在恢复文档版本");
	    lang.put("tab.document.security", "安全");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "属性");
	    lang.put("tab.folder.security", "安全");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "桌面");
	    lang.put("tab.workspace.search", "检索");
	    lang.put("tab.workspace.dashboard", "个人仪表盘");
	    lang.put("tab.workspace.administration", "管理面板");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "名称");
	    lang.put("document.folder", "文件夹");
	    lang.put("document.size", "大小");
	    lang.put("document.created", "创建");
	    lang.put("document.lastmodified", "修改");
	    lang.put("document.mimetype", "MIME 类型");
	    lang.put("document.keywords", "关键字");
	    lang.put("document.by", "被");
	    lang.put("document.status", "状态");
	    lang.put("document.status.checkout", "编辑由");
	    lang.put("document.status.locked", "上锁由");
	    lang.put("document.status.normal", "有效");
	    lang.put("document.subscribed", "已订阅");
	    lang.put("document.subscribed.yes", "是");
	    lang.put("document.subscribed.no", "否");
	    lang.put("document.history.size", "历史大小");
	    lang.put("document.subscribed.users", "已订阅用户");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "新增备注");
	    lang.put("document.keywords.cloud", "关键字云");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "名称");
	    lang.put("folder.parent", "上一级");
	    lang.put("folder.created", "创建");
	    lang.put("folder.by", "被");
	    lang.put("folder.subscribed", "已订阅");
	    lang.put("folder.subscribed.yes", "是");
	    lang.put("folder.subscribed.no", "否");
	    lang.put("folder.subscribed.users", "订阅用户");
	    lang.put("folder.webdav", "WebDAV");
	    
	    // Version
	    lang.put("version.name", "版本");
	    lang.put("version.created", "日期");
	    lang.put("version.author", "作者");
	    lang.put("version.size", "大小");
	    lang.put("version.purge.document", "压缩的历史");
	    lang.put("version.comment", "评论");
	    
	    // Security
	    lang.put("security.label", "安全");
	    lang.put("security.group.name", "组");
	    lang.put("security.group.permission.read", "读");
	    lang.put("security.group.permission.write", "写");
	    lang.put("security.user.name", "用户");
	    lang.put("security.user.permission.read", "读");
	    lang.put("security.user.permission.write", "写");
	    lang.put("security.users", "用户");
	    lang.put("security.groups", "组");
	    lang.put("security.recursive", "递归修改权限");
	    
	    // Preview
	    lang.put("preview.unavailable", "预览不可用");

	    // Mail
	    lang.put("mail.from", "寄信人(From)");
	    lang.put("mail.reply", "答复(Reply to)");//Reply to
	    lang.put("mail.to", "收信人(To)");
	    lang.put("mail.subject", "主题(Subject)");
	    
	    // Error
	    lang.put("error.label", "系统产生了一个错误");
	    lang.put("error.invocation", "与服务器通信时发生错误");
	    
	    // About
	    lang.put("about.label", "关于OpenKM");
	    lang.put("about.loading", "加载中...");
	    
	    // Logout
	    lang.put("logout.label", "退出");
	    lang.put("logout.closed", "OpenKM 已经正确关闭.");
	    lang.put("logout.logout", "OpenKM 正在注销, 请稍候");
	    
	    // Confirm
	    lang.put("confirm.label", "确认");
	    lang.put("confirm.delete.folder", "你真的想删除文件夹?");
	    lang.put("confirm.delete.document", "你真的想删除文档?");
	    lang.put("confirm.delete.trash", "你真的想清空回收站?");
        lang.put("confirm.purge.folder", "你真的想删除文件夹?");
        lang.put("confirm.purge.document", "你真的想删除文档?");
        lang.put("confirm.delete.propety.group", "你真的想删除属性组?");
        lang.put("confirm.purge.version.history.document", "你真的想删除文档历史?");
	    lang.put("confirm.purge.restore.document", "你真的想恢复这个文档版本?");
	    lang.put("confirm.set.default.home", "你真的想设为默认根目录?");
	    lang.put("confirm.delete.saved.search", "你真的想删除查询条件吗？");//¿ Do you really want to delete saved search ?
	    lang.put("confirm.delete.user.news", "你真的想删除用户信息吗？");//¿ Do you really want to delete user news ?
	    lang.put("confirm.delete.mail", "你真的想删除邮件吗？");//¿ Do you really want to delete mail ?
	    
	    // Search inputs
	    lang.put("search.context", "范围");
	    lang.put("search.content", "内容");
	    lang.put("search.name", "名称");
	    lang.put("search.keywords", "关键字");
	    lang.put("search.folder", "Folder");
	    lang.put("search.results", "结果");
	    lang.put("search.to", "至");
	    lang.put("search.page.results", "每页显示结果数");
	    lang.put("search.add.property.group", "新增属性组");
	    lang.put("search.mimetype", "Mime 类型");
	    lang.put("search.type", "类型");
	    lang.put("search.type.document", "文档");
	    lang.put("search.type.folder", "文件夹");
	    lang.put("search.type.mail", "邮件");
	    lang.put("search.advanced", "高级检索");
	    lang.put("search.user", "用户");
	    lang.put("search.date.and", "至");
	    lang.put("search.date.range", "日期范围从");
	    lang.put("search.save.as.news", "作为用户信息保存");

	    // search folder filter popup
	    lang.put("search.folder.filter", "按文件夹过滤");
	    
	    // Search results
	    lang.put("search.result.name", "名称");
	    lang.put("search.result.score", "相关性");
	    lang.put("search.result.size", "大小");
	    lang.put("search.result.date.update", "更新日期");
	    lang.put("search.result.author", "作者");
	    lang.put("search.result.version", "版本");
	    lang.put("search.result.path", "路径");
	    lang.put("search.result.menu.download", "下载");
	    lang.put("search.result.menu.go.folder", "转到文件夹");
	    lang.put("search.result.menu.go.document", "转到文档");
	    lang.put("search.result.status.findPaginated", "正在更新检索");
	    lang.put("search.result.status.runsearch", "正在更新已保存的检索");
	    
	    // Search saved
	    lang.put("search.saved.run", "运行");
	    lang.put("search.saved.delete", "删除");
	    lang.put("search.saved.status.getsearchs", "正在刷新已保存的检索");
        lang.put("search.saved.status.savesearch", "正在更新已保存的检索");
        lang.put("search.saved.status.deletesearch", "正在删除已保存的检索");
        lang.put("search.saved.status.getusernews", "正在更新游湖信息");
	    
	    // Button
	    lang.put("button.close", "关闭");
	    lang.put("button.logout", "注销");
	    lang.put("button.update", "更新");
	    lang.put("button.cancel", "取消");
	    lang.put("button.accept", "接受");
	    lang.put("button.restore", "恢复");
	    lang.put("button.move", "移动");
	    lang.put("button.change", "改变");
	    lang.put("button.search", "检索");
	    lang.put("button.save.search", "保存检索");
	    lang.put("button.view", "查看");
	    lang.put("button.clean", "清空");
	    lang.put("button.add", "新增");
	    lang.put("button.delete", "删除");
	    lang.put("button.copy", "复制");
	    lang.put("button.create", "创建");
	    lang.put("button.show", "展示");
	    lang.put("button.memory", "更新");
	    lang.put("button.copy.clipboard", "复制到剪切板");	//Copy to clipboard
	    lang.put("button.start", "开始");
	    lang.put("button.select", "选择");
	    
	    // Group
	    lang.put("group.label", "新增属性组");
	    lang.put("group.group", "组");
	    lang.put("group.property.group", "属性");
	    
	    // Bookmark
	    lang.put("bookmark.label", "新增书签");
	    lang.put("bookmark.name", "名称");
	    lang.put("bookmark.edit.label", "编辑书签");
	    lang.put("bookmark.path", "路径");
	    lang.put("bookmark.type", "类型");
	    lang.put("bookmark.type.document", "文档");
	    lang.put("bookmark.type.folder", "文件夹");
	    
	    // Notify
	    lang.put("notify.label", "正在发送文档链接");
	    
	    // Status
	    lang.put("status.document.copied", "文档已标记复制");
        lang.put("status.document.cut", "文档已标记剪切");
	    lang.put("status.folder.copied", "文件夹已标记复制");
        lang.put("status.folder.cut", "文件夹已标记剪切");
	    lang.put("status.keep.alive.error", "已检测到和服务器断开连接(保持活动)");
	    lang.put("status.debug.enabled", "允许调试");
	    lang.put("status.debug.disabled", "不允许调试");
	    lang.put("status.network.error.detected", "监测到网络错误");//Network error detected
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "周日");
	    lang.put("calendar.day.monday", "周一");
	    lang.put("calendar.day.tuesday", "周二");
	    lang.put("calendar.day.wednesday", "周三");
	    lang.put("calendar.day.thursday", "周四");
	    lang.put("calendar.day.friday", "周五");
	    lang.put("calendar.day.saturday", "周六");
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
	    lang.put("media.player.label", "媒体播放器");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "图片查看器");
	    lang.put("image.viewer.zoom.in", "缩小");
	    lang.put("image.viewer.zoom.out", "放大");
	    
	    // Debug console
	    lang.put("debug.console.label", "调试控制器");
	    lang.put("debug.enable.disable", "CTRL+Z 切换允许或不允许调试模式");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "概括信息");//General
	    lang.put("dashboard.tab.news", "信息");//News
	    lang.put("dashboard.tab.user", "用户");//User
	    lang.put("dashboard.tab.workflow", "工作流");//Workflow
	    lang.put("dashboard.tab.mail", "邮件");//Mail
	    lang.put("dashboard.tab.keymap", "关键字地图");//Keyword map

	    // Dahboard general
	    lang.put("dashboard.new.items", "New");
	    lang.put("dashboard.user.locked.documents", "被锁定的文档");//Locked documents
	    lang.put("dashboard.user.checkout.documents", "检出的文档");//Checkout documents
	    lang.put("dashboard.user.last.modified.documents", "最近修改过的文档");//Last modified documents
	    lang.put("dashboard.user.last.downloaded.documents", "最近被下载的文档");//Last downloaded documents
	    lang.put("dashboard.user.subscribed.documents", "被订阅的文档");//Subscribed documents
	    lang.put("dashboard.user.subscribed.folders", "被订阅的文件夹");//Subscribed folders
	    lang.put("dashboard.user.last.uploaded.documents", "最近上传的文档");//Last uploaded documents
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "上周被查看次数最多的文档");//Last week top viewed documents
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "上个月被查看次数最多的文档");//Last month top viewed documents
	    lang.put("dashboard.general.last.week.top.modified.documents", "上周被修改次数最多的文档");//Last week top modified documents
	    lang.put("dashboard.general.last.month.top.modified.documents", "上个月被修改次数最多的文档");//Last month top modified documents
	    lang.put("dashboard.general.last.uploaded.documents", "最近上传的文档");//Last uploaded documents
	    lang.put("dashboard.workflow.pending.tasks", "挂起的任务");//Pending tasks
	    lang.put("dashboard.workflow.task", "任务");//Task
	    lang.put("dashboard.workflow.task.id", "ID");
	    lang.put("dashboard.workflow.task.name", "名称");//Name
	    lang.put("dashboard.workflow.task.created", "创建日期");//Creation date
	    lang.put("dashboard.workflow.task.start", "开始日期");//Start date
	    lang.put("dashboard.workflow.task.duedate", "到期日期");//Due date
	    lang.put("dashboard.workflow.task.end", "结束日期");//End date
	    lang.put("dashboard.workflow.task.description", "描述");//Description
	    lang.put("dashboard.workflow.task.process.instance", "流程实例");//Process instance
	    lang.put("dashboard.workflow.task.process.id", "流程ID");//
	    lang.put("dashboard.workflow.task.process.version", "版本");//Version
	    lang.put("dashboard.workflow.task.process.name", "名称");
	    lang.put("dashboard.workflow.task.process.description", "描述");
	    lang.put("dashboard.workflow.task.process.data", "数据");//Data
	    lang.put("dashboard.workflow.comments", "Comments");
	    lang.put("dashboard.workflow.task.process.forms", "Form");
	    lang.put("dashboard.workflow.add.comment","Add comment");
	    lang.put("dashboard.workflow.task.process.definition", "流程定义");//Process definition
	    lang.put("dashboard.workflow.task.process.path", "路径");//Path
	    lang.put("dashboard.refreshing", "刷新中");//Refreshing
	    lang.put("dashboard.keyword", "关键字");//Keywords
	    lang.put("dashboard.keyword.suggest", "关键字类型");//Type the keyword
	    lang.put("dashboard.keyword.all", "所有关键字");//All keywords
	    lang.put("dashboard.keyword.top", "排在最前面的关键字");//Top keywords
	    lang.put("dashboard.keyword.related", "关联关键字");//Related keywords
	    lang.put("dashboard.keyword.goto.document", "转到文档");//Goto document
	    lang.put("dashboard.keyword.clean.keywords", "清楚关键字");//Clean keywords
	    lang.put("dashboard.mail.last.imported.mails", "电子邮件");//Electronic mails
	    lang.put("dashboard.mail.last.imported.attached.documents", "附件");//Attachments
	    
	    // Workflow
	    lang.put("workflow.label", "启动工作流");
	    
	    // User configuration
	    lang.put("user.preferences.label", "用户信息配置");//User configuration
	    lang.put("user.preferences.user", "用户名"); //User
	    lang.put("user.preferences.password", "密码");//Password
	    lang.put("user.preferences.mail", "E-mail");//E-mail
	    lang.put("user.preferences.imap.host", "IMAP server");
	    lang.put("user.preferences.imap.user", "IMAP user name");
	    lang.put("user.preferences.imap.user.password", "IMAP user password");
	    lang.put("user.preferences.imap.folder", "IMAP folder");
	    lang.put("user.preferences.password.error", "错误：两次输入的密码不相同。");//Error: passwords are diferents
	    lang.put("user.preferences.user.data", "用户账号");//User account
	    lang.put("user.preferences.mail.data", "邮箱账号");//Mail account
	    lang.put("user.preferences.imap.error", "All fields are obligatory to set the mail configurations");
	    lang.put("user.preferences.imap.password.error.void", "Password must not be empty on IMAP mail creation");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "文档拒绝访问");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "找不到文档");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "文档已经存在");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "文档拒绝上锁");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "文档需要解锁");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "存储器内部错误");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "应用程序内部错误");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "找不到文档路径");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "文件夹拒绝访问");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "找不到文件夹");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "文件夹已存在");
        lang.put("OKM-" + ErrorCode.ORIGIN_OKMFolderService + ErrorCode.CAUSE_Repository, "存储器内部错误");
        lang.put("OKM-" + ErrorCode.ORIGIN_OKMFolderService + ErrorCode.CAUSE_General, "存储器内部错误");
        lang.put("OKM-" + ErrorCode.ORIGIN_OKMFolderService + ErrorCode.CAUSE_PathNotFound, "找不到文件夹路径");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "项目拒接访问");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "找不到项目");
        lang.put("OKM-" + ErrorCode.ORIGIN_OKMAuthServlet + ErrorCode.CAUSE_Repository, "存储器内部错误");
        lang.put("OKM-" + ErrorCode.ORIGIN_OKMAuthServlet + ErrorCode.CAUSE_General, "存储器内部错误");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "找不到文档");
        lang.put("OKM-" + ErrorCode.ORIGIN_OKMPropertyGroupService + ErrorCode.CAUSE_General, "存储器内部错误");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "不支持的文件格式");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "文档已经存在");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "文档超过规定大小");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "会话关闭");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "通常查询错误");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "已保存的检索名必须唯一");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "书签名必须唯一");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "会话断开");
	    
	    lang.put("OKM-" + ErrorCode.ORIGIN_OKMDashboardService + ErrorCode.CAUSE_Repository, "存储器内部错误");
	  }
}
