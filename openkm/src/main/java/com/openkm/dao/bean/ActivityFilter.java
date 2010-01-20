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

package com.openkm.dao.bean;

import java.util.Calendar;

public class ActivityFilter {
	private Calendar actDateBegin;
	private Calendar actDateEnd;
	private String actUser;
	private String actAction;
	
	public Calendar getActDateBegin() {
		return actDateBegin;
	}
	
	public void setActDateBegin(Calendar actDateBegin) {
		this.actDateBegin = actDateBegin;
	}
	
	public Calendar getActDateEnd() {
		return actDateEnd;
	}
	
	public void setActDateEnd(Calendar actDateEnd) {
		this.actDateEnd = actDateEnd;
	}
	
	public String getActUser() {
		return actUser;
	}
	
	public void setActUser(String actUser) {
		this.actUser = actUser;
	}

	public String getActAction() {
		return actAction;
	}

	public void setActAction(String actAction) {
		this.actAction = actAction;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("actDateBegin="); sb.append(actDateBegin==null?null:actDateBegin.getTime());
		sb.append(", actDateEnd="); sb.append(actDateEnd==null?null:actDateEnd.getTime());
		sb.append(", actUser="); sb.append(actUser);
		sb.append(", actAction="); sb.append(actAction);
		sb.append("]");
		return sb.toString();
	}
}
