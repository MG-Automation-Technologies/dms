/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2012 Paco Avila & Josep Llort
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.openkm.util.FileUtils;

import net.sourceforge.jiu.data.Gray8Image;

import omrproj.ImageManipulation;
import omrproj.ImageUtil;

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
	public static Map<String, File> trainingTemplate(File template) throws IOException {
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
	public static File process(File fileToProcess, File config, File asc, File fields) throws IOException {
		Gray8Image grayimage = ImageUtil.readImage(fileToProcess.getCanonicalPath());
		ImageManipulation image = new ImageManipulation(grayimage);
        image.locateConcentricCircles();
        image.readConfig(config.getCanonicalPath());
        image.readFields(fields.getCanonicalPath());
        image.readAscTemplate(asc.getCanonicalPath());
        image.searchMarks();
        File dataFile = FileUtils.createTempFile();
        image.saveData(dataFile.getCanonicalPath());
        return dataFile;
	}
	
}