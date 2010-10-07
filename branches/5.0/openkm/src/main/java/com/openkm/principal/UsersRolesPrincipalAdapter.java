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

package com.openkm.principal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

public class UsersRolesPrincipalAdapter implements PrincipalAdapter {
	private static Logger log = LoggerFactory.getLogger(UsersRolesPrincipalAdapter.class);
		
	@Override
	public List<String> getUsers() throws PrincipalAdapterException {
		log.debug("getUsers()");
		List<String> list = new ArrayList<String>();
		Properties prop = new Properties();
				
		try {
			prop.load(new FileInputStream(Config.HOME_DIR+"/server/default/conf/props/openkm-users.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Enumeration<Object> e = prop.keys(); e.hasMoreElements(); ) {
			String user = (String) e.nextElement();
			if (!Config.ADMIN_USER.equals(user) && !Config.SYSTEM_USER.equals(user)) {
				list.add(user);
			}
		}
		
		log.debug("getUsers: {}", list);
		return list;
	}

	@Override
	public List<String> getRoles() throws PrincipalAdapterException {
		log.debug("getRoles()");
		List<String> list = new ArrayList<String>();
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream(Config.HOME_DIR+"/server/default/conf/props/openkm-roles.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Enumeration<Object> e = prop.elements(); e.hasMoreElements(); ) {
			for (StringTokenizer st = new StringTokenizer((String) e.nextElement(), ","); st.hasMoreTokens(); ) {
				String role = st.nextToken();
				
				if (!Config.DEFAULT_ADMIN_ROLE.equals(role) && !list.contains(role)) {
					list.add(role);
				}
			}
		}
		
		log.debug("getRoles: {}", list);
		return list;
	}
	
	@Override
	public List<String> getUsersByRole(String role) throws PrincipalAdapterException {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	@Override
	public List<String> getRolesByUser(String user) throws PrincipalAdapterException {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public List<String> getMails(List<String> users) throws PrincipalAdapterException {
		log.debug("getMails()");
		List<String> list = new ArrayList<String>();
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream(Config.HOME_DIR+"/server/default/conf/props/openkm-emails.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Iterator<String> it = users.iterator(); it.hasNext(); ) {
			String userId = it.next();
			String email = prop.getProperty(userId);
			
			if (email != null) {
				list.add(email);
			}
		}
		
		log.debug("getMails: {}", list);
		return list;
	}
}
