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

import java.util.Calendar;
import java.util.Iterator;
import java.util.TimerTask;

import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.SessionInfo;
import es.git.openkm.module.direct.DirectAuthModule;
import es.git.openkm.module.direct.DirectRepositoryModule;
import es.git.openkm.util.UserActivity;

public class Watchdog extends TimerTask {
	private static Logger log = LoggerFactory.getLogger(Watchdog.class);

	public void run() {
		log.debug("*** Watchdog activated ***");
        SessionManager sm = SessionManager.getInstance();
        
        for (Iterator<String> it = sm.getTokens().iterator(); it.hasNext(); ) {
			String token = it.next();
			SessionInfo si = sm.getInfo(token);
			Calendar expiration = (Calendar) si.getAccess().clone();
			expiration.add(Calendar.SECOND, Config.SESSION_EXPIRATION);
			log.debug(si.getSession().getUserID()+", Expiration: "+expiration.getTime());
			
			if (Calendar.getInstance().after(expiration)) {
				try {
					// Activity log
					Session system = DirectRepositoryModule.getSystemSession();
					UserActivity.log(system, "SESSION_EXPIRATION", si.getSession().getUserID(), token+", IDLE FROM: "+si.getAccess().getTime());
					new DirectAuthModule().logout(token);
				} catch (AccessDeniedException e) {
					log.error(e.getMessage(), e);
				} catch (RepositoryException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}
}
