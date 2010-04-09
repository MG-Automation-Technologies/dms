/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Document;
import es.git.openkm.bean.Folder;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.module.DocumentModule;
import es.git.openkm.module.FolderModule;
import es.git.openkm.module.ModuleManager;
import es.git.openkm.util.FileUtils;

public class RepositoryExporter {
	private static Logger log = LoggerFactory.getLogger(RepositoryExporter.class);
	private static boolean firstTime = true;
	
	private RepositoryExporter() {}

	/**
	 * Performs a recursive repository content export with metadata
	 * 
	 * @param repoPath
	 * @param fsPath
	 * @throws RepositoryException
	 */
	public static ImpExpStats exportDocuments(String token, String fldPath, File fs, Writer out, InfoDecorator deco)
			throws PathNotFoundException, AccessDeniedException, 
			RepositoryException, IOException {
		log.debug("exportDocuments(" + fldPath + "," + fs + ", " + out + ", " + deco + ")");
		ImpExpStats stats;
		
		try {
			if (fs.exists()) {
				firstTime = true;
				stats = exportDocumentsHelper(token, fldPath, fs, out, deco);
			} else  {
				throw new FileNotFoundException(fs.getPath());
			}
		} catch (PathNotFoundException e) {
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

		log.debug("exportDocuments: "+stats);
		return stats;
	}

	/**
	 * Performs a recursive repository content export with metadata
	 * 
	 * @param folderNode
	 * @param fsPath
	 * @throws FileNotFoundException
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws IllegalStateException
	 * @throws javax.jcr.RepositoryException
	 * @throws IOException
	 * @throws RepositoryException
	 */
	private static ImpExpStats exportDocumentsHelper(String token, String fldPath, File fs,  Writer out, InfoDecorator deco)
			throws FileNotFoundException, PathNotFoundException, AccessDeniedException, 
			RepositoryException, IOException {
		log.debug("exportDocumentsHelper(" + token + ", " + fldPath + ", " + fs + ", " + out + ", " + deco + ")");
		ImpExpStats stats = new ImpExpStats();
		String path = null;
		
		if (firstTime) {
			path = fs.getPath();
			firstTime = false;
		} else {
			// Repository path needs to be "corrected" under Windoze new
			path = fs.getPath() + File.separator + FileUtils.getName(fldPath).replace(':', '_');
		}
		
		File fsPath = new File(path);
		fsPath.mkdirs();

		DocumentModule dm = ModuleManager.getDocumentModule();
		for (Iterator<Document> it = dm.getChilds(token, fldPath).iterator(); it.hasNext();) {
			Document docChild = it.next();
			path = fsPath.getPath() + File.separator+ FileUtils.getName(docChild.getPath()).replace(':', '_');
			FileOutputStream fos = new FileOutputStream(path);
			InputStream is = dm.getContent(token, docChild.getPath(), false);
			IOUtils.copy(is, fos);
			is.close();
			fos.close();
			out.write(deco.print(docChild.getPath(), null));
			out.flush();
			
			// Stats
			stats.setSize(stats.getSize() + docChild.getActualVersion().getSize());
			stats.setDocuments(stats.getDocuments() + 1);
		}

		FolderModule fm = ModuleManager.getFolderModule();
		for (Iterator<Folder> it = fm.getChilds(token, fldPath).iterator(); it.hasNext();) {
			Folder fldChild = it.next();
			ImpExpStats tmp = exportDocumentsHelper(token, fldChild.getPath(), fsPath, out, deco);
			
			// Stats
			stats.setSize(stats.getSize() + tmp.getSize());
			stats.setDocuments(stats.getDocuments() + tmp.getDocuments());
			stats.setFolders(stats.getFolders() + tmp.getFolders() + 1);
			stats.setOk(stats.isOk() && tmp.isOk());
		}

		log.debug("exportDocumentsHelper: "+stats);
		return stats;
	}
}
