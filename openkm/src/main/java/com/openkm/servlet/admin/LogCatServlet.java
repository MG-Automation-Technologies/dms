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

package com.openkm.servlet.admin;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;
import com.openkm.util.FormatUtil;
import com.openkm.util.WebUtil;

/**
 * LogCatServlet servlet
 */
public class LogCatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(LogCatServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws 
			ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		
		if (action.equals(WebUtil.VIEW)) {
			view(request, response);
		} else if (action.equals(WebUtil.LIST) || action.equals("")) {
			list(request, response);
		} else {
			ServletContext sc = getServletContext();
			sc.getRequestDispatcher("/admin/logcat.jsp").forward(request, response);
		}
	}

	/**
	 * List logs
	 */
	private void list(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("list({}, {})", request, response);
		ServletContext sc = getServletContext();
		File dir = new File(Config.HOME_DIR + "/server/default/log");
		sc.setAttribute("path", dir.getPath());
		sc.setAttribute("files", FileUtils.listFiles(dir, null, false));
		sc.getRequestDispatcher("/admin/logcat.jsp").forward(request, response);
		log.debug("list: void");
	}
	
	/**
	 * View log
	 */
	private void view(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("view({}, {})", request, response);
		int begin = WebUtil.getInt(request, "begin");
		int end = WebUtil.getInt(request, "end");
		String str = WebUtil.getString(request, "str");
		String file = WebUtil.getString(request, "file");
		ServletContext sc = getServletContext();
		File lf = new File(file);
		sc.setAttribute("file", lf.getPath());
		sc.setAttribute("begin", begin);
		sc.setAttribute("end", end);
		sc.setAttribute("str", str);
		sc.setAttribute("messages", FormatUtil.parseLog(lf, begin, end, str));
		sc.getRequestDispatcher("/admin/logcat_view.jsp").forward(request, response);
		log.debug("view: void");
	}
}
