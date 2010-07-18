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
	public String login(@WebParam(name = "user") String user,
			@WebParam(name = "password") String password) throws AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("login({}, {})", user, password);
		AuthModule am = ModuleManager.getAuthModule();
		String token = am.login(user, password);
		log.debug("login: {}", token);
		return token;
	}

	@WebMethod
	public void logout(@WebParam(name = "token") String token) throws RepositoryException, 
			DatabaseException {
		log.debug("logout({})", token);
		AuthModule am = ModuleManager.getAuthModule();
		am.logout(token);
		log.debug("logout: void");
	}

	@WebMethod
	public BytePair[] getGrantedRoles(@WebParam(name = "token") String token,
			@WebParam(name = "nodePath") String nodePath) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("getGrantedRoles({}, {})", token, nodePath);
		AuthModule am = ModuleManager.getAuthModule();
		Map<String, Byte> hm = am.getGrantedRoles(token, nodePath);
		Set<String> keys = hm.keySet();
		BytePair[] result = new BytePair[keys.size()];
		int i=0;
		
		for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
			String key = it.next();
			BytePair p = new BytePair();
			p.setKey(key);
			p.setValue((Byte) hm.get(key));
			result[i++] = p;
		}
				
		log.debug("getGrantedRoles: {}", result);
		return result;
	}

	@WebMethod
	public BytePair[] getGrantedUsers(@WebParam(name = "token") String token,
			@WebParam(name = "nodePath") String nodePath) throws
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("getGrantedUsers({}, {})", token, nodePath);
		AuthModule am = ModuleManager.getAuthModule();
		Map<String, Byte> hm = am.getGrantedUsers(token, nodePath);
		Set<String> keys = hm.keySet();
		BytePair[] result = new BytePair[keys.size()];
		int i=0;
		
		for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
			String key = it.next();
			BytePair p = new BytePair();
			p.setKey(key);
			p.setValue((Byte) hm.get(key));
			result[i++] = p;
		}
		
		log.debug("getGrantedUsers: {}", result);
		return result;
	}

	@WebMethod
	public String[] getRoles(@WebParam(name = "token") String token) throws PrincipalAdapterException {
		log.debug("getRoles({})", token);
		AuthModule am = ModuleManager.getAuthModule();
		List<String> col = am.getRoles(token);
		String[] result = (String[]) col.toArray(new String[col.size()]);
		log.debug("getRoles: {}", result);
		return result;
	}

	@WebMethod
	public String[] getUsers(@WebParam(name = "token") String token) throws PrincipalAdapterException {
		log.debug("getUsers({})", token);
		AuthModule am = ModuleManager.getAuthModule();
		List<String> col = am.getUsers(token);
		String[] result = (String[]) col.toArray(new String[col.size()]);
		log.debug("getUsers: {]", result);
		return result;
	}

	@WebMethod
	public void grantRole(@WebParam(name = "token") String token,
			@WebParam(name = "nodePath") String nodePath, 
			@WebParam(name = "role") String role, 
			@WebParam(name = "permissions") int permissions, 
			@WebParam(name = "recursive") boolean recursive) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("grantRole({}, {}, {}, {}, {})", new Object[] { token, nodePath, role, permissions, recursive });
		AuthModule am = ModuleManager.getAuthModule();
		am.grantRole(token, nodePath, role, permissions, recursive); 
		log.debug("grantRole: void");
	}

	@WebMethod
	public void grantUser(@WebParam(name = "token") String token,
			@WebParam(name = "nodePath") String nodePath,
			@WebParam(name = "user") String user,
			@WebParam(name = "permissions") int permissions,
			@WebParam(name = "recursive") boolean recursive) throws PathNotFoundException, 
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("grantUser({}, {}, {}, {}, {})", new Object[] { token, nodePath, user, permissions, recursive });
		AuthModule am = ModuleManager.getAuthModule();
		am.grantUser(token, nodePath, user, permissions, recursive); 
		log.debug("grantUser: void");
	}

	@WebMethod
	public void revokeRole(@WebParam(name = "token") String token,
			@WebParam(name = "nodePath") String nodePath, 
			@WebParam(name = "user") String user,
			@WebParam(name = "permissions") int permissions,
			@WebParam(name = "recursive") boolean recursive) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("revokeRole({}, {}, {}, {}, {})", new Object[] { token, nodePath, user, permissions, recursive });
		AuthModule am = ModuleManager.getAuthModule();
		am.revokeRole(token, nodePath, user, permissions, recursive); 
		log.debug("revokeRole: void");
	}

	@WebMethod
	public void revokeUser(@WebParam(name = "token") String token,
			@WebParam(name = "nodePath") String nodePath,
			@WebParam(name = "user") String user,
			@WebParam(name = "permissions") int permissions,
			@WebParam(name = "recursive") boolean recursive) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("revokeUser({}, {}, {}, {}, {})", new Object[] { token, nodePath, user, permissions, recursive });
		AuthModule am = ModuleManager.getAuthModule();
		am.revokeUser(token, nodePath, user, permissions, recursive); 
		log.debug("revokeUser: void");
	}
}
