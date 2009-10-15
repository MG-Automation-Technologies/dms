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

import com.google.gwt.user.client.rpc.AsyncCallback;

import es.git.openkm.frontend.client.bean.GWTDashboardStatsDocumentResult;
import es.git.openkm.frontend.client.bean.GWTDashboardStatsFolderResult;
import es.git.openkm.frontend.client.bean.GWTDashboardStatsMailResult;

/**
 * @author jllort
 *
 */
public interface OKMDashboardServiceAsync {
	public void getUserLockedDocuments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback);
	public void getUserCheckedOutDocuments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback);
	public void getUserLastModifiedDocuments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback); 
	public void getUserSubscribedDocuments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback);
	public void getUserLastUploadedDocuments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback); 
	public void getUserSubscribedFolders(AsyncCallback<List<GWTDashboardStatsFolderResult>> callback);
	public void getUserSearchs(AsyncCallback<List<String>> callback);
	public void find(String name, AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback); 
	public void getLastWeekTopDownloadedDocuments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback);
	public void getLastMonthTopDownloadedDocuments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback);
	public void getLastWeekTopModifiedDocuments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback);
	public void getLastMonthTopModifiedDocuments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback);
	public void getUserLastDownloadedDocuments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback);
	public void getLastModifiedDocuments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback);
	public void getLastUploadedDocuments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback);
	public void getUserLastImportedMailAttachments(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback);
	public void getUserLastImportedMails(AsyncCallback<List<GWTDashboardStatsMailResult>> callback);
	public void getUserLastMails(AsyncCallback<List<GWTDashboardStatsDocumentResult>> callback);
	public void visiteNode(String source, String node, Date date, AsyncCallback callback);
}
