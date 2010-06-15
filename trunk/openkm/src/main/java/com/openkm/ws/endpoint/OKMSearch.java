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

package com.openkm.ws.endpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.jboss.annotation.security.SecurityDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Document;
import com.openkm.bean.QueryResult;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.bean.QueryParams;
import com.openkm.module.ModuleManager;
import com.openkm.module.SearchModule;
import com.openkm.ws.util.DocumentArray;
import com.openkm.ws.util.IntegerPair;
import com.openkm.ws.util.IntegerPairArray;
import com.openkm.ws.util.QueryResultArray;
import com.openkm.ws.util.StringArray;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMSearch"
 * @web.servlet-mapping url-pattern="/OKMSearch"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@SecurityDomain("OpenKM")
public class OKMSearch {
	private static Logger log = LoggerFactory.getLogger(OKMSearch.class);
	
	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByContent(java.lang.String, java.lang.String)
	 */
	public QueryResultArray findByContent(String words) throws IOException, ParseException, 
			RepositoryException, DatabaseException {
		log.debug("findByContent({})", words);
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		Collection<QueryResult> col = sm.findByContent(words);
		qra.setValue((QueryResult[]) col.toArray(new QueryResult[col.size()]));
		log.debug("findByContent: {}", qra);
		return qra;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByName(java.lang.String, java.lang.String)
	 */
	public QueryResultArray findByName(String words) throws IOException, ParseException, RepositoryException,
			DatabaseException {
		log.debug("findByName({})", words);
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		Collection<QueryResult> col = sm.findByName(words);
		qra.setValue((QueryResult[]) col.toArray(new QueryResult[col.size()]));
		log.debug("findByName: {}", qra);
		return qra;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByKeywords(java.lang.String, java.lang.String)
	 */
	public QueryResultArray findByKeywords(HashSet<String> words) throws IOException, ParseException, 
			RepositoryException, DatabaseException {
		log.debug("findByKeywords({})", words);
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		Collection<QueryResult> col = sm.findByKeywords(words);
		qra.setValue((QueryResult[]) col.toArray(new QueryResult[col.size()]));
		log.debug("findByKeywords: {}", qra);
		return qra;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByStatement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public QueryResultArray findByStatement(String statement, String type) throws RepositoryException,
			DatabaseException {
		log.debug("findByStatement({})", statement);
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		Collection<QueryResult> col = sm.findByStatement(statement, type);
		qra.setValue((QueryResult[]) col.toArray(new QueryResult[col.size()]));
		log.debug("findByStatement: {}", qra);
		return qra;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#find(java.lang.String, com.openkm.bean.QueryParams)
	 */
	public QueryResultArray find(QueryParams params) throws IOException, ParseException, RepositoryException,
			DatabaseException {
		log.debug("find({})", params);
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		Collection<QueryResult> col = sm.find(params);
		qra.setValue((QueryResult[]) col.toArray(new QueryResult[col.size()]));
		log.debug("find: {}", qra);
		return qra;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#getKeywordMap(java.lang.String, java.util.Collection)
	 */
	public IntegerPairArray getKeywordMap(StringArray filter) throws RepositoryException, DatabaseException {
		log.debug("getKeywordMap()");
		SearchModule sm = ModuleManager.getSearchModule();
		ArrayList<String> alFilter = new ArrayList<String>();
		String[] values = filter.getValue();
		
		for (int i=0; i<values.length; i++) {
			alFilter.add(values[i]);
		}
		
		Map<String, Integer> map = sm.getKeywordMap(alFilter);
		Set<String> keys = map.keySet();
		IntegerPair[] tmp = new IntegerPair[keys.size()];
		int i=0;
		
		for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
			String key = it.next();
			IntegerPair p = new IntegerPair();
			p.setKey(key);
			p.setValue((Integer) map.get(key));
			tmp[i++] = p;
		}
		
		IntegerPairArray uh = new IntegerPairArray();
		uh.setValue(tmp);
		log.debug("getKeywordMap: {}", uh);
		return uh;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#getCategorizedDocuments(java.lang.String, java.lang.String)
	 */
	public DocumentArray getCategorizedDocuments(String categoryId) throws RepositoryException,
			DatabaseException {
		log.debug("getCategorizedDocuments()");
		SearchModule sm = ModuleManager.getSearchModule();
		Collection<Document> col = sm.getCategorizedDocuments(categoryId);
		DocumentArray da = new DocumentArray();
		da.setValue((Document[]) col.toArray(new Document[col.size()]));
		log.debug("getCategorizedDocuments: {}", da);
		return da;
	}
}
