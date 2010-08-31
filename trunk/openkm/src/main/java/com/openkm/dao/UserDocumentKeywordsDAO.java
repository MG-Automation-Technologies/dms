package com.openkm.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.UserDocumentKeywords;

public class UserDocumentKeywordsDAO {
	private static Logger log = LoggerFactory.getLogger(UserDocumentKeywordsDAO.class);

	private UserDocumentKeywordsDAO() {}
	
	/**
	 * Remove
	 */
	public static void remove(int id) throws DatabaseException {
		log.debug("remove({})", id);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			UserDocumentKeywords udk = (UserDocumentKeywords) session.load(UserDocumentKeywords.class, id);
			session.delete(udk);
			HibernateUtil.commit(tx);
		} catch(HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("remove: void");
	}
	
	/**
	 * Update user items
	 */
	public static void create(UserDocumentKeywords udk) throws DatabaseException {
		log.debug("update({})", udk);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.save(udk);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("update: void");
	}

	/**
	 * Find by user
	 */
	@SuppressWarnings("unchecked")
	public static List<UserDocumentKeywords> findByUser(String user) throws DatabaseException {
		log.debug("findByUser({})", user);
		String qs = "from UserDocumentKeywords udk where udk.user=:user";
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery(qs);
			q.setString("user", user);
			List<UserDocumentKeywords> ret = q.list();
			log.debug("findByUser: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	/**
	 * Find users
	 */
	@SuppressWarnings("unchecked")
	public static List<String> findUsers() throws DatabaseException {
		log.debug("findUsers()");
		String qs = "select distinct udk.user from UserDocumentKeywords udk";
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery(qs);
			List<String> ret = q.list();
			log.debug("findUsers: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}	
	
	/**
	 * Find all
	 */
	@SuppressWarnings("unchecked")
	public static List<UserDocumentKeywords> findAll() throws DatabaseException {
		log.debug("findAll()");
		String qs = "from UserDocumentKeywords";
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery(qs);
			List<UserDocumentKeywords> ret = q.list();
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	/**
	 * Empty database
	 */
	@SuppressWarnings("unchecked")
	public static void clean() throws DatabaseException {
		log.debug("clean()");
		String qs = "from UserDocumentKeywords";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			List<UserDocumentKeywords> ret = session.createQuery(qs).list();
			
			for (UserDocumentKeywords udk : ret) {
				session.delete(udk);
			}
			
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("clean: void");
	}
}
