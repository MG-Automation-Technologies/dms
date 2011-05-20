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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.DatabaseMetadataDAO;
import com.openkm.dao.bean.DatabaseMetadataType;
import com.openkm.dao.bean.DatabaseMetadataValue;
import com.openkm.frontend.client.util.metadata.DatabaseMetadataMap;

/**
 * DatabaseMetadataUtils
 * 
 * @author pavila
 */
public class DatabaseMetadataUtils {
	private static Logger log = LoggerFactory.getLogger(DatabaseMetadataUtils.class);
	private static final String METADATA_COLUMN_NAME_COL00 	= "col00";
	private static final String METADATA_COLUMN_NAME_COL01 	= "col01";
	private static final String METADATA_COLUMN_NAME_COL02 	= "col02";
	private static final String METADATA_COLUMN_NAME_COL03 	= "col03";
	private static final String METADATA_COLUMN_NAME_COL04 	= "col04";
	private static final String METADATA_COLUMN_NAME_COL05 	= "col05";
	private static final String METADATA_COLUMN_NAME_COL06 	= "col06";
	private static final String METADATA_COLUMN_NAME_COL07 	= "col07";
	private static final String METADATA_COLUMN_NAME_COL08 	= "col08";
	private static final String METADATA_COLUMN_NAME_COL09 	= "col09";
	
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
			sb.append(" order by ").append(replaceVirtual(table, order));
		}
		
		ret = sb.toString();
		log.debug("buildQuery: {}", ret);
		return ret;
	}
	
	/**
	 * Build a query
	 */
	public static String buildQuery(String table, String filter) throws DatabaseException {
		log.debug("buildQuery({}, {})", new Object[] { table, filter });
		StringBuilder sb = new StringBuilder();
		String ret = null;
		
		sb.append("from DatabaseMetadataValue dmv where dmv.table='" + table + "'");
		
		if (filter != null && filter.length() > 0) {			
			sb.append(" ").append(replaceVirtual(table, filter));
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
	 * getDatabaseMetadataValueMap
	 * 
	 * @param value values
	 * 
	 * @return
	 * @throws DatabaseException
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static Map<String, String> getDatabaseMetadataValueMap(DatabaseMetadataValue value) throws DatabaseException, 
																  IllegalAccessException, InvocationTargetException, 
																  NoSuchMethodException {
		Map<String, String> map = new HashMap<String, String>();
		List<DatabaseMetadataType> types = DatabaseMetadataDAO.findAllTypes(value.getTable());
		
		for (DatabaseMetadataType emt : types) {
			if (emt.getVirtualColumn().equals(DatabaseMetadataMap.MV_NAME_ID) || 
				emt.getVirtualColumn().equals(DatabaseMetadataMap.MV_NAME_TABLE)) {
				throw new DatabaseException("Virtual column name restriction violated " + 
						                    DatabaseMetadataMap.MV_NAME_ID + " or " + DatabaseMetadataMap.MV_NAME_TABLE);
			}
			map.put(emt.getVirtualColumn(), BeanUtils.getProperty(value, emt.getRealColumn()));
		}
		
		map.put(DatabaseMetadataMap.MV_NAME_TABLE, value.getTable());
		map.put(DatabaseMetadataMap.MV_NAME_ID, ""+value.getId());
		
		return map;
	}
	
	/**
	 * getDatabaseMetadataValueByMap
	 * 
	 * @param map Values map
	 * @return
	 * @throws DatabaseException 
	 */
	public static DatabaseMetadataValue getDatabaseMetadataValueByMap (Map<String, String> map) throws DatabaseException {
		DatabaseMetadataValue dmv = new DatabaseMetadataValue();
		if (!map.isEmpty() && map.containsKey(DatabaseMetadataMap.MV_NAME_TABLE)) {
			dmv.setTable(map.get(DatabaseMetadataMap.MV_NAME_TABLE));
			List<DatabaseMetadataType> types = DatabaseMetadataDAO.findAllTypes(dmv.getTable());
			
			if (map.containsKey(DatabaseMetadataMap.MV_NAME_ID)) {
				dmv.setId(new Double(map.get(DatabaseMetadataMap.MV_NAME_ID)).longValue());
			}
			
			for (String key : map.keySet()) {
				String value = map.get(key);
				for (DatabaseMetadataType emt : types) {
					if (emt.getVirtualColumn().equals(key)) {
						if (emt.getRealColumn().equals(METADATA_COLUMN_NAME_COL00)) {
							dmv.setCol00(value);
						} else if (emt.getRealColumn().equals(METADATA_COLUMN_NAME_COL01)) {
							dmv.setCol01(value);
						} else if (emt.getRealColumn().equals(METADATA_COLUMN_NAME_COL02)) {
							dmv.setCol02(value);
						} else if (emt.getRealColumn().equals(METADATA_COLUMN_NAME_COL03)) {
							dmv.setCol03(value);
						} else if (emt.getRealColumn().equals(METADATA_COLUMN_NAME_COL04)) {
							dmv.setCol04(value);
						} else if (emt.getRealColumn().equals(METADATA_COLUMN_NAME_COL05)) {
							dmv.setCol05(value);
						} else if (emt.getRealColumn().equals(METADATA_COLUMN_NAME_COL06)) {
							dmv.setCol06(value);
						} else if (emt.getRealColumn().equals(METADATA_COLUMN_NAME_COL07)) {
							dmv.setCol07(value);
						} else if (emt.getRealColumn().equals(METADATA_COLUMN_NAME_COL08)) {
							dmv.setCol08(value);
						} else if (emt.getRealColumn().equals(METADATA_COLUMN_NAME_COL09)) {
							dmv.setCol09(value);
						}
					}
				}
			}
		}
		
		return dmv;
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
				String vcol = "\\$" + emt.getVirtualColumn().toLowerCase();
				filter = filter.replaceAll(vcol, emt.getRealColumn().toLowerCase());
			}
			
			ret = filter;
		}
		
		log.debug("replaceVirtual: {}", ret);
		return ret;
	}
}
