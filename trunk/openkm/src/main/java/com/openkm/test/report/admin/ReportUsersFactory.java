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

package com.openkm.test.report.admin;

import com.openkm.bean.report.admin.ReportUser;

/**
 * @author jllort
 *
 */
public class ReportUsersFactory {
	
	public static java.util.Collection<ReportUser> generateCollection() {
		
		java.util.Vector<ReportUser> col = new java.util.Vector<ReportUser>();
		
		ReportUser user1 = new ReportUser();
		ReportUser user2 = new ReportUser();
		
		user1.setActive(true);
		user1.setEmail("user1@openkm.com");
		user1.setId("user1");
		
		user2.setActive(false);
		user2.setEmail("user2@openkm.com");
		user2.setId("user2");
		
		col.add(user1);
		col.add(user2);
		
		return col;
	}
}