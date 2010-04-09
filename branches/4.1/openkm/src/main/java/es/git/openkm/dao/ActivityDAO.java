/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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
import es.git.openkm.dao.bean.Activity;
import es.git.openkm.dao.bean.ActivityFilter;

public class ActivityDAO extends AbstractDAO {
	private static Logger log = LoggerFactory.getLogger(ActivityDAO.class);
	private static ActivityDAO instance = null;

	/* (non-Javadoc)
	 * @see es.git.openkm.dao.AbstractDAO#getDataSourceName()
	 */
	protected String getDataSourceName() {
		return "java:/OKMActivity"+Config.INSTALL+"DS";
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.dao.AbstractDAO#getTableName()
	 */
	protected String getTableName() {
		return "activity";
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.dao.AbstractDAO#getSchema()
	 */
	protected String getSchema() {
		return "activity";
	}

	private ActivityDAO() {}
	
	/**
	 * @return
	 */
	public static synchronized ActivityDAO getInstance() { 
		if (instance == null) {
			log.debug("getInstance()");
			instance = new ActivityDAO();
		}
		
		return instance;
	}
	
	/**
	 * @param vo
	 * @throws SQLException
	 */
	public void create(Activity vo) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO activity (act_date, act_user, act_token, act_action, act_item, act_params) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		try {
			con = getConnection();
			
			if (con != null) {
				stmt = con.prepareStatement(sql);
				stmt.setTimestamp(1, new Timestamp(vo.getActDate().getTimeInMillis()));
				stmt.setString(2, vo.getActUser());
				stmt.setString(3, vo.getActToken());
				stmt.setString(4, vo.getActAction());
				stmt.setString(5, vo.getActItem());
				stmt.setString(6, vo.getActParams());
				stmt.execute();
			} else {
				log.info("["+vo.getActUser()+", "+vo.getActToken()+"] "+vo.getActAction()+" - "+vo.getActItem()+" ("+vo.getActParams()+") ");
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
	public Collection<Activity> findByFilter(ActivityFilter filter) throws SQLException {
		log.debug("findByFilter("+filter+")");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Activity> al = new ArrayList<Activity>();
		String sql = "SELECT act_date, act_user, act_token, act_action, act_item, act_params "+
			"FROM activity WHERE act_date BETWEEN ? AND ? ";

		if (filter.getActUser() != null && !filter.getActUser().equals("")) 
			sql += "AND act_user=? ";
		if (filter.getActAction() != null && !filter.getActAction().equals("")) 
			sql += "AND act_action=? ";

		try {
			con = getConnection();
			
			if (con != null) {
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setTimestamp(1, new Timestamp(filter.getActDateBegin().getTimeInMillis()));
				pst.setTimestamp(2, new Timestamp(filter.getActDateEnd().getTimeInMillis()));
				int pCount = 3;
				
				if (filter.getActUser() != null && !filter.getActUser().equals("")) 
					pst.setString(pCount++, filter.getActUser());
				if (filter.getActAction() != null && !filter.getActAction().equals(""))
					pst.setString(pCount++, filter.getActAction());
				
				rs = pst.executeQuery();
				
				while (rs.next()) {
					Activity vo = new Activity();
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(rs.getTimestamp(1).getTime());
					vo.setActDate(cal);
					vo.setActUser(rs.getString(2));
					vo.setActToken(rs.getString(3));
					vo.setActAction(rs.getString(4));
					vo.setActItem(rs.getString(5));
					vo.setActParams(rs.getString(6));
					al.add(vo);
				}
			} else {
				log.error("Can't connect to activity database");
			}
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection(con);
		}

		log.debug("findByFilter: "+al);
		return al;
	}
}
