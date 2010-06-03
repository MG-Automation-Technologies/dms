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

import java.util.Calendar;

public class Activity {
	private int id;
	private Calendar date;
	private String user;
	private String token;
	private String action;
	private String item;
	private String params;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public String getParams() {
		return params;
	}
	
	public void setParams(String params) {
		this.params = params;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("date="); sb.append(date==null?null:date.getTime());
		sb.append(", user="); sb.append(user);
		sb.append(", token="); sb.append(token);
		sb.append(", action="); sb.append(action);
		sb.append(", item="); sb.append(item);
		sb.append(", params="); sb.append(params);
		sb.append("}");
		return sb.toString();
	}
}
