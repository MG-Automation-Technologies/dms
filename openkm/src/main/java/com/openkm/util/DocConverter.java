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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.extractor.PdfTextExtractor;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeException;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;
import com.openkm.core.Config;
import com.openkm.core.ConversionException;
import com.openkm.core.DatabaseException;

import freemarker.template.TemplateException;

public class DocConverter {
	private static Logger log = LoggerFactory.getLogger(DocConverter.class);
	private static ArrayList<String> validOpenOffice = new ArrayList<String>();
	private static ArrayList<String> validImageMagick = new ArrayList<String>();
	private static ArrayList<String> validAutoCad = new ArrayList<String>();
	private static DocConverter instance = null;
	private static OfficeManager officeManager = null;
	
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
		
		// AutoCad
		validAutoCad.add(Config.MIME_DXF);
		validAutoCad.add(Config.MIME_DWG);
	}
	
	/**
	 * Retrieve class instance
	 */
	public static synchronized DocConverter getInstance() {
		if (instance == null) {
			instance = new DocConverter();
			
			if (!Config.SYSTEM_OPENOFFICE_PATH.equals("")) {
				log.info("*** Build Office Manager ***");
				log.info("system.openoffice.path=" + Config.SYSTEM_OPENOFFICE_PATH);
				log.info("system.openoffice.tasks=" + Config.SYSTEM_OPENOFFICE_TASKS);
				log.info("system.openoffice.port=" + Config.SYSTEM_OPENOFFICE_PORT);
				
				officeManager = new DefaultOfficeManagerConfiguration()
					.setOfficeHome(Config.SYSTEM_OPENOFFICE_PATH)
					.setMaxTasksPerProcess(Config.SYSTEM_OPENOFFICE_TASKS)
					.setPortNumber(Config.SYSTEM_OPENOFFICE_PORT)
					.buildOfficeManager();
			} else {
				log.warn("system.openoffice.path not configured");
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
		if ((!Config.SYSTEM_OPENOFFICE_PATH.equals("") || !Config.SYSTEM_OPENOFFICE_SERVER.equals("")) 
				&& validOpenOffice.contains(from)) {
			return true;
		} else if (!Config.SYSTEM_IMAGEMAGICK_CONVERT.equals("") && validImageMagick.contains(from)) {
			return true;
		//} else if (!Config.SYSTEM_DWG2DXF.equals("") && validAutoCad.contains(from)) {
		//	return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Test if a MIME document can be converted to SWF
	 */
	public boolean convertibleToSwf(String from) {
		if (!Config.SYSTEM_SWFTOOLS_PDF2SWF.equals("") && (convertibleToPdf(from) || Config.MIME_PDF.equals(from))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Test if a MIME document can be converted to DXF
	 */
	public boolean convertibleToDxf(String from) {
		if (!Config.SYSTEM_DWG2DXF.equals("") && validAutoCad.contains(from)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Convert a document format to another one.
	 */
	public void convert(File inputFile, String mimeType, File outputFile) throws ConversionException {
		log.debug("convert({}, {}, {})", new Object[] { inputFile, mimeType, outputFile });

		if (Config.SYSTEM_OPENOFFICE_PATH.equals("") && Config.SYSTEM_OPENOFFICE_SERVER.equals("")) {
			throw new ConversionException("system.openoffice.path or system.openoffice.server not configured");
		}

		if (!validOpenOffice.contains(mimeType)) {
			throw new ConversionException("Invalid document conversion MIME type: "+mimeType);
		}

		try {
			if (!Config.SYSTEM_OPENOFFICE_PATH.equals("")) {
				// Document conversion managed by local OO instance
				OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
				converter.convert(inputFile, outputFile);
			} else if (!Config.SYSTEM_OPENOFFICE_SERVER.equals("")) {
				// Document conversion managed by remote conversion server
				remoteConvert(inputFile, mimeType, outputFile);
			}
		} catch (OfficeException e) {
			throw new ConversionException("Error converting document: "+e.getMessage());
		}
	}
	
	/**
	 * Handle remote OpenOffice server conversion
	 */
	private void remoteConvert(File inputFile, String mimeType, File outputFile) throws ConversionException {
		PostMethod post = new PostMethod(Config.SYSTEM_OPENOFFICE_SERVER);
		
		try {
			Part[] parts = { 
					new FilePart(inputFile.getName(), inputFile),
					new StringPart("src_mime", mimeType),
					new StringPart("dst_mime", "application/pdf")
				};
			post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
			HttpClient httpclient = new HttpClient();
			int rc = httpclient.executeMethod(post);
			log.info("Response Code: {}", rc);
			
			if (rc == HttpStatus.SC_OK) { 
				FileOutputStream fos = new FileOutputStream(outputFile);
				BufferedInputStream bis = new BufferedInputStream(post.getResponseBodyAsStream());
				IOUtils.copy(bis, fos);
				bis.close();
				fos.close();
			} else {
				throw new IOException("Error in conversion: " + rc);
			}
		} catch (HttpException e) {
			throw new ConversionException("Http exception", e);
		} catch (FileNotFoundException e) {
			throw new ConversionException("File not found exeption", e);
		} catch (IOException e) {
			throw new ConversionException("IO exception", e);
		} finally {
			post.releaseConnection();
		}
	}
	
	/**
	 * Convert document to PDF.
	 */
	public void doc2pdf(InputStream is, String mimeType, File output) throws ConversionException,
			DatabaseException, IOException {
		log.debug("** Convert from {} to PDF **", mimeType);
		File tmp = FileUtils.createTempFileFromMime(mimeType);
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
			throw new ConversionException("Error in "+mimeType+" to PDF conversion", e);
		} finally {
			IOUtils.closeQuietly(fos);
			tmp.delete();
		}
	}
	
	/**
	 * Convert document to TXT.
	 */
	public void doc2txt(InputStream input, String mimeType, File output) throws ConversionException, 
			DatabaseException, IOException {
		log.debug("** Convert from {} to TXT **", mimeType);
		File tmp = FileUtils.createTempFileFromMime(mimeType);
		FileOutputStream fos = new FileOutputStream(tmp);
		
		try {
			long start = System.currentTimeMillis();
			
			if (Config.MIME_PDF.equals(mimeType)) {
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
			throw new ConversionException("Error in "+mimeType+" to TXT conversion", e);
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
	public void img2pdf(InputStream is, String mimeType, File output) throws ConversionException,
			DatabaseException, IOException {
		log.debug("** Convert from {} to PDF **", mimeType);
		File tmpFileIn = FileUtils.createTempFileFromMime(mimeType);
		FileOutputStream fos = null;
		String cmd = null;
	    
		try {
			fos = new FileOutputStream(tmpFileIn);
			IOUtils.copy(is, fos);
			fos.flush();
			fos.close();
			
			// Performs conversion
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("fileIn", tmpFileIn.getPath());
			hm.put("fileOut", output.getPath());
			String tpl = Config.SYSTEM_IMAGEMAGICK_CONVERT + " ${fileIn}[0] ${fileOut}";
			cmd = TemplateUtils.replace("SYSTEM_OCR", tpl, hm);
			ExecutionUtils.runCmd(cmd);
		} catch (SecurityException e) {
			throw new ConversionException("Security exception executing command: " + cmd, e);
    	} catch (InterruptedException e) {
			throw new ConversionException("Interrupted exception executing command: " + cmd, e);
    	} catch (IOException e) {
			throw new ConversionException("IO exception executing command: " + cmd, e);
		} catch (TemplateException e) {
			throw new ConversionException("Template exception", e);
		} finally {
			IOUtils.closeQuietly(fos);
			tmpFileIn.delete();
		}
	}
	
	/**
	 * Convert CAD files to PDF
	 */
	public void cad2pdf(InputStream is, String mimeType, File output) throws ConversionException,
			DatabaseException, IOException {
		log.debug("** Convert from {} to PDF **", mimeType);
		File tmpFileIn = File.createTempFile("okm", ".cad");
		FileOutputStream fos = null;
		String cmd = null;
		
	    try {
			fos = new FileOutputStream(tmpFileIn);
			IOUtils.copy(is, fos);
			fos.flush();
			fos.close();
			
			// Performs conversion
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("fileIn", tmpFileIn.getPath());
			hm.put("fileOut", output.getPath());
			String tpl = "wine " + Config.SYSTEM_DWG2DXF + " /r /ad /lw 1 /f 104 ${fileIn} ${fileOut}"; 
			cmd = TemplateUtils.replace("SYSTEM_DWG2DXF", tpl, hm);
			ExecutionUtils.runCmd(cmd);
	    } catch (SecurityException e) {
			throw new ConversionException("Security exception executing command: " + cmd, e);
    	} catch (InterruptedException e) {
			throw new ConversionException("Interrupted exception executing command: " + cmd, e);
    	} catch (IOException e) {
			throw new ConversionException("IO exception executing command: " + cmd, e);
		} catch (TemplateException e) {
			throw new ConversionException("Template exception", e);
		} finally {
			IOUtils.closeQuietly(fos);
			tmpFileIn.delete();
		}
	}
	
	/**
	 * Convert HTML to PDF
	 */
	public void html2pdf(InputStream is, File output) throws ConversionException,
			DatabaseException, IOException {
		log.debug("** Convert from HTML to PDF **");
		FileOutputStream fos = null;
		
	    try {			
	    	fos = new FileOutputStream(output);
	    	
	    	// Make conversion
			Document doc = new Document(PageSize.A4);
			PdfWriter.getInstance(doc, fos);
			doc.open();
			HTMLWorker html = new HTMLWorker(doc);
			html.parse(new InputStreamReader(is));
			doc.close();
		} catch (DocumentException e) {
			throw new ConversionException("Exception in conversion: " + e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}
	
	/**
	 * Convert PDF to SWF (for document preview feature).
	 */
	public void pdf2swf(File input, File output) throws ConversionException, DatabaseException,
			IOException {
		log.debug("** Convert from PDF to SWF **");
		BufferedReader stdout = null;
		String cmd = null;
		
		try {
			// Performs conversion
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("fileIn", input.getPath());
			hm.put("fileOut", output.getPath());
			String tpl = Config.SYSTEM_SWFTOOLS_PDF2SWF + " -T 9 ${fileIn} -o ${fileOut}";
			cmd = TemplateUtils.replace("SYSTEM_PDF2SWF", tpl, hm);
			ExecutionUtils.runCmd(cmd);
		} catch (SecurityException e) {
			throw new ConversionException("Security exception executing command: " + cmd, e);
    	} catch (InterruptedException e) {
			throw new ConversionException("Interrupted exception executing command: " + cmd, e);
    	} catch (IOException e) {
			throw new ConversionException("IO exception executing command: " + cmd, e);
		} catch (TemplateException e) {
			throw new ConversionException("Template exception", e);
		} finally {
			IOUtils.closeQuietly(stdout);
		}
	}
	
	/**
	 * Convert PDF to IMG (for document preview feature).
	 */
	public void pdf2img(File input, File output) throws ConversionException, DatabaseException,
			IOException {
		log.debug("** Convert from PDF to IMG **");
		File tmpDir = FileUtils.createTempDir();
		String cmd = null;
		
		try {
			// Performs step 1: split pdf into several images
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("fileIn", input.getPath());
			hm.put("fileOut", tmpDir + File.separator + "out.jpg");
			String tpl = Config.SYSTEM_IMAGEMAGICK_CONVERT + " ${fileIn} ${fileOut}";
			cmd = TemplateUtils.replace("SYSTEM_IMG2PDF", tpl, hm);
			ExecutionUtils.runCmd(cmd);
			
			// Performs step 2: join split images into a big one
			hm = new HashMap<String, String>();
			StringBuilder sb = new StringBuilder();
			File files[] = tmpDir.listFiles();
			Arrays.sort(files, new FileOrderComparator());
			
			for (File f : files) {
				sb.append(f.getPath()).append(" ");
			}
			
			hm.put("fileIn", sb.toString());
			hm.put("fileOut", output.getPath());
			tpl = Config.SYSTEM_IMAGEMAGICK_CONVERT + " ${fileIn}-append ${fileOut}";
			cmd = TemplateUtils.replace("SYSTEM_IMG2PDF", tpl, hm);
			ExecutionUtils.runCmd(cmd);
		} catch (SecurityException e) {
			throw new ConversionException("Security exception executing command: " + cmd, e);
    	} catch (InterruptedException e) {
			throw new ConversionException("Interrupted exception executing command: " + cmd, e);
    	} catch (IOException e) {
			throw new ConversionException("IO exception executing command: " + cmd, e);
		} catch (TemplateException e) {
			throw new ConversionException("Template exception", e);
		} finally {
			org.apache.commons.io.FileUtils.deleteQuietly(tmpDir);
		}
	}
	
	/**
	 * User by pdf2img
	 */
	private class FileOrderComparator implements Comparator<File> {
		@Override
		public int compare(File o1, File o2) {
			int o1Ord = Integer.parseInt((o1.getName().split("\\.")[0]).split("-")[1]);
			int o2Ord = Integer.parseInt((o2.getName().split("\\.")[0]).split("-")[1]);
			
			if (o1Ord > o2Ord) return 1;
			else if (o1Ord < o2Ord) return -1;
			else return 0;
		}
	}
	
	/**
	 * Convert DWG to DXF (for document preview feature).
	 * Actually only works with Acme CAD Converter 2010 v8.1.4
	 */
	public void dwg2dxf(File input, File output) throws ConversionException, DatabaseException,
			IOException {
		log.debug("** Convert from DWG to DXF **");
		BufferedReader stdout = null;
		String cmd = null;
	    
		try {
			// Performs conversion
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("fileIn", input.getPath());
			hm.put("fileOut", output.getPath());
			String tpl = "wine " + Config.SYSTEM_DWG2DXF + " /r /ad /x14 ${fileIn} ${fileOut}";
			cmd = TemplateUtils.replace("SYSTEM_DWG2DXF", tpl, hm);
			ExecutionUtils.runCmd(cmd);
		} catch (SecurityException e) {
			throw new ConversionException("Security exception executing command: " + cmd, e);
    	} catch (InterruptedException e) {
			throw new ConversionException("Interrupted exception executing command: " + cmd, e);
    	} catch (IOException e) {
			throw new ConversionException("IO exception executing command: " + cmd, e);
		} catch (TemplateException e) {
			throw new ConversionException("Template exception", e);
		} finally {
			IOUtils.closeQuietly(stdout);
		}
	}
}
