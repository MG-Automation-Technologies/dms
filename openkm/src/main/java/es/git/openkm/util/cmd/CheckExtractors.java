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

package es.git.openkm.util.cmd;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.activation.MimetypesFileTypeMap;
import javax.jcr.RepositoryException;

import org.apache.jackrabbit.core.query.lucene.JackrabbitTextExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.core.Config;

public class CheckExtractors {
	private static Logger log = LoggerFactory.getLogger(CheckExtractors.class);
	
	/**
	 * @param args
	 * @throws RepositoryException 
	 */
	public static void main(String[] args) throws RepositoryException {
		if (args.length < 1 || args[0] == null) {
			System.err.println("Usage: CheckExtractors <file> [encoding]");
			return;
		}

		String file = args[0];
		String encoding = "UTF-8";
		String classes = "org.apache.jackrabbit.core.query.lucene.TextPlainTextFilter,"+
			"org.apache.jackrabbit.extractor.MsExcelTextExtractor,"+
			"org.apache.jackrabbit.extractor.MsPowerPointTextExtractor,"+
			"org.apache.jackrabbit.extractor.MsWordTextExtractor,"+
			"org.apache.jackrabbit.extractor.PdfTextExtractor,"+
			"org.apache.jackrabbit.extractor.HTMLTextExtractor,"+
			"org.apache.jackrabbit.extractor.XMLTextExtractor,"+
			"org.apache.jackrabbit.extractor.RTFTextExtractor,"+
			"org.apache.jackrabbit.extractor.OpenOfficeTextExtractor,"+
			"es.git.openkm.extractor.MsOffice2007TextExtractor,"+
			"es.git.openkm.extractor.ExifTextExtractor,"+
			"es.git.openkm.extractor.TiffTextExtractor,"+
			"es.git.openkm.extractor.AudioTextExtractor";
		JackrabbitTextExtractor extractor = new JackrabbitTextExtractor(classes);
		
		if (args.length > 1 && args[1] != null) {
			encoding = args[1]; 
		}
		
		String[] contentTypes = extractor.getContentTypes();
		System.out.println("ACCEPTED CONTENT TYPES");
		System.out.println("======================");
		for (int i=0; i<contentTypes.length-1; i++) {
			System.out.print(contentTypes[i]+", ");
		}
		System.out.println(contentTypes[contentTypes.length-1]);
		System.out.println();
		
		try {
			// Read MIME types
			InputStream isMime = Config.class.getResourceAsStream(Config.MIME_FILE);
			MimetypesFileTypeMap mimeTypes = new MimetypesFileTypeMap(isMime);
			isMime.close();
			
			String mimeType = mimeTypes.getContentType(file.toLowerCase());
			System.out.println("File: "+file);
			System.out.println("MIME: "+mimeType);
			System.out.println("Encoding: "+encoding);

			InputStream is = new FileInputStream(file);
			Reader r = extractor.extractText(is, mimeType, encoding);
			BufferedReader br = new BufferedReader(r);
			String line;
			
			System.out.println();
			System.out.println("--------- CONTENT BEGIN -----------");
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("--------- CONTENT END -----------");
			
			br.close();
			is.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
