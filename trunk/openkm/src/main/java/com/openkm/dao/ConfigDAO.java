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

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Config;

public class ConfigDAO  {
	private static Logger log = LoggerFactory.getLogger(ConfigDAO.class);

	private ConfigDAO() {}
	
	/**
	 * Create activity
	 */
	public static void create(Config cfg) throws DatabaseException {
	    Session session = null;
	    Transaction tx = null;
	    
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
	    	session.save(cfg);
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
	public static void update(Config cfg) throws DatabaseException {
		log.debug("update({})", cfg);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(cfg);
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
	public static void delete(String key) throws DatabaseException {
		log.debug("delete({})", key);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Config mt = (Config) session.load(Config.class, key);
			session.delete(mt);
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
	public static Config findByPk(String key) throws DatabaseException {
		log.debug("findByPk({})", key);
		String qs = "from Config cfg where cfg.key=:key";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setString("key", key);
			Config ret = (Config) q.setMaxResults(1).uniqueResult();
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
	 * Find by pk with a default value
	 */
	public static Config findDefault(String key, String type, String value) throws DatabaseException {
		log.debug("findDefault({})", key);
		String qs = "from Configuration cfg where cfg.key=:key";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setString("key", key);
			Config ret = (Config) q.setMaxResults(1).uniqueResult();
			
			if (ret == null) {
				Config def = new Config();
				def.setKey(key);
				def.setType(type);
				def.setValue(value);
				session.save(def);
			}
			
			HibernateUtil.commit(tx);
			log.debug("findDefault: {}", ret);
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
	@SuppressWarnings("unchecked")
	public static List<Config> findAll() throws DatabaseException {
		log.debug("findAll()");
		String qs = "from Config cfg order by cfg.key";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			List<Config> ret = q.list();
			HibernateUtil.commit(tx);
			log.debug("findAll: {}", ret);
			return ret;
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
