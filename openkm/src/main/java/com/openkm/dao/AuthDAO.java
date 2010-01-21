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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;
import com.openkm.dao.bean.MailAccount;
import com.openkm.dao.bean.TwitterAccount;
import com.openkm.dao.bean.Role;
import com.openkm.dao.bean.User;
import com.openkm.util.SecureStore;

public class AuthDAO extends AbstractDAO {
	private static Logger log = LoggerFactory.getLogger(AuthDAO.class);
	private static AuthDAO instance = null;

	/* (non-Javadoc)
	 * @see com.openkm.dao.AbstractDAO#getDataSourceName()
	 */
	protected String getDataSourceName() {
		return "java:/OKMAuth"+Config.INSTALL+"DS";
	}

	/* (non-Javadoc)
	 * @see com.openkm.dao.AbstractDAO#getTableName()
	 */
	protected String getTableName() {
		return "users";
	}

	/* (non-Javadoc)
	 * @see com.openkm.dao.AbstractDAO#getSchema()
	 */
	protected String getSchema() {
		return "auth";
	}

	private AuthDAO() {}
	
	/**
	 * @return
	 */
	public static synchronized AuthDAO getInstance() { 
		if (instance == null) {
			log.debug("getInstance()");
			instance = new AuthDAO();
		}
		
		return instance;
	}
	
