package com.openkm.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
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
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.save(ma);
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
	public static void update(MailAccount ma) throws DatabaseException {
		log.debug("update({})", ma);
		String qs = "update MailAccount ma set ma.user=:user, ma.mailHost=:mailHost, " +
			"ma.mailUser=:mailUser, ma.mailFolder=:mailFolder, ma.active=:active " +
			"where ma.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("user", ma.getUser());
			q.setString("mailHost", ma.getMailHost());
			q.setString("mailUser", ma.getMailUser());
			q.setString("mailFolder", ma.getMailFolder());
			q.setBoolean("active", ma.isActive());
			q.setInteger("id", ma.getId());
			q.executeUpdate();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("update: void");
	}
	
	/**
	 * Update password
	 */
	public static void updatePassword(MailAccount ma) throws DatabaseException {
		log.debug("updatePassword({})", ma);
		String qs = "update MailAccount ma set ma.mailPassword=:mailPassword where ma.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("mailPassword", ma.getMailPassword());
			q.setInteger("id", ma.getId());
			q.executeUpdate();
		} catch (HibernateException e) {
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
		
		try {
			MailAccount ma = findByPk(maId);
			session = HibernateUtil.getSessionFactory().openSession();
			session.delete(ma);
		} catch (HibernateException e) {
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
			log.debug("findByUser: {}", ret);
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
