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

package es.git.openkm.backend.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import es.git.openkm.backend.client.OKMException;
import es.git.openkm.backend.client.bean.GWTActivity;
import es.git.openkm.backend.client.bean.GWTSessionInfo;
import es.git.openkm.backend.client.bean.GWTUser;
import es.git.openkm.backend.client.config.ErrorCode;
import es.git.openkm.backend.client.service.OKMUserService;
import es.git.openkm.bean.SessionInfo;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.Config;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.SessionManager;
import es.git.openkm.dao.ActivityDAO;
import es.git.openkm.dao.AuthDAO;
import es.git.openkm.dao.bean.Activity;
import es.git.openkm.dao.bean.ActivityFilter;
import es.git.openkm.dao.bean.Role;
import es.git.openkm.dao.bean.User;
import es.git.openkm.module.direct.DirectAuthModule;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMUserServletAdmin"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMUserServletAdmin"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMUserServletAdmin extends RemoteServiceServlet implements OKMUserService {
	
	private static Logger log = LoggerFactory.getLogger(OKMUserServletAdmin.class);
	private static final long serialVersionUID = 2638205115826644606L;
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMUserService#getLogedUsers()
	 */
	public List<GWTSessionInfo> getLogedUsers() throws OKMException {
		log.debug("getLogedUsers()");
		List<GWTSessionInfo> al = new ArrayList<GWTSessionInfo>();

		SessionManager sm = SessionManager.getInstance();

		for (Iterator<String> it = sm.getTokens().iterator(); it.hasNext(); ) {
			String token = it.next();
			SessionInfo si = sm.getInfo(token);
			al.add(Util.copy(si, token));
		}
		
		log.debug("getLogedUsers: ");
		return al;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMUserService#logout(java.lang.String)
	 */
	public void logout(String token) throws OKMException {
		log.debug("logout("+token+")");
		try {
			new DirectAuthModule().logout(token);
			
			log.debug("logout: ");
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServletAdmin, ErrorCode.CAUSE_AccessDenied), e.getMessage());
			
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServletAdmin, ErrorCode.CAUSE_Repository), e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMUserService#findAllUsers()
	 */
	public List<GWTUser>  findAllUsers() throws OKMException {
		log.debug("findAllUsers()");
		List<GWTUser> al = new ArrayList<GWTUser>();
		
		try {
			for (Iterator<User> it = AuthDAO.getInstance().findAllUsers().iterator(); it.hasNext(); ) {
				al.add(Util.copy(it.next()));
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServletAdmin, ErrorCode.CAUSE_SQLException), e.getMessage());
		}
		
		log.debug("findAllUsers: ");
		return al;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMUserService#createUser(es.git.openkm.backend.client.bean.GWTUser)
	 */
	public void createUser(GWTUser gWTUser) throws OKMException {
		log.debug("createUser()");
		try {
			List<String> roleList = new ArrayList<String>();
			for (Iterator it = AuthDAO.getInstance().findAllRoles().iterator(); it.hasNext();) {
				Role role = (Role) it.next();
				roleList.add(role.getId());
			}
			
			AuthDAO.getInstance().createUser(Util.copy(gWTUser));
			for (Iterator it = gWTUser.getRoles().iterator(); it.hasNext();) {
				String role = (String) it.next();
				
				// If role not exist must create
				if (!roleList.contains(role)) {
					Role newRole = new Role();
					newRole.setId(role);
					AuthDAO.getInstance().createRole(newRole);
				}
				
				AuthDAO.getInstance().grantRole(gWTUser.getId(), role);
			}
		}  catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServletAdmin, ErrorCode.CAUSE_SQLException), e.getMessage());
		}
		log.debug("createUser: ");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMUserService#updateUser(es.git.openkm.backend.client.bean.GWTUser)
	 */
	public void updateUser(GWTUser gWTUser) throws OKMException {
		log.debug("updateUser()");
		List<String> roleList = new ArrayList<String>();
		try {
			for (Iterator it = AuthDAO.getInstance().findAllRoles().iterator(); it.hasNext();) {
				Role role = (Role) it.next();
				roleList.add(role.getId());
			}
			
			AuthDAO.getInstance().deleteUserRoles(Util.copy(gWTUser));
			AuthDAO.getInstance().updateUser(Util.copy(gWTUser));
			for (Iterator it = gWTUser.getRoles().iterator(); it.hasNext();) {
				String role = (String) it.next();
				
				// If role not exist must create
				if (!roleList.contains(role)) {
					Role newRole = new Role();
					newRole.setId(role);
					AuthDAO.getInstance().createRole(newRole);
				}
				
				// Adds grant role to user
				AuthDAO.getInstance().grantRole(gWTUser.getId(), role);
			}
		}  catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServletAdmin, ErrorCode.CAUSE_SQLException), e.getMessage());
		}
		log.debug("updateUser: ");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMUserService#deleteUser(es.git.openkm.backend.client.bean.GWTUser)
	 */
	public void deleteUser(GWTUser gWTUser) throws OKMException {
		log.debug("deleteUser()");
		try {
			AuthDAO.getInstance().deleteUser(Util.copy(gWTUser));
		}  catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServletAdmin, ErrorCode.CAUSE_SQLException), e.getMessage());
		}
		log.debug("deleteUser: ");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMUserService#findAllRoles()
	 */
	public List<String> findAllRoles() throws OKMException {
		log.debug("findAllUsers()");
		List<String> al = new ArrayList<String>();
		
		try {
			for (Iterator it = AuthDAO.getInstance().findAllRoles().iterator(); it.hasNext();) {
				Role role = (Role) it.next();
				al.add(role.getId());
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServletAdmin, ErrorCode.CAUSE_SQLException), e.getMessage());
		}
		
		log.debug("findAllUsers: ");
		return al;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMUserService#findActivityByFilter(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<GWTActivity> findActivityByFilter(Date date_begin, Date date_end, String user, String action) throws OKMException {
		log.debug("findActivityByFilter(date_begin:)" + date_begin + ", date_end:" + date_end + ", user:" + user + ", action:" + action);
		List<GWTActivity> al = new ArrayList<GWTActivity>();
		
		try {
			ActivityDAO dao = ActivityDAO.getInstance();
			ActivityFilter filter = new ActivityFilter();
			Calendar begin = Calendar.getInstance();
			begin.setTime(date_begin);
			filter.setActDateBegin(begin);
			Calendar end = Calendar.getInstance();
			end.setTime(date_end);
			filter.setActDateEnd(end);
			filter.setActUser(user);
			filter.setActAction(action);
			Collection activityCol = dao.findByFilter(filter);
			
			for (Iterator it = activityCol.iterator(); it.hasNext();) {
				Activity vo = (Activity) it.next();
				al.add(Util.copy(vo));
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMAuthServletAdmin, ErrorCode.CAUSE_SQLException), e.getMessage());
		}
		
		log.debug("findActivityByFilter: ");
		return al;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.backend.client.service.OKMUserService#getRemoteUser()
	 */
	public Boolean isAdmin() {
		log.debug("isAdmin()");
		Boolean ret = getThreadLocalRequest().isUserInRole(Config.DEFAULT_ADMIN_ROLE);
		log.debug("isAdmin: "+ret);
		return ret;
	}
}
