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
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.bean.DashboardStatsDocumentResult;
import com.openkm.bean.DashboardStatsFolderResult;
import com.openkm.bean.DashboardStatsMailResult;
import com.openkm.core.RepositoryException;
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
		
	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getUserCheckedOutDocuments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getUserCheckedOutDocuments(String token)
			throws RepositoryException {
		log.debug("getUserCheckedOutDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserCheckedOutDocuments(token);
		log.debug("getUserCheckedOutDocuments: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getUserLastModifiedDocuments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getUserLastModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLastModifiedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserLastModifiedDocuments(token);
		log.debug("getUserLastModifiedDocuments: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getUserLockedDocuments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getUserLockedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLockedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserLockedDocuments(token);
		log.debug("getUserLockedDocuments: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getUserSubscribedDocuments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getUserSubscribedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserSubscribedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserSubscribedDocuments(token);
		log.debug("getUserSubscribedDocuments: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getUserSubscribedFolders(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsFolderResult> getUserSubscribedFolders(String token)
			throws RepositoryException {
		log.debug("getUserSubscribedFolders(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsFolderResult> result = dm.getUserSubscribedFolders(token);
		log.debug("getUserSubscribedFolders: "+result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getUserLastUploadedDocuments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getUserLastUploadedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLastUploadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserLastUploadedDocuments(token);
		log.debug("getUserLastUploadedDocuments: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getUserLastDownloadedDocuments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getUserLastDownloadedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLastDownloadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserLastDownloadedDocuments(token);
		log.debug("getUserLastDownloadedDocuments: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getUserLastImportedMails(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsMailResult> getUserLastImportedMails(String token)
			throws RepositoryException {
		log.debug("getUserLastImportedMails(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsMailResult> result = dm.getUserLastImportedMails(token);
		log.debug("getUserLastImportedMails: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getUserLastImportedMailAttachments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getUserLastImportedMailAttachments(String token)
			throws RepositoryException {
		log.debug("getUserLastImportedMailAttachments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserLastImportedMailAttachments(token);
		log.debug("getUserLastImportedMailAttachments: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getUserDocumentsSize(java.lang.String)
	 */
	@Override
	public long getUserDocumentsSize(String token) throws RepositoryException {
		log.debug("getUserDocumentsSize(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		long size = dm.getUserDocumentsSize(token);
		log.debug("getUserDocumentsSize: "+size);
		return size;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getUserSearchs(java.lang.String)
	 */
	@Override
	public Collection<String> getUserSearchs(String token)
			throws RepositoryException {
		log.debug("getUserSearchs(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<String> searchs = dm.getUserSearchs(token);
		log.debug("getUserSearchs: "+searchs);
		return searchs;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> find(String token, String name)
			throws IOException, RepositoryException {
		log.debug("find(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> documents = dm.find(token, name);
		log.debug("find: "+documents);
		return documents;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getLastWeekTopDownloadedDocuments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getLastWeekTopDownloadedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastWeekTopDownloadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getLastWeekTopDownloadedDocuments(token);
		log.debug("getLastWeekTopDownloadedDocuments: "+result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getLastMonthTopDownloadedDocuments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getLastMonthTopDownloadedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastMonthTopDownloadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getLastMonthTopDownloadedDocuments(token);
		log.debug("getLastMonthTopDownloadedDocuments: "+result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getLastWeekTopModifiedDocuments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getLastWeekTopModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastWeekTopModifiedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getLastWeekTopModifiedDocuments(token);
		log.debug("getLastWeekTopModifiedDocuments: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getLastMonthTopModifiedDocuments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getLastMonthTopModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastMonthTopModifiedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getLastMonthTopModifiedDocuments(token);
		log.debug("getLastMonthTopModifiedDocuments: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getLastModifiedDocuments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getLastModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastModifiedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getLastModifiedDocuments(token);
		log.debug("getLastModifiedDocuments: "+result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#getLastUploadedDocuments(java.lang.String)
	 */
	@Override
	public Collection<DashboardStatsDocumentResult> getLastUploadedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastUploadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getLastUploadedDocuments(token);
		log.debug("getLastUploadedDocuments: "+result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.openkm.module.DashboardModule#visiteNode(java.lang.String, java.lang.String, java.lang.String, java.util.Calendar)
	 */
	@Override
	public void visiteNode(String token, String source, String node, Calendar date)
			throws RepositoryException {
		log.debug("visiteNode(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		dm.visiteNode(token, source, node, date);
		log.debug("visiteNode: void");
	}	
}
