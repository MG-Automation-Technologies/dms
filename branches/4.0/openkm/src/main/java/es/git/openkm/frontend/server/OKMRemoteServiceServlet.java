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

package es.git.openkm.frontend.server;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.config.ErrorCode;

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
	public String getToken() throws OKMException {
		log.debug("getToken()");
		String token = "";
		
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
			
			for (Enumeration enu = req.getParameterNames(); enu.hasMoreElements(); ) {
				String param = (String) enu.nextElement();
				log.warn("** PARAMETER "+param+" = "+req.getParameter(param)+" **");
			}

			for (Enumeration enu = req.getHeaderNames(); enu.hasMoreElements(); ) {
				String header = (String) enu.nextElement();
				log.warn("** HEADER "+header+" = "+req.getHeader(header)+" **");
			}
			
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMRemoteService, ErrorCode.CAUSE_SessionLost), "Session lost");
		}
		
		log.debug("getToken: "+token);
		return token;
	}
}
