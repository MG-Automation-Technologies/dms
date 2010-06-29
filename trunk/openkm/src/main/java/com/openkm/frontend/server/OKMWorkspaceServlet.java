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

import java.util.Iterator;

import javax.jcr.LoginException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMDashboard;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.MailAccountDAO;
import com.openkm.dao.UserConfigDAO;
import com.openkm.dao.bean.MailAccount;
import com.openkm.dao.bean.User;
import com.openkm.dao.bean.UserConfig;
import com.openkm.dao.bean.UserProfile;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTAvailableOption;
import com.openkm.frontend.client.bean.GWTWorkspace;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMWorkspaceService;
import com.openkm.util.JCRUtils;
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
		updateSessionManager();
		
		if ( Config.APPLICATION_URL!= null && Config.APPLICATION_URL.indexOf("/OpenKM")>0) {
			workspace.setApplicationURL(Config.APPLICATION_URL);
		}
		
		workspace.setUser(getThreadLocalRequest().getRemoteUser());
		workspace.setAppVersion(WarUtils.getAppVersion().toString());
		workspace.setWorkflowRunConfigForm(Config.WORKFLOW_RUN_CONFIG_FORM);
		workspace.setWorkflowProcessIntanceVariableUUID(Config.WORKFLOW_PROCESS_INSTANCE_VARIABLE_UUID);
		workspace.setWorkflowProcessIntanceVariablePath(Config.WORKFLOW_PROCESS_INSTANCE_VARIABLE_PATH);
		workspace.setToken(getThreadLocalRequest().getSession().getId());
		
		// Is a wizard to uploading documents
		workspace.setWizardPropertyGroups(Config.WIZARD_PROPERTY_GROUPS.length>0);
		workspace.setWizardCategories(!Config.WIZARD_CATEGORIES.equals(""));
		workspace.setWizardKeywords(!Config.WIZARD_KEYWORDS.equals(""));
		
		// Schedule time
		workspace.setKeepAliveSchedule(Config.SCHEDULE_SESSION_KEEPALIVE);
		workspace.setDashboardSchedule(Config.SCHEDULE_DASHBOARD_REFRESH);
		
		UserProfile up = new UserProfile();
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			UserConfig uc = UserConfigDAO.findByPk(session, session.getUserID());
			up = uc.getProfile();
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			throw new OKMException("###", e.getMessage());
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException("###", e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException("###", e.getMessage());
		} finally {
			JCRUtils.logout(session);
		}
		
		// Advanced filters ( used when there a lot of users and groups )
		workspace.setAdvancedFilters(true);
		
		// Is chat enabled and autologin
		workspace.setChatEnabled(up.isChatEnabled());
		workspace.setChatAutoLogin(up.isChatAutoLogin());
		
		// User quota ( limit user repository size )
		workspace.setUserQuotaEnabled(up.isUserQuotaEnabled());
		workspace.setUserQuotaLimit(up.getUserQuotaLimit());
		
		// Stack visibility
		workspace.setStackCategoriesVisible(up.isStackCategoriesVisible());
		workspace.setStackThesaurusVisible(up.isStackThesaurusVisible());
		workspace.setStackTemplatesVisible(up.isStackTemplatesVisible());
		workspace.setStackPersonalVisible(up.isStackPersonalVisible());
		workspace.setStackMailVisible(up.isStackMailVisible());
		
		// Menus visibility
		workspace.setMenuEditVisible(up.isMenuEditVisible());
		workspace.setMenuBookmarksVisible(up.isMenuBookmarksVisible());
		workspace.setMenuToolsVisible(up.isMenuToolsVisible());
		workspace.setMenuHelpVisible(up.isMenuHelpVisible());
		
		// Tab visibility
		workspace.setTabDesktopVisible(up.isTabDesktopVisible());
		workspace.setTabSearchVisible(up.isTabSearchVisible());
		workspace.setTabDashboardVisible(up.isTabDashboardVisible());
		workspace.setTabAdminVisible(getThreadLocalRequest().isUserInRole(Config.DEFAULT_ADMIN_ROLE));
		
		// Dashboard visibility
		workspace.setDashboardUserVisible(up.isDashboardUserVisible());
		workspace.setDashboardMailVisible(up.isDashboardMailVisible());
		workspace.setDashboardNewsVisible(up.isDashboardNewsVisible());
		workspace.setDashboardGeneralVisible(up.isDashboardGeneralVisible());
		workspace.setDashboardWorkflowVisible(up.isDashboardWorkflowVisible());
		workspace.setDashboardKeywordsVisible(up.isDashboardKeywordsVisible());
		
		// Available options
		GWTAvailableOption availableOption = new GWTAvailableOption();
		availableOption.setCreateFolderOption(true);
		availableOption.setFindFolderOption(true);
		availableOption.setDownloadOption(true);
		availableOption.setDownloadPdfOption(true);
		availableOption.setLockOption(true);
		availableOption.setUnLockOption(true);
		availableOption.setAddDocumentOption(true);
		availableOption.setCheckinOption(true);
		availableOption.setCheckoutOption(true);
		availableOption.setCancelCheckoutOption(true);
		availableOption.setDeleteOption(true);	
		availableOption.setAddPropertyGroupOption(true);
		availableOption.setRemovePropertyGroupOption(true);
		availableOption.setWorkflowOption(true);
		availableOption.setAddSubscription(true);
		availableOption.setRemoveSubscription(true);
		availableOption.setRefreshOption(true);
		availableOption.setHomeOption(true);
		availableOption.setScannerOption(true);
		availableOption.setUploaderOption(true);
		availableOption.setRenameOption(true);
		availableOption.setCopyOption(true);
		availableOption.setMoveOption(true);
		availableOption.setAddBookmarkOption(true);
		
		availableOption.setSetHomeOption(true);
		availableOption.setExportOption(true);
		availableOption.setMediaPlayerOption(true);
		availableOption.setImageViewerOption(true);
		availableOption.setGotoFolderOption(true);
		availableOption.setCreateFromTemplateOption(true);
		availableOption.setPurgeOption(true);
		availableOption.setRestoreOption(true);
		availableOption.setPurgeTrashOption(true);
		availableOption.setSendDocumentLinkOption(true);
		availableOption.setSkinOption(true);
		availableOption.setDebugOption(true);
		availableOption.setAdministrationOption(true);
		availableOption.setManageBookmarkOption(true);
		availableOption.setHelpOption(true);
		availableOption.setDocumentationOption(true);
		availableOption.setBugReportOption(true);
		availableOption.setSupportRequestOption(true);
		availableOption.setPublicForumOption(true);
		availableOption.setVersionChangesOption(true);
		availableOption.setProjectWebOption(true);
		availableOption.setAboutOption(true);
		workspace.setAvailableOption(availableOption);

		try {
			User user = new User();
			
			if (Config.PRINCIPAL_ADAPTER.equals("com.openkm.principal.DatabasePrincipalAdapter")) {
				user = AuthDAO.findUserByPk(getThreadLocalRequest().getRemoteUser());
				workspace.setEmail(user.getEmail());
			} else {
				user.setId(getThreadLocalRequest().getRemoteUser());
				user.setName("");
				user.setEmail("");
				user.setActive(true);
				user.setPassword("");
			}
			
			for (Iterator<MailAccount> it = MailAccountDAO.findByUser(getThreadLocalRequest().getRemoteUser(), true).iterator(); it.hasNext();) {
				MailAccount mailAccount = it.next();
				workspace.setImapHost(mailAccount.getMailHost());
				workspace.setImapUser(mailAccount.getMailUser());
				workspace.setImapFolder(mailAccount.getMailFolder());
				workspace.setImapID(mailAccount.getId());
			}
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
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
		Double docSize = new Double(0);
		updateSessionManager();
		
		try {
			docSize = new Double(OKMDashboard.getInstance().getUserDocumentsSize());
			
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		}
		
		return docSize;
	}
	
	@Override
	public void updateUserWorkspace(GWTWorkspace workspace) throws OKMException {
		updateSessionManager();
		
		// For updating user
		User user = new User();
		user.setId(workspace.getUser());
		user.setPassword(workspace.getPassword());
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
				// Can change password
				if (Config.PRINCIPAL_ADAPTER.equals("com.openkm.principal.DatabasePrincipalAdapter")) {
					AuthDAO.updateUserPassword(workspace.getUser(), workspace.getPassword());
					if (!user.getEmail().equals("")) AuthDAO.updateUserEmail(workspace.getUser(), workspace.getEmail());
				}
				
				if (MailAccountDAO.findByUser(workspace.getUser(), false).size() > 0) {
					MailAccountDAO.update(mailAccount);
					
					if (!mailAccount.getMailPassword().equals("")) {
						MailAccountDAO.updatePassword(mailAccount.getId(), mailAccount.getMailPassword());
					}
				} else if (mailAccount.getMailHost().length()>0 && mailAccount.getMailFolder().length()>0 && mailAccount.getMailUser().length()>0 &&
						   !mailAccount.getMailPassword().equals("")) {
					MailAccountDAO.create(mailAccount);
				}
			} catch (DatabaseException e) {
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_SQLException), e.getMessage());
			}
		}
	}
	
	@Override
	public void deleteMailAccount(int id)  throws OKMException {
		updateSessionManager();
		
		// Disable user configuration modification in demo
		if (!Config.SYSTEM_DEMO) {
			try {
				MailAccountDAO.delete(id);
			} catch (DatabaseException e) {
				throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_SQLException), e.getMessage());
			}
		}
	}
	
	@Override
	public String isValidPassword(String password) throws OKMException {
		String msg = "";
		updateSessionManager();
		
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
