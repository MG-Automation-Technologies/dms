/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

package com.openkm.extension.core;

import java.io.File;
import java.net.URI;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

/**
 * Base for implementing core extension managers.
 * 
 * @author pavila
 */
public class ExtensionManager {
	private static Logger log = LoggerFactory.getLogger(ExtensionManager.class);
	protected static URI base = new File(Config.HOME_DIR + File.separator + "plugins").toURI();
	private static PluginManager pm = null;
	
	private ExtensionManager() {}
	
	protected static synchronized PluginManager getPluginManagerInstance() {
		if (pm == null) {
			log.info("Initialize and load plugins...");
			pm = PluginManagerFactory.createPluginManager();
			pm.addPluginsFrom(base);
		}
		
		return pm;
	}
	
	/**
	 * Reset the loaded plugins and load them again
	 */
	protected synchronized void reset() {
		log.info("Resetting extensions...");
		pm.shutdown();
		pm = PluginManagerFactory.createPluginManager();
		pm.addPluginsFrom(base);
	}
	
	/**
	 * Shutdown the extension manager
	 */
	protected synchronized void shutdown() {
		pm.shutdown();
	}
}
