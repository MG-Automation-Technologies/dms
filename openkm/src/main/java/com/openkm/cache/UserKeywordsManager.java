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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Property;
import com.openkm.core.RepositoryException;
import com.openkm.util.Serializer;

public class UserKeywordsManager {
	private static Logger log = LoggerFactory.getLogger(UserKeywordsManager.class);
	private static final String FILEALIZATION = "UserKeywordsManager";
	private static HashMap<String, HashMap<String, ArrayList<String>>> userDocKeywordsMgr;

	/**
	 * 
	 */
	public static HashMap<String, ArrayList<String>> get(String uid) throws RepositoryException {
		HashMap<String, ArrayList<String>> userDocKeywords = userDocKeywordsMgr.get(uid); 
		
		if (userDocKeywords == null) {
			userDocKeywords = new HashMap<String, ArrayList<String>>();
		}
		
		return userDocKeywords;
	}
	
	/**
	 * 
	 */
	public static synchronized void put(String uid, String doc, String keywords) {
		if (userDocKeywordsMgr.get(uid) == null) {
			userDocKeywordsMgr.put(uid, new HashMap<String, ArrayList<String>>());
		}
		
		userDocKeywordsMgr.get(uid).put(doc, splitKeywords(keywords));
	}
	
	/**
	 * 
	 */
	public static synchronized void update(String doc, String keywords) {
		//ArrayList<String> docKeywords = splitKeywords(keywords);
		
		/*
		for (Iterator<String> it = sm.getTokens().iterator(); it.hasNext(); ) {
			String uid = sm.getInfo(it.next()).getSession().getUserID();
			HashMap<String, ArrayList<String>> userDocKeywords = userDocKeywordsMgr.get(uid);
			
			if (userDocKeywords != null) {
				userDocKeywords.put(doc, docKeywords);
			}
		}
		*/
	}
	
	/**
	 * TODO: Not fully implemented
	 */
	public static synchronized void refreshUserDocKeywords(Session session) throws RepositoryException {
		log.info("refreshUserDocKeywords("+session+")");
		String statement = "/jcr:root/okm:root/element(*,okm:document)";
		
		try {
			Workspace workspace = session.getWorkspace();
			QueryManager queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, Query.XPATH);
			javax.jcr.query.QueryResult qResult = query.execute();
			HashMap<String, ArrayList<String>> userDocKeywords = new HashMap<String, ArrayList<String>>();
			
			for (NodeIterator nit = qResult.getNodes(); nit.hasNext(); ) {
				Node docNode = nit.nextNode();
				String keywords = docNode.getProperty(Property.KEYWORDS).getString();
				userDocKeywords.put(docNode.getUUID(), splitKeywords(keywords));
			}
			
			userDocKeywordsMgr.put(session.getUserID(), userDocKeywords);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.info("refreshUserDocKeywords: void");
	}
	
	/**
	 * 
	 */
	public static synchronized void serialize() {
		Serializer.write(FILEALIZATION, userDocKeywordsMgr);
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static synchronized void deserialize() {
		userDocKeywordsMgr = new HashMap<String, HashMap<String, ArrayList<String>>>();
		Object obj = Serializer.read(FILEALIZATION);
		if (obj != null) {
			userDocKeywordsMgr = (HashMap<String, HashMap<String, ArrayList<String>>>) obj;
		}
	}
	
	/**
	 * 
	 */
	private static ArrayList<String> splitKeywords(String keywords) {
		ArrayList<String> docKeywords = new ArrayList<String>();
		
		for (StringTokenizer st = new StringTokenizer(keywords); st.hasMoreTokens(); ) {
			String keyword = st.nextToken();
			docKeywords.add(keyword);
		}
		
		return docKeywords;
	}
}
