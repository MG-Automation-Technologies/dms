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

/**
 * Show SQL => Logger.getLogger("org.hibernate.SQL").setThreshold(Level.INFO);
 * JBPM Integration => org.jbpm.db.JbpmSessionFactory
 * 
 * @author pavila
 */
public class HibernateUtil {
	private static Logger log = LoggerFactory.getLogger(HibernateUtil.class);
	private static final SessionFactory sessionFactory;
	private static boolean SHOW_SQL = true;
	
	static {
		try {
			if (SHOW_SQL) {
				sessionFactory = new Configuration().configure().setProperty("hibernate.show_sql", "true")
						.buildSessionFactory();
			} else {
				sessionFactory = new Configuration().configure().buildSessionFactory();
			}
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
	 * Rollback transaction
	 */
	public static void rollback(Transaction tx) {
		if (tx != null && tx.isActive()) {
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
