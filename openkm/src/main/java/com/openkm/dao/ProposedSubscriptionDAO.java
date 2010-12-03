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

import javax.jcr.Node;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Folder;
import com.openkm.bean.Repository;
import com.openkm.core.DatabaseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.bean.MessageReceived;
import com.openkm.dao.bean.MessageSent;
import com.openkm.dao.bean.ProposedSubscriptionReceived;
import com.openkm.dao.bean.ProposedSubscriptionSent;
import com.openkm.util.JCRUtils;

public class ProposedSubscriptionDAO {
	private static Logger log = LoggerFactory.getLogger(ProposedSubscriptionDAO.class);

	private ProposedSubscriptionDAO() {}
	
	/**
	 * Send proposed subscription
	 */
	public static void send(String from, String to, String user, String uuid, String comment) throws 
			DatabaseException {
		log.debug("send({}, {}, {}, {}, {})", new Object[] { from, to, user, uuid, comment });
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();		
			
			ProposedSubscriptionSent psSent = new ProposedSubscriptionSent();
			psSent.setFrom(from);
			psSent.setTo(to);
			psSent.setUser(user);
			psSent.setUuid(uuid);
			psSent.setComment(comment);
			psSent.setSentDate(Calendar.getInstance());
			session.save(psSent);
			
			ProposedSubscriptionReceived psReceived = new ProposedSubscriptionReceived();
			psReceived.setFrom(from);
			psReceived.setTo(to);
			psReceived.setUser(user);
			psSent.setUuid(uuid);
			psSent.setComment(comment);
			psReceived.setSentDate(Calendar.getInstance());
			session.save(psReceived);
			
			HibernateUtil.commit(tx);
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("send: void");
	}
	
	/**
	 * Delete sent proposed subscription
	 */
	public static void deleteSent(int psId) throws DatabaseException {
		log.debug("deleteSent({})", psId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			ProposedSubscriptionSent ps = (ProposedSubscriptionSent) session.load(ProposedSubscriptionSent.class, psId);
			session.delete(ps);
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
	 * Delete received proposed subscription
	 */
	public static void deleteReceived(int psId) throws DatabaseException {
		log.debug("deleteReceived({})", psId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			ProposedSubscriptionReceived ps = (ProposedSubscriptionReceived) session.load(ProposedSubscriptionReceived.class, psId);
			session.delete(ps);
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
	 * Finde by user
	 */
	@SuppressWarnings("unchecked")
	public static List<ProposedSubscription> findByUser(javax.jcr.Session jcrSession, String usrId) throws 
			DatabaseException, RepositoryException {
		log.debug("findByUser({}, {})", jcrSession, usrId);
		String qs = "from ProposedSubscription ps where ps.to=:user order by ps.id";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setString("user", usrId);
			List<ProposedSubscription> ret = q.list();
			
			for (ProposedSubscription ps : ret) {
				try {
					Node node = jcrSession.getNodeByUUID(ps.getUuid());
					String nType = JCRUtils.getNodeType(node);
					ps.setPath(node.getPath());
					
					// TODO Se supone que el tipo no cambia
					if (!nType.equals(ps.getType())) {
						ps.setType(JCRUtils.getNodeType(node));
						session.update(ps);
					}
				} catch (javax.jcr.ItemNotFoundException e) {
					// If user bookmark is missing, set a default
					Node okmRoot = jcrSession.getRootNode().getNode(Repository.ROOT);
					ps.setPath(okmRoot.getPath());
					ps.setUuid(okmRoot.getUUID());
					ps.setType(Folder.TYPE);
					session.save(ps);
				}
			}
			
			HibernateUtil.commit(tx);
			log.debug("findByUser: {}", ret);
			return ret;
		} catch (javax.jcr.RepositoryException e) {
			HibernateUtil.rollback(tx);
			throw new RepositoryException(e.getMessage(), e);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Mark proposed as seen
	 */
	public static void markSeen(int psId) throws DatabaseException {
		log.debug("markSeen({})", psId);
		String qs = "update ProposedSubscriptionReceived ps set ps.seenDate=:seenDate where ps.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", psId);
			q.setCalendar("seenDate", Calendar.getInstance());
			q.executeUpdate();
			log.debug("markSeen: void");
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Mark proposed as accepted
	 */
	public static void markAccepted(int psId) throws DatabaseException {
		log.debug("markAccepted({})", psId);
		String qs = "update ProposedSubscriptionReceived ps set ps.accepted=:accepted where ps.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", psId);
			q.setBoolean("accepted", true);
			q.executeUpdate();
			log.debug("markAccepted: void");
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
