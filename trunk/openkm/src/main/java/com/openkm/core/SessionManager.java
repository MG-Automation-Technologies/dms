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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.SessionInfo;

/**
 * @author pavila
 */
public class SessionManager {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(SessionManager.class);
	private static SessionManager instance = new SessionManager();
	private List<SessionInfo> sessions = new ArrayList<SessionInfo>();

	/**
	 * Prevents class instantiation
	 */
	private SessionManager() {
	}
	
	/**
	 * Instantiate a SessionManager.
	 */
	public static SessionManager getInstance() {
		return instance;
	}
	
	/**
	 * Add a new session
	 */
	public synchronized void add(HttpServletRequest request) {
		SessionInfo si = new SessionInfo();
		HttpSession s = request.getSession();
		boolean add = true;
		
		for (SessionInfo rsi : sessions) {
			if (rsi.getId().equals(s.getId())) {
				add = false;
			}
		}
		
		if (add) {
			si.setUser(request.getRemoteUser());
			si.setIp(request.getRemoteAddr());
			si.setHost(request.getRemoteHost());
			si.setId(s.getId());
			Calendar creation = Calendar.getInstance();
			creation.setTimeInMillis(s.getCreationTime());
			si.setCreation(creation);
			Calendar lastAccessed = Calendar.getInstance();
			lastAccessed.setTimeInMillis(s.getLastAccessedTime());
			si.setLastAccessed(lastAccessed);
			
			s.setAttribute("user", request.getRemoteUser());
			sessions.add(si);
		}
	}
	
	/**
	 * Update session last accessed time
	 */
	public synchronized void update(String id) {
		for (SessionInfo si : sessions) {
			if (si.getId().equals(id)) {
				si.setLastAccessed(Calendar.getInstance());
			}
		}
	}
	
	/**
	 * Remove a session
	 */
	public synchronized void remove(String id) {
		for (Iterator<SessionInfo> it  = sessions.iterator(); it.hasNext(); ) {
			SessionInfo si = it.next();
			
			if (si.getId().equals(id)) {
				it.remove();
				break;
			}
		}
	}
	
	/**
	 * Return a session info
	 */
	public SessionInfo getSession(String id) {
		for (SessionInfo si : sessions) {
			if (si.getId().equals(id)) {
				return si;
			}
		}
		return null;
	}
		
	/**
	 * Return all active sessions
	 */
	public List<SessionInfo> getSessions() {
		return sessions;
	}
}
