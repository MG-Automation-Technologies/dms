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

package com.openkm.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import com.openkm.core.Config;

public class FileLogger {
	private static SimpleDateFormat logDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Writer cLogger = null;
	
	/**
	 * Create a new file logger.
	 * 
	 * @param baseName The base name of the log file
	 * @throws IOException If there is an exception when creating.
	 */
	public FileLogger(String baseName) throws IOException {
		String fileDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		cLogger = new FileWriter(Config.HOME_DIR + File.separator + baseName + "_" + fileDate + ".log");
	}
	
	/**
	 * Write message to log file.
	 * 
	 * @param message Message to write.
	 * @throws IOException If there is an exception when writing.
	 */
	public void write(String message) throws IOException {
		cLogger.write(logDate.format(new Date()) + " " + message + "\n");
		cLogger.flush();
	}
	
	/**
	 * Close log file.
	 */
	public void close() {
		IOUtils.closeQuietly(cLogger);
	}
	
	/**
	 * Static file logger.
	 * @throws IOException If there is an exception when writing.
	 */
	public static void write(String baseName, String message) throws IOException {
		String fileDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		Writer sLogger = new FileWriter(Config.HOME_DIR + File.separator + baseName + "_" + fileDate + ".log", true);
		sLogger.write(logDate.format(new Date()) + " " + message + "\n");
		sLogger.flush();
		sLogger.close();
	}
}
