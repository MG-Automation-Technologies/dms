package com.openkm.applet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

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

	/**
	 * 
	 */
	public static void createDocument(String token, String path, String url, File file) throws IOException,
			AccessDeniedException_Exception, FileSizeExceededException_Exception, IOException_Exception,
			ItemExistsException_Exception, PathNotFoundException_Exception, RepositoryException_Exception,
			UnsupportedMimeTypeException_Exception, VirusDetectedException_Exception {
		log.info("uploadDocument(" + token + ", " + path + ", " + url + ", " + file);

		OKMDocumentService okmDocumentService = new OKMDocumentService();
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
		log.info("createFolder(" + token + ", " + path + ", " + url + ", " + file);

		OKMFolderService okmFolderService = new OKMFolderService();
		OKMFolder okmFolder = okmFolderService.getOKMFolderPort();
		Folder fld = new Folder();

		fld.setPath(path + "" + file.getName());
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
}
