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

import javax.jws.WebMethod;
import javax.jws.WebParam;
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

	@WebMethod
	public void addGroup(@WebParam(name = "docPath") String docPath,
			@WebParam(name = "grpName") String grpName) throws NoSuchGroupException, LockException,
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("addGroup({}, {})", docPath, grpName);
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.addGroup(docPath, grpName);
		log.debug("addGroup: void");
	}

	@WebMethod
	public void removeGroup(@WebParam(name = "docPath") String docPath,
			@WebParam(name = "grpName") String grpName) throws AccessDeniedException, 
			NoSuchGroupException, LockException, PathNotFoundException, RepositoryException,
			DatabaseException {
		log.debug("removeGroup({}, {})", docPath, grpName);
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.removeGroup(docPath, grpName);
		log.debug("removeGroup: void");
	}

	@WebMethod
	public PropertyGroup[] getGroups(@WebParam(name = "docPath") String docPath) throws IOException,
			ParseException, PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getGroups({})", docPath);
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		List<PropertyGroup> col = cm.getGroups(docPath);
		PropertyGroup[] result = (PropertyGroup[]) col.toArray(new PropertyGroup[col.size()]);
		log.debug("getGroups: {}", result);
		return result;
	}

	@WebMethod
	public PropertyGroup[] getAllGroups() throws IOException, ParseException, RepositoryException,
			DatabaseException {
		log.debug("getAllGroups()");
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		List<PropertyGroup> col = cm.getAllGroups();
		PropertyGroup[] result = (PropertyGroup[]) col.toArray(new PropertyGroup[col.size()]);
		log.debug("getAllGroups: {} ", result);
		return result;
	}

	@WebMethod
	public FormElement[] getProperties(@WebParam(name = "docPath") String docPath,
			@WebParam(name = "grpName") String grpName) throws IOException, ParseException,
			NoSuchGroupException, PathNotFoundException, RepositoryException, DatabaseException {
		log.debug("getProperties({}, {})", docPath, grpName);
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		List<FormElement> col = cm.getProperties(docPath, grpName);
		FormElement[] result = (FormElement[]) col.toArray(new FormElement[col.size()]);
		log.debug("getProperties: {}", result);
		return result;
	}
	
	@WebMethod
	public void setProperties(@WebParam(name = "docPath") String docPath,
			@WebParam(name = "grpName") String grpName,
			@WebParam(name = "properties") FormElement[] properties) throws IOException, ParseException,
			NoSuchPropertyException, NoSuchGroupException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("setProperties({}, {}, {})", new Object[] { docPath, grpName, properties });
		PropertyGroupModule cm = ModuleManager.getPropertyGroupModule();
		cm.setProperties(docPath, grpName, Arrays.asList(properties));
		log.debug("setProperties: void");
	}
}
