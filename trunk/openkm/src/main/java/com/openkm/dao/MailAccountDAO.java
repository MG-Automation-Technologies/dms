package com.openkm.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.MailAccount;

public class MailAccountDAO {
	private static Logger log = LoggerFactory.getLogger(MailAccountDAO.class);

	private MailAccountDAO() {}
	
	/**
	 * Create
	 */
	public static void create(MailAccount ma) throws DatabaseException {
		log.debug("create({})", ma);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(ma);
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("create: void");
	}
	
	/**
	 * Update
	 */
	public static void update(MailAccount ma) throws DatabaseException {
		log.debug("update({})", ma);
		String qs = "select ma.pass from MailAccount ma where ma.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setParameter("id", ma.getId());
			String pass = (String) q.setMaxResults(1).uniqueResult();
			ma.setMailPassword(pass);
			session.update(ma);
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
	 * Update password
	 */
	public static void updatePassword(int maId, String mailPassword) throws DatabaseException {
		log.debug("updatePassword({})", maId, mailPassword);
		String qs = "update MailAccount ma set ma.mailPassword=:mailPassword where ma.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			if (mailPassword != null && mailPassword.trim().length() > 0) {
				session = HibernateUtil.getSessionFactory().openSession();
				tx = session.beginTransaction();
				Query q = session.createQuery(qs);
				q.setString("mailPassword", mailPassword);
				q.setInteger("id", maId);
				q.executeUpdate();
				tx.commit();
			}
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("updatePassword: void");
	}
	
	/**
	 * Delete
	 */
	public static void delete(int maId) throws DatabaseException {
		log.debug("delete({})", maId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			MailAccount ma = (MailAccount) session.load(MailAccount.class, maId);
			session.delete(ma);
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("delete: void");
	}
	
	/**
	 * Finde by user
	 */
	@SuppressWarnings("unchecked")
	public static List<MailAccount> findByUser(String usrId, boolean filterByActive) throws DatabaseException {
		log.debug("findByUser({}, {})", usrId, filterByActive);
		String qs = "from MailAccount ma where ma.user=:user " +
			(filterByActive?"and ma.active=:active":"") + " order by ma.id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("user", usrId);
			
			if (filterByActive) {
				q.setBoolean("active", true);
			}
			
			List<MailAccount> ret = q.list();
			log.info("findByUser: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	/**
	 * find all mail accounts
	 */
	@SuppressWarnings("unchecked")
	public static List<MailAccount> findAll(boolean filterByActive) throws DatabaseException {
		log.debug("findAll({})", filterByActive);
		String qs = "from MailAccount ma " + (filterByActive?"where ma.active=:active":"") +
			" order by ma.id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			
			if (filterByActive) {
				q.setBoolean("active", true);
			}
			
			List<MailAccount> ret = q.list();
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
	public static MailAccount findByPk(int maId) throws DatabaseException {
		log.debug("findByPk({})", maId);
		String qs = "from MailAccount ma where ma.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", maId);
			MailAccount ret = (MailAccount) q.setMaxResults(1).uniqueResult();
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
