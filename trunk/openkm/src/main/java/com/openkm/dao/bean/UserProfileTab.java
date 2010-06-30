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

public class UserProfileTab implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean desktopVisible;
	private boolean searchVisible;
	private boolean dashboardVisible;

	public boolean isDesktopVisible() {
		return desktopVisible;
	}

	public void setDesktopVisible(boolean desktopVisible) {
		this.desktopVisible = desktopVisible;
	}

	public boolean isSearchVisible() {
		return searchVisible;
	}

	public void setSearchVisible(boolean searchVisible) {
		this.searchVisible = searchVisible;
	}

	public boolean isDashboardVisible() {
		return dashboardVisible;
	}

	public void setDashboardVisible(boolean dashboardVisible) {
		this.dashboardVisible = dashboardVisible;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("desktopVisible="); sb.append(desktopVisible);
		sb.append(", searchVisible="); sb.append(searchVisible);
		sb.append(", dashboardVisible="); sb.append(dashboardVisible);
		sb.append("}");
		return sb.toString();
	}
}
