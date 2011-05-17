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

package com.openkm.util;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.openkm.core.DatabaseException;
import com.openkm.dao.DatabaseMetadataDAO;
import com.openkm.dao.HibernateUtil;
import com.openkm.dao.bean.DatabaseMetadataType;


/**
 * DatabaseMetadataUtils
 * 
 * @author jllort
 *
 */
public class DatabaseMetadataUtils {
	/**
	 * Build a query
	 */
	public static String buildQuery(String table, String filter, String order) {
		StringBuilder sb = new StringBuilder();
		String ret = null;
		
		sb.append("from DatabaseMetadataValue dmv where dmv.table='" + table + "'");
		
		if (filter != null && filter.length() > 0) {			
			sb.append(" and ").append(filter);
		}
		
		if (order != null && order.length() > 0) {
			sb.append(" ").append(order);
		}
		
		ret = sb.toString();
		return ret;
	}
	
	/**
	 * Build a query
	 */
	public static String buildUpdate(String table, String filter) {
		StringBuilder sb = new StringBuilder();
		String ret = null;
		
		sb.append("update DatabaseMetadataValue dmv");
		
		if (filter != null && filter.length() > 0) {
			sb.append(" ").append(filter).append(" ");
		}
		
		sb.append(" where dmv.table=:table");
		
		ret = sb.toString();
		return ret;
	}
	
	/**
	 * Build a query
	 */
	public static String buildDelete(String table, String filter) {
		StringBuilder sb = new StringBuilder();
		String ret = null;
		
		sb.append("delete from DatabaseMetadataValue dmv where dmv.table='" + table + "'");
		
		if (filter != null && filter.length() > 0) {
			sb.append(" and ").append(filter);
		}
		
		ret = sb.toString();
		return ret;
	}
	
	/**
	 * Replace virtual columns by real ones
	 */
	public static String replaceVirtual(String table, String filter) throws DatabaseException {
		Session session = null;
		String ret = "";
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			if (filter != null && filter.length() > 0) {
				List<DatabaseMetadataType> types = DatabaseMetadataDAO.findAllTypes(table);
				
				for (DatabaseMetadataType emt : types) {
					filter = filter.toLowerCase().replaceAll(emt.getVirtualColumn().toLowerCase(), emt.getRealColumn().toLowerCase());
				}
				
				ret = filter;
			}
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
		
		return ret;
	}
}
