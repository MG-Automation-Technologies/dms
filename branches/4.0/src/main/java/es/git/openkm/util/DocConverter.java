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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import es.git.openkm.core.Config;

public class DocConverter {
	private static Logger log = LoggerFactory.getLogger(DocConverter.class);
	ArrayList<String> validMimes = new ArrayList<String>();
	
	/**
	 * Initialize valid documents MIME types
	 */
	public DocConverter() {
		if (Config.SYSTEM_OPENOFFICE.equals("on")) {
			// Basic
			validMimes.add("text/plain");
			validMimes.add("text/html");
			validMimes.add("text/csv");
			validMimes.add("application/rtf");
			
			// OpenOffice.org OpenDocument
			validMimes.add("application/vnd.oasis.opendocument.text");
			validMimes.add("application/vnd.oasis.opendocument.presentation");
			validMimes.add("application/vnd.oasis.opendocument.spreadsheet");
			validMimes.add("application/vnd.oasis.opendocument.graphics");
			validMimes.add("application/vnd.oasis.opendocument.database");
			
			// Microsoft Office
			validMimes.add("application/msword");
			validMimes.add("application/vnd.ms-excel");
			validMimes.add("application/vnd.ms-powerpoint");
		}
	}

	/**
	 * Convert a document format to another one.
	 * 
	 * @param mimeFrom Original document MIME type
	 * @param mimeTo Destination document MIME type
	 * @param dataFrom Binary document content
	 * @return Converted binary document content
	 * @throws IOException If anything fails
	 */
	public void convert(InputStream is, String mimeFrom, OutputStream os, String mimeTo) throws IOException {
		log.debug("convert("+is+", "+mimeFrom+", "+os+", "+mimeTo+")");
				
		if (validMimes.contains(mimeFrom)) {
			OpenOfficeConnection connection = null;

			try {
				// Connect to an OpenOffice.org instance running on port 8100
				connection = new SocketOpenOfficeConnection(8100);
				connection.connect();
			
				// Convert
				DocumentFormat dfFrom = new DefaultDocumentFormatRegistry().getFormatByMimeType(mimeFrom);
				DocumentFormat dfTo = new DefaultDocumentFormatRegistry().getFormatByMimeType(mimeTo);
				DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
				converter.convert(is, dfFrom, os, dfTo);

				log.debug("convert: void");
			} finally {
				// close the connection
				if (connection != null && connection.isConnected()) {
					connection.disconnect();
				}
			}
		} else {
			throw new IOException("Invalid document conversion MIME type");
		}
	}
	
	
	/**
	 * Test if a MIME document can be converted to PDF
	 * 
	 * @param mime MIME to test
	 */
	public boolean isValid(String mime) {
		return validMimes.contains(mime);
	}
}
