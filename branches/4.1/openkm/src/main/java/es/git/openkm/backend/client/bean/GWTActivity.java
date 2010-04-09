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


package es.git.openkm.backend.client.bean;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * GWTActivity
 * 
 * @author jllort
 *
 */
public class GWTActivity implements IsSerializable {
	private Date actDate;
	private String actUser;
	private String actToken;
	private String actAction;
	private String actParams;
	
	public GWTActivity() {
	}
	
	public String getActAction() {
		return actAction;
	}
	
	public void setActAction(String actAction) {
		this.actAction = actAction;
	}
	
	public Date getActDate() {
		return actDate;
	}
	
	public void setActDate(Date actDate) {
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
}
