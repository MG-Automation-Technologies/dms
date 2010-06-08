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

package com.openkm.module.direct;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Folder;
import com.openkm.bean.Permission;
import com.openkm.bean.Repository;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.SessionManager;
import com.openkm.core.UserAlreadyLoggerException;
import com.openkm.dao.LockTokenDAO;
import com.openkm.dao.bean.LockToken;
import com.openkm.module.AuthModule;
import com.openkm.principal.PrincipalAdapter;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.util.JCRUtils;
import com.openkm.util.UUIDGenerator;
import com.openkm.util.UserActivity;

public class DirectAuthModule implements AuthModule {
	private static Logger log = LoggerFactory.getLogger(DirectAuthModule.class);
	private static PrincipalAdapter principalAdapter = null;
	
	@Override
	@SuppressWarnings("unchecked")
	public synchronized String login(String user, String pass) throws AccessDeniedException,
			UserAlreadyLoggerException, RepositoryException, DatabaseException {
		log.debug("login({}, {})", user, pass);
		String token = null;

		try {
			final javax.jcr.Repository r = DirectRepositoryModule.getRepository();
			Session session = null;
			
			if (Config.SESSION_MANAGER) {
				// If user and pass are null, there is an external authentication system
				// using JAAS.
				if (user != null && pass != null) {
					session = r.login(new SimpleCredentials(user, pass.toCharArray()));
				} else {
					InitialContext ctx = new InitialContext();
					Subject subject = (Subject) ctx.lookup("java:comp/env/security/subject");
					log.info("Principals: {}", subject.getPrincipals());

					Object obj = Subject.doAs(subject, new PrivilegedAction() {
						public Object run() {
							Session s = null;
							
							try {
								s = r.login();
							} catch (LoginException e) {
								return e;
							} catch (javax.jcr.RepositoryException e) {
								return new LoginException(e);
							}
							
							return s;
						}
					});
					
					try {
						session = (Session) obj;
					} catch (ClassCastException cce) {
						throw (LoginException) obj;
					}
				}
				
				// Check for a previous session
				SessionManager sessions = SessionManager.getInstance();
				String oldToken = sessions.getTokenByUserId(session.getUserID());
				
				if (oldToken != null) {
					Session oldSession = sessions.getInfo(oldToken).getSession();
					
					if (oldSession.isLive()) {
						session.logout();
						throw new UserAlreadyLoggerException("User already logged");
					} else {
						logout(oldToken);
					}
				} else {
					// Add generated session to pool
					token = UUIDGenerator.generate(this);
					log.info("Add generated session to pool: {}", token);
					sessions.put(token, session);
					loadUserData(session);
				}
			}
			
			// Activity log
			if (session != null) {
				UserActivity.log(session.getUserID(), "LOGIN", null, null);
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("login: {}", token);
		return token;
	}
	
	/**
	 * Load user data
	 */
	public static void loadUserData(Session session) throws DatabaseException, javax.jcr.RepositoryException {
		log.debug("loadUserData({}) -> {}", session.getUserID(), session);
		
		synchronized (session.getUserID()) {
			if (!session.itemExists("/"+Repository.TRASH+"/"+session.getUserID())) {
				log.info("Create okm:trash/{}", session.getUserID());
				Node okmTrash = session.getRootNode().getNode(Repository.TRASH);
				createBase(session, okmTrash);
				okmTrash.save();
			}
			
			if (!session.itemExists("/"+Repository.PERSONAL+"/"+session.getUserID())) {
				log.info("Create okm:personal/{}", session.getUserID());
				Node okmPersonal = session.getRootNode().getNode(Repository.PERSONAL);
				createBase(session, okmPersonal);
				okmPersonal.save();
			}
			
			if (!session.itemExists("/"+Repository.MAIL+"/"+session.getUserID())) {
				log.info("Create okm:mail/{}", session.getUserID());
				Node okmMail = session.getRootNode().getNode(Repository.MAIL);
				createBase(session, okmMail);
				okmMail.save();
			}
			
			List<LockToken> ltList = LockTokenDAO.findByUser(session.getUserID());
			
			for (Iterator<LockToken> it = ltList.iterator(); it.hasNext(); ) {
				LockToken lt = it.next();
				session.addLockToken(lt.getToken());
			}
		}
		
		log.debug("loadUserData: void");
	}

	/**
	 * Create base node
	 */
	private static Node createBase(Session session, Node root) throws 
			javax.jcr.RepositoryException {
		Node base = root.addNode(session.getUserID(), Folder.TYPE);

		// Add basic properties
		base.setProperty(Folder.AUTHOR, session.getUserID());
		base.setProperty(Folder.NAME, session.getUserID());

		// Auth info
		base.setProperty(Permission.USERS_READ, new String[] { session.getUserID() });
		base.setProperty(Permission.USERS_WRITE, new String[] { session.getUserID() });
		base.setProperty(Permission.USERS_DELETE, new String[] { session.getUserID() });
		base.setProperty(Permission.USERS_SECURITY, new String[] { session.getUserID() });
		base.setProperty(Permission.ROLES_READ, new String[] { Config.DEFAULT_USER_ROLE });
		base.setProperty(Permission.ROLES_WRITE, new String[] { Config.DEFAULT_USER_ROLE });
		base.setProperty(Permission.ROLES_DELETE, new String[] { Config.DEFAULT_USER_ROLE });
		base.setProperty(Permission.ROLES_SECURITY, new String[] { Config.DEFAULT_USER_ROLE });
		
		return base;
	}

	@Override
	public String login() throws AccessDeniedException, UserAlreadyLoggerException, RepositoryException,
			DatabaseException {
		log.debug("login()");
		String token = login(null, null);
		log.debug("login: {}", token);
		return token;
	}

	@Override
	public synchronized void logout(String token) throws AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("logout({})", token);
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			if (Config.SESSION_MANAGER) {
				if (session.isLive()) {
					// Activity log
					UserActivity.log(session.getUserID(), "LOGOUT", null, null);
					
					SessionManager.getInstance().remove(token);
					session.logout();
				}
			} else {
				// Activity log
				if (session != null && session.isLive()) {
					UserActivity.log(session.getUserID(), "LOGOUT", null, null);
				}
			}
		} catch (javax.jcr.PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
		
		log.debug("logout: void");
	}

	@Override
	public void grantUser(String token, String nodePath, String user, int permissions,
			boolean recursive) throws PathNotFoundException, AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("grantUser({}, {}, {}, {}, {})", new Object[] { token, nodePath, permissions, recursive });
		Node node = null;
		Session session = null;
		
		// TODO: Comprobar si el usuario es dueño del nodo.
		// O a lo mejor hacer esta comprobación en el OKMAccessManager.
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			node = session.getRootNode().getNode(nodePath.substring(1));
			String property = null;

			if (permissions == Permission.READ) {
				property = Permission.USERS_READ;
			} else if (permissions == Permission.WRITE) {
				property = Permission.USERS_WRITE;
			} else if (permissions == Permission.DELETE) {
				property = Permission.USERS_DELETE;
			} else if (permissions == Permission.SECURITY) {
				property = Permission.USERS_SECURITY;
			}

			synchronized (node) {
				if (recursive) {
					grantUserInDepth(node, user, property);
				} else {
					grantUser(node, user, property);
				}
			}

			// Activity log
			UserActivity.log(session.getUserID(), "GRANT_USER", nodePath, user+", "+permissions);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(node);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(node);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(node);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("grantUser: void");
	}

