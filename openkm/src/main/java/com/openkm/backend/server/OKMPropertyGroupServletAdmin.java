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

package com.openkm.backend.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMPropertyGroup;
import com.openkm.backend.client.OKMException;
import com.openkm.backend.client.bean.GWTFormElement;
import com.openkm.backend.client.config.ErrorCode;
import com.openkm.backend.client.service.OKMPropertyGroupService;
import com.openkm.bean.form.FormElement;
import com.openkm.core.RepositoryException;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMPropertyGroupServletAdmin"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMPropertyGroupServletAdmin"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMPropertyGroupServletAdmin extends OKMRemoteServiceServletAdmin implements OKMPropertyGroupService {
	private static Logger log = LoggerFactory.getLogger(OKMPropertyGroupServletAdmin.class);
	private static final long serialVersionUID = 2638205115826644606L;

	@Override
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
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupServiceAdmin, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupServiceAdmin, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getAllGroups: "+groupList);
		return groupList;
	}

	@Override
	public Collection<GWTFormElement> getMetaData(String grpName) throws OKMException {
		log.debug("getMetaData(" + grpName +")");
		String token = getToken();
		Collection<FormElement> properties = new ArrayList<FormElement>();
		Collection<GWTFormElement> gwtProperties = new ArrayList<GWTFormElement>();

		try {
			properties = OKMPropertyGroup.getInstance().getPropertyGroupForm(token, grpName);
			
			for (Iterator<FormElement> it = properties.iterator(); it.hasNext(); ) {
				FormElement fe = it.next();
				gwtProperties.add(Util.copy(fe));
			}
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupServiceAdmin, ErrorCode.CAUSE_IOException), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupServiceAdmin, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMPropertyGroupServiceAdmin, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getMetaData: ");
		
		return gwtProperties;
	}
}
