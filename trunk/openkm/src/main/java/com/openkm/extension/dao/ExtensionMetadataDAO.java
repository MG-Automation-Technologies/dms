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

package com.openkm.extension.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.HibernateUtil;
import com.openkm.extension.dao.bean.ExtensionMetadataType;
import com.openkm.extension.dao.bean.ExtensionMetadataValue;

/**
 * ExtensionMetadataDAO
 * 
 * @author pavila
 */
public class ExtensionMetadataDAO {
	private static Logger log = LoggerFactory.getLogger(ExtensionMetadataDAO.class);

	private ExtensionMetadataDAO() {}
	
	/**
	 * Create
	 */
	public static int create(ExtensionMetadataValue emv) throws DatabaseException {
		log.debug("create({})", emv);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Integer id = (Integer) session.save(emv);
			HibernateUtil.commit(tx);
			log.debug("create: {}" + id);
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
	public static void update(ExtensionMetadataValue emv) throws DatabaseException {
		log.debug("update({})", emv);
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
		
		log.debug("update: void");
	}
	
	/**
	 * Delete
	 */
	public static void delete(int emvId) throws DatabaseException {
		log.debug("delete({})", emvId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			ExtensionMetadataValue emv = (ExtensionMetadataValue) session.load(ExtensionMetadataValue.class, emvId);
			session.delete(emv);
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
	 * Find all wiki pages
	 */
	@SuppressWarnings("unchecked")
	public static List<ExtensionMetadataValue> findAll(String table) throws DatabaseException {
		log.debug("findAll({})", table);
		String qs = "from ExtensionMetadataValue emv where emv.table=:table order by wkp.id asc";		
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("table", table);
			List<ExtensionMetadataValue> ret = q.list();
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
	public static ExtensionMetadataValue findByPk(String table, long id) throws DatabaseException {
		log.debug("findByPk({}, {})", table, id);
		String qs = "from ExtensionMetadataValue emv where emv.table=:table and emv.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("table", table);
			q.setLong("id", id);
			ExtensionMetadataValue ret = (ExtensionMetadataValue) q.setMaxResults(1).uniqueResult();
			log.debug("findByPk: {}", ret);
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
	public static List<ExtensionMetadataValue> executeQuery(String query) throws DatabaseException {
		log.debug("executeQuery({})", query);
		StringBuilder sb = new StringBuilder();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(sb.toString());
			List<ExtensionMetadataValue> ret = q.list();
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
	 * Build a query
	 */
	public static String buildQuery(String table, String filter, String order) throws DatabaseException {
		log.debug("buildQuery({}, {}, {})", new Object[] { table, filter, order });
		StringBuilder sb = new StringBuilder();
		String ret = null;
		
		sb.append("from ExtensionMetadataValue emv where emv.table='" + table + "'");
		
		if (filter != null && filter.length() > 0) {
			String repFilter = replaceVirtual(table, filter);				
			sb.append(" and ").append(repFilter);
		}
		
		if (order != null && order.length() > 0) {
			sb.append(" ").append(order);
		}
		
		ret = sb.toString();
		log.debug("buildQuery: {}", ret);
		return ret;
	}
	
	/**
	 * Build a query
	 */
	public static String buildUpdate(String table, String filter) throws DatabaseException {
		log.debug("buildUpdate({}, {})", new Object[] { table, filter });
		StringBuilder sb = new StringBuilder();
		String ret = null;
		
		sb.append("update ExtensionMetadataValue emv");
		
		if (filter != null && filter.length() > 0) {
			String repFilter = replaceVirtual(table, filter);
			sb.append(" ").append(repFilter).append(" ");
		}
		
		sb.append("where emv.table=:table");
		
		ret = sb.toString();
		log.debug("buildUpdate: {}", ret);
		return ret;
	}
	
	/**
	 * Build a query
	 */
	public static String buildDelete(String table, String filter) throws DatabaseException {
		log.debug("buildDelete({}, {})", new Object[] { table, filter });
		StringBuilder sb = new StringBuilder();
		String ret = null;
		
		sb.append("delete from ExtensionMetadataValue emv where emv.table='" + table + "'");
		
		if (filter != null && filter.length() > 0) {
			String repFilter = replaceVirtual(table, filter);
			sb.append(" and ").append(repFilter);
		}
		
		ret = sb.toString();
		log.debug("buildDelete: {}", ret);
		return ret;
	}
	
	/**
	 * Replace virtual columns by real ones
	 */
	@SuppressWarnings("unchecked")
	private static String replaceVirtual(String table, String filter) throws DatabaseException {
		Session session = null;
		String ret = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			if (filter != null && filter.length() > 0) {
				String qs = "from ExtensionMetadataType where table=:table";
				Query q = session.createQuery(qs);
				q.setString("table", table);
				List<ExtensionMetadataType> types = q.list();
				
				for (ExtensionMetadataType emt : types) {
					filter = filter.replace(emt.getVirtualColumn(), emt.getRealColumn());
				}
				
				ret = filter;
			}
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		return ret;
	}
}