	/**
	 * Grant user
	 */
	private void grantUser(Node node, String user, String property) throws ValueFormatException,
			PathNotFoundException, javax.jcr.RepositoryException {
		Value[] actualUsers = node.getProperty(property).getValues();
		ArrayList<String> newUsers = new ArrayList<String>();
		
		for (int i=0; i<actualUsers.length; i++) {
			newUsers.add(actualUsers[i].getString());
		}

		// If the user isn't already granted add him
		if (!newUsers.contains(user)) {
			newUsers.add(user);
		}

		try {
			node.setProperty(property, (String[])newUsers.toArray(new String[newUsers.size()]));
			node.save();
		} catch (javax.jcr.lock.LockException e) {
			log.warn("grantUser -> LockException : {}", node.getPath());
			JCRUtils.discardsPendingChanges(node);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn("grantUser -> AccessDeniedException : {}", node.getPath());
			JCRUtils.discardsPendingChanges(node);
		}
	}

	/**
	 * Grant user recursively
	 */
	private void grantUserInDepth(Node node, String user, String property) throws ValueFormatException,
			PathNotFoundException, javax.jcr.RepositoryException {
		grantUser(node, user, property);

		if (node.isNodeType(Folder.TYPE)) {
			for (NodeIterator it = node.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				grantUserInDepth(child, user, property);
			}
		}
	}

