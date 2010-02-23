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

package com.openkm.core;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.security.auth.Subject;

import org.apache.jackrabbit.core.ItemId;
import org.apache.jackrabbit.core.NodeId;
import org.apache.jackrabbit.core.PropertyId;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.security.AMContext;
import org.apache.jackrabbit.core.security.AccessManager;
import org.apache.jackrabbit.core.security.authorization.AccessControlProvider;
import org.apache.jackrabbit.core.security.authorization.Permission;
import org.apache.jackrabbit.core.security.authorization.WorkspaceAccessManager;
import org.apache.jackrabbit.spi.Name;
import org.apache.jackrabbit.spi.Path;
import org.apache.jackrabbit.spi.commons.name.PathFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Document;
import com.openkm.bean.Note;
import com.openkm.module.direct.DirectRepositoryModule;

/**
 * @author pavila
 * 
 */
public class OKMAccessManager implements AccessManager {
	private static Logger log = LoggerFactory.getLogger(OKMAccessManager.class);
	private AMContext context;
	private Subject subject = null;
	private String principalUser = null;
	private Set<String> principalRoles = null;
	private ThreadLocal<Boolean> alreadyInsideAccessManager = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	@Override
	public void init(AMContext context) throws AccessDeniedException, Exception {
		log.info("init({})", context);
		this.context = context;
		subject = context.getSubject();
		principalRoles = new HashSet<String>();

		for (Iterator<java.security.Principal> it = subject.getPrincipals().iterator(); it.hasNext();) {
			Object obj = it.next();
			log.info("##### {}", obj.getClass());
			
			if (obj instanceof org.apache.jackrabbit.core.security.principal.EveryonePrincipal) {
				// Needed for test.
				log.info("o.a.j.c.s.p.EveryonePrincipal: {}", obj);
				org.apache.jackrabbit.core.security.principal.EveryonePrincipal everyonePrincipal = 
					(org.apache.jackrabbit.core.security.principal.EveryonePrincipal) obj;
			} else if (obj instanceof org.apache.jackrabbit.core.security.UserPrincipal) {
				// Needed for test.
				log.info("o.a.j.c.s.UserPrincipal: {}", obj);
				org.apache.jackrabbit.core.security.UserPrincipal userPrincipal = 
					(org.apache.jackrabbit.core.security.UserPrincipal) obj;
				principalUser = userPrincipal.getName();
				principalRoles.add(Config.DEFAULT_USER_ROLE);
			} else if (obj instanceof java.security.acl.Group) {
				log.info("j.s.a.Group: {}", obj);
				java.security.acl.Group group = (java.security.acl.Group) obj;
				for (Enumeration<? extends java.security.Principal> groups = group.members(); groups
						.hasMoreElements();) {
					java.security.Principal rol = (java.security.Principal) groups.nextElement();
					log.info("Rol: {}", rol.getName());
					principalRoles.add(rol.getName());
				}
			} else if (obj instanceof java.security.Principal) {
				log.info("j.s.Principal: {}", obj);
				java.security.Principal principal = (java.security.Principal) obj;
				principalUser = principal.getName();
			}
		}
	}

	@Override
	public void init(AMContext context, AccessControlProvider acProvider, WorkspaceAccessManager wspAccessMgr)
			throws AccessDeniedException, Exception {
		log.debug("init({}, {}, {}", new Object[] { context, acProvider, wspAccessMgr });
		init(context);
	}

	@Override
	public void close() throws Exception {
		log.debug("close()");
	}

	@Override
	public boolean canAccess(String workspaceName) throws RepositoryException {
		log.info("canAccess({})", workspaceName);
		return true;
	}

	@Override
	public boolean canRead(Path itemPath) throws RepositoryException {
		log.info("canRead({})", itemPath);
		return isGranted(itemPath, Permission.READ);
	}

	@Override
	// This method is deprecated in Jackrabbit 1.5.0
	public void checkPermission(ItemId id, int permissions) throws AccessDeniedException,
			ItemNotFoundException, RepositoryException {
		log.info("checkPermission({}, {})", id, permissionsToString(permissions));
		if (isGranted(id, deprecatedPermissionsToNewApi(permissions))) {
			return;
		}
		throw new AccessDeniedException("Permission denied!");
	}
	
	@Override
	public void checkPermission(Path absPath, int permissions) throws AccessDeniedException,
			RepositoryException {
		log.info("checkPermission({}, {})", absPath, permissions);
		if (isGranted(absPath, permissions)) {
			return;
		}
		throw new AccessDeniedException("Permission denied!");
	}

	@Override
	// This method is deprecated in Jackrabbit 1.5.0
	public boolean isGranted(ItemId id, int permissions) throws ItemNotFoundException, RepositoryException {
		log.info("isGranted({}, {})", id, permissionsToString(permissions));
		Path path = context.getHierarchyManager().getPath(id);
		return isGranted(path, deprecatedPermissionsToNewApi(permissions));
	}

	@Override
	public boolean isGranted(Path parentPath, Name childName, int permissions) throws RepositoryException {
		log.info("isGranted({}, {}, {})", new Object[] { parentPath, childName, permissionsToString(permissions) });
		Path p = PathFactoryImpl.getInstance().create(parentPath, childName, true);
		return isGranted(p, permissions);
	}
	
