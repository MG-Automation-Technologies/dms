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

package com.openkm.frontend.client.bean;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * GWTWorkspace
 * 
 * @author jllort
 *
 */
public class GWTWorkspace implements IsSerializable {
	
	private String user = "";
	private boolean isAdmin = false;
	private String token = "";
	private String applicationURL = "";
	private String imapHost = "";
	private String imapUser = "";
	private String imapPassword = "";
	private String imapFolder = "";
	private int imapID = -1;
	private String password = "";
	private boolean changePassword = true;
	private String email = "";
	private String appVersion = "";
	private String workflowRunConfigForm = "";
	private String workflowProcessIntanceVariableUUID = "";
	private String workflowProcessIntanceVariablePath = "";
	private boolean wizardPropertyGroups = false;
	private boolean wizardKeywords = false;
	private boolean wizardCategories = false;
	private boolean chatEnabled = false;
	private boolean chatAutoLogin = false;
	private double keepAliveSchedule = 900*1000; 	// 15 minutes
	private double dashboardSchedule = 1800*1000; 	// 30 minutes
	private boolean advancedFilters = false;
	private boolean userQuotaLimit = false;
	private int userQuotaLimitSize = 0;
	private boolean categoriesStackVisible = false;
	private boolean thesaurusStackVisible = false;
	private boolean personalStackVisible = false;
	private boolean mailStackVisible = false;
	private GWTAvailableOption availableOption = new GWTAvailableOption();

	/**
	 * GWTWorkspace
	 */
	public GWTWorkspace() {
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getApplicationURL() {
		return applicationURL;
	}
	
	public void setApplicationURL(String applicationURL) {
		this.applicationURL = applicationURL;
	}
	
	public String getImapHost() {
		return imapHost;
	}

	public void setImapHost(String imapHost) {
		this.imapHost = imapHost;
	}

	public String getImapUser() {
		return imapUser;
	}

	public void setImapUser(String imapUser) {
		this.imapUser = imapUser;
	}

	public String getImapPassword() {
		return imapPassword;
	}

	public void setImapPassword(String imapPassword) {
		this.imapPassword = imapPassword;
	}
	
	public String getImapFolder() {
		return imapFolder;
	}

	public void setImapFolder(String imapFolder) {
		this.imapFolder = imapFolder;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isChangePassword() {
		return changePassword;
	}

	public void setChangePassword(boolean changePassword) {
		this.changePassword = changePassword;
	}
	
	public int getImapID() {
		return imapID;
	}

	public void setImapID(int imapID) {
		this.imapID = imapID;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAppVersion() {
		return appVersion;
	}
	
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
	public String getWorkflowRunConfigForm() {
		return workflowRunConfigForm;
	}

	public void setWorkflowRunConfigForm(String workflowRunConfigForm) {
		this.workflowRunConfigForm = workflowRunConfigForm;
	}
	
	public String getWorkflowProcessIntanceVariableUUID() {
		return workflowProcessIntanceVariableUUID;
	}

	public void setWorkflowProcessIntanceVariableUUID(String workflowProcessIntanceVariableUUID) {
		this.workflowProcessIntanceVariableUUID = workflowProcessIntanceVariableUUID;
	}

	public String getWorkflowProcessIntanceVariablePath() {
		return workflowProcessIntanceVariablePath;
	}

	public void setWorkflowProcessIntanceVariablePath(String workflowProcessIntanceVariablePath) {
		this.workflowProcessIntanceVariablePath = workflowProcessIntanceVariablePath;
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
	
	public boolean isChatAutoLogin() {
		return chatAutoLogin;
	}

	public void setChatAutoLogin(boolean chatAutoLogin) {
		this.chatAutoLogin = chatAutoLogin;
	}

	public boolean isChatEnabled() {
		return chatEnabled;
	}

	public void setChatEnabled(boolean chatEnabled) {
		this.chatEnabled = chatEnabled;
	}
	
	public double getDashboardSchedule() {
		return dashboardSchedule;
	}

	public void setDashboardSchedule(double dashboardSchedule) {
		this.dashboardSchedule = dashboardSchedule;
	}

	public double getKeepAliveSchedule() {
		return keepAliveSchedule;
	}

	public void setKeepAliveSchedule(double keepAliveSchedule) {
		this.keepAliveSchedule = keepAliveSchedule;
	}
	
	public boolean isAdvancedFilters() {
		return advancedFilters;
	}

	public void setAdvancedFilters(boolean advancedFilters) {
		this.advancedFilters = advancedFilters;
	}
	
	public boolean isUserQuotaLimit() {
		return userQuotaLimit;
	}

	public void setUserQuotaLimit(boolean userQuotaLimit) {
		this.userQuotaLimit = userQuotaLimit;
	}
	
	public int getUserQuotaLimitSize() {
		return userQuotaLimitSize;
	}

	public void setUserQuotaLimitSize(int userQuotaLimitSize) {
		this.userQuotaLimitSize = userQuotaLimitSize;
	}
	
	public boolean isCategoriesStackVisible() {
		return categoriesStackVisible;
	}

	public void setCategoriesStackVisible(boolean categoriesStackVisible) {
		this.categoriesStackVisible = categoriesStackVisible;
	}

	public boolean isThesaurusStackVisible() {
		return thesaurusStackVisible;
	}

	public void setThesaurusStackVisible(boolean thesaurusStackVisible) {
		this.thesaurusStackVisible = thesaurusStackVisible;
	}

	public boolean isPersonalStackVisible() {
		return personalStackVisible;
	}

	public void setPersonalStackVisible(boolean personalStackVisible) {
		this.personalStackVisible = personalStackVisible;
	}
	
	public boolean isMailStackVisible() {
		return mailStackVisible;
	}

	public void setMailStackVisible(boolean mailStackVisible) {
		this.mailStackVisible = mailStackVisible;
	}
	
	public GWTAvailableOption getAvailableOption() {
		return availableOption;
	}

	public void setAvailableOption(GWTAvailableOption availableOption) {
		this.availableOption = availableOption;
	}
}