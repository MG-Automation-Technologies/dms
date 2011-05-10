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

import java.util.ServiceConfigurationError;

import javax.jcr.Node;
import javax.jcr.Session;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.util.PluginManagerUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Folder;

public class FolderExtensionManager implements FolderExtension {
	private static Logger log = LoggerFactory.getLogger(FolderExtensionManager.class);
	private static FolderExtensionManager service = null;
	
	private FolderExtensionManager() {}
	
	public static synchronized FolderExtensionManager getInstance() {
		if (service == null) {
			service = new FolderExtensionManager();
		}
		
		return service;
	}
	
	@Override
	public void preCreate(Session session, Node parent, Folder fld) throws ExtensionException {
		try {
			PluginManager pm = ExtensionManager.getPluginManagerInstance();
			PluginManagerUtil pmu = new PluginManagerUtil(pm);
			
			for (FolderExtension de : pmu.getPlugins(FolderExtension.class)) {
				log.info("Es: {}", de.getClass().getCanonicalName());
				de.preCreate(session, parent, fld);
			}
		} catch (ServiceConfigurationError e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void postCreate(Session session, Node parent, Node fldNode, Folder fld) throws ExtensionException {
		try {
			PluginManager pm = ExtensionManager.getPluginManagerInstance();
			PluginManagerUtil pmu = new PluginManagerUtil(pm);
			
			for (FolderExtension de : pmu.getPlugins(FolderExtension.class)) {
				log.info("Es: {}", de.getClass().getCanonicalName());
				de.postCreate(session, parent, fldNode, fld);
			}
		} catch (ServiceConfigurationError e) {
			log.error(e.getMessage(), e);
		}
	}
}
