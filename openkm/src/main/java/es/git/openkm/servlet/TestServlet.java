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

package es.git.openkm.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Class
 * 
 * @web.servlet name="Test" display-name="Name for Test"
 *              description="Description for Test"
 * @web.servlet-mapping url-pattern="/Test"
 */
public class TestServlet extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(TestServlet.class);
	private static final long serialVersionUID = 8388111332983911121L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String token = (String)request.getSession().getAttribute("token");
		PrintWriter out = response.getWriter();
		log.info("Token: "+token);
		out.println("Token: "+token);
		response.setContentType("text/html");
		
		try {
			// Dummy
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
