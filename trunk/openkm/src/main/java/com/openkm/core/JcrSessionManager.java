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

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pavila
 */
public class JcrSessionManager {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(JcrSessionManager.class);
	private static JcrSessionManager instance = new JcrSessionManager();
	private Map<String, Session> sessions = new HashMap<String, Session>();

	/**
	 * Prevents class instantiation
	 */
	private JcrSessionManager() {
	}
	
	/**
	 * Instantiate a SessionManager.
	 */
	public static JcrSessionManager getInstance() {
		return instance;
	}
	
	/**
	 * Add a new session
	 */
	public synchronized void add(String token, Session session) {
		sessions.put(token, session);
	}
	
	/**
	 * Remove a session
	 */
	public synchronized void remove(String token) {
		sessions.remove(token);
	}
	
	/**
	 * Return a session info
	 */
	public Session getSession(String token) {
		return sessions.get(token);
	}
		
	/**
	 * Return all active sessions
	 */
	public Map<String, Session> getSessions() {
		return sessions;
	}
}
