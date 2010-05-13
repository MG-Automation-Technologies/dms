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

package com.openkm.servlet;

import java.util.Date;

import javax.jcr.Session;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.SessionInfo;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.RepositoryException;
import com.openkm.core.SessionManager;
import com.openkm.module.direct.DirectAuthModule;
import com.openkm.module.direct.DirectRepositoryModule;
import com.openkm.util.UserActivity;

/**
 * Session Listener Class
 * 
 * @web.listener
 */
public class SessionListener implements HttpSessionListener {
	private static Logger log = LoggerFactory.getLogger(SessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		log.debug("New session created on {} with id {}", new Date(), se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		log.debug("Session destroyed on {} with id {}", new Date(), se.getSession().getId());
		
		try {
			if (Config.SESSION_MANAGER) {
				String token = (String) se.getSession().getAttribute("token");
				SessionInfo si = SessionManager.getInstance().getInfo(token);
				
				if (si != null) {
					// Activity log
					Session system = DirectRepositoryModule.getSystemSession();
					UserActivity.log(system, "SESSION_EXPIRATION", si.getSession().getUserID(), token+", IDLE FROM: "+si.getAccess().getTime());
					new DirectAuthModule().logout(token);
				}
			}
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
		}
	}
}
