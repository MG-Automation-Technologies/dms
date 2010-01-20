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

package com.openkm.util;

import java.io.File;
import java.io.IOException;

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
}
