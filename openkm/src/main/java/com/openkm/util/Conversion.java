package com.openkm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

public class Conversion {
	private static Logger log = LoggerFactory.getLogger(Conversion.class);
	private static final String PDF = "application/pdf";
	/**
	 * 
	 */
	public boolean isConvertible(String from, String to) {
		if (from.equals(PDF)) {
			if (Config.SYSTEM_PDF2SWF.equals("")) {
				return false;
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Convert document to PDF.
	 */
	public static void doc2pdf(InputStream is, String mimeType, File output) throws IOException {
		log.info("** Convert from "+mimeType+" to PDF **");
		try {
			DocConverter dc = DocConverter.getInstance();
			FileOutputStream os = new FileOutputStream(output);
			dc.convert(is, mimeType, os, "application/pdf");
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
	 * Convert PDF to SWF (for document preview feature).
	 */
	public static void pdf2swf(File input, File output) throws IOException {
		log.info("** Convert from PDF to SWF **");
		try {
			ProcessBuilder pb = new ProcessBuilder(Config.SYSTEM_PDF2SWF, input.getPath(), "-o", output.getPath());
			Process process = pb.start();
			process.waitFor();
			String info = IOUtils.toString(process.getInputStream());
			process.destroy();
		
			// Check return code
			if (process.exitValue() == 1) {
				log.warn(info);
			}
		} catch (Exception e) {
			log.error("Error in PDF to SWF conversion", e);
			output.delete();
			throw new IOException("Error in PDF to SWF conversion", e);
		}
	}
}
