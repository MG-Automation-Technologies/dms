package com.openkm.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
	public static int create(QueryParams qp) throws DatabaseException {
		log.debug("create({})", qp);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			Integer id = (Integer) session.save(qp);
			QueryParams qpTmp = (QueryParams) session.load(QueryParams.class, id);

			for (String keyword : qp.getKeywords()) {
				qpTmp.getKeywords().add(keyword);	
			}
			
			for (String category : qp.getCategories()) {
				qpTmp.getCategories().add(category);	
			}
			
			for (Iterator<Entry<String, String>> it = qp.getProperties().entrySet().iterator(); it.hasNext(); ) {
				Entry<String, String> entry = it.next();
				qpTmp.getProperties().put(entry.getKey(), entry.getValue());
			}
			
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
	public static void update(QueryParams qp) throws DatabaseException {
		log.debug("update({})", qp);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.update(qp);
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
	public static void delete(int qpId) throws DatabaseException {
		log.debug("delete({})", qpId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			QueryParams qp = (QueryParams) session.load(QueryParams.class, qpId);
			session.delete(qp);
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
	public static QueryParams findByPk(int qpId) throws DatabaseException {
		log.debug("findByPk({})", qpId);
		String qs = "from QueryParams qp where qp.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", qpId);
			QueryParams ret = (QueryParams) q.setMaxResults(1).uniqueResult();
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	/**
	 * Find by user
	 */
	@SuppressWarnings("unchecked")
	public static List<QueryParams> findByUser(String user) throws DatabaseException {
		log.debug("findByUser({})", user);
		String qs = "from QueryParams qp where qp.user=:user";
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery(qs);
			q.setString("user", user);
			List<QueryParams> ret = q.list();
			log.debug("findByUser: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}
