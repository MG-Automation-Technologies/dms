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

package es.git.openkm.cache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Document;
import es.git.openkm.bean.cache.UserKeywords;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.util.Serializer;

public class UserKeywordsManager {
	private static Logger log = LoggerFactory.getLogger(UserKeywordsManager.class);
	private static final String FILEALIZATION = "UserKeywordsManager";
	private static HashMap<String, UserKeywords> userKeywordsMgr;
	
	static {
		deserialize();
	}
	
	/**
	 * 
	 */
	public static UserKeywords get(Session session) throws RepositoryException {
		UserKeywords userKeywords = userKeywordsMgr.get(session.getUserID()); 
		
		if (userKeywords == null) {
			updateCachedDocumentSearch(session);
			userKeywords = userKeywordsMgr.get(session.getUserID());
		}
		
		return userKeywords;
	}
	
	/**
	 * 
	 */
	public static synchronized void updateCachedDocumentSearch(Session session) throws RepositoryException {
		log.info("updateCachedDocumentSearch("+session+")");
		String statement = "/jcr:root/okm:root/element(*,okm:document)";
		
		try {
			Workspace workspace = session.getWorkspace();
			QueryManager queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, Query.XPATH);
			javax.jcr.query.QueryResult qResult = query.execute();
			ArrayList<String> keywords = new ArrayList<String>();
			
			for (NodeIterator nit = qResult.getNodes(); nit.hasNext(); ) {
				Node docNode = nit.nextNode();
				keywords.add(docNode.getProperty(Document.KEYWORDS).getString());
			}
			
			UserKeywords userKeywords = new UserKeywords();
			userKeywords.setKeywords(keywords);
			userKeywords.setSearchDate(Calendar.getInstance());
			userKeywordsMgr.put(session.getUserID(), userKeywords);
			
			serialize();
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.info("updateCachedDocumentSearch: void");
	}
	
	/**
	 * 
	 */
	private static synchronized void serialize() {
		Serializer.write(FILEALIZATION, userKeywordsMgr);
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private static synchronized void deserialize() {
		userKeywordsMgr = new HashMap<String, UserKeywords>();
		Object obj = Serializer.read(FILEALIZATION);
		if (obj != null) {
			userKeywordsMgr = (HashMap<String, UserKeywords>) obj;
		}
	}
}
