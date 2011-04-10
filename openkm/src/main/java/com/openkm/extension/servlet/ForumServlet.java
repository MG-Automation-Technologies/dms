/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

package com.openkm.extension.servlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import com.openkm.core.DatabaseException;
import com.openkm.extension.dao.ForumDAO;
import com.openkm.extension.dao.bean.Forum;
import com.openkm.extension.dao.bean.ForumTopic;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.extension.GWTForumPost;
import com.openkm.frontend.client.bean.extension.GWTForumTopic;
import com.openkm.frontend.client.contants.service.ErrorCode;
import com.openkm.frontend.client.service.extension.OKMForumService;
import com.openkm.servlet.frontend.OKMRemoteServiceServlet;
import com.openkm.util.GWTUtil;

/**
 * ForumServlet
 * 
 * @author jllort
 *
 */
public class ForumServlet extends OKMRemoteServiceServlet implements OKMForumService {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ForumServlet.class);
	
	@Override
	public List<GWTForumTopic> getTopicsByUuid(String uuid) throws OKMException {
		log.debug("getTopicsByUuid({})",uuid);
		List<GWTForumTopic> topicList = new ArrayList<GWTForumTopic>(); 
		
		try {
			for (ForumTopic topic : ForumDAO.findAllTopicByUuid(uuid)) {
				topicList.add(GWTUtil.copy(topic));
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMForumService, ErrorCode.CAUSE_Database), e.getMessage());
		}
		log.debug("getTopicsByUuid:"+topicList);
		return topicList;
	}
	
	@Override
	public GWTForumTopic createTopic(int id, String uuid, GWTForumTopic topic) throws OKMException {
		log.debug("createTopic({}, {}, {})", new Object[] {id, uuid, topic});
		try {
			Forum forum = ForumDAO.findByPk(id);
			topic.setDate(new Date());
			topic.setLastDate(topic.getDate());
			topic.setUuid(uuid);
			topic.setUser(getThreadLocalRequest().getRemoteUser());
			topic.setLastUser(topic.getUser());
			topic.setReplies(0);
			topic.setViews(0);
			GWTForumPost post = topic.getPosts().iterator().next();
			post.setDate(topic.getDate());
			post.setUser(topic.getUser());
			forum.getTopics().add(GWTUtil.copy(topic));
			ForumDAO.update(forum);
			ForumTopic ft = new ForumTopic();
			for (Iterator<ForumTopic> it = forum.getTopics().iterator(); it.hasNext();) {
				ft = it.next();
			}
			log.debug("getTopicsByUuid:"+ft);
			return GWTUtil.copy(ft);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMForumService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public GWTForumTopic findTopicByPK(int id) throws OKMException {
		log.debug("findTopicByPK({})",id);
		try {
			return GWTUtil.copy(ForumDAO.findTopicByPk(id));
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMForumService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public void createPost(int id, GWTForumPost post) throws OKMException {
		log.debug("createPost({},{})",id,post.getSubject());
		try {
			ForumTopic topic = ForumDAO.findTopicByPk(id);
			post.setDate(new Date());
			post.setUser(getThreadLocalRequest().getRemoteUser());
			topic.getPosts().add(GWTUtil.copy(post));
			topic.setReplies(topic.getReplies()+1);
			topic.setLastUser(post.getUser());
			Calendar cal = Calendar.getInstance();
			cal.setTime(post.getDate());
			topic.setLastDate(cal);
			ForumDAO.update(topic);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMForumService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
	
	@Override
	public void increaseTopicView(int id) throws OKMException {
		log.debug("increaseTopicView({})",id);
		try {
			ForumTopic topic = ForumDAO.findTopicByPk(id);
			topic.setViews(topic.getViews()+1);
			ForumDAO.update(topic);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMForumService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}

	@Override
	public void increateTopicReplies(int id) throws OKMException {
		log.debug("increateTopicReplies({})",id);
		try {
			ForumTopic topic = ForumDAO.findTopicByPk(id);
			topic.setReplies(topic.getReplies()+1);
			ForumDAO.update(topic);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMForumService, ErrorCode.CAUSE_Database), e.getMessage());
		}
	}
}