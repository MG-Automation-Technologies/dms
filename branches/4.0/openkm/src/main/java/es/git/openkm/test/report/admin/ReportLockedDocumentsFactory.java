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

package es.git.openkm.test.report.admin;

import es.git.openkm.bean.report.admin.ReportLockedDocument;

/**
 * @author jllort
 *
 */
public class ReportLockedDocumentsFactory {
	
	public static java.util.Collection<ReportLockedDocument> generateCollection() {
		
		java.util.Vector<ReportLockedDocument> col = new java.util.Vector<ReportLockedDocument>();
		
		ReportLockedDocument locked1 = new ReportLockedDocument();
		ReportLockedDocument locked2 = new ReportLockedDocument();
		
		locked1.setOwner("user1");
		locked1.setPath("document1");
		
		locked2.setOwner("user2");
		locked2.setPath("document2");
		
		col.add(locked1);
		col.add(locked2);
		
		return col;
	}
}