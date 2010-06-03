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

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Activity;
import com.openkm.dao.bean.ActivityFilter;

public class ActivityDAO  {
	private static Logger log = LoggerFactory.getLogger(ActivityDAO.class);

	private ActivityDAO() {}
	
	/**
	 * Create activity
	 */
	public static void create(Activity activity) throws DatabaseException {
	    try {
	    	HibernateHelper.getSession().save(activity);
	    } catch (HibernateException e) {
	    	throw new DatabaseException(e.getMessage(), e);
	    }
	}
	
	/**
	 * Find by filter
	 */
	@SuppressWarnings("unchecked")
	public static List<Activity> findByFilter(ActivityFilter filter) throws DatabaseException {
		log.debug("findByFilter({})", filter);
		String qs = "SELECT act_date, act_user, act_token, act_action, act_item, act_params "+
			"FROM activity WHERE act_date BETWEEN ? AND ? ";
		if (filter.getActUser() != null && !filter.getActUser().equals("")) 
			qs += "AND act_user=? ";
		if (filter.getActAction() != null && !filter.getActAction().equals("")) 
			qs += "AND act_action=? ";

		try {
			Query q = HibernateHelper.getSession().createQuery(qs);
			q.setTimestamp(1, new Timestamp(filter.getActDateBegin().getTimeInMillis()));
			q.setTimestamp(2, new Timestamp(filter.getActDateEnd().getTimeInMillis()));
			int pCount = 3;
			
			if (filter.getActUser() != null && !filter.getActUser().equals("")) 
				q.setString(pCount++, filter.getActUser());
			if (filter.getActAction() != null && !filter.getActAction().equals(""))
				q.setString(pCount++, filter.getActAction());
		
			List<Activity> ret = q.list();
			log.debug("findByFilter: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	/**
	 * Get activity date
	 */
	@SuppressWarnings("unchecked")
	public static Calendar getActivityDate(String user, String action, String item) throws 
			DatabaseException {
		log.debug("getActivityDate({}, {}, {})", new Object[] { user, action, item });
		String qsAct = "select max(a.date) from Activity a " +
			"where a.user= :user and a.action= :action and a.item= :item";
		String qsNoAct = "select max(a.date) from Activity a " +
			"where (a.action='CREATE_DOCUMENT' or a.action='SET_DOCUMENT_CONTENT') and a.item= :item";
		
		try {
			Query q = null;
			
			if (action != null) {
				q = HibernateHelper.getSession().createQuery(qsAct);
				q.setString("user", user);
				q.setString("action", action);
				q.setString("item", item);
			} else {
				q = HibernateHelper.getSession().createQuery(qsNoAct);
				q.setString("item", item);
			}
			
			List<Activity> results = q.list();
			Calendar ret = null;
			
			if (results.size() == 1) {
				if (results.get(0).getDate() != null) {
					ret = results.get(0).getDate();
				} else {
					// May be the document has been moved or renamed? 
					ret = Calendar.getInstance();
				}
			}
			
			log.debug("getActivityDate: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}
