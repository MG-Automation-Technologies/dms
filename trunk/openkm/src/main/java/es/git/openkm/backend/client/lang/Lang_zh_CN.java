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

package es.git.openkm.backend.client.lang;

import java.util.HashMap;

import es.git.openkm.backend.client.config.ErrorCode;

/**
 * Chinese .(2009-12-02 21:07)
 * mail:mingjie.mj@gmail.com
 * 
 * @author wmj2003
 */
public class Lang_zh_CN {
	
	public final static HashMap<String,String> lang;
	  static {
	    lang = new HashMap<String,String>();//
	    
        // Button
		lang.put("button.close", "关闭");//Close
		lang.put("button.logout", "退出系统");//Logout
		lang.put("button.search", "查询");//Search
		lang.put("button.clean", "清除");//Clean
		lang.put("button.cancel", "取消");//Cancel
		lang.put("button.select", "选择");//Select
		lang.put("button.edit", "编辑");//Edit
		lang.put("button.view", "查看");//View
		lang.put("button.create.user", "创建用户");//Create user
		lang.put("button.update.user", "更新用户");//Update user
		lang.put("button.delete", "删除");//Delete
		lang.put("button.add.new.role", "新增角色");//Add new role
		lang.put("button.filter", "过滤");//Filter
		lang.put("button.refresh", "刷新");//Refresh
		lang.put("button.accept", "允许");//Accept
		lang.put("button.execute", "执行");//Execute
		
		// Toolbar
		lang.put("toolbar.general", "常规统计");//General
		lang.put("toolbar.search", "查询");//Search
		lang.put("toolbar.users", "用户管理");//Users
		lang.put("toolbar.utilities", "工具");//Utilities
		lang.put("toolbar.properties", "属性管理");//Properties
		lang.put("toolbar.workflow", "工作流");//Workflow
		lang.put("toolbar.config", "系统配置");//Configuration
		
		// Advanced Search
		lang.put("advanced.search.type", "类型");//Type
		lang.put("advanced.search.query", "查询");//Query
		lang.put("advanced.search.predefined", "预定义查询条件");//Predefined query
		lang.put("advanced.search.predefined.locked.documents", "被锁定的文档");//Locked documents
		lang.put("advanced.search.predefined.documents.created.by.admin", "用户'okmAdmin'创建的文档");//Documents created by user 'okmAdmin'
		lang.put("advanced.search.predefined.documents.with.property.group.test", "属性组 'okg:test'对应的文档");//"Documents with property group 'okg:test'"
		lang.put("advanced.search.predefined.documents.observed", "被观察的文档");//Documents observed
		lang.put("advanced.search.status.search", "查询中...");//Searching
		
		// Users
		lang.put("users.refreshing.every", "每次刷新间隔时间");//Refreshing every
		lang.put("users.refreshing.every.5.seconds", "5 秒");//5 seconds
		lang.put("users.refreshing.every.15.seconds", "15 秒");//15 seconds
		lang.put("users.refreshing.every.30.seconds", "30 秒");//30 seconds
		lang.put("users.refreshing.every.1.minute", "1 分");//1 minute
		lang.put("users.refreshing.every.5.minutes", "5 分");//5 minutes
		lang.put("users.refreshing.every.15.minutes", "15 分");//15 minutes
		lang.put("users.uid", "用户账号");//User
		lang.put("users.active", "激活");//Active
		lang.put("users.token", "令牌");//Token
		lang.put("users.creation", "创建");//Creation
		lang.put("users.last.access", "最近被访问");//Last access
		lang.put("users.refreshing", "刷新中 ...");//Refreshing ...
		lang.put("users.mail", "电子邮件");//Mail
		lang.put("users.password", "密码");//Password
		lang.put("users.active", "激活");//Active
		lang.put("users.active.yes", "是");//Yes
		lang.put("users.active.no", "否");//No
		lang.put("users.roles", "角色");//Roles
		lang.put("users.activity.log.uid", "用户账号");//User
		lang.put("users.activity.log.date.from", "开始日期(>)");//From date
		lang.put("users.activity.log.date.to", "结束日期(<)");//To date
		lang.put("users.activity.log.action", "动作");//Action
		lang.put("users.activity.date", "日期");//Date
		lang.put("users.activity.user", "用户");//User
		lang.put("users.activity.date", "日期");//Date
		lang.put("users.activity.token", "令牌");//Token 令牌 标记
		lang.put("users.activity.action", "动作");//Action
		lang.put("users.activity.params", "参数");//Params
		lang.put("users.error.password.equals", "两次输入的密码必须相同");//Passwords must be equals
		lang.put("users.error.password.blank", "密码不能为空");//Passwords must not be blank
		lang.put("users.error.rol.name.empty", "角色名称是空的");//Rol name is empty
		lang.put("users.error.rol.exist", "角色已经存在");//Rol previously exist
		lang.put("users.menu.delete", "删除");//Delete
		lang.put("users.menu.edit", "编辑");//Edit
		lang.put("users.menu.add", "新增用户");//Add user
		lang.put("users.status.search", "正在搜索中");//Searching
		
		// General utils
		lang.put("general.util.import", "导入");//Import
		lang.put("general.util.export", "导出");//Export
		lang.put("general.util.register", "注册");//Register
		lang.put("general.util.filesystem.path", "文件系统路径");//Filesystem path
		lang.put("general.util.repository.path", "存储库路径");//Repository path
		lang.put("general.util.register.property.groups", "注册属性组");//Register property groups
		lang.put("general.util.register.thesaurus","Register thesaurus");
		lang.put("general.util.property.group.definition.path", "属性组定义路径");//Property Group definition path
		lang.put("general.util.thesaurus.show.level","Show level");
		lang.put("general.util.folder.destination", "目标文件夹");//Destination
		lang.put("general.util.folder.destination.taxonomy", "分类");//Taxonomy
		lang.put("general.util.folder.destination.templates", "模版");//Templates
		lang.put("general.util.select.folder.destination", "选择目标文件夹");//Select folder destination
		lang.put("general.util.workflow.file", "工作流文件");//Workflow file
		lang.put("general.util.report", "报告");//Reports
		lang.put("general.util.advanced", "高级功能");//Advanced utils
		lang.put("general.util.advanced.edit.repository", "编辑存储库");//Edit repository
		lang.put("general.util.advanced.edit.repository.warning", "直接操作存储库是个关键操作，仅供专家用户使用。 请一定要当心 数据被破坏!!!");//Operate directly with repository is a critial operation that might be only used for expert users. Please be care with it !!!
		lang.put("general.util.report.type", "报告类型");//Report type
		lang.put("general.util.report.type.users", "用户");//Users
		lang.put("general.util.report.type.locked.documents", "被锁定的文档");//Locked documents
		lang.put("general.util.report.type.subscribed.documents", "被订阅的文档");//Subscribed documents
		lang.put("general.util.refreshing", "正在刷新中");//Refreshing
		lang.put("general.util.registering.properties", "正在注册属性");//Registering properties
		lang.put("general.util.registering.workflow", "正在注册工作流");//Registering workflow
		lang.put("general.util.exporting", "导出中...");//Exporting
		lang.put("general.util.importing", "导入中...");//Importing
		
		// Property groups
		lang.put("group.property.group", "属性组");//Property group
		lang.put("group.property.group.type.textarea", "文本区域");//Text area
		lang.put("group.property.group.type.input", "输入");//Input
		lang.put("group.property.group.type.select", "选择");//Select
		lang.put("group.property.group.type.select.multiple", "多选");//Select multiple
		lang.put("group.property.unique.id", "唯一 ID");//Unique ID
		lang.put("group.property.type", "类型");//Type
		lang.put("group.property.values", "值");//Values
		lang.put("group.property.status.refresh", "正在刷新属性");//Refreshing properties
		
		// Stats
		lang.put("stats.context.size", "存储库大小");//Repository size
		lang.put("stats.context.taxonomy", "分类");//Taxonomy
		lang.put("stats.context.personal", "我的文档");//My documents
		lang.put("stats.context.templates", "模版");//Templates
		lang.put("stats.context.trash", "垃圾");//Trash
		lang.put("stats.context.folder.number", "文件夹数量");//Number of folders
		lang.put("stats.context.document.number", "文档数量");//Number of documents
		lang.put("stats.context.documents", "文档");//documents
		lang.put("stats.context.total", "共计");//Total
		lang.put("stats.context.other", "其他");//Others
		lang.put("stats.context.document.subscribed", "被订阅的文档");//Subscribed documents
		lang.put("stats.context.folder.subscribed", "被订阅的文件夹");//Subscribed folders
		lang.put("stats.context.document.with.properties", "文档属性");//Document with properties
		lang.put("stats.context.folders", "文件夹");//folders
		lang.put("stats.support", "支持");//Support
		lang.put("stats.installation.id", "安装 ID");//Installation ID
		lang.put("stats.information", "信息");//Information
		lang.put("general.status.graph", "正在刷新统计图");//Refreshing graphs
		
		// Workflow
		lang.put("workflow.id", "ID");//
		lang.put("workflow.name", "名称");//Name
		lang.put("workflow.last.version", "最新版本");//Last version
		lang.put("workflow.version", "版本");//Version
		lang.put("workflow.ended", "已结束");//Ended
		lang.put("workflow.current.nodes", "当前节点");//Current nodes
		lang.put("workflow.variables", "流程变量");//Variables
		lang.put("workflow.suspended", "挂起");//Suspended
		lang.put("workflow.menu.instances", "流程实例");//Instances
		lang.put("workflow.menu.delete", "删除");//Delete
		lang.put("workflow.status.delete", "正在删除工作流");//Deleting workflow
		lang.put("workflow.status.version", "正在刷新工作流版本");//Refreshing workflow versions
		lang.put("workflow.status.instances", "正在刷新工作流实例");//Refreshing workflow instances
		
		// Calendar
	    lang.put("calendar.day.sunday", "星期日");//Sunday
	    lang.put("calendar.day.monday", "星期一");//
	    lang.put("calendar.day.tuesday", "星期二");//
	    lang.put("calendar.day.wednesday", "星期三");//
	    lang.put("calendar.day.thursday", "星期四");//
	    lang.put("calendar.day.friday", "星期五");//
	    lang.put("calendar.day.saturday", "星期六");//
	    lang.put("calendar.month.january", "一月");//
	    lang.put("calendar.month.february", "二月");//
	    lang.put("calendar.month.march", "三月");//
	    lang.put("calendar.month.april", "四月");//
	    lang.put("calendar.month.may", "五月");//
	    lang.put("calendar.month.june", "六月");//
	    lang.put("calendar.month.july", "七月");//
	    lang.put("calendar.month.august", "八月");//
	    lang.put("calendar.month.september", "九月");//
	    lang.put("calendar.month.october", "十月");//
	    lang.put("calendar.month.november", "十一月");//
	    lang.put("calendar.month.december", "十二月");//December
	    
	    // Confirm
	    lang.put("confirm.label", "确认");//Confirmation
	    lang.put("confirm.delete.user", "你真的想删除此用户吗？");//¿ Do you really want to delete user ?
	    lang.put("confirm.delete.workflow", "你真的想删除此工作流吗？");//¿ Do you really want to delete workflow ?
	    lang.put("confirm.delete.workflow.version", "你真的想删除此工作流版本吗？");//¿ Do you really want to delete this workflow version ?
		
		// Error
	    lang.put("error.label", "系统错误");//The system has generated an error
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "文档访问被拒绝");//Document access denied
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "文档未发现");//Document not found
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "文档已经存在");//Document already exists
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "文档锁定被拒绝");//Document lock denied
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "文档期望被解锁");//Document unlock desired
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "存储库内部错误");//Repository internal error
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "应用内部错误");//Application internal error
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "文档路径未找到");//Document path not found
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "文件夹访问被拒绝");//Folder access denied
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "文件夹未找到");//Folder not found
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "文件夹已经存在");//Folder already exists
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "存储库内部错误");//Repository internal error
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "存储库内部错误");//Repository internal error
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "文件夹路径未找到");//Folder path not found

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "访问项目被拒绝");//Item access denied
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "项目未找到");//Item not found
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "存储库内部错误");//Repository internal error
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "存储库内部错误");//Repository internal error
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "文档没有找到");//Document not found
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "存储库内部错误");//Repository internal error
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "不支持此文件类型");//Unsupported file format
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "文档已经存在");//Document already exists
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "文档大小超出限制");//Document size exceeded
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "会话关闭");//Session closed
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "查询名称必须唯一");//The stored search name must be unique
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "书签名称必须唯一");//The bookmark name must be unique
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "会话丢失");//Session lost
	  
	  }
}
