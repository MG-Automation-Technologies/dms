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

import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.DashboardStats;

public class DashboardStatsDAO {
	private static Logger log = LoggerFactory.getLogger(DashboardStatsDAO.class);

	private DashboardStatsDAO() {}
	
	/**
	 * Get dashboard stats
	 */
	@SuppressWarnings("unchecked")
	public DashboardStats findByPk(int dsId) throws DatabaseException {
		log.debug("findByPk({})", dsId);
		String qs = "from DashboardStats ds where ds.id= :id";
		
		try {
			Query q = HibernateHelper.getSession().createQuery(qs);
			q.setInteger("id", dsId);
			List<DashboardStats> results = q.list(); // uniqueResult
			DashboardStats ret = null;
			
			if (results.size() == 1) {
				ret = results.get(0);
			}
			
			log.debug("findByPk: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	/**
	 * Create dashboard stats
	 */
	public static void create(DashboardStats dashboardStats) throws DatabaseException {
		try {
			HibernateHelper.getSession().save(dashboardStats);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	/**
	 * Delete dashboard stats
	 */
	public void delete(int dsId) throws DatabaseException {
		try {
			DashboardStats ds = findByPk(dsId);
			HibernateHelper.getSession().update(ds);
			HibernateHelper.getSession().delete(ds);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	/**
	 * Find by user source
	 */
	@SuppressWarnings("unchecked")
	public static List<DashboardStats> findByUserSource(String user, String source) throws 
			DatabaseException {
		log.debug("findByUserSource({}, {})", user, source);
		String qs = "from DashboardStats ds where ds.user= :user and ds.source= :source";

		try {
			Query q = HibernateHelper.getSession().createQuery(qs);
			q.setString("user", user);
			q.setString("source", source);
			List<DashboardStats> ret = q.list();
			log.debug("findByUserSource: "+ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	/**
	 * Delete visited nodes
	 */
	public static void deleteVisitedNodes(String user, String source) throws DatabaseException {
		log.debug("deleteVisitedNodes({}, {})", user, source);
		String qs = "delete from DashboardStats ds where ds.user= :user and ds.source= :source";

		try {
			Query q = HibernateHelper.getSession().createQuery(qs);
			q.setString("user", user);
			q.setString("source", source);
			q.executeUpdate();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("deleteVisitedNodes: void");
	}
	
	/**
	 * Delete old visited node
	 */
	public static void purgeOldVisitedNode(String user, String source, String node, Calendar date) throws 
			DatabaseException {
		log.debug("purgeOldVisitedNode({}, {}, {}, {})", new Object[] { user, source, node, date });
		String qs = "delete from DashboardStats ds where ds.user= :user and ds.source= :source "+
			"and ds.node= :node and ds.date= :date";
		
		try {
			Query q = HibernateHelper.getSession().createQuery(qs);
			q.setString("user", user);
			q.setString("source", source);
			q.setString("node", node);
			q.setCalendar("date", date);
			q.executeUpdate();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		log.debug("purgeOldVisitedNode: void");
	}
}
