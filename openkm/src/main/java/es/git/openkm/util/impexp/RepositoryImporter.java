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

package es.git.openkm.util.impexp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Document;
import es.git.openkm.bean.Folder;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.FileSizeExceededException;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.UnsupportedMimeTypeException;
import es.git.openkm.core.VirusDetectedException;
import es.git.openkm.module.ModuleManager;

public class RepositoryImporter {
	private static Logger log = LoggerFactory.getLogger(RepositoryImporter.class);
		
	private RepositoryImporter() {}

	/**
	 * Import documents in filesystem
	 * 
	 * @param fsPath
	 * @param repoPath
	 * @throws RepositoryException
	 * @throws PathNotFoundException 
	 * @throws ItemExistsException 
	 * @throws AccessDeniedException 
	 * @throws javax.jcr.RepositoryException 
	 * @throws IOException 
	 */
	public static boolean importDocuments(String token, File fs, String fldPath, Writer out, InfoDecorator deco) 
			throws PathNotFoundException, ItemExistsException, AccessDeniedException, 
			RepositoryException, IOException {
		log.debug("importDocuments(" + token + ", " + fs + ", " + fldPath + ")");
		boolean ok = true;
		
		try {
			if (fs.exists()) {
				ok = importDocumentsHelper(token, fs, fldPath, out, deco);
			} else  {
				throw new FileNotFoundException(fs.getPath());
			}
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (ItemExistsException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
				
		log.debug("importDocuments: "+ok);
		return ok;
	}
	
	/**
	 * Import previously exported contents to the repository
	 * 
	 * @param dir
	 * @param folderNode
	 * @throws javax.jcr.RepositoryException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws RepositoryException
	 * @throws AccessDeniedException 
	 * @throws PathNotFoundException 
	 * @throws ItemExistsException 
	 * @throws ItemExistsException 
	 * @throws FileSizeExceededException 
	 * @throws UnsupportedMimeTypeException 
	 */
	private static boolean importDocumentsHelper(String token, File fs, String fldPath, Writer out, InfoDecorator deco) 
			throws FileNotFoundException, PathNotFoundException, AccessDeniedException, 
			ItemExistsException, RepositoryException, IOException {
		log.debug("importDocumentsHelper("+token+", "+fs+", "+fldPath+")");
		File[] files = fs.listFiles();
		boolean allOk = true;
				
		for (int i=0; i<files.length; i++) {
			if (files[i].isDirectory()) {
				Folder fld = new Folder();
				fld.setPath(fldPath + "/" + files[i].getName());
				
				try {
					ModuleManager.getFolderModule().create(token, fld);
					allOk &= importDocumentsHelper(token, files[i], fld.getPath(), out, deco);
				} catch (ItemExistsException e) {
					log.warn("ItemExistsException: "+e.getMessage());
					out.write(deco.print(files[i].getPath(), "ItemExists"));
					out.flush();
					allOk = false;
				}
			} else {
				Document doc = new Document();
				doc.setPath(fldPath + "/" + files[i].getName());
				FileInputStream fisContent = new FileInputStream(files[i]);
				boolean docOk = true;
				
				try {
					ModuleManager.getDocumentModule().create(token, doc, fisContent);
				} catch (UnsupportedMimeTypeException e) {
					log.warn("UnsupportedMimeTypeException: "+e.getMessage());
					out.write(deco.print(files[i].getPath(), "UnsupportedMimeType"));
					out.flush();
					allOk = docOk = false;
				} catch (FileSizeExceededException e) {
					log.warn("FileSizeExceededException: "+e.getMessage());
					out.write(deco.print(files[i].getPath(), "FileSizeExceeded"));
					out.flush();
					allOk = docOk = false;
				} catch (VirusDetectedException e) {
					log.warn("VirusWarningException: "+e.getMessage());
					out.write(deco.print(files[i].getPath(), "VirusWarningException"));
					out.flush();
					allOk = docOk = false;
				} catch (ItemExistsException e) {
					log.warn("ItemExistsException: "+e.getMessage());
					out.write(deco.print(files[i].getPath(), "ItemExists"));
					out.flush();
					allOk = docOk = false;
				} finally {
					fisContent.close();					
				}

				if (docOk) {
					out.write(deco.print(files[i].getPath(), null));
					out.flush();
				}
			}
		}
		
		log.debug("importDocumentsHelper: "+allOk);
		return allOk;
	}
}