	@Override
	public boolean isGranted(Path absPath, int permissions) throws RepositoryException {
		log.info("isGranted({}, {})", absPath, permissionsToString(permissions));
		boolean access = false;

		if (alreadyInsideAccessManager.get()) {
			log.debug("[YES inside]");
			access = true;
		} else {
			log.debug("[NOT inside]");
			alreadyInsideAccessManager.set(Boolean.TRUE);
			access = checkAccess(absPath, permissions);
			alreadyInsideAccessManager.remove();
		}

		return access;
	}

	/**
	 * 
	 */
	private boolean checkAccess(Path absPath, int permissions) throws RepositoryException {
		log.info("checkAccess({}, {})", absPath, permissionsToString(permissions));
		Session systemSession = DirectRepositoryModule.getSystemSession();
		boolean access = false;

		if (principalRoles.contains(Config.DEFAULT_ADMIN_ROLE)) {
			// An user with AdminRole has total access
			access = true;
		} else {
			log.debug("{} Path: {}", subject.getPrincipals(), absPath);
			NodeId nodeId = context.getHierarchyManager().resolveNodePath(absPath);
			// Workaround because of transiente node visibility
			// try {
			// log.debug("{} Item Path: {}", subject.getPrincipals(),
			// hierMgr.getPath(id));
			// } catch (ItemNotFoundException e) {
			// access = true;
			// log.debug("{} hierMgr.getPath() > ItemNotFoundException: {}",
			// subject.getPrincipals(), e.getMessage());
			// }

			if (nodeId != null) {
				log.debug("{} This is a NODE", subject.getPrincipals());
			} else {
				PropertyId propertyId = context.getHierarchyManager().resolvePropertyPath(absPath);
				nodeId = propertyId.getParentId();
				log.debug("{} This is a PROPERTY", subject.getPrincipals());
			}

			if (access || absPath.denotesRoot()) {
				// Root node has full access
				access = true;
			} else {
				Node node = ((SessionImpl) systemSession).getNodeById(nodeId);

				// Workaround because of transient node visibility
				// try {
				// node = ((SessionImpl) systemSession).getNodeById(nodeId);
				// } catch (ItemNotFoundException e1) {
				// log.debug("{} systemSession.getNodeById() > ItemNotFoundException: {}",
				// subject.getPrincipals(), e1.getMessage());
				// }

				if (node == null) {
					access = true;
				} else {
					log.debug("{} Node Name: {}", subject.getPrincipals(), node.getPath());
					log.debug("{} Node Type: {}", subject.getPrincipals(), node.getPrimaryNodeType()
							.getName());

					if (node.isNodeType(Document.CONTENT_TYPE)) {
						log.debug("{} Node is CONTENT_TYPE", subject.getPrincipals());
						node = node.getParent();
						log.debug("{} Real -> {}", subject.getPrincipals(), node.getPath());
					} else if (node.isNodeType(Note.LIST_TYPE)) {
						log.debug("{} Node is NOTE_LIST_TYPE", subject.getPrincipals());
						node = node.getParent();
						log.debug("{} Real -> {}", subject.getPrincipals(), node.getPath());
					} else if (node.isNodeType(Note.TYPE)) {
						log.debug("{} Node is NOTE_TYPE", subject.getPrincipals());
						node = node.getParent().getParent();
						log.debug("{} Real -> {}", subject.getPrincipals(), node.getPath());
					} else if (node.isNodeType("nt:frozenNode")) {
						log.debug("{} Node is FROZEN_NODE", subject.getPrincipals());
						String realNodeId = node.getProperty("jcr:frozenUuid").getString();
						node = systemSession.getNodeByUUID(realNodeId).getParent();
						log.debug("{} Real -> {}", subject.getPrincipals(), node.getPath());
					} else if (node.isNodeType("nt:version")) {
						log.debug("{} Node is VERSION", subject.getPrincipals());
						Node frozenNode = node.getNode("jcr:frozenNode");
						log.debug("{} Frozen node -> {}", subject.getPrincipals(), frozenNode.getPath());
						String realNodeId = frozenNode.getProperty("jcr:frozenUuid").getString();

						try {
							node = systemSession.getNodeByUUID(realNodeId).getParent();
							log.debug("{} Real -> {}", subject.getPrincipals(), node.getPath());
						} catch (javax.jcr.ItemNotFoundException e) {
							log.debug("{} ItemNotFoundException -> {}", subject.getPrincipals(), e
									.getMessage());
						}
					} else if (node.isNodeType("nt:versionHistory")) {
						log.debug("{} Node is VERSION_HISTORY", subject.getPrincipals());
						String realNodeId = node.getProperty("jcr:versionableUuid").getString();

						try {
							node = systemSession.getNodeByUUID(realNodeId).getParent();
							log.debug("{} Real -> {}", subject.getPrincipals(), node.getPath());
						} catch (javax.jcr.ItemNotFoundException e) {
							log.debug("{} ItemNotFoundException -> {}", subject.getPrincipals(), e
									.getMessage());
						}
					}

					if (permissions == AccessManager.READ) {
						// Comprueba los usuarios de lectura
						try {
							access = checkRead(node);
						} catch (PathNotFoundException e) {
							log.debug("{} PathNotFoundException({}) in {}", new Object[] {
									subject.getPrincipals(), e.getMessage(),
									node.getPrimaryNodeType().getName() });
							access = true;
						}
					} else if (permissions == AccessManager.WRITE || permissions == AccessManager.REMOVE) {
						// Comprueba los usuarios de escritura
						try {
							access = checkWrite(node);
						} catch (PathNotFoundException e) {
							log.debug("{} PropertyNotFoundException({}) in {}", new Object[] {
									subject.getPrincipals(), e.getMessage(),
									node.getPrimaryNodeType().getName() });
							access = true;
						}
					}
				}
			}
		}

		// Workaround because of transiente node visibility
		// try {
		// log.debug("{} Path: {}", subject.getPrincipals(),
		// hierMgr.getPath(id));
		// } catch (ItemNotFoundException e) {
		// log.debug("{} hierMgr.getPath() > ItemNotFoundException: {}",
		// subject.getPrincipals(), e.getMessage());
		// }

		return access;
	}

