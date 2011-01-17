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

package com.openkm.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public class TemplateUtils {
	private static Logger log = LoggerFactory.getLogger(Config.class);
	private static Configuration cfg = null;
	
	/**
	 * Singleton FreeMaker configuration
	 */
	public static Configuration getConfig() {
		if (cfg == null) {
			try {
				cfg = new Configuration();
				cfg.setDirectoryForTemplateLoading(new File(Config.HOME_DIR));
				cfg.setObjectWrapper(new DefaultObjectWrapper());
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return cfg;
	}
	
	/**
	 * Check for template existence
	 */
	public static boolean templateExists(String name) {
		try {
			getConfig().getTemplate(name);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
