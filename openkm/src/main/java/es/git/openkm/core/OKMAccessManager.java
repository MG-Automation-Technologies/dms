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
	private static final boolean DEBUG = false;
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
		log.debug("init(" + context + ")");
		subject = context.getSubject();
		principalRoles = new HashSet<String>();
		log.debug("##### "+subject.getPrincipals());
		
		log.debug("##### ##### ##### ##### ##### ##### ##### ");
		for (Iterator it = subject.getPrincipals().iterator(); it.hasNext();) {
			Object obj = it.next();
			log.debug("##### "+obj.getClass());
			  
			if (obj instanceof java.security.acl.Group) {
				java.security.acl.Group group = (java.security.acl.Group) obj;
				log.debug("Group: "+group.getName());
				for (Enumeration groups = group.members(); groups.hasMoreElements(); ) {
					java.security.Principal rol = (java.security.Principal) groups.nextElement(); 
					log.debug("Rol: "+rol.getName());
					principalRoles.add(rol.getName());
				}
			} else if (obj instanceof java.security.Principal) {
				java.security.Principal principal = (java.security.Principal) obj;
				principalUser = principal.getName();
				log.debug("Principal: "+principalUser);
			} else if (obj instanceof org.apache.jackrabbit.core.security.UserPrincipal) {
				// TODO Esto es sólo para que funcione en modo shell para el desarrollo
				// de interfaz web.
				org.apache.jackrabbit.core.security.UserPrincipal userPrincipal = (org.apache.jackrabbit.core.security.UserPrincipal) obj;
				principalUser = userPrincipal.getName();
				principalRoles.add(Config.DEFAULT_USER_ROLE);
				log.debug("UserPrincipal: "+principalUser);			
			}
		}
		
		log.debug("PrincipalUser: "+principalUser);
		log.debug("PrincipalRoles: "+principalRoles);
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
		if (DEBUG) log.debug("close()");
		if (DEBUG) log.debug("close: void");
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
		if (DEBUG) log.debug("checkPermission()");
		// TODO Auto-generated method stub
		if (DEBUG) log.debug("checkPermission: void");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jackrabbit.core.security.AccessManager#isGranted(org.apache.jackrabbit.core.ItemId,
	 *      int)
	 */
	public boolean isGranted(ItemId id, int permissions) throws ItemNotFoundException, RepositoryException {
		if (DEBUG) log.debug("isGranted(" + subject.getPrincipals() + ", " + id + ", "
					+ (permissions == AccessManager.READ ? "READ"
							: (permissions == AccessManager.WRITE ? "WRITE"
									: (permissions == AccessManager.REMOVE ? "REMOVE"
											: "NONE"))) + ")");
		Session systemSession = DirectRepositoryModule.getSystemSession();
		boolean access = false;
		
		if (principalRoles.contains(Config.DEFAULT_ADMIN_ROLE)) {
			// An user with AdminRole has total access
			access = true;
		} else {
			NodeId nodeId = null;
			if (DEBUG) log.debug(subject.getPrincipals()+" Item Id: "+id);
						
			// Workaround because of transiente node visibility
			try {
				if (DEBUG) log.debug(subject.getPrincipals()+" Item Path: "+hierMgr.getPath(id));
			} catch (ItemNotFoundException e) {
				access = true;
				if (DEBUG) log.debug(subject.getPrincipals()+" hierMgr.getPath() > ItemNotFoundException: "+e.getMessage());
			}

			if (id instanceof NodeId) {
				nodeId = (NodeId) id;
				if (DEBUG) log.debug(subject.getPrincipals()+" This is a NODE");
			} else {
				PropertyId propertyId = (PropertyId) id;
				nodeId = propertyId.getParentId();
				if (DEBUG) log.debug(subject.getPrincipals()+" This is a PROPERTY");
			}
			
			if (access || hierMgr.getPath(nodeId).denotesRoot()) {
				// Root node has full access
				access = true;
			} else {
				Node node = null;
				
				// Workaround because of transiente node visibility
				try {
					node = ((SessionImpl) systemSession).getNodeById(nodeId);
				} catch (ItemNotFoundException e1) {
					if (DEBUG) log.debug(subject.getPrincipals()+" systemSession.getNodeById() > ItemNotFoundException: "+e1.getMessage());
				}
				
				if (node == null) {
					access = true;
				} else {
					if (DEBUG) log.debug(subject.getPrincipals()+" Node Name: " + node.getPath());
					if (DEBUG) log.debug(subject.getPrincipals()+" Node Type: " + node.getPrimaryNodeType().getName());
				
					if (node.isNodeType(Document.CONTENT_TYPE)) {
						if (DEBUG) log.debug(subject.getPrincipals()+" Node is CONTENT_TYPE");
						node = node.getParent();
						if (DEBUG) log.debug(subject.getPrincipals()+" Real -> "+node.getPath());
					} else if (node.isNodeType(Note.LIST_TYPE)) {
						if (DEBUG) log.debug(subject.getPrincipals()+" Node is NOTE_LIST_TYPE");
						node = node.getParent();
						if (DEBUG) log.debug(subject.getPrincipals()+" Real -> "+node.getPath());
					} else if (node.isNodeType(Note.TYPE)) {
						if (DEBUG) log.debug(subject.getPrincipals()+" Node is NOTE_TYPE");
						node = node.getParent().getParent();
						if (DEBUG) log.debug(subject.getPrincipals()+" Real -> "+node.getPath());
					} else if (node.isNodeType("nt:frozenNode")) {
						if (DEBUG) log.debug(subject.getPrincipals()+" Node is FROZEN_NODE");
						String realNodeId = node.getProperty("jcr:frozenUuid").getString();
						node = systemSession.getNodeByUUID(realNodeId).getParent();
						if (DEBUG) log.debug(subject.getPrincipals()+" Real -> "+node.getPath());
					} else if (node.isNodeType("nt:version")) {
						log.debug(subject.getPrincipals()+" Node is VERSION");
						Node frozenNode = node.getNode("jcr:frozenNode");
						log.debug(subject.getPrincipals()+" el congelado -> "+frozenNode.getPath());
						String realNodeId = frozenNode.getProperty("jcr:frozenUuid").getString();
						
						try {
							node = systemSession.getNodeByUUID(realNodeId).getParent();
							if (DEBUG) log.debug(subject.getPrincipals()+" Real -> "+node.getPath());
						} catch (javax.jcr.ItemNotFoundException e) {
							if (DEBUG) log.debug(subject.getPrincipals()+" **************");
							if (DEBUG) log.debug(subject.getPrincipals()+" -> "+e.getMessage());
						}
					} else if (node.isNodeType("nt:versionHistory")) {
						if (DEBUG) log.debug(subject.getPrincipals()+" Node is VERSION_HISTORY");
						String realNodeId = node.getProperty("jcr:versionableUuid").getString();
						
						try {
							node = systemSession.getNodeByUUID(realNodeId).getParent();
							if (DEBUG) log.debug(subject.getPrincipals()+" Real -> "+node.getPath());
						} catch (javax.jcr.ItemNotFoundException e) {
							if (DEBUG) log.debug(subject.getPrincipals()+" **************");
							if (DEBUG) log.debug(subject.getPrincipals()+" **************");
							if (DEBUG) log.debug(subject.getPrincipals()+" -> "+e.getMessage());
						}
					}
					
					if (permissions == AccessManager.READ) {
						// Comprueba los usuarios de lectura
						try {
							access = checkRead(node);
						} catch (PathNotFoundException e) {
							if (DEBUG) log.debug(subject.getPrincipals()+" PathNotFoundException: "+e.getMessage()+
									" in "+node.getPrimaryNodeType().getName());
						}
					} else if (permissions == AccessManager.WRITE || permissions == AccessManager.REMOVE) {
						// Comprueba los usuarios de escritura
						try {
							access = checkWrite(node);
						} catch (PathNotFoundException e) {
							if (DEBUG) log.debug(subject.getPrincipals()+" PropertyNotFoundException: "+e.getMessage()+" in "+
								node.getPrimaryNodeType().getName());
						}
					}
				}
			}
		}

		// Workaround because of transiente node visibility
		try {
			if (DEBUG) log.debug(subject.getPrincipals()+" Path: " + hierMgr.getPath(id));
		} catch (ItemNotFoundException e) {
			if (DEBUG) log.debug(subject.getPrincipals()+" hierMgr.getPath() > ItemNotFoundException: "+e.getMessage());
		}
		
		if (DEBUG) log.debug(subject.getPrincipals()+" isGranted "+(permissions == AccessManager.READ ? "READ" 
				: (permissions == AccessManager.WRITE ? "WRITE" 
						: (permissions == AccessManager.REMOVE ? "REMOVE" 
								: "NONE")))+": " + access);
		if (DEBUG) log.debug("-------------------------------------");
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
		if (DEBUG) log.debug("canAccess(" + workspaceName + ")");
		if (DEBUG) log.debug("canAccess: " + access);
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
		if (DEBUG) log.debug("checkRead("+node+")");
		// Propiedad no definida en nt:versionHistory, nt:version,
		// nt:frozenNode y okm:resource
		Value[] users = node.getProperty(Permission.USERS_READ).getValues();
		boolean access = false;
		
		for (int i = 0; i < users.length; i++) {
			if (DEBUG) log.debug(Permission.USERS_READ+" User: " + users[i].getString());
			
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
				if (DEBUG) log.debug(Permission.ROLES_READ+" Rol: " + roles[i].getString());
				
				if (principalRoles.contains(roles[i].getString())) {
					access = true;
				}
			}
		}
		
		if (DEBUG) log.debug("checkRead: "+access);
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
		if (DEBUG) log.debug("checkWrite("+node+")");
		// Propiedad no definida en nt:versionHistory, nt:version y okm:resource
		Value[] users = node.getProperty(Permission.USERS_WRITE).getValues();
		boolean access = false;
		
		for (int i = 0; i < users.length; i++) {
			if (DEBUG) log.debug(Permission.USERS_WRITE+" User: " + users[i].getString());
			
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
				if (DEBUG) log.debug(Permission.ROLES_WRITE+" Rol: " + roles[i].getString());
				
				if (principalRoles.contains(roles[i].getString())) {
					access = true;
				}
			}
		}
		
		if (DEBUG) log.debug("checkWrite: "+access);
		return access;
	}
}
