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
import com.openkm.core.ParseException;
import com.openkm.core.RepositoryException;

public interface DashboardModule {

	/**
	 * Get list of locked documents by user
	 * 
	 * @param token
	 * @return
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public Collection<DashboardDocumentResult> getUserLockedDocuments(String token)
			throws RepositoryException;
	
	/**
	 * Get list of cheked-out documents by user
	 * 
	 * @param token
	 * @return
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public Collection<DashboardDocumentResult> getUserCheckedOutDocuments(String token)
			throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public Collection<DashboardDocumentResult> getUserSubscribedDocuments(String token)
			throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public Collection<DashboardFolderResult> getUserSubscribedFolders(String token)
			throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException 
	 */
	public Collection<DashboardDocumentResult> getUserLastUploadedDocuments(String token)
			throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException 
	 */
	public Collection<DashboardDocumentResult> getUserLastModifiedDocuments(String token)
			throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardDocumentResult> getUserLastDownloadedDocuments(String token)
		throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardMailResult> getUserLastImportedMails(String token)
		throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardDocumentResult> getUserLastImportedMailAttachments(String token)
			throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public long getUserDocumentsSize(String token) throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<String> getUserSearchs(String token) throws RepositoryException;
	
	/**
	 * @param token
	 * @param name
	 * @return
	 * @throws IOException
	 * @throws RepositoryException
	 */
	public Collection<DashboardDocumentResult> find(String token, String name)
			throws IOException, ParseException, RepositoryException;
		
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardDocumentResult> getLastWeekTopDownloadedDocuments(String token)
			throws RepositoryException;

	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardDocumentResult> getLastMonthTopDownloadedDocuments(String token)
			throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardDocumentResult> getLastWeekTopModifiedDocuments(String token)
			throws RepositoryException;

	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardDocumentResult> getLastMonthTopModifiedDocuments(String token)
			throws RepositoryException;

	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardDocumentResult> getLastModifiedDocuments(String token)
			throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardDocumentResult> getLastUploadedDocuments(String token)
			throws RepositoryException;

	/**
	 * @param token
	 * @param node
	 * @param source
	 * @throws RepositoryException
	 */
	public void visiteNode(String token, String source, String node, Calendar date)
			throws RepositoryException;
}
