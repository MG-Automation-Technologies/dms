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

package es.git.openkm.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class CachedUserDocumentsKeywords implements Serializable {

	private static final long serialVersionUID = -9193245646856878625L;
	Calendar calendar;
	ArrayList<ArrayList<String>> docsKeywords;

	public CachedUserDocumentsKeywords(ArrayList<ArrayList<String>> docsKeywords) {
		this.docsKeywords = docsKeywords;
		this.calendar = Calendar.getInstance();
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
	
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	
	public ArrayList<ArrayList<String>> getDocsKeywords() {
		return docsKeywords;
	}
	
	public void setDocsKeywords(ArrayList<ArrayList<String>> docsKeywords) {
		this.docsKeywords = docsKeywords;
	}
}
