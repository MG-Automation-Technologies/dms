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

import java.util.List;

import javax.jcr.Node;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.bean.Bookmark;
import com.openkm.util.JCRUtils;

public class BookmarkDAO {
	private static Logger log = LoggerFactory.getLogger(BookmarkDAO.class);

	private BookmarkDAO() {}
	
	/**
	 * Create
	 */
	public static void create(Bookmark bm) throws DatabaseException {
		log.debug("create({})", bm);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(bm);
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("create: void");
	}
	
	/**
	 * Update
	 */
	public static void update(Bookmark bm) throws DatabaseException {
		log.debug("update({})", bm);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(bm);
			tx.commit();
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
	public static void delete(int bmId) throws DatabaseException {
		log.debug("delete({})", bmId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Bookmark bm = (Bookmark) session.load(Bookmark.class, bmId);
			session.delete(bm);
			tx.commit();
		} catch (HibernateException e) {
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
	public static List<Bookmark> findByUser(javax.jcr.Session jcrSession, String usrId) throws DatabaseException,
			RepositoryException {
		log.debug("findByUser({}, {})", jcrSession, usrId);
		String qs = "from Bookmark bm where bm.user=:user order by bm.id";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setString("user", usrId);
			List<Bookmark> ret = q.list();
			
			for (Bookmark bm : ret) {
				Node node = jcrSession.getNodeByUUID(bm.getUuid());
				
				if (!node.getPath().equals(bm.getPath())) {
					bm.setPath(node.getPath());
					bm.setType(JCRUtils.getNodeType(node));
					session.update(bm);
				}
			}
			
			tx.commit();
			log.debug("findByUser: {}", ret);
			return ret;
		} catch (javax.jcr.RepositoryException e) {
			tx.rollback();
			throw new RepositoryException(e.getMessage(), e);
		} catch (HibernateException e) {
			tx.rollback();
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}


	/**
	 * Find by pk
	 */
	public static Bookmark findByPk(javax.jcr.Session jcrSession, int bmId) throws DatabaseException,
			RepositoryException {
		log.debug("findByPk({})", bmId);
		String qs = "from Bookmark bm where bm.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setInteger("id", bmId);
			Bookmark ret = (Bookmark) q.setMaxResults(1).uniqueResult();
			Node node = jcrSession.getNodeByUUID(ret.getUuid());
			
			if (!node.getPath().equals(ret.getPath())) {
				ret.setPath(node.getPath());
				ret.setType(JCRUtils.getNodeType(node));
				session.update(ret);
			}
			
			tx.commit();
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (javax.jcr.RepositoryException e) {
			tx.rollback();
			throw new RepositoryException(e.getMessage(), e);
		} catch (HibernateException e) {
			tx.rollback();
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
