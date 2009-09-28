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

package es.git.openkm.backend.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.backend.client.OKMException;
import es.git.openkm.backend.client.bean.GWTStatsInfo;
import es.git.openkm.backend.client.service.OKMStatsService;
import es.git.openkm.core.RepositoryInfo;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMStatsServletAdmin"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMStatsServletAdmin"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMStatsServletAdmin extends OKMRemoteServiceServletAdmin implements OKMStatsService  {
	private static Logger log = LoggerFactory.getLogger(OKMStatsServletAdmin.class);
	private static final long serialVersionUID = -4436438730167948558L;

	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMStatsService#getFoldersByContext()
	 */
	public GWTStatsInfo getFoldersByContext() throws OKMException {
		log.debug("getFoldersByContext()");
		return Util.copy(RepositoryInfo.getFoldersByContext());
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMStatsService#getDocumentsByContext()
	 */
	public GWTStatsInfo getDocumentsByContext() throws OKMException {
		log.debug("getDocumentsByContext()");
		return Util.copy(RepositoryInfo.getDocumentsByContext());
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMStatsService#getSizeContext()
	 */
	public GWTStatsInfo getSizeContext() throws OKMException {
		log.debug("getSizeContext()");
		return Util.copy(RepositoryInfo.getSizeContext());
	}
}