	/**
	 * Create user in database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void createUser(User vo) throws SQLException {
		log.debug("createUser("+vo+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO users (usr_id, usr_name, usr_pass, usr_email, usr_active) VALUES (?, ?, ?, ?, ?)";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getId());
				stmt.setString(2, vo.getName());
				stmt.setString(3, SecureStore.md5Encode(vo.getPass().getBytes()));
				stmt.setString(4, vo.getEmail());
				stmt.setBoolean(5, vo.isActive());
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} catch (NoSuchAlgorithmException e) {
			throw new SQLException(e.getMessage());
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("createUser: void");
	}

	/**
	 * Update user in database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void updateUser(User vo) throws SQLException {
		log.debug("updateUser("+vo+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE users SET usr_email=?, usr_active=?, usr_name=? WHERE usr_id=?";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getEmail());
				stmt.setBoolean(2, vo.isActive());
				stmt.setString(3, vo.getName());
				stmt.setString(4, vo.getId());
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("updateUser: void");
	}
	
	/**
	 * Update user password in database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void updateUserPassword(String usrId, String usrPass) throws SQLException {
		log.debug("updateUserPassword("+usrId+", "+usrPass+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE users SET usr_pass=? WHERE usr_id=?";

		try {
			con = getConnection();
			
			if (con != null) {
				if (usrPass != null && usrPass.trim().length() > 0) {
					stmt = con.prepareStatement(sql);
					stmt.setString(1, SecureStore.md5Encode(usrPass.getBytes()));
					stmt.setString(2, usrId);
					stmt.execute();
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} catch (NoSuchAlgorithmException e) {
			throw new SQLException(e.getMessage());
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("updateUserPassword: void");
	}
	
	/**
	 * Update user email in database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void updateUserEmail(String usrId, String usrEmail) throws SQLException {
		log.debug("updateUserEmail("+usrId+", "+usrEmail+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE users SET usr_email=? WHERE usr_id=?";

		try {
			con = getConnection();
			
			if (con != null) {
				if (usrEmail != null && usrEmail.trim().length() > 0) {
					stmt = con.prepareStatement(sql);
					stmt.setString(1, usrEmail);
					stmt.setString(2, usrId);
					stmt.execute();
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("updateUserEmail: void");
	}
	
	/**
	 * Delete user from database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void deleteUser(User vo) throws SQLException {
		log.debug("deleteUser("+vo+")");
		Connection con = null;
		PreparedStatement stmtUser = null;
		PreparedStatement stmtRole = null;
		PreparedStatement stmtMail = null;
		PreparedStatement stmtTwitter = null;
		String sqlUser = "DELETE FROM users WHERE usr_id=?";
		String sqlRole = "DELETE FROM user_role WHERE ur_user=?";
		String sqlMail = "DELETE FROM mail_accounts WHERE ma_user=?";
		String sqlTwitter = "DELETE FROM twitter_accounts WHERE ta_user=?";
		
		try {
			con = getConnection();
			
			if (con != null) {
				stmtUser = con.prepareStatement(sqlUser);
				stmtUser.setString(1, vo.getId());
				stmtUser.execute();
				
				stmtRole = con.prepareStatement(sqlRole);
				stmtRole.setString(1, vo.getId());
				stmtRole.execute();

				stmtMail = con.prepareStatement(sqlMail);
				stmtMail.setString(1, vo.getId());
				stmtMail.execute();

				stmtTwitter = con.prepareStatement(sqlTwitter);
				stmtTwitter.setString(1, vo.getId());
				stmtTwitter.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmtUser);
			closeStatement(stmtRole);
			closeConnection(con);
		}
		
		log.debug("deleteUser: void");
	}
	
	/**
	 * Get all users in database
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Collection<User> findAllUsers(boolean filterByActive) throws SQLException {
		log.debug("findAllUsers("+filterByActive+")");
		Connection con = null;
		PreparedStatement stmtUser = null;
		PreparedStatement stmtUserRoles = null;
		ResultSet rsUser = null;
		ResultSet rsUserRoles = null;
		String sqlUser = "SELECT usr_id, usr_name, usr_email, usr_active, usr_pass FROM users "+(filterByActive?"WHERE usr_active='true'":"")+" ORDER BY usr_id";
		String sqlUserRoles = "SELECT ur_role FROM user_role WHERE ur_user=? ORDER BY ur_role";
		ArrayList<User> al = new ArrayList<User>();
		
		try {
			con = getConnection();
			
			if (con != null) {
				stmtUser = con.prepareStatement(sqlUser);
				rsUser = stmtUser.executeQuery();
				
				while (rsUser.next()) {
					User user = new User();
					user.setId(rsUser.getString(1));
					user.setName(rsUser.getString(2));
					user.setEmail(rsUser.getString(3));
					user.setActive(rsUser.getBoolean(4));
					user.setPass(rsUser.getString(5));
					
					stmtUserRoles = con.prepareStatement(sqlUserRoles);
					stmtUserRoles.setString(1, user.getId());
					rsUserRoles = stmtUserRoles.executeQuery();
					
					while (rsUserRoles.next()) {
						user.addRole(rsUserRoles.getString(1));
					}
					
					closeStatement(stmtUserRoles);
					closeResultSet(rsUserRoles);
					
					al.add(user);
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeResultSet(rsUser);
			closeResultSet(rsUserRoles);
			closeStatement(stmtUser);
			closeStatement(stmtUserRoles);
			closeConnection(con);
		}
		
		log.debug("findAllUsers: "+al);
		return al;
	}

	/**
	 * Get all users in database
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Collection<User> findUsersByRole(boolean filterByActive, String role) throws SQLException {
		log.debug("findUsersByRole("+role+")");
		Connection con = null;
		PreparedStatement stmtUser = null;
		PreparedStatement stmtUserRoles = null;
		ResultSet rsUser = null;
		ResultSet rsUserRoles = null;
		String sqlUser = "SELECT usr_id, usr_name, usr_email, usr_active, usr_pass FROM users, user_role WHERE ur_user=usr_id AND ur_role=? "+(filterByActive?"AND usr_active='true'":"")+" ORDER BY usr_id";
		String sqlUserRoles = "SELECT ur_role FROM user_role WHERE ur_user = ? ORDER BY ur_role";
		ArrayList<User> al = new ArrayList<User>();
		
		try {
			con = getConnection();
			
			if (con != null) {
				stmtUser = con.prepareStatement(sqlUser);
				stmtUser.setString(1, role);
				rsUser = stmtUser.executeQuery();
				
				while (rsUser.next()) {
					User user = new User();
					user.setId(rsUser.getString(1));
					user.setName(rsUser.getString(2));
					user.setEmail(rsUser.getString(3));
					user.setActive(rsUser.getBoolean(4));
					user.setPass(rsUser.getString(5));
					
					stmtUserRoles = con.prepareStatement(sqlUserRoles);
					stmtUserRoles.setString(1, user.getId());
					rsUserRoles = stmtUserRoles.executeQuery();
					
					while (rsUserRoles.next()) {
						user.addRole(rsUserRoles.getString(1));
					}
					
					closeStatement(stmtUserRoles);
					closeResultSet(rsUserRoles);
					
					al.add(user);
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeResultSet(rsUser);
			closeResultSet(rsUserRoles);
			closeStatement(stmtUser);
			closeStatement(stmtUserRoles);
			closeConnection(con);
		}
		
		log.debug("findUsersByRole: "+al);
		return al;
	}
	
	/**
	 * Get user from database
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public User findUserByPk(String userId) throws SQLException {
		log.debug("findUserByPk("+userId+")");
		Connection con = null;
		PreparedStatement stmtUser = null;
		PreparedStatement stmtUserRoles = null;
		ResultSet rsUser = null;
		ResultSet rsUserRoles = null;
		String sqlUser = "SELECT usr_id, usr_name, usr_email, usr_active, usr_pass FROM users WHERE usr_id=?";
		String sqlUserRoles = "SELECT ur_role FROM user_role WHERE ur_user=? ORDER BY ur_role";
		User user = null;
		
		try {
			con = getConnection();
			
			if (con != null) {
				stmtUser = con.prepareStatement(sqlUser);
				stmtUser.setString(1, userId);
				rsUser = stmtUser.executeQuery();
				
				if (rsUser.next()) {
					user = new User();
					user.setId(rsUser.getString(1));
					user.setName(rsUser.getString(2));
					user.setEmail(rsUser.getString(3));
					user.setActive(rsUser.getBoolean(4));
					user.setPass(rsUser.getString(5));
					
					stmtUserRoles = con.prepareStatement(sqlUserRoles);
					stmtUserRoles.setString(1, user.getId());
					rsUserRoles = stmtUserRoles.executeQuery();
					
					while (rsUserRoles.next()) {
						user.addRole(rsUserRoles.getString(1));
					}
					
					closeStatement(stmtUserRoles);
					closeResultSet(rsUserRoles);
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeResultSet(rsUser);
			closeResultSet(rsUserRoles);
			closeStatement(stmtUser);
			closeStatement(stmtUserRoles);
			closeConnection(con);
		}
		
		log.debug("findUserByPk: "+user);
		return user;
	}

	/**
	 * Create role in database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void createRole(Role vo) throws SQLException {
		log.debug("createRole("+vo+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO roles (rol_id) VALUES (?)";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getId());
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("createRole: void");
	}

	/**
	 * Delete role from database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void deleteRole(Role vo) throws SQLException {
		log.debug("deleteRole("+vo+")");
		Connection con = null;
		PreparedStatement stmtUser = null;
		PreparedStatement stmtUserRoles = null;
		String sqlUser = "DELETE FROM roles WHERE rol_id=?";
		String sqlUserRoles = "DELETE FROM user_role WHERE ur_role=?";

		try {
			con = getConnection();
			
			if (con != null) {
				stmtUser = con.prepareStatement(sqlUser);
				stmtUser.setString(1, vo.getId());
				stmtUser.execute();
				
				stmtUserRoles = con.prepareStatement(sqlUserRoles);
				stmtUserRoles.setString(1, vo.getId());
				stmtUserRoles.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmtUser);
			closeStatement(stmtUserRoles);
			closeConnection(con);
		}
		
		log.debug("deleteRole: void");
	}

	/**
	 * Get all roles in database
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Collection<Role> findAllRoles() throws SQLException {
		log.debug("findAllRoles()");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT rol_id FROM roles ORDER BY rol_id";
		ArrayList<Role> al = new ArrayList<Role>();
		
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();

				while (rs.next()) {
					Role role = new Role();
					role.setId(rs.getString(1));
					al.add(role);
				}				
				
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("findAllRoles: "+al);
		return al;
	}
	
	/**
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public Role findRoleByPk(String roleId) throws SQLException {
		log.debug("findRoleByPk("+roleId+")");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT rol_id FROM roles WHERE rol_id=?";
		Role role = null;
		
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, roleId);
				rs = stmt.executeQuery();
				
				if (rs.next()) {
					role = new Role();
					role.setId(rs.getString(1));
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("findRoleByPk: "+role);
		return role;
	}

	/**
	 * Grant role to user
	 * 
	 * @param user
	 * @param role
	 * @throws SQLException
	 */
	public void grantRole(String user, String role) throws SQLException {
		log.debug("grantRole("+user+", "+role+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO user_role (ur_user, ur_role) VALUES (?, ?)";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, user);
				stmt.setString(2, role);
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("grantRole: void");
	}

	/**
	 * Revoke role from user
	 * 
	 * @param user
	 * @param role
	 * @throws SQLException
	 */
	public void revokeRole(String user, String role) throws SQLException {
		log.debug("revokeRole("+user+", "+role+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM user_role WHERE ur_user=? AND ur_role=?";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, user);
				stmt.setString(2, role);
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("revokeRole: void");
	}
	
	/**
	 * Delete user roles from database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void deleteUserRoles(User vo) throws SQLException {
		log.debug("deleteUserRoles("+vo+")");
		Connection con = null;
		PreparedStatement stmtUser = null;
		PreparedStatement stmtRole = null;
		String sqlRole = "DELETE FROM user_role WHERE ur_user=?";

		try {
			con = getConnection();
			
			if (con != null) {				
				stmtRole = con.prepareStatement(sqlRole);
				stmtRole.setString(1, vo.getId());
				stmtRole.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmtUser);
			closeStatement(stmtRole);
			closeConnection(con);
		}
		
		log.debug("deleteUserRoles: void");
	}
	
	/**
	 * @param user
	 * @param filterByActive
	 * @return
	 * @throws SQLException
	 */
	public Collection<MailAccount> findMailAccountsByUser(String user, boolean filterByActive) throws SQLException {
		log.debug("findMailAccountsByUser("+user+", "+filterByActive+")");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT ma_id, ma_user, ma_mhost, ma_mfolder, ma_muser, ma_mpass, ma_active FROM mail_accounts WHERE ma_user=? "+(filterByActive?"AND ma_active='true'":"")+" ORDER BY ma_id";
		ArrayList<MailAccount> al = new ArrayList<MailAccount>();
				
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, user);
				rs = stmt.executeQuery();

				while (rs.next()) {
					MailAccount ma = new MailAccount();
					ma.setId(rs.getInt(1));
					ma.setUser(rs.getString(2));
					ma.setMailHost(rs.getString(3));
					ma.setMailFolder(rs.getString(4));
					ma.setMailUser(rs.getString(5));
					ma.setMailPassword(rs.getString(6));
					ma.setActive(rs.getBoolean(7));
					al.add(ma);
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("findMailAccountsByUser: "+al);
		return al;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public Collection<MailAccount> findAllMailAccounts(boolean filterByActive) throws SQLException {
		log.debug("findAllMailAccounts()");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT ma_id, ma_user, ma_mhost, ma_mfolder, ma_muser, ma_mpass, ma_active FROM mail_accounts "+(filterByActive?"WHERE ma_active='true'":"")+" ORDER BY ma_id";
		ArrayList<MailAccount> al = new ArrayList<MailAccount>();
				
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();

				while (rs.next()) {
					MailAccount ma = new MailAccount();
					ma.setId(rs.getInt(1));
					ma.setUser(rs.getString(2));
					ma.setMailHost(rs.getString(3));
					ma.setMailFolder(rs.getString(4));
					ma.setMailUser(rs.getString(5));
					ma.setMailPassword(rs.getString(6));
					ma.setActive(rs.getBoolean(7));
					al.add(ma);
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("findAllMailAccounts: "+al);
		return al;
	}

	/**
	 * @param id
	 * @param host
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public MailAccount findMailAccountByPk(int id) throws SQLException {
		log.debug("findMailAccountByPk("+id+")");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT ma_id, ma_user, ma_mhost, ma_mfolder, ma_muser, ma_mpass, ma_active FROM mail_accounts WHERE ma_id=?";
		MailAccount ma = null;
				
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, id);
				rs = stmt.executeQuery();

				if (rs.next()) {
					ma = new MailAccount();
					ma.setId(rs.getInt(1));
					ma.setUser(rs.getString(2));
					ma.setMailHost(rs.getString(3));
					ma.setMailFolder(rs.getString(4));
					ma.setMailUser(rs.getString(5));
					ma.setMailPassword(rs.getString(6));
					ma.setActive(rs.getBoolean(7));
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("findMailAccountByPk: "+ma);
		return ma;
	}
	
	/**
	 * @param vo
	 * @throws SQLException
	 */
	public void createMailAccount(MailAccount vo) throws SQLException {
		log.debug("createMailAccount("+vo+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO mail_accounts (ma_user, ma_mhost, ma_muser, ma_mpass, ma_mfolder, ma_active) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getUser());
				stmt.setString(2, vo.getMailHost());
				stmt.setString(3, vo.getMailUser());
				stmt.setString(4, vo.getMailPassword());
				stmt.setString(5, vo.getMailFolder());
				stmt.setBoolean(6, vo.isActive());
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("createMailAccount: void");
	}
	
	/**
	 * @param vo
	 * @throws SQLException
	 */
	public void updateMailAccount(MailAccount vo) throws SQLException {
		log.debug("updateMailAccount("+vo+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE mail_accounts SET ma_user=?, ma_mhost=?, ma_muser=?, ma_mfolder=?, ma_active=? WHERE ma_id=?";
		
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getUser());
				stmt.setString(2, vo.getMailHost());
				stmt.setString(3, vo.getMailUser());
				stmt.setString(4, vo.getMailFolder());
				stmt.setBoolean(5, vo.isActive());
				stmt.setInt(6, vo.getId());
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("updateMailAccount: void");
	}
	
	/**
	 * @param vo
	 * @throws SQLException
	 */
	public void updateMailAccountPassword(MailAccount vo) throws SQLException {
		log.debug("updateMailAccountPassword("+vo+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE mail_accounts SET ma_mpass=? WHERE ma_id=?";

		try {
			con = getConnection();
			
			if (con != null) {
				if (vo.getMailPassword().trim().length() > 0) {
					stmt = con.prepareStatement(sql);
					stmt.setString(1, vo.getMailPassword());
					stmt.setInt(2, vo.getId());
					stmt.execute();
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("updateMailAccountPassword: void");
	}
	
	/**
	 * @param vo
	 * @throws SQLException
	 */
	public void deleteMailAccount(int id) throws SQLException {
		log.debug("deleteMailAccount("+id+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM mail_accounts WHERE ma_id=?";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, id);
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("deleteMailAccount: void");
	}

	/**
	 * @param user
	 * @param filterByActive
	 * @return
	 * @throws SQLException
	 */
	public Collection<TwitterAccount> findTwitterAccountsByUser(String user, boolean filterByActive) throws SQLException {
		log.debug("findTwitterAccountsByUser("+user+")");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT ta_id, ta_user, ta_tuser, ta_active FROM twitter_accounts WHERE ta_user=? "+(filterByActive?"AND ta_active='true'":"")+" ORDER BY ta_id";
		ArrayList<TwitterAccount> al = new ArrayList<TwitterAccount>();
				
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, user);
				rs = stmt.executeQuery();

				while (rs.next()) {
					TwitterAccount ta = new TwitterAccount();
					ta.setId(rs.getInt(1));
					ta.setUser(rs.getString(2));
					ta.setTwitterUser(rs.getString(3));
					ta.setActive(rs.getBoolean(4));
					al.add(ta);
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("findTwitterAccountsByUser: "+al);
		return al;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public Collection<TwitterAccount> findAllTwitterAccounts(boolean filterByActive) throws SQLException {
		log.debug("findAllTwitterAccounts()");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT ta_id, ta_user, ta_tuser, ta_active FROM twitter_accounts "+(filterByActive?"AND ta_active='true'":"")+" ORDER BY ta_id";
		ArrayList<TwitterAccount> al = new ArrayList<TwitterAccount>();
				
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();

				while (rs.next()) {
					TwitterAccount ta = new TwitterAccount();
					ta.setId(rs.getInt(1));
					ta.setUser(rs.getString(2));
					ta.setTwitterUser(rs.getString(3));
					ta.setActive(rs.getBoolean(4));
					al.add(ta);
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("findAllTwitterAccounts: "+al);
		return al;
	}

	/**
	 * @param id
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public TwitterAccount findTwitterAccountByPk(int id) throws SQLException {
		log.debug("findTwitterAccountByPk("+id+")");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT ta_id, ta_user, ta_tuser, ta_active FROM twitter_accounts WHERE ta_id=?";
		TwitterAccount ta = null;
				
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, id);
				rs = stmt.executeQuery();

				if (rs.next()) {
					ta = new TwitterAccount();
					ta.setId(rs.getInt(1));
					ta.setUser(rs.getString(2));
					ta.setTwitterUser(rs.getString(3));
					ta.setActive(rs.getBoolean(4));
				}
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("findTwitterAccountByPk: "+ta);
		return ta;
	}
	
	/**
	 * @param vo
	 * @throws SQLException
	 */
	public void createTwitterAccount(TwitterAccount vo) throws SQLException {
		log.debug("createTwitterAccount("+vo+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO twitter_accounts (ta_user, ta_tuser, ta_active) VALUES (?, ?, ?)";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getUser());
				stmt.setString(2, vo.getTwitterUser());
				stmt.setBoolean(3, vo.isActive());
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("createTwitterAccount: void");
	}
	
	/**
	 * @param vo
	 * @throws SQLException
	 */
	public void updateTwitterAccount(TwitterAccount vo) throws SQLException {
		log.debug("updateTwitterAccount("+vo+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE twitter_accounts SET ta_user=?, ta_tuser=?, ta_active=? WHERE ta_id=?";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getUser());
				stmt.setString(2, vo.getTwitterUser());
				stmt.setBoolean(3, vo.isActive());
				stmt.setInt(4, vo.getId());
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("updateTwitterAccount: void");
	}
	
	/**
	 * @param vo
	 * @throws SQLException
	 */
	public void deleteTwitterAccount(int id) throws SQLException {
		log.debug("deleteTwitterAccount("+id+")");
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM twitter_accounts WHERE ta_id=?";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, id);
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
		
		log.debug("deleteTwitterAccount: void");
	}
}
