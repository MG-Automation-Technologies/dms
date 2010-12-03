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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.MessageDAO;
import com.openkm.dao.ProposedQueryDAO;
import com.openkm.dao.QueryParamsDAO;
import com.openkm.dao.bean.MessageReceived;
import com.openkm.dao.bean.MessageSent;
import com.openkm.dao.bean.ProposedQuerySent;
import com.openkm.dao.bean.QueryParams;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.extension.GWTMessageReceived;
import com.openkm.frontend.client.bean.extension.GWTMessageSent;
import com.openkm.frontend.client.bean.extension.GWTProposedQuerySent;
import com.openkm.frontend.client.bean.extension.GWTTextMessageSent;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.extension.OKMMessageService;
import com.openkm.frontend.client.util.GWTMessageSentComparator;
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
		updateSessionManager();
		
		try {
			String remoteUser = getThreadLocalRequest().getRemoteUser();
			String to = "";
			if (!users.equals("") && !roles.equals("")) {
				to = users + "," + roles;
			} else {
				to = users + roles;
			}
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
	public List<String> findSentUsersTo() throws OKMException {
		log.debug("findSentUsersTo()");
		updateSessionManager();
		try {
			String me = getThreadLocalRequest().getRemoteUser();
			List<String> usersList = ProposedQueryDAO.findProposedQuerySentUsersTo(me);
			usersList.addAll(MessageDAO.findSentUsersTo(me));
			return usersList;
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public Map<String,Long> findReceivedUsersFrom() throws OKMException {
		log.debug("findReceivedUsersFrom()");
		Map<String,Long> received = new HashMap<String, Long>();
		updateSessionManager();
		try {
			String user = getThreadLocalRequest().getRemoteUser();
			Map<String, Long> unreadMap = MessageDAO.findReceivedUsersFromUnread(user);
			for (String sender : MessageDAO.findReceivedUsersFrom(getThreadLocalRequest().getRemoteUser())) {
				if (unreadMap.containsKey(sender)) {
					received.put(sender, unreadMap.get(sender));
				} else {
					received.put(sender, new Long(0));
				}
			}
			return received;
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public List<GWTMessageSent> findSentFromMeToUser(String user) throws OKMException {
		log.debug("findSentFromMeToUser({})",user);
		List<GWTMessageSent> messageSentList = new ArrayList<GWTMessageSent>();
		updateSessionManager();
		try {
			String me = getThreadLocalRequest().getRemoteUser();
			for (MessageSent messageSent : MessageDAO.findSentFromMeToUser(me, user)) {
				GWTTextMessageSent textMessageSent = new GWTTextMessageSent();
				textMessageSent = GWTUtil.copy(messageSent);
				GWTMessageSent message = new GWTMessageSent();
				message.setTestMessageSent(textMessageSent);
				message.setSentDate(textMessageSent.getSentDate());
				messageSentList.add(message);
			}
			for (QueryParams queryParams : QueryParamsDAO.findProposedQueryFromMeToUser(me, user)) {
				for (ProposedQuerySent proposedQuerySent : queryParams.getProposedSent()) {
					GWTProposedQuerySent gWTproposedQuerySent  = GWTUtil.copy(proposedQuerySent, queryParams);	
					GWTMessageSent message = new GWTMessageSent();
					message.setProposedQuerySent(gWTproposedQuerySent);
					message.setSentDate(gWTproposedQuerySent.getSentDate());
					messageSentList.add(message);
				}
			}
			Collections.sort(messageSentList, GWTMessageSentComparator.getInstance());
			return messageSentList;
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_IO), e.getMessage());
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Parse), e.getMessage());
		}
	}
	
	@Override
	public List<GWTMessageReceived> findReceivedByMeFromUser(String user) throws OKMException {
		log.debug("findSentFromMeToUser({})",user);
		List<GWTMessageReceived> messageReceivedList = new ArrayList<GWTMessageReceived>();
		updateSessionManager();
		try {
			for (MessageReceived messageReceived: MessageDAO.findReceivedByMeFromUser(getThreadLocalRequest().getRemoteUser(), user)) {
				messageReceivedList.add(GWTUtil.copy(messageReceived));
			}
			return messageReceivedList;
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public void deleteReceived(int msgId) throws OKMException {
		log.debug("deleteReceived({})", msgId);
		updateSessionManager();
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
		updateSessionManager();
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
		updateSessionManager();
		try {
			MessageDAO.markSeen(msgId);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public void deleteSentFromMeToUser(String user) throws OKMException {
		log.debug("deleteSentFromMeToUser({})",user);
		List<String> msgId = new ArrayList<String>();
		updateSessionManager();
		try {
			for (MessageSent messageSent :MessageDAO.findSentFromMeToUser(getThreadLocalRequest().getRemoteUser(), user)) {
				msgId.add(String.valueOf(messageSent.getId()));
			}
			for (String id : msgId) {
				MessageDAO.deleteSent(Integer.valueOf(id));
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public void deleteReceivedByMeFromUser(String user) throws OKMException {
		log.debug("deleteReceivedByMeFromUser({})",user);
		List<String> msgId = new ArrayList<String>();
		updateSessionManager();
		try {
			for (MessageReceived messageReceived :MessageDAO.findReceivedByMeFromUser(getThreadLocalRequest().getRemoteUser(), user)) {
				msgId.add(String.valueOf(messageReceived.getId()));
			}
			for (String id : msgId) {
				MessageDAO.deleteReceived(Integer.valueOf(id));
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMMessageService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
}