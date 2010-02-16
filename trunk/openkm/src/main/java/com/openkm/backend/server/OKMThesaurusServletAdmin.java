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

package com.openkm.backend.server;

import java.io.IOException;
import java.io.Writer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openkm.core.Config;
import com.openkm.kea.tree.KEATree;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMThesaurusServletAdmin" display-name="Report service"
 *              description="Report service"
 * @web.servlet-mapping url-pattern="/OKMThesaurusServletAdmin"
 * @web.servlet-init-param name="A parameter" value="A value"
 */
public class OKMThesaurusServletAdmin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		String token = (String) request.getSession().getAttribute("token");
		int level = (request.getParameter("level") != null && !request.getParameter("level").equals("")) ? Integer
				.parseInt(request.getParameter("level"))
				: 0;
		Writer out = response.getWriter();
		response.setContentType("text/html");
		out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
		out.write("<HTML>");
		out.write("<HEAD>");
		out.write("<STYLE type=\"text/css\">");
		out.write("body, td, a, div, .p { font-family:verdana,arial,sans-serif; font-size:10px; }");
		out.write("</STYLE>");
		out.write("<BODY>");
		out.flush();

		if (!Config.KEA_THESAURUS_OWL_FILE.equals("")) {
			out.write("<b>Starting thesaurus creation, this could take some hours.</b><br>");
			out.write("<b>Don't close this window meanwhile OpenKM is creating thesaurus.</b><br>");
			out.write("It'll be displayed creation information while creating nodes until level "
					+ (level + 1) + ", please be patient because tree deep level could be big.<br><br>");
			out.flush();
			KEATree.generateTree(token, level, "/okm:thesaurus", new Vector<String>(), out);
			out.write("<br><b>Finished thesaurus creation.</b><br>");
		} else {
			out.write("<b>Error - there's no thesaurus file defined in OpenKM.cfg</b>");
		}

		try {
			// Dummy
		} catch (Exception e) {
			e.printStackTrace();
		}

		out.write("</BODY>");
		out.write("</HTML>");
		out.flush();
	}
}
