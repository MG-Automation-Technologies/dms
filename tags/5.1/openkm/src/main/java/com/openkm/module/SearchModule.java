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

package com.openkm.module;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.openkm.bean.Document;
import com.openkm.bean.QueryResult;
import com.openkm.bean.ResultSet;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.bean.QueryParams;

public interface SearchModule {

	/**
	 * Search for documents using it indexed content.
	 * 
	 * @param expression Expression to be searched.
	 * @return A collection of document which content matched the searched expression.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public List<QueryResult> findByContent(String token, String expression) throws IOException,
			ParseException, RepositoryException, DatabaseException;

	/**
	 * Search for documents by document name.
	 * 
	 * @param expression Expression to be searched.
	 * @return A collection of document which name matched the searched expression.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public List<QueryResult> findByName(String token, String expression) throws IOException, ParseException,
			RepositoryException, DatabaseException;

	/**
	 * Search for documents using it associated keywords.
	 * 
	 * @param expression Expression to be searched.
	 * @return A collection of document which keywords matched the searched expression.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public List<QueryResult> findByKeywords(String token, Set<String> expression) throws IOException,
			ParseException, RepositoryException, DatabaseException;

	/**
	 * Performs a complex search by content, name and keywords (between others).
	 * 
	 * @param params The complex search elements. 
	 * @return A collection of documents.
	 * @throws RepositoryException If there is any general repository problem.
	 * @throws IOException If something fails when parsing metadata.
	 */
	public List<QueryResult> find(String token, QueryParams params) throws IOException, ParseException,
			RepositoryException, DatabaseException;

	/**
	 * Performs a complex search by content, name and keywords. 
	 * Paginated version.
	 * 
	 * @param params The complex search elements.
	 * @param offset Query result list offset.
	 * @param limit Query result list limit.
	 * @return A result set with the total of the results and a collection of document
	 * from the resulting query statement.
	 * @throws RepositoryException If there is any general repository problem.
	 */
	public ResultSet findPaginated(String token, QueryParams params, int offset, int limit) throws
			IOException, ParseException, RepositoryException, DatabaseException;
	
	/**
	 * Search for documents and folder nodes specifying a complex query statement.
	 * 
	 * @param statement Query statement to be executed.
	 * @param type The query language can be Query.SQL ("sql") or Query.XPATH ("xpath") .
	 * @return A collection of document from the resulting query statement.
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public List<QueryResult> findByStatement(String token, String statement, String type) throws
			RepositoryException, DatabaseException;
	
	/**
	 * Search for documents and folder nodes specifying a complex query statement.
	 * Paginated version.
	 * 
	 * @param statement Query statement to be executed.
	 * @param type The query language can be "sql" or "xpath".
	 * @param offset Query result list offset.
	 * @param limit Query result list limit.
	 * @return A result set with the total of the results and a collection of document
	 * from the resulting query statement.
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public ResultSet findByStatementPaginated(String token, String statement, String type, int offset,
			int limit) throws RepositoryException, DatabaseException;

	/**
	 * Save a search for future use.
	 * 
	 * @param params The query params.
	 * @param name The name of the query to be saved.
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public int saveSearch(String token, QueryParams params) throws AccessDeniedException,
			RepositoryException, DatabaseException;
	
	/**
	 * Updated a saved search.
	 * 
	 * @param params The query params.
	 * @param name The name of the query to be saved.
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public void updateSearch(String token, QueryParams params) throws AccessDeniedException,
			RepositoryException, DatabaseException;
	
	/**
	 * Get a saved search.
	 * 
	 * @param name The name of the saved search to retrieve.
	 * @return The saved search query params.
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public QueryParams getSearch(String token, int qpId) throws PathNotFoundException, RepositoryException, 
			DatabaseException;
	
	/**
	 * Get all saved search.
	 * 
	 * @return A collection with the names of the saved search.
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public List<QueryParams> getAllSearchs(String token) throws RepositoryException, DatabaseException;
	
	/**
	 * Delete a saved search.
	 * 
	 * @param name The name of the saved search
	 * @throws PathNotFoundException If there is no saved search with this name.
	 * @throws RepositoryException If there is any general repository problem or the query fails
	 */
	public void deleteSearch(String token, int qpId) throws AccessDeniedException, RepositoryException,
			DatabaseException;
	
	/**
	 * Return a Keyword map. This is a hash with the keywords and the occurrence.
	 * 
	 * @param filter A collection of keywords used to obtain the related document keywords. 
	 * @return The keyword map.
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public Map<String, Integer> getKeywordMap(String token, List<String> filter) throws RepositoryException,
			DatabaseException;
	
	/**
	 * Get the documents within a category
	 * 
	 * @param categoryId The category id (UUID)
	 * @return A Collection of documents in the category
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public List<Document> getCategorizedDocuments(String token, String categoryId) throws
			RepositoryException, DatabaseException;
}