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

package es.git.openkm.module.direct;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Bookmark;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.Permission;
import es.git.openkm.bean.QueryParams;
import es.git.openkm.bean.Repository;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.Config;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.SessionManager;
import es.git.openkm.core.UserAlreadyLoggerException;
import es.git.openkm.module.AuthModule;
import es.git.openkm.principal.PrincipalAdapter;
import es.git.openkm.principal.PrincipalAdapterException;
import es.git.openkm.util.JCRUtils;
import es.git.openkm.util.UUIDGenerator;
import es.git.openkm.util.UserActivity;

public class DirectAuthModule implements AuthModule {
	private static Logger log = LoggerFactory.getLogger(DirectAuthModule.class);
	private static PrincipalAdapter principalAdapter = null;

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#login(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public synchronized String login(String user, String pass) throws AccessDeniedException, UserAlreadyLoggerException,
			RepositoryException {
		log.debug("login(" + user + ", " + pass + ")");
		String token = null;

		try {
			final javax.jcr.Repository r = DirectRepositoryModule.getRepository();
			Session session = null;

			// If user and pass are null, there is an external authentication system
			// using JAAS.
			if (user != null && pass != null) {
				session = r.login(new SimpleCredentials(user, pass.toCharArray()), null);
			} else {
				InitialContext ctx = new InitialContext();
				Subject subject = (Subject) ctx.lookup("java:comp/env/security/subject");

				log.info("Principals: "+subject.getPrincipals());

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
				int loggedUsers = sessions.getTokens().size();
				
				// okmAdmin user is excluded from the allowed users count
				if (sessions.getTokenByUserId(Config.ADMIN_USER) != null) {
					loggedUsers--;
				}
				
				//log.info("loggedUsers: "+loggedUsers);
				
				if (loggedUsers < Config.MAX_USERS || session.getUserID().equals(Config.ADMIN_USER)) {
					// Add generated session to pool
					token = UUIDGenerator.generate(this);
					log.info("Add generated session to pool: "+token);
					sessions.put(token, session);
					loadUserData(session);
				} else {
					log.warn("Maximun allowed users: "+Config.MAX_USERS+", Logged users: "+loggedUsers);
					throw new AccessDeniedException("Maximun logged users allowed");
				}
			}

			// Activity log
			UserActivity.log(session, "LOGIN", null, null);
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

		log.debug("login: " + token);
		return token;
	}

	/**
	 * @param session
	 * @throws javax.jcr.RepositoryException
	 * @throws javax.jcr.PathNotFoundException
	 * @throws ValueFormatException
	 * @throws javax.jcr.AccessDeniedException
	 * @throws ItemExistsException
	 * @throws ConstraintViolationException
	 * @throws InvalidItemStateException
	 * @throws ReferentialIntegrityException
	 * @throws VersionException
	 * @throws LockException
	 * @throws NoSuchNodeTypeException
	 */
	public static void loadUserData(Session session)
			throws javax.jcr.RepositoryException,
			javax.jcr.PathNotFoundException, ValueFormatException,
			javax.jcr.AccessDeniedException, ItemExistsException,
			ConstraintViolationException, InvalidItemStateException,
			ReferentialIntegrityException, VersionException, LockException,
			NoSuchNodeTypeException {
		log.info("loadUserData("+session.getUserID()+")");
		if (session.itemExists("/"+Repository.HOME+"/"+session.getUserID())) {
			log.info("** User Home already created **");
			Node userConfig = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+Repository.USER_CONFIG);
			Property lockTokensProperty = userConfig.getProperty(Repository.LOCK_TOKENS);
			Value[] lockTokensValues = lockTokensProperty.getValues();

			// Reload previous session lockTokens
			log.info(session.getUserID()+" # lockTokensValues.length: "+lockTokensValues.length);
			for (int i=0; i<lockTokensValues.length; i++) {
				String lockToken = lockTokensValues[i].getString();

				if (lockToken.length() > 1) {
					log.info(session.getUserID()+" # lockToken added: "+lockToken);
					session.addLockToken(lockToken);
				}
			}

			userConfig.save();
		} else { // Create user space filesystem
			// Create user home
			Node okmHome = session.getRootNode().getNode(Repository.HOME);
			createUserHome(okmHome, session.getUserID());
			
			log.info("** SAVING... **");
			okmHome.save();
			log.info("** SAVED **");
		}
	}

