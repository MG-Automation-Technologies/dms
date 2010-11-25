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

package com.openkm.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.MessageReceived;
import com.openkm.dao.bean.MessageSent;

public class MessageDAO {
	private static Logger log = LoggerFactory.getLogger(MessageDAO.class);

	private MessageDAO() {}
	
	/**
	 * Create message
	 */
	public static void send(String from, String to, String user, String subject, String content) throws 
			DatabaseException {
		log.debug("create({}, {}, {}, {}, {})", new Object[] { from, to, user, subject, content });
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();		
			
			MessageSent msgSent = new MessageSent();
			msgSent.setFrom(from);
			msgSent.setTo(to);
			msgSent.setSubject(subject);
			msgSent.setContent(content);
			session.save(msgSent);
			
			MessageReceived msgReceived = new MessageReceived();
			msgReceived.setFrom(from);
			msgReceived.setTo(to);
			msgReceived.setUser(user);
			msgReceived.setSubject(subject);
			msgReceived.setContent(content);
			msgReceived.setSentDate(Calendar.getInstance());
			session.save(msgReceived);
			
			HibernateUtil.commit(tx);
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("create: void");
	}
	
	/**
	 * Delete sent message
	 */
	public static void deleteSent(int msgId) throws DatabaseException {
		log.debug("deleteSent({})", msgId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			MessageSent msg = (MessageSent) session.load(MessageSent.class, msgId);
			session.delete(msg);
			HibernateUtil.commit(tx);
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("deleteSent: void");
	}
	
	/**
	 * Delete received message
	 */
	public static void deleteReceived(int msgId) throws DatabaseException {
		log.debug("deleteReceived({})", msgId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			MessageReceived msg = (MessageReceived) session.load(MessageReceived.class, msgId);
			session.delete(msg);
			HibernateUtil.commit(tx);
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("delete: void");
	}
	
	/**
	 * Find sent by user
	 */
	@SuppressWarnings("unchecked")
	public static List<MessageSent> findSentByUser(String user) throws DatabaseException {
		log.debug("findByUser({})", user);
		String qs = "from MessageSent msg where msg.from=:user order by msg.id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("user", user);
			List<MessageSent> ret = q.list();
			log.debug("findSentByUser: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	/**
	 * Find received by user
	 */
	@SuppressWarnings("unchecked")
	public static List<MessageReceived> findReceivedByUser(String user) throws DatabaseException {
		log.debug("findByUser({})", user);
		String qs = "from MessageReceived msg where msg.user=:user order by msg.id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("user", user);
			List<MessageReceived> ret = q.list();
			log.debug("findReceivedByUser: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Mark message as seen
	 */
	public static void markSeen(int msgId) throws DatabaseException {
		log.debug("markSeen({})", msgId);
		String qs = "update MessageReceived msg set msg.seenDate=:seenDate where msg.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", msgId);
			q.setCalendar("seenDate", Calendar.getInstance());
			q.executeUpdate();
			log.debug("markSeen: void");
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
