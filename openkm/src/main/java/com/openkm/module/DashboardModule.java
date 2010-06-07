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
import java.util.Calendar;
import java.util.Collection;

import com.openkm.bean.DashboardDocumentResult;
import com.openkm.bean.DashboardFolderResult;
import com.openkm.bean.DashboardMailResult;
import com.openkm.core.DatabaseException;
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;

public interface DashboardModule {

	/**
	 * Get list of locked documents by user
	 */
	public Collection<DashboardDocumentResult> getUserLockedDocuments(String token)
			throws RepositoryException;
	
	/**
	 * Get list of checked-out documents by user
	 */
	public Collection<DashboardDocumentResult> getUserCheckedOutDocuments(String token)
			throws RepositoryException;
	
	/**
	 * Get user subscribed documents
	 */
	public Collection<DashboardDocumentResult> getUserSubscribedDocuments(String token)
			throws RepositoryException;
	
	/**
	 * Get user subscribed folders
	 */
	public Collection<DashboardFolderResult> getUserSubscribedFolders(String token)
			throws RepositoryException;
	
	/**
	 * Get user last uploaded documents 
	 */
	public Collection<DashboardDocumentResult> getUserLastUploadedDocuments(String token)
			throws RepositoryException;
	
	/**
	 * Get user last modified documents 
	 */
	public Collection<DashboardDocumentResult> getUserLastModifiedDocuments(String token)
			throws RepositoryException;
	
	/**
	 * Get user last downloaded documents
	 */
	public Collection<DashboardDocumentResult> getUserLastDownloadedDocuments(String token)
		throws RepositoryException;
	
	/**
	 * Get user last imported mails
	 */
	public Collection<DashboardMailResult> getUserLastImportedMails(String token)
		throws RepositoryException;
	
	/**
	 * Get user last imported mail attachments
	 */
	public Collection<DashboardDocumentResult> getUserLastImportedMailAttachments(String token)
			throws RepositoryException;
	
	/**
	 * Get user documents size
	 */
	public long getUserDocumentsSize(String token) throws RepositoryException, DatabaseException;
	
	/**
	 * Get user searchs
	 */
	public Collection<String> getUserSearchs(String token) throws RepositoryException, 
			DatabaseException;
	
	/**
	 * Find
	 */
	public Collection<DashboardDocumentResult> find(String token, int pqId)	throws IOException,
			ParseException, RepositoryException, DatabaseException;
		
	/**
	 * Get last week top downloaded documents
	 */
	public Collection<DashboardDocumentResult> getLastWeekTopDownloadedDocuments(String token)
			throws RepositoryException;

	/**
	 * Get last month downloaded documents
	 */
	public Collection<DashboardDocumentResult> getLastMonthTopDownloadedDocuments(String token)
			throws RepositoryException;
	
	/**
	 * Get last week top modified documents
	 */
	public Collection<DashboardDocumentResult> getLastWeekTopModifiedDocuments(String token)
			throws RepositoryException;

	/**
	 * Get las month top modified documentd
	 */
	public Collection<DashboardDocumentResult> getLastMonthTopModifiedDocuments(String token)
			throws RepositoryException;

	/**
	 * Get last modified documents
	 */
	public Collection<DashboardDocumentResult> getLastModifiedDocuments(String token)
			throws RepositoryException;
	
	/**
	 * Get last uploaded documents
	 */
	public Collection<DashboardDocumentResult> getLastUploadedDocuments(String token)
			throws RepositoryException;

	/**
	 * Visite node
	 */
	public void visiteNode(String token, String source, String node, Calendar date)
			throws RepositoryException;
}