	/**
	 * @param okmHome
	 * @param userId
	 */
	public static void createUserHome(Node okmHome, String userId)
			throws ItemExistsException, javax.jcr.PathNotFoundException,
			NoSuchNodeTypeException, LockException, VersionException,
			ConstraintViolationException, javax.jcr.RepositoryException,
			ValueFormatException, javax.jcr.AccessDeniedException,
			InvalidItemStateException, ReferentialIntegrityException {
		log.info("** Create user '"+userId+"' home ("+okmHome.getPath()+"/"+userId+") **");
		Node userHome = okmHome.addNode(userId, Folder.TYPE);
		userHome.setProperty(Folder.AUTHOR, userId);
		userHome.setProperty(Folder.NAME, userId);

		// Auth info
		userHome.setProperty(Permission.USERS_READ, new String[] { userId });
		userHome.setProperty(Permission.USERS_WRITE, new String[] { userId });
		userHome.setProperty(Permission.ROLES_READ, new String[] {});
		userHome.setProperty(Permission.ROLES_WRITE, new String[] {});
		
		log.info("** Create user '"+userId+"' trash ("+userHome.getPath()+"/"+Repository.TRASH+") **");
		Node userTrash = userHome.addNode(Repository.TRASH, Folder.TYPE);
		userTrash.setProperty(Folder.AUTHOR,  userId);
		userTrash.setProperty(Folder.NAME, Repository.TRASH);

		// Auth info
		userTrash.setProperty(Permission.USERS_READ, new String[] { userId });
		userTrash.setProperty(Permission.USERS_WRITE, new String[] { userId });
		userTrash.setProperty(Permission.ROLES_READ, new String[] {});
		userTrash.setProperty(Permission.ROLES_WRITE, new String[] {});
		
		log.info("** Create user '"+userId+"' config ("+userHome.getPath()+"/"+Repository.USER_CONFIG+") **");
		Node userConfig = userHome.addNode(Repository.USER_CONFIG, Repository.USER_CONFIG_TYPE);
		userConfig.setProperty(Bookmark.HOME_PATH, "/"+Repository.ROOT);
		userConfig.setProperty(Bookmark.HOME_TYPE, Folder.TYPE);

		// Auth info
		userConfig.setProperty(Permission.USERS_READ, new String[] { userId });
		userConfig.setProperty(Permission.USERS_WRITE, new String[] { userId });
		userConfig.setProperty(Permission.ROLES_READ, new String[] {});
		userConfig.setProperty(Permission.ROLES_WRITE, new String[] {});

		log.info("** Create user '"+userId+"' personal documents ("+userHome.getPath()+"/"+Repository.PERSONAL+") **");
		Node userDocuments = userHome.addNode(Repository.PERSONAL, Folder.TYPE);
		userDocuments.setProperty(Folder.AUTHOR, userId);
		userDocuments.setProperty(Folder.NAME, Repository.PERSONAL);

		// Auth info
		userDocuments.setProperty(Permission.USERS_READ, new String[] { userId });
		userDocuments.setProperty(Permission.USERS_WRITE, new String[] { userId });
		userDocuments.setProperty(Permission.ROLES_READ, new String[] {});
		userDocuments.setProperty(Permission.ROLES_WRITE, new String[] {});

		log.info("** Create user '"+userId+"' mail ("+userHome.getPath()+"/"+Repository.MAIL+") **");
		Node userMail = userHome.addNode(Repository.MAIL, Folder.TYPE);
		userMail.setProperty(Folder.AUTHOR, userId);
		userMail.setProperty(Folder.NAME, Repository.MAIL);

		// Auth info
		userMail.setProperty(Permission.USERS_READ, new String[] { userId });
		userMail.setProperty(Permission.USERS_WRITE, new String[] { userId });
		userMail.setProperty(Permission.ROLES_READ, new String[] {});
		userMail.setProperty(Permission.ROLES_WRITE, new String[] {});

		log.info("** Create user '"+userId+"' query ("+userHome.getPath()+"/"+QueryParams.LIST+") **");
		Node userQuery = userHome.addNode(QueryParams.LIST, QueryParams.LIST_TYPE);

		// Auth info
		userQuery.setProperty(Permission.USERS_READ, new String[] { userId });
		userQuery.setProperty(Permission.USERS_WRITE, new String[] { userId });
		userQuery.setProperty(Permission.ROLES_READ, new String[] {});
		userQuery.setProperty(Permission.ROLES_WRITE, new String[] {});

		log.info("** Create user '"+userId+"' bookmark ("+userHome.getPath()+"/"+Bookmark.LIST+") **");
		Node userBookmark = userHome.addNode(Bookmark.LIST, Bookmark.LIST_TYPE);

		// Auth info
		userBookmark.setProperty(Permission.USERS_READ, new String[] { userId });
		userBookmark.setProperty(Permission.USERS_WRITE, new String[] { userId });
		userBookmark.setProperty(Permission.ROLES_READ, new String[] {});
		userBookmark.setProperty(Permission.ROLES_WRITE, new String[] {});
		
		// Save
		okmHome.save();
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#login()
	 */
	@Override
	public String login() throws AccessDeniedException, UserAlreadyLoggerException, RepositoryException {
		log.debug("login()");
		String token = login(null, null);
		log.debug("login: " + token);
		return token;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#logout(java.lang.String)
	 */
	@Override
	public synchronized void logout(String token) throws AccessDeniedException, RepositoryException {
		log.debug("logout(" + token + ")");
		Session session = SessionManager.getInstance().get(token);

		try {
			if (session.isLive()) {
				Node userConfig = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+Repository.USER_CONFIG);
				Property lockTokens = userConfig.getProperty(Repository.LOCK_TOKENS);
				String[] sessionLockTokens = session.getLockTokens();
				lockTokens.setValue(sessionLockTokens);
				userConfig.save();

				log.info(session.getUserID()+" # sessionLockTokens.length: "+sessionLockTokens.length);
				for (int i=0; i<sessionLockTokens.length; i++) {
					log.info(session.getUserID()+" # sessionLockTokens saved: "+sessionLockTokens[i]);
				}

				// Activity log
				UserActivity.log(session, "LOGOUT", null, null);

				SessionManager.getInstance().remove(token);
				session.logout();
			}
		} catch (javax.jcr.PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("logout: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#grantUser(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public void grantUser(String token, String nodePath, String user,
			int permissions, boolean recursive) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("grantUser(" + token + ", " + nodePath + ", " + user
				+ ", " + permissions + ", " + recursive + ")");
		Node node = null;

		// TODO: Comprobar si el usuario es dueño del nodo.
		// O a lo mejor hacer esta comprobación en el OKMAccessManager.
		try {
			Session session = SessionManager.getInstance().get(token);
			node = session.getRootNode().getNode(nodePath.substring(1));
			String property = null;

			if (permissions == Permission.READ) {
				property = Permission.USERS_READ;
			} else if (permissions == Permission.WRITE) {
				property = Permission.USERS_WRITE;
			}

			if (recursive) {
				grantUserInDepth(node, user, property);
			} else {
				grantUser(node, user, property);
			}

			// Activity log
			UserActivity.log(session, "GRANT_USER", nodePath, user+", "+permissions);
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
		}

		log.debug("grantUser: void");
	}

	/**
	 * @param node
	 * @param user
	 * @param property
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 */
	private void grantUser(Node node, String user, String property) throws ValueFormatException, PathNotFoundException, javax.jcr.RepositoryException {
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
			node.setProperty(property, (String[])newUsers.toArray(new String[0]));
			node.save();
		} catch (javax.jcr.lock.LockException e) {
			log.warn("grantUser -> LockException : "+node.getPath());
			JCRUtils.discardsPendingChanges(node);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn("grantUser -> AccessDeniedException : "+node.getPath());
			JCRUtils.discardsPendingChanges(node);
		}
	}

	/**
	 * @param node
	 * @param user
	 * @param property
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 */
	private void grantUserInDepth(Node node, String user, String property) throws ValueFormatException, PathNotFoundException, javax.jcr.RepositoryException {
		grantUser(node, user, property);

		if (node.isNodeType(Folder.TYPE)) {
			for (NodeIterator it = node.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				grantUserInDepth(child, user, property);
			}
		}
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#revokeUser(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public void revokeUser(String token, String nodePath, String user,
			int permissions, boolean recursive) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("revokeUser(" + token + ", " + nodePath + ", " + user
				+ ", " + permissions + ", " + recursive + ")");
		Node node = null;

		// TODO: Comprobar si el usuario es dueño del nodo.
		// O a lo mejor hacer esta comprobación en el OKMAccessManager.
		try {
			Session session = SessionManager.getInstance().get(token);
			node = session.getRootNode().getNode(nodePath.substring(1));
			String property = null;

			if (permissions == Permission.READ) {
				property = Permission.USERS_READ;
			} else if (permissions == Permission.WRITE) {
				property = Permission.USERS_WRITE;
			}

			if (recursive) {
				revokeUserInDepth(node, user, property);
			} else {
				revokeUser(node, user, property);
			}

			// Activity log
			UserActivity.log(session, "REVOKE_USER", nodePath, user+", "+permissions);
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
		}

		log.debug("revokeUser: void");
	}

