/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

package com.openkm.frontend.client.util;


/**
 * DatabaseMetadataMap
 * 
 * @author jllort
 *
 */
public class DatabaseMetadataMap {
	
	/**
	 * getIntValue
	 * 
	 * @param value
	 * @return
	 */
	public static int getIntValue(String value) {
		return Integer.parseInt(value);
	}
	
	/**
	 * mapIntValue
	 * 
	 * @param value
	 * @return
	 */
	public static String mapIntValue(int value) {
		String mappedValue = String.valueOf(value);
		switch (mappedValue.length()) {
			case 0:
				mappedValue = "0000000000" + mappedValue; // Never might step into
				break;
			case 1:
				mappedValue = "000000000" + mappedValue;
				break;
			case 2:
				mappedValue = "00000000" + mappedValue;
				break;
			case 3:
				mappedValue = "0000000" + mappedValue;
				break;
			case 4:
				mappedValue = "000000" + mappedValue;
				break;
			case 5:
				mappedValue = "00000" + mappedValue;
				break;
			case 6:
				mappedValue = "0000" + mappedValue;
				break;
			case 7:
				mappedValue = "000" + mappedValue;
				break;
			case 8:
				mappedValue = "00" + mappedValue;
				break;
			case 9:
				mappedValue = "0" + mappedValue;
				break;
			case 10:
				// Never might step into
				break;
		}

		return mappedValue;
	}
}