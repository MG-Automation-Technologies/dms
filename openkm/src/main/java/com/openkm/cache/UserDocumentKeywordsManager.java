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

package com.openkm.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Property;
import com.openkm.core.DatabaseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.UserDocumentKeywordsDAO;
import com.openkm.dao.bean.UserDocumentKeywords;

public class UserDocumentKeywordsManager {
	private static Logger log = LoggerFactory.getLogger(UserDocumentKeywordsManager.class);
	private static Map<String, Set<UserDocumentKeywords>> userDocumentKeywordsMgr;

	/**
	 * 
	 */
	public static Set<UserDocumentKeywords> get(String uid) throws RepositoryException {
		Set<UserDocumentKeywords> userDocKeywords = userDocumentKeywordsMgr.get(uid);
		
		if (userDocKeywords == null) {
			userDocKeywords = new HashSet<UserDocumentKeywords>();
		}
		
		return userDocKeywords;
	}
	
	/**
	 * TODO: Not fully implemented
	 */
	public static synchronized void refreshUserDocKeywords(Session session) throws RepositoryException {
		log.info("refreshUserDocKeywords({})", session);
		String statement = "/jcr:root/okm:root/element(*,okm:document)";
		
		try {
			Workspace workspace = session.getWorkspace();
			QueryManager queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, Query.XPATH);
			javax.jcr.query.QueryResult qResult = query.execute();
			Map<String, Set<String>> userDocKeywords = new HashMap<String, Set<String>>();
			
			for (NodeIterator nit = qResult.getNodes(); nit.hasNext(); ) {
				Node docNode = nit.nextNode();
				Value[] keywords = docNode.getProperty(Property.KEYWORDS).getValues();
				Set<String> keywordSet = new HashSet<String>();
				
				for (int i=0; i<keywords.length; i++) {
					keywordSet.add(keywords[i].getString());
				}
				
				userDocKeywords.put(docNode.getUUID(), keywordSet);
			}
			
			//userDocumentKeywordsMgr.put(session.getUserID(), userDocKeywords);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.info("refreshUserDocKeywords: void");
	}
	
	/**
	 * 
	 */
	public static synchronized void serialize() throws DatabaseException {
		for (String user : userDocumentKeywordsMgr.keySet()) {
			for (UserDocumentKeywords udk : userDocumentKeywordsMgr.get(user)) {
				UserDocumentKeywordsDAO.update(udk);
			}
		}
	}
	
	/**
	 * 
	 */
	public static synchronized void deserialize() throws DatabaseException {
		userDocumentKeywordsMgr = new HashMap<String, Set<UserDocumentKeywords>>();
		
		for (String user : UserDocumentKeywordsDAO.findUsers()) {
			Set<UserDocumentKeywords> udkSet = new HashSet<UserDocumentKeywords>();
			
			for (UserDocumentKeywords udk: UserDocumentKeywordsDAO.findByUser(user)) {
				udkSet.add(udk);
			}
			
			userDocumentKeywordsMgr.put(user, udkSet);
		}
	}
}
