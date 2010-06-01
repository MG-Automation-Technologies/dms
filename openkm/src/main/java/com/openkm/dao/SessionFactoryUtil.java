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

package com.openkm.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * See org.jbpm.persistence.db.DbPersistenceServiceFactory
 * See http://www.java2s.com/Code/Java/Hibernate/CollectionMappingManyToManymapbasedonHashMap.htm
 * 
 * @author pavila
 */
public class SessionFactoryUtil {
	private static SessionFactory sessionFactory = null;

	static {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	/**
	 * Disable constructor to guaranty a single instance
	 */
	private SessionFactoryUtil() {
	}
	
	/**
	 * Get instance
	 */
	public static SessionFactory getInstance() {
		return sessionFactory;
	}
	
	/**
	 * Opens a session and will not bind it to a session context
	 */
	public Session openSession() {
		return sessionFactory.openSession();
	}
	
	/**
	 * Returns a session from the session context. If there is no session in the context it opens a session,
	 * stores it in the context and returns it.
	 * This factory is intended to be used with a hibernate.cfg.xml
	 * including the following property <property
	 * name="current_session_context_class">thread</property> This would return
	 * the current open session or if this does not exist, will create a new
	 * session
	 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Closes the session factory
	 */
	public static void close() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
		sessionFactory = null;
	}
}
