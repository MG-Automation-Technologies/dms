/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2012 Paco Avila & Josep Llort
 * 
 * No bytes were intentionally harmed during the development of this application.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Css;

/**
 * CssDAO
 * 
 * @author jllort
 * 
 */
public class CssDAO extends GenericDAO<Css, Long>{
	private static Logger log = LoggerFactory.getLogger(CssDAO.class);
	private static CssDAO single = new CssDAO();
	
	private CssDAO() {
	}
	
	public static CssDAO getInstance() {
		return single;
	}
	
	/**
	 * Find by name and type
	 */
	public Css findByNameAndType(String name, String context) throws DatabaseException {
		log.debug("findByPk({},{})", name, context);
		String qs = "from Css style where style.name=:name and style.context=:context";
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("name", name);
			q.setString("context", context);
			Css ret = (Css) q.setMaxResults(1).uniqueResult();
			log.debug("findByNameAndType: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}