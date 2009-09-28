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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.dao.AuthDAO;
import es.git.openkm.dao.bean.Role;
import es.git.openkm.dao.bean.User;

public class DatabasePrincipalAdapter implements PrincipalAdapter {
	private static Logger log = LoggerFactory.getLogger(DatabasePrincipalAdapter.class);
	
	/* (non-Javadoc)
	 * @see es.git.openkm.principal.PrincipalAdapter#getUsers()
	 */
	public Collection<String> getUsers() throws PrincipalAdapterException {
		log.debug("getUsers()");
		ArrayList<String> list = new ArrayList<String>();

		try {
			Collection<User> col = AuthDAO.getInstance().findAllUsers();
			
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
	 * @see es.git.openkm.principal.PrincipalAdapter#getRoles()
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
	 * @see es.git.openkm.principal.PrincipalAdapter#getMails(java.util.Collection)
	 */
	public Collection<String> getMails(Collection<String> users) throws PrincipalAdapterException {
		log.debug("getMails()");
		ArrayList<String> list = new ArrayList<String>();

		try {
			for (Iterator<String> it = users.iterator(); it.hasNext(); ) {
				String userId = it.next();
				es.git.openkm.dao.bean.User user = AuthDAO.getInstance().findUserByPk(userId);
				if (!user.getEmail().equals("")) {
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
