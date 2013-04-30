/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2013  Paco Avila & Josep Llort
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

package com.openkm.automation.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMDocument;
import com.openkm.api.OKMPropertyGroup;
import com.openkm.api.OKMRepository;
import com.openkm.automation.Action;
import com.openkm.automation.AutomationUtils;
import com.openkm.bean.PropertyGroup;
import com.openkm.dao.bean.NodeBase;
import com.openkm.dao.bean.NodeDocument;
import com.openkm.util.FileUtils;
import com.openkm.util.OMRUtils;

// INSERT INTO OKM_AUTO_METADATA (AMD_ID, AMD_AT, AMD_CLASS_NAME, AMD_NAME, AMD_GROUP, AMD_TYPE00, AMD_SRC00, AMD_DESC00, AMD_TYPE01, AMD_SRC01, AMD_DESC01) VALUES (124, 'post', 'com.openkm.automation.action.OMRCapture', 'OMRCapture', 'action', 'text', '', 'Template', '', '', '');

/**
 * OMRCapture
 * 
 * @author jllort
 *
 */
public class OMRCapture implements Action  {
	private static Logger log = LoggerFactory.getLogger(OMRCapture.class);
	
	@Override
	public void executePre(HashMap<String, Object> env, Object... params) {
	}
	
	@Override
	public void executePost(HashMap<String, Object> env, Object... params) {
		InputStream is = null;
		File fileToProcess = null;
		long omId = Long.parseLong(AutomationUtils.getString(0, params));
		NodeBase node = AutomationUtils.getNode(env);
		List<String> groups = new ArrayList<String>();
		
		try {
			if (node instanceof NodeDocument) {
				NodeDocument doc = (NodeDocument) node;
				fileToProcess = FileUtils.createTempFile();
				is = OKMDocument.getInstance().getContent(null, doc.getUuid(), false);
				FileUtils.copy(is, fileToProcess);
				is.close();
				Map<String, String> results = OMRUtils.process(fileToProcess, omId);
				for (String key : results.keySet()) {
					if (key.contains(":")) {
						String grpName = key.substring(0, key.indexOf("."));
						grpName = grpName.replace("okp", "okg"); // convert to okg ( group name always start with okg )
						if (!groups.contains(grpName)) {
							groups.add(grpName);
						}
					}
				}
				String docPath = OKMRepository.getInstance().getNodePath(null, doc.getUuid());
				for (PropertyGroup registeredGroup : OKMPropertyGroup.getInstance().getGroups(null, docPath)) {
					if (groups.contains(registeredGroup.getName())) {
						groups.remove(registeredGroup.getName());
					}
				}
				for (String grpName : groups) {
					OKMPropertyGroup.getInstance().addGroup(null, docPath, grpName);
					String propertyBeginning = grpName.replace("okg", "okp"); // convert to okp ( property format )
					Map<String, String> properties = new HashMap<String, String>();
					for (String key : results.keySet()) {
						if (key.startsWith(propertyBeginning)) {
							properties.put(key, results.get(key));
						}
					}
					OKMPropertyGroup.getInstance().setPropertiesSimple(null, docPath, grpName, properties);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (fileToProcess!=null) {
				FileUtils.deleteQuietly(fileToProcess);
			}
		}
	}
}
