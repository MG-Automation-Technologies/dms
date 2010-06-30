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
	private boolean active;
	private UserProfileChat chat = new UserProfileChat();
	private UserProfileDashboard dashboard = new UserProfileDashboard();
	private UserProfileMenu menu = new UserProfileMenu();
	private UserProfileMisc misc = new UserProfileMisc();
	private UserProfileStack stack = new UserProfileStack();
	private UserProfileTab tab = new UserProfileTab();
	private UserProfileWizard wizard = new UserProfileWizard();
	private UserProfileAction action = new UserProfileAction();

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public UserProfileChat getChat() {
		return chat;
	}

	public void setChat(UserProfileChat chat) {
		this.chat = chat;
	}

	public UserProfileDashboard getDashboard() {
		return dashboard;
	}

	public void setDashboard(UserProfileDashboard dashboard) {
		this.dashboard = dashboard;
	}

	public UserProfileMenu getMenu() {
		return menu;
	}

	public void setMenu(UserProfileMenu menu) {
		this.menu = menu;
	}

	public UserProfileMisc getMisc() {
		return misc;
	}

	public void setMisc(UserProfileMisc misc) {
		this.misc = misc;
	}

	public UserProfileStack getStack() {
		return stack;
	}

	public void setStack(UserProfileStack stack) {
		this.stack = stack;
	}

	public UserProfileTab getTab() {
		return tab;
	}

	public void setTab(UserProfileTab tab) {
		this.tab = tab;
	}

	public UserProfileAction getAction() {
		return action;
	}

	public void setAction(UserProfileAction action) {
		this.action = action;
	}

	public UserProfileWizard getWizard() {
		return wizard;
	}

	public void setWizard(UserProfileWizard wizard) {
		this.wizard = wizard;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id="); sb.append(id);
		sb.append(", name="); sb.append(name);
		sb.append(", active="); sb.append(active);
		sb.append(", chat="); sb.append(chat);
		sb.append(", dashboard="); sb.append(dashboard);
		sb.append(", menu="); sb.append(menu);
		sb.append(", misc="); sb.append(misc);
		sb.append(", stack="); sb.append(stack);
		sb.append(", tab="); sb.append(tab);
		sb.append(", wizard="); sb.append(wizard);
		sb.append(", action="); sb.append(action);
		sb.append("}");
		return sb.toString();
	}
}
