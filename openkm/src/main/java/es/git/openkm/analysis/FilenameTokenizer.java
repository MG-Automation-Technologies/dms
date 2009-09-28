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

package es.git.openkm.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.CharTokenizer;

/**
 * @author pavila
 * 
 */
public class FilenameTokenizer extends CharTokenizer {

	/** Construct a new FilenameTokenizer. */
	public FilenameTokenizer(Reader in) {
		super(in);
	}

	@Override
	protected boolean isTokenChar(char c) {
		return Character.isLetterOrDigit(c);
	}

	@Override
	protected char normalize(char c) {
		return Character.toLowerCase(c);
	}
}
