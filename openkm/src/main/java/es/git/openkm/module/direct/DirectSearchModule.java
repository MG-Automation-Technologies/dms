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

package es.git.openkm.module.direct;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.jcr.InvalidItemStateException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.Session;
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
import org.apache.jackrabbit.util.ISO8601;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.Document;
import es.git.openkm.bean.Folder;
import es.git.openkm.bean.Mail;
import es.git.openkm.bean.MetaData;
import es.git.openkm.bean.PropertyGroup;
import es.git.openkm.bean.QueryParams;
import es.git.openkm.bean.QueryResult;
import es.git.openkm.bean.Repository;
import es.git.openkm.bean.ResultSet;
import es.git.openkm.core.AccessDeniedException;
import es.git.openkm.core.Config;
import es.git.openkm.core.ItemExistsException;
import es.git.openkm.core.PathNotFoundException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.core.SessionManager;
import es.git.openkm.module.SearchModule;
import es.git.openkm.util.UserActivity;

public class DirectSearchModule implements SearchModule {
	private static Logger log = LoggerFactory.getLogger(DirectSearchModule.class);
	private static HashMap<String,ArrayList<String>> keywordMaps;
	
	static {
		// TODO Load serialized values.
		keywordMaps = new HashMap<String, ArrayList<String>>();
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#findByContent(java.lang.String, java.lang.String)
	 */
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
	 * @see es.git.openkm.module.SearchModule#findByName(java.lang.String, java.lang.String)
	 */
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
	 * @see es.git.openkm.module.SearchModule#findByKeywords(java.lang.String, java.lang.String)
	 */
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
	 * @see es.git.openkm.module.SearchModule#find(java.lang.String, es.git.openkm.bean.QuestionParams)
	 */
	public Collection<QueryResult> find(String token, QueryParams params) throws IOException, RepositoryException {
		log.debug("find(" + token + ", " + params + ")");
		Collection<QueryResult> ret = findPaginated(token, params, 0, Config.MAX_SEARCH_RESULTS).getResults();
		log.debug("find: " + ret);
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#findPaginated(java.lang.String, es.git.openkm.bean.QueryParams, int, int)
	 */
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
		log.debug("escapeContains("+str+")");
		String ret = str.replace("\\", "\\\\");
		ret = ret.replace("'", "\\'");
		ret = ret.replace("-", "\\-");
		ret = ret.replace("\"", "\\\"");
		ret = ret.replace("[", "\\[");
		ret = ret.replace("]", "\\]");
		ret = escapeXPath(ret);
		log.debug("escapeContains: "+ret);
		return ret;
	}

	/**
	 * Escape XPath string
	 *  
	 * @param str The String to be escaped.
	 * @return The escaped String.
	 */
	private String escapeXPath(String str) {
		log.debug("escapeXPath("+str+")");
		String ret = str.replace("'", "''");
		log.debug("escapeXPath: "+ret);
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
			sb.append("/jcr:root"+params.getPath()+"//*[@jcr:primaryType eq 'okm:void'");

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
					sb.append("jcr:contains(@okm:name,'"+ params.getName()+ "')");
				}

				if (!params.getKeywords().equals("")) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("jcr:contains(@okm:keywords,'"+ params.getKeywords()+ "')");
				}

