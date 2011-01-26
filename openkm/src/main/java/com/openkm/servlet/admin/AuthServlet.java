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

package com.openkm.servlet.admin;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.bean.Role;
import com.openkm.dao.bean.User;
import com.openkm.principal.DatabasePrincipalAdapter;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtil;

/**
 * User servlet
 */
public class AuthServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(AuthServlet.class);
	private static boolean db = Config.PRINCIPAL_ADAPTER.equals(DatabasePrincipalAdapter.class.getCanonicalName());
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			
			if (action.equals("userCreate")) {
				userCreate(session, request, response);
			} else if (action.equals("roleCreate")) {
				roleCreate(session, request, response);
			} else if (action.equals("userEdit")) {
				userEdit(session, request, response);
			} else if (action.equals("roleEdit")) {
				roleEdit(session, request, response);
			} else if (action.equals("userDelete")) {
				userDelete(session, request, response);
			} else if (action.equals("roleDelete")) {
				roleDelete(session, request, response);
			} else if (action.equals("userActive")) {
				userActive(session, request, response);
			} else if (action.equals("roleActive")) {
				roleActive(session, request, response);
			}
			
			if (action.equals("") || action.equals("userActive") ||
					(action.startsWith("user") && WebUtil.getBoolean(request, "persist"))) {
				userList(session, request, response);
			} else if (action.equals("roleList") || action.equals("roleActive") ||
					(action.startsWith("role") && WebUtil.getBoolean(request, "persist"))) {
				roleList(session, request, response);
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * New user
	 */
	private void userCreate(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("userCreate({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			User usr = new User();
			usr.setId(WebUtil.getString(request, "usr_id"));
			usr.setName(WebUtil.getString(request, "usr_name"));
			usr.setPassword(WebUtil.getString(request, "usr_password"));
			usr.setEmail(WebUtil.getString(request, "usr_email"));
			usr.setActive(WebUtil.getBoolean(request, "usr_active"));
			List<String> usrRoles = WebUtil.getStringList(request, "usr_roles");
			for (String rolId : usrRoles) {
				usr.getRoles().add(AuthDAO.findRoleByPk(rolId));
			}
			
			AuthDAO.createUser(usr);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_USER_CREATE", usr.getId(), usr.toString());
		} else {
			ServletContext sc = getServletContext();
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("roles", AuthDAO.findAllRoles());
			sc.setAttribute("usr", null);
			sc.getRequestDispatcher("/admin/user_edit.jsp").forward(request, response);
		}
		
		log.debug("userCreate: void");
	}
	
	/**
	 * Edit user
	 */
	private void userEdit(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("userEdit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			String password = WebUtil.getString(request, "usr_password");
			User usr = new User();
			usr.setId(WebUtil.getString(request, "usr_id"));
			usr.setName(WebUtil.getString(request, "usr_name"));
			usr.setEmail(WebUtil.getString(request, "usr_email"));
			usr.setActive(WebUtil.getBoolean(request, "usr_active"));
			List<String> usrRoles = WebUtil.getStringList(request, "usr_roles");
			
			for (String rolId : usrRoles) {
				usr.getRoles().add(AuthDAO.findRoleByPk(rolId));
			}
			
			AuthDAO.updateUser(usr);
			
			if (!password.equals("")) {
				AuthDAO.updateUserPassword(usr.getId(), password);
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_USER_EDIT", usr.getId(), usr.toString());
		} else {
			ServletContext sc = getServletContext();
			String usrId = WebUtil.getString(request, "usr_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("roles", AuthDAO.findAllRoles());
			sc.setAttribute("usr", AuthDAO.findUserByPk(usrId));
			sc.getRequestDispatcher("/admin/user_edit.jsp").forward(request, response);
		}
		
		log.debug("userEdit: void");
	}
	
	/**
	 * Update user
	 */
	private void userDelete(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("userDelete({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			String usrId = WebUtil.getString(request, "usr_id");
			AuthDAO.deleteUser(usrId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_USER_DELETE", usrId, null);
		} else {
			ServletContext sc = getServletContext();
			String usrId = WebUtil.getString(request, "usr_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("roles", AuthDAO.findAllRoles());
			sc.setAttribute("usr", AuthDAO.findUserByPk(usrId));
			sc.getRequestDispatcher("/admin/user_edit.jsp").forward(request, response);
		}
		
		log.debug("userDelete: void");
	}
	
	/**
	 * Active user
	 */
	private void userActive(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("userActive({}, {}, {})", new Object[] { session, request, response });
		String usrId = WebUtil.getString(request, "usr_id");
		boolean active = WebUtil.getBoolean(request, "usr_active");
		AuthDAO.activeUser(usrId, active);
			
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_USER_ACTIVE", usrId, Boolean.toString(active));
		log.debug("userActive: void");
	}

	/**
	 * List users
	 */
	private void userList(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException, PrincipalAdapterException {
		log.debug("userList({}, {}, {})", new Object[] { session, request, response });
		String roleFilter = WebUtil.getString(request, "roleFilter");
		ServletContext sc = getServletContext();
		sc.setAttribute("roleFilter", roleFilter);
		
		if (roleFilter.equals("")) {
			if (db) {
				sc.setAttribute("users", AuthDAO.findAllUsers(false));
				sc.setAttribute("roles", AuthDAO.findAllRoles());
			} else {
				sc.setAttribute("users", str2user(OKMAuth.getInstance().getUsers(null)));
				sc.setAttribute("roles", str2role(OKMAuth.getInstance().getRoles(null)));
			}
		} else {
			if (db) {
				sc.setAttribute("users", AuthDAO.findUsersByRole(roleFilter, false));
				sc.setAttribute("roles", AuthDAO.findAllRoles());
			} else {
				sc.setAttribute("users", str2user(OKMAuth.getInstance().getUsersByRole(null, roleFilter)));
				sc.setAttribute("roles", str2role(OKMAuth.getInstance().getRoles(null)));
			}
		}
		
		sc.setAttribute("db", db);
		sc.getRequestDispatcher("/admin/user_list.jsp").forward(request, response);
		log.debug("userList: void");
	}
	
	/**
	 * New role
	 */
	private void roleCreate(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("roleCreate({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			Role rol = new Role();
			rol.setId(WebUtil.getString(request, "rol_id"));
			rol.setActive(WebUtil.getBoolean(request, "rol_active"));
			AuthDAO.createRole(rol);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_ROLE_CREATE", rol.getId(), rol.toString());
		} else {
			ServletContext sc = getServletContext();
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("rol", null);
			sc.getRequestDispatcher("/admin/role_edit.jsp").forward(request, response);
		}
		
		log.debug("roleCreate: void");
	}
	
	/**
	 * Edit role
	 */
	private void roleEdit(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("roleEdit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			Role rol = new Role();
			rol.setId(WebUtil.getString(request, "rol_id"));
			rol.setActive(WebUtil.getBoolean(request, "rol_active"));
			AuthDAO.updateRole(rol);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_ROLE_EDIT", rol.getId(), rol.toString());
		} else {
			ServletContext sc = getServletContext();
			String rolId = WebUtil.getString(request, "rol_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("rol", AuthDAO.findRoleByPk(rolId));
			sc.getRequestDispatcher("/admin/role_edit.jsp").forward(request, response);
		}
		
		log.debug("roleEdit: void");
	}
	
	/**
	 * Delete role
	 */
	private void roleDelete(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("roleDelete({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			String rolId = WebUtil.getString(request, "rol_id");
			AuthDAO.deleteRole(rolId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_ROLE_DELETE", rolId, null);
		} else {
			ServletContext sc = getServletContext();
			String rolId = WebUtil.getString(request, "rol_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("rol", AuthDAO.findRoleByPk(rolId));
			sc.getRequestDispatcher("/admin/role_edit.jsp").forward(request, response);
		}
		
		log.debug("roleDelete: void");
	}
	
	/**
	 * Active role
	 */
	private void roleActive(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("roleActive({}, {}, {})", new Object[] { session, request, response });
		String rolId = WebUtil.getString(request, "rol_id");
		boolean active = WebUtil.getBoolean(request, "rol_active");
		AuthDAO.activeRole(rolId, active);
			
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_ROLE_ACTIVE", rolId, Boolean.toString(active));
		log.debug("roleActive: void");
	}

	/**
	 * List roles
	 */
	private void roleList(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException, PrincipalAdapterException {
		log.debug("roleList({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		
		if (db) {
			sc.setAttribute("roles", AuthDAO.findAllRoles());
		} else {
			sc.setAttribute("roles", str2role(OKMAuth.getInstance().getRoles(null)));
		}
		
		sc.setAttribute("db", db);
		sc.getRequestDispatcher("/admin/role_list.jsp").forward(request, response);
		log.debug("roleList: void");
	}
	
	/**
	 * Convenient conversion method 
	 */
	private List<User> str2user(List<String> strList) throws PrincipalAdapterException {
		List<User> usrList = new ArrayList<User>();
		
		for (String usrId : strList) {
			List<String> userList = new ArrayList<String>();
			userList.add(usrId);
			List<String> mailList = OKMAuth.getInstance().getMails(null, userList);
			List<String> roleList = OKMAuth.getInstance().getRolesByUser(null, usrId);			
			User usr = new User();
			usr.setId(usrId);
			usr.setActive(true);
			usr.setName(Config.PRINCIPAL_ADAPTER);
			
			if (!mailList.isEmpty()) {
				usr.setEmail(mailList.iterator().next());
			}
			
			if (!roleList.isEmpty()) {
				Set<Role> roles = new HashSet<Role>();
				for (String rolId : roleList) {
					Role rol = new Role();
					rol.setId(rolId);
					rol.setActive(true);
					roles.add(rol);
				}
				usr.setRoles(roles);
			}
			
			usrList.add(usr);
		}
		
		return usrList;
	}
	
	/**
	 * Convenient conversion method 
	 */
	private List<Role> str2role(List<String> strList) {
		List<Role> rolList = new ArrayList<Role>();
		
		for (String id : strList) {
			Role rol = new Role();
			rol.setId(id);
			rol.setActive(true);
			rolList.add(rol);
		}
		
		return rolList;
	}
}
