package com.openkm.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.TwitterAccount;

public class TwitterAccountDAO {
	private static Logger log = LoggerFactory.getLogger(TwitterAccountDAO.class);

	private TwitterAccountDAO() {}
	
	/**
	 * Create account
	 */
	public static void create(TwitterAccount ta) throws DatabaseException {
		log.debug("create({})", ta);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.save(ta);
		} catch(HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("create: void");
	}
	
	/**
	 * Update
	 */
	public static void update(TwitterAccount ta) throws DatabaseException {
		log.debug("update({})", ta);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.update(ta);
		} catch(HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("update: void");
	}
	
	/**
	 * Delete
	 */
	public static void delete(int taId) throws DatabaseException {
		log.debug("delete({})", taId);
		Session session = null;
		
		try {
			TwitterAccount ta = findByPk(taId);
			session = HibernateUtil.getSessionFactory().openSession();
			session.update(ta);
			session.delete(ta);
		} catch(HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("deleteTwitterAccount: void");
	}

	/**
	 * Find by user
	 */
	@SuppressWarnings("unchecked")
	public static List<TwitterAccount> findByUser(String user, boolean filterByActive) throws 
			DatabaseException {
		log.debug("findByUser({})", user);
		String qs = "from TwitterAccount ta where ta.user= :user " + 
			(filterByActive?"and ta.active= :active":"") + " order by ta.id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("user", user);
			
			if (filterByActive) {
				q.setBoolean("active", true);
			}
			
			List<TwitterAccount> ret = q.list();
			log.debug("findByUser: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	/**
	 * Find all twitter accounts
	 */
	@SuppressWarnings("unchecked")
	public static List<TwitterAccount> findAll(boolean filterByActive) throws 
			DatabaseException {
		log.debug("findAll()");
		String qs = "from TwitterAccount ta " + 
			(filterByActive?"where ta.active= :active":"") + " order by ta.id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			
			if (filterByActive) {
				q.setBoolean("active", true);
			}
			
			List<TwitterAccount> ret = q.list();
			log.debug("findAll: {}", ret);
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
	@SuppressWarnings("unchecked")
	public static TwitterAccount findByPk(int taId) throws DatabaseException {
		log.debug("findByPk({})", taId);
		String qs = "from TwitterAccount ta where ta.id= :id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setEntity("id", taId);
			List<TwitterAccount> results = q.list();
			TwitterAccount ret = null;
			
			if (results.size() == 0) {
				ret = results.get(0); 
			}
			
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}	
}
