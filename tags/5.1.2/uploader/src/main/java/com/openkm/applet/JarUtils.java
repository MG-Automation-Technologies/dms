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

package com.openkm.applet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class JarUtils {
	private static String appVersion = null;
	
	/**
	 * @throws IOException 
	 * 
	 */
	public static synchronized String getAppVersion() {
		if (appVersion == null) {
			try {
				appVersion = new JarUtils().readAppVersion();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return appVersion;
	}
		
	/**
	 * 
	 */
	private String readAppVersion() throws IOException {
		URLClassLoader cl = (URLClassLoader) getClass().getClassLoader();
		String ret = new String();
		
		try {
			URL url = cl.findResource("META-INF/MANIFEST.MF");
			Manifest mf = new Manifest(url.openStream());
			Attributes atts = mf.getMainAttributes();
			String impVersion = atts.getValue("Implementation-Version");
			String impBuild = atts.getValue("Implementation-Build");
			
			if (impVersion != null)	ret = impVersion;
			if (impBuild != null) ret += " (build: " + impBuild + ")";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
