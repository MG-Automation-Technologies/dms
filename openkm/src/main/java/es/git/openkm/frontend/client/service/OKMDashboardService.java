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

package es.git.openkm.frontend.client.service;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import es.git.openkm.frontend.client.OKMException;
import es.git.openkm.frontend.client.bean.GWTDashboardStatsDocumentResult;
import es.git.openkm.frontend.client.bean.GWTDashboardStatsFolderResult;
import es.git.openkm.frontend.client.bean.GWTDashboardStatsMailResult;

/**
 * @author jllort
 *
 */
public interface OKMDashboardService extends RemoteService {
	public List<GWTDashboardStatsDocumentResult> getUserLockedDocuments() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getUserCheckedOutDocuments() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getUserLastModifiedDocuments() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getUserSubscribedDocuments() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getUserLastUploadedDocuments() throws OKMException;
	public List<GWTDashboardStatsFolderResult> getUserSubscribedFolders() throws OKMException;
	public List<String> getUserSearchs() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> find(String name) throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getLastWeekTopDownloadedDocuments() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getLastMonthTopDownloadedDocuments() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getLastWeekTopModifiedDocuments() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getLastMonthTopModifiedDocuments() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getUserLastDownloadedDocuments() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getLastModifiedDocuments() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getLastUploadedDocuments() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getUserLastImportedMailAttachments() throws OKMException;
	public List<GWTDashboardStatsMailResult> getUserLastImportedMails() throws OKMException;
	public List<GWTDashboardStatsDocumentResult> getUserLastMails() throws OKMException;
	public void visiteNode(String source, String node, Date date) throws OKMException;
}
