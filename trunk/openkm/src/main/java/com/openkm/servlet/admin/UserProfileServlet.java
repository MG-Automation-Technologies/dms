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
import java.security.NoSuchAlgorithmException;

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
import com.openkm.dao.UserProfileDAO;
import com.openkm.dao.bean.UserProfile;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtil;

/**
 * User twitter accounts servlet
 */
public class UserProfileServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(UserProfileServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
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
			}
			
			if (action.equals("") || WebUtil.getBoolean(request, "persist")) {
				list(session, request, response);
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
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			sendErrorRedirect(request,response, e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * New user
	 */
	private void create(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException {
		log.debug("create({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			UserProfile up = getUserProfile(request);
			UserProfileDAO.create(up);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_USER_PROFILE_CREATE", null, up.toString());
		} else {
			ServletContext sc = getServletContext();
			UserProfile up = new UserProfile();
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("up", up);
			sc.getRequestDispatcher("/admin/user_profile_edit.jsp").forward(request, response);
		}
		
		log.debug("create: void");
	}
	
	/**
	 * Edit user
	 */
	private void edit(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("edit({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			UserProfile up = getUserProfile(request);
			UserProfileDAO.update(up);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_USER_PROFILE_EDIT", Integer.toString(up.getId()), up.toString());
		} else {
			ServletContext sc = getServletContext();
			int upId = WebUtil.getInt(request, "up_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("up", UserProfileDAO.findByPk(upId));
			sc.getRequestDispatcher("/admin/user_profile_edit.jsp").forward(request, response);
		}
		
		log.debug("edit: void");
	}

	/**
	 * Update user
	 */
	private void delete(Session session, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DatabaseException, NoSuchAlgorithmException {
		log.debug("delete({}, {}, {})", new Object[] { session, request, response });
		
		if (WebUtil.getBoolean(request, "persist")) {
			int upId = WebUtil.getInt(request, "up_id");
			UserProfileDAO.delete(upId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_USER_PROFILE_DELETE", Integer.toString(upId), null);
		} else {
			ServletContext sc = getServletContext();
			int upId = WebUtil.getInt(request, "up_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("up", UserProfileDAO.findByPk(upId));
			sc.getRequestDispatcher("/admin/user_profile_edit.jsp").forward(request, response);
		}
		
		log.debug("delete: void");
	}

	/**
	 * List user profiles
	 */
	private void list(Session session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("list({}, {}, {})", new Object[] { session, request, response });
		ServletContext sc = getServletContext();
		sc.setAttribute("userProfiles", UserProfileDAO.findAll(false));
		sc.getRequestDispatcher("/admin/user_profile_list.jsp").forward(request, response);
		log.debug("list: void");
	}
	
	/**
	 * Fille user profile object
	 */
	private UserProfile getUserProfile(HttpServletRequest request) {
		UserProfile up = new UserProfile();
		
		up.setId(WebUtil.getInt(request, "up_id"));
		up.setName(WebUtil.getString(request, "up_name"));
		up.setActive(WebUtil.getBoolean(request, "up_active"));
		
		// Misc
		up.getMisc().setUserQuota(WebUtil.getLong(request, "up_user_quota"));
		up.getMisc().setAdvancedFilters(WebUtil.getBoolean(request, "up_advanced_filter"));
		
		// Wizard
		up.getWizard().setPropertyGroups(WebUtil.getString(request, "up_wizard_property_groups"));
		up.getWizard().setKeywordsEnabled(WebUtil.getBoolean(request, "up_wizard_keywords"));
		up.getWizard().setCategoriesEnabled(WebUtil.getBoolean(request, "up_wizard_categories"));
		
		// Chat
		up.getChat().setChatEnabled(WebUtil.getBoolean(request, "up_chat_enabled"));
		up.getChat().setAutoLoginEnabled(WebUtil.getBoolean(request, "up_chat_auto_login"));
		
		// Stack
		up.getStack().setTaxonomyVisible(WebUtil.getBoolean(request, "up_stack_taxonomy_visible"));
		up.getStack().setCategoriesVisible(WebUtil.getBoolean(request, "up_stack_categories_visible"));
		up.getStack().setThesaurusVisible(WebUtil.getBoolean(request, "up_stack_thesaurus_visible"));
		up.getStack().setTemplatesVisible(WebUtil.getBoolean(request, "up_stack_templates_visible"));
		up.getStack().setPersonalVisible(WebUtil.getBoolean(request, "up_stack_personal_visible"));
		up.getStack().setMailVisible(WebUtil.getBoolean(request, "up_stack_mail_visible"));
		up.getStack().setTrashVisible(WebUtil.getBoolean(request, "up_stack_trash_visible"));
		
		// Menu
		up.getMenu().setFileVisible(WebUtil.getBoolean(request, "up_menu_file_visible"));
		up.getMenu().setEditVisible(WebUtil.getBoolean(request, "up_menu_edit_visible"));
		up.getMenu().setToolsVisible(WebUtil.getBoolean(request, "up_menu_tools_visible"));
		up.getMenu().setBookmarksVisible(WebUtil.getBoolean(request, "up_menu_bookmarks_visible"));
		up.getMenu().setHelpVisible(WebUtil.getBoolean(request, "up_menu_help_visible"));
		
		// Tab
		up.getTab().setDesktopVisible(WebUtil.getBoolean(request, "up_tab_desktop_visible"));
		up.getTab().setSearchVisible(WebUtil.getBoolean(request, "up_tab_search_visible"));
		up.getTab().setDashboardVisible(WebUtil.getBoolean(request, "up_tab_dashboard_visible"));
		
		// Dashboard
		up.getDashboard().setUserVisible(WebUtil.getBoolean(request, "up_dashboard_user_visible"));
		up.getDashboard().setMailVisible(WebUtil.getBoolean(request, "up_dashboard_mail_visible"));
		up.getDashboard().setNewsVisible(WebUtil.getBoolean(request, "up_dashboard_news_visible"));
		up.getDashboard().setGeneralVisible(WebUtil.getBoolean(request, "up_dashboard_general_visible"));
		up.getDashboard().setWorkflowVisible(WebUtil.getBoolean(request, "up_dashboard_workflow_visible"));
		up.getDashboard().setKeywordsVisible(WebUtil.getBoolean(request, "up_dashboard_keywords_visible"));
		
		// Action
		up.getAction().setCreateFolderVisible(WebUtil.getBoolean(request, "up_action_create_folder_visible"));
		up.getAction().setFindFolderVisible(WebUtil.getBoolean(request, "up_action_find_folder_visible"));
		up.getAction().setGoFolderVisible(WebUtil.getBoolean(request, "up_action_go_folder_visible"));
		up.getAction().setDownloadVisible(WebUtil.getBoolean(request, "up_action_download_visible"));
		up.getAction().setDownloadPdfVisible(WebUtil.getBoolean(request, "up_action_download_pdf_visible"));
		up.getAction().setAddDocumentVisible(WebUtil.getBoolean(request, "up_action_add_document_visible"));
		up.getAction().setAddPropertyGroupVisible(WebUtil.getBoolean(request, "up_action_add_property_group_visible"));
		up.getAction().setRemovePropertyGroupVisible(WebUtil.getBoolean(request, "up_action_remove_property_group_visible"));
		up.getAction().setStartWorkflowVisible(WebUtil.getBoolean(request, "up_action_start_workflow_visible"));
		up.getAction().setAddSubscriptionVisible(WebUtil.getBoolean(request, "up_action_add_subscription_visible"));
		up.getAction().setRemoveSubscriptionVisible(WebUtil.getBoolean(request, "up_action_remove_subscription_visible"));
		up.getAction().setRefreshVisible(WebUtil.getBoolean(request, "up_action_refresh_visible"));
		up.getAction().setGoHomeVisible(WebUtil.getBoolean(request, "up_action_go_home_visible"));
		up.getAction().setSetHomeVisible(WebUtil.getBoolean(request, "up_action_set_home_visible"));
		up.getAction().setScannerVisible(WebUtil.getBoolean(request, "up_action_scanner_visible"));
		up.getAction().setUploaderVisible(WebUtil.getBoolean(request, "up_action_uploader_visible"));
		up.getAction().setAddBookmarkVisible(WebUtil.getBoolean(request, "up_action_add_bookmark_visible"));
		up.getAction().setManageBookmarksVisible(WebUtil.getBoolean(request, "up_action_manage_bookmarks_visible"));
		up.getAction().setExportVisible(WebUtil.getBoolean(request, "up_action_export_visible"));
		up.getAction().setCreateFromTemplateVisible(WebUtil.getBoolean(request, "up_action_create_from_template_visible"));
		up.getAction().setPurgeVisible(WebUtil.getBoolean(request, "up_action_purge_visible"));
		up.getAction().setPurgeTrashVisible(WebUtil.getBoolean(request, "up_action_purge_trash_visible"));
		up.getAction().setRestoreVisible(WebUtil.getBoolean(request, "up_action_restore_visible"));
		up.getAction().setSendDocumentLinkVisible(WebUtil.getBoolean(request, "up_action_send_document_link_visible"));
		up.getAction().setSendDocumentAttachmentVisible(WebUtil.getBoolean(request, "up_action_send_document_attachment_visible"));
		up.getAction().setChangeSkinVisible(WebUtil.getBoolean(request, "up_action_change_skin_visible"));
		up.getAction().setDebugVisible(WebUtil.getBoolean(request, "up_action_debug_visible"));
		up.getAction().setAdministrationVisible(WebUtil.getBoolean(request, "up_action_administration_visible"));
		
		// Edit
		up.getEdit().setRenameVisible(WebUtil.getBoolean(request, "up_edit_rename_visible"));
		up.getEdit().setCopyVisible(WebUtil.getBoolean(request, "up_edit_copy_visible"));
		up.getEdit().setMoveVisible(WebUtil.getBoolean(request, "up_edit_move_visible"));
		up.getEdit().setLockVisible(WebUtil.getBoolean(request, "up_edit_lock_visible"));
		up.getEdit().setUnlockVisible(WebUtil.getBoolean(request, "up_edit_unlock_visible"));
		up.getEdit().setCheckInVisible(WebUtil.getBoolean(request, "up_edit_check_in_visible"));
		up.getEdit().setCheckOutVisible(WebUtil.getBoolean(request, "up_edit_check_out_visible"));
		up.getEdit().setCancelCheckOutVisible(WebUtil.getBoolean(request, "up_edit_cancel_check_out_visible"));
		up.getEdit().setDeleteVisible(WebUtil.getBoolean(request, "up_edit_delete_visible"));
		
		// Help
		up.getHelp().setHelpVisible(WebUtil.getBoolean(request, "up_help_help_visible"));
		up.getHelp().setDocumentationVisible(WebUtil.getBoolean(request, "up_help_documentation_visible"));
		up.getHelp().setBugTrackingVisible(WebUtil.getBoolean(request, "up_help_bug_tracking_visible"));
		up.getHelp().setSupportVisible(WebUtil.getBoolean(request, "up_help_support_visible"));
		up.getHelp().setForumVisible(WebUtil.getBoolean(request, "up_help_forum_visible"));
		up.getHelp().setChangelogVisible(WebUtil.getBoolean(request, "up_help_changelog_visible"));
		up.getHelp().setWebSiteVisible(WebUtil.getBoolean(request, "up_help_web_site_visible"));
		up.getHelp().setAboutVisible(WebUtil.getBoolean(request, "up_help_about_visible"));
				
		return up;
	}
}
