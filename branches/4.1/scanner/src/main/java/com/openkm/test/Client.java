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
