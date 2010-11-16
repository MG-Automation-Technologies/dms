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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.ConfigDAO;
import com.openkm.dao.bean.Config;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtil;

/**
 * Execute config servlet
 */
public class ConfigServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ConfigServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			Map<String, String> types = new LinkedHashMap<String, String>();
			types.put(Config.STRING, "String");
			types.put(Config.TEXT, "Text");
			types.put(Config.BOOLEAN, "Boolean");
			types.put(Config.INTEGER, "Integer");
			types.put(Config.LONG, "Long");
			
			if (action.equals("create")) {
				create(session, types, request, response);
			} else if (action.equals("edit")) {
				edit(session, types, request, response);
			} else if (action.equals("delete")) {
				delete(session, types, request, response);
			}
			
			if (action.equals("") || WebUtil.getBoolean(request, "persist")) {
				list(session, request, response);
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request, response, e);
		} finally {
			JCRUtils.logout(session);
		}
	}

	/**
	 * Create config
	 */
	private void create(Session session, Map<String, String> types, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, DatabaseException {
		if (WebUtil.getBoolean(request, "persist")) {
			String cfgKey = WebUtil.getString(request, "cfg_key");
			Config cfg = new Config();
			cfg.setKey(cfgKey);
			cfg.setType(WebUtil.getString(request, "cfg_type"));
			cfg.setValue(WebUtil.getString(request, "cfg_value"));
			ConfigDAO.create(cfg);
			com.openkm.core.Config.reload();
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_CONFIG_CREATE", cfgKey, cfg.toString());
		} else {
			ServletContext sc = getServletContext();
			Config cfg = new Config();
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("types", types);
			sc.setAttribute("cfg", cfg);
			sc.getRequestDispatcher("/admin/config_edit.jsp").forward(request, response);
		}
	}
	
	/**
	 * Edit config
	 */
	private void edit(Session session, Map<String, String> types, HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException, DatabaseException {
		if (WebUtil.getBoolean(request, "persist")) {
			String cfgKey = WebUtil.getString(request, "cfg_key");
			String cfgType = WebUtil.getString(request, "cfg_type");
			Config cfg = ConfigDAO.findByPk(cfgKey);
			cfg.setType(cfgType);
			
			if (Config.BOOLEAN.equals(cfgType)) {
				cfg.setValue(Boolean.toString(WebUtil.getBoolean(request, "cfg_value")));
			} else {
				cfg.setValue(WebUtil.getString(request, "cfg_value"));
			}
			
			ConfigDAO.update(cfg);
			com.openkm.core.Config.reload();
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_CONFIG_EDIT", cfgKey, cfg.toString());
		} else {
			ServletContext sc = getServletContext();
			String cfgKey = WebUtil.getString(request, "cfg_key");
			Config cfg = ConfigDAO.findByPk(cfgKey);
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("types", types);
			sc.setAttribute("cfg", cfg);
			sc.getRequestDispatcher("/admin/config_edit.jsp").forward(request, response);
		}
	}

	/**
	 * Delete config
	 */
	private void delete(Session session, Map<String, String> types, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, DatabaseException {
		if (WebUtil.getBoolean(request, "persist")) {
			String cfgKey = WebUtil.getString(request, "cfg_key");
			ConfigDAO.delete(cfgKey);
			com.openkm.core.Config.reload();
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_CONFIG_DELETE", cfgKey, null);
		} else {
			ServletContext sc = getServletContext();
			String cfgKey = WebUtil.getString(request, "cfg_key");
			Config cfg = ConfigDAO.findByPk(cfgKey);
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("types", types);
			sc.setAttribute("cfg", cfg);
			sc.getRequestDispatcher("/admin/config_edit.jsp").forward(request, response);
		}
	}

	/**
	 * List config
	 */
	private void list(Session session, HttpServletRequest request, HttpServletResponse response) throws
			ServletException, IOException, DatabaseException {
		log.debug("list({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		List<Config> list = ConfigDAO.findAll();
		
		for (Config cfg : list) {
			if (Config.STRING.equals(cfg.getType())) {
				cfg.setType("String");
			} else if (Config.TEXT.equals(cfg.getType())) {
				cfg.setType("Text");
			} else if (Config.BOOLEAN.equals(cfg.getType())) {
				cfg.setType("Boolean");
			} else if (Config.INTEGER.equals(cfg.getType())) {
				cfg.setType("Integer");
			} else if (Config.LONG.equals(cfg.getType())) {
				cfg.setType("Long");
			}
		}
		
		sc.setAttribute("configs", list);
		sc.getRequestDispatcher("/admin/config_list.jsp").forward(request, response);
		log.debug("list: void");
	}	
}
