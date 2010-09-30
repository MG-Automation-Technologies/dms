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

package com.openkm.frontend.server.extension;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.bean.extension.Stapling;
import com.openkm.dao.bean.extension.StaplingGroup;
import com.openkm.dao.extension.StaplingGroupDAO;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.extension.GWTStaplingGroup;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.extension.OKMStaplingService;
import com.openkm.frontend.server.OKMRemoteServiceServlet;
import com.openkm.frontend.server.Util;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMStaplingServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMStaplingServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMStaplingServlet extends OKMRemoteServiceServlet implements OKMStaplingService {
	private static Logger log = LoggerFactory.getLogger(OKMStaplingServlet.class);
	private static final long serialVersionUID = 395857404418870245L;
	
	@Override
	public String create(String username, String uuid, String uuid2) throws OKMException {
		StaplingGroup staplingGroup = new StaplingGroup();
		staplingGroup.setUsername(username);
		try {
			// Creating stapling group
			int id = StaplingGroupDAO.create(staplingGroup);
			
			// Adding stapling elements
			staplingGroup = StaplingGroupDAO.findByPk(id);
			Stapling stapling = new Stapling();
			stapling.setUuid(uuid);
			staplingGroup.getStaplings().add(stapling); // Added first
			stapling = new Stapling();
			stapling.setUuid(uuid2);
			staplingGroup.getStaplings().add(stapling); // Added second
			StaplingGroupDAO.update(staplingGroup); 	// Updating
			return String.valueOf(id);
		} catch (DatabaseException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStaplingService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		}
	}
	
	@Override
	public void add(String id, String uuid) throws OKMException {
		try {
			StaplingGroup staplingGroup = StaplingGroupDAO.findByPk(Integer.valueOf(id));
			Stapling stapling = new Stapling();
			stapling.setUuid(uuid);
			staplingGroup.getStaplings().add(stapling); // Added first
			StaplingGroupDAO.update(staplingGroup); 	// Updating
		} catch (NumberFormatException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStaplingService, ErrorCode.CAUSE_NumberFormatException), e.getMessage());
		} catch (DatabaseException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStaplingService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		}
	}
	
	@Override
	public List<GWTStaplingGroup> getAll(String uuid) throws OKMException {
		List<GWTStaplingGroup> stapList = new ArrayList<GWTStaplingGroup>();
		try {
			for (StaplingGroup sg : StaplingGroupDAO.findAll(uuid)) {
				stapList.add(Util.copy(sg));
			}
		} catch (DatabaseException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStaplingService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (RepositoryException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStaplingService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStaplingService, ErrorCode.CAUSE_AccessDenied), e.getMessage());
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStaplingService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		}
		return stapList;
	}
	
	
	@Override
	public void remove(String id) throws OKMException {
		try {
			StaplingGroupDAO.delete(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStaplingService, ErrorCode.CAUSE_NumberFormatException), e.getMessage());
		} catch (DatabaseException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMStaplingService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		}
	}
}
