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
import com.openkm.dao.DocumentFilterDAO;
import com.openkm.dao.bean.DocumentFilter;
import com.openkm.dao.bean.DocumentFilterRule;
import com.openkm.dao.bean.MailAccount;
import com.openkm.dao.bean.MailFilterRule;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtils;

/**
 * Document filter servlet
 */
public class DocumentFilterServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(DocumentFilterServlet.class);
	String fields[] = { MailFilterRule.FIELD_FROM, MailFilterRule.FIELD_TO, MailFilterRule.FIELD_SUBJECT,
			MailFilterRule.FIELD_CONTENT };
	String operations[] = { MailFilterRule.OPERATION_CONTAINS, MailFilterRule.OPERATION_EQUALS };
	String protocols[] = { MailAccount.PROTOCOL_POP3, MailAccount.PROTOCOL_IMAP, MailAccount.PROTOCOL_IMAPS };
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtils.getString(request, "action");
		Session session = null;
		updateSessionManager(request);
		
		try {
			session = JCRUtils.getSession();
			
			if (action.equals("create")) {
				create(session, request, response);
			} else if (action.equals("edit")) {
				edit(session, request, response);
			} else if (action.equals("delete")) {
				delete(session, request, response);
			} else if (action.equals("ruleList")) {
				ruleList(session, request, response);
			} else if (action.equals("ruleCreate")) {
				ruleCreate(session, request, response);
			} else if (action.equals("ruleEdit")) {
				ruleEdit(session, request, response);
			} else if (action.equals("ruleDelete")) {
				ruleDelete(session, request, response);
			}
			
			if (action.equals("") || WebUtils.getBoolean(request, "persist")) {
				if (action.startsWith("rule")) {
					ruleList(session, request, response);
				} else {
					list(session, request, response);
				}
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * List document filters
	 */
	private void list(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("list({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		sc.setAttribute("documentFilters", DocumentFilterDAO.findAll(false));
		sc.getRequestDispatcher("/admin/document_filter_list.jsp").forward(request, response);
		log.debug("list: void");
	}
	
	/**
	 * Create document filter 
	 */
	private void create(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, RepositoryException {
		log.debug("create({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			DocumentFilter df = new DocumentFilter();
			df.setFilter(WebUtils.getString(request, "df_filter"));
			df.setType(WebUtils.getString(request, "df_type"));
			df.setActive(WebUtils.getBoolean(request, "df_active"));
			DocumentFilterDAO.create(df);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_DOCUMENT_FILTER_CREATE", Integer.toString(df.getId()), df.toString());
		} else {
			ServletContext sc = getServletContext();
			DocumentFilter df = new DocumentFilter();
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("df", df);
			sc.getRequestDispatcher("/admin/document_filter_edit.jsp").forward(request, response);
		}
		
		log.debug("create: void");
	}
	
	/**
	 * Edit document filter
	 */
	private void edit(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, RepositoryException {
		log.debug("edit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			int dfId = WebUtils.getInt(request, "df_id");
			DocumentFilter df = DocumentFilterDAO.findByPk(dfId);
			
			if (df != null) {
				df.setFilter(WebUtils.getString(request, "df_filter"));
				df.setType(WebUtils.getString(request, "df_type"));
				df.setActive(WebUtils.getBoolean(request, "df_active"));
				DocumentFilterDAO.update(df);
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_DOCUMENT_FILTER_EDIT", Integer.toString(df.getId()), df.toString());
		} else {
			ServletContext sc = getServletContext();
			int dfId = WebUtils.getInt(request, "df_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("df", DocumentFilterDAO.findByPk(dfId));
			sc.getRequestDispatcher("/admin/document_filter_edit.jsp").forward(request, response);
		}
		
		log.debug("edit: void");
	}
	
	/**
	 * Delete filter rule
	 */
	private void delete(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, RepositoryException {
		log.debug("delete({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			int dfId = WebUtils.getInt(request, "df_id");
			DocumentFilterDAO.delete(dfId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_DOCUMENT_FILTER_DELETE", Integer.toString(dfId), null);
		} else {
			ServletContext sc = getServletContext();
			int dfId = WebUtils.getInt(request, "df_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("df", DocumentFilterDAO.findByPk(dfId));
			sc.getRequestDispatcher("/admin/document_filter_edit.jsp").forward(request, response);
		}
		
		log.debug("delete: void");
	}
	
	/**
	 * List filter rules
	 */
	private void ruleList(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("ruleList({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		int dfId = WebUtils.getInt(request, "df_id");
		sc.setAttribute("mf_id", dfId);
		DocumentFilter df = DocumentFilterDAO.findByPk(dfId);
		
		for (DocumentFilterRule dfr : df.getFilterRules()) {
			sc.setAttribute("filterRules", dfr);
		}
		
		sc.getRequestDispatcher("/admin/document_filter_rule_list.jsp").forward(request, response);
		log.debug("ruleList: void");
	}
	
	/**
	 * Create filter rule
	 */
	private void ruleCreate(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, RepositoryException {
		log.debug("ruleCreate({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			int df_id = WebUtils.getInt(request, "df_id");
			DocumentFilterRule dfr = new DocumentFilterRule();
			//dfr.setField(WebUtils.getString(request, "mfr_field"));
			//dfr.setOperation(WebUtils.getString(request, "mfr_operation"));
			dfr.setValue(WebUtils.getString(request, "mfr_value"));
			dfr.setActive(WebUtils.getBoolean(request, "mfr_active"));
			DocumentFilter df = DocumentFilterDAO.findByPk(df_id);
			df.getFilterRules().add(dfr);
			DocumentFilterDAO.update(df);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_DOCUMENT_FILTER_RULE_CREATE", Integer.toString(df.getId()), df.toString());
		} else {
			ServletContext sc = getServletContext();
			DocumentFilterRule dfr = new DocumentFilterRule();
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("dfr", dfr);
			sc.setAttribute("fields", fields);
			sc.setAttribute("operations", operations);
			sc.getRequestDispatcher("/admin/document_filter_rule_edit.jsp").forward(request, response);
		}
		
		log.debug("ruleCreate: void");
	}
	
	/**
	 * Edit filter rule 
	 */
	private void ruleEdit(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, RepositoryException {
		log.debug("ruleEdit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			int dfrId = WebUtils.getInt(request, "dfr_id");
			DocumentFilterRule dfr = DocumentFilterDAO.findRuleByPk(dfrId);
			
			if (dfr != null) {
				//dfr.setField(WebUtils.getString(request, "dfr_field"));
				//dfr.setOperation(WebUtils.getString(request, "dfr_operation"));
				dfr.setValue(WebUtils.getString(request, "dfr_value"));
				dfr.setActive(WebUtils.getBoolean(request, "dfr_active"));
				DocumentFilterDAO.updateRule(dfr);
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_DOCUMENT_FILTER_RULE_EDIT", Integer.toString(dfr.getId()), dfr.toString());
		} else {
			ServletContext sc = getServletContext();
			int dfId = WebUtils.getInt(request, "df_id");
			int dfrId = WebUtils.getInt(request, "dfr_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("df_id", dfId);
			sc.setAttribute("dfr", DocumentFilterDAO.findRuleByPk(dfrId));
			sc.setAttribute("fields", fields);
			sc.setAttribute("operations", operations);
			sc.getRequestDispatcher("/admin/document_filter_rule_edit.jsp").forward(request, response);
		}
		
		log.debug("ruleEdit: void");
	}
	
	/**
	 * Delete filter rule
	 */
	private void ruleDelete(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("ruleDelete({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			int dfrId = WebUtils.getInt(request, "dfr_id");
			DocumentFilterDAO.deleteRule(dfrId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_DOCUMENT_FILTER_RULE_DELETE", Integer.toString(dfrId), null);
		} else {
			ServletContext sc = getServletContext();
			int dfId = WebUtils.getInt(request, "df_id");
			int dfrId = WebUtils.getInt(request, "dfr_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("df_id", dfId);
			sc.setAttribute("dfr", DocumentFilterDAO.findRuleByPk(dfrId));
			sc.setAttribute("fields", fields);
			sc.setAttribute("operations", operations);
			sc.getRequestDispatcher("/admin/document_filter_rule_edit.jsp").forward(request, response);
		}
		
		log.debug("ruleDelete: void");
	}
}
