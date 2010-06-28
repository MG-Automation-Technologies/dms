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

package com.openkm.dao.bean;

import java.io.Serializable;

public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private boolean advancedFilters;
	private long userQuotaSize;
	private boolean userQuotaEnabled;
	private boolean wizardPropertyGroups;
	private boolean wizardKeywords;
	private boolean wizardCategories;
	private boolean chatEnabled;
	private boolean chatAutoLogin;	
	private boolean stackCategoriesVisible;
	private boolean stackThesaurusVisible;
	private boolean stackPersonalVisible;
	private boolean stackMailVisible;
	private boolean menuEditVisible;
	private boolean menuToolsVisible;
	private boolean menuBookmarkVisible;
	private boolean menuHelpVisible;
	private boolean tabDesktopVisible;
	private boolean tabSearchVisible;
	private boolean tabDashboardVisible;
	private boolean dashboardUserVisible;
	private boolean dashboardMailVisible;
	private boolean dashboardNewsVisible;
	private boolean dashboardGeneralVisible;
	private boolean dashboardWorkflowVisible;
	private boolean dashboardKeywordsVisible;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAdvancedFilters() {
		return advancedFilters;
	}

	public void setAdvancedFilters(boolean advancedFilters) {
		this.advancedFilters = advancedFilters;
	}

	public long getUserQuotaSize() {
		return userQuotaSize;
	}

	public void setUserQuotaSize(long userQuotaSize) {
		this.userQuotaSize = userQuotaSize;
	}

	public boolean isUserQuotaEnabled() {
		return userQuotaEnabled;
	}

	public void setUserQuotaEnabled(boolean userQuotaEnabled) {
		this.userQuotaEnabled = userQuotaEnabled;
	}

	public boolean isWizardPropertyGroups() {
		return wizardPropertyGroups;
	}

	public void setWizardPropertyGroups(boolean wizardPropertyGroups) {
		this.wizardPropertyGroups = wizardPropertyGroups;
	}

	public boolean isWizardKeywords() {
		return wizardKeywords;
	}

	public void setWizardKeywords(boolean wizardKeywords) {
		this.wizardKeywords = wizardKeywords;
	}

	public boolean isWizardCategories() {
		return wizardCategories;
	}

	public void setWizardCategories(boolean wizardCategories) {
		this.wizardCategories = wizardCategories;
	}

	public boolean isChatEnabled() {
		return chatEnabled;
	}

	public void setChatEnabled(boolean chatEnabled) {
		this.chatEnabled = chatEnabled;
	}

	public boolean isChatAutoLogin() {
		return chatAutoLogin;
	}

	public void setChatAutoLogin(boolean chatAutoLogin) {
		this.chatAutoLogin = chatAutoLogin;
	}

	public boolean isStackCategoriesVisible() {
		return stackCategoriesVisible;
	}

	public void setStackCategoriesVisible(boolean stackCategoriesVisible) {
		this.stackCategoriesVisible = stackCategoriesVisible;
	}

	public boolean isStackThesaurusVisible() {
		return stackThesaurusVisible;
	}

	public void setStackThesaurusVisible(boolean stackThesaurusVisible) {
		this.stackThesaurusVisible = stackThesaurusVisible;
	}

	public boolean isStackPersonalVisible() {
		return stackPersonalVisible;
	}

	public void setStackPersonalVisible(boolean stackPersonalVisible) {
		this.stackPersonalVisible = stackPersonalVisible;
	}

	public boolean isStackMailVisible() {
		return stackMailVisible;
	}

	public void setStackMailVisible(boolean stackMailVisible) {
		this.stackMailVisible = stackMailVisible;
	}

	public boolean isMenuEditVisible() {
		return menuEditVisible;
	}

	public void setMenuEditVisible(boolean menuEditVisible) {
		this.menuEditVisible = menuEditVisible;
	}

	public boolean isMenuToolsVisible() {
		return menuToolsVisible;
	}

	public void setMenuToolsVisible(boolean menuToolsVisible) {
		this.menuToolsVisible = menuToolsVisible;
	}

	public boolean isMenuBookmarkVisible() {
		return menuBookmarkVisible;
	}

	public void setMenuBookmarkVisible(boolean menuBookmarkVisible) {
		this.menuBookmarkVisible = menuBookmarkVisible;
	}

	public boolean isMenuHelpVisible() {
		return menuHelpVisible;
	}

	public void setMenuHelpVisible(boolean menuHelpVisible) {
		this.menuHelpVisible = menuHelpVisible;
	}

	public boolean isTabDesktopVisible() {
		return tabDesktopVisible;
	}

	public void setTabDesktopVisible(boolean tabDesktopVisible) {
		this.tabDesktopVisible = tabDesktopVisible;
	}

	public boolean isTabSearchVisible() {
		return tabSearchVisible;
	}

	public void setTabSearchVisible(boolean tabSearchVisible) {
		this.tabSearchVisible = tabSearchVisible;
	}

	public boolean isTabDashboardVisible() {
		return tabDashboardVisible;
	}

	public void setTabDashboardVisible(boolean tabDashboardVisible) {
		this.tabDashboardVisible = tabDashboardVisible;
	}
	
	public boolean isDashboardUserVisible() {
		return dashboardUserVisible;
	}

	public void setDashboardUserVisible(boolean dashboardUserVisible) {
		this.dashboardUserVisible = dashboardUserVisible;
	}

	public boolean isDashboardMailVisible() {
		return dashboardMailVisible;
	}

	public void setDashboardMailVisible(boolean dashboardMailVisible) {
		this.dashboardMailVisible = dashboardMailVisible;
	}

	public boolean isDashboardNewsVisible() {
		return dashboardNewsVisible;
	}

	public void setDashboardNewsVisible(boolean dashboardNewsVisible) {
		this.dashboardNewsVisible = dashboardNewsVisible;
	}

	public boolean isDashboardGeneralVisible() {
		return dashboardGeneralVisible;
	}

	public void setDashboardGeneralVisible(boolean dashboardGeneralVisible) {
		this.dashboardGeneralVisible = dashboardGeneralVisible;
	}

	public boolean isDashboardWorkflowVisible() {
		return dashboardWorkflowVisible;
	}

	public void setDashboardWorkflowVisible(boolean dashboardWorkflowVisible) {
		this.dashboardWorkflowVisible = dashboardWorkflowVisible;
	}

	public boolean isDashboardKeywordsVisible() {
		return dashboardKeywordsVisible;
	}

	public void setDashboardKeywordsVisible(boolean dashboardKeywordsVisible) {
		this.dashboardKeywordsVisible = dashboardKeywordsVisible;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id="); sb.append(id);
		sb.append(", name="); sb.append(name);
		sb.append(", userQuotaSize="); sb.append(userQuotaSize);
		sb.append(", userQuotaEnabled="); sb.append(userQuotaEnabled);
		sb.append(", wizardPropertyGroups="); sb.append(wizardPropertyGroups);
		sb.append(", wizardKeywords="); sb.append(wizardKeywords);
		sb.append(", wizardCategories="); sb.append(wizardCategories);
		sb.append(", chatEnabled="); sb.append(chatEnabled);
		sb.append(", chatAutoLogin="); sb.append(chatAutoLogin);
		sb.append(", advancedFilters="); sb.append(advancedFilters);
		sb.append(", stackCategoriesVisible="); sb.append(stackCategoriesVisible);
		sb.append(", stackThesaurusVisible="); sb.append(stackThesaurusVisible);
		sb.append(", stackPersonalVisible="); sb.append(stackPersonalVisible);
		sb.append(", stackMailVisible="); sb.append(stackMailVisible);
		sb.append(", menuEditVisible="); sb.append(menuEditVisible);
		sb.append(", menuToolsVisible="); sb.append(menuToolsVisible);
		sb.append(", menuBookmarkVisible="); sb.append(menuBookmarkVisible);
		sb.append(", menuHelpVisible="); sb.append(menuHelpVisible);
		sb.append(", tabDesktopVisible="); sb.append(tabDesktopVisible);
		sb.append(", tabSearchVisible="); sb.append(tabSearchVisible);
		sb.append(", tabDashboardVisible="); sb.append(tabDashboardVisible);
		sb.append(", dashboardUserVisible="); sb.append(dashboardUserVisible);
		sb.append(", dashboardMailVisible="); sb.append(dashboardMailVisible);
		sb.append(", dashboardNewsVisible="); sb.append(dashboardNewsVisible);
		sb.append(", dashboardGeneralVisible="); sb.append(dashboardGeneralVisible);
		sb.append(", dashboardWorkflowVisible="); sb.append(dashboardWorkflowVisible);
		sb.append(", dashboardKeywordsVisible="); sb.append(dashboardKeywordsVisible);
		sb.append("}");
		return sb.toString();
	}
}
