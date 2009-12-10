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

import es.git.openkm.frontend.client.lang.Lang_zh_CN;

/**
 * Language
 * 
 * @author jllort
 *
 */
public class Lang {
	
	// Languages
	public static final String LANG_en_GB = "en-GB";
	public static final String LANG_de_DE = "de-DE";
	public static final String LANG_zh_CN = "zh-CN";

	public static HashMap<String,String> getLang(String lang) {
		HashMap<String,String> hLang = new HashMap<String,String>();
		
		if (LANG_en_GB.equalsIgnoreCase(lang) || LANG_en_GB.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_en_GB.lang;
		} else if (LANG_de_DE.equalsIgnoreCase(lang) || LANG_de_DE.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_de_DE.lang;
		} else if (LANG_zh_CN.equalsIgnoreCase(lang) || LANG_zh_CN.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			hLang = Lang_zh_CN.lang;
		} else  {
			hLang = Lang_en_GB.lang;
		}
		
		return hLang;
	}
}
