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

package com.openkm.webdav;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openkm.core.Config;
import com.openkm.core.JcrSessionManager;
import com.openkm.servlet.BasicSecuredServlet;

public class WebDAVFilter implements Filter {
	@Override
	public void init(FilterConfig fConfig) throws ServletException {}
	
	@Override
	public void destroy() {}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (Config.SYSTEM_WEBDAV_SERVER) {
			response.setContentType(Config.MIME_HTML);
			handleRequest(request, response);
		} else {
			response.setContentType(Config.MIME_TEXT);
			PrintWriter out = response.getWriter();
			out.println("WebDAV is disabled. Contact with your administrator.");
			out.flush();
			out.close();
		}
	}
	
	/**
	 * Handle WebDAV requests.
	 */
	private void handleRequest(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		Session session = null;
		
		try {
			session = new BasicSecuredServlet().getSession((HttpServletRequest) request);
			String uid = UUID.randomUUID().toString();
			JcrSessionManager.getInstance().add(uid, session);
			JcrSessionTokenHolder.set(uid);
			WebDavService.get().handleRequest((HttpServletRequest) request, (HttpServletResponse) response);
		} catch (LoginException e) {
			((HttpServletResponse) response).setHeader("WWW-Authenticate", "Basic realm=\"OpenKM WebDAV Server\"");
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		} catch (RepositoryException e) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			response.getOutputStream().flush();
			response.flushBuffer();
			
			if (session != null) {
				JcrSessionManager.getInstance().remove(JcrSessionTokenHolder.get());
				session.logout();
			}
		}
	}
}
