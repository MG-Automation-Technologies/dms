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

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.DocumentFilterDAO;
import com.openkm.dao.bean.DocumentFilter;
import com.openkm.dao.bean.DocumentFilterRule;
import com.openkm.module.base.BasePropertyModule;

public class DocumentUtils {
	private static Logger log = LoggerFactory.getLogger(DocumentUtils.class);
	
	public static void checkFilters(Session session, Node node, String mimeType) throws DatabaseException, RepositoryException {
		log.info("checkFilters({}, {})", node.getPath(), mimeType);
		
		for (DocumentFilter df : DocumentFilterDAO.findAll(true)) {
			boolean match = false;
			
			if (df.isActive()) {
				if (df.getType().equals(DocumentFilter.TYPE_PATH) && checkPathFilter(node, df.getValue())) {
					match = true;
				} else if (df.getType().equals(DocumentFilter.TYPE_MIME_TYPE) && df.getValue().equals(mimeType)) {
					match = true;
				}
				
				if (match) {
					for (DocumentFilterRule dfr : df.getFilterRules()) {
						if (dfr.isActive()) {
							if (DocumentFilterRule.ACTION_PROPERTY_GROUP.equals(dfr.getAction())) {
								log.info("ACTION_PROPERTY_GROUP");
							} else if (DocumentFilterRule.ACTION_WORKFLOW.equals(dfr.getAction())) {
								log.info("ACTION_WORKFLOW");
							} else if (DocumentFilterRule.ACTION_CATEGORY.equals(dfr.getAction())) {
								try {
									log.info("ACTION_CATEGORY {}", dfr.getValue());
									BasePropertyModule.addCategory(session, node, dfr.getValue());
								} catch (Exception e) {
									JCRUtils.discardsPendingChanges(node);
								}
							} else if (DocumentFilterRule.ACTION_KEYWORD.equals(dfr.getAction())) {
								try {
									log.info("ACTION_KEYWORD: {}", dfr.getValue());
									BasePropertyModule.addKeyword(session, node, dfr.getValue());
								} catch (Exception e) {
									JCRUtils.discardsPendingChanges(node);
								}
							}
						}
					}
				}
			}
		}
	}
	
	private static boolean checkPathFilter(Node node, String path) throws RepositoryException {
		if (node.getPath().equals(path)) {
			return true;
		} else {
			return checkPathFilter(node.getParent(), path);
		}
	}
}
