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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

public abstract class AbstractDAO {
	private static Logger log = LoggerFactory.getLogger(AbstractDAO.class);
	private DataSource ds = null;
	protected abstract String getDataSourceName();
	protected abstract String getTableName();
	protected abstract String getSchema();
	
	protected static void close(Transaction tx) {
		if (tx != null && tx.isActive()) {
	        try {
	          tx.rollback();
	        } catch (HibernateException e1) {
	        	log.debug("Error rolling back transaction");
	        }
		}
	}
	
	/**
	 * 
	 */
	protected AbstractDAO() {
		try {
			checkSchema();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @return
	 */
	public Connection getConnection() {
		Connection con = null;

		try {
			if (Config.IN_SERVER) {
				if (ds == null) {
					log.info("Looking for "+getDataSourceName()+" DataSource...");
					Context ctx = new InitialContext();
					ds = (DataSource) ctx.lookup(getDataSourceName());
					ctx.close();
				}
			
				con = ds.getConnection();
			}
		} catch (NamingException ne) {
			log.warn("DataSource not found: "+ne.getMessage());
		} catch (SQLException se) {
			log.error("Can't get connection from DataSource", se);
			throw new RuntimeException(se);
		}

		return con;
	}

	/**
	 * Common utilities 
	 */
	public void closeConnection(final Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException se) {
				log.error("closeConnection: "+con.toString(), se);
			}
		}
	}

	/**
	 * @param stmt
	 */
	public void closeStatement(final Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException se) {
				log.error("closeStatement: "+stmt.toString(), se);
			}
		}
	}

	/**
	 * @param rs
	 */
	public void closeResultSet(final ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException se) {
				log.error("closeResultSet: "+rs.toString(), se);
			}
		}
	}
	
	/**
	 * Checks if the required schema objects exist and creates them if they
	 * don't exist yet.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	protected void checkSchema() throws Exception {
		log.debug("checkSchema()");
		Connection con = getConnection();
		
		if (con != null) {
			DatabaseMetaData metaData = con.getMetaData();
			String tableName = getTableName();
			String schema = getSchema();

			if (metaData.storesLowerCaseIdentifiers()) {
				tableName = tableName.toLowerCase();
			} else if (metaData.storesUpperCaseIdentifiers()) {
				tableName = tableName.toUpperCase();
			}

			ResultSet rs = metaData.getTables(null, null, tableName, null);
			boolean schemaExists;
			try {
				schemaExists = rs.next();
			} finally {
				rs.close();
			}

			if (!schemaExists) {
				log.info("Creating schema "+schema+"...");
				
				// read ddl from resources
				InputStream in = AbstractDAO.class.getResourceAsStream(schema + ".ddl");
				if (in == null) {
					String msg = "Configuration error: unknown schema '" + schema + "'";
					log.debug(msg);
					throw new Exception(msg);
				}
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				Statement stmt = con.createStatement();
				try {
					String sql = reader.readLine();
					while (sql != null) {
						// Skip comments and empty lines
						if (!sql.startsWith("#") && sql.length() > 0) {
							stmt.executeUpdate(sql);
						}
						sql = reader.readLine();
					}
				} finally {
					closeStream(in);
					closeStatement(stmt);
				}
			}
			
			closeConnection(con);
		}
	}
	
	/**
	 * @param in
	 */
	protected void closeStream(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException ie) {
				log.error("closeStream: "+in.toString(), ie);
			}
		}
	}
}
