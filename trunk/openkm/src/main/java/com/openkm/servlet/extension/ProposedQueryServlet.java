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

package com.openkm.servlet.extension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.ProposedQueryDAO;
import com.openkm.dao.QueryParamsDAO;
import com.openkm.dao.bean.ProposedQuery;
import com.openkm.dao.bean.QueryParams;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.extension.GWTProposedQuery;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.extension.OKMProposedQueryService;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.servlet.frontend.OKMRemoteServiceServlet;
import com.openkm.util.GWTUtil;

/**
 * ProposedQueryServlet
 * 
 * @author jllort
 *
 */
public class ProposedQueryServlet extends OKMRemoteServiceServlet implements OKMProposedQueryService {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ProposedQueryServlet.class);
	
	@Override
	public void create(int qpId, String users, String roles, String comment) throws OKMException {
		Object obj[] = {(Object)qpId, (Object)users, (Object)roles, (Object)comment};
		log.debug("create({}, {}, {}, {})", obj);
		
		try {
			String remoteUser = getThreadLocalRequest().getRemoteUser();
			List<String> userNames = new ArrayList<String>(Arrays.asList(users.split(",")));
			List<String> roleNames = Arrays.asList(roles.split(","));
			
			for (String role : roleNames) {
				List<String> usersInRole = OKMAuth.getInstance().getUsersByRole(null, role);
				
				for (String user : usersInRole) {
					if (!userNames.contains(user)) {
						userNames.add(user);
					}
				}
			}
			
			for (String user : userNames) {
				ProposedQuery pq = new ProposedQuery();
				pq.setFrom(remoteUser);
				pq.setTo(user);
				pq.setComment(comment);
				ProposedQueryDAO.create(qpId, pq);
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedQueryService, ErrorCode.CAUSE_Database), e.getMessage());
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedQueryService, ErrorCode.CAUSE_PrincipalAdapter), e.getMessage());
		}
	}

	@Override
	public List<GWTProposedQuery> findAll() throws OKMException {
		log.debug("findAll()");
		updateSessionManager();
		List<GWTProposedQuery> pqList = new ArrayList<GWTProposedQuery>();
		try {
			String user = getThreadLocalRequest().getRemoteUser();
			for (QueryParams params : QueryParamsDAO.findProposed(user)) {
				for (ProposedQuery proposed : params.getProposed()) {
					if (proposed.getTo().equals(user)) {
						pqList.add(GWTUtil.copy(proposed, params));
					}
				}
			} 
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedQueryService, ErrorCode.CAUSE_Database), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedQueryService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedQueryService, ErrorCode.CAUSE_IO), e.getMessage());
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedQueryService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedQueryService, ErrorCode.CAUSE_Parse), e.getMessage());
		}
		log.debug("findAll: List"+pqList);
		return pqList;
	}

	@Override
	public void delete(int msgId) throws OKMException {
		log.debug("delete({})", msgId);
		updateSessionManager();
		try {
			ProposedQueryDAO.delete(msgId);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedQueryService, ErrorCode.CAUSE_Database), e.getMessage());
		}
		log.debug("delete() : void");
	}

	@Override
	public void deleteAllBySender(String sender) throws OKMException {
		log.debug("deleteAllBySender()");
		updateSessionManager();
		List<String> IdToDelete = new ArrayList<String>();
		try {			
			for (ProposedQuery pq : ProposedQueryDAO.findByUser(getThreadLocalRequest().getRemoteUser())) {
				if (pq.getFrom().equals(sender)) {
					IdToDelete.add(String.valueOf(pq.getId()));
				}
			}
			for (String id : IdToDelete) {
				ProposedQueryDAO.delete(Integer.valueOf(id));
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedQueryService, ErrorCode.CAUSE_Database), e.getMessage());
		} 
		log.debug("deleteAllBySender: void");
	}

	@Override
	public void markAccepted(int msgId) throws OKMException {
		log.debug("markAccepted({})", msgId);
		updateSessionManager();
		try {
			ProposedQueryDAO.markAccepted(msgId);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedQueryService, ErrorCode.CAUSE_Database), e.getMessage());
		}
		log.debug("markAccepted() : void");
	}

	@Override
	public void markSeen(int msgId) throws OKMException {
		log.debug("markSeen({})", msgId);
		updateSessionManager();
		try {
			ProposedQueryDAO.markSeen(msgId);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMProposedQueryService, ErrorCode.CAUSE_Database), e.getMessage());
		}
		log.debug("markSeen() : void");
	}
}
	