/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Report;
import com.openkm.dao.bean.ReportParameter;

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
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Integer id = (Integer) session.save(rp);
			HibernateUtil.commit(tx);
			log.debug("create: {}", id);
			return id.intValue();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Update
	 */
	public static void update(Report rp) throws DatabaseException {
		log.debug("update({})", rp);
		String qs = "select rp.fileContent, rp.fileName from Report rp where rp.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			if (rp.getFileContent() == null || rp.getFileContent().length() == 0) {
				Query q = session.createQuery(qs);
				q.setParameter("id", rp.getId());
				Object[] data = (Object[]) q.setMaxResults(1).uniqueResult();
				rp.setFileContent((String) data[0]);
				rp.setFileName((String) data[1]);
			}
			
			session.update(rp);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
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
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Report rp = (Report) session.load(Report.class, rpId);
			session.delete(rp);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("delete: void");
	}
	
	/**
	 * Add parameter
	 */
	public static void addParam(int rpId, ReportParameter rpp) throws DatabaseException {
		log.debug("addParam({}, rpp)", rpId, rpp);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Report rp = (Report) session.load(Report.class, rpId);
			rp.getParams().add(rpp);
			session.update(rp);
			HibernateUtil.commit(tx);
			log.debug("addParam: void");
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Find by pk
	 */
	public static Report findByPk(int rpId) throws DatabaseException {
		log.debug("findByPk({})", rpId);
		String qs = "from Report rp where rp.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", rpId);
			Report ret = (Report) q.setMaxResults(1).uniqueResult();
			log.debug("findByPk: {}", ret);
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
	@SuppressWarnings("unchecked")
	public static List<Report> findAll() throws DatabaseException {
		log.debug("findAll()");
		String qs = "from Report rp order by rp.name";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			List<Report> ret = q.list();
			log.debug("findAll: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Update
	 */
	public static void updateParam(ReportParameter rpp) throws DatabaseException {
		log.debug("updateParam({})", rpp);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(rpp);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("updateParam: void");
	}
	
	/**
	 * Delete
	 */
	public static void deleteParam(int rppId) throws DatabaseException {
		log.debug("deleteParam({})", rppId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			ReportParameter rpp = (ReportParameter) session.load(ReportParameter.class, rppId);
			session.delete(rpp);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("deleteParam: void");
	}
	
	/**
	 * Find by pk
	 */
	public static ReportParameter findParamByPk(int rppId) throws DatabaseException {
		log.debug("findParamByPk({})", rppId);
		String qs = "from ReportParameter rpp where rpp.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", rppId);
			ReportParameter ret = (ReportParameter) q.setMaxResults(1).uniqueResult();
			log.debug("findParamByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Find by report
	 */
	public static Set<ReportParameter> findParamAll(int rpId) throws DatabaseException {
		log.debug("findParamAll({})", rpId);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Report rp = (Report) session.load(Report.class, rpId);
			Set<ReportParameter> params = rp.getParams();
			
			// Disable object lazy loading
			for (ReportParameter rpp : params) { rpp.getId(); }
			
			log.debug("findParamAll: {}", params);
			return params;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
