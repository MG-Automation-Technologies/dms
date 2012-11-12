/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2012  Paco Avila & Josep Llort
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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.core.MimeTypeConfig;
import com.openkm.dao.CssDAO;
import com.openkm.dao.bean.Css;
import com.openkm.util.WebUtils;

/**
 * Css Styles Servlet
 */
public class CssServlet extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(CssServlet.class);
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String path = request.getPathInfo();
		String uri = request.getRequestURI();
		
		OutputStream os = null;
		
		try {
			if (path.length() > 1) {
				String context = Css.CONTEXT_FRONTEND;
				if (uri.contains("/frontend/")) {
					context = Css.CONTEXT_FRONTEND;
				} else if (uri.contains("/mobile/")) {
					context = Css.CONTEXT_MOBILE;
				} else if (uri.contains("/admin/")) {
					context = Css.CONTEXT_ADMINISTRATION;
				} else if (uri.contains("/extension/")) {
					context = Css.CONTEXT_EXTENSION;
				}
				
				Css css = CssDAO.findByNameAndType(path.substring(1), context);
				// Prepare file headers
				WebUtils.prepareSendFile(request, response, css.getName()+".css", MimeTypeConfig.MIME_CSS, false);
				PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
				out.append(css.getContent());
				out.flush();
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(os);
		}
	}
}
