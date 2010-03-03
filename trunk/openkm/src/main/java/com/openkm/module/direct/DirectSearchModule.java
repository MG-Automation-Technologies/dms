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

package com.openkm.module.direct;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.InvalidItemStateException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;
import javax.jcr.version.VersionException;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.core.query.QueryImpl;
import org.apache.jackrabbit.util.ISO8601;
import org.apache.jackrabbit.util.ISO9075;
import org.apache.jackrabbit.util.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Mail;
import com.openkm.bean.MetaData;
import com.openkm.bean.PropertyGroup;
import com.openkm.bean.QueryParams;
import com.openkm.bean.QueryResult;
import com.openkm.bean.Repository;
import com.openkm.bean.ResultSet;
import com.openkm.cache.UserKeywordsManager;
import com.openkm.core.Config;
import com.openkm.core.ItemExistsException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.core.SessionManager;
import com.openkm.module.SearchModule;
import com.openkm.util.UserActivity;

public class DirectSearchModule implements SearchModule {
	private static Logger log = LoggerFactory.getLogger(DirectSearchModule.class);

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByContent(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<QueryResult> findByContent(String token, String words) throws RepositoryException {
		log.debug("findByContent(" + token + ", " + words + ")");

		QueryParams params = new QueryParams();
		params.setContent(words);
		Collection<QueryResult> ret = null;
		
		try {
			ret = find(token, params);
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.debug("findByContent: " + ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByName(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<QueryResult> findByName(String token, String words) throws RepositoryException {
		log.debug("findByName(" + token + ", " + words + ")");

		QueryParams params = new QueryParams();
		params.setName(words);
		Collection<QueryResult> ret = null;
		
		try {
			ret = find(token, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		log.debug("findByName: " + ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByKeywords(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<QueryResult> findByKeywords(String token, String words) throws RepositoryException {
		log.debug("findByKeywords(" + token + ", " + words + ")");

		QueryParams params = new QueryParams();
		params.setKeywords(words);
		Collection<QueryResult> ret = null;
		
		try {
			ret = find(token, params);
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.debug("findByKeywords: " + ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#find(java.lang.String, com.openkm.bean.QuestionParams)
	 */
	@Override
	public Collection<QueryResult> find(String token, QueryParams params) throws IOException, RepositoryException {
		log.debug("find(" + token + ", " + params + ")");
		Collection<QueryResult> ret = findPaginated(token, params, 0, Config.MAX_SEARCH_RESULTS).getResults();
		log.debug("find: " + ret);
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findPaginated(java.lang.String, com.openkm.bean.QueryParams, int, int)
	 */
	@Override
	public ResultSet findPaginated(String token, QueryParams params, int offset, int limit) throws IOException, RepositoryException {
		log.debug("findPaginated(" + token + ", " + params + ")");
		String query = prepareStatement(params);
		ResultSet rs = findByStatementPaginated(token, query, "xpath", offset, limit);
		log.debug("findPaginated: " + rs);
		return rs;
	}
	
	/**
	 * Escape jcr:contains searchExp (view 6.6.5.2)
	 *  
	 * @param str The String to be escaped.
	 * @return The escaped String.
	 */
	private String escapeContains(String str) {
		String ret = str;
		//ret = ret.replace("\\", "\\\\");
		//ret = ret.replace("'", "\\'");
		//ret = ret.replace("-", "\\-");
		ret = ret.replace("\"", "\\\"");
		ret = ret.replace("[", "\\[");
		ret = ret.replace("]", "\\]");
		ret = Text.escapeIllegalXpathSearchChars(ret);
		ret = escapeXPath(ret);
		return ret;
	}

	/**
	 * Escape XPath string
	 *  
	 * @param str The String to be escaped.
	 * @return The escaped String.
	 */
	private String escapeXPath(String str) {
		String ret = str.replace("'", "''");
		return ret;
	}

	/**
	 * @param params
	 * @return
	 * @throws IOException 
	 */
	public String prepareStatement(QueryParams params) throws IOException {
		log.info("prepareStatement("+params+")");
		StringBuffer sb = new StringBuffer();
		
		// Clean params
		params.setName(params.getName() != null?params.getName().trim():""); 
		params.setContent(params.getContent() != null?params.getContent().trim():"");
		params.setKeywords(params.getKeywords() != null?params.getKeywords().trim():"");
		params.setCategories(params.getCategories() != null?params.getCategories().trim():"");
		params.setMimeType(params.getMimeType() != null?params.getMimeType().trim():"");
		params.setAuthor(params.getAuthor() != null?params.getAuthor().trim():"");
		params.setPath(params.getPath() != null?params.getPath().trim():"");
		params.setSubject(params.getSubject() != null?params.getSubject().trim():"");
		params.setFrom(params.getFrom() != null?params.getFrom().trim():"");
		params.setTo(params.getTo() != null?params.getTo().trim():"");
		params.setProperties(params.getProperties() != null?params.getProperties():new HashMap<String, String>());

		// Domains
		boolean document = (params.getDomain() & QueryParams.DOCUMENT) != 0;
		boolean folder = (params.getDomain() & QueryParams.FOLDER) != 0;
		boolean mail = (params.getDomain() & QueryParams.MAIL) != 0;
		
		log.info("doc="+document+", fld="+folder+", mail="+mail);

		// Escape
		if (!params.getName().equals("")) {
			params.setName(escapeContains(params.getName()));
		}
		if (!params.getContent().equals("")) {
			params.setContent(escapeContains(params.getContent()));
		}
		if (!params.getKeywords().equals("")) {
			params.setKeywords(escapeContains(params.getKeywords()));
		}
		
		if (!params.getContent().equals("") || !params.getName().equals("") ||
				!params.getKeywords().equals("") || !params.getMimeType().equals("") ||
				!params.getAuthor().equals("") || !params.getProperties().isEmpty() ||
				!params.getSubject().equals("") || !params.getFrom().equals("") ||
				!params.getTo().equals("") ||
				(params.getLastModifiedFrom() != null && params.getLastModifiedTo() != null)) {
			
			// Construct the query
			sb.append("/jcr:root"+ISO9075.encodePath(params.getPath())+"//*[@jcr:primaryType eq 'okm:void'");

			/**
			 * DOCUMENT
			 */
			if (document) {
				sb.append(" or (@jcr:primaryType eq 'okm:document'");

				if (!params.getContent().equals("")) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("jcr:contains(okm:content,'" + params.getContent() + "')");
				}

				if (!params.getName().equals("")) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("jcr:contains(@okm:name,'" + params.getName() + "')");
				}
				
				if (!params.getKeywords().equals("")) {
					String keywords[] = params.getKeywords().split("\\s+");
					for (int i=0; i<keywords.length; i++) {
						sb.append(" "+params.getOperator()+" ");
						sb.append("@okm:keywords = '" + keywords[i] + "'");
					}
				}
				
				if (!params.getCategories().equals("")) {
					String categories[] = params.getCategories().split("\\s+");
					for (int i=0; i<categories.length; i++) {
						sb.append(" "+params.getOperator()+" ");
						sb.append("@okm:categories = '" + categories[i] + "'");
					}
				}

				if (!params.getMimeType().equals("")) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("@okm:content/jcr:mimeType='" + params.getMimeType() + "'");
				}

				if (!params.getAuthor().equals("")) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("@okm:content/okm:author='" + params.getAuthor() + "'");
				}

				if (params.getLastModifiedFrom() != null && params.getLastModifiedTo() != null) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("(");
					sb.append("@okm:content/jcr:lastModified >= xs:dateTime('" + ISO8601.format(params.getLastModifiedFrom()) + "')");				
					sb.append(" and ");
					sb.append("@okm:content/jcr:lastModified <= xs:dateTime('" + ISO8601.format(params.getLastModifiedTo()) + "')");
					sb.append(")");
				}

				if (!params.getProperties().isEmpty()) {
					HashMap<String, MetaData> metaMap = DirectPropertyGroupModule.parseMetadata();
					
					for (Iterator<Entry<String, String>> it = params.getProperties().entrySet().iterator(); it.hasNext() ; ) {
						Entry<String, String> ent = it.next();
						MetaData meta = (MetaData) metaMap.get(ent.getKey());
						
						if (meta != null) {
							sb.append(" "+params.getOperator()+" ");
							
							if (meta.getType() == MetaData.SELECT) {
								sb.append("@"+ent.getKey()+"='"+ escapeXPath(ent.getValue().toString())+ "'");
							} else {
								sb.append("jcr:contains(@"+ent.getKey()+",'"+ escapeContains(ent.getValue().toString())+ "')");
							}
						}
					}
				}
				
				sb.append(")");
			} 
			
			/**
			 * FOLDER
			 */
			if (folder) {
				sb.append(" or (@jcr:primaryType eq 'okm:folder'");
				
				if (!params.getName().equals("")) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("jcr:contains(@okm:name,'"+ params.getName()+ "')");
				}
				
				if (params.getLastModifiedFrom() != null && params.getLastModifiedTo() != null) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("(");
					sb.append("@jcr:created >= xs:dateTime('" + ISO8601.format(params.getLastModifiedFrom()) +"')");				
					sb.append(" and ");
					sb.append("@jcr:created <= xs:dateTime('" + ISO8601.format(params.getLastModifiedTo()) +"')");
					sb.append(")");
				}

				sb.append(")");
			}

			/**
			 * MAIL
			 */
			if (mail) {
				sb.append(" or (@jcr:primaryType eq 'okm:mail'");
				
				if (!params.getContent().equals("")) {
					sb.append(" and jcr:contains(.,'" + params.getContent() + "')");
				}
				
				if (!params.getSubject().equals("")) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("jcr:contains(@okm:subject,'"+ params.getSubject()+ "')");
				}
				
				if (!params.getFrom().equals("")) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("jcr:contains(@okm:from,'"+ params.getFrom()+ "')");
				}

				if (!params.getTo().equals("")) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("jcr:contains(@okm:to,'"+ params.getTo()+ "')");
				}
				
				if (!params.getMimeType().equals("")) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("@okm:content/jcr:mimeType='"+ params.getMimeType()+ "'");
				}

				if (!params.getProperties().isEmpty()) {
					HashMap<String, MetaData> metaMap = DirectPropertyGroupModule.parseMetadata();
					
					for (Iterator<Entry<String, String>> it = params.getProperties().entrySet().iterator(); it.hasNext() ; ) {
						Entry<String, String> ent = it.next();
						MetaData meta = (MetaData) metaMap.get(ent.getKey());
						
						if (meta != null) {
							sb.append(" "+params.getOperator()+" ");
							
							if (meta.getType() == MetaData.SELECT) {
								sb.append("@"+ent.getKey()+"='"+ escapeXPath(ent.getValue().toString())+ "'");
							} else {
								sb.append("jcr:contains(@"+ent.getKey()+",'"+ escapeContains(ent.getValue().toString())+ "')");
							}
						}
					}
				}
				
				sb.append(")");
			}
			
			sb.append("] order by @jcr:score descending");
		}
		
		log.info("prepareStatement: "+sb.toString());
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByStatement(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Collection<QueryResult> findByStatement(String token, String statement, String type) throws RepositoryException {
		log.debug("findByStatement(" + token + ", " + statement + ")");
		Collection<QueryResult> ret = findByStatementPaginated(token, statement, type, 0, Config.MAX_SEARCH_RESULTS).getResults();
		log.debug("findByStatement: " + ret);
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#findByStatementPaginated(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public ResultSet findByStatementPaginated(String token, String statement, String type, int offset, int limit) throws RepositoryException {
		log.debug("findByStatement(" + token + ", " + statement + ", " + type + ", " + offset + ", " + limit + ")");
		ResultSet rs = new ResultSet();
		
		try {
			Session session = SessionManager.getInstance().get(token);

			if (statement != null && !statement.equals("")) {
				Workspace workspace = session.getWorkspace();
				QueryManager queryManager = workspace.getQueryManager();
				Query query = queryManager.createQuery(statement, type);
				rs = executeQuery(session, query, offset, limit);
			}
			
			// Activity log
			UserActivity.log(session, "FIND", type, statement);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("findByStatement: " + rs);
		return rs;
	}

	/**
	 * @param session
	 * @param query
	 */
	private ResultSet executeQuery(Session session, Query query, int offset, int limit) throws RepositoryException {
		log.debug("executeQuery(" + session + ", " + query + ", " + offset + ", " + limit + ")");
		ResultSet rs = new ResultSet();
		ArrayList<QueryResult> al = new ArrayList<QueryResult>();
		
		try {
			// http://n4.nabble.com/Query-performance-for-large-query-results-td531360.html
			((QueryImpl) query).setLimit(limit);
			((QueryImpl) query).setOffset(offset);
			javax.jcr.query.QueryResult result = query.execute();
			RowIterator it = result.getRows();
			rs.setTotal(it.getSize());
			
			while (it.hasNext()) {
				Row row = it.nextRow();
				
				try {
					String path = row.getValue(JcrConstants.JCR_PATH).getString();
					Node node = session.getRootNode().getNode(path.substring(1));
					QueryResult qr = new QueryResult();
					
					if (node.isNodeType(Document.TYPE)) {
						Document doc = new DirectDocumentModule().getProperties(session, path);
						
						if (node.getParent().isNodeType(Mail.TYPE)) {
							qr.setAttachment(doc);
						} else {
							qr.setDocument(doc);
						}
					} else if (node.isNodeType(Folder.TYPE)) {
						Folder fld = new DirectFolderModule().getProperties(session, path);
						qr.setFolder(fld);
					} else if (node.isNodeType(Mail.TYPE)) {
						Mail mail = new DirectMailModule().getProperties(session, path);
						qr.setMail(mail);
					}
					
					qr.setScore(row.getValue(JcrConstants.JCR_SCORE).getLong());
					al.add(qr);
				} catch (javax.jcr.PathNotFoundException e) {
					log.error(e.getMessage(), e);
				}
				
				rs.setResults(al);
			}
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.debug("executeQuery: " + rs);
		return rs;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#saveSearch(java.lang.String, com.openkm.bean.QueryParams, java.lang.String, java.lang.String)
	 */
	@Override
	public void saveSearch(String token, QueryParams params, String name) throws ItemExistsException, RepositoryException {
		log.debug("saveSearch("+token+", "+params+", "+name+")");
		
		try {
			Session session = SessionManager.getInstance().get(token);
			Node userQuery = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+QueryParams.LIST);
			Node savedSearch = userQuery.addNode(name, QueryParams.TYPE);
			
			// Ignore dates if saved seach is dashboard
			if (params.isDashboard()) {
				params.setLastModifiedFrom(null);
				params.setLastModifiedTo(null);
			}
			
			saveSearch(savedSearch, params);
			userQuery.save();
			
			// Activity log
			UserActivity.log(session, "SAVE_SEARCH", name, params.toString());
		} catch (javax.jcr.ItemExistsException e) {
			log.warn(e.getMessage(), e);
			throw new ItemExistsException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("saveSearch: void");
	}

	/**
	 * @param savedSearch
	 * @param params
	 * @throws ValueFormatException
	 * @throws VersionException
	 * @throws LockException
	 * @throws ConstraintViolationException
	 * @throws javax.jcr.RepositoryException
	 * @throws javax.jcr.AccessDeniedException
	 * @throws javax.jcr.ItemExistsException
	 * @throws InvalidItemStateException
	 * @throws ReferentialIntegrityException
	 * @throws NoSuchNodeTypeException
	 */
	public void saveSearch(Node savedSearch, QueryParams params)
			throws ValueFormatException, VersionException, LockException,
			ConstraintViolationException, javax.jcr.RepositoryException,
			javax.jcr.AccessDeniedException, javax.jcr.ItemExistsException,
			InvalidItemStateException, ReferentialIntegrityException,
			NoSuchNodeTypeException {
		savedSearch.setProperty(QueryParams.CONTENT, params.getContent());
		savedSearch.setProperty(QueryParams.NAME, params.getName());
		savedSearch.setProperty(QueryParams.KEYWORDS, params.getKeywords());
		savedSearch.setProperty(QueryParams.CATEGORIES, params.getCategories());
		savedSearch.setProperty(QueryParams.MIME_TYPE, params.getMimeType());
		savedSearch.setProperty(QueryParams.AUTHOR, params.getAuthor());
		savedSearch.setProperty(QueryParams.PATH, params.getPath());
		savedSearch.setProperty(QueryParams.LAST_MODIFIED_FROM, params.getLastModifiedFrom());
		savedSearch.setProperty(QueryParams.LAST_MODIFIED_TO, params.getLastModifiedTo());
		savedSearch.setProperty(QueryParams.SUBJECT, params.getSubject());
		savedSearch.setProperty(QueryParams.FROM, params.getFrom());
		savedSearch.setProperty(QueryParams.TO, params.getTo());
		savedSearch.setProperty(QueryParams.DASHBOARD, params.isDashboard());
		savedSearch.setProperty(QueryParams.DOMAIN, params.getDomain());
		savedSearch.setProperty(QueryParams.OPERATOR, params.getOperator());
		HashMap<String, String> hm = params.getProperties();
		
		for (Iterator<Entry<String, String>> it = hm.entrySet().iterator(); it.hasNext(); ) {
			Entry<String, String> entry = it.next();
			savedSearch.setProperty(entry.getKey().toString(), entry.getValue().toString());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#runSearch(java.lang.String, java.lang.String)
	 */
	@Override
	public QueryParams getSearch(String token, String name) throws PathNotFoundException, RepositoryException {
		log.debug("getSearch("+token+", "+name+")");
		QueryParams qp = new QueryParams();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			Node userQuery = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+QueryParams.LIST);
			Node savedQuery = userQuery.getNode(name);
			qp = getSearch(savedQuery);
			
			// If this is a dashboard user search, dates are used internally
			if (qp.isDashboard()) {
				qp.setLastModifiedFrom(null);
				qp.setLastModifiedTo(null);
			}
			
			// Activity log
			UserActivity.log(session, "GET_SAVED_SEARCH", name, qp.toString());
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getSearch: "+qp);
		return qp;
	}

	/**
	 * @param savedQuery
	 * @return
	 * @throws javax.jcr.RepositoryException
	 * @throws ValueFormatException
	 */
	public QueryParams getSearch(Node savedQuery) throws javax.jcr.RepositoryException, 
			ValueFormatException {
		HashMap<String, String> hm = new HashMap<String, String>();
		QueryParams qp = new QueryParams();
		
		for (PropertyIterator pi = savedQuery.getProperties(); pi.hasNext(); ) {
			Property p = pi.nextProperty();
			
			if (p.getName().equals(QueryParams.CONTENT)) {
				qp.setContent(p.getString());
			} else if (p.getName().equals(QueryParams.NAME)) {
				qp.setName(p.getString());
			} else if (p.getName().equals(QueryParams.KEYWORDS)) {
				qp.setKeywords(p.getString());
			} else if (p.getName().equals(QueryParams.CATEGORIES)) {
				qp.setCategories(p.getString());
			} else if (p.getName().equals(QueryParams.MIME_TYPE)) {
				qp.setMimeType(p.getString());
			} else if (p.getName().equals(QueryParams.AUTHOR)) {
				qp.setAuthor(p.getString());
			} else if (p.getName().equals(QueryParams.PATH)) {
				qp.setPath(p.getString());
			} else if (p.getName().equals(QueryParams.LAST_MODIFIED_FROM)) {
				qp.setLastModifiedFrom(p.getDate());
			} else if (p.getName().equals(QueryParams.LAST_MODIFIED_TO)) {
				qp.setLastModifiedTo(p.getDate());
			} else if (p.getName().equals(QueryParams.SUBJECT)) {
				qp.setSubject(p.getString());
			} else if (p.getName().equals(QueryParams.FROM)) {
				qp.setFrom(p.getString());
			} else if (p.getName().equals(QueryParams.TO)) {
				qp.setTo(p.getString());
			} else if (p.getName().equals(QueryParams.DASHBOARD)) {
				qp.setDashboard(p.getBoolean());
			} else if (p.getName().equals(QueryParams.DOMAIN)) {
				qp.setDomain(p.getLong());
			} else if (p.getName().equals(QueryParams.OPERATOR)) {
				qp.setOperator(p.getString());
			} else if (p.getName().startsWith(PropertyGroup.GROUP_PROPERTY+":")) {
				hm.put(p.getName(), p.getString());
			}
		}
		
		qp.setProperties(hm);
		return qp;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#getAllSearchs(java.lang.String)
	 */
	@Override
	public Collection<String> getAllSearchs(String token) throws RepositoryException {
		log.debug("getAllSearchs("+token+")");
		Collection<String> ret = new ArrayList<String>();
		
		try {
			Session session = SessionManager.getInstance().get(token);
			Node userQuery = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+QueryParams.LIST);
			
			for (NodeIterator it = userQuery.getNodes(); it.hasNext(); ) {
				Node search = it.nextNode();
				if (!search.getProperty(QueryParams.DASHBOARD).getBoolean()) {
					ret.add(search.getName());
				}
			}
			
			// Activity log
			UserActivity.log(session, "GET_ALL_SEARCHS", null, null);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("getAllSearchs: "+ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#deleteSearch(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteSearch(String token, String name) throws PathNotFoundException, RepositoryException {
		log.debug("deleteSearch("+token+", "+name+")");
		
		try {
			Session session = SessionManager.getInstance().get(token);
			Node userQuery = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+QueryParams.LIST);
			Node savedQuery = userQuery.getNode(name);
			boolean isDashboard = savedQuery.getProperty(QueryParams.DASHBOARD).getBoolean();
			savedQuery.remove();
			userQuery.save();
			
			// Purge visited nodes table
			if (isDashboard) {
				new DirectDashboardModule().deleteVisitedNodes(session.getUserID(), name);
			}
			
			// Activity log
			UserActivity.log(session, "DELETE_SAVED_SEARCH", name, null);
		} catch (SQLException e) {
			log.warn(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.PathNotFoundException e) {
			log.warn(e.getMessage(), e);
			throw new PathNotFoundException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("deleteSearch: void");
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#getKeywords(java.lang.String, java.util.Collection)
	 */
	@Override
	public Map<String, Integer> getKeywordMap(String token, Collection<String> filter) throws RepositoryException {
		log.info("getKeywordMap("+token+", "+filter+")");
		Map<String, Integer> cloud = null;
		
		if (Config.USER_KEYWORDS_CACHE.equals("on")) {
			cloud = getKeywordMapCached(token, filter);
		} else {
			cloud = getKeywordMapLive(token, filter);
		}
		
		log.info("getKeywordMap: "+cloud);
		return cloud;
	}
	
	/**
	 * @param token
	 * @param filter
	 * @return
	 * @throws RepositoryException
	 */
	private Map<String, Integer> getKeywordMapLive(String token, Collection<String> filter) throws RepositoryException {
		log.info("getKeywordMapLive("+token+", "+filter+")");
		String statement = "/jcr:root//element(*,okm:document)";
		Session session = SessionManager.getInstance().get(token);
		HashMap<String, Integer> cloud = new HashMap<String, Integer>();
		
		try {
			Workspace workspace = session.getWorkspace();
			QueryManager queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, Query.XPATH);
			javax.jcr.query.QueryResult qResult = query.execute();
			
			for (NodeIterator nit = qResult.getNodes(); nit.hasNext(); ) {
				Node doc = nit.nextNode();
				Value[] keywordsValue = doc.getProperty(com.openkm.bean.Property.KEYWORDS).getValues();
				ArrayList<String> keywordCollection = new ArrayList<String>();
				
				for (int i = 0; i < keywordsValue.length; i++) {
					keywordCollection.add(keywordsValue[i].getString());
				}
				
				if (filter != null && keywordCollection.containsAll(filter)) {
					for (Iterator<String> it = keywordCollection.iterator(); it.hasNext(); ) {
						String keyword = it.next();
						if (!filter.contains(keyword)) {
							Integer occurs = cloud.get(keyword)!=null?cloud.get(keyword):0;
							cloud.put(keyword, occurs+1);
						}
					}
				}
			}
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}

		log.info("getKeywordMapLive: "+cloud);
		return cloud;
	}

	/**
	 * @param token
	 * @param filter
	 * @return
	 * @throws RepositoryException
	 */
	private Map<String, Integer> getKeywordMapCached(String token, Collection<String> filter) throws RepositoryException {
		log.info("getKeywordMapCached("+token+", "+filter+")");
		Session session = SessionManager.getInstance().get(token);
		HashMap<String, ArrayList<String>> userDocKeywords = UserKeywordsManager.get(session.getUserID());
		HashMap<String, Integer> keywordMap = new HashMap<String, Integer>();
		
		for (Iterator<ArrayList<String>> kwIt = userDocKeywords.values().iterator(); kwIt.hasNext(); ) {
			ArrayList<String> docKeywords = kwIt.next();
			
			if (filter != null && docKeywords.containsAll(filter)) {
				for (Iterator<String> itDocKeywords = docKeywords.iterator(); itDocKeywords.hasNext(); ) {
					String keyword = itDocKeywords.next();
					if (!filter.contains(keyword)) {
						Integer occurs = keywordMap.get(keyword)!=null?keywordMap.get(keyword):0;
						keywordMap.put(keyword, occurs+1);
					}
				}
			}
		}
		
		log.info("getKeywordMapCached: "+keywordMap);
		return keywordMap;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.SearchModule#getCategorizedDocuments(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<Document> getCategorizedDocuments(String token, String categoryId) throws RepositoryException {
		log.info("getCategorizedDocuments("+token+", "+categoryId+")");
		Session session = SessionManager.getInstance().get(token);
		ArrayList<Document> documents = new ArrayList<Document>();
		
		try {
			Node category = session.getNodeByUUID(categoryId);
			
			for (PropertyIterator it = category.getReferences(); it.hasNext(); ) {
				Property refProp = it.nextProperty();
				
				if (com.openkm.bean.Property.CATEGORIES.equals(refProp.getName())) {
					Node node = refProp.getParent();
					Document doc = new DirectDocumentModule().getProperties(session, node.getPath());
					documents.add(doc);
				}
			}
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.info("getCategorizedDocuments: "+documents);
		return documents;
	}
}
