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

package com.openkm.test.report.admin;

import com.openkm.bean.report.admin.ReportSubscribedDocuments;

/**
 * @author jllort
 *
 */
public class ReportSubscribedDocumentsFactory {
	
	public static java.util.Collection<ReportSubscribedDocuments> generateCollection() {
		
		java.util.Vector<ReportSubscribedDocuments> col = new java.util.Vector<ReportSubscribedDocuments>();
		
		ReportSubscribedDocuments subscriptor1 = new ReportSubscribedDocuments();
		ReportSubscribedDocuments subscriptor2 = new ReportSubscribedDocuments();
		
		subscriptor1.setPath("document1");
		subscriptor1.setSubscriptors("user1, user2, user3");
		
		subscriptor2.setPath("document2");
		subscriptor2.setSubscriptors("user1, user3");
		
		col.add(subscriptor1);
		col.add(subscriptor2);
		
		return col;
	}
}