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

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.LanguageDAO;
import com.openkm.dao.bean.Language;
import com.openkm.util.JCRUtils;
import com.openkm.util.WebUtil;

/**
 * Language servlet
 */
public class LanguageServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(LanguageServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
																					   ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			
			if (action.equals("langEdit")) {
				languageEdit(session, request, response);
			} else if (action.equals("langDelete")) {
				langDelete(session, request, response);
			} 
			
			if (action.equals("") || (action.startsWith("lang") && WebUtil.getBoolean(request, "persist"))) {
				langList(session, request, response);
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (com.openkm.core.RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 *
	 * List language 
	 * @throws com.openkm.core.RepositoryException 
	 * @throws DatabaseException 
	 */
	private void langList(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException, com.openkm.core.RepositoryException {
		log.debug("languageList({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		sc.setAttribute("langs", LanguageDAO.findAll());		
		sc.getRequestDispatcher("/admin/language_list.jsp").forward(request, response);
		log.debug("languageList: void");
	}
	
	/**
	 * Delete language
	 */
	private void langDelete(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("languageDelete({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			String lg_language = WebUtil.getString(request, "lg_language");
			LanguageDAO.delete(lg_language);
		} else {
			ServletContext sc = getServletContext();
			String lg_language = WebUtil.getString(request, "lg_language");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("lang", LanguageDAO.findByPk(lg_language));
			sc.getRequestDispatcher("/admin/language_edit.jsp").forward(request, response);
		}
		
		log.debug("languageDelete: void");
	}
	
	/**
	 * Edit language
	 */
	private void languageEdit(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("languageEdit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			String lg_language = WebUtil.getString(request, "lg_language");
			String lg_description = WebUtil.getString(request, "lg_description");
			Language language = LanguageDAO.findByPk(lg_language);
			language.setDescription(lg_description);
			LanguageDAO.update(language);
		} else {
			ServletContext sc = getServletContext();
			String lg_language = WebUtil.getString(request, "lg_language");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("lang", LanguageDAO.findByPk(lg_language));
			sc.getRequestDispatcher("/admin/language_edit.jsp").forward(request, response);
		}
		
		log.debug("languageDelete: void");
	}
}