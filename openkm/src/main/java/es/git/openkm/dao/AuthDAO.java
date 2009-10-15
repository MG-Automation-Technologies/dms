/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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

package es.git.openkm.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.core.Config;
import es.git.openkm.dao.bean.MailAccount;
import es.git.openkm.dao.bean.TwitterAccount;
import es.git.openkm.dao.bean.Role;
import es.git.openkm.dao.bean.User;
import es.git.openkm.util.SecureStore;

public class AuthDAO extends AbstractDAO {
	private static Logger log = LoggerFactory.getLogger(AuthDAO.class);
	private static AuthDAO instance = null;

	/* (non-Javadoc)
	 * @see es.git.openkm.dao.AbstractDAO#getDataSourceName()
	 */
	protected String getDataSourceName() {
		return "java:/OKMAuth"+Config.INSTALL+"DS";
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.dao.AbstractDAO#getTableName()
	 */
	protected String getTableName() {
		return "users";
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.dao.AbstractDAO#getSchema()
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
	}

	/**
	 * Update user in database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void updateUser(User vo) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE users set usr_email=?, usr_active=?, usr_name=? where usr_id=?";

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
	}
	
	/**
	 * Update user in database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void updatePassword(User vo) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE users set usr_pass=? where usr_id=?";

		try {
			con = getConnection();
			
			if (con != null && vo.getPass().length()>0) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, SecureStore.md5Encode(vo.getPass().getBytes()));
				stmt.setString(2, vo.getId());
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
	}
	
	/**
	 * Delete user from database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void deleteUser(User vo) throws SQLException {
		Connection con = null;
		PreparedStatement stmtUser = null;
		PreparedStatement stmtRole = null;
		String sqlUser = "DELETE FROM users WHERE usr_id=?";
		String sqlRole = "DELETE FROM user_role WHERE ur_user=?";

		try {
			con = getConnection();
			
			if (con != null) {
				stmtUser = con.prepareStatement(sqlUser);
				stmtUser.setString(1, vo.getId());
				stmtUser.execute();
				
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
	}
	
	/**
	 * Get all users in database
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Collection<User> findAllUsers() throws SQLException {
		Connection con = null;
		PreparedStatement stmtUser = null;
		PreparedStatement stmtUserRoles = null;
		ResultSet rsUser = null;
		ResultSet rsUserRoles = null;
		String sqlUser = "SELECT usr_id, usr_name, usr_email, usr_active, usr_pass FROM users ORDER BY usr_id";
		String sqlUserRoles = "SELECT ur_role FROM user_role WHERE ur_user=? order by ur_role";
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
		
		return al;
	}

	/**
	 * Get all users in database
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Collection<User> findUsersByRole(String role) throws SQLException {
		Connection con = null;
		PreparedStatement stmtUser = null;
		PreparedStatement stmtUserRoles = null;
		ResultSet rsUser = null;
		ResultSet rsUserRoles = null;
		String sqlUser = "SELECT usr_id, usr_name, usr_email, usr_active, usr_pass FROM users, user_role WHERE ur_user=usr_id AND ur_role=? ORDER BY usr_id";
		String sqlUserRoles = "SELECT ur_role FROM user_role WHERE ur_user = ? order by ur_role";
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
		Connection con = null;
		PreparedStatement stmtUser = null;
		PreparedStatement stmtUserRoles = null;
		ResultSet rsUser = null;
		ResultSet rsUserRoles = null;
		String sqlUser = "SELECT usr_id, usr_name, usr_email, usr_active, usr_pass FROM users WHERE usr_id=?";
		String sqlUserRoles = "SELECT ur_role FROM user_role WHERE ur_user=? order by ur_role";
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
		
		return user;
	}

	/**
	 * Create role in database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void createRole(Role vo) throws SQLException {
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
	}

	/**
	 * Delete role from database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void deleteRole(Role vo) throws SQLException {
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
	}

	/**
	 * Get all roles in database
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Collection<Role> findAllRoles() throws SQLException {
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
		
		return al;
	}
	
	/**
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public Role findRoleByPk(String roleId) throws SQLException {
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
	}

	/**
	 * Revoke role from user
	 * 
	 * @param user
	 * @param role
	 * @throws SQLException
	 */
	public void revokeRole(String user, String role) throws SQLException {
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
	}
	
	/**
	 * Delete user roles from database
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void deleteUserRoles(User vo) throws SQLException {
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
		String sql = "SELECT ma_user, ma_mhost, ma_mfolder, ma_muser, ma_mpass, ma_active FROM mail_accounts WHERE ma_user=?"+(filterByActive?" AND ma_active='true'":"");
		ArrayList<MailAccount> al = new ArrayList<MailAccount>();
				
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, user);
				rs = stmt.executeQuery();

				while (rs.next()) {
					MailAccount ma = new MailAccount();
					ma.setUser(rs.getString(1));
					ma.setMailHost(rs.getString(2));
					ma.setMailFolder(rs.getString(3));
					ma.setMailUser(rs.getString(4));
					ma.setMailPassword(rs.getString(5));
					ma.setActive(rs.getBoolean(6));
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
	public Collection<MailAccount> findAllMailAccounts() throws SQLException {
		log.debug("findAllMailAccounts()");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT ma_user, ma_mhost, ma_mfolder, ma_muser, ma_mpass, ma_active FROM mail_accounts";
		ArrayList<MailAccount> al = new ArrayList<MailAccount>();
				
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();

				while (rs.next()) {
					MailAccount ma = new MailAccount();
					ma.setUser(rs.getString(1));
					ma.setMailHost(rs.getString(2));
					ma.setMailFolder(rs.getString(3));
					ma.setMailUser(rs.getString(4));
					ma.setMailPassword(rs.getString(5));
					ma.setActive(rs.getBoolean(6));
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
	public MailAccount findMailAccountByPk(String user, String mailHost, String mailUser) throws SQLException {
		log.debug("findMailAccountByPk("+user+", "+mailHost+", "+mailUser+")");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT ma_user, ma_mhost, ma_mfolder, ma_muser, ma_mpass, ma_active FROM mail_accounts WHERE ma_user=? AND ma_mhost=? AND ma_muser=?";
		MailAccount ma = null;
				
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, user);
				stmt.setString(2, mailHost);
				stmt.setString(3, mailUser);
				rs = stmt.executeQuery();

				if (rs.next()) {
					ma = new MailAccount();
					ma.setUser(rs.getString(1));
					ma.setMailHost(rs.getString(2));
					ma.setMailFolder(rs.getString(3));
					ma.setMailUser(rs.getString(4));
					ma.setMailPassword(rs.getString(5));
					ma.setActive(rs.getBoolean(6));
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
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE mail_accounts SET ";
		
		if ( vo.getMailPassword().length()>0 ) {
			sql += "ma_mpass=?, ";	
		}
		
		sql += "ma_mfolder=?, ma_active=? WHERE ma_user=? AND ma_mhost=? AND ma_muser=?";

		try {
			con = getConnection();
			int count = 1;
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				if ( vo.getMailPassword().length()>0 ) {
					stmt.setString(count++, vo.getMailPassword());
				}
				stmt.setString(count++, vo.getMailFolder());
				stmt.setBoolean(count++, vo.isActive());
				stmt.setString(count++, vo.getUser());
				stmt.setString(count++, vo.getMailHost());
				stmt.setString(count++, vo.getMailUser());
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
	}
	
	/**
	 * @param vo
	 * @throws SQLException
	 */
	public void deleteMailAccount(MailAccount vo) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM mail_accounts WHERE ma_user=? AND ma_mhost=? AND ma_muser=?";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getUser());
				stmt.setString(2, vo.getMailHost());
				stmt.setString(3, vo.getMailUser());
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
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
		String sql = "SELECT ta_user, ta_tuser, ta_active FROM twitter_accounts WHERE ta_user=?"+(filterByActive?" AND ta_active='true'":"");
		ArrayList<TwitterAccount> al = new ArrayList<TwitterAccount>();
				
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, user);
				rs = stmt.executeQuery();

