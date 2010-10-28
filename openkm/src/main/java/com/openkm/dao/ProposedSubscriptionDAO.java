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

import com.openkm.bean.Folder;
import com.openkm.bean.Repository;
import com.openkm.core.DatabaseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.bean.ProposedSubscription;
import com.openkm.util.JCRUtils;

public class ProposedSubscriptionDAO {
	private static Logger log = LoggerFactory.getLogger(ProposedSubscriptionDAO.class);

	private ProposedSubscriptionDAO() {}
	
	/**
	 * Create
	 */
	public static void create(ProposedSubscription ps) throws DatabaseException {
		log.debug("create({})", ps);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(ps);
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
	public static void delete(int psId) throws DatabaseException {
		log.debug("delete({})", psId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			ProposedSubscription ps = (ProposedSubscription) session.load(ProposedSubscription.class, psId);
			session.delete(ps);
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
	 * Find by pk
	 */
	public static ProposedSubscription findByPk(javax.jcr.Session jcrSession, int psId) throws DatabaseException,
			RepositoryException {
		log.debug("findByPk({}, {})", jcrSession, psId);
		String qs = "from ProposedSubscription ps where ps.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setInteger("id", psId);
			ProposedSubscription ret = (ProposedSubscription) q.setMaxResults(1).uniqueResult();
			
			try {
				Node node = jcrSession.getNodeByUUID(ret.getUuid());
				String nType = JCRUtils.getNodeType(node);
				ret.setPath(node.getPath());
				
				if (!nType.equals(ret.getType())) {
					ret.setType(JCRUtils.getNodeType(node));
					session.update(ret);
				}
			} catch (javax.jcr.ItemNotFoundException e) {
				// If user bookmark is missing, set a default
				Node okmRoot = jcrSession.getRootNode().getNode(Repository.ROOT);
				ret.setPath(okmRoot.getPath());
				ret.setUuid(okmRoot.getUUID());
				ret.setType(Folder.TYPE);
				session.save(ret);
			}
			
			HibernateUtil.commit(tx);
			log.debug("findByPk: {}", ret);
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
}
