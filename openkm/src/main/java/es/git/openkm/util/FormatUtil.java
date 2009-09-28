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

import java.text.DecimalFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

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
		DecimalFormat sf = new DecimalFormat("#0.0");
		String str;
		
		if (size / 1024 < 1) {
			str = size + " B";
		} else if (size / 1048576 < 1) {
			str = sf.format(size / 1024.0) + " KB";
		} else if (size / 1073741824 < 1) {
			str = sf.format(size / 1048576.0) + " MB";
		} else if (size /  1099511627776L < 1) {
			str = sf.format(size / 1073741824.0) + " GB";
		} else {
			str = "BIG";
		}
					
		return str;
	}
	
	/**
	 * Format calendar date
	 */
	public static String formatDate(Calendar cal) {
		return cal.getTime().toString();
	}
}
