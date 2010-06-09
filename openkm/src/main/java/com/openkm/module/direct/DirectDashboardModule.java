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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.DashboardDocumentResult;
import com.openkm.bean.DashboardFolderResult;
import com.openkm.bean.DashboardMailResult;
import com.openkm.bean.Document;
import com.openkm.bean.Folder;
import com.openkm.bean.Mail;
import com.openkm.bean.cache.UserItems;
import com.openkm.cache.UserItemsManager;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.ActivityDAO;
import com.openkm.dao.DashboardDAO;
import com.openkm.dao.HibernateUtil;
import com.openkm.dao.QueryParamsDAO;
import com.openkm.dao.bean.Activity;
import com.openkm.dao.bean.Dashboard;
import com.openkm.dao.bean.QueryParams;
import com.openkm.module.DashboardModule;
import com.openkm.util.JCRUtils;
import com.openkm.util.UserActivity;

public class DirectDashboardModule implements DashboardModule {
	private static Logger log = LoggerFactory.getLogger(DirectDashboardModule.class);
	private static final int MAX_RESULTS = 20;

	@Override
	public List<DashboardDocumentResult> getUserLockedDocuments() throws RepositoryException,
			DatabaseException {
		log.debug("getUserLockedDocuments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getUserLockedDocuments(session);
			log.debug("getUserLockedDocuments: {}", al);
			return al;
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getUserLockedDocuments(Session session) throws
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getUserLockedDocuments({})", session);
		String qs = "/jcr:root/okm:root//element(*, okm:document)[@jcr:lockOwner='"+session.getUserID()+"' and okm:content/@jcr:isCheckedOut=false()]";
		List<DashboardDocumentResult> al = executeQueryDocument(session, qs, "LOCK_DOCUMENT", Integer.MAX_VALUE);

		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserLockedDocuments", al);
		log.debug("getUserLockedDocuments: {}", al);
		return al;
	}

	@Override
	public List<DashboardDocumentResult> getUserCheckedOutDocuments() throws RepositoryException, 
			DatabaseException {
		log.debug("getUserCheckedOutDocuments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getUserCheckedOutDocuments(session);
			log.debug("getUserCheckedOutDocuments: {}", al);
			return al;
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getUserCheckedOutDocuments(Session session) throws 
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getUserCheckedOutDocuments({})", session);
		String qs = "/jcr:root/okm:root//element(*, okm:document)[@jcr:lockOwner='"+session.getUserID()+"' and okm:content/@jcr:isCheckedOut=true()]";
		List<DashboardDocumentResult> al = executeQueryDocument(session, qs, "CHECKOUT_DOCUMENT", Integer.MAX_VALUE);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserCheckedOutDocuments", al);
		log.debug("getUserCheckedOutDocuments: {}", al);
		return al;
	}

	@Override
	public List<DashboardDocumentResult> getUserSubscribedDocuments() throws RepositoryException {
		log.debug("getUserSubscribedDocuments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getUserSubscribedDocuments(session);
			log.debug("getUserSubscribedDocuments: {}", al);
			return al;
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getUserSubscribedDocuments(Session session) throws 
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getUserSubscribedDocuments({})", session);
		String qs = "/jcr:root/okm:root//element(*, mix:notification)[@jcr:primaryType='okm:document' and @okm:subscriptors='"+session.getUserID()+"']";
		List<DashboardDocumentResult> al = executeQueryDocument(session, qs, "SUBSCRIBE_USER", Integer.MAX_VALUE);
			
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserSubscribedDocuments", al);
		log.debug("getUserSubscribedDocuments: {}", al);
		return al;
	}

	@Override
	public List<DashboardFolderResult> getUserSubscribedFolders() throws RepositoryException {
		log.debug("getUserSubscribedFolders()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardFolderResult> al = getUserSubscribedFolders(session);
			log.debug("getUserSubscribedFolders: {}", al);
			return al;
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardFolderResult> getUserSubscribedFolders(Session session) throws
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getUserSubscribedFolders({})", session);
		String qs = "/jcr:root/okm:root//element(*, mix:notification)[@jcr:primaryType='okm:folder' and @okm:subscriptors='"+session.getUserID()+"']";
		List<DashboardFolderResult> al = executeQueryFolder(session, qs, "SUBSCRIBE_USER", Integer.MAX_VALUE);

		// Check for already visited results
		checkVisitedFolders(session.getUserID(), "UserSubscribedFolders", al);
		log.debug("getUserSubscribedFolders: {}", al);
		return al;
	}

	/**
	 * Execute query with documents
	 */
	private List<DashboardDocumentResult> executeQueryDocument(Session session, String qs,
			String action, int maxResults) throws javax.jcr.RepositoryException, DatabaseException {
		List<DashboardDocumentResult> al = new ArrayList<DashboardDocumentResult>();
		Workspace workspace = session.getWorkspace();
		QueryManager queryManager = workspace.getQueryManager();
		Query query = queryManager.createQuery(qs, "xpath");
		QueryResult result = query.execute();
		int i = 0;
		
		for (NodeIterator nit = result.getNodes(); nit.hasNext() && i++ < maxResults; ) {
			Node node = nit.nextNode();
			Document doc = new DirectDocumentModule().getProperties(session, node.getPath());
			DashboardDocumentResult vo = new DashboardDocumentResult();
			vo.setDocument(doc);
			vo.setDate(ActivityDAO.getActivityDate(session.getUserID(), action, node.getPath()));
			vo.setVisited(false);
			al.add(vo);
		}
		
		// Sort results
		Collections.sort(al, new Comparator<DashboardDocumentResult>() {
		    public int compare(DashboardDocumentResult doc1, DashboardDocumentResult doc2) {
				return doc2.getDate().compareTo(doc1.getDate());
		    }
		});
		
		return al;
	}
	
	/**
	 * Execute query with folders
	 */
	private ArrayList<DashboardFolderResult> executeQueryFolder(Session session, String qs,
			String action, int maxResults) throws javax.jcr.RepositoryException, DatabaseException {
		ArrayList<DashboardFolderResult> al = new ArrayList<DashboardFolderResult>();
		Workspace workspace = session.getWorkspace();
		QueryManager queryManager = workspace.getQueryManager();
		Query query = queryManager.createQuery(qs, "xpath");
		QueryResult result = query.execute();
		int i = 0;
		
		for (NodeIterator nit = result.getNodes(); nit.hasNext() && i++ < maxResults; ) {
			Node node = nit.nextNode();
			Folder fld = new DirectFolderModule().getProperties(session, node.getPath());
			DashboardFolderResult vo = new DashboardFolderResult();
			vo.setFolder(fld);
			vo.setDate(ActivityDAO.getActivityDate(session.getUserID(), action, node.getPath()));
			vo.setVisited(false);
			al.add(vo);
		}
		
		// Order results
		Collections.sort(al, new Comparator<DashboardFolderResult>() {
		    public int compare(DashboardFolderResult fld1, DashboardFolderResult fld2) {
				return fld2.getDate().compareTo(fld1.getDate());
		    }
		});
		
		return al;
	}
	
	@Override
	public List<DashboardDocumentResult> getUserLastUploadedDocuments() throws RepositoryException {
		log.debug("getUserLastUploadedDocuments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getUserLastUploadedDocuments(session);
			log.debug("getUserLastUploadedDocuments: {}", al);
			return al;
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getUserLastUploadedDocuments(Session session) throws
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getUserLastUploadedDocuments({})", session);
		String qs = "from Activity a where a.action='CREATE_DOCUMENT' and a.user= :user " +
			"order by a.date desc";
		List<DashboardDocumentResult> al = getDocuments(session, qs);
			
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserLastUploadedDocuments", al);
		log.debug("getUserLastUploadedDocuments: {}", al);
		return al;
	}

	@Override
	public List<DashboardDocumentResult> getUserLastModifiedDocuments() throws RepositoryException {
		log.debug("getUserLastModifiedDocuments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getUserLastModifiedDocuments(session);
			log.debug("getUserLastModifiedDocuments: {}", al);
			return al;
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getUserLastModifiedDocuments(Session session) throws
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getUserLastModifiedDocuments({})", session);
		String qs = "select distinct a.item, max(a.date) from Activity a "+
			"where a.action='SET_DOCUMENT_CONTENT' and a.user= :user "+
			"group by a.item order by max(a.date) desc";
		List<DashboardDocumentResult> al = getDocuments(session, qs);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserLastModifiedDocuments", al);
		log.debug("getUserLastModifiedDocuments: {}", al);
		return al;
	}

	@Override
	public List<DashboardDocumentResult> getUserLastDownloadedDocuments() throws RepositoryException {
		log.debug("getUserLastDownloadedDocuments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getUserLastDownloadedDocuments(session);
			log.debug("getUserLastDownloadedDocuments: {}", al);
			return al;
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getUserLastDownloadedDocuments(Session session) throws
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getUserLastDownloadedDocuments({})", session);
		String qs = "select distinct a.item, max(a.date) from Activity a "+
			"where a.action='GET_DOCUMENT_CONTENT' and a.user= :user "+
			"group by a.item order by max(a.date) desc";
		List<DashboardDocumentResult> al = getDocuments(session, qs);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserLastDownloadedDocuments", al);
		log.debug("getUserLastDownloadedDocuments: {}", al);
		return al;
	}

	@Override
	public List<DashboardMailResult> getUserLastImportedMails() throws RepositoryException {
		log.debug("getUserLastImportedMails()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardMailResult> al = getUserLastImportedMails(session);
			log.debug("getUserLastImportedMails: {}", al);
			return al;
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardMailResult> getUserLastImportedMails(Session session) throws 
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getUserLastImportedMails({})", session);
		String sq = "from Activity a where a.action='CREATE_MAIL' and a.user= :user " +
			"order by a.date desc";
		List<DashboardMailResult> al = getMails(session, sq);
		
		// Check for already visited results
		checkVisitedMails(session.getUserID(), "UserLastImportedMails", al);
		log.debug("getUserLastImportedMails: {}", al);
		return al;
	}

	@Override
	public List<DashboardDocumentResult> getUserLastImportedMailAttachments() throws RepositoryException {
		log.debug("getUserLastImportedMailAttachments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getUserLastImportedMailAttachments(session);
			log.debug("getUserLastImportedMailAttachments: {}", al);
			return al;
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getUserLastImportedMailAttachments(Session session) throws
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getUserLastImportedMailAttachments({})", session);
		String qs = "from Activity a where a.action='CREATE_MAIL_ATTACHMENT' and a.user= :user " +
			"order by a.date desc";
		List<DashboardDocumentResult> al = getDocuments(session, qs);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "UserLastImportedMailAttachments", al);
		log.debug("getUserLastImportedMailAttachments: {}", al);
		return al;
	}
	
	
	/**
	 * Get documents from statement
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<DashboardDocumentResult> getDocuments(Session session, String qs) 
			throws javax.jcr.RepositoryException, DatabaseException {
		log.debug("getDocuments({}, {})", session, qs);
		ArrayList<DashboardDocumentResult> al = new ArrayList<DashboardDocumentResult>();
		org.hibernate.Session hSession = null;
		
		try {
			hSession = HibernateUtil.getSessionFactory().openSession();
			org.hibernate.Query q = hSession.createQuery(qs);
			q.setString("user", session.getUserID());
			q.setMaxResults(MAX_RESULTS);
			
			for (Iterator<Activity> it = q.list().iterator(); it.hasNext(); ) {
				Activity act = it.next();
				
				if (session.getRootNode().hasNode(act.getItem().substring(1))) {
					Document doc = new DirectDocumentModule().getProperties(session, act.getItem());
					DashboardDocumentResult vo = new DashboardDocumentResult();
					vo.setDocument(doc);
					vo.setDate(act.getDate());
					vo.setVisited(false);
					al.add(vo);
				}
			}
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(hSession);
		}
		
		log.debug("getDocuments: {}", al);
		return al;
	}

	/**
	 * Get mails from statement
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<DashboardMailResult> getMails(Session session, String qs) 
			throws javax.jcr.RepositoryException, DatabaseException {
		log.debug("getMails({}, {})", session, qs);
		ArrayList<DashboardMailResult> al = new ArrayList<DashboardMailResult>();
		org.hibernate.Session hSession = null;
		
		try {
			hSession = HibernateUtil.getSessionFactory().openSession();
			org.hibernate.Query q = hSession.createQuery(qs);
			q.setString("user", session.getUserID());
			q.setMaxResults(MAX_RESULTS);
			
			for (Iterator<Activity> it = q.list().iterator(); it.hasNext(); ) {
				Activity act = it.next();
				
				if (session.getRootNode().hasNode(act.getItem().substring(1))) {
					Mail mail = new DirectMailModule().getProperties(session, act.getItem());
					DashboardMailResult vo = new DashboardMailResult();
					vo.setMail(mail);
					vo.setDate(act.getDate());
					vo.setVisited(false);
					al.add(vo);
				}
			}
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(hSession);
		}
		
		log.debug("getMails: {}", al);
		return al;
	}

	@Override
	public long getUserDocumentsSize() throws RepositoryException, DatabaseException {
		log.info("getUserDocumentsSize()");
		long size = 0;
		
		if (Config.USER_SIZE_CACHE) {
			size = getUserDocumentsSizeCached();
		} else {
			size = getUserDocumentsSizeLive();
		}

		log.info("getUserDocumentsSize: {}", size);
		return size;
	}
	
	/**
	 * Get user document size
	 */
	private long getUserDocumentsSizeLive() throws RepositoryException, DatabaseException {
		log.info("getUserDocumentsSizeLive()");
		long size = 0;
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			String qs = "/jcr:root/okm:root//element(*, okm:document)[okm:content/@okm:author='"+session.getUserID()+"']";
			Workspace workspace = session.getWorkspace();
			QueryManager queryManager = workspace.getQueryManager();
			Query query = queryManager.createQuery(qs, "xpath");
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
			JCRUtils.logout(session);
		}

		log.info("getUserDocumentsSizeLive: {}", size);
		return size;
	}
	
	/**
	 * Get user document size
	 */
	private long getUserDocumentsSizeCached() throws RepositoryException, DatabaseException {
		log.info("getUserDocumentsSizeCached()");
		Session session = null;
		UserItems usrItems = null;
		
		try {
			session = JCRUtils.getSession();
			usrItems = UserItemsManager.get(session.getUserID());
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
		
		log.info("getUserDocumentsSizeCached: {}", usrItems.getSize());
		return usrItems.getSize();
	}

	@Override
	public List<QueryParams> getUserSearchs() throws RepositoryException, DatabaseException {
		log.debug("getUserSearchs()");
		List<QueryParams> ret = new ArrayList<QueryParams>();
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<QueryParams> qParams = QueryParamsDAO.findByUser(session.getUserID());
			
			for (Iterator<QueryParams> it = qParams.iterator(); it.hasNext(); ) {
				QueryParams qp = it.next();
				if (qp.isDashboard()) {
					ret.add(qp);
				}
			}
			
			// Activity log
			UserActivity.log(session.getUserID(), "GET_DASHBOARD_USER_SEARCHS", null, null);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (DatabaseException e) {
			throw e;
		} finally {
			JCRUtils.logout(session);
		}
		
		log.debug("getUserSearchs: {}", ret);
		return ret;
	}

	@Override
	public List<DashboardDocumentResult> find(int qpId) throws IOException, ParseException,
			RepositoryException, DatabaseException {
		log.debug("find({})", qpId);
		List<DashboardDocumentResult> al = new ArrayList<DashboardDocumentResult>();
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			al = find(session, qpId);
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
		
		log.debug("find: {}", al);
		return al;
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> find(Session session, int qpId) throws 
			javax.jcr.RepositoryException, DatabaseException, ParseException, IOException {
		log.debug("find({}, {})", session, qpId);
		List<DashboardDocumentResult> al = new ArrayList<DashboardDocumentResult>();
		DirectSearchModule directSearch = new DirectSearchModule();
		
		// Get the saved query params
		QueryParams params = QueryParamsDAO.findByPk(qpId);
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
		String qs = directSearch.prepareStatement(params);
		log.debug("STATEMENT {}", qs);
		
		// Execute query
		al = executeQueryDocument(session, qs, null, MAX_RESULTS);
		
		// Update query params
		QueryParamsDAO.update(params);
			
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), Integer.toString(params.getId()), al);
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
	public List<DashboardDocumentResult> getLastWeekTopDownloadedDocuments() throws RepositoryException {
		log.debug("getLastWeekTopDownloadedDocuments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getLastWeekTopDownloadedDocuments(session);
			log.debug("getLastWeekTopDownloadedDocuments: {}", al);
			return al;
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getLastWeekTopDownloadedDocuments(Session session) throws
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getLastWeekTopDownloadedDocuments({})", session);
		String qs = "select a.item, max(a.date) from Activity a "+
			"where a.action='GET_DOCUMENT_CONTENT' and a.item like '/okm:root/%' "+
			"and a.date>:date group by a.item order by count(a.item) desc";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		List<DashboardDocumentResult> al = getTopDocuments(session, qs, cal);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "LastWeekTopDownloadedDocuments", al);
		log.debug("getLastWeekTopDownloadedDocuments: {}", al);
		return al;
	}
	
	@Override
	public List<DashboardDocumentResult> getLastMonthTopDownloadedDocuments() throws RepositoryException {
		log.debug("getLastMonthTopDownloadedDocuments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getLastMonthTopDownloadedDocuments(session);
			log.debug("getLastMonthTopDownloadedDocuments: {}", al);
			return al;
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getLastMonthTopDownloadedDocuments(Session session) throws
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getLastMonthTopDownloadedDocuments({})", session);
		String qs = "select a.item, max(a.date) from Activity a "+
			"where a.action='GET_DOCUMENT_CONTENT' and a.item like '/okm:root/%' "+
			"and a.date>:date group by a.item order by count(a.item) desc";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		List<DashboardDocumentResult> al = getTopDocuments(session, qs, cal);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "LastMonthTopDownloadedDocuments", al);
		log.debug("getLastMonthTopDownloadedDocuments: {}", al);
		return al;
	}
	
	@Override
	public List<DashboardDocumentResult> getLastWeekTopModifiedDocuments() throws RepositoryException {
		log.debug("getLastWeekTopModifiedDocuments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getLastWeekTopModifiedDocuments(session);
			log.debug("getLastWeekTopModifiedDocuments: {}", al);
			return al;
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getLastWeekTopModifiedDocuments(Session session) throws
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getLastWeekTopModifiedDocuments({})", session);
		String qs = "select a.item, max(a.date) from Activity a "+
			"where a.action='SET_DOCUMENT_CONTENT' and a.item like '/okm:root/%' "+
			"and a.date>:date group by a.item order by count(a.item) desc";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		List<DashboardDocumentResult> al = getTopDocuments(session, qs, cal);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "LastWeekTopModifiedDocuments", al);
		log.debug("getLastWeekTopModifiedDocuments: {}", al);
		return al;
	}
	
	@Override
	public List<DashboardDocumentResult> getLastMonthTopModifiedDocuments() throws RepositoryException {
		log.debug("getLastMonthTopModifiedDocuments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getLastMonthTopModifiedDocuments(session);
			log.debug("getLastMonthTopModifiedDocuments: {}", al);
			return al;
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getLastMonthTopModifiedDocuments(Session session) throws
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getLastMonthTopModifiedDocuments({})", session);
		String qs = "select a.item, max(a.date) from Activity a "+
			"where a.action='SET_DOCUMENT_CONTENT' and a.item like '/okm:root/%' "+
			"and a.date>:date group by a.item order by count(a.item) desc";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		List<DashboardDocumentResult> al = getTopDocuments(session, qs, cal); 
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "LastMonthTopModifiedDocuments", al);
		log.debug("getLastMonthTopModifiedDocuments: {}", al);
		return al;
	}
	
	@Override
	public List<DashboardDocumentResult> getLastModifiedDocuments() throws RepositoryException {
		log.debug("getLastModifiedDocuments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getLastModifiedDocuments(session);
			log.debug("getLastModifiedDocuments: {}", al);
			return al;
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			log.error(e.getMessage(), e);
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getLastModifiedDocuments(Session session) throws
			javax.jcr.RepositoryException, DatabaseException {
		log.debug("getLastModifiedDocuments({})", session);
		String qs = "select distinct a.item, max(a.date) from Activity a "+
			"where a.action='SET_DOCUMENT_CONTENT' and a.item like '/okm:root/%' "+
			"group by a.item order by max(a.date) desc";
		List<DashboardDocumentResult> al = getTopDocuments(session, qs, null);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "LastModifiedDocuments", al);
		log.debug("getLastModifiedDocuments: {}", al);
		return al;
	}
	
	@Override
	public List<DashboardDocumentResult> getLastUploadedDocuments() throws RepositoryException {
		log.debug("getLastUploadedDocuments()");
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			List<DashboardDocumentResult> al = getLastUploadedDocuments(session);
			log.debug("getLastUploadedDocuments: {}", al);
			return al;
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
	}
	
	/**
	 * Convenient method for syndication
	 */
	public List<DashboardDocumentResult> getLastUploadedDocuments(Session session) 
			throws javax.jcr.RepositoryException, DatabaseException {
		log.debug("getLastUploadedDocuments({})", session);
		String qs = "select distinct a.item, max(a.date) from Activity a "+
			"where a.action='CREATE_DOCUMENT' and a.item like '/okm:root/%' "+
			"group by a.item order by max(a.date) desc";
		List<DashboardDocumentResult> al = getTopDocuments(session, qs, null);
		
		// Check for already visited results
		checkVisitedDocuments(session.getUserID(), "LastUploadedDocuments", al);
		
		log.debug("getLastUploadedDocuments: {}", al);
		return al;
	}

	/**
	 * Get top documents
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<DashboardDocumentResult> getTopDocuments(Session session, String qs, Calendar date) 
			throws javax.jcr.RepositoryException, DatabaseException {
		log.debug("getTopDocuments({}, {}, {})", new Object[] { session, qs, (date!=null?date.getTime():"null") });
		ArrayList<DashboardDocumentResult> al = new ArrayList<DashboardDocumentResult>();
		org.hibernate.Session hSession = null;
		
		try {
			hSession = HibernateUtil.getSessionFactory().openSession();
			org.hibernate.Query q = hSession.createQuery(qs);
			
			if (date != null) {
				q.setCalendar("date", date); 
			}
			
			q.setMaxResults(MAX_RESULTS);
			
			for (Iterator<Object[]> it = q.list().iterator(); it.hasNext(); ) {
				Object[] obj = it.next();
				String resItem = (String) obj[0];
				Calendar resDate = (Calendar) obj[1];
				
				if (session.getRootNode().hasNode(resItem.substring(1))) {
					Document doc = new DirectDocumentModule().getProperties(session, resItem);
					DashboardDocumentResult vo = new DashboardDocumentResult();
					vo.setDocument(doc);
					vo.setDate(resDate);
					vo.setVisited(false);
					al.add(vo);
				}
			}
			
			log.debug("getTopDocuments: {}", al);
			return al;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(hSession);
		}
	}
	
	@Override
	public void visiteNode(String source, String node, Calendar date) throws RepositoryException {
		log.info("visiteNode({}, {}, {})", new Object[] { source, node, (date==null?null:date.getTime()) });
		Session session = null;
		
		try {
			session = JCRUtils.getSession();
			Dashboard vo = new Dashboard();
			vo.setUser(session.getUserID());
			vo.setSource(source);
			vo.setNode(node);
			vo.setDate(date);
			DashboardDAO.create(vo);
		} catch (DatabaseException e) {
			throw new RepositoryException(e.getMessage(), e);
		} catch (javax.jcr.RepositoryException e) {
			throw new RepositoryException(e.getMessage(), e);
		} finally {
			JCRUtils.logout(session);
		}
		
		log.debug("visiteNode: void");
	}
	
	/**
	 * Check visited documents
	 */
	private void checkVisitedDocuments(String user, String source, 
			List<DashboardDocumentResult> docResult) throws DatabaseException {
		List<Dashboard> visitedNodes = DashboardDAO.findByUserSource(user, source);
		
		// Set already visited nodes
		for (Iterator<DashboardDocumentResult> itDocs = docResult.iterator(); itDocs.hasNext(); ) {
			DashboardDocumentResult dsDocResult = itDocs.next();
								
			for (Iterator<Dashboard> itVisited = visitedNodes.iterator(); itVisited.hasNext(); ) {
				Dashboard visitedNode = itVisited.next();
			
				// Same node path and same activity log date ? 
				if (visitedNode.getNode().equals(dsDocResult.getDocument().getPath()) && 
						visitedNode.getDate().equals(dsDocResult.getDate())) {
					dsDocResult.setVisited(true);
				}
			}
		}
			
		for (Iterator<Dashboard> itVisited = visitedNodes.iterator(); itVisited.hasNext(); ) {
			Dashboard visitedNode = itVisited.next();
			boolean old = true;
			
			for (Iterator<DashboardDocumentResult> itDocs = docResult.iterator(); itDocs.hasNext(); ) {
				DashboardDocumentResult dsDocResult = itDocs.next();
				
				// Same node path and same activity log date ? 
				if (visitedNode.getNode().equals(dsDocResult.getDocument().getPath()) && 
						visitedNode.getDate().equals(dsDocResult.getDate())) {
					old = false;
				}
			}
			
			if (old) {
				DashboardDAO.purgeOldVisitedNode(user, source, visitedNode.getNode(), visitedNode.getDate());
			}
		}
	}
	
	/**
	 * Check visited folders
	 */
	private void checkVisitedFolders(String user, String source, 
			List<DashboardFolderResult> fldResult) throws DatabaseException {
		List<Dashboard> visitedNodes = DashboardDAO.findByUserSource(user, source);
		
		// Set already visited nodes
		for (Iterator<DashboardFolderResult> itFlds = fldResult.iterator(); itFlds.hasNext(); ) {
			DashboardFolderResult dsFldResult = itFlds.next();
			
			for (Iterator<Dashboard> itVisited = visitedNodes.iterator(); itVisited.hasNext(); ) {
				Dashboard visitedNode = itVisited.next();
			
				if (visitedNode.getNode().equals(dsFldResult.getFolder().getPath()) && 
						visitedNode.getDate().equals(dsFldResult.getDate())) {
					dsFldResult.setVisited(true);
				}
			}
		}
			
		for (Iterator<Dashboard> itVisited = visitedNodes.iterator(); itVisited.hasNext(); ) {
			Dashboard visitedNode = itVisited.next();
			boolean old = true;
			
			for (Iterator<DashboardFolderResult> itFlds = fldResult.iterator(); itFlds.hasNext(); ) {
				DashboardFolderResult dsFldResult = itFlds.next();
				
				// Same node path and same activity log date ? 
				if (visitedNode.getNode().equals(dsFldResult.getFolder().getPath()) && 
						visitedNode.getDate().equals(dsFldResult.getDate())) {
					old = false;
				}
			}
			
			if (old) {
				DashboardDAO.purgeOldVisitedNode(user, source, visitedNode.getNode(), visitedNode.getDate());
			}
		}
	}

	/**
	 * Check visited mails
	 */
	private void checkVisitedMails(String user, String source, 
			List<DashboardMailResult> mailResult) throws DatabaseException {
		List<Dashboard> visitedNodes = DashboardDAO.findByUserSource(user, source);
		
		// Set already visited nodes
		for (Iterator<DashboardMailResult> itMails = mailResult.iterator(); itMails.hasNext(); ) {
			DashboardMailResult dsMailResult = itMails.next();
								
			for (Iterator<Dashboard> itVisited = visitedNodes.iterator(); itVisited.hasNext(); ) {
				Dashboard visitedNode = itVisited.next();
			
				// Same node path and same activity log date ? 
				if (visitedNode.getNode().equals(dsMailResult.getMail().getPath()) && 
						visitedNode.getDate().equals(dsMailResult.getDate())) {
					dsMailResult.setVisited(true);
				}
			}
		}
			
		for (Iterator<Dashboard> itVisited = visitedNodes.iterator(); itVisited.hasNext(); ) {
			Dashboard visitedNode = itVisited.next();
			boolean old = true;
			
			for (Iterator<DashboardMailResult> itMails = mailResult.iterator(); itMails.hasNext(); ) {
				DashboardMailResult dsMailResult = itMails.next();
				
				// Same node path and same activity log date ? 
				if (visitedNode.getNode().equals(dsMailResult.getMail().getPath()) && 
						visitedNode.getDate().equals(dsMailResult.getDate())) {
					old = false;
				}
			}
			
			if (old) {
				DashboardDAO.purgeOldVisitedNode(user, source, visitedNode.getNode(), visitedNode.getDate());
			}
		}
	}
}
