
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

package com.openkm.extension.frontend.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Customization
 * 
 * @author jllort
 *
 */
public class Customization {
	
	/**
	 * getExtensionWidgets
	 * 
	 * @return
	 */
	public static List<Object> getExtensionWidgets() {
		List<Object> extensions = new ArrayList<Object>();
		
		// add here your widget extensions
//		extensions.add(new HelloWorld());
//		extensions.add(new ToolBarButtonExample().getButton());
//		extensions.add(new TabFolderExample());
//		extensions.add(new TabWorkspaceExample());
//		extensions.add(new MainMenuExample().getNewMenu());
//		extensions.add(new HandlersTest());
//		extensions.add(new ToolBarBoxExample().getToolBarBox());
		
// 	    OPENKM PROPIETARY EXTENSIONS		
//		extensions.add(new DownloadButton().getButton());
//		extensions.add(new DownloadPdfButton().getButton());
//		extensions.addAll(new Stapling().getExtensions());
//		extensions.addAll(new MetromUsa().getExtensions());
		
		return extensions;
	}
}