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
import java.util.Calendar;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Note;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.JcrSessionManager;
import com.openkm.core.LockException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.module.NoteModule;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;

public class DirectNoteModule implements NoteModule {
	private static Logger log = LoggerFactory.getLogger(DirectNoteModule.class);

	@Override
	public void add(String token, String nodePath, String text) throws LockException, 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("add({}, {}, {})", new Object[] { token, nodePath, text });
		Session session = null;
		Node notesNode = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node documentNode = session.getRootNode().getNode(nodePath.substring(1));
			notesNode = documentNode.getNode(Note.LIST);
			Calendar cal = Calendar.getInstance();
			Node note = notesNode.addNode(cal.getTimeInMillis()+"", Note.TYPE);
			note.setProperty(Note.DATE, cal);
			note.setProperty(Note.USER, session.getUserID());
			note.setProperty(Note.TEXT, text);
			notesNode.save();
			
			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(documentNode, session.getUserID(), "ADD_NOTE", text);

			// Activity log
			UserActivity.log(session.getUserID(), "ADD_NOTE", nodePath, text);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(notesNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(notesNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(notesNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(notesNode);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}
		
		log.debug("add: void");
	}

	@Override
	public void remove(String token, String notePath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("remove({}, {})", token, notePath);
		Session session = null;
		Node parentNode = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node noteNode = session.getRootNode().getNode(notePath.substring(1));
			parentNode = noteNode.getParent();
			
			if (session.getUserID().equals(noteNode.getProperty(Note.USER).getString())) {
				noteNode.remove();
				parentNode.save();
			} else {
				throw new AccessDeniedException("Note can only be removed by its creator");
			}
						
			// Activity log
			UserActivity.log(session.getUserID(), "REMOVE_NOTE", notePath, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(parentNode);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}
		
		log.debug("remove: void");
	}
	
	@Override
	public Note get(String token, String notePath) throws LockException, PathNotFoundException,
			AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("get({}, {})", token, notePath);
		Session session = null;
		Note note = new Note();
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node noteNode = session.getRootNode().getNode(notePath.substring(1));
			note.setDate(noteNode.getProperty(Note.DATE).getDate());
			note.setUser(noteNode.getProperty(Note.USER).getString());
			note.setText(noteNode.getProperty(Note.TEXT).getString());
			note.setPath(noteNode.getPath());

			// Activity log
			UserActivity.log(session.getUserID(), "GET_NOTE", notePath, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}
		
		log.debug("get: {}", note);
		return note;
	}

	@Override
	public void set(String token, String notePath, String text) throws LockException, 
			PathNotFoundException, AccessDeniedException, RepositoryException, DatabaseException {
		log.debug("set({}, {}, {})", new Object[] { token, notePath, text });
		Session session = null;
		Node noteNode = null;
		
		if (Config.SYSTEM_READONLY) {
			throw new AccessDeniedException("System is in read-only mode");
		}
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			noteNode = session.getRootNode().getNode(notePath.substring(1));
			
			if (session.getUserID().equals(noteNode.getProperty(Note.USER).getString())) {
				noteNode.setProperty(Note.TEXT, text);
				noteNode.save();
			} else {
				throw new AccessDeniedException("Note can only be modified by its creator");
			}
			
			// Check subscriptions
			DirectNotificationModule.checkSubscriptions(noteNode, session.getUserID(), "SET_NOTE", null);

			// Activity log
			UserActivity.log(session.getUserID(), "SET_NOTE", notePath, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(noteNode);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(noteNode);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.lock.LockException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(noteNode);
			throw new LockException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			JCRUtils.discardsPendingChanges(noteNode);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}
		
		log.debug("set: void");
	}

	@Override
	public List<Note> list(String token, String nodePath) throws PathNotFoundException,
			RepositoryException, DatabaseException {
		log.debug("list({}, {})", token, nodePath);
		List<Note> notes = new ArrayList<Note>();
		Session session = null;
		
		try {
			if (token == null) {
				session = JCRUtils.getSession();
			} else {
				session = JcrSessionManager.getInstance().get(token);
			}
			
			Node nodeNode = session.getRootNode().getNode(nodePath.substring(1));
			Node notesNode = nodeNode.getNode(Note.LIST);
			
			for (NodeIterator nit = notesNode.getNodes(); nit.hasNext(); ) {
				Node noteNode = nit.nextNode();
				Note note = new Note();
				note.setDate(noteNode.getProperty(Note.DATE).getDate());
				note.setUser(noteNode.getProperty(Note.USER).getString());
				note.setText(noteNode.getProperty(Note.TEXT).getString());
				note.setPath(noteNode.getPath());
				notes.add(note);
			}

			// Activity log
			UserActivity.log(session.getUserID(), "LIST_NOTES", nodePath, null);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (token == null) JCRUtils.logout(session);
		}

		log.debug("getChilds: {}", notes);
		return notes;
	}
}
