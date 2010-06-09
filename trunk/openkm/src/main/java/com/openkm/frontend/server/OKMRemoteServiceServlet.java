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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Extends the RemoteServiceServlet to obtain token auth on development and production
 * environments. Config.GWTDS determines the environment development and production values.
 * 
 * @author jllort
 *
 */
public class OKMRemoteServiceServlet extends RemoteServiceServlet {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(OKMRemoteServiceServlet.class);
	private static final long serialVersionUID = 1801666502721600473L;
	public static String sessionToken = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
}
