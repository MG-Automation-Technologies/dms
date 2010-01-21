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

package com.openkm.frontend.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.api.OKMSearch;
import com.openkm.bean.QueryParams;
import com.openkm.bean.QueryResult;
import com.openkm.bean.ResultSet;
import com.openkm.core.ItemExistsException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTKeyword;
import com.openkm.frontend.client.bean.GWTQueryParams;
import com.openkm.frontend.client.bean.GWTQueryResult;
import com.openkm.frontend.client.bean.GWTResultSet;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.service.OKMSearchService;
import com.openkm.frontend.client.util.KeywordComparator;

/**
 * Servlet Class
 * 
 * @web.servlet              name="OKMSearchServlet"
 *                           display-name="Directory tree service"
 *                           description="Directory tree service"
 * @web.servlet-mapping      url-pattern="/OKMSearchServlet"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class OKMSearchServlet extends OKMRemoteServiceServlet implements OKMSearchService {
	private static Logger log = LoggerFactory.getLogger(OKMSearchServlet.class);
	private static final long serialVersionUID = 8673521252684830906L;
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMSearchService#getSearchs()
	 */
	public List<String> getAllSearchs() throws OKMException {
		log.debug("getAllSearchs()");
		List<String> resultList = new ArrayList<String>(); 
		String token = getToken();
		
		try {
			Collection<String> col = OKMSearch.getInstance().getAllSearchs(token);
			
			for (Iterator<String> it = col.iterator(); it.hasNext();) {		
				String search = it.next();
				log.debug("search: "+search);
				
				resultList.add(search);
			}
		}  catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getAllSearchs: "+resultList);
		return resultList;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMSearchService#saveSearch(com.openkm.frontend.client.bean.GWTQueryParams, java.lang.String, java.lang.String)
	 */
	public void saveSearch(GWTQueryParams params, String type, String name) throws OKMException {
		log.debug("saveSearch("+params+", "+type+", "+name+")");
		String token = getToken();
		
		try {
			OKMSearch.getInstance().saveSearch(token, Util.copy(params), name);
		} catch (ItemExistsException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_ItemExists), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("saveSearch: ");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMSearchService#getSearch(java.lang.String)
	 */
	public GWTQueryParams getSearch (String name) throws OKMException {
		log.debug("getSearch("+ name +")");
		GWTQueryParams gwtQueryParams = new GWTQueryParams();
		String token = getToken();
		
		try {
			QueryParams results = OKMSearch.getInstance().getSearch(token, name);
			gwtQueryParams = Util.copy(results, token);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_IOException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("getSearch: "+gwtQueryParams);
		return gwtQueryParams;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMSearchService#deleteSearch(java.lang.String)
	 */
	public void deleteSearch(String name) throws OKMException {
		log.debug("deleteSearch()");
		String token = getToken();
		
		try {
			OKMSearch.getInstance().deleteSearch(token,name);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("deleteSearch: ");
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMSearchService#findPaginated(com.openkm.frontend.client.bean.GWTQueryParams, int, int)
	 */
	public GWTResultSet findPaginated(GWTQueryParams params, int offset, int limit) throws OKMException {
		log.debug("findPaginated("+offset+","+limit+")");
		List<GWTQueryResult> resultList = new ArrayList<GWTQueryResult>(); 
		String token = getToken();
		ResultSet results;
		GWTResultSet gwtResultSet = new GWTResultSet();
		QueryParams queryParams = new QueryParams();
		
		try {
			queryParams = Util.copy(params);
			results = OKMSearch.getInstance().findPaginated(token, queryParams, offset, limit);
			
			for (Iterator<QueryResult> it = results.getResults().iterator(); it.hasNext();) {		
				QueryResult queryResult = it.next();
				GWTQueryResult gwtQueryResult = Util.copy(queryResult);
				
				resultList.add(gwtQueryResult);
			}
			
			gwtResultSet.setTotal(results.getTotal());
			gwtResultSet.setResults(resultList);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_IOException), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("findPaginated: "+resultList);
		return gwtResultSet;
	}
	

	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMSearchService#find(com.openkm.frontend.client.bean.GWTQueryParams)
	 */
	public GWTResultSet find(GWTQueryParams params) throws OKMException {
		log.debug("find()");
		List<GWTQueryResult> resultList = new ArrayList<GWTQueryResult>(); 
		String token = getToken();
		Collection<QueryResult> results;
		GWTResultSet gwtResultSet = new GWTResultSet();
		QueryParams queryParams = new QueryParams();
		
		try {
			queryParams = Util.copy(params);
			results = OKMSearch.getInstance().find(token, queryParams);
			
			for (Iterator<QueryResult> it = results.iterator(); it.hasNext();) {		
				QueryResult queryResult = it.next();
				GWTQueryResult gwtQueryResult = Util.copy(queryResult);
				
				resultList.add(gwtQueryResult);
			}
			
			gwtResultSet.setTotal(results.size());
			gwtResultSet.setResults(resultList);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_IOException), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("find: "+resultList);
		return gwtResultSet;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.frontend.client.service.OKMSearchService#getKeywordMap(java.util.Collection)
	 */
	public List<GWTKeyword> getKeywordMap(Collection<String> filter) throws OKMException {
		log.debug("getKeywordMap()");
		List<GWTKeyword> keyList = new ArrayList<GWTKeyword>();
		String token = getToken();
		int countTop10 = 0;
		int maxValues[] = new int[10];
		List<GWTKeyword> selectedTop10 = new ArrayList<GWTKeyword>();
		
		try {
			Map<String, Integer> keyMap = OKMSearch.getInstance().getKeywordMap(token, filter);
			for (Iterator<String> it = keyMap.keySet().iterator(); it.hasNext();)  {
				String key = it.next();
				GWTKeyword keyword = new GWTKeyword();
				keyword.setKeyword(key);
				keyword.setFrequency(keyMap.get(key).intValue());
				keyword.setTop10(false);
				
				// Identifiying the top 10 max values
				if (countTop10<10) {
					maxValues[countTop10]=keyword.getFrequency();
					selectedTop10.add(keyword);
					countTop10++;
				} else {
					Arrays.sort(maxValues); // Minimal value is maxValues[0] ( ordering is incremental )
					if (maxValues[0]<keyword.getFrequency()) {
						boolean found = false;
						int index = 0;
						while (!found && index<selectedTop10.size()) {
							if (selectedTop10.get(index).getFrequency()==maxValues[0]) {
								found = true;
								selectedTop10.remove(index);
								maxValues[0] = keyword.getFrequency(); // Adding value to max values list
								selectedTop10.add(keyword);			// Adding object to top selected
							}
							index++;
						}
					}
				}
				keyList.add(keyword);
			}
			
			// Marks selectedTop10 as selected
			for (Iterator<GWTKeyword> it = selectedTop10.iterator(); it.hasNext();) {
				it.next().setTop10(true);
			}
			
			Collections.sort(keyList, KeywordComparator.getInstance());
			
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_Repository), e.getMessage());
		} 
		
		log.debug("getKeywordMap: ");
		return keyList;
	}
}
