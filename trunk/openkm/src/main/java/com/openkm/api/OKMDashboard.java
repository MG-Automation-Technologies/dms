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

import com.openkm.bean.DashboardDocumentResult;
import com.openkm.bean.DashboardFolderResult;
import com.openkm.bean.DashboardMailResult;
import com.openkm.core.ParseException;
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

	@Override
	public Collection<DashboardDocumentResult> getUserCheckedOutDocuments(String token)
			throws RepositoryException {
		log.debug("getUserCheckedOutDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getUserCheckedOutDocuments(token);
		log.debug("getUserCheckedOutDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardDocumentResult> getUserLastModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLastModifiedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getUserLastModifiedDocuments(token);
		log.debug("getUserLastModifiedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardDocumentResult> getUserLockedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLockedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getUserLockedDocuments(token);
		log.debug("getUserLockedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardDocumentResult> getUserSubscribedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserSubscribedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getUserSubscribedDocuments(token);
		log.debug("getUserSubscribedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardFolderResult> getUserSubscribedFolders(String token)
			throws RepositoryException {
		log.debug("getUserSubscribedFolders(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardFolderResult> result = dm.getUserSubscribedFolders(token);
		log.debug("getUserSubscribedFolders: "+result);
		return result;
	}

	@Override
	public Collection<DashboardDocumentResult> getUserLastUploadedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLastUploadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getUserLastUploadedDocuments(token);
		log.debug("getUserLastUploadedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardDocumentResult> getUserLastDownloadedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLastDownloadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getUserLastDownloadedDocuments(token);
		log.debug("getUserLastDownloadedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardMailResult> getUserLastImportedMails(String token)
			throws RepositoryException {
		log.debug("getUserLastImportedMails(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardMailResult> result = dm.getUserLastImportedMails(token);
		log.debug("getUserLastImportedMails: "+result);
		return result;
	}

	@Override
	public Collection<DashboardDocumentResult> getUserLastImportedMailAttachments(String token)
			throws RepositoryException {
		log.debug("getUserLastImportedMailAttachments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getUserLastImportedMailAttachments(token);
		log.debug("getUserLastImportedMailAttachments: "+result);
		return result;
	}

	@Override
	public long getUserDocumentsSize(String token) throws RepositoryException {
		log.debug("getUserDocumentsSize(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		long size = dm.getUserDocumentsSize(token);
		log.debug("getUserDocumentsSize: "+size);
		return size;
	}

	@Override
	public Collection<String> getUserSearchs(String token)
			throws RepositoryException {
		log.debug("getUserSearchs(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<String> searchs = dm.getUserSearchs(token);
		log.debug("getUserSearchs: "+searchs);
		return searchs;
	}

	@Override
	public Collection<DashboardDocumentResult> find(String token, String name)
			throws IOException, ParseException, RepositoryException {
		log.debug("find(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> documents = dm.find(token, name);
		log.debug("find: "+documents);
		return documents;
	}

	@Override
	public Collection<DashboardDocumentResult> getLastWeekTopDownloadedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastWeekTopDownloadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getLastWeekTopDownloadedDocuments(token);
		log.debug("getLastWeekTopDownloadedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardDocumentResult> getLastMonthTopDownloadedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastMonthTopDownloadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getLastMonthTopDownloadedDocuments(token);
		log.debug("getLastMonthTopDownloadedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardDocumentResult> getLastWeekTopModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastWeekTopModifiedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getLastWeekTopModifiedDocuments(token);
		log.debug("getLastWeekTopModifiedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardDocumentResult> getLastMonthTopModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastMonthTopModifiedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getLastMonthTopModifiedDocuments(token);
		log.debug("getLastMonthTopModifiedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardDocumentResult> getLastModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastModifiedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getLastModifiedDocuments(token);
		log.debug("getLastModifiedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardDocumentResult> getLastUploadedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastUploadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardDocumentResult> result = dm.getLastUploadedDocuments(token);
		log.debug("getLastUploadedDocuments: "+result);
		return result;
	}

	@Override
	public void visiteNode(String token, String source, String node, Calendar date)
			throws RepositoryException {
		log.debug("visiteNode(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		dm.visiteNode(token, source, node, date);
		log.debug("visiteNode: void");
	}	
}
