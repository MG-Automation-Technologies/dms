/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2012 Paco Avila & Josep Llort
 * 
 * No bytes were intentionally harmed during the development of this application.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.module.db.stuff;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.dao.NodeBaseDAO;
import com.openkm.dao.bean.NodeBase;
import com.openkm.spring.PrincipalUtils;
import com.openkm.util.StackTraceUtils;

/**
 * Check user permissions on documents and folders.
 * 
 * @author pavila
 */
public class DbSimpleAccessManager implements DbAccessManager {
	private static Logger log = LoggerFactory.getLogger(DbSimpleAccessManager.class);
	public static final String NAME = "simple";
	
	public void checkPermission(NodeBase node, int permissions) throws AccessDeniedException, PathNotFoundException,
			DatabaseException {
		if (!isGranted(node, permissions)) {
			String nodePath = NodeBaseDAO.getInstance().getPathFromUuid(node.getUuid());
			throw new AccessDeniedException(node.getUuid() + " : " + nodePath);
		}
	}
	
	public boolean isGranted(NodeBase node, int permissions) {
		log.debug("isGranted({}, {})", node.getUuid(), permissions);
		boolean access = false;
		String user = PrincipalUtils.getUser();
		
		if (user != null) {
			if (Config.SYSTEM_USER.equals(user) || Config.ADMIN_USER.equals(user)) {
				// An okmAdmin user has total access
				access = true;
			} else {
				Set<String> roles = PrincipalUtils.getRoles();
				
				if (roles.contains(Config.DEFAULT_ADMIN_ROLE)) {
					// An user with AdminRole has total access
					access = true;
				} else {
					access = checkProperties(node.getUserPermissions(), node.getRolePermissions(), user, roles,
							permissions);
				}
			}
		} else {
			access = true;
			
			log.info("***************************");
			log.info("***************************");
			StackTraceUtils.logTrace(log);
			log.info("***************************");
			log.info("***************************");
		}
		
		log.debug("isGranted: {}", access);
		return access;
	}
	
	/**
	 * Check access properties
	 */
	private boolean checkProperties(Map<String, Integer> usersPerms, Map<String, Integer> rolesPerms, String user,
			Set<String> roles, int perms) {
		log.debug("checkProperties({}, {}, {}, {})", new Object[] { usersPerms, rolesPerms, roles, perms });
		boolean access = false;
		
		// Fist try with user permissions
		Integer userPerms = usersPerms.get(user);
		
		if (userPerms != null && (perms & userPerms) != 0) {
			log.debug("checkProperties: {}", true);
			return true;
		}
		
		// If there is no user specific access, try with roles permissions
		for (String role : roles) {
			Integer rolePerms = rolesPerms.get(role);
			
			if (rolePerms != null && (perms & rolePerms) != 0) {
				log.debug("checkProperties: {}", true);
				return true;
			}
		}
		
		log.debug("checkProperties: {}", access);
		return access;
	}
}
