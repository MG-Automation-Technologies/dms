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
		
		try {
			HibernateHelper.getSession().save(user);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("createUser: void");
	}

	/**
	 * Update user in database
	 */
	public static void updateUser(User user) throws DatabaseException {
		log.debug("updateUser({})", user);
		String qs = "update User u set u.name= :name, u.email= :email, u.active= :active where u.id= :id";
		
		try {
			Query q = HibernateHelper.getSession().createQuery(qs);
			q.setString("name", user.getName());
			q.setString("email", user.getEmail());
			q.setBoolean("active", user.isActive());
			q.setString("id", user.getId());
			q.executeUpdate();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("updateUser: void");
	}
	
	/**
	 * Update user password in database 
	 */
	public static void updateUserPassword(String usrId, String usrPass) throws NoSuchAlgorithmException,
			DatabaseException {
		log.debug("updateUserPassword({}, {})", usrId, usrPass);
		String qs = "update User u set u.pass= :pass where u.id= :id";
		
		try {
			if (usrPass != null && usrPass.trim().length() > 0) {
				Query q = HibernateHelper.getSession().createQuery(qs);
				q.setString("pass", SecureStore.md5Encode(usrPass.getBytes()));
				q.setString("id", usrId);
				q.executeUpdate();
			}
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("updateUserPassword: void");
	}
	
	/**
	 * Update user email in database
	 */
	public static void updateUserEmail(String usrId, String usrEmail) throws DatabaseException {
		log.debug("updateUserEmail({}, {})", usrId, usrEmail);
		String qs = "update User set u.email= :email where u.id= :id";

		try {
			if (usrEmail != null && usrEmail.trim().length() > 0) {
				Query q = HibernateHelper.getSession().createQuery(qs);
				q.setString("email", usrEmail);
				q.setString("id", usrId);
				q.executeUpdate();
			}
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("updateUserEmail: void");
	}
	
	/**
	 * Delete user from database
	 */
	public static void deleteUser(User user) throws DatabaseException {
		log.debug("deleteUser({})", user);
		String qsUser = "delete from User u where u.id=?";
		String qsRole = "DELETE FROM user_role WHERE ur_user=?";
		String qsMail = "DELETE FROM mail_accounts WHERE ma_user=?";
		String qsTwitter = "DELETE FROM twitter_accounts WHERE ta_user=?";
		
		try {
			Query qUser = HibernateHelper.getSession().createQuery(qsUser);
			qUser.setString(1, user.getId());
			qUser.executeUpdate();
			
			Query qRole = HibernateHelper.getSession().createQuery(qsRole);
			qRole.setString(1, user.getId());
			qRole.executeUpdate();
			
			Query qMail = HibernateHelper.getSession().createQuery(qsMail);
			qMail.setString(1, user.getId());
			qMail.executeUpdate();
			
			Query qTwitter = HibernateHelper.getSession().createQuery(qsTwitter);
			qTwitter.setString(1, user.getId());
			qTwitter.executeUpdate();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
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
		
		try {
			Query q = HibernateHelper.getSession().createQuery(qs);
			
			if (filterByActive) {
				q.setBoolean("active", true);
			}
			
			List<User> ret = q.list();
			log.debug("findAllUsers: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	/**
	 * Get all users in database
	 */
	@SuppressWarnings("unchecked")
	public static List<User> findUsersByRole(boolean filterByActive, String rolId) throws DatabaseException {
		log.info("findUsersByRole({}, {})", filterByActive, rolId);
		String qs = "select u from User u, Role r where r.id=:rolId and r in elements(u.roles)" + 
			(filterByActive?"and u.active=:active":"")+" order by u.id";
		
		try {
			Query q = HibernateHelper.getSession().createQuery(qs);
			q.setString("rolId", rolId);
			
			if (filterByActive) {
				q.setBoolean("active", true);
			}
			
			List<User> ret = q.list();
			log.info("findUsersByRole: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	/**
	 * Get user from database
	 */
	public static User findUserByPk(String usrId) throws DatabaseException {
		log.debug("findUserByPk({})", usrId);
		String qs = "from User u where u.id=:id";
		
		try {
			Query q = HibernateHelper.getSession().createQuery(qs);
			q.setString("id", usrId);
			User ret = (User) q.setMaxResults(1).uniqueResult();
			log.debug("findUserByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	/**
	 * Create role in database
	 */
	public static void createRole(Role role) throws DatabaseException {
		log.debug("createRole({})", role);
	
		try {
			HibernateHelper.getSession().save(role);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("createRole: void");
	}

	/**
	 * Delete role from database
	 */
	public static void deleteRole(String rolId) throws DatabaseException {
		log.debug("deleteRole({})", rolId);
		
		try {
			Role role = findRoleByPk(rolId);
			HibernateHelper.getSession().update(role);
			HibernateHelper.getSession().delete(role);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
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
		
		try {
			Query q = HibernateHelper.getSession().createQuery(qs);
			List<Role> ret = q.list();
			log.debug("findAllRoles: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	/**
	 * Find role by pk
	 */
	public static Role findRoleByPk(String rolId) throws DatabaseException {
		log.debug("findRoleByPk({})", rolId);
		String qs = "from Role r where r.id= :id";
		
		try {
			Query q = HibernateHelper.getSession().createQuery(qs);
			q.setString("id", rolId);
			Role ret = (Role) q.setMaxResults(1).uniqueResult();
			log.debug("findRoleByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	/**
	 * Grant role to user
	 */
	public static void grantRole(String usrId, String rolId) throws DatabaseException {
		log.debug("grantRole({}, {})", usrId, rolId);

		try {
			User user = findUserByPk(usrId);
			Role role = findRoleByPk(rolId);
			user.getRoles().add(role);
			HibernateHelper.getSession().update(user);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("grantRole: void");
	}

	/**
	 * Revoke role from user
	 */
	public void revokeRole(String usrId, String rolId) throws DatabaseException {
		log.debug("revokeRole({}, {})", usrId, rolId);

		try {
			User user = findUserByPk(usrId);
			Role role = findRoleByPk(rolId);
			user.getRoles().remove(role);
			HibernateHelper.getSession().update(user);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("revokeRole: void");
	}
}
