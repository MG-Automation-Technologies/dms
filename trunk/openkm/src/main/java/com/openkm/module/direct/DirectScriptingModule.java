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

import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.EvalError;
import bsh.Interpreter;

import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Scripting;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.SessionManager;
import com.openkm.module.ScriptingModule;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;

public class DirectScriptingModule implements ScriptingModule {
	private static Logger log = LoggerFactory.getLogger(DirectScriptingModule.class);

	@Override
	public void setScript(String token, String nodePath, String code)
			throws PathNotFoundException, AccessDeniedException,
			RepositoryException {
		log.debug("setScript(" + token + ", " + nodePath + ", " + code + ")");
		Node node = null;
		Node sNode = null;
		
		try {
			Session session = SessionManager.getInstance().get(token);
			
			if (Config.ADMIN_USER.equals(session.getUserID())) {
				Session systemSession = DirectRepositoryModule.getSystemSession();
				node = session.getRootNode().getNode(nodePath.substring(1));
				sNode = systemSession.getNodeByUUID(node.getUUID());
				
				// Perform scripting
				sNode.addMixin(Scripting.TYPE);
				sNode.setProperty(Scripting.SCRIPT_CODE, code);
				sNode.save();
				
				// Activity log
				UserActivity.log(session, "SET_SCRIPT", nodePath, null);
			} else {
				throw new AccessDeniedException("Sorry, only for admin user");
			}
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(sNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(sNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(sNode);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("setScript: void");
	}

	@Override
	public void removeScript(String token, String nodePath)
			throws PathNotFoundException, AccessDeniedException,
			RepositoryException {
		log.debug("removeScript(" + token + ", " + nodePath + ")");
		Node node = null;
		Node sNode = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			
			if (Config.ADMIN_USER.equals(session.getUserID())) {
				Session systemSession = DirectRepositoryModule.getSystemSession();
				node = session.getRootNode().getNode(nodePath.substring(1));
				sNode = systemSession.getNodeByUUID(node.getUUID());
				
				// Perform scripting
				if (sNode.isNodeType(Scripting.TYPE)) {
					sNode.removeMixin(Scripting.TYPE);
					sNode.save();
				}
				
				// Activity log
				UserActivity.log(session, "REMOVE_SCRIPT", nodePath, null);
			} else {
				throw new AccessDeniedException("Sorry, only for admin user");
			}
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(sNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(sNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(sNode);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("removeScript: void");
	}

	@Override
	public String getScript(String token, String nodePath)
			throws PathNotFoundException, AccessDeniedException,
			RepositoryException {
		log.debug("getScript(" + token + ", " + nodePath + ")");
		String code = null;

		try {
			Session session = SessionManager.getInstance().get(token);
			
			if (Config.ADMIN_USER.equals(session.getUserID())) {
				Node node = session.getRootNode().getNode(nodePath.substring(1));
				
				if (node.isNodeType(Scripting.TYPE)) {
					code = node.getProperty(Scripting.SCRIPT_CODE).getString();
				}
			} else {
				throw new AccessDeniedException("Sorry, only for admin user");
			}
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("getScript: "+code);
		return code;
	}

	/**
	 * Check for scripts and evaluate
	 * 
	 * @param node Node modified (Document or Folder)
	 * @param user User who generated the modification event
	 * @param eventType Type of modification event
	 */
	public static void checkScripts(Node scriptNode, String nodePath, String user, String eventType) {
		log.debug("checkScripts("+scriptNode+", "+nodePath+", "+user+", "+eventType+")");
		
		try {
			checkScriptsHelper(scriptNode, nodePath, user, eventType);
		} catch (ValueFormatException e) {
			e.printStackTrace();
		} catch (javax.jcr.PathNotFoundException e) {
			e.printStackTrace();
		} catch (javax.jcr.RepositoryException e) {
			e.printStackTrace();
		}
		
		log.debug("checkScripts: void");
	}

	/**
	 * Check script helper method for recursion.
	 */
	private static void checkScriptsHelper(Node scriptNode, String nodePath, String user, String eventType) throws javax.jcr.RepositoryException {
		log.debug("checkScriptsHelper("+scriptNode.getPath()+", "+nodePath+", "+user+", "+eventType+")");
				
		if (scriptNode.isNodeType(Folder.TYPE) || scriptNode.isNodeType(Document.TYPE)) {
			if (scriptNode.isNodeType(Scripting.TYPE)) {
				String code = scriptNode.getProperty(Scripting.SCRIPT_CODE).getString();
				
				// Evaluate script
				Interpreter i = new Interpreter();
				try {
					i.set("userId", user);
					i.set("eventType", eventType);
					i.set("nodePath", nodePath);
					i.set("scriptPath", scriptNode.getPath());
					i.eval(code);
				} catch (EvalError e) {
					e.printStackTrace();
				}
			}
			
			// Check for script in parent node
			checkScriptsHelper(scriptNode.getParent(), nodePath, user, eventType);
		}
		
		log.debug("checkScriptsHelper: void");
	}
}
