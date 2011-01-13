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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.extractor.AbstractTextExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.Config;
import com.openkm.util.FileUtils;
import com.openkm.util.FormatUtil;

/**
 * Text extractor for image documents.
 * Use OCR from https://code.launchpad.net/cuneiform-linux
 */
public class CuneiformTextExtractor extends AbstractTextExtractor {

    /**
     * Logger instance.
     */
    private static final Logger log = LoggerFactory.getLogger(CuneiformTextExtractor.class);
    
    /**
     * Creates a new <code>TextExtractor</code> instance.
     */
    public CuneiformTextExtractor() {
        super(new String[] { "image/tiff", "image/gif", "image/jpg", "image/png" });
    }
    
    //-------------------------------------------------------< TextExtractor >

    /**
     * {@inheritDoc}
     */ 
    public Reader extractText(InputStream stream, String type, String encoding) throws IOException {
    	BufferedReader stdout = null;
    	File tmpFileIn = null;
    	File tmpFileOut = null;
    	String cmd[] = null;
    	String line;
    	
		if (!Config.SYSTEM_OCR.equals("")) {
			try {
    			// Create temp file
    			tmpFileIn = FileUtils.createTempFileFromMime(type);
    			tmpFileOut = File.createTempFile("okm", ".txt");
    			FileOutputStream fos = new FileOutputStream(tmpFileIn);
    			IOUtils.copy(stream, fos);
    			fos.close();
    			
    			// Performs OCR
    			long start = System.currentTimeMillis();
    			cmd = new String[] { Config.SYSTEM_OCR, tmpFileIn.getPath(), "-o", tmpFileOut.getPath() };
    			log.info("Command: {}", Arrays.toString(cmd));
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
    			log.debug("Elapse time: {}", FormatUtil.formatSeconds(System.currentTimeMillis() - start));
    			
    			// Read result
    			String text = IOUtils.toString(new FileInputStream(tmpFileOut.getPath()));
    			log.debug("TEXT: "+text);
    			return new StringReader(text);
			} catch (Exception e) {
				log.warn(Arrays.toString(cmd));
				log.warn("Failed to extract OCR text", e);
				return new StringReader("");
			} finally {
				IOUtils.closeQuietly(stream);
				IOUtils.closeQuietly(stdout);
				tmpFileIn.delete();
				tmpFileOut.delete();
				new File(tmpFileOut.getPath()+".txt").delete();
			}
		} else {
			log.warn("Undefined OCR application");
			return new StringReader("");
		}
    }
}
