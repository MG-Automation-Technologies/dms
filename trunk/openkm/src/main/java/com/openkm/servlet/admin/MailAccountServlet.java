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
import com.openkm.dao.MailAccountDAO;
import com.openkm.dao.bean.MailAccount;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtil;

/**
 * User mail accounts servlet
 */
public class MailAccountServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(MailAccountServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			
			if (action.equals("create")) {
				create(session, request, response);
			} else if (action.equals("edit")) {
				edit(session, request, response);
			} else if (action.equals("delete")) {
				delete(session, request, response);
			}
			
			if (action.equals("") || WebUtil.getBoolean(request, "persist")) {
				list(session, request, response);
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
	private void create(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.info("create({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			MailAccount ma = new MailAccount();
			ma.setUser(WebUtil.getString(request, "ma_user"));
			ma.setMailUser(WebUtil.getString(request, "ma_muser"));
			ma.setMailPassword(WebUtil.getString(request, "ma_mpass"));
			ma.setMailHost(WebUtil.getString(request, "ma_mhost"));
			ma.setMailFolder(WebUtil.getString(request, "ma_mfolder"));
			ma.setActive(WebUtil.getBoolean(request, "ma_active"));
			MailAccountDAO.create(ma);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_MAIL_ACCOUNT_CREATE", ma.getUser(), ma.toString());
		} else {
			ServletContext sc = getServletContext();
			MailAccount ma = new MailAccount();
			ma.setUser(WebUtil.getString(request, "ma_user"));
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("ma", ma);
			sc.getRequestDispatcher("/admin/mail_edit.jsp").forward(request, response);
		}
		
		log.debug("create: void");
	}
	
	/**
	 * Edit user
	 */
	private void edit(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("edit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			MailAccount ma = new MailAccount();
			ma.setId(WebUtil.getInt(request, "ma_id"));
			ma.setUser(WebUtil.getString(request, "ma_user"));
			ma.setMailUser(WebUtil.getString(request, "ma_muser"));
			ma.setMailPassword(WebUtil.getString(request, "ma_mpass"));
			ma.setMailHost(WebUtil.getString(request, "ma_mhost"));
			ma.setMailFolder(WebUtil.getString(request, "ma_mfolder"));
			ma.setActive(WebUtil.getBoolean(request, "m_active"));
			MailAccountDAO.update(ma);
			
			if (!ma.getMailPassword().equals("")) {
				MailAccountDAO.updatePassword(ma.getId(), ma.getMailPassword());
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_MAIL_ACCOUNT_EDIT", Integer.toString(ma.getId()), ma.toString());
		} else {
			ServletContext sc = getServletContext();
			int maId = WebUtil.getInt(request, "ma_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("ma", MailAccountDAO.findByPk(maId));
			sc.getRequestDispatcher("/admin/mail_edit.jsp").forward(request, response);
		}
		
		log.debug("edit: void");
	}
	
	/**
	 * Update user
	 */
	private void delete(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("delete({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			int maId = WebUtil.getInt(request, "ma_id");
			MailAccountDAO.delete(maId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_MAIL_ACCOUNT_DELETE", Integer.toString(maId), null);
		} else {
			ServletContext sc = getServletContext();
			int maId = WebUtil.getInt(request, "ma_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("ma", MailAccountDAO.findByPk(maId));
			sc.getRequestDispatcher("/admin/mail_edit.jsp").forward(request, response);
		}
		
		log.debug("delete: void");
	}

	/**
	 * List node properties and children
	 */
	private void list(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("list({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		String usrId = WebUtil.getString(request, "ma_user");
		sc.setAttribute("ma_user", usrId);
		sc.setAttribute("mailAccounts", MailAccountDAO.findByUser(usrId, false));
		sc.getRequestDispatcher("/admin/mail_list.jsp").forward(request, response);
		log.debug("list: void");
	}
}
