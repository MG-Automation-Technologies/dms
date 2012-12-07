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

package com.openkm.util.impexp;

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

import com.google.gson.Gson;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Version;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.NoSuchGroupException;
import com.openkm.core.ParseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.jcr.JCRUtils;
import com.openkm.module.DocumentModule;
import com.openkm.module.FolderModule;
import com.openkm.module.ModuleManager;
import com.openkm.util.impexp.metadata.DocumentMetadata;
import com.openkm.util.impexp.metadata.FolderMetadata;
import com.openkm.util.impexp.metadata.MetadataAdapter;
import com.openkm.util.impexp.metadata.VersionMetadata;

public class RepositoryExporter {
	private static Logger log = LoggerFactory.getLogger(RepositoryExporter.class);
	private static boolean firstTime = true;
	
	private RepositoryExporter() {}

	/**
	 * Performs a recursive repository content export with metadata
	 */
	public static ImpExpStats exportDocuments(String token, String fldPath, File fs, boolean metadata,
			boolean history, Writer out, InfoDecorator deco) throws PathNotFoundException, AccessDeniedException, 
			RepositoryException, FileNotFoundException, IOException, DatabaseException, ParseException,
			NoSuchGroupException {
		log.debug("exportDocuments({}, {}, {}, {}, {}, {})", new Object[] { token, fldPath, fs, metadata, out, deco });
		ImpExpStats stats;
		
		try {
			if (fs.exists()) {
				firstTime = true;
				stats = exportDocumentsHelper(token, fldPath, fs, metadata, history, out, deco);
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
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (NoSuchGroupException e) {
			log.error(e.getMessage(), e);
			throw e;
		}

		log.debug("exportDocuments: {}", stats);
		return stats;
	}

	/**
	 * Performs a recursive repository content export with metadata 
	 */
	private static ImpExpStats exportDocumentsHelper(String token, String fldPath, File fs, boolean metadata,
			boolean history, Writer out, InfoDecorator deco) throws FileNotFoundException, PathNotFoundException,
			AccessDeniedException, RepositoryException, IOException, DatabaseException, ParseException,
			NoSuchGroupException {
		log.debug("exportDocumentsHelper({}, {}, {}, {}, {}, {}, {})", new Object[] { token, fldPath, fs, metadata, history, out, deco });
		ImpExpStats stats = new ImpExpStats();
		DocumentModule dm = ModuleManager.getDocumentModule();
		FolderModule fm = ModuleManager.getFolderModule();
		MetadataAdapter ma = MetadataAdapter.getInstance(token);
		Gson gson = new Gson();
		String path = null;
		File fsPath = null;
		
		if (firstTime) {
			path = fs.getPath();
			fsPath = new File(path);
			firstTime = false;
		} else {
			// Repository path needs to be "corrected" under Windoze
			path = fs.getPath() + File.separator + JCRUtils.getName(fldPath).replace(':', '_');
			fsPath = new File(path);
			fsPath.mkdirs();
			out.write(deco.print(fldPath, 0, null));
			out.flush();
		}
		
		for (Iterator<Document> it = dm.getChilds(token, fldPath).iterator(); it.hasNext();) {
			Document docChild = it.next();
			path = fsPath.getPath() + File.separator + JCRUtils.getName(docChild.getPath()).replace(':', '_');
			
			// Version history
			if (history) {
				// Create dummy document file
				new File(path).createNewFile();
				
				// Metadata
				if (metadata) {
					DocumentMetadata dmd = ma.getMetadata(docChild);
					String json = gson.toJson(dmd);
					FileOutputStream fos = new FileOutputStream(path + Config.EXPORT_METADATA_EXT);
					IOUtils.write(json, fos);
					IOUtils.closeQuietly(fos);
				}
				
				for (Version ver : dm.getVersionHistory(token, docChild.getPath())) {
					String versionPath = path + "#v" + ver.getName() + "#";
					FileOutputStream vos = new FileOutputStream(versionPath);
					InputStream vis = dm.getContentByVersion(token, docChild.getPath(), ver.getName());
					IOUtils.copy(vis, vos);
					IOUtils.closeQuietly(vis);
					IOUtils.closeQuietly(vos);
					
					// Metadata
					if (metadata) {
						VersionMetadata vmd = ma.getMetadata(ver, docChild.getMimeType());
						String json = gson.toJson(vmd);
						vos = new FileOutputStream(versionPath + Config.EXPORT_METADATA_EXT);
						IOUtils.write(json, vos);
						IOUtils.closeQuietly(vos);
					}
				}
			} else {
				FileOutputStream fos = new FileOutputStream(path);
				InputStream is = dm.getContent(token, docChild.getPath(), false);
				IOUtils.copy(is, fos);
				IOUtils.closeQuietly(is);
				IOUtils.closeQuietly(fos);
				
				// Metadata
				if (metadata) {
					DocumentMetadata dmd = ma.getMetadata(docChild);
					String json = gson.toJson(dmd);
					fos = new FileOutputStream(path + Config.EXPORT_METADATA_EXT);
					IOUtils.write(json, fos);
					IOUtils.closeQuietly(fos);
				}
			}
			
			out.write(deco.print(docChild.getPath(), docChild.getActualVersion().getSize(), null));
			out.flush();
			
			// Stats
			stats.setSize(stats.getSize() + docChild.getActualVersion().getSize());
			stats.setDocuments(stats.getDocuments() + 1);
		}
		
		for (Iterator<Folder> it = fm.getChilds(token, fldPath).iterator(); it.hasNext();) {
			Folder fldChild = it.next();
			ImpExpStats tmp = exportDocumentsHelper(token, fldChild.getPath(), fsPath, metadata, history, out, deco);
			path = fsPath.getPath() + File.separator + JCRUtils.getName(fldChild.getPath()).replace(':', '_');
			
			// Metadata
			if (metadata) {
				FolderMetadata fmd = ma.getMetadata(fldChild);
				String json = gson.toJson(fmd);
				FileOutputStream fos = new FileOutputStream(path + Config.EXPORT_METADATA_EXT);
				IOUtils.write(json, fos);
				fos.close();
			}
			
			// Stats
			stats.setSize(stats.getSize() + tmp.getSize());
			stats.setDocuments(stats.getDocuments() + tmp.getDocuments());
			stats.setFolders(stats.getFolders() + tmp.getFolders() + 1);
			stats.setOk(stats.isOk() && tmp.isOk());
		}

		log.debug("exportDocumentsHelper: {}", stats);
		return stats;
	}
}
