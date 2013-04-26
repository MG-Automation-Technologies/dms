/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2013 Paco Avila & Josep Llort
 * 
 * No bytes were intentionally harmed during the development of this application.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.jiu.data.Gray8Image;
import omrproj.ImageManipulation;
import omrproj.ImageUtil;

import org.apache.commons.io.IOUtils;

import com.openkm.core.Config;
import com.openkm.dao.bean.Omr;

/**
 * OMRUtils
 * 
 * @author jllort
 */
public class OMRUtils {
	public static final String ASC_FILE = "ASC_FILE";
	public static final String CONFIG_FILE = "CONFIG_FILE";
	
	/**
	 * trainingTemplate
	 */
	public static  Map<String, File> trainingTemplate(File template) throws IOException {
		Map<String, File> fileMap = new HashMap<String, File>();
		Gray8Image grayimage = ImageUtil.readImage(template.getCanonicalPath());
		ImageManipulation image = new ImageManipulation(grayimage);
		image.locateConcentricCircles();
		image.locateMarks();
		File ascFile = FileUtils.createTempFile();
		File configFile = FileUtils.createTempFile();
		image.writeAscTemplate(ascFile.getCanonicalPath());
        image.writeConfig(configFile.getCanonicalPath());
        fileMap.put(ASC_FILE, ascFile);
        fileMap.put(CONFIG_FILE, configFile);
        return fileMap;
	}
	
	/**
	 * process
	 */
	public static File process(File fileToProcess, long omr_id) throws IOException, OMRException {
		File ascCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omr_id + ".asc");
		File configCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omr_id + ".config");
		File fieldsCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omr_id + ".fields");
		
		if (ascCache.exists() && configCache.exists() && fieldsCache.exists()) {
			Gray8Image grayimage = ImageUtil.readImage(fileToProcess.getCanonicalPath());
			ImageManipulation image = new ImageManipulation(grayimage);
	        image.locateConcentricCircles();
	        image.readConfig(configCache.getCanonicalPath());
	        image.readFields(fieldsCache.getCanonicalPath());
	        image.readAscTemplate(ascCache.getCanonicalPath());
	        image.searchMarks();
	        File dataFile = FileUtils.createTempFile();
	        image.saveData(dataFile.getCanonicalPath());
	        return dataFile;
		} else {
			throw new OMRException("Error asc, config or fields files not found");
		}
	}
	
	/**
	 * storeFilesToCache
	 */
	public static void storeFilesToCache(Omr omr) throws IOException {
		File ascCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omr.getId() + ".asc");
		if (omr.getAscFileContent()!=null && omr.getAscFileContent().length!=0) {
			OutputStream ascFile = new FileOutputStream(ascCache);
			ascFile.write(omr.getAscFileContent());
			IOUtils.closeQuietly(ascFile);
		} else if (ascCache.exists()) {
			FileUtils.deleteQuietly(ascCache);
		}
		File configCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omr.getId() + ".config");
		if (omr.getConfigFileContent()!=null && omr.getConfigFileContent().length!=0) {
			OutputStream configFile = new FileOutputStream(configCache);
			configFile.write(omr.getConfigFileContent());
			IOUtils.closeQuietly(configFile);
		} else if (configCache.exists()) {
			FileUtils.deleteQuietly(configCache);
		}
		File fieldsCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omr.getId() + ".fields");
		if (omr.getFieldsFileContent()!=null && omr.getFieldsFileContent().length!=0) {
			OutputStream fieldsFile = new FileOutputStream(fieldsCache);
			fieldsFile.write(omr.getFieldsFileContent());
			IOUtils.closeQuietly(fieldsFile);
		} else if (fieldsCache.exists()) {
			FileUtils.deleteQuietly(fieldsCache);
		}
	}
	
	/**
	 * deleteCachedFiles
	 */
	public static void deleteCachedFiles(long omr_id) {
		File ascCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omr_id + ".asc");
		if (ascCache.exists()) {
			FileUtils.deleteQuietly(ascCache);
		}
		File configCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omr_id + ".config");
		if (configCache.exists()) {
			FileUtils.deleteQuietly(configCache);
		}
		File fieldsCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omr_id + ".fields");
		if (fieldsCache.exists()) {
			FileUtils.deleteQuietly(fieldsCache);
		}
	}
}