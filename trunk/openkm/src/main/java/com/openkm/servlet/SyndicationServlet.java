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

package com.openkm.servlet;

import java.io.IOException;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.server.BasicCredentialsProvider;
import org.apache.jackrabbit.server.CredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.module.direct.DirectRepositoryModule;

/**
 * Syndication Servlet
 */
public class SyndicationServlet extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(TestServlet.class);
	private static final long serialVersionUID = 1L;
	private CredentialsProvider cp = new BasicCredentialsProvider(null);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Session session = null;
		
		try {
			session = getSession(request);
			log.info("SessionId: {}", session.getUserID());
		} catch (LoginException e) {
			response.setHeader("WWW-Authenticate", "Basic realm=\"OpenKM RSS Server\"");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			if (session != null) {
				session.logout();
			}
		}
	}
		
	/**
	 * Get JCR cession
	 */
	public synchronized Session getSession(HttpServletRequest request) throws LoginException, 
			RepositoryException, ServletException {
		Credentials creds = cp.getCredentials(request);
		Repository rep = DirectRepositoryModule.getRepository();
		
		if (creds == null) {
            return rep.login();
        } else {
            return rep.login(creds);
        }
    }
}
