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

package com.openkm.dao.bean;

import java.io.Serializable;

public class UserProfileMenu implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean fileVisible;
	private boolean editVisible;
	private boolean toolsVisible;
	private boolean bookmarksVisible;
	private boolean helpVisible;
	private UserProfileMenuFile file = new UserProfileMenuFile();
	private UserProfileMenuBookmark bookmark = new UserProfileMenuBookmark();
	private UserProfileMenuTool tool = new UserProfileMenuTool();
	private UserProfileMenuEdit edit = new UserProfileMenuEdit();
	private UserProfileMenuHelp help = new UserProfileMenuHelp();
	
	public boolean isFileVisible() {
		return fileVisible;
	}

	public void setFileVisible(boolean fileVisible) {
		this.fileVisible = fileVisible;
	}
	
	public boolean isEditVisible() {
		return editVisible;
	}

	public void setEditVisible(boolean editVisible) {
		this.editVisible = editVisible;
	}

	public boolean isToolsVisible() {
		return toolsVisible;
	}

	public void setToolsVisible(boolean toolsVisible) {
		this.toolsVisible = toolsVisible;
	}

	public boolean isBookmarksVisible() {
		return bookmarksVisible;
	}

	public void setBookmarksVisible(boolean bookmarksVisible) {
		this.bookmarksVisible = bookmarksVisible;
	}

	public boolean isHelpVisible() {
		return helpVisible;
	}

	public void setHelpVisible(boolean helpVisible) {
		this.helpVisible = helpVisible;
	}

	public UserProfileMenuFile getFile() {
		return file;
	}

	public void setFile(UserProfileMenuFile file) {
		this.file = file;
	}
	
	public UserProfileMenuBookmark getBookmark() {
		return bookmark;
	}

	public void setBookmark(UserProfileMenuBookmark bookmark) {
		this.bookmark = bookmark;
	}

	public UserProfileMenuTool getTool() {
		return tool;
	}

	public void setTool(UserProfileMenuTool tool) {
		this.tool = tool;
	}

	public UserProfileMenuEdit getEdit() {
		return edit;
	}

	public void setEdit(UserProfileMenuEdit edit) {
		this.edit = edit;
	}

	public UserProfileMenuHelp getHelp() {
		return help;
	}

	public void setHelp(UserProfileMenuHelp help) {
		this.help = help;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("fileVisible="); sb.append(fileVisible);
		sb.append(", editVisible="); sb.append(editVisible);
		sb.append(", toolsVisible="); sb.append(toolsVisible);
		sb.append(", bookmarksVisible="); sb.append(bookmarksVisible);
		sb.append(", helpVisible="); sb.append(helpVisible);
		sb.append(", file="); sb.append(file);
		sb.append(", bookmark="); sb.append(bookmark);
		sb.append(", tool="); sb.append(tool);
		sb.append(", edit="); sb.append(edit);
		sb.append(", help="); sb.append(help);
		sb.append("}");
		return sb.toString();
	}
}
