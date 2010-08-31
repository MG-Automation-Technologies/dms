package com.openkm.dao;

import java.sql.Blob;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Report;

public class ReportDAO {
	private static Logger log = LoggerFactory.getLogger(ReportDAO.class);

	private ReportDAO() {}
	
	/**
	 * Create
	 */
	public static int create(Report rp) throws DatabaseException {
		log.debug("create({})", rp);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			Integer id = (Integer) session.save(rp);
			HibernateUtil.commit(tx);
			log.debug("create: {}", id);
			return id.intValue();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	/**
	 * Update
	 */
	public static void update(Report rp) throws DatabaseException {
		log.debug("update({})", rp);
		String qs = "select rp.fileContentBlob, rp.fileName from Report rp where rp.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			if (rp.getFileContent().length == 0) {
				Query q = session.createQuery(qs);
				q.setParameter("id", rp.getId());
				Object[] data = (Object[]) q.setMaxResults(1).uniqueResult();
				rp.setFileContent(HibernateUtil.toByteArray((Blob) data[0]));
				rp.setFileName((String) data[1]);
			}
			
			session.update(rp);
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
	public static void delete(int rpId) throws DatabaseException {
		log.debug("delete({})", rpId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			Report rp = (Report) session.load(Report.class, rpId);
			session.delete(rp);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("delete: void");
	}
	
	/**
	 * Find by pk
	 */
	public static Report findByPk(int rpId) throws DatabaseException {
		log.debug("findByPk({})", rpId);
		String qs = "from Report rp where rp.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", rpId);
			Report ret = (Report) q.setMaxResults(1).uniqueResult();
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	/**
	 * Find by pk
	 */
	@SuppressWarnings("unchecked")
	public static List<Report> findAll() throws DatabaseException {
		log.debug("findAll()");
		String qs = "from Report rp order by rp.name";
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery(qs);
			List<Report> ret = q.list();
			log.debug("findAll: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}
