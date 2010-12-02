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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PDFUtils {
	private static Logger log = LoggerFactory.getLogger(PDFUtils.class);
	
	/**
	 * Stamp PDF document with watermark
	 * 
	 * http://www.chillisoft.co.za/blog/?p=223
	 */
	public static void stamp(String input, String image, String output) throws FileNotFoundException,
			DocumentException, IOException {
		log.info("stamp({}, {}, {})", new Object[] { input, image, output });
		Image img = Image.getInstance(image);
		PdfReader reader = new PdfReader(input);
		PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(output));
		PdfContentByte under;
		int numPages = reader.getNumberOfPages();
		int count = 0;
		
		while (count < numPages) {
		    count++;
		    under = stamp.getUnderContent(count);
		    under.addImage(img);
		}
		
		stamp.close();
	}
}
