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

package com.openkm.ws.endpoint;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.jboss.annotation.security.SecurityDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.module.AuthModule;
import com.openkm.module.ModuleManager;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.ws.util.BytePair;
import com.openkm.ws.util.BytePairArray;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMAuth"
 * @web.servlet-mapping url-pattern="/OKMAuth"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@SecurityDomain("OpenKM")
public class OKMAuth {
	private static Logger log = LoggerFactory.getLogger(OKMAuth.class);
	
	@WebMethod
	public void login() throws RepositoryException, DatabaseException {
		log.debug("login()");
		AuthModule am = ModuleManager.getAuthModule();
		am.login();
		log.debug("login: void");
	}

	@WebMethod
	public void logout() throws RepositoryException, DatabaseException {
		log.debug("logout()");
		AuthModule am = ModuleManager.getAuthModule();
		am.logout();
		log.debug("logout: void");
	}

	@WebMethod
	public BytePairArray getGrantedRoles(@WebParam(name = "nodePath") String nodePath) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("getGrantedRoles({})", nodePath);
		AuthModule am = ModuleManager.getAuthModule();
		Map<String, Byte> hm = am.getGrantedRoles(nodePath);
		Set<String> keys = hm.keySet();
		BytePair[] tmp = new BytePair[keys.size()];
		int i=0;
		
		for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
			String key = it.next();
			BytePair p = new BytePair();
			p.setKey(key);
			p.setValue((Byte) hm.get(key));
			tmp[i++] = p;
		}
		
		BytePairArray uh = new BytePairArray();
		uh.setValue(tmp);
		log.debug("getGrantedRoles: {}", uh);
		return uh;
	}

	@WebMethod
	public BytePairArray getGrantedUsers(@WebParam(name = "nodePath") String nodePath) throws
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("getGrantedUsers({})", nodePath);
		AuthModule am = ModuleManager.getAuthModule();
		Map<String, Byte> hm = am.getGrantedUsers(nodePath);
		Set<String> keys = hm.keySet();
		BytePair[] tmp = new BytePair[keys.size()];
		int i=0;
		
		for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
			String key = it.next();
			BytePair p = new BytePair();
			p.setKey(key);
			p.setValue((Byte) hm.get(key));
			tmp[i++] = p;
		}
		
		BytePairArray uh = new BytePairArray();
		uh.setValue(tmp);
		log.debug("getGrantedUsers: {}", uh);
		return uh;
	}

	@WebMethod
	public String[] getRoles() throws PrincipalAdapterException {
		log.debug("getRoles()");
		AuthModule am = ModuleManager.getAuthModule();
		List<String> col = am.getRoles();
		String[] result = (String[]) col.toArray(new String[col.size()]);
		log.debug("getRoles: {}", result);
		return result;
	}

	@WebMethod
	public String[] getUsers() throws PrincipalAdapterException {
		log.debug("getUsers()");
		AuthModule am = ModuleManager.getAuthModule();
		List<String> col = am.getUsers();
		String[] result = (String[]) col.toArray(new String[col.size()]);
		log.debug("getUsers: {]", result);
		return result;
	}

	@WebMethod
	public void grantRole(@WebParam(name = "nodePath") String nodePath, 
			@WebParam(name = "role") String role, 
			@WebParam(name = "permissions") int permissions, 
			@WebParam(name = "recursive") boolean recursive) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("grantRole({}, {}, {}, {})", new Object[] { nodePath, role, permissions, recursive });
		AuthModule am = ModuleManager.getAuthModule();
		am.grantRole(nodePath, role, permissions, recursive); 
		log.debug("grantRole: void");
	}

	@WebMethod
	public void grantUser(@WebParam(name = "nodePath") String nodePath,
			@WebParam(name = "user") String user,
			@WebParam(name = "permissions") int permissions,
			@WebParam(name = "recursive") boolean recursive) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("grantUser({}, {}, {}, {})", new Object[] { nodePath, user, permissions, recursive });
		AuthModule am = ModuleManager.getAuthModule();
		am.grantUser(nodePath, user, permissions, recursive); 
		log.debug("grantUser: void");
	}

	@WebMethod
	public void revokeRole(@WebParam(name = "nodePath") String nodePath, 
			@WebParam(name = "user") String user,
			@WebParam(name = "permissions") int permissions,
			@WebParam(name = "recursive") boolean recursive) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("revokeRole({}, {}, {}, {})", new Object[] { nodePath, user, permissions, recursive });
		AuthModule am = ModuleManager.getAuthModule();
		am.revokeRole(nodePath, user, permissions, recursive); 
		log.debug("revokeRole: void");
	}

	@WebMethod
	public void revokeUser(@WebParam(name = "nodePath") String nodePath,
			@WebParam(name = "user") String user,
			@WebParam(name = "permissions") int permissions,
			@WebParam(name = "recursive") boolean recursive) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("revokeUser({}, {}, {}, {})", new Object[] { nodePath, user, permissions, recursive });
		AuthModule am = ModuleManager.getAuthModule();
		am.revokeUser(nodePath, user, permissions, recursive); 
		log.debug("revokeUser: void");
	}
}
