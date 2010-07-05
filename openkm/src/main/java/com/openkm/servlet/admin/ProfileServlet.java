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
import com.openkm.dao.ProfileDAO;
import com.openkm.dao.bean.Profile;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;
import com.openkm.util.WebUtil;

/**
 * User profiles servlet
 */
public class ProfileServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ProfileServlet.class);
	
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
			Profile prf = getUserProfile(request);
			ProfileDAO.create(prf);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_USER_PROFILE_CREATE", null, prf.toString());
		} else {
			ServletContext sc = getServletContext();
			Profile prf = new Profile();
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("prf", prf);
			sc.getRequestDispatcher("/admin/profile_edit.jsp").forward(request, response);
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
			Profile prf = getUserProfile(request);
			ProfileDAO.update(prf);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_USER_PROFILE_EDIT", Integer.toString(prf.getId()), prf.toString());
		} else {
			ServletContext sc = getServletContext();
			int prfId = WebUtil.getInt(request, "prf_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("prf", ProfileDAO.findByPk(prfId));
			sc.getRequestDispatcher("/admin/profile_edit.jsp").forward(request, response);
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
			int prfId = WebUtil.getInt(request, "prf_id");
			ProfileDAO.delete(prfId);
			
			// Activity log
			UserActivity.log(session.getUserID(), "ADMIN_USER_PROFILE_DELETE", Integer.toString(prfId), null);
		} else {
			ServletContext sc = getServletContext();
			int prfId = WebUtil.getInt(request, "prf_id");
			sc.setAttribute("action", WebUtil.getString(request, "action"));
			sc.setAttribute("persist", true);
			sc.setAttribute("prf", ProfileDAO.findByPk(prfId));
			sc.getRequestDispatcher("/admin/profile_edit.jsp").forward(request, response);
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
		sc.setAttribute("userProfiles", ProfileDAO.findAll(false));
		sc.getRequestDispatcher("/admin/profile_list.jsp").forward(request, response);
		log.debug("list: void");
	}
	
	/**
	 * Fille user profile object
	 */
	private Profile getUserProfile(HttpServletRequest request) {
		Profile prf = new Profile();
		
		prf.setId(WebUtil.getInt(request, "prf_id"));
		prf.setName(WebUtil.getString(request, "prf_name"));
		prf.setActive(WebUtil.getBoolean(request, "prf_active"));
		
		// Misc
		prf.getMisc().setUserQuota(WebUtil.getLong(request, "prf_user_quota"));
		prf.getMisc().setAdvancedFilters(WebUtil.getBoolean(request, "prf_advanced_filter"));
		
		// Wizard
		prf.getWizard().setPropertyGroups(WebUtil.getString(request, "prf_wizard_property_groups"));
		prf.getWizard().setKeywordsEnabled(WebUtil.getBoolean(request, "prf_wizard_keywords"));
		prf.getWizard().setCategoriesEnabled(WebUtil.getBoolean(request, "prf_wizard_categories"));
		
		// Chat
		prf.getChat().setChatEnabled(WebUtil.getBoolean(request, "prf_chat_enabled"));
		prf.getChat().setAutoLoginEnabled(WebUtil.getBoolean(request, "prf_chat_auto_login"));
		
		// Stack
		prf.getStack().setTaxonomyVisible(WebUtil.getBoolean(request, "prf_stack_taxonomy_visible"));
		prf.getStack().setCategoriesVisible(WebUtil.getBoolean(request, "prf_stack_categories_visible"));
		prf.getStack().setThesaurusVisible(WebUtil.getBoolean(request, "prf_stack_thesaurus_visible"));
		prf.getStack().setTemplatesVisible(WebUtil.getBoolean(request, "prf_stack_templates_visible"));
		prf.getStack().setPersonalVisible(WebUtil.getBoolean(request, "prf_stack_personal_visible"));
		prf.getStack().setMailVisible(WebUtil.getBoolean(request, "prf_stack_mail_visible"));
		prf.getStack().setTrashVisible(WebUtil.getBoolean(request, "prf_stack_trash_visible"));
		
		// Menu
		prf.getMenu().setFileVisible(WebUtil.getBoolean(request, "prf_menu_file_visible"));
		prf.getMenu().setEditVisible(WebUtil.getBoolean(request, "prf_menu_edit_visible"));
		prf.getMenu().setToolsVisible(WebUtil.getBoolean(request, "prf_menu_tools_visible"));
		prf.getMenu().setBookmarksVisible(WebUtil.getBoolean(request, "prf_menu_bookmarks_visible"));
		prf.getMenu().setHelpVisible(WebUtil.getBoolean(request, "prf_menu_help_visible"));
		
		// Menu File
		prf.getMenu().getFile().setCreateFolderVisible(WebUtil.getBoolean(request, "prf_menu_file_create_folder_visible"));
		prf.getMenu().getFile().setFindFolderVisible(WebUtil.getBoolean(request, "prf_menu_file_find_folder_visible"));
		prf.getMenu().getFile().setGoFolderVisible(WebUtil.getBoolean(request, "prf_menu_file_go_folder_visible"));
		prf.getMenu().getFile().setDownloadVisible(WebUtil.getBoolean(request, "prf_menu_file_download_visible"));
		prf.getMenu().getFile().setDownloadPdfVisible(WebUtil.getBoolean(request, "prf_menu_file_download_pdf_visible"));
		prf.getMenu().getFile().setAddDocumentVisible(WebUtil.getBoolean(request, "prf_menu_file_add_document_visible"));
		prf.getMenu().getFile().setStartWorkflowVisible(WebUtil.getBoolean(request, "prf_menu_file_start_workflow_visible"));
		prf.getMenu().getFile().setRefreshVisible(WebUtil.getBoolean(request, "prf_menu_file_refresh_visible"));
		prf.getMenu().getFile().setScannerVisible(WebUtil.getBoolean(request, "prf_menu_file_scanner_visible"));
		prf.getMenu().getFile().setUploaderVisible(WebUtil.getBoolean(request, "prf_menu_file_uploader_visible"));
		prf.getMenu().getFile().setExportVisible(WebUtil.getBoolean(request, "prf_menu_file_export_visible"));
		prf.getMenu().getFile().setCreateFromTemplateVisible(WebUtil.getBoolean(request, "prf_menu_file_create_from_template_visible"));
		prf.getMenu().getFile().setPurgeVisible(WebUtil.getBoolean(request, "prf_menu_file_purge_visible"));
		prf.getMenu().getFile().setPurgeTrashVisible(WebUtil.getBoolean(request, "prf_menu_file_purge_trash_visible"));
		prf.getMenu().getFile().setRestoreVisible(WebUtil.getBoolean(request, "prf_menu_file_restore_visible"));
		prf.getMenu().getFile().setSendDocumentLinkVisible(WebUtil.getBoolean(request, "prf_menu_file_send_document_link_visible"));
		prf.getMenu().getFile().setSendDocumentAttachmentVisible(WebUtil.getBoolean(request, "prf_menu_file_send_document_attachment_visible"));

		// Menu Bookmarks
		prf.getMenu().getBookmark().setManageBookmarksVisible(WebUtil.getBoolean(request, "prf_menu_bookmark_manage_bookmarks_visible"));
		prf.getMenu().getBookmark().setAddBookmarkVisible(WebUtil.getBoolean(request, "prf_menu_bookmark_add_bookmark_visible"));
		prf.getMenu().getBookmark().setSetHomeVisible(WebUtil.getBoolean(request, "prf_menu_bookmark_set_home_visible"));
		prf.getMenu().getBookmark().setGoHomeVisible(WebUtil.getBoolean(request, "prf_menu_bookmark_go_home_visible"));
		
		// Menu Tools
		prf.getMenu().getTool().setChangeSkinVisible(WebUtil.getBoolean(request, "prf_menu_tool_change_skin_visible"));
		prf.getMenu().getTool().setDebugVisible(WebUtil.getBoolean(request, "prf_menu_tool_debug_visible"));
		prf.getMenu().getTool().setAdministrationVisible(WebUtil.getBoolean(request, "prf_menu_tool_administration_visible"));
		
		// Menu Edit
		prf.getMenu().getEdit().setRenameVisible(WebUtil.getBoolean(request, "prf_menu_edit_rename_visible"));
		prf.getMenu().getEdit().setCopyVisible(WebUtil.getBoolean(request, "prf_menu_edit_copy_visible"));
		prf.getMenu().getEdit().setMoveVisible(WebUtil.getBoolean(request, "prf_menu_edit_move_visible"));
		prf.getMenu().getEdit().setLockVisible(WebUtil.getBoolean(request, "prf_menu_edit_lock_visible"));
		prf.getMenu().getEdit().setUnlockVisible(WebUtil.getBoolean(request, "prf_menu_edit_unlock_visible"));
		prf.getMenu().getEdit().setCheckInVisible(WebUtil.getBoolean(request, "prf_menu_edit_check_in_visible"));
		prf.getMenu().getEdit().setCheckOutVisible(WebUtil.getBoolean(request, "prf_menu_edit_check_out_visible"));
		prf.getMenu().getEdit().setCancelCheckOutVisible(WebUtil.getBoolean(request, "prf_menu_edit_cancel_check_out_visible"));
		prf.getMenu().getEdit().setDeleteVisible(WebUtil.getBoolean(request, "prf_menu_edit_delete_visible"));
		prf.getMenu().getEdit().setAddPropertyGroupVisible(WebUtil.getBoolean(request, "prf_menu_edit_add_property_group_visible"));
		prf.getMenu().getEdit().setRemovePropertyGroupVisible(WebUtil.getBoolean(request, "prf_menu_edit_remove_property_group_visible"));
		prf.getMenu().getEdit().setAddSubscriptionVisible(WebUtil.getBoolean(request, "prf_menu_edit_add_subscription_visible"));
		prf.getMenu().getEdit().setRemoveSubscriptionVisible(WebUtil.getBoolean(request, "prf_menu_edit_remove_subscription_visible"));
		
		// Menu Help
		prf.getMenu().getHelp().setHelpVisible(WebUtil.getBoolean(request, "prf_menu_help_help_visible"));
		prf.getMenu().getHelp().setDocumentationVisible(WebUtil.getBoolean(request, "prf_menu_help_documentation_visible"));
		prf.getMenu().getHelp().setBugTrackingVisible(WebUtil.getBoolean(request, "prf_menu_help_bug_tracking_visible"));
		prf.getMenu().getHelp().setSupportVisible(WebUtil.getBoolean(request, "prf_menu_help_support_visible"));
		prf.getMenu().getHelp().setForumVisible(WebUtil.getBoolean(request, "prf_menu_help_forum_visible"));
		prf.getMenu().getHelp().setChangelogVisible(WebUtil.getBoolean(request, "prf_menu_help_changelog_visible"));
		prf.getMenu().getHelp().setWebSiteVisible(WebUtil.getBoolean(request, "prf_menu_help_web_site_visible"));
		prf.getMenu().getHelp().setAboutVisible(WebUtil.getBoolean(request, "prf_menu_help_about_visible"));
		
		// Tab
		prf.getTab().setDesktopVisible(WebUtil.getBoolean(request, "prf_tab_desktop_visible"));
		prf.getTab().setSearchVisible(WebUtil.getBoolean(request, "prf_tab_search_visible"));
		prf.getTab().setDashboardVisible(WebUtil.getBoolean(request, "prf_tab_dashboard_visible"));
		
		// Tab Document
		prf.getTab().getDocument().setPropertiesVisible(WebUtil.getBoolean(request, "prf_tab_document_properties_visible"));
		prf.getTab().getDocument().setSecurityVisible(WebUtil.getBoolean(request, "prf_tab_document_security_visible"));
		prf.getTab().getDocument().setNotesVisible(WebUtil.getBoolean(request, "prf_tab_document_notes_visible"));
		prf.getTab().getDocument().setVersionsVisible(WebUtil.getBoolean(request, "prf_tab_document_versions_visible"));
		prf.getTab().getDocument().setPreviewVisible(WebUtil.getBoolean(request, "prf_tab_document_preview_visible"));
		prf.getTab().getDocument().setPropertyGroupsVisible(WebUtil.getBoolean(request, "prf_tab_document_property_groups_visible"));

		// Tab Folder
		prf.getTab().getFolder().setPropertiesVisible(WebUtil.getBoolean(request, "prf_tab_folder_properties_visible"));
		prf.getTab().getFolder().setSecurityVisible(WebUtil.getBoolean(request, "prf_tab_folder_security_visible"));

		// Tab Mail
		prf.getTab().getMail().setPropertiesVisible(WebUtil.getBoolean(request, "prf_tab_mail_properties_visible"));
		prf.getTab().getMail().setSecurityVisible(WebUtil.getBoolean(request, "prf_tab_mail_security_visible"));

		// Dashboard
		prf.getDashboard().setUserVisible(WebUtil.getBoolean(request, "prf_dashboard_user_visible"));
		prf.getDashboard().setMailVisible(WebUtil.getBoolean(request, "prf_dashboard_mail_visible"));
		prf.getDashboard().setNewsVisible(WebUtil.getBoolean(request, "prf_dashboard_news_visible"));
		prf.getDashboard().setGeneralVisible(WebUtil.getBoolean(request, "prf_dashboard_general_visible"));
		prf.getDashboard().setWorkflowVisible(WebUtil.getBoolean(request, "prf_dashboard_workflow_visible"));
		prf.getDashboard().setKeywordsVisible(WebUtil.getBoolean(request, "prf_dashboard_keywords_visible"));
				
		return prf;
	}
}
