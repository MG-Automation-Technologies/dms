/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.apache.jackrabbit.extractor.HTMLTextExtractor;
import org.apache.jackrabbit.extractor.MsExcelTextExtractor;
import org.apache.jackrabbit.extractor.MsOutlookTextExtractor;
import org.apache.jackrabbit.extractor.MsPowerPointTextExtractor;
import org.apache.jackrabbit.extractor.MsWordTextExtractor;
import org.apache.jackrabbit.extractor.OpenOfficeTextExtractor;
import org.apache.jackrabbit.extractor.PlainTextExtractor;
import org.apache.jackrabbit.extractor.PngTextExtractor;
import org.apache.jackrabbit.extractor.RTFTextExtractor;
import org.apache.jackrabbit.extractor.TextExtractor;
import org.apache.jackrabbit.extractor.XMLTextExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.util.ReaderInputStream;
import com.openkm.util.UserActivity;

public class RegisteredExtractors {
	private static Logger log = LoggerFactory.getLogger(RegisteredExtractors.class);
	private static List<TextExtractor> extractors = new ArrayList<TextExtractor>();
	private static final int MIN_EXTRACTION = 16;
	
	static {
		log.info("Initializing text extractors");
		extractors.add(new PlainTextExtractor());
		extractors.add(new MsWordTextExtractor());
		extractors.add(new MsExcelTextExtractor());
		extractors.add(new MsPowerPointTextExtractor());
		extractors.add(new OpenOfficeTextExtractor());
		extractors.add(new RTFTextExtractor());
		extractors.add(new HTMLTextExtractor());
		extractors.add(new XMLTextExtractor());
		extractors.add(new PngTextExtractor());
		extractors.add(new MsOutlookTextExtractor());
		extractors.add(new PdfTextExtractor());
		extractors.add(new AudioTextExtractor());
		extractors.add(new ExifTextExtractor());
		extractors.add(new CuneiformTextExtractor());
		extractors.add(new SourceCodeTextExtractor());
		extractors.add(new MsOffice2007TextExtractor());
	}
	
	/**
	 * Extract text to be indexed
	 */
	public static InputStream getText(Node node, String mimeType, String encoding, InputStream is)
			throws ValueFormatException, PathNotFoundException, RepositoryException, IOException {
		log.info("getText({}, {}, {})", new Object[] { mimeType, encoding, is });
		InputStream ret = null;
		boolean failure = false;
		
		try {
			// Check for available text extractor
			for (TextExtractor te : RegisteredExtractors.extractors) {
				log.info("Testing {} => {}", te.getClass().getCanonicalName(), te.getContentTypes());
				
				if (Arrays.asList(te.getContentTypes()).contains(mimeType)) {
					log.info("Resolved extractor: {}", te.getClass().getCanonicalName());
					Reader rd = te.extractText(is, mimeType, encoding);
					ret = new ReaderInputStream(rd);
				}
			}
		} catch (Exception e) {
			log.warn("Text extraction failure: {}", e.getMessage());
			failure = true;
		}
		
		if (failure || ret == null || ret.available() < MIN_EXTRACTION) {
			UserActivity.log(node.getSession().getUserID(), "MISC_TEXT_EXTRACTION", node.getUUID(), node.getPath());
		}
		
		log.info("getText: {}", ret);
		return ret;
	}
}
