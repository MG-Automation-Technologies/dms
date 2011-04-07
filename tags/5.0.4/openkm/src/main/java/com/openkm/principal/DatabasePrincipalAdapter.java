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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.bean.Role;
import com.openkm.dao.bean.User;

public class DatabasePrincipalAdapter implements PrincipalAdapter {
	private static Logger log = LoggerFactory.getLogger(DatabasePrincipalAdapter.class);
	
	@Override
	public List<String> getUsers() throws PrincipalAdapterException {
		log.debug("getUsers()");
		List<String> list = new ArrayList<String>();

		try {
			List<User> col = AuthDAO.findAllUsers(Config.PRINCIPAL_DATABASE_FILTER_INACTIVE_USERS);
			
			for (Iterator<User> it = col.iterator(); it.hasNext(); ) {
				User dbUser = it.next();
				list.add(dbUser.getId());
			}
		} catch (DatabaseException e) {
			throw new PrincipalAdapterException(e.getMessage(), e);
		}

		log.debug("getUsers: {}", list);
		return list;
	}

	@Override
	public List<String> getRoles() throws PrincipalAdapterException {
		log.debug("getRoles()");
		List<String> list = new ArrayList<String>();
				
		try {
			List<Role> col = AuthDAO.findAllRoles();
			
			for (Iterator<Role> it = col.iterator(); it.hasNext(); ) {
				Role dbRole = it.next();
				list.add(dbRole.getId());
			}
		} catch (DatabaseException e) {
			throw new PrincipalAdapterException(e.getMessage(), e);
		}
		
		log.debug("getRoles: {}", list);
		return list;
	}
	
	@Override
	public List<String> getUsersByRole(String role) throws PrincipalAdapterException {
		log.debug("getUsersByRole({})", role);
		List<String> list = new ArrayList<String>();
				
		try {
			List<User> col = AuthDAO.findUsersByRole(role, true);
			
			for (Iterator<User> it = col.iterator(); it.hasNext(); ) {
				User dbUser = it.next();
				list.add(dbUser.getId());
			}
		} catch (DatabaseException e) {
			throw new PrincipalAdapterException(e.getMessage(), e);
		}
		
		log.debug("getUsersByRole: {}", list);
		return list;
	}
	
	@Override
	public List<String> getRolesByUser(String user) throws PrincipalAdapterException {
		log.debug("getRolesByUser({})", user);
		List<String> list = new ArrayList<String>();
				
		try {
			List<Role> col = AuthDAO.findRolesByUser(user, true);
			
			for (Iterator<Role> it = col.iterator(); it.hasNext(); ) {
				Role dbRole = it.next();
				list.add(dbRole.getId());
			}
		} catch (DatabaseException e) {
			throw new PrincipalAdapterException(e.getMessage(), e);
		}
		
		log.debug("getRolesByUser: {}", list);
		return list;
	}

	@Override
	public List<String> getMails(List<String> users) throws PrincipalAdapterException {
		log.debug("getMails()");
		List<String> list = new ArrayList<String>();

		try {
			for (Iterator<String> it = users.iterator(); it.hasNext(); ) {
				String userId = it.next();
				com.openkm.dao.bean.User user = AuthDAO.findUserByPk(userId);
				if (user != null && !user.getEmail().equals("")) {
					list.add(user.getEmail());
				}
			}
		} catch (DatabaseException e) {
			throw new PrincipalAdapterException(e.getMessage(), e);
		}

		log.debug("getMails: {}", list);
		return list;
	}
}
