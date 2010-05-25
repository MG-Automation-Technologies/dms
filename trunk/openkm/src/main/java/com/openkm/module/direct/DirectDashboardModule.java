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
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.DashboardStatsDocumentResult;
import com.openkm.bean.DashboardStatsFolderResult;
import com.openkm.bean.DashboardStatsMailResult;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Mail;
import com.openkm.bean.QueryParams;
import com.openkm.bean.Repository;
import com.openkm.bean.cache.UserItems;
import com.openkm.cache.UserItemsManager;
import com.openkm.core.Config;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;
import com.openkm.core.SessionManager;
import com.openkm.dao.ActivityDAO;
import com.openkm.dao.DashboardStatsDAO;
import com.openkm.dao.bean.DashboardStats;
import com.openkm.module.DashboardModule;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;

public class DirectDashboardModule implements DashboardModule {
	private static Logger log = LoggerFactory.getLogger(DirectDashboardModule.class);
	private static ActivityDAO actDao = ActivityDAO.getInstance();
	private static DashboardStatsDAO dsDao = DashboardStatsDAO.getInstance();
	private static final int MAX_RESULTS = 20;

	@Override
	public Collection<DashboardStatsDocumentResult> getUserLockedDocuments(String token) throws RepositoryException {
		log.debug("getUserLockedDocuments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = null;
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getUserLockedDocuments(session);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getUserLockedDocuments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getUserLockedDocuments(Session session) throws
			javax.jcr.RepositoryException, SQLException {
		log.debug("getUserLockedDocuments({})", session);
		String statement = "/jcr:root/okm:root//element(*, okm:document)[@jcr:lockOwner='"+session.getUserID()+"' and okm:content/@jcr:isCheckedOut=false()]";
		ArrayList<DashboardStatsDocumentResult> al = executeQueryDocument(session, statement, "LOCK_DOCUMENT", Integer.MAX_VALUE);

		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserLockedDocuments", al);
		log.debug("getUserLockedDocuments: {}", al);
		return al;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getUserCheckedOutDocuments(String token) throws RepositoryException {
		log.debug("getUserCheckedOutDocuments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = null;
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getUserCheckedOutDocuments(session);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getUserCheckedOutDocuments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getUserCheckedOutDocuments(Session session) throws 
			javax.jcr.RepositoryException, SQLException {
		log.debug("getUserCheckedOutDocuments({})", session);
		String statement = "/jcr:root/okm:root//element(*, okm:document)[@jcr:lockOwner='"+session.getUserID()+"' and okm:content/@jcr:isCheckedOut=true()]";
		ArrayList<DashboardStatsDocumentResult> al = executeQueryDocument(session, statement, "CHECKOUT_DOCUMENT", Integer.MAX_VALUE);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserCheckedOutDocuments", al);
		log.debug("getUserCheckedOutDocuments: {}", al);
		return al;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getUserSubscribedDocuments(String token) throws RepositoryException {
		log.debug("getUserSubscribedDocuments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = null;
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getUserSubscribedDocuments(session);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getUserSubscribedDocuments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getUserSubscribedDocuments(Session session) throws 
			javax.jcr.RepositoryException, SQLException {
		log.debug("getUserSubscribedDocuments({})", session);
		String statement = "/jcr:root/okm:root//element(*, mix:notification)[@jcr:primaryType='okm:document' and @okm:subscriptors='"+session.getUserID()+"']";
		ArrayList<DashboardStatsDocumentResult> al = executeQueryDocument(session, statement, "SUBSCRIBE_USER", Integer.MAX_VALUE);
			
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserSubscribedDocuments", al);
		log.debug("getUserSubscribedDocuments: {}", al);
		return al;
	}

	@Override
	public Collection<DashboardStatsFolderResult> getUserSubscribedFolders(String token) throws RepositoryException {
		log.debug("getUserSubscribedFolders({})", token);
		ArrayList<DashboardStatsFolderResult> al = null;
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getUserSubscribedFolders(session);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getUserSubscribedFolders: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsFolderResult> getUserSubscribedFolders(Session session) throws
			javax.jcr.RepositoryException, SQLException {
		log.debug("getUserSubscribedFolders({})", session);
		String statement = "/jcr:root/okm:root//element(*, mix:notification)[@jcr:primaryType='okm:folder' and @okm:subscriptors='"+session.getUserID()+"']";
		ArrayList<DashboardStatsFolderResult> al = executeQueryFolder(session, statement, "SUBSCRIBE_USER", Integer.MAX_VALUE);

		// Check for already visited results
		checkVisitedFolders(session.getUserID(), "UserSubscribedFolders", al);
		log.debug("getUserSubscribedFolders: {}", al);
		return al;
	}

	/**
	 * Execute query with documents
	 */
	private ArrayList<DashboardStatsDocumentResult> executeQueryDocument(Session session, String statement, String action, int maxResults) 
			throws javax.jcr.RepositoryException, SQLException {
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Workspace workspace = session.getWorkspace();
		QueryManager queryManager = workspace.getQueryManager();
		Query query = queryManager.createQuery(statement, "xpath");
		QueryResult result = query.execute();
		int i = 0;
		
		for (NodeIterator nit = result.getNodes(); nit.hasNext() && i++ < maxResults; ) {
			Node node = nit.nextNode();
			Document doc = new DirectDocumentModule().getProperties(session, node.getPath());
			DashboardStatsDocumentResult vo = new DashboardStatsDocumentResult();
			vo.setDocument(doc);
			vo.setDate(getActivityDate(session.getUserID(), action, node.getPath()));
			vo.setVisited(false);
			al.add(vo);
		}
		
		// Sort results
		Collections.sort(al, new Comparator<DashboardStatsDocumentResult>() {
		    public int compare(DashboardStatsDocumentResult doc1, DashboardStatsDocumentResult doc2) {
				return doc2.getDate().compareTo(doc1.getDate());
		    }
		});
		
		return al;
	}
	
	/**
	 * Execute query with folders
	 */
	private ArrayList<DashboardStatsFolderResult> executeQueryFolder(Session session, String statement, String action, int maxResults) 
			throws javax.jcr.RepositoryException, SQLException {
		ArrayList<DashboardStatsFolderResult> al = new ArrayList<DashboardStatsFolderResult>();
		Workspace workspace = session.getWorkspace();
		QueryManager queryManager = workspace.getQueryManager();
		Query query = queryManager.createQuery(statement, "xpath");
		QueryResult result = query.execute();
		int i = 0;
		
		for (NodeIterator nit = result.getNodes(); nit.hasNext() && i++ < maxResults; ) {
			Node node = nit.nextNode();
			Folder fld = new DirectFolderModule().getProperties(session, node.getPath());
			DashboardStatsFolderResult vo = new DashboardStatsFolderResult();
			vo.setFolder(fld);
			vo.setDate(getActivityDate(session.getUserID(), action, node.getPath()));
			vo.setVisited(false);
			al.add(vo);
		}
		
		// Order results
		Collections.sort(al, new Comparator<DashboardStatsFolderResult>() {
		    public int compare(DashboardStatsFolderResult fld1, DashboardStatsFolderResult fld2) {
				return fld2.getDate().compareTo(fld1.getDate());
		    }
		});
		
		return al;
	}
	
	/**
	 * Get activity date
	 */
	private Calendar getActivityDate(String user, String action, String item) throws SQLException {
		log.debug("getActivityDate({}, {}, {})", new Object[] { user, action, item });
		String sqlAction = "SELECT MAX(act_date) FROM activity " +
			"WHERE act_user = ? AND act_action = ? AND act_item = ?";
		String sqlNoAction = "SELECT MAX(act_date) FROM activity " +
			"WHERE ( act_action = 'CREATE_DOCUMENT' OR act_action = 'SET_DOCUMENT_CONTENT' ) AND act_item = ?";
		Calendar cal = Calendar.getInstance();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = actDao.getConnection();
			
			if (action != null) {
				stmt = con.prepareStatement(sqlAction);
				stmt.setString(1, user);
				stmt.setString(2, action);
				stmt.setString(3, item);
			} else {
				stmt = con.prepareStatement(sqlNoAction);
				stmt.setString(1, item);
			}
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getTimestamp(1) != null) {
					cal.setTimeInMillis(rs.getTimestamp(1).getTime());
				} else {
					// May be the document has been moved or renamed? 
					cal.setTimeInMillis(0);
				}
			}
		} finally {
			actDao.closeResultSet(rs);
			actDao.closeStatement(stmt);
			actDao.closeConnection(con);
		}
		
		log.debug("getActivityDate: {}", cal);
		return cal;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getUserLastUploadedDocuments(String token) throws RepositoryException {
		log.debug("getUserLastUploadedDocuments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getUserLastUploadedDocuments(session);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
		
		log.debug("getUserLastUploadedDocuments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getUserLastUploadedDocuments(Session session) throws
			javax.jcr.RepositoryException, SQLException {
		log.debug("getUserLastUploadedDocuments({})", session);
		String statement = "SELECT act_item, act_date FROM activity "+
			"WHERE act_action='CREATE_DOCUMENT' "+
			"AND act_user=? ORDER BY act_date DESC";
		ArrayList<DashboardStatsDocumentResult> al = getDocuments(session, statement);
			
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserLastUploadedDocuments", al);
		log.debug("getUserLastUploadedDocuments: {}", al);
		return al;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getUserLastModifiedDocuments(String token) throws RepositoryException {
		log.debug("getUserLastModifiedDocuments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getUserLastModifiedDocuments(session);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
		
		log.debug("getUserLastModifiedDocuments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getUserLastModifiedDocuments(Session session) throws
			javax.jcr.RepositoryException, SQLException {
		log.debug("getUserLastModifiedDocuments({})", session);
		String statement = "SELECT DISTINCT act_item, MAX(act_date) FROM activity "+
			"WHERE act_action='SET_DOCUMENT_CONTENT' AND act_user=? "+
			"GROUP BY act_item ORDER BY MAX(act_date) DESC";
		ArrayList<DashboardStatsDocumentResult> al = getDocuments(session, statement);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserLastModifiedDocuments", al);
		log.debug("getUserLastModifiedDocuments: {}", al);
		return al;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getUserLastDownloadedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLastDownloadedDocuments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getUserLastDownloadedDocuments(session);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
		
		log.debug("getUserLastDownloadedDocuments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getUserLastDownloadedDocuments(Session session) throws
			javax.jcr.RepositoryException, SQLException {
		log.debug("getUserLastDownloadedDocuments({})", session);
		String statement = "SELECT DISTINCT act_item, MAX(act_date) FROM activity "+
			"WHERE act_action='GET_DOCUMENT_CONTENT' AND act_user=? "+
			"GROUP BY act_item ORDER BY MAX(act_date) DESC";
		ArrayList<DashboardStatsDocumentResult> al = getDocuments(session, statement);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserLastDownloadedDocuments", al);
		log.debug("getUserLastDownloadedDocuments: {}", al);
		return al;
	}

	@Override
	public Collection<DashboardStatsMailResult> getUserLastImportedMails(String token) 
			throws RepositoryException {
		log.debug("getUserLastImportedMails({})", token);
		ArrayList<DashboardStatsMailResult> al = new ArrayList<DashboardStatsMailResult>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getUserLastImportedMails(session);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
		
		log.debug("getUserLastImportedMails: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsMailResult> getUserLastImportedMails(Session session) throws 
			javax.jcr.RepositoryException, SQLException {
		log.debug("getUserLastImportedMails({})", session);
		String statement = "SELECT act_item, act_date FROM activity "+
			"WHERE act_action='CREATE_MAIL' "+
			"AND act_user=? ORDER BY act_date DESC";
		ArrayList<DashboardStatsMailResult> al = getMails(session, statement);
		
		// Check for already visited results
		checkVisitedMails(session.getUserID(), "UserLastImportedMails", al);
		log.debug("getUserLastImportedMails: {}", al);
		return al;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getUserLastImportedMailAttachments(String token) 
			throws RepositoryException {
		log.debug("getUserLastImportedMailAttachments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getUserLastImportedMailAttachments(session);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
		
		log.debug("getUserLastImportedMailAttachments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getUserLastImportedMailAttachments(Session session) throws
			javax.jcr.RepositoryException, SQLException {
		log.debug("getUserLastImportedMailAttachments({})", session);
		String statement = "SELECT act_item, act_date FROM activity "+
			"WHERE act_action='CREATE_MAIL_ATTACHMENT' "+
			"AND act_user=? ORDER BY act_date DESC";
		ArrayList<DashboardStatsDocumentResult> al = getDocuments(session, statement);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserLastImportedMailAttachments", al);
		log.debug("getUserLastImportedMailAttachments: {}", al);
		return al;
	}
	
	
	/**
	 * Get documents from statement
	 */
	private ArrayList<DashboardStatsDocumentResult> getDocuments(Session session, String statement) 
			throws javax.jcr.RepositoryException, SQLException {
		log.debug("getDocuments({}, {})", session, statement);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int i = 0;
		
		try {
			con = actDao.getConnection();
			stmt = con.prepareStatement(statement);
			stmt.setString(1, session.getUserID());
			rs = stmt.executeQuery();
			
			while (rs.next() && i++ < MAX_RESULTS) {
				if (session.getRootNode().hasNode(rs.getString(1).substring(1))) {
					Document doc = new DirectDocumentModule().getProperties(session, rs.getString(1));
					DashboardStatsDocumentResult vo = new DashboardStatsDocumentResult();
					vo.setDocument(doc);
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(rs.getTimestamp(2).getTime());
					vo.setDate(cal);
					vo.setVisited(false);
					al.add(vo);
				}
			}
		} finally {
			actDao.closeResultSet(rs);
			actDao.closeStatement(stmt);
			actDao.closeConnection(con);
		}
		
		log.debug("getDocuments: {}", al);
		return al;
	}

	/**
	 * Get mails from statement
	 */
	private ArrayList<DashboardStatsMailResult> getMails(Session session, String statement) 
			throws javax.jcr.RepositoryException, SQLException {
		log.debug("getMails({}, {})", session, statement);
		ArrayList<DashboardStatsMailResult> al = new ArrayList<DashboardStatsMailResult>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int i = 0;
		
		try {
			con = actDao.getConnection();
			stmt = con.prepareStatement(statement);
			stmt.setString(1, session.getUserID());
			rs = stmt.executeQuery();
			
			while (rs.next() && i++ < MAX_RESULTS) {
				if (session.getRootNode().hasNode(rs.getString(1).substring(1))) {
					Mail mail = new DirectMailModule().getProperties(session, rs.getString(1));
					DashboardStatsMailResult vo = new DashboardStatsMailResult();
					vo.setMail(mail);
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(rs.getTimestamp(2).getTime());
					vo.setDate(cal);
					vo.setVisited(false);
					al.add(vo);
				}
			}
		} finally {
			actDao.closeResultSet(rs);
			actDao.closeStatement(stmt);
			actDao.closeConnection(con);
		}
		
		log.debug("getMails: {}", al);
		return al;
	}

	@Override
	public long getUserDocumentsSize(String token) throws RepositoryException {
		log.info("getUserDocumentsSize({})", token);
		long size = 0;
		
		if (Config.USER_SIZE_CACHE) {
			size = getUserDocumentsSizeCached(token);
		} else {
			size = getUserDocumentsSizeLive(token);
		}

		log.info("getUserDocumentsSize: {}", size);
		return size;
	}
	
	/**
	 * Get user document size
	 */
	private long getUserDocumentsSizeLive(String token) throws RepositoryException {
		log.info("getUserDocumentsSizeLive({})", token);
		long size = 0;
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			String statement = "/jcr:root/okm:root//element(*, okm:document)[okm:content/@okm:author='"+session.getUserID()+"']";
			Workspace workspace = session.getWorkspace();
			QueryManager queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(statement, "xpath");
			QueryResult result = query.execute();
			
			for (NodeIterator nit = result.getNodes(); nit.hasNext(); ) {
				Node node = nit.nextNode();
				Node contentNode = node.getNode(Document.CONTENT);
				size += contentNode.getProperty(Document.SIZE).getLong();
			}
 		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.info("getUserDocumentsSizeLive: {}", size);
		return size;
	}
	
	/**
	 * Get user document size
	 */
	private long getUserDocumentsSizeCached(String token) throws RepositoryException {
		log.info("getUserDocumentsSizeCached({})", token);
		Session session = null;
		UserItems usrItems = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			usrItems = UserItemsManager.get(session.getUserID());
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
		
		log.info("getUserDocumentsSizeCached: {}", usrItems.getSize());
		return usrItems.getSize();
	}

	@Override
	public Collection<String> getUserSearchs(String token) throws RepositoryException {
		log.debug("getUserSearchs({})", token);
		Collection<String> ret = new ArrayList<String>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			Node userQuery = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+QueryParams.LIST);
			
			for (NodeIterator it = userQuery.getNodes(); it.hasNext(); ) {
				Node search = it.nextNode();
				if (search.getProperty(QueryParams.DASHBOARD).getBoolean()) {
					ret.add(search.getName());
				}
			}
			
			// Activity log
			UserActivity.log(session, "GET_ALL_DASHBOARD_SEARCHS", null, null);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
		
		log.debug("getUserSearchs: {}", ret);
		return ret;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> find(String token, String name) 
			throws IOException, ParseException, RepositoryException {
		log.debug("find({}, {})", token, name);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = find(session, name);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
		
		log.debug("find: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> find(Session session, String name) throws 
			javax.jcr.RepositoryException, SQLException, ParseException, IOException {
		log.debug("find({}, {})", session, name);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		DirectSearchModule directSearch = new DirectSearchModule();
		Node userQuery = session.getRootNode().getNode(Repository.HOME+"/"+session.getUserID()+"/"+QueryParams.LIST);
		
		synchronized (userQuery) {
			Node savedQuery = userQuery.getNode(name);
			
			// Get the saved query params
			QueryParams params = directSearch.getSearch(savedQuery);
			log.debug("PARAMS: {}", params.toString());
			
			// Set query date (first time)
			if (params.getLastModifiedTo() == null) {
				Calendar firstExecution = Calendar.getInstance();
				firstExecution.add(Calendar.MONTH, -1);
				params.setLastModifiedTo(firstExecution);
			}
			
			Calendar lastExecution = resetHours(params.getLastModifiedTo());
			Calendar actualDate = resetHours(Calendar.getInstance());
			log.debug("lastExecution -> {}", lastExecution.getTime());
			log.debug("actualDate -> {}", actualDate.getTime());
			
			if (lastExecution.before(actualDate)) {
				params.setLastModifiedFrom(params.getLastModifiedTo());
			}
			
			params.setLastModifiedTo(Calendar.getInstance());
			
			// Prepare statement
			log.debug("PARAMS {}", params);
			String statement = directSearch.prepareStatement(params);
			log.debug("STATEMENT {}", statement);
			
			// Execute query
			al = executeQueryDocument(session, statement, null, MAX_RESULTS);
			
			// Update query params
			directSearch.saveSearch(savedQuery, params);
			userQuery.save();
		}
	
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), name, al);
		log.debug("find: {}", al);
		return al;
	}
	
	/**
	 * Reset calendar hours
	 */
	private Calendar resetHours(Calendar cal) {
		Calendar tmp = (Calendar) cal.clone();
		tmp.set(Calendar.HOUR_OF_DAY, 0);
		tmp.set(Calendar.MINUTE, 0);
		tmp.set(Calendar.SECOND, 0);
		tmp.set(Calendar.MILLISECOND, 0);
		return tmp;
	}
	
	@Override
	public Collection<DashboardStatsDocumentResult> getLastWeekTopDownloadedDocuments(String token) 
			throws RepositoryException {
		log.debug("getLastWeekTopDownloadedDocuments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getLastWeekTopDownloadedDocuments(session);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getLastWeekTopDownloadedDocuments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getLastWeekTopDownloadedDocuments(Session session) throws
			javax.jcr.RepositoryException, SQLException {
		log.debug("getLastWeekTopDownloadedDocuments({})", session);
		String statement = "SELECT act_item, MAX(act_date) FROM activity "+
			"WHERE act_action='GET_DOCUMENT_CONTENT' AND act_item LIKE '/okm:root/%' "+
			"AND act_date>? GROUP BY act_item ORDER BY COUNT(act_item) DESC";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		ArrayList<DashboardStatsDocumentResult> al = getTopDocuments(session, statement, cal);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "LastWeekTopDownloadedDocuments", al);
		log.debug("getLastWeekTopDownloadedDocuments: {}", al);
		return al;
	}
	
	@Override
	public Collection<DashboardStatsDocumentResult> getLastMonthTopDownloadedDocuments(String token) 
			throws RepositoryException {
		log.debug("getLastMonthTopDownloadedDocuments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getLastMonthTopDownloadedDocuments(session);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getLastMonthTopDownloadedDocuments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getLastMonthTopDownloadedDocuments(Session session) throws
			javax.jcr.RepositoryException, SQLException {
		log.debug("getLastMonthTopDownloadedDocuments({})", session);
		String statement = "SELECT act_item, MAX(act_date) FROM activity "+
			"WHERE act_action='GET_DOCUMENT_CONTENT' AND act_item LIKE '/okm:root/%' "+
			"AND act_date>? GROUP BY act_item ORDER BY COUNT(act_item) DESC";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		ArrayList<DashboardStatsDocumentResult> al = getTopDocuments(session, statement, cal);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "LastMonthTopDownloadedDocuments", al);
		log.debug("getLastMonthTopDownloadedDocuments: {}", al);
		return al;
	}
	
	@Override
	public Collection<DashboardStatsDocumentResult> getLastWeekTopModifiedDocuments(String token) 
			throws RepositoryException {
		log.debug("getLastWeekTopModifiedDocuments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getLastWeekTopModifiedDocuments(session);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getLastWeekTopModifiedDocuments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getLastWeekTopModifiedDocuments(Session session) throws
			javax.jcr.RepositoryException, SQLException {
		log.debug("getLastWeekTopModifiedDocuments({})", session);
		String statement = "SELECT act_item, MAX(act_date) FROM activity "+
			"WHERE act_action='SET_DOCUMENT_CONTENT' AND act_item LIKE '/okm:root/%' "+
			"AND act_date>? GROUP BY act_item ORDER BY COUNT(act_item) DESC";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		ArrayList<DashboardStatsDocumentResult> al = getTopDocuments(session, statement, cal);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "LastWeekTopModifiedDocuments", al);
		log.debug("getLastWeekTopModifiedDocuments: {}", al);
		return al;
	}
	
	@Override
	public Collection<DashboardStatsDocumentResult> getLastMonthTopModifiedDocuments(String token) 
			throws RepositoryException {
		log.debug("getLastMonthTopModifiedDocuments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getLastMonthTopModifiedDocuments(session); 
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getLastMonthTopModifiedDocuments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getLastMonthTopModifiedDocuments(Session session) throws
			javax.jcr.RepositoryException, SQLException {
		log.debug("getLastMonthTopModifiedDocuments({})", session);
		String statement = "SELECT act_item, MAX(act_date) FROM activity "+
			"WHERE act_action='SET_DOCUMENT_CONTENT' AND act_item LIKE '/okm:root/%' "+
			"AND act_date>? GROUP BY act_item ORDER BY COUNT(act_item) DESC";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		ArrayList<DashboardStatsDocumentResult> al = getTopDocuments(session, statement, cal); 
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "LastMonthTopModifiedDocuments", al);
		log.debug("getLastMonthTopModifiedDocuments: {}", al);
		return al;
	}
	
	@Override
	public Collection<DashboardStatsDocumentResult> getLastModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastModifiedDocuments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getLastModifiedDocuments(session);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}

		log.debug("getLastModifiedDocuments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getLastModifiedDocuments(Session session) throws
			javax.jcr.RepositoryException, SQLException {
		log.debug("getLastModifiedDocuments({})", session);
		String statement = "SELECT DISTINCT act_item, MAX(act_date) FROM activity "+
			"WHERE act_action='SET_DOCUMENT_CONTENT' AND act_item LIKE '/okm:root/%' "+
			"GROUP BY act_item ORDER BY MAX(act_date) DESC";
		ArrayList<DashboardStatsDocumentResult> al = getTopDocuments(session, statement, null);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "LastModifiedDocuments", al);
		log.debug("getLastModifiedDocuments: {}", al);
		return al;
	}
	
	@Override
	public Collection<DashboardStatsDocumentResult> getLastUploadedDocuments(String token) 
			throws RepositoryException {
		log.debug("getLastUploadedDocuments({})", token);
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			al = getLastUploadedDocuments(session);
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
		
		log.debug("getLastUploadedDocuments: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public ArrayList<DashboardStatsDocumentResult> getLastUploadedDocuments(Session session) 
			throws javax.jcr.RepositoryException, SQLException {
		log.debug("getLastUploadedDocuments({})", session);
		String statement = "SELECT DISTINCT act_item, MAX(act_date) FROM activity "+
			"WHERE act_action='CREATE_DOCUMENT' AND act_item LIKE '/okm:root/%' "+
			"GROUP BY act_item ORDER BY MAX(act_date) DESC";
		ArrayList<DashboardStatsDocumentResult> al = getTopDocuments(session, statement, null);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "LastUploadedDocuments", al);
		
		log.debug("getLastUploadedDocuments: {}", al);
		return al;
	}

	/**
	 * Get top documents
	 */
	private ArrayList<DashboardStatsDocumentResult> getTopDocuments(Session session, String statement, Calendar date) 
			throws javax.jcr.RepositoryException, SQLException {
		log.debug("getTopDocuments({}, {}, {})", new Object[] { session, statement, (date!=null?date.getTime():"null") });
		ArrayList<DashboardStatsDocumentResult> al = new ArrayList<DashboardStatsDocumentResult>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int i = 0;
		
		try {
			con = actDao.getConnection();
			stmt = con.prepareStatement(statement);
			
			if (date != null) {
				stmt.setDate(1, new Date(date.getTimeInMillis())); 
			}
			
			rs = stmt.executeQuery();
			
			while (rs.next() && i++ < MAX_RESULTS) {
				if (session.getRootNode().hasNode(rs.getString(1).substring(1))) {
					Document doc = new DirectDocumentModule().getProperties(session, rs.getString(1));
					DashboardStatsDocumentResult vo = new DashboardStatsDocumentResult();
					vo.setDocument(doc);
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(rs.getTimestamp(2).getTime());
					vo.setDate(cal);
					vo.setVisited(false);
					al.add(vo);
				}
			}
		} finally {
			actDao.closeResultSet(rs);
			actDao.closeStatement(stmt);
			actDao.closeConnection(con);
		}
		
		log.debug("getTopDocuments: {}", al);
		return al;
	}
	
	@Override
	public void visiteNode(String token, String source, String node, Calendar date)
			throws RepositoryException {
		log.info("visiteNode({}, {}, {}, {})", new Object[] { token, source, node, (date==null?null:date.getTime()) });
		Session session = null;
		
		try {
			if (Config.SESSION_MANAGER) {
				session = SessionManager.getInstance().get(token);
			} else {
				session = JCRUtils.getSession();
			}
			
			DashboardStats vo = new DashboardStats();
			vo.setDsUser(session.getUserID());
			vo.setDsSource(source);
			vo.setDsNode(node);
			vo.setDsDate(date);
			dsDao.create(vo);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(session);
			}
		}
		
		log.debug("visiteNode: void");
	}
	
	/**
	 * Check visited documents
	 */
	private void checkVisitedDocuments(String user, String source, 
			Collection<DashboardStatsDocumentResult> docResult) throws SQLException {
		Collection<DashboardStats> visitedNodes = dsDao.findByUserSource(user, source);
		
		// Set already visited nodes
		for (Iterator<DashboardStatsDocumentResult> itDocs = docResult.iterator(); itDocs.hasNext(); ) {
			DashboardStatsDocumentResult dsDocResult = itDocs.next();
								
			for (Iterator<DashboardStats> itVisited = visitedNodes.iterator(); itVisited.hasNext(); ) {
				DashboardStats visitedNode = itVisited.next();
			
				// Same node path and same activity log date ? 
				if (visitedNode.getDsNode().equals(dsDocResult.getDocument().getPath()) && 
						visitedNode.getDsDate().equals(dsDocResult.getDate())) {
					dsDocResult.setVisited(true);
				}
			}
		}
		
		// Purge old visited nodes
		String statement = "DELETE FROM dashboard_stats "+
			"WHERE ds_user=? AND ds_source=? AND ds_node=? AND ds_date=?";
		Connection con = null;
		PreparedStatement stmt = null;
			
		try {
			con = dsDao.getConnection();
			stmt = con.prepareStatement(statement);
			
			for (Iterator<DashboardStats> itVisited = visitedNodes.iterator(); itVisited.hasNext(); ) {
				DashboardStats visitedNode = itVisited.next();
				boolean old = true;
				
				for (Iterator<DashboardStatsDocumentResult> itDocs = docResult.iterator(); itDocs.hasNext(); ) {
					DashboardStatsDocumentResult dsDocResult = itDocs.next();
					
					// Same node path and same activity log date ? 
					if (visitedNode.getDsNode().equals(dsDocResult.getDocument().getPath()) && 
							visitedNode.getDsDate().equals(dsDocResult.getDate())) {
						old = false;
					}
				}

				if (old) {
					stmt.setString(1, user);
					stmt.setString(2, source);
					stmt.setString(3, visitedNode.getDsNode());
					stmt.setTimestamp(4, new Timestamp(visitedNode.getDsDate().getTimeInMillis()));
					stmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			actDao.closeStatement(stmt);
			actDao.closeConnection(con);
		}
	}
	
	/**
	 * Check visited folders
	 */
	private void checkVisitedFolders(String user, String source, 
			Collection<DashboardStatsFolderResult> fldResult) throws SQLException {
		Collection<DashboardStats> visitedNodes = dsDao.findByUserSource(user, source);
		
		// Set already visited nodes
		for (Iterator<DashboardStatsFolderResult> itFlds = fldResult.iterator(); itFlds.hasNext(); ) {
			DashboardStatsFolderResult dsFldResult = itFlds.next();
			
			for (Iterator<DashboardStats> itVisited = visitedNodes.iterator(); itVisited.hasNext(); ) {
				DashboardStats visitedNode = itVisited.next();
			
				if (visitedNode.getDsNode().equals(dsFldResult.getFolder().getPath()) && 
						visitedNode.getDsDate().equals(dsFldResult.getDate())) {
					dsFldResult.setVisited(true);
				}
			}
		}
		
		// Purge old visited nodes
		String statement = "DELETE FROM dashboard_stats "+
			"WHERE ds_user=? AND ds_source=? AND ds_node=? AND ds_date=?";
		Connection con = null;
		PreparedStatement stmt = null;
			
		try {
			con = dsDao.getConnection();
			stmt = con.prepareStatement(statement);
			
			for (Iterator<DashboardStats> itVisited = visitedNodes.iterator(); itVisited.hasNext(); ) {
				DashboardStats visitedNode = itVisited.next();
				boolean old = true;
				
				for (Iterator<DashboardStatsFolderResult> itFlds = fldResult.iterator(); itFlds.hasNext(); ) {
					DashboardStatsFolderResult dsFldResult = itFlds.next();
					
					// Same node path and same activity log date ? 
					if (visitedNode.getDsNode().equals(dsFldResult.getFolder().getPath()) && 
							visitedNode.getDsDate().equals(dsFldResult.getDate())) {
						old = false;
					}
				}

				if (old) {
					stmt.setString(1, user);
					stmt.setString(2, source);
					stmt.setString(3, visitedNode.getDsNode());
					stmt.setTimestamp(4, new Timestamp(visitedNode.getDsDate().getTimeInMillis()));
					stmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			actDao.closeStatement(stmt);
			actDao.closeConnection(con);
		}
	}

	/**
	 * Check visited mails
	 */
	private void checkVisitedMails(String user, String source, 
			Collection<DashboardStatsMailResult> mailResult) throws SQLException {
		Collection<DashboardStats> visitedNodes = dsDao.findByUserSource(user, source);
		
		// Set already visited nodes
		for (Iterator<DashboardStatsMailResult> itMails = mailResult.iterator(); itMails.hasNext(); ) {
			DashboardStatsMailResult dsMailResult = itMails.next();
								
			for (Iterator<DashboardStats> itVisited = visitedNodes.iterator(); itVisited.hasNext(); ) {
				DashboardStats visitedNode = itVisited.next();
			
				// Same node path and same activity log date ? 
				if (visitedNode.getDsNode().equals(dsMailResult.getMail().getPath()) && 
						visitedNode.getDsDate().equals(dsMailResult.getDate())) {
					dsMailResult.setVisited(true);
				}
			}
		}
		
		// Purge old visited nodes
		String statement = "DELETE FROM dashboard_stats "+
			"WHERE ds_user=? AND ds_source=? AND ds_node=? AND ds_date=?";
		Connection con = null;
		PreparedStatement stmt = null;
			
		try {
			con = dsDao.getConnection();
			stmt = con.prepareStatement(statement);
			
			for (Iterator<DashboardStats> itVisited = visitedNodes.iterator(); itVisited.hasNext(); ) {
				DashboardStats visitedNode = itVisited.next();
				boolean old = true;
				
				for (Iterator<DashboardStatsMailResult> itMails = mailResult.iterator(); itMails.hasNext(); ) {
					DashboardStatsMailResult dsMailResult = itMails.next();
					
					// Same node path and same activity log date ? 
					if (visitedNode.getDsNode().equals(dsMailResult.getMail().getPath()) && 
							visitedNode.getDsDate().equals(dsMailResult.getDate())) {
						old = false;
					}
				}

				if (old) {
					stmt.setString(1, user);
					stmt.setString(2, source);
					stmt.setString(3, visitedNode.getDsNode());
					stmt.setTimestamp(4, new Timestamp(visitedNode.getDsDate().getTimeInMillis()));
					stmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			actDao.closeStatement(stmt);
			actDao.closeConnection(con);
		}
	}

	/**
	 * Delete visited nodes
	 */
	public void deleteVisitedNodes(String user, String source) throws SQLException {
		log.info("deleteVisitedNodes({}, {})", user, source);
		String statement = "DELETE FROM dashboard_stats WHERE ds_user=? AND ds_source=?";
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dsDao.getConnection();
			stmt = con.prepareStatement(statement);
			stmt.setString(1, user);
			stmt.setString(2, source);
			stmt.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			actDao.closeStatement(stmt);
			actDao.closeConnection(con);
		}
		
		log.info("deleteVisitedNodes: void");
	}
}
