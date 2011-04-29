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

package com.openkm.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.util.UserActivity;

/**
 * Status Servlet
 */
public class StatusServlet extends BasicSecuredServlet {
	private static Logger log = LoggerFactory.getLogger(StatusServlet.class);
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String action = request.getPathInfo();
		Session session = null;
		log.debug("action: {}", action);
		
		try {
			session = getSession(request);
			response.setContentType("text/plain; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("OK");
			out.close();
			
			// Activity log
			UserActivity.log(session.getUserID(), "MISC_STATUS", null, "OK");
		} catch (LoginException e) {
			response.setHeader("WWW-Authenticate", "Basic realm=\"OpenKM Status Server\"");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		} catch (RepositoryException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			
			// Activity log
			UserActivity.log(request.getRemoteUser(), "MISC_STATUS", null, e.getMessage());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			
			// Activity log
			UserActivity.log(request.getRemoteUser(), "MISC_STATUS", null, e.getMessage());
		} finally {
			if (session != null) {
				session.logout();
			}
		}
	}
}
