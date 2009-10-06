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

package es.git.openkm.extractor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.ConnectException;

import org.apache.jackrabbit.extractor.AbstractTextExtractor;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * Text extractor for JPEG image documents.
 * Use java metadata extraction library from 
 * http://www.drewnoakes.com/code/exif/index.html
 */
public class OOTextExtractor extends AbstractTextExtractor {

    /**
     * Logger instance.
     */
    private static final Logger log = LoggerFactory.getLogger(OOTextExtractor.class);
    
    private static OpenOfficeConnection connection = null;

    /**
     * Creates a new <code>JpegTextExtractor</code> instance.
     */
    public OOTextExtractor() {
        super(new String[]{
        		// MsExcel
        		"application/vnd.ms-excel", "application/msexcel",
        		
        		// MsPowerPoint
        		"application/vnd.ms-powerpoint", "application/mspowerpoint",
        		
        		// MsWord
        		"application/vnd.ms-word", "application/msword",
        		
        		// MsOffice2007
        		"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.template",
                "application/vnd.openxmlformats-officedocument.presentationml.template",
                "application/vnd.openxmlformats-officedocument.presentationml.slideshow",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.template"
        		});
    }
    
    //-------------------------------------------------------< TextExtractor >

    /**
     * {@inheritDoc}
     */ 
	@SuppressWarnings("unchecked")
	public Reader extractText(InputStream stream, String type, String encoding) throws IOException {
		String ret = "";
		
    	try {
    		// Convert to application/pdf
			DocumentFormat dfFrom = new DefaultDocumentFormatRegistry().getFormatByMimeType(type);
			DocumentFormat dfTo = new DefaultDocumentFormatRegistry().getFormatByMimeType("application/pdf");
			DocumentConverter converter = new OpenOfficeDocumentConverter(getConnection());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			converter.convert(stream, dfFrom, baos, dfTo);

			// Convert to text/plain
			baos.flush();
			PDFParser parser = new PDFParser(new ByteArrayInputStream(baos.toByteArray()));
			
			try {
                parser.parse();
                PDDocument document = parser.getPDDocument();
                CharArrayWriter writer = new CharArrayWriter();
                
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setLineSeparator("\n");
                stripper.writeText(document, writer);
                
    			ret = new String(writer.toCharArray());
            } finally {
                try {
                    PDDocument doc = parser.getPDDocument();
                    if (doc != null) {
                        doc.close();
                    }
                } catch (IOException e) {
                    // ignore
                }
            }
    		
            baos.close();
    		log.debug("TEXT: "+ret);
            return new StringReader(ret);
		} finally {
			stream.close();
        }
    }
	
	/**
	 * @return
	 * @throws ConnectException
	 */
	private static OpenOfficeConnection getConnection() throws ConnectException {
		if (connection == null) {
	        // Connect to an OpenOffice.org instance running on port 8100
			connection = new SocketOpenOfficeConnection(8100);
			connection.connect();
		}
		
		return connection;
	}
	
	/**
	 * 
	 */
	private static void closeConnection() {
		// close the connection
		if (connection != null && connection.isConnected()) {
			connection.disconnect();
		}
	}
}
