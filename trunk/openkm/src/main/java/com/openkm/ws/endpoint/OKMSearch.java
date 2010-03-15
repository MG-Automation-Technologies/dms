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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Document;
import com.openkm.bean.QueryParams;
import com.openkm.bean.QueryResult;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;
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
public class OKMSearch {
	private static Logger log = LoggerFactory.getLogger(OKMSearch.class);
	
	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByContent(java.lang.String, java.lang.String)
	 */
	public QueryResultArray findByContent(String token, String words) throws ParseException, 
			RepositoryException {
		log.debug("findByContent(" + token + ", " + words + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		Collection<QueryResult> col = sm.findByContent(token, words);
		qra.setValue((QueryResult[]) col.toArray(new QueryResult[col.size()]));
		log.debug("findByContent: " + qra);
		return qra;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByName(java.lang.String, java.lang.String)
	 */
	public QueryResultArray findByName(String token, String words) throws ParseException,
			RepositoryException {
		log.debug("findByName(" + token + ", " + words + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		Collection<QueryResult> col = sm.findByName(token, words);
		qra.setValue((QueryResult[]) col.toArray(new QueryResult[col.size()]));
		log.debug("findByName: " + qra);
		return qra;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByKeywords(java.lang.String, java.lang.String)
	 */
	public QueryResultArray findByKeywords(String token, String words)throws ParseException,
			RepositoryException {
		log.debug("findByKeywords(" + token + ", " + words + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		Collection<QueryResult> col = sm.findByKeywords(token, words);
		qra.setValue((QueryResult[]) col.toArray(new QueryResult[col.size()]));
		log.debug("findByKeywords: " + qra);
		return qra;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByStatement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public QueryResultArray findByStatement(String token, String statement, String type)
			throws RepositoryException {
		log.debug("findByStatement(" + token + ", " + statement + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		Collection<QueryResult> col = sm.findByStatement(token, statement, type);
		qra.setValue((QueryResult[]) col.toArray(new QueryResult[col.size()]));
		log.debug("findByStatement: " + qra);
		return qra;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#find(java.lang.String, com.openkm.bean.QueryParams)
	 */
	public QueryResultArray find(String token, QueryParams params) throws ParseException,
			RepositoryException {
		log.debug("find({}, {})", token, params);
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		Collection<QueryResult> col = sm.find(token, params);
		qra.setValue((QueryResult[]) col.toArray(new QueryResult[col.size()]));
		log.debug("find: {}", qra);
		return qra;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#getKeywordMap(java.lang.String, java.util.Collection)
	 */
	public IntegerPairArray getKeywordMap(String token, StringArray filter) 
			throws RepositoryException {
		log.debug("getKeywordMap({})", token);
		SearchModule sm = ModuleManager.getSearchModule();
		ArrayList<String> alFilter = new ArrayList<String>();
		String[] values = filter.getValue();
		
		for (int i=0; i<values.length; i++) {
			alFilter.add(values[i]);
		}
		
		Map<String, Integer> map = sm.getKeywordMap(token, alFilter);
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
	public DocumentArray getCategorizedDocuments(String token, String categoryId)
			throws RepositoryException {
		log.debug("getCategorizedDocuments({})", token);
		SearchModule sm = ModuleManager.getSearchModule();
		Collection<Document> col = sm.getCategorizedDocuments(token, categoryId);
		DocumentArray da = new DocumentArray();
		da.setValue((Document[]) col.toArray(new Document[col.size()]));
		log.debug("getCategorizedDocuments: {}", da);
		return da;
	}
}
