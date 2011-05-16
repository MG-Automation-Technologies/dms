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

package com.openkm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream.UnicodeExtraFieldPolicy;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.module.direct.DirectDocumentModule;

public class FileUtils {
	private static Logger log = LoggerFactory.getLogger(DirectDocumentModule.class);
	
	/**
	 * Returns the name of the file whithout the extension.  
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileName(String file) {
		log.debug("getFileName("+file+")");
		int idx = file.lastIndexOf(".");
		String ret = idx>=0?file.substring(0, idx):file;
		log.debug("getFileName: "+ret);
		return ret;
	}
	
	/**
	 * Returns the filename extension
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileExtension(String file) {
		log.debug("getFileExtension("+file+")");
		int idx = file.lastIndexOf(".");
		String ret = idx>=0?file.substring(idx+1):"";
		log.debug("getFileExtension: "+ret);
		return ret;
	}
	
	/**
	 * @param path
	 * @return
	 */
	public static String getParent(String path) {
		log.debug("getParent("+path+")");
		int lastSlash = path.lastIndexOf('/');
		String ret = (lastSlash > 0)?path.substring(0, lastSlash):"";
		log.debug("getParent: "+ret);
		return ret;	
	}

	/**
	 * @param path
	 * @return
	 */
	public static String getName(String path) {
		log.debug("getName("+path+")");
		String ret = path.substring(path.lastIndexOf('/')+1);
		log.debug("getName: "+ret);
		return ret;
	}
	
	/**
	 * Eliminate dangerous chars in name
	 * 
	 * @param name
	 * @return
	 */
	public static String escape(String name) {
		log.debug("escape("+name+")");
		String ret = name.replace('/', ' ');
		ret = ret.replace(':', ' ');
		ret = ret.replace('[', ' ');
		ret = ret.replace(']', ' ');
		ret = ret.replace('*', ' ');
		ret = ret.replace('\'', ' ');
		ret = ret.replace('"', ' ');
		ret = ret.replace('|', ' ');
		ret = ret.trim();
		log.debug("escape: "+ret);
		return ret;
	}
	
	/**
	 * Creates a temporal and unique directory
	 * 
	 * @throws IOException If something fails.
	 */
	public static File createTempDir() throws IOException {
		File tmpFile = File.createTempFile("okm", null);
		
		if (!tmpFile.delete())
            throw new IOException();
        if (!tmpFile.mkdir())
            throw new IOException();
        
        return tmpFile;       
	}
	
	/**
	 * Wrapper for FileUtils.deleteQuietly
	 */
	public static boolean deleteQuietly(File file) {
		return org.apache.commons.io.FileUtils.deleteQuietly(file);
	}
	
	/**
	 * Recursively create ZIP archive from directory 
	 */
	public static void createZip(File path, OutputStream os) throws IOException {
		log.debug("createZip({}, {})", path, os);
		
		if (path.exists() && path.canRead()) {
			ZipArchiveOutputStream zos =  new ZipArchiveOutputStream(os);
			zos.setComment("Generated by OpenKM");
			zos.setCreateUnicodeExtraFields(UnicodeExtraFieldPolicy.ALWAYS);
			zos.setUseLanguageEncodingFlag(true);
			zos.setFallbackToUTF8(true);
			zos.setEncoding("UTF-8");
			
			createZipHelper(path, zos, path.getName());
			
			zos.flush();
			zos.finish();
			zos.close();
		} else {
			throw new IOException("Can't access "+path);
		}
		
		log.debug("createZip: void");
	}
	
	/**
	 * Recursively create ZIP archive from directory helper utility 
	 */
	private static void createZipHelper(File fs, ZipArchiveOutputStream zos, String zePath) throws IOException {
		log.debug("createZipHelper({}, {}, {})", new Object[]{ fs, zos, zePath });
		File[] files = fs.listFiles();
		
		for (int i=0; i<files.length; i++) {
			if (files[i].isDirectory()) {
				createZipHelper(files[i], zos, zePath + "/" + files[i].getName());
			} else {
				ZipArchiveEntry zae = new ZipArchiveEntry(files[i], zePath + "/" + files[i].getName());
				zos.putArchiveEntry(zae);
				FileInputStream fis = new FileInputStream(files[i]);
				IOUtils.copy(fis, zos);
				fis.close();
				zos.closeArchiveEntry();
			}
		}
		
		log.debug("createZipHelper: void");
	}
}