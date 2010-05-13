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

package com.openkm.frontend.server;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.openkm.core.Config;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.config.ErrorCode;

/**
 * Extends the RemoteServiceServlet to optain token auth on developement and production
 * environments. Config.GWTDS determines the environment development and production values.
 * 
 * @author jllort
 *
 */
public class OKMRemoteServiceServlet extends RemoteServiceServlet {
	private static Logger log = LoggerFactory.getLogger(OKMRemoteServiceServlet.class);
	private static final long serialVersionUID = 1801666502721600473L;
	public static String sessionToken = null;
	
	/**
	 * @return The server token
	 */
	@SuppressWarnings("unchecked")
	public String getToken() throws OKMException {
		log.debug("getToken()");
		String token = "";
		
		if (Config.SESSION_MANAGER) {
			try {
				token = (String)getThreadLocalRequest().getSession().getAttribute("token");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRemoteService, ErrorCode.CAUSE_General), e.getMessage());
			}
		
			if (token == null) {
				log.warn("** Session token is NULL **");
				HttpServletRequest req = getThreadLocalRequest();
				log.warn("** ContextPath = "+req.getContextPath()+" **");
				log.warn("** PathInfo = "+req.getPathInfo()+" **");
				log.warn("** RemoteAddr = "+req.getRemoteAddr()+" **");
				Cookie[] cookies = req.getCookies();
				
				for (int i=0; i<cookies.length; i++) {
					log.warn("** COOKIE Name = "+cookies[i].getName()+", Value="+cookies[i].getValue()+", Path="+cookies[i].getPath()+", Domain="+cookies[i].getDomain()+" **");
				}
				
				for (Enumeration<String> enu = req.getParameterNames(); enu.hasMoreElements(); ) {
					String param = enu.nextElement();
					log.warn("** PARAMETER "+param+" = "+req.getParameter(param)+" **");
				}
				
				for (Enumeration<String> enu = req.getHeaderNames(); enu.hasMoreElements(); ) {
					String header = enu.nextElement();
					log.warn("** HEADER "+header+" = "+req.getHeader(header)+" **");
				}
				
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRemoteService, ErrorCode.CAUSE_SessionLost), "Session lost");
			}
		}
		
		log.debug("getToken: {}", token);
		return token;
	}
}
