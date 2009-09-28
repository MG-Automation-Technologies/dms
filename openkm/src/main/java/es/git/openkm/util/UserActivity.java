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

package es.git.openkm.util;

import java.sql.SQLException;
import java.util.Calendar;

import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.core.SessionManager;
import es.git.openkm.dao.ActivityDAO;
import es.git.openkm.dao.bean.Activity;

/**
 * 
 * @author pavila
 */
public class UserActivity {
	private static Logger log = LoggerFactory.getLogger(UserActivity.class);
	private static ActivityDAO dao = ActivityDAO.getInstance();
	
	/**
	 * @param session
	 * @param action
	 * @param node
	 * @param params
	 */
	public static void log(Session session, String action, String item, String params) {
		String token = SessionManager.getInstance().getTokenByUserId(session.getUserID());

		try {
			Activity vo = new Activity();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new java.util.Date());
			vo.setActDate(cal);
			vo.setActUser(session.getUserID());
			vo.setActToken(token);
			vo.setActAction(action);
			vo.setActItem(item);
			vo.setActParams(params);
			log.debug(vo.toString());
			dao.create(vo);
		} catch (SQLException se) {
			log.error(se.getMessage());
		}
	}
}
