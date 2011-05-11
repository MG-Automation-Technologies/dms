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

package com.openkm.extension.core;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ServiceConfigurationError;

import javax.jcr.Node;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Document;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.FileSizeExceededException;
import com.openkm.core.ItemExistsException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.Ref;
import com.openkm.core.RepositoryException;
import com.openkm.core.UnsupportedMimeTypeException;
import com.openkm.core.UserQuotaExceededException;
import com.openkm.core.VirusDetectedException;

public class DocumentExtensionManager {
	private static Logger log = LoggerFactory.getLogger(DocumentExtensionManager.class);
	private static DocumentExtensionManager service = null;
	
	private DocumentExtensionManager() {}
	
	public static synchronized DocumentExtensionManager getInstance() {
		if (service == null) {
			service = new DocumentExtensionManager();
		}
		
		return service;
	}
	
	/**
	 * Handle PRE create extensions 
	 */
	public void preCreate(Session session, Ref<Node> parentNode, Ref<File> content, Ref<Document> doc) throws 
			UnsupportedMimeTypeException, FileSizeExceededException, UserQuotaExceededException,
			VirusDetectedException, ItemExistsException, PathNotFoundException, AccessDeniedException, 
			RepositoryException, IOException, DatabaseException, ExtensionException {
		try {
			ExtensionManager em = ExtensionManager.getInstance();
			List<DocumentExtension> col = em.getPlugins(DocumentExtension.class);
			Collections.sort(col, new OrderComparator<DocumentExtension>());
			
			for (DocumentExtension de : col) {
				log.info("Es: {}", de.getClass().getCanonicalName());
				de.preCreate(session, parentNode, content, doc);
			}
		} catch (ServiceConfigurationError e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Handle POST create extensions 
	 */
	public void postCreate(Session session, Ref<Node> parentNode, Ref<Node> docNode, Ref<Document> doc) throws 
			UnsupportedMimeTypeException, FileSizeExceededException, UserQuotaExceededException,
			VirusDetectedException, ItemExistsException, PathNotFoundException, AccessDeniedException, 
			RepositoryException, IOException, DatabaseException, ExtensionException {
		try {
			ExtensionManager em = ExtensionManager.getInstance();
			List<DocumentExtension> col = em.getPlugins(DocumentExtension.class);
			Collections.sort(col, new OrderComparator<DocumentExtension>());
			
			for (DocumentExtension de : col) {
				log.info("Es: {}", de.getClass().getCanonicalName());
				de.postCreate(session, parentNode, docNode, doc);
			}
		} catch (ServiceConfigurationError e) {
			log.error(e.getMessage(), e);
		}
	}
}
