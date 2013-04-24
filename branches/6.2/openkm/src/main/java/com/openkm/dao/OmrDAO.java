/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2013  Paco Avila & Josep Llort
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
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PUomOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.core.MimeTypeConfig;
import com.openkm.dao.bean.Omr;

public class OmrDAO {
	private static Logger log = LoggerFactory.getLogger(OmrDAO.class);

	private OmrDAO() {}
	
	/**
	 * Create
	 */
	public static long create(Omr om) throws DatabaseException {
		log.debug("create({})", om);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Long id = (Long) session.save(om);
			HibernateUtil.commit(tx);
			log.debug("create: {}", id);
			return id;
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Create template from file
	 */
	public static long createFromFile(File OmrFile, String name, boolean active) throws DatabaseException, IOException {
		log.debug("createOmrTemplate({}, {}, {})", new Object[] { OmrFile, name, active });
		Session session = null;
		Transaction tx = null;
		FileInputStream fis = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			fis = new FileInputStream(OmrFile);
			
			// Fill bean
			Omr om = new Omr();
			om.setName(name);
			om.setTemplateFileName(OmrFile.getName());
			om.setTemplateFileMime(MimeTypeConfig.mimeTypes.getContentType(OmrFile.getName()));
			om.setTemplateFilContent(IOUtils.toByteArray(fis));
			om.setActive(active);
			
			Long id = (Long) session.save(om);
			HibernateUtil.commit(tx);
			log.debug("createFromFile: {}", id);
			return id;
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} catch (IOException e) {
			HibernateUtil.rollback(tx);
			throw e;
		} finally {
			IOUtils.closeQuietly(fis);
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Update template
	 */
	public static void updateTemplate(Omr om) throws DatabaseException {
		log.debug("update({})", om);
		String qs = "select om.fileContent, om.fileName, om.fileName from Omr om where om.id=:id";
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			if (om.getTemplateFileContent() == null || om.getTemplateFileContent().length == 0) {
				Query q = session.createQuery(qs);
				q.setParameter("id", om.getId());
				Object[] data = (Object[]) q.setMaxResults(1).uniqueResult();
				om.setTemplateFilContent((byte[]) data[0]);
				om.setTemplateFileName((String) data[1]);
				om.setTemplateFileMime((String) data[2]);
			}
			
			session.update(om);   
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("update: void");
	}
	
	/**
	 * Delete
	 */
	public static void delete(long omId) throws DatabaseException {
		log.debug("delete({})", omId);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			Omr om = (Omr) session.load(Omr.class, omId);
			session.delete(om);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		log.debug("delete: void");
	}
	
	/**
	 * Find by pk
	 */
	public static Omr findByPk(long omId) throws DatabaseException {
		log.debug("findByPk({})", omId);
		String qs = "from Omr om where om.id=:id";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setLong("id", omId);
			Omr ret = (Omr) q.setMaxResults(1).uniqueResult();
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Find by pk
	 */
	@SuppressWarnings("unchecked")
	public static List<Omr> findAll() throws DatabaseException {
		log.debug("findAll()");
		String qs = "from Omr om order by om.name";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			List<Omr> ret = q.list();
			log.debug("findAll: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}