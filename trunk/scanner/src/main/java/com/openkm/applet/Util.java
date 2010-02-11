package com.openkm.applet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
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

		OKMDocumentService okmDocumentService = new OKMDocumentService();
		OKMDocument okmDocument = okmDocumentService.getOKMDocumentPort();
		Document doc = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, "fileType", baos);
			baos.flush();

			BindingProvider bp = (BindingProvider)okmDocument; 
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url+"/OKMDocument");
			doc.setPath(path + "/" + fileName + "." + fileType);
			okmDocument.create(token, doc, baos.toByteArray());
		} finally {
			if (baos != null) {
				baos.close();
			}
		}
	}
}
