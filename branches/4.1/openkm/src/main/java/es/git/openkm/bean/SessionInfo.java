/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package es.git.openkm.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.jcr.Session;

public class SessionInfo implements Serializable {
	private static final long serialVersionUID = 8655072216466952885L;
	
	public static final String CREATION = "creation";
	public static final String ACCESS = "access";
	
	private Calendar creation;
	private Calendar access;
	private Session session;
	
	public Calendar getAccess() {
		return access;
	}
	
	public void setAccess(Calendar access) {
		this.access = access;
	}
	
	public Calendar getCreation() {
		return creation;
	}
	
	public void setCreation(Calendar creation) {
		this.creation = creation;
	}
	
	public javax.jcr.Session getSession() {
		return session;
	}
	
	public void setSession(javax.jcr.Session session) {
		this.session = session;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("session="); sb.append(session);
		sb.append(", creation="); sb.append(creation==null?null:creation.getTime());
		sb.append(", access="); sb.append(access==null?null:access.getTime());
		sb.append("]");
		return sb.toString();
	}
}
