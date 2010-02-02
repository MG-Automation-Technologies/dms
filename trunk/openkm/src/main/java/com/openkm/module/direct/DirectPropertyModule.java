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

import java.util.ArrayList;

import javax.jcr.Node;
import javax.jcr.PropertyType;
import javax.jcr.Session;
import javax.jcr.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Property;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.SessionManager;
import com.openkm.core.VersionException;
import com.openkm.module.PropertyModule;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;

public class DirectPropertyModule implements PropertyModule {
	private static Logger log = LoggerFactory.getLogger(DirectPropertyModule.class);

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyModule#addCategory(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addCategory(String token, String nodePath, String category)
			throws VersionException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("addCategory(" + token + ", " + nodePath + ", " + category + ")");
		Node documentNode = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			documentNode = session.getRootNode().getNode(nodePath.substring(1));
			Value[] property = documentNode.getProperty(Property.CATEGORIES).getValues();
			Value[] newProperty = new Value[property.length+1];
			boolean alreadyAdded = false;
			
			for (int i=0; i<property.length; i++) {
				newProperty[i] = property[i];
				log.info("New: "+newProperty[i].getString()+" --> "+newProperty[i].getType());
				
				if (property[i].getString().equals(category)) {
					alreadyAdded = true;
				}
			}
			
			if (!alreadyAdded) {
				Node reference = session.getNodeByUUID(category);
				newProperty[newProperty.length-1] = session.getValueFactory().createValue(reference);
				documentNode.setProperty(Property.CATEGORIES, newProperty, PropertyType.REFERENCE);
				documentNode.save();
			}
			
			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "ADD_CATEGORY", null);

			// Check scripting
			DirectScriptingModule.checkScripts(documentNode, documentNode.getPath(), session.getUserID(), "ADD_CATEGORY");

			// Activity log
			UserActivity.log(session, "ADD_CATEGORY", nodePath, category);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.version.VersionException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new VersionException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("addCategory: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyModule#removeCategory(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void removeCategory(String token, String nodePath, String category)
			throws VersionException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("removeCategory(" + token + ", " + nodePath + ", " + category + ")");
		Node documentNode = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			documentNode = session.getRootNode().getNode(nodePath.substring(1));
			Value[] property = documentNode.getProperty(Property.CATEGORIES).getValues();
			ArrayList<Value> newProperty = new ArrayList<Value>();
						
			for (int i=0; i<property.length; i++) {
				if (!property[i].getString().equals(category)) {
					newProperty.add(property[i]);
				}
			}
			
			documentNode.setProperty(Property.CATEGORIES, (Value[])newProperty.toArray(new Value[0]));
			documentNode.save();
			
			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "REMOVE_CATEGORY", null);

			// Check scripting
			DirectScriptingModule.checkScripts(documentNode, documentNode.getPath(), session.getUserID(), "REMOVE_CATEGORY");

			// Activity log
			UserActivity.log(session, "REMOVE_CATEGORY", nodePath, category);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.version.VersionException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new VersionException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("removeCategory: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyModule#addKeyword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addKeyword(String token, String nodePath, String keyword)
			throws VersionException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("addKeyword(" + token + ", " + nodePath + ", " + keyword + ")");
		Node documentNode = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			documentNode = session.getRootNode().getNode(nodePath.substring(1));
			Value[] property = documentNode.getProperty(Property.KEYWORDS).getValues();
			Value[] newProperty = new Value[property.length+1];
			boolean alreadyAdded = false;
			
			for (int i=0; i<property.length; i++) {
				newProperty[i] = property[i];
				
				if (property[i].equals(keyword)) {
					alreadyAdded = true;
				}
			}
			
			if (!alreadyAdded) {
				newProperty[newProperty.length-1] = session.getValueFactory().createValue(keyword);
				documentNode.setProperty(Property.KEYWORDS, newProperty);
				documentNode.save();
			}
			
			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "ADD_KEYWORD", null);

			// Check scripting
			DirectScriptingModule.checkScripts(documentNode, documentNode.getPath(), session.getUserID(), "ADD_KEYWORD");

			// Activity log
			UserActivity.log(session, "ADD_KEYWORD", nodePath, keyword);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.version.VersionException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new VersionException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("addKeyword: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.PropertyModule#removeKeyword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void removeKeyword(String token, String nodePath, String keyword)
			throws VersionException, LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("removeKeyword(" + token + ", " + nodePath + ", " + keyword + ")");
		Node documentNode = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			documentNode = session.getRootNode().getNode(nodePath.substring(1));
			Value[] property = documentNode.getProperty(Property.KEYWORDS).getValues();
			ArrayList<Value> newProperty = new ArrayList<Value>();
						
			for (int i=0; i<property.length; i++) {
				if (!property[i].getString().equals(keyword)) {
					newProperty.add(property[i]);
				}
			}
			
			documentNode.setProperty(Property.KEYWORDS, (Value[])newProperty.toArray(new Value[0]));
			documentNode.save();
			
			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "REMOVE_KEYWORD", null);

			// Check scripting
			DirectScriptingModule.checkScripts(documentNode, documentNode.getPath(), session.getUserID(), "REMOVE_KEYWORD");

			// Activity log
			UserActivity.log(session, "REMOVE_KEYWORD", nodePath, keyword);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.version.VersionException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new VersionException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(documentNode);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("removeKeyword: void");
	}	
}
