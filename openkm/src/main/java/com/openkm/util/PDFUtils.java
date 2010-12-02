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

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * http://itextpdf.sourceforge.net/howtosign.html
 * 
 * @author pavila
 */
public class PDFUtils {
	private static Logger log = LoggerFactory.getLogger(PDFUtils.class);

	/**
	 * Stamp PDF document with image watermark
	 */
	public static void stampImage(String input, String image, String output) throws FileNotFoundException,
			DocumentException, IOException {
		log.info("stampImage({}, {}, {})", new Object[] { input, image, output });
		Image img = Image.getInstance(image);
		PdfReader reader = new PdfReader(input);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(output));
		PdfGState gs = new PdfGState();
		gs.setFillOpacity(0.3f);
		gs.setStrokeOpacity(0.3f);
		int numPages = reader.getNumberOfPages();
		int count = 0;
		
		while (count++ < numPages) {
			float width = reader.getPageSizeWithRotation(1).getWidth() / 2 - img.getWidth() / 2;
			float height = reader.getPageSizeWithRotation(count).getHeight() / 2 - img.getHeight() / 2;
			img.setAbsolutePosition(width, height);
			PdfContentByte cb = stamper.getUnderContent(count);
			cb.saveState();
			cb.setGState(gs);
			cb.addImage(img);
			cb.restoreState();
		}
		
		stamper.close();
	}
	
	/**
	 * Stamp PDF document with text watermark
	 */
	public static void stampText(String input, String text, String output) throws FileNotFoundException,
			DocumentException, IOException {
		log.info("stampText({}, {}, {})", new Object[] { input, text, output });
		PdfReader reader = new PdfReader(input);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(output));
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
		PdfGState gs = new PdfGState();
		gs.setFillOpacity(0.3f);
		gs.setStrokeOpacity(0.3f);
		int numPages = reader.getNumberOfPages();
		int count = 0;
		
		while (count++ < numPages) {
			float width = reader.getPageSizeWithRotation(1).getWidth() / 2;
			float height = reader.getPageSizeWithRotation(count).getHeight() / 2;
			PdfContentByte cb = stamper.getUnderContent(count);
			cb.saveState();
			cb.setColorFill(Color.LIGHT_GRAY);
			cb.setGState(gs);
			cb.beginText();
			cb.setFontAndSize(bf, 100);
			cb.showTextAligned(Element.ALIGN_CENTER, text, width, height, 35);
			cb.endText();
			cb.restoreState();
		}
		
		stamper.close();
		reader.close();
	}
	
	/**
	 * Fill PDF form
	 */
	@SuppressWarnings("rawtypes")
	public static void fillForm(String input, Map<String, String> values, 
			String output) throws FileNotFoundException, DocumentException, IOException {
		log.info("fillForm({}, {}, {})", new Object[] { input, values, output });
		PdfReader reader = new PdfReader(input);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(output));
		AcroFields form = stamper.getAcroFields();
		
		for (Iterator it = reader.getAcroForm().getFields().iterator(); it.hasNext(); ) {
			PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation) it.next();
			String value = values.get(field.getName());
			
			if (value != null) {
				form.setField(field.getName(), value);
			}
		}
		
		stamper.setFormFlattening(true);
		stamper.close();
		reader.close();
	}
}
