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

/**
 * @author pavila
 *
 */
public class PropertyGroup implements Serializable {
	private static final long serialVersionUID = -1042616668984180283L;
	
	public static final String GROUP = "okg";
	public static final String GROUP_URI = "http://www.openkm.org/group/1.0";
	public static final String GROUP_PROPERTY = "okp";
	public static final String GROUP_PROPERTY_URI = "http://www.openkm.org/group/property/1.0";
	
	public static final int INPUT = 1;
	public static final int TEXTAREA = 2;
	public static final int SELECT = 3;
	public static final int SELECT_MULTIPLE = 4;
}