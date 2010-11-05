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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import com.openkm.dao.bean.Translation;
import com.openkm.util.JCRUtils;
import com.openkm.util.WebUtil;

/**
 * Language servlet
 */
public class LanguageServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(LanguageServlet.class);
	private final String LANG_BASE_CODE = "en-GB";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			
			if (action.equals("edit")) {
				edit(session, request, response);
			} else if (action.equals("delete")) {
				delete(session, request, response);
			} else if (action.equals("create")) {
				create(session, request, response);
			} else if (action.equals("translate")) {
				translate(session, request, response);
			} 
			
			if (action.equals("") || WebUtil.getBoolean(request, "persist")) {
				list(session, request, response);
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
	 * List languages
	 */
	private void list(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException, com.openkm.core.RepositoryException {
		log.debug("list({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		sc.setAttribute("langs", LanguageDAO.findAll());		
		sc.getRequestDispatcher("/admin/language_list.jsp").forward(request, response);
		log.debug("list: void");
	}
	
	/**
	 * Delete language
	 */
	private void delete(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("delete({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			String lgId = WebUtil.getString(request, "lg_id");
			LanguageDAO.delete(lgId);
		} else {
			ServletContext sc = getServletContext();
			String lgId = WebUtil.getString(request, "lg_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("lang", LanguageDAO.findByPk(lgId));
			sc.getRequestDispatcher("/admin/language_edit.jsp").forward(request, response);
		}
		
		log.debug("delete: void");
	}
	
	/**
	 * Edit language
	 */
	private void edit(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("edit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			String lgId = WebUtil.getString(request, "lg_id");
			String lgName = WebUtil.getString(request, "lg_name");
			Language language = LanguageDAO.findByPk(lgId);
			language.setName(lgName);
			LanguageDAO.update(language);
		} else {
			ServletContext sc = getServletContext();
			String lgId = WebUtil.getString(request, "lg_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("lang", LanguageDAO.findByPk(lgId));
			sc.getRequestDispatcher("/admin/language_edit.jsp").forward(request, response);
		}
		
		log.debug("edit: void");
	}
	
	/**
	 * Create language
	 */
	private void create(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("edit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			Language language = new Language();
			language.setId(WebUtil.getString(request, "lg_id"));
			language.setName(WebUtil.getString(request, "lg_name"));
			LanguageDAO.create(language);
		} else {
			ServletContext sc = getServletContext();
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("lang", null);
			sc.getRequestDispatcher("/admin/language_edit.jsp").forward(request, response);
		}
		
		log.debug("edit: void");
	}
	
	/**
	 * Translate language
	 */
	private void translate(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("translate({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			Set<Translation> newTranslations = new HashSet<Translation>();
			Language langBase = LanguageDAO.findByPk(LANG_BASE_CODE);
			for (Translation translation : langBase.getTranslations()) {
				String text = WebUtil.getString(request, String.valueOf(translation.getId()));
				if (!text.equals("")) {
					Translation newTranslation = new Translation();
					newTranslation.setModule(translation.getModule());
					newTranslation.setKey(translation.getKey());
					newTranslation.setText(text);
					newTranslations.add(newTranslation);
				}
			}
			String lgId = WebUtil.getString(request, "lg_id");
			Language language = LanguageDAO.findByPk(lgId);
			language.setTranslations(newTranslations);
			LanguageDAO.update(language);
		} else {
			ServletContext sc = getServletContext();
			String lgId = WebUtil.getString(request, "lg_id");
			Language langToTranslate = LanguageDAO.findByPk(lgId);
			Map<String, String> translations = new HashMap<String, String>();
			for (Translation translation : langToTranslate.getTranslations()) {
				translations.put(translation.getKey(), translation.getText());
			}
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("lg_id", lgId);
			sc.setAttribute("langToTranslateName", langToTranslate.getName());
			sc.setAttribute("translations",translations);
			sc.setAttribute("langBase", LanguageDAO.findByPk(LANG_BASE_CODE)); // English always it'll be used as a translations base
			sc.getRequestDispatcher("/admin/translation_edit.jsp").forward(request, response);
		}
		
		log.debug("translate: void");
	}
}