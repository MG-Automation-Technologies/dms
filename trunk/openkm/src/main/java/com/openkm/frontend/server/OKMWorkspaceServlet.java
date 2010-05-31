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

package com.openkm.frontend.server;

import java.sql.SQLException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMDashboard;
import com.openkm.core.Config;
import com.openkm.core.RepositoryException;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.bean.MailAccount;
import com.openkm.dao.bean.User;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTWorkspace;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMWorkspaceService;
import com.openkm.util.WarUtils;
import com.openkm.validator.ValidatorException;
import com.openkm.validator.ValidatorFactory;
import com.openkm.validator.password.PasswordValidator;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMWorkspaceServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMWorkspaceServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMWorkspaceServlet extends OKMRemoteServiceServlet implements OKMWorkspaceService {
	private static Logger log = LoggerFactory.getLogger(OKMWorkspaceServlet.class);
	private static final long serialVersionUID = 8673521252684830906L;
	
	@Override
	public GWTWorkspace getUserWorkspace() throws OKMException {
		GWTWorkspace workspace = new GWTWorkspace();
		
		if ( Config.APPLICATION_URL!= null && Config.APPLICATION_URL.indexOf("/OpenKM")>0) {
			workspace.setApplicationURL(Config.APPLICATION_URL);
		}
		
		workspace.setUser(getThreadLocalRequest().getRemoteUser());
		workspace.setAdmin(getThreadLocalRequest().isUserInRole(Config.DEFAULT_ADMIN_ROLE));
		workspace.setAppVersion(WarUtils.getAppVersion().toString());
		workspace.setWorkflowRunConfigForm(Config.WORKFLOW_RUN_CONFIG_FORM);
		workspace.setWorkflowProcessIntanceVariableUUID(Config.WORKFLOW_PROCESS_INSTANCE_VARIABLE_UUID);
		workspace.setWorkflowProcessIntanceVariablePath(Config.WORKFLOW_PROCESS_INSTANCE_VARIABLE_PATH);

		if (Config.SESSION_MANAGER) {
			workspace.setToken((String)getThreadLocalRequest().getSession().getAttribute("token"));
		} else {
			workspace.setToken(getThreadLocalRequest().getSession().getId());
		}
		
		// Is a wizard to uploading documents
		workspace.setWizardPropertyGroups(Config.WIZARD_PROPERTY_GROUPS.length>0);
		workspace.setWizardCategories(!Config.WIZARD_CATEGORIES.equals(""));
		workspace.setWizardKeywords(!Config.WIZARD_KEYWORDS.equals(""));
		
		// Is chat enabled and autologin
		workspace.setChatEnabled(Config.CHAT_ENABLED);
		workspace.setChatAutoLogin(Config.CHAT_AUTOLOGIN);
		
		// Schedule time
		workspace.setKeepAliveSchedule(Config.SCHEDULE_SESSION_KEEPALIVE);
		workspace.setDashboardSchedule(Config.SCHEDULE_DASHBOARD_REFRESH);
		
		// Advanced filters ( used when there a lot of users and groups )
		workspace.setAdvancedFilters(true);
		
		// User quota ( limit user repository size )
		workspace.setUserQuotaLimit(true);
		workspace.setUserQuotaLimitSize(10*1048576); // 10*1048576 = 5MB
		
		// Stack visibility
		workspace.setCategoriesStackVisible(true);
		workspace.setThesaurusStackVisible(true);
		workspace.setPersonalStackVisible(true);
		workspace.setMailStackVisible(true);
		
		AuthDAO authDAO = AuthDAO.getInstance();
		try {
			User user = new User();
			
			if (Config.PRINCIPAL_ADAPTER.equals("com.openkm.principal.DatabasePrincipalAdapter")) {
				user = authDAO.findUserByPk(getThreadLocalRequest().getRemoteUser());
				workspace.setEmail(user.getEmail());
			} else {
				user.setId(getThreadLocalRequest().getRemoteUser());
				user.setName("");
				user.setEmail("");
				user.setActive(true);
				user.setPass("");
			}
			
			for (Iterator<MailAccount> it = authDAO.findMailAccountsByUser(getThreadLocalRequest().getRemoteUser(), true).iterator(); it.hasNext();) {
				MailAccount mailAccount = it.next();
				workspace.setImapHost(mailAccount.getMailHost());
				workspace.setImapUser(mailAccount.getMailUser());
				workspace.setImapFolder(mailAccount.getMailFolder());
				workspace.setImapID(mailAccount.getId());
			}
		} catch (SQLException e) {
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_SQLException), e.getMessage());
		}
		
		if (Config.PRINCIPAL_ADAPTER.equals("com.openkm.principal.DatabasePrincipalAdapter")) {
			workspace.setChangePassword(true);
		} else {
			workspace.setChangePassword(false);
		}
		
		return workspace;
	}
	
	@Override
	public Double getUserDocumentsSize() throws OKMException {
		String token = getToken();
		Double docSize = new Double(0);
		
		try {
			docSize = new Double(OKMDashboard.getInstance().getUserDocumentsSize(token));
			
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		
		return docSize;
	}
	
	@Override
	public void updateUserWorkspace(GWTWorkspace workspace) throws OKMException {
		// For updating user
		User user = new User();
		user.setId(workspace.getUser());
		user.setPass(workspace.getPassword());
		user.setEmail(workspace.getEmail());
		
		// For updating imap mail
		MailAccount mailAccount = new MailAccount();
		mailAccount.setActive(true);
		mailAccount.setMailFolder(workspace.getImapFolder());
		mailAccount.setMailHost(workspace.getImapHost());
		mailAccount.setMailPassword(workspace.getImapPassword());
		mailAccount.setMailUser(workspace.getImapUser());
		mailAccount.setUser(workspace.getUser());
		mailAccount.setId(workspace.getImapID());
		
		// Disable user configuration modification in demo
		if (!Config.SYSTEM_DEMO) {
			try {
				AuthDAO authDAO = AuthDAO.getInstance();
				
				// Can change password
				if (Config.PRINCIPAL_ADAPTER.equals("com.openkm.principal.DatabasePrincipalAdapter")) {
					authDAO.updateUserPassword(workspace.getUser(), workspace.getPassword());
					if (!user.getEmail().equals("")) authDAO.updateUserEmail(workspace.getUser(), workspace.getEmail());
				}
				
				if (authDAO.findMailAccountsByUser(workspace.getUser(), false).size() > 0) {
					authDAO.updateMailAccount(mailAccount);
					if (!mailAccount.getMailPassword().equals("")) authDAO.updateMailAccountPassword(mailAccount);
				} else if (mailAccount.getMailHost().length()>0 && mailAccount.getMailFolder().length()>0 && mailAccount.getMailUser().length()>0 &&
						   !mailAccount.getMailPassword().equals("")) {
					authDAO.createMailAccount(mailAccount);
				}
			} catch (SQLException e) {
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_SQLException), e.getMessage());
			}
		}
	}
	
	@Override
	public void deleteMailAccount(int id)  throws OKMException {
		// Disable user configuration modification in demo
		if (!Config.SYSTEM_DEMO) {
			try {
				AuthDAO authDAO = AuthDAO.getInstance();
				authDAO.deleteMailAccount(id);
			} catch (SQLException e) {
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_SQLException), e.getMessage());
			}
		}
	}
	
	@Override
	public String isValidPassword(String password) throws OKMException {
		String msg = "";
		try {
			PasswordValidator passwordValidator = ValidatorFactory.getPasswordValidator();
			try {
				passwordValidator.Validate(password);
			} catch (ValidatorException e) {
				msg = e.getMessage();
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_Repository), e.getMessage());
		}
		return msg;
	}
}
