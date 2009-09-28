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

package es.git.openkm.principal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.core.Config;

public class UsersRolesPrincipalAdapter implements PrincipalAdapter {
	private static Logger log = LoggerFactory.getLogger(UsersRolesPrincipalAdapter.class);
		
	/* (non-Javadoc)
	 * @see es.git.openkm.principal.PrincipalAdapter#getUsers()
	 */
	public Collection<String> getUsers() throws PrincipalAdapterException {
		log.debug("getUsers()");
		ArrayList<String> list = new ArrayList<String>();
		Properties prop = new Properties();
				
		try {
			prop.load(new FileInputStream(Config.JBOSS_HOME+"/server/default/conf/props/openkm-users.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Enumeration<Object> e = prop.keys(); e.hasMoreElements(); ) {
			String user = (String) e.nextElement();
			if (!Config.ADMIN_USER.equals(user) && !Config.SYSTEM_USER.equals(user)) {
				list.add(user);
			}
		}
		
		log.debug("getUsers: "+list);
		return list;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.principal.PrincipalAdapter#getRoles()
	 */
	public Collection<String> getRoles() throws PrincipalAdapterException {
		log.debug("getRoles()");
		ArrayList<String> list = new ArrayList<String>();
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream(Config.JBOSS_HOME+"/server/default/conf/props/openkm-roles.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Enumeration<Object> e = prop.elements(); e.hasMoreElements(); ) {
			for (StringTokenizer st = new StringTokenizer((String) e.nextElement(), ","); st.hasMoreTokens(); ) {
				String role = st.nextToken();
				
				if (!Config.DEFAULT_ADMIN_ROLE.equals(role)) {
					if (!list.contains(role)) {
						list.add(role);
					}
				}
			}
		}
		
		log.debug("getRoles: "+list);
		return list;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.principal.PrincipalAdapter#getMails(java.util.Collection)
	 */
	public Collection<String> getMails(Collection<String> users) throws PrincipalAdapterException {
		log.debug("getMails()");
		ArrayList<String> list = new ArrayList<String>();
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream(Config.JBOSS_HOME+"/server/default/conf/props/openkm-emails.properties"));
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
		
		log.debug("getMails: "+list);
		return list;
	}
}
