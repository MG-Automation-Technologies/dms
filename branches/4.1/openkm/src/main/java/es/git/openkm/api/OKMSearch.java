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

package es.git.openkm.api;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.QueryParams;
import es.git.openkm.bean.QueryResult;
import es.git.openkm.bean.ResultSet;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.ParseException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.module.ModuleManager;
import es.git.openkm.module.SearchModule;

/**
 * @author pavila
 *
 */
public class OKMSearch implements SearchModule {
	private static Logger log = LoggerFactory.getLogger(OKMSearch.class);
	private static OKMSearch instance = new OKMSearch();

	private OKMSearch() {}
	
	public static OKMSearch getInstance() {
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#findByContent(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<QueryResult> findByContent(String token, String words) throws ParseException,
			RepositoryException {
		log.debug("findByContent(" + token + ", " + words + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		Collection<QueryResult> col = sm.findByContent(token, words);
		log.debug("findByContent: " + col);
		return col;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#findByName(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<QueryResult> findByName(String token, String words) throws ParseException,
			RepositoryException {
		log.debug("findByName(" + token + ", " + words + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		Collection<QueryResult> col = sm.findByName(token, words);
		log.debug("findByName: " + col);
		return col;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#findByKeywords(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<QueryResult> findByKeywords(String token, String words) throws ParseException,
			RepositoryException {
		log.debug("findByKeywords(" + token + ", " + words + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		Collection<QueryResult> col = sm.findByKeywords(token, words);
		log.debug("findByKeywords: " + col);
		return col;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#find(java.lang.String, es.git.openkm.bean.QueryParams)
	 */
	@Override
	public Collection<QueryResult> find(String token, QueryParams params) throws IOException, ParseException,
			RepositoryException {
		log.debug("find(" + token + ", " + params + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		Collection<QueryResult> col = sm.find(token, params);
		log.debug("find: " + col);
		return col;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#findPaginated(java.lang.String, es.git.openkm.bean.QueryParams, int, int)
	 */
	@Override
	public ResultSet findPaginated(String token, QueryParams params, int offset, int limit) 
			throws IOException, ParseException, RepositoryException {
		log.debug("find(" + token + ", " + params + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		ResultSet rs = sm.findPaginated(token, params, offset, limit);
		log.debug("find: " + rs);
		return rs;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#findByStatement(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public Collection<QueryResult> findByStatement(String token, String statement, String type) 
			throws RepositoryException {
		log.debug("findByStatement(" + token + ", " + statement + ", " + type + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		Collection<QueryResult> col = sm.findByStatement(token, statement, type);
		log.debug("findByKeywords: " + col);
		return col;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#findByStatementPaginated(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public ResultSet findByStatementPaginated(String token, String statement, String type, int offset, int limit)
			throws RepositoryException {
		log.debug("findByStatement(" + token + ", " + statement + ", " + type + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		ResultSet rs = sm.findByStatementPaginated(token, statement, type, offset, limit);
		log.debug("findByKeywords: " + rs);
		return rs;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#saveSearch(java.lang.String, es.git.openkm.bean.QueryParams, java.lang.String)
	 */
	@Override
	public void saveSearch(String token, QueryParams params, String name)
			throws ItemExistsException, RepositoryException {
		log.debug("saveSearch(" + token + ", " + params + ", " + name + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		sm.saveSearch(token, params, name);
		log.debug("saveSearch: void");
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#runSearch(java.lang.String, java.lang.String)
	 */
	@Override
	public QueryParams getSearch(String token, String name) throws PathNotFoundException,
			RepositoryException {
		log.debug("getSearch(" + token + ", " + name + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		QueryParams qp = sm.getSearch(token, name);
		log.debug("getSearch: " + qp);
		return qp;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#getAllSearchs(java.lang.String)
	 */
	@Override
	public Collection<String> getAllSearchs(String token) throws RepositoryException {
		log.debug("getAllSearchs(" + token + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		Collection<String> col = sm.getAllSearchs(token);
		log.debug("getAllSearchs: " + col);
		return col;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#deleteSearch(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteSearch(String token, String name) throws PathNotFoundException,
			RepositoryException {
		log.debug("deleteSearch(" + token + ", " + name + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		sm.deleteSearch(token, name);
		log.debug("deleteSearch: void");
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#getKeywordMap(java.lang.String, java.util.Collection)
	 */
	@Override
	public Map<String, Integer> getKeywordMap(String token, Collection<String> filter) 
			throws RepositoryException {
		log.debug("getKeywordMap(" + token + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		Map<String, Integer> kmap = sm.getKeywordMap(token, filter);
		log.debug("getKeywordMap: " + kmap);
		return kmap;
	}
}
