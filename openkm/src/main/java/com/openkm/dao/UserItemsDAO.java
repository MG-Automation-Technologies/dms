package com.openkm.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.UserItems;

public class UserItemsDAO {
	private static Logger log = LoggerFactory.getLogger(UserItemsDAO.class);

	private UserItemsDAO() {}
	
	/**
	 * Remove
	 */
	public static void remove(String user) throws DatabaseException {
		log.debug("remove({})", user);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			UserItems ui = (UserItems) session.load(UserItems.class, user);
			session.delete(ui);
			tx.commit();
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("remove: void");
	}
	
	/**
	 * Update user items
	 */
	public static void update(UserItems ui) throws DatabaseException {
		log.debug("update({})", ui);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(ui);
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
	 * Find by user
	 */
	public static UserItems findByPk(String user) throws DatabaseException {
		log.debug("findByPk({})", user);
		String qs = "from UserItems ui where ui.user=:user";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("user", user);
			UserItems ui = (UserItems) q.setMaxResults(1).uniqueResult();
			log.debug("findByPk: {}", ui);
			return ui;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Find all
	 */
	@SuppressWarnings("unchecked")
	public static List<UserItems> findAll() throws DatabaseException {
		log.debug("findAll()");
		String qs = "from UserItems";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			List<UserItems> ret = q.list();
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}	
}
