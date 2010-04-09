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

package es.git.openkm.api;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.bean.DashboardStatsDocumentResult;
import es.git.openkm.bean.DashboardStatsFolderResult;
import es.git.openkm.bean.DashboardStatsMailResult;
import es.git.openkm.core.ParseException;
import es.git.openkm.core.RepositoryException;
import es.git.openkm.module.DashboardModule;
import es.git.openkm.module.ModuleManager;

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
	public Collection<DashboardStatsDocumentResult> getUserCheckedOutDocuments(String token)
			throws RepositoryException {
		log.debug("getUserCheckedOutDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserCheckedOutDocuments(token);
		log.debug("getUserCheckedOutDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getUserLastModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLastModifiedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserLastModifiedDocuments(token);
		log.debug("getUserLastModifiedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getUserLockedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLockedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserLockedDocuments(token);
		log.debug("getUserLockedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getUserSubscribedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserSubscribedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserSubscribedDocuments(token);
		log.debug("getUserSubscribedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsFolderResult> getUserSubscribedFolders(String token)
			throws RepositoryException {
		log.debug("getUserSubscribedFolders(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsFolderResult> result = dm.getUserSubscribedFolders(token);
		log.debug("getUserSubscribedFolders: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getUserLastUploadedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLastUploadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserLastUploadedDocuments(token);
		log.debug("getUserLastUploadedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getUserLastDownloadedDocuments(String token)
			throws RepositoryException {
		log.debug("getUserLastDownloadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserLastDownloadedDocuments(token);
		log.debug("getUserLastDownloadedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsMailResult> getUserLastImportedMails(String token)
			throws RepositoryException {
		log.debug("getUserLastImportedMails(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsMailResult> result = dm.getUserLastImportedMails(token);
		log.debug("getUserLastImportedMails: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getUserLastImportedMailAttachments(String token)
			throws RepositoryException {
		log.debug("getUserLastImportedMailAttachments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getUserLastImportedMailAttachments(token);
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
	public Collection<DashboardStatsDocumentResult> find(String token, String name)
			throws IOException, ParseException, RepositoryException {
		log.debug("find(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> documents = dm.find(token, name);
		log.debug("find: "+documents);
		return documents;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getLastWeekTopDownloadedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastWeekTopDownloadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getLastWeekTopDownloadedDocuments(token);
		log.debug("getLastWeekTopDownloadedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getLastMonthTopDownloadedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastMonthTopDownloadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getLastMonthTopDownloadedDocuments(token);
		log.debug("getLastMonthTopDownloadedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getLastWeekTopModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastWeekTopModifiedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getLastWeekTopModifiedDocuments(token);
		log.debug("getLastWeekTopModifiedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getLastMonthTopModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastMonthTopModifiedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getLastMonthTopModifiedDocuments(token);
		log.debug("getLastMonthTopModifiedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getLastModifiedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastModifiedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getLastModifiedDocuments(token);
		log.debug("getLastModifiedDocuments: "+result);
		return result;
	}

	@Override
	public Collection<DashboardStatsDocumentResult> getLastUploadedDocuments(String token)
			throws RepositoryException {
		log.debug("getLastUploadedDocuments(" + token + ")");
		DashboardModule dm = ModuleManager.getDashboardModule();
		Collection<DashboardStatsDocumentResult> result = dm.getLastUploadedDocuments(token);
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
