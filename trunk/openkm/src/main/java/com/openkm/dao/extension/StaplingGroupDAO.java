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

package com.openkm.dao.extension;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.HibernateUtil;
import com.openkm.dao.bean.extension.StaplingGroup;

public class StaplingGroupDAO {
	private static Logger log = LoggerFactory.getLogger(StaplingGroupDAO.class);

	private StaplingGroupDAO() {}
	
	/**
	 * Create
	 */
	public static int create(StaplingGroup sg) throws DatabaseException {
		log.debug("create({})", sg);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Integer id = (Integer) session.save(sg);
			HibernateUtil.commit(tx);
			log.debug("create: {}" + id);
			return id.intValue();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Delete
	 */
	public static void delete(int sgId) throws DatabaseException {
		log.debug("delete({})", sgId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			StaplingGroup sg = (StaplingGroup) session.load(StaplingGroup.class, sgId);
			session.delete(sg);
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
	 * Find all stapling groups
	 */
	@SuppressWarnings("unchecked")
	public static List<StaplingGroup> findAll(String uuid) throws DatabaseException,
			RepositoryException {
		log.debug("findAll({}, {})", uuid);
		String qs = "select sg from StaplingGroup sg, Stapling st where st.uuid=:uuid and st in elements(sg.staplings)";		
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("uuid", uuid);
			List<StaplingGroup> ret = q.list();

			log.debug("findAll: {}", ret);
			return ret;
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}	
	
	/**
	 * Find by pk
	 */
	public static StaplingGroup findByPk(int sgId) throws DatabaseException {
		log.debug("findByPk({})", sgId);
		String qs = "from StaplingGroup sg where sg.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", sgId);
			StaplingGroup ret = (StaplingGroup) q.setMaxResults(1).uniqueResult();
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Update
	 */
	public static void update(StaplingGroup sg) throws DatabaseException {
		log.debug("update({})", sg);
		String qs = "select sg.username from StaplingGroup sg where sg.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setParameter("id", sg.getId());
			String username = (String) q.setMaxResults(1).uniqueResult();
			sg.setUsername(username);
			session.update(sg);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("update: void");
	}
}