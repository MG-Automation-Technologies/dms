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

package com.openkm.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.NodeGrants;

public class NodeGrantsDAO  {
	private static Logger log = LoggerFactory.getLogger(NodeGrantsDAO.class);
	private NodeGrantsDAO() {}
	
	/**
	 * Create
	 */
	public static void create(NodeGrants ng) throws DatabaseException {
	    Session session = null;
	    Transaction tx = null;
	    
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
	    	session.save(ng);
	    	HibernateUtil.commit(tx);
	    } catch (HibernateException e) {
	    	HibernateUtil.rollback(tx);
	    	throw new DatabaseException(e.getMessage(), e);
	    } finally {
	    	HibernateUtil.close(session);
	    }
	}
	
	/**
	 * Update
	 */
	public static void update(NodeGrants ng) throws DatabaseException {
		log.debug("update({})", ng);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(ng);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("update: void");
	}
	
	/**
	 * Delete
	 */
	public static void delete(String uuid) throws DatabaseException {
		log.debug("delete({})", uuid);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			NodeGrants ng = (NodeGrants) session.load(NodeGrants.class, uuid);
			session.delete(ng);
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
	 * Find by pk
	 */
	public static NodeGrants findByPk(String uuid) throws DatabaseException {
		log.debug("findByPk({})", uuid);
		String qs = "from NodeGrants ng where ng.uuid=:uuid";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setString("uuid", uuid);
			NodeGrants ret = (NodeGrants) q.setMaxResults(1).uniqueResult();
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
}
