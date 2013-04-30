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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.jiu.codecs.InvalidFileStructureException;
import net.sourceforge.jiu.codecs.InvalidImageIndexException;
import net.sourceforge.jiu.codecs.UnsupportedTypeException;
import net.sourceforge.jiu.data.Gray8Image;
import net.sourceforge.jiu.ops.MissingParameterException;
import net.sourceforge.jiu.ops.WrongParameterException;

import org.apache.commons.io.IOUtils;

import com.openkm.api.OKMPropertyGroup;
import com.openkm.automation.AutomationException;
import com.openkm.bean.PropertyGroup;
import com.openkm.bean.form.FormElement;
import com.openkm.bean.form.Select;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.LockException;
import com.openkm.core.NoSuchGroupException;
import com.openkm.core.NoSuchPropertyException;
import com.openkm.core.ParseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.OmrDAO;
import com.openkm.dao.bean.Omr;
import com.openkm.extension.core.ExtensionException;
import com.openkm.omr.ImageManipulation;
import com.openkm.omr.ImageUtil;

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
	public static Map<String, File> trainingTemplate(File template) throws IOException, InvalidFileStructureException,
			InvalidImageIndexException, UnsupportedTypeException, MissingParameterException, WrongParameterException {
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
	public static Map<String, String> process(File fileToProcess, long omId) throws IOException, OMRException, DatabaseException,
			InvalidFileStructureException, InvalidImageIndexException, UnsupportedTypeException, MissingParameterException,
			WrongParameterException {
		Map<String, String> values = new HashMap<String, String>();
		File ascCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omId + ".asc");
		File configCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omId + ".config");
		File fieldsCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omId + ".fields");
		
		if (ascCache.exists() && configCache.exists() && fieldsCache.exists()) {
			Gray8Image grayimage = ImageUtil.readImage(fileToProcess.getCanonicalPath());
			if (grayimage==null) {
				throw new OMRException("Not able to process the image as gray image");
			}
			ImageManipulation image = new ImageManipulation(grayimage);
			image.locateConcentricCircles();
			image.readConfig(configCache.getCanonicalPath());
			image.readFields(fieldsCache.getCanonicalPath());
			image.readAscTemplate(ascCache.getCanonicalPath());
			image.searchMarks();
			File dataFile = FileUtils.createTempFile();
			image.saveData(dataFile.getCanonicalPath());
			// Parse data file
			Omr omr = OmrDAO.getInstance().findByPk(omId);
			FileInputStream dfStream = new FileInputStream(dataFile);
			DataInputStream in = new DataInputStream(dfStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				// format key=value ( looking for first = )
				String key = "";
				String value = "";
				if (strLine.contains("=")) {
					key = strLine.substring(0, strLine.indexOf("="));
					value = strLine.substring(strLine.indexOf("=") + 1);
					value = value.trim();
				}
				if (!key.equals("")) {
					if (value.equals("")) {
						throw new OMRException("Empty value");
					}
					if (omr.getProperties().contains(key)) {
						values.put(key, value);
					}
				}
			}
			in.close();
			FileUtils.deleteQuietly(dataFile);
			return values;
		} else {
			throw new OMRException("Error asc, config or fields files not found");
		}
	}
	
	/**
	 * storeMetadata
	 */
	public static void storeMetadata(Map<String, String> results, String docPath) throws IOException, ParseException,
			PathNotFoundException, RepositoryException, DatabaseException, NoSuchGroupException, LockException, AccessDeniedException,
			ExtensionException, AutomationException, NoSuchPropertyException, OMRException {
		List<String> groups = new ArrayList<String>();
		for (String key : results.keySet()) {
			if (key.contains(":")) {
				String grpName = key.substring(0, key.indexOf("."));
				// convert to okg (group name always start with okg )
				grpName = grpName.replace("okp", "okg"); 
				if (!groups.contains(grpName)) {
					groups.add(grpName);
				}
			}
		}
		// Add missing groups
		for (PropertyGroup registeredGroup : OKMPropertyGroup.getInstance().getGroups(null, docPath)) {
			if (groups.contains(registeredGroup.getName())) {
				groups.remove(registeredGroup.getName());
			}
		}
		// Add properties
		for (String grpName : groups) {
			OKMPropertyGroup.getInstance().addGroup(null, docPath, grpName);
			// convert okg to okp ( property format )
			String propertyBeginning = grpName.replace("okg", "okp"); 
			Map<String, String> properties = new HashMap<String, String>();
			for (String key : results.keySet()) {
				if (key.startsWith(propertyBeginning)) {
					String value = results.get(key);
					// Evaluate select multiple otherside throw exception
					if (value.contains(" ")) {
						for (FormElement formElement : OKMPropertyGroup.getInstance().getPropertyGroupForm(null, grpName)) {
							if (formElement.getName().equals(key) && formElement instanceof Select) {
								if (!((Select) formElement).getType().equals(Select.TYPE_MULTIPLE)) {
									throw new OMRException(
											"Found multiple value in a non multiple select. White space indicates multiple value");
								} else {
									// Change " " to ";" the way to pass
									// multiple values into setPropertiesSimple
									value = value.replaceAll(" ", ";");
								}
							}
						}
					}
					properties.put(key, value);
				}
			}
			OKMPropertyGroup.getInstance().setPropertiesSimple(null, docPath, grpName, properties);
		}
	}
	
	/**
	 * storeFilesToCache
	 */
	public static void storeFilesToCache(Omr omr) throws IOException {
		File ascCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omr.getId() + ".asc");
		if (omr.getAscFileContent() != null && omr.getAscFileContent().length != 0) {
			OutputStream ascFile = new FileOutputStream(ascCache);
			ascFile.write(omr.getAscFileContent());
			IOUtils.closeQuietly(ascFile);
		} else if (ascCache.exists()) {
			FileUtils.deleteQuietly(ascCache);
		}
		File configCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omr.getId() + ".config");
		if (omr.getConfigFileContent() != null && omr.getConfigFileContent().length != 0) {
			OutputStream configFile = new FileOutputStream(configCache);
			configFile.write(omr.getConfigFileContent());
			IOUtils.closeQuietly(configFile);
		} else if (configCache.exists()) {
			FileUtils.deleteQuietly(configCache);
		}
		File fieldsCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omr.getId() + ".fields");
		if (omr.getFieldsFileContent() != null && omr.getFieldsFileContent().length != 0) {
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
	public static void deleteCachedFiles(long omId) {
		File ascCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omId + ".asc");
		if (ascCache.exists()) {
			FileUtils.deleteQuietly(ascCache);
		}
		File configCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omId + ".config");
		if (configCache.exists()) {
			FileUtils.deleteQuietly(configCache);
		}
		File fieldsCache = new File(Config.REPOSITORY_CACHE_OMR + File.separator + omId + ".fields");
		if (fieldsCache.exists()) {
			FileUtils.deleteQuietly(fieldsCache);
		}
	}
}