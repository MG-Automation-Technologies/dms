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

package es.git.openkm.ws.endpoint;

import java.util.Arrays;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.module.ModuleManager;
import es.git.openkm.module.NotificationModule;
import es.git.openkm.ws.util.StringArray;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMNotification"
 * @web.servlet-mapping url-pattern="/OKMNotification"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class OKMNotification {
	private static Logger log = LoggerFactory.getLogger(OKMNotification.class);
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.NotificationModule#subscribe(java.lang.String, java.lang.String)
	 */
	public void subscribe(String token, String nodePath) 
			throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("subscribe("+token+", "+nodePath+")");
		NotificationModule nm = ModuleManager.getNotificationModule();
		nm.subscribe(token, nodePath);
		log.debug("subscribe: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.NotificationModule#unsubscribe(java.lang.String, java.lang.String)
	 */
	public void unsubscribe(String token, String nodePath) 
			throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("unsubscribe("+token+", "+nodePath+")");
		NotificationModule nm = ModuleManager.getNotificationModule();
		nm.unsubscribe(token, nodePath);
		log.debug("unsubscribe: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.NotificationModule#getSubscriptors(java.lang.String, java.lang.String)
	 */
	public StringArray getSubscriptors(String token, String nodePath) throws PathNotFoundException,
			AccessDeniedException, RepositoryException {
		log.debug("getSubscriptors("+token+", "+nodePath+")");
		NotificationModule nm = ModuleManager.getNotificationModule();
		StringArray sa = new StringArray();
		sa.setValue(nm.getSubscriptors(token, nodePath).toArray(new String[0]));
		log.debug("getSubscriptors: "+sa);
		return sa;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.NotificationModule#notify(java.lang.String, java.lang.String, java.lang.String[], java.lang.String)
	 */
	public void notify(String token, String nodePath, StringArray users, String message) throws PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("notify("+token+", "+nodePath+", "+users+", "+message+")");
		NotificationModule nm = ModuleManager.getNotificationModule();
		nm.notify(token, nodePath, Arrays.asList(users.getValue()), message);
		log.debug("notify: void");
	}
}
