/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
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

package com.openkm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.extractor.PdfTextExtractor;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeException;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

public class DocConverter {
	private static Logger log = LoggerFactory.getLogger(DocConverter.class);
	private static ArrayList<String> validOpenOffice = new ArrayList<String>();
	private static ArrayList<String> validImageMagick = new ArrayList<String>();
	private static DocConverter instance = null;
	private static OfficeManager officeManager = null;
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
		validImageMagick.add("image/bmp");
		validImageMagick.add("image/svg+xml");
		validImageMagick.add("image/x-psd");
	}
	
	/**
	 * Retrieve class instance
	 */
	public static DocConverter getInstance() {
		if (instance == null) {
			instance = new DocConverter();
			
			if (!Config.SYSTEM_OPENOFFICE_PATH.equals("")) {
				log.info("*** Build Office Manager ***");
				officeManager = new DefaultOfficeManagerConfiguration()
					.setOfficeHome(Config.SYSTEM_OPENOFFICE_PATH)
					.setMaxTasksPerProcess(Config.SYSTEM_OPENOFFICE_TASKS)
					.setPortNumber(Config.SYSTEM_OPENOFFICE_PORT)
					.buildOfficeManager();
			}
		}
		
		return instance;
	}
	
	/**
	 * Start OpenOffice instance
	 */
	public void start() {
		if (officeManager != null) {
			officeManager.start();
		}
	}
	
	/**
	 * Stop OpenOffice instance
	 */
	public void stop() {
		if (officeManager != null) {
			officeManager.stop();
		}
	}
	
	/**
	 * Test if a MIME document can be converted to PDF
	 */
	public boolean convertibleToPdf(String from) {
		if (!Config.SYSTEM_OPENOFFICE_PATH.equals("") && validOpenOffice.contains(from)) {
			return true;
		} else if (!Config.SYSTEM_IMG2PDF.equals("") && validImageMagick.contains(from)) {
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
	 */
	public void convert(File inputFile, String mimeType, File outputFile) throws IOException {
		log.debug("convert({}, {}, {})", new Object[] { inputFile, mimeType, outputFile });

		if (Config.SYSTEM_OPENOFFICE_PATH.equals("")) {
			throw new IOException("system.openoffice not configured");
		}

		if (!validOpenOffice.contains(mimeType)) {
			throw new IOException("Invalid document conversion MIME type: "+mimeType);
		}

		try {
			OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
			converter.convert(inputFile, outputFile);
		} catch (OfficeException e) {
			throw new IOException("Error convertind document: "+e.getMessage());
		}
	}
	
	/**
	 * Convert document to PDF.
	 */
	public void doc2pdf(InputStream is, String mimeType, File output) throws IOException {
		log.debug("** Convert from {} to PDF **", mimeType);
		File tmp = File.createTempFile("okm", ".doc");
		FileOutputStream fos = null;
		
		try {
			long start = System.currentTimeMillis();
			fos = new FileOutputStream(tmp);
			IOUtils.copy(is, fos);
			fos.flush();
			fos.close();
			
			convert(tmp, mimeType, output);
			log.debug("Elapse doc2pdf time: {}", FormatUtil.formatSeconds(System.currentTimeMillis() - start));
		} catch (Exception e) {
			log.error("Error in {} to PDF conversion", mimeType, e);
			output.delete();
			throw new IOException("Error in "+mimeType+" to PDF conversion", e);
		} finally {
			IOUtils.closeQuietly(fos);
			tmp.delete();
		}
	}
	
	/**
	 * Convert document to TXT.
	 */
	public void doc2txt(InputStream input, String mimeType, File output) throws IOException {
		log.debug("** Convert from {} to TXT **", mimeType);
		File tmp = File.createTempFile(input.toString(), ".cnv");
		FileOutputStream fos = new FileOutputStream(tmp);
		
		try {
			long start = System.currentTimeMillis();
			
			if (PDF.equals(mimeType)) {
				Reader r = new PdfTextExtractor().extractText(input, mimeType, "utf-8");
				fos.close();
				fos = new FileOutputStream(output);
				IOUtils.copy(r, fos);
			} else if (validOpenOffice.contains(mimeType)) {
				IOUtils.copy(input, fos);
				fos.flush();
				fos.close();
				convert(tmp, mimeType, output);
			}
			
			log.debug("Elapse doc2txt time: {}", FormatUtil.formatSeconds(System.currentTimeMillis() - start));
		} catch (Exception e) {
			log.error("Error in {} to TXT conversion", mimeType, e);
			output.delete();
			throw new IOException("Error in "+mimeType+" to TXT conversion", e);
		} finally {
			FileUtils.deleteQuietly(tmp);
			IOUtils.closeQuietly(fos);
		}
	}
	
	/**
	 * Convert IMG to PDF (for document preview feature).
	 * 
	 * [0] => http://www.rubblewebs.co.uk/imagemagick/psd.php
	 */
	public void img2pdf(InputStream is, String mimeType, File output) throws IOException {
		log.debug("** Convert from {} to PDF **", mimeType);
		File tmp = File.createTempFile("okm", ".img");
		String inputFile = tmp.getPath()+"[0]";
		FileOutputStream fos = null;
		
		try {
			long start = System.currentTimeMillis();
			fos = new FileOutputStream(tmp);
			IOUtils.copy(is, fos);
			fos.flush();
			fos.close();
			
			ProcessBuilder pb = new ProcessBuilder(Config.SYSTEM_IMG2PDF, inputFile, output.getPath());
			Process process = pb.start();
			process.waitFor();
			String info = IOUtils.toString(process.getInputStream());
			process.destroy();
		
			// Check return code
			if (process.exitValue() == 1) {
				log.warn(info);
			}
			
			log.debug("Elapse img2pdf time: {}", FormatUtil.formatSeconds(System.currentTimeMillis() - start));
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
		String cmd[] = { Config.SYSTEM_PDF2SWF, "-T 9", input.getPath(), "-o", output.getPath() };
		log.debug("Command: {}", Arrays.toString(cmd));
		BufferedReader stdout = null;
	    String line;
	    
		try {
			long start = System.currentTimeMillis();
			ProcessBuilder pb = new ProcessBuilder(cmd);
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
			
			process.destroy();
			log.debug("Elapse pdf2swf time: {}", FormatUtil.formatSeconds(System.currentTimeMillis() - start));
		} catch (Exception e) {
			log.error(Arrays.toString(cmd));
			log.error("Error in PDF to SWF conversion", e);
			output.delete();
			throw new IOException("Error in PDF to SWF conversion", e);
		} finally {
			IOUtils.closeQuietly(stdout);
		}
	}
}
