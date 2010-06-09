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

package com.openkm.api;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.DashboardDocumentResult;
import com.openkm.bean.DashboardFolderResult;
import com.openkm.bean.DashboardMailResult;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.bean.QueryParams;
import com.openkm.module.DashboardModule;
import com.openkm.module.ModuleManager;

/**
 * @author pavila
 *
 */
public class OKMDashboard implements DashboardModule {
	private static Logger log = LoggerFactory.getLogger(OKMDashboard.class);
	private static OKMDashboard instance = new OKMDashboard();

	private OKMDashboard() {}
	
	public static OKMDashboard getInstance() {
		return instance;
	}

	@Override
	public List<DashboardDocumentResult> getUserCheckedOutDocuments() throws RepositoryException,
			DatabaseException {
		log.debug("getUserCheckedOutDocuments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getUserCheckedOutDocuments();
		log.debug("getUserCheckedOutDocuments: {}", result);
		return result;
	}

	@Override
	public List<DashboardDocumentResult> getUserLastModifiedDocuments() throws RepositoryException, 
			DatabaseException {
		log.debug("getUserLastModifiedDocuments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getUserLastModifiedDocuments();
		log.debug("getUserLastModifiedDocuments: {}", result);
		return result;
	}

	@Override
	public List<DashboardDocumentResult> getUserLockedDocuments() throws RepositoryException, 
			DatabaseException {
		log.debug("getUserLockedDocuments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getUserLockedDocuments();
		log.debug("getUserLockedDocuments: {}", result);
		return result;
	}

	@Override
	public List<DashboardDocumentResult> getUserSubscribedDocuments() throws RepositoryException,
			DatabaseException {
		log.debug("getUserSubscribedDocuments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getUserSubscribedDocuments();
		log.debug("getUserSubscribedDocuments: {}", result);
		return result;
	}

	@Override
	public List<DashboardFolderResult> getUserSubscribedFolders() throws RepositoryException,
			DatabaseException {
		log.debug("getUserSubscribedFolders()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardFolderResult> result = dm.getUserSubscribedFolders();
		log.debug("getUserSubscribedFolders: {}", result);
		return result;
	}

	@Override
	public List<DashboardDocumentResult> getUserLastUploadedDocuments() throws RepositoryException,
			DatabaseException {
		log.debug("getUserLastUploadedDocuments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getUserLastUploadedDocuments();
		log.debug("getUserLastUploadedDocuments: {}", result);
		return result;
	}

	@Override
	public List<DashboardDocumentResult> getUserLastDownloadedDocuments() throws RepositoryException,
			DatabaseException {
		log.debug("getUserLastDownloadedDocuments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getUserLastDownloadedDocuments();
		log.debug("getUserLastDownloadedDocuments: {}", result);
		return result;
	}

	@Override
	public List<DashboardMailResult> getUserLastImportedMails() throws RepositoryException,
			DatabaseException {
		log.debug("getUserLastImportedMails()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardMailResult> result = dm.getUserLastImportedMails();
		log.debug("getUserLastImportedMails: {}", result);
		return result;
	}

	@Override
	public List<DashboardDocumentResult> getUserLastImportedMailAttachments() throws RepositoryException,
			DatabaseException {
		log.debug("getUserLastImportedMailAttachments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getUserLastImportedMailAttachments();
		log.debug("getUserLastImportedMailAttachments: {}", result);
		return result;
	}

	@Override
	public long getUserDocumentsSize() throws RepositoryException, DatabaseException {
		log.debug("getUserDocumentsSize()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		long size = dm.getUserDocumentsSize();
		log.debug("getUserDocumentsSize: {}", size);
		return size;
	}

	@Override
	public List<QueryParams> getUserSearchs() throws RepositoryException, DatabaseException {
		log.debug("getUserSearchs()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<QueryParams> searchs = dm.getUserSearchs();
		log.debug("getUserSearchs: {}", searchs);
		return searchs;
	}

	@Override
	public List<DashboardDocumentResult> find(int qpId) throws IOException, ParseException, 
			RepositoryException, DatabaseException {
		log.debug("find({})", qpId);
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> documents = dm.find(qpId);
		log.debug("find: {}", documents);
		return documents;
	}

	@Override
	public List<DashboardDocumentResult> getLastWeekTopDownloadedDocuments() throws RepositoryException,
			DatabaseException {
		log.debug("getLastWeekTopDownloadedDocuments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getLastWeekTopDownloadedDocuments();
		log.debug("getLastWeekTopDownloadedDocuments: {}", result);
		return result;
	}

	@Override
	public List<DashboardDocumentResult> getLastMonthTopDownloadedDocuments() throws RepositoryException,
			DatabaseException {
		log.debug("getLastMonthTopDownloadedDocuments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getLastMonthTopDownloadedDocuments();
		log.debug("getLastMonthTopDownloadedDocuments: {}", result);
		return result;
	}

	@Override
	public List<DashboardDocumentResult> getLastWeekTopModifiedDocuments() throws RepositoryException,
			DatabaseException {
		log.debug("getLastWeekTopModifiedDocuments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getLastWeekTopModifiedDocuments();
		log.debug("getLastWeekTopModifiedDocuments: {}", result);
		return result;
	}

	@Override
	public List<DashboardDocumentResult> getLastMonthTopModifiedDocuments() throws RepositoryException,
			DatabaseException {
		log.debug("getLastMonthTopModifiedDocuments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getLastMonthTopModifiedDocuments();
		log.debug("getLastMonthTopModifiedDocuments: {}", result);
		return result;
	}

	@Override
	public List<DashboardDocumentResult> getLastModifiedDocuments() throws RepositoryException,
			DatabaseException {
		log.debug("getLastModifiedDocuments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getLastModifiedDocuments();
		log.debug("getLastModifiedDocuments: {}", result);
		return result;
	}

	@Override
	public List<DashboardDocumentResult> getLastUploadedDocuments() throws RepositoryException,
			DatabaseException {
		log.debug("getLastUploadedDocuments()");
		DashboardModule dm = ModuleManager.getDashboardModule();
		List<DashboardDocumentResult> result = dm.getLastUploadedDocuments();
		log.debug("getLastUploadedDocuments: {}", result);
		return result;
	}

	@Override
	public void visiteNode(String source, String node, Calendar date) throws RepositoryException,
			DatabaseException {
		log.debug("visiteNode({}, {}, {})", new Object[] { source, node, date });
		DashboardModule dm = ModuleManager.getDashboardModule();
		dm.visiteNode(source, node, date);
		log.debug("visiteNode: void");
	}	
}
