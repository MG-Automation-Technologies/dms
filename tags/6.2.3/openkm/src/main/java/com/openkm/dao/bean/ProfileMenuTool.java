/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2012  Paco Avila & Josep Llort
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

public class ProfileMenuTool implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean languagesVisible;
	private boolean skinVisible;
	private boolean debugVisible;
	private boolean administrationVisible;
	private boolean preferencesVisible;

	public boolean isLanguagesVisible() {
		return languagesVisible;
	}

	public void setLanguagesVisible(boolean languagesVisible) {
		this.languagesVisible = languagesVisible;
	}

	public boolean isSkinVisible() {
		return skinVisible;
	}

	public void setSkinVisible(boolean skinVisible) {
		this.skinVisible = skinVisible;
	}

	public boolean isDebugVisible() {
		return debugVisible;
	}

	public void setDebugVisible(boolean debugVisible) {
		this.debugVisible = debugVisible;
	}

	public boolean isAdministrationVisible() {
		return administrationVisible;
	}

	public void setAdministrationVisible(boolean administrationVisible) {
		this.administrationVisible = administrationVisible;
	}

	public boolean isPreferencesVisible() {
		return preferencesVisible;
	}

	public void setPreferencesVisible(boolean preferencesVisible) {
		this.preferencesVisible = preferencesVisible;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("languagesVisible="); sb.append(languagesVisible);
		sb.append(", skinVisible="); sb.append(skinVisible);
		sb.append(", debugVisible="); sb.append(debugVisible);
		sb.append(", administrationVisible="); sb.append(administrationVisible);
		sb.append(", preferencesVisible="); sb.append(preferencesVisible);
		sb.append("}");
		return sb.toString();
	}
}
