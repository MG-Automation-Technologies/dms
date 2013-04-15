/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2012 Paco Avila & Josep Llort
 * 
 * No bytes were intentionally harmed during the development of this application.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.openkm.extension.frontend.client.bean;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTFolder;

/**
 * GWTDropboxStatusListener
 * 
 * @author sochoa
 */
public class GWTDropboxStatusListener implements IsSerializable {
	private GWTDocument document;
	private GWTFolder folder;
	
	public GWTDocument getDocument() {
		return document;
	}
	
	public void setDocument(GWTDocument document) {
		this.document = document;
	}
	
	public GWTFolder getFolder() {
		return folder;
	}
	
	public void setFolder(GWTFolder folder) {
		this.folder = folder;
	}
}