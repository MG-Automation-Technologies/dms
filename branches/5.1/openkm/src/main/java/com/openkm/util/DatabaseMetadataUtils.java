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

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.DatabaseMetadataDAO;
import com.openkm.dao.bean.DatabaseMetadataType;
import com.openkm.dao.bean.DatabaseMetadataValue;

/**
 * DatabaseMetadataUtils
 * 
 * @author pavila
 */
public class DatabaseMetadataUtils {
	private static Logger log = LoggerFactory.getLogger(DatabaseMetadataUtils.class);
	
	/**
	 * Build a query
	 */
	public static String buildQuery(String table, String filter, String order) throws DatabaseException {
		log.debug("buildQuery({}, {}, {})", new Object[] { table, filter, order });
		StringBuilder sb = new StringBuilder();
		String ret = null;
		
		sb.append("from DatabaseMetadataValue dmv where dmv.table='" + table + "'");
		
		if (filter != null && filter.length() > 0) {			
			sb.append(" and ").append(replaceVirtual(table, filter));
		}
		
		if (order != null && order.length() > 0) {
			sb.append(" ").append(order);
		}
		
		ret = sb.toString();
		log.debug("buildQuery: {}", ret);
		return ret;
	}
	
	/**
	 * Build a query
	 */
	public static String buildUpdate(String table, String filter) throws DatabaseException {
		log.debug("buildUpdate({}, {})", new Object[] { table, filter });
		StringBuilder sb = new StringBuilder();
		String ret = null;
		
		sb.append("update DatabaseMetadataValue dmv");
		
		if (filter != null && filter.length() > 0) {
			sb.append(" ").append(replaceVirtual(table, filter)).append(" ");
		}
		
		sb.append(" where dmv.table=:table");
		
		ret = sb.toString();
		log.debug("buildUpdate: {}", ret);
		return ret;
	}
	
	/**
	 * Build a query
	 */
	public static String buildDelete(String table, String filter) throws DatabaseException {
		log.debug("buildDelete({}, {})", new Object[] { table, filter });
		StringBuilder sb = new StringBuilder();
		String ret = null;
		
		sb.append("delete from DatabaseMetadataValue dmv where dmv.table='" + table + "'");
		
		if (filter != null && filter.length() > 0) {
			sb.append(" and ").append(replaceVirtual(table, filter));
		}
		
		ret = sb.toString();
		log.debug("buildDelete: {}", ret);
		return ret;
	}
	
	/**
	 * Get virtual column string value
	 */
	public static String getString(DatabaseMetadataValue value, String column) throws DatabaseException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<DatabaseMetadataType> types = DatabaseMetadataDAO.findAllTypes(value.getTable());
		
		for (DatabaseMetadataType emt : types) {
			if (emt.getVirtualColumn().equals(column)) {
				return BeanUtils.getProperty(value, emt.getRealColumn());
			}
		}
		
		return null;
	}
	
	/**
	 * Replace virtual columns by real ones
	 */
	private static String replaceVirtual(String table, String filter) throws DatabaseException {
		log.debug("replaceVirtual({}, {})", new Object[] { table, filter });
		String ret = "";
		
		if (filter != null && filter.length() > 0) {
			List<DatabaseMetadataType> types = DatabaseMetadataDAO.findAllTypes(table);
			
			for (DatabaseMetadataType emt : types) {
				filter = filter.toLowerCase().replaceAll(emt.getVirtualColumn().toLowerCase(), emt.getRealColumn().toLowerCase());
			}
			
			ret = filter;
		}
		
		log.debug("replaceVirtual: {}", ret);
		return ret;
	}
}
