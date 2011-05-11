/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.PropertyGroup;
import es.git.openkm.bean.form.FormElement;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.LockException;
import es.git.openkm.core.NoSuchGroupException;
import es.git.openkm.core.NoSuchPropertyException;
import es.git.openkm.core.ParseException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.module.ModuleManager;
import es.git.openkm.module.PropertyGroupModule;

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
	
	@Override
	public void addGroup(String token, String docPath, String grpName)
			throws NoSuchGroupException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("addGroup({}, {}, {})", new Object[] { token, docPath, grpName });
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.addGroup(token, docPath, grpName);
		log.debug("addGroup: void");
	}

	@Override
	public void removeGroup(String token, String docPath, String grpName)
			throws NoSuchGroupException, LockException, PathNotFoundException, 
			RepositoryException {
		log.debug("removeGroup({}, {}, {})", new Object[] { token, docPath, grpName });
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.removeGroup(token, docPath, grpName);
		log.debug("removeGroup: void");
	}

	@Override
	public Collection<PropertyGroup> getGroups(String token, String docPath)
			throws IOException, ParseException, PathNotFoundException, RepositoryException {
		log.debug("getGroups({}, {})", token, docPath);
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		Collection<PropertyGroup> ret = cm.getGroups(token, docPath);
		log.debug("getGroups: {}", ret);
		return ret;
	}

	@Override
	public Collection<PropertyGroup> getAllGroups(String token) throws IOException, ParseException, 
			RepositoryException {
		log.debug("getAllGroups({})", token);
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		Collection<PropertyGroup> ret = cm.getAllGroups(token);
		log.debug("getAllGroups: "+ret);
		return ret;
	}

	@Override
	public HashMap<String, String[]> getProperties(String token, String docPath, String grpName) 
			throws NoSuchGroupException, PathNotFoundException, RepositoryException {
		log.debug("getProperties({}, {}, {})", new Object[] { token, docPath, grpName });
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		HashMap<String, String[]> ret = cm.getProperties(token, docPath, grpName);
		log.debug("getProperties: {}", ret);
		return ret;
	}

	@Override
	public void setProperties(String token, String docPath, String grpName, Map<String, String[]> properties)
			throws NoSuchPropertyException, NoSuchGroupException, LockException, PathNotFoundException, 
			AccessDeniedException, RepositoryException {
		log.debug("setProperties({}, {}, {})", new Object[] { token, docPath, properties });
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.setProperties(token, docPath, grpName, properties);
		log.debug("setProperties: void");
	}

	@Override
	public Collection<FormElement> getPropertyGroupForm(String token, String grpName) throws ParseException,
			IOException, RepositoryException {
		log.debug("getPropertyGroupForm({}, {})", token, grpName);
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		Collection<FormElement> ret = cm.getPropertyGroupForm(token, grpName);
		log.debug("getPropertyGroupForm: {}", ret);
		return ret;
	}
}