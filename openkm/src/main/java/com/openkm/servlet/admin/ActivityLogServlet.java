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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.core.DatabaseException;
import com.openkm.dao.ActivityDAO;
import com.openkm.dao.bean.ActivityFilter;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtil;

/**
 * Activity log servlet
 */
public class ActivityLogServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ActivityLogServlet.class);
	String actions[] = { "Auth", "LOGIN", "LOGOUT", "SESSION_EXPIRATION",
			"Document", "CANCEL_CHECKOUT_DOCUMENT", 
			"CHECKIN_DOCUMENT", "CHECKOUT_DOCUMENT", "CREATE_DOCUMENT", "DELETE_DOCUMENT", 
			"GET_CHILD_DOCUMENTS", "GET_DOCUMENT_CONTENT", "GET_DOCUMENT_CONTENT_BY_VERSION", 
			"GET_DOCUMENT_PROPERTIES", "GET_DOCUMENT_VERSION_HISTORY", "GET_PROPERTY_GROUP_PROPERTIES", 
			"LOCK_DOCUMENT", "MOVE_DOCUMENT", "PURGE_DOCUMENT", "RENAME_DOCUMENT", 
			"SET_DOCUMENT_CONTENT", "SET_DOCUMENT_PROPERTIES", "UNLOCK_DOCUMENT", "ADD_DOCUMENT_NOTE",
			"Folder", "COPY_FOLDER", "CREATE_FOLDER", "DELETE_FOLDER", "GET_CHILD_FOLDERS", 
			"GET_FOLDER_CONTENT_INFO", "GET_FOLDER_PROPERTIES", "MOVE_FOLDER", "PURGE_FOLDER", "RENAME_FOLDER", 
			"Admin", "PROPERTY_GROUP", "REPOSITORY_ACTION", "REPOSITORY_EDIT", "REPOSITORY_SET",
			"REPOSITORY_VIEW", "USER_ACTION" };
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		ServletContext sc = getServletContext();
		request.setCharacterEncoding("UTF-8");
		String dbegin = WebUtil.getString(request, "dbegin");
		String dend = WebUtil.getString(request, "dend");		
		String user = WebUtil.getString(request, "user");
		String action = WebUtil.getString(request, "action");
		
		try {
			if (!dbegin.equals("") && !dend.equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				ActivityFilter filter = new ActivityFilter();
				Calendar begin = Calendar.getInstance();
				begin.setTime(sdf.parse(dbegin));
				filter.setBegin(begin);
				Calendar end = Calendar.getInstance();
				end.setTime(sdf.parse(dend));
				filter.setEnd(end);
				filter.setUser(user);
				filter.setAction(action);
				sc.setAttribute("results", ActivityDAO.findByFilter(filter));
				
				// Activity log
				UserActivity.log(request.getRemoteUser(), "ADMIN_ACTIVITY_LOG", null, null);
			} else {
				sc.setAttribute("results", null);
			}
			
			sc.setAttribute("dbeginFilter", dbegin);
			sc.setAttribute("dendFilter", dend);
			sc.setAttribute("userFilter", user);
			sc.setAttribute("actionFilter", action);
			sc.setAttribute("actions", actions);
			sc.setAttribute("users", OKMAuth.getInstance().getUsers());
			sc.getRequestDispatcher("/admin/activity_log.jsp").forward(request, response);
		} catch (ParseException e) {
			sendErrorRedirect(request, response, e);
		} catch (DatabaseException e) {
			sendErrorRedirect(request, response, e);
		} catch (PrincipalAdapterException e) {
			sendErrorRedirect(request, response, e);
		}
	}
}