	/**
	 * 
	 */
	private boolean checkRead(Node node) throws ValueFormatException, RepositoryException,
			PathNotFoundException {
		log.debug("checkRead({})", node);
		// Propiedad no definida en nt:versionHistory, nt:version,
		// nt:frozenNode y okm:resource
		Value[] users = node.getProperty(com.openkm.bean.Permission.USERS_READ).getValues();
		boolean access = false;

		for (int i = 0; i < users.length; i++) {
			log.debug("{} User: {}", com.openkm.bean.Permission.USERS_READ, users[i].getString());

			if (principalUser.equals(users[i].getString())) {
				access = true;
			}
		}

		// If there is no user specific access, try with roles
		if (!access) {
			// Propiedad no definida en nt:versionHistory, nt:version y
			// okm:resource
			Value[] roles = node.getProperty(com.openkm.bean.Permission.ROLES_READ).getValues();

			for (int i = 0; i < roles.length; i++) {
				log.debug("{} Rol: {}", com.openkm.bean.Permission.ROLES_READ, roles[i].getString());

				if (principalRoles.contains(roles[i].getString())) {
					access = true;
				}
			}
		}

		log.debug("checkRead: {}", access);
		return access;
	}

	/**
	 * 
	 */
	private boolean checkWrite(Node node) throws ValueFormatException, RepositoryException,
			PathNotFoundException {
		log.debug("checkWrite({})", node);
		// Propiedad no definida en nt:versionHistory, nt:version y okm:resource
		Value[] users = node.getProperty(com.openkm.bean.Permission.USERS_WRITE).getValues();
		boolean access = false;

		for (int i = 0; i < users.length; i++) {
			log.debug("{} User: {}", com.openkm.bean.Permission.USERS_WRITE, users[i].getString());

			if (principalUser.equals(users[i].getString())) {
				access = true;
			}
		}

		// If there is no user specific access, try with roles
		if (!access) {
			// Propiedad no definida en nt:versionHistory, nt:version y
			// okm:resource
			Value[] roles = node.getProperty(com.openkm.bean.Permission.ROLES_WRITE).getValues();

			for (int i = 0; i < roles.length; i++) {
				log.debug("{} Rol: {}", com.openkm.bean.Permission.ROLES_WRITE, roles[i].getString());

				if (principalRoles.contains(roles[i].getString())) {
					access = true;
				}
			}
		}

		log.debug("checkWrite: {}", access);
		return access;
	}

	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	private int deprecatedPermissionsToNewApi(int permissions) {
		boolean read = (permissions & READ) != 0;
		boolean write = (permissions & WRITE) != 0;
		boolean remove = (permissions & REMOVE) != 0;
		int result = 0;

		if (read) {
			result = result | Permission.READ;
		}

		if (write) {
			result = result | Permission.ADD_NODE;
			result = result | Permission.SET_PROPERTY;
		}

		if (remove) {
			result = result | Permission.REMOVE_NODE;
			result = result | Permission.REMOVE_PROPERTY;
		}

		return result;
	}

	/**
	 * 
	 */
	private String permissionsToString(int permissions) {
		StringBuilder sb = new StringBuilder();

		if (!(permissions == Permission.NONE)) {
			// if ((actions & Permission.ALL) != 0) {
			// sb.append("all ");
			// }
			if ((permissions & Permission.ADD_NODE) != 0) {
				sb.append("add_node ");
			}
			if ((permissions & Permission.READ) != 0) {
				sb.append("read ");
			}
			if ((permissions & Permission.REMOVE_NODE) != 0) {
				sb.append("remove_node ");
			}
			if ((permissions & Permission.REMOVE_PROPERTY) != 0) {
				sb.append("remove_property ");
			}
			if ((permissions & Permission.SET_PROPERTY) != 0) {
				sb.append("set_property ");
			}
		}

		return sb.toString();
	}
}
