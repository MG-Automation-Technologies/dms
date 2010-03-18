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
 * English (United Kingdom)
 * 
 * @author Paco Avila
 */
public class Lang_en_GB {
	
	public final static HashMap<String,String> lang;
	  static {
	    lang = new HashMap<String,String>();
	    
        // Button
		lang.put("button.close", "Close");
		lang.put("button.logout", "Logout");
		lang.put("button.search", "Search");
		lang.put("button.clean", "Clean");
		lang.put("button.cancel", "Cancel");
		lang.put("button.select", "Select");
		lang.put("button.edit", "Edit");
		lang.put("button.view", "View");
		lang.put("button.add", "Add");
		lang.put("button.create.user", "Create user");
		lang.put("button.update.user", "Update user");
		lang.put("button.delete", "Delete");
		lang.put("button.add.new.role", "Add new role");
		lang.put("button.filter", "Filter");
		lang.put("button.refresh", "Refresh");
		lang.put("button.accept", "Accept");
		lang.put("button.execute", "Execute");
		
		// Toolbar
		lang.put("toolbar.general", "General");
		lang.put("toolbar.search", "Search");
		lang.put("toolbar.users", "Users");
		lang.put("toolbar.utilities", "Utilities");
		lang.put("toolbar.properties", "Properties");
		lang.put("toolbar.workflow", "Workflow");
		lang.put("toolbar.config", "Configuration");
		
		// Advanced Search
		lang.put("advanced.search.type", "Type");
		lang.put("advanced.search.query", "Query");
		lang.put("advanced.search.predefined", "Predefined query");
		lang.put("advanced.search.predefined.locked.documents", "Locked documents");
		lang.put("advanced.search.predefined.documents.created.by.admin", "Documents created by user 'okmAdmin'");
		lang.put("advanced.search.predefined.documents.with.property.group.test", "Documents with property group 'okg:test'");
		lang.put("advanced.search.predefined.documents.observed", "Documents observed");
		lang.put("advanced.search.status.search", "Searching");
		
		// Users
		lang.put("users.refreshing.every", "Refreshing every");
		lang.put("users.refreshing.every.5.seconds", "5 seconds");
		lang.put("users.refreshing.every.15.seconds", "15 seconds");
		lang.put("users.refreshing.every.30.seconds", "30 seconds");
		lang.put("users.refreshing.every.1.minute", "1 minute");
		lang.put("users.refreshing.every.5.minutes", "5 minutes");
		lang.put("users.refreshing.every.15.minutes", "15 minutes");
		lang.put("users.uid", "User");
		lang.put("users.active", "Active");
		lang.put("users.token", "Token");
		lang.put("users.creation", "Creation");
		lang.put("users.last.access", "Last access");
		lang.put("users.refreshing", "Refreshing ...");
		lang.put("users.mail", "Mail");
		lang.put("users.password", "Password");
		lang.put("users.active", "Active");
		lang.put("users.active.yes", "Yes");
		lang.put("users.active.no", "No");
		lang.put("users.roles", "Roles");
		lang.put("users.activity.log.uid", "User");
		lang.put("users.activity.log.date.from", "From date");
		lang.put("users.activity.log.date.to", "To date");
		lang.put("users.activity.log.action", "Action");
		lang.put("users.activity.date", "Date");
		lang.put("users.activity.user", "User");
		lang.put("users.activity.date", "Date");
		lang.put("users.activity.token", "Token");
		lang.put("users.activity.action", "Action");
		lang.put("users.activity.params", "Params");
		lang.put("users.error.password.equals", "Passwords must be equals");
		lang.put("users.error.password.blank", "Passwords must not be blank");
		lang.put("users.error.rol.name.empty", "Rol name is empty");
		lang.put("users.error.rol.exist", "Rol previously exist");
		lang.put("users.menu.delete", "Delete");
		lang.put("users.menu.edit", "Edit");
		lang.put("users.menu.add", "Add user");
		lang.put("users.status.search", "Searching");
		
		// General utils
		lang.put("general.util.import", "Import");
		lang.put("general.util.export", "Export");
		lang.put("general.util.register", "Register");
		lang.put("general.util.filesystem.path", "Filesystem path");
		lang.put("general.util.repository.path", "Repository path");
		lang.put("general.util.register.property.groups", "Register property groups");
		lang.put("general.util.property.group.definition.path", "Property Group definition path");
		lang.put("general.util.folder.destination", "Destination");
		lang.put("general.util.folder.destination.taxonomy", "Taxonomy");
		lang.put("general.util.folder.destination.templates", "Templates");
		lang.put("general.util.select.folder.destination", "Select folder destination");
		lang.put("general.util.workflow.file", "Workflow file");
		lang.put("general.util.report", "Reports");
		lang.put("general.util.advanced", "Advanced utils");
		lang.put("general.util.advanced.edit.repository", "Edit repository");
		lang.put("general.util.advanced.edit.repository.warning", "Operate directly with repository is a critial operation that might be only used for expert users. Please be care with it !!!");
		lang.put("general.util.report.type", "Report type");
		lang.put("general.util.report.type.users", "Users");
		lang.put("general.util.report.type.locked.documents", "Locked documents");
		lang.put("general.util.report.type.subscribed.documents", "Subscribed documents");
		lang.put("general.util.refreshing", "Refreshing");
		lang.put("general.util.registering.properties", "Registering properties");
		lang.put("general.util.registering.workflow", "Registering workflow");
		lang.put("general.util.exporting", "Exporting");
		lang.put("general.util.importing", "Importing");
		
		// Property groups
		lang.put("group.property.group", "Property group");
		lang.put("group.property.group.type.textarea", "Text area");
		lang.put("group.property.group.type.input", "Input");
		lang.put("group.property.group.type.select", "Select");
		lang.put("group.property.group.type.select.multiple", "Select multiple");
		lang.put("group.property.unique.id", "Unique ID");
		lang.put("group.property.type", "Type");
		lang.put("group.property.values", "Values");
		lang.put("group.property.status.refresh", "Refreshing properties");
		
		// Stats
		lang.put("stats.context.size", "Repository size");
		lang.put("stats.context.taxonomy", "Taxonomy");
		lang.put("stats.context.personal", "My documents");
		lang.put("stats.context.templates", "Templates");
		lang.put("stats.context.trash", "Trash");
		lang.put("stats.context.folder.number", "Number of folders");
		lang.put("stats.context.document.number", "Number of documents");
		lang.put("stats.context.documents", "documents");
		lang.put("stats.context.total", "Total");
		lang.put("stats.context.other", "Others");
		lang.put("stats.context.document.subscribed", "Subscribed documents");
		lang.put("stats.context.folder.subscribed", "Subscribed folders");
		lang.put("stats.context.document.with.properties", "Document with properties");
		lang.put("stats.context.folders", "folders");
		lang.put("stats.support", "Support");
		lang.put("stats.installation.id", "Installation ID");
		lang.put("stats.information", "Information");
		lang.put("general.status.graph", "Refreshing graphs");
		
		// Workflow
		lang.put("workflow.id", "ID");
		lang.put("workflow.name", "Name");
		lang.put("workflow.last.version", "Last version");
		lang.put("workflow.version", "Version");
		lang.put("workflow.ended", "Ended");
		lang.put("workflow.current.nodes", "Current nodes");
		lang.put("workflow.variables", "Variables");
		lang.put("workflow.suspended", "Suspended");
		lang.put("workflow.menu.instances", "Instances");
		lang.put("workflow.menu.delete", "Delete");
		lang.put("workflow.status.delete", "Deleting workflow");
		lang.put("workflow.status.version", "Refreshing workflow versions");
		lang.put("workflow.status.instances", "Refreshing workflow instances");
		
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
	    
	    // Confirm
	    lang.put("confirm.label", "Confirmation");
	    lang.put("confirm.delete.user", "¿ Do you really want to delete user ?");
	    lang.put("confirm.delete.workflow", "¿ Do you really want to delete workflow ?");
	    lang.put("confirm.delete.workflow.version", "¿ Do you really want to delete this workflow version ?");
		
		// Error
	    lang.put("error.label", "The system has generated an error");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "Document access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "Document not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "Document already exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "Document lock denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Document unlock desired");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "Application internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "Document path not found");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "Folder access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "Folder not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "Folder already exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "Folder path not found");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_AccessDenied, "Item access denied");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_ItemNotFound, "Item not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_Repository, "Repository internal error");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthServlet+ErrorCode.CAUSE_General, "Repository internal error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "Document not found");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "Repository internal error");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "Unsupported file format");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "Document already exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "Document size exceeded");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Session closed");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "The stored search name must be unique");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "The bookmark name must be unique");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Session lost");
	  
	  }
}
