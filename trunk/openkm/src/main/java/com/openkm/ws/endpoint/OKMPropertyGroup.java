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

package com.openkm.ws.endpoint;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.jboss.annotation.security.SecurityDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.PropertyGroup;
import com.openkm.bean.form.FormElement;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.LockException;
import com.openkm.core.NoSuchGroupException;
import com.openkm.core.NoSuchPropertyException;
import com.openkm.core.ParseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.module.ModuleManager;
import com.openkm.module.PropertyGroupModule;
import com.openkm.ws.util.FormElementArray;
import com.openkm.ws.util.PropertyGroupArray;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMPropertyGroup"
 * @web.servlet-mapping url-pattern="/OKMPropertyGroup"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@SecurityDomain("OpenKM")
public class OKMPropertyGroup {
	private static Logger log = LoggerFactory.getLogger(OKMPropertyGroup.class);

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#addGroup(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addGroup(String docPath, String grpName) throws NoSuchGroupException, LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("addGroup({}, {})", docPath, grpName);
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.addGroup(docPath, grpName);
		log.debug("addGroup: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#removeGroup(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void removeGroup(String docPath, String grpName) throws AccessDeniedException, 
			NoSuchGroupException, LockException, PathNotFoundException, RepositoryException,
			DatabaseException {
		log.debug("removeGroup({}, {})", docPath, grpName);
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.removeGroup(docPath, grpName);
		log.debug("removeGroup: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#getGroups(java.lang.String, java.lang.String)
	 */
	public PropertyGroupArray getGroups(String docPath) throws IOException, ParseException,
			PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getGroups({})", docPath);
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		PropertyGroupArray sa = new PropertyGroupArray();
		List<PropertyGroup> col = cm.getGroups(docPath);
		sa.setValue((PropertyGroup[]) col.toArray(new PropertyGroup[col.size()]));
		log.debug("getGroups: {}", sa);
		return sa;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#getAllGroups(java.lang.String)
	 */
	public PropertyGroupArray getAllGroups() throws IOException, ParseException, RepositoryException,
			DatabaseException {
		log.debug("getAllGroups()");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		PropertyGroupArray sa = new PropertyGroupArray();
		List<PropertyGroup> col = cm.getAllGroups();
		sa.setValue((PropertyGroup[]) col.toArray(new PropertyGroup[col.size()]));
		log.debug("getAllGroups: {} ", sa);
		return sa;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#getProperties(java.lang.String, java.lang.String, java.lang.String)
	 */
	public FormElementArray getProperties(String docPath, String grpName) throws IOException, 
			ParseException, NoSuchGroupException, PathNotFoundException, RepositoryException, 
			DatabaseException {
		log.debug("getProperties({}, {})", docPath, grpName);
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		FormElementArray fea = new FormElementArray();
		List<FormElement> fes = cm.getProperties(docPath, grpName);
		fea.setValue((FormElement[]) fes.toArray(new FormElement[fes.size()]));
		log.debug("getProperties: {}", fea);
		return fea;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyGroupModule#setProperties(java.lang.String, java.lang.String, java.lang.String, java.util.HashMap)
	 */
	public void setProperties(String docPath, String grpName, FormElementArray properties) throws 
			IOException, ParseException, NoSuchPropertyException, NoSuchGroupException, LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("setProperties({}, {}, {})", new Object[] { docPath, grpName, properties });
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		FormElement[] fes = properties.getValue();
		cm.setProperties(docPath, grpName, Arrays.asList(fes));
		log.debug("setProperties: void");
	}
}
