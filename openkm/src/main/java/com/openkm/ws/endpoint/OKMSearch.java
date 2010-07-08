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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebParam;
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
import com.openkm.ws.util.IntegerPair;
import com.openkm.ws.util.IntegerPairArray;

/**
 * Servlet Class
 * 
 * @web.servlet name="OKMSearch"
 * @web.servlet-mapping url-pattern="/OKMSearch"
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@SecurityDomain("OpenKM")
public class OKMSearch {
	private static Logger log = LoggerFactory.getLogger(OKMSearch.class);
	
	@WebMethod
	public QueryResult[] findByContent(@WebParam(name = "content") String content) throws IOException, 
			ParseException, RepositoryException, DatabaseException {
		log.debug("findByContent({})", content);
		SearchModule sm = ModuleManager.getSearchModule();
		List<QueryResult> col = sm.findByContent(content);
		QueryResult[] result = (QueryResult[]) col.toArray(new QueryResult[col.size()]);
		log.debug("findByContent: {}", result);
		return result;
	}

	@WebMethod
	public QueryResult[] findByName(@WebParam(name = "name") String name) throws IOException, 
			ParseException, RepositoryException, DatabaseException {
		log.debug("findByName({})", name);
		SearchModule sm = ModuleManager.getSearchModule();
		List<QueryResult> col = sm.findByName(name);
		QueryResult[] result = (QueryResult[]) col.toArray(new QueryResult[col.size()]);
		log.debug("findByName: {}", result);
		return result;
	}

	@WebMethod
	public QueryResult[] findByKeywords(@WebParam(name = "keywords") HashSet<String> keywords) throws
			IOException, ParseException, RepositoryException, DatabaseException {
		log.debug("findByKeywords({})", keywords);
		SearchModule sm = ModuleManager.getSearchModule();
		List<QueryResult> col = sm.findByKeywords(keywords);
		QueryResult[] result = (QueryResult[]) col.toArray(new QueryResult[col.size()]);
		log.debug("findByKeywords: {}", result);
		return result;
	}

	@WebMethod
	public QueryResult[] findByStatement(@WebParam(name = "statement") String statement,
			@WebParam(name = "type") String type) throws RepositoryException, DatabaseException {
		log.debug("findByStatement({})", statement);
		SearchModule sm = ModuleManager.getSearchModule();
		List<QueryResult> col = sm.findByStatement(statement, type);
		QueryResult[] result = (QueryResult[]) col.toArray(new QueryResult[col.size()]);
		log.debug("findByStatement: {}", result);
		return result;
	}
	
	@WebMethod
	public QueryResult[] find(@WebParam(name = "params") QueryParams params) throws IOException, 
			ParseException, RepositoryException, DatabaseException {
		log.debug("find({})", params);
		SearchModule sm = ModuleManager.getSearchModule();
		List<QueryResult> col = sm.find(params);
		QueryResult[] result = (QueryResult[]) col.toArray(new QueryResult[col.size()]);
		log.debug("find: {}", result);
		return result;
	}
	
	@WebMethod
	public IntegerPairArray getKeywordMap(@WebParam(name = "filter") String[] filter) throws 
			RepositoryException, DatabaseException {
		log.debug("getKeywordMap()");
		SearchModule sm = ModuleManager.getSearchModule();
		List<String> alFilter = Arrays.asList(filter);
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
	
	@WebMethod
	public Document[] getCategorizedDocuments(@WebParam(name = "categoryId") String categoryId) throws 
			RepositoryException, DatabaseException {
		log.debug("getCategorizedDocuments()");
		SearchModule sm = ModuleManager.getSearchModule();
		List<Document> col = sm.getCategorizedDocuments(categoryId);
		Document[] result = (Document[]) col.toArray(new Document[col.size()]);
		log.debug("getCategorizedDocuments: {}", result);
		return result;
	}
}
