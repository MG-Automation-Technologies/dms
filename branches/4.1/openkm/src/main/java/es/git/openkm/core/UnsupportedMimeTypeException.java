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

package es.git.openkm.core;

/**
 * @author pavila
 * 
 */
public class UnsupportedMimeTypeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7322363371347282080L;

	public UnsupportedMimeTypeException() {
		super();
	}

	public UnsupportedMimeTypeException(String arg0) {
		super(arg0);
	}

	public UnsupportedMimeTypeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UnsupportedMimeTypeException(Throwable arg0) {
		super(arg0);
	}
}
