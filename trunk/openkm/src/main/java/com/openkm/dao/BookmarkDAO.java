package com.openkm.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Bookmark;

public class BookmarkDAO {
	private static Logger log = LoggerFactory.getLogger(BookmarkDAO.class);

	private BookmarkDAO() {}
	
	/**
	 * Create
	 */
	public static void create(Bookmark bm) throws DatabaseException {
		log.debug("create({})", bm);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.save(bm);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("create: void");
	}
	
	/**
	 * Update
	 */
	public static void update(Bookmark bm) throws DatabaseException {
		log.debug("update({})", bm);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.update(bm);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("update: void");
	}
	
	/**
	 * Delete
	 */
	public static void delete(int bmId) throws DatabaseException {
		log.debug("delete({})", bmId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			Bookmark bm = (Bookmark) session.load(Bookmark.class, bmId);
			session.delete(bm);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("delete: void");
	}
	
	/**
	 * Finde by user
	 */
	@SuppressWarnings("unchecked")
	public static List<Bookmark> findByUser(String usrId) throws DatabaseException {
		log.debug("findByUser({})", usrId);
		String qs = "from Bookmark bm where bm.user=:user order by bm.id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery(qs);
			q.setString("user", usrId);
			List<Bookmark> ret = q.list();
			log.debug("findByUser: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}


	/**
	 * Find by pk
	 */
	public static Bookmark findByPk(int bmId) throws DatabaseException {
		log.debug("findByPk({})", bmId);
		String qs = "from Bookmark bm where bm.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", bmId);
			Bookmark ret = (Bookmark) q.setMaxResults(1).uniqueResult();
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}
