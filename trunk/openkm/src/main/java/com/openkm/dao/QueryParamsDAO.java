package com.openkm.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.QueryParams;

public class QueryParamsDAO {
	private static Logger log = LoggerFactory.getLogger(QueryParamsDAO.class);

	private QueryParamsDAO() {}
	
	/**
	 * Create
	 */
	public static void create(QueryParams qp) throws DatabaseException {
		log.debug("create({})", qp);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.save(qp);
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
	public static void update(QueryParams qp) throws DatabaseException {
		log.debug("update({})", qp);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.update(qp);
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
	public static void delete(int qpId) throws DatabaseException {
		log.debug("delete({})", qpId);
		Session session = null;
		
		try {
			QueryParams qp = findByPk(qpId);
			session = HibernateUtil.getSessionFactory().openSession();
			session.delete(qp);
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
	public static QueryParams findByPk(int qpId) throws DatabaseException {
		log.debug("findByPk({})", qpId);
		String qs = "from QueryParams qp where qp.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", qpId);
			QueryParams ret = (QueryParams) q.setMaxResults(1).uniqueResult();
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