				while (rs.next()) {
					TwitterAccount ta = new TwitterAccount();
					ta.setUser(rs.getString(1));
					ta.setTwitterUser(rs.getString(2));
					ta.setActive(rs.getBoolean(3));
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
	public Collection<TwitterAccount> findAllTwitterAccounts() throws SQLException {
		log.debug("findAllTwitterAccounts()");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT ta_user, ta_tuser, ta_active FROM twitter_accounts";
		ArrayList<TwitterAccount> al = new ArrayList<TwitterAccount>();
				
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();

				while (rs.next()) {
					TwitterAccount ta = new TwitterAccount();
					ta.setUser(rs.getString(1));
					ta.setTwitterUser(rs.getString(2));
					ta.setActive(rs.getBoolean(3));
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
	public TwitterAccount findTwitterAccountByPk(String user, String twitterUser) throws SQLException {
		log.debug("findTwitterAccountByPk("+user+", "+twitterUser+")");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT ta_user, ta_tuser, ta_active FROM twitter_accounts WHERE ta_user=? AND ta_tuser=?";
		TwitterAccount ta = null;
				
		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, user);
				stmt.setString(2, twitterUser);
				rs = stmt.executeQuery();

				if (rs.next()) {
					ta = new TwitterAccount();
					ta.setUser(rs.getString(1));
					ta.setTwitterUser(rs.getString(2));
					ta.setActive(rs.getBoolean(3));
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
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE twitter_accounts SET ta_active=? WHERE ta_user=? AND ta_tuser=?";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setBoolean(1, vo.isActive());
				stmt.setString(2, vo.getUser());
				stmt.setString(3, vo.getTwitterUser());
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
	}
	
	/**
	 * @param vo
	 * @throws SQLException
	 */
	public void deleteTwitterAccount(TwitterAccount vo) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM twitter_accounts WHERE ta_user=? AND ta_tuser=?";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getUser());
				stmt.setString(2, vo.getTwitterUser());
				stmt.execute();
			} else {
				log.error("Can't connect to auth database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
	}
}