	@Override
	public void revokeUser(String token, String nodePath, String user, int permissions,
			boolean recursive) throws PathNotFoundException, AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("revokeUser({}, {}, {}, {}, {})", new Object[] { token, nodePath, user, permissions, recursive });
		Node node = null;
		Session session = null;

		// TODO: Comprobar si el usuario es dueño del nodo.
		// O a lo mejor hacer esta comprobación en el OKMAccessManager.
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			node = session.getRootNode().getNode(nodePath.substring(1));
			String property = null;

			if (permissions == Permission.READ) {
				property = Permission.USERS_READ;
			} else if (permissions == Permission.WRITE) {
				property = Permission.USERS_WRITE;
			} else if (permissions == Permission.DELETE) {
				property = Permission.USERS_DELETE;
			} else if (permissions == Permission.SECURITY) {
				property = Permission.USERS_SECURITY;
			} 

			synchronized (node) {
				if (recursive) {
					revokeUserInDepth(node, user, property);
				} else {
					revokeUser(node, user, property);
				}
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "REVOKE_USER", nodePath, user+", "+permissions);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(node);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(node);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(node);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("revokeUser: void");
	}

	/**
	 * Revoke user
	 */
	private void revokeUser(Node node, String user, String property) throws ValueFormatException,
			PathNotFoundException, javax.jcr.RepositoryException {
		Value[] actualUsers = node.getProperty(property).getValues();
		List<String> newUsers = new ArrayList<String>();

		for (int i=0; i<actualUsers.length; i++) {
			if (!actualUsers[i].getString().equals(user)) {
				newUsers.add(actualUsers[i].getString());
			}
		}

		try {
			node.setProperty(property, (String[])newUsers.toArray(new String[newUsers.size()]));
			node.save();
		} catch (javax.jcr.lock.LockException e) {
			log.warn("revokeUser -> LockException : "+node.getPath());
			JCRUtils.discardsPendingChanges(node);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn("revokeUser -> AccessDeniedException : "+node.getPath());
			JCRUtils.discardsPendingChanges(node);
		}
	}

	/**
	 * Revoke user recursively
	 */
	private void revokeUserInDepth(Node node, String user, String property) throws ValueFormatException,
			PathNotFoundException, javax.jcr.RepositoryException {
		revokeUser(node, user, property);

		if (node.isNodeType(Folder.TYPE)) {
			for (NodeIterator it = node.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				revokeUserInDepth(child, user, property);
			}
		}
	}

	@Override
	public void grantRole(String token, String nodePath, String role, int permissions, 
			boolean recursive) throws PathNotFoundException, AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("grantRole({}, {}, {}, {}, {})", new Object[] { token, nodePath, role, permissions, recursive });
		Node node = null;
		Session session = null;
		
		// TODO: Comprobar si el usuario es dueño del nodo.
		// O a lo mejor hacer esta comprobación en el OKMAccessManager.
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			node = session.getRootNode().getNode(nodePath.substring(1));
			String property = null;

			if (permissions == Permission.READ) {
				property = Permission.ROLES_READ;
			} else if (permissions == Permission.WRITE) {
				property = Permission.ROLES_WRITE;
			} else if (permissions == Permission.DELETE) {
				property = Permission.ROLES_DELETE;
			} else if (permissions == Permission.SECURITY) {
				property = Permission.ROLES_SECURITY;
			}

			synchronized (node) {
				if (recursive) {
					grantRoleInDepth(node, role, property);
				} else {
					grantRole(node, role, property);
				}
			}

			// Activity log
			UserActivity.log(session.getUserID(), "GRANT_ROLE", nodePath, role+", "+permissions);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(node);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(node);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(node);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("grantRole: void");
	}

	/**
	 * Grant role
	 */
	private void grantRole(Node node, String role, String property) throws ValueFormatException,
			PathNotFoundException, javax.jcr.RepositoryException {
		Value[] actualRoles = node.getProperty(property).getValues();
		List<String> newRoles = new ArrayList<String>();

		for (int i=0; i<actualRoles.length; i++) {
			newRoles.add(actualRoles[i].getString());
		}

		// If the role isn't already granted add him
		if (!newRoles.contains(role)) {
			newRoles.add(role);
		}

		try {
			node.setProperty(property, (String[])newRoles.toArray(new String[newRoles.size()]));
			node.save();
		} catch (javax.jcr.lock.LockException e) {
			log.warn("grantRole -> LockException : {}", node.getPath());
			JCRUtils.discardsPendingChanges(node);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn("grantRole -> AccessDeniedException : {}", node.getPath());
			JCRUtils.discardsPendingChanges(node);
		}
	}

	/**
	 * Grant role recursively
	 */
	private void grantRoleInDepth(Node node, String role, String property) throws ValueFormatException,
			PathNotFoundException, javax.jcr.RepositoryException {
		grantRole(node, role, property);

		if (node.isNodeType(Folder.TYPE)) {
			for (NodeIterator it = node.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				grantRoleInDepth(child, role, property);
			}
		}
	}

	@Override
	public void revokeRole(String token, String nodePath, String role, int permissions, 
			boolean recursive) throws PathNotFoundException, AccessDeniedException, RepositoryException,
			DatabaseException {
		log.debug("revokeRole({}, {}, {}, {}, {})", new Object[] { token, nodePath, role, permissions, recursive });
		Node node = null;
		Session session = null;
		
		// TODO: Comprobar si el usuario es dueño del nodo.
		// O a lo mejor hacer esta comprobación en el OKMAccessManager.
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			node = session.getRootNode().getNode(nodePath.substring(1));
			String property = null;

			if (permissions == Permission.READ) {
				property = Permission.ROLES_READ;
			} else if (permissions == Permission.WRITE) {
				property = Permission.ROLES_WRITE;
			} else if (permissions == Permission.DELETE) {
				property = Permission.ROLES_DELETE;
			} else if (permissions == Permission.SECURITY) {
				property = Permission.ROLES_SECURITY;
			}

			synchronized (node) {
				if (recursive) {
					revokeRoleInDepth(node, role, property);
				} else {
					revokeRole(node, role, property);
				}
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "REVOKE_ROLE", nodePath, role+", "+permissions);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(node);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(node);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(node);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("revokeRole: void");
	}

	/**
	 * Revoke role
	 */
	private void revokeRole(Node node, String role, String property) throws ValueFormatException,
			PathNotFoundException, javax.jcr.RepositoryException {
		Value[] actualRoles = node.getProperty(property).getValues();
		List<String> newRoles = new ArrayList<String>();

		for (int i=0; i<actualRoles.length; i++) {
			if (!actualRoles[i].getString().equals(role)) {
				newRoles.add(actualRoles[i].getString());
			}
		}

		try {
			node.setProperty(property, (String[])newRoles.toArray(new String[newRoles.size()]));
			node.save();
		} catch (javax.jcr.lock.LockException e) {
			log.warn("revokeRole -> LockException : "+node.getPath());
			JCRUtils.discardsPendingChanges(node);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn("revokeRole -> AccessDeniedException : "+node.getPath());
			JCRUtils.discardsPendingChanges(node);
		}
	}

	/**
	 * Revoke role recursively
	 */
	private void revokeRoleInDepth(Node node, String role, String property) throws ValueFormatException, 
			PathNotFoundException, javax.jcr.RepositoryException {
		revokeRole(node, role, property);

		if (node.isNodeType(Folder.TYPE)) {
			for (NodeIterator it = node.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				revokeRoleInDepth(child, role, property);
			}
		}
	}

	@Override
	public HashMap<String, Byte> getGrantedUsers(String token, String nodePath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("getGrantedUsers({}, {})", token, nodePath);
		HashMap<String, Byte> users = new HashMap<String, Byte>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			Node node = session.getRootNode().getNode(nodePath.substring(1));
			Value[] usersRead = node.getProperty(Permission.USERS_READ).getValues();

			for (int i=0; i<usersRead.length; i++) {
				users.put(usersRead[i].getString(), new Byte(Permission.READ));
			}

			Value[] usersWrite = node.getProperty(Permission.USERS_WRITE).getValues();

			for (int i=0; i<usersWrite.length; i++) {
				Byte previous = (Byte) users.get(usersWrite[i].getString());

				if (previous != null) {
					users.put(usersWrite[i].getString(), new Byte((byte) (previous.byteValue() | Permission.WRITE)));
				} else {
					users.put(usersWrite[i].getString(), new Byte(Permission.WRITE));
				}
			}
			
			Value[] usersDelete = node.getProperty(Permission.USERS_DELETE).getValues();

			for (int i=0; i<usersDelete.length; i++) {
				Byte previous = (Byte) users.get(usersDelete[i].getString());

				if (previous != null) {
					users.put(usersDelete[i].getString(), new Byte((byte) (previous.byteValue() | Permission.DELETE)));
				} else {
					users.put(usersDelete[i].getString(), new Byte(Permission.DELETE));
				}
			}
			
			Value[] usersSecurity = node.getProperty(Permission.USERS_SECURITY).getValues();

			for (int i=0; i<usersSecurity.length; i++) {
				Byte previous = (Byte) users.get(usersSecurity[i].getString());

				if (previous != null) {
					users.put(usersSecurity[i].getString(), new Byte((byte) (previous.byteValue() | Permission.SECURITY)));
				} else {
					users.put(usersSecurity[i].getString(), new Byte(Permission.SECURITY));
				}
			}
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getGrantedUsers: {}", users);
		return users;
	}

	@Override
	public Map<String, Byte> getGrantedRoles(String token, String nodePath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("getGrantedRoles({}, {})", token, nodePath);
		Map<String, Byte> roles = new HashMap<String, Byte>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			Node node = session.getRootNode().getNode(nodePath.substring(1));
			Value[] rolesRead = node.getProperty(Permission.ROLES_READ).getValues();

			for (int i=0; i<rolesRead.length; i++) {
				roles.put(rolesRead[i].getString(), new Byte(Permission.READ));
			}

			Value[] rolesWrite = node.getProperty(Permission.ROLES_WRITE).getValues();

			for (int i=0; i<rolesWrite.length; i++) {
				Byte previous = (Byte) roles.get(rolesWrite[i].getString());

				if (previous != null) {
					roles.put(rolesWrite[i].getString(), new Byte((byte) (previous.byteValue() | Permission.WRITE)));
				} else {
					roles.put(rolesWrite[i].getString(), new Byte(Permission.WRITE));
				}
			}
			
			Value[] rolesDelete = node.getProperty(Permission.ROLES_DELETE).getValues();

			for (int i=0; i<rolesDelete.length; i++) {
				Byte previous = (Byte) roles.get(rolesDelete[i].getString());

				if (previous != null) {
					roles.put(rolesDelete[i].getString(), new Byte((byte) (previous.byteValue() | Permission.DELETE)));
				} else {
					roles.put(rolesDelete[i].getString(), new Byte(Permission.DELETE));
				}
			}
			
			Value[] rolesSecurity = node.getProperty(Permission.ROLES_SECURITY).getValues();

			for (int i=0; i<rolesSecurity.length; i++) {
				Byte previous = (Byte) roles.get(rolesSecurity[i].getString());

				if (previous != null) {
					roles.put(rolesSecurity[i].getString(), new Byte((byte) (previous.byteValue() | Permission.SECURITY)));
				} else {
					roles.put(rolesSecurity[i].getString(), new Byte(Permission.SECURITY));
				}
			}
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getGrantedRoles: {}", roles);
		return roles;
	}

	/**
	 *  View user session info
	 */
	public void view(String token) throws RepositoryException, DatabaseException {
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			String[] atributes = session.getAttributeNames();
			log.info("** ATRIBUTES **");
			for (int i=0; i<atributes.length; i++) {
				log.info(atributes[i]+" -> "+session.getAttribute(atributes[i]));
			}
			
			String[] lockTokens = session.getLockTokens();
			log.info("** LOCK TOKENS **");
			for (int i=0; i<lockTokens.length; i++) {
				log.info(lockTokens[i]);
			}
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
	}

	/**
	 * Singleton pattern for global Principal Adapter.
	 *
	 * @return
	 * @throws RepositoryException
	 */
	private PrincipalAdapter getPrincipalAdapter() throws PrincipalAdapterException {
		if (principalAdapter == null) {
			try {
				log.info("PrincipalAdapter: {}", Config.PRINCIPAL_ADAPTER);
				Object object = Class.forName(Config.PRINCIPAL_ADAPTER).newInstance();
				principalAdapter = (PrincipalAdapter) object;
			} catch (ClassNotFoundException e) {
				log.error(e.getMessage(), e);
				throw new PrincipalAdapterException(e.getMessage(), e);
			} catch (InstantiationException e) {
				log.error(e.getMessage(), e);
				throw new PrincipalAdapterException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				log.error(e.getMessage(), e);
				throw new PrincipalAdapterException(e.getMessage(), e);
			}
		}

		return principalAdapter;
	}
	
	@Override
	public List<String> getUsers(String token) throws PrincipalAdapterException {
		log.debug("getUsers({})", token);
		List<String> list = null;

		try {
			PrincipalAdapter principalAdapter = getPrincipalAdapter();
			list = principalAdapter.getUsers();
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw e;
		}

		log.debug("getUsers: {}", list);
		return list;
	}

	@Override
	public List<String> getRoles(String token) throws PrincipalAdapterException {
		log.debug("getRoles({})", token);
		List<String> list = null;

		try {
			PrincipalAdapter principalAdapter = getPrincipalAdapter();
			list = principalAdapter.getRoles();
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw e;
		}

		log.debug("getRoles: {}", list);
		return list;
	}

	/**
	 * Get mail user list from user list. 
	 */
	@Override
	public List<String> getMails(String token, List<String> users) throws PrincipalAdapterException {
		log.debug("getMails({})", token);
		List<String> list = null;

		try {
			PrincipalAdapter principalAdapter = getPrincipalAdapter();
			list = principalAdapter.getMails(users);
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw e;
		}

		log.debug("getMails: {}", list);
		return list;
	}
}
