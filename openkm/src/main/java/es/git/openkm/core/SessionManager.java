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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.SessionInfo;
import es.git.openkm.util.UUIDGenerator;

/**
 *
 * 
 * @author pavila
 */
public class SessionManager {
	private static Logger log = LoggerFactory.getLogger(SessionManager.class);
	private static SessionManager instance;
	private static String systemToken;
	private HashMap<String, SessionInfo> sessions = new HashMap<String, SessionInfo>();

	/**
	 * Instantiate a SessionManager.
	 * 
	 * @return
	 */
	public static SessionManager getInstance() {
		log.debug("getInstance()");
		if (instance == null) {
			instance = new SessionManager();
		}
		
		log.debug("getInstance: "+instance);
		return instance;
	}
	
	/**
	 * @return
	 */
	public String getSystemToken() {
		return systemToken;
	}
	
	/**
	 * @param systemSession
	 */
	public void putSystem(Session systemSession) {
		systemToken = UUIDGenerator.generate(this);
		put(systemToken, systemSession);
	}
	
	/**
	 * Put a new Session in the HashMap.
	 * 
	 * @param token
	 * @param session
	 */
	public void put(String token, Session session) {
		log.debug("put("+token+" ,"+session+")");
		SessionInfo si = new SessionInfo();
		si.setSession(session);
		si.setCreation(Calendar.getInstance());
		si.setAccess(Calendar.getInstance());
		sessions.put(token, si);
		log.debug("put: void");
	}
	
	/**
	 * Obtains a previous stored Session in the HashMap.
	 * 
	 * @param token
	 * @return
	 */
	public Session get(String token) {
		log.debug("get("+token+")");
		SessionInfo si = (SessionInfo) sessions.get(token);
		Session session = null;
		
		if (si != null) {
			si.setAccess(Calendar.getInstance());
			session = si.getSession();
		}
		
		log.debug("get: "+session);
		return session;
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	public SessionInfo getInfo(String token) {
		log.debug("get("+token+")");
		SessionInfo si = (SessionInfo) sessions.get(token);
		log.debug("get: "+si);
		return si;
	}
	
	/**
	 * Remove a previous stored Session from the HashMap
	 * 
	 * @param token
	 */
	public void remove(String token) {
		log.debug("remove("+token+")");
		sessions.remove(token);
		log.debug("remove: void");
	}
	
	/**
	 * Return all active tokens
	 */
	public Collection<String> getTokens() {
		log.debug("getActiveTokens()");
		ArrayList<String> list = new ArrayList<String>();
		
		for (Iterator<String> it = sessions.keySet().iterator(); it.hasNext(); ) {
			String token = it.next();
			
			if (!systemToken.equals(token)) {
				list.add(token);
			}
		}
		
		log.debug("getActiveTokens: "+list);
		return list;
	}

	/**
	 * Get session using user id.
	 * 
	 * @param token
	 */
	public String getTokenByUserId(String userId) {
		log.debug("getTokenByUserId("+userId+")");
		String token = null;
		
		for (Iterator<Entry<String, SessionInfo>> it = sessions.entrySet().iterator(); it.hasNext(); ) {
			Entry<String, SessionInfo> entry = it.next();
			Session session = ((SessionInfo) entry.getValue()).getSession();
			
			if (userId.equals(session.getUserID())) {
				token = (String) entry.getKey();
			}
		}
		
		log.debug("getTokenByUserId: "+token);
		return token;
	}

	public String toString() {
		return sessions.toString();
	}
}
