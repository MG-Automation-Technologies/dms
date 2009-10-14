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

package es.git.openkm.module;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;

import es.git.openkm.bean.DashboardStatsDocumentResult;
import es.git.openkm.bean.DashboardStatsFolderResult;
import es.git.openkm.bean.DashboardStatsMailResult;
import es.git.openkm.core.RepositoryException;

public interface DashboardModule {

	/**
	 * Get list of locked documents by user
	 * 
	 * @param token
	 * @return
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public Collection<DashboardStatsDocumentResult> getUserLockedDocuments(String token) throws RepositoryException;
	
	/**
	 * Get list of cheked-out documents by user
	 * 
	 * @param token
	 * @return
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public Collection<DashboardStatsDocumentResult> getUserCheckedOutDocuments(String token) throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public Collection<DashboardStatsDocumentResult> getUserSubscribedDocuments(String token) throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException If there is any general repository problem or the query fails.
	 */
	public Collection<DashboardStatsFolderResult> getUserSubscribedFolders(String token) throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException 
	 */
	public Collection<DashboardStatsDocumentResult> getUserLastUploadedDocuments(String token) throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException 
	 */
	public Collection<DashboardStatsDocumentResult> getUserLastModifiedDocuments(String token) throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardStatsDocumentResult> getUserLastDownloadedDocuments(String token) throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardStatsMailResult> getUserLastImportedMails(String token) throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardStatsDocumentResult> getUserLastImportedMailAttachments(String token) throws RepositoryException;
	
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
	public Collection<DashboardStatsDocumentResult> find(String token, String name) throws IOException, RepositoryException;
		
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardStatsDocumentResult> getLastWeekTopDownloadedDocuments(String token) throws RepositoryException;

	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardStatsDocumentResult> getLastMonthTopDownloadedDocuments(String token) throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardStatsDocumentResult> getLastWeekTopModifiedDocuments(String token) throws RepositoryException;

	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardStatsDocumentResult> getLastMonthTopModifiedDocuments(String token) throws RepositoryException;

	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardStatsDocumentResult> getLastModifiedDocuments(String token) throws RepositoryException;
	
	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardStatsDocumentResult> getLastUploadedDocuments(String token) throws RepositoryException;

	/**
	 * @param token
	 * @return
	 * @throws RepositoryException
	 */
	public Collection<DashboardStatsDocumentResult> getUserLastMails(String token) throws RepositoryException;	

	/**
	 * @param token
	 * @param node
	 * @param source
	 * @throws RepositoryException
	 */
	public void visiteNode(String token, String source, String node, Calendar date) throws RepositoryException;
}
