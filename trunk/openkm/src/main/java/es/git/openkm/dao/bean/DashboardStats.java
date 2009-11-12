/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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

package es.git.openkm.dao.bean;

import java.util.Calendar;

public class DashboardStats {
	private String dsUser;
	private String dsSource;
	private String dsNode;
	private Calendar dsDate;
	
	public String getDsUser() {
		return dsUser;
	}

	public void setDsUser(String dsUser) {
		this.dsUser = dsUser;
	}

	public String getDsSource() {
		return dsSource;
	}

	public void setDsSource(String dsSource) {
		this.dsSource = dsSource;
	}

	public String getDsNode() {
		return dsNode;
	}

	public void setDsNode(String dsNode) {
		this.dsNode = dsNode;
	}

	public Calendar getDsDate() {
		return dsDate;
	}

	public void setDsDate(Calendar dsDate) {
		this.dsDate = dsDate;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("dsUser="); sb.append(dsUser);
		sb.append(", actSource="); sb.append(dsSource);
		sb.append(", dsNode="); sb.append(dsNode);
		sb.append(", dsDate="); sb.append(dsDate==null?null:dsDate.getTime());
		sb.append("]");
		return sb.toString();
	}
}
