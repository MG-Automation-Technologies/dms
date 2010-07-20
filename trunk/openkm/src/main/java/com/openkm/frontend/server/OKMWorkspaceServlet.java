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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMAuth;
import com.openkm.api.OKMDashboard;
import com.openkm.api.OKMPropertyGroup;
import com.openkm.bean.PropertyGroup;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.MailAccountDAO;
import com.openkm.dao.UserConfigDAO;
import com.openkm.dao.bean.MailAccount;
import com.openkm.dao.bean.User;
import com.openkm.dao.bean.UserConfig;
import com.openkm.dao.bean.Profile;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTAvailableOption;
import com.openkm.frontend.client.bean.GWTPropertyGroup;
import com.openkm.frontend.client.bean.GWTWorkspace;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMWorkspaceService;
import com.openkm.principal.PrincipalAdapterException;
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
		
		// Schedule time
		workspace.setKeepAliveSchedule(Config.SCHEDULE_SESSION_KEEPALIVE);
		workspace.setDashboardSchedule(Config.SCHEDULE_DASHBOARD_REFRESH);
		
		List<GWTPropertyGroup> wizardPropGrpLst = new ArrayList<GWTPropertyGroup>();
		Profile up = new Profile();
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			UserConfig uc = UserConfigDAO.findByPk(session, session.getUserID());
			up = uc.getProfile();
			String[] wizardProperties = up.getWizard().getPropertyGroups().split(" ");
			
			for (int i=0; i<wizardProperties.length; i++) {
				for (PropertyGroup pg : OKMPropertyGroup.getInstance().getAllGroups(null)) {
					if (pg.getName().equals(wizardProperties[i]) && pg.isVisible()) {
						wizardPropGrpLst.add(Util.copy(pg));
						break;
					}
				}
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_IOException), e.getMessage());
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_ParseException), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_Repository), e.getMessage());
		} finally {
			JCRUtils.logout(session);
		}
		
		// Advanced filters ( used when there a lot of users and groups )
		workspace.setAdvancedFilters(up.getMisc().isAdvancedFilters());
		
		// Is a wizard to uploading documents
		workspace.setWizardPropertyGroups(!up.getWizard().getPropertyGroups().equals(""));
		workspace.setWizardPropertyGroupsList(wizardPropGrpLst);
		workspace.setWizardCategories(up.getWizard().isCategoriesEnabled());
		workspace.setWizardKeywords(up.getWizard().isKeywordsEnabled());
		
		// Is chat enabled and autologin
		workspace.setChatEnabled(up.getChat().isChatEnabled());
		workspace.setChatAutoLogin(up.getChat().isAutoLoginEnabled());
		
		// User quota ( limit user repository size )
		workspace.setUserQuotaEnabled(up.getMisc().getUserQuota() > 0);
		workspace.setUserQuotaLimit(up.getMisc().getUserQuota());
		
		// Stack visibility
		workspace.setStackTaxonomy(up.getStack().isTaxonomyVisible());
		workspace.setStackCategoriesVisible(up.getStack().isCategoriesVisible());
		workspace.setStackThesaurusVisible(up.getStack().isThesaurusVisible());
		workspace.setStackTemplatesVisible(up.getStack().isTemplatesVisible());
		workspace.setStackPersonalVisible(up.getStack().isPersonalVisible());
		workspace.setStackMailVisible(up.getStack().isMailVisible());
		workspace.setStackTrashVisible(up.getStack().isTrashVisible());
		
		// Menus visibility
		workspace.setMenuFileVisible(up.getMenu().isFileVisible());
		workspace.setMenuEditVisible(up.getMenu().isEditVisible());
		workspace.setMenuBookmarksVisible(up.getMenu().isBookmarksVisible());
		workspace.setMenuToolsVisible(up.getMenu().isToolsVisible());
		workspace.setMenuHelpVisible(up.getMenu().isHelpVisible());
				
		// Tab visibility
		workspace.setTabDesktopVisible(up.getTab().isDesktopVisible());
		workspace.setTabSearchVisible(up.getTab().isSearchVisible());
		workspace.setTabDashboardVisible(up.getTab().isDashboardVisible());
		workspace.setTabAdminVisible(getThreadLocalRequest().isUserInRole(Config.DEFAULT_ADMIN_ROLE));
		
		// Tab document visibility
		workspace.setTabDocumentPropertiesVisible(up.getTab().getDocument().isPropertiesVisible());
		workspace.setTabDocumentSecurityVisible(up.getTab().getDocument().isSecurityVisible());
		workspace.setTabDocumentNotesVisible(up.getTab().getDocument().isNotesVisible());
		workspace.setTabDocumentVersionVisible(up.getTab().getDocument().isVersionsVisible());
		workspace.setTabDocumentPreviewVisible(up.getTab().getDocument().isPreviewVisible());
		workspace.setTabDocumentPropertyGroupsVisible(up.getTab().getDocument().isPropertyGroupsVisible());
		
		// Tab folder visibility
		workspace.setTabFolderPropertiesVisible(up.getTab().getFolder().isPropertiesVisible());
		workspace.setTabFolderSecurityVisible(up.getTab().getFolder().isSecurityVisible());
		
		// Tab mail visibility
		workspace.setTabMailPropertiesVisible(up.getTab().getMail().isPropertiesVisible());
		workspace.setTabMailSecurityVisible(up.getTab().getMail().isSecurityVisible());
		
		// Dashboard visibility
		workspace.setDashboardUserVisible(up.getDashboard().isUserVisible());
		workspace.setDashboardMailVisible(up.getDashboard().isMailVisible());
		workspace.setDashboardNewsVisible(up.getDashboard().isNewsVisible());
		workspace.setDashboardGeneralVisible(up.getDashboard().isGeneralVisible());
		workspace.setDashboardWorkflowVisible(up.getDashboard().isWorkflowVisible());
		workspace.setDashboardKeywordsVisible(up.getDashboard().isKeywordsVisible());
		
		// Available options
		GWTAvailableOption availableOption = new GWTAvailableOption();
		
		// Menu File
		availableOption.setCreateFolderOption(up.getMenu().getFile().isCreateFolderVisible());
		availableOption.setFindFolderOption(up.getMenu().getFile().isFindFolderVisible());
		availableOption.setGotoFolderOption(up.getMenu().getFile().isGoFolderVisible());
		availableOption.setDownloadOption(up.getMenu().getFile().isDownloadVisible());
		availableOption.setDownloadPdfOption(up.getMenu().getFile().isDownloadPdfVisible());
		availableOption.setAddDocumentOption(up.getMenu().getFile().isAddDocumentVisible());
		availableOption.setWorkflowOption(up.getMenu().getFile().isStartWorkflowVisible());
		availableOption.setRefreshOption(up.getMenu().getFile().isRefreshVisible());
		availableOption.setScannerOption(up.getMenu().getFile().isScannerVisible());
		availableOption.setUploaderOption(up.getMenu().getFile().isUploaderVisible());
		availableOption.setExportOption(up.getMenu().getFile().isExportVisible());
		availableOption.setCreateFromTemplateOption(up.getMenu().getFile().isCreateFromTemplateVisible());
		availableOption.setPurgeOption(up.getMenu().getFile().isPurgeVisible());
		availableOption.setRestoreOption(up.getMenu().getFile().isRestoreVisible());
		availableOption.setPurgeTrashOption(up.getMenu().getFile().isPurgeTrashVisible());
		availableOption.setSendDocumentLinkOption(up.getMenu().getFile().isSendDocumentLinkVisible());
		availableOption.setSendDocumentAttachmentOption(up.getMenu().getFile().isSendDocumentAttachmentVisible());
				
		// Menu Edit
		availableOption.setLockOption(up.getMenu().getEdit().isLockVisible());
		availableOption.setUnLockOption(up.getMenu().getEdit().isUnlockVisible());
		availableOption.setRenameOption(up.getMenu().getEdit().isRenameVisible());
		availableOption.setCopyOption(up.getMenu().getEdit().isCopyVisible());
		availableOption.setMoveOption(up.getMenu().getEdit().isMoveVisible());
		availableOption.setCheckinOption(up.getMenu().getEdit().isCheckInVisible());
		availableOption.setCheckoutOption(up.getMenu().getEdit().isCheckOutVisible());
		availableOption.setCancelCheckoutOption(up.getMenu().getEdit().isCancelCheckOutVisible());
		availableOption.setDeleteOption(up.getMenu().getEdit().isDeleteVisible());
		availableOption.setAddPropertyGroupOption(up.getMenu().getEdit().isAddPropertyGroupVisible());
		availableOption.setRemovePropertyGroupOption(up.getMenu().getEdit().isRemovePropertyGroupVisible());
		availableOption.setAddSubscription(up.getMenu().getEdit().isAddSubscriptionVisible());
		availableOption.setRemoveSubscription(up.getMenu().getEdit().isRemoveSubscriptionVisible());
		
		// Menu Bookmark
		availableOption.setManageBookmarkOption(up.getMenu().getBookmark().isManageBookmarksVisible());
		availableOption.setAddBookmarkOption(up.getMenu().getBookmark().isAddBookmarkVisible());
		availableOption.setHomeOption(up.getMenu().getBookmark().isGoHomeVisible());
		availableOption.setSetHomeOption(up.getMenu().getBookmark().isSetHomeVisible());
		
		// Menu Tool
		availableOption.setLanguagesOption(up.getMenu().getTool().isLanguagesVisible());
		availableOption.setSkinOption(up.getMenu().getTool().isSkinVisible());
		availableOption.setDebugOption(up.getMenu().getTool().isDebugVisible());
		availableOption.setAdministrationOption(up.getMenu().getTool().isAdministrationVisible());
		availableOption.setPreferencesOption(up.getMenu().getTool().isPreferencesVisible());
		
		// Menu Help
		availableOption.setHelpOption(up.getMenu().getHelp().isHelpVisible());
		availableOption.setDocumentationOption(up.getMenu().getHelp().isDocumentationVisible());
		availableOption.setBugReportOption(up.getMenu().getHelp().isBugTrackingVisible());
		availableOption.setSupportRequestOption(up.getMenu().getHelp().isSupportVisible());
		availableOption.setPublicForumOption(up.getMenu().getHelp().isForumVisible());
		availableOption.setVersionChangesOption(up.getMenu().getHelp().isChangelogVisible());
		availableOption.setProjectWebOption(up.getMenu().getHelp().isWebSiteVisible());
		availableOption.setAboutOption(up.getMenu().getHelp().isAboutVisible());
		
		availableOption.setMediaPlayerOption(true);
		availableOption.setImageViewerOption(true); 
		
		workspace.setAvailableOption(availableOption);
		
		try {
			User user = new User();
			
			if (Config.PRINCIPAL_ADAPTER.equals(com.openkm.principal.DatabasePrincipalAdapter.class.getName())) {
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
			
			workspace.setRoleList(OKMAuth.getInstance().getRolesByUser(null, user.getId()));
			 
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_SQLException), e.getMessage());
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMWorkspaceService, ErrorCode.CAUSE_PrincipalAdapterException), e.getMessage());
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
			docSize = new Double(OKMDashboard.getInstance().getUserDocumentsSize(null));
			
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
