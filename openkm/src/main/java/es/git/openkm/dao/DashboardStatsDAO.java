/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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

package es.git.openkm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.core.Config;
import es.git.openkm.dao.bean.DashboardStats;

public class DashboardStatsDAO extends AbstractDAO {
	private static Logger log = LoggerFactory.getLogger(DashboardStatsDAO.class);
	private static DashboardStatsDAO instance = null;

	/* (non-Javadoc)
	 * @see es.git.openkm.dao.AbstractDAO#getDataSourceName()
	 */
	protected String getDataSourceName() {
		return "java:/OKMDashboardStats"+Config.INSTALL+"DS";
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.dao.AbstractDAO#getTableName()
	 */
	protected String getTableName() {
		return "dashboard_stats";
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.dao.AbstractDAO#getSchema()
	 */
	protected String getSchema() {
		return "dashboard_stats";
	}

	private DashboardStatsDAO() {}
	
	/**
	 * @return
	 */
	public static synchronized DashboardStatsDAO getInstance() { 
		if (instance == null) {
			log.debug("getInstance()");
			instance = new DashboardStatsDAO();
		}
		
		return instance;
	}
	
	/**
	 * @param vo
	 * @throws SQLException
	 */
	public void create(DashboardStats vo) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO dashboard_stats (ds_user, ds_source, ds_node, ds_date) "
				+ "VALUES (?, ?, ?, ?)";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getDsUser());
				stmt.setString(2, vo.getDsSource());
				stmt.setString(3, vo.getDsNode());
				stmt.setTimestamp(4, new Timestamp(vo.getDsDate().getTimeInMillis()));
				stmt.execute();
			} else {
				log.error("Can't connect to dashboard stats database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
	}
	
	/**
	 * @param vo
	 * @throws SQLException
	 */
	public void delete(DashboardStats vo) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM dashboard_stats WHERE ds_user=? AND ds_source=? AND ds_node=?";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, vo.getDsUser());
				stmt.setString(2, vo.getDsSource());
				stmt.setString(3, vo.getDsNode());
				stmt.execute();
			} else {
				log.error("Can't connect to dashboard stats database");
			}
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
	}

	/**
	 * @param filter
	 * @return
	 * @throws SQLException
	 */
	public Collection<DashboardStats> findByUserSource(String user, String source) throws SQLException {
		log.debug("findByUserSource("+user+", "+source+")");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<DashboardStats> al = new ArrayList<DashboardStats>();
		String sql = "SELECT ds_date, ds_node FROM dashboard_stats WHERE ds_user=? AND ds_source=?";

		try {
			con = getConnection();
			
			if (con != null) {
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, user);
				pst.setString(2, source);
				rs = pst.executeQuery();
				
				while (rs.next()) {
					DashboardStats vo = new DashboardStats();
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(rs.getTimestamp(1).getTime());
					vo.setDsDate(cal);
					vo.setDsNode(rs.getString(2));
					al.add(vo);
				}
			} else {
				log.error("Can't connect to dashboard stats database");
			}
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection(con);
		}

		log.debug("findByUserSource: "+al);
		return al;
	}
}
