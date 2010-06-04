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
 * Hibernate session helper
 * Logger.getLogger("org.hibernate.SQL").setThreshold(Level.INFO);
 * @author pavila
 */
public class HibernateHelper {
	private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
	private static final boolean SHOW_SQL = true;
	private static SessionFactory sessionFactory = null;
	
	static {
		if (SHOW_SQL) {
			sessionFactory = new Configuration().configure().setProperty("hibernate.show_sql", "true").buildSessionFactory();
		} else {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
	}
	
	/**
	 * Disable constructor to guaranty a single instance
	 */
	private HibernateHelper() {
	}
	
	/**
	 * Get instance
	 */
	public static Session getSession() {
		Session session = (Session) HibernateHelper.session.get();
		if (session == null) {
			session = sessionFactory.openSession();
			HibernateHelper.session.set(session);
		}
		return session;
	}
}
