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

package com.openkm.frontend.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMPropertyGroup;
import com.openkm.bean.PropertyGroup;
import com.openkm.bean.form.FormElement;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.LockException;
import com.openkm.core.NoSuchGroupException;
import com.openkm.core.NoSuchPropertyException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTFormElement;
import com.openkm.frontend.client.bean.GWTPropertyGroup;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMPropertyGroupService;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMPropertyGroupServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMPropertyGroupServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMPropertyGroupServlet extends OKMRemoteServiceServlet implements OKMPropertyGroupService {
	private static Logger log = LoggerFactory.getLogger(OKMPropertyGroupServlet.class);
	private static final long serialVersionUID = 2638205115826644606L;
	
	@Override
	public List<GWTPropertyGroup> getAllGroups() throws OKMException {
		log.debug("getAllGroups()");
		List<GWTPropertyGroup> groupList = new ArrayList<GWTPropertyGroup>(); 

		try {
			Collection<PropertyGroup> col = OKMPropertyGroup.getInstance().getAllGroups();
			
			for (Iterator<PropertyGroup> it = col.iterator(); it.hasNext();) {	
				GWTPropertyGroup group = Util.copy(it.next());
				log.debug("Group: "+group);
				
				groupList.add(group);
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getAllGroups: {}", groupList);
		return groupList;
	}
	
	@Override
	public List<GWTPropertyGroup> getAllGroups(String docPath) throws OKMException {
		log.debug("getAllGroups({})", docPath);
		List<GWTPropertyGroup> groupList = new ArrayList<GWTPropertyGroup>(); 

		try {
			Collection<PropertyGroup> col = OKMPropertyGroup.getInstance().getAllGroups();
			List<GWTPropertyGroup> actualGroupsList = getGroups(docPath);
			
			for (Iterator<PropertyGroup> it = col.iterator(); it.hasNext();) {	
				GWTPropertyGroup group = Util.copy(it.next());
				log.debug("Group: {}", group);
				
				groupList.add(group);
			}
			
			// Purge from list values that are assigned to document
			if (!actualGroupsList.isEmpty()) {
				for (Iterator<GWTPropertyGroup> it = actualGroupsList.iterator(); it.hasNext();) {	
					GWTPropertyGroup group = it.next();
					log.debug("Removing Group: {}", group);
					
					for (Iterator<GWTPropertyGroup> itGroupList = groupList.iterator(); itGroupList.hasNext();) {
						GWTPropertyGroup groupListElement = itGroupList.next();
						if (groupListElement.getName().equals(group.getName())) {
							groupList.remove(groupListElement);
							break;
						}
					}
				}
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (OKMException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getAllGroups: {}", groupList);
		return groupList;
	}
	
	@Override
	public void addGroup(String docPath, String grpName) throws OKMException {
		log.debug("addGroup({}, {})", docPath, grpName);
		
		try {
			OKMPropertyGroup.getInstance().addGroup(docPath, grpName);
		} catch (NoSuchGroupException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_NoSuchGroup), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("addGroup: void");
	}
	
	@Override
	public List<GWTPropertyGroup> getGroups(String docPath) throws OKMException {
		log.debug("getGroups({})", docPath);
		List<GWTPropertyGroup> groupList = new ArrayList<GWTPropertyGroup>(); 

		try {
			Collection<PropertyGroup> col = OKMPropertyGroup.getInstance().getGroups(docPath);
			
			for (Iterator<PropertyGroup> it = col.iterator(); it.hasNext();) {	
				GWTPropertyGroup group = Util.copy(it.next());
				log.debug("Group: {}", group);

				groupList.add(group);
			}
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getGroups: {}", groupList);
		return groupList;
	}
	
	@Override
	public Map<String, String[]> getProperties(String docPath, String grpName) throws OKMException {
		log.debug("getProperties({}, {})", docPath, grpName);
		Map<String, String[]> properties = new HashMap<String, String[]>();

		try {
			properties = OKMPropertyGroup.getInstance().getProperties(docPath, grpName);
		} catch (NoSuchGroupException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_NoSuchGroup), e.getMessage());
		}  catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getProperties: {}", properties);
		return properties;
	}
	
	@Override
	public Collection<GWTFormElement> getMetaData(String grpName) throws OKMException {
		log.debug("getMetaData({})", grpName);
		Collection<FormElement> properties = new ArrayList<FormElement>();
		Collection<GWTFormElement> gwtProperties = new ArrayList<GWTFormElement>();

		try {
			properties = OKMPropertyGroup.getInstance().getPropertyGroupForm(grpName);
			
			for (Iterator<FormElement> it = properties.iterator(); it.hasNext(); ) {
				gwtProperties.add(Util.copy(it.next()));
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_IOException), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getMetaData: {}", gwtProperties);
		return gwtProperties;
	}
	
	@Override
	public void setProperties(String docPath, String grpName, Map<String, String[]> properties) throws OKMException {
		log.debug("setProperties({}, {}, {})", new Object[] { docPath, grpName, properties });
		
		try {
			OKMPropertyGroup.getInstance().setProperties(docPath, grpName, properties);
		} catch (NoSuchPropertyException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_NoSuchProperty), e.getMessage());
		} catch (NoSuchGroupException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_NoSuchGroup), e.getMessage());
		} catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("setProperties: void");
	}
	
	@Override
	public void removeGroup(String docPath, String grpName) throws OKMException  {
		log.debug("removeGroup({}, {})", docPath, grpName);
		
		try {
			OKMPropertyGroup.getInstance().removeGroup(docPath, grpName);
		} catch (NoSuchGroupException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_NoSuchGroup), e.getMessage());
		}  catch (LockException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Lock), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("removeGroup: void");
	}
	
	@Override
	public List<GWTPropertyGroup> getPropertyGroupWizard() throws OKMException {
		List<GWTPropertyGroup> allPropertyGroupList = getAllGroups();
		List<GWTPropertyGroup> wizardPropertyGroupsList = new ArrayList<GWTPropertyGroup>();
		
		for (int i=0; i<Config.WIZARD_PROPERTY_GROUPS.length; i++) {
			String propertyGroup = Config.WIZARD_PROPERTY_GROUPS[i];
			for (Iterator<GWTPropertyGroup> it = allPropertyGroupList.iterator(); it.hasNext();) {
				GWTPropertyGroup gWTPropertyGroup = it.next();
				if (gWTPropertyGroup.getName().equals(propertyGroup)) {
					wizardPropertyGroupsList.add(gWTPropertyGroup);
					break;
				}
			}
		}
		
		return wizardPropertyGroupsList;
	}
}
