package com.openkm.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.save(ta);
			HibernateUtil.commit(tx);
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("create: void");
	}
	
	/**
	 * Update
	 */
	public static void update(TwitterAccount ta) throws DatabaseException {
		log.debug("update({})", ta);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.update(ta);
			HibernateUtil.commit(tx);
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("update: void");
	}
	
	/**
	 * Delete
	 */
	public static void delete(int taId) throws DatabaseException {
		log.debug("delete({})", taId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			TwitterAccount ta = (TwitterAccount) session.load(TwitterAccount.class, taId);
			session.delete(ta);
			HibernateUtil.commit(tx);
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
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
		String qs = "from TwitterAccount ta where ta.user=:user " + 
			(filterByActive?"and ta.active=:active":"") + " order by ta.id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
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
			(filterByActive?"where ta.active=:active":"") + " order by ta.id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery(qs);
			
			if (filterByActive) {
				q.setBoolean("active", true);
			}
			
			List<TwitterAccount> ret = q.list();
			log.debug("findAll: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	/**
	 * Find by pk
	 */
	public static TwitterAccount findByPk(int taId) throws DatabaseException {
		log.debug("findByPk({})", taId);
		String qs = "from TwitterAccount ta where ta.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", taId);
			TwitterAccount ret = (TwitterAccount) q.setMaxResults(1).uniqueResult();
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}	
}
