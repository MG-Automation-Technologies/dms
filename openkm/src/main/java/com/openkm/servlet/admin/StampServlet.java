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
import java.util.HashSet;
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
import com.openkm.dao.bean.Role;
import com.openkm.dao.bean.User;
import com.openkm.dao.bean.extension.StampText;
import com.openkm.dao.extension.StampImageDAO;
import com.openkm.dao.extension.StampTextDAO;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtils;

/**
 * Stamp servlet
 */
public class StampServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(StampServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtils.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			
			if (action.equals("textCreate")) {
				textCreate(session, request, response);
			} else if (action.equals("imageCreate")) {
				imageCreate(session, request, response);
			} else if (action.equals("textEdit")) {
				textEdit(session, request, response);
			} else if (action.equals("imageEdit")) {
				imageEdit(session, request, response);
			} else if (action.equals("textDelete")) {
				textDelete(session, request, response);
			} else if (action.equals("imageDelete")) {
				imageDelete(session, request, response);
			} else if (action.equals("textActive")) {
				textActive(session, request, response);
			} else if (action.equals("imageActive")) {
				imageActive(session, request, response);
			}
			
			if (action.equals("") || action.equals("textList") || action.equals("textActive") ||
					(action.startsWith("text") && WebUtils.getBoolean(request, "persist"))) {
				textList(session, request, response);
			} else if (action.equals("imageList") || action.equals("imageActive") ||
					(action.startsWith("image") && WebUtils.getBoolean(request, "persist"))) {
				imageList(session, request, response);
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
	 * New text stamp
	 */
	private void textCreate(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("textCreate({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			StampText st = new StampText();
			st.setName(WebUtils.getString(request, "st_name"));
			st.setDescription(WebUtils.getString(request, "st_description"));
			st.setText(WebUtils.getString(request, "st_text"));
			st.setLayer(WebUtils.getInt(request, "st_layer"));
			st.setOpacity(WebUtils.getFloat(request, "st_opacity"));
			st.setSize(WebUtils.getInt(request, "st_size"));
			st.setColor(WebUtils.getInt(request, "st_color"));
			st.setRotation(WebUtils.getInt(request, "st_rotation"));
			st.setExprX(WebUtils.getString(request, "st_expr_x"));
			st.setExprY(WebUtils.getString(request, "st_expr_y"));
			st.setActive(WebUtils.getBoolean(request, "st_active"));
			
			List<String> users = WebUtils.getStringList(request, "st_users");
			st.setUsers(new HashSet<String>(users));
			
			StampTextDAO.create(st);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_STAMP_TEXT_CREATE", null, st.toString());
		} else {
			ServletContext sc = getServletContext();
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("users", AuthDAO.findAllUsers(true));
			sc.setAttribute("stamp", null);
			sc.getRequestDispatcher("/admin/stamp_text_edit.jsp").forward(request, response);
		}
		
		log.debug("textCreate: void");
	}
	
	/**
	 * Edit text stamp
	 */
	private void textEdit(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("textEdit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			String password = WebUtils.getString(request, "usr_password");
			User usr = new User();
			usr.setId(WebUtils.getString(request, "usr_id"));
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
			UserActivity.log(session.getUserID(), "ADMIN_USER_EDIT", usr.getId(), usr.toString());
		} else {
			ServletContext sc = getServletContext();
			String usrId = WebUtils.getString(request, "usr_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("roles", AuthDAO.findAllRoles());
			sc.setAttribute("usr", AuthDAO.findUserByPk(usrId));
			sc.getRequestDispatcher("/admin/user_edit.jsp").forward(request, response);
		}
		
		log.debug("textEdit: void");
	}
	
	/**
	 * Delete text stamp
	 */
	private void textDelete(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("textDelete({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			String usrId = WebUtils.getString(request, "usr_id");
			AuthDAO.deleteUser(usrId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_USER_DELETE", usrId, null);
		} else {
			ServletContext sc = getServletContext();
			String usrId = WebUtils.getString(request, "usr_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("roles", AuthDAO.findAllRoles());
			sc.setAttribute("usr", AuthDAO.findUserByPk(usrId));
			sc.getRequestDispatcher("/admin/user_edit.jsp").forward(request, response);
		}
		
		log.debug("textDelete: void");
	}
	
	/**
	 * Active text stamp
	 */
	private void textActive(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("textActive({}, {}, {})", new Object[] { session, request, response });
		String stId = WebUtils.getString(request, "st_id");
		boolean active = WebUtils.getBoolean(request, "st_active");
		StampTextDAO.active(stId, active);
			
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_STAMP_TEXT_ACTIVE", stId, Boolean.toString(active));
		log.debug("textActive: void");
	}

	/**
	 * List text stamp
	 */
	private void textList(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException, PrincipalAdapterException {
		log.debug("textList({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		sc.setAttribute("stamps", StampTextDAO.findAll());
		sc.getRequestDispatcher("/admin/stamp_text_list.jsp").forward(request, response);
		log.debug("textList: void");
	}
	
	/**
	 * New image stamp
	 */
	private void imageCreate(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("imageCreate({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			Role rol = new Role();
			rol.setId(WebUtils.getString(request, "si_id"));
			rol.setActive(WebUtils.getBoolean(request, "rol_active"));
			AuthDAO.createRole(rol);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_ROLE_CREATE", rol.getId(), rol.toString());
		} else {
			ServletContext sc = getServletContext();
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("rol", null);
			sc.getRequestDispatcher("/admin/role_edit.jsp").forward(request, response);
		}
		
		log.debug("imageCreate: void");
	}
	
	/**
	 * Edit image stamp
	 */
	private void imageEdit(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("imageEdit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			Role rol = new Role();
			rol.setId(WebUtils.getString(request, "rol_id"));
			rol.setActive(WebUtils.getBoolean(request, "rol_active"));
			AuthDAO.updateRole(rol);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_ROLE_EDIT", rol.getId(), rol.toString());
		} else {
			ServletContext sc = getServletContext();
			String rolId = WebUtils.getString(request, "rol_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("rol", AuthDAO.findRoleByPk(rolId));
			sc.getRequestDispatcher("/admin/role_edit.jsp").forward(request, response);
		}
		
		log.debug("imageEdit: void");
	}
	
	/**
	 * Delete image stamp
	 */
	private void imageDelete(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("imageDelete({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			String rolId = WebUtils.getString(request, "rol_id");
			AuthDAO.deleteRole(rolId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_ROLE_DELETE", rolId, null);
		} else {
			ServletContext sc = getServletContext();
			String rolId = WebUtils.getString(request, "rol_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("rol", AuthDAO.findRoleByPk(rolId));
			sc.getRequestDispatcher("/admin/role_edit.jsp").forward(request, response);
		}
		
		log.debug("imageDelete: void");
	}
	
	/**
	 * Active image stamp
	 */
	private void imageActive(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("imageActive({}, {}, {})", new Object[] { session, request, response });
		String siId = WebUtils.getString(request, "si_id");
		boolean active = WebUtils.getBoolean(request, "si_active");
		StampImageDAO.active(siId, active);
			
		// Activity log
		UserActivity.log(session.getUserID(), "ADMIN_STAMP_IMAGE_ACTIVE", siId, Boolean.toString(active));
		log.debug("imageActive: void");
	}

	/**
	 * List image stamp
	 */
	private void imageList(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException, PrincipalAdapterException {
		log.debug("imageList({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		sc.setAttribute("stamps", StampImageDAO.findAll());
		sc.getRequestDispatcher("/admin/stamp_image_list.jsp").forward(request, response);
		log.debug("imageList: void");
	}
}
