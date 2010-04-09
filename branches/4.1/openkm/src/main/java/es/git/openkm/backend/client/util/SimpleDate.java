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

package es.git.openkm.backend.client.util;

import java.util.Date;

public class SimpleDate {
	
	Date date;

	public SimpleDate() {
		date = new Date();
	}
	
	public SimpleDate(Date date) {
		this.date = date;
	}
	
	@SuppressWarnings("deprecation")
	public SimpleDate(int year, int month, int day) {
		this.date = new Date(year-1900, month-1, day);
	}
	
	public int compareTo(Object other) {
		Date otherDate = (Date)other;
		return date.compareTo(otherDate);
	}
	
	public String toString(String format) {
		if (format.equals("DD/MM/YYYY HH:MM:SS")) {
			return formatDate_DDMMYYYYHHMMSS(this.date);
		} else if (format.equals("HH:MM:SS DD/MM/YYYY")) {
			return formatDate_HHMMSSDDMMYYYY(this.date);
		} else if (format.equals("DD/MM/YYYY")) {
			return formatDate_DDMMYYYY(this.date);
		} else if (format.equals("MM/DD/YYYY")) {
			return formatDate_MMDDYYYY(this.date);
		} else {
			return formatDate_DDMONYYYY(this.date);
		}
	}

	/*
	 * formatDate_DDMONYYYY
	 * 
	 * Formats the date in DD/MON/YYYY format
	 * 
	 * @param (Date to be formatted)
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	private String formatDate_DDMONYYYY(Date date) {
		String[] MONTHS = {
			"Jan",
			"Feb",
			"Mar",
			"Apr",
			"May",
			"Jun",
			"Jul",
			"Aug",
			"Sep",
			"Oct",
			"Nov",
			"Dec"
		};		
		StringBuffer dateStr = new StringBuffer();
		
		if(date.getDate() < 10) {
			dateStr.append("0");
		}
		
		dateStr.append(date.getDate());
		dateStr.append(" ");
		
		dateStr.append(MONTHS[date.getMonth()]);
		dateStr.append(" ");
		
		dateStr.append((date.getYear()+1900));
		return dateStr.toString();
	}			
	
	/*
	 * formatDate_MMDDYYYY
	 * 
	 * Formats the date in MM/DD/YYYY format
	 * 
	 * @param (Date to be formatted)
	 * @return String
	 */
	@SuppressWarnings({"deprecation"})
	private String formatDate_MMDDYYYY (Date date) {
		StringBuffer strDate = new StringBuffer();
		
		if (date.getMonth() < 9) {
			strDate.append("0");
		}
		
		strDate.append(date.getMonth()+1);
		strDate.append("/");
		
		if (date.getDate() < 10) {
			strDate.append("0");
		}
		
		strDate.append(date.getDate());
		strDate.append("/");
		strDate.append((date.getYear()+1900));
		
		return strDate.toString();
	}
	
	/*
	 * formatDate_DDMMYYYY
	 * 
	 * Formats the date in DD/MM/YYYY format
	 * 
	 * @param (Date to be formatted)
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	private String formatDate_DDMMYYYY (Date date) {
		StringBuffer strDate = new StringBuffer();
		
		if (date.getDate() < 10) {
			strDate.append("0");
		}
		
		strDate.append(date.getDate());
		strDate.append("/");
		
		if (date.getMonth() < 9) {
			strDate.append("0");
		}
		
		strDate.append(date.getMonth()+1);
		strDate.append("/");
		strDate.append((date.getYear()+1900));
		
		return strDate.toString();
	}
	
	/*
	 * formatDate_HHMMSSDDMMYYY
	 * 
	 * Formats the date in HH:MM:SS DD/MM/YYY format
	 * 
	 * @param (Date to be formatted)
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	private String formatDate_HHMMSSDDMMYYYY (Date date) {
		StringBuffer strDate = new StringBuffer();
		
		if (date.getHours() < 10) {
			strDate.append("0");
		}
		
		strDate.append(date.getHours());
		strDate.append(":");
		
		if (date.getMinutes() < 10) {
			strDate.append("0");
		}
		
		strDate.append(date.getMinutes());
		strDate.append(":");
		
		if (date.getSeconds() < 10) {
			strDate.append("0");
		}
		
		strDate.append(date.getSeconds());
		strDate.append(" ");
		
		if (date.getDate() < 10) {
			strDate.append("0");
		}
		
		strDate.append(date.getDate());
		strDate.append("/");
		
		if(date.getMonth() < 9){
			strDate.append("0");
		}
		
		strDate.append(date.getMonth()+1);
		strDate.append("/");
		strDate.append((date.getYear()+1900));
		
		return strDate.toString();
	}
	
	/*
	 * formatDate_DDMMYYYYHHMMSS
	 * 
	 * Formats the date in HH:MM:SS DD/MM/YYY format
	 * 
	 * @param (Date to be formatted)
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	private String formatDate_DDMMYYYYHHMMSS (Date date) {
		StringBuffer strDate = new StringBuffer();
		
		if (date.getDate() < 10) {
			strDate.append("0");
		}
		
		strDate.append(date.getDate());
		strDate.append("/");
		
		if(date.getMonth() < 9){
			strDate.append("0");
		}
		
		strDate.append(date.getMonth()+1);
		strDate.append("/");
		strDate.append((date.getYear()+1900));
		strDate.append(" ");

		if (date.getHours() < 10) {
			strDate.append("0");
		}
		
		strDate.append(date.getHours());
		strDate.append(":");
		
		if (date.getMinutes() < 10) {
			strDate.append("0");
		}
		
		strDate.append(date.getMinutes());
		strDate.append(":");
		
		if (date.getSeconds() < 10) {
			strDate.append("0");
		}
		
		strDate.append(date.getSeconds());

		return strDate.toString();
	}
}