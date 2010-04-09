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

package es.git.openkm.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author pavila
 *
 */
public class FormatUtil {
	
	/**
	 * Detect if the current browser is a mobile one
	 */
	public static final boolean isMobile(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent").toLowerCase();
		return userAgent.contains("android") || userAgent.contains("iphone");
	}
	
	/**
	 * Format the document size for human readers
	 */
	public static String formatSize(long size) {
		DecimalFormat df = new DecimalFormat("#0.0");
		String str;
		
		if (size / 1024 < 1) {
			str = size + " B";
		} else if (size / 1048576 < 1) {
			str = df.format(size / 1024.0) + " KB";
		} else if (size / 1073741824 < 1) {
			str = df.format(size / 1048576.0) + " MB";
		} else if (size /  1099511627776L < 1) {
			str = df.format(size / 1073741824.0) + " GB";
		} else {
			str = "BIG";
		}
		
		return str;
	}

	/**
	 * Format time for human readers
	 */
	public static String formatTime(long time) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss.SSS"); 
		String str = df.format(time);
		return str;
	}
	
	/**
	 * Format time interval for humans 
	 */
	public static String formatSeconds(long time) {
		long hours, minutes, seconds, mseconds;
		mseconds = time % 1000;
		time = time / 1000;
		hours = time / 3600;
		time = time - (hours * 3600);
		minutes = time / 60;
		time = time - (minutes * 60);
		seconds = time;
		return (hours<10?"0"+hours:hours)+":"+
			(minutes<10?"0"+minutes:minutes)+":"+
			(seconds<10?"0"+seconds:seconds)+"."+
			(mseconds<10?"00"+mseconds:(mseconds<100?"0"+mseconds:mseconds));
	}
	
	/**
	 * Format calendar date
	 */
	public static String formatDate(Calendar cal) {
		return cal.getTime().toString();
	}
	
	/**
	 * Format string array
	 */
	public static String formatArray(String[] values) {
		if (values != null) {
			if (values.length == 1) {
				return values[0];
			} else {
				return ArrayUtils.toString(values);
			}
		} else {
			return "NULL";
		}
	}
	
	/**
	 * Format object
	 */
	public static String formatObject(Object value) {
		if (value != null) {
			if (value instanceof Object[]) {
				return ArrayUtils.toString(value);
			} else {
				return value.toString();
			}
		} else {
			return "NULL";
		}
	}
}
