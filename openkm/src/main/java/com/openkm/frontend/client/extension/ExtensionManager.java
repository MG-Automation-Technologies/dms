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

package com.openkm.frontend.client.extension;

import java.util.Iterator;
import java.util.List;

import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.extension.widget.TabDocumentExtension;

/**
 * ExtensionManager
 * 
 * @author jllort
 *
 */
public class ExtensionManager {
	
	// Declare here your new widgets
	public static void start(List<Object> extensions) {
		for (Iterator<Object> it = extensions.iterator(); it.hasNext();) {
			Object obj = it.next();
			if (obj instanceof TabDocumentExtension) {
				addTabDocument((TabDocumentExtension)obj);
			}
		}
	}
	
	/**
	 * addTabDocument
	 * 
	 * @param extension
	 */
	private static void addTabDocument(TabDocumentExtension extension) {
		Main.get().mainPanel.browser.tabMultiple.tabDocument.addDocumentExtension(extension);
	}
}