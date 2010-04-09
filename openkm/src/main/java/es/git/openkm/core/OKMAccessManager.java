/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package es.git.openkm.core;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.security.auth.Subject;

import org.apache.jackrabbit.core.HierarchyManager;
import org.apache.jackrabbit.core.ItemId;
import org.apache.jackrabbit.core.NodeId;
import org.apache.jackrabbit.core.PropertyId;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.security.AMContext;
import org.apache.jackrabbit.core.security.AccessManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Document;
import es.git.openkm.bean.Note;
import es.git.openkm.bean.Permission;
import es.git.openkm.module.direct.DirectRepositoryModule;

/**
 * @author pavila
 * 
 */
public class OKMAccessManager implements AccessManager {
	private static Logger log = LoggerFactory.getLogger(OKMAccessManager.class);
	private Subject subject = null;
	private HierarchyManager hierMgr = null;
	private String principalUser = null;
	private Set<String> principalRoles = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jackrabbit.core.security.AccessManager#init(org.apache.jackrabbit.core.security.AMContext)
	 */
	public void init(AMContext context) throws AccessDeniedException {
		log.debug("init({})", context);
		subject = context.getSubject();
		principalRoles = new HashSet<String>();
		log.debug("##### {}", subject.getPrincipals());
		
		log.debug("##### ##### ##### ##### ##### ##### ##### ");
		for (Iterator it = subject.getPrincipals().iterator(); it.hasNext();) {
			Object obj = it.next();
			log.debug("##### {}", obj.getClass());
			  
			if (obj instanceof java.security.acl.Group) {
				java.security.acl.Group group = (java.security.acl.Group) obj;
				log.debug("Group: {}", group.getName());
				for (Enumeration groups = group.members(); groups.hasMoreElements(); ) {
					java.security.Principal rol = (java.security.Principal) groups.nextElement(); 
					log.debug("Rol: {}", rol.getName());
					principalRoles.add(rol.getName());
				}
			} else if (obj instanceof java.security.Principal) {
				java.security.Principal principal = (java.security.Principal) obj;
				principalUser = principal.getName();
				log.debug("Principal: {}", principalUser);
			} else if (obj instanceof org.apache.jackrabbit.core.security.UserPrincipal) {
				// TODO Esto es sólo para que funcione en modo shell para el desarrollo
				// de interfaz web.
				org.apache.jackrabbit.core.security.UserPrincipal userPrincipal = (org.apache.jackrabbit.core.security.UserPrincipal) obj;
				principalUser = userPrincipal.getName();
				principalRoles.add(Config.DEFAULT_USER_ROLE);
				log.debug("UserPrincipal: {}", principalUser);			
			}
		}
		
		log.debug("PrincipalUser: {}", principalUser);
		log.debug("PrincipalRoles: {}", principalRoles);
		log.debug("##### ##### ##### ##### ##### ##### ##### ");
		
		hierMgr = context.getHierarchyManager();
		log.debug("init: void");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jackrabbit.core.security.AccessManager#close()
	 */
	public void close() throws Exception {
		//log.debug("close()");
		//log.debug("close: void");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jackrabbit.core.security.AccessManager#checkPermission(org.apache.jackrabbit.core.ItemId,
	 *      int)
	 */
	public void checkPermission(ItemId id, int permissions)
			throws AccessDeniedException, ItemNotFoundException,
			RepositoryException {
		//log.debug("checkPermission()");
		//log.debug("checkPermission: void");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jackrabbit.core.security.AccessManager#isGranted(org.apache.jackrabbit.core.ItemId,
	 *      int)
	 */
	public boolean isGranted(ItemId id, int permissions) throws ItemNotFoundException, RepositoryException {
		log.debug("isGranted({}, {}, {})", new Object[] {subject.getPrincipals(), id, (permissions == AccessManager.READ ? "READ"
							: (permissions == AccessManager.WRITE ? "WRITE"
									: (permissions == AccessManager.REMOVE ? "REMOVE"
											: "NONE")))});
		Session systemSession = DirectRepositoryModule.getSystemSession();
		boolean access = false;
		
		if (principalRoles.contains(Config.DEFAULT_ADMIN_ROLE)) {
			// An user with AdminRole has total access
			access = true;
		} else {
			NodeId nodeId = null;
			log.debug("{} Item Id: {}", subject.getPrincipals(), id);
						
			// Workaround because of transiente node visibility
			try {
				log.debug("{} Item Path: {}", subject.getPrincipals(), hierMgr.getPath(id));
			} catch (ItemNotFoundException e) {
				access = true;
				log.debug("{} hierMgr.getPath() > ItemNotFoundException: {}", subject.getPrincipals(), e.getMessage());
			}

			if (id instanceof NodeId) {
				nodeId = (NodeId) id;
				log.debug("{} This is a NODE", subject.getPrincipals());
			} else {
				PropertyId propertyId = (PropertyId) id;
				nodeId = propertyId.getParentId();
				log.debug("{} This is a PROPERTY", subject.getPrincipals());
			}
			
			if (access || hierMgr.getPath(nodeId).denotesRoot()) {
				// Root node has full access
				access = true;
			} else {
				Node node = null;
				
				// Workaround because of transient node visibility
				try {
					node = ((SessionImpl) systemSession).getNodeById(nodeId);
				} catch (ItemNotFoundException e1) {
					log.debug("{} systemSession.getNodeById() > ItemNotFoundException: {}", subject.getPrincipals(), e1.getMessage());
				}
				
				if (node == null) {
					access = true;
				} else {
					log.debug("{} Node Name: {}", subject.getPrincipals(), node.getPath());
					log.debug("{} Node Type: {}", subject.getPrincipals(), node.getPrimaryNodeType().getName());
				
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
							log.debug("{} ItemNotFoundException -> {}", subject.getPrincipals(), e.getMessage());
						}
					} else if (node.isNodeType("nt:versionHistory")) {
						log.debug("{} Node is VERSION_HISTORY", subject.getPrincipals());
						String realNodeId = node.getProperty("jcr:versionableUuid").getString();
						
						try {
							node = systemSession.getNodeByUUID(realNodeId).getParent();
							log.debug("{} Real -> {}", subject.getPrincipals(), node.getPath());
						} catch (javax.jcr.ItemNotFoundException e) {
							log.debug("{} ItemNotFoundException -> {}", subject.getPrincipals(), e.getMessage());
						}
					}
					
					if (permissions == AccessManager.READ) {
						// Comprueba los usuarios de lectura
						try {
							access = checkRead(node);
						} catch (PathNotFoundException e) {
							log.debug("{} PathNotFoundException({}) in {}", new Object[] {subject.getPrincipals(), e.getMessage(), node.getPrimaryNodeType().getName()});
							access = true;
						}
					} else if (permissions == AccessManager.WRITE || permissions == AccessManager.REMOVE) {
						// Comprueba los usuarios de escritura
						try {
							access = checkWrite(node);
						} catch (PathNotFoundException e) {
							log.debug("{} PropertyNotFoundException({}) in {}", new Object[] {subject.getPrincipals(), e.getMessage(), node.getPrimaryNodeType().getName()});
							access = true;
						}
					}
				}
			}
		}

