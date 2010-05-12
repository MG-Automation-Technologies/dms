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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.bean.Role;
import com.openkm.dao.bean.User;

public class DatabasePrincipalAdapter implements PrincipalAdapter {
	private static Logger log = LoggerFactory.getLogger(DatabasePrincipalAdapter.class);
	
	/* (non-Javadoc)
	 * @see com.openkm.principal.PrincipalAdapter#getUsers()
	 */
	public Collection<String> getUsers() throws PrincipalAdapterException {
		log.debug("getUsers()");
		ArrayList<String> list = new ArrayList<String>();

		try {
			Collection<User> col = AuthDAO.getInstance().findAllUsers(Config.PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS);
			
			for (Iterator<User> it = col.iterator(); it.hasNext(); ) {
				User dbUser = it.next();
				list.add(dbUser.getId());
			}
		} catch (SQLException e) {
			throw new PrincipalAdapterException(e.getMessage(), e);
		}

		log.debug("getUsers: "+list);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.openkm.principal.PrincipalAdapter#getRoles()
	 */
	public Collection<String> getRoles() throws PrincipalAdapterException {
		log.debug("getRoles()");
		ArrayList<String> list = new ArrayList<String>();
				
		try {
			Collection<Role> col = AuthDAO.getInstance().findAllRoles();
			
			for (Iterator<Role> it = col.iterator(); it.hasNext(); ) {
				Role dbRole = it.next();
				list.add(dbRole.getId());
			}
		} catch (SQLException e) {
			throw new PrincipalAdapterException(e.getMessage(), e);
		}
		
		log.debug("getRoles: "+list);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.openkm.principal.PrincipalAdapter#getMails(java.util.Collection)
	 */
	public Collection<String> getMails(Collection<String> users) throws PrincipalAdapterException {
		log.debug("getMails()");
		ArrayList<String> list = new ArrayList<String>();

		try {
			for (Iterator<String> it = users.iterator(); it.hasNext(); ) {
				String userId = it.next();
				com.openkm.dao.bean.User user = AuthDAO.getInstance().findUserByPk(userId);
				if (user != null && !user.getEmail().equals("")) {
					list.add(user.getEmail());
				}
			}
		} catch (SQLException e) {
			throw new PrincipalAdapterException(e.getMessage(), e);
		}

		log.debug("getMails: "+list);
		return list;
	}
}
