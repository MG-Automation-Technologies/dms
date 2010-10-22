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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

/**
 * Show SQL => Logger.getLogger("org.hibernate.SQL").setThreshold(Level.INFO);
 * JBPM Integration => org.jbpm.db.JbpmSessionFactory
 * 
 * @author pavila
 */
public class HibernateUtil {
	private static Logger log = LoggerFactory.getLogger(HibernateUtil.class);
	private static final SessionFactory sessionFactory;
	
	static {
		try {
			Configuration cfg = new Configuration().configure();
			cfg.setProperty("hibernate.dialect", Config.HIBERNATE_DIALECT);
			cfg.setProperty("hibernate.connection.datasource", Config.HIBERNATE_DATASOURCE);
			cfg.setProperty("hibernate.hbm2ddl.auto", Config.HIBERNATE_HBM2DDL);
			cfg.setProperty("hibernate.show_sql", Config.HIBERNATE_SHOW_SQL);
			sessionFactory = cfg.buildSessionFactory();
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	/**
	 * Disable constructor to guaranty a single instance
	 */
	private HibernateUtil() {
	}
	
	/**
	 * Get instance
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Close session
	 */
	public static void close(Session session) {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}
	
	/**
	 * Commit transaction
	 */
	public static void commit(Transaction tx) {
		if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
			tx.commit();
		}
	}
	
	/**
	 * Rollback transaction
	 */
	public static void rollback(Transaction tx) {
		if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
			tx.rollback();
		}
	}

	/**
	 * Convert from Blob to byte array
	 */
	public static byte[] toByteArray(Blob fromImageBlob) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			byte buf[] = new byte[4000];
			int dataSize;
			InputStream is = fromImageBlob.getBinaryStream();
			
			try {
				while((dataSize = is.read(buf)) != -1) {
					baos.write(buf, 0, dataSize);
				}
			} finally {
				if(is != null) {
					is.close();
				}
			}
			
			return baos.toByteArray();
		} catch (Exception e) {
		}
		
		return null;
	}
}
