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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.core.DatabaseException;
import com.openkm.dao.MessageDAO;
import com.openkm.dao.bean.MessageReceived;
import com.openkm.dao.bean.MessageSent;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.extension.GWTMessageReceived;
import com.openkm.frontend.client.bean.extension.GWTMessageSent;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.extension.OKMMessageService;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.servlet.frontend.OKMRemoteServiceServlet;
import com.openkm.util.GWTUtil;

/**
 * MessageServlet
 * 
 * @author jllort
 *
 */
public class MessageServlet extends OKMRemoteServiceServlet implements OKMMessageService {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(MessageServlet.class);
	
	@Override
	public void send(String users, String roles, String subject, String content) throws OKMException {
		Object obj[] = {(Object)users, (Object)roles,(Object)subject,  (Object)content};
		log.debug("send({}, {}, {}, {})", obj);
		
		try {
			String remoteUser = getThreadLocalRequest().getRemoteUser();
			String to = "";
			if (!users.equals("") && !roles.equals("")) {
				to = users + "," + roles;
			} else {
				to = users + roles;
			}
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
				MessageDAO.send(remoteUser, to, user, subject, content);
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_PrincipalAdapter), e.getMessage());
		}
	}
	
	@Override
	public List<GWTMessageSent> findSentByUser() throws OKMException {
		log.debug("findSentByUser()");
		List<GWTMessageSent> ms = new ArrayList<GWTMessageSent>();
		
		try {
			for (MessageSent message : MessageDAO.findSentByUser(getThreadLocalRequest().getRemoteUser())) {
				ms.add(GWTUtil.copy(message));
			}
			return ms;
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public List<GWTMessageReceived> findReceivedByUser() throws OKMException {
		log.debug("findReceivedByUser()");
		List<GWTMessageReceived> mr = new ArrayList<GWTMessageReceived>();
		
		try {
			for (MessageReceived message : MessageDAO.findReceivedByUser(getThreadLocalRequest().getRemoteUser())) {
				mr.add(GWTUtil.copy(message));
			}
			return mr;
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public void deleteReceived(int msgId) throws OKMException {
		log.debug("deleteReceived({})", msgId);
		try {
			MessageDAO.deleteReceived(msgId);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public void deleteSent(int msgId) throws OKMException {
		log.debug("deleteSent({})", msgId);
		try {
			MessageDAO.deleteSent(msgId);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public void markSeen(int msgId) throws OKMException {
		log.debug("markSeen({})", msgId);
		try {
			MessageDAO.markSeen(msgId);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}

	@Override
	public void deleteAllByReceiver(String user) throws OKMException {
	}

	@Override
	public void deleteAllBySender(String user) throws OKMException {
	}
	
}