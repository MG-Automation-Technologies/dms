/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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
import java.util.Collections;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.QueryTranslator;
import org.hibernate.hql.QueryTranslatorFactory;
import org.hibernate.hql.ast.ASTQueryTranslatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;
import com.openkm.dao.bean.DatabaseMetadataSequence;
import com.openkm.dao.bean.DatabaseMetadataType;
import com.openkm.dao.bean.DatabaseMetadataValue;

/**
 * Show SQL => Logger.getLogger("org.hibernate.SQL").setThreshold(Level.INFO);
 * JBPM Integration => org.jbpm.db.JbpmSessionFactory
 * 
 * @author pavila
 */
public class HibernateUtil {
	private static Logger log = LoggerFactory.getLogger(HibernateUtil.class);
	private static SessionFactory sessionFactory;
	
	/**
	 * Disable constructor to guaranty a single instance
	 */
	private HibernateUtil() {}
	
	/**
	 * Get instance
	 */
	public static SessionFactory getSessionFactory() {
		return getSessionFactory(Config.HIBERNATE_HBM2DDL);
	}
	
	/**
	 * Get instance
	 */
	public static SessionFactory getSessionFactory(String hbm2ddl) {
		if (sessionFactory == null) {
			try {
				AnnotationConfiguration ac = new AnnotationConfiguration();
				
				// Add annotated beans
				ac.addAnnotatedClass(DatabaseMetadataType.class);
				ac.addAnnotatedClass(DatabaseMetadataValue.class);
				ac.addAnnotatedClass(DatabaseMetadataSequence.class);
				
				// Configure Hibernate
				Configuration cfg = ac.configure();
				cfg.setProperty("hibernate.dialect", Config.HIBERNATE_DIALECT);
				cfg.setProperty("hibernate.connection.datasource", Config.HIBERNATE_DATASOURCE);
				cfg.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
				cfg.setProperty("hibernate.show_sql", Config.HIBERNATE_SHOW_SQL);
				cfg.setProperty("hibernate.generate_statistics", Config.HIBERNATE_STATISTICS);
				
				// Show configuration
				log.info("Hibernate 'hibernate.dialect' = {}", cfg.getProperty("hibernate.dialect"));
				log.info("Hibernate 'hibernate.connection.datasource' = {}", cfg.getProperty("hibernate.connection.datasource"));
				log.info("Hibernate 'hibernate.hbm2ddl.auto' = {}", cfg.getProperty("hibernate.hbm2ddl.auto"));
				log.info("Hibernate 'hibernate.show_sql' = {}", cfg.getProperty("hibernate.show_sql"));
				log.info("Hibernate 'hibernate.generate_statistics' = {}", cfg.getProperty("hibernate.generate_statistics"));
				
				sessionFactory = cfg.buildSessionFactory();
			} catch (HibernateException e) {
				log.error(e.getMessage(), e);
				throw new ExceptionInInitializerError(e);
			}
		}
		
		return sessionFactory;
	}
	
	/**
	 * Close factory
	 */
	public static void closeSessionFactory() {
		if (sessionFactory != null) {
			sessionFactory.close();
			sessionFactory = null;
		}
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
	
	/**
	 * HQL to SQL translator
	 */
	 public static String toSql(String hql) {
		 if (hql != null && hql.trim().length() > 0) {
			 final QueryTranslatorFactory qtf = new ASTQueryTranslatorFactory();
			 final SessionFactoryImplementor sfi = (SessionFactoryImplementor) sessionFactory;
			 final QueryTranslator translator = qtf.createQueryTranslator(hql, hql, Collections.EMPTY_MAP, sfi);
			 translator.compile(Collections.EMPTY_MAP, false);
			 return translator.getSQLString(); 
		 }
		 
		 return null;
	 }
}