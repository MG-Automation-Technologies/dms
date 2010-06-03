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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

public class LegacyDAO {
	private static Logger log = LoggerFactory.getLogger(LegacyDAO.class);
	private static DataSource ds = null;
	
	/**
	 * Return JDBC Connection
	 */
	public static Connection getConnection() {
		try {
			if (ds == null) {
				log.info("Looking for {} DataSource...", Config.DATASOURCE);
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup(Config.DATASOURCE);
				ctx.close();
			}
			
			return ds.getConnection();
		} catch (NamingException e) {
			log.error("DataSource not found: {}", e.getMessage());
			throw new RuntimeException(e);
		} catch (SQLException e) {
			log.error("Can't get connection from DataSource", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Convenient method to close connections
	 */
	public static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				log.warn("Error closing connection: " + e.getMessage(), e);
			}
		}
	}
	
	/**
	 * Convenient method to close resultset
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.warn("Error closing resultset: " + e.getMessage(), e);
			}
		}
	}
	
	/**
	 * Convenient method to close statements
	 */
	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				log.warn("Error closing statement: " + e.getMessage(), e);
			}
		}	
	}
}
