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
 * English (USA)
 * 
 * @author Michael bugaev
 */
public class Lang_en_US {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "MM-dd-yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "MM-dd-yyyy");
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
	    lang.put("leftpanel.label.taxonomy", "Taxonomy");
	    lang.put("leftpanel.label.trash", "Trash");
	    lang.put("leftpanel.label.mail", "E-mail");
	    lang.put("leftpanel.label.stored.search", "Stored searches");
	    lang.put("leftpanel.label.categories", "Categories");
	    lang.put("leftpanel.label.thesaurus", "Thesaurus");
	    lang.put("leftpanel.label.templates", "Templates");
	    lang.put("leftpanel.label.my.documents", "My documents");
	    lang.put("leftpanel.label.user.search", "User news");
	    lang.put("leftpanel.label.all.repository", "All repository");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "Create folder");
	    lang.put("tree.menu.directory.remove", "Delete");
	    lang.put("tree.menu.directory.rename", "Rename");
	    lang.put("tree.menu.directory.refresh", "Refresh");
	    lang.put("tree.menu.directory.move", "Move");
	    lang.put("tree.menu.directory.copy", "Copy");
	    lang.put("tree.menu.directory.add.document", "Add document");
	    lang.put("tree.menu.add.bookmark", "Add bookmark");
	    lang.put("tree.menu.set.home", "Set default home");
	    lang.put("tree.menu.export", "Export to file");
	    lang.put("tree.status.refresh.folder", "Updating folder tree");
	    lang.put("tree.status.refresh.create", "Creating folder");
	    lang.put("tree.status.refresh.delete", "Deleting folder");
	    lang.put("tree.status.refresh.rename", "Renaming folder");
	    lang.put("tree.status.refresh.restore", "Restoring folder");
	    lang.put("tree.status.refresh.purge", "Purging folder");
	    lang.put("tree.status.refresh.get", "Updating folder");
	    lang.put("tree.folder.new", "New folder");
	    lang.put("tree.status.refresh.add.subscription", "Adding subscription");
	    lang.put("tree.status.refresh.remove.subscription", "Deleting subscription");
	    lang.put("tree.status.refresh.get.root", "Refreshing root folder");
	    lang.put("tree.status.refresh.get.keywords", "Refreshing keywords");
	    lang.put("tree.status.refresh.get.user.home", "Getting user home");
	    lang.put("tree.status.refresh.purge.trash", "Cleaning trash");
	    lang.put("tree.menu.directory.find.folder","Find folder");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "Restore");
	    lang.put("trash.menu.directory.purge", "Purge");
	    lang.put("trash.menu.directory.purge.trash", "Purge trash");
	    lang.put("trash.directory.select.label", "Select destination folder");
	    
	    // General menu
	    lang.put("general.menu.file", "File");
	    	lang.put("general.menu.file.exit", "Exit");
	    	lang.put("general.menu.file.purge.trash", "Purge trash");
	    lang.put("general.menu.edit", "Edit");
			lang.put("general.menu.file.create.directory", "Create folder");
			lang.put("general.menu.file.download.document", "Download document");
			lang.put("general.menu.file.download.document.pdf", "Download document as PDF");
			lang.put("general.menu.file.send.link", "Send document link");
			lang.put("general.menu.file.send.attachment", "Send document attachment");
			lang.put("general.menu.file.lock", "Lock");
			lang.put("general.menu.file.unlock", "Unlock");
			lang.put("general.menu.file.add.document", "Add document");
			lang.put("general.menu.file.checkout", "Check out");
			lang.put("general.menu.file.checkin", "Check in");
			lang.put("general.menu.file.cancel.checkout", "Cancel check out");
			lang.put("general.menu.file.delete", "Delete");
			lang.put("general.menu.file.refresh", "Refresh");
			lang.put("general.menu.file.scanner", "Scanner");
			lang.put("general.menu.file.uploader", "File uploader");
	    lang.put("general.menu.tools", "Tools");
	    	lang.put("general.menu.tools.languages", "Languages");
	    	lang.put("general.menu.tools.skin", "Skin");
    			lang.put("general.menu.tools.skin.default", "By default");
    			lang.put("general.menu.tools.skin.default2", "By default 2");
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
	    lang.put("general.menu.help", "Help");
		    lang.put("general.menu.bug.report", "Bug report");
		    lang.put("general.menu.support.request", "Support request");
		    lang.put("general.menu.public.forum", "Public forum");
		    lang.put("general.menu.project.web", "Project web");
		    lang.put("general.menu.version.changes", "Version notes");
		    lang.put("general.menu.documentation", "Documentation");
		    lang.put("general.menu.about", "About OpenKM");
	    lang.put("general.connected", "Connected as");
	    
	    // File Browser
	    lang.put("filebrowser.name", "Name");
	    lang.put("filebrowser.date.update", "Update date");
	    lang.put("filebrowser.size", "Size");
	    lang.put("filebrowser.path", "Path");
	    lang.put("filebrowser.author", "Author");
	    lang.put("filebrowser.version", "Version");
	    lang.put("filebrowser.menu.checkout", "Check out");
	    lang.put("filebrowser.menu.checkin", "Check in");
	    lang.put("filebrowser.menu.delete", "Delete");
	    lang.put("filebrowser.menu.rename", "Rename");
	    lang.put("filebrowser.menu.checkout.cancel", "Cancel check out");
	    lang.put("filebrowser.menu.lock", "Lock");
	    lang.put("filebrowser.menu.unlock", "Unlock");
	    lang.put("filebrowser.menu.download", "Download");
	    lang.put("filebrowser.menu.move", "Move");
	    lang.put("filebrowser.menu.copy", "Copy");
	    lang.put("filebrowser.menu.create.from.template", "Create from template");
	    lang.put("filebrowser.menu.add.property.group", "Add property group");
	    lang.put("filebrowser.menu.remove.property.group", "Remove property group");
	    lang.put("filebrowser.menu.start.workflow", "Start workflow");
	    lang.put("filebrowser.menu.add.subscription", "Add subscription");
	    lang.put("filebrowser.menu.remove.subscription", "Remove subscription");
	    lang.put("filebrowser.menu.add.bookmark", "Add bookmark");
	    lang.put("filebrowser.menu.set.home", "Set default home");
	    lang.put("filebrowser.menu.refresh", "Refresh");
	    lang.put("filebrowser.menu.export", "Export to ZIP");
	    lang.put("filebrowser.menu.play", "Play");
	    lang.put("filebrowser.menu.image.viewer", "Image viewer");
	    lang.put("filebrowser.status.refresh.folder", "Updating folder list");
	    lang.put("filebrowser.status.refresh.document", "Updating document list");
	    lang.put("filebrowser.status.refresh.mail", "Updating mail list");
	    lang.put("filebrowser.status.refresh.delete.folder", "Deleting folder");
	    lang.put("filebrowser.status.refresh.delete.document", "Deleting document");
	    lang.put("filebrowser.status.refresh.checkout", "Checking out document");
	    lang.put("filebrowser.status.refresh.lock", "Locking document");
	    lang.put("filebrowser.status.refresh.unlock", "Unlocking document");
	    lang.put("filebrowser.status.refresh.document.rename", "Renaming document");
	    lang.put("filebrowser.status.refresh.folder.rename", "Renaming folder");
	    lang.put("filebrowser.status.refresh.document.purge", "Deleting document");
	    lang.put("filebrowser.status.refresh.folder.purge", "Deleting folder");
	    lang.put("filebrowser.status.refresh.folder.get", "Updating folder");
	    lang.put("filebrowser.status.refresh.document.get", "Updating document");
	    lang.put("filebrowser.status.refresh.add.subscription", "Adding subscription");
	    lang.put("filebrowser.status.refresh.remove.subscription", "Deleting subscription");
	    lang.put("filebrowser.status.refresh.get.user.home", "Getting user home");
	    lang.put("filebrowser.status.refresh.delete.mail", "Deleting mail");
	    lang.put("filebrowser.status.refresh.mail.purge", "Deleting mail");
	    
	    // File Upload
	    lang.put("fileupload.send", "Send");
	    lang.put("fileupload.status.sending", "Uploading file...");
	    lang.put("fileupload.status.indexing", "Indexing file...");
	    lang.put("fileupload.status.ok", "File uploaded correctly");
	    lang.put("fileupload.upload.status", "Upload status");
	    lang.put("fileupload.upload.uploaded", "Uploaded");
	    lang.put("fileupload.upload.completed", "Upload completed");
	    lang.put("fileupload.upload.runtime", "Runtime");
	    lang.put("fileupload.upload.remaining", "Remaining");
	    lang.put("fileupload.button.close", "Close");
	    lang.put("fileupload.button.add.other.file", "Add another file");
	    lang.put("fileupload.status.move.file", "Moving file...");
	    lang.put("fileupload.status.move.mail", "Moving mail...");
	    lang.put("fileupload.status.copy.file", "Copying file...");
	    lang.put("fileupload.status.copy.mail", "Copying mail...");
	    lang.put("fileupload.status.restore.file", "Restoring file...");
	    lang.put("fileupload.status.restore.mail", "Restoring mail...");
	    lang.put("fileupload.status.move.folder", "Moving folder...");
	    lang.put("fileupload.status.copy.folder", "Copying folder...");
	    lang.put("fileupload.status.restore.folder", "Restoring folder...");
	    lang.put("fileupload.status.create.from.template", "Creating file from template...");
	    lang.put("fileupload.status.of", "of");
	    lang.put("fileupload.label.insert", "Add new documents");
	    lang.put("fileupload.label.update", "Update documents");
	    lang.put("fileupload.label.users.notify", "Notify users");
	    lang.put("fileupload.label.comment", "Comment");
	    lang.put("fileupload.label.users.to.notify",  "Users to be notified");
	    lang.put("fileupload.label.users",  "Users");
	    lang.put("fileupload.label.groups.to.notify","Groups to notify");
	    lang.put("fileupload.label.groups","Groups");
	    lang.put("fileupload.label.must.select.users",  "You must select some user to notify");
	    lang.put("fileupload.label.importZip", "Import Documents from ZIP");
	    lang.put("fileupload.label.notify.comment", "Notification message");
	    lang.put("fileupload.label.error.importing.zip", "Error importing file");
	    lang.put("fileupload.label.error.move.file", "Error moving file");
	    lang.put("fileupload.label.error.move.mail", "Error moving mail");
	    lang.put("fileupload.label.error.copy.file", "Error copying file");
	    lang.put("fileupload.label.error.copy.mail", "Error copying mail");
	    lang.put("fileupload.label.error.restore.file", "Error restoring file");
	    lang.put("fileupload.label.error.restore.mail", "Error restoring mail");
	    lang.put("fileupload.label.error.move.folder", "Error moving folder");
	    lang.put("fileupload.label.error.copy.folder", "Error copying folder");
	    lang.put("fileupload.label.error.restore.folder", "Error restoring folder");
	    lang.put("fileupload.label.error.create.from.template", "Error creating file from template");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "Not allowed to move to origin or child folder");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "Not allowed to move to origin folder");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "Not allowed to create a file in origin folder");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "Properties");
	    lang.put("tab.document.notes", "Notes");
	    lang.put("tab.document.history", "History");
	    lang.put("tab.document.status.history", "Updating history");
	    lang.put("tab.status.security.role", "Updating security roles");
	    lang.put("tab.status.security.user", "Updating security users");
	    lang.put("tab.document.status.group.properties", "Updating property group");
	    lang.put("tab.document.status.set.keywords", "Setting keywords");
	    lang.put("tab.document.status.set.categories", "Updating categories");
	    lang.put("tab.document.status.get.version.history.size", "Refreshing document history size");
	    lang.put("tab.document.status.purge.version.history", "Compacting document history");
	    lang.put("tab.document.status.restore.version", "Restoring document version");
	    lang.put("tab.document.security", "Security");
	    lang.put("tab.document.preview", "Preview");
	    lang.put("tab.folder.properties", "Properties");
	    lang.put("tab.folder.security", "Security");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "Desktop");
	    lang.put("tab.workspace.search", "Search");
	    lang.put("tab.workspace.dashboard", "Dashboard");
	    lang.put("tab.workspace.administration", "Administration");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "Name");
	    lang.put("document.folder", "Folder");
	    lang.put("document.size", "Size");
	    lang.put("document.created", "Created");
	    lang.put("document.lastmodified", "Modified");
	    lang.put("document.mimetype", "MIME type");
	    lang.put("document.keywords", "Keywords");
	    lang.put("document.by", "by");
	    lang.put("document.status", "Status");
	    lang.put("document.status.checkout", "Edited by");
	    lang.put("document.status.locked", "Locked by");
	    lang.put("document.status.normal", "Available");
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
	    lang.put("folder.name", "Name");
	    lang.put("folder.parent", "Parent");
	    lang.put("folder.created", "Created");
	    lang.put("folder.by", "by");
	    lang.put("folder.subscribed", "Subscribed");
	    lang.put("folder.subscribed.yes", "Yes");
	    lang.put("folder.subscribed.no", "No");
	    lang.put("folder.subscribed.users", "Subscribed users");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "Folders");
	    lang.put("folder.number.documents", "Documents");
	    lang.put("folder.number.mails", "Mails");
	    
	    // Version
	    lang.put("version.name", "Version");
	    lang.put("version.created", "Date");
	    lang.put("version.author", "Author");
	    lang.put("version.size", "Size");
	    lang.put("version.purge.document", "Compact history");
	    lang.put("version.comment", "Comment");
	    
	    // Security
	    lang.put("security.label", "Security");
	    lang.put("security.group.name", "Group");
	    lang.put("security.group.permission.read", "Read");
	    lang.put("security.group.permission.write", "Write");
	    lang.put("security.group.permission.delete", "Delete");
	    lang.put("security.group.permission.security", "Security");
	    lang.put("security.user.name", "User");
	    lang.put("security.user.permission.read", "Read");
	    lang.put("security.user.permission.write", "Write");
	    lang.put("security.user.permission.delete", "Delete");
	    lang.put("security.user.permission.security", "Security");
	    lang.put("security.users", "Users");
	    lang.put("security.groups", "Groups");
	    lang.put("security.recursive", "Recursive permission changes");
	    lang.put("secutiry.filter.by.users","Users filter");
	    lang.put("secutiry.filter.by.groups","Groups filter");
	    lang.put("security.status.updating","Updating security");
	    
	    // Preview
	    lang.put("preview.unavailable", "Preview unavailable");

	    // Mail
	    lang.put("mail.from", "From");
	    lang.put("mail.reply", "Reply to");
	    lang.put("mail.to", "To");
	    lang.put("mail.subject", "Subject");
	    lang.put("mail.attachment", "Attachments");
	    
	    // Error
	    lang.put("error.label", "The system has generated an error");
	    lang.put("error.invocation", "Error when communicating with server");
	    
	    // About
	    lang.put("about.label", "About OpenKM");
	    lang.put("about.loading", "Loading ...");
	    
	    // Logout
	    lang.put("logout.label", "Exit");
	    lang.put("logout.closed", "OpenKM has been closed correctly.");
	    lang.put("logout.logout", "OpenKM is logging out, please wait");
	    
	    // Confirm
	    lang.put("confirm.label", "Confirmation");
	    lang.put("confirm.delete.folder", "Do you really want to delete folder ?");
	    lang.put("confirm.delete.document", "Do you really want to delete document ?");
	    lang.put("confirm.delete.trash", "Do you really want to empty trash ?");
	    lang.put("confirm.purge.folder", "Do you really want to delete folder ?");
	    lang.put("confirm.purge.document", "Do you really want to delete document ?");
	    lang.put("confirm.delete.propety.group", "Do you really want to delete property group ?");
	    lang.put("confirm.purge.version.history.document", "Do you really want to delete document history ?");
	    lang.put("confirm.purge.restore.document", "Do you really want to restore to this document version ?");
	    lang.put("confirm.set.default.home", "Do you really want to set default home ?");
	    lang.put("confirm.delete.saved.search", "Do you really want to delete saved search ?");
	    lang.put("confirm.delete.user.news", "Do you really want to delete user news ?");
	    lang.put("confirm.delete.mail", "Do you really want to delete mail ?");
	    lang.put("confirm.get.pooled.workflow.task", "Do you want to assign this task to you ?");
	    lang.put("confirm.force.unlock", "Do you really want to force the document unlock ?");
	    lang.put("confirm.force.cancel.checkout", "Do you really want to force the document cancel checkout ?");
	    
	    // Search inputs
	    lang.put("search.context", "Context");
	    lang.put("search.content", "Content");
	    lang.put("search.name", "Name");
	    lang.put("search.keywords", "Keywords");
	    lang.put("search.folder", "Folder");
	    lang.put("search.category", "Category");
	    lang.put("search.results", "Results");
	    lang.put("search.to", "to");
	    lang.put("search.page.results", "Page results");
	    lang.put("search.add.property.group", "Add property group");
	    lang.put("search.mimetype", "MIME type");
	    lang.put("search.type", "Type");
	    lang.put("search.type.document", "Document");
	    lang.put("search.type.folder", "Folder");
	    lang.put("search.type.mail", "Mail");
	    lang.put("search.advanced", "Advanced search");
	    lang.put("search.user", "User");
	    lang.put("search.date.and", "and");
	    lang.put("search.date.range", "Date range between");
	    lang.put("search.save.as.news", "Save as user news");

	    // search folder filter popup
	    lang.put("search.folder.filter", "Filter by folder");
	    lang.put("search.category.filter", "Filter by category");
	    
	    // Search results
	    lang.put("search.result.name", "Name");
	    lang.put("search.result.score", "Relevance");
	    lang.put("search.result.size", "Size");
	    lang.put("search.result.date.update", "Update date");
	    lang.put("search.result.author", "Author");
	    lang.put("search.result.version", "Version");
	    lang.put("search.result.path", "Path");
	    lang.put("search.result.menu.download", "Download");
	    lang.put("search.result.menu.go.folder", "Go to folder");
	    lang.put("search.result.menu.go.document", "Go to document");
	    lang.put("search.result.status.findPaginated", "Updating search");
	    lang.put("search.result.status.runsearch", "Updating saved search");
	    
	    // Search saved
	    lang.put("search.saved.run", "Run");
	    lang.put("search.saved.delete", "Delete");
	    lang.put("search.saved.status.getsearchs", "Refreshing saved searches");
	    lang.put("search.saved.status.savesearch", "Updating saved search");
	    lang.put("search.saved.status.deletesearch", "Deleting saved search");
	    lang.put("search.saved.status.getusernews", "Refreshing user news");
	    
	    // Button
	    lang.put("button.close", "Close");
	    lang.put("button.logout", "Logout");
	    lang.put("button.update", "Update");
	    lang.put("button.cancel", "Cancel");
	    lang.put("button.accept", "Accept");
	    lang.put("button.restore", "Restore");
	    lang.put("button.move", "Move");
	    lang.put("button.change", "Change");
	    lang.put("button.search", "Search");
	    lang.put("button.save.search", "Save search");
	    lang.put("button.view", "View");
	    lang.put("button.clean", "Clean");
	    lang.put("button.add", "Add");
	    lang.put("button.delete", "Delete");
	    lang.put("button.copy", "Copy");
	    lang.put("button.create", "Create");
	    lang.put("button.show", "Show");
	    lang.put("button.memory", "Update");
	    lang.put("button.copy.clipboard", "Copy to clipboard");	
	    lang.put("button.start", "Start");
	    lang.put("button.select", "Select");
	    lang.put("button.test", "Test");
	    lang.put("button.next", "Next");
	    
	    // Group
	    lang.put("group.label", "Add property group");
	    lang.put("group.group", "Group");
	    lang.put("group.property.group", "Property");
	    
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
	    lang.put("notify.label.attachment", "Send document attachment");
	    
	    // Status
	    lang.put("status.document.copied", "Document marked to copy");
	    lang.put("status.document.cut", "Document marked to cut");
	    lang.put("status.folder.copied", "Folder marked to copy");
	    lang.put("status.folder.cut", "Folder marked to cut");
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
	    lang.put("user.preferences.roles","Roles");
	    lang.put("user.preferences.imap.host", "IMAP server");
	    lang.put("user.preferences.imap.user", "IMAP user name");
	    lang.put("user.preferences.imap.user.password", "IMAP user password");
	    lang.put("user.preferences.imap.folder", "IMAP folder");
	    lang.put("user.preferences.password.error", "Error: passwords are different");
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
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "Document access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "Document lock denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "Version error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Document access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Document not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Document already exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Document lock denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Document unlock desired");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Folder access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Folder not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Folder already exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Folder path not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_DatabaseException, "Database error");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_AccessDenied, "Item access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_ItemNotFound, "Item not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_General, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Document not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Repository internal error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Unsupported file format");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Document already exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Document size exceeded");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_DocumentNameMismatch, "Document name is diferent");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Session closed");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Generic error executing query");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "The stored search name must be unique");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "The bookmark name must be unique");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Session lost");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_DatabaseException, "Database error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error in browser configuration");
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