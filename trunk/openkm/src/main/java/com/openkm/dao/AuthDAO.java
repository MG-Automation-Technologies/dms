/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
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

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Role;
import com.openkm.dao.bean.User;
import com.openkm.util.SecureStore;

public class AuthDAO {
	private static Logger log = LoggerFactory.getLogger(AuthDAO.class);

	private AuthDAO() {}
			
	/**
	 * Create user in database
	 */
	public static void createUser(User user) throws DatabaseException {
		log.debug("createUser({})", user);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			user.setPass(SecureStore.md5Encode(user.getPass().getBytes()));
			session.save(user);
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("createUser: void");
	}

	/**
	 * Update user in database
	 */
	public static void updateUser(User user) throws DatabaseException {
		log.debug("updateUser({})", user);
		String qs = "select u.pass from User u where u.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery(qs);
			q.setParameter("id", user.getId());
			String pass = (String) q.setMaxResults(1).uniqueResult();
			user.setPass(pass);
			session.update(user);
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("updateUser: void");
	}
	
	/**
	 * Update user password in database 
	 */
	public static void updateUserPassword(String usrId, String usrPass) throws DatabaseException {
		log.debug("updateUserPassword({}, {})", usrId, usrPass);
		String qs = "update User u set u.pass=:pass where u.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			if (usrPass != null && usrPass.trim().length() > 0) {
				session = HibernateUtil.getSessionFactory().openSession();
				tx = session.beginTransaction();
				Query q = session.createQuery(qs);
				q.setString("pass", SecureStore.md5Encode(usrPass.getBytes()));
				q.setString("id", usrId);
				q.executeUpdate();
				tx.commit();
			}
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("updateUserPassword: void");
	}
	
	/**
	 * Update user email in database
	 */
	public static void updateUserEmail(String usrId, String usrEmail) throws DatabaseException {
		log.debug("updateUserEmail({}, {})", usrId, usrEmail);
		String qs = "update User set u.email=:email where u.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			if (usrEmail != null && usrEmail.trim().length() > 0) {
				session = HibernateUtil.getSessionFactory().openSession();
				tx = session.beginTransaction();
				Query q = session.createQuery(qs);
				q.setString("email", usrEmail);
				q.setString("id", usrId);
				q.executeUpdate();
				tx.commit();
			}
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("updateUserEmail: void");
	}
	
	/**
	 * Delete user from database
	 */
	public static void deleteUser(String usrId) throws DatabaseException {
		log.debug("deleteUser({})", usrId);
		String qsMail = "delete from MailAccount ma where ma.user=:user";
		String qsTwitter = "delete from TwitterAccount where ta.user=:user";
		String qsBookmark = "delete from Bookmark bm where bm.user=:user";
		String qsConfig = "delete from UserConfig uc where uc.user=:user";
		Session session = null;
		Transaction tx = null;
		
		try {
			User user = findUserByPk(usrId);
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.delete(user);
						
			Query qMail = session.createQuery(qsMail);
			qMail.setString("user", usrId);
			qMail.executeUpdate();
			
			Query qTwitter = session.createQuery(qsTwitter);
			qTwitter.setString("user", usrId);
			qTwitter.executeUpdate();
			
			Query qBookmark = session.createQuery(qsBookmark);
			qBookmark.setString("user", usrId);
			qBookmark.executeUpdate();
			
			Query qConfig = session.createQuery(qsConfig);
			qConfig.setString("user", usrId);
			qConfig.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("deleteUser: void");
	}
	
	/**
	 * Get all users in database
	 */
	@SuppressWarnings("unchecked")
	public static List<User> findAllUsers(boolean filterByActive) throws DatabaseException {
		log.debug("findAllUsers({})", filterByActive);
		String qs = "from User u "+(filterByActive?"where u.active=:active":"")+" order by u.id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			
			if (filterByActive) {
				q.setBoolean("active", true);
			}
			
			List<User> ret = q.list();
			log.info("findAllUsers: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	/**
	 * Get all users in database
	 */
	@SuppressWarnings("unchecked")
	public static List<User> findUsersByRole(boolean filterByActive, String rolId) throws DatabaseException {
		log.info("findUsersByRole({}, {})", filterByActive, rolId);
		String qs = "select u from User u, Role r where r.id=:rolId and r in elements(u.roles) " + 
			(filterByActive?"and u.active=:active":"")+" order by u.id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("rolId", rolId);
			
			if (filterByActive) {
				q.setBoolean("active", true);
			}
			
			List<User> ret = q.list();
			log.info("findUsersByRole: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Get user from database
	 */
	public static User findUserByPk(String usrId) throws DatabaseException {
		log.debug("findUserByPk({})", usrId);
		String qs = "from User u where u.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("id", usrId);
			User ret = (User) q.setMaxResults(1).uniqueResult();
			log.debug("findUserByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	/**
	 * Create role in database
	 */
	public static void createRole(Role role) throws DatabaseException {
		log.debug("createRole({})", role);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(role);
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("createRole: void");
	}

	/**
	 * Delete role from database
	 */
	public static void deleteRole(String rolId) throws DatabaseException {
		log.debug("deleteRole({})", rolId);
		Session session = null;
		Transaction tx = null;
		
		try {			
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Role role = (Role) session.load(Role.class, rolId);
			session.delete(role);
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("deleteRole: void");
	}

	/**
	 * Get all roles in database
	 */
	@SuppressWarnings("unchecked")
	public static List<Role> findAllRoles() throws DatabaseException {
		log.debug("findAllRoles()");
		String qs = "from Role";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			List<Role> ret = q.list();
			log.debug("findAllRoles: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Find role by pk
	 */
	public static Role findRoleByPk(String rolId) throws DatabaseException {
		log.debug("findRoleByPk({})", rolId);
		String qs = "from Role r where r.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("id", rolId);
			Role ret = (Role) q.setMaxResults(1).uniqueResult();
			log.debug("findRoleByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	/**
	 * Grant role to user
	 */
	public static void grantRole(String usrId, String rolId) throws DatabaseException {
		log.debug("grantRole({}, {})", usrId, rolId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			User user = (User) session.load(User.class, usrId);
			Role role = (Role) session.load(Role.class, rolId);
			user.getRoles().add(role);
			session.update(user);
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("grantRole: void");
	}

	/**
	 * Revoke role from user
	 */
	public void revokeRole(String usrId, String rolId) throws DatabaseException {
		log.debug("revokeRole({}, {})", usrId, rolId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			User user = (User) session.load(User.class, usrId);
			Role role = (Role) session.load(Role.class, rolId);
			user.getRoles().remove(role);
			session.update(user);
			tx.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("revokeRole: void");
	}
}
