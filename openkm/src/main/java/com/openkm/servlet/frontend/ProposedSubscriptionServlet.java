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

package com.openkm.servlet.frontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.core.DatabaseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.ProposedSubscriptionDAO;
import com.openkm.dao.bean.ProposedSubscription;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTProposedSubscription;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMProposedSubscriptionService;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.util.GWTUtil;
import com.openkm.util.JCRUtils;

/**
 * ProposedSubscriptionServlet
 * 
 * @author jllort
 *
 */
public class ProposedSubscriptionServlet extends OKMRemoteServiceServlet implements OKMProposedSubscriptionService {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ProposedSubscriptionServlet.class);
	
	@Override
	public void create(String uuid, String path, String type, String users, String roles) throws OKMException {
		try {
			String remoteUser = getThreadLocalRequest().getRemoteUser();
			List<String> userNames = Arrays.asList(users.split(","));
			List<String> roleNames = Arrays.asList(roles.split(","));
			
			for (String role : roleNames) {
				List<String> usersInRole;
				usersInRole = OKMAuth.getInstance().getUsersByRole(null, role);
				
				for (String user : usersInRole) {
					if (!userNames.contains(user)) {
						userNames.add(user);
					}
				}
			}
			
			for (String user : userNames) {
				ProposedSubscription ps = new ProposedSubscription();
				ps.setFrom(remoteUser);
				ps.setTo(user);
				ps.setUuid(uuid);
				ps.setPath(path);
				ps.setType(type);
				ProposedSubscriptionDAO.create(ps);
			}
			
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedSubscriptionService, ErrorCode.CAUSE_Database), e.getMessage());
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedSubscriptionService, ErrorCode.CAUSE_PrincipalAdapter), e.getMessage());
		}
		
	}

	@Override
	public List<GWTProposedSubscription> findAll() throws OKMException {
		log.debug("findAll({}, {})");
		updateSessionManager();
		List<GWTProposedSubscription> psList = new ArrayList<GWTProposedSubscription>();
		Session session = null;
		try {
			session = JCRUtils.getSession();			
			for (ProposedSubscription ps : ProposedSubscriptionDAO.findByUser(session, getThreadLocalRequest().getRemoteUser())) {
				psList.add(GWTUtil.copy(ps));
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedSubscriptionService, ErrorCode.CAUSE_Database), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedSubscriptionService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedSubscriptionService, ErrorCode.CAUSE_Login), e.getMessage());
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedSubscriptionService, ErrorCode.CAUSE_Repository), e.getMessage());
		}finally {
			JCRUtils.logout(session);
		}
		log.debug("findAll: List"+psList);
		return psList;
	}
}
