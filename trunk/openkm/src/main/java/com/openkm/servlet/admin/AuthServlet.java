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

package com.openkm.servlet.admin;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.bean.Role;
import com.openkm.dao.bean.User;
import com.openkm.principal.DatabasePrincipalAdapter;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtils;

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
		String action = WebUtils.getString(request, "action");
		String userId = request.getRemoteUser();
		updateSessionManager(request);
		
		try {
			if (action.equals("userCreate")) {
				userCreate(userId, request, response);
			} else if (action.equals("roleCreate")) {
				roleCreate(userId, request, response);
			} else if (action.equals("userEdit")) {
				userEdit(userId, request, response);
			} else if (action.equals("roleEdit")) {
				roleEdit(userId, request, response);
			} else if (action.equals("userDelete")) {
				userDelete(userId, request, response);
			} else if (action.equals("roleDelete")) {
				roleDelete(userId, request, response);
			} else if (action.equals("userActive")) {
				userActive(userId, request, response);
			} else if (action.equals("roleActive")) {
				roleActive(userId, request, response);
			}
			
			if (action.equals("") || action.equals("userActive") ||
					(action.startsWith("user") && WebUtils.getBoolean(request, "persist"))) {
				userList(userId, request, response);
			} else if (action.equals("roleList") || action.equals("roleActive") ||
					(action.startsWith("role") && WebUtils.getBoolean(request, "persist"))) {
				roleList(userId, request, response);
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		}
	}
	
	/**
	 * New user
	 */
	private void userCreate(String userId, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("userCreate({}, {}, {})", new Object[] { userId, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			User usr = new User();
			usr.setId(WebUtils.getString(request, "usr_id"));
			usr.setName(WebUtils.getString(request, "usr_name"));
			usr.setPassword(WebUtils.getString(request, "usr_password"));
			usr.setEmail(WebUtils.getString(request, "usr_email"));
			usr.setActive(WebUtils.getBoolean(request, "usr_active"));
			List<String> usrRoles = WebUtils.getStringList(request, "usr_roles");
			for (String rolId : usrRoles) {
				usr.getRoles().add(AuthDAO.findRoleByPk(rolId));
			}
			
			AuthDAO.createUser(usr);
			
			// Activity log
			UserActivity.log(userId, "ADMIN_USER_CREATE", usr.getId(), usr.toString());
		} else {
			ServletContext sc = getServletContext();
			sc.setAttribute("action", WebUtils.getString(request, "action"));
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
	private void userEdit(String userId, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("userEdit({}, {}, {})", new Object[] { userId, request, response });
		String usrId = WebUtils.getString(request, "usr_id");
		
		if (isMultipleInstancesAdmin(request) || !usrId.equals(Config.ADMIN_USER)) {
			if (WebUtils.getBoolean(request, "persist")) {
				String password = WebUtils.getString(request, "usr_password");
				User usr = new User();
				usr.setId(usrId);
				usr.setName(WebUtils.getString(request, "usr_name"));
				usr.setEmail(WebUtils.getString(request, "usr_email"));
				usr.setActive(WebUtils.getBoolean(request, "usr_active"));
				List<String> usrRoles = WebUtils.getStringList(request, "usr_roles");
				
				for (String rolId : usrRoles) {
					usr.getRoles().add(AuthDAO.findRoleByPk(rolId));
				}
				
				AuthDAO.updateUser(usr);
				
				if (!password.equals("")) {
					AuthDAO.updateUserPassword(usr.getId(), password);
				}
				
				// Activity log
				UserActivity.log(request.getRemoteUser(), "ADMIN_USER_EDIT", usr.getId(), usr.toString());
			} else {
				ServletContext sc = getServletContext();
				sc.setAttribute("action", WebUtils.getString(request, "action"));
				sc.setAttribute("persist", true);
				sc.setAttribute("roles", AuthDAO.findAllRoles());
				sc.setAttribute("usr", AuthDAO.findUserByPk(usrId));
				sc.getRequestDispatcher("/admin/user_edit.jsp").forward(request, response);
			}	
		} else {
			// Activity log
			UserActivity.log(request.getRemoteUser(), "ADMIN_ACCESS_DENIED", request.getRequestURI(), request.getQueryString());
			
			AccessDeniedException ade = new AccessDeniedException("You should not access this resource");
			sendErrorRedirect(request, response, ade);
		}
		
		log.debug("userEdit: void");
	}
	
	/**
	 * Update user
	 */
	private void userDelete(String userId, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("userDelete({}, {}, {})", new Object[] { userId, request, response });
		String usrId = WebUtils.getString(request, "usr_id");
		
		if (isMultipleInstancesAdmin(request) || !usrId.equals(Config.ADMIN_USER)) {
			if (WebUtils.getBoolean(request, "persist")) {
				AuthDAO.deleteUser(usrId);
				
				// Activity log
				UserActivity.log(request.getRemoteUser(), "ADMIN_USER_DELETE", usrId, null);
			} else {
				ServletContext sc = getServletContext();
				sc.setAttribute("action", WebUtils.getString(request, "action"));
				sc.setAttribute("persist", true);
				sc.setAttribute("roles", AuthDAO.findAllRoles());
				sc.setAttribute("usr", AuthDAO.findUserByPk(usrId));
				sc.getRequestDispatcher("/admin/user_edit.jsp").forward(request, response);
			}
		} else {
			// Activity log
			UserActivity.log(request.getRemoteUser(), "ADMIN_ACCESS_DENIED", request.getRequestURI(), request.getQueryString());
			
			AccessDeniedException ade = new AccessDeniedException("You should not access this resource");
			sendErrorRedirect(request, response, ade);
		}
		
		log.debug("userDelete: void");
	}
	
	/**
	 * Active user
	 */
	private void userActive(String userId, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("userActive({}, {}, {})", new Object[] { userId, request, response });
		String usrId = WebUtils.getString(request, "usr_id");
		
		if (isMultipleInstancesAdmin(request) || !usrId.equals(Config.ADMIN_USER)) {
			boolean active = WebUtils.getBoolean(request, "usr_active");
			AuthDAO.activeUser(usrId, active);
			
			// Activity log
			UserActivity.log(request.getRemoteUser(), "ADMIN_USER_ACTIVE", usrId, Boolean.toString(active));
		} else {
			// Activity log
			UserActivity.log(request.getRemoteUser(), "ADMIN_ACCESS_DENIED", request.getRequestURI(), request.getQueryString());
			
			AccessDeniedException ade = new AccessDeniedException("You should not access this resource");
			sendErrorRedirect(request, response, ade);
		}
		
		log.debug("userActive: void");
	}

	/**
	 * List users
	 */
	private void userList(String userId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException, PrincipalAdapterException {
		log.debug("userList({}, {}, {})", new Object[] { userId, request, response });
		String roleFilter = WebUtils.getString(request, "roleFilter");
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
		sc.setAttribute("multInstAdmin", isMultipleInstancesAdmin(request));
		sc.getRequestDispatcher("/admin/user_list.jsp").forward(request, response);
		log.debug("userList: void");
	}
	
	/**
	 * New role
	 */
	private void roleCreate(String userId, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("roleCreate({}, {}, {})", new Object[] { userId, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			Role rol = new Role();
			rol.setId(WebUtils.getString(request, "rol_id"));
			rol.setActive(WebUtils.getBoolean(request, "rol_active"));
			AuthDAO.createRole(rol);
			
			// Activity log
			UserActivity.log(userId, "ADMIN_ROLE_CREATE", rol.getId(), rol.toString());
		} else {
			ServletContext sc = getServletContext();
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("rol", null);
			sc.getRequestDispatcher("/admin/role_edit.jsp").forward(request, response);
		}
		
		log.debug("roleCreate: void");
	}
	
	/**
	 * Edit role
	 */
	private void roleEdit(String userId, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("roleEdit({}, {}, {})", new Object[] { userId, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			Role rol = new Role();
			rol.setId(WebUtils.getString(request, "rol_id"));
			rol.setActive(WebUtils.getBoolean(request, "rol_active"));
			AuthDAO.updateRole(rol);
			
			// Activity log
			UserActivity.log(userId, "ADMIN_ROLE_EDIT", rol.getId(), rol.toString());
		} else {
			ServletContext sc = getServletContext();
			String rolId = WebUtils.getString(request, "rol_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("rol", AuthDAO.findRoleByPk(rolId));
			sc.getRequestDispatcher("/admin/role_edit.jsp").forward(request, response);
		}
		
		log.debug("roleEdit: void");
	}
	
	/**
	 * Delete role
	 */
	private void roleDelete(String userId, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("roleDelete({}, {}, {})", new Object[] { userId, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			String rolId = WebUtils.getString(request, "rol_id");
			AuthDAO.deleteRole(rolId);
			
			// Activity log
			UserActivity.log(userId, "ADMIN_ROLE_DELETE", rolId, null);
		} else {
			ServletContext sc = getServletContext();
			String rolId = WebUtils.getString(request, "rol_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("rol", AuthDAO.findRoleByPk(rolId));
			sc.getRequestDispatcher("/admin/role_edit.jsp").forward(request, response);
		}
		
		log.debug("roleDelete: void");
	}
	
	/**
	 * Active role
	 */
	private void roleActive(String userId, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("roleActive({}, {}, {})", new Object[] { userId, request, response });
		String rolId = WebUtils.getString(request, "rol_id");
		boolean active = WebUtils.getBoolean(request, "rol_active");
		AuthDAO.activeRole(rolId, active);
			
		// Activity log
		UserActivity.log(userId, "ADMIN_ROLE_ACTIVE", rolId, Boolean.toString(active));
		log.debug("roleActive: void");
	}

	/**
	 * List roles
	 */
	private void roleList(String userId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException, PrincipalAdapterException {
		log.debug("roleList({}, {}, {})", new Object[] { userId, request, response });
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
			List<String> roleList = OKMAuth.getInstance().getRolesByUser(null, usrId);			
			User usr = new User();
			usr.setId(usrId);
			usr.setActive(true);
			usr.setName(OKMAuth.getInstance().getName(null, usrId));
			usr.setEmail(OKMAuth.getInstance().getMail(null, usrId));
			
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
		
		Collections.sort(usrList, new UserComparator());
		return usrList;
	}
	
	/**
	 * Convenient conversion method 
	 */
	private List<Role> str2role(List<String> strList) {
		List<Role> roleList = new ArrayList<Role>();
		
		for (String id : strList) {
			Role rol = new Role();
			rol.setId(id);
			rol.setActive(true);
			roleList.add(rol);
		}
		
		Collections.sort(roleList, new RoleComparator());
		return roleList;
	}
	
	/**
	 * User comparator
	 */
	private class UserComparator implements Comparator<User> {
		@Override
		public int compare(User arg0, User arg1) {
			if (arg0 != null && arg1 != null) {
				return arg0.getId().compareTo(arg1.getId());
			} else {
				return 0;
			}
		}	
	}
	
	/**
	 * Role comparator
	 */
	private class RoleComparator implements Comparator<Role> {
		@Override
		public int compare(Role arg0, Role arg1) {
			if (arg0 != null && arg1 != null) {
				return arg0.getId().compareTo(arg1.getId());
			} else {
				return 0;
			}
		}	
	}
}
