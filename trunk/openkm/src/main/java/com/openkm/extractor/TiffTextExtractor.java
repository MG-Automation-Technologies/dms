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

package com.openkm.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.extractor.AbstractTextExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;

/**
 * Text extractor for TIFF image documents.
 * Use OCR from http://code.google.com/p/tesseract-ocr/ 
 */
public class TiffTextExtractor extends AbstractTextExtractor {

    /**
     * Logger instance.
     */
    private static final Logger log = LoggerFactory.getLogger(TiffTextExtractor.class);
    
    /**
     * Creates a new <code>TiffTextExtractor</code> instance.
     */
    public TiffTextExtractor() {
        super(new String[]{"image/tiff"});
    }
    
    //-------------------------------------------------------< TextExtractor >

    /**
     * {@inheritDoc}
     */ 
    public Reader extractText(InputStream stream, String type, String encoding) throws IOException {
    	File tmpFileIn = null;
    	File tmpFilePre = null;
    	File tmpFileOut = null;

		if (!Config.SYSTEM_OCR.equals("")) {
			try {
    			// Create temp file
    			tmpFileIn = File.createTempFile("okm", ".tif");
    			tmpFilePre = File.createTempFile("okm", ".tif");
    			tmpFileOut = File.createTempFile("okm", "");
    			FileOutputStream fos = new FileOutputStream(tmpFileIn);
    			IOUtils.copy(stream, fos);
    			fos.close();

    			// Performs image pre-processing
    			log.debug("CMD: convert -depth 8 -monochrome "+tmpFileIn.getPath()+" "+tmpFilePre.getPath());
    			ProcessBuilder pb = new ProcessBuilder("convert", "-depth", "8", "-monochrome", tmpFileIn.getPath(), tmpFilePre.getPath());
    			Process process = pb.start();
    			process.waitFor();
    			process.destroy();
    			
    			// Performs OCR
    			log.debug("CMD: "+Config.SYSTEM_OCR+" "+tmpFilePre.getPath()+" "+tmpFileOut.getPath());
    			pb = new ProcessBuilder(Config.SYSTEM_OCR, tmpFilePre.getPath(), tmpFileOut.getPath());
    			process = pb.start();
    			process.waitFor();
    			process.destroy();
    			
    			// Read result
    			String text = IOUtils.toString(new FileInputStream(tmpFileOut.getPath()+".txt"));
    		
    			log.debug("TEXT: "+text);
    			return new StringReader(text);
			} catch (Exception e) {
				log.warn("Failed to extract OCR text", e);
				return new StringReader("");
			} finally {
				stream.close();
				tmpFileIn.delete();
				tmpFilePre.delete();
				tmpFileOut.delete();
				new File(tmpFileOut.getPath()+".txt").delete();
			}
		} else {
			log.warn("Undefined OCR application");
			return new StringReader("");
		}
    }
}
