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

package es.git.openkm.backend.server;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.git.openkm.core.Config;
import es.git.openkm.kea.tree.KEATree;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMThesaurusServletAdmin"
 *                           display-name="Report service"
 *                           description="Report service"
 * @web.servlet-mapping      url-pattern="/OKMThesaurusServletAdmin"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMThesaurusServletAdmin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			
			String token = (String)request.getSession().getAttribute("token");
			int level = (request.getParameter("level")!=null && !request.getParameter("level").equals(""))?Integer.parseInt(request.getParameter("level")):0;
			ServletOutputStream out = response.getOutputStream();
			response.setContentType("text/html");
			out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<STYLE type=\"text/css\">");
			out.println("body, td, a, div, .p { font-family:verdana,arial,sans-serif; font-size:10px; }");
			out.println("</STYLE>");
			out.println("<BODY>");
			out.flush();

			if (!Config.KEA_THESAURUS_OWL_FILE.equals("")) {
				out.println("<b>Starting thesaurus creation, this could take some hours.</b><br>");
				out.println("<b>Don't close this window meanwhile OpenKM is creating thesaurus.</b><br>");
				out.println("It'll be displayed creation information while creating nodes until level "+(level+1) + ", please be patient because tree deep level could be big.<br><br>");
				out.flush();
				KEATree.recursiveGenerateTree(null, 0, level, token, "/okm:thesaurus", new Vector<String>(), out );
				out.println("<br><b>Finished thesaurus creation.</b><br>");
			} else {
				out.println("<b>Error - there's no thesaurus file defined in OpenKM.cfg</b>");
			}
			
			try {
				// Dummy
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			out.println("</BODY>");
			out.println("</HTML>");
			out.flush();
		}
}
