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
import com.openkm.bean.QueryResult;
import com.openkm.bean.ResultSet;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.bean.QueryParams;
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
	
	@Override
	public List<GWTQueryParams> getAllSearchs() throws OKMException {
		log.debug("getAllSearchs()");
		List<GWTQueryParams> resultList = new ArrayList<GWTQueryParams>();
		updateSessionManager();
		
		try {
			for (Iterator<QueryParams> it = OKMSearch.getInstance().getAllSearchs(null).iterator(); it.hasNext();) {		
				resultList.add(Util.copy(it.next()));
			}
		}  catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("getAllSearchs: {}", resultList);
		return resultList;
	}
	
	@Override
	public Integer saveSearch(GWTQueryParams params, String type) throws OKMException {
		log.debug("saveSearch({}, {}, {})", new Object[] { params, type });
		updateSessionManager();
		
		try {
			return OKMSearch.getInstance().saveSearch(null, Util.copy(params));
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_General), e.getMessage());
		}
	}
	
	@Override
	public void deleteSearch(int id) throws OKMException {
		log.debug("deleteSearch()");
		updateSessionManager();
		
		try {
			OKMSearch.getInstance().deleteSearch(null, id);
		} catch (PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_PathNotFound), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("deleteSearch: void");
	}
	
	@Override
	public GWTResultSet findPaginated(GWTQueryParams params, int offset, int limit) throws OKMException {
		log.debug("findPaginated({}, {}, {})", new Object[] { params, offset, limit });
		List<GWTQueryResult> resultList = new ArrayList<GWTQueryResult>(); 
		GWTResultSet gwtResultSet = new GWTResultSet();
		QueryParams queryParams = new QueryParams();
		ResultSet results;
		updateSessionManager();
		
		try {
			queryParams = Util.copy(params);
			results = OKMSearch.getInstance().findPaginated(null, queryParams, offset, limit);
			
			for (Iterator<QueryResult> it = results.getResults().iterator(); it.hasNext();) {		
				QueryResult queryResult = it.next();
				GWTQueryResult gwtQueryResult = Util.copy(queryResult);
				
				resultList.add(gwtQueryResult);
			}
			
			gwtResultSet.setTotal(results.getTotal());
			gwtResultSet.setResults(resultList);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_ParseException), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("findPaginated: {}", resultList);
		return gwtResultSet;
	}
	
	@Override
	public GWTResultSet find(GWTQueryParams params) throws OKMException {
		log.debug("find({})", params);
		List<GWTQueryResult> resultList = new ArrayList<GWTQueryResult>(); 
		GWTResultSet gwtResultSet = new GWTResultSet();
		QueryParams queryParams = new QueryParams();
		Collection<QueryResult> results;
		updateSessionManager();
		
		try {
			queryParams = Util.copy(params);
			results = OKMSearch.getInstance().find(null, queryParams);
			
			for (Iterator<QueryResult> it = results.iterator(); it.hasNext();) {		
				QueryResult queryResult = it.next();
				GWTQueryResult gwtQueryResult = Util.copy(queryResult);
				
				resultList.add(gwtQueryResult);
			}
			
			gwtResultSet.setTotal(results.size());
			gwtResultSet.setResults(resultList);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_ParseException), e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_Repository), e.getMessage());
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_General), e.getMessage());
		}
		
		log.debug("find: {}", resultList);
		return gwtResultSet;
	}
	
	@Override
	public List<GWTKeyword> getKeywordMap(List<String> filter) throws OKMException {
		log.debug("getKeywordMap()");
		List<GWTKeyword> selectedTop10 = new ArrayList<GWTKeyword>();
		List<GWTKeyword> keyList = new ArrayList<GWTKeyword>();
		int maxValues[] = new int[10];
		int countTop10 = 0;
		updateSessionManager();
		
		try {
			Map<String, Integer> keyMap = OKMSearch.getInstance().getKeywordMap(null, filter);
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
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_DatabaseException), e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OKMSearchService, ErrorCode.CAUSE_General), e.getMessage());
		} 
		
		log.debug("getKeywordMap: {}", keyList);
		return keyList;
	}
}
