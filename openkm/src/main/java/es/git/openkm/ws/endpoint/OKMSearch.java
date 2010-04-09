/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package es.git.openkm.ws.endpoint;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.QueryResult;
import es.git.openkm.core.ParseException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.module.ModuleManager;
import es.git.openkm.module.SearchModule;
import es.git.openkm.ws.util.QueryResultArray;

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
	 * @see es.git.openkm.module.SearchModule#findByContent(java.lang.String, java.lang.String)
	 */
	public QueryResultArray findByContent(String token, String words) throws ParseException,
			RepositoryException {
		log.debug("findByContent(" + token + ", " + words + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		qra.setValue((QueryResult[]) sm.findByContent(token, words).toArray(new QueryResult[0]));
		log.debug("findByContent: " + qra);
		return qra;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#findByName(java.lang.String, java.lang.String)
	 */
	public QueryResultArray findByName(String token, String words) throws ParseException,
			RepositoryException {
		log.debug("findByName(" + token + ", " + words + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		qra.setValue((QueryResult[]) sm.findByName(token, words).toArray(new QueryResult[0]));
		log.debug("findByName: " + qra);
		return qra;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#findByKeywords(java.lang.String, java.lang.String)
	 */
	public QueryResultArray findByKeywords(String token, String words) throws ParseException, 
			RepositoryException {
		log.debug("findByKeywords(" + token + ", " + words + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		qra.setValue((QueryResult[]) sm.findByKeywords(token, words).toArray(new QueryResult[0]));
		log.debug("findByKeywords: " + qra);
		return qra;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#findByStatement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public QueryResultArray findByStatement(String token, String statement, String type)
			throws RepositoryException {
		log.debug("findByStatement(" + token + ", " + statement + ")");
		SearchModule sm = ModuleManager.getSearchModule();
		QueryResultArray qra = new QueryResultArray();
		qra.setValue((QueryResult[]) sm.findByStatement(token, statement, type).toArray(new QueryResult[0]));
		log.debug("findByStatement: " + qra);
		return qra;
	}
}
