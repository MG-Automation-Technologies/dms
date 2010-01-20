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
import com.openkm.bean.MetaData;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.LockException;
import com.openkm.core.NoSuchGroupException;
import com.openkm.core.NoSuchPropertyException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTMetaData;
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
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyGroupService#getAllGroups()
	 */
	public List<String> getAllGroups() throws OKMException {
		log.debug("getAllGroups()");
		List<String> groupList = new ArrayList<String>(); 
		String token = getToken();

		try {
			Collection<String> col = OKMPropertyGroup.getInstance().getAllGroups(token);
			
			for (Iterator<String> it = col.iterator(); it.hasNext();) {	
				String group = it.next();
				log.debug("Group: "+group);
				
				groupList.add(group);
			}

		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getAllGroups: "+groupList);
		return groupList;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyGroupService#getAllGroups(java.lang.String)
	 */
	public List<String> getAllGroups(String docPath) throws OKMException {
		log.debug("getAllGroups("+ docPath +")");
		List<String> groupList = new ArrayList<String>(); 
		String token = getToken();

		try {
			Collection<String> col = OKMPropertyGroup.getInstance().getAllGroups(token);
			List<String> actualGroupsList = getGroups(docPath);
			
			for (Iterator<String> it = col.iterator(); it.hasNext();) {	
				String group = it.next();
				log.debug("Group: "+group);
				
				groupList.add(group);
			}
			
			// Purge from list values that are assigned to document
			if (!actualGroupsList.isEmpty()) {
				for (Iterator<String> it = actualGroupsList.iterator(); it.hasNext();) {	
					String group = it.next();
					log.debug("Removing Group: "+group);
					
					groupList.remove(group);
				}
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (OKMException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getAllGroups: "+groupList);
		return groupList;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyGroupService#addGroup(java.lang.String, java.lang.String)
	 */
	public void addGroup(String docPath, String grpName) throws OKMException {
		log.debug("addGroup()");
		String token = getToken();
		
		try {
			OKMPropertyGroup.getInstance().addGroup(token, docPath, grpName);
			
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
		}  catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("addGroup: ");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyGroupService#getGroups(java.lang.String)
	 */
	public List<String> getGroups(String docPath) throws OKMException {
		log.debug("getGroups(" + docPath +")");
		List<String> groupList = new ArrayList<String>(); 
		String token = getToken();

		try {
			Collection<String> col = OKMPropertyGroup.getInstance().getGroups(token, docPath);
			
			for (Iterator<String> it = col.iterator(); it.hasNext();) {	
				String group = it.next();
				log.debug("Group: "+group);

				groupList.add(group);
			}
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getGroups: ");
		return groupList;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyGroupService#getTranslations(java.lang.String)
	 */
	public Map<String, String> getTranslations(String lang) throws OKMException {
		log.debug("getTranslations(" + lang +")");
		String token = getToken();
		Map<String, String> translations = new HashMap<String, String>();
		
		try {
			translations = OKMPropertyGroup.getInstance().getTranslations(token, lang);
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_IOException), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getTranslations: ");
		return translations;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyGroupService#getProperties(java.lang.String, java.lang.String)
	 */
	public Map<String, String[]> getProperties(String docPath, String grpName) throws OKMException {
		log.debug("getProperties(" + docPath + ", " + grpName +")");
		String token = getToken();
		Map<String, String[]> properties = new HashMap<String, String[]>();

		try {
			properties = OKMPropertyGroup.getInstance().getProperties(token, docPath, grpName);
			
		} catch (NoSuchGroupException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_NoSuchGroup), e.getMessage());
		}  catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getProperties: ");
		return properties;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyGroupService#getMetaData(java.lang.String)
	 */
	public Map<String, GWTMetaData> getMetaData(String grpName) throws OKMException {
		log.debug("getMetaData(" + grpName +")");
		String token = getToken();
		Map<String, MetaData> properties = new HashMap<String, MetaData>();
		Map<String, GWTMetaData> gwtProperties = new HashMap<String, GWTMetaData>();

		try {
			properties = OKMPropertyGroup.getInstance().getMetaData(token, grpName);
			Collection<String> col = properties.keySet();
			
			for (Iterator<String> it = col.iterator(); it.hasNext(); ) {
				String key = it.next();
				gwtProperties.put(key, Util.copy((MetaData) properties.get(key)));
			}
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_IOException), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getMetaData: ");
		return gwtProperties;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyGroupService#setProperties(java.lang.String, java.util.HashMap)
	 */
	public void setProperties(String docPath, String grpName, Map<String, String[]> properties) throws OKMException {
		log.debug("setProperties(" + docPath +")");
		String token = getToken();
		
		try {
			OKMPropertyGroup.getInstance().setProperties(token, docPath, grpName, (HashMap) properties);
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("setProperties: ");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMPropertyGroupService#removeGroup(java.lang.String, java.lang.String)
	 */
	public void removeGroup( String docPath, String grpName) throws OKMException  {
		log.debug("removeGroup(" + docPath + "," + grpName +")");
		String token = getToken();
		
		try {
			OKMPropertyGroup.getInstance().removeGroup(token, docPath, grpName);
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("removeGroup: ");
	}
}
