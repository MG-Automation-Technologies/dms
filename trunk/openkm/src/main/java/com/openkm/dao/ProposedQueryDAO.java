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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.ProposedQuery;
import com.openkm.dao.bean.QueryParams;

public class ProposedQueryDAO {
	private static Logger log = LoggerFactory.getLogger(ProposedQueryDAO.class);

	private ProposedQueryDAO() {}
	
	/**
	 * Create
	 */
	public static void create(int qpId, ProposedQuery pq) throws DatabaseException {
		log.debug("create({}, {})", qpId, pq);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			if (pq.getSentDate() == null) pq.setSentDate(Calendar.getInstance());
			QueryParams qp = (QueryParams) session.get(QueryParams.class, qpId);
			qp.getProposed().add(pq);
			session.save(qp);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("create: void");
	}
	
	/**
	 * Delete
	 */
	public static void delete(int pqId) throws DatabaseException {
		log.debug("delete({})", pqId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			ProposedQuery pq = (ProposedQuery) session.load(ProposedQuery.class, pqId);
			session.delete(pq);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("delete: void");
	}
	
	/**
	 * Find by user
	 */
	@SuppressWarnings("unchecked")
	public static List<String> findProposedQueriesUsersFrom(String usrId) throws DatabaseException {
		log.debug("findProposedQueriesUsersFrom({})", usrId);
		String qs = "select distinct(pq.from) from ProposedQuery pq where pq.user=:me order by pq.from";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("user", usrId);
			List<String> ret = q.list();
			log.debug("findProposedQueriesUsersFrom: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Find by pk
	 */
	public static ProposedQuery findByPk(int pqId) throws DatabaseException {
		log.debug("findByPk({})", pqId);
		String qs = "from ProposedQuery pq where pq.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setInteger("id", pqId);
			ProposedQuery ret = (ProposedQuery) q.setMaxResults(1).uniqueResult();
			HibernateUtil.commit(tx);
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Return a map users and number of unread proposed queries from them
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Long> findProposedQueriesUsersFromUnread(String me) throws DatabaseException {
		log.debug("findProposedQueriesUsersFromUnread({})", me);
		String qs = "select pq.from, count(pq.from) from ProposedQuery pq " + 
			"group by pq.from, pq.user, pq.seenDate having pq.seenDate is null and pq.user=:me";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("me", me);
			List<Object[]> list =  q.list();
			Map<String, Long> ret = new HashMap<String, Long>();
			
			for (Object[] item : list) {
				ret.put((String) item[0], (Long) item[1]);
			} 
			
			log.debug("findProposedQueriesUsersFromUnread: {}", ret);
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
	public static List<ProposedQuery> findProposedQueryByMeFromUser(String me, String user) throws DatabaseException {
		log.debug("findProposedQueryByMeFromUser({})", user);
		String qs = "from ProposedQuery pq where pq.from=:user and pq.user=:me order by msg.id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("me", me);
			q.setString("user", user);
			List<ProposedQuery> ret = q.list();
			log.debug("findProposedQueryByMeFromUser: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Mark proposed as seen
	 */
	public static void markSeen(int pqId) throws DatabaseException {
		log.debug("markSeen({})", pqId);
		String qs = "update ProposedQuery pq set pq.seenDate=:seenDate where pq.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", pqId);
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
	public static void markAccepted(int pqId) throws DatabaseException {
		log.debug("markAccepted({})", pqId);
		String qs = "update ProposedQuery ps set ps.accepted=:accepted where ps.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", pqId);
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
