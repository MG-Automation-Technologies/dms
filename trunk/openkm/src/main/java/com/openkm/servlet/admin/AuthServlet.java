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
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.bean.User;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtil;

/**
 * User servlet
 */
public class AuthServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(AuthServlet.class);
	
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
			} else if (action.equals("userUpdate")) {
				userUpdate(session, request, response);
			} else if (action.equals("userDelete")) {
				userDelete(session, request, response);
			}
			
			if (action.equals("") || WebUtil.getBoolean(request, "persist")) {
				userList(session, request, response);
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
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * New user
	 */
	private void userCreate(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.info("userCreate({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			User usr = new User();
			usr.setId(WebUtil.getString(request, "usr_id"));
			usr.setName(WebUtil.getString(request, "usr_name"));
			usr.setPass(WebUtil.getString(request, "usr_pass"));
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
			sc.getRequestDispatcher("/admin/user_edit.jsp").forward(request, response);
		}
		
		log.debug("userCreate: void");
	}
	
	/**
	 * Edit user
	 */
	private void userUpdate(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("userUpdate({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			User usr = new User();
			usr.setId(WebUtil.getString(request, "usr_id"));
			usr.setName(WebUtil.getString(request, "usr_name"));
			usr.setPass(WebUtil.getString(request, "usr_pass"));
			usr.setEmail(WebUtil.getString(request, "usr_email"));
			usr.setActive(WebUtil.getBoolean(request, "usr_active"));
			List<String> usrRoles = WebUtil.getStringList(request, "usr_roles");
			for (String rolId : usrRoles) {
				usr.getRoles().add(AuthDAO.findRoleByPk(rolId));
			}
			
			AuthDAO.updateUser(usr);
			if (!usr.getPass().equals("")) {
				AuthDAO.updateUserPassword(usr.getId(), usr.getPass());
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_USER_UPDATE", usr.getId(), usr.toString());
		} else {
			ServletContext sc = getServletContext();
			String usrId = WebUtil.getString(request, "usrId");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("roles", AuthDAO.findAllRoles());
			sc.setAttribute("user", AuthDAO.findUserByPk(usrId));
			sc.getRequestDispatcher("/admin/user_edit.jsp").forward(request, response);
		}
		
		log.debug("userUpdate: void");
	}
	
	/**
	 * Update user
	 */
	private void userDelete(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("userUpdate({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			String usrId = WebUtil.getString(request, "usr_id");
			AuthDAO.deleteUser(usrId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_USER_DELETE", usrId, null);
		} else {
			ServletContext sc = getServletContext();
			String usrId = WebUtil.getString(request, "usrId");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("roles", AuthDAO.findAllRoles());
			sc.setAttribute("user", AuthDAO.findUserByPk(usrId));
			sc.getRequestDispatcher("/admin/user_edit.jsp").forward(request, response);
		}
		
		log.debug("userUpdate: void");
	}

	/**
	 * List node properties and children
	 */
	private void userList(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("list({}, {}, {})", new Object[] { session, request, response });
		String roleFilter = WebUtil.getString(request, "roleFilter");
		ServletContext sc = getServletContext();
		
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_USER_LIST", null, null);
		
		if (roleFilter.equals("")) {
			sc.setAttribute("users", AuthDAO.findAllUsers(false));
		} else {
			sc.setAttribute("roleFilter", roleFilter);
			sc.setAttribute("users", AuthDAO.findUsersByRole(false, roleFilter));
		}
		
		sc.setAttribute("roles", AuthDAO.findAllRoles());
		sc.getRequestDispatcher("/admin/user_list.jsp").forward(request, response);
		log.debug("list: void");
	}
}
