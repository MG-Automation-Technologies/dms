package com.openkm.dao;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Folder;
import com.openkm.bean.Repository;
import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.UserConfig;
import com.openkm.util.JCRUtils;

public class UserConfigDAO {
	private static Logger log = LoggerFactory.getLogger(UserConfigDAO.class);

	private UserConfigDAO() {}
	
	/**
	 * Create
	 */
	public static void create(UserConfig uc) throws DatabaseException {
		log.debug("create({})", uc);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(uc);
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
	public static void update(UserConfig uc) throws DatabaseException {
		log.debug("update({})", uc);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(uc);
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
	 * Update user config profile
	 */
	public static void updateProfile(String ucUser, int upId) throws DatabaseException {
		log.debug("updateProfile({}, {})", ucUser, upId);
		String qs = "update UserConfig uc set uc.profile=:profile where uc.user=:user";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setEntity("profile", ProfileDAO.findByPk(upId));
			q.setString("user", ucUser);
			q.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("updateProfile: void");
	}
	
	/**
	 * Delete
	 */
	public static void delete(String user) throws DatabaseException {
		log.debug("delete({})", user);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			UserConfig uc = (UserConfig) session.load(UserConfig.class, user);
			session.delete(uc);
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
	 * Set user home
	 */
	public static void setHome(UserConfig uc) throws DatabaseException {
		log.debug("setHome({})", uc);
		String qs = "update UserConfig uc set uc.homePath=:path, uc.homeUuid=:uuid, " +
			"uc.homeType=:type where uc.user=:user";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setString("path", uc.getHomePath());
			q.setString("uuid", uc.getHomeUuid());
			q.setString("type", uc.getHomeType());
			q.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("setHome: void");
	}

	/**
	 * Find by pk
	 */
	public static UserConfig findByPk(javax.jcr.Session jcrSession, String user) throws DatabaseException, 
			RepositoryException {
		log.debug("findByPk({}, {})", jcrSession, user);
		String qs = "from UserConfig uc where uc.user=:user";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("user", user);
			UserConfig ret = (UserConfig) q.setMaxResults(1).uniqueResult();
			
			if (ret == null) {
				Node okmRoot = jcrSession.getRootNode().getNode(Repository.ROOT);
				ret = new UserConfig();
				ret.setHomePath(okmRoot.getPath());
				ret.setHomeUuid(okmRoot.getUUID());
				ret.setHomeType(Folder.TYPE);
				ret.setUser(user);
				ret.setProfile(ProfileDAO.findByPk(1));
				UserConfigDAO.create(ret);
			} else {
				Node node = jcrSession.getNodeByUUID(ret.getHomeUuid());
				
				if (!node.getPath().equals(ret.getHomePath())) {
					ret.setHomePath(node.getPath());
					ret.setHomeType(JCRUtils.getNodeType(node));
					UserConfigDAO.update(ret);
				}
			}
					
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