				if (!params.getMimeType().equals("")) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("@okm:content/jcr:mimeType='"+ params.getMimeType()+ "'");
				}

				if (!params.getAuthor().equals("")) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("@okm:content/okm:author='"+ params.getAuthor()+ "'");
				}

				if (params.getLastModifiedFrom() != null && params.getLastModifiedTo() != null) {
					sb.append(" "+params.getOperator()+" ");
					sb.append("(");
					sb.append("@okm:content/jcr:lastModified >= xs:dateTime('" + ISO8601.format(params.getLastModifiedFrom()) +"')");				
					sb.append(" and ");
					sb.append("@okm:content/jcr:lastModified <= xs:dateTime('" + ISO8601.format(params.getLastModifiedTo()) +"')");
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
	 * @see es.git.openkm.module.SearchModule#findByStatement(java.lang.String, java.lang.String, int)
	 */
	public Collection<QueryResult> findByStatement(String token, String statement, String type) throws RepositoryException {
		log.debug("findByStatement(" + token + ", " + statement + ")");
		Collection<QueryResult> ret = findByStatementPaginated(token, statement, type, 0, Config.MAX_SEARCH_RESULTS).getResults();
		log.debug("findByStatement: " + ret);
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#findByStatementPaginated(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
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
		int i = 0;
		
		try {
			javax.jcr.query.QueryResult result = query.execute();
			RowIterator it = result.getRows();
			
			rs.setTotal(it.getSize());
			it.skip(offset);
						
			while (it.hasNext() && i++ < limit) {
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
	 * @see es.git.openkm.module.SearchModule#saveSearch(java.lang.String, es.git.openkm.bean.QueryParams, java.lang.String, java.lang.String)
	 */
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
	 * @see es.git.openkm.module.SearchModule#runSearch(java.lang.String, java.lang.String)
	 */
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
	 * @see es.git.openkm.module.SearchModule#getAllSearchs(java.lang.String)
	 */
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
	 * @see es.git.openkm.module.SearchModule#deleteSearch(java.lang.String, java.lang.String)
	 */
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
	
	/**
	 * 
	 * @param token
	 * @param statement
	 * @return
	 * @throws AccessDeniedException
	 * @throws RepositoryException
	 */
	public Collection<Folder> findFolderByStatement(String token, String statement) throws AccessDeniedException, RepositoryException {
		log.debug("findByStatement(" + token + ", " + statement + ")");
		ArrayList<Folder> ret = new ArrayList<Folder>();

		try {
			Session session = SessionManager.getInstance().get(token);
			Workspace workspace = session.getWorkspace();
			QueryManager queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, javax.jcr.query.Query.XPATH);
			javax.jcr.query.QueryResult result = query.execute();

			for (NodeIterator it = result.getNodes(); it.hasNext();) {
				try {
					Node node = (Node) it.next();
					ret.add(new DirectFolderModule().getProperties(session, node.getUUID()));
				} catch (javax.jcr.PathNotFoundException e) {
					log.error(e.getMessage(), e);
				}
			}
			
			// Activity log
			UserActivity.log(session, "FIND_FOLDER", null, statement);
		} catch (javax.jcr.AccessDeniedException e) {
			log.warn(e.getMessage(), e);
			throw new AccessDeniedException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.debug("findByStatement: " + ret);
		return ret;
	}

	/* (non-Javadoc)
	 * @see es.git.openkm.module.SearchModule#getKeywords(java.lang.String, java.util.Collection)
	 */
	public Map<String, Integer> getKeywordMap(String token, Collection<String> filter) throws RepositoryException {
		log.info("getKeywordMap("+token+", "+filter+")");
		Map<String, Integer> cloud = null;
		
		if (Config.KEYWORD_MAP_LIVE.equals("on")) {
			cloud = getKeywordMapLive(token, filter);
		} else {
			cloud = getKeywordMapCached(token, filter);
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
				String keywordsString = doc.getProperty(Document.KEYWORDS).getString();
				ArrayList<String> keywordCollection = new ArrayList<String>();
				
				for (StringTokenizer st = new StringTokenizer(keywordsString); st.hasMoreTokens(); ) {
					String keyword = st.nextToken();
					keywordCollection.add(keyword);
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
		ArrayList<String> keywordCollection = keywordMaps.get(session.getUserID());
		HashMap<String, Integer> keywordMap = new HashMap<String, Integer>();
		
		if (keywordCollection == null) {
			updateKeywordMaps(session);
			keywordCollection = keywordMaps.get(session.getUserID());
		}
		
		if (filter != null && keywordCollection.containsAll(filter)) {
			for (Iterator<String> it = keywordCollection.iterator(); it.hasNext(); ) {
				String keyword = it.next();
				if (!filter.contains(keyword)) {
					Integer occurs = keywordMap.get(keyword)!=null?keywordMap.get(keyword):0;
					keywordMap.put(keyword, occurs+1);
				}
			}
		}
		
		log.info("getKeywordMapCached: "+keywordMap);
		return keywordMap;
	}
	
	/**
	 * @param session
	 * @throws RepositoryException
	 */
	private synchronized void updateKeywordMaps(Session session) throws RepositoryException {
		log.info("updateKeywordMaps("+session+")");
		String statement = "/jcr:root//element(*,okm:document)";
		
		try {
			Workspace workspace = session.getWorkspace();
			QueryManager queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, Query.XPATH);
			javax.jcr.query.QueryResult qResult = query.execute();
			
			for (NodeIterator nit = qResult.getNodes(); nit.hasNext(); ) {
				Node doc = nit.nextNode();
				String keywordsString = doc.getProperty(Document.KEYWORDS).getString();
				ArrayList<String> keywordCollection = new ArrayList<String>();
				
				for (StringTokenizer st = new StringTokenizer(keywordsString); st.hasMoreTokens(); ) {
					String keyword = st.nextToken();
					keywordCollection.add(keyword);
				}
				
				keywordMaps.put(session.getUserID(), keywordCollection);
				
				// TODO Serialize keyword map
			}
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		
		log.info("updateKeywordMaps: void");
	}
}
