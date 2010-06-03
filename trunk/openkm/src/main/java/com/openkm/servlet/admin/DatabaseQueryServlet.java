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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;
import com.openkm.core.SessionManager;
import com.openkm.dao.LegacyDAO;
import com.openkm.util.JCRUtils;
import com.openkm.util.WebUtil;

/**
 * Database query
 */
public class DatabaseQueryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(DatabaseQueryServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		ServletContext sc = getServletContext();
		String qs = WebUtil.getString(request, "qs");
		String token = (String) request.getSession().getAttribute("token");
		Session session = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			if (!qs.equals("")) {
				con = LegacyDAO.getConnection();
				stmt = con.createStatement();
				rs = stmt.executeQuery(qs);
				
				while (rs.next()) {
					log.info(rs.getString(1));
				}
												
				if (qs.toUpperCase().startsWith("SELECT")) {
					
					//for (Iterator it = q.list().iterator(); it.hasNext(); ) {
						//log.info(it.next().toString());
					//}
					//sc.setAttribute("results", q.list());
					//sc.setAttribute("columns", q.getReturnTypes());
				} else {
					//int rows = q.executeUpdate();
					//sc.setAttribute("rows", rows);
				}
				sc.setAttribute("qs", qs);
			}
			
			sc.getRequestDispatcher("/admin/database_query.jsp").forward(request, response);
		} catch (LoginException e) {
			sendErrorRedirect(request,response, e);
		} catch (RepositoryException e) {
			sendErrorRedirect(request,response, e);
		} catch (SQLException e) {
			sendErrorRedirect(request,response, e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
			LegacyDAO.close(rs);
			LegacyDAO.close(stmt);
			LegacyDAO.close(con);
		}
	}
}
