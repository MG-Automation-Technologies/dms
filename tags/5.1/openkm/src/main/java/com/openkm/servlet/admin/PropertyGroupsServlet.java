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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.LoginException;
import javax.jcr.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMPropertyGroup;
import com.openkm.bean.PropertyGroup;
import com.openkm.bean.form.Button;
import com.openkm.bean.form.CheckBox;
import com.openkm.bean.form.FormElement;
import com.openkm.bean.form.Input;
import com.openkm.bean.form.Option;
import com.openkm.bean.form.Select;
import com.openkm.bean.form.TextArea;
import com.openkm.bean.form.Validator;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;
import com.openkm.module.direct.DirectRepositoryModule;
import com.openkm.util.FileUtils;
import com.openkm.util.FormUtils;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtils;

/**
 * Property groups servlet
 */
public class PropertyGroupsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(PropertyGroupsServlet.class);
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtils.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			
			if (action.equals("register")) {
				register(session, request, response);
			}
			
			list(request, response);
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (org.apache.jackrabbit.core.nodetype.compact.ParseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (InvalidNodeTypeDefException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Register property group
	 */
	private void register(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException, 
			org.apache.jackrabbit.core.nodetype.compact.ParseException, 
			javax.jcr.RepositoryException, InvalidNodeTypeDefException {
		log.debug("register({}, {}, {})", new Object[] { session, request, response });
		
		// Check xml property groups definition
		FormUtils.resetPropertyGroupsForms();
		FormUtils.parsePropertyGroupsForms(Config.PROPERTY_GROUPS_XML);
		
		// If it is ok, register it
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(Config.PROPERTY_GROUPS_CND);
			DirectRepositoryModule.registerCustomNodeTypes(session, fis);
		} finally {
			IOUtils.closeQuietly(fis);
		}
		
		// Activity log
		UserActivity.log(request.getRemoteUser(), "ADMIN_PROPERTY_GROUP_REGISTER", Config.PROPERTY_GROUPS_CND, null);
		log.debug("register: void");
	}

	/**
	 * List property groups

	 */
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException, ParseException, RepositoryException, DatabaseException {
		log.debug("list({}, {})", new Object[] { request, response });
		ServletContext sc = getServletContext();
		FormUtils.resetPropertyGroupsForms();
		OKMPropertyGroup okmPropGroups = OKMPropertyGroup.getInstance();
		List<PropertyGroup> groups = okmPropGroups.getAllGroups(null);
		Map<PropertyGroup, List<Map<String, String>>> pGroups = new LinkedHashMap<PropertyGroup, List<Map<String,String>>>();
		
		for (PropertyGroup group : groups) {
			List<FormElement> mData = okmPropGroups.getPropertyGroupForm(null, group.getName());
			List<Map<String, String>> fMaps = new ArrayList<Map<String,String>>();
			
			for (FormElement fe : mData) {
				fMaps.add(getMap(fe));
			}
			
			pGroups.put(group, fMaps);
		}
		
		sc.setAttribute("pgCnd", FileUtils.getName(Config.PROPERTY_GROUPS_CND));
		sc.setAttribute("pGroups", pGroups);
		sc.getRequestDispatcher("/admin/property_groups.jsp").forward(request, response);
		
		// Activity log
		UserActivity.log(request.getRemoteUser(), "ADMIN_PROPERTY_GROUP_LIST", null, null);
		log.debug("list: void");
	}
	
	/**
	 * Get form element type
	 */
	private Map<String, String> getMap(FormElement fe) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("label", fe.getLabel());
		ret.put("name", fe.getName());
		ret.put("width", fe.getWidth());
		ret.put("height", fe.getHeight());
		
		if (fe instanceof Input) {
			Input input = (Input) fe;
			ret.put("field", "Input");
			StringBuilder sb = new StringBuilder();
			sb.append("<i>Type:</i> ");
			sb.append(input.getType());
			drawValidators(sb, input.getValidators());
			ret.put("others", sb.toString());
		} else if (fe instanceof CheckBox) {
			CheckBox checkBox = new CheckBox();
			ret.put("field", "CheckBox");
			StringBuilder sb = new StringBuilder();
			drawValidators(sb, checkBox.getValidators());
			ret.put("others", sb.toString());
		} else if (fe instanceof TextArea) {
			TextArea textArea = (TextArea) fe;
			ret.put("field", "TextArea");
			StringBuilder sb = new StringBuilder();
			drawValidators(sb, textArea.getValidators());
			ret.put("others", sb.toString());
		} else if (fe instanceof Select) {
			Select select = (Select) fe;
			ret.put("field", "Select");
			StringBuilder sb = new StringBuilder();
			sb.append("<i>Type:</i> ");
			sb.append(select.getType());
			sb.append("<br/><i>Options:</i><ul>");
			for (Iterator<Option> itOpt = select.getOptions().iterator(); itOpt.hasNext(); ) {
				Option opt = itOpt.next();
				sb.append("<li><i>Label:</i> ");
				sb.append(opt.getLabel());
				sb.append(", <i>Value:</i> ");
				sb.append(opt.getValue());
				sb.append("</li>");
			}
			sb.append("</ul>");
			drawValidators(sb, select.getValidators());
			ret.put("others", sb.toString());
		} else if (fe instanceof Button) {
			Button button = (Button) fe;
			ret.put("field", "Button");
			StringBuilder sb = new StringBuilder();
			sb.append("<i>Type:</i> ");
			sb.append(button.getType());
			ret.put("others", sb.toString());
		}
		
		return ret;
	}
	
	/**
	 * Draw validation configuration
	 */
	private void drawValidators(StringBuilder sb, List<Validator> validators) {
		if (!validators.isEmpty()) {
			sb.append("<br/><i>Validators:</i><ul>");
			for (Iterator<Validator> it = validators.iterator(); it.hasNext(); ) {
				Validator v = it.next();
				sb.append("<li><i>Type:</i> ");
				sb.append(v.getType());
				sb.append(", <i>Parameter:</i> ");
				sb.append(v.getParameter());
				sb.append("</li>");
			}
			sb.append("</ul>");
		}
	}
}
