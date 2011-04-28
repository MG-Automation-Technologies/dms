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

package es.git.openkm.bean.report.admin;

import java.io.Serializable;

/**
 * @author jllort
 *
 */
public class ReportSubscribedDocuments implements Serializable {

	private static final long serialVersionUID = -383463786318370922L;
	
	private String path = "";
	private String subscriptors = "";
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSubscriptors() {
		return subscriptors;
	}

	public void setSubscriptors(String subscriptors) {
		this.subscriptors = subscriptors;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("path="); sb.append(path);
		sb.append(", subscriptors="); sb.append(subscriptors);
		sb.append("}");
		return sb.toString();
	}
}