		// Workaround because of transiente node visibility
		try {
			log.debug("{} Path: {}", subject.getPrincipals(), hierMgr.getPath(id));
		} catch (ItemNotFoundException e) {
			log.debug("{} hierMgr.getPath() > ItemNotFoundException: {}", subject.getPrincipals(), e.getMessage());
		}
		
		log.debug("{} isGranted {} -> {}", new Object[] {subject.getPrincipals(), (permissions == AccessManager.READ ? "READ" 
				: (permissions == AccessManager.WRITE ? "WRITE" 
						: (permissions == AccessManager.REMOVE ? "REMOVE" 
								: "NONE"))), access});
		log.debug("-------------------------------------");
		return access;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jackrabbit.core.security.AccessManager#canAccess(java.lang.String)
	 */
	public boolean canAccess(String workspaceName)
			throws NoSuchWorkspaceException, RepositoryException {
		boolean access = true;
		//log.debug("canAccess({})", workspaceName);
		//log.debug("canAccess: {}", access);
		return access;
	}

	/**
	 * @param access
	 * @param node
	 * @return
	 * @throws ValueFormatException
	 * @throws RepositoryException
	 * @throws PathNotFoundException
	 */
	private boolean checkRead(Node node) throws ValueFormatException, RepositoryException, PathNotFoundException {
		log.debug("checkRead({})", node);
		// Propiedad no definida en nt:versionHistory, nt:version,
		// nt:frozenNode y okm:resource
		Value[] users = node.getProperty(Permission.USERS_READ).getValues();
		boolean access = false;
		
		for (int i = 0; i < users.length; i++) {
			log.debug("{} User: {}", Permission.USERS_READ, users[i].getString());
			
			if (principalUser.equals(users[i].getString())) {
				access = true;
			}
		}

		// If there is no user specific access, try with roles
		if (!access) {
			// Propiedad no definida en nt:versionHistory, nt:version y 
			// okm:resource
			Value[] roles = node.getProperty(Permission.ROLES_READ).getValues();
			
			for (int i = 0; i < roles.length; i++) {
				log.debug("{} Rol: {}", Permission.ROLES_READ, roles[i].getString());
				
				if (principalRoles.contains(roles[i].getString())) {
					access = true;
				}
			}
		}
		
		log.debug("checkRead: {}", access);
		return access;
	}

	/**
	 * @param access
	 * @param node
	 * @return
	 * @throws ValueFormatException
	 * @throws RepositoryException
	 * @throws PathNotFoundException
	 */
	private boolean checkWrite(Node node) throws ValueFormatException, RepositoryException, PathNotFoundException {
		log.debug("checkWrite({})", node);
		// Propiedad no definida en nt:versionHistory, nt:version y okm:resource
		Value[] users = node.getProperty(Permission.USERS_WRITE).getValues();
		boolean access = false;
		
		for (int i = 0; i < users.length; i++) {
			log.debug("{} User: {}", Permission.USERS_WRITE, users[i].getString());
			
			if (principalUser.equals(users[i].getString())) {
				access = true;
			}
		}

		// If there is no user specific access, try with roles
		if (!access) {
			// Propiedad no definida en nt:versionHistory, nt:version y
			// okm:resource
			Value[] roles = node.getProperty(Permission.ROLES_WRITE).getValues();
			
			for (int i = 0; i < roles.length; i++) {
				log.debug("{} Rol: {}", Permission.ROLES_WRITE, roles[i].getString());
				
				if (principalRoles.contains(roles[i].getString())) {
					access = true;
				}
			}
		}
		
		log.debug("checkWrite: {}", access);
		return access;
	}
}
