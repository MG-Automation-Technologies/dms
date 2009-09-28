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

package es.git.openkm.api;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.MetaData;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.NoSuchGroupException;
import es.git.openkm.core.NoSuchPropertyException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.module.PropertyGroupModule;
import es.git.openkm.module.ModuleManager;

/**
 * @author pavila
 * 
 */
public class OKMPropertyGroup implements PropertyGroupModule {
	private static Logger log = LoggerFactory.getLogger(OKMPropertyGroup.class);
	private static OKMPropertyGroup instance = new OKMPropertyGroup();
	
	private OKMPropertyGroup() {}
	
	public static OKMPropertyGroup getInstance() {
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#addGroup(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addGroup(String token, String docPath, String grpName)
			throws NoSuchGroupException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("addGroup(" + token + ", " + docPath + ", " + grpName + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.addGroup(token, docPath, grpName);
		log.debug("addGroup: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#removeGroup(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void removeGroup(String token, String docPath, String grpName)
			throws NoSuchGroupException, LockException, PathNotFoundException, 
			RepositoryException {
		log.debug("removeGroup(" + token + ", " + docPath + ", " + grpName + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.removeGroup(token, docPath, grpName);
		log.debug("removeGroup: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#getGroups(java.lang.String, java.lang.String)
	 */
	public Collection<String> getGroups(String token, String docPath)
			throws PathNotFoundException, RepositoryException {
		log.debug("getGroups(" + token + ", " + docPath + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		Collection<String> ret = cm.getGroups(token, docPath);
		log.debug("getGroups: "+ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#getAllGroups(java.lang.String)
	 */
	public Collection<String> getAllGroups(String token) throws RepositoryException {
		log.debug("getAllGroups(" + token + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		Collection<String> ret = cm.getAllGroups(token);
		log.debug("getAllGroups: "+ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#getProperties(java.lang.String, java.lang.String, java.lang.String)
	 */
	public HashMap<String, String[]> getProperties(String token, String docPath, String grpName) 
			throws NoSuchGroupException, PathNotFoundException, RepositoryException {
		log.debug("getProperties(" + token + ", " + docPath + ", " + grpName + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		HashMap<String, String[]> ret = cm.getProperties(token, docPath, grpName);
		log.debug("getProperties: "+ret);
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#setProperties(java.lang.String, java.lang.String, java.lang.String, java.util.HashMap)
	 */
	public void setProperties(String token, String docPath, String grpName, HashMap<String, String[]> properties)
			throws NoSuchPropertyException, NoSuchGroupException, LockException, PathNotFoundException, 
			AccessDeniedException, RepositoryException {
		log.debug("setProperties(" + token + ", " + docPath + ", " + properties + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.setProperties(token, docPath, grpName, properties);
		log.debug("setProperties: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#getValues(java.lang.String, java.lang.String)
	 */
	public HashMap<String, MetaData> getMetaData(String token, String grpName) throws IOException, RepositoryException {
		log.debug("getMetaData(" + token + ", " + grpName + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		HashMap<String, MetaData> ret = cm.getMetaData(token, grpName);
		log.debug("getMetaData: "+ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.PropertyGroupModule#getTranslations(java.lang.String, java.lang.String)
	 */
	public HashMap<String, String> getTranslations(String token, String lang) throws IOException, RepositoryException {
		log.debug("getTranslations(" + token + ", " + lang + ")");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		HashMap<String, String> ret = cm.getTranslations(token, lang);
		log.debug("getTranslations: "+ret);
		return ret;
	}
}
