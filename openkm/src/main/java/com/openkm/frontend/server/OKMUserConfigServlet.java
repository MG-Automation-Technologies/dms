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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMUserConfig;
import com.openkm.core.DatabaseException;
import com.openkm.core.RepositoryException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTUserConfig;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMUserConfigService;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMUserConfigServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMUserConfigServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMUserConfigServlet extends OKMRemoteServiceServlet implements OKMUserConfigService {
	private static Logger log = LoggerFactory.getLogger(OKMUserConfigServlet.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	public void setUserHome(String path) throws OKMException {
		log.debug("setUserHome({})", path);
		
		try {
			OKMUserConfig.getInstance().setHome(path);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMUserCopyService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMUserCopyService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMUserCopyService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("setUserHome: void");
	}
	
	@Override
	public GWTUserConfig getUserHome() throws OKMException {
		log.debug("getUserHome()");
		
		try {
			return Util.copy(OKMUserConfig.getInstance().getConfig());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMUserCopyService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMUserCopyService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMUserCopyService, ErrorCode.CAUSE_General), e.getMessage());
		}
	}
}