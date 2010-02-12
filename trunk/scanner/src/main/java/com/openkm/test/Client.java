package com.openkm.test;

import java.util.logging.Logger;

import com.openkm.ws.client.AccessDeniedException_Exception;
import com.openkm.ws.client.Document;
import com.openkm.ws.client.FileSizeExceededException_Exception;
import com.openkm.ws.client.IOException_Exception;
import com.openkm.ws.client.ItemExistsException_Exception;
import com.openkm.ws.client.OKMAuth;
import com.openkm.ws.client.OKMAuthService;
import com.openkm.ws.client.OKMDocument;
import com.openkm.ws.client.OKMDocumentService;
import com.openkm.ws.client.PathNotFoundException_Exception;
import com.openkm.ws.client.RepositoryException_Exception;
import com.openkm.ws.client.UnsupportedMimeTypeException_Exception;
import com.openkm.ws.client.UserAlreadyLoggerException_Exception;
import com.openkm.ws.client.VirusDetectedException_Exception;

public class Client {
	private static Logger log = Logger.getLogger(Client.class.getName());
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			OKMAuthService okmAuthService = new OKMAuthService();
			OKMAuth okmAuth = okmAuthService.getOKMAuthPort();
			String token = okmAuth.login("okmAdmin", "admin");
			log.info("Es: "+token);
						
			OKMDocumentService okmDocumentService = new OKMDocumentService();
			OKMDocument okmDocument = okmDocumentService.getOKMDocumentPort();
			Document doc = new Document();
			doc.setPath("/okm:root/prueba.txt");
			okmDocument.create(token, doc, "Esto es un mensaje".getBytes());
			
			okmAuth.logout(token);
		} catch (UserAlreadyLoggerException_Exception e) {
			log.severe("Error: "+e.getMessage());
		} catch (VirusDetectedException_Exception e) {
			log.severe("Error: "+e.getMessage());
		} catch (FileSizeExceededException_Exception e) {
			log.severe("Error: "+e.getMessage());
		} catch (UnsupportedMimeTypeException_Exception e) {
			log.severe("Error: "+e.getMessage());
		} catch (ItemExistsException_Exception e) {
			log.severe("Error: "+e.getMessage());
		} catch (PathNotFoundException_Exception e) {
			log.severe("Error: "+e.getMessage());
		} catch (AccessDeniedException_Exception e) {
			log.severe("Error: "+e.getMessage());
		} catch (RepositoryException_Exception e) {
			log.severe("Error: "+e.getMessage());
		} catch (IOException_Exception e) {
			log.severe("Error: "+e.getMessage());
		}
	}
}
