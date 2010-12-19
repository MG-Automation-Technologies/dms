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

import static com.openkm.dao.MailAccountDAO.findRuleByPk;

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
import com.openkm.dao.MailAccountDAO;
import com.openkm.dao.bean.MailAccount;
import com.openkm.dao.bean.MailFilter;
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
		int maId = WebUtils.getInt(request, "ma_id");
		String ma_user = WebUtils.getString(request, "ma_user");
		sc.setAttribute("ma_id", maId);
		sc.setAttribute("ma_user", ma_user);
		MailAccount ma = MailAccountDAO.findByPk(maId);
		sc.setAttribute("mailFilters", ma.getMailFilters());
		sc.getRequestDispatcher("/admin/mail_filter_list.jsp").forward(request, response);
		log.debug("list: void");
	}
	
	/**
	 * Create document filter 
	 */
	private void create(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, RepositoryException {
		log.debug("create({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			int maId = WebUtils.getInt(request, "ma_id");
			MailFilter mf = new MailFilter();
			mf.setPath(WebUtils.getString(request, "mf_path"));
			mf.setUuid(JCRUtils.getUUID(session, mf.getPath()));
			mf.setGrouping(WebUtils.getBoolean(request, "mf_grouping"));
			mf.setActive(WebUtils.getBoolean(request, "mf_active"));
			MailAccount ma = MailAccountDAO.findByPk(maId);
			ma.getMailFilters().add(mf);
			MailAccountDAO.update(ma);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_MAIL_FILTER_CREATE", Integer.toString(ma.getId()), mf.toString());
		} else {
			ServletContext sc = getServletContext();
			MailFilter mf = new MailFilter();
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("mf", mf);
			sc.getRequestDispatcher("/admin/mail_filter_edit.jsp").forward(request, response);
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
			int mfId = WebUtils.getInt(request, "mf_id");
			MailFilter mf = MailAccountDAO.findFilterByPk(session, mfId);
			
			if (mf != null) {
				mf.setPath(WebUtils.getString(request, "mf_path"));
				mf.setUuid(JCRUtils.getUUID(session, mf.getPath()));
				mf.setGrouping(WebUtils.getBoolean(request, "mf_grouping"));
				mf.setActive(WebUtils.getBoolean(request, "mf_active"));
				MailAccountDAO.updateFilter(mf);
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_MAIL_FILTER_EDIT", Integer.toString(mf.getId()), mf.toString());
		} else {
			ServletContext sc = getServletContext();
			int maId = WebUtils.getInt(request, "ma_id");
			int mfId = WebUtils.getInt(request, "mf_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("ma_id", maId);
			sc.setAttribute("mf", MailAccountDAO.findFilterByPk(session, mfId));
			sc.getRequestDispatcher("/admin/mail_filter_edit.jsp").forward(request, response);
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
			int maId = WebUtils.getInt(request, "ma_id");
			int mfId = WebUtils.getInt(request, "mf_id");
			MailAccountDAO.deleteFilter(mfId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_MAIL_FILTER_DELETE", Integer.toString(maId), null);
		} else {
			ServletContext sc = getServletContext();
			int maId = WebUtils.getInt(request, "ma_id");
			int mfId = WebUtils.getInt(request, "mf_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("ma_id", maId);
			sc.setAttribute("mf", MailAccountDAO.findFilterByPk(session, mfId));
			sc.getRequestDispatcher("/admin/mail_filter_edit.jsp").forward(request, response);
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
		int maId = WebUtils.getInt(request, "ma_id");
		int mfId = WebUtils.getInt(request, "mf_id");
		sc.setAttribute("ma_id", maId);
		sc.setAttribute("mf_id", mfId);
		MailAccount ma = MailAccountDAO.findByPk(maId);
		
		for (MailFilter mf : ma.getMailFilters()) {
			if (mf.getId() == mfId) {
				sc.setAttribute("filterRules", mf.getFilterRules());		
			}
		}
		
		sc.getRequestDispatcher("/admin/mail_filter_rule_list.jsp").forward(request, response);
		log.debug("ruleList: void");
	}
	
	/**
	 * Create filter rule
	 */
	private void ruleCreate(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, RepositoryException {
		log.debug("ruleCreate({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtils.getBoolean(request, "persist")) {
			int mf_id = WebUtils.getInt(request, "mf_id");
			MailFilterRule mfr = new MailFilterRule();
			mfr.setField(WebUtils.getString(request, "mfr_field"));
			mfr.setOperation(WebUtils.getString(request, "mfr_operation"));
			mfr.setValue(WebUtils.getString(request, "mfr_value"));
			mfr.setActive(WebUtils.getBoolean(request, "mfr_active"));
			MailFilter mf = MailAccountDAO.findFilterByPk(session, mf_id);
			mf.getFilterRules().add(mfr);
			MailAccountDAO.updateFilter(mf);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_MAIL_FILTER_CREATE", Integer.toString(mf.getId()), mf.toString());
		} else {
			ServletContext sc = getServletContext();
			MailFilterRule mfr = new MailFilterRule();
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("mfr", mfr);
			sc.setAttribute("fields", fields);
			sc.setAttribute("operations", operations);
			sc.getRequestDispatcher("/admin/mail_filter_rule_edit.jsp").forward(request, response);
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
			int mfrId = WebUtils.getInt(request, "mfr_id");
			MailFilterRule mfr = MailAccountDAO.findRuleByPk(mfrId);
			
			if (mfr != null) {
				mfr.setField(WebUtils.getString(request, "mfr_field"));
				mfr.setOperation(WebUtils.getString(request, "mfr_operation"));
				mfr.setValue(WebUtils.getString(request, "mfr_value"));
				mfr.setActive(WebUtils.getBoolean(request, "mfr_active"));
				MailAccountDAO.updateRule(mfr);
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_FILTER_RULE_EDIT", Integer.toString(mfr.getId()), mfr.toString());
		} else {
			ServletContext sc = getServletContext();
			int maId = WebUtils.getInt(request, "ma_id");
			int mfId = WebUtils.getInt(request, "mf_id");
			int mfrId = WebUtils.getInt(request, "mfr_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("ma_id", maId);
			sc.setAttribute("mf_id", mfId);
			sc.setAttribute("mfr", findRuleByPk(mfrId));
			sc.setAttribute("fields", fields);
			sc.setAttribute("operations", operations);
			sc.getRequestDispatcher("/admin/mail_filter_rule_edit.jsp").forward(request, response);
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
			int mfrId = WebUtils.getInt(request, "mfr_id");
			MailAccountDAO.deleteRule(mfrId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_FILTER_RULE_DELETE", Integer.toString(mfrId), null);
		} else {
			ServletContext sc = getServletContext();
			int maId = WebUtils.getInt(request, "ma_id");
			int mfId = WebUtils.getInt(request, "mf_id");
			int mfrId = WebUtils.getInt(request, "mfr_id");
			sc.setAttribute("action", WebUtils.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("ma_id", maId);
			sc.setAttribute("mf_id", mfId);
			sc.setAttribute("mfr", MailAccountDAO.findRuleByPk(mfrId));
			sc.setAttribute("fields", fields);
			sc.setAttribute("operations", operations);
			sc.getRequestDispatcher("/admin/mail_filter_rule_edit.jsp").forward(request, response);
		}
		
		log.debug("ruleDelete: void");
	}
}
