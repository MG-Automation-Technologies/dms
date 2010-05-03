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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import com.openkm.ws.client.AccessDeniedException_Exception;
import com.openkm.ws.client.Document;
import com.openkm.ws.client.FileSizeExceededException_Exception;
import com.openkm.ws.client.Folder;
import com.openkm.ws.client.IOException_Exception;
import com.openkm.ws.client.ItemExistsException_Exception;
import com.openkm.ws.client.OKMDocument;
import com.openkm.ws.client.OKMDocumentService;
import com.openkm.ws.client.OKMFolder;
import com.openkm.ws.client.OKMFolderService;
import com.openkm.ws.client.PathNotFoundException_Exception;
import com.openkm.ws.client.RepositoryException_Exception;
import com.openkm.ws.client.UnsupportedMimeTypeException_Exception;
import com.openkm.ws.client.VirusDetectedException_Exception;

public class Util {
	private static Logger log = Logger.getLogger(Util.class.getName());
	private static QName documentServiceName = new QName("http://endpoint.ws.openkm.git.es/", "OKMDocumentService");
	private static QName folderServiceName = new QName("http://endpoint.ws.openkm.git.es/", "OKMFolderService");

	/**
	 * 
	 */
	public static void createDocument(String token, String path, String url, File file) throws IOException,
			AccessDeniedException_Exception, FileSizeExceededException_Exception, IOException_Exception,
			ItemExistsException_Exception, PathNotFoundException_Exception, RepositoryException_Exception,
			UnsupportedMimeTypeException_Exception, VirusDetectedException_Exception {
		log.info("uploadDocument(" + token + ", " + path + ", " + url + ", " + file + ")");

		OKMDocumentService okmDocumentService = new OKMDocumentService(new URL(url+"/OKMDocument?wsdl"), documentServiceName);
		OKMDocument okmDocument = okmDocumentService.getOKMDocumentPort();
		Document doc = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 4];
			int n = 0;
			while (-1 != (n = fis.read(buffer))) {
				baos.write(buffer, 0, n);
			}

			BindingProvider bp = (BindingProvider) okmDocument;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url+"/OKMDocument");
			doc.setPath(path + "/" + file.getName());
			okmDocument.create(token, doc, baos.toByteArray());
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (baos != null) {
				baos.close();
			}
		}
	}

	/**
	 * 
	 */
	public static void createFolder(String token, String path, String url, File file) throws IOException,
			AccessDeniedException_Exception, ItemExistsException_Exception, PathNotFoundException_Exception,
			RepositoryException_Exception {
		log.info("createFolder(" + token + ", " + path + ", " + url + ", " + file + ")");

		OKMFolderService okmFolderService = new OKMFolderService(new URL(url+"/OKMFolder?wsdl"), folderServiceName);
		OKMFolder okmFolder = okmFolderService.getOKMFolderPort();
		Folder fld = new Folder();

		BindingProvider bp = (BindingProvider) okmFolder;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url+"/OKMFolder");
		fld.setPath(path + "/" + file.getName());
		okmFolder.create(token, fld);
	}

	/**
	 * 
	 */
	public static List<File> textURIListToFileList(String data) {
		List<File> list = new ArrayList<File>(1);

		for (StringTokenizer st = new StringTokenizer(data, "\r\n"); st.hasMoreTokens();) {
			String s = st.nextToken();

			if (s.startsWith("#")) {
				// the line is a comment (as per the RFC 2483)
				continue;
			}

			try {
				URI uri = new URI(s);
				File file = new File(uri);
				list.add(file);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}

		return list;
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
