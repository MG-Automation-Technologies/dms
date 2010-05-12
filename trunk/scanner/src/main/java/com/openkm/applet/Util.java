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

package com.openkm.applet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import com.openkm.ws.client.AccessDeniedException_Exception;
import com.openkm.ws.client.Document;
import com.openkm.ws.client.FileSizeExceededException_Exception;
import com.openkm.ws.client.IOException_Exception;
import com.openkm.ws.client.ItemExistsException_Exception;
import com.openkm.ws.client.OKMDocument;
import com.openkm.ws.client.OKMDocumentService;
import com.openkm.ws.client.PathNotFoundException_Exception;
import com.openkm.ws.client.RepositoryException_Exception;
import com.openkm.ws.client.UnsupportedMimeTypeException_Exception;
import com.openkm.ws.client.VirusDetectedException_Exception;

public class Util {
	private static Logger log = Logger.getLogger(Util.class.getName());
	private static QName DocumentServiceName = new QName("http://endpoint.ws.openkm.com/", "OKMDocumentService");

	/**
	 * 
	 */
	public static void uploadDocument(String token, String path, String fileName, String fileType,
			String url, BufferedImage image) throws IOException, AccessDeniedException_Exception,
			FileSizeExceededException_Exception, IOException_Exception, ItemExistsException_Exception,
			PathNotFoundException_Exception, RepositoryException_Exception,
			UnsupportedMimeTypeException_Exception, VirusDetectedException_Exception {
		log.info("uploadDocument(" + token + ", " + path + ", " + fileName + ", " + fileType + ", " + url
				+ ", " + image);

		OKMDocumentService okmDocumentService = new OKMDocumentService(new URL(url+"/OKMDocument?wsdl") , DocumentServiceName);
		OKMDocument okmDocument = okmDocumentService.getOKMDocumentPort();
		Document doc = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			if (ImageIO.write(image, fileType, baos)) {
				baos.flush();

				BindingProvider bp = (BindingProvider)okmDocument; 
				bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url+"/OKMDocument");
				doc.setPath(path + "/" + fileName + "." + fileType);
				okmDocument.create(token, doc, baos.toByteArray());
			} else {
				log.warning("Not appropiated writer found!");
			}
		} finally {
			if (baos != null) {
				baos.close();
			}
		}
	}
	
	/**
	 * 
	 */
	 public static Locale parseLocaleString(String localeString) {
		 String[] parts = localeString.split("-");
		 String language = (parts.length > 0 ? parts[0] : "");
		 String country = (parts.length > 1 ? parts[1] : "");
		 
	    return (language.length() > 0 ? new Locale(language, country) : null);
	 }
}
