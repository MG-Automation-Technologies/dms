/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2014 Paco Avila & Josep Llort
 * 
 * No bytes were intentionally harmed during the development of this application.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.rest.endpoint;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.ResultSet;
import com.openkm.dao.bean.QueryParams;
import com.openkm.module.ModuleManager;
import com.openkm.module.SearchModule;
import com.openkm.rest.GenericException;
import com.openkm.rest.util.DocumentList;
import com.openkm.rest.util.KeywordMap;
import com.openkm.rest.util.KeywordMapList;
import com.openkm.rest.util.QueryParamsList;
import com.openkm.rest.util.QueryResultList;
import com.openkm.util.ISO8601;

@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class SearchService {
	private static Logger log = LoggerFactory.getLogger(SearchService.class);
	
	@GET
	@Path("/findByContent")
	public QueryResultList findByContent(@QueryParam("content") String content) throws GenericException {
		try {
			log.debug("findByContent({})", content);
			SearchModule sm = ModuleManager.getSearchModule();
			QueryResultList qrl = new QueryResultList();
			qrl.getList().addAll(sm.findByContent(null, content));
			log.debug("findByContent: {}", qrl);
			return qrl;
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@GET
	@Path("/findByName")
	public QueryResultList findByName(@QueryParam("name") String name) throws GenericException {
		try {
			log.debug("findByName({})", name);
			SearchModule sm = ModuleManager.getSearchModule();
			QueryResultList qrl = new QueryResultList();
			qrl.getList().addAll(sm.findByName(null, name));
			log.debug("findByName: {}", qrl);
			return qrl;
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@GET
	@Path("/findByKeywords")
	public QueryResultList findByKeywords(@QueryParam("keyword") List<String> keywords) throws GenericException {
		try {
			log.debug("findByKeywords({})", keywords);
			SearchModule sm = ModuleManager.getSearchModule();
			Set<String> set = new HashSet<String>(keywords);
			QueryResultList qrl = new QueryResultList();
			qrl.getList().addAll(sm.findByKeywords(null, set));
			log.debug("findByKeywords: {}", qrl);
			return qrl;
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@GET
	@Path("/find")
	// Default "domain" is "1" for documents.
	public QueryResultList find(@QueryParam("content") String content, @QueryParam("name") String name,
			@DefaultValue("1") @QueryParam("domain") int domain, @QueryParam("keyword") List<String> keywords,
			@QueryParam("category") List<String> categories, @QueryParam("property") List<String> properties,
			@QueryParam("author") String author, @QueryParam("mimeType") String mimeType,
			@QueryParam("lastModifiedFrom") String lastModifiedFrom, @QueryParam("lastModifiedTo") String lastModifiedTo,
			@QueryParam("mailSubject") String mailSubject, @QueryParam("mailFrom") String mailFrom, @QueryParam("mailTo") String mailTo)
			throws GenericException {
		try {
			QueryParams params = copyToQueryParams(content, name, domain, keywords, categories, properties, author, mimeType,
					lastModifiedFrom, lastModifiedTo, mailSubject, mailFrom, mailTo);
			log.debug("find({})", params);
			SearchModule sm = ModuleManager.getSearchModule();
			QueryResultList qrl = new QueryResultList();
			qrl.getList().addAll(sm.find(null, params));
			log.debug("find: {}", qrl);
			return qrl;
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@GET
	@Path("/findPaginated")
	// Default "domain" is "1" for documents.
	public ResultSet findPaginated(@DefaultValue("0") @QueryParam("offset") int offset, @DefaultValue("10") @QueryParam("limit") int limit,
			@QueryParam("content") String content, @QueryParam("name") String name, @DefaultValue("1") @QueryParam("domain") int domain,
			@QueryParam("keyword") List<String> keywords, @QueryParam("category") List<String> categories,
			@QueryParam("property") List<String> properties, @QueryParam("author") String author, @QueryParam("mimeType") String mimeType,
			@QueryParam("lastModifiedFrom") String lastModifiedFrom, @QueryParam("lastModifiedTo") String lastModifiedTo,
			@QueryParam("mailSubject") String mailSubject, @QueryParam("mailFrom") String mailFrom, @QueryParam("mailTo") String mailTo)
			throws GenericException {
		try {
			QueryParams params = copyToQueryParams(content, name, domain, keywords, categories, properties, author, mimeType,
					lastModifiedFrom, lastModifiedTo, mailSubject, mailFrom, mailTo);
			log.debug("findPaginated({})", params);
			SearchModule sm = ModuleManager.getSearchModule();
			ResultSet rs = sm.findPaginated(null, params, offset, limit);
			log.debug("findPaginated: {}", rs);
			return rs;
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@GET
	@Path("/findSimpleQueryPaginated")
	// Default "domain" is "1" for documents.
	public ResultSet findSimpleQueryPaginated(@DefaultValue("0") @QueryParam("offset") int offset,
			@DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("statement") String statement) throws GenericException {
		try {
			log.debug("findSimpleQueryPaginated({},{},{})", new Object[] { offset, limit, statement });
			SearchModule sm = ModuleManager.getSearchModule();
			ResultSet rs = sm.findSimpleQueryPaginated(null, statement, offset, limit);
			log.debug("findSimpleQueryPaginated: {}", rs);
			return rs;
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@GET
	@Path("/findSimpleQueryPaginated/{uuid}/{max}")
	// Default "domain" is "1" for documents.
	public ResultSet findMoreLikeThis(@PathParam("uuid") String uuid, @PathParam("max") int max) throws GenericException {
		try {
			log.debug("findMoreLikeThis({}, {})", uuid, max);
			SearchModule sm = ModuleManager.getSearchModule();
			ResultSet rs = sm.findMoreLikeThis(null, uuid, max);
			log.debug("findSimpleQueryPaginated: {}", rs);
			return rs;
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@GET
	@Path("/getKeywordMap")
	public KeywordMapList getKeywordMap(@QueryParam("filter") String[] filter) throws GenericException {
		try {
			log.debug("getKeywordMap({})", filter);
			SearchModule sm = ModuleManager.getSearchModule();
			List<String> alFilter = Arrays.asList(filter);
			Map<String, Integer> map = sm.getKeywordMap(null, alFilter);
			KeywordMapList kml = new KeywordMapList();
			
			// Marshall HashMap
			for (Entry<String, Integer> entry : map.entrySet()) {
				KeywordMap km = new KeywordMap();
				km.setKeyword(entry.getKey());
				km.setOccurs(entry.getValue());
				kml.getList().add(km);
			}
			
			log.debug("getKeywordMap: {}", kml);
			return kml;
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@GET
	@Path("/getCategorizedDocuments/{categoryId}")
	public DocumentList getCategorizedDocuments(@PathParam("categoryId") String categoryId) throws GenericException {
		try {
			log.debug("getCategorizedDocuments({})", categoryId);
			SearchModule sm = ModuleManager.getSearchModule();
			DocumentList dl = new DocumentList();
			dl.getList().addAll(sm.getCategorizedDocuments(null, categoryId));
			log.debug("getCategorizedDocuments: {}", dl);
			return dl;
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@POST
	@Path("/saveSearch")
	// The "params" parameter comes in the POST request body (encoded as XML or JSON).
	public long saveSearch(QueryParams params) throws GenericException {
		try {
			log.debug("saveSearch({})", params);
			SearchModule sm = ModuleManager.getSearchModule();
			long id = sm.saveSearch(null, params);
			log.debug("saveSearch: {}", id);
			return id;
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@PUT
	@Path("/updateSearch")
	// The "params" parameter comes in the PUT request body (encoded as XML or JSON).
	public void updateSearch(QueryParams params) throws GenericException {
		try {
			log.debug("updateSearch({})", params);
			SearchModule sm = ModuleManager.getSearchModule();
			sm.saveSearch(null, params);
			log.debug("updateSearch: void");
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@GET
	@Path("/getSearch/{qpId}")
	public QueryParams getSearch(@PathParam("qpId") int qpId) throws GenericException {
		try {
			log.debug("getSearch({})", qpId);
			SearchModule sm = ModuleManager.getSearchModule();
			QueryParams qp = sm.getSearch(null, qpId);
			log.debug("getSearch: {}", qp);
			return qp;
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@GET
	@Path("/getAllSearchs")
	public QueryParamsList getAllSearchs() throws GenericException {
		try {
			log.debug("getAllSearchs()");
			SearchModule sm = ModuleManager.getSearchModule();
			QueryParamsList qpl = new QueryParamsList();
			qpl.getList().addAll(sm.getAllSearchs(null));
			log.debug("getAllSearchs: {}", qpl);
			return qpl;
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	@DELETE
	@Path("/deleteSearch/{qpId}")
	public void deleteSearch(@PathParam("qpId") int qpId) throws GenericException {
		try {
			log.debug("deleteSearch({})", qpId);
			SearchModule sm = ModuleManager.getSearchModule();
			sm.deleteSearch(null, qpId);
			log.debug("deleteSearch: void");
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	/**
	 * copyToQueryParams
	 */
	private QueryParams copyToQueryParams(String content, String name, int domain, List<String> keywords, List<String> categories,
			List<String> properties, String author, String mimeType, String lastModifiedFrom, String lastModifiedTo, String mailSubject,
			String mailFrom, String mailTo) {
		QueryParams params = new QueryParams();
		Map<String, String> propMap = new HashMap<String, String>();
		
		for (String propVal : properties) {
			String[] keyVal = propVal.split("=");
			propMap.put(keyVal[0], keyVal[1]);
		}
		
		if (content != null && !content.equals("")) {
			params.setContent(content);
		}
		
		if (name != null && !name.equals("")) {
			params.setName(name);
		}
		
		params.setDomain(domain);
		params.setKeywords(new HashSet<String>(keywords));
		params.setCategories(new HashSet<String>(categories));
		params.setProperties(propMap);
		
		if (author != null && !author.equals("")) {
			params.setAuthor(author);
		}
		
		if (mimeType != null && !mimeType.equals("")) {
			params.setMimeType(mimeType);
		}
		
		if (lastModifiedFrom != null && !lastModifiedFrom.equals("")) {
			if (lastModifiedFrom.length() == 6) {
				lastModifiedFrom += "000000";
			}
			params.setLastModifiedFrom(ISO8601.parseBasic(lastModifiedFrom));
		}
		
		if (lastModifiedTo != null && !lastModifiedTo.equals("")) {
			if (lastModifiedTo.length() == 6) {
				lastModifiedTo += "000000";
			}
			params.setLastModifiedTo(ISO8601.parseBasic(lastModifiedTo));
		}
		
		if (mailSubject != null && !mailSubject.equals("")) {
			params.setMailSubject(mailSubject);
		}
		
		if (mailFrom != null && !mailFrom.equals("")) {
			params.setMailFrom(mailFrom);
		}
		
		if (mailTo != null && !mailTo.equals("")) {
			params.setMailTo(mailTo);
		}
		
		return params;
	}
}