	/**
	 * @param node
	 * @param user
	 * @param property
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 */
	private void revokeUser(Node node, String user, String property) throws ValueFormatException, PathNotFoundException, javax.jcr.RepositoryException {
		Value[] actualUsers = node.getProperty(property).getValues();
		ArrayList<String> newUsers = new ArrayList<String>();

		for (int i=0; i<actualUsers.length; i++) {
			if (!actualUsers[i].getString().equals(user)) {
				newUsers.add(actualUsers[i].getString());
			}
		}

		try {
			node.setProperty(property, (String[])newUsers.toArray(new String[0]));
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
	 * @param node
	 * @param user
	 * @param property
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 */
	private void revokeUserInDepth(Node node, String user, String property) throws ValueFormatException, PathNotFoundException, javax.jcr.RepositoryException {
		revokeUser(node, user, property);

		if (node.isNodeType(Folder.TYPE)) {
			for (NodeIterator it = node.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				revokeUserInDepth(child, user, property);
			}
		}
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#grantRole(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public void grantRole(String token, String nodePath, String role,
			int permissions, boolean recursive) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("grantRole(" + token + ", " + nodePath + ", " + role
				+ ", " + permissions + ", " + recursive + ")");
		Node node = null;

		// TODO: Comprobar si el usuario es dueño del nodo.
		// O a lo mejor hacer esta comprobación en el OKMAccessManager.
		try {
			Session session = SessionManager.getInstance().get(token);
			node = session.getRootNode().getNode(nodePath.substring(1));
			String property = null;

			if (permissions == Permission.READ) {
				property = Permission.ROLES_READ;
			} else if (permissions == Permission.WRITE) {
				property = Permission.ROLES_WRITE;
			}

			if (recursive) {
				grantRoleInDepth(node, role, property);
			} else {
				grantRole(node, role, property);
			}

			// Activity log
			UserActivity.log(session, "GRANT_ROLE", nodePath, role+", "+permissions);
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
		}

		log.debug("grantRole: void");
	}

	/**
	 * @param node
	 * @param role
	 * @param property
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 */
	private void grantRole(Node node, String role, String property) throws ValueFormatException, PathNotFoundException, javax.jcr.RepositoryException {
		Value[] actualRoles = node.getProperty(property).getValues();
		ArrayList<String> newRoles = new ArrayList<String>();

		for (int i=0; i<actualRoles.length; i++) {
			newRoles.add(actualRoles[i].getString());
		}

		// If the role isn't already granted add him
		if (!newRoles.contains(role)) {
			newRoles.add(role);
		}

		try {
			node.setProperty(property, (String[])newRoles.toArray(new String[0]));
			node.save();
		} catch (javax.jcr.lock.LockException e) {
			log.warn("grantRole -> LockException : "+node.getPath());
			JCRUtils.discardsPendingChanges(node);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn("grantRole -> AccessDeniedException : "+node.getPath());
			JCRUtils.discardsPendingChanges(node);
		}
	}

	/**
	 * @param node
	 * @param role
	 * @param property
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 */
	private void grantRoleInDepth(Node node, String role, String property) throws ValueFormatException, PathNotFoundException, javax.jcr.RepositoryException {
		grantRole(node, role, property);

		if (node.isNodeType(Folder.TYPE)) {
			for (NodeIterator it = node.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				grantRoleInDepth(child, role, property);
			}
		}
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#revokeRole(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public void revokeRole(String token, String nodePath, String role, int permissions, boolean recursive) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("revokeRole(" + token + ", " + nodePath + ", " + role
				+ ", " + permissions + ", " + recursive + ")");
		Node node = null;

		// TODO: Comprobar si el usuario es dueño del nodo.
		// O a lo mejor hacer esta comprobación en el OKMAccessManager.
		try {
			Session session = SessionManager.getInstance().get(token);
			node = session.getRootNode().getNode(nodePath.substring(1));
			String property = null;

			if (permissions == Permission.READ) {
				property = Permission.ROLES_READ;
			} else if (permissions == Permission.WRITE) {
				property = Permission.ROLES_WRITE;
			}

			if (recursive) {
				revokeRoleInDepth(node, role, property);
			} else {
				revokeRole(node, role, property);
			}

			// Activity log
			UserActivity.log(session, "REVOKE_ROLE", nodePath, role+", "+permissions);
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
		}

		log.debug("revokeRole: void");
	}

