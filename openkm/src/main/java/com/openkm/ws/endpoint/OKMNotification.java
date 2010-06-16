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

package com.openkm.ws.endpoint;

import java.util.Arrays;
import java.util.Collection;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.jboss.annotation.security.SecurityDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.module.ModuleManager;
import com.openkm.module.NotificationModule;
import com.openkm.ws.util.StringArray;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMNotification"
 * @web.servlet-mapping url-pattern="/OKMNotification"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@SecurityDomain("OpenKM")
public class OKMNotification {
	private static Logger log = LoggerFactory.getLogger(OKMNotification.class);
	
	/* (non-Javadoc)
	 * @see com.openkm.module.NotificationModule#subscribe(java.lang.String, java.lang.String)
	 */
	public void subscribe(String nodePath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("subscribe({})", nodePath);
		NotificationModule nm = ModuleManager.getNotificationModule();
		nm.subscribe(nodePath);
		log.debug("subscribe: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.NotificationModule#unsubscribe(java.lang.String, java.lang.String)
	 */
	public void unsubscribe(String nodePath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("unsubscribe({})", nodePath);
		NotificationModule nm = ModuleManager.getNotificationModule();
		nm.unsubscribe(nodePath);
		log.debug("unsubscribe: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.NotificationModule#getSubscriptors(java.lang.String, java.lang.String)
	 */
	public StringArray getSubscriptors(String nodePath) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException {
		log.debug("getSubscriptors({})", nodePath);
		NotificationModule nm = ModuleManager.getNotificationModule();
		StringArray sa = new StringArray();
		Collection<String> col = nm.getSubscriptors(nodePath);
		sa.setValue(col.toArray(new String[col.size()]));
		log.debug("getSubscriptors: {}", sa);
		return sa;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.NotificationModule#notify(java.lang.String, java.lang.String, java.lang.String[], java.lang.String)
	 */
	public void notify(String nodePath, StringArray users, String message, boolean attachment) throws 
			PathNotFoundException, AccessDeniedException, RepositoryException {
		log.debug("notify({}, {}, {})", new Object[] { nodePath, users, message });
		NotificationModule nm = ModuleManager.getNotificationModule();
		nm.notify(nodePath, Arrays.asList(users.getValue()), message, attachment);
		log.debug("notify: void");
	}
}
