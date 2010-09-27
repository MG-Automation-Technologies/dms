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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.BasicDocumentFormatRegistry;
import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import es.git.openkm.core.Config;

public class DocConverter {
	private static Logger log = LoggerFactory.getLogger(DocConverter.class);
	private static ArrayList<String> validOpenOffice = new ArrayList<String>();
	private static ArrayList<String> validImageMagick = new ArrayList<String>();
	private static BasicDocumentFormatRegistry registry =  new DefaultDocumentFormatRegistry();
	private static DocConverter instance = new DocConverter();
	public static final String PDF = "application/pdf";
	public static final String SWF = "application/x-shockwave-flash";

	private DocConverter() {
		// Basic
		validOpenOffice.add("text/plain");
		validOpenOffice.add("text/html");
		validOpenOffice.add("text/csv");
		validOpenOffice.add("application/rtf");
		
		// OpenOffice.org OpenDocument
		validOpenOffice.add("application/vnd.oasis.opendocument.text");
		validOpenOffice.add("application/vnd.oasis.opendocument.presentation");
		validOpenOffice.add("application/vnd.oasis.opendocument.spreadsheet");
		validOpenOffice.add("application/vnd.oasis.opendocument.graphics");
		validOpenOffice.add("application/vnd.oasis.opendocument.database");
		
		// Microsoft Office
		validOpenOffice.add("application/msword");
		validOpenOffice.add("application/vnd.ms-excel");
		validOpenOffice.add("application/vnd.ms-powerpoint");
		
		// Microsoft Office 2007
		validOpenOffice.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		validOpenOffice.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		validOpenOffice.add("application/vnd.openxmlformats-officedocument.presentationml.presentation");
		
		// Images
		validImageMagick.add("image/jpeg");
		validImageMagick.add("image/png");
		validImageMagick.add("image/gif");
		validImageMagick.add("image/tiff");
		
		// Add new document types
		DocumentFormat docx = new DocumentFormat("Microsoft Word 2007 XML", DocumentFamily.TEXT,
				"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
		registry.addDocumentFormat(docx);
		
		DocumentFormat xlsx = new DocumentFormat("Microsoft Excel 2007 XML", DocumentFamily.SPREADSHEET,
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
		registry.addDocumentFormat(xlsx);
		
		DocumentFormat pptx = new DocumentFormat("Microsoft PowerPoint 2007 XML", DocumentFamily.PRESENTATION,
				"application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");
		registry.addDocumentFormat(pptx);
	}
	
	/**
	 * Retrieve class instance
	 */
	public static DocConverter getInstance() {
		return instance;
	}
	
	/**
	 * Test if a MIME document can be converted to PDF
	 */
	public boolean convertibleToPdf(String from) {
		if (Config.SYSTEM_OPENOFFICE.equals("on") && validOpenOffice.contains(from)) {
			return true;
		} else if (!Config.SYSTEM_CONVERT.equals("") && validImageMagick.contains(from)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Test if a MIME document can be converted to SWF
	 */
	public boolean convertibleToSwf(String from) {
		if (!Config.SYSTEM_PDF2SWF.equals("") && (convertibleToPdf(from) || PDF.equals(from))) {
			return true;
		}
	
		return false;
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
				
		if (validOpenOffice.contains(mimeFrom)) {
			OpenOfficeConnection connection = null;

			try {
				// Connect to an OpenOffice.org instance running on port 8100
				connection = new SocketOpenOfficeConnection(8100);
				connection.connect();
			
				// Workaround for wrong Rich Tech Format MIME type in JODConverter
				if (mimeFrom.equals("application/rtf")) {
					mimeFrom = "text/rtf";
				}
				
				// Convert
				DocumentFormat dfFrom = registry.getFormatByMimeType(mimeFrom);
				DocumentFormat dfTo = registry.getFormatByMimeType(mimeTo);
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
	 * Convert document to PDF.
	 */
	public void doc2pdf(InputStream is, String mimeType, File output) throws IOException {
		log.info("** Convert from "+mimeType+" to PDF **");
		try {
			FileOutputStream os = new FileOutputStream(output);
			instance.convert(is, mimeType, os, PDF);
			os.flush();
			os.close();
			is.close();
		} catch (Exception e) {
			log.error("Error in "+mimeType+" to PDF conversion", e);
			output.delete();
			throw new IOException("Error in "+mimeType+" to PDF conversion", e);
		}
	}
	
	/**
	 * Convert IMG to PDF (for document preview feature).
	 */
	public void img2pdf(InputStream is, String mimeType, File output) throws IOException {
		log.info("** Convert from "+mimeType+" to PDF **");
		File tmp = File.createTempFile("okm", ".img");
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(tmp);
			IOUtils.copy(is, fos);
			fos.flush();
			fos.close();
			ProcessBuilder pb = new ProcessBuilder(Config.SYSTEM_CONVERT, tmp.getPath(), output.getPath());
			Process process = pb.start();
			process.waitFor();
			String info = IOUtils.toString(process.getInputStream());
			process.destroy();
		
			// Check return code
			if (process.exitValue() == 1) {
				log.warn(info);
			}
		} catch (Exception e) {
			log.error("Error in IMG to PDF conversion", e);
			output.delete();
			throw new IOException("Error in IMG to PDF conversion", e);
		} finally {
			IOUtils.closeQuietly(fos);
			tmp.delete();
		}
	}
	
	/**
	 * Convert PDF to SWF (for document preview feature).
	 */
	public void pdf2swf(File input, File output) throws IOException {
		log.debug("** Convert from PDF to SWF **");
		String cmd = Config.SYSTEM_PDF2SWF+" -f -T 9 -t -G -s storeallcharacters "+input.getPath()+" -o "+output.getPath();
		log.debug("Command: {}", cmd);
		BufferedReader stdout = null;
	    String line;
	    
		try {
			long start = System.currentTimeMillis();
			ProcessBuilder pb = new ProcessBuilder(cmd.split(" "));
			Process process = pb.start();
			stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			while ((line = stdout.readLine()) != null) {
				log.debug("STDOUT: {}", line);
			}
			
			process.waitFor();	
			
			// Check return code
			if (process.exitValue() != 0) {
				log.warn("Abnormal program termination: {}" + process.exitValue());
				log.warn("STDERR: {}", IOUtils.toString(process.getErrorStream()));
			} else {
				log.debug("Normal program termination");
			}
			
			log.debug("Elapse pdf2swf time: {}", FormatUtil.formatSeconds(System.currentTimeMillis() - start));
		} catch (Exception e) {
			log.error(cmd);
			log.error("Error in PDF to SWF conversion", e);
			output.delete();
			throw new IOException("Error in PDF to SWF conversion", e);
		} finally {
			IOUtils.closeQuietly(stdout);
		}
	}
}
