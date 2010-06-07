package com.openkm.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.UserConfig;

public class UserConfigDAO {
	private static Logger log = LoggerFactory.getLogger(UserConfigDAO.class);

	private UserConfigDAO() {}
	
	/**
	 * Create
	 */
	public static void create(UserConfig uc) throws DatabaseException {
		log.debug("create({})", uc);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.save(uc);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("create: void");
	}
	
	/**
	 * Update
	 */
	public static void update(UserConfig uc) throws DatabaseException {
		log.debug("update({})", uc);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.update(uc);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("update: void");
	}
	
	/**
	 * Delete
	 */
	public static void delete(String user) throws DatabaseException {
		log.debug("delete({})", user);
		Session session = null;
		
		try {
			UserConfig uc = findByPk(user);
			session = HibernateUtil.getSessionFactory().openSession();
			session.delete(uc);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("delete: void");
	}

	/**
	 * Find by pk
	 */
	public static UserConfig findByPk(String user) throws DatabaseException {
		log.debug("findByPk({})", user);
		String qs = "from UserConfig uc where uc.user=:user";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("user", user);
			UserConfig ret = (UserConfig) q.setMaxResults(1).uniqueResult();
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