	/**
	 * @param node
	 * @param role
	 * @param property
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 */
	private void revokeRole(Node node, String role, String property) throws ValueFormatException, PathNotFoundException, javax.jcr.RepositoryException {
		Value[] actualRoles = node.getProperty(property).getValues();
		ArrayList<String> newRoles = new ArrayList<String>();

		for (int i=0; i<actualRoles.length; i++) {
			if (!actualRoles[i].getString().equals(role)) {
				newRoles.add(actualRoles[i].getString());
			}
		}

		try {
			node.setProperty(property, (String[])newRoles.toArray(new String[0]));
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
	 * @param node
	 * @param role
	 * @param property
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws javax.jcr.RepositoryException
	 */
	private void revokeRoleInDepth(Node node, String role, String property) throws ValueFormatException, PathNotFoundException, javax.jcr.RepositoryException {
		revokeRole(node, role, property);

		if (node.isNodeType(Folder.TYPE)) {
			for (NodeIterator it = node.getNodes(); it.hasNext(); ) {
				Node child = it.nextNode();
				revokeRoleInDepth(child, role, property);
			}
		}
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#getGrantedUsers(java.lang.String, java.lang.String)
	 */
	@Override
	public HashMap<String, Byte> getGrantedUsers(String token, String nodePath) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("getGrantedUsers(" + token + ", " + nodePath + ")");
		HashMap<String, Byte> users = new HashMap<String, Byte>();

		try {
			Session session = SessionManager.getInstance().get(token);
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
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getGrantedUsers: "+users);
		return users;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#getGrantedRoles(java.lang.String, java.lang.String)
	 */
	@Override
	public HashMap<String, Byte> getGrantedRoles(String token, String nodePath) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("getGrantedRoles(" + token + ", " + nodePath + ")");
		HashMap<String, Byte> roles = new HashMap<String, Byte>();

		try {
			Session session = SessionManager.getInstance().get(token);
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
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getGrantedRoles: "+roles);
		return roles;
	}

	/**
	 *  View user session info
	 *
	 * @param token The user login token.
	 */
	public void view(String token) {
		Session session = SessionManager.getInstance().get(token);

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
	}

	/**
	 * Singleton pattern for global Principal Adapter.
	 *
	 * @return
	 * @throws RepositoryException
	 */
	private PrincipalAdapter getPrincipalAdapter() throws RepositoryException {
		if (principalAdapter == null) {
			try {
				log.info("PrincipalAdapter: "+Config.PRINCIPAL_ADAPTER);
				Object object = Class.forName(Config.PRINCIPAL_ADAPTER).newInstance();
				principalAdapter = (PrincipalAdapter) object;
			} catch (ClassNotFoundException e) {
				log.error(e.getMessage(), e);
				throw new RepositoryException(e.getMessage(), e);
			} catch (InstantiationException e) {
				log.error(e.getMessage(), e);
				throw new RepositoryException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				log.error(e.getMessage(), e);
				throw new RepositoryException(e.getMessage(), e);
			}
		}

		return principalAdapter;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#getUsers(java.lang.String)
	 */
	@Override
	public Collection<String> getUsers(String token) throws RepositoryException {
		log.debug("getUsers("+token+")");
		Collection<String> list = null;

		try {
			PrincipalAdapter principalAdapter = getPrincipalAdapter();
			list = principalAdapter.getUsers();
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getUsers:"+list);
		return list;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.AuthModule#getRoles(java.lang.String)
	 */
	@Override
	public Collection<String> getRoles(String token) throws RepositoryException {
		log.debug("getRoles("+token+")");
		Collection<String> list = null;

		try {
			PrincipalAdapter principalAdapter = getPrincipalAdapter();
			list = principalAdapter.getRoles();
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getRoles:"+list);
		return list;
	}

	/**
	 * Get mail user list from user list. 
	 */
	@Override
	public Collection<String> getMails(String token, Collection<String> users) throws RepositoryException {
		log.debug("getMails("+token+")");
		Collection<String> list = null;

		try {
			PrincipalAdapter principalAdapter = getPrincipalAdapter();
			list = principalAdapter.getMails(users);
		} catch (PrincipalAdapterException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getMails:"+list);
		return list;
	}
}
