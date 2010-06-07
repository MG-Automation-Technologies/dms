package com.openkm.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.LockToken;

public class LockTokenDAO {
	private static Logger log = LoggerFactory.getLogger(LockTokenDAO.class);

	private LockTokenDAO() {}
	
	/**
	 * Create lock token
	 */
	public static void add(LockToken lt) throws DatabaseException {
		log.debug("add({})", lt);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.save(lt);
		} catch(HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("add: void");
	}
	
	/**
	 * Remove
	 */
	public static void remove(LockToken lt) throws DatabaseException {
		log.debug("remove({})", lt);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.delete(lt);
		} catch(HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("remove: void");
	}

	/**
	 * Find by user
	 */
	@SuppressWarnings("unchecked")
	public static List<LockToken> findByUser(String user) throws DatabaseException {
		log.debug("findByUser({})", user);
		String qs = "from LockToken lt where lt.user=:user";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("user", user);
			List<LockToken> ret = q.list();
			log.debug("findByUser: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}	
}
