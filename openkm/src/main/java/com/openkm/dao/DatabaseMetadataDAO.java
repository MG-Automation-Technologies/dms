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

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.DatabaseMetadataType;
import com.openkm.dao.bean.DatabaseMetadataValue;

/**
 * ExtensionMetadataDAO
 * 
 * @author pavila
 */
public class DatabaseMetadataDAO {
	private static Logger log = LoggerFactory.getLogger(DatabaseMetadataDAO.class);

	private DatabaseMetadataDAO() {}
	
	/**
	 * Create
	 */
	public static long createValue(DatabaseMetadataValue emv) throws DatabaseException {
		log.debug("createValue({})", emv);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Long id = (Long) session.save(emv);
			HibernateUtil.commit(tx);
			log.debug("createValue: {}", id);
			return id.longValue();
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Update
	 */
	public static void updateValue(DatabaseMetadataValue emv) throws DatabaseException {
		log.debug("updateValue({})", emv);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(emv);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("updateValue: void");
	}
	
	/**
	 * Delete
	 */
	public static void deleteValue(int emvId) throws DatabaseException {
		log.debug("deleteValue({})", emvId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			DatabaseMetadataValue emv = (DatabaseMetadataValue) session.load(DatabaseMetadataValue.class, emvId);
			session.delete(emv);
			HibernateUtil.commit(tx);
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("deleteValue: void");
	}
	
	/**
	 * Find all wiki pages
	 */
	@SuppressWarnings("unchecked")
	public static List<DatabaseMetadataValue> findAllValues(String table) throws DatabaseException {
		log.debug("findAllValues({})", table);
		String qs = "from DatabaseMetadataValue dmv where dmv.table=:table order by dmv.id asc";		
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("table", table);
			List<DatabaseMetadataValue> ret = q.list();
			log.debug("findAllValues: {}", ret);
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
	public static DatabaseMetadataValue findValueByPk(String table, long id) throws DatabaseException {
		log.debug("findValueByPk({}, {})", table, id);
		String qs = "from DatabaseMetadataValue dmv where dmv.table=:table and dmv.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("table", table);
			q.setLong("id", id);
			DatabaseMetadataValue ret = (DatabaseMetadataValue) q.setMaxResults(1).uniqueResult();
			log.debug("findValueByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Execute query
	 */
	@SuppressWarnings("unchecked")
	public static List<DatabaseMetadataValue> executeValueQuery(String query) throws DatabaseException {
		log.debug("executeQuery({})", query);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(query);
			List<DatabaseMetadataValue> ret = q.list();
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
	 * Create
	 */
	public static int createType(DatabaseMetadataType emt) throws DatabaseException {
		log.debug("createType({})", emt);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Integer id = (Integer) session.save(emt);
			HibernateUtil.commit(tx);
			log.debug("createType: {}", id);
			return id.intValue();
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Update
	 */
	public static void updateType(DatabaseMetadataType emt) throws DatabaseException {
		log.debug("updateType({})", emt);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(emt);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("updateType: void");
	}
	
	/**
	 * Delete
	 */
	public static void deleteType(int emtId) throws DatabaseException {
		log.debug("deleteType({})", emtId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			DatabaseMetadataType emt = (DatabaseMetadataType) session.load(DatabaseMetadataType.class, emtId);
			session.delete(emt);
			HibernateUtil.commit(tx);
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("deleteType: void");
	}
	
	/**
	 * Find all wiki pages
	 */
	@SuppressWarnings("unchecked")
	public static List<DatabaseMetadataType> findAllTypes(String table) throws DatabaseException {
		log.debug("findAllTypes({})", table);
		String qs = "from DatabaseMetadataType dmt where dmt.table=:table order by dmt.id asc";		
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("table", table);
			List<DatabaseMetadataType> ret = q.list();
			log.debug("findAllTypes: {}", ret);
			return ret;
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
