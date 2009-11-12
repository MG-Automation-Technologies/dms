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

public class Activity {
	private Calendar actDate;
	private String actUser;
	private String actToken;
	private String actAction;
	private String actItem;
	private String actParams;
	
	public String getActAction() {
		return actAction;
	}
	
	public void setActAction(String actAction) {
		this.actAction = actAction;
	}
	
	public Calendar getActDate() {
		return actDate;
	}
	
	public void setActDate(Calendar actDate) {
		this.actDate = actDate;
	}
	
	public String getActParams() {
		return actParams;
	}
	
	public void setActParams(String actParams) {
		this.actParams = actParams;
	}
	
	public String getActToken() {
		return actToken;
	}
	
	public void setActToken(String actToken) {
		this.actToken = actToken;
	}
	
	public String getActUser() {
		return actUser;
	}
	
	public void setActUser(String actUser) {
		this.actUser = actUser;
	}

	public String getActItem() {
		return actItem;
	}

	public void setActItem(String actItem) {
		this.actItem = actItem;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("actDate="); sb.append(actDate==null?null:actDate.getTime());
		sb.append(", actUser="); sb.append(actUser);
		sb.append(", actToken="); sb.append(actToken);
		sb.append(", actAction="); sb.append(actAction);
		sb.append(", actItem="); sb.append(actItem);
		sb.append(", actParams="); sb.append(actParams);
		sb.append("]");
		return sb.toString();
	}
}
