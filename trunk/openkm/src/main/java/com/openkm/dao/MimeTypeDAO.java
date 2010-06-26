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
import com.openkm.dao.bean.MimeType;

public class MimeTypeDAO {
	private static Logger log = LoggerFactory.getLogger(MimeTypeDAO.class);

	private MimeTypeDAO() {}
	
	/**
	 * Create
	 */
	public static int create(MimeType mt) throws DatabaseException {
		log.debug("create({})", mt);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Integer id = (Integer) session.save(mt);
			MimeType mtTmp = (MimeType) session.load(MimeType.class, id);

			for (String extensions : mt.getExtensions()) {
				mtTmp.getExtensions().add(extensions);	
			}

			tx.commit();
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
	public static void update(MimeType mt) throws DatabaseException {
		log.debug("update({})", mt);
		String qs = "select mt.imageDataBlob, mt.imageMime from MimeType mt where mt.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setParameter("id", mt.getId());
			Object[] data = (Object[]) q.setMaxResults(1).uniqueResult();
			mt.setImageContent(HibernateUtil.toByteArray((Blob) data[0]));
			mt.setImageMime((String) data[1]);
			session.update(mt);
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
	 * Delete
	 */
	public static void delete(int mtId) throws DatabaseException {
		log.debug("delete({})", mtId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			MimeType mt = (MimeType) session.load(MimeType.class, mtId);
			session.delete(mt);
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
	 * Delete all
	 */
	@SuppressWarnings("unchecked")
	public static void deleteAll() throws DatabaseException {
		log.debug("deleteAll()");
		String qs = "from MimeType";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			List<MimeType> ret = session.createQuery(qs).list();
			
			for (MimeType mt : ret) {
				session.delete(mt);
			}
			
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("deleteAll: void");
	}
	
	/**
	 * Find by pk
	 */
	public static MimeType findByPk(int mtId) throws DatabaseException {
		log.debug("findByPk({})", mtId);
		String qs = "from MimeType mt where mt.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", mtId);
			MimeType ret = (MimeType) q.setMaxResults(1).uniqueResult();
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
	public static List<MimeType> findAll() throws DatabaseException {
		log.debug("findAll()");
		String qs = "from MimeType mt order by mt.name";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			List<MimeType> ret = q.list();
			log.debug("findAll: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	/**
	 * Find by name
	 */
	public static MimeType findByName(String name, boolean filterByActive) throws DatabaseException {
		log.debug("findByName({})", name);
		String qs = "from MimeType mt where mt.name=:name " +
			(filterByActive?"and mt.active=:active":"");
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("name", name);
			
			if (filterByActive) {
				q.setBoolean("active", true);
			}
			
			MimeType ret = (MimeType) q.setMaxResults(1).uniqueResult();
			log.debug("findByName: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
