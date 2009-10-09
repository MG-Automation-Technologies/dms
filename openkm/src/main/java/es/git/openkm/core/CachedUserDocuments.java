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

package es.git.openkm.core;

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

import es.git.openkm.bean.CachedDocumentSearch;
import es.git.openkm.bean.Document;
import es.git.openkm.bean.Version;
import es.git.openkm.util.Serializer;

public class CachedUserDocuments {
	private static Logger log = LoggerFactory.getLogger(CachedUserDocuments.class);
	private static final String FILEALIZATION = "CachedUserDocuments";
	private static HashMap<String, CachedDocumentSearch> cachedUsersDocs;
	
	static {
		deserialize();
	}
	
	/**
	 * @param uid
	 * @return
	 * @throws RepositoryException 
	 */
	public static CachedDocumentSearch getCachedDocumentSearch(Session session) throws RepositoryException {
		CachedDocumentSearch cachedDocumentSearch = cachedUsersDocs.get(session.getUserID()); 
		
		if (cachedDocumentSearch == null) {
			updateCachedDocumentSearch(session);
			cachedDocumentSearch = cachedUsersDocs.get(session.getUserID());
		}
		
		return cachedDocumentSearch;
	}
	
	/**
	 * @param session
	 * @throws RepositoryException
	 */
	public static synchronized void updateCachedDocumentSearch(Session session) throws RepositoryException {
		log.info("updateCachedDocumentSearch("+session+")");
		String statement = "/jcr:root//element(*,okm:document)";
		
		try {
			Workspace workspace = session.getWorkspace();
			QueryManager queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, Query.XPATH);
			javax.jcr.query.QueryResult qResult = query.execute();
			ArrayList<Document> documents = new ArrayList<Document>();
			
			for (NodeIterator nit = qResult.getNodes(); nit.hasNext(); ) {
				Node docNode = nit.nextNode();
				Node cntNode = docNode.getNode(Document.CONTENT);
				Document doc = new Document();
				Version ver = new Version();
				
				ver.setSize(cntNode.getProperty(Document.SIZE).getLong());
				ver.setAuthor(cntNode.getProperty(Document.AUTHOR).getString());
				doc.setKeywords(docNode.getProperty(Document.KEYWORDS).getString());
				doc.setActualVersion(ver);
				
				documents.add(doc);
			}
			
			CachedDocumentSearch cachedDocumentSearch = new CachedDocumentSearch();
			cachedDocumentSearch.setDocuments(documents);
			cachedDocumentSearch.setSearchDate(Calendar.getInstance());
			cachedUsersDocs.put(session.getUserID(), cachedDocumentSearch);
			
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
		Serializer.write(FILEALIZATION, cachedUsersDocs);
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private static synchronized void deserialize() {
		cachedUsersDocs = new HashMap<String, CachedDocumentSearch>();
		Object obj = Serializer.read(FILEALIZATION);
		if (obj != null) {
			cachedUsersDocs = (HashMap<String, CachedDocumentSearch>)obj;
		}
	}
}
